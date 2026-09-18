package factory;

import model.StowagePlan;

public interface ShipmentDocument {
    String generate(StowagePlan plan);
}