import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ParkingSlotService {

    private ServerSocket serverSocket;

    public ParkingSlotService(int port) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started");
            start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void start() {
        while (true) {
            if (!serverSocket.isClosed()) {
                Socket clientSocket;
                try {
                    clientSocket = serverSocket.accept();
                    System.out.println("[Server] New client connected");

                    BufferedReader r = new BufferedReader(
                            new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8));


                    String request;
                    PrintWriter pw = new PrintWriter(clientSocket.getOutputStream(), true);

                    while ((request = r.readLine()) != null) {
                        String[] parts = request.split(":");
                        String requestType = parts[0];

                        System.out.println("Received " + request);
                        switch (requestType) {
                            case "ParkingSlot":
                                if (parts.length == 2) {
                                    System.out.println("Querying state of " + parts[1]);
                                    char res = ParkingDataManager.getSlotStatus(parts[1]);
                                    if (res == ' ') pw.write("Error: No data was found for given SID: " + parts[1] + "\n");
                                    else pw.write("ParkingSlot:" + res + "\n");
                                    System.out.println("res:" + res);

                                    pw.flush();
                                }
                                break;
                        }


                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
