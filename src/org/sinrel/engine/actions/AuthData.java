package org.sinrel.engine.actions;

public class AuthData {
	private String session;
	private String login;
	private AuthResult result;
	
	public String getSession() {
		return session;
	}
	
	public String getLogin() {
		return login;
	}
	
	public AuthResult getResult() {
		return result;
	}
	
	public void setSession(String session) {
		this.session = session;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public void setResult(AuthResult result) {
		this.result = result;
	}
	
	public AuthData() {
		this(null, null, AuthResult.BAD_CONNECTION);
	}
	
	public AuthData(String login, String session, AuthResult result){
		setLogin(login);
		setSession(session);
		setResult(result);
	}
}
