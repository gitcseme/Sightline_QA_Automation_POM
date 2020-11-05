package pageFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import automationLibrary.ElementCollection;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;

import org.openqa.selenium.interactions.Actions;
import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import junit.framework.Assert;
import testScriptsSmoke.Input;


public class BatchPrintPage {

    Driver driver;
    Element element;
    BaseClass base;
    
    public Element getMenuBatchPrint(){ return driver.FindElementByXPath("//a[@href='/BatchPrint/']"); }
    public Element getSearchBatchPrint(){ return driver.FindElementByXPath("//div[1]/div/label/i"); }
    public Element getTagBatchPrint(){ return driver.FindElementByXPath("//div[2]/div/label/i"); }
    public Element getFolderBatchPrint(){ return driver.FindElementByXPath("//div[3]/div/label/i"); }
    
    public Element getMySavedSearchArrow(){ return driver.FindElementByXPath("(//*[@id='-1g']/i)[1]"); }
    public Element getAllTagsArrow(){ return driver.FindElementByXPath("(//*[@id='-1g']/i)[2]"); }
    public Element getAllFoldersArrow(){ return driver.FindElementByXPath("(//*[@id='-1g']/i)[3]"); }
    public ElementCollection getSelectSavedSearchRows() {return driver.FindElementsByXPath("@data-content='My Saved Search']/following-sibling::ul/li");}
    public Element getSelectSavedSearch(String searchname){ return driver.FindElementByXPath(".//*[@id='searchTree']/ul/li[1]//a[contains(text(),'"+searchname+"')]"); }
    public Element getSelectTag(String tagName){ return driver.FindElementByXPath("//a[starts-with(text(),'"+tagName+"')]"); }
    public Element getSelectFolder(String folderName){ return driver.FindElementByXPath("//a[starts-with(text(),'"+folderName+"')]"); }
    
    
    public Element getNextbtn(){ return driver.FindElementByXPath("//a[contains(text(),'Next')]"); }
    public Element getSourcenextbutton(){ return driver.FindElementById("source-selection-next-button"); }
    public Element getExceptiontypenextbutton(){ return driver.FindElementById("exception-file-types-next-button"); }
    public Element getBrandingnextbutton(){ return driver.FindElementById("branding-and-redaction-next-button"); }
    public Element getSlipnextbutton(){ return driver.FindElementById("slip-sheets-next-button"); }
    public Element getAnalysisnextbutton(){ return driver.FindElementById("analysis-next-button"); }
    public Element getBasisnextbutton(){ return driver.FindElementById("basis-for-printing-next-button"); }
    public Element getAnalysis_Ignore_RadioButton(){ return driver.FindElementByXPath(".//*[@id='ignoreRadioButton']/following-sibling::i"); }
    public Element getSelectCustodianName(){ return driver.FindElementByCssSelector("input[data-friendlbl='CustodianName'] + i"); }
    public Element getSelectDOCID(){ return driver.FindElementByCssSelector("input[data-friendlbl='DocID'] + i"); }
    public Element getSelectSourceDOCID(){ return driver.FindElementByCssSelector("input[data-friendlbl='SourceDocID'] + i"); }
    public Element getSelectMasterDate(){ return driver.FindElementByCssSelector("input[data-friendlbl='MasterDate'] + i"); }
    public Element getSelectColumnAddtoSelected(){ return driver.FindElementById("addFormObjects"); }
    public Element getBrandingCentre(){ return driver.FindElementById("btn-batchprint-branding-1"); }
    public Element getBatchPrintEnterBranding(){ return driver.FindElementByXPath(".//*[@id='Edit User Group']//div[@class='redactor-editor']"); }
    public Element getInsertMetadataFieldOKButton(){ return driver.FindElementById("myModalOk"); }
    public Element getSelectExportFileName(){ return driver.FindElementById("exportFileDropDown"); }
    public Element getSelectExportFileSortBy(){ return driver.FindElementById("exportFileSortByDropDown"); }
    public Element getSelectExportFileSortByOption(String option) {return driver.FindElementByCssSelector(String.format("//select[@id = 'exportFileSortByDropDown']/option[text() = '%s']",option));}
    public Element getSelectExportDropDownByOption(String option) {return driver.FindElementByXPath(String.format("//select[@id = 'exportFileSortByDropDown']/option[text() = '%s']", option));}
    public Element getSelectExportSortByOrder() {return driver.FindElementById("exportFileSortingOrderDropDown");}
    public Element getSelectExportSortByOrderOption(String option) {return driver.FindElementByCssSelector(String.format("#exportFileSortingOrderDropDown option[value = '%s']",option));}
    public Element getGenerateButton(){ return driver.FindElementById("generate-batch-print-button"); }
    public Element getbackgroundDownLoadLink(){ return driver.FindElementByXPath("//*[@id='dt_basic']/tbody/tr[1]/td[contains(.,'Download File')]"); }
    public Element getTaskType(){ return driver.FindElementByXPath(".//*[@id='dt_basic']/tbody/tr[1]/td[2]"); }
    public Element getBatchPrintStatus(){ return driver.FindElementByXPath(".//*[@id='dt_basic']/tbody/tr[1]/td[8]"); }
    public Element getDownLoadLink(){ return driver.FindElementByCssSelector("#dt_basic a[onclick*='downloadFile']"); }
    public Element getAnalysis_SkipExcelFiles_RadioButton(){ return driver.FindElementByXPath(".//*[@id='skipExcelFileRadioButton']/following-sibling::i"); }
    public Element getMedia_InsertMetadata(){ return driver.FindElementById("mediaFileInsertMetadataLinkButton"); }
    public Element getExcel_InsertMetadata(){ return driver.FindElementById("excelFileInsertMetadataLinkButton"); }
    public Element getOther_InsertMetadata(){ return driver.FindElementById("otherExceptionFileTypesInsertMetadataLinkButton"); } 
    public Element getBP_Exception_Media(){ return driver.FindElementById("mediaFilePlaceHolderHtml"); }
    public Element getBP_Exception_Excel(){ return driver.FindElementById("excelFilePlaceHolderHtml"); }
    public Element getBP_Exception_Other(){ return driver.FindElementById("OtherExceptionFileTypesPlaceHolderHtml"); }
    public Element getselectMetadataFields_Media(){ return driver.FindElementById("mediaFileMetaData"); }
    public Element getselectMetadataFields_Excel(){ return driver.FindElementById("excelfileMetaData"); }
    public Element getselectMetadataFields_Other(){ return driver.FindElementById("otherExceptionFileTypesMetaData"); }
    public Element getOkButton_Media(){ return driver.FindElementById("mediaFileOk"); }
    public Element getOkButton_Excel(){ return driver.FindElementById("excelFileOk"); }
    public Element getOkButton_Other(){ return driver.FindElementById("otherExceptionFileTypesOk"); }
    public Element getPlaceHolderBox(){ return driver.FindElementByXPath("//div[@class='redactor-box']"); }
    public Element getSelectProduction(){ return driver.FindElementById("ProductionDropDown"); }
    
    //new addition
    public Element getProductionRadioButton(){ return driver.FindElementByXPath(".//*[@id='priorProductionRadioButton']/following-sibling::i"); }
    public Element getSkippedFolderButton(){ return driver.FindElementByXPath(".//*[@id='includeFolderSkippedDocuments']/following-sibling::i"); }
    public Element getAllSkippedDocumentsToggle() {return driver.FindElementById("includeFolderSkippedDocuments");}
    public Element getPDFCreationforAllButton(){ return driver.FindElementByXPath(".//*[@id='onePDFForAllDocRadioButton']/following-sibling::i"); }
    public Element getErrortext(){ return driver.FindElementByXPath(".//*[@id='BatchPrintErrorGrid']/tbody/tr[1]/td[2]"); }
    public Element getErrorInfoLink(){ return driver.FindElementById("idBtachPrintError"); }
    public Element getCloseButton(){ return driver.FindElementById("Close"); }
    
    public Element getSelectSearchParentOption(String parent) { return driver.FindElementByXPath("//*[@id='searchTree']//a[@data-content='"+parent+"']/preceding-sibling::i"); }
    public Element getSelectSearchParentGroup(String parent) { return driver.FindElementByXPath("//*[@id='searchTree']//a[@data-content='\"+parent+\"']/following-sibling::ul"); }
    public Element getSelectSearchOption(String option) { return driver.FindElementByXPath("//*[@id='searchTree']//a[@data-content='"+option+"']"); }
    public Element getSelectSearchChildGroup(String parent, String child) {return driver.FindElementByXPath(String.format("//*[@id='searchTree']//a[@data-content='%s']/following-sibling::ul/li/a[@data-content=%s']/i[1]",parent,child));}
    public ElementCollection getSelectSearchChildOptions(String parent) {return driver.FindElementsByCssSelector("#searchTree a[data-content = 'My Saved Search'] + ul a i:first-child");}
    public Element getSourceSelectionNextButton() { return driver.FindElementById("source-selection-next-button"); }
    public Element getNativeRadioButton() { return driver.FindElementById("nativesRadioButton"); }
    public Element getBasisForPrintingNextButton() { return driver.FindElementById("basis-for-printing-next-button"); }
    public Element getAnalysisNextButton() { return driver.FindElementById("analysis-next-button"); }
    public Element getPrintExcelFilesRadioButton() { return driver.FindElementById("printExcelFileRadiobutton"); } 
    public Element getExceptionFileTypesNextButton() { return driver.FindElementById("exception-file-types-next-button"); }

    public Element getSlipSheetsSelectFields() { return driver.FindElementById("slipSheetsSelectFieldsDiv"); }
    public Element getEnableSlipSheets() { return driver.FindElementById("includeSlipSheetCheckBox"); }
    public Element getEnableSlipSheetsToggle() { return driver.FindElementByXPath("//*[@id='includeSlipSheetCheckBox']/following-sibling::i"); }
    public Element getSlipSheetsDisabledPanel() { return driver.FindElementByXPath("//div[@id='slipSheetsSelectFieldsDiv' and @class='disablePanel']"); }
    public Element getSlipSheetsNextButton() { return driver.FindElementById("slip-sheets-next-button"); }
    public Element getBrandingHeaderLocation(String location) { return driver.FindElementByXPath("//div[@id='divbranding2']/div[@class='placeholder-header']//button[text()='"+location+"']"); }
    public Element getBandingLocationPopup() { return driver.FindElementByCssSelector(".smart-form.client-form"); }
    public Element getBrandingLocationTextField() { return driver.FindElementByCssSelector("div.redactor-editor"); }

    public Element getSharedWithSG1SearchParentGroup() { return driver.FindElementByXPath("//li[@id='2g']/i"); }
    public Element getCustodianNameCheckbox() { return driver.FindElementById("236_anchor"); }
    public Element getBrandingAndRedactionNextButton() {  return driver.FindElementById("branding-and-redaction-next-button"); } 
    public Element getPDFCreationForAllDocs() { return driver.FindElementByXPath("//input[@id='onePDFForAllDocRadioButton']/following-sibling::i"); }
    public Element getBullHornNotificationNumber() { return driver.FindElementByXPath("//span[@id='activity']/b"); }

    public Element getBackgroundTaskFirstRowStatus() { return driver.FindElementByXPath("//table[@id='dt_basic']//tr[1]/td[8]"); }
    public Element getBackgroundTaskFirstRowDownloadLink() { return driver.FindElementByXPath("//table[@id='dt_basic']//tr[1]/td[9]/a"); }
    public Element getBackgroundTaskFirstRowID() { return driver.FindElementByXPath("//table[@id='dt_basic']//tr[1]/td[1]");}
    public Element getOnePDFForAllDocsRadioButton() { return driver.FindElementByXPath("//input[@id='onePDFForAllDocRadioButton']/following-sibling::i"); } 
    public Element getGenerateSuccessMessage() { return driver.FindElementById("bigBox4"); }

    public Element getSelectFolderRadioButton() { return driver.FindElementById(""); }
    public Element getSelectFolderRadioButtonIcon() { return driver.FindElementByXPath("//input[@id='selectFolderRadioButton']/following-sibling::i"); }
    public Element getSelectFolderDisplaySet() { return driver.FindElementByXPath("//div[@id='folderSet' and @style='display: block;']"); }
    public Element get250DocsFolderOption() { return driver.FindElementById("1033_anchor"); }  
    public Element getLess250DocsFolderOption() { return driver.FindElementById("1034_anchor"); }
    public Element getExpandFolderIcon() { return driver.FindElementByXPath("//*[@id='folderSet']//li[@role='treeitem']/i"); } 
    public ElementCollection getOtherExceptionFileTypesDiv() { return driver.FindElementsById("OtherExceptionFileTypesPlaceHolderHtml"); }
    public Element getPlaceholderTextInputField() { return driver.FindElementByXPath("//*[@id='excelFilePlaceHolderHtml']//div[@class='redactor-editor']"); }
    public Element getSkipExcelPlaceholderTextInputField() { return driver.FindElementByXPath("//*[@id='excelFilePlaceHolderHtml']//div[@class='redactor-editor']"); }
    public Element getPrintExcelPlaceholderTextInputField() { return driver.FindElementByXPath("//ul[@id='redactor-toolbar-1']/following-sibling::div"); }
    public Element getExcelFileOptions() { return driver.FindElementByCssSelector("div.batch-print div.row.step-source-selection div.col-md-8.smart-form"); }
    public Element getBasisForPrintingHeader() { return driver.FindElementByXPath("//h2[contains(text(), 'Basis for Printing')]"); }
    public Element getSourceSelectionBackBtn() {return driver.FindElementById("basis-for-printing-back-button");}
    public Element getSourceSelectionSelectFolderRadioButton() {return driver.FindElementByCssSelector("#selectSearchRadioButton + i");}
    public ElementCollection getSourceSelectionSelectSearchRows() {return driver.FindElementsByCssSelector("#searchTree a");}
    public Element getBasisForPrintingProductionDropDownOption(String option) { return driver.FindElementByXPath(String.format("//select[@id = 'ProductionDropDown']/option[text() = '%s']", option));}
    public ElementCollection getBatchPrintAnalysisDocumentText() {return driver.FindElementsByCssSelector("#noIssuesDocDiv ul li");}
    public ElementCollection getBatchPrintAnalysisColumnHeaders() {return driver.FindElementsByCssSelector("#issueDocGrid thead th");}
    public ElementCollection getBtachPrintAnalysisFolderTree() {return driver.FindElementsById("folderTree");}
    public Element getGreenPopUpMessage() {return driver.FindElementByCssSelector("#divbigBoxes p");}
    public Element getBackGroundTasksApplyButton() {return driver.FindElementById("btnAppyFilter");}
    public ElementCollection getBackGroundTasksTableRows() {return driver.FindElementsByCssSelector("#dt_basic tbody tr ");}

    public ElementCollection getIncludeOtherExceptionFileTypesCheckBox() { return driver.FindElementsById("includeOtherExceptionFileTypesCheckBox"); }
    public ElementCollection getAllBrandingToggleButtons() { return driver.FindElementsByCssSelector(".btn.btn-default.btn-xs"); }
    public Element getIncludeAppliedRedactionsToggle() { return driver.FindElementByXPath("//input[@id='includeAppliedRedactions']/following-sibling::i"); }
    public Element getOpaqueTransparentDiv() { return driver.FindElementById("OpaqueTransparentDiv"); }
    public Element getTopCenterBrandingLocationButton() { return driver.FindElementById("btn-batchprint-branding-1"); }
    public Element getInsertMetadataFieldLink() { return driver.FindElementById("insertMetaDataHref"); }
    public Element getInsertMetadataFieldPopup() { return driver.FindElementByCssSelector(".popover.fade.right.in"); }
    public Element getMetadataDropdown() { return driver.FindElementById("locationMetaData"); }    
    public Element getMediaFilesHelpIcon() { return driver.FindElementByXPath("//a[@data-original-title='Media Files']"); }
    public Element getExcelFilesHelpIcon() { return driver.FindElementByXPath("//a[@data-original-title='Excel Files']"); }
    public Element getOtherFileTypesHelpIcon() { return driver.FindElementByXPath("//a[@data-original-title='Other Exception File Types']"); }
    public Element getFileTypeHelpPopup() { return driver.FindElementByCssSelector(".popover.fade.right.in"); }
    public Element getCurrentBreadcrumb(String breadcrumb) { return driver.FindElementByXPath("//li[@class='current-crumb' and @title='"+breadcrumb+"']");}
    public Element getSkipExcelFilesRadioButton() { return driver.FindElementByXPath("//input[@id='skipExcelFileRadioButton']/following-sibling::i"); }
    public Element getIncludePlaceholdersToggle() { return driver.FindElementByXPath("//input[@id='includeExcelFileCheckBox']/following-sibling::i"); }
    public Element getInsertMetadataFieldLinkText() { return driver.FindElementById("excelFileInsertMetadataLinkButton"); }
    public Element getSlipSheetsWorkProductTab() { return driver.FindElementByXPath("//span[text()='WORKPRODUCT']"); }
    public Element getSlipSheetsAllTagsToggle() { return driver.FindElementByXPath("//a[text()='All Tags']/preceding-sibling::i"); }
    public Element getSlipSheetsAllTagsList() { return driver.FindElementByXPath("//a[text()='All Tags']/preceding-sibling::i/parent::li[contains(@class, 'open')]]"); }
    public Element getSlipSheetsAllRedactionTagsToggle() { return driver.FindElementByXPath("//a[text()='All Redaction Tags']/preceding-sibling::i"); }
    public Element getSlipSheetsAllRedactionTagsList() { return driver.FindElementByXPath("//a[text()='All Redaction Tags']/preceding-sibling::i/parent::li[contains(@class, 'open')]"); }
    public ElementCollection getSlipSheetsAllTagsOptions() { return driver.FindElementsByXPath("//a[text()='All Tags']/parent::li/ul[@class='jstree-children']//a"); }
    public ElementCollection getSlipSheetsAllRedactionTagsOptions() { return driver.FindElementsByXPath("//a[text()='All Redaction Tags']/parent::li/ul[@class='jstree-children']//a"); }

    public Element getSearchRadioButton(){ return driver.FindElementByXPath(".//*[@id='selectSearchRadioButton']/following-sibling::i"); }
    public Element getSearchSG1ExpandFolderIcon(){ return driver.FindElementByXPath("//*[@id='searchTree']/ul/li[7]/i"); }
    public Element getSearchSelectFile(){ return driver.FindElementByXPath("//*[@id='searchTree']/ul/li[7]//li[1]/a"); }
    public Element getTagsRadioButton(){ return driver.FindElementByXPath(".//*[@id='selectTagRadioButton']/following-sibling::i"); }
    public Element getSelectAllTagsExpandFolder(){ return driver.FindElementByXPath("//*[@id=\"tagTree\"]/ul/li/i"); }
    public Element getDefaulTags() { return driver.FindElementByXPath("//*[@class='jstree-children']/li[4]/a[1]/i[1]"); }
    public Element get0Docs() { return driver.FindElementByXPath("//*[@class='jstree-children']/li[1]/a[1]/i[1]"); }
    public Element get0DocsAnalysisTitleText() { return driver.FindElementByXPath("//*[@id=\"view-content\"]/div[3]/div/div/div/div/h4"); }
    public Element get0DocsAnalysisText() { return driver.FindElementByXPath("//*[@id=\"view-content\"]/div[3]/div/div/div/div/div"); }
    public Element getFolderRadioButton(){ return driver.FindElementByXPath(".//*[@id='selectFolderRadioButton']/following-sibling::i"); }
    public Element getFolderAllTagsExpandFolder(){ return driver.FindElementByXPath("//*[@id='folderTree']/ul/li/i"); }
    public Element getPriorProduction() { return driver.FindElementByXPath(".//*[@id='priorProductionRadioButton']/following-sibling::i"); }
    public Element getPriorDefaultProductionOption() { return driver.FindElementByXPath("//*[@id=\"ProductionDropDown\"]//option[1]"); }
    public Element getAnalysisFolderDocExpand() { return driver.FindElementByXPath("//*[@id='folderTree']/ul/li/i"); }
    public Element getAnalysisDefaultProductionOption() { return driver.FindElementByXPath("//*[@id='folderTree']/ul/li/ul/li[10]/a"); }
    public ElementCollection getBackGroundTaskCompleteNotification(String id) {return driver.FindElementsByCssSelector(String.format("#bgTask span[id = '%s']", id));}
    public Element getOpenNotificationsMenu() {return driver.FindElementByCssSelector("#activity i");}
    public Element getFirstBackgroundTaskInProgress() {return driver.FindElementByXPath("//*[@id='dt_basic']/tbody/tr[1]/td[.='INPROGRESS']");}
    public Element getFirstBackgroundTaskCompleted() {return driver.FindElementByXPath("//*[@id='dt_basic']/tbody/tr[1]/td[.='COMPLETED']");}
    public Element changeProjectSelector() {return driver.FindElementById("project-selector");}
    public Element changeProjectSelectorField() {return driver.FindElementByCssSelector("#ddlProject11 > li:nth-child(1) > a:nth-child(1)");}
    public Element getSlipSheetsMetaDataCheckBoxByName(String name) {return driver.FindElementByCssSelector(String.format("#tab1 li input[data-friendlbl='%s'] + i",name));}
    public Element getAllFoldersExpandArrow() {return driver.FindElementByXPath("(//a[@data-content = 'All Folders']//preceding-sibling::i)[1]");}
    public Element getWorkProductFolderCheckBoxByName(String name) {return driver.FindElementByXPath(String.format("(//a[@data-content = '%s']/i)[1]",name));}
    
    public BatchPrintPage(Driver driver, int i) {
    		this.driver = driver;
    }
    public BatchPrintPage(Driver driver){

        this.driver = driver;
        this.driver.getWebDriver().get(Input.url+"BatchPrint/");
        base = new BaseClass(driver);
       }
    
    
    public void BatchPrintWithNative(String type, String name,String orderCriteria, String orderType) throws InterruptedException{
     	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMenuBatchPrint().Visible()  ;}}), Input.wait30); 
    	
    	
		
    	getMenuBatchPrint().Click();
		
    	if(type.equalsIgnoreCase("search")){
    	getSearchBatchPrint().waitAndClick(10);
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getMySavedSearchArrow().Visible()  ;}}), Input.wait30); 
		getMySavedSearchArrow().Click();
		
		getSelectSavedSearch(name).waitAndClick(5);
    	}
    	else if(type.equalsIgnoreCase("tag")){
        	getTagBatchPrint().waitAndClick(10);
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getAllTagsArrow().Visible()  ;}}), Input.wait30); 
        	getAllTagsArrow().Click();
        	getSelectTag(name).waitAndClick(10);
    	}
    	
    	else if(type.equalsIgnoreCase("folder")){
        	getFolderBatchPrint().waitAndClick(10);
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getAllFoldersArrow().Visible()  ;}}), Input.wait30); 
        	getAllFoldersArrow().Click();
        	getSelectFolder(name).waitAndClick(10);
    	
    	}
		
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSourcenextbutton().Enabled()  ;}}), Input.wait30); 
		
	
		
		getSourcenextbutton().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getBasisnextbutton().Enabled()  ;}}), Input.wait30); 
		getBasisnextbutton().waitAndClick(5);
		
		try{
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getAnalysis_Ignore_RadioButton().Visible() ;}}), Input.wait30); 
		getAnalysis_Ignore_RadioButton().waitAndClick(5);
		}catch (Exception e) {
			// TODO: handle exception
		}
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getAnalysisnextbutton().Enabled()  ;}}), Input.wait30); 
		getAnalysisnextbutton().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getExceptiontypenextbutton().Enabled()  ;}}), Input.wait30); 
		getExceptiontypenextbutton().waitAndClick(5);
		Thread.sleep(2000);
		
		driver.scrollingToBottomofAPage();
		
		//Driver.scrollingToElementofAPage(getSelectCustodianName());
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectCustodianName().Visible()  ;}}), Input.wait30); 
		getSelectCustodianName().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectDOCID().Visible()  ;}}), Input.wait30); 
		getSelectDOCID().ScrollTo();
		getSelectDOCID().waitAndClick(10);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectMasterDate().Visible()  ;}}), Input.wait30); 
		getSelectMasterDate().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectSourceDOCID().Visible()  ;}}), Input.wait30); 
		getSelectSourceDOCID().waitAndClick(5);
		
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectColumnAddtoSelected().Enabled()  ;}}), Input.wait30); 
		getSelectColumnAddtoSelected().waitAndClick(5);
		
		driver.scrollPageToTop();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSlipnextbutton().Enabled()  ;}}), Input.wait30); 
		getSlipnextbutton().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getBrandingCentre().Visible()  ;}}), Input.wait30); 
		getBrandingCentre().waitAndClick(5);
		//Thread.sleep(2000);
		getBatchPrintEnterBranding().waitAndFind(5);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getBatchPrintEnterBranding().Enabled() ;}}), Input.wait30); 
		getBatchPrintEnterBranding().ScrollTo();
		new Actions(driver.getWebDriver()).moveToElement(getBatchPrintEnterBranding().getWebElement()).click();
		//getTIFF_EnterBranding().Click();
    	getBatchPrintEnterBranding().SendKeys("Test");
    	//Thread.sleep(2000);
    		
	  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			  getInsertMetadataFieldOKButton().Visible()  ;}}), Input.wait30); 
		getInsertMetadataFieldOKButton().waitAndClick(5);
		Thread.sleep(2000);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getBrandingnextbutton().Visible()  ;}}), Input.wait30); 
		getBrandingnextbutton().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectExportFileName().Enabled()  ;}}), Input.wait30); 
		Thread.sleep(4000);
		getSelectExportFileName().selectFromDropdown().selectByVisibleText("DocID");
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectExportFileName().Enabled()  ;}}), Input.wait30); 
		getSelectExportFileSortBy().selectFromDropdown().selectByVisibleText("DocID");
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getGenerateButton().Enabled()  ;}}), Input.wait30); 
		getGenerateButton().waitAndClick(5);
		
		base.VerifySuccessMessage("Successfully initiated the batch print. You will be prompted with notification once completed.");
		
		for (int i=0;i<30;i++) {
			try {	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbackgroundDownLoadLink().Visible() && getbackgroundDownLoadLink().Enabled() ;}}), Input.wait120); 
		if(getbackgroundDownLoadLink().Visible() && getbackgroundDownLoadLink().Enabled())
			break;
		}
		catch (Exception e)
		{
			driver.Navigate().refresh();
			System.out.println("Refresh");
		}
		}	
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTaskType().Displayed() ;}}), Input.wait30); 
		String status = getBatchPrintStatus().getText();
		Assert.assertEquals("COMPLETED", status);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDownLoadLink().Enabled()  ;}}), Input.wait30); 
		getDownLoadLink().waitAndClick(40);
		//download time
		Thread.sleep(15000);
		
		
	   }
    
    /**
    * This Function is to read all the docID from files in a given directory and check whether IDs are in ascending order or not
    * We need to Pass directory path to the function.
    * 
     * @throws IOException
    * @throws InterruptedException 
     */
    public static void checkForOrderInPDF(String orderCriteria, String orderType) throws IOException, InterruptedException {
           //Call unzip function to unzip the downloaded file
    	   
    	File rootZip = new File("C:\\BatchPrintFiles\\downloads");
   File[] listZip = rootZip.listFiles();
   File zipFilePath = listZip[0];
   String destDir = "C:\\BatchPrintFiles\\PDFs";
   unzip(zipFilePath, destDir);
   
   //validate each fileonce unzip is done
   File root = new File("C:\\BatchPrintFiles\\PDFs");
   File[] list = root.listFiles();
   
   ArrayList orderInFiles = new ArrayList();
   List expectedOrder = new ArrayList(orderInFiles);
   
   for ( File f : list ) {
       if (!f.isDirectory() ) {
                                             
                                      try (PDDocument document = PDDocument.load(new File(f.getAbsoluteFile().toString()))) {
                                           document.getClass();
                                           System.out.println(f.getAbsoluteFile().toString());
                                           if (!document.isEncrypted()) {
                                               PDFTextStripperByArea stripper = new PDFTextStripperByArea();
                                               stripper.setSortByPosition(true);
                                               PDFTextStripper tStripper = new PDFTextStripper();
                                               String pdfFileInText = tStripper.getText(document);
                                                           // split by whitespace
                                               String lines[] = pdfFileInText.split("\\r?\\n");
                                             
                               
                                               //read only required lines!
                                               for (String line : lines) {
                                                   
                                                   if(orderCriteria.equals("DocID:")){
                                                           //System.out.println("***********************Testing DocID***********************************");
                                                                         if(line.contains(orderCriteria) &&!line.contains("SourceDocID:")){
                                                                                //Extract only digits
                                                                                int numberOnly= Integer.parseInt(line.replaceAll("[^0-9]", ""));
                                                                                //System.out.println(numberOnly);
                                                                                orderInFiles.add(numberOnly);
                                                                         }
                                                    }
                                                    else if(orderCriteria.equals("MasterDate:")||
                                                                  orderCriteria.equals("CreateDate:")||
                                                                  orderCriteria.equals("EmailSentDate:")||
                                                                  orderCriteria.equals("LastModifiedDate:")||
                                                                  orderCriteria.equals("LastEditDate:")||
                                                                  orderCriteria.equals("DocDate:")||
                                                                  orderCriteria.equals("LastSaveDate:")){
                                                           //System.out.println("***********************Testing MasterDate***********************************");
                                                           if(line.contains(orderCriteria)){
                                                                  if(!line.substring(line.indexOf("Date:")+5).toString().isEmpty()&&!line.substring(line.indexOf("Date:")+5).equalsIgnoreCase(" ")){
                                                                        //System.out.println("---"+line.substring(line.indexOf("Date:")+5).toString());
                                                                        System.out.println(f.getAbsoluteFile().toString());
                                                                        System.out.println(line.substring(line.indexOf("Date:")+5).toString().trim());
                                                                        orderInFiles.add(line.substring(line.indexOf("Date:")+5).toString().trim());
                                                                       
                                                                  }
                                                           }
                                                          
                                                    }
                                                    else if(orderCriteria.equals("CustodianName:")||
                                                                  orderCriteria.equals("DocFileName:")){
                                                           //System.out.println("***********************Testing MasterDate***********************************");
                                                           if(line.contains(orderCriteria)){
                                                                  if(!line.substring(line.indexOf("Name:")+5).toString().isEmpty()&&!line.substring(line.indexOf("Name:")+5).equalsIgnoreCase(" ")){
                                                                        //System.out.println("---"+line.substring(line.indexOf("Date:")+5).toString());
                                                                        System.out.println(f.getAbsoluteFile().toString());
                                                                        System.out.println(line.substring(line.indexOf("Name:")+5).toString().trim());
                                                                        orderInFiles.add(line.substring(line.indexOf("Name:")+5).toString().trim());
                                                                       
                                                                  }
                                                           }
                                                    }
                                                    
                                                    else{
                                                           System.out.println("Please passright criteria!!");
                                                    }
                                               }
                                            }
                                       }
       }
      
   }
   
   if(orderCriteria.equals("DocID:")){
	   
       //lets copy the output to other arraylist and sort it.  
      // System.out.println("Actual DOC IDs orderin file : "+orderInFiles);
	 System.out.println("Actual "+orderCriteria.replace(":", "")+"s order from downloaded files : "+orderInFiles);
	   expectedOrder.addAll(orderInFiles);


       Collections.sort(expectedOrder);
       if(orderType.equalsIgnoreCase("Desc")){
       Collections.reverse(expectedOrder);   
       }
       
     //  System.out.println("Expected DocIDs Order : "+expectedOrder);
       System.out.println("Expected "+orderCriteria.replace(":", "")+" order : "+expectedOrder);
       //Now compare sorted arraylist with output
       if(expectedOrder.equals(orderInFiles)){
    	   System.out.println(orderCriteria+" is in requested order! -->PASS");
       }else{
           // System.out.println("FAIL");
    	   System.out.println(orderCriteria+" is NOT in requested order! -->FAIL");
    	   Assert.assertTrue(expectedOrder.equals(orderInFiles));
       }
      
expectedOrder.clear();
orderInFiles.clear();
} 

   
   if(orderCriteria.equals("CustodianName:")||
                  orderCriteria.equals("DocFileName:")){
   
      //
     // System.out.println("Actual "+orderCriteria+" orderin file : "+orderInFiles);
            expectedOrder.addAll(orderInFiles);
           //sort strings!!
           expectedOrder = sortStrings(expectedOrder);
           
                   
           if(orderType.equalsIgnoreCase("Desc")){
                   Collections.reverse(expectedOrder);   
                   }
                   
                 
      expectedOrder.clear();
       orderInFiles.clear();
   }
      
   else if(orderCriteria.equals("MasterDate:")||orderCriteria.equals("CreateDate:")||orderCriteria.equals("SendDateTime")||
             orderCriteria.equals("EmailSentDate:")||orderCriteria.equals("LastModifiedDate:")||orderCriteria.equals("LastEditDate:")||
             orderCriteria.equals("DocDate:")||orderCriteria.equals("LastSaveDate:")){
   
            //lets copy the output to other arraylist and sort it.  
                 expectedOrder.addAll(orderInFiles);
                 //Decending Order! so, sort first and then reverse the list!
                 expectedOrder = sortDates(expectedOrder);
                 if(orderType.equalsIgnoreCase("Desc")){
                         Collections.reverse(expectedOrder);   
                         }
            
            //Now compare sorted arraylist with output
            if(expectedOrder.equals(orderInFiles)){
                 //System.out.println("Pass");
                 //Add_Log.info(TestCaseName + ":- Actual Count :-'");
                 System.out.println(orderCriteria.replace(":", "")+" is in requested order! -->PASS");
            }else{
                // System.out.println("FAIL");
            	System.out.println(orderCriteria.replace(":", "")+" is NOT in requested order! -->FAIL");
            	Assert.assertTrue(expectedOrder.equals(orderInFiles));
            }
           
               expectedOrder.clear();
               orderInFiles.clear();
             
}
   
  backUpBatchPrintFiles(orderCriteria,orderType);
}

     private static void unzip(File zipFilePath, String destDir) {
       File dir = new File(destDir);
       // create output directory if it doesn't exist
       if(!dir.exists()) dir.mkdirs();
       FileInputStream fis;
       //buffer for read and write data to file
       byte[] buffer = new byte[1024];
       try {
           fis = new FileInputStream(zipFilePath.toString());
           ZipInputStream zis = new ZipInputStream(fis);
           ZipEntry ze = zis.getNextEntry();
           while(ze != null){
               String fileName = ze.getName();
               File newFile = new File(destDir + File.separator + fileName);
               System.out.println("Unzipping to "+newFile.getAbsolutePath());
               //create directories for sub directories in zip
               new File(newFile.getParent()).mkdirs();
               FileOutputStream fos = new FileOutputStream(newFile);
               int len;
               while ((len = zis.read(buffer)) > 0) {
               fos.write(buffer, 0, len);
               }
               fos.close();
               //close this ZipEntry
               zis.closeEntry();
               ze = zis.getNextEntry();
           }
           //close last ZipEntry
           zis.closeEntry();
           zis.close();
           fis.close();
          // Add_Log.info(TestCaseName+": Unziped done successfully");
       } catch (IOException e) {
       //Add_Log.info(TestCaseName+": Failed to unzip the file");
           e.printStackTrace();
       }
       
    }

public static void backUpBatchPrintFiles(String orderCriteria, String order) {
    //back up TCs zip file for later verification
    File destinationFolder = new File("C:\\BatchPrintFiles\\BackUpFiles");
        File sourceFolder = new File("C:\\BatchPrintFiles\\downloads");
      
        if (!destinationFolder.exists())
        {
            destinationFolder.mkdirs();
        }

        // Check weather source exists and it is folder.
        if (sourceFolder.exists() && sourceFolder.isDirectory())
        {
        
            // Get list of the files and iterate over them
            File[] listOfFiles = sourceFolder.listFiles();

            if (listOfFiles != null)
            {
                for (File child : listOfFiles )
                {
                    // Move files to destination folder
                    child.renameTo(new File(destinationFolder + "\\--"+orderCriteria.replace(":", "")+"--"+order+"--"+child.getName()));
                }

                // Add if you want to delete the source folder 
               // sourceFolder.delete();
            }
        }
        else
        {
            System.out.println(sourceFolder + "  Folder does not exists");
        }
        
        //delete last TCs PDF from PDFs folder
        File file = new File("C:\\BatchPrintFiles\\PDFs");      
        String[] myFiles;    
            if(file.isDirectory()){
                myFiles = file.list();
                for (int i=0; i<myFiles.length; i++) {
                    File myFile = new File(file, myFiles[i]); 
                    myFile.delete();
                }
             }
}

@SuppressWarnings("unchecked")
public static List sortDates(List expectedOrder){
              Collections.sort(expectedOrder, new Comparator<String>() {
                   
                       public int compare(String object1, String object2) {
                           return object1.compareTo(object2);
                       }
                   });
              return expectedOrder;
} 

@SuppressWarnings("unchecked")
private static List sortStrings(List names) {
      Collections.sort(names, new Comparator<String>() {
               public int compare(String o1, String o2) {              
                   return o1.compareToIgnoreCase(o2);
               }
           });
      return names;
} 

 //********verify that user can view the details on Exception File Types****************
    public void BatchPrintWitExceptionalFile(final String searchname) throws InterruptedException{
 	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getMenuBatchPrint().Visible()  ;}}), Input.wait30); 
	 getMenuBatchPrint().Click();
	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getMySavedSearchArrow().Visible()  ;}}), Input.wait30); 
	getMySavedSearchArrow().Click();
	
	getSelectSavedSearch(searchname).waitAndClick(15);
	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getSourcenextbutton().Enabled()  ;}}), Input.wait30); 
	getSourcenextbutton().waitAndClick(5);
	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getBasisnextbutton().Enabled()  ;}}), Input.wait30); 
	getBasisnextbutton().waitAndClick(5);
	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getAnalysisnextbutton().Enabled()  ;}}), Input.wait30); 
	getAnalysisnextbutton().waitAndClick(5);
	
	driver.scrollingToBottomofAPage();
	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getAnalysis_SkipExcelFiles_RadioButton().Visible()  ;}}), Input.wait30); 
	getAnalysis_SkipExcelFiles_RadioButton().waitAndClick(5);
	

	try {
		getBP_Exception_Media().Displayed();
		//insert metadata for media files
		/*driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getMedia_InsertMetadata().Visible()  ;}}), Input.wait30); */
		getMedia_InsertMetadata().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getselectMetadataFields_Media().Enabled()  ;}}), Input.wait30); 
		getselectMetadataFields_Media().selectFromDropdown().selectByVisibleText("CustodianName");
		
		getOkButton_Media().waitAndClick(5);
	} catch (Exception e1) {
		
		System.out.println("No Media file displayed");
	}
	
	try {
		getBP_Exception_Excel().Displayed();
		
		getExcel_InsertMetadata().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getselectMetadataFields_Excel().Visible()  ;}}), Input.wait30); 
		getselectMetadataFields_Excel().selectFromDropdown().selectByVisibleText("DocID");
		
	/*	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getOkButton_Excel().Visible()  ;}}), Input.wait30); */
		getOkButton_Excel().waitAndClick(5);
	} catch (Exception e1) {
		
		System.out.println("No Excel file displayed");
	}
	
	driver.scrollingToBottomofAPage();
	try {
		getBP_Exception_Other().Displayed();
	
		getOther_InsertMetadata().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getselectMetadataFields_Other().Visible()  ;}}), Input.wait30); 
		getselectMetadataFields_Other().selectFromDropdown().selectByVisibleText("MasterDate");
		
		getOkButton_Other().waitAndClick(5);
	} catch (Exception e1) {
		
		System.out.println("No Other file displayed");
	}
	
	driver.scrollPageToTop();
	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getExceptiontypenextbutton().Enabled()  ;}}), Input.wait30); 
	getExceptiontypenextbutton().waitAndClick(5);
	
	driver.scrollingToBottomofAPage();
	
	
	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getSelectDOCID().Visible()  ;}}), Input.wait30); 
	getSelectDOCID().waitAndClick(5);
	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getSelectMasterDate().Visible()  ;}}), Input.wait30); 
	getSelectMasterDate().waitAndClick(5);
	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getSelectSourceDOCID().Visible()  ;}}), Input.wait30); 
	getSelectSourceDOCID().waitAndClick(5);
	
	driver.scrollingToBottomofAPage();
	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getSelectColumnAddtoSelected().Enabled()  ;}}), Input.wait30); 
	getSelectColumnAddtoSelected().waitAndClick(5);
	
	driver.scrollPageToTop();
	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getSlipnextbutton().Enabled()  ;}}), Input.wait30); 
	getSlipnextbutton().waitAndClick(5);
	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getBrandingCentre().Visible()  ;}}), Input.wait30); 
	getBrandingCentre().waitAndClick(5);
	//Thread.sleep(2000);
	getBatchPrintEnterBranding().waitAndFind(5);
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getBatchPrintEnterBranding().Enabled() ;}}), Input.wait30); 
	getBatchPrintEnterBranding().ScrollTo();
	new Actions(driver.getWebDriver()).moveToElement(getBatchPrintEnterBranding().getWebElement()).click();
	//getTIFF_EnterBranding().Click();
	getBatchPrintEnterBranding().SendKeys("Test");
	//Thread.sleep(2000);
		
  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		  getInsertMetadataFieldOKButton().Visible()  ;}}), Input.wait30); 
	getInsertMetadataFieldOKButton().waitAndClick(5);
	Thread.sleep(2000);
	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getBrandingnextbutton().Visible()  ;}}), Input.wait30); 
	getBrandingnextbutton().waitAndClick(5);
	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getSelectExportFileName().Enabled()  ;}}), Input.wait30); 
	getSelectExportFileName().selectFromDropdown().selectByVisibleText("DocID");
	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getSelectExportFileName().Enabled()  ;}}), Input.wait30); 
	getSelectExportFileSortBy().selectFromDropdown().selectByVisibleText("DocID");
	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getGenerateButton().Enabled()  ;}}), Input.wait30); 
	getGenerateButton().waitAndClick(5);
	
	base.VerifySuccessMessage("Successfully initiated the batch print. You will be prompted with notification once completed.");
	
	for (int i=0;i<20;i++) {
		try {	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getbackgroundDownLoadLink().Visible() && getbackgroundDownLoadLink().Enabled() ;}}), Input.wait120); 
	if(getbackgroundDownLoadLink().Visible() && getbackgroundDownLoadLink().Enabled())
		break;
	}
	catch (Exception e)
	{
		driver.Navigate().refresh();
		System.out.println("Refresh");
	}
	}	
	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getTaskType().Displayed() ;}}), Input.wait30); 
	String status = getBatchPrintStatus().getText();
	Assert.assertEquals("COMPLETED", status);
	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getDownLoadLink().Enabled()  ;}}), Input.wait30); 
	getDownLoadLink().waitAndClick(5);
	//download time
	Thread.sleep(10000);
	  }

     public void BatchPrintWithProduction(String searchname,String orderCriteria, String orderType) throws InterruptedException{
     	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMenuBatchPrint().Visible()  ;}}), Input.wait30); 
    	getMenuBatchPrint().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getMySavedSearchArrow().Visible()  ;}}), Input.wait30); 
		getMySavedSearchArrow().Click();
		
		getSelectSavedSearch(searchname).waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSourcenextbutton().Enabled()  ;}}), Input.wait30); 
		getSourcenextbutton().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProductionRadioButton().Enabled()  ;}}), Input.wait30); 
		getProductionRadioButton().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectProduction().Enabled()  ;}}), Input.wait30); 
		getSelectProduction().selectFromDropdown().selectByIndex(1);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getBasisnextbutton().Enabled()  ;}}), Input.wait30); 
		getBasisnextbutton().waitAndClick(5);
		Thread.sleep(2000);
		
		driver.scrollingToBottomofAPage();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSkippedFolderButton().Enabled()  ;}}), Input.wait30); 
		getSkippedFolderButton().waitAndClick(5);
		
		driver.scrollPageToTop();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getAnalysisnextbutton().Enabled()  ;}}), Input.wait30); 
		getAnalysisnextbutton().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSlipnextbutton().Enabled()  ;}}), Input.wait30); 
		getSlipnextbutton().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectExportFileName().Enabled()  ;}}), Input.wait30); 
		getSelectExportFileName().selectFromDropdown().selectByVisibleText("DocFileName");
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectExportFileName().Enabled()  ;}}), Input.wait30); 
		getSelectExportFileSortBy().selectFromDropdown().selectByVisibleText("CustodianName");
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getPDFCreationforAllButton().Enabled()  ;}}), Input.wait30); 
		getPDFCreationforAllButton().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getGenerateButton().Enabled()  ;}}), Input.wait30); 
		getGenerateButton().waitAndClick(5);
		
		base.VerifySuccessMessage("Successfully initiated the batch print. You will be prompted with notification once completed.");
		
		for (int i=0;i<20;i++) {
			try {	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbackgroundDownLoadLink().Visible() && getbackgroundDownLoadLink().Enabled() ;}}), Input.wait30); 
		if(getbackgroundDownLoadLink().Visible() && getbackgroundDownLoadLink().Enabled())
			break;
		}
		catch (Exception e)
		{
			driver.Navigate().refresh();
			System.out.println("Refresh");
		}
		}	
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTaskType().Displayed() ;}}), Input.wait30); 
		String status = getBatchPrintStatus().getText();
		Assert.assertEquals("COMPLETED", status);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDownLoadLink().Enabled()  ;}}), Input.wait30); 
		getDownLoadLink().waitAndClick(5);
		//download time
		Thread.sleep(10000);
		   }
     
   //*******To verify that batch print can give error if MP3 file is not native file ****************
     public void BatchPrintWitMP3(final String searchname) throws InterruptedException{
  	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getMenuBatchPrint().Visible()  ;}}), Input.wait30); 
 	 getMenuBatchPrint().Click();
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getMySavedSearchArrow().Visible()  ;}}), Input.wait30); 
 	getMySavedSearchArrow().Click();
 	
 	getSelectSavedSearch(searchname).waitAndClick(15);
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getSourcenextbutton().Enabled()  ;}}), Input.wait30); 
 	getSourcenextbutton().waitAndClick(5);
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getBasisnextbutton().Enabled()  ;}}), Input.wait30); 
 	getBasisnextbutton().waitAndClick(5);
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getAnalysisnextbutton().Enabled()  ;}}), Input.wait30); 
 	getAnalysisnextbutton().waitAndClick(5);
 	
 	driver.scrollingToBottomofAPage();
 	
 	try {
 		getBP_Exception_Other().Displayed();
 	
 		getOther_InsertMetadata().waitAndClick(5);
 		
 		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 				getselectMetadataFields_Other().Visible()  ;}}), Input.wait30); 
 		getselectMetadataFields_Other().selectFromDropdown().selectByVisibleText("MasterDate");
 		
 		getOkButton_Other().waitAndClick(5);
 	} catch (Exception e1) {
 		
 		System.out.println("No Other file displayed");
 	}
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getExceptiontypenextbutton().Enabled()  ;}}), Input.wait30); 
 	getExceptiontypenextbutton().waitAndClick(5);
 	
 	driver.scrollingToBottomofAPage();
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getSelectSourceDOCID().Visible()  ;}}), Input.wait30); 
 	getSelectSourceDOCID().waitAndClick(5);
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getSelectDOCID().Visible()  ;}}), Input.wait30); 
 	getSelectDOCID().waitAndClick(5);
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getSelectMasterDate().Visible()  ;}}), Input.wait30); 
 	getSelectMasterDate().waitAndClick(5);
 	
 	driver.scrollingToBottomofAPage();
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getSelectColumnAddtoSelected().Enabled()  ;}}), Input.wait30); 
 	getSelectColumnAddtoSelected().waitAndClick(5);
 	
 	driver.scrollPageToTop();
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getSlipnextbutton().Enabled()  ;}}), Input.wait30); 
 	getSlipnextbutton().waitAndClick(5);
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getBrandingCentre().Visible()  ;}}), Input.wait30); 
 	getBrandingCentre().waitAndClick(5);
 
 	getBatchPrintEnterBranding().waitAndFind(5);
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getBatchPrintEnterBranding().Enabled() ;}}), Input.wait30); 
 	getBatchPrintEnterBranding().ScrollTo();
 	new Actions(driver.getWebDriver()).moveToElement(getBatchPrintEnterBranding().getWebElement()).click();
 	
 	getBatchPrintEnterBranding().SendKeys("Test");
 	
   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 		  getInsertMetadataFieldOKButton().Visible()  ;}}), Input.wait30); 
 	getInsertMetadataFieldOKButton().waitAndClick(5);
 	Thread.sleep(2000);
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getBrandingnextbutton().Visible()  ;}}), Input.wait30); 
 	getBrandingnextbutton().waitAndClick(5);
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getSelectExportFileName().Enabled()  ;}}), Input.wait30); 
 	getSelectExportFileName().selectFromDropdown().selectByVisibleText("DocID");
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getSelectExportFileName().Enabled()  ;}}), Input.wait30); 
 	getSelectExportFileSortBy().selectFromDropdown().selectByVisibleText("MasterDate");
 	
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getGenerateButton().Enabled()  ;}}), Input.wait30); 
 	getGenerateButton().waitAndClick(5);
 	
 	base.VerifySuccessMessage("Successfully initiated the batch print. You will be prompted with notification once completed.");
 	
 	//base = new BaseClass(driver);
 //	base.BckTaskClick();
 	for (int i=0;i<5;i++) {
 		try {	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getbackgroundDownLoadLink().Visible() && getbackgroundDownLoadLink().Enabled() ;}}), Input.wait30); 
 	if(getbackgroundDownLoadLink().Visible() && getbackgroundDownLoadLink().Enabled())
 		break;
 	}
 	catch (Exception e)
 	{
 		driver.Navigate().refresh();
 		System.out.println("Refresh");
 	}
 	}	
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getTaskType().Displayed() ;}}), Input.wait30); 
 	String status = getBatchPrintStatus().getText();
 	Assert.assertEquals("COMPLETED", status);
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getErrorInfoLink().Enabled()  ;}}), Input.wait30); 
 	getErrorInfoLink().waitAndClick(5);
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getErrortext().Displayed() ;}}), Input.wait30); 
 	String ErrorText = getErrortext().getText();
 	Assert.assertEquals("The following operation has failed: Detail: Cannot print this document,"
 			+ " as file type cannot be recognized for this document.", ErrorText);
 	
 	getCloseButton().waitAndClick(5);
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getDownLoadLink().Enabled()  ;}}), Input.wait30); 
 	getDownLoadLink().waitAndClick(5);
 	//download time
 	Thread.sleep(5000);
   }
     
     
}
   
