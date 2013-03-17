package org.sinrel.example.gui;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.UIManager;

import org.sinrel.engine.Engine;
import org.sinrel.engine.EngineSettings;

public class MainWindow extends JFrame {
	private static final long serialVersionUID = 1L;

	static Engine engine;
	final static int width = 500, height = 300;

	public static void main(String[] args) {

		// инициализация двигла
		EngineSettings settings = new EngineSettings("example.com", "minecraft", "0.1");
		engine = new Engine(settings);

		MainWindow main = new MainWindow();
		main.setVisible(true);
	}

	public MainWindow() {
		// Заголовок окна
		setTitle("SLE Example launcher");
		setSize(width, height);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		// Установим системный Look-and-feel
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}

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
