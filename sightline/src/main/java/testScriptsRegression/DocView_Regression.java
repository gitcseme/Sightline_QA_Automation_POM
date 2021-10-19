package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
		bc=new BaseClass(driver);
		dp = new DocListPage(driver);
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
			bc.passedStep("*********Basic content search is done********");
			search.ViewInDocView();
			bc.passedStep("*********Document seen in doc view********");
			docView.VerifyPersistentHit(Input.searchString1);
			bc.passedStep("*********performed persistentHit********");
	
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
		bc.passedStep("********the document number in Text view is performed********");
	}
	
	@Test(groups={"regression"},priority=4)
	public void NonAudioAnnotation() throws InterruptedException
	{
		System.out.println("******Execution started for Annotation********");
		bc.stepInfo("Test CaseId : RPMXCON-48712,Verify RMU/Reviewer can annotate current (this) page in document in security group");
	
 
	 bc.selectproject();
	SessionSearch search = new SessionSearch(driver);
	search.basicContentSearch(Input.searchString2);
	bc.passedStep("*********Basic content search is done********");
	search.getPureHitAddButton().waitAndClick(20);
	bc.passedStep("*********Documents added to action panel********");
	search.getBulkActionButton().waitAndClick(10);
	bc.passedStep("*********Clicked Action button********");
	search.getDocViewAction().ElementToBeClickableExplicitWait(search.getDocViewAction(), 60000);
	search.getDocViewAction().waitAndClick(10);
	bc.passedStep("*********Clicked Doc view********");
		docView.NonAudioAnnotation();
		bc.passedStep("*********Annote this page is completed for Nonaudio file********");
	}
	
	
	
	
	
		
	 
	 
	
	
	@Test(groups={"regression"},priority=11)
		public void NonAudioRemarkAddEditDeletebyReviewer()
		{
		  System.out.println("******Execution started for NonAudioRemarkAddEditDeletebyReviewer********");
		  bc.stepInfo("Test CaseId : RPMXCON-51675, Verify the remark functionality in the docview other than audio documents");
		  SessionSearch search = new SessionSearch(driver);
	 		search.basicContentSearch(Input.searchString2);
	 		bc.passedStep("*********Basic content search is done********");
	 		search.getPureHitAddButton().Click();
	 		bc.passedStep("*********Documents added to action panel********");
	 		search.getBulkActionButton().ElementToBeClickableExplicitWait(search.getBulkActionButton(), 10000);
	 		search.getBulkActionButton().waitAndClick(10);
	 		bc.passedStep("*********Clicked Action button********");
	 		search.getDocViewAction().ElementToBeClickableExplicitWait(search.getDocViewAction(), 10000);
	 		search.getDocViewAction().waitAndClick(10);
	 		bc.passedStep("*********Clicked Doc view********");
		  docView.addRemarkNonAudioDoc(Remark);
		  bc.passedStep("*********Remark added to non audio doc is completed********");
		}

	  
		
		
	    
	  
	   

	 	
		 @Test(groups={"regression"},priority=15)
		public void getPersistentHit() throws InterruptedException {
			  System.out.println("******Execution started for getPersistentHit********");
			  bc.stepInfo("Test CaseId : RPMXCON-51010, To verify the details by clicking on Persistent search icon.");
	
			 agnmt.SelectAssignmentToViewinDocview(assignmentName);
			 bc.passedStep("*********Selected Assignment and view in doc view page********");
		     docView.VerifyPersistentHit(Input.searchString1);
		     bc.passedStep("*********persistent Hit is verified********");
	    }

			//@Test(groups={"regression"},priority=16)
			public void VerifyTooltipsforIcons() throws InterruptedException {
				bc.stepInfo("Test CaseId : ");
//				AssignmentsPage agnmt = new AssignmentsPage(driver);
				agnmt.SelectAssignmentToViewinDocview(assignmentName);
				bc.passedStep("*********Selected Assignment and view in doc view page********");
//				docView=new DocViewPage(driver); 
				docView.VerifyTooltipsforallIconsEnglish();
				bc.passedStep("*********All tool tips in english in doc view page are verified********");
				System.out.println("Verify tooltip for German language................");
				  
				lp.getSignoutMenu().waitAndClick(5);
				bc.passedStep("*********logged out succesfully********");
				lp.getEditProfile().waitAndClick(10);
				bc.passedStep("*********profile edited********");
		    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			lp.getSelectLanguage().Visible()  ;}}), Input.wait30); 
		    	lp.getSelectLanguage().selectFromDropdown().selectByVisibleText("German - Germany");
		    	bc.passedStep("*********language is selected********");
		    	lp.getEditprofilesave().waitAndClick(10);
		    	bc.passedStep("*********edited profile is saved********");
		    	
		    	hm = new HomePage(driver);
			    for (WebElement element : hm.getAssignmentsList().FindWebElements()) {
					if(element.getText().equalsIgnoreCase(assignmentName)){
						System.out.println(assignmentName +"is assigned to reviewer successfully");
						element.click();
						break;
					}
				}
			    bc.passedStep("*********(assignmentName is assigned to reviewer successfully********");
		      docView.VerifyTooltipsforallIconsGerman(); 
		      bc.passedStep("*********All tool tips in german in doc view page are verified********");
		      lp.getSignoutMenu().waitAndClick(5);
		      bc.passedStep("*********logged out succesfully********");
				lp.getEditProfile().waitAndClick(10);
				bc.passedStep("*********profile edited********");
		  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		  			lp.getSelectLanguage().Visible()  ;}}), Input.wait30); 
		  	lp.getSelectLanguage().selectFromDropdown().selectByVisibleText("English - United States");
		  	bc.passedStep("*********language is selected********");
		  	lp.getEditprofilesave().waitAndClick(10);
		  	bc.passedStep("*********edited profile is saved********");
		  	driver.Navigate().refresh();
				
			}

			
			  
			@Test(groups={"regression"},priority=18)
			public void VerifyTabswhenAllprefDisabled() throws InterruptedException
					{
				 System.out.println("******Execution started for VerifyTabswhenAllprefDisabled********");
				 bc.stepInfo("Test CaseId : RPMXCON-50839, To verify that RMU can not view the 'Assignment Progress' on Doc View, if preferences is set as 'Disabled' from the assignment.");
						agnmt.editAssignment(assignmentName);
						bc.passedStep("*********Assignment Edited********");
						agnmt.AssgnToggleButtons();
						bc.passedStep("*********Toggle buttons assigned********");
						agnmt.editAssignment(assignmentName);
						bc.passedStep("*********Assignment Edited********");
						agnmt.getAssignment_BackToManageButton().waitAndClick(10);
						bc.passedStep("*********Moved to Manage Assignments********");
						agnmt.SelectAssignmentToViewinDocview(assignmentName);
						bc.passedStep("*********Selected Assignment and view in doc view page********");
						
						docView.VerifyTabswhenAllprefDisabled();
						bc.passedStep("*********All preferable tabs are disabled********");
				    }
	
	  	@Test(groups={"regression"},priority=19)
	   public void VerifyAnalyticsforPA() throws InterruptedException
	   {
	  		 System.out.println("******Execution started for VerifyAnalyticsforPA********");
	  		bc.stepInfo("Test case Id: RPMXCON-XXXXX-----");
		lp.logout();
		bc.passedStep("*********logged out succesfully********");
		lp.loginToSightLine(Input.pa1userName,Input.pa1password);
		bc.passedStep("*********logged in as a PA user********");
	    SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString2);
		bc.passedStep("*********Basic content search is done********");
		search.getFamilyAddButton().waitAndClick(20);
		bc.passedStep("*********Family Documents added to action panel********");
		search.getBulkActionButton().waitAndClick(10);
		bc.passedStep("*********Clicked Action button********");
		search.getDocViewAction().ElementToBeClickableExplicitWait(search.getDocViewAction(), 10000);
		search.getDocViewAction().waitAndClick(10);
		bc.passedStep("*********Clicked Doc view********");
		docView.AnalyticsThreaded_Actions();
		bc.passedStep("*********Threaded Analytics actions is performed********");
       }
	
	  	@Test(groups={"regression"},priority=20)
		public void VerifyMiniDoclistConifgSortOrder() throws InterruptedException
		{
	  		 System.out.println("******Execution started for VerifyMiniDoclistConifgSortOrder********");
	  		bc.stepInfo("Test case Id: RPMXCON-XXXXX-----");
	  		SessionSearch search = new SessionSearch(driver);
	 		search.basicContentSearch(Input.searchString2);
	 		bc.passedStep("*********Basic content search is done********");
	 		search.getPureHitAddButton().Click();
	 		bc.passedStep("*********Documents added to action panel********");
	 		search.getBulkActionButton().waitAndClick(10);
	 		bc.passedStep("*********Clicked Action button********");
	 		search.getDocViewAction().waitAndClick(10);
	 		bc.passedStep("*********Clicked Doc view********");
		   docView.MiniDoclistConifgSortOrder();
		   bc.passedStep("*********Mini Doc list config sorted in an order********");
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
		
//	  @AfterClass(alwaysRun = true)
		public void close(){
			try{ 
				lp.logout();
			     //lp.quitBrowser();	
				}finally {
					lp.quitBrowser();
				}
		}
	}
