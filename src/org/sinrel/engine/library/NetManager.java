package org.sinrel.engine.library;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

public abstract class NetManager {
	
	/**
	 * Открывает браузером по-умолчанию принимаемою ссылку
	 * @param uri
	 */
	public static void openLink( URI uri ) {
		try {
			Object o = Class.forName( "java.awt.Desktop" )		
					.getMethod("getDesktop", new Class[0])
					.invoke(null, new Object[0]);
			
			o.getClass()
				.getMethod("browse", new Class[] { URI.class })
			    .invoke(o, new Object[] { uri });
			
		} catch (Throwable e) {
			System.err.println("Не удалось открыть ссылку " + uri.toString());
			e.printStackTrace();
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
	
}
