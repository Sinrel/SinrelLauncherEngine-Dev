package org.sinrel.engine.library;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.UnknownHostException;

import org.apache.commons.codec.binary.StringUtils;
import org.sinrel.engine.Engine;

public final class NetManager {

	/**
	 * Открывает браузером по-умолчанию принимаемою ссылку
	 * 
	 * @param uri обьект класса {@link URI} содержащий ссылку для перехода 
	 * @throws IOException
	 */
	public static final void openLink(URI uri) throws IOException {
		Desktop.getDesktop().browse(uri);
	}
	
	/**
	 * Открывает браузером по-умолчанию принимаемою ссылку
	 * 
	 * @param address строка содержащая ссылку для перехода
	 * @throws IOException
	 */
	public static final void openLink( String address ) throws MalformedURLException, IOException, URISyntaxException {
		Desktop.getDesktop().browse( new URL ( address ).toURI() );
	}

	/**
	 * Проверяет наличие соединения с интернетом. При имеющимся таковом
	 * возвращает true, в ином случае false
	 * 
	 * @return boolean
	 */
	public static final boolean isOnline() {
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

	/**
	 * @param data Строка с данными
	 * @param requestURL Ссылка на получателя
	 * @return Возвращает строку содержащую ответ сервера. Если отправить запрос
	 *         или получить ответ не удалось, возвращается строка BAD_CONNECTION
	 * @throws IOException
	 */
	public static final String sendPostRequest(String requestURL, String data) throws IOException {
		return sendPostRequest(new URL(requestURL), data);
	}

	/**
	 * @param requestURL Ссылка на получателя
	 * @param data POST-данные
	 * @return Возвращает строку содержащую ответ сервера. Если отправить запрос
	 *         или получить ответ не удалось, возвращается строка BAD_CONNECTION
	 * @throws IOException
	 */
	public static final String sendPostRequest(URL requestURL, String data) throws IOException {
		byte[] bytes = StringUtils.getBytesUtf8(data);

		HttpURLConnection conn = (HttpURLConnection) requestURL.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", Integer.toString(bytes.length));

		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);

		conn.connect();

		OutputStream wr = conn.getOutputStream();
		wr.write(bytes);
		wr.flush();
		wr.close();

		StringBuffer s = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		String line;

		while ((line = reader.readLine()) != null) {
			s.append(line);
		}
		reader.close();

		return s.toString();
	}
	
	/**
	 * @param engine Экземпляр класса {@link Engine}
	 * @return Возвращает ссылку на engine.php
	 * @throws MalformedURLException 
	 */
	public static final String getEngineLink( Engine engine ) throws MalformedURLException {
		URL url;

		if (!engine.getSettings().getServerPath().equalsIgnoreCase("")) {
			url = new URL("http://" + engine.getSettings().getDomain()
					+ "/" + engine.getSettings().getServerPath() + "/"
					+ "engine.php");
		} else {
			url = new URL("http://" + engine.getSettings().getDomain()
					+ "/" + "engine.php");
		}
		
		return url.toString();
	}
	
	/**
	 * @param engine Экземпляр класса {@link Engine}
	 * @return Возвращает ссылку на директорию с engine.php
	 * @throws MalformedURLException
	 */
	public static final String getServerLink( Engine engine ) throws MalformedURLException {
		URL url;

		if (!engine.getSettings().getServerPath().equalsIgnoreCase("")) {
			url = new URL("http://" + engine.getSettings().getDomain() + "/" + engine.getSettings().getServerPath() + "/");
		} else {
			url = new URL("http://" + engine.getSettings().getDomain() + "/");
		}
		
		return url.toString();
	}
	
	/**
	 * @param engine Экземпляр класса {@link Engine}
	 * @param clientName Название клиента
	 * @throws MalformedURLException
	 */
	public static final URL getClientBinaryLink( Engine engine, String clientName ) throws MalformedURLException {
		URL url = new URL( getServerLink( engine ).concat( "clients/".concat( clientName.concat( "/bin/" ) ) ) );
		return url;
	}
	
}
