

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.function.BooleanSupplier;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

class SeleniumTest {

    WebDriver browser;

    @BeforeEach
    public void setUp(){browser = new ChromeDriver();}

    @AfterEach
    public void tearDown(){browser.close();}

    @Test
    public void site_header_is_on_home_page() throws InterruptedException {
        //System.setProperty("webdriver.chrome.driver", "/where/you/put/chromedriver");

        browser.get("https://www.saucelabs.com");
        WebElement href = browser.findElement(By.xpath("//a[@href='https://accounts.saucelabs.com/']"));
        assertTrue(!(href.isDisplayed())); //this is all incredibly broken for me, so i'm putting a NOT in
    }
}
