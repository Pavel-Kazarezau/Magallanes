package io.kazarezau.magallanes.image;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImageTest {

    @Test
    void shouldCreate() {
        final Image.Essence essence = new Image.Essence();
        essence.setName("Kopernik museum");
        essence.setCountry("Poland");
        essence.setCity("Warsaw");
        essence.setType("jpeg");
        essence.setData(new byte[]{});

        final Image createdImage = new Image(essence);

        final Image expected = new Image();
        expected.setName("Kopernik museum");
        expected.setPoint(new Image.Point("Poland", "Warsaw"));
        expected.setType("jpeg");
        expected.setData(new byte[]{});

        assertEquals(expected, createdImage);
    }
}