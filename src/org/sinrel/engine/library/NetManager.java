package org.sinrel.engine.library;

import java.awt.Desktop;
import java.io.IOException;
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
	
}
