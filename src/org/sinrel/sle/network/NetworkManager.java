package org.sinrel.sle.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.StringUtils;
import org.sinrel.sle.Engine;

//TODO need in refactor
public final class NetworkManager {

	public static String sendGetRequest(URL requestURL, String data)
			throws IOException {
		byte[] bytes = StringUtils.getBytesUtf8(data);
		HttpURLConnection conn = (HttpURLConnection) requestURL
				.openConnection();

		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length",
				Integer.toString(bytes.length));

		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);

		conn.connect();

		OutputStream wr = conn.getOutputStream();
		wr.write(bytes);
		wr.flush();
		wr.close();

		StringBuffer s = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), "UTF-8"));
		String line;

		while ((line = reader.readLine()) != null) {
			s.append(line);
		}
		reader.close();

		if (s == null | s.equals(""))
			return "BAD_CONNECTION";

		return s.toString();
	}

	/**
	 * @param requestUrl
	 *            Ссылка на получателя
	 * @param command
	 *            имя команды
	 * @return Возвращает строку содержащую ответ сервера. Если отправить запрос
	 *         или получить ответ не удалось, возвращается строка BAD_CONNECTION
	 * @throws IOException
	 */
	public static String sendRequest(String requestUrl, Command command)
			throws IOException {
		return sendRequest(requestUrl, command, null);
	}

	/**
	 * @param requestUrl
	 *            Ссылка на получателя
	 * @param command
	 *            имя команды
	 * @param карта
	 *            дополнительных параметров
	 * @return Возвращает строку содержащую ответ сервера. Если отправить запрос
	 *         или получить ответ не удалось, возвращается строка BAD_CONNECTION
	 * @throws IOException
	 */
	public static String sendRequest(String requestUrl, Command command,
			Map<String, String> args) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("command=" + command.getValue() + "&");
		if (args != null) {
			for (String key : args.keySet()) {
				sb.append(key + "=" + args.get(key) + "&");
			}
		}
		return sendPostRequest(requestUrl, sb.toString());
	}

	public static List<String> sendRequestToAllServers(Engine engine,
			String requestURL, Command command) throws MalformedURLException,
			IOException {
		return sendRequestToAllServers(engine, requestURL, command, null);
	}

	public static List<String> sendRequestToAllServers(Engine engine,
			String requestURL, Command command, Map<String, String> args)
			throws MalformedURLException, IOException {
		
		List<String> answers = new ArrayList<String>();
		for (MirrorServer mirror : engine.getMirrorsServers()) {
			String answer = NetworkManager.sendRequest(mirror.getEngineLink(),
					command, args).trim();
			answers.add(answer);
		}
		return answers;
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
	private static String sendPostRequest(String requestURL, String data)
			throws IOException {
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
	public static String sendPostRequest(URL requestURL, String data)
			throws IOException {
		byte[] bytes = StringUtils.getBytesUtf8(data);

		HttpURLConnection conn = (HttpURLConnection) requestURL
				.openConnection();
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type",
				"application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length",
				Integer.toString(bytes.length));

		conn.setUseCaches(false);
		conn.setDoInput(true);
		conn.setDoOutput(true);

		conn.connect();

		OutputStream wr = conn.getOutputStream();
		wr.write(bytes);
		wr.flush();
		wr.close();

		StringBuffer s = new StringBuffer();
		BufferedReader reader = new BufferedReader(new InputStreamReader(
				conn.getInputStream(), "UTF-8"));
		String line;

		while ((line = reader.readLine()) != null) {
			s.append(line);
		}
		reader.close();

		if (s == null | s.equals(""))
			return "BAD_CONNECTION";

		return s.toString();
	}

	/**
	 * @param engine
	 *            Экземпляр класса {@link Engine}
	 * @return Возвращает ссылку на engine.php
	 * @throws MalformedURLException
	 */
	public static String getEngineLink(Engine engine)
			throws MalformedURLException {
		return new MirrorServer(engine.getSettings().getDomain(), engine
				.getSettings().getServerPath()).getEngineLink();
	}

	/**
	 * @param engine
	 *            Экземпляр класса {@link Engine}
	 * @return Возвращает ссылку на директорию с engine.php
	 * @throws MalformedURLException
	 */
	@Deprecated
	public static String getServerLink(Engine engine)
			throws MalformedURLException {
		return new MirrorServer(engine.getSettings().getDomain(), engine
				.getSettings().getServerPath()).getServerLink();
	}

	/**
	 * @param engine
	 *            Экземпляр класса {@link Engine}
	 * @param clientName
	 *            Название клиента
	 * @throws MalformedURLException
	 */
	@Deprecated
	public static URL getClientBinaryLink(Engine engine, String clientName)
			throws MalformedURLException {
		return new MirrorServer(engine.getSettings().getDomain(), engine
				.getSettings().getServerPath()).getClientBinaryLink(clientName);
	}

}
