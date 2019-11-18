package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.interactions.Actions;
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

public class ToolTipsInfos {
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
    	
		/*Input in = new Input();
		in.loadEnvConfig();*/
		//Open browser
		softAssertion= new SoftAssert();
		driver = new Driver();
		bc = new BaseClass(driver);
		ss= new SessionSearch(driver);
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		

	}
	
	@Test(dataProvider="metaDataDateOnlyFields",groups= {"regression"})
	public void BSsearchToolTipsDateOnlyFields(String field) throws InterruptedException {
		String msg="Dates are entered in YYYY-MM-DD format.";
		Assert.assertEquals(ss.getToolTipMsgBS("IS",field).replaceAll(" ", ""),msg.replaceAll(" ", ""));
		bc.selectproject();
		Assert.assertEquals(ss.getToolTipMsgBS("Range",field).replaceAll(" ", ""),msg.replaceAll(" ", ""));
		bc.selectproject();
		
		
	
	}
	
	@Test(dataProvider="metaDataDateOnlyFields",groups= {"regression"})
	public void ASsearchToolTipsDateOnlyFields(String field) throws InterruptedException {
		String msg="Dates are entered in YYYY-MM-DD format.";
		Assert.assertEquals(ss.getToolTipMsgAS("IS",field).replaceAll(" ", ""),msg.replaceAll(" ", ""));
		bc.selectproject();
		Assert.assertEquals(ss.getToolTipMsgAS("Range",field).replaceAll(" ", ""),msg.replaceAll(" ", ""));
		bc.selectproject();
		
		
	
	}
	 @DataProvider(name = "metaDataDateOnlyFields")
	    public Object[][] dataProviderMethod1() {
	        return new Object[][] { {"DocDateDateOnly"},
	        	{"DateCreatedDateOnly"},
	        	{"MasterDateDateOnly"},
	        	{"EmailDateSentDateOnly"}
	        	};
	        	
	    };
	
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
