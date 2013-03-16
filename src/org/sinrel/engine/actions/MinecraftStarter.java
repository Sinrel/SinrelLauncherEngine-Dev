package org.sinrel.engine.actions;

import java.awt.Frame;

public interface MinecraftStarter {
	abstract void startMinecraft(String dir, String login, String session, boolean useAutoConnect, String server, String port, Frame frame);
}
