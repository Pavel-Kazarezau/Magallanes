package io.kazarezau.magallanes.image.output.repository;

import io.kazarezau.magallanes.image.Image;
import io.kazarezau.magallanes.image.output.mapper.ImageMapper;
import io.kazarezau.magallanes.image.output.model.ImageEntity;
import io.kazarezau.magallanes.image.output.model.ImageEntityRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageRepositoryImplTest {

    @Mock
    private ImageEntityRepository imageEntityRepository;
    @Mock
    private ImageMapper imageMapper;
    @InjectMocks
    private ImageRepositoryImpl imageRepository;

    @Test
    void save() {
        final ImageEntity entity = new ImageEntity();
        when(imageMapper.imageToImageEntity(any(Image.class))).thenReturn(entity);
        when(imageEntityRepository.save(any(ImageEntity.class))).thenReturn(entity);

        imageRepository.save(new Image());

        verify(imageEntityRepository).save(any(ImageEntity.class));
        verify(imageMapper).imageEntityToImage(any(ImageEntity.class));
    }

    @Test
    void findByPoint() {
        final Image.Point point = new Image.Point("Poland", "Warsaw");
        
        when(imageEntityRepository.findAllByCountryAndCity("Poland", "Warsaw", Pageable.unpaged()))
                .thenReturn(new PageImpl<>(List.of(new ImageEntity())));

        imageRepository.findByPoint(point, Pageable.unpaged());

        verify(imageEntityRepository).findAllByCountryAndCity("Poland", "Warsaw", Pageable.unpaged());
        verify(imageMapper).imageEntityToImage(any(ImageEntity.class));
    }
}