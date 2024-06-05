package io.kazarezau.magallanes.core.countries;

import io.kazarezau.magallanes.core.countries.model.CountryModel;
import io.kazarezau.magallanes.core.countries.model.ResponseModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CountriesServiceImplTest {

    @Mock
    private CountriesFeignClient countriesClient;
    @InjectMocks
    private CountriesServiceImpl countriesService;

    @Test
    void shouldContainsCountryAndCity() {

        final ResponseModel responseModel = new ResponseModel();
        responseModel.setError(false);
        final CountryModel countryModel = new CountryModel();
        countryModel.setCountry("Poland");
        countryModel.setCities(List.of("Warsaw", "Krakow"));
        responseModel.setData(List.of(countryModel));

        when(countriesClient.getCountries()).thenReturn(responseModel);
        final boolean containsCountryAndCity = countriesService.containsCountryAndCity("Poland", "Warsaw");
        assertTrue(containsCountryAndCity);
    }

    @Test
    void shouldReturnFalseIfNotContainsCountryAndCity() {

        final ResponseModel responseModel = new ResponseModel();
        responseModel.setError(false);
        final CountryModel countryModel = new CountryModel();
        countryModel.setCountry("Poland");
        countryModel.setCities(List.of("Warsaw", "Krakow"));
        responseModel.setData(List.of(countryModel));

        when(countriesClient.getCountries()).thenReturn(responseModel);
        final boolean containsCountryAndCity = countriesService.containsCountryAndCity("France", "Paris");
        assertFalse(containsCountryAndCity);
    }
}