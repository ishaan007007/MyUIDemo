package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.apache.commons.io.FileUtils;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class Purchase {
    private WebDriver driver;

    @BeforeClass
    public void setup() {

        System.setProperty("WebDriver.chrome.driver", "C:/Users/Rahul/Downloads/chrome-win64/chrome-win64");
        driver = new ChromeDriver();
        driver.get("https://qa-challenge.codesubmit.io/");
        driver.manage().window().maximize();
    }

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][] {
                {"standard_user", "secret_sauce"}

        };
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password) throws IOException {
        // Find username and password fields and login button
        driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys(username);
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password); 
        driver.findElement(By.xpath("//input[@id='login-button']")).click(); 

    
        String expectedUrl = "https://qa-challenge.codesubmit.io/inventory.html";
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl, "Login failed for user: " + username);
        driver.findElement(By.xpath("//button[@id='add-to-cart-sauce-labs-backpack']")).click();
        driver.findElement(By.xpath("//a[@class='shopping_cart_link']")).click();
        driver.findElement(By.xpath("//button[@id='checkout']")).click();

        driver.findElement(By.xpath("//input[@id='first-name']")).sendKeys("ishaan");
        driver.findElement(By.xpath("//input[@id='last-name']")).sendKeys("iqbal");
        driver.findElement(By.xpath("//input[@id='postal-code']")).sendKeys("2191");
        driver.findElement(By.xpath("//input[@id='continue']")).click();

        driver.findElement(By.xpath("//button[@id='finish']")).click();

        WebElement element = driver.findElement(By.xpath("//h2[normalize-space()='Thank you for your order!']"));
        String capturedText = element.getText();
        System.out.println("Captured Text: " + capturedText);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(100));

        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File("screenshot.png"));



    }
   @AfterClass
    public void tearDown() {
    driver.quit();
    }

}



