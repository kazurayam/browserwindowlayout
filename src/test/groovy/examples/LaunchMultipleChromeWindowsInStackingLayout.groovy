package examples


import com.kazurayam.browserwindowlayout.StackingCellLayoutMetrics
import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.BeforeClass
import org.junit.Test
import org.openqa.selenium.Dimension
import org.openqa.selenium.Point
import org.openqa.selenium.chrome.ChromeDriver

class LaunchMultipleChromeWindowsInStackingLayout {

    @Test
    void test_open2windows_in_stacking_layout() {
        StackingCellLayoutMetrics layoutMetrics =
                new StackingCellLayoutMetrics.Builder(2)
                        .cellDimension(new Dimension(1000, 600))
                        .disposition(new Point(400, 200))
                        .build()
        ChromeDriver driver0 = new ChromeDriver()
        BrowserWindowLayoutManager.layout(driver0,
                layoutMetrics.getCellPosition(0),
                layoutMetrics.getCellDimension(0))
        ChromeDriver driver1 = new ChromeDriver()
        BrowserWindowLayoutManager.layout(driver1,
                layoutMetrics.getCellPosition(1),
                layoutMetrics.getCellDimension(1))
        driver0.navigate().to("https://en.wikipedia.org/wiki/Pablo_Picasso")
        driver1.navigate().to("https://en.wikipedia.org/wiki/Vincent_van_Gogh")
        Thread.sleep(1000)
        driver0.quit()
        driver1.quit()
    }

    @BeforeClass
    static void beforeClass() {
        // setup the ChromeDriver binary
        WebDriverManager.chromedriver().setup()
    }

}
