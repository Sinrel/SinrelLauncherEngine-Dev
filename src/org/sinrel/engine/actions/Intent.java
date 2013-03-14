package org.sinrel.engine.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.sinrel.engine.Engine;
import org.sinrel.engine.library.cryption.Base64;
import org.sinrel.engine.listeners.DownloadCompleteListener;

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
		return engine.getDownloader().downloadClient(dir, loadZip);
	}
	
	/**
	 * Старт асинхронного скачивания клиента "standart"
	 * @param loadZip Загружать ли client.zip
	 * @param dir Имя папки в которой находится клиент ( пример: minecraft )
	 * @param listener класс реализующий интерфейс DownloadCompleteListner, нужно для оповещение об окончании загрузки
	 */
	public void downloadClientAsync(final String dir, final boolean loadZip, final DownloadCompleteListener listener){
		Thread thread = new Thread(new Runnable() {
			public void run() {
				DownloadResult result = downloadClient(dir, loadZip);
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
		return engine.getChecker().checkClient(applicationName);
	}
	
}
