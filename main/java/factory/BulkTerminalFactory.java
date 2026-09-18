package factory;

import model.PlanDeEstiba;
import model.TerminalType;

public class BulkTerminalFactory implements TerminalFactory {
    @Override
    public LoadingEquipment createLoadingEquipment() {
        return new EquipmentImpl("Belt conveyor BT-3", 320.0);
    }

    @Override
    public StabilityValidator createStabilityValidator() {
        return new ValidatorImpl(8.0, 1.80);
    }

    @Override
    public ShipmentDocument createShipmentDocument() {
        return plan -> "Cargo knowledge with moisture certificate";
    }

    @Override
    public TerminalType type() {
        return TerminalType.BULK_SOLID;
    }
}