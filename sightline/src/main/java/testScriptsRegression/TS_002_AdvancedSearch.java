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
import pageFactory.LoginPage;
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
		//Open browser
		driver = new Driver();
		bc = new BaseClass(driver);
		searchText =Input.searchString1;
		//Login as PA
		lp=new LoginPage(driver);
		search = new SessionSearch(driver);
    	lp.loginToSightLine(Input.pa1userName, Input.pa1password);
    
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
			softAssertion.assertTrue(search.advancedContentSearch("CustodianName: (  P Allen)"+Keys.ENTER+"OR"+Keys.ENTER+Input.searchString1)>=1166);

			bc.selectproject();
			softAssertion.assertTrue(search.advancedContentSearch("CustodianName: (  P Allen)"+Keys.ENTER+"AND"+Keys.ENTER+Input.searchString1)>=19);
			
			bc.selectproject();
			softAssertion.assertTrue(search.advancedContentSearch("CustodianName: (  P Allen)"+Keys.ENTER+"NOT"+Keys.ENTER+Input.searchString1)>=1116);

			bc.selectproject();
			softAssertion.assertTrue(search.advancedContentSearch(Input.searchString1+Keys.ENTER+"NOT"+Keys.ENTER+"CustodianName: (  P Allen)")>0);

			//Proximity seach
			bc.selectproject();
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
		public void metaDataSearchsAS(int Expected_count,String metaDataName,String IS_or_Range,String first_input,String second_input) {
			SoftAssert softAssertion= new SoftAssert();
			driver.getWebDriver().get(Input.url+ "Search/Searches");
			driver.waitForPageToBeReady();
	    	bc.selectproject();
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
			
		   //Create Bulk Tag   and then untag
		   driver.getWebDriver().get(Input.url+"Search/Searches");
		   driver.waitForPageToBeReady();
		   bc.selectproject();
		   search.advancedContentSearch(Input.searchString1);
		   search.bulkTag(tagName);
		   search.bulkUnTag(tagName);
	       
		   final TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
	       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	       		tf.getTag_ToggleDocCount().Visible()  ;}}),Input.wait60); 
	  	
	       //Validate in tags panel
	       tf.getTag_ToggleDocCount().waitAndClick(10);
	       Thread.sleep(5000);
	       tf.getTagandCount(tagName, 0).waitAndGet(30);
	       System.out.println(tf.getTagandCount(tagName, 0).getText());
	       
	       Assert.assertTrue(tf.getTagandCount(tagName, 0).Displayed());
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
		driver.waitForPageToBeReady();
		 bc.selectproject();
		 search.advancedContentSearch(Input.searchString1);
		//Bulk folder and unfolder
		search.bulkFolder(folderName);
		search.bulkUnFolder(folderName);
		
		//Validate in folders panel
		final TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
	    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    tf.getFoldersTab().Visible()  ;}}),Input.wait60); 
	  	  
	       
	       tf.getFoldersTab().waitAndClick(10);
	       tf.getFolder_ToggleDocCount().waitAndClick(10);
	       tf.getFolderandCount(folderName, 0).WaitUntilPresent();
	       System.out.println(tf.getFolderandCount(folderName, 0).getText());
	     
	       Assert.assertTrue(tf.getFolderandCount(folderName, 0).Displayed());
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
    	 driver.getWebDriver().get(Input.url+"Search/Searches");
    	 
    	 driver.waitForPageToBeReady();
		 bc.selectproject();
		 search.advancedContentSearch(Input.searchString1);
		 search.bulkFolder(folderName);
		 
		 bc.selectproject();
		 search.switchToWorkproduct();
		 search.selectFolderInASwp(folderName);
		 Assert.assertEquals(search.serarchWP(), Input.pureHitSeachString1);
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
    	 driver.getWebDriver().get(Input.url+"Search/Searches");
    	 driver.waitForPageToBeReady();
    	 bc.selectproject();
		 search.advancedContentSearch(Input.searchString1);
		 search.bulkTag(tagName);
		
		 bc.selectproject();
		 search.switchToWorkproduct();
		 search.selectTagInASwp(tagName);
		 Assert.assertEquals(search.serarchWP(), Input.pureHitSeachString1);
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
    	 
    	 //Create security group	
    	 sgpage = new SecurityGroupsPage(driver);
		 sgpage.AddSecurityGroup(securitygroupname);
		 
		 
		 //Search and release doc to SG
		 driver.waitForPageToBeReady();
		 bc.selectproject();
		   
		 int count =search.basicContentSearch(Input.searchString1);
		 search.bulkRelease(securitygroupname);
		
		 bc.selectproject();
		 
		 //Validate in advance sreach under work product search
		 search.switchToWorkproduct();
		 search.selectSecurityGinWPS(securitygroupname);
		 
		 Assert.assertEquals(search.serarchWP(),count);
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
		 
		 driver.waitForPageToBeReady();
		 //create tag with searchString1
		 bc.selectproject();
		 search.advancedContentSearch(Input.searchString1);
		 search.bulkTag(tagName);
		
		 //create folder with searchString2
		 bc.selectproject();
		 search.advancedContentSearch(Input.searchString2);
		 search.bulkFolder(folderName);
		 
		 //Save the search for searchString2
		 bc.CloseSuccessMsgpopup();
		 search.saveSearch(saveSearchName);
		 
		 //Create security group with searchString1
    	 sgpage = new SecurityGroupsPage(driver);
		 sgpage.AddSecurityGroup(securitygroupname);
		 
		 //Search and release doc to SG
		 bc.selectproject();
		 search.basicContentSearch(Input.searchString1);
		 search.bulkRelease(securitygroupname);
		
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
		 softAssertion.assertEquals(53,search.serarchWP());
		 
		 
		 
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
		 softAssertion.assertEquals(15,search.serarchWP());
		 
		 //TagNotFolder
		 bc.selectproject();
		 search.switchToWorkproduct();
		 search.selectTagInASwp(tagName);
		 search.selectOperator("NOT");
		 search.selectFolderInASwp(folderName);
		 softAssertion.assertEquals(47,search.serarchWP());
		 
		 //FolderNotTag
		 bc.selectproject();
		 search.switchToWorkproduct();
		 search.selectFolderInASwp(folderName);	
		 search.selectOperator("NOT");
		 search.selectTagInASwp(tagName);
		 softAssertion.assertEquals(3,search.serarchWP());
		 
		 //SG Not folder
		 bc.selectproject();
		 search.switchToWorkproduct();
		 search.selectSecurityGinWPS(securitygroupname);
		 search.selectOperator("NOT");
		 search.selectFolderInASwp(folderName);
		 softAssertion.assertEquals(47,search.serarchWP());
		
		 //folder not SG
		 bc.selectproject();
		 search.switchToWorkproduct();
		 search.selectFolderInASwp(folderName);
		 search.selectOperator("NOT");
		 search.selectSecurityGinWPS(securitygroupname);
		 softAssertion.assertEquals(3,search.serarchWP());
		 
		 softAssertion.assertAll();
	}
	
     //To validate combination of audio and content 
     @Test(groups={"regression"},priority=9)
     public void ContentSearchORAudioSearch() throws InterruptedException {
    	 
    	//create tag for work product first
    	 String tagName = "tagName"+Utility.dynamicNameAppender();
    	 driver.getWebDriver().get(Input.url+"Search/Searches");
    	 driver.waitForPageToBeReady();
    	 bc.selectproject();
		 search.advancedContentSearch(Input.searchString1);
		 search.bulkTag(tagName);
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
    	
	}
     
     @Test(groups={"regression"},priority=10)
     public void contentANDwpTag() throws InterruptedException {
    	 
     	//create tag for work product first
     	 String tagName = "tagName"+Utility.dynamicNameAppender();
     	 driver.getWebDriver().get(Input.url+"Search/Searches");
     	driver.waitForPageToBeReady();
     	 bc.selectproject();
 		 search.advancedContentSearch(Input.searchString2);
 		 search.bulkTag(tagName);
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
 		
     	

	}
     //Complex query save and execute many time to check consistency!
     @Test(groups={"regression"},priority=11)
     public void contentANDwpTagSaveAndExecuteMultipleTimes() throws InterruptedException {
    	 
     	//create tag for work product first
     	 String tagName = "tagName"+Utility.dynamicNameAppender();
     	 driver.getWebDriver().get(Input.url+"Search/Searches");
     	driver.waitForPageToBeReady();
     	 bc.selectproject();
 		 search.advancedContentSearch(Input.searchString2);
 		 search.bulkTag(tagName);
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
 		 
 		Assert.assertEquals(16,search.serarchWP());
 		
 		String searchName="00Atest"+Utility.dynamicNameAppender();
 		search.saveSearch(searchName);
 		
 		SavedSearch savedSearch = new SavedSearch(driver);
 		savedSearch.savedSearchExecute(searchName, 15);
 		Thread.sleep(2000);
 		savedSearch.savedSearchExecute(searchName, 15);
 		Thread.sleep(2000);
 		savedSearch.savedSearchExecute(searchName, 15);
 		Thread.sleep(2000);
 		savedSearch.savedSearchExecute(searchName, 15);
 		Thread.sleep(2000);
 		savedSearch.savedSearchExecute(searchName, 15);
 		Thread.sleep(2000);
 		savedSearch.savedSearchExecute(searchName, 15);
 		Thread.sleep(2000);
 		savedSearch.savedSearchExecute(searchName, 15);
 		Thread.sleep(2000);
 		savedSearch.savedSearchExecute(searchName, 16);
 		Thread.sleep(2000);
 		savedSearch.savedSearchExecute(searchName, 16);
 		Thread.sleep(2000);
 		savedSearch.savedSearchExecute(searchName, 16);
 		
	}
     @Test(groups={"regression"},priority=12)
     public void contentNOTwpTag() throws InterruptedException {
    	 
     	//create tag for work product first
     	 String tagName = "tagName"+Utility.dynamicNameAppender();
     	 driver.getWebDriver().get(Input.url+"Search/Searches");
     	driver.waitForPageToBeReady();
     	 bc.selectproject();
 		 search.advancedContentSearch(Input.searchString2);
 		 search.bulkTag(tagName);
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
 		 
 		Assert.assertEquals(search.serarchWP(),33);
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
   		
     	Assert.assertEquals(search.getPureHitsCount2ndSearch().getText(),"33");

	}

     	@Test(groups={"regression"},priority=13)
	   	public void AdvSearchgetallresults() throws InterruptedException {
	   		
     		driver.waitForPageToBeReady();
	   		bc.selectproject();
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
			String  conceptcount = search.getConceptualCount().getText();
			System.out.println(conceptcount);
			softAssertion.assertTrue(Integer.parseInt(conceptcount)>=1);
			softAssertion.assertAll();
	    }
	   	
     	 @Test(groups={"regression"},priority=14)
	     public void contentwithAdvSearchoptions() throws InterruptedException {
	    	 
	    	 bc.selectproject();
		   	 search.advancedContentSearch(Input.searchString2);
		   	 bc.selectproject();
	 		 search.advContentSearchwithoptions(Input.searchString2);
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
     @DataProvider(name = "metaDataSearch2711")
  	public Object[][] metaData() {
  		return new Object[][] { 
  			{95,"MasterDate", "IS", "1980-01-01", null},
 			{11,"MasterDate", "IS", "1989-02-10 16:59:39", null},
 			{124,"MasterDate", "RANGE", "1980-01-01", "2000-01-01"},
 			{0,"EmailSentDate", "IS", "1990-05-05", null},
 			{0,"EmailSentDate", "RANGE", "1990-05-05", "2000-05-05"},
 			{0,"AppointmentStartDate", "IS", "1990-05-05", null},
 			{0,"AppointmentStartDate", "RANGE", "1990-05-05", "2000-05-05"},
// 			{"AppointmentEndDateOnly", "IS", "1990-05-05", null},
// 			{"AppointmentEndDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
 			{0,"DocDateDateOnly", "IS", "1990-05-05", null},
 			{0,"DocDateDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
// 			{"DateAccessedDateOnly", "IS", "1990-05-05", null},
// 			{"DateAccessedDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
// 			{0,"DateCreatedDateOnly", "IS", "1990-05-05", null},
// 			{26,"DateCreatedDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
//// 			{"DateEditedDateOnly", "IS", "1990-05-05", null},
// 			{"DateEditedDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
// 			{"DateModifiedDateOnly", "IS", "1990-05-05", null},
// 			{"DateModifiedDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
// 			{"DatePrintedDateOnly", "IS", "1990-05-05", null},
// 			{"DatePrintedDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
// 			{"DateReceivedDateOnly", "IS", "1990-05-05", null},
// 			{"DateReceivedDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
// 			{"DateSavedDateOnly", "IS", "1990-05-05", null},
// 			{"DateSavedDateOnly", "RANGE", "1990-05-05", "2000-05-05"}, 
 			{0,"MasterDateDateOnly", "IS", "1990-05-05", null},
 			{208,"MasterDateDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
 			{0,"EmailDateSentDateOnly", "IS", "1990-05-05", null},
 			{0,"EmailDateSentDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
// 			{"AppointmentStartDateOnly", "IS", "1990-05-05", null},
// 			{"AppointmentStartDateOnly", "RANGE", "1990-05-05", "2000-05-05"},
 			{13,"EmailAuthorName", null, "(Gouri Dhavalikar)", null},
 			{0,"EmailAuthorAddress", null, "Gouri.Dhavalikar@symphonyteleca.com", null},
 			{0,"EmailAllDomains", null, "consilio.com;harman;harman.com", null},
 			{29,"EmailRecipientNames", null, "Satish Pawal;Shunmugasundaram Senthivelu;Swapnal Sonawane", null},
 			{0,"EmailRecipientAddresses", null, "Robert.Superty@consilio.com", null},
 			{0,"EmailRecipientDomains", null, "consilio.com", null},
// 			{95,"DocFileSize", null, "9728", null}, 
// 			{138,"DocFileSize","RANGE", "60","9728"},
 			{841,"DocFileExtension", null,".msg", null},
  		};
      }
}