package tech.lalik.learntowrite.bitmap;

public class BoundingBox {
    public final int left;
    public final int top;
    public final int right;
    public final int bottom;
    public final int width;
    public final int height;

    public BoundingBox(int left, int top, int right, int bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.width = right - left;
        this.height = bottom - top;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoundingBox that = (BoundingBox) o;
        return left == that.left &&
                top == that.top &&
                right == that.right &&
                bottom == that.bottom;
    }

    @Override
    public String toString() {
        return "BoundingBox{" +
                "left=" + left +
                ", top=" + top +
                ", right=" + right +
                ", bottom=" + bottom +
                '}';
    }

    public boolean contains(int x, int y) {
        return inRange(x, left, right) && inRange(y, top, bottom);
    }

    private boolean inRange(int value, int from, int to) {
        return value >= from && value <= to;
    }
}
