package legalhold.sprintwork.sprint_41.testrunner;

import legalhold.legalholdpagefactory.LHMenus;
import legalhold.legalholdpagefactory.cases.CaseFactories;
import legalhold.legalholdpagefactory.domain_setup.DomainSetupTabNavigation;
import legalhold.legalholdpagefactory.domain_setup.DomainSetupTabs;
import legalhold.legalholdpagefactory.domain_setup.data_source.DataSourceFactories;
import legalhold.setup.BaseRunner;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;

public class Sprint41TestRunner extends BaseRunner {
    protected DataSourceFactories dataSourceFactories;
    protected DomainSetupTabNavigation domainSetupTabNavigation;
    protected CaseFactories caseFactories;
    public static String dataSourceName;

    public Sprint41TestRunner() throws ParseException, IOException, InterruptedException {
        dataSourceFactories = new DataSourceFactories(driver);
        domainSetupTabNavigation = new DomainSetupTabNavigation(driver);
        caseFactories = new CaseFactories(driver);
    }

    @Test(priority = 1, enabled = false)
    public void navigateToDataSourceTab() throws IOException {
        getNavigation().navigateToMenu(LHMenus.DomainSetup);
        domainSetupTabNavigation.navigateToDomainSetupTab(DomainSetupTabs.DataSources);
    }

    @Test(priority = 2, enabled = false)
    public void createDataSource() throws InterruptedException {
        dataSourceFactories.goToCreateDataSourcePage();
        dataSourceName = dataSourceFactories.enterDataSourceNameAndDescription();
        dataSourceFactories.enterDataSourceUrl("https://login.microsoftonline.com");
        dataSourceFactories.openConfigModal();
        dataSourceFactories.enterTenantID("9440f0a4-3c93-44be-a379-740f10731bf6");
        dataSourceFactories.enterApplicationID("89abeaac-8113-463b-8d08-2f66b4210111");
        dataSourceFactories.enterUserName("madmin@consiliodeveloper.onmicrosoft.com");
        dataSourceFactories.enterPassword("SLHT3st@");
        dataSourceFactories.enterClientSecret("PHY8Q~XAwgFmKffKFe6Ulg8Z~-6qhwGlLvaAtbDP");
        dataSourceFactories.saveConfigModal();
        dataSourceFactories.verifySuccessfulTestConnection();
        dataSourceFactories.saveDataSource();
        var status = dataSourceFactories.getStatusOfDataSourceFromTable(dataSourceName);
        if (status.equalsIgnoreCase("Disabled")) {
            throw new RuntimeException("Data Source is Disabled");
        }
    }

    @Test(priority = 3, enabled = false)
    public void editDataSource() throws InterruptedException {
        dataSourceFactories.goToEditDataSource(dataSourceName);
        dataSourceName = dataSourceFactories.enterDataSourceNameAndDescription();
        dataSourceFactories.enterDataSourceUrl("https://login.microsoftonline.com");
        dataSourceFactories.openConfigModal();
        dataSourceFactories.enterTenantID("9440f0a4-3c93-44be-a379-740f10731bf6");
        dataSourceFactories.enterApplicationID("2007e8b1-efdf-4922-8c1d-c3a26c106714");
        dataSourceFactories.enterUserName("madmin@consiliodeveloper.onmicrosoft.com");
        dataSourceFactories.enterPassword("SLHT3st@");
        dataSourceFactories.enterClientSecret("Jzk8Q~u58Dgj1Nq31CyLe028coeI4vZguLkLFas4");
        dataSourceFactories.saveConfigModal();
        dataSourceFactories.verifySuccessfulTestConnection();
        dataSourceFactories.saveDataSource();
        var status = dataSourceFactories.getStatusOfDataSourceFromTable(dataSourceName);
        if (status.equalsIgnoreCase("Disabled")) {
            throw new RuntimeException("Data Source is Disabled");
        }
    }

    @Test(priority = 4, enabled = true)
    public void closeCase() throws InterruptedException {
//        caseFactories.closeCase("Salmon Nigiri ferret violet");
//        caseFactories.reopenCase("Salmon Nigiri ferret violet");
        caseFactories.deleteCase("Case Preservation Hold");
    }
}
