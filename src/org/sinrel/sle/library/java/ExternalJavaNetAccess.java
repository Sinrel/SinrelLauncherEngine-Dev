/*
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package org.sinrel.sle.library.java;

public interface ExternalJavaNetAccess {
    /**
     * return the URLClassPath belonging to the given loader
     */
    ExternalURLClassPath geteURLClassPath (ExternalURLClassLoader u);
}
