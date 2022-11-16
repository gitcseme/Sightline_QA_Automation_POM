package pageFactory;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class ReportsPage {

	Driver driver;
	BaseClass base;
	CommunicationExplorerPage communicationExplorer;
	SoftAssert softAssertion;

	public Element getTallyReport() {
		return driver.FindElementByXPath(".//*[@id='collapseOne']//a[contains(.,'Tally Report')]");
	}

	public Element getCustomDocumentDataReport() {
		return driver.FindElementByXPath(".//*[@id='collapseOne']//a[contains(.,'Custom Document Data Report')]");
	}

	// Added by Raghuram
	public Element getThisLink(String linkName) {
		return driver.FindElementByXPath(".//*[@id='collapseOne']//a[text()='" + linkName + "']");
	}

	public Element getSelectSourcesBtn() {
		return driver.FindElementByXPath("//button[@id='select-source']");
	}

	public Element getSelectResourcesOption(String option) {
		return driver.FindElementByXPath("//a[@class='accordion-toggle']//strong[text()='" + option + "']");
	}

	public Element getNode(String nodeName) {
		return driver.FindElementByXPath(
				"//a[@class='jstree-anchor' and text()='My Saved Search']//..//ul//a[text()='" + nodeName + "']");
	}

	public Element getNodeCheckBox(String nodeName) {
		return driver.FindElementByXPath("//a[@class='jstree-anchor' and text()='My Saved Search']//..//ul//a[text()='"
				+ nodeName + "']//i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getNodeMySavedSearchCheckBox() {
		return driver.FindElementByXPath(
				"//a[@class='jstree-anchor' and text()='My Saved Search']//i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getSaveSelectionBtn() {
		return driver.FindElementByXPath("//button[@id='search']");
	}

	public Element getMetaDataChoose(String type) {
		return driver.FindElementByXPath(
				"//strong[text()='" + type + "']//..//input[@name='checkbox']//following-sibling::i");
	}

	public Element getChooseWorkProduct(String fieldName) {
		return driver.FindElementByXPath("(//a[@class='jstree-anchor' and text()='" + fieldName
				+ "']//i[@class='jstree-icon jstree-checkbox'])[2]");
	}

	public Element getSelectSearchCloseBtn() {
		return driver.FindElementByXPath("//div[@id='popover-head']//i");
	}

	public Element getAddToSelectedBtn() {
		return driver.FindElementByXPath("//a[@id='addFormObjects-coreList']");
	}

	public Element getRunReport() {
		return driver.FindElementByXPath("// a[@id='btnRunReport']");
	}

	public Element getAvailableObjectsTab(String name) {
		return driver.FindElementByXPath("//div[@class='tab-widget']//a//span[text()='" + name + "']");
	}

	public Element getBullHornIcon() {
		return driver.FindElementByXPath("//i[@class='fa fa-bullhorn']");
	}

	public Element getBullHornNotification() {
		return driver.FindElementByXPath("//b[@class='badge bg-color-red bounceIn animated']");
	}

	public Element getFileDownloaded() {
		return driver.FindElementByXPath("(//a[text()='Your export is ready please click here to download'])[1]");
	}

	public Element getbulHornNotification() {
		return driver.FindElementByXPath("(//b[@class='badge bg-color-red bounceIn animated']");
	}

	// 9-25-21
	public Element getCustomReport(String reportName) {
		return driver
				.FindElementByXPath("//div[@id='divDataExplorationCustomTemplate']//a[text()='" + reportName + "']");
	}

	// Added By Jeevitha
	public Element getAddNewSourceLink() {
		return driver.FindElementByXPath("//a[contains(text(),'Add New Sources')]");
	}

	public Element getFoldersLink() {
		return driver.FindElementByXPath("//strong[text()='Folders']");
	}

	public Element getFolderSaveSelectionBtn() {
		return driver.FindElementByXPath("//button[@id='folder']");
	}

	public Element getReviewersDD() {
		return driver.FindElementByXPath(
				"//span[contains(text(),'Select Reviewers')]//..//following-sibling::a[contains(@data-toggle,'collapse')]");
	}

	public Element getReviewersCheckbox() {
		return driver.FindElementByXPath(
				"//span[contains(text(),'Select Reviewers')]//..//following-sibling::a[contains(@data-toggle,'collapse')]//following::h4[contains(text(),'Reviewers')]");
	}

	public Element getAssignmentDD() {
		return driver.FindElementByXPath(
				"//span[contains(text(),'SELECT ASSIGNMENTS')]//..//following-sibling::a[contains(@data-toggle,'collapse')]");
	}

	public Element getAssignment(String assignment) {
		return driver.FindElementByXPath("(//a[@data-content='" + assignment
				+ "' and @class='jstree-anchor']//i[contains(@class,'checkbox')])[2]");
	}

	public Element getApplyChangesBtn() {
		return driver.FindElementByXPath("//button[contains(text(),'Apply Changes')]");
	}

	public Element getTotalDocs() {
		return driver.FindElementByXPath(
				"//h2[text()='Document Distribution Summary']//following-sibling::div//label[text()='Total Net Selected Docs ']//span");
	}

	public Element getSelectFolder(String foldername) {
		return driver.FindElementByXPath(
				"//ul[@class='jstree-container-ul jstree-children']//a[contains(.,'" + foldername + "')]");
	}

	public Element getPageHeader() {
		return driver.FindElementByXPath("//h2[@class='page-title header']");
	}

	public Element getReports() {
		return driver.FindElementByXPath("//label[text()='Reports']");
	}

	public Element getReviewResultReport() {
		return driver.FindElementByXPath("//a[text()='Review Results Report']");
	}

	public Element getFolder(String folderName) {
		return driver.FindElementByXPath("//a[text()='" + folderName + "']");
	}

	public Element getInFolders() {
		return driver.FindElementByXPath("//a[@data-content='All Folders']//following-sibling::ul");
	}

	public Element getInTags() {
		return driver.FindElementByXPath("((//a[text()='All Tags']//following-sibling::ul)[2]//a)[1]//i");
	}

	public Element expandOrCollapseTags() {
		return driver.FindElementByXPath("//span[text()='Tags']//..//following-sibling::a[@data-toggle]");
	}

	public Element selectTag(String tagName) {
		return driver.FindElementByXPath(
				"((//a[text()='All Tags']//following-sibling::ul)[2]//a[@data-content='" + tagName + "'])[1]//i");
	}

	public Element applyChangesButton() {
		return driver.FindElementByXPath("//button[contains(text(),'Apply Changes')]");
	}

	public Element aggregateSummery() {
		return driver.FindElementByXPath("//h2[contains(text(),'Aggregate Summary')]");
	}

	public Element getFileDownload(String text) {
		return driver.FindElementByXPath("(//a[text()='" + text + "'])[1]");
	}

	public Element getCommunicationExplorer_ApplyBtn() {
		return driver.FindElementById("btn_applychanges");
	}

	public Element getCommunicationExplorer_ApplyResult() {
		return driver.FindElementByXPath("//div[@class='font-lg col-md-8']//strong");
	}
	// Added by Iyappan

	public Element getConceptualExplorer_ApplyBtn() {
		return driver.FindElementById("btnAppyFilter");
	}

	public Element getConceptualExplorer_docsCount() {
		return driver.FindElementByXPath("//div[@id='textpannel']/span");
	}

	public Element getSelectSourcesBtnInConceptualExplorerReport() {
		return driver.FindElementByXPath("//a[@id='select-source']");
	}

	public Element getDocCountsInSTReport(String searchName) {
		return driver.FindElementByXPath("//td[text()='" + searchName + "']/parent::tr/td[6]//label");
	}

	// Added by Gopinath - 10/11/2021
	public Element getDocumentAuditReport() {
		return driver.FindElementByXPath("//a[text()='Document Audit Report']");
	}

	public Element getDocID() {
		return driver.FindElementByXPath("//input[@id='txtDocID']");
	}

	public Element getApplyChanges() {
		return driver.FindElementByXPath("//button[@id='btn_applychanges']");
	}

	public Element getTableInReportPage() {
		return driver.FindElementByXPath("//table[@id='dtDocumentAuditData']");
	}

	public ElementCollection getActionsRowCount() {
		return driver.FindElementsByXPath("//table[@id='dtDocumentAuditData']//tr//td[2]");
	}

	// Added by jayanthi
	public Element getReportBTN() {
		return driver.FindElementByXPath("//li//a[@name='Reports']");
	}

	public Element getTagCountByTagBtn() {
		return driver.FindElementByXPath("//td//a[text()='Tag Counts by Tag Report']");
	}

	public Element TagTypeCheckBox(String tagType) {
		return driver.FindElementByXPath("//div[@id='tagLevelid']/div/label/span[text()='" + tagType + "']");
	}

	public Element getTagTypesExpandBtn() {
		return driver.FindElementByXPath("//*[@id='divTagList']/div/a[@data-toggle='collapse']");
	}

	public Element SelectedSources() {
		return driver.FindElementByXPath("//ul[@id='bitlist-sources']//li[@class='active']");
	}

	public Element getdeleteToolTip_CustomReport(String reportName) {
		return driver.FindElementByXPath("//a[text()='" + reportName
				+ "']/parent::td/following-sibling::td/i[@data-original-title='Delete Report']");
	}

	// Added By Jeevitha
	public Element getShowTimelineDD(String options) {
		return driver.FindElementByXPath("//select[@id='ddlTimelineBy']//option[text()='" + options + "']");
	}

	public Element getTimelineReport() {
		return driver.FindElementById("level1timeline");
	}

	public Element getSearchTable() {
		return driver.FindElementById("divSearchGroupTree");
	}

	public Element getSearchLink(String option) {
		return driver.FindElementByXPath("//a[contains(@class,'accordion-toggle')]//strong[text()='" + option + "']");
	}

	// Added by Raghuram
	public Element navigateToReports() {
		return driver.FindElementByXPath("//a[@name='Reports']//i");
	}

	public ReportsPage(Driver driver) {

		this.driver = driver;
		base = new BaseClass(driver);
//		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		// This initElements method will create all WebElements
		// PageFactory.initElements(driver.getWebDriver(), this);
		softAssertion = new SoftAssert();
		communicationExplorer = new CommunicationExplorerPage(driver);
	}
	

	public void TallyReportButton() {
		ReportsPage report = new ReportsPage(driver);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTallyReport().Visible();
			}
		}), Input.wait60);
		report.getTallyReport().Click();
	}

	/**
	 * @author Raghuram.A
	 */
	public void customDataReportMethod() {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getThisLink("Custom Document Data Report"));
			getThisLink("Custom Document Data Report").Click();

			base.waitForElement(getSelectSourcesBtn());
			getSelectSourcesBtn().Click();

			base.waitForElement(getSelectResourcesOption("Searches"));
			getSelectResourcesOption("Searches").Click();

			Thread.sleep(5000);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @author Indium Raghuram Description : Close configureMiniDocTab Date: 8/15/21
	 *         Modified date: 11/2/21 Modified by: Raghuram A
	 */
	public void customDataReportMethodExport(String folderName, Boolean selectMySavedSearch) {

		base.waitForElement(getAddToSelectedBtn());

		try {
			if (selectMySavedSearch) {
				base.waitForElement(getNodeMySavedSearchCheckBox());
				getNodeMySavedSearchCheckBox().Click();
				// driver.scrollingToBottomofAPage();
				getSaveSelectionBtn().Click();
				System.out.println("selectMySavedSearch action performed");
			}
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			base.waitForElement(getMetaDataChoose(Input.sortDataBy));
			driver.scrollingToElementofAPage(getMetaDataChoose(Input.sortDataBy));
			getMetaDataChoose(Input.sortDataBy).waitAndClick(10);
			System.out.println(Input.sortDataBy + "clicked");

			getAvailableObjectsTab("WORKPRODUCT").ScrollTo();

			if (!folderName.equalsIgnoreCase("")) {
				base.waitForElement(getAvailableObjectsTab("WORKPRODUCT"));
				getAvailableObjectsTab("WORKPRODUCT").waitAndClick(5);
				System.out.println("WORKPRODUCT tab selected");

				System.out.println(folderName);
				Thread.sleep(5000);
				getChooseWorkProduct(folderName).waitAndClick(4);
			}

			// driver.scrollingToBottomofAPage();
			base.waitForElement(getAddToSelectedBtn());
			driver.scrollingToElementofAPage(getAddToSelectedBtn());
			getAddToSelectedBtn().waitAndClick(3);

			base.waitForElement(getRunReport());
			getRunReport().Click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Raghuram Date : 9/03/21 Description: Verifying created nodes and
	 *         searches 1.0
	 * @throws InterruptedException
	 */
	public void checkList(String newNodeFromPA, String newNodeFromRMU, String newNodeFromRev, String searchName,
			String searchName1, String currentRole) {
		System.out.println("Via " + currentRole + " role : Verifying RMU created nodes and searches");
		base.stepInfo("Via " + currentRole + " role : Verifying RMU created nodes and searches");
		checkdataPresent(newNodeFromRMU, "RMU");
		checkdataPresent(searchName, "RMU");
		System.out.println("Via " + currentRole + " role : Verifying Reviewer created nodes and searches");
		base.stepInfo("Via " + currentRole + " role : Verifying Reviewer created nodes and searches");
		checkdataPresent(newNodeFromRev, "Reviewer");
		checkdataPresent(searchName1, "Reviewer");
		System.out.println("Via " + currentRole + " role : Verifying PA created nodes and searches");
		base.stepInfo("Via " + currentRole + " role : Verifying PA created nodes and searches");
		checkdataPresent(newNodeFromPA, "PA");
	}

	/**
	 * @author Raghuram Date : 9/03/21 Description:Verifying created nodes and
	 *         searches modified on : 11/2/21
	 * @throws InterruptedException
	 */
	public void checkList(String newNodeFromPA, String newNodeFromRMU, String newNodeFromRev, String searchName,
			String searchName1, String currentRole, String selection) {
		System.out.println("Via " + currentRole + " role : Verifying RMU created nodes and searches");
		base.stepInfo("Via " + currentRole + " role : Verifying RMU created nodes and searches");
		checkdataPresent(newNodeFromRMU, "RMU");
		checkdataPresent(searchName, "RMU");
		System.out.println("Via " + currentRole + " role : Verifying Reviewer created nodes and searches");
		base.stepInfo("Via " + currentRole + " role : Verifying Reviewer created nodes and searches");
		checkdataPresent(newNodeFromRev, "Reviewer");
		checkdataPresent(searchName1, "Reviewer");
		System.out.println("Via " + currentRole + " role : Verifying PA created nodes and searches");
		base.stepInfo("Via " + currentRole + " role : Verifying PA created nodes and searches");
		checkdataPresent(newNodeFromPA, "PA");
		// getNodeCheckBox(newNodeFromPA).Click();
	}

	/**
	 * @author Raghuram Date : 9/03/21 Description:Method for verifying created and
	 *         search nodes
	 * @throws InterruptedException
	 */
	public void checkdataPresent(String nodeName, String role) {
		Boolean check = checkdata(nodeName);
		if (check.equals(true)) {
			System.out.println(role + " datas are Present ");
			base.stepInfo(role + " datas are Present ");
		} else {
			System.out.println(role + " datas are not Present");
			base.stepInfo(role + " datas are not Present ");
		}
	}

	/**
	 * @author Raghuram Date : 9/03/21 Description:Method for verifying created and
	 *         search nodes
	 * @throws InterruptedException
	 */
	public boolean checkdata(String nodeName) {
		if (getNode(nodeName).isElementAvailable(3)) {
			System.out.println("Element Present");
			return true;
		} else {
			System.out.println("Element Not present");
			return false;
		}
	}

	/**
	 * @author Raghuram.A Description : Download file via bullhorn icon
	 * @return
	 * @throws InterruptedException
	 */
	public void downLoadReport() throws InterruptedException {
		/*
		 * Thread.sleep(8000); try { base.waitForElement(getbulHornNotification());
		 * getbulHornNotification().Visible(); } catch (Exception e) {
		 * e.printStackTrace(); }
		 */
		base.waitForElement(getBullHornIcon());
		getBullHornIcon().waitAndClick(10);
		base.waitForElement(getFileDownloaded());
		getFileDownloaded().Click();
		getBullHornIcon().waitAndClick(2);
	}

	/**
	 * @author Raghuram.A Description : Download file via bullhorn icon
	 * @return
	 * @throws InterruptedException
	 */
	public void downLoadReport(int bgCount) throws InterruptedException {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.initialBgCount() == bgCount + 1;
			}
		}), Input.wait60);
		System.out.println("notification count : " + base.initialBgCount());
		base.waitForElement(getBullHornIcon());
		getBullHornIcon().waitAndClick(10);
		base.waitForElement(getFileDownloaded());
		getFileDownloaded().Click();
		getBullHornIcon().waitAndClick(2);
	}

	/**
	 * @author Raghuram.A Description : Exported .csv file data verifications
	 *         Created on : 9/6/21 Description : File Verification
	 * @return
	 */
	public int fileVerification(String fileLocation, String fileName) {
		String line = "";
		String splitBy = ",";
		String[] splitData = null;
		int count;
		List<String> data = new ArrayList<>();// list of lists to store data
		List<String> List = new ArrayList<>();// list of lists to store data
		FileReader file = base.fileLocate(fileLocation, fileName);
		// String[] datas = base.csvReader(file);

		try {
			BufferedReader br = new BufferedReader(file);
			while ((line = br.readLine()) != null) {
				splitData = line.split(splitBy);
				if ((splitData[0]).toString().contains("" + "DocID")) {

				} else {
					data.add(splitData[0].toString());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("-----------------------------------");
		count = data.size();
		System.out.println(count);
		return count;
	}

	/**
	 * @author Raghuram A Created on : 9/6/21 Description : To pick last modified
	 *         file from local drive
	 * @return
	 */
	public File getLatestFilefromDir(String dirPath) {
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}

		File lastModifiedFile = files[0];
		for (int i = 1; i < files.length; i++) {
			if (lastModifiedFile.lastModified() < files[i].lastModified()) {
				lastModifiedFile = files[i];
			}
		}
		return lastModifiedFile;
	}

	/**
	 * @author Raghuram.A Created on : 9/6/21 Modified on : 9/7/21 Modified by :
	 *         Raghuram A Description : report Generator and verification
	 *         RPMXCON-57409
	 * @return
	 */
	public void VerificationAndreportGenerator(String newNodeFromPA, String newNodeFromRMU, String newNodeFromRev,
			String folderName, String searchName, String searchNmae1, int pureHit) throws InterruptedException {
		// ReportPage
		new ReportsPage(driver);
		customDataReportMethod();
		checkList(newNodeFromPA, newNodeFromRMU, newNodeFromRev, searchName, searchNmae1, "RMU");
		getSelectSearchCloseBtn().waitAndClick(5);
		driver.waitForPageToBeReady();

		// impersonate As RMU via PA
		driver.waitForPageToBeReady();
		base.impersonateSAtoPA();
		// Get initial notification count
		int Bgcount = base.initialBgCount();
		System.out.println("Initial bg count : " + Bgcount);

		// Report Page
		new ReportsPage(driver);
		customDataReportMethod();
		checkList(newNodeFromPA, newNodeFromRMU, newNodeFromRev, searchName, searchNmae1, "PA", "Yes");
		customDataReportMethodExport(folderName, true);
		Thread.sleep(10000);// Wait until the report is available for download

		driver.waitForPageToBeReady();

		// Download report
		downLoadReport(Bgcount);
		base.stepInfo("File Downloaded");

		File ab = new File(Input.fileDownloadLocation);
		String testPath = ab.toString() + "//";

		// base.csvReader();
		File a = getLatestFilefromDir(testPath);
		System.out.println(a.getName());
		String fileName = a.getName();

		int countToCompare = fileVerification(testPath, fileName);
		System.out.println(countToCompare);
		System.err.println(pureHit);
		if (countToCompare == pureHit) {
			System.out.println("Pass");
			base.passedStep("Purehit and File count matches");
		} else {
			System.out.println("Fail");
			base.failedStep("Purehit and File count doesn't match");
		}
	}

	/**
	 * @author jeevitha
	 * @param foldername
	 * @param assignment
	 * @return
	 * @throws InterruptedException
	 */
	public int advanceBatchManagementReport(String foldername, String assignment) throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "Review/AdvancedBatchManagementReport");
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddNewSourceLink().Visible();
			}
		}), Input.wait60);
		getAddNewSourceLink().waitAndClick(30);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFoldersLink().Visible();
			}
		}), Input.wait60);
		getFoldersLink().Click();
		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectFolder(foldername).Visible();
			}
		}), Input.wait60);
		getSelectFolder(foldername).Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderSaveSelectionBtn().Visible();
			}
		}), Input.wait60);
		getFolderSaveSelectionBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getReviewersDD().Visible();
			}
		}), Input.wait60);
		getReviewersDD().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getReviewersCheckbox().Visible();
			}
		}), Input.wait60);
		getReviewersCheckbox().Click();

		BaseClass base = new BaseClass(driver);
		Thread.sleep(3000);
		base.waitForElement(getReviewersDD());
		getReviewersDD().waitAndClick(10);
		Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentDD().Visible();
			}
		}), Input.wait60);
		getAssignmentDD().waitAndClick(10);
		Thread.sleep(3000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignment(assignment).Visible();
			}
		}), Input.wait60);
		getAssignment(assignment).waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAssignmentDD().Visible();
			}
		}), Input.wait60);
		getAssignmentDD().waitAndClick(10);
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getApplyChangesBtn().Visible();
			}
		}), Input.wait60);
		getApplyChangesBtn().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTotalDocs().Visible();
			}
		}), Input.wait60);
		base.waitForElement(getTotalDocs());
		int totalDocs = Integer.valueOf(getTotalDocs().getText());
		return totalDocs;
	}

	/**
	 * @author Jeevitha
	 * @param folderName
	 * @param tagName
	 */
	public void verifyFolderNotPresent(String folderName, String tagName) {
		try {
			selectFoldersInReviewResult(folderName);
		} catch (Exception e) {
			try {
				System.out.println("Folder Not Present!");
				selectTagsInReviewResult(tagName);
			} catch (Exception e1) {
				System.out.println("Tag Not Present!");
			}
		}
	}

	public void reviewResultReport() {
		getReports().waitAndClick(10);
		getReviewResultReport().waitAndClick(15);
	}

	/**
	 * @Author jeevitha
	 * @param folderName
	 */
	public void selectFoldersInReviewResult(String folderName) {
		getAddNewSourceLink().waitAndClick(30);
		getFoldersLink().Click();
		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getInFolders().Visible();
			}
		}), Input.wait60);
		driver.scrollingToBottomofAPage();
		getInFolders().waitAndClick(10);
		getInFolders().waitAndClick(10);
		getFolder(folderName).waitAndClick(10);
		getFolderSaveSelectionBtn().Click();
	}

	/**
	 * @author Jeevitha
	 * @param tagName
	 */
	public void selectTagsInReviewResult(String tagName) {
		expandOrCollapseTags().Click();
		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getInTags().Visible();
			}
		}), Input.wait60);
		getInTags().waitAndClick(10);
		getInTags().waitAndClick(10);
		selectTag(tagName).Click();
		driver.scrollPageToTop();
	}

	/**
	 * @author jeevitha
	 */
	public void verifyAggregateSummary() {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return aggregateSummery().Visible();
			}
		}), Input.wait60);
		Assert.assertEquals("Aggregate Summary", aggregateSummery().getText().trim());
	}

	/**
	 * @author Raghuram A Date: 9/25/21 Modified date:N/A Modified by: N/A
	 *         Description :
	 */
	public void verifyCustomReportPresent(String reportName) {
		try {
			getCustomReport(reportName).Displayed();
			base.stepInfo(reportName + " is present");
		} catch (Exception e) {
			System.out.println("Not available");
			base.stepInfo(reportName + " Not available");
		}
	}

	/**
	 * @author Raghuram A Date: 11/2/21 Modified date:N/A Modified by: N/A
	 *         Description :
	 */
	public void TestcustomDataReportMethodExport(String folderName) {
		try {
			base.waitForElement(getMetaDataChoose(Input.sortDataBy));
			getMetaDataChoose(Input.sortDataBy).Click();
			System.out.println(Input.sortDataBy + "clicked");

			base.waitForElement(getAvailableObjectsTab("WORKPRODUCT"));
			getAvailableObjectsTab("WORKPRODUCT").Click();
			System.out.println("WORKPRODUCT tab selected");

			System.out.println(folderName);
			Thread.sleep(5000);
			getChooseWorkProduct(folderName).waitAndClick(4);

			driver.scrollingToBottomofAPage();
			base.waitForElement(getAddToSelectedBtn());
			getAddToSelectedBtn().waitAndClick(3);

			driver.scrollPageToTop();
			base.waitForElement(getRunReport());
			getRunReport().Click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Raghuram A Date: 11/2/21 Modified date:N/A Modified by: N/A
	 *         Description :
	 */
	public void communicationExp() {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getThisLink("Communications Explorer"));
			getThisLink("Communications Explorer").Click();

			base.waitForElement(getSelectSourcesBtn());
			getSelectSourcesBtn().Click();

			driver.waitForPageToBeReady();
			base.waitForElement(getSelectResourcesOption("Searches"));
			getSelectResourcesOption("Searches").waitAndClick(5);

			driver.waitForPageToBeReady();

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * @author Raghuram A Date: 11/2/21 Modified date:N/A Modified by: N/A
	 *         Description :
	 */
	public void VerificationAndrcommunicationReport(String newNodeFromPA, String newNodeFromRMU, String newNodeFromRev,
			String searchName, String searchNmae1, int pureHit) throws InterruptedException {
		// ReportPage
		new CommunicationExplorerPage(driver);
		communicationExp();
		checkList(newNodeFromPA, newNodeFromRMU, newNodeFromRev, searchName, searchNmae1, "RMU");
		getSelectSearchCloseBtn().waitAndClick(5);
		driver.waitForPageToBeReady();

		// impersonate As RMU via PA
		driver.waitForPageToBeReady();
		base.impersonateSAtoPA();

		// Report Page
		new CommunicationExplorerPage(driver);
		communicationExp();
		checkList(newNodeFromPA, newNodeFromRMU, newNodeFromRev, searchName, searchNmae1, "PA", "Yes");
		communnicationReportMethodExport(true);

	}

	/**
	 * @author Raghuram A Date: 11/2/21 Modified date:N/A Modified by: N/A
	 *         Description :
	 */
	public void communnicationReportMethodExport(Boolean selectMySavedSearch) {
		try {
			if (selectMySavedSearch) {
				base.waitForElement(getNodeMySavedSearchCheckBox());
				getNodeMySavedSearchCheckBox().Click();
				getSaveSelectionBtn().Click();
				System.out.println("selectMySavedSearch action performed");
			}

			// driver.scrollPageToTop();
			base.waitForElement(getCommunicationExplorer_ApplyBtn());
			getCommunicationExplorer_ApplyBtn().waitAndClick(5);

			base.waitForElement(getCommunicationExplorer_ApplyResult());
			base.stepInfo(getCommunicationExplorer_ApplyResult().getText());

			if (getCommunicationExplorer_ApplyResult().Displayed()) {
				base.passedStep("Report Generated");
			} else {
				base.failedMessage("Report not generated");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description this method select searches from conceptual export report
	 */
	public void conceptualExp() {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getThisLink("Concept Explorer Report"));
			getThisLink("Concept Explorer Report").Click();
			base.waitForElement(getSelectSourcesBtnInConceptualExplorerReport());
			getSelectSourcesBtnInConceptualExplorerReport().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getSelectResourcesOption("Searches"));
			getSelectResourcesOption("Searches").waitAndClick(5);
			driver.waitForPageToBeReady();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description This method verifies the saved searches are present in
	 *              conceptual report page
	 */
	public void VerificationOfConceptualReport(String newNodeFromPA, String newNodeFromRMU, String newNodeFromRev,
			String searchName, String searchNmae1, String searchName2) throws InterruptedException {
		// ReportPage
		new ConceptExplorerPage(driver);
		conceptualExp();
		checkList(newNodeFromPA, newNodeFromRMU, newNodeFromRev, searchName, searchNmae1, "RMU");
		checkdataPresent(searchName2, "PA");
		getSelectSearchCloseBtn().waitAndClick(5);
		driver.waitForPageToBeReady();

		// impersonate As RMU via PA
		driver.waitForPageToBeReady();
		base.impersonateSAtoPA();

		// Report Page
		new CommunicationExplorerPage(driver);
		conceptualExp();
		checkList(newNodeFromPA, newNodeFromRMU, newNodeFromRev, searchName, searchNmae1, "PA", "Yes");
		checkdataPresent(searchName2, "PA");
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description This method select and verified the counts of saved searches in
	 *              conceptual report page
	 */
	public void selectSearchAndVerifyPurehitsInConceptualReports(String searchNode, int pureHits) {
		try {
			base.waitTillElemetToBeClickable(getNodeCheckBox(searchNode));
			getNodeCheckBox(searchNode).waitAndClick(5);
			getSaveSelectionBtn().Click();
			base.waitForElement(getConceptualExplorer_ApplyBtn());
			getConceptualExplorer_ApplyBtn().waitAndClick(5);
			base.waitForElement(getConceptualExplorer_docsCount());
			getConceptualExplorer_docsCount().getText().contains(Integer.toString(pureHits));
			base.passedStep(
					"Concept Explorer report generated with the selected searches under 'My Searches' for PAU role");
			base.stepInfo(getConceptualExplorer_docsCount().getText());
		} catch (Exception e) {
			base.failedStep("Failed to generate the conceptual explorer report");
		}

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description This method verifies the saved searches are present in search
	 *              term report page
	 */
	public void VerificationOfSTReport(String newNodeFromPA, String newNodeFromRMU, String newNodeFromRev,
			String searchName, String searchNmae1, String searchName2) throws InterruptedException {
		// ReportPage
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		base.waitForElement(getThisLink("Search Term Report"));
		getThisLink("Search Term Report").Click();
		checkList(newNodeFromPA, newNodeFromRMU, newNodeFromRev, searchName, searchNmae1, "RMU");
		checkdataPresent(searchName2, "PA");
		driver.waitForPageToBeReady();

		// impersonate As RMU via PA
		driver.waitForPageToBeReady();
		base.impersonateSAtoPA();

		// Report Page
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		base.waitForElement(getThisLink("Search Term Report"));
		getThisLink("Search Term Report").Click();
		checkList(newNodeFromPA, newNodeFromRMU, newNodeFromRev, searchName, searchNmae1, "PA", "Yes");
		checkdataPresent(searchName2, "PA");
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description This method select and verified the counts of saved searches in
	 *              conceptual report page
	 */
	public void verifyDocCountsInSTReport(String searchName, int pureHits) {
		try {
			SoftAssert softAssertion = new SoftAssert();
			base.waitTillElemetToBeClickable(getNodeCheckBox(searchName));
			getNodeCheckBox(searchName).waitAndClick(5);
			base.waitForElement(getCommunicationExplorer_ApplyBtn());
			getCommunicationExplorer_ApplyBtn().waitAndClick(5);
			base.waitForElement(getDocCountsInSTReport(searchName));
			String docs = getDocCountsInSTReport(searchName).getText();
			softAssertion.assertEquals(docs, Integer.toString(pureHits));
			base.passedStep("Search term report generated with the selected searches under 'My Searches' for PAU role");
		} catch (Exception e) {
			base.failedStep("Failed to generate search term report");
		}
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by : NA.
	 * @description This method select and verified the counts of saved searches in
	 *              conceptual report page
	 * @param Document         : Document is a String value that document id.
	 * @param ProductionStatus : ProductionStatus is String value that production
	 *                         status.
	 */
	public void selectDocumentAuditReport(String Document, String ProductionStatus) throws InterruptedException {

		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDocumentAuditReport().Enabled();
				}
			}), 30000);
			getDocumentAuditReport().waitAndClick(10);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDocID().Enabled();
				}
			}), 30000);
			getDocID().SendKeys(Document);

			driver.waitForPageToBeReady();
			base.waitForElement(getApplyChanges());
			base.waitTime(3);
			getApplyChanges().isElementAvailable(10);
			getApplyChanges().Click();

			Thread.sleep(Input.wait30 / 10);
			for (int i = 0; i < 2; i++) {
				try {
					base.waitForElement(getTableInReportPage());
					base.waitTillElemetToBeClickable(getTableInReportPage());
					break;
				} catch (Exception e) {
					driver.waitForPageToBeReady();
					base.waitForElement(getApplyChanges());
					getApplyChanges().Click();
				}
			}
			if (!getActionsRowCount().isElementAvailable(6)) {
				driver.Navigate().refresh();
			}
			List<WebElement> RowData = getActionsRowCount().FindWebElements();
			System.out.println(RowData.size());
			List<String> values = new ArrayList<String>();
			String rowcounts;
			String expectedtext = ProductionStatus;
			int j;
			for (j = 0; j < RowData.size(); j++) {
				driver.waitForPageToBeReady();
				rowcounts = RowData.get(j).getText();
				values.add(RowData.get(j).getText());
			}
			System.out.println(values);
			if (values.contains(expectedtext)) {
				base.passedStep("" + expectedtext + " text is available as expected");
			} else {
				base.failedStep("" + expectedtext + " text is not available as expected");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while filling burnredaction" + e.getMessage());
		}
	}

	/**
	 * @author Jayanthi.Ganesan
	 * @param reportName
	 */
	public void verifyCustomReportDisplaydetails(String reportName) {
		try {
			if (getCustomReport(reportName).Displayed()) {
				base.passedStep(reportName + " is present under custom report block.");
			} else {
				base.failedStep(reportName + "is not Present in report landing page");
			}
			getCustomReport(reportName).waitAndClick(10);
			driver.waitForPageToBeReady();
			softAssertion.assertEquals(SelectedSources().getText(), "Security Group: Default Security Group");
			base.passedStep(
					"Report opened and selected criteria while saving the custom report is retained as expected.");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured" + e.getMessage());

		}
	}

	/**
	 * @author Jayanthi.Ganesan
	 * @param reportName
	 */
	public void deleteCustomReport(String reportName) {
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.scrollingToElementofAPage(getdeleteToolTip_CustomReport(reportName));
		if (getdeleteToolTip_CustomReport(reportName).Displayed()) {
			getdeleteToolTip_CustomReport(reportName).waitAndClick(10);
			base.waitForElement(base.getYesBtn());
			base.getYesBtn().waitAndClick(10);
			base.VerifySuccessMessage("Report deleted successfully");
		}
	}

	/**
	 * @author Jeevitha
	 */
	public void navigateToTimelineReportPage() {
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.waitForPageToBeReady();
		this.driver.getWebDriver().get(Input.url + "Report/Timeline");
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Jeevitha
	 */
	public void verifySearchFromTGReport(String options, String searchNode, boolean selectSave, boolean selectCB) {

		base.waitForElement(getAddNewSourceLink());
		getAddNewSourceLink().waitAndClick(30);

		driver.waitForPageToBeReady();
		base.waitForElement(getSearchLink(options));
		getSearchLink(options).waitAndClick(5);

		driver.waitForPageToBeReady();
		if (getNodeCheckBox(searchNode).isElementAvailable(5)) {
			System.out.println(searchNode + " : Is Present ");
			base.stepInfo(searchNode + " : Is Present ");
			if (selectCB) {
				getNodeCheckBox(searchNode).waitAndClick(5);
			}
		} else {
			System.out.println(searchNode + " : Is Not Present ");
			base.stepInfo(searchNode + " : Is Not Present ");
		}

		if (selectSave) {
			base.waitForElement(getSaveSelectionBtn());
			getSaveSelectionBtn().waitAndClick(10);
		}
		base.waitForElement(getSearchLink(options));
		getSearchLink(options).waitAndClick(5);

	}

	/**
	 * @author Jeevitha
	 */
	public void generateTimelineAndGapsReport(String options) {

		if (getShowTimelineDD(options).isElementAvailable(4)) {
			getShowTimelineDD(options).waitAndClick(6);
			base.passedStep(options + " :Is Selected In DropDown");
			System.out.println(options + " :Is Selected In DropDown");
		}

		driver.scrollPageToTop();
		base.waitForElement(getApplyChangesBtn());
		getApplyChangesBtn().waitAndClick(5);

		driver.waitForPageToBeReady();
		if (getTimelineReport().isElementAvailable(40)) {
			base.stepInfo("Timeline Report generated sucessfull");
		} else {
			base.failedStep("Timeline Report generated sucessfull");
		}
	}

	/**
	 * @author Raghuram.A
	 * @createdOn : 07/05/22
	 * @ModifiedOn : 8/10/22
	 * @ModifiedBy : Raghuram
	 * @description : Navigation to Reports Page
	 */
	public void navigateToReportsPage(String componentName) {
		driver.scrollPageToTop();
		navigateToReports().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.verifyPageNavigation("Report/ReportsLanding");
		if (!componentName.equalsIgnoreCase("") && componentName.equalsIgnoreCase("Concept Explorer Report")) {
			getThisLink(componentName).waitAndClick(10);
			base.verifyPageNavigation("DataAnalysisReport/ConceptExplorer");
		} else if (!componentName.equalsIgnoreCase("") && componentName.equalsIgnoreCase("Communications Explorer")) {
			getThisLink(componentName).waitAndClick(10);
			base.verifyPageNavigation("DataAnalysisReport/CommunicationExplorerReport");
		}else if (!componentName.equalsIgnoreCase("") && componentName.equalsIgnoreCase("Search Term Report")) {
			getThisLink("Search Term Report").waitAndClick(10);
			base.verifyPageNavigation("DataAnalysisReport/SearchTermReport");
		}else if(!componentName.equalsIgnoreCase("") && componentName.equalsIgnoreCase("Advanced Batch Management Report")) {
			getThisLink("Advanced Batch Management Report").waitAndClick(10);
			base.verifyPageNavigation("Review/AdvancedBatchManagementReport");
		}

	}

}