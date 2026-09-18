package manifest;

import model.BulkLot;
import model.LoadUnit;

public class BulkManifestRegistrar extends ManifestRegistrar {
    @Override
    protected boolean matches(String line) {
        return line != null && line.startsWith("GRA;");
    }

    @Override
    protected LoadUnit createUnit(String line) {
        String[] parts = splitFields(line, 4);
        String product = parts[1];

        double tonsDeclared;
        double humidity;
        try {
            tonsDeclared = Double.parseDouble(parts[2]);
            humidity = Double.parseDouble(parts[3]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("tons or humidity is not numeric");
        }

        if (product.isBlank() || tonsDeclared <= 0 || humidity < 0) {
            throw new IllegalArgumentException("invalid bulk record");
        }

        return new BulkLot(product, tonsDeclared, humidity);
    }

    @Override
    protected String registrarName() {
        return "bulk";
    }
}