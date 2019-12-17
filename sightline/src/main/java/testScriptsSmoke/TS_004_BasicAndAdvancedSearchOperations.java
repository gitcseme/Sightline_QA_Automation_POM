package testScriptsSmoke;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import org.testng.ITestResult;

import static org.testng.Assert.fail;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;

import org.openqa.selenium.Keys;
import automationLibrary.Driver;
import junit.framework.Assert;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;

public class TS_004_BasicAndAdvancedSearchOperations {

	Driver driver; 
	LoginPage lp;
	SessionSearch ss;
	BaseClass bc;
	
	String tagName = "tagSearch"+Utility.dynamicNameAppender();
	String folderName = "folderSearch"+Utility.dynamicNameAppender();
	String saveSearchName = "savedSearch"+Utility.dynamicNameAppender();
	String assignmentName = "assignmentSearch"+Utility.dynamicNameAppender();
	
	/*
	 * Author : Suresh Bavihalli
	 * Created date: April 2019
	 * Modified date: 
	 * Modified by:
	 * Description : Login as PAU, from here all the scripts will run! 
	 */	
	@BeforeClass(alwaysRun=true)
	public void preCondition() throws ParseException, InterruptedException, IOException{
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		
		/*
		 * Input in = new Input(); in.loadEnvConfig();
		 */
		
    	//Open browser
		driver = new Driver();
		bc = new BaseClass(driver);
		ss = new SessionSearch(driver);
		//Login as a PA
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
	}
	
	/*
	 * Author : Suresh Bavihalli
	 * Created date: Feb 2019
	 * Modified date: 
	 * Modified by:
	 * Description : As a PA user validate content search in Basic search with operators  
	 */	
	@Test(groups={"smoke","regression"})
	public void contentSearchWithOperatorsInBS() {
		SoftAssert softAssertion= new SoftAssert();
		driver.getWebDriver().get(Input.url+ "Search/Searches");
    	bc.selectproject();
    	softAssertion.assertEquals(Input.pureHitSeachString1,ss.basicContentSearch(Input.searchString1));
		bc.selectproject();
		softAssertion.assertEquals(Input.pureHitSeachString2,ss.basicContentSearch(Input.searchString2));
		bc.selectproject();
		softAssertion.assertEquals(Input.searchString1ANDsearchString2,ss.basicContentSearch(Input.searchString1+Keys.ENTER+"AND"+Keys.ENTER+Input.searchString2));
		bc.selectproject();
		softAssertion.assertEquals(Input.searchString1ANDsearchString2,ss.basicContentSearch(Input.searchString2+Keys.ENTER+"AND"+Keys.ENTER+Input.searchString1));
		bc.selectproject();
		softAssertion.assertEquals(Input.searchString1ORsearchString2,ss.basicContentSearch(Input.searchString1+Keys.ENTER+"OR"+Keys.ENTER+Input.searchString2));
		bc.selectproject();
		softAssertion.assertEquals(Input.searchString1NOTsearchString2,ss.basicContentSearch(Input.searchString1+Keys.ENTER+"NOT"+Keys.ENTER+Input.searchString2));
		bc.selectproject();
		softAssertion.assertEquals(Input.searchString2NOTsearchString1,ss.basicContentSearch(Input.searchString2+Keys.ENTER+"NOT"+Keys.ENTER+Input.searchString1));

		bc.selectproject();
		softAssertion.assertTrue(ss.basicContentSearch("\"very long\"")>=1);

		softAssertion.assertAll();
	}
	/*
	 * Author : Suresh Bavihalli
	 * Created date: Feb 2019
	 * Modified date: 
	 * Modified by:
	 * Description : As a PA user validate content search in advanced search with operators  
	 */	
	@Test(groups={"smoke","regression"})
	public void contentSearchWithOperatorsInAS() {
		SoftAssert softAssertion= new SoftAssert();
		driver.getWebDriver().get(Input.url+ "Search/Searches");
    	bc.selectproject();
    	softAssertion.assertEquals(Input.pureHitSeachString1,ss.advancedContentSearch(Input.searchString1));
		bc.selectproject();
		softAssertion.assertEquals(Input.pureHitSeachString2,ss.advancedContentSearch(Input.searchString2));
		bc.selectproject();
		softAssertion.assertEquals(Input.searchString1ANDsearchString2,ss.advancedContentSearch(Input.searchString1+Keys.ENTER+"AND"+Keys.ENTER+Input.searchString2));
		bc.selectproject();
		softAssertion.assertEquals(Input.searchString1ANDsearchString2,ss.advancedContentSearch(Input.searchString2+Keys.ENTER+"AND"+Keys.ENTER+Input.searchString1));
		bc.selectproject();
		softAssertion.assertEquals(Input.searchString1ORsearchString2,ss.advancedContentSearch(Input.searchString1+Keys.ENTER+"OR"+Keys.ENTER+Input.searchString2));
		bc.selectproject();
		softAssertion.assertEquals(Input.searchString1NOTsearchString2,ss.advancedContentSearch(Input.searchString1+Keys.ENTER+"NOT"+Keys.ENTER+Input.searchString2));
		bc.selectproject();
		softAssertion.assertEquals(Input.searchString2NOTsearchString1,ss.advancedContentSearch(Input.searchString2+Keys.ENTER+"NOT"+Keys.ENTER+Input.searchString1));
		softAssertion.assertAll();
	}
	
	/*
	 * Author : Suresh Bavihalli
	 * Created date: Feb 2019
	 * Modified date: 
	 * Modified by:
	 * Description : RPMXCON_37690 is scripted  
	 */
	////@Test(groups={"regression"})
	public void Search_RPMXCON_37690() throws InterruptedException {
		SoftAssert softAssertion= new SoftAssert();
		String saveSearch = "01Test"+Utility.dynamicNameAppender();
		bc.selectproject();
		ss.basicContentSearch("MasterDate: [1980-01-01 TO 2018-01-01]"+Keys.ENTER+"AND"+Keys.ENTER+
				"("+Keys.ENTER+"\"Test\""+Keys.ENTER+"OR"+Keys.ENTER+"\"Commit\""+Keys.ENTER+")"
				+Keys.ENTER+"AND"+Keys.ENTER+"("+Keys.ENTER+"\"Test\""+Keys.ENTER+"OR"+Keys.ENTER+"\"Commit\""+Keys.ENTER+")");	

		//Save the search
		ss.saveSearch(saveSearch);
		
		//View docs from session search
		ss.ViewInDocView();
		DocViewPage dv= new DocViewPage(driver);
		softAssertion.assertTrue(dv.getPersistentHit("Test").equals("Test- 1 Hit"));
		softAssertion.assertTrue(dv.getPersistentHit("Commit").equals("Commit- 1 Hit"));
		softAssertion.assertTrue(dv.getPersistentHit("MasterDat").equals("NULL"));
		
		//View docs from saved search
		driver.getWebDriver().get(Input.url+ "Search/Searches");
    	
		SavedSearch svdSe= new SavedSearch(driver);
		svdSe.savedSearchToDocView(saveSearch);
		softAssertion.assertTrue(dv.getPersistentHit("Test").equals("Test- 1 Hit"));
		softAssertion.assertTrue(dv.getPersistentHit("Commit").equals("Commit- 1 Hit"));
		softAssertion.assertTrue(dv.getPersistentHit("MasterDat").equals("NULL"));

		softAssertion.assertAll();
		
	}
	
	/*
	 * Author : Suresh Bavihalli
	 * Created date: Feb 2019
	 * Modified date: 
	 * Modified by:
	 * Description : As a PA user validate meta data searches in both basic and advance searches
	 */	
	@Test(groups={"smoke","regression"})
	public void metaDataSearch() {
		
		SoftAssert softAssertion= new SoftAssert();
		//Check in basic search--------------------------------------- 
		driver.getWebDriver().get(Input.url+ "Search/Searches");
    	bc.selectproject();
    	softAssertion.assertEquals(Input.metaDataCNcount,ss.basicMetaDataSearch("CustodianName", null, Input.metaDataCN, null));
		
    	bc.selectproject();
		if(Input.suite.equalsIgnoreCase("smoke"))
			softAssertion.assertTrue(85==ss.basicMetaDataSearch("DateCreatedDateOnly", "RANGE", "1990-05-05", "2018-05-05"));
		else 
			softAssertion.assertTrue(340==ss.basicMetaDataSearch("DateCreatedDateOnly", "RANGE", "1990-05-05", "2018-05-05"));
		
		bc.selectproject();
    	softAssertion.assertTrue(1==ss.basicMetaDataSearch("MasterDate", "IS", "2010-04-06", null));
		
    	//with time in IS 
    	bc.selectproject();
    	softAssertion.assertTrue(1==ss.basicMetaDataSearch("MasterDate", "IS", "2010-04-06 22:18:00", null));
	
    	bc.selectproject();
    	if(Input.suite.equalsIgnoreCase("smoke"))
    		softAssertion.assertTrue(116==ss.basicMetaDataSearch("MasterDate", "RANGE", "1986-04-06", "2010-04-06"));
		else
			softAssertion.assertTrue(1087==ss.basicMetaDataSearch("MasterDate", "RANGE", "1986-04-06", "2010-04-06"));
			
		//Check in Advance Search---------------------------------------------
		bc.selectproject();
		softAssertion.assertTrue(85==ss.advancedMetaDataSearch("CreateDate", "IS", "2010-10-18", null));
	
		bc.selectproject();
		softAssertion.assertTrue(85==ss.advancedMetaDataSearch("CreateDate", "RANGE", "2000-10-18", "2010-10-18"));
	
		//with time in Range	
		bc.selectproject();
		if(Input.suite.equalsIgnoreCase("smoke"))
			softAssertion.assertTrue(85>=ss.advancedMetaDataSearch("CreateDate", "RANGE", "1960-10-18 12:01:12", "2010-10-18 06:12:12"));
		else
			softAssertion.assertTrue(340==ss.advancedMetaDataSearch("CreateDate", "RANGE", "1960-10-18 12:01:12", "2010-10-18 06:12:12"));
		
		
		softAssertion.assertAll();
	}
	
	/*
	 * Author : Suresh Bavihalli
	 * Created date: April 2019
	 * Modified date: 
	 * Modified by:
	 * Description : save the search and validate in workproduct 
	 */	
	@Test(groups={"smoke","regression"})
	public void searchsavedSearch() {
		driver.getWebDriver().get(Input.url+ "Search/Searches");
    	bc.selectproject();
		ss.basicContentSearch(Input.searchString1);
		ss.saveSearch(saveSearchName);
		
		 bc.selectproject();
		 ss.switchToWorkproduct();
		 ss.searchSavedSearch(saveSearchName);
		
		Assert.assertEquals(Input.pureHitSeachString1,ss.serarchWP());

	}
	
	/*
	 * Author : Suresh Bavihalli
	 * Created date: April 2019
	 * Modified date: 
	 * Modified by:
	 * Description : Search audio files and validate the count
	 */	
	@Test(groups={"smoke","regression"})
	public void audioSearch() {
		bc.selectproject();
		driver.getWebDriver().get(Input.url+ "Search/Searches");
       	Assert.assertTrue(ss.audioSearch(Input.audioSearchString1,"North American English")>=1);

	}
	
	/*
	 * Author : Suresh Bavihalli
	 * Created date: April 2019
	 * Modified date: 
	 * Modified by:
	 * Description : validate conceptual Search in advance search with the low mid and high range options 
	 */	
	@Test(groups={"smoke","regression"})
	public void ConceptualSearch() {
		SoftAssert softAssertion= new SoftAssert();
		bc.selectproject();
		/*softAssertion.assertTrue(ss.conceptualSearch(Input.conceptualSearchString1,"right")>=0);
		bc.selectproject();*/
		driver.getWebDriver().get(Input.url+ "Search/Searches");
		softAssertion.assertTrue(ss.conceptualSearch(Input.conceptualSearchString1,"mid")>=1);
		/*bc.selectproject();
		softAssertion.assertTrue(ss.conceptualSearch(Input.conceptualSearchString1,"left")>=2);*/

		softAssertion.assertAll();
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
	@AfterClass(alwaysRun=true)
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
