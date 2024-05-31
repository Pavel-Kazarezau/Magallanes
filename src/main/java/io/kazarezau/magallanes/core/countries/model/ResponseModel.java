package io.kazarezau.magallanes.core.countries.model;

import lombok.Data;

import java.util.List;

@Data
public class ResponseModel {

    private boolean error;

    private String msg;

    private List<CountryModel> data;
}
