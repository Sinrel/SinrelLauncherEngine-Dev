package org.sinrel.example;

import org.sinrel.engine.launcher.JavaLauncher;
import org.sinrel.example.gui.MainWindow;

public class ExampleLauncher extends JavaLauncher {
	
	public static MainWindow main;
	
	@Override
	public void onEnable() {		
		main = new MainWindow();
		
		main.setVisible(true);
	}

	@Override
	public void onDisable() {
		main.setVisible( false );
		
		main.dispose();
	}

}
