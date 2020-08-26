package pageFactory;

import java.util.concurrent.Callable;
import org.testng.Assert;
import automationLibrary.Driver;
import automationLibrary.Element;
import testScriptsSmoke.Input;

public class SearchTermReportPage {

    Driver driver;
    BaseClass bc;
  
    public Element getReport_SearchTerm(){ return driver.FindElementByXPath(".//*[@id='collapseOne']//a[contains(.,'Search Term Report')]"); }
    public Element getST_Selectmysearches(){ return driver.FindElementByXPath("//a[contains(.,'My Saved Search')]"); }
    public Element getApplyBtn(){ return driver.FindElementById("btn_applychanges"); } 
    public Element getST_SummaryPage(){ return driver.FindElementById("tblCustomSummary"); } 
    public Element getST_SelectHits(){ return driver.FindElementById("chkHits"); } 
    public Element getST_Actionsbutton(){ return driver.FindElementById("tallyactionbtn"); }
    public Element getST_Actions_QB(){ return driver.FindElementByXPath("//a[contains(.,'Quick Batch')]"); }
    //public Element getST_SelectHits(){ return driver.FindElementById("chkHits"); }
    public Element getST_Selectsearch(String searchname){ return driver.FindElementByXPath("//a[contains(.,'"+searchname+"']/i[1]"); }
    
    
      public SearchTermReportPage(Driver driver){

    	this.driver = driver;
    	bc = new BaseClass(driver);
        this.driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
        
     }
    
    public void ValidateSearchTermreport(String Searchname) throws InterruptedException {
    	
    	 driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
         
      	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
       			getReport_SearchTerm().Visible()  ;}}), Input.wait30); 
         getReport_SearchTerm().Click();
		
		  getApplyBtn().waitAndClick(20);
    	  
    	  bc.VerifyErrorMessage("Please select at least one search.");
    	  bc.CloseSuccessMsgpopup();
		  
		  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				  getST_Selectmysearches().Visible()  ;}}), Input.wait30);		
		  getST_Selectmysearches().Click();
		   
		  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				  getApplyBtn().Visible()  ;}}), Input.wait30);	
	      getApplyBtn().Click();
	      Thread.sleep(2000);
	    
	     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    		 getST_SummaryPage().Visible()  ;}}), Input.wait60);
	   
		 Assert.assertTrue(getST_SummaryPage().Displayed());
		 System.out.println("test passed");
	  }
    
    public void TermtoQuickbatch() throws InterruptedException {
    	 
	    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	         			getST_SelectHits().Visible()  ;}}), Input.wait30);
	    getST_SelectHits().Click();
	         	
	     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getST_Actionsbutton().Visible()  ;}}), Input.wait30); 
	     getST_Actionsbutton().waitAndClick(20);
	     Thread.sleep(2000);
	   	 
	   	getST_Actions_QB().waitAndClick(20);
	   	 
	    }

     
}