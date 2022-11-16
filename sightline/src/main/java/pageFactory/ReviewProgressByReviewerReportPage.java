package pageFactory;

import java.text.ParseException;
import java.util.concurrent.Callable;
import org.testng.Assert;
import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import testScriptsSmoke.Input;

public class ReviewProgressByReviewerReportPage {

    Driver driver;
   
    public Element getReviewerProgressReportButton(){ return driver.FindElementByXPath(".//*[@id='collapseOne']//a[@href='/Review/ReviewerProgressReport']"); }
    public Element getReviewerProgressReport_Expand(){ return driver.FindElementByXPath(".//*[@id='divreviewer']/div/a[2]"); }
    public Element getReviewerProgressReport_SelectAll(){ return driver.FindElementByXPath(".//input[@id='checkAll4']/following-sibling::i"); }
    public Element getReviewerProgressReport_AssignmentGroupExpand(){ return driver.FindElementById("assignmentGroupID"); }
    public Element getReviewerProgressReport_AssignmentGroupExpandNew(){ return driver.FindElementByXPath("//*[@id='assignmentGroupID']"); }
    
    public Element getReviewerProgressReport_Assignment(){ return driver.FindElementByXPath(".//*[@id='-1_anchor']/i[1]"); } 
    public Element getReviewerProgressReport_DistributedCompletedDocs(){ return driver.FindElementByXPath(".//*[@id='DivId_asignmentgrid']//label[contains(.,'Distributed Completed Docs:')]/following-sibling::div"); } 
    public Element  getReviewerProgressReport_DocumentsDistributed(){ return driver.FindElementByXPath(".//*[@id='DivId_asignmentgrid']//label[contains(.,'Documents Distributed:')]/following-sibling::div"); }
    public Element getReviewerReviewProgress_SummaryTitle(){ return driver.FindElementByXPath(".//*[@id='DivId_asignmentgrid']/ul/li/h2[1]"); }
    public Element getToggleBtn(){ return driver.FindElementByXPath(".//*[@id='Edit User Group']//button[contains(@class,'toggle btn')]"); }
    public Element getFirstUserfromlist(){ return driver.FindElementByXPath(".//*[@id='Edit User Group']/fieldset/div/div[1]/div/div/div/ul/li/a/label"); }
    public Element getFirstUserfromlistNew(){ return driver.FindElementByXPath("(//*[@id='userMultiSelect']/option[1])[2]"); }
    
    public Element getReviewerReviewProgress_Share(){ return driver.FindElementById("ReportReviewer"); }
    public Element getEmailAddress(){ return driver.FindElementByXPath("//textarea[@id='txtEmail']"); }
    public Element getReports_ShareBtn(){ return driver.FindElementById("btnSaveShareReport"); }
    public Element getReviewProgressSummaryTitle(){ return driver.FindElementByXPath(".//*[@id='DivId_asignmentgrid']//li/h2[1]"); } 
    public Element getReviewerProgressReport_LoggedUserName(){ return driver.FindElementByXPath("//div[@id='utility-group']//span[@class='clsUserName']"); } 
    public ElementCollection getUserslist(){ return driver.FindElementsByXPath(".//*[@id='Edit User Group']//ul//input"); }
    
    public Element getSuccessMsgHeader(){ return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span"); }
    public Element getSuccessMsg(){ return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p"); }
    public Element getActivityIcon(){ return driver.FindElementByXPath(".//*[@id='activity']/i"); } 
    public Element getApplyChanges(){ return driver.FindElementById("btn_applychanges"); } 
    public Element getViewAllBtn(){ return driver.FindElementById("btnViewAll"); }     
    public Element getTally_LoadingScreen(){ return driver.FindElementByXPath("//span[@class='LoadImagePosition']/img[@src='/img/loading.gif']"); } 
    public Element getShareNotification(){ return driver.FindElementByXPath(".//*[@id='content']/div[4]//h1[@class='page-title']"); } 
    public Element getShareReportCol(){ return driver.FindElementByXPath(".//*[@id='dt_SharedNotifications']/tbody/tr/td[2]"); } 
    public Element getSharedBy(){ return driver.FindElementByXPath(".//*[@id='dt_SharedNotifications']/tbody/tr/td[3]"); } 
    public ElementCollection getElements(){ return driver.FindElementsByXPath("//*[@class='a-menu']"); }
    public Element getbtnScheduler(){ return driver.FindElementById("btnScheduler"); } 
    public Element getSchedulerForm(){ return driver.FindElementById("FormScheduler"); } 
    public Element getSchedulerTime(){ return driver.FindElementById("txtOneTimeStartDate"); }
    public Element getSchedulerForm_btnSubmit(){ return driver.FindElementById("btnScheduleSubmit"); }
   
    public ReviewProgressByReviewerReportPage(Driver driver){

    	this.driver = driver;
//        this.driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
        
        //This initElements method will create all WebElements
        //PageFactory.initElements(driver.getWebDriver(), this);

    }
    
    public void ValidateReviewProgressReportShareandSchdule(String usertosharewith) throws InterruptedException, ParseException {
    	
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			getReviewerProgressReportButton().Visible()  ;}}), Input.wait30); 
      	getReviewerProgressReportButton().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getReviewerProgressReport_Expand().Visible()  ;}}), Input.wait30); 
		getReviewerProgressReport_Expand().Click();
		//Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getReviewerProgressReport_SelectAll().Enabled()  ;}}), Input.wait30); 
		getReviewerProgressReport_SelectAll().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getReviewerProgressReport_AssignmentGroupExpand().Enabled()  ;}}), Input.wait30); 
		getReviewerProgressReport_AssignmentGroupExpand().waitAndClick(15);
	    //Thread.sleep(2000);
	    
	    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    		getReviewerProgressReport_AssignmentGroupExpandNew().Visible()  ;}}), Input.wait30);	
	    //getReviewerProgressReport_AssignmentGroupExpandNew().ScrollTo();
	    getReviewerProgressReport_AssignmentGroupExpandNew().waitAndClick(15);
		  Thread.sleep(2000);
	    
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			  getApplyChanges().Visible()  ;}}), Input.wait30);		
	   getApplyChanges().waitAndClick(5);
		
	  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getTally_LoadingScreen().Visible()  ;}}), Input.wait60);
		 
		 
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getReviewerReviewProgress_SummaryTitle().Visible()  ;}}), Input.wait30);	
		  Assert.assertEquals(getReviewerReviewProgress_SummaryTitle().getText(), "Reviewer Progress Summary");
		  
		  //schdeule
			 driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
					 getbtnScheduler().Enabled() ;}}), Input.wait30);
			 getbtnScheduler().Click();
			 
			 driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
					 getSchedulerForm().Visible() ;}}), Input.wait30);
			 getSchedulerForm().Displayed();
			 

			   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					   getSchedulerTime().Visible()  ;}}), Input.wait60);
			   
			   
			   getSchedulerTime().SendKeys(SavedSearch.schdulerTimePlus15Secs());
			   
			   getSchedulerForm_btnSubmit().Click();
			   
			   successMsgConfirmation("Record scheduled successfully"); 
		/*	   
			   SchedulesPage page1 = new SchedulesPage(driver);
			   page1.checkStatusComplete("Review Progress by Reviewer Report"); 	*/	
	   
		//share
		 LoginPage p1 = new LoginPage(driver);
			 p1.getSignoutMenu().Click();
		  Thread.sleep(5000);
		  
		 String projectAdminName = getReviewerProgressReport_LoggedUserName().getText();
		 System.out.println(projectAdminName);
		 UtilityLog.info(projectAdminName);
		 
		 p1.getSignoutMenu().Click();
		  
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getReviewerReviewProgress_Share().Visible()  ;}}), Input.wait30);
		 getReviewerReviewProgress_Share().waitAndClick(10);
		 
		 /*driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
				 getToggleBtn().Visible() ;}}), Input.wait30);
		 getToggleBtn().Click();*/
		 
		 try{
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getFirstUserfromlistNew().Visible()  ;}}), Input.wait30);	
    	//	 for(int i=0;i<getUserslist().size();i++){
		 getFirstUserfromlistNew().waitAndClick(10);
				Thread.sleep(2000);
		 }catch (Exception e) {
			getToggleBtn().waitAndClick(10);
			driver.FindElementByXPath("//*[@id='Edit User Group']/fieldset/div/div[1]/div/div/div/ul/li[1]/a/label/input").waitAndClick(10);
		}
				
		 //getToggleBtn().waitAndClick(5);
		 
		 //text area to enter mail id is getting blocked
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
				 getEmailAddress().Visible() ;}}), Input.wait30);
		 getEmailAddress().SendKeys(usertosharewith);
		 
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
				 getReports_ShareBtn().Enabled() ;}}), Input.wait30);
		 getReports_ShareBtn().Click();
		 
		  successMsgConfirmation("Your Report has been successfully shared with others."); 
		   
		 
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
				 getActivityIcon().Enabled() ;}}), Input.wait30);
		 getActivityIcon().Click();
		 
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
				 getViewAllBtn().Enabled() ;}}), Input.wait30);
		 getViewAllBtn().waitAndClick(15);
		 Thread.sleep(3000);
		
		//Verify 'My Share Notifications' sub-title
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
				 getShareNotification().Visible() ;}}), Input.wait30);
		 Assert.assertEquals(getShareNotification().getText(), "My Share Notifications");
		 
		//Verify Share Type
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
				 getShareReportCol().Visible() ;}}), Input.wait30);
		 Assert.assertEquals(getShareReportCol().getText(), "Share Report");
		 
		//Verify Shared by
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
				 getSharedBy().Visible() ;}}), Input.wait30);
		 Assert.assertEquals(getSharedBy().getText(), projectAdminName);
		 

		
		 
	
	  }
    
    public void successMsgConfirmation(String msg) {
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSuccessMsgHeader().Visible()  ;}}), Input.wait60); 
    	Assert.assertEquals("Success !", getSuccessMsgHeader().getText().toString());
    	Assert.assertEquals(msg, getSuccessMsg().getText().toString());
    	System.out.println("Success msg passed");
    	
	}
   
 
     
}