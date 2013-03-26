package org.sinrel.engine.actions;

import org.sinrel.engine.Engine;

public interface AuthBehavior {

	/**
	 * @param engine Экземпляр класса {@link Engine}
	 * @param login Логин
	 * @param pass Пароль
	 * @return Возвращает результат авторизации с типом {@link AuthData} 
	 */
	public AuthData auth(Engine engine, String login, String pass);
	
}