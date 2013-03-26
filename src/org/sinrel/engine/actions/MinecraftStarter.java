package org.sinrel.engine.actions;

import java.awt.Frame;

public interface MinecraftStarter {
	
	/**
	 * @param dir Рабочая папка
	 * @param clientName Имя клиента
	 * @param login Логин
	 * @param session Сессия
	 * @param useAutoConnect Использовать ли автоподключение 
	 * @param server Адрес сервера
	 * @param port Порт сервера
	 * @param frame Окно в котором запускать клиент
	 */
	void startMinecraft(String dir, String clientName , String login, String session, boolean useAutoConnect, String server, String port, Frame frame);

}
