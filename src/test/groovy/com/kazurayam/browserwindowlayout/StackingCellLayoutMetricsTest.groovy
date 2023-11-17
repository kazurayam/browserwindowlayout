package com.kazurayam.browserwindowlayout

import static org.junit.Assert.*

import org.openqa.selenium.Dimension
import org.openqa.selenium.Point

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4.class)
class StackingCellLayoutMetricsTest {

    StackingCellLayoutMetrics layoutMetrics
    int NUM_OF_WINDOWS = 3

    @Before
    void setup() {
        layoutMetrics = new StackingCellLayoutMetrics.Builder(NUM_OF_WINDOWS)
                .cellDimension(new Dimension(1280, 1024)).build()
    }


    @Test
    void test_getCellDimension() {
        assertEquals(1280, layoutMetrics.getCellDimension(0).width)
        assertEquals(1024, layoutMetrics.getCellDimension(0).height)
    }

    @Test
    void test_getCellPosition() {
        for (index in 0..2) {
            Point pos = layoutMetrics.getCellPosition(index)
            assertEquals(layoutMetrics.getDisposition().x * index, pos.x)
            assertEquals(layoutMetrics.getDisposition().y * index, pos.y)
        }
    }

}
