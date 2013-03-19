package org.sinrel.engine.library.cryption;

public abstract class Base64 {
	
	public static byte[] decode( String s ) {
		return org.apache.commons.codec.binary.Base64.decodeBase64( s );
	}

	public static String encode( byte[] b ) {
		return org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString( b );
	}

}