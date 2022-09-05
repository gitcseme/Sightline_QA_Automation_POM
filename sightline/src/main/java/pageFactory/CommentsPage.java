package pageFactory;

import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.Color;
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
	
	public ElementCollection totalRows(){ return driver.FindElementsByXPath("//table[@id='KeywordsDatatable']//tbody//tr"); }
    public Element getNextButton(){ return driver.FindElementByXPath("//a[text()='Next']/parent::li"); }
    public Element getNextButtonEle(){ return driver.FindElementByXPath("//a[text()='Next']"); }

	public Element getPopupNoBtn() {
		return driver.FindElementByXPath("//button[@id='bot2-Msg1']");
	}

	public Element getTotCommentsTable() {
		return driver.FindElementByXPath("//div[@id='CommentsTable_info']");
	}

	public Element getCommentsTableFieldValue(String fieldName) {
		return driver.FindElementByXPath("//*[@id='CommentsTable']//td[text()='" + fieldName + "']");
	}

	// Added by Vijaya.Rani
	public Element getCommentsTableErrorMsg() {
		return driver.FindElementByXPath("//span[@id='CommentLabel-error']");
	}

	public Element getEditCommentLabel() {
		return driver.FindElementByXPath("//input[@id='CommentLabel']");
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

	public String addComments(String CommentName) {

		driver.waitForPageToBeReady();
		base.waitForElement(getAddCommentsBtn());
		getAddCommentsBtn().Click();
		base.waitForElement(getCommentName());
		getCommentName().SendKeys(CommentName);
		String comment=getCommentName().GetAttribute("value");
		getSaveBtn().Click();

		base.VerifySuccessMessage("Comment Field added successfully");
		Reporter.log("Comment '" + CommentName + "' added successfully", true);
		base.CloseSuccessMsgpopup();
		return comment;
	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To validate comments Table
	 * @param commentsName
	 */
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

	/**
	 * @author Mohan.Venugopal
	 * @description: To navigate to Comment Page
	 * @param commentsName
	 */
	public void navigateToCommentsPage() {
		try {
			driver.getWebDriver().get(Input.url + "Comments/CommentsList");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Navigaing to Annotations Layer page is failed" + e.getLocalizedMessage());
		}
	}

	
	/**
	 * @author Mohan.Venugopal
	 * @description: To verify Comment Page
	 * @param commentsName
	 */
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

	/**
	 * @author Vijaya.Rani ModifyDate:01/07/2022
	 * @Description Add comments With Error Msg
	 */
	public void AddCommentsWithErrorMsg(String ComentName) {

		this.driver.getWebDriver().get(Input.url + "Comments/CommentsList");
		getAddCommentsBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCommentName().Visible();
			}
		}), Input.wait30);
		getCommentName().SendKeys(ComentName);
		getSaveBtn().Click();
		driver.waitForPageToBeReady();
		base.waitForElement(getCommentsTableErrorMsg());
		getCommentsTableErrorMsg().isElementAvailable(10);
		String errorMsg = getCommentsTableErrorMsg().getText();
		base.stepInfo(errorMsg);
		System.out.println(errorMsg);
	}

	/**
	 * @author Vijaya.Rani ModifyDate:01/07/2022
	 * @Description Add comments And Edit
	 */
	public void EditCommentsIsDisabled() {

		this.driver.getWebDriver().get(Input.url + "Comments/CommentsList");
		driver.waitForPageToBeReady();
		base.waitForElement(getEditCommentButton());
		getEditCommentButton().waitAndClick(5);

		driver.waitForPageToBeReady();
		String color = driver.FindElement(By.xpath("//input[@id='CommentLabel']")).GetCssValue("background-color");
		System.out.println(color);
		String ExpectedColor = Color.fromString(color).asHex();
		System.out.println(ExpectedColor);
		String ActualColor = "#eeeeee";
		if (ActualColor.equals(ExpectedColor)) {
			base.passedStep("EditComments are not Editable,it is disabled");
		} else {
			base.failedStep("Edit Comments is Editable");
		}

	}

	/**
	 * @author Mohan.Venugopal #description: To check Comment name is present in the
	 *         table or not
	 * @param commentsName
	 */
	public void validateCommentNameIsPresentOrNot(String commentsName) {

		driver.waitForPageToBeReady();
		int rowCount = totalRows().FindWebElements().size();
       		for(int i=0;i<rowCount;i++) {
       			
       			if (getCommentsTableFieldValue(commentsName).isElementAvailable(5)) {
       				base.passedStep(
       						"Comment are added under the security group of the logged in RMU user and success message is displayed successfully ");

       		        break;
       			}
       			String getNextButtonAtt = getNextButton().GetAttribute("class");
       			if((i==rowCount-1)&&!(getNextButtonAtt.contains("disabled"))) {
       				driver.scrollingToBottomofAPage();
       				driver.waitForPageToBeReady();
       				getNextButtonEle().isElementAvailable(8);
       				getNextButtonEle().Click();
       				driver.waitForPageToBeReady();
       				rowCount = totalRows().FindWebElements().size();
       				i=-1;
       			}else {
       				base.failedStep("Comments are not added under the security group of the logged in RMU user");
       			}

			
		} 
	}

	/**
	 * @author Mohan.Venugopal #description: To delete Comment name and click on
	 *         cancel button
	 * @param commentsName
	 */
	public void deleteCommentsAndClickOnCancelButton(String commentsName) {

		driver.waitForPageToBeReady();
		FindComment(commentsName);
		base.waitForElement(getDeleteCommentButton(commentsName));
		getDeleteCommentButton(commentsName).Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPopupNoBtn().Visible();
			}
		}), Input.wait30);
		getPopupNoBtn().Click();

		if (getCommentsTableFieldValue(commentsName).isDisplayed()) {
			base.passedStep("Comment is not deleted successfully");
		} else {
			base.failedStep("Delete fuction is not working fine");
		}

	}

}
