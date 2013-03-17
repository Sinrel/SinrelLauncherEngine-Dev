package org.sinrel.engine.exception;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class FatalError {

	public static void showErrorWindow(Exception e) {
		showErrorWindow(e.getClass(), e.getStackTrace(), e.getMessage());
	}

	/**
	 * @param cl
	 *            класс исключения
	 * @param trace
	 *            трассировка стека
	 * @param message
	 *            сообщение исключения
	 */
	public static void showErrorWindow(Class<?> cl, StackTraceElement[] trace, String message) {
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		BufferedImage img;
		try {
			img = ImageIO.read(FatalError.class.getResource("sinrel.png"));
		} catch (IOException e1) {
			img = null;
		}
		ImageIcon logo = new ImageIcon(img.getSubimage(0, 0, 53, 60));

		// setIconImage(icon);

		JLabel l = new JLabel();
		l.setIcon(logo);

		panel.add(l);

		JLabel info = new JLabel("Критическая ошибка привела к завершению работы.");
		info.setFont(info.getFont().deriveFont(12F));

		panel.add(info);

		JLabel info2 = new JLabel("Обратитесь к разработчику, прилагая текст ниже:");
		info2.setFont(info.getFont());

		panel.add(info2);

		JTextArea tp = new JTextArea();
		tp.setEnabled(true);
		tp.setEditable(false);
		tp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		tp.setText(cl.getName() + ": " + message + "\n");
		tp.setBounds(10, 45 + info.getHeight() + img.getHeight(), 370, 160);

		for (StackTraceElement st : trace) {
			tp.setText(tp.getText() + "at " + st.toString() + "\n");
		}

		JScrollPane sp = new JScrollPane(tp);

		panel.add(sp);

		final JButton b = new JButton();
		b.setText("Подробнее");

		b.addActionListener(
				new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						// setSize(785, 300);
						b.removeActionListener(this);
						b.setText("Закрыть");
						b.setBounds(671, 240, 100, 25);

						b.removeActionListener(this);

						b.addActionListener(new ActionListener() {

							public void actionPerformed(ActionEvent e) {
								System.exit(0);
							}

						});
					}

				}
				);

		panel.add(b);

		JTextArea about = new JTextArea();
		about.setEnabled(true);
		about.setEditable(false);
		about.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		about.setText("");

		String[] keys = { "java.version", "java.vm.version", "java.io.tmpdir", "os.name", "os.arch", "os.version", "user.name", "user.home" };

		for (String s : keys) {
			about.setText(about.getText() + "   " + s + ": " + System.getProperty(s) + "\n");
		}

		JScrollPane scroll = new JScrollPane(about);

		panel.add(scroll);

		JLabel aboutLabel = new JLabel("Информация о компьютере:");
		aboutLabel.setFont(aboutLabel.getFont().deriveFont(12F));

		panel.add(aboutLabel);

		// Make JOptionPane

		JOptionPane opane = new JOptionPane(panel, JOptionPane.ERROR_MESSAGE);
		opane.setPreferredSize(new Dimension(600, 400));

		opane.setComponentOrientation(JOptionPane.getRootFrame().getComponentOrientation());

		JDialog dialog = opane.createDialog("SLE Fatal Error");

		dialog.setVisible(true);
		dialog.dispose();
	}
}