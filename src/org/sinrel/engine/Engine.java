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
	
	private boolean debug = false;
	
	public Engine( EngineSettings settings ) {
		try {
			intent = new Intent(this);
			setSettings(settings);
			config = new DefaultConfig(this);
			downloader = new DefaultDownloader();
			checker = new DefaultChecker();
			auth = new DefaultAuthBehavior();
			starter = new MinecraftAppletStarter();
		}catch( Exception e ) {
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
		if( settings == null ) throw new NullPointerException();
		this.settings = settings;
	}
	
	public void setDownloader(Downloader downloader) {
		if( downloader == null ) throw new NullPointerException();
		this.downloader = downloader;
	}
	
	public void setChecker( Checker checker ) {
		if( checker == null ) throw new NullPointerException();
		this.checker = checker;
	}
	
	public void setAuth(AuthBehavior auth) {
		if( auth == null ) throw new NullPointerException();
		this.auth = auth;
	}
			
	public void setConfig( Config config ) {
		if( config == null ) throw new NullPointerException();
		this.config = config;
	}
	
	public void setMinecraftStarter( MinecraftStarter starter ){
		if( starter == null ) throw new NullPointerException();
		this.starter = starter;
	}
	
	public void useDebug( boolean debug ) {
		this.debug = debug;
	}
	
	public boolean isDebug(){
		return debug;
	}
	
}
