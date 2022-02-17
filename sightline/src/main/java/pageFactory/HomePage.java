package pageFactory;

import java.util.concurrent.Callable;
import org.openqa.selenium.By;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class HomePage {

    Driver driver;
    Element element;
    BaseClass base;
    SoftAssert assertion;
    SessionSearch search;
    DocListPage doclist;
    
   
    //Menu and sub menus 
    public Element getManageMenu(){ return driver.FindElementByXPath("//a/label[contains(text(),'Manage')]"); }
    public Element getTagsPage(){ return driver.FindElementByName("Tags"); }
    public Element getAssignmentsPage(){ return driver.FindElementByName("Assignments"); }
    public Element getKeywordsPage(){ return driver.FindElementByName("Keywords"); }
    public Element getCommentsPage(){ return driver.FindElementByName("Comments"); }
    public Element getRedactionPage(){ return driver.FindElementByName("Redaction"); }
    public Element getUsersPage(){ return driver.FindElementByName("Users"); }
    public Element getClassificationPage(){ return driver.FindElementByName("Classification"); }
    public Element getCodingPage(){ return driver.FindElementByName("Coding"); }
    public Element getAnnotationPage(){ return driver.FindElementByName("Annotation"); }
    public Element getSchedulesPage(){ return driver.FindElementByName("Schedules"); }
    public Element getWorkFlowPage(){ return driver.FindElementByName("WorkFlow"); }
    
    public Element getSearchMenu(){ return driver.FindElementByXPath("//a/label[contains(text(),'Search')]"); }
    public Element getIngestionMenu(){ return driver.FindElementByXPath("//a/label[contains(text(),'Ingestion')]"); }
    public Element getReportsMenu(){ return driver.FindElementByXPath("//a/label[contains(text(),'Reports')]"); }
    public Element getProductionsMenu(){ return driver.FindElementByXPath("//a/label[contains(text(),'Productions')]"); }
    public Element getBatchPrintMenu(){ return driver.FindElementByXPath("//a/label[contains(text(),'Batch Print')]"); }
    public Element getCategorizationMenu(){ return driver.FindElementByXPath("//a/label[contains(text(),'Categorize')]"); }
    
    //-----------------
    //Reviewer
    public ElementCollection getAssignmentsList(){ return driver.FindElementsByXPath("//strong[contains(text(),'')]"); }
    
    //Added by Gopinath - 17/02/2022
  	public Element getDashBoardPage() {
      	return driver.FindElementByXPath("//a[@name='Dashboard']/ancestor::li");
      }
      public Element getReviewManagerPageTitle() {
      	return driver.FindElementByXPath("//h1[@class='page-title' and contains(text(),'Review Manager Dashboard')]");
      }
      
 
     
    
    public HomePage(Driver driver){

        this.driver = driver; 
        base = new BaseClass(driver);
        driver.waitForPageToBeReady();
        assertion = new SoftAssert();
        doclist = new DocListPage(driver);
        search = new SessionSearch(driver);
        //This initElements method will create all WebElements
        //PageFactory.initElements(driver.getWebDriver(), this);

    }
    
 
      
  	
  	/**
  	 * @author Gopinath
  	 * @Description:method to verify dashBoard page is displayed as default menu for Review manager
  	 */
  	public void verifyDashboardPageAsDefaultMenu() {
  		try {
  			base.waitForElement(getDashBoardPage());
			if (getReviewManagerPageTitle().isDisplayed()) {
				if (getDashBoardPage().GetAttribute("class").contains("active")) {
					base.passedStep("DashBoard page is displayed as default menu for Review manager");

				}else {
					base.failedStep("DashBoard page is displayed as default menu for Review manager");
				}
			}else {
				base.stepInfo("Review manager dashboard is not displayed");
			}
  		} catch (Exception e) {
  			e.printStackTrace();
  			base.failedStep("Exception occured while dashBoard page is displayed as default menu for Review manager" + e.getMessage());
  		}

  	}

    }