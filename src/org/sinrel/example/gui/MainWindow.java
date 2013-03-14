package org.sinrel.example.gui;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.sinrel.engine.Engine;
import org.sinrel.engine.EngineSettings;

public class MainWindow extends JFrame{

	static Engine engine;
	
	private static final long serialVersionUID = -5258815870427404620L;
	
	public static final int width = 400, height = 300;
	
	public static void main(String[] args) {
		EngineSettings settings = new EngineSettings("example.com", "launcher", "0.1");
		engine = new Engine(settings);
		MainWindow main = new MainWindow();
		main.setVisible(true);
	}
	
	public MainWindow () {
		super();
		
		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		} catch ( Exception e ) {}
		
		setSize( width , height );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setLayout(null);
		setTitle("Example launcher on SLE");
		setResizable(false);
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {}
		});
		
		add( new TopPanel() );
		add( new LoginPanel() );
	}
	
}
