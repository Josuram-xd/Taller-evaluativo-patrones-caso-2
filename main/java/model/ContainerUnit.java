package model;

public record ContainerUnit(String registration, int feet, double netLoad, String seal) implements LoadUnit {
    @Override
    public String id() {
        return registration;
    }

    @Override
    public double weightInTons() {
        return netLoad + (feet == 20 ? 2.2 : 3.8);
    }

    @Override
    public String description() {
        return "Container " + registration + " (seal " + seal + ")";
    }
}