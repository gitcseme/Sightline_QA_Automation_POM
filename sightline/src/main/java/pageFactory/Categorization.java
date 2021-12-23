package pageFactory;

import java.util.concurrent.Callable;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;

public class Categorization {

	Driver driver;
	BaseClass base;
	ReportsPage reportPage;

	public Element getSelectIdentifyByTags() {
		return driver.FindElementByPartialLinkText("Identify by Tag");
	}

	public Element getSelectTag(String tagName) {
		return driver.FindElementByXPath("//div[@id='tags']//a[@data-content='" + tagName + "']");
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

	public Categorization(Driver driver) {

		this.driver = driver;
		this.driver.getWebDriver().get(Input.url + "Proview/Proview");
		base = new BaseClass(driver);
		reportPage = new ReportsPage(driver);
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

		reportPage.checkList(newNodeFromPA, newNodeFromRMU, newNodeFromRev, searchName, searchName1, "RMU");

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
		try {
			getNode(nodeName).Visible();
			System.out.println("Element Present");
			return true;
			// getNode(nodeName).Click();
		} catch (Exception e) {
			// e.printStackTrace();
			System.out.println("Element Not present");
			return false;
		}
	}

}