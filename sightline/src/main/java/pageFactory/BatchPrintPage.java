package pageFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import courgette.runtime.utils.FileUtils;
import executionMaintenance.UtilityLog;
import junit.framework.Assert;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import testScriptsSmoke.Input;

public class BatchPrintPage {

	Driver driver;
	Element element;
	BaseClass base;

	public Element getMenuBatchPrint() {
		return driver.FindElementByXPath("//a[@href='/BatchPrint/']");
	}

	public Element getSearchBatchPrint() {
		return driver.FindElementByXPath("//div[1]/div/label/i");
	}

	public Element getTagBatchPrint() {
		return driver.FindElementByXPath("//div[2]/div/label/i");
	}

	public Element getFolderBatchPrint() {
		return driver.FindElementByXPath("//div[3]/div/label/i");
	}

	public Element getMySavedSearchArrow() {
		return driver.FindElementByXPath("(//*[@id='-1g']/i)[1]");
	}

	public Element getAllTagsArrow() {
		return driver.FindElementByXPath("(//*[@id='-1g']/i)[2]");
	}

	public Element getAllFoldersArrow() {
		return driver.FindElementByXPath("(//*[@id='-1g']/i)[3]");
	}

	public Element getSelectSavedSearch(String searchname) {
		return driver.FindElementByXPath(".//*[@id='searchTree']/ul/li[1]//a[contains(text(),'" + searchname + "')]");
	}

	public Element getSelectTag(String tagName) {
		return driver.FindElementByXPath("//a[starts-with(text(),'" + tagName + "')]");
	}

	public Element getSelectFolder(String folderName) {
		return driver.FindElementByXPath("//a[starts-with(text(),'" + folderName + "')]");
	}

	public Element getNextbtn() {
		return driver.FindElementByXPath("//a[contains(text(),'Next')]");
	}

	public Element getSourcenextbutton() {
		return driver.FindElementById("source-selection-next-button");
	}

	public Element getExceptiontypenextbutton() {
		return driver.FindElementById("exception-file-types-next-button");
	}

	public Element getBrandingnextbutton() {
		return driver.FindElementById("branding-and-redaction-next-button");
	}

	public Element getSlipnextbutton() {
		return driver.FindElementById("slip-sheets-next-button");
	}

	public Element getAnalysisnextbutton() {
		return driver.FindElementById("analysis-next-button");
	}

	public Element getBasisnextbutton() {
		return driver.FindElementById("basis-for-printing-next-button");
	}

	public Element getAnalysis_Ignore_RadioButton() {
		return driver.FindElementByXPath(".//*[@id='ignoreRadioButton']/following-sibling::i");
	}

	public Element getSelectCustodianName() {
		return driver.FindElementByCssSelector("input[data-friendlbl='CustodianName'] + i");
	}

	public Element getSelectDOCID() {
		return driver.FindElementByCssSelector("input[data-friendlbl='DocID'] + i");
	}

	public Element getSelectSourceDOCID() {
		return driver.FindElementByCssSelector("input[data-friendlbl='SourceDocID'] + i");
	}

	public Element getSelectMasterDate() {
		return driver.FindElementByCssSelector("input[data-friendlbl='MasterDate'] + i");
	}

	public Element getSelectColumnAddtoSelected() {
		return driver.FindElementById("addFormObjects");
	}

	public Element getBrandingCentre() {
		return driver.FindElementById("btn-batchprint-branding-1");
	}

	public Element getBatchPrintEnterBranding() {
		return driver.FindElementByXPath(".//*[@id='Edit User Group']//div[@class='redactor-editor']");
	}

	public Element getInsertMetadataFieldOKButton() {
		return driver.FindElementById("myModalOk");
	}

	public Element getSelectExportFileName() {
		return driver.FindElementById("exportFileDropDown");
	}

	public Element getSelectExportFileSortBy() {
		return driver.FindElementById("exportFileSortByDropDown");
	}

	public Element getGenerateButton() {
		return driver.FindElementByXPath("//a[contains(text(),'Generate')]");
	}

	public Element getbackgroundDownLoadLink() {
		return driver.FindElementByXPath("//*[@id='dt_basic']/tbody/tr[1]/td[contains(.,'Download File')]");
	}

	public Element getTaskType() {
		return driver.FindElementByXPath(".//*[@id='dt_basic']/tbody/tr[1]/td[2]");
	}

	public Element getBatchPrintStatus() {
		return driver.FindElementByXPath(".//*[@id='dt_basic']/tbody/tr[1]/td[8]");
	}

	public Element getDownLoadLink() {
		return driver.FindElementByCssSelector("#dt_basic a[onclick*='downloadFile']");
	}

	public Element getAnalysis_SkipExcelFiles_RadioButton() {
		return driver.FindElementByXPath(".//*[@id='skipExcelFileRadioButton']/following-sibling::i");
	}

	public Element getMedia_InsertMetadata() {
		return driver.FindElementById("mediaFileInsertMetadataLinkButton");
	}

	public Element getExcel_InsertMetadata() {
		return driver.FindElementById("excelFileInsertMetadataLinkButton");
	}

	public Element getOther_InsertMetadata() {
		return driver.FindElementById("otherExceptionFileTypesInsertMetadataLinkButton");
	}

	public Element getBP_Exception_Media() {
		return driver.FindElementById("mediaFilePlaceHolderHtml");
	}

	public Element getBP_Exception_Excel() {
		return driver.FindElementById("excelFilePlaceHolderHtml");
	}

	public Element getBP_Exception_Other() {
		return driver.FindElementById("OtherExceptionFileTypesPlaceHolderHtml");
	}

	public Element getselectMetadataFields_Media() {
		return driver.FindElementById("mediaFileMetaData");
	}

	public Element getselectMetadataFields_Excel() {
		return driver.FindElementById("excelfileMetaData");
	}

	public Element getselectMetadataFields_Other() {
		return driver.FindElementById("otherExceptionFileTypesMetaData");
	}

	public Element getOkButton_Media() {
		return driver.FindElementById("mediaFileOk");
	}

	public Element getOkButton_Excel() {
		return driver.FindElementById("excelFileOk");
	}

	public Element getOkButton_Other() {
		return driver.FindElementById("otherExceptionFileTypesOk");
	}

	public Element getPlaceHolderBox() {
		return driver.FindElementByXPath("//div[@class='redactor-box']");
	}

	public Element getSelectProduction() {
		return driver.FindElementById("ProductionDropDown");
	}

	// new addition
	public Element getProductionRadioButton() {
		return driver.FindElementByXPath(".//*[@id='priorProductionRadioButton']/following-sibling::i");
	}

	public Element getSkippedFolderButton() {
		return driver.FindElementByXPath(".//*[@id='includeFolderSkippedDocuments']/following-sibling::i");
	}

	public Element getPDFCreationforAllButton() {
		return driver.FindElementByXPath(".//*[@id='onePDFForAllDocRadioButton']/following-sibling::i");
	}

	public Element getErrortext() {
		return driver.FindElementByXPath(".//*[@id='BatchPrintErrorGrid']/tbody/tr[1]/td[2]");
	}

	public Element getErrorInfoLink() {
		return driver.FindElementById("idBtachPrintError");
	}

	public Element getCloseButton() {
		return driver.FindElementById("Close");
	}

	// Added By Jeevitha

	public Element getReportAnalysisDetails() {
		return driver.FindElementByXPath("//div[@id='noIssuesDocDiv']");
	}

	public Element getAnalysisRequestDetails() {
		return driver.FindElementByXPath("//span[@id='noIssuesDocSpan']//parent::p");
	}

	public ElementCollection getGridValues(int i) {
		return driver.FindElementsByXPath("//div[@id='issueDocGrid']//td[" + i + "]//label");
	}

	public Element getPaginationOfProbTablegrid() {
		return driver.FindElementByXPath("//div[@id='issueDocGrid']//ul[@class='pagination']");
	}

	public ElementCollection getAnalysisProdTableHeader() {
		return driver.FindElementsByXPath("//div[@id='issueDocGrid']//th");
	}

	public Element getFolderTreeStructure() {
		return driver.FindElementById("folderTree");
	}

	public Element getSkipFoldToggleStatus() {
		return driver.FindElementById("includeFolderSkippedDocuments");
	}

	public Element getEnableSlipSheetToggle() {
		return driver.FindElementByXPath("//input[@id='includeSlipSheetCheckBox']//parent::label//i");
	}

	public Element getSlipSheetOfProd_DDText() {
		return driver.FindElementByXPath("//div[@id='slipSheetsDropdownDiv']//parent::div//label");
	}

	public Element getSlipSheetOfProd_DD() {
		return driver.FindElementByXPath("//div[@id='slipSheetsDropdownDiv' and @class='disablePanel']");
	}

	public Element getTitle_TXB() {
		return driver.FindElementByXPath("//input[@id='coverSheetTitleName']");
	}

	public Element getFromRecipient_TXB() {
		return driver.FindElementByXPath("//input[@id='coverSheetSenderName']");
	}

	public Element getToRecipient_TXB() {
		return driver.FindElementByXPath("//input[@id='coverSheetRecipientName']");
	}

	public Element getCoverAndIntroToggle() {
		return driver.FindElementByXPath("//input[@id='includeCoverdIntroCheckBox']//parent::label//i");
	}

	public Element getHeaderPositionBtn(int i) {
		return driver.FindElementByXPath("(//div[@id='divbranding2']//button)[" + i + "]");
	}

	public ElementCollection getBrandingPositions() {
		return driver.FindElementsByXPath("//div[@id='divbranding2']//button");
	}

	public Element getRedactionToggleStatus() {
		return driver.FindElementByXPath("//input[@class='encryp-check activeC']");
	}

	public ElementCollection getSlipsheetsFields() {
		return driver.FindElementsByXPath("//ul[@class='list-unstyled']//li//strong");
	}

	public ElementCollection getProductionList() {
		return driver.FindElementsByXPath("//select[@id='ProductionDropDown']//option");
	}

	public ElementCollection getBackGroundPageHeader() {
		return driver.FindElementsByXPath("//table[@id='dt_basic']//th");
	}

	public Element getValuesFromBackGroundPage(int i) {
		return driver.FindElementByXPath("//table[@id='dt_basic']/tbody/tr[1]/td[" + i + "]");
	}

	public ElementCollection getExportFileNameDD() {
		return driver.FindElementsByXPath("//select[@id='exportFileDropDown']//option");
	}

	public Element getOtherExceptionFileTypeHelpBlock() {
		return driver.FindElementByXPath(
				"//h3[text()='Other Exception File Types']/parent::div[@class='popover fade right in']");
	}

	public Element getExcelFilesHelpBlock() {
		return driver.FindElementByXPath("//h3[text()='Excel Files']/parent::div[@class='popover fade right in']");
	}

	public Element getMediaFilesHelpBlock() {
		return driver.FindElementByXPath("//h3[text()='Media Files']/parent::div[@class='popover fade right in']");
	}

	public Element getMediaFilesHelpIcon() {
		return driver.FindElementByXPath("//a[@data-original-title='Media Files']");
	}

	public Element getExcelFilesHelpIcon() {
		return driver.FindElementByXPath("//a[@data-original-title='Excel Files']");
	}

	public Element getOtherExceptionFileTypeHelpIcon() {
		return driver.FindElementByXPath("//a[@data-original-title='Other Exception File Types']");
	}

	public Element getAnalysisRequestHeader() {
		return driver.FindElementByXPath("//div[@class='clearfix']//following-sibling::div//strong");
	}

	public Element getAnalysisMessage() {
		return driver
				.FindElementByXPath("//div[@class='clearfix']//following-sibling::div//h4//following-sibling::div//p");
	}

	public Element getAscAndDescDDlist() {
		return driver.FindElementByXPath("//select[@id='exportFileSortingOrderDropDown']");
	}

	public ElementCollection getSortByDDList() {
		return driver.FindElementsByXPath("//select[@id='exportFileSortByDropDown']//option");
	}

	public Element getSlipSheetDD_prod() {
		return driver.FindElementByXPath("//select[@id='slipSheetsDropdown']");
	}

	public Element getTagStatus() {
		return driver.FindElementByXPath("//div[@id='tagSet']");
	}

	public Element getFolderStatus() {
		return driver.FindElementByXPath("//div[@id='folderSet']");
	}

	public Element getAnalysSkipDocFolder_DD() {
		return driver.FindElementByXPath("//li[contains(@class,' jstree-closed')]//i[@class='jstree-icon jstree-ocl']");
	}

	public ElementCollection getAnalysSkipDocFolder_List() {
		return driver.FindElementsByXPath("//ul[@class='jstree-children']//li");
	}

	public Element getAnalysisTransTextOfNaive() {
		return driver.FindElementByXPath("//div[@id='nativeDiv']//p");
	}

	public Element getAnalysisIgnoreBtn() {
		return driver.FindElementByXPath("//input[@id='ignoreRadioButton']//parent::label");
	}

	public Element getAnalysisSkipDocBtn() {
		return driver.FindElementByXPath("//input[@id='skipDocumentsRadioButton']//parent::label");
	}

	public Element getAnalysisPrintTranBtn() {
		return driver.FindElementByXPath("//input[@id='printTransRadioButton']//parent::label");
	}

	public Element getAnalysisNativeBtn() {
		return driver.FindElementByXPath("//input[@id='printNativeRadioButton']//parent::label");
	}

	public Element getToggleButton() {
		return driver.FindElementByXPath("(//i[@class='pull-left'])[last()]");
	}

	public Element getBackButton() {
		return driver.FindElementByXPath("//a[@title='Back']");
	}

	public Element getBgPageDD() {
		return driver.FindElementByXPath("//select[@name='StatusList']");
	}

	public Element getSavedSearch_dropdown() {
		return driver.FindElementByXPath("(//i[@class='jstree-icon jstree-ocl'])[1]");
	}

	public Element getSelectRadioButton() {
		return driver.FindElementByXPath("//*[@id='selectSearchRadioButton']/following-sibling::i");
	}

	public Element getbtnNext() {
		return driver.FindElementByXPath("//a[@title='Next']");
	}

	public Element getSavedSearch_MySearchTab() {
		return driver.FindElementByXPath("//a[text()='My Saved Search']");
	}

	public Element getPageHeader() {
		return driver.FindElementByXPath("//div[@class='row prod']//h2");
	}

	public Element getNativeCB_BasisPage() {
		return driver.FindElementByXPath("//input[@id='nativesRadioButton']//..//i");
	}

	// Added by Gopinath - 24/01/2022
	public Element getExcelFilesText() {
		return driver.FindElementByXPath(
				"//div[@class='col-md-12 clear']//p[contains(text(),'In your selected documents,') and contains(text(),'docs are Excel files. Many of these have printing issues.')]");
	}

	public Element getPrintPlaceholdersForDocs() {
		return driver.FindElementByXPath(
				"//div[@class='col-md-12 clear']//p[text()='Do you want to print them or skip them? If skipping them, do you want to include placeholders for these docs?']");
	}

	public Element getPrintExcelFileRadioButton() {
		return driver.FindElementByXPath("//input[@id='printExcelFileRadiobutton']/following-sibling::i");
	}

	public Element getSkipExcelFileRadioButton() {
		return driver.FindElementByXPath("//input[@id='skipExcelFileRadioButton']/following-sibling::i");
	}

	public Element getIncludePlaceholdersToogle() {
		return driver.FindElementByXPath("//input[@id='includeExcelFileCheckBox']/following-sibling::i");
	}

	public Element getExcelMetaDataLink() {
		return driver.FindElementByXPath("//a[@id='excelFileInsertMetadataLinkButton']");
	}

	public Element getExcelOkButton() {
		return driver.FindElementByXPath("//button[@id='excelFileOk']");
	}

	public Element getExceptionNext() {
		return driver.FindElementById("exception-file-types-next-button");
	}

	public Element getExceptionFileTypeException() {
		return driver.FindElementByXPath("//input[@id='includeOtherExceptionFileTypesCheckBox']/following-sibling::i");
	}

	public Element getMetaDataCheckbox(String metadata) {
		return driver.FindElementByXPath("//strong[text()='" + metadata + "']/preceding-sibling::i");
	}

	public Element getAddToSelectedButton() {
		return driver.FindElementByXPath("//a[text()='Add to Selected']");
	}

	public Element getBrandingAndRedactionNextButton() {
		return driver.FindElementByXPath("//a[@id='branding-and-redaction-next-button']");
	}

	public Element getIncludePlaceHolderToogle() {
		return driver.FindElementById("includeExcelFileCheckBox");
	}

	public Element getExcelSkipMetaDataDropdown() {
		return driver.FindElementById("excelfileMetaData");
	}

	// Added by Gopinath - 11/02/2022
	public Element getMediaFilesPrintableText() {
		return driver.FindElementByXPath(
				"//div[@id='mediaFilePlaceHolderHtml']//p[contains(text(),'In your selected documents') and contains(text(),'docs are media files. These will be skipped, as they are not printable')]");
	}

	public Element getIncludePlaceHolderText() {
		return driver.FindElementByXPath(
				"//div[@id='mediaFilePlaceHolderHtml']//p[text()='Do you want to include placeholder for these docs?']");
	}

	public Element getMediaFilesToogle() {
		return driver.FindElementByXPath("//input[@id='includeMediaFileCheckBox']//..//i");
	}

	public Element getMediaFileEditor() {
		return driver.FindElementByXPath("//textarea[@id='editorMediaFile']//..//div");
	}

	public Element getMediaFileMetaDataLink() {
		return driver.FindElementById("mediaFileInsertMetadataLinkButton");
	}

	public Element getMediaFilFieldDropdown() {
		return driver.FindElementById("mediaFileMetaData");
	}

	public Element getMediaFilFieldOkButton() {
		return driver.FindElementById("mediaFileOk");
	}

	public Element getIncludeMediaCheckBox() {
		return driver.FindElementByXPath("//input[@id='includeMediaFileCheckBox']");
	}

	public Element getExpectedMediaFileWarningMessage() {
		return driver.FindElementByXPath(
				"//div[@id='divbigBoxes']/div/descendant::span[text()='Warning !']/following-sibling::p[contains(text(),'Please include placeholder for excepted media files.')]");
	}

	public Element getIncludeMediaFileCheckBox() {
		return driver.FindElementByXPath("//input[@id='includeMediaFileCheckBox']/following-sibling::i");
	}

	public Element getIncludeMediaFileCheckBoxActive() {
		return driver.FindElementByXPath("//input[@id='includeMediaFileCheckBox' and @class='encryp-check activeC']");
	}

	public Element getExceptionMediaSkippedMessage() {
		return driver.FindElementByXPath(
				"//div[@id='mediaFilePlaceHolderHtml']/div/p[contains(text(),'docs are media files. These will be skipped, as they are not printable')]");
	}

	public Element getDownloadBatchFile(int id) {
		return driver.FindElementByXPath(
				"//table[@id='dt_basic']//tbody//td[text()[normalize-space()='" + id + "']]//following-sibling::td[8]");
	}

	// added by sowndarya

	public Element getBatchId(int i) {
		return driver.FindElementByXPath("//table[@id='dt_basic']//td[@class='sorting_1'][" + i + "]");
	}

	public Element getRequestedDocCountInAnalysisPage() {
		return driver.FindElementByXPath("//strong[contains(text(),'Analysis')]//..//..//div//p");
	}
	
	public Element getDocCountInAnalysisPage() {
		return driver.FindElementByXPath("//p//span[last()]");
	}
	
	public BatchPrintPage(Driver driver) {

		this.driver = driver;
//		this.driver.getWebDriver().get(Input.url + "BatchPrint/");
		base = new BaseClass(driver);
	}

	public void BatchPrintWithNative(String type, String name, String orderCriteria, String orderType)
			throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMenuBatchPrint().Visible();
			}
		}), Input.wait30);

		getMenuBatchPrint().Click();

		if (type.equalsIgnoreCase("search")) {
			getSearchBatchPrint().waitAndClick(10);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getMySavedSearchArrow().Visible();
				}
			}), Input.wait30);
			getMySavedSearchArrow().Click();

			getSelectSavedSearch(name).waitAndClick(5);
		} else if (type.equalsIgnoreCase("tag")) {
			getTagBatchPrint().waitAndClick(10);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAllTagsArrow().Visible();
				}
			}), Input.wait30);
			getAllTagsArrow().Click();
			getSelectTag(name).waitAndClick(10);
		}

		else if (type.equalsIgnoreCase("folder")) {
			getFolderBatchPrint().waitAndClick(10);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAllFoldersArrow().Visible();
				}
			}), Input.wait30);
			getAllFoldersArrow().Click();
			getSelectFolder(name).waitAndClick(10);

		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSourcenextbutton().Enabled();
			}
		}), Input.wait30);

		getSourcenextbutton().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBasisnextbutton().Enabled();
			}
		}), Input.wait30);
		getBasisnextbutton().waitAndClick(5);

		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAnalysis_Ignore_RadioButton().Visible();
				}
			}), Input.wait30);
			getAnalysis_Ignore_RadioButton().waitAndClick(5);
		} catch (Exception e) {
			// TODO: handle exception
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAnalysisnextbutton().Enabled();
			}
		}), Input.wait30);
		getAnalysisnextbutton().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getExceptiontypenextbutton().Enabled();
			}
		}), Input.wait30);
		getExceptiontypenextbutton().waitAndClick(5);
		Thread.sleep(2000);

		driver.scrollingToBottomofAPage();

		// Driver.scrollingToElementofAPage(getSelectCustodianName());
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectCustodianName().Visible();
			}
		}), Input.wait30);
		getSelectCustodianName().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectDOCID().Visible();
			}
		}), Input.wait30);
		getSelectDOCID().ScrollTo();
		getSelectDOCID().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectMasterDate().Visible();
			}
		}), Input.wait30);
		getSelectMasterDate().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectSourceDOCID().Visible();
			}
		}), Input.wait30);
		getSelectSourceDOCID().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectColumnAddtoSelected().Enabled();
			}
		}), Input.wait30);
		getSelectColumnAddtoSelected().waitAndClick(5);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSlipnextbutton().Enabled();
			}
		}), Input.wait30);
		getSlipnextbutton().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBrandingCentre().Visible();
			}
		}), Input.wait30);
		getBrandingCentre().waitAndClick(5);
		// Thread.sleep(2000);
		getBatchPrintEnterBranding().waitAndFind(5);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBatchPrintEnterBranding().Enabled();
			}
		}), Input.wait30);
		getBatchPrintEnterBranding().ScrollTo();
		new Actions(driver.getWebDriver()).moveToElement(getBatchPrintEnterBranding().getWebElement()).click();
		// getTIFF_EnterBranding().Click();
		getBatchPrintEnterBranding().SendKeys("Test");
		// Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getInsertMetadataFieldOKButton().Visible();
			}
		}), Input.wait30);
		getInsertMetadataFieldOKButton().waitAndClick(5);
		Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBrandingnextbutton().Visible();
			}
		}), Input.wait30);
		getBrandingnextbutton().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectExportFileName().Enabled();
			}
		}), Input.wait30);
		Thread.sleep(4000);
		getSelectExportFileName().selectFromDropdown().selectByVisibleText("DocID");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectExportFileName().Enabled();
			}
		}), Input.wait30);
		getSelectExportFileSortBy().selectFromDropdown().selectByVisibleText("DocID");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getGenerateButton().Enabled();
			}
		}), Input.wait30);
		getGenerateButton().waitAndClick(5);

		base.VerifySuccessMessage(
				"Successfully initiated the batch print. You will be prompted with notification once completed.");

		for (int i = 0; i < 30; i++) {
			try {
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getbackgroundDownLoadLink().Visible() && getbackgroundDownLoadLink().Enabled();
					}
				}), Input.wait120);
				if (getbackgroundDownLoadLink().Visible() && getbackgroundDownLoadLink().Enabled())
					break;
			} catch (Exception e) {
				driver.Navigate().refresh();
				System.out.println("Refresh");
				UtilityLog.info("Refresh");
			}
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTaskType().Displayed();
			}
		}), Input.wait30);
		String status = getBatchPrintStatus().getText();
		Assert.assertEquals("COMPLETED", status);
		Reporter.log("Batch Print with saved search " + name + " with order " + orderCriteria + " " + "and order type "
				+ orderType + " is completed!", true);
		UtilityLog.info("Batch Print with saved search " + name + " with order " + "" + orderCriteria
				+ " and order type " + orderType + " is completed!");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDownLoadLink().Enabled();
			}
		}), Input.wait30);
		getDownLoadLink().waitAndClick(40);
		// download time
		Thread.sleep(15000);

	}

	/**
	 * This Function is to read all the docID from files in a given directory and
	 * check whether IDs are in ascending order or not We need to Pass directory
	 * path to the function.
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public static void checkForOrderInPDF(String orderCriteria, String orderType)
			throws IOException, InterruptedException {
		// Call unzip function to unzip the downloaded file

		File rootZip = new File("C:\\BatchPrintFiles\\downloads");
		File[] listZip = rootZip.listFiles();
		File zipFilePath = listZip[0];
		String destDir = "C:\\BatchPrintFiles\\PDFs";
		unzip(zipFilePath, destDir);

		// validate each fileonce unzip is done
		File root = new File("C:\\BatchPrintFiles\\PDFs");
		File[] list = root.listFiles();

		ArrayList orderInFiles = new ArrayList();
		List expectedOrder = new ArrayList(orderInFiles);

		for (File f : list) {
			if (!f.isDirectory()) {

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

						// read only required lines!
						for (String line : lines) {

							if (orderCriteria.equals("DocID:")) {
								// System.out.println("***********************Testing
								// DocID***********************************");
								if (line.contains(orderCriteria) && !line.contains("SourceDocID:")) {
									// Extract only digits
									int numberOnly = Integer.parseInt(line.replaceAll("[^0-9]", ""));
									// System.out.println(numberOnly);
									orderInFiles.add(numberOnly);
								}
							} else if (orderCriteria.equals("MasterDate:") || orderCriteria.equals("CreateDate:")
									|| orderCriteria.equals("EmailSentDate:")
									|| orderCriteria.equals("LastModifiedDate:")
									|| orderCriteria.equals("LastEditDate:") || orderCriteria.equals("DocDate:")
									|| orderCriteria.equals("LastSaveDate:")) {
								// System.out.println("***********************Testing
								// Dates***********************************");
								if (line.contains(orderCriteria)) {
									if (!line.substring(line.indexOf("Date:") + 5).toString().isEmpty()
											&& !line.substring(line.indexOf("Date:") + 5).equalsIgnoreCase(" ")) {
										// System.out.println("---"+line.substring(line.indexOf("Date:")+5).toString());
										System.out.println(f.getAbsoluteFile().toString());
										UtilityLog.info(f.getAbsoluteFile().toString());
										System.out.println(line.substring(line.indexOf("Date:") + 5).toString().trim());
										UtilityLog.info(line.substring(line.indexOf("Date:") + 5).toString().trim());
										orderInFiles.add(line.substring(line.indexOf("Date:") + 5).toString().trim());

									}
								}

							} else if (orderCriteria.equals("CustodianName:") || orderCriteria.equals("DocFileName:")) {
								// System.out.println("***********************Testing
								// Names***********************************");
								if (line.contains(orderCriteria)) {
									if (!line.substring(line.indexOf("Name:") + 5).toString().isEmpty()
											&& !line.substring(line.indexOf("Name:") + 5).equalsIgnoreCase(" ")) {
										// System.out.println("---"+line.substring(line.indexOf("Date:")+5).toString());
										System.out.println(f.getAbsoluteFile().toString());
										UtilityLog.info(f.getAbsoluteFile().toString());
										System.out.println(line.substring(line.indexOf("Name:") + 5).toString().trim());
										UtilityLog.info(line.substring(line.indexOf("Name:") + 5).toString().trim());
										orderInFiles.add(line.substring(line.indexOf("Name:") + 5).toString().trim());

									}
								}
							}

							else {
								System.out.println("Please passright criteria!!");
							}
						}
					}
				}
			}

		}

		if (orderCriteria.equals("DocID:")) {

			// lets copy the output to other arraylist and sort it.
			// System.out.println("Actual DOC IDs orderin file : "+orderInFiles);
			System.out.println(
					"Actual " + orderCriteria.replace(":", "") + "s order from downloaded files : " + orderInFiles);
			expectedOrder.addAll(orderInFiles);

			Collections.sort(expectedOrder);
			if (orderType.equalsIgnoreCase("Desc")) {
				Collections.reverse(expectedOrder);
			}

			// System.out.println("Expected DocIDs Order : "+expectedOrder);
			System.out.println("Expected " + orderCriteria.replace(":", "") + " order : " + expectedOrder);
			// Now compare sorted arraylist with output
			if (expectedOrder.equals(orderInFiles)) {
				// System.out.println(orderCriteria+" is in requested order! -->PASS");
				Reporter.log(orderCriteria + " is in requested order! -->PASS", true);
				UtilityLog.info(orderCriteria + " is in requested order! -->PASS");
			} else {
				// System.out.println("FAIL");
				// System.out.println(orderCriteria+" is NOT in requested order! -->FAIL");
				Reporter.log(orderCriteria + " is NOT in requested order! -->FAIL", true);
				UtilityLog.info(orderCriteria + " is NOT in requested order! -->FAIL");
				Assert.assertTrue(expectedOrder.equals(orderInFiles));
			}

			expectedOrder.clear();
			orderInFiles.clear();
		}

		if (orderCriteria.equals("CustodianName:") || orderCriteria.equals("DocFileName:")) {

			//
			// System.out.println("Actual "+orderCriteria+" orderin file : "+orderInFiles);
			expectedOrder.addAll(orderInFiles);
			// sort strings!!
			expectedOrder = sortStrings(expectedOrder);

			if (orderType.equalsIgnoreCase("Desc")) {
				Collections.reverse(expectedOrder);
			}

			expectedOrder.clear();
			orderInFiles.clear();
		}

		else if (orderCriteria.equals("MasterDate:") || orderCriteria.equals("CreateDate:")
				|| orderCriteria.equals("SendDateTime") || orderCriteria.equals("EmailSentDate:")
				|| orderCriteria.equals("LastModifiedDate:") || orderCriteria.equals("LastEditDate:")
				|| orderCriteria.equals("DocDate:") || orderCriteria.equals("LastSaveDate:")) {

			// lets copy the output to other arraylist and sort it.
			expectedOrder.addAll(orderInFiles);
			// Decending Order! so, sort first and then reverse the list!
			expectedOrder = sortDates(expectedOrder);
			if (orderType.equalsIgnoreCase("Desc")) {
				Collections.reverse(expectedOrder);
			}

			// Now compare sorted arraylist with output
			if (expectedOrder.equals(orderInFiles)) {
				// System.out.println("Pass");
				// Add_Log.info(TestCaseName + ":- Actual Count :-'");
				System.out.println(orderCriteria.replace(":", "") + " is in requested order! -->PASS");
			} else {
				// System.out.println("FAIL");
				System.out.println(orderCriteria.replace(":", "") + " is NOT in requested order! -->FAIL");
				Assert.assertTrue(expectedOrder.equals(orderInFiles));
			}

			expectedOrder.clear();
			orderInFiles.clear();

		}

		backUpBatchPrintFiles(orderCriteria, orderType);
	}

	private static void unzip(File zipFilePath, String destDir) {
//		File dir = new File(destDir);
//		// create output directory if it doesn't exist
//		if (!dir.exists())
//			dir.mkdirs();
////		FileInputStream fis;
//		// buffer for read and write data to file
//		byte[] buffer = new byte[1024];
//		try {
//			fis = new FileInputStream(zipFilePath.toString());
//			ZipInputStream zis = new ZipInputStream(fis);
//			ZipEntry ze = zis.getNextEntry();
//			while (ze != null) {
//				String fileName = ze.getName();
//				File newFile = new File(destDir + File.separator + fileName);
//				System.out.println("Unzipping to " + newFile.getAbsolutePath());
//				// create directories for sub directories in zip
//				new File(newFile.getParent()).mkdirs();
//				FileOutputStream fos = new FileOutputStream(newFile);
//				int len;
//				while ((len = zis.read(buffer)) > 0) {
//					fos.write(buffer, 0, len);
//				}
//				fos.close();
//				// close this ZipEntry
//				zis.closeEntry();
//				ze = zis.getNextEntry();
//			}
//			// close last ZipEntry
//			zis.closeEntry();
//			zis.close();
//			fis.close();
//			// Add_Log.info(TestCaseName+": Unziped done successfully");
//		} catch (IOException e) {
//			// Add_Log.info(TestCaseName+": Failed to unzip the file");
//			e.printStackTrace();
//		}

	}

	public static void backUpBatchPrintFiles(String orderCriteria, String order) {
		// back up TCs zip file for later verification
		File destinationFolder = new File("C:\\BatchPrintFiles\\BackUpFiles");
		File sourceFolder = new File("C:\\BatchPrintFiles\\downloads");

		if (!destinationFolder.exists()) {
			destinationFolder.mkdirs();
		}

		// Check weather source exists and it is folder.
		if (sourceFolder.exists() && sourceFolder.isDirectory()) {

			// Get list of the files and iterate over them
			File[] listOfFiles = sourceFolder.listFiles();

			if (listOfFiles != null) {
				for (File child : listOfFiles) {
					// Move files to destination folder
					child.renameTo(new File(destinationFolder + "\\--" + orderCriteria.replace(":", "") + "--" + order
							+ "--" + child.getName()));
				}

				// Add if you want to delete the source folder
				// sourceFolder.delete();
			}
		} else {
			System.out.println(sourceFolder + "  Folder does not exists");
		}

		// delete last TCs PDF from PDFs folder
		File file = new File("C:\\BatchPrintFiles\\PDFs");
		String[] myFiles;
		if (file.isDirectory()) {
			myFiles = file.list();
			for (int i = 0; i < myFiles.length; i++) {
				File myFile = new File(file, myFiles[i]);
				myFile.delete();
			}
		}
	}

	// @SuppressWarnings("unchecked")
	public static List sortDates(List expectedOrder) {
		Collections.sort(expectedOrder, new Comparator<String>() {

			public int compare(String object1, String object2) {
				return object1.compareTo(object2);
			}
		});
		return expectedOrder;
	}

//	@SuppressWarnings("unchecked")
	private static List sortStrings(List names) {
		Collections.sort(names, new Comparator<String>() {
			public int compare(String o1, String o2) {
				return o1.compareToIgnoreCase(o2);
			}
		});
		return names;
	}

	// ********verify that user can view the details on Exception File
	// Types****************
	public void BatchPrintWitExceptionalFile(final String searchname) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMenuBatchPrint().Visible();
			}
		}), Input.wait30);
		getMenuBatchPrint().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMySavedSearchArrow().Visible();
			}
		}), Input.wait30);
		getMySavedSearchArrow().Click();

		getSelectSavedSearch(searchname).waitAndClick(15);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSourcenextbutton().Enabled();
			}
		}), Input.wait30);
		getSourcenextbutton().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBasisnextbutton().Enabled();
			}
		}), Input.wait30);
		getBasisnextbutton().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAnalysisnextbutton().Enabled();
			}
		}), Input.wait30);
		getAnalysisnextbutton().waitAndClick(5);

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAnalysis_SkipExcelFiles_RadioButton().Visible();
			}
		}), Input.wait30);
		getAnalysis_SkipExcelFiles_RadioButton().waitAndClick(5);

		try {
			getBP_Exception_Media().Displayed();
			// insert metadata for media files
			/*
			 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
			 * getMedia_InsertMetadata().Visible() ;}}), Input.wait30);
			 */
			getMedia_InsertMetadata().waitAndClick(5);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getselectMetadataFields_Media().Enabled();
				}
			}), Input.wait30);
			getselectMetadataFields_Media().selectFromDropdown().selectByVisibleText("CustodianName");

			getOkButton_Media().waitAndClick(5);
		} catch (Exception e1) {

			System.out.println("No Media file displayed");
		}

		try {
			getBP_Exception_Excel().Displayed();

			getExcel_InsertMetadata().waitAndClick(5);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getselectMetadataFields_Excel().Visible();
				}
			}), Input.wait30);
			getselectMetadataFields_Excel().selectFromDropdown().selectByVisibleText("DocID");

			/*
			 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
			 * getOkButton_Excel().Visible() ;}}), Input.wait30);
			 */
			getOkButton_Excel().waitAndClick(5);
		} catch (Exception e1) {

			System.out.println("No Excel file displayed");
		}

		driver.scrollingToBottomofAPage();
		try {
			getBP_Exception_Other().Displayed();

			getOther_InsertMetadata().waitAndClick(5);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getselectMetadataFields_Other().Visible();
				}
			}), Input.wait30);
			getselectMetadataFields_Other().selectFromDropdown().selectByVisibleText("MasterDate");

			getOkButton_Other().waitAndClick(5);
		} catch (Exception e1) {

			System.out.println("No Other file displayed");
		}

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getExceptiontypenextbutton().Enabled();
			}
		}), Input.wait30);
		getExceptiontypenextbutton().waitAndClick(5);

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectDOCID().Visible();
			}
		}), Input.wait30);
		getSelectDOCID().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectMasterDate().Visible();
			}
		}), Input.wait30);
		getSelectMasterDate().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectSourceDOCID().Visible();
			}
		}), Input.wait30);
		getSelectSourceDOCID().waitAndClick(5);

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectColumnAddtoSelected().Enabled();
			}
		}), Input.wait30);
		getSelectColumnAddtoSelected().waitAndClick(5);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSlipnextbutton().Enabled();
			}
		}), Input.wait30);
		getSlipnextbutton().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBrandingCentre().Visible();
			}
		}), Input.wait30);
		getBrandingCentre().waitAndClick(5);
		// Thread.sleep(2000);
		getBatchPrintEnterBranding().waitAndFind(5);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBatchPrintEnterBranding().Enabled();
			}
		}), Input.wait30);
		getBatchPrintEnterBranding().ScrollTo();
		new Actions(driver.getWebDriver()).moveToElement(getBatchPrintEnterBranding().getWebElement()).click();
		// getTIFF_EnterBranding().Click();
		getBatchPrintEnterBranding().SendKeys("Test");
		// Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getInsertMetadataFieldOKButton().Visible();
			}
		}), Input.wait30);
		getInsertMetadataFieldOKButton().waitAndClick(5);
		Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBrandingnextbutton().Visible();
			}
		}), Input.wait30);
		getBrandingnextbutton().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectExportFileName().Enabled();
			}
		}), Input.wait30);
		getSelectExportFileName().selectFromDropdown().selectByVisibleText("DocID");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectExportFileName().Enabled();
			}
		}), Input.wait30);
		getSelectExportFileSortBy().selectFromDropdown().selectByVisibleText("DocID");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getGenerateButton().Enabled();
			}
		}), Input.wait30);
		getGenerateButton().waitAndClick(5);

		base.VerifySuccessMessage(
				"Successfully initiated the batch print. You will be prompted with notification once completed.");

		for (int i = 0; i < 20; i++) {
			try {
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getbackgroundDownLoadLink().Visible() && getbackgroundDownLoadLink().Enabled();
					}
				}), Input.wait120);
				if (getbackgroundDownLoadLink().Visible() && getbackgroundDownLoadLink().Enabled())
					break;
			} catch (Exception e) {
				driver.Navigate().refresh();
				System.out.println("Refresh");
			}
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTaskType().Displayed();
			}
		}), Input.wait30);
		String status = getBatchPrintStatus().getText();
		Assert.assertEquals("COMPLETED", status);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDownLoadLink().Enabled();
			}
		}), Input.wait30);
		getDownLoadLink().waitAndClick(5);
		// download time
		Thread.sleep(10000);
	}

	/**
	 * @Modified By Jeevitha
	 * @param searchname             : Search Name of Source Selection
	 * @param orderCriteria
	 * @param orderType
	 * @param production             : If Production to be Selected
	 * @param prodName               : Production Name FOr Selection
	 * @param DisableSlipSheetToggle : If Sip Sheet Toggle to be Disabled
	 * @param randomProd             : Random Production To be Selected
	 * @throws InterruptedException
	 */
	public void BatchPrintWithProduction(String searchname, String orderCriteria, String orderType, boolean production,
			String prodName, boolean DisableSlipSheetToggle, boolean randomProd) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMenuBatchPrint().Visible();
			}
		}), Input.wait30);
		getMenuBatchPrint().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMySavedSearchArrow().Visible();
			}
		}), Input.wait30);
		getMySavedSearchArrow().Click();

		getSelectSavedSearch(searchname).waitAndClick(5);

		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSourcenextbutton().Enabled();
			}
		}), Input.wait30);
		getSourcenextbutton().waitAndClick(5);

		if (production) {

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getProductionRadioButton().Enabled();
				}
			}), Input.wait30);
			getProductionRadioButton().waitAndClick(5);

			if (randomProd) {
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getSelectProduction().Enabled();
					}
				}), Input.wait30);
				getSelectProduction().selectFromDropdown().selectByIndex(1);
			}

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSelectProduction().Enabled();
				}
			}), Input.wait30);
			getSelectProduction().selectFromDropdown().selectByVisibleText(prodName);

		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBasisnextbutton().Enabled();
			}
		}), Input.wait30);
		getBasisnextbutton().waitAndClick(5);
		Thread.sleep(2000);

		driver.scrollingToBottomofAPage();

		if (getSkippedFolderButton().isElementAvailable(4)) {
			getSkippedFolderButton().waitAndClick(5);
		} else {
			System.out.println("Skip Folder Toggle is Not Available");
		}
		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAnalysisnextbutton().Enabled();
			}
		}), Input.wait30);
		getAnalysisnextbutton().waitAndClick(5);

		if (getExceptionNext().isElementAvailable(8)) {
			getExceptionNext().waitAndClick(5);
		}

		driver.waitForPageToBeReady();
		if (DisableSlipSheetToggle) {
			base.waitForElement(getToggleButton());
			getToggleButton().waitAndClick(5);
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSlipnextbutton().Enabled();
			}
		}), Input.wait30);
		getSlipnextbutton().waitAndClick(5);

		if (getBrandingnextbutton().isElementAvailable(8)) {
			getBrandingnextbutton().waitAndClick(5);
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectExportFileName().Enabled();
			}
		}), Input.wait30);
		getSelectExportFileName().selectFromDropdown().selectByVisibleText("DocFileName");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectExportFileName().Enabled();
			}
		}), Input.wait30);
		getSelectExportFileSortBy().selectFromDropdown().selectByVisibleText("CustodianName");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPDFCreationforAllButton().Enabled();
			}
		}), Input.wait30);
		getPDFCreationforAllButton().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getGenerateButton().Enabled();
			}
		}), Input.wait30);
		getGenerateButton().waitAndClick(5);

		base.VerifySuccessMessage(
				"Successfully initiated the batch print. You will be prompted with notification once completed.");

		for (int i = 0; i < 40; i++) {
			if (getbackgroundDownLoadLink().isElementAvailable(20)) {
				base.stepInfo("DOWNLOAD link is Available");
				break;
			} else {
				driver.Navigate().refresh();
				System.out.println("Refresh");
				driver.waitForPageToBeReady();
			}
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTaskType().Displayed();
			}
		}), Input.wait30);
		String status = getBatchPrintStatus().getText();
		Assert.assertEquals("COMPLETED", status);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDownLoadLink().Enabled();
			}
		}), Input.wait30);
		getDownLoadLink().waitAndClick(5);
		// download time
		Thread.sleep(10000);
	}

	// *******To verify that batch print can give error if MP3 file is not native
	// file ****************
	public void BatchPrintWitMP3(final String searchname) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMenuBatchPrint().Visible();
			}
		}), Input.wait30);
		getMenuBatchPrint().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMySavedSearchArrow().Visible();
			}
		}), Input.wait30);
		getMySavedSearchArrow().Click();

		getSelectSavedSearch(searchname).waitAndClick(15);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSourcenextbutton().Enabled();
			}
		}), Input.wait30);
		getSourcenextbutton().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBasisnextbutton().Enabled();
			}
		}), Input.wait30);
		getBasisnextbutton().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAnalysisnextbutton().Enabled();
			}
		}), Input.wait30);
		getAnalysisnextbutton().waitAndClick(5);

		driver.scrollingToBottomofAPage();

		try {
			getBP_Exception_Other().Displayed();

			getOther_InsertMetadata().waitAndClick(5);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getselectMetadataFields_Other().Visible();
				}
			}), Input.wait30);
			getselectMetadataFields_Other().selectFromDropdown().selectByVisibleText("MasterDate");

			getOkButton_Other().waitAndClick(5);
		} catch (Exception e1) {

			System.out.println("No Other file displayed");
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getExceptiontypenextbutton().Enabled();
			}
		}), Input.wait30);
		getExceptiontypenextbutton().waitAndClick(5);

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectSourceDOCID().Visible();
			}
		}), Input.wait30);
		getSelectSourceDOCID().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectDOCID().Visible();
			}
		}), Input.wait30);
		getSelectDOCID().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectMasterDate().Visible();
			}
		}), Input.wait30);
		getSelectMasterDate().waitAndClick(5);

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectColumnAddtoSelected().Enabled();
			}
		}), Input.wait30);
		getSelectColumnAddtoSelected().waitAndClick(5);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSlipnextbutton().Enabled();
			}
		}), Input.wait30);
		getSlipnextbutton().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBrandingCentre().Visible();
			}
		}), Input.wait30);
		getBrandingCentre().waitAndClick(5);

		getBatchPrintEnterBranding().waitAndFind(5);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBatchPrintEnterBranding().Enabled();
			}
		}), Input.wait30);
		getBatchPrintEnterBranding().ScrollTo();
		new Actions(driver.getWebDriver()).moveToElement(getBatchPrintEnterBranding().getWebElement()).click();

		getBatchPrintEnterBranding().SendKeys("Test");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getInsertMetadataFieldOKButton().Visible();
			}
		}), Input.wait30);
		getInsertMetadataFieldOKButton().waitAndClick(5);
		Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBrandingnextbutton().Visible();
			}
		}), Input.wait30);
		getBrandingnextbutton().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectExportFileName().Enabled();
			}
		}), Input.wait30);
		getSelectExportFileName().selectFromDropdown().selectByVisibleText("DocID");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectExportFileName().Enabled();
			}
		}), Input.wait30);
		getSelectExportFileSortBy().selectFromDropdown().selectByVisibleText("MasterDate");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getGenerateButton().Enabled();
			}
		}), Input.wait30);
		getGenerateButton().waitAndClick(5);

		base.VerifySuccessMessage(
				"Successfully initiated the batch print. You will be prompted with notification once completed.");

		// base = new BaseClass(driver);
		// base.BckTaskClick();
		for (int i = 0; i < 5; i++) {
			try {
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getbackgroundDownLoadLink().Visible() && getbackgroundDownLoadLink().Enabled();
					}
				}), Input.wait30);
				if (getbackgroundDownLoadLink().Visible() && getbackgroundDownLoadLink().Enabled())
					break;
			} catch (Exception e) {
				driver.Navigate().refresh();
				System.out.println("Refresh");
			}
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTaskType().Displayed();
			}
		}), Input.wait30);
		String status = getBatchPrintStatus().getText();
		Assert.assertEquals("COMPLETED", status);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getErrorInfoLink().Enabled();
			}
		}), Input.wait30);
		getErrorInfoLink().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getErrortext().Displayed();
			}
		}), Input.wait30);
		String ErrorText = getErrortext().getText();
		Assert.assertEquals("The following operation has failed: Detail: Cannot print this document,"
				+ " as file type cannot be recognized for this document.", ErrorText);

		getCloseButton().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDownLoadLink().Enabled();
			}
		}), Input.wait30);
		getDownLoadLink().waitAndClick(5);
		// download time
		Thread.sleep(5000);
	}

	/**
	 * @author Jeevitha Description: Verifies Search in BAtch Print Page
	 * @param SearchName
	 * @return
	 */
	public boolean saveSearchRadiobutton(String SearchName) {
		boolean searchStatus = false;
		try {
			getSelectRadioButton().waitAndClick(5);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSavedSearch_MySearchTab().Visible();
				}
			}), Input.wait60);
			getSavedSearch_dropdown().waitAndClick(5);
			driver.scrollingToBottomofAPage();
			if (getSelectSavedSearch(SearchName).isElementAvailable(5)) {
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getSelectSavedSearch(SearchName).Visible();
					}
				}), Input.wait120);
				getSelectSavedSearch(SearchName).waitAndClick(20);
				System.out.println(SearchName + " is Selected");
				base.stepInfo(SearchName + " is Selected");

				driver.scrollPageToTop();
				getbtnNext().waitAndClick(20);
				UtilityLog.info("Saved search with  name  " + SearchName);

				searchStatus = true;
			} else {
				base.stepInfo(SearchName + " is Not Displayed");
			}
		} catch (Exception e) {
			System.out.println("Search Group of Rmu is not Present in Project Administration");
			UtilityLog.info("Search Group of RMU is not Present in Project Administration");

		}
		return searchStatus;
	}

	/**
	 * @author Gopinath
	 * @Description: Verifies Search in BAtch Print Page
	 * @param searchName : searchName is String value that name of search.
	 */
	public void BatchPrintWithProduction(String searchname) {
		try {
			driver.getWebDriver().get(Input.url + "BatchPrint/");
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getMenuBatchPrint().Visible();
				}
			}), Input.wait30);
			getMenuBatchPrint().Click();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getMySavedSearchArrow().Visible();
				}
			}), Input.wait30);
			getMySavedSearchArrow().Click();

			getSelectSavedSearch(searchname).waitAndClick(5);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSourcenextbutton().Enabled();
				}
			}), Input.wait30);
			getSourcenextbutton().waitAndClick(5);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getBasisnextbutton().Enabled();
				}
			}), Input.wait30);
			getBasisnextbutton().waitAndClick(5);
			base.waitTime(2);

			driver.scrollPageToTop();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAnalysisnextbutton().Enabled();
				}
			}), Input.wait30);
			getAnalysisnextbutton().waitAndClick(5);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while entering to exception file types" + e.getLocalizedMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description: Slip sheet to batch print creation
	 * @param metaData : metaData is String value that name of metadata.
	 */
	public void slipSheetToBatchPrintCreation(String metaData) {
		try {
			driver.scrollingToBottomofAPage();
			getExceptionFileTypeException().isElementAvailable(10);
			getExceptionFileTypeException().Click();
			driver.scrollPageToTop();
			getExceptionNext().isElementAvailable(10);
			getExceptionNext().Click();
			getMetaDataCheckbox(metaData).isElementAvailable(10);
			getMetaDataCheckbox(metaData).Click();
			driver.scrollingToBottomofAPage();
			getAddToSelectedButton().isElementAvailable(10);
			getAddToSelectedButton().Click();
			driver.scrollPageToTop();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSlipnextbutton().Enabled();
				}
			}), Input.wait30);
			getSlipnextbutton().waitAndClick(5);

			getBrandingAndRedactionNextButton().isElementAvailable(10);
			getBrandingAndRedactionNextButton().Click();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getGenerateButton().Enabled();
				}
			}), Input.wait30);
			getGenerateButton().waitAndClick(5);

			base.VerifySuccessMessage(
					"Successfully initiated the batch print. You will be prompted with notification once completed.");

			for (int i = 0; i < 10; i++) {
				try {

					if (getbackgroundDownLoadLink().isDisplayed()) {
						break;
					} else {
						driver.Navigate().refresh();
						System.out.println("Refresh");
					}

				} catch (Exception e) {
					driver.Navigate().refresh();
					System.out.println("Refresh");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while slip sheet to batch print" + e.getLocalizedMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description: Verify excel file printing issues without slip excel print.
	 */
	public void verifyPrintExcelFileIssuesWithoutSkipExcelPrint() {
		try {
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			getExcelFilesText().isElementAvailable(10);
			if (getExcelFilesText().isDisplayed()) {
				base.passedStep(
						"In your selected documents, 17 docs are Excel files. Many of these have printing issues. -- text displayed succuessfully");
			} else {
				base.failedStep(
						"In your selected documents, 17 docs are Excel files. Many of these have printing issues. -- text not displayed");

			}
			if (getPrintPlaceholdersForDocs().isDisplayed()) {
				base.passedStep(
						"Do you want to print them or skip them? If skipping them, do you want to include placeholders for these docs? -- text displayed succuessfully");
			} else {
				base.failedStep(
						"Do you want to print them or skip them? If skipping them, do you want to include placeholders for these docs? -- text not displayed");

			}
			if (getPrintExcelFileRadioButton().isDisplayed()) {
				base.passedStep("Print excel file toogle is displayed successsfully");
			} else {
				base.failedStep("Print excel file toogle is not displayed");
			}
			if (getSkipExcelFileRadioButton().isDisplayed()) {
				base.passedStep("Skip excel file toogle is displayed successsfully");
			} else {
				base.failedStep("Skip excel file toogle is not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while verifying excel file printing issues without slip excel print."
					+ e.getLocalizedMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description: Clickon excel file printing issues without slip excel print and
	 *               disable place holder toogle.
	 */
	public void clickOnSkipExcelFilesAndDisablePlaceHolderToogle(boolean flag) {
		try {
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			getSkipExcelFileRadioButton().isElementAvailable(10);
			getSkipExcelFileRadioButton().Click();
			driver.waitForPageToBeReady();
			String value = getIncludePlaceHolderToogle().GetAttribute("class");
			if (flag == false && value.contains("active")) {
				base.passedStep("Excel include place holder toogle is already enabled");
			} else if (!(value.contains("active")) && flag == false) {
				getIncludePlaceholdersToogle().isElementAvailable(10);
				getIncludePlaceholdersToogle().Click();
				base.passedStep("Excel include place holder toogle is enabled successfully");
			} else if (value.contains("active") && flag == true) {
				getIncludePlaceholdersToogle().isElementAvailable(10);
				getIncludePlaceholdersToogle().Click();
				base.passedStep("Excel include place holder toogle is disabled successfully");
			} else if (!(value.contains("active")) && flag == true) {
				base.passedStep("Excel include place holder toogle is already disabled");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured whileClickon excel file printing issues without slip excel print and disable place holder toogle."
							+ e.getLocalizedMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @Description: Verify Skip place holder and insert meta data.
	 */
	public void verifyRedactorSkipPlaceHolderAndSelectMetaData(String metaData) {
		try {
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			getExcelMetaDataLink().isElementAvailable(10);
			getExcelMetaDataLink().Click();
			driver.waitForPageToBeReady();
			getExcelSkipMetaDataDropdown().isElementAvailable(10);
			getExcelSkipMetaDataDropdown().selectFromDropdown().selectByVisibleText(metaData);
			getExcelOkButton().isElementAvailable(10);
			getExcelOkButton().Click();
			driver.scrollPageToTop();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured whileClickon excel file printing issues without slip excel print and disable place holder toogle."
							+ e.getLocalizedMessage());

		}
	}

	/**
	 * @author Brundha
	 * @Description: Method to select a folder in batch sprint
	 * @param searchName : Tagname in String value .
	 */
	public void BatchPrintWithSourceSelection(String name) {

		driver.getWebDriver().get(Input.url + "BatchPrint/");
		base.waitForElement(getMenuBatchPrint());
		getMenuBatchPrint().Click();
		getFolderBatchPrint().isElementAvailable(10);
		getFolderBatchPrint().waitAndClick(10);
		getAllFoldersArrow().isElementAvailable(10);
		getAllFoldersArrow().waitAndClick(10);
		getSelectFolder(name).isElementAvailable(10);
		getSelectFolder(name).waitAndClick(10);
		base.waitForElement(getSourcenextbutton());
		getSourcenextbutton().waitAndClick(5);
		base.waitForElement(getBasisnextbutton());
		getBasisnextbutton().waitAndClick(5);
		base.waitTime(2);
		driver.scrollPageToTop();
		getAnalysisnextbutton().isElementAvailable(10);
		getAnalysisnextbutton().waitAndClick(5);

	}

	/**
	 * @author Brundha
	 * @Description: filling Batch Redaction page
	 * @param metaData : metaData is String value that name of metadata.
	 */
	public void FillingBatchRedactionAndverifyingTheDownloadInBatchSprint(String metaData) {

		getExceptionNext().isElementAvailable(10);
		getExceptionNext().Click();
		getMetaDataCheckbox(metaData).isElementAvailable(10);
		getMetaDataCheckbox(metaData).Click();
		driver.scrollingToBottomofAPage();
		getAddToSelectedButton().isElementAvailable(10);
		getAddToSelectedButton().Click();
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSlipnextbutton().Enabled();
			}
		}), Input.wait30);
		getSlipnextbutton().waitAndClick(5);
		getBrandingAndRedactionNextButton().isElementAvailable(10);
		getBrandingAndRedactionNextButton().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getGenerateButton().Enabled();
			}
		}), Input.wait30);
		getGenerateButton().waitAndClick(5);
		base.VerifySuccessMessage(
				"Successfully initiated the batch print. You will be prompted with notification once completed.");
		for (int i = 0; i < 10; i++) {
			try {
				if (getbackgroundDownLoadLink().isDisplayed()) {
					getbackgroundDownLoadLink().Click();
					break;
				} else {
					driver.Navigate().refresh();
					System.out.println("Refresh");
				}
			} catch (Exception e) {
				driver.Navigate().refresh();
				System.out.println("Refresh");
			}
		}
	}

	/**
	 * @author Gopinath
	 * @Description: Verify details displayed for media files.
	 */
	public void verifyDetailsDisplayedForMediaFiles() {
		try {
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			getExcelFilesText().isElementAvailable(10);
			if (getMediaFilesPrintableText().isDisplayed()) {
				base.passedStep(
						"In your selected documents, 12 docs are media files. These will be skipped, as they are not printable -- text displayed succuessfully");
			} else {
				base.failedStep(
						"In your selected documents, 12 docs are media files. These will be skipped, as they are not printable. -- text not displayed");

			}
			if (getIncludePlaceHolderText().isDisplayed()) {
				base.passedStep("Do you want to include placeholder for these docs? -- text displayed succuessfully");
			} else {
				base.failedStep("Do you want to include placeholder for these docs? -- text not displayed");

			}
			if (getMediaFilesToogle().isDisplayed()) {
				base.passedStep("Media files toogle is displayed successsfully");
			} else {
				base.failedStep("Media files file toogle is not displayed");
			}

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while verify details displayed for media files." + e.getLocalizedMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description: Verify media files text field displayed by enabling toogle.
	 */
	public void verifyMediaFilesFieldsDisplayedByEnablingToogle() {
		try {
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			getMediaFilesToogle().isElementAvailable(10);
			if (getMediaFilesToogle().isDisplayed()) {
				base.passedStep("Media files toogle is displayed successsfully");
			} else {
				base.failedStep("Media files file toogle is not displayed");
			}
			if (getIncludeMediaCheckBox().GetAttribute("class").contains("active")) {
				base.passedStep("Media file toogle is enabled");
			} else {
				base.stepInfo("Media file toogle is not enabled");
				getMediaFilesToogle().Click();
			}
			driver.waitForPageToBeReady();
			getMediaFileEditor().isElementAvailable(10);
			if (getMediaFileEditor().isDisplayed()) {
				base.passedStep("Media files text field editor is displayed successfully");
			} else {
				base.failedStep("Media files text field editor is not displayed");
			}
			if (getMediaFileMetaDataLink().isDisplayed()) {
				base.passedStep("Media files meta data link is displayed successfully");
			} else {
				base.failedStep("Media files meta data link is not displayed");
			}

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while  Verify media files text field displayed by enabling toogle."
					+ e.getLocalizedMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description: Verify media files text field displayed by disabling toogle.
	 */
	public void verifyMediaFilesFieldsDisplayedByDisableToogle() {
		try {
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			getMediaFilesToogle().isElementAvailable(10);
			if (getMediaFilesToogle().isDisplayed()) {
				base.passedStep("Media files toogle is displayed successsfully");
			} else {
				base.failedStep("Media files file toogle is not displayed");
			}
			if (getIncludeMediaCheckBox().GetAttribute("class").contains("active")) {
				base.stepInfo("Media file toogle is enabled");
				getMediaFilesToogle().Click();
			} else {
				base.stepInfo("Media file toogle is not enabled");
			}
			if (!getMediaFileEditor().isDisplayed()) {
				base.passedStep("Media files text field editor is not displayed");
			} else {
				base.failedStep("Media files text field editor is displayed");
			}
			if (!getMediaFileMetaDataLink().isDisplayed()) {
				base.passedStep("Media files meta data link is not displayed");
			} else {
				base.failedStep("Media files meta data link is displayed");
			}

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while verify media files text field displayed by disabling toogle."
					+ e.getLocalizedMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description: Method to insert metadata field for media files
	 */
	public void insertMetaDataFieldForMediaFiles(String metaData) {
		try {
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			getMediaFilesToogle().isElementAvailable(10);
			if (getMediaFilesToogle().isDisplayed()) {
				base.passedStep("Media files toogle is displayed successsfully");
			} else {
				base.failedStep("Media files file toogle is not displayed");
			}
			if (getIncludeMediaCheckBox().GetAttribute("class").contains("active")) {
				base.passedStep("Media file toogle is enabled");
			} else {
				base.stepInfo("Media file toogle is not enabled");
				getMediaFilesToogle().Click();
			}
			driver.waitForPageToBeReady();
			getMediaFileMetaDataLink().isElementAvailable(10);
			getMediaFileMetaDataLink().Click();
			driver.waitForPageToBeReady();
			getMediaFilFieldDropdown().isElementAvailable(10);
			getMediaFilFieldDropdown().selectFromDropdown().selectByVisibleText(metaData);
			getMediaFilFieldOkButton().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while insert metadata field for media files" + e.getLocalizedMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description method to navigate BatchPrint page
	 */
	public void navigateToBatchPrintPage() {
		try {
			driver.getWebDriver().get(Input.url + "BatchPrint/");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while navigating BatchPrint page." + e.getMessage());

		}
	}

	/**
	 * @Author Jeevitha
	 * @Description : Filling source Selection TAb Of BAtch Print PAge
	 * @param select           : To Select Search Or TAg Or FOlder
	 * @param searchOrTagOrFol : SaerchNAme OR TAgNAme OR FOlderNAme
	 * @param Next             : Next Button
	 */
	public boolean fillingSourceSelectionTab(String select, String searchOrTagOrFol, boolean Next) {
		if (getSelectRadioButton().isElementAvailable(3) && getTagBatchPrint().isElementAvailable(3)
				&& getFolderBatchPrint().isElementAvailable(3)) {
			System.out.println("Select Search , Select Tag & Select Folder is Displayed");
			base.stepInfo("Select Search , Select Tag & Select Folder is Displayed");
		}
		boolean flag = false;
		if (select.equalsIgnoreCase("Search")) {
			saveSearchRadiobutton(searchOrTagOrFol);

		} else if (select.equalsIgnoreCase("Tag")) {
			base.waitForElement(getTagBatchPrint());
			getTagBatchPrint().waitAndClick(10);
			base.waitForElement(getAllTagsArrow());
			getAllTagsArrow().waitAndClick(10);

			if (getSelectTag(searchOrTagOrFol).isElementAvailable(10)) {
				base.waitForElement(getSelectTag(searchOrTagOrFol));
				getSelectTag(searchOrTagOrFol).waitAndClick(10);
				System.out.println("Selected Tag : " + searchOrTagOrFol);
				base.passedStep("Selected Tag From Source Selection Tab: " + searchOrTagOrFol);
				flag = true;
			} else {
				flag = false;
			}

		} else if (select.equalsIgnoreCase("Folder")) {
			base.waitForElement(getFolderBatchPrint());
			getFolderBatchPrint().waitAndClick(10);
			base.waitForElement(getAllFoldersArrow());
			getAllFoldersArrow().waitAndClick(10);

			if (getSelectTag(searchOrTagOrFol).isElementAvailable(10)) {
				base.waitForElement(getSelectFolder(searchOrTagOrFol));
				getSelectFolder(searchOrTagOrFol).waitAndClick(10);
				System.out.println("Selected Folder : " + searchOrTagOrFol);
				base.passedStep("Select Folder From Sorce Selection Tab is : " + searchOrTagOrFol);
				flag = true;
			} else {
				flag = false;
			}
		}
		if (Next) {
			navigateToNextPage(1);
		}
		return flag;
	}

	/**
	 * @AUthor Jeevitha
	 * @Description : Filling Basis For Printing Tab
	 * @param Native : If Native Checkbox or not
	 * @param Next   : Next Button
	 */
	public void fillingBasisForPrinting(boolean Native, boolean Next, String production) {
		driver.waitForPageToBeReady();

		if (Native) {
			base.waitForElement(getNativeCB_BasisPage());
			getNativeCB_BasisPage().waitAndClick(3);
			base.stepInfo("Checked Viewable file variant in DocView (typically natives)");
		} else {
			base.waitForElement(getProductionRadioButton());
			getProductionRadioButton().waitAndClick(5);

			base.waitForElement(getSelectProduction());
			getSelectProduction().selectFromDropdown().selectByVisibleText(production);
		}

		if (Next) {
			navigateToNextPage(1);
		}
	}

	/**
	 * @Author Jeevitha
	 * @Description : Clicks On next Button and Returns Navigated Page Header
	 * @param count :no of times to click NEXT btn
	 */
	public String navigateToNextPage(int count) {
		String Header = null;
		for (int i = 1; i <= count; i++) {
			driver.scrollPageToTop();
			base.waitForElement(getbtnNext());
			getbtnNext().waitAndClick(10);
			driver.waitForPageToBeReady();

			base.waitForElement(getPageHeader());
			Header = getPageHeader().getText();
			System.out.println("Navigated To : " + Header);
			base.passedStep("Navigated To : " + Header);
		}
		return Header;
	}

	/**
	 * @Author Jeevitha
	 * @Description : filling slipSheet with METADATA
	 * @param metadata : Metadata to select
	 * @param Next     : Next Button
	 */
	public void fillingSlipSheetWithMetadata(String metadata, boolean Next, String[] listofMetadata) {
		driver.scrollingToBottomofAPage();
		if (metadata != null) {
			base.waitForElement(getMetaDataCheckbox(metadata));
			getMetaDataCheckbox(metadata).waitAndClick(5);
			System.out.println("Selected METADATA : " + metadata);
			base.stepInfo("Selected METADATA from SlipSheets Is : " + metadata);
		} else {
			for (String meta : listofMetadata) {
				base.waitForElement(getMetaDataCheckbox(meta));
				getMetaDataCheckbox(meta).waitAndClick(5);
				System.out.println("Selected METADATA : " + meta);
				base.stepInfo("Selected METADATA from SlipSheets Is : " + meta);
			}
		}
		driver.scrollingToBottomofAPage();
		base.waitForElement(getAddToSelectedButton());
		getAddToSelectedButton().waitAndClick(5);

		if (Next) {
			navigateToNextPage(1);
		}
	}

	/**
	 * @Author Jeevitha
	 * @Description :filling export page and verifying complete tstaus in Background
	 *              task page
	 * @param exportFile   :export Filen Name
	 * @param sortBy       : sort by DropDown
	 * @param onePdfForAll : checkBox One pdf for all doc
	 * @param refreshCount : no of times to refresh in BG page
	 * @param Download     : download link
	 */
	public void fillingExportFormatPage(String exportFile, String sortBy, boolean onePdfForAll, int refreshCount) {
//     	base.waitForElement(getSelectExportFileName());
		getSelectExportFileName().selectFromDropdown().selectByVisibleText(exportFile);

		base.waitForElement(getSelectExportFileSortBy());
		getSelectExportFileSortBy().selectFromDropdown().selectByVisibleText(sortBy);
		base.stepInfo("Selected Export File Name : " + exportFile + "  Selected Sort By : " + sortBy);

		if (onePdfForAll) {
			base.waitForElement(getPDFCreationforAllButton());
			getPDFCreationforAllButton().waitAndClick(10);
			base.stepInfo("One PDF for all documents is CHECKED");
		}

		base.waitForElement(getGenerateButton());
		getGenerateButton().waitAndClick(5);

		if (base.getSuccessMsgHeader().isElementAvailable(3)) {
			base.VerifySuccessMessage(
					"The Batch Print has been successfully initiated. You will be notified once it has completed.");
		}

		// verifying In Background ask Page
		driver.waitForPageToBeReady();
		base.waitForElement(getBgPageDD());
		String expURL = Input.url + "Background/BackgroundTask";
		String currentUrl = driver.getUrl();
		base.textCompareEquals(expURL, currentUrl, "Navigated to My backgroud task page.", "");

		// verify INPROGRESS status
		base.waitForElement(getBatchPrintStatus());
		String inprogressStatus = getBatchPrintStatus().getText();
		base.textCompareEquals("INPROGRESS", inprogressStatus, "Batch Print Entry Status is in : INPROGRESS",
				"Batch Print Status Is Not As Expected");

		for (int i = 0; i < refreshCount; i++) {

			if (getbackgroundDownLoadLink().isDisplayed()) {
				base.stepInfo("DOWNLOAD link is Available");
				break;
			} else {
				driver.Navigate().refresh();
				System.out.println("Refresh");
				driver.waitForPageToBeReady();
			}

		}
		base.waitForElement(getBatchPrintStatus());
		String status = getBatchPrintStatus().getText();
		base.textCompareEquals("COMPLETED", status, "Batch Print Status IS Updated As COMPLETED",
				"Batch Print Status Is Not As Expected");

	}

	/**
	 * @Author Jeevitha
	 * @Description : Downloaded latest batch print file
	 * @return : retruns file name
	 */
	public String DownloadBatchPrintFile() {
		base.waitForElement(getDownLoadLink());
		getDownLoadLink().waitAndClick(10);

		base.waitTime(5);
		File ab = new File(Input.fileDownloadLocation);
		String testPath = ab.toString() + "\\";

		// wait until file download is complete
		base.waitUntilFileDownload();

		// base.csvReader();
		ReportsPage report = new ReportsPage(driver);
		File a = report.getLatestFilefromDir(testPath);
		System.out.println(a.getName());
		base.stepInfo(a.getName());

		String fileName = a.getName();

		String fielPath = testPath + fileName;
		System.out.println(fileName);
		base.stepInfo("Downloade File  : " + fielPath);
		return fileName;

	}

	/**
	 * @Author Jeevitha
	 * @Description : Extracts ZIp File And Creates Another Folder And Place The
	 *              extracted file in it
	 * @param fileName : Zip file name
	 * @return : return newFolder created file name
	 * @throws ZipException
	 */
	public String extractFile(String fileName) throws ZipException {
		String extractedfile = "Extracted File" + Utility.dynamicNameAppender();

		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");

		ZipFile zipFile = new ZipFile(Input.fileDownloadLocation + "\\" + fileName);
		zipFile.extractAll(Input.fileDownloadLocation + "\\" + extractedfile);

		System.out.println("Unzipped the downloaded files");
		driver.waitForPageToBeReady();
		base.stepInfo("Downloaded zip file was extracted");
		return extractedfile;

	}

	/**
	 * @Author Jeevitha
	 * @Description : verifies Downloaded file Count, Gets File Names and verify
	 *              File format
	 * @param filePath : PAth OF FIle
	 * @return : Return Downloaded File Names
	 */
	public List<String> verifyDownloadedFileCountAndFormat(String filePath) {
		List<String> fileNames = new ArrayList<>();

		File dir = new File(filePath);
		File[] files = dir.listFiles();

		// Verify No of files Downloaded Count
		int noOfFile = files.length;
		System.out.println("Number of PDF FILE : " + noOfFile);
		if (noOfFile == 1) {
			base.stepInfo("One PDF for All Document  And Count IS : " + noOfFile);
		} else if (noOfFile > 1) {
			base.stepInfo("One PDF for Each Document  And Count IS : " + noOfFile);
		} else {
			base.failedStep("PDF Files Not Available ");
		}

		for (int i = 0; i < noOfFile; i++) {
			// Get File Names
			String filename = files[i].getName();
			System.out.println("Downloaded File Name : " + filename);

			// Verify File Extension
			String actualfileName = FilenameUtils.getBaseName(filename);
			String fileFormat = FilenameUtils.getExtension(filename);
			String expectedFormat = "pdf";

			String passMsg = "Downloaded File : " + filename + "    And Verified Format IS  : " + fileFormat;
			String failMsg = "Downloaded File Format is Not As Expected";
			base.textCompareEquals(fileFormat, expectedFormat, passMsg, failMsg);

			fileNames.add(filename);
		}
		return fileNames;
	}

	/**
	 * @Author Jeevitha
	 * @Description : verifies back bTn
	 * @param select
	 * @param searchOrTagOrFol
	 * @param Next
	 */
	public void navigateAndVerifyBackBtn(String select, String searchOrTagOrFol, boolean Next) {

		String[] listOfHeaders = { "Source Selection", "Basis for Printing", "Analysis", "Exception File Types",
				"Slip Sheets", "Branding and Redactions", "Export Format" };
		fillingSourceSelectionTab(select, searchOrTagOrFol, Next);
		String Header = getPageHeader().getText();
		SoftAssert softAssert = new SoftAssert();
		softAssert.assertEquals(listOfHeaders[0], Header);
		base.stepInfo("The Current Header : " + Header);
		for (int i = 1; i < listOfHeaders.length; i++) {

			String currentHeader = navigateToNextPage(1);
			base.ValidateElement_Presence(getBackButton(), "Back Button");
			softAssert.assertEquals(currentHeader, listOfHeaders[i]);

			if (currentHeader.equalsIgnoreCase("Export Format")) {
				base.ValidateElement_Presence(getGenerateButton(), "Generate Button");
			}
			if (currentHeader.equalsIgnoreCase("Slip Sheets")) {
				getToggleButton().waitAndClick(5);
			}
			if (currentHeader.equalsIgnoreCase("Exception File Types")) {
				getToggleButton().waitAndClick(5);
			}
		}
		softAssert.assertAll();
	}

	/**
	 * @Author Baskar.Modified by sowndarya on 03/10/22 (changes success message as
	 *         per new deployment)
	 * @Description :filling and generate the batch print
	 * @param exportFile   :export Filen Name
	 * @param sortBy       : sort by DropDown
	 * @param onePdfForAll : checkBox One pdf for all doc
	 */
	public void generateBatchPrint(String exportFile, String sortBy, boolean onePdfForAll) {
		base.waitForElement(getSelectExportFileName());
		getSelectExportFileName().selectFromDropdown().selectByVisibleText(exportFile);

		base.waitForElement(getSelectExportFileSortBy());
		getSelectExportFileSortBy().selectFromDropdown().selectByVisibleText(sortBy);
		base.stepInfo("Selected Export File Name : " + exportFile + "  Selected Sort By : " + sortBy);

		if (onePdfForAll) {
			base.waitForElement(getPDFCreationforAllButton());
			getPDFCreationforAllButton().waitAndClick(10);
			base.stepInfo("One PDF for all documents is CHECKED");
		}

		base.waitForElement(getGenerateButton());
		getGenerateButton().waitAndClick(5);

		base.VerifySuccessMessage(
				"The Batch Print has been successfully initiated. You will be notified once it has completed.");
	}

	/**
	 * @Author Baskar
	 * @Description :refreshing untill batch print
	 * @param refreshCount :export Filen Name
	 */
	public void refreshUntillComplete(int refreshCount) {
		// verifying In Background ask PAge
		driver.waitForPageToBeReady();
		base.waitForElement(getBgPageDD());
		String expURL = Input.url + "Background/BackgroundTask";
		String currentUrl = driver.getUrl();
		base.textCompareEquals(expURL, currentUrl, "Navigated to My backgroud task page.", "");

		for (int i = 0; i < refreshCount; i++) {

			if (getbackgroundDownLoadLink().isDisplayed()) {
				base.stepInfo("DOWNLOAD link is Available");
				break;
			} else {
				driver.Navigate().refresh();
				System.out.println("Refresh");
				driver.waitForPageToBeReady();
			}

		}
		base.waitForElement(getBatchPrintStatus());
		String status = getBatchPrintStatus().getText();
		base.textCompareEquals("COMPLETED", status, "Batch Print Status IS Updated As COMPLETED",
				"Batch Print Status Is Not As Expected");

	}

	/**
	 * @Author Indium-Baskar
	 * @Description : Downloaded latest batch print file
	 * @return : retruns file name
	 */
	public String DownloadBatchPrintFileBG(int Id) {
		base.waitForElement(getDownloadBatchFile(Id));
		getDownloadBatchFile(Id).waitAndClick(10);

		base.waitTime(25);
		File ab = new File(Input.fileDownloadLocation);
		String testPath = ab.toString() + "\\";

		// base.csvReader();
		ReportsPage report = new ReportsPage(driver);
		File a = report.getLatestFilefromDir(testPath);
		System.out.println(a.getName());
		base.stepInfo(a.getName());

		String fileName = a.getName();

		String fielPath = testPath + fileName;
		System.out.println(fileName);
		base.stepInfo("Downloade File  : " + fielPath);
		return fileName;
	}

	/**
	 * @Author Jeevitha
	 * @Description : Filling Analysis Tab
	 * @param clickNative
	 * @param clickPrintTran
	 * @param clickSkipDoc
	 * @param clickIgnore
	 */
	public void fillingAnalysisTab(boolean clickNative, boolean clickPrintTran, boolean clickSkipDoc,
			boolean clickIgnore) {
		SoftAssert softassert = new SoftAssert();

		base.waitForElement(getPageHeader());
		String Header = getPageHeader().getText();
		softassert.assertEquals(Header, "Analysis");

		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();

		if (getAnalysisTransTextOfNaive().isElementAvailable(15)) {
			String actualMsg = getAnalysisTransTextOfNaive().getText();
			String expectedMsg = " documents that have one or more translated text of the natives. You must select what you want to do with these ";

			String passMsg = "Displayed message : " + actualMsg;
			String failMsg = "Expected message is Not Displayed";
			base.compareTextViaContains(actualMsg, expectedMsg, passMsg, failMsg);
		}

		boolean nativeBtn = getAnalysisNativeBtn().isElementPresent();
		if (nativeBtn) {
			String nativeRadioBtn = getAnalysisNativeBtn().getText();
			System.out.println(nativeRadioBtn + " : RadioBtn is Present");
			base.passedStep(nativeRadioBtn + " : RadioBtn is Present");

			if (clickNative) {
				getAnalysisNativeBtn().waitAndClick(10);
				base.stepInfo(nativeRadioBtn + " : RadioBtn is Clicked");
			}
		} else {
			base.failedStep("NAtive Radio Button is Not Dispalyed");
		}

		boolean printTranBtn = getAnalysisPrintTranBtn().isElementPresent();
		if (printTranBtn) {
			String printTranRadioBtn = getAnalysisPrintTranBtn().getText();
			System.out.println(printTranRadioBtn + " : RadioBtn is Present");
			base.passedStep(printTranRadioBtn + " : RadioBtn is Present");

			if (clickPrintTran) {
				getAnalysisPrintTranBtn().waitAndClick(10);
				base.stepInfo(printTranRadioBtn + " : RadioBtn is Clicked");
			}
		} else {
			base.failedStep("print translated text Radio Button is Not Dispalyed");
		}

		boolean ignoreBtn = getAnalysisIgnoreBtn().isElementPresent();
		if (ignoreBtn) {
			String ignoreRadioBtn = getAnalysisIgnoreBtn().getText();
			System.out.println(ignoreRadioBtn + " : RadioBtn is Present");
			base.passedStep(ignoreRadioBtn + " : RadioBtn is Present");

			if (clickIgnore) {
				getAnalysisIgnoreBtn().waitAndClick(10);
				base.stepInfo(ignoreRadioBtn + " : RadioBtn is Clicked");
			}
		}

		boolean skipDocBtn = getAnalysisSkipDocBtn().isElementPresent();
		if (skipDocBtn) {
			String skipDocRadioBtn = getAnalysisSkipDocBtn().getText();
			System.out.println(skipDocRadioBtn + " : RadioBtn is Present");
			base.passedStep(skipDocRadioBtn + " : RadioBtn is Present");

			if (clickSkipDoc) {
				getAnalysisSkipDocBtn().waitAndClick(10);
				base.stepInfo(skipDocRadioBtn + " : RadioBtn is Clicked");
				driver.waitForPageToBeReady();

				if (getAnalysSkipDocFolder_DD().isElementAvailable(15)) {
					getAnalysSkipDocFolder_DD().waitAndClick(10);
				}
				if (getAnalysSkipDocFolder_List().isElementAvailable(10)) {
					base.passedStep("All Created Folders is Displayed");
				} else {
					base.failedStep("All Created Folders is Not Displayed");
				}

			}
		} else {
			base.failedStep("Skip document Radio Button is Not Dispalyed");
		}

		softassert.assertAll();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Disable slipsheet toggle
	 * @param Next
	 */
	public void disableSlipSheet(boolean Next) {
		if (getToggleButton().isElementAvailable(10)) {
			getToggleButton().waitAndClick(5);

			base.passedStep("Disables Slipsheet Toggle");
		}

		if (Next) {
			navigateToNextPage(1);
		}
	}

	/**
	 * @Author Jeevitha
	 * @Description : filling exception file type tab
	 * @param Next
	 */
	public void fillingExceptioanlFileTypeTab(Boolean skipExcel, String Metadata, String[] listOfMetadata,
			Boolean Next) {
		if (skipExcel) {
			base.waitForElement(getAnalysis_SkipExcelFiles_RadioButton());
			getAnalysis_SkipExcelFiles_RadioButton().waitAndClick(5);
		}

		driver.scrollingToBottomofAPage();

		if (getExceptionFileTypeException().isElementAvailable(10)) {

			if (Metadata != null) {
				getOther_InsertMetadata().waitAndClick(5);
				base.waitForElement(getselectMetadataFields_Other());
				getselectMetadataFields_Other().selectFromDropdown().selectByVisibleText(Metadata);
				getOkButton_Other().waitAndClick(5);
				System.out.println("Selected Metdata : " + Metadata);
				base.stepInfo("Selected Metdata : " + Metadata);
			} else {
				for (String meta : listOfMetadata) {
					getOther_InsertMetadata().waitAndClick(5);
					base.waitForElement(getselectMetadataFields_Other());
					getselectMetadataFields_Other().selectFromDropdown().selectByVisibleText(meta);
					getOkButton_Other().waitAndClick(5);

					System.out.println("Selected Metdata : " + meta);
					base.stepInfo("Selected Metdata : " + meta);
				}
			}

		} else {
			System.out.println("No Exceptional file displayed");
			base.stepInfo("No Exceptional file displayed");

		}

		if (Next) {
			navigateToNextPage(1);
		}
	}

	/**
	 * @Author Jeevitha
	 * @Description : select Dropdown In slipsheet
	 * @param ddValue
	 */
	public void selectDropdownFromSlipSheet_prod(String ddValue) {
		base.waitForElement(getSlipSheetDD_prod());
		getSlipSheetDD_prod().selectFromDropdown().selectByVisibleText(ddValue);
	}

	public void selectSortingFromExportPage(String ddValue) {
		base.waitForElement(getAscAndDescDDlist());
		getAscAndDescDDlist().selectFromDropdown().selectByVisibleText(ddValue);
		base.stepInfo("Selected Sort from Export format  tab is : " + ddValue);
	}

	/**
	 * @Author Jeevitha
	 * @Description : to verify downloaded filename is as expected when downloaded
	 *              for one pdf for all doc
	 * @param source
	 * @param compareString
	 * @param passMsg
	 * @param failMsg
	 * @throws InterruptedException
	 */
	public void compareListWithStringviaContains(List<String> source, String compareString, String passMsg,
			String failMsg) throws InterruptedException {
		boolean compare = false;
		for (String actualValue : source) {

			String basevalue = actualValue.replace(":", " ");
			String compare1 = compareString.replace(".pdf", "");
			String compare2 = compare1.replaceAll("\\[.*?\\]", "");
			String compareValue = compare2.replace("_", " ");

			if (basevalue.trim().contains(compareValue.trim())) {
				base.stepInfo("Expected Name & Compare Name are Same : " + compareValue);
				compare = true;
				break;
			} else {
				compare = false;
			}
		}
		if (compare) {
			base.passedStep(passMsg);
		} else {
			base.failedStep(failMsg);
		}
	}

	/**
	 * @authorJeevitha
	 * @Description : clcik back button and verify current and navigated tab
	 * @param elementName
	 */
	public void ClickBackButtonAndVerify(boolean verifyCurrentPage, String expectCurrentTab, int noOfTime,
			boolean verifyNavigatedTab, String expectNavigationTab) {
		boolean flag = true;
		for (int i = 1; i <= noOfTime; i++) {

			if (verifyCurrentPage) {
				base.waitForElement(getPageHeader());
				String Header = getPageHeader().getText();
				base.compareTextViaContains(Header, expectCurrentTab,
						"Current Tab before Clicking back button is : " + Header, "Current tab is not as expected");
			}

			if (getBackButton().isElementAvailable(3)) {
				getBackButton().waitAndClick(10);
				driver.waitForPageToBeReady();
				base.stepInfo("Clicked Back Button ");
			} else {
				base.failedStep("Back button is not available");
			}

			if (verifyNavigatedTab) {
				driver.waitForPageToBeReady();
				base.waitForElement(getPageHeader());
				String Header = getPageHeader().getText();
				base.compareTextViaContains(Header, expectNavigationTab,
						"Navigated Tab After Clicking back button is : " + Header, "Navigated tab is not as expected");
			}
		}
	}

	/**
	 * @Author jeevitha
	 * @Description : verify current tab
	 */
	public void verifyCurrentTab(String expectCurrentTab) {
		base.waitForElement(getPageHeader());
		String Header = getPageHeader().getText();
		base.compareTextViaContains(Header, expectCurrentTab, "Current Tab is as Expected : " + Header,
				"Current tab is not as expected");
	}

	/**
	 * @Author jeevitha
	 * @Description : verify message in analysis tab
	 */
	public void verifyAnalysisReportMessage() {
		String header = getAnalysisRequestHeader().getText();
		String expectedMsg = "Analysis of your request";
		base.textCompareEquals(header, expectedMsg, expectedMsg, "Expected header in Analysis Tab is not displayed");

		if (getAnalysisMessage().isElementAvailable(5)) {
			base.stepInfo("Analysis Message is displayed");
			String actualReport = getAnalysisMessage().getText();
			String expected = "You requested to print 0 documents.";
			base.compareTextViaContains(actualReport, expected, expected, "Expected message is not dispalyed");
		} else {
			base.failedStep("Analysis Message is not displayed in Analysis tab");
		}
	}

	/**
	 * @Author jeevitha
	 * @Description : Disable Media Toggle In Exception file type tab & verify media
	 *              file count
	 * @param verifyCount
	 * @param expectedCount
	 * @return
	 */
	public int DisableMediaToggle(boolean verifyCount, int expectedCount) {
		base.waitForElement(getMediaFilesPrintableText());
		String completeText = getMediaFilesPrintableText().getText();
		String[] count = completeText.split(" ");
		System.out.println("Media file count : " + count[4]);
		if (verifyCount) {
			base.digitCompareEquals(Integer.parseInt(count[4]), expectedCount, "Media File Count is : " + count[4],
					"Media file count is not as expected");
		}
		if (getIncludeMediaCheckBox().GetAttribute("class").contains("active")) {
			base.passedStep("Media file toogle is enabled");
			getMediaFilesToogle().waitAndClick(10);
			base.stepInfo("Media file toogle is Disabled");
		} else {
			base.passedStep("Media file toogle is Disabled");

		}

		return Integer.parseInt(count[4]);

	}

	/**
	 * @Author jeevitha
	 * @Description : filling and verifying branding and redaction tab
	 * @param enableRedactToggle
	 * @param configureAndverifyPosition
	 * @param configureTxt
	 */
	public void verifyBrandingAndReadctTab(boolean enableRedactToggle, boolean configureAndverifyPosition,
			String configureTxt, boolean verifyColour) {
		verifyCurrentTab("Branding and Redactions");
		boolean flag = true;
		driver.waitForPageToBeReady();
		// ON/OFF Include Applied Redaction toggle
		if (getRedactionToggleStatus().isElementAvailable(10)) {
			base.stepInfo("Include Applied Redaction toggle is in Enabled State");
			flag = true;
		} else {
			base.stepInfo("Include Applied Redaction toggle is in Disabled State");
			flag = false;
		}

		if (flag && enableRedactToggle) {
			base.passedStep("Include Applied Redaction toggle is Enabled");
		} else if (!flag && enableRedactToggle) {
			getToggleButton().waitAndClick(10);
			base.ValidateElement_Presence(getRedactionToggleStatus(), "Redaction Toggle is Enabled");
		} else if (flag && !enableRedactToggle) {
			getToggleButton().waitAndClick(10);
			base.ValidateElement_Absence(getRedactionToggleStatus(), "Redaction Toggle is Disabled");
		} else if (!flag && !enableRedactToggle) {
			base.stepInfo("Include Applied Redaction toggle is Disabled");
		}

		if (configureAndverifyPosition) {
			List<String> positionss = base.availableListofElements(getBrandingPositions());
			for (int i = 1; i <= positionss.size(); i++) {
				// Configure the position to Left, Right, Center- topLeft, Right, Center- Bottom
				// & verify Location are fixed as per selected position
				base.waitForElement(getHeaderPositionBtn(i));
				getHeaderPositionBtn(i).waitAndClick(10);
				base.waitForElement(getBatchPrintEnterBranding());
				getBatchPrintEnterBranding().waitAndClick(10);
				base.waitTime(2);
				getBatchPrintEnterBranding().SendKeys(configureTxt);
				base.waitForElement(getInsertMetadataFieldOKButton());
				getInsertMetadataFieldOKButton().waitAndClick(5);

				String actualPosition = getHeaderPositionBtn(i).GetCssValue("z-index");
				base.digitCompareEquals(Integer.parseInt(actualPosition), 2,
						"Location are fixed as per selected position",
						"Location are Not fixed as per selected position");

				if (verifyColour) {
					base.waitTime(3);
					String color = getHeaderPositionBtn(i).GetCssValue("background-color");
					String actualColour = base.rgbTohexaConvertor(color);
					base.textCompareEquals(actualColour, "#739E73", "Metadata selected to add is in green color ",
							"Metadata selected to add is Not in green color ");
				}
			}
		}
	}

	public void enableCoverToggleAnFillTheDetails(String toRecipient, String FromRecipient, String title) {
		getCoverAndIntroToggle().waitAndClick(10);

		if (getToRecipient_TXB().isDisplayed()) {
			base.stepInfo("Cover & intro Page toggle is Enabled");
			base.waitForElement(getToRecipient_TXB());
			getToRecipient_TXB().waitAndClick(10);
			getToRecipient_TXB().SendKeys(toRecipient);
			driver.waitForPageToBeReady();
			getFromRecipient_TXB().waitAndClick(10);
			getFromRecipient_TXB().SendKeys(FromRecipient);
			driver.waitForPageToBeReady();
			getTitle_TXB().waitAndClick(10);
			getTitle_TXB().SendKeys(title);
		} else {
			base.stepInfo("Cover & Intro Page Toggle is Disabled");
		}
	}

	/**
	 * @Author Jeevitha
	 * @Description : enable/disable fodler skipped documents toggle & verify folder tree structure is displayed
	 */
	public void verifyFolderSkippedDoc(Boolean clickSkipFold, Boolean enableFolderSkip) {
		if (clickSkipFold) {
			getSkippedFolderButton().waitAndClick(10);
		}

		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();
		String toggleStatus = getSkipFoldToggleStatus().GetAttribute("class");
		if (enableFolderSkip)
			if (toggleStatus.contains("activeC") && enableFolderSkip) {
				base.stepInfo("Folder skipped document toggle is Enabled");
				driver.waitForPageToBeReady();
				base.ValidateElement_Presence(getFolderTreeStructure(), "Folder Tree Structure is displayed");
			} else {
				getSkippedFolderButton().waitAndClick(10);
				base.stepInfo("Folder skipped document toggle is Enabled Now");
				driver.waitForPageToBeReady();
				base.ValidateElement_Presence(getFolderTreeStructure(), "Folder Tree Structure is displayed");
			}

		if (!enableFolderSkip) {
			if (!toggleStatus.contains("activeC") && !enableFolderSkip) {
				base.stepInfo("Folder skipped document toggle is Disabled");
				driver.waitForPageToBeReady();
				boolean flag = getFolderTreeStructure().isDisplayed();
				base.printResutInReport(flag, "Folder Tree Structure is not displayed",
						"Folder Tree Structure is not as expected", "Fail");
			} else {
				getSkippedFolderButton().waitAndClick(10);
				base.stepInfo("Folder skipped document toggle is Disabled Now");
				driver.waitForPageToBeReady();
				boolean flag = getFolderTreeStructure().isDisplayed();
				base.printResutInReport(flag, "Folder Tree Structure is not displayed",
						"Folder Tree Structure is not as expected", "Fail");
			}
		}
	}

	/**
	 * @Author Jeevitha
	 * @Description : verify grid in analysis tab . verifies pagination , Header &
	 *              Radio Button Displayed for all docid
	 * @param verifyPagination
	 */
	public void verifyTableGridInAnalysisTab(boolean verifyPagination) {
		String[] expectedTableHeader = { "DOC ID", "SKIP PRINTING", "NOT IN ANY PRODUCTION" };
		base.waitForElementCollection(getAnalysisProdTableHeader());
		List<String> headercompleteteList = base.availableListofElements(getAnalysisProdTableHeader());
		List<String> actualList = new ArrayList<>();

		for (int i = 0; i < headercompleteteList.size(); i++) {
			if (headercompleteteList.get(i).equals("")) {
				System.out.println("Ignore");
			} else {
				actualList.add(headercompleteteList.get(i));
			}
		}

		base.compareArraywithDataList(expectedTableHeader, actualList, true,
				actualList + " Headers is displayed as expected", "headers are not as expected");

		if (verifyPagination) {
			driver.waitForPageToBeReady();
			base.ValidateElement_Presence(getPaginationOfProbTablegrid(), "Grid Pagination");
		}
		int radioBtnIndex = base.getIndex(getAnalysisProdTableHeader(), "SKIP PRINTING");
		System.out.println(radioBtnIndex);
		base.waitForElementCollection(getGridValues(radioBtnIndex));
		List<WebElement> ActualElement = getGridValues(radioBtnIndex).FindWebElements();
		for (int i = 1; i < getGridValues(radioBtnIndex).size(); i++) {
			String actualType = ActualElement.get(i).getAttribute("class");
			if (actualType.equals("radio")) {
				System.out.println("Radio Button is  Available");
			} else {
				base.failedStep("Radio Button is not Available");
			}
		}
		base.passedStep("Radio button is visible for all The Documents");
	}

	/**
	 * @Authro Jeevitha
	 * @Description : verify analysis request details displayed in analysis tab
	 */
	public void verifyAnalysisReportDetails() {
		String details1 = getAnalysisRequestDetails().getText();
		String actualMsg1 = "You requested to print";
		base.compareTextViaContains(details1, actualMsg1, actualMsg1, "Analysis Request is not as expected");
		String actualMsg2 = "There is no issues with";
		base.compareTextViaContains(details1, actualMsg2, actualMsg2, "Analysis Request is not as expected");
		base.passedStep(details1);

		String details2 = getReportAnalysisDetails().getText();
		String actualMsg3 = "documents that need your decision below. Of these";
		base.compareTextViaContains(details2, actualMsg3, actualMsg3, "Analysis Request is not as expected");

		String actualMsg4 = "are not in any of your specified productions";
		base.compareTextViaContains(details2, actualMsg4, actualMsg4, "Analysis Request is not as expected");

		String actualMsg5 = "are in more than one production";
		base.compareTextViaContains(details2, actualMsg5, actualMsg5, "Analysis Request is not as expected");
		base.passedStep(details2);

	}

}
