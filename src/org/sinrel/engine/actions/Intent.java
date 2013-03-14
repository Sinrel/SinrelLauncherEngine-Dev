package org.sinrel.engine.actions;

import org.sinrel.engine.Engine;
import org.sinrel.engine.listeners.DownloadAdapter;
import org.sinrel.engine.listeners.DownloadCompleteListener;
import org.sinrel.engine.listeners.DownloadListener;

public class Intent {
	
	private Engine engine;
	
	public Intent(Engine engine) {
		this.engine = engine;
	}

	/**
	 * Старт скачивания клиента "standart"
	 * @param loadZip Загружать ли client.zip
	 * @param dir Имя папки в которой находится клиент ( пример: minecraft )
	 * @return Возвращает одно из значений DownloadResult
	 */
	public DownloadResult downloadClient( String dir, boolean loadZip ) {
		return downloadClient(dir, loadZip, null);
	}
	
	/**
	 * Старт скачивания клиента "standart"
	 * @param loadZip Загружать ли client.zip
	 * @param dir Имя папки в которой находится клиент ( пример: minecraft )
	 * @param listener реализация интерфейса DownloadListener, необходима для отслеживания прогресса 
	 * @return Возвращает одно из значений DownloadResult
	 */
	public DownloadResult downloadClient( String dir, boolean loadZip, DownloadListener listener){
		if(listener != null)
			engine.getDownloader().AddDownloadListener(listener);
		return engine.getDownloader().downloadClient(engine, dir, loadZip);
		
	}
	
	/**
	 * Старт асинхронного скачивания клиента "standart"
	 * @param loadZip Загружать ли client.zip
	 * @param dir Имя папки в которой находится клиент ( пример: minecraft )
	 * @param listener класс реализующий интерфейс DownloadCompleteListner, нужно для оповещение об окончании загрузки
	 */
	public void downloadClientAsync(final String dir, final boolean loadZip, final DownloadCompleteListener listener){
		downloadClientAsync(dir, loadZip, listener, null);
	}
	
	/**
	 * Старт асинхронного скачивания клиента "standart"
	 * @param loadZip Загружать ли client.zip
	 * @param dir Имя папки в которой находится клиент ( пример: minecraft )
	 * @param listener класс реализующий интерфейс DownloadCompleteListner, нужно для оповещение об окончании загрузки
	 * @param dl реализация интерфейса DownloadListener, необходима для отслеживания прогресса 
	 */
	public void downloadClientAsync(final String dir, final boolean loadZip, final DownloadCompleteListener listener, final DownloadListener dl){
		Thread thread = new Thread(new Runnable() {
			public void run() {
				DownloadResult result = downloadClient(dir, loadZip, dl);
				listener.onDownloadComplete(result);
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * авторизации пользователя
	 * @param login логин
	 * @param pass пароль
	 * @return обьект AuthData с содержимом и сессии логине и результате авторизации
	 */
	public AuthData auth( String login , String pass ) {
		return engine.getAuth().auth(engine, login, pass);
	}
		
	/**
	 * проверка клиента
	 * @param applicationName имя приложения
	 * @return статус клиента
	 */
	public ClientStatus checkClient( String applicationName ) {
		return engine.getChecker().checkClient(engine, applicationName);
	}
	
}
