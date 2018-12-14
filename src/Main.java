import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class Main {

    public static void main(String[] args) {
        String sensorsfolder = "ressources/description";
        ArrayList<String> filesRead = new ArrayList<>();
        ArrayList<Adaptator> adaptators = new ArrayList<>();
        ArrayList<Reading> readingStream = new ArrayList<>();
        HashMap<Integer, Character> readingMap = new HashMap<>();
        ParkingDataManager.setResourceMap(readingMap);

        // ADAPTATION
        ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(adaptators.size() + 2);
        Adaptator.setPool(scheduledPool);

        Runnable fileReader = new Runnable() {
            @Override
            public void run() {
                try {
                    File folder = new File(sensorsfolder);
                    for (File child : folder.listFiles())
                    {
                        System.out.println(child.getName());
                        if (child.isFile() && !filesRead.contains(child.getName())) {
                            BufferedReader reader = new BufferedReader(new FileReader(child));
                            String line;
                            while ((line = reader.readLine()) != null) {
                                String[] data = line.split(" ");
                                adaptators.add(new Adaptator(readingStream, Integer.parseInt(data[0]), Integer.parseInt(data[1]), data[2]));
                            }
                            filesRead.add(child.getName());
                            reader.close();
                        }
                    }
                }
                catch(Exception e) {
                    System.out.println("Exception : " + e);
                    System.exit(0);
                }
            }
        };
        scheduledPool.scheduleAtFixedRate(fileReader, 0, 1, TimeUnit.SECONDS);

        // FILTRAGE

        Runnable filter = new Runnable() {
            @Override
            public void run() {
//                readingMap.putAll(
                readingStream.stream()
                        .map(reading -> new Reading(reading.getSid(), reading.getValue() == '1' ? 'D' : 'N'))
                        .filter(reading -> !(readingMap.containsKey(reading.getSid()))
                                || readingMap.get(reading.getSid()) != reading.getValue())
                        .forEach(reading -> readingMap.put(reading.getSid(), reading.getValue()));

                readingStream.clear();
                ParkingDataManager.updateResourceMap(readingMap);
                        //.collect(Collectors.toMap(Reading::getSid, Reading::getValue));

            }
        };
        scheduledPool.scheduleAtFixedRate(filter, 0, 50, MILLISECONDS);


        // SERVICE
        ParkingSlotService service = new ParkingSlotService(9090);

    }

}
