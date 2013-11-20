//FIXME Изменить порядок проверки клиента

package org.sinrel.engine.actions;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.sinrel.engine.Engine;
import org.sinrel.engine.library.NetManager;
import org.sinrel.engine.library.OSManager;
import org.sinrel.engine.library.OSManager.OS;
import org.sinrel.engine.library.cryption.MD5;

public class DefaultChecker extends Checker {

	// Проверяемые файлы. Так же проверяется наличие natives под систему клиента
	static String[] files = { "jinput.jar", "lwjgl.jar", "lwjgl_util.jar", "minecraft.jar" };

	private String clientName, folder;
	
	public DefaultChecker( Engine engine ) {
		super( engine );
	}
	
	public ClientStatus checkClient( String clientName ) {
		try {
			clientName = clientName.toLowerCase();
			String applicationName = engine.getSettings().getDirectory();

			if (!engine.isOnline()) {
				return ClientStatus.BAD_CONNECTION;
			}

			if (!OSManager.getWorkingDirectory(applicationName).exists() | !OSManager.getClientFolder(applicationName , clientName).exists()) {
				return ClientStatus.CLIENT_NOT_EXIST;
			}

			StringBuffer hash = new StringBuffer();
			File f = null;
			
			for (String name : files) {
				f = new File( OSManager.getClientFolder(applicationName, clientName ), name);
				if (!f.exists()) {
					return ClientStatus.WRONG_CLIENT;
				}

				hash.append( MD5.getMD5(f) );
			}
			
			return send( clientName , hash.toString(), OSManager.getPlatform() );
			
		} catch ( IOException ex ) {
			ex.printStackTrace();
			return ClientStatus.CLIENT_DOES_NOT_MATCH;
		}
	}

	private ClientStatus send( String client, String hash, OS system ) {
		try {
			String data = "action=" + URLEncoder.encode("check", "UTF-8");
			data += "&client=" + URLEncoder.encode(client, "UTF-8");
			data += "&hash=" + URLEncoder.encode(hash, "UTF-8");
			data += "&system=" + URLEncoder.encode(system.toString(), "UTF-8");

			String s = NetManager.sendPostRequest( NetManager.getEngineLink( engine ) , data);
			
			return ClientStatus.valueOf(s.trim());
		} catch (IOException e) {
			e.printStackTrace();
			return ClientStatus.BAD_CONNECTION;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return ClientStatus.BAD_CONNECTION;
		}
	}
				
	public DirectoryStatus checkMods( String clientName ) {
		this.clientName = clientName;
		folder = "mods";
		
		try {
			File directory = new File( OSManager.getWorkingDirectory(engine) + File.separator + clientName, folder );
			String data;
						
			if( !directory.exists() ) {
				data = generateData( "not_exist" );	
				String answer = NetManager.sendPostRequest(  NetManager.getEngineLink( engine ) , data );
				
				if( engine.isDebug() )
					System.out.println( answer );	
				
				if ( answer.equals( "OK" ) ) 
					return DirectoryStatus.OK;
				else
					if( answer.equals( "DIRECTORY_DOES_NOT_MATCH" ) )
						return DirectoryStatus.DIRECTORY_DOES_NOT_MATCH;
			}else{
				ArrayList< File > files = new ArrayList< File >();
				
				for ( File f : directory.listFiles() ) {					
					if( f.isFile() ) {
						files.add( f );
					}
				}
				
				String answer = NetManager.sendPostRequest(  NetManager.getEngineLink( engine ) , generateData( "count", Integer.toString( files.size() ) ) );

				if( engine.isDebug() )
					System.out.println( answer );
				
				if( answer.contains( "DIRECTORY_DOES_NOT_MATCH" ) )
					return DirectoryStatus.DIRECTORY_DOES_NOT_MATCH;
				
				//TODO сделать сборку хеша и сверку с сгенерированным на сервере
				
				return DirectoryStatus.OK;
 			}	
		}catch( Exception e ) {
			e.printStackTrace();
			return DirectoryStatus.BAD_CONNECTION;
		}
		
		return DirectoryStatus.DIRECTORY_DOES_NOT_MATCH;
	}

	@Deprecated
	public DirectoryStatus checkDirectory( String clientName, String directoryName ) {
		//TODO Реализовать метод
		return null;
	}
	
	private String generateData( String data ) {
		return "action=directory&folder=" + this.folder + "&client=" + this.clientName + "&data=" + data;
	}
	
	private String generateData( String data, String option ) {
		return "action=directory&folder=" + this.folder + "&client=" + this.clientName + "&data=" + data + "&option=" + option;
	}
	
}
