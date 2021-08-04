package testScriptsSmoke;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import automationLibrary.Driver;
import configsAndTestData.ConfigLoader;
import executionMaintenance.ExtentTestManager;
import executionMaintenance.UtilityLog;
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
import pageFactory.RedactionPage;
import pageFactory.Utility;

public class TS_011_DocViewNonAudioReviewer {
	Driver driver;
	LoginPage loginPage;
	DocViewPage docView;
	public static int purehits;

	String tagName = "tag" + Utility.dynamicNameAppender();
	String folderName = "folder" + Utility.dynamicNameAppender();

	HomePage hm;
	BaseClass baseClass;
	String newTag = "newTag" + Utility.dynamicNameAppender();
	String codingfrom = "CF" + Utility.dynamicNameAppender();
	String assignmentName = "assi" + Utility.dynamicNameAppender();
	String commentName = "Comt" + Utility.dynamicNameAppender();
	String RedactionLabel = "Default Redaction Tag";
	/*
	 * Author : Suresh Bavihalli Created date: April 2019 Modified date:
	 * Modified by: Description : to assign docs to reviewer, create assignment
	 * with coding form(with tags and comments) and distribute
	 * 
	 */
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws InterruptedException, ParseException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		// Open browser
		Input in = new Input();
		in.loadEnvConfig();
		
		driver = new Driver();
		baseClass = new BaseClass(driver);
		// Login as PA
		loginPage = new LoginPage(driver);		

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		docView = new DocViewPage(driver);
	}

	/*
	 * Author : Suresh Bavihalli Created date: April 2019 Modified date:
	 * Modified by: Description : As a reviewer add comment to document
	 * 
	 */
	@Test(groups = { "smoke", "regression" })
	public void addCommentandcompleteDoc() throws Exception {
		
		        // add tag
				TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
				page.CreateTag(newTag, "Default Security Group");

				// add comment field
				CommentsPage comments = new CommentsPage(driver);
				comments.AddComments(commentName);
						
				// Create coding for for assignment
				CodingForm cf = new CodingForm(driver);
				cf.createCodingform(codingfrom);

				// Create assignment with newly created coding form
				AssignmentsPage agnmt = new AssignmentsPage(driver);
				agnmt.createAssignment(assignmentName, codingfrom);

				// Search docs and assign to newly created assignment
				SessionSearch search = new SessionSearch(driver);
				purehits = search.basicContentSearch(Input.searchString1);
				search.bulkAssign();
				agnmt.assignDocstoExisting(assignmentName);

				// Edit assignment and add reviewers
				agnmt.editAssignment(assignmentName);
				agnmt.addReviewerAndDistributeDocs(assignmentName, purehits);
				loginPage.logout();

				// login as a reviewer and select the specific assignment to review the
				// docs
				loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);

				hm = new HomePage(driver);
				Boolean found = false;
				for (WebElement element : hm.getAssignmentsList().FindWebElements()) {
					if (element.getText().equalsIgnoreCase(assignmentName)) {
						found = true;
						System.out.println(assignmentName + "is assigned to reviewer successfully");
						UtilityLog.info(assignmentName + "is assigned to reviewer successfully");
						element.click();
						break;
					}
				}
				Assert.assertTrue(found);
		
		docView.addCommentToNonAudioDoc("firstcomment");

	}

	/*
	 * Author : Suresh Bavihalli Created date: April 2019 Modified date:
	 * Modified by: Description : As a reviewer add remark to first document
	 * 
	 */
	@Test(groups = { "smoke", "regression" })
	public void addRemarkToFirstDoc() {

		docView.addRemarkNonAudioDoc("FirstRemark2");
	}

	/*
	 * Author : Suresh Bavihalli Created date: April 2019 Modified date:
	 * Modified by: Description : As a reviewer redact the page, search for it and delete the redaction
	 * 
	 */
	@Test(groups = { "smoke", "regression" })
	public void addredaction() throws InterruptedException {
		{
           
			docView.nonAudioPageRedaction(RedactionLabel);
			SessionSearch search = new SessionSearch(driver);
	    	
			//Validate in advance sreach under work product search
			search.switchToWorkproduct();
		    search.selectRedactioninWPS(RedactionLabel);	    
		    //One page should be redacted
		    Assert.assertTrue(search.serarchWP()>=1);
		    
		    //Delete page redaction
		    search.ViewInDocView();
		    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    		docView.getDocView_RedactIcon().Displayed()  ;}}), Input.wait30);   
		    docView.getDocView_RedactIcon().waitAndClick(5);
		    docView.getPreRedaction().waitAndClick(5);
		    docView.getDocView_Annotate_DeleteIcon().waitAndClick(5);
		    baseClass.VerifySuccessMessage("Redaction Removed successfully.");
		    
		    
		}

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result,Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());
		
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);

		}
		System.out.println("Executed :" + result.getMethod().getMethodName());	
		ExtentTestManager.getTest().log(Status.INFO, this.getClass().getSimpleName()+"/"+testMethod.getName());
	}


	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			loginPage.logout();
		} finally {
			loginPage.closeBrowser();;
			loginPage.clearBrowserCache();
		}
	}
}