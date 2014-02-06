//TODO Добавить/Проверить поддержку Spigot
package org.sinrel.sle.network;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

@Deprecated
public final class Online {
	
	private int max_players, amount_players, port;
	private String address;
	
	/**
	 * @param address адрес сервера
	 * @param port порт сервера
	 * @throws Exception
	 */
	public Online( String address, int port ) throws Exception {
		this.getOnline( address, port );
	}
	
	private final static String readString( DataInputStream in , int par ) throws IOException {
		int sh = in.readShort();

		if (sh > par) {
			throw new IOException( "Поступила длина строки больше, чем максимально допустимая (" + sh + " > " + par + ")" );
		} else if (sh < 0) {
			throw new IOException( "Поступила длина строки меньше нуля!" );
		} else {
			StringBuilder sb = new StringBuilder();

			for (int i = 0; i < sh; i++ ) {
				sb.append( in.readChar() );
			}

			return sb.toString();
		}
	}
	
	private final void getOnline( String address, int port ) throws Exception {
		Socket soc = null;
		DataInputStream in = null;
		DataOutputStream out = null;
		
		try {
			soc = new Socket();
			soc.setTcpNoDelay(true);
			soc.setTrafficClass(18);
			soc.connect( new InetSocketAddress( address , port ) );
			
			in = new DataInputStream( soc.getInputStream() );
			out = new DataOutputStream( soc.getOutputStream() );
			
			out.write(254);

			if (in.read() != 255) {
				throw new IOException("Bad message");
			}			
			
			String s = readString( in, 256 );
			char[] c  = s.toCharArray();
			
			s = new String(c);
			String[] ar = s.split("\u00a7");
			s = ar[0];

			amount_players = Integer.parseInt( ar[1] );
			max_players = Integer.parseInt( ar[2] ); 
			this.address = address;
			this.port = port;
		} catch ( Exception e ) {
			throw new Exception("Не удалось получить онлайн сервера!");
		}finally{
			soc.close();
		}	
	}
	
	/**
	 * Обновляет данные об онлайне
	 * @throws Exception 
	 */
	public final void refresh() throws Exception {
		this.getOnline( this.getAddress() , this.getPort() );
	}
	
	/**
	 * @return возвращает порт по которому была получена информация о онлайне сервера
	 */
	public final int getPort() {
		return port;
	}
	
	/**
	 * @return возвращает адрес по которому была получена информация о онлайне сервера
	 */
	public final String getAddress() {
		return address;
	}
	
	/**
	 * @return возвращает количество игроков играющих на сервере
	 */
	public final int getPlayersAmount() {
		return amount_players;
	}
	
	/**
	 * @return возвращает максимальное количество игроков способных играть на сервере одновременно (=количество слотов)
	 */
	public final int getServerSize() {
		return max_players;
	}
	
}
