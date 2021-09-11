package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
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
import pageFactory.DocumentAuditReportPage;
import pageFactory.HomePage;
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


public class AssignQuickBatch_Regression {
	Driver driver;
	LoginPage lp;
	HomePage hm;
	BaseClass bc;
	AssignmentsPage agnmt;
	TagsAndFoldersPage page;
	DocListPage dp;
	ReportsPage report; 
	
	String codingfrom = "cfC1"+Utility.dynamicNameAppender();
	String tagname = "Assgntag"+Utility.dynamicNameAppender(); 
    String foldername= "Assgnfolder"+Utility.dynamicNameAppender(); 
	String savedsearchname = "Save"+Utility.dynamicNameAppender();
	
	
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException{
		
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
				//Open browser
		driver = new Driver();
		
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		agnmt = new AssignmentsPage(driver);
		bc = new BaseClass(driver);
		report = new ReportsPage(driver);
	
		//add tag
		page = new TagsAndFoldersPage(driver);
		page.CreateTag("newTag"+Utility.dynamicNameAppender(),"Default Security Group");
		    	
		//add comment field
		CommentsPage comments = new CommentsPage(driver);
		comments.AddComments("Comment"+Utility.dynamicNameAppender());
				
		//Create coding for for assignment
		CodingForm cf = new CodingForm(driver);
		cf.createCodingform(codingfrom);
			
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString1);
		search.saveSearch(savedsearchname);
		dp = new DocListPage(driver);
		
	}
	
       @Test(groups={"regression"},priority=1)
	   public void ValidateUserlistonQuickbatch() throws InterruptedException, ParseException, IOException {
		 //Login as SA
		lp.logout();
       lp.loginToSightLine(Input.sa1userName, Input.sa1password);
     
		//create test user
    UserManagement	um = new UserManagement(driver);
   	String firstName = "QBRMU";
   	String lastName =  "QBRMUTest";
   	String emailId = "r.muserconsilio@gmai.com";

		/*
		 * try { um.deleteUser(firstName); } catch(Exception e) {
		 * System.out.println("User does not exist"); }
		 */
   	try {
   	um.createUser(firstName, lastName, "Review Manager", emailId, null, Input.projectName);
   	}
   	catch (Exception e)
   	{
   		System.out.println("User already exist");
   	}
    bc.impersonateSAtoRMU();
    SessionSearch search = new SessionSearch(driver);
	search.basicContentSearch(Input.searchString1);
	search.quickbatch();
	agnmt.ValidateReviewerlistquickbatch(emailId);
	String assignmentQB1= "assignmentQB1"+Utility.dynamicNameAppender();
	agnmt.createnewquickbatch_chronologic_withoutReviewer(assignmentQB1, codingfrom);
  }
      
	   @Test(groups={"regression"},priority=2)
	   public void CreateQuickBatchfromsavedsearch() throws InterruptedException, ParseException, IOException {
		
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password); 
		SavedSearch savesearch = new SavedSearch(driver);
		savesearch.savedsearchquickbatch(savedsearchname);
		String assignmentQB5= "assignmentQB5"+Utility.dynamicNameAppender();
		agnmt.createnewquickbatch_Optimized_withReviewer(assignmentQB5, codingfrom,"AllRev");
	   }
	   
	  @Test(groups={"smoke","regression"},priority=3)
	   public void CreateQuickBatchfromdoclist() throws InterruptedException, ParseException, IOException {
		bc.selectproject();
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString1);
		search.ViewInDocList();
		dp.DoclisttoQuickbatch("100");
		String assignmentQB2= "assignmentQB2"+Utility.dynamicNameAppender();
		agnmt.createnewquickbatch_Optimized_withReviewer(assignmentQB2, codingfrom,"selectrmu");
	   }
	   
	   
	 @Test(groups={"smoke","regression"},priority=4)
	public void CreateQuickBatchfromTally() throws InterruptedException, ParseException, IOException {
	
	driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
	report.TallyReportButton();
	TallyPage tally = new TallyPage(driver);
	tally.ValidateTallySubTally_QuickBatch();
	String assignmentQB3= "assignmentQB3"+Utility.dynamicNameAppender();
	agnmt.createnewquickbatch_Optimized_withReviewer(assignmentQB3, codingfrom,"selectrev");
  }
		   
     @Test(groups={"smoke","regression"},priority=5)
	   public void CreateQuickBatchfromDocExplorer() throws InterruptedException, ParseException, IOException {
	
    	DocExplorerPage docexp = new DocExplorerPage(driver);
		docexp.DocExplorertoquickBatch();
		String assignmentQB4= "assignmentQB4"+Utility.dynamicNameAppender();
		agnmt.createnewquickbatch_Optimized_withReviewer(assignmentQB4, codingfrom,"AllRev");
	 }
			   
	  @Test(groups={"regression"},priority=6)
	   public void ValidateNameQuickBatchfailure() throws InterruptedException
	   {
		   DocExplorerPage docexp = new DocExplorerPage(driver);
			docexp.DocExplorertoquickBatch();
			agnmt.Quickbatchfailure();
	   }
	   
      @Test(groups={"smoke","regression"},priority=7)
	   public void CreateQuickBatchfromadvancedsearch() throws InterruptedException, ParseException, IOException {
		
		SessionSearch advsearch = new SessionSearch(driver);
		advsearch.advancedContentSearch(Input.searchString1);
		advsearch.quickbatch();
		String assignmentQB6= "assignmentQB6"+Utility.dynamicNameAppender();
		agnmt.createnewquickbatch_Optimized_withReviewer(assignmentQB6, codingfrom,"AllRev");
	   }
	   
      @Test(groups={"smoke","regression"},priority=8)
	   public void QuickBatchModifyValidation() throws InterruptedException, ParseException, IOException {
		
		   SavedSearch savesearch = new SavedSearch(driver);
			savesearch.savedsearchquickbatch(savedsearchname);
			String assignmentQB7= "assignmentQB7"+Utility.dynamicNameAppender();
		   agnmt.createnewquickbatch(assignmentQB7, codingfrom);
	   }

       @Test(groups={"regression"},priority=9)
	   public void QuickBatchfromSearchTermDocAuditreport() throws InterruptedException, ParseException, IOException {
		

		    SearchTermReportPage st = new SearchTermReportPage(driver);
		    st.ValidateSearchTermreport(savedsearchname);
		    st.TermtoQuickbatch();
			String assignmentQB8= "assignmentQB8"+Utility.dynamicNameAppender();
			agnmt.createnewquickbatch_chronologic_withoutReviewer(assignmentQB8, codingfrom);
			DocumentAuditReportPage da = new DocumentAuditReportPage(driver);
			da.ValidateDAreport(savedsearchname, assignmentQB8);
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