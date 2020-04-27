package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.DocumentAuditReportPage;
import pageFactory.HomePage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SearchTermReportPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.TallyPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;


public class Assignment_Regression {
	Driver driver;
	LoginPage lp;
	HomePage hm;
	BaseClass bc;
	AssignmentsPage agnmt;
	TagsAndFoldersPage page;
	DocListPage dp;
	ReportsPage report; 
	KeywordPage kp;
	
	
	String assgnname = "Assgnm";
	//String codingfrom = "cfC1"+Utility.dynamicNameAppender();
	String assignmentName3= "assignmentA3"+Utility.dynamicNameAppender();
	String assignmentName4= "assignmentA4"+Utility.dynamicNameAppender();
	String assignmentNamecopy = "assignment5"+Utility.dynamicNameAppender();
	//String assignmentName= "assignmentA1"+Utility.dynamicNameAppender();
	String assignmentNameMultiRev= "assignmentR2"+Utility.dynamicNameAppender();
	String assgngrpName= "assgnGrp"+Utility.dynamicNameAppender();
	String assgngrpName1= "assgnGrp1"+Utility.dynamicNameAppender();
	String tagname = "Assgntag"+Utility.dynamicNameAppender(); 
    String foldername= "Assgnfolder"+Utility.dynamicNameAppender(); 
	String savedsearchname = "Save"+Utility.dynamicNameAppender();
	String assignmentName="Test2";
	String codingfrom = "cfC1866745";

	/*
	 * Author : Shilpi Mangal
	 * Created date: April 2019
	 * Modified date: 
	 * Modified by:
	 * Description : Login as RMU and create an assignment with documents distributed to user,
	 *               from here all the scripts will run. 
	 */	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException{
		
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		
        Input in = new Input();
        in.loadEnvConfig();
			//Open browser
		driver = new Driver();
		
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		agnmt = new AssignmentsPage(driver);
		bc = new BaseClass(driver);
		dp = new DocListPage(driver);
		page = new TagsAndFoldersPage(driver);
		report = new ReportsPage(driver);
	
	}
	
	
	/*
	 * Author : Shilpi Mangal
	 * Created date: April 2020
	 * Modified date: 
	 * Modified by:
	 * Description : Validate distributed list and results for searching by
	 *               Assignment workproduct
  	 */	
	@Test(groups= {"regression"})
	public void Assgnwp() throws InterruptedException
	{
		//Search docs and assign to newly created assignment
		SessionSearch search = new SessionSearch(driver);
		search.switchToWorkproduct();
		search.selectAssignmentInASwp(assignmentName);
	}
	
	
	
	
	
	
	
	/*
	 * Author : Shilpi Mangal
	 * Created date: April 2020
	 * Modified date: 
	 * Modified by:
	 * Description : Test Case 4737: Assignments displayed on RU Dashboard if
	 *                Draw from Pool option is enabled/disabled.
  	 */	
	//@Test
	public void Assgnwithdrawfrompool() throws InterruptedException
	{
		//Search docs and assign to newly created assignment
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString1);
		search.bulkAssign();
		agnmt.assignDocstoNewAssgn(assignmentName, codingfrom);		
		agnmt.addReviewerAndDistributeDocs(assignmentName, Input.pureHitSeachString1);
		lp.logout();
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.impersonatePAtoRMU();
		agnmt.CompleteAssgn(assignmentName);
		
	}
	
	
	
	
	 
	 // @Test(groups={"regression"},priority=5)
	  public void AssignmentManagerevTabActions() throws InterruptedException {
		  
		 page.CreateTag(tagname,"Default Security Group");
		 page.CreateFolder(foldername,"Default Security Group");
		 agnmt.editAssignment(assignmentName);
		  agnmt.Assignment_ManageRevtab_TagFolder(tagname, foldername);
		  Thread.sleep(3000);
		  
		  final TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
		    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		       		tf.getTag_ToggleDocCount().Visible()  ;}}),Input.wait60); 
		  	
		       tf.getTag_ToggleDocCount().waitAndClick(10);
		       tf.getTagandCount(tagname,Input.pureHitSeachString1).WaitUntilPresent();
		       Assert.assertTrue(tf.getTagandCount(tagname,Input.pureHitSeachString1).Displayed());
		       System.out.println(tagname+" could be seen under tags and folder page");
		       
		  
		  //verify tag and folder count
	       tf.getFoldersTab().waitAndClick(10);
	       tf.getFolder_ToggleDocCount().waitAndClick(10);
	       tf.getFolderandCount(foldername, Input.pureHitSeachString1).WaitUntilPresent();
	       Assert.assertTrue(tf.getFolderandCount(foldername, Input.pureHitSeachString1).Displayed());
	       System.out.println(foldername+" could be seen under tags and folder page");
	       
	           agnmt.editAssignment(assignmentName);
	           agnmt.editAssignment(assignmentName);
		       agnmt.Assignment_ManageRevtab_UnTagUnFolder(tagname, foldername);
		       //verify tag and folder count
		       this.driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
		       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			       		tf.getTag_ToggleDocCount().Visible()  ;}}),Input.wait60); 
		       Thread.sleep(3000);
			       tf.getTag_ToggleDocCount().waitAndClick(10);
			       tf.getTagandCount(tagname,0).WaitUntilPresent();
			       Assert.assertTrue(tf.getTagandCount(tagname,0).Displayed());
			       System.out.println(tagname+" could be seen under tags and folder page");
		       
				     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			       		tf.getTag_ToggleDocCount().Visible()  ;}}),Input.wait30); 
			  	   tf.getFoldersTab().waitAndClick(10);
			       tf.getFolder_ToggleDocCount().waitAndClick(10);
			       tf.getFolderandCount(foldername, 0).WaitUntilPresent();
			       Assert.assertTrue(tf.getFolderandCount(foldername, 0).Displayed());
			       System.out.println(foldername+" could be seen under tags and folder page");
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
			}
	}
}