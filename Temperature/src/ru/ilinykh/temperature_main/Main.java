package ru.ilinykh.temperature_main;

import ru.ilinykh.temperature.CelsiusScale;
import ru.ilinykh.temperature.FahrenheitScale;
import ru.ilinykh.temperature.KelvinScale;
import ru.ilinykh.temperature.TemperatureWindow;

public class Main {
    public static void main(String[] args) {
        TemperatureWindow temperature = new TemperatureWindow(new CelsiusScale(), new KelvinScale(), new FahrenheitScale());
        temperature.show();
    }
}