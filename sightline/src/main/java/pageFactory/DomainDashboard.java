package pageFactory;

import java.util.concurrent.Callable;
import org.testng.Assert;
import automationLibrary.Driver;
import automationLibrary.Element;
import testScriptsSmoke.Input;

public class DomainDashboard {
	 Driver driver;
	 BaseClass bc;
	 
	
	 
	 public Element getDataRefresh_info(){ return driver.FindElementByXPath("//*[@id='domainDashboardRefreshTime']"); }
	 public Element getAddComment1(){ return driver.FindElementById("1_textarea"); }
	 public Element getSaveDoc(){ return driver.FindElementById("Save"); }
	 public Element getprojectlink_GotoProject(){ return driver.FindElementByXPath("//*[@id='dropdownMenuLink' and contains(text(),"+Input.projectName+")]/following-sibling::ul//li[1]"); }
	 public Element getprojectnamelink(String projectname){ return driver.FindElementByXPath("(//*[@id='taskbasic']//a[contains(text(),'"+Input.projectName+"')])[1]"); }
	 public Element getdashboardtitle(){ return driver.FindElementById("domainDashboardTitle"); }
	 public Element getBacktodomaindashboard(){ return driver.FindElementByXPath("//a[@name='Back to the Domain Dashboard']"); }
	 
	 public DomainDashboard(Driver driver){

	        this.driver = driver;
	        this.driver.getWebDriver().get(Input.url+ "DomainDashboard/DomainDashboard");
	        bc = new BaseClass(driver);
	        //This initElements method will create all WebElements
	        //PageFactory.initElements(driver.getWebDriver(), this);
	  
	    }
	 
	 public void ImpersonatetoPAforDomainProject()
	 {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getprojectnamelink(Input.projectName).Visible()  ;}}), Input.wait60); 
			getprojectnamelink(Input.projectName).Click();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 	
			bc.selectproject();
		 
			getBacktodomaindashboard().waitAndClick(10);
			
		  Assert.assertTrue(getdashboardtitle().Displayed());
		 
	 }
	    
}
