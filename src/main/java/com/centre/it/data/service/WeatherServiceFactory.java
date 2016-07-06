package com.centre.it.data.service;

import com.centre.it.entity.data.WeatherRepository;
import org.springframework.stereotype.Component;

@Component
public class WeatherServiceFactory {

    public WeatherService getWeatherService(WeatherRepository.WeatherServices service) {
        WeatherService result;
        switch (service) {
            case OpenWeatherMap:
                result = new OpenWeatherMap();
                break;
            case WorldWeatherOnline:
                result = new WorldWeatherOnline();
                break;
            default:
                result = new OpenWeatherMap();
                break;
        }
        return result;
    }
}
