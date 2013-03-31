package org.sinrel.engine.actions;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.sinrel.engine.Engine;
import org.sinrel.engine.library.OSManager;
import org.sinrel.engine.library.ZipManager;

public class DefaultDownloader extends Downloader{

	static final String[] files = { "jinput.jar" , "lwjgl.jar" , "lwjgl_util.jar" , "minecraft.jar" , "client.zip" , "natives/"+OSManager.getPlatform().toString()+".zip"};

	public DownloadResult downloadClient( Engine e , String clientName ) {
		DownloadResult status = null;
		
		try{
			this.onStartDownload();
			URL url;
			
			delete( OSManager.getClientFolder( e.getSettings().getDirectory() , clientName ) );
			
	        if( !e.getSettings().getServerPath().equalsIgnoreCase("") ) {
		      	  url =	new URL( 
		      			  "http://" + e.getSettings().getDomain() + "/" +  e.getSettings().getServerPath() + "/" + "clients" + "/" + clientName + "/" 
		      	  );
		        }else{
		      	  url =	new URL( 
		      			  "http://" + e.getSettings().getDomain()  + "/clients/"+ clientName +"/"
		      	  );
		        }
	        
	        String now, next = null;      
	    
	        for ( int num = 0; num < files.length ; num++ ) {
	        	try{
	        		now = files[num];
	        		if( num != files.length - 1 )
	        			next = files[num + 1];
	        		else 
	        			next = "Finish";
        			
	        		download( new URL( url + files[num] ) , new File( OSManager.getClientFolder( e.getSettings().getDirectory() , clientName ) , files[num]) );

	        		onFileChange( now , next );
	        	}catch( IOException ex ) {
	        		status = DownloadResult.FILE_NOT_EXIST;
	        		return status;
	        	}
	        }
	        
	        File to = OSManager.getClientFolder( e.getSettings().getDirectory() ,  clientName );
			String path = OSManager.getWorkingDirectory( e.getSettings().getDirectory() ).toPath().toString();
	        
			ZipManager.unzip( new File( to , "client.zip" ) , new File( path + File.separator + clientName + File.separator ) );
	        ZipManager.removeAllZipFiles( to );
	        
	        ZipManager.unzip( new File( to  , files[ files.length - 1 ] ) , new File( to + File.separator + "natives" ) );
	        ZipManager.removeAllZipFiles( new File( to.toString()  + File.separator + "natives" ) );
	        
	        status = DownloadResult.OK;    
		}catch( MalformedURLException ex ) {
			ex.printStackTrace();
		}
		
		return status;
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
				onPercentChange(total, count);	
			}
			fw.close();
		}else{
			return;
		}
	}
	
	private static void delete( File file ) {
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
