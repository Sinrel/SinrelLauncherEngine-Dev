package net.minecraft;

import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.awt.BorderLayout;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

public class Launcher extends JPanel implements AppletStub {
	private static final long serialVersionUID = 1L;
	public Map<String, String> customParameters = new HashMap<String, String>();
	private boolean active = false;

	public void replace(Applet applet) {
		applet.setStub(this);
		applet.setSize(getWidth(), getHeight());

		setLayout(new BorderLayout());
		add(applet, "Center");

		applet.init();

		this.active = true;

		applet.start();
		validate();
	}

	public boolean isActive() {
		return active;
	}

	public void appletResize(int w, int h) {
		setSize(w, h);
	}
	
	@Override
	public String getParameter(String name) {
		return customParameters.get(name);
	}

	public URL getDocumentBase() {
		try {
			return new URL("http://google.com");
		} catch (MalformedURLException e) {}
		return null;
	}

	@Override
	public URL getCodeBase() {
		return null;
	}

	@Override
	public AppletContext getAppletContext() {
		return null;
	}
}
