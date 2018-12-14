import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ParkingSlotClient {

    private Socket socket;
    private Writer writer;
    private BufferedReader reader;

    public ParkingSlotClient(String ip, int port)
    {
        System.out.println("Creating client");
        try {
            socket = new Socket(ip, port);
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8));
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private String receive() {
        try {

            String res;
            while ((res = reader.readLine()) != null) {
                return res.split(":")[1];
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    public String getParkInSlot(int sid) {
        try {
            writer.write("ParkingSlot:" + sid + "\n");
            writer.flush();
            return receive();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void main(String[] args) {
        ParkingSlotClient client = new ParkingSlotClient("127.0.0.1", 9090);
        System.out.println("First sensor : " + client.getParkInSlot(768));
    }
}
