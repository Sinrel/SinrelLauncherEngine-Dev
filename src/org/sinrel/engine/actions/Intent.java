package org.sinrel.engine.actions;

import java.awt.Frame;

import org.sinrel.engine.Engine;

public class Intent {

	private Engine engine;

	public Intent(Engine engine) {
		this.engine = engine;
	}

	public DownloadResult downloadClient( String clientName ) {
		return engine.getDownloader().downloadClient( engine, clientName );
	}
	
	/**
	 * Авторизация пользователя
	 * 
	 * @param login Логин
	 * @param pass Пароль
	 * @return обьект AuthData с содержимым сессии, логина и результате авторизации
	 */
	public AuthData auth( String login, String pass ) {
		return engine.getAuth().auth( engine, login, pass );
	}
	
	/**
	 * Проверка клиента
	 * 
	 * @param clientName Имя клиента
	 * @return Статус клиента
	 */
	public ClientStatus checkClient( String clientName ) {
		return engine.getChecker().checkClient( engine, clientName );
	}

	public void startMinecraft( String clientName, String login, Frame frame ) {
		engine.getStarter().startMinecraft( engine.getSettings().getDirectory(), clientName, login, "", false, null, null, frame );
	}
	
	public void startMinecraft( String clientName, String login, String session, Frame frame) {
		engine.getStarter().startMinecraft( engine.getSettings().getDirectory(), clientName, login, session, false, null, null, frame );
	}
	
	public void startMinecraft( String clientName, String login, String session, String server, String port, Frame frame ) {
		engine.getStarter().startMinecraft( engine.getSettings().getDirectory() , clientName, login, session, true , server, port, frame );
	}
	
}