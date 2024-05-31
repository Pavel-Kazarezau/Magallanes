package io.kazarezau.magallanes.core.countries;

import io.kazarezau.magallanes.core.countries.model.CountryModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountriesServiceImpl implements CountriesService {

    private final CountriesFeignClient countriesClient;

    @Override
    public boolean containsCountryAndCity(final String country, final String city) {
        final List<CountryModel> data = countriesClient.getCountries().getData();
        return data.stream()
                .filter(el -> el.getCountry().equals(country))
                .findFirst()
                .map(countryModel -> countryModel
                        .getCities().stream()
                        .anyMatch(el -> el.equals(city)))
                .orElse(false);
    }
}
