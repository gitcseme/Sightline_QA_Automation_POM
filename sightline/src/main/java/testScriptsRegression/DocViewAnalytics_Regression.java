package testScriptsRegression;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.Callable;
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


 public class DocViewAnalytics_Regression {
	Driver driver;
	LoginPage lp;
	DocViewPage docView;
	TagsAndFoldersPage page;
	AssignmentsPage agnmt;
	HomePage hm;
	BaseClass bc;
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
          
        agnmt = new AssignmentsPage(driver);
        docView=new DocViewPage(driver); 
	}
	
   	
	  @Test(groups={"regression"},priority=9)
			public void VerifyNearDupesCompwinodw() throws InterruptedException
			{
				System.out.println("******Execution started for VerifyNearDupesCompwinodw********");
				bc.stepInfo("Test CaseId : RPMXCON-50869, To verify user can select a set of documents in the Near Dupe panel in doc view and view them in doc list");
			  BaseClass bc = new BaseClass(driver);
				 bc.selectproject();
				search.basicContentSearch(Input.searchString1);
				bc.passedStep("*********Basic content search is done********");
				search.Removedocsfromresults();
				bc.passedStep("*********removed docs from the result is done********");
				search.getNearDupesAddButton().waitAndClick(20);
				bc.passedStep("*********Near Dupes Documents added to action panel********");
				search.getBulkActionButton().ElementToBeClickableExplicitWait(search.getBulkActionButton(), 10000);
				search.getBulkActionButton().waitAndClick(10);
				bc.passedStep("*********Clicked Action button********");
				search.getDocViewAction().ElementToBeClickableExplicitWait(search.getDocViewAction(), 10000);
				search.getDocViewAction().waitAndClick(10);
				bc.passedStep("*********Clicked Doc view********");
				docView.NearDupesCompwinodw();
				bc.passedStep("*********Near Dupes Comp window is opened********");
			    dp.getDocList_info().WaitUntilPresent();
			    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			    		   !dp.getDocList_info().getText().isEmpty()  ;}}),Input.wait60);
			    Assert.assertEquals(dp.getDocList_info().getText().toString().replaceAll(",", ""),"Showing 1 to 1 of 1 entries");
			    System.out.println("Expected docs '1' is shown in doclist");
			    bc.passedStep("*********Doc list info is shown********");

		 }
	  
	  @Test(groups={"regression"},priority=14)
		public void VerifyTabswhenAllprefEnabled() throws InterruptedException
		{
	    	  System.out.println("******Execution started for VerifyTabswhenAllprefEnabled********");
	    	  bc.stepInfo("Test CaseId : RPMXCON-50858, To verify that user can view the analytics panel on Doc View, if preferences is set as 'Enabled' from the assignent.");
	    	
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
			docView.VerifyTabswhenAllprefEnabled();
			bc.passedStep("*********All preferable tabs are enabled********");
			
	    }
	  
	  @Test(groups={"regression"},priority=21)
	 	public void VerifyAnalyticsConceptual() throws InterruptedException
	 	{
	  		 System.out.println("******Execution started for VerifyAnalyticsConceptual********");
	  		bc.stepInfo("Test CaseId : RPMXCON-50828, To verify user will be able to see all the conceptually similar documents in the analytics panel of the main selected document in the Doc view");
	  		 BaseClass bc = new BaseClass(driver);
			 bc.selectproject();
	 		search.basicContentSearch(Input.searchString2);
	 		bc.passedStep("*********Basic content search is done********");
	 	    search.getConceptuallyPlayButton().waitAndClick(20);
	 	    Thread.sleep(1000);
	 	   bc.passedStep("*********Conceptually Play Button is clicked********");
	 	   search.getConceptAddButton().ElementToBeClickableExplicitWait(search.getConceptAddButton(), 5000);
	 	    search.getConceptAddButton().waitAndClick(60);
	 	   bc.passedStep("*********Conceptual Documents added to action panel********");
	 	    
	 		search.getBulkActionButton().waitAndClick(10);
	 		bc.passedStep("*********Clicked Action button********");
	 		search.getDocViewAction().ElementToBeClickableExplicitWait(search.getDocViewAction(), 10000);
	 		search.getDocViewAction().waitAndClick(10);
	 		bc.passedStep("*********Clicked Doc view********");
	 		docView.getDocView_AnalyticsEmail();	
	 		bc.passedStep("*********Email Analytics on Doc View page is performed********");
	 	}
	  
	  @Test(groups={"regression"},priority=13)
		public void VerifyMiniDoclistFolderAction() throws InterruptedException
		{
		  System.out.println("******Execution started for VerifyMiniDoclistFolderAction********");
		  bc.stepInfo("Test CaseId : RPMXCON-50822, To verify Family Member documents should be displayed as per the clicked document from mini doc list panel and from document navigation ");
		 	 BaseClass bc = new BaseClass(driver);
			 bc.selectproject();
			search.basicContentSearch(Input.searchString2);
			bc.passedStep("*********Basic content search is done********");
			search.getPureHitAddButton().waitAndClick(20);
			bc.passedStep("********* Documents added to action panel********");
			search.getBulkActionButton().ElementToBeClickableExplicitWait(search.getBulkActionButton(), 10000);
			search.getBulkActionButton().waitAndClick(10);
			bc.passedStep("*********Clicked Action button********");
			search.getDocViewAction().ElementToBeClickableExplicitWait(search.getDocViewAction(),10000);
			Thread.sleep(2000);
			search.getDocViewAction().waitAndClick(10);
			bc.passedStep("*********Clicked Doc view********");
			docView.MiniDoclistFolderAction(folderName);
			bc.passedStep("*********Mini Doc List Folder Action completed********");
	    }
	 
	  
		@Test(groups={"regression"},priority=2)
		public void VerifyFoldertab() throws InterruptedException {
	 		System.out.println("******Execution started for Verify folder tab********");
	 		bc.stepInfo("Test CaseId : RPMXCON-50819, To verify RMU can assign documents to selected folder from Analytics-Family Members.");
	 		
			search.basicContentSearch(Input.searchString1);
			bc.passedStep("*********Basic content search is done********");
			search.ViewInDocView();
			bc.passedStep("*********Document seen in doc view********");
			docView.VerifyFolderTab(folderName,2);
			bc.passedStep("*********Folder Action performed********");
	    
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
