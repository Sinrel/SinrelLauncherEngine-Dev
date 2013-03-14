package org.sinrel.engine.actions;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.sinrel.engine.Engine;
import org.sinrel.engine.library.NetManager;
import org.sinrel.engine.library.OSManager;
import org.sinrel.engine.library.cryption.MD5;
import org.sinrel.engine.listeners.CheckerListener;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class DefaultChecker extends ClientChecker{
	
	//Проверяемые файлы. Так же проверяется наличие natives под систему клиента
	static String[] files = { "jinput.jar" , "lwjgl.jar" , "lwjgl_util.jar" , "minecraft.jar" };
	
	private Engine engine;
	
	public ClientStatus checkClient(String applicationName, String serverName){
		throw new NotImplementedException();
	}
	
	public ClientStatus checkClient(Engine e, String applicationName) {
		
		this.engine = e;
		
		this.onStartChecking();
		
		if( !NetManager.isOnline() ) {
			return ClientStatus.BAD_CONNECTION;
		}
		
		if( !OSManager.getWorkingDirectory( applicationName ).exists() | !OSManager.getClientFolder( applicationName ).exists() ) {
			return ClientStatus.CLIENT_NOT_EXIST;
		}
		
		ArrayList<String> hash = new ArrayList<String>();
		File f = null;
		
		for ( String name : files ) {
			f = new File( OSManager.getClientFolder( applicationName ) , name );
			if( !f.exists() ) {
				return ClientStatus.WRONG_CLIENT;
			}
			
			hash.add( MD5.getMD5( f ) );
		}
		
		StringBuilder sb = new StringBuilder();
		for( String s : hash ) {
			sb.append( s );
		}
		
		this.onFinishChecking();
		return send( "standart", sb.toString() , OSManager.getPlatform() );
	}
	
	
	private ClientStatus send( String client, String hash , OSManager.OS system ) {
	     try {	    	 
	    	  StringBuilder data = new StringBuilder( URLEncoder.encode("action", "UTF-8") + "=" + URLEncoder.encode("check", "UTF-8") );
	 		  
	    	  data.append( "&" + URLEncoder.encode("client", "UTF-8") + "=" + URLEncoder.encode( client , "UTF-8" ) );
	 		  data.append( "&" + URLEncoder.encode("hash", "UTF-8") + "=" + URLEncoder.encode( hash , "UTF-8") );
	 		  data.append( "&" + URLEncoder.encode("system", "UTF-8") + "=" + URLEncoder.encode( system.toString() , "UTF-8") );
	 		 
	          URL url; 
	          
	          if( !engine.getSettings().getFolder().equalsIgnoreCase("") ) {
	        	  url =	new URL( 
	        			  "http://" + engine.getSettings().getDomain() + "/" +  engine.getSettings().getFolder() + "/" + "engine.php" 
	        	  );
	          }else {
	        	  url =	new URL( 
	        			  "http://" + engine.getSettings().getDomain() + "/" + "engine.php" 
	        	  );
	          }
	          	
	          URLConnection conn = url.openConnection();
	          conn.setDoOutput(true);
	          OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

	          writer.write( data.toString() );
	          writer.flush();
	          
	          StringBuffer s = new StringBuffer();
	          BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	          String line;
	          
	          while ((line = reader.readLine()) != null) {
	              s.append(line);
	          }
	          writer.close();
	          reader.close();
	          
	          String answer = s.toString();	  
	          	          
	          switch ( answer ) {
	          	case "BAD_CONNECTION" :
	          		return ClientStatus.BAD_CONNECTION;
	          	case "CLIENT_DOES_NOT_MATCH":
	          		return ClientStatus.CLIENT_DOES_NOT_MATCH;
	          	
	          	case "CLIENT_NOT_EXITST":
	          		return ClientStatus.CLIENT_NOT_EXIST;
	          	
	          	case "CLIENT_NOT_EXIST_ON_SERVER":
	          		return ClientStatus.CLIENT_NOT_EXIST_ON_SERVER;
	          	
	          	case "WRONG_CLIENT":
	          		return ClientStatus.WRONG_CLIENT;
	          	
	          	case "OK":
	          		return ClientStatus.OK;
	          }
	          
	     }catch (IOException e) {
	 		return ClientStatus.BAD_CONNECTION;
	     }
	     
	     return ClientStatus.BAD_CONNECTION;
	}
	
	
}
