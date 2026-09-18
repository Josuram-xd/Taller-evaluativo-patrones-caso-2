package model;

public interface LoadUnit {
    String id();
    double weightInTons();
    String description();

    default String extraInfo() {
        return "";
    }
}