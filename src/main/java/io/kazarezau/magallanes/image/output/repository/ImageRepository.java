package io.kazarezau.magallanes.image.output.repository;

import io.kazarezau.magallanes.image.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ImageRepository {

    Image save(Image image);

    Page<Image> findByPoint(Image.Point point, Pageable pageable);
}
