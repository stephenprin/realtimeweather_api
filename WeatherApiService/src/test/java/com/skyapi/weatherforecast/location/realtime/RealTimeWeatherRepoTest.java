package com.skyapi.weatherforecast.location.realtime;

import com.skyapi.weatherforecast.commonn.RealtimeWeather;
import com.skyapi.weatherforecast.realtime.RealtimeWeatherRepository;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class RealTimeWeatherRepoTest {

    @Autowired
    private RealtimeWeatherRepository realTimeWeatherRepository;


    @Test
    public void testUpdateRealTimeWeather(){

        String locationCode = "NYC";

        RealtimeWeather realtimeWeather = realTimeWeatherRepository.findById(locationCode).get();
        realtimeWeather.setTemperature(19);
        realtimeWeather.setHumidity(10);
        realtimeWeather.setWindSpeed(10);
        realtimeWeather.setPrecipitation(40);
        realtimeWeather.setPressure( 1700);
        realtimeWeather.setStatus("Sunny");
        realtimeWeather.setLastUpdated(new Date());
        RealtimeWeather updatedRealtimeWeather = realTimeWeatherRepository.save(realtimeWeather);
        assertThat(updatedRealtimeWeather.getHumidity()).isEqualTo(10);

        }




}
