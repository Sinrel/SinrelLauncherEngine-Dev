//FIXME Изменить порядок проверки клиента

package org.sinrel.sle.actions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.codec.digest.DigestUtils;
import org.sinrel.sle.Engine;
import org.sinrel.sle.Engine.OS;
import org.sinrel.sle.network.Command;
import org.sinrel.sle.network.NetworkManager;

public class DefaultChecker extends Checker {

	// Проверяемые файлы. Так же проверяется наличие natives под систему клиента
	static String[] files = { "forge.jar", "libraries.jar", "extra.jar",
			"minecraft.jar" };

	private String clientName, folder;

	public DefaultChecker(Engine engine) {
		super(engine);
	}

	public ClientStatus checkClient(String clientName) {
		try {
			clientName = clientName.toLowerCase();

			if (!engine.isOnline()) {
				return ClientStatus.BAD_CONNECTION;
			}

			if (!engine.getDirectory().exists()
					| !engine.getClientBinaryFolder(clientName).exists()) {
				return ClientStatus.CLIENT_NOT_EXIST;
			}

			StringBuffer hash = new StringBuffer();
			File f = null;

			for (String name : files) {
				f = new File(engine.getClientBinaryFolder(clientName), name);
				if (!f.exists()) {
					return ClientStatus.WRONG_CLIENT;
				}

				hash.append(getFileMD5(f));
			}

			return send(clientName, hash.toString(), engine.getPlatform());

		} catch (IOException ex) {
			ex.printStackTrace();
			return ClientStatus.CLIENT_DOES_NOT_MATCH;
		}
	}

	private ClientStatus send(String client, String hash, OS system) {
		try {
			Map<String, String> args = new HashMap<String, String>();
			args.put("client", URLEncoder.encode(client, "UTF-8"));
			args.put("hash", URLEncoder.encode(hash, "UTF-8"));
			args.put("system", URLEncoder.encode(system.toString(), "UTF-8"));

			String s = NetworkManager.sendRequest(
					NetworkManager.getEngineLink(engine), Command.CHECK, args);

			return ClientStatus.valueOf(s.trim());
		} catch (IOException e) {
			e.printStackTrace();
			return ClientStatus.BAD_CONNECTION;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return ClientStatus.BAD_CONNECTION;
		}
	}

	public DirectoryStatus checkMods(String clientName) {
		this.clientName = clientName;
		folder = "mods";

		try {
			File directory = new File(engine.getDirectory() + File.separator
					+ clientName, folder);
			Map<String, String> data;

			if (!directory.exists()) {
				data = generateData("not_exist");
				String answer = NetworkManager.sendRequest(
						NetworkManager.getEngineLink(engine),
						Command.DIRECTORY, data);

				if (engine.isDebug())
					System.out.println(answer);

				if (answer.equals("OK"))
					return DirectoryStatus.OK;
				else if (answer.equals("DIRECTORY_DOES_NOT_MATCH"))
					return DirectoryStatus.DIRECTORY_DOES_NOT_MATCH;
			} else {
				ArrayList<File> files = new ArrayList<File>();

				for (File f : directory.listFiles()) {
					if (f.isFile()) {
						files.add(f);
					}
				}

				String answer = NetworkManager.sendRequest(
						NetworkManager.getEngineLink(engine),
						Command.DIRECTORY,
						generateData("count", Integer.toString(files.size())));

				if (engine.isDebug())
					System.out.println(answer);

				if (answer.contains("DIRECTORY_DOES_NOT_MATCH"))
					return DirectoryStatus.DIRECTORY_DOES_NOT_MATCH;

				// TODO сделать сборку хеша и сверку с сгенерированным на
				// сервере

				return DirectoryStatus.OK;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return DirectoryStatus.BAD_CONNECTION;
		}

		return DirectoryStatus.DIRECTORY_DOES_NOT_MATCH;
	}

	private String getFileMD5(File file) throws IOException {
		FileInputStream fis = new FileInputStream(file);

		return DigestUtils.md5Hex(fis);
	}

	@Deprecated
	public DirectoryStatus checkDirectory(String clientName,
			String directoryName) {
		// TODO Реализовать метод
		return null;
	}

	private Map<String, String> generateData(String data) {
		Map<String, String> args = new HashMap<String, String>();
		args.put("folder", this.folder);
		args.put("client", this.clientName);
		args.put("data", data);
		return args;
	}

	private Map<String, String> generateData(String data, String option) {
		Map<String, String> args = generateData(data);
		args.put("option", option);
		return args;
	}

}
