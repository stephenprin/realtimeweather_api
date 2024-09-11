package com.skyapi.weatherforecast.location;

import com.ip2location.IP2Location;
import com.ip2location.IPResult;
import org.assertj.core.api.AbstractBigDecimalAssert;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

public class IP2LocationTest {
    private String DBpath= "ip2locdb/IP2LOCATION-LITE-DB3.BIN";

    @Test
    public void testInvalidIp() throws IOException {
        IP2Location ip2Location= new IP2Location();
        ip2Location.Open(DBpath);

        String ipAddress= "abc";
        IPResult ipResult = ip2Location.IPQuery(ipAddress);

        assertThat(ipResult.getStatus()).isEqualTo("INVALID_IP_ADDRESS");
        System.out.println(ipResult);

    }
    @Test void testValidIp() throws IOException {
        IP2Location ip2Location= new IP2Location();
        ip2Location.Open(DBpath);

        String ipAddress= "8.8.8";
        IPResult ipResult = ip2Location.IPQuery(ipAddress);

        assertThat(ipResult.getStatus()).isEqualTo("OK");
        assertThat(ipResult.getRegion()).isEqualTo("Pennsylvania");
        System.out.println(ipResult);
    }

}
