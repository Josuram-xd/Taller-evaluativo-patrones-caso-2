package service;

import factory.LoadingEquipment;
import factory.StabilityValidator;
import factory.TerminalFactory;
import model.LoadUnit;
import model.StowagePlan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class StowagePlanner {

    private final TerminalFactory factory;

    public StowagePlanner(TerminalFactory factory) {
        this.factory = factory;
    }

    public PlanningOutcome plan(String planNumber, LocalDate departureDate, String vesselRegistration,
                                 List<LoadUnit> units) {
        DistributionResult distribution = LoadDistributor.distribute(units);
        Map<String, List<LoadUnit>> byBay = distribution.byBay();

        double totalWeight = sumWeight(byBay.values().stream().flatMap(List::stream).toList());
        double bowWeight = sumWeight(bowUnits(byBay));
        double sternWeight = totalWeight - bowWeight;

        StabilityValidator validator = factory.createStabilityValidator();
        LoadingEquipment equipment = factory.createLoadingEquipment();

        double imbalancePercent = totalWeight == 0 ? 0 : Math.abs(bowWeight - sternWeight) / totalWeight * 100.0;
        boolean imbalanceOk = imbalancePercent <= validator.maxImbalancePercent();
        double tonsToTransfer = imbalanceOk ? 0 : tonsToTransfer(bowWeight, sternWeight, totalWeight, validator.maxImbalancePercent());

        double draft = 0.55 + totalWeight / 4200.0;
        boolean draftOk = draft <= validator.maxDraftMeters();

        String approvalStatus = approvalStatus(imbalanceOk, draftOk);
        String loadingTime = formatLoadingTime(totalWeight, equipment.rateTonsPerHour());
        Map<String, Double> occupancy = occupancyByBay(byBay);
        List<String> sequence = loadingSequence(byBay);

        StowagePlan.Builder builder = new StowagePlan.Builder()
                .planNumber(planNumber)
                .departureDate(departureDate)
                .vesselRegistration(vesselRegistration)
                .terminalType(factory.type())
                .stowageDistribution(byBay)
                .totalWeight(totalWeight)
                .loadingSequence(sequence)
                .occupancyPercentages(occupancy)
                .imbalancePercent(round(imbalancePercent, 2))
                .tonsToTransfer(round(tonsToTransfer, 2))
                .draft(round(draft, 2))
                .loadingTime(loadingTime)
                .approvalStatus(approvalStatus);

        return new PlanningOutcome(builder, distribution.unassignedUnits(), bowWeight, sternWeight, equipment, validator);
    }

    private static List<LoadUnit> bowUnits(Map<String, List<LoadUnit>> byBay) {
        List<LoadUnit> bowUnits = new ArrayList<>();
        for (String bay : Bays.getInstance().bow) {
            bowUnits.addAll(byBay.get(bay));
        }
        return bowUnits;
    }

    private static double sumWeight(List<LoadUnit> units) {
        return units.stream().mapToDouble(LoadUnit::weightInTons).sum();
    }

    private static double tonsToTransfer(double bowWeight, double sternWeight, double totalWeight, double maxImbalancePercent) {
        double currentDifference = Math.abs(bowWeight - sternWeight);
        double targetDifference = maxImbalancePercent / 100.0 * totalWeight;
        return Math.max(0, (currentDifference - targetDifference) / 2.0);
    }

    private static String approvalStatus(boolean imbalanceOk, boolean draftOk) {
        if (imbalanceOk && draftOk) {
            return "APPROVED";
        }
        if (!imbalanceOk && !draftOk) {
            return "NOT APPROVED (imbalance and draft)";
        }
        return imbalanceOk ? "NOT APPROVED (draft)" : "NOT APPROVED (imbalance)";
    }

    private static String formatLoadingTime(double totalWeight, double rateTonsPerHour) {
        double totalMinutes = totalWeight / rateTonsPerHour * 60.0;
        long hours = (long) (totalMinutes / 60);
        long minutes = Math.round(totalMinutes - hours * 60);
        if (minutes == 60) {
            hours += 1;
            minutes = 0;
        }
        return hours + " h " + minutes + " min";
    }

    private static Map<String, Double> occupancyByBay(Map<String, List<LoadUnit>> byBay) {
        Bays bays = Bays.getInstance();
        Map<String, Double> occupancy = new LinkedHashMap<>();
        for (String bay : bays.order) {
            double bayWeight = sumWeight(byBay.get(bay));
            occupancy.put(bay, round(bayWeight / bays.capacityOf(bay) * 100.0, 1));
        }
        return occupancy;
    }

    private static List<String> loadingSequence(Map<String, List<LoadUnit>> byBay) {
        List<String> sequence = new ArrayList<>();
        int step = 1;
        for (String bay : Bays.getInstance().order) {
            List<LoadUnit> bayUnits = new ArrayList<>(byBay.get(bay));
            bayUnits.sort(Comparator.comparingDouble(LoadUnit::weightInTons).reversed());
            for (LoadUnit unit : bayUnits) {
                String extra = unit.extraInfo().isEmpty() ? "" : unit.extraInfo() + " ";
                sequence.add(String.format(Locale.US, "%d. %s <- %s %s%.2f t", step++, bay, unit.id(), extra, unit.weightInTons()));
            }
        }
        return sequence;
    }

    private static double round(double value, int decimals) {
        double factor = Math.pow(10, decimals);
        return Math.round(value * factor) / factor;
    }
}
