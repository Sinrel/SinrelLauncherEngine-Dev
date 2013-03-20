package org.sinrel.engine.actions;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLClassLoader;

import javax.swing.JFrame;

import net.minecraft.Launcher;

import org.sinrel.engine.exception.SLException;

public class MinecraftAppletStarter implements MinecraftStarter {

	public void startMinecraft(File clientRoot, String login, String session, String server, int port, JFrame frame) {
		if (frame == null)
			throw new NullPointerException("frame не может быть null (frame could't be null)");

		try {
			File binDir = new File(clientRoot, "bin");

			URL[] urls = new URL[4];
			urls[0] = new File(binDir, "minecraft.jar").toURI().toURL();
			urls[1] = new File(binDir, "lwjgl.jar").toURI().toURL();
			urls[2] = new File(binDir, "jinput.jar").toURI().toURL();
			urls[3] = new File(binDir, "lwjgl_util.jar").toURI().toURL();

			final Launcher launcher = new Launcher();
			launcher.customParameters.put("username", login);
			launcher.customParameters.put("sessionid", session);
			if (server != null) {
				launcher.customParameters.put("server", server);
				launcher.customParameters.put("port", String.valueOf(port));
			}

			frame.setSize(850, 480);
			launcher.setForeground(Color.BLACK);
			launcher.setBackground(Color.BLACK);

			frame.getRootPane().removeAll();
			frame.getRootPane().setLayout(new BorderLayout());
			frame.getRootPane().add(launcher, BorderLayout.CENTER);
			frame.getRootPane().validate();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);

			// System.setErr(new PrintStream(new NulledStream()));
			// System.setOut(new PrintStream(new NulledStream()));

			String nativesPath = new File(binDir, "natives").getAbsolutePath();
			System.setProperty("org.lwjgl.librarypath", nativesPath);
			System.setProperty("net.java.games.input.librarypath", nativesPath);

			// set minecraft.applet.WrapperClass to support newer FML builds
			// FML seems to restart the whole game which causes some problems in
			// custom launchers like this one
			System.setProperty("minecraft.applet.WrapperClass", "net.minecraft.Launcher");

			URLClassLoader cl = new URLClassLoader(urls);
			Class<?> appletClass = cl.loadClass("net.minecraft.client.MinecraftApplet");
			Applet applet = (Applet) appletClass.newInstance();

			launcher.replace(applet);
		} catch (Exception e) {
			throw new SLException(e);
		}
	}

	private class NulledStream extends OutputStream {
		public void write(int b) throws IOException {}
	}

}
