package org.sinrel.engine.actions;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.sinrel.engine.Engine;
import org.sinrel.engine.library.OSManager;
import org.sinrel.engine.library.ZipManager;
import org.sinrel.engine.listeners.DownloadListener;

public final class Downloader {
	
	static ArrayList<DownloadListener> listeners = new ArrayList<DownloadListener>();
	static Download status;
	static String[] files = { "jinput.jar" , "lwjgl.jar" , "lwjgl_util.jar" , "minecraft.jar" , "natives/"+OSManager.getPlatform().toString()+".zip" };
	
	
	Downloader( final String directory , boolean loadZip ) {
		try{
	 		for( DownloadListener dl : listeners ) {
				dl.onStartDownload();
			}
			
			delete( new File( OSManager.getWorkingDirectory( directory ).toString() ) );
			
	        URL url; 
	        
	        if( !Engine.getDescriptionFile().get("folder").equalsIgnoreCase("") ) {
	      	  url =	new URL( 
	      			  "http://" + Engine.getDescriptionFile().get("domain") + "/" +  Engine.getDescriptionFile().get("folder") + "/" + "client" + "/" 
	      	  );
	        }else{
	      	  url =	new URL( 
	      			  "http://" + Engine.getDescriptionFile().get("domain") + "/" + "client/"
	      	  );
	        }
	        
	        String now, next = null;
	        
	        for ( int num = 0; num < files.length ; num++ ) {
	        	try{
	        		now = files[num];
	        		if( num != files.length - 1 )
	        			next = files[num + 1];
	        		else 
	        			if( !loadZip ) 
	        				next = "Finish";
	        			else 
	        				next = "client.zip";
	        		
	        		download( new URL( url + files[num] ) , new File( OSManager.getClientFolder( directory ) , files[num]) );
	        		
	        		for( DownloadListener dl : listeners ) {
						dl.onFileChange( now , next );
					}
	        	}catch( IOException e ) {
	        		status = Download.FILE_NOT_EXIST;
	        		return;
	        	}
	        }
	        
	        if( loadZip ) {
	        	try{
	        		now = "client.zip";
	        		next = "Finish";
	        	
	        		download( new URL( url + "client.zip" ) , new File( OSManager.getWorkingDirectory( directory ) , "client.zip" ) );
	        		
	        		for( DownloadListener dl : listeners ) {
						dl.onFileChange( now , next );
					}
	        	
	    	        ZipManager.unzip( new File( OSManager.getWorkingDirectory( directory ) , "client.zip" ) , new File( OSManager.getWorkingDirectory( directory ), "") );
	    	        ZipManager.removeAllZipFiles( new File( OSManager.getWorkingDirectory( directory ).toString() ) );
	        	}catch( IOException e ) {
	        		status = Download.FILE_NOT_EXIST;
	        		return;
	        	}
	        }
	        
	        ZipManager.unzip( new File( OSManager.getClientFolder( directory ), files[ files.length - 1 ] ) , new File( OSManager.getClientFolder( directory ) + File.separator + "natives" ) );
	        ZipManager.removeAllZipFiles( new File( OSManager.getClientFolder( directory ) + File.separator + "natives" ) );
	        
	        status = Download.OK;
	        
		}catch( MalformedURLException e ) {
			e.printStackTrace();
		}
	}
	
	
	private void download( URL url, File f ) throws IOException {
		f.mkdirs();

		f.delete();
		f.createNewFile();
		
		URLConnection connection = url.openConnection();

		long down = connection.getContentLength();

		long downm = f.length();

		if (downm != down) {

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			BufferedInputStream bis = new BufferedInputStream(
					conn.getInputStream());

			FileOutputStream fw = new FileOutputStream(f);

			byte[] b = new byte[1024];
			int count = 0;
			long total = 0;

			while ((count = bis.read(b)) != -1) {
				total += count;
				fw.write(b, 0, count);
				
				for( DownloadListener dl : listeners ) {
					dl.onPercentChange( total , count );
				}
			}
			fw.close();
		}else{
			return;
		}
	}
	
	public Download getAnswer() {
		return status;
	}
	
	private void delete( File file ) {
	    if( !file.exists() ) return;
	    
	    if( file.isDirectory() ) {
	    	for(File f : file.listFiles())
	    		delete(f);
	    		file.delete();
	    	}else{
	    		file.delete();
	    	}
		}
	}
