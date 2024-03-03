package examples

import com.kazurayam.browserwindowlayout.BrowserWindowLayoutManager
import com.kazurayam.browserwindowlayout.TilingCellLayoutMetrics

import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.BeforeClass
import org.junit.Test
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions

class LaunchMultipleChromeWindowsInTilingLayoutTest {

    @Test
    void test_open2windows_in_tiling_layout() {
        TilingCellLayoutMetrics layoutMetrics =
                new TilingCellLayoutMetrics.Builder(2).build()
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("excludeSwitches", ["enable-automation"]);
        ChromeDriver driver0 = new ChromeDriver(options)
        ChromeDriver driver1 = new ChromeDriver(options)
        BrowserWindowLayoutManager.layout(driver0,
                layoutMetrics.getCellPosition(0),
                layoutMetrics.getCellDimension(0))
        BrowserWindowLayoutManager.layout(driver1,
                layoutMetrics.getCellPosition(1),
                layoutMetrics.getCellDimension(1))
        driver0.navigate().to("https://en.wikipedia.org/wiki/Pablo_Picasso")
        driver1.navigate().to("https://en.wikipedia.org/wiki/Vincent_van_Gogh")
        Thread.sleep(3000)
        driver0.quit()
        driver1.quit()
    }

    @BeforeClass
    static void beforeClass() {
        // setup the ChromeDriver binary
        WebDriverManager.chromedriver().setup()
    }
}
