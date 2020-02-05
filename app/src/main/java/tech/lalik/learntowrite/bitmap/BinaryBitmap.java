package tech.lalik.learntowrite.bitmap;

import java.util.ArrayList;
import java.util.Arrays;

public class BinaryBitmap {
    private final int width;
    private final int height;
    private boolean[][] data;
    private BoundingBox canvasBoundingBox;
    private BoundingBox contentsBoundingBox;

    public BinaryBitmap(int width, int height) {
        this.width = width;
        this.height = height;

        this.data = new boolean[width][height];
        this.canvasBoundingBox = new BoundingBox(0, 0, width - 1, height - 1);
    }

    public BoundingBox getBoundingBox() {
        if (this.contentsBoundingBox == null) {
            int x1 = Integer.MAX_VALUE,
                    y1 = Integer.MAX_VALUE,
                    x2 = Integer.MIN_VALUE,
                    y2 = Integer.MIN_VALUE;

            for (int i = 0; i < this.width; i++) {
                for (int j = 0; j < this.height; j++) {
                    if (this.data[i][j]) {
                        x1 = Math.min(x1, i);
                        y1 = Math.min(y1, j);
                        x2 = Math.max(x2, i);
                        y2 = Math.max(y2, j);
                    }
                }
            }

            this.contentsBoundingBox = new BoundingBox(x1, y1, x2, y2);
        }

        return this.contentsBoundingBox;
    }

    public void turnOnPixel(int x, int y) {
        this.data[x][y] = true;
        this.contentsBoundingBox = null;
    }

    public boolean getPixel(int x, int y) {
        return this.data[x][y];
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BinaryBitmap that = (BinaryBitmap) o;
        return width == that.width &&
                height == that.height &&
                Arrays.deepEquals(data, that.data);
    }

    public void translate(int x, int y) {
        this.contentsBoundingBox = null;
        boolean[][] source = this.duplicatedData();

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                int srcX = i - x;
                int srcY = j - y;

                if (canvasBoundingBox.contains(srcX, srcY)) {
                    data[i][j] = source[srcX][srcY];
                } else {
                    data[i][j] = false;
                }
            }
        }
    }

    private boolean[][] duplicatedData() {
        boolean[][] duplicate = new boolean[this.width][this.height];
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                duplicate[i][j] = this.data[i][j];
            }
        }

        return duplicate;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int j = 0; j < this.height; j++) {
            for (int i = 0; i < this.width; i++) {
                if (data[i][j]) {
                    builder.append('o');
                } else {
                    builder.append('x');
                }
            }
            builder.append('\n');
        }

        return builder.toString();
    }

    public void scaleBox(BoundingBox from, BoundingBox to) {
        this.contentsBoundingBox = null;
        boolean[][] source = this.duplicatedData();

        float xScale = (float) from.width / (float) to.width;
        float yScale = (float) from.height / (float) to.height;

        for (int i = to.left; i <= to.right; i++) {
            for (int j = to.top; j <= to.bottom; j++) {
                int srcX = to.left + Math.round((i - to.left) * xScale);
                int srcY = to.top + Math.round((j - to.top) * yScale);

                if (from.contains(srcX, srcY)) {
                    data[i][j] = source[srcX][srcY];
                } else {
                    data[i][j] = false;
                }
            }
        }
    }

    public double closestPixelDistance(int x, int y) {
        BoundingBox box = this.getBoundingBox();
        int maxR = Math.max(Math.max(
                Math.max(x - box.left, box.right - x),
                Math.max(y - box.top, box.bottom - y)
        ), 20);

        for (int r = 0; r <= maxR; r++) {
            for (int i = x - r; i < x + r + 1; i++) {
                if (canvasBoundingBox.contains(i, y - r) && data[i][y - r]) {
                    return distance(x, y, i, y - r);
                }
                if (canvasBoundingBox.contains(i, y + r) && data[i][y + r]) {
                    return distance(x, y, i, y + r);
                }
            }

            for (int j = y - r + 1; j < y + r; j++) {
                if (canvasBoundingBox.contains(x - r, j) && data[x - r][j]) {
                    return distance(x, y, x - r, j);
                }
                if (canvasBoundingBox.contains(x + r, j) && data[x + r][j]) {
                    return distance(x, y, x + r, j);

                }
            }
        }

        return Double.POSITIVE_INFINITY;
    }

    private double distance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public BitmapDistanceResult distanceToBitmap(BinaryBitmap bitmap2) {
        BoundingBox box = this.getBoundingBox();
        ArrayList<PixelDistance> distances = new ArrayList<>();
        double totalDistance = 0.0;
        int pixels = 0;

        for (int j = box.top; j < box.bottom; j++) {
            for (int i = box.left; i < box.right; i++) {
                if (data[i][j]) {
                    double distance = bitmap2.closestPixelDistance(i, j);
                    totalDistance += distance;
                    pixels++;
                    distances.add(new PixelDistance(
                            i,
                            j,
                            Math.min(1.0, distance / 50)
                    ));
                }
            }
        }

        return new BitmapDistanceResult(
                totalDistance / pixels,
                distances
        );
    }
}
