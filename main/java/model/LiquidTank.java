package model;

public record LiquidTank(String product, double liters, double densityKgPerL) implements LoadUnit {
    @Override
    public String id() {
        return product;
    }

    @Override
    public double weightInTons() {
        return liters * densityKgPerL / 1000.0;
    }

    @Override
    public String description() {
        return "Liquid tank " + product;
    }
}