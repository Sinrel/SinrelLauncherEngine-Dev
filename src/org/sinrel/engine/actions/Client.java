package org.sinrel.engine.actions;

public enum Client {
	WRONG_CLIENT, //клиент пользователя повреждён
	CLIENT_DOES_NOT_MATCH, //клиент не совпадает с оригиналом на сервере
	CLIENT_NOT_EXIST, //клиент отсуствует
	CLIENT_NOT_EXIST_ON_SERVER, //клиент отсуствует на сервере
	BAD_CONNECTION, //отсуствует соединение с проверочным сервером
	OK //проверка прошла успешно
}
