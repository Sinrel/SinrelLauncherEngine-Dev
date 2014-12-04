package org.sinrel.sle.network;

public enum Command {
	LAUNCHER("launcher"), KEY("key"), CHECK("check"), DIRECTORY("directory"),
	AUTH("auth"), EMPTY("empty");
	
	private String value;
	
	private Command(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	@Override
	public String toString() {
		return value;
	}
	
}
