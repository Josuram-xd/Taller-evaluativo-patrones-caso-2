package factory;

import model.TerminalType;

public class LiquidTerminalFactory implements TerminalFactory {
    @Override
    public LoadingEquipment createLoadingEquipment() {
        return new EquipmentImpl("Loading arm BC-2", 240.0);
    }

    @Override
    public StabilityValidator createStabilityValidator() {
        return new ValidatorImpl(3.0, 1.95);
    }

    @Override
    public ShipmentDocument createShipmentDocument() {
        return plan -> "Manifest with chemical compatibility certificate";
    }

    @Override
    public TerminalType type() {
        return TerminalType.LIQUIDS;
    }
}