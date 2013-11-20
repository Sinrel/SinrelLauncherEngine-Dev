package org.sinrel.engine.actions;

import org.sinrel.engine.Engine;

public class LauncherData {
	
	private int versionCode;
	private String version;
	
	private Engine engine;
	
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
	
	/**
	 * @return Возвращает результат проверки на устаревшесть лаунчера.<br>
	 * Если true, то значит, что текущий лаунчер устарел.<br><br>
	 * <b>Проверяется только код версии!</b><br>True-ответ, означающий устаревшесть, возможет только в случае более большего кода версии на сервере.
	 */
	public boolean isOutdated() {
		if( getVersionCode() > engine.getSettings().getVersionCode() ) return true;
		
		return false;
	}
	
	public LauncherData( Engine engine, int versionCode, String version ) {
		if( version == null ) throw new NullPointerException();
		if( engine == null ) throw new NullPointerException();
		if( versionCode < 0 ) throw new IllegalArgumentException( "Нельзя использовать код версии меньше нуля" );
		
		this.engine = engine;
		
		setVersion( version );
		setVersionCode( versionCode );
	}

}
