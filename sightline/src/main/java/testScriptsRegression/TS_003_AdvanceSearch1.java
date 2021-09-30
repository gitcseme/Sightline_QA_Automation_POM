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
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.RedactionPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class TS_003_AdvanceSearch1 {
	Driver driver;
	LoginPage lp;
	String searchText;
	SessionSearch search;	
	SecurityGroupsPage sgpage;
	RedactionPage redact;
	int pureHit;
	BaseClass bc;
	SoftAssert softAssertion;
	DocViewPage dc;
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
    	
		
		softAssertion= new SoftAssert();
//		Input in = new Input(); in.loadEnvConfig();
		driver = new Driver();
		bc = new BaseClass(driver);
		
		//Login as PA
		lp=new LoginPage(driver);
    	lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
    	searchText =Input.searchString1;
    	search = new SessionSearch(driver);
    	dc =new DocViewPage(driver);  		
    	
	}
	
	  
	 @Test(groups={"regression"})
     public void AdvancedWorkproductRedactionSearch() throws InterruptedException {
    
		 bc.passedStep("********logged in succesfully as RMU user********");
			bc.stepInfo("Test Case Id : RPMXCON-57044 To verify an an PA user login, I will be able to select multiple Tags from Redaction Tags column under Work Product tab & set that as a search criteria for advanced search");	 
    	String RedactionName = "Redact"+Utility.dynamicNameAppender();
    	redact = new RedactionPage(driver);
		redact.AddRedaction(RedactionName,"RMU");
		System.out.println("Redaction added "+RedactionName);
		bc.passedStep("********Redaction added successfully********");
		driver.getWebDriver().get(Input.url+ "Search/Searches");
		search.basicContentSearch("test");
		bc.passedStep("********Basic content search is successfull********");
		search.ViewInDocView();
		
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 dc.getDocView_RedactIcon().Visible()  ;}}), Input.wait60);
		dc.getDocView_RedactIcon().VisibilityOfElementExplicitWait(dc.getDocView_RedactIcon(), 2000);
		dc.getDocView_RedactIcon().Click();
		 
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 dc.getDocView_RedactThisPage().Visible()  ;}}), Input.wait60);
		dc.getDocView_RedactThisPage().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				dc.getDocView_SelectReductionLabel().Visible()  ;}}), Input.wait60);
		dc.getDocView_SelectReductionLabel().selectFromDropdown().selectByVisibleText(RedactionName);
		dc.getDocView_SelectReductionLabel().Click();
		bc.passedStep("********Document is added to redationName********");
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 dc.getRedactionTag_SaveButton().Visible()  ;}}), Input.wait60);
		dc.getRedactionTag_SaveButton().Click();
			
		

		bc.VerifySuccessMessage("Redaction tags saved successfully.");
		bc.passedStep("********Redaction tags are saved succesfully********");
		
		/*bc.VerifySuccessMessage("Redaction tags saved successfully.");*/
		bc.selectproject();
		 
		//Validate in advance sreach under work product search
		search.switchToWorkproduct();
		
	    search.selectRedactioninWPS(RedactionName);
	    Assert.assertEquals(search.serarchWP(), 1);
	    bc.passedStep("********Redaction WorkProduct is successful********");
	 }
	
	 @Test(groups={"regression"})
     public void workproduct_tagFolderSavedSearchAssignment() throws InterruptedException {
    	 String tagName = "tagName"+Utility.dynamicNameAppender();
		 String folderName = "folderName1"+Utility.dynamicNameAppender();
		 String saveSearchName = "A_SaveSearch"+Utility.dynamicNameAppender();
		 String assignMentName = "Assignment"+Utility.dynamicNameAppender();
		 String codingfrom = "CF"+Utility.dynamicNameAppender();
		 bc.passedStep("********logged in succesfully as RMU user********");
		 bc.stepInfo("Test Case Id : RPMXCON-57038 To verify an an PA user login, I will be able to select multiple Tags from Tags column under Work Product tab & set that as a search criteria for advanced search");
		 bc.stepInfo("Test Case Id : RPMXCON-57041 To verify an an PA user login, I will be able to select multiple Folder from Folder column under Work Product tab & set that as a search criteria for advanced search");
		 bc.stepInfo("Test Case Id : RPMXCON-57048 To verify an an RMU login, I will be able to select multiple Assignment from Assignments column under Work Product tab & set that as a search criteria for advanced search");
		 bc.stepInfo("Test Case Id : RPMXCON-57066 Verify that  Boolean operator - AND/OR/NOT are working together properly with Plain Text on Advanced Search screen");
		 //Create assignment and assign docs to it
			//add tag for assignment
			TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
			page.CreateTag("newTag"+Utility.dynamicNameAppender(),"Default Security Group");
			bc.passedStep("********Created Tag name succesfully********");   	
			//add comment field for assignment
			CommentsPage comments = new CommentsPage(driver);
			comments.AddComments("Comment"+Utility.dynamicNameAppender());
					
			//Create coding for for assignment
			CodingForm cf = new CodingForm(driver);
			cf.createCodingform(codingfrom);
			
			//Create assignment with newly created coding form
			AssignmentsPage agnmt = new AssignmentsPage(driver);
			agnmt.createAssignment(assignMentName,codingfrom);
			bc.passedStep("********Created Assignment name succesfully********");
			//Search docs and assign to newly created assignment
			SessionSearch search = new SessionSearch(driver);
			bc.selectproject();
			search.basicContentSearch(Input.searchString1);
			bc.passedStep("********Basic content search is succesful********");
			search.bulkAssign();
			agnmt.assignDocstoExisting(assignMentName);
			bc.passedStep("********Bulk assignment of documents is succesful********");
			 //create tag searchString1
			 bc.selectproject();
			 search.advancedContentSearch(Input.searchString1);
			 search.bulkTag(tagName);
			 bc.passedStep("********Bulk tag of documents is succesful********");
			 
			 //save the search searchString1
			 search.saveSearch(saveSearchName);
			 bc.passedStep("********Search is saved succesfully********");
			 
			 //bulk folder searchString2
			 bc.selectproject();
			 search.advancedContentSearch(Input.searchString2);
			 search.bulkFolder(folderName);
			 bc.passedStep("********Bulk Folder of documents is succesful********");
			 
				 
			 //TagAndFolderANDSavedSearch
			 bc.selectproject();
			 search.switchToWorkproduct();
			 search.selectTagInASwp(tagName);
			 search.selectOperator("AND");
			 search.selectFolderInASwp(folderName);
			 search.selectOperator("AND");
			 search.searchSavedSearch(saveSearchName);
			 search.selectOperator("AND");
			 search.selectAssignmentInWPS(assignMentName);
			 softAssertion.assertEquals(3,search.serarchWP());
			 bc.passedStep("********TagAndFolderANDSavedSearch Work Product search is successful********");	
			 bc.passedStep("********pure Hit count is same as expected********");	
			 
			 	
			 //TagOrFolderORsavedSearchs
			 bc.selectproject();
			 search.switchToWorkproduct();
			 search.selectTagInASwp(tagName);
			 search.selectOperator("OR");
			 search.selectFolderInASwp(folderName);
			 search.selectOperator("OR");
			 search.searchSavedSearch(saveSearchName);
			 search.selectOperator("AND");
			 search.selectAssignmentInWPS(assignMentName);
			 softAssertion.assertEquals(53,search.serarchWP());
			 bc.passedStep("********TagOrFolderORsavedSearchs Work Product search is successful********");
			 bc.passedStep("********pure Hit count is same as expected********");
			 softAssertion.assertAll();
		    
	}
	   @Test(groups={"regression"})
	   public void existingBulkTag() throws InterruptedException {
		   String Folder = "AFolder"+Utility.dynamicNameAppender(); 
			bc.passedStep("********logged in succesfully as RMU user********");
			bc.stepInfo("Test Case Id : RPMXCON-57041 To verify an an PA user login, I will be able to select multiple Folder from Folder column under Work Product tab & set that as a search criteria for advanced search");
	    	//create folder 
	    	TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
			page.CreateFolder(Folder,"Default Security Group");
			bc.passedStep("********Created Folder name succesfully********");  
	    	System.out.println("Folder creation is Successful : "+Folder);
	    
	    	//Search and add docs to created folder 
	    	bc = new BaseClass(driver);
			bc.selectproject();
	    	int count =search.advancedContentSearch(Input.searchString1);
	    	bc.passedStep("********Advance content search is succesfully********");
		   	search.bulkFolderExisting(Folder);
		   	bc.passedStep("********Bulk Folder of documents is succesful********");
			
			//check folder and count in advance search
			bc.selectproject();
			
			search.switchToWorkproduct();
		    search.selectFolderInASwp(Folder);
			Assert.assertEquals(count,search.serarchWP());
			bc.passedStep("********Bulk Folder Work Product search is successful********");	
			 bc.passedStep("********pure Hit count is same as expected********");	
		}
		    @Test(groups={"smoke","regression"})
			public void audioSearch() throws InterruptedException {
		    	
		    	bc.passedStep("********logged in succesfully as RMU user********");
		    	bc.stepInfo("Test Case Id : RPMXCON-46877 Verify that Audio search functionality is working proper in Advanced Search");
				driver.getWebDriver().get(Input.url+ "Search/Searches");
				bc = new BaseClass(driver);
		    	
				bc.selectproject();
		    	driver.getWebDriver().get(Input.url+ "Search/Searches");
		    	softAssertion.assertTrue(search.audioSearch("spiritual","ventures","North American English","left")==2);
		    	bc.passedStep("********Audio search is successful********");	
				 bc.passedStep("********pure Hit count is same as expected********");
		    		    	
		    	bc.selectproject();
		    	driver.getWebDriver().get(Input.url+ "Search/Searches");
		    	softAssertion.assertTrue(search.audioSearch("spiritual","ventures","North American English","mid")>=0);
		    	bc.passedStep("********Audio search is successful********");	
				 bc.passedStep("********pure Hit count is same as expected********");
				 
		    	bc.selectproject();
		    	driver.getWebDriver().get(Input.url+ "Search/Searches");
		    	softAssertion.assertTrue(search.audioSearch("spiritual","ventures","North American English","right")>=0);
		    	bc.passedStep("********Audio search is successful********");	
				 bc.passedStep("********pure Hit count is same as expected********");
		    	softAssertion.assertAll();
		    	
			}
		 @Test(groups={"smoke","regression"})
		 public void starSerach(){
			 bc.passedStep("********logged in succesfully as RMU user********");
			 bc.stepInfo("Test Case Id : RPMXCON-57269 Verify that belly band message appears when user tries to search proximity which contains wildcard * in Advanced Search Query Screen.");
			 bc = new BaseClass(driver);
		     bc.selectproject();
		     driver.getWebDriver().get(Input.url+ "Search/Searches");
		     Assert.assertEquals(search.advancedContentSearch("*"),Input.totalNumberOfDocs);
		     bc.passedStep("********Advance Content Search is successful********");
		     bc.selectproject();
		     Assert.assertTrue(search.advancedContentSearch("(\"that this\")")>=1);
		     bc.passedStep("********pureHit Count is same as Expected count********");
		     
		     
		 
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
		LoginPage.clearBrowserCache();
	}
}
