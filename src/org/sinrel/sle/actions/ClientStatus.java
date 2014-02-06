package org.sinrel.sle.actions;

/**
 * <b>WRONG_CLIENT</b> - Клиент пользователя повреждён<br>
 * <b>CLIENT_DOES_NOT_MATCH</b> - Клиент не совпадает с оригиналом на сервере<br>
 * <b>CLIENT_NOT_EXIST</b> - Клиент отсуствует<br>
 * <b>CLIENT_NOT_EXIST_ON_SERVER</b> - Клиент отсуствует на сервере<br>
 * <b>BAD_CONNECTION</b> - Отсуствует соединение с проверочным сервером<br>
 * <b>OK</b> - Проверка прошла успешно<br>
*/
public enum ClientStatus {
	WRONG_CLIENT,
	CLIENT_DOES_NOT_MATCH,
	CLIENT_NOT_EXIST,
	CLIENT_NOT_EXIST_ON_SERVER,
	BAD_CONNECTION,
	OK
}
