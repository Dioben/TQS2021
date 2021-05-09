package com.example.homework;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SeleniumInterfaceIT {

    @LocalServerPort
    int randomServerPort;



    @Test
    @Order(0)
    void initialCache(ChromeDriver driver){
        driver.get("http://localhost:" + randomServerPort + "/cache");
        Assertions.assertEquals("Cache has been used in 0 out of 0 calls\n" +
                "0 calls to the main API , 0 successful calls\n" +
                "0 calls to the backup API , 0 successful calls",driver.findElement(By.tagName("pre")).getText());
    }


    @Test
    @Order(1)
    void validQuery(ChromeDriver driver) throws InterruptedException {
        driver.get("http://localhost:" + randomServerPort + "/");
        driver.manage().window().setSize(new Dimension(1920, 885));
        TimeUnit.SECONDS.sleep(1);
        WebElement text = driver.findElement(By.id("info"));
        Assertions.assertEquals("Search for coordinates or click the map for information",text.getText());
        driver.findElement(By.cssSelector(".gm-style > div:nth-child(1) > div:nth-child(3)")).click();
        TimeUnit.SECONDS.sleep(1);
        Assertions.assertEquals("AQI: ",text.getText().substring(0,5));
        driver.findElement(By.cssSelector(".gm-style > div:nth-child(1) > div:nth-child(3)")).click();
        driver.findElement(By.cssSelector("input:nth-child(5)")).click();
    }
    @Test
    @Order(2)
    void OOBQuery(ChromeDriver driver) throws InterruptedException { //technically counts as a cache usage
        driver.get("http://localhost:" + randomServerPort + "/");
        WebElement text = driver.findElement(By.id("info"));
        Assertions.assertEquals("Search for coordinates or click the map for information",text.getText());
        driver.manage().window().setSize(new Dimension(1920, 885));
        driver.findElement(By.cssSelector("p:nth-child(2)")).click();
        driver.findElement(By.id("latbox")).sendKeys("-200");
        driver.findElement(By.cssSelector("input:nth-child(5)")).click();
        TimeUnit.SECONDS.sleep(1);
        Assertions.assertEquals("Search has failed, please check your coordinate bounds",text.getText());
    }
    @Test
    @Order(3)
    void cacheCheck(ChromeDriver driver){
        driver.get("http://localhost:" + randomServerPort + "/cache");
        Assertions.assertEquals("Cache has been used in 2 out of 3 calls\n2 calls to the main API",
                                        driver.findElement(By.tagName("pre")).getText().substring(0,63));
    }
}
