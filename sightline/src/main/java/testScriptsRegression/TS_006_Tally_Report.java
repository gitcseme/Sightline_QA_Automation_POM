package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.HomePage;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class TS_006_Tally_Report {
	Driver driver;
	LoginPage lp;
	HomePage home;
	ReportsPage report; 
	BaseClass baseclass;
	
	//String usertosharewith = "smangal@consilio.com";
     
	
	   @BeforeClass(alwaysRun = true)
	   public void beforeClass() throws ParseException, InterruptedException, IOException{
		
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		/* Input in = new Input(); 
		 in.loadEnvConfig(); */
		driver = new Driver();
	    lp = new LoginPage(driver);
	    baseclass = new BaseClass(driver);
	   
	    
		report = new ReportsPage(driver);
	
	}
	   
	   @BeforeMethod
		 public void beforeTestMethod(Method testMethod){
			System.out.println("------------------------------------------");
		    System.out.println("Executing method : " + testMethod.getName());       
		 }
	  @AfterMethod(alwaysRun = true)
		public void takeScreenShot(ITestResult result) {
	 	 if(ITestResult.FAILURE==result.getStatus()){
	 		Utility bc = new Utility(driver);
	 		bc.screenShot(result);
	 		//baseclass.failedStep("login failed");
	 	 }
	 	 System.out.println("Executed :" + result.getMethod().getMethodName());
	 	try{
	 		lp.logout();
	 	}catch (Exception e) {
			// TODO: handle exception
		}
	     }	   
	@Test(groups={"smoke","regression"})
	public void tallyBTagandBFolder() throws InterruptedException {
		
		
		
		String tagName = "R_tag_"+Utility.dynamicNameAppender();
	    String folderName = "R_folder_"+Utility.dynamicNameAppender();
		String SecGroup= "Default Security Group";
		
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseclass.passedStep("login successfull");
		
		
		driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
		baseclass.passedStep("*******report page is visible********");
		report.TallyReportButton();
		baseclass.passedStep("********Tally report page is selected***********");
		
		TallyPage tally = new TallyPage(driver);
		
		baseclass.stepInfo("Test case Id: RPMXCON-56219- TallyBulkTag");
		baseclass.stepInfo("Validating Tally and Tagging Documents under Tally");
		//Bulk Tag
		tally.validateTally();
		tally.tallyActions();
		tally.bulkTag(tagName,1);
		baseclass.passedStep("Bulk Tag added successfully ");
		
		baseclass.stepInfo("Test case Id: RPMXCON-56209- TallyBulkFolder");
		baseclass.stepInfo("Adding Documents To Folder under Tally");
		//Bulk Folder
		tally.tallyActions();
		tally.bulkFolder(folderName,1);
		baseclass.passedStep("Bulk Folder added successfully ");
		
		//lp.logout();
	}
	 
	//Bulk Assign : create assignment then assign docs to it then verify assignment as a reviewer 
	@Test(groups={"smoke","regression"})
	 public void tallyBulkAssign() throws InterruptedException {
		 String tagName = "TallyR_tag_"+Utility.dynamicNameAppender();
		 String codingfrom = "TallyR_CF_"+Utility.dynamicNameAppender();
		 String assignmentName = "TallyAssign"+Utility.dynamicNameAppender();
		 
		 lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		 baseclass.passedStep("******Login successfull*******");
		 baseclass.stepInfo("Test case Id: RPMXCON-48696- TallyBulkAssign");
		 
		   //add tag
		    baseclass.stepInfo("*****Creating Tag*****");
			TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
	    	page.CreateTag(tagName,"Default Security Group");
	    	 baseclass.passedStep("******Tag created *******");
	    	
	    	//add comment field
	    	 baseclass.stepInfo("*****Adding comment field*****");
	    	CommentsPage comments = new CommentsPage(driver);
	    	comments.AddComments("Comment"+Utility.dynamicNameAppender());
	    	 baseclass.passedStep("******Comments Added for Tag created *******");
			
	    	//Create coding for for assignment
	    	 baseclass.stepInfo("*****Creating coding for assignment*****");
			CodingForm cf = new CodingForm(driver);
			cf.createCodingform(codingfrom);
			 baseclass.passedStep("******coding form created *******");
			
			//Create assignment with newly created coding form
			 baseclass.stepInfo("*****Creating assignment with newly created coding form*****");
			AssignmentsPage agnmt = new AssignmentsPage(driver);
			agnmt.createAssignment(assignmentName,codingfrom);
			baseclass.passedStep("******Assignment created with newly created coding form*******");
		
			driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
			report.TallyReportButton();
			
			TallyPage tally = new TallyPage(driver);
			tally.validateTally();
			tally.tallyActions();
			tally.bulkAssign(1);
			agnmt.assignDocstoExisting(assignmentName);
			
			//Edit assignment and add reviewers 
			baseclass.stepInfo("*****Editing assignment and adding reviewers*****");
			agnmt.editAssignment(assignmentName);
			agnmt.addReviewerAndDistributeDocs(assignmentName,Input.totalNumberOfDocs);
			baseclass.passedStep("******Assignment edited and added reviewers*******");
			lp.logout();
			
			//Login as reviewer and verify assignment
			baseclass.stepInfo("*****Login as reviewer and verify assignment*****");
			lp.loginToSightLine(Input.rev1userName, Input.rev1password);
			
			home = new HomePage(driver);
			Boolean found= false;
	        for (WebElement element : home.getAssignmentsList().FindWebElements()) {
				if(element.getText().equalsIgnoreCase(assignmentName)){
					found = true;
					System.out.println(assignmentName +"is assigned to reviewer successfully");
					element.click();
					break;
				}
			}	
	        Assert.assertTrue(found);
	        baseclass.passedStep("******Assignment verified by reviewers*******");
	       // lp.logout();

	}
	 
	 //Run Tally reprot and view docs in doclist and docview
	@Test(groups={"smoke","regression"})
	 public void tallyViewDLandDV() throws InterruptedException {
		 //login
		 lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		 baseclass.passedStep("******Login successfull*******");
		 baseclass.stepInfo("Test case Id: RPMXCON-48698- TallyDocviewAndDocList");
		 final DocListPage dp = new DocListPage(driver);
		 //run tally report
		 baseclass.stepInfo("****Tally Report Page*****");
		 final TallyPage tally = new TallyPage(driver);
		 tally.validateTally();
		 tally.tallyActions();
		
		 //Select doclist view for all docs-----------------------------------------------------------------------------------
		 baseclass.stepInfo("****Selecting DocList View From TallyReport page*****");
		 Actions ac=new Actions(driver.getWebDriver());
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 tally.getView().Visible()  ;}}),Input.wait30);
		 Thread.sleep(1000);
		 ac.moveToElement(tally.getView().getWebElement()).build().perform();
		 tally.getDocListView().Click();
		  try{
				tally.getTallyContinue().waitAndClick(10);
			}catch (Exception e) {
				// TODO: handle exception
			} 
		  baseclass.passedStep("******Selected DocListView successfully*******");
		
		 //view in doclist and verify count
		 baseclass.stepInfo("****Verifing DocList count*****");
	     dp.getDocList_info().WaitUntilPresent();
	     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	     !dp.getDocList_info().getText().isEmpty()  ;}}),Input.wait60);
	     Assert.assertTrue(dp.getDocList_info().getText().toString().replaceAll(",", "").contains(String.valueOf(1202)));
	     System.out.println("Expected docs("+1202+") are shown in doclist");
	     baseclass.passedStep("******Expected docs(\"+1202+\") are shown in doclist*******");

	     //Verify DocView from tally---------------------------------------------------------------------------------------------
	     dp.getBackToSourceBtn().Click();
	     Thread.sleep(8000);
	     baseclass.passedStep("******Back to Tally Report Menut*******");
	     
	    // Actions ac1=new Actions(driver.getWebDriver());
	     baseclass.stepInfo("****Selecting DocView View from TallyReport page*****");
	     tally.tallyActions();
	     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		 tally.getView().Visible()  ;}}),Input.wait30);
	     Thread.sleep(2000);
		 ac.moveToElement(tally.getView().getWebElement()).build().perform();
		 tally.getDocViewView().Click();
		 baseclass.passedStep("******Selected DocviewView successfully*******");
		 
		 //verify count in docview
		 DocViewPage dv= new DocViewPage(driver);
		 //dv.getDocView_info().WaitUntilPresent();
		 try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 baseclass.stepInfo("****Verifing DocView count*****");
		 Assert.assertEquals(dv.getDocView_info().getText().toString(),"of "+1202+" Docs");
		 System.out.println("Expected docs("+1202+") are shown in docView");
		 baseclass.passedStep("******Expected docs(\"+1202+\") are shown in docView*******");
		       
		 //lp.logout();
	}
	 @Test(groups={"regression"})
	 public void subTallyBulkTag() throws InterruptedException {
		 String tagName = "SubTallyR_tag_"+Utility.dynamicNameAppender();
		 String folderName = "SubTallyR_folder_"+Utility.dynamicNameAppender();
		 
		 lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		 baseclass.passedStep("******Login successfull*******");
		 baseclass.stepInfo("Test case Id: RPMXCON-56220- SubTallyBulkTag");
		 
		 driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
		 report.TallyReportButton();
	     final TallyPage tally = new TallyPage(driver);
		 tally.validateTally();
		 tally.tallyActions();
		 baseclass.stepInfo("****Selecting SubTally*****");
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					tally.getTally_actionSubTally().Visible()  ;}}), Input.wait30);
		 tally.getTally_actionSubTally().Click();
		 Thread.sleep(2000);
		 tally.validateSubTally();
		 tally.subTallyActions();
		 
		 //bulk tag
		 baseclass.stepInfo("Tagging Documents under SubTally");
		 tally.bulkTag(tagName, 2);
		 baseclass.passedStep("******Bulk Tag is created under SubTally*******");
		 
		 baseclass.stepInfo("Test case Id: RPMXCON-56210- SubTallyBulkFolder");
		 //bulk folder
		 baseclass.stepInfo("Adding Documents to Folder under SubTally");
		 tally.subTallyActions();
		 tally.bulkFolder(folderName, 2);
		 baseclass.passedStep("******Bulk Folder is created under SubTally*******");
		// lp.logout();
		 

	}
	 
	@Test(groups={"smoke","regression"})
	 public void subTallyBulkAssign() throws InterruptedException {
		 String tagName = "SubTallyR_tag_"+Utility.dynamicNameAppender();
		 String codingfrom = "SubTallyR_CF_"+Utility.dynamicNameAppender();
		 String assignmentName = "SubTallyAssign"+Utility.dynamicNameAppender();
		 
		 lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		 baseclass.passedStep("******Login successfull*******");
		 baseclass.stepInfo("Test case Id: RPMXCON-48697- SubTallyBulkAssign");
		 
		//add tag
		 baseclass.stepInfo("Tagging Documents under SubTally");
			TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
	    	page.CreateTag(tagName,"Default Security Group");
	    	baseclass.passedStep("******Tag created under SubTallyBulkAssign*******");
	    	
	    	//add comment field
	    	baseclass.stepInfo("*****Adding Comment field Under SubTallyBulkAssign****");
	    	CommentsPage comments = new CommentsPage(driver);
	    	comments.AddComments("Comment"+Utility.dynamicNameAppender());
	    	baseclass.passedStep("******Comment Added under SubTallyBulkAssign*******");
			
	    	//Create coding for for assignment
	    	baseclass.stepInfo("*****Creating coding for assignment Under SubTallyBulkAssign****");
			CodingForm cf = new CodingForm(driver);
			cf.createCodingform(codingfrom);
			baseclass.passedStep("******Coding Form Created under SubTallyBulkAssign*******");
			
			//Create assignment with newly created coding form
			baseclass.stepInfo("*****Creating assignment with newly created coding form Under SubTallyBulkAssign****");
			AssignmentsPage agnmt = new AssignmentsPage(driver);
			agnmt.createAssignment(assignmentName,codingfrom);
			baseclass.passedStep("******assignment Created with newly created coding form under SubTallyBulkAssign*******");
		
			driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
			report.TallyReportButton();
			
			 final TallyPage tally = new TallyPage(driver);
			 tally.validateTally();
			 tally.tallyActions();
			 
			 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						tally.getTally_actionSubTally().Visible()  ;}}), Input.wait30);
			 Thread.sleep(2000);
			 tally.getTally_actionSubTally().Click();
			 tally.validateSubTally();
			 tally.subTallyActions();
			
			tally.bulkAssign(2);
			agnmt.assignDocstoExisting(assignmentName);
			
			//Edit assignment and add reviewers 
			baseclass.stepInfo("*****Editing assignment and adding reviewers Under SubTallyBulkAssign****");
			agnmt.editAssignment(assignmentName);
			agnmt.addReviewerAndDistributeDocs(assignmentName,Input.totalNumberOfDocs);
			baseclass.passedStep("******Added reviewers for verifying assignment under SubTallyBulkAssign*******");
			lp.logout();
			
			//Login as reviewer and verify assignment
			lp.loginToSightLine(Input.rev1userName, Input.rev1password);
			baseclass.passedStep("*****Verifying assignment by login as Reviewer under SubTallyBulkAssign******");
			
			home = new HomePage(driver);
			Boolean found= false;
	        for (WebElement element : home.getAssignmentsList().FindWebElements()) {
				if(element.getText().equalsIgnoreCase(assignmentName)){
					found = true;
					System.out.println(assignmentName +"is assigned to reviewer successfully");
					element.click();
					break;
				}
			}	
	        Assert.assertTrue(found); 
	        baseclass.passedStep("****** reviewer verified successfully under SubTallyBulkAssign*******");
	        //lp.logout();

	}
	 @AfterClass(alwaysRun = true)
		public void close(){
			try{ 
				//lp.logout();
			     //lp.quitBrowser();	
				}finally {
					lp.quitBrowser();
				}	
			LoginPage.clearBrowserCache();
		}
	 
}
