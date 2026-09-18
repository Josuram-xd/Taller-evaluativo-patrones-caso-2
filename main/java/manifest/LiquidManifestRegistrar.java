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

        double liters;
        double density;
        try {
            liters = Double.parseDouble(parts[2]);
            density = Double.parseDouble(parts[3]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("liters or density is not numeric");
        }

        if (product.isBlank() || liters <= 0 || density <= 0) {
            throw new IllegalArgumentException("invalid liquid record");
        }

        return new LiquidTank(product, liters, density);
    }

    @Override
    protected String registrarName() {
        return "liquid";
    }
}