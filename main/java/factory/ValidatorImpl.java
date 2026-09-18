package factory;

final class ValidatorImpl implements StabilityValidator {
    private final double maxImbalancePercent;
    private final double maxDraftMeters;

    ValidatorImpl(double maxImbalancePercent, double maxDraftMeters) {
        this.maxImbalancePercent = maxImbalancePercent;
        this.maxDraftMeters = maxDraftMeters;
    }

    @Override
    public double maxImbalancePercent() {
        return maxImbalancePercent;
    }

    @Override
    public double maxDraftMeters() {
        return maxDraftMeters;
    }
}
