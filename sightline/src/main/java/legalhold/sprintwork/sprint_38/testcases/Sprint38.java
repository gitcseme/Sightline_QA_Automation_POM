package legalhold.sprintwork.sprint_38.testcases;

import automationLibrary.Driver;
import legalhold.legalholdpagefactory.cases.CaseFactories;
import legalhold.setup.BaseModule;

import java.io.IOException;
import java.util.HashMap;

public class Sprint38 extends BaseModule {
    CaseFactories caseFactories;

    public Sprint38(Driver driver) throws IOException {
        super("src/main/java/legalhold/selectors/cases/casepage.properties", driver);
        caseFactories = new CaseFactories(driver);
    }

    public HashMap getPreservationsColumn_X_Y_Value(String caseName) throws InterruptedException {
        HashMap<String, Integer> countMap = new HashMap<>();
        int preservationsColumnIndex = caseFactories.getColumnIndexFromDataTable("Preservations");
        Thread.sleep(3500);
        caseFactories.searchCaseByName(caseName);
        String preservation_X_And_Y_Data = driver.FindElementByXPath("//table[@id='id-CaseTable']/tbody/tr[1]/td[" + preservationsColumnIndex + "]").getText();
        countMap.put("valueX",Integer.parseInt(preservation_X_And_Y_Data.split("/")[0].trim()));
        countMap.put("valueY",Integer.parseInt(preservation_X_And_Y_Data.split("/")[1].trim()));
        driver.FindElementById(locatorReader.getobjectLocator("btnClearFilter")).waitAndClick(30);
        return countMap;
    }
}
