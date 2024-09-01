package com.skyapi.weatherforecast.location.service;

import com.skyapi.weatherforecast.commonn.Location;
import com.skyapi.weatherforecast.location.exception.LocationNotFoundException;

import java.util.List;

public interface LocationService
{
    Location createLocation(Location location);

    List<Location> listLocations();
    public Location findLocationByCode(String code);
    public Location updateLocation(Location locationRequest ) throws LocationNotFoundException;

    public void trashedByCode(String code) throws LocationNotFoundException;
}
