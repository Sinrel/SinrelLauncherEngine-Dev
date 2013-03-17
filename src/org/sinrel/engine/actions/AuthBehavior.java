package org.sinrel.engine.actions;

import org.sinrel.engine.Engine;

public interface AuthBehavior {

	public AuthData auth(Engine engine, String login, String pass);
	
}