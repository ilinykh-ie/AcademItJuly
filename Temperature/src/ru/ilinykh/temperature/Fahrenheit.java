package ru.ilinykh.temperature;

public class Fahrenheit implements TemperatureScale {
    @Override
    public String getName() {
        return "Градусы Фаренгейта (°F)";
    }

    @Override
    public double getCelsiusFromThis(double value) {
        return (value - 32) * 5 / 9;
    }

    @Override
    public double getThisFromCelsius(double value) {
        return (value * 9 / 5) + 32;
    }
}
