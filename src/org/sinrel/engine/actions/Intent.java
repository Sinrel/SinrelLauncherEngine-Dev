package org.sinrel.engine.actions;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import org.sinrel.engine.Engine;
import org.sinrel.engine.library.cryption.Base64;

public class Intent {
	
	private Engine engine;
	private Downloader downloader;	
	private ClientChecker checker;
	
	public Intent(Engine engine) {
		this.engine = engine;
		downloader = new DefaultDownloader(engine);
		checker = new DefaultChecker(engine);
	}

	/**
	 * Старт скачивания клиента "standart"
	 * @param loadZip Загружать ли client.zip
	 * @param dir Имя папки в которой находится клиент ( пример: minecraft )
	 * @return Возвращает одно из значений DownloadResult
	 */
	public DownloadResult downloadClient( String dir  , boolean loadZip ) {
		return downloader.downloadClient(dir, loadZip);
	}
	
	public AuthData auth( String login , String pass ) {
		AuthData ret = new AuthData(login, null, AuthResult.BAD_CONNECTION); 
	     try {	    	
	    	  StringBuilder data = new StringBuilder( URLEncoder.encode("action", "UTF-8") + "=" + URLEncoder.encode("auth", "UTF-8") );
	 		  data.append( "&" + URLEncoder.encode("login", "UTF-8") + "=" + URLEncoder.encode( Base64.encode( Base64.encode( login ) ) , "UTF-8") );
	 		  data.append( "&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode( Base64.encode( Base64.encode( pass ) ), "UTF-8") );
	 		  
	          URL url; 
	          
	          System.out.println(engine == null);
	          
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
	          
	          if(engine.isDebug()){
	        	  System.out.println(answer);
	          }
	          
	          if( answer.contains("OK") ) {
	        	  ret.setSession(answer.split("<:>")[1]);
	        	  
	        	  ret.setResult(AuthResult.OK);
	        	  return ret;
	          }
	          
	          switch ( answer ) {
	           case "BAD_CONNECTION":
	        	   ret.setResult(AuthResult.BAD_CONNECTION);
	        	   
	           case "LOGIN_OR_PASS_NOT_EXIST":
	        	   ret.setResult(AuthResult.LOGIN_OR_PASS_NOT_EXIST);
	        	   
	           case "BAD_LOGIN_OR_PASSWORD":
	        	   ret.setResult(AuthResult.BAD_LOGIN_OR_PASSWORD);
	          }
	     }catch (IOException e) {
	 		ret.setResult(AuthResult.BAD_CONNECTION);
	     }
	     
	     return ret;
	}
		
	public ClientStatus checkClient( String applicationName ) {
		return checker.checkClient(applicationName);
	}
	
}
