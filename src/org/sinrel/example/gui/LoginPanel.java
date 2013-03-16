package org.sinrel.example.gui;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.sinrel.engine.Engine;
import org.sinrel.engine.actions.AuthData;
import org.sinrel.engine.actions.AuthResult;
import org.sinrel.engine.actions.ClientStatus;
import org.sinrel.engine.actions.DownloadResult;
import org.sinrel.engine.actions.Intent;
import org.sinrel.engine.listeners.DownloadCompleteListener;

class LoginPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = -5742208666546302146L;
	
	private final JButton auth = new JButton("Войти"), pc = new JButton("ЛК"), update = new JButton();
	private final JTextField login = new JTextField();
	private final JPasswordField pass = new JPasswordField();
	//private final JLabel online = new JLabel();
	
	LoginPanel() {
		setLayout( null );
		setBounds( 25 , 75 , 350, 175 );

		login.setBounds( 100 , 20 , 145 , 25);
		pass.setBounds( 100, 70, 145, 25 );
		
		auth.setBounds( 166 , 100 , 80, 30);
		auth.addActionListener( this );
		auth.setFocusable(false);
		
		pc.setBounds( 99 , 100 , 50, 30);
		pc.addActionListener( this );
		pc.setFocusable(false);
		
		update.setFocusable(false);
		
		
		add( auth );
		add( login );
		add( pass );
		add( pc );
	}

	public void actionPerformed( ActionEvent e ) {
		if( e.getSource() == pc )
			System.out.println("Personal");
		else {
			
			final Intent i = MainWindow.engine.getIntent();
			//авторизация
			final AuthData data = i.auth( login.getText() , login.getText() );
			
			if(data.getResult() == AuthResult.OK){
				JOptionPane.showMessageDialog(this, "you are logined!! your session: " + data.getSession());
				
				//проверка клиента
				ClientStatus status = i.checkClient("minecraft");
				
				if(status == ClientStatus.WRONG_CLIENT){
					
					//асинхронная загрузка клиента
					i.downloadClientAsync("minecraft", true, new DownloadCompleteListener() {
						public void onDownloadComplete(DownloadResult result) {
							System.out.println("client is downloaded i'm need to launch minecraft");
							i.startMinecraft("minecraft", data.getLogin(), data.getSession(), getFrame());
						}
					});
				}
				else if(status == ClientStatus.OK)
					i.startMinecraft("minecraft", data.getLogin(), data.getSession(), getFrame());

			}
			else if(data.getResult() == AuthResult.BAD_CONNECTION){
				JOptionPane.showMessageDialog(this, "ошибка");
			}
			else if(data.getResult() == AuthResult.BAD_LOGIN_OR_PASSWORD){
				JOptionPane.showMessageDialog(this, "неверный логин или пароль");
			}
			
			System.out.println("AuthResult action is detected!");
		}
	}

	public void paint(Graphics g){
		super.paint(g);
		
		g.drawString("Логин:", 100 , 15);
		g.drawString("Пароль:", 100, 65);
	}
	
	private Frame getFrame(){
		return (Frame) this.getParent();
	}
}
