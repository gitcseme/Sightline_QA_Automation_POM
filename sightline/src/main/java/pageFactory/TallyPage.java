package pageFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
		return driver.FindElementByXPath("//a[contains(@class,'jstree-anchor') and text()='" + searchName
				+ "']//i[@class='jstree-icon jstree-checkbox']");
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
		return driver.FindElementByXPath("//ul[contains(@Class,'dropdown-menu')]//li//a[@id='idViewInDocview']");
	}

	public Element getTallyViewinDocListBtn() {
		return driver.FindElementById("idViewDoclist");
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
		return driver
				.FindElementByXPath("//ul[contains(@Class,'dropdown-menu')]//li//a[@id='idSubTallyViewInDocview']");
	}

	public Element clearingActiveFiltersInTallyBy() {
		return driver
				.FindElementByXPath("//strong[text()='ACTIVE FILTERS:']/parent::div//i[@class='fa fa-times-circle']");
	}

	public Element metaDataFilterForTallyBy(String metaData) {
		return driver.FindElementByXPath("//ul[@id='optionFilters']/li[text()='" + metaData + "']");
	}

	public Element IncludeRadioButtonForTallyBy() {
		return driver.FindElementByXPath(
				"//div[@class='popover bottom in']//div[@class='inline-group']//input[@id='Include']/parent::label/i");
	}

	public Element ExcludeRadioButtonForTallyBy() {
		return driver.FindElementByXPath(
				"//div[@class='popover bottom in']//div[@class='inline-group']//input[@id='Exclude']/parent::label/i");
	}

	public Element FilterInputTextBoxTallyBy() {
		return driver.FindElementByXPath("//input[@class='select2-search__field']");
	}

	public Element FilterInputOptionTallyBy(String filterValue) {
		return driver.FindElementByXPath("//ul[@role='tree']/li[@role='treeitem' and text()='" + filterValue + "']");
	}

	public Element ApplyFilterTallyBy() {
		return driver
				.FindElementByXPath("//div[@class='popover bottom in']//div[@class='row']//a[text()=' Add to Filter']");
	}

	public Element ActiveFiltersTallyBy(String FilteData) {
		return driver.FindElementByXPath("//li[contains(text(),'" + FilteData + "')]");
	}

	public Element metaDataFilterForSubTallyBy(String metaData) {
		return driver.FindElementByXPath("(//ul[@id='optionFilters'])[last()]/li[text()='" + metaData + "']");
	}

	public Element IncludeRadioButtonForSubTallyBy() {
		return driver.FindElementByXPath(
				"//div[@class='popover bottom in']//descendant::input[@id='Include']//following-sibling::i");
	}

	public Element ExcludeRadioButtonForSubTallyBy() {
		return driver.FindElementByXPath(
				"//div[@class='popover bottom in']//descendant::input[@id='Exclude']//following-sibling::i");
	}

	public Element ApplyFilterSubTallyBy() {
		return driver.FindElementByXPath("//div[@class='popover bottom in']//descendant::a[text()=' Add to Filter']");
	}

	public Element ActiveFiltersSubTallyBy(String FilteData) {
		return driver.FindElementByXPath("//div[@id='activeFilters']/li[contains(text(),'" + FilteData + "')]");
	}

	public ElementCollection ListOfMetaDataInSubTallyResults() {
		return driver.FindElementsByXPath("//div[@id='subTallyChart']//td[@class='text-left rowlead formatDate']");
	}

	public Element getFolderTreeExpandButton(String expandValue) {
		return driver.FindElementByXPath("//div[@id='_foldersTree']//li[@aria-expanded='" + expandValue + "']/i");
	}

	public Element getTallyByFolder(String folderName) {
		return driver.FindElementByXPath("//div[@id='_foldersTree']//a[@data-content='" + folderName + "']");
	}

	public Element getFolderRadioButton() {
		return driver.FindElementByXPath("//input[@id='folders']/following-sibling::i");
	}

	public Element getAssgnRadioButton() {
		return driver.FindElementByXPath("//input[@id='assignments']/following-sibling::i");
	}

	public Element getTallyByAssignment(String assgnName) {
		return driver.FindElementByXPath("//div[@id='_assignmentsTree']//a[@data-content='" + assgnName + "']");
	}

	public Element getSubTallyByName(String tagName) {
		return driver.FindElementByXPath("//a[@data-content = '" + tagName + "']");
	}

	public Element getSubTallyByFolderName(String folderName) {
		return driver.FindElementByXPath("//div[@id='_subfoldersTree']//a[@data-content = '" + folderName + "']");
	}

	public Element getSubTallyByTagName(String tagName) {
		return driver.FindElementByXPath("//div[@id='_subtagsTree']//a[@data-content = '" + tagName + "']");
	}

	public Element getSubTallyByAssgnName(String assgnName) {
		return driver.FindElementByXPath("//div[@id='_subassignmentsTree']//a[@data-content = '" + assgnName + "']");
	}

	public Element getTallyFolderBoard() {
		return driver.FindElementById("_foldersTree");
	}

	public Element getFolderBoard() {
		return driver.FindElementById("_subfoldersTree");
	}

	public Element getSubFoldersCheckBox() {
		return driver.FindElementByXPath("//strong[text()='Folders']//..//..//input[@name='sub-metadata']//..//i");
	}

	public Element getAssgnmentBoard() {
		return driver.FindElementById("_subassignmentsTree");
	}

	public Element getSubAssgnCheckBox() {
		return driver.FindElementByXPath("//strong[text()=' Assignments']//..//..//input[@name='sub-metadata']//..//i");
	}

	public Element getsubTallyFolderExpandButton(String expandValue) {
		return driver.FindElementByXPath("//div[@id='_subfoldersTree']//li[@aria-expanded='" + expandValue + "']/i");
	}

	public Element getsubTallyAssgnExpandButton(String expandValue) {
		return driver
				.FindElementByXPath("//div[@id='_subassignmentsTree']//li[@aria-expanded='" + expandValue + "']/i");
	}

	public Element getsubTallyTagExpandButton(String expandValue) {
		return driver.FindElementByXPath("//div[@id='_subtagsTree']//li[@aria-expanded='" + expandValue + "']/i");
	}

	// Added by Gopinath - 21/03/2022
	public Element getTallyPageHeader() {
		return driver.FindElementByXPath("//h1[@class='page-title' and contains(text(),'Tally')]");
	}

	public Element getSubTally_ExportData() {
		return driver.FindElementById("SubTallyToExportDataId");
	}

	public ElementCollection getSelectedColumnDocs_Subtally() {
		return driver
				.FindElementsByXPath("//table[@class='table table-striped dataGrid']/tbody/tr/td[@class='active']");
	}

	public Element getHeaderSubtally(int i) {
		return driver.FindElementByXPath("//table[@class='table table-striped dataGrid']/thead/tr/th[" + i + "]");
	}

	public ElementCollection getSelectSourcedOptionsList() {
		return driver.FindElementsByXPath("//div[@class='custom-popup' and @style=\"display: block;\"]//strong");
	}
	
	//added by arun
	public Element getMetaData(String field) {
		return driver.FindElementByXPath("//select[@id='metadataselect']//option[contains(text(),'"+field+"')]");
	}
	public Element getToolTipCount() {
		return driver.FindElementByXPath("//div[@class='tipsy-inner']");
	}
	public Element getValues(int i) {
		return driver.FindElementByCssSelector("rect[class='selected'][y='"+i+"']");
	}
	public Element getExportButtonStatus() {
		return driver.FindElementByXPath("//button[@id='btnExportTallyReportToExcel']");
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
		driver.scrollingToElementofAPage(getTally_btnTallyApply());
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
			getTally_SelectSource().waitAndClick(10);
			getProjectSource().waitAndClick(10);
			base.waitForElement(getProjectCheckbox());
			for (int i = 0; i < 20; i++) {
				try {
					getProjectCheckbox().Click();
					break;
				} catch (Exception e) {
					base.waitForElement(getProjectCheckbox());
				}
			}
			getProjectSaveButton().waitAndClick(10);
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
			driver.scrollPageToTop();
			base.waitForElement(getSubTallyField());
			base.waitTillElemetToBeClickable(getSubTallyField());
			getSubTallyField().Click();
			base.waitTillElemetToBeClickable(getSubTagsCheckBox());
			getSubTagsCheckBox().Click();
			base.waitForElement(getTagsBoard());
//			getTagsBoard().Click();//another tag is getting selected.
			Actions ac = new Actions(driver.getWebDriver());
			ac.moveToElement(getTagsBoard().getWebElement()).perform();
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
	public void selectMetaData_SubTally(String subtallyBy, int j) throws InterruptedException {
		base.waitForElement(getSubTallyField());
		base.waitTillElemetToBeClickable(getSubTallyField());
		getSubTallyField().waitAndClick(5);
		base.waitTime(10);
		if (j == 1) {
			base.waitTillElemetToBeClickable(getTally_subMetadata());
			getTally_subMetadata().ScrollTo();
			getTally_subMetadata().waitAndClick(20);
		}
//		Actions action = new Actions(driver.getWebDriver());
//		action.moveToElement(getTally_subMetadata().getWebElement()).click().perform();		
		base.waitTillElemetToBeClickable(getTally_submetadataselect());
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
		// base.waitForElement(getTallyViewBtn());
		// getTallyViewBtn().ScrollTo();
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
		base.waitTime(2);
		base.waitForElement(getTally_SelectSource());
		base.waitTillElemetToBeClickable(getTally_SelectSource());
		getTally_SelectSource().Click();
		base.waitTime(2);
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
		System.out.println("From hashmap doc count extracted are " + docCount);
		Integer sum = 0;
		if (docCount == null || docCount.size() < 1) {
			System.out.println("List size is zero");
		} else {
			for (int i : docCount) {
				sum = sum + i;
			}
		}
		System.out.println("List sum up" + sum);
		base.stepInfo("Count of docs reflected in tally by report is " + sum.toString());
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
		// base.waitForElement(getSubTallyViewBtn());
		driver.scrollingToBottomofAPage();
		base.waitTime(1);
		// getSubTallyViewBtn().ScrollTo();
		// getSubTallyViewBtn().ScrollTo();
		getSubTallyViewinDocViewBtn().ScrollTo();
		getSubTallyViewinDocViewBtn().waitAndClick(30);
		base.stepInfo("Navigating from Tally page to view in doc view page.");
	}

	public void sourceSelectionUsers(String role, String[] sourceArray, int i) {
		if (role == "PA") {
			selectSources_PA(i, sourceArray[i]);
		}
		if (role == "RMU") {
			selectSources_RMU(i, sourceArray[i]);
		}
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
		base.stepInfo("MetaData displayed in horizontal barchart is : ");
		base.stepInfo(elementNames.toString());
		return elementNames.toString();

	}

	/**
	 * @author Jayanthi.ganesan
	 * @return
	 */
	public int getDocCountSubTally() {
		Integer sum = 0;
		if (getSubtallyTable().isElementAvailable(30)) {
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

	/**
	 * @author Jayanthi.ganesan
	 * @param ListOfMetaData
	 * @param metaDataTally
	 * @param FilterType
	 * @return
	 */

	public String applyFilterToTallyBy(List<String> ListOfMetaData, String metaDataTally, String FilterType) {

		driver.waitForPageToBeReady();
		String ApplyFilterMetaData = null;
		base.waitForElement(metaDataFilterForTallyBy(metaDataTally));
		base.waitTillElemetToBeClickable(metaDataFilterForTallyBy(metaDataTally));
		metaDataFilterForTallyBy(metaDataTally).waitAndClick(10);
		if (FilterType.equalsIgnoreCase("Include")) {
			IncludeRadioButtonForTallyBy().waitAndClick(10);
		} else if (FilterType.equalsIgnoreCase("Exclude")) {
			ExcludeRadioButtonForTallyBy().waitAndClick(10);
		}
		for (int i = 0; i < ListOfMetaData.size(); i++) {
			if (ListOfMetaData.get(i) != null && ListOfMetaData.get(i) != "") {
				ApplyFilterMetaData = ListOfMetaData.get(i);
				break;
			} else {
				continue;
			}
		}
		System.out.println(ApplyFilterMetaData + " filter tally by");
		FilterInputTextBoxTallyBy().SendKeys(ApplyFilterMetaData);
		FilterInputOptionTallyBy(ApplyFilterMetaData).waitAndClick(10);
		ApplyFilterTallyBy().waitAndClick(10);
		base.waitForElement(ActiveFiltersTallyBy(ApplyFilterMetaData));
		if (ActiveFiltersTallyBy(ApplyFilterMetaData).isElementAvailable(2)) {
			base.passedStep("Selected Metadata is reflected in Active Filter under Tally by option .");
			getTally_btnTallyApply().Click();
			driver.waitForPageToBeReady();
		} else {
			base.failedStep("selected metadata is not reflected in active filters under tally  by option.");

		}
		return ApplyFilterMetaData;
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param FilterType
	 * @param ApplyFilterMetaData
	 */

	public void verifyTallyChartAfterApplyingFilter(String FilterType, String ApplyFilterMetaData) {

		List<String> ListOfMetaAfterApplyingFilter = verifyTallyChart();

		if (FilterType.equalsIgnoreCase("Include")) {
			if (ApplyFilterMetaData.equals(ListOfMetaAfterApplyingFilter.get(0))) {
				base.passedStep(
						"Include Filter applied worked and the value of metadata applied in the filter matches with the Result appeared on the chart");
			} else {
				base.failedStep(
						"Include Filter applied is not worked and the value of metadata applied in the filter is not matches with the Result appeared on the chart");
			}
		} else if (FilterType.equalsIgnoreCase("Exclude")) {
			if (!ListOfMetaAfterApplyingFilter.contains(ApplyFilterMetaData)
					|| ListOfMetaAfterApplyingFilter.isEmpty()) {
				base.passedStep(
						"Exclude Filter applied worked and the value of metadata applied in the filter Exculed from the chart");
			} else if (ListOfMetaAfterApplyingFilter.contains(ApplyFilterMetaData)) {
				base.failedStep(
						"Exclude Filter applied is not worked and the value of metadata applied in the filter is not Exculed from the chart");
			}
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param ListOfMetaData
	 * @param metaDataTally
	 * @param FilterType
	 * @return
	 */
	public String applyFilterToSubTallyBy(List<String> ListOfMetaData, String metaDataTally, String FilterType) {

		driver.waitForPageToBeReady();
		String ApplyFilterMetaData = null;
		base.waitForElement(metaDataFilterForSubTallyBy(metaDataTally));
		base.waitTillElemetToBeClickable(metaDataFilterForSubTallyBy(metaDataTally));
		metaDataFilterForSubTallyBy(metaDataTally).waitAndClick(10);

		if (FilterType.equalsIgnoreCase("Include")) {
			IncludeRadioButtonForSubTallyBy().waitAndClick(10);
		} else if (FilterType.equalsIgnoreCase("Exclude")) {
			ExcludeRadioButtonForSubTallyBy().waitAndClick(10);
		}
		for (int i = 0; i < ListOfMetaData.size(); i++) {
			if (ListOfMetaData.get(i) != null && ListOfMetaData.get(i) != "") {
				ApplyFilterMetaData = ListOfMetaData.get(i);
				break;
			} else {
				continue;
			}
		}
		System.out.println("Subtaly filter " + ApplyFilterMetaData);
		FilterInputTextBoxTallyBy().SendKeys(ApplyFilterMetaData);
		FilterInputOptionTallyBy(ApplyFilterMetaData).waitAndClick(10);
		ApplyFilterSubTallyBy().waitAndClick(10);
		base.waitForElement(ActiveFiltersSubTallyBy(ApplyFilterMetaData));
		if (ActiveFiltersSubTallyBy(ApplyFilterMetaData).isElementAvailable(3)) {
			base.passedStep(
					"Selected metadata is reflected in active filter" + ApplyFilterMetaData + "under sub tally by.");
			base.waitForElement(getTally_btnSubTallyApply());
			getTally_btnSubTallyApply().Click();
			driver.waitForPageToBeReady();
		} else {
			base.failedStep("Selected metadata not reflected in Active filter under sub tally by");
		}
		return ApplyFilterMetaData;
	}

	/**
	 * @author Jayanthi.ganesan
	 */
	public void verifySubTallyResultTableAfterApplyingFilter(String FilterType, String ApplyFilterMetaData) {

		List<String> ListOfMetaAfterApplyingFilter = base.availableListofElements(ListOfMetaDataInSubTallyResults());

		if (FilterType.equalsIgnoreCase("Include")) {
			if (ApplyFilterMetaData.equals(ListOfMetaAfterApplyingFilter.get(0))) {
				base.passedStep(
						"Include Filter applied worked and the value of metadata applied in the filter matches with the Result appeared on the Sub-Tally By Table");
			} else {
				base.failedStep(
						"Include Filter applied is not worked and the value of metadata applied in the filter is not matches with the Result appeared on the Sub-Tally By Table");
			}
		} else if (FilterType.equalsIgnoreCase("Exclude")) {
			if (!ListOfMetaAfterApplyingFilter.contains(ApplyFilterMetaData)
					|| ListOfMetaAfterApplyingFilter.isEmpty()) {
				base.passedStep(
						"Exclude Filter applied worked and the value of metadata applied in the filter Exculed from the Sub-Tally By Table");
			} else if (ListOfMetaAfterApplyingFilter.contains(ApplyFilterMetaData)) {
				base.failedStep(
						"Exclude Filter applied is not worked and the value of metadata applied in the filter is not Exculed from the Sub-Tally By Table");
			}
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param folderName[Names of Folder to be selected] This method will select
	 *                         Folder as source
	 */
	public void SelectMultipleSource_Folder(String[] folderName) {
		base.stepInfo("**Selecting source as Folders**");
		base.waitForElement(getTally_SelectSource());
		getTally_SelectSource().Click();
		base.waitForElement(getTally_Folders());
		getTally_Folders().Click();
		for (int i = 0; i < folderName.length; i++) {
			driver.scrollingToElementofAPage(getTally_FoldersCheckBox(folderName[i]));
			getTally_FoldersCheckBox(folderName[i]).ScrollTo();
			base.waitTime(2);
			getTally_FoldersCheckBox(folderName[i]).waitAndClick(5);
			base.waitTime(2);
		}
		driver.scrollingToElementofAPage(getTally_foldersSaveSelections());
		getTally_foldersSaveSelections().ScrollTo();
		base.waitForElement(getTally_foldersSaveSelections());
		getTally_foldersSaveSelections().Click();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param saveSearch[Names of SaveSearch to be selected] This method will select
	 *                         SaveSearch as source
	 */
	public void SelectMultipleSource_SavedSearch(String[] saveSearch) {
		base.stepInfo("**selecting source as search**");
		base.waitForElement(getTally_SelectSource());
		getTally_SelectSource().Click();
		base.waitForElement(getTally_Searches());
		getTally_Searches().Click();
		for (int i = 0; i < saveSearch.length; i++) {
			base.waitTime(2);
			getTally_SelectSearches(saveSearch[i]).ScrollTo();
			System.out.println(saveSearch[i]);
			base.waitForElement(getTally_SelectSearches(saveSearch[i]));
			getTally_SelectSearches(saveSearch[i]).waitAndClick(10);
			base.waitTime(2);
		}
		driver.scrollingToElementofAPage(getTally_SearchSaveSelections());
		getTally_SearchSaveSelections().ScrollTo();
		base.waitForElement(getTally_SearchSaveSelections());
		base.waitTime(2);
		getTally_SearchSaveSelections().Click();
		base.stepInfo("Selected " + saveSearch + " as source to generate tally report");
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param securityGroup[Names of security groups to be selected] This method
	 *                            will select security group as source
	 */
	public void SelectMultipleSource_SecurityGroup(String[] securityGroup) {
		base.stepInfo("**Selecting source as security group **");
		base.waitForElement(getTally_SelectSource());
		getTally_SelectSource().Click();
		base.waitForElement(getTally_SecurityGroup());
		getTally_SecurityGroup().Click();
		for (int i = 0; i < securityGroup.length; i++) {
			if (getTally_SecurityGroupCheckBox(securityGroup[i]).isElementAvailable(1)) {
				driver.scrollingToElementofAPage(getTally_SecurityGroupCheckBox(securityGroup[i]));
				base.waitTime(2);
				getTally_SecurityGroupCheckBox(securityGroup[i]).waitAndClick(5);
			}
		}
		base.waitTime(2);
		driver.scrollingToElementofAPage(getTally_SearchSaveSelections());
		base.waitForElement(getTally_SGSaveSelections());
		getTally_SGSaveSelections().ScrollTo();
		getTally_SGSaveSelections().Click();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param i
	 * @param name[names of the source to be selected ex-'N' number of search names]
	 *                   This method will select source as Search/Folder/Security
	 *                   Group/PRoject based on 'i' value passed.
	 */
	public void selectmultipleSources(int i, String[] name) {
		if (i == 0)
			SelectMultipleSource_SavedSearch(name);
		if (i == 1)
			SelectMultipleSource_Folder(name);
		if (i == 2)
			SelectMultipleSource_SecurityGroup(name);
		if (i == 3)
			selectSource_Project(name[0]);

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param folderName This method will select the given Folder name as Tally By
	 *                   option
	 */
	public void selectTallyByFolderName(String folderName) {
		try {
			base.waitForElement(getTally_SelectaTallyFieldtoruntallyon());
			base.waitTillElemetToBeClickable(getTally_SelectaTallyFieldtoruntallyon());
			getTally_SelectaTallyFieldtoruntallyon().Click();
			base.waitForElement(getFolderRadioButton());
			base.waitTillElemetToBeClickable(getFolderRadioButton());
			getFolderRadioButton().Click();
			if (getFolderTreeExpandButton("false").isElementAvailable(2)) {
				base.waitTillElemetToBeClickable(getFolderTreeExpandButton("false"));
				getFolderTreeExpandButton("false").Click();
			}
			getTallyFolderBoard().ScrollTo();
			getTallyByFolder(folderName).ScrollTo();
			base.waitTillElemetToBeClickable(getTallyByFolder(folderName));
			getTallyByFolder(folderName).Click();
			base.waitTime(2);
			base.waitForElement(getTally_btnTallyApply());
			driver.scrollPageToTop();
			getTally_btnTallyApply().ScrollTo();
			getTally_btnTallyApply().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while selecting tally by meta data field" + e.getMessage());
		}

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param assgnName This method will select the given Assignment name as Tally
	 *                  By option
	 */
	public void selectTallyByAssgnName(String assgnName) {
		try {
			base.waitForElement(getTally_SelectaTallyFieldtoruntallyon());
			base.waitTillElemetToBeClickable(getTally_SelectaTallyFieldtoruntallyon());
			getTally_SelectaTallyFieldtoruntallyon().Click();
			base.waitForElement(getAssgnRadioButton());
			base.waitTillElemetToBeClickable(getAssgnRadioButton());
			getAssgnRadioButton().Click();

			base.waitForElement(getTallyByAssignment(assgnName));
			getTallyByAssignment(assgnName).ScrollTo();
			getTallyByAssignment(assgnName).Click();
			base.waitTime(2);
			base.waitForElement(getTally_btnTallyApply());
			driver.scrollPageToTop();
			getTally_btnTallyApply().ScrollTo();
			getTally_btnTallyApply().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while selecting tally by meta data field" + e.getMessage());
		}

	}

	/**
	 * @author : Jayanthi Method for selecting Tag as subtally field from tally.
	 * @param tagName : (tagName is a string value that name of tag)
	 */
	public void applyingSubTallyTagField(String tagName) {
		try {
			base.waitForElement(getSubTallyField());
			base.waitTillElemetToBeClickable(getSubTallyField());
			getSubTallyField().Click();
			base.waitTillElemetToBeClickable(getSubTagsCheckBox());
			getSubTagsCheckBox().Click();
			if (getsubTallyTagExpandButton("false").isElementAvailable(2)) {
				base.waitForElement(getsubTallyTagExpandButton("false"));
				getsubTallyTagExpandButton("false").Click();
			}
			getTagsBoard().ScrollTo();
			base.waitForElement(getSubTallyByTagName(tagName));
			base.waitTillElemetToBeClickable(getSubTallyByTagName(tagName));
			getSubTallyByTagName(tagName).Click();
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
	 * @author Jayanthi.ganesan This method will select the Sub-Tally by option as
	 *         Tags/Folders/Metadata/Assignments
	 * @param tallyType[It   should be tags/folder/assignment/metadata]
	 * @param tallyName[Name of the tag/folder/assignment/metadata to be selected]
	 * @throws InterruptedException
	 */
	public void selectTallyBy(String tallyType, String tallyName) {
		switch (tallyType) {

		case "tag":
			selectTallyByTagName(tallyName);
			break;
		case "folder":
			selectTallyByFolderName(tallyName);
			break;
		case "assignment":
			selectTallyByAssgnName(tallyName);
			break;
		case "metadata":
			selectTallyByMetaDataField(tallyName);
			break;
		}
	}

	/**
	 * @author Jayanthi.ganesan This method will select the Tally by option as
	 *         Tags/Folders/Metadata/Assignments
	 * @param tallyType[It   should be tags/folder/assignment/metadata]
	 * @param tallyName[Name of the tag/folder/assignment/metadata to be selected]
	 * @throws InterruptedException
	 */
	public void selectSubTallyBy(String tallyType, String tallyName) throws InterruptedException {
		switch (tallyType) {
		case "tag":
			applyingSubTallyTagField(tallyName);
			break;
		case "folder":
			applyingSubTallyByFolderName(tallyName);
			break;
		case "assignment":
			applyingSubTallyByAssgnName(tallyName);
			break;
		case "metadata":
			selectMetaData_SubTally(tallyName, 1);
			break;
		}
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param folderName This method will select the given folder name as Sub Tally
	 *                   By option
	 */
	public void applyingSubTallyByFolderName(String folderName) {

		base.waitForElement(getSubTallyField());
		base.waitTillElemetToBeClickable(getSubTallyField());
		getSubTallyField().Click();
		base.waitTillElemetToBeClickable(getSubFoldersCheckBox());
		getSubFoldersCheckBox().Click();
		if (getsubTallyFolderExpandButton("false").isElementAvailable(2)) {
			base.waitForElement(getsubTallyFolderExpandButton("false"));
			getsubTallyFolderExpandButton("false").Click();
		}
		getFolderBoard().ScrollTo();
		base.waitForElement(getSubTallyByFolderName(folderName));
		getSubTallyByFolderName(folderName).ScrollTo();
		getSubTallyByFolderName(folderName).Click();
		driver.scrollPageToTop();
		base.waitForElement(getSubTallyApplyButton());
		getSubTallyApplyButton().Click();
	}

	/**
	 * @author Jayanthi.ganesan
	 * @param assgnName This method will select the given assignment name as
	 *                  SubTally By option
	 */
	public void applyingSubTallyByAssgnName(String assgnName) {

		base.waitForElement(getSubTallyField());
		base.waitTillElemetToBeClickable(getSubTallyField());
		getSubTallyField().Click();
		base.waitTillElemetToBeClickable(getSubAssgnCheckBox());
		getSubAssgnCheckBox().Click();
		if (getsubTallyAssgnExpandButton("false").isElementAvailable(2)) {
			base.waitForElement(getsubTallyAssgnExpandButton("false"));
			getsubTallyAssgnExpandButton("false").Click();
		}
		getAssgnmentBoard().ScrollTo();
		base.waitForElement(getSubTallyByAssgnName(assgnName));
		getSubTallyByAssgnName(assgnName).Click();
		driver.scrollPageToTop();
		base.waitForElement(getSubTallyApplyButton());
		getSubTallyApplyButton().Click();
	}

	/**
	 * This method will apply filter in sub tally report.
	 * 
	 * @author Jayanthi.ganesan
	 * @param ApplyFilterMetaData(Metadata value which needs to be
	 *                                     selected--Ex-Andrew)
	 * @param metaDataTally(Metadata       type which needs to be selected
	 *                                     --Ex-CustodainName)
	 * @param FilterType(Filter            type-Include or Exclude)
	 * @return
	 */
	public String applyFilterToSubTallyBy(String ApplyFilterMetaData, String metaDataTally, String FilterType) {

		driver.waitForPageToBeReady();

		base.waitForElement(metaDataFilterForSubTallyBy(metaDataTally));
		base.waitTillElemetToBeClickable(metaDataFilterForSubTallyBy(metaDataTally));
		metaDataFilterForSubTallyBy(metaDataTally).waitAndClick(10);

		if (FilterType.equalsIgnoreCase("Include")) {
			IncludeRadioButtonForSubTallyBy().waitAndClick(10);
		} else if (FilterType.equalsIgnoreCase("Exclude")) {
			ExcludeRadioButtonForSubTallyBy().waitAndClick(10);
		}

		System.out.println("Subtaly filter " + ApplyFilterMetaData);
		FilterInputTextBoxTallyBy().SendKeys(ApplyFilterMetaData);
		FilterInputOptionTallyBy(ApplyFilterMetaData).waitAndClick(10);
		ApplyFilterSubTallyBy().waitAndClick(10);
		base.waitForElement(ActiveFiltersSubTallyBy(ApplyFilterMetaData));
		if (ActiveFiltersSubTallyBy(ApplyFilterMetaData).isElementAvailable(3)) {
			base.passedStep(
					"Selected metadata is reflected in active filter" + ApplyFilterMetaData + "under sub tally by.");
			base.waitForElement(getTally_btnSubTallyApply());
			getTally_btnSubTallyApply().Click();
			driver.waitForPageToBeReady();
		} else {
			base.failedStep("Selected metadata not reflected in Active filter under sub tally by");
		}
		return ApplyFilterMetaData;
	}

	/**
	 * @author Jayanthi.Ganesan This method will navigate from tally page[sub tally
	 *         report] to export page
	 */
	public void subTallyToExport() {
		base.waitForElement(getSubTally_ExportData());
		getSubTally_ExportData().Click();
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Jayanthi.Ganesan This method will navigate from tally page[ tally
	 *         report] to doc list page
	 */
	public void tallyToDocList() {
		base.waitForElement(getTallyViewinDocListBtn());
		getTallyViewinDocListBtn().Click();
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Jayanthi.ganesan
	 */
	public void SelectSource_MultipleSavedSearch(String saveSearch[]) {
		base.stepInfo("**selecting source as search--" + saveSearch + "**");
		base.waitForElement(getTally_SelectSource());
		getTally_SelectSource().Click();
		base.waitForElement(getTally_Searches());
		getTally_Searches().Click();
		for (int i = 0; i < saveSearch.length; i++) {
			driver.scrollingToElementofAPage(getTally_SelectSearches(saveSearch[i]));
			getTally_SelectSearches(saveSearch[i]).ScrollTo();
			base.waitTime(2);
			getTally_SelectSearches(saveSearch[i]).waitAndClick(5);
		}
		driver.scrollingToElementofAPage(getTally_SearchSaveSelections());
		base.waitForElement(getTally_SearchSaveSelections());
		base.waitTime(2);
		getTally_SearchSaveSelections().Click();
		driver.waitForPageToBeReady();
		verifySourceSelected();
		base.stepInfo("Selected " + saveSearch + " as source to generate tally report");
	}

	/**
	 * @author Jayanthi.ganesan This method will select the column in sub tally
	 *         table based on the value 'k' we are passing.
	 * @return selected docs count in sub tally table
	 */
	public int getSelectedColumnDocCountSubTally(int k) {
		Integer sum = 0;
		if (getSubtallyTable().isElementAvailable(10)) {
			getHeaderSubtally(k).ScrollTo();
			getHeaderSubtally(k).waitAndClick(10);
			List<WebElement> elementList = null;
			List<Integer> docCount = new ArrayList<>();
			elementList = getSelectedColumnDocs_Subtally().FindWebElements();
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
			base.stepInfo("Total Count of  docs selected in sub tally Table" + sum.toString());

		} else {
			base.failedStep("sub tally report not displayed.");
		}
		return sum;
	}

	/**
	 * @author Jayanthi.Ganesan this method will click on sub tally action button
	 */
	public void subTallyActionsWithOutTallyAllSelection() {
		base.waitForElement(getTally_SubTallyActionButton());
		getTally_SubTallyActionButton().waitAndClick(25);

	}

	/**
	 * @author Jayanthi.Ganesan
	 * @param metaDataTally
	 * @param FilterType
	 * @param ApplyFilterMetaData
	 */

	public void applyFilterToTallyBy(String metaDataTally, String FilterType, String ApplyFilterMetaData) {

		driver.waitForPageToBeReady();
		base.waitForElement(metaDataFilterForTallyBy(metaDataTally));
		base.waitTillElemetToBeClickable(metaDataFilterForTallyBy(metaDataTally));
		metaDataFilterForTallyBy(metaDataTally).waitAndClick(10);
		if (FilterType.equalsIgnoreCase("Include")) {
			IncludeRadioButtonForTallyBy().waitAndClick(10);
		} else if (FilterType.equalsIgnoreCase("Exclude")) {
			ExcludeRadioButtonForTallyBy().waitAndClick(10);
		}
		System.out.println(ApplyFilterMetaData + " filter tally by");
		FilterInputTextBoxTallyBy().SendKeys(ApplyFilterMetaData);
		FilterInputOptionTallyBy(ApplyFilterMetaData).waitAndClick(10);
		ApplyFilterTallyBy().waitAndClick(10);
		base.waitForElement(ActiveFiltersTallyBy(ApplyFilterMetaData));
		if (ActiveFiltersTallyBy(ApplyFilterMetaData).isElementAvailable(2)) {
			base.passedStep("Selected Metadata " + FilterType + " : " + metaDataTally + " : " + ApplyFilterMetaData
					+ " reflected in Active Filter under Tally by option .");
		} else {
			base.failedStep("selected metadata " + FilterType + " : " + metaDataTally + " : " + ApplyFilterMetaData
					+ "is not reflected in active filters under tally  by option.");

		}

	}

	/**
	 * @author Jayanthi
	 * @param selectSourceList
	 */
	public void verifySourceList(String userNAme) {
		driver.waitForPageToBeReady();
		base.waitForElement(getTally_SelectSource());
		getTally_SelectSource().Click();
		if (userNAme.equals(Input.rmu1userName)) {
			String[] selectSourceList = { "Security Groups", "Searches", "Assignments", "Folders" };
			List<String> listDatas = base.getAvailableListofElements(getSelectSourcedOptionsList());
			base.printListString(listDatas);
			base.compareArraywithDataList(selectSourceList, listDatas, true,
					"Expected sources displayed in select source popup For RMU USer",
					"Expected sources not displayed in select source popup For RMU USer");
		}
		if (userNAme.equals(Input.pa1userName)) {
			String[] selectSourceList = { "Security Groups", "Searches", "Projects", "Folders" };
			List<String> listDatas = base.getAvailableListofElements(getSelectSourcedOptionsList());
			base.printListString(listDatas);
			base.compareArraywithDataList(selectSourceList, listDatas, true,
					"Expected sources displayed in select source popup For PA USer",
					"Expected sources not displayed in select source popup For PA USer");
		}
		base.waitForElement(getTally_SelectSource());
		getTally_SelectSource().Click();
	}

	/**
	 * @author : Vijaya.Rani Created date: NA Modified date: NA
	 * @Description: Method for selecting subtally field from tally..
	 * @param folderName : (folderName is a string value that name of tag)
	 */
	public void applyingSubTallyFolderField(String folderName) {
		try {
			driver.scrollPageToTop();
			base.waitForElement(getSubTallyField());
			base.waitTillElemetToBeClickable(getSubTallyField());
			getSubTallyField().Click();
			base.waitTillElemetToBeClickable(getSubFoldersCheckBox());
			getSubFoldersCheckBox().Click();
			base.waitForElement(getFolderBoard());
			Actions ac = new Actions(driver.getWebDriver());
			ac.moveToElement(getFolderBoard().getWebElement()).perform();
			base.waitForElement(getSubTallyByFolderName(folderName));
			base.waitTillElemetToBeClickable(getSubTallyByFolderName(folderName));
			getSubTallyByFolderName(folderName).Click();
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
	 * @author : Vijaya.Rani Created date: NA Modified date: NA
	 * @Description: Method for verifying documents count by folderName subtally..
	 * @param folderName : (folderName is a string value that name of tag)
	 */
	public void verifyDocumentsCountByFolderNameSubTally(String folderName) {
		try {
			driver.scrollingToBottomofAPage();
			base.waitForElement(getDocumentsCount());
			int count = Integer.parseInt(getDocumentsCount().getWebElement().getText().trim());
			String folder = getDocumentsCount().getWebElement().getAttribute("data-y").trim();
			if (count > 0) {
				base.passedStep("Tag : " + folder + " with document count : " + count);
			} else {
				base.failedStep("Failed to get count of documents");
			}

			if (folder.equalsIgnoreCase(folderName)) {
				base.passedStep("Tag : " + folder + " with document count : " + count + " displayed successfully");
			} else {
				base.failedStep(folderName + " with count is not displayed");
			}

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifying documents count by folderName subtally" + e.getMessage());
		}

	}
	
	/**
	 * @author: Arun Created Date: 17/10/2022 Modified by: NA Modified Date: NA
	 * @description: this method will check the field available un metadata list
	 */
	public void verifyMetaDataUnAvailabilityInTallyReport(String field) {
		
		navigateTo_Tallypage();
		base.waitForElement(getTally_SelectaTallyFieldtoruntallyon());
		getTally_SelectaTallyFieldtoruntallyon().Click();
		base.waitForElement(getTally_Metadataselect());
		if(getMetaData(field).isElementAvailable(10)) {
			base.failedStep(field+"is available in metadata list");
		}
		else {
			base.passedStep(field+"is not available in metadata list");
		}
	}
	
	/**
	 * @author: Arun Created Date: 18/10/2022 Modified by: NA Modified Date: NA
	 * @description: this method will get the mapping in tally report
	 */
	
	public HashMap<String,Integer> getDocsCountFortallyReport() {
		
		HashMap<String,Integer> map = new HashMap<String,Integer>();
		
		List<String> meta =verifyTallyChart();
		List<Integer> count = new ArrayList<Integer>();
		Actions action = new Actions(driver.getWebDriver());
		int chartnumber =getrectValues().size();
		System.out.println(chartnumber);
		for(int i=0;i<30*chartnumber;i+=30) {
			Element ele = getValues(i);
			action.moveToElement(ele.getWebElement()).build().perform();
			String text =getToolTipCount().getText();
			String[] data = text.split(" ");
			int docsCount = Integer.parseInt(data[0]);
			count.add(docsCount);
		}
		for(int j=0;j<meta.size();j++) {
			map.put(meta.get(j), count.get(j));
		}
		base.passedStep("DocsCount mapping in tally report"+map);
		return map;
		
		
	}
	
	/**
	 * @author: Arun Created Date: 09/11/2022 Modified by: NA Modified Date: NA
	 * @description: this method will check the generation of tally report for metadata
	 */
	public void verifyTallyReportGenerationForMetadata(String metadata) {
		
		selectSourceByProject();
		selectTallyByMetaDataField(metadata);
		base.waitForElement(getTally_btnTallyAll());
		getTally_btnTallyAll().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		String exportStatus = getExportButtonStatus().GetAttribute("disabled");
		base.stepInfo("export button disabled status after searching with metadata-"+exportStatus);
		if(getTallyChartRectbar().isDisplayed() && exportStatus==null) {
			base.passedStep("Tally report generated for the field-" + metadata);
		}
		else {
			base.failedStep("Tally report not generated and export option not available"+metadata);
		}
	}
	/**
	 * @author: Arun Created Date: 09/11/2022 Modified by: NA Modified Date: NA
	 * @description: this method will perform tally and search for allcustodians metadata
	 */
	public void performTallyAndSearchForAllCustodians() {
		
		List<String> custodians =verifyTallyChart();
		HashMap<String, Integer> map = getDocsCountFortallyReport();
		for(int i=0;i<custodians.size();i++) {
			String custodian = custodians.get(i);
			if(!(custodian.isEmpty())) {
				int tallyResult = map.get(custodian);
				base.stepInfo("tally result for "+ custodian + "-" + tallyResult);
				base.stepInfo("perform search with field and verify purehit count with report");
				SessionSearch search = new SessionSearch(driver);
				int searchResult =search.MetaDataSearchInBasicSearch("AllCustodians", "\""+custodian+"\"");
				base.stepInfo("search result for "+custodian+"-" + tallyResult);
				if(tallyResult==searchResult ) {
					base.passedStep("Counts matched for search result and tally report");
					break;
				}
				else {
					base.failedStep("Counts not matched for search result and tally report");
				}
			}
		}
	}

}