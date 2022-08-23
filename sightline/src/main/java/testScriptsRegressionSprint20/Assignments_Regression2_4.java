package testScriptsRegressionSprint20;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
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
import pageFactory.CodingForm;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Assignments_Regression2_4 {

	Driver driver;
	LoginPage loginPage;
	SessionSearch sessionSearch;
	SavedSearch savedSearch;
	AssignmentsPage assignPage;
	BaseClass baseClass;
	Input in;
	SoftAssert softAssertion;
	SecurityGroupsPage securityGroupsPage;
	TagsAndFoldersPage tagsAndFolderPage;
	DocViewRedactions docViewRedact;

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
		assignPage = new AssignmentsPage(driver);
		loginPage = new LoginPage(driver);
		baseClass = new BaseClass(driver);
		softAssertion = new SoftAssert();
		sessionSearch = new SessionSearch(driver);

	}

	@DataProvider(name = "Users")
	public Object[][] CombinedSearchwithUsers() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password }, { Input.pa1userName, Input.pa1password },
				{ Input.rev1userName, Input.rev1password } };
		return users;
	}

	/**
	 * @author
	 * @throws Exception
	 * @Date: 08/22/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description : Validate updating existing assignment with Sort by metadata
	 *              field. RPMXCON-54503
	 */
	@Test(description = "RPMXCON-54503", enabled = true, groups = { "regression" })
	public void validateUpdatingExistingAssignmentWithSortByMetadata() throws InterruptedException, AWTException {
		List<String> metaDataList = new ArrayList<String>();
		String assgnName = "Assgn" + Utility.dynamicNameAppender();
		MiniDocListPage miniDocList = new MiniDocListPage(driver);

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id:RPMXCON-54503");
		baseClass.stepInfo("Validate updating existing assignment with Sort by metadata field.");

		// create assignment
		baseClass.stepInfo("creating assignment");
		assignPage.createAssignment(assgnName, Input.codeFormName);

		// edit assignment sort by metaData
		baseClass.stepInfo("Editing Existing Assignment sort by metaData");
		assignPage.editAssignmentUsingPaginationConcept(assgnName);
		assignPage.Assgnwithdocumentsequence(Input.metaDataName, Input.sortType);

		// bulk assign documents to assignment
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assgnName);

		// viewing the assignment in DocView page
		assignPage.selectAssignmentToViewinDocviewThreadMap(assgnName);

		// configuring the miniDocList with assignment metaData
		baseClass.waitForElement(miniDocList.getGearIcon());
		miniDocList.clickingGearIcon();
		miniDocList.removingAllExistingFieldsAndAddingNewField(Input.metaDataName);

		// verifying that Documents are sorted based on the Selected metadata.
		metaDataList = baseClass.availableListofElements(miniDocList.getListofDocIDinCW());
		baseClass.verifyOriginalSortOrder(metaDataList, metaDataList, "Ascending", true);
		baseClass.stepInfo("verified that Documents are sorted based on the Selected metadata.");

		// delete assignment
		assignPage.deleteAssgnmntUsingPagination(assgnName);

		// logOut
		loginPage.logout();
	}

	/**
	 * @author
	 * @throws Exception
	 * @Date: 08/22/22
	 * @Modified date:N/A
	 * @Modified by: N/A
	 * @Description :Validate changing metadata for Sort by metadata field of an
	 *              existing assignment. RPMXCON-54504
	 */
	@Test(description = "RPMXCON-54504", enabled = true, groups = { "regression" })
	public void validateChangingMetadataForSortByMetadataFieldForExistingAssignment()
			throws InterruptedException, AWTException {
		List<String> metaDataList = new ArrayList<String>();
		String assgnName = "Assgn" + Utility.dynamicNameAppender();
		MiniDocListPage miniDocList = new MiniDocListPage(driver);

		// login as RMU
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		baseClass.stepInfo("Test case Id:RPMXCON-54504");
		baseClass.stepInfo("Validate changing metadata for Sort by metadata field of an existing assignment.");

		// create assignment with sort by metaData
		baseClass.stepInfo("create assignment with sort by metaData");
		assignPage.createAssignment_withoutSave(assgnName, Input.codeFormName);
		assignPage.Assgnwithdocumentsequence(Input.sortDataBy, Input.sortType);

		// edit assignment sort by metaData
		baseClass.stepInfo("Editing Existing Assignment sort by metaData");
		assignPage.editAssignmentUsingPaginationConcept(assgnName);
		assignPage.Assgnwithdocumentsequence(Input.metaDataName, Input.sortType);

		// bulk assign documents to assignment
		sessionSearch.basicContentSearch(Input.searchString1);
		sessionSearch.bulkAssignExisting(assgnName);

		// viewing the assignment in DocView page
		assignPage.selectAssignmentToViewinDocviewThreadMap(assgnName);

		// configuring the miniDocList with assignment metaData
		baseClass.waitForElement(miniDocList.getGearIcon());
		miniDocList.clickingGearIcon();
		miniDocList.removingAllExistingFieldsAndAddingNewField(Input.metaDataName);

		// verifying that Documents are sorted based on the updated metadata.
		metaDataList = baseClass.availableListofElements(miniDocList.getListofDocIDinCW());
		baseClass.verifyOriginalSortOrder(metaDataList, metaDataList, "Ascending", true);
		baseClass.passedStep("verified that Documents are sorted based on the updated metadata.");

		// delete assignment
		assignPage.deleteAssgnmntUsingPagination(assgnName);

		// logOut
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
		System.out.println("**Executed  Assignments_Regression2_2 .**");
	}

}
