package org.sinrel.engine.actions;

import org.sinrel.engine.Engine;

public abstract class Intent {
	
	public final static void Do( Action a ) {
		switch ( a ) {
			case ENABLE : 
				Engine.getLauncher().onEnable();
				break;
			case DISABLE :
				Engine.getLauncher().onDisable();
				break;
			case DOWNLOAD :
				Download.init();
				Download.t.start();
				break;
		}
	}
	
	public static final AuthAnswers DoAuth( String login , String pass ) {
		//DO AUTH
		
		return AuthAnswers.OK;
	}
	
}
