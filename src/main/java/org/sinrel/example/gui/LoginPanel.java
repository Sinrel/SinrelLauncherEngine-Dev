package org.sinrel.example.gui;

import org.sinrel.engine.actions.Intent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
			System.out.println( Intent.DoAuth( login.getText() , login.getText() ) );
			System.out.println("Auth action is detected!");
		}
	}
	
	public void paint(Graphics g){
		super.paint(g);
		
		g.drawString("Логин:", 100 , 15);
		g.drawString("Пароль:", 100, 65);
	}
	
}
