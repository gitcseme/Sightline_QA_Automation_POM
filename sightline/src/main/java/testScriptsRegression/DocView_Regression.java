package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
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


 public class DocView_Regression {
	Driver driver;
	LoginPage lp;
	DocViewPage docView;
	TagsAndFoldersPage page;
	AssignmentsPage agnmt;
	HomePage hm;
	BaseClass bc;//changes here
	DocListPage dp;
	SessionSearch search;

	
	String Remark= "Re"+Utility.dynamicNameAppender();
	String newTag = "newtag"+Utility.dynamicNameAppender();
	String codingfrom = "CF"+Utility.dynamicNameAppender();
	String assignmentName= "assi"+Utility.dynamicNameAppender();
	String redactiontag= "RTag"+Utility.dynamicNameAppender();
	String annotationname = "annotationname"+Utility.dynamicNameAppender();
	String folderName = "Fol"+Utility.dynamicNameAppender();
	String folderName1 = "Fol"+Utility.dynamicNameAppender();
	String folderName2 = "Fol"+Utility.dynamicNameAppender();
	
	
	//For reviewer assign docs,so create assignment with coding form(with tags and comments) and distribute 
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws InterruptedException, ParseException, IOException {
		
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		
		
		//Open browser
		driver = new Driver();
		//Login as PA
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
    	//add tag
		page = new TagsAndFoldersPage(driver);
    	page.CreateTag(newTag,"Default Security Group");
    	page.CreateFolder(folderName,"Default Security Group");
    	
    	//add comment field
    	CommentsPage comments = new CommentsPage(driver);
    	comments.AddComments("Comment"+Utility.dynamicNameAppender());
		
    	//Create coding for assignment
		CodingForm cf = new CodingForm(driver);
		cf.createCodingform(codingfrom); 
		
		//Create assignment with newly created coding form
		agnmt = new AssignmentsPage(driver);
		agnmt.createAssignment(assignmentName,codingfrom);
		
		AnnotationLayer alayer = new AnnotationLayer(driver);
    	alayer.AddAnnotation(annotationname);
    	System.out.println("Annotation Successful");
		
	
		//Search docs and assign to newly created assignment
		search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString2);
		search.bulkAssign();
		agnmt.assignDocstoExisting(assignmentName);
		
		//Edit assignment and add reviewers 
		agnmt.editAssignment(assignmentName);
		agnmt.addReviewerAndDistributeDocs(assignmentName,Input.pureHitSeachString2);
		
		lp.logout();
		
		lp.loginToSightLine(Input.rev1userName, Input.rev1password); 
		
		hm = new HomePage(driver);
		Boolean found= false;
        for (WebElement element : hm.getAssignmentsList().FindWebElements()) {
			if(element.getText().equalsIgnoreCase(assignmentName)){
				found = true;
				System.out.println(assignmentName +"is assigned to reviewer successfully");
				element.click();
				break;
			}
		}	
        Assert.assertTrue(found);
    
        docView=new DocViewPage(driver);     
        agnmt = new AssignmentsPage(driver);
		
        
	}
	
      	@Test(groups={"regression"},priority=1)
		public void  VerifyPersistentHit() throws InterruptedException {
 		
   		 System.out.println("******Execution started for Persistent Hits********");
   		 bc.stepInfo("Test CaseId : RPMXCON-51010, To verify the details by clicking on Persistent search icon.");
		
			search.basicContentSearch(Input.searchString1);
			search.ViewInDocView();
			docView.VerifyPersistentHit(Input.searchString1);
	
		}
 
	@Test(groups={"regression"},priority=2)
	public void VerifyTextTab() throws InterruptedException {
		bc.stepInfo("******Execution started forverify text tab********");
		bc.stepInfo("Test CaseId : RPMXCON-50911,To verify when user can view the document in Text tab");
		bc.stepInfo("Test CaseId : RPMXCON-50913,To verify when user enters the document number in Text view");
		
		search.basicContentSearch(Input.searchString1);
		bc.passedStep("*********Basic content search is done********");
		search.ViewInDocView();
		bc.passedStep("*********Document seen in doc view********");
		docView.ViewTextTab();
	}
	
	@Test(groups={"regression"},priority=4)
	public void NonAudioAnnotation() throws InterruptedException
	{
		System.out.println("******Execution started for Annotation********");
		docView.NonAudioAnnotation();
	}
	

	
	

	@Test(groups={"regression"},priority=11)
		public void NonAudioRemarkAddEditDeletebyReviewer()
		{
		  System.out.println("******Execution started for NonAudioRemarkAddEditDeletebyReviewer********");
			docView.addRemarkNonAudioDoc(Remark);
		}
			  
			@Test(groups={"regression"},priority=18)
			public void VerifyTabswhenAllprefDisabled() throws InterruptedException
					{
				 System.out.println("******Execution started for VerifyTabswhenAllprefDisabled********");
						agnmt = new AssignmentsPage(driver);
						agnmt.editAssignment(assignmentName);
						agnmt.AssgnToggleButtons();
						docView.VerifyTabswhenAllprefDisabled();
				    }
	
	  	@Test(groups={"regression"},priority=19)
	   public void VerifyAnalyticsforPA() throws InterruptedException
	   {
	  		 System.out.println("******Execution started for VerifyAnalyticsforPA********");
		lp.logout();
		lp.loginToSightLine(Input.pa1userName,Input.pa1password);
	    SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString2);
		search.getFamilyAddButton().waitAndClick(20);
		search.getBulkActionButton().waitAndClick(10);
		search.getDocViewAction().waitAndClick(10);
		docView.AnalyticsThreaded_Actions();
       }
	
	  	@Test(groups={"regression"},priority=20)
		public void VerifyMiniDoclistConifgSortOrder() throws InterruptedException
		{
	  		 System.out.println("******Execution started for VerifyMiniDoclistConifgSortOrder********");
		   System.out.println("******Execution started for VerifyMiniDoclistConifgSortOrder********");
		   docView.MiniDoclistConifgSortOrder();
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
