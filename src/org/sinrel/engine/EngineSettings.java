package org.sinrel.engine;

public class EngineSettings {
	
	protected String domain; //адрес сервера
	protected String version;//версия лаунчера
	protected String serverPath;//путь на сервере
	protected String directory;//рабочая папка
	
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
	
	/**
	 * @param domain Адрес сервера (example.com)
	 * @param serverPath Путь на сервере (launcher)
	 * @param directory Рабочая папка (minecraft)
	 * @param version Версия лаунчера (1)
	 */
	public EngineSettings(String domain, String serverPath, String directory , String version){
		setDomain(domain);
		setServerPath(serverPath);
		setVersion(version);
		setDirectory(directory);
	}
}
