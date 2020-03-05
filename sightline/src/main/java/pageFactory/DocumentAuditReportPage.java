package pageFactory;

import java.util.concurrent.Callable;
import org.testng.Assert;
import automationLibrary.Driver;
import automationLibrary.Element;
import testScriptsSmoke.Input;

public class DocumentAuditReportPage {

    Driver driver;
    BaseClass bc;
  
    public Element getReport_DocAudit(){ return driver.FindElementByXPath("//a[contains(.,'Document Audit Report')]"); }
    public Element getDA_Selectsearch(){ return driver.FindElementByXPath("//a[contains(.,'My Saved Search')]"); }
    public Element getApplyBtn(){ return driver.FindElementById("btn_applychanges"); } 
    public Element getDA_Actions(int colno){ return driver.FindElementByXPath(".//*[@id='dtDocumentAuditData']//tbody/tr[1]/td["+colno+"]"); } 
    public Element getDA_SelectSearch_Expand(){ return driver.FindElementByXPath("//*[@href='#collapseSearchBy']"); } 
    public Element getDA_SelectActionstype(){ return driver.FindElementByXPath("//*[@id='chkCheckAllActionTypes']/following-sibling::i"); }
    public Element getDA_Actions_Expand(){ return driver.FindElementByXPath("//*[@id='actiontypeAuditreport']/a[2]"); }
     
      public DocumentAuditReportPage(Driver driver){

    	this.driver = driver;
    	bc = new BaseClass(driver);
        this.driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
        
     }
    
    public void ValidateDAreport(String searchname,String assignmentname) throws InterruptedException {
    	
    	 driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
         
      	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			getReport_DocAudit().Visible()  ;}}), Input.wait30); 
        	getReport_DocAudit().Click();
        	
        	getDA_SelectSearch_Expand().waitAndClick(10);
		
		  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				  getDA_Selectsearch().Visible()  ;}}), Input.wait30);		
		  getDA_Selectsearch().Click();
		   
		  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				  getApplyBtn().Visible()  ;}}), Input.wait30);	
	      getApplyBtn().Click();
	      Thread.sleep(2000);
	    
	     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    		 getDA_Actions(2).Visible()  ;}}), Input.wait30);
	     String actaction = getDA_Actions(2).getText();
	     System.out.println(actaction);
	     Assert.assertEquals("Assigned to Assignment", actaction);
	     
	     String actactionon = getDA_Actions(3).getText();
	     System.out.println(actactionon);
	     Assert.assertEquals(assignmentname, actactionon);
	   
	 }
    
     
}