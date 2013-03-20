package org.sinrel.engine.actions;

import java.io.File;

import javax.swing.JFrame;

public interface MinecraftStarter {
	void startMinecraft(File clientRoot, String login, String session, String server, int port, JFrame frame);
}
