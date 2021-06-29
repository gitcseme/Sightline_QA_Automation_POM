package testScriptsSmoke;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import SightlineObjects.DatasetDetails;
import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import automationLibrary.GenFunc;
import executionMaintenance.UtilityLog;
import junit.framework.Assert;
import pageFactory.BaseClass;
import pageFactory.ICE_DatasetProgressStatusPage;
import pageFactory.ICE_DatasetSummaryPage;
import pageFactory.ICE_DatasetsPage;
import pageFactory.ICE_ManageUploadPage;
import pageFactory.LoginPage;
import pageFactory.ManageUsersPage;
import pageFactory.Utility;

public class TS_019_ICEWorkFlow {
	Driver driver;
	LoginPage lp;
	SoftAssert sa = new SoftAssert();
	BaseClass bc = new BaseClass(driver);
	

	@SuppressWarnings("static-access")
	@BeforeClass(alwaysRun = true)
	public void before() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");

		Input in = new Input();
		in.loadEnvConfig();

		driver = new Driver();
		lp = new LoginPage(driver);
		lp.clearBrowserCache();
	}

	 @Test(groups={"smoke","regression"},priority=1)
	public void SystemAdminUserVerifications() throws InterruptedException {
		lp.loginToSightLine(Input.sa1userName, Input.sa1password);
		driver.waitForPageToBeReady();
		ManageUsersPage muPage = new ManageUsersPage(driver);
		muPage.getProjectListByProjectName(Input.ICEProjectName);
		// Test Case No: 9450: Priority 1
		Assert.assertTrue(muPage.getICEProjectStatuslabel().getText().toString().equalsIgnoreCase("ICE PROJECT STATUS"));
		// Test Case No: 9451: Priority 1
		Assert.assertTrue(muPage.getICEProjectStatus().getText().toString().trim().equalsIgnoreCase("ACTIVE"));
		
		BaseClass bc = new BaseClass(driver);
		bc.impersonateSAtoPAICE();
		ICE_DatasetsPage dp = new ICE_DatasetsPage(driver);
		// Test case No: 9533: Priority 1
		Assert.assertTrue(dp.getdatasetleftmenuBtn().Displayed());
		dp.getdatasetleftmenuBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		// Test Case No: 9548, Priority 2
		Assert.assertTrue(dp.getTotalDocumentsEl().getText().equals("Total Documents Published"));
		bc.impersonatePAtoRMUICE();
		// Test case No: 9533: Priority 1
		Assert.assertTrue(dp.getdatasetleftmenuBtn().Displayed());
		dp.getdatasetleftmenuBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		// Test Case No: 9548, Priority 2
		Assert.assertTrue(dp.getTotalDocumentsEl().getText().equals("Total Documents Released"));
		lp.logout();
	}

	@Test(groups = { "smoke", "regression" }, priority = 2)
	public void PMUploadAndInitiate() throws InterruptedException {
		// The below line of codes will make sure the RMU user has access to dataset
		// option.
		// For the current project, also verifies that by default Project Admin user has
		// access.

		int fileCountBeforeUpload = 0;
		int fileuploaded = 0;
		int position = 0;
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		ICE_DatasetsPage dp = new ICE_DatasetsPage(driver);
		// Test Case No: 11037, Priority 2
	
		 List<String> test1 = new ArrayList<String>(); 
		 List<WebElement> menuposition = dp.getleftmenuList().FindWebElements(); 
		 for(int i=0; i<menuposition.size(); i++)
		 {
			 position++;
			 test1.add(menuposition.get(i).getText());
			 if(test1.contains("DATASETS"))
			 {
			 Assert.assertTrue(test1.contains("DATASETS"));
			 System.out.println(position);
			 menuposition.get(i).click();
			 break;
			 
			 }
		 }
	
		 Assert.assertEquals(position, 2);
		
		 Reporter.log("Dataset menu option avilable under doc explorer");
		 UtilityLog.info("Dataset menu option avilable under doc explorer");
		 
		
		// Assert.assertTrue(dp.getDatasetBtnLMenuPosition() == 2);
		// Test Case No: 9473, Prioirty 2
		
		dp.getdatasetleftmenuBtn().Click();
		driver.waitForPageToBeReady();
		// Test Case No: 9460, Priority 2
		Assert.assertEquals(dp.getDatasetPageTitle(), "Datasets");
		// Test Case No: 11090, Priority 2
		Assert.assertTrue(dp.getShowDropDown().Visible());
		Assert.assertTrue(dp.getSearchByBox().Visible());
		Assert.assertTrue(dp.getSortBy().Visible());
		// Test Case No: 11090, Priority 2 Ends
		Assert.assertTrue(dp.isDatasetActive());
		// Test Case NO: 9547, Priority 2
		Assert.assertTrue(dp.getTotalDocumentsEl().getText().equals("Total Documents Published"));
		dp.getShowDropDown().Click();
		// Test Case No: 11089, Priority 2
		//Assert.assertTrue(dp.getShowDropDown().FindElementBycssSelector("option").Selected());
		String showdrpdown = dp.getShowDropDown().selectFromDropdown().getFirstSelectedOption().getText();
		Assert.assertEquals(showdrpdown, "All Datasets");
		
		
		DatasetDetails testdd = new DatasetDetails();
		//testdd.setDatasetName("Auto" + Utility.dynamicNameAppender());
		//testdd.setCustodianName("Auto " + Utility.dynamicNameAppender());
		//testdd.setDescription(this.getClass().toString());
		// Test Case No:9520
		//dp.CreateNewUploadSet(testdd);
		
		String dname ="Auto" + Utility.dynamicNameAppender();
		String dcustodian ="Auto" + Utility.dynamicNameAppender();
		String ddisc ="Auto test for dataset" + Utility.dynamicNameAppender();
		dp.setdatasetdetails(dname,dcustodian, ddisc);
		driver.waitForPageToBeReady();
		
		dp.getdatasetleftmenuBtn().waitAndClick(30);
		dp.DeleteUploadedDatasetByName(dname);
		
		dp.setdatasetdetails(dname,dcustodian, ddisc);
		
		ICE_ManageUploadPage mup = new ICE_ManageUploadPage(driver, testdd.getDatasetName());
		System.out.println(Input.iCESmokeFolderPath);
		String testFolderPath = System.getProperty("user.dir") + Input.iCESmokeFolderPath;
		mup.getdraganddroptab().waitAndFind(30);

		fileCountBeforeUpload = mup.getUploadCount();
		// Test Case No: 11069, Priority 2
		Assert.assertTrue(mup.getDropZoneLink().Displayed());
		// Test Case No: 10840, Priority 2
	//	Assert.assertTrue(mup.getDropZoneStaticText().getText().trim().contains(
	//			"* Please ensure that the names of files being uploaded are unique in a Dataset. If a file being uploaded has the same name as an already uploaded file, it will overwrite the file which was uploaded earlier.Also, we recommend zipping/compressing files prior to upload for faster transmittal over the Internet."));
		
		sa.assertTrue(mup.getDropZoneStaticText().getText().trim().contains("* Using Drag-and-Drop Upload is recommended to upload smaller datasets (smaller than 10GB) to Sightline. For better experience uploading data using Drag-and-Drop upload and High-Speed Upload, Consilio recommends never putting PSTs, MBOX or other email containers into ZIP archives. However, we recommend putting folders and loose eFiles into ZIP archives and upload. In addition, please ensure that the unzipped loose eFiles have unique names within a dataset, in order to avoid files with same names being overwritten."));
			    
		
		// Test Case no: 10827, Priority 2
		Assert.assertTrue(mup.getUploadFilesBtn().getText().equalsIgnoreCase("Upload Files"));
		fileuploaded = mup.uploadFilesByFolder(testFolderPath);
		// Test Case no: 10791, Priority 1 & 10998, Priority 2
		Assert.assertTrue(mup.getUploadCount() == (fileCountBeforeUpload + fileuploaded));
		// Test Case no: 10827, Priority 2
		Assert.assertTrue(mup.getUploadFilesBtn().getText().equalsIgnoreCase("Files Uploaded, Initiate Processing >"));
		Thread.sleep(2000);
		mup.getUploadFilesBtn().waitAndClick(10);
		// Test Case No: 10861, Priority 2
		
		mup.getInitatePopup().WaitUntilPresent();
		Assert.assertTrue(mup.getInitatePopup().Displayed());
		Assert.assertEquals(mup.getInitatePopupMessage().getText(),
				"Are you sure you want to initiate processing for this dataset?");
		mup.getInitatePopupNoBtn().Click();
		driver.waitForPageToBeReady();
		// Test Case No: 10858, Priority 2
		Assert.assertTrue(mup.getUploadFilesBtn().Enabled());
		
		final BaseClass bc = new BaseClass(driver);
        final int Bgcount = bc.initialBgCount();
        
		mup.InitiateProcessing();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return driver.FindElementByCssSelector("#lblProjectTitle").Visible();
			}
		}), Input.wait90);
		// Test Case No: 10859, Priority 2
		Assert.assertTrue(driver.FindElementByCssSelector("#lblProjectTitle").Visible());
		ICE_DatasetProgressStatusPage dpdp = new ICE_DatasetProgressStatusPage(driver, testdd.getDatasetName(), false);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {	return dpdp.getProcessingStatus().equalsIgnoreCase("Processing");}}), 10000);
		
		driver.Navigate().refresh();
		// Test Case No: 9518, Priority 1
		Assert.assertTrue(dpdp.getEntireProjectSummaryTableHeader().equalsIgnoreCase("TYPE FILE COUNT TOTAL SIZE (MB)"));
		Assert.assertTrue(dpdp.getEntireProjectSummaryTableValues().contains("Excluded"));
		Assert.assertTrue(dpdp.getEntireProjectSummaryTableValues().contains("Processed"));
		Assert.assertTrue(dpdp.getEntireProjectSummaryTableValues().contains("Duplicates"));
        
		ICE_DatasetSummaryPage dsumpage = new ICE_DatasetSummaryPage(driver, testdd.getDatasetName());
		sa.assertTrue(dsumpage.isDatasetSummaryPageLoaded());
		Assert.assertTrue(dpdp.getEntireProjectSummaryTableValues().contains("Duplicates"));

		
		// Test Case No: 9527, Priority 1
		sa.assertTrue(dpdp.isWorkLoadEntireProjectDisplayed());

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {return dpdp.getProcessingStatus().equalsIgnoreCase("PUBLISHCOMPLETE");}}), 1800000);

		Assert.assertTrue(dpdp.getProcessingStatus().equalsIgnoreCase("PUBLISHCOMPLETE"));

		  
  	 	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			bc.initialBgCount() == Bgcount+1  ;}}), Input.wait120); 
  
  	 	driver.WaitUntil((new Callable<Boolean>() {	public Boolean call() {
				return bc.getBackgroundTask_Button().Visible();	}}), Input.wait120);
		bc.getBackgroundTask_Button().Click();
		

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() {
				return bc.getBckTask_selecttask().Visible();}}), Input.wait30);
		System.out.println( bc.getBckTask_selecttask().getText());
	 String actualtext = bc.getBckTask_selecttask().getText();
	 Assert.assertEquals("The dataset "+dname+" has been processed successfully.", actualtext);
  	 UtilityLog.info("Processing is completed");
  	 Reporter.log("Processing is completed",true);

		bc.impersonatePAtoRMU();

		// Test Case No: 9529, Priority 1
		sa.assertTrue(dp.getdatasetleftmenuBtn().Displayed());
		lp.logout();

	}

     @Test(groups={"smoke","regression"},priority=3)
	public void RMUploadAndInitiate() throws InterruptedException {
    	
		ICE_DatasetsPage dp;
		dp = new ICE_DatasetsPage(driver);
		lp.loginToSightLineICE(Input.rmu1userName, Input.rmu1password);
		
		// Test Case No: 9456, Priority 2
		sa.assertNull(dp.getdatasetleftmenuBtn());
		
		lp.logout();
		dp = null;
		driver.waitForPageToBeReady();

		// The below line of codes will make sure the RMU user has access to dataset
		// option.
		// For the current project, also verifies that by default RMU user has no
		// access.

		lp.loginToSightLineICE(Input.sa1userName, Input.sa1password);
		ManageUsersPage mp = new ManageUsersPage(driver);
		// Element project =
		// mp.getUserListbyUserName(Input.rmu1userName,Input.ICEProjectName);
		// mp.getEditBtn(project).Click();
		driver.waitForPageToBeReady();
		mp.getUserList(Input.rmu1userName);
		
	//	mp.getUserListbyUserName(Input.rmu1userName,Input.ICEProjectName);
		
		mp.getEditUserFunctionality(Input.ICEProjectName).waitAndClick(10);
		
		mp.getUserFunctionalitytab().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() {return mp.getDatasetCheckbox().Visible();}}), Input.wait90);
		
		// Test Case No: 10983,11048, Priority 2
		sa.assertFalse(mp.getDatasetCheckStatus().Selected());
		if (!mp.getDatasetCheckStatus().Selected()) {
			mp.getDatasetCheckbox().Click();
			mp.getSaveBtnEditUser().Click();
			driver.waitForPageToBeReady();
		}
		lp.logout();
		driver.waitForPageToBeReady();

		// Now starts Review Manager user creating dataset, uplaod and initiate.

		int fileCountBeforeUpload = 0;
		int fileuploaded = 0;
		int position = 0;
		lp.loginToSightLineICE(Input.rmu1userName, Input.rmu1password);
		dp = new ICE_DatasetsPage(driver);
		// Test Case No: 11038, Priority 2
	//	Assert.assertTrue(dp.getDatasetBtnLMenuPosition() == 3);
		
		 List<String> test1 = new ArrayList<String>(); 
		 List<WebElement> menuposition = dp.getleftmenuList().FindWebElements(); 
		 for(int i=0; i<menuposition.size(); i++)
		 {
			 position++;
			 test1.add(menuposition.get(i).getText());
			 if(test1.contains("DATASETS"))
			 {
			 Assert.assertTrue(test1.contains("DATASETS"));
			 System.out.println(position);
			 menuposition.get(i).click();
			 break;
			 
			 }
		 }
	
		 Assert.assertEquals(position, 3);
		
		
		// Test Case No: 11055, Priority 2

		 Reporter.log("Dataset menu option avilable under doc explorer");
		 UtilityLog.info("Dataset menu option avilable under doc explorer");
		 
		
	   dp.getdatasetleftmenuBtn().Click();
		driver.waitForPageToBeReady();
		// Test Case No: 9460, Priority 2

		// Test Case No: 9460,10981, Priority 2
		Assert.assertEquals(dp.getDatasetPageTitle(), "Datasets");
		// Test Case No: 11090, Priority 2
		Assert.assertTrue(dp.getShowDropDown().Visible());
		Assert.assertTrue(dp.getSearchByBox().Visible());
		Assert.assertTrue(dp.getSortBy().Visible());
		// Test Case No: 11090, Priority 2 Ends
		// Test Case No: 10973, Priority 2
		Assert.assertTrue(dp.getCreateNewUploadSetLink().Displayed());
		// Test Case NO: 9546 Priority 2
		Assert.assertTrue(dp.getTotalDocumentsEl().getText().equals("Total Documents Released"));
		dp.getShowDropDown().Click();
		// Test Case No: 11089, Priority 2
		//Assert.assertTrue(dp.getShowDropDown().FindElementBycssSelector("option").Selected());

		
		String showdrpdown = dp.getShowDropDown().selectFromDropdown().getFirstSelectedOption().getText();
		Assert.assertEquals(showdrpdown, "All Datasets");
		
		
		DatasetDetails testdd = new DatasetDetails();
//		testdd.setDatasetName("Auto" + Utility.dynamicNameAppender());
//		testdd.setCustodianName("Auto " + Utility.dynamicNameAppender());
//		testdd.setDescription(this.getClass().toString());
//		// Test Case No:9520
//		dp.CreateNewUploadSet(testdd);
		
		String dname ="Auto" + Utility.dynamicNameAppender();
		String dcustodian ="Auto" + Utility.dynamicNameAppender();
		String ddisc ="Auto test for dataset" + Utility.dynamicNameAppender();
		dp.setdatasetdetails(dname,dcustodian, ddisc);
		driver.waitForPageToBeReady();
		
		dp.getdatasetleftmenuBtn().waitAndClick(30);
		dp.DeleteUploadedDatasetByName(dname);
		
		dp.setdatasetdetails(dname,dcustodian, ddisc);
		driver.waitForPageToBeReady();
		ICE_ManageUploadPage mup = new ICE_ManageUploadPage(driver, testdd.getDatasetName());
		String testFolderPath = System.getProperty("user.dir") + Input.iCESmokeFolderPath;
		fileCountBeforeUpload = mup.getUploadCount();
		// Test Case No: 11069, Priority 2
		Assert.assertTrue(mup.getDropZoneLink().Displayed());
		// Test Case No: 10840, Priority 2
		Assert.assertTrue(mup.getDropZoneStaticText().getText().trim().contains(
				"* Please ensure that the names of files being uploaded are unique in a Dataset. If a file being uploaded has the same name as an already uploaded file, it will overwrite the file which was uploaded earlier.Also, we recommend zipping/compressing files prior to upload for faster transmittal over the Internet."));
		// Test Case no: 10827, Priority 2
		Assert.assertTrue(mup.getUploadFilesBtn().getText().equalsIgnoreCase("Upload Files"));
		fileuploaded = mup.uploadFilesByFolder(testFolderPath);
		// Test Case no: 10791, Priority 1 & 10996, Priority 2
		Assert.assertTrue(mup.getUploadCount() == (fileCountBeforeUpload + fileuploaded));
		// Test Case no: 10827, Priority 2
		Assert.assertTrue(mup.getUploadFilesBtn().getText().equalsIgnoreCase("Files Uploaded, Initiate Processing >"));
		Thread.sleep(2000);
		mup.getUploadFilesBtn().Click();
		// Test Case No: 10855, Priority 2
		Assert.assertTrue(mup.getInitatePopup().Displayed());
		Assert.assertEquals(mup.getInitatePopupMessage().getText(),
				"Are you sure you want to initiate processing for this dataset?");
		mup.getInitatePopupNoBtn().Click();
		driver.waitForPageToBeReady();
		// Test Case No: 10853, Priority 2
		Assert.assertTrue(mup.getUploadFilesBtn().Enabled());
		mup.InitiateProcessing();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return driver.FindElementByCssSelector("#lblProjectTitle").Visible();
			}
		}), Input.wait90);
		// Test Case No: 10854, Priority 2
		Assert.assertTrue(driver.FindElementByCssSelector("#lblProjectTitle").Visible());
		ICE_DatasetProgressStatusPage dpdp = new ICE_DatasetProgressStatusPage(driver, testdd.getDatasetName(), false);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return dpdp.getProcessingStatus().equalsIgnoreCase("Processing");
			}
		}), 10000);
		driver.Navigate().refresh();
		// Test Case No: 9572,9573, Priority 1
		Assert.assertTrue(
				dpdp.getEntireProjectSummaryTableHeader().equalsIgnoreCase("TYPE FILE COUNT TOTAL SIZE (MB)"));
		Assert.assertTrue(dpdp.getEntireProjectSummaryTableValues().contains("Excluded"));
		Assert.assertTrue(dpdp.getEntireProjectSummaryTableValues().contains("Processed"));
		Assert.assertTrue(dpdp.getEntireProjectSummaryTableValues().contains("Duplicates"));

		// Test Case No: 9572,9574, Priority 1
		Assert.assertTrue(dpdp.isWorkLoadEntireProjectDisplayed());

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return dpdp.getProcessingStatus().equalsIgnoreCase("PUBLISHCOMPLETE");
			}
		}), 1800000);

		Assert.assertTrue(dpdp.getProcessingStatus().equalsIgnoreCase("PUBLISHCOMPLETE"));

		
		dp.getdatasetleftmenuBtn().Click();
		driver.waitForPageToBeReady();
		dp.viewSetInDocListByName(dname, false);
		driver.waitForPageToBeReady();
		dp.getdatasetleftmenuBtn().Click();
		driver.waitForPageToBeReady();
		dp.viewSetInDocViewByName(dname);
		driver.waitForPageToBeReady();
		dp.getdatasetleftmenuBtn().Click();
		driver.waitForPageToBeReady();
		dp.viewSetInTallyByName(dname);

		lp.logout();

	}

	 @Test(groups={"smoke","regression"},priority=4)
	public void PMUploadAndRefresh() throws InterruptedException {
		lp.loginToSightLineICE(Input.pa1userName, Input.pa1password);
		ICE_DatasetsPage dp = new ICE_DatasetsPage(driver);
		dp.getdatasetleftmenuBtn().waitAndClick(30);
		driver.waitForPageToBeReady();
		DatasetDetails testdd = new DatasetDetails();
		testdd.setDatasetName("Auto" + Utility.dynamicNameAppender());
		testdd.setCustodianName("Auto " + Utility.dynamicNameAppender());
		testdd.setDescription(this.getClass().toString());
		dp.CreateNewUploadSet(testdd);
		driver.waitForPageToBeReady();
		ICE_ManageUploadPage mup = new ICE_ManageUploadPage(driver, testdd.getDatasetName());
		String testFolderPath = System.getProperty("user.dir") + "\\ICETestData\\UploadDelay";
		mup.uploadFilesByFolderAndRefresh(testFolderPath);
		// Test Case No: 10792, Priority 1
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				mup.getConfirmNavigationPopup().Visible() ;}}),Input.wait60);
		Assert.assertTrue(mup.getConfirmNavigationPopup().Displayed());
		Assert.assertTrue(mup.getConfirmNavigationPopupNoBtn().Enabled());
		Assert.assertTrue(mup.getConfirmNavigationPopupYesBtn().Enabled());
		mup.getConfirmNavigationPopupYesBtn().Click();
		driver.waitForPageToBeReady();
		lp.logout();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			/*
			 * if(!lp.isLoggedOut()) { lp.logout(); }
			 */
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());

	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			lp.logout();
		} finally {
			lp.quitBrowser();
			lp.clearBrowserCache();

		}
	}

}
