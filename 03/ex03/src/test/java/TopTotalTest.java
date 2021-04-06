import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.chrome.ChromeDriver;
import pages.DeveloperApplyPage;
import pages.HomePage;
import org.openqa.selenium.WebDriver;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TopTotalTest {


        WebDriver driver;

        @BeforeEach
        public void setup(){
            //use FF Driver
            driver = new ChromeDriver();

        }

        @Test
        public void applyAsDeveloper() throws InterruptedException {
            //Create object of HomePage Class
            HomePage home = new HomePage(driver);
            home.clickOnDeveloperApplyButton();

            //Create object of DeveloperApplyPage
            DeveloperApplyPage applyPage =new DeveloperApplyPage(driver);
            //Check if page is opened
            assertTrue(applyPage.isPageOpened());

            //Fill up data
            applyPage.setDeveloper_email("dejan@toptal.com");
            applyPage.setDeveloper_full_name("Dejan Zivanovic Automated Test");
            applyPage.setDeveloper_password("password123");
            applyPage.setDeveloper_password_confirmation("password123");


            //Click on join
            //applyPage.clickOnJoin();
        }

        @AfterEach
        public void close(){
            driver.close();
        }
    }

