package io.kazarezau.magallanes.image;

import io.kazarezau.magallanes.core.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jmolecules.ddd.annotation.AggregateRoot;
import org.jmolecules.ddd.annotation.ValueObject;
import org.jmolecules.ddd.types.Identifier;

import java.util.Objects;
import java.util.UUID;

@AggregateRoot
@Getter
@Setter
@NoArgsConstructor
public class Image extends BaseEntity<Image.ImageId> {

    private String name;

    private String type;

    private Image.Point point;

    private byte[] data;

    public record ImageId(UUID id) implements Identifier {
    }

    public Image(Image.Essence essence) {
        super(new Image.ImageId(UUID.randomUUID()));
        this.name = Objects.requireNonNull(essence.getName());
        this.type = Objects.requireNonNull(essence.getType());
        this.data = essence.getData();
        this.point = new Image.Point(Objects.requireNonNull(essence.country), Objects.requireNonNull(essence.city));
    }

    @ValueObject
    public record Point(String country, String city) {
    }

    @Data
    public static class Essence {
        private String name;
        private String country;
        private String city;
        private String type;
        private byte[] data;
    }
}
