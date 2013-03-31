package org.sinrel.engine.actions;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;

import org.sinrel.engine.Engine;
import org.sinrel.engine.library.NetManager;
import org.sinrel.engine.library.OSManager;
import org.sinrel.engine.library.OSManager.OS;
import org.sinrel.engine.library.cryption.MD5;

public class DefaultChecker extends ClientChecker {

	// Проверяемые файлы. Так же проверяется наличие natives под систему клиента
	static String[] files = { "jinput.jar", "lwjgl.jar", "lwjgl_util.jar", "minecraft.jar" };

	private Engine engine;

	public ClientStatus checkClient(Engine e, String clientName) {
		try {
			String applicationName = e.getSettings().getDirectory();
			
			this.engine = e;

			this.onStartChecking();

			if (!NetManager.isOnline()) {
				return ClientStatus.BAD_CONNECTION;
			}

			if (!OSManager.getWorkingDirectory(applicationName).exists() | !OSManager.getClientFolder(applicationName , clientName).exists()) {
				return ClientStatus.CLIENT_NOT_EXIST;
			}

			StringBuffer hash = new StringBuffer();
			File f = null;
			
			for (String name : files) {
				f = new File( OSManager.getClientFolder(applicationName, clientName ), name);
				if (!f.exists()) {
					return ClientStatus.WRONG_CLIENT;
				}

				hash.append( MD5.getMD5(f) );
			}
			
			this.onFinishChecking();
			return send( clientName , hash.toString(), OSManager.getPlatform());
			
		} catch (IOException ex) {
			return ClientStatus.CLIENT_DOES_NOT_MATCH;
		}
	}

	private ClientStatus send(String client, String hash, OS system) {
		try {
			String data = "action=" + URLEncoder.encode("check", "UTF-8");
			data += "&client=" + URLEncoder.encode(client, "UTF-8");
			data += "&hash=" + URLEncoder.encode(hash, "UTF-8");
			data += "&system=" + URLEncoder.encode(system.toString(), "UTF-8");

			URL url;

			if (!engine.getSettings().getServerPath().equalsIgnoreCase("")) {
				url = new URL(
						"http://" + engine.getSettings().getDomain() + "/" + engine.getSettings().getServerPath() + "/" + "engine.php"
						);
			} else {
				url = new URL(
						"http://" + engine.getSettings().getDomain() + "/" + "engine.php"
						);
			}

			String s = NetManager.sendPostRequest(url, data);
			
			return ClientStatus.valueOf(s.trim());
		} catch (IOException e) {
			return ClientStatus.BAD_CONNECTION;
		} catch (IllegalArgumentException e) {
			return ClientStatus.BAD_CONNECTION;
		}
	}
	
}
