package org.sinrel.example.gui;

import java.io.UnsupportedEncodingException;

import org.sinrel.engine.Engine;
import org.sinrel.engine.EngineSettings;

public abstract class Main {
	
	static Engine engine;
	static MainWindow main;
	
	public static void main( String args[] ) throws UnsupportedEncodingException {
		EngineSettings settings = new EngineSettings( "sinrel.org", "projects/sle", "minecraft", "0.1", 1);
		engine = new Engine(settings);
	}
	
	public static Engine getEngine() {
		return engine;
	}
	
}
