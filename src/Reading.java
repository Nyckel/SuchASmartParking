public class Reading {

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }

    private int sid;
    private char value;

    public Reading (int captorSid,  char valueRead) {
        sid = captorSid;
        value = valueRead;
    }
}