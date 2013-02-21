package org.sinrel.engine.listeners;

public interface DownloadListener {
	
	/**
	 * При старте загрузки
	 */
	public void onStartDownload();
	
	/**
	 * При смене загружаемого файла
	 */
	public void onFileChange();
	
	/**
	 * При смене статуса загрузки
	 */
	public void onStatusChange();
	
	/**
	 * При подсчёте загруженного
	 */
	public void onPercentChange();
	
}
