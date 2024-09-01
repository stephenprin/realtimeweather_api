package com.skyapi.weatherforecast.commonn;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

@Entity
@Table(name = "locations")
public class Location {


    @Column(length = 12, nullable = false, unique = true)
    @Id
    @NotNull(message = "Code is mandatory")
    private String code;
    @JsonProperty("city_name")
    @NotNull(message = "City name is mandatory")
    @Column(length = 150, nullable = false)
    @Length(min = 3, max = 100, message = "City name must be between 3 and 100 characters")
    private String cityName;

    @JsonProperty("region_name")
    @Column(length = 128)
    @Length(min= 3, max = 128, message = "Region name must be less than 128 characters")
    private String regionName;
    @JsonProperty("country_name")
    @NotNull(message = "Country name is mandatory")
    @Column(length = 70, nullable = false)
    private String countryName;
    @JsonProperty("country_code")
    @NotNull(message = "Country code is mandatory")
    @Column(length = 4, nullable = false)
    @Length(min = 2, max = 4, message = "Country code must be 2 characters")
    private String countryCode;

    private boolean enabled;

    @JsonIgnore
    private boolean trashed;
    @OneToOne(mappedBy = "location", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private RealtimeWeather realtimeWeather;

    public RealtimeWeather getRealtimeWeather() {
        return realtimeWeather;
    }

    public void setRealtimeWeather(RealtimeWeather realtimeWeather) {
        this.realtimeWeather = realtimeWeather;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isTrashed() {
        return trashed;
    }

    public void setTrashed(boolean trashed) {
        this.trashed = trashed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Location location)) return false;
        return Objects.equals(code, location.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    @Override
    public String toString() {
        return "Location{" +
                "code='" + code + '\'' +
                ", cityName='" + cityName + '\'' +
                ", regionName='" + regionName + '\'' +
                ", countryName='" + countryName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", enabled=" + enabled +
                ", trashed=" + trashed +
                '}';
    }
}
