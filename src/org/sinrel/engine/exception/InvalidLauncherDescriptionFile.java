package org.sinrel.engine.exception;

public class InvalidLauncherDescriptionFile extends Exception{
	
	private static final long serialVersionUID = 2565223425956379970L;
	
	public InvalidLauncherDescriptionFile(String message){
		super("Служебный файл launcher.properties не соответствует требованиям - "+message);
	}
	
}
