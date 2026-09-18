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
        double tonsDeclared = Double.parseDouble(parts[2]);
        double humidity = Double.parseDouble(parts[3]);

        if (product.isBlank() || tonsDeclared <= 0 || humidity < 0) {
            throw new IllegalArgumentException("Invalid bulk record");
        }

        return new BulkLot(product, tonsDeclared, humidity);
    }
}