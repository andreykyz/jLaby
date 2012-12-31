package devhead.ru.jlaby;

import javax.swing.JTextArea;

public class ProgramRunner implements Runnable {
    
    private String gameType;
    private JTextArea feedBack;
    private JTextArea codeArea;
    
    public ProgramRunner(JTextArea codeArea, JTextArea feedBack, String gameType) {
        this.codeArea = codeArea;
        this.feedBack = feedBack;
        this.gameType = gameType;
    }

    @Override
    public void run() {

    }

}
