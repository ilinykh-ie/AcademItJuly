package ru.ilinykh.temperature;

public final class TemperatureConverter {

    public TemperatureConverter() {
    }

    public double convert(double temperature, Dimensions dimension1, Dimensions dimension2) {
        if (dimension1 == dimension2) {
            return temperature;
        }

        if (dimension1 == Dimensions.CELSIUS) {
            if (dimension2 == Dimensions.FAHRENHEIT) {
                return temperature * 9 / 5 + 32;
            }

            return temperature + 273.15;
        } else if (dimension1 == Dimensions.FAHRENHEIT) {
            if (dimension2 == Dimensions.CELSIUS) {
                return (temperature - 32) * 5 / 9;
            }

            return (temperature - 32) * 5 / 9 + 273.15;
        } else {
            if (dimension2 == Dimensions.CELSIUS) {
                return temperature - 273.15;
            }

            return (temperature - 273.15) * 9 / 5 + 32;
        }
    }
}
