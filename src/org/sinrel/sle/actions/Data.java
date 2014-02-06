package org.sinrel.sle.actions;

import org.sinrel.sle.Engine;

public abstract class Data {
	 
	/** Код версии */
	protected int versionCode;
	/** Версия */
	protected String version;
	
	/** Экземпляр движка */
	protected Engine engine;
	
	public String getVersion() {
		return version;
	}
	
	public int getVersionCode() {
		return versionCode;
	}
	
	/**
	 * @param version
	 */
	void setVersion( String version ) {
		if( version == null ) return;
		this.version = version;
	}
	
	/**
	 * @param versionCode
	 */
	void setVersionCode( int versionCode ) {
		if( versionCode < 0 ) versionCode = Math.abs( versionCode );
		this.versionCode = versionCode;
	}
	
	/**
	 * @return Возвращает результат проверки на устаревшесть<br>
	 * Означающий устаревшесть true-ответ, возможен только в случае более большего кода версии на сервере.<br>
	 * <b>Проверяется только код версии!</b>
	 */	
	public abstract boolean isOutdated();
	
	/**
	 * @param engine Экземпляр движка
	 * @param versionCode Код версии
	 * @param version Версия
	 */
	public Data( Engine engine, int versionCode, String version ) {
		if( version == null ) throw new NullPointerException();
		if( engine == null ) throw new NullPointerException();
		if( versionCode < 0 ) throw new IllegalArgumentException( "Нельзя использовать код версии меньше нуля" );
		
		this.engine = engine;
		
		setVersion( version );
		setVersionCode( versionCode );
	}

}
