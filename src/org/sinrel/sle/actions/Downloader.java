package org.sinrel.sle.actions;

import java.util.ArrayList;

import org.sinrel.sle.Engine;
import org.sinrel.sle.library.DownloadListener;

/**
 * 
 * @author Sinrel Group
 * @version 3.0
 * @since SLE 2.0
 * 
 */
public abstract class Downloader {
	
	public abstract void resume();
	public abstract void restart();
	public abstract void stop();
	
	//old code below
	
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
