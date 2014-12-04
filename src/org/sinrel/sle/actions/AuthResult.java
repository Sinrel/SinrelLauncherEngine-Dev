package org.sinrel.sle.actions;

/**
 * <b>BAD_LOGIN_OR_PASSWORD</b> - Неправильный логин или пароль<br>
 * <b>BAD_CONNECTION</b>
 * <ul>
 * 	<li>Не удалось произвести авторизацию</li>
 * 	<li>Не удалось подключиться к серверу</li>
 * </ul>
 * <b>INTERNAL_ERROR</b> - Произошла необработанная ошибка на сервере.
 * <b>OK</b> - Авторизация прошла успешно 
 */
public enum AuthResult {
	BAD_LOGIN_OR_PASSWORD,
	BAD_CONNECTION,
	INTERNAL_ERROR,
	OK
}