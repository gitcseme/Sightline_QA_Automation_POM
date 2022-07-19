package pageFactory;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.internal.FindsByXPath;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
		return driver.FindElementByXPath("//select[@id='UserList']//option");
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
		return driver.FindElementByXPath("//strong[text()='" + assignmentName + "']//..//..//..//td["+ i +"]");
	}

	public ElementCollection getAssignmentsTableName() {
		return driver.FindElementsByXPath("//table[@id='dt_basic']//th");
	}
	
	
	public Element getCenterStyle() {
		return driver.FindElementByXPath("\\#taskbasic > tbody >tr:first-child > td > div.col-md-12\\).style.getPropertyValue('left')");
	}

	public ElementCollection getReviewerProgressTableName() {
		return driver.FindElementsByXPath("//table[@id='ReviewerTable']//th");
	}
	
	public ElementCollection getReviewerProgressData() {
		return driver.FindElementsByXPath("//table[@id='ReviewerTable']//tbody//tr");
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
		String data ;
		System.out.println(size);
		for (int i = 1; i <= size; i++) {
			base.waitForElement(getAssignmentData(assignmentName, i));
			driver.waitForPageToBeReady();
			data = getAssignmentData(assignmentName, i).getText();
			System.out.println(data);
			
			base.passedStep("Assignment " + availableListofElements.get(i-1) + " : " + data);

		}

	}
	
	/**
	 * @authorSowndarya.velraj
	 * @Description : To verify Reviewer progress data
	 */
	public void verifyReviewerProgressData(String assignmentName) {

		List<String> availableListofElements = base.availableListofElements(getReviewerProgressTableName());
		
		int size = getReviewerProgressTableName().size();
		System.out.println(size);
		for (int i = 1; i <= size; i++) {
			base.waitForElementCollection(getReviewerProgressData());
			String data = getReviewerProgressData().toString();
			driver.waitForPageToBeReady();
			System.out.println(data);
			
			base.passedStep("Reviewer Progress " + availableListofElements.get(i-1) + " : " + data);

		}

	}
	
	/**
	 * @authorSowndarya.velraj
	 * @Description : To check image is in center
	 */
	public String checkIMageAtCenter()
	{
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
	public void select4Reviewers_ReviewerProgressWidget( String user1,String user2,String user3,String user4, String assignmentName) throws AWTException {

		driver.waitForPageToBeReady();
		reviewerProgress_gearIcon().waitAndClick(10);
		base.stepInfo("Select Reviewers");
		
			selectReviewers_selectReviewers().waitAndClick(10);
			base.waitForElement(selectSpecificReviewer());
			selectSpecificReviewer().selectFromDropdown().selectByVisibleText(user1);
			Robot r= new Robot();
			r.keyPress(KeyEvent.VK_CONTROL);
			selectSpecificReviewer().selectFromDropdown().selectByVisibleText(user2);
			selectSpecificReviewer().selectFromDropdown().selectByVisibleText(user3);
			selectSpecificReviewer().selectFromDropdown().selectByVisibleText(user4);
			r.keyRelease(KeyEvent.VK_CONTROL);
		
		base.stepInfo("selecting assignment");
		arrow_selectAssignment().waitAndClick(10);
		base.waitForElement(selectAssignment_CustomizeWidget(assignmentName));
		selectAssignment_CustomizeWidget(assignmentName).waitAndClick(10);
		base.waitForElement(btnSave_customizeWidget());
		btnSave_customizeWidget().waitAndClick(10);
	}
}
