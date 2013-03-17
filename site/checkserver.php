<?php
define('INCLUDE_CHECK', true);
require_once 'config.php';

$login = $_GET['user'];
$serverid = $_GET['serverId'];

$stmt = $pdo -> prepare("SELECT COUNT(*) FROM `$db_table` WHERE `$db_columnUser` = :user AND `$db_columnServer` = :serverid");
$stmt -> bindValue(':user', $login);
$stmt -> bindValue(':serverid', $serverid);
$stmt -> execute() or die(print_r($stmt -> errorInfo(), true));
$stmt -> bindColumn(1, $count);
$stmt -> fetch();

if((int)$count === 1) echo 'YES';
else echo 'NO';

?>