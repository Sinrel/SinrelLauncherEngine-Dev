package org.sinrel.engine.actions;

public enum Auth {
    BAD_LOGIN_OR_PASSWORD,
    LOGIN_OR_PASS_NOT_EXIST,
    BAD_CONNECTION,
    OK;

    static public boolean isMember(String aName) {
        Auth[] items = Auth.values();
        for (Auth item : items)
            if (item.name().equals(aName))
                return true;
        return false;
    }
}