package org.sinrel.engine.library;

import java.io.File;

import org.sinrel.engine.Engine;

public final class OSManager {
	
	/**
	 * @param name Имя рабочей папки
 	 * @param clientName Имя сервера, к которому принадлежит клиент
	 * @return Возвращает директорию в которой содержатся файлы клиента
	 * Пример:
	 *  name = sinrel
	 *  clientName = simple
	 *  Будет возвращена директория - C:\Users\%USERNAME%\AppData\Roaming\.sinrel\simple\bin (Для Windows)
	 */
	public static final File getClientFolder( String workingDirectory, String clientName ) {
		return new File( getWorkingDirectory( workingDirectory ).getPath() , clientName + File.separator + "bin" + File.separator );		
	}
	
	public static final File getClientFolder( Engine engine, String clientName ) {
		return getClientFolder( engine.getSettings().getDirectory(), clientName );
	}
	
	public static final File getClientWorkingDirectory( Engine e, String clientName ) {
		return  new File( getWorkingDirectory( e.getSettings().getDirectory() ).getPath(), clientName );
	}
	
	public static final File getClientWorkingDirectory( String applicationName, String clientName ) {
		return  new File( getWorkingDirectory( applicationName ).getPath(), clientName );
	}
	
	public static final File getWorkingDirectory(String applicationName) {
		String userHome = System.getProperty("user.home", ".");
		File workingDirectory;
		
		switch (getPlatform().ordinal()) {
		case 0:
			case 1:
				workingDirectory = new File(userHome, '.' + applicationName + '/');
				break;
			case 2:
				String applicationData = System.getenv("APPDATA");
				
				if (applicationData != null)
					workingDirectory = new File(applicationData, "." + applicationName + '/');
				else {
					workingDirectory = new File(userHome, '.' + applicationName + '/');
				}
				break;
			case 3:
				workingDirectory = new File(userHome, "Library/Application Support/" + applicationName);
				break;
			default:
				workingDirectory = new File(userHome, applicationName + '/');
		}
				
		return workingDirectory;
    }
	
	public static final File getWorkingDirectory( Engine engine ) {
		return getWorkingDirectory( engine.getSettings().getDirectory() );
	}
	
	/**
	 * @return Возвращает имя серии операционной системы ( Для Windows 7/XP будет возвращено значение windows )
	 */
    public static final OS getPlatform() {
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
	
	public static enum OS {
		linux, solaris, windows, macos, unknown;
	}
	
	/**
	 * @return Возвращает версию Java, на которой запущен движок.
	 */
	public static float getJavaVersion() {
		String version = System.getProperty( "java.version" );
		
		return Float.parseFloat( version.substring( 0, 3 ) );
	}
	
}