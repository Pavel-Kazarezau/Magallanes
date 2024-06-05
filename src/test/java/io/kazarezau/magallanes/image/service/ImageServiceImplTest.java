package io.kazarezau.magallanes.image.service;

import io.kazarezau.magallanes.core.CountriesService;
import io.kazarezau.magallanes.image.Image;
import io.kazarezau.magallanes.image.ImageLocationNotFound;
import io.kazarezau.magallanes.image.output.repository.ImageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ImageServiceImplTest {

    @Mock
    private CountriesService countriesService;
    @Mock
    private ImageRepository imageRepository;
    @InjectMocks
    private ImageServiceImpl imageService;

    @Test
    void createImage() {
        final Image.Essence essence = new Image.Essence();
        essence.setName("Kopernik museum");
        essence.setCountry("Poland");
        essence.setCity("Warsaw");
        essence.setType("jpeg");
        essence.setData(new byte[]{});

        when(countriesService.containsCountryAndCity("Poland", "Warsaw")).thenReturn(true);

        imageService.createImage(essence);
        verify(imageRepository).save(any(Image.class));
    }

    @Test
    void shouldFailIfCountryDoesNotExist() {
        final Image.Essence essence = new Image.Essence();
        essence.setCountry("Poland");
        essence.setCity("Warsaw");

        when(countriesService.containsCountryAndCity("Poland", "Warsaw")).thenReturn(false);

        final ImageLocationNotFound exception = assertThrows(ImageLocationNotFound.class,
                () -> imageService.createImage(essence));
        assertEquals("Country or city does not exist", exception.getMessage());
        verify(imageRepository, never()).save(any(Image.class));
    }

    @Test
    void findByPoint() {
        final Image.Point point = new Image.Point("Poland", "Warsaw");
        imageService.findByPoint(point, Pageable.unpaged());

        verify(imageRepository).findByPoint(point, Pageable.unpaged());
    }
}