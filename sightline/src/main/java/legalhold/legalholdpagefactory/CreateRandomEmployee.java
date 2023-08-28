package legalhold.legalholdpagefactory;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import com.github.javafaker.Faker;
import legalhold.BaseModule;
import legalhold.utilities.parse_locators.LocatorReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

public class CreateRandomEmployee extends BaseModule {
    public CreateRandomEmployee(Driver driver) throws IOException {
        super("./src/main/java/legalhold/selectors/sl_slh_integration/employees.properties", driver);
    }

    public void navigateToEmployeeTAB() {

        List<WebElement> sidebarElements = driver.getWebDriver().findElements(By.className("menu-link"));
        sidebarElements.get(1).click();
        driver.waitForPageToBeReady();

//        ElementCollection sidebarElements = driver.FindElementsByClassName("menu-link");
//        sidebarElements.getElementByIndex(1).Click();

    }


    public void CreateEmployeeManually() {

        //Click Manage Employee dropdown
        Element manageEmployeeDropdown = driver.FindElementById("manageEmployeeDropdown");
        manageEmployeeDropdown.Click();

        //Click add employee manually
        Element addEmployeeManually = driver.FindElementById("modal-add-employee-btn");
        addEmployeeManually.Click();

        WebElement addForm = driver.getWebDriver().findElement(By.id("modal-add-employee"));
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
        String id = firstName+"_"+((int)(Math.random()*rangeId));
        String emailAddress = firstName+"_"+lastName+((int)(Math.random()*rangeMail))+"@gmail.com";

        for(int i=0;i<employeeAllFields.length;i++)
        {

            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd");
            String formattedDate = formatDate.format(faker.date().birthday());


            WebElement employeeField = addForm.findElement(By.id(employeeAllFields[i]));

            if(employeeAllFields[i].equals("EmployeeId"))
            {
                employeeField.sendKeys(id);
            } else if(employeeAllFields[i].equals("FirstName")) {
                employeeField.sendKeys(firstName);
            }else if(employeeAllFields[i].equals("LastName")) {
                employeeField.sendKeys(lastName);
            } else if(employeeAllFields[i].equals("EmailAddress")) {
                employeeField.sendKeys(emailAddress);
            }else if(employeeAllFields[i].contains("Date")) {
                employeeField.sendKeys(formattedDate);
            }  else {
                employeeField = addForm.findElement(By.id(employeeAllFields[i]));
                employeeField.sendKeys(faker.lorem().word());
            }



        }

        WebElement AddEmployeeSubmit = driver.getWebDriver().findElement(By.id("add-employee-submit-btn"));
        AddEmployeeSubmit.click();
    }

//    public void verifyEmployeeCreation() throws InterruptedException {
//
//        WebElement EmployeeIdSearchBox = driver.getWebDriver().findElement(By.cssSelector("input[placeholder='Search Employee ID']"));
//        EmployeeIdSearchBox.clear();
//        driver.waitForPageToBeReady();
//        WebElement paginationDropdown = driver.getWebDriver().findElement(By.id("id-employee_length"));
//        Thread.sleep(2000);
//        EmployeeIdSearchBox.click();
//        EmployeeIdSearchBox.sendKeys("just_emp");
//        EmployeeIdSearchBox.getText();
//    }

//    public void test(){
//        int range = 100000 - 100 + 1;
//        String id = faker.name().firstName()+"_"+((int)(Math.random()*range));
//        System.out.println(id);
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//        String formattedDate = sdf.format(faker.date().birthday());
//        System.out.println(formattedDate);
//    }

}


