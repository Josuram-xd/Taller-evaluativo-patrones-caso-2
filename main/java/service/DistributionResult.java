package service;

import model.LoadUnit;

import java.util.List;
import java.util.Map;

public record DistributionResult(Map<String, List<LoadUnit>> byBay, List<LoadUnit> unassignedUnits) {
}
