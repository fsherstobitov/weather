package com.centre.it.data;

import com.centre.it.data.service.WeatherServiceFactory;
import com.centre.it.entity.data.WeatherRepository;
import com.centre.it.entity.model.WeatherStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CachingWeatherRepository implements WeatherRepository {

    private static final Map<String, WeatherStatus> statusCache = new ConcurrentHashMap<>();
    private static final long UPDATE_INTERVAL = 10;

    private final WeatherServiceFactory factory;

    @Autowired
    public CachingWeatherRepository(WeatherServiceFactory factory) {
        this.factory = factory;
    }

    @Override
    public WeatherStatus getWeatherStatus(String cityName, WeatherServices service) {
        WeatherStatus status;
        String cacheKey = constructCacheKey(cityName, service);
        if (!statusCache.containsKey(cacheKey)) {
            status = updateCacheAndGet(cityName, cacheKey, service);
        } else {
            status = statusCache.get(cacheKey);
            if (distanceInMinutes(status.getLastUpdate(), LocalDateTime.now()) > UPDATE_INTERVAL) {
                status = updateCacheAndGet(cityName, cacheKey, service);
            }
        }
        return status;
    }

    private long distanceInMinutes(LocalDateTime firstDate, LocalDateTime secondDate) {
        return Duration.between(firstDate, secondDate).get(ChronoUnit.SECONDS) / 60;
    }

    private WeatherStatus updateCacheAndGet(String cityName, String cacheKey, WeatherServices service) {
        WeatherStatus status = getWeatherStatusFromService(cityName, service);
        statusCache.put(cacheKey, status);
        return status;
    }

    private WeatherStatus getWeatherStatusFromService(String cityName, WeatherServices service) {
        return factory.getWeatherService(service).getWeatherStatus(cityName);
    }

    private String constructCacheKey(String cityName, WeatherServices service) {
        return String.format("%s@%s", cityName, service);
    }
}
