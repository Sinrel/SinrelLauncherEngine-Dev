package org.sinrel.sle.actions;

import java.net.URLEncoder;
import java.util.HashMap;

import org.sinrel.sle.Engine;
import org.sinrel.sle.network.Command;
import org.sinrel.sle.network.NetworkManager;

public class DefaultAuthBehavior extends AuthBehavior {

	private Engine engine;

	public DefaultAuthBehavior(Engine engine) {
		this.engine = engine;
	}

	public AuthData auth(String login, String pass) {
		AuthData ret = new AuthData(login, null, AuthResult.BAD_CONNECTION);
		try {
			HashMap<String, String> args = new HashMap<>();
			args.put("login", URLEncoder.encode(
					engine.getCryptor().encrypt(login), "UTF-8"));
			args.put("pass", URLEncoder.encode(engine.getCryptor()
					.encrypt(pass), "UTF-8"));

			String answer = NetworkManager.sendRequest(
					NetworkManager.getEngineLink(engine), Command.AUTH, args).trim();

			if (engine.isDebug()) {
				System.out.println(answer);
			}

			String[] answerParts = answer.split("<:>");
			if (answerParts.length > 3)
				return ret;

			if ("OK".equals(answerParts[0])) {
				ret.setSession(engine.getCryptor().decrypt(answerParts[1]));
				ret.setResult(AuthResult.OK);
			} else
				ret.setResult(AuthResult.valueOf(answer.trim()));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return ret;
	}
}
