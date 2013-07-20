package org.sinrel.engine.actions;

public abstract class Config {
	
	public abstract void setPassword( String pass );
	
	public abstract String getPassword();
	
	public abstract void setLogin( String login );
	
	public abstract String getLogin();
	
	public abstract String getProperty( String name ) ;
	
	public abstract void setServer( String server );
	
	public abstract String getServer();
	
	public abstract void setMemory( int memory );
	
	public abstract int getMemory();
	
	public abstract void setProperty( String key, String value );
	
	public abstract void setSecureProperty( String key, String value );
	
}
