package testScriptsSmoke;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.io.IOException;
import java.text.ParseException;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import automationLibrary.Driver;
import pageFactory.AnnotationLayer;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.RedactionPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;

public class TS_008_AllManageModules {
	Driver driver;
	LoginPage lp;
	
	String cfName = "CF"+Utility.dynamicNameAppender();
	String keywordname = "Testkeyword"+Utility.dynamicNameAppender();;
	String keywords = "Test,This,The,To,Do";
	String Tag = "Tag"+Utility.dynamicNameAppender();
	String Folder = "Folder"+Utility.dynamicNameAppender();
	String Comment = "Comment"+Utility.dynamicNameAppender();
	String Redaction = "Redaction"+Utility.dynamicNameAppender();
	String securitygroupname = "securitygroupname"+Utility.dynamicNameAppender();
	String annotationname = "annotationname"+Utility.dynamicNameAppender();
	
	@BeforeClass(alwaysRun = true)
	public void before() throws ParseException, InterruptedException, IOException {
	System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
	
	driver = new Driver();
	lp = new LoginPage(driver);
	lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);

	}
	@Test(priority =1,groups={"smoke","regression"})
	public void createCommentField() throws ParseException, InterruptedException {
		
		//add comment field
    	CommentsPage comments = new CommentsPage(driver);
    	comments.AddComments(Comment);
    	System.out.println("Comment Successful");
	}
	
	@Test(priority =2,groups={"smoke","regression"})
	public void createKeywordField() throws ParseException, InterruptedException {
		
		KeywordPage page2= new KeywordPage(driver);
		page2.AddKeyword(keywordname, keywords);
		System.out.println("Keyword Successful");
	}
	
	@Test(priority =3,groups={"smoke","regression"})
	public void createRedaction() throws ParseException, InterruptedException {
		
		RedactionPage page3 = new RedactionPage(driver);
		page3.AddRedaction(Redaction);
		System.out.println("Redaction Successful");
	}
		
	@Test(priority =4,groups={"smoke","regression"})
	public void CreateTagField() throws ParseException, InterruptedException {
		
		TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
    	page.CreateTag(Tag,"Default Security Group");
    	System.out.println("Tag Successful");
	}	
		
	@Test(priority =5,groups={"smoke","regression"})
	public void CreateFolderField() throws ParseException, InterruptedException {
		
		TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
		page.CreateFolder(Folder,"Default Security Group");
    	System.out.println("Folder Successful");
	}	
				
	@Test(priority =6,groups={"smoke","regression"})
	public void CreateAnnotationLayer() throws ParseException, InterruptedException {
		
		AnnotationLayer alayer = new AnnotationLayer(driver);
    	alayer.AddAnnotation(annotationname);
    	System.out.println("Annotation Successful");
	}	
	@Test(priority =7,groups={"smoke","regression"})
	public void CreateCodingform() throws ParseException, InterruptedException {
		
		CodingForm cf = new CodingForm(driver);
    	cf.createCodingform(cfName);
    	System.out.println("Coding Form Successful");
    	
	}	
    	
	@Test(priority =8,groups={"smoke","regression"})
	public void CreateSecurityGroup() throws ParseException, InterruptedException {
		lp.logout();
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		SecurityGroupsPage scpage = new SecurityGroupsPage(driver);
    	scpage.AddSecurityGroup(securitygroupname);
    	System.out.println("Security Group Successful");  
    	
	}	
	
	@Test(priority =9,groups={"smoke","regression"})
	public void DeleteTagField() throws ParseException, InterruptedException {
		
		TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
    	page.DeleteTag(Tag,"Default Security Group");
    	System.out.println("Tag delete Successful");
	}	
		
	@Test(priority =10,groups={"smoke","regression"})
	public void DeleteFolderField() throws ParseException, InterruptedException {
		
		TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
		page.DeleteFolder(Folder,"Default Security Group");
    	System.out.println("Folder delete Successful");
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
				lp.clearBrowserCache();
			}
	}

}
