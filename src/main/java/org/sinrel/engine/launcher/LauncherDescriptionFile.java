package org.sinrel.engine.launcher;

import java.io.IOException;
import java.util.Properties;

import org.sinrel.engine.exception.InvalidLauncherDescriptionFile;
import org.sinrel.engine.exception.LauncherDescriptionNotFoundException;

public final class LauncherDescriptionFile {

	/** Заголовок приложения */
	private String title;

	/** Главный класс лаунчера */
	private String main;

	/** Версия приложения */
	private String version;

	private String domain;

	private String folder;

	/** Код версии для проверки обновлений */
	private int code;

	private static Properties prop = new Properties();

	public LauncherDescriptionFile() throws LauncherDescriptionNotFoundException, InvalidLauncherDescriptionFile {
		try {

			prop.load(getClass().getResourceAsStream("/launcher.properties"));

			String[] required = { "title", "main", "code", "version", "domain", "folder" }; // обязательные
																							// пункты

			for (String s : required) {
				if (prop.getProperty(s) == null) {
					throw new InvalidLauncherDescriptionFile("пункт " + s + " отсутствует в launcher.properties");
				}
			}

			title = get("title");
			main = get("main");
			version = get("version");
			code = Integer.parseInt(get("code"));
			domain = get("domain");
			folder = get("folder");

		} catch (IOException e) {
			throw new LauncherDescriptionNotFoundException();
		} catch (InvalidLauncherDescriptionFile e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			throw new LauncherDescriptionNotFoundException();
		}
	}

	public String get(String key) {
		return prop.getProperty(key);
	}

	/**
	 * @return главный класс лаунчера из файла launcher.properties
	 */
	public String getMain() {
		return main;
	}

	/**
	 * @return заголовок из файла launcher.properties
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @return версию из файла launcher.properties
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @return код версии из файла launcher.properties
	 */
	public int getCode() {
		return code;
	}

	public String getDomain() {
		return domain;
	}

	public String getFolder() {
		return folder;
	}
}
