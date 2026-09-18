package service;

import model.LoadUnit;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class LoadDistributor {

    private LoadDistributor() {
    }

    public static DistributionResult distribute(List<LoadUnit> units) {
        List<LoadUnit> sortedByWeightDescending = new ArrayList<>(units);
        sortedByWeightDescending.sort(Comparator.comparingDouble(LoadUnit::weightInTons).reversed());

        Bays bays = Bays.getInstance();
        Map<String, List<LoadUnit>> byBay = new LinkedHashMap<>();
        Map<String, Double> remainingCapacity = new LinkedHashMap<>();
        for (String bay : bays.order) {
            byBay.put(bay, new ArrayList<>());
            remainingCapacity.put(bay, bays.capacityOf(bay));
        }

        List<LoadUnit> unassigned = new ArrayList<>();
        for (LoadUnit unit : sortedByWeightDescending) {
            String chosenBay = null;
            for (String bay : bays.order) {
                if (remainingCapacity.get(bay) >= unit.weightInTons()) {
                    chosenBay = bay;
                    break;
                }
            }
            if (chosenBay == null) {
                unassigned.add(unit);
            } else {
                byBay.get(chosenBay).add(unit);
                remainingCapacity.put(chosenBay, remainingCapacity.get(chosenBay) - unit.weightInTons());
            }
        }

        return new DistributionResult(byBay, unassigned);
    }
}
