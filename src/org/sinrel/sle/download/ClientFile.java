package org.sinrel.sle.download;

import org.sinrel.sle.library.OperationSystem;

public class ClientFile {
	
	private String name; //filename
	
	private OperationSystem system;
	
	private String target;
	
	private boolean unpack;
	
	public ClientFile() {
		
	}
	
	public String getName() {
		return name;
	}

}