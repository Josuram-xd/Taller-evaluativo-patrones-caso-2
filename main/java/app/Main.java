package app;

import factory.BulkTerminalFactory;
import factory.ContainerTerminalFactory;
import factory.TerminalFactory;
import manifest.BulkManifestRegistrar;
import manifest.ContainerManifestRegistrar;
import manifest.ManifestRegistrar;
import manifest.ManifestResult;
import model.LoadUnit;
import model.StowagePlan;
import model.TerminalType;
import service.PlanningOutcome;
import service.StowagePlanPrinter;
import service.StowagePlanner;

import java.time.LocalDate;
import java.util.List;

public final class Main {

    public static void main(String[] args) {
        System.out.println("=== TERMINAL FLUVIAL DE BARRANCABERMEJA ===");

        runContainerDemo();
        runBulkImbalanceDemo();
        runBuilderValidationDemo();
    }

    private static void runContainerDemo() {
        List<String> lines = List.of(
                "CNT;MSKU1234567;40;18.5;SELLO-8891",
                "CNT;TCLU8877213;40;21.90;SELLO-1002",
                "CNT;HLXU4432198;20;12.40;SELLO-1003",
                "CNT;MSCU7788221;40;24.10;SELLO-1004",
                "CNT;CMAU9911003;20;9.80;SELLO-1005",
                "CNT;OOLU5567321;40;19.75;SELLO-1006",
                "CNT;TGHU3321987;20;11.20;SELLO-1007",
                "CNT;FCIU6654321;40;23.40;SELLO-1008",
                "CNT;SEGU8890012;20;10.60;SELLO-1009",
                "CNT;APLU4456789;40;20.15;SELLO-1010",
                "GRA;CARBON-TERMICO;850;13.5",
                "CNT;MSKU9999999;XX;18.5;SELLO-9999"
        );

        ManifestRegistrar registrar = new ContainerManifestRegistrar();
        ManifestResult result = registrar.process(lines);
        StowagePlanPrinter.printManifestSummary("CONTAINERS", lines.size(), result);

        TerminalFactory factory = new ContainerTerminalFactory();
        StowagePlanner planner = new StowagePlanner(factory);
        PlanningOutcome outcome = planner.plan(
                "PE-2026-0148", LocalDate.now().plusDays(5), "BZ-4417", result.units());

        StowagePlan plan = outcome.builder()
                .plannerName("J. Ramirez")
                .build();

        StowagePlanPrinter.print(plan, outcome, factory);
    }

    private static void runBulkImbalanceDemo() {
        System.out.println();
        System.out.println("=== BULK SOLID TERMINAL: FORCED IMBALANCE ===");

        List<String> lines = List.of(
                "GRA;CARBON-TERMICO;500;10.0",
                "GRA;COQUE-METALURGICO;500;8.0",
                "GRA;CLINKER;200;5.0"
        );

        ManifestRegistrar registrar = new BulkManifestRegistrar();
        ManifestResult result = registrar.process(lines);
        StowagePlanPrinter.printManifestSummary("BULK_SOLID", lines.size(), result);

        TerminalFactory factory = new BulkTerminalFactory();
        StowagePlanner planner = new StowagePlanner(factory);
        PlanningOutcome outcome = planner.plan(
                "PE-2026-0149", LocalDate.now().plusDays(3), "BZ-5521", result.units());

        StowagePlan plan = outcome.builder().build();

        StowagePlanPrinter.print(plan, outcome, factory);
    }

    private static void runBuilderValidationDemo() {
        System.out.println();
        System.out.println("=== BUILDER VALIDATION: MISSING VESSEL REGISTRATION ===");

        try {
            List<LoadUnit> units = new BulkManifestRegistrar()
                    .process(List.of("GRA;CARBON-TERMICO;100;5.0"))
                    .units();

            new StowagePlan.Builder()
                    .planNumber("PE-2026-0150")
                    .departureDate(LocalDate.now().plusDays(1))
                    .terminalType(TerminalType.BULK_SOLID)
                    .stowageDistribution(java.util.Map.of("B1", units))
                    .totalWeight(units.get(0).weightInTons())
                    .build();
        } catch (IllegalStateException e) {
            System.out.println("Build failed as expected: " + e.getMessage());
        }
    }
}
