package org.sinrel.sle.actions;

import java.net.URL;

public class DownloadEvent {

	private CurrentFile currentFile = new CurrentFile();
	
	private String nextFileName;
	private URL nextFileAddress;
	private int nextFileSize;

	private int filesAmount;
	private int totalSize;

	private double downloadSpeed;

	public DownloadEvent() {}

	public DownloadEvent(String currentFileName, URL currentFileAddress,
			String nextFileName, URL nextFileAddress, int currentFileNumber,
			int filesAmount, int currentFileSize, int currentFilePercents,
			int nextFileSize, int totalSize) {
		
		currentFile.setFilename( currentFileName );
		currentFile.setAddress( currentFileAddress );
		currentFile.setPercents( currentFilePercents );
		currentFile.setSize( currentFileSize );
		currentFile.setNumber( currentFileNumber );
		
		this.nextFileName = nextFileName;
		this.nextFileAddress = nextFileAddress;
		this.nextFileSize = nextFileSize;
		
		this.filesAmount = filesAmount;
		this.totalSize = totalSize;
	}

	/**
	 * 
	 * @return Возвращает объект, описывающий текущий скачиваемый файл.
	 * 
	 */
	public final CurrentFile getCurrentFile() {
		return currentFile;
	}
	
	/**
	 * @return Возвращает имя файла, который будет загружаться следующим<br>
	 *         Если следующего файла нет, возвращается null !
	 */
	public final String getNextFileName() {
		return nextFileName;
	}

	/**
	 * @return Возращает количество скачиваемых файлов
	 */
	public final int getFilesAmount() {
		return filesAmount;
	}

	/**
	 * @return Возвращает размер, следующего по очереди загрузки, файла в
	 *         КилоБайтах.<br>
	 *         Если следующего файла нет, возвращается -1 !
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
	 *         Если следующего файла нет, возвращается null !
	 */
	public final URL getNextFileAddress() {
		return nextFileAddress;
	}

	/**
	 * @return Возвращает скорость загрузки файла (KiB/s)
	 */
	public final double getDownloadSpeed() {
		return downloadSpeed;
	}

	final void setNextFileName(String filename) {
		this.nextFileName = filename;
	}

	final void setNextFileAddress(URL address) {
		this.nextFileAddress = address;
	}

	final void setFilesAmount(int amount) {
		this.filesAmount = amount;
	}

	final void setTotalSize(int amount) {
		this.totalSize = amount;
	}

	final void setNextFileSize(int nextFileSize) {
		this.nextFileSize = nextFileSize;
	}

	final void setDownloadSpeed(double downloadSpeed) {
		this.downloadSpeed = downloadSpeed;
	}

}
