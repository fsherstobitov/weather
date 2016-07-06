package com.centre.it.data.service;

import com.centre.it.entity.model.WeatherStatus;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

class OpenWeatherMap implements WeatherService {

    private static final String API_KEY = "12324d843ed075b29a7b3250fd4b6856";
    private static final String UNITS = "metric";

    @Override
    public WeatherStatus getWeatherStatus(String cityName) {
        Map<String, Object> params = new HashMap<>();
        params.put("q", cityName);
        params.put("APPID", API_KEY);
        params.put("units", UNITS);
        RestTemplate restTemplate = new RestTemplate();
        JsonNode response = restTemplate.getForObject("http://api.openweathermap.org/data/2.5/weather?q={q}&APPID={APPID}&units={units}", JsonNode.class, params);
        double temp = 0.0;
        int pressure = 0;
        int humidity = 0;
        if (response.has("main")) {
            JsonNode main = response.get("main");
            temp = main.get("temp").asDouble(0.0);
            pressure = main.get("pressure").asInt(0);
            humidity = main.get("humidity").asInt(0);
        }
        return new WeatherStatus(temp, humidity, pressure, LocalDateTime.now());
    }
}
