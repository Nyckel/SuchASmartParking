import java.util.HashMap;

public class ParkingDataManager {
    private static HashMap<Integer, Character> resourceMap;

    public static void setResourceMap(HashMap<Integer, Character> rMap) {
        System.out.println("Setting resource map to " + rMap);
        resourceMap = new HashMap<>();
        resourceMap.putAll(rMap);
    }
    public static void updateResourceMap(HashMap<Integer, Character> rMap) {
        System.out.println("Updating resource map with " + rMap.keySet().size() + " values");
        System.out.println("Size before: " + resourceMap.size());
        for (Integer k : rMap.keySet()) System.out.println(k + " -> " + rMap.get(k));
        resourceMap.putAll(rMap);
        System.out.println("Size after: " + resourceMap.size());
    }

    public static char getSlotStatus(String sid) {
        if (resourceMap == null) {
            System.out.println("[ResourceManager] ResourceMap is null");
            return ' ';
        }
        if (null == resourceMap.get(Integer.parseInt(sid))) {
            System.out.println("[ResourceManager] Resource is null for sid " + sid);
            return ' ';
        }

        for (Integer k : resourceMap.keySet()) {
            System.out.println("In map : " + k + "-> " + resourceMap.get(k));
        }
        return resourceMap.get(Integer.parseInt(sid));
    }
}
