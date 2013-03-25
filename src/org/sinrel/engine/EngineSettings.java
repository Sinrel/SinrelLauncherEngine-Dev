package org.sinrel.engine;

public class EngineSettings {
	
	protected String domain; //адрес сервера
	protected String version;//версия лаунчера
	protected String folder;//путь на сервере
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
	
	public String getFolder() {
		return folder;
	}
	
	public void setFolder(String folder) {
		this.folder = folder;
	}
	
	public String getDirectory() {
		return directory;
	}
	
	public void setDirectory( String directory ) {
		this.directory = directory;
	}
	
	/**
	 * @param domain Адрес сервера (example.com)
	 * @param folder Путь на сервере (launcher)
	 * @param directory Рабочая папка (minecraft)
	 * @param version Версия лаунчера (1)
	 */
	public EngineSettings(String domain, String folder, String directory , String version){
		setDomain(domain);
		setFolder(folder);
		setVersion(version);
		setDirectory(directory);
	}
}
