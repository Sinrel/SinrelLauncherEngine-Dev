/*
package org.sinrel.engine.actions;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

import org.sinrel.engine.Engine;
import org.sinrel.engine.library.OSManager;

import org.sinrel.engine.actions.PatchResult;

public class DefaultPatcher extends Patcher {
	
	public DefaultPatcher( Engine engine ) {
		super( engine );
	}
	
	//TODO реализовать все методы
	
	public PatchResult patchDirectory( String clientName , String directory ) {
		try {
			//URLClassLoader cl = new URLClassLoader( new URL[] {  } );
			Class< ? > c = cl.loadClass( "net.minecraft.client.Minecraft" );
			
			
		 	for ( Field f : c.getDeclaredFields() ) {	 		
		 		if( f.getType().getName().equals( "java.io.File" ) & f.getName().length() == 2 
		 				& Modifier.isPrivate( f.getModifiers() ) & Modifier.isStatic( f.getModifiers() )) {
		 			f.setAccessible( true );
		 			f.set( null, OSManager.getClientFolder( "minecraft", "classic" ) );//FIXME
		 		}
		 	}
		} catch ( ClassNotFoundException e ) {
			return PatchResult.CLIENT_NOT_EXIST;
		} catch ( Exception e ) {
			return PatchResult.NOT_COMPLETED;
		}
		
		return PatchResult.OK;
	}
	
	public void patchSkins( String clientName, URL skinsURL ) {}
	public void patchCloaks( String clientName, URL cloaksURL ) {}
	public void patchJoinServerLink( String clientName, URL joinServerLink ) {}
	public void patchAll( String clientName, String directory, URL skinsURL, URL cloaksURL, URL joinServerLink ) {}
	
}
*/