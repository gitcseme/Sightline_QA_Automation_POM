package sightline.docviewRedactions;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DataSets;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.DocViewMetaDataPage;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocViewRedactions_Phase2_Regression {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	DocViewRedactions docViewRedact;
	Input ip;
	DocViewPage docView;
	Utility utility;
	SecurityGroupsPage securityGroupsPage;
	DocViewMetaDataPage docViewMetaDataPage;
	UserManagement userManage;
	DocExplorerPage docexp;
	AssignmentsPage assignPage;
	KeywordPage keywordPage;
	SavedSearch savedsearch;

	String assignmentName = "AAassignment" + Utility.dynamicNameAppender();

	@BeforeClass(alwaysRun = true)

	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());

		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
	}

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}
	
	@DataProvider(name = "userDetails2")
	public Object[][] userLoginDetails2() {
		return new Object[][] { { Input.rmu1FullName, Input.rmu1userName, Input.rmu1password },
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password } };
	}
	
	
	/**
	 * Author :Krishna date: NA Modified date: NA Modified by: NA Test Case
	 * Id:RPMXCON-47910
	 * @throws InterruptedException
	 * @throws AWTException
	 */

	@Test(description ="RPMXCON-47910",enabled = true, alwaysRun = true, groups = { "regression" }, dataProvider = "userDetails2")
	public void verifyRedactionPanelRetainedOnNavigation(String fullName, String userName, String password) throws Exception {
		baseClass = new BaseClass(driver);
		baseClass.stepInfo("Test case id : RPMXCON-47910");
		baseClass.stepInfo("Verify when Redactions menu is selected from doc view and navigates to another document from mini doc list child window then previously selected panels/menus should remain");
		loginPage.loginToSightLine(userName, password);
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.randomText);
		sessionSearch.viewInDocView();
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		docViewRedact.clickingRedactionIcon();
		DocViewPage docView = new DocViewPage(driver);
		docViewRedact.navigatingDocsFromMiniDocListChildWindowandClose();
		if (docViewRedact.thisPageRedaction().isDisplayed()) {
			baseClass.passedStep("Redaction panel is retained after doc navigation");
		} else {
			baseClass.failedStep("Redaction panel not retained after doc navigation");
		}
		loginPage.logout();
	}
	
	
	
	/**
     * Author : Krishna date: NA Modified date:NA Modified by: Test Case Id: 49074
     * Verify the Redaction and Highlighting is deleted successfully in DocView
     */
    @Test(description = "RPMXCON-49074", enabled = true, alwaysRun = true, groups = { "regression" })
    public void verifyRedactionHighlitingDeletedInDocView() throws Exception {
        baseClass.stepInfo("Test case Id: RPMXCON-49074");
        baseClass.stepInfo("Verify the Redaction and Highlighting is deleted successfully in DocView");
        Actions actions = new Actions(driver.getWebDriver());
        baseClass = new BaseClass(driver);
        DocViewRedactions docViewRedact = new DocViewRedactions(driver);
        SessionSearch sessionsearch = new SessionSearch(driver);
        DocViewMetaDataPage docViewMetaDataPage = new DocViewMetaDataPage(driver);
        String RedactName = "newRedac" + Utility.dynamicNameAppender();
        DocListPage doclist = new DocListPage(driver);

       // Login as RMU
        loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
        baseClass.stepInfo("User successfully logged into slightline webpage RMU as with " + Input.rmu1userName + "");
        sessionsearch.basicContentSearch(Input.randomText);
        sessionsearch.ViewInDocView();
        baseClass.stepInfo("Documents viewd in DocView");
       baseClass.stepInfo("Clicked on reduction button ");
        baseClass.waitForElement(doclist.getDocIDFromDocView(4));
        doclist.getDocIDFromDocView(4).waitAndClick(5);
        baseClass.waitForElement(docViewMetaDataPage.getRedactIcon());
        driver.waitForPageToBeReady();
        docViewMetaDataPage.getRedactIcon().waitAndClick(15);
        driver.waitForPageToBeReady();
        baseClass.waitTillElemetToBeClickable(docViewRedact.thisPageRedaction());
        docViewRedact.thisPageRedaction().waitAndClick(5);
        baseClass.stepInfo("Selected on this page add redaction");
        driver.waitForPageToBeReady();
        docViewRedact.verifySelectDefaultRedactionTag(Input.defaultRedactionTag, RedactName);
        baseClass.waitTime(8);
        baseClass.waitForElement(doclist.getDocIDFromDocView(4));
        doclist.getDocIDFromDocView(4).waitAndClick(5);
        driver.waitForPageToBeReady();
        docViewRedact.getDocView_RedactHTextarea().waitAndClick(5);
        driver.scrollPageToTop();
        if (docViewRedact.highliteDeleteBtn().isElementAvailable(5)) {
            docViewRedact.highliteDeleteBtn().waitAndClick(5);
            baseClass.passedStep("The highlight has been deleted successfully");
            baseClass.stepInfo("Redaction Removed successfully.");
        } else {
            System.out.println("This Page highlight not done at current doc");
        }
        driver.Navigate().refresh();
        driver.waitForPageToBeReady();
        baseClass.stepInfo("Reload the document");
        baseClass.waitForElement(doclist.getDocIDFromDocView(4));
        doclist.getDocIDFromDocView(4).waitAndClick(5);
        baseClass.waitTime(5);
        actions.moveToElement(docViewRedact.getDocView_Redactrec_textarea().getWebElement(), 0, 0).click();
        actions.build().perform();
        if (docViewRedact.highliteDeleteBtn().isElementAvailable(5) == true) {
            docViewRedact.highliteDeleteBtn().Click();
            baseClass.failedStep("the highlight has not been deleted after refresh");
        } else {
            baseClass.passedStep("The highlight is not present on the page after deleting and refreshing");
       }
        loginPage.logout();
}

	/**
	 * 
	 * @author Krishna Date:NA Modified date:N/A Modified by::N/A Test
	 *         caseID:47923 Description :Verify after impersonation user can not see
	 *         the multi page and all page options from highlighting
	 */

	@Test(description = "RPMXCON-47923", enabled = true, groups = { "regression" })

	public void verifyAfterImpersonationCannotSeeMultiAndAllPage() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-47923");
		baseClass.stepInfo(
				"Verify after impersonation user can not see the multi page and all page options from highlighting");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewRedactions redact = new DocViewRedactions(driver);
		String multipage = "multi page";
		String allpage = "all page";

		// Login as
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage SAU as with " + Input.sa1userName + "");
		baseClass.impersonateSAtoRMU();
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Documents viewd in DocView");
		baseClass.waitTime(5);
		driver.waitForPageToBeReady();
		redact.clickingHighlitingIcon();
		baseClass.stepInfo("Sub menus is displayed for highlighting on clicked icons as expected ");
		redact.verifyMultiPageandAllpageHighlight(multipage, allpage);
		loginPage.logout();

		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage SAU as with " + Input.sa1userName + "");
		baseClass.impersonateSAtoReviewer();
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Documents viewd in DocView");
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		redact.clickingHighlitingIcon();
		baseClass.stepInfo("Sub menus is displayed for highlighting on clicked icons as expected ");
		redact.verifyMultiPageandAllpageHighlight(multipage, allpage);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage PAU as with " + Input.pa1userName + "");
		baseClass.impersonatePAtoRMU();
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Documents viewd in DocView");
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		redact.clickingHighlitingIcon();
		baseClass.stepInfo("Sub menus is displayed for highlighting on clicked icons as expected ");
		redact.verifyMultiPageandAllpageHighlight(multipage, allpage);
		loginPage.logout();

		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage PAU as with " + Input.pa1userName + "");
		baseClass.impersonatePAtoReviewer();
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Documents viewd in DocView");
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		redact.clickingHighlitingIcon();
		baseClass.stepInfo("Sub menus is displayed for highlighting on clicked icons as expected ");
		redact.verifyMultiPageandAllpageHighlight(multipage, allpage);
		loginPage.logout();

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage RMU as with " + Input.rmu1userName + "");
		baseClass.impersonateRMUtoReviewer();
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Documents viewd in DocView");
		driver.waitForPageToBeReady();
		baseClass.waitTime(5);
		redact.clickingHighlitingIcon();
		baseClass.stepInfo("Sub menus is displayed for highlighting on clicked icons as expected ");
		redact.verifyMultiPageandAllpageHighlight(multipage, allpage);
		loginPage.logout();

	}

	/**
	 * Author : Krishna date: NA Modified date:NA Modified by: Test Case Id: 47909
	 * Verify Redactions menu is selected from doc view and then navigates to
	 * another document then selected panels/menus previously selected should
	 * remain.
	 */
	@Test(description = "RPMXCON-47909", enabled = true, dataProvider = "userDetails", groups = { "regression" })
	
	public void verifyRedactionMenuNavigatesMenusRemain(String fullName, String userName, String password) throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-47909");
		baseClass.stepInfo(
				"Verify Redactions menu is selected from doc view and then navigates to another document then selected panels/menus previously selected should remain.");
		baseClass = new BaseClass(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewMetaDataPage docViewMetaDataPage = new DocViewMetaDataPage(driver);
		DocViewPage docview = new DocViewPage(driver);
		DocListPage doclist = new DocListPage(driver);

		// login as
	    loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("User successfully logged into slightline webpage RMU as with " + Input.rmu1userName + "");
		sessionsearch.basicContentSearch(Input.randomText);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Documents viewd in DocView");

		baseClass.stepInfo("Clicked on reduction button ");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewMetaDataPage.getRedactIcon());
		docViewMetaDataPage.getRedactIcon().waitAndClick(15);
		driver.waitForPageToBeReady();
		baseClass.ValidateElementCollection_Presence(docview.getDocView_BatchRedaction(),
				"Batch Redaction panel displayed in redaction panel");
		baseClass.waitForElement(doclist.getDocIDFromDocView(2));
		doclist.getDocIDFromDocView(2).waitAndClick(5);
		baseClass.stepInfo("navigate to another document in minidoclist");
		docViewRedact.verifyRedactionIconRemain();
		baseClass.ValidateElementCollection_Presence(docview.getDocView_BatchRedaction(),
				"Batch Redaction panel displayed in redaction panel");
		docview.clickGearIconOpenMiniDocList();
		docview.switchToNewWindow(2);
		baseClass.waitForElement(doclist.getDocIDFromDocView(2));
		doclist.getDocIDFromDocView(2).waitAndClick(5);
		baseClass.stepInfo("navigate to another document in minidoclist childwindow");
		docview.closeWindow(1);
		docview.switchToNewWindow(1);
		baseClass.waitTime(5);
		docViewRedact.verifyRedactionIconRemain();
		baseClass.ValidateElementCollection_Presence(docview.getDocView_BatchRedaction(),
				"Batch Redaction panel displayed in redaction panel");
		baseClass.waitForElement(docview.getDocView_Next());
		docview.getDocView_Next().waitAndClick(5);
		baseClass.stepInfo("navigate to another document in navigation options-> , >");
		docViewRedact.verifyRedactionIconRemain();
		baseClass.ValidateElementCollection_Presence(docview.getDocView_BatchRedaction(),
				"Batch Redaction panel displayed in redaction panel");
		baseClass.waitForElement(docview.getDocView_NumTextBox());
		docview.getDocView_NumTextBox().waitAndClick(10);
		docview.getDocView_NumTextBox().SendKeys("5");
		docview.getDocView_NumTextBox().Enter();
		baseClass.stepInfo("Navigate to document on entering the document number");
		docViewRedact.verifyRedactionIconRemain();
		baseClass.ValidateElementCollection_Presence(docview.getDocView_BatchRedaction(),
				"Batch Redaction panel displayed in redaction panel");
		docview.clickClockIconMiniDocList();
		baseClass.stepInfo("Selected the document from history drop down");
		docViewRedact.verifyRedactionIconRemain();
		baseClass.ValidateElementCollection_Presence(docview.getDocView_BatchRedaction(),
				"Batch Redaction panel displayed in redaction panel");
		loginPage.logout();

	}

	
	/**
	 * @author Brundha.T date: 2/11/2022 TestCase Id:RPMXCON-47880 Description When
	 *         the user closes the redaction or highlight control in the ribbon, all
	 *         states must revert to "off"
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-47880", enabled = true, groups = { "regression" })
	public void validatingaRevertOnAndOff() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-47880");
		baseClass.stepInfo(
				"When the user closes the redaction or highlight control in the ribbon, all states must revert to 'off'");

		String[] UserName = { Input.rmu1userName, Input.rev1userName };
		String[] Password = { Input.rmu1password, Input.rev1password };

		for (int i = 0; i < UserName.length; i++) {

			loginPage.loginToSightLine(UserName[i], Password[i]);
			baseClass.stepInfo("Logged in as " + UserName[i]);

			SessionSearch sessionSearch = new SessionSearch(driver);
			sessionSearch.basicContentSearch(Input.testData1);
			sessionSearch.ViewInDocView();

			DocViewRedactions DocRedact = new DocViewRedactions(driver);
			baseClass.waitTillElemetToBeClickable(DocRedact.redactionIcon());
			DocRedact.redactionIcon().waitAndClick(5);
			baseClass.waitTime(2);

			baseClass.stepInfo("verifying revert on and off state in Redaction icon");
			String DefaultReactionTabClr = DocRedact
					.verifyingRevertOffAndOn(DocRedact.getRectangleRedactionIconColor());

			DocRedact.rectangleClick().waitAndClick(10);
			String RevertOnColor = DocRedact.verifyingRevertOffAndOn(DocRedact.getRectangleRedactionIconColor());
			baseClass.textCompareNotEquals(DefaultReactionTabClr, RevertOnColor,
					"Rectangle icon in changed to red color and revert is on", "Rectangle icon is revert off");
			driver.waitForPageToBeReady();
			DocRedact.doubleClickRedactionBtn();
			String RevertDefaultOff = DocRedact.verifyingRevertOffAndOn(DocRedact.getRectangleRedactionIconColor());
			baseClass.textCompareEquals(DefaultReactionTabClr, RevertDefaultOff, "After panel reopening revert is off",
					"After panel reopening revert is on");

			baseClass.stepInfo("verifying revert on and off state in Highlighter icon");
			DocRedact.doubleclickingHighlitingIcon(false, false);
			String DefaultHighlighterColor = DocRedact
					.verifyingRevertOffAndOn(DocRedact.getHighlightedPageRedactionIconColor());

			DocRedact.thisPageHighlite().waitAndClick(10);
			String HighlightOn = DocRedact.verifyingRevertOffAndOn(DocRedact.getHighlightedPageRedactionIconColor());
			baseClass.textCompareNotEquals(HighlightOn, DefaultHighlighterColor, "Revert is on in Highlighter panel",
					"Revert is off in Highlighter panel");

			DocRedact.doubleclickingHighlitingIcon(false, true);
			String HighlightOff = DocRedact.verifyingRevertOffAndOn(DocRedact.getHighlightedPageRedactionIconColor());
			baseClass.textCompareEquals(HighlightOff, DefaultHighlighterColor, "Revert is off in Highlighter panel",
					"Revert is on in Highlighter panel");
			loginPage.logout();
		}
	}

	/**
	 * @author Brundha.T date: 2/11/2022 TestCase Id:RPMXCON-47879 Description To
	 *         verify that when redaction control in red "on" state, if the icon is
	 *         clicked again by the user, it must revert to an "off" state
	 * @throws Exception
	 */
	@Test(description = "RPMXCON-47879", enabled = true, groups = { "regression" })
	public void validatingaRevertOffStateInSubMenu() throws Exception {

		baseClass.stepInfo("Test case Id: RPMXCON-47879");
		baseClass.stepInfo(
				"To verify that when redaction control in red 'on' state, if the icon is clicked again by the user, it must revert to an \"off\" state");

		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Logged in as " + Input.rmu1userName);

		String[] SubMenu = { "Rectangle", "This Page", "Text Redaction", "Multipage" };
		String ComapreString = "#727272";
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocView();

		DocViewRedactions DocRedact = new DocViewRedactions(driver);
		baseClass.waitTillElemetToBeClickable(DocRedact.redactionIcon());
		DocRedact.redactionIcon().waitAndClick(5);
		baseClass.waitTime(2);

		for (int i = 0; i < SubMenu.length; i++) {
			String RedactionSubMenuOff = DocRedact.verifyingRevertOffAndOn(baseClass.text(SubMenu[i]));
			baseClass.textCompareEquals(RedactionSubMenuOff, ComapreString, "Sub menu is in grey color by default",
					"Sub menu is not in grey color by default");
		}

		for (int i = 1; i <= 2; i++) {
			driver.waitForPageToBeReady();
			DocRedact.rectangleClick().waitAndClick(10);
			driver.waitForPageToBeReady();
			String SubMenuOnColor = DocRedact.verifyingRevertOffAndOn(DocRedact.getRectangleRedactionIconColor());
			if (i == 1) {
				baseClass.textCompareNotEquals(ComapreString, SubMenuOnColor, "Rectangle icon is revert on ",
						"Rectangle icon is revert off");
			} else {
				baseClass.textCompareEquals(ComapreString, SubMenuOnColor, "Sub Menu revert to off state ",
						"Sub Menu not revert to off state");
			}
		}
		loginPage.logout();
	}

	/**
	 *
	 * @Author : Brundha @Testcase id : 49989 
	 * Verify that after deletion of the last saved redaction tag,
	 *         'Default Redaction Tag' should be selected automatically from
	 *         redaction pop up
	 */
	@Test(description = "RPMXCON-49989", enabled = true, groups = { "regression" })
	public void verifyRedactionTagInPopUpDropDown() throws Exception {
		BaseClass baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id: RPMXCON-49989 from Docview/redaction Component");
		baseClass.stepInfo(
				"Verify that after deletion of the last saved redaction tag, 'Default Redaction Tag' should be selected automatically from redaction pop up");

		String Redactiontag1 = "FirstRedactionTag" + Utility.dynamicNameAppender();
		String Redactiontag2 = "SecondRedactionTag" + Utility.dynamicNameAppender();

		RedactionPage redactionpage = new RedactionPage(driver);
		baseClass.stepInfo("Creating a redaction tag");
		 this.driver.getWebDriver().get(Input.url+"Redaction/Redaction");
		 driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag1);
		driver.waitForPageToBeReady();
		redactionpage.manageRedactionTagsPage(Redactiontag2);

		SessionSearch sessionSearch = new SessionSearch(driver);
		baseClass.stepInfo("Basic meta data search");
		sessionSearch.basicContentSearch(Input.testData1);

		baseClass.stepInfo("Navigate to doc view page");
		sessionSearch.ViewInDocView();

		DocViewRedactions docViewRedactions = new DocViewRedactions(driver);
		docViewRedactions.redactRectangleUsingOffset(10, 10, 40, 40);
		docViewRedactions.selectingRedactionTag2(Input.defaultRedactionTag);
		driver.scrollPageToTop();
		docViewRedactions.selectDoc1();

		baseClass.waitTime(1);
		driver.scrollPageToTop();
		docViewRedactions.redactRectangleUsingOffsetWithDoubleClick(10, 10, 20, 20);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag1);
		baseClass.waitTime(1);
		docViewRedactions.redactRectangleUsingOffsetWithDoubleClick(10, 10, 80, 80);
		driver.waitForPageToBeReady();
		docViewRedactions.selectingRedactionTag2(Redactiontag2);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		redactionpage = new RedactionPage(driver);
		baseClass.stepInfo("Deleting Redaction tag");
		redactionpage.DeleteRedaction(Redactiontag2);

		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
		sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocView();

		docViewRedactions = new DocViewRedactions(driver);
		docViewRedactions.selectFirstDoc().isElementAvailable(10);
		docViewRedactions.redactRectangleUsingOffset(10, 10, 30, 30);
		docViewRedactions.rectangleRedactionTagSelect().isDisplayed();
		Select select = new Select(docViewRedactions.rectangleRedactionTagSelect().getWebElement());
		String option = select.getFirstSelectedOption().getText();
		System.out.println("the value is " + option);
		baseClass.textCompareEquals(option,Redactiontag1,"Last saved redaction tag is selected automatically","Last saved redaction tag is not selected automatically");
		
		
		redactionpage = new RedactionPage(driver);
		baseClass.stepInfo("Deleting Redaction tag");
		redactionpage.DeleteRedaction(Redactiontag1);
		
		driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearch.ViewInDocViewWithoutPureHit();
		docViewRedactions.selectFirstDoc().isElementAvailable(10);
		docViewRedactions.redactRectangleUsingOffset(10, 10, 30, 30);
		docViewRedactions.rectangleRedactionTagSelect().isDisplayed();
		Select OptionInDrop = new Select(docViewRedactions.rectangleRedactionTagSelect().getWebElement());
		String DefaultTag = OptionInDrop.getFirstSelectedOption().getText();
		baseClass.textCompareEquals(DefaultTag, Input.defaultRedactionTag, "after deletion of the last saved redaction tag, 'Default Redaction Tag' is selected automatically","Default tag is not displayed" );
		
		
		loginPage.logout();

	}

	
	/**
	 * 
	 * @author Sakthivel Date:NA Modified date:N/A Modified by::N/A Test
	 *         caseID:46962 Description :Verify the automatically selection of the
	 *         redaction tag when Project Admin impersonates as RMU/Reviewer
	 */

	@Test(description = "RPMXCON-46962", enabled = true, groups = { "regression" })

	public void verifySelectionRedactionTagPAImpersonateRMU() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-46962");
		baseClass.stepInfo(
				"Verify the automatically selection of the redaction tag when Project Admin impersonates as RMU/Reviewer");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewRedactions redact = new DocViewRedactions(driver);
		String RedactName = "newRedac" + Utility.dynamicNameAppender();
		RedactionPage redactac = new RedactionPage(driver);

		// Login as RMU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PAU as with " + Input.pa1userName + "");
		baseClass.impersonatePAtoRMU();
		redactac.AddRedaction(RedactName, Input.rmu1FullName);

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.searchnameenron);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();

		// verify default RedactionTag in popup
		baseClass.stepInfo("Add redaction in rectangle");
		redact.redactRectangleUsingOffset(10, 10, 20, 20);
		baseClass.stepInfo("Selected on this page add redaction");
		redact.verifySelectDefaultRedactionTag(Input.defaultRedactionTag, RedactName);
		redactac.DeleteRedaction(RedactName);
	}

	/**
	 * 
	 * @author Krishna Date:NA Modified date:N/A Modified by::N/A Test
	 *         caseID:46960 Description :Verify the automatically selection of the
	 *         redaction tag when System Admin impersonates as RMU/Reviewer
	 */

	@Test(description = "RPMXCON-46960", enabled = true, groups = { "regression" })

	public void verifySelectionRedactionTagSAImpersonateRmu() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-46960");
		baseClass.stepInfo(
				"Verify the automatically selection of the redaction tag when System Admin impersonates as RMU/Reviewer");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewMetaDataPage docViewMetaDataPage = new DocViewMetaDataPage(driver);
		DocViewRedactions redact = new DocViewRedactions(driver);
		String RedactName = "newRedac" + Utility.dynamicNameAppender();
		RedactionPage redactac = new RedactionPage(driver);

		// Login as RMU
		loginPage.loginToSightLine(Input.sa1userName, Input.sa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  SAU as with " + Input.sa1userName + "");
		baseClass.impersonateSAtoRMU();
		redactac.AddRedaction(RedactName, Input.rmu1FullName);

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.searchnameenron);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Clicked on reduction button ");
		baseClass.waitForElement(docViewMetaDataPage.getRedactIcon());
		docViewMetaDataPage.getRedactIcon().waitAndClick(15);
		driver.waitForPageToBeReady();

		// verify default RedactionTag in popup
		baseClass.waitForElement(redact.thisPageRedaction());
		baseClass.waitTillElemetToBeClickable(redact.thisPageRedaction());
		redact.thisPageRedaction().waitAndClick(5);
		baseClass.stepInfo("Selected on this page add redaction");
		redact.verifySelectDefaultRedactionTag(Input.defaultRedactionTag, RedactName);
		redactac.DeleteRedaction(RedactName);
	}

	/**
	 * 
	 * @author Krishna Date:NA Modified date:N/A Modified by::N/A Test
	 *         caseID:46961 Description :Verify the automatically selection of the
	 *         redaction tag when Domain Admin impersonates as RMU/Reviewer
	 */

	@Test(description = "RPMXCON-46961", enabled = true, groups = { "regression" })

	public void verifySelectionRedactionTagDAImpersonateRMU() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-46961");
		baseClass.stepInfo(
				"Verify the automatically selection of the redaction tag when Domain Admin impersonates as RMU/Reviewer");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewRedactions redact = new DocViewRedactions(driver);
		String RedactName = "newRedac" + Utility.dynamicNameAppender();
		RedactionPage redactac = new RedactionPage(driver);

		// Login as RMU
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  DAU as with " + Input.da1userName + "");
		baseClass.impersonateDAtoRMU();
		redactac.AddRedaction(RedactName, Input.rmu1FullName);

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.searchnameenron);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		baseClass.stepInfo("Clicked on reduction button ");
		driver.waitForPageToBeReady();

		// verify default RedactionTag in popup
		baseClass.stepInfo("Add redaction in rectangle");
		redact.redactRectangleUsingOffset(10, 10, 20, 20);
		baseClass.stepInfo("Selected on this page add redaction");
		redact.verifySelectDefaultRedactionTag(Input.defaultRedactionTag, RedactName);
		redactac.DeleteRedaction(RedactName);
	}

	/**
	 * 
	 * @author Krishna Date:NA Modified date:N/A Modified by::N/A Test
	 *         caseID:46959 Description :Verify that after editing a redaction and
	 *         applies a redaction tag to a redaction, then this applied redaction
	 *         tag should be considered as the latest redaction tag
	 */

	@Test(description = "RPMXCON-46959", enabled = true, groups = { "regression" })

	public void verifyAfterEditingRedactionApplyRedactionTag() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-46959");
		baseClass.stepInfo(
				"Verify that after editing a redaction and applies a redaction tag to a redaction, then this applied redaction tag should be considered as the latest redaction tag");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewRedactions redact = new DocViewRedactions(driver);
		String RedactName = "newRedac" + Utility.dynamicNameAppender();
		RedactionPage redactac = new RedactionPage(driver);

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage RMU as with " + Input.rmu1userName + "");
		redactac.AddRedaction(RedactName, Input.rmu1FullName);
		redactac.EditRedaction(RedactName);
		driver.waitForPageToBeReady();

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.testData1);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Clicked on reduction button ");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Add redaction in rectangle as expected");

		// verify default RedactionTag in popup
		redact.redactRectangleUsingOffset(10, 10, 20, 20);
		redact.verifySelectDefaultRedactionTag(Input.defaultRedactionTag, RedactName);
		baseClass.passedStep(
				"After Edited redaction tag is considered as the latest redaction tag is saved successfully.");
		redactac.DeleteRedaction(RedactName);
	}

	/**
	 * 
	 * @author Krishna Date: Modified date:N/A Modified by:N/A Test caseID:49979
	 *         Description :Verify that when applies rectangle redaction, the
	 *         redaction popup should automatically select the redaction tag that
	 *         was last applied across user session(s)
	 */

	@Test(description = "RPMXCON-49979", enabled = true, groups = { "regression" })

	public void verifyApplyRedactionPopUpAutomaticSelectRedactionTag() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-49979");
		baseClass.stepInfo(
				"Verify that when applies rectangle redaction, the redaction popup should automatically select the redaction tag that was last applied across user session(s)");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewPage docView = new DocViewPage(driver);
		DocViewMetaDataPage docViewMetaDataPage = new DocViewMetaDataPage(driver);
		DocViewRedactions redact = new DocViewRedactions(driver);

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		baseClass.stepInfo(
				"Verify that when applies rectangle redaction, the redaction popup should automatically select the redaction tag that was last applied across user session(s)");

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Click on reduction button ");

		docViewMetaDataPage.clickOnRedactAndRectangle();
		baseClass.waitTime(5);
		String color = redact.get_textHighlightedColorOnRectangleSubMenu().getWebElement().getCssValue("color");
		System.out.println(color);
		String ExpectedColor = org.openqa.selenium.support.Color.fromString(color).asHex();
		System.out.println(ExpectedColor);
		if (Input.iconColor.equalsIgnoreCase(ExpectedColor)) {
			baseClass.passedStep("Reduction Rectangle submenu icon is highlighted red color is displayed successfully");
		} else {
			baseClass.failedStep("Reduction submenu icon is not highlighted");
		}

		baseClass.stepInfo("redaction popup is automatically select the redaction tag");
		docViewMetaDataPage.redactbyrectangle(10, 15, Input.defaultRedactionTag);
	}

	/**
	 * 
	 * @author Krishna Date: Modified date:N/A Modified by::N/A Test caseID:49980
	 *         Description: Verify that when applies ‘This Page’ redaction, the
	 *         redaction popup should automatically select the redaction tag that
	 *         was last applied across user session(s)
	 */

	@Test(description = "RPMXCON-49980", enabled = true, groups = { "regression" })

	public void verifyApplyThisPageRedactionAutomaticSelectRedactionTag() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-49980");
		baseClass.stepInfo(
				"Verify that when applies rectangle redaction, the redaction popup should automatically select the redaction tag that was last applied across user session(s)");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewMetaDataPage docViewMetaDataPage = new DocViewMetaDataPage(driver);
		DocViewRedactions redact = new DocViewRedactions(driver);
		SoftAssert softassert = new SoftAssert();

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		baseClass.stepInfo(
				"Verify that when applies ‘This Page’ redaction, the redaction popup should automatically select the redaction tag that was last applied across user session(s)");

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Click on reduction button ");
		baseClass.waitForElement(docViewMetaDataPage.getRedactIcon());
		docViewMetaDataPage.getRedactIcon().waitAndClick(15);
		driver.waitForPageToBeReady();
		redact.performThisPageRedactionWithoutSaving(Input.defaultRedactionTag);
		softassert.assertTrue(docViewMetaDataPage.getSaveButton().isDisplayed());
		baseClass.stepInfo("selected 'This Page' redacted area is displayed for the current page and pop up is opened");

		driver.waitForPageToBeReady();
		baseClass.stepInfo("Redaction popup is automatically select the redaction tag successfully");
		baseClass.waitForElement(docViewMetaDataPage.getSaveButton());
		docViewMetaDataPage.getSaveButton().Click();
		baseClass.VerifySuccessMessage("Redaction tags saved successfully.");
		baseClass.getCloseSucessmsg();
		softassert.assertAll();
	}

	/**
	 * 
	 * @author Krishna Date: Modified date:N/A Modified by::N/A Test caseID:49997
	 *         Description : Verify the automatically selection of the redaction tag
	 *         when RMU impersonates as Reviewer
	 */

	@Test(description = "RPMXCON-49997", enabled = true, groups = { "regression" })

	public void verifySelectedRedactionTagRmuImpersonateAsRev() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-49997");
		baseClass.stepInfo("Verify the automatically selection of the redaction tag when RMU impersonates as Reviewer");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewMetaDataPage docViewMetaDataPage = new DocViewMetaDataPage(driver);
		DocViewRedactions redact = new DocViewRedactions(driver);
		String RedactName = "newRedac" + Utility.dynamicNameAppender();
		RedactionPage redactac = new RedactionPage(driver);

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		baseClass.stepInfo("Verify the automatically selection of the redaction tag when RMU impersonates as Reviewer");
		redactac.AddRedaction(RedactName, Input.rev1FullName);
		baseClass.impersonateRMUtoReviewer();

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Clicked on reduction button ");
		baseClass.waitForElement(docViewMetaDataPage.getRedactIcon());
		docViewMetaDataPage.getRedactIcon().waitAndClick(15);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(redact.thisPageRedaction());
		baseClass.waitTillElemetToBeClickable(redact.thisPageRedaction());
		redact.thisPageRedaction().waitAndClick(5);
		baseClass.stepInfo("Selected on this page add redaction");
		redact.verifySelectDefaultRedactionTag(Input.defaultRedactionTag, RedactName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		redactac.DeleteRedaction(RedactName);
	}

	/**
	 * 
	 * @author Krishna Date: Modified date:N/A Modified by::N/A Test caseID:49996
	 *         Description :Verify the automatically selection of the redaction tag
	 *         when Project Admin impersonates as RMU/Reviewer
	 */

	@Test(description = "RPMXCON-49996", enabled = true, groups = { "regression" })

	public void verifySelectionRedactionTagPAImpersonateRmu() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-49996");
		baseClass.stepInfo(
				"Verify the automatically selection of the redaction tag when Project Admin impersonates as RMU/Reviewer");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewMetaDataPage docViewMetaDataPage = new DocViewMetaDataPage(driver);
		DocViewRedactions redact = new DocViewRedactions(driver);
		String RedactName = "newRedac" + Utility.dynamicNameAppender();
		RedactionPage redactac = new RedactionPage(driver);

		// Login as RMU
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PAU as with " + Input.pa1userName + "");
		baseClass.stepInfo("Verify the automatically selection of the redaction tag when RMU impersonates as Reviewer");
		baseClass.impersonatePAtoRMU();
		redactac.AddRedaction(RedactName, Input.rmu1FullName);

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.searchnameenron);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Clicked on reduction button ");
		baseClass.waitForElement(docViewMetaDataPage.getRedactIcon());
		docViewMetaDataPage.getRedactIcon().waitAndClick(15);
		driver.waitForPageToBeReady();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(redact.thisPageRedaction());
		baseClass.waitTillElemetToBeClickable(redact.thisPageRedaction());
		redact.thisPageRedaction().waitAndClick(5);
		baseClass.stepInfo("Selected on this page add redaction");
		redact.verifySelectDefaultRedactionTag(Input.defaultRedactionTag, RedactName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		redactac.DeleteRedaction(RedactName);
	}

	/**
	 * 
	 * @author Krishna Date: Modified date:N/A Modified by::N/A Test caseID:49995
	 *         Description :Verify the automatically selection of the redaction tag
	 *         when Domain Admin impersonates as RMU/Reviewer
	 */

	@Test(description = "RPMXCON-49995", enabled = true, groups = { "regression" })

	public void verifySelectionRedactionTagDAImpersonateRmu() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-49995");
		baseClass.stepInfo(
				"Verify the automatically selection of the redaction tag when Domain Admin impersonates as RMU/Reviewer");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewMetaDataPage docViewMetaDataPage = new DocViewMetaDataPage(driver);
		DocViewRedactions redact = new DocViewRedactions(driver);
		String RedactName = "newRedac" + Utility.dynamicNameAppender();
		RedactionPage redactac = new RedactionPage(driver);

		// Login as RMU is successful 
		loginPage.loginToSightLine(Input.da1userName, Input.da1password);
		baseClass.stepInfo("User successfully logged into slightline webpage  PAU as with " + Input.pa1userName + "");
		baseClass.stepInfo("Verify the automatically selection of the redaction tag when RMU impersonates as Reviewer");
		baseClass.impersonateDAtoRMU();
		redactac.AddRedaction(RedactName, Input.rmu1FullName);

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.searchnameenron);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Clicked on reduction button ");
		baseClass.waitForElement(docViewMetaDataPage.getRedactIcon());
		docViewMetaDataPage.getRedactIcon().waitAndClick(15);
		driver.waitForPageToBeReady();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(redact.thisPageRedaction());
		baseClass.waitTillElemetToBeClickable(redact.thisPageRedaction());
		redact.thisPageRedaction().waitAndClick(5);
		baseClass.stepInfo("Selected on this page add redaction");
		redact.verifySelectDefaultRedactionTag(Input.defaultRedactionTag, RedactName);
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		redactac.DeleteRedaction(RedactName);
	}

	/**
	 * 
	 * @author Krishna Date: Modified date:N/A Modified by::N/A Test caseID:48037
	 *         Description: Verify that on Saving
	 *         document/Redactions/Annotations/Reviewer Remarks should not affect
	 *         the displayed tab of the Analytics Panel
	 */

	@Test(description = "RPMXCON-48037", enabled = true, dataProvider = "userDetails", groups = { "regression" })

	public void verifySavingDocNotAffectDisplayedTabAnalayticalPanel(String fullName, String userName, String password)
			throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-48037");
		baseClass.stepInfo(
				"Verify that on Saving document/Redactions/Annotations/Reviewer Remarks should not affect the displayed tab of the Analytics Panel");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewMetaDataPage docViewMetaDataPage = new DocViewMetaDataPage(driver);
		SoftAssert softassert = new SoftAssert();
		DocViewPage docView = new DocViewPage(driver);
		final String remark = Input.randomText + Utility.dynamicNameAppender();

		// login as
		loginPage.loginToSightLine(userName, password);
		baseClass.stepInfo("User successfully logged into slightline webpage  RMU as with " + Input.rmu1userName + "");
		baseClass.stepInfo(
				"Verify that when applies ‘This Page’ redaction, the redaction popup should automatically select the redaction tag that was last applied across user session(s)");

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewNearDupeDocumentsInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();
		docView.getDocView_Analytics_NearDupeTab().waitAndClick(15);
		baseClass.stepInfo("Neardupe Tab is selected from the analytical panel");
		driver.waitForPageToBeReady();
		docView.editCodingFormSave();
		softassert.assertTrue(docView.getDocView_AnalyticsPanel_NearDupeWholeTabel().isDisplayed());
		softassert.assertAll();
		baseClass.passedStep("After saved document and Selected tab of analytics panel is not get affected ");

		driver.waitForPageToBeReady();
		docViewMetaDataPage.clickOnRedactAndRectangle();
		docViewMetaDataPage.redactbyrectangle(10, 15, Input.defaultRedactionTag);
		softassert.assertTrue(docView.getDocView_AnalyticsPanel_NearDupeWholeTabel().isDisplayed());
		softassert.assertAll();
		baseClass.passedStep("After saved document and Selected tab of analytics panel is not get affected ");

		driver.waitForPageToBeReady();
		docView.performNonAudioAnnotation();
		softassert.assertTrue(docView.getDocView_AnalyticsPanel_NearDupeWholeTabel().isDisplayed());
		softassert.assertAll();
		baseClass.passedStep("After saved document and Selected tab of analytics panel is not get affected ");

		baseClass.stepInfo("Perform Remark with save");
		driver.waitForPageToBeReady();
		docViewMetaDataPage.performRemarkWithSaveOperation(5, 55, remark);
		softassert.assertTrue(docView.getDocView_AnalyticsPanel_NearDupeWholeTabel().isDisplayed());
		softassert.assertAll();
		baseClass.passedStep("After saved document and Selected tab of analytics panel is not get affected ");

	}

	/**
	 * 
	 * @author Krishna Date: Modified date:N/A Modified by::N/A Test caseID:49987
	 *         Description :Verify that after editing a redaction and applies a
	 *         redaction tag to a redaction, then this applied redaction tag should
	 *         be considered as the latest redaction tag
	 */

	@Test(description = "RPMXCON-49987", enabled = true, groups = { "regression" })

	public void verifyAfterEditingRedactionApplyRedactionTag2() throws Exception {
		baseClass.stepInfo("Test case Id: RPMXCON-49987");
		baseClass.stepInfo(
				"Verify that after editing a redaction and applies a redaction tag to a redaction, then this applied redaction tag should be considered as the latest redaction tag");
		SessionSearch sessionsearch = new SessionSearch(driver);
		DocViewMetaDataPage docViewMetaDataPage = new DocViewMetaDataPage(driver);
		DocViewRedactions redact = new DocViewRedactions(driver);
		String RedactName = "newRedac" + Utility.dynamicNameAppender();
		RedactionPage redactac = new RedactionPage(driver);

		// Login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("User successfully logged into slightline webpage RMU as with " + Input.rmu1userName + "");
		redactac.AddRedaction(RedactName, Input.rmu1FullName);
		redactac.EditRedaction(RedactName);
		driver.waitForPageToBeReady();

		// document searched and navigated to DocView
		sessionsearch.basicContentSearch(Input.searchString1);
		sessionsearch.ViewInDocView();
		baseClass.stepInfo("Docs Viewed in Doc View");
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Clicked on reduction button ");
		baseClass.waitForElement(docViewMetaDataPage.getRedactIcon());
		docViewMetaDataPage.getRedactIcon().waitAndClick(15);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(redact.thisPageRedaction());
		baseClass.waitTillElemetToBeClickable(redact.thisPageRedaction());
		redact.thisPageRedaction().waitAndClick(5);
		baseClass.stepInfo("Selected on this page add redaction");
		redact.verifySelectDefaultRedactionTag(Input.defaultRedactionTag, RedactName);
		baseClass.passedStep("After editing a redaction as the latest redaction tag is saved successfully");
		loginPage.logout();
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		redactac.DeleteRedaction(RedactName);
	}
	@DataProvider(name = "userDetails")
	public Object[][] userLoginDetails() {
		return new Object[][] {
				{ Input.rmu1FullName, Input.rmu1userName, Input.rmu1password },
				{ Input.rev1FullName, Input.rev1userName, Input.rev1password } };
	}
}
