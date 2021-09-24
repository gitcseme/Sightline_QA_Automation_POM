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
			
			bc.stepInfo("Test case Id: RPMXCON-50911- DocviewTexttab");
			bc.stepInfo("****review on Text tab in Docview *****");
			dv.getDocView_textView().waitAndClick(10);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					driver.getWebDriver().findElement(By.id("divViewerText")).isDisplayed();}}),Input.wait60);
			Assert.assertTrue(driver.getWebDriver().findElement(By.id("divViewerText")).isDisplayed());
			docId = dv.getDocView_CurrentDocId().getText();
			bc.passedStep("****Document is reviewed on Text tab in Doc view*****");
			
			
			bc.stepInfo("Test case Id: RPMXCON-51914- DocviewImagetab");
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
			
			bc.stepInfo("Test case Id: RPMXCON-51689- DocviewTranslationstab");
			bc.stepInfo("****Document reviewe on Translations tab in Doc view*****");
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
		
		
		Thread.sleep(4000);
		Assert.assertTrue(dv.getDocView_textArea().Displayed());
		bc.stepInfo("Test case Id: RPMXCON-51969- DocviewNavigation");
		bc.stepInfo("****Navigating Last document in DocView *****");
		dv.getDocView_Last().waitAndClick(10);
		Thread.sleep(4000);
		Assert.assertTrue(dv.getDocView_textArea().Displayed());
		bc.passedStep("****Navigation to Last document in DocView is success*****");
		bc.stepInfo("Test case Id: RPMXCON-51968 - DocviewNavigation");
		bc.stepInfo("****Navigating first document in DocView *****");
		dv.getDocView_First().waitAndClick(10);
		Thread.sleep(4000);
		Assert.assertTrue(dv.getDocView_textArea().Displayed());
		bc.passedStep("****Navigation to First document in DocView is success*****");
		bc.stepInfo("Test case Id: RPMXCON-51967 - DocviewNavigation");
		bc.stepInfo("****Navigating Next document in DocView *****");
		dv.getDocView_Next().waitAndClick(10);
		Thread.sleep(4000);
		Assert.assertTrue(dv.getDocView_textArea().Displayed());
		bc.passedStep("****Navigation to Next document in DocView is success*****");
		bc.stepInfo("Test case Id: RPMXCON-51966 - DocviewNavigation");
		bc.stepInfo("****Navigating Previous document in DocView*****");
		dv.getDocView_Previous().waitAndClick(10);
		Thread.sleep(4000);
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
