package testScriptsRegression;

import java.awt.AWTException;
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
	DocViewPage docview;
	public static int purehits;
	
	
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
	public void preCondition() throws ParseException, InterruptedException, IOException, AWTException{
		
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		
	   	
//		Input in = new Input(); in.loadEnvConfig();
		driver = new Driver();
		
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		agnmt = new AssignmentsPage(driver);
		bc = new BaseClass(driver);
        dp = new DocListPage(driver);
		report = new ReportsPage(driver);
		dp = new DocListPage(driver);
		docview = new DocViewPage(driver);
		page = new TagsAndFoldersPage(driver);
	
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
		SavedSearch savesearch = new SavedSearch(driver);
		
		purehits=search.basicContentSearch(Input.searchString1);
		search.saveSearch(savedsearchname);
		
		System.out.println(savedsearchname);
	   
		search.bulkAssign();
		agnmt.assignDocstoExisting(assignmentName);
		
		//Edit assignment and add reviewers 
		agnmt.editAssignment(assignmentName);
		agnmt.addReviewerAndDistributeDocs(assignmentName,Input.pureHitSeachString1); 
		
	
	}
	

	@Test(groups={"regression"},priority=1)
	  public void AssgnManageRev_ViewInDocView() throws InterruptedException, AWTException {
		bc.stepInfo("Test Case Id : RPMXCON-54042 To verify that View All Docs in DocView option is present in Manage Reviewer page.");
		
		agnmt.editAssignment(assignmentName);
		bc.passedStep("****edited Assignment successfully*****");
		agnmt.Assignment_ManageRevtab_ViewinDocView();
		bc.passedStep("**** Assignment viewed in doc view successfully*****");
		
	}
	 @Test(groups={"regression"},priority=2)
	  public void AssgnManageRev_ViewInDoclist() throws InterruptedException{
		  bc.stepInfo("Test Case Id : RPMXCON-54041 To verify that View All Docs in Doclist option is present in Manage Reviewer page.");
		 agnmt.editAssignment(assignmentName);
		 bc.passedStep("****edited Assignment successfully*****");
		Thread.sleep(2000);
		agnmt.Assignment_ManageRevtab_ViewinDoclist();
		bc.passedStep("**** Assignment viewed in doc list successfully*****");
	    dp.getDocList_info().WaitUntilPresent();
	    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    		   !dp.getDocList_info().getText().isEmpty()  ;}}),Input.wait60);
	    Assert.assertEquals(dp.getDocList_info().getText().toString().replaceAll(",", ""),"Showing 1 to 10 of "+purehits+" entries");
	    System.out.println("Expected docs("+purehits+") are shown in doclist");
		
	} 
////	  //tc-1576,1010
	@Test(groups={"regression"},priority=3)
	  public void AssgnwithDocSequnece() throws InterruptedException {
		  bc.stepInfo("Test Case Id : RPMXCON-53600 To verify that in DOCUMENT PRESENTATION SEQUENCE RMU is able to drag and drop the sequencing options in Category tab.");
		 
		  agnmt.editAssignment(assignmentName);
		  bc.passedStep("****edited Assignment successfully*****");
		 
		  agnmt.Assgnwithdocumentsequence();
		  bc.passedStep("**** Assignment with  doc sequence is successful*****");
	}
	
	@Test(groups={"regression"},priority=4)
	  public void AssignmentDashboard() throws InterruptedException {
	bc.stepInfo("Test Case Id : RPMXCON-53617 To verify that in RMU dashboard only 5 Assignments are displayed.");
		  agnmt.AssignmentDashboard();
		  bc.passedStep("**** Assignment seen in dashboard is successful*****");
		
	}
	  
	  @Test(groups={"regression"},priority=5)
	  public void AssignmentManagerevTabActions() throws InterruptedException {
		  bc.stepInfo("Test Case Id : RPMXCON-54027 To verify that RMU can Tag All Docs from Manage Reviewer tab");
		  bc.stepInfo("Test Case Id : RPMXCON-54028 To verify that RMU can Folder All Docs from Manage Reviewer tab");
		// page.CreateTag(tagname,"Default Security Group");
		// page.CreateFolder(foldername,"Default Security Group");
		 agnmt.editAssignment(assignmentName);
		 bc.passedStep("****edited Assignment successfully*****");
		 
		  agnmt.Assignment_ManageRevtab_TagFolder(tagname, foldername);
		  bc.passedStep("**** Tagged the folder successfully*****");
		  Thread.sleep(3000);
		  
		  final TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
		    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		       		tf.getTag_ToggleDocCount().Visible()  ;}}),Input.wait60); 
		  	
		       tf.getTag_ToggleDocCount().waitAndClick(10);
		       tf.getTagandCount(tagname,purehits).WaitUntilPresent();
		       Assert.assertTrue(tf.getTagandCount(tagname,purehits).Displayed());
		       System.out.println(tagname+" could be seen under tags and folder page");
		       bc.passedStep("**** tagname could be seen under tags and folder page*****");
		  
		  //verify tag and folder count
	       tf.getFoldersTab().waitAndClick(10);
	       tf.getFolder_ToggleDocCount().waitAndClick(10);
	       tf.getFolderandCount(foldername, purehits).WaitUntilPresent();
	       Assert.assertTrue(tf.getFolderandCount(foldername, purehits).Displayed());
	       System.out.println(foldername+" could be seen under tags and folder page");
	       bc.passedStep("**** foldername could be seen under tags and folder page*****");
	       
	          agnmt.editAssignment(assignmentName);
	          bc.passedStep("****edited Assignment successfully*****");
		       agnmt.Assignment_ManageRevtab_UnTagUnFolder(tagname, foldername);
		       bc.passedStep("**** UnTagged the folder successfully*****");
		       //verify tag and folder count
		       this.driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
		       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			       		tf.getTag_ToggleDocCount().Visible()  ;}}),Input.wait60); 
		       Thread.sleep(3000);
		       
			       tf.getTag_ToggleDocCount().waitAndClick(10);
			       
			       tf.getTagandCount(tagname,0).WaitUntilPresent();
			      driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			    		   tf.getTagandCount(tagname,0).Visible()  ;}}),Input.wait60); 
			       Assert.assertTrue(tf.getTagandCount(tagname,0).Displayed());
			       System.out.println(tagname+" could be seen under tags and folder page");
			       bc.passedStep("**** tagname could be seen under tags and folder page*****");
				     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			       		tf.getTag_ToggleDocCount().Visible()  ;}}),Input.wait30); 
			  	   tf.getFoldersTab().waitAndClick(10);
			  	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			  			tf.getFolder_ToggleDocCount().Visible()  ;}}),Input.wait30); 
			       tf.getFolder_ToggleDocCount().waitAndClick(10);
			       tf.getFolderandCount(foldername, 0).WaitUntilPresent();
			       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			    		   tf.getFolderandCount(foldername, 0).Visible()  ;}}),Input.wait60); 
			       Assert.assertTrue(tf.getFolderandCount(foldername, 0).Displayed());
			       System.out.println(foldername+" could be seen under tags and folder page");
			       bc.passedStep("**** foldername could be seen under tags and folder page*****");
	  }
	
	
	    
	   @Test(groups={"regression"},priority=7)
		  public void AssnmGroupwithsubassgn() throws InterruptedException {
		  bc.stepInfo("Test Case Id : RPMXCON-53591 To verify that RMU can create subgroup into Assingment Group");
	      bc.stepInfo("Test Case Id : RPMXCON-53596 To verify that RMU cannot delete the assignment group if there are elements in it downline.");
		  this.driver.getWebDriver().get(Input.url+ "Assignment/ManageAssignment");

		  agnmt.createAssgnGroup(assgngrpName);
		  bc.passedStep("**** Assignment grp created successfully*****");
//		  agnmt.deleteAssignment(assignmentName);
		  agnmt.createAssignmentForAssgnGrp(assignmentName3, codingfrom,assgngrpName);
		  bc.passedStep("**** Assignment for Assignment grp created successfully*****");
		  agnmt.getAssgnGrp_Select(assgngrpName).waitAndClick(10);
		  agnmt.getAssgGrptionDropdown().waitAndClick(10);
		  agnmt.getAssgnGrp_Delete().waitAndClick(20);
		  bc.passedStep("**** delete Assignment from the Assignment grp *****");
		  bc.getYesBtn().waitAndClick(10);
		  Thread.sleep(2000);
		  bc.VerifyWarningMessage("To delete this group you have to delete its child elements");
		}
	
	
	     @Test(groups={"regression"},priority=8)
		  public void DistributeDoc() throws InterruptedException {
	    	 bc.stepInfo("Test Case Id : To verify the functionality of the ReDistribute Document for Completed Document.");
	    	agnmt.createAssignment(assignmentNameMultiRev,codingfrom);
	    	bc.passedStep("**** Assignment created successfully*****");
	    	SessionSearch search = new SessionSearch(driver);
	    	bc.selectproject();
	    	search.basicContentSearch(Input.searchString1);
	    	bc.passedStep("**** Basic search is successful*****");
	    	search.bulkAssign();
	    	agnmt.assignDocstoExisting(assignmentNameMultiRev);
	    	bc.passedStep("**** Assignment docs assigned to Multi Rev is successful*****");
	    		
	    		//Edit assignment and add reviewers 
	    	agnmt.editAssignment(assignmentNameMultiRev);
	    	bc.passedStep("****edited Assignment successfully*****");
	    	agnmt.add2ReviewerAndDistributeDocs();
	    	bc.passedStep("****  Multi Rev added is successful*****");
	    	agnmt.RedistributeDoc_ReviewerTab();
	    	bc.passedStep("**** docs redistributed successfully*****");
		}
	
	   @Test(groups={"regression"},priority=9)
		  public void Assgn_RemoveDoc_ReviewerTab() throws InterruptedException {
			  
		   bc.stepInfo("To verify that on selecting Remove Documents will remove the documents for selected Reviewer the Grid View."); 
			agnmt.editAssignment(assignmentName);
			bc.passedStep("****  Multi Rev added is successful*****");
			agnmt.RemoveDoc_ReviewerTab(assignmentName);
			bc.passedStep("****  Remove doc from reviewer tab is successful*****");
		}
	   
	   
	   
	   @Test(groups={"regression"},priority=10,dataProvider="Specail char")
		  public void EditAssgn_Disallowspeclchar(String data) throws InterruptedException {
		   bc.stepInfo("Test Case Id : RPMXCON-54442 Verify that Application disallow special characters in New Assignments screen."); 
			agnmt.Assgnwithspecialchars(data);
			bc.passedStep("****  edit assignment name with special characters are successful*****");
		}
	   
	   @Test(groups={"regression"},priority=29)
		  public void DeleteAssignment() throws InterruptedException {
			bc.stepInfo("Test Case Id : RPMXCON-53608 To verify that RMU can delete the assignment.");   
			agnmt.createAssignment(assignmentName, codingfrom);
			bc.passedStep("****  assignment created successfully*****");
			agnmt.deleteAssignment(assignmentName);
			bc.passedStep("****  assignment deleted successfully*****");
			}
	


	  
	  @Test(groups={"regression"},priority=14)
      public void CopyAssignment() throws InterruptedException
      {
		  bc.stepInfo("Test Case Id : RPMXCON-53754 To verify the funcationality of the Copy Assignment option"); 
		  agnmt.createAssignment(assignmentName, codingfrom);
		  bc.passedStep("****  assignment created successfully*****");
		  agnmt.editAssignment(assignmentName);
		  bc.passedStep("****edited Assignment successfully*****");
		  agnmt.CopyAssignment(assignmentName, codingfrom);
		  bc.passedStep("****Assignment copied successfully*****");
		  SessionSearch search = new SessionSearch(driver);
		  search.basicContentSearch(Input.searchString1);
		  bc.passedStep("**** Basic search is successful*****");
		  search.bulkAssign();
		  bc.passedStep("**** Bulk assign is successful*****");
		  agnmt.assignDocstoExisting("Copy");
		  bc.passedStep("****Copied Assignment docs assigned to Rev is successful*****");
      }
	 	
	  @Test(groups={"regression"},priority=15)
      public void AssgnCompleteDoc() throws InterruptedException
      {
		  bc.stepInfo("Test Case Id : To verify the functionality of the ReDistribute Document for Completed Document.");
		SessionSearch search = new SessionSearch(driver);
		purehits=search.basicContentSearch("*");
		bc.passedStep("**** Basic search is successful*****");
		search.bulkAssign();
		bc.passedStep("**** Bulk assign is successful*****");
		agnmt.assignDocstoNewAssgn(assignmentName4, codingfrom,purehits);
		bc.passedStep("**** docs assigned to new Assignment is successful*****");
		search.bulkAssign();
		agnmt.UnassignDocsfromAssgn(assignmentName4);
		bc.passedStep("**** docs unassigned from Assignment is successful*****");
		
      }
	  
	   @Test(groups={"regression"},priority=16)
	   public void ValidateUserlistonQuickbatch() throws InterruptedException, ParseException, IOException {
		 //Login as SA

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
    SessionSearch search = new SessionSearch(driver);
	search.basicContentSearch(Input.searchString1);
	bc.passedStep("**** basic content search is successful*****");
	search.quickbatch();
	agnmt.ValidateReviewerlistquickbatch(emailId);
	bc.passedStep("**** validated the reviewer list in quick batch is successful*****");
	String assignmentQB1= "assignmentQB1"+Utility.dynamicNameAppender();
	agnmt.createnewquickbatch_chronologic_withoutReviewer(assignmentQB1, codingfrom);
	bc.passedStep("********new quick batch is created from saved search with reviewer in chronological order********");

  }
      
	   @Test(groups={"regression"},priority=17)
	   public void CreateQuickBatchfromsavedsearch() throws InterruptedException, ParseException, IOException {
		   bc.stepInfo("Test Case Id : RPMXCON-54854 Create new Quick Assignment Saved Search");
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password); 
		bc.passedStep("********logged in as a RMU user********");
		SavedSearch savesearch = new SavedSearch(driver);
		
		System.out.println(savedsearchname);
		savesearch.savedsearchquickbatch(savedsearchname);
		bc.passedStep("**** saved search is successful*****");
		String assignmentQB5= "assignmentQB5"+Utility.dynamicNameAppender();
		agnmt.createnewquickbatch_Optimized_withReviewer(assignmentQB5, codingfrom,"AllRev");
		bc.passedStep("********new quick batch is created from saved search with reviewer in Optimized order********");
	   }
	   
	  @Test(groups={"smoke","regression"},priority=18)
	   public void CreateQuickBatchfromdoclist() throws InterruptedException, ParseException, IOException {
		  bc.stepInfo("Test Case Id : RPMXCON-54851 Create new Quick Assignment from Document list");
		bc.selectproject();
		SessionSearch search = new SessionSearch(driver);
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
	   
	   @Test(groups={"smoke","regression"},priority=19)
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
		   
    @Test(groups={"smoke","regression"},priority=20)
	   public void CreateQuickBatchfromDocExplorer() throws InterruptedException, ParseException, IOException {
    	bc.stepInfo("Test Case Id : RPMXCON-54858 Create new Quick Assignment from Document Explorer");
    	DocExplorerPage docexp = new DocExplorerPage(driver);
		docexp.DocExplorertoquickBatch();
		bc.passedStep("**** adding to quick batch from doc explorer is successful*****");
		String assignmentQB4= "assignmentQB4"+Utility.dynamicNameAppender();
		agnmt.createnewquickbatch_Optimized_withReviewer(assignmentQB4, codingfrom,"AllRev");
		bc.passedStep("********new quick batch is created from Doc Explorer with reviewer in Optimized order********"); 
    }
			   
	 @Test(groups={"regression"},priority=21)
	   public void ValidateNameQuickBatchfailure() throws InterruptedException
	   {
		 bc.stepInfo("Test Case Id : RPMXCON-54873 Validate for quick batch failure");
		   DocExplorerPage docexp = new DocExplorerPage(driver);
			docexp.DocExplorertoquickBatch();
			bc.passedStep("**** adding to quick batch from doc explorer is successful*****");
			agnmt.Quickbatchfailure();
	   }
	   
      @Test(groups={"smoke","regression"},priority=22)
	   public void CreateQuickBatchfromadvancedsearch() throws InterruptedException, ParseException, IOException {
    	  bc.stepInfo("Test Case Id : RPMXCON-54853 Create new Quick Assignment (with Chronological sort order, All reviewers added, enable Family Members/Email Threaded Docs/Near Duplicates ) from Advanced search");
		SessionSearch advsearch = new SessionSearch(driver);
		advsearch.advancedContentSearch(Input.searchString1);
		bc.passedStep("**** advance content search is successful*****");
		advsearch.quickbatch();
		bc.passedStep("**** adding to quick batch from advance search is successful*****");
		String assignmentQB6= "assignmentQB6"+Utility.dynamicNameAppender();
		agnmt.createnewquickbatch_Optimized_withReviewer(assignmentQB6, codingfrom,"AllRev");
		bc.passedStep("********new quick batch is created from Advance Search with reviewer in Optimized order********");
	   }
	   
      @Test(groups={"smoke","regression"},priority=23)
	   public void QuickBatchModifyValidation() throws InterruptedException, ParseException, IOException {
    	  bc.stepInfo("Test Case Id : RPMXCON-54859 Modifying assignment created through quick batch");
		   SavedSearch savesearch = new SavedSearch(driver);
			savesearch.savedsearchquickbatch(savedsearchname);
			bc.passedStep("**** adding to quick batch from saved search is successful*****");
			String assignmentQB7= "assignmentQB7"+Utility.dynamicNameAppender();
		   agnmt.createnewquickbatch(assignmentQB7, codingfrom);
		   bc.passedStep("**** new quick batch is created  successfully*****");
	   }

      @Test(groups={"regression"},priority=24)
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
	  
/*
	 * Author : Shilpi Mangal
	 * Created date: April 2020
	 * Modified date: 
	 * Modified by:
	 * Description : As a RMU validate all keyowrds should bydefault added in new assignment  
	 */	
	@Test(groups={"regression"},priority=25)
	public void AssignmentKeywordHighlight() throws InterruptedException
	{
		 bc.stepInfo("Test Case Id : RPMXCON-54753 Existing Assignment - All keywords part of the security group should be selected for existing assignment");
		kp = new KeywordPage(driver);
		String[] keywords = {"test","hi","this","to","all"};
		
		 for(int i=0;i<keywords.length;i++) { 
			 System.out.println(keywords[i]); 
		 try {
		 kp.AddKeyword("AutoKey"+i, keywords[i]); 
		 } catch(Exception e) {
		 System.out.println("keyword already exist");
		 }
		 }
		 
		
		String assignmentkey="Keyword"+Utility.dynamicNameAppender();

		agnmt.createAssignmentwithkeywords(assignmentkey,codingfrom,keywords);
		bc.passedStep("**** assignment with keyword created is successful*****");
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString1);
		bc.passedStep("**** basic content search is successful*****");
		  
		search.bulkAssign();
		bc.passedStep("**** bulk assign is successful*****");
		agnmt.assignDocstoExisting(assignmentkey);
		bc.passedStep("****assignment assigned to existing docs is successful*****");
		agnmt.SelectAssignmentToViewinDocview(assignmentkey);
		bc.passedStep("**** assignment is selected to view in doc view is successful*****");
		 
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			docview.getDocView_info().Visible()  ;}}), Input.wait60);
    	 
    	for(int j=0;j<keywords.length;j++)
		{

    		docview.VerifyPersistentHit(keywords[j]);//edited here
		}
    	
		
	}
	


	/*
	 * Author : Shilpi Mangal
	 * Created date: April 2020
	 * Modified date: 
	 * Modified by:
	 * Description : Test Case 4313:To verify that if System impersonate as RMU, 
	 *    then he is not able to complete documents from Manage Assignment Page
	 *    But Project admin can do it
  
	 */	
	@Test(groups= {"regression"},priority=26)
	public void SAORPATORMUCompleteDocs() throws InterruptedException
	{
		bc.stepInfo("Test Case Id : RPMXCON-54166 To verify that if System impersonate as RMU, then he is not able to complete documents from Manage Assignment Page But Project admin can do it");
		lp.logout();
		bc.passedStep("**** logged out successfully*****");
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		bc.passedStep("**** logged in as system user successfully*****");
		bc.impersonateSAtoRMU();
		bc.passedStep("**** impersonated from system to RMU user is successful*****");
		agnmt.Impersonateusercompletedoc(assignmentName);
		bc.passedStep("**** completed the assigned docs successfully*****");
		lp.logout();
		bc.passedStep("**** logged out successfully*****");
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.passedStep("**** logged in as PA user successfully*****");
		bc.impersonatePAtoRMU();
		bc.passedStep("**** impersonated from PA to RMU user is successful*****");
		agnmt.CompleteAssgn(assignmentName);//changed here
		bc.passedStep("**** completed the assigned docs successfully*****");
		
	}
	

	/*
	 * Author : Shilpi Mangal
	 * Created date: April 2020
	 * Modified date: 
	 * Modified by:
	 * Description : Validate distributed list and results for searching by
	 *               Assignment workproduct
  	 */	
	@Test(groups= {"regression"},priority=27)
	public void Assgnwp() throws InterruptedException
	{
		//Search docs and assign to newly created assignment
		bc.stepInfo("Test Case Id : To verify an an RMU login, I will be able to select multiple Assignment from Assignments column under Work Product tab & set that as a search criteria for advanced search");
		lp.logout();
		bc.passedStep("**** logged out successfully*****");
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.passedStep("**** logged in as RMU user successfully*****");
		SessionSearch search = new SessionSearch(driver);
		search.switchToWorkproduct();
		search.selectAssignmentInASwp(assignmentName);
		bc.passedStep("**** Search for the assignment work product is completed successfully*****");
	}
	
/*
	 * Author : Shilpi Mangal
	 * Created date: April 2020
	 * Modified date: 
	 * Modified by:
	 * Description : Test Case 4737: Assignments displayed on RU Dashboard if
	 *                Draw from Pool option is enabled/disabled.
  	 */	
	@Test(groups= {"regression"},priority=28)
	public void Assgnwithdrawfrompool() throws InterruptedException
	{
		//Search docs and assign to newly created assignment
		bc.stepInfo("Test Case Id : RPMXCON-53598 To verify that if Draw from Pool on then scale Keep families together should change wrt number enter in Allowance of drawn document to");
		agnmt.deleteAssignment(assignmentName);
		
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString1);
		bc.passedStep("**** basic content search is successful*****");
		search.bulkAssign();
		bc.passedStep("**** bulk assign is successful*****");
		
		agnmt.assignDocstoNewAssgn(assignmentName, codingfrom,purehits);	
		bc.passedStep("****assignment assigned to existing docs is successful*****");
		agnmt.editAssignment(assignmentName);
		bc.passedStep("****edited assignment successfully*****");
		agnmt.addReviewerAndDistributeDocs(assignmentName, Input.pureHitSeachString1);
		bc.passedStep("**** docs redistributed successfully*****");
		lp.logout();
		bc.passedStep("**** logged out successfully*****");
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.passedStep("**** logged in as PA user successfully*****");
		bc.impersonatePAtoRMU();
		bc.passedStep("**** impersonated from PA to RMU user is successful*****");
		agnmt.CompleteAssgn(assignmentName);
		bc.passedStep("**** completed the assigned docs successfully*****");
		
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
			}
	}
}