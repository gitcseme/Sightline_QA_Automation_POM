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
	
	
	
	@BeforeClass(alwaysRun=true)
	public void preCondition() throws ParseException, InterruptedException, IOException{
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		
		/*Input in = new Input();
		in.loadEnvConfig();
		*/
    	//Open browser
		driver = new Driver();
		bc = new BaseClass(driver);
		ss = new SessionSearch(driver);
		//Login as a PA
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
	}
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
	
    @Test(groups={"regression"})
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
		bc.selectproject();
		softAssertion.assertTrue(ss.advancedContentSearch("CustodianName: (  P Allen)"+Keys.ENTER+"OR"+Keys.ENTER+Input.searchString1)>=1166);

		bc.selectproject();
		softAssertion.assertTrue(ss.advancedContentSearch("CustodianName: (  P Allen)"+Keys.ENTER+"AND"+Keys.ENTER+Input.searchString1)>=19);
		
		bc.selectproject();
		softAssertion.assertTrue(ss.advancedContentSearch("CustodianName: (  P Allen)"+Keys.ENTER+"NOT"+Keys.ENTER+Input.searchString1)>=1116);

		bc.selectproject();
		softAssertion.assertTrue(ss.advancedContentSearch(Input.searchString1+Keys.ENTER+"NOT"+Keys.ENTER+"CustodianName: (  P Allen)")>0);


		bc.selectproject();
		softAssertion.assertTrue(ss.advancedContentSearch("\"discrepancy scripts\"~3")>=4);

		softAssertion.assertAll();
	}
	
	
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
	
	
	@Test(groups={"smoke","regression"})
	public void metaDataSearch() {
		SoftAssert softAssertion= new SoftAssert();
		driver.getWebDriver().get(Input.url+ "Search/Searches");
    	bc.selectproject();
    	softAssertion.assertEquals(Input.metaDataCNcount,ss.basicMetaDataSearch("CustodianName", null, Input.metaDataCN, null));//1135);
		bc.selectproject();
		softAssertion.assertEquals(340,ss.basicMetaDataSearch("DateCreatedDateOnly", "RANGE", "1990-05-05", "2018-05-05"));
		
		softAssertion.assertAll();
	}
	@Test(groups={"regression"})
	public void metaDataSearchsBS() {
		SoftAssert softAssertion= new SoftAssert();
		driver.getWebDriver().get(Input.url+ "Search/Searches");
    	bc.selectproject();
    	softAssertion.assertEquals(95,ss.basicMetaDataSearch("MasterDate", "IS", "1980-01-01", null));
		
    	//with time in IS 
    	bc.selectproject();
    	softAssertion.assertEquals(11,ss.basicMetaDataSearch("MasterDate", "IS", "1989-02-10 16:59:39", null));
		
    	
    	
    	bc.selectproject();
		softAssertion.assertEquals(124,ss.basicMetaDataSearch("MasterDate", "RANGE", "1980-01-01", "2000-01-01"));
		
		
		bc.selectproject();
		softAssertion.assertEquals(1,ss.basicMetaDataSearch("CreateDate", "IS", "1993-08-11", null));
	
		bc.selectproject();
		softAssertion.assertEquals(340,ss.basicMetaDataSearch("CreateDate", "RANGE", "1986-01-01", "2018-01-01"));
       
		//with time in Range	
		bc.selectproject();
		softAssertion.assertEquals(170,ss.basicMetaDataSearch("CreateDate", "RANGE", "2010-06-17 05:53:18", "2010-10-18 05:53:18"));
	
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("EmailSentDate", "IS", "1990-05-05", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("EmailSentDate", "RANGE", "1990-05-05", "2000-05-05"));
		
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("AppointmentStartDate", "IS", "1990-05-05", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("AppointmentStartDate", "RANGE", "1990-05-05", "2000-05-05"));
		//check IS and Range options
		/*bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("AppointmentEndDateOnly", "IS", "1990-05-05", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("AppointmentEndDateOnly", "RANGE", "1990-05-05", "2000-05-05"));
	*/	
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DocDateDateOnly", "IS", "1990-05-05", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DocDateDateOnly", "RANGE", "1990-05-05", "2000-05-05"));
		
		/*bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DateAccessedDateOnly", "IS", "1990-05-05", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DateAccessedDateOnly", "RANGE", "1990-05-05", "2000-05-05"));
		*/
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DateCreatedDateOnly", "IS", "1990-05-05", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DateCreatedDateOnly", "RANGE", "1990-05-05", "2000-05-05"));
		
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DateEditedDateOnly", "IS", "1990-05-05", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DateEditedDateOnly", "RANGE", "1990-05-05", "2000-05-05"));
		
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DateModifiedDateOnly", "IS", "1990-05-05", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DateModifiedDateOnly", "RANGE", "1990-05-05", "2000-05-05"));
		
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DatePrintedDateOnly", "IS", "1990-05-05", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DatePrintedDateOnly", "RANGE", "1990-05-05", "2000-05-05"));
		
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DateReceivedDateOnly", "IS", "1990-05-05", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DateReceivedDateOnly", "RANGE", "1990-05-05", "2000-05-05"));
		
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DateSavedDateOnly", "IS", "1990-05-05", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("DateSavedDateOnly", "RANGE", "1990-05-05", "2000-05-05"));
		
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("MasterDateDateOnly", "IS", "1990-05-05", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("MasterDateDateOnly", "RANGE", "1990-05-05", "2000-05-05"));
		
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("EmailDateSentDateOnly", "IS", "1990-05-05", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("EmailDateSentDateOnly", "RANGE", "1990-05-05", "2000-05-05"));
		
		//field mapping is not done for blow meta data search
		
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("AppointmentStartDateOnly", "IS", "1990-05-05", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0<=ss.basicMetaDataSearch("AppointmentStartDateOnly", "RANGE", "1990-05-05", "2000-05-05"));
		
		softAssertion.assertAll();
	}
	@Test(groups={"regression"})
	public void metaDataSearchsAS() {
		SoftAssert softAssertion= new SoftAssert();
		driver.getWebDriver().get(Input.url+ "Search/Searches");
    	bc.selectproject();
    	softAssertion.assertEquals(95,ss.advancedMetaDataSearch("MasterDate", "IS", "1980-01-01", null));
		

    	//with time in IS 
    	bc.selectproject();
    	softAssertion.assertEquals(11,ss.advancedMetaDataSearch("MasterDate", "IS", "1989-02-10 16:59:39", null));
		
    	bc.selectproject();
		softAssertion.assertEquals(124,ss.advancedMetaDataSearch("MasterDate", "RANGE", "1980-01-01", "2000-01-01"));
		bc.selectproject();
		softAssertion.assertEquals(1,ss.advancedMetaDataSearch("CreateDate", "IS", "1993-08-11", null));
	
		bc.selectproject();
		softAssertion.assertEquals(340,ss.advancedMetaDataSearch("CreateDate", "RANGE", "1986-01-01", "2018-01-01"));
	
		//with time in Range	
		bc.selectproject();
		softAssertion.assertEquals(170,ss.advancedMetaDataSearch("CreateDate", "RANGE", "2010-06-17 05:53:18", "2010-10-18 05:53:18"));
			
		bc.selectproject();
		softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("EmailSentDate", "IS", "1990-05-05", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("EmailSentDate", "RANGE", "1990-05-05", "2000-05-05"));
		
		bc.selectproject();
		softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("AppointmentStartDate", "IS", "1990-05-05", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("AppointmentStartDate", "RANGE", "1990-05-05", "2000-05-05"));
		
		//check IS and Range options
		//bc.selectproject();
		//softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("AppointmentEndDateOnly", "IS", "1990-05-05", null));
		
		//bc.selectproject();
		//softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("AppointmentEndDateOnly", "RANGE", "1990-05-05", "2000-05-05"));
		
		bc.selectproject();
		softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("DocDateDateOnly", "IS", "1990-05-05", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("DocDateDateOnly", "RANGE", "1990-05-05", "2000-05-05"));
		
		//bc.selectproject();
		//softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("DateAccessedDateOnly", "IS", "1990-05-05", null));
		
		//bc.selectproject();
		//softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("DateAccessedDateOnly", "RANGE", "1990-05-05", "2000-05-05"));
		
		//bc.selectproject();
		//softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("DateCreatedDateOnly", "IS", "1990-05-05", null));
		
		/*bc.selectproject();
		softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("DateCreatedDateOnly", "RANGE", "1990-05-05", "2000-05-05"));
		
		bc.selectproject();
		softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("DateEditedDateOnly", "IS", "1990-05-05", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("DateEditedDateOnly", "RANGE", "1990-05-05", "2000-05-05"));
		
		bc.selectproject();
		softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("DateModifiedDateOnly", "IS", "1990-05-05", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("DateModifiedDateOnly", "RANGE", "1990-05-05", "2000-05-05"));
		
		bc.selectproject();
		softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("DatePrintedDateOnly", "IS", "1990-05-05", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("DatePrintedDateOnly", "RANGE", "1990-05-05", "2000-05-05"));
		
		bc.selectproject();
		softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("DateReceivedDateOnly", "IS", "1990-05-05", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("DateReceivedDateOnly", "RANGE", "1990-05-05", "2000-05-05"));
		
		bc.selectproject();
		softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("DateSavedDateOnly", "IS", "1990-05-05", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("DateSavedDateOnly", "RANGE", "1990-05-05", "2000-05-05")); 
		*/
		bc.selectproject();
		softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("MasterDateDateOnly", "IS", "1990-05-05", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("MasterDateDateOnly", "RANGE", "1990-05-05", "2000-05-05"));
		
		bc.selectproject();
		softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("EmailDateSentDateOnly", "IS", "1990-05-05", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("EmailDateSentDateOnly", "RANGE", "1990-05-05", "2000-05-05"));
		
		
	/* * //field mapping is not done for blow meta data search
	 * 
	 * *//* bc.selectproject();
		softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("AppointmentStartDateOnly", "IS", "1990-05-05", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0>=ss.advancedMetaDataSearch("AppointmentStartDateOnly", "RANGE", "1990-05-05", "2000-05-05"));
	*/	
		bc.selectproject();
		softAssertion.assertTrue(4==ss.advancedMetaDataSearch("EmailAuthorName", null, "(Gouri Dhavalikar)", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0>ss.advancedMetaDataSearch("EmailAuthorAddress", null, "Gouri.Dhavalikar@symphonyteleca.com", null));
		
		
		bc.selectproject();
		softAssertion.assertTrue(26==ss.advancedMetaDataSearch("EmailAllDomains", null, "consilio.com;harman;harman.com", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0>ss.advancedMetaDataSearch("EmailRecipientNames", null, "Satish Pawal;Shunmugasundaram Senthivelu;Swapnal Sonawane", null));
		
		bc.selectproject();
		softAssertion.assertTrue(0>ss.advancedMetaDataSearch("EmailRecipientAddresses", null, "Robert.Superty@consilio.com", null));
		
		bc.selectproject();
		softAssertion.assertTrue(26==ss.advancedMetaDataSearch("EmailRecipientDomains", null, "consilio.com", null));
		
		bc.selectproject();
		softAssertion.assertTrue(95==ss.advancedMetaDataSearch("DocFileSize", null, "9728", null));
		
		bc.selectproject();
		softAssertion.assertTrue(138==ss.advancedMetaDataSearch("DocFileSize","RANGE", "60","9728"));
		
		bc.selectproject();
		softAssertion.assertTrue(841==ss.advancedMetaDataSearch("DocFileExtension", null,".msg", null));
		
		softAssertion.assertAll();
	}
	
	
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
	@Test(groups={"smoke","regression"})
	public void audioSearch() {
		bc.selectproject();
		driver.getWebDriver().get(Input.url+ "Search/Searches");
       	Assert.assertTrue(ss.audioSearch(Input.audioSearchString1,"North American English")>=1);

	}
	
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
