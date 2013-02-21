package org.sinrel.engine.actions;

import java.util.ArrayList;

import org.sinrel.engine.listeners.DownloadListener;

final class Download implements Runnable {
	
	static ArrayList<DownloadListener> listeners = new ArrayList<DownloadListener>();
	
	static Thread t;
	
	static void init() {
		t = new Thread( new Download() , "Download" );
	}
	
	public void run() {
		startDownloadOnListeners();
	}
	
	private void startDownloadOnListeners() {
		for( DownloadListener dl : listeners ) {
			dl.onStartDownload();
		}
		
		//TODO
	}
	
}
