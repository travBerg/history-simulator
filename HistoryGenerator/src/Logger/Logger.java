package Logger;

import World.Groups.Group;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;

public class Logger {
    private final String name;
    private static final File DEBUG = new File("debugLog.txt");
    private static final File STATS = new File("statsLog.txt");

    private Logger(final String name) {
        this.name = name;
    }

    public static Logger getLogger(final String name) {
        return new Logger(name);
    }

    public static Logger getLogger(final Class c) {
        return new Logger(c.getName());
    }

    public static void clear(final boolean rmDebug, final boolean rmStats) {
        if(rmDebug && DEBUG.exists()){
            if (DEBUG.delete()) {
                System.out.println("Deleted the file: " + DEBUG.getName());
            } else {
                System.out.println("Failed to delete the file: " + DEBUG.getName());
            }
        }
        if (rmStats && STATS.exists()) {
            if (STATS.delete()) {
                System.out.println("Deleted the file: " + STATS.getName());
            } else {
                System.out.println("Failed to delete the file:" + STATS.getName());
            }
        }
    }

    public void debug(final Object o) {
        final int callersLineNumber = Thread.currentThread().getStackTrace()[1].getLineNumber();
        debug(o.toString(), callersLineNumber);
    }

    public void debug(final String msg) {
        final int callersLineNumber = Thread.currentThread().getStackTrace()[2].getLineNumber();
        debug(msg, callersLineNumber);
    }

    private void debug(final String msg, final int cLN) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try{
            if(!DEBUG.exists()){
                System.out.println("Made a new file: DEBUG");
                DEBUG.createNewFile();
            }
            PrintWriter out = new PrintWriter(new FileWriter(DEBUG, true));
            out.append("\n" + this.name + ": Line " + cLN + ":\n");
            out.append("******* " + timestamp.toString() +"******* " + "\n");
            out.append(msg + "\n");
            out.close();
        }catch(IOException e){
            System.out.println("COULD NOT WRITE TO DEBUG FILE");
        }
    }

    public void stats(final Object o) {
        stats(o.toString());
    }

    public void stats(final String msg) {
        //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try{
            if(!STATS.exists()){
                System.out.println("Made a new file: STATS");
                STATS.createNewFile();
            }
            PrintWriter out = new PrintWriter(new FileWriter(STATS, true));
            out.append(msg + "\n");
            out.close();
        }catch(IOException e){
            System.out.println("COULD NOT WRITE TO STATS FILE");
        }
    }
}
