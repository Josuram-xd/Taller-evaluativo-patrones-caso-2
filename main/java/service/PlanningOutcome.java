package service;

import factory.LoadingEquipment;
import factory.StabilityValidator;
import model.LoadUnit;
import model.StowagePlan;

import java.util.List;

public record PlanningOutcome(
        StowagePlan.Builder builder,
        List<LoadUnit> unassignedUnits,
        double bowWeight,
        double sternWeight,
        LoadingEquipment equipment,
        StabilityValidator validator
) {
}
