package com.loveskai;

import com.google.gson.Gson;
import com.loveskai.model.Request;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class Scanner {
    private static Logger log = LoggerFactory.getLogger("Scanner");
    private static final Gson gson = new Gson();

    void start() {
        // fetch what to scan
        Request request = new Request()
                .setDepartureAirport("LAX")
                .setArrivalAirport("TPE")
                .setDepartureDate("02/15/2018")
                .setReturnDate("02/19/2018")
                .setCabinClass("premium");

        // scanning
        Map<String, Float> lowestPriceMap = scan(request);
        System.out.println(gson.toJson(lowestPriceMap));
    }

    private Map<String, Float> scan(Request request) {
        Map<String, Float> lowestPriceMap = new HashMap<>(); // saving lowest price per airline
        long start = System.currentTimeMillis();
        log.debug("start scanning: " + request);

        ChromeOptions options = new ChromeOptions();
        WebDriver driver = new ChromeDriver(options);
        String queryURL = String.format(
                "https://www.expedia.com/Flights-Search?flight-type=on&starDate=%s&endDate=%s&" +
                "_xpid=11905|1&mode=search&trip=roundtrip&" +
                "leg1=from:%s,to:%s,departure:%sTANYT&" +
                "leg2=from:%s,to:%s,departure:%sTANYT&" +
                "passengers=children:0,adults:1,seniors:0,infantinlap:Y&" +
                "options=cabinclass:%s,",
                request.getDepartureDate(),
                request.getReturnDate(),
                request.getDepartureAirport(),
                request.getArrivalAirport(),
                request.getDepartureDate(),
                request.getArrivalAirport(),
                request.getDepartureAirport(),
                request.getReturnDate(),
                request.getCabinClass());

        driver.get(queryURL);

        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.attributeContains(
                By.id("pi-interstitial"), "style", "display: none;"));
        List<WebElement> flightElementList = driver
                .findElement(By.id("flight-listing-container"))
                .findElements(By.cssSelector("li[data-is-split-ticket='false']"));
        for (WebElement flightElement : flightElementList) {
            String airlineName = flightElement
                    .findElement(By.cssSelector("div[class='secondary truncate']"))
                    .getText();
            WebElement priceElement = flightElement.findElement(By.cssSelector("div[class*='price-column']"));
            String priceString = priceElement.getAttribute("data-test-price-per-traveler");
            if (priceString != null) {
                try {
                    float curPrice = priceStringToInt(priceString);
                    Float prevPrice = lowestPriceMap.get(airlineName);
                    if (prevPrice == null || curPrice < prevPrice) {
                        lowestPriceMap.put(airlineName, curPrice);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        }

        //Close the browser
        driver.quit();
        log.info("Total time: {} secs", (System.currentTimeMillis()-start)/1000.0);
        return lowestPriceMap;
    }

    private static float priceStringToInt(String priceString) throws ParseException {
        Locale locale = Locale.US;
        Number number;
        number = NumberFormat.getCurrencyInstance(locale).parse(priceString);
        return number.floatValue();
    }
}
