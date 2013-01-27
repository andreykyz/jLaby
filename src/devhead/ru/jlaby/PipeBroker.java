package devhead.ru.jlaby;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
        
    }
    
    public void backward() {
        
    }
    
    public void fastPlay() {
        
    }
    
    public void reset() {
        
    }

}
