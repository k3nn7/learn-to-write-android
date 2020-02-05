package tech.lalik.learntowrite.bitmap;

import java.util.Objects;

public class PixelDistance {
    public final int x;
    public final int y;
    public final double distance;

    public PixelDistance(int x, int y, double distance) {
        this.x = x;
        this.y = y;
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PixelDistance that = (PixelDistance) o;
        return x == that.x &&
                y == that.y &&
                Double.compare(that.distance, distance) == 0;
    }
}
