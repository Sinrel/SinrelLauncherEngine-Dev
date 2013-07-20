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
		if( version == null ) return;
		this.version = version;
	}
	
	void setVersionCode( int versionCode ) {
		if( versionCode < 0 ) versionCode = Math.abs( versionCode );
		this.versionCode = versionCode;
	}
	
	public LauncherData( int versionCode, String version ) {
		if( version == null ) throw new NullPointerException();
		if( versionCode < 0 ) throw new IllegalArgumentException( "Нельзя использовать код версии меньше нуля" );
		setVersion( version );
		setVersionCode( versionCode );
	}
	
}
