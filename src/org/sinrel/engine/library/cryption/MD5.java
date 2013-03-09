package org.sinrel.engine.library.cryption;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.util.Formatter;

public abstract class MD5 {

	/**
	 * @param s обрабатываемая строка
	 * @return MD5 строки, если вызвано исключение возвращает пустую строку
	 */
	public static String getMD5( String s ) {
		try{
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update( s.getBytes() );
			byte[] digest = md.digest();
			StringBuffer sb = new StringBuffer();
			for (byte b : digest) {
				sb.append(Integer.toHexString((int) (b & 0xff)));
			}
			
			return sb.toString();
		}catch( Exception e ) {
			e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * @param f обрабатываемый файл
	 * @return MD5 файла, если вызвано исключение возвращает пустую строку
	 */
	public static String getMD5( File f ) {
		try{
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			
			FileInputStream fis = new FileInputStream( f.toString() );
			BufferedInputStream bis = new BufferedInputStream(fis);
			DigestInputStream dis = new DigestInputStream(bis, algorithm);
			
			while (dis.read() != -1);
			
			byte[] hash = algorithm.digest();
			dis.close();
			
			Formatter formatter = new Formatter();
			
			for (byte b : hash) {
				formatter.format("%02x", b);
			}	
			
			try{
				return formatter.toString();
			}finally{
				formatter.close();
			}
		}catch( Exception e ) {
			return "";
		}
	}
	
}