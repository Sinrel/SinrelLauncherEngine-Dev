package org.sinrel.sle.actions;

public abstract class Config {
	
	public abstract void setPassword( String password );
	
	public abstract String getPassword();
	
	public abstract void setLogin( String login );
	
	public abstract String getLogin();
	
	public abstract void setMemory( int memory );
	
	public abstract int getMemory();
	
	// Methods for set not crypted properties
	
	public abstract void setPropertyString( String key, String value );
	
	public abstract void setPropertyBoolean( String key, boolean value );
	
	public abstract void setPropertyInt( String key, int value );
	
	public abstract void setPropertyDouble( String key, double value );
	
	// Methods for set crypted properties 
	
	public abstract void setCryptedPropertyString( String key, String value );
	
	public abstract void setCryptedPropertyBoolean( String key, boolean value );
	
	public abstract void setCryptedPropertyInt( String key, int value );
	
	public abstract void setCryptedPropertyDouble( String key, double value );
	
	//Methods for get not crypted properties
	
	public abstract String getPropertyString( String key );

	public abstract String getPropertyString( String key, String defaultValue );
	
	public abstract boolean getPropertyBoolean( String key );
	
	public abstract boolean getPropertyBoolean( String key, boolean defaultValue );
	
	public abstract int getPropertyInt( String key );
	
	public abstract int getPropertyInt( String key, int defaultValue );
	
	public abstract double getPropertyDouble( String key );
	
	public abstract double getPropertyDouble( String key, double defaultValue );

	// Methods for get crypted properties
	
	public abstract String getCryptedPropertyString( String key );
	
	public abstract String getCryptedPropertyString( String key, String defaultValue );
	
	public abstract boolean getCryptedPropertyBoolean( String key );

	public abstract boolean getCryptedPropertyBoolean( String key, boolean defaultValue );
	
	public abstract int getCryptedPropertyInt( String key );
	
	public abstract int getCryptedPropertyInt( String key, int defaultValue );
	
	public abstract double getCryptedPropertyDouble( String key );
	
	public abstract double getCryptedPropertyDouble( String key, double defaultValue );

}
