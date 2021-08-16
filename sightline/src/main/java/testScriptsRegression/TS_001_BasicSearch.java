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
import pageFactory.LoginPage;
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
	
	BaseClass bc;
	/*String tagName = "tagName"+Utility.dynamicNameAppender();
	String folderName = "AFolderName"+Utility.dynamicNameAppender();*/
	
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
    	
		//Open browser
		softAssertion= new SoftAssert();
		Input in = new Input(); in.loadEnvConfig();
		
		driver = new Driver();
		
		bc = new BaseClass(driver);
		ss= new SessionSearch(driver);
		

	}
	@Test(groups={"regression"},priority=1)
	public void copySearchTextToNewSearch(){
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		driver.getWebDriver().get(Input.url+ "Search/Searches");
    	bc.selectproject();
    	softAssertion.assertEquals(Input.pureHitSeachString1,ss.basicContentSearch(Input.searchString1));
    	
    	//below locators are one time use in second search
    	ss.getCopyTo().Click();
    	ss.getCopyToNewSearch().Click();
    	ss.getSecondSearchBtn().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			ss.getSecondPureHit().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait90);
    	    	
    	softAssertion.assertEquals(Integer.parseInt(ss.getSecondPureHit().getText()),Input.pureHitSeachString1);
		lp.logout();
	}
	
	@Test(groups={"regression"},priority=2)
	public void autoSuggest() throws InterruptedException {
		
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
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
		
			
		ss.getMetaDataSearchText1().SendKeys("P Allen");
		Thread.sleep(2000);
		ss.getMetaDataSearchText1().SendKeys(""+Keys.DOWN+Keys.ENTER);
		
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
		lp.logout();

	}

	@Test(groups={"regression"},priority=3)
	public void emailInclusive() {
		
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		softAssertion.assertTrue(ss.basicMetaDataSearch("EmailInclusiveReason", null, "*", null)>=1);//1135);
		lp.logout();
		
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		softAssertion.assertTrue(ss.basicMetaDataSearch("EmailInclusiveReason", null, "*", null)>=1);//1135);
		
		softAssertion.assertAll();
		
		lp.logout();
	}
	
	@Test(groups={"regression"},priority=4)
	public void conceptuallySimilar() throws InterruptedException {
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		//Impersonate as Reviewer
		bc = new BaseClass(driver);
		bc.impersonateRMUtoReviewer();	
		ss.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		ss.getConceptuallyPlayButton().waitAndClick(10);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				ss.getCSHitsCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait90);
		Assert.assertTrue(Integer.parseInt(ss.getCSHitsCount().getText())>= 1);
	
		lp.logout();
	}
	
	@Test(groups={"regression"},priority=5)
    public void exsitingBulkFolder() throws InterruptedException {
		String Folder = "AFolder"+Utility.dynamicNameAppender(); 
		//Login as PA
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
    	
    	//create folder 
    	TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
		page.CreateFolder(Folder,"Default Security Group");
    	System.out.println("Folder creation is Successful : "+Folder);
    
    	//Search and add docs to created folder 
    	sessionSearch = new SessionSearch(driver);
     	sessionSearch.basicContentSearch(Input.searchString1);
	
     	//add docs to folder 
		sessionSearch.bulkFolderExisting(Folder);
		
		//check folder and count in advance search
		
		bc.selectproject();
		sessionSearch.switchToWorkproduct();
		sessionSearch.selectFolderInASwp(Folder);
		Assert.assertEquals(Input.pureHitSeachString1, sessionSearch.serarchWP());
		lp.logout();
        
	}
	
   @Test(groups={"regression"},priority=6)
   public void existingBulkTag() throws InterruptedException {
	   String Tag = "ATag"+Utility.dynamicNameAppender(); 
		//Login as PA
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
   	
	   	//create tag 
	   	TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
		page.CreateTag(Tag,"Default Security Group");
	   	System.out.println("Tag creation is Successful : "+Tag);
   
	   	//Search and add docs to created tag 
	   	sessionSearch = new SessionSearch(driver);
    	sessionSearch.basicContentSearch(Input.searchString1);
	
    	//add docs to folder 
		sessionSearch.bulkTagExisting(Tag);
		
		//check folder and count in advance search
		
		bc.selectproject();
		 sessionSearch.switchToWorkproduct();
		 sessionSearch.selectTagInASwp(Tag);
		 Assert.assertEquals(Input.pureHitSeachString1,sessionSearch.serarchWP());
		lp.logout();
	}
	
    @Test(groups={"smoke","regression"},priority=7)
	public void starSearch() {
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
	  	sessionSearch = new SessionSearch(driver);
    	
	  	Assert.assertEquals(sessionSearch.basicContentSearch("*"), 1202);
	}
	@Test(groups={"regression"},priority=8)
    public void bulkUnTag() throws InterruptedException {
	
	   String tagName = "tagName"+Utility.dynamicNameAppender();
	   //Login
	   lp=new LoginPage(driver);
	   lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		
	   //perform search
	   sessionSearch = new SessionSearch(driver);
	   sessionSearch.basicContentSearch(Input.searchString1);
	   //Bulk Tag
	   sessionSearch.bulkTag(tagName);
	   //Untag
	   sessionSearch.bulkUnTag(tagName);
       final TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
       		tf.getTag_ToggleDocCount().Visible()  ;}}),Input.wait60); 
       
       //Validate tag count in tags tab
       tf.getTag_ToggleDocCount().waitAndClick(20);
       tf.getTagandCount(tagName, 0).waitAndGet(30);
       System.out.println(tf.getTagandCount(tagName, 0).getText());

       Assert.assertTrue(tf.getTagandCount(tagName, 0).Displayed());
       System.out.println(tagName+" could be seen under tags and folder page");
       
       lp.logout();
   }

	@Test(groups={"regression"},priority=9)
	public void bulkUnFolder() throws InterruptedException {
	
		String folderName = "folderName1"+Utility.dynamicNameAppender();
		//Login
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		//Perform search
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.searchString1);
		
		//Bulk folder
		sessionSearch.bulkFolder(folderName);
		
		//Unfolder
		sessionSearch.bulkUnFolder(folderName);
		final TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
	    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    tf.getFoldersTab().Visible()  ;}}),Input.wait60); 
	  	
	    //Validate in folders tab
	    tf.getFoldersTab().waitAndClick(10);
	    tf.getFolder_ToggleDocCount().waitAndClick(10);
	    tf.getFolderandCount(folderName, 0).WaitUntilPresent();
	    
	    System.out.println(tf.getFolderandCount(folderName, 0).getText());
	    Assert.assertTrue(tf.getFolderandCount(folderName, 0).Displayed());

	    System.out.println(folderName+" could be seen under tags and folder page");
	    lp.logout();
	}
	
	@Test(dataProvider="metaDataSearch",groups={"regression"},priority=10)
	public void metaDataSearchsBS(int Expected_count, String metaDataName,String IS_or_Range,String
			first_input, String second_input) {
		SoftAssert softAssertion= new SoftAssert();
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		driver.getWebDriver().get(Input.url+ "Search/Searches");
		bc.selectproject();
		softAssertion.assertEquals(Expected_count,ss.basicMetaDataSearch(metaDataName, IS_or_Range, first_input, second_input));
		softAssertion.assertAll();
		lp.logout();
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
     
   
	//@AfterClass(alwaysRun = true)
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
			{1,"CreateDate", "IS", "1993-08-11", null}, 
			{340,"CreateDate", "RANGE", "1986-01-01", "2018-01-01"},
			{170,"CreateDate", "RANGE", "2010-06-17 05:53:18", "2010-10-18 05:53:18"},
			{0,"EmailSentDate", "IS", "1990-05-05", null},
			{0,"EmailSentDate", "RANGE","1990-05-05", "2000-05-05"},
			{0,"AppointmentStartDate", "IS", "1990-05-05", null},
			{0,"AppointmentStartDate", "RANGE", "1990-05-05", "2000-05-05"},
//			{"AppointmentEndDateOnly", "IS", "1990-05-05", null},
//			{"AppointmentEndDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
			{0,"DocDateDateOnly", "IS", "1990-05-05", null},
			{0,"DocDateDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
//			{"DateAccessedDateOnly", "IS", "1990-05-05", null},
//			{"DateAccessedDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
			{0,"DateCreatedDateOnly", "IS", "1990-05-05", null},
			{26,"DateCreatedDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
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
			{0,"MasterDateDateOnly", "IS", "1990-05-05", null},
			{208,"MasterDateDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
			{0,"EmailDateSentDateOnly", "IS", "1990-05-05", null},
			{0,"EmailDateSentDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
//			{"AppointmentStartDateOnly", "IS", "1990-05-05", null},
//			{"AppointmentStartDateOnly", "RANGE", "1990-05-05", "2000-05-05"},	

		};
	}
	
}
