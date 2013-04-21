package org.sinrel.engine;

import org.sinrel.engine.actions.AuthBehavior;
import org.sinrel.engine.actions.Checker;
import org.sinrel.engine.actions.Config;
import org.sinrel.engine.actions.DefaultAuthBehavior;
import org.sinrel.engine.actions.DefaultChecker;
import org.sinrel.engine.actions.DefaultConfig;
import org.sinrel.engine.actions.DefaultDownloader;
import org.sinrel.engine.actions.Downloader;
import org.sinrel.engine.actions.Intent;
import org.sinrel.engine.actions.MinecraftAppletStarter;
import org.sinrel.engine.actions.MinecraftStarter;
import org.sinrel.engine.exception.FatalError;

public class Engine {
	
	private EngineSettings settings;
	
	private Intent intent;
	private Config config;
	
	private Downloader downloader;
	private Checker checker;
	private AuthBehavior auth;
	private MinecraftStarter starter;
	
	public Engine( EngineSettings settings ) {
		try {
			intent = new Intent(this);
			setSettings(settings);
			config = new DefaultConfig(this);
			downloader = new DefaultDownloader();
			checker = new DefaultChecker();
			auth = new DefaultAuthBehavior();
			starter = new MinecraftAppletStarter();
		}catch(Exception e) {
			e.printStackTrace();
			FatalError.showErrorWindow(e);
		}
	}	
	
	public EngineSettings getSettings() {
		return settings;
	}
	
	public Downloader getDownloader() {
		return downloader;
	}
	
	public Checker getChecker() {
		return checker;
	}
	
	public AuthBehavior getAuth() {
		return auth;
	}
		
	public Intent getIntent(){
		return intent;
	}
	
	public Config getConfig() {
		return config;
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
	
	public void setChecker( Checker checker ) {
		this.checker = checker;
	}
	
	public void setAuth(AuthBehavior auth) {
		this.auth = auth;
	}
			
	public void setConfig( Config config ) {
		this.config = config;
	}
	
	public void setMinecraftStarter( MinecraftStarter starter ){
		this.starter = starter;
	}
	
	public boolean isDebug(){
		return false;
	}
	
}
