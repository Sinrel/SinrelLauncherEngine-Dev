package org.sinrel.engine.actions;

import org.sinrel.engine.listeners.CheckerListener;
import org.sinrel.engine.listeners.DownloadListener;

import sun.rmi.transport.proxy.CGIHandler;

public final class ListenersManager {
	
	/**
	 * @param dl реализация интерфейса DownloadListener
	 * @see DownloadListener
	 */
	public static void addDownloadListener( DownloadListener dl ) {
		Downloader.listeners.add( dl );
	}
	
	public static void removeDownloadListener( DownloadListener dl){
		Downloader.listeners.remove(dl);
	}
	
	/**
	 * @param dl реализация интерфейса CheckerListener
	 * @see CheckerListener
	 */
	public static void addCheckerListener( CheckerListener cl ) {
		Checker.listeners.add( cl );
	}
		
	public static void removeCheckerListener( CheckerListener cl){
		Checker.listeners.remove(cl);
	}
}
