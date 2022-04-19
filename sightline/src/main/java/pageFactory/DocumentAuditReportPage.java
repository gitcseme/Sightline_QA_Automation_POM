package pageFactory;

import java.util.concurrent.Callable;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import testScriptsSmoke.Input;

public class DocumentAuditReportPage {

	Driver driver;
	BaseClass bc;
	SoftAssert softAssertion;

	public Element getReport_DocAudit() {
		return driver.FindElementByXPath("//a[contains(.,'Document Audit Report')]");
	}

	public Element getDA_Selectsearch() {
		return driver.FindElementByXPath("//a[contains(.,'My Saved Search')]");
	}

	public Element getApplyBtn() {
		return driver.FindElementById("btn_applychanges");
	}

	public Element getDA_Actions(int colno) {
		return driver.FindElementByXPath(".//*[@id='dtDocumentAuditData']//tbody/tr[1]/td[" + colno + "]");
	}

	public Element getDA_SelectSearch_Expand() {
		return driver.FindElementByXPath("//*[@href='#collapseSearchBy']");
	}

	public Element getDA_SelectActionstype() {
		return driver.FindElementByXPath("//*[@id='chkCheckAllActionTypes']/following-sibling::i");
	}

	public Element getDA_Actions_Expand() {
		return driver.FindElementByXPath("//*[@id='actiontypeAuditreport']/a[2]");
	}

	// Added By jeevitha
	public Element getDASearchNode(String searchNode) {
		return driver.FindElementByXPath("//a[text()='" + searchNode + "']//i");
	}

	public Element getwaitDialogue() {
		return driver.FindElementByXPath("//span[text()='Wait for Task to Complete?']");
	}

	public Element getwaitOkBtn() {
		return driver.FindElementByXPath("//button[@id='btnYes']");
	}

	public Element getDocAuditTable() {
		return driver.FindElementByXPath("//table[@id='dtDocumentAuditData']");
	}

	public Element getDocIDTextBox() {
		return driver.FindElementByXPath("//input[@id='txtDocID']");
	}

	public DocumentAuditReportPage(Driver driver) {

		this.driver = driver;
		bc = new BaseClass(driver);
		// this.driver.getWebDriver().get(Input.url+ "Report/ReportsLanding");
		softAssertion = new SoftAssert();

	}

	public void ValidateDAreport(String searchname, String assignmentname) throws InterruptedException {

		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getReport_DocAudit().Visible();
			}
		}), Input.wait30);
		getReport_DocAudit().Click();

		getDA_SelectSearch_Expand().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDA_Selectsearch().Visible();
			}
		}), Input.wait30);
		getDA_Selectsearch().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getApplyBtn().Visible();
			}
		}), Input.wait30);
		getApplyBtn().Click();
		Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDA_Actions(2).Visible();
			}
		}), Input.wait30);
		String actaction = getDA_Actions(2).getText();
		System.out.println(actaction);
		Assert.assertEquals("Assigned to Assignment", actaction);

		String actactionon = getDA_Actions(3).getText();
		System.out.println(actactionon);
		Assert.assertEquals(assignmentname, actactionon);

	}

	/**
	 * @author Jeevitha
	 * @param source
	 * @param source1
	 * @throws InterruptedException
	 * Modified on : 1/6/22
	 */
	public void verifySource(String source, String source1) throws InterruptedException {
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		driver.getWebDriver().get(Input.url + "Review/DocumentAudit");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDA_SelectSearch_Expand().Visible();
			}
		}), Input.wait30);
		getDA_SelectSearch_Expand().waitAndClick(10);

		if (getDASearchNode(source).isElementAvailable(3)) {
			getDASearchNode(source).waitAndClick(10);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDASearchNode(source1).Visible();
				}
			}), Input.wait30);
			getDASearchNode(source1).waitAndClick(10);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getApplyBtn().Visible();
				}
			}), Input.wait30);
			getApplyBtn().waitAndClick(10);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDocAuditTable().Visible();
				}
			}), Input.wait60);
			Thread.sleep(2000);

			if (getwaitDialogue().isElementPresent()) {
				getwaitOkBtn().waitAndClick(10);
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						getDocAuditTable().Visible();
						System.out.println("It is Displayed");
						return getDocAuditTable().Visible();
					}
				}), Input.wait120);
			} else if (getDocAuditTable().isElementPresent()) {
				System.out.println("It is Displayed");
			} else {

				System.out.println("It is not Displayed");
			}

		}
		bc.stepInfo("RMU and REV nodes not present in PA as Expected");
	}

	/**
	 * @author Jayanthi.ganesan
	 */
	public void navigateTODocAuditReportPage() {
		driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		String expectedURL = Input.url + "Review/DocumentAudit";
		bc.waitForElement(getReport_DocAudit());
		if (getReport_DocAudit().isDisplayed()) {
			bc.passedStep("Document Audit Report link is displayed in reports landing page");
			getReport_DocAudit().Click();
			driver.waitForPageToBeReady();
			softAssertion.assertEquals(expectedURL, driver.getUrl());
			softAssertion.assertAll();
			bc.passedStep("Sucessfully navigated to Document audit Report Page");
		} else {
			bc.failedStep("Document audit Report link is not  displayed in reports landing page");
		}
	}

	/**
	 * @author Jayanthi
	 * @throws InterruptedException
	 */
	public void verifyReportDisplay(String DocID) throws InterruptedException {
		bc.waitForElement(getDocIDTextBox());
		getDocIDTextBox().SendKeys(DocID);
		bc.stepInfo("Entered " + DocID + " as doc id value  in doc id text box");
		bc.waitTillElemetToBeClickable(getApplyBtn());
		getApplyBtn().waitAndClick(10);
		bc.waitForElement(getDocAuditTable());
		if (getDocAuditTable().isElementPresent()) {

			bc.passedStep("Document Audit report is Displayed");
		} else {

			bc.failedStep("Document Audit is not Displayed");
		}

	}

	public void validateDocumentAuditActionColumn(String expectedActionName) {
		bc.waitForElement(getDA_Actions(2));
		String actualActionName = getDA_Actions(2).getText();
		if (actualActionName.equals(expectedActionName)) {
			bc.passedStep("Different actions performed on the document  listed in the report");
		} else {
			bc.failedStep("Different actions performed on the document not listed in the report");
		}
	}

}