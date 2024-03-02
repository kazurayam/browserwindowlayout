package com.kazurayam.browserwindowlayout;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

public abstract class CellLayoutMetrics {

    public abstract int getSize();

    public abstract Point getCellPosition(int cellIndex);

    public abstract Dimension getCellDimension(int cellIndex);

}
