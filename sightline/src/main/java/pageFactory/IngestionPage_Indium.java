package pageFactory;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;

import org.apache.pdfbox.contentstream.operator.text.EndText;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
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
	
	public Element getMp3FilePathFieldinDAT() {
		return driver.FindElementById("ddlFilesPathMP3Variant");
	}
	
	public Element getTranscriptFilePathFieldinDAT() {
		return driver.FindElementById("ddlFilesPathTranscript");
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
	
	public Element getDatCheckBox() {
		return driver.FindElementByXPath("//*[@name='IngestionSpecifySetting.IsDATFolder']/following-sibling::i");
	}

	public Element getPDFCheckBoxButton() {
		return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsPDFFolder']/following-sibling::i");
	}

	public Element getMP3CheckBoxButton() {
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
	public Element getExportIconButton() {
		return driver.FindElementByXPath("//a[@id='ExportDatasetExceptions']");

		}	

	public Element getIngestion_CopyingTableValue(int row , int value) {
		return driver.FindElementByXPath("//div[@id='Copyingblock']//table//tbody//tr["+row+"]//td[contains(text(),'"+value+"')]");
	}

	public Element getIngestion_GenerateSearchablePDFsCheckbox() {
		return driver.FindElementByXPath("//div[@id='TIFFfile']//label[contains(normalize-space(.),'Generate')]//i");
	}

	public Element getIngestion_DraftTable() {
		return driver.FindElementByXPath("//div[@id='cardCanvas']");
	}

	public Element getIngestion_DraftCount() {
		return driver.FindElementByXPath("//div[@id='cardCanvas']/input[@id='hddtotalIngestionCount']");
	}

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

	// Added by Gopinath - 23/02/2022
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
		return driver.FindElementByXPath("//tbody[@id='tblBody']//option[text()='" + sourceDATField
				+ "' and @selected='selected']//../self::select//..//following-sibling::td[1]//select[not(contains(@disabled,'disabled'))]");
	}

	public Element getDestinationFieldBySourceDat(String sourceDATField) {
		return driver.FindElementByXPath("//tbody[@id='tblBody']//option[text()='" + sourceDATField
				+ "' and @selected='selected']//../self::select//..//following-sibling::td[2]//select[not(contains(@disabled,'disabled'))]");
	}

	public Element getIngestionTitle(int row) {
		return driver.FindElementByXPath("//div[@id='cardCanvas']//li[" + row + "]//a//span");
	}

	public Element getStatus(int row) {
		return driver.FindElementByXPath("//div[@id='cardCanvas']//li[" + row + "]//strong[contains(text(),'Status')]");
	}

	public Element getStatusByingestionName(String ingestionName) {
		return driver.FindElementByXPath("//span[@title='" + ingestionName + "']//..//..//div[2]//strong[1]");
	}

	public Element getIngestionLinkByName(String ingestionName) {
		return driver.FindElementByXPath("//a//span[@title='" + ingestionName + "']");
	}

	public Element getStartCopy() {
		return driver.FindElementByXPath("//strong[text()='Copying']//..//..//..//i[@class='fa fa-play-circle-o']");
	}

	public Element getIngestionWizardDateFormat() {
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

	public Element copyTableDataValue(String term, int row) {
		return driver.FindElementByXPath("//*[@id='Copyingblock']//table//td[contains(text(),'" + term
				+ "')]/following-sibling::td[" + row + "]");
	}

	public Element doneButton() {
		return driver.FindElementById("Catalogdone");
	}

	public Element errorMessageMissingDate() {
		return driver.FindElementById("errorMsgDateFormat");
	}

	public Element ingestionErrorNote(int row) {
		return driver.FindElementByXPath("//*[@id='myDataTable']//tbody//tr[" + row + "]//td[3]");
	}

	public Element errorCountStatus() {
		return driver.FindElementById("errcount");
	}

	public ElementCollection mappedSourceFields(int row) {
		return driver.FindElementsByXPath("//*[@id='formMapping']//table//tbody//td[" + row + "]");
	}

	public ElementCollection previewRecordPopupHeaderFields() {
		return driver.FindElementsByXPath("//*[@id='popupdiv']//th");
	}

	public Element ingestionDetailActionDropdown() {
		return driver.FindElementByXPath("//button[@class='btn btn-defualt dropdown-toggle']");
	}

	public Element deleteActionButton() {
		return driver.FindElementByXPath("//ul[@role='menu']//li[contains(.,'Delete')]");
	}

	public Element copyingSectionDetails() {
		return driver.FindElementByXPath("//div[@id='Copyingblock']//div//div//div//span");
	}

	public Element catalogSectionDetails() {
		return driver.FindElementByXPath("//div[@id='Catalogingblock']//div//div//div//span");
	}

	public Element indexingSectionDetails() {
		return driver.FindElementByXPath("//div[@id='Indexingblock']//div//div//div//span");
	}

	public Element goBackButton() {
		return driver.FindElementByXPath("//*[@class='ui-dialog-buttonset']//button[text()='Go Back']");
	}

	public Element rollbackOption() {
		return driver.FindElementByXPath("//dt[contains(.,'Rollback')]//a");
	}

	public Element getIngestionOpenWizardbutton() {
		return driver.FindElementByXPath("//dt[contains(.,'Wizard')]");
	}

	public Element getIngestionDetailPopup(int ingestionNumber) {
		return driver.FindElementByXPath("//*[@id='cardCanvas']/ul/li[" + ingestionNumber + "]//a//span");
	}

	public Element getRollbackWarningMessage() {
		return driver.FindElementByXPath("//*[@id='MsgBoxBack']//p");
	}

	public Element rollbackOptionInPopup() {
		return driver.FindElementByXPath("//ul[@role='menu']//li[contains(.,'Rollback')]");
	}

	public Element getIngestion_TileView() {
		return driver.FindElementById("TileView");
	}

	public Element getSourceCount() {
		return driver.FindElementByXPath("//div[text()='Source']//span");
	}

	public Element getIngestedCount() {
		return driver.FindElementByCssSelector("[class*='ingestCt'] span");
	}

	public Element getProjectNameInPopup() {
		return driver.FindElementByXPath("//label[text()='Project Name :']/following-sibling::div");
	}

	public Element getIngestionStatusInPopup() {
		return driver.FindElementByXPath("//label[text()='% Complete :']/following-sibling::div");
	}

	public Element getTimeStampInPopup() {
		return driver.FindElementByXPath("//label[text()='End Date :']/following-sibling::div");
	}

	public Element getApproveMessageCancelButton() {
		return driver.FindElementById("bot2-Msg1");
	}

	public Element ingestionNextButton() {
		return driver.FindElementByXPath("//li[@id='IngestionGridViewtable_next']//a");
	}

	public Element ingestionPreviousButton() {
		return driver.FindElementByXPath("//li[@id='IngestionGridViewtable_previous']//a");
	}

	public Element ingestionPaginationNext() {
		return driver.FindElementById("IngestionGridViewtable_next");
	}

	public Element ingestionPaginationPrevious() {
		return driver.FindElementById("IngestionGridViewtable_previous");
	}

	public Element moveToNextPage() {
		return driver.FindElementByXPath("//li[@class='paginate_button ']//a");
	}

	public Element showAllIngestion() {
		return driver.FindElementByXPath("//label[@class='checkbox']//i");
	}

	public Element currentActivePage() {
		return driver.FindElementByXPath("//li[@class='paginate_button active']");
	}

	public Element ingestionModifiedUser() {
		return driver.FindElementByXPath("//div[@class='bottomStamps row']//span");
	}

	public Element ingestionCompletedDate() {
		return driver.FindElementByXPath("//strong[contains(text(),'Status')]/following-sibling::div");
	}

	public Element ingestionProgressBar() {
		return driver.FindElementByXPath("//div[@role='progressbar']");
	}

	public Element getIgnoreOptionSelection() {
		return driver.FindElementByXPath("//input[@id='rbignore']");
	}

	public Element ignoreOptionInErrorList() {
		return driver.FindElementByXPath("//tr[@role='row']//label[text()='Ignore']");
	}

	public Element rollbackButtonStatus() {
		return driver.FindElementByXPath("//ul[@role='menu']//li[contains(.,'Rollback')]//a");
	}

	public Element gridTable() {
		return driver.FindElementByXPath("//div[@class='dataTables_scrollHeadInner']/table[@role='grid']");
	}

	public Element copyingErrorCount() {
		return driver.FindElementByXPath("//label[contains(.,'Document Files Copied :')]//..//div//span//a");
	}

	public Element pdfCheckboxStatus() {
		return driver.FindElementByXPath("//*[@id='PDFFile']");
	}

	public Element getIngestionCopyButton() {
		return driver.FindElementByXPath("//dt[contains(.,'Copy')]");
	}

	public Element getIngestionDeleteButton() {
		return driver.FindElementByXPath("//dt[contains(.,'Delete')]");
	}

	public Element getActionOpenWizard() {
		return driver.FindElementByXPath(".//*[@id='IngestionDetailsPopUp1']//a[text()='Open in Wizard ']");
	}

	public Element datCheckboxStatus() {
		return driver.FindElementByXPath("//*[@id='chkDAT']//..//i");
	}

	public Element getActionCopy() {
		return driver.FindElementByXPath(".//*[@id='IngestionDetailsPopUp1']//a[text()='Copy ']");
	}
	
	public Element getActionDelete() {
		return driver.FindElementByXPath("//*[@id='IngestionDetailsPopUp1']//a[text()='Delete']");
	}

	public Element backButton() {
		return driver.FindElementByXPath("//*[@class='ui-dialog-buttonset']//button[text()='Back']");
	}

	public Element getIngestionStatusInPopup(String ingestionStage) {
		return driver.FindElementByXPath("//div[@id='" + ingestionStage
				+ "']//div//div//label[contains(.,'% Complete :')]//following-sibling::div[1]");

	}

	public Element mappingWarningMessage() {
		return driver.FindElementByXPath("//div[@id='Msg1']//p");
	}

	public Element warningMessageCancelButton() {
		return driver.FindElementByXPath("//button[@id='bot2-Msg1']");
	}

	public Element getNextColumnTerm(String term) {
		return driver.FindElementByXPath("//*[@id='Copyingblock']//table//td[contains(text(),'" + term
				+ "')]//..//following-sibling::tr//td[1]");
	}

	public Element getTotalIngestedCount() {
		return driver.FindElementByXPath("//p[@id='totalIngestionDocuments']");
	}

	public Element getApproveMessageSecondOKButton() {
		return driver.FindElementById("bot1-Msg2");
	}

	public Element getTotalPageCount() {
		return driver.FindElementByXPath("//*[@id='IngestionGridViewtable_info']//span[@class='text-primary']");
	}
	
	public Element getAnalyticsStatus(int analyticRow,int dataColumn) {
		return driver.FindElementByXPath("//table[@id='ProjectFieldsDataTable']//tbody//tr["+ analyticRow +"]//td["+ dataColumn +"]");
	}
	
	public Element getMappingFieldBackButton() {
		return driver.FindElementById("BackButton");
	}

	// Added by Gopinath - 28/02/2022
	public Element getRollBack(String ingestionName) {
		return driver.FindElementByXPath("//a//span[@title='" + ingestionName + "']//..//..//a[text()='Rollback']");
	}

	public Element getIngestionGearIcon(String ingestionName) {
		return driver.FindElementByXPath(
				"//a//span[@title='" + ingestionName + "']//..//..//a[@class='dropdown-toggle']//i");
	}

	// Added by Gopinath - 12/04/2022
	public Element firstTileTitle() {
		return driver.FindElementByXPath("(//div[@id='cardCanvas']//li//a//span)[1]");
	}

	public Element errorsCount() {
		return driver.FindElementByXPath("//span[contains(text(),'Errors')]//a");
	}

	public Element ignoreAllButon() {
		return driver.FindElementByXPath("//button[@id='btnignoreall'] | //button[contains(text(),'Ignore All')]");
	}

	public Element catalogDone() {
		return driver.FindElementByXPath("//button[@id='Catalogdone'] | //button[text()='Done']");
	}

	public Element startCoping() {
		return driver.FindElementByXPath("//a[@id='RunCopying']//i");
	}

	public Element statusOfIngestion(String ingestionName) {
		return driver.FindElementByXPath("(//span[@title='" + ingestionName + "']//..//..//strong)[2]");
	}

	public Element startIndexing() {
		return driver.FindElementByXPath("//a[@id='RunIndexing']//i");
	}

	public Element actionDropDown() {
		return driver.FindElementByXPath(
				"//div[@class='btn-group select-actions']//button[@class='btn btn-defualt dropdown-toggle']");
	}

	public Element approveOption() {
		return driver.FindElementByXPath("//a[text()='Approve']");
	}

	public Element fullAnalysisRadioButton() {
		return driver.FindElementByXPath("(//h6[text()='Project Level Analytics and Indexing']//..//div//i)[1]");
	}

	public Element runButton() {
		return driver.FindElementById("run");
	}

	public Element endTime() {
		return driver.FindElementByXPath("//table[@id='ProjectFieldsDataTable']//tbody//td[4]");
	}

	public Element publishButton() {
		return driver.FindElementById("publish");
	}

	public Element savedSearch(String savedSearch) {
		return driver.FindElementByXPath("//div[@id='treeFolder']/ul/li/ul/li//a[text()='" + savedSearch + "']");
	}

	public Element unPublishButton() {
		return driver.FindElementById("Analyze");
	}

	public ElementCollection totalPages() {
		return driver.FindElementsByXPath("//ul[@class='pagination pagination-sm']//li//a");
	}

	public Element disabledNextButton() {
		return driver.FindElementByXPath("//ul[@class='pagination pagination-sm']//li[contains(@class,'disabled')]");
	}

	public ElementCollection totalRowsUnpublishTable() {
		return driver.FindElementsByXPath("//table[@id='UnpublishHistoryDatatable']//tbody//tr");
	}

	public Element nextButton() {
		return driver.FindElementByXPath("//li//a[text()='Next']");
	}

	public Element unPunlishSearch(String savedSearch) {
		return driver.FindElementByXPath("//table[@id='UnpublishHistoryDatatable']//tbody//tr//td[text()='"
				+ savedSearch + "']/following-sibling::td//a[text()='Inprogress']");
	}

	// Add by Aathith
	
	public Element getDatasetMappedCount(String ingestionCount) {
		return driver.FindElementByXPath("//div[contains(@class,'sourceCt')]//span[contains(normalize-space(.),'"+ingestionCount+"')]");
	}
	
	
	public Element getDataSetViewInDocView(String DataSet) {
		return driver.FindElementByXPath("//a[contains(@title,'"+DataSet+"')]/../..//a[text()='DocView']");
	}
	
	public Element getDataSetActionBtn(String DataSet) {
		return driver.FindElementByXPath("//a[contains(@title,'"+DataSet+"')]/../..//button");
	}
	
	public Element getDataSetName(String DataSet) {
		return driver.FindElementByXPath("//a[contains(@title,'"+DataSet+"')]");
	}
	public Element getRefeshBtn() {
		return driver.FindElementByXPath("//a[@id='refresh']");
	}

	public Element getIngestions() {
		return driver.FindElementByXPath("//a[@title='Ingestions']");
	}

	public Element generateSearchablePdfSourceDoc() {
		return driver.FindElementByXPath("//*[@id='dt_basic']/tbody/tr[9]/td[2]");
	}

	public Element generateSearchablePdfCopiedDoc() {
		return driver.FindElementByXPath("//*[@id='dt_basic']/tbody/tr[9]/td[3]");
	}

	public Element getInestionPage() {
		return driver.FindElementByXPath("//a[@name='Ingestion']");
	}

	// Added by Vijaya.Rani
	public ElementCollection getUnpublishTableHeader() {
		return driver.FindElementsByXPath("//*[@id='UnpublishHistoryDatatable']/thead/tr/th");
	}

	public Element getUnpublishtableValues(String searchName, int coulumNumber) {
		return driver.FindElementByXPath("//*[@id='UnpublishHistoryDatatable']/tbody/tr/td[text()='" + searchName
				+ "']/../td[" + coulumNumber + "]");
	}
	public Element indexingErrorCount() {
		return driver.FindElementByXPath("//label[contains(.,'Document Data Indexed')]//..//div//span//a");
	}
	public Element getCloseBtn() {
		return driver.FindElementByXPath("//i[@class='fa fa-lg fa-close']");
	}
	//Added by Arun
	
	public Element getAddProjectBtn(){ 
		 return driver.FindElementById("btnAdd"); 
		 }
	 
	 public Element getHelpContentIcon(String content) {
		 return driver.FindElementByCssSelector("a[data-original-title*='"+content+"']");
	 }
	 
	 public Element helpContentPopup() {
		 return driver.FindElementByXPath("//div[@role='tooltip']");
	 }
	 public Element getHelpContent() {
		 return driver.FindElementByXPath("//div[@role='tooltip']//div[@class='popover-content']");
	 }
	 
	 public Element getKickOffAnalyticsCheckbox() {
		 return driver.FindElementByXPath("//input[@id='chkAutoAnalytics']//..//i");
	 }
	 
	 public Element getIncrementalAnalyticsCheckbox() {
		 return driver.FindElementByXPath("//input[@id='chkAutoIncrAnalytics']//..//i");
	 }
	 public Element getOptionStatus() {
		 return driver.FindElementByXPath("//input[@id='chkAutoIncrAnalytics']");
	 }
	 
	 public Element getMappingSourceField(int row) {
			return driver.FindElementById("SF_"+row+"");
		}
		
		public Element getMappingCategoryField(int row) {
			return driver.FindElementById("TY_"+row+"");
		}
		public Element getMappingDestinationField(int row) {
			return driver.FindElementById("DF_"+row+"");
		}
		public Element indexTableDataValue(String term, int row) {
			return driver.FindElementByXPath("//*[@id='Indexingblock']//table//td[contains(text(),'" + term
					+ "')]/following-sibling::td['" + row + "']");
		}
	public Element sourceLocationMandatoryError() {
		return driver.FindElementById("ddlServerLocation-error");
	}
	public Element sourceFolderMandatoryError() {
		return driver.FindElementById("ddlFolders-error");
	}
	public Element dateFormatMandatoryError() {
		return driver.FindElementById("ddlDateFormat-error");
	}
	public Element datKeyMandatryError() {
		return driver.FindElementById("lblErrorDATKey");
	}
	
	public Element previewRecordPopup() {
		return driver.FindElementByXPath("//div[@id='popupdiv']");
	}
	
	
	public IngestionPage_Indium(Driver driver) {

		this.driver = driver;
		this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
		base = new BaseClass(driver);
		driver.waitForPageToBeReady();

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
					base.waitTime(5);
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
					base.waitTime(5);
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
					base.waitTime(10);
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
		base.waitTime(2);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getIngestionActionButton().Visible();
			}
		}), Input.wait30);
		getIngestionActionButton().waitAndClick(5);
		base.waitTime(2);

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
		base.waitTime(2);

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
		base.waitTime(2);

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
			base.waitTime(2);
			getSpecifyLocation().selectFromDropdown().selectByVisibleText(Input.SourceLocation);

			base.waitForElement(getSpecifySourceFolder());
			base.waitTime(2);
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
			base.waitTime(2);
			base.waitForElement(getDATDelimitersFieldSeparator());
			getDATDelimitersFieldSeparator().selectFromDropdown().selectByVisibleText(Input.fieldSeperator);

			base.waitForElement(getDATDelimitersTextQualifier());
			getDATDelimitersTextQualifier().selectFromDropdown().selectByVisibleText(Input.textQualifier);

			base.waitForElement(getDATDelimitersNewLine());
			getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);

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
			base.waitTime(2);

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
				base.waitForElement(getPDFCheckBoxButton());
				getPDFCheckBoxButton().waitAndClick(20);
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
				base.waitForElement(getMP3CheckBoxButton());
				getMP3CheckBoxButton().waitAndClick(15);
				base.waitForElement(getMP3LST());
				getMP3LST().selectFromDropdown().selectByVisibleText(Input.MP3File);
			}

			if (dataset.contains("CJK_GermanAudioTestData")) {
				base.stepInfo("*******Selecing MP3 files***************");
				base.waitForElement(getMP3CheckBoxButton());
				getMP3CheckBoxButton().waitAndClick(15);
				base.waitForElement(getMP3LST());
				getMP3LST().selectFromDropdown().selectByVisibleText(Input.MP3GermanFile);
			}

			if (dataset.contains("CJK_JapaneseAudioTestData")) {
				base.stepInfo("*******Selecing MP3 files***************");
				base.waitForElement(getMP3CheckBoxButton());
				getMP3CheckBoxButton().waitAndClick(15);
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
				base.waitTime(2);

				base.waitForElement(getMappingFIELDCAT6());
				getMappingSOURCEFIELD6().selectFromDropdown().selectByVisibleText("EmailBCCNameAndBCCAddress");
				getMappingFIELDCAT6().selectFromDropdown().selectByVisibleText("EMAIL");
				getMappingDESTINATIONFIELD6().selectFromDropdown().selectByVisibleText("EmailBCCNamesAndAddresses");

				getAddButton().waitAndClick(15);
				base.waitTime(2);

				base.waitForElement(getMappingFIELDCAT7());
				getMappingSOURCEFIELD7().selectFromDropdown().selectByVisibleText("EmailCCNamAndCCAddress");
				getMappingFIELDCAT7().selectFromDropdown().selectByVisibleText("EMAIL");
				getMappingDESTINATIONFIELD7().selectFromDropdown().selectByVisibleText("EmailCCNamesAndAddresses");

				getAddButton().waitAndClick(15);
				base.waitTime(2);

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
		base.waitTime(2);
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
	 * @param ingestionType  : ingestionType is String value need to select type of
	 *                       ingestion need to perform.
	 * @param sourceSystem   : sourceSystem is String value that name of source
	 *                       system.
	 * @param sourceLocation : sourceLocation is String value that location of
	 *                       source.
	 * @param sourceFolder   : sourceFolder is String value that folder of source.
	 */
	public void selectIngestionTypeAndSpecifySourceLocation(String ingestionType, String sourceSystem,
			String sourceLocation, String sourceFolder) {
		try {
			driver.getWebDriver().get(Input.url + "Ingestion/Home");
			driver.waitForPageToBeReady();
			base.waitForElement(getAddanewIngestionButton());
			getAddanewIngestionButton().isElementAvailable(10);
			getAddanewIngestionButton().Click();
			base.waitForElement(getIngestion_IngestionType());
			getIngestion_IngestionType().isElementAvailable(10);
			getIngestion_IngestionType().selectFromDropdown().selectByVisibleText(ingestionType);
			if (!ingestionType.trim().equalsIgnoreCase("Overlay Only")) {
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
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while selecting ingestion type and specify source loaction"
					+ e.getLocalizedMessage());
		}
	}

	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to select DAT delimiters.
	 * @param fieldSeperator : fieldSeperator is String value that field seperator .
	 * @param textQualifier  : textQualifier is String value that name of text
	 *                       qualifier.
	 * @param multiValue     : multiValue is String value that multi value.
	 */
	public void addDelimitersInIngestionWizard(String fieldSeperator, String textQualifier, String multiValue) {
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
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while selecting DAT delimiters." + e.getLocalizedMessage());
		}
	}

	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to select DAT source.
	 * @param loadFile    : loadFile is String value that load file value.
	 * @param documentKey : documentKey is String value that document key.
	 */
	public void selectDATSource(String loadFile, String documentKey) {
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
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while selecting DAT source." + e.getLocalizedMessage());
		}
	}

	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to select native source.
	 * @param loadFile          : loadFile is String value that load file value.
	 * @param pathInDATFileflag : pathInDATFileflag is boolean value that weather
	 *                          path in DAT file check box need to enable or not.
	 */
	public void selectNativeSource(String loadFile, boolean pathInDATFileflag) {
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
			if (pathInDATFileflag) {
				getNativePathInDATFileCheckBox().isElementAvailable(10);
				getNativePathInDATFileCheckBox().Click();
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while selecting Native source." + e.getLocalizedMessage());
		}
	}

	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to select text source.
	 * @param loadFile          : loadFile is String value that load file value.
	 * @param pathInDATFileflag : pathInDATFileflag is boolean value that weather
	 *                          path in DAT file check box need to enable or not.
	 */
	public void selectTextSource(String loadFile, boolean pathInDATFileflag) {
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
			if (pathInDATFileflag) {
				getTextPathInDATFileCheckBox().isElementAvailable(10);
				getTextPathInDATFileCheckBox().Click();
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while selecting text source." + e.getLocalizedMessage());
		}
	}

	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to select PDF source.
	 * @param loadFile          : loadFile is String value that load file value.
	 * @param pathInDATFileflag : pathInDATFileflag is boolean value that weather
	 *                          path in DAT file check box need to enable or not.
	 */
	public void selectPDFSource(String loadFile, boolean pathInDATFileflag) {
		try {
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			getPDFCheckBoxButton().ScrollTo();
			getPDFCheckBoxButton().isElementAvailable(15);
			getPDFCheckBoxButton().Click();
			getPDFLST().ScrollTo();
			base.waitForElement(getPDFLST());
			getPDFLST().isElementAvailable(15);
			getPDFLST().selectFromDropdown().selectByVisibleText(loadFile);
			if (pathInDATFileflag) {
				getPDFPathInDATFileCheckBox().isElementAvailable(10);
				getPDFPathInDATFileCheckBox().Click();
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while selecting PDF source." + e.getLocalizedMessage());
		}
	}

	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to select TIFF source.
	 * @param loadFile                 : loadFile is String value that load file
	 *                                 value.
	 * @param pathInDATFileflag        : pathInDATFileflag is boolean value that
	 *                                 weather path in DAT file check box need to
	 *                                 enable or not.
	 * @param genSearchPDFCheckBoxFlag : genSearchPDFCheckBoxFlag is boolean value
	 *                                 that weather generate searchable pdf check
	 *                                 box need to enable or not.
	 */
	public void selectTIFFSource(String loadFile, boolean pathInDATFileflag, boolean genSearchPDFCheckBoxFlag) {
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
			if (pathInDATFileflag) {
				getTIFFPathInDATFileCheckBox().isElementAvailable(10);
				getTIFFPathInDATFileCheckBox().Click();
			}
			if (genSearchPDFCheckBoxFlag) {
				getTIFFSearchablePDFCheckBox().isElementAvailable(10);
				getTIFFSearchablePDFCheckBox().Click();
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while selecting TIFF source." + e.getLocalizedMessage());
		}
	}

	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to select MP3 varient source.
	 * @param loadFile          : loadFile is String value that load file value.
	 * @param pathInDATFileflag : pathInDATFileflag is boolean value that weather
	 *                          path in DAT file check box need to enable or not.
	 */
	public void selectMP3VarientSource(String loadFile, boolean pathInDATFileflag) {
		try {
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			getMP3CheckBoxButton().ScrollTo();
			getMP3CheckBoxButton().isElementAvailable(15);
			getMP3CheckBoxButton().Click();
			getMP3LST().ScrollTo();
			base.waitForElement(getMP3LST());
			getMP3LST().isElementAvailable(15);
			getMP3LST().selectFromDropdown().selectByVisibleText(loadFile);
			if (pathInDATFileflag) {
				getMP3PathInDATFileCheckBox().isElementAvailable(10);
				getMP3PathInDATFileCheckBox().Click();
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while selecting MP3 varient source." + e.getLocalizedMessage());
		}
	}

	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to select Audio Transcript source.
	 * @param loadFile          : loadFile is String value that load file value.
	 * @param pathInDATFileflag : pathInDATFileflag is boolean value that weather
	 *                          path in DAT file check box need to enable or not.
	 */
	public void selectAudioTranscriptSource(String loadFile, boolean pathInDATFileflag) {
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
			if (pathInDATFileflag) {
				getAudioTransistPathInDATFileCheckBox().isElementAvailable(10);
				getAudioTransistPathInDATFileCheckBox().Click();
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while selecting Audio Transcript source." + e.getLocalizedMessage());
		}
	}

	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to select Other source.
	 * @param loadFile          : loadFile is String value that load file value.
	 * @param linkType          : linkType is String value that link type value.
	 * @param pathInDATFileflag : pathInDATFileflag is boolean value that weather
	 *                          path in DAT file check box need to enable or not.
	 */
	public void selectOtherSource(String linkType, String loadFile, boolean pathInDATFileflag) {
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
			if (pathInDATFileflag) {
				getOtherPathInDATFileCheckBox().isElementAvailable(10);
				getOtherPathInDATFileCheckBox().Click();
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while selecting other source." + e.getLocalizedMessage());
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
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while selecting Date and Time format." + e.getLocalizedMessage());
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
			if (getApproveMessageOKButton().isDisplayed()) {
				getApproveMessageOKButton().isElementAvailable(15);
				getApproveMessageOKButton().Click();
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while click on next button." + e.getLocalizedMessage());
		}
	}

	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to select value from first three source DAT fields
	 */
	public void selectValueFromEnabledFirstThreeSourceDATFields(String firstDropDown, String secondDropDown,
			String thirdDropDown) {
		try {
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			getEnabledFirstDropDown().isElementAvailable(15);
			getEnabledFirstDropDown().selectFromDropdown().selectByVisibleText(firstDropDown);
			getEnabledSecondDropDown().isElementAvailable(15);
			getEnabledSecondDropDown().selectFromDropdown().selectByVisibleText(secondDropDown);
			getEnabledThirdDropDown().isElementAvailable(15);
			getEnabledThirdDropDown().selectFromDropdown().selectByVisibleText(thirdDropDown);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while select value from first three source DAT fields."
					+ e.getLocalizedMessage());
		}
	}

	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: Arunkumar Modified Date: 11/07/2022
	 * @description: Method to click on preview and run button.
	 */
	public void clickOnPreviewAndRunButton() {
		try {
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			getPreviewRun().isElementAvailable(15);
			getPreviewRun().Click();
			driver.waitForPageToBeReady();
			if(getApproveMessageOKButton().isElementAvailable(15)) {
			getApproveMessageOKButton().Click();
			driver.waitForPageToBeReady();
			}
			if(previewRecordPopup().isElementAvailable(10)) {
				base.passedStep("Preview record popup displayed");
			}
			getbtnRunIngestion().isElementAvailable(15);
			getbtnRunIngestion().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while click on preview and run button." + e.getLocalizedMessage());
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
			if (!getFilterINPROGRESS().GetAttribute("class").contains("active")) {
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
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while selecting all options from filter by dropdown." + e.getLocalizedMessage());
		}
	}

	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to navigate to ingestion page.
	 */
	public void navigateToIngestionPage() {
		try {
			driver.getWebDriver().get(Input.url + "Ingestion/Home");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while navigating to ingestion page." + e.getLocalizedMessage());
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
			for (int i = 1; i < 500; i++) {
				driver.waitForPageToBeReady();
				getIngestionTitle(count + 1).ScrollTo();
				getIngestionTitle(count + 1).isElementAvailable(15);
				title = getIngestionTitle(count + 1).GetAttribute("title").trim();
				getStatus(count + 1).isElementAvailable(15);
				String status = getStatus(count + 1).getText().trim();
				driver.waitForPageToBeReady();
				for (int j = 1; j < 50; j++) {
					titleCar = getIngestionTitle(j).GetAttribute("title").trim();
					getIngestionTitle(j).ScrollTo();
					if (titleCar.equalsIgnoreCase(title)) {
						if (j != 1) {
							count = j - 1;
						}
						break;
					} else {
						driver.scrollingToBottomofAPage();
					}
				}
				if (status.contains("In Progress")) {
					driver.scrollPageToTop();
					getRefreshButton().isElementAvailable(15);
					getRefreshButton().Click();
				}
				if (status.contains("Cataloged") && titleCar.equalsIgnoreCase(title)) {
					base.passedStep("Ingestion completed till cataloged stage");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while navigating to ingestion page." + e.getLocalizedMessage());
		}
		return title;
	}

	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to select field catagory and destination field by using
	 *               source DAT field.
	 * @param sourceDATField   : sourceDATField is String value that source DAT
	 *                         field selected in drop down.
	 * @param fieldCatagory    : fieldCatagory is String value that field catagory
	 *                         need to select sibling drop down of sourceDATField.
	 * @param destinationField : destinationField is String value that destination
	 *                         Field need to select sibling drop down of
	 *                         sourceDATField.
	 */
	public void selectFieldCatagoryDestinationFields(String sourceDATField, String fieldCatagory,
			String destinationField) {
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

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while selecting field catagory and destination field by using source DAT field."
							+ e.getLocalizedMessage());
		}
	}

	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to process to click on copied state without ignoring
	 *               errors.
	 */
	public void clickOnCopiedStateWithoutIgnoringErrors(String ingestionName) {
		try {
			driver.scrollPageToTop();
			String status = null;
			for (int i = 0; i < 30; i++) {
				if (getStatusByingestionName(ingestionName).isDisplayed()) {
					status = getStatusByingestionName(ingestionName).getText().trim();
					break;
				} else {
					driver.scrollingToBottomofAPage();
				}
			}
			if (status.contains("Cataloged")) {
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

			for (int i = 0; i < 2000; i++) {
				for (int j = 0; j < 30; j++) {
					if (getStatusByingestionName(ingestionName).isDisplayed()) {
						status = getStatusByingestionName(ingestionName).getText().trim();
						break;
					} else {
						driver.scrollingToBottomofAPage();
					}
				}
				if (status.contains("In Progress")) {
					driver.scrollPageToTop();
					getRefreshButton().isElementAvailable(15);
					getRefreshButton().Click();
				}
				if (status.contains("Failed")) {
					base.passedStep("Ingestion entered to failed state if copied state starts without ignoring errors");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while selecting field catagory and destination field by using source DAT field."
							+ e.getLocalizedMessage());
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
		base.waitTime(2);
		getSpecifyLocation().selectFromDropdown().selectByVisibleText(Input.SourceLocation);

		base.waitForElement(getSpecifySourceFolder());
		base.waitTime(2);
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
		base.waitTime(2);
		base.waitForElement(getDATDelimitersFieldSeparator());
		getDATDelimitersFieldSeparator().selectFromDropdown().selectByVisibleText(Input.fieldSeperator);

		base.waitForElement(getDATDelimitersTextQualifier());
		getDATDelimitersTextQualifier().selectFromDropdown().selectByVisibleText(Input.textQualifier);

		base.waitForElement(getDATDelimitersNewLine());
		getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);

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
		base.waitTime(2);

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
			base.waitForElement(getPDFCheckBoxButton());
			getPDFCheckBoxButton().waitAndClick(20);
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
			base.waitForElement(getMP3CheckBoxButton());
			getMP3CheckBoxButton().waitAndClick(15);
			base.waitForElement(getMP3LST());
			getMP3LST().selectFromDropdown().selectByVisibleText(Input.MP3File);
		}

		if (dataset.contains("CJK_GermanAudioTestData")) {
			base.stepInfo("*******Selecing MP3 files***************");
			base.waitForElement(getMP3CheckBoxButton());
			getMP3CheckBoxButton().waitAndClick(15);
			base.waitForElement(getMP3LST());
			getMP3LST().selectFromDropdown().selectByVisibleText(Input.MP3GermanFile);
		}

		if (dataset.contains("CJK_JapaneseAudioTestData")) {
			base.stepInfo("*******Selecing MP3 files***************");
			base.waitForElement(getMP3CheckBoxButton());
			getMP3CheckBoxButton().waitAndClick(15);
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

			String dateFormate1 = getIngestionWizardDateFormat().getText();
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
		base.waitTime(2);
		getSpecifyLocation().selectFromDropdown().selectByVisibleText(Input.SourceLocation);

		base.waitForElement(getSpecifySourceFolder());
		base.waitTime(2);
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
		base.waitTime(2);
		base.waitForElement(getDATDelimitersFieldSeparator());
		getDATDelimitersFieldSeparator().selectFromDropdown().selectByVisibleText(Input.fieldSeperator);

		base.waitForElement(getDATDelimitersTextQualifier());
		getDATDelimitersTextQualifier().selectFromDropdown().selectByVisibleText(Input.textQualifier);

		base.waitForElement(getDATDelimitersNewLine());
		getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);

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
		base.waitTime(2);

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
			base.waitForElement(getPDFCheckBoxButton());
			getPDFCheckBoxButton().waitAndClick(20);
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
			base.waitForElement(getMP3CheckBoxButton());
			getMP3CheckBoxButton().waitAndClick(15);
			base.waitForElement(getMP3LST());
			getMP3LST().selectFromDropdown().selectByVisibleText(Input.MP3File);
		}

		if (dataset.contains("CJK_GermanAudioTestData")) {
			base.stepInfo("*******Selecing MP3 files***************");
			base.waitForElement(getMP3CheckBoxButton());
			getMP3CheckBoxButton().waitAndClick(15);
			base.waitForElement(getMP3LST());
			getMP3LST().selectFromDropdown().selectByVisibleText(Input.MP3GermanFile);
		}

		if (dataset.contains("CJK_JapaneseAudioTestData")) {
			base.stepInfo("*******Selecing MP3 files***************");
			base.waitForElement(getMP3CheckBoxButton());
			getMP3CheckBoxButton().waitAndClick(15);
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
			base.waitTime(2);

			base.waitForElement(getMappingFIELDCAT6());
			getMappingSOURCEFIELD6().selectFromDropdown().selectByVisibleText("EmailBCCNameAndBCCAddress");
			getMappingFIELDCAT6().selectFromDropdown().selectByVisibleText("EMAIL");
			getMappingDESTINATIONFIELD6().selectFromDropdown().selectByVisibleText("EmailBCCNamesAndAddresses");

			getAddButton().waitAndClick(15);
			base.waitTime(2);

			base.waitForElement(getMappingFIELDCAT7());
			getMappingSOURCEFIELD7().selectFromDropdown().selectByVisibleText("EmailCCNamAndCCAddress");
			getMappingFIELDCAT7().selectFromDropdown().selectByVisibleText("EMAIL");
			getMappingDESTINATIONFIELD7().selectFromDropdown().selectByVisibleText("EmailCCNamesAndAddresses");

			getAddButton().waitAndClick(15);
			base.waitTime(2);

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
	 * @author: Mohan Created Date: 24/02/2022 Modified by: Arunkumar Modified Date:
	 *          28/04/2022
	 * @description: To roll back an Ingestion
	 */
	public void rollBackIngestion() {

		getRefreshButton().waitAndClick(10);
		base.waitTime(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getIngestionSettingGearIcon().Visible();
			}
		}), Input.wait30);
		getIngestionSettingGearIcon().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getIngestionRollbackbutton().Visible();
			}
		}), Input.wait30);
		getIngestionRollbackbutton().waitAndClick(10);
		base.waitTime(3);
		if (getApproveMessageOKButton().isElementAvailable(5)) {
			getApproveMessageOKButton().waitAndClick(5);
		}

		base.VerifySuccessMessage(
				"Rollback of this ingestion has been started. Refresh the page to view for updated status.");
		base.passedStep("Rollback Done Successfully");
	}

	/**
	 * @author: Mohan Created Date: 24/02/2022 Modified by: Arunkumar Modified Date:
	 *          06/04/2022
	 * @description: Verify ingestion at catalog status
	 */
	public void ingestionAtCatlogState(String dataset) {

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
		for (int i = 0; i < 30; i++) {
			base.waitTime(2);
			String status = getStatus(1).getText().trim();
			if (status.contains("Cataloged")) {
				base.passedStep("Cataloged completed");
				break;
			} else if (status.contains("Failed")) {
				System.out.println("Execution aborted!");
				UtilityLog.info("Execution aborted!");
				System.out.println(dataset + " is failed in catalog stage. Take a look and continue!");
				UtilityLog.info(dataset + " is failed in catalog stage. Take a look and continue!");
				break;
			} else {
				base.waitTime(5);
				getRefreshButton().waitAndClick(10);
			}
		}
	}

	/**
	 * @author: Arunkumar Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: this method will perform ingestion with Dat file
	 */
	public void IngestionOnlyForDatFile(String dataset, String DATFile) throws InterruptedException {
		base.stepInfo("Click on add new ingestion button");
		base.waitForElement(getAddanewIngestionButton());
		getAddanewIngestionButton().waitAndClick(10);

		base.stepInfo("Select Source system");
		base.waitForElement(getSpecifySourceSystem());
		getSpecifySourceSystem().selectFromDropdown().selectByVisibleText("TRUE");

		base.stepInfo("Select Location");
		base.waitForElement(getSpecifyLocation());
		base.waitTime(2);
		getSpecifyLocation().selectFromDropdown().selectByVisibleText(Input.SourceLocation);

		base.waitForElement(getSpecifySourceFolder());
		base.waitTime(2);
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
			getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.CJK_FrenchAudioTestDataFolder);
		} else if (dataset.contains("QA_EmailConcatenatedData_SS")) {
			getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.EmailConcatenatedDataFolder);
		} else if (dataset.contains("SSAudioSpeech_Transcript")) {
			getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.SSAudioSpeechFolder);
		} else if (dataset.contains("GD_994_Native_Text_ForProduction")) {
			getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.GD994NativeTextForProductionFolder);
		} else if (dataset.contains("GNon_searchable_PDF_Load_file")) {
			getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.GNonsearchablePDFLoadfileFolder);
		} else if (dataset.contains("HiddenProperties_IngestionData")) {
			getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.HiddenPropertiesFolder);
		} else if (dataset.contains("UniCodeFiles")) {
			getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.UniCodeFilesFolder);
		} else if (dataset.contains("IngestionEmailData")) {
			getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.IngestionEmailDataFolder);
		} else if (dataset.contains("CJK_GermanAudioTestData") || dataset.contains("CJK_JapaneseAudioTestData")) {
			getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.CJK_FrenchAudioTestDataFolder);
		}
		base.waitTime(2);
		base.waitForElement(getDATDelimitersFieldSeparator());
		getDATDelimitersFieldSeparator().selectFromDropdown().selectByVisibleText(Input.fieldSeperator);

		base.waitForElement(getDATDelimitersTextQualifier());
		getDATDelimitersTextQualifier().selectFromDropdown().selectByVisibleText(Input.textQualifier);

		base.waitForElement(getDATDelimitersNewLine());
		getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);

		driver.scrollingToBottomofAPage();

		base.waitForElement(getSourceSelectionDATLoadFile());
		getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(DATFile);
		base.waitForElement(getSourceSelectionDATKey());
		base.waitTime(2);
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
		base.waitTime(2);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMappingSOURCEFIELD2().Visible();
			}
		}), Input.wait30);

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

			
			base.waitTime(2);

			base.waitForElement(getMappingFIELDCAT6());
			getMappingSOURCEFIELD6().selectFromDropdown().selectByVisibleText("EmailBCCNameAndBCCAddress");
			getMappingFIELDCAT6().selectFromDropdown().selectByVisibleText("EMAIL");
			getMappingDESTINATIONFIELD6().selectFromDropdown().selectByVisibleText("EmailBCCNamesAndAddresses");

			
			base.waitTime(2);

			base.waitForElement(getMappingFIELDCAT7());
			getMappingSOURCEFIELD7().selectFromDropdown().selectByVisibleText("EmailCCNamAndCCAddress");
			getMappingFIELDCAT7().selectFromDropdown().selectByVisibleText("EMAIL");
			getMappingDESTINATIONFIELD7().selectFromDropdown().selectByVisibleText("EmailCCNamesAndAddresses");

			
			base.waitTime(2);

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

		} else if (dataset.contains("Tiff_Images")) {

			base.waitForElement(getMappingSOURCEFIELD2());
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("DocID");
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("Datasource");
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");
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
		if(getApproveMessageOKButton().isElementAvailable(5)) {
		getApproveMessageOKButton().waitAndClick(10);
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnRunIngestion().Visible();
			}
		}), Input.wait30);
		getbtnRunIngestion().waitAndClick(10);

	}

	/**
	 * @author: Arunkumar Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: this method will validate the term which present in the copying
	 *               table column
	 */
	public void verifyDataPresentInCopyColumn(String term) {
		getRefreshButton().waitAndClick(10);
		getIngestionDetailPopup(1).waitAndClick(Input.wait30);

		driver.scrollingToElementofAPage(getRunIndexing());
		base.waitForElement(getRunIndexing());
		if (copyTableDataName(term).isDisplayed()) {
			base.passedStep(term + " is displayed in the copying table column");
		} else {
			base.failedStep(term + "is not displayed in the copying table column");
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCloseButton().Enabled();
			}
		}), Input.wait30);
		getCloseButton().waitAndClick(10);

	}

	/**
	 * @author: Arunkumar Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: this method will perform the ingestion process of catalog ad
	 *               copying
	 */
	public void IngestionCatlogtoCopying(String dataset) throws InterruptedException {

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
		for (int i = 0; i < 60; i++) {
			base.waitTime(2);
			String status = getStatus(1).getText().trim();

			if (status.contains("Cataloged")) {
				base.passedStep("Cataloged completed");
				break;
			} else if (status.contains("In Progress")) {
				base.waitTime(5);
				getRefreshButton().waitAndClick(10);
			} else if (status.contains("Failed")) {
				base.failedStep("Ingestion Failed");
			}
		}

		// copy
		getRefreshButton().waitAndClick(10);

		getIngestionDetailPopup(1).waitAndClick(Input.wait30);

		driver.scrollingToElementofAPage(getRunCopying());
		base.waitForElement(getRunCopying());
		getRunCopying().waitAndClick(10);

		base.VerifySuccessMessage("Ingestion copy has Started.");
		UtilityLog.info(dataset + "'s copying is started.");
		driver.waitForPageToBeReady();
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

		getRefreshButton().waitAndClick(10);
		for (int i = 0; i < 90; i++) {
			base.waitTime(2);
			String status = getStatus(1).getText().trim();

			if (status.contains("Copied")) {
				base.passedStep("Copied completed");
				break;
			} else if (status.contains("In Progress")) {
				base.waitTime(5);
				getRefreshButton().waitAndClick(5);
			} else if (status.contains("Failed")) {
				base.failedStep("Ingestion Failed");
			}
		}
	}

	/**
	 * @author: Mohan Created Date: 25/02/2022 Modified by: NA Modified Date: NA
	 * @description: To verify cataloging error with selected DateFormate
	 */
	public void verifyCatalogigErrorForDatSelectDateFormate() {

		driver.waitForPageToBeReady();

		getIngestionDetailPopup(1).waitAndClick(10);
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
		String dateFormate1 = getIngestionWizardDateFormat().getText();
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
	 * @description: Method to process to click on copied state without ignoring
	 *               errors by over lay.
	 */
	public void clickOnCopiedStateWithoutIgnoringErrorsByOverlay(String ingestionName) {
		try {
			driver.scrollPageToTop();
			String status = null;
			for (int i = 0; i < 30; i++) {
				if (getStatusByingestionName(ingestionName).isDisplayed()) {
					status = getStatusByingestionName(ingestionName).getText().trim();
					break;
				} else {
					driver.scrollingToBottomofAPage();
				}
			}
			if (status.contains("Cataloged")) {
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

			for (int i = 0; i < 2000; i++) {
				for (int j = 0; j < 30; j++) {
					if (getStatusByingestionName(ingestionName).isDisplayed()) {
						status = getStatusByingestionName(ingestionName).getText().trim();
						break;
					} else {
						driver.scrollingToBottomofAPage();
					}
				}
				if (status.contains("In Progress")) {
					driver.scrollPageToTop();
					getRefreshButton().isElementAvailable(15);
					getRefreshButton().Click();
				}
				if (status.contains("Passed")) {
					base.passedStep("Ingestion entered to failed state if copied state starts without ignoring errors");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while selecting field catagory and destination field by using source DAT field."
							+ e.getLocalizedMessage());
		}
	}

	/**
	 * @author: Gopinath Created Date: 23/02/2022 Modified by: NA Modified Date: NA
	 * @description: Method to process to click on roll back from catalog stage and
	 *               verify status changed is inprogress.
	 */
	public void verifyInprogressStatusByclickOnRollback(String ingestionName) {
		try {
			driver.scrollPageToTop();
			String status = null;
			for (int i = 0; i < 30; i++) {
				if (getStatusByingestionName(ingestionName).isDisplayed()) {
					status = getStatusByingestionName(ingestionName).getText().trim();
					break;
				} else {
					driver.scrollingToBottomofAPage();
				}
			}

			if (status.contains("Cataloged")) {
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
			if (status.contains("In Progress")) {
				base.passedStep(" Got Ingestion : " + ingestionName + " status is in progess successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while process to click on roll back from catalog stage and verify status changed is inprogress.."
							+ e.getLocalizedMessage());
		}
	}

	/**
	 * @author: Arunkumar Created Date: 16/03/2022 Modified by: NA Modified Date: NA
	 * @description: Method to check next button status when date is selected and
	 *               not selected
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
		getDATDelimitersFieldSeparator().selectFromDropdown().selectByVisibleText(Input.fieldSeperator);

		base.waitForElement(getDATDelimitersTextQualifier());
		getDATDelimitersTextQualifier().selectFromDropdown().selectByVisibleText(Input.textQualifier);

		base.waitForElement(getDATDelimitersNewLine());
		getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);

		base.waitForElement(getSourceSelectionDATKey());

		getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
		getNextButton().waitAndClick(20);
		driver.scrollingToBottomofAPage();
		if (errorMessageMissingDate().isDisplayed()) {
			base.passedStep("Next button is disabled if date format is not selected");
		} else {
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

		if (getApproveMessageOKButton().isDisplayed()) {
			base.passedStep("Next button is enabled if date format is selected");
		} else {
			base.passedStep("Next button is not enabled if date format is selected");
		}
	}

	/**
	 * @author: Arunkumar Created Date: 16/03/2022 Modified by: NA Modified Date: NA
	 * @description: this method will validate the term which present in the copying
	 *               table column
	 * @return: will return the missed doc value of term which displayed in copy
	 *          table columns
	 */
	public int verifyMissedDocValuePresentInCopyTableColumn(String term) {
		getRefreshButton().waitAndClick(10);
		getIngestionDetailPopup(1).waitAndClick(Input.wait30);

		driver.scrollingToElementofAPage(getRunIndexing());
		base.waitForElement(getRunIndexing());

		if (copyTableDataValue(term, 4).isDisplayed()) {
			base.passedStep(term + "count is displayed in the copying table column");
		} else {
			base.failedStep(term + "count is not displayed in the copying table column");
		}
		int value = Integer.parseInt(copyTableDataValue(term, 4).getText());
		getCloseButton().waitAndClick(10);
		return value;
	}

	/**
	 * @author: Arunkumar Created Date: 17/03/2022 Modified by: NA Modified Date: NA
	 * @description: this method will validate the date format in ingestion field
	 *               cataloging stage
	 */
	public void verifyExpectedDateFormatAfterCatalogingStage() {

		driver.waitForPageToBeReady();
		String dateFormat = getIngestionWizardDateFormat().getText();
		String firstSectionInDateFormat[] = dateFormat.split("/");
		int firstsectionLength = firstSectionInDateFormat[0].length();
		int dateFormatTotalLength = dateFormat.length();

		if (dateFormatTotalLength == 19 && firstsectionLength == 4) {
			base.passedStep(" ingestion converted the provided data into the Sightline desired/expected date format ");
		} else {
			base.failedStep(
					"ingestion not converted the provided data into the Sightline desired/expected date format");
		}
	}

	/**
	 * @author: Arunkumar Created Date: 18/03/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify the error when selected date format is
	 *               different than in DAT.
	 */
	public void verifyCatalogingErrorIfDateFormatIsDifferentThanDAT() {

		driver.waitForPageToBeReady();

		String status = getStatus(1).getText().trim();
		if (status.contains("Failed")) {
			int numberOfErrors = Integer.parseInt(errorCountStatus().getText());

			getIngestionDetailPopup(1).waitAndClick(10);

			base.waitForElement(errorCountCatalogingStage());
			errorCountCatalogingStage().waitAndClick(10);
			base.waitTime(5);
			base.waitForElement(ignoreAllButton());

			for (int i = 1; i <= numberOfErrors; i++) {

				if (ingestionErrorNote(i).getText().contains(Input.differentDateFormatError)) {
					base.passedStep("Cataloging Error displayed when selected date format different than in DAT");
					break;
				} else {
					System.out.println("Error not belonged to date format");
				}
			}
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getCloseButton().Enabled();
				}
			}), Input.wait30);
			getCloseButton().waitAndClick(10);

		} else if (status.contains("Cataloged")) {
			base.failedStep("No Errors and Selected Date format is same as in DAT");

		}

	}

	/**
	 * @author: Gopinath Created Date: NA Modified by: NA Modified Date: NA
	 * @description: Method to create ingestion to failed stage
	 */
	public String ingestionCreationToFailedState() {
		String title = null;
		try {
			String titleCar = null;
			int count = 0;
			driver.waitForPageToBeReady();
			base.waitForElement(getStatus());
			for (int i = 1; i < 500; i++) {
				driver.waitForPageToBeReady();
				getIngestionTitle(count + 1).ScrollTo();
				getIngestionTitle(count + 1).isElementAvailable(15);
				title = getIngestionTitle(count + 1).GetAttribute("title").trim();
				getStatus(count + 1).isElementAvailable(15);
				String status = getStatus(count + 1).getText().trim();
				driver.waitForPageToBeReady();
				for (int j = 1; j < 50; j++) {
					titleCar = getIngestionTitle(j).GetAttribute("title").trim();
					getIngestionTitle(j).ScrollTo();
					if (titleCar.equalsIgnoreCase(title)) {
						if (j != 1) {
							count = j - 1;
						}
						break;
					} else {
						driver.scrollingToBottomofAPage();
					}
				}
				if (status.contains("In Progress")) {
					driver.scrollPageToTop();
					getRefreshButton().isElementAvailable(15);
					getRefreshButton().Click();
				}
				if (status.contains("Failed") && titleCar.equalsIgnoreCase(title)) {
					base.passedStep("Ingestion is failed as per expected.");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while creating ingestion to failed stage." + e.getLocalizedMessage());
		}
		return title;
	}

	/**
	 * @author: Arun Created Date: 22/03/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify ingestion status after ignoring all the
	 *               errors
	 */

	public void verifyIgnoringErrorsAndContinueIngestion() {

		getIngestionDetailPopup(1).waitAndClick(10);

		base.waitForElement(errorCountCatalogingStage());
		errorCountCatalogingStage().waitAndClick(10);
		base.waitForElement(ignoreAllButton());
		ignoreAllButton().waitAndClick(10);
		if (getApproveMessageOKButton().isElementAvailable(5)) {
			getApproveMessageOKButton().waitAndClick(10);
			base.passedStep("Clicked on OK button to ignore all errors");
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return doneButton().Enabled();
			}
		}), Input.wait30);
		doneButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCloseButton().Enabled();
			}
		}), Input.wait30);
		getCloseButton().waitAndClick(10);
		base.VerifySuccessMessage("Action done successfully");
		base.waitTime(2);
		getRefreshButton().waitAndClick(10);

		// catlogging
		for (int i = 0; i < 20; i++) {
			getRefreshButton().waitAndClick(10);
			if (getCatalogedIngestionStatus().isElementAvailable(5)) {
				base.passedStep("Cataloged completed");
				base.passedStep("Ingestion Continued successfully after ignoring errors");
				break;
			} else if (getInprogressIngestionStatus().isElementAvailable(5)) {
				base.waitTime(20);
				getRefreshButton().waitAndClick(10);
			} else if (getFailedIngestionStatus().isElementAvailable(5)) {
				base.passedStep("Ingestion not continued successfully after ignoring errors");
			}
		}
	}

	/**
	 * @author: Arun Created Date: 23/03/2022 Modified by: NA Modified Date: NA
	 * @description: this method will enter source selection and ingestion type
	 *               section
	 */
	public void sourceSelectionAndIngestionTypeSectionOnlyWithDATfile(String dataset, String DATFile) {

		base.stepInfo("Click on add new ingestion button");
		base.waitForElement(getAddanewIngestionButton());
		getAddanewIngestionButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(getIngestion_IngestionType());
		getIngestion_IngestionType().selectFromDropdown().selectByVisibleText(Input.ingestionType);
		base.waitTime(2);
		base.stepInfo("Select Source system");
		base.waitForElement(getSpecifySourceSystem());
		getSpecifySourceSystem().selectFromDropdown().selectByVisibleText("TRUE");

		base.stepInfo("Select Location");
		base.waitForElement(getSpecifyLocation());
		base.waitTime(2);
		getSpecifyLocation().selectFromDropdown().selectByVisibleText(Input.SourceLocation);

		base.waitForElement(getSpecifySourceFolder());
		base.waitTime(2);
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
		base.waitTime(2);
		base.waitForElement(getDATDelimitersFieldSeparator());
		getDATDelimitersFieldSeparator().selectFromDropdown().selectByVisibleText(Input.fieldSeperator);

		base.waitForElement(getDATDelimitersTextQualifier());
		getDATDelimitersTextQualifier().selectFromDropdown().selectByVisibleText(Input.textQualifier);

		base.waitForElement(getDATDelimitersNewLine());
		getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);

		driver.scrollingToBottomofAPage();

		base.waitForElement(getSourceSelectionDATLoadFile());

		getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(DATFile);

		base.waitForElement(getSourceSelectionDATKey());
		base.waitTime(1);

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

	}

	/**
	 * @author: Arun Created Date: 23/03/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify the source selection and ingestion type
	 *               section after clicking next button
	 */

	public void verifySourceSectionStatusAfterClickingNextButton() {

		driver.waitForPageToBeReady();
		base.waitTime(2);
		String sourceSystemStatus = getSpecifySourceSystem().GetAttribute("disabled");
		String ingestionTypeStatus = getIngestion_IngestionType().GetAttribute("disabled");
		String nextButtonStatus = getNextButton().GetAttribute("disabled");

		if (getPreviewRun().Enabled()) {
			base.passedStep("Configure mapping section enabled");
		} else {
			base.failedStep("Configure mapping section not enabled");
		}

		if (sourceSystemStatus.equalsIgnoreCase("true") && ingestionTypeStatus.equalsIgnoreCase("true")) {
			base.passedStep("Source and overwrite setting page disabled");
		} else {
			base.failedStep("Source and overwrite setting page not disabled");
		}

		if (nextButtonStatus.equalsIgnoreCase("true")) {
			base.passedStep("Next button in source section is disabled and in non editable state");
		} else {
			base.failedStep("Next button is enabled");
		}

	}

	/**
	 * @author: Arun Created Date: 23/03/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify the count matching in header section
	 *               pop up with mapping section
	 */
	public void verifyHeaderCountInPreviewRecordPopupPage() {
		driver.waitForPageToBeReady();

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

		int headerSectionCount = previewRecordPopupHeaderFields().size();

		int mappedFieldCount = mappedSourceFields(1).size();

		if (mappedFieldCount == headerSectionCount) {
			base.passedStep(
					"Headers in preview record popup page count matched with mapped field in configuring section");
		} else {
			base.failedStep(
					"Headers in preview record popup page count not matched with mapped field in configuring section");
		}

	}

	/**
	 * @author: Arun Created Date: 25/03/2022 Modified by: NA Modified Date: NA
	 * @description: this method will perform catalogging
	 */
	public void ingestionCatalogging() {

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
			base.waitTime(2);
			String status = getStatus(1).getText().trim();

			if (status.contains("Cataloged")) {
				base.passedStep("Cataloged completed");
				break;
			} else if (status.contains("In Progress")) {
				base.waitTime(10);
				getRefreshButton().waitAndClick(5);
			} else if (status.contains("Failed")) {
				base.failedStep("Failed");
			}
		}
	}

	/**
	 * @author: Arun Created Date: 24/03/2022 Modified by: NA Modified Date: NA
	 * @description: this method will perform indexing
	 */
	public void ingestionIndexing(String dataset) {

		getRefreshButton().waitAndClick(10);

		getIngestionDetailPopup(1).waitAndClick(Input.wait30);
		base.waitTime(2);
		driver.scrollingToElementofAPage(getRunIndexing());

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

		getRunIndexing().waitAndClick(10);
		base.waitTime(2);
		base.VerifySuccessMessage("Ingestion Indexing has Started.");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCloseButton().Enabled();
			}
		}), Input.wait30);
		getCloseButton().waitAndClick(10);
		base.waitTime(2);
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
		getRefreshButton().waitAndClick(5);
		for (int i = 0; i < 50; i++) {
			base.waitTime(2);
			String status = getStatus(1).getText().trim();
			if (status.contains("Indexed")) {
				base.passedStep("Indexing completed");
				break;
			} else if (status.contains("failed")) {
				base.failedStep("Ingestion failed");
			} else {
				base.waitTime(10);
				getRefreshButton().waitAndClick(5);
			}
		}

	}

	/**
	 * @author: Arun Created Date: 24/03/2022 Modified by: NA Modified Date: NA
	 * @description: this method willverify ingestion details status after rollback
	 *               the ingestion at indexing stage
	 */

	public void verifyIngestionDetailsTillIndexingAfterRollback() {

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

		getRefreshButton().waitAndClick(10);
		driver.waitForPageToBeReady();

		for (int i = 0; i < 60; i++) {
			base.waitTime(3);
			String status = getStatus(1).getText().trim();

			if (status.contains("Draft")) {
				base.passedStep("Draft completed");
				break;
			} else if (status.contains("In Progress")) {
				base.waitTime(5);
				getRefreshButton().waitAndClick(10);
			} else {
				base.failedStep("rollback failed");
			}
		}
		getIngestionDetailPopup(1).waitAndClick(10);

		driver.waitForPageToBeReady();

		String catalogData = catalogSectionDetails().getText();
		String copyData = catalogSectionDetails().getText();
		String indexData = indexingSectionDetails().getText();

		if (catalogData.trim().equals("") && copyData.trim().equals("") && indexData.trim().equals("")) {
			base.passedStep("Cataloging,Copying and Indexing field is blank");
		} else {
			base.failedStep("Cataloging,Copying and Indexing field is not blank");
		}

	}

	/**
	 * @author: Arun Created Date: 25/03/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify deletion of ingestion after saving as
	 *               draft
	 */

	public void verifyIngestionSaveAsDraftAndDelete() {
		driver.waitForPageToBeReady();
		base.waitForElement(getIngestion_SaveAsDraft());
		getIngestion_SaveAsDraft().waitAndClick(5);

		if (getApproveMessageOKButton().isElementAvailable(5)) {
			getApproveMessageOKButton().waitAndClick(10);
			base.passedStep("Clicked on OK button to save as draft");
		}

		base.VerifySuccessMessage("Your changes to the ingestion were successfully saved.");
		navigateToIngestionPage();

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

		getRefreshButton().waitAndClick(10);
		driver.waitForPageToBeReady();

		for (int i = 0; i < 60; i++) {
			base.waitTime(2);
			String status = getStatus(1).getText().trim();

			if (status.contains("Draft")) {
				base.passedStep("Draft completed");
				break;
			} else if (status.contains("In Progress")) {
				base.waitTime(5);
				getRefreshButton().waitAndClick(10);
			} else {
				base.failedStep("Draft failed");
			}
		}
		getIngestionDetailPopup(1).waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return ingestionDetailActionDropdown().Visible();
			}
		}), Input.wait30);
		ingestionDetailActionDropdown().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return deleteActionButton().Visible();
			}
		}), Input.wait30);
		deleteActionButton().waitAndClick(10);

		if (getApproveMessageOKButton().isElementAvailable(5)) {
			getApproveMessageOKButton().waitAndClick(10);
			base.passedStep("Clicked on OK button to Delete the ingestion");
		}

		base.VerifySuccessMessage("Ingestion deleted successfully.");

	}

	/**
	 * @author: Arun Created Date: 28/03/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify mapping field selection after clicking
	 *               back button
	 */

	public void verifyMappingFiledPriorSelection(String Field1, String Field2, String Field3) {

		driver.waitForPageToBeReady();
		getPreviewRun().waitAndClick(10);
		base.VerifyErrorMessage("Please specify a mapping for the mandatory fields.");

		getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText(Field1);
		getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText(Field2);
		getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText(Field3);

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

		goBackButton().waitAndClick(5);

		String mappedParentDocID = getMappingSOURCEFIELD2().selectFromDropdown().getFirstSelectedOption().getText();
		String mappedDataSource = getMappingSOURCEFIELD3().selectFromDropdown().getFirstSelectedOption().getText();
		String mappedCustodian = getMappingSOURCEFIELD4().selectFromDropdown().getFirstSelectedOption().getText();

		if (mappedParentDocID.equalsIgnoreCase(Field1) && mappedDataSource.equalsIgnoreCase(Field2)
				&& mappedCustodian.equalsIgnoreCase(Field3)) {
			base.passedStep("Mapped fields displayed with prior selection");
		} else {
			base.failedStep("Mapped fields not displayed with prior selection");
		}
	}

	/**
	 * @author: Arun Created Date: 29/03/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify ingestion status after saving as draft
	 */

	public void verifyIngestionStatusAfterSaveAsDraft() {
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		base.waitForElement(getIngestion_SaveAsDraft());
		getIngestion_SaveAsDraft().waitAndClick(5);

		if (getApproveMessageOKButton().isElementAvailable(5)) {
			getApproveMessageOKButton().waitAndClick(10);
			base.passedStep("Clicked on OK button to save as draft");
		}

		base.VerifySuccessMessage("Your changes to the ingestion were successfully saved.");
		navigateToIngestionPage();

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

		getRefreshButton().waitAndClick(10);
		driver.waitForPageToBeReady();

		for (int i = 0; i < 60; i++) {
			base.waitTime(2);
			String status = getStatus(1).getText().trim();

			if (status.contains("Draft")) {
				base.passedStep("Draft completed");
				break;
			} else if (status.contains("In Progress")) {
				base.waitTime(5);
				getRefreshButton().waitAndClick(10);
			} else {
				base.failedStep("Draft failed");
			}
		}
		getIngestionSettingGearIcon().waitAndClick(10);

		String rollbackButtonStatus = rollbackOption().GetAttribute("class");
		System.out.println(rollbackButtonStatus);
		if (rollbackButtonStatus.equalsIgnoreCase("disable")) {
			base.passedStep("Rollback button is disabled in draft mode");
		} else {
			base.failedMessage("Rollback button not disabled in draft mode");
		}
		getRefreshButton().waitAndClick(10);

	}

	/**
	 * @author: Arun Created Date: 29/03/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify ingestion status after saving as draft
	 */

	public void verifyDraftModeStatusAfterRollbackIngestion() {

		rollBackIngestion();
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

		getRefreshButton().waitAndClick(10);
		driver.waitForPageToBeReady();

		for (int i = 0; i < 60; i++) {
			base.waitTime(2);
			String status = getStatus(1).getText().trim();

			if (status.contains("Draft")) {
				base.passedStep("After Rollback ,ingestion moved to Draft mode");
				break;
			} else if (status.contains("In Progress")) {
				base.waitTime(5);
				getRefreshButton().waitAndClick(10);
			} else {
				base.failedStep("After Rollback ,ingestion not moved to Draft mode");
			}
		}
	}

	/**
	 * @author: Arun Created Date: 29/03/2022 Modified by: NA Modified Date: NA
	 * @description: this method will perform ingestion again from draft mode
	 */

	public void IngestionFromDraftMode() {

		driver.waitForPageToBeReady();
		getIngestionDetailPopup(1).waitAndClick(5);
		base.waitForElement(getActionDropdownArrow());
		getActionDropdownArrow().waitAndClick(5);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getActionOpenWizard().Visible();
			}
		}), Input.wait30);

		getActionOpenWizard().waitAndClick(5);
		base.waitTime(3);
		base.stepInfo("Starting ingestion again from draft mode");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNextButton().Visible();
			}
		}), Input.wait30);
		getNextButton().waitAndClick(10);
		base.passedStep("Clicked on Next button");

		base.stepInfo("Pop up message for Ingestion without text file");
		if (getApproveMessageOKButton().isElementAvailable(10)) {
			getApproveMessageOKButton().waitAndClick(10);
			base.passedStep("Clicked on OK button to continue without text files");
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPreviewRun().Visible();
			}
		}), Input.wait30);
		getPreviewRun().waitAndClick(10);

		if (getApproveMessageOKButton().isElementAvailable(10)) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getApproveMessageOKButton().Visible();
				}
			}), Input.wait30);
			getApproveMessageOKButton().waitAndClick(10);
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnRunIngestion().Visible();
			}
		}), Input.wait30);
		getbtnRunIngestion().waitAndClick(10);

	}

	/**
	 * @author: Arun Created Date: 30/03/2022 Modified by: NA Modified Date: NA
	 * @description: this method will perform catalogging and copying for two
	 *               ingestion
	 */

	public void multipleIngestionCopying(int numberOfIngestion) {

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
		for (int j = 1; j <= numberOfIngestion; j++) {
			for (int i = 0; i < 60; i++) {
				base.waitTime(2);
				String status = getStatus(j).getText().trim();

				if (status.contains("Cataloged")) {
					base.passedStep("Cataloged completed for ingestion - " + j + "");
					break;
				} else if (status.contains("In Progress")) {
					base.waitTime(5);
					getRefreshButton().waitAndClick(5);
				} else if (status.contains("Failed")) {
					base.failedStep("Cataloged Failed for ingestion - " + j + "");
				}
			}
		}

		// copy
		for (int j = 1; j <= numberOfIngestion; j++) {

			getIngestionDetailPopup(j).waitAndClick(Input.wait30);

			driver.scrollingToElementofAPage(getRunCopying());
			base.waitForElement(getRunCopying());
			getRunCopying().waitAndClick(10);

			base.VerifySuccessMessage("Ingestion copy has Started.");

			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getCloseButton().Enabled();
				}
			}), Input.wait30);
			getCloseButton().waitAndClick(10);
		}

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

		getRefreshButton().waitAndClick(10);
		for (int k = 1; k <= numberOfIngestion; k++) {
			for (int i = 0; i < 40; i++) {
				base.waitTime(2);
				String status = getStatus(k).getText().trim();

				if (status.contains("Copied")) {
					base.passedStep("Copied completed for ingestion - " + k + "");
					break;
				} else if (status.contains("In Progress")) {
					base.waitTime(10);
					getRefreshButton().waitAndClick(5);
				} else if (status.contains("Failed")) {
					base.failedStep("copied Failed for ingestion - " + k + "");
				}
			}
		}
	}

	/**
	 * @author: Arun Created Date: 30/03/2022 Modified by: NA Modified Date: NA
	 * @description: this method will perform indexing for more than one ingestion
	 */
	public void multipleIngestionIndexing(String dataset[], int numberOfIngestion) {

		getRefreshButton().waitAndClick(5);

		for (int i = 1; i <= numberOfIngestion; i++) {
			getIngestionDetailPopup(i).waitAndClick(Input.wait30);

			driver.scrollingToElementofAPage(getMP3Count());

			if (dataset[i - 1].contains("AllSources") || dataset[i - 1].contains("SSAudioSpeech_Transcript")) {

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
			} else if (dataset[i - 1].contains("CJK_GermanAudioTestData")) {

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
			} else if (dataset[i - 1].contains("CJK_JapaneseAudioTestData")) {

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
			} else if (dataset[i - 1].contains("0002_H13696_1_Latest")) {

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
		}

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
		getRefreshButton().waitAndClick(5);
		for (int j = 1; j <= numberOfIngestion; j++) {
			for (int i = 0; i < 50; i++) {
				base.waitTime(2);
				String status = getStatus(j).getText().trim();
				if (status.contains("Indexed")) {
					base.passedStep("Indexing completed for ingestion - " + j + "");
					break;
				} else if (status.contains("failed")) {
					base.failedStep("Indexing failed for ingestion - " + j + "");
				} else {
					base.waitTime(10);
					getRefreshButton().waitAndClick(5);
				}
			}
		}
	}

	/**
	 * @author: Arun Created Date: 31/03/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify the indexing warning message and
	 *               rollback
	 */
	public void verifyWarningMessageAndRollbackAddOnlyIngestion() {

		getRefreshButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		getIngestionDetailPopup(1).waitAndClick(Input.wait30);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return ingestionDetailActionDropdown().Visible();
			}
		}), Input.wait30);
		ingestionDetailActionDropdown().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return rollbackOptionInPopup().Visible();
			}
		}), Input.wait30);
		rollbackOptionInPopup().waitAndClick(10);
		base.waitTime(2);
		String warningMessage = getRollbackWarningMessage().getText();

		if (warningMessage.equalsIgnoreCase(Input.indexingWarningMessage)) {
			base.failedStep("Indexing warning message prompted when rollingback add only ingestion");
		} else {
			base.passedStep("Indexing warning message not prompted when rollingback add only ingestion");
		}
		if (getApproveMessageOKButton().isElementAvailable(5)) {
			getApproveMessageOKButton().waitAndClick(5);
		}

		base.VerifySuccessMessage(
				"Rollback of this ingestion has been started. Refresh the page to view for updated status.");
		base.passedStep("Rollback Done Successfully");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCloseButton().Enabled();
			}
		}), Input.wait30);
		getCloseButton().waitAndClick(5);

	}

	/**
	 * @author: Arun Created Date: 04/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify the details in ingestion pop up
	 */
	public void verifyIngestionDetails() {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByButton().Visible();
			}
		}), Input.wait30);
		getFilterByButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByINPROGRESS().Visible();
			}
		}), Input.wait30);
		getFilterByINPROGRESS().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByAPPROVED().Visible();
			}
		}), Input.wait30);
		getFilterByINDEXED().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByAPPROVED().Visible();
			}
		}), Input.wait30);
		getFilterByAPPROVED().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByPUBLISHED().Visible();
			}
		}), Input.wait30);
		getFilterByPUBLISHED().waitAndClick(10);

		getRefreshButton().waitAndClick(5);

		String viewStatus = getIngestion_TileView().GetAttribute("class");
		if (viewStatus.equalsIgnoreCase("active")) {
			base.passedStep("Information displayed on Tiles view");
		} else {
			base.failedMessage("Information displayed on grid view");

		}

		if (getStatus(1).isElementPresent()) {
			int sourceCount = Integer.parseInt(getSourceCount().getText());
			int ingestedCount = Integer.parseInt(getIngestedCount().getText());
			int errorCount = Integer.parseInt(errorCountStatus().getText());
			if (sourceCount >= 0 && ingestedCount >= 0 && errorCount == 0) {
				base.passedStep("Source , Ingested and Error count details displayed");
			} else {
				base.failedStep("Source,Ingested and Error count details not displayed");
			}
			getIngestionDetailPopup(1).waitAndClick(Input.wait30);
			base.waitTime(2);

			String projectName = getProjectNameInPopup().getText();
			String ingestionStatus = getIngestionStatusInPopup().getText();
			String timeStamp = getTimeStampInPopup().getText();

			if (projectName.trim().equals("") && ingestionStatus.trim().equals("") && timeStamp.trim().equals("")) {
				base.failedStep("project name, ingestion Status and Time stamp details are blank");
			} else {
				base.passedStep("Project name, ingestion Status and Time stamp details are present");
			}

			String catalogData = catalogSectionDetails().getText();
			String copyData = catalogSectionDetails().getText();
			String indexData = indexingSectionDetails().getText();

			if (catalogData.trim().equals("") && copyData.trim().equals("") && indexData.trim().equals("")) {
				base.failedStep("Cataloging,Copying and Indexing field details are blank");
			} else {
				base.passedStep("Cataloging,Copying and Indexing field details are present");
			}

		} else {
			base.failedStep("Ingestion details not present");
		}
	}

	/**
	 * @author: Arun Created Date: 04/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify the availability of rollback option
	 */
	public void verifyRollbackOptionStatus() {

		getRefreshButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		getIngestionDetailPopup(1).waitAndClick(Input.wait30);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return ingestionDetailActionDropdown().Visible();
			}
		}), Input.wait30);
		ingestionDetailActionDropdown().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return rollbackOptionInPopup().Visible();
			}
		}), Input.wait30);
		rollbackOptionInPopup().waitAndClick(10);
		base.waitTime(2);
		if (getRollbackWarningMessage().isElementAvailable(5)) {
			base.passedStep("Rollback option is displayed and available to perform rollback action");
		} else {
			base.failedStep("Rollback option is not available");
		}
		if (getApproveMessageCancelButton().isElementAvailable(5)) {
			getApproveMessageCancelButton().waitAndClick(5);
		}
		base.waitForElement(getCloseButton());
		getCloseButton().waitAndClick(5);

	}

	/**
	 * @author: Arun Created Date: 05/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify the Navigation control in ingestion
	 *               home page
	 */
	public void verifyHomePageNavigationControl() {
		driver.waitForPageToBeReady();
		base.waitForElement(showAllIngestion());
		showAllIngestion().waitAndClick(5);
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
				return getFilterByINDEXED().Visible();
			}
		}), Input.wait30);
		getFilterByINDEXED().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByAPPROVED().Visible();
			}
		}), Input.wait30);
		getFilterByAPPROVED().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByPUBLISHED().Visible();
			}
		}), Input.wait30);
		getFilterByPUBLISHED().waitAndClick(10);

		getRefreshButton().waitAndClick(5);

		getIngestion_GridView().waitAndClick(5);
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();

		try {
			String nextbuttonStatus = ingestionPaginationNext().GetAttribute("class").trim();
			String previousbuttonStatus = ingestionPaginationPrevious().GetAttribute("class").trim();
			if (nextbuttonStatus.equalsIgnoreCase("paginate_button next disabled")
					&& previousbuttonStatus.equalsIgnoreCase("paginate_button previous disabled")) {
				base.passedStep("only one page available in ingestion home page");
			} else {
				int currentPageNumber = Integer.parseInt(currentActivePage().getText());
				ingestionNextButton().waitAndClick(5);
				base.stepInfo("Clicked Next button");
				base.waitTime(2);
				int currentPageNumberAfterNext = Integer.parseInt(currentActivePage().getText());
				if (currentPageNumberAfterNext > currentPageNumber) {
					base.passedStep("After clicking next button it displays next page");
				} else {
					base.failedStep("After clicking next button , next page not displayed");
				}
				ingestionPreviousButton().waitAndClick(5);
				base.stepInfo("Clicked Previous button");
				base.waitTime(2);
				int currentPageNumberAfterPrevious = Integer.parseInt(currentActivePage().getText());
				if (currentPageNumberAfterPrevious == currentPageNumber) {
					base.passedStep("After clicking previous button it displays previous page");
				} else {
					base.failedStep("After clicking previous button , previous page not displayed");
				}
				moveToNextPage().waitAndClick(5);
				base.stepInfo("Clicked Next available page number");
				base.waitTime(2);
				int currentPageAfterPagination = Integer.parseInt(currentActivePage().getText());
				if (currentPageAfterPagination > currentPageNumber) {
					base.passedStep("After clicking next available page number , it displays particular page");
				} else {
					base.failedStep("After clicking page number ,particular page not displayed");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while navigating page in ingestion home page");
		}

	}

	/**
	 * @author: Arun Created Date: 05/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify the contents present in the ingestion
	 *               tiles
	 */

	public void verifyContentOnIngestionTiles() {

		getRefreshButton().waitAndClick(5);
		base.waitTime(2);

		if (getIngestionDetailPopup(1).isElementAvailable(5)) {
			int sourceCount = Integer.parseInt(getSourceCount().getText());
			int ingestedCount = Integer.parseInt(getIngestedCount().getText());
			int errorCount = Integer.parseInt(errorCountStatus().getText());
			String status = getStatus(1).getText().trim();
			if (sourceCount > 0 && ingestedCount >= 0 && errorCount >= 0) {
				base.passedStep("Source , Ingested and Error count details displayed");
			} else {
				base.failedStep("Source,Ingested and Error count details not displayed");
			}
			if (status.contains("Cataloged") || status.contains("Failed")) {
				base.passedStep("Ingestion Status details are displayed");
			} else {
				base.failedStep("Ingestion Status details are not displayed");
			}
			if (ingestionCompletedDate().isDisplayed() && ingestionProgressBar().isDisplayed()
					&& ingestionModifiedUser().isDisplayed()) {
				base.passedStep("Latest time stamp.modified admin name and progress bar present");
			} else {
				base.failedStep("Latest time stamp.modified admin name  and progress bar not present");
			}
		} else {
			base.failedMessage("No ingestion is present in Failed/Cataloged state");
		}
	}

	/**
	 * @author: Arun Created Date: 06/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will perform Copying stage ingestion
	 */
	public void ingestionCopying() {

		base.waitTime(2);
		getIngestionDetailPopup(1).waitAndClick(10);
		driver.scrollingToElementofAPage(getRunCopying());
		base.waitForElement(getRunCopying());
		getRunCopying().waitAndClick(10);

		driver.waitForPageToBeReady();
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
				return getFilterByCATALOGED().Visible();
			}
		}), Input.wait30);
		getFilterByCOPIED().waitAndClick(10);

		// copying
		for (int i = 0; i < 50; i++) {
			getRefreshButton().Click();
			base.waitTime(2);
			String status = getStatus(1).getText().trim();

			if (status.contains("Copied")) {
				base.passedStep("Copied completed");
				break;
			} else if (status.contains("In Progress")) {
				base.waitTime(10);
				getRefreshButton().waitAndClick(5);
			} else if (status.contains("Failed")) {
				base.failedStep("Failed");
			}
		}
	}

	/**
	 * @author: Arun Created Date: 06/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify ignore option and checkbox in ingestion
	 *               error list
	 */
	public void verifyIgnoreOptionAndCheckbox() {
		getIngestionDetailPopup(1).waitAndClick(5);
		base.waitTime(1);
		driver.scrollingToElementofAPage(errorCountCatalogingStage());
		base.waitForElement(errorCountCatalogingStage());
		errorCountCatalogingStage().waitAndClick(10);
		base.waitForElement(ignoreOptionInErrorList());
		String type = getIgnoreOptionSelection().GetAttribute("type").trim();

		if (ignoreOptionInErrorList().isDisplayed() && type.equalsIgnoreCase("checkbox")) {
			base.passedStep("Ignore option displayed with checkbox in error list");
		} else {
			base.failedStep("Ignore option not displayed with check box in error list");
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCloseButton().Enabled();
			}
		}), Input.wait30);
		getCloseButton().waitAndClick(10);
	}

	/**
	 * @author: Arun Created Date: 08/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify perform the approval stage ingestion
	 */
	public void approveIngestion(int numberofingestion) {

		driver.waitForPageToBeReady();
		int j = numberofingestion;
		getIngestionDetailPopup(j).waitAndClick(Input.wait30);
		base.waitTime(1);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return ingestionDetailActionDropdown().Visible();
			}
		}), Input.wait30);
		ingestionDetailActionDropdown().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getActionApprove().Visible();
			}
		}), Input.wait30);
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
		getFilterByAPPROVED().waitAndClick(10);

		getRefreshButton().waitAndClick(10);

		for (int i = 0; i < 40; i++) {
			base.waitTime(2);
			String status = getStatus(j).getText().trim();

			if (status.contains("Approved")) {
				base.passedStep("Approve completed for ingestion");
				break;
			} else if (status.contains("In Progress")) {
				base.waitTime(10);
				getRefreshButton().waitAndClick(5);
			} else if (status.contains("Failed")) {
				base.failedStep("Approve Failed for ingestion");
			}
		}
	}

	/**
	 * @author: Arun Created Date: 08/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify rollback status for approved ingestion
	 */
	public void verifyRollbackOptionForApprovedIngestion() {

		getRefreshButton().waitAndClick(10);
		getIngestionDetailPopup(1).waitAndClick(Input.wait30);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return ingestionDetailActionDropdown().Visible();
			}
		}), Input.wait30);
		ingestionDetailActionDropdown().waitAndClick(10);

		String status = rollbackButtonStatus().GetAttribute("class").trim();
		if (status.contains("disable")) {
			base.passedStep("Rollback option not available for approved ingestion");
		} else {
			base.failedStep("Rollback option available for approved ingestion");
		}

	}

	/**
	 * @author: Gopinath Created Date: 12/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method is used to get name of ingestion created.
	 * @return createdIngestionName : createdIngestionName is String value that
	 *         returns created ingestion name.
	 */
	public String getNameOfIngestionCreated() {
		String createdIngestionName = null;
		try {
			driver.waitForPageToBeReady();
			base.waitTime(2);
			firstTileTitle().isElementAvailable(15);
			createdIngestionName = firstTileTitle().GetAttribute("title").trim();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while getting name of ingestion created." + e.getLocalizedMessage());
		}
		return createdIngestionName;
	}

	/**
	 * @author: Gopinath Created Date: 12/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method is used to ignore erros in catalog stage and
	 *               continue to copied state.
	 * @param ingestionName : ingestionName is String value that name of ingestion
	 *                      created.
	 */
	public void ignoreErrorsInCatalogStageAndContinueToCopiedState(String ingestionName) {
		try {
			String status = null;
			driver.waitForPageToBeReady();
			base.waitTime(2);
			getIngestionLinkByName(ingestionName).isElementAvailable(15);
			getIngestionLinkByName(ingestionName).Click();
			driver.waitForPageToBeReady();
			base.waitTime(2);
			errorsCount().ScrollTo();
			errorsCount().isElementAvailable(15);
			errorsCount().Click();
			ignoreAllButon().isElementAvailable(15);
			ignoreAllButon().Click();
			base.waitTime(2);
			getApproveMessageOKButton().isElementAvailable(10);
			getApproveMessageOKButton().Click();
			driver.waitForPageToBeReady();
			catalogDone().isElementAvailable(15);
			catalogDone().Click();
			base.VerifySuccessMessage("Action done successfully");
			base.waitTime(2);
			driver.waitForPageToBeReady();
			startCoping().ScrollTo();
			startCoping().isElementAvailable(10);
			startCoping().Click();
			driver.scrollPageToTop();
			getCloseButton().isElementAvailable(15);
			getCloseButton().Click();
			base.waitTime(2);
			for (int i = 0; i < 8000; i++) {
				driver.scrollPageToTop();
				getRefreshButton().isElementAvailable(15);
				getRefreshButton().Click();
				driver.waitForPageToBeReady();
				statusOfIngestion(ingestionName).isElementAvailable(15);
				status = statusOfIngestion(ingestionName).getText();
				if (status.contains("Copied")) {
					base.passedStep("Errors are ignored and shifted to copied state successfully");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while ignoring erros in catalog stage and continue to copied state."
					+ e.getLocalizedMessage());
		}
	}

	/**
	 * @author: Gopinath Created Date: 12/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method is used to shift copied stage to indexed stage by
	 *               without marking audio optional
	 * @param ingestionName : ingestionName is String value that name of ingestion
	 *                      created.
	 */
	public void copiedStageToIndexedStateByWithoutAudioOptional(String ingestionName) {
		try {
			String status = null;
			driver.waitForPageToBeReady();
			base.waitTime(2);
			getIngestionLinkByName(ingestionName).isElementAvailable(15);
			getIngestionLinkByName(ingestionName).Click();
			driver.waitForPageToBeReady();
			base.waitTime(2);
			startIndexing().ScrollTo();
			startIndexing().isElementAvailable(15);
			startIndexing().Click();
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			getCloseButton().isElementAvailable(15);
			getCloseButton().Click();
			base.waitTime(2);
			for (int i = 0; i < 8000; i++) {
				driver.scrollPageToTop();
				getRefreshButton().isElementAvailable(15);
				getRefreshButton().Click();
				driver.waitForPageToBeReady();
				statusOfIngestion(ingestionName).isElementAvailable(15);
				status = statusOfIngestion(ingestionName).getText();
				if (status.contains("Indexed")) {
					base.passedStep("Copied stage to indexed stage shifted successfully");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while shifting copied stage to indexed stage by without marking audio optional"
							+ e.getLocalizedMessage());
		}
	}

	/**
	 * @author: Gopinath Created Date: 12/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method is used to approved indexed ingestion.
	 * @param ingestionName : ingestionName is String value that name of ingestion
	 *                      created.
	 */
	public void approveIndexedIngestion(String ingestionName) {
		try {
			String status = null;
			driver.waitForPageToBeReady();
			base.waitTime(2);
			getIngestionLinkByName(ingestionName).isElementAvailable(15);
			getIngestionLinkByName(ingestionName).Click();
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			base.waitTime(2);
			actionDropDown().isElementAvailable(15);
			actionDropDown().Click();
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			base.waitTime(2);
			approveOption().isElementAvailable(15);
			approveOption().Click();
			getApproveMessageOKButton().isElementAvailable(10);
			if (getApproveMessageOKButton().isDisplayed()) {
				getApproveMessageOKButton().Click();
			}
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			getCloseButton().isElementAvailable(15);
			getCloseButton().Click();
			base.waitTime(2);
			for (int i = 0; i < 8000; i++) {
				driver.scrollPageToTop();
				getRefreshButton().isElementAvailable(15);
				getRefreshButton().Click();
				driver.waitForPageToBeReady();
				statusOfIngestion(ingestionName).isElementAvailable(15);
				status = statusOfIngestion(ingestionName).getText();
				if (status.contains("Approved")) {
					base.passedStep("Approved ingestion successfully");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while Approving ingestion" + e.getLocalizedMessage());
		}
	}

	/**
	 * @author: Gopinath Created Date: 12/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method is used to navigate analytics page.
	 */
	public void navigateToAnalyticsPage() {
		try {
			driver.getWebDriver().get(Input.url + "Ingestion/Analytics");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while navigating analytics page." + e.getLocalizedMessage());
		}
	}

	/**
	 * @author: Gopinath Created Date: 12/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method is used to run full analysis and publish.
	 */
	public void runFullAnalysisAndPublish() {
		try {
			driver.getWebDriver().get(Input.url + "Ingestion/Analytics");
			driver.waitForPageToBeReady();
			base.waitTime(2);
			fullAnalysisRadioButton().isElementAvailable(15);
			fullAnalysisRadioButton().Click();
			driver.waitForPageToBeReady();
			runButton().isElementAvailable(10);
			if (runButton().getWebElement().isEnabled()) {
				runButton().Click();
			}
			for (int i = 0; i < 10000; i++) {
				driver.Navigate().refresh();
				endTime().ScrollTo();
				String endTime = endTime().getText();
				publishButton().isElementAvailable(15);
				if ((!endTime.contentEquals("")) && publishButton().getWebElement().isEnabled()) {
					driver.waitForPageToBeReady();
					publishButton().ScrollTo();
					publishButton().Click();
					break;
				}
			}
			driver.Navigate().refresh();
			publishButton().isElementAvailable(10);
			if (publishButton().getWebElement().isEnabled()) {
				driver.waitForPageToBeReady();
				base.passedStep("Published ingested documents successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while running full analysis and publish." + e.getLocalizedMessage());
		}
	}

	/**
	 * @author: Gopinath Created Date: 12/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method is used to navigate unpublish.
	 */
	public void navigateToUnPublishPage() {
		try {
			driver.getWebDriver().get(Input.url + "Ingestion/UnPublish");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while navigating unpublish page." + e.getLocalizedMessage());
		}
	}

	/**
	 * @author: Gopinath Created Date: 12/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method is used to unpublish saved search.
	 */
	public void unpublish(String savedSearch) {
		try {
			driver.waitForPageToBeReady();
			base.waitTime(2);
			savedSearch(savedSearch).isElementAvailable(15);
			savedSearch(savedSearch).waitAndClick(10);
			unPublishButton().isElementAvailable(15);
			unPublishButton().waitAndClick(10);
			driver.scrollingToBottomofAPage();
			List<WebElement> totalPages = totalPages().FindWebElements();
			int totalPagesCount = totalPages.size() - 2;
			for (int i = 0; i < totalPagesCount + 1; i++) {
				driver.scrollingToBottomofAPage();
				nextButton().isElementAvailable(10);
				nextButton().waitAndClick(10);
				if (disabledNextButton().isDisplayed()) {
					base.waitTime(3);
					unPunlishSearch(savedSearch).isElementAvailable(10);
					if (unPunlishSearch(savedSearch).isDisplayed()) {
						base.passedStep("Unpublish of '" + savedSearch + "' is in progess");
					}
					break;
				}

			}
			driver.Navigate().refresh();
			for (int i = 0; i < 1000; i++) {
				driver.scrollingToBottomofAPage();
				nextButton().isElementAvailable(10);
				nextButton().waitAndClick(10);
				if (disabledNextButton().isDisplayed()) {
					base.waitTime(3);
					if (!unPunlishSearch(savedSearch).isDisplayed()) {
						base.passedStep("Unpublish of '" + savedSearch + "' is completed");
						break;
					} else {
						driver.Navigate().refresh();
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while unpublish saved search." + e.getLocalizedMessage());
		}
	}

	/**
	 * @author: Arun Created Date: 13/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify size of ingestion grid after resize
	 *               browser
	 */

	public void verifySizeOfIngestionGrid() {
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
				return getFilterByDRAFT().Visible();
			}
		}), Input.wait30);
		getFilterByDRAFT().waitAndClick(10);
		getIngestion_GridView().Click();
		driver.waitForPageToBeReady();
		base.waitTime(2);
		String gridsizeBefore = gridTable().GetAttribute("style");
		System.out.println(gridsizeBefore);

		driver.Manage().window().maximize();
		base.waitTime(2);
		String gridsizeAfter = gridTable().GetAttribute("style");
		System.out.println(gridsizeAfter);
		if (gridsizeBefore.equalsIgnoreCase(gridsizeAfter)) {
			base.passedStep("Size of the grid not resized");
		} else {
			base.failedStep("Size of the grid resized");
		}

	}

	/**
	 * @author: Arun Created Date: 18/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will perform add only ingestion for TIFF images
	 *               folder
	 */
	public void tiffImagesIngestion(String DATfile, String TIFFfile, String genSearchPDFCheckBox) {
		selectIngestionTypeAndSpecifySourceLocation("Add Only", "TRUE", Input.sourceLocation, Input.TiffImagesFolder);
		base.waitTime(2);
		base.waitForElement(getDATDelimitersFieldSeparator());
		getDATDelimitersFieldSeparator().selectFromDropdown().selectByVisibleText(Input.fieldSeperator);

		base.waitForElement(getDATDelimitersTextQualifier());
		getDATDelimitersTextQualifier().selectFromDropdown().selectByVisibleText(Input.textQualifier);

		base.waitForElement(getDATDelimitersNewLine());
		getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);
		base.waitTime(2);
		selectDATSource(DATfile, "ProdBeg");
		getTIFFLST().ScrollTo();
		base.waitForElement(getTIFFLST());
		getTIFFLST().isElementAvailable(15);
		getTIFFLST().selectFromDropdown().selectByVisibleText(TIFFfile);

		if (genSearchPDFCheckBox.contains("true")) {
			getTIFFSearchablePDFCheckBox().isElementAvailable(10);
			getTIFFSearchablePDFCheckBox().Click();
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDateFormat().Visible();
			}
		}), Input.wait30);
		getDateFormat().selectFromDropdown().selectByVisibleText("YYYY/MM/DD HH:MM:SS");

		clickOnNextButton();
		selectValueFromEnabledFirstThreeSourceDATFields(Input.prodBeg, Input.prodBeg, Input.custodian);
		clickOnPreviewAndRunButton();
	}

	/**
	 * @author: Arun Created Date: 18/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will ignore errors and perform catlogging
	 */

	public void ignoreErrorsAndCatlogging() {

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
		for (int i = 0; i < 70; i++) {
			base.waitTime(2);
			String status = getStatus(1).getText().trim();

			if (status.contains("Cataloged")) {
				base.passedStep("Cataloged completed");
				break;
			} else if (status.contains("In Progress")) {
				base.waitTime(5);
				getRefreshButton().waitAndClick(10);
			} else if (status.contains("Failed")) {

				//driver.Manage().window().fullscreen();
				getIngestionDetailPopup(1).waitAndClick(10);
				base.waitForElement(errorCountCatalogingStage());
				errorCountCatalogingStage().waitAndClick(10);
				base.waitForElement(ignoreAllButton());
				ignoreAllButton().waitAndClick(10);
				if (getApproveMessageOKButton().isElementAvailable(5)) {
					getApproveMessageOKButton().waitAndClick(10);
					base.passedStep("Clicked on OK button to ignore all errors");
				}

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return doneButton().Enabled();
					}
				}), Input.wait30);
				doneButton().waitAndClick(10);

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getCloseButton().Enabled();
					}
				}), Input.wait30);
				getCloseButton().waitAndClick(10);
				base.VerifySuccessMessage("Action done successfully");
				base.waitTime(2);
				getRefreshButton().waitAndClick(10);
			}
		}

	}

	/**
	 * @author: Arun Created Date: 18/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will ignore errors and perform copying
	 */
	public void ignoreErrorsAndCopying() {

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
		getIngestionDetailPopup(1).waitAndClick(10);
		base.waitTime(2);
		driver.scrollingToElementofAPage(getRunCopying());
		base.waitForElement(getRunCopying());
		getRunCopying().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCloseButton().Enabled();
			}
		}), Input.wait30);
		getCloseButton().waitAndClick(10);
		base.waitTime(2);
		base.VerifySuccessMessage("Ingestion copy has Started.");

		getRefreshButton().waitAndClick(10);
		for (int i = 0; i < 40; i++) {
			getRefreshButton().waitAndClick(15);
			base.waitTime(2);
			String status = getStatus(1).getText().trim();

			if (status.contains("Copied")) {
				base.passedStep("Copied completed");
				break;
			} else if (status.contains("In Progress")) {
				base.waitTime(5);
				getRefreshButton().waitAndClick(5);
			} else if (status.contains("Failed")) {
				getIngestionDetailPopup(1).waitAndClick(10);
				driver.scrollingToElementofAPage(copyingErrorCount());
				copyingErrorCount().waitAndClick(10);
				base.waitTime(1);
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return ignoreAllButton().Enabled();
					}
				}), Input.wait30);
				ignoreAllButton().waitAndClick(10);
				if (getApproveMessageOKButton().isElementAvailable(5)) {
					getApproveMessageOKButton().waitAndClick(10);
					base.passedStep("Clicked on OK button to ignore all errors");
				}

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return doneButton().Enabled();
					}
				}), Input.wait30);
				doneButton().waitAndClick(10);
				base.waitTime(2);
				base.VerifySuccessMessage("Action done successfully");
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getCloseButton().Enabled();
					}
				}), Input.wait30);
				getCloseButton().waitAndClick(10);
				getRefreshButton().waitAndClick(5);
				base.waitTime(2);
				getIngestionDetailPopup(1).waitAndClick(10);
				base.waitTime(2);
				driver.scrollingToElementofAPage(getRunCopying());
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getRunCopying().Enabled();
					}
				}), Input.wait30);

				getRunCopying().waitAndClick(10);
				base.waitTime(5);
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getCloseButton().Enabled();
					}
				}), Input.wait30);
				getCloseButton().waitAndClick(10);
				base.waitTime(10);
				getRefreshButton().waitAndClick(10);

			}
		}

	}

	/**
	 * @author: Arunkumar Created Date: 18/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will validate the value and term which present in
	 *               the copying table column
	 */
	public void verifyDataPresentInCopyTableColumn(String term, String type) {
		getRefreshButton().waitAndClick(10);
		base.waitTime(2);
		getIngestionDetailPopup(1).waitAndClick(Input.wait30);

		driver.scrollingToElementofAPage(getRunIndexing());
		base.waitForElement(getRunIndexing());
		if (type.equalsIgnoreCase("source")) {
			int sourceCount = Integer.parseInt(copyTableDataValue(term, 1).getText());
			if (sourceCount > 0 && copyTableDataValue(term, 1).isElementAvailable(5)) {
				base.passedStep(term + " source docs count is present in the copying table column");
			} else {
				base.failedMessage(term + "source docs count in the copying table column is 0");
			}
		}
		if (type.equalsIgnoreCase("copied")) {
			int copiedCount = Integer.parseInt(copyTableDataValue(term, 2).getText());
			if (copiedCount > 0 && copyTableDataValue(term, 2).isElementAvailable(5)) {
				base.passedStep(term + "Copied docs count present in the copying table column");
			} else {
				base.failedMessage(term + "Copied count in the copying table column is 0");
			}
		}
		if (type.equalsIgnoreCase("error")) {
			int errorCount = Integer.parseInt(copyTableDataValue(term, 3).getText());
			if (errorCount > 0 && copyTableDataValue(term, 3).isElementAvailable(5)) {
				base.passedStep(term + "Errors count present in the copying table column");
			} else {
				base.failedMessage(term + "Errors count in the copying table column is 0");
			}
		}
		if (type.equalsIgnoreCase("missed")) {
			int missedCount = Integer.parseInt(copyTableDataValue(term, 4).getText());
			if (missedCount > 0 && copyTableDataValue(term, 4).isElementAvailable(5)) {
				base.passedStep(term + "Missed docs count present in the copying table column");
			} else {
				base.failedMessage(term + "Missed docs count in the copying table column is 0");
			}
		}
		getCloseButton().waitAndClick(10);
	}

	/**
	 * @author: Arunkumar Created Date: 18/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will check the value in copying section after
	 *               performing rollback
	 */
	public void verifyValueInCopyingSectionAfterRollback(String term) {
		getIngestionDetailPopup(1).waitAndClick(Input.wait30);

		driver.scrollingToElementofAPage(getRunIndexing());
		base.waitTime(1);
		int count = Integer.parseInt(copyTableDataValue(term, 1).getText());

		if (count > 0) {
			base.failedStep(term + "count not set to 0 in the copying table column after rolling back Ingestion");
		} else {
			base.passedStep(term + "count set to 0 in the copying table column after rolling back ingestion");
		}
		getCloseButton().waitAndClick(10);
	}

	/**
	 * @author: Arun Created Date: 20/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will perform add only ingestion and select pdf in
	 *               path file to save as draft
	 */
	public void selectPdfInPathFileAndSaveAsDraft(String ingestionName, String datFile, String datKey, String pdfKey) {

		selectIngestionTypeAndSpecifySourceLocation("Add Only", "TRUE", Input.sourceLocation, ingestionName);
		base.waitForElement(getDATDelimitersFieldSeparator());
		getDATDelimitersFieldSeparator().selectFromDropdown().selectByVisibleText(Input.fieldSeperator);

		base.waitForElement(getDATDelimitersTextQualifier());
		getDATDelimitersTextQualifier().selectFromDropdown().selectByVisibleText(Input.textQualifier);

		base.waitForElement(getDATDelimitersNewLine());
		getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);
		base.stepInfo("Selecting DAT source");
		selectDATSource(datFile, datKey);

		driver.scrollingToBottomofAPage();
		base.stepInfo("Selecting PDF source");
		String checkboxStatus = pdfCheckboxStatus().GetAttribute("style");
		try {
			if (checkboxStatus.contains("block")) {
				getPDFPathInDATFileCheckBox().waitAndClick(5);
				getPDFFilePathFieldinDAT().Click();
				getPDFFilePathFieldinDAT().selectFromDropdown().selectByVisibleText(pdfKey);
			} else {
				getPDFCheckBoxButton().waitAndClick(5);
				getPDFPathInDATFileCheckBox().waitAndClick(10);
				getPDFFilePathFieldinDAT().waitAndClick(5);
				getPDFFilePathFieldinDAT().selectFromDropdown().selectByVisibleText(pdfKey);
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while selecting PDF source." + e.getLocalizedMessage());
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDateFormat().Visible();
			}
		}), Input.wait30);
		getDateFormat().selectFromDropdown().selectByVisibleText("YYYY/MM/DD HH:MM:SS");
		driver.scrollPageToTop();

		base.waitForElement(getIngestion_SaveAsDraft());
		getIngestion_SaveAsDraft().waitAndClick(5);
		base.VerifySuccessMessage("Your changes to the ingestion were successfully saved.");
		base.passedStep("Ingestion successfully saved as draft");

	}

	/**
	 * @author: Arun Created Date: 20/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify error message for duplicate ingestion
	 */
	public void verifyDuplicateIngestionErrorMessage() {
		driver.waitForPageToBeReady();
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

		getRefreshButton().waitAndClick(5);

		for (int i = 0; i < 50; i++) {
			base.waitTime(2);
			String status = getStatus(1).getText().trim();
			if (status.contains("Cataloged")) {
				base.failedMessage("Ingestion is not present in published state");
				break;
			} else if (status.contains("Failed")) {
				getIngestionDetailPopup(1).waitAndClick(5);
				base.waitForElement(errorCountCatalogingStage());
				errorCountCatalogingStage().waitAndClick(10);
				base.waitTime(3);
				String errorMessage1 = ingestionErrorNote(1).getText();
				String errorMessage2 = ingestionErrorNote(2).getText();
				if (errorMessage1.contains(Input.duplicateIngestionError)
						|| errorMessage2.contains(Input.duplicateIngestionError)) {
					base.passedStep("Cataloging Error displayed when ingesting duplicate files");
				} else {
					System.out.println("Error not belonged to duplicate ingestion");
				}
				break;
			} else {
				base.waitTime(5);
				getRefreshButton().waitAndClick(10);
			}
		}
		getCloseButton().waitAndClick(10);

	}

	/**
	 * @author: Arun Created Date: 21/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify the options available for ingestion in
	 *               draft state
	 */
	public void verifyOptionsAvailableForDraftStageIngestion() {
		try {
			getIngestionSettingGearIcon().waitAndClick(10);
			if (getIngestionOpenWizardbutton().isElementAvailable(5) && getIngestionCopyButton().isElementAvailable(5)
					&& getIngestionDeleteButton().isElementAvailable(5)) {
				base.passedStep("Ingestion in draft state have Edit,Copy and Delete option available");
			} else {
				base.failedStep("Ingestion in draft state have no option available");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while checking available option in draft state." + e.getLocalizedMessage());
		}

	}

	/**
	 * @author: Arun Created Date: 21/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify the ingestion details after started
	 *               ingestion
	 */
	public void verifyDetailsAfterStartedIngestion() {
		base.waitTime(5);
		driver.waitForPageToBeReady();
		String currentPageUrl = driver.getUrl();
		if (currentPageUrl.contains("Ingestion/Home")) {
			base.passedStep("User directed to home page after ingestion started");
		} else {
			base.failedStep("User not directed to home page after ingestion started");
		}

		if (getIngestionDetailPopup(1).isElementAvailable(5) && getIngestionWizardDateFormat().isElementAvailable(5)) {
			base.passedStep("ingestion tile and time stamp present in homepage after started ingestion");
		} else {
			base.failedStep("ingestion tile and time stamp not present in homepage after started ingestion");
		}
		getIngestionDetailPopup(1).waitAndClick(5);

		if (catalogSectionDetails().isDisplayed() && getCloseButton().isElementAvailable(5)) {
			base.passedStep("Ingestion details and popup displayed");
		} else {
			base.failedStep("Ingestion details popup not displayed");
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCloseButton().Enabled();
			}
		}), Input.wait30);
		getCloseButton().waitAndClick(10);

	}

	/**
	 * @author: Arun Created Date: 21/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will perform add only ingestion for unicodefiles
	 *               folder
	 */
	public void unicodeFilesIngestion(String datFile, String textFile, String datKey) {
		selectIngestionTypeAndSpecifySourceLocation("Add Only", "TRUE", Input.sourceLocation, Input.UniCodeFilesFolder);
		base.waitTime(2);
		base.waitForElement(getDATDelimitersFieldSeparator());
		getDATDelimitersFieldSeparator().selectFromDropdown().selectByVisibleText(Input.fieldSeperator);

		base.waitForElement(getDATDelimitersTextQualifier());
		getDATDelimitersTextQualifier().selectFromDropdown().selectByVisibleText(Input.textQualifier);

		base.waitForElement(getDATDelimitersNewLine());
		getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);
		base.waitTime(2);
		selectDATSource(datFile, datKey);
		base.stepInfo("*******Selecing text files***************");
		base.waitForElement(getSourceSelectionText());
		getSourceSelectionText().waitAndClick(20);
		base.waitForElement(getSourceSelectionTextLoadFile());
		getSourceSelectionTextLoadFile().selectFromDropdown().selectByVisibleText(textFile);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDateFormat().Visible();
			}
		}), Input.wait30);
		getDateFormat().selectFromDropdown().selectByVisibleText("YYYY/MM/DD HH:MM:SS");

		clickOnNextButton();
		base.waitTime(2);
		selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey, Input.documentKey, Input.custodian);
		clickOnPreviewAndRunButton();
		base.stepInfo("Ingestion started");
	}

	/**
	 * @author: Arun Created Date: 21/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify the copy,index and approve option
	 *               status during the ingestion inprogress
	 */

	public void verifyStatusDuringInProgress() {
		base.waitTime(3);
		driver.waitForPageToBeReady();
		getIngestionDetailPopup(1).waitAndClick(5);

		String copyingStatus = getRunCopying().GetAttribute("class");
		String indexingStatus = getRunIndexing().GetAttribute("class");

		base.waitForElement(getActionDropdownArrow());
		getActionDropdownArrow().waitAndClick(5);
		String approveStatus = getActionApprove().GetAttribute("class");

		if (copyingStatus.contains("disable") && indexingStatus.contains("disable")
				&& approveStatus.contains("disable")) {
			base.passedStep(
					"Copying and Indexing option not enabled and approve option disabled during inprogress ingestion");
		} else {
			base.failedStep("copying,indexing and approve option enabled during inprogress ingestion");
		}
		getCloseButton().waitAndClick(5);

	}

	/**
	 * @author: Arun Created Date: 22/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will enter overlay ingestion data without enabling
	 *               mapping field
	 */

	public void OverlayIngestionForDATWithoutMappingFieldSection(String ingestionName, String datFile, String datKey) {
		selectIngestionTypeAndSpecifySourceLocation("Overlay Only", "TRUE", Input.sourceLocation, ingestionName);
		base.waitTime(2);
		base.waitForElement(getDATDelimitersFieldSeparator());
		getDATDelimitersFieldSeparator().selectFromDropdown().selectByVisibleText(Input.fieldSeperator);

		base.waitForElement(getDATDelimitersTextQualifier());
		getDATDelimitersTextQualifier().selectFromDropdown().selectByVisibleText(Input.textQualifier);

		base.waitForElement(getDATDelimitersNewLine());
		getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);
		base.waitTime(2);
		selectDATSource(datFile, datKey);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDateFormat().Visible();
			}
		}), Input.wait30);
		getDateFormat().selectFromDropdown().selectByVisibleText("YYYY/MM/DD HH:MM:SS");

	}

	/**
	 * @author: Arun Created Date: 22/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will enter overlay ingestion data with enabling
	 *               mapping field
	 */

	public void OverlayIngestionForDATWithMappingFieldSection(String ingestionName, String datFile, String datKey) {
		selectIngestionTypeAndSpecifySourceLocation("Overlay Only", "TRUE", Input.sourceLocation, ingestionName);
		base.waitTime(2);
		base.waitForElement(getDATDelimitersFieldSeparator());
		getDATDelimitersFieldSeparator().selectFromDropdown().selectByVisibleText(Input.fieldSeperator);

		base.waitForElement(getDATDelimitersTextQualifier());
		getDATDelimitersTextQualifier().selectFromDropdown().selectByVisibleText(Input.textQualifier);

		base.waitForElement(getDATDelimitersNewLine());
		getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);
		base.waitTime(2);
		selectDATSource(datFile, datKey);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDateFormat().Visible();
			}
		}), Input.wait30);
		getDateFormat().selectFromDropdown().selectByVisibleText("YYYY/MM/DD HH:MM:SS");
		clickOnNextButton();
		base.waitTime(2);
		base.stepInfo("mapping field section enabled");

	}

	/**
	 * @author: Arun Created Date: 26/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will enter overlay ingestion data without enabling
	 *               mapping field and DAT field
	 */

	public void OverlayForNativeWithoutIngestion(String ingestionName, String nativeFile) {
		selectIngestionTypeAndSpecifySourceLocation("Overlay Only", "TRUE", Input.sourceLocation, ingestionName);
		base.waitTime(2);
		base.waitForElement(getDATDelimitersFieldSeparator());
		getDATDelimitersFieldSeparator().selectFromDropdown().selectByVisibleText(Input.fieldSeperator);

		base.waitForElement(getDATDelimitersTextQualifier());
		getDATDelimitersTextQualifier().selectFromDropdown().selectByVisibleText(Input.textQualifier);

		base.waitForElement(getDATDelimitersNewLine());
		getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);
		
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return datCheckboxStatus().Visible();
			}
		}), Input.wait30);
		datCheckboxStatus().waitAndClick(5);
		base.waitTime(2);
		selectNativeSource(nativeFile, false);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDateFormat().Visible();
			}
		}), Input.wait30);
		getDateFormat().selectFromDropdown().selectByVisibleText("YYYY/MM/DD HH:MM:SS");

	}

	/**
	 * @author: Arun Created Date: 27/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will perform ingestion using copy action for Native
	 *               from draft mode
	 */

	public void IngestionOverlayUsingCopyFromDraftMode(String fileType, String sourceFolder, String nativeFile,
			String datFile, String docKey) {

		driver.waitForPageToBeReady();
		getIngestionDetailPopup(1).waitAndClick(5);
		base.waitForElement(getActionDropdownArrow());
		getActionDropdownArrow().waitAndClick(5);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getActionCopy().Visible();
			}
		}), Input.wait30);

		getActionCopy().waitAndClick(5);
		base.waitTime(3);
		base.stepInfo("Starting ingestion again from draft mode using copy option");

		getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(sourceFolder);
		base.waitTime(2);

		try {

			if (fileType.equalsIgnoreCase("native")) {
				base.waitForElement(datCheckboxStatus());
				datCheckboxStatus().waitAndClick(5);

				selectNativeSource(nativeFile, false);
			} else if (fileType.equalsIgnoreCase("dat")) {
				selectDATSource(datFile, docKey);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNextButton().Visible();
			}
		}), Input.wait30);
		getNextButton().waitAndClick(10);
		base.passedStep("Clicked on Next button");

		base.stepInfo("Pop up messgae for Ingestion without text file");
		if (getApproveMessageOKButton().isElementAvailable(10)) {
			getApproveMessageOKButton().waitAndClick(10);
			base.passedStep("Clicked on OK button to continue without text files");
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPreviewRun().Visible();
			}
		}), Input.wait30);
		getPreviewRun().waitAndClick(10);

		if (getApproveMessageOKButton().isElementAvailable(10)) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getApproveMessageOKButton().Visible();
				}
			}), Input.wait30);
			getApproveMessageOKButton().waitAndClick(10);
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnRunIngestion().Visible();
			}
		}), Input.wait30);
		getbtnRunIngestion().waitAndClick(10);

	}

	/**
	 * @author: Arun Created Date: 27/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify back,ignore and done option in error
	 *               detail popup
	 */
	public void verifyOptionsInErrorDetailsPopup() {

		getIngestionDetailPopup(1).waitAndClick(10);
		base.waitForElement(errorCountCatalogingStage());
		errorCountCatalogingStage().waitAndClick(10);
		if (backButton().isElementAvailable(10)) {
			backButton().waitAndClick(5);
			if (!backButton().isElementAvailable(10)) {
				base.passedStep("Back button displayed and working properly");
			}
		} else {
			base.failedStep("back button not displayed in error details popup");
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return errorCountCatalogingStage().Enabled();
			}
		}), Input.wait30);
		errorCountCatalogingStage().waitAndClick(10);
		base.waitTime(3);
		base.waitForElement(getIgnoreOptionSelection());
		getIgnoreOptionSelection().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return doneButton().Enabled();
			}
		}), Input.wait30);
		doneButton().waitAndClick(10);

		base.VerifySuccessMessage("Action done successfully");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return errorCountCatalogingStage().Enabled();
			}
		}), Input.wait30);
		errorCountCatalogingStage().waitAndClick(10);

		base.waitTime(5);
		String status = getIgnoreOptionSelection().GetAttribute("checked");
		System.out.println(status);
		if (status.equalsIgnoreCase("true")) {
			base.passedStep("Error ignore checkbox, done button working properly and changes made in error list saved");
		} else {
			base.failedStep(
					"Error ignore checkbox, done button working properly and changes made in error list not saved");
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCloseButton().Enabled();
			}
		}), Input.wait30);
		getCloseButton().waitAndClick(10);

	}

	/**
	 * @author: Vijaya.Rani Created Date: 29/04/2022 Modified by: Arunkumar Modified
	 *          Date: 09/05/2022
	 * @description: verify Ingestion publish
	 */
	public boolean verifyIngestionpublish(String dataset) throws InterruptedException {

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
		base.waitTime(3);
		getRefreshButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.stepInfo("Searching for Datasets");
		driver.scrollingToBottomofAPage();
		int count;
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTotalIngestedCount().Visible();
			}
		}), Input.wait30);
		
		String totalDocsIngestedCount = getTotalIngestedCount().getText();	
		int ingestedCount;
		
		if ((String.valueOf(totalDocsIngestedCount)).contains(",")) {
			totalDocsIngestedCount = totalDocsIngestedCount.replace(",", "");
			ingestedCount = Integer.parseInt(totalDocsIngestedCount);
			System.out.println(ingestedCount);
			 
		} else {
			ingestedCount = Integer.parseInt(totalDocsIngestedCount);
			
		}
		if(ingestedCount==0) {
			base.passedStep("No ingestion is currently present");
			count =1;
		}
		else {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTotalPageCount().Visible();
			}
		}), Input.wait30);
		String totalCount = getTotalPageCount().getText();
		int pageCount = Integer.parseInt(getTotalPageCount().getText());
		
		if ((String.valueOf(totalCount)).contains("0")) {
			count = Integer.parseInt(totalCount) / 10;
		} else if (pageCount < 10) {
			count = 1;
		} else {
			count = (Integer.parseInt(totalCount) / 10) + 1;
		}
		}
		System.out.println(count);
		
		boolean status = false;
		for (int i = 1; i <= count; i++) {

			if (getAllIngestionName(dataset).isElementAvailable(5)) {
				status = true;
				base.passedStep("The Ingestion " + dataset + " is already done for this project");
				break;
			} else if (!getAllIngestionName(dataset).isElementAvailable(5) && count == i) {
				status = false;
				base.stepInfo("Ingestion not found");
				break;
			}

			else {
				status = false;
				driver.scrollingToBottomofAPage();
				getIngestionPaginationNextButton().waitAndClick(3);
				base.stepInfo("Expected Ingestion not found in the page " + i);

			}

		}
		return status;

	}

	/**
	 * @author: Arun Created Date: 28/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify back,ignore and done option in error
	 *               detail popup
	 */
	public void performAKNativeFolderIngestion(String datFile) {

		selectIngestionTypeAndSpecifySourceLocation("Add Only", "TRUE", Input.sourceLocation, Input.AK_NativeFolder);
		base.waitTime(2);
		base.waitForElement(getDATDelimitersFieldSeparator());
		getDATDelimitersFieldSeparator().selectFromDropdown().selectByVisibleText(Input.fieldSeperator);

		base.waitForElement(getDATDelimitersTextQualifier());
		getDATDelimitersTextQualifier().selectFromDropdown().selectByVisibleText(Input.textQualifier);

		base.waitForElement(getDATDelimitersNewLine());
		getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);
		base.waitTime(2);
		base.stepInfo("Selecting Dat file");
		selectDATSource(datFile, Input.prodBeg);
		base.stepInfo("Selecting Native file");
		selectNativeSource(Input.NativeFile, false);
		base.stepInfo("Selecting Text file");
		selectTextSource(Input.TextFile, false);
		base.waitTime(2);
		base.stepInfo("Selecting Pdf file");
		selectPDFSource(Input.PDFFile, false);
		base.stepInfo("Selecting Mp3 file");
		selectMP3VarientSource(Input.MP3File, false);
		base.waitTime(2);
		base.stepInfo("Selecting Transcript file");
		selectAudioTranscriptSource(Input.TranscriptFile, false);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDateFormat().Visible();
			}
		}), Input.wait30);
		getDateFormat().selectFromDropdown().selectByVisibleText("YYYY/MM/DD HH:MM:SS");

		clickOnNextButton();
		base.waitTime(2);
		selectValueFromEnabledFirstThreeSourceDATFields(Input.prodBeg, Input.prodBeg, Input.custodian);
		clickOnPreviewAndRunButton();
		base.stepInfo("Ingestion started");

	}

	/**
	 * @author: Arun Created Date: 29/04/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify back,ignore and done option in error
	 *               detail popup
	 */
	public void verifyStatusUpdatenInIngestionDetailPopup(String ingestionStage) {

		driver.waitForPageToBeReady();
		getIngestionDetailPopup(1).waitAndClick(10);

		base.waitTime(2);

		if (ingestionStage.equalsIgnoreCase("Catalogingblock")) {
			String catalogData = catalogSectionDetails().getText();
			String status = getIngestionStatusInPopup(ingestionStage).getText().trim();
			if (status.contains("Cataloged") && !catalogData.trim().equals("")) {
				base.passedStep("Cataloging section details are present in ingestion details popup");
			} else {
			}
		} else if (ingestionStage.equalsIgnoreCase("Copyingblock")) {
			driver.scrollingToElementofAPage(getRunCopying());
			String copyData = copyingSectionDetails().getText();
			String status = getIngestionStatusInPopup(ingestionStage).getText().trim();
			if (status.contains("Copied") && !copyData.trim().equals("")) {
				base.passedStep("Copying section details are present in ingestion details popup");
			} else {
				base.failedStep("details not present in copying section");
			}
		} else if (ingestionStage.equalsIgnoreCase("Indexingblock")) {
			driver.scrollingToElementofAPage(getRunIndexing());
			String indexData = indexingSectionDetails().getText();
			String status = getIngestionStatusInPopup(ingestionStage).getText().trim();
			if (status.contains("Indexed") && !indexData.trim().equals("")) {
				base.passedStep("Indexing section details are present in ingestion details popup");
			} else {
				base.failedStep("details not present in indexing section");
			}
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCloseButton().Enabled();
			}
		}), Input.wait30);
		getCloseButton().waitAndClick(10);

	}

	/**
	 * @author: Arunkumar Created Date: 02/05/2022 Modified by: NA Modified Date: NA
	 * @description: this method will check whether stitched Tiff displayed before
	 *               generate searchable pdf in copying table
	 */
	public void verifyTermPositionInCopyColumn(String term) {
		getIngestionDetailPopup(1).waitAndClick(10);
		driver.scrollingToElementofAPage(getRunIndexing());
		base.waitTime(1);
		String afterTerm = getNextColumnTerm(term).getText();
		if (afterTerm.equalsIgnoreCase(Input.generateSearchablePDF)) {
			base.passedStep(
					"Stitching TIFFs details displayed before the Generate Searchable PDFs row on Ingestion details pop up");
		} else {
			base.failedStep(
					"Stitching TIFFs details not displayed before the Generate Searchable PDFs row on Ingestion details pop up");
		}
		getCloseButton().waitAndClick(10);
	}

	/**
	 * @author: Arunkumar Created Date: 02/05/2022 Modified by: NA Modified Date: NA
	 * @description: this method will perform ingestion with media and transcript
	 *               selection
	 */
	public void mediaAndTranscriptIngestion(String sourceFolder, String datFile) {

		selectIngestionTypeAndSpecifySourceLocation("Add Only", "TRUE", Input.sourceLocation, sourceFolder);
		base.waitTime(2);
		base.waitForElement(getDATDelimitersFieldSeparator());
		getDATDelimitersFieldSeparator().selectFromDropdown().selectByVisibleText(Input.fieldSeperator);

		base.waitForElement(getDATDelimitersTextQualifier());
		getDATDelimitersTextQualifier().selectFromDropdown().selectByVisibleText(Input.textQualifier);

		base.waitForElement(getDATDelimitersNewLine());
		getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);
		base.waitTime(2);
		base.stepInfo("Selecting Dat file");
		selectDATSource(datFile, Input.prodBeg);
		base.waitTime(2);
		base.stepInfo("Selecting Native file");
		selectNativeSource(Input.NativeFile, false);
		base.stepInfo("Selecting Text file");
		selectTextSource(Input.TextFile, false);
		base.waitTime(2);
		base.stepInfo("Selecting Mp3 file");
		selectMP3VarientSource(Input.MP3File, false);
		base.waitTime(2);
		base.stepInfo("Selecting Transcript file");
		selectAudioTranscriptSource(Input.TranscriptFile, false);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDateFormat().Visible();
			}
		}), Input.wait30);
		getDateFormat().selectFromDropdown().selectByVisibleText("YYYY/MM/DD HH:MM:SS");

		clickOnNextButton();
		base.waitTime(2);
		selectValueFromEnabledFirstThreeSourceDATFields(Input.prodBeg, Input.prodBeg, Input.custodian);
		clickOnPreviewAndRunButton();
		base.stepInfo("Ingestion started");

	}

	/**
	 * @author: Arun Created Date: 02/05/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify warning message for different document
	 *               key selection
	 */

	public void verifyWarningMessageForConfigureMappingSection(String sourceFolder, String datFile, String textFile,
			String docKey1, String docKey2) {
		driver.waitForPageToBeReady();

		getIngestionDetailPopup(1).waitAndClick(5);
		base.waitForElement(getActionDropdownArrow());
		getActionDropdownArrow().waitAndClick(5);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getActionCopy().Visible();
			}
		}), Input.wait30);

		getActionCopy().waitAndClick(5);
		driver.waitForPageToBeReady();

		getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(sourceFolder);
		base.waitTime(2);

		selectDATSource(datFile, docKey1);

		base.waitForElement(getSourceSelectionText());
		getSourceSelectionText().waitAndClick(20);
		base.waitForElement(getSourceSelectionTextLoadFile());
		getSourceSelectionTextLoadFile().selectFromDropdown().selectByVisibleText(textFile);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNextButton().Visible();
			}
		}), Input.wait30);
		getNextButton().waitAndClick(10);
		base.passedStep("Clicked on Next button");

		String warningMessage = mappingWarningMessage().getText();
		if (warningMessage.contains(Input.mappingWarningMessage)) {
			base.passedStep("warning message displayed if configure mapping is not matched");
		} else {
			base.failedStep("warning message not displayed if configure mapping is not matched");
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return warningMessageCancelButton().Enabled();
			}
		}), Input.wait30);
		warningMessageCancelButton().waitAndClick(10);

		getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText(docKey2);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNextButton().Visible();
			}
		}), Input.wait30);
		getNextButton().waitAndClick(10);
		base.passedStep("Clicked on Next button");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPreviewRun().Visible();
			}
		}), Input.wait30);
		getPreviewRun().waitAndClick(10);
		if (getApproveMessageOKButton().isElementAvailable(10)) {
			getApproveMessageOKButton().waitAndClick(10);
			base.passedStep("Clicked on OK button to preview and run ingestion");
		}

		if (getbtnRunIngestion().isElementAvailable(15)) {
			base.passedStep("As per selection , admin can proceed and start the ingestion");
		} else {
			base.passedStep("As per selection , admin can't proceed and start the ingestion");
		}

	}

	/**
	 * @author: Arun Created Date: 02/05/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify warning message for different document
	 *               key selection
	 */

	public void verifyUnselectedSourceCountInCopySection(String term) {
		getRefreshButton().waitAndClick(10);
		base.waitTime(2);
		getIngestionDetailPopup(1).waitAndClick(Input.wait30);

		driver.scrollingToElementofAPage(getRunIndexing());
		base.waitForElement(getRunIndexing());

		int sourceCount = Integer.parseInt(copyTableDataValue(term, 1).getText());
		if (sourceCount == 0 && copyTableDataValue(term, 1).isElementAvailable(5)) {
			base.passedStep(term + " unselected source docs count is not present in the copying table column");
		} else {
			base.failedMessage(term + " unselected source docs count is present in the copying table column");
		}

		getCloseButton().waitAndClick(10);

	}

	/**
	 * @author: Mohan Created Date: 29/04/2022
	 * @param dataset
	 * @param sourceSystem
	 * @param Dat
	 * @param text
	 * @param nativeFile
	 * @param PDF
	 * @param tiffImage
	 * @param mp3Variant
	 * @param audioTranscript
	 * @param other
	 * @param checkbox 
	 * @throws InterruptedException
	 */
	public void IngestionRegressionForDifferentDAT(String dataset,String ingestionType,String sourceSystem ,String Dat,String nativeFile ,String text,String PDF,String tiffImage,String checkbox,String mp3Variant,String audioTranscript,String other) throws InterruptedException {
		
			driver.waitForPageToBeReady();
			this.driver.getWebDriver().get(Input.url + "Ingestion/Home");
			base.stepInfo("Click on add new ingestion button");
			base.waitForElement(getAddanewIngestionButton());
			getAddanewIngestionButton().waitAndClick(10);
			
			if (ingestionType.contains("Add")) {
				getIngestion_IngestionType().selectFromDropdown().selectByVisibleText(ingestionType);
				
			}else if (ingestionType.contains("Overlay")) {
				getIngestion_IngestionType().selectFromDropdown().selectByVisibleText(ingestionType);
			}
			
			if (ingestionType.contains("Add")) {
			base.stepInfo("Select Source system");
			base.waitForElement(getSpecifySourceSystem());
			getSpecifySourceSystem().selectFromDropdown().selectByVisibleText(sourceSystem);
			
			}
			base.stepInfo("Select Location");
			base.waitForElement(getSpecifyLocation());
			base.waitTime(2);
			getSpecifyLocation().selectFromDropdown().selectByVisibleText(Input.SourceLocation);

			base.waitForElement(getSpecifySourceFolder());
			base.waitTime(2);
			base.stepInfo("Select Folder");
			base.waitForElement(getSpecifySourceFolder());			
			getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(dataset);
			base.waitTime(2);
			base.waitForElement(getDATDelimitersFieldSeparator());
			getDATDelimitersFieldSeparator().selectFromDropdown().selectByVisibleText(Input.fieldSeperator);

			base.waitForElement(getDATDelimitersTextQualifier());
			getDATDelimitersTextQualifier().selectFromDropdown().selectByVisibleText(Input.textQualifier);

			base.waitForElement(getDATDelimitersNewLine());
			getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);

			driver.scrollingToBottomofAPage();

			base.stepInfo("*******Selecing DAT files***************");
			base.waitForElement(getSourceSelectionDATLoadFile());
			getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(Dat);
			
			base.waitForElement(getSourceSelectionDATKey());
			base.waitTime(2);

			if (dataset.contains("Collection1K_Tally")) {
				getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
			} else if (dataset.contains("20Family_20Threaded")) {
				getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText("DocID");
			} else if (dataset.contains("AllSources")||dataset.contains("PP_PDFGen_10Docs")||dataset.contains(Input.AK_NativeFolder)) {
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
		
				
				base.stepInfo("*******Selecing Native files***************");
				if (nativeFile==null) {
					System.out.println("This Dataset doesn't requries Native files");
					base.stepInfo("This Dataset doesn't requries Native files");
				}else {
					base.waitForElement(getNativeCheckBox());
					getNativeCheckBox().waitAndClick(10);
					base.waitForElement(getNativeLST());
					getNativeLST().selectFromDropdown().selectByVisibleText(nativeFile);
				}
				base.stepInfo("*******Selecing Text files***************");
				if (text==null) {
					System.out.println("This Dataset doesn't requries Text files");
					base.stepInfo("This Dataset doesn't requries Text files");
				}else {
					base.waitForElement(getSourceSelectionText());
					getSourceSelectionText().waitAndClick(20);
					base.waitForElement(getSourceSelectionTextLoadFile());
					getSourceSelectionTextLoadFile().selectFromDropdown().selectByVisibleText(text);
				}
				
				base.stepInfo("*******Selecing PDF files***************");
				if (PDF==null) {
					System.out.println("This Dataset doesn't requries PDF files");
					base.stepInfo("This Dataset doesn't requries PDF files");
				}else {
					base.waitForElement(getPDFCheckBoxButton());
					getPDFCheckBoxButton().waitAndClick(20);
					base.waitForElement(getPDFLST());
					getPDFLST().selectFromDropdown().selectByVisibleText(PDF);
				}

			driver.scrollingToBottomofAPage();
			base.stepInfo("*******Selecing TIFF files***************");
			if (tiffImage==null) {
				System.out.println("This Dataset doesn't requries Tiff files");
				base.stepInfo("This Dataset doesn't requries Tiff files");
			}else {
				base.waitForElement(getTIFFCheckBox());
				getTIFFCheckBox().waitAndClick(10);
				base.waitForElement(getTIFFLST());
				getTIFFLST().selectFromDropdown().selectByVisibleText(tiffImage);
			
			
			if (checkbox==null) {
				base.stepInfo("No Need to select the Generate Searchable PDFs Checkbox");
				
			}else {
				getIngestion_GenerateSearchablePDFsCheckbox().waitAndClick(5);
			}
			}
			
			
			driver.scrollingToBottomofAPage();
			base.stepInfo("*******Selecing MP3 files***************");
			if (mp3Variant==null) {
				System.out.println("This Dataset doesn't requries MP3 files");
				base.stepInfo("This Dataset doesn't requries MP3 files");
			}else {
				
				base.waitForElement(getMP3CheckBoxButton());
				getMP3CheckBoxButton().waitAndClick(15);
				base.waitForElement(getMP3LST());
				getMP3LST().selectFromDropdown().selectByVisibleText(mp3Variant);
			}

			

			base.stepInfo("*******Selecing Audio Transcript files***************");
			if (audioTranscript==null) {
				System.out.println("This Dataset doesn't requries Audio Transcript files");
				base.stepInfo("This Dataset doesn't requries Audio Transcript files");
			}else {
				
				
				base.waitForElement(getAudioTranscriptCheckBoxstionButton());
				getAudioTranscriptCheckBoxstionButton().waitAndClick(15);
				base.waitForElement(getAudioTranscriptLST());
				getAudioTranscriptLST().selectFromDropdown().selectByVisibleText(audioTranscript);
			}

			
			driver.scrollingToBottomofAPage();
			base.stepInfo("*******Selecing Other files***************");
			if (audioTranscript==null) {
				System.out.println("This Dataset doesn't requries Other files");
				base.stepInfo("This Dataset doesn't requries Other files");
			}else {
				
				base.waitForElement(getOtherCheckBox());
				getOtherCheckBox().waitAndClick(15);
				base.waitForElement(getOtherLoadFile());
				getOtherLoadFile().selectFromDropdown().selectByVisibleText(other);
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

			if (ingestionType.contains("Add")) {
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
			else if(dataset.contains("PP_PDFGen_10Docs")||dataset.contains(Input.AK_NativeFolder)){
				
				getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("ProdBeg");
				getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("ProdBeg");
				getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");
				
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
				base.waitTime(2);

				base.waitForElement(getMappingFIELDCAT6());
				getMappingSOURCEFIELD6().selectFromDropdown().selectByVisibleText("EmailBCCNameAndBCCAddress");
				getMappingFIELDCAT6().selectFromDropdown().selectByVisibleText("EMAIL");
				getMappingDESTINATIONFIELD6().selectFromDropdown().selectByVisibleText("EmailBCCNamesAndAddresses");

				getAddButton().waitAndClick(15);
				base.waitTime(2);

				base.waitForElement(getMappingFIELDCAT7());
				getMappingSOURCEFIELD7().selectFromDropdown().selectByVisibleText("EmailCCNamAndCCAddress");
				getMappingFIELDCAT7().selectFromDropdown().selectByVisibleText("EMAIL");
				getMappingDESTINATIONFIELD7().selectFromDropdown().selectByVisibleText("EmailCCNamesAndAddresses");

				getAddButton().waitAndClick(15);
				base.waitTime(2);

				base.waitForElement(getMappingFIELDCAT8());
				getMappingSOURCEFIELD8().selectFromDropdown().selectByVisibleText("EmailToNameAndAddress");
				getMappingFIELDCAT8().selectFromDropdown().selectByVisibleText("EMAIL");
				getMappingDESTINATIONFIELD8().selectFromDropdown().selectByVisibleText("EmailToNamesAndAddresses");
				
				
				if(getCloseBtn().isDisplayed()) {
					for(int i=0;i<3;i++) {
						driver.waitForPageToBeReady();
						getCloseBtn().waitAndClick(5);
						getApproveMessageOKButton().waitAndClick(10);
					}
				}
				driver.scrollPageToTop();
				driver.waitForPageToBeReady();
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
			}
			
				base.stepInfo("This "+dataset+" Ingestion is of Overlay type");
				driver.scrollPageToTop();

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getPreviewRun().Visible();
					}
				}), Input.wait30);
				getPreviewRun().waitAndClick(10);

				if(getApproveMessageOKButton().isElementAvailable(5)) {
				getApproveMessageOKButton().waitAndClick(10);
				}
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getbtnRunIngestion().Visible();
					}
				}), Input.wait30);
				getbtnRunIngestion().waitAndClick(10);
				
				if(ingestionType.equalsIgnoreCase(Input.overlayOnly)) {
				verifyApprovedStatusForOverlayIngestion();
				runFullAnalysisAndPublish();
				}
				else {
					ignoreErrorsAndCatlogging();
					ignoreErrorsAndCopying();
					ingestionIndexing(dataset);
					approveIngestion(1);
					runFullAnalysisAndPublish();
				}
	}

	/**
	 * @author: Arun Created Date: 02/05/2022 Modified by: NA Modified Date: NA
	 * @description: this method will enter overlay ingestion data without enabling
	 *               mapping field and DAT field
	 */

	public void OverlayIngestionWithoutDat(String ingestionName, String type, String file) {
		selectIngestionTypeAndSpecifySourceLocation("Overlay Only", "TRUE", Input.sourceLocation, ingestionName);
		base.waitTime(2);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDATDelimitersNewLine().Visible();
			}
		}), Input.wait30);

		getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);
		base.waitTime(2);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return datCheckboxStatus().Visible();
			}
		}), Input.wait30);
		datCheckboxStatus().waitAndClick(5);
		
		base.waitTime(3);
		if (type.equalsIgnoreCase("Native")) {
			selectNativeSource(file, false);
		}
		if (type.equalsIgnoreCase("Tiff")) {
			base.stepInfo("Selecting Tiff file");
			getTIFFCheckBox().Click();
			base.waitTime(1);
			selectTIFFSource(file, false, false);
		}
		
		if (type.equalsIgnoreCase("text")) {
			base.stepInfo("Selecting text file");
			selectTextSource(file, false);
		}

		if (type.equalsIgnoreCase("pdf")) {
			base.stepInfo("Selecting Pdf file");
			selectPDFSource(file, false);
		}
		if (type.equalsIgnoreCase("mp3")) {
			base.stepInfo("Selecting Mp3 file");
			selectMP3VarientSource(file, false);
		}
		if (type.equalsIgnoreCase("Transcript")) {
			base.stepInfo("Selecting Transcript file");
			selectAudioTranscriptSource(file, false);
		}
		if (type.equalsIgnoreCase("Translation")) {
			base.stepInfo("Selecting Translation file");
			selectOtherSource(type, file, false);
		}
		base.waitTime(2);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDateFormat().Visible();
			}
		}), Input.wait30);
		getDateFormat().selectFromDropdown().selectByVisibleText("YYYY/MM/DD HH:MM:SS");

		clickOnNextButton();
		base.waitTime(2);
		clickOnPreviewAndRunButton();

	}

	/**
	 * @author: Arun Created Date: 03/05/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify the status of overlay ingestion till
	 *               approving stage
	 */
	public void verifyApprovedStatusForOverlayIngestion() {

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

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByCOPIED().Visible();
			}
		}), Input.wait30);
		getFilterByCOPIED().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByINDEXED().Visible();
			}
		}), Input.wait30);
		getFilterByINDEXED().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByAPPROVED().Visible();
			}
		}), Input.wait30);
		getFilterByAPPROVED().waitAndClick(10);

		getRefreshButton().waitAndClick(5);

		for (int i = 0; i < 120; i++) {
			base.waitTime(5);
			getRefreshButton().waitAndClick(5);
			base.waitTime(2);
			String status = getStatus(1).getText().trim();

			if (status.contains("Cataloged")) {
				base.passedStep("Cataloged completed");
				base.waitTime(5);
				getRefreshButton().waitAndClick(5);

			} else if (status.contains("Copied")) {
				base.passedStep("Copied completed");
				base.waitTime(5);
				getRefreshButton().waitAndClick(5);

			} else if (status.contains("Indexed")) {
				base.passedStep("Indexed completed");
				base.waitTime(5);
				getRefreshButton().waitAndClick(5);

			} else if (status.contains("Approved")) {
				base.passedStep("Approved completed");
				break;

			} else if (status.contains("In Progress")) {
				base.waitTime(5);
				getRefreshButton().waitAndClick(5);
			} else if (status.contains("Failed")) {
				base.waitTime(5);
				getRefreshButton().waitAndClick(5);
				System.out.println("Ingestion Failed, will re-run after ignoring errors");
			}
		}
	}

	/**
	 * @author: Arun Created Date: 03/05/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify the status of overlay ingestion till
	 *               approving stage
	 */
	public void verifyDocAvailability() {
		driver.getWebDriver().get(Input.url + "Ingestion/Home");
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

		getRefreshButton().waitAndClick(5);
		base.waitTime(1);
		String ingestionName = getIngestionDetailPopup(1).GetAttribute("title");
		int ingestedCount = Integer.parseInt(getIngestedCount().getText());
		System.out.println(ingestedCount);
		SessionSearch search = new SessionSearch(driver);
		int purehitCount = search.MetaDataSearchInAdvancedSearch(Input.metadataIngestion, ingestionName);
		System.out.println(purehitCount);
		if (ingestedCount == purehitCount) {
			base.passedStep("Document ingested in overlay available for user in application");
		} else {
			base.failedStep("Document ingested in overlay not available for user in application");
		}

	}

	/**
	 * @author Mohan.Venugopal
	 * @description Clean up all the created ingestion in the draft Level
	 * 
	 */
	public void deleteMultipleIngestion() {

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
				return getFilterByINPROGRESS().Visible();
			}
		}), Input.wait30);
		getFilterByINPROGRESS().waitAndClick(10);

		if (getIngestion_DraftTable().isElementAvailable(5)) {
			getRefreshButton().waitAndClick(10);
			base.waitTime(5);
			String getAttribute = getIngestion_DraftCount().GetAttribute("value");
			System.out.println(getAttribute);
			int parseInt = Integer.parseInt(getAttribute);
			for (int i = 1; i <= parseInt; i++) {
				base.waitTime(2);
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getIngestionSettingGearIcon().Visible();
					}
				}), Input.wait30);
				getIngestionSettingGearIcon().waitAndClick(10);

				base.waitForElement(getIngestionDeleteButton());
				getIngestionDeleteButton().waitAndClick(5);
				base.waitForElement(getApproveMessageOKButton());
				getApproveMessageOKButton().isElementAvailable(5);
				getApproveMessageOKButton().waitAndClick(5);

				base.stepInfo("Ingestion is deleted successfully");

			}
		} else {
			base.stepInfo("There is no Ingestions to delete");
		}
	}

	/**
	 * @author: Arun Created Date: 05/05/2022 Modified by: NA Modified Date: NA
	 * @description: this method will perform add only ingestion for unicodefiles
	 *               folder
	 */
	public void unicodeFilesIngestionWithDifferentSourceSystem(String source, String datFile, String textFile,
			String datKey) {
		selectIngestionTypeAndSpecifySourceLocation("Add Only", source, Input.sourceLocation, Input.UniCodeFilesFolder);
		base.waitTime(2);
		base.waitForElement(getDATDelimitersFieldSeparator());
		getDATDelimitersFieldSeparator().selectFromDropdown().selectByVisibleText(Input.fieldSeperator);

		base.waitForElement(getDATDelimitersTextQualifier());
		getDATDelimitersTextQualifier().selectFromDropdown().selectByVisibleText(Input.textQualifier);

		base.waitForElement(getDATDelimitersNewLine());
		getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);
		base.waitTime(2);
		selectDATSource(datFile, datKey);
		base.stepInfo("*******Selecing text files***************");
		base.waitTime(2);
		base.waitForElement(getSourceSelectionText());
		getSourceSelectionText().waitAndClick(10);
		base.waitForElement(getSourceSelectionTextLoadFile());
		getSourceSelectionTextLoadFile().selectFromDropdown().selectByVisibleText(textFile);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDateFormat().Visible();
			}
		}), Input.wait30);
		getDateFormat().selectFromDropdown().selectByVisibleText("YYYY/MM/DD HH:MM:SS");

		clickOnNextButton();
		base.waitTime(2);
		selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey, Input.documentKey, Input.custodian);
		clickOnPreviewAndRunButton();
		base.stepInfo("Ingestion started");
	}

	/**
	 * @author: Arun Created Date: 05/05/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify total ingested count with pure hit
	 *               count
	 */
	public void verifyTotalDocsIngestedWithPurehitCount() {

		driver.getWebDriver().get(Input.url + "Ingestion/Home");
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

		getRefreshButton().waitAndClick(5);
		base.waitTime(3);

		String totalDocsIngestedCount = getTotalIngestedCount().getText();
		SessionSearch search = new SessionSearch(driver);
		int purehitCount = search.basicContentSearch(Input.searchStringStar);
		System.out.println(purehitCount);
		if ((String.valueOf(totalDocsIngestedCount)).contains(",")) {
			totalDocsIngestedCount = totalDocsIngestedCount.replace(",", "");
			int ingestedCount = Integer.parseInt(totalDocsIngestedCount);
			System.out.println(ingestedCount);
			if (ingestedCount == purehitCount) {
				base.passedStep("Pure hit count showed the total number of documents ingested");
			} else {
				base.failedStep("Pure hit count not showed the total number of documents ingested");
			}
		} else {
			int finalCount = Integer.parseInt(totalDocsIngestedCount);
			if (finalCount == purehitCount) {
				base.passedStep("Pure hit count showed the total number of documents ingested");
			} else {
				base.failedStep("Pure hit count not showed the total number of documents ingested");
			}
		}
	}

	/**
	 * @author: Arun Created Date: 05/05/2022 Modified by: NA Modified Date: NA
	 * @description: this method will perform a new add only ingestion using copy
	 *               option
	 */
	public void performNewAddOnlyIngestionUsingCopyOption(String sourceSystem, String sourceFolder, String datFile,
			String docKey) {
		driver.waitForPageToBeReady();
		getIngestionDetailPopup(1).waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getActionDropdownArrow().Visible();
			}
		}), Input.wait30);
		getActionDropdownArrow().waitAndClick(5);

		if (getActionCopy().Displayed() && getActionApprove().Displayed() && getActionOpenWizard().Displayed()
				&& rollbackOptionInPopup().Displayed()) {
			base.passedStep("All available options displayed in action drop down");
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getActionCopy().Visible();
			}
		}), Input.wait30);

		getActionCopy().waitAndClick(5);
		base.waitTime(3);
		base.stepInfo("performing new ingestion using copy option");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSpecifySourceSystem().Visible();
			}
		}), Input.wait30);
		getSpecifySourceSystem().selectFromDropdown().selectByVisibleText(sourceSystem);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSpecifySourceFolder().Visible();
			}
		}), Input.wait30);
		getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(sourceFolder);

		base.stepInfo("Selected source system and source folder");
		base.waitTime(2);

		selectDATSource(datFile, docKey);

		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNextButton().Visible();
			}
		}), Input.wait30);
		getNextButton().waitAndClick(10);
		base.passedStep("Clicked on Next button");

		base.stepInfo("Pop up message for Ingestion without text file");
		if (getApproveMessageOKButton().isElementAvailable(10)) {
			getApproveMessageOKButton().waitAndClick(10);
			base.passedStep("Clicked on OK button to continue without text files");
		}

		if (getApproveMessageSecondOKButton().isElementAvailable(10)) {
			getApproveMessageSecondOKButton().waitAndClick(10);
			base.passedStep("Clicked on OK button to continue ingestion with different mapping field selection");
		}

		clickOnPreviewAndRunButton();
		base.waitTime(3);
		base.passedStep("Ingestion performed successfully");
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @Description Nativigate Ingestion page using button
	 */
	public void nativigateToIngestionViaButton() {
		driver.waitForPageToBeReady();
		getIngestions().waitAndClick(10);
		driver.waitForPageToBeReady();
		getInestionPage().waitAndClick(10);
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @Description Searchable pdf count verification
	 */
	public void searchablePdfCountCheck() {
		driver.waitForPageToBeReady();
		getIngestionDetailPopup(1).waitAndClick(10);
		driver.waitForPageToBeReady();
		String source = generateSearchablePdfSourceDoc().getText().trim();
		String copied = generateSearchablePdfCopiedDoc().getText().trim();
		base.compareTextViaContains(source, copied, "Both count is consider on Ingestion Execution detail pop up",
				"verification failed");
	}

	/**
	 * @author: Vijaya.Rani Created Date: 5/05/2022 Modified by: NA Modified Date:
	 *          NA
	 * @description: this method will perform overlay for ak native folder ingestion
	 *               
	 */
	public void performAKNativeFolderIngestionInOverlay(String datFile) {

		selectIngestionTypeAndSpecifySourceLocation("Overlay Only", "TRUE", Input.sourceLocation,
				Input.AK_NativeFolder);
		base.waitForElement(getDATDelimitersFieldSeparator());
		getDATDelimitersFieldSeparator().selectFromDropdown().selectByVisibleText(Input.fieldSeperator);

		base.waitForElement(getDATDelimitersTextQualifier());
		getDATDelimitersTextQualifier().selectFromDropdown().selectByVisibleText(Input.textQualifier);

		base.waitForElement(getDATDelimitersNewLine());
		getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);
		base.stepInfo("Selecting Dat file");
		selectDATSource(datFile, Input.prodBeg);
		base.stepInfo("Selecting Native file");
		selectNativeSource(Input.NativeFile, false);
		base.stepInfo("Selecting Text file");
		selectTextSource(Input.TextFile, false);
		base.stepInfo("Selecting Pdf file");
		selectPDFSource(Input.PDFFile, false);
		base.stepInfo("Selecting Mp3 file");
		selectMP3VarientSource(Input.MP3File, false);
		base.stepInfo("Selecting Transcript file");
		selectAudioTranscriptSource(Input.TranscriptFile, false);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDateFormat().Visible();
			}
		}), Input.wait30);
		getDateFormat().selectFromDropdown().selectByVisibleText("YYYY/MM/DD HH:MM:SS");

		clickOnNextButton();
		base.waitTime(2);
		clickOnPreviewAndRunButton();
		base.stepInfo("Ingestion started");

	}

	/**
	 * @author: Arun Created Date:05/05/2022 Modified by: NA Modified Date: NA
	 * @description: this method will verify error message for doc id not available
	 *               in database
	 */
	public void verifyNonExistingDatasetErrorMessage() {
		driver.waitForPageToBeReady();
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

		getRefreshButton().waitAndClick(5);

		for (int i = 0; i < 50; i++) {
			base.waitTime(2);
			String status = getStatus(1).getText().trim();
			if (status.contains("Cataloged")) {
				base.failedMessage("Ingestion is already published using add only");
				break;
			} else if (status.contains("Failed")) {
				getIngestionDetailPopup(1).waitAndClick(5);
				base.waitForElement(errorCountCatalogingStage());
				errorCountCatalogingStage().waitAndClick(10);
				base.waitTime(3);
				String errorMessage1 = ingestionErrorNote(1).getText();
				String errorMessage2 = ingestionErrorNote(2).getText();
				if (errorMessage1.contains(Input.nonExistingDataError)
						|| errorMessage2.contains(Input.nonExistingDataError)) {
					base.passedStep("Cataloging Error displayed when doc id not available in the database");
				} else {
					System.out.println("Error not belonged to doc id in database");
				}
				break;
			} else {
				base.waitTime(5);
				getRefreshButton().waitAndClick(10);
			}
		}
		getCloseButton().waitAndClick(10);

	}

	/**
	 * @author:Brundha
	 * @description: this method will verify error message for duplicate ingestion
	 */
	public void verifyingErrorMsgInOverLayMethod() {
		driver.waitForPageToBeReady();
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

		getRefreshButton().waitAndClick(5);

		for (int i = 0; i < 50; i++) {
			base.waitTime(2);
			String status = getStatus(1).getText().trim();
			if (status.contains("Cataloged")) {
				base.failedMessage("Ingestion is not present in published state");
				break;
			} else if (status.contains("Failed")) {
				getIngestionDetailPopup(1).waitAndClick(5);
				base.waitForElement(errorCountCatalogingStage());
				errorCountCatalogingStage().waitAndClick(10);
				base.waitTime(3);
				String ErrorMsg = ingestionErrorNote(1).getText();
				String ExpectedText = "SourceDocID provided in the overlay for this doc is not available in the database.";
				if (ErrorMsg.contains(ExpectedText)) {
					base.passedStep("Error Message is displayed as expected");
				} else {
					System.out.println("Error Message is not displayed as expecetd");
				}
				break;
			} else {
				base.waitTime(5);
				getRefreshButton().waitAndClick(10);
			}
		}
		getCloseButton().waitAndClick(10);

	}
	
	/**
	 * @author:Vijaya.Rani
	 * @description: this method Pagination In Unpublishpage
	 */
	public void navigteToUnpublished(String savedSearch) {
		List<WebElement> totalPages = totalPages().FindWebElements();
		int totalPagesCount = totalPages.size() - 2;
		for (int i = 0; i < totalPagesCount + 1; i++) {
			driver.scrollingToBottomofAPage();
			nextButton().isElementAvailable(10);
			nextButton().waitAndClick(10);
			if (disabledNextButton().isDisplayed()) {
				base.waitTime(3);
				unPunlishSearch(savedSearch).isElementAvailable(10);
				if (unPunlishSearch(savedSearch).isDisplayed()) {
					base.passedStep("Unpublish of '" + savedSearch + "' is in progess");
				}
				break;
			}

		}

	}
	
	/**
	 * @author: Arun Created Date:09/05/2022 Modified by: NA Modified Date: NA
	 * @description: this method will get the total unique ingested document count
	 */
	public int getIngestedUniqueCount() {

		driver.getWebDriver().get(Input.url + "Ingestion/Home");
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

		getRefreshButton().waitAndClick(5);
		base.waitTime(3);

		String totalDocsIngestedCount = getTotalIngestedCount().getText();	
		int ingestedCount;
		
		if ((String.valueOf(totalDocsIngestedCount)).contains(",")) {
			totalDocsIngestedCount = totalDocsIngestedCount.replace(",", "");
			ingestedCount = Integer.parseInt(totalDocsIngestedCount);
			System.out.println(ingestedCount);
			 
		} else {
			ingestedCount = Integer.parseInt(totalDocsIngestedCount);
			
		}
		return ingestedCount;
	}
	
	/**
	 * @author: Arunkumar Created Date: 09/05/2022 Modified by: NA Modified Date: NA
	 * @description: this method will perform audio96docs ingestion 
	 */
	public void performAudio96DocsIngestion(String datFile, String docKey) {
		
		selectIngestionTypeAndSpecifySourceLocation("Add Only", "TRUE", Input.sourceLocation, Input.audio96DocsFolder);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDATDelimitersNewLine().Visible();
			}
		}), Input.wait30);
		getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);
		base.waitTime(2);
		base.stepInfo("Selecting Dat file");
		selectDATSource(datFile,docKey );
		base.stepInfo("Selecting Native file");
		selectNativeSource(Input.selectNativeFile, false);
		base.waitTime(2);
		base.stepInfo("Selecting Text file");
		selectTextSource(Input.selectTextFile, false);
		base.waitTime(2);
		base.stepInfo("Selecting Mp3 file");
		selectMP3VarientSource(Input.selectMp3File, false);
		
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDateFormat().Visible();
			}
		}), Input.wait30);
		getDateFormat().selectFromDropdown().selectByVisibleText(Input.dateFormat);

		clickOnNextButton();
		base.waitTime(2);
		selectValueFromEnabledFirstThreeSourceDATFields(docKey, docKey, docKey);
		clickOnPreviewAndRunButton();
		base.stepInfo("Ingestion started");
	}
	
	/**
	 * @author Mohan.Venugopal
	 * @description: To Select published docs from filter and get Ingestion name from the viewed overall Ingestion 
	 * @param dataset
	 * @return
	 */
	public String selectPublishedFromFilterDropDown(String dataset) {

		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByButton().Visible();
			}
		}), Input.wait30);
		getFilterByButton().waitAndClick(10);
		
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByINPROGRESS().Visible();
			}
		}), Input.wait30);
		getFilterByINPROGRESS().waitAndClick(10);
		
		

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByPUBLISHED().Visible();
			}
		}), Input.wait30);
		getFilterByPUBLISHED().waitAndClick(10);
		
		getRefreshButton().waitAndClick(10);
		getIngestion_DraftTable().isElementAvailable(5);
			String getAttribute = getIngestion_DraftCount().GetAttribute("value");
			System.out.println(getAttribute);
			int parseInt = Integer.parseInt(getAttribute);
			String ingestionName = firstTileTitle().GetAttribute("title");
			System.out.println(ingestionName);
			for (int i = 1; i <=parseInt; i++) {
				base.waitTime(2);
				if (ingestionName.contains(dataset)) {
					base.passedStep("Expected Ingestion is found successfully");
					break;
					
					
				}else {
					base.stepInfo("The Ingestion Name is not there in this project");
				}
					
				}
			
		
	return ingestionName;
	}
	
	/**
	 * @author: Arun Created Date: 10/05/2022 Modified by: NA Modified Date: NA
	 * @description: this method will perform overlay ingestion with dat along with any type 
	 */

	public void OverlayIngestionWithDat(String ingestionName,String datFile,String datKey,String type,String file) {
		
		selectIngestionTypeAndSpecifySourceLocation("Overlay Only", "TRUE", Input.sourceLocation, ingestionName);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDATDelimitersNewLine().Visible();
			}
		}), Input.wait30);

		getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);
		base.waitTime(1);
		selectDATSource(datFile, datKey);
		
		base.waitTime(2);
		if (type.equalsIgnoreCase("Native")) {
			selectNativeSource(file, false);
		}
		if (type.equalsIgnoreCase("Tiff")) {
			base.stepInfo("Selecting Tiff file");
			getTIFFCheckBox().Click();
			base.waitTime(1);
			selectTIFFSource(file, false, false);
		}

		if (type.equalsIgnoreCase("pdf")) {
			base.stepInfo("Selecting Pdf file");
			selectPDFSource(file, false);
		}
		if (type.equalsIgnoreCase("mp3")) {
			base.stepInfo("Selecting Mp3 file");
			selectMP3VarientSource(file, false);
		}
		if (type.equalsIgnoreCase("Transcript")) {
			base.stepInfo("Selecting Transcript file");
			selectAudioTranscriptSource(file, false);
		}
		if (type.equalsIgnoreCase("Translation")) {
			base.stepInfo("Selecting Translation file");
			selectOtherSource(type, file, false);
		}
		base.waitTime(2);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDateFormat().Visible();
			}
		}), Input.wait30);
		getDateFormat().selectFromDropdown().selectByVisibleText(Input.dateFormat);

		clickOnNextButton();
		base.waitTime(2);
		clickOnPreviewAndRunButton();
		base.stepInfo("Overlay ingestion started");

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @throws InterruptedException
	 */
	public void getIngestionSatatusAndPerform() throws InterruptedException {
		
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		getRefeshBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.waitForElement(getStatus(1));
		String status = getStatus(1).getText().trim();
		driver.waitForPageToBeReady();
		
		if(status.contains("In Progress")){
			base.waitTime(5);
			getRefeshBtn().waitAndClick(5);
			getIngestionSatatusAndPerform();
		}else if(status.contains("failed")) {
			base.failedStep("ingestion failed");
		}else if(status.contains("Cataloged")) {
			base.stepInfo("cataloged stage is reached");
			clickCopied();
			getIngestionSatatusAndPerform();
		}else if(status.contains("Copied")) {
			base.stepInfo("copied stage is reached");
			clickIndex();
			getIngestionSatatusAndPerform();
		}else if(status.contains("Indexed")) {
			base.stepInfo("Indexed stage is reached");
			clickApprove();
			getIngestionSatatusAndPerform();
		}else if(status.contains("Approved")) {
			driver.Navigate().refresh();
			driver.Manage().window().maximize();
			runFullAnalysisAndPublish();
		}else {
			base.failedStep("failed to get ingestion status");
		}
		
	}
	/**
	 * @author Aathith.Senthilkumar
	 */
	public void clickCopied() {
		driver.waitForPageToBeReady();
		getIngestionDetailPopup(1).waitAndClick(10);
		driver.Manage().window().fullscreen();
		base.waitTime(2);
		driver.scrollingToElementofAPage(getRunCopying());
		base.waitForElement(getRunCopying());
		driver.waitForPageToBeReady();
		base.waitForElement(getRunCopying());
		getRunCopying().waitAndClick(10);
		getCloseButton().waitAndClick(10);
		base.stepInfo("copied button is clicked");
	}
	/**
	 * @author Aathith.Senthilkumar
	 */
	public void clickIndex() {
		driver.waitForPageToBeReady();
		getIngestionDetailPopup(1).waitAndClick(Input.wait30);
		driver.Manage().window().fullscreen();
		base.waitTime(2);
		driver.scrollingToElementofAPage(getRunIndexing());
		driver.waitForPageToBeReady();
		base.waitForElement(getRunIndexing());
		getRunIndexing().waitAndClick(10);
		getCloseButton().waitAndClick(10);
		base.stepInfo("Ingdex button is clicked");
	}
	/**
	 * @author Aathith.Senthilkumar
	 */
	public void clickApprove() {
		getIngestionDetailPopup(1).waitAndClick(Input.wait30);
		driver.waitForPageToBeReady();
		base.waitForElement(ingestionDetailActionDropdown());
		ingestionDetailActionDropdown().waitAndClick(10);
		driver.waitForPageToBeReady();
		if(getActionApprove().isElementAvailable(2)) {
		getActionApprove().waitAndClick(3);
		if(getApproveMessageOKButton().isElementAvailable(1)) {
		getApproveMessageOKButton().waitAndClick(3);}}
		getCloseButton().waitAndClick(10);
		base.stepInfo("Ingestion is approved");
	}

	
	/**
	*@author Gopinath
	*@description : Method to navigate data sets page.
	*/
	public void navigateToDataSetsPage() {
		try {
			driver.getWebDriver().get(Input.url+ "ICE/Datasets");
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while navigating data sets page."+e.getMessage());
			
		}
	}
	
	/**
	 * @author Aathith.Senthilkumar
	 * @param DataSet
	 * @return
	 * @Description check weather data is there or not 
	 */
	public String isDataSetisAvailable(String DataSet, String ingestionCount) {
		
		navigateToDataSetsPage();
		String datasetName = null;
		driver.waitForPageToBeReady();
		for(int i=0;i<10;i++)
		if(getDataSetName(DataSet).isElementAvailable(2)&& getDatasetMappedCount(ingestionCount).isElementAvailable(10)) {
			base.passedStep(DataSet+" is available in this project");
			datasetName = getDataSetName(DataSet).GetAttribute("title");
			break;
		}
		else if(datasetName==null){
			base.stepInfo("Dataset is not in the project, we need to ingest it");
			driver.scrollingToBottomofAPage();
			
		}else {
			driver.scrollingToBottomofAPage();
		}
		return datasetName;
	}
	
	/**
	 * @author Sakthivel
	 * @param DataSet
	 */
	public void selectDataSetWithNameInDocView(String DataSet) {
		driver.waitForPageToBeReady();
		int i = 1;
		try {
		while(!getDataSetActionBtn(DataSet).isElementAvailable(1)){
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			if(i==10) {
				System.out.println("DataSet not in the project");
				base.failedStep("DataSet is not in project");
				break;
			}
			i++;
		}
		getDataSetActionBtn(DataSet).ScrollTo();
		driver.waitForPageToBeReady();
		getDataSetActionBtn(DataSet).waitAndClick(10);
		base.waitForElement(getDataSetViewInDocView(DataSet));
		getDataSetViewInDocView(DataSet).waitAndClick(10);
		base.stepInfo("DataSet is selected and viewed in docView.");
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("failed"+e.getMessage());
		}
	}
	
	
	
	 /**
		 * @author: Mohan Created Date: 05/05/2022 Modified by: NA Modified Date: NA
		 * @description: verify Ingestion publish
		 */
		public boolean verifyIngestionpublishWithAdditionalOptions(String dataset, String ingestionType) throws InterruptedException {
			
		
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
				Boolean var1 = getAllIngestionName(dataset).isElementAvailable(10);
				System.out.println(var1+"Variable 1");
				
				Boolean var2 = getAllIngestionName(ingestionType).isElementAvailable(10);
				System.out.println(var2+"Variable 2");
				
				if (var1 && var2) {
					String publishedDataSet = getAllIngestionName(dataset).getText();
					if (publishedDataSet.contains(dataset)) {
						status = true;
						base.passedStep("The Ingestion " + dataset + " is already done for this project");
					}
					break;
				} else if (status = false) {
					status = false;
					driver.scrollingToBottomofAPage();
					getIngestionPaginationNextButton().waitAndClick(3);
					base.stepInfo("Expected Ingestion not found in the page " + i);
				}else{
					base.stepInfo("The Ingestion is not found in this project");

				}

			}	
			return status;



		}

		/**
		 * @author Aathith.Senthilkumar
		 * @throws InterruptedException
		 * @Description perform ingestion upto copied stage
		 */
		public void getIngestionSatatusAndPerformUptoCopiedStage() throws InterruptedException {
			
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			getRefeshBtn().waitAndClick(5);
			driver.waitForPageToBeReady();
			String status = getStatus(1).getText().trim();
			
			if(status.contains("In Progress")){
				base.waitTime(5);
				getRefeshBtn().waitAndClick(5);
				getIngestionSatatusAndPerformUptoCopiedStage();
			}else if(status.contains("failed")) {
				base.failedStep("ingestion failed");
			}else if(status.contains("Cataloged")) {
				base.stepInfo("cataloged stage is reached");
				clickCopied();
				getIngestionSatatusAndPerformUptoCopiedStage();
			}else if(status.contains("Copied")) {
				base.stepInfo("copied stage is reached");
			}
			
		}
		/**
		 * @author Aathith.Senthilkumar
		 * @param Language
		 * @Description verify audio language is selectable
		 */
	public void verifyLanguageIsSelectable(String Language) {
		getIngestionDetailPopup(1).waitAndClick(10);
		base.waitTime(2);
		driver.scrollingToElementofAPage(getIsAudioCheckbox());
		getIsAudioCheckbox().waitAndClick(10);
		
		String disabled=getLanguage().GetAttribute("disabled");
		if(disabled==null) {
			base.stepInfo("language seleter is selectable");
			getLanguage().selectFromDropdown().selectByVisibleText(Language);
			base.stepInfo("user abled to selectable "+Language);
		}
		getCloseButton().waitAndClick(10);
	}
	/**
	 * @author Aathith.Senthilkumar
	 * @Description ignore cataloge stage errors
	 */
	public void removeCatalogError() {
		for (int i = 0; i < 70; i++) {
			base.waitTime(2);
			String status = getStatus(1).getText().trim();

			if (status.contains("Cataloged")) {
				base.passedStep("Cataloged completed");
				break;
			} else if (status.contains("In Progress")) {
				base.waitTime(5);
				getRefeshBtn().waitAndClick(10);
			} else if (status.contains("Failed")) {

				driver.Manage().window().fullscreen();
				getIngestionDetailPopup(1).waitAndClick(10);
				base.waitForElement(errorCountCatalogingStage());
				errorCountCatalogingStage().waitAndClick(10);
				base.waitForElement(ignoreAllButton());
				ignoreAllButton().waitAndClick(10);
				if (getApproveMessageOKButton().isElementAvailable(5)) {
					getApproveMessageOKButton().waitAndClick(10);
					base.passedStep("Clicked on OK button to ignore all errors");
				}

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return doneButton().Enabled();
					}
				}), Input.wait30);
				doneButton().waitAndClick(10);

				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getCloseButton().Enabled();
					}
				}), Input.wait30);
				getCloseButton().waitAndClick(10);
				base.waitTime(2);
				getRefeshBtn().waitAndClick(10);
			}
		}
	}

		
		
		/**
		 * @author: Arunkumar Created Date: 11/05/2022 Modified by: NA Modified Date: NA
		 * @description: this method will verify the status of analytics without text files 
		 */
		public void runAnalyticsAndVerifySkippedStatus() {
			try {
				driver.getWebDriver().get(Input.url + "Ingestion/Analytics");
				driver.waitForPageToBeReady();
				base.waitTime(2);
				fullAnalysisRadioButton().isElementAvailable(15);
				fullAnalysisRadioButton().Click();
				driver.waitForPageToBeReady();
				runButton().isElementAvailable(10);
				if (runButton().getWebElement().isEnabled()) {
					runButton().Click();
				}
				for (int i = 0; i < 1000; i++) {
					driver.Navigate().refresh();
					endTime().ScrollTo();
					String endTime = endTime().getText();
					String status = getAnalyticsStatus(1,5).getText();
					System.out.println(status);
					
					if ((!endTime.contentEquals("")) && publishButton().getWebElement().isEnabled()) {
						driver.waitForPageToBeReady();
						if(status.contains(Input.skippedAnalyticMessage)) {
							base.passedStep("Analytics process skipped when perform overlay ingestion without text files");
							
						}
						else {
							base.failedStep("Analytics process not skipped when perform overlay ingestion without text files");
						}
						publishButton().ScrollTo();
						publishButton().Click();
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				base.failedStep("Exception occured while checking analytics status." + e.getLocalizedMessage());
			}
		}
		
		/**
		 * @author: Arunkumar Created Date: 11/05/2022 Modified by: NA Modified Date: NA
		 * @description: this method will verify the status of analytics with text files 
		 */
		public void runAnalyticsAndVerifyAnalyticStatus() {
			try {
				driver.getWebDriver().get(Input.url + "Ingestion/Analytics");
				driver.waitForPageToBeReady();
				base.waitTime(2);
				fullAnalysisRadioButton().isElementAvailable(15);
				fullAnalysisRadioButton().Click();
				driver.waitForPageToBeReady();
				runButton().isElementAvailable(10);
				if (runButton().getWebElement().isEnabled()) {
					runButton().Click();
				}
				for (int i = 0; i < 10000; i++) {
					driver.Navigate().refresh();
					endTime().ScrollTo();
					String endTime = endTime().getText();
					String status = getAnalyticsStatus(1,5).getText();
					
					if ((!endTime.contentEquals("")) && publishButton().getWebElement().isEnabled()) {
						driver.waitForPageToBeReady();
						if(status.contains(Input.completedAnalyticMessage)) {
							base.passedStep("Analytics process takes place when perform overlay ingestion with text files");
							
						}
						else {
							base.failedStep("Analytics process skipped when perform overlay ingestion with text files");
						}
						publishButton().ScrollTo();
						publishButton().Click();
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				base.failedStep("Exception occured while checking analytics status." + e.getLocalizedMessage());
			}
		}
		/**
		 * @author: Brundha
		 * @description: filling indexing stage in ingestion 
		 */
		public void IgnoreErrorAndIndexing() {
			
			getRefreshButton().waitAndClick(10);
			getIngestionName().waitAndClick(Input.wait30);
			driver.waitForPageToBeReady();
			base.waitForElement(getIsAudioCheckbox());
			getIsAudioCheckbox().waitAndClick(10);
			base.waitForElement(getLanguage());
			getLanguage().selectFromDropdown().selectByVisibleText(Input.language);
			getRunIndexing().waitAndClick(10);
			base.waitForElement(getCloseButton());
			getCloseButton().waitAndClick(10);
			base.waitForElement(getFilterByButton());
			getFilterByButton().waitAndClick(10);
			base.waitForElement(getFilterByINDEXED());
			getFilterByINDEXED().waitAndClick(10);
			
				for (int i = 0; i < 120; i++) {
					getRefreshButton().waitAndClick(15);
					base.waitTime(2);
					String status = getStatus(1).getText().trim();

					if (status.contains("Indexed")) {
						base.passedStep("Indexed completed");
						break;
					} else if (status.contains("In Progress")) {
						base.waitTime(5);
						getRefreshButton().waitAndClick(5);
					} else if (status.contains("Failed")) {
						getIngestionDetailPopup(1).waitAndClick(10);
						driver.scrollingToElementofAPage(indexingErrorCount());
						indexingErrorCount().waitAndClick(5);
						base.waitTime(1);
						ignoreAllButton().waitAndClick(5);
						doneButton().waitAndClick(10);
						base.VerifySuccessMessage("Action done successfully");
						getCloseButton().waitAndClick(10);
						getRefreshButton().waitAndClick(5);
						getIngestionDetailPopup(1).waitAndClick(10);
						base.waitTime(2);
						driver.scrollingToElementofAPage(getRunIndexing());
						getRunIndexing().waitAndClick(5);
						getCloseButton().waitAndClick(5);
						getRefreshButton().waitAndClick(5);

					}
				}
		}
	
		/**
		 * @author: Brundha
		 * @description: filling source field
		 */
		public void fillingSourceField() {
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText(Input.prodBeg);
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText(Input.prodBeg);
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");

		}
			
		/**
		* @author Mohan.Venugopal
		* @description : To verify tool tip of Export Botton
		*
		*/
		public void verifyToolTipOfExportIcon() {
		driver.waitForPageToBeReady();
		try {
		Actions act = new Actions(driver.getWebDriver());
		base.waitForElement(getExportIconButton());
		act.moveToElement(getExportIconButton().getWebElement());
		act.build().perform();
		String export = getExportIconButton().GetAttribute("title");
		System.out.println(export);
		base.stepInfo(export);
		base.passedStep("Export Dataset Exceptions tooltip is available.");
		} catch (Exception e) {
		e.printStackTrace();
		}
		}
		
		/**
		 * @author: Arun Created Date: 16/05/2022 Modified by: NA Modified Date: NA
		 * @description: this method will perform add only ingestion for TIFF images
		 *               folder
		 */
		
		public void allSourcesIngestionWithText(String datFile,String docKey) {
			selectIngestionTypeAndSpecifySourceLocation("Add Only", "TRUE", Input.sourceLocation, Input.AllSourcesFolder);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDATDelimitersNewLine().Visible();
				}
			}), Input.wait30);
			getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);
			base.waitTime(2);
			base.stepInfo("Selecting Dat file");
			selectDATSource(datFile,docKey );
			base.waitTime(2);
			base.stepInfo("Selecting Text file");
			selectTextSource(Input.TextFile, false);
			
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDateFormat().Visible();
				}
			}), Input.wait30);
			getDateFormat().selectFromDropdown().selectByVisibleText(Input.dateFormat);

			clickOnNextButton();
			base.waitTime(2);
			selectValueFromEnabledFirstThreeSourceDATFields(docKey, docKey, Input.custodian);
			clickOnPreviewAndRunButton();
			base.stepInfo("Ingestion started");
			
		}
		/**
		 * @author Aathith.Senthilkumar
		 * @param map1
		 * @param map2
		 * @param map3
		 * @Description map the datafield
		 */
		public void ingestionMapping(String map1,String map2,String map3) {
			getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText(map1);
			getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText(map2);
			getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText(map3);
		}
		
		/**
		 * @author: Arun Created Date: 20/06/2022 Modified by: NA Modified Date: NA
		 * @description: Navigate to project page
		 *               
		 */
		public void navigateToManageProjectPage() {
			try {
				driver.getWebDriver().get(Input.url + "Project/Project");
			} catch (Exception e) {
				e.printStackTrace();
				base.failedStep("Exception occured while navigating to project page" + e.getMessage());
			}
		}
		
		/**
		 * @author: Arun Created Date: 20/06/2022 Modified by: NA Modified Date: NA
		 * @description: this method will verify the help content message in project creation Page
		 * @param content
		 *               
		 */
		public void verifyHelpContentOnProjectCreationPage(String content,String expectedMessage) {
			
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAddProjectBtn().Enabled();
				}
			}), Input.wait30);
			getAddProjectBtn().waitAndClick(10);
			driver.scrollingToBottomofAPage();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getHelpContentIcon(content).Visible();
				}
			}), Input.wait30);
			getHelpContentIcon(content).waitAndClick(5);
			
			if(helpContentPopup().isElementAvailable(10)) {
				String contentMessage = getHelpContent().getText();
				
				if(contentMessage.equalsIgnoreCase(expectedMessage)) {
					base.passedStep("Help content displayed correctly for '"+content+"'");
				}
				else {
					base.failedStep("Help content message not displayed correctly");
				}
				
			}
			else {
				base.failedStep("help content popup message not displayed");
			}
			
		}
		
		/**
		 * @author: Arun Created Date: 20/06/2022 Modified by: NA Modified Date: NA
		 * @description: this method will verify the options available in project creation Page
		 * @param content
		 *               
		 */
		public void verifyoptionsAvailability(String content1,String content2) {
			
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAddProjectBtn().Enabled();
				}
			}), Input.wait30);
			getAddProjectBtn().waitAndClick(10);
			driver.waitForPageToBeReady();
			driver.scrollingToBottomofAPage();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getKickOffAnalyticsCheckbox().Visible();
				}
			}), Input.wait30);
			// verify options availablity and default enabled status
			if(getHelpContentIcon(content1).isElementAvailable(5) &&
					getHelpContentIcon(content2).isElementAvailable(5)) {
				base.passedStep("'"+content1+"' and '"+content2+"' options available in project settings");
				getKickOffAnalyticsCheckbox().waitAndClick(5);
				base.waitTime(2);
				String status = getOptionStatus().GetAttribute("disabled");
				System.out.println(status);
				if(status.equalsIgnoreCase("true")) {
					base.passedStep("Both options enabled by default");
				}
				else {
					base.failedStep("Options not enabled by default");
				}
			}
			else {
				base.failedStep("Expected options not available in project settings");
			}
		}
		
		/**
		 * @author: Arun Created Date: 20/06/2022 Modified by: NA Modified Date: NA
		 * @description: this method will verify the option of 'generate searchable pdf' in tiff section
		 *               
		 */
		public void verifyGeneratePdfOptionInTiffSection() {
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAddanewIngestionButton().Enabled();
				}
			}), Input.wait30);
			getAddanewIngestionButton().waitAndClick(5);
			driver.waitForPageToBeReady();
			driver.scrollingToBottomofAPage();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getTIFFCheckBox().Visible();
				}
			}), Input.wait30);
			getTIFFCheckBox().waitAndClick(5);
			
			if(getTIFFSearchablePDFCheckBox().isElementAvailable(10)) {
				base.passedStep("Generate searchable pdf option available in Tiff Section");
			}
			else {
				base.failedStep("Generate searchable pdf option not available in tiff section");
			}
		}
		
		/**
		 * @author: Arun Created Date: 21/06/2022 Modified by: NA Modified Date: NA
		 * @description: this method will perform ingestion for AllSourcesFolder
		 */
		
		public void performAutomationAllsourcesIngestion(String datFile,String docKey) {
			selectIngestionTypeAndSpecifySourceLocation("Add Only", "TRUE", Input.sourceLocation, Input.AllSourcesFolder);
			base.waitTime(2);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDATDelimitersNewLine().Visible();
				}
			}), Input.wait30);
			getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);
			base.waitTime(2);
			base.stepInfo("Selecting Dat file");
			selectDATSource(datFile,docKey );
			base.waitTime(2);
			base.stepInfo("Selecting Native file");
			selectNativeSource(Input.NativeFile, false);
			base.stepInfo("Selecting Text file");
			selectTextSource(Input.TextFile, false);
			base.waitTime(2);
			base.stepInfo("Selecting Pdf file");
			selectPDFSource(Input.PDFFile, false);
			base.stepInfo("Selecting Tiff file");
			selectTIFFSource(Input.TIFFFile, false,false);
			base.waitTime(2);
			base.stepInfo("Selecting Mp3 file");
			selectMP3VarientSource(Input.MP3File, false);
			base.waitTime(2);
			base.stepInfo("Selecting Transcript file");
			selectAudioTranscriptSource(Input.TranscriptFile, false);
			base.stepInfo("Selecting Translation file");
			selectOtherSource("Translation", Input.TranslationFile, false);
			
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDateFormat().Visible();
				}
			}), Input.wait30);
			getDateFormat().selectFromDropdown().selectByVisibleText(Input.dateFormat);

			clickOnNextButton();
			base.waitTime(2);
			selectValueFromEnabledFirstThreeSourceDATFields(docKey, docKey, Input.custodian);
			clickOnPreviewAndRunButton();
			base.stepInfo("Ingestion started");
			
		}
		
		/**
		 * @author: Arun Created Date: 21/06/2022 Modified by: NA Modified Date: NA
		 * @description: this method will start the indexing process
		 */
		public String startIndexing(boolean languageStatus,String language) {

			getRefreshButton().waitAndClick(10);
			base.waitTime(2);
			
			String ingestionName =getIngestionDetailPopup(1).getText();

			getIngestionDetailPopup(1).waitAndClick(10);
			base.waitTime(2);
			driver.scrollingToElementofAPage(getRunIndexing());
			if(languageStatus==true) {
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
			getLanguage().selectFromDropdown().selectByVisibleText(language);	
			} else {
				System.out.println("No need to select for other datasets");
			}
			getRunIndexing().waitAndClick(10);
			base.waitTime(2);
			base.VerifySuccessMessage("Ingestion Indexing has Started.");
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getCloseButton().Enabled();
				}
			}), Input.wait30);
			getCloseButton().waitAndClick(10);
			base.passedStep("Indexing started");
			return ingestionName;
			
		}
		
		/**
		 * @author: Arun Created Date: 21/06/2022 Modified by: NA Modified Date: NA
		 * @description: this method will verify the options available in ingestion settings              
		 */
		public void verifyOptionsAvailableInIngestionSetting() {
			
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getIngestionSettingGearIcon().Visible();
				}
			}), Input.wait60);
			getIngestionSettingGearIcon().waitAndClick(10);
			if (getIngestionOpenWizardbutton().Visible() && getIngestionCopyButton().Visible()
					&& getIngestionDeleteButton().Visible() && getIngestionRollbackbutton().Visible()) {
				base.passedStep("Ingestion settings have Edit,Copy,Rollback and Delete option available");
			} else {
				base.failedStep("Ingestion have no option available");
			}
				
				
	
		}
		
		/**
		 * @author: Arun Created Date: 22/06/2022 Modified by: NA Modified Date: NA
		 * @description: this method will perform ingestion for GD994NativeTextForProductionFolder
		 */
		public void performGD_994NativeFolderIngestion(String datFile,String nativeFile,String textFile) {
			selectIngestionTypeAndSpecifySourceLocation("Add Only", "TRUE", Input.sourceLocation, Input.GD994NativeTextForProductionFolder);
			base.waitForElement(getDATDelimitersNewLine());
			getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText(Input.multiValue);
			base.waitTime(2);
			base.stepInfo("Selecting Dat file");
			selectDATSource(datFile, Input.documentKey);
			base.stepInfo("Selecting Native file");
			selectNativeSource(nativeFile, false);
			base.stepInfo("Selecting Text file");
			selectTextSource(textFile, false);
			base.waitForElement(getDateFormat());
			getDateFormat().selectFromDropdown().selectByVisibleText(Input.dateFormat);
			clickOnNextButton();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDateFormat().Visible();
				}
			}), Input.wait30);
			selectValueFromEnabledFirstThreeSourceDATFields(Input.documentKey, Input.documentKey, Input.documentKey);
			performMapping(6,"EmailBCCNameAndBCCAddress",Input.email,"EmailBCCNamesAndAddresses");
			performMapping(7,"EmailCCNamAndCCAddress",Input.email,"EmailCCNamesAndAddresses");
			performMapping(8,"EmailToNameAndAddress",Input.email,Input.emailToNamesAndAddresses);
			clickOnPreviewAndRunButton();
			base.stepInfo("Ingestion started");
			
		}
		
		/**
		 * @author: Arun Created Date: 22/06/2022 Modified by: NA Modified Date: NA
		 * @description: this method will perform mapping in Mapping section
		 */
		
		public void performMapping(int row,String source,String category,String destination) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getMappingSourceField(row).Visible();
				}
			}), Input.wait30);
			getMappingSourceField(row).selectFromDropdown().selectByVisibleText(source);
			
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getMappingCategoryField(row).Visible();
				}
			}), Input.wait30);
			getMappingCategoryField(row).selectFromDropdown().selectByVisibleText(category);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getMappingDestinationField(row).Visible();
				}
			}), Input.wait30);
			getMappingDestinationField(row).selectFromDropdown().selectByVisibleText(destination);
				
		}
		
		/**
		 * @author: Arun Created Date: 22/06/2022 Modified by: NA Modified Date: NA
		 * @description: this method will perform all the ingestion process and publish ingestion
		 */
		public String publishAddonlyIngestion(String dataset) {
			ignoreErrorsAndCatlogging();
			ignoreErrorsAndCopying();
			ingestionIndexing(dataset);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getIngestionDetailPopup(1).Displayed();
				}
			}), Input.wait30);
			String ingestionName =getIngestionDetailPopup(1).getText();
			approveIngestion(1);
			runFullAnalysisAndPublish();
			return ingestionName;
		}
		
		/**
		 * @author: Arun Created Date: 22/06/2022 Modified by: NA Modified Date: NA
		 * @description: this method will verify the default value of delimiter
		 *               
		 */
		public void verifyDefaultValueOfDelimiter() {
			driver.waitForPageToBeReady();
			
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDATDelimitersNewLine().Visible();
				}
			}), Input.wait30);
			String newLineDelimiter=getDATDelimitersNewLine().selectFromDropdown().getFirstSelectedOption().getText();
			if(newLineDelimiter.equalsIgnoreCase(Input.defaultNewLineDelimiter)) {
				base.passedStep("New line delimiter have default value selected");
			}
			else {
				base.failedStep("default value not selected for new line delimiter");
			}
		}
		
		/**
		 * @author: Arun Created Date: 24/06/2022 Modified by: NA Modified Date: NA
		 * @description: this method will perform ingestion using open in wizard option
		 */

		public void IngestionFromDraftModeWithOpenWizardOption(String type ,String file) {

			driver.waitForPageToBeReady();
			getIngestionDetailPopup(1).waitAndClick(5);
			base.waitForElement(getActionDropdownArrow());
			getActionDropdownArrow().waitAndClick(5);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getActionOpenWizard().Visible();
				}
			}), Input.wait30);

			getActionOpenWizard().waitAndClick(5);
			base.waitTime(3);
			
			if (type.equalsIgnoreCase("Native")) {
				selectNativeSource(file, false);
			}
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getNextButton().Visible();
				}
			}), Input.wait30);
			getNextButton().waitAndClick(10);
			base.passedStep("Clicked on Next button");
			if (getPreviewRun().Enabled()) {
				base.passedStep("Configure mapping section enabled after changing value");
			} else {
				base.failedStep("Configure mapping section not enabled");
			}
			base.stepInfo("Pop up message for Ingestion without text file");
			if (getApproveMessageOKButton().isElementAvailable(10)) {
				getApproveMessageOKButton().waitAndClick(10);
				base.passedStep("Clicked on OK button to continue without text files");
			}

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getPreviewRun().Visible();
				}
			}), Input.wait30);
			getPreviewRun().waitAndClick(10);

			if (getApproveMessageOKButton().isElementAvailable(10)) {
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getApproveMessageOKButton().Visible();
					}
				}), Input.wait30);
				getApproveMessageOKButton().waitAndClick(10);
			}
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getbtnRunIngestion().Visible();
				}
			}), Input.wait30);
			getbtnRunIngestion().waitAndClick(10);

		}
		
		public String getPublishedIngestionName(String dataset) {
			String ingestionName="null";
			if (getAllIngestionName(dataset).isElementAvailable(5)) {
				  ingestionName =getAllIngestionName(dataset).getText();
			}
			else {
				System.out.println("dataset not available");
			}
			return ingestionName;
		}
		
		/**
		 * @author: Arun Created Date: 05/07/2022 Modified by: NA Modified Date: NA
		 * @description: this method will verify the value of metadata
		 */

		public void addMetadatAndVerifyValue(String metadata ,String value) {
			DocListPage docList = new DocListPage(driver);
			docList.selectingSingleValueInCoumnAndRemovingExistingOne(metadata);
			for(int i=1;i<=5;i++) {
				String Language = docList.getDataInDoclist(i,4).getText();
				System.out.println(Language);
				if(docList.getDataInDoclist(i,4).Displayed() && Language.equalsIgnoreCase(value)) {
					base.passedStep("value for metdata 'DocPrimaryLanguage' displayed");
					break;
				}
				else {
					System.out.println("value for metdata 'DocPrimaryLanguage' not displayed");
				}
		   }
			
		}
		
		/**
		 * @author: Arun Created Date: 05/07/2022 Modified by: NA Modified Date: NA
		 * @description: this method will verify options available under source system dropdown
		 */

		public void verifyOptionAvailableInSourceSystem() {
			base.stepInfo("Click on add new ingestion button");
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAddanewIngestionButton().Visible();
				}
			}), Input.wait30);
			getAddanewIngestionButton().waitAndClick(10);
			if(getSpecifySourceSystem().isElementAvailable(15)) {
			 List<WebElement> availableOptions =getSpecifySourceSystem().selectFromDropdown().getOptions();
			 int size = availableOptions.size();
			 System.out.println(size);
			 for(int i=0;i<size;i++) {
				String option = availableOptions.get(i).getText();
				if(option.equalsIgnoreCase(Input.iceSourceSystem)) {
					base.passedStep("'ICE' option available in the source system");
				}	
				else if(option.equalsIgnoreCase(Input.sourceSystem)) {
					base.passedStep("'TRUE' option available in the source system");
				}	
				else if(option.equalsIgnoreCase(Input.nuix)) {
					base.passedStep("'NUIX' option available in the source system");
				}	
				else if(option.equalsIgnoreCase(Input.mappedData)) {
					base.passedStep("'Mapped Data' option available in the source system");
				}	
			 }	
			}
			else {
				base.failedStep("Selecting source system option not available");
			}
			
		}
		
		/**
		 * @author: Arun Created Date: 08/07/2022 Modified by: NA Modified Date: NA
		 * @description: this method will verify options available under action dropdown in popup
		 */

		public void verifyIngestionDetailPopupAndActionDropDown() {
			
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getIngestionDetailPopup(1).Visible();
				}
			}), Input.wait30);
			getIngestionDetailPopup(1).waitAndClick(5);
			driver.waitForPageToBeReady();
			if(getActionDropdownArrow().isElementAvailable(10) && getRunCopying().isElementAvailable(10)) {
				base.passedStep("Ingestion details popup displayed");
			}
			else {
				base.failedStep("Ingestion details popup not displayed");
			}
			
			getActionDropdownArrow().waitAndClick(5);
			if(getActionOpenWizard().Displayed() && getActionCopy().isDisplayed() 
					&& getActionDelete().isDisplayed()) {
				base.passedStep("Open in Wizard,Copy and Delete option available in action dropdown");
			}
			else {
				base.failedStep("action dropdown options not available in popup");
			}
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getCloseButton().Enabled();
				}
			}), Input.wait30);
			getCloseButton().waitAndClick(10);
		}
		
		/**
		 * @author: Arun Created Date: 08/07/2022 Modified by: NA Modified Date: NA
		 * @description: this method will delete the ingestion using action dropdown in popup
		 */

		public void deleteIngestion() {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getIngestionDetailPopup(1).Visible();
				}
			}), Input.wait30);
			getIngestionDetailPopup(1).waitAndClick(5);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getActionDropdownArrow().Visible();
				}
			}), Input.wait30);
			getActionDropdownArrow().waitAndClick(5);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getActionDelete().Visible();
				}
			}), Input.wait30);

			getActionDelete().waitAndClick(5);
			
			if (getApproveMessageOKButton().isElementAvailable(5)) {
				getApproveMessageOKButton().waitAndClick(10);
				base.passedStep("Clicked on OK button to delete ingestion");
			}
			
			base.VerifySuccessMessage("Ingestion deleted successfully.");
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getCloseButton().Enabled();
				}
			}), Input.wait30);
			getCloseButton().waitAndClick(10);
		}
		
		/**
		 * @author: Arun Created Date: 08/07/2022 Modified by: NA Modified Date: NA
		 * @description: this method will verify the status of source and mapping section
		 */
		
		public void verifySourceAndMappingSectionStatusAfterClickingBackButton() {
			base.waitForElement(getMappingFieldBackButton());
			getMappingFieldBackButton().waitAndClick(5);
			
			String sourceSystemStatus = getSpecifySourceSystem().GetAttribute("disabled");
			System.out.println(sourceSystemStatus);
			String ingestionTypeStatus = getIngestion_IngestionType().GetAttribute("disabled");
			System.out.println(ingestionTypeStatus);
			String type = getIngestion_IngestionType().selectFromDropdown().getFirstSelectedOption().getText();
			System.out.println(type);
			
			if(type.contains(Input.ingestionType) || type.contains(Input.overlayOnly)) {
				base.passedStep("Source and overwrite setting page enabled with values");
			}
			else {
				base.failedStep("Source and overwrite setting page not enabled with values");
			}
			if (getPreviewRun().Enabled()) {
				base.failedStep("Configure mapping section enabled");
			} else {
				base.passedStep("Configure mapping section disabled after clicking back button");
			}
			if (sourceSystemStatus == null && ingestionTypeStatus == null) {
				base.passedStep("Source and overwrite setting page enabled after clicking back button");
				
			} else {
				base.failedStep("Source and overwrite setting page disabled");
			}
			
			
		}
		
		/**
		 * @author: Arun Created Date: 11/07/2022 Modified by: NA Modified Date: NA
		 * @description: this method will validate the mandatory field warning message in ingestion wizard
		 */
		
		public void validateIngestionWizardMandatoryFieldWarningMessage() {
			
			base.stepInfo("Click on add new ingestion button");
			base.waitForElement(getAddanewIngestionButton());
			getAddanewIngestionButton().waitAndClick(10);
			base.stepInfo("Without entering mandatory field enter next button");
			base.waitForElement(getNextButton());
			getNextButton().waitAndClick(5);
			
			if(sourceLocationMandatoryError().Displayed() && sourceFolderMandatoryError().Displayed() 
					&& dateFormatMandatoryError().Displayed()) {
				base.passedStep("Error messsage displayed to fill in mandatory fields");
			}
			else {
				base.failedStep("Error message not displayed");
			}
			
		}
		
		/**
		 * @author: Arun Created Date: 11/07/2022 Modified by: NA Modified Date: NA
		 * @description: this method will validate the mandatory field warning message in mapping section
		 */
		
		public void validateMappingSectionMandatoryFieldWarningMessage() {
			
			driver.waitForPageToBeReady();
			base.waitForElement(getPreviewRun());
			getPreviewRun().waitAndClick(5);
			base.VerifyErrorMessage(Input.mandatoryMappingError);
			
		}
		
		/**
		 * @author: Arun Created Date: 11/07/2022 Modified by: NA Modified Date: NA
		 * @description: this method will verify the availability of different available file types in ingestion wizard
		 */
		
		public void verifyDifferentFileTypesAvailability() {
			
			base.stepInfo("Click on add new ingestion button");
			base.waitForElement(getAddanewIngestionButton());
			getAddanewIngestionButton().waitAndClick(10);
			driver.scrollingToBottomofAPage();
			if(getDatCheckBox().Displayed() && getNativeCheckBox().Displayed() && 
					getTextCheckBox().Displayed() && getPDFCheckBoxButton().Displayed()) {
				base.passedStep("Dat, Native, Text, Pdf file types available under source selection");
			}
			else {
				base.failedStep("Dat, Native, Text, Pdf files not available");
			}
			
			if(getTIFFCheckBox().Displayed() && getMP3CheckBoxButton().Displayed() && 
					getAudioTranscriptCheckBoxstionButton().Displayed() && getOtherCheckBox().Displayed()) {
				base.passedStep("Tiff,Mp3,Audio transcript,other file types available under source selection");
			}
			else {
				base.failedStep("Tiff,Mp3,Audio transcript,other file options not available");
			}
			
		}
		
		/**
		 * @author: Arun Created Date: 11/07/2022 Modified by: NA Modified Date: NA
		 * @description: this method will verify the different available file types and status in ingestion wizard
		 */
		
		public void verifyDatAndRemainingFileFieldOptionsAvailability(String datFile) {
			if(!getSourceSelectionDATLoadFile().isElementAvailable(10)) {
				getDatCheckBox().waitAndClick(5);
			}
			driver.waitForPageToBeReady();
			if(getSourceSelectionDATLoadFile().isElementAvailable(10) && getSourceSelectionDATKey().isElementAvailable(10)) {
				getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(datFile);
				base.passedStep("both load file and key selection options available");
			}
			else {
				base.failedStep("options not available under dat selection");
			}
			// Verify native file options and status
			if(getNativeCheckBox().isElementAvailable(5)) {
				base.stepInfo("Verify available options and status for Native file");
				getNativeCheckBox().waitAndClick(5);
				if(getNativeLST().isElementAvailable(5) && getNativePathInDATFileCheckBox().isElementAvailable(5)) {
					base.passedStep("Load file and is path in dat option available for Native File");
					getNativePathInDATFileCheckBox().ScrollTo();
					getNativePathInDATFileCheckBox().waitAndClick(5);
					base.waitTime(1);
					String status =getNativeLST().GetAttribute("disabled");
					System.out.println(status);
					if(status.contains("true")) {
						base.passedStep("Load file disabled upon selection of IS DAT option");
					}else {
						base.failedStep("Load file not disabled upon selection of IS DAT option");
					}
					if(getNativeFilePathFieldinDAT().isElementAvailable(5)) {
						base.passedStep("File path for native file enabled");
					}else {
						base.failedStep("File path for native file not enabled");
					}
				}else {
					base.failedStep("Native options not available");
				}
			}
			// Verify text file options and status
			if(getTextCheckBox().isElementAvailable(5)) {
				base.stepInfo("Verify available options and status for Text file");
				getTextCheckBox().waitAndClick(5);
				if(getSourceSelectionTextLoadFile().isElementAvailable(5) && getTextPathInDATFileCheckBox().isElementAvailable(5)) {
					base.passedStep("Load file and is path in dat option available for Text File");
					getTextPathInDATFileCheckBox().waitAndClick(5);
					base.waitTime(1);
					String status =getSourceSelectionTextLoadFile().GetAttribute("disabled");
					if(status.contains("true")) {
						base.passedStep("Load file disabled upon selection of IS DAT option");
					}else {
						base.failedStep("Load file not disabled upon selection of IS DAT option");
					}
					if(getTextFilePathFieldinDAT().isElementAvailable(5)) {
						base.passedStep("File path for text file enabled");
					}else {
						base.failedStep("File path for text file not enabled");
					}
				}else {
					base.failedStep("Text options not available");
				}
			}
			// Verify pdf file options and status
			if(getPDFCheckBoxButton().isElementAvailable(5)) {
				base.stepInfo("Verify available options and status for Pdf file");
				getPDFCheckBoxButton().waitAndClick(5);
				if(getPDFLST().isElementAvailable(5) && getPDFPathInDATFileCheckBox().isElementAvailable(5)) {
					base.passedStep("Load file and is path in dat option available for Pdf File");
					getPDFPathInDATFileCheckBox().waitAndClick(5);
					base.waitTime(1);
					String status =getPDFLST().GetAttribute("disabled");
					if(status.contains("true")) {
						base.passedStep("Load file disabled upon selection of IS DAT option");
					}else {
						base.failedStep("Load file not disabled upon selection of IS DAT option");
					}
					if(getPDFFilePathFieldinDAT().isElementAvailable(5)) {
						base.passedStep("File path for Pdf file enabled");
					}else {
						base.failedStep("File path for Pdf file not enabled");
					}
				}else {
					base.failedStep("Pdf options not available");
				}
			}
			// Verify tiff file options and status
			if(getTIFFCheckBox().isElementAvailable(5)) {
				base.stepInfo("Verify available options and status for Tiff file");
				getTIFFCheckBox().waitAndClick(5);
				if(getTIFFLST().isElementAvailable(5) && getTIFFPathInDATFileCheckBox().isElementAvailable(5) && getTIFFSearchablePDFCheckBox().Displayed()) {
					base.passedStep("Load file and is path in dat option available for Tiff File");
					getTIFFPathInDATFileCheckBox().waitAndClick(5);
					base.waitTime(1);
					String status =getTIFFLST().GetAttribute("disabled");
					if(status.contains("true")) {
						base.passedStep("Load file disabled upon selection of IS DAT option");
					}else {
						base.failedStep("Load file not disabled upon selection of IS DAT option");
					}
					if(getTIFFFilePathFieldinDAT().isElementAvailable(5)) {
						base.passedStep("File path for Tiff file enabled");
					}else {
						base.failedStep("File path for Tiff file not enabled");
					}
				}else {
					base.failedStep("Tiff options not available");
				}
			}
			// Verify mp3 file options and status
			if(getMP3CheckBoxButton().isElementAvailable(5)) {
				base.stepInfo("Verify available options and status for Mp3 file");
				getMP3CheckBoxButton().waitAndClick(5);
				if(getMP3LST().isElementAvailable(5) && getMP3PathInDATFileCheckBox().isElementAvailable(5)) {
					base.passedStep("Load file and is path in dat option available for Mp3 File");
					getMP3PathInDATFileCheckBox().waitAndClick(5);
					base.waitTime(1);
					String status =getMP3LST().GetAttribute("disabled");
					if(status.contains("true")) {
						base.passedStep("Load file disabled upon selection of IS DAT option");
					}else {
						base.failedStep("Load file not disabled upon selection of IS DAT option");
					}
					if(getMp3FilePathFieldinDAT().isElementAvailable(5)) {
						base.passedStep("File path for Mp3 file enabled");
					}else {
						base.failedStep("File path for Mp3 file not enabled");
					}
				}else {
					base.failedStep("Mp3 options not available");
				}
			}
			// Verify audio transcript file options and status
			if(getAudioTranscriptCheckBoxstionButton().isElementAvailable(5)) {
				base.stepInfo("Verify available options and status for Audio transcript file");
				getAudioTranscriptCheckBoxstionButton().waitAndClick(5);
				if(getAudioTranscriptLST().isElementAvailable(5) && getAudioTransistPathInDATFileCheckBox().isElementAvailable(5)) {
					base.passedStep("Load file and is path in dat option available for Audio transcript File");
					getAudioTransistPathInDATFileCheckBox().waitAndClick(5);
					base.waitTime(1);
					String status =getAudioTranscriptLST().GetAttribute("disabled");
					if(status.contains("true")) {
						base.passedStep("Load file disabled upon selection of IS DAT option");
					}else {
						base.failedStep("Load file not disabled upon selection of IS DAT option");
					}
					if(getTranscriptFilePathFieldinDAT().isElementAvailable(5)) {
						base.passedStep("File path for audio transcript file enabled");
					}else {
						base.failedStep("File path for audio transcript file not enabled");
					}
				}else {
					base.failedStep("Audio transcript options not available");
				}
			}
			
		}
		

		/**
		 * @author: Arun Created Date: 11/07/2022 Modified by: NA Modified Date: NA
		 * @description: this method will verify the options for other file type
		 */
		
		public void verifyOtherFileFieldOptionAndDropdownValueAvailability() {
			if(getOtherCheckBox().isElementAvailable(10)) {
				getOtherCheckBox().waitAndClick(5);
				driver.waitForPageToBeReady();
				if(getOtherLinkType().Displayed() && getOtherLoadFile().Displayed() 
						&& getOtherPathInDATFileCheckBox().Displayed()) {
					 List<WebElement> availableOptions =getOtherLinkType().selectFromDropdown().getOptions();
					 int size = availableOptions.size();
					 System.out.println(size);
					 for(int i=0;i<size;i++) {
						String option = availableOptions.get(i).getText();
						if(option.equalsIgnoreCase(Input.translation)) {
							base.passedStep("'Translation' option available in the link type dropdown");
						}	
						else if(option.equalsIgnoreCase(Input.related)) {
							base.passedStep("'Related' option available in the link type dropdown");
						}	
					 }	
					base.passedStep("Link type,load options and is path options available");
				}
				else {
					base.failedStep("Other file type options not available");
				}
				
			}
			else {
				base.failedStep("Other field options not available");
			}
		}
		
		/**
		 * @author: Arun Created Date: 13/07/2022 Modified by: NA Modified Date: NA
		 * @description: this method will verify the details retained after opening ingestion using open wizard option
		 * @param: ingestionType
		 * @param: sourceFolder
		 */
		
		public void validateDetailsAfterOpeningIngestionFromDraft(String ingestionType,String sourceFolder) {
			
			String retainedSourceFolder= getSpecifySourceFolder().selectFromDropdown().getFirstSelectedOption().getText();
			String retainedDate=getDateFormat().selectFromDropdown().getFirstSelectedOption().getText();
			String retainedType=getIngestion_IngestionType().selectFromDropdown().getFirstSelectedOption().getText();
			
			if(retainedDate.equalsIgnoreCase(Input.dateFormat) && retainedSourceFolder.equalsIgnoreCase(sourceFolder)
					&& retainedType.equalsIgnoreCase(ingestionType)) {
				base.passedStep("All information displayed in wizard");
			}
			else {
				base.failedStep("Information not displayed");
			}
		}
				
}
