package manifest;

import model.LiquidTank;
import model.LoadUnit;

public class LiquidManifestRegistrar extends ManifestRegistrar {
    @Override
    protected boolean matches(String line) {
        return line != null && line.startsWith("LIQ;");
    }

    @Override
    protected LoadUnit createUnit(String line) {
        String[] parts = splitFields(line, 4);
        String product = parts[1];
        double liters = Double.parseDouble(parts[2]);
        double density = Double.parseDouble(parts[3]);

        if (product.isBlank() || liters <= 0 || density <= 0) {
            throw new IllegalArgumentException("Invalid liquid record");
        }

        return new LiquidTank(product, liters, density);
    }
}