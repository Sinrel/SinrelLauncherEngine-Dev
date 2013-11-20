package org.sinrel.engine.actions;

import org.sinrel.engine.Engine;

public abstract class Checker {
	
	protected Engine engine;
	
	public Checker( Engine engine ) {
		this.engine = engine;
	}
	
	/**
	 * Проверка клиента
	 * 
	 * @param clientName Имя клиента
	 * @return Статус клиента типа {@link ClientStatus}
	*/
	public abstract ClientStatus checkClient( String clientName );

	public abstract DirectoryStatus checkMods( String clientName );
	
	public abstract DirectoryStatus checkDirectory( String clientName, String directoryName );
	
}
