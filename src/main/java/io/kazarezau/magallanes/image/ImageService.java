package io.kazarezau.magallanes.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ImageService {

    Image createImage(final Image.Essence essence);

    Page<Image> findByPoint(Image.Point point, Pageable pageable);
}
