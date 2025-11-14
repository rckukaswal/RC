package com.jeff;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class C1 {

    public static void main(String[] args) throws Exception {

        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // STEP 1
        driver.get("https://www.amazon.in");
        System.out.println("STEP 1: Amazon opened.");

        // STEP 2
        WebElement searchBox = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox"))
        );
        searchBox.sendKeys("Laptop");
        searchBox.sendKeys(Keys.ENTER);
        System.out.println("STEP 2: Search submitted.");

        // STEP 3 - Wait until product container loads
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div[data-component-type='s-search-result']")
            )
        );
        System.out.println("STEP 3: Results loaded.");

        // STEP 4 - Get ALL search result products
        List<WebElement> products = driver.findElements(
            By.cssSelector("div[data-component-type='s-search-result'] h2 a")
        );

        System.out.println("PRODUCT COUNT FOUND: " + products.size());

        if (products.size() == 0) {
            System.out.println("❌ No product title found with this selector!");
            driver.quit();
            return;
        }

        // FIRST PRODUCT
        WebElement firstProduct = products.get(0);

        System.out.println("STEP 4: First Product = " + firstProduct.getText());

        firstProduct.click();

        // Step 5: Switch to new tab
        for (String tab : driver.getWindowHandles()) {
            driver.switchTo().window(tab);
        }

        // STEP 6 - Get product title
        WebElement productTitle = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("productTitle"))
        );
        System.out.println("PRODUCT TITLE: " + productTitle.getText());

        // STEP 7
        try {
            WebElement price = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".a-price-whole"))
            );
            System.out.println("PRODUCT PRICE: ₹" + price.getText());
        } catch (Exception e) {
            System.out.println("⚠ Price not available for this product.");
        }

        driver.quit();
        System.out.println("STEP 8: Automation Done!");
    }
}
