package model;

public record BulkLot(String product, double tonsDeclared, double humidityPercent) implements LoadUnit {
    @Override
    public String id() {
        return product;
    }

    @Override
    public double weightInTons() {
        return tonsDeclared * (1 + (humidityPercent / 100.0));
    }

    @Override
    public String description() {
        return "Bulk lot " + product;
    }
}