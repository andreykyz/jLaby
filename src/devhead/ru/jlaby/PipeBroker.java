package devhead.ru.jlaby;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class PipeBroker {

    private Ant ant;
    BufferedReader stdIn;
    BufferedWriter stdOut;

    public PipeBroker(Process process, Ant ant) {
        this.ant = ant;
        stdIn = new BufferedReader(new InputStreamReader(process.getInputStream()));
        stdOut = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()));
    }
    
    public void play() {
        
    }
    
    public void forward() {
        String inLine = null;
        String outLine = "";
        try {
            inLine = stdIn.readLine();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        if (inLine.equals("forward")) {
            ant.forward();
        } else if (inLine.equals("right")) {
            ant.right();
        } else if (inLine.equals("left")) {
            ant.left();
        } else if (inLine.equals("take")) {
            ant.take();
        } else if (inLine.equals("drop")) {
            ant.drop();
        } else if (inLine.equals("look")) {
            outLine = ant.look().toString().toLowerCase();
        } else if (inLine.substring(0, 3).equals("say")) {
            ant.say(outLine.substring(4));
        } else if (inLine.equals("escape")) {
            ant.escape();
        } else if (inLine.equals("start")) {
            //todo ????
        }
        try {
            stdOut.write(outLine);
            stdOut.newLine();
            stdOut.flush();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
   }
    
    public void backward() {
        
    }
    
    public void fastPlay() {
        
    }
    
    public void reset() {
        
    }

}
