package org.sinrel.engine.library;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Formatter;

public final class FileManager {
	
	/**
	 * @param f обрабатываемый файл
	 * @return MD5 файла, если вызвано исключение возвращает пустую строку
	 */
	public static String getMD5(File f) {
		try {
			return calculateHash( MessageDigest.getInstance("MD5"), f.toString() );
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static String calculateHash( MessageDigest algorithm, String fileName ) throws Exception {
		
		FileInputStream fis = new FileInputStream(fileName);
		BufferedInputStream bis = new BufferedInputStream(fis);
		DigestInputStream dis = new DigestInputStream(bis, algorithm);

		while (dis.read() != -1);
		
		byte[] hash = algorithm.digest();
		dis.close();
		
		return byteArray2Hex(hash);
	}

	private static String byteArray2Hex( byte[] hash ) {
		
		Formatter formatter = new Formatter();
		
		for (byte b : hash) {
			formatter.format("%02x", b);
		}
		formatter.close();
		
		return formatter.toString();
	}
	
}
