<?php
define('INCLUDE_CHECK', true);
require_once 'config.php';

$login = $_GET['user'];
$serverid = $_GET['serverId'];
$count = 0;

try {
	$stmt = $pdo -> prepare("SELECT COUNT(*) FROM `$db_table` WHERE `$db_columnUser` = :user AND `$db_columnServer` = :serverid");
	$stmt -> execute(array(':user' => $login, ':serverid', $serverid));
	$stmt -> bindColumn(1, $count);
	$stmt -> fetch(PDO::FETCH_BOUND);
} catch(PDOException $e) {
	die('PDO error: '. $e -> getMessage() . PHP_EOL);
}

if((int)$count === 1) echo 'YES';
else echo 'NO';

?>