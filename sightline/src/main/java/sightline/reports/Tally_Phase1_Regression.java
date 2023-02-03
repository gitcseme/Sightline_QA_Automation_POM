
package sightline.reports;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

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
import pageFactory.CustomDocumentDataReport;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TallyPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;
public class Tally_Phase1_Regression {
	Driver driver;
	LoginPage lp;
	SessionSearch search;
	BaseClass bc;
	String hitsCountPA;
	int hitsCount;
	
	String SearchName="Tally"+Utility.dynamicNameAppender();
	String  assgnName="Tally"+Utility.dynamicNameAppender();
	String  folderName="Tally"+Utility.dynamicNameAppender();
	String securityGrpName="Tally"+Utility.dynamicNameAppender(); 
	String projectName=Input.projectName;
	//String[] sourceName_RMU ;
	//String[] sourceName_PA ;
	String expectedCusName;
	String expectedEAName;
	String expectedDocFileType;
	String expectedEAAdress;
	String saerchSG = "ss" + Utility.dynamicNameAppender();
	int pureHitTwoStrings;
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		Input in = new Input();
		in.loadEnvConfig();

		// Open browser
		driver = new Driver();
		bc = new BaseClass(driver);
		// Login as a PA
		lp = new LoginPage(driver);
		// Search and save it
		SecurityGroupsPage securityPage = new SecurityGroupsPage(driver);
		SessionSearch ss = new SessionSearch(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		AssignmentsPage agnmt = new AssignmentsPage(driver);
		ss.basicContentSearch(Input.TallySearch);
		hitsCountPA = ss.verifyPureHitsCount();
		ss.saveSearchAtAnyRootGroup(SearchName, Input.shareSearchDefaultSG);
		ss.bulkAssign();
		agnmt.FinalizeAssignmentAfterBulkAssign();
		agnmt.createAssignment_fromAssignUnassignPopup(assgnName, Input.codeFormName);
		agnmt.getAssignmentSaveButton().waitAndClick(5);
		bc.stepInfo("Created a assignment " + assgnName);
		driver.waitForPageToBeReady();
		DocListPage dp = new DocListPage(driver);
		ss.ViewInDocListWithOutPureHit();
		dp.SelectColumnDisplayByRemovingExistingOnes();  
		//getting  metadata list for search term so that we can use this list as expected value when we generate tally report(Bar chart).
		expectedCusName = dp.duplicateCheckList1(dp.getColumnValue(Input.metaDataName,true));
		expectedEAName = dp.duplicateCheckList1(dp.getColumnValue(Input.MetaDataEAName,true));
		expectedDocFileType = dp.duplicateCheckList1(dp.getColumnValue(Input.docFileType,true));
		expectedEAAdress = dp.duplicateCheckList1(dp.getColumnValue("EmailAuthorAddress",true));
		driver.getWebDriver().get(Input.url + "Search/Searches");
		ss.bulkFolderWithOutHitADD(folderName);
		lp.logout();
	lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		securityPage.AddSecurityGroup(securityGrpName);
		ss.basicContentSearch(Input.TallySearch);
		hitsCount = Integer.parseInt(ss.verifyPureHitsCount());
		ss.bulkRelease(securityGrpName);
		bc.stepInfo("Created a security group" + securityGrpName + "Bulk Realese is done");
		lp.logout();
		lp.quitBrowser();  

	}

/**
 * @author Jayanthi.ganesan
 * @throws InterruptedException
 */
	@Test(description ="RPMXCON-56223",groups = { "regression" })
	public void ValidateTally_BulkRelease() throws InterruptedException {
		bc.stepInfo("Test case Id: RPMXCON-56223");
		bc.stepInfo("To verify  Admin can release all the documents in a Tally");
		SoftAssert softAssertion = new SoftAssert();
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		String securitygroupname = "SG" + Utility.dynamicNameAppender();
		SecurityGroupsPage securityPage = new SecurityGroupsPage(driver);
		driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
		securityPage.AddSecurityGroup(securitygroupname);
		bc.stepInfo("Added security group -"+securitygroupname);
		TallyPage tp = new TallyPage(driver);
		tp.navigateTo_Tallypage();
		tp.SelectSource_SavedSearch(SearchName);
		tp.selectTallyByMetaDataField(Input.metaDataName);
		driver.waitForPageToBeReady();
		tp.tallyActions();
		String totalDocsCount = tp.bulkRelease(securitygroupname,false);
		bc.stepInfo("sucessfully released tally docs to security group  -"+securitygroupname);
		softAssertion.assertEquals(totalDocsCount,hitsCountPA );
		bc.stepInfo("Document Count associated to selected security group in search page is "+hitsCountPA);
		securityPage.deleteSecurityGroups(securitygroupname);
		softAssertion.assertAll();
		bc.passedStep("The docs released to Security group "+securitygroupname+" is reflected as expected");
	}
	
		/**
		 * @author Jayanthi.ganesan
		 * @param username
		 * @param password
		 * @param role
		 * @throws InterruptedException
		 */
		@Test(description ="RPMXCON-56212",dataProvider = "Users_PARMU",groups = { "regression" })
		public void ValidateTally_ViewInDocView(String username, String password, String role) throws InterruptedException {
			bc.stepInfo("Test case Id: RPMXCON-56212");
			bc.stepInfo("To verify Admin/RMU will be able to view tallied documents in a doc view(Tally)");
			SoftAssert softAssertion = new SoftAssert();
			lp.loginToSightLine(username, password);
			bc.stepInfo("Logged in as -" + role);
			TallyPage tp = new TallyPage(driver);
			tp.navigateTo_Tallypage();
			tp.SelectSource_SavedSearch(SearchName);
			tp.selectTallyByMetaDataField(Input.metaDataName);
			driver.waitForPageToBeReady();
			tp.tallyActions();
			tp.Tally_ViewInDocView();
			bc.stepInfo("Tally Report  generated for selected search.");
			DocViewPage dc = new DocViewPage(driver);
			String ActualCount = dc.verifyDocCountDisplayed_DocView();
			System.out.println(ActualCount);
			softAssertion.assertEquals(hitsCountPA, ActualCount);
			softAssertion.assertAll();
			bc.stepInfo("Document Count associated to selected saved search in Tally page is  displayed"
					+ " as excpeted in doc view page");
		}
		/**
		 * @author Jayanthi.ganesan
		 * @throws InterruptedException
		 */
		@Test(description ="RPMXCON-56204",groups = { "regression" })
		public void verifyTally_Assignments() throws InterruptedException {
			bc.stepInfo("Test case Id: RPMXCON-56204");
			bc.stepInfo("To Verify RMU will have a report in Tally with document counts by metadata fields for assignment groups.");
			String[] metadata={"CustodianName","DocFileType","EmailAuthorName","EmailAuthorAddress" };
			lp.loginToSightLine(Input.rmu1userName,Input.rmu1password);
			bc.stepInfo("Logged in as RMU");
			String assignmentName="AssignTally"+Utility.dynamicNameAppender();
			SessionSearch sessionsearch = new SessionSearch(driver);
			AssignmentsPage agnmt = new AssignmentsPage(driver);
			sessionsearch.basicContentSearch(Input.testData1);
			sessionsearch.bulkAssign();
			agnmt.FinalizeAssignmentAfterBulkAssign();
			agnmt.createAssignment_fromAssignUnassignPopup(assignmentName, Input.codeFormName);
			agnmt.getAssignmentSaveButton().waitAndClick(5);
			bc.stepInfo("Created a assignment " + assignmentName);  
			TallyPage tp = new TallyPage(driver);
			tp.navigateTo_Tallypage();
			tp.SelectSource_Assignment(assignmentName);
			for(int i=0;i<metadata.length;i++) {
			bc.stepInfo("**To Verify Tally Report if Tally By MetaData as-"+metadata[i]+"**");			
			tp.selectTallyByMetaDataField(metadata[i]);
			tp.validateMetaDataFieldName(metadata[i]);
			tp.verifyTallyChart();
			bc.passedStep("Verified whether RMU will have a report in Tally with document counts by metadata "
					+ "fields for assignment groups if Tally by metadata is "+metadata[i]);
			}
		}
		
		/**
		 * @author Jayanthi.ganesan
		 * @throws InterruptedException
		 */
		@Test(description ="RPMXCON-56203",dataProvider = "Users_PARMU", groups = { "regression" })
		public void verifyTally_Searches(String username, String password, String role) throws InterruptedException {
			bc.stepInfo("Test case Id: RPMXCON-56203");
			bc.stepInfo("To Verify Admin/RMU will have a report in Tally with document counts by metadata fields for searches.");
			String[] metadata = { "CustodianName", "DocFileType", "EmailAuthorName", "EmailAuthorAddress" };
			String[] expectedMetaData = { expectedCusName,expectedDocFileType, expectedEAName, expectedEAAdress };
			lp.loginToSightLine(username, password);
			bc.stepInfo("Logged in as " + role);
			String metadataBarchartTally;
			SoftAssert softAssertion = new SoftAssert();
			TallyPage tp = new TallyPage(driver);
			tp.navigateTo_Tallypage();
			tp.SelectSource_SavedSearch(SearchName);
			for (int i = 0; i < metadata.length; i++) {
				bc.stepInfo("**To Verify Tally Report if Tally By MetaData as-" + metadata[i] + "**");
				tp.selectTallyByMetaDataField(metadata[i]);
				tp.validateMetaDataFieldName(metadata[i]);
				metadataBarchartTally=tp.verifyTallyChartMetadata();
				softAssertion.assertEquals(expectedMetaData[i], metadataBarchartTally.toLowerCase());
			softAssertion.assertAll();
				bc.passedStep(
						"Verified whether " + role + " will have a report in Tally with document counts by metadata "
								+ "fields for Searches if Tally by metadata is " + metadata[i]);
			}
		}
		

		
		/**
		 * @author Jayanthi.ganesan
		 * @throws InterruptedException
		 */
		@Test(description ="RPMXCON-56196",dataProvider = "Users_PARMU", groups = { "regression" })
		public void verifyTallyDropDown(String username, String password, String role) throws InterruptedException {
			bc.stepInfo("Test case Id: RPMXCON-56196");
			bc.stepInfo("To Verify \"Tally By\" Drop Down And \"Apply\" button on Tally Page");
			String[] metadata = { "CustodianName", "DocFileType", "EmailAuthorName", "EmailAuthorAddress"};
			String[] sourceName_RMU = { assgnName, folderName,SearchName,"Default Security Group"};
			String[] sourceName_PA = {Input.projectName, folderName,SearchName,"Default Security Group"};
			lp.loginToSightLine(username, password);
			bc.stepInfo("Logged in as " + role);
			SoftAssert softAssertion = new SoftAssert();
			TallyPage tp = new TallyPage(driver);
			for(int k=0;k<4;k++) {
				tp.navigateTo_Tallypage();
				if(role=="PA") {
				tp.selectSources_PA(k,sourceName_PA[k]);}
				if(role=="RMU") {
					tp.selectSources_RMU(k,sourceName_RMU[k]);}
			for (int i = 0; i < metadata.length; i++) {
				tp.selectTallyByMetaDataField(metadata[i]);
				tp.validateMetaDataFieldName(metadata[i]);
				 tp.verifyTallyChart();
				softAssertion.assertAll();
			}
			bc.passedStep("Sucessfully verified tally by drop down and apply button if source is-- "+sourceName_PA[k] );
			}
		}
		/**
		 * @author Jayanthi.ganesan
		 * @throws InterruptedException
		 */
			@Test(description ="RPMXCON-56226",groups = { "regression" })
			public void ValidateSubTally_BulkRelease() throws InterruptedException {
				bc.stepInfo("Test case Id: RPMXCON-56226");
				bc.stepInfo("To Verify Admin can release all the documents in subtally");
				SoftAssert softAssertion = new SoftAssert();
				lp.loginToSightLine(Input.pa1userName, Input.pa1password);
				String securityGrp_SubTally ="SG" + Utility.dynamicNameAppender();
				SecurityGroupsPage securityPage = new SecurityGroupsPage(driver);
				driver.getWebDriver().get(Input.url + "SecurityGroups/SecurityGroups");
				securityPage.AddSecurityGroup(securityGrp_SubTally);
				bc.stepInfo("Added security group -"+securityGrp_SubTally);
				TallyPage tp = new TallyPage(driver);
				tp.navigateTo_Tallypage();
				tp.SelectSource_SavedSearch(SearchName);
				tp.selectTallyByMetaDataField(Input.metaDataName);
				bc.stepInfo("Created Tally Report for saved search-"+SearchName);
				driver.waitForPageToBeReady();
				tp.tallyActions();
				bc.waitTime(2);
				tp.getTally_actionSubTally().Click();
				tp.selectMetaData_SubTally(Input.docFileName,1);
				driver.waitForPageToBeReady();
				bc.stepInfo("Created SubTally Report");
				tp.subTallyActions();
				String totalDocsCount = tp.bulkRelease(securityGrp_SubTally,true);
				bc.stepInfo("sucessfully released sub-tally docs to security group  -"+securityGrp_SubTally);
				SessionSearch search = new SessionSearch(driver);
				search.switchToWorkproduct();
				search.selectSecurityGinWPS(securityGrp_SubTally);
				bc.stepInfo("Configured a search query with Security group-" + securityGrp_SubTally);
				search.serarchWP();
				String expectedCount = search.verifyPureHitsCount();
				softAssertion.assertEquals(totalDocsCount, expectedCount);
				bc.stepInfo("Document Count associated to selected security group in search page is "+expectedCount);
			//	securityPage.deleteSecurityGroups(securitygroupname);
				softAssertion.assertAll();
				bc.passedStep("The docs released to Security group "+securityGrp_SubTally+" is reflected as expected");
			}
		
		/**
		 * @author Jayanthi.ganesan
		 * @throws InterruptedException
		 */
		@Test(description ="RPMXCON-48704", groups = { "regression" })
			public void verifyTally_SG() throws InterruptedException {
			bc.stepInfo("Test case Id: RPMXCON-48704");
			bc.stepInfo("To Verify Admin/RMU will have a report that provides ability to view the document volume by metadata "
					+ "fields for security groups in Tally.");
			String[] metadata = { "CustodianName", "DocFileType", "EmailAuthorName", "EmailAuthorAddress" };
			String[] expectedMetaData = { expectedCusName, expectedDocFileType, expectedEAName, expectedEAAdress };
			lp.loginToSightLine(Input.pa1userName, Input.pa1password);
			bc.stepInfo("Logged in as PA");
			String metadataBarchartTally;
			SoftAssert softAssertion = new SoftAssert();
			TallyPage tp = new TallyPage(driver);
			tp.navigateTo_Tallypage();
			tp.SelectSource_SecurityGroup(securityGrpName);
			for (int i = 0; i < metadata.length; i++) {
				bc.stepInfo("**To Verify Tally Report if Tally By MetaData as-" + metadata[i] + "**");
				tp.selectTallyByMetaDataField(metadata[i]);
				tp.validateMetaDataFieldName(metadata[i]);
				metadataBarchartTally=tp.verifyTallyChartMetadata();
					softAssertion.assertEquals(expectedMetaData[i], metadataBarchartTally.toLowerCase());
				softAssertion.assertAll();
				bc.passedStep(
						"Verified whether user will have a report in Tally with document counts by metadata "
								+ "fields for Searches if Tally by metadata is " + metadata[i]);
			}
		}

		/**
		 * @author Jayanthi.ganesan
		 * @throws InterruptedException
		 */
		@Test(description ="RPMXCON-56205", groups = { "regression" })
		public void verifyTallyDocsCount_SG() throws InterruptedException {
			bc.stepInfo("Test case Id: RPMXCON-56205");
			bc.stepInfo("To Verify Admin/RMU will have a report in Tally with document counts by"
					+ " metadata fields for security groups.");
			String[] metadata = { "CustodianName", "DocFileType", "EmailAuthorName", "EmailAuthorAddress" };
			String[] expectedMetaData = { expectedCusName, expectedDocFileType, expectedEAName, expectedEAAdress };
			lp.loginToSightLine(Input.pa1userName, Input.pa1password);
			bc.stepInfo("Logged in as PA");
			String metadataBarchartTally;
			SoftAssert softAssertion = new SoftAssert();
			TallyPage tp = new TallyPage(driver);
			tp.navigateTo_Tallypage();
			tp.SelectSource_SecurityGroup(securityGrpName);
			for (int i = 0; i < metadata.length; i++) {
				bc.stepInfo("**To Verify Tally Report if Tally By MetaData as-" + metadata[i] + "**");
				tp.selectTallyByMetaDataField(metadata[i]);
				tp.validateMetaDataFieldName(metadata[i]);
				metadataBarchartTally=tp.verifyTallyChartMetadata();
					softAssertion.assertEquals(expectedMetaData[i], metadataBarchartTally.toLowerCase());
					softAssertion.assertEquals(hitsCount, tp.verifyDocCountBarChart());
				
				softAssertion.assertAll();
				bc.passedStep(
						"Verified whether user will have a report in Tally with document counts by metadata "
								+ "fields for Searches if Tally by metadata is " + metadata[i]);
			}
		}
		
		/**
		 * @author Jayanthi.ganesan
		 * @throws InterruptedException
		 */
		@Test(description ="RPMXCON-56218",groups = { "regression" })
		public void verifySubTally_Assignments() throws InterruptedException {
			bc.stepInfo("Test case Id: RPMXCON-56218");
			bc.stepInfo("To Verify RMU will have a report in SubTally with document counts by metadata fields for assignment groups.");
			String[][] subTally = { { "CustodianName", "DocFileType", "EmailAuthorName", "EmailAuthorAddress" },
					{ "EmailAuthorName", "CustodianName", "DocFileType", "EmailAuthorAddress" },
					{ "DocFileType", "EmailAuthorName", "EmailAuthorAddress", "CustodianName" },
					{ "EmailAuthorAddress", "CustodianName", "DocFileType", "EmailAuthorName" } };
			lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			bc.stepInfo("Logged in as RMU");
			TallyPage tp = new TallyPage(driver);
			SoftAssert softAssertion = new SoftAssert();
			for (int i = 0; i < subTally.length; i++) {
				tp.navigateTo_Tallypage();
				tp.SelectSource_Assignment(assgnName);
				String metadataTally = subTally[i][0];
				tp.selectTallyByMetaDataField(metadataTally);
				tp.validateMetaDataFieldName(metadataTally);
				tp.verifyTallyChart();
				softAssertion.assertEquals(hitsCount, tp.verifyDocCountBarChart());
				tp.tallyActions();
				bc.waitTime(2);
				tp.getTally_actionSubTally().Click();
				for (int j = 1; j < subTally[i].length; j++) {
					bc.stepInfo("**To Verify Sub Tally Report Table  if Tally By Metadata as " + metadataTally
							+ " and Sub Tally By subMetaData as-" + subTally[i][j] + "**");
					tp.selectMetaData_SubTally(subTally[i][j],j);
					tp.verifyDocCountSubTally(hitsCount);
				}
				bc.selectproject();
			}
			softAssertion.assertAll();
		}

		/**
		 * @author Jayanthi.ganesan
		 * @throws InterruptedException
		 */

		@Test(description ="RPMXCON-48703",dataProvider = "Users_PARMU",groups = { "regression" })
		public void verifySubTally_Searches(String username, String password, String role) throws InterruptedException {
			bc.stepInfo("Test case Id: RPMXCON-48703");
			bc.stepInfo("To Verify Admin/RMU will have a report in SubTally with document counts by metadata fields for searches.");
			String[][] subTally = { { "CustodianName", "DocFileType", "EmailAuthorName", "EmailAuthorAddress" },
					{ "EmailAuthorName", "CustodianName", "DocFileType", "EmailAuthorAddress" },
					{ "DocFileType", "EmailAuthorName", "EmailAuthorAddress", "CustodianName" },
					{ "EmailAuthorAddress", "CustodianName", "DocFileType", "EmailAuthorName" } };
			lp.loginToSightLine(username, password);
			bc.stepInfo("Logged in as "+role);
			TallyPage tp = new TallyPage(driver);
			SoftAssert softAssertion = new SoftAssert();
			for (int i = 0; i < subTally.length; i++) {
				tp.navigateTo_Tallypage();
				tp.SelectSource_SavedSearch(SearchName);
				String metadataTally = subTally[i][0];
				tp.selectTallyByMetaDataField(metadataTally);
				tp.validateMetaDataFieldName(metadataTally);
				tp.verifyTallyChart();
				softAssertion.assertEquals(hitsCountPA, Integer.toString(tp.verifyDocCountBarChart()));
				tp.tallyActions();
				bc.waitTime(2);
				tp.getTally_actionSubTally().Click();
				for (int j = 1; j < subTally[i].length; j++) {
					bc.stepInfo("**To Verify Sub Tally Report Table for searches-- if Tally By Metadata as " + metadataTally
							+ " and Sub Tally By subMetaData as-" + subTally[i][j] + "**");
					tp.selectMetaData_SubTally(subTally[i][j],j);
					tp.verifyDocCountSubTally(hitsCount);
				}
				bc.selectproject();
			}
			softAssertion.assertAll();
		}

		/**
		 * @author Jayanthi.ganesan
		 * @throws InterruptedException
		 */

		@Test(description ="RPMXCON-56227",dataProvider = "Users_PARMU",groups = { "regression" })
		public void verifySubTally_SavedSearchToTally(String username, String password, String role) throws InterruptedException {
			bc.stepInfo("Test case Id: RPMXCON-56227");
			bc.stepInfo("To Verify Admin/RMU will be able to go to Tally from Saved Search");
			String[][] subTally = { { "CustodianName", "DocFileType", "EmailAuthorName", "EmailAuthorAddress" },
					{ "EmailAuthorName", "CustodianName", "DocFileType", "EmailAuthorAddress" },
					{ "DocFileType", "EmailAuthorName", "EmailAuthorAddress", "CustodianName" },
					{ "EmailAuthorAddress", "CustodianName", "DocFileType", "EmailAuthorName" } };
			lp.loginToSightLine(username, password);
			bc.stepInfo("Logged in as "+role);
			TallyPage tp = new TallyPage(driver);
			SoftAssert softAssertion = new SoftAssert();
			SavedSearch savedSearch=new SavedSearch(driver);
			for (int i = 0; i < subTally.length; i++) {
				savedSearch.savedSearchToTally_sharedToSG(SearchName);
				String metadataTally = subTally[i][0];
				tp.selectTallyByMetaDataField(metadataTally);
				tp.validateMetaDataFieldName(metadataTally);
				tp.verifyTallyChart();
				softAssertion.assertEquals(hitsCountPA, Integer.toString(tp.verifyDocCountBarChart()));
				tp.tallyActions();
				bc.waitTime(2);
				tp.getTally_actionSubTally().Click();
				for (int j = 1; j < subTally[i].length; j++) {
					bc.stepInfo("**To Verify Sub Tally Report Table from saved search-- if Tally By Metadata as " + metadataTally
							+ " and Sub Tally By subMetaData as-" + subTally[i][j] + "**");
					tp.selectMetaData_SubTally(subTally[i][j],j);
					tp.verifyDocCountSubTally(hitsCount);
				}
				bc.selectproject();
			}
			softAssertion.assertAll();
		}
		/**
		 * @author Jayanthi.ganesan
		 * @throws InterruptedException
		 */

		@Test(description ="RPMXCON-56213",dataProvider = "Users_PARMU", groups = { "regression" })
		public void verifySubTally_docview(String username, String password, String role) throws InterruptedException {
			bc.stepInfo("Test case Id: RPMXCON-56213");
			bc.stepInfo("To verify Admin/RMU can view all the documents tallied in a doc view (SubTally)");
			String[][] subTally = { { "CustodianName", "DocFileType", "EmailAuthorName", "EmailAuthorAddress" },
					{ "EmailAuthorName", "CustodianName", "DocFileType", "EmailAuthorAddress" },
					{ "DocFileType", "EmailAuthorName", "EmailAuthorAddress", "CustodianName" },
					{ "EmailAuthorAddress", "CustodianName", "DocFileType", "EmailAuthorName" } };
			String[] expectedMetaData = { expectedCusName, expectedEAName, expectedDocFileType, expectedEAAdress };
			String[] sourceName_RMU = { assgnName, folderName,SearchName,"Default Security Group"};
			String[] sourceName_PA = {Input.projectName, folderName,SearchName,"Default Security Group"};
			lp.loginToSightLine(username, password);
			String[] sourceNames = new String[4];
			if (role == "RMU") {
				sourceNames = sourceName_RMU;
			}
			if (role == "PA") {
				sourceNames = sourceName_PA;
			}
			bc.stepInfo("Logged in as " + role);
			TallyPage tp = new TallyPage(driver);
			SoftAssert softAssertion = new SoftAssert();
			tp.navigateTo_Tallypage();
			//iterating this for loop to change the source for every iteration of loop
			for (int k = 0; k < expectedMetaData.length; k++) {
				//iterating this for loop to change the tally by metadata value  for every iteration of loop
				for (int i = 0; i < subTally.length; i++) {
					String metadataTally = subTally[i][0];
					//iterating this for loop to change thesub- tally by metadata value for every iteration of loop
					for (int j = 1; j < subTally[i].length; j++) {
						bc.stepInfo(
								"**To Verify View in doc view after Sub Tally Report Table generated -- if Tally By Metadata as "
										+ metadataTally + " and Sub Tally By subMetaData as-" + subTally[i][j] + "**");

						tp.sourceSelectionUsers(role, sourceNames, k);
						tp.selectTallyByMetaDataField(metadataTally);
						tp.validateMetaDataFieldName(metadataTally);
						String metadataBarchartTally=tp.verifyTallyChartMetadata();
						if ((!sourceNames[k].equalsIgnoreCase(Input.projectName))&&(!sourceNames[k].equalsIgnoreCase(Input.securityGroup)) ){
							softAssertion.assertEquals(expectedMetaData[i],metadataBarchartTally.toLowerCase());
						}
						tp.tallyActions();
						bc.waitTime(2);
						tp.getTally_actionSubTally().Click();
						driver.waitForPageToBeReady();
						tp.selectMetaData_SubTally(subTally[i][j],1);
						int subTallyDocCount=tp.getDocCountSubTally();
						bc.stepInfo("Doc Count selected in subtally to view in doc view--"+subTallyDocCount);
						tp.subTallyActions();
						tp.SubTally_ViewInDocView();
						driver.waitForPageToBeReady();
						AssignmentsPage ap = new AssignmentsPage(driver);
						int ActualCount = ap.docCountViewinDocView();
						//Validating the doc ocunt selected from sub taly table
						//with doc count in doc view page
						softAssertion.assertEquals(subTallyDocCount, ActualCount);
						this.driver.getWebDriver().get(Input.url + "Report/Tally");
					}
					
				}
			}

			softAssertion.assertAll();
		}


		/**
		 * @author Jayanthi.ganesan
		 * @throws InterruptedException
		 */

		@Test(description ="RPMXCON-56221",dataProvider = "Users_PARMU", groups = { "regression" })
		public void verifyFilterDocumentsBy(String username, String password, String role) throws InterruptedException {
			bc.stepInfo("Test case Id: RPMXCON-56221");
			bc.stepInfo("To Verify Admin/RMU on the Tally report page will be able to filter on multiple "
					+ "metadata fields on both the Tally and Subtally areas");
			String[][] subTally = { { "CustodianName", "DocFileType", "EmailAuthorName", "EmailAuthorAddress" },
					{ "EmailAuthorName", "CustodianName", "DocFileType", "EmailAuthorAddress" },
					{ "DocFileType", "EmailAuthorName", "CustodianName", "EmailAuthorAddress" } };
			String[] sourceName_RMU = { assgnName, folderName,SearchName,"Default Security Group"};
			String[] sourceName_PA = {Input.projectName, folderName,SearchName,"Default Security Group"};
			lp.loginToSightLine(username, password);
			bc.stepInfo("Logged in as " + role);
			TallyPage tp = new TallyPage(driver);
			String[] sourceNames = new String[4];
			String FilterType = "Include";
			if (role == "RMU") {
				sourceNames = sourceName_RMU;
			}
			if (role == "PA") {
				sourceNames = sourceName_PA;
			}
			//iterating this for loop to change the source for every iteration of loop
			for (int k = 0; k < sourceNames.length; k++) {
				//iterating this for loop to change the tally by metadata value  for every iteration of loop
				for (int i = 0; i < subTally.length; i++) {
					String metadataTally = subTally[i][0];
					tp.navigateTo_Tallypage();
					tp.sourceSelectionUsers(role, sourceNames, k);
					tp.verifySourceSelected();
					tp.selectTallyByMetaDataField(metadataTally);
					tp.validateMetaDataFieldName(metadataTally);
					List<String> ListOfMetaData = tp.verifyTallyChart();
					bc.stepInfo("**To Verify Tally Report filter documents by functionality  if Tally By Metadata as "
							+ metadataTally + " **");
					String ApplyFilterMetaData = tp.applyFilterToTallyBy(ListOfMetaData, metadataTally, FilterType); //Applying filter 'Include'
					tp.verifyTallyChartAfterApplyingFilter(FilterType, ApplyFilterMetaData); //validating 'Include' filters functionality
					tp.clearingActiveFiltersInTallyBy().waitAndClick(7);
					tp.getTally_btnTallyApply().Click();
					driver.waitForPageToBeReady();
					tp.tallyActions();
					bc.waitTime(2);
					tp.getTally_actionSubTally().Click();
					//iterating this for loop to change the sub- tally by metadata value for every iteration of loop
					for (int j = 1; j < subTally[i].length; j++) {
						bc.stepInfo(
								"**To Verify Sub Tally Report filter documents by functionality  if Tally By Metadata as "
										+ metadataTally + " and Sub Tally By subMetaData as-" + subTally[i][j] + "**");
						tp.selectMetaData_SubTally(subTally[i][j],j);
						tp.applyFilterToSubTallyBy(ListOfMetaData, metadataTally, FilterType); //Applying filter 'Include'
						tp.verifySubTallyResultTableAfterApplyingFilter(FilterType, ApplyFilterMetaData); //validating 'Include' filters functionality
						tp.clearingActiveFiltersInTallyBy().waitAndClick(7);
					}

				}
			}
		}
		/**
		 * @author Jayanthi.ganesan
		 * @throws InterruptedException
		 */
		@Test(description ="RPMXCON-56964",dataProvider = "Users_PARMU", groups = { "regression" })
		public void verifyTallyReportSourceAsSearch(String username, String password, String role)
				throws InterruptedException {
			bc.stepInfo("Test case Id: RPMXCON-56964");
			bc.stepInfo("Verify and generate Tally Report with source as Search");
			lp.loginToSightLine(username, password);
			bc.stepInfo("Logged in as " + role);
			TallyPage tp = new TallyPage(driver);
			String saveSearch = "ss" + Utility.dynamicNameAppender();
			String[] SavedSearchName = { saerchSG, saveSearch };
			SoftAssert softAssertion = new SoftAssert();
			SessionSearch ss = new SessionSearch(driver);
			if (role == "RMU") {
				ss.basicContentSearchForTwoItems(Input.testData1, Input.TallySearch);
				pureHitTwoStrings = Integer.parseInt(ss.verifyPureHitsCount());
				ss.getNewSearchButton().waitAndClick(5);
				ss.advancedContentSearchWithSearchChanges(Input.TallySearch, "Yes");
				ss.saveSearchAtAnyRootGroup(saerchSG, Input.shareSearchDefaultSG);
				ss.getNewSearchButton().waitAndClick(5);
				ss.advancedContentSearchWithSearchChanges(Input.testData1, "Yes");
				ss.saveSearchAtAnyRootGroup(saveSearch, Input.mySavedSearch);

			}
			if (role == "PA") {
				ss.navigateToSessionSearchPageURL();
				ss.advancedContentSearchWithSearchChanges(Input.testData1, "Yes");
				ss.saveSearchAtAnyRootGroup(saveSearch, Input.shareSearchPA);
			}
			tp.navigateTo_Tallypage();
			tp.SelectSource_MultipleSavedSearch(SavedSearchName);
			tp.selectTallyByMetaDataField(Input.metaDataName);
			tp.validateMetaDataFieldName(Input.metaDataName);
			tp.verifyTallyChart();
			softAssertion.assertEquals(pureHitTwoStrings, tp.verifyDocCountBarChart());
			tp.tallyActions();
			bc.waitTime(2);
			tp.getTally_actionSubTally().Click();
			bc.stepInfo("**To Verify Sub Tally Report Table**");
			tp.selectMetaData_SubTally(Input.docFileType,1);
			tp.verifyDocCountSubTally(pureHitTwoStrings);
			softAssertion.assertAll();
		}

		/**
		 * @author Jayanthi.ganesan
		 * @throws InterruptedException
		 */

		@Test(description ="RPMXCON-48705",dataProvider = "Users_PARMU", groups = { "regression" })
		public void verifyExport(String username, String password, String role) throws InterruptedException {
			bc.stepInfo("Test case Id: RPMXCON-48705");
			bc.stepInfo("To Verify User will be able to export data in an excel format from Subtally.");

			lp.loginToSightLine(username, password);
			bc.stepInfo("Logged in as " + role);
			TallyPage tp = new TallyPage(driver);
			String[] sourceNames = new String[4];
			String[] sourceName_RMU = { assgnName, folderName,SearchName,"Default Security Group"};
			String[] sourceName_PA = {Input.projectName, folderName,SearchName,"Default Security Group"};
			if (role == "RMU") {
				sourceNames = sourceName_RMU;
			}
			if (role == "PA") {
				sourceNames = sourceName_PA;
			}
			// iterating this for loop to change the source for every iteration of loop
			for (int k = 0; k < sourceNames.length; k++) {

				tp.navigateTo_Tallypage();
				tp.sourceSelectionUsers(role, sourceNames, k);
				tp.verifySourceSelected();
				tp.selectTallyByMetaDataField(Input.metaDataName);
				tp.validateMetaDataFieldName(Input.metaDataName);
				bc.stepInfo("** Generating Tally report **");
				tp.verifyTallyChart();
				tp.tallyActions();
				bc.waitTime(2);
				tp.getTally_actionSubTally().Click();
				bc.stepInfo("**Generating  Sub Tally Report Table**");
				tp.selectMetaData_SubTally(Input.docFileType,1);
				int docsSelected = tp.getSelectedColumnDocCountSubTally(2);
				tp.subTallyActionsWithOutTallyAllSelection();
				bc.stepInfo("**Exporting selected data from  Sub Tally Report Table**");
				tp.subTallyToExport();
				CustomDocumentDataReport cddr = new CustomDocumentDataReport(driver);
				String metaData[] = { Input.metaDataName };
				cddr.selectMetaDataFields(metaData);
				String Filename2 = cddr.runReportandVerifyFileDownloaded();
				System.out.println(Filename2);
				List<String> exportedData = cddr.csvfileSize("", Filename2);
				String value = exportedData.get(0);
				String[] strArray = value.split(",");
				List<String> slectdFieldList_excel = Arrays.asList(strArray);
				List<String> slectdFieldList = Arrays.asList(metaData);
				for (int i = 0; i < slectdFieldList_excel.size(); i++) {
					String temp = slectdFieldList_excel.get(i).replaceAll("\"", "");
					slectdFieldList_excel.set(i, temp);
					slectdFieldList_excel.get(i).trim();
				}
				// Verification of doc count reflected in export page from tally page selected
				// criteria reflected in report
				if (slectdFieldList.containsAll(slectdFieldList_excel) && (exportedData.size() - 2) == (docsSelected)) {
					bc.passedStep("Sucessfully Verified that  Export Data Action is working properly on Tally Page");
				} else {
					bc.failedStep("Exported Data is not  reflected in excel file.");
				}
			}

		}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		driver = new Driver();
		lp = new LoginPage(driver);
		bc = new BaseClass(driver);
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
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}
	}
	

	@DataProvider(name = "Users_PARMU")
	public Object[][] PA_RMU() {
		Object[][] users = { { Input.rmu1userName, Input.rmu1password, "RMU" },
				{ Input.pa1userName, Input.pa1password, "PA" } 
		};
		return users;
	}
	

	@AfterClass(alwaysRun = true)
	public void close() {
		System.out.println("**Executed Tally Regression1**");

	}
}
