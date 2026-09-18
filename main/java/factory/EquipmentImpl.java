package factory;

final class EquipmentImpl implements LoadingEquipment {
    private final String name;
    private final double rateTonsPerHour;

    EquipmentImpl(String name, double rateTonsPerHour) {
        this.name = name;
        this.rateTonsPerHour = rateTonsPerHour;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public double rateTonsPerHour() {
        return rateTonsPerHour;
    }
}
