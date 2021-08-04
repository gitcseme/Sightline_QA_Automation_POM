package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import SightlineObjects.DatasetDetails;
import automationLibrary.Driver;
import automationLibrary.GenFunc;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.ICE_DatasetProgressStatusPage;
import pageFactory.ICE_DatasetSummaryPage;
import pageFactory.ICE_DatasetsPage;
import pageFactory.ICE_ErrorNExceptionsPage;
import pageFactory.ICE_ManageUploadPage;
import junit.framework.Assert;
import pageFactory.LoginPage;
import pageFactory.ManageUsersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class ICE_Regression {
	
	Driver driver;
    LoginPage lp;
    SoftAssert sa = new SoftAssert();
    BaseClass bc ;
    
   @BeforeClass(alwaysRun = true)
     public void before() throws ParseException, InterruptedException, IOException {
    	System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
			
      		driver = new Driver();
    	    lp = new LoginPage(driver);
    	    bc = new BaseClass(driver);
    	     	    
	}

    
   //Test Case No: 10803(RPMXCON-50398)	
    @Test(groups={"regression"},priority=2)
  	public void PMUploadAndDeleteFile() throws InterruptedException
  	{
      	lp.loginToSightLineICE(Input.pa1userName, Input.pa1password);
  		ICE_DatasetsPage dp = new ICE_DatasetsPage(driver);
  		dp.getdatasetleftmenuBtn().waitAndClick(10);
  		driver.waitForPageToBeReady();
  		DatasetDetails testdd = new DatasetDetails();
  		testdd.setDatasetName("Auto"+Utility.dynamicNameAppender());
  		testdd.setCustodianName("Auto "+Utility.dynamicNameAppender());
  		testdd.setDescription(this.getClass().toString());
  		dp.CreateNewUploadSet(testdd);
  		driver.waitForPageToBeReady();
  		ICE_ManageUploadPage mup = new ICE_ManageUploadPage(driver, testdd.getDatasetName());
  		String testFolderPath = System.getProperty("user.dir")+"\\ICETestData\\Removefiles";
  		mup.uploadFilesByFolderReturnWithoutCompleting(testFolderPath);
  		
  	    //Test Case No: 10801(RPMXCON-50398), Priority 1
  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				mup.getremovelink().Enabled();}}),Input.wait90);
  		mup.getremovelink().waitAndClick(10);
  		mup.AcceptAlert();
  		bc.VerifySuccessMessage("Removed Uploaded file Successfully");
  		
  		mup.uploadFilesByFolderReturnWithoutCompleting(testFolderPath);
  		
  		 //Test Case No: 10802(RPMXCON-46948), Priority 1
  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				mup.getcancellink().Enabled();}}),Input.wait90);
  		mup.getcancellink().waitAndClick(10);
  		mup.AcceptAlert();
  		bc.VerifySuccessMessage("Removed Uploaded file Successfully");
  		
  	  	
        driver.scrollPageToTop();
  		dp.getdatasetleftmenuBtn().Click();
  		//mup.AcceptNavigation();
  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			dp.getDatasets().getFirstElement().Displayed();	}}),Input.wait90);
  		dp.DeleteUploadedDatasetByName(testdd.getDatasetName());
  		lp.logout();
  	}
  	
    //Test Case No: 10807(RPMXCON-50403),10804(RPMXCON-50400),9568(RPMXCON-47556), Priority 1
	@Test(groups={"regression"},priority=5)
	public void PAUDupeAllFiles() throws InterruptedException
	{
		String testFolderPath = System.getProperty("user.dir")+"\\ICETestData\\DupeAllFiles";//DupeAllFiles
		lp.loginToSightLineICE(Input.pa1userName, Input.pa1password);	
		ICE_DatasetsPage dp=new ICE_DatasetsPage(driver);
		ICE_ManageUploadPage mup;
		
		dp.getdatasetleftmenuBtn().waitAndClick(20);
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
		dp.getdatasetleftmenuBtn().waitAndClick(20);
		driver.waitForPageToBeReady();
		DatasetDetails dupeDset = new DatasetDetails();
		dupeDset.setDatasetName("Auto"+Utility.dynamicNameAppender());
		dupeDset.setCustodianName("Dupe Test2");
		dupeDset.setDescription(this.getClass().toString());
		dp.CreateNewUploadSet(dupeDset);
		driver.waitForPageToBeReady();
		mup = new ICE_ManageUploadPage(driver, dupeDset.getDatasetName());
		mup.uploadFilesByFolder(testFolderPath);
		
		 final BaseClass bc = new BaseClass(driver);
	     final int Bgcount = bc.initialBgCount();
		mup.InitiateProcessing();
		ICE_DatasetProgressStatusPage dpdp1 = new ICE_DatasetProgressStatusPage(driver,dupeDset.getDatasetName(),true);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				dpdp.getProcessingStatus().equalsIgnoreCase("PUBLISHCOMPLETE"); }}),1800000);
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			bc.initialBgCount() == Bgcount+1  ;}}), 200000); 
		
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call() {return
				 bc.getBackgroundTask_Button().Visible();}}), Input.wait60);
			bc.getBackgroundTask_Button().Click();
			Thread.sleep(3000);
			
		String dataname=	bc.getBckTask_selecttask().getText();
		System.out.println(dataname);
		//Test Case No: 9568, Priority 1
		Assert.assertTrue(bc.getBckTask_selecttask().getText().contains(dupeDset.getDatasetName()));
		driver.waitForPageToBeReady();
		
		ICE_DatasetSummaryPage dsp = new ICE_DatasetSummaryPage(driver, dset.getDatasetName());
		sa.assertTrue(dsp.getPageTitle().getText().contains("Dataset Summary: "+dset.getDatasetName()));
		
		dp.getdatasetleftmenuBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
			
		dp.viewSetInDocListByName(dset.getDatasetName(),true);
		DocListPage dlpage= new DocListPage(driver);
		String coltext = dlpage.getrowColumnText(1, 8).getText();
		System.out.println(coltext);
		
		Assert.assertTrue(coltext.contains(dupeDset.getCustodianName()));
		
		lp.logout();	
		
	}
	
	@Test(groups={"regression"},priority=7)
	public void PAExceptionFiles() throws Exception
	{
		
		//Start to create a dataset for error documents, where we have some survival document.
		bc = new BaseClass(driver);
		String testFolderPath = System.getProperty("user.dir")+"\\ICETestData\\Errordoc";
		lp.loginToSightLineICE(Input.pa1userName, Input.pa1password);	
		ICE_DatasetsPage dp=new ICE_DatasetsPage(driver);
		ICE_ManageUploadPage mup;
		dp.getdatasetleftmenuBtn().waitAndClick(20);
		driver.waitForPageToBeReady();
		DatasetDetails testdd = new DatasetDetails();

		String dname ="Auto" + Utility.dynamicNameAppender();
		String dcustodian ="Auto" + Utility.dynamicNameAppender();
		String ddisc ="Auto test for dataset" + Utility.dynamicNameAppender();
		dp.setdatasetdetails(dname,dcustodian, ddisc);
		driver.waitForPageToBeReady();
		
		dp.getdatasetleftmenuBtn().waitAndClick(30);
		mup = new ICE_ManageUploadPage(driver, testdd.getDatasetName());
		mup.uploadFilesByFolder(testFolderPath);
		mup.InitiateProcessing();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					driver.FindElementByCssSelector("#lblProjectTitle").Visible(); }}),Input.wait90);
			ICE_DatasetProgressStatusPage dpdp = new ICE_DatasetProgressStatusPage(driver,testdd.getDatasetName(),false);
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					dpdp.getProcessingStatus().equalsIgnoreCase("PUBLISHCOMPLETE"); }}),1800000);
			driver.waitForPageToBeReady();
	
		//Here we start to verify accessing error page from Manage dataset tile.
		dp.getdatasetleftmenuBtn().Click();
		driver.waitForPageToBeReady();
		
		//tc - 10794(RPMXCON-50391),
		dp.getDatasetByName(dname);
		int ErrorCount= GenFunc.StringToInt(dp.geterrorcount().getText());
				
		//click on dataset name link
		dp.getdatasetnamelink(dname).waitAndClick(20);
		driver.waitForPageToBeReady();
		
		ICE_DatasetSummaryPage summarypage = new ICE_DatasetSummaryPage(driver,testdd.getDatasetName());
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				summarypage.getExcludeFilesLink().Displayed(); }}),Input.wait90);
		
		int eCountSumamryExcluded =GenFunc.StringToInt(summarypage.getExcludeFilesLink().getText());
		int eCountSumamryIncluded =GenFunc.StringToInt(summarypage.getIncludeErrorsLink().getText());
		System.out.println(eCountSumamryExcluded  +"-----"+eCountSumamryIncluded );
		//tc id - 10795,10796
		sa.assertTrue(ErrorCount == eCountSumamryExcluded+eCountSumamryIncluded);
		sa.assertTrue(ErrorCount == eCountSumamryExcluded);
		
		summarypage.getIncludeErrorsLink().waitAndClick(10);
		driver.waitForPageToBeReady();
		ICE_ErrorNExceptionsPage eep = new ICE_ErrorNExceptionsPage(driver,testdd.getDatasetName());
		String pagetitle = eep.getErrorNExceptionPage().getText();
		
		sa.assertEquals("Errors & Exceptions", pagetitle);
		
		eep.getIncludedDocsWithErrorsTab().waitAndClick(10);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				eep.getIncludedDocsWithErrors().Displayed(); }}),Input.wait90);
		int expcount = GenFunc.StringToInt(eep.getIncludedDocsWithErrors().getText());
		System.out.println(expcount);
		
		//tc - 10795(RPMXCON-50392),10796(RPMXCON-50393),
		sa.assertEquals(expcount, ErrorCount);
		
		sa.assertEquals(expcount, eCountSumamryExcluded);
	}
	
	

	@Test(groups={"regression"},priority=1)
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
		//Test Case No: 9454(RPMXCON-47506), Priority 2
		sa.assertTrue(mupage.getDatasetOptionInFunctionalityTabCheck().Displayed());
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
		Assert.assertTrue(mupage.getDatasetOptionInFunctionalityTabCheck().Displayed());
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
		Assert.assertTrue(mupage.getDatasetOptionInFunctionalityTabCheck().Displayed());
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
		Assert.assertTrue(mupage.getDatasetOptionInFunctionalityTabCheck().Displayed());
		mupage.getEditUserCancelBtn().Click();
		driver.waitForPageToBeReady();

				mupage.getUserList(Input.rev1userName);
		mupage.editSAUserBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
	    		mupage.getEditUserFunctionality().Displayed()  ;}}),Input.wait90);
		mupage.getEditUserFunctionality().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() {return 
				mupage.getDatasetOptionInFunctionalityTabCheck().Displayed() ;}}),Input.wait90);
		//Test Case No: 9458(RPMXCON-47509), Priority 2
		Assert.assertTrue(mupage.getDatasetOptionInFunctionalityTabCheck().GetAttribute("class").equalsIgnoreCase("BackgroundGrey"));
		mupage.getEditUserCancelBtn().Click();
		driver.waitForPageToBeReady();

		lp.logout();	
	}

	@Test(groups={"regression"},priority=3)
    public void datasetOptionDisplayedIfChecked()
    {
		final ManageUsersPage mupage;
		ICE_DatasetsPage dp;
		
		//login as system admin and select dataset option if not checked
		lp.loginToSightLineICE(Input.sa1userName, Input.sa1password);
		driver.waitForPageToBeReady();
		mupage=new ManageUsersPage(driver);
		
		mupage.selectdatasetoption(Input.rmu1userName);
		mupage.selectdatasetoption(Input.pa1userName);
		lp.logout();
		
		//login as rmu user and validate dataset menu	
		lp.loginToSightLineICE(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();
		dp = new ICE_DatasetsPage(driver);
		//Test Case No: 9455(RPMXCON-47507), Priority 2
		Assert.assertTrue(dp.getdatasetleftmenuBtn().Displayed());
		lp.logout();
	

		//login as pa user and validate dataset menu	
		lp.loginToSightLineICE(Input.pa1userName, Input.pa1password);
		driver.waitForPageToBeReady();
		dp = new ICE_DatasetsPage(driver);
		Assert.assertTrue(dp.getdatasetleftmenuBtn().Displayed());
		lp.logout();
		
		lp.loginToSightLineICE(Input.sa1userName, Input.sa1password);
		driver.waitForPageToBeReady();
		
		mupage.Deselectdatasetoption(Input.rmu1userName);
		mupage.Deselectdatasetoption(Input.pa1userName);
		lp.logout();
		
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		driver.waitForPageToBeReady();
		dp = new ICE_DatasetsPage(driver);
		//Test Case No: 9455, Priority 2
		Assert.assertTrue(dp.getDatasetBtnLMenu()==null);
		lp.logout();
	
		lp.loginToSightLineICE(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();
		Assert.assertTrue(dp.getDatasetBtnLMenu()==null);
		lp.logout();
   }
    
	@Test(groups={"regression"},priority=4)
    public void BulkUserAccessControlHasDatasetOption() throws InterruptedException
    {
    	ICE_DatasetsPage dp;
    	final ManageUsersPage mupage ;
    	lp.loginToSightLineICE(Input.sa1userName, Input.sa1password);
		driver.waitForPageToBeReady();
		mupage = new ManageUsersPage(driver);
		mupage.getBulkUserAccessButton().waitAndClick(30);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() throws Exception{return 
	    		mupage.getBulkDataset().Displayed()  ;}}),Input.wait90);
		
		//Test Case No: 9467, Priority 2
		//Test Case No: 9468, Priority 1
		Assert.assertTrue(mupage.getBulkDataset().Displayed());
		mupage.getBulkCancelBtn().waitAndClick(10);
	
		mupage.BulkUserAccessDataset("Project Administrator",Input.pa1FullName,"disable");
		
		
		mupage.BulkUserAccessDataset("Review Manager",Input.rmu1FullName,"disable");
		driver.waitForPageToBeReady();
	
		lp.logout();
		
		lp.loginToSightLineICE(Input.pa1userName, Input.pa1password);
		driver.waitForPageToBeReady();
		dp = new ICE_DatasetsPage(driver);
		Assert.assertTrue(dp.getDatasetBtnLMenu()==null);	
		lp.logout();
		
		lp.loginToSightLineICE(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();
		dp = new ICE_DatasetsPage(driver);
		Assert.assertTrue(dp.getDatasetBtnLMenu()==null);	
		lp.logout();
		
		lp.loginToSightLineICE(Input.sa1userName, Input.sa1password);
		driver.waitForPageToBeReady();
			
		mupage.BulkUserAccessDataset("Project Administrator",Input.pa1FullName,"enable");
     
		mupage.BulkUserAccessDataset("Review Manager",Input.rmu1FullName,"enable");
		driver.waitForPageToBeReady();
		
		lp.logout();
		

		lp.loginToSightLineICE(Input.pa1userName, Input.pa1password);
		driver.waitForPageToBeReady();
		dp = new ICE_DatasetsPage(driver);
		
		Assert.assertTrue(dp.getdatasetleftmenuBtn().Displayed());	
		lp.logout();
		
		lp.loginToSightLineICE(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();
		dp = new ICE_DatasetsPage(driver);
		//Test Case No: 9468, Priority 1
		Assert.assertTrue(dp.getdatasetleftmenuBtn().Displayed());	
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
 
    //Test Case No: 10819(RPMXCON-50113), Priority 2

	@Test(groups={"regression"},priority=8)
	public void DragAndDropSAToPA() throws InterruptedException
	{
		String testFolderPath = System.getProperty("user.dir")+"\\ICETestData\\SmokeFolder";
		lp.loginToSightLineICE(Input.sa1userName, Input.sa1password);
		driver.waitForPageToBeReady();
		bc.impersonateSAtoPAICE();
		ICE_DatasetsPage dp=new ICE_DatasetsPage(driver);
		ICE_ManageUploadPage mup;
		dp.getdatasetleftmenuBtn().waitAndClick(20);
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
		
		Assert.assertEquals(fileUploadCount, fileUploadedCount);
	}

	//Test Case No: 10822(RPMXCON-50115), Priority 2
	@Test(groups={"regression"},priority=6)
	public void DragAndDropMultipleUsers() throws InterruptedException
	{
		String testFolderPath1 = System.getProperty("user.dir")+"\\ICETestData\\SmokeFolder";
		String testFolderPath2 = System.getProperty("user.dir")+"\\ICETestData\\DupeAllFiles";
		
		
		lp.loginToSightLineICE(Input.pa1userName, Input.pa1password);
		driver.waitForPageToBeReady();
		ICE_DatasetsPage dp=new ICE_DatasetsPage(driver);
		final ICE_ManageUploadPage mup;
		dp.getdatasetleftmenuBtn().waitAndClick(20);
		driver.waitForPageToBeReady();
		DatasetDetails dset = new DatasetDetails();
		dset.setDatasetName("Auto"+Utility.dynamicNameAppender());
		dset.setCustodianName("Dupe Test1");
		dset.setDescription(this.getClass().toString());
		dp.CreateNewUploadSet(dset);
		driver.waitForPageToBeReady();
		mup = new ICE_ManageUploadPage(driver, dset.getDatasetName());
		int fileUploadedCount1 = mup.uploadFilesByFolder(testFolderPath1);
		System.out.println(fileUploadedCount1);
		int fileUploadCount1 = GenFunc.getFilesNamesByFolder(testFolderPath1, new ArrayList<String>()).size();
		System.out.println(fileUploadCount1);
		
		Assert.assertEquals(fileUploadCount1, fileUploadedCount1);

		lp.logout();
		driver.waitForPageToBeReady();
		
		
		lp.loginToSightLineICE(Input.pa2userName, Input.pa2password);
		driver.waitForPageToBeReady();
		
		dp=new ICE_DatasetsPage(driver);
		dp.getdatasetleftmenuBtn().waitAndClick(20);
		driver.waitForPageToBeReady();
		
		dp.getDatasetByName(dset.getDatasetName());
		//Element existingdataset =dp.getDatasetByName(dset.getDatasetName());
		dp.getdatasetnamelink(dset.getDatasetName()).waitAndClick(10);
		
		bc.getNOBtn().waitAndClick(30);
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				mup.getDropZoneLink().Displayed();}}),Input.wait90);
		
		
		int fileCountBeforeUpload = mup.getUploadCount();
		System.out.println(fileCountBeforeUpload);
		String filecount = mup.getUploadfileCount().GetAttribute("data-filecount");
		Assert.assertTrue(filecount.contains(Integer.toString(fileUploadedCount1)));
		
		int fileUploadedCount2 = mup.uploadFilesByFolder(testFolderPath2);
		int fileUploadCount2 = GenFunc.getFilesNamesByFolder(testFolderPath2, new ArrayList<String>()).size();
		
		System.out.println(fileUploadedCount2  +"----------"+fileUploadCount2);
		

		Assert.assertEquals(fileUploadCount2, fileUploadedCount2);
		lp.logout();
		driver.waitForPageToBeReady();
		
		lp.loginToSightLineICE(Input.rmu1userName, Input.rmu1password);
		driver.waitForPageToBeReady();
		dp=new ICE_DatasetsPage(driver);
		dp.getdatasetleftmenuBtn().waitAndClick(20);
		driver.waitForPageToBeReady();
	
		DatasetDetails dset1 = new DatasetDetails();
		dset1.setDatasetName("Auto"+Utility.dynamicNameAppender());
		dset1.setCustodianName("Auto"+Utility.dynamicNameAppender());
		dset1.setDescription(this.getClass().toString());
		dp.CreateNewUploadSet(dset1);
		driver.waitForPageToBeReady();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				mup.getDropZoneLink().Displayed();}}),Input.wait90);
		int fileUploadedCount3 = mup.uploadFilesByFolder(testFolderPath2);
		int fileUploadCount3 = GenFunc.getFilesNamesByFolder(testFolderPath2, new ArrayList<String>()).size();
		
		Assert.assertEquals(fileUploadCount3, fileUploadedCount3);
		lp.logout();
		
		lp.loginToSightLineICE(Input.pa1userName, Input.pa1password);
		driver.waitForPageToBeReady();
		
		dp=new ICE_DatasetsPage(driver);
		dp.getdatasetleftmenuBtn().waitAndClick(20);
		driver.waitForPageToBeReady();
		
		dp.getDatasetByName(dset1.getDatasetName());
		dp.getdatasetnamelink(dset1.getDatasetName()).waitAndClick(10);
		
		bc.getNOBtn().waitAndClick(30);
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				mup.getDropZoneLink().Displayed();}}),Input.wait90);
		
		
		int fileCountBeforeUpload1 = mup.getUploadCount();
		System.out.println(fileCountBeforeUpload1);
		String filecount1 = mup.getUploadfileCount().GetAttribute("data-filecount");
		Assert.assertTrue(filecount1.contains(Integer.toString(fileCountBeforeUpload1)));
		
		int fileUploadedCount4 = mup.uploadFilesByFolder(testFolderPath2);
		int fileUploadCount4= GenFunc.getFilesNamesByFolder(testFolderPath2, new ArrayList<String>()).size();
		//Test Case No: 10822, Priority 2
		System.out.println(fileUploadedCount4  +"----------"+fileUploadCount4);
		

		Assert.assertEquals(fileUploadCount4, fileUploadedCount4);
		lp.logout();
		driver.waitForPageToBeReady();
		
		
		
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
	
	
		lp.quitBrowser();
	}




}
