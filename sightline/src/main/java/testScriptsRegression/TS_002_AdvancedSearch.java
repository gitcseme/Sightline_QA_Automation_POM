package testScriptsRegression;

import java.io.IOException;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;
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
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class TS_002_AdvancedSearch {
	Driver driver;
	LoginPage lp;
	String searchText;
	SessionSearch search;	
	SecurityGroupsPage sgpage;
	RedactionPage redact;
	SoftAssert softAssertion;
	int pureHit;
	BaseClass bc;
	String CustomFieldname;
	ProjectFields pf;
	IngestionPage ip;
	//
	/*
	 * Author : Suresh Bavihalli
	 * Created date: Feb 2019
	 * Modified date: 
	 * Modified by:
	 * Description : Login as PA, all test methods start executing from login page
	 */
		@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
    
	
		softAssertion= new SoftAssert();
		
//		Input in = new Input(); 
//		in.loadEnvConfig();
		driver = new Driver();
		
		//Login as PA
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		bc = new BaseClass(driver);
		bc.passedStep("********logged in succesfully as PA user********");
		pf = new ProjectFields(driver);
		ip =new IngestionPage(driver);
		CustomFieldname = Input.CustomFieldname;
		searchText =Input.searchString1;
		search = new SessionSearch(driver);
		
    	
    
	}
		
		/*
		 * Author : Suresh Bavihalli
		 * Created date: Feb 2019
		 * Modified date: 
		 * Modified by:
		 * Description : As a PA user validate metadata search with string search in advanced search with an operators  
		 */	
		@Test(groups={"regression"},priority=1)
	   	public void metaSearchWithOperatorsInASreg() {
	   		SoftAssert softAssertion= new SoftAssert();
	   		driver.getWebDriver().get(Input.url+ "Search/Searches");
	   		driver.waitForPageToBeReady();
	   		bc.selectproject();
	   		bc.stepInfo("Test Case Id : RPMXCON-57050 Verify that configured query with custodianName get inserted properly into Advanced Search Query builder screen");
			softAssertion.assertTrue(search.advancedContentSearch("CustodianName: (  P Allen)"+Keys.ENTER+"OR"+Keys.ENTER+Input.searchString1)>=1166);

			bc.selectproject();
			bc.stepInfo("Test Case Id : RPMXCON-57050 Verify that configured query with custodianName get inserted properly into Advanced Search Query builder screen");
			softAssertion.assertTrue(search.advancedContentSearch("CustodianName: (  P Allen)"+Keys.ENTER+"AND"+Keys.ENTER+Input.searchString1)>=19);
			
			bc.selectproject();
			bc.stepInfo("Test Case Id : RPMXCON-57050 Verify that configured query with custodianName get inserted properly into Advanced Search Query builder screen");
			softAssertion.assertTrue(search.advancedContentSearch("CustodianName: (  P Allen)"+Keys.ENTER+"NOT"+Keys.ENTER+Input.searchString1)>=1116);

			bc.selectproject();
			bc.stepInfo("Test Case Id : RPMXCON-57050 Verify that configured query with custodianName get inserted properly into Advanced Search Query builder screen");
			softAssertion.assertTrue(search.advancedContentSearch(Input.searchString1+Keys.ENTER+"NOT"+Keys.ENTER+"CustodianName: (  P Allen)")>0);

			//Proximity seach
			bc.selectproject();
			bc.stepInfo("Test Case Id : RPMXCON-57050 Verify that configured query with custodianName get inserted properly into Advanced Search Query builder screen");
			softAssertion.assertTrue(search.advancedContentSearch("\"discrepancy scripts\"~3")>=4);

			softAssertion.assertAll();
	    }
	    
	    /*
		 * Author : Suresh Bavihalli
		 * Created date: Feb 2019
		 * Modified date: 
		 * Modified by:
		 * Description : As a PA user validate all meta data searches in advance searches
		 */	
		@Test(dataProvider="metaDataSearch",groups={"regression"},priority=0)
		public void metaDataSearchsAS(String TestCaseInfo, int Expected_count,String metaDataName,String IS_or_Range,String first_input,String second_input) {
			SoftAssert softAssertion= new SoftAssert();
			driver.getWebDriver().get(Input.url+ "Search/Searches");
			driver.waitForPageToBeReady();
	    	bc.selectproject();
	    	bc.passedStep("******** Search page is successfully opened********");
	    	bc.stepInfo(TestCaseInfo);
	    	softAssertion.assertEquals(Expected_count,search.advancedMetaDataSearch(metaDataName,IS_or_Range,first_input,second_input));
			softAssertion.assertAll();
		}
		
	/*
	 * Author : Suresh Bavihalli
	 * Created date: Feb 2019
	 * Modified date: 
	 * Modified by:
	 * Description : search docs in advanced search and do bulk tag. 
	 * Untag the same and validate the count under 'Tags and Folders' page  
//	 */
	   @Test(groups={"regression"},priority=3)
	   public void bulkUnTag() throws InterruptedException {
		
		   String tagName = "tagName"+Utility.dynamicNameAppender();
		   bc.stepInfo("Test Case Id : RPMXCON-48752 To verify that user can untag the documents from the selected Tag");
		   //Create Bulk Tag   and then untag
		   driver.getWebDriver().get(Input.url+"Search/Searches");
		   driver.waitForPageToBeReady();
		   bc.selectproject();
		   search.advancedContentSearch(Input.searchString1);
		   bc.passedStep("********Advance Search is successful********");
		   search.bulkTag(tagName);
		   bc.passedStep("********Bulk Tag is successful********");
		   search.bulkUnTag(tagName);
		   bc.passedStep("********Bulk UnTag is successful********");
	       
		   final TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
	       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	       		tf.getTag_ToggleDocCount().Visible()  ;}}),Input.wait60); 
	  	
	       //Validate in tags panel
	       tf.getTag_ToggleDocCount().waitAndClick(10);
	       Thread.sleep(5000);
	       tf.getTagandCount(tagName, 0).waitAndGet(30);
	       System.out.println(tf.getTagandCount(tagName, 0).getText());
	       
	       Assert.assertTrue(tf.getTagandCount(tagName, 0).Displayed());
	       bc.passedStep("********Tag Name is Displayed under Tags and Folder page********");
	       System.out.println(tagName+" could be seen under tags and folder page");
	       
	}
  /*
	 * Author : Suresh Bavihalli
	 * Created date: Feb 2019
	 * Modified date: 
	 * Modified by:
	 * Description : search docs in advanced search and do bulk folder. 
	 * unfolder the same and validate the count under 'Tags and Folders page'  
	 */	
	@Test(groups={"regression"},priority=4)
    public void bulkUnFolder() throws InterruptedException {
	
		String folderName = "folderName1"+Utility.dynamicNameAppender();
		bc.stepInfo("Test Case Id : RPMXCON-48753 To verify that user can unfolder the documents from the selected Folder.");
		driver.waitForPageToBeReady();
		 bc.selectproject();
		 search.advancedContentSearch(Input.searchString1);
		 bc.passedStep("********Advance Search is successful********");
		//Bulk folder and unfolder
		search.bulkFolder(folderName);
		 bc.passedStep("********Bulk Folder is successful********");
		search.bulkUnFolder(folderName);
		bc.passedStep("********Bulk UnFolder is successful********");
		
		//Validate in folders panel
		final TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
	    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    tf.getFoldersTab().Visible()  ;}}),Input.wait60); 
	  	  
	       
	       tf.getFoldersTab().waitAndClick(10);
	       tf.getFolder_ToggleDocCount().waitAndClick(10);
	       tf.getFolderandCount(folderName, 0).WaitUntilPresent();
	       System.out.println(tf.getFolderandCount(folderName, 0).getText());
	     
	       Assert.assertTrue(tf.getFolderandCount(folderName, 0).Displayed());
	       bc.passedStep("********Folder Name is Displayed under Tags and Folder page********");
	       System.out.println(folderName+" could be seen under tags and folder page");
	       
	}
 /*
	 * Author : Suresh Bavihalli
	 * Created date: Feb 2019
	 * Modified date: 
	 * Modified by:
	 * Description : Search for docs and do bulk folder. Search for the same folder 
	 * under work product search in advance search, validate the count.  
	 */	
    @Test(groups={"regression"},priority=5)
    public void WPFolderSearch() throws InterruptedException {
    	
    	 String folderName = "folderName1"+Utility.dynamicNameAppender();
    	 bc.stepInfo("Test Case Id : RPMXCON-57041 To verify an an PA user login, I will be able to select multiple Folder from Folder column under Work Product tab & set that as a search criteria for advanced search");
    	 driver.getWebDriver().get(Input.url+"Search/Searches");
    	 
    	 driver.waitForPageToBeReady();
		 bc.selectproject();
		 search.advancedContentSearch(Input.searchString1);
		 bc.passedStep("********Advance Search is successful********");
		 search.bulkFolder(folderName);
		 bc.passedStep("********Bulk Folder is successful********");
		 
		 bc.selectproject();
		 search.switchToWorkproduct();
		 search.selectFolderInASwp(folderName);
		 bc.passedStep("******** Folder name is selected for advance search********");
		 Assert.assertEquals(search.serarchWP(), Input.pureHitSeachString1);
		 bc.passedStep("********WPFolder Search is successful********");
		 
	  }
	/*
	 * Author : Suresh Bavihalli
	 * Created date: Feb 2019
	 * Modified date: 
	 * Modified by:
	 * Description : Search for docs and do bulk tag. Search for the same tag 
	 * under work product search in advance search, validate the count.  
	 */	
     @Test(groups={"regression"},priority=6)
     public void WPTagSearch() throws InterruptedException {
		
    	 String tagName = "tagName"+Utility.dynamicNameAppender();
    	 bc.stepInfo("Test Case Id : RPMXCON-57038 To verify an an PA user login, I will be able to select multiple Tags from Tags column under Work Product tab & set that as a search criteria for advanced search");
    	 driver.getWebDriver().get(Input.url+"Search/Searches");
    	 driver.waitForPageToBeReady();
    	 bc.selectproject();
		 search.advancedContentSearch(Input.searchString1);
		 bc.passedStep("********Advance Search is successful********");
		 search.bulkTag(tagName);
		 bc.passedStep("********Bulk Tag is successful********");
		
		 bc.selectproject();
		 search.switchToWorkproduct();
		 search.selectTagInASwp(tagName);
		 bc.passedStep("******** Tag name is selected for advance search********");
		 Assert.assertEquals(search.serarchWP(), Input.pureHitSeachString1);
		 bc.passedStep("********WPTag Search is successful********");
	}
	
	/*
	 * Author : Suresh Bavihalli
	 * Created date: Feb 2019
	 * Modified date: 
	 * Modified by:
	 * Description : As a PA user create security group and release some docs to it.
	 * Validate docs and counts of security group under advanced search  
	 */	
     @Test(groups={"regression"},priority=7)
     public void WPSecuirtyGroupSearch() throws InterruptedException {
    	
    	 String securitygroupname = "SG1"+Utility.dynamicNameAppender();
    	 bc.stepInfo("Test Case Id : RPMXCON-57046 To verify an an PA user login, I will be able to select multiple Security Group from Security Group column under Work Product tab & set that as a search criteria for advanced search");
    	 //Create security group	
    	 sgpage = new SecurityGroupsPage(driver);
		 sgpage.AddSecurityGroup(securitygroupname);
		 
		 
		 //Search and release doc to SG
		 driver.waitForPageToBeReady();
		 bc.selectproject();
		   
		 int count =search.basicContentSearch(Input.searchString1);
		 bc.passedStep("********Basic Content Search is successful********");
		 search.bulkRelease(securitygroupname);
		 bc.passedStep("********Bulk Release to a security group is successful********");
		
		 bc.selectproject();
		 
		 //Validate in advance sreach under work product search
		 search.switchToWorkproduct();
		 search.selectSecurityGinWPS(securitygroupname);
		 bc.passedStep("******** Security Group is selected for advance search********");
		 
		 Assert.assertEquals(search.serarchWP(),count);
		 bc.passedStep("********WPSecuirtyGroup Search is successful********");
	     }
     
 /*
	 * Author : Suresh Bavihalli
	 * Created date: Feb 2019
	 * Modified date: 
	 * Modified by:
	 * Description : Combinational search under work product search in advanced search.
	 * Tags AND folders and security group and saved search
	 */	
     @Test(groups={"regression"},priority=8)
     public void workProductSearches() throws InterruptedException {
    	 String tagName = "tagName"+Utility.dynamicNameAppender();
		 String folderName = "folderName1"+Utility.dynamicNameAppender();
		 String securitygroupname = "SG1"+Utility.dynamicNameAppender();
		 String saveSearchName = "A_SaveSearch"+Utility.dynamicNameAppender();
		 bc.stepInfo("Test Case Id : RPMXCON-57041 To verify an an PA user login, I will be able to select multiple Folder from Folder column under Work Product tab & set that as a search criteria for advanced search");
		 bc.stepInfo("Test Case Id : RPMXCON-57038 To verify an an PA user login, I will be able to select multiple Tags from Tags column under Work Product tab & set that as a search criteria for advanced search");
		 bc.stepInfo("Test Case Id : RPMXCON-57046 To verify an an PA user login, I will be able to select multiple Security Group from Security Group column under Work Product tab & set that as a search criteria for advanced search");
		 bc.stepInfo("Test Case Id : RPMXCON-57066 Verify that  Boolean operator - AND/OR/NOT are working together properly with Plain Text on Advanced Search screen");
		 driver.waitForPageToBeReady();
		 //create tag with searchString1
		 bc.selectproject();
		 search.advancedContentSearch(Input.searchString1);
		 bc.passedStep("********Advance Search is successful********");
		 search.bulkTag(tagName);
		 bc.passedStep("********Bulk Tag is successful********");
		
		 //create folder with searchString2
		 bc.selectproject();
		 search.advancedContentSearch(Input.searchString2);
		 search.bulkFolder(folderName);
		 bc.passedStep("********Bulk Folder is successful********");
		 
		 //Save the search for searchString2
		 bc.CloseSuccessMsgpopup();
		 search.saveSearch(saveSearchName);
		 bc.passedStep("********Search saved successfully********");
		 //Create security group with searchString1
    	 sgpage = new SecurityGroupsPage(driver);
		 sgpage.AddSecurityGroup(securitygroupname);
		 bc.passedStep("********security group is created successfully********");
		 
		 //Search and release doc to SG
		 bc.selectproject();
		 search.basicContentSearch(Input.searchString1);
		 search.bulkRelease(securitygroupname);
		 bc.passedStep("********Bulk Release to a security group is successful********");
		
		 //TagAndFolderAndSecurityGroupANDSavedSearch
		 bc.selectproject();
		 search.switchToWorkproduct();
		 search.selectTagInASwp(tagName);
		 search.selectOperator("AND");
		 search.selectFolderInASwp(folderName);
		 search.selectOperator("AND");
		 search.selectSecurityGinWPS(securitygroupname);
		 search.selectOperator("AND");
		 search.searchSavedSearch(saveSearchName);
		 softAssertion.assertEquals(50,search.serarchWP());
		 
		 
		 
		 //TagOrFolderOrSecurityGroupORsavedSearch
		 bc.selectproject();
		 search.switchToWorkproduct();
		 search.selectTagInASwp(tagName);
		 search.selectOperator("OR");
		 search.selectFolderInASwp(folderName);
		 search.selectOperator("OR");
		 search.selectSecurityGinWPS(securitygroupname);
		 search.selectOperator("OR");
		 search.searchSavedSearch(saveSearchName);
		 softAssertion.assertEquals(87,search.serarchWP());
		 
		 //TagNotFolder
		 bc.selectproject();
		 search.switchToWorkproduct();
		 search.selectTagInASwp(tagName);
		 search.selectOperator("NOT");
		 search.selectFolderInASwp(folderName);
		 softAssertion.assertEquals(35,search.serarchWP());
		 
		 //FolderNotTag
		 bc.selectproject();
		 search.switchToWorkproduct();
		 search.selectFolderInASwp(folderName);	
		 search.selectOperator("NOT");
		 search.selectTagInASwp(tagName);
		 softAssertion.assertEquals(37,search.serarchWP());
		 
		 //SG Not folder
		 bc.selectproject();
		 search.switchToWorkproduct();
		 search.selectSecurityGinWPS(securitygroupname);
		 search.selectOperator("NOT");
		 search.selectFolderInASwp(folderName);
		 softAssertion.assertEquals(35,search.serarchWP());
		
		 //folder not SG
		 bc.selectproject();
		 search.switchToWorkproduct();
		 search.selectFolderInASwp(folderName);
		 search.selectOperator("NOT");
		 search.selectSecurityGinWPS(securitygroupname);
		 softAssertion.assertEquals(37,search.serarchWP());
		 
		 softAssertion.assertAll();
		 bc.passedStep("********WP searches is successful********");
	}
	
     //To validate combination of audio and content 
     @Test(groups={"regression"},priority=9)
     public void ContentSearchORAudioSearch() throws InterruptedException {
    	 
    	//create tag for work product first
    	 String tagName = "tagName"+Utility.dynamicNameAppender();
    	 bc.stepInfo("Test Case Id : RPMXCON-46877 Verify that Audio search functionality is working proper in Advanced Search");
    	 bc.stepInfo("Test Case Id : RPMXCON-57070 Verify that  Boolean operator - OR is working properly with Plain Text and Metadata on Advanced Search screen");
    	 driver.getWebDriver().get(Input.url+"Search/Searches");
    	 driver.waitForPageToBeReady();
    	 bc.selectproject();
		 search.advancedContentSearch(Input.searchString1);
		 bc.passedStep("********Advance Search is successful********");
		 search.bulkTag(tagName);
		 bc.passedStep("********Bulk Tag is successful********");
		//Content search--------------------------------------------------------
		 bc.selectproject();
    	 driver.getWebDriver().get(Input.url+ "Search/Searches");
     	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			search.getAdvancedSearchLink().Visible()  ;}}), Input.wait30); 
     	search.getAdvancedSearchLink().Click();
     	
     	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			search.getContentAndMetaDatabtn().Visible()  ;}}), Input.wait30); 
     	search.getContentAndMetaDatabtn().Click();
         
    	
     	 //Enter search string
     	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			search.getAdvancedContentSearchInput().Visible()  ;}}), Input.wait30); 
     	search.getAdvancedContentSearchInput().SendKeys(Input.searchString1) ;
     	

    	     	
    	//Audio search -------------------------------------------------------------
    	
     	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			search.getAs_Audio().Visible()  ;}}), Input.wait30); 
    	search.getAs_Audio().waitAndClick(10);
    	
    	//condition --------------------------------------------------------------
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			search.getAs_SelectOperation(1).Visible()  ;}}), Input.wait30); 
     	search.getAs_SelectOperation(1).selectFromDropdown().selectByVisibleText("OR");
     	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			search.getAs_AudioLanguage().Visible()  ;}}), Input.wait30); 
    	search.getAs_AudioLanguage().selectFromDropdown().selectByVisibleText("North American English");
    	 //Enter search string
    	driver.scrollingToBottomofAPage();
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			search.getAs_AudioText_Combination().Visible()  ;}}), Input.wait30); 
    	search.getAs_AudioText_Combination().SendKeys(Input.audioSearchString1) ;
    	
    	  //Click on Search button
    	driver.scrollPageToTop();
    	search.getQuerySearchButton().Click();
    	
    	//verify counts for all the tiles
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			search.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait90);
    	
    	Assert.assertTrue(Integer.parseInt(search.getPureHitsCount().getText())>50);
    	bc.passedStep("********Audio search is successful********");
	}
     
     @Test(groups={"regression"},priority=10)
     public void contentANDwpTag() throws InterruptedException {
    	 
     	//create tag for work product first
     	 String tagName = "tagName"+Utility.dynamicNameAppender();
     	 bc.stepInfo("Test Case Id : RPMXCON-57038 To verify an an PA user login, I will be able to select multiple Tags from Tags column under Work Product tab & set that as a search criteria for advanced search");
     	 bc.stepInfo("Test Case Id : RPMXCON-57069 Verify that  Boolean operator - AND is working properly with Plain Text and Metadata on Advanced Search screen");
     	 driver.getWebDriver().get(Input.url+"Search/Searches");
     	driver.waitForPageToBeReady();
     	 bc.selectproject();
 		 search.advancedContentSearch(Input.searchString2);
 		bc.passedStep("********Advance Search is successful********");
 		 search.bulkTag(tagName);
 		bc.passedStep("********Bulk Tag is successful********");
 		//Content search--------------------------------------------------------
 		 bc.selectproject();
     	 driver.getWebDriver().get(Input.url+ "Search/Searches");
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			search.getAdvancedSearchLink().Visible()  ;}}), Input.wait30); 
      	search.getAdvancedSearchLink().Click();
      	
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			search.getContentAndMetaDatabtn().Visible()  ;}}), Input.wait30); 
      	search.getContentAndMetaDatabtn().Click();
          
     	
      	 //Enter search string
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			search.getAdvancedContentSearchInput().Visible()  ;}}), Input.wait30); 
      	search.getAdvancedContentSearchInput().SendKeys(Input.searchString1) ;

     
     	//Work product tag search
     	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     				search.getWorkproductBtn().Visible()  ;}}), Input.wait30); 
     	 search.getWorkproductBtn().Click();
     	//condition --------------------------------------------------------------
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			search.getAs_SelectOperation(2).Visible()  ;}}), Input.wait30); 
       	search.getAs_SelectOperation(2).selectFromDropdown().selectByVisibleText("AND");
       	driver.scrollingToBottomofAPage();
 		 search.selectTagInASwp(tagName);
 		 
 		Assert.assertEquals(search.serarchWP(),15);
 		bc.passedStep("********Advance content and workProduct Tag is successful********");
 		
     	

	}
     //Complex query save and execute many time to check consistency!
     @Test(groups={"regression"},priority=11)
     public void contentANDwpTagSaveAndExecuteMultipleTimes() throws InterruptedException {
    	 
     	//create tag for work product first
     	 String tagName = "tagName"+Utility.dynamicNameAppender();
     	 bc.stepInfo("Test Case Id : RPMXCON-57200 Verify that - Application returns consistent search result when user resubmits a saved search(Conceptual Block,Content & Metadata Block,Audio Block and WorkProduct Block - Tag - Random Order ) multiple times(twice)");	 
     	 driver.getWebDriver().get(Input.url+"Search/Searches");
     	driver.waitForPageToBeReady();
     	 bc.selectproject();
 		 search.advancedContentSearch(Input.searchString2);
 		bc.passedStep("********Advance Search is successful********");
 		 search.bulkTag(tagName);
 		bc.passedStep("********Bulk Tag is successful********");
 		//Content search--------------------------------------------------------
 		 bc.selectproject();
     	 driver.getWebDriver().get(Input.url+ "Search/Searches");
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			search.getAdvancedSearchLink().Visible()  ;}}), Input.wait30); 
      	search.getAdvancedSearchLink().Click();
      	
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			search.getContentAndMetaDatabtn().Visible()  ;}}), Input.wait30); 
      	search.getContentAndMetaDatabtn().Click();
          
     	
      	 //Enter search string
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			search.getAdvancedContentSearchInput().Visible()  ;}}), Input.wait30); 
      	search.getAdvancedContentSearchInput().SendKeys(Input.searchString1) ;

     
     	//Work product tag search
     	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     				search.getWorkproductBtn().Visible()  ;}}), Input.wait30); 
     	 search.getWorkproductBtn().Click();
     	//condition --------------------------------------------------------------
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			search.getAs_SelectOperation(2).Visible()  ;}}), Input.wait30); 
       	search.getAs_SelectOperation(2).selectFromDropdown().selectByVisibleText("AND");
       	driver.scrollingToBottomofAPage();
 		 search.selectTagInASwp(tagName);
 		 
 		softAssertion.assertEquals(15,search.serarchWP());
 		bc.passedStep("********Advance content and workProduct Tag is successful********");
 		
 		String searchName="00Atest"+Utility.dynamicNameAppender();
 		search.saveSearch(searchName);
 		
 		SavedSearch savedSearch = new SavedSearch(driver);
 		bc.passedStep("********Search is saved********");
 		for(int i=10;i<=10;i++) {
 				savedSearch.savedSearchExecute(searchName, 15);
 				Thread.sleep(2000);
 		} 		
 		bc.passedStep("********Content & WP Tag search is executed 10 times********");
	}
     @Test(groups={"regression"},priority=12)
     public void contentNOTwpTag() throws InterruptedException {
    	 
     	//create tag for work product first
     	 String tagName = "tagName"+Utility.dynamicNameAppender();
     	 bc.stepInfo("Test Case Id : RPMXCON-38084  When User configured query (Combination of Content & Metadata NOT WorkProduct(TAG)) and navigate to Saved Search and come back to Session search then Search Criteria reset to default.");
     	 driver.getWebDriver().get(Input.url+"Search/Searches");
     	driver.waitForPageToBeReady();
     	 bc.selectproject();
 		 search.advancedContentSearch(Input.searchString2);
 		bc.passedStep("********Advance Search is successful********");
 		 search.bulkTag(tagName);
 		bc.passedStep("********Bulk Tag is successful********");
 		//Content search--------------------------------------------------------
 		 bc.selectproject();
     	 driver.getWebDriver().get(Input.url+ "Search/Searches");
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			search.getAdvancedSearchLink().Visible()  ;}}), Input.wait30); 
      	search.getAdvancedSearchLink().Click();
      	
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			search.getContentAndMetaDatabtn().Visible()  ;}}), Input.wait30); 
      	search.getContentAndMetaDatabtn().Click();
          
     	
      	 //Enter search string
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			search.getAdvancedContentSearchInput().Visible()  ;}}), Input.wait30); 
      	search.getAdvancedContentSearchInput().SendKeys(Input.searchString1) ;

     
     	//Work product tag search
     	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     				search.getWorkproductBtn().Visible()  ;}}), Input.wait30); 
     	 search.getWorkproductBtn().Click();
     	//condition --------------------------------------------------------------
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
      			search.getAs_SelectOperation(1).Visible()  ;}}), Input.wait30); 
       	search.getAs_SelectOperation(1).selectFromDropdown().selectByVisibleText("NOT");
       	//driver.scrollingToBottomofAPage();
 		search.selectTagInASwp(tagName);
 		 
 		Assert.assertEquals(search.serarchWP(),35);
 		bc.passedStep("********Advance content and workProduct Tag search is successful********");
 		Thread.sleep(3000);
 		//below code for covering RPMXCON-38084
     	SavedSearch savedSeach = new SavedSearch(driver);
     	driver.getWebDriver().get(Input.url+"Search/Searches");
      	Thread.sleep(3000);
     	search.getModifyASearch().waitAndClick(10);
     	
     	driver.scrollPageToTop();
     	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 search.getQuerySearchButton().Visible()  ;}}), Input.wait30); 
     	search.getQuerySearchButton().waitAndClick(5);
     	//verify counts for all the tiles
   		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   				search.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait90);
   		
   		Assert.assertEquals(search.getPureHitsCount2ndSearch().getText(),"35");
   		bc.passedStep("********Saved Search is executed for the second time gives same document count upon search********");

	}

     	@Test(groups={"regression"},priority=13)
	   	public void AdvSearchgetallresults() throws InterruptedException {
     		
     		driver.waitForPageToBeReady();
	   		bc.selectproject();
	   		bc.stepInfo("Test Case Id : RPMXCON-49272 Verify that relevant information message appears on Search Result screen When the user Selects to include 90% or higher textual near dupe documents and Family Member Documents Advanced Search options");
	   		driver.getWebDriver().get(Input.url+ "Search/Searches");
			softAssertion.assertTrue(search.advancedContentSearch("CustodianName: (  P Allen)"+Keys.ENTER+"OR"+Keys.ENTER+Input.searchString1)>=1165);
		  	Thread.sleep(5000);
			
			String nearcount = search.verifyNearDupeCount();
			softAssertion.assertTrue(Integer.parseInt(nearcount)>=1);
			String threadcount =search.verifyThreadedCount();
			softAssertion.assertTrue(Integer.parseInt(threadcount)>=1);
			String familycount =search.verifyFamilyount();
			softAssertion.assertTrue(Integer.parseInt(familycount)>=1);
			System.out.println(nearcount+ "  -----  "+threadcount+" -----"+familycount);	
			
			//conceptual count
			search.getConceptuallyPlayButton().waitAndClick(10);
			Thread.sleep(5000);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
					search.getConceptualCount().Present()  ;}}), Input.wait60); 
			search.getConceptualCount().VisibilityOfElementExplicitWait(search.getConceptualCount(), 10000);
			String  conceptcount = search.getConceptualCount().getText();
			System.out.println(conceptcount);
			softAssertion.assertTrue(Integer.parseInt(conceptcount)>=1);
			softAssertion.assertAll();
			bc.passedStep("********Advance search for all is successful********");
	    }
	   	
     	 @Test(groups={"regression"},priority=14)
	     public void contentwithAdvSearchoptions() throws InterruptedException {
     		bc.stepInfo("Test Case Id : RPMXCON-49271 Verify that relevant  information message appears on Search Result screen When the user Selects to include All threaded email documents and Family Member Documents Advanced Search options.");
	    	 bc.selectproject();
		   	 search.advancedContentSearch(Input.searchString2);
		   	bc.passedStep("********Advance Search is successful********");
		   	 bc.selectproject();
	 		 search.advContentSearchwithoptions(Input.searchString2);
	 		 bc.passedStep("********Content with Advance Search options is successful********");
	 		
	    }
     	 
     	 @Test(groups={"regression"},priority=16)
    	public void Audiosearchwithmultipleterms() throws ParseException, InterruptedException {
    		
    		 bc.stepInfo("Test case Id: RPMXCON-56906 - Audio Proximity searches in Sightline should allow the user to specify more than two search terms");
    		 bc.stepInfo("*****Verify Audio search with more than two terms*****");
    		 SessionSearch search = new SessionSearch(driver);
    		 SoftAssert softAssertion= new SoftAssert();
    		 softAssertion.assertTrue(search.audioSearch("morning","nation" ,"tonight" ,"this" ,"North American English") >=1);
    		bc.passedStep("*****Audio search successfull*****");
     	 }
     	 
     	 @Test(groups={"regression"},priority=17)
     	public void advancedsearchCustomField() throws InterruptedException {
     		try {
    			
     			lp.logout();
    		}
    		catch(Exception e) {}
     		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
     		bc.passedStep("********logged in succesfully as PA user********");
     		bc.selectproject();
     		 String fieldname = CustomFieldname;
     		 SoftAssert softAssertion= new SoftAssert();
     		 bc.stepInfo("Test Case Id :RPMXCON-49177 : Verify that Advanced Search works properly for CustomField date/time field with 'Is' operator");
     		 bc.stepInfo("********Verify that CustomField date/time field is created and perform Advanced search********");
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
     		bc.stepInfo("*******Advanced Search for CustomField date/time field with 'Is' operator*********");
     		softAssertion.assertTrue(ss.advancedMetaDataSearch(fieldname,"IS","2000-01-04 19:39:00","")>=1);
     		bc.passedStep("*******Advanced Search for CustomField date/time field with 'Is' operator is created*********");
     		
     				 }
     				 
     		else {
     			    SessionSearch ss = new SessionSearch(driver);
     				driver.getWebDriver().get(Input.url+ "Search/Searches"); 
     				
     				bc.passedStep("******** Search page is successfully opened********");
     				bc.stepInfo("*******Advanced Search for CustomField date/time field with 'Is' operator*********");
     				softAssertion.assertTrue(ss.advancedMetaDataSearch(fieldname,"IS","2000-01-04 19:39:00","")>=1);
     				bc.passedStep("*******Advanced Search for CustomField date/time field with 'Is' operator is created*********");
     	  
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
 	}
 	 System.out.println("Executed :" + result.getMethod().getMethodName());
// 	lp.logout();
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
    //step info added
     @DataProvider(name = "metaDataSearch")
  	public Object[][] metaData() {
  		return new Object[][] { 
  			{"TestCase Id: RPMXCON-57061 Verify that Advanced Search is working properly for MasterDate Metadata with IS Operator",95,"MasterDate", "IS", "1980-01-01", null},
 			{"Test Case Id : RPMXCON-57236 Verify that Advanced Search works properly - for MasterDate time metadata - Provide only dates (not times) with Is operator",11,"MasterDate", "IS", "1989-02-10 16:59:39", null},
 			{"TestCase Id: RPMXCON-57061 Verify that Advanced Search is working properly for MasterDate Metadata with IS Operator",124,"MasterDate", "RANGE", "1980-01-01", "2000-01-01"},
 			{"TestCase Id: RPMXCON-57240 Verify that Advanced Search works properly - for EmailSentDate time metadata - Provide only dates (not times) with Is operator",0,"EmailSentDate", "IS", "1990-05-05", null},
 			{"TestCase Id: RPMXCON-57241 Verify that Advanced Search works properly - for EmailSentDate time metadata - Provide only dates (not times) with Range operator",0,"EmailSentDate", "RANGE", "1990-05-05", "2000-05-05"},
 			{"Test Case Id : RPMXCON-57228 Verify that Advanced Search works properly for AppointmentStartDate date/time field with Is operator",0,"AppointmentStartDate", "IS", "1990-05-05", null},
 			{"Test Case Id : RPMXCON-57229 Verify that Advanced Search works properly for AppointmentStartDate date/time field with Range operator",0,"AppointmentStartDate", "RANGE", "1990-05-05", "2000-05-05"},
// 			{"Test Case Id : RPMXCON-55469 Verify that application displays correct options for AppointmentEndDateOnly field on Advanced Search screen","AppointmentEndDateOnly", "IS", "1990-05-05", null},
// 			{"Test Case Id : RPMXCON-55469 Verify that application displays correct options for AppointmentEndDateOnly field on Advanced Search screen","AppointmentEndDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
 			{"Test Case Id : RPMXCON-57148 Verify that application displays correct options for DocDateDateOnly field on Advanced Search screen",0,"DocDateDateOnly", "IS", "1990-05-05", null},
 			{"Test Case Id : RPMXCON-57148 Verify that application displays correct options for DocDateDateOnly field on Advanced Search screen",0,"DocDateDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
// 			{"Test Case Id : RPMXCON-57126 Verify that application displays correct options for DateAccessedDateOnly field on Basic Search screen","DateAccessedDateOnly", "IS", "1990-05-05", null},
// 			{"Test Case Id : RPMXCON-57126 Verify that application displays correct options for DateAccessedDateOnly field on Basic Search screen","DateAccessedDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
// 			{"Test Case Id : RPMXCON-57149 Verify that application displays correct options for DateCreatedDateOnly field on Advanced Search screen",0,"DateCreatedDateOnly", "IS", "1990-05-05", null},
// 			{"Test Case Id : RPMXCON-57149 Verify that application displays correct options for DateCreatedDateOnly field on Advanced Search screen",26,"DateCreatedDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
//			{"Test Case Id : RPMXCON-57128 Verify that application displays correct options for DateEditedDateOnly field on Basic Search screen","DateEditedDateOnly", "IS", "1990-05-05", null},
// 			{"Test Case Id : RPMXCON-57128 Verify that application displays correct options for DateEditedDateOnly field on Basic Search screen","DateEditedDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
// 			{"Test Case Id : RPMXCON-57129 Verify that application displays correct options for DateModifiedDateOnly field on Basic Search screen","DateModifiedDateOnly", "IS", "1990-05-05", null},
// 			{"Test Case Id : RPMXCON-57129 Verify that application displays correct options for DateModifiedDateOnly field on Basic Search screen","DateModifiedDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
// 			{"Test Case Id : RPMXCON-57130 Verify that application displays correct options for DatePrintedDateOnly field on Basic Search screen","DatePrintedDateOnly", "IS", "1990-05-05", null},
// 			{"Test Case Id : RPMXCON-57130 Verify that application displays correct options for DatePrintedDateOnly field on Basic Search screen","DatePrintedDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
// 			{"Test Case Id : RPMXCON-57131 Verify that application displays correct options for DateReceivedDateOnly field on Basic Search screen","DateReceivedDateOnly", "IS", "1990-05-05", null},
// 			{"Test Case Id : RPMXCON-57131 Verify that application displays correct options for DateReceivedDateOnly field on Basic Search screen","DateReceivedDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
// 			{"Test Case Id : RPMXCON-57132 Verify that application displays correct options for DateSavedDateOnly field on Basic Search screen","DateSavedDateOnly", "IS", "1990-05-05", null},
// 			{"Test Case Id : RPMXCON-57132 Verify that application displays correct options for DateSavedDateOnly field on Basic Search screen","DateSavedDateOnly", "RANGE", "1990-05-05", "2000-05-05"}, 
 			{"Test Case Id : RPMXCON-57150 Verify that application displays correct options for MasterDateDateOnly field on Advanced Search screen",0,"MasterDateDateOnly", "IS", "1990-05-05", null},
 			{"Test Case Id : RPMXCON-57150 Verify that application displays correct options for MasterDateDateOnly field on Advanced Search screen",208,"MasterDateDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
 			{"Test Case Id : RPMXCON-57151 Verify that application displays correct options for EmailDateSentDateOnly field on Advanced Search screen",0,"EmailDateSentDateOnly", "IS", "1990-05-05", null},
 			{"Test Case Id : RPMXCON-57151 Verify that application displays correct options for EmailDateSentDateOnly field on Advanced Search screen",0,"EmailDateSentDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
// 			{"Test Case Id : RPMXCON-57152 Verify that application displays correct options for AppointmentStartDateOnly field on Advanced Search screen","AppointmentStartDateOnly", "IS", "1990-05-05", null},
// 			{"Test Case Id : RPMXCON-57152 Verify that application displays correct options for AppointmentStartDateOnly field on Advanced Search screen","AppointmentStartDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
 			{"Test Case Id : RPMXCON-49684 Verify that warning and pure hit result appears for EmailAuthorName Metadata search having phrase included in the query without wrapping in quotes on Advanced Search Screen",13,"EmailAuthorName", null, "(Gouri Dhavalikar)", null},
 			{"Test Case Id : RPMXCON-49679 Verify that warning and pure hit result appears for EmailAuthorAddress Metadata search having phrase included in the query without wrapping in quotes on Advanced Search Screen",0,"EmailAuthorAddress", null, "Gouri.Dhavalikar@symphonyteleca.com", null},
 			{null,0,"EmailAllDomains", null, "consilio.com;harman;harman.com", null},
 			{"Test Case Id : RPMXCON-49690 Verify that warning and pure hit result appears for EmailRecipientNames Metadata search having phrase included in the query without wrapping in quotes on Advanced Search Screen",29,"EmailRecipientNames", null, "Satish Pawal;Shunmugasundaram Senthivelu;Swapnal Sonawane", null},
 			{"Test Case Id : RPMXCON-49689 Verify that warning and pure hit result appears for EmailRecipientAddresses Metadata search having phrase included in the query without wrapping in quotes on Advanced Search Screen",0,"EmailRecipientAddresses", null, "Robert.Superty@consilio.com", null},
 			{null,0,"EmailRecipientDomains", null, "consilio.com", null},
// 			{"Test Case Id :, RPMXCON-57063 Verify that Advanced Search is working properly for DocumentFileSize Metadata with IS Operator",95,"DocFileSize", null, "9728", null}, 
// 			{"Test Case Id : RPMXCON-57060 Verify that Advanced Search is working properly for DocumentFileSize Metadata with Range Operator",138,"DocFileSize","RANGE", "60","9728"},
 			{"Test Case Id : RPMXCON-48710 Verify that Advanced Search is working properly for DocumentFileExtension Metadata",841,"DocFileExtension", null,".msg", null},
  		};
      }
}
