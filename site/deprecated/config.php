<?php

if(!defined('INCLUDE_CHECK')) die("У вас нет прав на выполнение данного файла!");

// Настройка подключения к базе данных
$db_host				= '**********'; // Ip-адрес MySQL
$db_port				=  3306; // Порт базы данных
$db_user				= '**********'; // Пользователь базы данных
$db_pass				= '**********'; // Пароль базы данных
$db_database			= '**********'; //База данных

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

//DANGER ZONE
$db_columnSesId	 	    = 'session'; //Колонка с сессиями пользователей
$db_columnToken	        = 'token'; //Колонка с "билетами" для захода на сервер
$db_columnServer	    = 'server'; //Колонка с серверами пользователей
$db_columnBlockUntil	= 'blockUntil'; //Колонка с временем, по которое заблокирован пользователь
$db_columnRetriesLeft	= 'retriesLeft'; //Колонка с количеством оставшихся неудачных попыток до блокировки

$versionCode			= 1;
$version 				= 'beta 1';
$protectionKey			= 'sle2'; //Ключ защиты. Никому его не говорите.

try {
	// Подключаемся к БД
	$pdo = new PDO("mysql:host=$db_host;port=$db_port;dbname=$db_database", $db_user, $db_pass, array( PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8') );
	$pdo -> setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch ( PDOException $e ) {
	die('BAD_CONNECTION');
}

?>