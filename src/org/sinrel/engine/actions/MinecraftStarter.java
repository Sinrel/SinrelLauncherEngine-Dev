package org.sinrel.engine.actions;

import java.awt.Frame;

@Deprecated
public interface MinecraftStarter {

	/**
	 * @param dir
	 * @param clientName Имя клиента
	 * @param authData
	 * @param server Адрес сервера для автоподключения, если server != null
	 * @param port Порт сервера для автоподключения
	 * @param frame Окно в котором запускать клиент
	 */
	public void startMinecraft( String clientName, AuthData authData, String server, String port, Frame frame );
	 	
	public void useFullScreen( boolean bool );
	
	public void setSize( int width, int height );
}
