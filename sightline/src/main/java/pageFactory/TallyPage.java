package pageFactory;

import java.util.concurrent.Callable;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import testScriptsSmoke.Input;

public class TallyPage {

	Driver driver;
	BaseClass base;
	final DocListPage dp;

	public Element getAutoSelectedSearchSource() {
		return driver.FindElementByXPath("//li[contains(text(),'Documents: Selected Documents from Search')]");
	}

	public Element getTally_SelectSource() {
		return driver.FindElementById("select-source");
	}

	public Element getTally_SecurityGroupsButton() {
		return driver.FindElementByXPath("//strong[contains(.,'Security Groups')]");
	}

	public Element getTally_SelectSourceGroup() {
		return driver.FindElementById("secgroup");
	}

	public ElementCollection getfindSearchRow() {
		return driver.FindElementsByXPath("//table[@id='GridSchedulerExecutionList']//td[2]");
	}

	public Element getPopupYesBtn() {
		return driver.FindElementByXPath("//button[@id='btnYes']");
	}

	public Element getTally_SelectSecurityGroup() {
		return driver.FindElementByXPath(".//*[@id='secgroup']/li[contains(.,'Default Security Group')]/label/i");
	}

	public Element getTally_SaveSelections() {
		return driver.FindElementByXPath("//button[@id='secgroup']");
	}

	public Element getTally_SelectaTallyFieldtoruntallyon() {
		return driver.FindElementById("select-source1");
	}

	public Element getTally_Metadataselect() {
		return driver.FindElementById("metadataselect");
	}

	public Element getTally_btnTallyApply() {
		return driver.FindElementById("btnTallyApply");
	}

	public Element getTally_btnTallyAll() {
		return driver.FindElementById("btnTallyAll");
	}

	public Element getTally_tallyactionbtn() {
		return driver.FindElementById("tallyactionbtn");
	}

	public Element getTally_actionSubTally() {
		return driver.FindElementById("actionSubTally");
	}

	public Element getTally_SourceSubTally() {
		return driver.FindElementById("select-source21");
	}

	public Element getTally_btnSubTallyApply() {
		return driver.FindElementById("btnSubTallyApply");
	}

	public Element getTally_btnSubTallyAll() {
		return driver.FindElementById("btnSubTallyAll");
	}

	public Element getTally_SubMetadata() {
		return driver.FindElementById("subMetadata");
	}

	public Element getTally_submetadataselect() {
		return driver.FindElementById("submetadataselect");
	}

	public Element getTally_SubTally_Action_ViewButton() {
		return driver.FindElementByXPath(".//*[@id='freezediv']//a[contains(.,'View')]");
	}

	public Element getTally_subMetadata() {
		return driver.FindElementByXPath(".//*[@id='accordion2']//strong[contains(.,'METADATA')]");
	}

	public Element getTally_SubTally_Action_ViewDocList() {
		return driver.FindElementById("idSubTallyViewDoclist");
	}

	public Element getTally_LoadingScreen() {
		return driver.FindElementByXPath("//span[@class='LoadImagePosition']/img[@src='/img/loading.gif']");
	}

	public Element getTallyContinue() {
		return driver.FindElementByXPath("//*[@id='bot1-Msg1']");
	}

	// bulk actions
	public Element getTally_SubTallyActionButton() {
		return driver.FindElementById("subtallyactionbtn");
	}

	public Element getBulkTagAction(int i) {
		return driver.FindElementByXPath("(//a[contains(text(),'Bulk Tag')])[" + i + "]");
	}

	public Element getBulkActionButton() {
		return driver.FindElementById("idAction");
	}

	public Element getTagsAllRoot() {
		return driver.FindElementByXPath("//*[@id='tagsJSTree']//*[@id='-1_anchor']");
	}

	public Element getEnterTagName() {
		return driver.FindElementById("txtTagName");
	}

	public Element getBulkFolderAction(int i) {
		return driver.FindElementByXPath("(//a[contains(text(),'Bulk Folder')])[" + i + "]");
	}

	public Element getBulkNewTab() {
		return driver.FindElementById("tabNew");
	}

	public Element getBulkTagConfirmationButton() {
		return driver.FindElementByXPath("//button[contains(text(),'Ok')]");
	}

	public Element getFolderAllRoot() {
		return driver.FindElementByXPath("//*[@id='folderJSTree']//*[@id='-1_anchor']");
	}

	public Element getEnterFolderName() {
		return driver.FindElementById("txtFolderName");
	}

	public Element getContinueCount() {
		return driver.FindElementByXPath("//div[@class='bulkActionsSpanLoderTotal']");
	}

	public Element getContinueButton() {
		return driver.FindElementByXPath(".//*[@id='divBulkAction']//button[contains(.,'Continue')]");
	}

	public Element getFinalCount() {
		return driver.FindElementByXPath("//span[@id='spanTotal']");
	}

	public Element getFinalizeButton() {
		return driver.FindElementById("btnfinalizeAssignment");
	}

	public Element getView() {
		return driver.FindElementByXPath(
				"//div[@class='col-md-6']//div[@class='col-md-3 text-right']//li[@class='dropdown-submenu']/a[contains(text(),'View')]");
	}

	public Element getDocListView() {
		return driver.FindElementById("idViewDoclist");
	}

	public Element getDocViewView() {
		return driver.FindElementById("idViewInDocview");
	}

	public Element getBulkAssignAction(int i) {
		return driver.FindElementByXPath("(//a[contains(.,'Bulk Assign')])[" + i + "]");
	}

	public ElementCollection getElements() {
		return driver.FindElementsByXPath("//*[@class='a-menu']");
	}

	public Element getQuickBatchAction(int i) {
		return driver.FindElementByXPath("(//a[contains(text(),'Quick Batch')])[" + i + "]");
	}

	// Added by Gopinath
	public Element getProjectSource() {
		return driver.FindElementByXPath("//strong[text()='Projects']");
	}

	public Element getProjectCheckbox() {
		return driver.FindElementByXPath("//ul[@class='list-unstyled select-project project']//i");
	}

	public Element getProjectSaveButton() {
		return driver.FindElementByXPath("//button[@id='project']");
	}

	public Element getSubTallyField() {
		return driver.FindElementByXPath("//div[@id='metadataDiv2']//button");
	}

	public Element getSubTallyApplyButton() {
		return driver.FindElementById("btnSubTallyApply");
	}

	public Element getSubTagsCheckBox() {
		return driver.FindElementByXPath("//strong[text()='Tags']//..//..//input[@name='sub-metadata']//..//i");
	}

	public Element getSubTagByName(String tagName) {
		return driver.FindElementByXPath("//a[@data-content = '" + tagName + "']");
	}

	public Element getDocumentsCount() {
		return driver.FindElementByXPath("//table[@class='table table-striped dataGrid']/tbody[1]/tr[1]/td[2]");
	}

	public Element getTagsBoard() {
		return driver.FindElementById("_subtagsTree");
	}

	// Added by Gopinath - 06/10/2021
	public Element getTallyTagRadioButton() {
		return driver.FindElementByXPath("//input[@id='tags']/following-sibling::i");
	}

	public Element getTag(String tagName) {
		return driver.FindElementByXPath("//a[@data-content='" + tagName + "']");
	}

	public Element getTallyChartRectbar() {
		return driver.FindElementByCssSelector("rect:nth-child(1)");
	}

	// Added by raghu
	public Element getTallyCount() {
		return driver.FindElementByXPath("(//*[name()='svg']//*[name()='text' and @dy='70'])[last()]");
	}

	// Added by Jayanthi
	public Element getTallyReport() {
		return driver.FindElementByXPath("//div[@id='collapseOne']//a[contains(.,'Tally Report')]");
	}

	public Element getTally_Searches() {
		return driver.FindElementByXPath("//strong[text()='Searches']/parent::a//span[@class='fa fa-plus']");
	}

	public Element getTally_SelectSearches(String searchName) {
		return driver.FindElementByXPath(
				"//a[@class='jstree-anchor' and text()='" + searchName + "']//i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getTally_SearchSaveSelections() {
		return driver.FindElementByXPath("//button[@id='search']");
	}

	public Element getTallyBulkReleaseAction() {
		return driver.FindElementByXPath(
				"//button[@id='tallyactionbtn']/following-sibling::ul//li//a[contains(.,'Bulk Release')]");
	}

	public Element getBulkRelease_ButtonRelease() {
		return driver.FindElementById("btnRelease");
	}

	public Element getBulkRelDefaultSecurityGroup_CheckBox(String SG) {
		return driver.FindElementByXPath("//form[@id='Edit User Group']//div[text()='" + SG + "']/../div[1]/label/i");
	}

	public Element getTotalSelectedDocs() {
		return driver.FindElementByXPath("//span[@id='spanTotal']");
	}
	public Element getTallyViewBtn() {
		return driver.FindElementByXPath("//button[@id='tallyactionbtn']/following-sibling::ul[@class='dropdown-menu action-dd']//li//a[text()='View']");
	}
	public Element getTallyViewinDocViewBtn() {
		return driver.FindElementByXPath("//ul[@Class='dropdown-menu']//li//a[@id='idViewInDocview']");
	}
	public Element getSubTallyBulkReleaseAction() {
		return driver.FindElementByXPath("//button[@id='subtallyactionbtn']/following-sibling::ul//li//a[contains(.,'Bulk Release')]");
	}
	public TallyPage(Driver driver) {

		this.driver = driver;
		dp = new DocListPage(driver);
	//	this.driver.getWebDriver().get(Input.url + "Report/Tally");

		base = new BaseClass(driver);

	}

	public void validateTally() {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SelectSource().Visible();
			}
		}), Input.wait30);
		getTally_SelectSource().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SecurityGroupsButton().Visible();
			}
		}), Input.wait30);
		getTally_SecurityGroupsButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SelectSecurityGroup().Visible();
			}
		}), Input.wait30);
		getTally_SelectSecurityGroup().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SaveSelections().Visible();
			}
		}), Input.wait30);
		getTally_SaveSelections().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SelectaTallyFieldtoruntallyon().Visible();
			}
		}), Input.wait30);
		getTally_SelectaTallyFieldtoruntallyon().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_Metadataselect().Visible();
			}
		}), Input.wait30);
		getTally_Metadataselect().selectFromDropdown().selectByVisibleText("CustodianName");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_btnTallyApply().Visible();
			}
		}), Input.wait30);
		getTally_btnTallyApply().Click();

		BaseClass bc = new BaseClass(driver);
		// pop up may appear multiple times depends on app response
		bc.yesPopUp();
		bc.yesPopUp();

	}

	public void tallyActions() {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_btnTallyAll().Visible();
			}
		}), Input.wait30);

		getTally_btnTallyAll().ScrollTo();
		getTally_btnTallyAll().waitAndClick(30);
        driver.scrollingToElementofAPage(getTally_tallyactionbtn());
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_tallyactionbtn().Visible();
			}
		}), Input.wait30);
		getTally_tallyactionbtn().waitAndClick(10);
	}

	public void bulkAssign(final int tally1subtally2) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkAssignAction(tally1subtally2).Visible();
			}
		}), Input.wait60);

		getBulkAssignAction(tally1subtally2).Click();

		System.out.println("performing bulk assign");

	}

	public void bulkTag(String TagName, final int tally1subtally2) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkTagAction(tally1subtally2).Visible();
			}
		}), Input.wait30);
		getBulkTagAction(tally1subtally2).Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkNewTab().Visible();
			}
		}), Input.wait60);

		getBulkNewTab().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEnterTagName().Visible();
			}
		}), Input.wait60);
		getEnterTagName().SendKeys(TagName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTagsAllRoot().Visible();
			}
		}), Input.wait60);
		getTagsAllRoot().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getContinueButton().Click();

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getFinalizeButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkTagConfirmationButton().Visible();
			}
		}), Input.wait60);
		getBulkTagConfirmationButton().Click();

		base.VerifySuccessMessage("Records saved successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		System.out.println("Bulk Tag is done, Tag is : " + TagName);

	}

	public void bulkFolder(String folderName, final int tally1subtally2) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkFolderAction(tally1subtally2).Visible();
			}
		}), Input.wait60);

		getBulkFolderAction(tally1subtally2).waitAndClick(20);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkNewTab().Visible();
			}
		}), Input.wait60);

		getBulkNewTab().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEnterFolderName().Visible();
			}
		}), Input.wait60);
		getEnterFolderName().SendKeys(folderName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderAllRoot().Visible();
			}
		}), Input.wait60);
		getFolderAllRoot().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getContinueButton().Click();

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		getFinalizeButton().Click();

		base.VerifySuccessMessage("Records saved successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		System.out.println("Bulk folder is done, folder is : " + folderName);

	}

	public void validateSubTally() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SourceSubTally().Visible();
			}
		}), Input.wait30);
		Thread.sleep(3000);
		getTally_SourceSubTally().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_subMetadata().Visible();
			}
		}), Input.wait30);
		getTally_subMetadata().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_submetadataselect().Visible();
			}
		}), Input.wait30);
		getTally_submetadataselect().selectFromDropdown().selectByVisibleText("DocFileExtension");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_btnSubTallyApply().Visible();
			}
		}), Input.wait30);
		getTally_btnSubTallyApply().Click();

		// pop up may appear multiple times depends on app response
		base.yesPopUp();
		base.yesPopUp();

	}

	public void subTallyActions() {
		getTally_btnSubTallyAll().waitAndClick(30);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SubTallyActionButton().Visible();
			}
		}), Input.wait30);
		getTally_SubTallyActionButton().waitAndClick(25);

	}

	public void ValidateTallySubTally() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SelectSource().Visible();
			}
		}), Input.wait30);
		getTally_SelectSource().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SecurityGroupsButton().Visible();
			}
		}), Input.wait30);
		getTally_SecurityGroupsButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SelectSecurityGroup().Visible();
			}
		}), Input.wait30);
		getTally_SelectSecurityGroup().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SaveSelections().Visible();
			}
		}), Input.wait30);
		getTally_SaveSelections().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SelectaTallyFieldtoruntallyon().Visible();
			}
		}), Input.wait30);
		getTally_SelectaTallyFieldtoruntallyon().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_Metadataselect().Visible();
			}
		}), Input.wait30);
		getTally_Metadataselect().selectFromDropdown().selectByVisibleText("CustodianName");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_btnTallyApply().Visible();
			}
		}), Input.wait30);
		getTally_btnTallyApply().Click();

		base.yesPopUp();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_btnTallyAll().Visible();
			}
		}), Input.wait30);

		getTally_btnTallyAll().ScrollTo();
		getTally_btnTallyAll().waitAndClick(30);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_tallyactionbtn().Visible();
			}
		}), Input.wait30);
		getTally_tallyactionbtn().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_actionSubTally().Visible();
			}
		}), Input.wait30);
		getTally_actionSubTally().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SourceSubTally().Visible();
			}
		}), Input.wait30);
		Thread.sleep(3000);
		getTally_SourceSubTally().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_subMetadata().Visible();
			}
		}), Input.wait30);
		getTally_subMetadata().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_submetadataselect().Visible();
			}
		}), Input.wait30);
		getTally_submetadataselect().selectFromDropdown().selectByVisibleText("DocFileExtension");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_btnSubTallyApply().Visible();
			}
		}), Input.wait30);
		getTally_btnSubTallyApply().Click();

		base = new BaseClass(driver);
		base.yesPopUp();

		getTally_btnSubTallyAll().waitAndClick(30);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SubTallyActionButton().Visible();
			}
		}), Input.wait30);
		getTally_SubTallyActionButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SubTally_Action_ViewButton().Visible();
			}
		}), Input.wait30);

		getTally_SubTally_Action_ViewButton().ScrollTo();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SubTally_Action_ViewDocList().Visible();
			}
		}), Input.wait30);
		getTally_SubTally_Action_ViewDocList().Click();

		try {
			getTallyContinue().waitAndClick(10);
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("Navigated to Doclist page");
		UtilityLog.info("Navigated to Doclist page");

		// dp.getDocList_info().WaitUntilPresent();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return !dp.getDocList_info().getText().isEmpty();
			}
		}), Input.wait30);
		Assert.assertTrue(dp.getDocList_info().getText().toString().contains("Showing 1 to 10 of"));
		System.out.println("Docs are shown in doclist");
		UtilityLog.info("Docs are shown in doclist");

	}

	public void ValidateTallySubTally_QuickBatch() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SelectSource().Visible();
			}
		}), Input.wait30);
		getTally_SelectSource().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SecurityGroupsButton().Visible();
			}
		}), Input.wait30);
		getTally_SecurityGroupsButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SelectSecurityGroup().Visible();
			}
		}), Input.wait30);
		getTally_SelectSecurityGroup().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SaveSelections().Visible();
			}
		}), Input.wait30);
		getTally_SaveSelections().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SelectaTallyFieldtoruntallyon().Visible();
			}
		}), Input.wait30);
		getTally_SelectaTallyFieldtoruntallyon().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_Metadataselect().Visible();
			}
		}), Input.wait30);
		getTally_Metadataselect().selectFromDropdown().selectByVisibleText("CustodianName");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_btnTallyApply().Visible();
			}
		}), Input.wait30);
		getTally_btnTallyApply().Click();

		base.yesPopUp();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_btnTallyAll().Visible();
			}
		}), Input.wait30);

		getTally_btnTallyAll().ScrollTo();
		getTally_btnTallyAll().waitAndClick(30);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_tallyactionbtn().Visible();
			}
		}), Input.wait30);
		getTally_tallyactionbtn().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_actionSubTally().Visible();
			}
		}), Input.wait30);
		getTally_actionSubTally().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SourceSubTally().Visible();
			}
		}), Input.wait30);
		Thread.sleep(3000);
		getTally_SourceSubTally().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_subMetadata().Visible();
			}
		}), Input.wait30);
		getTally_subMetadata().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_submetadataselect().Visible();
			}
		}), Input.wait30);
		getTally_submetadataselect().selectFromDropdown().selectByVisibleText("DocFileExtension");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_btnSubTallyApply().Visible();
			}
		}), Input.wait30);
		getTally_btnSubTallyApply().Click();

		base = new BaseClass(driver);
		base.yesPopUp();

		getTally_btnSubTallyAll().waitAndClick(30);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTally_SubTallyActionButton().Visible();
			}
		}), Input.wait30);
		getTally_SubTallyActionButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getQuickBatchAction(2).Visible();
			}
		}), Input.wait30);

		getQuickBatchAction(2).waitAndClick(10);

	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for selecting source by project..
	 */
	public void selectSourceByProject() {
		try {
			getTally_SelectSource().Click();
			getProjectSource().Click();
			for (int i = 0; i < 20; i++) {
				try {
					getProjectCheckbox().Click();
					break;
				} catch (Exception e) {
					base.waitForElement(getProjectCheckbox());
				}
			}
			getProjectSaveButton().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while selecting project in tally page" + e.getMessage());
		}

	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for selecting tally by meta data field..
	 * @param fieldName : (fieldName is the name of meta data field)
	 */
	public void selectTallyByMetaDataField(String fieldName) {
		try {
			base.waitForElement(getTally_SelectaTallyFieldtoruntallyon());
			base.waitTillElemetToBeClickable(getTally_SelectaTallyFieldtoruntallyon());
			getTally_SelectaTallyFieldtoruntallyon().Click();
			base.waitForElement(getTally_Metadataselect());
			base.waitTillElemetToBeClickable(getTally_Metadataselect());
			getTally_Metadataselect().selectFromDropdown().selectByVisibleText(fieldName);
			base.waitForElement(getTally_btnTallyApply());
			base.waitTillElemetToBeClickable(getTally_btnTallyApply());
			getTally_btnTallyApply().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while selecting tally by meta data field" + e.getMessage());
		}

	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for selecting subtally from action dropdown..
	 */
	public void selectSubTallyFromActionDropDown() {
		try {
			base.waitForElement(getTally_actionSubTally());
			base.waitTillElemetToBeClickable(getTally_actionSubTally());
			getTally_actionSubTally().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while selecting sub tally from action drop down" + e.getMessage());
		}

	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for selecting subtally field from tally..
	 * @param tagName : (tagName is a string value that name of tag)
	 */
	public void applyingSubTallyField(String tagName) {
		try {
			base.waitForElement(getSubTallyField());
			base.waitTillElemetToBeClickable(getSubTallyField());
			getSubTallyField().Click();
			base.waitTillElemetToBeClickable(getSubTagsCheckBox());
			getSubTagsCheckBox().Click();
			base.waitForElement(getTagsBoard());
			getTagsBoard().Click();
			base.waitForElement(getSubTagByName(tagName));
			base.waitTillElemetToBeClickable(getSubTagByName(tagName));
			getSubTagByName(tagName).Click();
			driver.scrollPageToTop();
			base.waitForElement(getSubTallyApplyButton());
			base.waitTillElemetToBeClickable(getSubTallyApplyButton());
			getSubTallyApplyButton().Click();

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while applying sub tally field" + e.getMessage());
		}

	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for verifying documents count by tag name subtally..
	 * @param tagName : (tagName is a string value that name of tag)
	 */
	public void verifyDocumentsCountByTagNameSubTally(String tagName) {
		try {
			driver.scrollingToBottomofAPage();
			base.waitForElement(getDocumentsCount());
			int count = Integer.parseInt(getDocumentsCount().getWebElement().getText().trim());
			String tag = getDocumentsCount().getWebElement().getAttribute("data-y").trim();
			if (count > 0) {
				base.passedStep("Tag : " + tag + " with document count : " + count);
			} else {
				base.failedStep("Failed to get count of documents");
			}

			if (tag.equalsIgnoreCase(tagName)) {
				base.passedStep("Tag : " + tag + " with document count : " + count + " displayed successfully");
			} else {
				base.failedStep(tagName + " with count is not displayed");
			}

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying documents count by tagname subtally" + e.getMessage());
		}

	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for selecting tally by tag name..
	 * @param tagName : (tagName is the name of meta data field)
	 */
	public void selectTallyByTagName(String tagName) {
		try {
			base.waitForElement(getTally_SelectaTallyFieldtoruntallyon());
			base.waitTillElemetToBeClickable(getTally_SelectaTallyFieldtoruntallyon());
			getTally_SelectaTallyFieldtoruntallyon().Click();
			base.waitForElement(getTallyTagRadioButton());
			base.waitTillElemetToBeClickable(getTallyTagRadioButton());
			getTallyTagRadioButton().Click();
			base.waitForElement(getTag(tagName));
			base.waitTillElemetToBeClickable(getTag(tagName));
			getTag(tagName).Click();
			base.waitForElement(getTally_btnTallyApply());
			base.waitTillElemetToBeClickable(getTally_btnTallyApply());
			getTally_btnTallyApply().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while selecting tally by meta data field" + e.getMessage());
		}

	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for verify tally chart rect is displayed or not..
	 * @param flag : (flag is the boolean value that tally chart neeed to display or
	 *             not).
	 */
	public void verifyTallyChartRectIsDisplayed(boolean flag) {
		try {
			base.waitForElement(getTallyChartRectbar());
			base.waitTillElemetToBeClickable(getTallyChartRectbar());
			getTallyChartRectbar().Displayed();
			try {
				if (getTallyChartRectbar().Displayed() && flag) {
					base.passedStep("Tally chart rect displayed successfully");
					base.passedStep("Filter applied successfully");
				}
			} catch (Exception e) {
				if (flag == false) {
					base.passedStep("Tally chart rect not displayed successfully");
				} else {
					base.failedStep("Tally chart rect not displayed");
					base.failedStep("Falied to apply filters");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying tally chart rect is displayed or not" + e.getMessage());
		}

	}
/**
 * @author Jayanthi.ganesan
 */
	public void navigateTo_Tallypage() {
		this.driver.getWebDriver().get(Input.url + "Report/ReportsLanding");
		base.waitForElement(getTallyReport());
		getTallyReport().Click();
		base.stepInfo("Navigating to tally page");
		driver.waitForPageToBeReady();
	}
	/**
	 * @author Jayanthi.ganesan
	 */
	public void SelectSource_SavedSearch(String saveSearch) {
		base.waitForElement(getTally_SelectSource());
		getTally_SelectSource().Click();
		base.waitForElement(getTally_Searches());
		getTally_Searches().Click();
		driver.scrollingToElementofAPage(getTally_SelectSearches(saveSearch));
		getTally_SelectSearches(saveSearch).waitAndClick(5);
		driver.scrollingToElementofAPage(getTally_SearchSaveSelections());
		base.waitForElement(getTally_SearchSaveSelections());
		getTally_SearchSaveSelections().Click();
		base.stepInfo("Selected "+saveSearch+" as source to generate tally report");
	}
	/**
	  * @author Jayanthi.ganesan
	  */
	public String bulkRelease(String SG,boolean subtally) {
		if(subtally) {
			base.waitForElement(getSubTallyBulkReleaseAction());
			getSubTallyBulkReleaseAction().Click();
		}
		else {
			base.waitForElement(getTallyBulkReleaseAction());
			getTallyBulkReleaseAction().Click();
		}
		getBulkRelDefaultSecurityGroup_CheckBox(SG).Click();
		base.waitForElement(getBulkRelease_ButtonRelease());
		getBulkRelease_ButtonRelease().waitAndClick(20);
		base.waitForElement(getTotalSelectedDocs());
		String TotalDocs = getTotalSelectedDocs().getText();
		base.waitForElement(getFinalizeButton());
		getFinalizeButton().waitAndClick(20);
		base.VerifySuccessMessageB("Records saved successfully");
		base.stepInfo("performing bulk release for "+SG+" docs count is " + TotalDocs);
		return TotalDocs;

	}
	/**
	 * @author Jayanthi.ganesan
	 * @param subtallyBy
	 * @throws InterruptedException
	 */
	public void selectMetaData_SubTally(String subtallyBy) throws InterruptedException {
		base.waitForElement(getSubTallyField());
		base.waitTillElemetToBeClickable(getSubTallyField());
		getSubTallyField().Click();
		base.waitForElement( getTally_subMetadata());
		getTally_subMetadata().Click();
		base.waitForElement(getTally_submetadataselect());
		getTally_submetadataselect().selectFromDropdown().selectByVisibleText(subtallyBy);
		base.waitForElement(getTally_btnSubTallyApply());			
		getTally_btnSubTallyApply().Click();
	}
	/**
	 * @author Jayanthi.ganesan
	 */
	public void Tally_ViewInDocView() {
		base.waitForElement(getTallyViewBtn());
		getTallyViewBtn().ScrollTo();
		getTallyViewinDocViewBtn().ScrollTo();
		getTallyViewinDocViewBtn().waitAndClick(30);
		base.stepInfo("Navigating from Tally page to view in doc view page.");
	}

}