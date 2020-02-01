package tech.lalik.learntowrite.bitmap;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BinaryBitmapTest {
    @Test
    public void getBoundingBox_for_blank_bitmap() {
        BinaryBitmap bitmap = new BinaryBitmap(5, 5);
        BoundingBox expected = new BoundingBox(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);

        assertEquals(expected, bitmap.getBoundingBox());
    }

    @Test
    public void getBoundingBox_for_single_pixel() {
        BinaryBitmap bitmap = new BinaryBitmap(5, 5);
        bitmap.turnOnPixel(3, 3);
        BoundingBox expected = new BoundingBox(3, 3, 3, 3);

        assertEquals(expected, bitmap.getBoundingBox());
    }

    @Test
    public void getBoundingBox_for_all_pixels_on() {
        BinaryBitmap bitmap = new BinaryBitmap(2, 2);
        bitmap.turnOnPixel(0, 0);
        bitmap.turnOnPixel(1, 0);
        bitmap.turnOnPixel(0, 1);
        bitmap.turnOnPixel(1, 1);

        BoundingBox expected = new BoundingBox(0, 0, 1, 1);

        assertEquals(expected, bitmap.getBoundingBox());
    }

    @Test
    public void translate_blank_bitmap() {
        BinaryBitmap bitmap = new BinaryBitmap(5, 5);
        BinaryBitmap expectedTranslated = new BinaryBitmap(5, 5);

        bitmap.translate(-2, -2);

        assertEquals(expectedTranslated, bitmap);
    }

    @Test
    public void translate_single_pixel() {
        BinaryBitmap bitmap = new BinaryBitmap(5, 5);
        BinaryBitmap expectedTranslated = new BinaryBitmap(5, 5);
        bitmap.turnOnPixel(2, 2);

        bitmap.translate(-2, -2);
        expectedTranslated.turnOnPixel(0, 0);

        assertEquals(expectedTranslated, bitmap);
    }

    @Test
    public void scale() {
        BinaryBitmap bitmap = new BinaryBitmap(5, 5);

        BinaryBitmap expectedScaled = new BinaryBitmap(5, 5);
        expectedScaled.turnOnPixel(0, 0);
        expectedScaled.turnOnPixel(1, 0);
        expectedScaled.turnOnPixel(4, 0);
        expectedScaled.turnOnPixel(1, 1);
        expectedScaled.turnOnPixel(2, 1);
        expectedScaled.turnOnPixel(3, 1);
        expectedScaled.turnOnPixel(4, 1);
        expectedScaled.turnOnPixel(1, 2);
        expectedScaled.turnOnPixel(2, 2);
        expectedScaled.turnOnPixel(3, 2);
        expectedScaled.turnOnPixel(4, 2);

        bitmap.turnOnPixel(0, 0);
        bitmap.turnOnPixel(1, 0);
        bitmap.turnOnPixel(4, 0);
        bitmap.turnOnPixel(1, 1);
        bitmap.turnOnPixel(2, 1);

        BoundingBox sourceBox = new BoundingBox(1, 1, 2, 2);
        BoundingBox destinationBox = new BoundingBox(1, 1, 4, 4);
        bitmap.scaleBox(sourceBox, destinationBox);

        assertEquals(expectedScaled, bitmap);
    }

    @Test
    public void closestPixelDistance() {
        BinaryBitmap bitmap = new BinaryBitmap(5, 5);
        bitmap.turnOnPixel(3, 3);

        assertEquals(0.0, bitmap.closestPixelDistance(3, 3), 0.01);
        assertEquals(1.0, bitmap.closestPixelDistance(3, 2), 0.01);
        assertEquals(1.0, bitmap.closestPixelDistance(3, 4), 0.01);
        assertEquals(1.0, bitmap.closestPixelDistance(2, 3), 0.01);
        assertEquals(1.0, bitmap.closestPixelDistance(4, 3), 0.01);

        assertEquals(4.24, bitmap.closestPixelDistance(0, 0), 0.01);
    }

    @Test
    public void distance_from_another_bitmap() {
        BinaryBitmap bitmap1 = new BinaryBitmap(3, 3);
        bitmap1.turnOnPixel(1, 1);

        BinaryBitmap bitmap2 = new BinaryBitmap(3, 3);
        bitmap2.turnOnPixel(1, 2);

        assertEquals(1.0, bitmap1.distanceToBitmap(bitmap2), 0.01);
    }
}
