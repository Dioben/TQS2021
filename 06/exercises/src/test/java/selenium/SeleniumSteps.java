package selenium;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class SeleniumSteps {
    private WebDriver driver;

@Given("A new browser session")
    public void setup() {
    driver = new ChromeDriver();
    }

    @When("I navigate to {string}")
    public void navigate(String site) {
        driver.get(site);
    }
    @When("I type {string}")
    public void type(String query){
        driver.findElement(By.name("q")).sendKeys(query);

    }
    @When("I press Enter")
    public void Enter() {
        driver.findElement(By.name("q")).sendKeys(Keys.ENTER);
    }

    @Then("I should be shown results including {string}")
    public void the_result_includes(String expected) {
    try {
        driver.findElement(By.xpath("//*[contains(text(), '" + expected + "')]"));
    }catch (NoSuchElementException a){
        throw new AssertionError(
                "\"" + expected + "\" not available in results");
    }finally { driver.quit();}

    }

    @And("I click {string}")
    public void iClick(String arg0) {
    driver.findElement(By.xpath("//input[@value='"+arg0+"']")).click();
    }
}

