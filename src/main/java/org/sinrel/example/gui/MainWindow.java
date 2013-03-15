package org.sinrel.example.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.sinrel.engine.actions.Action;
import org.sinrel.engine.actions.Intent;

public class MainWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	final static int width = 500, height = 400;

	public MainWindow() {
		// Заголовок окна
		setTitle("SLE Example launcher");
		setSize(width, height);

		// Установим системный Look-and-feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				Intent.Do(Action.DISABLE); // Зачем нужно это событие?
			}
		});

		// Установим контент-панели менеджер слоев BorderLayout
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		// Добавим панельки на главный контейнер
		add(new TopPanel(), BorderLayout.NORTH);
		add(SwingTools.center(new LoginPanel()));

		// Разместим окно по центру экрана
		setLocationRelativeTo(null);

	}
}
