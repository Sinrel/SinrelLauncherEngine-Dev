package org.sinrel.engine.actions;

import java.awt.Frame;

public interface MinecraftStarter {

	/**
	 * @param dir
	 * @param clientName Имя клиента
	 * @param authData
	 * @param server Адрес сервера для автоподключения, если server != null
	 * @param port Порт сервера для автоподключения
	 * @param frame Окно в котором запускать клиент
	 */
	public void startMinecraft( String dir, String clientName, AuthData authData, String server, String port, Frame frame );
	
	/**
	 * Принимает boolean-значение и устанавливает его.<br>
	 * Если true, то будет использоваться переход в полно-экранный режим
 	 * @param bool - boolean значение
	 */
	public void useFullScreen( boolean bool );

	/**
	 * Включает или выключает консольный вывод запускаемого клиента
	 * 
	 * @param bool
	 */
	public void useOutput( boolean output );
	
 	public boolean isFullScreen();
 
 	public boolean isOutput();
 	
}
