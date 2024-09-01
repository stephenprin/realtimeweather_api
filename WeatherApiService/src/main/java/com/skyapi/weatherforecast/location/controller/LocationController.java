package com.skyapi.weatherforecast.location.controller;

import com.skyapi.weatherforecast.commonn.Location;
import com.skyapi.weatherforecast.location.service.LocationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/v1/location")
public class LocationController {

    private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody @Valid Location location){
        Location createdLocation = locationService.createLocation(location);
        URI uri = URI.create("/v1/location/" + createdLocation.getCode());
        return ResponseEntity.created(uri).body(createdLocation);
    }

    @GetMapping("/list")

    public ResponseEntity<List<Location>> listLocations(){
        List<Location> locations = locationService.listLocations();
        if(locations.isEmpty()){
            return ResponseEntity.noContent().build();
        }
return ResponseEntity.ok(locations);

    }

    @GetMapping("/{code}")
    public ResponseEntity<Location> findLocationByCode(@PathVariable String code){
        Location location = locationService.findLocationByCode(code);
       if(location == null){
           return ResponseEntity.notFound().build();
       }
         return ResponseEntity.ok(location);
    }
    @PutMapping("/{code}")
    public ResponseEntity<Location> updateLocation(@PathVariable ("code") String code, @RequestBody @Valid Location location){
        try {
            Location updatedLocation = locationService.updateLocation(location);
            return ResponseEntity.ok(updatedLocation);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{code}/trashed")
    public ResponseEntity<Void> trashedByCode( @PathVariable("code") String code){
        System.out.println("code = " + code);
        try {
            locationService.trashedByCode(code);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
