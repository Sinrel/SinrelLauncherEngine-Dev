package org.sinrel.engine.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Properties;

import org.apache.commons.codec.binary.StringUtils;
import org.sinrel.engine.Engine;
import org.sinrel.engine.library.OSManager;
import org.sinrel.engine.library.cryption.AES;

public class DefaultConfig extends Config {
	
	private Properties prop = new Properties();
	private File config;

	public DefaultConfig( Engine engine ) {
		config = new File( OSManager.getWorkingDirectory( engine ).toString(), "launcher.config" );
		
		try {
			if( !config.exists() ) {
				config.getParentFile().mkdirs();
				new FileOutputStream(config).close();
			}
			
			prop.load( new FileInputStream( config ) );
			
			check("login");
			check("password");
			check("memory");
			check("server");
			
			save();
		}catch( IOException e ) {
			e.printStackTrace();
		}			
	}
	
	private void save() {
		try {
			prop.store( new FileOutputStream( config ) ,"");
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	private void check( String key ) {
		if( !prop.containsKey( key ) ) {
			prop.setProperty( key, "" );
		}
	}
	
	public void setPassword( String pass ) {
		if( pass == null ) return;
		pass = StringUtils.newStringUtf8( pass.getBytes() );
		try {
			prop.setProperty("password", AES.encrypt( pass ) );
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			prop.setProperty("password", pass);
		}
		save();
	}

	public String getPassword() {
		try {
			return AES.decrypt( prop.getProperty("password") );
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			return prop.getProperty("password");
		}
	}

	public void setLogin( String login ) {
		if( login == null ) return;
		login = StringUtils.newStringUtf8( login.getBytes() );
		prop.setProperty("login", login);
		save();
	}

	public String getLogin() {
		return prop.getProperty("login");
	}

	public void setProperty( String key, String value ) {
		if( key == null || value == null ) return;
		key = StringUtils.newStringUtf8( key.getBytes() );
		value = StringUtils.newStringUtf8( value.getBytes() );
		prop.setProperty( key , value );
		save();
	}

	public void setSecureProperty( String key, String value ) {
		if( key == null || value == null ) return;
		key = StringUtils.newStringUtf8( key.getBytes() );
		value = StringUtils.newStringUtf8( value.getBytes() );
		try {
			prop.setProperty( key, AES.encrypt( value ) );
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			prop.setProperty( key, value );
		}
		save();
	}

	public void setServer( String server ) {
		if( server == null ) return;
		server = StringUtils.newStringUtf8( server.getBytes() );
		prop.setProperty( "server", server );
		save();
	}

	public String getServer() {
		return prop.getProperty( "server" ) ;
	}

	public void setMemory( int memory ) {
		if( memory < 0 ) memory = Math.abs( memory );
		prop.setProperty( "memory", Integer.toString( memory ) );
		save();
	}

	public int getMemory() {
		return Integer.parseInt( prop.getProperty("memory") );
	}
	
}
