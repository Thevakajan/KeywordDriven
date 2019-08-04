package com.test.qa.keywords;


import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;


public class Keywords {
    public static Properties prop = new Properties();
    protected static WebDriver driver;
    private static Logger logger = LogManager.getLogger(Keywords.class);
    private static String os;
    private static String baseUrl;
    private static String webDriverLocation;

    public void Open_Browser(String browser) throws IOException {
        InputStream input = null;
        input =
                this.getClass().getClassLoader().getResourceAsStream("config/config.properties");
        prop.load(input);
        os = prop.getProperty("os");
        baseUrl = prop.getProperty("baseUrl");
        webDriverLocation = prop.getProperty("webDriverLocation");
        switch (os) {
            case "windows":
                if (browser.equals("chrome")) {
                    System.setProperty("webdriver.chrome.driver", webDriverLocation +
                            "chromedriver.exe");
                    driver = new ChromeDriver();
                } else {
                    System.setProperty("webdriver.gecko.driver", webDriverLocation +
                            "geckodriver,exe");
                    driver = new FirefoxDriver();
                }
                break;
            case "ubuntu":
                if (browser.equals("chrome")) {
                    System.setProperty("webdriver.chrome.driver", webDriverLocation +
                            "chromedriver");
                    driver = new ChromeDriver();
                } else {
                    System.setProperty("webdriver.gecko.driver", webDriverLocation +
                            "geckodriver");
                    driver = new FirefoxDriver();
                }
                break;
        }
    }

    public void Navigate_Url(String url) {
        driver.get(url);
        logger.log(Level.INFO, "Navigate to url : " + url);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    public void Enter_Text(String locator, String inputText) {
        driver.findElement(By.xpath(locator)).sendKeys(inputText);
        logger.log(Level.INFO, "Enter input text : " + inputText + " to the ui element with xpath : " + locator);
    }

    public void Press_Button(String locator) {
        driver.findElement(By.xpath(locator)).click();
    }

    public void Verify_Text(String locator, String expectedText) {
        String actualText = driver.findElement(By.xpath(locator)).getText();
        logger.log(Level.INFO, "Reading text from ui element with xpath : " + locator);
        Assert.assertEquals(actualText, expectedText);
    }

    public void Click(String locator) {
        driver.findElement(By.xpath(locator)).click();
        logger.log(Level.INFO, "Click ui element with xpath : " + locator);
    }

    public void Close_Browser() {
        driver.quit();
        logger.log(Level.INFO, "Close browser");

    }
}
