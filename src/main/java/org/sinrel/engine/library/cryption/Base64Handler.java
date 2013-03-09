package org.sinrel.engine.library.cryption;

import org.apache.commons.codec.binary.Base64;

public abstract class Base64Handler {

    public static String decode(String s) {
        return new String(Base64.decodeBase64(s.getBytes()));
    }

    public static String encode(String s) {
        return new String(Base64.encodeBase64(s.getBytes()));
    }

}