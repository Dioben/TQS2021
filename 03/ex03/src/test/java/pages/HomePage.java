package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.concurrent.TimeUnit;

public class HomePage {
    private WebDriver driver;

    //Page URL
    private static String PAGE_URL="https://www.toptal.com";

    //Locators

    //EXPAND
    @FindBy(how = How.CSS, using= ".\\_2zORpOH1")
    private WebElement sidebutton;
    //Apply as Developer Button
    @FindBy(how = How.LINK_TEXT, using = "Apply as a Freelancer")
    private WebElement developerApplyButton;

    //Constructor
    public HomePage(WebDriver driver){
        this.driver=driver;
        driver.get(PAGE_URL);
        //Initialise Elements
        PageFactory.initElements(driver, this);
    }

    public void clickOnDeveloperApplyButton() throws InterruptedException {
        sidebutton.click();
        TimeUnit.SECONDS.sleep(1);
        developerApplyButton.click();

    }
}