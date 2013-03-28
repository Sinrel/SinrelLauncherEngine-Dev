package org.sinrel.example.gui;

import java.io.UnsupportedEncodingException;

import org.sinrel.engine.Engine;
import org.sinrel.engine.EngineSettings;
import org.sinrel.engine.library.cryption.Base64;

public abstract class Main {
	
	static Engine engine;
	static MainWindow main;
	
	public static void main( String args[] ) throws UnsupportedEncodingException {
		EngineSettings settings = new EngineSettings( "sinrel.org", "projects/sle/", "minecraft", "1" );
		engine = new Engine(settings);
		
		System.out.println( org.sinrel.engine.library.cryption.Base64.encode( "hello, sdworld!".getBytes() ) );
		 
	}
	
	//aGVsbG8sIHdvcmxkIQ==
	
	public static Engine getEngine() {
		return engine;
	}
	
}
