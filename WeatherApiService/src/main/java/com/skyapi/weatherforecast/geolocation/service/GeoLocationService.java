package com.skyapi.weatherforecast.geolocation.service;


import com.skyapi.weatherforecast.commonn.Location;
import com.skyapi.weatherforecast.exception.GeoLocationExcption;

public interface GeoLocationService {
    Location getLocation(String ipAddress) throws GeoLocationExcption;
}
