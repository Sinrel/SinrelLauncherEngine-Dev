<?php
define('INCLUDE_CHECK', true);
require_once 'config.php';

$sessionid = $_GET['sessionId'];
$serverid = $_GET['serverId'];
$login = $_GET['user'];

$stmt = $pdo -> prepare("SELECT COUNT(*) FROM `$db_table` WHERE `$db_columnSesId` = :sessionid AND `$db_columnUser` = :user AND `$db_columnServer` = :serverid");
$stmt -> bindValue(':user', $login);
$stmt -> bindValue(':serverid', $serverid);
$stmt -> bindValue(':sessionid', $sessionid);
$stmt -> execute() or die(print_r($stmt -> errorInfo(), true));
$stmt -> bindColumn(1, $count);
$stmt -> fetch();

if((int) $count === 1) echo 'OK';
else {
	$stmt = $pdo -> prepare("UPDATE `$db_table` SET `$db_columnServer` = :serverid WHERE `$db_columnSesId` = :sessionid AND `$db_columnUser` = :user");
	$stmt -> bindValue(':user', $login);
	$stmt -> bindValue(':serverid', $serverid);
	$stmt -> bindValue(':sessionid', $sessionid);
	$stmt -> execute() or die(print_r($stmt -> errorInfo(), true));
	
	if($stmt -> rowCount() == 1) echo 'OK';
	else echo 'Bad login';
}

?>