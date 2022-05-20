package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.IngestionPage_Indium;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class IngestionCreationClass02 {
	
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	IngestionPage_Indium ingestionPage;
	Input ip;
	SoftAssert softAssertion;

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		ip = new Input();
		ip.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);

		// Login as a PA
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
	}
	
	
	/**
	 * Author: Mohan date: 02/05/2022 Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-49521 Description :Verify that if PA ingested both TEXT and TIFF's
	 * file,the "Generate Searchable PDFs"is true and TIFF is missing then it should
	 * displays default PDF file
	 * 
	 * @throws InterruptedException
	 */
    @Test(enabled = true, groups = { "regression" }, priority = 42)
	public void verifyTEXTAndTIFFSFileGenerateSearchablePDFsIsTrue() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		String ingestionType="Add Only";
		baseClass.stepInfo("Test case Id: RPMXCON-49521");
		baseClass.stepInfo(
				"Verify that if PA ingested both TEXT and TIFF's file,the 'Generate Searchable PDFs' is true and TIFF is missing then it should displays default PDF file");
		boolean status = ingestionPage.verifyIngestionpublish(Input.PP_PDFGen_10Docs);
		System.out.println(status);

		if (status == false) {

			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
			
		}
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicSearchWithMetaDataQuery(Input.sourceDocIDPPPDF10Docs, "SourceDocID");
		sessionSearch.viewInDocView();
		DocViewPage docViewPage = new DocViewPage(driver);

		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_TextTab());
		docViewPage.getDocView_TextTab().waitAndClick(5);
		String text = docViewPage.getDocViewDefaultViewText().getText();
		if (text.contains("There is no file")) {
			baseClass.passedStep(
					"In text tab it displayed as 'There is no file associated with this document on text view'");
		} else {
			baseClass.failedStep("There is no such message");
		}

		loginPage.logout();

	}

	/**
	 * Author: Vijaya.Rani date: 02/05/2022 Modified date: NA Modified by: NA
	 * Description :Verify when user ingest only metadata, message like 'No files
	 * associated with this document' should be displayed on
	 * text/Images/Translations view.'RPMXCON-51236'
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 43)
	public void verifyIngestMetaDataMessageDisplayTEXTFile() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		String ingestionType="Add Only";
		baseClass.stepInfo("Test case Id: RPMXCON-51236");
		baseClass.stepInfo(
				"Verify when user ingest only metadata, message like 'No files associated with this document' should be displayed on text/Images/Translations view");

		boolean status = ingestionPage.verifyIngestionpublish(Input.PP_PDFGen_10Docs);
		System.out.println(status);

		if (status == false) {
			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs, "select",null, null, null);
		}

		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("step 1:Search Metadata SourceDocID go to docView ");
		sessionSearch.basicSearchWithMetaDataQuery(Input.sourceDocIDPPPDF10Docs, "SourceDocID");
		sessionSearch.viewInDocView();
		DocViewPage docViewPage = new DocViewPage(driver);

		driver.waitForPageToBeReady();
		baseClass.stepInfo("step 2:Select Text Tab in Docview");
		baseClass.waitForElement(docViewPage.getDocView_TextTab());
		docViewPage.getDocView_TextTab().waitAndClick(5);
		String text = docViewPage.getDocViewDefaultViewText().getText();
		if (text.contains("There is no file")) {
			baseClass.passedStep(
					"In text tab it displayed as 'There is no file associated with this document on text view'");
		} else {
			baseClass.failedStep("There is no such message");
		}

		loginPage.logout();

	}

	/**
	 * Author: Vijaya.Rani date: 02/05/2022 Modified date: NA Modified by: NA
	 * Description :Verify when user ingest only metadata, 'No files associated with
	 * this document' message should be displayed on default view.'RPMXCON-51235'
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 44)
	public void verifyIngestMetaDataMessageDisplayDefaultAndTEXTFile() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51235");
		baseClass.stepInfo(
				"Verify when user ingest only metadata, 'No files associated with this document' message should be displayed on default view");

		String ingestionType="Add Only";
		boolean status = ingestionPage.verifyIngestionpublish(Input.PP_PDFGen_10Docs);
		System.out.println(status);

		if (status == false) {
			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}


		
		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("step 1:Search Metadata SourceDocID go to docView ");
		sessionSearch.basicSearchWithMetaDataQuery(Input.sourceDocIDPPPDF10Docs, "SourceDocID");
		sessionSearch.viewInDocView();
		
		baseClass.stepInfo("step 2:Select Default PDF Tab in Docview");
		baseClass.waitForElement(docViewPage.getDocView_DefaultViewTab());
		docViewPage.getDocView_DefaultViewTab().waitAndClick(5);
		if (docViewPage.getDocview_DefaultTextArea().Displayed()) {
			baseClass.passedStep(
					"In default tab it displayed as 'This record does not contain a PDF rendering,A TIFF rendering,TEXT or a supported native file type.In order to view This document,you must download the native and view it locally.'");
			baseClass.passedStep(
					"In default tab it displayed as 'Alternatively,contact your review manager who can engage with the data processing team to see if a PDF rendering of the document can be created so it is viewable here.'");
		} else {
			baseClass.failedMessage("There is no such message");
		}

		// TEXT tab Verification
		driver.waitForPageToBeReady();
		baseClass.stepInfo("step 3:Select Text Tab in Docview");
		baseClass.waitForElement(docViewPage.getDocView_TextTab());
		docViewPage.getDocView_TextTab().waitAndClick(5);
		String text = docViewPage.getDocViewDefaultViewText().getText();
		if (text.contains("There is no file")) {
			baseClass.passedStep(
					"In text tab it displayed as 'There is no file associated with this document on text view'");
		} else {
			baseClass.failedStep("There is no such message");
		}
		loginPage.logout();

	}

	/**
	 * Author: Vijaya.Rani date: 02/05/2022 Modified date: NA Modified by: NA
	 * Description :Verify when user ingest only metadata, error PDF should be
	 * displayed on Images view.'RPMXCON-51237'
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 45)
	public void verifyIngestMetaDataMessageDisplayIMAGEAndTEXTFile() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51237");
		baseClass.stepInfo("Verify when user ingest only metadata, error PDF should be displayed on Images view");

		String ingestionType="Add Only";
		boolean status = ingestionPage.verifyIngestionpublish(Input.PP_PDFGen_10Docs);
		System.out.println(status);

		if (status == false) {
			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}

		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("step 1:Search Metadata SourceDocID go to docView ");
		sessionSearch.basicSearchWithMetaDataQuery(Input.sourceDocIDPPPDF10Docs, "SourceDocID");
		sessionSearch.viewInDocView();
		DocViewPage docViewPage = new DocViewPage(driver);
		// DEFAULTView Tab Verification
		driver.waitForPageToBeReady();
		baseClass.stepInfo("step 2:Select Default PDF Tab IN Docview");
		baseClass.waitForElement(docViewPage.getDocView_DefaultViewTab());
		docViewPage.getDocView_DefaultViewTab().waitAndClick(5);
		if (docViewPage.getDocview_DefaultTextArea().Displayed()) {
			baseClass.passedStep(
					"In default tab it displayed as 'This record does not contain a PDF rendering,A TIFF rendering,TEXT or a supported native file type.In order to view This document,you must download the native and view it locally.'");
			baseClass.passedStep(
					"In default tab it displayed as 'Alternatively,contact your review manager who can engage with the data processing team to see if a PDF rendering of the document can be created so it is viewable here.'");
		} else {
			baseClass.failedMessage("There is no such message");
		}

		// TEXT tab Verification
		driver.waitForPageToBeReady();
		baseClass.stepInfo("step 3:Select Text Tab in Docview");
		baseClass.waitForElement(docViewPage.getDocView_TextTab());
		docViewPage.getDocView_TextTab().waitAndClick(5);
		String text2 = docViewPage.getDocViewDefaultViewText().getText();
		if (text2.contains("There is no file")) {
			baseClass.passedStep(
					"In text tab it displayed as 'There is no file associated with this document on text view'");
		} else {
			baseClass.failedStep("There is no such message");
		}
		loginPage.logout();
	}

	/**
	 * Author: Vijaya.Rani date: 04/05/2022 Modified date: NA Modified by: NA
	 * Description :Verify when user ingest only dat file, only metadata should get
	 * ingested.'RPMXCON-51238'
	 * 
	 * @throws InterruptedException
	 */
	@Test(enabled = true, groups = { "regression" }, priority = 46)
	public void verifyIngestMetaDataDATFileIsIngested() throws InterruptedException {

		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-51238");
		baseClass.stepInfo("Verify when user ingest only dat file, only metadata should get ingested.");

		String ingestionType="Add Only";
		boolean status = ingestionPage.verifyIngestionpublish(Input.PP_PDFGen_10Docs);
		System.out.println(status);

		if (status == false) {
			ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, "TRUE", Input.DATPPPDF10Docs, null,
					Input.TextPPPDF10Docs, null, Input.ImagePPPDF10docs,"select", null, null, null);
		}

		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("step 1:Search Metadata SourceDocID go to docView ");
		sessionSearch.basicSearchWithMetaDataQuery(Input.sourceDocIDPPPDF10Docs, "SourceDocID");
		sessionSearch.viewInDocView();
		DocViewPage docViewPage = new DocViewPage(driver);
		// DEFAULTView Tab Verification
		driver.waitForPageToBeReady();
		baseClass.stepInfo("step 2:Select Default PDF Tab in Docview");
		baseClass.waitForElement(docViewPage.getDocView_DefaultViewTab());
		docViewPage.getDocView_DefaultViewTab().waitAndClick(5);
		if (docViewPage.getDocview_DefaultTextArea().Displayed()) {
			baseClass.passedStep(
					"In default tab it displayed as 'This record does not contain a PDF rendering,A TIFF rendering,TEXT or a supported native file type.In order to view This document,you must download the native and view it locally.'");
			baseClass.passedStep(
					"In default tab it displayed as 'Alternatively,contact your review manager who can engage with the data processing team to see if a PDF rendering of the document can be created so it is viewable here.'");
		} else {
			baseClass.failedMessage("There is no such message");
		}
		loginPage.logout();
	}

	
	/** 
     *Author: Mohan date: 02/05/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49520
	 * Description :Verify that if PA ingested both PDF and TIFF's file,the "Generate Searchable PDFs"is true and TIFF is missing then it PDF should displays PDF in viewer
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 47)
	public void verifyPDFAndTIFFSFileGenerateSearchablePDFsIsTrue() throws InterruptedException  {
		ingestionPage = new IngestionPage_Indium(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49520");
		baseClass.stepInfo("Verify that if PA ingested both PDF and TIFF's file,the 'Generate Searchable PDFs' is true and TIFF is missing then it PDF should displays PDF in viewer");
		String ingestionType="Overlay Only";
		
		ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, null, Input.DATPPPDF10Docs, null,
					null, "PDFs - 5Docs.lst", Input.ImagePPPDF10docs,"Select", null, null, null);
		ingestionPage.navigateToIngestionPage();
		
		driver.Navigate().refresh();
		String ingestionName = ingestionPage.selectPublishedFromFilterDropDown(Input.PP_PDFGen_10Docs);
		System.out.println(ingestionName);
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicSearchWithMetaDataQuery(ingestionName, "IngestionName");
		sessionSearch.viewInDocView();
		DocViewPage docViewPage = new DocViewPage(driver);

		driver.waitForPageToBeReady();
		docViewPage.selectDocIdInMiniDocList(Input.sourceDocIDPPPDF10Docs);
		baseClass.waitForElement(docViewPage.getDocView_DefaultViewTab());
		docViewPage.getDocView_DefaultViewTab().waitAndClick(5);
		String text = docViewPage.getDocViewDefaultViewPDF().getText();
		if (text.contains("PDF")) {
			baseClass.passedStep(
					"PDF is displays in PDF viewer");
		} else {
			baseClass.failedStep("There is no such message");
		}

		loginPage.logout();
		

	}
	
	/** 
     *Author: Mohan date: 06/05/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49519
	 * Description :Verify that if PA ingested both Native and TIFF's file, the "Generate Searchable PDFs"is true and TIFF is missing then searchable PDF's should be generated from the Natives.
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 48)
	public void verifyNativeAndTIFFSFileGenerateSearchablePDFsIsTrue() throws InterruptedException  {
		
	
		ingestionPage = new IngestionPage_Indium(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-49519");
		baseClass.stepInfo("Verify that if PA ingested both Native and TIFF's file, the 'Generate Searchable PDFs' is true and TIFF is missing then searchable PDF's should be generated from the Natives.");
		String ingestionType="Overlay Only";
		
		ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, null, Input.DATPPPDF10Docs, "Natives -5Docs.lst",
					null,null , Input.ImagePPPDF10docs,"Select", null, null, null);
		ingestionPage.navigateToIngestionPage();
		
		driver.Navigate().refresh();
		String ingestionName = ingestionPage.selectPublishedFromFilterDropDown(Input.PP_PDFGen_10Docs);
		System.out.println(ingestionName);
		baseClass.waitForElement(ingestionPage.firstTileTitle());
		ingestionPage.firstTileTitle().waitAndClick(5);
		
		driver.scrollingToElementofAPage(ingestionPage.getIngestion_CopyingTableValue(1, 5));
		
		String text = ingestionPage.getIngestion_CopyingTableValue(1, 5).getText();
		System.out.println(text);
		String text2 = ingestionPage.getIngestion_CopyingTableValue(9, 5).getText();
		System.out.println(text2);
		
		if (text.equalsIgnoreCase(text2)) {
			baseClass.passedStep(
					"Searchable PDF is been generated from the Natives");
		} else {
			baseClass.failedStep("There is no such message");
		}

		loginPage.logout();
	}
	
	/** 
     *Author: Mohan date: 06/05/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-50377
	 * Description :Verify that if PA ingested Native, PDF and TIFF's file and the "Generate Searchable PDFs" option is set to true, then PDF should be generated from the TIFF's
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 49)
	public void verifyNativePDFAndTIFFSFileGenerateSearchablePDFsIsTrue() throws InterruptedException  {
		
	
		ingestionPage = new IngestionPage_Indium(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-50377");
		baseClass.stepInfo("Verify that if PA ingested Native, PDF and TIFF's file and the 'Generate Searchable PDFs' option is set to true, then PDF should be generated from the TIFF's");
		String ingestionType="Add Only";
		boolean status = ingestionPage.verifyIngestionpublishWithAdditionalOptions(Input.PP_PDFGen_10Docs,"50");
		System.out.println(status);

		if(status==false) {
		ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, "TRUE", "DAT4_STC_09172014_newdateformat_50Docs.dat", "Natives_50Docs.lst",
					null,"PDFs_50Docs.lst" , "Images.lst","Select", null, null, null);
		}
		
			driver.Navigate().refresh();
			String ingestionFullName = ingestionPage.isDataSetisAvailable(Input.PP_PDFGen_10Docs,"50");
		if (ingestionFullName != null) {
			ingestionPage.selectDataSetWithNameInDocView(Input.PP_PDFGen_10Docs);
			String name = docViewPage.getDefaultViewerFileType().GetAttribute("xlink:href");
			System.out.println(name);
			if (name.contains("image")) {
				baseClass.passedStep("Tiff file only displayed in default viewer");
			} else {
				baseClass.failedStep("verification failed");
			}
		}

			loginPage.logout();
	}
	
	/** 
     *Author: Mohan date: 06/05/2022 Modified date: NA Modified by: NA Test Case Id:RPMXCON-49509
	 * Description :Verify that if PA ingested both native's and TIFF's file,and the "Generate Searchable PDFs" option is set to false then it should display TIFF in default viewer
     * @throws InterruptedException 
	 */
	@Test(enabled = true,  groups = {"regression" },priority = 50)
	public void verifyNativeAndTIFFSFileGenerateSearchablePDFsIsFalse() throws InterruptedException  {
		
		ingestionPage = new IngestionPage_Indium(driver);
		DocViewPage docViewPage = new DocViewPage(driver);
		baseClass.stepInfo("Test case Id: RPMXCON-49509");
		baseClass.stepInfo("Verify that if PA ingested both native's and TIFF's file,and the \"Generate Searchable PDFs\" option is set to false then it should display TIFF in default viewer");
		String ingestionType="Overlay Only";
		
		ingestionPage.IngestionRegressionForDifferentDAT(Input.PP_PDFGen_10Docs,ingestionType, null, Input.DATPPPDF10Docs, "Natives -5Docs.lst",
					null,null , Input.ImagePPPDF10docs,null, null, null, null);
		ingestionPage.navigateToIngestionPage();
		
		driver.Navigate().refresh();
		driver.Navigate().refresh();
		String ingestionFullName = ingestionPage.isDataSetisAvailable(Input.PP_PDFGen_10Docs,"5");
	if (ingestionFullName != null) {
		ingestionPage.selectDataSetWithNameInDocView(Input.PP_PDFGen_10Docs);
		String name = docViewPage.getDefaultViewerFileType().GetAttribute("xlink:href");
		System.out.println(name);
		if (name.contains("image")) {
			baseClass.passedStep("Application displays 'TIFF file' in default viewer");
		} else {
			baseClass.failedStep("verification failed");
		}
	}

		loginPage.logout();
	}
	
	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
	baseClass = new BaseClass(driver);
	loginPage = new LoginPage(driver);
	Reporter.setCurrentTestResult(result);
	if (ITestResult.FAILURE == result.getStatus()) {
	Utility baseClass = new Utility(driver);
	baseClass.screenShot(result);
	}
	loginPage.quitBrowser();

	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR INGESTION EXECUTED******");

	}

}
