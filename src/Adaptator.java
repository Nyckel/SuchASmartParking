import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

public class Adaptator implements Runnable {

    private final int id;
    private final String filename;
    private final int freq;
    private ArrayList<Reading> stream;
    private BufferedReader reader;
    static private ScheduledExecutorService scheduledPool;

    public int getId() {
        return id;
    }

    public int getFreq() {
        return freq;
    }

    public static void setPool(ScheduledExecutorService Pool){
        scheduledPool = Pool;
    }

    public Adaptator(ArrayList<Reading> stream, int id, int freq, String filename) {
        this.stream = stream;
        this.id = id;
        this.filename = filename;
        this.freq = freq;
        try {
            this.reader = new BufferedReader(new FileReader("ressources/sensors/" + this.filename));
            Adaptator.scheduledPool.scheduleAtFixedRate(this, 0, freq, MILLISECONDS);
        }
        catch(Exception e) {
            System.out.println("Exception : " + e);
        }
    }

    @Override
    public void run() {
        try {
            char value = (char) reader.read();
            System.out.println("Setting in stream" + id + " -> " + (value == '1' ? '1' : '0'));
            stream.add(new Reading(this.id, value == '1' ? '1' : '0'));
        }
        catch (Exception e) {
            stream.add(new Reading(this.id, '0'));
        }
    }
}
