package sightline.smallComponents;

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
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocList_Regression28 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignment;
	BaseClass baseClass;
	Input in;
	SoftAssert softAssert;
	DocExplorerPage docExplorer;
	DocListPage docList;

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		in = new Input();
		in.loadEnvConfig();
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		assignment = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		softAssert = new SoftAssert();
		docExplorer = new DocExplorerPage(driver);

	}
	
	/**
	 * @author Vijaya.Rani ModifyDate:17/12/2022 RPMXCON-66469
	 * @throws InterruptedException
	 * @throws AWTException
	 * @Description Verify that on mouse hover of the small list icon on the bottom
	 *              left of the thumbnail should present metadata and if the list of
	 *              metadata is more than 150px, then a vertical scrollbar should
	 *              appear..
	 */
	@Test(description = "RPMXCON-66469", dataProvider = "AllTheUsers", enabled = true, groups = { "regression" })
	public void verifyThumbnailsViewMetadataListOfMetaDataInpx(String username, String password, String role)
			throws InterruptedException {

		baseClass.stepInfo("Test case Id: RPMXCON-66469");
		baseClass.stepInfo(
				"Verify that on mouse hover of the small list icon on the bottom left of the thumbnail should present metadata and if the list of metadata is more than 150px, then a vertical scrollbar should appear.");

		DocListPage docList = new DocListPage(driver);
		sessionSearch = new SessionSearch(driver);
		SoftAssert softAssert = new SoftAssert();

		// Login As user
		loginPage.loginToSightLine(username, password);
		baseClass.stepInfo("User successfully logged into slightline webpage as with " + username + "");

		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.ViewInDocList();
		baseClass.stepInfo("DocList Page is get displayed");

		driver.waitForPageToBeReady();
		softAssert.assertTrue(docList.getTileView().isDisplayed());
		softAssert.assertTrue(docList.getGridViewIcon().isDisplayed());
		softAssert.assertTrue(docList.SelectColumnBtn().isDisplayed());
		softAssert.assertTrue(docList.getSaveToProfileBtn().isDisplayed());
		baseClass.passedStep(
				" \"List View\" and \"Tile View\" icon is displayed the same row of \"Select Column\" , \"Save to Profile\" button.");
		softAssert.assertAll();
		docList.getTileView().waitAndClick(5);
		baseClass.stepInfo("Verify that thumbnail generation is attempted and Viewed");
		driver.waitForPageToBeReady();
		docList.SelectColumnDisplay(docList.getSelectEmailAuthorName());
		docList.SelectColumnDisplay(docList.getSelectEmailAuthorDomain());
		driver.waitForPageToBeReady();
		docList.getTileView().waitAndClick(5);
		for (int i = 1; i < docList.getInfoBtn().size(); i++) {
			driver.waitForPageToBeReady();
			docList.getInfoBtnInThumbnailBoxes(i).waitAndClick(2);
			baseClass.waitForElement(docList.getMetaDataBoxInDocList());
		    String metadataDimat= docList.getMetaDataBoxInDocList().GetAttribute("style").trim();
		    System.out.println(metadataDimat);
		    String[] metadataBox = metadataDimat.split(" ");
			String BoxDim = metadataBox[1];
			String[] metadataBoxSplit = BoxDim.split("p");
			String boxDimens = metadataBoxSplit[0];
			int metadataDimentions= Integer.parseInt(boxDimens);
			System.out.println(metadataDimentions);
			driver.waitForPageToBeReady();
			if (metadataDimentions > 150 ) {
				baseClass.passedStep("metadata and if the list of metadata is more than 150px");
			} else {
				baseClass.failedStep("No such Metadataboxs");
			}
			driver.waitForPageToBeReady();
			driver.scrollingToElementofAPage(docList.getMetaDataAuthorName());
			if (docList.getMetaDataAuthorName().isDisplayed()) {
				baseClass.passedStep("vertical scrollbar is appear");
			} else {
				baseClass.failedStep("No such scrollbar");
			}
		}
		loginPage.logout();
		
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed  DocExplorer_Regression26.**");
	}
}
