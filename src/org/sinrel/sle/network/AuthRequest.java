package org.sinrel.sle.network;

public class AuthRequest extends Request {
	
	public AuthRequest( String username, String password ) {
		put( "command", Command.AUTH );
		put( "username" , username );
		put( "password", password );
	}
	
	public String getUsername() {
		return (String) get( "username" );
	}
	
	public String getPassword() {
		return (String) get( "password" );
	}
	
	public void setUsername( String username ) {
		put( "username" , username );
	}
	
	public void setPassword( String password ) {
		put( "password", password );
	}

}
