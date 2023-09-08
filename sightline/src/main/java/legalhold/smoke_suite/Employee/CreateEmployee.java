package legalhold.smoke_suite.Employee;
import automationLibrary.CustomWebDriverWait;
import automationLibrary.Driver;
import automationLibrary.Element;
import legalhold.setup.BaseModule;
import legalhold.legalholdpagefactory.employee.EmployeeFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.text.SimpleDateFormat;


public class CreateEmployee extends BaseModule {
    public CreateEmployee(Driver driver) throws IOException {
        super("./src/main/java/legalhold/selectors/sl_slh_integration/employees.properties", driver);
    }

    public String CreateEmployeeManually() {

        //Click Manage Employee dropdown
        Element manageEmployeeDropdown = driver.FindElementById(locatorReader.getobjectLocator("manageEmployeeDropdown"));
        manageEmployeeDropdown.Click();

        //Click add employee manually
        Element addEmployeeManually = driver.FindElementById(locatorReader.getobjectLocator("btn_add_employee"));
        addEmployeeManually.Click();

        WebElement addForm = driver.getWebDriver().findElement(By.id(locatorReader.getobjectLocator("modal-add-employee")));
//        Element addForm = driver.FindElementById("modal-add-employee");
        String[] employeeAllFields = locatorReader.getArray("employeeFormFields");


        driver.waitForPageToBeReady();
//        Element FIELD =addForm.FindElementById("FirstName");


//        if(employeeField.waitAndFind(30))
//        {
//            employeeField.Click();
//        }
//
        int rangeId = 100000 - 100 + 1;
        int rangeMail = 999 - 100 + 1;
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String id = firstName + "_" + ((int) (Math.random() * rangeId));
        String emailAddress = firstName + "_" + lastName + ((int) (Math.random() * rangeMail)) + "@gmail.com";

        for (int i = 0; i < employeeAllFields.length; i++) {

            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd");
            String formattedDate = formatDate.format(faker.date().birthday());


            WebElement employeeField = addForm.findElement(By.id(employeeAllFields[i]));

            if (employeeAllFields[i].equals("EmployeeId")) {
                employeeField.sendKeys(id);
            } else if (employeeAllFields[i].equals("FirstName")) {
                employeeField.sendKeys(firstName);
            } else if (employeeAllFields[i].equals("LastName")) {
                employeeField.sendKeys(lastName);
            } else if (employeeAllFields[i].equals("EmailAddress")) {
                employeeField.sendKeys(emailAddress);
            } else if (employeeAllFields[i].contains("Date")) {
                employeeField.sendKeys(formattedDate);
            } else {
                employeeField = addForm.findElement(By.id(employeeAllFields[i]));
                employeeField.sendKeys(faker.lorem().word());
            }


        }

        WebElement AddEmployeeSubmit = driver.getWebDriver().findElement(By.id(locatorReader.getobjectLocator("add-employee-submit-btn")));
        AddEmployeeSubmit.click();
        return id;
    }


    public void verifyEmployeeCreation(String id) throws InterruptedException, IOException {

        driver.waitForPageToBeReady();


        Element paginationDropdown = driver.FindElementById(locatorReader.getobjectLocator("paginationDropdown"));
        WebDriverWait wait = new CustomWebDriverWait(driver.getWebDriver(), 30);

//        Thread.sleep(7000);


        EmployeeFactory empFactory = new EmployeeFactory(driver);
        empFactory.SearchEmployeeById(id);

////        boolean rowsVisible = dataTable.findElements(By.tagName("tr")).size() >0;

            Element firstColumn = driver.FindElementByCssSelector(locatorReader.getobjectLocator("TableFirstData"));
            String cellValue = firstColumn.getText();
            softAssert.assertEquals(id, cellValue);

    }
}



