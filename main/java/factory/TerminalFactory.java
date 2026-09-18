package factory;

import model.TerminalType;

public interface TerminalFactory {
    LoadingEquipment createLoadingEquipment();
    StabilityValidator createStabilityValidator();
    ShipmentDocument createShipmentDocument();
    TerminalType type();
}