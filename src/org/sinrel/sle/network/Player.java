package org.sinrel.sle.network;

public abstract class Player {
	
	/**
	 * Имя игрока
	 */
	protected String username;
	/**
	 * Сессия игрока
	 */
	protected String session;
	
	protected String accessToken;
	protected String uuid;
	
	public Player( String username, String session ) {
		this.username = username;
		this.session = session;
	}
	
	public abstract String getUsername();
	
	public abstract boolean isBanned();
	
	

}
