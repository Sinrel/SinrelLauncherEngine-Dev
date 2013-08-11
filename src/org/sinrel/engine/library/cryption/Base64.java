package org.sinrel.engine.library.cryption;

public class Base64 {
	
	public static byte[] decode( String string ) {
		return org.apache.commons.codec.binary.Base64.decodeBase64( string );
	}

	public static String encode( byte[] b ) {
		return org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString( b );
	}

	public static String encode( String string ) {
		return org.apache.commons.codec.binary.Base64.encodeBase64URLSafeString( string.getBytes() );
	}
			
}