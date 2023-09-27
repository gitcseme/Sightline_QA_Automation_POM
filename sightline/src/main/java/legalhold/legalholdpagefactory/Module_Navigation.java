package legalhold.legalholdpagefactory;

import automationLibrary.Driver;
import legalhold.setup.BaseModule;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Module_Navigation extends BaseModule {

    HashMap<LHMenus, Integer> menuNameIndexMap = new HashMap<>();

    List<WebElement> sidebarElements;

    public Module_Navigation(Driver driver) throws IOException {
        super("./src/main/java/legalhold/selectors/sl_slh_integration/navigation.properties", driver);

        sidebarElements = driver.getWebDriver().findElements(By.className(locatorReader.getobjectLocator("ModuleTabs")));

        menuNameIndexMap.put(LHMenus.Cases, 0);
        menuNameIndexMap.put(LHMenus.Employees, 1);
        menuNameIndexMap.put(LHMenus.Templates, 2);
        menuNameIndexMap.put(LHMenus.Mailbox, 3);
        menuNameIndexMap.put(LHMenus.Reports, 4);
        menuNameIndexMap.put(LHMenus.AuditTrail, 5);
        menuNameIndexMap.put(LHMenus.GlobalNotice, 6);
        menuNameIndexMap.put(LHMenus.DomainSetup, 7);

    }


    public void navigateToMenu(LHMenus menu) {
        sidebarElements.get(menuNameIndexMap.get(menu)).click();
        driver.waitForPageToBeReady();
    }

}


