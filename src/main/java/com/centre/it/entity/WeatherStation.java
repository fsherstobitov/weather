package com.centre.it.entity;

import com.centre.it.data.CachingWeatherRepository;
import com.centre.it.entity.data.WeatherRepository;
import com.centre.it.entity.exception.WeatherException;
import com.centre.it.entity.model.WeatherStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class WeatherStation {

    private final WeatherRepository repository;
    private final List<String> cities;
    private final List<String> services;

    @Autowired
    public WeatherStation(WeatherRepository repository) {
        this.repository = repository;
        this.cities = new ArrayList<>();
        this.cities.add("Moscow");
        this.cities.add("New York");
        this.cities.add("London");
        this.services = new ArrayList<>();
        this.services.add("OpenWeatherMap");
        this.services.add("WorldWeatherOnline");
    }

    public List<String> getAvailableCities() {
        return Collections.unmodifiableList(cities);
    }

    public List<String> getAvailableServices() {
        return Collections.unmodifiableList(services);
    }

    public WeatherStatus getWeatherStatus(String cityName, String serviceName) throws WeatherException {
        if (!cities.contains(cityName)) {
            throw new WeatherException(String.format("Weather data for %s is not available", cityName));
        }
        if (!services.contains(serviceName)) {
            throw new WeatherException(String.format("Weather data from %s is not available", cityName));
        }
        WeatherStatus status;
        switch (serviceName) {
            case "OpenWeatherMap":
                status = repository.getWeatherStatus(cityName, CachingWeatherRepository.WeatherServices.OpenWeatherMap);
                break;
            case "WorldWeatherOnline":
                status = repository.getWeatherStatus(cityName, CachingWeatherRepository.WeatherServices.WorldWeatherOnline);
                break;
            default:
                status = repository.getWeatherStatus(cityName, CachingWeatherRepository.WeatherServices.OpenWeatherMap);
                break;
        }
        return status;
    }
}
