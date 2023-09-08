package legalhold.legalholdpagefactory.login_logout;

import automationLibrary.Driver;
import legalhold.setup.BaseModule;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import testScriptsSmoke.Input;

import java.io.IOException;

public class LogoutLegalHold extends BaseModule {
    public LogoutLegalHold(Driver driver) throws IOException {
        super(driver);
    }

    public void logOutFromLegalHold() throws InterruptedException {
        driver.getWebDriver().navigate().refresh();
        driver.waitForPageToBeReady();
        WebElement userMenu = driver.getWebDriver().findElement(By.cssSelector("a[title='User Menu']"));
        wait.until(ExpectedConditions.elementToBeClickable(userMenu));
        userMenu.click();
        driver.FindElementByCssSelector("a[aria-label='Log out']").Click();
        System.out.println("*****************************************************************************");
        System.out.println("Log out from Legal Hold is successful");
        Thread.sleep(3000);
        driver.waitForPageToBeReady();
    }

    public void logOutFromSightline(){
        String currentUrl = driver.getUrl();
        String sessionOutUrl = Input.url+"en-us/Login/SessionExpired";
        if(!currentUrl.equalsIgnoreCase(sessionOutUrl)){
            driver.FindElementById("user-selector").waitAndClick(30);
            driver.FindElementByCssSelector("a[title='Sign Out']").waitAndClick(30);
            System.out.println("*****************************************************************************");
            System.out.println("Log out from Sightline is successful");
        }
    }
}
