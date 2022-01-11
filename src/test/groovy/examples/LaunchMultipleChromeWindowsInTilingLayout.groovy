package examples

import com.kazurayam.browserwindowlayout.BrowserWindowLayoutManager
import com.kazurayam.browserwindowlayout.TilingWindowLayoutMetrics

import io.github.bonigarcia.wdm.WebDriverManager
import org.junit.BeforeClass
import org.junit.Test
import org.openqa.selenium.chrome.ChromeDriver

class LaunchMultipleChromeWindowsInTilingLayout {

    @Test
    void test_open2windows_in_tiling_layout() {
        TilingWindowLayoutMetrics layoutMetrics = new TilingWindowLayoutMetrics.Builder(2).build()
        ChromeDriver driver0 = new ChromeDriver()
        ChromeDriver driver1 = new ChromeDriver()
        BrowserWindowLayoutManager.layout(driver0,
                layoutMetrics.getWindowPosition(0),
                layoutMetrics.getWindowDimension(0))
        BrowserWindowLayoutManager.layout(driver1,
                layoutMetrics.getWindowPosition(1),
                layoutMetrics.getWindowDimension(1))
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
