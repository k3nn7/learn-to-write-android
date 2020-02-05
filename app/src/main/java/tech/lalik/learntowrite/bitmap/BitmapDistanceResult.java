package tech.lalik.learntowrite.bitmap;

import java.util.Collection;

public class BitmapDistanceResult {
    public final double distance;
    public final Collection<PixelDistance> pixelDistances;

    public BitmapDistanceResult(double distance, Collection<PixelDistance> pixelDistances) {
        this.distance = distance;
        this.pixelDistances = pixelDistances;
    }
}
