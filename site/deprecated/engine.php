<?php
define('INCLUDE_CHECK', true);

require_once 'config.php';

switch ( $integration ) {
	case 'wordpress': 
		define( 'DB_TABLE', $prefix.'_users' );
		define( 'DB_COLUMN_ID', 'id' );
		define( 'DB_COLUMN_USER', 'user_login' );
		define( 'DB_COLUMN_PASS', 'user_pass' );
		
		break;
	case 'dle': 
		define( 'DB_TABLE', $prefix.'_users' );
		define( 'DB_COLUMN_ID', 'user_id' );
		define( 'DB_COLUMN_USER', 'name' );
		define( 'DB_COLUMN_PASS', 'password' );
		
		break;
	case 'xenforo':
		define( 'DB_TABLE', $prefix.'_user' );
		define( 'DB_COLUMN_ID', 'user_id' );
		define( 'DB_COLUMN_USER', 'username' );
		define( 'DB_COLUMN_PASS', 'data' );
		
		define( 'DB_TABLE_OTHER', 'xf_user_authenticate' );
		break;
	case 'joomla': 
		define( 'DB_TABLE', $prefix.'_users' );
		define( 'DB_COLUMN_ID', 'id' );
		define( 'DB_COLUMN_USER', 'name' );
		define( 'DB_COLUMN_PASS', 'password' );

		break;
	default:
		define( 'DB_TABLE', $prefix.$db_table );
		define( 'DB_COLUMN_ID', $db_columnId );
		define( 'DB_COLUMN_USER', $db_columnUser );
		define( 'DB_COLUMN_PASS', $db_columnPass );
		
		define( 'DB_COLUMN_SESSION', $db_columnSesId );
		define( 'DB_COLUMN_TOKEN',  $db_columnToken );
		define( 'DB_COLUMN_SERVER', $db_columnServer );
		define( 'DB_COLUMN_BLOCK_UNTIL', $db_columnBlockUntil );
		define( 'DB_COLUMN_RETRIES_LEFT', $db_columnRetriesLeft ); 
		
		break;
}

if(!isset($_POST['command']))
	die('NULL');

$command = $_POST['command'];

try {
	if( $command == "key" ) {
					die( strrev( base64_encode( $protectionKey ) ) );
	}elseif($command  === 'check') {
		if(!isset($_POST['system'], $_POST['client']))
			die('NULL');

		$os = $_POST['system'];
		$client = $_POST['client'];

		$files = array(
				'forge.jar',
				'libraries.jar',
				'minecraft.jar',
				'extra.jar',
				'natives/'. $os .'.zip');

		foreach($files as $file) {
			if(!file_exists('clients/'.$client.'/bin/'. $file)) {
				die("CLIENT_NOT_EXIST_ON_SERVER");
			}
		}

		$md5jinput		= md5_file("clients/".$client."/bin/forge.jar");
		$md5lwjql		= md5_file("clients/".$client."/bin/libraries.jar");
		$md5lwjql_util	= md5_file("clients/".$client."/bin/minecraft.jar");
		$md5jar			= md5_file("clients/".$client."/bin/extra.jar");
			
		$result = $md5jinput;
		$result .= $md5lwjql;
		$result .= $md5lwjql_util;
		$result .= $md5jar;

		if( strcmp( $result, $_POST['hash'] ) == 0 ) {
			die('OK');
		}else{
			die('CLIENT_DOES_NOT_MATCH');
		}
	} elseif( $command === "directory" ) {
		$client = $_POST['client'];
		$data = $_POST['data'];
		$folder = $_POST['folder'];
		if ( $data === "not_exist" ) 
			if( !file_exists( "clients/".$client."/".$folder ) )
				die( "OK" );
			else
				die( "DIRECTORY_DOES_NOT_MATCH" );
		if( $data === "count" )
			if( !file_exists( "clients/".$client."/".$folder ) ) 
				die( "DIRECTORY_DOES_NOT_MATCH" ); 
			
			$count = filescount( "clients/".$client."/".$folder );
									
			if( $count == $_POST['option']  )
				die( "OK" );
			else 
				die( "DIRECTORY_DOES_NOT_MATCH" ); 
		if( $data === "checksum" ) {
			
		}
	} elseif($command  === 'auth') {
		if(!isset($_POST['login'], $_POST['pass'])) {
			die('LOGIN_OR_PASS_NOT_EXIST');
		}

		$login = AES::decrypt( $_POST['login'] , $protectionKey );
		$pass = AES::decrypt( $_POST['pass'] , $protectionKey );

		$stmt = $pdo -> prepare("SELECT `$db_columnBlockUntil` FROM ".DB_TABLE." WHERE ".DB_COLUMN_USER." = ?");
		$stmt -> execute(array($login));
		$stmt -> bindColumn(1, $blockuntil);
		$stmt -> fetch(PDO::FETCH_BOUND);
		
		if(!is_null($blockuntil)) {
			$blockUntilDate = new DateTime($blockuntil);
			if($blockUntilDate < new DateTime()) {
				$stmt = $pdo -> prepare("UPDATE ".DB_TABLE." SET ".
						"`$db_columnRetriesLeft` = :retries, `$db_columnBlockUntil` = NULL WHERE ".DB_COLUMN_USER." = :login");
				$stmt -> bindValue(':retries', $cfg_maxRetries, PDO::PARAM_INT);
				$stmt -> bindValue(':login', $login);
				$stmt -> execute();
				$blockuntil = null;
			}
		}

		if(!is_null($blockuntil))
			throw new Exception('TOO_MANY_REQUESTS');
			
		$loggedIn = false;
			
		{
			global $realPass;

			if ( $integration === 'joomla' || $integration === 'wordpress' || $integration === 'dle' ) {
				$stmt = $pdo -> prepare("SELECT ".DB_COLUMN_PASS." FROM ".DB_TABLE." WHERE ".DB_COLUMN_USER." = ?");
				$stmt -> execute(array($login));
				$stmt -> bindColumn(1, $realPass);
				$stmt -> fetch(PDO::FETCH_BOUND);
			}

			if($integration === 'xenforo') {
				$stmt = $pdo -> prepare("SELECT `$db_tableOther`.`$db_columnPass` FROM `$db_tableOther` WHERE `$db_table`.`$db_columnId` = `$db_tableOther`.`$db_columnId` AND `$db_table`.`$db_columnUser` = ?");
				$stmt -> execute(array($login));
				$stmt -> bindColumn(1, $resultPass);
				$stmt -> fetch(PDO::FETCH_BOUND);

				$realPass = substr($resultPass, 22, 64);
				$salt = substr($resultPass, 105, 64);
			}

			if ($realPass) {
				$crypt = 'hash_'. $integration;
				$checkPass = $crypt($pass);

				$loggedIn = ($realPass === $checkPass);
			}
		}

		if(!$loggedIn) {
			$date = new DateTime($cfg_loginCooldown);
			$mysqldate = $date -> format('Y-m-d H:i:s');

			$stmt = $pdo -> prepare("UPDATE ".DB_TABLE." SET ".
					"`$db_columnRetriesLeft` = IF (`$db_columnRetriesLeft` > 0, `$db_columnRetriesLeft` - 1, 0), ".
					"`$db_columnBlockUntil` = IF (`$db_columnRetriesLeft` = 0, :date, NULL) WHERE ".DB_COLUMN_USER." = :user");
			$stmt -> execute(array(':date' => $mysqldate, ':user' => $login));
			throw new Exception('BAD_LOGIN_OR_PASSWORD');
		} else {

			// Generate session
			$len = rand(28, 32);
			$session = substr(str_shuffle(str_repeat("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_-", 5)), 0, $len);
			
			// Update session and token
			$stmt = $pdo -> prepare("UPDATE ".DB_TABLE." SET `$db_columnSesId` = :session, `$db_columnToken` = :token WHERE ".DB_COLUMN_USER." = :login");
			$stmt -> execute(array(':session' => $session, ':token' => $token, ':login' => $login));

			echo 'OK<:>'.AES::encrypt( $session , $protectionKey );
		}
	} else if( $command  === 'launcher' ) {
		die( $versionCode.'<:>'.$version );
	}
} catch(PDOException $e) {
	die('PDO error: '. $e -> getMessage() . PHP_EOL);
	//INTERNAL_ERROR
} catch(Exception $e) {
	//INTERNAL_ERROR
	echo $e -> getMessage();
}

?>
