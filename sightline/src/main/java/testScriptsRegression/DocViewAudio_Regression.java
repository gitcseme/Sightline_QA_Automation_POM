package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.RedactionPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocViewAudio_Regression {
	Driver driver;
	LoginPage lp;
	String codingfrom = "Audio CF"+Utility.dynamicNameAppender();
	String RedactName = "AudioRedaction"+Utility.dynamicNameAppender();
	String remark = "Audio Remark"+Utility.dynamicNameAppender();
	String Comments = "Audio comment";
	DocViewPage docview ;
	BaseClass baseclass;
	SessionSearch search;
	
	@BeforeClass(alwaysRun = true)
	public void before() throws InterruptedException, ParseException, IOException {
		
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
	 
		driver = new Driver();
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseclass = new BaseClass(driver);
		
		//add comment field
    	CommentsPage comments = new CommentsPage(driver);
    	comments.AddComments("Comment"+Utility.dynamicNameAppender());
		
    	//Create coding for for assignment
		CodingForm cf = new CodingForm(driver);
		cf.createCodingform(codingfrom);
		cf.CodingformToSecurityGroup(codingfrom);
		
		RedactionPage redpage = new RedactionPage(driver);
		redpage.AddRedaction(RedactName,"RMU");
		
		lp.logout();
		driver.getWebDriver().get(Input.url);
		
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		docview = new DocViewPage(driver);
		search = new SessionSearch(driver);
		search.audioSearch("morning", "North American English");
		search.ViewInDocView();
	}
	
	  @Test(priority=1,groups={"regression"})
		public void Verifyaudiodocthruhistorydropdown() throws ParseException, InterruptedException {
		  baseclass.stepInfo("Test case Id: RPMXCON-51835 - Verify if user select Audio documents from Doc View History, document should be loaded immediately ");
		  baseclass.stepInfo("*****Select Audio Doc through history dropdown*****");	
		  docview.audiodocthruhistorydropdown();
		  baseclass.passedStep("*****Audio Doc Selected through history dropdown successfully*****");		
		}
	
	@Test(priority=2,groups={"smoke","regression"})
	public void AudioPersistentHits() throws ParseException, InterruptedException {
		baseclass.stepInfo("Test case Id: RPMXCON-51886 - Verify that persistent hits should be displayed on audio doc view when searched with term and work product");	
		baseclass.stepInfo("*****Verify Audio persistent hits*****");
		docview.VerifyAudiopersistentHit("morning");
		baseclass.passedStep("*****Audio persistent hits verified successfully*****");
		
	}
	
	@Test(priority=3,groups={"smoke","regression"})
	public void addPlay() throws ParseException, InterruptedException {
		baseclass.stepInfo("Test case Id: RPMXCON-51054 - Verify user can listen the audio files in Doc View when redirecting from basic search/saved search/doc list");	
		baseclass.stepInfo("***** Verify Audio Play*****");
		docview.playAudio();
		baseclass.passedStep("*****Audio Played successfully*****");
		
	}

	@Test(priority=4,groups={"smoke","regression"})
	public void addRemark() throws ParseException, InterruptedException {
		baseclass.stepInfo("Test case Id: RPMXCON-46857 -  Verify RMU/Reviewer can add remarks for audio files on doc view");	
		baseclass.stepInfo("*****Add Audio remark*****");
		docview.audioRemark(remark);
		baseclass.passedStep("*****Audio remark added successfully*****");
	}
	
	@Test(priority=5,groups={"smoke","regression"})
	public void DeleteRemark() throws ParseException, InterruptedException {
		baseclass.stepInfo("Test case Id: RPMXCON-46866 - To verify that remark can be deleted by reviewers in audio doc view");	
		baseclass.stepInfo("*****Delete Audio remark*****");
		docview.deleteRemark(remark);
		baseclass.passedStep("*****Audio remark deleted successfully*****");
	}
	
	@Test(priority=6,groups={"smoke","regression"})
	public void addComment() throws ParseException, InterruptedException {
		baseclass.stepInfo("Test case Id: RPMXCON-59815 -  Verify that User can add comments and Save Video file documents inside Doc view screen");
		baseclass.stepInfo("*****Add Audio coments*****");
		docview.audioComment(remark);
		baseclass.passedStep("*****Audio coments added successfully*****");
	}
	
	@Test(priority=7,groups={"smoke","regression"})
	public void AudioDownload() throws ParseException, InterruptedException {
		 baseclass.stepInfo("Test case Id: RPMXCON-51110 - Verify user should be able to click the download icon of audio file in doc view");
		 baseclass.stepInfo("*****Verify Audio download*****");
		docview.audioDownload();
		baseclass.passedStep("*****Audio download successfull*****");
	
	}
	
	@Test(priority=8,groups={"smoke","regression"})
	public void addredaction() throws ParseException, InterruptedException {
		baseclass.stepInfo("Test case Id: RPMXCON-46859 - Verify RMU/Reviewer can redact an audio file in security group");
		baseclass.stepInfo("*****Verify adding audio Redaction*****");
		docview.audioRedaction();
		baseclass.passedStep("*****Audio Redaction added successfully*****");
	}
	
	@Test(priority=9,groups={"smoke","regression"})
	public void Deleteredaction() throws ParseException, InterruptedException {
		baseclass.stepInfo("Test case Id: RPMXCON-46861 - Verify user can delete the redactions in a audio file");
		baseclass.stepInfo("*****Verify deleteing audio Redaction*****");
		docview.deleteRedaction();
		baseclass.passedStep("*****Audio Redaction deleted successfully*****");
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
				lp.clearBrowserCache();
			}
	}
}
