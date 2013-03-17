package org.sinrel.engine;

import org.sinrel.engine.actions.AuthBehavior;
import org.sinrel.engine.actions.ClientChecker;
import org.sinrel.engine.actions.DefaultAuthBehavior;
import org.sinrel.engine.actions.DefaultChecker;
import org.sinrel.engine.actions.DefaultDownloader;
import org.sinrel.engine.actions.Downloader;
import org.sinrel.engine.actions.Intent;
import org.sinrel.engine.actions.MinecraftStarter;
import org.sinrel.engine.exception.FatalError;

public class Engine {
	
	/** Обьект с содержимым launcher.properties */
	private EngineSettings settings;
	
	private Intent intent;
	
	private Downloader downloader;	
	private ClientChecker checker;
	private AuthBehavior auth;
	private MinecraftStarter starter; 
	
	public Engine(EngineSettings settings) {
		try {
			intent = new Intent(this);
			setSettings(settings);
			downloader = new DefaultDownloader();
			checker = new DefaultChecker();
			auth = new DefaultAuthBehavior();
		}catch(Exception e) {
			e.printStackTrace();
			FatalError.showErrorWindow(e);
		}
	}	
	/**
	 * @return обьект с содержимым launcher.properties
	 */
	public EngineSettings getSettings() {
		return settings;
	}
	
	public Downloader getDownloader() {
		return downloader;
	}
	
	public ClientChecker getChecker() {
		return checker;
	}
	
	public AuthBehavior getAuth() {
		return auth;
	}
	
	public Intent getIntent(){
		return intent;
	}
	
	public MinecraftStarter getStarter(){
		return starter;
	}
	
	public void setSettings(EngineSettings settings){
		this.settings = settings;
	}
	
	public void setDownloader(Downloader downloader) {
		this.downloader = downloader;
	}
	
	public void setChecker(ClientChecker checker) {
		this.checker = checker;
	}
	
	public void setAuth(AuthBehavior auth) {
		this.auth = auth;
	}
	
	public void setMinecraftStarter(MinecraftStarter starter){
		this.starter = starter;
	}
	
	public boolean isDebug(){
		return true;
	}
	
}
