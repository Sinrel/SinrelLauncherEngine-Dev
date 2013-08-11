package org.sinrel.engine.actions;

import java.awt.Desktop;
import java.io.IOException;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URI;
import java.net.UnknownHostException;

import org.sinrel.engine.Engine;
import org.sinrel.engine.library.NetManager;

public class DefaultManager extends Manager {
	
	public DefaultManager( Engine engine ) {
		super( engine );
	}

	public boolean isDuplicated() {
		try {
			new ServerSocket( 60_000 );
			return false;
		} catch ( IOException e ) {
			return true;
		}
	}
	
	public boolean isOnline() {
		try {
			InetAddress.getByName("sinrel.org");
			return true;
	       }catch( UnknownHostException e ) {
	    	   try {
	    		   InetAddress.getByName("google.com");
	    		   return true;
	    	   }catch( UnknownHostException ex ) {
	    		   return false;   
	    	   }
		   }
	}

	public float getJavaVersion() {
		String version = System.getProperty( "java.version" );
		
		return Float.parseFloat( version.substring( 0, 3 ) );
	}
	
	public void openLink(URI uri) throws IOException {
		Desktop.getDesktop().browse(uri);
	}
	
	public LauncherData getLauncherData() {
		LauncherData laun = null;

		try{
			String answer = NetManager.sendPostRequest( NetManager.getEngineLink( engine ), "action=launcher");
			
			if( engine.isDebug() )
				System.out.println( answer );
			
			laun = new LauncherData( engine, Integer.parseInt( answer.split("<:>")[0] ) , answer.split("<:>")[1] );
			
			return laun;
		}catch( MalformedURLException e ) {
			e.printStackTrace();
		}catch( IOException e ) {
			e.printStackTrace();
		}
		
		return laun;
	}
	
}
