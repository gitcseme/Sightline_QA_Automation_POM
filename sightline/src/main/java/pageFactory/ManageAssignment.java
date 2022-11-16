package pageFactory;

import java.util.concurrent.Callable;
import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class ManageAssignment {
	Driver driver;
	public static int pureHit;
	BaseClass base;
	DocViewMetaDataPage docView;

	public Element getAssignment(int rowNum) {
		return driver.FindElementByXPath("//table[@id='GridAssignment']//tr[" + String.valueOf(rowNum) + "]//td[1]");
	}

	public Element getAssignmentDropdownButton() {
		return driver.FindElementByXPath("//button[@id='btnAction']//..//button[2]");
	}

	public Element getEditAssignmentOption() {
		return driver.FindElementByXPath("//a[text()='Edit Assignment']");
	}

	public Element getEditAssignmentHeader() {
		return driver.FindElementByXPath("//h1[text()='Edit Assignment']");
	}

	public Element getDisplayFoldertabDoogle() {
		return driver.FindElementByXPath("//input[@id='AdditionalPreferences_IsShowFoldersTab']/following-sibling::i");
	}

	public ElementCollection getWarningMsg() {
		return driver.FindElementsByXPath(
				"//p[text()='Please select an assignment which contains documents assigned to it.']");
	}

	public Element getSaveButton() {
		return driver.FindElementByXPath("//input[@value='Save']");
	}

	public Element getBackToManageButton() {
		return driver.FindElementByXPath("//a[@class='btn btn-primary save-btn']");
	}

	public Element getViewAllDocViewOption() {
		return driver.FindElementByXPath("//a[text()='View All Docs In DocView']");
	}

	public Element getWarningMessageCloseButton() {
		return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//i[@class='botClose fa fa-times']");
	}

	public Element getDisplayHistoryFolderDoogle() {
		return driver.FindElementByXPath(
				"//input[@id='AdditionalPreferences_IsExposeDocumentHistory']/following-sibling::i");
	}

	public Element getMetaDataPanelToogle() {
		return driver
				.FindElementByXPath("//input[@id='AdditionalPreferences_IsAllowMetadataView']/following-sibling::i");
	}

	// Added by Gopinath - 11/10/2021
	public Element getRedactionsToogle() {
		return driver
				.FindElementByXPath("//input[@id='AdditionalPreferences_IsEnableRedactions']/following-sibling::i");
	}

	public Element getSaveButtonToogle() {
		return driver.FindElementByXPath(
				"//input[@id='AdditionalPreferences_IsAllowSaveWithoutCompletion']/following-sibling::i");
	}
	
	//Added by Gopinath - 03/01/2022
	public Element getPrintToogle() {
		return driver
				.FindElementByXPath("//input[@id='AdditionalPreferences_IsAllowPDFPrinting']/following-sibling::i");
	}
	public Element getNativeDownloadToogle() {
		return driver
				.FindElementByXPath("//input[@id='AdditionalPreferences_IsAllowNativeDownloads']/following-sibling::i");
	}
	
	//Added by Gopinath - 04/01/2022
	public Element getRemarksToogle() {
		return driver
				.FindElementByXPath("//input[@id='AdditionalPreferences_IsAllowReviwerRemarks']/following-sibling::i");
	}
	
	//Added by Gopinath - 18/01/2022
	public Element getShowDefaultTabToogle() {
		return driver.FindElementByXPath("//input[@id='AdditionalPreferences_IsShowDefaultViewTab']/following-sibling::i");
	}

	public ManageAssignment(Driver driver) {

		this.driver = driver;
		base = new BaseClass(driver);
//		this.driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		docView = new DocViewMetaDataPage(driver);
	}

	/**
	 * @author Gopinath Method for editing an Assignment
	 * @param rowNum - (rowNum is integer value that is a row number.)
	 */
	public void editAssignment(int rowNum) {
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAssignment(rowNum).Enabled() && getAssignment(rowNum).isDisplayed();
				}
			}), Input.wait90);
			getAssignment(rowNum).isElementAvailable(15);
			getAssignment(rowNum).isDisplayed();
			getAssignment(rowNum).Click();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAssignmentDropdownButton().Enabled() && getAssignmentDropdownButton().isDisplayed();
				}
			}), Input.wait90);
			getAssignmentDropdownButton().isElementAvailable(15);
			getEditAssignmentOption().isElementAvailable(15);
			docView.selectValueFromDropDown(getAssignmentDropdownButton(), getEditAssignmentOption());
			driver.waitForPageToBeReady();
			getEditAssignmentHeader().isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getEditAssignmentHeader().isDisplayed();
				}
			}), Input.wait90);
			getEditAssignmentHeader();

		} catch (Exception e) {
			UtilityLog.info("Failed to click on assignment");
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath Method to enable or disable Display Folder
	 * @param flag - (flag is boolean value that weather folder need to disable or
	 *             not.)
	 */
	public void disableDisplayFolder(boolean flag) {
		try {
			driver.waitForPageToBeReady();
			
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDisplayFoldertabDoogle().isDisplayed();
				}
			}), Input.wait90);
			getDisplayFoldertabDoogle().isElementAvailable(15);
			String value = getDisplayFoldertabDoogle().GetAttribute("class").toLowerCase().trim();

			if (value.contains("true") && flag == true) {
				getDisplayFoldertabDoogle().isElementAvailable(15);
				getDisplayFoldertabDoogle().isDisplayed();
				getDisplayFoldertabDoogle().Click();
				base.passedStep("Display folder is disabled successfully in Edit Assignment page");
			} else if (flag == false && !(value.contains("true"))) {
				getDisplayFoldertabDoogle().isElementAvailable(15);
				getDisplayFoldertabDoogle().isDisplayed();
				getDisplayFoldertabDoogle().Click();
				base.passedStep("Display folder is enabled successfully in Edit Assignment page");
			} else if (flag == true) {
				base.passedStep("Display folder is already disabled in Edit Assignment page");
			} else if (flag == false) {
				base.passedStep("Display folder is already enabled in Edit Assignment page");
			}

			driver.scrollPageToTop();
			getSaveButton().isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSaveButton().isDisplayed() && getSaveButton().Enabled();
				}
			}), Input.wait90);
			getSaveButton().isDisplayed();
			getSaveButton().Click();
			base.getSuccessMsg().isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return base.getSuccessMsg().isDisplayed() && base.getSuccessMsg().Enabled();
				}
			}), Input.wait90);
			Assert.assertEquals("Success message is not displayed", true,
					base.getSuccessMsg().getWebElement().isDisplayed());
			if (base.getSuccessMsg().getWebElement().isDisplayed()) {
				base.passedStep("Success message is displayed successfully");
			}
			driver.scrollPageToTop();

		} catch (Exception e) {
			UtilityLog.info("Display folder is enabling failed");
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath Method to get doc view displayed assignment row number
	 * @return rowNum - (rowNum doc view displayed assignment row number.)
	 */
	public int getDocViewRowNum() {
		int rowNum = 0;
		try {
			driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
			driver.waitForPageToBeReady();
			Thread.sleep(Input.wait30/10);
			for (int i = 1; i < 9; i++) {
				getAssignment(i).isElementAvailable(15);
				getAssignment(i).isDisplayed();
				getAssignment(i).Click();
				getAssignmentDropdownButton().isElementAvailable(15);
				getViewAllDocViewOption().isElementAvailable(15);
				docView.selectValueFromDropDown(getAssignmentDropdownButton(), getViewAllDocViewOption());
				if (getWarningMsg().size() == 0) {
					rowNum = i;
					driver.waitForPageToBeReady();
					driver.getWebDriver().navigate().back();
					break;

				} else {
					getWarningMessageCloseButton().Click();
				}
			}
		} catch (Exception e) {
			UtilityLog.info("Geting row number of assignment in manage assignment table is failed");
			e.printStackTrace();
		}
		return rowNum;
	}

	/**
	 * @author Gopinath Method for navigate To Doc View
	 * @param rowNum - (rowNum doc view displayed assignment row number.)
	 */
	public void navigateToDocView(int rowNum) {
		try {
			driver.waitForPageToBeReady();
			getAssignmentDropdownButton().isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAssignmentDropdownButton().isDisplayed() && getAssignmentDropdownButton().Enabled();
				}
			}), Input.wait90);
			getAssignmentDropdownButton().isDisplayed();
			driver.waitForPageToBeReady();
			base.waitForElement(getAssignmentDropdownButton());
			base.waitTillElemetToBeClickable(getAssignmentDropdownButton());
			docView.selectValueFromDropDown(getAssignmentDropdownButton(), getViewAllDocViewOption());
		} catch (Exception e) {
			UtilityLog.info("Display folder is enabling failed");
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath Method for selecting Row assignments table
	 * @param rowNum - (rowNum doc view displayed assignment row number.)
	 */
	public void selectRow(int rowNumber) {
		try {
			driver.waitForPageToBeReady();
			getAssignment(rowNumber).isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAssignment(rowNumber).isDisplayed() && getAssignment(rowNumber).Enabled();
				}
			}), Input.wait90);
			getAssignment(rowNumber).isDisplayed();
			getAssignment(rowNumber).Click();
		} catch (Exception e) {
			UtilityLog.info("Display folder is enabling failed");
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath Method for disabling History Folder
	 * @param flag - (flag is boolean value for enabling and disabling history)
	 */
	public void disableHistoryFolder(boolean flag) {
		try {
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDisplayHistoryFolderDoogle().isDisplayed() && getDisplayHistoryFolderDoogle().Enabled();
				}
			}), Input.wait90);
			getDisplayHistoryFolderDoogle().isElementAvailable(15);
			getDisplayHistoryFolderDoogle().isDisplayed();
			String value = getDisplayHistoryFolderDoogle().GetAttribute("class").toLowerCase().trim();

			if (value.contains("true") && flag == true) {
				getDisplayHistoryFolderDoogle().isDisplayed();
				getDisplayHistoryFolderDoogle().Click();
				base.passedStep("Display History is disabled successfully in Edit Assignment page");
			} else if (flag == false && !(value.contains("true"))) {
				getDisplayHistoryFolderDoogle().isDisplayed();
				getDisplayHistoryFolderDoogle().Click();
				base.passedStep("Display History is enabled successfully in Edit Assignment page");
			} else if (flag == true) {
				base.passedStep("Display History is already disabled in Edit Assignment page");
			} else if (flag == false) {
				base.passedStep("Display History is already enabled in Edit Assignment page");
			}

			driver.scrollPageToTop();

			getSaveButton().isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSaveButton().isDisplayed() && getSaveButton().Enabled();
				}
			}), Input.wait90);
			getSaveButton().isDisplayed();

			getSaveButton().Click();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return base.getSuccessMsg().isDisplayed() && base.getSuccessMsg().Enabled();
				}
			}), Input.wait90);

			Assert.assertEquals("Success message is not displayed", true,
					base.getSuccessMsg().getWebElement().isDisplayed());
			if (base.getSuccessMsg().getWebElement().isDisplayed()) {
				base.passedStep("Success message is displayed successfully");
			}
			driver.scrollPageToTop();

		} catch (Exception e) {
			UtilityLog.info("Display History is enabling failed");
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath Method to enable or disable Meta data tab panel..
	 * @param flag - (flag is boolean value that weather folder need to disable or
	 *             not.)
	 */
	public void disableMetaTabPanel(boolean flag) {
		try {
			driver.waitForPageToBeReady();
			getMetaDataPanelToogle().isElementAvailable(15);
			base.waitForElement(getMetaDataPanelToogle());
			String value = getMetaDataPanelToogle().GetAttribute("class").toLowerCase().trim();
			
			if (value.contains("true") && flag == true) {
				getMetaDataPanelToogle().isDisplayed();
				getMetaDataPanelToogle().Click();
				base.passedStep("Meta data panel toogle is disabled successfully in Edit Assignment page");
			} else if (flag == false && !(value.contains("true"))) {
				getMetaDataPanelToogle().isDisplayed();
				getMetaDataPanelToogle().Click();
				base.passedStep("Meta data panel toogle is enabled successfully in Edit Assignment page");
			} else if (flag == true) {
				base.passedStep("Meta data panel toogle is already disabled in Edit Assignment page");
			} else if (flag == false) {
				base.passedStep("Meta data panel toogle is already enabled in Edit Assignment page");
			}

			driver.scrollPageToTop();
			getSaveButton().isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSaveButton().isDisplayed() && getSaveButton().Enabled();
				}
			}), Input.wait90);
			getSaveButton().isDisplayed();
			getSaveButton().Click();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return base.getSuccessMsg().isDisplayed() && base.getSuccessMsg().Enabled();
				}
			}), Input.wait90);
			Assert.assertEquals("Success message is not displayed", true,
					base.getSuccessMsg().getWebElement().isDisplayed());
			if (base.getSuccessMsg().getWebElement().isDisplayed()) {
				base.passedStep("Success message is displayed successfully");
			}
			driver.scrollPageToTop();

		} catch (Exception e) {
			UtilityLog.info("Display folder is enabling failed");
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath Method to enable or disable Redactions opeation..
	 * @param flag - (flag is boolean value that weather radations button need to
	 *             disable or not.)
	 */
	public void disableRedactionsButton(boolean flag) {
		try {
			driver.waitForPageToBeReady();
			getRedactionsToogle().isElementAvailable(15);
			base.waitForElement(getRedactionsToogle());
			String value = getRedactionsToogle().GetAttribute("class").toLowerCase().trim();

			if (value.contains("true") && flag == true) {
				getRedactionsToogle().isDisplayed();
				getRedactionsToogle().Click();
				base.passedStep("Redactions toogle is disabled successfully in Edit Assignment page");
			} else if (flag == false && !(value.contains("true"))) {
				getRedactionsToogle().isDisplayed();
				getRedactionsToogle().Click();
				base.passedStep("Redactions toogle is enabled successfully in Edit Assignment page");
			} else if (flag == true) {
				base.passedStep("Redactions toogle is already disabled in Edit Assignment page");
			} else if (flag == false) {
				base.passedStep("Redactions toogle is already enabled in Edit Assignment page");
			}

			driver.scrollPageToTop();
			getSaveButton().isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSaveButton().isDisplayed() && getSaveButton().Enabled();
				}
			}), Input.wait90);
			getSaveButton().isDisplayed();
			getSaveButton().Click();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return base.getSuccessMsg().isDisplayed() && base.getSuccessMsg().Enabled();
				}
			}), Input.wait90);
			Assert.assertEquals("Success message is not displayed", true,
					base.getSuccessMsg().getWebElement().isDisplayed());
			if (base.getSuccessMsg().getWebElement().isDisplayed()) {
				base.passedStep("Success message is displayed successfully");
			}
			driver.scrollPageToTop();

		} catch (Exception e) {
			UtilityLog.info("Redations toogle is opearations failed");
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath Method to enable or disable save button opeation..
	 * @param flag - (flag is boolean value that weather save button need to disable
	 *             or not.)
	 */
	public void disableSaveButtonToogle(boolean flag) {
		try {
			driver.Navigate().refresh();
			driver.waitForPageToBeReady();
			getRedactionsToogle().isElementAvailable(15);
			base.waitForElement(getRedactionsToogle());
			String value = getSaveButtonToogle().GetAttribute("class").toLowerCase().trim();

			if (value.contains("true") && flag == true) {
				getSaveButtonToogle().isDisplayed();
				getSaveButtonToogle().Click();
				base.passedStep("Save toogle is disabled successfully in Edit Assignment page");
			} else if (flag == false && !(value.contains("true"))) {
				getSaveButtonToogle().isDisplayed();
				getSaveButtonToogle().Click();
				base.passedStep("Save toogle is enabled successfully in Edit Assignment page");
			} else if (flag == true) {
				base.passedStep("Save toogle is already disabled in Edit Assignment page");
			} else if (flag == false) {
				base.passedStep("Save toogle is already enabled in Edit Assignment page");
			}

			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			getSaveButton().isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSaveButton().isDisplayed() && getSaveButton().Enabled();
				}
			}), Input.wait90);
			getSaveButton().isDisplayed();
			getSaveButton().Click();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return base.getSuccessMsg().isDisplayed() && base.getSuccessMsg().Enabled();
				}
			}), Input.wait90);
			Assert.assertEquals("Success message is not displayed", true,
					base.getSuccessMsg().getWebElement().isDisplayed());
			if (base.getSuccessMsg().getWebElement().isDisplayed()) {
				base.passedStep("Success message is displayed successfully");
			}
			driver.scrollPageToTop();

		} catch (Exception e) {
			UtilityLog.info("Redations toogle is opearations failed");
			e.printStackTrace();
		}
	}
	
	/**
	 * @author Gopinath
	 * @Description : Method for navigating to assignments page.
	 */
	public void navigateToAssignmentsPageURL() {
		try {
			driver.getWebDriver().get(Input.url + "Assignment/ManageAssignment");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while navigating to assignments page is failed" + e.getMessage());
		}
	}
	
	/**
	 * @author Gopinath Method to enable or disable print button..
	 * @param flag - (flag is boolean value that weather folder need to disable or
	 *             not.)
	 */
	public void disablePrintButton(boolean flag) {
		try {
			driver.waitForPageToBeReady();
			getPrintToogle().isElementAvailable(15);
			base.waitForElement(getPrintToogle());
			String value = getPrintToogle().GetAttribute("class").toLowerCase().trim();
			
			if (value.contains("true") && flag == true) {
				getPrintToogle().isDisplayed();
				getPrintToogle().Click();
				base.passedStep("Allow reviewers to print docs to PDF toogle is disabled successfully in Edit Assignment page");
			} else if (flag == false && !(value.contains("true"))) {
				getPrintToogle().isDisplayed();
				getPrintToogle().Click();
				base.passedStep("Allow reviewers to print docs to PDFl toogle is enabled successfully in Edit Assignment page");
			} else if (flag == true) {
				base.passedStep("Allow reviewers to print docs to PDF toogle is already disabled in Edit Assignment page");
			} else if (flag == false) {
				base.passedStep("Allow reviewers to print docs to PDF toogle is already enabled in Edit Assignment page");
			}

			driver.scrollPageToTop();
			getSaveButton().isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSaveButton().isDisplayed() && getSaveButton().Enabled();
				}
			}), Input.wait90);
			getSaveButton().isDisplayed();
			getSaveButton().Click();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return base.getSuccessMsg().isDisplayed() && base.getSuccessMsg().Enabled();
				}
			}), Input.wait90);
			Assert.assertEquals("Success message is not displayed", true,
					base.getSuccessMsg().getWebElement().isDisplayed());
			if (base.getSuccessMsg().getWebElement().isDisplayed()) {
				base.passedStep("Success message is displayed successfully");
			}
			driver.scrollPageToTop();

		} catch (Exception e) {
			UtilityLog.info("Display folder is enabling failed");
			e.printStackTrace();
		}
	}
	
	
	
	/**
	 * @author Gopinath Method to enable or disable download button..
	 * @param flag - (flag is boolean value that weather folder need to disable or
	 *             not.)
	 */
	public void disableNativeDownloadButton(boolean flag) {
		try {
			driver.waitForPageToBeReady();
			getNativeDownloadToogle().isElementAvailable(15);
			base.waitForElement(getNativeDownloadToogle());
			String value = getNativeDownloadToogle().GetAttribute("class").toLowerCase().trim();
			
			if (value.contains("true") && flag == true) {
				getNativeDownloadToogle().isDisplayed();
				getNativeDownloadToogle().Click();
				base.passedStep("Allow reviewers to download natives toogle is disabled successfully in Edit Assignment page");
			} else if (flag == false && !(value.contains("true"))) {
				getNativeDownloadToogle().isDisplayed();
				getNativeDownloadToogle().Click();
				base.passedStep("Allow reviewers to download natives toogle is enabled successfully in Edit Assignment page");
			} else if (flag == true) {
				base.passedStep("Allow reviewers to download natives toogle is already disabled in Edit Assignment page");
			} else if (flag == false) {
				base.passedStep("Allow reviewers to download natives toogle is already enabled in Edit Assignment page");
			}

			driver.scrollPageToTop();
			getSaveButton().isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSaveButton().isDisplayed() && getSaveButton().Enabled();
				}
			}), Input.wait90);
			getSaveButton().isDisplayed();
			getSaveButton().Click();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return base.getSuccessMsg().isDisplayed() && base.getSuccessMsg().Enabled();
				}
			}), Input.wait90);
			Assert.assertEquals("Success message is not displayed", true,
					base.getSuccessMsg().getWebElement().isDisplayed());
			if (base.getSuccessMsg().getWebElement().isDisplayed()) {
				base.passedStep("Success message is displayed successfully");
			}
			driver.scrollPageToTop();

		} catch (Exception e) {
			UtilityLog.info("Display folder is enabling failed");
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath Method to enable or disable reviewer remarks button..
	 * @param flag - (flag is boolean value that weather folder need to disable or
	 *             not.)
	 */
	public void disableReviewerRemarks(boolean flag) {
		try {
			driver.waitForPageToBeReady();
			getRemarksToogle().isElementAvailable(15);
			base.waitForElement(getRemarksToogle());
			String value = getRemarksToogle().GetAttribute("class").toLowerCase().trim();
			
			if (value.contains("true") && flag == true) {
				getRemarksToogle().isDisplayed();
				getRemarksToogle().Click();
				base.passedStep("Allow reviewers to edit Reviewer Remarkss toogle is disabled successfully in Edit Assignment page");
			} else if (flag == false && !(value.contains("true"))) {
				getRemarksToogle().isDisplayed();
				getRemarksToogle().Click();
				base.passedStep("Allow reviewers to edit Reviewer Remarks toogle is enabled successfully in Edit Assignment page");
			} else if (flag == true) {
				base.passedStep("Allow reviewers to edit Reviewer Remarks toogle is already disabled in Edit Assignment page");
			} else if (flag == false) {
				base.passedStep("Allow reviewers to edit Reviewer Remarks toogle is already enabled in Edit Assignment page");
			}

			driver.scrollPageToTop();
			getSaveButton().isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSaveButton().isDisplayed() && getSaveButton().Enabled();
				}
			}), Input.wait90);
			getSaveButton().isDisplayed();
			getSaveButton().Click();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return base.getSuccessMsg().isDisplayed() && base.getSuccessMsg().Enabled();
				}
			}), Input.wait90);
			Assert.assertEquals("Success message is not displayed", true,
					base.getSuccessMsg().getWebElement().isDisplayed());
			if (base.getSuccessMsg().getWebElement().isDisplayed()) {
				base.passedStep("Success message is displayed successfully");
			}
			driver.scrollPageToTop();

		} catch (Exception e) {
			UtilityLog.info("Display folder is enabling failed");
			e.printStackTrace();
		}
	}
	
	/**
	 * @author Gopinath Method to enable or disable default tab toogle..
	 * @param flag - (flag is boolean value that weather folder need to disable or
	 *             not.)
	 */
	public void disableDefaultTabToogle(boolean flag) {
		try {
			driver.waitForPageToBeReady();
			getShowDefaultTabToogle().isElementAvailable(15);
			base.waitForElement(getShowDefaultTabToogle());
			String value = getShowDefaultTabToogle().GetAttribute("class").toLowerCase().trim();
			
			if (value.contains("true") && flag == true) {
				getShowDefaultTabToogle().isDisplayed();
				getShowDefaultTabToogle().Click();
				base.passedStep("Show Default View Tab toogle is disabled successfully in Edit Assignment page");
			} else if (flag == false && !(value.contains("true"))) {
				getShowDefaultTabToogle().isDisplayed();
				getShowDefaultTabToogle().Click();
				base.passedStep("Show Default View Tab toogle is enabled successfully in Edit Assignment page");
			} else if (flag == true) {
				base.passedStep("Show Default View Tab toogle is already disabled in Edit Assignment page");
			} else if (flag == false) {
				base.passedStep("Show Default View Tab toogle is already enabled in Edit Assignment page");
			}

			driver.scrollPageToTop();
			getSaveButton().isElementAvailable(15);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSaveButton().isDisplayed() && getSaveButton().Enabled();
				}
			}), Input.wait90);
			getSaveButton().isDisplayed();
			getSaveButton().Click();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return base.getSuccessMsg().isDisplayed() && base.getSuccessMsg().Enabled();
				}
			}), Input.wait90);
			Assert.assertEquals("Success message is not displayed", true,
					base.getSuccessMsg().getWebElement().isDisplayed());
			if (base.getSuccessMsg().getWebElement().isDisplayed()) {
				base.passedStep("Success message is displayed successfully");
			}
			driver.scrollPageToTop();

		} catch (Exception e) {
			UtilityLog.info("Display folder is enabling failed");
			e.printStackTrace();
		}
	}
}
