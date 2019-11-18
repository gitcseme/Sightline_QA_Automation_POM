package pageFactory;

import static org.testng.Assert.assertTrue;

import java.util.concurrent.Callable;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class ReportsPage {

    Driver driver;
  
    public Element getTallyReport(){ return driver.FindElementByXPath(".//*[@id='collapseOne']//a[contains(.,'Tally Report')]"); }
    public Element getCustomDocumentDataReport(){ return driver.FindElementByXPath(".//*[@id='collapseOne']//a[contains(.,'Custom Document Data Report')]"); }
    
    
    public ReportsPage(Driver driver){

        this.driver = driver;
        this.driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
        //This initElements method will create all WebElements
        //PageFactory.initElements(driver.getWebDriver(), this);

    }
    
    public void TallyReportButton(){
		ReportsPage report = new ReportsPage(driver);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getTallyReport().Visible()  ;}}),Input.wait60); 
		report.getTallyReport().Click();
    }

   
}