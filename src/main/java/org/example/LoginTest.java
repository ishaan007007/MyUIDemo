package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.awt.*;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class LoginTest {
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
                {"standard_user", "secret_sauce"},
                {"problem_user", "secret_sauce"},
                {"performance_glitch_user", "secret_sauce"},
                {"error_user", "secret_sauce"},
                {"visual_user", "secret_sauce"},
                {"locked_out_user", "secret_sauce"}
        };
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password) {
        // Find username and password fields and login button
        driver.findElement(By.xpath("//input[@id='user-name']")).sendKeys(username);
        driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password); 
        driver.findElement(By.xpath("//input[@id='login-button']")).click(); 
     
        String expectedUrl = "https://qa-challenge.codesubmit.io/inventory.html";
        Assert.assertEquals(driver.getCurrentUrl(), expectedUrl, "Login failed for user: " + username);

       
        driver.findElement(By.xpath("//button[@id='react-burger-menu-btn']")).click();


        WebElement logoutLink = driver.findElement(By.id("logout_sidebar_link"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", logoutLink);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        driver.get("https://qa-challenge.codesubmit.io/");

    }
    @AfterClass
    public void tearDown() {
        driver.quit();
   }

}



