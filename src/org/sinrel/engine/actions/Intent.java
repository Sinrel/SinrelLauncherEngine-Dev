package org.sinrel.engine.actions;

import org.sinrel.engine.Engine;
import org.sinrel.engine.library.cryption.Base64;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public abstract class Intent {

    @SuppressWarnings("unused")
    private static String session;

    public final static void Do(Action a) {
        switch (a) {
            case ENABLE:
                Engine.getLauncher().onEnable();
                break;
            case DISABLE:
                Engine.getLauncher().onDisable();
                break;
        }
    }

    /**
     * Старт скачивания клиента "standart"
     *
     * @param loadZip Загружать ли client.zip
     * @param dir     Имя папки в которой находится клиент ( пример: minecraft )
     * @return Возвращает одно из значений Download
     */
    public static final Download DoDownload(String dir, boolean loadZip) {
        return new Downloader(dir, loadZip).getAnswer();
    }

    public static final Auth DoAuth(String login, String pass) {
        try {
            StringBuilder data = new StringBuilder(URLEncoder.encode("action", "UTF-8") + "=" + URLEncoder.encode("auth", "UTF-8"));
            data.append("&" + URLEncoder.encode("login", "UTF-8") + "=" + URLEncoder.encode(Base64.encode(Base64.encode(login)), "UTF-8"));
            data.append("&" + URLEncoder.encode("pass", "UTF-8") + "=" + URLEncoder.encode(Base64.encode(Base64.encode(pass)), "UTF-8"));

            URL url;

            if (!Engine.getDescriptionFile().get("folder").equalsIgnoreCase("")) {
                url = new URL(
                        "http://" + Engine.getDescriptionFile().get("domain") + "/" + Engine.getDescriptionFile().get("folder") + "/" + "engine.php"
                );
            } else {
                url = new URL(
                        "http://" + Engine.getDescriptionFile().get("domain") + "/" + "engine.php"
                );
            }

            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

            writer.write(data.toString());
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

            if (answer.contains("OK")) {
                session = answer.split("<:>")[1];

                return Auth.OK;
            }

            if (Auth.isMember(answer)) {
                return Auth.valueOf(answer);
            } else {
                return Auth.BAD_CONNECTION;
            }

        } catch (IOException e) {
            return Auth.BAD_CONNECTION;
        }
    }

    public static final Client DoCheckClient(String applicationName, String serverName) {
        return new Checker(applicationName, serverName).getClientStatus();
    }

    public static final Client DoCheckClient(String applicationName) {
        return new Checker(applicationName).getClientStatus();
    }
}
