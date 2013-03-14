package org.sinrel.engine.actions;

import java.util.ArrayList;

import org.sinrel.engine.Engine;
import org.sinrel.engine.listeners.CheckerListener;
import org.sinrel.engine.listeners.DownloadListener;

public abstract class ClientChecker {
	
	private ArrayList<CheckerListener> listeners = new ArrayList<CheckerListener>();
	private Engine engine;
	
	public ClientChecker(Engine e) {
		this.engine = e;
	}
	
	protected Engine getEngine(){
		return engine;
	}
	
	public void AddCheckerListener(CheckerListener listener){
		listeners.add(listener);
	}
	
	public void removeCheckerListener(CheckerListener listener){
		listeners.remove(listener);
	}
	
	protected void onFinishChecking(){
		for(CheckerListener cl : listeners){
			cl.onFinishChecking();
		}
	}
	
	protected void onStartChecking(){
		for(CheckerListener cl : listeners){
			cl.onStartChecking();
		}
	}
	
	public abstract ClientStatus checkClient(String applicationName);
}
