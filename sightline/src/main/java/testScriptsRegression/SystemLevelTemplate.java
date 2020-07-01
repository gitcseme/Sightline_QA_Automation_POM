package testScriptsRegression;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.io.IOException;
import java.text.ParseException;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import automationLibrary.Driver;
import junit.framework.Assert;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.LoginPage;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SystemLevelTemplate {
	Driver driver;
	LoginPage lp;
	TagsAndFoldersPage tnfpage;
	BaseClass bc;
	CodingForm cf;

	@BeforeClass(alwaysRun = true)
	public void before() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
	 	driver = new Driver();
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		
	}
	
	@Test(priority =1,groups={"smoke","regression"})
	public void provisionedTags_8633_1() throws  InterruptedException {
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		tnfpage = new TagsAndFoldersPage(driver);
	    tnfpage.provisionedTags();
	    lp.logout();
	
	}
	
	@Test(priority =2,groups={"smoke","regression"})
	public void provisionedTags_8633_2() throws InterruptedException {
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		tnfpage = new TagsAndFoldersPage(driver);
	    tnfpage.provisionedTagsAsRMU();
	    lp.logout();
	
	}
	
	@Test(priority =3,groups={"smoke","regression"})
	public void tagsClassifications_8657_1() throws InterruptedException {
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		tnfpage = new TagsAndFoldersPage(driver);
	    tnfpage.tagsClassifications();
	    lp.logout();
	
	}
	
	@Test(priority =4,groups={"smoke","regression"})
	public void tagsClassifications_8657_2() throws InterruptedException {
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		tnfpage = new TagsAndFoldersPage(driver);
	    tnfpage.tagsClassificationsAsRMU();
	    lp.logout();
	
	}
	
	@Test(priority =5,groups={"smoke","regression"})
	public void layerAnnotations_8660_1() throws InterruptedException {
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		tnfpage = new TagsAndFoldersPage(driver);
	    tnfpage.layerAnnotations();
	    lp.logout();
	
	}
	
	@Test(priority =6,groups={"smoke","regression"})
	public void layerAnnotations_8660_2() throws InterruptedException {
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		tnfpage = new TagsAndFoldersPage(driver);
	    tnfpage.layerAnnotationsAsRMU();
	    lp.logout();
	
	}
	
	@Test(priority =7,groups={"smoke","regression"})
	public void provisionedFolder_8661_1() throws  InterruptedException {
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		tnfpage = new TagsAndFoldersPage(driver);
	    tnfpage.provisionedFolder();
	    lp.logout();
	
	}
	
	@Test(priority =8,groups={"smoke","regression"})
	public void provisionedFolder_8661_2() throws  InterruptedException {
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		tnfpage = new TagsAndFoldersPage(driver);
	    tnfpage.provisionedFolderAsRMU();
	    lp.logout();
	
	}
	
	@Test(priority =9,groups={"smoke","regression"})
	public void redactionTags_8663_1() throws  InterruptedException {
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		tnfpage = new TagsAndFoldersPage(driver);
	    tnfpage.redactionTags();
	    lp.logout();
	
	}
	
	@Test(priority =10,groups={"smoke","regression"})
	public void redactionTags_8663_2() throws  InterruptedException {
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		tnfpage = new TagsAndFoldersPage(driver);
	    tnfpage.redactionTagsAsRMU();
	    lp.logout();
	
	}
	
	@Test(priority =11,groups={"smoke","regression"})
	public void provisionedFolderGroup_9028() throws InterruptedException {
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		tnfpage = new TagsAndFoldersPage(driver);
	    tnfpage.provisionedFolderGroup();
	    lp.logout();
	
	}
	
	@Test(priority =12,groups={"smoke","regression"})
	public void codingForm_8719() throws InterruptedException {
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		cf = new CodingForm(driver);
	    cf.codingForm();
	    lp.logout();
	
	}
	
	@Test(priority =13,groups={"smoke","regression"})
	public void codingFormTagsOrder_8720() throws InterruptedException {
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		cf = new CodingForm(driver);
	    cf.codingFormTagsOrder();
	    lp.logout();
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
			
			}
		LoginPage.clearBrowserCache();
	}

}
