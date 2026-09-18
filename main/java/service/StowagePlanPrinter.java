package service;

import factory.TerminalFactory;
import manifest.ManifestResult;
import model.LoadUnit;
import model.StowagePlan;

import java.util.List;
import java.util.Locale;

public final class StowagePlanPrinter {

    private StowagePlanPrinter() {
    }

    public static void printManifestSummary(String registrarLabel, int linesRead, ManifestResult result) {
        System.out.printf(Locale.US, "Registrar: %s | lines read: %d | accepted: %d | rejected: %d%n",
                registrarLabel, linesRead, result.units().size(), result.rejected().size());
        for (String rejected : result.rejected()) {
            System.out.println("  [REJECTED] " + rejected);
        }
    }

    public static void print(StowagePlan plan, PlanningOutcome outcome, TerminalFactory factory) {
        System.out.println();
        System.out.println("STOWAGE PLAN No. " + plan.planNumber());
        System.out.printf(Locale.US, "Vessel: %s   Departure: %s   Terminal: %s%n",
                plan.vesselRegistration(), plan.departureDate(), plan.terminalType());
        System.out.printf(Locale.US, "Equipment: %s (%.1f t/h)%n",
                outcome.equipment().name(), outcome.equipment().rateTonsPerHour());

        System.out.println();
        System.out.printf(Locale.US, "%-6s %-10s %-10s %-10s %-8s%n", "BAY", "LOAD (t)", "CAPACITY", "OCCUPANCY", "UNITS");
        Bays bays = Bays.getInstance();
        for (String bay : bays.order) {
            List<LoadUnit> units = plan.stowageDistribution().get(bay);
            double bayWeight = units.stream().mapToDouble(LoadUnit::weightInTons).sum();
            System.out.printf(Locale.US, "%-6s %-10.2f %-10.1f %-9.1f%% %-8d%n",
                    bay, bayWeight, bays.capacityOf(bay), plan.occupancyPercentages().get(bay), units.size());
        }
        System.out.printf(Locale.US, "%-6s %-10.2f%n", "TOTAL", plan.totalWeight());

        System.out.println();
        System.out.printf(Locale.US, "Bow: %.2f t | Stern: %.2f t | Imbalance: %.2f %% (max %.2f %%) -> %s%n",
                outcome.bowWeight(), outcome.sternWeight(), plan.imbalancePercent(), outcome.validator().maxImbalancePercent(),
                plan.imbalancePercent() <= outcome.validator().maxImbalancePercent() ? "OK" : "EXCEEDED");
        if (plan.tonsToTransfer() > 0) {
            System.out.printf(Locale.US, "Tons to transfer to balance within limit: %.2f t%n", plan.tonsToTransfer());
        }
        System.out.printf(Locale.US, "Estimated draft: %.2f m (max %.2f m) -> %s%n",
                plan.draft(), outcome.validator().maxDraftMeters(),
                plan.draft() <= outcome.validator().maxDraftMeters() ? "OK" : "EXCEEDED");
        System.out.println("Estimated loading time: " + plan.loadingTime());
        System.out.println("PLAN STATUS: " + plan.approvalStatus());

        System.out.println();
        System.out.println("LOADING SEQUENCE");
        for (String step : plan.loadingSequence()) {
            System.out.println(" " + step);
        }

        if (!outcome.unassignedUnits().isEmpty()) {
            System.out.println();
            System.out.println("UNSHIPPED LOAD (did not fit in any bay):");
            for (LoadUnit unit : outcome.unassignedUnits()) {
                System.out.printf(Locale.US, " - %s (%.2f t)%n", unit.description(), unit.weightInTons());
            }
        }

        System.out.println();
        System.out.println("DOCUMENT: " + factory.createShipmentDocument().generate(plan));
    }
}
