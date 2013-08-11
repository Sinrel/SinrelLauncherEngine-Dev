package org.sinrel.engine.actions;

import java.io.IOException;
import java.net.URI;

import org.sinrel.engine.Engine;

public abstract class Manager {
	
	protected Engine engine;
	
	public Manager( Engine engine ) {
		this.engine = engine;
	}
	
	/**
	 * @return Проверяет наличие запущенных копий лаунчера и возвращает boolean-ответ.
	 */
	public abstract boolean isDuplicated();
	
	/**
	 * Проверяет наличие соединения с интернетом. При имеющимся таковом
	 * возвращает true, в ином случае false
	 * 
	 * @return boolean
	 */
	public abstract boolean isOnline();
	
	/**
	 * @return Возвращает версию Java, на которой запущен движок.
	 */
	public abstract float getJavaVersion();
	
	/**
	 * Открывает браузером по-умолчанию принимаемою ссылку
	 * 
	 * @param uri обьект класса {@link URI} содержащий ссылку для перехода 
	 * @throws IOException если адрес из URI недоступен
	 */
	public abstract void openLink(URI uri) throws IOException;
	
	public abstract LauncherData getLauncherData();
	
}
