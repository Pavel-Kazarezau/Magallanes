package io.kazarezau.magallanes.image.input.rest.controller;

import io.kazarezau.magallanes.image.Image;
import io.kazarezau.magallanes.image.input.ImageFacade;
import io.kazarezau.magallanes.image.input.command.CreateImageCommand;
import io.kazarezau.magallanes.image.input.command.FindByPointCommand;
import lombok.RequiredArgsConstructor;
import org.jmolecules.architecture.hexagonal.PrimaryAdapter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@PrimaryAdapter
@RestController
@RequestMapping("/image")
@RequiredArgsConstructor
public class ImageController {

    private final ImageFacade imageApplication;

    @PostMapping
    public Image uploadImage(@RequestParam("file") MultipartFile file,
                             @RequestParam("country") String country,
                             @RequestParam("city") String city) throws IOException {
        final CreateImageCommand command = new CreateImageCommand();
        command.setName(file.getOriginalFilename());
        command.setType(file.getContentType());
        command.setData(file.getBytes());
        command.setCountry(country);
        command.setCity(city);

        return imageApplication.createImage(command);
    }

    @GetMapping
    public Page<Image> getImages(@RequestParam("country") String country,
                                 @RequestParam("city") String city,
                                 Pageable pageable) {
        final FindByPointCommand command = new FindByPointCommand(country, city, pageable);
        return imageApplication.findByPoint(command);
    }
}
