package io.kazarezau.magallanes.image.output.mapper;

import io.kazarezau.magallanes.image.Image;
import io.kazarezau.magallanes.image.output.model.ImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImageMapper {

    @Mapping(target = "id", source = "id.id")
    @Mapping(target = "country", source = "point.country")
    @Mapping(target = "city", source = "point.city")
    ImageEntity imageToImageEntity(Image image);

    @Mapping(target = "id.id", source = "id")
    @Mapping(target = "point.country", source = "country")
    @Mapping(target = "point.city", source = "city")
    Image imageEntityToImage(ImageEntity imageEntity);
}
