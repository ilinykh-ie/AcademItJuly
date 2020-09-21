package ru.ilinykh.temperature;

public class Kelvin implements TemperatureScale {
    @Override
    public String getName() {
        return "Градусы Кельвина (К)";
    }

    @Override
    public double getCelsiusFromThis(double value) {
        return value - 273.15;
    }

    @Override
    public double getThisFromCelsius(double value) {
        return value + 273.15;
    }
}
