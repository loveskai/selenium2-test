package com.loveskai;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.util.List;

/**
 * This is a simple program to fetch airline name and its price by a given Expedia query link.
 */
public class Selenium2Example {
    public static void main(String[] args) {
        WebDriver driver = new FirefoxDriver();
        String url = "https://www.expedia.com/Flights-Search?flight-type=on&starDate=02%2F14%2F2018&endDate=02%2F19%2F2018&_xpid=11905%7C1&mode=search&trip=roundtrip&leg1=from%3ALos+Angeles%2C+CA+%28LAX-Los+Angeles+Intl.%29%2Cto%3ATaipei%2C+Taiwan+%28TPE-All+Airports%29%2Cdeparture%3A02%2F14%2F2018TANYT&leg2=from%3ATaipei%2C+Taiwan+%28TPE-All+Airports%29%2Cto%3ALos+Angeles%2C+CA+%28LAX-Los+Angeles+Intl.%29%2Cdeparture%3A02%2F19%2F2018TANYT&passengers=children%3A0%2Cadults%3A1%2Cseniors%3A0%2Cinfantinlap%3AY&options=cabinclass%3Apremium%2C&searchPriorityOverride=213";
        driver.get(url);

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
                System.out.print(++numFlightWithPrice + ". ");
                System.out.println(airlineName + ": " + price);
            }
        }

        //Close the browser
        driver.quit();
    }
}
