package legalhold.legalholdpagefactory;

import automationLibrary.Driver;
import legalhold.setup.BaseModule;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.List;

public class Module_Navigation extends BaseModule {


    List<WebElement> sidebarElements;

    public Module_Navigation(Driver driver) throws IOException {
        super("./src/main/java/legalhold/selectors/sl_slh_integration/navigation.properties", driver);



    }

    public void GetNavList(){
        sidebarElements = driver.getWebDriver().findElements(By.className(locatorReader.getobjectLocator("ModuleTabs")));
    }


    public void navigateToCaseTAB() {

        GetNavList();
        sidebarElements.get(0).click();
        driver.waitForPageToBeReady();
    }

    public void navigateToEmployeeTAB() {

        GetNavList();
        sidebarElements.get(1).click();
        driver.waitForPageToBeReady();


    }
    public void navigateToTemplatesTAB() {

        GetNavList();
        sidebarElements.get(2).click();
        driver.waitForPageToBeReady();


    }

    public void navigateToMailboxTAB() {

        GetNavList();
        sidebarElements.get(3).click();
        driver.waitForPageToBeReady();


    }

    public void navigateToReportsTAB() {

        GetNavList();
        sidebarElements.get(4).click();
        driver.waitForPageToBeReady();


    }

    public void navigateToAuditTrailTAB() {

        GetNavList();
        sidebarElements.get(5).click();
        driver.waitForPageToBeReady();


    }

    public void navigateToGlobalNoticeTAB() {

        GetNavList();
        sidebarElements.get(6).click();
        driver.waitForPageToBeReady();


    }

    public void navigateToDomainSetupTAB() {

        GetNavList();
        sidebarElements.get(7).click();
        driver.waitForPageToBeReady();


    }



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


