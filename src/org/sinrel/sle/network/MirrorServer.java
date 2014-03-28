package org.sinrel.sle.network;

import java.net.MalformedURLException;
import java.net.URL;

public class MirrorServer {
	
	private String domain;
	private String serverPath;
	
	public MirrorServer( String domain, String serverPath ) {
		this.domain = domain;
		this.serverPath = serverPath;
	}
	
	public String getDomain() {
		return domain;
	}
	
	public String getServerPath() {
		return serverPath;
	}
	
	public void setDomain( String domain ) {
		this.domain = domain;
	}
	
	public void setServerPath( String serverPath ) {
		this.serverPath = serverPath;
	}
	
	public String getServerLink( ) throws MalformedURLException {
		URL url;

		if (!this.getServerPath().equalsIgnoreCase("")) {
			url = new URL("http://" + this.getDomain() + "/" + this.getServerPath() + "/");
		} else {
			url = new URL("http://" + this.getDomain() + "/");
		}
		
		return url.toString();
	}
	

	public URL getClientBinaryLink( String clientName ) throws MalformedURLException {
		URL url = new URL( this.getServerLink().concat( "clients/".concat( clientName.concat( "/bin/" ) ) ) );
		return url;
	}
	
	public String getEngineLink( ) throws MalformedURLException {
		URL url;
		
		if (!this.getServerPath().equalsIgnoreCase("")) {
			url = new URL("http://" + this.getDomain()
					+ "/" + this.getServerPath() + "/"
					+ "engine.php");
		} else {
			url = new URL("http://" + this.getDomain()
					+ "/" + "engine.php");
		}
		
		return url.toString();
	}
	
}

