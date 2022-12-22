package sightline.savedSearch;

import static org.testng.Assert.assertEquals;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import org.testng.Assert;
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
import cucumber.api.java.sl.In;
import automationLibrary.Element;

import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.BatchPrintPage;
import pageFactory.Categorization;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProductionPage;
import pageFactory.ReportsPage;
import pageFactory.SavedSearch;
import pageFactory.SchedulesPage;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class SavedSearchRegression_New_Set_01 {

	Driver driver;
	LoginPage login;
	SavedSearch saveSearch;
	SessionSearch session;
	BaseClass base;
	SecurityGroupsPage sg;
	SoftAssert softAssertion;
	ReportsPage report;
	TagsAndFoldersPage tagsAndFolderPage;
	Categorization categorize;
	AssignmentsPage assign;
	ProductionPage page;
	BatchPrintPage batch;
	DocListPage dcPage;
	MiniDocListPage miniDocListpage;

	public static int purehits;
	String folderName = "Folder" + Utility.dynamicNameAppender();
	String searchName = "Search Name" + Utility.dynamicNameAppender();
	String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
	String searchNameRMU = "RMU" + Utility.dynamicNameAppender();
	String securitygroupname = "securitygroupname" + Utility.dynamicNameAppender();

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");
		Input in = new Input();
		in.loadEnvConfig();

	}

	/**
	 * @author Raghuram Modified date:N/A Modified by: N/A - modified Dataprovider
	 *         parameter
	 * @throws InterruptedException
	 */
	@DataProvider(name = "SavedSearchwithUsers")
	public Object[][] SavedSearchwithUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	@DataProvider(name = "SavedSearchwithRMUandREV")
	public Object[][] SavedSearchwithRMUandREV() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	@DataProvider(name = "user_Roles")
	public Object[][] user_Roles() {
		Object[][] users = { { "RMU" }, { "REV" }, { "PA" } };
		return users;
	}

	@DataProvider(name = "userRolesOnlyrmuAndRev")
	public Object[][] userRolesOnlyrmuAndRev() {
		Object[][] users = { { "RMU" }, { "REV" } };
		return users;
	}

	@DataProvider(name = "SavedSearchwithPAandRMUwithRole")
	public Object[][] SavedSearchwithPAandRMUwithRole() {
		Object[][] users = {
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, "RMU", Input.pa1userName,
						Input.pa1password },
				{ Input.pa1userName, Input.pa1password, Input.pa1FullName, "PA", Input.rmu1userName,
						Input.rmu1password } };
		return users;
	}

	@DataProvider(name = "SavedSearchwithPAandRMUwithRoleANDshareType")
	public Object[][] SavedSearchwithPAandRMUwithRoleANDshareType() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName, "PA", 1 },
				{ Input.pa1userName, Input.pa1password, Input.pa1FullName, "PA", 2 },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName, "RMU", 0 },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName, "REV", 0 } };
		return users;
	}

	@DataProvider(name = "UserSaUDaUPaU")
	public Object[][] UserSaUDaUPaU() {
		Object[][] users = { { Input.sa1userName, Input.sa1password, Input.sa1userName, "SA" },
				{ Input.da1userName, Input.da1password, Input.da1userName, "DA" },
				{ Input.pa1userName, Input.pa1password, Input.pa1FullName, "PA" } };
		return users;
	}

	@DataProvider(name = "SavedSearchwithoutReviewer")
	public Object[][] SavedSearchwithoutReviewer() {
		Object[][] users = { { Input.pa1userName, Input.pa1password }, { Input.rmu1userName, Input.rmu1password } };
		return users;
	}

	/**
	 * @author Raghuram A Description: To verify, As an user login into the
	 *         Application, it will not allow to upload a file for batch search
	 *         other than xlsx format(RPMXCON-47731)
	 * @param username
	 * @param password
	 * @param fullName
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-47731", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void saveSearchBatchUploadInvalidFileFormat(String username, String password, String fullName)
			throws InterruptedException {
		String search = "Search" + Utility.dynamicNameAppender();
		base.stepInfo("Test case Id: RPMXCON-47731 - Saved Search Sprint 05");

		// Login as User
		login.loginToSightLine(username, password);

		// Create Node
		String new_node = saveSearch.createSearchGroupAndReturn("Input.mysavedsearch", "PA", "No");
		driver.waitForPageToBeReady();
		saveSearch.getCreatedNode(new_node).waitAndClick(20);

		// upload batch file
		saveSearch.uploadBatchFile_D(Input.invalidBatchFileNewLocation, Input.xlsBatchFile, false);
		saveSearch.verifyBatchUploadMessage("Failure", false);

		login.logout();
	}

	/**
	 * @author Raghuram Description:User Impersonation - Validate SA Sharing saved
	 *         search after impersonating down as PAU/RMU/Reviewer RPMXCON-49897
	 *         Sprint 5
	 *         validateDAviaPASharingSavedSearchImpersonateAsRMUAndReviewer()
	 */

	@Test(description = "RPMXCON-49897", enabled = true, groups = { "regression" })
	public void validateSAviaPASharingSavedSearchImpersonateAsRMUAndReviewer() throws Exception {

		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		String SGtoShare = Input.shareSearchDefaultSG;
		String PAtoShare = Input.shareSearchPA;
		Boolean inputValue = true;
		String mySavedSearch = Input.mySavedSearch;

		base.stepInfo("Test case Id: RPMXCON-49897 - Saved Search Sprint 05");
		base.stepInfo("Validate SA Sharing saved search after impersonating down as PAU/RMU/Reviewer");

		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String rmuSearch = "RMU Search Name" + UtilityLog.dynamicNameAppender();
		String reviewerSearch = "Reviewer Search Name" + UtilityLog.dynamicNameAppender();

		// Login as SA
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("User successfully logged into slightline webpage as PA with" + Input.sa1userName);

		// Impersonate as PA
		base.impersonateSAtoPA();
		base.stepInfo("Impersoante as PA from SA");

		// Searching documents based on search string and saving the search

		base.stepInfo("Searching documents based on search string and saving the search");
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		// Share Search via Saved Search
		base.stepInfo("Sharing the saved search with PA and with security group");
		saveSearch.shareSavedSearchAsPA(searchName, Input.securityGroup);

		// Impersonate PA User to RMU base.stepInfo("Impersonating PA user as RMU");
		base.impersonatePAtoRMU();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		base.stepInfo("Verifying whether the search saved by PA role is not available for RMU user");
		try {
			if (saveSearch.getSGTab(PAtoShare).isElementAvailable(5)) {
				base.failedStep("Shared with project adminitrator security group is available for RMU user");
			}
		} catch (Exception e) {
			base.passedStep("Shared with project adminitrator security group is not available for RMU user");
			UtilityLog.info("Shared with project adminitrator security group is not available for RMU user");
		}
		saveSearch.verifySharedGroupSearch1(mySavedSearch, searchName, false);

		base.stepInfo("Verifying whether the shared saved search is available in security group");
		saveSearch.verifySharedGroupSearch(Input.securityGroup, searchName);

		// Searching documents based on search string and saving the search base.
		base.stepInfo("Searching documents based on search string and saving the search");
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(rmuSearch);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		// Share Search via Saved Search
		base.stepInfo("Sharing the saved search with security group");
		saveSearch.shareSavedSearchRMU(rmuSearch, Input.securityGroup);

		// Impersonate RMU to Reviewer
		base.stepInfo("Impersonating RMU user as Reviewer");
		base.impersonateRMUtoReviewer();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		base.stepInfo("Verifying whether the search saved by PA role is not available for Reviewer user");
		try {
			if (saveSearch.getSGTab(PAtoShare).isElementAvailable(5)) {
				base.failedStep("Shared with project adminitrator security group is available for Reviewer user");
			}
		} catch (Exception e) {
			base.passedStep("Shared with project adminitrator security group is not available for Reviewer user");
			UtilityLog.info("Shared with project adminitrator security group is not available for Reviewer user");
		}
		saveSearch.verifySharedGroupSearch1(mySavedSearch, searchName, false);

		base.stepInfo("Verifying whether the search saved by RMU role is available in My saved search");
		saveSearch.verifySharedGroupSearch(mySavedSearch, rmuSearch);

		base.stepInfo("Verifying whether the shared saved search is available in security group");
		saveSearch.verifySharedGroupSearch(Input.securityGroup, rmuSearch);

		// Searching documents based on search string and saving the search base.
		base.stepInfo("Searching documents based on search string and saving the search");
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(reviewerSearch);

		// Impersonate Reviewer to RMU
		base.stepInfo("Impersonating Reviewer user as RMU");
		base.impersonateReviewertoRMU();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		base.stepInfo("Verifying whether the search saved by PA role is not available for RMU user");
		try {
			if (saveSearch.getSGTab(PAtoShare).isElementAvailable(5)) {
				base.failedStep("Shared with project adminitrator security group is available for RMU user");
			}
		} catch (Exception e) {
			base.passedStep("Shared with project adminitrator security group is not available for RMU user");
			UtilityLog.info("Shared with project adminitrator security group is not available for RMU user");
		}
		saveSearch.verifySharedGroupSearch1(mySavedSearch, searchName, false);

		base.stepInfo("Verifying whether the search saved by RMU role is available in My saved search");
		saveSearch.verifySharedGroupSearch(mySavedSearch, rmuSearch);

		base.stepInfo("Verifying whether the search saved by Reviewer role is available in My saved search");
		saveSearch.verifySharedGroupSearch(mySavedSearch, reviewerSearch);

		// Impersonate RMU to PA
		base.stepInfo("Impersonating RMU user as PA");
		base.impersonateSAtoPA();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		base.stepInfo("Verifying whether the search saved by PA role is available for PA user");
		saveSearch.verifySharedGroupSearch1(mySavedSearch, searchName, true);

		base.stepInfo("Verifying whether the search saved by RMU role is not available in My saved search");
		saveSearch.verifySharedGroupSearch1(mySavedSearch, rmuSearch, false);

		base.stepInfo("Verifying whether the search saved by Reviewer role is not available in My saved search");
		saveSearch.verifySharedGroupSearch1(mySavedSearch, reviewerSearch, false);

		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("PA", "No", 1);
		System.out.println("Node creation is done followed by adding searches to the created nodes");
		base.stepInfo("Node creation is done followed by adding searches to the created nodes");

		// Adding searches to the created nodes
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		base.stepInfo("-------Pre-requesties completed--------");

		// Search ID collection
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.shareSavedNodeWithDesiredGroup(newNodeList.get(0), SGtoShare);
		saveSearch.shareSavedNodeWithDesiredGroup(newNodeList.get(0), PAtoShare);
		String PANode = newNodeList.get(0);

		// Impersonate PA User to RMU
		base.stepInfo("Impersonating PA user as RMU");
		base.impersonatePAtoRMU();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.stepInfo("Verifying whether the search saved by PA role is not available for RMU user");
		try {
			if (saveSearch.getSGTab(PAtoShare).isElementAvailable(5)) {
				base.failedStep("Shared with project adminitrator security group is available for RMU user");
			}
		} catch (Exception e) {
			base.passedStep("Shared with project adminitrator security group is not available for RMU user");
			UtilityLog.info("Shared with project adminitrator security group is not available for RMU user");
		}

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		String PANodeSearchName = nodeSearchpair.get(newNodeList.get(0));
		saveSearch.verifySharedGroupSearch1(mySavedSearch, PANodeSearchName, false);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.stepInfo("Verifying whether the shared saved search is available in security group");
		saveSearch.getCollapsedSharedWithDefaultSecurityGroup().waitAndClick(10);
		saveSearch.verifySharedNode(newNodeList.get(0));
		saveSearch.verifySharedGroupSearch1(newNodeList.get(0), nodeSearchpair.get(newNodeList.get(0)), true);

		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		// Multiple Node Creation
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
		newNodeList = saveSearch.createSGAndReturn("PA", "No", 1);
		System.out.println("Node creation is done followed by adding searches to the created nodes");
		base.stepInfo("Node creation is done followed by adding searches to the created nodes");

		// Adding searches to the created nodes
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		base.stepInfo("-------Pre-requesties completed--------");

		// Search ID collection
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
		saveSearch.rootGroupExpansion();
		saveSearch.shareSavedNodeWithDesiredGroup(newNodeList.get(0), SGtoShare);
		String RMUNode = newNodeList.get(0);

		// Impersonate RMU User to Reviewer
		base.stepInfo("Impersonating RMU user as Reviewer");
		base.impersonateRMUtoReviewer();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.stepInfo("Verifying whether the search saved by PA role is not available for Reviewer user");
		try {
			if (saveSearch.getSGTab(PAtoShare).isElementAvailable(5)) {
				base.failedStep("Shared with project adminitrator security group is available for Reviewer user");
			}
		} catch (Exception e) {
			base.passedStep("Shared with project adminitrator security group is not available for Reviewer user");
			UtilityLog.info("Shared with project adminitrator security group is not available for Reviewer user");
		}

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.verifySharedGroupSearch1(mySavedSearch, PANodeSearchName, false);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.stepInfo("Verifying whether the shared saved search is available in security group");
		saveSearch.getCollapsedSharedWithDefaultSecurityGroup().waitAndClick(10);
		saveSearch.verifySharedNode(PANode);
		saveSearch.verifySharedGroupSearch1(PANode, PANodeSearchName, true);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.stepInfo("Verifying whether the shared saved search by RMU is available in security group");
		saveSearch.getCollapsedSharedWithDefaultSecurityGroup().waitAndClick(10);
		saveSearch.verifySharedNode(RMUNode);
		saveSearch.verifySharedGroupSearch1(RMUNode, nodeSearchpair.get(RMUNode), true);
		String RMUNodeSearchName = nodeSearchpair.get(RMUNode);
		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("PA", "No", 1);
		System.out.println("Node creation is done followed by adding searches to the created nodes");
		base.stepInfo("Node creation is done followed by adding searches to the created nodes");

		// Adding searches to the created nodes
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		// Search ID collection
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
		saveSearch.rootGroupExpansion();
		saveSearch.shareSavedNodeWithDesiredGroup(newNodeList.get(0), SGtoShare);
		String reviewerNode = newNodeList.get(0);

		// Impersonate PA User to RMU
		base.stepInfo("Impersonating Reviewer user as RMU");
		driver.scrollPageToTop();
		base.impersonateReviewertoRMU();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.verifySharedGroupSearch1(mySavedSearch, PANodeSearchName, false);
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.selectNode1(RMUNode);
		driver.waitForPageToBeReady();
		saveSearch.verifySharedGroupSearch1(RMUNode, RMUNodeSearchName, true);
		saveSearch.selectNode1(reviewerNode);
		saveSearch.verifySharedGroupSearch1(reviewerNode, nodeSearchpair.get(reviewerNode), true);
		String reviewerSearchName = nodeSearchpair.get(reviewerNode);
		// Impersonate PA User to RMU
		base.stepInfo("Impersonating SA user as PA");
		base.impersonateSAtoPA();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.selectNode1(PANode);
		saveSearch.verifySharedGroupSearch1(PANode, PANodeSearchName, true);
		saveSearch.verifySharedGroupSearch1(mySavedSearch, reviewerSearchName, false);
		saveSearch.verifySharedGroupSearch1(mySavedSearch, RMUNodeSearchName, false);

		login.logout();

	}

	/**
	 * @author Raghuram
	 * @Description:User Impersonation - User Impersonation - Validate DAU Sharing
	 *                   saved search after impersonating down as PAU/RMU/Reviewer
	 *                   RPMXCON-49898 Sprint 5
	 */

	@Test(description = "RPMXCON-49898", enabled = true, groups = { "regression" })
	public void validateDAviaPASharingSavedSearchImpersonateAsRMUAndReviewer() throws Exception {

		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();
		String SGtoShare = Input.shareSearchDefaultSG;
		String PAtoShare = Input.shareSearchPA;
		Boolean inputValue = true;
		String mySavedSearch = Input.mySavedSearch;

		base.stepInfo("Test case Id: RPMXCON-49898 - Saved Search Sprint 05");
		base.stepInfo("Validate DA Sharing saved search after impersonating down as PAU/RMU/Reviewer");

		String searchName = "Search Name" + UtilityLog.dynamicNameAppender();
		String rmuSearch = "RMU Search Name" + UtilityLog.dynamicNameAppender();
		String reviewerSearch = "Reviewer Search Name" + UtilityLog.dynamicNameAppender();

		// Login as DA
		login.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("User successfully logged into slightline webpage as DA with" + Input.da1userName);

		// Impersonate as PA
		base.impersonateDAtoPA();
		base.stepInfo("Impersoante as PA from DA");

		// Searching documents based on search string and saving the search

		base.stepInfo("Searching documents based on search string and saving the search");
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(searchName);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		// Share Search via Saved Search
		base.stepInfo("Sharing the saved search with PA and with security group");
		saveSearch.shareSavedSearchAsPA(searchName, Input.securityGroup);

		// Impersonate PA User to RMU
		base.stepInfo("Impersonating PA user as RMU");
		base.impersonatePAtoRMU();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		base.stepInfo("Verifying whether the search saved by PA role is not available for RMU user");
		try {

			if (saveSearch.getSGTab(PAtoShare).isElementAvailable(5)) {
				base.failedStep("Shared with project adminitrator security group is available for RMU user");
			}
		} catch (Exception e) {
			base.passedStep("Shared with project adminitrator security group is not available for RMU user");
			UtilityLog.info("Shared with project adminitrator security group is not available for RMU user");
		}
		saveSearch.verifySharedGroupSearch1(mySavedSearch, searchName, false);

		base.stepInfo("Verifying whether the shared saved search is available in security group");
		saveSearch.verifySharedGroupSearch(Input.securityGroup, searchName);

		// Searching documents based on search string and saving the search base.
		base.stepInfo("Searching documents based on search string and saving the search");
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(rmuSearch);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		// Share Search via Saved Search
		base.stepInfo("Sharing the saved search with security group");
		saveSearch.shareSavedSearchRMU(rmuSearch, Input.securityGroup);

		// Impersonate RMU to Reviewer
		base.stepInfo("Impersonating RMU user as Reviewer");
		base.impersonateRMUtoReviewer();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		base.stepInfo("Verifying whether the search saved by PA role is not available for Reviewer user");
		try {
			if (saveSearch.getSGTab(PAtoShare).isElementAvailable(5)) {
				base.failedStep("Shared with project adminitrator security group is available for Reviewer user");
			}
		} catch (Exception e) {
			base.passedStep("Shared with project adminitrator security group is not available for Reviewer user");
			UtilityLog.info("Shared with project adminitrator security group is not available for Reviewer user");
		}
		saveSearch.verifySharedGroupSearch1(mySavedSearch, searchName, false);

		base.stepInfo("Verifying whether the search saved by RMU role is available in My saved search");
		saveSearch.verifySharedGroupSearch(mySavedSearch, rmuSearch);

		base.stepInfo("Verifying whether the shared saved search is available in security group");
		saveSearch.verifySharedGroupSearch(Input.securityGroup, rmuSearch);

		// Searching documents based on search string and saving the search base.
		base.stepInfo("Searching documents based on search string and saving the search");
		session.basicContentSearch(Input.searchString2);
		session.saveSearch(reviewerSearch);

		// Impersonate Reviewer to RMU
		base.stepInfo("Impersonating Reviewer user as RMU");
		base.impersonateReviewertoRMU();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		base.stepInfo("Verifying whether the search saved by PA role is not available for RMU user");
		try {
			if (saveSearch.getSGTab(PAtoShare).isElementAvailable(5)) {
				base.failedStep("Shared with project adminitrator security group is available for RMU user");
			}
		} catch (Exception e) {
			base.passedStep("Shared with project adminitrator security group is not available for RMU user");
			UtilityLog.info("Shared with project adminitrator security group is not available for RMU user");
		}
		saveSearch.verifySharedGroupSearch1(mySavedSearch, searchName, false);

		base.stepInfo("Verifying whether the search saved by RMU role is available in My saved search");
		saveSearch.verifySharedGroupSearch(mySavedSearch, rmuSearch);

		base.stepInfo("Verifying whether the search saved by Reviewer role is available in My saved search");
		saveSearch.verifySharedGroupSearch(mySavedSearch, reviewerSearch);

		// Impersonate RMU to PA base.stepInfo("Impersonating RMU user as PA");
		base.impersonateSAtoPA();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();

		base.stepInfo("Verifying whether the search saved by PA role is available for PA user");
		saveSearch.verifySharedGroupSearch1(mySavedSearch, searchName, true);

		base.stepInfo("Verifying whether the search saved by RMU role is not available in My saved search");
		saveSearch.verifySharedGroupSearch1(mySavedSearch, rmuSearch, false);

		base.stepInfo("Verifying whether the search saved by Reviewer role is not available in My saved search");
		saveSearch.verifySharedGroupSearch1(mySavedSearch, reviewerSearch, false);

		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("PA", "No", 1);
		System.out.println("Node creation is done followed by adding searches to the created nodes");
		base.stepInfo("Node creation is done followed by adding searches to the created nodes");

		// Adding searches to the created nodes
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		base.stepInfo("-------Pre-requesties completed--------");

		// Search ID collection
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.shareSavedNodeWithDesiredGroup(newNodeList.get(0), SGtoShare);
		saveSearch.shareSavedNodeWithDesiredGroup(newNodeList.get(0), PAtoShare);
		String PANode = newNodeList.get(0);

		// Impersonate PA User to RMU
		base.stepInfo("Impersonating PA user as RMU");
		base.impersonatePAtoRMU();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.stepInfo("Verifying whether the search saved by PA role is not available for RMU user");
		try {
			if (saveSearch.getSGTab(PAtoShare).isElementAvailable(5)) {
				base.failedStep("Shared with project adminitrator security group is available for RMU user");
			}
		} catch (Exception e) {
			base.passedStep("Shared with project adminitrator security group is not available for RMU user");
			UtilityLog.info("Shared with project adminitrator security group is not available for RMU user");
		}

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		String PANodeSearchName = nodeSearchpair.get(newNodeList.get(0));
		saveSearch.verifySharedGroupSearch1(mySavedSearch, PANodeSearchName, false);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.stepInfo("Verifying whether the shared saved search is available in security group");
		saveSearch.getCollapsedSharedWithDefaultSecurityGroup().waitAndClick(10);
		saveSearch.verifySharedNode(newNodeList.get(0));
		saveSearch.verifySharedGroupSearch1(newNodeList.get(0), nodeSearchpair.get(newNodeList.get(0)), true);

		// Landed on Saved Search
		base.selectproject();
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("PA", "No", 1);
		System.out.println("Node creation is done followed by adding searches to the created nodes");
		base.stepInfo("Node creation is done followed by adding searches to the created nodes");

		// Adding searches to the created nodes
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		base.stepInfo("-------Pre-requesties completed--------");

		// Search ID collection
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.shareSavedNodeWithDesiredGroup(newNodeList.get(0), SGtoShare);
		String RMUNode = newNodeList.get(0);

		// Impersonate RMU User to Reviewer
		base.stepInfo("Impersonating RMU user as Reviewer");
		base.impersonateRMUtoReviewer();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.stepInfo("Verifying whether the search saved by PA role is not available for Reviewer user");
		try {
			if (saveSearch.getSGTab(PAtoShare).isElementAvailable(5)) {
				base.failedStep("Shared with project adminitrator security group is available for Reviewer user");
			}
		} catch (Exception e) {
			base.passedStep("Shared with project adminitrator security group is not available for Reviewer user");
			UtilityLog.info("Shared with project adminitrator security group is not available for Reviewer user");
		}

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.verifySharedGroupSearch1(mySavedSearch, PANodeSearchName, false);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.stepInfo("Verifying whether the shared saved search is available in security group");
		saveSearch.getCollapsedSharedWithDefaultSecurityGroup().waitAndClick(10);
		saveSearch.verifySharedNode(PANode);
		saveSearch.verifySharedGroupSearch1(PANode, PANodeSearchName, true);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.stepInfo("Verifying whether the shared saved search by RMU is available in security group");
		saveSearch.getCollapsedSharedWithDefaultSecurityGroup().waitAndClick(10);
		saveSearch.verifySharedNode(RMUNode);
		saveSearch.verifySharedGroupSearch1(RMUNode, nodeSearchpair.get(RMUNode), true);
		String RMUNodeSearchName = nodeSearchpair.get(RMUNode);
		// Landed on Saved Search
		base.selectproject();
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("PA", "No", 1);
		System.out.println("Node creation is done followed by adding searches to the created nodes");
		base.stepInfo("Node creation is done followed by adding searches to the created nodes");

		// Adding searches to the created nodes
		driver.getWebDriver().get(Input.url + "Search/Searches");
		driver.waitForPageToBeReady();
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);

		// Search ID collection
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base.waitForElement(saveSearch.getSavedSearchNewGroupExpand());
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.shareSavedNodeWithDesiredGroup(newNodeList.get(0), SGtoShare);
		String reviewerNode = newNodeList.get(0);

		// Impersonate PA User to RMU
		base.stepInfo("Impersonating Reviewer user as RMU");
		driver.scrollPageToTop();
		base.impersonateReviewertoRMU();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.verifySharedGroupSearch1(mySavedSearch, PANodeSearchName, false);
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.selectNode1(RMUNode);
		driver.waitForPageToBeReady();
		saveSearch.verifySharedGroupSearch1(RMUNode, RMUNodeSearchName, true);
		saveSearch.selectNode1(reviewerNode);
		saveSearch.verifySharedGroupSearch1(reviewerNode, nodeSearchpair.get(reviewerNode), true);
		String reviewerSearchName = nodeSearchpair.get(reviewerNode);
		// Impersonate PA User to RMU
		base.stepInfo("Impersonating SA user as PA");
		base.impersonateSAtoPA();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		saveSearch.selectNode1(PANode);
		saveSearch.verifySharedGroupSearch1(PANode, PANodeSearchName, true);
		saveSearch.verifySharedGroupSearch1(mySavedSearch, reviewerSearchName, false);
		saveSearch.verifySharedGroupSearch1(mySavedSearch, RMUNodeSearchName, false);

		login.logout();

	}

	/*
	 * @author jeevitha Description: (RPMXCON-57393)RMU/RU scheduling searches saved
	 * under My Saved Searches and verify documents
	 */
	@Test(description = "RPMXCON-57393", enabled = true, dataProvider = "SavedSearchwithRMUandREV", groups = {
			"regression" })
	public void verifySchedulingSearches(String userName, String password, String fullName) throws Exception {
		String savedSearch = "Search1" + Utility.dynamicNameAppender();
		String savedSearch2 = "Search2" + Utility.dynamicNameAppender();
		String savedSearch3 = "Search3" + Utility.dynamicNameAppender();
		String savedSearch4 = "Search4" + Utility.dynamicNameAppender();
		SchedulesPage schedule = new SchedulesPage(driver);
		String pureHit0;
		int pureHit4 = 0;

		// Login as PA
		login.loginToSightLine(userName, password);
		base.stepInfo("Test case Id: RPMXCON-57393 - Saved Search Sprint 04");

		// create Node
		String newNode1 = saveSearch.createSearchGroupAndReturn(searchName, fullName, "Yes");
		String newNode2 = saveSearch.createSearchGroupAndReturn(searchName, fullName, "Yes");
		String newNode3 = saveSearch.createSearchGroupAndReturn(searchName, fullName, "Yes");
		String newNode4 = saveSearch.createSearchGroupAndReturn(searchName, fullName, "Yes");

		int pureHit1 = session.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		session.saveSearchInNewNode(savedSearch, newNode1);

		session.getNewSearchButton().waitAndClick(20);
		int pureHit2 = session.advancedContentSearchWithSearchChanges(Input.searchString1, "Yes");
		session.saveSearchInNewNode(savedSearch2, newNode2);

		base.selectproject();
		int pureHit3 = session.conceptualSearch_new("field report", "mid");
		session.saveSearchInNewNode(savedSearch3, newNode3);
		System.out.println(pureHit3);

		base.selectproject();
		session.switchToWorkproduct();
		session.selectRedactioninWPS(Input.defaultRedactionTag);
		pureHit4 = session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearchInNewNode(savedSearch4, newNode4);
		System.out.println(pureHit4);

		String dataSet[][] = { { savedSearch, String.valueOf(pureHit1) }, { savedSearch2, String.valueOf(pureHit2) },
				{ savedSearch3, String.valueOf(pureHit3) }, { savedSearch4, String.valueOf(pureHit4) } };
		String nodeList[][] = { { newNode1 }, { newNode2 }, { newNode3 }, { newNode4 } };

		int k = 0;
		for (int i = 0; i < dataSet.length; i++) {

			int j = 0;
			int l = 0;
			String searches = dataSet[i][j];
			j++;
			pureHit0 = dataSet[i][j];
			String Nodes = nodeList[k][l];

			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			// Schedule save search
			saveSearch.scheduleSavedSearchInMinute(searches, 2);

			// Make sure shared Node reflected in the SG
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectRootGroupTab(Input.mySavedSearch);
			saveSearch.selectNode1(Nodes);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");

			// verify Document count After Execution
			String actualDocCount = saveSearch.getCountofDocs().getText();
			System.out.println(actualDocCount);
			softAssertion.assertEquals(pureHit0, actualDocCount);

			// Perform Bulk action
			driver.Navigate().refresh();
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectRootGroupTab(Input.mySavedSearch);
			saveSearch.selectNode1(Nodes);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");
			saveSearch.saveSearchToDoclist();
			k++;
		}
		softAssertion.assertAll();
		login.logout();
	}

	/*
	 * @author jeevitha Description: (RPMXCON-57393)RMU/RU scheduling searches saved
	 * under My Saved Searches and verify documents
	 */
	@Test(description = "RPMXCON-57393", enabled = true, dataProvider = "SavedSearchwithRMUandREV", groups = {
			"regression" })
	public void verifySchedulingSearches2(String userName, String password, String fullName) throws Exception {
		String savedSearch = "Search1" + Utility.dynamicNameAppender();
		String savedSearch2 = "Search2" + Utility.dynamicNameAppender();
		String savedSearch3 = "Search3" + Utility.dynamicNameAppender();
		String savedSearch4 = "Search4" + Utility.dynamicNameAppender();
		SchedulesPage schedule = new SchedulesPage(driver);
		String pureHit0;
		int pureHit4 = 0;

		// Login as PA
		login.loginToSightLine(userName, password);
		base.stepInfo("Test case Id: RPMXCON-57393 - Saved Search Sprint 04");

		// create Node
		String newNode1 = saveSearch.createSearchGroupAndReturn(searchName, fullName, "Yes");
		String child1 = saveSearch.createNewSearchGrp(newNode1, 1);
		String newNode2 = saveSearch.createSearchGroupAndReturn(searchName, fullName, "Yes");
		String child2 = saveSearch.createNewSearchGrp(newNode2, 1);
		String newNode3 = saveSearch.createSearchGroupAndReturn(searchName, fullName, "Yes");
		String child3 = saveSearch.createNewSearchGrp(newNode3, 1);
		String newNode4 = saveSearch.createSearchGroupAndReturn(searchName, fullName, "Yes");
		String child4 = saveSearch.createNewSearchGrp(newNode4, 1);

		int pureHit1 = session.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		session.saveSearchInRootNode(savedSearch, newNode1, child1);

		session.getNewSearchButton().waitAndClick(20);
		int pureHit2 = session.advancedContentSearchWithSearchChanges(Input.searchString1, "Yes");
		session.saveSearchInRootNode(savedSearch2, newNode2, child2);

		base.selectproject();
		int pureHit3 = session.conceptualSearch_new("field report", "mid");
		session.saveSearchInRootNode(savedSearch3, newNode3, child3);
		System.out.println(pureHit3);

		base.selectproject();
		session.switchToWorkproduct();
		session.selectRedactioninWPS(Input.defaultRedactionTag);
		pureHit4 = session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearchInRootNode(savedSearch4, newNode4, child4);
		System.out.println(pureHit4);

		String dataSet[][] = { { savedSearch, String.valueOf(pureHit1) }, { savedSearch2, String.valueOf(pureHit2) },
				{ savedSearch3, String.valueOf(pureHit3) }, { savedSearch4, String.valueOf(pureHit4) } };
		String nodeList[][] = { { newNode1, child1 }, { newNode2, child2 }, { newNode3, child3 },
				{ newNode4, child4 } };

		int k = 0;

		for (int i = 0; i < dataSet.length; i++) {

			int j = 0;
			int l = 0;
			String searches = dataSet[i][j];
			j++;
			pureHit0 = dataSet[i][j];
			String Nodes = nodeList[k][l];
			l++;
			String childNode = nodeList[k][l];

			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.selectNode1(childNode);
			// Schedule save search
			saveSearch.scheduleSavedSearchInMinute(searches, 2);

			// Make sure shared Node reflected in the SG
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.selectNode1(childNode);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");

			// verify Document count After Execution
			String actualDocCount = saveSearch.getCountofDocs().getText();
			System.out.println(actualDocCount);
			softAssertion.assertEquals(pureHit0, actualDocCount);

			// Perform Bulk action
			driver.Navigate().refresh();
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.selectNode1(childNode);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");
			saveSearch.saveSearchToDoclist();
			k++;

		}
		softAssertion.assertAll();
		login.logout();

	}

	/*
	 * @author jeevitha Description: (RPMXCON-57396)DAU impersonating down as RMU/RU
	 * schedule searches saved under MY Saved Searches
	 */
	@Test(description = "RPMXCON-57396", enabled = true, dataProvider = "userRolesOnlyrmuAndRev", groups = {
			"regression" })
	public void verifyDASchedulingSearches(String role) throws Exception {
		String savedSearch = "Search1" + Utility.dynamicNameAppender();
		String savedSearch2 = "Search2" + Utility.dynamicNameAppender();
		String savedSearch3 = "Search3" + Utility.dynamicNameAppender();
		String savedSearch4 = "Search4" + Utility.dynamicNameAppender();
		SchedulesPage schedule = new SchedulesPage(driver);
		String pureHit0;
		int pureHit4 = 0;

		// Login as PA
		login.loginToSightLine(Input.da1userName, Input.da1password);
		if (role.equalsIgnoreCase("RMU")) {
			base.impersonateSAtoRMU();
		} else if (role.equalsIgnoreCase("REV")) {
			base.impersonateSAtoReviewer();
		}
		base.stepInfo("Impersonated as : " + role);
		base.stepInfo("Test case Id: RPMXCON-57396 - Saved Search Sprint 04");

		// create Node
		String newNode1 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String newNode2 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String newNode3 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String newNode4 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");

		int pureHit1 = session.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		session.saveSearchInNewNode(savedSearch, newNode1);

		session.getNewSearchButton().waitAndClick(20);
		int pureHit2 = session.advancedContentSearchWithSearchChanges(Input.searchString1, "Yes");
		session.saveSearchInNewNode(savedSearch2, newNode2);

		base.selectproject();
		int pureHit3 = session.conceptualSearch_new("field report", "mid");
		session.saveSearchInNewNode(savedSearch3, newNode3);
		System.out.println(pureHit3);

		base.selectproject();
		session.switchToWorkproduct();
		session.selectRedactioninWPS(Input.defaultRedactionTag);
		pureHit4 = session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearchInNewNode(savedSearch4, newNode4);
		System.out.println(pureHit4);

		String dataSet[][] = { { savedSearch, String.valueOf(pureHit1) }, { savedSearch2, String.valueOf(pureHit2) },
				{ savedSearch3, String.valueOf(pureHit3) }, { savedSearch4, String.valueOf(pureHit4) } };
		String nodeList[][] = { { newNode1 }, { newNode2 }, { newNode3 }, { newNode4 } };

		int k = 0;
		for (int i = 0; i < dataSet.length; i++) {

			int j = 0;
			int l = 0;
			String searches = dataSet[i][j];
			j++;
			pureHit0 = dataSet[i][j];
			String Nodes = nodeList[k][l];

			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			// Schedule save search
			saveSearch.scheduleSavedSearchInMinute(searches, 2);

			// Make sure shared Node reflected in the SG
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");

			// verify Document count After Execution
			String actualDocCount = saveSearch.getCountofDocs().getText();
			System.out.println(actualDocCount);
			softAssertion.assertEquals(pureHit0, actualDocCount);

			// Perform Bulk action
			driver.Navigate().refresh();
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");
			saveSearch.saveSearchToDoclist();
			k++;
		}
		softAssertion.assertAll();
		login.logout();
	}

	/*
	 * @author jeevitha Description: (RPMXCON-57396)DAU impersonating down as RMU/RU
	 * schedule searches saved under MY Saved Searches
	 * 
	 */
	@Test(description = "RPMXCON-57396", enabled = true, dataProvider = "userRolesOnlyrmuAndRev", groups = {
			"regression" })
	public void verifyDASchedulingSearches2(String role) throws Exception {
		String savedSearch = "Search1" + Utility.dynamicNameAppender();
		String savedSearch2 = "Search2" + Utility.dynamicNameAppender();
		String savedSearch3 = "Search3" + Utility.dynamicNameAppender();
		String savedSearch4 = "Search4" + Utility.dynamicNameAppender();
		SchedulesPage schedule = new SchedulesPage(driver);
		String pureHit0;
		int pureHit4 = 0;

		// Login as PA
		login.loginToSightLine(Input.da1userName, Input.da1password);
		if (role.equalsIgnoreCase("RMU")) {
			base.impersonateSAtoRMU();
		} else if (role.equalsIgnoreCase("REV")) {
			base.impersonateSAtoReviewer();
		}
		base.stepInfo("Impersonated as : " + role);
		base.stepInfo("Test case Id: RPMXCON-57396 - Saved Search Sprint 04");

		// create Node
		String newNode1 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String child1 = saveSearch.createNewSearchGrp(newNode1, 1);
		String newNode2 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String child2 = saveSearch.createNewSearchGrp(newNode2, 1);
		String newNode3 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String child3 = saveSearch.createNewSearchGrp(newNode3, 1);
		String newNode4 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String child4 = saveSearch.createNewSearchGrp(newNode4, 1);

		int pureHit1 = session.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		session.saveSearchInRootNode(savedSearch, newNode1, child1);

		session.getNewSearchButton().waitAndClick(20);
		int pureHit2 = session.advancedContentSearchWithSearchChanges(Input.searchString1, "Yes");
		session.saveSearchInRootNode(savedSearch2, newNode2, child2);

		base.selectproject();
		int pureHit3 = session.conceptualSearch_new("field report", "mid");
		session.saveSearchInRootNode(savedSearch3, newNode3, child3);
		System.out.println(pureHit3);

		base.selectproject();
		session.switchToWorkproduct();
		session.selectRedactioninWPS(Input.defaultRedactionTag);
		pureHit4 = session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearchInRootNode(savedSearch4, newNode4, child4);
		System.out.println(pureHit4);

		String dataSet[][] = { { savedSearch, String.valueOf(pureHit1) }, { savedSearch2, String.valueOf(pureHit2) },
				{ savedSearch3, String.valueOf(pureHit3) }, { savedSearch4, String.valueOf(pureHit4) } };
		String nodeList[][] = { { newNode1, child1 }, { newNode2, child2 }, { newNode3, child3 },
				{ newNode4, child4 } };

		int k = 0;

		for (int i = 0; i < dataSet.length; i++) {

			int j = 0;
			int l = 0;
			String searches = dataSet[i][j];
			j++;
			pureHit0 = dataSet[i][j];
			String Nodes = nodeList[k][l];
			l++;
			String childNode = nodeList[k][l];

			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.selectNode1(childNode);
			// Schedule save search
			saveSearch.scheduleSavedSearchInMinute(searches, 2);

			// Make sure shared Node reflected in the SG
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.selectNode1(childNode);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");

			// verify Document count After Execution
			String actualDocCount = saveSearch.getCountofDocs().getText();
			System.out.println(actualDocCount);
			softAssertion.assertEquals(pureHit0, actualDocCount);

			// Perform Bulk action
			driver.Navigate().refresh();
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.selectNode1(childNode);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");
			saveSearch.saveSearchToDoclist();
			k++;

		}
		softAssertion.assertAll();
		login.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws Exception
	 * @description Verify that when batch upload is done with Security Groups then
	 *              count should be displayed correctly on saved search after Search
	 *              completes
	 */
	@Test(description = "RPMXCON-49092", enabled = true, groups = { "regression" })
	public void validateSecurityGroupCountsThroughBatchFile() throws Exception {
		base.stepInfo("Test case Id: RPMXCON-49092");
		base.stepInfo(
				"Verify that when batch upload is done with Security Groups then count should be displayed correctly on saved search after Search completes");
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		session.switchToWorkproduct();
		session.selectSecurityGinWPS(Input.securityGroup);
		int pureHits = session.serarchWP();
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.uploadWPBatchFile(saveSearch.renameFile(Input.WPbatchFile));
		base.passedStep("Batch file uploaded successfully");
		saveSearch.verifyDocCountsAndStatus("WP securityGroups", pureHits);
		driver.scrollPageToTop();
		saveSearch.getSavedSearchDeleteButton().waitAndClick(10);
		base.waitForElement(saveSearch.getDeleteOkBtn());
		saveSearch.getDeleteOkBtn().waitAndClick(10);
		base.VerifySuccessMessage("Save search tree node successfully deleted.");
		login.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException
	 * @description Verify that when batch upload is done with Assignments then
	 *              count should be displayed correctly on saved search after Search
	 *              completes
	 */

	@Test(description = "RPMXCON-49095", enabled = true, groups = { "regression" })

	public void validateAssignmentDocCountsThroughBatchFile() throws Exception {
		base.stepInfo("Test case Id: RPMXCON-49095");
		base.stepInfo(
				"Verify that when batch upload is done with Assignments then count should be displayed correctly on saved search after Search completes");
		login.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		String assignmentName = "batchAssignment";
		assign.createAssignment(assignmentName, Input.codeFormName);
		try {
			int pureHits = session.basicContentSearch("null");
			session.bulkAssignExisting(assignmentName);
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.uploadWPBatchFile(saveSearch.renameFile(Input.WPbatchFile));
			base.passedStep("Batch file uploaded successfully");
			saveSearch.verifyDocCountsAndStatus("WP assignment", pureHits);
			driver.scrollPageToTop();
			saveSearch.getSavedSearchDeleteButton().waitAndClick(10);
			base.waitForElement(saveSearch.getDeleteOkBtn());
			saveSearch.getDeleteOkBtn().waitAndClick(10);
			base.VerifySuccessMessage("Save search tree node successfully deleted.");
			assign.deleteAssgnmntUsingPagination(assignmentName);
			login.logout();
		} catch (Exception e) {
			assign.deleteAssgnmntUsingPagination(assignmentName);
			base.failedStep("Failed to validate document counts via batchfile ");
		}

	}

	/**
	 * @author jeevitha Description : Verify status and count in Saved Search screen
	 *         when user uploads Batch Search file with Basic and Advanced search
	 *         queries (RPMXCON-48536)
	 */
	@Test(description = "RPMXCON-48536", enabled = true, groups = { "regression" })
	public void verifyBatchUpload() throws Exception {
		String advanceSearch = "Basic Work Product";
		String basicsearch = "Basic Content Search without Family Members";
		String File = saveSearch.renameFile(Input.WPbatchFile);
		String nearDupe = "Near Duplicate Count";

		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Test case Id: RPMXCON-48536  Saved Search");
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.uploadWPBatchFile(File);
		saveSearch.getDocCountAndStatusOfBatch(advanceSearch, nearDupe, true);

		driver.waitForPageToBeReady();
		saveSearch.getDocCountAndStatusOfBatch(basicsearch, nearDupe, true);

		// Delete Uploaded File
		driver.Navigate().refresh();
		saveSearch.getMySeraches().waitAndClick(10);
		base.waitForElement(saveSearch.getSelectUploadedFile(File));
		saveSearch.getSelectUploadedFile(File).waitAndClick(20);

		saveSearch.deleteFunctionality();
		login.logout();
	}

	/**
	 * @author jeevitha Description : Verify that when batch upload is done with
	 *         Tags then count should be displayed correctly on saved search after
	 *         Search completes (RPMXCON-49089) @modified on : 12/4/21
	 */
	@Test(description = "RPMXCON-49089", enabled = true, dataProvider = "SavedSearchwithoutReviewer", groups = {
			"regression" })
	public void validateTagThroughBatchFile(String username, String password) throws Exception {
		String tag = "WPTag";
		String File = saveSearch.renameFile(Input.WPbatchFile);

		// login
		login.loginToSightLine(username, password);
		base.stepInfo("Test case Id: RPMXCON-49089 Saved Search");
		base.stepInfo(
				"Verify that when batch upload is done with Tags then count should be displayed correctly on saved search after Search completes");

		// Basic Search and perform Bulk Tag
		int pureHits = session.basicContentSearch(Input.searchString1);
		try {
			session.bulkTag(tag);
		} catch (InterruptedException e) {
			System.out.println("Tag Already Exists");
			session.bulkTagExisting(tag);
		}

		// upload batch File And verify Status
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.uploadWPBatchFile(File);
		saveSearch.verifyDocCountsAndStatus(tag, pureHits);

		// Delete Uploaded File
		driver.Navigate().refresh();
		saveSearch.getMySeraches().waitAndClick(10);
		base.waitForElement(saveSearch.getSelectUploadedFile(File));
		saveSearch.getSelectUploadedFile(File).waitAndClick(20);

		saveSearch.deleteFunctionality();

		// Delete Tag
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteTagWithClassificationInRMU(tag);

		login.logout();
	}

	/**
	 * @author jeevitha Description : Verify that when batch upload is done with
	 *         Folders then count should be displayed correctly on saved search
	 *         after Search completes (RPMXCON-49090)
	 * @modified on : 12/4/21 by : Jeevitha
	 */
	@Test(description = "RPMXCON-49090", enabled = true, dataProvider = "SavedSearchwithoutReviewer", groups = {
			"regression" })
	public void validateFolderThroughBatchFile(String username, String password) throws Exception {
		String folder = "WPFolder";
		String File = saveSearch.renameFile(Input.WPbatchFile);

		// login
		login.loginToSightLine(username, password);
		base.stepInfo("Test case Id: RPMXCON-49090 Saved Search");
		base.stepInfo(
				"Verify that when batch upload is done with Folders then count should be displayed correctly on saved search after Search completes");

		// Basic Search and perform Bulk Tag
		int pureHits = session.basicContentSearch(Input.searchString1);
		try {
			session.bulkFolder(folder);
		} catch (InterruptedException e) {
			System.out.println("Folder Already Exists");
			session.bulkFolderExisting(folder);
		}

		// upload batch File And verify Status
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.uploadWPBatchFile(File);
		saveSearch.verifyDocCountsAndStatus(folder, pureHits);

		// Delete Uploaded File
		driver.Navigate().refresh();
		saveSearch.getMySeraches().waitAndClick(10);
		base.waitForElement(saveSearch.getSelectUploadedFile(File));
		saveSearch.getSelectUploadedFile(File).waitAndClick(20);

		saveSearch.deleteFunctionality();
		// delete folder
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.DeleteFolderWithSecurityGroupInRMU(folder);
		login.logout();
	}

	/**
	 * @author Raghuram A Description : Verify that application displays all
	 *         documents that are in the aggregate results set of Shared groups and
	 *         Export (RPMXCON-49016)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49016", enabled = true, groups = { "regression" })
	public void verifyExportDocs() throws InterruptedException {

		String SearchName = "Search" + Utility.dynamicNameAppender();
		String folderName = "Folder" + Utility.dynamicNameAppender();

		base.stepInfo("Test case Id: RPMXCON-49016 - Saved Search Sprint 05");
		base.stepInfo(
				"Verify that application displays all documents that are in the aggregate results set of Shared groups and Export");
		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo(Input.pa1FullName);

		// create new search group and save search
		String new_node = saveSearch.createASearchGroupandReturnName(SearchName);

		// create folder and add save search in folder
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(folderName, Input.securityGroup);

		// Search and Save
		int purehit = session.basicContentSearch(Input.searchString1);
		// session.saveSearchInNode(SearchName);
		session.saveSearchInNewNode(SearchName, new_node);
		driver.waitForPageToBeReady();
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		base.stepInfo("Landed on SavedSearchPage");

		// Share under available shared groups
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		String searchID1 = saveSearch.shareSavedNodePA("Shared with Default Security Group", new_node, true, true,
				SearchName); // Input.shareSearchDefaultSG

		// Make sure shared Node reflected in the SG & Select the shared Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getSavedSearchGroupName("Shared with Default Security Group").waitAndClick(10); // Input.shareSearchDefaultSG
		saveSearch.selectNode1(new_node);
		saveSearch.savedSearch_SearchandSelect(SearchName, "Yes"); // modified

		// Verify Export
		saveSearch.getSavedSearchExportButton().Click();
		base.waitForElement(saveSearch.getExportPopup());

		report.customDataReportMethodExport(folderName, false);
		driver.waitForPageToBeReady();

		// Download report
		report.downLoadReport();
		base.stepInfo("File Downloaded");

		base.stepInfo(
				"Verifying Exported file lists all the documents from the search group with  selected metadata and work product.");
		int countToCompare = saveSearch.fileVerificationSpecificMethod();
		base.stepInfo("Document count from the export : " + countToCompare);
		softAssertion.assertEquals(countToCompare, purehit);
		if (countToCompare == purehit) {
			System.out.println("Pass");
			base.passedStep(
					"Exported file lists all the documents from the search group with  selected metadata and work product -Purehit and File count matches");
		} else {
			System.out.println("Fail");
			base.failedStep("Purehit and File count doesn't match");
		}

		softAssertion.assertAll();
		login.logout();

	}

	/**
	 * @author Raghuram A Description : Validate DAU user is allowed to move
	 *         SearchGroups/Searches only within MySearches (RPMXCON-49948)
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49948", enabled = true, groups = { "regression" })
	public void verifyMoveActionViaDAU() throws InterruptedException {

		base.stepInfo("RPMXCON-49948 Saved Search - Sprint 05");
		base.stepInfo("Validate DAU user is allowed to move SearchGroups/Searches only within MySearches");
		// Login as DAU
		login.loginToSightLine(Input.da1userName, Input.da1password);

		// Validate DAU user is allowed to move SearchGroups/Searches
		saveSearch.validateMoveActionsVIaDiffRoles();

		login.logout();

	}

	/**
	 * @author Raghuram A Description : To verify that user can release all the
	 *         tagged document into a security group which is associated from Save
	 *         Search->Bulk Tag. (RPMXCON-49957) Modified on : 12/4/21 - delete
	 *         datas
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-49957", enabled = true, groups = { "regression" })
	public void releaseAllTagtoSGviaSS() throws InterruptedException {
		String TagName = "Tag" + Utility.dynamicNameAppender(), finalCount;
		int pureHit, finalCountresult;
		int latencyCheckTime = 5;
		String passMessage = "Application not hang or shows latency more than " + latencyCheckTime + " seconds.";
		String failureMsg = "Continues Loading more than " + latencyCheckTime + " seconds.";
		String SearchName = "Search Name" + Utility.dynamicNameAppender();

		base.stepInfo("RPMXCON-49957 Saved Search - Sprint 05");
		base.stepInfo(
				"To verify that user can release all the tagged document into a security group which is associated from Save Search->Bulk Tag.");
		// Login as User
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin as : " + Input.pa1FullName);

		// create new search group and save search
		saveSearch.createSearchGroupAndReturn("Input.mysavedsearch", "PA", "No");
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		String new_node = saveSearch.getSavedSearchNewNode().getText();
		base.stepInfo("Search and saveSearch in the created node");
		int purehit = session.basicContentSearch(Input.searchString1);
//		session.saveSearchInNode(SearchName);
		session.saveSearchInNewNode(SearchName, new_node);
		driver.waitForPageToBeReady();
		base.selectproject();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		base.stepInfo("Landed on SavedSearchPage");
		saveSearch.selectNode1(new_node);
		saveSearch.savedSearch_SearchandSelect(SearchName, "Yes");
		saveSearch.getSavedSearchToBulkTag().Click();
		base.stepInfo("Clicked tag icon from Code");

		// Load latency Verification
		Element loadingElement = saveSearch.getTotalCountLoad();
		saveSearch.loadingCountVerify(loadingElement, latencyCheckTime, passMessage, failureMsg);
		finalCount = session.bulkActions_TagSS_returnCount(TagName);
		base.stepInfo("Completed Bulk Tag");

		// Tag >> Bulk release action
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.getTagName(TagName).waitAndClick(5);
		tagsAndFolderPage.selectActionArrow("Bulk release");
		int bulkReleaseDocCount = tagsAndFolderPage.verifyBulk_releaseCount(Input.securityGroup);
		base.stepInfo("Documents released to the tag : " + TagName + "  with SG : " + Input.securityGroup);

		base.stepInfo(
				"Navigating to Search >> Basic Search >> Advanced Search >> WorkProduct >> Tags >> Verify document count.");
		session.switchToWorkproduct();
		session.selectTagInASwp(TagName);
		pureHit = session.serarchWP();
		if (bulkReleaseDocCount == pureHit) {
			softAssertion.assertEquals(bulkReleaseDocCount, pureHit);
			base.passedStep("All documents released to the Security group.");
		} else {
			base.failedStep("Count Mismatches");
		}

		// Delete Node
		base.stepInfo("Initiated Delete Searches");
		saveSearch.deleteNode(Input.mySavedSearch, new_node);

		softAssertion.assertAll();
		login.logout();

	}

	/**
	 * @author Raghuram A Description : SA/DA/PA user impersonate down as RMU/RU
	 *         role, create Searchgroups and Searches, and then runs the
	 *         Communication report against My saved searches in PAU role
	 *         (RPMXCON-57402) Sprint 05
	 * @throws InterruptedException
	 */
	@Test(description = "RPMXCON-57402", enabled = true, dataProvider = "UserSaUDaUPaU", groups = { "regression" })
	public void communicatioNReport(String username, String password, String fullName, String role)
			throws InterruptedException {

		String searchName = "Search Name" + Utility.dynamicNameAppender();
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();

		report = new ReportsPage(driver);
		base.stepInfo("Test case Id: RPMXCON-57402 - Saved Search Sprint 05");
		base.stepInfo(
				"SA/DA/PA user impersonate down as RMU/RU role, create Searchgroups and Searches, and then runs the Communication report against My saved searches in PAU role");

		// Login via User
		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin As : " + role);
		// Pre-requesties
		base.rolesToImp(role, "PA");
		String newNodeFromPA = saveSearch.createSearchGroupAndReturn(searchName, "PA");
		int purehit = session.basicContentSearch(Input.searchString1);

		// Impersonate As RMU via PA and create new searchgroup
		base.rolesToImp("PA", "RMU");
		String newNodeFromRMU = saveSearch.createSearchGroupAndReturn(searchName, "RMU");

		// Save Search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);
		// session.saveSearchInNode(searchName);
		session.saveSearchInNewNode(searchName, newNodeFromRMU);

		// impersonate As REV and create new searchgroup
		base.rolesToImp("RMU", "REV");
		String newNodeFromRev = saveSearch.createSearchGroupAndReturn(searchName, "REV");

		// Save search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName1);
		// session.saveSearchInNode(searchName1);
		session.saveSearchInNewNode(searchName1, newNodeFromRev);
		driver.waitForPageToBeReady();

		// impersonate As Reviewer to RMU
		driver.waitForPageToBeReady();
		base.rolesToImp("REV", "RMU");

		report.VerificationAndrcommunicationReport(newNodeFromPA, newNodeFromRMU, newNodeFromRev, searchName,
				searchName1, purehit);

		login.logout();

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws Exception
	 * @description Verify that when batch upload is done with Saved Search Results
	 *              then count should be displayed correctly on saved search after
	 *              Search completes
	 */
	@Test(description = "RPMXCON-49093", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void validateSavedSearchResultsThroughBatchFile(String username, String password, String fullName)
			throws Exception {
		base.stepInfo("Test case Id: RPMXCON-49093");
		base.stepInfo(
				"Verify that when batch upload is done with Saved Search Results then count should be displayed correctly on saved search after Search completes");
		login.loginToSightLine(username, password);
		try {
			int pureHits = session.MetaDataSearchInAdvancedSearch(Input.metaDataName, "Andrew");
			session.saveSearchAdvanced("batchSavedSearch");
			base.selectproject();
			session.switchToWorkproduct();
			String id = session.getSavedSearchId("batchSavedSearch");
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			System.out.println(Input.WPbatchFile);
			String batchFIleName = saveSearch.renameFile(Input.WPbatchFile);
			System.out.println(batchFIleName);
			saveSearch.writeDataIntoExcel(batchFIleName, "savedSearches: [ ID: [" + id + "] ]");
			saveSearch.uploadWPBatchFile(batchFIleName);
			base.passedStep("Batch file uploaded successfully");
			driver.waitForPageToBeReady();
			saveSearch.verifyDocCountsAndStatus("WP savedSearch", pureHits);
			driver.scrollPageToTop();
			saveSearch.getSavedSearchDeleteButton().waitAndClick(10);
			base.waitForElement(saveSearch.getDeleteOkBtn());
			saveSearch.getDeleteOkBtn().waitAndClick(10);
			base.VerifySuccessMessage("Save search tree node successfully deleted.");
			saveSearch.SaveSearchDelete("batchSavedSearch");
			login.logout();
		} catch (Exception e) {
			saveSearch.SaveSearchDelete("batchSavedSearch");
			base.failedStep("Failed to validate document counts via batchfile ");
		}

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws Exception
	 * @description To verify Refresh action on saved search screen under My Saved
	 *              Search and Shared folders
	 */
	@Test(description = "RPMXCON-49960", enabled = true, dataProvider = "SavedSearchwithUsers", groups = {
			"regression" })
	public void validateSavedSearchResultsThroughBatchFileAftreRefreshing(String username, String password,
			String fullname) throws Exception {
		base.stepInfo("Test case Id: RPMXCON-49960");
		base.stepInfo("To verify Refresh action on saved search screen under My Saved Search and Shared folders");
		login.loginToSightLine(username, password);
		try {
			session.MetaDataSearchInAdvancedSearch(Input.metaDataName, "Andrew");
			session.saveSearchAdvanced("batchSavedSearch");
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			System.out.println(Input.WPbatchFile);
			saveSearch.uploadWPBatchFile(saveSearch.renameFile(Input.WPbatchFile));
			base.passedStep("Batch file uploaded successfully");
			saveSearch.selectSavedSearch("WP savedSearch");
			saveSearch.getSavedSearchRefresh().waitAndClick(5);
			base.waitForElement(saveSearch.getSavedSearch_SearchName());
			saveSearch.getSavedSearch_SearchName().SendKeys("WP savedSearch");
			base.waitTillElemetToBeClickable(saveSearch.getSavedSearch_ApplyFilterButton());
			saveSearch.getSavedSearch_ApplyFilterButton().waitAndClick(10);
			base.waitForElement(saveSearch.getStatusBySavedSearchStatus("WP savedSearch", "COMPLETED"));
			if (saveSearch.getStatusBySavedSearchStatus("WP savedSearch", "COMPLETED").isElementPresent() == true) {
				base.passedStep("After the refreshing the query the status is displayed as completed as expected");
			} else {
				base.failedStep("After the refreshing the query the status is not displayed as completed");
			}
			driver.scrollPageToTop();
			saveSearch.getSavedSearchDeleteButton().waitAndClick(10);
			base.waitForElement(saveSearch.getDeleteOkBtn());
			saveSearch.getDeleteOkBtn().waitAndClick(10);
			base.VerifySuccessMessage("Save search tree node successfully deleted.");
			base.waitTillElemetToBeClickable(saveSearch.getSavedSearchGroupName(Input.mySavedSearch));
			saveSearch.getSavedSearchGroupName(Input.mySavedSearch).waitAndClick(5);
			saveSearch.selectSavedSearch("batchSavedSearch");
			saveSearch.getSavedSearchRefresh().waitAndClick(5);
			saveSearch.selectSavedSearch("batchSavedSearch");
			base.waitForElement(saveSearch.getStatusBySavedSearchStatus("batchSavedSearch", "COMPLETED"));
			if (saveSearch.getStatusBySavedSearchStatus("batchSavedSearch", "COMPLETED").isElementPresent() == true) {
				base.passedStep("After the refreshing the query the status is displayed as completed as expected");
			} else {
				base.failedStep("After the refreshing the query the status is not displayed as completed");
			}
			saveSearch.SaveSearchDelete("batchSavedSearch");
			login.logout();
		} catch (Exception e) {
			saveSearch.SaveSearchDelete("batchSavedSearch");
			base.failedStep("Failed to validate document counts via batchfile ");
		}

	}

	/**
	 * @author Mohan Date: 11/01/21 Modified date:N/A Modified by: N/A Description :
	 *         Validate executing searches/groups from the shared with PAU by any
	 *         other PAU user-RPMXCON-49854 Sprint 05
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	@Test(description = "RPMXCON-49854", enabled = true, groups = { "regression" })
	public void validateSavedSearchPageProperOptions() throws InterruptedException, ParseException {
		int noOfNodesToCreate = 2;
		int selectIndex = 1;
		int rowNo = 9;
		int countNo = 3;
		String nodeName = Input.shareSearchPA;
		String defaultTab = "Shared with Default Security Group";
		String node;
		Boolean inputValue = true;
		List<String> newNodeList = new ArrayList<>();
		HashMap<String, String> nodeSearchpair = new HashMap<>();

		base.stepInfo("Test case Id: RPMXCON-49854");
		base.stepInfo(" Validate executing searches/groups from the shared with PAU by any other PAU user");

		// Login as PA
		login.loginToSightLine(Input.pa1userName, Input.pa1password);
		base.stepInfo("Loggedin As : " + Input.pa1FullName);

		// Landed on Saved Search
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		// Multiple Node Creation
		newNodeList = saveSearch.createSGAndReturn("PA", "No", noOfNodesToCreate);
		System.out.println("Node creation is done followed by adding searches to the created nodes");
		base.stepInfo("Node creation is done followed by adding searches to the created nodes");

		// Adding searches to the created nodes
		driver.getWebDriver().get(Input.url + "Search/Searches");
		nodeSearchpair = session.saveSearchInNodewithChildNode(newNodeList, inputValue);
		saveSearch.sortedMapList(nodeSearchpair);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(nodeName, node, false, true, nodeSearchpair.get(node));

		System.out.println(nodeSearchpair.get(node));
		node = saveSearch.childNodeSelectionToShare(selectIndex, newNodeList);
		System.out.println("Final : " + node);
		saveSearch.shareSavedNodePA(defaultTab, node, false, true, nodeSearchpair.get(node));

		base.stepInfo("-------Pre-requesties completed--------");

		base.waitForElement(saveSearch.getSavedSearchTreeNode(nodeName));
		saveSearch.getSavedSearchTreeNode(nodeName).waitAndClick(10);
		saveSearch.selectChildNodeOfSharedWithProjectAdmin();
		saveSearch.executeChildNodeOfProjectAdmin(rowNo, countNo);
		saveSearch.selectRootGroupTab(Input.shareSearchDefaultSG);
		saveSearch.rootGroupExpansion();
		saveSearch.selectChildNodeOfSharedWithDefault();
		saveSearch.executeAnySpecficSearch(rowNo, countNo);

		login.logout();

		// Login as PA
		login.loginToSightLine(Input.pa2userName, Input.pa2password);
		base.stepInfo("Loggedin As : " + Input.pa2FullName);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectChildNodeOfSharedWithProjectAdmin();
		saveSearch.verifyChildNodeCount(countNo);
		saveSearch.selectRootGroupTab(Input.shareSearchDefaultSG);
		saveSearch.rootGroupExpansion();
		saveSearch.selectChildNodeOfSharedWithDefault();
		saveSearch.verifyChildNodeCount(countNo);

		// logout
		login.logout();
	}

	/**
	 * @author jayanthi ganesan Description: (RPMXCON-57398)SA impersonate down as
	 *         PAU/RMU/RU, save search under <Shared with
	 *         SecurityGroupName>directly, schedule saved search and verify
	 *         documents
	 * 
	 */
	@Test(description = "RPMXCON-57398", enabled = true, dataProvider = "user_Roles", groups = { "regression" })
	public void verifySASchedulingSearches_Impersonation(String role) throws Exception {
		String savedSearch1 = "Search1" + Utility.dynamicNameAppender();
		String savedSearch2 = "Search2" + Utility.dynamicNameAppender();
		String savedSearch3 = "Search3" + Utility.dynamicNameAppender();
		String savedSearch4 = "Search4" + Utility.dynamicNameAppender();
		SchedulesPage schedule = new SchedulesPage(driver);
		String pureHit0;
		int pureHit4 = 0;

		// Login as SA
		login.loginToSightLine(Input.sa1userName, Input.sa1password);
		base.stepInfo("Logged in as SA");
		if (role.equalsIgnoreCase("RMU")) {
			base.impersonateSAtoRMU();
		} else if (role.equalsIgnoreCase("REV")) {
			base.impersonateSAtoReviewer();
		} else if (role.equalsIgnoreCase("PA")) {
			base.impersonateSAtoPA();
		}
		base.stepInfo("Impersonated as : " + role);
		base.stepInfo("Test case Id: RPMXCON-57398");
		base.stepInfo(
				"SA impersonate down as PAU/RMU/RU, save search under <Shared with SecurityGroupName>directly,schedule "
						+ "saved search and verify documents");

		// create Node
		String newNode1 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String child1 = saveSearch.createNewSearchGrp(newNode1, 1);
		String newNode2 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String child2 = saveSearch.createNewSearchGrp(newNode2, 1);
		String newNode3 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String child3 = saveSearch.createNewSearchGrp(newNode3, 1);
		String newNode4 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String child4 = saveSearch.createNewSearchGrp(newNode4, 1);

		int pureHit1 = session.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		session.saveSearchInNewNode(savedSearch1, newNode1);
		session.saveSearchInRootNode(savedSearch1, newNode1, child1);

		session.getNewSearchButton().waitAndClick(20);
		int pureHit2 = session.advancedContentSearchWithSearchChanges(Input.searchString1, "Yes");
		session.saveSearchInNewNode(savedSearch2, newNode2);
		session.saveSearchInRootNode(savedSearch2, newNode2, child2);

		base.selectproject();
		int pureHit3 = session.conceptualSearch_new("field report", "mid");
		session.saveSearchInNewNode(savedSearch3, newNode3);
		session.saveSearchInRootNode(savedSearch3, newNode3, child3);
		System.out.println(pureHit3);

		base.selectproject();
		session.switchToWorkproduct();
		session.selectRedactioninWPS(Input.defaultRedactionTag);
		pureHit4 = session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearchInNewNode(savedSearch4, newNode4);
		session.saveSearchInRootNode(savedSearch4, newNode4, child4);
		System.out.println(pureHit4);
		// share node to SG
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.shareSavedNodeToSG(Input.shareSearchDefaultSG, newNode1, savedSearch1);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.shareSavedNodeToSG(Input.shareSearchDefaultSG, newNode2, savedSearch2);
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.shareSavedNodeToSG(Input.shareSearchDefaultSG, newNode3, savedSearch3);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.shareSavedNodeToSG(Input.shareSearchDefaultSG, newNode4, savedSearch4);

		String dataSet1[][] = { { savedSearch1, String.valueOf(pureHit1) }, { savedSearch2, String.valueOf(pureHit2) },
				{ savedSearch3, String.valueOf(pureHit3) }, { savedSearch4, String.valueOf(pureHit4) } };
		String nodeList1[][] = { { newNode1 }, { newNode2 }, { newNode3 }, { newNode4 } };

		int m = 0;
		for (int i = 0; i < dataSet1.length; i++) {

			int j = 0;
			int l = 0;
			String searches = dataSet1[i][j];
			j++;
			pureHit0 = dataSet1[i][j];
			String Nodes = nodeList1[m][l];

			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			// Schedule save search
			saveSearch.scheduleSavedSearchInMinute(searches, 2);

			// verify Document count
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");

			String actualDocCount = saveSearch.getCountofDocs().getText();
			System.out.println(actualDocCount);
			softAssertion.assertEquals(pureHit0, actualDocCount);

			// Verify doc count in DocList Page.
			int DocCountInDocList = saveSearch.launchDocListViaSSandReturnDocCount();
			try {
				softAssertion.assertEquals(Integer.parseInt(actualDocCount), DocCountInDocList);
				base.passedStep("DocCounts Displayed in DocListPage is as Expected");
			} catch (Exception e) {
				base.failedStep("DocCounts Displayed in DocListPage is not as Expected");

			}
			m++;
		}

		String dataSet[][] = { { savedSearch1, String.valueOf(pureHit1) }, { savedSearch2, String.valueOf(pureHit2) },
				{ savedSearch3, String.valueOf(pureHit3) }, { savedSearch4, String.valueOf(pureHit4) } };
		String nodeList[][] = { { newNode1, child1 }, { newNode2, child2 }, { newNode3, child3 },
				{ newNode4, child4 } };

		int k = 0;

		for (int i = 0; i < dataSet.length; i++) {

			int j = 0;
			int l = 0;
			String searches = dataSet[i][j];
			j++;
			pureHit0 = dataSet[i][j];
			String Nodes = nodeList[k][l];
			l++;
			String childNode = nodeList[k][l];

			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.selectNode1(childNode);
			// Schedule save search
			saveSearch.scheduleSavedSearchInMinute(searches, 2);

			// verify Document count
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.selectNode1(childNode);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");

			String actualDocCount = saveSearch.getCountofDocs().getText();
			System.out.println(actualDocCount);
			softAssertion.assertEquals(pureHit0, actualDocCount);

			// Verify DocCount in DocList Page.
			int DocCountInDocList = saveSearch.launchDocListViaSSandReturnDocCount();
			try {
				softAssertion.assertEquals(Integer.parseInt(actualDocCount), DocCountInDocList);
				base.passedStep("DocCounts Displayed in DocListPage is as Expected");
			} catch (Exception e) {
				base.failedStep("DocCounts Displayed in DocListPage is not as Expected");

			}
			k++;

		}
		softAssertion.assertAll();
		login.logout();

	}

	/**
	 * @author jayanthi ganesan Description: (RPMXCON-57397)DAU impersonate down as
	 *         PAU, save search under <Shared with SecurityGroupName>directly,
	 *         schedule saved searches and verify documents
	 * 
	 */
	@Test(description = "RPMXCON-57397", enabled = true, dataProvider = "user_Roles", groups = { "regression" })
	public void verifyDASchedulingSearches_Impersonation(String role) throws Exception {
		String savedSearch1 = "Search1" + Utility.dynamicNameAppender();
		String savedSearch2 = "Search2" + Utility.dynamicNameAppender();
		String savedSearch3 = "Search3" + Utility.dynamicNameAppender();
		String savedSearch4 = "Search4" + Utility.dynamicNameAppender();
		SchedulesPage schedule = new SchedulesPage(driver);
		String pureHit0;
		int pureHit4 = 0;

		// Login as DA
		login.loginToSightLine(Input.da1userName, Input.da1password);
		base.stepInfo("Logged in as DA");
		if (role.equalsIgnoreCase("RMU")) {
			base.impersonateSAtoRMU();
		} else if (role.equalsIgnoreCase("REV")) {
			base.impersonateSAtoReviewer();
		} else if (role.equalsIgnoreCase("PA")) {
			base.impersonateDAtoPA();
		}
		base.stepInfo("Impersonated as : " + role);
		base.stepInfo("DAU impersonate down as PAU, save search under <Shared with SecurityGroupName>directly,"
				+ " schedule saved searches and verify documents");
		base.stepInfo("Test case Id: RPMXCON-57397");
		// create Node
		String newNode1 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String child1 = saveSearch.createNewSearchGrp(newNode1, 1);
		String newNode2 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String child2 = saveSearch.createNewSearchGrp(newNode2, 1);
		String newNode3 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String child3 = saveSearch.createNewSearchGrp(newNode3, 1);
		String newNode4 = saveSearch.createSearchGroupAndReturn(searchName, role, "Yes");
		String child4 = saveSearch.createNewSearchGrp(newNode4, 1);

		int pureHit1 = session.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		session.saveSearchInNewNode(savedSearch1, newNode1);
		session.saveSearchInRootNode(savedSearch1, newNode1, child1);

		session.getNewSearchButton().waitAndClick(20);
		int pureHit2 = session.advancedContentSearchWithSearchChanges(Input.searchString1, "Yes");
		session.saveSearchInNewNode(savedSearch2, newNode2);
		session.saveSearchInRootNode(savedSearch2, newNode2, child2);

		base.selectproject();
		int pureHit3 = session.conceptualSearch_new("field report", "mid");
		session.saveSearchInNewNode(savedSearch3, newNode3);
		session.saveSearchInRootNode(savedSearch3, newNode3, child3);
		System.out.println(pureHit3);

		base.selectproject();
		session.switchToWorkproduct();
		session.selectRedactioninWPS(Input.defaultRedactionTag); // Input.defaultRedactionTag // modified by samapth
		pureHit4 = session.saveAndReturnPureHitCount();
		driver.scrollPageToTop();
		session.saveSearchInNewNode(savedSearch4, newNode4);
		session.saveSearchInRootNode(savedSearch4, newNode4, child4);
		System.out.println(pureHit4);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.shareSavedNodeToSG("Shared with Default Security Group", newNode1, savedSearch1);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.shareSavedNodeToSG("Shared with Default Security Group", newNode2, savedSearch2);
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.shareSavedNodeToSG("Shared with Default Security Group", newNode3, savedSearch3);

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.getSavedSearchNewGroupExpand().waitAndClick(20);
		saveSearch.shareSavedNodeToSG("Shared with Default Security Group", newNode4, savedSearch4);

		String dataSet1[][] = { { savedSearch1, String.valueOf(pureHit1) }, { savedSearch2, String.valueOf(pureHit2) },
				{ savedSearch3, String.valueOf(pureHit3) }, { savedSearch4, String.valueOf(pureHit4) } };
		String nodeList1[][] = { { newNode1 }, { newNode2 }, { newNode3 }, { newNode4 } };

		int m = 0;
		for (int i = 0; i < dataSet1.length; i++) {

			int j = 0;
			int l = 0;
			String searches = dataSet1[i][j];
			j++;
			pureHit0 = dataSet1[i][j];
			String Nodes = nodeList1[m][l];

			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			// Schedule save search
			saveSearch.scheduleSavedSearchInMinute(searches, 2);

			// VerifyDocCount
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");
			String actualDocCount = saveSearch.getCountofDocs().getText();
			System.out.println(actualDocCount);
			softAssertion.assertEquals(pureHit0, actualDocCount);

			// verify DocCount in DocListPage.
			int DocCountInDocList = saveSearch.launchDocListViaSSandReturnDocCount();
			try {
				softAssertion.assertEquals(Integer.parseInt(actualDocCount), DocCountInDocList);
				base.passedStep("DocCounts Displayed in DocListPage is as Expected");
			} catch (Exception e) {
				base.failedStep("DocCounts Displayed in DocListPage is not as Expected");

			}
			m++;
		}

		String dataSet[][] = { { savedSearch1, String.valueOf(pureHit1) }, { savedSearch2, String.valueOf(pureHit2) },
				{ savedSearch3, String.valueOf(pureHit3) }, { savedSearch4, String.valueOf(pureHit4) } };
		String nodeList[][] = { { newNode1, child1 }, { newNode2, child2 }, { newNode3, child3 },
				{ newNode4, child4 } };

		int k = 0;

		for (int i = 0; i < dataSet.length; i++) {

			int j = 0;
			int l = 0;
			String searches = dataSet[i][j];
			j++;
			pureHit0 = dataSet[i][j];
			String Nodes = nodeList[k][l];
			l++;
			String childNode = nodeList[k][l];

			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.selectNode1(childNode);
			// Schedule save search
			saveSearch.scheduleSavedSearchInMinute(searches, 2);

			// VerifyDocCount
			driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
			saveSearch.selectNode1(Nodes);
			saveSearch.selectNode1(childNode);
			saveSearch.savedSearch_SearchandSelect(searches, "Yes");

			String actualDocCount = saveSearch.getCountofDocs().getText();
			System.out.println(actualDocCount);
			softAssertion.assertEquals(pureHit0, actualDocCount);

			// Verify Doc Count In DocListPage.
			int DocCountInDocList = saveSearch.launchDocListViaSSandReturnDocCount();
			try {
				softAssertion.assertEquals(Integer.parseInt(actualDocCount), DocCountInDocList);
				base.passedStep("DocCounts Displayed in DocListPage is as Expected");
			} catch (Exception e) {
				base.failedStep("DocCounts Displayed in DocListPage is not as Expected");

			}
			k++;

		}
		softAssertion.assertAll();
		login.logout();

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws Exception
	 * @description Verify that when batch upload is done with Productions then
	 *              count should be displayed correctly on saved search after Search
	 *              completes
	 */
	@Test(description = "RPMXCON-49094", enabled = true, dataProvider = "SavedSearchwithoutReviewer", groups = {
			"regression" })
	public void validateProductionsCountsThroughBatchFile(String username, String password) throws Exception {
		base.stepInfo("Test case Id: RPMXCON-49094");
		base.stepInfo(
				"Verify that when batch upload is done with Productions then count should be displayed correctly on saved search after Search completes");
		login.loginToSightLine(username, password);
		base.selectproject();
		String foldernameProd = "FolderProd" + Utility.dynamicNameAppender();
		String TagnameProd = "TagProd" + Utility.dynamicNameAppender();
		String productionname_2 = "Patch" + Utility.dynamicNameAppender();
		String PrefixID_2 = "A_" + Utility.dynamicNameAppender();
		String SuffixID_2 = "_P" + Utility.dynamicNameAppender();
		String Tagnameprev = "Privileged";
		driver.getWebDriver().get(Input.url + "TagsAndFolders/TagsAndFolders");
		tagsAndFolderPage.CreateFolder(foldernameProd, Input.securityGroup);
		base.selectproject();
		tagsAndFolderPage.CreateTagwithClassification(TagnameProd, Tagnameprev);
		base.selectproject();
		session.basicContentSearch("prabu");
		session.bulkFolderExisting(foldernameProd);
		ProductionPage page = new ProductionPage(driver);
		driver.getWebDriver().get(Input.url + "Production/Home");
		page.CreateNewProduction(productionname_2, PrefixID_2, SuffixID_2, foldernameProd, TagnameProd);
		base.stepInfo("Created a Production " + productionname_2);
		session.switchToWorkproduct();
		session.selectProductionstInASwp(productionname_2);
		int pureHits = session.serarchWP();
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		String batchFIleName = saveSearch.renameFile(Input.WPbatchFile);
		saveSearch.writeProductionDataIntoExcel(batchFIleName,
				"productions: [ name: [" + productionname_2 + "], produced: \"true\" ]");
		saveSearch.uploadWPBatchFile(batchFIleName);
		base.passedStep("Batch file uploaded successfully");
		saveSearch.verifyDocCountsAndStatus("WP productions", 2);
		driver.scrollPageToTop();
		saveSearch.getSavedSearchDeleteButton().waitAndClick(10);
		base.waitForElement(saveSearch.getDeleteOkBtn());
		saveSearch.getDeleteOkBtn().waitAndClick(10);
		base.VerifySuccessMessage("Save search tree node successfully deleted.");
		login.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws Exception
	 * @description SA/DA/PA User impersonate down as RMU/RU role, create
	 *              Searchgroups and Searches, and then runs the Concept Explorer
	 *              report against My saved searches in PAU role
	 */
	@Test(description = "RPMXCON-57403", enabled = true, dataProvider = "UserSaUDaUPaU", groups = { "regression" })
	public void validateConceptualReportAgainstMySavedSearch(String username, String password, String name, String role)
			throws Exception {
		base.stepInfo("Test case Id: RPMXCON-57403");
		base.stepInfo(
				"SA/DA/PA User impersonate down as RMU/RU role, create Searchgroups and Searches, and then runs the Concept Explorer report against My saved searches in PAU role");
		String searchName = "Search Name" + Utility.dynamicNameAppender();
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		String searchName2 = "Search Name" + UtilityLog.dynamicNameAppender();
		report = new ReportsPage(driver);

		// Login via User
		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin As : " + role);
		// Pre-requesties
		base.rolesToImp(role, "PA");
		String newNodeFromPA = saveSearch.createSearchGroupAndReturn(searchName, "PA");
		int purehit = session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName2);
		// session.saveSearchInNode(searchName2);
		session.saveSearchInNewNode(searchName2, newNodeFromPA);

		// Impersonate As RMU via PA and create new searchgroup
		base.rolesToImp("PA", "RMU");
		String newNodeFromRMU = saveSearch.createSearchGroupAndReturn(searchName, "RMU");

		// Save Search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);
//		session.saveSearchInNode(searchName);
		session.saveSearchInNewNode(searchName, newNodeFromRMU);

		// impersonate As REV and create new searchgroup
		base.rolesToImp("RMU", "REV");
		String newNodeFromRev = saveSearch.createSearchGroupAndReturn(searchName, "REV");

		// Save search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName1);
//		session.saveSearchInNode(searchName1);
		session.saveSearchInNewNode(searchName1, newNodeFromRev);
		driver.waitForPageToBeReady();

		// impersonate As Reviewer to RMU
		driver.waitForPageToBeReady();
		base.rolesToImp("REV", "RMU");

		// verify the saved searches are present in report page
		report.navigateToReportsPage();// this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		report.VerificationOfConceptualReport(newNodeFromPA, newNodeFromRMU, newNodeFromRev, searchName, searchName1,
				searchName2);

		// verify the generation of report under pau role
		report.selectSearchAndVerifyPurehitsInConceptualReports(searchName2, purehit);

		login.logout();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws Exception
	 * @description SA/DA/PA User impersonate down as RMU/RU role, create
	 *              Searchgroups and Searches, and then runs the STR report against
	 *              My saved searches in PAU role
	 */
	@Test(description = "RPMXCON-57404", enabled = true, dataProvider = "UserSaUDaUPaU", groups = { "regression" })
	public void validateSearchTermReportAgainstMySavedSearch(String username, String password, String name, String role)
			throws Exception {
		base.stepInfo("Test case Id: RPMXCON-57404");
		base.stepInfo(
				"SA/DA/PA User impersonate down as RMU/RU role, create Searchgroups and Searches, and then runs the STR report against My saved searches in PAU role");
		String searchName = "Search Name" + Utility.dynamicNameAppender();
		String searchName1 = "Search Name" + UtilityLog.dynamicNameAppender();
		String searchName2 = "Search Name" + UtilityLog.dynamicNameAppender();
		report = new ReportsPage(driver);

		// Login via User
		login.loginToSightLine(username, password);
		base.stepInfo("Loggedin As : " + role);
		// Pre-requesties
		base.rolesToImp(role, "PA");
		String newNodeFromPA = saveSearch.createSearchGroupAndReturn(searchName, "PA");
		int purehit = session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName2);
//		session.saveSearchInNode(searchName2);
		session.saveSearchInNewNode(searchName2, newNodeFromPA);

		// Impersonate As RMU via PA and create new searchgroup
		base.rolesToImp("PA", "RMU");
		String newNodeFromRMU = saveSearch.createSearchGroupAndReturn(searchName, "RMU");

		// Save Search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName);
		// session.saveSearchInNode(searchName);
		session.saveSearchInNewNode(searchName, newNodeFromRMU);

		// impersonate As REV and create new searchgroup
		base.rolesToImp("RMU", "REV");
		String newNodeFromRev = saveSearch.createSearchGroupAndReturn(searchName, "REV");

		// Save search
		session.basicContentSearch(Input.searchString1);
		session.saveSearch(searchName1);
		// session.saveSearchInNode(searchName1);
		session.saveSearchInNewNode(searchName1, newNodeFromRev);
		driver.waitForPageToBeReady();

		// impersonate As Reviewer to RMU
		driver.waitForPageToBeReady();
		base.rolesToImp("REV", "RMU");

		// verify the saved searches are present in report page
		report.VerificationOfSTReport(newNodeFromPA, newNodeFromRMU, newNodeFromRev, searchName, searchName1,
				searchName2);

		// verify the generation of report under pau role
		report.verifyDocCountsInSTReport(searchName2, purehit);

		login.logout();
	}

	/**
	 * @author jayanthi ganesan Description:To verify RMU/Reviewer is able to filter
	 *         saved search based on their status.
	 */
	@Test(description = "RPMXCON-49959", dataProvider = "SavedSearchwithRMUandREV", groups = {
			"regression" }, enabled = true)
	public void VerifyStatus(String username, String password, String fullName) throws Exception {
		base.stepInfo("Test case Id: RPMXCON-49959");
		base.stepInfo("To verify RMU/Reviewer is able to filter saved search based on their status");
		// Login via User
		login.loginToSightLine(username, password);

		String savedSearch1 = "SavedSearch" + Utility.dynamicNameAppender();
		String savedSearch = "SavedSearch" + Utility.dynamicNameAppender();

		base.stepInfo(
				"Selecting searches under My SavedSearches  folder and applying status filter to verify the status");

		session.basicContentSearch(Input.searchString1);
		session.saveSearch(savedSearch);
		base.selectproject();

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		// verifying the status column in saved search page
		saveSearch.CheckStatus("COMPLETED");

		// Creating search group and saving search under the newly created group.
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		String newNode1 = saveSearch.createSearchGroupAndReturn(searchName, "PA", "Yes");

		session.advancedMetaDataSearch("CustodianName", null, Input.metaDataCN, null);
		session.saveSearchInNewNode(savedSearch1, newNode1);
		base.stepInfo("Selecting searches under Search group under My savedSearch Tab and applying status filter");
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		saveSearch.selectRootGroupTab(Input.mySavedSearch);
		saveSearch.selectNode1(newNode1);
		// verifying the status column in saved search page
		saveSearch.CheckStatus("COMPLETED");
		login.logout();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.logBefore(testMethod.getName());
		// Open browser
		driver = new Driver();
		base = new BaseClass(driver);
		login = new LoginPage(driver);
		saveSearch = new SavedSearch(driver);
		session = new SessionSearch(driver);
		sg = new SecurityGroupsPage(driver);
		softAssertion = new SoftAssert();
		dcPage = new DocListPage(driver);
		tagsAndFolderPage = new TagsAndFoldersPage(driver);
		assign = new AssignmentsPage(driver);
		report = new ReportsPage(driver);
		miniDocListpage = new MiniDocListPage(driver);
	}

	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result, Method testMethod) {
		Reporter.setCurrentTestResult(result);
		UtilityLog.logafter(testMethod.getName());
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			try { // if any tc failed and dint logout!
				login.logoutWithoutAssert();
			} catch (Exception e) {
//						 TODO: handle exception
			}
		}
		try {
			login.quitBrowser();
		} catch (Exception e) {
			login.quitBrowser();
			login.clearBrowserCache();
		}
		System.out.println("Executed :" + result.getMethod().getMethodName());
	}

	@AfterClass(alwaysRun = true)
	public void close() {

		System.out.println("Executed :");

	}

}
