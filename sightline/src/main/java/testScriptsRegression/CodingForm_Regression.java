package testScriptsRegression;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.text.ParseException;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.CommentsPage;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class CodingForm_Regression {
	Driver driver;
	LoginPage lp;
	SessionSearch sessionSearch;
	int pureHit;
	CodingForm cf;
	BaseClass baseclass;
	String codingfrom = "CF"+Utility.dynamicNameAppender();
	String codingfromTagComm = "CFTagComm"+Utility.dynamicNameAppender();
	String codingfromTag = "CFTag"+Utility.dynamicNameAppender();
	String codingfromMeta = "CFMeta"+Utility.dynamicNameAppender();
	String AssgnName = "Assgn"+Utility.dynamicNameAppender();
	String tagname = "Tag"+Utility.dynamicNameAppender();
	String tagnameprev = "Tagprev"+Utility.dynamicNameAppender();
	String comment = "C"+Utility.dynamicNameAppender();
	
	@BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		Input in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
	     
		//Open browser
		driver = new Driver();
		baseclass = new BaseClass(driver);
		//Login as a RMU
    	lp=new LoginPage(driver);
    	lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
    	baseclass.passedStep("*****Login successfull*****");
    	baseclass.stepInfo("*****Creating codingfrom*****");
    	cf = new CodingForm(driver);
		cf.createCodingform(codingfrom);
		cf.CodingformToSecurityGroup(codingfrom);
		baseclass.passedStep("*****Coding form Created*****");
	}
	
	@Test(groups={"smoke","regression"},priority=1)
    public void EditCodingForm() throws InterruptedException {
		baseclass.stepInfo("Test case Id: RPMXCON-53995 - editCodingForm");
		baseclass.stepInfo("*****Edit Coding Form*****");
		System.out.println("******Edit Coding Form********");
		
		baseclass.stepInfo("*****Create Tag*****");		
		TagsAndFoldersPage tp = new TagsAndFoldersPage(driver);
		tp.CreateTag(tagname,"Default Security Group");
		baseclass.passedStep("*****Tag added successfully*****");
		
		baseclass.stepInfo("*****Adding comment field*****");
		CommentsPage cp= new CommentsPage(driver);
		cp.AddComments(comment);
		baseclass.passedStep("******Comments Added for Tag created *******");
		
		baseclass.stepInfo("*****Add codingform with Comments and Tag*****");
    	cf = new CodingForm(driver);
		cf.AddCodingformwithcommentsandtag(codingfromTagComm, comment, tagname);
		baseclass.passedStep("******Coding form added successfully*******");
		cf.EditCodingform(codingfromTagComm);
		baseclass.passedStep("******Coding form edited successfully*******");
	}
	
	   @Test(groups={"smoke","regression"},priority=2)
	   public void ValidateCFinDocViewFromSearch() throws InterruptedException {
		   baseclass.stepInfo("Test case Id: RPMXCON-50967 - Verify when user navigates from Basic Search/Saved search/Doc List and coding form is assigned to security group, custom coding form is editableh");
		   baseclass.stepInfo("*****Validating Coding form in DocView from search*****");
		   String serachString = Input.searchString2;
		    
		   baseclass.stepInfo("*****Search for any content on basic search screen*****");
		    //Search for any content on basic search screen
			sessionSearch =new SessionSearch(driver);
			//System.out.println(serachString);
	    	sessionSearch.basicContentSearch(serachString);
	    	System.out.println(serachString);
	    	pureHit = Integer.parseInt(sessionSearch.getPureHitsCount().getText());
	    	
	    	sessionSearch.ViewInDocView();
	    	cf.ViewCFinDocViewThrSearch(codingfrom);
	    	baseclass.passedStep("*****Validation Successfull*****");
	    	   
	   }
	   

	   @Test(groups={"smoke","regression"},priority=3)
	   public void ValidateCFinDocViewFromAssignment() throws InterruptedException {
		   baseclass.stepInfo("Test case Id: RPMXCON-51004 - Verify coding form for a document in an assignment after applying coding stamp when different coding form is applied to assignment and security group and document is viewed from basic search");
		   baseclass.stepInfo("*****Validating Coding form in DocView from Assignment*****");
		    
		   baseclass.stepInfo("*****Creating Assignment*****");
		    AssignmentsPage ass = new AssignmentsPage(driver);
		    ass.createAssignment(AssgnName, codingfrom);
		  
		    String serachString = Input.searchString2;
		  
		   // Search for any content on basic search screen
			sessionSearch =new SessionSearch(driver);
			System.out.println(serachString);
	    	sessionSearch.basicContentSearch(serachString);
	    	System.out.println(serachString);
	    	pureHit = Integer.parseInt(sessionSearch.getPureHitsCount().getText()); 
	    	
		    baseclass.stepInfo("*****Assign Docs to existing Assignment*****");
	    	sessionSearch.bulkAssign();
	    	ass.assignDocstoExisting(AssgnName);
	    	sessionSearch.ViewInDocView();
	    	cf.ViewCFinDocViewThrAssignment(codingfrom);
	    	baseclass.passedStep("*****Validation Successfull*****");
	   
	   }
	  
	    @Test(groups={"smoke","regression"},priority=4)
	    public void CopyCodingForm() throws InterruptedException {
	    	baseclass.stepInfo("Test case Id: RPMXCON-54001 - CopyCodingForm");
	    	baseclass.stepInfo("*****Copy codingform*****");
			cf = new CodingForm(driver);
		    cf.CopyCodingform(codingfrom);
		    baseclass.passedStep("*****codingform copied Successfully*****");
		}


	    @Test(groups={"smoke","regression"},priority=10)
	    public void DeleteCodingForm() {
	    	baseclass.stepInfo("Test case Id: RPMXCON-53993 - DeleteCodingForm");
	    	baseclass.stepInfo("*****Delete codingform*****");
			cf = new CodingForm(driver);
		    cf.DeleteCodingform(codingfrom);
		    baseclass.passedStep("*****deleted codingform Successfully*****");
		}

	    @Test(groups={"smoke","regression"},priority=5)
        public void AddCodingformwithMetadata() {
		
	    baseclass.stepInfo("Test case Id: RPMXCON-53954 - AddCodingformwithMetadata");
	    baseclass.stepInfo("*****Add codingform with Metadata*****");
	 	cf = new CodingForm(driver);
		cf.AddCodingformwithMetadata(codingfromMeta);
		baseclass.passedStep("*****Added codingform with metadata Successfully*****");
	}
     
 	@Test(groups={"smoke","regression"},priority=6)
    public void AddnewCodingFormwithtag() throws InterruptedException {
		
 		baseclass.stepInfo("Test case Id: RPMXCON-53952 - AddnewCodingFormwithtag");
 		baseclass.stepInfo("*****Add codingform with Tag*****");
		TagsAndFoldersPage p = new TagsAndFoldersPage(driver);
		p.CreateTag(tagnameprev,"Default Security Group");
		cf = new CodingForm(driver);
		cf.AddCodingformwithTag(codingfromTag, tagnameprev);
		baseclass.passedStep("*****Added codingform with Tag Successfully*****");
	}
 	
 	 //@Test(groups={"smoke","regression"},priority=7)
	    public void AddCodingformwithSpecialObjects() throws InterruptedException {
	    	baseclass.stepInfo("Test case Id: RPMXCON-53986 - AddCodingformwithSpecialObjects");
	    	baseclass.stepInfo("*****Create coding form with SpecialObjects*****");
			cf = new CodingForm(driver);
		    cf.AddCodingformwithSpecialObjects(codingfrom);
		    baseclass.passedStep("*****codingform Created Successfully with SpecialObjects*****");
		}
 	 
    	//@Test(dataProvider="validations",groups={"regression"},priority=8)
        public void PreviewValidations(String S1,String S2,String S3) throws InterruptedException {
		
        baseclass.stepInfo("*****Preview validation*****");
	 	cf = new CodingForm(driver);
		cf.PreviewValidations(codingfrom,S1,S2,S3);
		baseclass.passedStep("*****Preview validation Successfully*****");
	}
	
    @DataProvider(name="validations")
     public static Object[][] CFscenarios()
     {
    
    	 return new Object[][] {{"tag", "check item","Make It Optional"} , 
    		 {"tag", "check item","Make It Required"} ,
    		 {"tag", "check item","Make It Hidden"} ,
    		 {"tag", "radio item","Make It Display But Not Selectable"} ,
    		 {"comment", "radio item" ,"Make It Required"},
    		 {"comment", "radio item" ,"Make It Display But Not Selectable"},
    		 {"metadata", "radio item" ,"Make It Optional"},
    	     {"metadata", "radio item" ,"Make It Display But Not Selectable"},
    	 }; 
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
