package com.centre.it.data;

import com.centre.it.data.service.WeatherService;
import com.centre.it.data.service.WeatherServiceFactory;
import com.centre.it.entity.data.WeatherRepository;
import com.centre.it.entity.model.WeatherStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CachingWeatherRepositoryTest {

    @Mock private WeatherServiceFactory factory;
    @Mock private WeatherService service;

    @Test
    public void testGetWeatherStatus() throws Exception {
        String cityName = "Moscow";
        WeatherRepository.WeatherServices weatherService = WeatherRepository.WeatherServices.OpenWeatherMap;
        WeatherStatus status = new WeatherStatus(15.0, 93, 1044, LocalDateTime.now());

        when(factory.getWeatherService(weatherService)).thenReturn(service);
        when(service.getWeatherStatus(cityName)).thenReturn(status);
        WeatherRepository repository = new CachingWeatherRepository(factory);
        WeatherStatus weatherStatus = repository.getWeatherStatus(cityName, weatherService);

        assertEquals(status, weatherStatus);
    }

    @Test
    public void testCache() {
        String cityName = "London";
        WeatherRepository.WeatherServices weatherService = WeatherRepository.WeatherServices.OpenWeatherMap;
        WeatherStatus status = new WeatherStatus(15.0, 93, 1044, LocalDateTime.now());

        when(factory.getWeatherService(weatherService)).thenReturn(service);
        when(service.getWeatherStatus(cityName)).thenReturn(status);
        WeatherRepository repository = new CachingWeatherRepository(factory);
        repository.getWeatherStatus(cityName, weatherService);
        repository.getWeatherStatus(cityName, weatherService);
        repository.getWeatherStatus(cityName, weatherService);

        verify(service, timeout(1)).getWeatherStatus(cityName);
    }
}