package testScriptsRegression;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import automationLibrary.Driver;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.AnnotationLayer;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.HomePage;
import pageFactory.LoginPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;


 public class DocViewP2 {
	Driver driver;
	LoginPage lp;
	DocViewPage docView;
	BaseClass bc;
	
    HomePage hm;
	//BaseClass bc;
	String Remark= "Re"+Utility.dynamicNameAppender();
	String newTag = "newtag"+Utility.dynamicNameAppender();
	String codingfrom = "CF"+Utility.dynamicNameAppender();
	String assignmentName= "assi"+Utility.dynamicNameAppender();
	String redactiontag= "RTag"+Utility.dynamicNameAppender();
	String annotationname = "annotationname"+Utility.dynamicNameAppender();
	String folderName = "Fol"+Utility.dynamicNameAppender();

	
	//For reviewer assign docs,so create assignment with coding form(with tags and comments) and distribute 
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws InterruptedException, ParseException, IOException {
		
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		//Open browser
		driver = new Driver();
		//Login as PA
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		bc = new BaseClass(driver);
		
    	  
        
	}
	
		@Test(groups={"regression"},priority=1)
		public void  VerifyTabsOnDocView() throws InterruptedException {
 		    
   		 	String docId;
   		    DocViewPage dv = new DocViewPage(driver);
			SessionSearch search = new SessionSearch(driver);
			search.basicContentSearch(Input.searchString1);
			search.ViewInDocView();
			
			bc.stepInfo("Test case Id: RPMXCON-50911- To verify when user can view the document in Text tab");
			bc.stepInfo("****review on Text tab in Docview *****");
			dv.getDocView_textView().waitAndClick(10);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					driver.getWebDriver().findElement(By.id("divViewerText")).isDisplayed();}}),Input.wait60);
			Assert.assertTrue(driver.getWebDriver().findElement(By.id("divViewerText")).isDisplayed());
			docId = dv.getDocView_CurrentDocId().getText();
			bc.passedStep("****Document is reviewed on Text tab in Doc view*****");
			
			
			bc.stepInfo("Test case Id: RPMXCON-51914- Verify that when user in on Images tab and completes the document then should be on Images tab for next navigated document");
			bc.stepInfo("****review on Images tab and navigate to next document in Doc view*****");
			dv.getDocView_Next().waitAndClick(10);
			Assert.assertTrue(!docId.equals(dv.getDocView_CurrentDocId().getText()));
			docId = dv.getDocView_CurrentDocId().getText();
			dv.getDocView_imagesView().waitAndClick(10);
			
			
			dv.getDocView_Next().waitAndClick(10);
			Assert.assertTrue(!docId.equals(dv.getDocView_CurrentDocId().getText()));
			docId = dv.getDocView_CurrentDocId().getText();
			dv.getDocView_imagesView().waitAndClick(10);
			bc.passedStep("****next navigated document is reviewed on image tab in Doc view*****");
			
			bc.stepInfo("Test case Id: RPMXCON-51689-Verify that document should be loaded successfully when document navigation done from Translations tab of previous document");
			bc.stepInfo("****Document review on Translations tab in Doc view*****");
			dv.getDocView_Next().waitAndClick(10);
			Assert.assertTrue(!docId.equals(dv.getDocView_CurrentDocId().getText()));
			docId = dv.getDocView_CurrentDocId().getText();
			
			
			dv.getDocView_translationsView().waitAndClick(10);
			bc.passedStep("****Document is reviewed on Translations tab in Doc view*****");
			
			
			
		}
	@Test(groups={"regression"},priority=2)	
 	public void navigations() throws InterruptedException {
		
		DocViewPage dv = new DocViewPage(driver);
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString1);
		search.ViewInDocView();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				dv.getDocView_textArea().Displayed()  ;}}), Input.wait60);
		
		
		//Thread.sleep(4000);
		Assert.assertTrue(dv.getDocView_textArea().Displayed());
		bc.stepInfo("Test case Id: RPMXCON-51969- Verify that navigating the last document in DocView should bring up the document in 4 sec and ready for the user to act up on");
		bc.stepInfo("****Navigating Last document in DocView *****");
		dv.getDocView_Last().waitAndClick(10);
		//Thread.sleep(4000);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				dv.getDocView_textArea().Displayed()  ;}}), Input.wait60);
		Assert.assertTrue(dv.getDocView_textArea().Displayed());
		bc.passedStep("****Navigation to Last document in DocView is success*****");
		bc.stepInfo("Test case Id: RPMXCON-51968 - Verify that navigating the first document in DocView should bring up the document in 4 sec and ready for the user to act up on");
		bc.stepInfo("****Navigating first document in DocView *****");
		dv.getDocView_First().waitAndClick(10);
		//Thread.sleep(4000);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				dv.getDocView_textArea().Displayed()  ;}}), Input.wait60);
		Assert.assertTrue(dv.getDocView_textArea().Displayed());
		bc.passedStep("****Navigation to First document in DocView is success*****");
		bc.stepInfo("Test case Id: RPMXCON-51967 - Verify that navigating the previous document in DocView should bring up the document in 4 sec and ready for the user to act up on");
		bc.stepInfo("****Navigating Next document in DocView *****");
		dv.getDocView_Next().waitAndClick(10);
		//Thread.sleep(4000);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				dv.getDocView_textArea().Displayed()  ;}}), Input.wait60);
		Assert.assertTrue(dv.getDocView_textArea().Displayed());
		bc.passedStep("****Navigation to Next document in DocView is success*****");
		bc.stepInfo("Test case Id: RPMXCON-51966 - Verify that navigating the next document in DocView should bring up the document in 4 sec and ready for the user to act up on");
		bc.stepInfo("****Navigating Previous document in DocView*****");
		dv.getDocView_Previous().waitAndClick(10);
		//Thread.sleep(4000);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				dv.getDocView_textArea().Displayed()  ;}}), Input.wait60);
		Assert.assertTrue(dv.getDocView_textArea().Displayed());
		bc.passedStep("****Navigation to Previous document in DocView is success*****");
		
		

	}


	  @AfterMethod(alwaysRun = true)
		 public void takeScreenShot(ITestResult result) {
	   	 if(ITestResult.FAILURE==result.getStatus()){
	   		Utility bc = new Utility(driver);
	   		bc.screenShot(result);
	   	}
	    }
		
		@AfterClass(alwaysRun = true)
		public void close(){
			try{ 
				lp.logout();
			     //lp.quitBrowser();	
				}finally {
					lp.quitBrowser();
					LoginPage.clearBrowserCache();
				}
		}
	}
