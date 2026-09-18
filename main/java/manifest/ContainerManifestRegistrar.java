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
        int feet = Integer.parseInt(parts[2]);
        double netLoad = Double.parseDouble(parts[3]);

        if (feet != 20 && feet != 40) {
            throw new IllegalArgumentException("Container size must be 20 or 40 feet");
        }
        if (registration.isBlank() || seal.isBlank()) {
            throw new IllegalArgumentException("Registration and seal are required");
        }

        return new ContainerUnit(registration, feet, netLoad, seal);
    }
}