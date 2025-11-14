package com.jeff;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class RC {

    public static void main(String[] args) throws Exception {

        WebDriverManager.chromedriver().setup(); 
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        driver.get("https://www.google.com");

        WebElement searchBox = driver.findElement(By.name("q"));
        searchBox.sendKeys("Selenium WebDriver automation tutorial");
        searchBox.sendKeys(Keys.ENTER);

        // EXPLICIT WAIT (Fix NoSuchElementException)
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // Google new layout — the first result title is inside an <h3> but wrapped differently
        WebElement firstResult = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h3"))
        );

        System.out.println("First result: " + firstResult.getText());
        firstResult.click();

        Thread.sleep(2000);
        System.out.println("Page Title = " + driver.getTitle());
        

        driver.quit();
    }
}
