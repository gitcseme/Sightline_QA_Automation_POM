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
		
		Input input = new Input();
		input.loadEnvConfig();
				
    	//Open browser
		driver = new Driver();
		//Login as PA
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		
    	  
        
	}
	
		@Test(groups={"regression"},priority=1)
		public void  VerifyTabsOnDocView() throws InterruptedException {
 		
   		 	String docId;
			SessionSearch search = new SessionSearch(driver);
			search.basicContentSearch(Input.searchString1);
			search.ViewInDocView();
			
			DocViewPage dv = new DocViewPage(driver);
			dv.getDocView_textView().waitAndClick(10);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					driver.getWebDriver().findElement(By.id("divViewerText")).isDisplayed();}}),Input.wait60);
			Assert.assertTrue(driver.getWebDriver().findElement(By.id("divViewerText")).isDisplayed());
			docId = dv.getDocView_CurrentDocId().getText();
			
			dv.getDocView_Next().waitAndClick(10);
			Assert.assertTrue(!docId.equals(dv.getDocView_CurrentDocId().getText()));
			docId = dv.getDocView_CurrentDocId().getText();
			
			dv.getDocView_imagesView().waitAndClick(10);
			dv.getDocView_Next().waitAndClick(10);
			Assert.assertTrue(!docId.equals(dv.getDocView_CurrentDocId().getText()));
			docId = dv.getDocView_CurrentDocId().getText();
			
			dv.getDocView_imagesView().waitAndClick(10);
			dv.getDocView_Next().waitAndClick(10);
			Assert.assertTrue(!docId.equals(dv.getDocView_CurrentDocId().getText()));
			docId = dv.getDocView_CurrentDocId().getText();
			
			dv.getDocView_translationsView().waitAndClick(10);
			
			
			
		}
	@Test(groups={"regression"},priority=2)	
 	public void navigations() throws InterruptedException {
 		
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString1);
		search.ViewInDocView();
		
		DocViewPage dv = new DocViewPage(driver);
		Thread.sleep(4000);
		Assert.assertTrue(dv.getDocView_textArea().Displayed());
		
		dv.getDocView_Last().waitAndClick(10);
		Thread.sleep(4000);
		Assert.assertTrue(dv.getDocView_textArea().Displayed());
		
		dv.getDocView_First().waitAndClick(10);
		Thread.sleep(4000);
		Assert.assertTrue(dv.getDocView_textArea().Displayed());
		
		dv.getDocView_Next().waitAndClick(10);
		Thread.sleep(4000);
		Assert.assertTrue(dv.getDocView_textArea().Displayed());
		
		dv.getDocView_Previous().waitAndClick(10);
		Thread.sleep(4000);
		Assert.assertTrue(dv.getDocView_textArea().Displayed());
		
		

	}


	  @AfterMethod(alwaysRun = true)
		 public void takeScreenShot(ITestResult result) {
	   	 if(ITestResult.FAILURE==result.getStatus()){
	   		Utility bc = new Utility(driver);
	   		bc.screenShot(result);
	   	}
	    }
		
		//@AfterClass(alwaysRun = true)
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
