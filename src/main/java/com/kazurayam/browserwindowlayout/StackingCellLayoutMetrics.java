package com.kazurayam.browserwindowlayout;

import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

public class StackingCellLayoutMetrics extends CellLayoutMetrics {

    public static final StackingCellLayoutMetrics DEFAULT =
            new StackingCellLayoutMetrics.Builder(2).build();

    private StackingCellLayoutMetrics(Builder builder) {
        size = builder.size;
        cellDimension = builder.cellDimension;
        disposition = builder.disposition;
    }

    public Dimension getCellDimension() {
        return this.cellDimension;
    }

    public Point getDisposition() {
        return this.disposition;
    }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public Point getCellPosition(int cellIndex) {
        if (cellIndex < 0 || cellIndex >= this.size) {
            throw new IllegalArgumentException("windowIndex must be >=0 and <" + String.valueOf(this.getSize()));
        }
        int x = disposition.getX() * cellIndex;
        int y = disposition.getY() * cellIndex;
        return new Point(x, y);
    }

    @Override
    public Dimension getCellDimension(int cellIndex) {
        return this.cellDimension;
    }

    @Override
    public boolean equals(Object o) {
        if (DefaultGroovyMethods.is(o, this)) {
            return true;
        }

        if (!(o instanceof StackingCellLayoutMetrics)) {
            return false;
        }

        StackingCellLayoutMetrics other = (StackingCellLayoutMetrics) o;
        return this.cellDimension.equals(other.getCellDimension()) && this.disposition.equals(other.getDisposition());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.cellDimension.hashCode();
        result = 31 * result + this.disposition.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"StackingCellLayoutMetrics\":{");
        sb.append("\"size\":");
        sb.append(this.getSize());
        sb.append(",");
        sb.append("\"cellDimension\":{\"width\":");
        sb.append(getCellDimension().getWidth());
        sb.append(",\"height\":");
        sb.append(getCellDimension().getHeight());
        sb.append("}");
        sb.append(",");
        sb.append("\"disposition\":{\"x\":");
        sb.append(getDisposition().getX());
        sb.append(",\"y\":");
        sb.append(getDisposition().getY());
        sb.append("}");
        sb.append("}}");
        return sb.toString();
    }

    private final int size;
    private final Dimension cellDimension;
    private final Point disposition;

    /**
     * Builder by Effective Java
     */
    public static class Builder {
        public Builder(final int size) {
            if (size <= 0) {
                throw new IllegalArgumentException("size=" + size + " must not be <=0");
            }
            this.size = size;
        }

        public Builder cellDimension(Dimension cellDimension) {
            this.cellDimension = cellDimension;
            return this;
        }

        public Builder disposition(Point disposition) {
            this.disposition = disposition;
            return this;
        }

        public StackingCellLayoutMetrics build() {
            return new StackingCellLayoutMetrics(this);
        }

        private final int size;
        private Dimension cellDimension = new Dimension(1280, 600);
        private Point disposition = new Point(120, 120);
    }
}
