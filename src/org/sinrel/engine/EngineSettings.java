package org.sinrel.engine;

public class EngineSettings {
	
	protected String domain; //адрес сервера
	protected String serverPath;//путь на сервере
	protected String directory;//рабочая папка
	protected String version;//версия лаунчера
	protected int versionCode; //код версии лаунчера
	
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
	 */
	public EngineSettings(String domain, String serverPath, String directory , String version, int versionCode){
		setDomain(domain);
		setServerPath(serverPath);
		setVersion(version);
		setDirectory(directory);
		setVersionCode( versionCode );
	}
}
