package com.centre.it.entity.data;

import com.centre.it.entity.model.WeatherStatus;

public interface WeatherRepository {

    enum WeatherServices {
        OpenWeatherMap, WorldWeatherOnline
    }

    WeatherStatus getWeatherStatus(String cityName, WeatherServices service);
}
