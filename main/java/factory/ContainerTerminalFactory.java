package factory;

import model.PlanDeEstiba;
import model.TerminalType;

public class ContainerTerminalFactory implements TerminalFactory {
    @Override
    public LoadingEquipment createLoadingEquipment() {
        return new EquipmentImpl("Mobile Port Crane GP-1", 180.0);
    }

    @Override
    public StabilityValidator createStabilityValidator() {
        return new ValidatorImpl(5.0, 2.10);
    }

    @Override
    public ShipmentDocument createShipmentDocument() {
        return plan -> "List of units with seal numbers by container";
    }

    @Override
    public TerminalType type() {
        return TerminalType.CONTAINERS;
    }
}