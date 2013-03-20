package org.sinrel.example.gui;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.sinrel.engine.actions.AuthData;
import org.sinrel.engine.actions.AuthResult;
import org.sinrel.engine.actions.ClientStatus;
import org.sinrel.engine.actions.DownloadResult;
import org.sinrel.engine.actions.Intent;
import org.sinrel.engine.listeners.DownloadCompleteListener;

class LoginPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private final JButton auth, pc, update;
	private final JTextField login;
	private final JPasswordField pass;

	LoginPanel() {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBorder(new EmptyBorder(10, 10, 10, 10));

		update = new JButton();
		update.setFocusable(false);

		JLabel loginLabel = new JLabel("Логин:");

		login = new JTextField(15);

		JLabel passLabel = new JLabel("Пароль:");

		pass = new JPasswordField(15);

		// Кнопка входа
		auth = new JButton("Войти");
		auth.setAlignmentX(0.5f);
		auth.addActionListener(this);

		// Кнопка ЛК
		pc = new JButton("ЛК");
		pc.addActionListener(this);

		add(SwingTools.alignLeft(loginLabel));
		add(login);
		add(SwingTools.alignLeft(passLabel));
		add(pass);
		add(SwingTools.center(SwingTools.boxHorizontal(auth, pc)));

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == pc)
			System.out.println("Personal");
		else {
			final Intent i = MainWindow.engine.getIntent();
			// аутентификация
			final AuthData data = i.auth(login.getText(), String.valueOf(pass.getPassword()));

			if (data.getResult() == AuthResult.OK) {
				JOptionPane.showMessageDialog(this, "you are logined!! your session: " + data.getSession());

				// проверка клиента
				ClientStatus status = i.checkClient("minecraft");

				if (status == ClientStatus.WRONG_CLIENT) {

					// асинхронная загрузка клиента
					i.downloadClientAsync("minecraft", true, new DownloadCompleteListener() {
						public void onDownloadComplete(DownloadResult result) {
							System.out.println("client is downloaded i'm need to launch minecraft");
							i.startMinecraft("minecraft", data.getLogin(), data.getSession(), MainWindow.frame);
						}
					});
				}
				else if (status == ClientStatus.OK)
					i.startMinecraft("minecraft", data.getLogin(), data.getSession(), MainWindow.frame);

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

}
