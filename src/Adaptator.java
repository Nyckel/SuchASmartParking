public class Adaptator implements Runnable {

    private final int id;
    private final String filename;
    private final int freq;

    public Adaptator(int id, int freq, String filename) {
        this.id = id;
        this.filename = filename;
        this.freq = freq;
    }

    public void run() {

    }
}
