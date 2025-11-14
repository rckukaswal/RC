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

public class RC {

    public static void main(String[] args) throws Exception {

        WebDriverManager.chromedriver().setup();  
        WebDriver driver = new ChromeDriver();
        driver.manage().window().maximize();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

        // 1️⃣ AMAZON OPEN
        driver.get("https://www.amazon.in");
        System.out.println("STEP 1: Amazon opened.");

        // 2️⃣ SEARCH LAPTOP
        WebElement searchBox = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.id("twotabsearchtextbox"))
        );
        searchBox.sendKeys("Laptop");
        searchBox.sendKeys(Keys.ENTER);
        System.out.println("STEP 2: Search submitted.");

        // 3️⃣ WAIT FOR RESULTS
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("h2 a")));

        // 4️⃣ GET FIRST PRODUCT
        List<WebElement> products = driver.findElements(By.cssSelector("h2 a"));
        WebElement firstProduct = products.get(0);

        System.out.println("STEP 3: First Product Title = " + firstProduct.getText());

        // 5️⃣ CLICK FIRST PRODUCT
        firstProduct.click();
        System.out.println("STEP 4: Product page opened.");

        // SWITCH TAB (Amazon opens in new tab)
        for (String tab : driver.getWindowHandles()) {
            driver.switchTo().window(tab);
        }

        // 6️⃣ WAIT FOR PRICE
        WebElement price = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#corePrice_feature_div")));
        System.out.println("STEP 5: Product Price Section Found.");

        // 7️⃣ PRINT TITLE + PRICE
        String productTitle = driver.findElement(By.id("productTitle")).getText();
        String productPrice = driver.findElement(By.cssSelector(".a-price-whole")).getText();

        System.out.println("PRODUCT TITLE: " + productTitle);
        System.out.println("PRODUCT PRICE: ₹" + productPrice);

        // 8️⃣ CLOSE BROWSER
        driver.quit();
        System.out.println("STEP 6: Automation Completed!");
    }
}
