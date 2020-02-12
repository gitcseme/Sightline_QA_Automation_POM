package pageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import junit.framework.Assert;
import junit.framework.AssertionFailedError;
import testScriptsSmoke.Input;

public class DocExplorerPage {

    Driver driver;
    Element element;
    BaseClass bc;
    SoftAssert assertion;
    SessionSearch search;
    DocListPage doclist;
    
    //Filters
    public Element getIngestionNameFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'IngestionName')]"); }
    public Element getCustodianFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'CustodianName')]"); }
    public Element getAllCustodianFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'AllCustodians')]"); }
    public Element getGetDocFIleTypeFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'DocFileType')]"); }
    public Element getMasterDateFiler(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'MasterDate')]"); }
    public Element getEmailAuthNameFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'EmailAuthorName')]"); }
    public Element getEmailRecNameFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'EmailRecipientNames')]"); }
    public Element getEmailAuthDomainFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'EmailAuthorDomain')]"); }
    public Element getEmailReciepientDomainFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'EmailRecipientDomains')]"); }
    public Element getTagFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'Tags')]"); }
    public Element getFolderFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'Folders')]"); }
    public Element getAssignmentFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'Assignments')]"); }
    public Element getCommentsFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'Comments')]"); }
    public Element getDocumentList_info(){ return driver.FindElementById("dtDocumentList_info"); }
    public Element getDocID(){ return driver.FindElementByXPath(".//*[@id='dtDocumentList']//tr[1]/td[2]/div"); }
    public Element getDocIDSearchName(){ return driver.FindElementByXPath("//*[@class='dataTables_scrollHead']//tr[2]/th[2]/input"); }
    public Element getCusName(){ return driver.FindElementByXPath(".//*[@id='dtDocumentList']//tr[1]/td[4]/div"); }
    public Element getCustodianSearchName(){ return driver.FindElementByXPath(".//*[@class='dataTables_scrollHead']//tr[2]/th[4]/input"); }
    public Element getMasterDate(){ return driver.FindElementByXPath(".//*[@id='dtDocumentList']//tr[1]/td[7]/div"); }
    public Element getMasterDateSearchName(){ return driver.FindElementByXPath(".//*[@class='dataTables_scrollHead']//tr[2]/th[7]/input"); }
    public Element getActiveFilter(){ return driver.FindElementByXPath(".//*[@id='activeFilters']/li"); }
    public Element getUpdateFilter(){ return driver.FindElementByXPath("(.//*[contains(text(),'Update Filter')])[2]"); }
    
    
    
    
     public DocExplorerPage(Driver driver){

        this.driver = driver;
        bc = new BaseClass(driver);
        this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
        driver.waitForPageToBeReady();
        assertion = new SoftAssert();
        doclist = new DocListPage(driver);
        search = new SessionSearch(driver);

    }

    public void HeaderFilter(String Filtertype) throws InterruptedException {
    
    	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
    	switch (Filtertype) {
    	
    	case "DocID":
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getDocID().Visible() ;}}), Input.wait30);
        	String DocId = getDocID().getText();
        	System.out.println(DocId);
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getDocIDSearchName().Visible() ;}}), Input.wait30);
        		getDocIDSearchName().SendKeys(DocId);
        		doclist.getApplyFilter().waitAndClick(10);
            	Thread.sleep(2000);
            	validateCount("Showing 1 to 1 of 1 entries");
            	getDocIDSearchName().Clear();
        	   break;
        	
    	case "Custodian":
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getCusName().Visible() ;}}), Input.wait30);
        	String Cusname = getCusName().getText();
        	System.out.println(Cusname);
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getCustodianSearchName().Visible() ;}}), Input.wait30);
        	getCustodianSearchName().SendKeys(Cusname);
        	doclist.getApplyFilter().waitAndClick(10);
        	Thread.sleep(2000);
        	validateCount("Showing 1 to 50 of 1,134 entries");
        	getCustodianSearchName().Clear();
        	break;
        	
    	case "MasterDate":
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getMasterDate().Visible() ;}}), Input.wait30);
        	String date = getMasterDate().getText();
        	System.out.println(date);
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getMasterDateSearchName().Visible() ;}}), Input.wait30);
        	getMasterDateSearchName().SendKeys(date);
            doclist.getApplyFilter().waitAndClick(10);
            Thread.sleep(2000);
        	validateCount("Showing 1 to 50 of 95 entries");
        	break;
    	
    	}
    	
     	
   }
    
    public void TagWithMasterDateFilter(String dataforfilter) throws InterruptedException {
        
    	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getTagFilter().Visible() ;}}), Input.wait60);
    	getTagFilter().waitAndClick(10);
    	
    	doclist.exclude(dataforfilter);
    	Thread.sleep(2000);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMasterDateFiler().Visible() ;}}), Input.wait30);
    	getMasterDateFiler().waitAndClick(10);
    	
    	doclist.dateFilter("before",  "2010/01/01", null);
    	doclist.getApplyFilter().waitAndClick(10);
    	
    	validateCount("Showing 1 to 50 of 1,202 entries");
   }
    
    
    public void MasterDateFilter() throws InterruptedException {
      
    	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
    	
    	doclist.dateFilter("Between", "2001/01/01", "2020/01/01");
    	Thread.sleep(2000);
    	
    	doclist.getApplyFilter().waitAndClick(10);
    	
    	validateCount("Showing 1 to 50 of 302 entries");
   }
    
 public void TagFilter(String data1,String data2) throws InterruptedException {
      
    	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getTagFilter().Visible() ;}}), Input.wait60);
    	getTagFilter().waitAndClick(10);
    	
    	doclist.include(data1);
    	Thread.sleep(2000);
    	
    	doclist.getApplyFilter().waitAndClick(10);
    	
    	validateCount("Showing 1 to 48 of 48 entries");
    	
	    UpdateFilter(data2);
    	
        doclist.getApplyFilter().waitAndClick(10);
    	
    	validateCount("Showing 1 to 50 of 1,202 entries");
   }
    
  public void CustodianFilter(String data1,String data2,String functionality) throws InterruptedException {
     
    	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getCustodianFilter().Visible() ;}}), Input.wait60);
    	getCustodianFilter().waitAndClick(10);
    	
    	if(functionality.equalsIgnoreCase("include"))
	  	{
    	doclist.include(data1);
    	Thread.sleep(2000);
    	
    	doclist.getApplyFilter().waitAndClick(10);
    	
    	validateCount("Showing 1 to 50 of 1,134 entries");
    	
    	UpdateFilter(data2);
    	
        doclist.getApplyFilter().waitAndClick(10);
    	
    	validateCount("Showing 1 to 50 of 1,136 entries");
	  	}
    	if(functionality.equalsIgnoreCase("exclude"))
	  	{
    	doclist.exclude(data1);
    	Thread.sleep(2000);
    	
    	doclist.getApplyFilter().waitAndClick(10);
    	
    	validateCount("Showing 1 to 50 of 68 entries");
    	
    	UpdateFilter(data2);
    	
        doclist.getApplyFilter().waitAndClick(10);
    	
    	validateCount("Showing 1 to 50 of 66 entries");
	  	}
    	
   }
    
  public void DocFileTypeFilter(String data1,String data2) throws InterruptedException {
   
  	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getCustodianFilter().Visible() ;}}), Input.wait60);
  	getCustodianFilter().waitAndClick(10);
  	
  	doclist.include(data1);
  	Thread.sleep(2000);
  	
  	doclist.getApplyFilter().waitAndClick(10);
  	
  	validateCount("Showing 1 to 50 of 813 entries");
  	
  	UpdateFilter(data2);
  	
    doclist.getApplyFilter().waitAndClick(10);
  	
  	validateCount("Showing 1 to 50 of 817 entries");
 }
  
  public void EmailRecipientNameFilter(String data1,String data2) throws InterruptedException {
	   
	  	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
	  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	  			getEmailRecNameFilter().Visible() ;}}), Input.wait60);
	  	getEmailRecNameFilter().waitAndClick(10);
	  	
	  	doclist.include(data1);
	  	Thread.sleep(2000);
	  	
	  	doclist.getApplyFilter().waitAndClick(10);
	  	
	  	validateCount("Showing 1 to 3 of 3 entries");
	  	
	  	UpdateFilter(data2);
	  	
	    doclist.getApplyFilter().waitAndClick(10);
	  	
	  	validateCount("Showing 1 to 13 of 13 entries");
	 }
  
  public void EmailAuthorNameFilter(String data1,String data2) throws InterruptedException {
	   
	  	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
	  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	  			getEmailAuthNameFilter().Visible() ;}}), Input.wait60);
	  	getEmailAuthNameFilter().waitAndClick(10);
	  	
	  	doclist.exclude(data1);
	  	Thread.sleep(2000);
	  	
	  	doclist.getApplyFilter().waitAndClick(10);
	  	
	  	validateCount("Showing 1 to 1 of 1 entries");
	  	
	  	UpdateFilter(data2);
	  	
	    doclist.getApplyFilter().waitAndClick(10);
	  	
	  	validateCount("Showing 1 to 3 of 3 entries");
	 }
  
  public void EmailAuthorDomainFilter(String data1,String data2) throws InterruptedException {
	   
	  	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
	  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	  			getEmailAuthDomainFilter().Visible() ;}}), Input.wait60);
	  	getEmailAuthDomainFilter().waitAndClick(10);
	  	
	  	doclist.exclude(data1);
	  	Thread.sleep(2000);
	  	
	  	doclist.getApplyFilter().waitAndClick(10);
	  	
	  	validateCount("Showing 1 to 6 of 6 entries");
	  	
	  	UpdateFilter(data2);
	  	
	    doclist.getApplyFilter().waitAndClick(10);
	  	
	  	validateCount("Showing 1 to 10 of 10 entries");
	 }
  
  public void AssignmentFilter(String assgnm1,String assgnm2,String functionality) throws InterruptedException {
	   
	  	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
	  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	  			getAssignmentFilter().Visible() ;}}), Input.wait30);
	  	getAssignmentFilter().waitAndClick(10);
	  	
	  	if(functionality.equalsIgnoreCase("include"))
	  	{
	  	doclist.include(assgnm1);
	  	Thread.sleep(2000);
	  	
	  	doclist.getApplyFilter().waitAndClick(10);
	  	
	  	validateCount("Showing 1 to 10 of 10 entries");
	  	
	  	UpdateFilter(assgnm2);
	  	
	    doclist.getApplyFilter().waitAndClick(10);
	  	
	  	validateCount("Showing 1 to 48 of 48 entries");
	  	}
		if(functionality.equalsIgnoreCase("exclude"))
	  	{
	  	doclist.exclude(assgnm1);
	  	Thread.sleep(2000);
	  	
	  	doclist.getApplyFilter().waitAndClick(10);
	  	
	  	validateCount("Showing 1 to 50 of 1,192 entries");
	  	
	  	UpdateFilter(assgnm2);
	  	
	    doclist.getApplyFilter().waitAndClick(10);
	  	
	  	validateCount("Showing 1 to 50 of 1,154 entries");
	  	}
	  	
	 }
  
    
    public void validateCount(String counts) {
 	   driver.scrollingToBottomofAPage();
 	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     		   !getDocumentList_info().getText().isEmpty()  ;}}),Input.wait60);
 	   //to make sure that not reading previous results and wait for page mask to complete,
 	   //simply try to click on result text!
 	   driver.scrollingToBottomofAPage();
 	  getDocumentList_info().waitAndClick(30); //works only when results updates!!
        
 	   //Now validate results
 	   Assert.assertTrue(getDocumentList_info().getText().toString().equalsIgnoreCase(counts));
 	   
 }
  
    public void UpdateFilter(String data) {
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getActiveFilter().Visible()  ;}}), Input.wait30);
   	getActiveFilter().Click();
   	doclist.getSearchTextArea().SendKeys(data);
   	try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	doclist.getSearchTextArea().SendKeysNoClear(""+Keys.ENTER);
   	getUpdateFilter().waitAndClick(10);
   	doclist.getAddToFilter().Click();
   
	}
 
 
 }