package devhead.ru.jlaby;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Arrays;

import javax.swing.JEditorPane;
import javax.swing.JTextArea;

public class ProgramRunner implements Runnable {
    
    private String gameType;
    private JTextArea feedBack;
    private JEditorPane codeArea;
    private LevelMap levelMap;
    
    public ProgramRunner(JEditorPane codeArea, JTextArea feedBack, LevelMap levelMap, String gameType) {
        this.codeArea = codeArea;
        this.feedBack = feedBack;
        this.gameType = gameType;
        this.levelMap = levelMap;
    }

    @Override
    public void run() {
        try {
            buildLib();
        } catch (FileNotFoundException e) {
            // simple skip
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            buildGame();
        } catch (FileNotFoundException e) {
            // simple skip
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    
    private void buildLib() throws IOException {
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(Laby.MODS_PATH + "/" + gameType + "/lib/build"));
        String[] commandLine = reader.readLine().split(" ");
        reader.close();
        ProcessBuilder pb = new ProcessBuilder(Arrays.asList(commandLine));
        pb.directory(new File(Laby.MODS_PATH + "/" + gameType + "/lib"));
        pb.redirectErrorStream(true);
        Process process = pb.start();
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String s;
        while ((s = stdIn.readLine()) != null) {
                System.out.println(s);
        }
    }
    
    private void buildGame() throws IOException {
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(Laby.MODS_PATH + "/" + gameType + "/build"));
        String[] commandLine = reader.readLine().split(" ");
        reader.close();
        ProcessBuilder pb = new ProcessBuilder(Arrays.asList(commandLine));
        pb.directory(new File(Laby.MODS_PATH + "/" + gameType));
        pb.redirectErrorStream(true);
        Process process = pb.start();
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String s;
        while ((s = stdIn.readLine()) != null) {
                System.out.println(s);
        }
    }
 
}
