package org.sinrel.engine.listeners;

import org.sinrel.engine.actions.DownloadEvent;

public interface DownloadListener {
	
	/**
	 * При старте загрузки
	 */
	public void onStartDownload( DownloadEvent e );
	
	/**
	 * Вызывается при смене загружаемого файла
	 * @param e - {@link DownloadEvent}
	 */
	public void onFileChange( DownloadEvent e );
	
	/**
	 * При подсчёте загруженного
	 */
	public void onPercentChange( DownloadEvent e );
		
}
