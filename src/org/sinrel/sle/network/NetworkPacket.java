//TODO продумать новый протокол

package org.sinrel.sle.network;

import com.google.gson.Gson;

public class NetworkPacket {
	
	private String description;
	private String answer;
	private String command;
	private String system;
	private String client;
	
	public String getAnswer() {
		return answer;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String toJson() {
		return new Gson().toJson(this);
	}
	
}
