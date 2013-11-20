package org.sinrel.engine;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URI;
import java.net.UnknownHostException;

import org.sinrel.engine.actions.AuthBehavior;
import org.sinrel.engine.actions.Checker;
import org.sinrel.engine.actions.Config;
import org.sinrel.engine.actions.DefaultAuthBehavior;
import org.sinrel.engine.actions.DefaultChecker;
import org.sinrel.engine.actions.DefaultConfig;
import org.sinrel.engine.actions.DefaultDownloader;
import org.sinrel.engine.actions.Downloader;
import org.sinrel.engine.actions.LauncherData;
import org.sinrel.engine.actions.MinecraftAppletStarter;
import org.sinrel.engine.actions.MinecraftStarter;
import org.sinrel.engine.exception.FatalError;
import org.sinrel.engine.library.NetManager;
import org.sinrel.engine.library.OSManager;

/**
 *	Главный класс SLE, предоставляющий доступ ко всем функциям движка через свой экземпляр.
 */
public class Engine {
	
	private EngineSettings settings;
	
	private Config config;
	
	private Downloader downloader;
	private Checker checker;
	private AuthBehavior auth;
	private MinecraftStarter starter;

	private boolean debug = false;
	
	public Engine( EngineSettings settings ) {
		try {
			setSettings(settings);
			
			checker = new DefaultChecker(this);
			config = new DefaultConfig(this);
			
			downloader = new DefaultDownloader(this);
			auth = new DefaultAuthBehavior(this);
			starter = new MinecraftAppletStarter(this);
		}catch( Exception e ) {
			FatalError.showErrorWindow(e);
		}
	}	
	
	public EngineSettings getSettings() {
		return settings;
	}
	
	public Downloader getDownloader() {
		return downloader;
	}
	
	public Checker getChecker() {
		return checker;
	}
	
	public AuthBehavior getAuth() {
		return auth;
	}	
	
	public Config getConfig() {
		return config;
	}
	
	public MinecraftStarter getStarter(){
		return starter;
	}
		
	public void setSettings( EngineSettings settings ){
		if( settings == null ) throw new NullPointerException();
		this.settings = settings;
	}
	
	public void setDownloader(Downloader downloader) {
		if( downloader == null ) throw new NullPointerException();
		this.downloader = downloader;
	}
	
	public void setChecker( Checker checker ) {
		if( checker == null ) throw new NullPointerException();
		this.checker = checker;
	}
	
	public void setAuth(AuthBehavior auth) {
		if( auth == null ) throw new NullPointerException();
		this.auth = auth;
	}
			
	public void setConfig( Config config ) {
		if( config == null ) throw new NullPointerException();
		this.config = config;
	}
	
	public void setMinecraftStarter( MinecraftStarter starter ){
		if( starter == null ) throw new NullPointerException();
		this.starter = starter;
	}
	
	public void useDebug( boolean debug ) {
		this.debug = debug;
	}
	
	public boolean isDebug(){
		return debug;
	}
	
	/**
	 * @return Проверяет наличие запущенных копий движка и возвращает boolean-ответ.
	 */
	public boolean isDuplicated() {
		try {
			new ServerSocket( 60_000 );
			return false;
		} catch ( IOException e ) {
			return true;
		}
	}
	
	/**
	 * Проверяет наличие соединения с интернетом.
	 * <br>При имеющимся таковом возвращает true, в ином случае false
	 * 
	 * @return boolean
	 */
	public boolean isOnline() {
		try {
			InetAddress.getByName("sinrel.org");
			return true;
	       }catch( UnknownHostException e ) {
	    	   try {
	    		   InetAddress.getByName("google.com");
	    		   return true;
	    	   }catch( UnknownHostException ex ) {
	    		   return false;   
	    	   }
		   }
	}
	
	/**
	 * @return Возвращает версию Java, на которой запущен движок.
	 */
	public float getJavaVersion() {
		String version = System.getProperty( "java.version" );
		
		return Float.parseFloat( version.substring( 0, 3 ) );
	}
	
	/**
	 * @return Возвращает путь к Java-машине.
	 * <br>Для Windows: {java.home} + /bin/ + javaw.exe 
	 * <br>Для других систем: {java.home} + /bin/ + java
	 */
	public String getJavaDir() {
		String path = System.getProperty("java.home") + File.separator + "bin" + File.separator;

		if (( OSManager.getPlatform() == OSManager.OS.windows ) && ( new File(path + "javaw.exe").isFile() )) {
			return path.concat( "javaw.exe" );
		}

		return path.concat( "java" );
	}

	/**
	 * Открывает браузером по-умолчанию принимаемою ссылку
	 * 
	 * @param uri обьект класса {@link URI} содержащий ссылку для перехода 
	 * @throws IOException если адрес из URI недоступен
	 */
	public void openLink(URI uri) throws IOException {
		Desktop.getDesktop().browse(uri);
	}
	
	public LauncherData getLauncherData() {
		LauncherData laun = null;

		try{
			String answer = NetManager.sendPostRequest( NetManager.getEngineLink( this ), "action=launcher");
			
			if( isDebug() )
				System.out.println( answer );
			
			laun = new LauncherData( this, Integer.parseInt( answer.split("<:>")[0] ) , answer.split("<:>")[1] );
			
			return laun;
		}catch( MalformedURLException e ) {
			e.printStackTrace();
		}catch( IOException e ) {
			e.printStackTrace();
		}
		
		return laun;
	}
		
}
