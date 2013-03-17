package org.sinrel.engine.listeners;

public interface CheckerListener {
	
	/**
	 * При старте проверки
	 */
	public void onStartChecking();
	
	/**
	 * При окончании проверки
	 */
	public void onFinishChecking();
	
}
