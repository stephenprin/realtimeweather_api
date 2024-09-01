package com.skyapi.weatherforecast.location.service;

import com.skyapi.weatherforecast.commonn.Location;
import com.skyapi.weatherforecast.location.LocationRepository;
import com.skyapi.weatherforecast.location.exception.LocationNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class LocationImpl implements LocationService{

    private LocationRepository locationRepository;

    public LocationImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location createLocation(Location location) {
        return locationRepository.save(location);

    }

    @Override
    public List<Location> listLocations() {
        return locationRepository.findUntrashed();
    }

    public Location findLocationByCode(String code){
        return locationRepository.findByCode(code);
    }

    @Override
    public Location updateLocation(Location locationRequest) throws LocationNotFoundException {
         String code = locationRequest.getCode();
            Location location = locationRepository.findByCode(code);
            if(location == null){
                throw new LocationNotFoundException("Location not found" +  code);
            }
            location.setCityName(locationRequest.getCityName());
            location.setCountryCode(locationRequest.getCountryCode());
            location.setCountryName(locationRequest.getCountryName());
            location.setRegionName(locationRequest.getRegionName());
            location.setEnabled(locationRequest.isEnabled());
            return locationRepository.save(location);
    }

    @Override
    public void trashedByCode(String code) throws LocationNotFoundException {
        Location location = locationRepository.findByCode(code);
        if(location==null){
            throw new LocationNotFoundException("Location not found" + code);
        }
        locationRepository.trashedByCode(code);

    }
}
