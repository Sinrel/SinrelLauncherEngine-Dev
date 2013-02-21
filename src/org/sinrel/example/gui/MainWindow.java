package org.sinrel.example.gui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.sinrel.engine.actions.Action;
import org.sinrel.engine.actions.Intent;

public class MainWindow extends JFrame implements WindowListener {

	private static final long serialVersionUID = -5258815870427404620L;
	
	public static final int width = 400, height = 300;
	
	public MainWindow () {
		super();
		
		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		} catch ( Exception e ) {}
		
		setSize( width , height );
		setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE );
		addWindowListener( this );
		setLayout(null);
		setTitle("Example launcher on SLE");
		setResizable(false);
		
		add( new TopPanel() );
		add( new LoginPanel() );
	}
	
	public void windowClosing(WindowEvent arg0) {
		Intent.Do( Action.DISABLE );
	}
	
	public void windowActivated(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowOpened(WindowEvent arg0) {}
	
}
