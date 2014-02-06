package org.sinrel.sle.listeners;

import org.sinrel.sle.actions.DownloadEvent;

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