package com.centre.it.ui;

import com.centre.it.entity.WeatherStation;
import com.centre.it.entity.exception.WeatherException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WeatherController {

    private final WeatherStation weatherStation;

    @Autowired
    public WeatherController(WeatherStation weatherStation) {
        this.weatherStation = weatherStation;
    }

    @RequestMapping("/")
    public String index(@CookieValue(name = "selectedCity", required = false) String selectedCity, @CookieValue(name = "selectedService", required = false) String selectedService, Model model) {
        model.addAttribute("selectedCity", selectedCity);
        model.addAttribute("selectedService", selectedService);
        model.addAttribute("cityList", weatherStation.getAvailableCities());
        model.addAttribute("serviceList", weatherStation.getAvailableServices());
        return "index";
    }

    @RequestMapping(path = "weather_status", method = RequestMethod.GET)
    public ModelAndView status(@RequestParam("city") String city, @RequestParam("service") String serviceName, HttpServletResponse response) {
        response.addCookie(new Cookie("selectedCity", city));
        response.addCookie(new Cookie("selectedService", serviceName));
        Map<String, Object> model = new HashMap<>();
        try {
            model.put("city", city);
            model.put("status", weatherStation.getWeatherStatus(city, serviceName));
            return new ModelAndView("weather", model);
        } catch (WeatherException e) {
            model.put("error", e.getMessage());
            return new ModelAndView("error", model);
        }
    }
}
