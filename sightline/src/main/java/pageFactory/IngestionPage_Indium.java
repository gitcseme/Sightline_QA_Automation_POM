package pageFactory;

import java.util.Iterator;
import java.util.concurrent.Callable;

import org.apache.pdfbox.contentstream.operator.text.EndText;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import testScriptsSmoke.Input;

public class IngestionPage_Indium {

	Driver driver;
	public String IngestionName;
	BaseClass base;
	SoftAssert softAssertion;

	// ID's
	public Element getSpecifySourceSystem() {
		return driver.FindElementById("ddlSourceSystem");
	}

	public Element getSpecifySourceFolder() {
		return driver.FindElementById("ddlFolders");
	}

	public Element getSpecifyLocation() {
		return driver.FindElementById("ddlServerLocation");
	}

	public Element getSpecifySourceIngestionType() {
		return driver.FindElementById("ddlOverwrite");
	}

	public Element getDATDelimitersFieldSeparator() {
		return driver.FindElementById("ddlColumnDelimiter");
	}

	public Element getDATDelimitersTextQualifier() {
		return driver.FindElementById("ddlTextQualifier");
	}

	public Element getDATDelimitersNewLine() {
		return driver.FindElementById("ddlDataDelimiter");
	}

	public Element getSourceSelectionDAT() {
		return driver.FindElementById("chkDAT");
	}

	public Element getSourceSelectionDATLoadFile() {
		return driver.FindElementById("ddlLoadDatFile");
	}

	public Element getSourceSelectionDATKey() {
		return driver.FindElementById("ddlKeyDatFile");
	}

	public Element getSourceSelectionTextLoadFile() {
		return driver.FindElementById("ddlLoadTextFile");
	}

	public Element getDocumentKey() {
		return driver.FindElementById("ddlKeyDatFile");
	}

	public Element getNativeLST() {
		return driver.FindElementById("ddlLoadNativeFile");
	}

	public Element getNativeFilePathFieldinDAT() {
		return driver.FindElementById("ddlFilesPathNative");
	}

	public Element getTextLST() {
		return driver.FindElementById("ddlLoadTextFile");
	}

	public Element getTextFilePathFieldinDAT() {
		return driver.FindElementById("ddlFilesPathText");
	}

	public Element getPDFLST() {
		return driver.FindElementById("ddlLoadPDFFile");
	}

	public Element getMP3LST() {
		return driver.FindElementById("ddlLoadMP3Variantfile");
	}

	public Element getAudioTranscriptLST() {
		return driver.FindElementById("ddlLoadTranscriptfile");
	}

	public Element getPDFFilePathFieldinDAT() {
		return driver.FindElementById("ddlFilesPathPDF");
	}

	public Element getTIFFLST() {
		return driver.FindElementById("ddlLoadTIFFfile");
	}

	public Element getTIFFFilePathFieldinDAT() {
		return driver.FindElementById("ddlFilesPathTIFF");
	}

	public Element getOtherLinkType() {
		return driver.FindElementById("ddlLoadLinkType");
	}

	public Element getOtherLoadFile() {
		return driver.FindElementById("ddlLoadOtherfile");
	}

	public Element getOtherFilePathFieldinDAT() {
		return driver.FindElementById("ddlFilesPathOther");
	}

	public Element getAddButton() {
		return driver.FindElementById("AddButton");
	}

	public Element getMappingSOURCEFIELD2() {
		return driver.FindElementById("SF_2");
	}

	public Element getMappingSOURCEFIELD3() {
		return driver.FindElementById("SF_3");
	}

	public Element getMappingSOURCEFIELD4() {
		return driver.FindElementById("SF_4");
	}

	public Element getMappingSOURCEFIELD5() {
		return driver.FindElementById("SF_5");
	}

	public Element getMappingSOURCEFIELD6() {
		return driver.FindElementById("SF_6");
	}

	public Element getMappingSOURCEFIELD7() {
		return driver.FindElementById("SF_7");
	}

	public Element getMappingSOURCEFIELD8() {
		return driver.FindElementById("SF_8");
	}

	public Element getMappingSOURCEFIELD9() {
		return driver.FindElementById("SF_9");
	}

	public Element getMappingSOURCEFIELD10() {
		return driver.FindElementById("SF_10");
	}

	public Element getMappingSOURCEFIELD11() {
		return driver.FindElementById("SF_11");
	}

	public Element getMappingSOURCEFIELD13() {
		return driver.FindElementById("SF_13");
	}

	public Element getMappingSOURCEFIELD14() {
		return driver.FindElementById("SF_14");
	}

	public Element getMappingSOURCEFIELD73() {
		return driver.FindElementById("SF_73");
	}

	public Element getMappingSOURCEFIELD75() {
		return driver.FindElementById("SF_75");
	}

	public Element getMappingSOURCEFIELD76() {
		return driver.FindElementById("SF_76");
	}

	public Element getMappingFIELDCAT2() {
		return driver.FindElementById("TY_2");
	}

	public Element getMappingFIELDCAT5() {
		return driver.FindElementById("TY_5");
	}

	public Element getMappingFIELDCAT6() {
		return driver.FindElementById("TY_6");
	}

	public Element getMappingFIELDCAT7() {
		return driver.FindElementById("TY_7");
	}

	public Element getMappingFIELDCAT8() {
		return driver.FindElementById("TY_8");
	}

	public Element getMappingFIELDCAT9() {
		return driver.FindElementById("TY_9");
	}

	public Element getMappingFIELDCAT10() {
		return driver.FindElementById("TY_10");
	}

	public Element getMappingFIELDCAT11() {
		return driver.FindElementById("TY_11");
	}

	public Element getMappingFIELDCAT13() {
		return driver.FindElementById("TY_13");
	}

	public Element getMappingFIELDCAT14() {
		return driver.FindElementById("TY_14");
	}

	public Element getMappingFIELDCAT25() {
		return driver.FindElementById("TY_25");
	}

	public Element getMappingFIELDCAT26() {
		return driver.FindElementById("TY_26");
	}

	public Element getMappingFIELDCAT27() {
		return driver.FindElementById("TY_27");
	}

	public Element getMappingFIELDCAT28() {
		return driver.FindElementById("TY_28");
	}

	public Element getMappingFIELDCAT29() {
		return driver.FindElementById("TY_29");
	}

	public Element getMappingFIELDCAT31() {
		return driver.FindElementById("TY_31");
	}

	public Element getMappingFIELDCAT46() {
		return driver.FindElementById("TY_46");
	}

	public Element getMappingFIELDCAT49() {
		return driver.FindElementById("TY_49");
	}

	public Element getMappingFIELDCAT50() {
		return driver.FindElementById("TY_50");
	}

	public Element getMappingFIELDCAT51() {
		return driver.FindElementById("TY_51");
	}

	public Element getMappingFIELDCAT73() {
		return driver.FindElementById("TY_73");
	}

	public Element getMappingFIELDCAT75() {
		return driver.FindElementById("TY_75");
	}

	public Element getMappingFIELDCAT76() {
		return driver.FindElementById("TY_76");
	}

	public Element getMappingDESTINATIONFIELD2() {
		return driver.FindElementById("DF_2");
	}

	public Element getMappingDESTINATIONFIELD3() {
		return driver.FindElementById("DF_3");
	}

	public Element getMappingDESTINATIONFIELD4() {
		return driver.FindElementById("DF_4");
	}

	public Element getMappingDESTINATIONFIELD5() {
		return driver.FindElementById("DF_5");
	}

	public Element getMappingDESTINATIONFIELD6() {
		return driver.FindElementById("DF_6");
	}

	public Element getMappingDESTINATIONFIELD7() {
		return driver.FindElementById("DF_7");
	}

	public Element getMappingDESTINATIONFIELD8() {
		return driver.FindElementById("DF_8");
	}

	public Element getMappingDESTINATIONFIELD9() {
		return driver.FindElementById("DF_9");
	}

	public Element getMappingDESTINATIONFIELD10() {
		return driver.FindElementById("DF_10");
	}

	public Element getMappingDESTINATIONFIELD11() {
		return driver.FindElementById("DF_11");
	}

	public Element getMappingDESTINATIONFIELD13() {
		return driver.FindElementById("DF_13");
	}

	public Element getMappingDESTINATIONFIELD14() {
		return driver.FindElementById("DF_14");
	}

	public Element getMappingDESTINATIONFIELD25() {
		return driver.FindElementById("DF_25");
	}

	public Element getMappingDESTINATIONFIELD26() {
		return driver.FindElementById("DF_26");
	}

	public Element getMappingDESTINATIONFIELD27() {
		return driver.FindElementById("DF_27");
	}

	public Element getMappingDESTINATIONFIELD28() {
		return driver.FindElementById("DF_28");
	}

	public Element getMappingDESTINATIONFIELD29() {
		return driver.FindElementById("DF_29");
	}

	public Element getMappingDESTINATIONFIELD31() {
		return driver.FindElementById("DF_31");
	}

	public Element getMappingDESTINATIONFIELD46() {
		return driver.FindElementById("DF_46");
	}

	public Element getMappingDESTINATIONFIELD49() {
		return driver.FindElementById("DF_49");
	}

	public Element getMappingDESTINATIONFIELD50() {
		return driver.FindElementById("DF_50");
	}

	public Element getMappingDESTINATIONFIELD51() {
		return driver.FindElementById("DF_51");
	}

	public Element getMappingDESTINATIONFIELD73() {
		return driver.FindElementById("DF_73");
	}

	public Element getMappingDESTINATIONFIELD75() {
		return driver.FindElementById("DF_75");
	}

	public Element getMappingDESTINATIONFIELD76() {
		return driver.FindElementById("DF_76");
	}

	public Element getPreviewRun() {
		return driver.FindElementById("PreviewRun");
	}

	public Element getbtnRunIngestion() {
		return driver.FindElementById("btnRunIngestion");
	}

	public Element getRefreshButton() {
		return driver.FindElementById("refresh");
	}

	public Element getApproveMessageOKButton() {
		return driver.FindElementById("bot1-Msg1");
	}

	public Element getRunAnalyticsRunButton() {
		return driver.FindElementById("run");
	}

	public Element getRunAnalyticsPublishButton() {
		return driver.FindElementById("publish");
	}

	public Element getLanguage() {
		return driver.FindElementById("worldSelect");
	}

	// Xpaths
	public Element getAddanewIngestionButton() {
		return driver.FindElementByXPath("//a[text()='Add a new Ingestion']");
	}

	public Element getSourceSelectionText() {
		return driver.FindElementByXPath("//strong[contains(.,'Text')]/../i");
	}

	public Element getNextButton() {
		return driver.FindElementByXPath(".//*[@class='btn btn-primary btn-next']");
	}

	public Element getNativeCheckBox() {
		return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsNativeFolder']/following-sibling::i");
	}

	public Element getIsNativeInPathInDAT() {
		return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsDATNative']/following-sibling::i");
	}

	public Element getIsTextInPathInDAT() {
		return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsDATText']/following-sibling::i");
	}

	public Element getTextCheckBox() {
		return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsTextFolder']/following-sibling::i");
	}

	public Element getPDFCheckBoxstionButton() {
		return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsPDFFolder']/following-sibling::i");
	}

	public Element getMP3CheckBoxstionButton() {
		return driver
				.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsMP3VariantFolder']/following-sibling::i");
	}

	public Element getAudioTranscriptCheckBoxstionButton() {
		return driver
				.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsTranscriptFolder']/following-sibling::i");
	}

	public Element getIsPDFInPathInDAT() {
		return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsDATPDF']/following-sibling::i");
	}

	public Element getTIFFCheckBox() {
		return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsTIFFFolder']/following-sibling::i");
	}

	public Element getIsTIFFInPathInDAT() {
		return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsDATTIFF']/following-sibling::i");
	}

	public Element getOtherCheckBox() {
		return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsOtherFolder']/following-sibling::i");
	}

	public Element getIsOtherInPathInDAT() {
		return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsDATOther']/following-sibling::i");
	}

	public Element getCatalogedIngestionStatus() {
		return driver.FindElementByXPath("//strong[contains(.,'Cataloged')]");
	}

	public Element getCopiedIngestionStatus() {
		return driver.FindElementByXPath("//strong[contains(.,'Copied')]");
	}

	public Element getFailedIngestionStatus() {
		return driver.FindElementByXPath("//strong[contains(.,'Failed')]");
	}

	public Element getIndexedIngestionStatus() {
		return driver.FindElementByXPath("//strong[contains(.,'Indexed')]");
	}

	public Element getApproveIngestionStatus() {
		return driver.FindElementByXPath("//strong[contains(.,'Approved')]");
	}

	public Element getPublishIngestionStatus() {
		return driver.FindElementByXPath("//strong[contains(.,'Published')]");
	}

	public Element getcurrentPublishIngestionStatus() {
		return driver.FindElementByXPath("//ul[@class='cards']/li[1]//strong[contains(.,'Published')]");
	}

	public Element getLoadMoreButton() {
		return driver.FindElementByXPath("//*[@id='btnLoadTile']");
	}

	public Element getFilterByButton() {
		return driver
				.FindElementByXPath(".//*[@id='cardGrid']/div[1]//button[@class='multiselect dropdown-toggle btn']");
	}

	public Element getFilterByDRAFT() {
		return driver
				.FindElementByXPath(".//*[@class='multiselect-container dropdown-menu']//label/input[@value='DRAFT']");
	}

	public Element getFilterByFAILED() {
		return driver
				.FindElementByXPath(".//*[@class='multiselect-container dropdown-menu']//label/input[@value='FAILED']");
	}

	public Element getFilterByINPROGRESS() {
		return driver.FindElementByXPath(
				".//*[@class='multiselect-container dropdown-menu']//label/input[@value='INPROGRESS']");
	}

	public Element getFilterByCATALOGED() {
		return driver.FindElementByXPath(
				".//*[@class='multiselect-container dropdown-menu']//label/input[@value='CATALOGED']");
	}

	public Element getFilterByCOPIED() {
		return driver
				.FindElementByXPath(".//*[@class='multiselect-container dropdown-menu']//label/input[@value='COPIED']");
	}

	public Element getFilterByINDEXED() {
		return driver.FindElementByXPath(
				".//*[@class='multiselect-container dropdown-menu']//label/input[@value='INDEXED']");
	}

	public Element getFilterByAPPROVED() {
		return driver.FindElementByXPath(
				".//*[@class='multiselect-container dropdown-menu']//label/input[@value='APPROVED']");
	}

	public Element getFilterByPUBLISHED() {
		return driver.FindElementByXPath(
				".//*[@class='multiselect-container dropdown-menu']//label/input[@value='PUBLISHED']");
	}

	public Element getRunCopying() {
		return driver.FindElementByXPath(".//*[@id='RunCopying']/i");
	}

	public Element getCloseButton() {
		return driver.FindElementByXPath("//*[@class='ui-dialog-titlebar-close']");
	}

	public Element getRunIndexing() {
		return driver.FindElementByXPath(".//*[@id='RunIndexing']/i");
	}

	public Element getIsAudioCheckbox() {
		return driver.FindElementByXPath("//input[@id='IsAudio']/following-sibling::i");
	}

	public Element getActionDropdownArrow() {
		return driver.FindElementByXPath(".//*[@id='IngestionDetailsPopUp1']//button[2]");
	}

	public Element getActionApprove() {
		return driver.FindElementByXPath(".//*[@id='IngestionDetailsPopUp1']//a[text()='Approve']");
	}

	public Element getApproveMessageHeader() {
		return driver.FindElementByXPath(".//*[@id='Msg1']/div/span");
	}

	public Element getApproveMessageContent() {
		return driver.FindElementByXPath(".//*[@id='Msg1']/div/p");
	}

	public Element getAnalyticsCAATINGESTIONCOMPLETED() {
		return driver.FindElementByXPath(
				".//*[@id='ProjectFieldsDataTable']//td[contains(.,'ANALYTICS_INGESTION_COMPLETED')]");
	}

	public Element getAnalyticsCAATSTAGINGCOMPLETED() {
		return driver.FindElementByXPath(
				".//*[@id='ProjectFieldsDataTable']//td[contains(.,'ANALYTICS_STAGING_COMPLETED')]");
	}

	public Element getAnalyticsCAATINDEXINGCOMPLETED() {
		return driver.FindElementByXPath(
				".//*[@id='ProjectFieldsDataTable']//td[contains(.,'ANALYTICS_INDEXING_COMPLETED')]");
	}

	public Element Analytics_CAATESINDEXINGCOMPLETED() {
		return driver.FindElementByXPath(".//*[@id='ProjectFieldsDataTable']//td[contains(.,'ES_INDEXING_COMPLETED')]");
	}

	public Element getIngestionName() {
		return driver.FindElementByXPath("//*[@id='cardCanvas']/ul/li/a/span");
	}

	public Element getIngestionName1() {
		return driver.FindElementByXPath("(//*[@id='cardCanvas']//span[contains(.,'Automation')])[2]");
	}

	public Element getIngestionName2() {
		return driver.FindElementByXPath("//*[@id='cardCanvas']//span[contains(.,'Automation_All')]");
	}

	public Element getMP3Count() {
		return driver.FindElementByXPath(".//*[@id='dt_basic']/tbody/tr[6]/td[2]");
	}

	public Element getIngestionNameText() {
		return driver
				.FindElementByXPath(".//*[@id='IngestionDetailsPopUp1']/section/div/div/div[3]/fieldset/div[2]/div");
	}

	public Element getIncrementalAnalyticsbutton() {
		return driver.FindElementByXPath(".//*[@id='IncrementalAnalytics']/following-sibling::i");
	}

	// added on 04-04
	public Element getIngestionName_CloseButton() {
		return driver
				.FindElementByXPath(".//*[@id='IngestionDetailsPopUp1']/se//button[@class='ui-dialog-titlebar-close']");
	}

	public Element getIngestionActionButton() {
		return driver.FindElementByXPath(".//*[@id='cardCanvas']//div[1]/a");
	}

	public Element getIngestionAction_Copy() {
		return driver.FindElementByXPath("//dl[@class='dropdown-menu']//a[contains(text(),'Copy')]");
	}

	public Element getIngestion_IngestionType() {
		return driver.FindElementById("ddlOverwrite");
	}

	public Element getDateFormat() {
		return driver.FindElementById("ddlDateFormat");
	}

	public Element getIngestionStatus() {
		return driver.FindElementByXPath("//*[@id='cardCanvas']/ul/li[1]//strong/text()[2]");
	}

	// Added by Mohan

	public Element getAllIngestionName(String dataSets) {
		return driver.FindElementByXPath("//*[@id='IngestionGridViewtable']//td[contains(text(),'" + dataSets + "')]");
	}

	public ElementCollection getIngestionPaginationCount() {
		return driver.FindElementsByCssSelector("li[class*='paginate_button '] a");
	}

	public Element getIngestionPaginationNextButton() {
		return driver.FindElementByCssSelector("li[class='paginate_button next'] a");
	}

	public Element getIngestion_GridView() {
		return driver.FindElementById("GridView");
	}

	public Element getIngestion_PublishEndTime() {
		return driver.FindElementByXPath("//*[@id='ProjectFieldsDataTable']//tr[1]//td[4]");
	}

	public Element getIngestion_PublishStartTime() {
		return driver.FindElementByXPath("//*[@id='ProjectFieldsDataTable']//tr[1]//td[3]");
	}

	public Element getInprogressIngestionStatus() {
		return driver.FindElementByXPath("//strong[contains(.,'In Progress')]");
	}
	
	public Element getIngestionErrorNumber() {
		return driver.FindElementByXPath("//div[@class='col-md-7 form-group']//a[@data-toggle='tab']");
	}

	public Element getIngestionErrorMessage() {
		return driver.FindElementByXPath(
				"//*[@id='myDataTable']//td[contains(text(),'Date format selected in the ingestion is not matching')]");
	}


	//Added by Gopinath - 23/02/2022
	public Element getNativeLoadFileCheckBox() {
		return driver.FindElementByXPath("//input[@id='chkNative']//..//i");
	}
	public Element getNativePathInDATFileCheckBox() {
		return driver.FindElementByXPath("//input[@id='chkLoadNativeFileDAT']//..//i");
	}
	public Element getTextPathInDATFileCheckBox() {
		return driver.FindElementByXPath("//input[@id='chkLoadTextFileDAT']//..//i");
	}
	public Element getPDFPathInDATFileCheckBox() {
		return driver.FindElementByXPath("//input[@id='chkLoadPDFFileDAT']//..//i");
	}
	public Element getTIFFPathInDATFileCheckBox() {
		return driver.FindElementByXPath("//input[@id='chkLoadTIFFfileDAT']//..//i");
	}
	public Element getTIFFSearchablePDFCheckBox() {
		return driver.FindElementByXPath("//input[@id='chkGenerateSearchablePDFforTIFFs']//..//i");
	}
	public Element getMP3PathInDATFileCheckBox() {
		return driver.FindElementByXPath("//input[@id='chkLoadMP3VariantfileDAT']//..//i");
	}
	public Element getAudioTransistPathInDATFileCheckBox() {
		return driver.FindElementByXPath("//input[@id='chkLoadTranscriptfileDAT']/following-sibling::i");
	}
	public Element getOtherPathInDATFileCheckBox() {
		return driver.FindElementByXPath("//input[@id='chkLoadOtherfileDAT']/following-sibling::i");
	}
	public Element getEnabledFirstDropDown() {
		return driver.FindElementByXPath("//table[@id='dt_basic']//tbody//tr[2]//td[1]//select");
	}
	public Element getEnabledSecondDropDown() {
		return driver.FindElementByXPath("//table[@id='dt_basic']//tbody//tr[3]//td[1]//select");
	}
	public Element getEnabledThirdDropDown() {
		return driver.FindElementByXPath("//table[@id='dt_basic']//tbody//tr[4]//td[1]//select");
	}
	public Element getFilterINPROGRESS() {
		return driver.FindElementByXPath(
				".//*[@class='multiselect-container dropdown-menu']//label/input[@value='INPROGRESS']//..//..//..//..//li[2]");
	}
	public Element getStatus() {
		return driver.FindElementByXPath("//div[@id='cardCanvas']//li[1]//strong[contains(text(),'Status')]");
	}
	public Element getIngestionTitle() {
		return driver.FindElementByXPath("//div[@id='cardCanvas']//li[1]//a//span");
	}
	public Element getFieldCatagoryBySourceDat(String sourceDATField) {
		return driver.FindElementByXPath("//tbody[@id='tblBody']//option[text()='"+sourceDATField+"' and @selected='selected']//../self::select//..//following-sibling::td[1]//select[not(contains(@disabled,'disabled'))]");
	}
	public Element getDestinationFieldBySourceDat(String sourceDATField) {
		return driver.FindElementByXPath("//tbody[@id='tblBody']//option[text()='"+sourceDATField+"' and @selected='selected']//../self::select//..//following-sibling::td[2]//select[not(contains(@disabled,'disabled'))]");
	}
	public Element getIngestionTitle(int row) {
		return driver.FindElementByXPath("//div[@id='cardCanvas']//li["+row+"]//a//span");
	}
	public Element getStatus(int row) {
		return driver.FindElementByXPath("//div[@id='cardCanvas']//li["+row+"]//strong[contains(text(),'Status')]");
	}
	public Element getStatusByingestionName(String ingestionName) {
		return driver.FindElementByXPath("//span[@title='"+ingestionName+"']//..//..//div[2]//strong[1]");
	}
	public Element getIngestionLinkByName(String ingestionName) {
		return driver.FindElementByXPath("//a//span[@title='"+ingestionName+"']");
	}
	public Element getStartCopy() {
		return driver.FindElementByXPath("//strong[text()='Copying']//..//..//..//i[@class='fa fa-play-circle-o']");
	}

	public Element getIngestionWizardDateFormate() {
		return driver.FindElementByXPath("//div[@style='padding-right:0px;']//div[@class='formatDate']");
	}

	public Element getIngestion_SaveAsDraft() {
		return driver.FindElementById("SaveIngestion");
	}

	public Element getIngestionSettingGearIcon() {
		return driver.FindElementByXPath("//div[@class='dropdown pull-right actionBtn']");
	}

	public Element getDraftIngestionStatus() {
		return driver.FindElementByXPath("//strong[contains(.,'Draft')]");
	}

	public Element getIngestionRollbackbutton() {
		return driver.FindElementByXPath("//dt[contains(.,'Rollback')]");
	}
	
	public Element copyTableDataName(String term) {
		return driver.FindElementByXPath("//*[@id='Copyingblock']//td[contains(text(),'" + term + "')]");

	}
	
	public Element errorCountCatalogingStage() {
		return driver.FindElementByXPath("//*[@id='Catalogingblock']//tbody//tr//td//a");
	}
	
	public Element ignoreAllButton() {
		return driver.FindElementById("btnignoreall");
	}
	
	public Element copyTableDataValue(String term,int row) {
		return driver.FindElementByXPath("//*[@id='Copyingblock']//table//td[contains(text(),'" + term + "')]/following-sibling::td["+row+"]");
	}
	
	public Element doneButton() {
		return driver.FindElementById("Catalogdone");
	}
	
	public Element errorMessageMissingDate() {
		return driver.FindElementById("errorMsgDateFormat");
	}
	
	public Element ingestionErrorNote(int row) {
		return driver.FindElementByXPath("//*[@id='myDataTable']//tbody//tr["+row+"]//td[3]");
	}
	
	public Element errorCountStatus() {
		return driver.FindElementById("errcount");
	}

  	//Added by Gopinath - 28/02/2022
	public Element getRollBack(String ingestionName) {
		return driver.FindElementByXPath("//a//span[@title='"+ingestionName+"']//..//..//a[text()='Rollback']");
	}
	public Element getIngestionGearIcon(String ingestionName) {
		return driver.FindElementByXPath("//a//span[@title='"+ingestionName+"']//..//..//a[@class='dropdown-toggle']//i");
	}
	public IngestionPage_Indium(Driver driver) {

		this.driver = driver;
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		base = new BaseClass(driver);
		driver.waitForPageToBeReady();

	}

	public boolean AddOnlyNewIngestion(String dataset) throws InterruptedException {

		boolean ingestionStatus = false;
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAddanewIngestionButton().Enabled();
				}
			}), Input.wait30);
			getAddanewIngestionButton().waitAndClick(10);

			// Select Source System
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSpecifySourceSystem().Visible();
				}
			}), Input.wait30);
			getSpecifySourceSystem().selectFromDropdown().selectByVisibleText("TRUE");

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSpecifyLocation().Visible();
				}
			}), Input.wait30);
			for (int i = 0; i < 30; i++) {
				try {
					getSpecifyLocation().selectFromDropdown().selectByVisibleText(Input.SourceLocation);
					break;
				} catch (Exception e) {
					Thread.sleep(1000);
				}
			}

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSpecifySourceFolder().Visible();
				}
			}), Input.wait30);

			for (int i = 0; i < 30; i++) {
				try {
					if (dataset.contains("Automation_Collection1K_Tally")) {
						getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.Collection1KFolder);
					} else if (dataset.contains("Automation_20Family_20Threaded")) {
						getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.FamilyFolder);
					} else if (dataset.contains("Automation_AllSources")) {
						getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.AllSourcesFolder);
					}
				} catch (Exception e) {
					Thread.sleep(1000);
				}
			}

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDATDelimitersFieldSeparator().Visible();
				}
			}), Input.wait30);

			for (int i = 0; i < 30; i++) {
				try {
					getDATDelimitersFieldSeparator().selectFromDropdown().selectByVisibleText("ASCII(20)");
				} catch (Exception e) {
					Thread.sleep(1000);
				}
			}

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDATDelimitersTextQualifier().Visible();
				}
			}), Input.wait30);
			getDATDelimitersTextQualifier().selectFromDropdown().selectByVisibleText("ASCII(254)");

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDATDelimitersNewLine().Visible();
				}
			}), Input.wait30);
			getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText("ASCII(174)");

			driver.scrollingToBottomofAPage();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSourceSelectionDATLoadFile().Visible();
				}
			}), Input.wait30);

			// getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(Input.AllSourcesDATFile);

			if (dataset.contains("Automation_Collection1K_Tally")) {
				getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(Input.Collection1KDATFile);
			} else if (dataset.contains("Automation_20Family_20Threaded")) {
				getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(Input.FamilyDATFile);
			} else if (dataset.contains("Automation_AllSources")) {
				getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(Input.AllSourcesDATFile);
			}

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSourceSelectionDATKey().Visible();
				}
			}), Input.wait30);
			// getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText(Input.AllSourcesDockey);
			Thread.sleep(3000);
			if (dataset.contains("Automation_Collection1K_Tally")) {
				getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText(Input.Collection1KDockey);
			} else if (dataset.contains("Automation_20Family_20Threaded")) {
				getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText(Input.FamilyDockey);
			} else if (dataset.contains("Automation_AllSources")) {
				getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText(Input.AllSourcesDockey);
			}

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSourceSelectionText().Enabled();
				}
			}), Input.wait30);
			getSourceSelectionText().waitAndClick(10);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSourceSelectionTextLoadFile().Visible();
				}
			}), Input.wait30);

			if (dataset.contains("Automation_Collection1K_Tally")) {
				getSourceSelectionTextLoadFile().selectFromDropdown().selectByVisibleText(Input.Collection1KTextFile);
			} else if (dataset.contains("Automation_20Family_20Threaded")) {
				getSourceSelectionTextLoadFile().selectFromDropdown().selectByVisibleText(Input.FamilyTextFile);
			} else if (dataset.contains("Automation_AllSources")) {
				getSourceSelectionTextLoadFile().selectFromDropdown().selectByVisibleText(Input.AllSourcesTextFile);
			}
			// getSourceSelectionTextLoadFile().selectFromDropdown().selectByVisibleText("DAT4_STC_Text.lst");

			if (dataset.contains("Automation_AllSources") || dataset.contains("Automation_20Family_20Threaded")) {
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getNativeCheckBox().Visible();
					}
				}), Input.wait30);
				getNativeCheckBox().waitAndClick(10);

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getNativeLST().Visible();
					}
				}), Input.wait30);

				if (dataset.contains("Automation_20Family_20Threaded")) {
					getNativeLST().selectFromDropdown().selectByVisibleText(Input.FamilyNativeFile);
				} else if (dataset.contains("Automation_AllSources")) {
					getNativeLST().selectFromDropdown().selectByVisibleText(Input.AllSourcesNativeFile);
				}

			} else {
				System.out.println("No need to select Native");
			}
			if (dataset.contains("Automation_AllSources")) {

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getPDFCheckBoxstionButton().Enabled();
					}
				}), Input.wait30);
				getPDFCheckBoxstionButton().waitAndClick(10);

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getPDFLST().Visible();
					}
				}), Input.wait30);
				getPDFLST().selectFromDropdown().selectByVisibleText("PDFs.lst");

				driver.scrollingToBottomofAPage();

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getTIFFCheckBox().Enabled();
					}
				}), Input.wait30);
				getTIFFCheckBox().waitAndClick(10);

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getSourceSelectionTextLoadFile().Visible();
					}
				}), Input.wait30);
				getTIFFLST().selectFromDropdown().selectByVisibleText("Images.lst");

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getMP3CheckBoxstionButton().Enabled();
					}
				}), Input.wait30);
				getMP3CheckBoxstionButton().waitAndClick(10);

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getSourceSelectionTextLoadFile().Visible();
					}
				}), Input.wait30);
				getMP3LST().selectFromDropdown().selectByVisibleText("MP3.lst");

				driver.scrollingToBottomofAPage();

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getAudioTranscriptCheckBoxstionButton().Enabled();
					}
				}), Input.wait30);
				getAudioTranscriptCheckBoxstionButton().waitAndClick(10);

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getSourceSelectionTextLoadFile().Visible();
					}
				}), Input.wait30);
				getAudioTranscriptLST().selectFromDropdown().selectByVisibleText("Transcript.lst");

				driver.scrollingToBottomofAPage();

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getOtherCheckBox().Enabled();
					}
				}), Input.wait30);
				getOtherCheckBox().waitAndClick(10);

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getOtherLoadFile().Visible();
					}
				}), Input.wait30);
				getOtherLoadFile().selectFromDropdown().selectByVisibleText("Translation.lst");
			} else {
				System.out.println("No need to select PDF and MP3");
			}

			// select date format
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDateFormat().Visible();
				}
			}), Input.wait30);
			getDateFormat().selectFromDropdown().selectByVisibleText("YYYY/MM/DD HH:MM:SS");

			driver.scrollPageToTop();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getNextButton().Visible();
				}
			}), Input.wait30);
			getNextButton().waitAndClick(10);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getMappingSOURCEFIELD2().Visible();
				}
			}), Input.wait30);
			if (dataset.contains("Automation_Collection1K_Tally")) {
				getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText(Input.Collection1KDockey);
			} else if (dataset.contains("Automation_20Family_20Threaded")) {
				getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("ProdEnd");
			} else if (dataset.contains("Automation_AllSources")) {
				getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText(Input.AllSourcesDockey);
			}
			// getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("ProdBeg");

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getMappingSOURCEFIELD3().Visible();
				}
			}), Input.wait30);
			if (dataset.contains("Automation_Collection1K_Tally")) {
				getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("Datasource");
			} else if (dataset.contains("Automation_20Family_20Threaded")) {
				getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("Datasource");
			} else if (dataset.contains("Automation_AllSources")) {
				getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText(Input.AllSourcesDockey);
			}

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getMappingSOURCEFIELD4().Visible();
				}
			}), Input.wait30);
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");

			if (dataset.contains("Automation_Collection1K_Tally") || dataset.contains("Automation_AllSources")) {

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getMappingFIELDCAT25().Visible();
					}
				}), Input.wait30);
				getMappingFIELDCAT25().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD25().selectFromDropdown().selectByVisibleText("DocFileExtension");

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getMappingFIELDCAT26().Visible();
					}
				}), Input.wait30);
				getMappingFIELDCAT26().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD26().selectFromDropdown().selectByVisibleText("DocFileName");

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getMappingFIELDCAT27().Visible();
					}
				}), Input.wait30);
				getMappingFIELDCAT27().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD27().selectFromDropdown().selectByVisibleText("DocFileSize");

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getMappingFIELDCAT28().Visible();
					}
				}), Input.wait30);
				getMappingFIELDCAT28().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD28().selectFromDropdown().selectByVisibleText("DocFileType");
			} else {
				System.out.println("No need to selected 'Field 25 to 28' from here");
			}

			if (dataset.contains("Automation_20Family_20Threaded")) {

				/*
				 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
				 * getMappingFIELDCAT8().Visible() ;}}), Input.wait30);
				 * getMappingFIELDCAT8().selectFromDropdown().selectByVisibleText("EMAIL");
				 * getMappingDESTINATIONFIELD8().selectFromDropdown().selectByVisibleText(
				 * "EmailAuthorName");
				 */
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getMappingFIELDCAT9().Visible();
					}
				}), Input.wait30);
				getMappingFIELDCAT9().selectFromDropdown().selectByVisibleText("EMAIL");
				getMappingDESTINATIONFIELD9().selectFromDropdown().selectByVisibleText("EmailAuthorNameAndAddress");

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getMappingFIELDCAT10().Visible();
					}
				}), Input.wait30);
				getMappingFIELDCAT10().selectFromDropdown().selectByVisibleText("EMAIL");
				getMappingDESTINATIONFIELD10().selectFromDropdown().selectByVisibleText("EmailBCCNamesAndAddresses");

//	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//	    			getMappingFIELDCAT11().Visible()  ;}}), Input.wait30); 
//	    	getMappingFIELDCAT11().selectFromDropdown().selectByVisibleText("EMAIL");
//	    	getMappingDESTINATIONFIELD11().selectFromDropdown().selectByVisibleText("EmailBCCNames");
//	    	
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getMappingFIELDCAT13().Visible();
					}
				}), Input.wait30);
				getMappingFIELDCAT13().selectFromDropdown().selectByVisibleText("EMAIL");
				getMappingDESTINATIONFIELD13().selectFromDropdown().selectByVisibleText("EmailCCNamesAndAddresses");

//	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//	    			getMappingFIELDCAT14().Visible()  ;}}), Input.wait30); 
//	    	getMappingFIELDCAT14().selectFromDropdown().selectByVisibleText("EMAIL");
//	    	getMappingDESTINATIONFIELD14().selectFromDropdown().selectByVisibleText("EmailCCNames");
				//
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getMappingFIELDCAT25().Visible();
					}
				}), Input.wait30);
				getMappingFIELDCAT25().selectFromDropdown().selectByVisibleText("FAMILY");
				getMappingDESTINATIONFIELD25().selectFromDropdown().selectByVisibleText("FamilyRelationship");

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getMappingFIELDCAT26().Visible();
					}
				}), Input.wait30);
				getMappingFIELDCAT26().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD26().selectFromDropdown().selectByVisibleText("DocFileExtension");

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getMappingFIELDCAT27().Visible();
					}
				}), Input.wait30);
				getMappingFIELDCAT27().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD27().selectFromDropdown().selectByVisibleText("DocFileName");

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getMappingFIELDCAT28().Visible();
					}
				}), Input.wait30);
				getMappingFIELDCAT28().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD28().selectFromDropdown().selectByVisibleText("DocFileSize");

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getMappingFIELDCAT29().Visible();
					}
				}), Input.wait30);
				getMappingFIELDCAT29().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD29().selectFromDropdown().selectByVisibleText("DocFileType");

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getMappingFIELDCAT31().Visible();
					}
				}), Input.wait30);
				getMappingFIELDCAT31().selectFromDropdown().selectByVisibleText("FAMILY");
				getMappingDESTINATIONFIELD31().selectFromDropdown().selectByVisibleText("FamilyID");

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getMappingFIELDCAT49().Visible();
					}
				}), Input.wait30);
				getMappingFIELDCAT49().selectFromDropdown().selectByVisibleText("EMAIL");
				getMappingDESTINATIONFIELD49().selectFromDropdown().selectByVisibleText("EmailReceivedDate");

//	      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//	    			getMappingFIELDCAT50().Visible()  ;}}), Input.wait30); 
//	    	getMappingFIELDCAT50().selectFromDropdown().selectByVisibleText("EMAIL");
//	    	getMappingDESTINATIONFIELD50().selectFromDropdown().selectByVisibleText("EmailToNames");
//	        	
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getMappingFIELDCAT51().Visible();
					}
				}), Input.wait30);
				getMappingFIELDCAT51().selectFromDropdown().selectByVisibleText("EMAIL");
				getMappingDESTINATIONFIELD51().selectFromDropdown().selectByVisibleText("EmailToNamesAndAddresses");
			} else {
				System.out.println("No need to select fields for this dataset'");
			}

			// Below called function handles all the stages of ingestion from catalog to
			// publish!
			IngestionCatlogtoIndexing(dataset);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getIngestionName().Visible();
				}
			}), Input.wait60);
			getIngestionName().waitAndClick(10);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getIngestionNameText().Visible();
				}
			}), Input.wait60);
			IngestionName = getIngestionNameText().getText();
			Thread.sleep(2000);
			System.out.println(IngestionName);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getCloseButton().Enabled();
				}
			}), Input.wait30);
			getCloseButton().waitAndClick(10);

			ingestionStatus = true;

		} finally {
			return ingestionStatus;
		}
	}

	public void ReIngestionofNativeWithOverlay(String dataset) throws InterruptedException {

		// Publish
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByButton().Visible();
			}
		}), Input.wait30);
		getFilterByButton().waitAndClick(Input.wait30);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByPUBLISHED().Visible();
			}
		}), Input.wait30);
		getFilterByPUBLISHED().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getIngestionActionButton().Displayed();
			}
		}), Input.wait30);
		getIngestionActionButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getIngestionAction_Copy().Displayed();
			}
		}), Input.wait30);
		getIngestionAction_Copy().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSpecifySourceFolder().Visible();
			}
		}), Input.wait30);

		getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.AllSourcesFolder);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSpecifySourceFolder().Visible();
			}
		}), Input.wait30);
		getIngestion_IngestionType().selectFromDropdown().selectByVisibleText("Overlay Only");

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSourceSelectionDATLoadFile().Visible();
			}
		}), Input.wait30);
		getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(Input.AllSourcesDATFile);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSourceSelectionDATKey().Visible();
			}
		}), Input.wait30);
		getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText(Input.AllSourcesDockey);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNativeCheckBox().Visible();
			}
		}), Input.wait30);
		getNativeCheckBox().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNativeLST().Visible();
			}
		}), Input.wait30);

		getNativeLST().selectFromDropdown().selectByVisibleText(Input.AllSourcesNativeFile);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNextButton().Visible();
			}
		}), Input.wait30);
		getNextButton().waitAndClick(10);

		IngestionCatlogtoIndexing(dataset);

		// ***************************Verify in DocView Default tab should come as
		// Native*********************

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getIngestionName().Visible();
			}
		}), Input.wait60);
		getIngestionName().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getIngestionNameText().Visible();
			}
		}), Input.wait60);
		IngestionName = getIngestionNameText().getText();
		Thread.sleep(2000);
		System.out.println(IngestionName);
	}

	public void ReIngestionofDataWithOverlay(String dataset, String fieldname) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddanewIngestionButton().Displayed();
			}
		}), Input.wait60);

		getAddanewIngestionButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSpecifySourceFolder().Visible();
			}
		}), Input.wait30);
		getIngestion_IngestionType().selectFromDropdown().selectByVisibleText("Overlay Only");

		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSpecifyLocation().Visible();
			}
		}), Input.wait60);

		getSpecifyLocation().selectFromDropdown().selectByVisibleText(Input.SourceLocation);

		// Thread.sleep(4000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSpecifySourceFolder().Visible();
			}
		}), Input.wait60);
		base.waitForElement(getSpecifySourceFolder());

		getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.Collection1KFolder);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSourceSelectionDATLoadFile().Visible();
			}
		}), Input.wait60);
		getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(Input.Collection1KDATFile);

		// Thread.sleep(2000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSourceSelectionDATKey().Visible();
			}
		}), Input.wait60);
		base.waitTillElemetToBeClickable(getSourceSelectionDATKey());
		getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText(Input.Collection1KSourceDatField2);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDateFormat().Visible();
			}
		}), Input.wait30);
		getDateFormat().selectFromDropdown().selectByVisibleText("YYYY/MM/DD HH:MM:SS");

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNextButton().Visible();
			}
		}), Input.wait30);
		getNextButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMappingSOURCEFIELD2().Visible();
			}
		}), Input.wait60);

		getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText(Input.SourceDatFieldCustom);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMappingFIELDCAT2().Visible();
			}
		}), Input.wait30);
		getMappingFIELDCAT2().selectFromDropdown().selectByVisibleText("CUSTOM");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMappingDESTINATIONFIELD2().Visible();
			}
		}), Input.wait30);
		getMappingDESTINATIONFIELD2().selectFromDropdown().selectByVisibleText(fieldname);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPreviewRun().Visible();
			}
		}), Input.wait30);
		getPreviewRun().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getApproveMessageOKButton().Visible();
			}
		}), Input.wait30);
		getApproveMessageOKButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnRunIngestion().Visible();
			}
		}), Input.wait30);
		getbtnRunIngestion().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByButton().Visible();
			}
		}), Input.wait30);
		getFilterByButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByCATALOGED().Visible();
			}
		}), Input.wait30);
		getFilterByCATALOGED().waitAndClick(10);

		// catlogging
		for (int i = 0; i < 40; i++) {
			try {
				getCatalogedIngestionStatus().Displayed();
				UtilityLog.info(dataset + " cataloged.");
				break;
			} catch (Exception e) {

				try {
					Thread.sleep(5000);
					getRefreshButton().waitAndClick(10);
					if (getFailedIngestionStatus().Displayed()) {
						System.out.println("Execution aborted!");
						UtilityLog.info("Execution aborted!");
						System.out.println(dataset + " is failed in catalog stage. Take a look and continue!");
						UtilityLog.info(dataset + " is failed in catalog stage. Take a look and continue!");
						System.exit(1);

					}
				} catch (Throwable e1) {
					System.out.println("Task in Progress : " + i);
					UtilityLog.info("Task in Progress : " + i);
				}
			}
		}

		// copy
		getRefreshButton().waitAndClick(10);

		UtilityLog.info(dataset + "'s copying is started.");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByButton().Visible();
			}
		}), Input.wait30);
		getFilterByButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByCOPIED().Visible();
			}
		}), Input.wait30);
		getFilterByCOPIED().waitAndClick(10);

		for (int i = 0; i < 120; i++) {
			try {
				getCopiedIngestionStatus().Displayed();
				UtilityLog.info(dataset + " copied.");
				break;
			} catch (Exception e) {

				try {
					Thread.sleep(5000);
					getRefreshButton().waitAndClick(10);
					if (getFailedIngestionStatus().Displayed()) {
						System.out.println("Execution aborted!");
						UtilityLog.info("Execution aborted!");
						System.out.println(dataset + " is failed in copying stage. Take a look and continue!");
						UtilityLog.info(dataset + " is failed in copying stage. Take a look and continue!");
						System.exit(1);

					}
				} catch (Throwable e1) {
					System.out.println("Task in Progress : " + i);
					UtilityLog.info("Task in Progress : " + i);
				}

			}
		}

		// Indexing
		getRefreshButton().waitAndClick(10);

		getIngestionName().waitAndClick(Input.wait30);

		if (dataset.contains("Automation_AllSources")) {

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getIsAudioCheckbox().Visible();
				}
			}), Input.wait60);
			getIsAudioCheckbox().waitAndClick(10);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getLanguage().Visible();
				}
			}), Input.wait60);
			getLanguage().selectFromDropdown().selectByVisibleText("North American English");
		} else {
			System.out.println("No need to select for other datasets");
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getRunIndexing().Visible();
			}
		}), Input.wait60);
		getRunIndexing().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCloseButton().Enabled();
			}
		}), Input.wait30);
		getCloseButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByButton().Visible();
			}
		}), Input.wait30);
		getFilterByButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByINDEXED().Visible();
			}
		}), Input.wait30);
		getFilterByINDEXED().waitAndClick(10);

		for (int i = 0; i < 120; i++) {

			try {
				getIndexedIngestionStatus().Displayed();
				UtilityLog.info(dataset + " indexed.");
				break;
			} catch (Exception e) {

				try {
					Thread.sleep(10000);
					getRefreshButton().waitAndClick(10);
					if (getFailedIngestionStatus().Displayed()) {
						System.out.println("Execution aborted!");
						UtilityLog.info("Execution aborted!");
						System.out.println(dataset + " is failed in indexing stage. Take a look and continue!");
						UtilityLog.info(dataset + " is failed in indexing stage. Take a look and continue!");
						System.exit(1);

					}
				} catch (Throwable e1) {
					System.out.println("Task in Progress : " + i);
					UtilityLog.info("Task in Progress : " + i);
				}
			}
		}

		Thread.sleep(5000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByButton().Visible();
			}
		}), Input.wait30);
		getFilterByButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByAPPROVED().Visible();
			}
		}), Input.wait30);
		getFilterByAPPROVED().waitAndClick(10);

		for (int i = 0; i < 30; i++) {
			try {
				getApproveIngestionStatus().Displayed();
				UtilityLog.info(dataset + " approved.");
				break;

			} catch (Exception e) {
				try {
					Thread.sleep(5000);
					getRefreshButton().waitAndClick(10);
					if (getFailedIngestionStatus().Displayed()) {
						System.out.println("Execution aborted!");
						UtilityLog.info("Execution aborted!");
						System.out.println(dataset + " is failed in approving stage. Take a look and continue!");
						UtilityLog.info(dataset + " is failed in approving stage. Take a look and continue!");
						System.exit(1);

					}
				} catch (Throwable e1) {
					System.out.println("Task in Progress : " + i);
					UtilityLog.info("Task in Progress : " + i);
				}

			}
		}

		Thread.sleep(5000);

		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getFilterByPUBLISHED().Visible();
				}
			}), Input.wait30);
			getFilterByPUBLISHED().waitAndClick(10);
		} catch (Exception e) {
			System.out.println("Not able to select Published. Pleasae check!!");
		}

		for (int i = 0; i < 10; i++) {
			try {

				getcurrentPublishIngestionStatus().Displayed();
				UtilityLog.info(dataset + " published.");
				break;
			} catch (Exception e) {
				try {
					Thread.sleep(5000);
					getRefreshButton().waitAndClick(10);
					if (getFailedIngestionStatus().Displayed()) {
						System.out.println("Execution aborted!");
						UtilityLog.info("Execution aborted!");
						System.out.println(dataset + " is failed in publishing stage. Take a look and continue!");
						UtilityLog.info(dataset + " is failed in publishing stage. Take a look and continue!");
						System.exit(1);

					}
				} catch (Throwable e1) {
					System.out.println("Task in Progress : " + i);
					UtilityLog.info("Task in Progress : " + i);
				}

			}

		}
	}

	public void IngestionCatlogtoIndexing(String dataset) throws InterruptedException {

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPreviewRun().Visible();
			}
		}), Input.wait30);
		getPreviewRun().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getApproveMessageOKButton().Visible();
			}
		}), Input.wait30);
		getApproveMessageOKButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnRunIngestion().Visible();
			}
		}), Input.wait30);
		getbtnRunIngestion().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByButton().Visible();
			}
		}), Input.wait30);
		getFilterByButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByFAILED().Visible();
			}
		}), Input.wait30);
		getFilterByFAILED().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByCATALOGED().Visible();
			}
		}), Input.wait30);
		getFilterByCATALOGED().waitAndClick(10);

		// catlogging
		for (int i = 0; i < 40; i++) {
			try {
				if (getInprogressIngestionStatus().isDisplayed()) {
					base.waitTime(6);
					for (int j = 0; j <= 3; j++) {
						getRefreshButton().waitAndClick(10);
					}

					getCatalogedIngestionStatus().isDisplayed();
					base.passedStep("Cataloged completed");
				} else {
					base.waitForElement(getCatalogedIngestionStatus());
					getCatalogedIngestionStatus().isDisplayed();
					base.passedStep("Cataloged completed");
				}
				break;
			} catch (Exception e) {

				try {
					Thread.sleep(5000);
					getRefreshButton().waitAndClick(10);
					if (getFailedIngestionStatus().Displayed()) {
						System.out.println("Execution aborted!");
						UtilityLog.info("Execution aborted!");
						System.out.println(dataset + " is failed in catalog stage. Take a look and continue!");
						UtilityLog.info(dataset + " is failed in catalog stage. Take a look and continue!");
						System.exit(1);

					}
				} catch (Throwable e1) {
					System.out.println("Task in Progress : " + i);
					UtilityLog.info("Task in Progress : " + i);
				}
			}
		}

		// copy
		getRefreshButton().waitAndClick(10);

		getIngestionName().waitAndClick(Input.wait30);

		driver.scrollingToElementofAPage(getRunCopying());
		base.waitForElement(getRunCopying());
		getRunCopying().waitAndClick(10);

		base.VerifySuccessMessage("Ingestion copy has Started.");
		UtilityLog.info(dataset + "'s copying is started.");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCloseButton().Enabled();
			}
		}), Input.wait30);
		getCloseButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByButton().Visible();
			}
		}), Input.wait30);
		getFilterByButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByCOPIED().Visible();
			}
		}), Input.wait30);
		getFilterByCOPIED().waitAndClick(10);

		for (int i = 0; i < 120; i++) {
			try {

				if (getInprogressIngestionStatus().isDisplayed() || getCatalogedIngestionStatus().isDisplayed()) {
					base.waitTime(6);
					getRefreshButton().waitAndClick(10);
					getCopiedIngestionStatus().isDisplayed();
					base.passedStep("Copied completed");
				} else {
					base.waitForElement(getCopiedIngestionStatus());
					getCopiedIngestionStatus().isDisplayed();
					base.passedStep("Copied completed");
				}
				break;
			} catch (Exception e) {

				try {
					Thread.sleep(5000);
					getRefreshButton().waitAndClick(10);
					if (getFailedIngestionStatus().Displayed()) {
						System.out.println("Execution aborted!");
						UtilityLog.info("Execution aborted!");
						System.out.println(dataset + " is failed in copying stage. Take a look and continue!");
						UtilityLog.info(dataset + " is failed in copying stage. Take a look and continue!");
						System.exit(1);

					}
				} catch (Throwable e1) {
					System.out.println("Task in Progress : " + i);
					UtilityLog.info("Task in Progress : " + i);
				}

			}

		}
		// Indexing
		getRefreshButton().waitAndClick(10);

		getIngestionName().waitAndClick(Input.wait30);

		driver.scrollingToElementofAPage(getMP3Count());

		if (dataset.contains("AllSources") || dataset.contains("SSAudioSpeech_Transcript")) {

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getIsAudioCheckbox().Visible();
				}
			}), Input.wait60);
			getIsAudioCheckbox().waitAndClick(10);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getLanguage().Visible();
				}
			}), Input.wait60);
			getLanguage().selectFromDropdown().selectByVisibleText("North American English");
		} else if (dataset.contains("CJK_GermanAudioTestData")) {

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getIsAudioCheckbox().Visible();
				}
			}), Input.wait60);
			getIsAudioCheckbox().waitAndClick(10);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getLanguage().Visible();
				}
			}), Input.wait60);
			getLanguage().selectFromDropdown().selectByVisibleText("German");
		} else if (dataset.contains("CJK_JapaneseAudioTestData")) {

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getIsAudioCheckbox().Visible();
				}
			}), Input.wait60);
			getIsAudioCheckbox().waitAndClick(10);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getLanguage().Visible();
				}
			}), Input.wait60);
			getLanguage().selectFromDropdown().selectByVisibleText("Japanese");
		} else if (dataset.contains("0002_H13696_1_Latest")) {

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getIsAudioCheckbox().Visible();
				}
			}), Input.wait60);
			getIsAudioCheckbox().waitAndClick(10);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getLanguage().Visible();
				}
			}), Input.wait60);
			getLanguage().selectFromDropdown().selectByVisibleText("International English");
		} else {
			System.out.println("No need to select for other datasets");
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getRunIndexing().Visible();
			}
		}), Input.wait60);
		getRunIndexing().waitAndClick(10);

		base.VerifySuccessMessage("Ingestion Indexing has Started.");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCloseButton().Enabled();
			}
		}), Input.wait30);
		getCloseButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByButton().Visible();
			}
		}), Input.wait30);
		getFilterByButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByINDEXED().Visible();
			}
		}), Input.wait30);
		getFilterByINDEXED().waitAndClick(10);

		for (int i = 0; i < 120; i++) {

			try {

				if (getInprogressIngestionStatus().isDisplayed() || getCopiedIngestionStatus().isDisplayed()) {
					base.waitTime(6);
					getRefreshButton().waitAndClick(10);
					getIndexedIngestionStatus().isDisplayed();
					base.passedStep("Indexing completed");
					UtilityLog.info(dataset + " indexed.");
				} else {
					base.waitForElement(getIndexedIngestionStatus());
					getIndexedIngestionStatus().isDisplayed();
					base.passedStep("Indexing completed");
				}

				break;
			} catch (Exception e) {

				try {
					Thread.sleep(10000);
					getRefreshButton().waitAndClick(10);
					if (getFailedIngestionStatus().Displayed()) {
						System.out.println("Execution aborted!");
						UtilityLog.info("Execution aborted!");
						System.out.println(dataset + " is failed in indexing stage. Take a look and continue!");
						UtilityLog.info(dataset + " is failed in indexing stage. Take a look and continue!");
						System.exit(1);

					}
				} catch (Throwable e1) {
					System.out.println("Task in Progress : " + i);
					UtilityLog.info("Task in Progress : " + i);
				}
			}
		}

	}

	public void MetadataOverlay(String dataset) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByButton().Visible();
			}
		}), Input.wait30);
		getFilterByButton().waitAndClick(Input.wait30);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByPUBLISHED().Visible();
			}
		}), Input.wait30);
		getFilterByPUBLISHED().waitAndClick(10);

		getRefreshButton().waitAndClick(5);
		Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getIngestionActionButton().Visible();
			}
		}), Input.wait30);
		getIngestionActionButton().waitAndClick(5);
		Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getIngestionAction_Copy().Visible();
			}
		}), Input.wait30);
		getIngestionAction_Copy().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSpecifySourceFolder().Visible();
			}
		}), Input.wait30);

		getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.AllSourcesFolder);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getIngestion_IngestionType().Visible();
			}
		}), Input.wait30);
		getIngestion_IngestionType().selectFromDropdown().selectByVisibleText("Overlay Only");

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSourceSelectionDATLoadFile().Visible();
			}
		}), Input.wait30);
		getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(Input.AllSourcesDATFile);
		Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSourceSelectionDATKey().Visible();
			}
		}), Input.wait60);
		getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText(Input.AllSourcesDockey);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNextButton().Visible();
			}
		}), Input.wait30);
		getNextButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getApproveMessageOKButton().Visible();
			}
		}), Input.wait30);
		getApproveMessageOKButton().waitAndClick(10);
		Thread.sleep(5000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMappingSOURCEFIELD6().Visible();
			}
		}), Input.wait30);
		getMappingFIELDCAT6().selectFromDropdown().selectByVisibleText("EMAIL");
		getMappingDESTINATIONFIELD6().selectFromDropdown().selectByVisibleText("EmailAuthorNameAndAddress");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMappingFIELDCAT7().Visible();
			}
		}), Input.wait30);
		getMappingFIELDCAT7().selectFromDropdown().selectByVisibleText("EMAIL");
		getMappingDESTINATIONFIELD7().selectFromDropdown().selectByVisibleText("EmailBCCNamesAndAddresses");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMappingFIELDCAT10().Visible();
			}
		}), Input.wait30);
		getMappingFIELDCAT10().selectFromDropdown().selectByVisibleText("EMAIL");
		getMappingDESTINATIONFIELD10().selectFromDropdown().selectByVisibleText("EmailCCNamesAndAddresses");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMappingFIELDCAT46().Visible();
			}
		}), Input.wait30);
		getMappingFIELDCAT46().selectFromDropdown().selectByVisibleText("EMAIL");
		getMappingDESTINATIONFIELD46().selectFromDropdown().selectByVisibleText("EmailToNamesAndAddresses");

		IngestionCatlogtoIndexing(dataset);

	}

	// Mohan

	public void IngestionRegression(String dataset) throws InterruptedException {
		base.stepInfo("Validating whether the ingestion is done for particular project");
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByButton().Visible();
			}
		}), Input.wait30);
		getFilterByButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByPUBLISHED().Visible();
			}
		}), Input.wait30);
		getFilterByPUBLISHED().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getIngestion_GridView().Visible();
			}
		}), Input.wait30);
		getIngestion_GridView().waitAndClick(10);

		driver.waitForPageToBeReady();
		base.stepInfo("Searching for Datasets");
		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getIngestionPaginationCount().Visible();
			}
		}), Input.wait30);
		int count = ((getIngestionPaginationCount().size()) - 2);
		Boolean status = false;
		for (int i = 0; i < count; i++) {
			// driver.waitForPageToBeReady();
			if (getAllIngestionName(dataset).isElementAvailable(5)) {
				String publishedDataSet = getAllIngestionName(dataset).getText();
				if (publishedDataSet.contains(dataset)) {
					status = true;
					base.passedStep("The Ingestion " + dataset + " is already done for this project");
				}
				break;
			} else {
				status = false;
				driver.scrollingToBottomofAPage();
				getIngestionPaginationNextButton().waitAndClick(3);
				base.stepInfo("Expected Ingestion not found in the page " + i);

			}

		}
		if (status == false) {
			base.stepInfo("Click on add new ingestion button");
			base.waitForElement(getAddanewIngestionButton());
			getAddanewIngestionButton().waitAndClick(10);

			base.stepInfo("Select Source system");
			base.waitForElement(getSpecifySourceSystem());
			getSpecifySourceSystem().selectFromDropdown().selectByVisibleText("TRUE");

			base.stepInfo("Select Location");
			base.waitForElement(getSpecifyLocation());
			Thread.sleep(2000);
			getSpecifyLocation().selectFromDropdown().selectByVisibleText(Input.SourceLocation);

			base.waitForElement(getSpecifySourceFolder());
			Thread.sleep(2000);
			base.stepInfo("Select Folder");
			for (int i = 0; i < 30; i++) {

				if (dataset.contains("Collection1K_Tally")) {
					getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.Collection1KFolder);
				} else if (dataset.contains("20Family_20Threaded")) {
					getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.FamilyFolder);
				} else if (dataset.contains("AllSources")) {
					getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.AllSourcesFolder);
				} else if (dataset.contains("0002_H13696_1_Latest")) {
					getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.H1369Folder);
				} else if (dataset.contains("16JanMultiPTIFF")) {
					getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.MultiPTIFFFolder);
				} else if (dataset.contains("27MarSinglePageTIFF")) {
					getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.SinglePageTIFFFolder);
				} else if (dataset.contains("CJK_FrenchAudioTestData")) {
					getSpecifySourceFolder().selectFromDropdown()
							.selectByVisibleText(Input.CJK_FrenchAudioTestDataFolder);
				} else if (dataset.contains("QA_EmailConcatenatedData_SS")) {
					getSpecifySourceFolder().selectFromDropdown()
							.selectByVisibleText(Input.EmailConcatenatedDataFolder);
				} else if (dataset.contains("SSAudioSpeech_Transcript")) {
					getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.SSAudioSpeechFolder);
				} else if (dataset.contains("GD_994_Native_Text_ForProduction")) {
					getSpecifySourceFolder().selectFromDropdown()
							.selectByVisibleText(Input.GD994NativeTextForProductionFolder);
				} else if (dataset.contains("GNon_searchable_PDF_Load_file")) {
					getSpecifySourceFolder().selectFromDropdown()
							.selectByVisibleText(Input.GNonsearchablePDFLoadfileFolder);
				} else if (dataset.contains("HiddenProperties_IngestionData")) {
					getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.HiddenPropertiesFolder);
				} else if (dataset.contains("UniCodeFiles")) {
					getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.UniCodeFilesFolder);
				} else if (dataset.contains("IngestionEmailData")) {
					getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.IngestionEmailDataFolder);
				} else if (dataset.contains("CJK_GermanAudioTestData")
						|| dataset.contains("CJK_JapaneseAudioTestData")) {
					getSpecifySourceFolder().selectFromDropdown()
							.selectByVisibleText(Input.CJK_FrenchAudioTestDataFolder);
				}
			}
			Thread.sleep(2000);
			base.waitForElement(getDATDelimitersFieldSeparator());
			getDATDelimitersFieldSeparator().selectFromDropdown().selectByVisibleText("ASCII(20)");

			base.waitForElement(getDATDelimitersTextQualifier());
			getDATDelimitersTextQualifier().selectFromDropdown().selectByVisibleText("ASCII(254)");

			base.waitForElement(getDATDelimitersNewLine());
			getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText("ASCII(174)");

			driver.scrollingToBottomofAPage();

			base.waitForElement(getSourceSelectionDATLoadFile());
			if (dataset.contains("CJK_GermanAudioTestData")) {
				getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(Input.DATGermanFile);
			} else if (dataset.contains("CJK_JapaneseAudioTestData")) {
				getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(Input.DATJapneseFile);
			} else {
				getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(Input.DATFile);
			}
			base.waitForElement(getSourceSelectionDATKey());
			Thread.sleep(2000);

			if (dataset.contains("Collection1K_Tally")) {
				getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
			} else if (dataset.contains("20Family_20Threaded")) {
				getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
			} else if (dataset.contains("AllSources")) {
				getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("ProdBeg");
			} else if (dataset.contains("0002_H13696_1_Latest")) {
				getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DOCID");
			} else if (dataset.contains("16JanMultiPTIFF")) {
				getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("BNum");
			} else if (dataset.contains("27MarSinglePageTIFF")) {
				getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("BNum");
			} else if (dataset.contains("QA_EmailConcatenatedData_SS")) {
				getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("BatesNumber");
			} else if (dataset.contains("SSAudioSpeech_Transcript")) {
				getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
			} else if (dataset.contains("GD_994_Native_Text_ForProduction")) {
				getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
			} else if (dataset.contains("GNon_searchable_PDF_Load_file")) {
				getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("SourceDocID");
			} else if (dataset.contains("HiddenProperties_IngestionData")) {
				getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("SourceDocID");
			} else if (dataset.contains("UniCodeFiles")) {
				getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
			} else if (dataset.contains("IngestionEmailData")) {
				getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocumentID");
			} else if (dataset.contains("CJK_GermanAudioTestData") || dataset.contains("CJK_JapaneseAudioTestData")) {
				getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
			}

			if (dataset.contains("20Family_20Threaded") || dataset.contains("AllSources")
					|| dataset.contains("16JanMultiPTIFF") || dataset.contains("QA_EmailConcatenatedData_SS")
					|| dataset.contains("GD_994_Native_Text_ForProduction")
					|| dataset.contains("GNon_searchable_PDF_Load_file") || dataset.contains("IngestionEmailData")) {
				base.stepInfo("*******Selecing text files***************");
				base.waitForElement(getSourceSelectionText());
				getSourceSelectionText().waitAndClick(20);
				base.waitForElement(getSourceSelectionTextLoadFile());
				getSourceSelectionTextLoadFile().selectFromDropdown().selectByVisibleText(Input.TextFile);

				base.stepInfo("*******Selecing Native files***************");
				base.waitForElement(getNativeCheckBox());
				getNativeCheckBox().waitAndClick(10);
				base.waitForElement(getNativeLST());
				getNativeLST().selectFromDropdown().selectByVisibleText(Input.NativeFile);
			}

			if (dataset.contains("Collection1K_Tally") || dataset.contains("UniCodeFiles")) {
				base.stepInfo("*******Selecing text files***************");
				base.waitForElement(getSourceSelectionText());
				getSourceSelectionText().waitAndClick(20);
				base.waitForElement(getSourceSelectionTextLoadFile());
				getSourceSelectionTextLoadFile().selectFromDropdown().selectByVisibleText(Input.TextFile);
			}

			if (dataset.contains("0002_H13696_1_Latest") || dataset.contains("HiddenProperties_IngestionData")) {
				base.stepInfo("*******Selecing Native files***************");
				base.waitForElement(getNativeCheckBox());
				getNativeCheckBox().waitAndClick(10);
				base.waitForElement(getNativeLST());
				getNativeLST().selectFromDropdown().selectByVisibleText(Input.NativeFile);
			}

			if (dataset.contains("AllSources")) {
				base.stepInfo("*******Selecing PDF files***************");
				base.waitForElement(getPDFCheckBoxstionButton());
				getPDFCheckBoxstionButton().waitAndClick(20);
				base.waitForElement(getPDFLST());
				getPDFLST().selectFromDropdown().selectByVisibleText(Input.PDFFile);
			}

			driver.scrollingToBottomofAPage();

			if (dataset.contains("AllSources") || dataset.contains("16JanMultiPTIFF")) {
				base.stepInfo("*******Selecing TIFF files***************");
				base.waitForElement(getTIFFCheckBox());
				getTIFFCheckBox().waitAndClick(20);
				base.waitForElement(getSourceSelectionTextLoadFile());
				getTIFFLST().selectFromDropdown().selectByVisibleText(Input.TIFFFile);
			}

			/*
			 * if( dataset.contains("27MarSinglePageTIFF")) {
			 * base.stepInfo("*******Selecing TIFF files***************");
			 * base.waitForElement(getTIFFCheckBox()); getTIFFCheckBox().waitAndClick(20);
			 * base.waitForElement(getSourceSelectionTextLoadFile());
			 * getTIFFLST().selectFromDropdown().selectByVisibleText(Input.TIFFFile1); }
			 */
			driver.scrollingToBottomofAPage();

			if (dataset.contains("0002_H13696_1_Latest") || dataset.contains("SSAudioSpeech_Transcript")
					|| dataset.contains("AllSources")) {
				base.stepInfo("*******Selecing MP3 files***************");
				base.waitForElement(getMP3CheckBoxstionButton());
				getMP3CheckBoxstionButton().waitAndClick(15);
				base.waitForElement(getMP3LST());
				getMP3LST().selectFromDropdown().selectByVisibleText(Input.MP3File);
			}

			if (dataset.contains("CJK_GermanAudioTestData")) {
				base.stepInfo("*******Selecing MP3 files***************");
				base.waitForElement(getMP3CheckBoxstionButton());
				getMP3CheckBoxstionButton().waitAndClick(15);
				base.waitForElement(getMP3LST());
				getMP3LST().selectFromDropdown().selectByVisibleText(Input.MP3GermanFile);
			}

			if (dataset.contains("CJK_JapaneseAudioTestData")) {
				base.stepInfo("*******Selecing MP3 files***************");
				base.waitForElement(getMP3CheckBoxstionButton());
				getMP3CheckBoxstionButton().waitAndClick(15);
				base.waitForElement(getMP3LST());
				getMP3LST().selectFromDropdown().selectByVisibleText(Input.MP3JapneseFile);
			}

			if (dataset.contains("AllSources")) {
				base.stepInfo("*******Selecing Audio Transcript files***************");
				base.waitForElement(getAudioTranscriptCheckBoxstionButton());
				getAudioTranscriptCheckBoxstionButton().waitAndClick(15);
				base.waitForElement(getAudioTranscriptLST());
				getAudioTranscriptLST().selectFromDropdown().selectByVisibleText(Input.TranscriptFile);
			}

			if (dataset.contains("CJK_GermanAudioTestData")) {
				base.stepInfo("*******Selecing Audio Transcript files***************");
				base.waitForElement(getAudioTranscriptCheckBoxstionButton());
				getAudioTranscriptCheckBoxstionButton().waitAndClick(15);
				base.waitForElement(getAudioTranscriptLST());
				getAudioTranscriptLST().selectFromDropdown().selectByVisibleText(Input.TranscriptGermanFile);
			}

			if (dataset.contains("CJK_JapaneseAudioTestData")) {
				base.stepInfo("*******Selecing Audio Transcript files***************");
				base.waitForElement(getAudioTranscriptCheckBoxstionButton());
				getAudioTranscriptCheckBoxstionButton().waitAndClick(15);
				base.waitForElement(getAudioTranscriptLST());
				getAudioTranscriptLST().selectFromDropdown().selectByVisibleText(Input.TranscriptJapneseFile);
			}
			driver.scrollingToBottomofAPage();

			if (dataset.contains("AllSources")) {
				base.stepInfo("*******Selecing Other files***************");
				base.waitForElement(getOtherCheckBox());
				getOtherCheckBox().waitAndClick(15);
				base.waitForElement(getOtherLoadFile());
				getOtherLoadFile().selectFromDropdown().selectByVisibleText(Input.TranslationFile);
			}

			base.stepInfo("Select Date Format");
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDateFormat().Visible();
				}
			}), Input.wait30);
			getDateFormat().selectFromDropdown().selectByVisibleText("YYYY/MM/DD HH:MM:SS");

			driver.scrollPageToTop();

			base.waitForElement(getNextButton());
			getNextButton().waitAndClick(20);
			base.passedStep("Clicked on Next button");

			base.stepInfo("Pop up messgae for Ingestion without text file");
			if (getApproveMessageOKButton().isElementAvailable(5)) {
				getApproveMessageOKButton().waitAndClick(10);
				base.passedStep("Clicked on OK button to continue without text files");
			}

			base.waitForElement(getMappingSOURCEFIELD2());
			if (dataset.contains("Collection1K_Tally")) {
				getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DocID");
				getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("Datasource");
				getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");

				base.waitForElement(getMappingFIELDCAT25());
				getMappingFIELDCAT25().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD25().selectFromDropdown().selectByVisibleText("DocFileExtension");

				base.waitForElement(getMappingFIELDCAT26());
				getMappingFIELDCAT26().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD26().selectFromDropdown().selectByVisibleText("DocFileName");

				base.waitForElement(getMappingFIELDCAT27());
				getMappingFIELDCAT27().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD27().selectFromDropdown().selectByVisibleText("DocFileSize");

				base.waitForElement(getMappingFIELDCAT28());
				getMappingFIELDCAT28().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD28().selectFromDropdown().selectByVisibleText("DocFileType");
			}

			else if (dataset.contains("20Family_20Threaded")) {
				base.waitForElement(getMappingSOURCEFIELD2());
				getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("ProdEnd");
				getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("Datasource");
				getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");

				base.waitForElement(getMappingFIELDCAT9());
				getMappingFIELDCAT9().selectFromDropdown().selectByVisibleText("EMAIL");
				getMappingDESTINATIONFIELD9().selectFromDropdown().selectByVisibleText("EmailAuthorNameAndAddress");

				base.waitForElement(getMappingFIELDCAT10());
				getMappingFIELDCAT10().selectFromDropdown().selectByVisibleText("EMAIL");
				getMappingDESTINATIONFIELD10().selectFromDropdown().selectByVisibleText("EmailBCCNamesAndAddresses");

				base.waitForElement(getMappingFIELDCAT13());
				getMappingFIELDCAT13().selectFromDropdown().selectByVisibleText("EMAIL");
				getMappingDESTINATIONFIELD13().selectFromDropdown().selectByVisibleText("EmailCCNamesAndAddresses");

				base.waitForElement(getMappingFIELDCAT25());
				getMappingFIELDCAT25().selectFromDropdown().selectByVisibleText("FAMILY");
				getMappingDESTINATIONFIELD25().selectFromDropdown().selectByVisibleText("FamilyRelationship");

				base.waitForElement(getMappingFIELDCAT26());
				getMappingFIELDCAT26().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD26().selectFromDropdown().selectByVisibleText("DocFileExtension");

				base.waitForElement(getMappingFIELDCAT27());
				getMappingFIELDCAT27().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD27().selectFromDropdown().selectByVisibleText("DocFileName");

				base.waitForElement(getMappingFIELDCAT28());
				getMappingFIELDCAT28().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD28().selectFromDropdown().selectByVisibleText("DocFileSize");

				base.waitForElement(getMappingFIELDCAT29());
				getMappingFIELDCAT29().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD29().selectFromDropdown().selectByVisibleText("DocFileType");

				base.waitForElement(getMappingFIELDCAT31());
				getMappingFIELDCAT31().selectFromDropdown().selectByVisibleText("FAMILY");
				getMappingDESTINATIONFIELD31().selectFromDropdown().selectByVisibleText("FamilyID");

				base.waitForElement(getMappingFIELDCAT49());
				getMappingFIELDCAT49().selectFromDropdown().selectByVisibleText("EMAIL");
				getMappingDESTINATIONFIELD49().selectFromDropdown().selectByVisibleText("EmailReceivedDate");

				base.waitForElement(getMappingFIELDCAT51());
				getMappingFIELDCAT51().selectFromDropdown().selectByVisibleText("EMAIL");
				getMappingDESTINATIONFIELD51().selectFromDropdown().selectByVisibleText("EmailToNamesAndAddresses");
			}

			else if (dataset.contains("AllSources")) {

				getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("ProdBeg");
				getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("ProdBeg");
				getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");

				base.waitForElement(getMappingFIELDCAT25());
				getMappingFIELDCAT25().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD25().selectFromDropdown().selectByVisibleText("DocFileExtension");

				base.waitForElement(getMappingFIELDCAT26());
				getMappingFIELDCAT26().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD26().selectFromDropdown().selectByVisibleText("DocFileName");

				base.waitForElement(getMappingFIELDCAT27());
				getMappingFIELDCAT27().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD27().selectFromDropdown().selectByVisibleText("DocFileSize");

				base.waitForElement(getMappingFIELDCAT28());
				getMappingFIELDCAT28().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD28().selectFromDropdown().selectByVisibleText("DocFileType");
			}

			else if (dataset.contains("0002_H13696_1_Latest")) {

				base.waitForElement(getMappingSOURCEFIELD2());
				getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DOCID");
				getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("DOCID");
				getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("DOCID");
			} else if (dataset.contains("16JanMultiPTIFF")) {

				base.waitForElement(getMappingSOURCEFIELD2());
				getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("BNum");
				getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("BNum");
				getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("CName");
			}

			else if (dataset.contains("27MarSinglePageTIFF")) {

				base.waitForElement(getMappingSOURCEFIELD2());
				getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("BNum");
				getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("BNum");
				getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("CName");
				getMappingSOURCEFIELD5().selectFromDropdown().selectByVisibleText("RequirePDFGeneration");
				getMappingFIELDCAT5().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD5().selectFromDropdown().selectByVisibleText("RequirePDFGeneration");
			}

			else if (dataset.contains("QA_EmailConcatenatedData_SS")) {

				base.waitForElement(getMappingSOURCEFIELD2());
				getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("BatesNumber");
				getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("DataSource");
				getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("CustodianName");

			}

			else if (dataset.contains("SSAudioSpeech_Transcript")) {

				base.waitForElement(getMappingSOURCEFIELD2());
				getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DocID");
				getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("Datasource");
				getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");

				base.waitForElement(getMappingFIELDCAT25());
				getMappingFIELDCAT25().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD25().selectFromDropdown().selectByVisibleText("DocFileExtension");

				base.waitForElement(getMappingFIELDCAT26());
				getMappingFIELDCAT26().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD26().selectFromDropdown().selectByVisibleText("DocFileName");

				base.waitForElement(getMappingFIELDCAT27());
				getMappingFIELDCAT27().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD27().selectFromDropdown().selectByVisibleText("DocFileSize");

				base.waitForElement(getMappingFIELDCAT28());
				getMappingFIELDCAT28().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD28().selectFromDropdown().selectByVisibleText("DocFileType");

			}

			else if (dataset.contains("GD_994_Native_Text_ForProduction")) {

				base.waitForElement(getMappingSOURCEFIELD2());
				getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DocID");
				getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("DocID");
				getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("DocID");

				base.waitForElement(getMappingFIELDCAT5());
				getMappingSOURCEFIELD5().selectFromDropdown().selectByVisibleText("EmailAuthorNameAndAddress");
				getMappingFIELDCAT5().selectFromDropdown().selectByVisibleText("EMAIL");
				getMappingDESTINATIONFIELD5().selectFromDropdown().selectByVisibleText("EmailAuthorNameAndAddress");

				getAddButton().waitAndClick(15);
				Thread.sleep(2000);

				base.waitForElement(getMappingFIELDCAT6());
				getMappingSOURCEFIELD6().selectFromDropdown().selectByVisibleText("EmailBCCNameAndBCCAddress");
				getMappingFIELDCAT6().selectFromDropdown().selectByVisibleText("EMAIL");
				getMappingDESTINATIONFIELD6().selectFromDropdown().selectByVisibleText("EmailBCCNamesAndAddresses");

				getAddButton().waitAndClick(15);
				Thread.sleep(2000);

				base.waitForElement(getMappingFIELDCAT7());
				getMappingSOURCEFIELD7().selectFromDropdown().selectByVisibleText("EmailCCNamAndCCAddress");
				getMappingFIELDCAT7().selectFromDropdown().selectByVisibleText("EMAIL");
				getMappingDESTINATIONFIELD7().selectFromDropdown().selectByVisibleText("EmailCCNamesAndAddresses");

				getAddButton().waitAndClick(15);
				Thread.sleep(2000);

				base.waitForElement(getMappingFIELDCAT8());
				getMappingSOURCEFIELD8().selectFromDropdown().selectByVisibleText("EmailToNameAndAddress");
				getMappingFIELDCAT8().selectFromDropdown().selectByVisibleText("EMAIL");
				getMappingDESTINATIONFIELD8().selectFromDropdown().selectByVisibleText("EmailToNamesAndAddresses");
			}

			else if (dataset.contains("GNon searchable PDF Load file")) {

				base.waitForElement(getMappingSOURCEFIELD2());
				getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("SourceDocID");
			}

			else if (dataset.contains("HiddenProperties_IngestionData")) {

				base.waitForElement(getMappingSOURCEFIELD2());
				getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("SourceDocID");
			}

			else if (dataset.contains("UniCodeFiles")) {

				base.waitForElement(getMappingSOURCEFIELD2());
				getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DocID");
				getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("DocID");
				getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");
			}

			else if (dataset.contains("IngestionEmailData")) {

				base.waitForElement(getMappingSOURCEFIELD2());
				getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DocumentID");
				getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("DocumentID");
				getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("CustID");

				base.waitForElement(getMappingFIELDCAT76());
				getMappingFIELDCAT76().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD76().selectFromDropdown().selectByVisibleText("DocFileName");

				base.waitForElement(getMappingFIELDCAT73());
				getMappingFIELDCAT73().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD73().selectFromDropdown().selectByVisibleText("DocFileSize");

				base.waitForElement(getMappingFIELDCAT75());
				getMappingFIELDCAT75().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD75().selectFromDropdown().selectByVisibleText("DocFileType");
			} else if (dataset.contains("CJK_GermanAudioTestData")) {

				base.waitForElement(getMappingSOURCEFIELD2());
				getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DocID");
				getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("Datasource");
				getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");

				base.waitForElement(getMappingFIELDCAT25());
				getMappingFIELDCAT25().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD25().selectFromDropdown().selectByVisibleText("DocFileExtension");

				base.waitForElement(getMappingFIELDCAT26());
				getMappingFIELDCAT26().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD26().selectFromDropdown().selectByVisibleText("DocFileName");

				base.waitForElement(getMappingFIELDCAT27());
				getMappingFIELDCAT27().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD27().selectFromDropdown().selectByVisibleText("DocFileSize");

				base.waitForElement(getMappingFIELDCAT28());
				getMappingFIELDCAT28().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD28().selectFromDropdown().selectByVisibleText("DocFileType");

			} else if (dataset.contains("CJK_JapaneseAudioTestData")) {

				base.waitForElement(getMappingSOURCEFIELD2());
				getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DocID");
				getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("Datasource");
				getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");

				base.waitForElement(getMappingFIELDCAT25());
				getMappingFIELDCAT25().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD25().selectFromDropdown().selectByVisibleText("DocFileExtension");

				base.waitForElement(getMappingFIELDCAT26());
				getMappingFIELDCAT26().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD26().selectFromDropdown().selectByVisibleText("DocFileName");

				base.waitForElement(getMappingFIELDCAT27());
				getMappingFIELDCAT27().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD27().selectFromDropdown().selectByVisibleText("DocFileSize");

				base.waitForElement(getMappingFIELDCAT28());
				getMappingFIELDCAT28().selectFromDropdown().selectByVisibleText("DOCBASIC");
				getMappingDESTINATIONFIELD28().selectFromDropdown().selectByVisibleText("DocFileType");

			}
			// Below called function handles all the stages of ingestion from catalog to
			// publish!
			IngestionCatlogtoIndexing(dataset);

		}

	}

	public void approveAndPublishIngestion(String dataset) throws InterruptedException {

		Thread.sleep(5000);
		// Approve
		getIngestionName().waitAndClick(Input.wait30);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getActionDropdownArrow().Visible();
			}
		}), Input.wait60);
		getActionDropdownArrow().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getActionApprove().Visible();
			}
		}), Input.wait60);
		getActionApprove().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getApproveMessageOKButton().Visible();
			}
		}), Input.wait30);
		getApproveMessageOKButton().waitAndClick(10);

		base.VerifySuccessMessage("Approve started successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCloseButton().Visible();
			}
		}), Input.wait30);
		getCloseButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByButton().Visible();
			}
		}), Input.wait30);
		getFilterByButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByAPPROVED().Visible();
			}
		}), Input.wait30);
		getFilterByAPPROVED().waitAndClick(10);

		for (int i = 0; i < 30; i++) {
			try {
				if (getInprogressIngestionStatus().isDisplayed()) {
					base.waitTime(6);
					getRefreshButton().waitAndClick(10);
					getApproveIngestionStatus().isDisplayed();
					base.passedStep("Approved completed");
					UtilityLog.info(dataset + " approved.");
				} else {
					base.waitForElement(getApproveIngestionStatus());
					getApproveIngestionStatus().isDisplayed();
					base.passedStep("Approved completed");
					UtilityLog.info(dataset + " approved.");
				}
				break;

			} catch (Exception e) {
				try {
					Thread.sleep(5000);
					getRefreshButton().waitAndClick(10);
					if (getFailedIngestionStatus().Displayed()) {
						System.out.println("Execution aborted!");
						UtilityLog.info("Execution aborted!");
						System.out.println(dataset + " is failed in approving stage. Take a look and continue!");
						UtilityLog.info(dataset + " is failed in approving stage. Take a look and continue!");
						System.exit(1);

					}
				} catch (Throwable e1) {
					System.out.println("Task in Progress : " + i);
					UtilityLog.info("Task in Progress : " + i);
				}

			}
		}

		// Analytics run
		this.driver.getWebDriver().get(Input.url + "Ingestion/Analytics");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getRunAnalyticsRunButton().Visible();
			}
		}), Input.wait60);
		getRunAnalyticsRunButton().waitAndClick(10);

		base.VerifySuccessMessage("Run has Started successfully");

		for (int iteration = 1; iteration <= 10; iteration++) {

			String endTime = getIngestion_PublishEndTime().getText();
			try {

				if (endTime.isEmpty() || endTime.startsWith("2")) {
					try {
						WebElement element = driver.getWebDriver()
								.findElement(By.xpath("//*[@id='ProjectFieldsDataTable']//tr[1]//td[4]"));
						element.isDisplayed();
						endTime = getIngestion_PublishEndTime().getText();
						if (endTime.length() > 5) {
							break;
						}

					} catch (Exception E) {
						base.waitTime(60);
						driver.Navigate().refresh();

					}

				}
			} catch (Exception e) {
				System.out.println("EndTime status" + endTime);

			}
			driver.waitForPageToBeReady();
		}

		base.waitForElement(getRunAnalyticsPublishButton());
		getRunAnalyticsPublishButton().waitAndClick(10);
		base.passedStep("Publish completed");
		UtilityLog.info(dataset + " analytics completed.");

		base.VerifySuccessMessage("Publish has Started successfully");

		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");

		// Publish
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByButton().Visible();
			}
		}), Input.wait30);
		getFilterByButton().waitAndClick(Input.wait30);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByFAILED().Visible();
			}
		}), Input.wait30);
		getFilterByFAILED().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByPUBLISHED().Visible();
			}
		}), Input.wait30);
		getFilterByPUBLISHED().waitAndClick(10);

		for (int i = 0; i < 10; i++) {
			try {

				getPublishIngestionStatus().Displayed();
				UtilityLog.info(dataset + " published.");
				break;
			} catch (Exception e) {
				try {
					Thread.sleep(5000);
					getRefreshButton().waitAndClick(10);
					if (getFailedIngestionStatus().Displayed()) {
						System.out.println("Execution aborted!");
						UtilityLog.info("Execution aborted!");
						System.out.println(dataset + " is failed in publishing stage. Take a look and continue!");
						UtilityLog.info(dataset + " is failed in publishing stage. Take a look and continue!");
						System.exit(1);

					}
				} catch (Throwable e1) {
					System.out.println("Task in Progress : " + i);
					UtilityLog.info("Task in Progress : " + i);
				}

			}

		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getIngestionName().Visible();
			}
		}), Input.wait60);
		getIngestionName().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getIngestionNameText().Visible();
			}
		}), Input.wait60);
		IngestionName = getIngestionNameText().getText();
		Thread.sleep(2000);
		System.out.println(IngestionName);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCloseButton().Enabled();
			}
		}), Input.wait30);
		getCloseButton().waitAndClick(10);
	}

	/**
	 * @author: Mohan Created Date: 17/02/2022 Modified by: NA Modified Date: NA
	 * @description: verify Source System tobe disabled when we add overlay in the
	 *               Ingestion
	 */
	public void verifySourceSystemDisabledWhenOverylayIsAddedAsIngestionType() {

		base.waitForElement(getAddanewIngestionButton());
		getAddanewIngestionButton().waitAndClick(10);

		base.waitForElement(getIngestion_IngestionType());
		getIngestion_IngestionType().selectFromDropdown().selectByVisibleText("Overlay Only");

		if (getSpecifySourceSystem().Enabled()) {
			base.failedStep("Sorce System is Enabled");

		} else {
			base.passedStep("'Source System' is disabled on add new Ingestion");
		}
	}
	
	
	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to select ingestion type and specify source loaction.
	 * @param ingestionType : ingestionType is String value need to select type of ingestion need to perform.
	 * @param sourceSystem : sourceSystem is String value that name of source system.
	 * @param sourceLocation : sourceLocation is String value that location of source.
	 * @param sourceFolder : sourceFolder is String value that folder of source.
	 */
	public void selectIngestionTypeAndSpecifySourceLocation(String ingestionType,String sourceSystem,String sourceLocation,String sourceFolder) {
		try {
			driver.getWebDriver().get(Input.url + "Ingestion/Home");
			driver.waitForPageToBeReady();
			base.waitForElement(getAddanewIngestionButton());
			getAddanewIngestionButton().isElementAvailable(10);
			getAddanewIngestionButton().Click();
			base.waitForElement(getIngestion_IngestionType());
			getIngestion_IngestionType().isElementAvailable(10);
			getIngestion_IngestionType().selectFromDropdown().selectByVisibleText(ingestionType);
			if(!ingestionType.trim().equalsIgnoreCase("Overlay Only")) {
				base.waitForElement(getSpecifySourceSystem());
				getSpecifySourceSystem().isElementAvailable(10);
				getSpecifySourceSystem().selectFromDropdown().selectByVisibleText(sourceSystem);
			}
			base.waitForElement(getSpecifyLocation());
			getSpecifyLocation().isElementAvailable(10);
			for (int i = 0; i < 30; i++) {
				try {
					getSpecifyLocation().selectFromDropdown().selectByVisibleText(sourceLocation);
					break;
				} catch (Exception e) {
					base.waitTime(1);
				}
			}
			base.waitForElement(getSpecifySourceFolder());
			getSpecifySourceFolder().isElementAvailable(10);
			for (int i = 0; i < 30; i++) {
				try {
					getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(sourceFolder);
					break;
				} catch (Exception e) {
					base.waitTime(1);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while selecting ingestion type and specify source loaction"+e.getLocalizedMessage());
		}
	}
	
	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to select DAT delimiters.
	 * @param fieldSeperator : fieldSeperator is String value that field seperator .
	 * @param textQualifier : textQualifier is String value that name of text qualifier.
	 * @param multiValue : multiValue is String value that multi value.
	 */
	public void addDelimitersInIngestionWizard(String fieldSeperator,String textQualifier,String multiValue) {
		try {
			driver.scrollPageToTop();
			base.waitForElement(getDATDelimitersFieldSeparator());
			getDATDelimitersFieldSeparator().isElementAvailable(15);
			for (int i = 0; i < 30; i++) {
				try {
					getDATDelimitersFieldSeparator().selectFromDropdown().selectByVisibleText(fieldSeperator);
					break;
				} catch (Exception e) {
					base.waitTime(1);
				}
			}
			base.waitForElement(getDATDelimitersTextQualifier());
			getDATDelimitersTextQualifier().isElementAvailable(15);
			getDATDelimitersTextQualifier().selectFromDropdown().selectByVisibleText(textQualifier);
			base.waitForElement(getDATDelimitersNewLine());
			getDATDelimitersNewLine().isElementAvailable(15);
			getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(multiValue);
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while selecting DAT delimiters."+e.getLocalizedMessage());
		}
	}
	
	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to select DAT source.
	 * @param loadFile : loadFile is String value that load file value.
	 * @param documentKey : documentKey is String value that document key.
	 */
	public void selectDATSource(String loadFile,String documentKey) {
		try {
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			getSourceSelectionDATLoadFile().ScrollTo();
			base.waitForElement(getSourceSelectionDATLoadFile());
			getSourceSelectionDATLoadFile().isElementAvailable(15);
			getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(loadFile);
			driver.waitForPageToBeReady();
			getSourceSelectionDATKey().ScrollTo();
			base.waitForElement(getSourceSelectionDATKey());
			getSourceSelectionDATKey().isElementAvailable(15);
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText(documentKey);
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while selecting DAT source."+e.getLocalizedMessage());
		}
	}
	
	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to select native source.
	 * @param loadFile : loadFile is String value that load file value.
	 * @param pathInDATFileflag : pathInDATFileflag is boolean value that weather path in DAT file check box need to enable or not.
	 */
	public void selectNativeSource(String loadFile,boolean pathInDATFileflag) {
		try {
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			getNativeLoadFileCheckBox().ScrollTo();
			getNativeLoadFileCheckBox().isElementAvailable(15);
			getNativeLoadFileCheckBox().Click();
			getNativeLST().ScrollTo();
			base.waitForElement(getNativeLST());
			getNativeLST().isElementAvailable(15);
			getNativeLST().selectFromDropdown().selectByVisibleText(loadFile);
			if(pathInDATFileflag) {
				getNativePathInDATFileCheckBox().isElementAvailable(10);
				getNativePathInDATFileCheckBox().Click();
			}
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while selecting Native source."+e.getLocalizedMessage());
		}
	}
	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to select text source.
	 * @param loadFile : loadFile is String value that load file value.
	 * @param pathInDATFileflag : pathInDATFileflag is boolean value that weather path in DAT file check box need to enable or not.
	 */
	public void selectTextSource(String loadFile,boolean pathInDATFileflag) {
		try {
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			getTextCheckBox().ScrollTo();
			getTextCheckBox().isElementAvailable(15);
			getTextCheckBox().Click();
			getSourceSelectionTextLoadFile().ScrollTo();
			base.waitForElement(getSourceSelectionTextLoadFile());
			getSourceSelectionTextLoadFile().isElementAvailable(15);
			getSourceSelectionTextLoadFile().selectFromDropdown().selectByVisibleText(loadFile);
			if(pathInDATFileflag) {
				getTextPathInDATFileCheckBox().isElementAvailable(10);
				getTextPathInDATFileCheckBox().Click();
			}
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while selecting text source."+e.getLocalizedMessage());
		}
	}
	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to select PDF source.
	 * @param loadFile : loadFile is String value that load file value.
	 * @param pathInDATFileflag : pathInDATFileflag is boolean value that weather path in DAT file check box need to enable or not.
	 */
	public void selectPDFSource(String loadFile,boolean pathInDATFileflag) {
		try {
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			getPDFCheckBoxstionButton().ScrollTo();
			getPDFCheckBoxstionButton().isElementAvailable(15);
			getPDFCheckBoxstionButton().Click();
			getPDFLST().ScrollTo();
			base.waitForElement(getPDFLST());
			getPDFLST().isElementAvailable(15);
			getPDFLST().selectFromDropdown().selectByVisibleText(loadFile);
			if(pathInDATFileflag) {
				getPDFPathInDATFileCheckBox().isElementAvailable(10);
				getPDFPathInDATFileCheckBox().Click();
			}
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while selecting PDF source."+e.getLocalizedMessage());
		}
	}
	
	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to select TIFF source.
	 * @param loadFile : loadFile is String value that load file value.
	 * @param pathInDATFileflag : pathInDATFileflag is boolean value that weather path in DAT file check box need to enable or not.
	 * @param genSearchPDFCheckBoxFlag : genSearchPDFCheckBoxFlag is boolean value that weather generate searchable pdf check box need to enable or not.
	 */
	public void selectTIFFSource(String loadFile,boolean pathInDATFileflag,boolean genSearchPDFCheckBoxFlag) {
		try {
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			getTIFFCheckBox().ScrollTo();
			getTIFFCheckBox().isElementAvailable(15);
			getTIFFCheckBox().Click();
			getTIFFLST().ScrollTo();
			base.waitForElement(getTIFFLST());
			getTIFFLST().isElementAvailable(15);
			getTIFFLST().selectFromDropdown().selectByVisibleText(loadFile);
			if(pathInDATFileflag) {
				getTIFFPathInDATFileCheckBox().isElementAvailable(10);
				getTIFFPathInDATFileCheckBox().Click();
			}
			if(genSearchPDFCheckBoxFlag) {
				getTIFFSearchablePDFCheckBox().isElementAvailable(10);
				getTIFFSearchablePDFCheckBox().Click();
			}
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while selecting TIFF source."+e.getLocalizedMessage());
		}
	}
	
/**
 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
 * @description: Method to select MP3 varient source.
 * @param loadFile : loadFile is String value that load file value.
 * @param pathInDATFileflag : pathInDATFileflag is boolean value that weather path in DAT file check box need to enable or not.
 */
public void selectMP3VarientSource(String loadFile,boolean pathInDATFileflag) {
	try {
		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();
		getMP3CheckBoxstionButton().ScrollTo();
		getMP3CheckBoxstionButton().isElementAvailable(15);
		getMP3CheckBoxstionButton().Click();
		getMP3LST().ScrollTo();
		base.waitForElement(getMP3LST());
		getMP3LST().isElementAvailable(15);
		getMP3LST().selectFromDropdown().selectByVisibleText(loadFile);
		if(pathInDATFileflag) {
			getMP3PathInDATFileCheckBox().isElementAvailable(10);
			getMP3PathInDATFileCheckBox().Click();
		}
	}catch(Exception e) {
		e.printStackTrace();
		base.failedStep("Exception occured while selecting MP3 varient source."+e.getLocalizedMessage());
	}
}

	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to select Audio Transcript source.
	 * @param loadFile : loadFile is String value that load file value.
	 * @param pathInDATFileflag : pathInDATFileflag is boolean value that weather path in DAT file check box need to enable or not.
	 */
	public void selectAudioTranscriptSource(String loadFile,boolean pathInDATFileflag) {
		try {
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			getAudioTranscriptCheckBoxstionButton().ScrollTo();
			getAudioTranscriptCheckBoxstionButton().isElementAvailable(15);
			getAudioTranscriptCheckBoxstionButton().Click();
			getAudioTranscriptLST().ScrollTo();
			base.waitForElement(getAudioTranscriptLST());
			getAudioTranscriptLST().isElementAvailable(15);
			getAudioTranscriptLST().selectFromDropdown().selectByVisibleText(loadFile);
			if(pathInDATFileflag) {
				getAudioTransistPathInDATFileCheckBox().isElementAvailable(10);
				getAudioTransistPathInDATFileCheckBox().Click();
			}
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while selecting Audio Transcript source."+e.getLocalizedMessage());
		}
	}
	
	
	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to select Other source.
	 * @param loadFile : loadFile is String value that load file value.
	 * @param linkType : linkType is String value that link type value.
	 * @param pathInDATFileflag : pathInDATFileflag is boolean value that weather path in DAT file check box need to enable or not.
	 */
	public void selectOtherSource(String linkType,String loadFile,boolean pathInDATFileflag) {
		try {
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			getOtherCheckBox().ScrollTo();
			getOtherCheckBox().isElementAvailable(15);
			getOtherCheckBox().Click();
			getOtherLinkType().ScrollTo();
			base.waitForElement(getOtherLinkType());
			getOtherLinkType().isElementAvailable(15);
			getOtherLinkType().selectFromDropdown().selectByVisibleText(linkType);
			driver.waitForPageToBeReady();
			getOtherLoadFile().selectFromDropdown().selectByVisibleText(loadFile);
			if(pathInDATFileflag) {
				getOtherPathInDATFileCheckBox().isElementAvailable(10);
				getOtherPathInDATFileCheckBox().Click();
			}
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while selecting other source."+e.getLocalizedMessage());
		}
	}
	
	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to select Date and Time format.
	 * @param format : format is String value that date format.
	 */
	public void selectDateAndTimeForamt(String format) {
		try {
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			getDateFormat().isElementAvailable(15);
			getDateFormat().selectFromDropdown().selectByVisibleText(format);
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while selecting Date and Time format."+e.getLocalizedMessage());
		}
	}
	
	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to click on next button.
	 */
	public void clickOnNextButton() {
		try {
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			getNextButton().isElementAvailable(15);
			getNextButton().Click();
			driver.waitForPageToBeReady();
			if(getApproveMessageOKButton().isDisplayed()) {
				getApproveMessageOKButton().isElementAvailable(15);
				getApproveMessageOKButton().Click();
			}
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while click on next button."+e.getLocalizedMessage());
		}
	}
	
	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to select value from first three source DAT fields
	 */
	public void selectValueFromEnabledFirstThreeSourceDATFields(String firstDropDown,String secondDropDown,String thirdDropDown) {
		try {
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			getEnabledFirstDropDown().isElementAvailable(15);
			getEnabledFirstDropDown().selectFromDropdown().selectByVisibleText(firstDropDown);
			getEnabledSecondDropDown().isElementAvailable(15);
			getEnabledSecondDropDown().selectFromDropdown().selectByVisibleText(secondDropDown);
			getEnabledThirdDropDown().isElementAvailable(15);
			getEnabledThirdDropDown().selectFromDropdown().selectByVisibleText(thirdDropDown);
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while select value from first three source DAT fields."+e.getLocalizedMessage());
		}
	}
	
	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to click on preview and run button.
	 */
	public void clickOnPreviewAndRunButton() {
		try {
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			getPreviewRun().isElementAvailable(15);
			getPreviewRun().Click();
			driver.waitForPageToBeReady();
			getApproveMessageOKButton().isElementAvailable(15);
			getApproveMessageOKButton().Click();
			driver.waitForPageToBeReady();
			getbtnRunIngestion().isElementAvailable(15);
			getbtnRunIngestion().Click();
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while click on preview and run button."+e.getLocalizedMessage());
		}
	}
	
	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to select all options from filter by dropdown.
	 */
	public void selectAllOptionsFromFilterByDropdown() {
		try {
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			getFilterByButton().isElementAvailable(15);
			getFilterByButton().Click();
			driver.waitForPageToBeReady();
			getFilterByDRAFT().isElementAvailable(10);
			getFilterByDRAFT().Click();
			getFilterByFAILED().isElementAvailable(10);
			getFilterByFAILED().Click();
			getFilterINPROGRESS().isElementAvailable(10);
			if(!getFilterINPROGRESS().GetAttribute("class").contains("active")) {
				getFilterByINPROGRESS().isElementAvailable(10);
				getFilterByINPROGRESS().Click();
			}
			getFilterByCATALOGED().isElementAvailable(10);
			getFilterByCATALOGED().Click();
			getFilterByCOPIED().isElementAvailable(10);
			getFilterByCOPIED().Click();
			getFilterByINDEXED().isElementAvailable(10);
			getFilterByINDEXED().Click();
			getFilterByAPPROVED().isElementAvailable(10);
			getFilterByAPPROVED().Click();
			getFilterByPUBLISHED().isElementAvailable(10);
			getFilterByPUBLISHED().Click();
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while selecting all options from filter by dropdown."+e.getLocalizedMessage());
		}
	}
	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to navigate to ingestion page.
	 */
	public void navigateToIngestionPage() {
		try {
			driver.getWebDriver().get(Input.url + "Ingestion/Home");
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while navigating to ingestion page."+e.getLocalizedMessage());
		}
	}
	
	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to create ingestion to cataloged stage
	 */
	public String ingestionCreationToCatalogedStage() {
		String title = null;
		try {
			String titleCar = null;
			int count = 0;
			driver.waitForPageToBeReady();
			base.waitForElement(getStatus());
			for(int i=1;i<500;i++) {
				driver.waitForPageToBeReady();
				getIngestionTitle(count+1).ScrollTo();
				getIngestionTitle(count+1).isElementAvailable(15);
				title = getIngestionTitle(count+1).GetAttribute("title").trim();
				getStatus(count+1).isElementAvailable(15);
				String status = getStatus(count+1).getText().trim();
				driver.waitForPageToBeReady();
				for(int j=1;j<50;j++) {
					titleCar = getIngestionTitle(j).GetAttribute("title").trim();
					getIngestionTitle(j).ScrollTo();
					if(titleCar.equalsIgnoreCase(title)) {
						if(j!=1) {
							count=j-1;
						}
						break;
					}else {
						driver.scrollingToBottomofAPage();
					}
				}
				if(status.contains("In Progress")) {
					driver.scrollPageToTop();
					getRefreshButton().isElementAvailable(15);
					getRefreshButton().Click();
				}
				if(status.contains("Cataloged") && titleCar.equalsIgnoreCase(title)) {
					base.passedStep("Ingestion completed till cataloged stage");
					break;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while navigating to ingestion page."+e.getLocalizedMessage());
		}
		return title;
	}
	
	
	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to select field catagory and destination field by using source DAT field.
	 * @param sourceDATField : sourceDATField is String value that source DAT field selected in drop down.
	 * @param fieldCatagory : fieldCatagory is String value that field catagory need to select sibling drop down of sourceDATField.
	 * @param destinationField : destinationField is String value that destination Field need to select sibling drop down of sourceDATField.
	 */
	public void selectFieldCatagoryDestinationFields(String sourceDATField,String fieldCatagory,String destinationField) {
		try {
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			getFieldCatagoryBySourceDat(sourceDATField).ScrollTo();
			getFieldCatagoryBySourceDat(sourceDATField).isElementAvailable(15);
			getFieldCatagoryBySourceDat(sourceDATField).selectFromDropdown().selectByVisibleText(fieldCatagory);
			driver.waitForPageToBeReady();
			getDestinationFieldBySourceDat(sourceDATField).ScrollTo();
			getDestinationFieldBySourceDat(sourceDATField).isElementAvailable(15);
			getDestinationFieldBySourceDat(sourceDATField).selectFromDropdown().selectByVisibleText(destinationField);
			
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while selecting field catagory and destination field by using source DAT field."+e.getLocalizedMessage());
		}
	}
	
	
	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to process to click on copied state without ignoring errors.
	 */
	public void clickOnCopiedStateWithoutIgnoringErrors(String ingestionName) {
		try {
			driver.scrollPageToTop();
			String status = null;
			for(int i=0;i<30;i++) {
				if(getStatusByingestionName(ingestionName).isDisplayed()) {
					status = getStatusByingestionName(ingestionName).getText().trim();
					break;
				}else {
					driver.scrollingToBottomofAPage();
				}
			}
			if(status.contains("Cataloged")) {
				driver.waitForPageToBeReady();
				getIngestionLinkByName(ingestionName).isElementAvailable(15);
				getIngestionLinkByName(ingestionName).Click();
				driver.waitForPageToBeReady();
				getStartCopy().ScrollTo();
				getStartCopy().isElementAvailable(15);
				getStartCopy().Click();
				driver.scrollPageToTop();
				getCloseButton().isElementAvailable(15);
				getCloseButton().Click();
				getRefreshButton().isElementAvailable(15);
				getRefreshButton().Click();
			}
			
			for(int i=0;i<2000;i++) {
				for(int j=0;j<30;j++) {
					if(getStatusByingestionName(ingestionName).isDisplayed()) {
						status = getStatusByingestionName(ingestionName).getText().trim();
						break;
					}else {
						driver.scrollingToBottomofAPage();
					}
				}
				if(status.contains("In Progress")) {
					driver.scrollPageToTop();
					getRefreshButton().isElementAvailable(15);
					getRefreshButton().Click();
				}
				if(status.contains("Failed")) {
					base.passedStep("Ingestion entered to failed state if copied state starts without ignoring errors");
					break;
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while selecting field catagory and destination field by using source DAT field."+e.getLocalizedMessage());
		}
	}
/**
	 * @author: Mohan Created Date: 24/02/2022 Modified by: NA Modified Date: NA
	 * @description: verify Source System tobe disabled when we add overlay in the
	 *               Ingestion
	 */
	public void validateDateAndTimeFormateWhenIngestionIsSaveAsDraft(String dataset, String dateFormate)
			throws InterruptedException {

		base.stepInfo("Click on add new ingestion button");
		base.waitForElement(getAddanewIngestionButton());
		getAddanewIngestionButton().waitAndClick(10);

		base.stepInfo("Select Source system");
		base.waitForElement(getSpecifySourceSystem());
		getSpecifySourceSystem().selectFromDropdown().selectByVisibleText("TRUE");

		base.stepInfo("Select Location");
		base.waitForElement(getSpecifyLocation());
		Thread.sleep(2000);
		getSpecifyLocation().selectFromDropdown().selectByVisibleText(Input.SourceLocation);

		base.waitForElement(getSpecifySourceFolder());
		Thread.sleep(2000);
		base.stepInfo("Select Folder");
		for (int i = 0; i < 30; i++) {

			if (dataset.contains("Collection1K_Tally")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.Collection1KFolder);
			} else if (dataset.contains("20Family_20Threaded")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.FamilyFolder);
			} else if (dataset.contains("AllSources")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.AllSourcesFolder);
			} else if (dataset.contains("0002_H13696_1_Latest")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.H1369Folder);
			} else if (dataset.contains("16JanMultiPTIFF")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.MultiPTIFFFolder);
			} else if (dataset.contains("27MarSinglePageTIFF")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.SinglePageTIFFFolder);
			} else if (dataset.contains("CJK_FrenchAudioTestData")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.CJK_FrenchAudioTestDataFolder);
			} else if (dataset.contains("QA_EmailConcatenatedData_SS")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.EmailConcatenatedDataFolder);
			} else if (dataset.contains("SSAudioSpeech_Transcript")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.SSAudioSpeechFolder);
			} else if (dataset.contains("GD_994_Native_Text_ForProduction")) {
				getSpecifySourceFolder().selectFromDropdown()
						.selectByVisibleText(Input.GD994NativeTextForProductionFolder);
			} else if (dataset.contains("GNon_searchable_PDF_Load_file")) {
				getSpecifySourceFolder().selectFromDropdown()
						.selectByVisibleText(Input.GNonsearchablePDFLoadfileFolder);
			} else if (dataset.contains("HiddenProperties_IngestionData")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.HiddenPropertiesFolder);
			} else if (dataset.contains("UniCodeFiles")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.UniCodeFilesFolder);
			} else if (dataset.contains("IngestionEmailData")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.IngestionEmailDataFolder);
			} else if (dataset.contains("CJK_GermanAudioTestData") || dataset.contains("CJK_JapaneseAudioTestData")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.CJK_FrenchAudioTestDataFolder);
			}
		}
		Thread.sleep(2000);
		base.waitForElement(getDATDelimitersFieldSeparator());
		getDATDelimitersFieldSeparator().selectFromDropdown().selectByVisibleText("ASCII(20)");

		base.waitForElement(getDATDelimitersTextQualifier());
		getDATDelimitersTextQualifier().selectFromDropdown().selectByVisibleText("ASCII(254)");

		base.waitForElement(getDATDelimitersNewLine());
		getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText("ASCII(174)");

		driver.scrollingToBottomofAPage();

		base.waitForElement(getSourceSelectionDATLoadFile());
		if (dataset.contains("CJK_GermanAudioTestData")) {
			getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(Input.DATGermanFile);
		} else if (dataset.contains("CJK_JapaneseAudioTestData")) {
			getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(Input.DATJapneseFile);
		} else if (dataset.contains("HiddenProperties_IngestionData")) {
			getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(Input.YYYYMMDDHHMISSDat);
		} else {
			getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(Input.DATFile);
		}
		base.waitForElement(getSourceSelectionDATKey());
		Thread.sleep(2000);

		if (dataset.contains("Collection1K_Tally")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
		} else if (dataset.contains("20Family_20Threaded")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
		} else if (dataset.contains("AllSources")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("ProdBeg");
		} else if (dataset.contains("0002_H13696_1_Latest")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DOCID");
		} else if (dataset.contains("16JanMultiPTIFF")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("BNum");
		} else if (dataset.contains("27MarSinglePageTIFF")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("BNum");
		} else if (dataset.contains("QA_EmailConcatenatedData_SS")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("BatesNumber");
		} else if (dataset.contains("SSAudioSpeech_Transcript")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
		} else if (dataset.contains("GD_994_Native_Text_ForProduction")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
		} else if (dataset.contains("GNon_searchable_PDF_Load_file")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("SourceDocID");
		} else if (dataset.contains("HiddenProperties_IngestionData")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("SourceDocID");
		} else if (dataset.contains("UniCodeFiles")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
		} else if (dataset.contains("IngestionEmailData")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocumentID");
		} else if (dataset.contains("CJK_GermanAudioTestData") || dataset.contains("CJK_JapaneseAudioTestData")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
		}

		if (dataset.contains("20Family_20Threaded") || dataset.contains("AllSources")
				|| dataset.contains("16JanMultiPTIFF") || dataset.contains("QA_EmailConcatenatedData_SS")
				|| dataset.contains("GD_994_Native_Text_ForProduction")
				|| dataset.contains("GNon_searchable_PDF_Load_file") || dataset.contains("IngestionEmailData")) {
			base.stepInfo("*******Selecing text files***************");
			base.waitForElement(getSourceSelectionText());
			getSourceSelectionText().waitAndClick(20);
			base.waitForElement(getSourceSelectionTextLoadFile());
			getSourceSelectionTextLoadFile().selectFromDropdown().selectByVisibleText(Input.TextFile);

			base.stepInfo("*******Selecing Native files***************");
			base.waitForElement(getNativeCheckBox());
			getNativeCheckBox().waitAndClick(10);
			base.waitForElement(getNativeLST());
			getNativeLST().selectFromDropdown().selectByVisibleText(Input.NativeFile);
		}

		if (dataset.contains("Collection1K_Tally") || dataset.contains("UniCodeFiles")) {
			base.stepInfo("*******Selecing text files***************");
			base.waitForElement(getSourceSelectionText());
			getSourceSelectionText().waitAndClick(20);
			base.waitForElement(getSourceSelectionTextLoadFile());
			getSourceSelectionTextLoadFile().selectFromDropdown().selectByVisibleText(Input.TextFile);
		}

		if (dataset.contains("0002_H13696_1_Latest")) {
			base.stepInfo("*******Selecing Native files***************");
			base.waitForElement(getNativeCheckBox());
			getNativeCheckBox().waitAndClick(10);
			base.waitForElement(getNativeLST());
			getNativeLST().selectFromDropdown().selectByVisibleText(Input.NativeFile);
		}
		if (dataset.contains("HiddenProperties_IngestionData")) {
			base.stepInfo("*******Selecing Native files***************");
			base.waitForElement(getNativeCheckBox());
			getNativeCheckBox().waitAndClick(10);
			base.waitForElement(getNativeLST());
			getNativeLST().selectFromDropdown().selectByVisibleText(Input.YYYYMMDDHHMISSLst);
		}

		if (dataset.contains("AllSources")) {
			base.stepInfo("*******Selecing PDF files***************");
			base.waitForElement(getPDFCheckBoxstionButton());
			getPDFCheckBoxstionButton().waitAndClick(20);
			base.waitForElement(getPDFLST());
			getPDFLST().selectFromDropdown().selectByVisibleText(Input.PDFFile);
		}

		driver.scrollingToBottomofAPage();

		if (dataset.contains("AllSources") || dataset.contains("16JanMultiPTIFF")) {
			base.stepInfo("*******Selecing TIFF files***************");
			base.waitForElement(getTIFFCheckBox());
			getTIFFCheckBox().waitAndClick(20);
			base.waitForElement(getSourceSelectionTextLoadFile());
			getTIFFLST().selectFromDropdown().selectByVisibleText(Input.TIFFFile);
		}

		/*
		 * if( dataset.contains("27MarSinglePageTIFF")) {
		 * base.stepInfo("*******Selecing TIFF files***************");
		 * base.waitForElement(getTIFFCheckBox()); getTIFFCheckBox().waitAndClick(20);
		 * base.waitForElement(getSourceSelectionTextLoadFile());
		 * getTIFFLST().selectFromDropdown().selectByVisibleText(Input.TIFFFile1); }
		 */
		driver.scrollingToBottomofAPage();

		if (dataset.contains("0002_H13696_1_Latest") || dataset.contains("SSAudioSpeech_Transcript")
				|| dataset.contains("AllSources")) {
			base.stepInfo("*******Selecing MP3 files***************");
			base.waitForElement(getMP3CheckBoxstionButton());
			getMP3CheckBoxstionButton().waitAndClick(15);
			base.waitForElement(getMP3LST());
			getMP3LST().selectFromDropdown().selectByVisibleText(Input.MP3File);
		}

		if (dataset.contains("CJK_GermanAudioTestData")) {
			base.stepInfo("*******Selecing MP3 files***************");
			base.waitForElement(getMP3CheckBoxstionButton());
			getMP3CheckBoxstionButton().waitAndClick(15);
			base.waitForElement(getMP3LST());
			getMP3LST().selectFromDropdown().selectByVisibleText(Input.MP3GermanFile);
		}

		if (dataset.contains("CJK_JapaneseAudioTestData")) {
			base.stepInfo("*******Selecing MP3 files***************");
			base.waitForElement(getMP3CheckBoxstionButton());
			getMP3CheckBoxstionButton().waitAndClick(15);
			base.waitForElement(getMP3LST());
			getMP3LST().selectFromDropdown().selectByVisibleText(Input.MP3JapneseFile);
		}

		if (dataset.contains("AllSources")) {
			base.stepInfo("*******Selecing Audio Transcript files***************");
			base.waitForElement(getAudioTranscriptCheckBoxstionButton());
			getAudioTranscriptCheckBoxstionButton().waitAndClick(15);
			base.waitForElement(getAudioTranscriptLST());
			getAudioTranscriptLST().selectFromDropdown().selectByVisibleText(Input.TranscriptFile);
		}

		if (dataset.contains("CJK_GermanAudioTestData")) {
			base.stepInfo("*******Selecing Audio Transcript files***************");
			base.waitForElement(getAudioTranscriptCheckBoxstionButton());
			getAudioTranscriptCheckBoxstionButton().waitAndClick(15);
			base.waitForElement(getAudioTranscriptLST());
			getAudioTranscriptLST().selectFromDropdown().selectByVisibleText(Input.TranscriptGermanFile);
		}

		if (dataset.contains("CJK_JapaneseAudioTestData")) {
			base.stepInfo("*******Selecing Audio Transcript files***************");
			base.waitForElement(getAudioTranscriptCheckBoxstionButton());
			getAudioTranscriptCheckBoxstionButton().waitAndClick(15);
			base.waitForElement(getAudioTranscriptLST());
			getAudioTranscriptLST().selectFromDropdown().selectByVisibleText(Input.TranscriptJapneseFile);
		}
		driver.scrollingToBottomofAPage();

		if (dataset.contains("AllSources")) {
			base.stepInfo("*******Selecing Other files***************");
			base.waitForElement(getOtherCheckBox());
			getOtherCheckBox().waitAndClick(15);
			base.waitForElement(getOtherLoadFile());
			getOtherLoadFile().selectFromDropdown().selectByVisibleText(Input.TranslationFile);
		}

		base.stepInfo("Select Date Format");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDateFormat().Visible();
			}
		}), Input.wait30);
		getDateFormat().selectFromDropdown().selectByVisibleText(dateFormate);

		driver.scrollPageToTop();

		base.waitForElement(getIngestion_SaveAsDraft());
		getIngestion_SaveAsDraft().waitAndClick(20);
		base.VerifySuccessMessage("Your changes to the ingestion were successfully saved.");
		base.passedStep("Clicked on SaveAsDraft");
	}

	/**
	 * @author: Mohan Created Date: 24/02/2022 Modified by: NA Modified Date: NA
	 * @description: verify Date Formate in Draft section
	 */
	public void verifyDateFormateInCatalogeAndDraft() {

		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByButton().Visible();
			}
		}), Input.wait30);
		getFilterByButton().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByDRAFT().Visible();
			}
		}), Input.wait30);
		getFilterByDRAFT().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByFAILED().Visible();
			}
		}), Input.wait30);
		getFilterByFAILED().waitAndClick(10);

		for (int i = 0; i < 5; i++) {
			if (getDraftIngestionStatus().isElementAvailable(5)) {
				base.passedStep("Draft completed");
				break;
			} else if (getFailedIngestionStatus().isElementAvailable(5)) {
				System.out.println("Execution aborted!");
				UtilityLog.info("Execution aborted!");
				System.out.println("dataset is failed in catalog stage. Take a look and continue!");
				UtilityLog.info("dataset is failed in catalog stage. Take a look and continue!");
				System.exit(1);
			} else {
				base.waitTime(5);
				getRefreshButton().waitAndClick(10);
			}

			String dateFormate1 = getIngestionWizardDateFormate().getText();
			System.out.println(dateFormate1);
			if (dateFormate1.length() > 11) {
				base.passedStep(
						"When Ingestion in draft mode is opened Ingestion Wizard is retain with the selected 'Date & Time Format'");
			} else {
				base.failedStep("Date & time formate is not valid");
			}

		}

	}
	
	/**
	 * @author: Mohan Created Date: 24/02/2022 Modified by: NA Modified Date: NA
	 * @description: Add new ingestion with selecting Date Formate
	 */
	public void IngestionRegressionForDateFormate(String dataset, String dateFormate, String datDateFormate,
			String nativeDateFormate) throws InterruptedException {

		base.stepInfo("Click on add new ingestion button");
		base.waitForElement(getAddanewIngestionButton());
		getAddanewIngestionButton().waitAndClick(10);

		base.stepInfo("Select Source system");
		base.waitForElement(getSpecifySourceSystem());
		getSpecifySourceSystem().selectFromDropdown().selectByVisibleText("TRUE");

		base.stepInfo("Select Location");
		base.waitForElement(getSpecifyLocation());
		Thread.sleep(2000);
		getSpecifyLocation().selectFromDropdown().selectByVisibleText(Input.SourceLocation);

		base.waitForElement(getSpecifySourceFolder());
		Thread.sleep(2000);
		base.stepInfo("Select Folder");
		for (int i = 0; i < 30; i++) {

			if (dataset.contains("Collection1K_Tally")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.Collection1KFolder);
			} else if (dataset.contains("20Family_20Threaded")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.FamilyFolder);
			} else if (dataset.contains("AllSources")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.AllSourcesFolder);
			} else if (dataset.contains("0002_H13696_1_Latest")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.H1369Folder);
			} else if (dataset.contains("16JanMultiPTIFF")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.MultiPTIFFFolder);
			} else if (dataset.contains("27MarSinglePageTIFF")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.SinglePageTIFFFolder);
			} else if (dataset.contains("CJK_FrenchAudioTestData")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.CJK_FrenchAudioTestDataFolder);
			} else if (dataset.contains("QA_EmailConcatenatedData_SS")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.EmailConcatenatedDataFolder);
			} else if (dataset.contains("SSAudioSpeech_Transcript")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.SSAudioSpeechFolder);
			} else if (dataset.contains("GD_994_Native_Text_ForProduction")) {
				getSpecifySourceFolder().selectFromDropdown()
						.selectByVisibleText(Input.GD994NativeTextForProductionFolder);
			} else if (dataset.contains("GNon_searchable_PDF_Load_file")) {
				getSpecifySourceFolder().selectFromDropdown()
						.selectByVisibleText(Input.GNonsearchablePDFLoadfileFolder);
			} else if (dataset.contains("HiddenProperties_IngestionData")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.HiddenPropertiesFolder);
			} else if (dataset.contains("UniCodeFiles")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.UniCodeFilesFolder);
			} else if (dataset.contains("IngestionEmailData")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.IngestionEmailDataFolder);
			} else if (dataset.contains("CJK_GermanAudioTestData") || dataset.contains("CJK_JapaneseAudioTestData")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.CJK_FrenchAudioTestDataFolder);
			}
		}
		Thread.sleep(2000);
		base.waitForElement(getDATDelimitersFieldSeparator());
		getDATDelimitersFieldSeparator().selectFromDropdown().selectByVisibleText("ASCII(20)");

		base.waitForElement(getDATDelimitersTextQualifier());
		getDATDelimitersTextQualifier().selectFromDropdown().selectByVisibleText("ASCII(254)");

		base.waitForElement(getDATDelimitersNewLine());
		getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText("ASCII(174)");

		driver.scrollingToBottomofAPage();

		base.waitForElement(getSourceSelectionDATLoadFile());
		if (dataset.contains("CJK_GermanAudioTestData")) {
			getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(Input.DATGermanFile);
		} else if (dataset.contains("CJK_JapaneseAudioTestData")) {
			getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(Input.DATJapneseFile);
		} else if (dataset.contains("HiddenProperties_IngestionData")) {
			getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(datDateFormate);
		} else {
			getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(Input.DATFile);
		}
		base.waitForElement(getSourceSelectionDATKey());
		Thread.sleep(2000);

		if (dataset.contains("Collection1K_Tally")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
		} else if (dataset.contains("20Family_20Threaded")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
		} else if (dataset.contains("AllSources")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("ProdBeg");
		} else if (dataset.contains("0002_H13696_1_Latest")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DOCID");
		} else if (dataset.contains("16JanMultiPTIFF")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("BNum");
		} else if (dataset.contains("27MarSinglePageTIFF")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("BNum");
		} else if (dataset.contains("QA_EmailConcatenatedData_SS")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("BatesNumber");
		} else if (dataset.contains("SSAudioSpeech_Transcript")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
		} else if (dataset.contains("GD_994_Native_Text_ForProduction")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
		} else if (dataset.contains("GNon_searchable_PDF_Load_file")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("SourceDocID");
		} else if (dataset.contains("HiddenProperties_IngestionData")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("SourceDocID");
		} else if (dataset.contains("UniCodeFiles")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
		} else if (dataset.contains("IngestionEmailData")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocumentID");
		} else if (dataset.contains("CJK_GermanAudioTestData") || dataset.contains("CJK_JapaneseAudioTestData")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
		}

		if (dataset.contains("20Family_20Threaded") || dataset.contains("AllSources")
				|| dataset.contains("16JanMultiPTIFF") || dataset.contains("QA_EmailConcatenatedData_SS")
				|| dataset.contains("GD_994_Native_Text_ForProduction")
				|| dataset.contains("GNon_searchable_PDF_Load_file") || dataset.contains("IngestionEmailData")) {
			base.stepInfo("*******Selecing text files***************");
			base.waitForElement(getSourceSelectionText());
			getSourceSelectionText().waitAndClick(20);
			base.waitForElement(getSourceSelectionTextLoadFile());
			getSourceSelectionTextLoadFile().selectFromDropdown().selectByVisibleText(Input.TextFile);

			base.stepInfo("*******Selecing Native files***************");
			base.waitForElement(getNativeCheckBox());
			getNativeCheckBox().waitAndClick(10);
			base.waitForElement(getNativeLST());
			getNativeLST().selectFromDropdown().selectByVisibleText(Input.NativeFile);
		}

		if (dataset.contains("Collection1K_Tally") || dataset.contains("UniCodeFiles")) {
			base.stepInfo("*******Selecing text files***************");
			base.waitForElement(getSourceSelectionText());
			getSourceSelectionText().waitAndClick(20);
			base.waitForElement(getSourceSelectionTextLoadFile());
			getSourceSelectionTextLoadFile().selectFromDropdown().selectByVisibleText(Input.TextFile);
		}

		if (dataset.contains("0002_H13696_1_Latest")) {
			base.stepInfo("*******Selecing Native files***************");
			base.waitForElement(getNativeCheckBox());
			getNativeCheckBox().waitAndClick(10);
			base.waitForElement(getNativeLST());
			getNativeLST().selectFromDropdown().selectByVisibleText(Input.NativeFile);
		}
		if (dataset.contains("HiddenProperties_IngestionData")) {
			base.stepInfo("*******Selecing Native files***************");
			base.waitForElement(getNativeCheckBox());
			getNativeCheckBox().waitAndClick(10);
			base.waitForElement(getNativeLST());
			getNativeLST().selectFromDropdown().selectByVisibleText(nativeDateFormate);
		}
		if (dataset.contains("AllSources")) {
			base.stepInfo("*******Selecing PDF files***************");
			base.waitForElement(getPDFCheckBoxstionButton());
			getPDFCheckBoxstionButton().waitAndClick(20);
			base.waitForElement(getPDFLST());
			getPDFLST().selectFromDropdown().selectByVisibleText(Input.PDFFile);
		}

		driver.scrollingToBottomofAPage();

		if (dataset.contains("AllSources") || dataset.contains("16JanMultiPTIFF")) {
			base.stepInfo("*******Selecing TIFF files***************");
			base.waitForElement(getTIFFCheckBox());
			getTIFFCheckBox().waitAndClick(20);
			base.waitForElement(getSourceSelectionTextLoadFile());
			getTIFFLST().selectFromDropdown().selectByVisibleText(Input.TIFFFile);
		}

		/*
		 * if( dataset.contains("27MarSinglePageTIFF")) {
		 * base.stepInfo("*******Selecing TIFF files***************");
		 * base.waitForElement(getTIFFCheckBox()); getTIFFCheckBox().waitAndClick(20);
		 * base.waitForElement(getSourceSelectionTextLoadFile());
		 * getTIFFLST().selectFromDropdown().selectByVisibleText(Input.TIFFFile1); }
		 */
		driver.scrollingToBottomofAPage();

		if (dataset.contains("0002_H13696_1_Latest") || dataset.contains("SSAudioSpeech_Transcript")
				|| dataset.contains("AllSources")) {
			base.stepInfo("*******Selecing MP3 files***************");
			base.waitForElement(getMP3CheckBoxstionButton());
			getMP3CheckBoxstionButton().waitAndClick(15);
			base.waitForElement(getMP3LST());
			getMP3LST().selectFromDropdown().selectByVisibleText(Input.MP3File);
		}

		if (dataset.contains("CJK_GermanAudioTestData")) {
			base.stepInfo("*******Selecing MP3 files***************");
			base.waitForElement(getMP3CheckBoxstionButton());
			getMP3CheckBoxstionButton().waitAndClick(15);
			base.waitForElement(getMP3LST());
			getMP3LST().selectFromDropdown().selectByVisibleText(Input.MP3GermanFile);
		}

		if (dataset.contains("CJK_JapaneseAudioTestData")) {
			base.stepInfo("*******Selecing MP3 files***************");
			base.waitForElement(getMP3CheckBoxstionButton());
			getMP3CheckBoxstionButton().waitAndClick(15);
			base.waitForElement(getMP3LST());
			getMP3LST().selectFromDropdown().selectByVisibleText(Input.MP3JapneseFile);
		}

		if (dataset.contains("AllSources")) {
			base.stepInfo("*******Selecing Audio Transcript files***************");
			base.waitForElement(getAudioTranscriptCheckBoxstionButton());
			getAudioTranscriptCheckBoxstionButton().waitAndClick(15);
			base.waitForElement(getAudioTranscriptLST());
			getAudioTranscriptLST().selectFromDropdown().selectByVisibleText(Input.TranscriptFile);
		}

		if (dataset.contains("CJK_GermanAudioTestData")) {
			base.stepInfo("*******Selecing Audio Transcript files***************");
			base.waitForElement(getAudioTranscriptCheckBoxstionButton());
			getAudioTranscriptCheckBoxstionButton().waitAndClick(15);
			base.waitForElement(getAudioTranscriptLST());
			getAudioTranscriptLST().selectFromDropdown().selectByVisibleText(Input.TranscriptGermanFile);
		}

		if (dataset.contains("CJK_JapaneseAudioTestData")) {
			base.stepInfo("*******Selecing Audio Transcript files***************");
			base.waitForElement(getAudioTranscriptCheckBoxstionButton());
			getAudioTranscriptCheckBoxstionButton().waitAndClick(15);
			base.waitForElement(getAudioTranscriptLST());
			getAudioTranscriptLST().selectFromDropdown().selectByVisibleText(Input.TranscriptJapneseFile);
		}
		driver.scrollingToBottomofAPage();

		if (dataset.contains("AllSources")) {
			base.stepInfo("*******Selecing Other files***************");
			base.waitForElement(getOtherCheckBox());
			getOtherCheckBox().waitAndClick(15);
			base.waitForElement(getOtherLoadFile());
			getOtherLoadFile().selectFromDropdown().selectByVisibleText(Input.TranslationFile);
		}

		base.stepInfo("Select Date Format");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDateFormat().Visible();
			}
		}), Input.wait30);
		getDateFormat().selectFromDropdown().selectByVisibleText(dateFormate);

		driver.scrollPageToTop();

		base.waitForElement(getNextButton());
		getNextButton().waitAndClick(20);
		base.passedStep("Clicked on Next button");

		base.stepInfo("Pop up messgae for Ingestion without text file");
		if (getApproveMessageOKButton().isElementAvailable(5)) {
			getApproveMessageOKButton().waitAndClick(10);
			base.passedStep("Clicked on OK button to continue without text files");
		}

		base.waitForElement(getMappingSOURCEFIELD2());
		if (dataset.contains("Collection1K_Tally")) {
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DocID");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("Datasource");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");

			base.waitForElement(getMappingFIELDCAT25());
			getMappingFIELDCAT25().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD25().selectFromDropdown().selectByVisibleText("DocFileExtension");

			base.waitForElement(getMappingFIELDCAT26());
			getMappingFIELDCAT26().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD26().selectFromDropdown().selectByVisibleText("DocFileName");

			base.waitForElement(getMappingFIELDCAT27());
			getMappingFIELDCAT27().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD27().selectFromDropdown().selectByVisibleText("DocFileSize");

			base.waitForElement(getMappingFIELDCAT28());
			getMappingFIELDCAT28().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD28().selectFromDropdown().selectByVisibleText("DocFileType");
		}

		else if (dataset.contains("20Family_20Threaded")) {
			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("ProdEnd");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("Datasource");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");

			base.waitForElement(getMappingFIELDCAT9());
			getMappingFIELDCAT9().selectFromDropdown().selectByVisibleText("EMAIL");
			getMappingDESTINATIONFIELD9().selectFromDropdown().selectByVisibleText("EmailAuthorNameAndAddress");

			base.waitForElement(getMappingFIELDCAT10());
			getMappingFIELDCAT10().selectFromDropdown().selectByVisibleText("EMAIL");
			getMappingDESTINATIONFIELD10().selectFromDropdown().selectByVisibleText("EmailBCCNamesAndAddresses");

			base.waitForElement(getMappingFIELDCAT13());
			getMappingFIELDCAT13().selectFromDropdown().selectByVisibleText("EMAIL");
			getMappingDESTINATIONFIELD13().selectFromDropdown().selectByVisibleText("EmailCCNamesAndAddresses");

			base.waitForElement(getMappingFIELDCAT25());
			getMappingFIELDCAT25().selectFromDropdown().selectByVisibleText("FAMILY");
			getMappingDESTINATIONFIELD25().selectFromDropdown().selectByVisibleText("FamilyRelationship");

			base.waitForElement(getMappingFIELDCAT26());
			getMappingFIELDCAT26().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD26().selectFromDropdown().selectByVisibleText("DocFileExtension");

			base.waitForElement(getMappingFIELDCAT27());
			getMappingFIELDCAT27().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD27().selectFromDropdown().selectByVisibleText("DocFileName");

			base.waitForElement(getMappingFIELDCAT28());
			getMappingFIELDCAT28().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD28().selectFromDropdown().selectByVisibleText("DocFileSize");

			base.waitForElement(getMappingFIELDCAT29());
			getMappingFIELDCAT29().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD29().selectFromDropdown().selectByVisibleText("DocFileType");

			base.waitForElement(getMappingFIELDCAT31());
			getMappingFIELDCAT31().selectFromDropdown().selectByVisibleText("FAMILY");
			getMappingDESTINATIONFIELD31().selectFromDropdown().selectByVisibleText("FamilyID");

			base.waitForElement(getMappingFIELDCAT49());
			getMappingFIELDCAT49().selectFromDropdown().selectByVisibleText("EMAIL");
			getMappingDESTINATIONFIELD49().selectFromDropdown().selectByVisibleText("EmailReceivedDate");

			base.waitForElement(getMappingFIELDCAT51());
			getMappingFIELDCAT51().selectFromDropdown().selectByVisibleText("EMAIL");
			getMappingDESTINATIONFIELD51().selectFromDropdown().selectByVisibleText("EmailToNamesAndAddresses");
		}

		else if (dataset.contains("AllSources")) {

			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("ProdBeg");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("ProdBeg");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");

			base.waitForElement(getMappingFIELDCAT25());
			getMappingFIELDCAT25().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD25().selectFromDropdown().selectByVisibleText("DocFileExtension");

			base.waitForElement(getMappingFIELDCAT26());
			getMappingFIELDCAT26().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD26().selectFromDropdown().selectByVisibleText("DocFileName");

			base.waitForElement(getMappingFIELDCAT27());
			getMappingFIELDCAT27().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD27().selectFromDropdown().selectByVisibleText("DocFileSize");

			base.waitForElement(getMappingFIELDCAT28());
			getMappingFIELDCAT28().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD28().selectFromDropdown().selectByVisibleText("DocFileType");
		}

		else if (dataset.contains("0002_H13696_1_Latest")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DOCID");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("DOCID");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("DOCID");
		} else if (dataset.contains("16JanMultiPTIFF")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("BNum");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("BNum");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("CName");
		}

		else if (dataset.contains("27MarSinglePageTIFF")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("BNum");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("BNum");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("CName");
			getMappingSOURCEFIELD5().selectFromDropdown().selectByVisibleText("RequirePDFGeneration");
			getMappingFIELDCAT5().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD5().selectFromDropdown().selectByVisibleText("RequirePDFGeneration");
		}

		else if (dataset.contains("QA_EmailConcatenatedData_SS")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("BatesNumber");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("DataSource");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("CustodianName");

		}

		else if (dataset.contains("SSAudioSpeech_Transcript")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DocID");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("Datasource");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");

			base.waitForElement(getMappingFIELDCAT25());
			getMappingFIELDCAT25().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD25().selectFromDropdown().selectByVisibleText("DocFileExtension");

			base.waitForElement(getMappingFIELDCAT26());
			getMappingFIELDCAT26().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD26().selectFromDropdown().selectByVisibleText("DocFileName");

			base.waitForElement(getMappingFIELDCAT27());
			getMappingFIELDCAT27().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD27().selectFromDropdown().selectByVisibleText("DocFileSize");

			base.waitForElement(getMappingFIELDCAT28());
			getMappingFIELDCAT28().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD28().selectFromDropdown().selectByVisibleText("DocFileType");

		}

		else if (dataset.contains("GD_994_Native_Text_ForProduction")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DocID");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("DocID");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("DocID");

			base.waitForElement(getMappingFIELDCAT5());
			getMappingSOURCEFIELD5().selectFromDropdown().selectByVisibleText("EmailAuthorNameAndAddress");
			getMappingFIELDCAT5().selectFromDropdown().selectByVisibleText("EMAIL");
			getMappingDESTINATIONFIELD5().selectFromDropdown().selectByVisibleText("EmailAuthorNameAndAddress");

			getAddButton().waitAndClick(15);
			Thread.sleep(2000);

			base.waitForElement(getMappingFIELDCAT6());
			getMappingSOURCEFIELD6().selectFromDropdown().selectByVisibleText("EmailBCCNameAndBCCAddress");
			getMappingFIELDCAT6().selectFromDropdown().selectByVisibleText("EMAIL");
			getMappingDESTINATIONFIELD6().selectFromDropdown().selectByVisibleText("EmailBCCNamesAndAddresses");

			getAddButton().waitAndClick(15);
			Thread.sleep(2000);

			base.waitForElement(getMappingFIELDCAT7());
			getMappingSOURCEFIELD7().selectFromDropdown().selectByVisibleText("EmailCCNamAndCCAddress");
			getMappingFIELDCAT7().selectFromDropdown().selectByVisibleText("EMAIL");
			getMappingDESTINATIONFIELD7().selectFromDropdown().selectByVisibleText("EmailCCNamesAndAddresses");

			getAddButton().waitAndClick(15);
			Thread.sleep(2000);

			base.waitForElement(getMappingFIELDCAT8());
			getMappingSOURCEFIELD8().selectFromDropdown().selectByVisibleText("EmailToNameAndAddress");
			getMappingFIELDCAT8().selectFromDropdown().selectByVisibleText("EMAIL");
			getMappingDESTINATIONFIELD8().selectFromDropdown().selectByVisibleText("EmailToNamesAndAddresses");
		}

		else if (dataset.contains("GNon searchable PDF Load file")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("SourceDocID");
		}

		else if (dataset.contains("HiddenProperties_IngestionData")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("SourceDocID");
		}

		else if (dataset.contains("UniCodeFiles")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DocID");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("DocID");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");
		}

		else if (dataset.contains("IngestionEmailData")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DocumentID");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("DocumentID");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("CustID");

			base.waitForElement(getMappingFIELDCAT76());
			getMappingFIELDCAT76().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD76().selectFromDropdown().selectByVisibleText("DocFileName");

			base.waitForElement(getMappingFIELDCAT73());
			getMappingFIELDCAT73().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD73().selectFromDropdown().selectByVisibleText("DocFileSize");

			base.waitForElement(getMappingFIELDCAT75());
			getMappingFIELDCAT75().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD75().selectFromDropdown().selectByVisibleText("DocFileType");
		} else if (dataset.contains("CJK_GermanAudioTestData")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DocID");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("Datasource");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");

			base.waitForElement(getMappingFIELDCAT25());
			getMappingFIELDCAT25().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD25().selectFromDropdown().selectByVisibleText("DocFileExtension");

			base.waitForElement(getMappingFIELDCAT26());
			getMappingFIELDCAT26().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD26().selectFromDropdown().selectByVisibleText("DocFileName");

			base.waitForElement(getMappingFIELDCAT27());
			getMappingFIELDCAT27().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD27().selectFromDropdown().selectByVisibleText("DocFileSize");

			base.waitForElement(getMappingFIELDCAT28());
			getMappingFIELDCAT28().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD28().selectFromDropdown().selectByVisibleText("DocFileType");

		} else if (dataset.contains("CJK_JapaneseAudioTestData")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DocID");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("Datasource");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");

			base.waitForElement(getMappingFIELDCAT25());
			getMappingFIELDCAT25().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD25().selectFromDropdown().selectByVisibleText("DocFileExtension");

			base.waitForElement(getMappingFIELDCAT26());
			getMappingFIELDCAT26().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD26().selectFromDropdown().selectByVisibleText("DocFileName");

			base.waitForElement(getMappingFIELDCAT27());
			getMappingFIELDCAT27().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD27().selectFromDropdown().selectByVisibleText("DocFileSize");

			base.waitForElement(getMappingFIELDCAT28());
			getMappingFIELDCAT28().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD28().selectFromDropdown().selectByVisibleText("DocFileType");
		}

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPreviewRun().Visible();
			}
		}), Input.wait30);
		getPreviewRun().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getApproveMessageOKButton().Visible();
			}
		}), Input.wait30);
		getApproveMessageOKButton().waitAndClick(10);

		base.stepInfo("'Preview Documents' pop up is opened successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnRunIngestion().Visible();
			}
		}), Input.wait30);
		getbtnRunIngestion().waitAndClick(10);

		softAssertion = new SoftAssert();
		softAssertion.assertTrue(getAddanewIngestionButton().isElementAvailable(5));
		base.passedStep("Run Ingestion done successfully");

	}
	
	/**
	 * @author: Mohan Created Date: 24/02/2022 Modified by: NA Modified Date: NA
	 * @description: To roll back an Ingestion
	 */
	public void rollBackIngestion() {
		
		base.waitTime(5);
		getRefreshButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(getIngestionSettingGearIcon());
		getIngestionSettingGearIcon().waitAndClick(10);
		
		base.waitForElement(getIngestionRollbackbutton());
		getIngestionRollbackbutton().waitAndClick(5);
		base.waitTime(2);
		if(getApproveMessageOKButton().isElementAvailable(5)) {
		getApproveMessageOKButton().waitAndClick(5);
		}
		
		base.VerifySuccessMessage("Rollback of this ingestion has been started. Refresh the page to view for updated status.");
		}
	
	
	/**
	 * @author: Mohan Created Date: 24/02/2022 Modified by: NA Modified Date: NA
	 * @description: Verify ingestion at catalog status
	 */
	public void ingestionAtCatlogState(String dataset) {
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFilterByButton().Visible()  ;}}), Input.wait30); 
    	getFilterByButton().waitAndClick(10);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFilterByFAILED().Visible()  ;}}), Input.wait30); 
    	getFilterByFAILED().waitAndClick(10);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFilterByCATALOGED().Visible()  ;}}), Input.wait30); 
    	getFilterByCATALOGED().waitAndClick(10);
    	
    	//catlogging
    	for (int i = 0; i < 5; i++) {
			if (getCatalogedIngestionStatus().isElementAvailable(5)) {
				base.passedStep("Cataloged completed");
				break;
			}else if (getFailedIngestionStatus().isElementAvailable(5)) {
				System.out.println("Execution aborted!");
				UtilityLog.info("Execution aborted!");
				System.out.println(dataset+" is failed in catalog stage. Take a look and continue!");
				UtilityLog.info(dataset+" is failed in catalog stage. Take a look and continue!");
				break;
			}else{
				base.waitTime(5);
				getRefreshButton().waitAndClick(10);
			}
		}
    }
	
	
	/**
	 * @author: Arunkumar Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: this method will perform ingestion with Dat file
	 */
	public void IngestionOnlyForDatFile(String dataset,String DATFile) throws InterruptedException {
		base.stepInfo("Click on add new ingestion button");
		base.waitForElement(getAddanewIngestionButton());
		getAddanewIngestionButton().waitAndClick(10);

		base.stepInfo("Select Source system");
		base.waitForElement(getSpecifySourceSystem());
		getSpecifySourceSystem().selectFromDropdown().selectByVisibleText("TRUE");

		base.stepInfo("Select Location");
		base.waitForElement(getSpecifyLocation());
		Thread.sleep(2000);
		getSpecifyLocation().selectFromDropdown().selectByVisibleText(Input.SourceLocation);

		base.waitForElement(getSpecifySourceFolder());
		Thread.sleep(2000);
		base.stepInfo("Select Folder");
		if (dataset.contains("Tiff_Images")) {
			getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.TiffImagesFolder);
		} else if (dataset.contains("Collection1K_Tally")) {
			getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.Collection1KFolder);
		} else if (dataset.contains("20Family_20Threaded")) {
			getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.FamilyFolder);
		} else if (dataset.contains("AllSources")) {
			getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.AllSourcesFolder);
		} else if (dataset.contains("0002_H13696_1_Latest")) {
			getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.H1369Folder);
		} else if (dataset.contains("16JanMultiPTIFF")) {
			getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.MultiPTIFFFolder);
		} else if (dataset.contains("27MarSinglePageTIFF")) {
			getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.SinglePageTIFFFolder);
		} else if (dataset.contains("CJK_FrenchAudioTestData")) {
			getSpecifySourceFolder().selectFromDropdown()
					.selectByVisibleText(Input.CJK_FrenchAudioTestDataFolder);
		} else if (dataset.contains("QA_EmailConcatenatedData_SS")) {
			getSpecifySourceFolder().selectFromDropdown()
					.selectByVisibleText(Input.EmailConcatenatedDataFolder);
		} else if (dataset.contains("SSAudioSpeech_Transcript")) {
			getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.SSAudioSpeechFolder);
		} else if (dataset.contains("GD_994_Native_Text_ForProduction")) {
			getSpecifySourceFolder().selectFromDropdown()
					.selectByVisibleText(Input.GD994NativeTextForProductionFolder);
		} else if (dataset.contains("GNon_searchable_PDF_Load_file")) {
			getSpecifySourceFolder().selectFromDropdown()
					.selectByVisibleText(Input.GNonsearchablePDFLoadfileFolder);
		} else if (dataset.contains("HiddenProperties_IngestionData")) {
			getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.HiddenPropertiesFolder);
		} else if (dataset.contains("UniCodeFiles")) {
			getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.UniCodeFilesFolder);
		} else if (dataset.contains("IngestionEmailData")) {
			getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.IngestionEmailDataFolder);
		} else if (dataset.contains("CJK_GermanAudioTestData")
				|| dataset.contains("CJK_JapaneseAudioTestData")) {
			getSpecifySourceFolder().selectFromDropdown()
					.selectByVisibleText(Input.CJK_FrenchAudioTestDataFolder);
		} 
		Thread.sleep(2000);
		base.waitForElement(getDATDelimitersFieldSeparator());
		getDATDelimitersFieldSeparator().selectFromDropdown().selectByVisibleText("ASCII(20)");

		base.waitForElement(getDATDelimitersTextQualifier());
		getDATDelimitersTextQualifier().selectFromDropdown().selectByVisibleText("ASCII(254)");

		base.waitForElement(getDATDelimitersNewLine());
		getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText("ASCII(174)");

		driver.scrollingToBottomofAPage();

		base.waitForElement(getSourceSelectionDATLoadFile());
		getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(DATFile);
		base.waitForElement(getSourceSelectionDATKey());
		Thread.sleep(2000);
		if (dataset.contains("Collection1K_Tally")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
		} else if (dataset.contains("20Family_20Threaded")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
		} else if (dataset.contains("AllSources")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("ProdBeg");
		} else if (dataset.contains("0002_H13696_1_Latest")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DOCID");
		} else if (dataset.contains("16JanMultiPTIFF")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("BNum");
		} else if (dataset.contains("27MarSinglePageTIFF")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("BNum");
		} else if (dataset.contains("QA_EmailConcatenatedData_SS")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("BatesNumber");
		} else if (dataset.contains("SSAudioSpeech_Transcript")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
		} else if (dataset.contains("GD_994_Native_Text_ForProduction")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
		} else if (dataset.contains("GNon_searchable_PDF_Load_file")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("SourceDocID");
		} else if (dataset.contains("HiddenProperties_IngestionData")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("SourceDocID");
		} else if (dataset.contains("UniCodeFiles")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
		} else if (dataset.contains("IngestionEmailData")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocumentID");
		} else if (dataset.contains("CJK_GermanAudioTestData") || dataset.contains("CJK_JapaneseAudioTestData")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
		} else if (dataset.contains("Tiff_Images")) {
			getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
		}

		driver.scrollingToBottomofAPage();

		base.stepInfo("Select Date Format");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDateFormat().Visible();
			}
		}), Input.wait30);
		getDateFormat().selectFromDropdown().selectByVisibleText("YYYY/MM/DD HH:MM:SS");

		driver.scrollPageToTop();

		base.waitForElement(getNextButton());
		getNextButton().waitAndClick(20);
		base.passedStep("Clicked on Next button");

		base.stepInfo("Pop up messgae for Ingestion without text file");
		if (getApproveMessageOKButton().isElementAvailable(5)) {
			getApproveMessageOKButton().waitAndClick(10);
			base.passedStep("Clicked on OK button to continue without text files");
		}

		base.waitForElement(getMappingSOURCEFIELD2());
		if (dataset.contains("Collection1K_Tally")) {
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DocID");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("Datasource");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");

			base.waitForElement(getMappingFIELDCAT25());
			getMappingFIELDCAT25().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD25().selectFromDropdown().selectByVisibleText("DocFileExtension");

			base.waitForElement(getMappingFIELDCAT26());
			getMappingFIELDCAT26().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD26().selectFromDropdown().selectByVisibleText("DocFileName");

			base.waitForElement(getMappingFIELDCAT27());
			getMappingFIELDCAT27().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD27().selectFromDropdown().selectByVisibleText("DocFileSize");

			base.waitForElement(getMappingFIELDCAT28());
			getMappingFIELDCAT28().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD28().selectFromDropdown().selectByVisibleText("DocFileType");
		}

		else if (dataset.contains("20Family_20Threaded")) {
			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("ProdEnd");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("Datasource");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");

			base.waitForElement(getMappingFIELDCAT9());
			getMappingFIELDCAT9().selectFromDropdown().selectByVisibleText("EMAIL");
			getMappingDESTINATIONFIELD9().selectFromDropdown().selectByVisibleText("EmailAuthorNameAndAddress");

			base.waitForElement(getMappingFIELDCAT10());
			getMappingFIELDCAT10().selectFromDropdown().selectByVisibleText("EMAIL");
			getMappingDESTINATIONFIELD10().selectFromDropdown().selectByVisibleText("EmailBCCNamesAndAddresses");

			base.waitForElement(getMappingFIELDCAT13());
			getMappingFIELDCAT13().selectFromDropdown().selectByVisibleText("EMAIL");
			getMappingDESTINATIONFIELD13().selectFromDropdown().selectByVisibleText("EmailCCNamesAndAddresses");

			base.waitForElement(getMappingFIELDCAT25());
			getMappingFIELDCAT25().selectFromDropdown().selectByVisibleText("FAMILY");
			getMappingDESTINATIONFIELD25().selectFromDropdown().selectByVisibleText("FamilyRelationship");

			base.waitForElement(getMappingFIELDCAT26());
			getMappingFIELDCAT26().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD26().selectFromDropdown().selectByVisibleText("DocFileExtension");

			base.waitForElement(getMappingFIELDCAT27());
			getMappingFIELDCAT27().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD27().selectFromDropdown().selectByVisibleText("DocFileName");

			base.waitForElement(getMappingFIELDCAT28());
			getMappingFIELDCAT28().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD28().selectFromDropdown().selectByVisibleText("DocFileSize");

			base.waitForElement(getMappingFIELDCAT29());
			getMappingFIELDCAT29().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD29().selectFromDropdown().selectByVisibleText("DocFileType");

			base.waitForElement(getMappingFIELDCAT31());
			getMappingFIELDCAT31().selectFromDropdown().selectByVisibleText("FAMILY");
			getMappingDESTINATIONFIELD31().selectFromDropdown().selectByVisibleText("FamilyID");

			base.waitForElement(getMappingFIELDCAT49());
			getMappingFIELDCAT49().selectFromDropdown().selectByVisibleText("EMAIL");
			getMappingDESTINATIONFIELD49().selectFromDropdown().selectByVisibleText("EmailReceivedDate");

			base.waitForElement(getMappingFIELDCAT51());
			getMappingFIELDCAT51().selectFromDropdown().selectByVisibleText("EMAIL");
			getMappingDESTINATIONFIELD51().selectFromDropdown().selectByVisibleText("EmailToNamesAndAddresses");
		}

		else if (dataset.contains("AllSources")) {

			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("ProdBeg");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("ProdBeg");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");

			base.waitForElement(getMappingFIELDCAT25());
			getMappingFIELDCAT25().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD25().selectFromDropdown().selectByVisibleText("DocFileExtension");

			base.waitForElement(getMappingFIELDCAT26());
			getMappingFIELDCAT26().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD26().selectFromDropdown().selectByVisibleText("DocFileName");

			base.waitForElement(getMappingFIELDCAT27());
			getMappingFIELDCAT27().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD27().selectFromDropdown().selectByVisibleText("DocFileSize");

			base.waitForElement(getMappingFIELDCAT28());
			getMappingFIELDCAT28().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD28().selectFromDropdown().selectByVisibleText("DocFileType");
		}

		else if (dataset.contains("0002_H13696_1_Latest")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DOCID");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("DOCID");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("DOCID");
		} else if (dataset.contains("16JanMultiPTIFF")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("BNum");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("BNum");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("CName");
		}

		else if (dataset.contains("27MarSinglePageTIFF")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("BNum");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("BNum");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("CName");
			getMappingSOURCEFIELD5().selectFromDropdown().selectByVisibleText("RequirePDFGeneration");
			getMappingFIELDCAT5().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD5().selectFromDropdown().selectByVisibleText("RequirePDFGeneration");
		}

		else if (dataset.contains("QA_EmailConcatenatedData_SS")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("BatesNumber");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("DataSource");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("CustodianName");

		}

		else if (dataset.contains("SSAudioSpeech_Transcript")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DocID");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("Datasource");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");

			base.waitForElement(getMappingFIELDCAT25());
			getMappingFIELDCAT25().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD25().selectFromDropdown().selectByVisibleText("DocFileExtension");

			base.waitForElement(getMappingFIELDCAT26());
			getMappingFIELDCAT26().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD26().selectFromDropdown().selectByVisibleText("DocFileName");

			base.waitForElement(getMappingFIELDCAT27());
			getMappingFIELDCAT27().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD27().selectFromDropdown().selectByVisibleText("DocFileSize");

			base.waitForElement(getMappingFIELDCAT28());
			getMappingFIELDCAT28().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD28().selectFromDropdown().selectByVisibleText("DocFileType");

		}

		else if (dataset.contains("GD_994_Native_Text_ForProduction")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DocID");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("DocID");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("DocID");

			base.waitForElement(getMappingFIELDCAT5());
			getMappingSOURCEFIELD5().selectFromDropdown().selectByVisibleText("EmailAuthorNameAndAddress");
			getMappingFIELDCAT5().selectFromDropdown().selectByVisibleText("EMAIL");
			getMappingDESTINATIONFIELD5().selectFromDropdown().selectByVisibleText("EmailAuthorNameAndAddress");

			getAddButton().waitAndClick(15);
			Thread.sleep(2000);

			base.waitForElement(getMappingFIELDCAT6());
			getMappingSOURCEFIELD6().selectFromDropdown().selectByVisibleText("EmailBCCNameAndBCCAddress");
			getMappingFIELDCAT6().selectFromDropdown().selectByVisibleText("EMAIL");
			getMappingDESTINATIONFIELD6().selectFromDropdown().selectByVisibleText("EmailBCCNamesAndAddresses");

			getAddButton().waitAndClick(15);
			Thread.sleep(2000);

			base.waitForElement(getMappingFIELDCAT7());
			getMappingSOURCEFIELD7().selectFromDropdown().selectByVisibleText("EmailCCNamAndCCAddress");
			getMappingFIELDCAT7().selectFromDropdown().selectByVisibleText("EMAIL");
			getMappingDESTINATIONFIELD7().selectFromDropdown().selectByVisibleText("EmailCCNamesAndAddresses");

			getAddButton().waitAndClick(15);
			Thread.sleep(2000);

			base.waitForElement(getMappingFIELDCAT8());
			getMappingSOURCEFIELD8().selectFromDropdown().selectByVisibleText("EmailToNameAndAddress");
			getMappingFIELDCAT8().selectFromDropdown().selectByVisibleText("EMAIL");
			getMappingDESTINATIONFIELD8().selectFromDropdown().selectByVisibleText("EmailToNamesAndAddresses");
		}

		else if (dataset.contains("GNon searchable PDF Load file")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("SourceDocID");
		}

		else if (dataset.contains("HiddenProperties_IngestionData")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("SourceDocID");
		}

		else if (dataset.contains("UniCodeFiles")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DocID");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("DocID");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");
		}

		else if (dataset.contains("IngestionEmailData")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DocumentID");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("DocumentID");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("CustID");

			base.waitForElement(getMappingFIELDCAT76());
			getMappingFIELDCAT76().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD76().selectFromDropdown().selectByVisibleText("DocFileName");

			base.waitForElement(getMappingFIELDCAT73());
			getMappingFIELDCAT73().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD73().selectFromDropdown().selectByVisibleText("DocFileSize");

			base.waitForElement(getMappingFIELDCAT75());
			getMappingFIELDCAT75().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD75().selectFromDropdown().selectByVisibleText("DocFileType");
		} else if (dataset.contains("CJK_GermanAudioTestData")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DocID");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("Datasource");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");

			base.waitForElement(getMappingFIELDCAT25());
			getMappingFIELDCAT25().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD25().selectFromDropdown().selectByVisibleText("DocFileExtension");

			base.waitForElement(getMappingFIELDCAT26());
			getMappingFIELDCAT26().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD26().selectFromDropdown().selectByVisibleText("DocFileName");

			base.waitForElement(getMappingFIELDCAT27());
			getMappingFIELDCAT27().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD27().selectFromDropdown().selectByVisibleText("DocFileSize");

			base.waitForElement(getMappingFIELDCAT28());
			getMappingFIELDCAT28().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD28().selectFromDropdown().selectByVisibleText("DocFileType");

		} else if (dataset.contains("CJK_JapaneseAudioTestData")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DocID");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("Datasource");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");

			base.waitForElement(getMappingFIELDCAT25());
			getMappingFIELDCAT25().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD25().selectFromDropdown().selectByVisibleText("DocFileExtension");

			base.waitForElement(getMappingFIELDCAT26());
			getMappingFIELDCAT26().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD26().selectFromDropdown().selectByVisibleText("DocFileName");

			base.waitForElement(getMappingFIELDCAT27());
			getMappingFIELDCAT27().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD27().selectFromDropdown().selectByVisibleText("DocFileSize");

			base.waitForElement(getMappingFIELDCAT28());
			getMappingFIELDCAT28().selectFromDropdown().selectByVisibleText("DOCBASIC");
			getMappingDESTINATIONFIELD28().selectFromDropdown().selectByVisibleText("DocFileType");

		}else if (dataset.contains("Tiff_Images")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DocID");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("Datasource");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");
		}
		
		driver.scrollPageToTop();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getPreviewRun().Visible()  ;}}), Input.wait30); 
    	getPreviewRun().waitAndClick(10);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getApproveMessageOKButton().Visible()  ;}}), Input.wait30); 
    	getApproveMessageOKButton().waitAndClick(10);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getbtnRunIngestion().Visible()  ;}}), Input.wait30); 
    	getbtnRunIngestion().waitAndClick(10);

	}
	
	/**
	 * @author: Arunkumar Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: this method will validate the term which present in the copying table column
	 */
	public void verifyDataPresentInCopyColumn(String term) {
		getRefreshButton().waitAndClick(10);	
        getIngestionName().waitAndClick(Input.wait30);
       
    	driver.scrollingToElementofAPage(getRunIndexing());
    	base.waitForElement(getRunIndexing());
    	if(copyTableDataName(term).isDisplayed()) {
    		base.passedStep(term+ " is displayed in the copying table column");
    	}
    	else {
    		base.failedStep(term+"is not displayed in the copying table column");
    	}
    	
		
	}

/**
	 * @author: Arunkumar Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: this method will perform the ingestion process of catalog ad copying
	 */
public void IngestionCatlogtoCopying(String dataset) throws InterruptedException {
    	
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFilterByButton().Visible()  ;}}), Input.wait30); 
    	getFilterByButton().waitAndClick(10);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFilterByFAILED().Visible()  ;}}), Input.wait30); 
    	getFilterByFAILED().waitAndClick(10);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFilterByCATALOGED().Visible()  ;}}), Input.wait30); 
    	getFilterByCATALOGED().waitAndClick(10);
    	
    	//catlogging
    	for(int i=0;i<10;i++) {
    		if(getCatalogedIngestionStatus().isElementAvailable(5)) {
    			base.passedStep("Cataloged completed");
    			break;
    		}
    		else if (getFailedIngestionStatus().isElementAvailable(5)) {
    			base.failedStep("Ingestion failed");
    		}
    		else{
    			base.waitTime(40);
    			getRefreshButton().waitAndClick(10);
    		}
    	}
	
    	
    	//copy
    	getRefreshButton().waitAndClick(10);
    	    		
        getIngestionName().waitAndClick(Input.wait30);
    
       
    	driver.scrollingToElementofAPage(getRunCopying());
    	base.waitForElement(getRunCopying());
        getRunCopying().waitAndClick(10);
        
        base.VerifySuccessMessage("Ingestion copy has Started.");
        UtilityLog.info(dataset+"'s copying is started.");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getCloseButton().Enabled()  ;}}), Input.wait30); 
    	getCloseButton().waitAndClick(10);	
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFilterByButton().Visible()  ;}}), Input.wait30); 
    	getFilterByButton().waitAndClick(10);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFilterByCOPIED().Visible()  ;}}), Input.wait30); 
    	getFilterByCOPIED().waitAndClick(10);
    	
    	for(int i=0;i<10;i++) {
    		if(getCopiedIngestionStatus().isElementAvailable(5)) {
    			base.passedStep("Copied completed");
    			break;
    		}
    		else if (getFailedIngestionStatus().isElementAvailable(5)) {
    			base.failedStep("Ingestion failed");
    		}
    		else{
    			base.waitTime(40);
    			getRefreshButton().waitAndClick(10);
    		}
    	}
    	
		
	}

	/**
	 * @author: Mohan Created Date: 25/02/2022 Modified by: NA Modified Date: NA
	 * @description: To verify cataloging error with selected DateFormate
	 */
	public void verifyCatalogigErrorForDatSelectDateFormate() {

		driver.waitForPageToBeReady();

		getIngestionName().waitAndClick(10);
		base.waitForElement(getIngestionErrorNumber());
		getIngestionErrorNumber().waitAndClick(5);

		if (getIngestionErrorMessage().isElementAvailable(5)) {
			base.passedStep(
					"Date format selected in the ingestion is not matching with the date format of the dates in the DAT file. Please provide the matching date format. Is displayed Successfully");

		} else {
			base.failedStep("The expected error message is not displayed");
		}
		base.waitForElement(getCloseButton());
		getCloseButton().waitAndClick(5);

}
	
	/**
	 * @author: Mohan Created Date: 25/02/2022 Modified by: NA Modified Date: NA
	 * @description: To verify Dateformate in the Ingestion wizard
	 */
	public void verifyDateFormateInIngestionField() {
		
		driver.waitForPageToBeReady();
		String dateFormate1 = getIngestionWizardDateFormate().getText();
		System.out.println(dateFormate1);
		if (dateFormate1.length() > 11) {
			base.passedStep(
					"When Ingestion in draft mode is opened Ingestion Wizard is retain with the selected 'Date & Time Format'");
		} else {
			base.failedStep("Date & time formate is not valid");
		}
		
	}
  /**
 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
 * @description: Method to process to click on copied state without ignoring errors by over lay.
 */
public void clickOnCopiedStateWithoutIgnoringErrorsByOverlay(String ingestionName) {
	try {
		driver.scrollPageToTop();
		String status = null;
		for(int i=0;i<30;i++) {
			if(getStatusByingestionName(ingestionName).isDisplayed()) {
				status = getStatusByingestionName(ingestionName).getText().trim();
				break;
			}else {
				driver.scrollingToBottomofAPage();
			}
		}
		if(status.contains("Cataloged")) {
			driver.waitForPageToBeReady();
			getIngestionLinkByName(ingestionName).isElementAvailable(15);
			getIngestionLinkByName(ingestionName).Click();
			driver.waitForPageToBeReady();
			getStartCopy().ScrollTo();
			getStartCopy().isElementAvailable(15);
			getStartCopy().Click();
			driver.scrollPageToTop();
			getCloseButton().isElementAvailable(15);
			getCloseButton().Click();
			getRefreshButton().isElementAvailable(15);
			getRefreshButton().Click();
		}
		
		for(int i=0;i<2000;i++) {
			for(int j=0;j<30;j++) {
				if(getStatusByingestionName(ingestionName).isDisplayed()) {
					status = getStatusByingestionName(ingestionName).getText().trim();
					break;
				}else {
					driver.scrollingToBottomofAPage();
				}
			}
			if(status.contains("In Progress")) {
				driver.scrollPageToTop();
				getRefreshButton().isElementAvailable(15);
				getRefreshButton().Click();
			}
			if(status.contains("Passed")) {
				base.passedStep("Ingestion entered to failed state if copied state starts without ignoring errors");
				break;
			}
		}
	}catch(Exception e) {
		e.printStackTrace();
		base.failedStep("Exception occured while selecting field catagory and destination field by using source DAT field."+e.getLocalizedMessage());
	}
}


/**
 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
 * @description: Method to process to click on roll back from catalog stage and verify status changed is inprogress.
 */
public void verifyInprogressStatusByclickOnRollback(String ingestionName) {
	try {
		driver.scrollPageToTop();
		String status = null;
		for(int i=0;i<30;i++) {
			if(getStatusByingestionName(ingestionName).isDisplayed()) {
				status = getStatusByingestionName(ingestionName).getText().trim();
				break;
			}else {
				driver.scrollingToBottomofAPage();
			}
		}
		
		if(status.contains("Cataloged")) {
			driver.waitForPageToBeReady();
			getIngestionLinkByName(ingestionName).isElementAvailable(15);
			driver.waitForPageToBeReady();
			getIngestionGearIcon(ingestionName).isElementAvailable(15);
			getIngestionGearIcon(ingestionName).Click();
			driver.waitForPageToBeReady();
			getRollBack(ingestionName).isElementAvailable(15);
			getRollBack(ingestionName).Click();
			driver.waitForPageToBeReady();
			getApproveMessageOKButton().isElementAvailable(10);
			getApproveMessageOKButton().Click();
			driver.scrollPageToTop();
			getRefreshButton().isElementAvailable(15);
			getRefreshButton().Click();
		}
		status = getStatusByingestionName(ingestionName).getText().trim();
		if(status.contains("In Progress")) {
			base.passedStep( " Got Ingestion : "+ingestionName+" status is in progess successfully");
		}
	}catch(Exception e) {
		e.printStackTrace();
		base.failedStep("Exception occured while process to click on roll back from catalog stage and verify status changed is inprogress.."+e.getLocalizedMessage());
	}
}

	/**
	 * @author: Arunkumar Created Date: 16/03/2022 Modified by: NA Modified Date: NA
	 * @description: Method to check next button status when date is selected and not selected
	 */
	
	public void VerifyNextButtonStatusBasedOnDateTimeFormatSelection() {
		base.stepInfo("Click on add new ingestion button");
		base.waitForElement(getAddanewIngestionButton());
		getAddanewIngestionButton().waitAndClick(10);
		base.stepInfo("Select Source system");
		base.waitForElement(getSpecifySourceSystem());
		getSpecifySourceSystem().selectFromDropdown().selectByVisibleText("TRUE");
		base.stepInfo("Select Location");
		base.waitForElement(getSpecifyLocation());
		getSpecifyLocation().selectFromDropdown().selectByVisibleText(Input.SourceLocation);
		base.waitForElement(getSpecifySourceFolder());
		base.stepInfo("Select Folder");
	
		getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.TiffImagesFolder);
		base.waitForElement(getDATDelimitersFieldSeparator());
		getDATDelimitersFieldSeparator().selectFromDropdown().selectByVisibleText("ASCII(20)");
	
		base.waitForElement(getDATDelimitersTextQualifier());
		getDATDelimitersTextQualifier().selectFromDropdown().selectByVisibleText("ASCII(254)");
	
		base.waitForElement(getDATDelimitersNewLine());
		getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText("ASCII(174)");
	
		base.waitForElement(getSourceSelectionDATKey());
		
		getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
		getNextButton().waitAndClick(20);
		driver.scrollingToBottomofAPage();
		if(errorMessageMissingDate().isDisplayed()) {
			base.passedStep("Next button is disabled if date format is not selected");
		}
		else {
			base.passedStep("Next button is enabled if date format is not selected");
		}
		base.stepInfo("Select Date Format");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDateFormat().Visible();
			}
		}), Input.wait30);
		getDateFormat().selectFromDropdown().selectByVisibleText("YYYY/MM/DD HH:MM:SS");
	
		driver.scrollPageToTop();
		base.waitForElement(getNextButton());
		getNextButton().waitAndClick(20);
		
		if(getApproveMessageOKButton().isDisplayed()) {
			base.passedStep("Next button is enabled if date format is selected");
		}
		else {
			base.passedStep("Next button is not enabled if date format is selected");
		}
	}
	
	/**
	 * @author: Arunkumar Created Date: 16/03/2022 Modified by: NA Modified Date: NA
	 * @description: this method will validate the term which present in the copying table column
	 * @return: will return the missed doc value of term which displayed in copy table columns
	 */
	public int verifyMissedDocValuePresentInCopyTableColumn(String term) {
		 getRefreshButton().waitAndClick(10);	
	     getIngestionName().waitAndClick(Input.wait30);
	    
	 	driver.scrollingToElementofAPage(getRunIndexing());
	 	base.waitForElement(getRunIndexing());
	 	
	 	if(copyTableDataValue(term,4).isDisplayed()) {
	 		base.passedStep(term+ "count is displayed in the copying table column");
	 	}
	 	else {
	 		base.failedStep(term+"count is not displayed in the copying table column");
	 	}
	 	int value = Integer.parseInt(copyTableDataValue(term,4).getText());
	 	getCloseButton().waitAndClick(10);
	 	return value;	
	}
	
	/**
	 * @author: Arunkumar Created Date: 17/03/2022 Modified by: NA Modified Date: NA
	 * @description: this method will validate the date format in ingestion field cataloging stage
	 */
	public void verifyExpectedDateFormatAfterCatalogingStage() {
		
		driver.waitForPageToBeReady();
		String dateFormat = getIngestionWizardDateFormate().getText();
		String firstSectionInDateFormat[] = dateFormat.split("/");
		int firstsectionLength= firstSectionInDateFormat[0].length();
		int dateFormatTotalLength = dateFormat.length();
		
		if (dateFormatTotalLength==19 && firstsectionLength==4) {
			base.passedStep(" ingestion converted the provided data into the Sightline desired/expected date format ");
		} else {
			base.failedStep("ingestion not converted the provided data into the Sightline desired/expected date format");
		}	
	}
	
	/**
	 * @author: Arunkumar Created Date: 18/03/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify the error when selected date format is different than in DAT.
	 */
	public void verifyCatalogingErrorIfDateFormatIsDifferentThanDAT() {
		
		driver.waitForPageToBeReady();
		if (getFailedIngestionStatus().isElementAvailable(5)) {
			int numberOfErrors= Integer.parseInt(errorCountStatus().getText());
			
			getIngestionName().waitAndClick(10);
			
			base.waitForElement(errorCountCatalogingStage());
		    errorCountCatalogingStage().waitAndClick(10);
		    base.waitTime(5);
		    base.waitForElement(ignoreAllButton());
		
			for(int i=1;i<=numberOfErrors;i++) {
				
				if(ingestionErrorNote(i).getText().contains(Input.differentDateFormatError)) {
					base.passedStep("Cataloging Error displayed when selected date format different than in DAT");
				}
				else {
					base.failedStep("Cataloging Error not displayed when selected date format different than in DAT");
				}
			}
			
			getCloseButton().waitAndClick(10);
	}
		else if(getCatalogedIngestionStatus().isElementAvailable(5)) {
			base.failedStep("No Errors and Selected Date format is same as in DAT");
			
		}
			
	}
}
