package org.sinrel.engine.actions;

import java.net.MalformedURLException;
import java.net.URL;

import org.sinrel.engine.Engine;

/**
 * Класс задаёт методы, которые используются для динамической модификации клиента.
 * <br>Таких модификаций как:
 * <ul>
 * 	<li>Изменение директории</li>
 * 	<li>Изменение ссылок для поиска скинов и плащей</li>
 * </ul>
 * Используются обьекты со ссылками на скины и плащи только с типом URL, что гарантирует корректность ссылок.
 */
public abstract class Patcher {
	//TODO изменить тип возвращаемого значения на статусный
	
    /** Стандартная директория клиента. Используется Minecraft'ом по-умолчанию */
	protected String defaultClientDirectory = "minecraft";
	
	protected URL defaultSkinsAddress;
	protected URL defaultCloaksAddress;
	
	protected Engine engine;
	
	public Patcher( Engine engine ) {
		this.engine = engine;
		
		try{
			defaultSkinsAddress = new URL( "http://skins.minecraft.net/MinecraftSkins/" );
			defaultCloaksAddress = new URL( "http://skins.minecraft.net/MinecraftCloaks/" );
		}catch( MalformedURLException e ){}
	}
	
	public URL getDefaultSkinsAddress() {
		return defaultSkinsAddress;
	}

	public void setDefaultSkinsAddress( URL defaultSkinsURL ) {
		defaultSkinsAddress = defaultSkinsURL;
	}
	
	public URL getDefaultCloaksAddress() {
		return defaultCloaksAddress;
	}

	public void setDefaultCloaksAddress( URL defaultCloaksURL ) {
		defaultCloaksAddress = defaultCloaksURL;
	}
	
	/**
	 * @param clientName Имя изменяемого клиента
	 * @param directory Директория на которую заменять
	 */
	public abstract void patchDirectory( String clientName, String directory );
	
	/**
	 * Поиск скинов осуществляется клиентом по такой конструкции:<br>
	 * {skinsURL} + {username}.png  
	 * 
	 * @param clientName Имя изменяемого клиента
	 * @param skinsURL Адрес на место поиска скинов
	 * 
	 * @exception MalformedURLException Вызывается при использовании некорректной ссылки
	 */
	public abstract void patchSkins( String clientName, URL skinsURL );
	
	/**
	 * Поиск плащей осуществляется клиентом по такой конструкции:<br>
	 * {cloaksURL} + {username}.png  
	 * 
	 * @param clientName Имя изменяемого клиента
	 * @param cloaksURL Адрес на место поиска плащей
	 * 
	 * @exception MalformedURLException Вызывается при использовании некорректной ссылки
	 */
	public abstract void patchCloaks( String clientName, URL cloaksURL );
	
	/**
	 * @param clientName Имя изменяемого клиента
	 * @param joinServerLink Адрес на joinserver-скрипт. Добавлять в конец ?user= не обязательно
	 */
	public abstract void patchJoinServerLink( String clientName, URL joinServerLink );
	
	/**
	 * @param clientName Имя изменяемого клиента
	 * @param directory Директория на которую заменять
	 * @param skinsURL Адрес на место поиска скинов
	 * @param cloaksURL Адрес на место поиска плащей
	 * @param joinServerLink Адрес на joinserver-скрипт. Добавлять в конец ?user= не обязательно
	 */
	public abstract void patchAll( String clientName, String directory, URL skinsURL, URL cloaksURL, URL joinServerLink );
	
}