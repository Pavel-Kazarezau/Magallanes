package io.kazarezau.magallanes.image;

import io.kazarezau.magallanes.image.input.ImageFacade;
import io.kazarezau.magallanes.image.input.command.CreateImageCommand;
import io.kazarezau.magallanes.image.input.command.FindByPointCommand;
import io.kazarezau.magallanes.image.input.mapper.ImageEssenceMapper;
import io.kazarezau.magallanes.image.output.repository.ImageRepository;
import io.kazarezau.magallanes.image.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.hexagonal.Application;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Application
@Service
@RequiredArgsConstructor
public class ImageApplication implements ImageFacade {

    private final ImageService imageService;
    private final ImageRepository imageRepository;
    private final ImageEssenceMapper imageEssenceMapper;

    @Override
    public Image createImage(final CreateImageCommand command) {
        final Image.Essence essence = imageEssenceMapper.createImageCommandToImageEssence(command);

        final Image image = imageService.createImage(essence);
        return imageRepository.save(image);
    }

    @Override
    public Page<Image> findByPoint(final FindByPointCommand command) {
        final Image.Point point = new Image.Point(command.getCountry(), command.getCity());
        return imageService.findByPoint(point, command.getPageable());
    }
}
