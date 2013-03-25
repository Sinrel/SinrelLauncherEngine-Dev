package org.sinrel.engine.library.cryption;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Класс для шифрования строк алгоритмом AES-128
 */
public final class AES {

	private static byte[] key;
	
	static {
		AES.key = MD5.md5( StringUtils.getBytesUtf8( "sle" ) ); 
	}

	/**
	 * Превращает текст в ключ и устанавливает его для методов <br>
	 * {@link AESUtils#encryptAESBase64String(String)} <br>
	 * и {@link AESUtils#decryptAESBase64String(String)}
	 * 
	 * @param key
	 *            Строка с ключом
	 */
	public static void setKey( String key ) {
		AES.key = DigestUtils.md5( StringUtils.getBytesUtf8( key ) );
	}


	/**
	 * Шифрует строку по алгоритму AES и кодирует ее в Base64 <br>
	 * С использованием ранее установленного через метод {@link AES#setKey(String) } ключа
 	 * 
	 * @param text
	 *            Текст для шифрования
	 * @return Зашифрованный в AES и закодированный в Base64 текст
	 * @throws GeneralSecurityException
	 */
	public static String encrypt(String text) throws GeneralSecurityException {
		return encryptAESBase64String(text, key);
	}

	/**
	 * Декодирует строку из Base64 и дешифрует по алгоритму AES <br>
	 * С использованием ранее установленного через метод {@link AES#setKey(String) } ключа
	 * 
	 * @param b64_encrypted
	 *            Шифрованные данные
	 * @return Дешифрованную строку
	 * @throws GeneralSecurityException
	 */
	public static String decrypt(String b64_encrypted) throws GeneralSecurityException {
		return decryptAESBase64String(b64_encrypted, key);
	}

	/**
	 * Шифрует строку по алгоритму AES и кодирует ее в Base64
	 * 
	 * @param text
	 *            Текст для шифрования
	 * @param key
	 *            Cтрока с ключом
	 * @return Зашифрованный в AES и закодированный в Base64 текст
	 * @throws GeneralSecurityException
	 */
	public static String encrypt( String text, String key ) throws GeneralSecurityException {
		return encrypt( text, StringUtils.getBytesUtf8(key ) );
	}

	/**
	 * Декодирует строку из Base64 и дешифрует по алгоритму AES
	 * 
	 * @param b64_encrypted
	 *            Шифрованные данные
	 * @param key
	 *            Cтрока с ключом
	 * @return Дешифрованную строку
	 * @throws GeneralSecurityException
	 */
	public static String decrypt( String b64_encrypted, String key ) throws GeneralSecurityException {
		return decrypt( b64_encrypted, StringUtils.getBytesUtf8(key) );
	}

	/**
	 * Шифрует строку по алгоритму AES и кодирует ее в Base64
	 * 
	 * @param text
	 *            Текст для шифрования
	 * @param key
	 *            Массив байтов с ключом
	 * @return Зашифрованный в AES и закодированный в Base64 текст
	 * @throws GeneralSecurityException
	 */
	public static String encrypt(String text, byte[] key) throws GeneralSecurityException {
		return encryptAESBase64String(text, makeKey(key));
	}

	/**
	 * Декодирует строку из Base64 и дешифрует по алгоритму AES
	 * 
	 * @param b64_encrypted
	 *            Шифрованные данные
	 * @param key
	 *            Массив байтов с ключом
	 * @return Дешифрованную строку
	 * @throws GeneralSecurityException
	 */
	public static String decrypt(String b64_encrypted, byte[] key) throws GeneralSecurityException {
		return decryptAESBase64String(b64_encrypted, makeKey(key));
	}

	/**
	 * Генерирует ключ для алгоритма AES
	 * 
	 * @return Сгенерированный ключ
	 */
	public static byte[] generateKey() {
		byte[] key = null;
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128);

			SecretKey skey = kgen.generateKey();
			key = skey.getEncoded();
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return key;
	}

	// Private methods

	public static String encryptAESBase64String(String text, byte[] key) throws GeneralSecurityException {
		SecretKeySpec keyspec = new SecretKeySpec(key, "AES");

		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, keyspec, getIVSpec());

		byte[] padded = padByteArray(StringUtils.getBytesUtf8(text));
		byte[] encrypted = cipher.doFinal(padded);
		return Base64.encodeBase64URLSafeString(encrypted);
	}

	public static String decryptAESBase64String(String b64_encrypted, byte[] key) throws GeneralSecurityException {
		SecretKeySpec keyspec = new SecretKeySpec(key, "AES");

		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, keyspec, getIVSpec());
		byte[] decrypted = cipher.doFinal(Base64.decodeBase64(b64_encrypted));
		return StringUtils.newStringUtf8(decrypted).replace("\0", "");
	}

	private static IvParameterSpec getIVSpec() {
		return new IvParameterSpec(StringUtils.getBytesUtf8("%jUS*(Aol(-y)lC/"));
	}

	private static byte[] padByteArray(byte[] source) {
		int size = 16; // Key length
		int x = source.length / size + 1;
		return Arrays.copyOf(source, size * x);
	}

	private static byte[] makeKey(byte[] bytes) {
		return MD5.md5(bytes);
	}
}