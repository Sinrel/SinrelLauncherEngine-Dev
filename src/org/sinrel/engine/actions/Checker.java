package org.sinrel.engine.actions;

import org.sinrel.engine.Engine;

public abstract class Checker {
			
	public abstract ClientStatus checkClient( Engine engine, String clientName );
		
	public abstract LauncherData getLauncherData( Engine engine );
	
	public abstract DirectoryStatus checkMods( Engine engine, String clientName );
	
	public abstract DirectoryStatus checkDirectory( Engine engine, String clientName, String directoryName );
	
}
