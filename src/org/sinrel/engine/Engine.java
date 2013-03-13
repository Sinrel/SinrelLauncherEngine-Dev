package org.sinrel.engine;

import org.sinrel.engine.actions.Intent;
import org.sinrel.engine.exception.FatalError;

public class Engine {
	
	/** Обьект с содержимым launcher.properties */
	private EngineSettings settings;
	
	private Intent intent;
	 
	public Engine(EngineSettings settings) {
		try {
			intent = new Intent(this);
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
	
	public void setSettings(EngineSettings settings){
		this.settings = settings;
	}
	
	public Intent getIntent(){
		return intent;
	}
	
	public boolean isDebug(){
		return true;
	}
	
}
