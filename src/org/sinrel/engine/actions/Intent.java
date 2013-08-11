package org.sinrel.engine.actions;

import javax.swing.JFrame;

import org.sinrel.engine.Engine;

/**
 * Данный класс содержит методы, которые позволяют подавать команды движку. <br>
 * Рекомендуется использовать только данный класс в целях поддержания совместимости с новыми версиями движка. 
 */
public class Intent {

	private Engine engine;

	public Intent(Engine engine) {
		this.engine = engine;
	}
	
	/** Запуск клиента Minecraft
	 * @param clientName Имя клиента
	 * @param authData Идентификационные данные
	 * @param frame Окно в котором запускать
	 */
	public void startMinecraft( String clientName, AuthData authData, JFrame frame) {
		engine.getStarter().startMinecraft( engine.getSettings().getDirectory(), clientName, authData, null, null, frame );
	}
	
	/**
	 * Запуск клиента Minecraft
	 * @param clientName Имя клиента
	 * @param authData Идентификационные данные
	 * @param server Адрес сервера
	 * @param port Порт сервера
	 * @param frame Окно в котором запускать
	 */
	public void startMinecraft( String clientName, AuthData authData, String server, String port, JFrame frame ) {
		engine.getStarter().startMinecraft( engine.getSettings().getDirectory() , clientName, authData, server, port, frame );
	}
	
}