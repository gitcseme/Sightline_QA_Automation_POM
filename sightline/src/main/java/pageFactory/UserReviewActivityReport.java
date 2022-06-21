package pageFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

import javax.management.ListenerNotFoundException;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import com.fasterxml.jackson.databind.deser.Deserializers.Base;
import com.sun.org.apache.bcel.internal.util.BCELComparator;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import testScriptsSmoke.Input;

public class UserReviewActivityReport {

	Driver driver;
	BaseClass base;

	public Element getReports_UserReviewActivity() {
		return driver.FindElementByXPath("//span[text()='OTHER']/parent::div/parent::div//a[@href='/Review/UserActivity']");
	}


	public UserReviewActivityReport(Driver driver) {

		this.driver = driver;
		base = new BaseClass(driver);

		// This initElements method will create all WebElements
		// PageFactory.initElements(driver.getWebDriver(), this);

	}
	/**
	 * @Author : Iyappan.Kasinathan
	 * @Description : This method navigates from reports landing page to user review activity report page. 
	 */
	public void navigateToUserReviewActivityReport() {
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getReports_UserReviewActivity());
		getReports_UserReviewActivity().Click();
		base.stepInfo("User review activity report is clicked");
		driver.waitForPageToBeReady();
		base.waitForElement(base.getPageTitle());
		String reportName = base.getPageTitle().getText();
		if(reportName.contains("Report: User Activity")) {
			base.passedStep("Navigated to user activity report page successfully");
		}else {
			base.failedStep("Page is not navigated to user activity report page ");
		}
	}
}