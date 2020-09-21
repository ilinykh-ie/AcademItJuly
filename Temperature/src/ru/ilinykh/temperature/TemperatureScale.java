package ru.ilinykh.temperature;

public interface TemperatureScale {
    String getName();

    double getCelsiusFromThis(double value);

    double getThisFromCelsius(double value);
}