package org.sinrel.engine.actions;

import java.awt.Frame;

public interface MinecraftStarter {
	
	/**
	 * @param dir Рабочая папка
	 * @param clientName Имя клиента
	 * @param login Логин
	 * @param session Сессия 
	 * @param server Адрес сервера для автоподключения, если server != null
	 * @param port Порт сервера для автоподключения
	 * @param frame Окно в котором запускать клиент
	 */
	void startMinecraft(String dir, String clientName, AuthData authData, String server, String port, Frame frame);

}
