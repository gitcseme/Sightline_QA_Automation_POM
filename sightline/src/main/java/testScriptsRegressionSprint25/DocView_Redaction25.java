package testScriptsRegressionSprint25;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.Select;
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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.DomainDashboard;
import pageFactory.KeywordPage;
import pageFactory.LoginPage;
import pageFactory.ProjectPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocView_Redaction25 {

	Driver driver;
	LoginPage loginPage;
	BaseClass baseClass;
	Input ip;
	Utility utility;
	SessionSearch sessionSearch;
	AssignmentsPage assignmentPage;
	DocViewRedactions DocRedact;

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

			DocRedact = new DocViewRedactions(driver);
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

		DocRedact = new DocViewRedactions(driver);
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

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR DOCVIEW REDACTION EXECUTED******");

	}

}
