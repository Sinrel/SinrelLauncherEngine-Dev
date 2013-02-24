package org.sinrel.engine.exception;

/**
 * Класс исключения вызываемого при обнаружении отсутствия обязательного файла launcher.properties
 * @see EngineDescriptionFile
 */
public class LauncherDescriptionNotFoundException extends Exception{

	private static final long serialVersionUID = -8690110096409493285L;
	
	public LauncherDescriptionNotFoundException(){
		super("Обязательный файл launcher.properties отсутствует");
	}

}
