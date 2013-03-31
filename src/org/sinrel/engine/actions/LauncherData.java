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
	
	public void setVersion( String version ) {
		this.version = version;
	}
	
	public void setVersionCode( int versionCode ) {
		this.versionCode = versionCode;
	}
	
	public LauncherData( int versionCode, String version ) {
		setVersion( version );
		setVersionCode( versionCode );
	}
	
}
