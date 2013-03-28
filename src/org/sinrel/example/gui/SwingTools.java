package org.sinrel.example.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridBagLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

@Deprecated
public class SwingTools {

	/**
	 * Добавляет все components в контейнер container
	 * 
	 * @param container
	 *            контейнер, в который будут добавлены компоненты
	 * @param components
	 *            компоненты для добавления
	 */
	public static void addAll(final Container container, final Component... components) {
		for (Component c : components)
			container.add(c);
	}

	public static JPanel boxHorizontal(final Component... components) {
		JPanel tp = transparentPanel();
		tp.setLayout(new BoxLayout(tp, BoxLayout.X_AXIS));
		addAll(tp, components);
		return tp;
	}

	public static JPanel boxVertical(final Component... components) {
		JPanel tp = transparentPanel();
		tp.setLayout(new BoxLayout(tp, BoxLayout.Y_AXIS));
		addAll(tp, components);
		return tp;
	}

	public static JPanel alignLeft(Component c) {
		JPanel tp = transparentPanel();
		tp.setLayout(new BorderLayout());
		tp.add(c, BorderLayout.WEST);
		return tp;
	}

	public static JPanel alignRight(Component c) {
		JPanel tp = transparentPanel();
		tp.setLayout(new BorderLayout());
		tp.add(c, BorderLayout.EAST);
		return tp;
	}

	public static JPanel alignUp(Component c) {
		JPanel tp = transparentPanel();
		tp.setLayout(new BorderLayout());
		tp.add(c, BorderLayout.NORTH);
		return tp;
	}

	public static JPanel alignDown(Component c) {
		JPanel tp = transparentPanel();
		tp.setLayout(new BorderLayout());
		tp.add(c, BorderLayout.SOUTH);
		return tp;
	}

	public static JPanel center(Component c) {
		JPanel tp = transparentPanel();
		tp.setLayout(new GridBagLayout());
		tp.add(c);
		return tp;
	}

	public static JPanel padderPanel(final int top, final int left, final int bottom, final int right) {
		return new JPanel() {
			private static final long serialVersionUID = 1L;

			{
				setBorder(new EmptyBorder(top, left, bottom, right));
			}
		};
	}

	public static JPanel transparentPanel() {
		return new JPanel() {
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isOpaque() {
				return false;
			}
		};
	}

}
