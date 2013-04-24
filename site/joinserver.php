<?php
define('INCLUDE_CHECK', true);
require_once 'config.php';
require_once 'core.php';

$sessionid = AES::decrypt( $_GET['sessionId'] , $protectionKey );
$serverid = $_GET['serverId'];
$login = $_GET['user'];
$token = $_GET['mob'];

try {
	$stmt = $pdo -> prepare("SELECT COUNT(*) FROM `$db_table` WHERE `$db_columnSesId` = :sessionid AND `$db_columnToken` = :token AND `$db_columnUser` = :user AND `$db_columnServer` = :serverid");
	$stmt -> execute(array(':user' => $login, ':serverid' => $serverid, ':sessionid' => $sessionid, ':token' => $token));
	$stmt -> bindColumn(1, $count);
	$stmt -> fetch(PDO::FETCH_BOUND);

	if((int) $count === 1) echo 'OK';
	else {
		$stmt = $pdo -> prepare("UPDATE `$db_table` SET `$db_columnServer` = :serverid WHERE `$db_columnSesId` = :sessionid AND `$db_columnUser` = :user");
		$stmt -> execute(array(':user' => $login, ':serverid' => $serverid, ':sessionid' => $sessionid));

		if($stmt -> rowCount() == 1) echo 'OK';
		else echo 'Bad login';
	}
} catch(PDOException $e) {
	die('PDO error: '. $e -> getMessage() . PHP_EOL);
}
?>