package devhead.ru.jlaby;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 * jLaby according by laby
 * 
 * @author Kuznetsov Andrey
 * 
 */
public class Laby extends JFrame {

	private static final long serialVersionUID = 1L;

	final String PROGRAM_NAME = "Laby programming game (Swing version)";
	final String DEVELOPER = "Andrey Kyznetsov";
	final String EMAIL = "andreykyz@gmail.com";

	public Laby() {
		final JFrame frame = this;
		JMenuItem miExit = new JMenuItem("Exit");
		miExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		JMenuItem miAbout = new JMenuItem("About");
		miAbout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, DEVELOPER + "\n" + EMAIL, PROGRAM_NAME,
						JOptionPane.PLAIN_MESSAGE);
			}
		});

		JMenu mFile = new JMenu("File");
		mFile.setMnemonic(KeyEvent.VK_F);
		mFile.addSeparator();
		mFile.add(miExit);

		JMenu mHelp = new JMenu("Help");
		mHelp.setMnemonic(KeyEvent.VK_H);
		mHelp.add(miAbout);

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(mFile);
		menuBar.add(mHelp);
		setJMenuBar(menuBar);
		// setPreferredSize(new Dimension(DisplayWrapper.TABLE_WIDTH,
		// DisplayWrapper.TABLE_HEIGHT));
		pack();

		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		new Laby().setVisible(true);
	}

}
