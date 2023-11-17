package com.kazurayam.browserwindowlayout;

import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

import java.awt.*;

public class TilingCellLayoutMetrics extends CellLayoutMetrics {

    public static final TilingCellLayoutMetrics DEFAULT =
            new TilingCellLayoutMetrics.Builder(2).build();

    private TilingCellLayoutMetrics(Builder builder) {
        this.columns = builder.columns;
        this.size = builder.size;
        virtualScreenSize = new Dimension(
                builder.physicalScreenSize.getWidth() - builder.basePoint.getX() * 2,
                builder.physicalScreenSize.getHeight() - builder.basePoint.getY() * 2);
        basePoint = builder.basePoint;
    }

    public Dimension getVirtualScreenSize() {
        return virtualScreenSize;
    }

    public Point getBasePoint() {
        return basePoint;
    }

    public int getColumns() { return this.columns; }

    @Override
    public int getSize() {
        return this.size;
    }

    @Override
    public Dimension getCellDimension(final int cellIndex) {
        if (cellIndex < 0 || cellIndex >= this.size) {
            throw new IllegalArgumentException("windowIndex=" + cellIndex + " must not be <0 and >=size");
        }
        if (size == 1) {
            return virtualScreenSize;
        } else {
            int width = (int) Math.floor((double) virtualScreenSize.getWidth() / this.columns);
            int rows = (int) Math.ceil((double) this.size / this.columns);
            int height = (int) Math.floor((double) virtualScreenSize.getHeight() / rows);
            return new Dimension(width, height);
        }
    }

    @Override
    public Point getCellPosition(final int cellIndex) {
        if (cellIndex < 0 || cellIndex >= this.size) {
            throw new IllegalArgumentException("windowIndex=" +
                    String.valueOf(cellIndex) + " must not be <0 and >=size");
        }
        int x = basePoint.getX() + (cellIndex % this.columns) * this.getCellDimension(cellIndex).getWidth();

        int y = (int) ((int) basePoint.getY() +
                Math.floor((double) cellIndex / this.columns) * this.getCellDimension(cellIndex).getHeight());

        return new Point(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (DefaultGroovyMethods.is(o, this)) {
            return true;
        }

        if (!(o instanceof TilingCellLayoutMetrics)) {
            return false;
        }

        TilingCellLayoutMetrics other = (TilingCellLayoutMetrics) o;
        return this.virtualScreenSize.equals(other.getVirtualScreenSize()) &&
                this.basePoint.equals(other.getBasePoint());
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + this.virtualScreenSize.hashCode();
        result = 31 * result + this.basePoint.hashCode();
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"TilingCellLayoutMetrics\":{");
        sb.append("\"columns\":");
        sb.append(getColumns());
        sb.append("\"size\":");
        sb.append(getSize());
        sb.append(",");
        sb.append("\"virtualScreenSize\":[");
        sb.append(getVirtualScreenSize().getWidth());
        sb.append(",");
        sb.append(getVirtualScreenSize().getHeight());
        sb.append("]");
        sb.append(",");
        sb.append("\"basePoint\":[");
        sb.append(getBasePoint().getX());
        sb.append(",");
        sb.append(getBasePoint().getY());
        sb.append("]");
        sb.append("}}");
        return sb.toString();
    }

    private final int columns;
    private final int size;
    private final Dimension virtualScreenSize;
    private final Point basePoint;

    /**
     * Builder pattern by "Effective Java"
     */
    public static class Builder {
        public Builder(final int columns, final int size) {
            if (columns <= 0) {
                throw new IllegalArgumentException("columns=" + columns + " must not be <=0");
            }
            if (size <= 0) {
                throw new IllegalArgumentException("size=" + size + " must not be <=0");
            }
            this.columns = columns;
            this.size = size;
        }

        public Builder(final int size) {
            this(1, size);
        }

        public Builder physicalScreenSize(Dimension physicalScreenSize) {
            this.physicalScreenSize = physicalScreenSize;
            return this;
        }

        public Builder basePoint(Point basePoint) {
            this.basePoint = basePoint;
            return this;
        }

        public TilingCellLayoutMetrics build() {
            return new TilingCellLayoutMetrics(this);
        }

        private final int size;
        private final int columns;
        private final java.awt.Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
        private Dimension physicalScreenSize = new Dimension((int) ss.getWidth(), (int) ss.getHeight());
        private Point basePoint = new Point(0, 0);
    }
}
