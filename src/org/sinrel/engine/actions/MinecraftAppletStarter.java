package org.sinrel.engine.actions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JFrame;

import net.minecraft.Launcher;

import org.sinrel.engine.library.OSManager;

public class MinecraftAppletStarter implements MinecraftStarter {
	
	private JFrame temp = new JFrame();
	
	private boolean fullscreen = false, output = true;
	private int width = 800, height = 600;

	public void startMinecraft( String dir, String clientName, AuthData authData, String server, String port, Frame frame ) {
		if ( dir == null || clientName == null || authData == null || frame == null ) throw new NullPointerException();
		if( port == null ) port = "25565";
			else if ( port.equals( "" ) ) port = "25565";

		String bin = OSManager.getClientFolder(dir, clientName).getAbsolutePath() + File.separator;
		
		URL[] urls = new URL[4];
		try {
			urls[0] = new File(bin, "minecraft.jar").toURI().toURL();
			urls[1] = new File(bin, "lwjgl.jar").toURI().toURL();
			urls[2] = new File(bin, "jinput.jar").toURI().toURL();
			urls[3] = new File(bin, "lwjgl_util.jar").toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		final Launcher mcapplet = new Launcher(bin, urls, authData);
		mcapplet.customParameters.put("username", authData.getLogin());
		mcapplet.customParameters.put("sessionid", authData.getSession());
		mcapplet.customParameters.put("stand-alone", "true");
		if (server != null) {
			mcapplet.customParameters.put("server", server);
			mcapplet.customParameters.put("port", port);
		}
		
		if( fullscreen ) {
			temp.setExtendedState( JFrame.MAXIMIZED_BOTH );
			temp.setMinimumSize( new Dimension( 800, 600 ) );
		}else
			temp.setSize( new Dimension( width, height ) );
		
		temp.setLayout( new BorderLayout() );
		temp.setIconImage( frame.getIconImage() );
		temp.setLocationRelativeTo( null );
		temp.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
	
		mcapplet.setForeground(Color.BLACK);
		mcapplet.setBackground(Color.BLACK);
		
		frame.setVisible( false );
		frame.dispose();
		
		temp.add( mcapplet, BorderLayout.CENTER );
		temp.validate();
		temp.setVisible( true );

		System.setProperty("minecraft.applet.WrapperClass", "net.minecraft.Launcher");
			
		if( output ) {
			System.setErr(new PrintStream( new NulledStream()) );
			System.setOut(new PrintStream( new NulledStream()) );
		}
		
		mcapplet.init();
		mcapplet.start();
	}

	private class NulledStream extends OutputStream {
		public void write(int b) throws IOException {}
	}

	public void useFullScreen( boolean bool ) {
		this.fullscreen = bool;
	}
	
	public void useOutput( boolean output ) {
		this.output = output;
	}

	public boolean isFullScreen() {
		return fullscreen;
	}

	public boolean isOutput() {
		return output;
	}

	public void setSize( int width, int height ) {
		this.width = width;
		this.height = height;
	}
	
}