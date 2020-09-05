package ru.ilinykh.temperature;

public enum Dimensions {
    CELSIUS("Градусы Цельсия (°C)"),
    FAHRENHEIT("Градусы Фаренгейта (°F)"),
    KELVIN("Градусы Кельвина (К)");

    private final String russianName;

    Dimensions(String russianName) {
        this.russianName = russianName;
    }

    public String getRussianName() {
        return russianName;
    }

    public static Dimensions getEnum(String string) {
        if (string.equals("Градусы Цельсия (°C)")) {
            return Dimensions.CELSIUS;
        }

        if (string.equals("Градусы Фаренгейта (°F)")) {
            return Dimensions.FAHRENHEIT;
        }

        return Dimensions.KELVIN;
    }
}
