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

	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
    	//bt = new BaseTest();
		//Open browser
		softAssertion= new SoftAssert();
		Input in = new Input(); in.loadEnvConfig();
		driver = new Driver();
		bc = new BaseClass(driver);
		searchText =Input.searchString1;
		//Login as PA
		lp=new LoginPage(driver);
		search = new SessionSearch(driver);
    	lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
    	   		
    	
	}
	
	  
	 @Test(groups={"regression"})
     public void AdvancedWorkproductRedactionSearch() throws InterruptedException {
    
		 
    	String RedactionName = "Redact"+Utility.dynamicNameAppender();
    	redact = new RedactionPage(driver);
		redact.AddRedaction(RedactionName,"RMU");
		System.out.println("Redaction added "+RedactionName);
		
		driver.getWebDriver().get(Input.url+ "Search/Searches");
		search.basicContentSearch("test");
		search.ViewInDocView();
		
		final DocViewPage dc =new DocViewPage(driver);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 dc.getDocView_RedactIcon().Visible()  ;}}), Input.wait60);
		dc.getDocView_RedactIcon().Click();
		 
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 dc.getDocView_RedactThisPage().Visible()  ;}}), Input.wait60);
		dc.getDocView_RedactThisPage().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				dc.getDocView_SelectReductionLabel().Visible()  ;}}), Input.wait60);
		dc.getDocView_SelectReductionLabel().selectFromDropdown().selectByVisibleText(RedactionName);
		dc.getDocView_SelectReductionLabel().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 dc.getRedactionTag_SaveButton().Visible()  ;}}), Input.wait60);
		dc.getRedactionTag_SaveButton().Click();
			
		

		bc.VerifySuccessMessage("Redaction tags saved successfully.");
		
		
		/*bc.VerifySuccessMessage("Redaction tags saved successfully.");*/
		bc.selectproject();
		 
		//Validate in advance sreach under work product search
		search.switchToWorkproduct();
		
	    search.selectRedactioninWPS(RedactionName);
	    Assert.assertEquals(search.serarchWP(), 1);
	    
	 }
	
	 @Test(groups={"regression"})
     public void workproduct_tagFolderSavedSearchAssignment() throws InterruptedException {
    	 String tagName = "tagName"+Utility.dynamicNameAppender();
		 String folderName = "folderName1"+Utility.dynamicNameAppender();
		 String saveSearchName = "A_SaveSearch"+Utility.dynamicNameAppender();
		 String assignMentName = "Assignment"+Utility.dynamicNameAppender();
		 String codingfrom = "CF"+Utility.dynamicNameAppender();
		
		 //Create assignment and assign docs to it
		//add tag for assignment
		TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
		page.CreateTag("newTag"+Utility.dynamicNameAppender(),"Default Security Group");
		    	
		//add comment field for assignment
		CommentsPage comments = new CommentsPage(driver);
		comments.AddComments("Comment"+Utility.dynamicNameAppender());
				
		//Create coding for for assignment
		CodingForm cf = new CodingForm(driver);
		cf.createCodingform(codingfrom);
		
		//Create assignment with newly created coding form
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		agnmt.createAssignment(assignMentName,codingfrom);
	
		//Search docs and assign to newly created assignment
		SessionSearch search = new SessionSearch(driver);
		bc.selectproject();
		search.basicContentSearch(Input.searchString1);
		search.bulkAssign();
		agnmt.assignDocstoExisting(assignMentName);
	
		 //create tag searchString1
		 bc.selectproject();
		 search.advancedContentSearch(Input.searchString1);
		 search.bulkTag(tagName);
		 
		 //save the search searchString1
		 search.saveSearch(saveSearchName);
		 
		 //bulk folder searchString2
		 bc.selectproject();
		 search.advancedContentSearch(Input.searchString2);
		 search.bulkFolder(folderName);
		 
		 		 
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
		 
		 softAssertion.assertAll();
}
	
	    @Test(groups={"regression"})
	    public void exsitingBulkFolder() throws InterruptedException {
			String Folder = "AFolder"+Utility.dynamicNameAppender(); 
			
	    	//create folder 
	    	TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
			page.CreateFolder(Folder,"Default Security Group");
	    	System.out.println("Folder creation is Successful : "+Folder);
	    
	    	//Search and add docs to created folder 
	    	bc = new BaseClass(driver);
			bc.selectproject();
	    	int count =search.advancedContentSearch(Input.searchString1);
		   	search.bulkFolderExisting(Folder);
			
			//check folder and count in advance search
			bc.selectproject();
			bc.selectproject();
			search.switchToWorkproduct();
		    search.selectFolderInASwp(Folder);
			Assert.assertEquals(count,search.serarchWP());
		    
	}
	   @Test(groups={"regression"})
	   public void existingBulkTag() throws InterruptedException {
		   String Tag = "ATag"+Utility.dynamicNameAppender(); 
			
		    //create tag 
		   	TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
			page.CreateTag(Tag,"Default Security Group");
		   	System.out.println("Tag creation is Successful : "+Tag);
	   
		   	//Search and add docs to created tag 
		   	bc = new BaseClass(driver);
			bc.selectproject();
		   	int count = search.advancedContentSearch(Input.searchString1);
		   	search.bulkTagExisting(Tag);
			
			//check tag and count in advance search
			bc.selectproject();
			search.switchToWorkproduct();
			search.selectTagInASwp(Tag);
			 
			Assert.assertEquals(count,search.serarchWP());
			
			
		}
		    @Test(groups={"smoke","regression"})
			public void audioSearch() throws InterruptedException {
		    	
				driver.getWebDriver().get(Input.url+ "Search/Searches");
				bc = new BaseClass(driver);
		    	
				bc.selectproject();
		    	driver.getWebDriver().get(Input.url+ "Search/Searches");
		    	softAssertion.assertTrue(search.audioSearch("spiritual","ventures","North American English","left")==2);
		    	
		    		    	
		    	bc.selectproject();
		    	driver.getWebDriver().get(Input.url+ "Search/Searches");
		    	softAssertion.assertTrue(search.audioSearch("spiritual","ventures","North American English","mid")>=1);
		    	
		    	bc.selectproject();
		    	driver.getWebDriver().get(Input.url+ "Search/Searches");
		    	softAssertion.assertTrue(search.audioSearch("spiritual","ventures","North American English","right")>=0);
		    	
		    	softAssertion.assertAll();
		    	
			}
		 @Test(groups={"smoke","regression"})
		 public void starSerach(){
			 bc = new BaseClass(driver);
		     bc.selectproject();
		     driver.getWebDriver().get(Input.url+ "Search/Searches");
		     Assert.assertEquals(search.advancedContentSearch("*"),Input.totalNumberOfDocs);
		     bc.selectproject();
		     Assert.assertTrue(search.advancedContentSearch("(\"that this\")")>=1);
		     
		     
		 
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
