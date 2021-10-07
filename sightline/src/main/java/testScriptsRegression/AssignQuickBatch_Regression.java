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
	DocExplorerPage docexp;
	SessionSearch search; 
	String codingfrom = "cfC1"+Utility.dynamicNameAppender();
	String tagname = "Assgntag"+Utility.dynamicNameAppender(); 
    String foldername= "Assgnfolder"+Utility.dynamicNameAppender(); 
	String savedsearchname = "Save"+Utility.dynamicNameAppender();
	
	
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException{
		
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		Input in = new Input(); 
		in.loadEnvConfig();
		driver = new Driver();
		
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		agnmt = new AssignmentsPage(driver);
		docexp = new DocExplorerPage(driver);
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
			
		search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString1);
		search.saveSearch(savedsearchname);
		dp = new DocListPage(driver);
		
	}
	
       @Test(groups={"regression"},priority=1)
	   public void ValidateUserlistonQuickbatch() throws InterruptedException, ParseException, IOException {

		   bc.stepInfo("Test Case Id : RPMXCON-54874 Validate reviewers list available in quick batch creation");  
		   
		   lp.logout();
		   bc.passedStep("**** logged out successfully*****");
       lp.loginToSightLine(Input.sa1userName, Input.sa1password);
       bc.passedStep("**** logged in successfully as system user*****");
     
		//create test user
    UserManagement	um = new UserManagement(driver);
   	String firstName = "QBRMU"+Utility.dynamicNameAppender();//"QBRMU";
   	String lastName =  "QBRMUTest";
   	String emailId = "r.muserconsilio"+Utility.dynamicNameAppender()+"@gmai.com";
   	
		/*
		 * try { um.deleteUser(firstName); } catch(Exception e) {
		 * System.out.println("User does not exist"); }
		 */
   	um.createUser(firstName, lastName, "Review Manager", emailId, null, Input.projectName);
   	bc.passedStep("**** new user created successfully*****");
    bc.impersonateSAtoRMU();
    bc.passedStep("**** impersonated to RMU user successfully*****");
    
	search.basicContentSearch(Input.searchString1);
	bc.passedStep("**** basic content search is successful*****");
	search.quickbatch();
	agnmt.ValidateReviewerlistquickbatch(emailId);
	bc.passedStep("**** validated the reviewer list in quick batch is successful*****");
	String assignmentQB1= "assignmentQB1"+Utility.dynamicNameAppender();
	agnmt.createnewquickbatch_chronologic_withoutReviewer(assignmentQB1, codingfrom);
	bc.passedStep("********new quick batch is created from saved search with reviewer in chronological order********");
  }
      
	   @Test(groups={"regression"},priority=2)
	   public void CreateQuickBatchfromsavedsearch() throws InterruptedException, ParseException, IOException {
		   bc.stepInfo("Test Case Id : RPMXCON-54854 Create new Quick Assignment Saved Search");
			lp.logout();
			lp.loginToSightLine(Input.rmu1userName, Input.rmu1password); 
			bc.passedStep("********logged in as a RMU user********");
			SavedSearch savesearch = new SavedSearch(driver);
			
			savesearch.savedsearchquickbatch(savedsearchname);
			bc.passedStep("**** saved search is successful*****");
			String assignmentQB5= "assignmentQB5"+Utility.dynamicNameAppender();
			agnmt.createnewquickbatch_Optimized_withReviewer(assignmentQB5, codingfrom,"AllRev");
			bc.passedStep("********new quick batch is created from saved search with reviewer in Optimized order********");
	   }
	   
	  @Test(groups={"smoke","regression"},priority=3)
	   public void CreateQuickBatchfromdoclist() throws InterruptedException, ParseException, IOException {
		  bc.stepInfo("Test Case Id : RPMXCON-54851 Create new Quick Assignment from Document list");
			bc.selectproject();
			
			search.basicContentSearch(Input.searchString1);
			bc.passedStep("**** basic content search is successful*****");
			search.ViewInDocList();
			bc.passedStep("**** docs viewed in doclist is successful*****");
			dp.DoclisttoQuickbatch("100");
			bc.passedStep("**** adding to quick batch from doclist is successful*****");
			String assignmentQB2= "assignmentQB2"+Utility.dynamicNameAppender();
			agnmt.createnewquickbatch_Optimized_withReviewer(assignmentQB2, codingfrom,"selectrmu");
			bc.passedStep("********new quick batch is created from Doc List with reviewer in Optimized order********");
	   }
	   
	   
	 @Test(groups={"smoke","regression"},priority=4)
	public void CreateQuickBatchfromTally() throws InterruptedException, ParseException, IOException {
		   bc.stepInfo("Test Case Id : RPMXCON-54857 Create new Quick Assignment from Tally/Sub-Tally report");
			driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
			report.TallyReportButton();
			TallyPage tally = new TallyPage(driver);
			tally.ValidateTallySubTally_QuickBatch();
			bc.passedStep("**** adding to quick batch from tally is successful*****");
			String assignmentQB3= "assignmentQB3"+Utility.dynamicNameAppender();
			agnmt.createnewquickbatch_Optimized_withReviewer(assignmentQB3, codingfrom,"selectrev");
			bc.passedStep("********new quick batch is created from Tally Page with reviewer in Optimized order********");
  }
		   
     @Test(groups={"smoke","regression"},priority=5)
	   public void CreateQuickBatchfromDocExplorer() throws InterruptedException, ParseException, IOException {
    	 bc.stepInfo("Test Case Id : RPMXCON-54858 Create new Quick Assignment from Document Explorer");
     	
 		docexp.DocExplorertoquickBatch();
 		bc.passedStep("**** adding to quick batch from doc explorer is successful*****");
 		String assignmentQB4= "assignmentQB4"+Utility.dynamicNameAppender();
 		agnmt.createnewquickbatch_Optimized_withReviewer(assignmentQB4, codingfrom,"AllRev");
 		bc.passedStep("********new quick batch is created from Doc Explorer with reviewer in Optimized order********"); 
	 }
			   
	  @Test(groups={"regression"},priority=6)
	   public void ValidateNameQuickBatchfailure() throws InterruptedException
	   {
		  bc.stepInfo("Test Case Id : RPMXCON-54873 Validate for quick batch failure");
		  
			docexp.DocExplorertoquickBatch();
			bc.passedStep("**** adding to quick batch from doc explorer is successful*****");
			agnmt.Quickbatchfailure();
	   }
	   
      @Test(groups={"smoke","regression"},priority=7)
	   public void CreateQuickBatchfromadvancedsearch() throws InterruptedException, ParseException, IOException {
    	  bc.stepInfo("Test Case Id : RPMXCON-54853 Create new Quick Assignment (with Chronological sort order, All reviewers added, enable Family Members/Email Threaded Docs/Near Duplicates ) from Advanced search");
        bc.selectproject();	
  		search.advancedContentSearch(Input.searchString1);
  		bc.passedStep("**** advance content search is successful*****");
  		search.quickbatch();
  		bc.passedStep("**** adding to quick batch from advance search is successful*****");
  		String assignmentQB6= "assignmentQB6"+Utility.dynamicNameAppender();
  		agnmt.createnewquickbatch_Optimized_withReviewer(assignmentQB6, codingfrom,"AllRev");
  		bc.passedStep("********new quick batch is created from Advance Search with reviewer in Optimized order********");
	   }
	   
      @Test(groups={"smoke","regression"},priority=8)
	   public void QuickBatchModifyValidation() throws InterruptedException, ParseException, IOException {
    	  bc.stepInfo("Test Case Id : RPMXCON-54859 Modifying assignment created through quick batch");
		   SavedSearch savesearch = new SavedSearch(driver);
			savesearch.savedsearchquickbatch(savedsearchname);
			bc.passedStep("**** adding to quick batch from saved search is successful*****");
			String assignmentQB7= "assignmentQB7"+Utility.dynamicNameAppender();
		   agnmt.createnewquickbatch(assignmentQB7, codingfrom);
		   bc.passedStep("**** new quick batch is created  successfully*****");
	   }

       @Test(groups={"regression"},priority=9)
	   public void QuickBatchfromSearchTermDocAuditreport() throws InterruptedException, ParseException, IOException {
		
    	   bc.stepInfo("Test Case Id : RPMXCON-54856 Create new Quick Assignment from Search Term report");
    	      bc.stepInfo("Test Case Id : RPMXCON-54871 Validate Document Audit report for quick batched documents");
 		    SearchTermReportPage st = new SearchTermReportPage(driver);
 		    st.ValidateSearchTermreport(savedsearchname);
 		    st.TermtoQuickbatch();
 		    bc.passedStep("**** adding to quick batch from search term report is successful*****");
 			String assignmentQB8= "assignmentQB8"+Utility.dynamicNameAppender();
 			agnmt.createnewquickbatch_chronologic_withoutReviewer(assignmentQB8, codingfrom);
 			bc.passedStep("********new quick batch is created from  search term report without reviewer in chronologic order********");
 			DocumentAuditReportPage da = new DocumentAuditReportPage(driver);
 			da.ValidateDAreport(savedsearchname, assignmentQB8);
 			bc.passedStep("****report validated successful*****");
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