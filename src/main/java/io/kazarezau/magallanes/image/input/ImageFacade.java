package io.kazarezau.magallanes.image.input;

import io.kazarezau.magallanes.image.Image;
import io.kazarezau.magallanes.image.input.command.CreateImageCommand;
import io.kazarezau.magallanes.image.input.command.FindByPointCommand;
import org.springframework.data.domain.Page;

public interface ImageFacade {

    Image createImage(CreateImageCommand command);

    Page<Image> findByPoint(FindByPointCommand command);
}
