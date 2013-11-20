package org.sinrel.engine.actions;

import java.util.ArrayList;

import org.sinrel.engine.Engine;
import org.sinrel.engine.listeners.DownloadListener;

public abstract class Downloader {
	
 	private ArrayList<DownloadListener> listeners = new ArrayList<DownloadListener>();
	
	protected ArrayList< String > additionalFiles = new ArrayList< String >(),
								  additionalArchives = new ArrayList< String >();
	
	protected Engine engine;
	
	public Downloader( Engine engine ) {
		this.engine = engine;
	}
		
	public void addDownloadListener( DownloadListener listener ){
		listeners.add( listener );
	}
	
	public void removeDownloadListener( DownloadListener listener ){
		listeners.remove( listener );
	}
	
	public void addAdditionalFile( String filename ) {
		additionalFiles.add( filename );
	}
	
	public void removeAdditionalFile( String filename ) {
		additionalFiles.remove( filename );
	}
	
	public void addAdditionalArchive( String filename ) {
		additionalArchives.add( filename );
	}
	
	public void removeAdditionalArchive( String filename ) {
		additionalArchives.remove( filename );
	}
	
	protected void onStartDownload( DownloadEvent e ){
		for( DownloadListener dl : listeners )
			dl.onStartDownload(e);
	}
	
	protected void onFileChange( DownloadEvent e ){
		for( DownloadListener dl : listeners )
			dl.onFileChange(e);
	}
	
	protected void onPercentChange( DownloadEvent e ){
		for( DownloadListener dl : listeners )
			dl.onPercentChange(e);
	}
	
	/**
	 * Загрузка клиента с сервера
	 * 
	 * @param clientName Имя клиента
	 * @return Возвращает результат загрузки типа {@link DownloadResult}
	 */
	public abstract DownloadResult downloadClient( String clientName );
	
}
