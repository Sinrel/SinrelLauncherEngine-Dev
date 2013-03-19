<?php

if(!defined('INCLUDE_CHECK')) die("У вас нет прав на выполнение данного файла!");

/* Метод шифрования
joomla        - интеграция с Joomla
xenforo	 	  - интеграция с XenForo
wordpress     - интеграция с WordPress
dle 		  - интеграция с DLE
*/
$integration = 'wordpress';

$cfg_maxRetries     = 5; // Количество неудачных попыток до блокировки пользователя
$cfg_loginCooldown  = '5 mins'; // Время блокировки пользователя после истечения попыток 

// Настройка подключения к базе данных
$db_host			= 'localhost'; // Ip-адрес MySQL
$db_port			= 3306; // Порт базы данных
$db_user			= 'root'; // Пользователь базы данных
$db_pass			= ''; // Пароль базы данных
$db_database		= 'wordpress'; //База данных

// Настройка структуры базы данных
$db_table       	= 'wp_users'; //Таблица с пользователями
$db_columnId  		= 'id'; //Колонка с ID пользователей
$db_columnUser  	= 'user_login'; //Колонка с именами пользователей
$db_columnPass  	= 'user_pass'; //Колонка с паролями пользователей

// Только если вы используете XenForo
$db_tableOther 		= 'xf_user_authenticate'; //Дополнительная таблица для XenForo



//DANGER ZONE
$db_columnSesId		 	= 'session'; //Колонка с сессиями пользователей
$db_columnServer		= 'server'; //Колонка с серверами пользователей
$db_columnBlockUntil	= 'blockUntil'; //Колонка с временем, по которое заблокирован пользователь
$db_columnRetriesLeft	= 'retriesLeft'; //Колонка с количеством оставшихся неудачных попыток до блокировки



try {
	// Подключаемся к БД
	$pdo = new PDO("mysql:host=$db_host;port=$db_port;dbname=$db_database", $db_user, $db_pass, array(PDO::MYSQL_ATTR_INIT_COMMAND => 'SET NAMES utf8'));
	// Кидать исключение при возникновении ошибки
	$pdo -> setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
	// TODO: Избавиться от die()
	die('BAD_CONNECTION');
}

?>