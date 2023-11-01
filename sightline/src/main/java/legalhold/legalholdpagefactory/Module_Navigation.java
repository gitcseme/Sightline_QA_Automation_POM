package legalhold.legalholdpagefactory;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import legalhold.setup.BaseModule;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Module_Navigation extends BaseModule {

    HashMap<LHMenus, Integer> menuNameIndexMap = new HashMap<>();

    ElementCollection sidebarElements;
    List<WebElement> elems;

    public Module_Navigation(Driver driver) throws IOException {
        super("./src/main/java/legalhold/selectors/sl_slh_integration/navigation.properties", driver);

        sidebarElements = driver.FindElementsByClassName(locatorReader.getobjectLocator("ModuleTabs"));
//        elems = driver.getWebDriver().findElements(By.className(locatorReader.getobjectLocator("ModuleTabs")));

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
        sidebarElements.getElementByIndex(menuNameIndexMap.get(menu)).waitAndClick(30);
//        elems.get(menuNameIndexMap.get(menu)).click();
        driver.waitForPageToBeReady();
    }

    public void changeTenant(String tenantName) throws InterruptedException {
        var tenantDropdown = driver.FindElementById(locatorReader.getobjectLocator("tenantDropdown"));
        tenantDropdown.selectFromDropdown().selectByVisibleText(tenantName);
        Thread.sleep(3000);
        driver.waitForPageToBeReady();
        var currentTenant = tenantDropdown.getText();
        if (currentTenant.equalsIgnoreCase(tenantName)) {
            System.out.println("Tenant switch successful. Current tenant is: " + currentTenant);
        } else {
            throw new RuntimeException("System couldn't switch to '" + tenantName + "', Current tenant is: " + currentTenant);
        }
    }

}


