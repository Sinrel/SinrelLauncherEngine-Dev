package org.sinrel.engine.exception;

/**
 * Класс исключения вызываемого при обнаружении отсутствия обязательного файла-класса лаунчера
 */
public class MainLauncherClassNotFoundException extends Exception{

	private static final long serialVersionUID = -8690110096409493285L;
	
	public MainLauncherClassNotFoundException(String main){
		super("Обязательный файл-класс "+main+" отсутствует");
	}

}