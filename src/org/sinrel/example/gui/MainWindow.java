package org.sinrel.example.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.UIManager;

public class MainWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public MainWindow() {
		// Заголовок окна
		setTitle("SLE Example launcher");
		setSize( 400, 300);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setAlwaysOnTop(false);
		setResizable(false);

		// Установим системный Look-and-feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {}

		getContentPane().setLayout( new BorderLayout() );

		// Разместим окно по центру экрана
		setLocationRelativeTo(null);
		
		add( new LoginPanel() );
		
		setVisible(true);
	}
}
