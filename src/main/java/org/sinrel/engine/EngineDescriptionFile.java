package org.sinrel.engine;

import org.sinrel.engine.exception.InvalidLauncherDescriptionFile;
import org.sinrel.engine.exception.LauncherDescriptionNotFoundException;

import java.io.IOException;
import java.util.Properties;

class EngineDescriptionFile {
	
	private static Properties prop = new Properties();
	
	public EngineDescriptionFile() throws LauncherDescriptionNotFoundException, InvalidLauncherDescriptionFile {
		try {
			
			prop.load( getClass().getResourceAsStream("/launcher.properties") );
			
			String[] required = {"debug", "code"};
			
			for(String s : required){
				if(prop.getProperty(s) == null){
					throw new InvalidLauncherDescriptionFile("пункт "+s+" отсутствует в launcher.properties");
				}
			}
			
		} catch (IOException e) {
			throw new LauncherDescriptionNotFoundException();
		}catch (InvalidLauncherDescriptionFile e) {
			e.printStackTrace();
		}catch (NullPointerException e) {
			throw new LauncherDescriptionNotFoundException();
		}
	}

	public String get( String key ) {
		return prop.getProperty( key );
	}
		
}
