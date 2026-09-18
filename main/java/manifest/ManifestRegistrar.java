package manifest;

import model.LoadUnit;

import java.util.ArrayList;
import java.util.List;

public abstract class ManifestRegistrar {
    public final ManifestResult process(List<String> lines) {
        List<LoadUnit> accepted = new ArrayList<>();
        List<String> rejected = new ArrayList<>();

        for (String line : lines) {
            try {
                if (line == null || !matches(line)) {
                    throw new IllegalArgumentException("does not match the " + registrarName() + " registrar");
                }
                accepted.add(createUnit(line));
            } catch (RuntimeException e) {
                rejected.add(line + " -> " + e.getMessage());
            }
        }

        return new ManifestResult(accepted, rejected);
    }

    protected abstract boolean matches(String line);

    protected abstract LoadUnit createUnit(String line);

    protected abstract String registrarName();

    protected static String[] splitFields(String line, int expectedFields) {
        String[] fields = line.split(";", -1);
        if (fields.length != expectedFields) {
            throw new IllegalArgumentException("Invalid number of fields");
        }
        return fields;
    }
}