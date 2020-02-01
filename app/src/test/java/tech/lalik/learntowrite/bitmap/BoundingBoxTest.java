package tech.lalik.learntowrite.bitmap;

import org.junit.Test;
import static org.junit.Assert.*;

public class BoundingBoxTest {
    @Test
    public void that_contain_pixels_inside() {
        BoundingBox box = new BoundingBox(0, 0, 2, 2);

        assertTrue(box.contains(0, 0));
        assertTrue(box.contains(1, 1));
        assertTrue(box.contains(2, 2));
    }

    @Test
    public void that_does_not_contain_pixels_outside() {
        BoundingBox box = new BoundingBox(0, 0, 2, 2);

        assertFalse(box.contains(-1, -1));
        assertFalse(box.contains(3, 3));
    }

    @Test
    public void that_returns_valid_width_and_height() {
        BoundingBox box = new BoundingBox(0, 2, 5, 8);

        assertEquals(5, box.width);
        assertEquals(6, box.height);
    }
}
