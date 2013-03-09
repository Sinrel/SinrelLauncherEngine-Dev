<?php

	if(!defined('INCLUDE_CHECK')) die("У вас нет прав на выполнение данного файла!");
	
	/* Метод шифрования
		joomla        - интеграция с Joomla
		xenforo	 	  - интеграция с XenForo
		wordpress     - интеграция с WordPress
		dle 		  - интеграция с DLE
	*/
	$crypt = 'wordpress';
	
	// Настройка подключения к базе данных
	$db_host			= '***********'; // Ip-адрес MySQL
	$db_port			= '3306'; // Порт базы данных
	$db_user			= '***********'; // Пользователь базы данных
	$db_pass			= '***********'; // Пароль базы данных
	$db_database		= '***********'; //База данных

	// Настройка структуры базы данных
	$db_table       	= 'wp_users'; //Таблица с пользователями
	$db_columnId  		= 'id'; //Колонка с ID пользователей
	$db_columnUser  	= 'user_login'; //Колонка с именами пользователей
	$db_columnPass  	= 'user_pass'; //Колонка с паролями пользователей
	
	//DANGER ZONE
	$db_tableOther 		= 'xf_user_authenticate'; //Дополнительная таблица для XenForo
	$db_columnSesId	 	= 'session'; //Колонка с сессиями пользователей
	$db_columnServer	= 'server'; //Колонка с серверами пользователей
	
	$link = @mysql_connect($db_host.':'.$db_port,$db_user,$db_pass) or die( "BAD_CONNECTION" );

	mysql_select_db($db_database,$link);
	mysql_query("SET names UTF8");