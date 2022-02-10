package pageFactory;

import java.awt.Robot;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
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
    
    
    //Added By Gopinath - 09-01-2021
    public Element getAllDocSelectedCheckBox(){ return driver.FindElementByXPath("//thead/tr[1]/th[1]/label[1]/i[1]"); }
    public Element getOkButton(){ return driver.FindElementById("bot1-Msg1"); }
    public Element getBulkTagButton(){ return driver.FindElementById("idBulkTag"); }
    public Element getTag(String tagName){ return driver.FindElementByXPath("//a[@data-content = '"+tagName+"']//i[@class='jstree-icon jstree-checkbox']"); }
    public Element getContinueButton() {return driver.FindElementById("btnAdd"); }
    public Element getFinalizeButton() {return driver.FindElementById("btnfinalizeAssignment"); }
    public Element getDocBoard() {return driver.FindElementById("divBulkTagJSTree"); }
   // Added  by jayanthi
    public Element getDocExp_BulkAssign(){ return driver.FindElementByXPath("//li//a[text()='Bulk Assign ']"); }
  
    
 // added by sowndariya
 	public Element getBulkFolder() {
 		return driver.FindElementByXPath(" //a[text()='Bulk Folder']");
 	}

 	public Element getBulkFolderCheckBox() {
 		return driver.FindElementByXPath(
 				"//div[@id='divBulkFolderJSTree']//a[text()='analyticsFolderName7838786']/i[contains(@class,'checkbox')]");
 	}

 	public Element getselectDoc(int rowno) {
 		return driver
 				.FindElementByXPath("//*[@id='dtDocumentList']//following-sibling::tbody//tr[" + rowno + "]//label");
 	}
 	public Element getBulkFolderCheckBox(String folderName){ return driver.FindElementByXPath("//div[@id='divBulkFolderJSTree']//a[text()='"+folderName+"']/i[contains(@class,'checkbox')]");}

 	//*** Added by Jagadeesh.Jana _ Starts ***
    public Element get_filterByFolder() 
	 {
        return driver
                .FindElementById("option-NVARCHAR--32720");
    }
    public Element get_filterByFolderInclude() 
	 {
        return driver
                .FindElementByXPath("(//div[@id='rbIncExclude']/label/i)[1]");
    }
    public Element get_ClickToMakeYourSelection()  
	 {
        return driver
                .FindElementByXPath("//input[@placeholder='Click to make your selection']");
    }
    public Element get_firstRecordFromList()  
	 {
        return driver
                .FindElementByXPath("//ul[@id='select2-Folders-results']/li[1]");
	 }
    public Element addToFilterButton()  
	 {
        return driver
                .FindElementByXPath("(//a[@id='action-NVARCHAR--32720'])[1]");
	 }
    public Element doclistTableMultiSelect(int row) 
    { 
		return driver.FindElementByXPath("//table[@id='dtDocumentList']/tbody/tr["+ row +"]/td[1]");
	 }
    public Element actionDropdown() 
    { 
		return driver.FindElementByXPath("//button[@id='idDocExplorerActions']");
	 }     
    public Element exportDataFromActionDropdown() 
    { 
		return driver.FindElementByXPath("//a[@id='idExportData']");
	 }
    public Element exportWindow_AllCustodiansCheckbox() 
    { 
		return driver.FindElementByXPath("(//*[text()='AllCustodians'])[2]");
	 }
    public Element exportWindow_AddToSelectedButton() 
    { 
		return driver.FindElementByXPath("//a[@id='addFormObjects-coreList']");
	 }
    public Element exportWindow_RunExport() 
    { 
		return driver.FindElementByXPath("//a[@id='btnRunReport']");
	 }
    public Element exportWindow_closeButton() 
    { 
		return driver.FindElementByXPath("(//button[@class='ui-dialog-titlebar-close'])[2]");
	 }
    public Element exportWindow_PreviewDocumentCloseButton() 
    { 
		return driver.FindElementByXPath("(//button[@class='ui-dialog-titlebar-close'])[1]");
	 }
    public Element doclistTableMulti_EyeIconSelect(int row1) 
    { 
		return driver.FindElementByXPath("//table[@id='dtDocumentList']/tbody/tr["+ row1 +"]/td[10]");
	 }
  //*** Added by Jagadeesh.Jana _ Ends *** 
    
    
    
    
    //Added by Gopinath - 08/10/2021
    public Element getDocumentsCheckBoxbyRowNum(int rowNumber) { return driver.FindElementByXPath("//table[@id='dtDocumentList']//tbody//tr["+rowNumber+"]//td[1]//i");}
    public Element getExcludeRadioBtn() { return driver.FindElementByXPath("(//*[@id='rbIncExclude']/label[2])[1]");}
    public Element getSearchTextArea() {return driver.FindElementByXPath("//*[@type='search']");}
    public Element getAddToFilter() {return driver.FindElementByXPath("(//*[contains(text(),' Add to Filter')])[1]");}
    public Element getDocumentsIdbyRowNum(int rowNumber) { return driver.FindElementByXPath("//table[@id='dtDocumentList']//tbody//tr["+rowNumber+"]//td[2]//div");}
    public ElementCollection getTotalRowsInTable() {return driver.FindElementsByXPath("//table[@id='dtDocumentList']//tbody//tr");}
    public Element getApplyFilter() {return driver.FindElementById("btnAppyFilter");}
    public Element getAllDocumentsCount() {return driver.FindElementByXPath("//div[@id='divNodeTree']/ul[1]/li[1]/a[1]");}
    public Element getPresentDocCount(){return driver.FindElementById("dtDocumentList_info");}
    public Element getIncludeRadioBtn() {return driver.FindElementByXPath("(//*[@id='rbIncExclude']/label[1])[1]");}
    public Element getActiveFilter(){return driver.FindElementByXPath("//div[@id='activeFilters']//li");}
    public Element getUpdateFilter(){return driver.FindElementByXPath("(//a[text()='Update Filter'])[1]");}   
    public ElementCollection getDocumentsName() {return driver.FindElementsByXPath("//table[@id='dtDocumentList']//tbody//tr//td[2]/div");}	
    public Element getDocumentId(int rowNo) {return driver.FindElementByXPath("//table[@id='dtDocumentList']/tbody/tr["+rowNo+"]//td[2]//div");}
   
    //Added by gopinath-12/05/2021
    public Element getEmailSubjectTextField(){return driver.FindElementByXPath("//div[@class='dataTables_scrollHeadInner']//thead//tr[2]/th[@data-searchname='DocFileName']//input");}   
    public Element getMasterDatetextField(){return driver.FindElementByXPath("//div[@class='dataTables_scrollHeadInner']//thead//tr//th[@data-searchname='MasterDate']//input");}   
    public Element getDocListHeader(){return driver.FindElementByXPath("//h1[@class='page-title']");}   
     
    //Added by Gopinath - 10/02/2022
    public Element getfolderFromTreeByNumber(String folderNumber) {
		return driver.FindElementByXPath("//ul[@class='jstree-container-ul jstree-children']/li/a[@id='-1_anchor']/following-sibling::ul[@class='jstree-children']/li["+folderNumber+"]/a");
	}


	public Element getDocListCustodianName() {
			return driver.FindElementByXPath("//table[@id='dtDocumentList']/tbody/tr[1]/td[4]/div");
		}
	public ElementCollection getDocListInPage() {
			return driver.FindElementsByXPath("//table[@id='dtDocumentList']/tbody/tr");
		}
	public ElementCollection getDocLictPaginationCount() {
			return driver.FindElementsByXPath("//ul[@class='pagination pagination-sm']/li/a");
		}
	public Element getDocLictPaginationNextButton() {
			return driver.FindElementByXPath("//ul[@class='pagination pagination-sm']/li/a[text()='Next']");
		}
    
    
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
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getDocExp_DocID().Visible() ;}}), Input.wait30);
        	String DocId = getDocExp_DocID().getText();
        	System.out.println(DocId);
        	UtilityLog.info(DocId);
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getDocExp_DocIDSearchName().Visible() ;}}), Input.wait30);
        		getDocExp_DocIDSearchName().SendKeys(DocId);
        		doclist.getApplyFilter().waitAndClick(10);
            	Thread.sleep(2000);
            	validateCount("Showing 1 to 1 of 1 entries");
            	getDocExp_DocIDSearchName().Clear();
        	   break;
        	
    	case "Custodian":
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getDocExp_CusName().Visible() ;}}), Input.wait30);
        	String Cusname = getDocExp_CusName().getText();
        	System.out.println(Cusname);
        	UtilityLog.info(Cusname);
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getDocExp_CustodianSearchName().Visible() ;}}), Input.wait30);
        	getDocExp_CustodianSearchName().SendKeys(Cusname);
        	doclist.getApplyFilter().waitAndClick(10);
        	Thread.sleep(2000);
        	validateCount("Showing 1 to 50 of 1,134 entries");
        	getDocExp_CustodianSearchName().Clear();
        	break;
        	
    	case "MasterDate":
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getDocExp_MasterDate().Visible() ;}}), Input.wait30);
        	String date = getDocExp_MasterDate().getText();
        	SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        	Date date1 = dateformat.parse(date);
        	String expdate = dateformat.format(date1);
        	System.out.println(expdate);
        	UtilityLog.info(expdate);
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getDocExp_MasterDateSearchName().Visible() ;}}), Input.wait30);
        	getDocExp_MasterDateSearchName().SendKeys(expdate);
            doclist.getApplyFilter().waitAndClick(10);
            Thread.sleep(2000);
        	validateCount("Showing 1 to 50 of 95 entries");
        	break;
    	
    	}
    	
     	
   }
    
    public void TagWithMasterDateFilter(String dataforfilter) throws InterruptedException {
        
    	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getDocExp_TagFilter().Visible() ;}}), Input.wait60);
    	getDocExp_TagFilter().waitAndClick(10);
    	
    	doclist.exclude(dataforfilter);
    	Thread.sleep(2000);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getDocExp_MasterDateFiler().Visible() ;}}), Input.wait30);
    	getDocExp_MasterDateFiler().waitAndClick(10);
    	
    	doclist.dateFilter("before",  "2010/01/01", null);
    	doclist.getApplyFilter().waitAndClick(10);
    	Thread.sleep(5000);
    	
    	validateCount("Showing 1 to 50 of 1,145 entries");
   }
    
    
    public void MasterDateFilter() throws InterruptedException {
      
    	dateFilterexplorer("between", "2001/01/01", "2020/01/01");
    	Thread.sleep(2000);
    	
    	doclist.getApplyFilter().waitAndClick(10);
    	
    	validateCount("Showing 1 to 50 of 302 entries");
   }
    
 public void TagFilter(String data1,String data2) throws InterruptedException {
      
    	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getDocExp_TagFilter().Visible() ;}}), Input.wait60);
    	getDocExp_TagFilter().waitAndClick(10);
    	
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
    			getDocExp_CustodianFilter().Visible() ;}}), Input.wait60);
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
	  	}
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
    	
   }
    
  public void DocFileTypeFilter(String data1,String data2) throws InterruptedException {
   
  	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getDocExp_GetDocFIleTypeFilter().Visible() ;}}), Input.wait60);
  	getDocExp_GetDocFIleTypeFilter().waitAndClick(10);
  	
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
	  			getDocExp_EmailRecNameFilter().Visible() ;}}), Input.wait60);
	  	getDocExp_EmailRecNameFilter().waitAndClick(10);
	  	
	  	doclist.include(data1);
	  	Thread.sleep(2000);
	  	
	  	doclist.getApplyFilter().waitAndClick(10);
	  	Thread.sleep(5000);
	  	
	  	validateCount("Showing 1 to 3 of 3 entries");
	  	
	  	UpdateFilter(data2);
	  	
	    doclist.getApplyFilter().waitAndClick(10);
	    Thread.sleep(5000);
	  	
	  	validateCount("Showing 1 to 13 of 13 entries");
	 }
  
  public void EmailAuthorNameFilter(String data1,String data2) throws InterruptedException {
	   
	  	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
	  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	  			getDocExp_EmailAuthNameFilter().Visible() ;}}), Input.wait60);
	  	getDocExp_EmailAuthNameFilter().waitAndClick(10);
	  	
	  	doclist.exclude(data1);
	  	Thread.sleep(2000);
	  	
	  	doclist.getApplyFilter().waitAndClick(10);
	  	Thread.sleep(5000);
	  	
	  	validateCount("Showing 1 to 50 of 2,537 entries");
	  	
	  	UpdateFilter(data2);
	  	
	    doclist.getApplyFilter().waitAndClick(10);
	    Thread.sleep(5000);
	  	
	  	validateCount("Showing 1 to 50 of 2,535 entries");
	 }
  
  public void EmailAuthorDomainFilter(String data1,String data2) throws InterruptedException {
	   
	  	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
	  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	  			getDocExp_EmailAuthDomainFilter().Visible() ;}}), Input.wait60);
	  	getDocExp_EmailAuthDomainFilter().waitAndClick(10);
	  	
	  	doclist.include(data1);
	  	Thread.sleep(2000);
	  	
	  	doclist.getApplyFilter().waitAndClick(10);
	  	Thread.sleep(5000);
	  	
	  	validateCount("Showing 1 to 6 of 6 entries");
	  	
	  	UpdateFilter(data2);
	  	
	    doclist.getApplyFilter().waitAndClick(10);
	    Thread.sleep(5000);
	  	
	  	validateCount("Showing 1 to 10 of 10 entries");
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
    
    public void DocExplorertoquickBatch()
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
      
  	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getDocExp_CommentsFilter().Visible() ;}}), Input.wait60);
  	getDocExp_CommentsFilter().waitAndClick(10);
  	
  	doclist.include("Document_Comments");
  	Thread.sleep(2000);
  	
  	doclist.getApplyFilter().waitAndClick(10);
  	
    validateCount("Showing 1 to 2 of 2 entries");
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
  
  
  
  /**
   * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
   * @Description: Method for selecting documents and bulk tag them.. 
   * @param tagName : tagName is a string value that name of tag need to select.
   */
     public void selectDocAndBulkTag(String tagName) {
  	   try {
  		   driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
		   for(int i=0;i<20;i++) {
			   try {
				   getAllDocSelectedCheckBox().Click();
				   break;
			   }catch(Exception e) {
				   bc.waitForElement(getAllDocSelectedCheckBox());
				   bc.waitTillElemetToBeClickable(getAllDocSelectedCheckBox());
			   }
		   }
		   try {
	  			 bc.waitTillElemetToBeClickable(getOkButton());
	  			 getOkButton().Click();
	  		   }catch(Exception e) {
	  			   bc.stepInfo("Ok button is not appered");
	  		   }
		   for(int i=0;i<10;i++) {
			   try {
				   bc.waitForElement(getDocExp_actionButton());
				   bc.waitTillElemetToBeClickable(getDocExp_actionButton());
				   getDocExp_actionButton().Click();
				   break;
			   }catch(Exception e) {
				  Thread.sleep(1000);
			   }
		   }
		   bc.waitTillElemetToBeClickable(getBulkTagButton());
  		   getBulkTagButton().Click();
  		   driver.scrollingToBottomofAPage();
  		   bc.waitForElement(getDocBoard());
  		   getDocBoard().Click();
  		   getTag(tagName).Click();
  		   for(int i=0;i<20;i++) {
			   try {
				   Thread.sleep(1500);
				   getContinueButton().Click();
				   break;
			   }catch(Exception e) {
				   bc.waitTillElemetToBeClickable(getContinueButton());
			   }
		   }
  		   bc.waitForElement(getFinalizeButton());
		   bc.waitTillElemetToBeClickable(getFinalizeButton());
  		   getFinalizeButton().Click();
  		   try {
  			 bc.waitForElement(getOkButton());
  			 bc.waitTillElemetToBeClickable(getOkButton());
  			 getOkButton().Click();
  		   }catch(Exception e) {
  			   bc.stepInfo("Ok button is not appered after bulk tag operation");
  		   }
  		   bc.waitForElement(bc.getSuccessMsg());
		   bc.getSuccessMsg().waitAndFind(10);
		   Assert.assertEquals("Success message is not displayed", true, bc.getSuccessMsg().getWebElement().isDisplayed());
		   if( bc.getSuccessMsg().getWebElement().isDisplayed()) {
				bc.passedStep("Success message is displayed successfully");
			}
  	   }catch(Exception e) {
  		   e.printStackTrace();
	   			bc.failedStep("Exception occcured while selecting documents or bulk tag"+e.getMessage());
	   			
 			}
     }

 	/**
 	 * @Author:Indium-sowndarya.Velraj
 	 * 
 	 */
 	public void documentSelectionIteration() throws InterruptedException {
 		this.driver.getWebDriver().get(Input.url + "DocExplorer/Explorer");
 		for (int D = 1; D < 4; D++) {
 			driver.waitForPageToBeReady();
 			bc.waitForElement(getselectDoc(D));
 			getselectDoc(D).waitAndClick(10);
 		}
 	}

 	/**
 	 * @Author:Indium-sowndarya.Velraj
 	 * 
 	 */
 	public void docExpViewInDocView() {
 		driver.waitForPageToBeReady();
 		bc.waitForElement(getDocExp_actionButton());
 		getDocExp_actionButton().waitAndClick(5);
 		bc.waitForElement(getDocViewAction());
 		getDocViewAction().waitAndClick(5);
 	}

 	public void bulkFolderExisting( String folderName) throws  InterruptedException {
 		Thread.sleep(3000);
 	    driver.WaitUntil((new Callable<Boolean>() {

 	     public Boolean call() {

 	      return getDocExp_actionButton().Visible();}

 	    }), Input.wait60);

 	    getDocExp_actionButton().Click();   

 	    Thread.sleep(2000);
 	    driver.WaitUntil((new Callable<Boolean>() {

 	        public Boolean call() {

 	         return getBulkFolder().Visible();}

 	       }), Input.wait60);

 	    getBulkFolder().Click();       

 	  driver.Manage().window().fullscreen();
 	  System.out.println("Popup is displayed");

 	    bc.hitTabKey(3);
 	    bc.hitEnterKey(2);
 	    try {

 	                 Thread.sleep(5000);

 	                 driver.WaitUntil((new Callable<Boolean>() {

 	                      public Boolean call() {

 	                       return getBulkFolderCheckBox(folderName).Visible();}

 	                     }), Input.wait60);

 	                 getBulkFolderCheckBox(folderName).Click();       
 	                 getBulkFolderCheckBox(folderName).ScrollTo();

 	               System.out.println("Folder is selected");

 	               }catch(Exception e) {
 	                 System.out.println("Folder is Searching");
 	           }
 	           Thread.sleep(12000);

 	    bc.waitForElement(getContinueButton());
 	    getContinueButton().Click();
 	    System.out.println("Clicked continue");

 	   driver.waitForPageToBeReady();

 	    bc.waitForElement(getFinalizeButton());

 	    getFinalizeButton().Click();
 	    driver.Manage().window().maximize();
 	    bc.VerifySuccessMessage("Records saved successfully");
 }
 	
 	/* Jagadeesh.Jana
 	 * Test Case Id: RPMXCON-51992
 	 */   
     public void exportData() throws Exception
     {
    	 
    	 bc = new BaseClass(driver);
    	 this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
    	 
    	 bc.waitForElement(get_filterByFolder());
    	 get_filterByFolder().waitAndClick(10);
    	 
    	 bc.waitForElement(get_filterByFolderInclude());
    	 get_filterByFolderInclude().waitAndClick(10);
    	 
    	 bc.waitForElement(get_ClickToMakeYourSelection());
    	 get_ClickToMakeYourSelection().waitAndClick(10);
    	 
    	 bc.waitForElement(get_firstRecordFromList());
    	 get_firstRecordFromList().waitAndClick(10);
    	 
    	 bc.waitForElement(addToFilterButton());
    	 addToFilterButton().waitAndClick(10);	 
    	 for (int row = 1; row <= 10; row++) 
    	 {
    		 doclistTableMultiSelect(row).Click();
 			 Thread.sleep(5000);  //required here as per testCase requirements 
 		 }
    	 bc.waitForElement(actionDropdown());
    	 actionDropdown().waitAndClick(10);
    	 
    	 bc.waitForElement(exportDataFromActionDropdown());
    	 exportDataFromActionDropdown().waitAndClick(10);
    	 
    	 bc.waitForElement(exportWindow_AllCustodiansCheckbox());
    	 exportWindow_AllCustodiansCheckbox().waitAndClick(10);
    	 
    	 driver.scrollingToBottomofAPage();
    	 bc.waitForElement(exportWindow_AddToSelectedButton());
    	 exportWindow_AddToSelectedButton().waitAndClick(10);
    	 
    	 driver.scrollPageToTop();
    	 bc.waitForElement(exportWindow_RunExport());
    	 exportWindow_RunExport().waitAndClick(10);
    	 
    	 bc.waitForElement(exportWindow_closeButton());
    	 exportWindow_closeButton().waitAndClick(10);
    	 
    	 for (int row1 = 1; row1 <= 10; row1++) 
    	 {
    		 doclistTableMulti_EyeIconSelect(row1).Click();
    		 Thread.sleep(5000); //required here as per testCase requirements 
    		 bc.waitForElement(exportWindow_PreviewDocumentCloseButton());
    		 exportWindow_PreviewDocumentCloseButton().waitAndClick(10);
 			 Thread.sleep(5000); //required here as per testCase requirements 		 
 		 }
    	   
    	 System.out.println("DocView and Doc Explorer_Performance_Navigate through documents one by one- Successfully");
         bc.passedStep("DocView and Doc Explorer_Performance_Navigate through documents one by one- Successfully");
     }
     
     
     
     /**
      * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
      * @Description: Method for selecting some documents and bulk tag them.. 
      * @param tagName : tagName is a string value that name of tag need to select.
      * @param numberOfDocuments : numberOfDocuments is integer value that number of documents need to select.
      * @return docids : Selected documents ID's for bulk tag.
      */
        public ArrayList<String> selectDocumentsAndBulkTag(int numberOfDocuments,String tagName) {
        	 ArrayList<String> docids = new ArrayList<String>();
     	   try {
     		  driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
     		  driver.getWebDriver().navigate().refresh();
     		  driver.waitForPageToBeReady();
     		  for(int i=0;i<5;i++) {
	   			   try {
	   				   bc.waitForElement(getDocumentsCheckBoxbyRowNum(1));
	   				   bc.waitTillElemetToBeClickable(getDocumentsCheckBoxbyRowNum(1));
	   				   break;
	   			   }catch(Exception e) {
	   				   bc.waitForElement(getDocumentsCheckBoxbyRowNum(1));
	   				   bc.waitTillElemetToBeClickable(getDocumentsCheckBoxbyRowNum(1));
	   			   }
	   		   }
     		  for(int row=0;row<numberOfDocuments;row++) {
     			 bc.waitForElement(getDocumentsCheckBoxbyRowNum(1));
  				 bc.waitTillElemetToBeClickable(getDocumentsCheckBoxbyRowNum(1));
  				 getDocumentsCheckBoxbyRowNum(row+1).Click();
  				docids.add(getDocumentsIdbyRowNum(row+1).GetAttribute("data-content").trim());
     		  }
	   		   for(int i=0;i<7;i++) {
	   			   try {
	   				   bc.waitForElement(getDocExp_actionButton());
	   				   bc.waitTillElemetToBeClickable(getDocExp_actionButton());
	   				   getDocExp_actionButton().Click();
	   				   break;
	   			   }catch(Exception e) {
	   				  Thread.sleep(Input.wait30/10);
	   			   }
	   		   }
	   		   bc.waitTillElemetToBeClickable(getBulkTagButton());
	   		   getBulkTagButton().Click();
     		   driver.scrollingToBottomofAPage();
     		   bc.waitForElement(getDocBoard());
     		   getDocBoard().Click();
     		   bc.waitForElement(getTag(tagName));
     		  bc.waitTillElemetToBeClickable(getTag(tagName));
     		   getTag(tagName).Click();
     		   for(int i=0;i<5;i++) {
	   			   try {
	   				   Thread.sleep(Input.wait30/10);
	   				   getContinueButton().Click();
	   				   break;
	   			   }catch(Exception e) {
	   				   bc.waitTillElemetToBeClickable(getContinueButton());
	   			   }
     		   }
     		   bc.waitForElement(getFinalizeButton());
	   		   bc.waitTillElemetToBeClickable(getFinalizeButton());
     		   getFinalizeButton().Click();
     		   try {
     			 bc.waitForElement(getOkButton());
     			 bc.waitTillElemetToBeClickable(getOkButton());
     			 getOkButton().Click();
     			 bc.waitForElement(bc.getSuccessMsg());
     			 bc.getSuccessMsg().waitAndFind(10);
     			 Assert.assertEquals("Success message is not displayed", true, bc.getSuccessMsg().getWebElement().isDisplayed());
     			 if( bc.getSuccessMsg().getWebElement().isDisplayed()) {
 	   				bc.passedStep("Success message is displayed successfully");
     			 }
     		   }catch(Exception e) {
     			   bc.stepInfo("Ok button is not appered after bulk tag operation");
     		   }
     		   
     	   }catch(Exception e) {
     		   e.printStackTrace();
   	   			bc.failedStep("Exception occcured while selecting documents or bulk tag"+e.getMessage());
   	   			
    			}
     	   return docids;
        }
       

        /**
         * @author : Gopinath Created date: NA Modified date: NA Modified by:NA.
         * @Description: Method for performing exclude filter for tag.. 
         * @param tagName : tagName is a string value that name of tag need to select.
         */
           public void performExculdeTagFilter(String tagName) {
        	   try {
        		   
        		   driver.waitForPageToBeReady();
        		   driver.scrollPageToTop();
        		   bc.waitForElement(getDocExp_TagFilter());
        		   bc.waitTillElemetToBeClickable(getDocExp_TagFilter());
        		   getDocExp_TagFilter().Click();
        		   bc.waitForElement(getExcludeRadioBtn());
        		   bc.waitTillElemetToBeClickable(getExcludeRadioBtn());
        		   getExcludeRadioBtn().Click();
        		   getSearchTextArea().SendKeys(tagName);
        		   Thread.sleep(Input.wait30/30);
        		   getSearchTextArea().SendKeysNoClear("" + Keys.ENTER);
        		   driver.waitForPageToBeReady();
        		   bc.waitForElement(getAddToFilter());
        		   bc.waitTillElemetToBeClickable(getAddToFilter());
        		   getAddToFilter().Click();
        		   bc.waitForElement(getApplyFilter());
        		   bc.waitTillElemetToBeClickable(getApplyFilter());
        		   getApplyFilter().Click();
        	   }catch(Exception e) {
         		   e.printStackTrace();
       	   			bc.failedStep("Exception occcured while performing exclude filter for tag"+e.getMessage());
        		}
            }
           
           /**
            * @author : Gopinath Created date: NA Modified date: NA Modified by:NA.
            * @Description: Method for verifying include tag filter works properly or not.. 
            * @param docIds : docIds is array list of strings that documents of expected.
            */
              public void verifyIncludeTagFilterWorksProperly(ArrayList<String> docIds) {
           	   try {
           		   driver.waitForPageToBeReady();
           		   int totalRows = getTotalRowsInTable().FindWebElements().size();
           		   for(int row=0;row<totalRows;row++) {
           			   driver.waitForPageToBeReady();
           			   bc.waitForElement(getDocumentsIdbyRowNum(row+1));
           			   String docId = getDocumentsIdbyRowNum(row+1).GetAttribute("data-content").trim();
           			   if(!docIds.contains(docId)) {
           				   bc.failedStep("expected document with ID :: "+docId+" is not appered properly");
           			   }else if(docIds.contains(docId)){
           				 bc.passedStep("Document of Id :: "+docId+" is displayed on doc explorer table by applying tag filter");
           			   }
           		   }
           		   bc.passedStep("Include functionality is worked properly as expected");
           	   }catch(Exception e) {
            		   e.printStackTrace();
          	   			bc.failedStep("Exception occcured while verifying include tag filter works properly or not"+e.getMessage());
           		}
               }
 
              
              /**
               * @author : Gopinath Created date: NA Modified date: NA Modified by:NA.
               * @Description: Method for getting total number of documents.. 
               * @return docCount : docCount is a integer value that number of documents.
               */
                 public int totalDocumentsCount() {
                	 String docCount = null;
              	   try {
              		   driver.waitForPageToBeReady();
              		   bc.waitForElement(getAllDocumentsCount());
              		   bc.waitTillElemetToBeClickable(getAllDocumentsCount());
              		 docCount = getAllDocumentsCount().GetAttribute("data-content").trim().replaceAll("[^0-9]", "");
              	   }catch(Exception e) {
               		   e.printStackTrace();
               		   bc.failedStep("Exception occcured while getting total number of documents"+e.getMessage());
              		}
              	   return Integer.parseInt(docCount);
                  }
                 
                 /**
                  * @author : Gopinath Created date: NA Modified date: NA Modified by:NA.
                  * @Description: Method to verify documents after applying exclude functionality by tag.. 
                  * @param totalDocCount : docCount is a integer value that total number of documents.
                  * @param selectedDocCount : selectedDocCount is a integer that count of selected documents count for bulk tag
                  */
                    public void verifyExcludeFunctionlityForTag(int totalDocCount,int selectedDocCount) {
                   	
                 	   try {
                 		   driver.waitForPageToBeReady();
                 		   bc.waitForElement(getPresentDocCount());
                 		   bc.waitTillElemetToBeClickable(getPresentDocCount());
                 		   String[] presentDocCount = getPresentDocCount().getText().trim().split("of",2);
                 		   String docCount = presentDocCount[1].trim().replaceAll("[^0-9]", "");
                 		   int expectedCount = totalDocCount-selectedDocCount;
                 		   if(expectedCount == Integer.parseInt(docCount)) {
                 			   bc.passedStep("Exclude filter functionality by tag is working as expected");
                 		   }else {
                 			   bc.failedStep("Exclude filter functionality by tag is not working as expected");
                 		   }
                 	   }catch(Exception e) {
                  		   e.printStackTrace();
                  		   bc.failedStep("Exception occcured while verifying documents after applying exclude functionality by tag"+e.getMessage());
                 		}
                     }
                 
                    /**
                     * @author : Gopinath Created date: NA Modified date: NA Modified by:NA.
                     * @Description: Method for performing include filter for tag.. 
                     * @param tagName : tagName is a string value that name of tag need to select.
                     */
                       public void performInculdeTagFilter(String tagName) {
                    	   try {
                    		   
                    		   driver.waitForPageToBeReady();
                    		   driver.scrollPageToTop();
                    		   bc.waitForElement(getDocExp_TagFilter());
                    		   bc.waitTillElemetToBeClickable(getDocExp_TagFilter());
                    		   getDocExp_TagFilter().Click();
                    		   bc.waitForElement(getIncludeRadioBtn());
                    		   bc.waitTillElemetToBeClickable(getIncludeRadioBtn());
                    		   getIncludeRadioBtn().Click();
                    		   getSearchTextArea().SendKeys(tagName);
                    		   Thread.sleep(Input.wait30/30);
                    		   getSearchTextArea().SendKeysNoClear("" + Keys.ENTER);
                    		   driver.waitForPageToBeReady();
                    		   bc.waitForElement(getAddToFilter());
                    		   bc.waitTillElemetToBeClickable(getAddToFilter());
                    		   getAddToFilter().Click();
                    		   bc.waitForElement(getApplyFilter());
                    		   bc.waitTillElemetToBeClickable(getApplyFilter());
                    		   getApplyFilter().Click();
                    	   }catch(Exception e) {
                     		   e.printStackTrace();
                   	   			bc.failedStep("Exception occcured while performing include filter for tag"+e.getMessage());
                    		}
                        }
                 
	           
	           /**
	            * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	            * @Description: Method for selecting some documents and bulk folder them.. 
	            * @param folderName : folderName is a string value that name of folder need to select.
	            * @param numberOfDocuments : numberOfDocuments is integer value that number of documents need to select.
	            * @return docids : Selected documents ID's for bulk folder.
	            */
	              public ArrayList<String> selectDocumentsAndBulkFolder(int numberOfDocuments,String folderName) {
	              	 ArrayList<String> docids = new ArrayList<String>();
	           	   try {
	           		  driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
	           		  driver.getWebDriver().navigate().refresh();
	           		  driver.waitForPageToBeReady();
	           		  for(int i=0;i<5;i++) {
	      	   			   try {
	      	   				   bc.waitForElement(getDocumentsCheckBoxbyRowNum(1));
	      	   				   bc.waitTillElemetToBeClickable(getDocumentsCheckBoxbyRowNum(1));
	      	   				   break;
	      	   			   }catch(Exception e) {
	      	   				   bc.waitForElement(getDocumentsCheckBoxbyRowNum(1));
	      	   				   bc.waitTillElemetToBeClickable(getDocumentsCheckBoxbyRowNum(1));
	      	   			   }
	      	   		   }
	           		  for(int row=0;row<numberOfDocuments;row++) {
	           			 bc.waitForElement(getDocumentsCheckBoxbyRowNum(1));
	        				 bc.waitTillElemetToBeClickable(getDocumentsCheckBoxbyRowNum(1));
	        				 getDocumentsCheckBoxbyRowNum(row+1).Click();
	        				docids.add(getDocumentsIdbyRowNum(row+1).GetAttribute("data-content").trim());
	           		  }
	           		  bulkFolderExisting(folderName);
	           			 bc.waitForElement(bc.getSuccessMsg());
	           			 bc.getSuccessMsg().waitAndFind(10);
	           			 Assert.assertEquals("Success message is not displayed", true, bc.getSuccessMsg().getWebElement().isDisplayed());
	           			 if( bc.getSuccessMsg().getWebElement().isDisplayed()) {
	       	   				bc.passedStep("Success message is displayed successfully");
	           			 }
	           		   
	           	   }catch(Exception e) {
	           		   e.printStackTrace();
	           		   bc.failedStep("Exception occcured while selecting documents or bulk folder"+e.getMessage());
	         	   			
	          		}
	           	   return docids;
	              }
	             
	              /**
	               * @author : Gopinath Created date: NA Modified date: NA Modified by:NA.
	               * @Description: Method for performing exclude filter for folder.. 
	               * @param folderName : folderName is a string value that name of folder need to select.
	               */
	                 public void performExculdeFolderFilter(String folderName) {
	              	   try {
	              		   
	              		   driver.waitForPageToBeReady();
	              		   driver.scrollPageToTop();
	              		   bc.waitForElement(getDocExp_FolderFilter());
	              		   bc.waitTillElemetToBeClickable(getDocExp_FolderFilter());
	              		   getDocExp_FolderFilter().Click();
	              		   bc.waitForElement(getExcludeRadioBtn());
	              		   bc.waitTillElemetToBeClickable(getExcludeRadioBtn());
	              		   getExcludeRadioBtn().Click();
	              		   getSearchTextArea().SendKeys(folderName);
	              		   Thread.sleep(Input.wait30/30);
	              		   getSearchTextArea().SendKeysNoClear("" + Keys.ENTER);
	              		   driver.waitForPageToBeReady();
	              		   bc.waitForElement(getAddToFilter());
	              		   bc.waitTillElemetToBeClickable(getAddToFilter());
	              		   getAddToFilter().Click();
	              		   bc.waitForElement(getApplyFilter());
	              		   bc.waitTillElemetToBeClickable(getApplyFilter());
	              		   getApplyFilter().Click();
	              	   }catch(Exception e) {
	               		   e.printStackTrace();
	               		   bc.failedStep("Exception occcured while performing exclude filter for folder"+e.getMessage());
	              		}
	                  }
	                 
       
             /**
              * @author : Gopinath Created date: NA Modified date: NA Modified by:NA.
              * @Description: Method to verify documents after applying exclude functionality by folder.. 
              * @param totalDocCount : docCount is a integer value that total number of documents.
              * @param selectedDocCount : selectedDocCount is a integer that count of selected documents count for bulk folder
              */
                public void verifyExcludeFunctionlityForFolder(int totalDocCount,int selectedDocCount) {
               	
             	   try {
             		   driver.waitForPageToBeReady();
             		   bc.waitForElement(getPresentDocCount());
             		   bc.waitTillElemetToBeClickable(getPresentDocCount());
             		   String[] presentDocCount = getPresentDocCount().getText().trim().split("of",2);
             		   String docCount = presentDocCount[1].trim().replaceAll("[^0-9]", "");
             		   int expectedCount = totalDocCount-selectedDocCount;
             		   if(expectedCount == Integer.parseInt(docCount)) {
             			   bc.passedStep("Exclude filter functionality by folder is working as expected");
             		   }else {
             			   bc.failedStep("Exclude filter functionality by folder is not working as expected");
             		   }
             	   }catch(Exception e) {
              		   e.printStackTrace();
              		   bc.failedStep("Exception occcured while verifying documents after applying exclude functionality by folder"+e.getMessage());
             		}
                 }
                
                /**
                 * @author : Gopinath Created date: NA Modified date: NA Modified by:NA.
                 * @Description: Method to refreh pager.. 
                 */
                   public void refreshPage() {
                  	
                	   try {
                		   driver.Navigate().refresh();
                	   }catch(Exception e) {
                		   e.printStackTrace();
                  		   bc.failedStep("Exception occcured while refresh page"+e.getMessage());
                	   }
                   }
             
                   /**
                    * @author : Gopinath Created date: NA Modified date: NA Modified by:NA.
                    * @Description: Method for performing exclude another filter for folder.. 
                    * @param folderName : folderName is a string value that name of folder need to select.
                    */
                      public void performUpdateExculdeFolderFilter(String folderName) {
                   	   try {
                   		   
                   		   driver.waitForPageToBeReady();
                   		   driver.scrollPageToTop();
                   		   bc.waitForElement(getActiveFilter());
                   		   bc.waitTillElemetToBeClickable(getActiveFilter());
                   		   getActiveFilter().Click();
                   		   bc.waitForElement(getExcludeRadioBtn());
                   		   bc.waitTillElemetToBeClickable(getExcludeRadioBtn());
                   		   getExcludeRadioBtn().Click();
                   		   getSearchTextArea().SendKeys(folderName);
                   		   Thread.sleep(Input.wait30/30);
                   		   getSearchTextArea().SendKeysNoClear("" + Keys.ENTER);
                   		   driver.waitForPageToBeReady();
                   		   bc.waitForElement(getUpdateFilter());
                   		   bc.waitTillElemetToBeClickable(getUpdateFilter());
                   		   getUpdateFilter().Click();
                   		   bc.waitForElement(getApplyFilter());
                   		   bc.waitTillElemetToBeClickable(getApplyFilter());
                   		   getApplyFilter().Click();
                   	   }catch(Exception e) {
                    		   e.printStackTrace();
                    		   bc.failedStep("Exception occcured while performing exclude filter for folder"+e.getMessage());
                   		}
                       }
         
                    
                  /**
                   * @author Gopinath
                   * @Description : Method for selecting Documents.
                   * @param noofDocuments : noofDocuments is a integer value that number of documents need to select from doc explorer table.
                   * @return documentId : documentId is array list value that contains document ids of selected document.
                   */
                   public ArrayList<String> documentsSelection(int noofDocuments) throws InterruptedException {
                  	 	ArrayList<String> documentId=new ArrayList<String>();
                  	 	try {
                      		this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
                      			
                      		 for (int D=0;D<noofDocuments;D++) {
	                      		 driver.waitForPageToBeReady();
	                      		 bc.waitForElement(getDocumentsCheckBoxbyRowNum(D+1));
	                      		 getDocumentsCheckBoxbyRowNum(D+1).waitAndClick(10);
	                      		 documentId.add(getDocumentId(D+1).getText().trim());
                      		 }
                  	 	} catch (Exception e) {
                      		e.printStackTrace();
                      		bc.failedStep("Exception occcured while selecting the document ."+e.getMessage());
                      	}
                  	 return documentId;
                  	}

               /**
               * @author Gopinath
               * @Description :verifying the presence of documents after filling document_comments section .
               */
               public void verifyingTheSelectedDocumentInDocExplorerPage(ArrayList <String> documentId,String data) throws InterruptedException {
	               	 try {
	               		this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
	               		String Counts=null;
	               		driver.waitForPageToBeReady();
	               		
	               		bc.waitForElement(getDocExp_CommentsFilter());
	               		getDocExp_CommentsFilter().Click();
	               		doclist.include(data);
	               		bc.waitForElement(doclist.getApplyFilter());
	               		doclist.getApplyFilter().waitAndClick(10);
	               		
	               		driver.waitForPageToBeReady();
	               		List<WebElement> allvalues = getDocumentsName().FindWebElements();
	               		System.out.println(allvalues.size());
	               		int j;
	               		
	               		List<String> values = new ArrayList<String>();
	               		for(j=0;j<10;j++)
	               		{
	               			   driver.waitForPageToBeReady();
	               			   Counts=allvalues.get(j).getText();
	               			   values.add(allvalues.get(j).getText());
	               		}
	               		System.out.println(values);
	               		if(values.retainAll(documentId)){
	               			 bc.passedStep("The Document is displayed successfullly");
	               		}else {
	               		bc.failedStep("The Document is not displayed");
	               		}
	               		
	               	} catch (Exception e) {
	               		e.printStackTrace();
	               		bc.failedStep("Exception occured while verifying the presence of documents after filling document_comments section ."+e.getMessage());
	               	}
               	
               }
                                      
               /**     
                * @author Gopinath
                * @Description : Method for navigating to doc Explorer page.
                */               
                public void navigateToDocExplorerURL() {
                	try {
                		driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
                	}catch(Exception e) { 
                		e.printStackTrace();
                		bc.failedStep("Exception occured while navigating to doc explorer url is failed"+e.getMessage());
                	}
                }               
                /**
             	 * @Author:jayanthi
             	 * 
             	 */
             	public void docExp_BulkAssign() {
             		driver.waitForPageToBeReady();
             		bc.waitForElement(getDocExp_actionButton());
             		//getDocExp_actionButton().ScrollTo();
             		getDocExp_actionButton().waitAndClick(15);
             		bc.waitForElement(getDocExp_BulkAssign());
             		getDocExp_BulkAssign().waitAndClick(15);
             	}
         	/**     
             * @author Gopinath
             * @Description : Method for navigating To DocView From Doc Explorer.
             */         
         	public void navigateToDocViewFromDocExplorer() {
         		try {
         			getDocExp_actionButton().isElementAvailable(15);
	             	getDocExp_actionButton().waitAndClick(10);
	             	getDocExp_actionButton().isElementAvailable(15);
	             	getDocListAction().waitAndClick(20);
         		}catch(Exception e) {
         			e.printStackTrace();
            		bc.failedStep("Exception occured while navigating To DocView From Doc Explorer is failed"+e.getMessage());
            	}
         	}
         	
        	/**     
             * @author Gopinath
             * @Description : Method for navigating To DocView From Doc Explorer.
             * @param emailSubject : emailSubject is string value that email subject value.
             * @param masterDate : masterDate is string value that master date value.
             */         
         	public void masterDateWithEmailSubject(String emailSubject,String masterDate) {
         		try {
         			getEmailSubjectTextField().isElementAvailable(15);
         			getEmailSubjectTextField().SendKeys(emailSubject);
         			getEmailSubjectTextField().getWebElement().sendKeys(Keys.ENTER);
         			getMasterDatetextField().isElementAvailable(15);
         			getMasterDatetextField().SendKeys(masterDate);
         			getMasterDatetextField().getWebElement().sendKeys(Keys.ENTER);
         		}catch(Exception e) {
         			e.printStackTrace();
            		bc.failedStep("Exception occured while navigating To DocView From Doc Explorer is failed"+e.getMessage());
            	}
         	}
         	

        	/**
        	*@author Gopinath
        	*@description : Method to navigate doc explorer page.
        	*/
        	public void navigateToDocExplorerPage() {
        		try {
        			driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
        		}catch(Exception e) {
        			e.printStackTrace();
        			bc.failedStep("Exception occcured while navigating doc explorer page."+e.getMessage());
        			
        		}
        	}	
        	
        	  /**
             * @author Gopinath
             * @Description : Method for selecting Documents.
             */
             public void selectDocument(int noofDocuments){
            	 	try {
                		 for (int D=0;D<noofDocuments;D++) {
                    		 driver.waitForPageToBeReady();
                    		 bc.waitForElement(getDocumentsCheckBoxbyRowNum(D+1));
                    		 getDocumentsCheckBoxbyRowNum(D+1).waitAndClick(10);
                		 }
            	 	} catch (Exception e) {
                		e.printStackTrace();
                		bc.failedStep("Exception occcured while selecting the document ."+e.getMessage());
                	}
            	}
             
             /**
         	*@author Gopinath
         	*@description : Method to verify doc list header.
         	*/
         	public void verifyDocListHeader() {
         		try {
         			getDocListHeader().isElementAvailable(10);
         			if(getDocListHeader().isDisplayed()) {
         				bc.passedStep("Navigated to doclist successfully");
         			}else {
         				bc.failedStep("Navigating to doclist is failed");
         			}
         		}catch(Exception e) {
         			e.printStackTrace();
         			bc.failedStep("Exception occcured while verify doc list header."+e.getMessage());
         			
         		}
         	}
         	
         	/**     
             * @author Gopinath
             * @Description : Method for entering file name in file name filter.
             */         
         	public void enterFileNameInFileNameFilter(String fileName) {
         		try {
         			getEmailSubjectTextField().isElementAvailable(15);
         			getEmailSubjectTextField().SendKeys(fileName);
         			getEmailSubjectTextField().getWebElement().sendKeys(Keys.ENTER);
         		}catch(Exception e) {
         			e.printStackTrace();
            		bc.failedStep("Exception occured while entering file name in file name filter."+e.getMessage());
            	}
         	}
         	/**     
             * @author Gopinath
             * @Description : Method for navigating from doc explorer to doc list.
             */ 
         	 public void docExplorerToDocList()
         	  {
         	  	this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
         	  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
         	  			getDocExp_SelectAllDocs().Visible()  ;}}), Input.wait30); 
         	  	getDocExp_SelectAllDocs().waitAndClick(10);
         	  	
         	  	bc.waitTime(2);
         	  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
         	  			doclist.getPopUpOkBtn().Visible()  ;}}), Input.wait30); 
         	  	doclist.getPopUpOkBtn().Click();
         		bc.waitTime(3);
         		getDocExp_actionButton().isElementAvailable(10);
         	  	 getDocExp_actionButton().waitAndClick(10);
         	  		
         		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
         				 getDocListAction().Visible()  ;}}), Input.wait60); 
         		bc.waitTime(2);
         		getDocListAction().isElementAvailable(10);
         		 getDocListAction().waitAndClick(20);
         		 try{
         			 if(bc.getYesBtn().isDisplayed()) {
         				bc.getYesBtn().waitAndClick(10);
         			 }
         			}catch (Exception e) {
         				// TODO: handle exception
         			}
         		 System.out.println("Navigated to doclist, to view docslist");
         		 UtilityLog.info("Navigated to doclist, to view docslist");
         		
         	  }
         	 
         	 
         	/**
         	 * @author Gopinath
         	 * @Description:methoad to select folder from tree to view docs in doc list
         	 * @param folderNumber
         	 */
         	public void selectFolder(String folderNumber) {
         		try {
         		driver.waitForPageToBeReady();
         		bc.waitForElement(getfolderFromTreeByNumber(folderNumber));
         		getfolderFromTreeByNumber(folderNumber).waitAndClick(3);
         		if(getfolderFromTreeByNumber(folderNumber).GetAttribute("class").contains("clicked")) {
         			bc.passedStep("folder selected successfully");
         		}else {
         			bc.failedStep("Unable to select the folder ");
         		}
         		}catch(Exception e) {
         			e.printStackTrace();
         			bc.failedStep("Exception occured while selecting the folder to view docs");
         		}
         		
         	}

         /**
         	 * @author Gopinath
         	 * @Description: method to verify docList displayed selected from folder in a tree
         	 * @param folderNumber
         	 */
         	public void verifyDocList(String folderNumber) {
         		try {
         			selectFolder(folderNumber);

         			driver.waitForPageToBeReady();
         			String custodianNameInTable = getDocListCustodianName().getText();

         			bc.waitForElement(getfolderFromTreeByNumber(folderNumber));
         			String CustodianNameInTree = getfolderFromTreeByNumber(folderNumber).getText();
         			String numberOfDocumentInFolder = CustodianNameInTree.substring(CustodianNameInTree.indexOf("(") + 1,CustodianNameInTree.indexOf(")"));
         					
         			bc.waitForElementCollection(getDocListInPage());
         			int numberOfDocumentsInPage = getDocListInPage().FindWebElements().size();
         			int PaginationCount = getDocLictPaginationCount().size();

         			if (PaginationCount > 3) {
         				for (int i = 1; i <= PaginationCount - 3; i++) {
         					bc.waitForElement(getDocLictPaginationNextButton());
         					getDocLictPaginationNextButton().waitAndClick(3);
         					driver.waitForPageToBeReady();
         					int numberOfDocumentsInNextPage = getDocListInPage().FindWebElements().size();
         					numberOfDocumentsInPage = numberOfDocumentsInPage + numberOfDocumentsInNextPage;
         				}

         			}
         			if (CustodianNameInTree.contains(custodianNameInTable)) {
         				if (Integer.parseInt(numberOfDocumentInFolder) == numberOfDocumentsInPage) {
         					bc.passedStep(
         							"List View presents the documents corresponding to the folder(s) selected in the tree view");
         				} else {
         					bc.failedStep("All documents corresponding to the folder(s) are not displated in doc list");
         				}
         			} else {
         				bc.failedStep("corresponding  folder document are not displayed in doc list");
         			}
         		} catch (Exception e) {
         			e.printStackTrace();
         			bc.failedStep("Exception occured while verifying docExplorer docList due to " + e.getMessage());
         		}
         	}
 }