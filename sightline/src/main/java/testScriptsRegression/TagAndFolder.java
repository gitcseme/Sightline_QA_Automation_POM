package testScriptsRegression;

import java.io.IOException;
import java.text.ParseException;
import java.util.NoSuchElementException;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;

import automationLibrary.Driver;
import junit.framework.Assert;
import pageFactory.BaseClass;
import pageFactory.HomePage;
import pageFactory.LoginPage;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class TagAndFolder {
	Driver driver;
	LoginPage lp;
	HomePage hm;
	BaseClass bc;
	
	@BeforeClass(alwaysRun = true)
	public void before() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
	 	driver = new Driver();
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
		
	}
	
	@Test(priority =1, groups={"smoke","regression"})
	public void deleteTagSAasPA() throws ParseException, InterruptedException, IOException {
		
		driver =  new Driver();
		//Login as PA
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		//Impersonate as RMU
		bc = new BaseClass(driver);
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
    	
    	
    	//try to delete tag under same security group again, it should fail!
    	try{
    		page.DeleteTag(tag,"Default Security Group");
    		Assert.assertFalse(1==0);
    	}catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("Tag successfully deleted from security group!");
		}
    	
    	//Delete from all Groups, it make sure deleting tag under security group not deleted from all groups!
    	
    	page.DeleteTag(tag,"All Groups");
    	
    	//try deleting tag from all groups again to make sure its been deleted
    	try{
    		page.DeleteTag(tag,"All Groups");
    		Assert.assertFalse(1==0);
    	}catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("Tag successfully deleted from all groups!");
			System.out.println("Tag deletion working fine!!");
		}
    	
    	
    	page.DeleteFolder(folder,"Default Security Group");
    	System.out.println("Folder deleted from security group");
    	
    	//try deleting again to confirm, it should fail
    	try{
    		page.DeleteFolder(folder,"Default Security Group");
    	}catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("Folder successfully deleted from security group");
			
		}
    	//delete folder form all groups!
    	page.DeleteFolder(folder,"All Groups");
    	
    	//try deleting folder from all groups again to make sure its been deleted
    	try{
    		page.DeleteFolder(folder,"All Groups");
    		Assert.assertFalse(1==0);
    	}catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("folder successfully deleted from all groups!");
			System.out.println("folder deletion working fine!!");
		}
    	
	}
	
	    //added by Narendra
		@Test(priority =2,groups={"smoke","regression"})
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
		
		@Test(priority =3,groups={"smoke","regression"})
		public void OperationOnFolder() throws ParseException, IOException, InterruptedException {
					lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
						
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


