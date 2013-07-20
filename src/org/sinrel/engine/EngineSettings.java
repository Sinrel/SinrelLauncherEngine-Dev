package org.sinrel.engine;

public class EngineSettings {
	
	/** Домен сервера */
	protected String domain;
	/** Путь на сервере */
	protected String serverPath;
	/** Рабочая папка */
	protected String directory;
	/** Версия лаунчера */
	protected String version;
	/** Код версии лаунчера */
	protected int versionCode;
	
	public String getDomain() {
		return domain;
	}
	
	public void setDomain(String domain) {
		this.domain = domain;
	}
	
	public String getVersion() {
		return version;
	}
	
	public void setVersion(String version) {
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
	
	/**
	 * @param domain Адрес сервера (example.com)
	 * @param serverPath Путь на сервере (launcher)
	 * @param directory Рабочая папка (minecraft)
	 * @param version Версия лаунчера (1)
	 * @param versionCode Код версии
	 * 
	 * @exception NullPointerException - Возбуждается, если одна из строк null
	 * @exception IllegalArqumentException - Возбуждается, если код версии отрицателен
	 */
	public EngineSettings(String domain, String serverPath, String directory , String version, int versionCode){
        if ( domain == null | serverPath == null | directory == null | version == null ) {
            throw new NullPointerException();
        }
        if ( versionCode < 0 ) throw new IllegalArgumentException("Отрицательный код версии недопустим");
        
		setDomain(domain);
		setServerPath(serverPath);
		setVersion(version);
		setDirectory(directory);
		setVersionCode( versionCode );
	}
}
