import java.util.HashMap;

public class ParkingDataManager {
    private static HashMap<Integer, Character> resourceMap;

    public static void setResourceMap(HashMap<Integer, Character> rMap) {
        resourceMap = new HashMap<>();
        resourceMap.putAll(rMap);
    }
    public static void updateResourceMap(HashMap<Integer, Character> rMap) {
        System.out.println("Updating status of SIDs : ");
        rMap.keySet().forEach(key -> System.out.println("- " + key));
        resourceMap.putAll(rMap);
    }

    public static char getSlotStatus(String sid) {
        if (resourceMap == null) {
            return ' ';
        }
        if (null == resourceMap.get(Integer.parseInt(sid))) {
            return ' ';
        }

        return resourceMap.get(Integer.parseInt(sid));
    }
}
