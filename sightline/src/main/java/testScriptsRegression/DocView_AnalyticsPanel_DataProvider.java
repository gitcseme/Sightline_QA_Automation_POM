package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.Color;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_AnalyticsPanel_DataProvider {
	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	MiniDocListPage miniDocListpage;
	SessionSearch sessionSearch;
	SoftAssert softAssertion;
	DocViewPage docViewPage;
	AssignmentsPage assignmentPage;
	TagsAndFoldersPage tagsAndFolderPage;
	ReusableDocViewPage reusableDocViewPage;
	SavedSearch savedSearch;
	String assignmentNameToChoose;


	//
	@BeforeClass(alwaysRun = true)
	private void TestStart() throws Exception, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
	// Open browser
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("Executing method : " + testMethod.getName());
		UtilityLog.info("Executing method : " + testMethod.getName());
	}

	/**
	 * Author : Raghuram A date: 8/18/21 NA Modified date: NA Modified by: N/A
	 * Description : DataProvider for Different User Login
	 */
	@DataProvider(name = "userDetailss")
	public Object[][] userLoginSaPaRmu() {
		return new Object[][] { 
			{ "rmu", Input.rmu1userName, Input.rmu1password,"rev" },
				{ "sa", Input.sa1userName, Input.sa1password,"rmu" },
				{  "sa", Input.sa1userName, Input.sa1password,"rev"  }, 
				{  "pa", Input.pa1userName, Input.pa1password,"rmu"  } ,
				{  "pa", Input.pa1userName, Input.pa1password,"rev"  }
				};
	}
	@DataProvider(name = "userDetails")
	public Object[][] userLoginDetails() {
		return new Object[][] { { Input.pa1FullName, Input.pa1userName, Input.pa1password },
				{ Input.rmu1FullName, Input.rmu1userName, Input.rmu1password },
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password } };
	}
	
	@DataProvider(name = "specificUsers")
	public Object[][] userLoginWithSpecificCredentials() {
		return new Object[][] { {Input.pa1FullName, Input.pa1userName, Input.pa1password},
				{ Input.rmu1FullName, Input.rmu1userName, Input.rmu1password }, };
	}
	
	@DataProvider(name = "multiUsers")
	public Object[][] userLoginWithMultiCredentials() {
		return new Object[][] {
				{ Input.rmu1FullName, Input.rmu1userName, Input.rmu1password }, 
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password }};
	}
	
	
	/**
	 * @Author : Mohan date: 21/10/2021 Modified date: NA Modified by: Mohan
	 * @Description : To verify user after impersonation should able to 'Remove Code same as this' 
	 * 				  on document selection from Thread Map panel. 'RPMXCON-51229'
	 * @Stabilization - done
	 */
	
	@Test(enabled = true,dataProvider="userDetailss", groups = { "regression" }, priority = 1)
	public void removeCodeSameAsForThreadMapDocsAfterImpersonate(String roll,String userName, String password,String impersonate) throws InterruptedException {
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51229");
		
		// Login as Reviewer Manager
		loginPage.loginToSightLine(userName, password);
		switch (impersonate) {
		case "rmu":
			if (roll.equalsIgnoreCase("sa")&& impersonate.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateSAtoRMU();
			}
			if (roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonatePAtoRMU();
			} 
				
		case "rev":
			if (roll.equalsIgnoreCase("sa")&& impersonate.equalsIgnoreCase("rev")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateSAtoReviewer();
			}
			if (roll.equalsIgnoreCase("pa")&& impersonate.equalsIgnoreCase("rev")) {
				driver.waitForPageToBeReady();
				baseClass.impersonatePAtoReviewer();
			} 
			if (roll.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateRMUtoReviewer();
			}
		}
		
		if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rmu")
				|| roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rev")
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rev")
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")||roll.equalsIgnoreCase("rmu")) {
			driver.waitForPageToBeReady();
			sessionSearch.basicContentSearch(Input.searchString1);
			sessionSearch.ViewThreadedDocsInDocViews();
			docViewPage.selectDocsFromThreadMapTabAndActionCodeSame();
			docViewPage.selectDocsFromThreadMapTabAndActionRemoveCodeSameAs();
		}

		driver.waitForPageToBeReady();
		loginPage.logout();
	}
	
	/**
	 * @Author : Mohan date: 21/10/2021 Modified date: NA Modified by: Mohan
	 * @Description : To verify user after impersonation should able to 'Remove Code same as this' 
	 * 				  on document selection from Conceptual panel 'RPMXCON-51230'
	 *  @Stabilization - done
	 */
	
	@Test(enabled = true,dataProvider="userDetailss", groups = { "regression" }, priority = 2)
	public void removeCodeSameAsForConceputalDocsAfterImpersonate(String roll,String userName, String password,String impersonate) throws InterruptedException {
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51230");
		// Login as Reviewer Manager
		loginPage.loginToSightLine(userName, password);
		switch (impersonate) {
		case "rmu":
			if (roll.equalsIgnoreCase("sa")&& impersonate.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateSAtoRMU();
			}
			if (roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonatePAtoRMU();
			} 
				
		case "rev":
			if (roll.equalsIgnoreCase("sa")&& impersonate.equalsIgnoreCase("rev")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateSAtoReviewer();
			}
			if (roll.equalsIgnoreCase("pa")&& impersonate.equalsIgnoreCase("rev")) {
				driver.waitForPageToBeReady();
				baseClass.impersonatePAtoReviewer();
			} 
			if (roll.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateRMUtoReviewer();
			}
		}
		
		if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rmu")
				|| roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rev")
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rev")
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")||roll.equalsIgnoreCase("rmu")) {
			driver.waitForPageToBeReady();
			
			sessionSearch.basicContentSearch(Input.searchString1);
			sessionSearch.ViewConceptualDocsInDocViews();
		
			docViewPage.selectDocsFromConceptualTabAndActionCodeSame();
			docViewPage.selectDocsFromConceptualAndRemoveCodeAsSameForConceptTab();
		}

		driver.waitForPageToBeReady();
		loginPage.logout();
	}
	
	
	/**
	 * Author : Mohan date: 10/26/21 NA Modified date: NA Modified by:NA
	 * Description : To verify that user has selected any tab from Analytics Panel 
	 * and selected document from doc history, previously selected Analytics Panel Tab must remain.'RPMXCON-51426' Sprint : 5
	 * @throws InterruptedException 
	 *  @Stabilization - done
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 3)
	public void verifyAnalyticsTabAfterSelectingDocsFromHistoryIconInMiniDoclist(String fullName, String userName, String password) throws InterruptedException {
		
		loginPage = new LoginPage(driver);
		miniDocListpage = new MiniDocListPage(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51426");

		baseClass.stepInfo(
				"To verify that user has selected any tab from Analytics Panel and selected document from doc history, previously selected Analytics Panel Tab must remain.");

		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		
		String docIdHistoryIcon = Input.historyClockIconDocId;

		baseClass.stepInfo("Step 2 : Search for Docs and go to Docview");
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		baseClass.stepInfo("Step 3 : View docs from MiniDocList");
		//Select docs from MiniDocList
		miniDocListpage.Select10DocsMiniDocList();
		
		baseClass.stepInfo("Step 4 : Select Analytics Panel");
		//Select Analytics Panel and verify docs are loaded
		docViewPage.verifyThreadMapWithNoEmailDocs();
		docViewPage.selectFamilyMemberTabAndVerifyDocsAreDisplayed();
		docViewPage.selectNearDupeTabAndVerifyDocsAreDisplayed();
		docViewPage.selectConceptuallyTabAndVerifyDocsAreDisplayed();
		
		baseClass.stepInfo("Step 5 : Select Docs from History icon of MiniDoclist");
		//verify when docs are selected from MiniDoclist History Icon
		miniDocListpage.SelectaDocsFromHistoryDropDownInMiniDocList(docIdHistoryIcon);
		docViewPage.verifyConceputalTabRemainsSameNavigatedAlso();
		
		loginPage.logout();
		

	}
	
	/**
	 * Author : Mohan date: 10/26/21 NA Modified date: NA Modified by:NA
	 * Description : To verify that user has selected an Analytics Panel tab 
	 * and selected document from the same document to view which exists in mini doc list, 
	 * then previously selected Analytics Panel Tab must remain.'RPMXCON-51427' Sprint : 5
	 * @throws InterruptedException 
	 *  @Stabilization - done
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 4)
	public void verifyAnalyticsTabAfterSelectingDocsFromAnalyticsPanel(String fullName, String userName, String password) throws InterruptedException {
		
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51427");
		
		baseClass.stepInfo("To verify that user has selected an Analytics Panel tab and selected document from the same document to view which exists in mini doc list, then previously selected Analytics Panel Tab must remain.");
		
		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		
		String docIdHistoryIcon = Input.historyClockIconDocId;
		
		baseClass.stepInfo("Step 2 : Search for Docs and go to Docview");
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewInDocView();
		
		docViewPage.selectDocIdInMiniDocList(docIdHistoryIcon);
		
		baseClass.stepInfo("Step 3 : Select Analytics Panel");
		//Select Analytics Panel and verify docs are loaded
		docViewPage.verifyThreadMapWithNoEmailDocs();
		docViewPage.selectFamilyMemberTabAndVerifyDocsAreDisplayed();
		docViewPage.selectNearDupeTabAndVerifyDocsAreDisplayed();
		docViewPage.selectConceptuallyTabAndVerifyDocsAreDisplayed();
		
		baseClass.stepInfo("Step 4 : Select Docs from Analytics Panel which is part of MiniDocList");
		//select docs from analytics panel and view in docview and verify in MiniDoclist
		docViewPage.selectDocsFromConceptualTabAndViewTheDocs();
		docViewPage.verifyArrowMark();
		
		baseClass.stepInfo("Step 5 : Verify Analytics Panel to remain in same postion");
		//verify Conceputal tab remains same 
		docViewPage.verifyConceputalTabRemainsSameNavigatedAlso();

		loginPage.logout();

	}
	
	/**
	 * Author : Mohan date: 11/03/21 NA Modified date: NA Modified by:NA
	 * Description : Verify that PCC-Fitcontent/Reset Zoom icon should be 
	 * functional from 'Original' and 'Near Dupe' document panel of near dupe comparison'RPMXCON-51704' Sprint : 5
	 * @throws InterruptedException 
	 *  @Stabilization - done
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 5)
	public void verifyResetZoomIconInNearDupeComparisonWindow(String fullName, String userName, String password) throws InterruptedException {
		
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51704");
		
		baseClass.stepInfo("To Verify that PCC-Fitcontent/Reset Zoom icon should be functional from 'Original' and 'Near Dupe' document panel of near dupe comparison");
		
		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		
		String docId= Input.nearDupeCompletedDocId;
		
		baseClass.stepInfo("Step 2 : Search for Docs and go to Docview");
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewNearDupeDocumentsInDocView();
		
		baseClass.stepInfo("Step 3 : View the document from mini doc list having near dupe documents");
		//View the document from mini doc list
		docViewPage.selectDocIdInMiniDocList(docId);
		String parentWindowID = driver.getWebDriver().getWindowHandle();
		
		baseClass.stepInfo("Step 4 : From Analytics panel > Near Dupe window click the icon");
		// Open Comparison Window in NearDupes Tab
		docViewPage.openNearDupeComparisonWindow();
		baseClass.passedStep("Comparison Window is opened Successfully");

		baseClass.stepInfo("Step 5 : Click Zoom-in/Zoom-out from Original view and then click PCC-Fitcontent/Reset Zoom icon");
		//click zoom-in/zoom-out and reset button in Original View
		docViewPage.verifyOriginalViewNearDupeDocs();
		baseClass.passedStep("Zoom-in/Zoom-out from Original view and PCC-Fitcontent/Reset Zoom icon has been clicked successfully");
		
		baseClass.stepInfo("Step 6 : Click Zoom-in/Zoom-out from Near Dupe view and then click PCC-Fitcontent/Reset Zoom icon");
		//click zoom-in/zoom-out and reset button in Near Dupe View
		docViewPage.verifyNearDupeViewNearDupeDocs();
		baseClass.passedStep("Zoom-in/Zoom-out from Near Dupe view and PCC-Fitcontent/Reset Zoom icon has been clicked successfully");
		
		
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		
		loginPage.logout();
		
	}
	
	/**
	 * Author : Mohan date: 11/03/21 NA Modified date: NA Modified by:NA
	 * Description : Verify that Zoom-in, Zoom-out should work from the 
	 * Original document panel of near dupe comparison window'RPMXCON-51705' Sprint : 5
	 * @throws InterruptedException 
	 *  @Stabilization - done
	 */
	@Test(enabled = true, dataProvider = "userDetails", groups = { "regression" }, priority = 6)
	public void verifyZoomInZoomOutIconInNearDupeComparisonWindow(String fullName, String userName, String password) throws InterruptedException {
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51705");
		
		baseClass.stepInfo("To Verify that Zoom-in, Zoom-out should work from the Original document panel of near dupe comparison window");
		
		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		
		String docId= Input.nearDupeCompletedDocId;
		
		baseClass.stepInfo("Step 2 : Search for Docs and go to Docview");
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewNearDupeDocumentsInDocView();
		
		baseClass.stepInfo("Step 3 : View the document from mini doc list having near dupe documents");
		//View the document from mini doc list
		docViewPage.selectDocIdInMiniDocList(docId);
		String parentWindowID = driver.getWebDriver().getWindowHandle();
		
		baseClass.stepInfo("Step 4 : From Analytics panel > Near Dupe window click the icon");
		// Open Comparison Window in NearDupes Tab
		docViewPage.openNearDupeComparisonWindow();
		baseClass.passedStep("Comparison Window is opened Successfully");

		baseClass.stepInfo("Step 5 : Click the icon to Zoom-in, Zoom-out from Original/Near Dupe panel");
		//click zoom-in/zoom-out and reset button in Original View
		docViewPage.verifyOriginalViewZoomInAndZoomOutNearDupeDocs();
		baseClass.passedStep("Zoom-in, Zoom-out from Original/Near Dupe panel has been clicked successfully");
		
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		
		loginPage.logout();
		
	}
	
	/**
	 * @Author : Mohan date: 09/11/2021 Modified date: NA Modified by: Mohan
	 * @Description : To verify user after impersonation should able to 'Remove Code same as this' 
	 * on document selection from Family Members panel 'RPMXCON-51221'
	 *  @Stabilization - done
	 */
	
	@Test(enabled = true,dataProvider="userDetailss", groups = { "regression" }, priority = 7)
	public void removeCodeSameAsForFamilyMemberDocsAfterImpersonate(String roll,String userName, String password,String impersonate) throws InterruptedException {
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51221");
		baseClass.stepInfo(
				"To verify user after impersonation should able to 'Remove Code same as this' on document selection from Family Members panel");
		String miniDocs = Input.familyDocument;
		// Login as Reviewer Manager
		loginPage.loginToSightLine(userName, password);
		switch (impersonate) {
		case "rmu":
			if (roll.equalsIgnoreCase("sa")&& impersonate.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateSAtoRMU();
			}
			if (roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonatePAtoRMU();
			} 
				
		case "rev":
			if (roll.equalsIgnoreCase("sa")&& impersonate.equalsIgnoreCase("rev")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateSAtoReviewer();
			}
			if (roll.equalsIgnoreCase("pa")&& impersonate.equalsIgnoreCase("rev")) {
				driver.waitForPageToBeReady();
				baseClass.impersonatePAtoReviewer();
			} 
			if (roll.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateRMUtoReviewer();
			}
		}
		
		if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rmu")
				|| roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rev")
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rev")
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")||roll.equalsIgnoreCase("rmu")) {
			driver.waitForPageToBeReady();
			
			sessionSearch.basicContentSearch(Input.searchString1);
			sessionSearch.ViewFamilyMemberDocsInDocViews();
			driver.waitForPageToBeReady();
            docViewPage.selectDocIdInMiniDocList(miniDocs);
			docViewPage.selectDocsFromFamilyMemberTabAndActionCodeSame();
			docViewPage.selectDocsFromFamilyMemberTabAndActionRemoveCodeSame();
		}

		driver.waitForPageToBeReady();
		loginPage.logout();
	}
	
	
	/**
	 * Author : Mohan date: 11/09/21 NA Modified date: NA Modified by:NA
	 * Description : To verify user can select Multiple documents in Analytics panel > Conceptual 
	 * and Select Action as 'Remove Code Same as this''RPMXCON-51219' Sprint : 6
	 * @throws InterruptedException 
	 *  @Stabilization - done
	 */
	@Test(enabled = true,  groups = { "regression" }, priority = 8)
	public void verifyMultiDocsInConceptAndActionAsRemoveCodeAsSame() throws InterruptedException {
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		assignmentPage = new AssignmentsPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51219");

		baseClass.stepInfo(
				"Verify user can select Multiple documents in Analytics panel > Conceptual and Select Action as 'Remove Code Same as this'");
		
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.conceptualDocumentReviewer;
		String revDocToBeSelected = Input.MetaDataId;

		// Login as a Admin
		loginPage.loginToSightLine(Input.rmu1userName,Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1FullName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1FullName);
		

		baseClass.stepInfo("Step 2 : Search for Docs and go to Docview");
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Creating assignments with conceptual documents");
		sessionSearch.getConceptDocument();
		sessionSearch.bulkAssign();
        assignmentPage.assignFamilyDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 0);
		baseClass.stepInfo("Assignment is created and docs are distribute to the users successfully");

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		driver.waitForPageToBeReady();

		// Select Docid from MiniDocList
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		docViewPage.selectDocIdInMiniDocList(documentToBeSelected);
		
		baseClass.stepInfo("Step 3 : Select multiple documents from Conceptual and action as 'Code same as this'");
		//select Multi docs from conceptual tab
		docViewPage.selectDocsFromConceptualTabAndActionCodeSame();
		
		baseClass.stepInfo("Step 4: Select the documents having code same icon and action as 'Remove Code Same'");
		docViewPage.selectDocsFromConceptTabAndActionRemoveCodeSame();
		
		loginPage.logout();
		
		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");
		
		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		driver.waitForPageToBeReady();
		
		// Select Docid from MiniDocList
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		docViewPage.selectDocIdInMiniDocList(revDocToBeSelected);

		baseClass.stepInfo("Step 3 : Select multiple documents from Conceptual and action as 'Code same as this'");
		// select Multi docs from conceptual tab
		docViewPage.selectDocsFromConceptualTabAndActionCodeSame();

		baseClass.stepInfo("Step 4: Select the documents having code same icon and action as 'Remove Code Same'");
		docViewPage.selectDocsFromConceptTabAndActionRemoveCodeSame();
		
		loginPage.logout();
		
		
		
	}
	
	/**
	 * Author : Mohan date: 11/03/21 NA Modified date: NA Modified by:NA
	 * Description : To verify user can assign documents to selected folder 
	 * from Dockout screen->Analytics-Family Members.'RPMXCON-51128' Sprint : 6
	 * @throws Exception 
	 *  @Stabilization - done
	 */
	@Test(enabled = true, dataProvider = "specificUsers", groups = { "regression" }, priority = 9)
	public void verifyFolderActionInAnalyticsFamilyMemberTab(String fullName, String userName, String password) throws Exception {
		
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51128");
		
		baseClass.stepInfo("To verify user can assign documents to selected folder from Dockout screen->Analytics-Family Members.");
		
		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		
		String miniDocs = Input.familyDocument;
		String folderName = "FamilyMember"+ Utility.dynamicNameAppender();
		
		baseClass.stepInfo("Step 2 : Go to doc view and click on gear icon");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewFamilyMemberDocsInDocViews();
		String parentWindowID = driver.getWebDriver().getWindowHandle();
		docViewPage.selectDocIdInMiniDocList(miniDocs);
		
		baseClass.stepInfo("Step 3 : open Analytical panel child window->Family member  select multiple document");
		
		//View the document from mini doc list
		driver.waitForPageToBeReady();
		docViewPage.popOutAnalyticsPanel();
		
		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		docViewPage.Analytics_FamilyActionsFolderCreation();
		
		
		baseClass.stepInfo("Step 4 and Step 5: Select action as Folder and Select folder and click on Add.");
		
		//Select action as Folder
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		docViewPage.createFolderforDocsInAnlyticsPanel(folderName);
		
		//delete the folder after the performance
		
		this.driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		driver.switchTo().alert().accept();
		tagsAndFolderPage.deleteAllFolders(folderName);
		loginPage.logout();
		
	}
	
	/**
	 * Author : Mohan date: 11/11/21 NA Modified date: NA Modified by:NA
	 * Description : To verify user can select Multiple documents in Family Members->Analytic Panel 
	 * from dockout screens and Select Action as 'Code Same as'.'RPMXCON-51126' Sprint : 6
	 * @throws Exception 
	 *  @Stabilization - done
	 */
	@Test(enabled = true, dataProvider = "multiUsers", groups = { "regression" }, priority = 10)
	public void verifyCodeSameAsActionInAnalyticsFamilyMemberTabAndSaveCodingForm(String fullName, String userName, String password) throws Exception {
		
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		reusableDocViewPage = new ReusableDocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51126");
		
		baseClass.stepInfo("To verify user can select Multiple documents in Family Members->Analytic Panel from dockout screens and Select Action as 'Code Same as'");
		
		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		
		String AnalyticsPanel= Input.familyDocument;
		
		baseClass.stepInfo("Step 2 : Go to doc view and click on gear icon");
		
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewFamilyMemberDocsInDocViews();
		String parentWindowID = driver.getWebDriver().getWindowHandle();
		docViewPage.selectDocIdInMiniDocList(AnalyticsPanel);
		
		
		baseClass.stepInfo("Step 3 : Pop out the analytics panel");
		
		//View the document from mini doc list
		driver.waitForPageToBeReady();
		docViewPage.popOutAnalyticsPanel();
		
		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		
		baseClass.stepInfo("Step 4 & 5: Go to family member and select multiple documents and Select action as Code same as");
		docViewPage.performCodeSameForFamilyMembersDocuments(parentWindowID);
		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		
		baseClass.stepInfo("Step 6 : Edit Coding Form and Click on Save document.");
		
		//Edit Coding form save
		reusableDocViewPage.editingCodingFormWithSaveButton();
		
		loginPage.logout();
	
	}
	
	/**
	 * Author : Mohan date: 11/09/21 NA Modified date: NA Modified by:NA
	 * Description :Verify check mark icon should be displayed when coding stamp 
	 * applied after selecting 'Code same as this' action' from Analytics Panel > Family Member'RPMXCON-51060' Sprint : 6
	 * @throws InterruptedException 
	 *  @Stabilization - done
	 */
	@Test(enabled = true,  groups = { "regression" }, priority = 11)
	public void verifyCheckMarkApplyingCodingStampAfterSelectingCodeSameAsForFamilyMember() throws InterruptedException {
		
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		docViewPage = new DocViewPage(driver);
		reusableDocViewPage = new ReusableDocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51060");

		baseClass.stepInfo(
				"Verify check mark icon should be displayed when coding stamp applied after selecting 'Code same as this' action' from Analytics Panel > Family Member");
		
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String colour = "BLUE";
		String colourName = "colourName" + Utility.dynamicNameAppender();
		String documentToBeSelected = Input.familyDocument;
		String revDocToBeSelected= Input.threadDocId;

		// Login as a Admin
		loginPage.loginToSightLine(Input.rmu1userName,Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1FullName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1FullName);
		

		baseClass.stepInfo("Step 2 : Search for Docs and go to Docview");
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Creating assignments with conceptual documents");
		sessionSearch.bulkAssignFamilyMemberDocuments();
		assignmentPage.assignFamilyDocstoNewAssgnEnableAnalyticalPanel(assname, codingForm, 0);
		assignmentPage.editAssignmentAndEnableToggleForCodingStamp(assname);
		
		baseClass.stepInfo("Assignment is created and docs are distribute to the users successfully");

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		driver.waitForPageToBeReady();

		// Select Docid from MiniDocList
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		docViewPage.selectDocIdInMiniDocList(documentToBeSelected);
		docViewPage.selectDocsFromFamilyMemberTabAndActionCodeSame();
		
		
		// Edit Coding Stamp and Apply Coding Stamp
		
		docViewPage.editCodingFormComplete();
		reusableDocViewPage.stampColourSelection(colourName, colour);
		docViewPage.verifyCheckMark();
		baseClass.stepInfo("Coding Stamp is applied and docs are verified with the check mark successfully");
		
		//logout
		loginPage.logout();
		
		
		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");
		
		
		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		driver.waitForPageToBeReady();

		// Select Docid from MiniDocList
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		docViewPage.selectDocIdInMiniDocList(revDocToBeSelected);
		docViewPage.selectDocsFromFamilyMemberTabAndActionCodeSame();

		// Edit Coding Stamp and Apply Coding Stamp
		docViewPage.editCodingFormComplete();
		reusableDocViewPage.stampColourSelection(colourName, colour);
		docViewPage.verifyCheckMark();
		baseClass.stepInfo("Coding Stamp is applied and docs are verified with the check mark successfully");

		// logout
		loginPage.logout();
		
		}
	
	/**
	 * Author : Mohan date: 11/09/21 NA Modified date: NA Modified by:NA
	 * Description :Verify check mark icon should be displayed when coding stamp 
	 * applied after selecting 'Code same as this' action' from Analytics Panel > Near Dupe'RPMXCON-51061' Sprint : 6
	 * @throws InterruptedException
	 * @Stabilization - done 
	 */
	@Test(enabled = true,  groups = { "regression" }, priority = 12)
	public void verifyCheckMarkApplyingCodingStampAfterSelectingCodeSameAsForNearDupe() throws InterruptedException {
		
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		assignmentPage = new AssignmentsPage(driver);
		docViewPage = new DocViewPage(driver);
		reusableDocViewPage = new ReusableDocViewPage(driver);
		
		
		baseClass.stepInfo("Test case Id: RPMXCON-51061");
		driver.Manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		baseClass.stepInfo(
				"Verify check mark icon should be displayed when coding stamp applied after selecting 'Code same as this' action' from Analytics Panel > Near Dupe");
		
		String codingForm = Input.codeFormName;
		String assname = "assgnment" + Utility.dynamicNameAppender();
		String colour = "BLUE";
		String colourName = "colourName" + Utility.dynamicNameAppender();

		// Login as a Admin
		loginPage.loginToSightLine(Input.rmu1userName,Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1FullName);
		baseClass.stepInfo("Logged in as User: " + Input.rmu1FullName);
		

		baseClass.stepInfo("Step 2 : Search for Docs and go to Docview");
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		baseClass.stepInfo("Creating assignments with conceptual documents");
		sessionSearch.bulkAssignNearDupeDocuments();
		assignmentPage.assignNearDupeDocstoNewAssgn(assname, codingForm, 0);
		assignmentPage.editAssignmentAndEnableToggleForCodingStamp(assname);
		
		baseClass.stepInfo("Assignment is created and docs are distribute to the users successfully");

		// Impersonate RMU to Reviewer
		baseClass.impersonateRMUtoReviewer();

		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		driver.waitForPageToBeReady();

		// Select Docid from MiniDocList
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		docViewPage.performCodeSameForNearDupeDocuments(1);
		
		
		// Edit Coding Stamp and Apply Coding Stamp
		
		docViewPage.editCodingFormComplete();
		reusableDocViewPage.stampColourSelection(colourName, colour);
		docViewPage.verifyCheckMark();
		baseClass.stepInfo("Coding Stamp is applied and docs are verified with the check mark successfully");
		
		//logout
		loginPage.logout();
		
		
		// login Reviewer
		loginPage.loginToSightLine(Input.rev1userName, Input.rev1password);
		UtilityLog.info("User successfully logged into slightline webpage as reviewer with " + Input.rev1userName + "");
		
		
		// Select the Assignment from dashboard
		assignmentPage.SelectAssignmentByReviewer(assname);
		baseClass.stepInfo("Doc is selected from dashboard and viewed in DocView successfully");

		driver.waitForPageToBeReady();

		// Select Docid from MiniDocList
		baseClass.stepInfo("Docs are selected and viewed In MiniDocList");
		docViewPage.performCodeSameForNearDupeDocuments(1);

		// Edit Coding Stamp and Apply Coding Stamp
		docViewPage.editCodingFormComplete();
		reusableDocViewPage.stampColourSelection(colourName, colour);
		docViewPage.verifyCheckMark();
		baseClass.stepInfo("Coding Stamp is applied and docs are verified with the check mark successfully");

		// logout
		loginPage.logout();
		
		}
	
	/**
	 * @Author : Mohan date: 24/10/2021 Modified date: NA Modified by: Mohan
	 * @Description : TTo verify Sys Admin/Project Admin/RMU after impersonating-
	 *  Threaded Map tab when no documents to display. 'RPMXCON-50903'
	 *  @Stabilization - done
	 */
	
	@Test(enabled = true,dataProvider="userDetailss", groups = { "regression" }, priority = 13)
	public void verifyThreadedMapTabWhenNoDocsToDisplay(String roll,String userName, String password,String impersonate) throws InterruptedException {
		loginPage = new LoginPage(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-50903");
		baseClass.stepInfo("To verify Sys Admin/Project Admin/RMU after impersonating- Threaded Map tab when no documents to display");
		
		// Login as Reviewer Manager
		loginPage.loginToSightLine(userName, password);
		switch (impersonate) {
		case "rmu":
			if (roll.equalsIgnoreCase("sa")&& impersonate.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateSAtoRMU();
			}
			if (roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonatePAtoRMU();
			} 
				
		case "rev":
			if (roll.equalsIgnoreCase("sa")&& impersonate.equalsIgnoreCase("rev")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateSAtoReviewer();
			}
			if (roll.equalsIgnoreCase("pa")&& impersonate.equalsIgnoreCase("rev")) {
				driver.waitForPageToBeReady();
				baseClass.impersonatePAtoReviewer();
			} 
			if (roll.equalsIgnoreCase("rmu")) {
				driver.waitForPageToBeReady();
				baseClass.impersonateRMUtoReviewer();
			}
		}
		
		if (roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rmu")
				|| roll.equalsIgnoreCase("sa") && impersonate.equalsIgnoreCase("rev")
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rev")
				|| roll.equalsIgnoreCase("pa") && impersonate.equalsIgnoreCase("rmu")||roll.equalsIgnoreCase("rmu")) {
			driver.waitForPageToBeReady();
			sessionSearch.basicContentSearch(Input.searchString1);
			sessionSearch.ViewInDocViews();
			docViewPage.verifyThreadMapWithNoDocs();
		}

		driver.waitForPageToBeReady();
		loginPage.logout();
	}
	
	/**
	 * Author : Mohan date: 24/12/21 NA Modified date: NA Modified by:NA
	 * Description : Verify that Near dupe window to see the differences should open,
	 *  on click of the icon from Analytics Panel > Near Dupe child window'RPMXCON-51709' Sprint : 9
	 * @throws InterruptedException
	 * @Stabilization - not done 
	 */ 
	//@Test(enabled = false, dataProvider = "userDetails", groups = { "regression" }, priority = 14) // Doc are need to be ingested
	public void verifyNearDupeWindowToSeeDifferenceInTheDocs(String fullName, String userName, String password) throws InterruptedException {
		
		loginPage = new LoginPage(driver);
		miniDocListpage = new MiniDocListPage(driver);
		sessionSearch = new SessionSearch(driver);
		docViewPage = new DocViewPage(driver);
		
		baseClass.stepInfo("Test case Id: RPMXCON-51709");

		baseClass.stepInfo(
				"Verify that Near dupe window to see the differences should open, on click of the icon from Analytics Panel > Near Dupe child window");

		// Login as a Admin
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		
		String docToBeSelected = Input.threadData1;
		baseClass.stepInfo("Step 2 : Search for documents to get the near dupe documents and drag the result to shopping cart, select action as View in Doc View");
		// Session search to doc view Coding Form
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.ViewNearDupeDocumentsInDocView();
		
		baseClass.stepInfo("Step 3: View the document from mini doc list having near dupe documents");
		driver.waitForPageToBeReady();
		docViewPage.selectDocIdInMiniDocList(docToBeSelected);
		
		baseClass.stepInfo("Step 4: Click the gear icon to pop out the panels and pop out the analytics panel");
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		docViewPage.popOutAnalyticsPanel();
		
		String parentWindowID = driver.getWebDriver().getWindowHandle();
		
		baseClass.stepInfo("Step 5 : From Analytics panel child window > Near Dupe click the icon to open the near dupe comparison window");
		
		
		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		
		baseClass.waitForElement(docViewPage.getDocView_Analytics_NearDupeTab());
		docViewPage.getDocView_Analytics_NearDupeTab().waitAndClick(10);
		docViewPage.getDocView_NearDupeIcon().waitAndClick(10);

		for (String winHandle : driver.getWebDriver().getWindowHandles()) {
			driver.switchTo().window(winHandle);
			driver.waitForPageToBeReady();
		}

		
			
			for (int i = 1; i <=3; i++) {
				if (docViewPage.getDocView_NearDupeComparisonWindow_IgnoreButton().Enabled()) {
					System.out.println("Comparison Window is Ready to perform next steps");
					break;
				}
				else {
					driver.Navigate().refresh();
				}
			}
				
				
				docViewPage.getDocView_NearDupe_DocID().WaitUntilPresent();
				String docidinchildwinodw = docViewPage.getDocView_NearDupe_DocID().getText().toString();
				System.out.println(docidinchildwinodw);
				
				String color = docViewPage.get_textHighlightedColor().getWebElement().getCssValue("fill");
				String hex2 = Color.fromString(color).asHex();
				System.out.println(hex2);
				baseClass.passedStep("Near Dupe comparison window is opened and the differences highlighted on the near dupe comparison window");
				
				driver.getWebDriver().close();
				driver.switchTo().window(parentWindowID);
				
			
		
		
		loginPage.logout();
	}
	@DataProvider(name = "multiUserCredentials")
	public Object[][] multiuserLoginDetails() {
		return new Object[][] { 
				{ Input.rmu1FullName, Input.rmu1userName, Input.rmu1password,"RMU","REV" },
				{ Input.pa1FullName, Input.pa1userName, Input.pa1password,"PA","RMU" },
				{ Input.pa1FullName, Input.pa1userName, Input.pa1password,"PA","rev" }};
	}
	/**
	 * @author Gopinath
	 * @TestCase Id:51438 Verify that after impersonation if the document native/PDF/TIFF/Text is being presented, the N/P/T/X icon with the accompanying mouse over tool tip must appear
	 * @Description :To Verify that after impersonation if the document native/PDF/TIFF/Text is being presented, the N/P/T/X icon with the accompanying mouse over tool tip must appear.
	 * @throws InterruptedException
	 */
	@Test(enabled = true,dataProvider="multiUserCredentials", groups = { "regression" }, priority = 1)
	public void verifyDocIdIconOnDocViewPanal(String fullName,String userName,String password,String fromRole,String toRole) throws InterruptedException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		baseClass.stepInfo("Test case id : RPMXCON-51438");
		baseClass.stepInfo("Verify that after impersonation if the document native/PDF/TIFF/Text is being presented, the N/P/T/X icon with the accompanying mouse over tool tip must appear");
		String N_DocID="ID00001351";
		String N_DocToolTipMessage="Native file variant of the document being displayed";
		String X_DocID="ID00000102";
		String X_DocToolTipMessage= "Text file variant of the document being displayed";
		String T_DocID="ID00001012";
		String T_DocToolTipMessage="TIFF file variant of the document being displayed";
		String P_DocId="ID00001464";
		String P_DocToolTipMessage="PDF file variant of the document being displayed";
		loginPage=new LoginPage(driver);
		loginPage.loginToSightLine(userName, password);
		UtilityLog.info("Logged in as User: " + fullName);
		baseClass.stepInfo("Logged in as User: " + fullName);
		SessionSearch session = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		
		baseClass.stepInfo("Impersnated from "+fromRole+" to "+toRole);
		baseClass.rolesToImp(fromRole, toRole);
		
		baseClass.stepInfo("Step 1: Search for the docs ");
		session.basicContentSearch(Input.searchString1);
		
		baseClass.stepInfo("Step 2:view docS in DocView");
		session.ViewInDocView();
		
		baseClass.stepInfo("Verify T icon and tolltip message for selected document");
		docView.verifyingToolTipPopupMessage(T_DocID, T_DocToolTipMessage);
		
		baseClass.stepInfo("Verify X icon and tolltip message for selected document");
		docView.verifyingToolTipPopupMessage(X_DocID, X_DocToolTipMessage);
		
		baseClass.stepInfo("Verifying P icon and tolltip message for selected document");
		docView.verifyingToolTipPopupMessage(P_DocId, P_DocToolTipMessage);
		
		baseClass.stepInfo("Verifying N icon and tolltip message for selected document");
		docView.verifyingToolTipPopupMessage(N_DocID, N_DocToolTipMessage);
		
		
		loginPage.logout();
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
			try { // if any tc failed and dint logout!
				loginPage.logout();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		UtilityLog.info("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			loginPage.quitBrowser();
		} finally {
			//LoginPage.clearBrowserCache();
		}
		
	}

}
