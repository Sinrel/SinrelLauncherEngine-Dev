package org.sinrel.engine.util;

public abstract class Logger {
	
	private static String info = "[INFO]";
	private static String warning = "[WARNING]";
	private static String error = "[ERROR]";
	
	public static void warning(String message) {
		System.err.println(warning+" "+message);
	}
	
	public static void info(String message) {
		System.out.println(info+" "+message);
	}
	
	public static void error(String message) {
		System.err.println(error+" "+message);
	}
}
