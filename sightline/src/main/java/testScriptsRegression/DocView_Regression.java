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
	BaseClass bc;
	DocListPage dp;
	
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
		
		Input in = new Input(); in.loadEnvConfig();
		//Open browser
		driver = new Driver();
		//Login as PA
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
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
		SessionSearch search = new SessionSearch(driver);
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
			SessionSearch search = new SessionSearch(driver);
			search.basicContentSearch(Input.searchString1);
			bc.passedStep("*********Basic content search is done********");
			search.ViewInDocView();
			bc.passedStep("*********Document seen in doc view********");
			docView.VerifyPersistentHit(Input.searchString1);
			bc.passedStep("*********performed persistentHit********");
	
		}
 	
 	@Test(groups={"regression"},priority=2)
	public void VerifyFoldertab() throws InterruptedException {
 		System.out.println("******Execution started for Verify folder tab********");
 		bc.stepInfo("Test CaseId : RPMXCON-50819, To verify RMU can assign documents to selected folder from Analytics-Family Members.");
 		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString1);
		bc.passedStep("*********Basic content search is done********");
		search.ViewInDocView();
		bc.passedStep("*********Document seen in doc view********");
		docView.VerifyFolderTab(folderName,2);
		bc.passedStep("*********Folder Action performed********");
    
	}
	@Test(groups={"regression"},priority=3)
	public void VerifyTextTab() throws InterruptedException {
		System.out.println("******Execution started forverify text tab********");
		bc.stepInfo("Test CaseId : RPMXCON-50911,To verify when user can view the document in Text tab");
		bc.stepInfo("Test CaseId : RPMXCON-50913,To verify when user enters the document number in Text view");
		SessionSearch search = new SessionSearch(driver);
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
	
	
	
	
	@Test(groups={"regression"},priority=6)
	public void VerifyAnalyticsEmailInclusive() throws InterruptedException
	{
		System.out.println("******Execution started for VerifyAnalyticsEmailInclusive********");
		bc.stepInfo("Test CaseId : RPMXCON-50858,To verify that user can view the  analytics panel on Doc View, if preferences is set as 'Enabled' from the assignent.");
		 BaseClass bc = new BaseClass(driver);
		 bc.selectproject();
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString2);
		bc.passedStep("*********Basic content search is done********");
		//  search.Removedocsfromresults();
		search.getThreadedAddButton().waitAndClick(20);
		bc.passedStep("*********Threaded Documents added to action panel********");
		search.getBulkActionButton().ElementToBeClickableExplicitWait(search.getBulkActionButton(), 10000);
		search.getBulkActionButton().waitAndClick(10);
		bc.passedStep("*********Clicked Action button********");
		search.getDocViewAction().ElementToBeClickableExplicitWait(search.getDocViewAction(), 10000);
		search.getDocViewAction().waitAndClick(10);
		bc.passedStep("*********Clicked Doc view********");
		docView.getDocView_AnalyticsEmail();
		bc.passedStep("*********Doc View Email Analytics is completed********");
		
	}
	
	@Test(groups={"regression"},priority=7)
		public void VerifyAnalyticsThreaded() throws InterruptedException
		{
		System.out.println("******Execution started for VerifyAnalyticsThreaded********");
		bc.stepInfo("Test case Id: RPMXCON-XXXXX-----");
		 BaseClass bc = new BaseClass(driver);
		 bc.selectproject();
			SessionSearch search = new SessionSearch(driver);
			search.basicContentSearch(Input.searchString1);
			bc.passedStep("*********Basic content search is done********");
			search.getPureHitAddButton().waitAndClick(20);
			bc.passedStep("*********Documents added to action panel********");
			search.getBulkActionButton().ElementToBeClickableExplicitWait(search.getBulkActionButton(), 10000);
			search.getBulkActionButton().waitAndClick(10);
			bc.passedStep("*********Clicked Action button********");
			search.getDocViewAction().ElementToBeClickableExplicitWait(search.getDocViewAction(), 10000);
			search.getDocViewAction().waitAndClick(10);
			bc.passedStep("*********Clicked Doc view********");
			docView.AnalyticsThreadedNoDocument();
			bc.passedStep("*********Doc View Threaded docs Analytics is completed********");
	    }
		
	   @Test(groups={"regression"},priority=8)
		public void VerifyAnalyticsChildWinodw() throws InterruptedException
		{
			System.out.println("******Execution started for VerifyAnalyticsChildWinodw********");
			bc.stepInfo("Test case Id: RPMXCON-XXXXX-----");
//			 driver.getWebDriver().navigate().refresh();
//			 driver.getWebDriver().switchTo().alert().accept();
			BaseClass bc = new BaseClass(driver);
		 
			 bc.selectproject();
			SessionSearch search = new SessionSearch(driver);
			search.basicContentSearch(Input.searchString2);
			bc.passedStep("*********Basic content search is done********");
			search.getFamilyAddButton().waitAndClick(20);
			bc.passedStep("*********Family Documents added to action panel********");
			search.getBulkActionButton().ElementToBeClickableExplicitWait(search.getBulkActionButton(), 10000);
			search.getBulkActionButton().waitAndClick(10);
			bc.passedStep("*********Clicked Action button********");
			search.getDocViewAction().ElementToBeClickableExplicitWait(search.getDocViewAction(), 60000);
			search.getDocViewAction().waitAndClick(10);
			bc.passedStep("*********Clicked Doc view********");
			docView.AnalyticsCodeSameAs();
			bc.passedStep("********* docs Analytics for code same as is completed********");
	    }
	 
	  @Test(groups={"regression"},priority=9)
		public void VerifyNearDupesCompwinodw() throws InterruptedException
		{
			System.out.println("******Execution started for VerifyNearDupesCompwinodw********");
			bc.stepInfo("Test CaseId : RPMXCON-50869, To verify user can select a set of documents in the Near Dupe panel in doc view and view them in doc list");
		  BaseClass bc = new BaseClass(driver);
			 bc.selectproject();
			SessionSearch search = new SessionSearch(driver);
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
	  @Test(groups={"regression"},priority=10)
		public void VerifyThreadedChildWinodw() throws InterruptedException
		{
		  System.out.println("******Execution started for VerifyThreadedChildWinodw********");
		  bc.stepInfo("Test case Id: RPMXCON-XXXXX-----");
		  BaseClass bc = new BaseClass(driver);
			 bc.selectproject();
	        SessionSearch search = new SessionSearch(driver);
			search.basicContentSearch(Input.searchString2);
			bc.passedStep("*********Basic content search is done********");
			search.getThreadedAddButton().waitAndClick(20);
			bc.passedStep("*********Threaded Documents added to action panel********");
			search.getBulkActionButton().ElementToBeClickableExplicitWait(search.getBulkActionButton(), 10000);
			search.getBulkActionButton().waitAndClick(10);
			bc.passedStep("*********Clicked Action button********");
			search.getDocViewAction().ElementToBeClickableExplicitWait(search.getDocViewAction(), 10000);
			search.getDocViewAction().waitAndClick(10);
			bc.passedStep("*********Clicked Doc view********");
			docView.AnalyticsActions();
			bc.passedStep("*********Analytics actions performed********");
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

	  
		@Test(groups={"regression"},priority=12)
		public void VerifyAnalytics_FamilyActions() throws InterruptedException
		{
			
			  System.out.println("******Execution started for VerifyAnalytics_FamilyActions********");
			  bc.stepInfo("Test case Id: RPMXCON-XXXXX-----");
			
			lp.logout();
			bc.passedStep("*********logged out successfuly********");
			lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			bc.passedStep("*********logged in as rmu user successfully********");
			page = new TagsAndFoldersPage(driver);
			System.out.println("folderName1"+folderName1);
			page.CreateFolder(folderName1,"Default Security Group");
			bc.passedStep("*********new folder created successfuly********");
			Actions actions = new Actions(driver.getWebDriver());
		    SessionSearch search = new SessionSearch(driver);
			search.basicContentSearch(Input.searchString2);
			bc.passedStep("*********Basic content search is done********");
			search.getFamilyAddButton().waitAndClick(20);
			bc.passedStep("*********Family Documents added to action panel********");
			search.getBulkActionButton().ElementToBeClickableExplicitWait(search.getBulkActionButton(), 10000);
			search.getBulkActionButton().waitAndClick(10);
			bc.passedStep("*********Clicked Action button********");
			search.getDocViewAction().VisibilityOfElementExplicitWait(search.getDocViewAction(), 10000);
			actions.moveToElement(search.getDocViewAction().getWebElement()).click().build().perform();
			bc.passedStep("*********Clicked Doc view********");
			docView.Analytics_FamilyActions(folderName1);
			bc.passedStep("*********Analytics on Family actions completed successfully********");
//			final DocListPage dp = new DocListPage(driver);
		    dp.getDocList_info().WaitUntilPresent();
		    
		    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    		   !dp.getDocList_info().getText().isEmpty()  ;}}),Input.wait60);
		    Assert.assertEquals(dp.getDocList_info().getText().toString().replaceAll(",", ""),"Showing 1 to 1 of 1 entries");
		    System.out.println("Expected docs '1' is shown in doclist");
		    bc.passedStep("*********doclist info is shown********");
			 final TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
		       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		       		tf.getTag_ToggleDocCount().Visible()  ;}}),Input.wait60); 
		  	   tf.getFoldersTab().waitAndClick(10);
		       tf.getFolder_ToggleDocCount().waitAndClick(10);
		       tf.getFolderandCount(folderName1, 1).WaitUntilPresent();
		       Assert.assertTrue(tf.getFolderandCount(folderName1, 1).Displayed());
		       System.out.println(folderName1+" could be seen under tags and folder page");
		       bc.passedStep("*********no of docs under the folder is seen in tags & Folder page********");
		}
		
		 @Test(groups={"regression"},priority=13)
			public void VerifyMiniDoclistFolderAction() throws InterruptedException
			{
			  System.out.println("******Execution started for VerifyMiniDoclistFolderAction********");
			  bc.stepInfo("Test CaseId : RPMXCON-50822, To verify Family Member documents should be displayed as per the clicked document from mini doc list panel and from document navigation ");
			 	 BaseClass bc = new BaseClass(driver);
				 bc.selectproject();
				SessionSearch search = new SessionSearch(driver);
				search.basicContentSearch(Input.searchString2);
				bc.passedStep("*********Basic content search is done********");
				search.getPureHitAddButton().waitAndClick(20);
				bc.passedStep("********* Documents added to action panel********");
				search.getBulkActionButton().ElementToBeClickableExplicitWait(search.getBulkActionButton(), 10000);
				search.getBulkActionButton().waitAndClick(10);
				bc.passedStep("*********Clicked Action button********");
				search.getDocViewAction().ElementToBeClickableExplicitWait(search.getDocViewAction(),10000);
				search.getDocViewAction().waitAndClick(10);
				bc.passedStep("*********Clicked Doc view********");
				docView.MiniDoclistFolderAction(folderName);
				bc.passedStep("*********Mini Doc List Folder Action completed********");
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

			  @Test(groups={"regression"},priority=17)
				public void VerifyAnalyticsthrAssignment() throws InterruptedException
				{
				  System.out.println("******Execution started for VerifyAnalyticsthrAssignment********");
				  bc.stepInfo("Test case Id: RPMXCON-XXXXX-----");
				  lp.logout();
				  bc.passedStep("*********logged out succesfully********");
					lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
					bc.passedStep("*********logged in as a rmu user********");
				  BaseClass bc = new BaseClass(driver);
					 bc.selectproject();
					SessionSearch search = new SessionSearch(driver);
					search.basicContentSearch(Input.searchString2);
					bc.passedStep("*********Basic content search is done********");
					// search.Removedocsfromresults();
					search.getThreadedAddButton().waitAndClick(20);
					bc.passedStep("*********Threaded Documents added to action panel********");
				
					
					search.bulkAssign();
					bc.passedStep("********* Bulk is performed********");
					driver.getWebDriver().get(Input.url+"Search/Searches");
					search.getBulkActionButton().ElementToBeClickableExplicitWait(search.getBulkActionButton(), 10000);
					search.getBulkActionButton().waitAndClick(10);
					bc.passedStep("*********Clicked Action button********");
					 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							 search.getBulkAssignAction().Visible()  ;}}), Input.wait60); 
					
				     
					 search.getBulkAssignAction().waitAndClick(10);
					 bc.passedStep("*********Clicked Bulk Assign********");
					agnmt.assignDocstoExisting(assignmentName);
					bc.passedStep("*********Assignned docs to existing assignment********");
					agnmt.SelectAssignmentToViewinDocview(assignmentName);
					bc.passedStep("*********Selected Assignment and view in doc view page********");
					docView.AnalyticsActions();
					bc.passedStep("*********Analytics action is performed********");
					
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
//		
	  	 @Test(groups={"regression"},priority=21)
	 	public void VerifyAnalyticsConceptual() throws InterruptedException
	 	{
	  		 System.out.println("******Execution started for VerifyAnalyticsConceptual********");
	  		bc.stepInfo("Test CaseId : RPMXCON-50828, To verify user will be able to see all the conceptually similar documents in the analytics panel of the main selected document in the Doc view");
	  		 BaseClass bc = new BaseClass(driver);
			 bc.selectproject();
	 		SessionSearch search = new SessionSearch(driver);
	 		search.basicContentSearch(Input.searchString2);
	 		bc.passedStep("*********Basic content search is done********");
	 	    search.getConceptuallyPlayButton().waitAndClick(20);
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
