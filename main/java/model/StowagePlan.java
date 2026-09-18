package model;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class StowagePlan {
    private final String planNumber;
    private final LocalDate departureDate;
    private final String vesselRegistration;
    private final TerminalType terminalType;
    private final Map<String, List<LoadUnit>> stowageDistribution;
    private final double totalWeight;
    private final List<String> loadingSequence;
    private final String timeWindow;
    private final String tugboatAssigned;
    private final String safetyNotes;
    private final Double draftRestriction;
    private final String plannerName;
    private final String approvalStatus;
    private final double imbalancePercent;
    private final double tonsToTransfer;
    private final double draft;
    private final String loadingTime;
    private final Map<String, Double> occupancyPercentages;

    private StowagePlan(Builder builder) {
        this.planNumber = builder.planNumber;
        this.departureDate = builder.departureDate;
        this.vesselRegistration = builder.vesselRegistration;
        this.terminalType = builder.terminalType;
        this.stowageDistribution = Map.copyOf(builder.stowageDistribution);
        this.totalWeight = builder.totalWeight;
        this.loadingSequence = List.copyOf(builder.loadingSequence);
        this.timeWindow = builder.timeWindow;
        this.tugboatAssigned = builder.tugboatAssigned;
        this.safetyNotes = builder.safetyNotes;
        this.draftRestriction = builder.draftRestriction;
        this.plannerName = builder.plannerName;
        this.approvalStatus = builder.approvalStatus;
        this.imbalancePercent = builder.imbalancePercent;
        this.tonsToTransfer = builder.tonsToTransfer;
        this.draft = builder.draft;
        this.loadingTime = builder.loadingTime;
        this.occupancyPercentages = Map.copyOf(builder.occupancyPercentages);
    }

    public String planNumber() { return planNumber; }
    public LocalDate departureDate() { return departureDate; }
    public String vesselRegistration() { return vesselRegistration; }
    public TerminalType terminalType() { return terminalType; }
    public Map<String, List<LoadUnit>> stowageDistribution() { return stowageDistribution; }
    public double totalWeight() { return totalWeight; }
    public List<String> loadingSequence() { return loadingSequence; }
    public String timeWindow() { return timeWindow; }
    public String tugboatAssigned() { return tugboatAssigned; }
    public String safetyNotes() { return safetyNotes; }
    public Double draftRestriction() { return draftRestriction; }
    public String plannerName() { return plannerName; }
    public String approvalStatus() { return approvalStatus; }
    public double imbalancePercent() { return imbalancePercent; }
    public double tonsToTransfer() { return tonsToTransfer; }
    public double draft() { return draft; }
    public String loadingTime() { return loadingTime; }
    public Map<String, Double> occupancyPercentages() { return occupancyPercentages; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== STOWAGE PLAN ===\n");
        sb.append("Plan number: ").append(planNumber).append("\n");
        sb.append("Departure date: ").append(departureDate).append("\n");
        sb.append("Vessel registration: ").append(vesselRegistration).append("\n");
        sb.append("Terminal type: ").append(terminalType).append("\n");
        sb.append("Status: ").append(approvalStatus).append("\n");
        sb.append("Total weight: ").append(String.format(Locale.US, "%.2f", totalWeight)).append(" tons\n");
        sb.append("Imbalance: ").append(String.format(Locale.US, "%.2f", imbalancePercent)).append("%\n");
        sb.append("Tons to transfer: ").append(String.format(Locale.US, "%.2f", tonsToTransfer)).append(" tons\n");
        sb.append("Draft: ").append(String.format(Locale.US, "%.2f", draft)).append(" m\n");
        sb.append("Loading time: ").append(loadingTime).append("\n");
        sb.append("Occupancy: ").append(occupancyPercentages).append("\n");
        sb.append("Distribution by bay: ").append(stowageDistribution).append("\n");
        sb.append("Sequence:\n");
        for (String step : loadingSequence) {
            sb.append("  - ").append(step).append("\n");
        }
        return sb.toString();
    }

    public static class Builder {
        private String planNumber;
        private LocalDate departureDate;
        private String vesselRegistration;
        private TerminalType terminalType;
        private Map<String, List<LoadUnit>> stowageDistribution;
        private double totalWeight;
        private List<String> loadingSequence = List.of();
        private String timeWindow;
        private String tugboatAssigned;
        private String safetyNotes;
        private Double draftRestriction;
        private String plannerName;
        private String approvalStatus;
        private double imbalancePercent;
        private double tonsToTransfer;
        private double draft;
        private String loadingTime;
        private Map<String, Double> occupancyPercentages = Map.of();

        public Builder planNumber(String planNumber) {
            this.planNumber = planNumber;
            return this;
        }

        public Builder departureDate(LocalDate departureDate) {
            this.departureDate = departureDate;
            return this;
        }

        public Builder vesselRegistration(String vesselRegistration) {
            this.vesselRegistration = vesselRegistration;
            return this;
        }

        public Builder terminalType(TerminalType terminalType) {
            this.terminalType = terminalType;
            return this;
        }

        public Builder stowageDistribution(Map<String, List<LoadUnit>> stowageDistribution) {
            this.stowageDistribution = stowageDistribution;
            return this;
        }

        public Builder totalWeight(double totalWeight) {
            this.totalWeight = totalWeight;
            return this;
        }

        public Builder loadingSequence(List<String> loadingSequence) {
            this.loadingSequence = loadingSequence;
            return this;
        }

        public Builder timeWindow(String timeWindow) {
            this.timeWindow = timeWindow;
            return this;
        }

        public Builder tugboatAssigned(String tugboatAssigned) {
            this.tugboatAssigned = tugboatAssigned;
            return this;
        }

        public Builder safetyNotes(String safetyNotes) {
            this.safetyNotes = safetyNotes;
            return this;
        }

        public Builder draftRestriction(Double draftRestriction) {
            this.draftRestriction = draftRestriction;
            return this;
        }

        public Builder plannerName(String plannerName) {
            this.plannerName = plannerName;
            return this;
        }

        public Builder approvalStatus(String approvalStatus) {
            this.approvalStatus = approvalStatus;
            return this;
        }

        public Builder imbalancePercent(double imbalancePercent) {
            this.imbalancePercent = imbalancePercent;
            return this;
        }

        public Builder tonsToTransfer(double tonsToTransfer) {
            this.tonsToTransfer = tonsToTransfer;
            return this;
        }

        public Builder draft(double draft) {
            this.draft = draft;
            return this;
        }

        public Builder loadingTime(String loadingTime) {
            this.loadingTime = loadingTime;
            return this;
        }

        public Builder occupancyPercentages(Map<String, Double> occupancyPercentages) {
            this.occupancyPercentages = occupancyPercentages;
            return this;
        }

        public StowagePlan build() {
            if (planNumber == null || departureDate == null || vesselRegistration == null || terminalType == null ||
                    stowageDistribution == null || totalWeight <= 0) {
                throw new IllegalStateException("Missing required plan fields.");
            }
            if (departureDate.isBefore(LocalDate.now())) {
                throw new IllegalStateException("Departure date cannot be earlier than the current system date.");
            }
            if (stowageDistribution.values().stream().allMatch(List::isEmpty)) {
                throw new IllegalStateException("The plan has no assigned units.");
            }
            return new StowagePlan(this);
        }
    }
}
