package io.kazarezau.magallanes.image.input.command;

import lombok.Data;

@Data
public class CreateImageCommand {

    private String name;

    private String type;

    private byte[] data;

    private String country;

    private String city;
}
