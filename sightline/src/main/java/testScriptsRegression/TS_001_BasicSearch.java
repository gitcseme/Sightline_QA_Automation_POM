package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.IngestionPage;
import pageFactory.LoginPage;
import pageFactory.ProjectFields;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class TS_001_BasicSearch {
	Driver driver;
	LoginPage lp;
	SessionSearch sessionSearch;	
	int pureHit;
	SoftAssert softAssertion;
	SessionSearch ss;
	String CustomFieldname;
	BaseClass bc;
	ProjectFields pf;
	IngestionPage ip;
	/*String tagName = "tagName"+Utility.dynamicNameAppender();
	String folderName = "AFolderName"+Utility.dynamicNameAppender();*/
	
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
    	
		
		softAssertion= new SoftAssert();
//		Input in = new Input(); in.loadEnvConfig();
		
		driver = new Driver();
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);//modified here
		bc = new BaseClass(driver);
		pf = new ProjectFields(driver);
		ip =new IngestionPage(driver);
		ss= new SessionSearch(driver);
		CustomFieldname = Input.CustomFieldname;

	}
	@Test(groups={"regression"},priority=1)
	public void copySearchTextToNewSearch(){
		lp.logout();
		bc.passedStep("********logged in succesfully as RMU user********");
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.passedStep("********logged in succesfully as PA user********");
		bc.stepInfo("Test Case Id : RPMXCON-57088 To verify as an user login into the Application, User will be able to copy the text query from search text box in basic search page via Clipboard & same text user will be able to copy in the new search box in basic search");
		driver.getWebDriver().get(Input.url+ "Search/Searches");
    	bc.selectproject();
    	softAssertion.assertEquals(Input.pureHitSeachString1,ss.basicContentSearch(Input.searchString1));
    	bc.passedStep("********basic content Search is succesful********");
    	//below locators are one time use in second search
    	ss.getCopyTo().Click();
    	bc.passedStep("********Copied the query********");
    	ss.getCopyToNewSearch().Click();
    	ss.getSecondSearchBtn().Click();
    	bc.passedStep("********Copied query search is succesful********");
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			ss.getSecondPureHit().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait90);
    	    	
    	softAssertion.assertEquals(Integer.parseInt(ss.getSecondPureHit().getText()),Input.pureHitSeachString1);
    	bc.passedStep("********Actual pureHit count is same as expected********");

	}
	
	@Test(groups={"regression"},priority=2)
	public void autoSuggest() throws InterruptedException {
	
		bc.passedStep("Test Case Id : RPMXCON-46873 Verify that Auto suggest functionality is working fine with CustodianName Metadata in Basic Search Screen");
		driver.getWebDriver().get(Input.url+ "Search/Searches");
		bc.selectproject();
		driver.getWebDriver().get(Input.url+ "Search/Searches");
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				ss.getBasicSearch_MetadataBtn().Visible()  ;}}), Input.wait30); 
		ss.getBasicSearch_MetadataBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				ss.getSelectMetaData().Visible()  ;}}), Input.wait30); 
		
	    try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ss.getSelectMetaData().selectFromDropdown().selectByVisibleText("CustodianName");
		
			

		ss.getMetaDataSearchText1().SendKeys("P Allen"+Keys.DOWN+Keys.ENTER);
		bc.passedStep("********Search Text is selected from auto suggest successfully********");
		ss.getMetaDataInserQuery().Click();
		  //Click on Search button
		ss.getSearchButton().Click();
  		try {
				
				ss.getTallyContinue().waitAndClick(4000);
			} catch (Exception e) {
				
			}
		
		//verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		ss.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait90);
		Assert.assertEquals(ss.getPureHitsCount().getText(), "1135");
		bc.passedStep("********Actual pureHit count is same as expected********");

	}

	@Test(groups={"regression"},priority=3)
	public void emailInclusive() {
	
		bc.stepInfo("Test Case Id : RPMXCON-49738 Validate for Metadata searches for terms with @ including email addresses in Basic Search");
		bc.selectproject();
		driver.getWebDriver().get(Input.url+ "Search/Searches");
		softAssertion.assertTrue(ss.basicMetaDataSearch("EmailInclusiveReason", null, "*", null)>=1);//1135);
		bc.passedStep("********MetaData Search for EmailInclusiveReason is completed succesfully********");
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.passedStep("********logged in succesfully as RMU user********");
		softAssertion.assertTrue(ss.basicMetaDataSearch("EmailInclusiveReason", null, "*", null)>=1);//1135);
		bc.passedStep("********MetaData Search for EmailInclusiveReason is completed succesfully********");
		softAssertion.assertAll();

	}
	
	@Test(groups={"regression"},priority=0)
	public void conceptuallySimilar() throws InterruptedException {
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.passedStep("********logged in succesfully as RMU user********");
		bc.stepInfo("Test Case Id : RPMXCON-57032 To verify as an user, Custodian Name metadata field in search is working");
		//Impersonate as Reviewer
		Thread.sleep(3000);
		bc.impersonateRMUtoReviewer();	
		ss.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		bc.passedStep("********Custodian MetaDataSearch is completed successfully********");
		ss.getConceptuallyPlayButton().waitAndClick(10);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				ss.getCSHitsCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait90);
		Assert.assertTrue(Integer.parseInt(ss.getCSHitsCount().getText())>= 1);
		bc.passedStep("********Actual pureHitCount is same as Expected********");

	}
	

	
   @Test(groups={"regression"},priority=6)
   public void existingBulkTag() throws InterruptedException {
	   String Tag = "ATag"+Utility.dynamicNameAppender(); 
		//Login as PA
		lp.logout();bc.passedStep("********logged out succesfully as RMU user********");
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.passedStep("********logged in succesfully as PA user********");
		bc.stepInfo("Test Case Id : RPMXCON-57205 Verify that Bulk Tag functionality is working correctly through Basic Search Screen");
	   	//create tag 
	   	TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
		page.CreateTag(Tag,"Default Security Group");
		bc.passedStep("********Tag created successfuly********");
	   	System.out.println("Tag creation is Successful : "+Tag);
   
	   	//Search and add docs to created tag 
	   	sessionSearch = new SessionSearch(driver);
    	sessionSearch.basicContentSearch(Input.searchString1);
    	bc.passedStep("********Basic Content Search is successfuly********");
    	//add docs to folder 
		sessionSearch.bulkTagExisting(Tag);
		bc.passedStep("********Documents are added to the Existing Tag name successfuly********");
		
		//check folder and count in advance search
		
		 bc.selectproject();
		 sessionSearch.switchToWorkproduct();
		 bc.passedStep("********Switched to WorkProduct successfuly********");
		 sessionSearch.selectTagInASwp(Tag);
		 bc.passedStep("********Selected the Existing Tag name********");
		 Assert.assertEquals(Input.pureHitSeachString1,sessionSearch.serarchWP());
		 bc.passedStep("********pureHit Count is same as Expected count********");

		 
	}

    @Test(groups={"smoke","regression"},priority=7)
	public void starSearch() {

		bc.stepInfo("Test Case Id : RPMXCON-57275 Verify that belly band message appears when user tries to search proximity which contains wildcard * in Basic Search Query Screen.");
	  	sessionSearch = new SessionSearch(driver);
	  	Assert.assertEquals(sessionSearch.basicContentSearch("*"), 1202);
	  	bc.passedStep("********pureHit Count is same as Expected count********");

	  	
	}
	@Test(groups={"regression"},priority=8)
    public void bulkUnTag() throws InterruptedException {
		bc.stepInfo("Test Case Id : RPMXCON-57178 Verify that UnTag works properly using Bulk Tag Action in Basic Search Screen");
	   String tagName = "tagName"+Utility.dynamicNameAppender();

	   //perform search
	   sessionSearch = new SessionSearch(driver);
	   sessionSearch.basicContentSearch(Input.searchString1);
	   bc.passedStep("********Basic Content Search is successfuly********");
	   //Bulk Tag
	   sessionSearch.bulkTag(tagName);
	   bc.passedStep("********Bulk Tag the documents to Tag Name is successfull********");
	   //Untag
	   sessionSearch.bulkUnTag(tagName);
	   bc.passedStep("********Bulk UnTag is successfull********");
       final TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
       		tf.getTag_ToggleDocCount().Visible()  ;}}),Input.wait60); 
       
       //Validate tag count in tags tab
       tf.getTag_ToggleDocCount().waitAndClick(20);
       tf.getTagandCount(tagName, 0).waitAndGet(30);
       System.out.println(tf.getTagandCount(tagName, 0).getText());

       Assert.assertTrue(tf.getTagandCount(tagName, 0).Displayed());
       bc.passedStep("********TagCount is displayed under tags and folder page as expected********");
       System.out.println(tagName+" could be seen under tags and folder page");
       

      
   }

	@Test(groups={"regression"},priority=9)
	public void bulkUnFolder() throws InterruptedException {
	
		bc.stepInfo("Test Case Id : RPMXCON-57179 Verify that UnFolder works properly using Bulk Folder Action in Basic Search Screen");
		String folderName = "folderName1"+Utility.dynamicNameAppender();
	     bc.selectproject();
		//Perform search
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.searchString1);
		bc.passedStep("********Basic Content Search is successfuly********");
		
		//Bulk folder
		sessionSearch.bulkFolder(folderName);
		bc.passedStep("********Bulk Folder the documents to Folder Name is successfull********");
		
		//Unfolder
		sessionSearch.bulkUnFolder(folderName);
		bc.passedStep("********Bulk UnFolder is successfull********");
		final TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
	    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    tf.getFoldersTab().Visible()  ;}}),Input.wait60); 
	  	
	    //Validate in folders tab
	    tf.getFoldersTab().waitAndClick(10);
	    tf.getFolder_ToggleDocCount().waitAndClick(10);
	    tf.getFolderandCount(folderName, 0).WaitUntilPresent();
	    
	    System.out.println(tf.getFolderandCount(folderName, 0).getText());
	    Assert.assertTrue(tf.getFolderandCount(folderName, 0).Displayed());
	    bc.passedStep("********FolderCount is displayed under tags and folder page as expected********");
	    System.out.println(folderName+" could be seen under tags and folder page");

	}
	
	@Test(dataProvider="metaDataSearch",groups={"regression"},priority=10)
	public void metaDataSearchsBS(String TestCaseId,int Expected_count, String metaDataName,String IS_or_Range,String
			first_input, String second_input) {
		SoftAssert softAssertion= new SoftAssert();

		driver.getWebDriver().get(Input.url+ "Search/Searches");
		bc.selectproject();
		bc.passedStep("******** Search page is successfully opened********");
		bc.stepInfo(TestCaseId);
		softAssertion.assertEquals(Expected_count,ss.basicMetaDataSearch(metaDataName, IS_or_Range, first_input, second_input));
		softAssertion.assertAll();

	}
	
	@Test(groups={"regression"},priority=12)
	public void creditcardsearch() {
		
		SoftAssert softAssertion= new SoftAssert();
		
		driver.getWebDriver().get(Input.url+ "Search/Searches");
		bc.selectproject();
		bc.passedStep("******** Search page is successfully opened********");
		bc.stepInfo("Test Case Id : RPMXCON-59636 Verify Search result should work correctly for Credit card number");
		softAssertion.assertTrue(ss.basicContentSearch("##[0-9]{4} [0-9]{4} [0-9]{4} [0-9]{2,4}")>=1);
		bc.passedStep("******** Search is successfully done********");
		
	}
	
	@Test(groups={"regression"},priority=13)
	public void basicsearchCustomField() throws InterruptedException {
		try {
			
			lp.logout();
		}
		catch(Exception e) {}
		
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.passedStep("********logged in succesfully as PA user********");
		bc.selectproject();
		 String fieldname = CustomFieldname;
		 SoftAssert softAssertion= new SoftAssert();
		 bc.stepInfo("Test Case Id :RPMXCON-49165 :Verify that Basic Search works properly for CustomField date/time field with 'Is' operator");
		 bc.stepInfo("********Verify that CustomField date/time field is created and perform Basic search********");
		 String Verifyfield = pf.VerifyCustomfield(fieldname);
		 
			if(!Verifyfield.equalsIgnoreCase(fieldname))
			{
		  bc.stepInfo("********Create CustomField date/time field********"); 
	      pf.CreateProjectField(fieldname);
	      bc.passedStep("********CustomField date/time field is created********");
	      bc.stepInfo("********Ingestion of metadata with Custom date/time field********");
	      driver.getWebDriver().get(Input.url+ "Ingestion/Home");
	  
	     ip.ReIngestionofDataWithOverlay(Input.Collection1KFolder,fieldname);
	     bc.passedStep("********Ingestion of metadata with Custom date/time field is completed********");
	     SessionSearch ss = new SessionSearch(driver);
		 driver.getWebDriver().get(Input.url+ "Search/Searches"); 
		
		bc.passedStep("******** Search page is successfully opened********");
		bc.stepInfo("*******Basic Search for CustomField date/time field with 'Is' operator*********");
		softAssertion.assertTrue(ss.basicMetaDataSearch(fieldname,"IS","2000-01-04 19:39:00","")>=1);
		bc.passedStep("*******Basic Search for CustomField date/time field with 'Is' operator is created*********");
		
				 }
				 
		else {
			   
				driver.getWebDriver().get(Input.url+ "Search/Searches"); 
				 SessionSearch ss = new SessionSearch(driver);
				bc.passedStep("******** Search page is successfully opened********");
				bc.stepInfo("*******Basic Search for CustomField date/time field with 'Is' operator*********");
				softAssertion.assertTrue(ss.basicMetaDataSearch(fieldname,"IS","2000-01-04 19:39:00","")>=1);
				bc.passedStep("*******Basic Search for CustomField date/time field with 'Is' operator is created*********");
	  
		}
		
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
 		try{ //if any tc failed and dint logout!
 		lp.logout();
 		}catch (Exception e) {
			// TODO: handle exception
		}
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
	@DataProvider(name = "metaDataSearch")
	public Object[][] metaData() {
		return new Object[][] { 
			{"Test Case Id : RPMXCON-57222 Verify that Basic Search works properly - for CreateDate time metadata - Provide only dates (not times) with Is operator",1,"CreateDate", "IS", "1993-08-11", null}, 
			{"Test Case Id : RPMXCON-57223 Verify that Basic Search works properly - for CreateDate  time metadata - Provide only dates (not times) with Range operator",340,"CreateDate", "RANGE", "1986-01-01", "2018-01-01"},
			{"Test Case Id : RPMXCON-57219 Verify that Basic Search works properly for CreateDate date/time field  with Range operator",170,"CreateDate", "RANGE", "2010-06-17 05:53:18", "2010-10-18 05:53:18"},
			{"Test Case Id : RPMXCON-49149 Verify that Basic Search works properly for EmailSentDate date/time field with Is operator and NOT having time components",0,"EmailSentDate", "IS", "1990-05-05", null},
			{"Test Case Id : RPMXCON-49150 Verify that Basic Search works properly for EmailSentDate date/time field with Range operator and NOT having time components",0,"EmailSentDate", "RANGE","1990-05-05", "2000-05-05"},
			{"Test Case Id : RPMXCON-57135 Verify that application displays correct options for AppointmentStartDateOnly field on Basic Search screen.",0,"AppointmentStartDate", "IS", "1990-05-05", null},
			{"Test Case Id : RPMXCON-49143 Verify that Basic Search works properly for AppointmentStartDate  date/time field  with Range operator",0,"AppointmentStartDate", "RANGE", "1990-05-05", "2000-05-05"},
//			{null,"AppointmentEndDateOnly", "IS", "1990-05-05", null},
//			{null,"AppointmentEndDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
			{"Test Case Id : RPMXCON-57125 Verify that application displays correct options for DocDateDateOnly field on Basic Search screen.",0,"DocDateDateOnly", "IS", "1990-05-05", null},
			{"Test Case Id : RPMXCON-57125 Verify that application displays correct options for DocDateDateOnly field on Basic Search screen.",0,"DocDateDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
//			{"DateAccessedDateOnly", "IS", "1990-05-05", null},
//			{"DateAccessedDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
			{"Test Case Id : RPMXCON-57127 Verify that application displays correct options for DateCreatedDateOnly field on Basic Search screen.",0,"DateCreatedDateOnly", "IS", "1990-05-05", null},
			{"Test Case Id : RPMXCON-57127 Verify that application displays correct options for DateCreatedDateOnly field on Basic Search screen.",26,"DateCreatedDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
//			{"DateEditedDateOnly", "IS", "1990-05-05", null},
//			{"DateEditedDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
//			{"DateModifiedDateOnly", "IS", "1990-05-05", null},
//			{"DateModifiedDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
//			{"DatePrintedDateOnly", "IS", "1990-05-05", null},
//			{"DatePrintedDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
//			{"DateReceivedDateOnly", "IS", "1990-05-05", null},
//			{"DateReceivedDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
//			{"DateSavedDateOnly", "IS", "1990-05-05", null},
//			{"DateSavedDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
			{"Test Case Id : RPMXCON-57003 Verify that Basic Search works properly for MasterDateDateOnly field",0,"MasterDateDateOnly", "IS", "1990-05-05", null},
			{"Test Case Id : RPMXCON-57003 Verify that Basic Search works properly for MasterDateDateOnly field",208,"MasterDateDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
			{"Test Case Id : RPMXCON-57134 Verify that application displays correct options for EmailDateSentDateOnly field on Basic Search screen.",0,"EmailDateSentDateOnly", "IS", "1990-05-05", null},
			{"Test Case Id : RPMXCON-57134 Verify that application displays correct options for EmailDateSentDateOnly field on Basic Search screen.",0,"EmailDateSentDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
//			{"AppointmentStartDateOnly", "IS", "1990-05-05", null},
//			{"AppointmentStartDateOnly", "RANGE", "1990-05-05", "2000-05-05"},	

		};
	}
	
}
