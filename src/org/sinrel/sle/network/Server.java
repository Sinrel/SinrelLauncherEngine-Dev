package org.sinrel.sle.network;

public class Server {

	private String address;
	private int port;
	
	public Server( String address, int port ) {
		this.address = address;
		this.port = port;
	}
	
	public Online getOnline() throws Exception {
		return new Online( address, port );
	}
	
}
