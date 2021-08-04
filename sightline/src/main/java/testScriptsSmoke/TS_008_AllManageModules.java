/*
 * Manage Module to verify all the modules data creations and deletion as Project Admin Review Manager
 * User
 	 * Author : Shilpi M.
	 * Created date: June 2019
	 * Modified date:June 2021 
	 * Modified by: Srinivas Anand
	 
	 *public void createCommentField()
	 *public void createKeywordField()
	 *public void createRedactionLabel()
	 *public void CreateTagField()
	 *public void CreateFolderField()
	 *public void CreateAnnotationLayer()
	 *public void CreateSecurityGroup()
	 *public void DeleteTagField()
	 *public void DeleteFolderField()
	 *public void CreateCodingform()
 * 
 */
package testScriptsSmoke;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import automationLibrary.Driver;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;

import executionMaintenance.ExtentTestManager;
import executionMaintenance.UtilityLog;
import pageFactory.AnnotationLayer;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.RedactionPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;

public class TS_008_AllManageModules {
	Driver driver;
	LoginPage login;
	
	String cfName = "CF"+Utility.dynamicNameAppender();
	String keywordname = "Testkeyword"+Utility.dynamicNameAppender();;
	String keywords = "Test,This,The,To,Do";
	String Tag = "Tag"+Utility.dynamicNameAppender();
	String Folder = "Folder"+Utility.dynamicNameAppender();
	String Comment = "Comment"+Utility.dynamicNameAppender();
	String Redaction = "Redaction"+Utility.dynamicNameAppender();
	String securitygroupname = "securitygroupname"+Utility.dynamicNameAppender();
	String annotationname = "annotationname"+Utility.dynamicNameAppender();
	

	/*
	 * Description : Login as Project Admin
	 */
	@BeforeClass(alwaysRun = true)
	public void before() throws ParseException, InterruptedException, IOException {
	System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
	UtilityLog.info("******Execution started for "+this.getClass().getSimpleName()+"********");
		
	//Input input = new Input();
	//input.loadEnvConfig();
	
	driver = new Driver();
	login = new LoginPage(driver);
	login.loginToSightLine(Input.pa1userName, Input.pa1password);
	}
	
	//Description : Validate if add comment is working correctly
	@Test(priority =1,groups={"smoke","regression"})
	public void createCommentField() throws ParseException, InterruptedException {
		
		//add comment field
    	CommentsPage comments = new CommentsPage(driver);
    	comments.AddComments(Comment);
    }
	
	//Description : Validate if add keyword is working correctly	
	@Test(priority =2,groups={"smoke","regression"})
	public void createKeywordField() throws ParseException, InterruptedException {
		
		KeywordPage page2= new KeywordPage(driver);
		page2.AddKeyword(keywordname, keywords);
		
	}
	
	//Description : Validate if add redaction is working correctly	
	@Test(priority =3,groups={"smoke","regression"})
	public void createRedactionLabel() throws ParseException, InterruptedException {
		
		RedactionPage page3 = new RedactionPage(driver);
		page3.AddRedaction(Redaction,"PA");
		
	}		
	
	//Description : Validate if add tag is working correctly	
	@Test(priority =4,groups={"smoke","regression"})
	public void CreateTagField() throws ParseException, InterruptedException {
		
		TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
    	page.CreateTag(Tag,"Default Security Group");
    }	
	
	
	//Description : Validate if add folder is working correctly	
	@Test(priority =5,groups={"smoke","regression"})
	public void CreateFolderField() throws ParseException, InterruptedException {
		
		TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
		page.CreateFolder(Folder,"Default Security Group");
    	
	}	
		
	//Description : Validate if add annotation layer is working correctly				
	@Test(priority =6,groups={"smoke","regression"})
	public void CreateAnnotationLayer() throws ParseException, InterruptedException {
		
		AnnotationLayer alayer = new AnnotationLayer(driver);
    	alayer.AddAnnotation(annotationname);
    	
	}
	
	//Description : Validate if create security group is working correctly
	@Test(priority =7,groups={"smoke","regression"})
	public void CreateSecurityGroup() throws ParseException, InterruptedException {
		
		SecurityGroupsPage scpage = new SecurityGroupsPage(driver);
    	scpage.AddSecurityGroup(securitygroupname);
    	
   }	
	
	// Description : Validate if delete tag is working correctly
	@Test(priority =8,groups={"smoke","regression"})
	public void DeleteTagField() throws ParseException, InterruptedException {
		
		TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
    	page.DeleteTag(Tag,"Default Security Group");
    	
	}	
	
		
	//Description : Validate if delete folder is working correctly
	@Test(priority =9,groups={"smoke","regression"})
	public void DeleteFolderField() throws ParseException, InterruptedException {
		
		TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
		page.DeleteFolder(Folder,"Default Security Group");
    	
	}
	
	//Description : Validate if add coding form is working correctly
    @Test(priority =10,groups={"smoke","regression"})
	public void CreateCodingform() throws ParseException, InterruptedException {
		
		login.logout();
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		CodingForm cf = new CodingForm(driver);
    	cf.createCodingform(cfName);
    	
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
			Utility bc = new Utility(driver);
			bc.screenShot(result);
	
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}
	@AfterClass(alwaysRun = true)
	public void close(){
		try{ 
			login.logout();		     	
			}finally {
				login.closeBrowser();;
				login.clearBrowserCache();
			}
	}

}