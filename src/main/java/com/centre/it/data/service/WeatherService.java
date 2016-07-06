package com.centre.it.data.service;

import com.centre.it.entity.model.WeatherStatus;

public interface WeatherService {

    WeatherStatus getWeatherStatus(String cityName);
}
