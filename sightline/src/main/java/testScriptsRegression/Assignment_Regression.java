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
import pageFactory.HomePage;
import pageFactory.LoginPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
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
	
	String assgnname = "Assgnm";
	String codingfrom = "cfC1"+Utility.dynamicNameAppender();
	String assignmentName3= "assignmentA3"+Utility.dynamicNameAppender();
	String assignmentName4= "assignmentA4"+Utility.dynamicNameAppender();
	String assignmentNamecopy = "assignment5"+Utility.dynamicNameAppender();
	String assignmentName= "assignmentA1"+Utility.dynamicNameAppender();
	String assignmentNameMultiRev= "assignmentR2"+Utility.dynamicNameAppender();
	String assgngrpName= "assgnGrp"+Utility.dynamicNameAppender();
	String assgngrpName1= "assgnGrp1"+Utility.dynamicNameAppender();
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
			
		//Create assignment with newly created coding form
		
		agnmt.createAssignment(assignmentName,codingfrom);
	
	  //Search docs and assign to newly created assignment
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString1);
		search.bulkAssign();
		agnmt.assignDocstoExisting(assignmentName);
		
		//Edit assignment and add reviewers 
		agnmt.editAssignment(assignmentName);
		agnmt.addReviewerAndDistributeDocs(assignmentName,Input.pureHitSeachString1); 
		dp = new DocListPage(driver);
		
	}
	
	
	  @Test(groups={"regression"},priority=1)
	  public void AssgnManageRev_ViewInDocView() throws InterruptedException {
		  
		 
		//agnmt.editAssignment(assignmentName);
		agnmt.Assignment_ManageRevtab_ViewinDocView();
		
	}
	  @Test(groups={"regression"},priority=2)
	  public void AssgnManageRev_ViewInDoclist() throws InterruptedException {
		  
		 
		agnmt.editAssgnwithselectedassgn();
		agnmt.Assignment_ManageRevtab_ViewinDoclist();
	
	    dp.getDocList_info().WaitUntilPresent();
	    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    		   !dp.getDocList_info().getText().isEmpty()  ;}}),Input.wait60);
	    Assert.assertEquals(dp.getDocList_info().getText().toString().replaceAll(",", ""),"Showing 1 to 10 of "+Input.pureHitSeachString1+" entries");
	    System.out.println("Expected docs("+Input.pureHitSeachString1+") are shown in doclist");
		
	} 
	  
	  @Test(groups={"regression"},priority=3)
	  public void AssgnwithDocSequnece() throws InterruptedException {
		  
		 
		  agnmt.editAssgnwithselectedassgn();
		  agnmt.Assgnwithdocumentsequence();
		
	}
	
	 @Test(groups={"regression"},priority=4)
	  public void AssignmentDashboard() throws InterruptedException {
		  agnmt.AssignmentDashboard();
		
	}
	  
	  @Test(groups={"regression"},priority=5)
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
	
	
	    @Test(groups={"regression"},priority=6)
		  public void AssignmentGroup() throws InterruptedException {
		  
		  this.driver.getWebDriver().get(Input.url+ "Assignment/ManageAssignment");
		  agnmt.createAssgnGroup(assgngrpName);
		  agnmt.EditAssgnGroup(assgngrpName);	
		  agnmt.DeleteAssgnGroup(assgngrpName);
		}
	    
	    @Test(groups={"regression"},priority=7)
		  public void AssnmGroupwithsubassgn() throws InterruptedException {
		  
		  this.driver.getWebDriver().get(Input.url+ "Assignment/ManageAssignment");
		  agnmt.createAssgnGroup(assgngrpName);
		  agnmt.getAssgnGrp_Select(assgngrpName).waitAndClick(10);
		  agnmt.createAssignment(assignmentName, codingfrom);
		  agnmt.getAssgnGrp_Select(assgngrpName).waitAndClick(10);
		  agnmt.DeleteAssgnGroup(assgngrpName);
		  bc.getYesBtn().waitAndClick(10);
		  bc.VerifyWarningMessage("To delete this group you have to delete its child elements");
		}
	
	
	      @Test(groups={"regression"},priority=8)
		  public void DistributeDoc() throws InterruptedException {
	    	agnmt.createAssignment(assignmentNameMultiRev,codingfrom);
	    	
	    	SessionSearch search = new SessionSearch(driver);
	    	search.basicContentSearch(Input.searchString1);
	    	search.bulkAssign();
	    	agnmt.assignDocstoExisting(assignmentNameMultiRev);
	    		
	    		//Edit assignment and add reviewers 
	    	agnmt.editAssignment(assignmentNameMultiRev);
	    	agnmt.add2ReviewerAndDistributeDocs();
	    	agnmt.RedistributeDoc_ReviewerTab();
		}
	
	   @Test(groups={"regression"},priority=9)
		  public void Assgn_RemoveDoc_ReviewerTab() throws InterruptedException {
			  
			 
			agnmt.editAssignment(assignmentName);
			agnmt.RemoveDoc_ReviewerTab(assignmentName);
			
		}
	   
	   @Test(groups={"regression"},priority=10,dataProvider="Specail char")
		  public void EditAssgn_Disallowspeclchar(String data) throws InterruptedException {
			  
			agnmt.Assgnwithspecialchars(data);
		}
	   
	   @Test(groups={"regression"},priority=11)
		  public void DeleteAssignment() throws InterruptedException {
			  
			 
			agnmt.deleteAssignment(assignmentName);
			}
	
	  @Test(groups={"regression"},priority=12)
      public void assignmentwithpercentmethod() throws InterruptedException
      {
    	assgnname =assgnname+Utility.dynamicNameAppender();
    	agnmt.createAssignment(assgnname,codingfrom);
	 	
	 	//Search docs and assign to newly created assignment
	 	SessionSearch search = new SessionSearch(driver);
	 	search.basicContentSearch(Input.searchString1);
	 	search.bulkAssign();
	 	agnmt.assignDocswithpercentagemethod(assignmentName,"Percentage");
      }
	  
	 @Test(groups={"regression"},priority=13)
      public void NewAssgnUnassgn() throws InterruptedException
      {
	    
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString1);
		search.bulkAssign();
		agnmt.assignDocstoNewAssgn(assignmentName3, codingfrom,Input.pureHitSeachString1);
		
		search.bulkAssign();
		agnmt.UnassignDocsfromAssgn(assignmentName3);
		
      }
	  
	  @Test(groups={"regression"},priority=14)
      public void CopyAssignment() throws InterruptedException
      {
		/*  agnmt.createAssgnGroup(assgngrpName1);
		  agnmt.getAssgnGrp_Select(assgngrpName1).waitAndClick(10);
		*/  agnmt.createAssignment(assignmentName, codingfrom);
		  agnmt.editAssignment(assignmentNamecopy);
		  agnmt.CopyAssignment(assignmentNamecopy, codingfrom);
		  SessionSearch search = new SessionSearch(driver);
		  search.basicContentSearch(Input.searchString1);
		  search.bulkAssign();
		  agnmt.assignDocstoExisting("Copy");
      }
	 	
	  @Test(groups={"regression"},priority=15)
      public void AssgnCompleteDoc() throws InterruptedException
      {
	    
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch("*");
		int pureHit = search.basicContentSearch("*");
		search.bulkAssign();
		agnmt.assignDocstoNewAssgn(assignmentName4, codingfrom,pureHit);
		
		search.bulkAssign();
		agnmt.UnassignDocsfromAssgn(assignmentName3);
		
      }
      
    @Test(groups={"regression"},priority=16)
	   public void ValidateUserlistonQuickbatch() throws InterruptedException, ParseException, IOException {
		 //Login as SA
		lp.logout();
       lp.loginToSightLine(Input.sa1userName, Input.sa1password);

		//create test user
    UserManagement	um = new UserManagement(driver);
   	String firstName = "QBRMU";
   	String lastName =  "QBRMUTest";
   	String emailId = "r.muserconsilio@gmai.com";
    um.createUser(firstName, lastName, "Review Manager", emailId, null, Input.projectName);
	
    bc.impersonateSAtoRMU();
    SessionSearch search = new SessionSearch(driver);
	search.basicContentSearch(Input.searchString1);
	search.quickbatch();
	agnmt.ValidateReviewerlistquickbatch(emailId);
	String assignmentQB1= "assignmentQB1"+Utility.dynamicNameAppender();
	agnmt.createnewquickbatch_chronologic_withoutReviewer(assignmentQB1, codingfrom);
  }
      
	   @Test(groups={"smoke","regression"},priority=17)
	   public void CreateQuickBatchfromsavedsearch() throws InterruptedException, ParseException, IOException {
		
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password); 
		SavedSearch savesearch = new SavedSearch(driver);
		savesearch.savedsearchquickbatch(savedsearchname);
		String assignmentQB5= "assignmentQB5"+Utility.dynamicNameAppender();
		agnmt.createnewquickbatch_Optimized_withReviewer(assignmentQB5, codingfrom,"AllRev");
	   }
	   
	  @Test(groups={"smoke","regression"},priority=18)
	   public void CreateQuickBatchfromdoclist() throws InterruptedException, ParseException, IOException {
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString1);
		search.ViewInDocList();
		dp.DoclisttoQuickbatch("100");
		String assignmentQB2= "assignmentQB2"+Utility.dynamicNameAppender();
		agnmt.createnewquickbatch_Optimized_withReviewer(assignmentQB2, codingfrom,"selectrmu");
	   }
	   
	   
	  @Test(groups={"smoke","regression"},priority=19)
	public void CreateQuickBatchfromTally() throws InterruptedException, ParseException, IOException {
	
	driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
	report.TallyReportButton();
	TallyPage tally = new TallyPage(driver);
	tally.ValidateTallySubTally_QuickBatch();
	String assignmentQB3= "assignmentQB3"+Utility.dynamicNameAppender();
	agnmt.createnewquickbatch_Optimized_withReviewer(assignmentQB3, codingfrom,"selectrev");
  }
		   
   @Test(groups={"smoke","regression"},priority=20)
	   public void CreateQuickBatchfromDocExplorer() throws InterruptedException, ParseException, IOException {
	
    	DocExplorerPage docexp = new DocExplorerPage(driver);
		docexp.DocExplorertoquickBatch();
		String assignmentQB4= "assignmentQB4"+Utility.dynamicNameAppender();
		agnmt.createnewquickbatch_Optimized_withReviewer(assignmentQB4, codingfrom,"AllRev");
	 }
			   
	   @Test(groups={"regression"},priority=21)
	   public void ValidateNameQuickBatchfailure()
	   {
		   DocExplorerPage docexp = new DocExplorerPage(driver);
			docexp.DocExplorertoquickBatch();
			agnmt.Quickbatchfailure();
	   }
	   
	   @Test(groups={"smoke","regression"},priority=22)
	   public void CreateQuickBatchfromadvancedsearch() throws InterruptedException, ParseException, IOException {
		
		SessionSearch advsearch = new SessionSearch(driver);
		advsearch.advancedContentSearch(Input.searchString1);
		advsearch.quickbatch();
		String assignmentQB6= "assignmentQB6"+Utility.dynamicNameAppender();
		agnmt.createnewquickbatch_Optimized_withReviewer(assignmentQB6, codingfrom,"AllRev");
	   }
	   
      
	
	
	@DataProvider(name="Specail char")
	public Object[][] specialcharsmethod() 
	{
		return new Object[][] {{"AssignmentNameWith<test"},{"AssignmentNameWith(test"},{"AssignmentNameWith)test"},
			{"AssignmentNameWith[test"},{"AssignmentNameWith{test"},{"AssignmentNameWith}test"},{"AssignmentNameWith}test"},
			{"AssignmentNameWith:test"},{"AssignmentNameWith'test"},{"AssignmentNameWith~test"},{"AssignmentNameWith*test"},
			{"AssignmentNameWith?test"},{"AssignmentNameWith&test"},{"AssignmentNameWith$test"},{"AssignmentNameWith#test"},
			{"AssignmentNameWith@test"},{"AssignmentNameWith!test"},{"AssignmentNameWith-test"},{"AssignmentNameWith_test"},
			};
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
