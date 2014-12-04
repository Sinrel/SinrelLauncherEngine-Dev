package org.sinrel.sle.network;

import java.util.HashMap;

import com.google.gson.Gson;

public class Response {
	
	private Command command = Command.EMPTY;

	private HashMap< String, Object > properties = new HashMap<>();
	
	public void setCommand( Command command ) {
		this.command = command;
	}
	
	public Command getCommand() {
		return command;
	}
	
	public Object get( String key ) {
		return properties.get(key);
	}
	
	public String toJson() {
		return new Gson().toJson(this);
	}
	
	@Override
	public String toString() {
		return new Gson().toJson(this);
	}
	
}
