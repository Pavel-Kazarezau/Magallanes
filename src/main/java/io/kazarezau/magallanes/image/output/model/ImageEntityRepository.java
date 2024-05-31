package io.kazarezau.magallanes.image.output.model;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ImageEntityRepository extends JpaRepository<ImageEntity, UUID> {

    Page<ImageEntity> findAllByCountryAndCity(String country, String city, Pageable pageable);
}
