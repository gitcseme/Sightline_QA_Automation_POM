package pageFactory;

import java.text.ParseException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;

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
    public Element getDocExp_IngestionNameFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'IngestionName')]"); }
    public Element getDocExp_CustodianFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'CustodianName')]"); }
    public Element getDocExp_AllCustodianFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'AllCustodians')]"); }
    public Element getDocExp_GetDocFIleTypeFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'DocFileType')]"); }
    public Element getDocExp_MasterDateFiler(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'MasterDate')]"); }
    public Element getDocExp_EmailAuthNameFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'EmailAuthorName')]"); }
    public Element getDocExp_EmailRecNameFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'EmailRecipientNames')]"); }
    public Element getDocExp_EmailAuthDomainFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'EmailAuthorDomain')]"); }
    public Element getDocExp_EmailReciepientDomainFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'EmailRecipientDomains')]"); }
    public Element getDocExp_TagFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'Tags')]"); }
    public Element getDocExp_FolderFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'Folders')]"); }
    public Element getDocExp_AssignmentFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'Assignments')]"); }
    public Element getDocExp_CommentsFilter(){ return driver.FindElementByXPath("//*[@id='optionFilters']/li[contains(text(),'Comments')]"); }
    public Element getDocExp_DocumentList_info(){ return driver.FindElementById("dtDocumentList_info"); }
    public Element getDocExp_DocID(){ return driver.FindElementByXPath(".//*[@id='dtDocumentList']//tr[1]/td[2]/div"); }
    public Element getDocExp_Docfiletype(){ return driver.FindElementByXPath(".//*[@id='dtDocumentList']//tr[1]/td[5]/div"); }
    
    public Element getDocExp_DocIDSearchName(){ return driver.FindElementByXPath("//*[@class='dataTables_scrollHead']//tr[2]/th[2]/input"); }
    public Element getDocExp_DocFiletypeSearchName(){ return driver.FindElementByXPath("//*[@class='dataTables_scrollHead']//tr[2]/th[5]/input"); }
    public Element getDocExp_CusName(){ return driver.FindElementByXPath(".//*[@id='dtDocumentList']//tr[1]/td[4]/div"); }
    public Element getDocExp_CustodianSearchName(){ return driver.FindElementByXPath(".//*[@class='dataTables_scrollHead']//tr[2]/th[4]/input"); }
    public Element getDocExp_MasterDate(){ return driver.FindElementByXPath(".//*[@id='dtDocumentList']//tr[1]/td[7]/div"); }
    public Element getDocExp_MasterDateSearchName(){ return driver.FindElementByXPath(".//*[@class='dataTables_scrollHead']//tr[2]/th[7]/input"); }
    public Element getDocExp_ActiveFilter(){ return driver.FindElementByXPath(".//*[@id='activeFilters']/li"); }
    public Element getDocExp_UpdateFilter(){ return driver.FindElementByXPath("(.//*[contains(text(),'Update Filter')])[2]"); }
    public Element getDocExp_SetFromMasterDate(){ return driver.FindElementByXPath("(//*[contains(@id,'MasterDate-1-DOCEXPLORER')])[1]"); }
    public Element getDocExpt_SetToMasterDate(){ return driver.FindElementByXPath("(//*[@id='MasterDate-2-DOCEXPLORER'])[1]"); }
    public Element getDocExp_SelectAllDocs(){ return driver.FindElementByXPath("(//*[@id='selectAllDocuments']/following-sibling::i)[1]"); }
    
    public Element getDocExp_actionButton(){ return driver.FindElementById("idDocExplorerActions"); }
    public Element getDocExp_action_quickbatch(){ return driver.FindElementById("idQuickAssign"); }
    public Element getDocViewAction(){ return driver.FindElementById("idViewInDocView"); }
    public Element getDocListAction(){ return driver.FindElementById("idViewInDocList"); }
    
    
    
     public DocExplorerPage(Driver driver){

        this.driver = driver;
        bc = new BaseClass(driver);
        this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
        driver.waitForPageToBeReady();
        assertion = new SoftAssert();
        doclist = new DocListPage(driver);
        search = new SessionSearch(driver);

    }

    public void HeaderFilter(String Filtertype) throws InterruptedException, ParseException {
    
    	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
    	switch (Filtertype) {
    	
    	case "DocID":
    		bc.stepInfo("Test case Id: RPMXCON-54658 - HeaderDocID");
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getDocExp_DocID().Visible() ;}}), Input.wait30);
    		bc.stepInfo("*****Select DocID*****");
        	String DocId = getDocExp_DocID().getText();
        	System.out.println(DocId);
        	UtilityLog.info(DocId);
        	bc.passedStep("*****DocID HeaderFilter Selected*****");
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getDocExp_DocIDSearchName().Visible() ;}}), Input.wait30);
        	     bc.stepInfo("*****Enter DocID*****");
        		getDocExp_DocIDSearchName().SendKeys(DocId);
        		doclist.getApplyFilter().waitAndClick(10);
            	Thread.sleep(2000);
            	validateCount("Showing 1 to 1 of 1 entries");
            	getDocExp_DocIDSearchName().Clear();
            	bc.passedStep("*****DocID HeaderFilter Validated successfully*****");
        	   break;
        	
    	case "Custodian":
    		bc.stepInfo("Test case Id: RPMXCON-54660 - HeaderCustodianName");
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getDocExp_CusName().Visible() ;}}), Input.wait30);
    		bc.stepInfo("*****Select Custodian Name*****");
        	String Cusname = getDocExp_CusName().getText();
        	System.out.println(Cusname);
        	UtilityLog.info(Cusname);
        	bc.passedStep("*****Custodian Name HeaderFilter Selected*****");
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getDocExp_CustodianSearchName().Visible() ;}}), Input.wait30);
        	bc.stepInfo("*****Enter Custodian Name*****");
        	getDocExp_CustodianSearchName().SendKeys(Cusname);
        	doclist.getApplyFilter().waitAndClick(10);
        	Thread.sleep(2000);
        	validateCount("Showing 1 to 50 of 1,134 entries");
        	getDocExp_CustodianSearchName().Clear();
        	bc.passedStep("*****Custodian Name HeaderFilter Validated successfully*****");
        	break;
        	
    	case "MasterDate":
    		bc.stepInfo("Test case Id: RPMXCON-54663 - HeaderMasterDate");
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getDocExp_MasterDate().Visible() ;}}), Input.wait30);
    		bc.stepInfo("*****Select Master Date*****");
        	String date = getDocExp_MasterDate().getText();
        	SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
        	Date date1 = dateformat.parse(date);
        	String expdate = dateformat.format(date1);
        	System.out.println(expdate);
        	UtilityLog.info(expdate);
        	bc.passedStep("*****MasterDate HeaderFilter Selected*****");
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getDocExp_MasterDateSearchName().Visible() ;}}), Input.wait30);
        	bc.stepInfo("*****Enter Master Date*****");
        	getDocExp_MasterDateSearchName().SendKeys(expdate);
            doclist.getApplyFilter().waitAndClick(10);
            Thread.sleep(2000);
        	validateCount("Showing 1 to 50 of 95 entries");
        	bc.passedStep("*****MasterDate HeaderFilter Validated successfully*****");
        	break;
    	
    	}
    	
     	
   }
    
    public void TagWithMasterDateFilter(String dataforfilter) throws InterruptedException {
    	bc.stepInfo("Test case Id: RPMXCON-54726 - TagWithMasterDate");
    	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getDocExp_TagFilter().Visible() ;}}), Input.wait60);
    	getDocExp_TagFilter().waitAndClick(10);
    	bc.stepInfo("*****Exclude Tag*****");
    	doclist.exclude(dataforfilter); 
    	Thread.sleep(2000);
    	bc.passedStep("*****Filter with Tag Validated is successfull*****");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getDocExp_MasterDateFiler().Visible() ;}}), Input.wait30);
    	getDocExp_MasterDateFiler().waitAndClick(10);
    	bc.stepInfo("*****Add Datefilter*****");
    	doclist.dateFilter("before",  "2010/01/01", null);
    	doclist.getApplyFilter().waitAndClick(10);
    	Thread.sleep(5000);
    	
    	validateCount("Showing 1 to 50 of 1,143 entries");
    	bc.passedStep("*****MasterDate Filter with Tag excluded Validated successfully*****");
   }
    
    
    public void MasterDateFilter() throws InterruptedException {
    	 bc.stepInfo("Test case Id: RPMXCON-54686 - IncludeTagFilter");
    	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
    	bc.stepInfo("*****Filter with Include between date*****");
    	dateFilterexplorer("between", "2001/01/01", "2020/01/01");
    	Thread.sleep(2000);
    	
    	doclist.getApplyFilter().waitAndClick(10);
    	
    	validateCount("Showing 1 to 50 of 302 entries");
    	bc.passedStep("*****MasterDate Filter with Include between date Validated successfully*****");
   }
    
 public void TagFilter(String data1,String data2) throws InterruptedException {
	 bc.stepInfo("Test case Id: RPMXCON-54680 - IncludeTagFilter");
    	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getDocExp_TagFilter().Visible() ;}}), Input.wait60);
    	getDocExp_TagFilter().waitAndClick(10);
    	bc.stepInfo("*****Include Tag*****");
    	doclist.include(data1);
    	Thread.sleep(2000);
    	
    	doclist.getApplyFilter().waitAndClick(10);
    	
    	validateCount("Showing 1 to 50 of 50 entries");
    	bc.stepInfo("*****Update Tag*****");
	    UpdateFilter(data2);
    	
        doclist.getApplyFilter().waitAndClick(10);
    	
    	validateCount("Showing 1 to 50 of 1,202 entries");
    	bc.passedStep("*****Include Tag Filter Validated successfully*****");
   }
    
  public void CustodianFilter(String data1,String data2,String functionality) throws InterruptedException {
	    bc.stepInfo("Test case Id: RPMXCON-54720 - Custodiannamefilter");
    	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getDocExp_CustodianFilter().Visible() ;}}), Input.wait60);
    	bc.stepInfo("*****Include Custodian name*****");
    	getDocExp_CustodianFilter().waitAndClick(10);
    	
    	if(functionality.equalsIgnoreCase("include"))
	  	{
    	doclist.include(data1);
    	Thread.sleep(2000);
    	
    	doclist.getApplyFilter().waitAndClick(10);
    	Thread.sleep(5000);
    	
    	validateCount("Showing 1 to 50 of 1,134 entries");
    	
    	UpdateFilter(data2);
    	Thread.sleep(2000);
    	
        doclist.getApplyFilter().waitAndClick(10);
        Thread.sleep(5000);
        
    	validateCount("Showing 1 to 50 of 1,136 entries");
    	bc.passedStep("*****Custodian name include Filter Validated successfully*****");
	  	}
    	bc.stepInfo("*****Exclude Custodian name*****");
    	if(functionality.equalsIgnoreCase("exclude"))
	  	{
    	doclist.exclude(data1);
    	Thread.sleep(2000);
    	
    	doclist.getApplyFilter().waitAndClick(10);
    	Thread.sleep(2000);
    	
    	validateCount("Showing 1 to 50 of 68 entries");
    	
    	UpdateFilter(data2);
    	Thread.sleep(2000);
    	
        doclist.getApplyFilter().waitAndClick(10);
        Thread.sleep(2000);
    	
    	validateCount("Showing 1 to 50 of 66 entries");
	  	}
    	bc.passedStep("*****Custodian name exclude Filter Validated successfully*****");
   }
    
  public void DocFileTypeFilter(String data1,String data2) throws InterruptedException {
	  bc.stepInfo("Test case Id: RPMXCON-54673 - DocFileTypeFilter");
  	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getDocExp_GetDocFIleTypeFilter().Visible() ;}}), Input.wait60);
  	getDocExp_GetDocFIleTypeFilter().waitAndClick(10);
  	bc.stepInfo("*****Include Docfile type*****");
  	doclist.include(data1);
  	Thread.sleep(2000);
  	
  	doclist.getApplyFilter().waitAndClick(10);
  	
  	validateCount("Showing 1 to 50 of 865 entries");
  	bc.stepInfo("*****Update Docfile type*****");
  	UpdateFilter(data2);
  	
    doclist.getApplyFilter().waitAndClick(10);
  	
  	validateCount("Showing 1 to 50 of 869 entries");
  	bc.passedStep("*****Docfile Type Filter Validated successfully*****");
 }
  
  public void EmailRecipientNameFilter(String data1,String data2) throws InterruptedException {
	  bc.stepInfo("Test case Id: RPMXCON-54675 - EmailRecipientNameFilter");
	  	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
	  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	  			getDocExp_EmailRecNameFilter().Visible() ;}}), Input.wait60);
	  	getDocExp_EmailRecNameFilter().waitAndClick(10);
	  	bc.stepInfo("*****Include EmailRecipientName*****");
	  	doclist.include(data1);
	  	Thread.sleep(2000);
	  	
	  	doclist.getApplyFilter().waitAndClick(10);
	  	Thread.sleep(5000);
	  	
	  	validateCount("Showing 1 to 1 of 1 entries");
	  	bc.stepInfo("*****update EmailRecipientName*****");
	  	UpdateFilter(data2);
	  	
	    doclist.getApplyFilter().waitAndClick(10);
	    Thread.sleep(5000);
	  	
	  	validateCount("Showing 1 to 22 of 22 entries");
	  	bc.passedStep("*****EmailRecipientName Filter  Validated successfully*****");
	 }
  
  public void EmailAuthorNameFilter(String data1,String data2) throws InterruptedException {
	  bc.stepInfo("Test case Id: RPMXCON-54692 - EmailAuthorNameFilter");
	  	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
	  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	  			getDocExp_EmailAuthNameFilter().Visible() ;}}), Input.wait60);
	  	getDocExp_EmailAuthNameFilter().waitAndClick(10);
	  	bc.stepInfo("*****Exclude EmailAuthorName*****");
	  	doclist.exclude(data1);
	  	Thread.sleep(2000);
	  	
	  	doclist.getApplyFilter().waitAndClick(10);
	  	Thread.sleep(5000);
	  	
	  	validateCount("Showing 1 to 50 of 1,201 entries");
	  	bc.stepInfo("*****Update EmailAuthorName*****");
	  	UpdateFilter(data2);
	  	
	    doclist.getApplyFilter().waitAndClick(10);
	    Thread.sleep(5000);
	  	
	  	validateCount("Showing 1 to 50 of 1,200 entries");
	  	bc.passedStep("*****EmailAuthorName Filter  Validated successfully*****");
	 }
  
  public void EmailAuthorDomainFilter(String data1,String data2) throws InterruptedException {
	     bc.stepInfo("Test case Id: RPMXCON-54676 - EmailAuthorDomainFilter");
	  	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
	  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	  			getDocExp_EmailAuthDomainFilter().Visible() ;}}), Input.wait60);
	  	getDocExp_EmailAuthDomainFilter().waitAndClick(10);
	  	bc.stepInfo("*****Include EmailAuthorDomain*****");
	  	doclist.include(data1);
	  	Thread.sleep(2000);
	  	
	  	doclist.getApplyFilter().waitAndClick(10);
	  	Thread.sleep(5000);
	  	
	  	validateCount("Showing 1 to 6 of 6 entries");
	  	bc.stepInfo("*****Update EmailAuthorDomain*****");
	  	UpdateFilter(data2);
	  	
	    doclist.getApplyFilter().waitAndClick(10);
	    Thread.sleep(5000);
	  	
	  	validateCount("Showing 1 to 10 of 10 entries");
	  	bc.passedStep("*****EmailAuthorDomain Filter  Validated successfully*****");
	 }
  
  public void AssignmentFilter(String assgnm1,String assgnm2,String functionality) throws InterruptedException {
	   
	  	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
	  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	  			getDocExp_AssignmentFilter().Visible() ;}}), Input.wait30);
	  	getDocExp_AssignmentFilter().waitAndClick(10);
	  	
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
     		   !getDocExp_DocumentList_info().getText().isEmpty()  ;}}),Input.wait60);
 	   //to make sure that not reading previous results and wait for page mask to complete,
 	   //simply try to click on result text!
 	   driver.scrollingToBottomofAPage();
 	  getDocExp_DocumentList_info().waitAndClick(30); //works only when results updates!!
        
 	   //Now validate results
 	   Assert.assertTrue(getDocExp_DocumentList_info().getText().toString().equalsIgnoreCase(counts));
 	   
 }
  
    public void UpdateFilter(String data) {
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getDocExp_ActiveFilter().Visible()  ;}}), Input.wait30);
   	getDocExp_ActiveFilter().Click();
   	doclist.getSearchTextArea().SendKeys(data);
   	try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	doclist.getSearchTextArea().SendKeysNoClear(""+Keys.ENTER);
   	getDocExp_UpdateFilter().waitAndClick(10);
   }
    
    public void dateFilterexplorer(String option, String fromDate, String toDate) {
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			doclist.getMasterDateFilter().Visible()  ;}}), Input.wait30);
		
    	doclist.getMasterDateFilter().waitAndClick(10);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			doclist.getMasterDateRange().Visible()  ;}}), Input.wait30);
    	doclist.getMasterDateRange().selectFromDropdown().selectByValue(option);
    	
    	getDocExp_SetFromMasterDate().SendKeys(fromDate+Keys.TAB);
    	
    	if(option.equalsIgnoreCase("between")) 
    	getDocExpt_SetToMasterDate().SendKeys(toDate+Keys.TAB);
    	
    	doclist.getAddToFilter().waitAndClick(10);
    	doclist.getApplyFilter().waitAndClick(10);
	}
    
    public void DocExplorertoquickBatch() throws InterruptedException
    {
    	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getDocExp_SelectAllDocs().Visible()  ;}}), Input.wait30); 
    	getDocExp_SelectAllDocs().waitAndClick(10);
    	Thread.sleep(2000);//here
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			doclist.getYesAllPageDocs().Visible()  ;}}), Input.wait120); 
    	doclist.getYesAllPageDocs().waitAndClick(15);//changed here..
    	Thread.sleep(2000);
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			doclist.getPopUpOkBtn().Visible()  ;}}), Input.wait60); 
    	doclist.getPopUpOkBtn().Click();
    	
    	getDocExp_actionButton().waitAndClick(10);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getDocExp_action_quickbatch().Visible()  ;}}), Input.wait30); 
    	getDocExp_action_quickbatch().Click();
    }
 
  public void DocIDandDocFileTypeFilters() throws InterruptedException {
        
        this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
        
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getDocExp_DocID().Visible() ;}}), Input.wait30);
    	String DocId = getDocExp_DocID().getText();
    	System.out.println(DocId);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getDocExp_DocIDSearchName().Visible() ;}}), Input.wait30);
    	getDocExp_DocIDSearchName().SendKeys(DocId);
    	
    	String Docfiletype = getDocExp_Docfiletype().getText();
    	System.out.println(Docfiletype);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getDocExp_DocFiletypeSearchName().Visible() ;}}), Input.wait30);
    	getDocExp_DocFiletypeSearchName().SendKeys(Docfiletype);
    	
    	doclist.getApplyFilter().waitAndClick(10);
        Thread.sleep(2000);
        validateCount("Showing 1 to 1 of 1 entries");
        }
        
  public void CusNameandDocFileTypeFilters() throws InterruptedException {
      
      this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
      
      driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		  getDocExp_CusName().Visible() ;}}), Input.wait30);
  	String Cusname = getDocExp_CusName().getText();
  	System.out.println(Cusname);
  	
  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getDocExp_CustodianSearchName().Visible() ;}}), Input.wait30);
  	getDocExp_CustodianSearchName().SendKeys(Cusname);
  	
  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getDocExp_DocFiletypeSearchName().Visible() ;}}), Input.wait30);
  	getDocExp_DocFiletypeSearchName().SendKeys("Outlook");
  	
  	doclist.getApplyFilter().waitAndClick(10);
      Thread.sleep(2000);
      validateCount("Showing 1 to 50 of 822 entries");
      }
       
   
  public void CommentFilter() throws InterruptedException {
	  bc.stepInfo("Test case Id: RPMXCON-54682 - CommentFilter");
  	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getDocExp_CommentsFilter().Visible() ;}}), Input.wait60);
  	getDocExp_CommentsFilter().waitAndClick(10);
  	bc.stepInfo("*****Include Comment*****");
  	doclist.include("Document_Comments");
  	Thread.sleep(2000);
  	
  	doclist.getApplyFilter().waitAndClick(10);
  	
    validateCount("Showing 1 to 1 of 1 entries");
    bc.passedStep("*****Comment Filter  Validated successfully*****");
 }

  public void DocExplorertodoclist()
  {
  	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getDocExp_SelectAllDocs().Visible()  ;}}), Input.wait30); 
  	getDocExp_SelectAllDocs().waitAndClick(10);
  	
  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			doclist.getPopUpOkBtn().Visible()  ;}}), Input.wait30); 
  	doclist.getPopUpOkBtn().Click();
  	
  	 getDocExp_actionButton().waitAndClick(10);
  		
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getDocListAction().Visible()  ;}}), Input.wait60); 
	 
	 getDocListAction().waitAndClick(20);
	 try{
			bc.getYesBtn().waitAndClick(10);
		}catch (Exception e) {
			// TODO: handle exception
		}
	 System.out.println("Navigated to doclist, to view docslist");
	 UtilityLog.info("Navigated to doclist, to view docslist");
	
  }

 
 }