package org.sinrel.engine;

public class EngineSettings {
	
	protected String domain;
	protected String version;
	protected String folder;
	
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
	
	public EngineSettings() {
		this("", "", "");
	}
	
	public EngineSettings(String domain){
		this(domain, "", "");
	}
	
	public EngineSettings(String domain, String folder){
		this(domain, folder, "");
	}
	
	public EngineSettings(String domain, String folder, String version){
		setDomain(domain);
		setFolder(folder);
		setVersion(version);
	}
}
