package automationLibrary;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;

import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import testScriptsSmoke.Input;

public class ElementCollection implements Iterable<Element> {
    private WebElement element = null;
    private Driver driver;
    private By by;
    private List<WebElement> elements = null;
    BaseClass base;


    public ElementCollection(Driver driver, By by, WebElement element) {
        this.driver = driver;
        this.by = by;
        this.element = element;
        base = new BaseClass(driver);
    }

    public Element getElementByIndex(int index) {
        AssertExists();
        return new Element(driver, elements.get(index));
    }

    public int size() {
        AssertExists();
        return elements.size();
    }

    /// <summary>
    /// Waits until the collection becomes present or until the timeout is reached.
    /// </summary>
    public ElementCollection WaitUntilPresent() {
        long startingTime = System.currentTimeMillis();
        Duration timeout = Duration.ofMillis((long) driver.getApplicationTimeout());
        Duration interval = Duration.ofMillis((long) driver.getApplicationPollingInterval());
        boolean running = true;
        while (running) {
            try {
                if (elements != null || FindWebElements() != null) return this;
            } catch (Exception e) {
                try {
                    Thread.currentThread().join(interval.toMillis());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                //System.Threading.Thread.CurrentThread.Join((int)interval.Value.TotalMilliseconds);
                if ((System.currentTimeMillis() - startingTime) > timeout.toMillis()) {
                    running = false;
                }

            }
        }
        throw new NotFoundException(String.format("Unable to located element {0}", by.toString()));
    }

    /// <summary>
    /// Waits until the collection becomes present or until the timeout is reached.
    /// </summary>
    /// <param name="timeout">Optional: override the application timeout value</param>
    /// <param name="message">Optional: override the message used if the element is never found</param>
    /// <param name="interval">Optional: override the polling interval value</param>
    public ElementCollection WaitUntilPresent(Duration timeout, String message, Duration interval) {
        long startingTime = System.currentTimeMillis();
        boolean running = true;
        while (running) {
            try {
                if (elements != null || FindWebElements() != null) return this;
            } catch (Exception e) {
                try {
                    Thread.currentThread().join(interval.toMillis());
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                //System.Threading.Thread.CurrentThread.Join((int)interval.Value.TotalMilliseconds);
                if ((System.currentTimeMillis() - startingTime) > timeout.toMillis()) {
                    running = false;
                }

            }
        }
        throw new NotFoundException(message.toString());
    }

    public List<WebElement> FindWebElements() {
        if (element != null) {
            return element.findElements(by);
        }
        if (driver != null) {
            return driver.getWebDriver().findElements(by);
        }
        return null;
    }

    private void AssertExists() {
        if (elements == null) {
            elements = FindWebElements();
        }

        AssertElementsFound();
    }

    private void AssertElementsFound() {
        if (elements != null) return;
        throw new NotFoundException(String.format("unable to locate elementsb: {0}", by.toString()));
    }

    public Iterator<Element> iterator() {
        AssertExists();
        List<Element> elementList = new ArrayList<Element>();
        for (WebElement elem : elements) {
            elementList.add(new Element(driver, elem));
        }
        return elementList.iterator();
    }

    public Boolean Displayed() {
        AssertExists();
        return element.isDisplayed();

    }

    public Boolean Visible() {

        return this.Displayed();
    }

    public Boolean Present() {

        return this.Exists();
    }

    public Boolean Exists() {
        AssertExists();
        return true;

    }

    /**
     * author name-Jayanthi
     */
    public boolean isElementPresent() {
        try {
            return this.Exists();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public Boolean isElementAvailable(int timeOut) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        base.implicitWait(0);
        for (int iteration = 1; iteration <= timeOut; iteration++) {
            try {
                element = driver.getWebDriver().findElement(by);
                base.implicitWait(30);
                return true;
            } catch (StaleElementReferenceException E) {
                base.waitTime(1);
                if (iteration == timeOut) {
                    E.printStackTrace(pw);
                    UtilityLog.info(sw.toString());
                } else {
                    continue;
                }
            } catch (NoSuchElementException E) {
                base.waitTime(1);
                if (iteration == timeOut) {
                    E.printStackTrace(pw);
                    UtilityLog.info(sw.toString());
                } else {
                    continue;
                }
            } catch (WebDriverException E) {
                base.waitTime(1);
                if (iteration == timeOut) {
                    E.printStackTrace(pw);
                    UtilityLog.info(sw.toString());
                } else {
                    continue;
                }
            }
        }
        base.implicitWait(30);
        return false;


    }


}

	

