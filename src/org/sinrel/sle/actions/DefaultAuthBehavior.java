package org.sinrel.sle.actions;

import java.net.URLEncoder;

import org.sinrel.sle.Engine;
import org.sinrel.sle.network.NetworkManager;

public class DefaultAuthBehavior extends AuthBehavior {

	private Engine engine;
	
	public DefaultAuthBehavior( Engine engine ) {
		this.engine = engine;
	}

	public AuthData auth( String login, String pass ) {
		AuthData ret = new AuthData(login, null, AuthResult.BAD_CONNECTION);
		try {
			String data = "command=auth";
			data += "&login=" + URLEncoder.encode( engine.getCryptor().encrypt( login ), "UTF-8");
			data += "&pass=" + URLEncoder.encode( engine.getCryptor().encrypt( pass ), "UTF-8");
			
			String answer = NetworkManager.sendPostRequest( NetworkManager.getEngineLink( engine ), data ).trim();

			if (engine.isDebug()) {
				System.out.println(answer);
			}
			
			String[] answerParts = answer.split("<:>");
			if(answerParts.length > 3)
				return ret;
			
			if ("OK".equals( answerParts[0] ) ) {
				ret.setSession( engine.getCryptor().decrypt( answerParts[1] ) );
				ret.setResult( AuthResult.OK );
			} else
				ret.setResult( AuthResult.valueOf( answer.trim() ) );
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}
}
