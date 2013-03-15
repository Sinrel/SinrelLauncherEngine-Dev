package org.sinrel.example.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.sinrel.engine.actions.Intent;

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
			System.out.println(Intent.DoAuth(login.getText(), login.getText()));
			System.out.println("Auth action detected!");
		}
	}

}
