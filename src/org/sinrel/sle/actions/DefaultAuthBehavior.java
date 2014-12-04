package org.sinrel.sle.actions;

import org.sinrel.sle.Engine;
import org.sinrel.sle.network.AuthRequest;
import org.sinrel.sle.network.Command;
import org.sinrel.sle.network.NetworkManager;
import org.sinrel.sle.network.Response;

public class DefaultAuthBehavior extends AuthBehavior {

	private Engine engine;

	public DefaultAuthBehavior(Engine engine) {
		this.engine = engine;
	}

	public AuthData auth(String login, String pass) {
		AuthData ret = new AuthData(login, null, AuthResult.BAD_CONNECTION);
		AuthRequest req = new AuthRequest( login , pass );
			
		try {
			Response res = NetworkManager.sendRequest(engine, req);
			
			if (engine.isDebug()) {
				System.out.println( res.toString() );
			}
			
			if( !isValidResponse( res ) || res.getCommand() != Command.AUTH || res.getCommand() == Command.EMPTY ) return ret; 
			
			if( ( (String) res.get("code") ).equalsIgnoreCase("ok") ) {
				ret.setSession( (String) res.get("session") );
				ret.setResult( AuthResult.OK );
			}else
				ret.setResult( AuthResult.valueOf( (String) res.get("code") ) );
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret;
	}
	
	private boolean isValidResponse( Response res ) {
		if( res.get("code") == null || res.get("session") == null || res.getCommand() == null )  return false;
		
		return true;
	}
	
}
