package io.kazarezau.magallanes.image.input.command;

import jakarta.annotation.Nonnull;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class FindByPointCommand {

    @Nonnull
    private String country;

    @Nonnull
    private String city;

    @Nonnull
    private Pageable pageable;
}
