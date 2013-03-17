<?php
echo '<html><head>';
echo '<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>';
echo '</head><body>';

require_once 'config.php';


$query = "ALTER TABLE `$db_table`".
		"ADD `$db_columnSesId` varchar(255) DEFAULT NULL,".
		"ADD `$db_columnServer` varchar(255) DEFAULT NULL,".
		"ADD `$db_columnBlockUntil` datetime DEFAULT NULL,".
		"ADD `$db_columnRetriesLeft` tinyint(3) unsigned NOT NULL";

$stmt = $pdo -> query($query);
if($stmt === false) {
	echo 'Не удалось создать колонки: ' . "<br/>";
	print_r($stmt -> errorInfo());
} else {
	echo 'Создано '. $stmt->columnCount .' колонок' . "<br/>";
	echo 'Удалите этот файл (install.php), иначе придут злые хацкеры и взломают вас :O';
}

echo '</body></html>'
?>