package pageFactory;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;

public class ReusableDocViewPage {

	Driver driver;
	BaseClass base;
	SessionSearch sessionPage;
	SoftAssert softAssertion;

	public Element getCompleteDocBtn() {return driver.FindElementById("btnDocumentComplete");}
	public Element getDocView_Mini_ActionButton() {return driver.FindElementById("btnAction");}
	public Element getDocView_NumTextBox() {return driver.FindElementById("txtBoxPageNum");}
	public Element getDocView_MiniDocListIds(int rowno) {return driver.FindElementByXPath(".//*[@id='SearchDataTable']/tbody/tr[" + rowno + "]");}
	public Element getDocView_EditMode() {return driver.FindElementByXPath("//a[@id='wEdit']");}
	public Element getDocView_HdrMinDoc() {return driver.FindElementByXPath(".//*[@id='HdrMiniDoc']/div/div/i");}
	public Element getDocView_HdrCoddingForm() {return driver.FindElementByXPath(".//*[@id='HdrCoddingForm']/div/div/i");}
	public Element getDocView_DefaultViewTab() {return driver.FindElementById("aliDocumentDefaultView");}
	public Element geDocView_MiniList_CodeSameAsIcon() {return driver.FindElementByXPath(".//*[@id='SearchDataTable']//i[@class='fa fa-link']");}
	public Element geDocView_FamilyMem_CodeSameAsIcon() {return driver.FindElementByXPath(".//*[@id='dtDocumentFamilyMembers']//i[@class='fa fa-link']");}
	public Element getDocView_ChildWindow_ActionButton() {return driver.FindElementById("btnAnalyticAction");}
	public Element getDocView_MiniDoc_ChildWindow_Selectdoc(int rowno) {return driver.FindElementByXPath("//*[@id='SearchDataTable']//tr[" + rowno + "]/td[1]/label");}
	public Element getDocView__ChildWindow_Mini_CodeSameAs() {return driver.FindElementById("liCodeSameAsMiniDocList");}
	public Element getDocView_HistoryButton() {return driver.FindElementById("btnDocHistory");}
	public Element getDocView_ConfigMinidoclist() {return driver.FindElementById("miniDocListConfig");}
	public ElementCollection getDocView_Config_Selectedfield() {return driver.FindElementsByXPath("//*[@id='sortable2PickColumns']/li");}
	public Element getDocView_MiniDocFields_ConfigSaveButton() {return driver.FindElementByXPath("//div[@class='ui-dialog-buttonset']//button[2]");}
	// Doc View Coding Form
	public Element getResponsiveCheked() {return driver.FindElementByXPath("//div[@id='item1']//div[@id='0_radiogroup']//div[1]//div[1]//label[1]//span[1]");}
	public Element getNonPrivilegeRadio() {return driver.FindElementByXPath("//input[@id='9_radio']//parent::label//span");}
	public Element getConfidentialRadio() {return driver.FindElementByXPath("//div[@id='item17']//div[@id='0_radiogroup']//div[1]//div[1]//label[1]//span[1]");}
	public Element getDocument_CommentsTextBox() {return driver.FindElementByXPath("//textarea[@id='1_textarea']");}
	public Element getReadOnlyTextBox(String projectFieldName) {return driver.FindElementByXPath("//input[@name='FIELD'][@projectfieldname='" + projectFieldName + "']");}
	//public Element getCodingFormSaveButton() {return driver.FindElementByXPath("//div[@id='divCodingFormSaveComplete']//child::a[@id='Save']");}
	public Element getCodingFormSaveButton() {return driver.FindElementByXPath("//div[@id='divCodingForms']//child::a[@id='Save']");}
//	Coding Stamp button
	public Element getCodingFormStampButton() {return driver.FindElementById("SaveUserStamps");}
//	Stamp Name text box
	public Element getCodingStampTextBox() {return driver.FindElementById("txtStampName");}
//	coding stamp editing mode text box
	public Element getCodingEditStampTextBox() {return driver.FindElementByXPath("//input[@id='txtStampNameEdit']");}
	public Element getDrp_CodingEditStampColour() {return driver.FindElementByXPath("//div//dl[@id='ddlEditStamps']");}
	public Element getVerifyPopUpColour() {return driver.FindElementByXPath("//div//dl[@id='ddlEditStamps']//dt//a");}
//	coding Stamp drop down
	public Element getDrp_StampColour() {return driver.FindElementById("stampSelect");}
//	coding stamp save button
	public Element getCodingStampSaveBtn() {return driver.FindElementByXPath("//div[@class='ui-dialog-buttonset']//button[text()='Save']");}
//	coding stamp save this form button
	public Element getCodingStampSaveThisFormBtn() {return driver.FindElementByXPath("//div[@class='ui-dialog-buttonset']//button[text()='Save This Form']");}
//	coding Stamp colour selection 
	public Element getAssignedColour(String colour) {return driver.FindElementByXPath("//dl[@id='stampSelect']//ul//li[@id='" + colour + "']");}
	public Element getEditAssignedColour(String editColour) {return driver.FindElementByXPath("//dl[@id='ddlEditStamps']//ul//li[@id='" + editColour + "']");}
//	pencil icon for last assigned colour 
//	modified by baskar
	public Element getCodingStampLastIcon(String icon) {return driver.FindElementByXPath("//ul[@id='UserStamps']//li[@id='" + icon + "']");}
	public Element getCodingLastIcon() {return driver.FindElementByXPath("//div[@id='divCodingStamp']//ul//li");}
	public Element getverifyCodeSameAsLast() {return driver.FindElementByXPath("//table[@id='SearchDataTable']//i[@class='fa fa-check-circle']");}
//	Gear icon for coding stamp pencil icon
//	Modified by baskar
	public Element getEditStampSettings() {return driver.FindElementByXPath("//a[@id='stampSettings']//i");}
	public Element getClickDocviewID(int row) {return driver.FindElementByXPath("//*[@id='SearchDataTable']//tr[" + row + "]/td[2]");}
	public Element getCodeSameAsLast() {return driver.FindElementByXPath("//div[@id='lastCodingStamp']//div//ul//li//i");}
	public Element getConfigureMiniDocListPopUP() {return driver.FindElementById("Configure Mini DocList");}
	public ElementCollection getMiniDocListHeaderValue() {return driver.FindElementsByXPath(
				"//div[@class='dataTables_scrollHeadInner']//table[contains(@class,'DTTT_selectable')]/thead/tr/th");}
	public Element getCentralPanelDispaly() {return driver.FindElementByXPath("(//span[@class='LoadImagePosition']//img[@id='imgLoadPM'])[1]");}
//	coding stamp popup window
	public Element getCodingStampPopup() {return driver.FindElementByXPath("(//div[contains(@class,'ui-dialog')])[1]");}
	public Element getCodingStampCancel() {return driver.FindElementByXPath("//div[@class='ui-dialog-buttonset']//button[text()='Cancel']");}
	public Element getDocumetId() {return driver.FindElementByXPath("//td[contains(text(),'ID00000')]");}
	public Element getDocumetListLoading() {return driver.FindElementByXPath("//div[@class='dataTables_processing dts_loading']");}
	public ElementCollection getDocumetCountMiniDocList() {return driver.FindElementsByXPath("//*[@id='SearchDataTable']//tr/td[1]/label");}
	public Element getselectDocFromClckIcon() {return driver.FindElementByXPath("//*[@id='ulDocViewHistory']/li");}
	public Element getDeletePopUpAssignedColour() {return driver.FindElementByXPath("//button[text()='Delete']");}
	public Element getHeader() {return driver.FindElementById("header");}
//	configure minidoclist
	public Element getReviewModeBtn() {return driver.FindElementByXPath("//i[@class='fa fa-gear']/parent::a");}
	public Element getToggleEnableConfiOn() {return driver.FindElementByXPath("//i[@data-swchon-text='ON']");}
	public Element getConfigSaveButton() {return driver.FindElementByXPath("//button[text()='Save']/parent::div[@class='ui-dialog-buttonset']");}
//  verify coding form checkbox
	public Element getVer_Responsive_ChckBox() {return driver.FindElementByXPath("//span[.='Responsive']//preceding-sibling::label//input[@checked='checked']");}
	public Element getVer_Not_Privileged_ChckBox() {return driver.FindElementByXPath("//span[.='Not_Privileged']//preceding-sibling::label//input[@checked='checked']");}
	public Element getSaveAndNextButton() {return driver.FindElementByXPath("//a[@id='SaveAndNext']");}
	public Element getDocListAllDocCheckBox() {return driver.FindElementByXPath("//div[@id='dtDocList_wrapper']//th/label/i");}
	public Element getYesButtonForAllDoc() {return driver.FindElementByXPath("(//input[@id='Yes'])[1]");}
	public Element getDocListOkBtn() {return driver.FindElementByXPath("//div[@class='MessageBoxButtonSection']//button[text()=' OK']");}
	public Element getDocList_Action_Drp_Dwn() {return driver.FindElementByXPath("//button[@id='idAction']");}
	public Element getDocListViewInDocView() {return driver.FindElementByXPath("//ul[@class='dropdown-menu action-dd']//li//a[@id='ViewInDocView']");}
	public Element getDocView_HdrAnalytics() {return driver.FindElementByXPath(".//*[@id='HdrAnalytics']/div/div/i");}
	public Element getViewDocumentNearDupe() {return driver.FindElementByXPath("//li[@id='liViewDocumentNearDupe']");}
	public Element getCodeSameAsNearDupe() {return driver.FindElementByXPath("//li[@id='liCodeSameAsNearDupe']");}
	public Element getChainVerifyInAnalDocs() {return driver.FindElementByXPath("//table[@id='dtDocumentNearDuplicates']//label//..//i[@class='fa fa-link']");}
	public ElementCollection getMiniDocListDocIdText() {return driver.FindElementsByXPath("//*[@id='SearchDataTable']//tr/td[2]");}
	public Element getMiniDocClick(String docId) {return driver.FindElementByXPath("//table[@id='SearchDataTable']//td[text()='"+docId+"']");}
	public ElementCollection getDocView_MiniListDocuments() {return driver.FindElementsByXPath("//div[@id='divMiniDocList']//tbody//tr");}
	public Element getDocView_Analytics_NearDupeTab() {return driver.FindElementById("liDocumentNearDupe");}
	public ElementCollection getAnalyticalPanelDocIdText() {return driver.FindElementsByXPath("//table[@id='dtDocumentNearDuplicates']/tbody/tr//td[2]");}
	public Element getAnalyCheckBox(String docid) {return driver.FindElementByXPath("//table[@id='dtDocumentNearDuplicates']//td[text()=' "+docid+"']//..//label[@class='checkbox']");}
	public Element getVerifyPrincipalDocument() {return driver.FindElementByXPath("//i[@class='fa fa-arrow-right']/parent::td//following-sibling::td[1]");}
	public Element getDocView__ChildWindow_Mini_RemoveCodeSameAs() {return driver.FindElementById("liRemoveCodeSameAsMiniDocList");}
	public Element getDocView_ImagesTab() {return driver.FindElementById("liDocumentProductionView");}
	public Element getDocView_TranslationTab() {return driver.FindElementById("liDocumentTranslationsView");}
	public Element getDocView_TextTab() {return driver.FindElementById("liDocumentTxtView");}
	public ElementCollection getCodingStampColourOption() {return driver.FindElementsByXPath("//ul[@id='ddlAvailableStamps']/li");}
	public ElementCollection getDefaultColourOption() {return driver.FindElementsByXPath("//ul[@id='UserStamps']/li");}
	public ElementCollection getStampColourListPopUp() {return driver.FindElementsByXPath("//ul[@id='ddlAvailableStamps']/li");}
	public Element getStampColourPopUp(int i) {return driver.FindElementByXPath("(//ul[@id='ddlAvailableStamps']/li)["+i+"]");}
	public ElementCollection getStampColourListCf() {return driver.FindElementsByXPath("//ul[@id='UserStamps']/li");}
	public Element getStampColourCf(int i) {return driver.FindElementByXPath("(//ul[@id='UserStamps']/li)["+i+"]");}
	public Element getPopUpVerify() {return driver.FindElementByXPath("//div[contains(@class,'ui-dialog ui-corner')]");}
	// added by iyappan
	public Element getEditAssignedColourDrpdwn(String editColour) {return driver.FindElementByXPath("//dl[@id='ddlEditStamps']//a[@id='" + editColour + "']");}
	public Element getCodingStampEditTextBox() {return driver.FindElementById("txtStampNameEdit");}
	public Element getFloppyIcon() {return driver.FindElementByXPath("//ul[@id='SaveUserStamps']//i");}
	public Element getUnCompleteBtnNotPresent() {
		return driver.FindElementByXPath("//button[@id='btnDocumentUnComplete' and contains(@style,'display: none')]");
	}
	public Element getAlreadySelectedError() {return driver.FindElementByXPath("//p[text()='Please enter the details.']");}
	public Element getUnCompleteButton() {return driver.FindElementByXPath("//button[@id='btnDocumentUnComplete']");}
	public Element getDocView_CurrentDocId() {
		return driver.FindElementById("activeDocumentId");
	}
	
	public ReusableDocViewPage(Driver driver) {

		this.driver = driver;
		softAssertion = new SoftAssert();
		base = new BaseClass(driver);
		sessionPage = new SessionSearch(driver);
	}

	/**
	 * @author Indium-Baskar
	 */
//  Reusable Method for checking header value in mini doc list
//  without editing the configure minido list
	public void configureMiniDocList() {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_ConfigMinidoclist().Visible() && getDocView_ConfigMinidoclist().Enabled();
			}
		}), Input.wait30);
		getDocView_ConfigMinidoclist().Click();
		driver.waitForPageToBeReady();
		List<WebElement> optimized = getDocView_Config_Selectedfield().FindWebElements();
		List<String> arrayOptimized = new ArrayList<String>();
		for (int k = 0; k < optimized.size(); k++) {
			arrayOptimized.add(optimized.get(k).getText());
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_MiniDocFields_ConfigSaveButton().Visible()
						&& getDocView_MiniDocFields_ConfigSaveButton().Enabled();
			}
		}), Input.wait30);
		getDocView_MiniDocFields_ConfigSaveButton().Click();
		driver.waitForPageToBeReady();
		base.passedStep("Persistent hits panel refreshed immediately after saving the configure mini doclist");
		List<WebElement> allValues = getMiniDocListHeaderValue().FindWebElements();
		List<String> arrayMiniDocList = new ArrayList<String>();
		for (int j = 1; j < allValues.size(); j++) {
			arrayMiniDocList.add(allValues.get(j).getText());
			if (j == 3) {
				JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();
				jse.executeScript("document.querySelector('.dataTables_scrollBody').scrollBy(400,0)");
				driver.waitForPageToBeReady();
			}
		}
		String optmizedText = arrayOptimized.toString().toUpperCase();
		base.stepInfo(optmizedText);
		String miniDocListText = arrayMiniDocList.toString().toUpperCase();
		base.stepInfo(miniDocListText);
		softAssertion.assertEquals(optmizedText, miniDocListText);
		base.passedStep("Document header value as per the excepted condition");
		base.passedStep("Persistent hits panel reflected for all the document");

	}

	/**
	 * @author Indium-Baskar 
	 */
//  Reusable method for entering document number in central panel
//	Verifying loading display in mini doc list and cental panel
	public void enterDocumentNumberTillLoading() {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_NumTextBox().Enabled();
			}
		}), Input.wait30);
		getDocView_NumTextBox().SendKeys("125" + Keys.ENTER);
		softAssertion.assertEquals(getDocumetListLoading().isDisplayed().booleanValue(), true);
		softAssertion.assertEquals(getCentralPanelDispaly().isDisplayed().booleanValue(), true);
		base.passedStep("While Entering the document number persistent hits displayed");
		base.passedStep("Persistent hits loaded once while entering the document number");
	}


	/**
	 * @author Indium-Baskar
	 *         
	 */
	
//  Reusable method scrolling the document
//  Till the loading display in mini doc list 
	public void scrollUntilLoadingElementIsDisplayed() {
		try {
			WebElement element = getDocumetId().getWebElement();
			int xcord = 50;
			int ycord = 100;
			Actions actions = new Actions(driver.getWebDriver());
			for (int i = 0; i < 5; i++) {
				actions.moveToElement(element, xcord, ycord).build().perform();
			}
			softAssertion.assertEquals(getDocumetListLoading().isDisplayed().booleanValue(), true);
		} catch (Exception e) {
			e.getLocalizedMessage();
			e.printStackTrace();
		}
	}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for switching to child window for all panel
//	Child Window Display and control in child window
	public String switchTochildWindow() {
		String parentWindow = driver.getWebDriver().getWindowHandle();
		Set<String> childWindow = driver.getWebDriver().getWindowHandles();
		for (String miniDocListChild : childWindow) {
			if (!parentWindow.equals(miniDocListChild)) {
				driver.switchTo().window(miniDocListChild);
			    base.waitTime(10);
			}
		}
		return parentWindow;

	}

	/**
	 * @author Indium-Baskar
	 */
//  Reusable method for closing the child window
//	Control goes to parent window after child window close
	public void childWindowToParentWindowSwitching(String parentWindow) {
		driver.waitForPageToBeReady();
		driver.close();
		driver.switchTo().window(parentWindow);
	}

	/**
	 * @author Indium-Baskar
	 */

//	Reusable method for editing the coding form only with complete button click
//	Work with complete button and verify the same as appear
	public void editingCodingFormWithCompleteButton() {
		base.waitForElement(getResponsiveCheked());
		getResponsiveCheked().waitAndClick(5);
		base.waitForElement(getNonPrivilegeRadio());
		getNonPrivilegeRadio().waitAndClick(5);
		base.waitForElement(getDocument_CommentsTextBox());
		getDocument_CommentsTextBox().SendKeys("verify check mark icon");
		base.waitForElement(getCompleteDocBtn());
		getCompleteDocBtn().waitAndClick(5);
		getverifyCodeSameAsLast().WaitUntilPresent().ScrollTo();
		base.stepInfo("Excepted Message:Document completed successfully");
		softAssertion.assertEquals(getverifyCodeSameAsLast().isDisplayed().booleanValue(), true);
		
	}

	/**
	 * @author Indium-Baskar
	 */
//  Reusable method for Session search to till document count to verify both parent and child window
//	For verifying the document count in Minidoclist
	public void documentCountShouldBeSame(String search) throws InterruptedException {
		int basicContentSearchCount = sessionPage.basicContentSearch(search);
		sessionPage.ViewInDocView();
		base.stepInfo("Checking number of document present in mini doc list");
		driver.waitForPageToBeReady();
		base.waitForElementCollection(getDocumetCountMiniDocList());
		int miniDocListCount = getDocumetCountMiniDocList().WaitUntilPresent().size();
		base.stepInfo("MiniDoclist count :  " + miniDocListCount);
		System.out.println(miniDocListCount);
		clickGearIconOpenMiniDocList();
		String childWindow = switchTochildWindow();
		int miniDocListChildCount = getDocumetCountMiniDocList().WaitUntilPresent().size();
		base.stepInfo("MiniDocList Child Window Count :  " + miniDocListChildCount);
		base.stepInfo("Checking number of document equals to minidoc list in pop up state");
		if (miniDocListCount == miniDocListChildCount) {
			base.passedStep(" MiniDoclist count : " + miniDocListCount + " MiniDocList Child Window Count : " + miniDocListChildCount + "");
		} else {
			base.failedStep(" MiniDoclist count : " + miniDocListCount + " MiniDocList Child Window Count : " + miniDocListChildCount + "");
		}
		childWindowToParentWindowSwitching(childWindow);
	}

	/**
	 * @author Indium-Baskar
	 */
//  Reusable method for clicking the checkbox and perform codesameas and verify chain link
//	Action clcik code same as
	public void clickCheckBoxDocListActionCodeSameAs() {
		driver.waitForPageToBeReady();
		base.waitForElementCollection(getDocumetCountMiniDocList());
		for (int i = 1; i <= 3; i++) {
			getDocView_MiniDoc_ChildWindow_Selectdoc(i).waitAndClick(5);;
		}
		base.waitForElement(getDocView_Mini_ActionButton());
		getDocView_Mini_ActionButton().waitAndClick(5);
		base.waitForElement(getDocView__ChildWindow_Mini_CodeSameAs());
		getDocView__ChildWindow_Mini_CodeSameAs().waitAndClick(5);
		base.VerifySuccessMessage("Code same performed successfully.");
		geDocView_MiniList_CodeSameAsIcon().WaitUntilPresent().ScrollTo();
		softAssertion.assertEquals(geDocView_MiniList_CodeSameAsIcon().isDisplayed().booleanValue(), true);
	}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable Method For Click GearIcon To Open MiniDocList
//	Minidoclist child window will open
	public void clickGearIconOpenMiniDocList() {
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		base.waitForElement(getDocView_EditMode());
		getDocView_EditMode().waitAndClick(5);
		base.waitTime(10);
		base.waitForElement(getDocView_HdrMinDoc());
		getDocView_HdrMinDoc().waitAndClick(5);
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium-Baskar
	 */
	
//	Reusable Method For scrolling MiniDocList
//	Till method will work till the scroll end in minidoclist with more number of document
	public void scrollingDocumentInMiniDocList() {
		base.waitForElementCollection(getDocumetCountMiniDocList());
		List<WebElement> scrollTillLast = getDocumetCountMiniDocList().FindWebElements();
		for (int j = 0; j < scrollTillLast.size(); j++) {
			if (j == 12) {
				JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();
				jse.executeScript("document.querySelector('.dataTables_scrollBody').scrollBy(0,4000)");
				driver.waitForPageToBeReady();
			}

			if (j == 30) {
				JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();
				jse.executeScript("document.querySelector('.dataTables_scrollBody').scrollBy(0,15000)");
				softAssertion.assertEquals(getDocumetListLoading().isDisplayed().booleanValue(), true);
				driver.waitForPageToBeReady();
			}
		}
		base.passedStep("Document scrolled with more number of document till scroll end");
		base.passedStep("While scrolling the document loading dispalyed");
	}
	
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable Method For Click clock icon in minidoclist
//	After clicking clcok icon drop down will open in the select document
//	Document should display in default view page
	public void clickClockIconMiniDocList() {
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_HistoryButton());
		getDocView_HistoryButton().waitAndClick(5);
		base.passedStep("User clicked clock icon in minidoclist");
		base.waitForElement(getselectDocFromClckIcon());
		getselectDocFromClckIcon().waitAndClick(5);
		base.passedStep("User selected the document from history drop down");
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_DefaultViewTab());
		softAssertion.assertEquals(getDocView_DefaultViewTab().isDisplayed().booleanValue(), true);
		base.passedStep("Document displaying in default view page");
	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable Method For Click GearIcon To Open Codingform child window
//	Codingform child window will open
	public void clickGearIconOpenCodingFormChildWindow() {
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_EditMode());
		base.waitTillElemetToBeClickable(getDocView_EditMode());
		getDocView_EditMode().waitAndClick(10);
		getHeader().Click();
		base.waitForElement(getDocView_HdrCoddingForm());
		base.waitTillElemetToBeClickable(getDocView_HdrCoddingForm());
		getDocView_HdrCoddingForm().waitAndClick(10);

	}
	
	/**
	 * @author Indium-Baskar
	 */

//	Reusable method for editing the coding form only with Save button click
//	Edited coding form save with save button
	public void editingCodingFormWithSaveButton() {
		driver.waitForPageToBeReady();
		base.waitForElement(getResponsiveCheked());
		getResponsiveCheked().waitAndClick(10);
		base.waitForElement(getNonPrivilegeRadio());
		getNonPrivilegeRadio().waitAndClick(10);
		base.waitForElement(getDocument_CommentsTextBox());
		getDocument_CommentsTextBox().SendKeys("Edited and save with save button");
		driver.scrollPageToTop();
		base.waitForElement(getCodingFormSaveButton());
		getCodingFormSaveButton().waitAndClick(15);
		softAssertion.assertEquals(getCodingFormSaveButton().isDisplayed().booleanValue(), true);
		base.stepInfo("Excepted Message:Document completed successfully");
			
	}
	
	/**
	 * @author Indium-Baskar
	 */

//	Reusable method for editing the coding form and getting Attribute value
//	Attribute Value to verify document same as last (with save Button)
	public void gettingAttributeWithSaveButton() {
	getDocument_CommentsTextBox().WaitUntilPresent().ScrollTo();
	base.waitForElement(getDocument_CommentsTextBox());
	String getAttribute = getDocument_CommentsTextBox().WaitUntilPresent().GetAttribute("value");
	softAssertion.assertEquals("Edited and save with save button", getAttribute);
	if (getAttribute.equals("Edited and save with save button")) {
		base.stepInfo("Document is saved with the last applied coding of  the document..");
		base.passedStep("Expected Message - code same as last scuessfully..");
	} else {
		base.failedStep("Expected Message - code NOT same as last scuessfully..");
	}

	}
	
	/**
	 * @author Indium-Baskar
	 */

//	Reusable method for click code same as last in coding form 
//	If code same as last is clicked from coding form,cursor will move to next document in minidoclist.
	public void clickCodeSameAsLast() {
		driver.waitForPageToBeReady();
		base.waitForElement(getCodeSameAsLast());
		getCodeSameAsLast().waitAndClick(10);
		softAssertion.assertTrue(getCodeSameAsLast().isDisplayed() && getCodeSameAsLast().Enabled());
		if (getCodeSameAsLast().Displayed() && getCodeSameAsLast().Enabled()) {
			base.stepInfo("coded as per the previous document..");
			base.passedStep("Cursor has moved to the next document in mini doc list..");
		} else {
			base.failedStep("Failed to move next document in mini doc list..");
		}
	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for click coding stamp button and text box filling & assign colour  
	public void stampColourSelection(String fieldValue, String colour) {
		base.waitTillElemetToBeClickable(getResponsiveCheked());
		getResponsiveCheked().waitAndClick(5);
		base.waitForElement(getNonPrivilegeRadio());
		getNonPrivilegeRadio().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.waitForElement(getCodingFormStampButton());
		getCodingFormStampButton().waitAndClick(10);
		base.waitForElement(getCodingStampTextBox());
		getCodingStampTextBox().SendKeys(fieldValue);
		base.waitForElement(getDrp_StampColour());
		getDrp_StampColour().waitAndClick(5);
		base.waitForElement(getAssignedColour(colour));
		getAssignedColour(colour).waitAndClick(5);
		base.waitForElement(getCodingStampSaveBtn());
		getCodingStampSaveBtn().waitAndClick(5);
		base.passedStep("User successfully assigned colour for coding stamp");
		
		
	}
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for click coding stamp button and text box filling & assign colour  
	public void codingStampPopUpWindow(String fieldValue, String colour) {
		driver.waitForPageToBeReady();
		base.waitForElement(getCodingFormStampButton());
		getCodingFormStampButton().waitAndClick(10);
		base.waitForElement(getCodingStampTextBox());
		getCodingStampTextBox().SendKeys(fieldValue);
		base.waitForElement(getDrp_StampColour());
		getDrp_StampColour().waitAndClick(10);
		base.waitForElement(getAssignedColour(colour));
		getAssignedColour(colour).waitAndClick(10);
		base.waitForElement(getCodingStampSaveBtn());
		getCodingStampSaveBtn().waitAndClick(10);
		base.passedStep("User successfully assigned colour for coding stamp");
		
		
	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for click pencil icon for last applied colour
	public void pencilGearicon(String icon) {
		driver.waitForPageToBeReady();
		base.waitForElement(getEditStampSettings());
		getEditStampSettings().waitAndClick(10);
		base.waitForElement(getCodingStampLastIcon(icon));
		getCodingStampLastIcon(icon).waitAndClick(10);
		
		
	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for verifying coding stamp colour in Stamp popup window
	public void verifyingPostFixAssignedColour(String textBox) {
		driver.waitForPageToBeReady();
		getCodingStampPopup().Visible();
		String expected = getCodingEditStampTextBox().GetAttribute("value");
		String actual="New";
		softAssertion.assertEquals(actual, expected);
		base.waitForElement(getVerifyPopUpColour());
		boolean flag = getVerifyPopUpColour().GetAttribute("id").contains("BLACK");
		if (flag) {
			softAssertion.assertTrue(flag);
			base.passedStep("Verified  coding stamp Popup as per the assigned colour");
		} else {
			base.failedStep("Coding stamp colour not as per the assigned colour");
		}
		base.waitForElement(getDeletePopUpAssignedColour());
		getDeletePopUpAssignedColour().waitAndClick(10);
	}
	
	/**
	 * @author Indium-Baskar
	 */
//  Reusable method for perform codesameas and verify chain link
//	Action click code same as
	public String clickCodeSameAs() {
		driver.waitForPageToBeReady();
		String prinipalDocs=getVerifyPrincipalDocument().getText().trim();
		base.waitForElement(getDocView_Mini_ActionButton());
		getDocView_Mini_ActionButton().waitAndClick(5);
		base.waitForElement(getDocView__ChildWindow_Mini_CodeSameAs());
		getDocView__ChildWindow_Mini_CodeSameAs().waitAndClick(5);
		base.stepInfo("Expected Message : Code same performed successfully.");
//		driver.getWebDriver().navigate().refresh();
//		driver.switchTo().alert().accept();
		geDocView_MiniList_CodeSameAsIcon().WaitUntilPresent().ScrollTo();
		softAssertion.assertEquals(geDocView_MiniList_CodeSameAsIcon().isDisplayed().booleanValue(), true);
		base.passedStep("Chain link displayed for document after performing code same as action");
		return prinipalDocs;
	}
	
	/**
	 * @author Indium-Baskar
	 */
//  Reusable method for perform codesameas and verify warning message
//	Action click code same as But verify will done in main class
	public void verifyWarningMessageClickCodeSameAs() {
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_Mini_ActionButton());
		getDocView_Mini_ActionButton().waitAndClick(5);
		base.waitForElement(getDocView__ChildWindow_Mini_CodeSameAs());
		getDocView__ChildWindow_Mini_CodeSameAs().waitAndClick(5);
		
	}
	
	/**
	 * @author Indium-Baskar
	 */
//  Reusable method for Enable Toggle button
	public void enableToggleConfigMiniDocList() {
		driver.waitForPageToBeReady();
		base.waitForElement(getReviewModeBtn());
		getReviewModeBtn().waitAndClick(10);
		base.waitForElement(getToggleEnableConfiOn());
		getToggleEnableConfiOn().waitAndClick(10);
		base.waitForElement(getConfigSaveButton());
		getConfigSaveButton().waitAndClick(10);
		
	}
	
	/**
	 * @author Indium-Baskar
	 */
//  Reusable method for perform codesameas and verify chain link parent window
//	Action clickk code same as
	public void clickCodeSameAsParent() {
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_Mini_ActionButton());
		getDocView_Mini_ActionButton().waitAndClick(5);
		base.waitForElement(getDocView__ChildWindow_Mini_CodeSameAs());
		getDocView__ChildWindow_Mini_CodeSameAs().waitAndClick(5);
		base.stepInfo("Expected Message : Code same performed successfully.");
		driver.getWebDriver().navigate().refresh();
		try {
			driver.switchTo().alert().accept();
		} catch (org.openqa.selenium.NoAlertPresentException e) {
			e.printStackTrace();
		}
		driver.waitForPageToBeReady();
		geDocView_MiniList_CodeSameAsIcon().WaitUntilPresent().ScrollTo();
		softAssertion.assertEquals(geDocView_MiniList_CodeSameAsIcon().isDisplayed().booleanValue(), true);
		base.passedStep("Chain link displayed for document after performing code same as action");
	}
	
	/**
	 * @author Indium-Baskar
	 */
//  Reusable Method for checking header value in mini doc list
//  without editing the configure minido list
	public String defaultHeaderValue() {
		driver.waitForPageToBeReady();
		List<WebElement> allValues = getMiniDocListHeaderValue().FindWebElements();
		List<String> arrayMiniDocList = new ArrayList<String>();
		driver.waitForPageToBeReady();
		for (int j = 1; j < allValues.size(); j++) {
			arrayMiniDocList.add(allValues.get(j).getText());
			if (j == 3) {
				JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();
				jse.executeScript("document.querySelector('.dataTables_scrollBody').scrollBy(400,0)");
				driver.waitForPageToBeReady();
			}
		}
		String expvalues[] = { "DOCID", "DOCFILETYPE", "FAMILYRELATIONSHIP", "FAMILYMEMBERCOUNT" };
		String miniDocListText = arrayMiniDocList.toString().toUpperCase();
		base.stepInfo("As per the expected condition of header value :  " + miniDocListText + "");
		softAssertion.assertEquals(expvalues, miniDocListText);
		base.passedStep("Document header value as per the excepted condition");
		driver.waitForPageToBeReady();
		return miniDocListText;
		

	}
	
	/**
	 * @author Indium-Baskar
	 */

//	Reusable method for editing the coding form and getting Attribute value
//	Attribute Value to verify document same as last (with Complete Button)
	public void gettingAttributeWithCompleteButton() {
		base.CloseSuccessMsgpopup();
		base.waitForElement(getDocument_CommentsTextBox());
		try {
			getDocument_CommentsTextBox().WaitUntilPresent().ScrollTo();
		} catch (org.openqa.selenium.StaleElementReferenceException e1) {
			e1.printStackTrace();
		}
		try {
			String getAttribute = getDocument_CommentsTextBox().WaitUntilPresent().GetAttribute("value");
			softAssertion.assertEquals("verify check mark icon", getAttribute);
			if (getAttribute.equals("verify check mark icon")) {
				base.stepInfo("Document is saved with the last applied coding of  the document..");
				base.passedStep("Expected Message - code same as last scuessfully..");
			} else {
				base.stepInfo("Expected Message - code NOT same as last scuessfully..");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for saving coding form with coding stamp with applying colour
    public void codingFormSavingWithCodingStamp(String fieldValue, String colourIcon) {
    	driver.waitForPageToBeReady();
    	base.waitTillElemetToBeClickable(getResponsiveCheked());
		getResponsiveCheked().waitAndClick(5);
		base.waitForElement(getNonPrivilegeRadio());
		getNonPrivilegeRadio().Click();
		base.waitForElement(getDocument_CommentsTextBox());
		getDocument_CommentsTextBox().SendKeys("Edited and saving with coding stamp button");
		stampColourSelection(fieldValue, colourIcon);
    }
    
    /**
	 * @author Indium-Baskar
	 */

//	Reusable method for editing the coding form only with Save button click
//	Edited coding form save with save and next button
	public String editingCodingFormWithSaveAndNextButton() {
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String principalDocs=getVerifyPrincipalDocument().getText().trim();
		base.waitForElement(getResponsiveCheked());
		getResponsiveCheked().waitAndClick(5);
		base.waitForElement(getNonPrivilegeRadio());
		getNonPrivilegeRadio().waitAndClick(5);
		base.waitTillElemetToBeClickable(getDocument_CommentsTextBox());
		getDocument_CommentsTextBox().SendKeys("Edited and save with save button");
		base.waitForElement(getSaveAndNextButton());
		getSaveAndNextButton().waitAndClick(5);
		base.stepInfo("Excepted Message:Document completed successfully");
		return principalDocs;
	}
	
	/**
	 * @author Indium-Baskar
	 */

//	Reusable method for clicking all checkbox in doclist and action view as docview
//  performing from doclist
	public void docListCheckBoxAndViewInDocView() {
		driver.waitForPageToBeReady();
		base.waitForElement(getDocListAllDocCheckBox());
		getDocListAllDocCheckBox().waitAndClick(10);
		base.waitForElement(getYesButtonForAllDoc());
		getYesButtonForAllDoc().waitAndClick(5);
		base.waitForElement(getDocListOkBtn());
		getDocListOkBtn().waitAndClick(5);
		base.waitForElement(getDocList_Action_Drp_Dwn());
		getDocList_Action_Drp_Dwn().waitAndClick(5);
		base.waitForElement(getDocListViewInDocView());
		getDocListViewInDocView().waitAndClick(5);
		base.stepInfo("User selected document from doclist panel and action to view in docview");

	}
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for deleteing stamp colour
	public void deleteStampColour(String icon) {
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		getHeader().Click();
		pencilGearicon(icon);
		base.waitForElement(getDeletePopUpAssignedColour());
		getDeletePopUpAssignedColour().waitAndClick(5);
	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable Method For Click GearIcon To Open Analytical panel
//	Analytical child window panel to open
	public void clickGearIconOpenAnalyticalPanel() {
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_EditMode());
		getDocView_EditMode().waitAndClick(5);
		base.waitForElement(getDocView_HdrAnalytics());
		getDocView_HdrAnalytics().ScrollTo();
		base.waitForElement(getDocView_HdrAnalytics());
		getDocView_HdrAnalytics().waitAndClick(5);
		base.stepInfo("Analytical panel child window opened");
	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable Method For ViewDocument in analytical panel
	public void viewInDocumentAnalyticalPanel() {
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(10);
		base.waitForElement(getViewDocumentNearDupe());
		getViewDocumentNearDupe().waitAndClick(10);
	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable Method For code same as action in analytical panel
//	Chain link to verify
	public void codeSameAsInAnalyticalPanel() {
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(10);
		base.waitForElement(getCodeSameAsNearDupe());
		getCodeSameAsNearDupe().waitAndClick(10);
        base.waitForElement(getChainVerifyInAnalDocs());
        softAssertion.assertEquals(getChainVerifyInAnalDocs().isDisplayed().booleanValue(),true);
        if (getChainVerifyInAnalDocs().Displayed()) {
        base.passedStep("Chain link displayed in analytical panel while performing code same as action");			
		}
        else {
			base.stepInfo("Chain link not displayed");
		}
	}
	
	public List<String> availableListofElements(ElementCollection element) {
		List<String> elementNames = new ArrayList<>();
		List<WebElement> elementList = null;
		elementList = element.FindWebElements();
		for (WebElement wenElementNames : elementList) {
			String elementName = wenElementNames.getText();
			elementNames.add(elementName);
		}
		return elementNames;
	}
	

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method click minidoclist to verify document td[2]
	public String miniDocListElementText(int name) {
		List<String> miniDocListElementsText = new ArrayList<>();
		ElementCollection element2 = getMiniDocListDocIdText();
		miniDocListElementsText = availableListofElements(element2);
		String docId = miniDocListElementsText.get(name);
        getMiniDocClick(docId).waitAndClick(5);
		return docId;
	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method click analytical panel code same as part of minidoclist
	public String analyticalDocsPartOfMiniDocList(int count) throws InterruptedException {
		driver.waitForPageToBeReady();
		boolean status=false;
		base.waitForElementCollection(getDocView_MiniListDocuments());
		List<String> uniqueDocuments= new ArrayList<>();
		Set<String>docList=new HashSet<String>();
		List<String> miniDocList= new ArrayList<>();
		ElementCollection miniDocListelement=getMiniDocListDocIdText();
		miniDocList=availableListofElements(miniDocListelement);
		for (String minidoclist : miniDocList) {
			docList.add(minidoclist);
		}
        driver.waitForPageToBeReady();
        Thread.sleep(5000);// Mandatory thread.sleep no need to delete
        getDocView_Analytics_NearDupeTab().WaitUntilPresent().ScrollTo();
		getDocView_Analytics_NearDupeTab().waitAndClick(5);
		List<String> analyticalDocs= new ArrayList<>();
		List<String> analyticalDocsAgain= new ArrayList<>();
		base.waitTime(5);
		ElementCollection analyticsElement=getAnalyticalPanelDocIdText();
		analyticalDocs=availableListofElements(analyticsElement);
		for (String analytical : analyticalDocs) {
			if (docList.add(analytical)) {
				}
			else {
				uniqueDocuments.add(analytical);
			}
		}
			if (uniqueDocuments.size()<1) {
			for (int i = 1; i < docList.size(); i++) {
				driver.scrollPageToTop();
				getClickDocviewID(++i).waitAndClick(5);
				base.waitTime(5);
				ElementCollection analyticsElementAgain=getAnalyticalPanelDocIdText();
				analyticalDocsAgain=availableListofElements(analyticsElementAgain);
				for (String analyticalAgain : analyticalDocsAgain) {
					if (!docList.add(analyticalAgain)) {
						uniqueDocuments.add(analyticalAgain);
						status=true;
						break;
						}
				}
				if (status == true) {
					break;
				}

			}
		}
		String docIdText=uniqueDocuments.get(0);
		getAnalyCheckBox(docIdText).WaitUntilPresent().ScrollTo();
		base.waitForElement(getAnalyCheckBox(docIdText));
		getAnalyCheckBox(docIdText).waitAndClick(10);
		return docIdText;

	}
	
	
	/**
	 * @author Indium-Baskar
	 */
	// checking duplicate in mnidoclist
	public List<String> miniDocList() throws InterruptedException {
		driver.waitForPageToBeReady();
		base.waitForElementCollection(getDocView_MiniListDocuments());
		List<String> listOFData= new ArrayList<>();
		ElementCollection element=getMiniDocListDocIdText();
		listOFData=availableListofElements(element);
        System.out.println(listOFData);
		return listOFData;
	}
	
	/**
	 * @author Indium-Baskar
	 */
   // checking duplicates in analytical panel child wondow
   public List<String> analyticalDocs() throws InterruptedException {     
        driver.waitForPageToBeReady();
        Thread.sleep(5000);// Mandatory this thread.sleep no need to delete(for loading analytical docs taking time)
        getDocView_Analytics_NearDupeTab().WaitUntilPresent().ScrollTo();
		getDocView_Analytics_NearDupeTab().waitAndClick(5);
        Thread.sleep(5000);// Mandatory this thread. sleep no need to delete
		List<String> listOFData= new ArrayList<>();
		ElementCollection element=getAnalyticalPanelDocIdText();
		listOFData=availableListofElements(element);
		System.out.println(listOFData);
		return listOFData;

	}
   
	/**
	 * @author Indium-Baskar
	 */
//   Reusable method for alert
   public void refreshAndAlert() {
		driver.getWebDriver().navigate().refresh();
		driver.switchTo().alert().accept();
	   
   }
   
   
	/**
	 * @author Indium-Baskar
	 */
	public void VerifyTheDocument() {
		driver.waitForPageToBeReady();
		base.waitTime(3);
		boolean flagOne = getVer_Responsive_ChckBox().getWebElement().isSelected();
		boolean flagTwo = getVer_Not_Privileged_ChckBox().getWebElement().isSelected();
		if (flagOne == true && flagTwo == true) {
			softAssertion.assertEquals(flagOne = flagTwo, true);
			base.passedStep("Verified child  window get updated as well");
		} else {
			base.failedStep("Not as per the expected condition in parent window");
		}
	}
   
	
	/**
	 * @author Indium-Baskar
	 */

//	Reusable method for editing the coding form only with complete button click
//	Work with complete button and verify the same as appear
	public void editingCodingFormWithCompleteButtonChild() {
		base.waitForElement(getResponsiveCheked());
		getResponsiveCheked().waitAndClick(5);
		base.waitForElement(getNonPrivilegeRadio());
		getNonPrivilegeRadio().waitAndClick(5);
		base.waitForElement(getDocument_CommentsTextBox());
		getDocument_CommentsTextBox().SendKeys("verify check mark icon");
		base.waitForElement(getCompleteDocBtn());
		getCompleteDocBtn().waitAndClick(5);
		base.stepInfo("Excepted Message:Document completed successfully");
		
	}
	
	
	/**
	 * @author Indium-Baskar
	 */

//	Reusable method for removing code same as
	public void removeCodeSameAs() {
	driver.waitForPageToBeReady();
	base.waitForElement(getDocView_Mini_ActionButton());
	getDocView_Mini_ActionButton().waitAndClick(5);
	base.waitForElement(getDocView__ChildWindow_Mini_RemoveCodeSameAs());
	getDocView__ChildWindow_Mini_RemoveCodeSameAs().waitAndClick(5);
	driver.waitForPageToBeReady();
	if (geDocView_MiniList_CodeSameAsIcon().isElementAvailable(5)) {
		base.failedStep("Chain link displayed");
	} else {
		base.passedStep("Chain link not displayed after performing removecodesame as action");
	}
}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for click for last applied colour
	public void lastAppliedStamp(String icon) {
		driver.waitForPageToBeReady();
		base.waitForElement(getCodingStampLastIcon(icon));
		getCodingStampLastIcon(icon).waitAndClick(10);
	}
	
	/**
	 * @author Indium-Baskar
	 */

//	Reusable method for editing the coding form only with complete button click
//	Work with complete button and verify the same as appear
	public void editingCodingFormWithSaveAndNextChild() {
		base.waitForElement(getResponsiveCheked());
		getResponsiveCheked().waitAndClick(5);
		base.waitForElement(getNonPrivilegeRadio());
		getNonPrivilegeRadio().waitAndClick(5);
		base.waitForElement(getDocument_CommentsTextBox());
		getDocument_CommentsTextBox().SendKeys("verify check mark icon");
		base.waitForElement(getSaveAndNextButton());
		getSaveAndNextButton().waitAndClick(5);
		base.stepInfo("Excepted Message:Document completed successfully");
		
	}
	
	/**
	 * @author Indium-Baskar
	 */
	
//	Reusable method for edit coding form with stamp colour and save it.
	public void editCodingFormAndSaveWithStamp(String fieldValue,String stampColour) {
		driver.waitForPageToBeReady();
		base.waitForElement(getResponsiveCheked());
		getResponsiveCheked().waitAndClick(5);
		base.waitForElement(getNonPrivilegeRadio());
		getNonPrivilegeRadio().waitAndClick(5);
		base.waitForElement(getDocument_CommentsTextBox());
		getDocument_CommentsTextBox().SendKeys("Saving with Stamp");
		stampColourSelection(fieldValue, stampColour);
		
	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for switching to child window for all panel
//	Child Window Display and control in Coding Form child window
	public Set<String> switchToCodingFormchildWindow() {
		String parentWindow = driver.getWebDriver().getWindowHandle();
		Set<String> childWindow = driver.getWebDriver().getWindowHandles();
		for (String miniDocListChild : childWindow) {
			if (!parentWindow.equals(miniDocListChild)) {
				driver.switchTo().window(miniDocListChild);
				driver.waitForPageToBeReady();
			}
		}
		return childWindow;
	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for switch child window
	public void switchToNewWindow(int windowNumber) {
	Set<String> s = driver.getWebDriver().getWindowHandles();   
    Iterator < String > ite = s.iterator();
    int i = 1;
    while (ite.hasNext() && i < 10) {
        String popupHandle = ite.next().toString();
        driver.switchTo().window(popupHandle);
        System.out.println("Window title is : "+driver.getTitle());
        if (i == windowNumber) break;
        i++;
	}
}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method to get document docid text in ascending order
	public LinkedList<String> miniDocListSortSequence() {
		driver.waitForPageToBeReady();
		base.waitForElementCollection(getMiniDocListDocIdText());
		List<WebElement> ascending=getMiniDocListDocIdText().FindWebElements();
		LinkedList<String> docIds =  new LinkedList<String>();
		for(int i=0;i<ascending.size();i++){
		    String s = ascending.get(i).getText();
		    docIds.add(s);
		}
		return docIds;
	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for click coding stamp button and text box filling & assign colour  
	public void stampColourInparentWindow(String fieldValue, String colour) {
		driver.waitForPageToBeReady();
		base.waitForElement(getCodingStampTextBox());
		getCodingStampTextBox().SendKeys(fieldValue);
		base.waitForElement(getDrp_StampColour());
		getDrp_StampColour().waitAndClick(10);
		base.waitForElement(getAssignedColour(colour));
		getAssignedColour(colour).waitAndClick(10);
		base.waitForElement(getCodingStampSaveBtn());
		getCodingStampSaveBtn().waitAndClick(10);
		base.passedStep("User successfully assigned colour for coding stamp");
	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for scroll with less document
	public void scrollDownWithLessDocument() {
		driver.waitForPageToBeReady();
		base.waitForElementCollection(getDocumetCountMiniDocList());
		List<WebElement> scrollTillLast = getDocumetCountMiniDocList().FindWebElements();
		for (int j = 0; j < scrollTillLast.size(); j++) {
			if (j == 12) {
				JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();
				jse.executeScript("document.querySelector('.dataTables_scrollBody').scrollBy(0,4000)");
				driver.waitForPageToBeReady();
				softAssertion.assertEquals(getDocumetListLoading().isDisplayed().booleanValue(), true);
				base.passedStep("Document scrolled with less number of document");
			}
			else {
				base.stepInfo("Document not get scrolled");
			}
	}
	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for clicking translation tab
	public void docViewTranslationTab() {
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_TranslationTab());
		getDocView_TranslationTab().waitAndClick(5);
		driver.waitForPageToBeReady();
		Boolean flag = getDocView_TranslationTab().Enabled();
		softAssertion.assertTrue(flag);
	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for clicking text tab
	public void docviewTextTab() {
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_TextTab());
		getDocView_TextTab().waitAndClick(5);
		driver.waitForPageToBeReady();
		Boolean flag = getDocView_TextTab().Enabled();
		softAssertion.assertTrue(flag);
	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for verifying all tabs for document loading
	public void verifyDocsNavigateToAllTabs() {
		getDocView_TextTab().waitAndClick(5);
		driver.waitForPageToBeReady();
		Boolean flag2 = getDocView_TextTab().Enabled();
		getDocView_ImagesTab().waitAndClick(5);
		driver.waitForPageToBeReady();
		Boolean flag3 = getDocView_ImagesTab().Enabled();
		getDocView_TranslationTab().waitAndClick(5);
		driver.waitForPageToBeReady();
		Boolean flag4 = getDocView_TranslationTab().Enabled();
		if (flag2 == true && flag3 == true && flag4 == true) {
			base.passedStep("Same document loaded in all tabs");
		} else {
			base.failedMessage("Same document not loaded in all tabs");
		}
	}
	
	/**
	 * @author Indium-Baskar
	 */

//	Reusable method for verify code same as last is disabled after refresh
	public void codeSameAsLastDisable() {
		driver.waitForPageToBeReady();
		boolean flag;
		base.waitForElement(getCodeSameAsLast());
		flag = getCodeSameAsLast().GetAttribute("style").contains("default");
		if (flag==true) {
			softAssertion.assertTrue(true);
			base.passedStep("Code same as last is disable ");
		} else {
			softAssertion.assertTrue(false);
			base.failedStep("Code same as last is not disable");
		}
	}
	
	/**
	 * @author Indium-Baskar
	 */
	
//  Reusable method for verify  coding stamp button should display	
	public void stampButtonShouldDisplay() {
		driver.waitForPageToBeReady();
		boolean flag;
		base.waitForElement(getCodingFormStampButton());
		flag=getCodingFormStampButton().isDisplayed();
		if (flag==true) {
			softAssertion.assertTrue(true);
			base.passedStep("Coding stamp displayed");
		} else {
			softAssertion.assertTrue(false);
			base.failedStep("Coding stamp not displayed");
		}
	}
	
	/**
	 * @author Indium-Baskar
	 */

//	Reusable method for verify code same as last is disabled after refresh
	public void codeSameAsLastDisplay() {
		driver.waitForPageToBeReady();
		boolean flag;
		base.waitForElement(getCodeSameAsLast());
		flag = getCodeSameAsLast().GetAttribute("style").contains("default");
		if (flag==true) {
			softAssertion.assertTrue(true);
			base.passedStep("Code same as last button displayed");
		} else {
			softAssertion.assertTrue(false);
			base.failedStep("Code same as last is not displayed");
		}
	}
	
	
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable editable metadata using save and next
	public void editableMetaDataValidation(String metaDataBox,String fieldText) {
		driver.waitForPageToBeReady();
		base.waitForElement(getReadOnlyTextBox(metaDataBox));
		getReadOnlyTextBox(metaDataBox).SendKeys(fieldText);
		base.waitForElement(getSaveAndNextButton());
		getSaveAndNextButton().waitAndClick(5);
	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for closing child window one by one
	public void closeWindow(int count) {
		List<String> windows = new ArrayList<>(driver.getWebDriver().getWindowHandles());
		for (String s : windows) {
			System.out.println("window: " + s);
		}
		String closingWidow = windows.get(count);
		driver.switchTo().window(closingWidow).close();
	}
	
	
	/**
	 * @author Indium-Baskar
	 */
	
//	Reusable method for edit coding form with stamp colour and save it.
	public void editCodingFormAndSaveWithStampColour(String fieldValue,String stampColour,String comment) {
		driver.waitForPageToBeReady();
		base.waitForElement(getResponsiveCheked());
		getResponsiveCheked().waitAndClick(5);
		base.waitForElement(getNonPrivilegeRadio());
		getNonPrivilegeRadio().waitAndClick(5);
		base.waitForElement(getDocument_CommentsTextBox());
		getDocument_CommentsTextBox().SendKeys(comment);
		stampColourSelection(fieldValue, stampColour);
	}
	
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for verify the saved stamp
	public void verifySavedStamp(String comment) {
		driver.waitForPageToBeReady();
		base.waitForElement(getDocument_CommentsTextBox());
		String text=getDocument_CommentsTextBox().GetAttribute("value");
		softAssertion.assertEquals(text,comment);
		if (text.equals(comment) ) {
			base.stepInfo("Coding form value as per the previous saved stamp");
			base.passedStep("Coding form value updated while clicking the saved stamp");
		} else {
			base.failedStep("Not as per the expected condition in parent window");
		}
	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for saved colour should not clickable
	public void savedColorNotClickable(String colour) {
		driver.waitForPageToBeReady();
		base.waitForElement(getDrp_StampColour());
		getDrp_StampColour().waitAndClick(10);
		base.waitForElement(getAssignedColour(colour));
		getAssignedColour(colour).waitAndClick(10);
		boolean flag = getAssignedColour(colour).GetAttribute("class").contains("disable");
		if (flag) {
			softAssertion.assertTrue(flag);
			base.passedStep("Verified assigned stamp colour is not clickable");
		} else {
			base.failedStep("Assigned stamp colour is clickabe");
		}
		base.waitForElement(getCodingStampCancel());
		getCodingStampCancel().waitAndClick(5);
	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for getting default colour popup stamp
	public List<String> getDefaultPopUpStampColour() {
		List<String> colorNames = new ArrayList<>();
		driver.waitForPageToBeReady();
		base.waitForElement(getCodingFormStampButton());
		getCodingFormStampButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.waitForElement(getDrp_StampColour());
		getDrp_StampColour().waitAndClick(5);
		base.waitForElementCollection(getStampColourListPopUp());
		for(int i=1;i<=getStampColourListPopUp().size();i++) {
		base.waitForElement(getStampColourPopUp(i));
		String color = getStampColourPopUp(i).GetAttribute("id");
		colorNames.add(color);
		System.out.println(color);
		}
		getCodingStampCancel().waitAndClick(5);
		return colorNames;
		}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for getting default colour in coding form
	public List<String> getDefaultCodingFormColour() {
		List<String> colorNames = new ArrayList<>();
		driver.waitForPageToBeReady();
		base.waitForElementCollection(getStampColourListCf());
		for(int i=1;i<=getStampColourListCf().size();i++) {
		base.waitForElement(getStampColourCf(i));
		String color = getStampColourCf(i).GetAttribute("id");
		colorNames.add(color);
		System.out.println(color);
		}
		return colorNames;
		}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for click coding stamp button and text box filling & assign colour  
	public void cancelButtonValidate(String fieldValue, String colour) {
		driver.waitForPageToBeReady();
		base.waitForElement(getCodingFormStampButton());
		getCodingFormStampButton().waitAndClick(10);
		base.waitForElement(getCodingStampTextBox());
		getCodingStampTextBox().SendKeys(fieldValue);
		base.waitForElement(getDrp_StampColour());
		getDrp_StampColour().waitAndClick(10);
		base.waitForElement(getAssignedColour(colour));
		getAssignedColour(colour).waitAndClick(10);
		base.waitForElement(getCodingStampCancel());
		getCodingStampCancel().waitAndClick(10);
		if (getPopUpVerify().isDisplayed()) {
			base.failedStep("Coding stamp saved");
		}
		else {
			base.passedStep("Coding stamp not saved");
			base.passedStep("Coding stamp PopUp closed after clicking cancel button");
		}
	}
	
	/**
	 * @author Indium-Baskar
	 */
	
//	Reusable method for edit coding form with stamp colour and cancel the button
	public void editCodingFormAndCancel(String fieldValue,String stampColour,String comment) {
		driver.waitForPageToBeReady();
		base.waitForElement(getResponsiveCheked());
		getResponsiveCheked().waitAndClick(5);
		base.waitForElement(getNonPrivilegeRadio());
		getNonPrivilegeRadio().waitAndClick(5);
		base.waitForElement(getDocument_CommentsTextBox());
		getDocument_CommentsTextBox().SendKeys(comment);
		cancelButtonValidate(fieldValue, stampColour);
	}
	
	/**
	 * @author Indium-Baskar
	 */
	
//	Reusable method for edit coding without saving values
	public void editCodingForm(String comment) {
		driver.waitForPageToBeReady();
		base.waitForElement(getResponsiveCheked());
		getResponsiveCheked().waitAndClick(5);
		base.waitForElement(getNonPrivilegeRadio());
		getNonPrivilegeRadio().waitAndClick(5);
		base.waitTillElemetToBeClickable(getDocument_CommentsTextBox());
		getDocument_CommentsTextBox().Clear();
		base.waitForElement(getDocument_CommentsTextBox());
		getDocument_CommentsTextBox().SendKeys(comment);
	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for click coding stamp button and text box filling & assign colour  
	public void popUpAction(String fieldValue, String colour) {
		driver.waitForPageToBeReady();
		base.waitForElement(getCodingStampTextBox());
		getCodingStampTextBox().SendKeys(fieldValue);
		base.waitForElement(getDrp_StampColour());
		getDrp_StampColour().waitAndClick(10);
		base.waitForElement(getAssignedColour(colour));
		getAssignedColour(colour).waitAndClick(10);
		base.waitForElement(getCodingStampSaveBtn());
		getCodingStampSaveBtn().waitAndClick(10);
	}
	
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to edit the existing stamp
	 */
	public void editStampColour(String fieldValue, String colour, String existingColor) {
		driver.waitForPageToBeReady();
		base.waitForElement(getEditStampSettings());
		getEditStampSettings().waitAndClick(10);
		base.waitTillElemetToBeClickable(getCodingStampLastIcon(existingColor));
		getCodingStampLastIcon(existingColor).waitAndClick(10);
		base.waitForElement(getEditAssignedColourDrpdwn(existingColor));
		getEditAssignedColourDrpdwn(existingColor).waitAndClick(10);
		base.waitForElement(getEditAssignedColour(colour));
		getEditAssignedColour(colour).waitAndClick(10);
		base.waitForElement(getCodingStampEditTextBox());
		getCodingStampEditTextBox().SendKeys(fieldValue);
		base.waitForElement(getCodingStampSaveThisFormBtn());
		getCodingStampSaveThisFormBtn().waitAndClick(10);
		base.passedStep("User successfully edited the colour and text in coding stamp");		
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to verify floppy icon tooltip
	 */
	public void verifyFloppyIconToolTip(String expectedTooltip) {
		driver.waitForPageToBeReady();
		base.waitForElement(getFloppyIcon());
		getFloppyIcon().ScrollTo();
		String actualToolTip = getFloppyIcon().GetAttribute("title");
		softAssertion.assertEquals(expectedTooltip, actualToolTip);
		base.passedStep("Floppy icon tool tip is displayed as expected");
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to verify floppy icon tooltip
	 */
	public void verifyCompletedIconNotDisplayed() {
		driver.waitForPageToBeReady();
		if(getverifyCodeSameAsLast().isElementAvailable(5)==false) {
			base.passedStep("Tick icon in mini doc list is not displayed for the uncompletd documents");
		}else {
			base.failedStep("Tick icon in mini doc list is displayed for the uncompletd documents");
		}
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method is used to edit codingform textbox and complete it
	 */
	public void editTextBoxInCodingFormWithCompleteButton(String value) {
		base.waitForElement(getDocument_CommentsTextBox());
		getDocument_CommentsTextBox().SendKeys(value);
		base.waitForElement(getCompleteDocBtn());
		getCompleteDocBtn().waitAndClick(5);
		getverifyCodeSameAsLast().WaitUntilPresent().ScrollTo();
		base.stepInfo("Excepted Message:Document completed successfully");
		softAssertion.assertEquals(getverifyCodeSameAsLast().isDisplayed().booleanValue(), true);
		
	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method if stamp already selected
	public void errorMessageForStamp() {
		try {
			if (getAlreadySelectedError().isDisplayed()==true) {
				base.waitForElement(getCodingStampCancel());
			  getCodingStampCancel().waitAndClick(5);
			}
			else {
				System.out.println("Stamp selected");
			}
		} catch (org.openqa.selenium.NoSuchElementException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for verify the comments
	public void verifyingComments(String comment) {
		driver.waitForPageToBeReady();
		base.waitForElement(getDocument_CommentsTextBox());
		String text=getDocument_CommentsTextBox().GetAttribute("value");
		softAssertion.assertEquals(text,comment);
		if (text.equals(comment) ) {
			base.passedStep("Coding form value as per the expected one");
		} else {
			base.failedStep("Not as per the expected condition");
		}
	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for complete button
	public void completeButton() {
		driver.waitForPageToBeReady();
		base.waitForElement(getCompleteDocBtn());
		getCompleteDocBtn().waitAndClick(5);
	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for verifying the uncomplete button
	public void uncompleteButtonValidate() {
		driver.waitForPageToBeReady();
		if (getUnCompleteButton().isDisplayed()) {
			softAssertion.assertEquals(getUnCompleteButton().Displayed().booleanValue(), true);
			base.passedStep("Uncomplete button displayed for completed documents ");
		} else {
			base.failedStep("Uncomplete button not displayed for completed documents ");

		}
	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for saving document using save button
	public void saveButton() {
		driver.waitForPageToBeReady();
		base.waitForElement(getCodingFormSaveButton());
		getCodingFormSaveButton().waitAndClick(5);
	}
	
	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for clearing comment text
	public void clearComments() {
		driver.waitForPageToBeReady();
		base.waitForElement(getDocument_CommentsTextBox());
		getDocument_CommentsTextBox().Clear();
	}
	/**
	 * @author Iyappan.Kasinathan
	 */
	public void selectLastDocInMiniDocList() {
		driver.waitForPageToBeReady();
		base.waitForElementCollection(getDocumetCountMiniDocList());
		int miniDocListCount = getDocumetCountMiniDocList().WaitUntilPresent().size();
		getDocView_MiniDocListIds(miniDocListCount).waitAndClick(5);
	}
	/**
	 * @author Iyappan.Kasinathan
	 */
	public void verifyNavigationOfDocAfterUnCompleteTheDoc() {
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_CurrentDocId());
		String currentDocId=getDocView_CurrentDocId().getText();		
		completeButton();
		base.waitForElement(getMiniDocClick(currentDocId));
		getMiniDocClick(currentDocId).waitAndClick(10);
		getUnCompleteButton().waitAndClick(10);
		if(getUnCompleteBtnNotPresent().isElementAvailable(5)) {
			base.passedStep("Document uncompleted successfully");
		}else {
			base.failedStep("Document not uncompleted successfully");
		}
		softAssertion.assertEquals(currentDocId, getDocView_CurrentDocId().getText());
		softAssertion.assertAll();
		base.passedStep("After uncompleting the document it doesn't automatically move to next document");
		
	}
	/**
	 * @author Iyappan.Kasinathan
	 */
	public void clickCodeSameAsLastAndVerifyNavigatedToNextDoc() {
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_CurrentDocId());
		String currentDocId=getDocView_CurrentDocId().getText();
		base.waitForElement(getCodeSameAsLast());
		getCodeSameAsLast().waitAndClick(10);
		base.stepInfo("Coded as per the coding form for the previous document");
		base.stepInfo("Code same as last icon clicked");
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_CurrentDocId());
		String docId = getDocView_CurrentDocId().getText();
		softAssertion.assertNotEquals(currentDocId, docId);
		softAssertion.assertAll();
		base.passedStep("Cursor has moved to the next document in mini doc list..");
		
		
	}
	
	/**
	 * @author Gopinath
	 * Description: Reusable method for clicking the checkbox and perform codesameas and verify chain link from child window
	 * @param parentwindow(for verify success message in parent window
	 */
	public void clickCheckBoxDocListActionCodeSameAsOnChildWindow(String parentwindow) {
		driver.waitForPageToBeReady();
		base.waitForElementCollection(getDocumetCountMiniDocList());
		for (int i = 1; i <= 3; i++) {
			getDocView_MiniDoc_ChildWindow_Selectdoc(i).waitAndClick(5);;
		}
		base.waitForElement(getDocView_Mini_ActionButton());
		getDocView_Mini_ActionButton().waitAndClick(5);
		base.waitForElement(getDocView__ChildWindow_Mini_CodeSameAs());
		getDocView__ChildWindow_Mini_CodeSameAs().waitAndClick(5);
		driver.switchTo().window(parentwindow);
		base.VerifySuccessMessage("Code same performed successfully.");
		switchTochildWindow();
		geDocView_MiniList_CodeSameAsIcon().WaitUntilPresent().ScrollTo();
		softAssertion.assertEquals(geDocView_MiniList_CodeSameAsIcon().isDisplayed().booleanValue(), true);
	}
	/**
	 * @author Aathith.Senthilkumar
	 * @Description open a mini Doclist child window
	 */
	public void clickGearIconMiniDocListChildWindow() {
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_EditMode());
		base.waitTillElemetToBeClickable(getDocView_EditMode());
		getDocView_EditMode().waitAndClick(10);
		
		DocViewPage doc = new DocViewPage(driver);
		base.waitForElement(doc.getDocView_ChildWindowPopOut());
		base.waitTillElemetToBeClickable(doc.getDocView_ChildWindowPopOut());
		doc.getDocView_ChildWindowPopOut().waitAndClick(10);
		
	}
}
