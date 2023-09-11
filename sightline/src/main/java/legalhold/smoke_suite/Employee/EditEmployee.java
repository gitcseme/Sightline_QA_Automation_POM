package legalhold.smoke_suite.Employee;

import automationLibrary.CustomWebDriverWait;
import automationLibrary.Driver;
import automationLibrary.Element;
import legalhold.BaseModule;
import legalhold.legalholdpagefactory.employee.EmployeeFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.text.SimpleDateFormat;

public class EditEmployee extends BaseModule {
    public EditEmployee(Driver driver) throws IOException {
        super("./src/main/java/legalhold/selectors/sl_slh_integration/employees.properties", driver);
    }

    public String EditCreatedEmployee(String employeeID) throws IOException, InterruptedException {

        WebDriverWait wait = new CustomWebDriverWait(driver.getWebDriver(), 30);

//        Thread.sleep(7000);

        System.out.println(employeeID);

        EmployeeFactory empFactory = new EmployeeFactory(driver);
        empFactory.SearchEmployeeById(employeeID);

        Element editButton = driver.FindElementByCssSelector("img[title='Edit Employee']");
        editButton.Click();

        WebElement editForm = driver.getWebDriver().findElement(By.id(locatorReader.getobjectLocator("modal-edit-employee")));

        String[] employeeAllFields = locatorReader.getArray("editEmployeeFormFields");


        driver.waitForPageToBeReady();

        int rangeId = 100000 - 100 + 1;
        int rangeMail = 999 - 100 + 1;
        String firstName = faker.name().firstName();
        String lastName = faker.name().lastName();
        String id = firstName + "_" + ((int) (Math.random() * rangeId));
        String emailAddress = firstName + "_" + lastName + ((int) (Math.random() * rangeMail)) + "@gmail.com";

        for (int i = 0; i < employeeAllFields.length; i++) {


            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd");
            String formattedDate = formatDate.format(faker.date().birthday());


            WebElement employeeField = editForm.findElement(By.id(employeeAllFields[i]));
            employeeField.clear();


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
                employeeField = editForm.findElement(By.id(employeeAllFields[i]));
                employeeField.sendKeys(faker.lorem().word());
            }


        }

        WebElement EditEmployeeSubmit = driver.getWebDriver().findElement(By.id(locatorReader.getobjectLocator("edit-employee-submit-btn")));
        EditEmployeeSubmit.click();
        driver.waitForPageToBeReady();
        Thread.sleep(5000);
        return id;
    }
}