package io.kazarezau.magallanes.core.countries;

import io.kazarezau.magallanes.core.countries.model.ResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "countries",  url = "${integration.countries.url}")
public interface CountriesFeignClient {

    @GetMapping("/countries")
    ResponseModel getCountries();
}
