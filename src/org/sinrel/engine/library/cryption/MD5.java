package org.sinrel.engine.library.cryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;

public class MD5 {

	/**
	 * @param s обрабатываемая строка
	 * @return MD5 строки в Hex
	 */
	public static String md5Hex(String s) {
		return DigestUtils.md5Hex(s);
	}

	/**
	 * @param s обрабатываемая строка
	 * @return MD5 строки
	 */
	public static String md5( String s ) {
		return new String( DigestUtils.md5(s) );
	}
	
	public static byte[] md5( byte[] s ) {
		return DigestUtils.md5( s );
	}
	
	/**
	 * @param f обрабатываемый файл
	 * @return MD5 файла в hex, если вызвано исключение возвращает пустую строку
	 * @throws IOException
	 */
	public static String getMD5(File f) throws IOException {
		FileInputStream fis = new FileInputStream(f);
		return DigestUtils.md5Hex(fis);
	}

}