package manifest;

import model.LoadUnit;

import java.util.List;

public record ManifestResult(List<LoadUnit> units, List<String> rejected) {
    public ManifestResult {
        units = List.copyOf(units);
        rejected = List.copyOf(rejected);
    }
}