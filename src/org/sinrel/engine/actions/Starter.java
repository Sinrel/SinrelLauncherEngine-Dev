//FIXME В строке запуска указывать абсолютный путь к Java. Для исключения работы с переменными окружения JAVA_HOME
package org.sinrel.engine.actions;

import java.util.Map;

import org.sinrel.engine.Engine;

/** 
 * Класс реализующий методы для регулировки и запуска игрового клиента
 * 
 * @since 2.0.0
 */
public abstract class Starter {
	
	/** Никнейм игрока по-умолчанию */
    protected String username = "Player";
    /** Сессия игрока по-умолчанию */
    protected String session = "1234567890";
    /** Рабочая папка клиента по-умолчанию */
    protected String gameDir = "minecraft";
    protected String assetsDir = "assets";
    protected String resourcePackDir = "resourcepacks";
    /** Версия клиента. По-умолчанию не указывается при запуске клиента **/
    protected String version = null;
    
    /** Сервер для подключения после запуска */
    protected Map< String, String > server = null;
    /** Данные для работы с прокси в клиенте */
    protected Map< String, String > proxy = null;
    /*
     * добавить isValidProxy (в переводе "правильное прокси?");
     */
    
    /** Количество <b>мегабайт</b> оперативной памяти выделяемых при запуске клиента */
    protected int memory = 512;
    /** Размер окна запускаемого клиента в пикселях по-умолчанию */
    protected int width = 854, height = 480;
    
	protected boolean hideOutput = true;
	protected boolean fullscreen = true;
	protected boolean autoconnect = false;
	
	/** Экземпляр движка */
	protected Engine engine;
	
	/**
	 * @param engine Экземпляр движка
	 */
	public Starter( Engine engine ) {
		this.engine = engine;
		
		proxy.put( "proxyHost", null );
		proxy.put( "proxyPort", "8080" );
		proxy.put( "proxyUser", null );
		proxy.put( "proxyPass", null );
		
		server.put( "server", null );
		server.put( "port", "25565" );
		gameDir = this.engine.getSettings().getDirectory();
	}

	public abstract void startMinecraft( String clientName );	
	
	public abstract void startMinecraft( String clientName, String login, String session );
	
	public abstract void startMinecraft( String clientName, String login, String session, String server, String port );
	
	public int getMemory() {
		return memory;
	}
		
	/**
	 * @return Возвращает сессию с которой будет запущен клиент
	 */
	public String getSession() {
		return session;
	}
	
	public String getGameDir() {
		return gameDir;
	}
	
	public String getAssetsDir() {
		return assetsDir;
	}
	
	public String getResourcePackDir() {
		return resourcePackDir;
	}
	
	public String getVersion() {
		return version;
	}
	
	/**
	 * @return Возвращает адрес сервера на который будет производиться автозаход.
	 * <br>Если автозаход отключен, то будет возвращен NULL.
	 */
	public String getServerAddress() {
		return server.get( "server" );
	}
	
	/**
	 * @return Возвращает порт сервера на который будет производиться автозаход.
 	 */
	public int getServerPort() {
		return Integer.parseInt( server.get( "port" ) );
	}
	
	/**
	 * @return Возвращает порт сервера, как строку, на который будет производиться автозаход.
 	 */
	public String getServerPortAsString() {
		return server.get( "port" );
	}
	
	/**
	 * @return Возвращает данные от прокси, которые будет использовать клиент. 
	 * <br>Содержит ключи:
	 * <ul>
	 * 	<li>proxyHost</li>
	 *  <li>proxyPort</li>
	 *  <li>proxyUser</li>
	 *  <li>proxyPass</li>
	 * </ul>
	 */
	public Map< String, String > getProxyData() {
		return proxy;
	}
	
	public void setMemory( int memory ) {
		if( memory < 0 ) throw new IllegalArgumentException( "Нельзя использовать отрицательное количество выделяемой памяти" );
		this.memory = memory;
	}
	
	public void setProxyHost( String proxyHost ) {
		proxy.put( "proxyHost", proxyHost );
	}
	
	public void setProxyPort( String proxyPort ) {
		proxy.put( "proxyPort", proxyPort );
	}
	
	public void setProxyUser( String proxyUser ) {
		proxy.put( "proxyUser", proxyUser );
	}
	
	public void setProxyPass( String proxyPass ) {
		proxy.put( "proxyPass", proxyPass );
	}
	
	public void setVersion( String version ) {
		this.version = version;
	}
	
	public void setGameDir( String gameDir ) {
		if( gameDir == null ) throw new NullPointerException( "Главная папка клиента не может быть NULL" );
		this.gameDir = gameDir;
	}
	
	/**
	 * Устанавливает сессию с которой будет запущен клиент
	 * 
	 * @throws NullPointerException Возбуждается, если принятая сессия - null
	 * 
	 * @param session Сессия
	 */
	public void setSession( String session ) {
		if( session == null ) throw new NullPointerException( "Сессия не может быть NULL" );
		this.session = session;
	}
	
	public void setAssetsDir( String assetsDir ) {
		if( assetsDir == null ) throw new NullPointerException("Служебная папка клиента не может быть NULL");
		this.assetsDir = assetsDir;
	}
	
	/**
	 * Устанавливает имя папки, из которой клиент будет брать Assets
	 * 
	 * @throws NullPointerException Возбуждается, если принятый параметр - NULL 
	 * 
	 * @param resourcePackDir Имя папки для Assets клиента
	 */
	public void setResourcePackDir( String resourcePackDir ) {
		if( resourcePackDir == null ) throw new NullPointerException("Служебная папка клиента не может быть NULL");
		this.resourcePackDir = resourcePackDir;
	}
	
	/**
	 * Устанавливает адрес сервера на который будет заходить клиент после запуска.
	 * <br>Использование NULL-значений не возбуждает NullPointerException.
	 * <br>По-умолчанию автозаход отключается, если один из параметров - NULL.
	 * 
	 * @throws IllegalArgumentException Возбуждается, если порт нельзя привести к числовому значению, или порт является отрицательным.
	 * 
	 * @param serverAddress Адрес сервера
	 * @param serverPort Порт сервера
	 */
	public void setServer( String serverAddress, String serverPort ) {
		if( serverAddress == null | serverPort == null ) autoconnect = false;
		
		int temp;
		try {
			temp = Integer.parseInt( serverPort );
			if( temp < 0 ) throw new NumberFormatException();
		}catch( NumberFormatException e ) {
			throw new IllegalArgumentException( "Порт не является допустимым для использования" );
		}	
		
		server.put( "server", serverAddress );
		server.put( "port", serverPort );
		autoconnect = true;
	}
	
	/**
	 * Устанавливает адрес сервера на который будет заходить клиент после запуска.
	 * <br>Использование NULL-значений не возбуждает NullPointerException.
	 * <br>По-умолчанию автозаход отключается, если адрес сервера - NULL.
	 * 
	 * @throws IllegalArgumentException Возбуждается, если порт является отрицательным.
	 * 
	 * @param serverAddress Адрес сервера
	 * @param serverPort Порт сервера
	 */
	public void setServer( String serverAddress, int serverPort ) {
		if( serverAddress == null ) autoconnect = false;
		if( serverPort < 0 ) throw new IllegalArgumentException( "Нельзя использовать отрицательный порт" );
		
		server.put( "server", serverAddress );
		server.put( "port", Integer.toString( serverPort ) );
		autoconnect = true;
	}
	
	/**
	 * @param username Имя игрока
	 * @throws NullPointerException Возбуждается, если имя игрока NULL 
	 */
	public void setUsername( String username ) {
		if( username == null ) throw new NullPointerException("Логин не может быть NULL");
		this.username = username;
	}
	
 	/**
 	 * Устанавливает размер окна в котором запускается клиент.
 	 * <br>
 	 * Размер окна не будет изменён, если активирован полноэкранный режим (fullscreen)
 	 * 
 	 * @param width - ширина окна
 	 * @param height - высота окна
 	 * 
 	 * @throws IllegalArqumentException Возбуждается, если один из размеров отрицателен
 	 */
 	public void setSize( int width, int height ) {
 		if( width < 0 | height < 0 ) throw new IllegalArgumentException("Размер окна не может иметь отрицательные размеры");
 		this.width = width;
 		this.height = height;
 	}
		
	/**
	 * @param hideOutput Скрывать данные из выходного потока
	 */
	public void hideOutput( boolean hideOutput ) {
		this.hideOutput = hideOutput;
	}
	
	public boolean isOutput() {
		return hideOutput;
	}
	
	/**
	 * @return Возвращает имя игрока с которым запустится клиент
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * @return Возвращает ширину окна запускаемого клиента для не полноэкранного режима
	 */
	public int getWindowWidth() {
		return width;
	}
	
	/**
	 * @return Возвращает высоту окна запускаемого клиента для не полноэкранного режима
	 */
	public int getWindowHeight() {
		return height;
	}
	
	public void useAutoConnect( boolean useAutoConnect ) {
		autoconnect = useAutoConnect;
	}
	
	/**
	 * Принимает boolean-значение и устанавливает его.<br>
	 * Если true, то будет использоваться переход в полно-экранный режим
 	 * @param useFullScreen
	 */
	public void useFullScreen( boolean useFullScreen ) {
		fullscreen = useFullScreen;
	}
	
	public boolean isFullScreenActivated() {
		return fullscreen;
	}
	
	public boolean isAutoConnectActivated() {
		return autoconnect;
	}
	 	
}
