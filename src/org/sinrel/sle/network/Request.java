package org.sinrel.sle.network;

import java.util.HashMap;

import com.google.gson.Gson;

public class Request {
	
	private Command command = Command.EMPTY;

	private HashMap<String, Object > properties = new HashMap<>();
	
	public void setCommand( Command command ) {
		this.command = command;
	}
	
	public Command getCommand() {
		return command;
	}
	
	public void put( String key, Object value ) {
		if( key.equalsIgnoreCase("command") && value instanceof Command ) {
			command = (Command) value;
			return;
		}
		properties.put(key, value);
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
