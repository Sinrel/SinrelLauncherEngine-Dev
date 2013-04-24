package org.sinrel.engine.actions;

import java.net.URL;

public class DownloadEvent {
	
	private String currentFileName;
	private URL currentFileAddress;
	private int currentFilePercents;
	private int currentFileSize;
	
	private String nextFileName;
	private URL nextFileAddress;
	private int nextFileSize;
	
	private int currentFileNumber;
	private int filesAmount;
	private int totalSize;
	
	public DownloadEvent() {}
	
	public DownloadEvent( String currentFileName, 
							URL currentFileAddress, 
							String nextFileName, 
							URL nextFileAddress, 
							int currentFileNumber, 
							int filesAmount, 
							int currentFileSize , 
							int currentFilePercents, 
							int nextFileSize,
							int totalSize) {
		this.currentFileName = currentFileName;
		this.currentFileAddress = currentFileAddress;
		this.currentFilePercents = currentFilePercents;
		this.currentFileSize = currentFileSize;
		
		this.nextFileName = nextFileName;
		this.nextFileAddress = nextFileAddress;
		this.nextFileSize = nextFileSize;
		
		this.currentFileNumber = currentFileNumber;
		this.filesAmount = filesAmount;
		this.totalSize = totalSize;
	}
	
	/**
	 * @return Возращает имя текущего скачиваемого файла
	 */
	public final String getCurrentFileName() {
		return currentFileName;
	}
	
	/**
	 * @return Возвращает имя файла, который будет загружаться следующим<br>
	 * Если следующего файла нет, возвращается null !
	 */
	public final String getNextFileName() {
		return nextFileName;
	}
	
	/**
	 * @return Возвращает количество загруженого текущего файла в процентах 
	 */
	public final int getCurrentFilePercents() {
		return currentFilePercents;
	}
	
	public final int getCurrentFileSize() {
		return currentFileSize;
	}
	
	/**
	 * @return Возращает количество скачиваемых файлов
	 */
	public final int getFilesAmount() {
		return filesAmount;
	}
	
	/**
	 * @return Возвращает порядковый номер скачиваемого файла
	 */
	public final int getCurrentFileNumber() {
		return currentFileNumber;
	}
	
	/**
	 * @return Возвращает адрес с которого скачивается текущий файл
	 */
	public final URL getCurrentFileAddress() {
		return currentFileAddress;
	}
	
	/**
	 * @return Возвращает размер, следующего по очереди загрузки, файла в КилоБайтах.<br>
	 * Если следующего файла нет, возвращается -1 !
	 */
	public final int getNextFileSize() {
		return nextFileSize;
	}
	
	/**
	 * @return Возвращает суммарный размер всех скачиваемых файлов
	 */
	public final int getTotalSize() {
		return totalSize;
	}
	
	/**
	 * @return Возвращает адрес с которого будет скачиваться следующий файл<br>
	 * Если следующего файла нет, возвращается null !
	 */
	public final URL getNextFileAddress() {
		return nextFileAddress;
	}
	
	final void setCurrentFileName( String filename ) {
		this.currentFileName = filename;
	}
	
	final void setCurrentFileAddress( URL address ) {
		this.currentFileAddress = address;
	}
	
	final void setNextFileName( String filename ) {
		this.nextFileName = filename;
	}
	
	final void setNextFileAddress( URL address ) {
		this.nextFileAddress = address;
	}
	
	final void setCurrentFileNumber( int number ) {
		this.currentFileNumber = number;
	}
	
	final void setFilesAmount( int amount ) {
		this.filesAmount = amount;
	}
	
	final void setTotalSize( int amount ) {
		this.totalSize = amount;
	}
	
	final void setCurrentFileSize( int currentFileSize ) {
		this.currentFileSize = currentFileSize;
	}
	
	final void setCurrentFilePercents( int percents ) {
		this.currentFilePercents = percents;
	}
	
	final void setNextFileSize( int nextFileSize ) {
		this.nextFileSize = nextFileSize;
	}
	
}
