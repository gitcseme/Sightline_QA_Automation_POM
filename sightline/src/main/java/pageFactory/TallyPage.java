package pageFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
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
		return driver.FindElementByXPath("//select[@id='submetadataselect']");
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
		return driver.FindElementByXPath(
				"//button[@id='tallyactionbtn']/following-sibling::ul[@class='dropdown-menu action-dd']//li//a[text()='View']");
	}

	public Element getTallyViewinDocViewBtn() {
		return driver.FindElementByXPath("//ul[@Class='dropdown-menu']//li//a[@id='idViewInDocview']");
	}

	public Element getSubTallyBulkReleaseAction() {
		return driver.FindElementByXPath(
				"//button[@id='subtallyactionbtn']/following-sibling::ul//li//a[contains(.,'Bulk Release')]");
	}

	public Element getTally_SecurityGroupCheckBox(String checkBoxName) {
		return driver.FindElementByXPath("//*[@class='checkbox']//span[text()='" + checkBoxName + "']");
	}

	public Element getTally_SearchesCheckBox(String checkBoxName) {
		return driver.FindElementByXPath("//*[@id='divSearchGroupTree']//*[text()='" + checkBoxName + "']");
	}

	public Element getTally_AssignmentsCheckBox(String checkBoxName) {
		return driver.FindElementByXPath("//*[@id='divAssignmentGroupTree']//*[text()='" + checkBoxName + "']");
	}

	public Element getTally_FoldersCheckBox(String checkBoxName) {
		return driver.FindElementByXPath("//*[@id='divFolderGroupTree']//*[text()='" + checkBoxName + "']");
	}

	public Element getTally_PrjCheckBox(String checkBoxName) {
		return driver.FindElementByXPath("//span[text()='" + checkBoxName + "']/parent::label/i");
	}

	public Element getTally_ClickMetaSourceButton() {
		return driver.FindElementByXPath("//*[@id='select-source1']");
	}

	public Element getTally_ClickMetaSourceDropDown() {
		return driver.FindElementByXPath("//*[@id='metadataselect']");
	}

	public Element getTally_SelectMetaSourceDropDown(String options) {
		return driver.FindElementByXPath("//*[@id='metadataselect']/option[text()='" + options + "']");
	}

	public Element getTally_assignSaveSelections() {
		return driver.FindElementByXPath("//button[@id='assignment']");
	}

	public Element getTally_SGSaveSelections() {
		return driver.FindElementByXPath("//button[@id='secgroup']");
	}

	public Element getTally_foldersSaveSelections() {
		return driver.FindElementByXPath("//button[@id='folder']");
	}

	public Element getTally_PrjSaveSelections() {
		return driver.FindElementByXPath("//button[@id='project']");
	}

	public Element getTally_Assignments() {
		return driver.FindElementByXPath("//strong[text()=' Assignments']/parent::a//span[@class='fa fa-plus']");
	}

	public Element getTally_Folders() {
		return driver.FindElementByXPath("//strong[text()='Folders']/parent::a//span[@class='fa fa-plus']");
	}

	public Element getTally_Projects() {
		return driver.FindElementByXPath("//strong[text()='Projects']/parent::a//span[@class='fa fa-plus']");
	}

	public Element getTally_SecurityGroup() {
		return driver.FindElementByXPath("//strong[text()='Security Groups']/parent::a//span[@class='fa fa-plus']");
	}

	public ElementCollection getTallyChartMetaData() {
		return driver.FindElementsByCssSelector("div[id='tallyChart'] > svg > g:nth-child(2) > text");
	}

	public Element getTally_SelectedSource() {
		return driver.FindElementByXPath("//ul[@id='bitlist-sources']/li");
	}

	public ElementCollection getDocs_Xaxis() {
		return driver.FindElementsByCssSelector("#tallyChart > svg > g:nth-child(1)>text");
	}

	public ElementCollection getrectValues() {
		return driver.FindElementsByCssSelector("#tallyChart > svg > g:nth-child(3)>rect");
	}

	public ElementCollection getUniqueDocs_Subtally() {
		return driver
				.FindElementsByXPath("//table[@class='table table-striped dataGrid']/tbody/tr/td[@class='rowtotal']");
	}

	public ElementCollection getMetadataList_SubtallyTable() {
		return driver.FindElementsByXPath("//table//tbody//td[@class='text-left rowlead formatDate']");
	}

	public ElementCollection getSubtallyTable() {
		return driver.FindElementsByXPath("//table[@class='table table-striped dataGrid']");
	}

	public ElementCollection getSubtallyTableheader() {
		return driver.FindElementsByXPath("//table[@class='table table-striped dataGrid']/thead/tr/th");
	}
	public Element getSubTallyViewBtn() {
		return driver.FindElementByXPath(
				"//button[@id='subtallyactionbtn']/following-sibling::ul[@class='dropdown-menu action-dd']//li//a[text()='View']");
	}

	public Element getSubTallyViewinDocViewBtn() {
		return driver.FindElementByXPath("//ul[@Class='dropdown-menu']//li//a[@id='idSubTallyViewInDocview']");
	}
	public TallyPage(Driver driver) {

		this.driver = driver;
		dp = new DocListPage(driver);
		// this.driver.getWebDriver().get(Input.url + "Report/Tally");

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
		base.stepInfo("**selecting source as search--" + saveSearch + "**");
		base.waitForElement(getTally_SelectSource());
		getTally_SelectSource().Click();
		base.waitForElement(getTally_Searches());
		getTally_Searches().Click();
		driver.scrollingToElementofAPage(getTally_SelectSearches(saveSearch));
		getTally_SelectSearches(saveSearch).ScrollTo();
		base.waitTime(2);
		getTally_SelectSearches(saveSearch).waitAndClick(5);
		driver.scrollingToElementofAPage(getTally_SearchSaveSelections());
		base.waitForElement(getTally_SearchSaveSelections());
		base.waitTime(2);
		getTally_SearchSaveSelections().Click();
		driver.waitForPageToBeReady();
		verifySourceSelected();
		base.stepInfo("Selected " + saveSearch + " as source to generate tally report");
	}

	/**
	 * @author Jayanthi.ganesan
	 */
	public String bulkRelease(String SG, boolean subtally) {
		if (subtally) {
			base.waitForElement(getSubTallyBulkReleaseAction());
			getSubTallyBulkReleaseAction().Click();
		} else {
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
		base.stepInfo("performing bulk release for " + SG + " docs count is " + TotalDocs);
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
		base.waitForElement(getTally_subMetadata());
		getTally_subMetadata().Click();
		base.waitForElement(getTally_submetadataselect());
		getTally_submetadataselect().selectFromDropdown().selectByVisibleText(subtallyBy);
		base.waitForElement(getTally_subMetadata());
		String actualFieldName = getSubTallyField().getText();
		System.out.println(actualFieldName);
		System.out.println("subMetadata :" + subtallyBy);
		if (actualFieldName.equals("subMetadata :" + subtallyBy)) {
			base.passedStep("Expected Sub MetaData Field Name " + subtallyBy
					+ " Displayed in sub Tally By  sub MetaData Button.");
		} else {
			base.failedStep("Expected SubMetaData Field Name is not Displayed");
		}

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

	/**
	 * @author Jayanthi.ganesan
	 */

	public void SelectSource_Assignment(String assignmentName) {
		base.stepInfo("**Selecting source as Assignment --" + assignmentName + "**");
		base.waitForElement(getTally_SelectSource());
		getTally_SelectSource().Click();
		base.waitForElement(getTally_Assignments());
		getTally_Assignments().Click();
		base.waitTime(2);
		driver.scrollingToElementofAPage(getTally_AssignmentsCheckBox(assignmentName));
		getTally_AssignmentsCheckBox(assignmentName).waitAndClick(15);
		base.waitTime(2);
		getTally_assignSaveSelections().ScrollTo();
		driver.scrollingToElementofAPage(getTally_assignSaveSelections());
		base.waitForElement(getTally_assignSaveSelections());
		base.waitTime(2);
		getTally_assignSaveSelections().waitAndClick(15);
		driver.waitForPageToBeReady();
		verifySourceSelected();
	}

	/**
	 * @author Jayanthi.ganesan
	 */
	public void SelectSource_Folder(String folderName) {
		base.stepInfo("**Selecting source as Folder--" + folderName + "**");
		base.waitForElement(getTally_SelectSource());
		getTally_SelectSource().Click();
		base.waitForElement(getTally_Folders());
		getTally_Folders().Click();
		driver.scrollingToElementofAPage(getTally_FoldersCheckBox(folderName));
		getTally_FoldersCheckBox(folderName).ScrollTo();
		base.waitTime(2);
		getTally_FoldersCheckBox(folderName).waitAndClick(5);
		base.waitTime(2);
		driver.scrollingToElementofAPage(getTally_foldersSaveSelections());
		getTally_foldersSaveSelections().ScrollTo();
		base.waitForElement(getTally_foldersSaveSelections());
		getTally_foldersSaveSelections().Click();
		driver.waitForPageToBeReady();
		verifySourceSelected();
	}

	/**
	 * @author Jayanthi.ganesan
	 */
	public void SelectSource_SecurityGroup(String securityGroup) {
		base.stepInfo("**Selecting source as security group --" + securityGroup + "**");
		base.waitForElement(getTally_SelectSource());
		getTally_SelectSource().Click();
		base.waitForElement(getTally_SecurityGroup());
		getTally_SecurityGroup().Click();
		driver.scrollingToElementofAPage(getTally_SecurityGroupCheckBox(securityGroup));
		base.waitTime(2);
		getTally_SecurityGroupCheckBox(securityGroup).waitAndClick(5);
		base.waitTime(2);
		driver.scrollingToElementofAPage(getTally_SearchSaveSelections());
		base.waitForElement(getTally_SGSaveSelections());
		getTally_SGSaveSelections().ScrollTo();
		getTally_SGSaveSelections().Click();
		driver.waitForPageToBeReady();
		verifySourceSelected();
	}

	/**
	 * @author Jayanthi.ganesan
	 */
	public void validateMetaDataFieldName(String expectedFieldName) {
		try {
			base.waitForElement(getTally_SelectaTallyFieldtoruntallyon());
			base.waitTillElemetToBeClickable(getTally_SelectaTallyFieldtoruntallyon());
			String actualFieldName = getTally_SelectaTallyFieldtoruntallyon().getText();
			if (actualFieldName.equals("Metadata :" + expectedFieldName)) {
				base.passedStep("Expected MetaData Field Name " + expectedFieldName
						+ " Displayed in Tally By  MetaData Button.");
			} else {
				base.failedStep("Expected MetaData Field Name is not Displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while validating tally by meta data field" + e.getMessage());
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @return
	 */
	public List<String> verifyTallyChart() {
		base.waitForElement(getTallyChartRectbar());
		base.waitTillElemetToBeClickable(getTallyChartRectbar());
		getTallyChartRectbar().Displayed();
		List<String> metaData = new ArrayList<String>();
		if (getTallyChartRectbar().Displayed()) {
			base.passedStep("Tally chart  displayed successfully");
			metaData = base.availableListofElements(getTallyChartMetaData());
			System.out.println(metaData);
		} else {
			base.failedStep("Tally chart  not displayed");
		}

		return metaData;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param prjName
	 */
	public void selectSource_Project(String prjName) {
		base.stepInfo("**Selecting source as project --" + prjName + "**");
		base.waitForElement(getTally_SelectSource());
		getTally_SelectSource().Click();
		base.waitForElement(getTally_Projects());
		getTally_Projects().Click();
		base.waitTime(2);
		driver.scrollingToElementofAPage(getTally_PrjCheckBox(prjName));
		getTally_PrjCheckBox(prjName).waitAndClick(15);
		base.waitTime(2);
		getTally_PrjSaveSelections().ScrollTo();
		driver.scrollingToElementofAPage(getTally_PrjSaveSelections());
		base.waitForElement(getTally_PrjSaveSelections());
		base.waitTime(2);
		getTally_PrjSaveSelections().waitAndClick(15);
		driver.waitForPageToBeReady();
		verifySourceSelected();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param i
	 * @param name
	 */
	public void selectSources_PA(int i, String name) {
		if (i == 0)
			selectSource_Project(name);
		if (i == 1)
			SelectSource_Folder(name);
		if (i == 2)
			SelectSource_SavedSearch(name);
		if (i == 3)
			SelectSource_SecurityGroup(name);

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param i
	 * @param name
	 */
	public void selectSources_RMU(int i, String name) {
		if (i == 0)
			SelectSource_Assignment(name);
		if (i == 1)
			SelectSource_Folder(name);
		if (i == 2)
			SelectSource_SavedSearch(name);
		if (i == 3)
			SelectSource_SecurityGroup(name);
	}

	/**
	 * @author Jayanthi.ganesan
	 */
	public void verifySourceSelected() {
		if (getTally_SelectedSource().isElementAvailable(3)) {
			base.passedStep("Source Selected get reflected.");
			String sourceName = getTally_SelectedSource().getText();
		} else {
			base.failedStep("Source Selected Not reflected.");
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @return
	 */
	public int verifyDocCountBarChart() {
		HashMap<String, String> DocCount_BarData = new HashMap<String, String>();
		List<WebElement> elementList = null;
		elementList = getDocs_Xaxis().FindWebElements();
		for (WebElement webElementNames : elementList) {
			String elementName = webElementNames.getText();
			String width = webElementNames.getAttribute("x");
			DocCount_BarData.put(width, elementName);
		}
		System.out.println(DocCount_BarData);
		List<String> elementNames = new ArrayList<>();
		List<WebElement> elementList1 = null;
		elementList1 = getrectValues().FindWebElements();
		for (WebElement webElementNames : elementList1) {
			String width = webElementNames.getAttribute("width");
			elementNames.add(width);
		}
		System.out.println(elementNames);
		List<Integer> docCount = new ArrayList<>();
		for (String ele : elementNames) {
			String temp = DocCount_BarData.get(ele);
			docCount.add(Integer.parseInt(temp));
		}
		System.out.println("From hashmap doc count extracted are" + docCount);
		Integer sum = 0;
		if (docCount == null || docCount.size() < 1) {
			System.out.println("List size is zero");
		} else {
			for (int i : docCount) {
				sum = sum + i;
			}
		}
		System.out.println("List sum up" + sum);
		base.stepInfo("Count of docs reflected in tally by report" + sum.toString());
		return sum;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @return
	 */
	public int verifyDocCountSubTally(int purehit) {
		List<String> metadata_subtally = new ArrayList<>();
		metadata_subtally = base.availableListofElements(getMetadataList_SubtallyTable());
		List<String> metadata_tally = new ArrayList<>();
		metadata_tally = base.availableListofElements(getTallyChartMetaData());
		if (metadata_subtally.containsAll(metadata_tally)) {
			base.passedStep("Metadata list displayed in subtally report table is as expected");

		} else {
			base.failedStep("Metadata list displayed in subtally report table is not as expected");
		}
		Integer sum = 0;
		if (getSubtallyTable().isElementAvailable(10)) {
			List<WebElement> elementList = null;
			List<Integer> docCount = new ArrayList<>();
			elementList = getUniqueDocs_Subtally().FindWebElements();
			for (WebElement webElementNames : elementList) {
				String elementName = webElementNames.getText();
				docCount.add(Integer.parseInt(elementName));
			}
			System.out.println(docCount);
			if (docCount == null || docCount.size() < 1) {
				System.out.println("List size is zero");
			} else {
				for (int i : docCount) {
					sum = sum + i;
				}
			}
			base.stepInfo("Total Count of Unique docs reflected in sub tally Table" + sum.toString());
			if (sum == purehit) {
				base.passedStep(
						"Total count of unique docs was as expected which is same as number of docs selected for tally by");
			} else {
				base.failedStep("Total count of unique docs was not as expected ");

			}
		} else {
			base.failedStep("sub tally report not displayed.");
		}
		return sum;
	}
	/**
	 * @author Jayanthi.ganesan
	 */
	public void SubTally_ViewInDocView() {
		base.waitForElement(getSubTallyViewBtn());
		driver.scrollingToBottomofAPage();
		base.waitTime(1);
		getSubTallyViewBtn().ScrollTo();
		getSubTallyViewBtn().ScrollTo();
		getSubTallyViewinDocViewBtn().ScrollTo();
		getSubTallyViewinDocViewBtn().waitAndClick(30);
		base.stepInfo("Navigating from Tally page to view in doc view page.");
	}
	public void sourceSelectionUsers(String role, String[] sourceArray,int i) {
		if(role=="PA") {
	selectSources_PA(i,sourceArray[i]);}
		if(role=="RMU") {
	selectSources_RMU(i,sourceArray[i]);}
}
	/**
	 * @author Jayanthi.ganesan
	 * @return
	 */
	public String verifyTallyChartMetadata() {
		base.waitForElement(getTallyChartRectbar());
		base.waitTillElemetToBeClickable(getTallyChartRectbar());
		getTallyChartRectbar().Displayed();
		List<WebElement> elementList = null;
		List<String> elementNames = new ArrayList<>();
		
		if (getTallyChartRectbar().Displayed()) {
			base.passedStep("Tally chart  displayed successfully");
			elementList = getTallyChartMetaData().FindWebElements();
			for (WebElement webElementNames : elementList) {
				String elementName = webElementNames.getText();
				elementNames.add(elementName.toLowerCase());
			}
		} else {
			base.failedStep("Tally chart  not displayed");
		}	
		System.out.println(elementNames);
		Collections.sort(elementNames);
		
		return elementNames.toString();
	
	}
	/**
	 * @author Jayanthi.ganesan
	 * @return
	 */
	public int getDocCountSubTally() {
		Integer sum = 0;
		if (getSubtallyTable().isElementAvailable(10)) {
			List<WebElement> elementList = null;
			List<Integer> docCount = new ArrayList<>();
			elementList = getUniqueDocs_Subtally().FindWebElements();
			for (WebElement webElementNames : elementList) {
				String elementName = webElementNames.getText();
				docCount.add(Integer.parseInt(elementName));
			}
			System.out.println(docCount);
			if (docCount == null || docCount.size() < 1) {
				System.out.println("List size is zero");
			} else {
				for (int i : docCount) {
					sum = sum + i;
				}
			}
			base.stepInfo("Total Count of Unique docs reflected in sub tally Table" + sum.toString());
		
		} else {
			base.failedStep("sub tally report not displayed.");
		}
		return sum;
	}
}