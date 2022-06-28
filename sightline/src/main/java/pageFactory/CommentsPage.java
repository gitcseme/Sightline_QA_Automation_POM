package pageFactory;

import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Reporter;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class CommentsPage {

	Driver driver;
	BaseClass base;

	public Element getAddCommentsBtn() {
		return driver.FindElementById("btnAddComment");
	}

	public Element getCommentName() {
		return driver.FindElementById("CommentLabel");
	}

	public Element getEditCommentButton() {
		return driver.FindElementByXPath("//table[@id='CommentsTable']//tr[1]//a[contains(text(),'Edit')]");
	}

	public Element getDeleteCommentButton(String commentname) {
		return driver.FindElementByXPath(
				"//table[@id='CommentsTable']//tr[contains(.,'" + commentname + "')]//a[contains(text(),'Delete')]");
	}

	public Element getPopupYesBtn() {
		return driver.FindElementByXPath("//button[@id='bot1-Msg1']");
	}

	public Element getSaveBtn() {
		return driver.FindElementById("btnSave");
	}

	public Element getSuccessMsgHeader() {
		return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span");
	}

	public Element getSuccessMsg() {
		return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p");
	}

	// select coding's radio button from table
	public Element getCommentlistTable() {
		return driver.FindElementByXPath("//*[@id='CommentsTable']");
	}

	public ElementCollection getCommentnames() {
		return driver.FindElementsByXPath("//*[@id='CommentsTable']/tbody/tr/td[1]");
	}

	public Element getCommentname(String commentname) {
		return driver.FindElementByXPath("//table[@id='CommentsTable']//tr[contains(.,'" + commentname + "')]");
	}

	// Added by Mohan
	public Element getTotCommentsTable() {
		return driver.FindElementByXPath("//div[@id='CommentsTable_info']");
	}

	public CommentsPage(Driver driver) {

		this.driver = driver;
		base = new BaseClass(driver);
		this.driver.getWebDriver().get(Input.url + "Comments/CommentsList");
	}

	public void AddComments(String ComentName) {

		this.driver.getWebDriver().get(Input.url + "Comments/CommentsList");
		getAddCommentsBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCommentName().Visible();
			}
		}), Input.wait30);
		getCommentName().SendKeys(ComentName);
		getSaveBtn().Click();

		base.VerifySuccessMessage("Comment Field added successfully");
		Reporter.log("Comment '" + ComentName + "' added successfully", true);
		base.CloseSuccessMsgpopup();
	}

	public void DeleteComments(String ComentName) {

		FindComment(ComentName);
		getDeleteCommentButton(ComentName).Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPopupYesBtn().Visible();
			}
		}), Input.wait30);
		getPopupYesBtn().Click();
		base.VerifySuccessMessage("Comment Field deleted successfully.");
		base.CloseSuccessMsgpopup();

	}

	public boolean FindComment(final String Commentname) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCommentlistTable().Visible();
			}
		}), Input.wait30);

		boolean nextPage = true;
		boolean found = false;
		System.out.println(getCommentnames().size());
		UtilityLog.info(getCommentnames().size());
		while (nextPage) {
			int row = 1;

			for (WebElement ele : getCommentnames().FindWebElements()) {
				System.out.println(ele.getText().trim());
				UtilityLog.info(ele.getText().trim());
				if (ele.getText().trim().equals(Commentname)) {
					nextPage = false;
					found = true;
					// System.out.println(row);
					getCommentname(Commentname).waitAndClick(10);
					System.out.println(Commentname + " is selected");
					UtilityLog.info(Commentname + " is selected");
					return true;

				}

				row++;

			}
			try {
				driver.scrollingToBottomofAPage();
				driver.getWebDriver().findElement(By.xpath("//li[@class='paginate_button next disabled']/a"))
						.isDisplayed();
				nextPage = false;
				// System.out.println("Not found!!!!!!");
			} catch (Exception e) {
				driver.getWebDriver().findElement(By.linkText("Next")).click();
			}

		}
		return false;

	}

	public void addComments(String CommentName) {

		driver.waitForPageToBeReady();
		base.waitForElement(getAddCommentsBtn());
		getAddCommentsBtn().Click();
		base.waitForElement(getCommentName());
		getCommentName().SendKeys(CommentName);
		getSaveBtn().Click();

		base.VerifySuccessMessage("Comment Field added successfully");
		Reporter.log("Comment '" + CommentName + "' added successfully", true);
		base.CloseSuccessMsgpopup();
	}

	public void validateCommentsTable() {

		driver.waitForPageToBeReady();
		base.waitTime(2);
		String totCommentsTable = getTotCommentsTable().getText();
		System.out.println(totCommentsTable);

		if (totCommentsTable.contains("0") && totCommentsTable.contains("3 entries")) {

			base.waitForElement(getAddCommentsBtn());
			getAddCommentsBtn().waitAndClick(5);
			addComments("CommentsCloning");
			addComments("CommentsCloning01");
			addComments("CommentsCloning02");
			addComments("CommentsCloning03");

			base.passedStep(
					" There are more than 2 comments  in each security Groups(1-1 in Security Group) which are exists in source template project.");

		} else if (totCommentsTable.contains("Showing 1 to ")) {
			base.passedStep(
					" There are more than 2 comments  in each security Groups(1-1 in Security Group) which are exists in source template project.");

		} else {
			base.failedStep("There are no Comments in this project");
		}
	}

	public void navigateToCommentsPage() {
		try {
			driver.getWebDriver().get(Input.url + "Comments/CommentsList");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Navigaing to Annotations Layer page is failed" + e.getLocalizedMessage());
		}
	}

	public void verifyCommentsPage() {

		driver.waitForPageToBeReady();
		String totCommentsTable = getTotCommentsTable().getText();
		System.out.println(totCommentsTable);

		if (totCommentsTable.contains("Showing 1 to")) {
			base.passedStep(
					" There are more than 2 comments  in each security Groups(1-1 in Security Group) which are exists in source template project.");

		} else {
			base.failedStep("There are no Comments in this project");
		}

	}
}
