package org.sinrel.engine.actions;

import org.sinrel.engine.listeners.DownloadListener;

public final class ListenersManager {
	
	/**
	 * @param dl реализация интерфейса DownloadListener
	 * @since DownloadListener
	 */
	public static void addDownloadListener( DownloadListener dl ) {
		Download.listeners.add( dl );
	}
	
}
