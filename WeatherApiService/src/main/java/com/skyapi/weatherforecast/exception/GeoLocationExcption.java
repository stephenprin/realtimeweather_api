package com.skyapi.weatherforecast.exception;

import java.io.IOException;

public class GeoLocationExcption extends Exception {
    public GeoLocationExcption(String message) {
        super(message);
    }

    public GeoLocationExcption(String message, Throwable cause) {
        super(message, cause);
    }
}

