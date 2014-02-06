package org.sinrel.sle.actions;

import java.io.File;
import java.util.ArrayList;

import org.sinrel.sle.Engine;
import org.sinrel.sle.Engine.OS;
import org.sinrel.sle.exception.FatalError;

public class DefaultStarter extends Starter {

	private String cps = engine.getPlatform() == OS.windows ? ";" : ":";
	private String java = engine.getPlatform() == OS.windows ? "javaw" : "java";
	
	private String libraries =  "libraries.jar";
	private String forge =  "forge.jar";
	private String extra =  "extra.jar";
	private String minecraft =  "minecraft.jar";
	
	private ArrayList<String> parameters = new ArrayList<>();
	
	public DefaultStarter( Engine engine ) {
		super( engine );
	}
	
	public void startMinecraft( String clientName ) {
		try {
			String bin = engine.getClientBinaryFolder(clientName).toString() + File.separator, 
				   root = engine.getDirectory().toString() + File.separator + clientName + File.separator, 
				   assets = root + "assets" + File.separator;
			
			parameters.add( java );
			parameters.add("-Xmx" + memory + "m");
			parameters.add("-Dfml.ignoreInvalidMinecraftCertificates=true");
			parameters.add("-Dfml.ignorePatchDiscrepancies=true");
			parameters.add("-Djava.library.path=" + bin + "natives");
			parameters.add("-cp");
			
			//parameters.add( bin + libraries + cps + bin + extra + cps + bin + forge + cps + bin + minecraft );
			
			parameters.add( bin + libraries + cps + bin + minecraft );
			
			// FIXME Автовыбор класса. Проверка libraries.jar на наличие класса
			// net.minecraft.launchwrapper.Launch
			parameters.add("net.minecraft.client.main.Main");

			if (fullscreen) {
				parameters.add("--fullscreen");
				parameters.add("true");
			} else {
				parameters.add("--width");
				parameters.add(String.valueOf(width));
				parameters.add("--height");
				parameters.add(String.valueOf(height));
			}

			parameters.add("--username");
			parameters.add(username);
			parameters.add("--session");
			parameters.add(session);
			parameters.add("--version");
			parameters.add("1");

			parameters.add("--gameDir");
			parameters.add(root);
			parameters.add("--assetsDir");
			parameters.add(assets);

			if (autoconnect) {
				parameters.add("--server");
				parameters.add(server.get("server"));
				parameters.add("--port");
				parameters.add(server.get("port"));
			}

			parameters.add("--accessToken");
			parameters.add(username);
			parameters.add("--uuid");
			parameters.add(session);
			parameters.add("--userProperties");
			parameters.add("{}");
			
			// FIXME организовать твики
			/*
			 * params.add("--tweakClass");
			 * params.add("cpw.mods.fml.common.launcher.FMLTweaker");
			 * params.add("--tweakClass");
			 * params.add("com.mumfrey.liteloader.launch.LiteLoaderTweaker");
			 */
			
			for( String s : parameters ) {
				System.out.print( s  + " " );
			}

			ProcessBuilder pb = new ProcessBuilder( parameters );
			pb.inheritIO();
			pb.directory( new File(bin) );
			pb.start();

			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
			FatalError.showErrorWindow(e);
		}
	}

	public void startMinecraft(String clientName, String login, String session) {
		this.username = login;
		this.session = session;
		
		startMinecraft( clientName );
	}

	public void startMinecraft( String clientName, String login, String session, String server, String port ) {
		this.username = login;
		this.session = session;
		
		if( ( server != null ) & ( port != null ) ) {
			this.server.put( "server", server );
			this.server.put( "port", port );
			
			useAutoConnect( true );
		}
		
		startMinecraft( clientName );
	}
	
	public void startMinecraft( String clientName, AuthData authData ) {
		username = authData.getLogin();
		session = authData.getSession();
		
		startMinecraft( clientName );
	}

	public void startMinecraft(String clientName, AuthData authData, String server, String port) {
		username = authData.getLogin();
		session = authData.getSession();
		
		if( ( server != null ) & ( port != null ) ) {
			this.server.put( "server", server );
			this.server.put( "port", port );
			
			useAutoConnect( true );
		}
		
		startMinecraft( clientName );
	}

}
