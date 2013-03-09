package org.sinrel.engine.actions;

import org.sinrel.engine.listeners.CheckerListener;
import org.sinrel.engine.listeners.DownloadListener;
import org.sinrel.engine.actions.Downloader;

public final class ListenersManager {
	
	/**
	 * @param dl реализация интерфейса DownloadListener
	 * @see DownloadListener
	 */
	public static void addDownloadListener( DownloadListener dl ) {
		Downloader.listeners.add( dl );
	}
	
	/**
	 * @param dl реализация интерфейса CheckerListener
	 * @see CheckerListener
	 */
	public static void addCheckerListener( CheckerListener cl ) {
		Checker.listeners.add( cl );
	}
		
}
