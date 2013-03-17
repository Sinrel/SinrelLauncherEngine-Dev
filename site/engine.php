<?php
define('INCLUDE_CHECK', true);

require_once 'config.php';
require_once 'core.php';

if(!isset($_POST['action']))
	die('NULL');

$action = $_POST['action'];

if($action === 'check') {
	if(!isset($_POST['system'], $_POST['client']))
		die('NULL');

	$os = $_POST['system'];
	$client = $_POST['client'];

	if($client === 'standart') {
		$files = array(
				'jinput.jar',
				'lwjgl_util.jar',
				'minecraft.jar',
				'natives/'. $os .'.zip');

		foreach($files as $file) {
			if(!file_exists('client/'. $file)) {
				die("CLIENT_NOT_EXIST_ON_SERVER");
			}
		}

		// Poor, Poor SLE...

		$md5jinput		= md5_file("client/jinput.jar");
		$md5lwjql		= md5_file("client/lwjgl.jar");
		$md5lwjql_util	= md5_file("client/lwjgl_util.jar");
		$md5jar			= md5_file("client/minecraft.jar");
			
		$result = $md5jinput;
		$result .= $md5lwjql;
		$result .= $md5lwjql_util;
		$result .= $md5jar;

		if( strcmp( $result, $_POST['hash'] ) == 0 ) {
			die('OK');
		}else{
			die('CLIENT_DOES_NOT_MATCH');
		}

	} else {
		//Do multiclient checking
	}
} else if($action === 'auth') {

	if(!isset($_POST['login'], $_POST['pass'])) {
		die('LOGIN_OR_PASS_NOT_EXIST');
	}

	$login = $_POST['login']; // It's already decoded
	$pass = $_POST['pass'];

	$stmt = DB::prepare('SELECT `'. DB_L_BLOCKUNTIL .'` FROM `'. DB_L_TABLE .'` WHERE `'. DB_L_USERNAME .'` = ?');
	$stmt -> bind_param('s', $user);
	stmtexec($stmt);
	$stmt -> bind_result($blockuntil);
	$stmt -> fetch();
	$stmt -> close();

	if(!is_null($blockuntil)) {
		$blockUntilDate = new DateTime($blockuntil);
		if($blockUntilDate < new DateTime()) {
			$stmt = DB::prepare('UPDATE `'. DB_L_TABLE .'` SET '.
					'`'. DB_L_RETRIESLEFT .'` = '. L_LOGIN_FAIL_MAX_RETRIES .', `'. DB_L_BLOCKUNTIL .'` = NULL WHERE `'. DB_L_USERNAME .'` = ?');
			$stmt -> bind_param('s', $user);
			stmtexec($stmt);
			$stmt -> fetch();
			$stmt -> close();
			$blockuntil = null;
		}
	}

	if(!is_null($blockuntil))
		die('TOO_MANY_REQUESTS');

	global $realPass;

	if ( $integration === 'joomla' || $integration === 'wordpress' || $integration === 'dle' ) {
		$stmt = $pdo -> prepare("SELECT `$db_columnPass` FROM `$db_table` WHERE `$db_columnUser` = ?");
		$stmt -> bindValue(1, $login);
		$stmt -> execute() or die(print_r($stmt -> errorInfo(), true));
		$stmt -> bindColumn(1, $realPass);
		$stmt -> fetch();
	}

	if($integration === 'xenforo') {
		$stmt = $pdo -> prepare("SELECT `$db_tableOther`.`$db_columnPass` FROM `$db_tableOther` WHERE `$db_table`.`$db_columnId` = `$db_tableOther`.`$db_columnId` AND `$db_table`.`$db_columnUser` = ?");
		$stmt -> bindValue(1, $login);
		$stmt -> execute() or die(print_r($stmt -> errorInfo(), true));
		$stmt -> bindColumn(1, $resultPass);
		$stmt -> fetch();

		$realPass = substr($resultPass, 22, 64);
		$salt = substr($resultPass, 105, 64);
	}

	if ($realPass) {
		$crypt = 'hash_'. $integration;
		$checkPass = $crypt($pass);

		if($realPass === $checkPass) {
			$len = rand(28, 32);
			$session = substr(str_shuffle(str_repeat("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ_-", 5)), 0, $len);

			$stmt = $pdo -> prepare("UPDATE `$db_table` SET `$db_columnSesId` = :session WHERE `$db_columnUser` = :login");
			$stmt -> bindValue(':session', $session);
			$stmt -> bindValue(':login', $login);
			$stmt -> execute() or die(print_r($stmt -> errorInfo(), true));

			die('OK<:>'. $session);
		} else {
			echo 'BAD_LOGIN_OR_PASSWORD';
		}
	} else {
		echo 'BAD_LOGIN_OR_PASSWORD';
	}

}