package ru.netology.sender;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;

import java.util.HashMap;
import java.util.Map;

public class MessageSenderImplTest {
    @Test
    public void test_message_sender_implementation_russian() {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp("172.0.32.11")).thenReturn(new Location("test", Country.RUSSIA, "test street", 1));

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.RUSSIA)).thenReturn("Добро пожаловать");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "172.0.32.11");

        String message = messageSender.send(headers);

        Mockito.verify(geoService, Mockito.times(1)).byIp("172.0.32.11");
        Mockito.verify(localizationService, Mockito.times(2)).locale(Country.RUSSIA);
        Assertions.assertSame(message, "Добро пожаловать");
    }

    @Test
    public void test_message_sender_implementation_english() {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp("96.44.183.149")).thenReturn(new Location("test", Country.USA, "test street", 1));

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "96.44.183.149");

        String message = messageSender.send(headers);

        Mockito.verify(geoService, Mockito.times(1)).byIp("96.44.183.149");
        Mockito.verify(localizationService, Mockito.times(2)).locale(Country.USA);
        Assertions.assertSame(message, "Welcome");
    }

    @Test
    public void test_message_sender_implementation_fail() {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp("")).thenReturn(new Location("test", Country.RUSSIA, "test street", 1));

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(Country.USA)).thenReturn("Welcome");

        MessageSender messageSender = new MessageSenderImpl(geoService, localizationService);

        Map<String, String> headers = new HashMap<>();
        headers.put(MessageSenderImpl.IP_ADDRESS_HEADER, "");

        String message = messageSender.send(headers);

        Mockito.verify(geoService, Mockito.times(0)).byIp("");
        Mockito.verify(localizationService, Mockito.times(1)).locale(Country.USA);
        Assertions.assertSame(message, "Welcome");
    }
}
