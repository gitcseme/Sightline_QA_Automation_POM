package testScriptsRegression;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.io.IOException;
import java.text.ParseException;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.LoginPage;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SystemTemplate_Regression {
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
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc = new BaseClass(driver);
		
		
	}
	
	@Test(priority =1,groups={"smoke","regression"})
	public void provisionedTags_8633_1() throws  InterruptedException {
		tnfpage = new TagsAndFoldersPage(driver);
	    tnfpage.provisionedTags();
	}
	
	@Test(priority =2,groups={"smoke","regression"})
	public void tagsClassifications_8657_1() throws InterruptedException {
		bc.stepInfo("Test case Id:RPMXCON-52927-tagsClassifications");
        tnfpage = new TagsAndFoldersPage(driver);
		tnfpage.tagsClassifications();
		bc.passedStep("Verified Tag Classification under All Tags");
	}
	
	@Test(priority =3,groups={"smoke","regression"})
	public void layerAnnotations_8660_1() throws InterruptedException {
		bc.stepInfo("Test case Id:RPMXCON-52930-layerAnnotations");
     	tnfpage = new TagsAndFoldersPage(driver);
		tnfpage.layerAnnotations();
		bc.passedStep("Verified provisioned Annotation Layer is available in Default Security Group");
	}
	
	@Test(priority =4,groups={"smoke","regression"})
	public void provisionedFolder_8661_1() throws  InterruptedException {
	
		bc.stepInfo("Test case Id:RPMXCON-52931-provisionedFolder");

		tnfpage = new TagsAndFoldersPage(driver);
		tnfpage.provisionedFolder();
		bc.passedStep("Verified Productions is available under All Folders ");
	}
	
	@Test(priority =5,groups={"smoke","regression"})
	public void redactionTags_8663_1() throws  InterruptedException {
	
		bc.stepInfo("Test case Id:RPMXCON-52933-redactionTags");
		tnfpage = new TagsAndFoldersPage(driver);
		tnfpage.redactionTags();
		bc.passedStep("Verified provisioned Redaction Tags are available under Default Security Group ");
		
	}
	
	@Test(priority =6,groups={"smoke","regression"})
	public void provisionedFolderGroup_9028() throws InterruptedException {
	
		bc.stepInfo("Test case Id:RPMXCON-53129-provisionedFolderGroup");
		tnfpage = new TagsAndFoldersPage(driver);
		tnfpage.provisionedFolderGroup();
		bc.passedStep("Productions is available under Default Security Group");
		lp.logout();
	}
	
	
	@Test(priority =7,groups={"smoke","regression"})
	public void provisionedTags_8633_2() throws InterruptedException {
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Test case Id:RPMXCON-52910-provisionedTagsAsRMU");
        tnfpage = new TagsAndFoldersPage(driver);
		tnfpage.provisionedTagsAsRMU();
		bc.passedStep("Verified provisioned Tags are available under Default Tags as RMU User");
		
	}
	
	
	
	@Test(priority =8,groups={"smoke","regression"})
	public void tagsClassifications_8657_2() throws InterruptedException {
		//lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Test case Id:RPMXCON-52927-tagsClassificationsAsRMU");
		tnfpage = new TagsAndFoldersPage(driver);
		tnfpage.tagsClassificationsAsRMU();
		bc.passedStep("Verified Tag Classification under All Tags for RMU User");
		//lp.logout();

	}
	

	
	
	@Test(priority =9,groups={"smoke","regression"})
	public void layerAnnotations_8660_2() throws InterruptedException {
			bc.stepInfo("Test case Id:RPMXCON-52930-layerAnnotationsAsRMU");
		tnfpage = new TagsAndFoldersPage(driver);
		tnfpage.layerAnnotationsAsRMU();
		bc.passedStep("Verified provisioned Annotation Layer is available in the Project as RMU User");
		
	}
	

	
	@Test(priority =10,groups={"smoke","regression"})
	public void provisionedFolder_8661_2() throws  InterruptedException {
		bc.stepInfo("Test case Id:RPMXCON-52931-provisionedFolderAsRMU");
		tnfpage = new TagsAndFoldersPage(driver);
		tnfpage.provisionedFolderAsRMU();
		bc.passedStep("Verified Productions is available under under All Folders as RMU User ");
			}
	
	
	@Test(priority =11,groups={"smoke","regression"})
	public void redactionTags_8663_2() throws  InterruptedException {
		bc.stepInfo("Test case Id:RPMXCON-52933-redactionTagsAsRMU");
		tnfpage = new TagsAndFoldersPage(driver);
		tnfpage.redactionTagsAsRMU();
		bc.passedStep("Verified provisioned Redaction Tags are available in the Project as RMU User");

	}
	
	
	
	@Test(priority =12,groups={"smoke","regression"})
	public void codingForm_8719() throws InterruptedException {
		bc.stepInfo("Test case Id:RPMXCON-52971-codingForm");
		cf = new CodingForm(driver);
		cf.codingForm();
		bc.passedStep("Verified provisioned CF is available in the Project");
		}
	
	@Test(priority =13,groups={"smoke","regression"})
	public void codingFormTagsOrder_8720() throws InterruptedException {
		bc.stepInfo("Test case Id:RPMXCON-52972-codingFormTagsOrder");
		cf = new CodingForm(driver);
		cf.codingFormTagsOrder();
		bc.passedStep("Verified provisioned CF with Specific Tags and Order is  available in the Project- RMU");
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
