package ru.ilinykh.temperature;

public final class TemperatureConverter {
    public TemperatureConverter() {
    }

    public double convert(double temperature, TemperatureScale from, TemperatureScale to) {
        return to.getThisFromCelsius(from.getCelsiusFromThis(temperature));
    }
}
