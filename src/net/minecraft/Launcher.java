package net.minecraft;

import java.applet.Applet;
import java.applet.AppletStub;
import java.awt.BorderLayout;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import org.sinrel.engine.actions.AuthData;

public class Launcher extends Applet implements AppletStub {
	
	private static final long serialVersionUID = 1L;
	
	private Applet applet = null;
	public Map<String, String> customParameters = new HashMap<String, String>();
	private int context = 0;
	private boolean active = false;
	private URL[] urls;
	private String bin;
	private AuthData authData;

	public Launcher(String bin, URL[] urls, AuthData authData) {
		this.bin = bin;
		this.urls = urls;
		this.authData = authData;
	}

	public void init() {
		if (applet != null) {
			applet.init();
			return;
		}

		URLClassLoader cl = new URLClassLoader(urls);

		/*
		boolean hasReloader = false;
		
		try {
			cl.loadClass("cpw.mods.fml.relauncher.FMLRelauncher");
			hasReloader = true;
		} catch (ClassNotFoundException ex) {}

		if (!hasReloader) // Not a FML
			addExtra(cl, Launcher.class.getProtectionDomain());
		 */

		ClassPool pool = new ClassPool(true);

		try {
			String token = authData.getToken().replace("\"", "\\\"");
			CtClass dummy = pool.makeClass("sle.Dummy");
			dummy.addMethod(CtMethod.make("public static String bob() {return \"" + token + "\";}", dummy));

			dummy.toClass(cl, Launcher.class.getProtectionDomain());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		System.setProperty("org.lwjgl.librarypath", bin + "natives");
		System.setProperty("net.java.games.input.librarypath", bin + "natives");
		
		try {
			Class<?> Mine = cl.loadClass("net.minecraft.client.MinecraftApplet");
			Applet applet = (Applet) Mine.newInstance();
			this.applet = applet;
			applet.setStub(this);
			applet.setSize(getWidth(), getHeight());
			setLayout(new BorderLayout());
			add(applet, "Center");
			applet.init();
			active = true;
			validate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void replace(Applet applet) {
		this.applet = applet;
		
		applet.setStub(this);
		applet.setSize(getWidth(), getHeight());
		
		setLayout(new BorderLayout());
		
		add(applet, "Center");
		applet.init();
		active = true;
		validate();

        this.start();
   }
	
	public String getParameter(String name) {
		String custom = (String) customParameters.get(name);
		if (custom != null)
			return custom;
		try {
			return super.getParameter(name);
		} catch (Exception e) {
			customParameters.put(name, null);
		}
		return null;
	}

	public void start() {
		if (applet != null) {
			applet.start();
			return;
		}
	}

	public boolean isActive() {
		if (context == 0) {
			context = -1;
			try {
				if (getAppletContext() != null)
					context = 1;
			} catch (Exception e) {}
		}
		if (context == -1)
			return active;
		return super.isActive();
	}

	public void stop() {
		if (applet != null) {
			active = false;
			applet.stop();
			return;
		}
	}

	public void destroy() {
		if (applet != null) {
			applet.destroy();
			return;
		}
	}

	/*
	public void addExtra(ClassLoader loader, ProtectionDomain pd) {
		try {
			ClassPool pool = new ClassPool(true);
			String token = authData.getToken();

			try {
				loader.loadClass("sle.Dummy");
				return;
			} catch (ClassNotFoundException e) {
				System.err.println("Dummy not found");
			}

			token = token.replace("\"", "\\\"");

			CtClass pussy = pool.makeClass("dick.Pussy");
			pussy.addMethod(CtMethod.make("public static String getJoinserverToken() {return \"" + token + "\";}", pussy));
			pussy.addMethod(CtMethod.make("public static String getCheckserverToken() {return \"" + token + "\";}", pussy));

			pussy.toClass(loader, pd);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	*/

	public void appletResize(int w, int h) {}

	public URL getDocumentBase() {
		try {
			return new URL("http://www.minecraft.net/game/");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}
}