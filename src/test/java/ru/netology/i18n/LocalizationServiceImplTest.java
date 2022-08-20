package ru.netology.i18n;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;

import java.util.stream.Stream;

public class LocalizationServiceImplTest {
    private static Stream<Arguments> provideDataForCountryCheck() {
        return Stream.of(
                Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.GERMANY, "Welcome")
        );
    }

    @ParameterizedTest(name = "{0} = {1}")
    @MethodSource("provideDataForCountryCheck")
    public void test_localization_implementation(Country country, String needMessage) {
        LocalizationService localizationService = new LocalizationServiceImpl();
        String message = localizationService.locale(country);
        Assertions.assertSame(message, needMessage);
    }
}
