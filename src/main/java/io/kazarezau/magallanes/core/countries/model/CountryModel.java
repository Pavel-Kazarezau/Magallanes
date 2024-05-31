package io.kazarezau.magallanes.core.countries.model;

import lombok.Data;

import java.util.List;

@Data
public class CountryModel {
    private String iso2;
    private String iso3;
    private String country;
    private List<String> cities;
}
