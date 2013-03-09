package org.sinrel.engine.actions;

import org.sinrel.engine.Engine;
import org.sinrel.engine.library.NetManager;
import org.sinrel.engine.library.OSManager;
import org.sinrel.engine.library.cryption.MD5;
import org.sinrel.engine.listeners.CheckerListener;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

final class Checker {

    //Проверяемые файлы. Так же проверяется наличие natives под систему клиента
    static String[] files = {"jinput.jar", "lwjgl.jar", "lwjgl_util.jar", "minecraft.jar"};
    //static ArrayList<File> additionalFiles = new ArrayList<File>();

    static ArrayList<CheckerListener> listeners = new ArrayList<CheckerListener>();

    private Client clientStatus;

    Checker(String applicationName, String serverName) {
        //FIXME Now do nothing :C
    }

    Checker(String applicationName) {
        for (CheckerListener cl : listeners) {
            cl.onStartChecking();
        }

        if (!NetManager.isOnline()) {
            clientStatus = Client.BAD_CONNECTION;
            return;
        }

        if (!OSManager.getWorkingDirectory(applicationName).exists() | !OSManager.getClientFolder(applicationName).exists()) {
            clientStatus = Client.CLIENT_NOT_EXIST;
            return;
        }

        ArrayList<String> hash = new ArrayList<String>();
        File f = null;

        for (String name : files) {
            f = new File(OSManager.getClientFolder(applicationName), name);
            if (!f.exists()) {
                clientStatus = Client.WRONG_CLIENT;
                return;
            }

            hash.add(MD5.getMD5(f));
        }

        StringBuilder sb = new StringBuilder();
        for (String s : hash) {
            sb.append(s);
        }

        switch (send("standart", sb.toString(), OSManager.getPlatform())) {
            case BAD_CONNECTION:
                onFinish();
                clientStatus = Client.BAD_CONNECTION;
                return;

            case CLIENT_DOES_NOT_MATCH:
                onFinish();
                clientStatus = Client.CLIENT_DOES_NOT_MATCH;
                return;

            case CLIENT_NOT_EXIST:
                onFinish();
                clientStatus = Client.CLIENT_NOT_EXIST;
                return;

            case CLIENT_NOT_EXIST_ON_SERVER:
                onFinish();
                clientStatus = Client.CLIENT_NOT_EXIST_ON_SERVER;
                return;

            case OK:
                onFinish();
                clientStatus = Client.OK;
                return;

            case WRONG_CLIENT:
                onFinish();
                clientStatus = Client.WRONG_CLIENT;
                return;
        }
    }

    Client getClientStatus() {
        return clientStatus;
    }

    private static Client send(String client, String hash, OSManager.OS system) {
        try {
            StringBuilder data = new StringBuilder(URLEncoder.encode("action", "UTF-8") + "=" + URLEncoder.encode("check", "UTF-8"));

            data.append("&" + URLEncoder.encode("client", "UTF-8") + "=" + URLEncoder.encode(client, "UTF-8"));
            data.append("&" + URLEncoder.encode("hash", "UTF-8") + "=" + URLEncoder.encode(hash, "UTF-8"));
            data.append("&" + URLEncoder.encode("system", "UTF-8") + "=" + URLEncoder.encode(system.toString(), "UTF-8"));

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

            if (Client.valueOf(answer) != null) {
                return Client.valueOf(answer);
            } else {
                return Client.BAD_CONNECTION;
            }
        } catch (IOException e) {
            return Client.BAD_CONNECTION;
        }
    }

    private static void onFinish() {
        for (CheckerListener cl : listeners) {
            cl.onFinishChecking();
        }
    }

}
