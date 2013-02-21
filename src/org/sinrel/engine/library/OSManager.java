package org.sinrel.engine.library;

public final class OSManager {
	
	public OS getPlatform() {
		String osName = System.getProperty("os.name").toLowerCase();
		
		if (osName.contains("win"))
			return OS.windows;
		if (osName.contains("mac"))
			return OS.macos;
		if (osName.contains("solaris"))
			return OS.solaris;
		if (osName.contains("sunos"))
			return OS.solaris;
		if (osName.contains("linux"))
			return OS.linux;
		if (osName.contains("unix"))
			return OS.linux;
		
		return OS.unknown;
	}
	
	private enum OS {
		linux, solaris, windows, macos, unknown;
	}
	
}