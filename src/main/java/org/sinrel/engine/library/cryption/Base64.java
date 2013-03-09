package org.sinrel.engine.library.cryption;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

public abstract class Base64 {

   public static String decode( String s ) {
	   BASE64Decoder decoder = new BASE64Decoder();
	   
	   byte[] decoded;
	   
	   try {
		   decoded = decoder.decodeBuffer( s );
		   
		   return new String( decoded );  
	   } catch (IOException e) {
		   return "";
	   }
   }
   
   public static String encode( String s ) {
	   BASE64Encoder encoder = new BASE64Encoder();
	   
	   String encoded = encoder.encodeBuffer( s.getBytes() );
	   
	   return encoded;
   }
	
 }