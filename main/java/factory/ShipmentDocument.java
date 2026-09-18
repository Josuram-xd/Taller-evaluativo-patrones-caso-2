package factory;

import model.PlanDeEstiba;

public interface ShipmentDocument {
    String generate(PlanDeEstiba plan);
}