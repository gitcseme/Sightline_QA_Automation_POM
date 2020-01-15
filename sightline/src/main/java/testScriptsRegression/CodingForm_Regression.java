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
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
	
		//Open browser
		driver = new Driver();
		//Login as a RMU
    	lp=new LoginPage(driver);
    	lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);   		
    	cf = new CodingForm(driver);
		cf.createCodingform(codingfrom);
		cf.CodingformToSecurityGroup(codingfrom);
	}
	
	
	@Test(groups={"smoke","regression"},priority =1)
    public void EditCodingForm() {
		System.out.println("******Edit Coding Form********");
		
		TagsAndFoldersPage tp = new TagsAndFoldersPage(driver);
		tp.CreateTag(tagname,"Default Security Group");
		
		CommentsPage cp= new CommentsPage(driver);
		cp.AddComments(comment);
		
    	cf = new CodingForm(driver);
		cf.AddCodingformwithcommentsandtag(codingfromTagComm, comment, tagname);
		cf.EditCodingform(codingfromTagComm);
	}
	
	   @Test(groups={"smoke","regression"},priority=2)
	   public void ValidateCFinDocViewFromSearch() throws InterruptedException {
		   
		   String serachString = Input.searchString2;
		  
		  //Search for any content on basic search screen
			sessionSearch =new SessionSearch(driver);
			System.out.println(serachString);
	    	sessionSearch.basicContentSearch(serachString);
	    	System.out.println(serachString);
	    	pureHit = Integer.parseInt(sessionSearch.getPureHitsCount().getText());
	    	
	    	sessionSearch.ViewInDocView();
	    	cf.ViewCFinDocViewThrSearch(codingfrom);
	   
	   }
	   

	  @Test(groups={"smoke","regression"},priority=3)
	   public void ValidateCFinDocViewFromAssignment() throws InterruptedException {
		   
		   
		    AssignmentsPage ass = new AssignmentsPage(driver);
		    ass.createAssignment(AssgnName, codingfrom);
		  /*  
		    String serachString = Input.searchString2;
		  
		  //Search for any content on basic search screen
			sessionSearch =new SessionSearch(driver);
			System.out.println(serachString);
	    	sessionSearch.basicContentSearch(serachString);
	    	System.out.println(serachString);
	    	pureHit = Integer.parseInt(sessionSearch.getPureHitsCount().getText());*/
	    	
	    	sessionSearch.bulkAssign();
	    	ass.assignDocstoExisting(AssgnName);
	    	sessionSearch.ViewInDocView();
	    	cf.ViewCFinDocViewThrAssignment(codingfrom);
	   
	   }
	  
	  @Test(groups={"smoke","regression"},priority =4)
	    public void CopyCodingForm() {
			
			cf = new CodingForm(driver);
		    cf.CopyCodingform(codingfrom);
		}


	 @Test(groups={"smoke","regression"},priority =10)
	    public void DeleteCodingForm() {
			
			cf = new CodingForm(driver);
		    cf.DeleteCodingform(codingfrom);
		}

     @Test(groups={"smoke","regression"},priority =5)
        public void AddCodingformwithMetadata() {
		
	 	cf = new CodingForm(driver);
		cf.AddCodingformwithMetadata(codingfromMeta);
	}
     
 	@Test(groups={"smoke","regression"},priority=6)
    public void AddnewCodingFormwithtag() {
		
		TagsAndFoldersPage p = new TagsAndFoldersPage(driver);
		p.CreateTag(tagnameprev,"Default Security Group");
		cf = new CodingForm(driver);
		cf.AddCodingformwithTag(codingfromTag, tagnameprev);
	}

    @Test(dataProvider="validations",groups={"regression"},priority =7)
        public void PreviewValidations(String S1,String S2,String S3) throws InterruptedException {
		
	 	cf = new CodingForm(driver);
		cf.PreviewValidations(codingfrom,S1,S2,S3);
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
