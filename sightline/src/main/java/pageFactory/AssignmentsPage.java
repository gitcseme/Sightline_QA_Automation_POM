package pageFactory;


import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.ArrayList;

import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import testScriptsSmoke.Input;

public class AssignmentsPage {

    Driver driver;
    Element element;
    BaseClass bc;
    SoftAssert assertion;
    SessionSearch search;
    DocViewPage docview ;
    
   
    public Element getAssignmentActionDropdown(){ return driver.FindElementByXPath("//*[@id='ulActions']/../button[@class='btn btn-defualt dropdown-toggle']"); }
    public Element getAssignmentAction_NewAssignment(){ return driver.FindElementByXPath("//*[@id='ulActions']//a[text()='New Assignment']"); }
    public Element getAssignmentAction_EditAssignment(){ return driver.FindElementByXPath("//*[@id='ulActions']//a[text()='Edit Assignment']"); }
    public Element getAssignmentAction_DeleteAssignment(){ return driver.FindElementByXPath("//*[@id='ulActions']//a[text()='Delete Assignment']"); }
    public Element getAssignmentName(){ return driver.FindElementById("AssignmentName"); }
    public Element getsampleMethod(){ return driver.FindElementById("sampleMethod"); }
    public Element getCountToAssign(){ return driver.FindElementById("txtCountToAssign"); }
    public Element getParentAssignmentGroupName(){ return driver.FindElementById("ParentassignmentGroupName"); }
    public Element getSelectedClassification(){ return driver.FindElementById("SelectedClassification"); }
    public Element getAssignmentCodingFormDropDown(){ return driver.FindElementById("SelectedCodingForm"); }
    public Element getAssignmentSaveButton(){ return driver.FindElementByXPath("//input[@value='Save']"); }
    public Element getSelectAssignmentToBulkAssign(String assignmentName){ return driver.FindElementByXPath("//*[@id='jstreeComplete']//a[text()='"+assignmentName+"']"); }
    public Element getStartingCount(){ return driver.FindElementByXPath("(//div[@class='col-md-3 bulkActionsSpanLoderTotal'])[2]"); }
    public Element getContinueBulkAssign(){ return driver.FindElementByXPath("//*[@id='divBulkAction']//button[contains(.,'Continue')]"); }
    public Element getFinalCount(){ return driver.FindElementByXPath("//span[@id='spanTotal']"); }
    public Element getFinalizeButton(){ return driver.FindElementById("btnfinalizeAssignment"); }
    public Element getNumberOfAssignmentsToBeShown(){ return driver.FindElementByXPath("//*[@id='GridAssignment_length']/label/select"); }
    public Element getSelectAssignment(String assignmentName){ return driver.FindElementByXPath("//*[@id='GridAssignment']/tbody//tr[td='"+assignmentName+"']"); }
    //change this name to pop or smtg
    public Element getAssignment_Actionlikepopup(){ return driver.FindElementByXPath("//span[@class='ui-dialog-title']/div"); }
    
    public Element getAssignment_ManageReviewersTab(){ return driver.FindElementByXPath("//*[contains(text(),'Manage Reviewers')]"); }
    
    public Element getSelectAssignmentDocCount(String assignmentName,int colno){ return driver.FindElementByXPath("//*[@id='GridAssignment']/tbody//tr/td[.='"+assignmentName+"']/following-sibling::td["+colno+"]"); }
    public Element getAssgnCounts(String assignmentName,int colno){ return driver.FindElementByXPath("//*[@id='GridAssignment']/tbody//tr/td[.='"+assignmentName+"']/following-sibling::td["+colno+"]"); }

    public Element getPersistCB_ExistAssgn(){ return driver.FindElementByXPath("//div[@id='existingassignment']//label[@class='checkbox']/i"); }
    public Element getPersistCB_NewAssgn(){ return driver.FindElementByXPath("//div[@id='newassignmentdiv']//label[@class='checkbox']/i"); }
    public Element getSelectSavePermission(){ return driver.FindElementByXPath("(//label[@class='toggle'])[23]"); }
    
    //Assign users to assignment
    public Element getAddReviewersBtn(){ return driver.FindElementById("btnShowPopup"); }
    public Element getSelectUserToAssign(){ return driver.FindElementByXPath("//*[@id='divNotAssignedUsers']//div[contains(.,'"+Input.rev1userName+"')]/../div/label"); }
    public Element getAdduserBtn(){ return driver.FindElementById("btnAddReviewer"); }
    
    public Element getDistributeTab(){ return driver.FindElementById("ui-id-3"); }
    public Element getSelectUserInDistributeTab(){ return driver.FindElementByXPath("//*[@id='divDistributedDocUsers']//div[contains(.,'"+Input.rev1userName+"')]/div/label"); }
    public Element getDistributeBtn(){ return driver.FindElementById("btnDistribute"); }
    
    public Element getBulkAssign_NewAssignment(){ return driver.FindElementById("tabNew"); }
  //added by shilpi on 04-01
    public Element getAssignmentAction_ViewinDocView(){ return driver.FindElementByXPath("//a[text()='View All Docs In DocView']"); }
    public Element getSelectAssignmentAsReviewer(String assignmentName){ return driver.FindElementByXPath(".//*[@id='dt_basic']//strong[contains(.,'"+assignmentName+"')]"); }
    public Element getAssignmentAction_ViewinDocList(){ return driver.FindElementByXPath("//a[text()='View All Docs in DocList']"); }
     
    //added on 06/03
    public Element getAssignment_Edit_DisplayDocumentHistory(){ return driver.FindElementByXPath(".//*[@id='CascadingDivision']//label[contains(.,'Document History Tab')]/i"); }
    public Element getAssignment_Edit_ApplyRedactionbyreviewer(){ return driver.FindElementByXPath(".//*[@id='CascadingDivision']//label[contains(.,'Allow reviewers to apply redactions')]/i"); }
    public Element getAssignment_Edit_CodingStampApplied(){ return driver.FindElementByXPath(".//*[@id='CascadingDivision']//label[contains(.,'Coding Stamp Applied')]/i"); }
    public Element getAssignment_BackToManageButton(){ return driver.FindElementByXPath("//a[contains(.,'Back to Manage')]"); }
    
    //added by shilpi on 8/5/2019
    public Element getAssgn_DocSequenceblock(){ return driver.FindElementById("doc-seq"); }
    public Element getAssgn_DocSequencelabel(){ return driver.FindElementByXPath("//*[@id='doc-seq']/div[1]/label"); }
    public Element getAssgn_DocSequence_SortbyMetadata(){ return driver.FindElementByXPath("//a[contains(text(),'Sort by Metadata')]"); }
    public Element getAssgn_DocSequence_Selectmetadata(){ return driver.FindElementById("ddlMetaDataField"); }
    public Element getAssgn_ManageRev_Action(){ return driver.FindElementById("btnDropdown"); }
    public Element getAssgn_ManageRev_Action_ViewDocview(){ return driver.FindElementById("View in Doc View"); }
    public Element getAssgn_ManageRev_Action_Unassignuser(){ return driver.FindElementById("Unassign User"); }
    public Element getAssgn_ManageRev_Action_removedoc(){ return driver.FindElementById("Remove Documents"); }
    public Element getAssgn_ManageRev_Action_redistributedoc(){ return driver.FindElementById("Redistribute Documents"); }
    public Element getAssgn_ManageRev_Action_tagdoc(){ return driver.FindElementById("Tag Documents"); }
    public Element getAssgn_ManageRev_Action_folderdoc(){ return driver.FindElementById("Folder Documents"); }
    public Element getAssgn_ManageRev_Action_ViewDoclist(){ return driver.FindElementById("View in Doc List"); }
    public ElementCollection getDashboadAssgn(){ return driver.FindElementsByXPath("//*[@id='dt_basic']/tbody/tr"); }
    public Element getAssgn_ManageRev_selectrev(){ return driver.FindElementByXPath(".//*[@id='dt_basic']//td[contains(.,'"+Input.rev1userName+"')]"); }
    public Element getAssgnAction_Export(){ return driver.FindElementById("rbnExport"); }
    public Element getAssgn_LikeDoc_Family(){ return driver.FindElementByXPath("//*[@class='light-bg toggleSwitch']//div[contains(text(),'Family Members')]"); }
    public Element getAssgn_LikeDoc_Email(){ return driver.FindElementByXPath("//*[@class='light-bg toggleSwitch']//div[contains(text(),'Email Threaded')]"); }
    public Element getAssgn_LikeDoc_Near(){ return driver.FindElementByXPath("//*[@class='light-bg toggleSwitch']//div[contains(text(),'Near Duplicate')]"); }
    public Element getAssgGrptionDropdown(){ return driver.FindElementByXPath("//*[@id='collapseOne']//button[@class='btn btn-defualt dropdown-toggle']"); }
    public Element getAssgnGrp_Create(){ return driver.FindElementById("creategroup"); }
    public Element getAssgnGrp_Edit(){ return driver.FindElementById("editgroup"); }
    public Element getAssgnGrp_Delete(){ return driver.FindElementById("deletegroup"); }
    public Element getAssgnGrp_Select(String assgname){ return driver.FindElementByXPath("//a[contains(text(),'"+assgname+"')]"); }
    public Element getAssgnGrp_Create_DrawPooltoggle(){ return driver.FindElementByXPath("//*[@id='IsDrawFromPool']/following-sibling::i"); }
    public Element getAssgnGrp_Create_DrawPoolCount(){ return driver.FindElementById("DrawFromPoolCount"); }
    public Element getAssgnGrp_Create_Keepfamilycount(){ return driver.FindElementById("KeepFamiliesTogetherCount"); }
    public Element getAssgn_DocSequenceblock_avacriteria(int i){ return driver.FindElementByXPath("//*[@class='dd-list']/li["+i+"]"); }
    public Element getAssgn_DocSequenceblock_LiveSeq(){ return driver.FindElementByXPath(".//*[@class='dd-empty']"); }
    public Element getbulkassgnpopup(){ return driver.FindElementByXPath("//*[text()='Assign/Unassign Documents']"); }
    public Element getSelect2ndUserToAssign(){ return driver.FindElementByXPath("//*[@id='divNotAssignedUsers']//div[contains(.,'"+Input.rmu1userName+"')]/../div/label"); }
    public Element getSelect2ndUserInDistributeTab(){ return driver.FindElementByXPath("//*[@id='divDistributedDocUsers']//div[contains(.,'"+Input.rmu1userName+"')]/div/label"); }
    public Element getEditAggn_Distribute_Totaldoc(){ return driver.FindElementById("lblTotalDocuments"); }
    public Element getEditAggn_Distribute_Unassgndoc(){ return driver.FindElementById("lblUnAssignedDoc"); }
    public Element getAssgn_ManageRev_DisDoc(int rowno){ return driver.FindElementByXPath("//*[@id='dt_basic']/tbody/tr[1]/td["+rowno+"]"); }
    public Element getAssgnnameErrormsg(){ return driver.FindElementByCssSelector(".field-validation-error span"); }
    public Element getUnassignbutton(){ return driver.FindElementByXPath("//*[@id='toUnassign']/following-sibling::i"); }
    public Element getAssignmentAction_Completedoc(){ return driver.FindElementByXPath("//a[contains(text(),'Complete All Documents')]"); }
    public Element getAssgn_Redistributepopup(){ return driver.FindElementByXPath("//*[@name='Users']/following-sibling::i"); }
    public Element getAssgn_Redistributepopup_save(){ return driver.FindElementById("btnShareSave"); }
    public Element getAssgn_DocSequence_avacrtiera(int no){ return driver.FindElementByCssSelector(".dd-list li:nth-of-type("+no+")"); }
    public Element getAssgn_DocSequence_liveseq(){ return driver.FindElementByCssSelector(".live-seq"); }
    public Element getAssignmentAction_CopyAssignment(){ return driver.FindElementById("CopyAssignmentText"); }
    public Element getSelectCopyAssignment(){ return driver.FindElementByXPath("//*[@id='GridAssignment']/tbody//tr[contains(.,'Copy')]/td[1]"); }
    public ElementCollection getSelectcopyAssgnmToBulkAssign(){ return driver.FindElementsByXPath("//*[@id='jstreeComplete']//a[starts-with(.,'')]"); }
    public ElementCollection getSelectcopyAssgnmToBulkUnAssign(){ return driver.FindElementsByXPath("//*[@id='jstreeUnAssign']//a[starts-with(.,'')]"); }
    public Element getAssgn_permissions(int no){ return driver.FindElementByXPath("(//label[@class='toggle'])["+no+"]//i"); }
    public Element getAssgngrp_CascadeSetting(){ return driver.FindElementByXPath(".//*[@id='IsCascadeEnabled']/following-sibling::i"); }
    public Element getrev_assgnprogress(String assignmentName){ return driver.FindElementById(".//td[contains(.,'"+assignmentName+"')]//following::span[starts-with(@id,'AssignmentProgress')]"); }
    public Element getrev_batchprogress(String assignmentName){ return driver.FindElementById(".//td[contains(.,'"+assignmentName+"')]//following::div[starts-with(@class,'progress-bar bg-color-green')]"); }
    // public Element getAssignmentAction_Completedoc(){ return driver.FindElementById("CompleteAllDocuments"); }
    
    //Quick Batch added by shilpi
    public Element getQuickBatchpopup(){ return driver.FindElementByXPath("//span[contains(text(),'Quick Batch')]"); }
  //  public Element getQuickBatch_optimizedorder(){ return driver.FindElementByXPath("//*[@id='optimized']/following-sibling::i"); }
    public Element getQuickBatch_chornologicorder(){ return driver.FindElementByXPath(".//*[@id='chronological']/following-sibling::i"); }
    public Element getQuickBatch_Doccount(){ return driver.FindElementByXPath("//label[text()='Selected Documents']/preceding-sibling::div"); }
    public Element getQuickBatch_Selectallrev(){ return driver.FindElementByXPath("//*[@id='chkAll']/following-sibling::i"); }
    public Element getAssgn_LikeDoc_Familytoggle(){ return driver.FindElementByXPath("//*[@id='chkIncludeFamilyMemeber']/following-sibling::i"); }
    public Element getAssgn_LikeDoc_Emailtoggle(){ return driver.FindElementByXPath("//*[@id='chkIncludeEmailThreads']/following-sibling::i"); }
    public Element getAssgn_LikeDoc_Neartoggle(){ return driver.FindElementByXPath("//*[@id='chkIncludeNearDuplicates']/following-sibling::i"); }
    public Element getAddViewers(){ return driver.FindElementByXPath("//div[@id='divReviewers']"); }
    public Element getSelectUserToAssign(String userName){ return driver.FindElementByXPath("//*[@id='divNotAssignedUsers']//div[2][contains(.,'"+userName+"')]"); }
    public Element getQuickBatch_NameErrormsg(){ return driver.FindElementByXPath("//*[@id='assignmentName']/span/span"); }
    public Element getQuickBatch_CodingErrormsg(){ return driver.FindElementByXPath("//*[@id='codingForm']/span/span"); }
    
    public Element getQuickBatch_optimizedorder(){ return driver.FindElementById("optimized"); }
    public Element getAssgn_ManageRev_revdoccount(){ return driver.FindElementByXPath(".//*[@id='dt_basic']//td[contains(.,'"+Input.rev1userName+"')]/following-sibling::td[2]"); }
    
    //Keywords element added by shilpi
    public Element getAssgn_Keywordsbutton(){ return driver.FindElementById("btnKeywords"); }
    public Element getAssgn_Keywordspopup(){ return driver.FindElementById("divkeyword"); }
    public ElementCollection getAssgn_AllKeywords(){ return driver.FindElementsByXPath("//*[@id='assKeywordsListDiv']//div/div[1]"); }
    public ElementCollection getAssgn_AllKeywordscheck(){ return driver.FindElementsByName("assKeywordsList"); }
     
    public Element getAssgn_Keywordokbutton(){ return driver.FindElementById("keywordOK"); }
    public Element getBulkUnFolderbutton(){ return driver.FindElementByXPath("//*[@id='toUnfolder']/following-sibling::i"); }
    public Element getContinueCount(){ return driver.FindElementByXPath("//div[@class='bulkActionsSpanLoderTotal']"); }
    public Element getContinueButton(){ return driver.FindElementByXPath(".//*[@id='divBulkAction']//button[contains(.,'Continue')]"); }
    public Element getSelectFolderExisting(String foldername){ return driver.FindElementByXPath("//*[@id='divBulkFolderJSTree']//a[contains(.,'"+foldername+"')]/i[1]"); }
    public Element getPanel() { return driver.FindElementByXPath("//*[@id='content']/div[3]");}
    
    public AssignmentsPage(Driver driver){

        this.driver = driver;
        bc = new BaseClass(driver);
        this.driver.getWebDriver().get(Input.url+ "Assignment/ManageAssignment");
        driver.waitForPageToBeReady();
        //This initElements method will create all WebElements
        assertion = new SoftAssert();
        search = new SessionSearch(driver);
        docview = new DocViewPage(driver);//

    }

    public void createAssignment(String assignmentName, String codingForm) throws InterruptedException {
    	this.driver.getWebDriver().get(Input.url+ "Assignment/ManageAssignment");
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignmentActionDropdown().Visible() ;}}), Input.wait60);
    	Thread.sleep(2000);
    	getAssignmentActionDropdown().waitAndClick(10);
    	
     	getAssignmentAction_NewAssignment().WaitUntilPresent();
    	Thread.sleep(2000);
    	getAssignmentAction_NewAssignment().waitAndClick(20);
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getAssignmentName().Visible()  ;}}), Input.wait60);
    	getAssignmentName().SendKeys(assignmentName);
    	getParentAssignmentGroupName().Displayed();
    	getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
    	try {
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getAssignmentCodingFormDropDown().Visible()  ;}}), Input.wait60);
       	getAssignmentCodingFormDropDown().selectFromDropdown().selectByVisibleText(codingForm);
    	}
    	catch (Exception e) {
    		getAssignmentCodingFormDropDown().selectFromDropdown().selectByIndex(1);
    	} 
    	
    	//permissions
    	driver.scrollingToBottomofAPage();
    	driver.scrollingToBottomofAPage();
    	Thread.sleep(2000);
    	//give redaction permission to reviewers
    	driver.getWebDriver().findElement(By.xpath("(//div[@class = 'smart-form'])[25]")).click();
    	Thread.sleep(2000);
    	getSelectSavePermission().ScrollTo();
    	getSelectSavePermission().waitAndClick(5);
    	driver.scrollPageToTop();
    	//getAssignmentCodingFormDropDown().selectFromDropdown().selectByVisibleText(codingForm);
    	getAssignmentSaveButton().waitAndClick(5);
    	System.out.println("Assignment "+assignmentName+" created with CF "+codingForm);
    	UtilityLog.info("Assignment "+assignmentName+" created with CF "+codingForm);
    	 
	}
    
    public void assignDocstoExisting(final String assignmentName) {
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getStartingCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60);
       	
    	getPersistCB_ExistAssgn().waitAndClick(5);
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSelectAssignmentToBulkAssign(assignmentName).Visible()  ;}}), Input.wait60);
       	
    	System.out.println(getSelectcopyAssgnmToBulkAssign().FindWebElements().size());
    	UtilityLog.info(getSelectcopyAssgnmToBulkAssign().FindWebElements().size());
		for (WebElement iterable_element : getSelectcopyAssgnmToBulkAssign().FindWebElements()) {
			
			if(iterable_element.getText().contains(assignmentName)){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
				driver.scrollingToBottomofAPage();
		
				iterable_element.click();
			}
		}
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getContinueBulkAssign().Visible()  ;}}), Input.wait60);
    	getContinueBulkAssign().waitAndClick(10);
    
       	final BaseClass bc = new BaseClass(driver);
        final int Bgcount = bc.initialBgCount();
        
       	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFinalCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60);
       	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
       			getAssignment_Actionlikepopup().Visible();}}), Input.wait30);
       	
       	
       	String assignpop = getAssignment_Actionlikepopup().getText();
       	System.out.println(assignpop);
       	UtilityLog.info(assignpop);
       	Assert.assertEquals("Action Like Documents" , assignpop);
       	
       	getAssgn_LikeDoc_Family().Displayed();
       	Assert.assertEquals("Family Members" , getAssgn_LikeDoc_Family().getText());
       	Assert.assertEquals("Email Threaded Docs" , getAssgn_LikeDoc_Email().getText());
       	Assert.assertEquals("Near Duplicates" , getAssgn_LikeDoc_Near().getText());
       	
        
    	getFinalizeButton().Click();
    	bc.VerifySuccessMessage("Bulk Assign has been added to background process. You will get notification on completion.");
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	 			bc.initialBgCount() == Bgcount+1  ;}}), Input.wait60);
    	
    	System.out.println("Docs assigned to  "+assignmentName);
    	UtilityLog.info("Docs assigned to  "+assignmentName);
	}
    
    public void editAssignment(final String assignmentName) throws InterruptedException {
    	driver.getWebDriver().get(Input.url+ "Assignment/ManageAssignment");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getNumberOfAssignmentsToBeShown().Visible()  ;}}), Input.wait60);
    	
    	getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
    	
    	
    	driver.scrollingToBottomofAPage();
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSelectAssignment(assignmentName).Visible()  ;}}), Input.wait60);
    	driver.scrollingToBottomofAPage();
    
    	getSelectAssignment(assignmentName).waitAndClick(5);;
    	driver.scrollPageToTop();
    	
    	getAssignmentActionDropdown().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignmentAction_EditAssignment().Visible()  ;}}), Input.wait60);
        Thread.sleep(2000);
    	getAssignmentAction_EditAssignment().waitAndClick(3);
    	
	}
    
      public void addReviewerAndDistributeDocs(String assignmentName,int docCount) throws InterruptedException {

    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignment_ManageReviewersTab().Visible()  ;}}), Input.wait60);
    	getAssignment_ManageReviewersTab().waitAndClick(30);
    	
    	getAddReviewersBtn().waitAndClick(10);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSelectUserToAssign().Visible()  ;}}), Input.wait60);
    	getSelectUserToAssign().waitAndClick(10);
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAdduserBtn().Visible()  ;}}), Input.wait60);
    	getAdduserBtn().waitAndClick(10);
    	
    	bc.VerifySuccessMessage("Record saved successfully");
    	getDistributeTab().waitAndClick(20);
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSelectUserInDistributeTab().Visible()  ;}}), Input.wait60);
    	getSelectUserInDistributeTab().waitAndClick(20);
    	
    	getDistributeBtn().waitAndClick(15);
    	
    	getAssignment_BackToManageButton().waitAndClick(10);
    	Thread.sleep(2000);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getNumberOfAssignmentsToBeShown().Visible()  ;}}), Input.wait60);
    	
    	getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
    	
    	
    	driver.scrollingToBottomofAPage();
    	WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 10L);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id='GridAssignment']/tbody//tr[td[text()='"+assignmentName+"']]")));
    	
    	getAssgnCounts(assignmentName, 9);
    	//verify total docs count
    	String acttotalcount = getAssgnCounts(assignmentName, 9).getText();
    	System.out.println(Integer.parseInt(acttotalcount));
    	UtilityLog.info(acttotalcount);
    	assertion.assertEquals(docCount, Integer.parseInt(acttotalcount));
    	
    	//verify distributed docs count
    	String actdistributedcount = getAssgnCounts(assignmentName, 9).getText();
    	System.out.println(Integer.parseInt(actdistributedcount));
    	UtilityLog.info(Integer.parseInt(actdistributedcount));
    //	assertion.assertEquals(docCount, Integer.parseInt(actdistributedcount));
    //	assertion.assertAll();
     	
	}
    
    public void assignDocstoNewAssgn(final String assignmentName,String codingForm,int purehits) {
      	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getBulkAssign_NewAssignment().Visible()  ;}}), Input.wait60);
    	getBulkAssign_NewAssignment().waitAndClick(20);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getbulkassgnpopup().Visible()  ;}}), Input.wait60);
    	Assert.assertTrue(getbulkassgnpopup().Displayed());
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getPersistCB_NewAssgn().Visible()  ;}}), Input.wait60);
    	getPersistCB_NewAssgn().waitAndClick(15);
   	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getContinueBulkAssign().Visible()  ;}}), Input.wait60);
        getContinueBulkAssign().waitAndClick(15);
  
     	final BaseClass bc = new BaseClass(driver);
      final int Bgcount = bc.initialBgCount();
      
      driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		  getFinalizeButton().Visible()  ;}}), Input.wait60);
      getFinalizeButton().waitAndClick(Input.wait30);
      
      driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			 getAssignmentName().Visible()  ;}}), Input.wait60);
  	getAssignmentName().SendKeys(assignmentName);
  	
  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getParentAssignmentGroupName().Visible()  ;}}), Input.wait60);
  	getParentAssignmentGroupName().Displayed();
  	
  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getSelectedClassification().Visible()  ;}}), Input.wait60);
      getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
 	  try {
 		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 				getAssignmentCodingFormDropDown().Visible()  ;}}), Input.wait60);
    	getAssignmentCodingFormDropDown().selectFromDropdown().selectByVisibleText(codingForm);
 	}
 	catch (Exception e) {
 		getAssignmentCodingFormDropDown().selectFromDropdown().selectByIndex(1);
 	} 
 	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			 getAssgnGrp_Create_DrawPooltoggle().Visible()  ;}}), Input.wait60);
 	  getAssgnGrp_Create_DrawPooltoggle().waitAndClick(10);
 	  
 	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getAssignmentSaveButton().Visible()  ;}}), Input.wait60);  
    getAssignmentSaveButton().waitAndClick(Input.wait30);
 	  System.out.println("Assignment "+assignmentName+" created with CF "+codingForm);
      
   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	 			bc.initialBgCount() == Bgcount+1  ;}}), Input.wait60);
  	
  	System.out.println("Docs assigned to  "+assignmentName);
  	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getNumberOfAssignmentsToBeShown().Visible()  ;}}), Input.wait60);
	
	getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
	
	
	driver.scrollingToBottomofAPage();
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getSelectAssignment(assignmentName).Visible()  ;}}), Input.wait60);
	driver.scrollingToBottomofAPage();

	Assert.assertEquals(purehits,Integer.parseInt(getSelectAssignmentDocCount(assignmentName,7).getText()));
	
     }
    
    
    public void SelectAssignmentToViewinDocview(final String assignmentName) {
   	driver.getWebDriver().get(Input.url+ "Assignment/ManageAssignment");
   	
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getNumberOfAssignmentsToBeShown().Visible()  ;}}), Input.wait60);
   	
   	getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
   	
   	
   	driver.scrollingToBottomofAPage();
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getSelectAssignment(assignmentName).Visible()  ;}}), Input.wait60);
   	driver.scrollingToBottomofAPage();
   	
   	getSelectAssignment(assignmentName).waitAndClick(5);;
   	driver.scrollPageToTop();
   	
   	getAssignmentActionDropdown().Click();
   	
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getAssignmentAction_ViewinDocView().Visible()  ;}}), Input.wait60);
   	Assert.assertTrue(getAssgnAction_Export().Displayed());
   	getAssignmentAction_ViewinDocView().waitAndClick(3);
   	
	}
    
    public void SelectAssignmentByReviewer(final String assignmentName) {
    	driver.getWebDriver().get(Input.url+ "ReviewerDashboard/Index");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSelectAssignmentAsReviewer(assignmentName).Visible()  ;}}), Input.wait30);
    	
    	getSelectAssignmentAsReviewer(assignmentName).waitAndClick(10);
    	
	}
  public void AssgnToggleButtons() throws InterruptedException  {
    	
    	driver.scrollingToBottomofAPage();

    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignment_Edit_DisplayDocumentHistory().Visible()  ;}}), Input.wait60);
    	getAssignment_Edit_DisplayDocumentHistory().Click();
    	
    	getAssignment_Edit_CodingStampApplied().waitAndClick(10);
    	
    	getAssignment_Edit_ApplyRedactionbyreviewer().waitAndClick(10);
    	
    	driver.scrollPageToTop();
    	
    	getAssignmentSaveButton().waitAndClick(10);
   		Thread.sleep(2000);
	}
  
  public void Assgnwithdocumentsequence() throws InterruptedException
  {
	  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getAssgn_DocSequenceblock().Displayed()  ;}}), Input.wait90);
	  Assert.assertEquals("DOCUMENT PRESENTATION SEQUENCE", getAssgn_DocSequencelabel().getText());
	 
	  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			  getAssgn_DocSequence_liveseq().Displayed()  ;}}), Input.wait60);  
	Element taget = getAssgn_DocSequence_liveseq();
	
	for(int i=1;i<=4;i++)
	{
		taget.Draganddrop(getAssgn_DocSequence_avacrtiera(i),taget);
	  
	}
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getAssgn_DocSequence_SortbyMetadata().Displayed()  ;}}), Input.wait60);
  	getAssgn_DocSequence_SortbyMetadata().waitAndClick(10);
  	
  	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getAssgn_DocSequence_Selectmetadata().Displayed()  ;}}), Input.wait60);
  	getAssgn_DocSequence_Selectmetadata().selectFromDropdown().selectByVisibleText("CustodianName");
  	
  	driver.scrollPageToTop();
  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getAssignmentSaveButton().Displayed()  ;}}), Input.wait60);
  	getAssignmentSaveButton().waitAndClick(10);
	  
  	Thread.sleep(2000);
  	bc.VerifySuccessMessage("Assignment updated successfully");
  	
  	
  	bc.CloseSuccessMsgpopup();

  }
  
   public void Assignment_ManageRevtab_ViewinDocView() {
	   
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignment_ManageReviewersTab().Visible()  ;}}), Input.wait60);
    	getAssignment_ManageReviewersTab().waitAndClick(10);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssgn_ManageRev_revdoccount().Visible()  ;}}), Input.wait60);
    	String count= getAssgn_ManageRev_revdoccount().getText();
    	System.out.println(count);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssgn_ManageRev_selectrev().Visible()  ;}}), Input.wait60);
    	getAssgn_ManageRev_selectrev().waitAndClick(10);
       	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssgn_ManageRev_Action().Visible()  ;}}), Input.wait60);
       	getAssgn_ManageRev_Action().waitAndClick(10);
       	
       	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
       			getAssgn_ManageRev_Action_ViewDocview().Visible()  ;}}), Input.wait60);
       	Assert.assertTrue(getAssgn_ManageRev_Action_ViewDocview().Displayed());
    	
       	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
       			getAssgn_ManageRev_Action_ViewDocview().Visible()  ;}}), Input.wait60);
    	getAssgn_ManageRev_Action_ViewDocview().waitAndClick(10);
    	
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			bc.getYesBtn().Visible()  ;}}), Input.wait60);
    	bc.getYesBtn().waitAndClick(10);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			docview.getDocView_info().Visible()  ;}}), Input.wait60);//
    	String expcount= docview.getDocView_info().getText();
    	
    	Assert.assertTrue(expcount.contains(count));
      
		}
   
   public void Assignment_ManageRevtab_ViewinDoclist() {
	   
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getAssignment_ManageReviewersTab().Visible()  ;}}), Input.wait60);
   	getAssignment_ManageReviewersTab().waitAndClick(10);
   	
   	String count= getAssgn_ManageRev_revdoccount().getText();
	System.out.println(count);
   	
   	getAssgn_ManageRev_selectrev().waitAndClick(10);
      	
     getAssgn_ManageRev_Action().waitAndClick(10);
      	
   	assertion.assertTrue(getAssgn_ManageRev_Action_ViewDoclist().Displayed());
   	
   	getAssgn_ManageRev_Action_ViewDoclist().waitAndClick(10);
   	bc.getYesBtn().waitAndClick(10);
   }
   
   
   public void AssignmentDashboard() {
	   driver.getWebDriver().get(Input.url+ "Dashboard/Dashboard");
	   
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getDashboadAssgn().Visible()  ;}}), Input.wait30);
  
	List<WebElement> assgnlist = new ArrayList<WebElement>();
	assgnlist = getDashboadAssgn().FindWebElements();
	System.out.println("Assign list is"+assgnlist.size());
	
	Assert.assertTrue(assgnlist.size()<=5);

	}
   
   public void Assignment_ManageRevtab_TagFolder(String tagname,String foldername) throws InterruptedException {
	   
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getAssignment_ManageReviewersTab().Visible()  ;}}), Input.wait120);
   	getAssignment_ManageReviewersTab().waitAndClick(20);
   	
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getAssgn_ManageRev_selectrev().Visible()  ;}}), Input.wait30);
   	getAssgn_ManageRev_selectrev().waitAndClick(10);
   	
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getAssgn_ManageRev_Action().Visible()  ;}}), Input.wait30);
   	getAssgn_ManageRev_Action().waitAndClick(10);
   	
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getAssgn_ManageRev_Action_tagdoc().Visible()  ;}}), Input.wait30);
   	getAssgn_ManageRev_Action_tagdoc().waitAndClick(10);
   	
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			bc.getYesBtn().Visible()  ;}}), Input.wait30);
   	bc.getYesBtn().waitAndClick(10);
   	
   	search.BulkActions_Tag(tagname);
   	
   	//bulk folder
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   		 getAssgn_ManageRev_Action().Visible()  ;}}), Input.wait30);
    getAssgn_ManageRev_Action().waitAndClick(20);
   	
    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		getAssgn_ManageRev_Action_folderdoc().Visible()  ;}}), Input.wait30);
   	getAssgn_ManageRev_Action_folderdoc().waitAndClick(10);
   	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			bc.getYesBtn().Visible()  ;}}), Input.wait30);
   	bc.getYesBtn().waitAndClick(10);
   	
   	search.BulkActions_Folder(foldername);
   	assertion.assertAll();
	  
	}
   
   public void Assignment_ManageRevtab_UnTagUnFolder(String tagname,String foldername) throws InterruptedException {
	   
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAssignment_ManageReviewersTab().Visible()  ;}}), Input.wait30);
	   	getAssignment_ManageReviewersTab().waitAndClick(10);
	   	
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAssgn_ManageRev_selectrev().Visible()  ;}}), Input.wait30);
	   	getAssgn_ManageRev_selectrev().waitAndClick(10);
	   	
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAssgn_ManageRev_Action().Visible()  ;}}), Input.wait30);
	   	getAssgn_ManageRev_Action().waitAndClick(10);
	   	
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAssgn_ManageRev_Action_tagdoc().Visible()  ;}}), Input.wait30);
	  	getAssgn_ManageRev_Action_tagdoc().waitAndClick(10);
	   	
	  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	  			bc.getYesBtn().Visible()  ;}}), Input.wait30);
	   	bc.getYesBtn().waitAndClick(10);
	   	
	   	search.bulkUnTag_popUp(tagname);
	   	
	   	//bulk folder
	   	
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAssignment_ManageReviewersTab().Visible()  ;}}), Input.wait30);
	   	getAssignment_ManageReviewersTab().waitAndClick(10);
	   	
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAssgn_ManageRev_selectrev().Visible()  ;}}), Input.wait30);
	   	getAssgn_ManageRev_selectrev().waitAndClick(10);
	   	
	 
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAssgn_ManageRev_Action().Visible()  ;}}), Input.wait30);
	   	getAssgn_ManageRev_Action().waitAndClick(10);
	   	
	   	
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAssgn_ManageRev_Action_folderdoc().Visible()  ;}}), Input.wait30);
	  	getAssgn_ManageRev_Action_folderdoc().waitAndClick(10);
	  	
	  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	  			bc.getYesBtn().Visible()  ;}}), Input.wait30);
	   	bc.getYesBtn().waitAndClick(10);
	   	
	   	search.bulkUnFolder_popUp(foldername);
		  //
		}
   
   public void createAssgnGroup(String assgngrpName) throws InterruptedException {
	   
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getAssgGrptionDropdown().Visible() ;}}), Input.wait60);
//   	Thread.sleep(2000);
   	getAssgGrptionDropdown().waitAndClick(10);
   	
//   	Thread.sleep(2000);
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getAssgnGrp_Create().Visible() ;}}), Input.wait60);
	getAssgnGrp_Create().waitAndClick(20);
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getAssignmentName().Visible()  ;}}), Input.wait60);
   	getAssignmentName().SendKeys(assgngrpName);
    try{
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignmentCodingFormDropDown().Visible()  ;}}), Input.wait60);
    	getAssignmentCodingFormDropDown().Displayed();
    	  Assert.fail();
	     }catch (org.openqa.selenium.NoSuchElementException e) {
	             
	               System.out.println("'getAssignmentCodingFormDropDown' is not displayed");
	 }
    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		getAssgngrp_CascadeSetting().Visible()  ;}}), Input.wait60);
    getAssgngrp_CascadeSetting().waitAndClick(10);
 
    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		getAssgnGrp_Create_DrawPooltoggle().Visible()  ;}}), Input.wait60);
    getAssgnGrp_Create_DrawPooltoggle().waitAndClick(10);
//	Thread.sleep(2000);
    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		getAssgnGrp_Create_DrawPoolCount().Visible()  ;}}), Input.wait60);
	getAssgnGrp_Create_DrawPoolCount().SendKeys("100");
//	Thread.sleep(2000);
	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getAssgnGrp_Create_Keepfamilycount().Visible()  ;}}), Input.wait60);
	//getAssgnGrp_Create_Keepfamilycount().WaitUntilPresent();
	System.out.println(getAssgnGrp_Create_Keepfamilycount().GetAttribute("value"));
	
	Assert.assertEquals("100", getAssgnGrp_Create_Keepfamilycount().GetAttribute("value"));
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getAssignmentSaveButton().Visible()  ;}}), Input.wait60);
   	getAssignmentSaveButton().waitAndClick(5);
   	 
	}
   
   public void EditAssgnGroup(String assgngrpName) throws InterruptedException {
	   
//	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//	   			getAssgGrptionDropdown().Visible() ;}}), Input.wait30);
	   	
	   	getAssgnGrp_Select(assgngrpName).waitAndClick(10);
	   	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAssgGrptionDropdown().Visible() ;}}), Input.wait30);
	   	getAssgGrptionDropdown().waitAndClick(10);
	   	
	   	getAssgnGrp_Edit().WaitUntilPresent();
//	   	Thread.sleep(2000);
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAssgnGrp_Edit().Visible() ;}}), Input.wait30);
	   	getAssgnGrp_Edit().waitAndClick(20);
	   	
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			 getAssignmentName().Visible()  ;}}), Input.wait60);
	   	getAssignmentName().SendKeys(assgngrpName+Utility.dynamicNameAppender());
	   	
	   	try{
	    	getAssignmentCodingFormDropDown().Displayed();
	    	  Assert.fail();
		     }catch (org.openqa.selenium.NoSuchElementException e) {
		             
		               System.out.println("'getAssignmentCodingFormDropDown' is not displayed");
		 }
	   	
	   	for(int k=1;k<=4;k++)
	   	{
	   		Actions action = new Actions(driver.getWebDriver());
	   		action.dragAndDrop(getAssgn_DocSequenceblock_avacriteria(k).getWebElement(),
	   				getAssgn_DocSequenceblock_LiveSeq().getWebElement());
	   		Thread.sleep(1000);
	   	}
	 
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAssignmentSaveButton().Visible()  ;}}), Input.wait60);
	   	getAssignmentSaveButton().waitAndClick(5);
	   	 
		}
   
   public void DeleteAssgnGroup(String assgngrpName) throws InterruptedException {
	   
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAssgnGrp_Select(assgngrpName).Visible() ;}}), Input.wait30);
	   	
	   	getAssgnGrp_Select(assgngrpName).waitAndClick(10);
	   	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAssgGrptionDropdown().Visible() ;}}), Input.wait30);
	   	getAssgGrptionDropdown().waitAndClick(10);
	   	
	   	getAssgnGrp_Delete().WaitUntilPresent();
	   	
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAssgnGrp_Delete().Visible() ;}}), Input.wait30);
	   	getAssgnGrp_Delete().waitAndClick(20);
	   	
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   		 bc.getYesBtn().Visible() ;}}), Input.wait30);
	    bc.getYesBtn().waitAndClick(5);
	    bc.VerifySuccessMessage("Assignment group deleted successfully");
	   	 
		}
  
   public void add2ReviewerAndDistributeDocs() {
	   
	   assertion=new SoftAssert();

   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getAssignment_ManageReviewersTab().Visible()  ;}}), Input.wait60);
   	getAssignment_ManageReviewersTab().Click();
   	
   	getAddReviewersBtn().waitAndClick(10);
   	
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getSelectUserToAssign().Visible()  ;}}), Input.wait60);
   	getSelectUserToAssign().waitAndClick(10);
   	getSelect2ndUserToAssign().waitAndClick(10);
   	
   	getAdduserBtn().Click();
   	bc.VerifySuccessMessage("Record saved successfully");
   	getDistributeTab().Click();
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getSelectUserInDistributeTab().Visible()  ;}}), Input.wait60);
   	
   	assertion.assertEquals(Input.searchString1, getEditAggn_Distribute_Totaldoc().getText());
	
   	getSelectUserInDistributeTab().waitAndClick(20);
   	getSelect2ndUserInDistributeTab().waitAndClick(20);
   	
   	getDistributeBtn().waitAndClick(15);
   	
   	assertion.assertEquals(0, getEditAggn_Distribute_Unassgndoc().getText());
   	
	}
   
   public void RemoveDoc_ReviewerTab(String assignmentName) throws InterruptedException {
	   assertion=new SoftAssert();

	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAssignment_ManageReviewersTab().Visible()  ;}}), Input.wait60);
	   	getAssignment_ManageReviewersTab().Click();
	   	
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAddReviewersBtn().Visible()  ;}}), Input.wait60);
	   	getAddReviewersBtn().waitAndClick(15);
	   	
//	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//	   			getSelectUserToAssign().Visible()  ;}}), Input.wait60);
//	   	getSelectUserToAssign().waitAndClick(10);
	   	
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getSelect2ndUserToAssign().Visible()  ;}}), Input.wait60);
	   	getSelect2ndUserToAssign().waitAndClick(15);
	   	
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAdduserBtn().Visible()  ;}}), Input.wait60);
	   	getAdduserBtn().Click();
//	   	bc.VerifySuccessMessage("Record saved successfully");
	   	
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAssgn_ManageRev_selectrev().Visible()  ;}}), Input.wait60);
	   	getAssgn_ManageRev_selectrev().waitAndClick(10);
	 
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAssgn_ManageRev_Action().Visible()  ;}}), Input.wait60);
        getAssgn_ManageRev_Action().waitAndClick(10);
       	
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		getAssgn_ManageRev_Action_removedoc().Visible()  ;}}), Input.wait60);
      	getAssgn_ManageRev_Action_removedoc().waitAndClick(10);
      	
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			bc.getYesBtn().Visible()  ;}}), Input.wait60);
    	bc.getYesBtn().waitAndClick(10);
//    	bc.VerifySuccessMessage("Record saved successfully");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssgn_ManageRev_DisDoc(4).Visible()  ;}}), Input.wait60);
    	assertion.assertEquals(0,getAssgn_ManageRev_DisDoc(4).getText());
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignment_BackToManageButton().Visible()  ;}}), Input.wait60);
    	getAssignment_BackToManageButton().waitAndClick(15);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getNumberOfAssignmentsToBeShown().Visible()  ;}}), Input.wait60);
    	
    	getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
    	
    	
    	driver.scrollingToBottomofAPage();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssgnCounts(assignmentName, 9).Visible()  ;}}), Input.wait60);
    	
    	//verify distributed and left to do count
    	String actleftcount = getAssgnCounts(assignmentName, 9).getText();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssgnCounts(assignmentName, 6).Visible()  ;}}), Input.wait60);
    	String actassignedcount = getAssgnCounts(assignmentName, 6).getText();
    	assertion.assertEquals(0, Integer.parseInt(actleftcount));
    	assertion.assertEquals(0, Integer.parseInt(actassignedcount));
	   	
		}
   

   public void Assgnwithspecialchars(String data) throws InterruptedException {
	   assertion=new SoftAssert();
	   this.driver.getWebDriver().get(Input.url+ "Assignment/ManageAssignment");
	   
	  driver.waitForPageToBeReady();
	  
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getAssignmentActionDropdown().Visible() ;}}), Input.wait60);
   	getAssignmentActionDropdown().waitAndClick(10);
   	
   	Thread.sleep(2000);
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getAssignmentAction_NewAssignment().Visible() ;}}), Input.wait60);
    	getAssignmentAction_NewAssignment().waitAndClick(10);
    	
   	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getAssignmentName().Visible()  ;}}), Input.wait60);
   	getAssignmentName().SendKeys(data);
   	
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getAssignmentSaveButton().Visible()  ;}}), Input.wait60);
   	getAssignmentSaveButton().waitAndClick(5);
   	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getAssgnnameErrormsg().Visible()  ;}}), Input.wait60);
   	System.out.println(getAssgnnameErrormsg().getText());
   	
   	assertion.assertTrue(getAssgnnameErrormsg().getText().equalsIgnoreCase("Please enter an assignment name without using special characters"));
   }
   
   public void deleteAssignment(final String assignmentName) {
   	driver.getWebDriver().get(Input.url+ "Assignment/ManageAssignment");
   	driver.waitForPageToBeReady();
   	
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getNumberOfAssignmentsToBeShown().Visible()  ;}}), Input.wait60);
   	
   	getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
   	
   	
   	driver.scrollingToBottomofAPage();
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getSelectAssignment(assignmentName).Visible()  ;}}), Input.wait60);
   	driver.scrollingToBottomofAPage();
   
   	getSelectAssignment(assignmentName).waitAndClick(10);;
   	driver.scrollPageToTop();
   	
   	getAssignmentActionDropdown().Click();
   	
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getAssignmentAction_DeleteAssignment().Visible()  ;}}), Input.wait60);
   
   	getAssignmentAction_DeleteAssignment().waitAndClick(10);
   	
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			bc.getYesBtn().Visible()  ;}}), Input.wait60);
	bc.getYesBtn().waitAndClick(5);
	bc.VerifySuccessMessage("Assignment deleted successfully");
	   	 
	}
   
   public void editAssgnwithselectedassgn() {
   	driver.getWebDriver().get(Input.url+ "Assignment/ManageAssignment");
   	
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getNumberOfAssignmentsToBeShown().Visible()  ;}}), Input.wait60);
   	
   	
   	driver.scrollPageToTop();
   	
   	getAssignmentActionDropdown().waitAndClick(10);
   	
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getAssignmentAction_EditAssignment().Visible()  ;}}), Input.wait60);
   
   	getAssignmentAction_EditAssignment().waitAndClick(3);
   	
	}
   
   public void assignDocswithpercentagemethod(final String assignmentName,String samplemethod) {
	   assertion=new SoftAssert();
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getStartingCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60);
   	
   	if(samplemethod.equalsIgnoreCase("Percentage"))
   	{
   		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   				getsampleMethod().Visible()  ;}}), Input.wait60);	
   	getsampleMethod().selectFromDropdown().selectByVisibleText("Percent of Selected Docs");
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getCountToAssign().Visible()  ;}}), Input.wait60);
   	getCountToAssign().SendKeys("10");
   	}
   	else if (samplemethod.equalsIgnoreCase("ParentLevel"))
   	{
   		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   				getsampleMethod().Visible()  ;}}), Input.wait60);
   		getsampleMethod().selectFromDropdown().selectByVisibleText("Parent Level Docs Only");
   	}
   		
   	else if (samplemethod.equalsIgnoreCase("InclusiveEmail"))
   	{
   		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   				getsampleMethod().Visible()  ;}}), Input.wait60);
   		getsampleMethod().selectFromDropdown().selectByVisibleText("Inclusive Email");
   	}
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getPersistCB_ExistAssgn().Visible()  ;}}), Input.wait60);  	
   	getPersistCB_ExistAssgn().waitAndClick(5);
   	
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getSelectAssignmentToBulkAssign(assignmentName).Visible()  ;}}), Input.wait60);
   	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getSelectAssignmentToBulkAssign(assignmentName).Visible()  ;}}), Input.wait60);	
   	getSelectAssignmentToBulkAssign(assignmentName).waitAndClick(20);
   	
   	
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getContinueBulkAssign().Visible()  ;}}), Input.wait60);
   	getContinueBulkAssign().waitAndClick(15);
   
      	final BaseClass bc = new BaseClass(driver);
       final int Bgcount = bc.initialBgCount();
       
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getFinalCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60);
      
   	getFinalizeButton().Click();
   	bc.VerifySuccessMessage("Bulk Assign has been added to background process. You will get notification on completion.");
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	 			bc.initialBgCount() == Bgcount+1  ;}}), Input.wait60);
   	
   	System.out.println("Docs assigned to  "+assignmentName);
   	driver.getWebDriver().get(Input.url+ "Assignment/ManageAssignment");	
   	
   	driver.waitForPageToBeReady();
   	
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getNumberOfAssignmentsToBeShown().Visible()  ;}}), Input.wait60);
	getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
	

   	driver.scrollingToBottomofAPage();
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getAssgnCounts(assignmentName, 7).Visible()  ;}}), Input.wait60);
	//verify total docs count
	String acttotalcount = getAssgnCounts(assignmentName, 7).getText();
	System.out.println(Integer.parseInt(acttotalcount));
	Assert.assertEquals(50, Integer.parseInt(acttotalcount));
	}
   
   public void CompleteAssgn(String assignmentName)throws InterruptedException{
	   	driver.getWebDriver().get(Input.url+ "Assignment/ManageAssignment");
	   	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getNumberOfAssignmentsToBeShown().Visible()  ;}}), Input.wait60);
    	
    	getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
    	
    	driver.scrollingToBottomofAPage();
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSelectAssignment(assignmentName).Visible()  ;}}), Input.wait60);
    	driver.scrollingToBottomofAPage();
    
    	getSelectAssignment(assignmentName).waitAndClick(5);;
    	driver.scrollPageToTop();
    	
    	getAssignmentActionDropdown().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignmentAction_EditAssignment().Visible()  ;}}), Input.wait60);
    
    	Assert.assertTrue(getAssignmentAction_Completedoc().Displayed());
    	
    	getAssignmentAction_Completedoc().waitAndClick(10);
    	bc.getYesBtn().waitAndClick(10);
    	bc.VerifySuccessMessage("All Documents successfully completed.");
    	
    	Assert.assertEquals(0, Integer.parseInt(getAssgnCounts(assignmentName, 10).getText()));
		}
   
   public void UnassignDocsfromAssgn(final String assignmentName) {
	  
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getStartingCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60);
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getUnassignbutton().Visible()  ;}}), Input.wait60); 	
 	getUnassignbutton().waitAndClick(5);
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getSelectAssignmentToBulkAssign(assignmentName).Visible()  ;}}), Input.wait30); 
 	
 	System.out.println(getSelectcopyAssgnmToBulkUnAssign().FindWebElements().size());
	UtilityLog.info(getSelectcopyAssgnmToBulkUnAssign().FindWebElements().size());
	for (WebElement iterable_element : getSelectcopyAssgnmToBulkUnAssign().FindWebElements()) {
		
		if(iterable_element.getText().contains(assignmentName)){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
			driver.scrollingToBottomofAPage();
	
			iterable_element.click();
		}
	}
   	
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getContinueBulkAssign().Visible()  ;}}), Input.wait60);
   	getContinueBulkAssign().waitAndClick(5);
   
      	final BaseClass bc = new BaseClass(driver);
       final int Bgcount = bc.initialBgCount();
       
     	bc.VerifySuccessMessage("Bulk UnAssign has been added to background process. You will get notification on completion.");
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	 			bc.initialBgCount() == Bgcount+1  ;}}), Input.wait60);
    	
    	System.out.println("Docs Unassigned from  "+assignmentName);
    	this.driver.getWebDriver().get(Input.url+ "Assignment/ManageAssignment");
 	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getNumberOfAssignmentsToBeShown().Visible()  ;}}), Input.wait60);
	
	getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
	
	driver.scrollingToBottomofAPage();
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getSelectAssignment(assignmentName).Visible()  ;}}), Input.wait60);
	driver.scrollingToBottomofAPage();

	Assert.assertEquals(0,Integer.parseInt(getSelectAssignmentDocCount(assignmentName,7).getText()));
	}
   
   public void RedistributeDoc_ReviewerTab() throws InterruptedException {
	   assertion=new SoftAssert();
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAssignment_ManageReviewersTab().Visible()  ;}}), Input.wait60);
	   	getAssignment_ManageReviewersTab().waitAndClick(10);;
	   	
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAssgn_ManageRev_selectrev().Visible()  ;}}), Input.wait60);
	  	getAssgn_ManageRev_selectrev().waitAndClick(10);
	 
	  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	  			getAssgn_ManageRev_Action().Visible()  ;}}), Input.wait60);
       getAssgn_ManageRev_Action().waitAndClick(15);
      	
       
       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		   getAssgn_ManageRev_Action_redistributedoc().Visible()  ;}}), Input.wait60);
       getAssgn_ManageRev_Action_redistributedoc().WaitUntilPresent();
       assertion.assertTrue(getAssgn_ManageRev_Action_redistributedoc().Displayed());
       getAssgn_ManageRev_Action_redistributedoc().waitAndClick(15);
       
        
       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		   bc.getYesBtn().Visible()  ;}}), Input.wait60);
   	   bc.getYesBtn().waitAndClick(10);
   	   
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getAssgn_Redistributepopup().Visible()  ;}}), Input.wait60);
     	getAssgn_Redistributepopup().waitAndClick(10);
     	
     	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			getAssgn_Redistributepopup_save().Visible()  ;}}), Input.wait60);
     	getAssgn_Redistributepopup_save().waitAndClick(10);
   	   
     	bc.VerifySuccessMessage("Record saved successfully");
     	Thread.sleep(2000);
     	
     	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			getAssgn_ManageRev_DisDoc(1).Visible()  ;}}), Input.wait60);
     	assertion.assertEquals(0,getAssgn_ManageRev_DisDoc(1).getText());
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssgn_ManageRev_DisDoc(2).Visible()  ;}}), Input.wait60);
    	assertion.assertEquals(50,getAssgn_ManageRev_DisDoc(2).getText());
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssgn_ManageRev_selectrev().Visible()  ;}}), Input.wait60);
    	getAssgn_ManageRev_selectrev().waitAndClick(10);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssgn_ManageRev_Action().Visible()  ;}}), Input.wait60);
    	 getAssgn_ManageRev_Action().waitAndClick(10);
       	
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getAssgn_ManageRev_Action_redistributedoc().Visible()  ;}}), Input.wait60);
    	getAssgn_ManageRev_Action_redistributedoc().waitAndClick(10);
    	
   	    bc.VerifyWarningMessage("No documents available to do the selected action");
    	
    }
   
        public void CopyAssignment(final String assignmentName,String codingfrom) throws InterruptedException {
        	
        String drawpool = getAssgnGrp_Create_DrawPooltoggle().GetAttribute("class");
        System.out.println(drawpool);
        
        driver.scrollingToBottomofAPage();
        String permission1  = getAssgn_permissions(14).GetAttribute("class");
        String permission2  = getAssgn_permissions(24).GetAttribute("class");
        String permission3  = getAssgn_permissions(25).GetAttribute("class");
        String permission4  = getAssgn_permissions(28).GetAttribute("class"); //getAssgn_permissions(29).GetAttribute("class");
               
     	driver.scrollPageToTop();
	   	getAssignment_BackToManageButton().waitAndClick(10);
	 	
	    getAssignmentActionDropdown().waitAndClick(10);
	    
	    Thread.sleep(2000);
	  	Assert.assertTrue(getAssignmentAction_CopyAssignment().Displayed());
	  
	  	
	   	getAssignmentAction_CopyAssignment().waitAndClick(5);
	   	
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			bc.getYesBtn().Visible()  ;}}), Input.wait60);
		bc.getYesBtn().waitAndClick(5);
		Thread.sleep(2000);
		bc.VerifySuccessMessage("Record copied successfully");
		
		
		
		getAssignmentActionDropdown().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getAssignmentAction_EditAssignment().Visible()  ;}}), Input.wait60);
	    getAssignmentAction_EditAssignment().waitAndClick(5);
	    
	     Assert.assertTrue(drawpool.equalsIgnoreCase("false"));
	    System.out.println(getAssignmentCodingFormDropDown().selectFromDropdown().getFirstSelectedOption().getText());
	    Assert.assertTrue(getAssignmentCodingFormDropDown().selectFromDropdown().getFirstSelectedOption().getText().equalsIgnoreCase(codingfrom));
	    
	    try {
			Assgnwithspecialchars("AssignmentNameWith@test");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    driver.scrollingToBottomofAPage();
	    Assert.assertTrue(permission1.equalsIgnoreCase("false"));
	    Assert.assertTrue(permission2.equalsIgnoreCase("true"));
	    Assert.assertTrue(permission3.equalsIgnoreCase("false"));
	    Assert.assertTrue(permission4.equalsIgnoreCase("false"));
	  	}
        
        public void completedocsinassgn(String assignmentName) throws InterruptedException
        {
        	getSelectAssignment(assignmentName).waitAndClick(5);;
        	driver.scrollPageToTop();
        	
        	getAssignmentActionDropdown().Click();
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getAssignmentAction_Completedoc().Visible()  ;}}), Input.wait60);
        
        	getAssignmentAction_Completedoc().waitAndClick(3);
        	
        	bc.getYesBtn().waitAndClick(5);
    		bc.VerifySuccessMessage("All Documents successfully completed.");
    		
    		bc.impersonateRMUtoReviewer();
    		
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getSelectAssignmentAsReviewer(assignmentName).Visible()  ;}}), Input.wait30);
        	
        	Assert.assertTrue(getSelectAssignmentAsReviewer(assignmentName).Displayed());
        	System.out.println(getrev_assgnprogress(assignmentName).getText());
        	
        	Assert.assertTrue(getrev_assgnprogress(assignmentName).getText().equalsIgnoreCase("100%"));
        	Assert.assertTrue(getrev_assgnprogress(assignmentName).getText().equalsIgnoreCase("100%"));
        	
        	// Verify Yellow color of highlighted text
    		WebElement activeElement = driver.switchTo().activeElement();
    		String HighlightedColor = driver.FindElementByCssSelector("rect[style*='#FFFF00']").GetCssValue("fill");
    		System.out.println(HighlightedColor);
    		
    		Assert.assertEquals(HighlightedColor, "rgb(255, 255, 0)");

    		/*if (HighlightedColor.equalsIgnoreCase("rgb(255, 255, 0)")) {

    			System.out.println("Annotation_Highlight Yellow color displayed properly");
    		} else {
    			System.out.println("Annotation_Highlight Yellow color not displayed properly");
    		}*/
    		
        }
        
        public void Viewindoclistfromassgn(String assignmentName)
        {
        	this.driver.getWebDriver().get(Input.url+ "Assignment/ManageAssignment");
          	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getNumberOfAssignmentsToBeShown().Visible()  ;}}), Input.wait30);
        	
        	getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
        	
           	driver.scrollingToBottomofAPage();
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getSelectAssignment(assignmentName).Visible()  ;}}), Input.wait30);
        	driver.scrollingToBottomofAPage();
        	getSelectAssignment(assignmentName).waitAndClick(15);

           driver.scrollPageToTop();
        	
           getAssignmentActionDropdown().waitAndClick(15);
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getAssignmentAction_ViewinDocList().Visible()  ;}}), Input.wait60);
        	getAssignmentAction_ViewinDocList().Click();
        	
        	final DocListPage dp = new DocListPage(driver);
    	    dp.getDocList_info().WaitUntilPresent();
    	    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    	    		   !dp.getDocList_info().getText().isEmpty()  ;}}),Input.wait60);
    	  //  Assert.assertEquals(dp.getDocList_info().getText().toString().replaceAll(",", ""),"Showing 1 to 10 of "+Input.pureHitSeachString1+" entries");
    	    System.out.println("Expected docs("+Input.pureHitSeachString1+") are shown in doclist");

        }
        
        public void Viewindocviewfromassgn(String assignmentName)
        {
        	this.driver.getWebDriver().get(Input.url+ "Assignment/ManageAssignment");
          	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getNumberOfAssignmentsToBeShown().Visible()  ;}}), Input.wait30);
        	
        	getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
        	
           	driver.scrollingToBottomofAPage();
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getSelectAssignment(assignmentName).Visible()  ;}}), Input.wait30);
        	driver.scrollingToBottomofAPage();
        	getSelectAssignment(assignmentName).waitAndClick(15);

           driver.scrollPageToTop();
        	
           getAssignmentActionDropdown().waitAndClick(15);
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getAssignmentAction_ViewinDocView().Visible()  ;}}), Input.wait60);
        	getAssignmentAction_ViewinDocView().Click();
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			docview.getDocView_info().Visible()  ;}}), Input.wait60);
        	docview.getDocView_info().WaitUntilPresent();
      	    Assert.assertEquals(docview.getDocView_info().getText().toString(),"of "+Input.pureHitSeachString1+" Docs");
      	    System.out.println("Expected docs("+Input.pureHitSeachString1+") are shown in docView");
        }
        
        public void createnewquickbatch_chronologic_withoutReviewer(final String assignmentName,String codingForm) throws InterruptedException {
     
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			 getAssignmentName().Visible()  ;}}), Input.wait60);
      	getAssignmentName().SendKeys(assignmentName);
      
         try {
     		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     				getAssignmentCodingFormDropDown().Visible()  ;}}), Input.wait30);
        	getAssignmentCodingFormDropDown().selectFromDropdown().selectByVisibleText(codingForm);
     	}
     	catch (Exception e) {
     		getAssignmentCodingFormDropDown().selectFromDropdown().selectByIndex(1);
     	} 
         try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
         Assert.assertTrue(getQuickBatch_optimizedorder().Selected());
         getQuickBatch_chornologicorder().waitAndClick(10);
         
          
         driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		 getQuickBatch_Doccount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60);
        	    	
         int doccount = Integer.parseInt(getQuickBatch_Doccount().getText());
         System.out.println("Doc Count is :"+doccount);
         
         getContinueBulkAssign().waitAndClick(Input.wait30);
         

       final BaseClass bc = new BaseClass(driver);
       final int Bgcount = bc.initialBgCount();
       
       getFinalizeButton().waitAndClick(Input.wait30);
       
       bc.VerifySuccessMessage("Quick Batch Assign has been added to background process. You will get notification on completion.");
       System.out.println("Assignment "+assignmentName+" created with CF "+codingForm);
          
       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    	 			bc.initialBgCount() == Bgcount+1  ;}}), Input.wait60);
      	
      	System.out.println("Docs assigned to  "+assignmentName);
      	
      	bc.BckTaskMessageverify("QuickBatch");
      	
      	this.driver.getWebDriver().get(Input.url+ "Assignment/ManageAssignment");
      	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getNumberOfAssignmentsToBeShown().Visible()  ;}}), Input.wait60);
    	
    	getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
    	
    	
    	driver.scrollingToBottomofAPage();
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSelectAssignment(assignmentName).Visible()  ;}}), Input.wait60);
    	driver.scrollingToBottomofAPage();
    	getSelectAssignment(assignmentName).waitAndClick(15);

    	System.out.println(Integer.parseInt(getSelectAssignmentDocCount(assignmentName,7).getText().toString()));

    //	Assert.assertEquals(Integer.parseInt(getSelectAssignmentDocCount(assignmentName).getText().toString()),doccount);
    	
	   driver.scrollPageToTop();
    	
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getAssignmentActionDropdown().Visible()  ;}}), Input.wait60);
	   getAssignmentActionDropdown().Click();
    
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignmentAction_EditAssignment().Visible();}}), Input.wait120);
    	
    	getAssignmentAction_EditAssignment().waitAndClick(20);
    	Thread.sleep(2000);//
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssgnGrp_Create_DrawPoolCount().Visible()  ;}}), Input.wait120);
    	getAssgnGrp_Create_DrawPoolCount().WaitUntilPresent();
    
    	System.out.println(getAssgnGrp_Create_DrawPooltoggle().GetAttribute("class"));
    	System.out.println(getAssgnGrp_Create_DrawPoolCount().GetAttribute("value").toString());
    	Assert.assertEquals(getAssgnGrp_Create_DrawPooltoggle().GetAttribute("class"),"true");
    	
    	Assert.assertEquals(getAssgnGrp_Create_DrawPoolCount().GetAttribute("value").toString(),"100");
    //	assertion.assertAll();
    }
        
        public void createnewquickbatch_Optimized_withReviewer(final String assignmentName,String codingForm,String Reviewercheck) throws InterruptedException {
           
            driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
         			 getAssignmentName().Visible()  ;}}), Input.wait60);
          	getAssignmentName().SendKeys(assignmentName);
          
             try {
         		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
         				getAssignmentCodingFormDropDown().Visible()  ;}}), Input.wait30);
            	getAssignmentCodingFormDropDown().selectFromDropdown().selectByVisibleText(codingForm);
         	}
         	catch (Exception e) {
         		getAssignmentCodingFormDropDown().selectFromDropdown().selectByIndex(1);
         	} 
         
             if(Reviewercheck.equals("AllRev"))
            {
            getQuickBatch_Selectallrev().waitAndClick(10);
            }
            else if(Reviewercheck.equals("selectrmu"))
            {
            	getSelectUserToAssign(Input.rmu1userName).waitAndClick(10);
            	getSelectUserToAssign(Input.rmu2userName).waitAndClick(10);
            }
            else if(Reviewercheck.equals("selectrev"))
            {
            	getSelectUserToAssign(Input.rev1userName).waitAndClick(10);
            	getSelectUserToAssign(Input.rev2userName).waitAndClick(10);
            }
             final String doccount = getQuickBatch_Doccount().getText();
             System.out.println(doccount);
             UtilityLog.info(doccount);
             
             getContinueBulkAssign().waitAndClick(Input.wait30);
             
             driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
         			getFinalCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60);
              
           getAssgn_LikeDoc_Familytoggle().waitAndClick(Input.wait30);
           getAssgn_LikeDoc_Emailtoggle().waitAndClick(10);
           getAssgn_LikeDoc_Neartoggle().waitAndClick(10);
          	
           String totaldoccount = getFinalCount().getText();
           Integer.parseInt(totaldoccount);
           System.out.println("Doc Count:-"+  totaldoccount);
           UtilityLog.info("Doc Count:-"+  totaldoccount);
           
           final BaseClass bc = new BaseClass(driver);
           final int Bgcount = bc.initialBgCount();
           
           getFinalizeButton().waitAndClick(Input.wait30);
           
           bc.VerifySuccessMessage("Quick Batch Assign has been added to background process. You will get notification on completion.");
           System.out.println("Assignment "+assignmentName+" created with CF "+codingForm);
           UtilityLog.info("Assignment "+assignmentName+" created with CF "+codingForm);
              
           driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        	 			bc.initialBgCount() == Bgcount+1  ;}}), Input.wait60);
          	
          	System.out.println("Docs assigned to  "+assignmentName);
          	UtilityLog.info("Docs assigned to  "+assignmentName);
          	
          	bc.BckTaskMessageverify("QuickBatch");
          	
          	this.driver.getWebDriver().get(Input.url+ "Assignment/ManageAssignment");
          	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getNumberOfAssignmentsToBeShown().Visible()  ;}}), Input.wait60);
        	
        	getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
        	
        	
        	driver.scrollingToBottomofAPage();
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getSelectAssignment(assignmentName).Visible()  ;}}), Input.wait60);
        	driver.scrollingToBottomofAPage();
        	getSelectAssignment(assignmentName).waitAndClick(15);
        	
        	System.out.println(Integer.parseInt(getSelectAssignmentDocCount(assignmentName,7).getText().toString()));
        	UtilityLog.info(Integer.parseInt(getSelectAssignmentDocCount(assignmentName,7).getText().toString()));

        	assertion.assertEquals(Integer.parseInt(getSelectAssignmentDocCount(assignmentName,7).getText().toString()),totaldoccount);
        	
    	    driver.scrollPageToTop();
        	
        	getAssignmentActionDropdown().waitAndClick(15);
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getAssignmentAction_EditAssignment().Visible()  ;}}), Input.wait60);
        	getAssignmentAction_EditAssignment().WaitUntilPresent();
        	getAssignmentAction_EditAssignment().waitAndClick(15);
        	driver.waitForPageToBeReady();
        
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getAssgnGrp_Create_DrawPoolCount().Visible()  ;}}), Input.wait120);
        	Thread.sleep(2000);
        	
        	Assert.assertEquals(getAssgnGrp_Create_DrawPooltoggle().GetAttribute("class"),"true");
        	System.out.println(getAssgnGrp_Create_DrawPooltoggle().GetAttribute("class"));
        	UtilityLog.info(getAssgnGrp_Create_DrawPooltoggle().GetAttribute("class"));
        	System.out.println(getAssgnGrp_Create_DrawPoolCount().GetAttribute("value"));
        	UtilityLog.info(getAssgnGrp_Create_DrawPoolCount().GetAttribute("value"));
        	
        	Assert.assertEquals(getAssgnGrp_Create_DrawPoolCount().GetAttribute("value"),"100");
        	//assertion.assertAll();
        }
        
        public void ValidateReviewerlistquickbatch(String pendinguser) {
         
            driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
            		getAddViewers().Visible()  ;}}), Input.wait60);
            
          
         
            getAddViewers().Click(); 
           //verify all users are available
           System.out.println(getSelectUserToAssign(Input.pa1userName).Exists());
         //  Assert.assertTrue(getSelectUserToAssign(Input.da1userName).Exists());
           Assert.assertTrue(getSelectUserToAssign(Input.pa1userName).Exists());
           Assert.assertTrue(getSelectUserToAssign(Input.pa2userName).Exists());
           Assert.assertTrue(getSelectUserToAssign(Input.rmu1userName).Exists());
           Assert.assertTrue(getSelectUserToAssign(Input.rmu2userName).Exists());
           Assert.assertTrue(getSelectUserToAssign(Input.rev1userName).Exists());
           Assert.assertTrue(getSelectUserToAssign(Input.rev2userName).Exists());
           Assert.assertTrue(getSelectUserToAssign(pendinguser).Exists());
            
          }
        
        public void Quickbatchfailure() {
        	
        	//click on continue button without selecting mandatory fields
        	 getContinueBulkAssign().waitAndClick(Input.wait30);
        	 
        	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
           			getQuickBatch_NameErrormsg().Visible()  ;}}), Input.wait30);
           	String expnamemsg = getQuickBatch_NameErrormsg().getText();
           	System.out.println(expnamemsg);
           	Assert.assertEquals("Name required", expnamemsg);
           	
           	String expcodingmsg = getQuickBatch_CodingErrormsg().getText();
           	System.out.println(expcodingmsg);
           	Assert.assertEquals("Please select Coding Form", expcodingmsg);
        	
            String aasgnname = "AutomationTestingforQuickBatchAutomationTestingforQuickBatchAutomationTestingforQuickBatchAutomationTestingforQuickBatchAutomationTestingforQuickBatchAutomationTestingforQuickBatchAutomationTestingforQuickBatchAutomationTestingforQuickBatchAutomationTesting";   
           
            driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
         			 getAssignmentName().Visible()  ;}}), Input.wait30);
          	getAssignmentName().SendKeys(aasgnname);
          	
          	getAssignmentName().Click();
          	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
          			getQuickBatch_NameErrormsg().Visible()  ;}}), Input.wait30);
          	String expmsg = getQuickBatch_NameErrormsg().getText();
          	System.out.println(expmsg);
          	Assert.assertEquals("Maximum length should be 250", expmsg);
          
           	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
         				getAssignmentCodingFormDropDown().Visible()  ;}}), Input.wait60);
           	getAssignmentCodingFormDropDown().selectFromDropdown().selectByIndex(1);
        
            getContinueBulkAssign().waitAndClick(Input.wait30);
        } 
        
         
          
        public void createnewquickbatch(final String assignmentName,String codingForm) throws NumberFormatException, InterruptedException {
            
            driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
         			 getAssignmentName().Visible()  ;}}), Input.wait60);
          	getAssignmentName().SendKeys(assignmentName);
          
             try {
         		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
         				getAssignmentCodingFormDropDown().Visible()  ;}}), Input.wait30);
            	getAssignmentCodingFormDropDown().selectFromDropdown().selectByVisibleText(codingForm);
         	}
         	catch (Exception e) {
         		getAssignmentCodingFormDropDown().selectFromDropdown().selectByIndex(1);
         	} 
             
             String doccount = getQuickBatch_Doccount().getText();
             Integer.parseInt(doccount);
             System.out.println(doccount);
          
          getContinueBulkAssign().waitAndClick(Input.wait30);
             
           final BaseClass bc = new BaseClass(driver);
           final int Bgcount = bc.initialBgCount();
           
           getFinalizeButton().waitAndClick(Input.wait30);
           
           bc.VerifySuccessMessage("Quick Batch Assign has been added to background process. You will get notification on completion.");
           System.out.println("Assignment "+assignmentName+" created with CF "+codingForm);
              
           driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        	 			bc.initialBgCount() == Bgcount+1  ;}}), Input.wait60);
          	
          	System.out.println("Docs assigned to  "+assignmentName);
          	
          	this.driver.getWebDriver().get(Input.url+ "Assignment/ManageAssignment");
          	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getNumberOfAssignmentsToBeShown().Visible()  ;}}), Input.wait60);
        	
        	getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
        	
        	driver.scrollingToBottomofAPage();
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getSelectAssignment(assignmentName).Visible()  ;}}), Input.wait60);
        	driver.scrollingToBottomofAPage();
        	getSelectAssignment(assignmentName).waitAndClick(15);

     	   driver.scrollPageToTop();
        	
        	getAssignmentActionDropdown().Click();
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getAssignmentAction_EditAssignment().Visible()  ;}}), Input.wait60);
        
        	getAssignmentAction_EditAssignment().waitAndClick(15);
        	driver.waitForPageToBeReady();
        	Thread.sleep(2000);
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getAssignmentName().Visible()  ;}}), Input.wait120);
        	getAssignmentName().SendKeys(assignmentName+"New");
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
          			getAssignmentSaveButton().Displayed()  ;}}), Input.wait60);
          	getAssignmentSaveButton().waitAndClick(10);
          	
        	Thread.sleep(2000);
            bc.VerifySuccessMessage("Assignment updated successfully");
        	bc.CloseSuccessMsgpopup();
        	Thread.sleep(2000);
	
        	
			addReviewerAndDistributeDocs(assignmentName+"New", Integer.parseInt(doccount));
			
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getAssignmentActionDropdown().Visible()  ;}}), Input.wait60);            
        	getAssignmentActionDropdown().waitAndClick(10);
       
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getAssignmentAction_CopyAssignment().Visible()  ;}}), Input.wait60); 
        	getAssignmentAction_CopyAssignment().waitAndClick(5);
       	   	
       		bc.getYesBtn().waitAndClick(5);
       		bc.VerifySuccessMessage("Record copied successfully");
       		
       		//view all docs in doclist
       		Viewindoclistfromassgn(assignmentName+"New");
       		
       	   //view all docs in docview
       		Viewindocviewfromassgn(assignmentName+"New");

        	//delete assignment
       		deleteAssignment(assignmentName+"New");
        	
         }
            

        public void createAssignmentwithkeywords(String assignmentName, String codingForm,String[] keys) throws InterruptedException {
        	this.driver.getWebDriver().get(Input.url+ "Assignment/ManageAssignment");
        	
        	driver.waitForPageToBeReady();
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getAssignmentActionDropdown().Visible() ;}}), Input.wait60);
        	
        	getAssignmentActionDropdown().waitAndClick(20);
        	
         	getAssignmentAction_NewAssignment().WaitUntilPresent();
        	
        	getAssignmentAction_NewAssignment().waitAndClick(20);
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			 getAssignmentName().Visible()  ;}}), Input.wait60);
        	System.out.println(assignmentName);
        	getAssignmentName().SendKeys(assignmentName);
        	getParentAssignmentGroupName().Displayed();
        	getSelectedClassification().selectFromDropdown().selectByVisibleText("1LR");
        	try {
        		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        				getAssignmentCodingFormDropDown().Visible()  ;}}), Input.wait60);
           	getAssignmentCodingFormDropDown().selectFromDropdown().selectByVisibleText(codingForm);
        	}
        	catch (Exception e) {
        		getAssignmentCodingFormDropDown().selectFromDropdown().selectByIndex(1);
        	} 
        	
        	//verify keywords
        	getAssgn_Keywordsbutton().waitAndClick(10);
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getAssgn_Keywordspopup().Visible()  ;}}), Input.wait30);
        	
        	  List<WebElement> allvalues = getAssgn_AllKeywordscheck().FindWebElements();
        	  List<String> expkey = new ArrayList<String>();
			   for(int j=1;j<=allvalues.size();j++)
			   {
				  System.out.println(allvalues.get(j).getAttribute("checked").toString());
				  System.out.println(allvalues.get(j).getText());
				  if(allvalues.get(j).getAttribute("checked").equalsIgnoreCase("checked")) {
					   System.out.println("Pass");
					  
				   }
				   else {
					   System.out.println("FAIL");
				   }
			   }
            	
        	 	//permissions
        	driver.scrollingToBottomofAPage();
        	getSelectSavePermission().ScrollTo();
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getSelectSavePermission().Visible()  ;}}), Input.wait60);
        	getSelectSavePermission().waitAndClick(10);
        	driver.scrollPageToTop();
        	//getAssignmentCodingFormDropDown().selectFromDropdown().selectByVisibleText(codingForm);
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getAssignmentSaveButton().Visible()  ;}}), Input.wait60);
        	getAssignmentSaveButton().waitAndClick(5);
        	System.out.println("Assignment "+assignmentName+" created with CF "+codingForm);
        	 
    	}
        
        public void Impersonateusercompletedoc(String assignmentName) throws InterruptedException
        {
        	driver.getWebDriver().get(Input.url+ "Assignment/ManageAssignment");
    	   	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getNumberOfAssignmentsToBeShown().Visible()  ;}}), Input.wait60);
        	
        	getNumberOfAssignmentsToBeShown().selectFromDropdown().selectByVisibleText("100");
        	
        	driver.scrollingToBottomofAPage();
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getSelectAssignment(assignmentName).Visible()  ;}}), Input.wait60);
        	driver.scrollingToBottomofAPage();
        
        	getSelectAssignment(assignmentName).waitAndClick(5);;
        	driver.scrollPageToTop();
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getAssignmentActionDropdown().Visible()  ;}}), Input.wait60);
        	getAssignmentActionDropdown().Click();
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getAssignmentAction_Completedoc().Visible()  ;}}), Input.wait60);
        	getAssignmentAction_Completedoc().WaitUntilPresent();
         	getAssignmentAction_Completedoc().waitAndClick(20);
         	Thread.sleep(2000);
        	bc.VerifyErrorMessage("Impersonated User cannot do this operation");
         		
        }
   
 
       
 
 }
