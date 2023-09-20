package legalhold.legalholdpagefactory.domain_setup;

import automationLibrary.Driver;
import legalhold.setup.BaseModule;

import java.io.IOException;

public class DomainSetupTabNavigation extends BaseModule {
    public DomainSetupTabNavigation(Driver driver) throws IOException {
        super("src/main/java/legalhold/selectors/domain_setup/sub_tab_navigation.properties", driver);
    }

    public void navigateToDataSourceTab(){
        driver.FindElementById(locatorReader.getobjectLocator("tabDataSource")).waitAndClick(30);
        driver.waitForPageToBeReady();
    }

    public void navigateToEmailTab(){
        driver.FindElementById(locatorReader.getobjectLocator("tabEmail")).waitAndClick(30);
        driver.waitForPageToBeReady();
    }

    public void navigateToEmployeeDataTab(){
        driver.FindElementById(locatorReader.getobjectLocator("tabHRIS")).waitAndClick(30);
        driver.waitForPageToBeReady();
    }

    public void navigateToPermissionsTab(){
        driver.FindElementById(locatorReader.getobjectLocator("tabPermission")).waitAndClick(30);
        driver.waitForPageToBeReady();
    }

    public void navigateToCaseAccessTab(){
        driver.FindElementById(locatorReader.getobjectLocator("tabCaseAccess")).waitAndClick(30);
        driver.waitForPageToBeReady();
    }

    public void navigateToCustomFieldsTab(){
        driver.FindElementById(locatorReader.getobjectLocator("tabCustomFields")).waitAndClick(30);
        driver.waitForPageToBeReady();
    }
}
