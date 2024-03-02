package com.kazurayam.browserwindowlayout

import static org.junit.Assert.*

import org.openqa.selenium.Dimension
import org.openqa.selenium.Point
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4.class)
class TilingCellLayoutMetricsTest {

    TilingCellLayoutMetrics cellsSingleColumn
    TilingCellLayoutMetrics cellsDualColumns
    int NUM_OF_CELLS = 4

    @Before
    void setup() {
        // single column
        cellsSingleColumn = new TilingCellLayoutMetrics.Builder(NUM_OF_CELLS)
                .physicalScreenSize(new Dimension(1000, 800)).build()

        // 2 columns
        cellsDualColumns = new TilingCellLayoutMetrics.Builder(2, NUM_OF_CELLS)
                .physicalScreenSize(new Dimension(1000, 800)).build()
    }

    @Test
    void test_getVirtualScreenSize() {
        Dimension virtualScreenSize = cellsSingleColumn.getVirtualScreenSize()
        assertTrue(virtualScreenSize.width == 1000)
        assertTrue(virtualScreenSize.height == 800)
    }

    @Test
    void test_getBasePoint() {
        Point basePoint = cellsSingleColumn.getBasePoint()
        assertTrue(basePoint.x == 0)
        assertTrue(basePoint.y == 0)
    }

    @Test
    void test_getCellDimension_1column() {
        Dimension dim = cellsSingleColumn.getCellDimension(0)
        assertEquals("dim.width", 1000, dim.width)
        assertEquals("dim.height", 200, dim.height)
    }

    @Test
    void test_singleColumn_cell0() {
        Point basePoint = cellsSingleColumn.getBasePoint()
        Point loc = cellsSingleColumn.getCellPosition(0)
        Dimension dim = cellsSingleColumn.getCellDimension(0)
        Point expectedLoc = new Point((int)basePoint.x, (int)basePoint.y)
        assertEquals("loc.x", expectedLoc.x, loc.x)
        assertEquals("loc.y", expectedLoc.y, loc.y)
        assertEquals("dim.width", 1000, dim.width)
        assertEquals("dim.height", 200, dim.height)
    }

    @Test
    void test_singleColumn_cell1() {
        Point basePoint = cellsSingleColumn.getBasePoint()
        Point loc = cellsSingleColumn.getCellPosition(1)
        Dimension dim = cellsSingleColumn.getCellDimension(1)
        Point expectedLoc = new Point((int)basePoint.x,
                (int)basePoint.y + (int)dim.getHeight())
        assertEquals("loc.x", expectedLoc.x, loc.x)
        assertEquals("loc.y", expectedLoc.y, loc.y)
        assertEquals("dim.width", 1000, dim.width)
        assertEquals("dim.height", 200, dim.height)
    }

    @Test
    void test_singleColumn_cell2() {
        Point basePoint = cellsSingleColumn.getBasePoint()
        Point loc = cellsSingleColumn.getCellPosition(2)
        Dimension dim = cellsSingleColumn.getCellDimension(2)
        Point expectedLoc = new Point((int)basePoint.x,
                (int)basePoint.y + (int)dim.height * 2)
        assertEquals("loc.x", expectedLoc.x, loc.x)
        assertEquals("loc.y", expectedLoc.y, loc.y)
        assertEquals("dim.width", 1000, dim.width)
        assertEquals("dim.height", 200, dim.height)
    }

    @Test
    void test_dualColumns_cell0() {
        Point basePoint = cellsDualColumns.getBasePoint()
        Point loc = cellsDualColumns.getCellPosition(0)
        Dimension dim = cellsDualColumns.getCellDimension(0)
        Point expectedLoc = new Point((int)basePoint.x, (int)basePoint.y)
        assertEquals("loc.x", expectedLoc.x, loc.x)
        assertEquals("loc.y", expectedLoc.y, loc.y)
        assertEquals("dim.width", 500, dim.width)
        assertEquals("dim.height", 400, dim.height)
    }

    @Test
    void test_dualColumns_cell1() {
        Point basePoint = cellsDualColumns.getBasePoint()
        Point loc = cellsDualColumns.getCellPosition(1)
        Dimension dim = cellsDualColumns.getCellDimension(1)
        Point expectedLoc =
                new Point((int)basePoint.x + (int)dim.width,
                        (int)basePoint.y)
        assertEquals("loc.x", expectedLoc.x, loc.x)
        assertEquals("loc.y", expectedLoc.y, loc.y)
        assertEquals("dim.width", 500, dim.width)
        assertEquals("dim.height", 400, dim.height)
    }

    @Test
    void test_dualColumns_cell2() {
        Point basePoint = cellsDualColumns.getBasePoint()
        Point loc = cellsDualColumns.getCellPosition(2)
        Dimension dim = cellsDualColumns.getCellDimension(2)
        Point expectedLoc =
                new Point((int)basePoint.x,
                        (int)basePoint.y + (int)dim.height)
        assertEquals("loc.x", expectedLoc.x, loc.x)
        assertEquals("loc.y", expectedLoc.y, loc.y)
        assertEquals("dim.width", 500, dim.width)
        assertEquals("dim.height", 400, dim.height)
    }

}
