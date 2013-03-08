package org.sinrel.engine.library;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;

public abstract class NetManager {
	
	/**
	 * Открывает браузером по-умолчанию принимаемою ссылку
	 * @param uri
	 */
	public static void openLink( URI uri ) {
		try {
			Desktop.getDesktop().browse( uri );	
		} catch ( IOException e ) {
			System.err.println("Не удалось открыть ссылку " + uri.toString());
		}
	}
	
	/**
	 * Проверяет наличие соединения с интернетом. При имеющимся таковом возвращает true, в ином случае false
	 * @return boolean
	 */
	public static boolean isOnline() {
		boolean result = false;
		HttpURLConnection con = null;
		
		try {
			con = (HttpURLConnection) new URL("http://sinrel.org").openConnection();
			con.setRequestMethod("HEAD");
			result = (con.getResponseCode() == HttpURLConnection.HTTP_OK);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if ( con != null ) {
				try {
					con.disconnect();
				} catch ( Exception e ) {
					e.printStackTrace();
				}
			}
		}
		
		return result;
	}
	
	/**
	 * @param data Строка с данными
	 * @param requestURL Ссылка на получателя
	 * @return Возвращает строку содержащую ответ сервера. Если отправить запрос или получить ответ не удалось, возвращается строка BAD_CONNECTION 
	 */
	public static final String sendPostRequest( String data, String requestURL ) {
	     try {	    	 	 		 
	          URL url; 
	          
	          url = new URL( requestURL );
	          	          	
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
	          
	          return s.toString();	  
	          
	     }catch ( IOException e ) {
	    	 return "BAD_CONNECTION";
	     }
	}
	
}
