package org.sinrel.engine.library;

import java.io.File;

public abstract class OSManager {
	
	/**
	 * @param name Имя рабочей папки
 	 * @param serverName Имя сервера, к которому принадлежит клиент
	 * @return Возвращает директорию в которой содержатся файлы клиента
	 * 
	 * Пример:
	 *  name = sinrel
	 *  serverName = simple
	 *  Будет возвращена директория - C:\Users\%USERNAME%\AppData\Roaming\.sinrel\simple\bin (Для Windows)
	 */
	public static File getClientFolder( String name, String serverName ) {
		return new File( getWorkingDirectory( name ).getPath() , serverName + File.separator + "bin" + File.separator );
	}
	
	public static File getClientFolder( String name ) {
		return new File( getWorkingDirectory(name).getPath() , "bin" + File.separator );
	}
	
	public static File getWorkingDirectory(String applicationName) {
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

	/**
	 * @return Возвращает имя серии операционной системы ( Для Windows 7/XP будет возвращено значение windows )
	 */
    public static OS getPlatform() {
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
	
}