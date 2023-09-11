package legalhold.legalholdpagefactory.employee;

import automationLibrary.Driver;
import automationLibrary.Element;
import legalhold.setup.BaseModule;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.io.IOException;

public class EmployeeFactory extends BaseModule {


    public EmployeeFactory(Driver driver) throws IOException {
        super("./src/main/java/legalhold/selectors/sl_slh_integration/employees.properties", driver);
    }


    public void SearchEmployeeById(String id) {



        try {

            Element EmployeeIdSearchBox = driver.FindElementByXPath(locatorReader.getobjectLocator("employee-id-search-box"));
            EmployeeIdSearchBox.Click();
            EmployeeIdSearchBox.SendKeys(id);


//        boolean rowsVisible = dataTable.findElements(By.tagName("tr")).size() >0;

            String expected_text = "Showing 1 to 1 of 1 entries";
            wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("paginationText")), expected_text));
        }catch (Exception e){
            System.out.println("Expected Pagination Text Not Found" + e.getMessage());
        }
    }

}