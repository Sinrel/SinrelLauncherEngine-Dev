package org.sinrel.engine.exception;

import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import org.sinrel.engine.util.Logger;

public class FatalError extends JFrame {

	private static final long serialVersionUID = -7839456828040805832L;

	public static void showErrorWindow(Exception e){
		showErrorWindow(e.getClass(), e.getStackTrace(), e.getMessage());
	}
	
	public static void showErrorWindow(Class<?> cl ,StackTraceElement[] trace, String message){
		new FatalError(cl, trace, message);
	}
	
	/**
	 * @param cl класс исключения
	 * @param trace трассировка стека
	 * @param message сообщение исключения
	 */
	private FatalError(Class<?> cl ,StackTraceElement[] trace, String message){
		super("SLE Fatal Error");
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}catch(Exception e){}
		
		setSize(400,300);
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setAlwaysOnTop(true);
		setLocationRelativeTo(null);
		setLayout(null);
		setResizable( false );
		
		try {
			BufferedImage img = ImageIO.read(getClass().getResource("sinrel.png"));
			Image icon = img.getSubimage(0, 0, 53, 60);
			ImageIcon logo = new ImageIcon(img);
			
			setIconImage(icon);
			
			JLabel l = new JLabel();
			l.setIcon(logo);
			l.setBounds(90, 10, img.getWidth(), img.getHeight());
			
			add(l);
			
			JLabel info = new JLabel("Критическая ошибка привела к завершению работы.");
			info.setFont(info.getFont().deriveFont(12F));
			info.setBounds(10, 70, 400, 15);
			
			add(info);
			
			
			JLabel info2 = new JLabel("Обратитесь к разработчику, прилагая текст ниже:");
			info2.setFont(info.getFont());
			info2.setBounds(15,85, 400, 15);
			
			add(info2);
			
			JTextArea tp = new JTextArea();
			tp.setEnabled(true);
			tp.setEditable(false);
			tp.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
			tp.setText(cl.getName()+": "+message+"\n");
			tp.setBounds(10, 45 + info.getHeight() + img.getHeight(), 370, 160);
			
			
			for(StackTraceElement st : trace){
				tp.setText(tp.getText()+"at " + st.toString()+"\n");
			}
            
			JScrollPane sp = new JScrollPane(tp);
			sp.setBounds(10, 45 + img.getHeight(), 370, 130);
			
			add(sp);
			
			final JButton b = new JButton();
			b.setText("Подробнее");
			b.setBounds(281, 240, 100, 25);
			
			b.addActionListener( 
					 new ActionListener() {

							@Override
							public void actionPerformed(ActionEvent e) {
								setSize( 785, 300 );
								b.removeActionListener( this );
								b.setText("Закрыть");
								b.setBounds(671, 240, 100, 25);
								
								b.removeActionListener( this );
								
								b.addActionListener( new ActionListener() {

									@Override
									public void actionPerformed(ActionEvent e) {
										System.exit(0);
									}
									
								});
							}
							
						}
			);
		
			add(b);
			
			JTextArea about = new JTextArea();
			about.setEnabled(true);
			about.setEditable(false);
			about.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
			about.setText("");
			
			String[] keys = { "java.version", "java.vm.version", "java.io.tmpdir", "os.name", "os.arch", "os.version", "user.name", "user.home" };
			
			for ( String s : keys ) {
				about.setText( about.getText()+"   "+s+": "+System.getProperty(s)+"\n");
			}
			
			JScrollPane scroll = new JScrollPane( about );
			scroll.setBounds( 30 + tp.getWidth(), 35 , 370, 200 );
			
			add( scroll );
			
			JLabel aboutLabel = new JLabel("Информация о компьютере:");
			aboutLabel.setFont( aboutLabel .getFont().deriveFont(12F) );
			aboutLabel.setBounds( 30 + tp.getWidth(), 10, 200, 15);
			
			add( aboutLabel );
		} catch (IOException e) {
			Logger.error("Не найден компонент SLE");
			e.printStackTrace();
			System.exit(0);
		}	
		
		setVisible(true);
	}
}
