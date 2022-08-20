package ru.netology.geo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.stream.Stream;

public class GeoServiceImplTest {
    private static Stream<Arguments> provideDataForGeoServiceTest() {
        return Stream.of(
                Arguments.of(GeoServiceImpl.LOCALHOST, null),
                Arguments.of(GeoServiceImpl.MOSCOW_IP, Country.RUSSIA),
                Arguments.of(GeoServiceImpl.NEW_YORK_IP, Country.USA),
                Arguments.of("172.22.10.2", Country.RUSSIA),
                Arguments.of("96.128.56.44", Country.USA)
        );
    }

    @ParameterizedTest(name = "{0} = {1}")
    @MethodSource("provideDataForGeoServiceTest")
    public void test_geo_service_implementation(String ip, Country country) {
        GeoService geoService = new GeoServiceImpl();
        Location location = geoService.byIp(ip);
        Assertions.assertSame(location.getCountry(), country);
    }

    @Test
    public void test_geo_service_not_work() {
        GeoService geoService = new GeoServiceImpl();
        Location location = geoService.byIp("fail");
        Assertions.assertNull(location);
    }
}
