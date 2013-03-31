package org.sinrel.engine.actions;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.sinrel.engine.Engine;
import org.sinrel.engine.library.NetManager;

public class DefaultLauncherChecker extends LauncherChecker {	
	
	public LauncherData getLauncherData( Engine engine ) {
		
		LauncherData laun = null;
		
		try{
			URL url;

			if (!engine.getSettings().getServerPath().equalsIgnoreCase("")) {
				url = new URL("http://" + engine.getSettings().getDomain()
						+ "/" + engine.getSettings().getServerPath() + "/"
						+ "engine.php");
			} else {
				url = new URL("http://" + engine.getSettings().getDomain()
						+ "/" + "engine.php");
			}

			String answer = NetManager.sendPostRequest( url, "action=launcher");
			
			if( engine.isDebug() )
				System.out.println( answer );
			
			laun = new LauncherData( Integer.parseInt( answer.split("<:>")[0] ) , answer.split("<:>")[1] );
			
			return laun;
		}catch( MalformedURLException e ) {
			e.printStackTrace();
		}catch( IOException e ) {
			e.printStackTrace();
		}
		
		return laun;
	}
	
}
