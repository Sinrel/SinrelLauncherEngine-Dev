package org.sinrel.example.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

@Deprecated
class TopPanel extends JPanel implements ActionListener {

	private JButton left, right;

	private static final long serialVersionUID = -2675943654799714669L;

	TopPanel() {
		setLayout(new BorderLayout());
		setBounds(0, 0, 394, 50);

		left = new JButton();
		left.setPreferredSize(new Dimension(200, 50));
		left.setText("Новости");
		left.addActionListener(this);
		left.setFocusable(false);

		right = new JButton();
		right.setPreferredSize(new Dimension(200, 50));
		right.setText("Настройки");
		right.setFocusable(false);
		right.addActionListener(this);

		add(left, "West");
		add(right, "Center");
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == left)
			System.out.println("left");
		else
			System.out.println("right");
	}

}
