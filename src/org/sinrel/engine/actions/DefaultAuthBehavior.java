package org.sinrel.engine.actions;

import java.net.URLEncoder;

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
			
			String answer = NetManager.sendPostRequest( NetManager.getEngineLink( engine ), data );

			if (engine.isDebug()) {
				System.out.println(answer);
			}
			
			String[] answerParts = answer.split("<:>");
			if(answerParts.length > 3)
				return ret;

			if ("OK".equals(answerParts[0])) {
				ret.setSession( AES.decrypt( answerParts[1] ) );
				ret.setToken( AES.decrypt( answerParts[2] ) );
				ret.setResult( AuthResult.OK );
			} else
				ret.setResult( AuthResult.valueOf( answer.trim() ) );
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}
}
