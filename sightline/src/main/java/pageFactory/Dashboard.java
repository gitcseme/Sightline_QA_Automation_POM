package pageFactory;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;
import org.apache.commons.collections4.Get;
import org.testng.asserts.SoftAssert;
import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;

public class Dashboard {
	Driver driver;
	BaseClass base;
	Element element;
	SoftAssert assertion;

	// Added by sowndarya.velraj

	public Element dashboardWidgetIcon() {
		return driver.FindElementByXPath("//a[@id='wEdit']");
	}

	public Element btnAddNewWidget() {
		return driver.FindElementByXPath("//input[@id='wAdd']");
	}

	public Element selectWidget(String widget) {
		return driver.FindElementByXPath("//ul[@id='DataCollection']//span[contains(text(),'" + widget + "')]");
	}

	public Element btnAddToDashboard() {
		return driver.FindElementById("btnAddToDashboard");
	}

	public Element warningPopup() {
		return driver.FindElementByXPath("//div[@id='bigBoxColor2']//p");
	}

	public Element btndeleteWidgetInDashboard() {
		return driver.FindElementByXPath("(//div[@class='jarviswidget-ctrls']//a)[last()]");
	}

	public Element btnYes_deleteWidget() {
		return driver.FindElementByXPath("//div[@class='MessageBoxButtonSection']//button[contains(text(),'Yes')]");
	}

	public Element btnNo_deleteWidget() {
		return driver.FindElementByXPath("//div[@class='MessageBoxButtonSection']//button[contains(text(),'No')]");
	}

	public Element reviewerProgress_gearIcon() {
		return driver.FindElementByXPath("//span[contains(text(),'Reviewer Progress')]//..//i[@id='OpenPop']");
	}

	public Element reviewerProductivity_gearIcon() {
		return driver.FindElementByXPath("//i[@id='OpenReviewProductivityPop']");
	}

	public Element getSelectReviewersChkbox() {
		return driver.FindElementByXPath("//label[contains(normalize-space(),'Select Reviewers :')]");
	}

	public Element getSelectReviewersRadioBtn() {
		return driver.FindElementByXPath(
				"//div[@id='Subradiobutton']//label[contains(normalize-space(),'Select Reviewers.')]");
	}

	public Element selectReviewers_mostToDoDocs() {
		return driver.FindElementByXPath(
				"//p[contains(text(),'Select Reviewers')]//..//label[contains(normalize-space(),'Most To Do Docs')]");
	}

	public Element selectReviewers_mostCompleteDocs() {
		return driver.FindElementByXPath(
				"//p[contains(text(),'Select Reviewers')]//..//label[contains(normalize-space(),'Most Complete Docs')]");
	}

	public Element selectReviewers_selectReviewers() {
		return driver.FindElementByXPath(
				"//p[contains(text(),'Select Reviewers')]//..//label[contains(normalize-space(),'Select Specific Reviewers')]");
	}

	public Element btnSave_customizeWidget() {
		return driver.FindElementByXPath("//button[@onclick='ReviewerProcessOpreation();']");
	}

	public Element arrow_selectAssignment() {
		return driver.FindElementByXPath("//i[@class='jstree-icon jstree-ocl']");
	}

	public Element selectAssignment_CustomizeWidget(String assignment) {
		return driver.FindElementByXPath(
				"//div[@id='treeAssignment-review-progress']//a[contains(text(),'" + assignment + "')]");
	}

	public ElementCollection countOfWidget() {
		return driver.FindElementsByXPath("//div[@class='wellHdr font-md']");
	}

	public Element selectSpecificReviewer() {
		return driver.FindElementByXPath("//select[@id='UserList']");
	}

	public Element selectSpecificReviewer_reviewProductivity() {
		return driver.FindElementByXPath("//select[@id='UserList-rpd']");
	}

	public Element btnSave_reviewProductivity() {
		return driver.FindElementByXPath("//form[@id='Edit User Group']//button[@id='btnReviewerProductivityFilter']");
	}

	public Element mostToDoDocs_InsideWidget() {
		return driver.FindElementByXPath("//label[contains(text(),'Most To Do Docs')]");
	}

	public Element mostCompletedDocs_InsideWidget() {
		return driver.FindElementByXPath("//label[contains(text(),'Completed')]");
	}

	public Element selectProjectName(String project) {
		return driver.FindElementByXPath("//strong[contains(text(),'" + project + "')]");
	}

	public ElementCollection selectedTags_TaggingWidget() {
		return driver.FindElementsByXPath("//div[@id='tableex']//tbody//th[@class='text-left']");
	}

	public ElementCollection privilegedAndResponsiveTagCount() {
		return driver.FindElementsByXPath("//table[@id='TotalTagsCountTable']//td[@class='text-center']");
	}

	public ElementCollection privilegedAndResponsiveTagTableCount() {
		return driver.FindElementsByXPath("//table[@id='TotalTagsCountTable']//th[@class='text-center']");
	}

	public Element txtselectedTags() {
		return driver.FindElementByXPath("//span[text()='Selected Tags']");
	}

	public Element releasedCount_EndToEnd() {
		return driver
				.FindElementByXPath("//table[@id='taskbasic']//div//label[contains(text(),'RELEASED:')]//..//span");
	}

	public Element notReviewedCount_EndToEnd() {
		return driver.FindElementByXPath("//table[@id='taskbasic']//div//label[text()='NOT REVIEWED:']//..//span");
	}

	public Element reviewedCount_EndToEnd() {
		return driver.FindElementByXPath("//table[@id='taskbasic']//div//label[text()='REVIEWED:']//..//span");
	}

	public Element totalProducedCount() {
		return driver.FindElementByXPath("//tbody//td[@class='text-center odd']//span");
	}

	public Element getSelectAssignmentFromDashborad(String assignmentName) {
		return driver.FindElementByXPath("//*[@id='dt_basic']//strong[text()='" + assignmentName + "']");
	}

	public Element getAssignmentData(String assignmentName, int i) {
		return driver.FindElementByXPath("//strong[text()='" + assignmentName + "']//..//..//..//td[" + i + "]");
	}

	public ElementCollection getAssignmentsTableName() {
		return driver.FindElementsByXPath("//table[@id='dt_basic']//th");
	}

	public Element getCenterStyle() {
		return driver.FindElementByXPath(
				"\\#taskbasic > tbody >tr:first-child > td > div.col-md-12\\).style.getPropertyValue('left')");
	}

	public ElementCollection getReviewerProgressTableName() {
		return driver.FindElementsByXPath("//table[@id='ReviewerTable']//th");
	}

	public Element getReviewerProgressData(int i) {
		return driver.FindElementByXPath("//table[@id='ReviewerTable']//tbody//tr//td[" + i + "]");
	}

	public Element getReviewerProgressName(String fullName) {
		return driver.FindElementByXPath("//table[@id='ReviewerTable']//tbody//td[text()='" + fullName + "']");
	}

	public Element get1LR_completedPercent() {
		return driver.FindElementByXPath("//div[@id='oneLRdiv']//h6[text()='1LR']//..//ul//span[@class='txt-green']");
	}

	public Element get2LR_completedPercent() {
		return driver.FindElementByXPath("//div[@id='oneLRdiv']//h6[text()='2LR']//..//ul//span[@class='txt-green']");
	}

	public Element getQC_completedPercent() {
		return driver.FindElementByXPath("//div[@id='oneLRdiv']//h6[text()='QC']//..//ul//span[@class='txt-green']");
	}

	public Element get1LR_distirbutedPercent() {
		return driver.FindElementByXPath("//div[@id='oneLRdiv']//h6[text()='1LR']//..//ul//span[@class='txt-yellow']");
	}

	public Element get2LR_distirbutedPercent() {
		return driver.FindElementByXPath("//div[@id='oneLRdiv']//h6[text()='2LR']//..//ul//span[@class='txt-yellow']");
	}

	public Element getQC_distirbutedPercent() {
		return driver.FindElementByXPath("//div[@id='oneLRdiv']//h6[text()='QC']//..//ul//span[@class='txt-yellow']");
	}

	public Element get1LR_notDistirbutedPercent() {
		return driver.FindElementByXPath("//div[@id='oneLRdiv']//h6[text()='1LR']//..//ul//span[@class='txt-red']");
	}

	public Element get2LR_notDistirbutedPercent() {
		return driver.FindElementByXPath("//div[@id='oneLRdiv']//h6[text()='2LR']//..//ul//span[@class='txt-red']");
	}

	public Element getQC_notDistirbutedPercent() {
		return driver.FindElementByXPath("//div[@id='oneLRdiv']//h6[text()='QC']//..//ul//span[@class='txt-red']");
	}

	public ElementCollection listOfReviewers() {
		return driver.FindElementsByXPath("//table[@id='taskbasic']//strong");
	}

	public ElementCollection getReviewerProgressNameList(int indexNum) {
		return driver.FindElementsByXPath("//table[@id='ReviewerTable']//tbody//td[" + indexNum + "]");
	}

	public Element getReviewerProgressNameListRespectiveDetails(String name, int indexNum) {
		return driver.FindElementByXPath(
				"//table[@id='ReviewerTable']//tbody//td[text()='" + name + "']//..//td[" + indexNum + "]");
	}

	public Element cancelDeleteWidgetIcon() {
		return driver.FindElementByXPath("//a[@id='wCancel']");
	}

	// Added By Jeevitha

	public Element getPopupCloseBtn() {
		return driver.FindElementByXPath("//div[@id='PreviewHeader']//button");
	}

	public Element widgetPopupHeader() {
		return driver.FindElementByXPath("//div[@id='PreviewHeader']//h4");
	}

	public ElementCollection widgetsPresentInPopup() {
		return driver.FindElementsByXPath("//ul[@id='DataCollection']//span");
	}

	public Element DashboardWidgetHeader(String widgetName) {
		return driver.FindElementByXPath("//section[@id='widget-grid']//span[contains(text(),'" + widgetName + "')]");
	}

	public Element saveWidgetIcon() {
		return driver.FindElementByXPath("//a[@id='wSave']");
	}

	public Element widgetLibraryPopup() {
		return driver.FindElementByXPath(
				"//div[@class='modal-backdrop fade in']//..//..//div//div[@id='myModal']//div[@class='modal-content']");
	}

	public ElementCollection getReviewerProdTop6Header() {
		return driver.FindElementsByXPath("//div[@id='divReviewerProuctivity']//tbody//td//strong");
	}

	// Added by Mohan
	public Element getAssignmentHeaderInDashboard() {
		return driver.FindElementByXPath("//h2[contains(text(),'Assignments within Assignment Group >>')]");
	}

	public Element getAssignmentPageTitleInAssignmentPage() {
		return driver.FindElementByXPath("//h1[text()='My Assignments']");
	}
	public Element getDomainDashboardPage() {
		return driver.FindElementByXPath("//a[@name='DomainDashboard']");
	}
	public Dashboard(Driver driver) {

		this.driver = driver;
		base = new BaseClass(driver);
		assertion = new SoftAssert();

	}

	/**
	 * @authorSowndarya.velraj
	 * @Description : To navigate to dashboard
	 */
	public void navigateToDashboard() {
		try {
			driver.getWebDriver().get(Input.url + "Dashboard/Dashboard");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while navigating to production page" + e.getMessage());
		}
	}

	/**
	 * @authorSowndarya.velraj
	 * @Description : To add new widget "Reviewer Progress" in the dashboard
	 */
	public void AddNewWidgetToDashboard(String widget) {

		navigateToDashboard();

		base.waitForElementCollection(countOfWidget());
		int count = countOfWidget().size();
		System.out.println("count of widget : " + count);
		if (count < 3) {
			driver.Navigate().refresh();
			dashboardWidgetIcon().waitAndClick(10);
			btnAddNewWidget().waitAndClick(10);
			selectWidget(widget).waitAndClick(10);
			driver.scrollingToBottomofAPage();
			btnAddToDashboard().waitAndClick(10);

		} else if (count == 3) {
			navigateToDashboard();
			base.waitForElement(dashboardWidgetIcon());
			dashboardWidgetIcon().waitAndClick(10);
			selectProjectName(Input.projectName).ScrollTo();
			base.waitTime(2);
			base.waitForElement(btndeleteWidgetInDashboard());
			btndeleteWidgetInDashboard().waitAndClick(10);

			base.waitForElement(btnYes_deleteWidget());
			btnYes_deleteWidget().waitAndClick(10);
			base.VerifySuccessMessage("Widget Deleted");
			driver.waitForPageToBeReady();
			driver.Navigate().refresh();
			base.waitForElement(dashboardWidgetIcon());
			dashboardWidgetIcon().waitAndClick(10);
			base.waitForElement(btnAddNewWidget());
			btnAddNewWidget().waitAndClick(10);
			base.waitForElement(selectWidget(widget));
			selectWidget(widget).waitAndClick(10);
			driver.scrollingToBottomofAPage();
			btnAddToDashboard().waitAndClick(10);
			base.stepInfo("New widget is added");
		}

	}

	/**
	 * @authorSowndarya.velraj
	 * @Description : To add new widget "Reviewer Progress" in the dashboard
	 */
	public void DeleteWidgetFromDashboard() {

		navigateToDashboard();
		driver.Navigate().refresh();
		base.waitForElementCollection(countOfWidget());
		int count = countOfWidget().size();
		System.out.println("count of widget from dashboard: " + count);

		for (int i = count; i > 0; i--) {

			base.waitForElement(dashboardWidgetIcon());
			dashboardWidgetIcon().waitAndClick(10);
			selectProjectName(Input.projectName).ScrollTo();
			base.waitForElement(btndeleteWidgetInDashboard());
			btndeleteWidgetInDashboard().waitAndClick(10);

			base.waitForElement(btnYes_deleteWidget());
			btnYes_deleteWidget().waitAndClick(10);
			base.VerifySuccessMessage("Widget Deleted");
			cancelDeleteWidgetIcon().ScrollTo();
			cancelDeleteWidgetIcon().waitAndClick(10);
		}

	}

	/**
	 * @authorSowndarya.velraj
	 * @Description : To customize Reviewer Progress Widget
	 */
	public void customizeReviewerProgressWidget(String radioBtn, String fullName, String assignmentName) {

		driver.waitForPageToBeReady();
		reviewerProgress_gearIcon().waitAndClick(10);
		base.stepInfo("Select Reviewers");
		if (radioBtn.equalsIgnoreCase("ToDoDocs")) {
			selectReviewers_mostToDoDocs().waitAndClick(10);
			System.out.println("selected - " + selectReviewers_mostToDoDocs().getText());
		} else if (radioBtn.equalsIgnoreCase("Complete")) {
			selectReviewers_mostCompleteDocs().waitAndClick(10);
			System.out.println("selected - " + selectReviewers_mostCompleteDocs().getText());
		} else {
			selectReviewers_selectReviewers().waitAndClick(10);
			base.waitForElement(selectSpecificReviewer());
			selectSpecificReviewer().selectFromDropdown().selectByVisibleText(fullName);
		}
		base.stepInfo("selecting assignment");
		arrow_selectAssignment().waitAndClick(10);
		base.waitForElement(selectAssignment_CustomizeWidget(assignmentName));
		selectAssignment_CustomizeWidget(assignmentName).waitAndClick(10);
		base.waitForElement(btnSave_customizeWidget());
		btnSave_customizeWidget().waitAndClick(10);
	}

	/**
	 * @authorSowndarya.velraj
	 * @Description : To verify selected tags
	 */
	public void verifySelectedTagsInTaggingWidget() {

		base.waitForElementCollection(selectedTags_TaggingWidget());
		List<String> listofElements = base.availableListofElements(selectedTags_TaggingWidget());
		base.waitForElementCollection(privilegedAndResponsiveTagTableCount());
		List<String> elements = base.availableListofElements(privilegedAndResponsiveTagTableCount());
		if (txtselectedTags().isElementAvailable(5)) {
			base.passedStep("Selected tag 1 :" + listofElements.get(0) + " counts are :" + elements.get(2));
			base.passedStep("Selected tag 2 :" + listofElements.get(1) + "counts are : " + elements.get(3));
			base.passedStep("Selected tag 3 :" + listofElements.get(2) + "counts are : " + elements.get(4));
			base.passedStep("Selected tag 4 :" + listofElements.get(3) + "counts are : " + elements.get(5));
			base.passedStep("Selected tag 5 :" + listofElements.get(4) + "counts are : " + elements.get(6));
			base.passedStep("Selected tag 6 :" + listofElements.get(5) + "counts are : " + elements.get(7));
		}
	}

	/**
	 * @authorSowndarya.velraj
	 * @Description : To verify privileged and responsive tags count
	 */
	public void verifyPrivilegedAndResponsiveTags() {
		driver.waitForPageToBeReady();
		List<String> elements = base.availableListofElements(privilegedAndResponsiveTagTableCount());
		String priv = elements.get(0);
		System.out.println(priv);
		String resp = elements.get(1);
		System.out.println(resp);
		base.waitTime(2);
		List<String> availableListofElements = base.availableListofElements(privilegedAndResponsiveTagCount());

		String PrivilegedCount = availableListofElements.get(0);
		System.out.println(PrivilegedCount);
		String ResponsiveCount = availableListofElements.get(1);
		System.out.println(ResponsiveCount);
		if (priv.equalsIgnoreCase("Privileged") && resp.equalsIgnoreCase("Responsive")) {
			base.passedStep("Privileged counts are : " + PrivilegedCount);
			base.passedStep("Responsive  counts are : " + ResponsiveCount);
		}

	}

	/**
	 * @authorSowndarya.velraj
	 * @Description : To verify end to end value and count
	 */
	public void verifyAssignmentData(String assignmentName) {

		if (getSelectAssignmentFromDashborad(assignmentName).isDisplayed()) {
			base.passedStep("Assignment created with name as : " + assignmentName);
		}
		List<String> availableListofElements = base.availableListofElements(getAssignmentsTableName());

		int size = getAssignmentsTableName().size();
		String data;
		System.out.println(size);
		for (int i = 1; i <= size; i++) {
			base.waitForElement(getAssignmentData(assignmentName, i));
			driver.waitForPageToBeReady();
			data = getAssignmentData(assignmentName, i).getText();
			System.out.println(data);

			base.passedStep("Assignment " + availableListofElements.get(i - 1) + " : " + data);

		}

	}

	/**
	 * @authorSowndarya.velraj
	 * @Description : To verify Reviewer progress data
	 */
	public void verifyReviewerProgressData(String[] fullName) {

		// Collection list for table header
		base.waitForElementCollection(getReviewerProgressTableName());
		List<String> availableListofElements = base.availableListofElements(getReviewerProgressTableName());

		// Column header List size
		int size = getReviewerProgressTableName().size();
		System.out.println(size);

		// Get index for the respective header
		int indexNo = base.getIndex(getReviewerProgressTableName(), "NAME");
		System.out.println(indexNo);

		// Get the collection list of available users
		List<String> availableListofUsers = base.availableListofElements(getReviewerProgressNameList(indexNo));

		base.printListString(availableListofUsers);

		base.compareArraywithDataList(fullName, availableListofUsers, true, "Only the assigned Reviewers are present",
				"Additional reviewers are displayed apart from the assigned Reviewers");

		// Data iteration and verification
		for (int j = 0; j <= availableListofUsers.size() - 1; j++) {
			base.stepInfo("For : " + availableListofUsers.get(j));
			for (int i = 1; i <= size; i++) {
				driver.waitForPageToBeReady();
				String exactData = getReviewerProgressNameListRespectiveDetails(fullName[j], i).getText();
				if (!exactData.isEmpty()) {
					base.passedStep(availableListofElements.get(i - 1) + " : " + exactData);
				} else {
					base.failedStep(availableListofElements.get(i - 1) + " - is empty");
				}
			}
		}
	}

	/**
	 * @authorSowndarya.velraj
	 * @Description : To check image is in center
	 */
	public String checkIMageAtCenter() {
		String jsQuery = "return document.querySelector(\"#taskbasic > tbody >tr:first-child > td > div.col-md-12\").style.getPropertyValue('left')";
		System.out.println(jsQuery);
		String fitSize = driver.findAttributeValueViaJS(jsQuery);
		System.out.println(fitSize);
		return fitSize;
	}

	/**
	 * @throws AWTException
	 * @authorSowndarya.velraj
	 * @Description : To customize Reviewer Progress Widget
	 */
	public void select4Reviewers_ReviewerProgressWidget(String[] reviewers, String assignmentName) throws AWTException {

		driver.waitForPageToBeReady();
		reviewerProgress_gearIcon().waitAndClick(10);
		base.stepInfo("Select Reviewers");

		Robot r = new Robot();
		base.waitTime(2);
		selectReviewers_selectReviewers().ScrollTo();
		selectReviewers_selectReviewers().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(selectSpecificReviewer());
		selectSpecificReviewer().ScrollTo();

		selectSpecificReviewer().selectFromDropdown().selectByVisibleText(reviewers[0]);
		for (int i = 1; i < reviewers.length; i++) {
			r.keyPress(KeyEvent.VK_CONTROL);
			selectSpecificReviewer().selectFromDropdown().selectByVisibleText(reviewers[i]);
			r.keyRelease(KeyEvent.VK_CONTROL);
			base.waitTime(5);
		}

		base.stepInfo("selecting assignment");
		arrow_selectAssignment().waitAndClick(10);
		base.waitForElement(selectAssignment_CustomizeWidget(assignmentName));
		selectAssignment_CustomizeWidget(assignmentName).waitAndClick(10);
		base.waitForElement(btnSave_customizeWidget());
		btnSave_customizeWidget().waitAndClick(10);
	}

	/**
	 * @throws AWTException
	 * @authorSowndarya.velraj
	 * @Description : To customize Reviewer Progress Widget
	 */
	public void select4Reviewers_reviewerProductivityWidget(String[] reviewers, String assignmentName)
			throws AWTException {

		driver.waitForPageToBeReady();
		reviewerProductivity_gearIcon().waitAndClick(10);
		base.stepInfo("Select Reviewers");

		base.waitForElement(getSelectReviewersChkbox());
		getSelectReviewersChkbox().waitAndClick(10);
		Robot r = new Robot();

		base.waitForElement(getSelectReviewersRadioBtn());
		getSelectReviewersRadioBtn().waitAndClick(10);

		base.waitForElement(selectSpecificReviewer_reviewProductivity());
		selectSpecificReviewer_reviewProductivity().selectFromDropdown().selectByVisibleText(reviewers[0]);

		for (int i = 1; i < reviewers.length; i++) {
			r.keyPress(KeyEvent.VK_CONTROL);
			selectSpecificReviewer_reviewProductivity().selectFromDropdown().selectByVisibleText(reviewers[i]);
			r.keyRelease(KeyEvent.VK_CONTROL);
		}

		base.waitForElement(btnSave_reviewProductivity());
		btnSave_reviewProductivity().waitAndClick(10);
	}

	/**
	 * @Author Jeevitha
	 * @Description : Click wheel button & verify Buttons displayed. Also if we
	 *              click add new widget then verifies the popup dispalyed
	 * @param ClickAddNew
	 */
	public void ClickWheelIconAndVerify(boolean ClickAddNew) {
		dashboardWidgetIcon().waitAndClick(10);
		base.stepInfo("Clicked : Wheel Icon");

		driver.waitForPageToBeReady();
		if (btnAddNewWidget().isElementAvailable(3) && cancelDeleteWidgetIcon().isElementAvailable(2)
				&& saveWidgetIcon().isElementAvailable(1)) {
			base.stepInfo("Three buttons is Displayed as Add New Widget, Add Button and Cancel Button.");
		} else {
			base.failedStep("Three buttons are not Displayed As expected");
		}

		if (ClickAddNew) {
			btnAddNewWidget().waitAndClick(10);
			base.stepInfo("Clicked : Add new Widget Button");

			driver.waitForPageToBeReady();
			if (widgetLibraryPopup().isElementAvailable(5)) {
				base.stepInfo("Widget Library Popup is Displayed");
			} else {
				base.failedStep("Widget Library Popup is Not Displayed");
			}
		}
	}

	/**
	 * @Author Jeevitha
	 * @Description : on click on widgets present in Popup verify preview popup is
	 *              Displayed
	 * @param ClickAddNew
	 */
	public void clickWidgetAndVerifyPopUp(String widgetName, boolean close, boolean addToDashBoard) {
		if (widgetLibraryPopup().isElementAvailable(5)) {
			driver.waitForPageToBeReady();
			List<String> widgetsNameList = base.availableListofElements(widgetsPresentInPopup());
			System.out.println("Widgets Present in Popup is: " + widgetsNameList);

			for (int i = 0; i < widgetsNameList.size(); i++) {
				String actualWidget;

				if (widgetName.equals("")) {
					driver.waitForPageToBeReady();
					actualWidget = widgetsNameList.get(i);
					selectWidget(actualWidget).waitAndClick(10);
					base.stepInfo("Clicked Widget From Popup Is : " + widgetsNameList.get(i));
				} else {
					actualWidget = widgetName;
					selectWidget(widgetName).waitAndClick(10);
					base.stepInfo("Clicked Widget From Popup Is : " + widgetName);
				}

				driver.waitForPageToBeReady();
				if (widgetPopupHeader().isElementAvailable(5)) {
					String popupHeader = widgetPopupHeader().getText();
					base.compareTextViaContains(popupHeader, actualWidget, actualWidget + " Popup is displayed",
							"Displayed popup is not as expected");

					if (close) {
						getPopupCloseBtn().waitAndClick(10);
						base.stepInfo("Clicked : Close Btn");
					}
					if (addToDashBoard) {
						driver.scrollingToBottomofAPage();
						btnAddToDashboard().waitAndClick(10);
						base.stepInfo(actualWidget + " : Widget is added");
					}
				} else {
					base.failedStep("Popup is not displayed");
				}
			}
		}
	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To verify dashboard page
	 */
	public void verifyDashboardPage() {

		driver.waitForPageToBeReady();
		base.waitForElement(getAssignmentHeaderInDashboard());
		if (getAssignmentHeaderInDashboard().isElementAvailable(5)) {
			base.passedStep("User is on Dashboard home page");
		} else {
			base.failedStep("User is not in Dashboard home page");
		}

	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To click on assignment panel tile
	 */
	public void clickonAssignmentPanelTile() {

		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getAssignmentHeaderInDashboard());
			getAssignmentHeaderInDashboard().waitAndClick(5);

			if (getAssignmentPageTitleInAssignmentPage().isElementAvailable(5)) {
				base.passedStep("Assignments page is displayed successfully");
			} else {
				base.failedStep("Assignments is not displayed");
			}
		} catch (Exception e) {
			base.failedStep("Assignments is not displayed" + Get.class);
		}
	}

}
