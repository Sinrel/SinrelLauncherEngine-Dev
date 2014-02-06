package org.sinrel.sle.exception;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class FatalError {
	
	/**
	 * @param e - Исключение
	 */
	public static void showErrorWindow( Exception e ) {
		if( e != null )
			showErrorWindow( e.getClass(), e.getStackTrace(), e.getMessage() );
	}

	/**
	 * @param cl
	 *            класс исключения
	 * @param trace
	 *            трассировка стека
	 * @param message
	 *            сообщение исключения
	 */
 	private static void showErrorWindow( Class<?> cl, StackTraceElement[] trace, String message ) {
 		try {
			UIManager.setLookAndFeel( UIManager.getSystemLookAndFeelClassName() );
		} catch ( ClassNotFoundException | InstantiationException
				| IllegalAccessException | UnsupportedLookAndFeelException e ) {}
 		
		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		BufferedImage img;
		try {
			img = ImageIO.read(FatalError.class.getResource("sinrel.png"));
		} catch (IOException e1) {
			img = null;
		}
		ImageIcon logo = new ImageIcon(img);

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
			tp.setText(tp.getText() + "   " + "at " + st.toString() + "\n");
		}
		
		tp.setText( tp.getText().trim() );

		JScrollPane sp = new JScrollPane(tp);

		panel.add(sp);

		JLabel aboutLabel = new JLabel("Информация о компьютере:");
		aboutLabel.setFont(aboutLabel.getFont().deriveFont(12F));

		panel.add(aboutLabel);
		
		JTextArea about = new JTextArea();
		about.setEnabled(true);
		about.setEditable(false);
		about.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
		about.setText("");

		String[] keys = { "java.version", "java.vm.version", "java.io.tmpdir", "os.name", "os.arch", "os.version", "user.name", "user.home" };

		for (String s : keys) {
			about.setText(about.getText() + "   " + s + ": " + System.getProperty(s) + "\n");
		}
		
		String s = about.getText();
		s = s.substring( 0, s.length()-1 );
		about.setText( s );

		JScrollPane scroll = new JScrollPane(about);

		panel.add(scroll);

		// Make JOptionPane

		JOptionPane opane = new JOptionPane(panel);
		opane.setPreferredSize(new Dimension(500, 400));

		opane.setComponentOrientation( JOptionPane.getRootFrame().getComponentOrientation() );

		JDialog dialog = opane.createDialog("SLE Fatal Error");
				
		dialog.setIconImage( img.getSubimage( 1, 4, 52, 52) );
		dialog.setVisible(true);
		dialog.dispose();
	}
	
}