package com.kazurayam.browserwindowlayout

import static org.junit.Assert.*

import org.openqa.selenium.Dimension
import org.openqa.selenium.Point
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4.class)
class TilingWindowLayoutMetricsTest {

    TilingWindowLayoutMetrics tilesSingleColumn
    TilingWindowLayoutMetrics tilesDualColumns
    int NUM_OF_WINDOWS = 4

    @Before
    void setup() {
        // single column
        tilesSingleColumn = new TilingWindowLayoutMetrics.Builder(NUM_OF_WINDOWS)
                .physicalScreenSize(new Dimension(1020, 820)).build()

        // 2 columns
        tilesDualColumns = new TilingWindowLayoutMetrics.Builder(2, NUM_OF_WINDOWS)
                .physicalScreenSize(new Dimension(1020, 820)).build()
    }

    @Test
    void test_getVirtualScreenSize() {
        Dimension virtualScreenSize = tilesSingleColumn.getVirtualScreenSize()
        assertTrue(virtualScreenSize.width == 1000)
        assertTrue(virtualScreenSize.height == 800)
    }

    @Test
    void test_getBasePoint() {
        Point basePoint = tilesSingleColumn.getBasePoint()
        assertTrue(basePoint.x == 10)
        assertTrue(basePoint.y == 10)
    }

    @Test
    void test_getWindowDimension_1column() {
        Dimension tileDimension = tilesSingleColumn.getWindowDimension(0)
        assertEquals("tileDimension.width", 1000, tileDimension.width)
        assertEquals("tileDimension.height", 200, tileDimension.height)
    }

    @Test
    void test_singleColumn_window0() {
        Point basePoint = tilesSingleColumn.getBasePoint()
        Point loc = tilesSingleColumn.getWindowPosition(0)
        Dimension dim = tilesSingleColumn.getWindowDimension(0)
        Point expectedLoc = new Point((int)basePoint.x, (int)basePoint.y)
        assertEquals("loc.x", expectedLoc.x, loc.x)
        assertEquals("loc.y", expectedLoc.y, loc.y)
        assertEquals("dim.width", 1000, dim.width)
        assertEquals("dim.height", 200, dim.height)
    }

    @Test
    void test_singleColumn_window1() {
        Point basePoint = tilesSingleColumn.getBasePoint()
        Point loc = tilesSingleColumn.getWindowPosition(1)
        Dimension dim = tilesSingleColumn.getWindowDimension(1)
        Point expectedLoc = new Point((int)basePoint.x, (int)basePoint.y + (int)dim.getHeight())
        assertEquals("loc.x", expectedLoc.x, loc.x)
        assertEquals("loc.y", expectedLoc.y, loc.y)
        assertEquals("dim.width", 1000, dim.width)
        assertEquals("dim.height", 200, dim.height)
    }

    @Test
    void test_singleColumn_window2() {
        Point basePoint = tilesSingleColumn.getBasePoint()
        Point loc = tilesSingleColumn.getWindowPosition(2)
        Dimension dim = tilesSingleColumn.getWindowDimension(2)
        Point expectedLoc = new Point((int)basePoint.x, (int)basePoint.y + (int)dim.height * 2)
        assertEquals("loc.x", expectedLoc.x, loc.x)
        assertEquals("loc.y", expectedLoc.y, loc.y)
        assertEquals("dim.width", 1000, dim.width)
        assertEquals("dim.height", 200, dim.height)
    }

    @Test
    void test_dualColumns_window0() {
        Point basePoint = tilesDualColumns.getBasePoint()
        Point loc = tilesDualColumns.getWindowPosition(0)
        Dimension dim = tilesDualColumns.getWindowDimension(0)
        Point expectedLoc = new Point((int)basePoint.x, (int)basePoint.y)
        assertEquals("loc.x", expectedLoc.x, loc.x)
        assertEquals("loc.y", expectedLoc.y, loc.y)
        assertEquals("dim.width", 500, dim.width)
        assertEquals("dim.height", 400, dim.height)
    }

    @Test
    void test_dualColumns_window1() {
        Point basePoint = tilesDualColumns.getBasePoint()
        Point loc = tilesDualColumns.getWindowPosition(1)
        Dimension dim = tilesDualColumns.getWindowDimension(1)
        Point expectedLoc = new Point((int)basePoint.x + (int)dim.width, (int)basePoint.y)
        assertEquals("loc.x", expectedLoc.x, loc.x)
        assertEquals("loc.y", expectedLoc.y, loc.y)
        assertEquals("dim.width", 500, dim.width)
        assertEquals("dim.height", 400, dim.height)
    }

    @Test
    void test_dualColumns_window2() {
        Point basePoint = tilesDualColumns.getBasePoint()
        Point loc = tilesDualColumns.getWindowPosition(2)
        Dimension dim = tilesDualColumns.getWindowDimension(2)
        Point expectedLoc = new Point((int)basePoint.x, (int)basePoint.y + (int)dim.height)
        assertEquals("loc.x", expectedLoc.x, loc.x)
        assertEquals("loc.y", expectedLoc.y, loc.y)
        assertEquals("dim.width", 500, dim.width)
        assertEquals("dim.height", 400, dim.height)
    }

}
