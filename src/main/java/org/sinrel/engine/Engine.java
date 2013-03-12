package org.sinrel.engine;

import org.sinrel.engine.actions.Action;
import org.sinrel.engine.actions.Intent;
import org.sinrel.engine.exception.FatalError;
import org.sinrel.engine.exception.MainLauncherClassNotFoundException;
import org.sinrel.engine.launcher.JavaLauncher;
import org.sinrel.engine.launcher.LauncherDescriptionFile;

public final class Engine {

	/** Обьект с содержимым launcher.properties */
	private static LauncherDescriptionFile desc;

	/** Обьект основного класса лаунчера */
	private static JavaLauncher launcher;

	/**
	 * Точка входа при запуске
	 */
	public static void main(String args[]) {
		try {
			desc = new LauncherDescriptionFile();

			launcher = loadLauncher(desc.getMain());

			new Thread(new Runnable() {
				public void run() {
					Intent.Do(Action.ENABLE);
				}
			}).start();
		} catch (Exception e) {
			e.printStackTrace();
			new FatalError(e.getClass(), e.getStackTrace(), e.getMessage());
		}

	}

	/**
	 * @param pathToMain
	 *            строка содержащая путь к главному классу
	 * @return возвращает обьект главного класса лаунчера
	 * @throw MainLauncherClassNotFoundException вызывается при отсуствии
	 *        главного класса лаунчера
	 */
	private static JavaLauncher loadLauncher(String pathToMain) throws MainLauncherClassNotFoundException {
		Class<?> cl = null;
		JavaLauncher jl = null;
		try {
			cl = Class.forName(pathToMain);

			jl = (JavaLauncher) cl.newInstance();

		} catch (Exception e) {
			throw new MainLauncherClassNotFoundException(pathToMain);
		}

		return jl;
	}

	/**
	 * @return обьект с содержимым launcher.properties
	 */
	public static LauncherDescriptionFile getDescriptionFile() {
		return desc;
	}

	/**
	 * @return обьект с доступом к основному классу лаунчера
	 */
	public static JavaLauncher getLauncher() {
		return launcher;
	}

}
