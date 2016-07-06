package com.centre.it.data.service;

import com.centre.it.entity.model.WeatherStatus;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

class WorldWeatherOnline implements WeatherService {

    private static final String API_KEY = "5cad2310736449e0a2593313160607";
    private static final String FORMAT = "json";

    @Override
    public WeatherStatus getWeatherStatus(String cityName) {
        Map<String, Object> params = new HashMap<>();
        params.put("key", API_KEY);
        params.put("q", cityName);
        params.put("format", FORMAT);
        RestTemplate restTemplate = new RestTemplate();
        JsonNode response = restTemplate.getForObject("http://api.worldweatheronline.com/premium/v1/weather.ashx?key={key}&q={q}&format={format}", JsonNode.class, params);
        double temp = 0.0;
        int pressure = 0;
        int humidity = 0;
        if (response.has("data") && response.get("data").has("current_condition")) {
            JsonNode currentCondition = response.get("data").get("current_condition").get(0);
            temp = currentCondition.get("temp_C").asInt(0);
            pressure = currentCondition.get("pressure").asInt(0);
            humidity = currentCondition.get("humidity").asInt(0);
        }
        return new WeatherStatus(temp, humidity, pressure, LocalDateTime.now());
    }
}
