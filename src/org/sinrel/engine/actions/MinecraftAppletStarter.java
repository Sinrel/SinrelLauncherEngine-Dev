package org.sinrel.engine.actions;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Frame;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URL;

import net.minecraft.Launcher;

import org.sinrel.engine.library.OSManager;

public class MinecraftAppletStarter implements MinecraftStarter {
	
	public void startMinecraft(String dir, String clientName, String login, String session, boolean useAutoConnect, String server, String port, Frame frame) {
		if (frame == null)
			throw new NullPointerException("frame не может быть null (frame could't be null)");
		
		String bin = OSManager.getClientFolder(dir, clientName) + File.separator;

		URL[] urls = new URL[4];
		try {
			urls[0] = new File(bin, "minecraft.jar").toURI().toURL();
			urls[1] = new File(bin, "lwjgl.jar").toURI().toURL();
			urls[2] = new File(bin, "jinput.jar").toURI().toURL();
			urls[3] = new File(bin, "lwjgl_util.jar").toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		final Launcher mcapplet = new Launcher(bin, urls);
		mcapplet.customParameters.put("username", login);
		mcapplet.customParameters.put("sessionid", session);
		mcapplet.customParameters.put("stand-alone", "true");
		if (useAutoConnect) {
			mcapplet.customParameters.put("server", server);
			mcapplet.customParameters.put("port", port);
		}

		frame.setSize(850, 480);
		mcapplet.setForeground(Color.BLACK);
		mcapplet.setBackground(Color.BLACK);
		frame.setLayout(new BorderLayout());
		frame.add(mcapplet, BorderLayout.CENTER);
		frame.validate();
		frame.setVisible(true);
		
		System.setErr( new PrintStream( new NulledStream() ) );
		System.setOut( new PrintStream( new NulledStream() ) );
		
		mcapplet.init();
		mcapplet.start();
	}
	
	private class NulledStream extends OutputStream {
		public void write(int b) throws IOException {}
	}
	
}
