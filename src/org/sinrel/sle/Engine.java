/* TODO 
 * Was changed:
 *   action -> changed to -> command
 *   
 *   NetManager -> renamed to -> NetworkManager
 *   
 * Commands:
 * 	launcher
 *  version
 *  key
 *  
 *  /////
 *  Must delete OSManager, NetManager, ZipManager, MD5
 *  
 *  TODO check path set in another systems.
*/
package org.sinrel.sle;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.apache.commons.lang3.Validate;
import org.sinrel.sle.actions.AuthBehavior;
import org.sinrel.sle.actions.Checker;
import org.sinrel.sle.actions.Config;
import org.sinrel.sle.actions.DefaultAuthBehavior;
import org.sinrel.sle.actions.DefaultChecker;
import org.sinrel.sle.actions.DefaultConfig;
import org.sinrel.sle.actions.DefaultDownloader;
import org.sinrel.sle.actions.DefaultStarter;
import org.sinrel.sle.actions.Downloader;
import org.sinrel.sle.actions.LauncherData;
import org.sinrel.sle.actions.Starter;
import org.sinrel.sle.exception.FatalError;
import org.sinrel.sle.library.cryption.Cryptor;
import org.sinrel.sle.network.Command;
import org.sinrel.sle.network.MirrorServer;
import org.sinrel.sle.network.NetworkManager;

/**
 *	Главный класс SLE, предоставляющий доступ ко всем функциям движка через свой экземпляр.
 */
public class Engine {
	
	private EngineSettings settings;
	
	private Config config;
	private Cryptor cryptor;
	
	private Downloader downloader;
	private Checker checker;
	private AuthBehavior auth;
	private Starter starter;

	private boolean debug = false;
	private File path;
	
	/** Адреса зеркальных серверов */
	protected ArrayList<MirrorServer> mirrors = new ArrayList<MirrorServer>();
	
	public Engine( EngineSettings settings ) {
		try {
			setSettings(settings);
			
			path = settings.getPath();
			
			checker = new DefaultChecker(this);
			config = new DefaultConfig(this);
			cryptor = new Cryptor(this);
			
			downloader = new DefaultDownloader(this);
			auth = new DefaultAuthBehavior(this);
			starter = new DefaultStarter(this);
		}catch( Exception e ) {
			e.printStackTrace();
			FatalError.showErrorWindow(e);
		}
	}	
	
	public Cryptor getCryptor() { 
		return cryptor;
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
	
	public Starter getStarter() {
		return starter;
	}
		
	public void setSettings( EngineSettings settings ){
		Validate.notNull( settings, "Settings for engine cannot be null" );
		this.settings = settings;
	}
	
	public void setDownloader(Downloader downloader) {
		Validate.notNull( downloader, "Implementation of module to engine cannot be null" );
		this.downloader = downloader;
	}
	
	public void setStarter( Starter starter ) {
		Validate.notNull( starter, "Implementation of module to engine cannot be null" );
		this.starter = starter;
	}
	
	public void setChecker( Checker checker ) {
		Validate.notNull( starter, "Implementation of module to engine cannot be null" );
		this.checker = checker;
	}
	
	public void setAuth(AuthBehavior auth) {
		Validate.notNull( starter, "Implementation of module to engine cannot be null" );
		this.auth = auth;
	}
			
	public void setConfig( Config config ) {
		Validate.notNull( starter, "Implementation of module to engine cannot be null" );
		this.config = config;
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
			InetAddress.getByName("google.com");
			return true;
	    }catch( UnknownHostException e ) {
	    	return false;
		}
	}

	/**
	 * @return all mirror servers
	 */
	public ArrayList<MirrorServer> getMirrorsServers() {
		return mirrors;
	}
	
	/**
	 * @param domain domain or IP of mirror server. 
	 * @param serverPath
	 */
	public void addMirrorServer( String domain, String serverPath ) {
		mirrors.add( new MirrorServer( domain, serverPath ) );
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
	 * <br><b>Для Windows:</b><br> {java.home} + /bin/ + javaw.exe 
	 * <br><b>Для других систем:</b><br> {java.home} + /bin/ + java
	 */
	public String getJavaDir() {
		String path = System.getProperty("java.home") + File.separator + "bin" + File.separator;

		if ( ( getPlatform() == OS.windows ) && ( new File(path + "javaw.exe").isFile() )) {
			return path.concat( "javaw.exe" );
		}

		return path.concat( "java" );
	}

	/**
	 * Открывает браузером по-умолчанию принимаемою ссылку
	 * 
	 * @param uri обьект класса {@link URI} содержащий ссылку для перехода по ней 
	 * @throws IOException если адрес из URI недоступен
	 */
	public void openLink( URI uri ) throws IOException {
		Desktop.getDesktop().browse( uri );
	}
	
	/**
	 * Открывает браузером по-умолчанию принимаемою ссылку
	 * 
	 * @param url обьект класса {@link URL} содержащий ссылку для перехода по ней
	 * @throws IOException, если адрес из URL недоступен
	 * @throws URISyntaxException 
	 */
	public void openLink( URL url ) throws IOException, URISyntaxException {
		Desktop.getDesktop().browse( url.toURI() );
	}
	
	public LauncherData getLauncherData() {
		LauncherData laun = null;

		try{
			String answer = NetworkManager.sendRequest( NetworkManager.getEngineLink( this ), Command.LAUNCHER);
			
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
		
	//FIXME Add cross-platform support set of path
	public File getDirectory() {
		String directory = getSettings().getDirectory();
		File workingDirectory;
		
		switch ( getPlatform().ordinal() ) {
		 case 0:
			case 1:
				workingDirectory = new File( path, '.' + directory + '/');
				break;
			case 2:
				String applicationData = System.getenv("APPDATA");
				
				if (applicationData != null)
					workingDirectory = new File( applicationData, "." + directory + '/');
				else {
					workingDirectory = new File( path, '.' + directory + '/');
				}
				break;
			case 3:
				workingDirectory = new File( path, "Library/Application Support/" + directory);
				break;
			default:
				workingDirectory = new File( path, directory + '/');
		}
				
		return workingDirectory;
    }
	
	public File getClientBinaryFolder( String clientName ) {
		return new File( getDirectory().getPath() , clientName + File.separator + "bin" + File.separator );		
	}
	
	public File getClientDirectory( String clientName ) {
		return new File( getDirectory().getPath(), clientName );
	}
		
	/**
	 * @return Возвращает имя серии операционной системы ( Для Windows 7/XP будет возвращено значение windows )
	 */
    public OS getPlatform() {
		String osName = System.getProperty("os.name").toLowerCase();
		
		if (osName.contains("win"))
			return OS.windows;
		if (osName.contains("mac"))
			return OS.macos;
		if (osName.contains("solaris"))
			return OS.solaris;
		if (osName.contains("sunos")) 
			return OS.solaris;
		if (osName.contains("linux")) 
			return OS.linux;
		if (osName.contains("unix")) 
			return OS.linux;
		
		return OS.unknown;
	}
	
	public enum OS {
		linux, solaris, windows, macos, unknown;
	}

}
