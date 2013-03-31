package org.sinrel.engine.library;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import org.apache.commons.codec.binary.StringUtils;

public abstract class NetManager {

	/**
	 * Открывает браузером по-умолчанию принимаемою ссылку
	 * 
	 * @param uri
	 * @throws IOException
	 */
	public static void openLink(URI uri) throws IOException {
		Desktop.getDesktop().browse(uri);
	}

	/**
	 * Проверяет наличие соединения с интернетом. При имеющимся таковом
	 * возвращает true, в ином случае false
	 * 
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
			if (con != null) {
				try {
					con.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return result;
	}

	/**
	 * @param data
	 *            Строка с данными
	 * @param requestURL
	 *            Ссылка на получателя
	 * @return Возвращает строку содержащую ответ сервера. Если отправить запрос
	 *         или получить ответ не удалось, возвращается строка BAD_CONNECTION
	 * @throws IOException
	 */
	public static final String sendPostRequest(String requestURL, String data) throws IOException {
		return sendPostRequest(new URL(requestURL), data);
	}

	/**
	 * @param requestURL
	 *            Ссылка на получателя
	 * @param data
	 *            POST-данные
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

}
