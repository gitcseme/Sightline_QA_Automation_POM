package testScriptsRegression;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.ITestResult;
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
import junit.framework.Assert;
import pageFactory.BaseClass;
import pageFactory.ICE_DatasetProgressStatusPage;
import pageFactory.ICE_DatasetSummaryPage;
import pageFactory.ICE_DatasetsPage;
import pageFactory.ICE_ErrorNExceptionsPage;
import pageFactory.ICE_ManageUploadPage;
import pageFactory.LoginPage;
import pageFactory.ManageUsersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ICE_Regression {
	
	Driver driver;
    LoginPage lp;
    SoftAssert sa = new SoftAssert();
    BaseClass bc ;
    
    @SuppressWarnings("static-access")
	@BeforeClass(alwaysRun = true)
     public void before() {
    	System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
			
    	//	String securitygroup= "Default Security Group";
    		driver = new Driver();
    	    lp = new LoginPage(driver);
    	    lp.clearBrowserCache();
    	    
	}

   
    @Test(groups={"regression"})
  	public void PMUploadAndDeleteFile() throws InterruptedException
  	{
      	lp.loginToSightLineICE(Input.pa1userName, Input.pa1password);
  		ICE_DatasetsPage dp = new ICE_DatasetsPage(driver);
  		dp.getDatasetBtnLMenu().Click();
  		driver.waitForPageToBeReady();
  		DatasetDetails testdd = new DatasetDetails();
  		testdd.setDatasetName("Auto"+Utility.dynamicNameAppender());
  		testdd.setCustodianName("Auto "+Utility.dynamicNameAppender());
  		testdd.setDescription(this.getClass().toString());
  		dp.CreateNewUploadSet(testdd);
  		driver.waitForPageToBeReady();
  		ICE_ManageUploadPage mup = new ICE_ManageUploadPage(driver, testdd.getDatasetName());
  		String testFolderPath = System.getProperty("user.dir")+"\\ICETestData\\UploadDelay";
  		mup.uploadFilesByFolderReturnWithoutCompleting(testFolderPath);
  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				mup.getInProgressFiles().size()==2;}}),Input.wait90);
  		mup.getNonProgressedFiles().getLastElement().FindElementBycssSelector("a.dz-remove").Click();
  		//Test Case No: 10801, Priority 1
  		Assert.assertTrue(driver.FindElementByCssSelector("#divbigBoxes > div > div > p").WaitUntilPresent().getText().equalsIgnoreCase("File Upload Cancelled."));
  		

  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				mup.getInProgressFiles().size()>0;}}),Input.wait90);
  		mup.getInProgressFiles().getFirstElement().FindElementBycssSelector("a.dz-remove").Click();
  		mup.AcceptAlert();
  		//Test Case No: 10802, Priority 1
  		Assert.assertTrue(driver.FindElementByCssSelector("#divbigBoxes > div > div > p").WaitUntilPresent().getText().equalsIgnoreCase("File Upload Cancelled."));
  	
  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				mup.getCompletedFiles().size()>0;}}),300000);
  		mup.getCompletedFiles().getFirstElement().FindElementBycssSelector("a.dz-remove").Click();
  		//Test Case No: 10803, Priority 1
  		Assert.assertTrue(driver.FindElementByCssSelector("#divbigBoxes > div > div > p").WaitUntilPresent().getText().equalsIgnoreCase("Removed Uploaded file Successfully"));
  		
  		driver.scrollPageToTop();
  		dp.getDatasetBtnLMenu().Click();
  		mup.AcceptNavigation();
  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			dp.getDatasets().getFirstElement().Displayed();	}}),Input.wait90);
  		dp.DeleteUploadedDatasetByName(testdd.getDatasetName());
  		lp.logout();
  	}
    
	@Test(groups={"regression"})
	public void PAUDupeAllFiles() throws InterruptedException
	{
		String testFolderPath = System.getProperty("user.dir")+"\\ICETestData\\TestFolder4";//DupeAllFiles
		lp.loginToSightLineICE(Input.pa1userName, Input.pa1password);	
		ICE_DatasetsPage dp=new ICE_DatasetsPage(driver);
		ICE_ManageUploadPage mup;
		
		dp.getDatasetBtnLMenu().Click();
		driver.waitForPageToBeReady();
		DatasetDetails dset = new DatasetDetails();
		dset.setDatasetName("Auto"+Utility.dynamicNameAppender());
		dset.setCustodianName("Dupe Test1");
		dset.setDescription(this.getClass().toString());
		dp.CreateNewUploadSet(dset);
		driver.waitForPageToBeReady();
		mup = new ICE_ManageUploadPage(driver, dset.getDatasetName());
		mup.uploadFilesByFolder(testFolderPath);
		mup.InitiateProcessing();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				driver.FindElementByCssSelector("#lblProjectTitle").Visible(); }}),Input.wait90);
		ICE_DatasetProgressStatusPage dpdp = new ICE_DatasetProgressStatusPage(driver,dset.getDatasetName(),false);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				dpdp.getProcessingStatus().equalsIgnoreCase("PUBLISHCOMPLETE"); }}),1800000);
		lp.logout();
		
		driver.waitForPageToBeReady();
		lp.loginToSightLineICE(Input.pa1userName, Input.pa1password);
		dp.getDatasetBtnLMenu().Click();
		driver.waitForPageToBeReady();
		DatasetDetails dupeDset = new DatasetDetails();
		dupeDset.setDatasetName("Auto"+Utility.dynamicNameAppender());
		dupeDset.setCustodianName("Dupe Test2");
		dupeDset.setDescription(this.getClass().toString());
		dp.CreateNewUploadSet(dupeDset);
		driver.waitForPageToBeReady();
		mup = new ICE_ManageUploadPage(driver, dupeDset.getDatasetName());
		mup.uploadFilesByFolder(testFolderPath);
		//System.out.println(bc.getBullHornDetailByIndex(0));
		bc = new BaseClass(driver);
		int bCountBefore = 0;
		bc.getBackgroundTask_Button().Click();
		driver.waitForPageToBeReady();
		bc.getBackgroundTask_Button().Click();
		driver.waitForPageToBeReady();
		mup.InitiateProcessing();
		ICE_DatasetProgressStatusPage dpdp1 = new ICE_DatasetProgressStatusPage(driver,dupeDset.getDatasetName(),true);
		//Test Case No: 10807, Priority 1
		Assert.assertTrue(dpdp1.getSLProcessingStatgesCompleted(1800000,1000).isAllStagesCompleted());
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			bc.initialBgCount()==bCountBefore+1  ;}}), 20000);
		int bCountAfter = bc.initialBgCount();
		Assert.assertTrue(bCountAfter>bCountBefore);
		bc.getBackgroundTask_Button().Click();
		driver.waitForPageToBeReady();
		bc.getBackgroundTask_Button().Click();
		//Test Case No: 9568, Priority 1
		Assert.assertTrue(bc.getBullHornDetailByIndex(0).contains(dupeDset.getDatasetName()));
		driver.waitForPageToBeReady();
		ICE_DatasetSummaryPage dsp = new ICE_DatasetSummaryPage(driver, dset.getDatasetName());
		Assert.assertTrue(dsp.getPageTitle().getText().contains("Dataset Summary: "+dset.getDatasetName()));
		dp.getDatasetBtnLMenu().Click();
		driver.waitForPageToBeReady();
		dp.viewSetInDocListByName(dset.getDatasetName(),true);
		int indexOfField = dp.getFieldPositionInDocListByName("AllCustodians");
		ElementCollection documents = dp.DocViewDocumentList();
		for(Element document:documents)
		{
		//Test Case No: 10804, Priority 1
			Assert.assertTrue(document.FindElementsBycssSelector("td").getElementByIndex(indexOfField).getText().split(";").length>1);
			Assert.assertTrue(document.FindElementsBycssSelector("td").getElementByIndex(indexOfField).getText().replaceAll(";", " ").contains(dupeDset.getCustodianName()));
		}
		lp.logout();	
		
	}
	
	@Test(groups={"regression"})
	public void PAExceptionFileFromManageScreen() throws Exception
	{
		
		//Start to create a dataset for error documents, where we have some survival document.
		bc = new BaseClass(driver);
		String testFolderPath = System.getProperty("user.dir")+"\\ICETestData\\ErrorFilesFolder";
		lp.loginToSightLineICE(Input.pa1userName, Input.pa1password);	
		ICE_DatasetsPage dp=new ICE_DatasetsPage(driver);
		ICE_ManageUploadPage mup;
		dp.getDatasetBtnLMenu().Click();
		driver.waitForPageToBeReady();
		DatasetDetails dset = new DatasetDetails();
		dset.setDatasetName("Auto ErrorTest");
		dset.setCustodianName("Error Test");
		dset.setDescription(this.getClass().toString());
		
		if(!dp.getDatasetByName("Auto ErrorTest").Displayed())
		{
			dp.CreateNewUploadSet(dset);
			driver.waitForPageToBeReady();
			mup = new ICE_ManageUploadPage(driver, dset.getDatasetName());
			mup.uploadFilesByFolder(testFolderPath);
			mup.InitiateProcessing();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					driver.FindElementByCssSelector("#lblProjectTitle").Visible(); }}),Input.wait90);
			ICE_DatasetProgressStatusPage dpdp = new ICE_DatasetProgressStatusPage(driver,dset.getDatasetName(),false);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					dpdp.getProcessingStatus().equalsIgnoreCase("PUBLISHCOMPLETE"); }}),1800000);
			driver.waitForPageToBeReady();
		}
		//Here we start to verify accessing error page from Manage dataset tile.
		dp.getDatasetBtnLMenu().Click();
		driver.waitForPageToBeReady();
		Element errordataset = dp.getDatasetByName(dset.getDatasetName());
		int ErrorCount = GenFunc.StringToInt(dp.getErrorCtLink(errordataset).getText());
		dp.getErrorCtLink(errordataset).WaitUntilPresent().Click();
		driver.waitForPageToBeReady();
		ICE_ErrorNExceptionsPage eep = new ICE_ErrorNExceptionsPage(driver,dset.getDatasetName());
		eep.getErrorNExceptionPage();
		bc.getBackgroundTask_Button().Click();
		bc.getBackgroundTask_Button().waitAndClick(500);;
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				eep.getErrorNExceptionPage().Displayed()  ;}}),Input.wait90);
		//Test Case No: 10795,10794, Priority 1
		Assert.assertTrue(eep.getErrorNExceptionPage().Displayed());
		int excludeFileCount = eep.getExcludedDocsListCount();
		int includedFileCount = eep.getIncludeDocsListCount();
		//Test Case No: 10795, Priority 1
		Assert.assertTrue(ErrorCount == (excludeFileCount+includedFileCount));
		eep.getExcludeFileDocumentsTab().Click();
		driver.waitForPageToBeReady();
		int[] indexArray = new int[] {1,3,11,12};
		String[][] selected = eep.SelectExcludedFilesForExportByIndex(indexArray);
		driver.scrollPageToTop();
		eep.getExportListBtn().Click();
		//Test Case NO: 10797, Priority 1
		bc.VerifySuccessMessage("Your report has been pushed into the background, and you will get a notification (in the upper right-hand corner \"bullhorn\" icon when your report is completed and ready for viewing.");
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   	 			bc.initialBgCount()==1 ;}}), Input.wait60); 
	   	bc.getBackgroundTask_Button().Click();
	   	bc.getBackTasks().getFirstElement().FindElementBycssSelector("span > a").Click();
	   	bc.getBackgroundTask_Button().Click();
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
	    		eep.getExcludeFileDownload(System.getProperty("user.dir")+"\\"+Input.batchFilesPath+"\\BatchPrintFiles").length>0  ;}}), Input.wait60); 
	    String[][] exportValues = eep.getExcludeFileDownload(System.getProperty("user.dir")+"\\"+Input.batchFilesPath+"\\BatchPrintFiles");
	    //Test Case NO: 10797,10844, Priority 1
		Assert.assertTrue(eep.isExportAccurate(selected,exportValues));
	    eep.deleteExportedFile(System.getProperty("user.dir")+"\\"+Input.batchFilesPath+"\\BatchPrintFiles");
	    driver.waitForPageToBeReady();
	    //Here we start to test exporting all the records
	    String[][] allSelected = eep.SelectAllExcludedFilesForExport();
		driver.scrollPageToTop();
		eep.getExportListBtn().Click();
		bc.VerifySuccessMessage("Your report has been pushed into the background, and you will get a notification (in the upper right-hand corner \"bullhorn\" icon when your report is completed and ready for viewing.");
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   	 			bc.initialBgCount() == 1  ;}}), Input.wait60); 
	   	bc.getBackgroundTask_Button().Click();
	   	bc.getBackTasks().getFirstElement().FindElementBycssSelector("span > a").Click();
	   	bc.getBackgroundTask_Button().Click();
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
	    		eep.getExcludeFileDownload(System.getProperty("user.dir")+"\\"+Input.batchFilesPath+"\\BatchPrintFiles").length>0  ;}}), Input.wait60); 
	    String[][] exportAllValues = eep.getExcludeFileDownload(System.getProperty("user.dir")+"\\"+Input.batchFilesPath+"\\BatchPrintFiles");
		//Test Case NO: 10798, Priority 1
	    Assert.assertTrue(eep.isExportAccurate(allSelected,exportAllValues));
	    eep.deleteExportedFile(System.getProperty("user.dir")+"\\"+Input.batchFilesPath+"\\BatchPrintFiles");
		lp.logout();
	}
	
	@Test(groups={"regression"})
	public void PAExceptionFileFromDatasetScreen() throws Exception
	{
		
		//Start to create a dataset for error documents, where we have some survival document.
		bc = new BaseClass(driver);
		String testFolderPath = System.getProperty("user.dir")+"\\ICETestData\\ErrorFilesFolder";
		lp.loginToSightLineICE(Input.pa1userName, Input.pa1password);	
		ICE_DatasetsPage dp=new ICE_DatasetsPage(driver);
		ICE_ManageUploadPage mup;
		dp.getDatasetBtnLMenu().Click();
		driver.waitForPageToBeReady();
		DatasetDetails dset = new DatasetDetails();
		dset.setDatasetName("Auto ErrorTest");
		dset.setCustodianName("Error Test");
		dset.setDescription(this.getClass().toString());
		
		if(!dp.getDatasetByName(dset.getDatasetName()).Displayed())
		{
			dp.CreateNewUploadSet(dset);
			driver.waitForPageToBeReady();
			mup = new ICE_ManageUploadPage(driver, dset.getDatasetName());
			mup.uploadFilesByFolder(testFolderPath);
			mup.InitiateProcessing();
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					driver.FindElementByCssSelector("#lblProjectTitle").Visible(); }}),Input.wait90);
			ICE_DatasetProgressStatusPage dpdp = new ICE_DatasetProgressStatusPage(driver,dset.getDatasetName(),false);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					dpdp.getProcessingStatus().equalsIgnoreCase("PUBLISHCOMPLETE"); }}),1800000);
			driver.waitForPageToBeReady();
		}
		//Here we start to verify accessing error page from Manage dataset tile.
		dp.getDatasetBtnLMenu().Click();
		driver.waitForPageToBeReady();
		Element errordataset = dp.getDatasetByName(dset.getDatasetName());
		int ErrorCount = GenFunc.StringToInt(dp.getErrorCtLink(errordataset).getText());
		dp.getDatasetNamelink(errordataset).WaitUntilPresent().Click();
		ICE_DatasetSummaryPage summarypage = new ICE_DatasetSummaryPage(driver,dset.getDatasetName());
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				summarypage.getExcludeFilesLink().Displayed();}}),Input.wait90);
		int eCountSumamryExcluded =GenFunc.StringToInt(summarypage.getExcludeFilesLink().getText());
		int eCountSumamryIncluded =GenFunc.StringToInt(summarypage.getIncludeErrorsLink().getText());
		Assert.assertTrue(ErrorCount == eCountSumamryExcluded+eCountSumamryIncluded);
		summarypage.getExcludeFilesLink().Click();
		driver.waitForPageToBeReady();
		ICE_ErrorNExceptionsPage eep = new ICE_ErrorNExceptionsPage(driver,dset.getDatasetName());
		eep.getErrorNExceptionPage();
		bc.getBackgroundTask_Button().Click();
		bc.getBackgroundTask_Button().waitAndClick(500);;
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				eep.getErrorNExceptionPage().Displayed()  ;}}),Input.wait90);
		//Test Case No: 10796, Priority 1
		Assert.assertTrue(eep.getErrorNExceptionPage().Displayed());
		int excludeFileCount = eep.getExcludedDocsListCount();
		int includedFileCount = eep.getIncludeDocsListCount();
		//Test Case No: 10796, Priority 1
		Assert.assertTrue(eCountSumamryExcluded == excludeFileCount);
		Assert.assertTrue(eCountSumamryIncluded == includedFileCount);
		eep.getExcludeFileDocumentsTab().Click();
		driver.waitForPageToBeReady();
		int[] indexArray = new int[] {1,3,11,12};
		String[][] selected = eep.SelectExcludedFilesForExportByIndex(indexArray);
		driver.scrollPageToTop();
		eep.getExportListBtn().Click();
		bc.VerifySuccessMessage("Your report has been pushed into the background, and you will get a notification (in the upper right-hand corner \"bullhorn\" icon when your report is completed and ready for viewing.");
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   	 			bc.initialBgCount()==1 ;}}), Input.wait60); 
	   	bc.getBackgroundTask_Button().Click();
	   	bc.getBackTasks().getFirstElement().FindElementBycssSelector("span > a").Click();
	   	bc.getBackgroundTask_Button().Click();
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
	    		eep.getExcludeFileDownload(System.getProperty("user.dir")+"\\"+Input.batchFilesPath+"\\BatchPrintFiles").length>0  ;}}), Input.wait60); 
	    String[][] exportValues = eep.getExcludeFileDownload(System.getProperty("user.dir")+"\\"+Input.batchFilesPath+"\\BatchPrintFiles");
	    //Test Case NO: 10799, Priority 1
		sa.assertTrue(eep.isExportAccurate(selected,exportValues));
	    eep.deleteExportedFile(System.getProperty("user.dir")+"\\"+Input.batchFilesPath+"\\BatchPrintFiles");
	    driver.waitForPageToBeReady();
	    
	    //Here we start to test exporting all the records
	    String[][] allSelected = eep.SelectAllExcludedFilesForExport();
		driver.scrollPageToTop();
		eep.getExportListBtn().Click();
		bc.VerifySuccessMessage("Your report has been pushed into the background, and you will get a notification (in the upper right-hand corner \"bullhorn\" icon when your report is completed and ready for viewing.");
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   	 			bc.initialBgCount() == 1  ;}}), Input.wait60); 
	   	bc.getBackgroundTask_Button().Click();
	   	bc.getBackTasks().getFirstElement().FindElementBycssSelector("span > a").Click();
	   	bc.getBackgroundTask_Button().Click();
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
	    		eep.getExcludeFileDownload(System.getProperty("user.dir")+"\\"+Input.batchFilesPath+"\\BatchPrintFiles").length>0  ;}}), Input.wait60); 
	    String[][] exportAllValues = eep.getExcludeFileDownload(System.getProperty("user.dir")+"\\"+Input.batchFilesPath+"\\BatchPrintFiles");
		//Test Case NO: 10800, Priority 1
	    sa.assertTrue(eep.isExportAccurate(allSelected,exportAllValues));
	    eep.deleteExportedFile(System.getProperty("user.dir")+"\\"+Input.batchFilesPath+"\\BatchPrintFiles");
		lp.logout();
	}

	@Test(groups={"regression"})
	public void VerifyEditUserHasDatasetOption() throws Exception
	{
    	bc = new BaseClass(driver);
		lp.loginToSightLineICE(Input.sa1userName, Input.sa1password);	
		ManageUsersPage mupage = new ManageUsersPage(driver);
		
		mupage.getUserList(Input.sa1userName);
		mupage.editSAUserBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() {return 
	    		mupage.getEditUserFunctionality().Displayed()  ;}}),Input.wait90);
		mupage.getEditUserFunctionality().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() {return 
				mupage.getDatasetOptionInFunctionalityTabCheck().Displayed() ;}}),Input.wait90);
		//Test Case No: 9454, Priority 2
		Assert.assertTrue(mupage.getDatasetOptionInFunctionalityTab().Displayed());
		mupage.getEditUserCancelBtn().Click();
		driver.waitForPageToBeReady();
		
		mupage.getUserList(Input.da1userName);
		mupage.editSAUserBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
	    		mupage.getEditUserFunctionality().Displayed()  ;}}),Input.wait90);
		mupage.getEditUserFunctionality().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() {return 
				mupage.getDatasetOptionInFunctionalityTabCheck().Displayed() ;}}),Input.wait90);
		//Test Case No: 9454, Priority 2
		Assert.assertTrue(mupage.getDatasetOptionInFunctionalityTab().Displayed());
		mupage.getEditUserCancelBtn().Click();
		driver.waitForPageToBeReady();
		

		mupage.getUserList(Input.pa1userName);
		mupage.editSAUserBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
	    		mupage.getEditUserFunctionality().Displayed()  ;}}),Input.wait90);
		mupage.getEditUserFunctionality().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() {return 
		    	mupage.getDatasetOptionInFunctionalityTabCheck().Displayed() ;}}),Input.wait90);
		//Test Case No: 9454, Priority 2
		Assert.assertTrue(mupage.getDatasetOptionInFunctionalityTab().Displayed());
		mupage.getEditUserCancelBtn().Click();
		driver.waitForPageToBeReady();


		mupage.getUserList(Input.rmu1userName);
		mupage.editSAUserBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
	    		mupage.getEditUserFunctionality().Displayed()  ;}}),Input.wait90);
		mupage.getEditUserFunctionality().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() {return 
				mupage.getDatasetOptionInFunctionalityTabCheck().Displayed() ;}}),Input.wait90);
		//Test Case No: 9454, Priority 2
		Assert.assertTrue(mupage.getDatasetOptionInFunctionalityTab().Displayed());
		mupage.getEditUserCancelBtn().Click();
		driver.waitForPageToBeReady();

				mupage.getUserList(Input.rev1userName);
		mupage.editSAUserBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
	    		mupage.getEditUserFunctionality().Displayed()  ;}}),Input.wait90);
		mupage.getEditUserFunctionality().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() {return 
				mupage.getDatasetOptionInFunctionalityTabCheck().Displayed() ;}}),Input.wait90);
		//Test Case No: 9458, Priority 2
		Assert.assertTrue(mupage.getDatasetOptionInFunctionalityTabCheck().GetAttribute("class").equalsIgnoreCase("BackgroundGrey"));
		mupage.getEditUserCancelBtn().Click();
		driver.waitForPageToBeReady();

		lp.logout();	
	}

	@Test(groups={"regression"})
    public void datasetOptionDisplayedIfChecked()
    {
    	final ManageUsersPage mupage;
    	ICE_DatasetsPage dp;
    	lp.loginToSightLineICE(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();
		dp = new ICE_DatasetsPage(driver);
		Assert.assertTrue(dp.getDatasetBtnLMenu()==null);
		lp.logout();
		
		lp.loginToSightLineICE(Input.sa1userName, Input.sa1password);
		driver.waitForPageToBeReady();
		mupage=new ManageUsersPage(driver);
		Element rmuuser = mupage.getUserListbyUserName(Input.rmu1userName,Input.ICEProjectName);
		rmuuser.FindElementBycssSelector("td:nth-child(9) > a:nth-child(1)").Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() {return 
	    		mupage.getEditUserFunctionality().Displayed()  ;}}),Input.wait60);
		mupage.getEditUserFunctionality().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() {return 
				mupage.getDatasetOptionInFunctionalityTabCheck().Displayed() ;}}),Input.wait60);
		mupage.getDatasetOptionInFunctionalityTabCheck().Click();
		mupage.getSaveBtnEditUser().Click();
		driver.waitForPageToBeReady();
		lp.logout();
		
		lp.loginToSightLineICE(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();
		dp = new ICE_DatasetsPage(driver);
		//Test Case No: 9455, Priority 2
		Assert.assertTrue(dp.getDatasetBtnLMenu()!=null);	
		lp.logout();
	

		lp.loginToSightLineICE(Input.sa1userName, Input.sa1password);
		driver.waitForPageToBeReady();
		rmuuser = mupage.getUserListbyUserName(Input.rmu1userName,Input.ICEProjectName);
		rmuuser.FindElementBycssSelector("td:nth-child(9) > a:nth-child(1)").Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
	    		mupage.getEditUserFunctionality().Displayed()  ;}}),Input.wait90);
		mupage.getEditUserFunctionality().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() {return 
				mupage.getDatasetOptionInFunctionalityTabCheck().Displayed() ;}}),Input.wait60);
		mupage.getDatasetOptionInFunctionalityTabCheck().Click();
		mupage.getSaveBtnEditUser().Click();
		driver.waitForPageToBeReady();
		lp.logout();
		
		
		lp.loginToSightLineICE(Input.pa1userName, Input.pa1password);
		driver.waitForPageToBeReady();
		dp = new ICE_DatasetsPage(driver);
		Assert.assertTrue(dp.getDatasetBtnLMenu()!=null);
		lp.logout();
		
		lp.loginToSightLineICE(Input.sa1userName, Input.sa1password);
		driver.waitForPageToBeReady();
		Element pauser = mupage.getUserListbyUserName(Input.rmu1userName,Input.ICEProjectName);
		pauser.FindElementBycssSelector("td:nth-child(9) > a:nth-child(1)").Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
	    		mupage.getEditUserFunctionality().Displayed()  ;}}),Input.wait90);
		mupage.getEditUserFunctionality().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() {return 
				mupage.getDatasetOptionInFunctionalityTabCheck().Displayed() ;}}),Input.wait60);
		mupage.getDatasetOptionInFunctionalityTabCheck().Click();
		mupage.getSaveBtnEditUser().Click();
		driver.waitForPageToBeReady();
		lp.logout();
		
		lp.loginToSightLineICE(Input.pa1userName, Input.pa1password);
		driver.waitForPageToBeReady();
		dp = new ICE_DatasetsPage(driver);
		//Test Case No: 9455, Priority 2
		Assert.assertTrue(dp.getDatasetBtnLMenu()==null);
		lp.logout();
	
		lp.loginToSightLineICE(Input.sa1userName, Input.sa1password);
		driver.waitForPageToBeReady();
		pauser = mupage.getUserListbyUserName(Input.rmu1userName,Input.ICEProjectName);
		pauser.FindElementBycssSelector("td:nth-child(9) > a:nth-child(1)").Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
	    		mupage.getEditUserFunctionality().Displayed()  ;}}),Input.wait60);
		mupage.getEditUserFunctionality().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() {return 
				mupage.getDatasetOptionInFunctionalityTabCheck().Displayed() ;}}),Input.wait60);
		mupage.getDatasetOptionInFunctionalityTabCheck().Click();
		mupage.getSaveBtnEditUser().Click();
		driver.waitForPageToBeReady();
		lp.logout();
   }
    
	@Test(groups={"regression"})
    public void BulkUserAccessControlHasDatasetOption()
    {
    	ICE_DatasetsPage dp;
    	final ManageUsersPage mupage ;
    	lp.loginToSightLineICE(Input.sa1userName, Input.sa1password);
		driver.waitForPageToBeReady();
		mupage = new ManageUsersPage(driver);
		mupage.getBulkUserAccessButton().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
	    		mupage.getBulkDataset().Displayed()  ;}}),Input.wait90);
		//Test Case No: 9467, Priority 2
		Assert.assertTrue(mupage.getBulkDataset().Displayed());
		mupage.getBulkCancelBtn().Click();
		try {
			mupage.BulkUserAccessDataset(Input.ICEProjectName);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		lp.logout();
		
		lp.loginToSightLineICE(Input.pa1userName, Input.pa1password);
		driver.waitForPageToBeReady();
		dp = new ICE_DatasetsPage(driver);
		Assert.assertTrue(dp.getDatasetBtnLMenu()==null);	
		lp.logout();
		
		lp.loginToSightLineICE(Input.sa1userName, Input.sa1password);
		driver.waitForPageToBeReady();
		mupage.getBulkCancelBtn().Click();
		try {
			mupage.BulkUserAccessDataset(Input.ICEProjectName);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		lp.logout();
		

		lp.loginToSightLineICE(Input.pa1userName, Input.pa1password);
		driver.waitForPageToBeReady();
		dp = new ICE_DatasetsPage(driver);
		//Test Case No: 9468, Priority 1
		Assert.assertTrue(dp.getDatasetBtnLMenu()!=null);	
		lp.logout();
		
		lp.loginToSightLineICE(Input.sa1userName, Input.sa1password);
		driver.waitForPageToBeReady();
		boolean isReview = false;
			try {
				isReview = mupage.isBulkUserAccessDatasetGreyedForReview(Input.ICEProjectName);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Test Case No: 9469, Priority 1
			Assert.assertTrue(isReview);
			lp.logout();
		
		
		
		
		
   }
	

	@Test(groups={"regression"})
	public void DragAndDropSAToPA() throws InterruptedException
	{
		String testFolderPath = System.getProperty("user.dir")+"\\ICETestData\\SmokeFolder";
		lp.loginToSightLineICE(Input.sa1userName, Input.sa1password);
		driver.waitForPageToBeReady();
		bc.impersonateSAtoPAICE();
		ICE_DatasetsPage dp=new ICE_DatasetsPage(driver);
		ICE_ManageUploadPage mup;
		dp.getDatasetBtnLMenu().Click();
		driver.waitForPageToBeReady();
		DatasetDetails dset = new DatasetDetails();
		dset.setDatasetName("Auto"+Utility.dynamicNameAppender());
		dset.setCustodianName("Dupe Test1");
		dset.setDescription(this.getClass().toString());
		dp.CreateNewUploadSet(dset);
		driver.waitForPageToBeReady();
		mup = new ICE_ManageUploadPage(driver, dset.getDatasetName());
		int fileUploadedCount = mup.uploadFilesByFolder(testFolderPath);
		int fileUploadCount = GenFunc.getFilesNamesByFolder(testFolderPath, new ArrayList<String>()).size();
		//Test Case No: 10819, Priority 2
		Assert.assertEquals(fileUploadCount, fileUploadedCount);
	}

	@Test(groups={"regression"})
	public void DragAndDropMultipleUsers() throws InterruptedException
	{
		String testFolderPath1 = System.getProperty("user.dir")+"\\ICETestData\\SmokeFolder";
		String testFolderPath2 = System.getProperty("user.dir")+"\\ICETestData\\DupeAllFiles";
		String testFolderPath3 = System.getProperty("user.dir")+"\\ICETestData\\ErrorFilesFolder";
		
		lp.loginToSightLineICE(Input.pa1userName, Input.pa1password);
		driver.waitForPageToBeReady();
		ICE_DatasetsPage dp=new ICE_DatasetsPage(driver);
		final ICE_ManageUploadPage mup;
		dp.getDatasetBtnLMenu().Click();
		driver.waitForPageToBeReady();
		DatasetDetails dset = new DatasetDetails();
		dset.setDatasetName("Auto"+Utility.dynamicNameAppender());
		dset.setCustodianName("Dupe Test1");
		dset.setDescription(this.getClass().toString());
		dp.CreateNewUploadSet(dset);
		driver.waitForPageToBeReady();
		mup = new ICE_ManageUploadPage(driver, dset.getDatasetName());
		int fileUploadedCount1 = mup.uploadFilesByFolder(testFolderPath1);
		int fileUploadCount1 = GenFunc.getFilesNamesByFolder(testFolderPath1, new ArrayList<String>()).size();
		//Test Case No: 10822, Priority 2
		Assert.assertEquals(fileUploadCount1, fileUploadedCount1);

		lp.logout();
		driver.waitForPageToBeReady();
		
		lp.loginToSightLineICE(Input.pa2userName, Input.pa2password);
		driver.waitForPageToBeReady();
		dp=new ICE_DatasetsPage(driver);
		dp.getDatasetBtnLMenu().Click();
		driver.waitForPageToBeReady();
		Element existingdataset =dp.getDatasetByName(dset.getDatasetName());
		dp.getDatasetNamelink(existingdataset).Click();
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				mup.getDropZoneLink().Displayed();}}),Input.wait90);
		int fileUploadedCount2 = mup.uploadFilesByFolder(testFolderPath2);
		int fileUploadCount2 = GenFunc.getFilesNamesByFolder(testFolderPath2, new ArrayList<String>()).size();
		//Test Case No: 10822, Priority 2
		Assert.assertEquals(fileUploadCount2, fileUploadedCount2);
		lp.logout();
		driver.waitForPageToBeReady();
		
		lp.loginToSightLineICE(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();
		dp=new ICE_DatasetsPage(driver);
		dp.getDatasetBtnLMenu().Click();
		driver.waitForPageToBeReady();
		Element existingdataset1 =dp.getDatasetByName(dset.getDatasetName());
		dp.getDatasetNamelink(existingdataset1).Click();
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				mup.getDropZoneLink().Displayed();}}),Input.wait90);
		int fileUploadedCount3 = mup.uploadFilesByFolder(testFolderPath2);
		int fileUploadCount3 = GenFunc.getFilesNamesByFolder(testFolderPath2, new ArrayList<String>()).size();
		//Test Case No: 10822, Priority 2
		Assert.assertEquals(fileUploadCount3, fileUploadedCount3);
		lp.logout();
		
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
		
		lp.logout();
	

 	}
 	 System.out.println("Executed :" + result.getMethod().getMethodName());
 	
     }
	
	@AfterClass(alwaysRun = true)
	public void close(){
	
	
		lp.logout();
		lp.quitBrowser();
	}




}
