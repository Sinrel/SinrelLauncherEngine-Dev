package org.sinrel.engine.library;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Stack;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public final class ZipManager {
	
	 private final static int BUFFER = 1024;  
	    
	 public static void unzip( File inFile, File outFolder ) { 
		 try {  
			 createFolder(outFolder, true);  
	         BufferedOutputStream out = null;  
	         ZipInputStream  in = new ZipInputStream((new FileInputStream(inFile)));  
	         ZipEntry entry;  
	         
	         while((entry = in.getNextEntry()) != null) {  
	        	 int count;  
	             byte data[] = new byte[BUFFER];  
	             
	             File newFile = new File(outFolder.getPath() + File.separator + entry.getName());  
	             Stack<File> pathStack = new Stack<File>();  
	             File newNevigate = newFile.getParentFile();  
	       
	             while(newNevigate != null){  
	            	 pathStack.push(newNevigate);  
	                 newNevigate = newNevigate.getParentFile();  
	             }  
	 
	             while(!pathStack.isEmpty()){  
	            	 File createFile = pathStack.pop();  
	                 createFolder(createFile, true);  
	             }  
	             
	             if(!entry.isDirectory()){  
	            	 out = new BufferedOutputStream( new FileOutputStream(newFile), BUFFER );  
	                 
	            	 while ( (count = in.read(data,0,BUFFER) ) != -1 ){  
	            		 out.write(data,0,count);  
	                 }  
	                 
	            	 cleanUp(out);
	             }  
	         }  
	         
	         cleanUp(in);  
		 }catch( Exception e ) {  
			 e.printStackTrace();
	     }  
     }  
	  
	 private static void cleanUp( InputStream in ) throws Exception {
		 in.close();  
	 }  
	 
	 private static void cleanUp( OutputStream out ) throws Exception {
		 out.flush();  
	     out.close();  
	 }  
	      
	 public static void removeAllZipFiles( File folder ) {
		 String[] files = folder.list();
	     
		 for(String file: files){  
			 File item = new File(folder.getPath() + File.separator + file);
	            
	         if( item.exists() && item.getName().toLowerCase().endsWith(".zip") ) {
	        	 item.delete();   
	         }  
	     }  
	 }  
	 
	 private static void createFolder( File folder, boolean isDirectory ) {  
		 if( isDirectory ) {  
			 folder.mkdir();  
	     }  
	 }  
	 
}
