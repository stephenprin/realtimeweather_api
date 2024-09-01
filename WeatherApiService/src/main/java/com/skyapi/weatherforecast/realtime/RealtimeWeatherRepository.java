package com.skyapi.weatherforecast.realtime;

import com.skyapi.weatherforecast.commonn.RealtimeWeather;
import org.springframework.data.repository.CrudRepository;

public interface RealtimeWeatherRepository extends CrudRepository<RealtimeWeather, String> {

}
