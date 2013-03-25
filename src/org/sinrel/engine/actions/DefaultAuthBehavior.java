package org.sinrel.engine.actions;

import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;

import org.sinrel.engine.Engine;
import org.sinrel.engine.library.NetManager;
import org.sinrel.engine.library.cryption.AES;

public class DefaultAuthBehavior implements AuthBehavior {

	public AuthData auth(Engine engine, String login, String pass) {
		AuthData ret = new AuthData(login, null, AuthResult.BAD_CONNECTION);
		try {
			String data = "action=auth";
			data += "&login=" + URLEncoder.encode( AES.encrypt( login ), "UTF-8");
			data += "&pass=" + URLEncoder.encode( AES.encrypt( pass ), "UTF-8");

			URL url;

			if (!engine.getSettings().getFolder().equalsIgnoreCase("")) {
				url = new URL("http://" + engine.getSettings().getDomain()
						+ "/" + engine.getSettings().getFolder() + "/"
						+ "engine.php");
			} else {
				url = new URL("http://" + engine.getSettings().getDomain()
						+ "/" + "engine.php");
			}

			String answer = NetManager.sendPostRequest(url, data);

			if (engine.isDebug()) {
				System.out.println(answer);
			}

			if (answer.contains("OK")) {
				ret.setSession( AES.decrypt( answer.split("<:>")[1] ) );

				ret.setResult(AuthResult.OK);
				return ret;
			}

			AuthResult.valueOf(answer.trim());
		} catch (IOException e) {
			ret.setResult(AuthResult.BAD_CONNECTION);
		} catch (IllegalArgumentException e) {
			ret.setResult(AuthResult.BAD_CONNECTION);
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}

		return ret;
	}
}
