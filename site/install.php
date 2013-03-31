<?php
define('INCLUDE_CHECK', true);
echo '<html><head>';
echo '<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>';
echo <<<CSS
<style type="text/css">
body {
	font-family: sans-serif;
	font-size: 82%;
}
div.container{
	width:30em;
	margin:0 auto;
	border-radius:4px 4px 0 0;
	-moz-border-radius:4px 4px 0 0;
	-webkit-border-radius:4px 4px 0 0;
	border:#aaa solid 1px;
	padding:1.5em;
	background:#eee;
}
div.success,
div.notice,
div.error {
    margin:             0.5em 0 0.5em 0;
    border:             1px solid;
    background-repeat:  no-repeat;
    background-position: 10px 50%;
    padding:            10px 10px 10px 25px;
            
    -moz-border-radius:5px;
    -webkit-border-radius:5px;
    border-radius:5px;

    -moz-box-shadow: 0 1px 1px #fff inset;
    -webkit-box-shadow: 0 1px 1px #fff inset;
    box-shadow:  0 1px 1px #fff inset;
}
.error {
    border:1px solid maroon !important;
    color: #000;
    background:pink;
}
.success {
    color:              #000000;
    background-color:   #ebf8a4;
}
.notice {
    color:              #000;
    background-color:   #e8eef1;
}
div.container{
	width:30em;
	margin:0 auto;
	border-radius:4px 4px 0 0;
	-moz-border-radius:4px 4px 0 0;
	-webkit-border-radius:4px 4px 0 0;
	border:#aaa solid 1px;
	padding:1em;
	background:#eee;
}
</style>
CSS;

echo '</head><body>';
echo '<div style="width:1px;height:40%"></div>';
echo '<div class="container">';

require_once 'config.php';


try {
	$query = "SELECT `COLUMN_NAME` ".
			"FROM information_schema.COLUMNS ".
			"WHERE ".
			"TABLE_SCHEMA = '$db_database' ".
			"AND TABLE_NAME = '$db_table' ".
			"AND COLUMN_NAME IN ('$db_columnSesId', '$db_columnServer', '$db_columnBlockUntil', '$db_columnRetriesLeft')";
	$stmt = $pdo -> query($query);
	$stmt -> execute();
	$arr = $stmt->fetchAll(PDO::FETCH_COLUMN, 0); // Получаем массив колонок в таблице

	echo '<div class="success">';
	if(count($arr) === 4) {
		echo 'Все требуемые колонки уже есть в таблице';
	} else {
		$neededCols = array($db_columnSesId, $db_columnServer, $db_columnBlockUntil, $db_columnRetriesLeft);
		$parts = array(); // Куски запроса
		$cols = array(); // Добавленные колонки

		$col = $db_columnSesId;
		if(!in_array($col, $arr))
			$parts[] = "ADD `$col` varchar(255) DEFAULT NULL";

		$prevcol = $col;
		$col = $db_columnServer;
		if(!in_array($col, $arr))
			$parts[] = "ADD `$col` varchar(255) DEFAULT NULL AFTER `$prevcol`";

		$prevcol = $col;
		$col = $db_columnBlockUntil;
		if(!in_array($col, $arr))
			$parts[] = "ADD `$col` datetime DEFAULT NULL AFTER `$prevcol`";

		$prevcol = $col;
		$col = $db_columnRetriesLeft;
		if(!in_array($col, $arr))
			$parts[] = "ADD `$col` tinyint(3) unsigned NOT NULL AFTER `$prevcol`";

		$stmt = $pdo -> query("ALTER TABLE `$db_table` ". implode(', ', $parts));
		echo 'Добавлены колонки: '. implode(', ', array_diff($neededCols, $arr));

	}
	echo '</div>';
	echo '<div class="notice">Не забудьте удалить этот файл (install.php)</div>';
} catch(PDOException $ex) {
	echo '<div class="error">Не удалось создать колонки: <br/>';
	echo $ex -> getMessage();
	echo '</div>';
}

echo '</div>';
echo '</body></html>'
?>