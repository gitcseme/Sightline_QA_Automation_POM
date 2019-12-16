package pageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

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

public class AssignmentsPage {

    Driver driver;
    Element element;
    BaseClass bc;
    SoftAssert assertion;
    SessionSearch search;
    
   
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
    public Element getAssignment_ManageReviewersTab(){ return driver.FindElementByXPath("//*[@id='ui-id-2']"); }
    public Element getSelectAssignmentDocCount(String assignmentName){ return driver.FindElementByXPath("//*[@id='GridAssignment']/tbody//tr[td='"+assignmentName+"']/td[7]"); }
    public Element getAssgnCounts(String assignmentName,int colno){ return driver.FindElementByXPath("//*[@id='GridAssignment']/tbody//tr[td='"+assignmentName+"']/td["+colno+"]"); }
    public Element getPersistCB(){ return driver.FindElementByXPath("//div[@id='existingassignment']//label[@class='checkbox']/i"); }
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
    public Element getAssignmentAction_ViewinDocView(){ return driver.FindElementByXPath("//*[@id='ulActions']//a[text()='View All Docs In DocView']"); }
    public Element getSelectAssignmentAsReviewer(String assignmentName){ return driver.FindElementByXPath(".//*[@id='dt_basic']//strong[contains(.,'"+assignmentName+"')]"); }
    
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
    public ElementCollection getDashboadAssgn(){ return driver.FindElementsByXPath("//*[@id='assignmentList']/tbody/tr"); }
    public Element getAssgn_ManageRev_selectrev(){ return driver.FindElementByXPath(".//*[@id='dt_basic']//td[contains(.,'"+Input.rmu1userName+"')]"); }
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
    public Element getAssignmentAction_Completedoc(){ return driver.FindElementById("CompleteAllDocuments"); }
    public Element getAssgn_Redistributepopup(){ return driver.FindElementByXPath("//*[@name='Users']/following-sibling::i"); }
    public Element getAssgn_Redistributepopup_save(){ return driver.FindElementById("btnShareSave"); }
    public Element getAssgn_DocSequence_avacrtiera(int no){ return driver.FindElementByCssSelector(".dd-list li:nth-of-type("+no+")"); }
    public Element getAssgn_DocSequence_liveseq(){ return driver.FindElementByCssSelector(".live-seq"); }
    public Element getAssignmentAction_CopyAssignment(){ return driver.FindElementById("CopyAssignmentText"); }
    public Element getSelectCopyAssignment(){ return driver.FindElementByXPath("//*[@id='GridAssignment']/tbody//tr[contains(.,'Copy')]/td[1]"); }
    public Element getSelectcopyAssgnmToBulkAssign(String assignmentName){ return driver.FindElementByXPath("//*[@id='jstreeComplete']//a[starts-with(.,'"+assignmentName+"')]"); }
    
    public Element getAssgn_permissions(int no){ return driver.FindElementByXPath("(//label[@class='toggle'])["+no+"]//i"); }
    public Element getAssgngrp_CascadeSetting(){ return driver.FindElementByXPath(".//*[@id='IsCascadeEnabled']/following-sibling::i"); }
    public Element getrev_assgnprogress(String assignmentName){ return driver.FindElementById(".//td[contains(.,'"+assignmentName+"')]//following::span[starts-with(@id,'AssignmentProgress')]"); }
    public Element getrev_batchprogress(String assignmentName){ return driver.FindElementById(".//td[contains(.,'"+assignmentName+"')]//following::div[starts-with(@class,'progress-bar bg-color-green')]"); }
    // public Element getAssignmentAction_Completedoc(){ return driver.FindElementById("CompleteAllDocuments"); }
    
    
    public AssignmentsPage(Driver driver){

        this.driver = driver;
        bc = new BaseClass(driver);
        this.driver.getWebDriver().get(Input.url+ "Assignment/ManageAssignment");
        driver.waitForPageToBeReady();
        //This initElements method will create all WebElements
        assertion = new SoftAssert();
        search = new SessionSearch(driver);

    }

    public void createAssignment(String assignmentName, String codingForm) throws InterruptedException {
    	this.driver.getWebDriver().get(Input.url+ "Assignment/ManageAssignment");
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignmentActionDropdown().Visible() ;}}), Input.wait60);
    	Thread.sleep(2000);
    	getAssignmentActionDropdown().waitAndClick(10);
    	
    	/*driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignmentAction_NewAssignment().Displayed()  ;}}), Input.wait60);*/
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
    	getSelectSavePermission().ScrollTo();
    	getSelectSavePermission().waitAndClick(5);
    	driver.scrollPageToTop();
    	//getAssignmentCodingFormDropDown().selectFromDropdown().selectByVisibleText(codingForm);
    	getAssignmentSaveButton().waitAndClick(5);
    	System.out.println("Assignment "+assignmentName+" created with CF "+codingForm);
    	 
	}
    
    public void assignDocstoExisting(final String assignmentName) {
    	
    	//getSelectAssignmentToBulkAssign(assignmentName).WaitUntilPresent();
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getStartingCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60);
       	
    	getPersistCB().waitAndClick(5);
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSelectAssignmentToBulkAssign(assignmentName).Visible()  ;}}), Input.wait60);
       	
    	//getSelectAssignmentToBulkAssign(assignmentName).waitAndClick(20);
    	getSelectcopyAssgnmToBulkAssign(assignmentName).waitAndClick(20);
    	
    	getContinueBulkAssign().waitAndClick(5);
    
       	final BaseClass bc = new BaseClass(driver);
        final int Bgcount = bc.initialBgCount();
        
       	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFinalCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60);
       	
       	String assignpop = getAssignment_ManageReviewersTab().getText();
       	System.out.println(assignpop);
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
	}
    
    public void editAssignment(final String assignmentName) {
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
    
    	getAssignmentAction_EditAssignment().waitAndClick(3);
    	
	}
    
      public void addReviewerAndDistributeDocs(String assignmentName) throws InterruptedException {

    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignment_ManageReviewersTab().Visible()  ;}}), Input.wait60);
    	getAssignment_ManageReviewersTab().Click();
    	
    	getAddReviewersBtn().waitAndClick(10);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSelectUserToAssign().Visible()  ;}}), Input.wait60);
    	getSelectUserToAssign().waitAndClick(10);
    	getAdduserBtn().Click();
    	 bc.VerifySuccessMessage("Record saved successfully");
    	getDistributeTab().Click();
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
    	Thread.sleep(2000);
    	
    	getAssgnCounts(assignmentName, 9).WaitUntilPresent();
    	//verify total docs count
    	String acttotalcount = getAssgnCounts(assignmentName, 9).getText();
    	System.out.println(Integer.parseInt(acttotalcount));
    	Assert.assertEquals(Input.pureHitSeachString1, Integer.parseInt(acttotalcount));
    	
    	//verify distributed docs count
    	String actdistributedcount = getAssgnCounts(assignmentName, 9).getText();
    	System.out.println(Integer.parseInt(actdistributedcount));
    	Assert.assertEquals(Input.pureHitSeachString1, Integer.parseInt(actdistributedcount));
     	
	}
    
    public void assignDocstoNewAssgn(final String assignmentName,String codingForm,int pureHit) {
      	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getBulkAssign_NewAssignment().Visible()  ;}}), Input.wait60);
    	getBulkAssign_NewAssignment().waitAndClick(20);
    	Assert.assertTrue(getbulkassgnpopup().Displayed());
   	
        	getContinueBulkAssign().waitAndClick(15);
  
     	final BaseClass bc = new BaseClass(driver);
      final int Bgcount = bc.initialBgCount();
      
      getFinalizeButton().waitAndClick(5);
      
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
    getAssignmentSaveButton().waitAndClick(5);
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

	Assert.assertEquals(pureHit,Integer.parseInt(getSelectAssignmentDocCount(assignmentName).getText()));
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
  
  public void Assgnwithdocumentsequence()
  {
	  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getAssgn_DocSequenceblock().Displayed()  ;}}), Input.wait60);
	  assertion.assertEquals("DOCUMENT PRESENTATION SEQUENCE", getAssgn_DocSequencelabel().getText());
	  
	Element taget = getAssgn_DocSequence_liveseq();
	for(int i=1;i<=4;i++)
	{
		element.Draganddrop(getAssgn_DocSequence_avacrtiera(i),taget);
	  
	}
  	getAssgn_DocSequence_SortbyMetadata().waitAndClick(10);
  	
  	getAssgn_DocSequence_Selectmetadata().selectFromDropdown().selectByVisibleText("CustodianName");
  	
  	getAssignmentSaveButton().waitAndClick(10);
	  
  	bc.VerifySuccessMessage("Assignment updated successfully");
  	bc.CloseSuccessMsgpopup();
	assertion.assertAll();
  }
  
   public void Assignment_ManageRevtab_ViewinDocView() {
	   
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAssignment_ManageReviewersTab().Visible()  ;}}), Input.wait60);
    	getAssignment_ManageReviewersTab().waitAndClick(10);
    	
    	getAssgn_ManageRev_selectrev().waitAndClick(10);
       	
       	getAssgn_ManageRev_Action().waitAndClick(10);
       	
       	assertion.assertTrue(getAssgn_ManageRev_Action_ViewDocview().Displayed());
    	assertion.assertTrue(getAssgn_ManageRev_Action_ViewDoclist().Displayed());
    	
    	getAssgn_ManageRev_Action_ViewDocview().waitAndClick(10);
    	bc.getYesBtn().waitAndClick(10);
    	
    	DocViewPage docview = new DocViewPage(driver);
    	//docview.
      
		}
   
   public void Assignment_ManageRevtab_ViewinDoclist() {
	   
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getAssignment_ManageReviewersTab().Visible()  ;}}), Input.wait60);
   	getAssignment_ManageReviewersTab().waitAndClick(10);
   	
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
	List<String> asgncount = new ArrayList<String>();
	for(WebElement count: assgnlist)
	{
		asgncount.add(count.getText());
		System.out.println(asgncount.add(count.getText()));
	}
	if(asgncount.size()<=5)
	{
		System.out.println("Pass");
	}
	else {System.out.println("Fail");}
	
 		}
   
   public void Assignment_ManageRevtab_TagFolder(String tagname,String foldername) throws InterruptedException {
	   
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getAssignment_ManageReviewersTab().Visible()  ;}}), Input.wait30);
   	getAssignment_ManageReviewersTab().waitAndClick(10);
   	
   	getAssgn_ManageRev_selectrev().waitAndClick(10);
   	
   	getAssgn_ManageRev_Action().waitAndClick(10);
   	
   	getAssgn_ManageRev_Action_tagdoc().waitAndClick(10);
   	
   	bc.getYesBtn().waitAndClick(10);
   	
   	search.BulkActions_Tag(tagname);
   	
   	//bulk folder
   	
    getAssgn_ManageRev_Action().waitAndClick(20);
   	
   	getAssgn_ManageRev_Action_folderdoc().waitAndClick(10);
   	
   	bc.getYesBtn().waitAndClick(10);
   	
   	search.BulkActions_Folder(foldername);
   	assertion.assertAll();
	  
	}
   
   public void Assignment_ManageRevtab_UnTagUnFolder(String tagname,String foldername) throws InterruptedException {
	   
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAssignment_ManageReviewersTab().Visible()  ;}}), Input.wait30);
	   	getAssignment_ManageReviewersTab().waitAndClick(10);
	   	
	   	getAssgn_ManageRev_selectrev().waitAndClick(10);
	   	
	   	getAssgn_ManageRev_Action().waitAndClick(10);
	   	
	  	getAssgn_ManageRev_Action_tagdoc().waitAndClick(10);
	   	
	   	bc.getYesBtn().waitAndClick(10);
	   	
	   	search.bulkUnTag(tagname);
	   	
	   	//bulk folder
	   	
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAssignment_ManageReviewersTab().Visible()  ;}}), Input.wait30);
	   	getAssignment_ManageReviewersTab().waitAndClick(10);
	   	
	   	getAssgn_ManageRev_selectrev().waitAndClick(10);
	   	
	   	bc.getYesBtn().waitAndClick(10);
	   	
	   	search.bulkUnFolder(foldername);
	   	assertion.assertAll();
		  
		}
   
   public void createAssgnGroup(String assgngrpName) throws InterruptedException {
	   
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getAssgGrptionDropdown().Visible() ;}}), Input.wait60);
   	Thread.sleep(2000);
   	getAssgGrptionDropdown().waitAndClick(10);
   	
   	Thread.sleep(2000);
   	
	getAssgnGrp_Create().waitAndClick(20);
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getAssignmentName().Visible()  ;}}), Input.wait60);
   	getAssignmentName().SendKeys(assgngrpName);
    try{
    	getAssignmentCodingFormDropDown().Displayed();
    	  Assert.fail();
	     }catch (org.openqa.selenium.NoSuchElementException e) {
	             
	               System.out.println("'getAssignmentCodingFormDropDown' is not displayed");
	 }
    getAssgngrp_CascadeSetting().waitAndClick(10);
 
    getAssgnGrp_Create_DrawPooltoggle().waitAndClick(10);
	Thread.sleep(2000);
	
	getAssgnGrp_Create_DrawPoolCount().SendKeys("100");
	Thread.sleep(2000);
	
	//getAssgnGrp_Create_Keepfamilycount().WaitUntilPresent();
	System.out.println(getAssgnGrp_Create_Keepfamilycount().GetAttribute("value"));
	Assert.assertEquals("100", getAssgnGrp_Create_Keepfamilycount().GetAttribute("value"));
   	getAssignmentSaveButton().waitAndClick(5);
   	 
	}
   
   public void EditAssgnGroup(String assgngrpName) throws InterruptedException {
	   
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAssgGrptionDropdown().Visible() ;}}), Input.wait30);
	   	
	   	getAssgnGrp_Select(assgngrpName).waitAndClick(10);
	   	
	   	Thread.sleep(2000);
	   	getAssgGrptionDropdown().waitAndClick(10);
	   	
	   	getAssgnGrp_Edit().WaitUntilPresent();
	   	Thread.sleep(2000);
	   	
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
	 
	 
	   	getAssignmentSaveButton().waitAndClick(5);
	   	 
		}
   
   public void DeleteAssgnGroup(String assgngrpName) throws InterruptedException {
	   
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAssgGrptionDropdown().Visible() ;}}), Input.wait30);
	   	
	   	getAssgnGrp_Select(assgngrpName).waitAndClick(10);
	   	
	   	Thread.sleep(2000);
	   	getAssgGrptionDropdown().waitAndClick(10);
	   	
	   	getAssgnGrp_Delete().WaitUntilPresent();
	   	Thread.sleep(2000);
	   	
	   	getAssgnGrp_Delete().waitAndClick(20);
	   	
	    bc.getYesBtn().waitAndClick(5);
	    bc.VerifySuccessMessage("Assignment group deleted successfully");
	   	 
		}
  
   public void add2ReviewerAndDistributeDocs() {

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
   	
	Assert.assertEquals(Input.searchString1, getEditAggn_Distribute_Totaldoc().getText());
	
   	getSelectUserInDistributeTab().waitAndClick(20);
   	getSelect2ndUserInDistributeTab().waitAndClick(20);
   	
   	getDistributeBtn().waitAndClick(15);
   	
   	Assert.assertEquals(0, getEditAggn_Distribute_Unassgndoc().getText());
   	
	}
   
   public void RemoveDoc_ReviewerTab(String assignmentName) {

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
	   	
	   	getAssgn_ManageRev_selectrev().waitAndClick(10);
	 
        getAssgn_ManageRev_Action().waitAndClick(10);
       	
      	getAssgn_ManageRev_Action_removedoc().waitAndClick(10);
    	bc.getYesBtn().waitAndClick(10);
    	bc.VerifySuccessMessage("Record saved successfully");
    
    	Assert.assertEquals(0,getAssgn_ManageRev_DisDoc(1).getText());
    	
    	getAssignment_BackToManageButton().waitAndClick(10);
    	
    	//verify distributed and left to do count
    	String actleftcount = getAssgnCounts(assignmentName, 9).getText();
    	String actassignedcount = getAssgnCounts(assignmentName, 9).getText();
    	Assert.assertEquals(0, Integer.parseInt(actleftcount));
    	Assert.assertEquals(0, Integer.parseInt(actassignedcount));
	   	
		}
   

   public void Assgnwithspecialchars(String data) throws InterruptedException {
	   
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getAssignmentName().Visible()  ;}}), Input.wait60);
   	getAssignmentName().SendKeys(data);
   	getAssignmentSaveButton().waitAndClick(5);
   	Thread.sleep(1000);
   	System.out.println(getAssgnnameErrormsg().getText());
    Assert.assertTrue(getAssgnnameErrormsg().getText().equalsIgnoreCase("Please enter an assignment name without using special characters"));
   }
   
   public void deleteAssignment(final String assignmentName) {
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
   			getAssignmentAction_DeleteAssignment().Visible()  ;}}), Input.wait60);
   
   	getAssignmentAction_DeleteAssignment().waitAndClick(3);
   	
	bc.getYesBtn().waitAndClick(5);
	bc.VerifySuccessMessage("Assignment deleted successfully");
	   	 
	}
   
   public void editAssgnwithselectedassgn() {
   	driver.getWebDriver().get(Input.url+ "Assignment/ManageAssignment");
   	
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getNumberOfAssignmentsToBeShown().Visible()  ;}}), Input.wait60);
   	
   	driver.scrollPageToTop();
   	
   	getAssignmentActionDropdown().Click();
   	
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getAssignmentAction_EditAssignment().Visible()  ;}}), Input.wait60);
   
   	getAssignmentAction_EditAssignment().waitAndClick(3);
   	
	}
   
   public void assignDocswithpercentagemethod(final String assignmentName,String samplemethod) {
   	
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getStartingCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60);
   	
   	if(samplemethod.equalsIgnoreCase("Percentage"))
   	{
   	getsampleMethod().selectFromDropdown().selectByVisibleText("Percent of Selected Docs");
   	getCountToAssign().SendKeys("10");
   	}
   	else if (samplemethod.equalsIgnoreCase("ParentLevel"))
   	{
   		getsampleMethod().selectFromDropdown().selectByVisibleText("Parent Level Docs Only");
   	}
   		
   	else if (samplemethod.equalsIgnoreCase("InclusiveEmail"))
   	{
   		getsampleMethod().selectFromDropdown().selectByVisibleText("Inclusive Email");
   	}
      	
   	getPersistCB().waitAndClick(5);
   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getSelectAssignmentToBulkAssign(assignmentName).Visible()  ;}}), Input.wait60);
      	
   	getSelectAssignmentToBulkAssign(assignmentName).waitAndClick(20);
   	
   	
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
	//verify total docs count
	String acttotalcount = getAssgnCounts(assignmentName, 7).getText();
	System.out.println(Integer.parseInt(acttotalcount));
	Assert.assertEquals(5, Integer.parseInt(acttotalcount));
	}
   
   public void CompleteAssgn(String assignmentName) {
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
    	
    	bc.getYesBtn().waitAndClick(10);
    	bc.VerifySuccessMessage("All Documents successfully completed.");
    	
    	Assert.assertEquals(0, Integer.parseInt(getAssgnCounts(assignmentName, 10).getText()));
		}
   
   public void UnassignDocsfromAssgn(final String assignmentName) {
     	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getStartingCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60);
      	
 	getUnassignbutton().waitAndClick(5);
   	getSelectAssignmentToBulkAssign(assignmentName).waitAndClick(20);
   	
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

	Assert.assertEquals(0,getSelectAssignmentDocCount(assignmentName).getText());
	}
   
   public void RedistributeDoc_ReviewerTab() throws InterruptedException {

	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getAssignment_ManageReviewersTab().Visible()  ;}}), Input.wait60);
	   	getAssignment_ManageReviewersTab().Click();
	   	
	  	getAssgn_ManageRev_selectrev().waitAndClick(10);
	 
       getAssgn_ManageRev_Action().waitAndClick(10);
      	
       Assert.assertTrue(getAssgn_ManageRev_Action_redistributedoc().Displayed());
       getAssgn_ManageRev_Action_redistributedoc().waitAndClick(10);
   	   bc.getYesBtn().waitAndClick(10);
   	   
     	getAssgn_Redistributepopup().waitAndClick(10);
     	
     	getAssgn_Redistributepopup_save().waitAndClick(10);
   	   
     	bc.VerifySuccessMessage("Record saved successfully");
     	Thread.sleep(2000);
   
    	Assert.assertEquals(0,getAssgn_ManageRev_DisDoc(1).getText());
    	Assert.assertEquals(50,getAssgn_ManageRev_DisDoc(2).getText());
    	
    	getAssgn_ManageRev_selectrev().waitAndClick(10);
    	 getAssgn_ManageRev_Action().waitAndClick(10);
       	
    	getAssgn_ManageRev_Action_redistributedoc().waitAndClick(10);
   	    bc.VerifyWarningMessage("No documents available to do the selected action");
    	
    }
   
        public void CopyAssignment(final String assignmentName,String codingfrom) {
        	
        String drawpool = getAssgnGrp_Create_DrawPooltoggle().GetAttribute("class");
        System.out.println(drawpool);
        
        driver.scrollingToBottomofAPage();
        String permission1  = getAssgn_permissions(14).GetAttribute("class");
        String permission2  = getAssgn_permissions(24).GetAttribute("class");
        String permission3  = getAssgn_permissions(25).GetAttribute("class");
        String permission4  = getAssgn_permissions(29).GetAttribute("class");
               
     	driver.scrollPageToTop();
	   	getAssignment_BackToManageButton().waitAndClick(10);
	 	
	    getAssignmentActionDropdown().waitAndClick(10);
	  	
	  	Assert.assertTrue(getAssignmentAction_CopyAssignment().Displayed());
	  
	   	getAssignmentAction_CopyAssignment().waitAndClick(5);
	   	
		bc.getYesBtn().waitAndClick(5);
		bc.VerifySuccessMessage("Record copied successfully");
		
		getSelectCopyAssignment().waitAndClick(10);
		
		getAssignmentActionDropdown().waitAndClick(10);
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
        
        public void completedocsinassgn(String assignmentName)
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
   
   
 
 
 }