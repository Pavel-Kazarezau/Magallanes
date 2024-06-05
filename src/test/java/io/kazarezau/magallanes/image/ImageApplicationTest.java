package io.kazarezau.magallanes.image;

import io.kazarezau.magallanes.image.input.command.CreateImageCommand;
import io.kazarezau.magallanes.image.input.command.FindByPointCommand;
import io.kazarezau.magallanes.image.input.mapper.ImageEssenceMapper;
import io.kazarezau.magallanes.image.output.repository.ImageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageApplicationTest {

    @Mock
    private ImageService imageService;
    @Mock
    private ImageRepository imageRepository;
    @Mock
    private ImageEssenceMapper imageEssenceMapper;
    @InjectMocks
    private ImageApplication imageApplication;

    @Test
    void shouldCreateImage() {
        when(imageEssenceMapper.createImageCommandToImageEssence(any())).thenReturn(new Image.Essence());

        imageApplication.createImage(new CreateImageCommand());
        verify(imageService).createImage(any());
        verify(imageRepository).save(any());
    }

    @Test
    void shouldFindByPoint() {
        final FindByPointCommand command = new FindByPointCommand("Poland", "Warsaw", Pageable.unpaged());

        imageApplication.findByPoint(command);
        verify(imageService).findByPoint(any(Image.Point.class), eq(Pageable.unpaged()));
    }

}