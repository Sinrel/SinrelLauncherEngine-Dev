<?
	define( 'INCLUDE_CHECK', true );
	
	include 'config.php';
	require_once 'core.php';
	
	$action = $_POST[ 'action' ];
	
	if( !isset( $action ) ) {
		die( "NULL" );
	}

	if( $action == 'auth' ) {
		
		$login = $_POST['login'];
		$pass = $_POST['pass'];
		
		if( !isset( $login ) || !isset( $pass ) ) {
			die( "LOGIN_OR_PASS_NOT_EXIST" );
		}
		
		$login = urldecode( $_POST['login'] );
		$pass = urldecode( $_POST['pass'] );
		
		$login = base64_decode( base64_decode( $login ) ) ;
		$pass =  base64_decode( base64_decode( $pass ) ) ;
				
		$login = mysql_real_escape_string( $login );
   		$pass = mysql_real_escape_string( $pass );
		
		if ( $crypt == 'joomla' || $crypt == 'wordpress' || $crypt == 'dle' ) {
			$row = mysql_fetch_assoc(mysql_query("SELECT $db_columnUser,$db_columnPass FROM $db_table WHERE $db_columnUser='{$login}'"));
			$realPass = $row[$db_columnPass];
		}

		if($crypt == 'xenforo') {
			$row = mysql_fetch_assoc(mysql_query("SELECT $db_table.$db_columnId,$db_table.$db_columnUser,$db_tableOther.$db_columnId,$db_tableOther.$db_columnPass FROM $db_table, $db_tableOther WHERE $db_table.$db_columnId = $db_tableOther.$db_columnId AND $db_table.$db_columnUser='{$login}'"));
			$realPass = substr($row[$db_columnPass],22,64);
			$salt = substr($row[$db_columnPass],105,64);
		}
		
		if ($realPass) {
				
			$checkPass = $crypt();

		    if( strcmp( $realPass,$checkPass ) == 0 ) {
		    	
				$sess = rand(1000000000, 2147483647).rand(1000000000, 2147483647).rand(0,9);
				
				die ( 'OK<:>'.base64_encode( base64_encode( $sess ) ) );	
		    	
				/*
		        mysql_query("UPDATE $db_table SET $db_columnSesId='$session' WHERE $db_columnUser = '$login'") or die ("BAD_CONNECTION");
		    	
		    	$dlticket = md5($login);
		    	echo $gamebuild.':'.$dlticket.':'.$login.':'.$sessid.':';
				 
				 */
				
			} else {
				echo "BAD_LOGIN_OR_PASSWORD";
			}
		} else {
			echo "BAD_LOGIN_OR_PASSWORD";
		}
		
	}
 
?>