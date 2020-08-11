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


 public class DocView_Regression {
	Driver driver;
	LoginPage lp;
	DocViewPage docView;
	TagsAndFoldersPage page;
	AssignmentsPage agnmt;
	HomePage hm;
	BaseClass bc;
	
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
			SessionSearch search = new SessionSearch(driver);
			search.basicContentSearch(Input.searchString1);
			search.ViewInDocView();
			docView.VerifyPersistentHit(Input.searchString1);
	
		}
 	
 	@Test(groups={"regression"},priority=2)
	public void VerifyFoldertab() throws InterruptedException {
 		System.out.println("******Execution started for Verify folder tab********");
		docView.VerifyFolderTab(folderName,2);
    
	}
	@Test(groups={"regression"},priority=3)
	public void VerifyTextTab() {
		System.out.println("******Execution started forverify text tab********");
		docView.ViewTextTab();
	}
	
	@Test(groups={"regression"},priority=4)
	public void NonAudioAnnotation() throws InterruptedException
	{
		System.out.println("******Execution started for Annotation********");
		docView.NonAudioAnnotation();
	}
	
	
	
	
	@Test(groups={"regression"},priority=6)
	public void VerifyAnalyticsEmailInclusive() throws InterruptedException
	{
		System.out.println("******Execution started for VerifyAnalyticsEmailInclusive********");
		 BaseClass bc = new BaseClass(driver);
		 bc.selectproject();
		SessionSearch search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString2);
		//  search.Removedocsfromresults();
		search.getThreadedAddButton().waitAndClick(20);
		search.getBulkActionButton().waitAndClick(10);
		search.getDocViewAction().waitAndClick(10);
		docView.getDocView_AnalyticsEmail();
		
	}
	
	@Test(groups={"regression"},priority=7)
		public void VerifyAnalyticsThreaded() throws InterruptedException
		{
		System.out.println("******Execution started for VerifyAnalyticsThreaded********");
		 BaseClass bc = new BaseClass(driver);
		 bc.selectproject();
			SessionSearch search = new SessionSearch(driver);
			search.basicContentSearch(Input.searchString1);
			search.getPureHitAddButton().waitAndClick(20);
			search.getBulkActionButton().waitAndClick(10);
			search.getDocViewAction().waitAndClick(10);
			docView.AnalyticsThreadedNoDocument();
	    }
		
	   @Test(groups={"regression"},priority=8)
		public void VerifyAnalyticsChildWinodw() throws InterruptedException
		{
			System.out.println("******Execution started for VerifyAnalyticsChildWinodw********");
			 driver.getWebDriver().navigate().refresh();
			BaseClass bc = new BaseClass(driver);
		 
			 bc.selectproject();
			SessionSearch search = new SessionSearch(driver);
			search.basicContentSearch(Input.searchString2);
			search.getFamilyAddButton().waitAndClick(20);
			search.getBulkActionButton().waitAndClick(10);
			search.getDocViewAction().waitAndClick(10);
			docView.AnalyticsCodeSameAs();
	    }
	 
	  @Test(groups={"regression"},priority=9)
		public void VerifyNearDupesCompwinodw() throws InterruptedException
		{
			System.out.println("******Execution started for VerifyNearDupesCompwinodw********");
		  BaseClass bc = new BaseClass(driver);
		  driver.getWebDriver().navigate().refresh();
			 bc.selectproject();
			SessionSearch search = new SessionSearch(driver);
			search.basicContentSearch(Input.searchString1);
			search.Removedocsfromresults();
			search.getNearDupesAddButton().waitAndClick(20);
			search.getBulkActionButton().waitAndClick(10);
			search.getDocViewAction().waitAndClick(10);
			docView.NearDupesCompwinodw();
	            final DocListPage dp = new DocListPage(driver);
		    dp.getDocList_info().WaitUntilPresent();
		    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    		   !dp.getDocList_info().getText().isEmpty()  ;}}),Input.wait60);
		    Assert.assertEquals(dp.getDocList_info().getText().toString().replaceAll(",", ""),"Showing 1 to 1 of 1 entries");
		    System.out.println("Expected docs '1' is shown in doclist");

	 }
	  @Test(groups={"regression"},priority=10)
		public void VerifyThreadedChildWinodw() throws InterruptedException
		{
		  System.out.println("******Execution started for VerifyThreadedChildWinodw********");
		  BaseClass bc = new BaseClass(driver);
		  driver.getWebDriver().navigate().refresh();
			 bc.selectproject();
	        SessionSearch search = new SessionSearch(driver);
			search.basicContentSearch(Input.searchString2);
			search.getThreadedAddButton().waitAndClick(20);
			search.getBulkActionButton().waitAndClick(10);
			search.getDocViewAction().waitAndClick(10);
			docView.AnalyticsActions();
	    }

	@Test(groups={"regression"},priority=11)
		public void NonAudioRemarkAddEditDeletebyReviewer()
		{
		  System.out.println("******Execution started for NonAudioRemarkAddEditDeletebyReviewer********");
			docView.addRemarkNonAudioDoc(Remark);
		}

	  
		@Test(groups={"regression"},priority=12)
		public void VerifyAnalytics_FamilyActions() throws InterruptedException
		{
			
			  System.out.println("******Execution started for VerifyAnalytics_FamilyActions********");
			driver.getWebDriver().navigate().refresh();
			lp.logout();
			lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			page = new TagsAndFoldersPage(driver);
			page.CreateFolder(folderName1,"Default Security Group");
	    	
		    SessionSearch search = new SessionSearch(driver);
			search.basicContentSearch(Input.searchString2);
			search.getFamilyAddButton().waitAndClick(20);
			search.getBulkActionButton().waitAndClick(10);
			search.getDocViewAction().waitAndClick(10);
			docView.Analytics_FamilyActions(folderName1);
			final DocListPage dp = new DocListPage(driver);
		    dp.getDocList_info().WaitUntilPresent();
		    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    		   !dp.getDocList_info().getText().isEmpty()  ;}}),Input.wait60);
		    Assert.assertEquals(dp.getDocList_info().getText().toString().replaceAll(",", ""),"Showing 1 to 1 of 1 entries");
		    System.out.println("Expected docs '1' is shown in doclist");

			 final TagsAndFoldersPage tf = new TagsAndFoldersPage(driver);
		       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		       		tf.getTag_ToggleDocCount().Visible()  ;}}),Input.wait60); 
		  	   tf.getFoldersTab().waitAndClick(10);
		       tf.getFolder_ToggleDocCount().waitAndClick(10);
		       tf.getFolderandCount(folderName1, 1).WaitUntilPresent();
		       Assert.assertTrue(tf.getFolderandCount(folderName1, 1).Displayed());
		       System.out.println(folderName1+" could be seen under tags and folder page");
		}
		
		 @Test(groups={"regression"},priority=13)
			public void VerifyMiniDoclistFolderAction() throws InterruptedException
			{
			  System.out.println("******Execution started for VerifyMiniDoclistFolderAction********");
			 	driver.getWebDriver().navigate().refresh();
			 	 BaseClass bc = new BaseClass(driver);
				 bc.selectproject();
				SessionSearch search = new SessionSearch(driver);
				search.basicContentSearch(Input.searchString2);
				search.getPureHitAddButton().waitAndClick(20);
				search.getBulkActionButton().waitAndClick(10);
				search.getDocViewAction().waitAndClick(10);
				docView.MiniDoclistFolderAction(folderName2);
		    }
		 
		
	    
	  
	    @Test(groups={"regression"},priority=14)
		public void VerifyTabswhenAllprefEnabled() throws InterruptedException
		{
	    	  System.out.println("******Execution started for VerifyTabswhenAllprefEnabled********");
	    	 driver.getWebDriver().navigate().refresh();
			agnmt.editAssignment(assignmentName);
			agnmt.AssgnToggleButtons();
			agnmt.getAssignment_BackToManageButton().waitAndClick(10);
			agnmt.getAssignmentActionDropdown().waitAndClick(10);
			agnmt.getAssignmentAction_ViewinDocView().waitAndClick(5);
			docView.VerifyTabswhenAllprefEnabled();
	    }

	 	
		 @Test(groups={"regression"},priority=15)
		public void getPersistentHit() throws InterruptedException {
			  System.out.println("******Execution started for getPersistentHit********");
			 driver.getWebDriver().navigate().refresh();
			 agnmt = new AssignmentsPage(driver);
			 agnmt.SelectAssignmentToViewinDocview(assignmentName);
		     docView.VerifyPersistentHit(Input.searchString1);
	    }

			//@Test(groups={"regression"},priority=16)
			public void VerifyTooltipsforIcons() throws InterruptedException {
				driver.getWebDriver().navigate().refresh();
				AssignmentsPage agnmt = new AssignmentsPage(driver);
				agnmt.SelectAssignmentToViewinDocview(assignmentName);
				docView=new DocViewPage(driver); 
				docView.VerifyTooltipsforallIconsEnglish();
				
				System.out.println("Verify tooltip for German language................");
				  
				lp.getSignoutMenu().waitAndClick(5);
				lp.getEditProfile().waitAndClick(10);
		    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    			lp.getSelectLanguage().Visible()  ;}}), Input.wait30); 
		    	lp.getSelectLanguage().selectFromDropdown().selectByVisibleText("German - Germany");
		    	lp.getEditprofilesave().waitAndClick(10);
		    	
		    	hm = new HomePage(driver);
			    for (WebElement element : hm.getAssignmentsList().FindWebElements()) {
					if(element.getText().equalsIgnoreCase(assignmentName)){
						System.out.println(assignmentName +"is assigned to reviewer successfully");
						element.click();
						break;
					}
				}	
		      docView.VerifyTooltipsforallIconsGerman();     
		      lp.getSignoutMenu().waitAndClick(5);
				lp.getEditProfile().waitAndClick(10);
		  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		  			lp.getSelectLanguage().Visible()  ;}}), Input.wait30); 
		  	lp.getSelectLanguage().selectFromDropdown().selectByVisibleText("English - United States");
		  	lp.getEditprofilesave().waitAndClick(10);
		  	driver.Navigate().refresh();
				
			}

			  @Test(groups={"regression"},priority=17)
				public void VerifyAnalyticsthrAssignment() throws InterruptedException
				{
				  System.out.println("******Execution started for VerifyAnalyticsthrAssignment********");
				  driver.getWebDriver().navigate().refresh();
				  BaseClass bc = new BaseClass(driver);
					 bc.selectproject();
					SessionSearch search = new SessionSearch(driver);
					search.basicContentSearch(Input.searchString2);
					// search.Removedocsfromresults();
					search.getThreadedAddButton().waitAndClick(20);
					agnmt = new AssignmentsPage(driver);
					search.bulkAssign();
					
					agnmt.assignDocstoExisting(assignmentName);
					agnmt.SelectAssignmentToViewinDocview(assignmentName);
					
					docView.AnalyticsCodeSameAs();
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
		
	  	 @Test(groups={"regression"},priority=21)
	 	public void VerifyAnalyticsConceptual() throws InterruptedException
	 	{
	  		 System.out.println("******Execution started for VerifyAnalyticsConceptual********");
	  		 BaseClass bc = new BaseClass(driver);
			 bc.selectproject();
	 		SessionSearch search = new SessionSearch(driver);
	 		search.basicContentSearch(Input.searchString2);
	 	    search.getConceptuallyPlayButton().waitAndClick(20);
	 	    search.getConceptAddButton().waitAndClick(60);
	 		search.getBulkActionButton().waitAndClick(10);
	 		search.getDocViewAction().waitAndClick(10);
	 		docView.getDocView_AnalyticsEmail();
	 		
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
