package automationLibrary;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CustomWebDriverWait extends WebDriverWait {

    public CustomWebDriverWait(WebDriver driver, long timeOutInSeconds) {
        super(driver, Duration.ofSeconds(timeOutInSeconds));
    }

    public CustomWebDriverWait(WebDriver driver, long timeOutInSeconds, long sleepInMillis) {
        super(driver, Duration.ofSeconds(timeOutInSeconds), Duration.ofSeconds(sleepInMillis));
    }

    // You can add more constructors as needed.

}