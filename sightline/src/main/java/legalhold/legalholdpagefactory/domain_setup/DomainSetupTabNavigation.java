package legalhold.legalholdpagefactory.domain_setup;

import automationLibrary.Driver;
import legalhold.setup.BaseModule;

import java.io.IOException;
import java.util.HashMap;

public class DomainSetupTabNavigation extends BaseModule {
    HashMap<DomainSetupTabs, String> tabLocatorNameMap = new HashMap<>();

    public DomainSetupTabNavigation(Driver driver) throws IOException {
        super("src/main/java/legalhold/selectors/domain_setup/sub_tab_navigation.properties", driver);

        tabLocatorNameMap.put(DomainSetupTabs.DataSources, "tabDataSource");
        tabLocatorNameMap.put(DomainSetupTabs.Email, "tabEmail");
        tabLocatorNameMap.put(DomainSetupTabs.EmployeeData, "tabHRIS");
        tabLocatorNameMap.put(DomainSetupTabs.Permissions, "tabPermission");
        tabLocatorNameMap.put(DomainSetupTabs.CaseAccess, "tabCaseAccess");
        tabLocatorNameMap.put(DomainSetupTabs.Fields, "tabCustomFields");
        tabLocatorNameMap.put(DomainSetupTabs.Migration, "tabMigration");

    }

    public void navigateToDomainSetupTab(DomainSetupTabs tabName) {
        driver.FindElementById(locatorReader.getobjectLocator(tabLocatorNameMap.get(tabName))).waitAndClick(30);
        driver.waitForPageToBeReady();
    }


}
