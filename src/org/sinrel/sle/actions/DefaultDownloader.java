package org.sinrel.sle.actions;   

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Path;
import java.util.ArrayList;

import org.sinrel.sle.Engine;
import org.sinrel.sle.library.ArchiveManager;
import org.sinrel.sle.network.NetworkManager;

import com.google.gson.JsonElement;

/**
 * 
 * @author Sinrel Group
 * @since SLE 2.0
 * @version 2.5
 */
public class DefaultDownloader extends Downloader {
	
	private Path root; //root folder of client hierarchy
		
	//TODO Old code below.
		
	private ArrayList<String> basic = new ArrayList<String>(),
			archives = new ArrayList<String>();

	private DownloadEvent event = new DownloadEvent();

	private String clientName;
	private ArrayList<String> temp = new ArrayList<String>();

	private static final int BUFFER_SIZE = 1024;
	
	private CurrentFile currentFile = event.getCurrentFile();
	
	public DefaultDownloader(Engine engine) {
		super(engine);

		basic.add("forge.jar");
		basic.add("libraries.jar");
		basic.add("minecraft.jar");
		basic.add("extra.jar");

		archives.add("client.zip");
		archives.add("assets.zip");
		archives.add("natives/" + engine.getPlatform().toString() + ".zip");
	}

	public DownloadResult downloadClient(String clientName) {
		this.clientName = clientName;

		temp.clear();
		temp.addAll(basic);
		temp.addAll(additionalFiles);
		temp.addAll(archives);
		temp.addAll(additionalArchives);

		initEvent();

		for (String s : temp) {
			if (!fileExist(getFileAddress(s))) {
				System.err.println("Файл не найден: " + getFileAddress(s));
				return DownloadResult.FILE_NOT_EXIST;
			}
		}

		onStartDownload(event);

		delete(new File(engine.getDirectory(), clientName));

		for (int num = 0; num < temp.size(); num++) {
			String filename = temp.get(num);

			currentFile.setAddress( getFileAddress(filename) );
			currentFile.setFilename( filename );
			currentFile.setNumber( num );
			currentFile.setSize( getFileSizeKB(filename) );
			
			if (num != temp.size() - 1) {
				event.setNextFileAddress(getFileAddress(temp.get(num + 1)));
				event.setNextFileName(temp.get(num + 1));
				event.setNextFileSize(getFileSizeKB(temp.get(num + 1)));
			} else {
				event.setNextFileAddress(null);
				event.setNextFileName(null);
				event.setNextFileSize(-1);
			}

			try {
				download( currentFile.getAddress(),
						new File(engine.getClientBinaryFolder(clientName),
								currentFile.getFilename() ) );
			} catch (IOException ex) {
				return DownloadResult.FILE_NOT_EXIST;
			}

			onFileChange(event);
		}

		File to = new File(engine.getDirectory() + File.separator + clientName
				+ File.separator), from = engine
				.getClientBinaryFolder(clientName);

		unpack(from, to);

		// TODO распаковка

		ArchiveManager.removeAllArchiveFiles(from);
		// TODO check stroke ArchiveManager.removeAllArchiveFiles( new File(
		// engine.getClientBinaryFolder( clientName ), "natives") );

		return DownloadResult.OK;
	}

	private void unpack(File from, File to) {
		String archiveWithNatives = "natives/"
				+ engine.getPlatform().toString() + ".zip";

		for (String archive : archives) {
			if (archive.equalsIgnoreCase(archiveWithNatives)) {
				ArchiveManager.unpack(new File(from, archive),
						new File(engine.getClientBinaryFolder(clientName),
								"natives"));
			} else if (archive.equalsIgnoreCase("assets.zip")) {
				ArchiveManager.unpack(new File(from, archive),
						new File(engine.getClientDirectory(clientName),
								"assets"));
			} else
				ArchiveManager.unpack(new File(from, archive), to);
		}
	}

	private void initEvent() {
		event = new DownloadEvent();
		
		currentFile.setAddress( getFileAddress(basic.get(0)) );
		currentFile.setFilename( basic.get(0) );
		currentFile.setNumber( 0 );
		currentFile.setSize( getFileSizeKB(basic.get(0)) );
		currentFile.setPercents(0);
		
		event.setFilesAmount(temp.size() - 1);
		event.setTotalSize(getTotalSize());
		event.setDownloadSpeed(0);

		event.setNextFileAddress(getFileAddress(basic.get(1)));
		event.setNextFileName(basic.get(1));
	}

	private boolean fileExist(URL address) {
		try {
			HttpURLConnection con = (HttpURLConnection) address.openConnection();
			if (con.getResponseCode() != 200) {
				return false;
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	private int getTotalSize() {
		int sum = 0;

		for (String s : basic) {
			sum += getFileSizeKB(s);
		}

		for (String s : archives) {
			sum += getFileSizeKB(s);
		}

		return sum;
	}

	private int getFileSizeKB(String filename) {
		try {
			URLConnection connection = getFileAddress(filename).openConnection();
			
			return connection.getContentLength() / 1024;
		} catch (IOException e) {
			return 0;
		}
	}

	private URL getFileAddress(String filename) {
		try {
			return new URL(NetworkManager.getServerLink(this.engine).concat(
					"clients/".concat(this.clientName.concat("/bin/"
							.concat(filename)))));
		} catch (MalformedURLException e) {
			return null;
		}
	}

	private void download(URL url, File f) throws IOException {
		int size = getFileSizeKB( event.getCurrentFile().getFilename() );

		f.mkdirs();

		f.delete();
		f.createNewFile();

		URLConnection connection = url.openConnection();

		long down = connection.getContentLength();

		long downm = f.length();

		if (downm != down) {

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			InputStream bis = new BufferedInputStream(conn.getInputStream());

			FileOutputStream fw = new FileOutputStream(f);

			byte[] b = new byte[BUFFER_SIZE];
			int count = 0;
			long total = 0;

			do {
				long startTime = System.nanoTime();
				count = bis.read(b);
				if (count == -1)
					break;
				total += count;
				long taked = (System.nanoTime() - startTime) / 1000;
				fw.write(b, 0, count);
				if (taked > 1000) {
					double speed = ((double) count) / ((double) taked) * 1000D;
					System.out.println(speed + " " + count + " " + taked);
					event.setDownloadSpeed(speed);
				}
				currentFile.setPercents( (total * 100) / (size * 1024) );
				this.onPercentChange(event);
			} while (true);

			fw.close();
		} else {
			return;
		}
	}

	private static void delete(File file) {
		if (!file.exists())
			return;

		if (file.isDirectory()) {
			for (File f : file.listFiles())
				delete(f);
			file.delete();
		} else {
			file.delete();
		}
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}

}