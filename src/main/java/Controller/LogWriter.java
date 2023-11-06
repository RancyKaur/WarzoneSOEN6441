package Controller;
import Model.LogEntry;
import Model.Observable;
import Model.Observer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;


/**
 *class to implement the logging of events to a log file.
 */
public class LogWriter implements Observer {
    String d_fileName="log.txt";
    private static String d_Store;
    private FileWriter d_WriteFile;
    private BufferedWriter d_Bwr ;

    /**
     * Constructor for initializing FileWriter and BufferedWriter
     */
    public LogWriter() {

        try{
            d_WriteFile = new FileWriter("log.txt");
            d_Bwr = new BufferedWriter(d_WriteFile);
        } catch(IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * On change of state in Observable ,the observer function is called.
     * Tracks changes of phase,command and command effect and updates them in a log file
     * using filewriter.
     * @param p_observable Observable,in this game it is LogEntryBuffer
     */
    @Override
    public void workUpdate(Observable p_observable) {

        LogEntry l_logEntry = (LogEntry)p_observable;
        if(l_logEntry.getGamePhaseSet()){

            d_Store=l_logEntry.getPhaseValue();
            l_logEntry.setGamePhaseSet(false);
        }
        else if(l_logEntry.getCommandSet()){
            d_Store=l_logEntry.getCommand();
            l_logEntry.setCommandSet(false);
        }
        else if(l_logEntry.getMessageSet()){
            d_Store=l_logEntry.getMessage();
            l_logEntry.setMessageSet(false);
        }

        try {
            d_Bwr.newLine();
            d_Bwr.write(d_Store.toString());
            d_Bwr.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
