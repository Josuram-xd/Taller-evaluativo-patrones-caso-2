package manifest;

import model.ContainerUnit;
import model.LoadUnit;

public class ContainerManifestRegistrar extends ManifestRegistrar {
    @Override
    protected boolean matches(String line) {
        return line != null && line.startsWith("CNT;");
    }

    @Override
    protected LoadUnit createUnit(String line) {
        String[] parts = splitFields(line, 5);
        String registration = parts[1];
        String seal = parts[4];

        int feet;
        try {
            feet = Integer.parseInt(parts[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("feet is not numeric");
        }

        double netLoad;
        try {
            netLoad = Double.parseDouble(parts[3]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("net load is not numeric");
        }

        if (feet != 20 && feet != 40) {
            throw new IllegalArgumentException("container size must be 20 or 40 feet");
        }
        if (registration.isBlank() || seal.isBlank()) {
            throw new IllegalArgumentException("registration and seal are required");
        }

        return new ContainerUnit(registration, feet, netLoad, seal);
    }

    @Override
    protected String registrarName() {
        return "container";
    }
}