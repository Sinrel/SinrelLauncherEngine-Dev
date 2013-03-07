<?
	define( 'INCLUDE_CHECK', true );
	
	include 'config.php';
	require_once 'core.php';
	
	$action = $_POST[ 'action' ];
	
	if( !isset( $action ) ) {
		die( "NULL" );
	}

	if( $action == 'check'  ) {
		
		if( $_POST['client'] == 'standart' ) {	
			if( !file_exists( 'client/jinput.jar' ) || 
				!file_exists( 'client/lwjgl.jar' ) || 
			    !file_exists( 'client/lwjgl_util.jar' ) || 
			    !file_exists( 'client/minecraft.jar' )
			) die ( "CLIENT_NOT_EXIST_ON_SERVER" );
		
			$md5jinput		= md5_file("client/jinput.jar");
			$md5lwjql		= md5_file("client/lwjgl.jar");
			$md5lwjql_util	= md5_file("client/lwjgl_util.jar");
			$md5jar			= md5_file("client/minecraft.jar");
			
			$result  = $md5jinput;
			$result .= $md5lwjql;
			$result .= $md5lwjql_util;
			$result .= $md5jar;

			if( strcmp( $result , $_POST['hash'] ) == 0 ) {	
				die( 'OK' );
			}else{
				die( 'CLIENT_DOES_NOT_MATCH' );
			}
		
		}else{
			//Do multiclient checking
		}
	}

	if( $action == 'auth' ) {		

		if( !isset( $_POST['login'] ) || !isset( $_POST['pass'] ) ) {
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
		    	
				$session = rand(1000000000, 2147483647).rand(0,9);
				
				mysql_query("UPDATE $db_table SET $db_columnSesId='$session' WHERE $db_columnUser = '$login'") or die ("BAD_CONNECTION");
				
				die ( 'OK<:>'.base64_encode( base64_encode( $session ) ) );	
				
			} else {
				echo "BAD_LOGIN_OR_PASSWORD";
			}
		} else {
			echo "BAD_LOGIN_OR_PASSWORD";
		}
		
	}
 
?>