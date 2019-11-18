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
    	
		Input in = new Input();
		in.loadEnvConfig();
		//Open browser
		softAssertion= new SoftAssert();
		driver = new Driver();
		bc = new BaseClass(driver);
		ss= new SessionSearch(driver);
		

	}
	//RPMXCON-37996
	//@Test(groups={"regression"})
	public void autoSuggest() throws InterruptedException {
		
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		
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
		
			
		ss.getMetaDataSearchText1().SendKeys("P Al");
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
	//RPMXCON-37807
	@Test(groups={"regression"})
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
	//RPMXCON-38005
	//@Test(groups={"regression"})
	public void conceptuallySimilar() {
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		//Impersonate as RMU
		bc = new BaseClass(driver);
		bc.impersonateRMUtoReviewer();	
		ss.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		ss.getConceptuallyPlayButton().waitAndClick(10);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				ss.getCSHitsCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait90);
		Assert.assertTrue(Integer.parseInt(ss.getCSHitsCount().getText())>= 1);
	
		lp.logout();
	}
	//@Test(groups={"regression"})
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
	//@Test(groups={"smoke","regression"})
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
   //@Test(groups={"smoke","regression"})
	public void starSearch() {
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
	  	sessionSearch = new SessionSearch(driver);
    	
	  	Assert.assertEquals(sessionSearch.basicContentSearch("*"), 1202);
	}
	//@Test(groups={"regression"})
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

	//@Test(groups={"regression"})
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
	
}
