package pageFactory;

import java.util.concurrent.Callable;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;

public class Categorization {

	Driver driver;
	BaseClass base;
	ReportsPage reportPage;
	SoftAssert softAssertion;

	public Element getSelectIdentifyByTags() {
		return driver.FindElementByPartialLinkText("Identify by Tag");
	}

	public Element getSelectTag(String tagName) {
		return driver.FindElementByXPath("//div[@id='tags']//a[@data-content='" + tagName + "']");
	}

	public Element getSelectFolderName(String folderName) {
		return driver.FindElementByXPath("//div[@id='divTree']//a[@data-content='" + folderName + "']");
	}

	public Element getGotoStep2() {
		return driver.FindElementByXPath("//button[@id='SelectDocs']");
	}

	public Element getAnalyzeSelectFolders() {
		return driver.FindElementByLinkText("Analyze Select Folders");
	}

	public Element getFolderSelectionPopUp() {
		return driver.FindElementByXPath("//button[@id='btnFolder']");
	}

	public Element getSelectFolder(String folderName) {
		return driver.FindElementByXPath("//div[@id='divTree']//a[@data-content='" + folderName + "']");
	}

	public Element getSelectBtn() {
		return driver.FindElementByXPath("//button[@id='btnSelect']");
	}

	public Element getRun() {
		return driver.FindElementByXPath("//button[@id='btnRun']");
	}

	public Element getPopupYesBtn() {
		return driver.FindElementByXPath("//button[@id='btnYes']");
	}

	public Element getPopupNoBtn() {
		return driver.FindElementByXPath("//button[@id='btnNo']");
	}

	public Element getResults() {
		return driver.FindElementByXPath("//*[@id='divConfiguration']/div[2]/div[2]/div[1]");
	}

	public ElementCollection getTree() {
		return driver.FindElementsByXPath("//a[@class='jstree-anchor'][contains(text(),'')]");
	}

	public ElementCollection getElements() {
		return driver.FindElementsByXPath("//*[@class='a-menu']");
	}

	public ElementCollection getCatFolderTree() {
		return driver.FindElementsByXPath("(//div[@class='panel-body'])[6]//a");
	}

	// Added by Raghuram
	public Element getSelectTraingSet(String type) {
		return driver.FindElementByXPath("//a[text()=' " + type + " ']");
	}

	public Element getGoToStepTwoBtn() {
		return driver.FindElementByXPath("//button[@id='SelectDocs']");
	}

	public Element getSavedSearchBtn() {
		return driver.FindElementByXPath("//button[@id='btnSavedSearch']");
	}

	public Element getNode(String nodeName) {
		return driver.FindElementByXPath(
				"//div[@class='well no-padding prod']//a[@class='jstree-anchor' and text()='My Saved Search']//..//ul//a[text()='"
						+ nodeName + "']");
	}

	public Element getSelectSavedSearchPopupCloseBtn() {
		return driver.FindElementByXPath("//button[@class='ui-dialog-titlebar-close']");
	}

	public Element getCategorizeMenu() {
		return driver.FindElementByXPath("//a[@title='Categorize']");
	}

	// Added by Jayanthi
	public Element getBullHornIcon() {
		return driver.FindElementByXPath("//i[@class='fa fa-bullhorn']");
	}

	public Element getBullHornIcon_CC() {
		return driver.FindElementByXPath("//i[@class='fa fa-bullhorn']//following::b[1]");
	}

	public Element getBullHornIcon_NotificationMsg() {
		return driver.FindElementByXPath(
				"(//div[@id='bgTask']//a[contains(text(),' Your CATEGORIZATION with Notification Id ')])[1]");
	}

	public Element getCohesionResult() {
		return driver.FindElementByXPath("//div[@class='cohesion']");
	}

	public Element getSetCohesionLevel() {
		return driver.FindElementByXPath("//span[@class='irs-single']");
	}

	public Element getViewInDoclist() {
		return driver.FindElementById("btnGotoDocList");
	}

	public Element getDocListWarningPopUp() {
		return driver.FindElementByXPath("//div[@class='MessageBoxMiddle']");
	}

	// Added By Jeevitha
	public Element selectionTable() {
		return driver.FindElementByXPath("//div[@class='panel-body']");
	}
	
	public Element getViewAllBtn() {
		return driver.FindElementByXPath("//div[contains(@style,'block')]//button[@id='btnViewAll']");
	}

	public ElementCollection getBGTaskHeader() {
		return driver.FindElementsByXPath("(//thead//tr)[1]//th");
	}

	public Element getCategoreIdLinkInBG(int i) {
		return driver.FindElementByXPath("(//td[text()='Categorization']//..//td//a)[ " + i + "]");
	}

	public Element getSGCheckbox(String sgName) {
		return driver.FindElementByXPath("//div[@class='tagselector']//label[normalize-space()='" + sgName + "']");
	}

	public Element getSelectedCorpusToAnalyze(String analyzesdName) {
		return driver.FindElementByXPath("//div[@class='bootstrap-tagsinput']//span[text()='" + analyzesdName + "']");
	}

	public Element getDocCount() {
		return driver.FindElementByXPath("//div[@class='proview-result']//div[@class='docs']");
	}

	public Categorization(Driver driver) {

		this.driver = driver;
		this.driver.getWebDriver().get(Input.url + "Proview/Proview");
		base = new BaseClass(driver);
		softAssertion = new SoftAssert();

		// reportPage = new ReportsPage(driver);
		driver.waitForPageToBeReady();
		// This initElements method will create all WebElements
		// PageFactory.initElements(driver.getWebDriver(), this);

	}

	public void selectTagInCat(String tagName) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTree().Visible();
			}
		}), Input.wait30);
		System.out.println(getTree().FindWebElements().size());
		for (WebElement iterable_element : getTree().FindWebElements()) {
			// System.out.println(iterable_element.getText());
			if (iterable_element.getText().contains(tagName)) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
				driver.scrollingToBottomofAPage();
				// System.out.println(iterable_element.getText());
				iterable_element.click();
			}
		}

	}

	public int runCatWithTagsAndFolders(final String tagName, final String folderName) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectIdentifyByTags().Visible();
			}
		}), Input.wait30);

		getSelectIdentifyByTags().Click();
		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectTag(tagName).Exists();
			}
		}), Input.wait30);
		/* getSelectTag(tagName).ScrollTo(); */

		selectTagInCat(tagName);
		driver.scrollingToBottomofAPage();

		getGotoStep2().Click();

		getAnalyzeSelectFolders().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderSelectionPopUp().Visible();
			}
		}), Input.wait30);
		getFolderSelectionPopUp().Click();

		// iterate and select the folder
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCatFolderTree().Visible();
			}
		}), Input.wait30);
		System.out.println(getCatFolderTree().FindWebElements().size());
		for (WebElement iterable_element : getCatFolderTree().FindWebElements()) {
			// System.out.println(iterable_element.getText());
			if (iterable_element.getText().contains(folderName)) {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
				driver.scrollingToBottomofAPage();

				iterable_element.click();
			}
		}

		getSelectBtn().Click();

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		getRun().Click();
		// driver.scrollingToBottomofAPage();
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getPopupYesBtn().Visible() || getResults().getText() != null;
				}
			}), Input.wait30);
			getPopupYesBtn().Click();

		} catch (Exception e) {
			// TODO: handle exception
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getResults().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);

		return Integer.parseInt(getResults().getText());

	}

	/**
	 * @author Raghuram Date : 9/03/21 Description: Choice selection
	 * @throws InterruptedException
	 */
	public void selectTrainingSet(String type) {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getSelectTraingSet(type));
			getSelectTraingSet(type).waitAndClick(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Raghuram Date : 9/03/21 Description:categorization Testcase Flow
	 * @throws InterruptedException
	 */
	public void categorizationFlow(String newNodeFromPA, String newNodeFromRMU, String newNodeFromRev,
			String searchName, String searchName1, String currentRole) throws InterruptedException {
		getCategorizeMenu().Click();
		base.stepInfo("categorization Verification as : " + currentRole);
		selectTrainingSet("Identify by Saved Search");
		base.stepInfo("Identify by Saved Search Result  as : " + currentRole);

		checkList(newNodeFromPA, newNodeFromRMU, newNodeFromRev, searchName, searchName1, "RMU");

		selectTrainingSet("Identify by Saved Search");
		getGoToStepTwoBtn().waitAndClick(5);
		selectTrainingSet("Analyze Select Saved Search Results Sets");
		base.stepInfo("Analyze Select Saved Search Results Sets Expanded");
		driver.waitForPageToBeReady();
		base.waitForElement(getSavedSearchBtn());
		try {
			getSavedSearchBtn().waitAndClick(5);
			base.stepInfo("Clicked Saved Search Button");
			System.out.println("Clicked Saved Search Button");
			driver.waitForPageToBeReady();
		} catch (Exception e) {
			driver.getWebDriver().navigate().refresh();
			System.out.println("Exception handled");
			// selectTrainingSet("Identify by Saved Search");
			selectTrainingSet("Identify by Saved Search");
			driver.scrollingToBottomofAPage();
			getGoToStepTwoBtn().waitAndClick(5);
			selectTrainingSet("Analyze Select Saved Search Results Sets");
			driver.waitForPageToBeReady();
			base.waitForElement(getSavedSearchBtn());
			getSavedSearchBtn().waitAndClick(5);

		} finally {
			base.stepInfo("Analyze Select Saved Search Results Sets Result  as : " + currentRole);
			checkList(newNodeFromPA, newNodeFromRMU, newNodeFromRev, searchName, searchName1, currentRole);
//			reportPage.checkList(newNodeFromPA, newNodeFromRMU, newNodeFromRev, searchName, searchName1, currentRole);

			getSelectSavedSearchPopupCloseBtn().waitAndClick(5);
		}
	}

	/**
	 * @author Raghuram Date : 9/03/21 Description:Method for verifying created and
	 *         search nodes
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
	 * @author Raghuram Date : 9/03/21 Description: Method for verifying created and
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
	 * @author Jayanthi.ganesan @Modified By Jeevitha
	 * @param tagName
	 * @param folderName
	 * @throws InterruptedException
	 */
	public void CategorizationFunctionalityVerification(String tagNameORSG, String folderName, String select)
			throws InterruptedException {
		String bullHornValue = getBullHornIcon_CC().getText();
		int valueBeforeAnalysis = Integer.parseInt(bullHornValue);
		System.out.println(valueBeforeAnalysis);

		if (select.equalsIgnoreCase("Tag")) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSelectIdentifyByTags().Visible();
				}
			}), Input.wait30);

			getSelectIdentifyByTags().Click();
			driver.scrollingToBottomofAPage();

			Actions action = new Actions(driver.driver);
			action.moveToElement(selectionTable().getWebElement()).build().perform();
			base.waitTime(4);

//		getSelectTag(tagName).ScrollTo();
			getSelectTag(tagNameORSG).Click();
//		 selectTagInCat(tagName);

		} else if (select.equalsIgnoreCase("SG")) {
			selectTrainingSet("Identify by Security Group");
			driver.scrollingToBottomofAPage();
			getSGCheckbox(tagNameORSG).waitAndClick(5);
		}
		driver.scrollingToBottomofAPage();

		getGotoStep2().Click();
		getAnalyzeSelectFolders().ScrollTo();
		getAnalyzeSelectFolders().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderSelectionPopUp().Visible();
			}
		}), Input.wait30);
		getFolderSelectionPopUp().Click();
		base.waitTime(3);
		System.out.println(getCatFolderTree().FindWebElements().size());
		for (WebElement iterable_element : getCatFolderTree().FindWebElements()) {
			System.out.println(iterable_element.getText());
			System.out.println(folderName);
			if (iterable_element.getText().contains(folderName)) {
				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
				driver.scrollingToBottomofAPage();
				iterable_element.click();
				break;
			}
		}
		getSelectBtn().Click();

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		getRun().Click();
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getPopupNoBtn().Visible() || getResults().getText() != null;
				}
			}), Input.wait30);
			getPopupNoBtn().Click();

		} catch (Exception e) {
			// TODO: handle exception
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getResults().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);

		String bullHornValue2 = getBullHornIcon_CC().getText();
		int valueAfterAnalysis = Integer.parseInt(bullHornValue2);
		System.out.println(valueAfterAnalysis);
		if (valueAfterAnalysis > valueBeforeAnalysis) {
			// verify Bull Horn Icon
			softAssertion.assertEquals(valueAfterAnalysis, valueBeforeAnalysis);
			base.passedStep("Bull horn icon has New Notification");
			// verify color of bullhorn
			String color2 = getBullHornIcon().GetCssValue("color");
			color2 = base.rgbTohexaConvertor(color2);
			System.out.println(color2);
			if (color2.equalsIgnoreCase(Input.bullHornIconColor)) {
				System.out.println("Notification is [Red color] : " + color2);
				base.stepInfo("Notification is [Red color] : " + color2);
			} else {
				base.failedMessage("Color Doesnot Match");
			}
			base.waitForElement(getBullHornIcon());
			getBullHornIcon().waitAndClick(10);
			String id = getBullHornIcon_NotificationMsg().GetAttribute("id");
			System.out.println(id);
			getBullHornIcon_NotificationMsg().Click();
			driver.waitForPageToBeReady();
			base.waitTime(1);
			System.out.println(getResults().getText());
			System.out.println(getCohesionResult().getText());
			System.out.println(getSetCohesionLevel().getText());
			softAssertion.assertEquals(getCohesionResult().getText(), getSetCohesionLevel().getText());

		}
	}

	/**
	 * @author Jayanthi.ganesan
	 */

	public void ViewInDocLIst() {
		getViewInDoclist().Click();
		if (getDocListWarningPopUp().isElementAvailable(2)) {
			base.getYesBtn();
		} else {
			base.stepInfo("PopUp Not Appeared");
		}

	}

	/**
	 * @Author Jeevitha
	 * @Description :Navigation Categorization Page
	 */
	public void navigateToCategorizePage() {
		driver.getWebDriver().get(Input.url + "Proview/Proview");
		driver.waitForPageToBeReady();
		String currentUrl = driver.getWebDriver().getCurrentUrl();
		softAssertion.assertEquals(Input.url + "Proview/Proview", currentUrl);
		base.stepInfo("Landed on Categorization Page : " + currentUrl);
	}

	/**
	 * @Author Jeevitha
	 * @Description : verify Categorization id as Link from BackGroundPage
	 */
	public void backGroundTaskPageToCategorize() {
		if (getViewAllBtn().isElementAvailable(3)) {
			getViewAllBtn().waitAndClick(3);
		} else {
			base.waitForElement(getBullHornIcon());
			getBullHornIcon().waitAndClick(10);

			base.waitForElement(getViewAllBtn());
			getViewAllBtn().waitAndClick(3);
		}

		// verify Background Task page
		driver.waitForPageToBeReady();
		String url = driver.getUrl();
		String expURL = "https://sightlinept.consilio.com/Background/BackgroundTask";
		softAssertion.assertEquals(expURL, url);
		base.stepInfo("Navigated to My Backgroud Task Page.");

		int indexValue = base.getIndex(getBGTaskHeader(), "ID/NAME");
		System.out.println(indexValue);

		base.waitForElement(getCategoreIdLinkInBG(indexValue));
		String IdValue = getCategoreIdLinkInBG(indexValue).getText();
		String actualURL = getCategoreIdLinkInBG(indexValue).GetAttribute("href");

		System.out.println("ID Value is  : " + IdValue);
		base.stepInfo("ID/NAME value is  : " + IdValue + " With the link : " + actualURL);

		getCategoreIdLinkInBG(indexValue).waitAndClick(5);
		driver.waitForPageToBeReady();
		String currentUrl = driver.getUrl();
		softAssertion.assertEquals(actualURL, currentUrl);
		base.passedStep("Navigated Back to categorization Page : " + currentUrl);

	}
}