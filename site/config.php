<?php

if(!defined('INCLUDE_CHECK')) die("У вас нет прав на выполнение данного файла!");

// Настройка подключения к базе данных
$hostname				= '**********'; // Ip-адрес MySQL
$port				=  3306; // Порт базы данных
$username				= '**********'; // Пользователь базы данных
$password				= '**********'; // Пароль базы данных
$databaseName			= '**********'; //База данных

/*
 joomla      - интеграция с Joomla
 xenforo     - интеграция с XenForo
 wordpress   - интеграция с WordPress
 dle         - интеграция с DLE
 other       - ручная настройка
*/
$integration = 'wordpress';
$prefix = 'wp';

// Настройка структуры базы данных (если выбран режим ручной настройки)
$db_table       		= '_users'; //Таблица с пользователями
$db_columnId  			= 'id'; //Колонка с ID пользователей
$db_columnUser  	    = 'user_login'; //Колонка с именами пользователей
$db_columnPass        	= 'user_pass'; //Колонка с паролями пользователей

$versionCode			= 1;
$version 				= 'beta 1';
$protectionKey			= 'sle2'; //Ключ защиты. Никому его не говорите.

/*
try {
	// Подключаемся к БД
	$pdo = new PDO("mysql:host=$db_host;port=$db_port;dbname=$db_database", $db_user, $db_pass, array( PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8') );
	$pdo -> setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch ( PDOException $e ) {
	die('BAD_CONNECTION');
}
*/

?>