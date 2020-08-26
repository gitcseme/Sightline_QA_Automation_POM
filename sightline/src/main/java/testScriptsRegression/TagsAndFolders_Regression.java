package testScriptsRegression;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import java.io.IOException;
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
	
 	driver = new Driver();
	lp = new LoginPage(driver);
	bc = new BaseClass(driver);
	
	lp.loginToSightLine(Input.pa1userName, Input.pa1password);
	
	tnfpage = new TagsAndFoldersPage(driver);
	tnfpage.CreateTag(Tag,"Default Security Group");
 	tnfpage.CreateFolder(Folder,"Default Security Group");
	
	SessionSearch search= new SessionSearch(driver); 
	search.basicContentSearch(Input.searchString1);
	search.bulkTagExisting(Tag);
	search.bulkFolderExisting(Folder);
	
	}
	
	@Test(priority =1,groups={"smoke","regression"})
	public void TagsViewinDocview() throws ParseException, InterruptedException {
		tnfpage = new TagsAndFoldersPage(driver);
	    tnfpage.ViewinDocViewthrTag(Tag);
    
		//Validate in docview count
		docview= new DocViewPage(driver);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 docview.getDocView_info().Visible()  ;}}), Input.wait30);
		String num = docview.getDocView_info().getText();
		Assert.assertTrue(Integer.parseInt(num.replaceAll("[^0-9]", ""))>0);
		System.out.println("Expected docs("+Integer.parseInt(num.replaceAll("[^0-9]", ""))+") are shown in docView");
	}
	
	@Test(priority =2,groups={"smoke","regression"})
	public void TagsViewinDocList() throws ParseException, InterruptedException {
		
		 tnfpage = new TagsAndFoldersPage(driver);
	     tnfpage.ViewinDocListthrTag(Tag);
       //view in doclist and verify count
		 doclist = new DocListPage(driver);
		 doclist.getDocList_info().WaitUntilPresent();
	     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	     !doclist.getDocList_info().getText().isEmpty()  ;}}),Input.wait60);
	     
	     Assert.assertTrue(Integer.parseInt(doclist.getDocList_info().getText().replaceAll("[^0-9]", ""))>0);
	     System.out.println("Expected docs("+Integer.parseInt(doclist.getDocList_info().getText().replaceAll("[^0-9]", ""))+") are shown in doclist");
	     //doclist.getBackToSourceBtn().Click();
	     //tnfpage.getTagsTab().WaitUntilPresent();
	}
	
	
	@Test(priority =3,groups={"smoke","regression"})
	public void FolderViewinDocview() throws ParseException, InterruptedException {
		tnfpage = new TagsAndFoldersPage(driver);
		tnfpage.ViewinDocViewthrFolder(Folder);
		Thread.sleep(3000);
		//Validate in docview count
		docview= new DocViewPage(driver);
		//docview.getDocView_info().WaitUntilPresent();
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 docview.getDocView_info().Visible()  ;}}), Input.wait30);
		String num = docview.getDocView_info().getText();
		Assert.assertTrue(Integer.parseInt(num.replaceAll("[^0-9]", ""))>0);
		System.out.println("Expected docs("+Integer.parseInt(num.replaceAll("[^0-9]", ""))+") are shown in docView");
	}
	
	@Test(priority =4,groups={"smoke","regression"})
	public void FolderViewinDocList() throws ParseException, InterruptedException {
		tnfpage = new TagsAndFoldersPage(driver);
		tnfpage.ViewinDocListthrFolder(Folder);
    
		 //view in doclist and verify count
		 doclist = new DocListPage(driver);
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 docview.getDocView_info().Visible()  ;}}), Input.wait30);
	     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	     !doclist.getDocList_info().getText().isEmpty()  ;}}),Input.wait60);
	     Assert.assertTrue(Integer.parseInt(doclist.getDocList_info().getText().replaceAll("[^0-9]", ""))>0);
	     System.out.println("Expected docs("+Integer.parseInt(doclist.getDocList_info().getText().replaceAll("[^0-9]", ""))+") are shown in doclist");
	     //doclist.getBackToSourceBtn().Click();
	     //tnfpage.getTagsTab().WaitUntilPresent();
	     lp.logout();
	}
	
	@Test(priority =5,groups={"smoke","regression"})
	public void deleteTagSAasPA() throws ParseException, InterruptedException, IOException {
 
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		//Impersonate as RMU
	
		bc.impersonateSAtoPA();

		//add tag
		String tag = "newTag"+Utility.dynamicNameAppender();
		TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
		page.CreateTag(tag,"Default Security Group");
		System.out.println("Tag added Successfully : "+tag);
		
		//add folder
		String folder = "newFolder"+Utility.dynamicNameAppender();
		page.CreateFolder(folder, "Default Security Group");
    	System.out.println("Folder added Successfully : "+folder);
    	
    	//Delete tag under security group
    	page.DeleteTag(tag,"Default Security Group");
    	System.out.println("Tag deleted from security group");
    	
    	//Delete folder under security group
    	page.DeleteFolder(folder,"Default Security Group");
    	System.out.println("Folder deleted from security group");
    	lp.logout();
 	}

	//added by Narendra
@Test(priority =6,groups={"smoke","regression"})
public void OperationOnTag() throws ParseException, IOException, InterruptedException {
			lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
					
	        //Add tag
			String tag = "newTag"+Utility.dynamicNameAppender();
			TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
			page.CreateTag(tag,"Default Security Group");
			System.out.println("Tag added Successfully : "+tag);
			
			//Cancel tag
			page.Tags(tag,"Default Security Group");
			System.out.println("Tag modification cancelled successfully");
			
			//Edit Tag Group
			page.TagGroup("Default Security Group");
			System.out.println("Tag group modified successfully");
				    	
	    	//Delete tag under security group
	    	page.DeleteTag(tag,"Default Security Group");
	    	System.out.println("Tag successfully deleted from security group!");
	    				 

}

@Test(priority =7,groups={"smoke","regression"})
public void OperationOnFolder() throws ParseException, IOException, InterruptedException {
			//lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
				
			//add folder
			String folder = "newFolder"+Utility.dynamicNameAppender();
			TagsAndFoldersPage page = new TagsAndFoldersPage(driver);
			page.CreateFolder(folder, "Default Security Group");
	    	System.out.println("Folder added Successfully : "+folder);
	    	
	    	
	    	//Delete tag under security group
	    	page.DeleteFolder(folder,"Default Security Group");
	    	System.out.println("Folder deleted from security group");

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
			
			}
		LoginPage.clearBrowserCache();
	}

}
