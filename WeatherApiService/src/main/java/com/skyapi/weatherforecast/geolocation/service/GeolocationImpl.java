package com.skyapi.weatherforecast.geolocation.service;

import com.ip2location.IP2Location;
import com.ip2location.IPResult;
import com.skyapi.weatherforecast.commonn.Location;
import com.skyapi.weatherforecast.exception.GeoLocationExcption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class GeolocationImpl implements GeoLocationService {
    private static final Logger LOGGER= LoggerFactory.getLogger(GeolocationImpl.class);
    private String DBpath= "ip2locdb/IP2LOCATION-LITE-DB3.BIN";
    IP2Location ip2Location= new IP2Location();

    public GeolocationImpl() {
        try {
            ip2Location.Open(DBpath);
        } catch (IOException ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
    }

    @Override
    public Location getLocation(String ipAddress) throws GeoLocationExcption {
        try {
            IPResult ipResult = ip2Location.IPQuery(ipAddress);
            if(!"OK".equals(ipResult.getStatus())){
                throw new GeoLocationExcption("Gelocation failed with status" + ipResult.getStatus());
            }
            return new Location(ipResult.getCountryLong(), ipResult.getRegion(), ipResult.getCity(), ipResult.getCountryShort());
        } catch (IOException ex) {
            throw new GeoLocationExcption("Error querying IP database", ex);
        }
    }
}
