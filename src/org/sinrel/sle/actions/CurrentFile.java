/*
 * TODO Выбрать более удобный способ хранения и использования путей к файлу.
 */
package org.sinrel.sle.actions;

import java.io.File;
import java.net.URL;

import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.ReadOnlyDoubleWrapper;
import javafx.beans.property.ReadOnlyIntegerProperty;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringProperty;
import javafx.beans.property.ReadOnlyStringWrapper;

public class CurrentFile {

	private ReadOnlyStringWrapper name = new ReadOnlyStringWrapper(); 
	
	private ReadOnlyObjectWrapper<URL> address = new ReadOnlyObjectWrapper<>(),
										localPath = new ReadOnlyObjectWrapper<>();
	
	private ReadOnlyDoubleWrapper percents = new ReadOnlyDoubleWrapper();
	
	private ReadOnlyIntegerWrapper size = new ReadOnlyIntegerWrapper(),
								    number = new ReadOnlyIntegerWrapper();
	
	CurrentFile() {}
	
	public ReadOnlyStringProperty filenameProperty() {
		return name.getReadOnlyProperty();
	}
	
	/**
	 * @return Возращает имя текущего скачиваемого файла
	 */
	public String getFilename() {
		return name.get();
	}
	
	void setFilename( String filename ) {
		name.setValue( filename );
	}
	
	public ReadOnlyObjectProperty<URL> addressProperty() {
		return address.getReadOnlyProperty();
	}
	
	/**
	 * @return Возвращает адрес с которого скачивается текущий файл
	 */
	public URL getAddress() {
		return address.get();
	}
	
	void setAddress( URL address ) {
		this.address.setValue( address );
	}
	
	public ReadOnlyObjectProperty<URL> localPathProperty() {
		return localPath.getReadOnlyProperty();
	}
	
	public URL getLocalPath() {
		return localPath.get();
	}
	
	void setLocalPath( URL localPath ) {
		this.localPath.set( localPath );
	}
	
	public ReadOnlyDoubleProperty percentsProperty() {
		return percents;
	}
	
	/**
	 * @return Возвращает количество загруженого текущего файла в процентах
	 */
	public double getPercents() {
		return percents.get();
	}
	
	void setPercents( double percents ) {
		this.percents.set( percents );
	}
	
	public ReadOnlyIntegerProperty sizeProperty() {
		return size.getReadOnlyProperty();
	}
	
	public int getSize() {
		return size.get();
	}
	
	void setSize( int size ) {
		this.size.set( size );
	}
	
	public ReadOnlyIntegerProperty numberProperty() {
		return number;
	}
	
	/**
	 * @return Возвращает порядковый номер скачиваемого файла
	 */
	public int getNumber() {
		return number.get();
	}
	
	void setNumber( int number ) {
		this.number.set( number );
	}
	
	public File getFile() {
		return new File( localPath.get().toString(), name.get() );
	}
	
}
