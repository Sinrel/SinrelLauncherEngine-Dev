package org.sinrel.sle.library.cryption;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.sinrel.sle.Engine;
import org.sinrel.sle.network.Command;
import org.sinrel.sle.network.NetworkManager;
import org.apache.commons.codec.binary.Base64;

/**
 * Класс для шифрования строк алгоритмом AES-128
 */
public class Cryptor {
	
	private Engine engine;

	private byte[] key = DigestUtils.md5( "sle2" );
	
	public Cryptor( Engine engine ) {
		this.engine = engine;

		getAndSetKey();
	}
	
	private void getAndSetKey() {
		try {
			if( engine.getSettings().getKey() == null ) {
				key = DigestUtils.md5( decodeKey( getKey() ) );
			}else{
				key = DigestUtils.md5( engine.getSettings().getKey() );
			}
		}catch (IOException e) {}
	}
	
	private String getKey() throws IOException {
		return NetworkManager.sendRequest(NetworkManager.getEngineLink( engine ), Command.KEY);
		//return NetworkManager.sendPostRequest( NetworkManager.getEngineLink( engine ), "command=key" );
	}
	
	private String decodeKey( String encryptedKey) {
		StringBuffer temp = new StringBuffer( encryptedKey );
		return new String( Base64.decodeBase64( temp.reverse().toString() ) );
	}

	public String encrypt( String text ) throws GeneralSecurityException {
		return encryptAESBase64String(text, key);
	}

	public String decrypt( String encryptedText ) throws GeneralSecurityException {
		return decryptAESBase64String( encryptedText, key );
	}

	private String encryptAESBase64String(String text, byte[] key) throws GeneralSecurityException {
		SecretKeySpec keyspec = new SecretKeySpec(key, "AES");

		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		cipher.init(Cipher.ENCRYPT_MODE, keyspec, getIVSpec());

		byte[] padded = padByteArray(StringUtils.getBytesUtf8(text));
		byte[] encrypted = cipher.doFinal(padded);
		return Base64.encodeBase64URLSafeString(encrypted);
	}

	private String decryptAESBase64String(String b64_encrypted, byte[] key) throws GeneralSecurityException {
		SecretKeySpec keyspec = new SecretKeySpec(key, "AES");

		Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");
		cipher.init(Cipher.DECRYPT_MODE, keyspec, getIVSpec());
		
		byte[] decrypted = cipher.doFinal(Base64.decodeBase64(b64_encrypted));
		
		return StringUtils.newStringUtf8(decrypted).replace("\0", "");
	}

	private IvParameterSpec getIVSpec() {
		return new IvParameterSpec( StringUtils.getBytesUtf8("%jUS*(Aol(-y)lC/") );
	}

	private byte[] padByteArray(byte[] source) {
		int size = 16; // Key length
		int x = source.length / size + 1;
		return Arrays.copyOf(source, size * x);
	}
	
}
