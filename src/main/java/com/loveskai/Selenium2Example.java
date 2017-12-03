package com.loveskai;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * This is a simple program to fetch airline name and its price by a given Expedia query link.
 */
public class Selenium2Example {
    public static void main(String[] args) throws InterruptedException {
        WebDriver driver = new FirefoxDriver();

        String expediaHomePage = "https://www.expedia.com/";
        String origin = "LAX";
        String destination = "TPE";
        String departing = "02/15/2018";
        String returning = "02/19/2018";
        String numAdults = "1";
        String preferredClass = "premium";

        driver.get(expediaHomePage);

        driver.findElement(By.id("tab-flight-tab-hp")).click();

        Select adultsSelect = new Select(driver.findElement(By.id("flight-adults-hp-flight")));
        adultsSelect.selectByValue(numAdults);

        // fill the returning input before departing input to prevent auto filling returning input
        WebElement returningInput = driver.findElement(By.id("flight-returning-hp-flight"));
        returningInput.sendKeys(returning);

        WebElement departingInput = driver.findElement(By.id("flight-departing-hp-flight"));
        departingInput.sendKeys(departing);

        WebElement originInput = driver.findElement(By.id("flight-origin-hp-flight"));
        originInput.sendKeys(origin);

        WebElement destinationInput = driver.findElement(By.id("flight-destination-hp-flight"));
        destinationInput.sendKeys(destination);

        driver.findElement(By.id("flight-advanced-options-hp-flight")).click();

        Select preferredClassSelect = new Select(driver.findElement(By.id("flight-advanced-preferred-class-hp-flight")));
        preferredClassSelect.selectByValue(preferredClass);

        driver.findElement(By.id("gcw-flights-form-hp-flight")).submit();

        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.attributeContains(By.id("pi-interstitial"), "style", "display: none;"));

        WebElement flightListingContainer = driver.findElement(By.id("flight-listing-container"));
        List<WebElement> flightElementList = flightListingContainer
                .findElements(By.cssSelector("li[data-is-split-ticket='false']"));
        int numFlightWithPrice = 0;
        for (WebElement flightElement: flightElementList) {
            String airlineName = flightElement
                    .findElement(By.cssSelector("div[class='secondary truncate']"))
                    .getText();
            WebElement priceElement = flightElement.findElement(By.cssSelector("div[class*='price-column']"));
            String price = priceElement.getAttribute("data-test-price-per-traveler");
            if (price != null) {
                System.out.println(++numFlightWithPrice + ". " + airlineName + ": " + price);
            }
        }

        //Close the browser
        driver.quit();
    }
}
