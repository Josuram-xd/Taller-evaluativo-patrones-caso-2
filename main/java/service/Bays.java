package service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class Bays {
    private static final Bays INSTANCE = new Bays();

    public final List<String> order = List.of("B1", "B2", "B3", "B4");
    public final Set<String> bow = Set.of("B1", "B2");

    private final Map<String, Double> capacity = new LinkedHashMap<>();

    private Bays() {
        capacity.put("B1", 600.0);
        capacity.put("B2", 750.0);
        capacity.put("B3", 750.0);
        capacity.put("B4", 600.0);
    }

    public static Bays getInstance() {
        return INSTANCE;
    }

    public double capacityOf(String bay) {
        return capacity.get(bay);
    }
}
