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
	//String usertosharewith = "smangal@consilio.com";

	
	   @BeforeClass(alwaysRun = true)
	   public void beforeClass(){
		
		
		
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		driver = new Driver();
	    lp = new LoginPage(driver);
	    
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
		
		driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
		report.TallyReportButton();
		
		TallyPage tally = new TallyPage(driver);
		
		//Bulk Tag
		tally.validateTally();
		tally.tallyActions();
		tally.bulkTag(tagName,1);
		
		//Bulk Folder
		tally.tallyActions();
		tally.bulkFolder(folderName,1);
		
		lp.logout();
	}
	 
	//Bulk Assign : create assignment then assign docs to it then verify assignment as a reviewer 
	 @Test(groups={"smoke","regression"})
	 public void tallyBulkAssign() throws InterruptedException {
		 String tagName = "TallyR_tag_"+Utility.dynamicNameAppender();
		 String codingfrom = "TallyR_CF_"+Utility.dynamicNameAppender();
		 String assignmentName = "TallyAssign"+Utility.dynamicNameAppender();
		 
		 lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		 
		   //add tag
			TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
	    	page.CreateTag(tagName,"Default Security Group");
	    	
	    	//add comment field
	    	CommentsPage comments = new CommentsPage(driver);
	    	comments.AddComments("Comment"+Utility.dynamicNameAppender());
			
	    	//Create coding for for assignment
			CodingForm cf = new CodingForm(driver);
			cf.createCodingform(codingfrom);
			
			//Create assignment with newly created coding form
			AssignmentsPage agnmt = new AssignmentsPage(driver);
			agnmt.createAssignment(assignmentName,codingfrom);
		
			driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
			report.TallyReportButton();
			
			TallyPage tally = new TallyPage(driver);
			tally.validateTally();
			tally.tallyActions();
			tally.bulkAssign(1);
			agnmt.assignDocstoExisting(assignmentName);
			
			//Edit assignment and add reviewers 
			agnmt.editAssignment(assignmentName);
			agnmt.addReviewerAndDistributeDocs(assignmentName);
			lp.logout();
			
			//Login as reviewer and verify assignment
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
	        
	        lp.logout();

	}
	 
	 //Run Tally reprot and view docs in doclist and docview
	 @Test(groups={"smoke","regression"})
	 public void tallyViewDLandDV() {
		 //login
		 lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		 
		 //run tally report
		 final TallyPage tally = new TallyPage(driver);
		 tally.validateTally();
		 tally.tallyActions();
		
		 //Select doclist view for all docs-----------------------------------------------------------------------------------
		 Actions ac=new Actions(driver.getWebDriver());
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 tally.getView().Visible()  ;}}),Input.wait30);
		 ac.moveToElement(tally.getView().getWebElement()).build().perform();
		 tally.getDocListView().Click();
		 try{
				tally.getTallyContinue().waitAndClick(10);
			}catch (Exception e) {
				// TODO: handle exception
			}
		
		 //view in doclist and verify count
		 final DocListPage dp = new DocListPage(driver);
	     dp.getDocList_info().WaitUntilPresent();
	     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	     !dp.getDocList_info().getText().isEmpty()  ;}}),Input.wait60);
	     Assert.assertTrue(dp.getDocList_info().getText().toString().replaceAll(",", "").contains(String.valueOf(1202)));
	     System.out.println("Expected docs("+1202+") are shown in doclist");

	     //Verify DocView from tally---------------------------------------------------------------------------------------------
	     dp.getBackToSourceBtn().Click();
	       
	     tally.tallyActions();
	     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		 tally.getView().Visible()  ;}}),Input.wait30);
		 ac.moveToElement(tally.getView().getWebElement()).build().perform();
		 tally.getDocViewView().Click();
		 
		 //verify count in docview
		 DocViewPage dv= new DocViewPage(driver);
		 //dv.getDocView_info().WaitUntilPresent();
		 try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 Assert.assertEquals(dv.getDocView_info().getText().toString(),"of "+1202+" Docs");
		 System.out.println("Expected docs("+1202+") are shown in docView");
		       
		 lp.logout();
	}
	 @Test(groups={"regression"})
	 public void subTallyBulkTag() throws InterruptedException {
		 String tagName = "SubTallyR_tag_"+Utility.dynamicNameAppender();
		 String folderName = "SubTallyR_folder_"+Utility.dynamicNameAppender();
		 
		 lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		 
		 driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
		 report.TallyReportButton();
	     final TallyPage tally = new TallyPage(driver);
		 tally.validateTally();
		 tally.tallyActions();
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					tally.getTally_actionSubTally().Visible()  ;}}), Input.wait30);
		 tally.getTally_actionSubTally().Click();
		 tally.validateSubTally();
		 tally.subTallyActions();
		 
		 //bulk tag
		 tally.bulkTag(tagName, 2);
		 
		 //bulk folder
		 tally.subTallyActions();
		 tally.bulkFolder(folderName, 2);
		 
		 lp.logout();
		 

	}
	 
	 @Test(groups={"smoke","regression"})
	 public void subTallyBulkAssign() throws InterruptedException {
		 String tagName = "SubTallyR_tag_"+Utility.dynamicNameAppender();
		 String codingfrom = "SubTallyR_CF_"+Utility.dynamicNameAppender();
		 String assignmentName = "SubTallyAssign"+Utility.dynamicNameAppender();
		 
		 lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		 
		//add tag
			TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
	    	page.CreateTag(tagName,"Default Security Group");
	    	
	    	//add comment field
	    	CommentsPage comments = new CommentsPage(driver);
	    	comments.AddComments("Comment"+Utility.dynamicNameAppender());
			
	    	//Create coding for for assignment
			CodingForm cf = new CodingForm(driver);
			cf.createCodingform(codingfrom);
			
			//Create assignment with newly created coding form
			AssignmentsPage agnmt = new AssignmentsPage(driver);
			agnmt.createAssignment(assignmentName,codingfrom);
		
			driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
			report.TallyReportButton();
			
			 final TallyPage tally = new TallyPage(driver);
			 tally.validateTally();
			 tally.tallyActions();
			 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						tally.getTally_actionSubTally().Visible()  ;}}), Input.wait30);
			 tally.getTally_actionSubTally().Click();
			 tally.validateSubTally();
			 tally.subTallyActions();
			
			tally.bulkAssign(2);
			agnmt.assignDocstoExisting(assignmentName);
			
			//Edit assignment and add reviewers 
			agnmt.editAssignment(assignmentName);
			agnmt.addReviewerAndDistributeDocs(assignmentName);
			lp.logout();
			
			//Login as reviewer and verify assignment
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
	        lp.logout();

	}
	 @AfterClass(alwaysRun = true)
		public void close(){
			try{ 
				lp.logout();
			     //lp.quitBrowser();	
				}finally {
					lp.quitBrowser();
				}	
			LoginPage.clearBrowserCache();
		}
	 
}
