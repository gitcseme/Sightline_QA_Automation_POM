package legalhold.sprintwork.Sprint39.testcases;

import automationLibrary.Driver;
import automationLibrary.Element;
import legalhold.legalholdpagefactory.cases.CaseFactories;
import legalhold.legalholdpagefactory.cases.CustodianFactories;
import legalhold.setup.BaseModule;
import legalhold.utilities.parse_locators.LocatorReader;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;

public class Sprint39_ReleaseWithCom extends BaseModule {
    CaseFactories caseFactories;
    CustodianFactories custodianFactories;
    LocatorReader reader;
    LocatorReader seriesReader;

    public Sprint39_ReleaseWithCom(Driver driver) throws IOException {
        super("./src/main/java/legalhold/selectors/cases/casepage.properties", driver);
        caseFactories = new CaseFactories(driver);
        custodianFactories = new CustodianFactories(driver);
        reader = new LocatorReader("./src/main/java/legalhold/selectors/cases/manage_case/custodian/custodian.properties");
        seriesReader = new LocatorReader("src/main/java/legalhold/selectors/cases/manage_case/communication/communication_page.properties");
    }


    public void releaseWithCommunication(String empId, String seriesName) throws InterruptedException {

        custodianFactories.searchByCustodianId(empId);


        int rowCount = custodianFactories.getDataTableRowCount();

        if(rowCount > 1)
        {
            Element selectAllCheckbox = driver.FindElementByXPath(reader.getobjectLocator("selectAllCheckBox"));
            selectAllCheckbox.waitAndClick(30);
        }else if(rowCount==1)
        {
            Element actionMenu = driver.FindElementByXPath(reader.getobjectLocator("actionMenu"));
            actionMenu.waitAndClick(30);
        }

    }
}
