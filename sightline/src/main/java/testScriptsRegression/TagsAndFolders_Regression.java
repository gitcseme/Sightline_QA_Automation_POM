package testScriptsRegression;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class TagsAndFolders_Regression {
	Driver driver;
	LoginPage lp;
	TagsAndFoldersPage tnfpage;
	DocViewPage docview;
	DocListPage doclist;
	BaseClass bc;

	String Tag = "Tag"+Utility.dynamicNameAppender();
	String Folder = "Folder"+Utility.dynamicNameAppender();
	
	@BeforeClass(alwaysRun = true)
	public void before() throws ParseException, InterruptedException, IOException {
	System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
	/*Input in = new Input();
	in.loadEnvConfig(); */
 	driver = new Driver();
	lp = new LoginPage(driver);
	bc = new BaseClass(driver);	
	/*
    tnfpage = new TagsAndFoldersPage(driver);
	bc.stepInfo("Test case Id: RPMXCON-52476 - CreateTag");
	bc.stepInfo("*****Create new Tag*****");
	tnfpage.CreateTag(Tag,"Default Security Group");
	bc.passedStep("*****Tag added successfully*****");
	bc.stepInfo("Test case Id: RPMXCON-52489 - CreateFolder");
	bc.stepInfo("*****Create new Folder*****");
 	tnfpage.CreateFolder(Folder,"Default Security Group");
 	bc.passedStep("*****Folder added successfully*****");
	
	SessionSearch search= new SessionSearch(driver); 
	search.basicContentSearch(Input.searchString1);
	bc.stepInfo("*****Bulk Tag with Existing Tag*****");
	search.bulkTagExisting(Tag);
	bc.passedStep("*****Bulk Tag is done with Existing Tag*****");
	bc.stepInfo("*****Bulk Folder with Existing Folder*****");
	search.bulkFolderExisting(Folder);
	bc.stepInfo("*****Bulk Folder is done with Existing Folder*****"); */
	
	}
	
	@BeforeMethod
	 public void beforeTestMethod(Method testMethod){
		System.out.println("------------------------------------------");
	    System.out.println("Executing method : " + testMethod.getName());       
	 }
	
	@Test(priority=1,groups={"smoke","regression"})
	public void CreateTagandfolder() throws InterruptedException {
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.passedStep("*****Login successfull*****");
		tnfpage = new TagsAndFoldersPage(driver);
		bc.stepInfo("Test case Id: RPMXCON-52476 - CreateTag");
		bc.stepInfo("*****Create new Tag*****");
		tnfpage.CreateTag(Tag,"Default Security Group");
		bc.passedStep("*****Tag added successfully*****");
		driver.Navigate().refresh();
		
		bc.stepInfo("Test case Id: RPMXCON-52489 - CreateFolder");
		bc.stepInfo("*****Create new Folder*****");
	 	tnfpage.CreateFolder(Folder,"Default Security Group");
	 	bc.passedStep("*****Folder added successfully*****");
		
		SessionSearch search= new SessionSearch(driver); 
		search.basicContentSearch(Input.searchString1);
		bc.stepInfo("*****Bulk Tag with Existing Tag*****");
		search.bulkTagExisting(Tag);
		bc.passedStep("*****Bulk Tag is done with Existing Tag*****");
		bc.stepInfo("*****Bulk Folder with Existing Folder*****");
		search.bulkFolderExisting(Folder);
		bc.stepInfo("*****Bulk Folder is done with Existing Folder*****");
		
	}
	@Test(priority=2,groups={"smoke","regression"})
	public void TagsViewinDocview() throws ParseException, InterruptedException {
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.passedStep("*****Login successfull*****");
		docview= new DocViewPage(driver);
		bc.stepInfo("Test case Id: RPMXCON-53185 - TagViewinDocview");
		tnfpage = new TagsAndFoldersPage(driver);
		bc.stepInfo("****View tag in Docview****");
	    tnfpage.ViewinDocViewthrTag(Tag);
	    bc.stepInfo("****Validating count in Docview****");
    
		//Validate in docview count
		//docview= new DocViewPage(driver);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 docview.getDocView_info().Visible()  ;}}), Input.wait30);
		String num = docview.getDocView_info().getText();
		Assert.assertTrue(Integer.parseInt(num.replaceAll("[^0-9]", ""))>0);
		System.out.println("Expected docs("+Integer.parseInt(num.replaceAll("[^0-9]", ""))+") are shown in docView");
		bc.passedStep("*****Expected docs Validation successfull in TagviewinDocview*****");
	}
	
	@Test(priority=3,groups={"smoke","regression"})
	public void TagsViewinDocList() throws ParseException, InterruptedException {
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.passedStep("*****Login successfull*****");
		bc.stepInfo("Test case Id: RPMXCON-53187 - TagViewinDoclist");
		 tnfpage = new TagsAndFoldersPage(driver);
		 doclist = new DocListPage(driver);
		 bc.stepInfo("****View Tag in Doclist****");
	     tnfpage.ViewinDocListthrTag(Tag);
	     bc.stepInfo("****Validating count in DocList****");
       //view in doclist and verify count
		 //doclist = new DocListPage(driver);
		 doclist.getDocList_info().WaitUntilPresent();
	     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	     !doclist.getDocList_info().getText().isEmpty()  ;}}),Input.wait60);
	     
	     Assert.assertTrue(Integer.parseInt(doclist.getDocList_info().getText().replaceAll("[^0-9]", ""))>0);
	     System.out.println("Expected docs("+Integer.parseInt(doclist.getDocList_info().getText().replaceAll("[^0-9]", ""))+") are shown in doclist");
	     bc.passedStep("*****Expected docs Validation successfull in TagViewinDocList*****");
	     bc.stepInfo("Test case Id: RPMXCON-53189 - BacktoTagandfolderpage");
	     bc.stepInfo("click on back to source from Tagviewindoclist");
	     doclist.getBackToSourceBtn().Click();
	    tnfpage.getTagsTab().WaitUntilPresent();
	    bc.passedStep("*****Redirected to Tagandfolder page from Tagviewindoclist *****");
	    
	}
	
	
	@Test(priority=4,groups={"smoke","regression"})
	public void FolderViewinDocview() throws ParseException, InterruptedException {
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.passedStep("*****Login successfull*****");
		bc.stepInfo("Test case Id: RPMXCON-53186 - FolderViewinDocview");
		tnfpage = new TagsAndFoldersPage(driver);
		docview= new DocViewPage(driver);
		 bc.stepInfo("****View folder in DocView****");
		tnfpage.ViewinDocViewthrFolder(Folder);
		
		Thread.sleep(3000);
		 bc.stepInfo("****Validating count in DocView****");
		//Validate in docview count
		//docview= new DocViewPage(driver);
		//docview.getDocView_info().WaitUntilPresent();
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 docview.getDocView_info().Visible()  ;}}), Input.wait30);
		String num = docview.getDocView_info().getText();
		Assert.assertTrue(Integer.parseInt(num.replaceAll("[^0-9]", ""))>0);
		System.out.println("Expected docs("+Integer.parseInt(num.replaceAll("[^0-9]", ""))+") are shown in docView");
		bc.passedStep("*****Expected docs Validation successfull in FolderviewinDocView*****");
	}
	
	@Test(priority=5,groups={"smoke","regression"})
	public void FolderViewinDocList() throws ParseException, InterruptedException {
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.passedStep("*****Login successfull*****");
		bc.stepInfo("Test case Id: RPMXCON-53188 - FolderViewinDocList");
		tnfpage = new TagsAndFoldersPage(driver);
		doclist = new DocListPage(driver);
		bc.stepInfo("****View folder in Doclist****");
		tnfpage.ViewinDocListthrFolder(Folder);
		 bc.stepInfo("****Validating count in DocListview****");
		 //view in doclist and verify count
		 //doclist = new DocListPage(driver);
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 docview.getDocView_info().Visible()  ;}}), Input.wait30);
	     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	     !doclist.getDocList_info().getText().isEmpty()  ;}}),Input.wait60);
	     Assert.assertTrue(Integer.parseInt(doclist.getDocList_info().getText().replaceAll("[^0-9]", ""))>0);
	     System.out.println("Expected docs("+Integer.parseInt(doclist.getDocList_info().getText().replaceAll("[^0-9]", ""))+") are shown in doclist");
	     bc.passedStep("*****Expected docs Validation successfull in FolderviewinDocList*****");
	     bc.stepInfo("Test case Id: RPMXCON-53190 - BacktoTagandfolderpage");
	     bc.stepInfo("click on back to source from folder viewindoclist");
	     doclist.getBackToSourceBtn().Click();
	     tnfpage.getTagsTab().WaitUntilPresent();
	     bc.passedStep("*****Redirected to Tagandfolder page from Folderviewindoclist *****");
	     //lp.logout();
	     
	}
	
	@Test(priority=6,groups={"smoke","regression"})
	public void deleteTagSAasPA() throws ParseException, InterruptedException, IOException {
		bc.stepInfo("Test case Id: RPMXCON-52490 - OperationsAsPA");
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		//Impersonate as RMU
		
		
		bc.stepInfo("Impersnating from SA to PA");
		bc.impersonateSAtoPA();
		bc.stepInfo("Impersnated from SA to PA");

		//add tag
		bc.stepInfo("*****Create new Tag*****");
		TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
		String tag = "newTag"+Utility.dynamicNameAppender();
		page.CreateTag(tag,"Default Security Group");
		System.out.println("Tag added Successfully : "+tag);
		bc.passedStep("*****Tag added successfully*****");
		
		//add folder
		bc.stepInfo("*****Create new Folder*****");
		String folder = "newFolder"+Utility.dynamicNameAppender();
		page.CreateFolder(folder,"Default Security Group");
    	System.out.println("Folder added Successfully : "+folder);
    	bc.passedStep("*****Folder added successfully*****");
    	
    	//Delete tag under security group
    	bc.stepInfo("*****Delete Existing Tag*****");
    	page.DeleteTag(tag,"Default Security Group");
    	System.out.println("Tag deleted from security group");
    	bc.passedStep("*****Tag Deleted successfully from security group*****");
    	
    	
    	//Delete folder under security group
    	bc.stepInfo("*****Delete Existing Folder*****");
    	page.DeleteFolder(folder,"Default Security Group");
    	System.out.println("Folder deleted from security group");
    	bc.passedStep("*****Folder Deleted successfully from security group*****");
    	lp.logout();
 	}

	//added by Narendra
@Test(priority=8,groups={"smoke","regression"})
public void OperationOnTag() throws ParseException, IOException, InterruptedException {
			lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			bc.stepInfo("Test case Id: RPMXCON-53181 - OperationOnTag");		
	        //Add tag
			bc.stepInfo("*****Create new Tag*****");
			TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
			String tag = "newTag"+Utility.dynamicNameAppender();
			page.CreateTag(tag,"Default Security Group");
			System.out.println("Tag added Successfully : "+tag);
			bc.passedStep("*****Tag added successfully*****");
			
			//Cancel tag
			bc.stepInfo("*****Cancel Tag modification*****");
			page.Tags(tag,"Default Security Group");
			System.out.println("Tag modification cancelled successfully");
			bc.passedStep("*****Tag modification cancelled successfully*****");
			
			//Edit Tag Group
			bc.stepInfo("*****Tag modification*****");
			page.TagGroup("Default Security Group");
			System.out.println("Tag group modified successfully");
			bc.passedStep("*****Tag group modified successfully*****");
				    	
	    	//Delete tag under security group
			bc.stepInfo("*****Delete Tag*****");
	    	page.DeleteTag(tag,"Default Security Group");
	    	System.out.println("Tag successfully deleted from security group!");
	    	bc.passedStep("*****Tag successfully deleted from security group!*****");
	    				 

}

@Test(priority=7,groups={"smoke","regression"})
public void OperationOnFolder() throws ParseException, IOException, InterruptedException {
	      
			lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
	        bc.stepInfo("Test case Id: RPMXCON-53182 - OperationOnFolder");		
			//add folder
	        String folder = "newfolder"+Utility.dynamicNameAppender();
	        TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
	        bc.stepInfo("*****Create new Folder*****");
            page.CreateFolder(folder, "Default Security Group");
	    	System.out.println("Folder added Successfully : "+folder);
	    	bc.passedStep("*****Folder added successfully*****");
	    	
	    	
	    	//Delete tag under security group
	    	bc.stepInfo("*****Delete new Folder*****");
	    	page.DeleteFolder(folder,"Default Security Group");
	    	System.out.println("Folder deleted from security group");
	    	bc.passedStep("*****Folder deleted from security group*****");

}
			
  
	@AfterMethod(alwaysRun = true)
	 public void takeScreenShot(ITestResult result) {
 	 if(ITestResult.FAILURE==result.getStatus()){
 		Utility bc = new Utility(driver);
 		bc.screenShot(result);
 	}
 	 System.out.println("Executed :" + result.getMethod().getMethodName());
	 	try{
	 		lp.logout();
	 	}catch (Exception e) {
			// TODO: handle exception
		}
	}
	@AfterClass(alwaysRun = true)
	public void close(){
		try{ 
			lp.logout();
		     //lp.quitBrowser();
		}catch (Exception e) {}
			finally {
				lp.quitBrowser();
			
			}
		LoginPage.clearBrowserCache();
	}

}
