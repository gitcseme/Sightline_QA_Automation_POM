package testScriptsSmoke;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import configsAndTestData.ConfigLoader;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.HomePage;
import pageFactory.LoginPage;
import pageFactory.Utility;


public class TS_011_DocViewNonAudioReviewer {
	Driver driver;
	LoginPage lp;
	DocViewPage docView;
	
	String tagName = "tag"+Utility.dynamicNameAppender();
	String folderName = "folder"+Utility.dynamicNameAppender();
	
	HomePage hm;
	BaseClass bc;
	String newTag = "newtag"+Utility.dynamicNameAppender();
	String  codingfrom = "CF"+Utility.dynamicNameAppender();
	String assignmentName= "assi"+Utility.dynamicNameAppender();;


	 /*
	 * Author : Suresh Bavihalli
	 * Created date: April 2019
	 * Modified date: 
	 * Modified by:
	 * Description : to assign docs to reviewer, create assignment with coding 
	 * form(with tags and comments) and distribute
	 *   
	 */	 
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws InterruptedException, ParseException, IOException {
		
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		//Open browser
		Input in =new Input();
		in.loadEnvConfig();
		driver = new Driver();
		bc = new BaseClass(driver);
		//Login as PA
		lp=new LoginPage(driver);
		
    	lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
    	   		
    	//add tag
		TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
    	page.CreateTag(newTag,"Default Security Group");
    	
    	//add comment field
    	CommentsPage comments = new CommentsPage(driver);
    	comments.AddComments("Comment"+Utility.dynamicNameAppender());
		
    	//Create coding for for assignment
		CodingForm cf = new CodingForm(driver);
		cf.createCodingform(codingfrom);
		
		//Create assignment with newly created coding form
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		agnmt.createAssignment(assignmentName,codingfrom);
	
		//Search docs and assign to newly created assignment
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString1);
		search.bulkAssign();
		agnmt.assignDocstoExisting(assignmentName);
		
		//Edit assignment and add reviewers 
		agnmt.editAssignment(assignmentName);
		agnmt.addReviewerAndDistributeDocs(assignmentName,Input.pureHitSeachString1);
		lp.logout();
		
		//login as a reviewer and select the specific assignment to review the docs
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		
		hm = new HomePage(driver);
		Boolean found= false;
        for (WebElement element : hm.getAssignmentsList().FindWebElements()) {
			if(element.getText().equalsIgnoreCase(assignmentName)){
				found = true;
				System.out.println(assignmentName +"is assigned to reviewer successfully");
				element.click();
				break;
			}
		}	
        Assert.assertTrue(found);
    	        
        docView=new DocViewPage(driver);
	}
	
	 /*
	 * Author : Suresh Bavihalli
	 * Created date: April 2019
	 * Modified date: 
	 * Modified by:
	 * Description : As a reviewer add comment to document
	 *   
	 */	
	@Test(groups={"smoke","regression"})
	public void addCommentToFirstDoc() {
		
		docView.addCommentToNonAudioDoc("firstcomment");
    
	}
	
	 /*
	 * Author : Suresh Bavihalli
	 * Created date: April 2019
	 * Modified date: 
	 * Modified by:
	 * Description : As a reviewer add remark to first document
	 *   
	 */
	@Test(groups={"smoke","regression"})
	public void addRemarkToFirstDoc() {
		
		docView.addRemarkNonAudioDoc("FirstRemark2");
	}
	
	/*
	 * Author : Suresh Bavihalli
	 * Created date: April 2019
	 * Modified date: 
	 * Modified by:
	 * Description : As a reviewer mark the document as complete
	 *   
	 */
	@Test(groups={"smoke","regression"})
	public void completeDoc() {
		docView.completeNonAudioDocument();
		
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
 //    @AfterClass(alwaysRun=true)
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