package testScriptsSmoke;


import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.openqa.selenium.WebElement;
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
import pageFactory.HomePage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class TS_009_ValidateAssignmentFunctionality {
	Driver driver;
	LoginPage lp;
	HomePage hm;
	BaseClass bc;	
	AssignmentsPage agnmt;
	CodingForm cf;
	
	String  codingfrom = "cfC1"+Utility.dynamicNameAppender();
	String assignmentName= "assignmentA1"+Utility.dynamicNameAppender();
	
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException{
	
		
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");

		Input in = new Input();
		in.loadEnvConfig();
		
   
 	//Open browser
		driver = new Driver();
		//Login as a PA
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
		//add tag
				TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
				page.CreateTag("newTag"+Utility.dynamicNameAppender(),"Default Security Group");
				    	
				//add comment field
				CommentsPage comments = new CommentsPage(driver);
				comments.AddComments("Comment"+Utility.dynamicNameAppender());
						
				//Create coding for for assignment
				 cf = new CodingForm(driver);
				cf.createCodingform(codingfrom);
				agnmt = new AssignmentsPage(driver);
				
	}
	
	  @Test(groups={"smoke","regression"},priority=1)
	   public void CreateQuickBatch() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
	
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString1);
		search.quickbatch();
		agnmt.createnewquickbatch(assignmentName, codingfrom, Input.searchString1);
	   }
	
	  @Test(groups={"smoke","regression"},priority=2)
	   public void CreateAssignmentDistributeToReviwer() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
	
		
		//Create assignment with newly created coding form
		
		agnmt.createAssignment(assignmentName,codingfrom);
	
		//Search docs and assign to newly created assignment
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString1);
		search.bulkAssign();
		agnmt.assignDocstoExisting(assignmentName);
		
		//Edit assignment and add reviewers 
		agnmt.editAssignment(assignmentName);
		agnmt.addReviewerAndDistributeDocs(assignmentName, Input.pureHitSeachString1);
		lp.logout();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		
		hm = new HomePage(driver);
		Boolean found= false;
        for (WebElement element : hm.getAssignmentsList().FindWebElements()) {
			if(element.getText().equalsIgnoreCase(assignmentName)){
				found = true;
				System.out.println(assignmentName +"is assigned to reviewer successfully");
				break;
			}
		}	
        Assert.assertTrue(found);
       
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
 	
     }
	@AfterClass(alwaysRun = true)
	public void close(){
		try{ 
			lp.logout();
		     //lp.quitBrowser();	
			}finally {
				lp.quitBrowser();
				lp.clearBrowserCache();
			}
	}
}
