package com.skyapi.weatherforecast.location;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skyapi.weatherforecast.commonn.Location;
import com.skyapi.weatherforecast.location.controller.LocationController;
import com.skyapi.weatherforecast.location.exception.LocationNotFoundException;
import com.skyapi.weatherforecast.location.service.LocationService;
import jakarta.persistence.Id;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;

import java.util.Collections;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LocationController.class)
public class LocationControllerTest {

    private static final String LOCATION_API_URL = "/v1/location";
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper mapper;
    @MockBean
    LocationService locationService;

    @Test
    public void testAddShouldReturnBadRequest() throws Exception {
        Location location = new Location();
        String bodyContent = mapper.writeValueAsString(location);
        mockMvc.perform(post(LOCATION_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyContent))
                .andExpect(status().isBadRequest());


    }
    @Test
    public void testAddShouldReturnCreated() throws Exception {
        Location location = new Location();
        location.setCode("NYC");
        location.setCityName("New York City");
        location.setCountryCode("US");
        location.setCountryName("USA");
        location.setRegionName("New York State");
        location.setEnabled(true);
        Mockito.when(locationService.createLocation(location)).thenReturn(location);
        String bodyContent = mapper.writeValueAsString(location);
        mockMvc.perform(post(LOCATION_API_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(bodyContent))
                .andExpect(status().isCreated())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(header().string("Location", "/v1/location/NYC"));


    }
    @Test
    public void testValidatedRequestCode() throws Exception {
        Location location = new Location();
        location.setCityName("New York City");
        location.setCountryCode("US");
        location.setCountryName("USA");
        location.setRegionName("New York State");
        location.setEnabled(true);
        String bodyContent = mapper.writeValueAsString(location);
        mockMvc.perform(post(LOCATION_API_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(bodyContent))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

    }

    @Test
    public void testShouldReturnNoContent() throws Exception {

        Mockito.when(locationService.listLocations()).thenReturn(Collections.emptyList());
        mockMvc.perform(get(LOCATION_API_URL + "/list"))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
    @Test
    public void testListShouldReturnn200() throws Exception {
        Location location = new Location();
        location.setCode("NYC");
        location.setCityName("New York City");
        location.setCountryCode("US");
        location.setCountryName("USA");
        location.setRegionName("New York State");
        location.setEnabled(true);
        Mockito.when(locationService.listLocations()).thenReturn(Collections.singletonList(location));
        mockMvc.perform(get(LOCATION_API_URL + "/list"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());

    }

    @Test
    public void testFindLocationByCodeShouldReturnNotFound() throws Exception {
        String code = "XYZ";
        Mockito.when(locationService.findLocationByCode(code)).thenReturn(null);
        mockMvc.perform(get(LOCATION_API_URL + "/" + code))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    public void testFindLocationByCodeShouldReturn200() throws Exception {
        Location location = new Location();
        location.setCode("NYC");
        location.setCityName("New York City");
        location.setCountryCode("US");
        location.setCountryName("USA");
        location.setRegionName("New York State");
        location.setEnabled(true);
        Mockito.when(locationService.findLocationByCode("NYC")).thenReturn(location);
        mockMvc.perform(get(LOCATION_API_URL + "/NYC"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    public  void testShouldReturn405NotAllowed() throws Exception {
        String requestURI =LOCATION_API_URL + "/ABCDE";

        mockMvc.perform(post(requestURI))
                .andExpect(status().isMethodNotAllowed())
                .andDo(print());

    }

    @Test
    public void testShouldReturn404NotFound() throws Exception {
        Location location = new Location();
        location.setCode("NYCDA");
        location.setCityName("New York City");
        location.setCountryCode("US");
        location.setCountryName("USA");
        location.setRegionName("New York State");
        location.setEnabled(true);
        Mockito.when(locationService.updateLocation(location)).thenThrow(new LocationNotFoundException("Location not found" + location.getCode()));
        mockMvc.perform(put(LOCATION_API_URL + "/NYCDA").contentType(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(location)))
                .andExpect(status().isNotFound())
                .andDo(print());
    }
    @Test
    public void testShouldReturn400BadRequest() throws Exception {
        Location location = new Location();
        location.setCityName("New York City");
        location.setCountryCode("US");
        location.setCountryName("USA");
        location.setRegionName("New York State");
        location.setEnabled(true);
        String bodyContent = mapper.writeValueAsString(location);
        mockMvc.perform(put(LOCATION_API_URL + "/NYCA").contentType(MediaType.APPLICATION_JSON).content(bodyContent))
                .andExpect(status().isBadRequest())
                .andDo(print());
    } @Test
    public void testUpdateLocationByCodeShouldReturn200() throws Exception {
        Location location = new Location();
        location.setCode("NYC");
        location.setCityName("New York City");
        location.setCountryCode("US");
        location.setCountryName("USA");
        location.setRegionName("New York State");
        location.setEnabled(true);
        String bodyContent = mapper.writeValueAsString(location);
        Mockito.when(locationService.updateLocation(location)).thenReturn(location);

        mockMvc.perform(put(LOCATION_API_URL + "/NYC").contentType(MediaType.APPLICATION_JSON).content(bodyContent))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
public void testDeleteShouldReturn204NoContent() throws Exception {
    String code = "NYC";
    Mockito.doNothing().when(locationService).trashedByCode(code);
    mockMvc.perform(delete(LOCATION_API_URL + "/NYC/trashed"))
            .andExpect(status().isNoContent())
            .andDo(print());
}
    @Test
    public void testDeleteShouldReturn404NotFound() throws Exception {
        String code = "XYZ";
        Mockito.doThrow(LocationNotFoundException.class).when(locationService).trashedByCode(code);
        mockMvc.perform(delete(LOCATION_API_URL + "/XYZ/trashed"))
                .andExpect(status().isNotFound())
                .andDo(print());
    }


}
