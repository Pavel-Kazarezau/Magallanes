package io.kazarezau.magallanes.image.service;

import io.kazarezau.magallanes.core.CountriesService;
import io.kazarezau.magallanes.image.Image;
import io.kazarezau.magallanes.image.ImageLocationNotFound;
import io.kazarezau.magallanes.image.ImageService;
import io.kazarezau.magallanes.image.output.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final CountriesService countriesService;
    private final ImageRepository imageRepository;

    @Override
    public Image createImage(final Image.Essence essence) {
        final String country = Objects.requireNonNull(essence.getCountry());
        final String city = Objects.requireNonNull(essence.getCity());

        if (!countriesService.containsCountryAndCity(country, city)) {
            throw new ImageLocationNotFound("Country or city does not exist");
        }

        final Image image = new Image(essence);
        return imageRepository.save(image);
    }

    @Override
    public Page<Image> findByPoint(final Image.Point point, final Pageable pageable) {

        return imageRepository.findByPoint(point, pageable);
    }
}
