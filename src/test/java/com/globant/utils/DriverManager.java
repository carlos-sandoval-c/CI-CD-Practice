package com.globant.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class DriverManager {
    private final WebDriver driver;
    protected static Logger logger = LogManager.getLogger(DriverManager.class);

    public DriverManager() {
        String browser = System.getProperty("browserName");
        if (browser == null) {
            browser = "chrome";
            DriverManager.logger.info("DRIVER_MANAGER: ENV VARIABLE IS EMPTY OR INVALID - USING DEFAULT BROWSER");
        }

        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--no-sandbox");
                firefoxOptions.addArguments("--disable-dev-shm-usage");
                firefoxOptions.addArguments("--headless");
                this.driver = new FirefoxDriver(firefoxOptions);
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--no-sandbox");
                chromeOptions.addArguments("--disable-dev-shm-usage");
                chromeOptions.addArguments("--headless");
                this.driver = new ChromeDriver(chromeOptions);
                break;
        }
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public void goToUrl(String url) throws IllegalArgumentException {
        if (url == null || url.isEmpty())
            throw new IllegalArgumentException("DriverManger: Try to get empty url");
        this.driver.get(url);
    }

    public void maximizeWindow() {
        this.driver.manage().window().maximize();
    }

    public void close() {
        this.driver.close();
    }
}