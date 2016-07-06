package com.centre.it.entity;

import com.centre.it.entity.data.WeatherRepository;
import com.centre.it.entity.exception.WeatherException;
import com.centre.it.entity.model.WeatherStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class WeatherStationTest {

    @Mock private WeatherRepository repository;

    @Test
    public void testGetWeatherStatus() throws Exception {
        String cityName = "Moscow";
        String serviceName = "OpenWeatherMap";
        WeatherStatus status = new WeatherStatus(15.0, 56, 1044, LocalDateTime.now());

        when(repository.getWeatherStatus(cityName, WeatherRepository.WeatherServices.OpenWeatherMap)).thenReturn(status);
        WeatherStation station = new WeatherStation(repository);
        WeatherStatus weatherStatus = station.getWeatherStatus(cityName, serviceName);
        assertEquals(status, weatherStatus);
    }

    @Test(expected = WeatherException.class)
    public void testGetWeatherStatusIncorrectCity() throws Exception {
        String cityName = "Chelyabinsk";
        String serviceName = "OpenWeatherMap";
        WeatherStatus status = new WeatherStatus(15.0, 56, 1044, LocalDateTime.now());

        when(repository.getWeatherStatus(cityName, WeatherRepository.WeatherServices.OpenWeatherMap)).thenReturn(status);
        WeatherStation station = new WeatherStation(repository);
        WeatherStatus weatherStatus = station.getWeatherStatus(cityName, serviceName);
    }

    @Test(expected = WeatherException.class)
    public void testGetWeatherStatusIncorrectService() throws Exception {
        String cityName = "Moscow";
        String serviceName = "YandexPogoda";
        WeatherStatus status = new WeatherStatus(15.0, 56, 1044, LocalDateTime.now());

        when(repository.getWeatherStatus(cityName, WeatherRepository.WeatherServices.OpenWeatherMap)).thenReturn(status);
        WeatherStation station = new WeatherStation(repository);
        WeatherStatus weatherStatus = station.getWeatherStatus(cityName, serviceName);
    }
}