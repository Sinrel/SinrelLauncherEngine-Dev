package org.sinrel.sle.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.lang3.Validate;
import org.sinrel.sle.Engine;
import org.sinrel.sle.library.JsonProperties;

/**
 * This class include a standard implementation for config module
 * @since 2.0.0 build 5
 * @see Config
 */
public class DefaultConfig extends Config {
	
	private JsonProperties prop;
	private File config;
	
	/** Engine instance */
	private Engine engine;

	public DefaultConfig( Engine engine ) {
		this.engine = engine;
		
		prop = new JsonProperties();
		config = new File( engine.getDirectory(), "config.json" );
				
		try {
			if( !config.exists() ) createConfig();
			
			prop.load( new FileInputStream( config ) );
			
			save();
		}catch( IOException e ) {
			e.printStackTrace();
		}			
	}
	
	private void createConfig() throws FileNotFoundException, IOException {
		config.getParentFile().mkdirs();
		new FileOutputStream(config).close();
		
		FileWriter fw = new FileWriter( config );
		fw.write("{}"); //write null object
		fw.close();
	}
	
	private void save() {
		try {
			prop.store( new FileOutputStream( config ) ,"SLE config file" );
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @param pass string with password
	 */
	public void setPassword( String pass ) {
		Validate.notNull( pass, "Password cannot be null" );
		
		pass = StringUtils.newStringUtf8( StringUtils.getBytesUtf8( pass ) );
		try {
			prop.setProperty("password", engine.getCryptor().encrypt( pass ) );
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			prop.setProperty("password", pass);
		}
		save();
	}

	public String getPassword() {
		try {
			return engine.getCryptor().decrypt( prop.getProperty("password") );
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			return prop.getProperty("password");
		}
	}
	
	/**
	 * @param login string with login
	 */
	public void setLogin( String login ) {
		Validate.notNull( login, "Login cannot be null" );
		
		login = StringUtils.newStringUtf8( StringUtils.getBytesUtf8( login ) );
		prop.setProperty("login", login);
		
		save();
	}

	public String getLogin() {
		return prop.getProperty("login");
	}
	
	@Deprecated
	public void setServer( String server ) {
		Validate.notNull( server, "Server cannot be null" );
		
		server = StringUtils.newStringUtf8( StringUtils.getBytesUtf8( server ) );
		prop.setProperty( "server", server );
		
		save();
	}
	
	@Deprecated
	public String getServer() {
		return prop.getProperty( "server" ) ;
	}

	public void setMemory( int memory ) {
		if( memory < 0 ) throw new IllegalArgumentException("Can't use a negative values of memory");
		prop.setProperty( "memory", Integer.toString( memory ) );
		save();
	}

	public int getMemory() {
		return Integer.parseInt( prop.getProperty("memory") ); // Epmty if not defined
	}
	
	//TODO Root methods
	
	private void setProperty( String key, String value ) {
		Validate.notNull( key, "Key cannot be null" );
		Validate.notNull( key, "The key value cannot be null" );
		
		key = StringUtils.newStringUtf8( StringUtils.getBytesUtf8( key ) );
		value = StringUtils.newStringUtf8( StringUtils.getBytesUtf8( value ) );
		
		prop.setProperty( key , value );
		
		save();
	}
	
	private void setSecureProperty( String key, String value ) {
		Validate.notNull( key, "Key cannot be null" );
		Validate.notNull( key, "The key value cannot be null" );
		
		key = StringUtils.newStringUtf8( StringUtils.getBytesUtf8( key ) );
		value = StringUtils.newStringUtf8( StringUtils.getBytesUtf8( value ) );
		
		try {
			prop.setProperty( key, engine.getCryptor().encrypt( value ) );
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
			prop.setProperty( key, value );
		}
		
		save();
	}
	
	private String getProperty( String key ) {
		Validate.notNull( key, "Key cannot be null" );
		
		if( !prop.containsKey( key ) ) return null;
		else return prop.getProperty( key );
	}
	
	private String getProperty( String key, String defaultValue ) {
		Validate.notNull( key, "Key cannot be null" );
		Validate.notNull( key, "The default key value cannot be null" );
		
		if( !prop.containsKey(key) ) return defaultValue;
		else return prop.getProperty( key );
	}
	
	private String getCryptedProperty( String key ) {	 
		if( !prop.containsKey( key ) ) return null;
		
		try {
			return engine.getCryptor().decrypt( prop.getProperty( key ) );
		} catch (GeneralSecurityException e) {
			return prop.getProperty(key);
		}
	}
	
	private String getCryptedProperty( String key, String defaultValue ) {
		if( !prop.containsKey( key ) ) return null;
		
		try {
			return engine.getCryptor().decrypt( prop.getProperty( key ) );
		} catch (GeneralSecurityException e) {
			return defaultValue;
		}
	}
	
	//TODO setters
	
	/**
	 * @param key
	 * @param value
	 */
	public void setPropertyString( String key, String value ) {
		setProperty( key, value );
	}
	
	/**
	 * @param key
	 * @param value
	 */
	public void setPropertyBoolean( String key, boolean value ) {
		setProperty( key, Boolean.toString( value ) );
	}

	/**
	 * @param key
	 * @param value
	 */
	public void setPropertyInt(String key, int value) {
		setProperty( key, Integer.toString( value ) );
	}

	/**
	 * @param key
	 * @param value
	 */
	public void setPropertyDouble(String key, double value) {
		setProperty( key, Double.toString( value ) );
	}

	//TODO setters for crypted values
	
	/**
	 * @param key
	 * @param value
	 */
	public void setCryptedPropertyString( String key, String value ) {
		setSecureProperty( key, value );
	}

	/**
	 * @param key
	 * @param value
	 */
	public void setCryptedPropertyBoolean(String key, boolean value) {
		setSecureProperty( key, Boolean.toString( value ) );
	}

	/**
	 * @param key
	 * @param value
	 */
	public void setCryptedPropertyInt(String key, int value) {
		setSecureProperty( key, Integer.toString( value ) );
	}

	/**
	 * @param key
	 * @param value
	 */
	public void setCryptedPropertyDouble(String key, double value) {
		setSecureProperty( key, Double.toString( value ) );
	}

	//TODO getters
	
	/**
	 * @param key property name
	 * @return the value of key. If value not found returns {@code null}.
	 */
	public String getPropertyString( String key ) {
		return getProperty( key );
	}
	
	/**
	 * @param key property name
	 * @param defaultValue Стандартное значение параметра.
	 * @return Значение параметра связанного с ключом. Если значение ключа не найдено, возвращается значение аргумента defaultValue.
	 */
	public String getPropertyString( String key, String defaultValue ) {
		return getProperty( key, defaultValue );
	}
	
	/**
	 * @param key property name
	 * @return Значение параметра связанного с ключом. Если значение ключа не найдено, возвращается false.<br>При невалидном значении ключа возвращается false.
	 */
	public boolean getPropertyBoolean( String key ) {
		return Boolean.parseBoolean( getProperty( key, "false" ) );
	}
	
	/**
	 * @param key property name
	 * @param defaultValue Стандартное значение параметра.
	 * @return Значение параметра связанного с ключом. Если значение ключа не найдено, возвращается значение аргумента defaultValue.<br>При невалидном значении ключа возвращается defaultValue
	 */
	public boolean getPropertyBoolean( String key, boolean defaultValue ) {
		return Boolean.parseBoolean( getProperty( key, Boolean.toString( defaultValue ) ) );
	}	
	
	/**
	 * @param key property name
	 * @return Значение параметра связанного с ключом. Если значение ключа не найдено, возвращается 0.<br>При невалидном значении ключа возвращается 0.
	 */
	public int getPropertyInt( String key ) {
		return Integer.parseInt( getProperty( key, "0" ) );
	}
	
	/**
	 * @param key property name
	 * @param defaultValue Стандартное значение параметра.
	 * @return Значение параметра связанного с ключом. Если значение ключа не найдено, возвращается значение аргумента defaultValue.<br>При невалидном значении ключа возвращается defaultValue
	 */
	public int getPropertyInt( String key, int defaultValue ) {
		return Integer.parseInt( getProperty( key, Integer.toString( defaultValue ) ) );
	}

	/**
	 * @param key property name
	 * @return Значение параметра связанного с ключом. Если значение ключа не найдено, возвращается 0.<br>При невалидном значении ключа возвращается 0.
	 */
	public double getPropertyDouble( String key ) {
		return Double.parseDouble( getProperty( key, "0" ) );
	}
	
	/**
	 * @param key property name
	 * @param defaultValue Стандартное значение параметра.
	 * @return Значение параметра связанного с ключом. Если значение ключа не найдено, возвращается значение аргумента defaultValue.<br>При невалидном значении ключа возвращается defaultValue
	 */
	public double getPropertyDouble( String key, double defaultValue ) {
		return Double.parseDouble( getProperty( key, Double.toString( defaultValue ) ) );
	}
	
	//TODO getters for crypted values

	/**
	 * @param key property name
	 */
	public String getCryptedPropertyString( String key ) {
		return getCryptedProperty( key );
	}

	/**
	 * @param key property name
	 * @param defaultValue
	 */
	public String getCryptedPropertyString( String key, String defaultValue ) {
		return getCryptedProperty( key, defaultValue );
	}

	/**
	 * @param key property name
	 */
	public boolean getCryptedPropertyBoolean(String key) {
		String s = getCryptedProperty( key );
		
		if( s == null ) return false;
		else return Boolean.parseBoolean( s );
	}

	/**
	 * @param key property name
	 * @param defaultValue
	 */
	public boolean getCryptedPropertyBoolean(String key, boolean defaultValue) {
		String s = getCryptedProperty( key );
		
		if( s == null ) return defaultValue;
		else return Boolean.parseBoolean( s );
	}

	/**
	 * @param key property name
	 */
	public int getCryptedPropertyInt(String key) {
		String s = getCryptedProperty( key );
		
		if( s == null ) return 0;
		else return Integer.parseInt( s );
	}

	/**
	 * @param key property name
	 * @param defaultValue
	 */
	public int getCryptedPropertyInt(String key, int defaultValue) {
		String s = getCryptedProperty( key );
		
		if( s == null ) return defaultValue;
		else return Integer.parseInt( s );
	}

	/**
	 * @param key property name
	 */
	public double getCryptedPropertyDouble(String key) {
		String s = getCryptedProperty( key );
		
		if( s == null ) return 0;
		else return Double.parseDouble( s );
	}

	/**
	 * @param key property name
	 * @param defaultValue
	 */
	public double getCryptedPropertyDouble(String key, double defaultValue) {
		String s = getCryptedProperty( key );
		
		if( s == null ) return defaultValue;
		else return Double.parseDouble( s );
	}
	
}
