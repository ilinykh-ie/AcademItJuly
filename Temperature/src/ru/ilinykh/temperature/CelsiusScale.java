package ru.ilinykh.temperature;

public class CelsiusScale implements TemperatureScale {
    @Override
    public String getName() {
        return "Градусы Цельсия (°C)";
    }

    @Override
    public double getCelsiusFromThis(double value) {
        return value;
    }

    @Override
    public double getThisFromCelsius(double value) {
        return value;
    }
}
