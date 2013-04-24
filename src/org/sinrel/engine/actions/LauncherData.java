package org.sinrel.engine.actions;

public class LauncherData {
	
	private int versionCode;
	private String version;
	
	public String getVersion() {
		return version;
	}
	
	public int getVersionCode() {
		return versionCode;
	}
	
	void setVersion( String version ) {
		this.version = version;
	}
	
	void setVersionCode( int versionCode ) {
		this.versionCode = versionCode;
	}
	
	public LauncherData( int versionCode, String version ) {
		setVersion( version );
		setVersionCode( versionCode );
	}
	
}
