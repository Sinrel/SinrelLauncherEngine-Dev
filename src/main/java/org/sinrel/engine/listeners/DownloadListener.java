package org.sinrel.engine.listeners;

public interface DownloadListener {
	
	/**
	 * При старте загрузки
	 */
	public void onStartDownload();
	
	/**
	 * При смене загружаемого файла
	 */
	public void onFileChange( String now, String next  );
	
	/**
	 * При подсчёте загруженного
	 */
	public void onPercentChange( long total , int count );
	
}
