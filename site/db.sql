/*
	Данный скрипт вставляет в таблицу с именем table_name две колонки: session и server
	table_name необходимо заменить на нужную для вас таблицу.
	
	dle - dle_users (по умолчанию)
	xenforo - xf_user (по умолчанию)
	wordpress - wp_users (по умолчанию)
*/
ALTER TABLE table_name
ADD session varchar(255) DEFAULT NULL,
ADD server varchar(255) DEFAULT NULL
