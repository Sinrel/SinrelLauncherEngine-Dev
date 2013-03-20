package org.sinrel.engine.actions;

import java.awt.Frame;
import java.io.File;

import javax.swing.JFrame;

import org.sinrel.engine.Engine;
import org.sinrel.engine.library.OSManager;
import org.sinrel.engine.listeners.DownloadCompleteListener;
import org.sinrel.engine.listeners.DownloadListener;

public class Intent {

	private Engine engine;

	public Intent(Engine engine) {
		this.engine = engine;
	}

	/**
	 * Старт скачивания клиента "standart"
	 * 
	 * @param loadZip
	 *            Загружать ли client.zip
	 * @param dir
	 *            Имя папки в которой находится клиент ( пример: minecraft )
	 * @return Возвращает одно из значений DownloadResult
	 */
	public DownloadResult downloadClient(String dir, boolean loadZip) {
		return downloadClient(dir, loadZip, null);
	}

	/**
	 * Старт скачивания клиента "standart"
	 * 
	 * @param loadZip
	 *            Загружать ли client.zip
	 * @param dir
	 *            Имя папки в которой находится клиент ( пример: minecraft )
	 * @param listener
	 *            реализация интерфейса DownloadListener, необходима для
	 *            отслеживания прогресса
	 * @return Возвращает одно из значений DownloadResult
	 */
	public DownloadResult downloadClient(String dir, boolean loadZip, DownloadListener listener) {
		if (listener != null)
			engine.getDownloader().addDownloadListener(listener);
		return engine.getDownloader().downloadClient(engine, dir, loadZip);

	}

	/**
	 * Старт асинхронного скачивания клиента "standart"
	 * 
	 * @param loadZip
	 *            Загружать ли client.zip
	 * @param dir
	 *            Имя папки в которой находится клиент ( пример: minecraft )
	 * @param listener
	 *            класс реализующий интерфейс DownloadCompleteListner, нужно для
	 *            оповещение об окончании загрузки
	 */
	public void downloadClientAsync(final String dir, final boolean loadZip, final DownloadCompleteListener listener) {
		downloadClientAsync(dir, loadZip, listener, null);
	}

	/**
	 * Старт асинхронного скачивания клиента "standart"
	 * 
	 * @param loadZip
	 *            Загружать ли client.zip
	 * @param dir
	 *            Имя папки в которой находится клиент ( пример: minecraft )
	 * @param listener
	 *            класс реализующий интерфейс DownloadCompleteListner, нужно для
	 *            оповещение об окончании загрузки
	 * @param dl
	 *            реализация интерфейса DownloadListener, необходима для
	 *            отслеживания прогресса
	 */
	public void downloadClientAsync(final String dir, final boolean loadZip, final DownloadCompleteListener listener, final DownloadListener dl) {
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
	 * 
	 * @param login
	 *            логин
	 * @param pass
	 *            пароль
	 * @return обьект AuthData с содержимом и сессии логине и результате
	 *         авторизации
	 */
	public AuthData auth(String login, String pass) {
		return engine.getAuth().auth(engine, login, pass);
	}

	/**
	 * Проверка клиента
	 * 
	 * @param applicationName
	 *            имя приложения
	 * @return статус клиента
	 */
	public ClientStatus checkClient(String applicationName) {
		return engine.getChecker().checkClient(engine, applicationName);
	}

	/**
	 * Проверка клиента
	 * 
	 * @param applicationName
	 *            имя приложения
	 * @param clientName
	 *            имя клиента
	 * @return статус клиента
	 */
	public ClientStatus checkClient(String applicationName, String clientName) {
		return engine.getChecker().checkClient(engine, applicationName, clientName);
	}

	/**
	 * запуск minecraft во фрейме
	 * 
	 * @param serverName
	 *            Название клиента
	 * @param login
	 *            Логин
	 * @param session
	 *            Сессия пользователя
	 * @param frame
	 *            Фрейм для запуска
	 */
	public void startMinecraft(String serverName, String login, String session, JFrame frame) {
		startMinecraft(serverName, login, session, frame, null, 0);
	}

	/**
	 * запуск minecraft в фрейме
	 * 
	 * @param serverName
	 *            Название клиента
	 * @param login
	 *            Логин
	 * @param session
	 *            сессия пользователя
	 * @param frame
	 *            фрейм для запуска
	 * @param server
	 *            сервер для автозахода
	 * @param port
	 *            порт для автозахода
	 */
	public void startMinecraft(String serverName, String login, String session, JFrame frame, String server, int port) {
		File clientFolder = OSManager.getClientFolder(engine.getSettings().getWorkDir(), serverName);
		engine.getStarter().startMinecraft(clientFolder, login, session, server, port, frame);
	}

	/**
	 * запуск minecraft в фрейме
	 * 
	 * @param login
	 *            Логин
	 * @param frame
	 *            фрейм для запуска
	 * @param session
	 *            сессия пользователя
	 * @param server
	 *            сервер для автозапуска
	 * @param port
	 *            порт для автозапуска
	 */
	public void startMinecraft(String login, String session, JFrame frame, String server, int port) {
		startMinecraft("", login, session, frame, server, port);
	}

	/**
	 * запуск minecraft в фрейме
	 * 
	 * @param login
	 *            Логин
	 * @param frame
	 *            фрейм для запуска
	 * @param session
	 *            сессия пользователя
	 */
	public void startMinecraft(String login, String session, JFrame frame) {
		startMinecraft(login, session, frame, null, 0);
	}

}
