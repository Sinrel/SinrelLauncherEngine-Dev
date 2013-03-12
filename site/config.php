<?php

if(!defined('INCLUDE_CHECK')) die("У вас нет прав на выполнение данного файла!");

/* Метод шифрования
 joomla        - интеграция с Joomla
xenforo	 	  - интеграция с XenForo
wordpress     - интеграция с WordPress
dle 		  - интеграция с DLE
*/
$integration = 'wordpress';

// Настройка подключения к базе данных
$db_host			= '***********'; // Ip-адрес MySQL
$db_port			= 3306; // Порт базы данных
$db_user			= '***********'; // Пользователь базы данных
$db_pass			= '***********'; // Пароль базы данных
$db_database		= '***********'; //База данных

// Настройка структуры базы данных
$db_table       	= 'wp_users'; //Таблица с пользователями
$db_columnId  		= 'id'; //Колонка с ID пользователей
$db_columnUser  	= 'user_login'; //Колонка с именами пользователей
$db_columnPass  	= 'user_pass'; //Колонка с паролями пользователей

//DANGER ZONE (why?)
$db_tableOther 		= 'xf_user_authenticate'; //Дополнительная таблица для XenForo
$db_columnSesId	 	= 'session'; //Колонка с сессиями пользователей
$db_columnServer	= 'server'; //Колонка с серверами пользователей
$db_columnBlockUntil	= 'blockUntil'; //Колонка с временем, по которое заблокирован пользователь
$db_columnRetriesLeft	= 'retriesLeft'; //Колонка с количеством оставшихся неудачных попыток до блокировки


//PDO круче
$pdo = new PDO("mysql:host=$db_host;port=$db_port;dbname=$db_database", $db_user, $db_pass, array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8'));
if($pdo === false)
	die('BAD_CONNECTION');
}

?>