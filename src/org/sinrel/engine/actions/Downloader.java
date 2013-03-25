package org.sinrel.engine.actions;

import java.util.ArrayList;

import org.sinrel.engine.Engine;
import org.sinrel.engine.listeners.DownloadListener;

public abstract class Downloader {
		
	private ArrayList<DownloadListener> listeners = new ArrayList<DownloadListener>();
	
	public void addDownloadListener(DownloadListener listener){
		listeners.add(listener);
	}
	
	public void removeDownloadListener(DownloadListener listener){
		listeners.remove(listener);
	}
	
	protected void onStartDownload(){
		for(DownloadListener dl : listeners)
			dl.onStartDownload();
	}
	
	protected void onFileChange(String now, String next){
		for(DownloadListener dl : listeners)
			dl.onFileChange(now, next);
	}
	
	protected void onPercentChange(long total, int count){
		for(DownloadListener dl : listeners)
			dl.onPercentChange(total, count);
	}

	public abstract DownloadResult downloadClient( Engine e , String clientName );
}
