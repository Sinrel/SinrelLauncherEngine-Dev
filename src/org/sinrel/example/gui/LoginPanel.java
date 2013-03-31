package org.sinrel.example.gui;

import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

class LoginPanel extends JPanel {

	private static final long serialVersionUID = -386355955456541316L;
	
	private JTextField login;
	private JPasswordField password;
	
	public LoginPanel() {
		setLayout( null );
		
		login = new JTextField();
		login.setToolTipText("Ваш логин");
		login.setBounds( getWidth() - 120 , 50 , 100, 30);
		
		add( login );
		
		password = new JPasswordField();
		password.setToolTipText("Ваш пароль");
		
	
		
	}

	/*
	public void actionPerformed(ActionEvent e) {
		/*
		if (e.getSource() == pc)
			System.out.println("Personal");
		else {
			final Intent i = MainWindow.engine.getIntent();
			// аутентификация
			final AuthData data = i.auth(login.getText(), login.getText());

			if (data.getResult() == AuthResult.OK) {
				JOptionPane.showMessageDialog(this, "you are logined!! your session: " + data.getSession());

				// проверка клиента
				ClientStatus status = i.checkClient("minecraft");

				if (status == ClientStatus.WRONG_CLIENT) {

					// асинхронная загрузка клиента
					i.downloadClientAsync("minecraft", true, new DownloadCompleteListener() {
						public void onDownloadComplete(DownloadResult result) {
							System.out.println("client is downloaded i'm need to launch minecraft");
							//i.startMinecraft("minecraft", data.getLogin(), data.getSession(), (Frame) getParent());
						}
					});
				}
				else if (status == ClientStatus.OK)
					i.startMinecraft("minecraft", data.getLogin(), data.getSession(), (Frame) getParent());

			}
			else if (data.getResult() == AuthResult.BAD_CONNECTION) {
				JOptionPane.showMessageDialog(this, "ошибка");
			}
			else if (data.getResult() == AuthResult.BAD_LOGIN_OR_PASSWORD) {
				JOptionPane.showMessageDialog(this, "неверный логин или пароль");
			}

			System.out.println("AuthResult action is detected!");
		}

	}
*/
}