package com.skyapi.weatherforecast.location;


import com.skyapi.weatherforecast.commonn.Location;
import com.skyapi.weatherforecast.commonn.RealtimeWeather;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class LocationRepositoryTest {

    @Autowired
    private LocationRepository locationRepository;

    @Test
    public void testCreateLocation(){
        Location location = new Location();
        location.setCode("NYC");
        location.setCityName("New York City");
        location.setCountryCode("US");
        location.setCountryName("USA");
        location.setRegionName("New York State");
         location.setEnabled(true);

        Location savedLocation = locationRepository.save(location);
        assertThat(savedLocation).isNotNull();
        assertThat(savedLocation.getCode()).isEqualTo("NYC");
    }

    @Test
    public void testFindUntrashed(){
        List<Location> location = locationRepository.findUntrashed();
        assertThat(location).isNotNull();
       location.forEach(System.out::println);

    }

    @Test
    public void testLocationNotFound(){
        String code = "XYZ";
        Location location = locationRepository.findByCode(code);
        assertThat(location).isNull();
    }

    @Test
    public void testFindLocationByCode(){
        String code = "NYC";
        Location location = locationRepository.findByCode(code);
        assertThat(location).isNotNull();
        System.out.println(location);
    }

    @Test
    public void testTrashedByCode(){
        String code = "NYC";
        locationRepository.trashedByCode(code);
        Location location = locationRepository.findByCode(code);
        assertThat(location).isNull();
    }
    @Test
    public void testAddRealtimeWeatherData(){
        String code = "DEHI";
        Location location = locationRepository.findByCode(code);
        RealtimeWeather realtimeWeather = location.getRealtimeWeather();

        if(realtimeWeather == null){
           realtimeWeather = new RealtimeWeather();
           realtimeWeather.setLocation(location);
           location.setRealtimeWeather(realtimeWeather);
        }
        realtimeWeather.setTemperature(15);
        realtimeWeather.setHumidity(70);
        realtimeWeather.setWindSpeed(20);
        realtimeWeather.setPrecipitation(40);
        realtimeWeather.setPressure( 1900);
        realtimeWeather.setStatus("Sunny");
        realtimeWeather.setLastUpdated(new Date());
        Location updatedLocation= locationRepository.save(location);
        assertThat(updatedLocation.getRealtimeWeather().getLocationCode()).isEqualTo(code);

    }
}
