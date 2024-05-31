package io.kazarezau.magallanes.image.output.repository;

import io.kazarezau.magallanes.image.Image;
import io.kazarezau.magallanes.image.output.mapper.ImageMapper;
import io.kazarezau.magallanes.image.output.model.ImageEntity;
import io.kazarezau.magallanes.image.output.model.ImageEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ImageRepositoryImpl implements ImageRepository {

    private final ImageEntityRepository imageEntityRepository;
    private final ImageMapper imageMapper;

    @Override
    public Image save(final Image image) {
        final ImageEntity imageEntity = imageMapper.imageToImageEntity(image);

        final ImageEntity createdImageEntity = imageEntityRepository.save(imageEntity);
        return imageMapper.imageEntityToImage(createdImageEntity);
    }

    @Override
    public Page<Image> findByPoint(final Image.Point point, final Pageable pageable) {
        return imageEntityRepository.findAllByCountryAndCity(point.country(), point.city(), pageable)
                .map(imageMapper::imageEntityToImage);
    }
}
