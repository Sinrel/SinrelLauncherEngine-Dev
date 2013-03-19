package org.sinrel.engine.actions;

import java.awt.Frame;

public interface MinecraftStarter {
	void startMinecraft(String dir, String login, String session, boolean useAutoConnect, String server, String port, Frame frame);
	
	void startMinecraft(String dir, String clientName , String login, String session, boolean useAutoConnect, String server, String port, Frame frame);
}
