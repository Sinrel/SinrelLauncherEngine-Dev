package org.sinrel.sle;

import java.io.File;

import org.apache.commons.lang3.Validate;

public class EngineSettings {
	
	/** Домен сервера */
	protected String domain = "example.com";
	/** Путь на сервере */
	protected String serverPath = "launcher";
	/** Рабочая папка */
	protected String directory = "minecraft";
	/** Версия лаунчера */
	protected String version;
	/** Код версии лаунчера */
	protected int versionCode;
	/** Путь к рабочей папке */
	protected File path = new File( System.getProperty("user.home", ".") );
	/** Ключ шифрования **/
	protected String key = null;
	
	public String getKey() {
		return key;
	}
	
	public String getDomain() {
		return domain;
	}
	
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion( String version ) {
		this.version = version;
	}
	
	public String getServerPath() {
		return serverPath;
	}
	
	public void setServerPath( String serverPath ) {
		this.serverPath = serverPath;
	}
	
	public String getDirectory() {
		return directory;
	}
	
	public void setDirectory( String directory ) {
		this.directory = directory;
	}
	
	public void setVersionCode( int versionCode ) {
		this.versionCode = versionCode;
	}
	
	public int getVersionCode() {
		return versionCode;
	}
	
	public void setKey( String key ) {
		this.key = key;
	}
	
	public void setPath( File path ) {
		this.path = path;
	}
	
	public File getPath() {
		return path;
	}
	
	/**
	 * @param domain Адрес сервера (example.com)
	 * @param serverPath Путь на сервере (launcher)
	 * @param directory Рабочая папка (minecraft)
	 * @param version Версия лаунчера (1)
	 * @param versionCode Код версии
	 * 
	 * @exception NullPointerException Возбуждается, если одна из строк null
	 * @exception IllegalArqumentException Возбуждается, если код версии отрицателен
	 */
	public EngineSettings( String domain, String serverPath, String directory , String version, int versionCode ){
		Validate.notNull( domain );
		Validate.notNull( serverPath );
		Validate.notNull( directory );
		Validate.notNull( version );
		
        if ( versionCode < 0 ) throw new IllegalArgumentException("Отрицательный код версии недопустим");
        
		setDomain( domain );
		setServerPath( serverPath );
		setVersion( version );
		setDirectory( directory );
		setVersionCode( versionCode );
	}
	
	/**
	 * @param domain Адрес сервера (example.com)
	 * @param serverPath Путь на сервере (launcher)
	 * @param directory Рабочая папка (minecraft)
	 * @param version Версия лаунчера (1)
	 * @param versionCode Код версии
	 * @param path Путь к рабочей папке
	 * 
	 * @exception NullPointerException Возбуждается, если одна из строк null
	 * @exception IllegalArqumentException Возбуждается, если код версии отрицателен
	 */
	public EngineSettings( String domain, String serverPath, String directory , String version, int versionCode, File path ){
		this( domain, serverPath, directory , version, versionCode );
		
		setPath( path );
	}
	
}
