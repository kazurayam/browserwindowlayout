package com.kazurayam.browserwindowlayout;

import org.codehaus.groovy.runtime.DefaultGroovyMethods;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

import java.awt.*;

public class TilingWindowLayoutMetrics extends WindowLayoutMetrics {

    public static final TilingWindowLayoutMetrics DEFAULT =
            new TilingWindowLayoutMetrics.Builder(8).build();

    private TilingWindowLayoutMetrics(Builder builder) {
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
    public Dimension getWindowDimension(final int windowIndex) {
        if (windowIndex < 0 || windowIndex >= this.size) {
            throw new IllegalArgumentException("windowIndex=" + windowIndex + " must not be <0 and >=size");
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
    public Point getWindowPosition(final int windowIndex) {
        if (windowIndex < 0 || windowIndex >= this.size) {
            throw new IllegalArgumentException("windowIndex=" +
                    String.valueOf(windowIndex) + " must not be <0 and >=size");
        }
        int x = basePoint.getX() + (windowIndex % this.columns) * this.getWindowDimension(windowIndex).getWidth();

        int y = (int) ((int) basePoint.getY() +
                Math.floor((double) windowIndex / this.columns) * this.getWindowDimension(windowIndex).getHeight());

        return new Point(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (DefaultGroovyMethods.is(o, this)) {
            return true;
        }

        if (!(o instanceof TilingWindowLayoutMetrics)) {
            return false;
        }

        TilingWindowLayoutMetrics other = (TilingWindowLayoutMetrics) o;
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
        sb.append("{\"TilingWindowLayoutMetrics\":{");
        sb.append("\"columns\":" + getColumns());
        sb.append("\"size\":" + getSize());
        sb.append(",");
        sb.append("\"virtualScreenSize\":[" + String.valueOf(getVirtualScreenSize().getWidth()) + "," + String.valueOf(getVirtualScreenSize().getHeight()) + "]");
        sb.append(",");
        sb.append("\"basePoint\":[" + String.valueOf(getBasePoint().getX()) + "," + String.valueOf(getBasePoint().getY()) + "]");
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
            if (size <= 0) {
                throw new IllegalArgumentException("columns=" + String.valueOf(columns) + " must not be <=0");
            }
            if (size <= 0) {
                throw new IllegalArgumentException("size=" + String.valueOf(size) + " must not be <=0");
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

        public TilingWindowLayoutMetrics build() {
            return new TilingWindowLayoutMetrics(this);
        }

        private int size;
        private int columns;
        private java.awt.Dimension ss = Toolkit.getDefaultToolkit().getScreenSize();
        private Dimension physicalScreenSize = new Dimension((int) ss.getWidth(), (int) ss.getHeight());
        private Point basePoint = new Point(10, 10);
    }
}
