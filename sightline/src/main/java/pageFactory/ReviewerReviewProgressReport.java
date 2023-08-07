package pageFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.Callable;

import javax.management.ListenerNotFoundException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;
//import com.sun.org.apache.bcel.internal.util.BCELComparator;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import testScriptsSmoke.Input;

public class ReviewerReviewProgressReport {

	Driver driver;
	BaseClass base;
	SoftAssert softAssertion; 

	public Element getReports_ReviewerReviewProgressReport() {
		return driver.FindElementByXPath("//span[text()='REVIEW']/parent::div/parent::div//a[@href='/Review/ReviewerProgressReport']");
	}
	
	public Element getAssignmentGroupExpandIcon() {
		return driver.FindElementByXPath("//a[@id='assignmentGroupID']");
	}
	public Element getReviewerExpandIcon() {
		return driver.FindElementByXPath("//a[@href='#collapseDivReviewer']");
	}
	public Element selectReviewerCheckBox(String reviewer) {
		return driver.FindElementByXPath("//span[text()='"+reviewer+"']");
	}
	public Element selectAssignmentGroupCheckBox(String assignmentGrpName) {
		return driver.FindElementByXPath("//a[text()='"+assignmentGrpName+"']/i");
	}
	public Element applyChangesBtn() {
		return driver.FindElementByXPath("//button[@id='btn_applychanges']");
	}
	public Element titleHeader() {
		return driver.FindElementByXPath("//h2[@class='page-title header']");
	}
	public ElementCollection reviewerColumnNameHeader() {
		return driver.FindElementsByXPath("//th[@aria-controls='reviewerProgress_ID']");
	}
	public Element reviewerColumnNameValue(String reviewer,int index) {
		return driver.FindElementByXPath("(//td[text()='"+reviewer+"']/parent::tr/td)["+index+"]");
	}


	public ReviewerReviewProgressReport(Driver driver) {

		this.driver = driver;
		base = new BaseClass(driver);
		softAssertion = new SoftAssert();
		
		// This initElements method will create all WebElements
		// PageFactory.initElements(driver.getWebDriver(), this);

	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method navigate to reviewer review progress report from reports page
	 */
	public void navigateToReviewerReviewProgressReport() {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getReports_ReviewerReviewProgressReport());
		getReports_ReviewerReviewProgressReport().Click();
		driver.waitForPageToBeReady();
		base.waitForElement(base.getPageTitle());
		String reportName = base.getPageTitle().getText();
		if(reportName.contains("Reviewer Review Progress")) {
			base.passedStep("Navigated to reviewer review progress report page successfully");
		}else {
			base.failedStep("Page is not navigated to reviewer review progress report page ");
		}
		
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method select reviewer in the popup
	 */
	
	public void selectReviewer(String reviewer) {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getReviewerExpandIcon());
		getReviewerExpandIcon().Click();
		base.stepInfo("Reviewer popup is expanded");
		base.waitTillElemetToBeClickable(selectReviewerCheckBox(reviewer));
		selectReviewerCheckBox(reviewer).waitAndClick(10);
		base.stepInfo("Reviewer: "+reviewer+" is selected");
		base.waitTillElemetToBeClickable(getReviewerExpandIcon());
		getReviewerExpandIcon().Click();
		base.stepInfo("Reviewer popup is collapsed");		
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method select assignment group in the popup
	 */
	public void selectAssignmentGroup(String agnmtGrp) {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getAssignmentGroupExpandIcon());
		getAssignmentGroupExpandIcon().Click();
		base.stepInfo("Assignment group popup is expanded");
		base.waitTillElemetToBeClickable(selectAssignmentGroupCheckBox(agnmtGrp));
		selectAssignmentGroupCheckBox(agnmtGrp).waitAndClick(10);
		base.stepInfo("Assignment group of "+agnmtGrp+" is selected");
		base.waitTillElemetToBeClickable(getAssignmentGroupExpandIcon());
		getAssignmentGroupExpandIcon().Click();
		base.stepInfo("Assignment group popup is collapsed");		
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method applied the changes and generate the report
	 */
	public void applyChanges() {
		base.waitTillElemetToBeClickable(applyChangesBtn());
		applyChangesBtn().Click();
		base.stepInfo("Changes applied successfully");
	}


	/**
	 * Modified by sowndarya
	 * @author Iyappan.Kasinathan
	 * @description This method generates the assignment review progress report
	 */
	public void generateRRPreport(String agnmtGrp,String reviewer) {
		driver.waitForPageToBeReady();
		selectReviewer(reviewer);
		driver.waitForPageToBeReady();
		selectAssignmentGroup(agnmtGrp);
		driver.waitForPageToBeReady();
		applyChanges();	
		base.waitForElement(titleHeader());
		String title = titleHeader().getText();
		if(title.contains("Reviewer Progress Summary")) {
			base.passedStep("Report generated for the reviewer "+reviewer+" and the assignment group "+agnmtGrp+" is successfully");
		}else {
			base.failedStep("Report not generated as expected");
		}
		
	}
	
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method returns the value of the column.
	 */
	public String getColoumnValue(ElementCollection coloumnHeader, String textValue, String agnmtName ) {
		int indexValue =base.getIndex(coloumnHeader, textValue);
		driver.waitForPageToBeReady();
		String value = reviewerColumnNameValue(agnmtName,indexValue).getText();
		return value;	
		
	}
}