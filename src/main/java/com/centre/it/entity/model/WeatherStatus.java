package com.centre.it.entity.model;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by frodo on 06.07.16.
 */
public class WeatherStatus {

    private double tempCelcius;
    private int humidity;
    private int pressure;
    private LocalDateTime lastUpdate;

    public WeatherStatus(double tempCelcius, int humidity, int pressure, LocalDateTime lastUpdate) {
        this.tempCelcius = tempCelcius;
        this.humidity = humidity;
        this.pressure = pressure;
        this.lastUpdate = lastUpdate;
    }

    public double getTempCelcius() {
        return tempCelcius;
    }

    public int getHumidity() {
        return humidity;
    }

    public int getPressure() {
        return pressure;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }
}
