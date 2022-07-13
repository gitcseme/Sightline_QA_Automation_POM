package pageFactory;

import org.openqa.selenium.interactions.Actions;
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
    
    public Element dashboardWidgetIcon(){ return driver.FindElementByXPath("//a[@id='wEdit']"); }
    public Element btnAddNewWidget(){ return driver.FindElementByXPath("//input[@id='wAdd']"); }
    public Element selectWidget(String widget){ return driver.FindElementByXPath("//ul[@id='DataCollection']//span[contains(text(),'"+widget+"')]"); }
    public Element btnAddToDashboard(){ return driver.FindElementById("btnAddToDashboard"); }
    public Element warningPopup(){ return driver.FindElementByXPath("//div[@id='bigBoxColor2']//p"); }
    public Element btndeleteWidgetInDashboard(){ return driver.FindElementByXPath("(//div[@class='jarviswidget-ctrls']//a)[last()]"); }
    public Element btnYes_deleteWidget(){ return driver.FindElementByXPath("//div[@class='MessageBoxButtonSection']//button[contains(text(),'Yes')]"); }
    public Element btnNo_deleteWidget(){ return driver.FindElementByXPath("//div[@class='MessageBoxButtonSection']//button[contains(text(),'No')]"); }
    public Element reviewerProgress_gearIcon(){ return driver.FindElementByXPath("//span[contains(text(),'Reviewer Progress')]//..//i[@id='OpenPop']"); }
    public Element selectReviewers_mostToDoDocs(){ return driver.FindElementByXPath("//p[contains(text(),'Select Reviewers')]//..//label[contains(normalize-space(),'Most To Do Docs')]"); }
    public Element selectReviewers_mostCompleteDocs(){ return driver.FindElementByXPath("//p[contains(text(),'Select Reviewers')]//..//label[contains(normalize-space(),'Most Complete Docs')]");}
    public Element selectReviewers_selectReviewers(){ return driver.FindElementByXPath("//p[contains(text(),'Select Reviewers')]//..//label[contains(normalize-space(),'Select Specific Reviewers')]");}
  
    public Element btnSave_customizeWidget(){ return driver.FindElementByXPath("//button[@onclick='ReviewerProcessOpreation();']"); }
    public Element arrow_selectAssignment(){ return driver.FindElementByXPath("//i[@class='jstree-icon jstree-ocl']"); }
    public Element selectAssignment_CustomizeWidget(String assignment){ return driver.FindElementByXPath("//div[@id='treeAssignment-review-progress']//a[contains(text(),'"+assignment+"')]"); }
    public ElementCollection countOfWidget(){ return driver.FindElementsByXPath("//div[@class='wellHdr font-md']"); }
    public Element selectSpecificReviewer(){ return driver.FindElementByXPath("//select[@id='UserList']//option"); }
    public Element mostToDoDocs_InsideWidget(){ return driver.FindElementByXPath("//label[contains(text(),'Most To Do Docs')]"); }
    public Element mostCompletedDocs_InsideWidget(){ return driver.FindElementByXPath("//label[contains(text(),'Completed')]"); }
    public Element selectProjectName(String project){ return driver.FindElementByXPath("//strong[contains(text(),'"+project+"')]"); }
  
    
    public Dashboard(Driver driver){

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
		System.out.println("count of widget : "+count);
		if (count<3) {
			driver.Navigate().refresh();
			dashboardWidgetIcon().waitAndClick(10);
			btnAddNewWidget().waitAndClick(10);
			selectWidget(widget).waitAndClick(10);
			driver.scrollingToBottomofAPage();
			btnAddToDashboard().waitAndClick(10);
			
		}else if (count==3) {
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
	public void customizeReviewerProgressWidget(String radioBtn,String fullName,String assignmentName) {
		
		driver.waitForPageToBeReady();
		reviewerProgress_gearIcon().waitAndClick(10);
		base.stepInfo("Select Reviewers");
		if(radioBtn.equalsIgnoreCase("ToDoDocs")) {
			selectReviewers_mostToDoDocs().waitAndClick(10);
			System.out.println("selected - "+selectReviewers_mostToDoDocs().getText());
		}else if (radioBtn.equalsIgnoreCase("Complete")) {
		selectReviewers_mostCompleteDocs().waitAndClick(10);
		System.out.println("selected - "+selectReviewers_mostCompleteDocs().getText());
		}
		else {
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

}
