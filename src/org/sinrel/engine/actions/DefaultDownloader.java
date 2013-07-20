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
import org.sinrel.engine.library.NetManager;
import org.sinrel.engine.library.OSManager;
import org.sinrel.engine.library.ZipManager;

public class DefaultDownloader extends Downloader{

	private ArrayList< String > basic = new ArrayList< String >(),
								archives = new ArrayList< String >();
		
	public DefaultDownloader() {
		basic.add( "jinput.jar" );
		basic.add( "lwjgl.jar" );
		basic.add( "lwjgl_util.jar" );
		basic.add( "minecraft.jar" );
		
		archives.add( "client.zip" );
		archives.add( "natives/"+OSManager.getPlatform().toString()+".zip" );
	}

	private DownloadEvent event = new DownloadEvent();
	
	private Engine engine = null;
	private String clientName = null;
	private ArrayList< String > temp = new ArrayList< String >();
		
	public DownloadResult downloadClient( Engine e , String clientName ) {
		this.engine = e;
		this.clientName = clientName;
		
		basic.addAll( additionalFiles );
		archives.addAll( additionalArchives );
		
		temp.addAll( basic );
		temp.addAll( archives );
		
		event = new DownloadEvent();
		event.setCurrentFileAddress( getFileAddress( basic.get( 0 ) ) );
		event.setCurrentFileName( basic.get( 0 ) );
		event.setCurrentFileNumber( 0 );
		event.setCurrentFilePercents( 0 );
		event.setCurrentFileSize( getFileSizeKB( basic.get( 0 ) ) );
		
		event.setFilesAmount( temp.size() - 1 );
		event.setTotalSize( getTotalSize() );
		
		event.setNextFileAddress(  getFileAddress( basic.get( 1 ) ) );
		event.setNextFileName( basic.get( 1 ) );

		for ( String s : temp ) {
			if( !fileExist( getFileAddress( s ) ) ) {
				System.err.println( "Файл не найден: " + getFileAddress( s ) );
				return DownloadResult.FILE_NOT_EXIST;
			}
		}
		
		onStartDownload( event );
		
		delete( new File ( OSManager.getWorkingDirectory( engine ), clientName ) );
		
		for ( int num = 0; num < temp.size(); num++ ) {
			String filename = temp.get( num );
			
			event.setCurrentFileAddress( getFileAddress( filename ) );
			event.setCurrentFileName( filename );
			event.setCurrentFileNumber( num );
			event.setCurrentFileSize( getFileSizeKB( filename ) );
			
			if( num != temp.size() - 1 ) {
				event.setNextFileAddress( getFileAddress( temp.get( num + 1 )  ) );
				event.setNextFileName( temp.get( num + 1 ) );
				event.setNextFileSize( getFileSizeKB( temp.get( num + 1 ) ) );
			}else{
				event.setNextFileAddress( null );
				event.setNextFileName( null );
				event.setNextFileSize( -1 );
			}
			
			try {
				download( event.getCurrentFileAddress(), new File( OSManager.getClientFolder( engine, clientName ), event.getCurrentFileName() ) );
			} catch ( IOException ex ) {
				return DownloadResult.FILE_NOT_EXIST;
			}
			
			onFileChange( event );
		}
		
		File to = OSManager.getClientWorkingDirectory( engine, clientName ),
			 from = OSManager.getClientFolder( engine, clientName );
		
		for ( String s : archives ) {
			if( s.equalsIgnoreCase( "natives/"+OSManager.getPlatform().toString()+".zip" ) ) {
				ZipManager.unzip( new File( from, s ), new File( OSManager.getClientFolder( engine, clientName ), "natives") );
			}else
				ZipManager.unzip( new File( from, s ), to );
		}
		
		ZipManager.removeAllZipFiles( from );
		ZipManager.removeAllZipFiles( new File( OSManager.getClientFolder( engine, clientName ), "natives") );
			
		return DownloadResult.OK;
	}

	private boolean fileExist( URL address ) {
		try {
			HttpURLConnection con = (HttpURLConnection) address.openConnection();
			if( con.getResponseCode() != 200 ) {
				return false;
			}
			return true;
		} catch ( IOException e ) {
			return false;
		}
	}
	
	private int getTotalSize() {
		int sum = 0;

		for ( String s : basic ) {
			sum += getFileSizeKB( s );
		}
		
		for ( String s : archives ) {
			sum += getFileSizeKB( s );
		}
	
		return sum;
	}
	
	private int getFileSizeKB( String filename ) {
		try {
			URLConnection connection = getFileAddress( filename ).openConnection();
			connection.setDefaultUseCaches(false);
			
			return connection.getContentLength() / 1024;
		}catch( IOException e ) {
			return 0;
		}
	}
	
	private URL getFileAddress( String filename ) {
		try {
			return new URL( NetManager.getServerLink( this.engine ).concat( "clients/".concat( this.clientName.concat( "/bin/".concat( filename ) ) ) ) );
		} catch ( MalformedURLException e ) {
			return null;
		}
	}
	
	private void download( URL url, File f ) throws IOException {
		int size = getFileSizeKB( event.getCurrentFileName() );
		
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
				event.setCurrentFilePercents( (int) ( total * 100 ) / ( size * 1024 ) );
				this.onPercentChange( event );
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