package org.sinrel.engine.library.cryption;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.codec.digest.DigestUtils;

public abstract class MD5 {

	/**
	 * @param s обрабатываемая строка
	 * @return MD5 строки, если вызвано исключение возвращает пустую строку
	 */
	public static String getMD5(String s) {
		return DigestUtils.md5Hex(s);
	}

	/**
	 * @param f обрабатываемый файл
	 * @return MD5 файла, если вызвано исключение возвращает пустую строку
	 * @throws IOException
	 */
	public static String getMD5(File f) throws IOException {
		FileInputStream fis = new FileInputStream(f);
		return DigestUtils.md5Hex(fis);
	}

}