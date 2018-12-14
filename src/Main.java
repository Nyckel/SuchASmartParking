import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String sensorsfile = "ressources/sensors.txt";
        ArrayList<Adaptator> adaptators = new ArrayList<>();


        // PHASE 1 : Lecture du fichier "ressources/sensors.txt"

        try {
            BufferedReader reader = new BufferedReader(new FileReader(sensorsfile));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(" ");
                adaptators.add(new Adaptator(Integer.parseInt(data[0]), Integer.parseInt(data[1]), data[2]));
            }
            reader.close();
        }
        catch(Exception e) {
            System.out.println("Exception : " + e);
        }


        // PHASE 2 : Ouverture d'un adaptateur pour chaque capteur

        // PHASE 3 : Filtrage des données

        // PHASE 4 : Service des données (à voir)

    }
}
