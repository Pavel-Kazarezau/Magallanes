package io.kazarezau.magallanes.image.input.mapper;

import io.kazarezau.magallanes.image.Image;
import io.kazarezau.magallanes.image.input.command.CreateImageCommand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ImageEssenceMapper {

    Image.Essence createImageCommandToImageEssence(CreateImageCommand createImageCommand);
}
