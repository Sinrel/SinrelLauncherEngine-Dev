package org.sinrel.engine.actions;

public abstract class AuthBehavior {
	
	/**
	 * Авторизация пользователя
	 * 
	 * @param login Логин
	 * @param pass Пароль
	 * @return Возвращает результат авторизации типа {@link AuthData}.<br>
	 * При удачной авторизации обьект будет содержать сессию.
	 */
	public abstract AuthData auth( String login, String pass );
	
}