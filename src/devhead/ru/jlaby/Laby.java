package devhead.ru.jlaby;

import java.awt.Dimension;
import java.awt.Event;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.Timer;

import jsyntaxpane.DefaultSyntaxKit;

/**
 * jLaby according by laby
 * 
 * @author Kuznetsov Andrey
 * 
 */
public class Laby extends JFrame implements KeyListener{

	private static final long serialVersionUID = 1L;

	final String PROGRAM_NAME = "Laby programming game (Swing version)";
	final String DEVELOPER = "Andrey Kyznetsov";
	final String EMAIL = "andreykyz@gmail.com";
	
	final static String LEVEL_PATH = "data/levels";
	final static String IMAGES_PATH = "data/tiles";
	final static String MODS_PATH = "data/mods";
	final static String FILE_TYPE = ".png";
    
	private String openedFile = "";
	JRadioButtonMenuItem miSurvivor;
	LevelMap levelMap;
	PipeBroker broker;
	JTextArea feedBack;
	final JEditorPane codeArea;

    private Timer slowTimer;

    private Timer fastTimer;
	
	public Laby() {
		final JFrame frame = this;
		JPanel panel = new JPanel();
		ActionListener resetAction = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                levelMap.reset();
                broker = new PipeBroker(new ProgramRunner(codeArea, feedBack, levelMap, "c").startGame(), levelMap);
                frame.repaint();
            }

        };	
        
        ActionListener forwardAction = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                broker.forward();

            }

        };
        
        ActionListener playAction = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                slowTimer.start();

            }

        };
        
        ActionListener fastForwardAction = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                fastTimer.start();

            }

        };
        
        ActionListener slowTaskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //...Perform a task...
            }
        };
        
        slowTimer = new Timer(2000, slowTaskPerformer);     
        
        ActionListener fastTaskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //...Perform a task...
            }
        };
        
        fastTimer = new Timer(500, fastTaskPerformer);
		
        JMenuItem miNew = new JMenuItem("New file");
        miNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Event.CTRL_MASK));
        miNew.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                codeArea.setText("public static void main(String[] args) {\n}");;
                setOpenedFile("");
            }
        });
        
        JMenuItem miOpen = new JMenuItem("Open");
        miOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, Event.CTRL_MASK));
        miOpen.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser c = new JFileChooser();
                // "Open" dialog:
                int rVal = c.showOpenDialog(Laby.this);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        Reader reader = new FileReader(c.getSelectedFile());
                        codeArea.read(reader, null);
                        reader.close();
                        setOpenedFile(c.getSelectedFile().getAbsolutePath());
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });
        
        JMenuItem miSave = new JMenuItem("Save");
        miSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK));
        miSave.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (!openedFile.equals("")) {
                    try {
                        Writer writer = new FileWriter(new File(openedFile));
                        writer.write(codeArea.getText());
                        writer.close();
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                } else {
                    // "Save" dialog:
                    JFileChooser c = new JFileChooser();
                    int rVal = c.showSaveDialog(Laby.this);
                    if (rVal == JFileChooser.APPROVE_OPTION) {
                        try {
                            Writer writer = new FileWriter(c.getSelectedFile());
                            writer.write(codeArea.getText());
                            writer.close();
                            setOpenedFile(c.getSelectedFile().getAbsolutePath());
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                }
            }
        });
        
        JMenuItem miSaveAs = new JMenuItem("Save as");
        miSaveAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Event.CTRL_MASK | Event.SHIFT_MASK));
        miSaveAs.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // "Save" dialog:
                JFileChooser c = new JFileChooser();
                int rVal = c.showSaveDialog(Laby.this);
                if (rVal == JFileChooser.APPROVE_OPTION) {
                    try {
                        Writer writer = new FileWriter(c.getSelectedFile());
                        writer.write(codeArea.getText());
                        writer.close();
                        setOpenedFile(c.getSelectedFile().getAbsolutePath());
                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                }
            }
        });
        
		JMenuItem miExit = new JMenuItem("Exit");
		miExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		miSurvivor = new JRadioButtonMenuItem("Survivor mode");
		miSurvivor.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                if(arg0.getActionCommand().equals("disable")) {
                    frame.setFocusable(false);
                } else {
                    frame.setFocusable(true);
                    frame.requestFocus();
                }
                
            }
		    
		});
		
		JMenuItem miStart = new JMenuItem("Start");
		miStart.addActionListener(resetAction);
		
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
		mFile.add(miNew);
        mFile.add(miOpen);
        mFile.add(miSave);
        mFile.add(miSaveAs);
        mFile.addSeparator();
		mFile.add(miSurvivor);
		mFile.add(miStart);
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
		FilenameFilter filenamefilter = new FilenameFilter() {
			public boolean accept(File directory, String fileName) {
				return fileName.endsWith(".laby");
			}
		};
		setOpenedFile(openedFile);
		File path = new File(Laby.LEVEL_PATH);// + Laby.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		JLabel languageLabel = new JLabel("Language:");
		String[] language = {"C"};
		JComboBox<String> languageChooser = new JComboBox<String>(language);
		JLabel levelLabel = new JLabel("Level:");
		File[] arFiles = path.listFiles(filenamefilter);
		JComboBox<File> levelChooser = new JComboBox<File>(arFiles);
		levelChooser.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent itemEvent) {
                if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                    levelMap.LoadLevel(itemEvent.getItem().toString());
                    if (miSurvivor.isSelected()) {
                        frame.requestFocus();
                    } else {
                        codeArea.requestFocus();
                    }
                    frame.repaint();
                }
            }
		    
		});
		feedBack =  new JTextArea();
		feedBack.enableInputMethods(false);
		feedBack.setMinimumSize(new Dimension(100,feedBack.getFont().getSize()*15));
        JScrollPane feedBackS = new JScrollPane(feedBack, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        feedBackS.setAutoscrolls(true);
		levelMap = new LevelMap("data/levels/1c.laby", feedBack);
		
		JLabel helpArea = new JLabel("<html><b>This is help</b><br>First line<br>Second line</html>");

		DefaultSyntaxKit.initKit();

		codeArea = new JEditorPane();
		JScrollPane codeAreaS = new JScrollPane(codeArea);
		codeArea.setMinimumSize(new Dimension(100,codeArea.getFont().getSize()*20));
		codeArea.setContentType("text/java");
		codeArea.setText("public static void main(String[] args) {\n}");
		
        JButton forwardButton = new JButton(new ImageIcon("data/buttons/green_single_arrows_play.png"));
        forwardButton.addActionListener(forwardAction);
		JButton playButton = new JButton(new ImageIcon("data/buttons/green_single_arrows_right.png"));
		playButton.addActionListener(playAction);
		JButton backwardButton = new JButton(new ImageIcon("data/buttons/green_single_arrows_play_left.png"));
	    JButton fastForwardButton = new JButton(new ImageIcon("data/buttons/green_double_arrows_right.png"));
	    fastForwardButton.addActionListener(fastForwardAction);
	    JButton fastBackwardButton = new JButton(new ImageIcon("data/buttons/green_double_arrows_left.png"));
	    JButton resetButton = new JButton(new ImageIcon("data/buttons/Spinaround.png"));
	    resetButton.addActionListener(resetAction);

	    
	    
		/* Layouts*/
		GroupLayout layout = new GroupLayout(panel);
		panel.setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);
		
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(helpArea)
                        .addComponent(levelMap))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(languageLabel)
                                        .addComponent(levelLabel))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(languageChooser)
                                        .addComponent(levelChooser))
                        )
                        .addComponent(codeAreaS)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(backwardButton)
                                .addComponent(resetButton)
                                .addComponent(forwardButton)
                                .addComponent(playButton)
                                .addComponent(fastForwardButton))
                        .addComponent(feedBackS))
        );
		
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(helpArea)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(languageLabel)
                                        .addComponent(languageChooser))
                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(levelLabel)
                                        .addComponent(levelChooser))
                                ))
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(levelMap)
                        .addGroup(layout.createSequentialGroup()
                        .addComponent(codeAreaS)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(backwardButton)
                                .addComponent(resetButton)
                                .addComponent(forwardButton)
                                .addComponent(playButton)
                                .addComponent(fastForwardButton))
                        .addComponent(feedBackS)))
        );

		this.setContentPane(panel);
		pack();
		levelMap.repaint();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		addKeyListener(this);
	}

	public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Laby().setVisible(true);
            }
        });

	}

    @Override
    public void keyPressed(KeyEvent key) {
        if (key.getKeyCode() == KeyEvent.VK_LEFT) {
            levelMap.left();
        }
        if (key.getKeyCode() == KeyEvent.VK_RIGHT) {
            levelMap.right();
        }
        if (key.getKeyCode() == KeyEvent.VK_UP) {
            levelMap.forward();
        }   
        if (key.getKeyCode() == KeyEvent.VK_SPACE | key.getKeyCode() == KeyEvent.VK_T) {
            levelMap.take();
        }
        if (key.getKeyCode() == KeyEvent.VK_D) {
            levelMap.drop();
        }
        if (key.getKeyCode() == KeyEvent.VK_E) {
            levelMap.escape();
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void keyTyped(KeyEvent arg0) {
        // TODO Auto-generated method stub
        
    }
    
    public void setOpenedFile(String openedFile) {
        this.openedFile = openedFile;
        this.setTitle("jLaby " + openedFile);
    }

}
