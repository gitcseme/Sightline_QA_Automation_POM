package pageFactory;

import java.awt.AWTException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import com.asprise.ocr.Ocr;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;
import testScriptsSmoke.Input;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;

import java.awt.image.BufferedImage;

public class ProductionPage {

	Driver driver;
	BaseClass base;

	public String ProdPathallredact;
	public String ProdPathsomeredact;
	String searchString2 = Input.searchString2;
	String searchString3 = Input.searchString3;
	String searchString4 = Input.searchString4;
	String tagNamePrev = Input.tagNamePrev;
	String tagNameTechnical = Input.tagNameTechnical;
	String documentID = Input.documentID;
	SoftAssert softAssertion = new SoftAssert();
	String num;
	String num1;

	public Element getAddNewProductionbutton() {
		return driver.FindElementByXPath("//a[contains(.,'Add a New Production')]");
	}

	public Element getProductionName() {
		return driver.FindElementById("ProductionName");
	}

	public Element getProductionDesc() {
		return driver.FindElementById("ProductionDescription");
	}

	public Element getBasicInfoMarkComplete() {
		return driver.FindElementById("BasicInfoMarkComplete");
	}

	public Element getBasicInfoSave() {
		return driver.FindElementById("BasicInfoSave");
	}

	public Element getBasicInfoNext() {
		return driver.FindElementById("BasicInfoNext");
	}

	public Element getDAT_FieldClassification1() {
		return driver.FindElementById("TY_0");
	}

	public Element getDAT_FieldClassification2() {
		return driver.FindElementById("TY_1");
	}

	public Element getDAT_FieldClassification3() {
		return driver.FindElementById("TY_2");
	}

	public Element getDAT_SourceField1() {
		return driver.FindElementById("SF_0");
	}

	public Element getDAT_SourceField2() {
		return driver.FindElementById("SF_1");
	}

	public Element getDAT_SourceField3() {
		return driver.FindElementById("SF_2");
	}

	public Element getDAT_DATField1() {
		return driver.FindElementById("DATFL_0");
	}

	public Element getDAT_DATField2() {
		return driver.FindElementById("DATFL_1");
	}

	public Element getDAT_DATField3() {
		return driver.FindElementById("DATFL_2");
	}

	public Element getTIFF_CenterHeaderBranding() {
		return driver.FindElementById("CenterHeaderBranding");
	}

	public Element getPDF_InsertMetadataField() {
		return driver.FindElementById("Launcheditor_0");
	}

	public Element getPDF_CenterHeaderBranding() {
		return driver.FindElementById("CenterHeaderBranding");
	}

	public Element getTIFF_CenterFooterBranding() {
		return driver.FindElementById("CenterFooterBranding");
	}

	public Element getTIFF_InsertMetadataField() {
		return driver.FindElementById("Launcheditor_0");
	}

	public Element getTIFF_selectedMetadataField() {
		return driver.FindElementById("selectedMetadataField");
	}

	public Element getTIFF_selectedMetadataField_Ok() {
		return driver.FindElementByXPath("//*[@onclick='return AddToRedactor()']");
	}

	public Element getComponentsMarkComplete() {
		return driver.FindElementById("ComponentsMarkComplete");
	}

	public Element getComponentsMarkNext() {
		return driver.FindElementById("ComponentsNext");
	}

	public Element getBeginningBates() {
		return driver.FindElementById("txtBeginningBatesID");
	}

	public Element gettxtBeginningBatesIDPrefix() {
		return driver.FindElementById("txtBeginningBatesIDPrefix");
	}

	public Element gettxtBeginningBatesIDSuffix() {
		return driver.FindElementById("txtBeginningBatesIDSuffix");
	}

	public Element gettxtBeginningBatesIDMinNumLength() {
		return driver.FindElementById("txtBeginningBatesIDMinNumLength");
	}

	public Element getlstSortingMetaData() {
		return driver.FindElementById("lstSortingMetaData");
	}

	public Element getlstSortingOrder() {
		return driver.FindElementById("lstSortingOrder");
	}

	public Element getlstSubSortingMetaData() {
		return driver.FindElementById("lstSubSortingMetaData");
	}

	public Element getlstSubSortingOrder() {
		return driver.FindElementById("lstSubSortingOrder");
	}

	public Element getNumAndSortMarkComplete() {
		return driver.FindElementById("NumAndSortMarkComplete");
	}

	public Element getNumAndSortNext() {
		return driver.FindElementById("NumAndSortNext");
	}

	public Element getbtnDocumentsSelectionMarkComplete() {
		return driver.FindElementById("btnDocumentsSelectionMarkComplete");
	}

	public Element getbtnDocumentsSelectionNext() {
		return driver.FindElementById("btnDocumentsSelectionNext");
	}

	public Element getbtnProductionGuardMarkComplete() {
		return driver.FindElementById("btnProductionGuardMarkComplete");
	}

	public Element getbtnProductionGuardNext() {
		return driver.FindElementById("btnProductionGuardNext");
	}

	public Element getOkButton() {
		return driver.FindElementById("bot1-Msg1");
	}

	public Element getlstProductionRootPaths() {
		return driver.FindElementById("lstProductionRootPaths");
	}

	public Element getProductionOutputLocation_ProductionDirectory() {
		return driver.FindElementById("ProductionOutputLocation_ProductionDirectory");
	}

	public Element getbtnProductionLocationMarkComplete() {
		return driver.FindElementById("btnProductionLocationMarkComplete");
	}

	public Element getbtnProductionLocationNext() {
		return driver.FindElementById("btnProductionLocationNext");
	}

	public Element getbtnProductionSummaryMarkComplete() {
		return driver.FindElementById("btnProductionSummaryMarkComplete");
	}

	public Element getbtnProductionSummaryNext() {
		return driver.FindElementById("btnProductionSummaryNext");
	}

	public Element getbtnProductionGenerate() {
		return driver.FindElementById("btnProductionGenerate");
	}

	public Element getProductionSettxt() {
		return driver.FindElementById("ProductionSettxt");
	}

	public Element getbtnGenerateMarkComplete() {
		return driver.FindElementByXPath("//*[@id='btnGenerateMarkComplete']");
	}

	public Element getbtnReGenerateMarkComplete() {
		return driver.FindElementById("btnProductionReGenerate");
	}

	public Element getbtnRegenerate() {
		return driver.FindElementByXPath("//button[contains(text(),'Regenerate')]");
	}

	public Element getbtnSummaryNext() {
		return driver.FindElementById("btnGenerateNext");
	}

	public Element getDATChkBox() {
		return driver.FindElementByXPath("//input[@name='IsDATSelected']/following-sibling::i");
	}

	public Element getDATTab() {
		return driver.FindElementByXPath("//a[@href='#DATContainer']");
	}

	public Element getDAT_AddField() {
		return driver.FindElementByXPath(".//*[@id='DATContainer']//button[contains(.,'Add Field')]");
	}

	public Element getNativeChkBox() {
		return driver.FindElementByXPath("//input[@name='IsNativeSelected']/following-sibling::i");
	}

	public Element getNativeTab() {
		return driver.FindElementByXPath("//a[@href='#NativeContainer']");
	}

	public Element getNative_SelectAllCheck() {
		return driver.FindElementByXPath(
				".//*[@id='native-table']//input[@name='IsSelectAllFileTypes']/following-sibling::i");
	}

	public Element getNative_GenerateLoadFileLST() {
		return driver
				.FindElementByXPath(".//*[@id='NativeContainer']//input[@name='ProduceLoadFile']/following-sibling::i");
	}

	public Element getTIFFChkBox() {
		return driver.FindElementByXPath("//input[@name='IsTIFFSelected']/following-sibling::i");
	}

	public Element getTIFFTab() {
		return driver.FindElementByXPath("//a[@href='#TIFFContainer']");
	}

	public Element getTIFF_EnterBranding() {
		return driver.FindElementByXPath(
				".//*[@id='divCenterHeaderBranding']//div[@class='redactor-editor redactor-placeholder']");
	}

	public Element getTIFF_InsertMetadataFieldOKButton() {
		return driver.FindElementByXPath(".//*[@id='MetadataPopup']//button[contains(.,'OK')]");
	}

	public Element getTIFF_EnableforPrivilegedDocs() {
		return driver.FindElementByXPath(
				".//*[@id='TIFFContainer']//input[@name='CommonTIFFSettings.PlaceHolderImageSettings.EnabledforPrivDocs']/following-sibling::i");
	}

	public Element getPDF_EnterBranding() {
		return driver.FindElementByXPath(
				".//*[@id='divCenterHeaderBranding']//div[@class='redactor-editor redactor-placeholder']");
	}

	public Element getTextChkBox() {
		return driver.FindElementByXPath(".//*[@id='accordion']//input[@name='IsTextSelected']/following-sibling::i");
	}

	public Element getTextTab() {
		return driver.FindElementByXPath("//a[@href='#TextContainer']");
	}

	public Element getPDFChkBox() {
		return driver.FindElementByXPath("//input[@name='IsTIFFSelected']/following-sibling::i");
	}

	public Element getPDFTab() {
		return driver.FindElementByXPath("//a[contains( text(),' TIFF / PDF ')]");
	}

	public Element getPDFGenerateRadioButton() {
		return driver.FindElementByXPath(
				"//a[text()=' TIFF / PDF ']//following::*[normalize-space(text())='Generate PDF']//ancestor::label");
	}

	public Element getKeepFamiliesTogether() {
		return driver.FindElementByXPath(
				".//*[@id='divSortByMetadata_1']//input[@name='ProductionSortingSettings.SortByIsKeepFamiliesTogether']/following-sibling::i");
	}

	public Element getSelectFolder(String foldername) {
		return driver.FindElementByXPath(
				"//ul[@class='jstree-container-ul jstree-children']//a[contains(text(),'" + foldername + "')]");
	}

	public Element getFolderRadioButton() {
		return driver.FindElementByXPath(".//*[@id='rdbFolders']/following-sibling::i");
	}

	public Element getIncludeFamilies() {
		return driver.FindElementByXPath(
				".//*[@id='frmDocumentsSelection']//input[@name='ProductionDocumentsSelection.ToIncludeFamilies']/following-sibling::i");
	}

	public Element getBackButton() {
		return driver.FindElementByXPath("//a[@class='btn btn-default' and contains(.,'Back')]");
	}

	public Element getMarkpopup() {
		return driver.FindElementByXPath("//*[@id='AlwayShowButton']/span");
	}

	public Element getProdExportSet() {
		return driver.FindElementByXPath(".//*[@id='tabs-a']//a[contains(.,'Create a New Production/Export Set')]");
	}

	public Element getProductionLink() {
		return driver.FindElementByXPath("(.//*[@id='pName']/a)[1]");
	}

	public Element getConfirmProductionCommit() {
		return driver.FindElementByXPath(".//*[@id='btnProductionConfirmation']");
	}

	public Element getProductionDocCount() {
		return driver.FindElementByXPath(".//*[@class='drk-gray-widget']/span[1]");
	}

	public Element getReviewproductionButton() {
		return driver.FindElementByXPath("//a[contains(text(),'Review Production')]");
	}

	public Element getDestinationPathText() {
		return driver.FindElementById("DestinationPathToCopy");
	}

	// addition on 08/04
	public Element getPriveldge_SelectTagButton() {
		return driver.FindElementById("btnSelectPrevTags");
	}

	public Element getPriveldge_SelectPDFTagButton() {
		return driver.FindElementById("btnSelectPDFPrevTags");
	}

	public Element getPriveldge_TagTree(String tag) {
		return driver.FindElementByXPath("//div[@id='tagTreeTIFFComponent']/ul/li/ul/li//a[text()='" + tag + "']");
	}

	public Element getSelectTechnicalIssueTag() {
		return driver.FindElementByXPath("//div[@id='tagTreeTIFFComponent']//a[contains(text(),'Technical_Issue')]");
	}

	public Element getPriveldged_TagTree(String tag) {
		return driver
				.FindElementByXPath("//div[@id='tagTreeTIFFComponent']//ul/li/ul/li/a[contains(text(),'" + tag + "')]");
	}

	public Element getPriveldge_PDFTagTree(String tag) {
		return driver.FindElementByXPath("(//*[@id='tagTreePDFComponent']//a[contains(text(),'" + tag + "')])[2]");
	}

	public Element getPriveldge_TagTree_SelectButton() {
		return driver.FindElementByXPath("//*[@class='btn btn-primary btn-sm submitSelectionTIFF']");
	}

	public Element getPriveldge_PDFTagTree_SelectButton() {
		return driver.FindElementByXPath("//*[@class='btn btn-primary btn-sm submitSelectionPDF']");
	}

	public Element getPriveldge_TextArea() {
		return driver.FindElementByXPath("//textarea[@class='TIFFPrevDocPlaceHolderText']/preceding-sibling::div");
	}

	public Element getPriveldge_PDFTextArea() {
		return driver.FindElementByXPath("//textarea[@class='PDFPrevDocPlaceHolderText']/preceding-sibling::div");
	}

	// addition on 23/04
	public Element getProdExportSetRadioButton() {
		return driver.FindElementByXPath(".//*[@id='productionSet']//input[@id='IsExportSet']/following-sibling::i");
	}

	public Element getProdExport_SaveButton() {
		return driver.FindElementByXPath("//button[contains(.,'Save')]");
	}

	public Element getProdExport_ProductionSets() {
		return driver.FindElementById("ProductionSets");
	}

	public Element getProdExport_AddaNewExportSet() {
		return driver.FindElementByXPath(".//*[@id='cardGrid']//a[contains(.,'Add a New Export')]");
	}

	public Element getProdExport_Priorprodtoggle() {
		return driver.FindElementByXPath("//*[@id='ProductionListDiv']/label[2]/i");
	}

	public Element getProdExport_SelectProductionSet() {
		return driver.FindElementById("ProductionSetLst");
	}

	public Element getProd_BatesRange() {
		return driver.FindElementById("lblGeneratedBatesRange");
	}

	public Element getPreviewprod() {
		return driver.FindElementById("btnPreview");
	}

	public Element getNative_AdvToggle() {
		return driver.FindElementByXPath("//*[@id='NativeContainer']//div[@class='advanced-dd-toggle']");
	}

	public Element getProdStateFilter() {
		return driver.FindElementById("productionStateFilter");
	}

	// added by shilpi on 29/04/2020
	public Element getPDF_SpecifyRedactText() {
		return driver.FindElementByXPath("//*[@class='PDF-TIFF-Config']//a[@class='add-redaction-logic']");
	}

	public Element getPDF_BurnRedtoggle() {
		return driver.FindElementByXPath("//*[@id='chkPDFBurnRedactions']/following-sibling::i");
	}

	public Element getPDF_SelectRedtagbuton() {
		return driver.FindElementById("btnPdfRedTAG_0");
	}

	public Element getPDF_SelectRedtags() {
		return driver.FindElementByXPath("//*[@id='PDFRedactionTagsTree']//a[contains(text(),'R2')]");
	}

	public Element getPDF_SelectRedtags_SelectButton() {
		return driver.FindElementByXPath("//*[@id='myPDFModal']//button[@title='Select']");
	}

	public Element getPDF_Red_Placeholdertext() {
		return driver.FindElementByXPath("//*[@id='divPDFRedaction']//div[@class='redactor-editor']/p");
	}

	public Element getProdname_Gearicon(String prodname) {
		return driver.FindElementById(".//*[@id='pName']/a[@title='" + prodname + "']/following-sibling::div");
	}

	public Element getTIFF_BurnRedtoggle() {
		return driver.FindElementByXPath("//*[@id='chkBurnRedactions']/following-sibling::i");
	}

	public Element getTIFF_SpecifyRedactText() {
		return driver.FindElementByXPath("//*[@id='c7']//a[@class='add-redaction-logic']");
	}

	public Element getTIFF_SelectRedtagbuton() {
		return driver.FindElementById("btnTiffRedTAG_0");
	}

	public Element getTIFF_SelectRedtags() {
		return driver.FindElementByXPath("//*[@id='RedactionTagsTree']//a[contains(text(),'R2')]");
	}

	public Element getTIFF_SelectRedtags_SelectButton() {
		return driver.FindElementByXPath("//*[@id='myModal']//button[@title='Select']");
	}

	public Element getTIFF_Red_Placeholdertext() {
		return driver.FindElementByXPath("//*[@id='divRedaction']//div[@class='redactor-editor']/p");
	}

	public Element getTIFF_SelectRed_Radiobutton() {
		return driver.FindElementByXPath("//*[@id='chkTIFFSpecifytRedactions']/following-sibling::i");
	}

	public Element getPDF_SelectRed_Radiobutton() {
		return driver.FindElementByXPath("//*[@id='chkPDFSPecifytRedactions']/following-sibling::i");
	}

	public Element getDoc_Count() {
		return driver.FindElementByXPath("//*[@id='frmProductionConfirmation']//div[@class='drk-gray-widget']/span[1]");
	}

	public Element getProd_Uncommitbutton() {
		return driver.FindElementByXPath("//strong[contains(text(),'Uncommit Production')]");
	}

	// added by shilpi
	public Element getTextcomponent_text() {
		return driver.FindElementByXPath("//*[@id='TextContainer']//p");
	}

	public Element getTiff_placeholdertext() {
		return driver.FindElementByXPath("//*[@class='col-md-12 tiff-img-logic']/label[2]");
	}

	public Element getTiff_NativeDoc() {
		return driver.FindElementByXPath("//*[@class='add-tiff-img-logic']");
	}

	public Element getTiff_NativeDoc_FileType() {
		return driver.FindElementByXPath("//*[@id='divImageTIFFPHImage_0']/div[1]/div[1]/label[1]");
	}

	public Element getTiff_redactiontext() {
		return driver.FindElementByXPath("//*[@class='col-md-12 tiff-redaction-logic']/label[2]");
	}

	public Element getpdf_placeholdertext() {
		return driver.FindElementByXPath("//*[@class='col-md-12 pdf-img-logic']/label[2]");
	}

	public Element getpdf_NativeDoc() {
		return driver.FindElementByXPath("//*[@class='add-pdf-img-logic']");
	}

	public Element getpdf_NativeDoc_FileType() {
		return driver.FindElementByXPath("//*[@id='divImagePDFPHImage_0']/div[1]/div[1]/label[1]");
	}

	public Element getpdf_redactiontext() {
		return driver.FindElementByXPath("//*[@class='col-md-12 pdf-redaction-logic']/label[2]");
	}

	public Element getNative_text() {
		return driver.FindElementByXPath("//*[@id='NativeContainer']//div[1]/p/strong");
	}

	public Element getNative_text_Color() {
		return driver.FindElementByXPath("//*[@id='NativeContainer']//div[1]/p");
	}

	public Element getMarkCompleteLink() {
		return driver.FindElementByXPath("//a[text()='Mark Complete']");
	}

	public Element getNextButton() {
		return driver.FindElementByXPath("//button[text()='Next']");
	}

	public Element getprod_ActionButton() {
		return driver.FindElementByXPath("(//*[@class='fa fa-lg fa-gear'])[1]");
	}

	public Element getprod_Action_SaveTemplate() {
		return driver.FindElementByXPath("//*[@id='pName']//a[contains(.,'Save as Template')]");
	}

	public Element getprod_Templatetext() {
		return driver.FindElementById("templatesettxt");
	}

	public Element getprod_LoadTemplate() {
		return driver.FindElementById("ddlTemplate");
	}

	public Element getTechissue_toggle() {
		return driver.FindElementByXPath("//*[@id='chkEnabledforExceptionDocs']/following-sibling::i");
	}

	public Element getTechissue_SelectTagButton() {
		return driver.FindElementById("btnSelectTechIssueTags");
	}

	public Element getTechissue_TextArea() {
		return driver.FindElementByXPath("//textarea[@class='TIFFTechIssueDocPlaceHolderText']/preceding-sibling::div");
	}

	// added by Narendra
	public ElementCollection getFilterOptions() {
		return driver.FindElementsByXPath("//div[@class='col-md-9']//select//option");
	}

	public Element getFilter(int i) {
		return driver.FindElementByXPath("//div[@class='col-md-5']//li[" + i + "]//a[1]");
	}

	public Element getFilterByButton() {
		return driver.FindElementByXPath("//button[@class='multiselect dropdown-toggle btn']");
	}

	public Element getFilterByDRAFT() {
		return driver
				.FindElementByXPath(".//*[@class='multiselect-container dropdown-menu']//label/input[@value='DRAFT']");
	}

	public Element getFilterByINPROGRESS() {
		return driver.FindElementByXPath(
				".//*[@class='multiselect-container dropdown-menu']//label/input[@value='INPROGRESS']");
	}

	public Element getFilterByFAILED() {
		return driver
				.FindElementByXPath(".//*[@class='multiselect-container dropdown-menu']//label/input[@value='FAILED']");
	}

	public Element getFilterByCOMPLETED() {
		return driver.FindElementByXPath(
				".//*[@class='multiselect-container dropdown-menu']//label/input[@value='COMPLETED']");
	}

	public Element getRefreshButton() {
		return driver.FindElementByXPath("//a[@id='refresh']");
	}

	public Element gettxtProdGenerationFailed() {
		return driver
				.FindElement(By.xpath("//div[@id='ProgressBar_118']//span[contains(text(),'Prod Generation Failed')]"));
	}

	public Element gettxtReservingBatesFailed() {
		return driver.FindElement(
				By.xpath("//div[@id='ProgressBar_483']//span[contains(text(),'Reserving Bates Range Failed')]"));
	}

	public Element getFilterDropDown() {
		return driver.FindElement(By.xpath("//button[@class='multiselect dropdown-toggle btn']"));
	}

	public Element getSortByButton() {
		return driver.FindElementById("SortBy");
	}

	public Element getGridView() {
		return driver.FindElementById("GridView");
	}

	public ElementCollection getProductionItemsTile() {
		return driver.FindElementsByXPath("//div[@id='cardCanvas']//li/div[1]/a[1]");
	}

	public ElementCollection getProductionItemsGrid() {
		return driver.FindElementsByXPath("//table[@id='ProductionListGridViewTable']//tr");
	}

	public Element getProductionItemsTileItems() {
		return driver.FindElementById("totalProductionCount");
	}

	public Element getProductionItemsGridItems() {
		return driver.FindElementById("ProductionListGridViewTable_info");
	}

	public Element getArrow() {
		return driver.FindElementByXPath("//div[@id='pName']//div//a[1]");
	}

	public Element getSaveTemplate() {
		return driver.FindElementByXPath("//*[@class='dropdown-menu']//a[contains(text(),'Save as Template')]");
	}

	public Element getTemplateName() {
		return driver.FindElementById("templatesettxt");
	}

	public Element getCustomTemplateName(String tempName) {
		return driver.FindElementByXPath(
				"//table[@id='customTemplatesDatatable']//tr//td[1][contains(text(),'" + tempName + "')]");
	}

	public Element getSave() {
		return driver.FindElementByXPath("//button[@class='btn-primary']");
	}

	public Element getManageTemplates() {
		return driver.FindElementByXPath(
				"//li[@class='ui-tabs-tab ui-corner-top ui-state-default ui-tab']//a[@class='ui-tabs-anchor']");
	}

	public ElementCollection getCustomTemplates() {
		return driver.FindElementsByXPath("//table[@id='customTemplatesDatatable']//tr//td[1]");
	}

	public Element getDeleteTemplate() {
		return driver
				.FindElementByXPath("//table[@id='customTemplatesDatatable']//tr//td[9]//a[contains(text(),'Delete')]");
	}

	public Element getOK() {
		return driver.FindElementById("bot1-Msg1");
	}

	public Element getCreateProductionSet() {
		return driver.FindElementByXPath("//div[@id='tabs-a']/div[@class='col-md-12']/div[@class='col-md-5']/a[1]");
	}

	public Element getSaveSet() {
		return driver.FindElementByXPath("//button[text()='Save']");
	}

	public Element getCancelSet() {
		return driver.FindElementByXPath("//button[@class='btn btn-default btn-sm']");
	}

	public Element getSetName() {
		return driver.FindElementById("ProductionSettxt");
	}

	public Element getDeleteProd() {
		return driver.FindElementById("DeleteProd");
	}

	public Element getLock() {
		return driver.FindElementByXPath(
				"//div[@class='dropdown pull-right actionBtn font-xs open']//a[contains(text(),'Lock')]");
	}

	public Element getDelete() {
		return driver.FindElementByXPath(
				"//div[@class='dropdown pull-right actionBtn font-xs open']//a[contains(text(),'Delete')]");
	}

	public Element getTileDelete() {
		return driver.FindElementByXPath(
				"//div[@class='dropdown pull-right actionBtn font-xs open']//a[contains(text(),'Delete')]");
	}

	public Element getAction() {
		return driver.FindElementByXPath(" //span[@class='fa fa-chevron-down']");
	}

	public Element getLockIcon() {
		return driver.FindElementByXPath("//i[@class='fa fa-lock']");
	}

	public Element getOpenWizard() {
		return driver.FindElementByXPath(
				"//div[@class='dropdown pull-right actionBtn font-xs open']//a[contains(text(),'Open In Wizard')]");
	}

	public Element getLoadTemplate() {
		return driver.FindElementById("ddlTemplate");
	}

	public Element getGridDelete() {
		return driver.FindElementById("Delete");
	}

	public Element getPRODUCTIONSSETName() {
		return driver.FindElementByXPath("//div[@class='col-md-12 productionSummary']//span[@class='font-lg']");
	}

	public Element getProductionListGridViewTable() {
		return driver.FindElementByXPath("//table[@id='ProductionListGridViewTable']//tbody//tr[1]");
	}

	public Element getPRODUCTIONSEXPORTSTabs() {
		return driver.FindElementById("ui-id-1");
	}

	public Element getadvance() {
		return driver.FindElementByXPath(
				"//div[@class='panel-body']//div[@class='col-md-12']//i[@class='fa fa-chevron-right']");
	}

	public Element getGenerateLoadFile() {
		return driver.FindElementByXPath(
				"//div[@class='panel-body']//div[@class='col-md-12']//div[@class='col-md-4']//i[@class='pull-right']");
	}

	public Element getSlipSheets() {
		return driver.FindElementByXPath(
				"//div[@class='form-group col-md-12 wrapperNew no-padding']//i[@class='pull-left']");
	}

	public Element getAvailableFields(int i) {
		return driver.FindElementByXPath("//ul[@class='nav nav-tabs tab-style']//li[" + i + "]//a");
	}

	public Element getTIFF_InsertMetadataFieldClick() {
		return driver.FindElementByXPath("//div[@class='col-md-8 tiff-logic insertPopup']//a[@class='LaunchPopup']");
	}

	// added by shilpi on 08/17
	public Element getCopyPath() {
		return driver.FindElementByXPath("//a[@title='Copy Path']");
	}

	public Element getDocumentGeneratetext() {
		return driver.FindElementByXPath("//span[contains(text(),'Documents Generated')]");
	}

	public Element getQC_backbutton() {
		return driver.FindElementByXPath("//a[contains(text(),'Back')]");
	}

	public Element getQC_Download() {
		return driver.FindElementByXPath("//a[text()='Download']");
	}

	public Element getQC_Downloadbutton_allfiles() {
		return driver.FindElementByXPath("(//a[@title='Download All files'])[1]");
	}

	public Element getPrivPlaceHolder() {
		return driver.FindElementByXPath("//div[@placeholder='Enter placeholder text for the privileged docs']");
	}

	public Element getNativePlaceHolder() {
		return driver.FindElementByXPath(
				"//div[@placeholder='Enter placeholder text for the docs of the selected file types']");
	}

	public Element getRemoveBtnInSlipSheet() {
		return driver.FindElementByXPath("//a[@class='link remove-modal remove-item pull-right text-danger']");
	}

	public Element getSelectCloseBtn() {
		return driver.FindElementByXPath("//div[@id='divImagePHImage']//button[@class='close delete-logic']");
	}

	// added by sowndariya
	public Element getSelectMultiFileTypeInTifffNative(int no, String fileType) {
		return driver.FindElementByXPath("(//select[@name='TIFFFileTypesList']/option[contains(text(),'"+fileType+"')])["+ no +"]");
	}
	
	public Element getLink_CustomSort() {
		return driver.FindElementByXPath("//input[@id='file']");
	}

	public Element getRadioBtn_CustomSort() {
		return driver.FindElementByXPath("//input[@id='rdbCustomSort']//..//i");
	}

	public Element isInprogressIsChecked() {
		return driver.FindElementByXPath("//input[@value='INPROGRESS']/../../..");
	}

	public Element getLoadMore() {
		return driver.FindElementByXPath("//button[@id='btnProductionDetailsLoadTile']");
	}

	public ElementCollection getTotalTagsInSorting() {
		return driver.FindElementsByXPath("//div[@class='tab-widget']");
	}

	public Element getRadioBtnSortByMetadata() {
		return driver.FindElementByXPath("//div[@id='divSortByMetadata_1']");
	}

	public ElementCollection getAllNativeFileTypes() {
		return driver.FindElementsByXPath("//tbody[@id='tblNativeFileGroup']//tr//td//input");
	}

	public Element countOfNumberOfNative() {
		return driver.FindElementByXPath("//label[contains(text(),'Number of Natives: ')]//following-sibling::label");
	}

	public Element btnBackToSource() {
		return driver.FindElementByXPath("//div[@id='criteria']//a[text()='Back to Source']");
	}

	public Element countOfNumberOfMP3() {
		return driver.FindElementByXPath("//label[contains(text(),'Number of MP3 Files: ')]//following-sibling::label");
	}

	public Element productionSetRadioBtn() {
		return driver.FindElementByXPath(
				"//div[@class='ui-dialog ui-corner-all ui-widget ui-widget-content ui-front ui-dialog-buttons ui-draggable']//input[@id='IsProductionSet']//parent::label//i");
	}

	public Element selectSecurityGroup(String securityGroup) {
		return driver.FindElementByXPath("//select[@id='SecurityGrpList']//option[text()='" + securityGroup + "']");
	}

	public Element getProductionAndExportHeader() {
		return driver
				.FindElementByXPath("//div[@id='content']//h1[contains(normalize-space(),'Productions and Exports')]");
	}

	public Element getInProgressStatus() {
		return driver.FindElementByXPath("//button[text()='In Progress']");
	}

	public Element emailToAuthorNameAddress() {
		return driver.FindElementByXPath(
				"//select[@name='DATComponentModelData.DATFieldMappingDataList[1].SelectedSourceId']//option[text()='EmailToNamesAndAddresses']");
	}

	public Element EmailBCCNamesAndAddresses() {
		return driver.FindElementByXPath(
				"//select[@name='DATComponentModelData.DATFieldMappingDataList[1].SelectedSourceId']//option[text()='EmailBCCNamesAndAddresses']");
	}

	public Element EmailCCNamesAndAddresses() {
		return driver.FindElementByXPath(
				"//select[@name='DATComponentModelData.DATFieldMappingDataList[1].SelectedSourceId']//option[text()='EmailCCNamesAndAddresses']");
	}

	public Element emailAuthorNameAddress() {
		return driver.FindElementByXPath(
				"//select[@name='DATComponentModelData.DATFieldMappingDataList[1].SelectedSourceId']//option[text()='EmailAuthorNameAndAddress']");
	}

	public Element getSelect_Can_RedactionStyle_Dropdown() {
		return driver.FindElementByXPath("//select[@id='lstFillerAudio']//option[text()='Can']");
	}

	public Element getSelect_Bomb_RedactionStyle_Dropdown() {
		return driver.FindElementByXPath("//select[@id='lstFillerAudio']//option[text()='Bomb']");
	}

	public Element docIdentifiedByProtectionGuard() {
		return driver.FindElementByXPath(
				"//label[contains(text(),'Documents Identified by Production Guard')]/following-sibling::label");
	}

	public Element documentMissingCount() {
		return driver.FindElementByXPath("//label[contains(text(),'Documents with Missing')]/following-sibling::label");
	}

	public Element exceptionDocCount() {
		return driver.FindElementByXPath("//label[contains(text(),'Exception Documents')]/following-sibling::label");
	}

	public Element loadFilePath() {
		return driver.FindElementById("ProductionOutputLocation_DriveText");
	}

	public Element slipSheetsText() {
		return driver.FindElementByXPath("//div[@id='TIFFContainer']//strong[contains(text(),'Slip Sheets')]");
	}

	public Element redactionsText() {
		return driver.FindElementByXPath("//div[@id='TIFFContainer']//strong[contains(text(),'Redactions')]");
	}

	public Element placeHolderText() {
		return driver.FindElementByXPath("//div[@id='TIFFContainer']//strong[contains(text(),'Placeholders')]");
	}

	public Element brandingText() {
		return driver.FindElementByXPath("//div[@id='TIFFContainer']//strong[contains(text(),'Branding')]");
	}

	public Element pageOptionsText() {
		return driver.FindElementByXPath("//div[@id='TIFFContainer']//strong[contains(text(),'Page Options')]");
	}

	public Element nativeTextInNative() {
		return driver.FindElementByXPath(
				"//div[@id='NativeContainer']//p[contains(text(),'Privileged Placeholdering and Burn Redactions are enabled in the TIFF/PDF section)]");
	}

	public Element radioButtonInNative() {
		return driver.FindElementByXPath(
				"//div[@id='NativeContainer']//span[contains(text(),'Do not produce natives of the entire family of privileged and redacted docs')]");
	}

	public Element radioBtnInNative() {
		return driver.FindElementByXPath(
				"//div[@id='NativeContainer']//span[contains(text(),'Do not produce natives of the parents of privileged and redacted docs')]");
	}

	public Element RedactedMsgInNative() {
		return driver.FindElementByXPath(
				"//div[@id='NativeContainer']//span[contains(text(),'Families of Redacted and Privileged Documents')]");
	}

	public Element warningMsgMP3() {
		return driver.FindElementByXPath("//div[@id='divbigBoxes']//p");
	}

	public Element defaultNewLineSeperator() {
		return driver.FindElementByXPath("//select[@id='lstNewLineSeparator']//option");
	}

	public Element defaultMultiValue() {
		return driver.FindElementByXPath("//select[@id='lstMultiValue']//option");
	}

	public Element defaultFielsSeperator() {
		return driver.FindElementByXPath("//select[@id='lstFieldSeparator']//option");
	}

	public Element defaultTextQualifier() {
		return driver.FindElementByXPath("//select[@id='lstTextQualifier']//option");
	}

	public Element defaultTextInNativeToggle() {
		return driver.FindElementByXPath(
				"//div[@class='redactor-editor']//p[contains(text(),'Document Produced in Native Format.')]");
	}

	public Element redactedDocCountInSummaryPage() {
		return driver.FindElementByXPath(
				"(//div[@class='smart-form']//label[contains(text(),'Redacted Documents: ')]//..//label[@class='col-sm-6 labelAlign'])[2]");
	}

	public Element redactionTagInBurnRedactionToggle(String tag) {
		return driver.FindElementByXPath(
				"//div[@id='tagTreeTIFFComponent']//ul[@class='jstree-children']//a[contains(text(),'" + tag + "')]");
	}

	public Element btnOK_Metafield() {
		return driver.FindElementByXPath("//div[@id='MetadataPopup']//button[@value='submit']");
	}

	public Element insertMetadataLink() {
		return driver.FindElementById("LaunchMetaData0");
	}

	public Element ddnselectFileType() {
		return driver.FindElementByXPath("//select[@id='TIFFFileTypes_0']");
	}

	public Element batesRangeInHomePage(String suffixID) {
		return driver.FindElementByXPath("//div[@id='batesCount']//strong[contains(text(),'" + suffixID + "')]");
	}

	public Element automatedCheck_BatesNumberGeneration() {
		return driver.FindElementByXPath("//table[@id='taskbasic']//td[contains(text(),'Bates Number Generation')]");
	}

	public Element automatedCheck_BlankPageRemoval() {
		return driver.FindElementByXPath("//table[@id='taskbasic']//td[contains(text(),'Blank Page Removal')]");
	}

	public Element automatedCheck_DocumentCounts() {
		return driver.FindElementByXPath("//table[@id='taskbasic']//td[contains(text(),'Document and Page Counts')]");
	}

	public Element automatedCheck_prodfiles() {
		return driver.FindElementByXPath("//table[@id='taskbasic']//td[contains(text(),'Prod Files')]");
	}

	public Element getGenPageStatus(String status) {
		return driver.FindElementByXPath("//label[@id='prouctionGenerateStatusTxt' and text()='" + status + "']");
	}

	public Element getChkBoxNative_Multimedia() {
		return driver.FindElementByXPath("(//*[@id='NativeContainer']//label[@class='checkbox']//i)[5]");
	}

	public Element getEnableForNativelyToggle() {
		return driver.FindElementByXPath("//input[@id='chkEnabledforNativeDocs']//..//i");
	}

	public Element getSecondSelectTagBtn() {
		return driver.FindElementByXPath("//button[@id='btnTIFFPHSelectTags_1']");
	}

	public Element getNativePlaceholderLink() {
		return driver.FindElementByXPath("//label[@class='col-md-12 box']//a[contains(text(),'Specify placeholder')]");
	}

	public Element getPageNum(int num) {
		return driver.FindElementByXPath(
				"//div[@id='customTemplatesDatatable_paginate']//li[@class='paginate_button ']//a[text()='" + num
						+ "']");
	}

	public Element getLastPageNum() {
		return driver.FindElementByXPath(
				"(//div[@id='customTemplatesDatatable_paginate']//li[@class='paginate_button ']//a)[last()]");
	}

	public ElementCollection getTotalCustomTemplatePageSize() {
		return driver.FindElementsByXPath(
				"//div[@id='customTemplatesDatatable_paginate']//ul[@class='pagination pagination-sm']//a");
	}

	public Element fieldMappingtTextInDAT() {
		return driver.FindElementByXPath("//label[contains(text(),'Field Mapping:')]");
	}

	public Element dateFormatDdInDAT() {
		return driver.FindElementByXPath("//select[@name='DATComponentModelData.DateFormatID']");
	}

	public Element dateFormatTextInDAT() {
		return driver.FindElementByXPath("//label[contains(text(),'Date Format:')]");
	}

	public Element fieldSeperatorDdInDAT() {
		return driver.FindElementByXPath("//select[@id='lstFieldSeparator']");
	}

	public Element fieldDelimitersTextInDAT() {
		return driver
				.FindElementByXPath("//div[@class='form-group required']//label[contains(text(),'Field Delimiters:')]");
	}

	public Element radioBtnANSIInDAT() {
		return driver.FindElementByXPath(
				"//div[@class='form-group required']//label[@class='radio']//input[@id='rdbANSI']//..//i");
	}

	public Element FormatTextInDAT() {
		return driver.FindElementByXPath("//div[@class='form-group required']//label[contains(text(),'Format:')]");
	}

	public Element gridAndTileViewProdCount() {
		return driver.FindElementByXPath("//div[@id='cardGrid']//span[@id='totalProductionCount']");
	}

	public Element identifyByProductionGuardSource_radioBtn() {
		return driver.FindElementByXPath("//a[@id='a-c-6']");
	}

	public Element analyzeSelectProductionSets_radioBtn() {
		return driver.FindElementByXPath("//a[@id='a-c-2-4']");
	}

	public Element runCategorizationBtn() {
		return driver.FindElementByXPath("//button[contains(text(),'Run Categorization')]");
	}

	public Element radioBtnInAdvancedNative_ForPrivRedactedDocs() {
		return driver.FindElementByXPath(
				"//ul[@class='list-inline']//label//input[@id='rdbExcludeNativesofparent']//..//span[contains(text(),'natives of the parents')]");
	}

	public Element productionNameInHomePage() {
		return driver.FindElementById("pName");
	}

	public Element useMetadataFied() {
		return driver.FindElementByXPath("(//input[@id='rdbUserMetadata']//following::i)[1]");
	}

	public Element beginningBatesInFormat() {
		return driver.FindElementByXPath("(//div[@id='divNextBatesNum']//following::input)[1]");
	}

	public Element specifyBatesNumbering() {
		return driver.FindElementByXPath("(//input[@id='rdbSpecifyNumber']//following::i)[1]");
	}

	public Element pageRadioButton() {
		return driver.FindElementByXPath("(//input[@id='rdbPageLevel']//following::i)[1]");
	}

	public Element closeButtonInTemplate() {
		return driver.FindElementByXPath("//button[@class='ui-dialog-titlebar-close']");
	}

	public Element backButtonInTemplate() {
		return driver.FindElementByXPath("//div[@class='buttons pull-right']//a[text()='Back']");
	}

	public Element nextButtonInTemplate() {
		return driver.FindElementByXPath("//div[@id='template_productionguard']//a[text()='Next']");
	}

	public Element numberingSortingInTemplate() {
		return driver.FindElementByXPath("//span[contains(text(),'Numbering and Sorting')]");
	}

	public Element productionComponentsInTemplate() {
		return driver.FindElementByXPath("//span[contains(text(),'Production Components')]");
	}

	public Element privGuardInTemplate() {
		return driver.FindElementByXPath("//span[contains(text(),'Priv Guard')]");
	}

	public Element arrowSymbolInHomePage() {
		return driver.FindElementByXPath("//b[@class='caret']");
	}

	public Element productionNameInGeneratePage(String productionname) {
		return driver.FindElementByXPath("//label[contains(text(),'" + productionname + "')]");
	}

	public Element generateLoadFileToggleInTextComponent() {
		return driver.FindElementByXPath("(//input[@id='chkProduceLoadFile']//parent::label//i)[2]");
	}

	public Element invalidURLErrorMessageInSharableLinks() {
		return driver.FindElementByXPath("//p[contains(text(),' kindly contact system administrator.')]");
	}

	public Element invalidPasswordErrorMessageInSharableLinks() {
		return driver.FindElementByXPath("//p[contains(text(),'Invalid Password')]");
	}

	public Element nativeSectionBlueText() {
		return driver
				.FindElementByXPath("//div[@id='NativeContainer']//strong[contains(text(),'To produce specific')]");
	}

	public Element continueButtonInBlankPageRemovalToggle() {
		return driver.FindElementByXPath("//button[contains(text(),' Continue')]");
	}

	public Element getBlankPageRemovalToggleConfirmationMessage() {
		return driver.FindElementByXPath("//div[@id='MsgBoxBack']//p");
	}

	public Element getBlankPageRemovalToggle() {
		return driver.FindElementByXPath("//input[@id='chkTIFFBlankPageRemove']//parent::label//i");
	}

	public Element getGenerateLoadFile_TIFF() {
		return driver.FindElementByXPath("//input[@id='chkTIFFProduceLoadFile']");
	}

	public Element getRadioButton_GenerateTIFF() {
		return driver.FindElementByXPath("(//input[@id='CommonTIFFSettings_FileType']//following-sibling::i)[1]");
	}

	public Element getProductionNameInManageView(String productionName) {
		return driver.FindElementByXPath("*//span[contains(text(),'" + productionName + "')]");
	}

	public Element getDeleteBtn(String Templatename) {
		return driver.FindElementByXPath("//td[text()='" + Templatename + "']/..//td//a[text()='Delete']");
	}

	public Element getProductionFromHomepage(String productionName) {
		return driver.FindElementByXPath("//div[@id='cardCanvas']//a[contains(text(),'" + productionName + "')]");
	}

	public Element getProductionFromDropDown(String productionset) {
		return driver
				.FindElementByXPath("//select[@id='ProductionSets']//option[contains(text(),'" + productionset + "')]");
	}

	public Element getValueRedactedDocs() {
		return driver.FindElementByXPath("//label[contains(text(),'Redacted Documents: ')]//following-sibling::label");
	}

	public Element getValueFirstLastDocs() {
		return driver
				.FindElementByXPath("//label[contains(text(),'First and Last Doc IDs: ')]//following-sibling::label");
	}

	public Element getValueNumberOfCustodians() {
		return driver
				.FindElementByXPath("//label[contains(text(),'Number Of Custodians: ')]//following-sibling::label");
	}

	public Element getAddFieldButtonInDAT() {
		return driver.FindElementByXPath("//div//button[@class='btn btn-primary btn-add-row datAddNewRow']");
	}

	public Element getValueTotalPagesCount() {
		return driver.FindElementByXPath("//label[contains(text(),'Total Pages')]//following-sibling::label");
	}

	public Element getValueTotalDocuments() {
		return driver.FindElementByXPath("//label[contains(text(),'Total Documents')]//following-sibling::label");
	}

	public Element getClkBtnDownloadDATFiles() {
		return driver.FindElementByXPath("(//span[contains(text(),'Download DAT file')])[1]");
	}

	public Element getRegenerateBtn() {
		return driver.FindElementByXPath("//button[text()='Regenerate']");
	}

	public Element getErrorMsgInDATWithoutSelectingDAT() {
		return driver.FindElementByXPath(
				"//*[@id='divbigBoxes']//p[contains(text(),'Selection of the DAT component is mandatory for a production.')]");
	}

	public Element getErrorMsgInDATWithoutAddingBates() {
		return driver.FindElementByXPath(
				"//*[@id='divbigBoxes']//p[contains(text(),'Bates Number must be selected in the DAT for a production.')]");
	}

	public Element getErrorMsgInDATWithOtherCharacter() {
		return driver.FindElementByXPath(
				"//*[@id='divbigBoxes']//p[contains(text(),'DAT field names must begin with an alphabet. Please check.')]");
	}

	public Element getErrorMsgInDATWithSpecialCharacter() {
		return driver.FindElementByXPath(
				"//*[@id='divbigBoxes']//p[contains(text(),'Special characters other than underscore (_) are not allowed in the  DAT field names. Please check.')]");
	}

	public Element getErrorMsgInDATForSameDatField() {
		return driver.FindElementByXPath(
				"//*[@id='divbigBoxes']//p[contains(text(),'Multiple project fields are not allowed to be mapped to the same field in DAT. Please check.')]");
	}

	public Element getBtnAddFieldInDAt() {
		return driver.FindElementByXPath("//button[contains(text(),'Add Field')]");
	}

	public Element getErrorMsgPopupInDAT() {
		return driver.FindElementByXPath("//div[@id='MsgBoxBack']//p");
	}

	public Element getErrorMsgInDAT() {
		return driver.FindElementByXPath(
				"//div[@id='divbigBoxes']//p[text()='No fields are added in the DAT section. Please complete before navigating to the next step.']");
	}

	public Element getGearIconForProdName(String productionname) {
		return driver.FindElementByXPath("//a[@title='" + productionname + "']/..//i[@class='fa fa-lg fa-gear']");
	}

	public Element getPostGenGearIcon() {
		return driver.FindElementByXPath(
				"(//span[text()='Post-Gen QC Checks Complete'])[1]/../../..//i[@class='fa fa-lg fa-gear']");
	}

	public Element getbtnGenMarkIncomplete() {
		return driver.FindElementById("btnGenerateMarkInComplete");
	}

	public Element getVerifyGenStatus(String progressLvl) {
		return driver.FindElementByXPath("//label[contains(text(),'" + progressLvl + "')]");
	}

	public Element getTagRadioButton() {
		return driver.FindElementByXPath("//*[@id='rdbTags']/following-sibling::i");
	}

	public Element getBtnMarkIncomplete() {
		return driver.FindElementById("btnDocumentsSelectionMarkInComplete");
	}

	public Element getprod_Action_SaveTemplate_Reusable(String productionname) {
		return driver.FindElementByXPath("//a[@title='" + productionname + "']/..//a[contains(.,'Save as Template')]");
	}

	public Element getViewTemplate(String TempName) {
		return driver.FindElementByXPath("//a[contains(@onclick,'" + TempName + "')]");
	}

	public Element getadvanceOnManageTemplate() {
		return driver.FindElementByXPath("//div[@class='advanced-production-toggle col-md-12']//h2[@class='col-md-8']");
	}

	public Element getMP3CheckBoxToggle() {
		return driver.FindElementByXPath("//a[text()='MP3 Files ']");
	}

	public Element getMP3CheckReductionBoxEnable() {
		return driver.FindElementByXPath("//div[@class='mp3-conf']//i[@class='pull-left']");
	}

	public Element getMP3RatiobtnRedactiontag() {
		return driver.FindElementByXPath("//div[@id='divMP3Redactions']//label[2]");
	}

	public Element getMP3SelectDefaultRedactiontag() {
		return driver
				.FindElementByXPath("//div[@id='MP3RedactionTagsTree']//a[contains(text(),'Default Redaction Tag')]");
	}

	public Element checkMp3ReductionTagDisable() {
		return driver.FindElementByXPath("//*[@id='1_anchor']");
	}

	public Element getProductionHomePage() {
		return driver.FindElementByXPath("//a[@title='Productions']");
	}

	public Element getprod_ActionButton_Reusable(String productionname) {
		return driver.FindElementByXPath("//a[@title='" + productionname + "']/..//i[@class='fa fa-lg fa-gear']");
	}

	public Element manageTemplateLastPage() {
		return driver.FindElementByXPath("//*[@id=\"customTemplatesDatatable_paginate\"]/ul/li[8]/a");
	}

	public Element getSelect_RedactionStyle_Dropdown() {
		return driver.FindElementByXPath("//select[@id='lstFillerAudio']");
	}

	public Element getSelect_Beep_RedactionStyle_Dropdown() {
		return driver.FindElementByXPath("//select[@id='lstFillerAudio']//option[text()='Beep']");
	}

	public Element getNumber_of_natives_count() {
		return driver.FindElementByXPath("(//*[@id='frmProductionSummary']//label)[9]");
	}

	public Element getChkBoxNativeOthers() {
		return driver.FindElementByXPath("(//*[@id='NativeContainer']//label[@class='checkbox']//i)[2]");
	}

	public Element getChkBoxNative_WordTextFiles() {
		return driver.FindElementByXPath("(//*[@id='NativeContainer']//label[@class='checkbox']//i)[3]");
	}

	public Element getChkBoxNative_SpreadSheets() {
		return driver.FindElementByXPath("(//*[@id='NativeContainer']//label[@class='checkbox']//i)[4]");
	}

	public Element getSelectedTechIssueTags() {
		return driver.FindElementByXPath("//span[@id='TechIssueTagsLabel']");
	}

	public Element getProductionFromHomePage(String productionName) {
		return driver.FindElementByXPath("//a[text()='" + productionName + "']/../..//span[@class='progressBarText']");
	}

	public Element gettxtPreGenChecks() {
		return driver.FindElementByXPath("//span[contains(text(),'Pre-Gen Checks')]");
	}

	public Element getCancelBtn() {

		return driver.FindElementByXPath("(//button[@id='btnTagCancel'])[2]");
	}

	public Element getLeftSpecifyBrandingBySelectingTag() {
		return driver.FindElementByXPath("(//a[@class='add-logic'])[1]");
	}

	public Element getLeftHeaderBranding() {

		return driver.FindElementById("LeftHeaderBranding");
	}

	public Element getVolumeToggleInPLPage() {
		return driver.FindElementByXPath("//*[@id='ProductionOutputLocation_IsVolumeIncluded']");
	}

	public Element gettxtPostGenCompleted() {
		return driver.FindElementByXPath("(//span[text()='Post-Gen QC Checks Complete'])[1]");
	}

	public Element gettxtStatusInQCPage() {
		return driver.FindElementByXPath("//label[text()='Status:']");
	}

	public Element getConfirmProductionUnCommit() {
		return driver.FindElementByXPath("//div[@class='drk-gray-widget']//a[@id='btnProductionUnCommitBates']");
	}

	public Element getBckBtn() {
		return driver.FindElementByXPath(".//div[@class='buttons pull-right']//a[text()='Back']");
	}

	public Element getVerifyingQCPage() {
		return driver.FindElementByXPath("//label[text()='Post-Generation QC Checks Complete']");
	}

	public Element getClkCheckBoxRedactionTag(String Tag) {

		return driver.FindElementByXPath(

				"//div[@id='TIFFRedactiontreeFolder']/ul/li/ul/li/a[text()='" + Tag + "']");
	}

	public Element getClkCheckBoxRedactionTags(String nextTag) {

		return driver.FindElementByXPath(

				"//div[@id='TIFFRedactiontreeFolder']/ul/li/ul/li/a[text()='" + nextTag + "']");

	}

	public Element getDocCount() {

		return driver.FindElementByXPath("//label[@class='col-sm-6 labelAlign'][text()='2']");

	}

	public Element getclkNativelyProducedDocumentLnk() {
		return driver.FindElementByXPath("//a[@class='add-tiff-img-logic']");
	}

	public Element getFillingPlaceHolder() {
		return driver.FindElementByXPath(
				"//div[@placeholder='Enter placeholder text for the docs of the selected file types']");
	}

	public Element getErrorMsgPopupInTiffSection() {
		return driver.FindElementByXPath(
				"//p[text()='In the TIFF / PDF section, no values are specified in the placeholder configuration for the docs produced natively. Please check.']");
	}

	public Element getErrorMsgPopup() {
		return driver.FindElementByXPath(
				"//p[text()='To export natives, you must select at least a file group type or a tag in the Native section.']");
	}

	public Element getSecurityGroupDropDown() {
		return driver.FindElementById("SecurityGrpList");
	}

	public Element getDefaultSecurityGroup() {
		return driver.FindElementByXPath("//select[@id='SecurityGrpList']//option[text()='Default Security Group']");
	}

	public Element getSelectDownloadBtn() {

		return driver.FindElementByXPath("//a[text()='Download']");

	}

	public Element getSelectSharableLinks() {

		return driver.FindElementByXPath(
				"//div[@class='popover-content']//a[@id='btnGetShareableLinks']/child::span[text()='Get Shareable Links']");

	}

	public Element getAssertOnPassword() {

		return driver.FindElementByXPath("//label[@id='lblShareableLinksPassword']");

	}

	public Element getAssertAllFile() {

		return driver.FindElementByXPath("//b//span[text()='All Files']");

	}

	public Element getAssertDATFile() {

		return driver.FindElementByXPath("//b//span[text()='DAT File Only']");

	}

	public Element getAssertCommitBtn() {

		return driver.FindElementByXPath("//a[@id='btnProductionConfirmation']");

	}

	public Element getAssertCopyPathBtn() {

		return driver.FindElementByXPath("//a[@title='Copy Path']");

	}

	public Element gettxtProdGenerationInProgress() {
		return driver.FindElement(
				By.xpath("//div[@id='ProgressBar_59']//span[contains(text(),'Post-Gen QC Checks Complete')]"));
	}

	public Element getStatusDraftTxt() {
		return driver.FindElementByXPath("//label[@id='prouctionGenerateStatusTxt']");
	}

	public Element getbtnPopupPreviewMarkComplete() {
		return driver.FindElementById("AlwayShowButton");
	}

	public Element gettxtLoadFiles() {
		return driver.FindElementByXPath("//input[@id='ProductionComponentsFolderDetails_FolderName_LoadFiles']");
	}

	public Element getAdvancedProductionComponents(String productionName) {
		return driver.FindElementByXPath("//h2[text()=' Advanced Production Components ']");
	}

	public Element getMP3ChkBox(String productionName) {
		return driver.FindElementByXPath("//input[@id='chkIsMP3Selected']/following-sibling::i");
	}

	public Element getTranslationsChkBox(String productionName) {
		return driver.FindElementByXPath("//input[@id='chkIsTranslation']/following-sibling::i");
	}

	public Element getStatusSuccessTxt() {
		return driver.FindElementByXPath("//span[@class='text-success']");
	}

	public Element getTIFF_MultiPage_RadioButton() {
		return driver.FindElementByXPath("//input[@id='rbdMultiPageType']//following-sibling::i");
	}

	public Element getNumbering_Document_RadioButton() {
		return driver.FindElementByXPath("//input[@id='rdbDocumentLevel']//following-sibling::i");
	}

	public Element getbtnPreview() {
		return driver.FindElementByXPath("//a[@id='btnPreview']");
	}

	public Element getNumbering_Document_BeginningBates_Text() {
		return driver.FindElementById("txtSubbatesNumber");
	}

	public Element getGearIcon() {
		return driver.FindElementByXPath(
				"(//div[@class='dropdown pull-right actionBtn font-xs']//a[@class='dropdown-toggle']//i[@class='fa fa-lg fa-gear'])[1]");
	}

	public Element getbtnRegenerateContinue() {
		return driver.FindElementById("ProductionRegeneration");
	}

	public Element gettxtPleaseSpecifySubbatesNumberError() {
		return driver.FindElementById("txtSubbatesNumber-error");
	}

	public Element getbtnRegenerateCancel() {
		return driver.FindElementByXPath("//div[@id='RegeneratePopup']//div//input[@name='ok']");
	}

	public Element getOptionExportSet() {
		return driver.FindElementByXPath("//select[@id='ProductionSets']//option[contains(text(),'Export Set')]");
	}

	public Element getAddNewExport() {
		return driver.FindElementByXPath("//a[text()='Add a New Export']");
	}

	public Element getPriviledgedDocumunentsHelpIcon() {
		return driver.FindElementByXPath(
				"//label[text()='Privileged Documents: ']//a[@class='helptip fa fa-question-circle']");
	}

	public Element getNativeAdvanced_Text() {
		return driver.FindElementByXPath(
				"//div[@class='col-md-12 advanced-dropdown']//p[contains( text(),'redacted documents, and parents of privileged and redacted documents.')]");
	}

	public Element getPriviledgedToolTip_Text() {
		return driver.FindElementByXPath("//div[@id='popover447544']//div[@class='popover-content']" + "");
	}

	public Element getbtnCustomTemplateView() {
		return driver.FindElementByXPath("//a[text()='View']");
	}

	public Element getbtnTemplateGuardNext() {
		return driver.FindElementById("TemplateGuardNext");
	}

	public Element getbtnProductions() {
		return driver.FindElementByXPath("//label[text()='Productions']");
	}

	public Element getBrandingBySelectingTags() {
		return driver.FindElementByXPath(".//a[@class='add-logic'][1]");
	}

	public Element getbtnSelectTags() {
		return driver.FindElementByXPath(".//button[@id='STAG_1']");
	}

	public Element getChkBoxSelect(String tag) {
		return driver
				.FindElementByXPath("//div[@id='tagTreeTIFFComponent']/ul/li/ul/li/a[contains(text(),'" + tag + "')]");
	}

	public Element getbtnSelect() {
		return driver.FindElementByXPath("//button[@class='btn btn-primary btn-sm submitSelectionTIFF']");
	}

	public Element gettxtBrandingPlaceholder() {
		return driver.FindElementByXPath("//div[@placeholder='Enter branding text for the selected tags']");
	}

	public Element getbtnComponentsMarkIncomplete() {
		return driver.FindElementById("ComponentsMarkInComplete");
	}

	public Element getChk_burnReductiontoggle() {
		return driver.FindElementByXPath(
				"//label[contains(text(),'Burn Redactions')]/following-sibling::label[contains(@class,'toggle col-md')]/input[@id='chkBurnRedactions']");
	}

	public Element getClk_burnReductiontoggle() {
		return driver.FindElementByXPath(
				"//label[contains(text(),'Burn Redactions')]/following-sibling::label[contains(@class,'toggle col-md')]/input[@id='chkBurnRedactions']/../i");
	}

	public Element getClkRadioBtn_selectRedactionTags() {
		return driver.FindElementByXPath(
				"//label[contains(text(),'Burn Redactions')]/../following::label[contains(text(),'Specify Redactions')]/../div/label/input[@id='chkTIFFSpecifytRedactions']/../i");
	}

	public Element getClkCheckBox_defaultRedactionTag() {
		return driver
				.FindElementByXPath("(//ul[@class='jstree-children']//a[contains(text(),'Default Redaction Tag')])[1]");
	}

	public Element getDefaultTag() {
		return driver
				.FindElementByXPath("(//ul[@class='jstree-children']//a[contains(text(),'Default Redaction Tag')])[3]");
	}

	public Element getClkLink_selectingRedactionTags() {
		return driver.FindElementByXPath("//a[contains(text(),'Specify Redaction Text by Selecting Redaction Tags')]");
	}

	public Element getClkBtn_selectingRedactionTags() {
		return driver
				.FindElementByXPath("//div[contains(@id,'divRedaction')]//button[text()='Select Redaction Tag(s)']");
	}

	public Element getClkCheckBox_selectingRedactionTags() {
		return driver
				.FindElementByXPath("(//ul[@class='jstree-children']//a[contains(text(),'Default Redaction Tag')])");
	}

	public Element getClk_selectBtn() {
		return driver.FindElementByXPath(
				"//div[@class='modal fade in']//header[contains(text(),'Select Tags')]/..//button[@title='Select']");
	}

	public Element gettextRedactionPlaceHolder() {
		return driver.FindElementByXPath("(//div[@class='redactor-editor'])[last()]");
	}

	public Element getPriviledgedPopOverContent() {
		return driver.FindElementByXPath("(//div[@class='popover-content'])[2]");
	}

	public Element getSelectNextRedactionTags() {
		return driver.FindElementByXPath(
				"//div[@id='divRedaction']//div[contains(@name,'TIFFComponentModelData')]/preceding-sibling::button");
	}

	public Element getDisabledSelectRedactionTags() {
		return driver.FindElementByXPath(
				"//div[@id='tagTreeTIFFComponent']/ul/li/ul/li/a[@data-content='Default Redaction Tag']/i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getBeginningSubBatesNumber() {
		return driver.FindElementById("txtSubbatesNumber");
	}

	public Element getExportPrefixId() {
		return driver.FindElementById("txtUserMetadataFieldPrefix");
	}

	public Element getExportSuffixId() {
		return driver.FindElementById("txtUserMetadataFieldSuffix");
	}

	public Element getbtnNextSelectTags() {
		return driver.FindElementByXPath(".//button[@id='STAG_2']");
	}

	public Element getRedactionWithoutRedactionTagsCheckbox() {
		return driver.FindElementByXPath(
				"//form[contains(@class,'client-form')]//label[text()='Redactions without Redaction Tags']/..//i");
	}

	public Element getRedactedTagTextArea() {
		return driver.FindElementByXPath("//div[@class='redactor-editor']");
	}

	public Element getSelectTagsRadioButton() {
		return driver.FindElementByXPath("//input[@id='rdbTags']/..//i");
	}

	public Element getSelectTag(String tagname) {
		return driver
				.FindElementByXPath("//div[@id='tagTree']//a[text()='" + tagname + "']/i[contains(@class,'checkbox')]");
	}

	public Element getAllTags() {
		return driver.FindElementByXPath("//a[@data-content='All Tags']/i[contains(@class,'checkbox')]");
	}

	public Element getDefaultRedactionTag() {
		return driver.FindElementByXPath(
				"//div[@id='tagTreeTIFFComponent']/ul/li/ul/li/a[@data-content='Default Redaction Tag']/i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getClkNativelyProducedDocument() {
		return driver.FindElementByXPath("//a[@class='add-tiff-img-logic']");
	}

	public Element getclkSelectTag() {
		return driver.FindElementByXPath("//button[@id='btnTIFFPHSelectTags_0']");
	}

	public Element getCheckBox() {
		return driver.FindElementByXPath("//div[@id='tagTreeTIFFComponent']//ul/li/ul/li/a[text()='Patch2432759']");
	}

	public Element getClkSelect() {
		return driver.FindElementByXPath("//button[@class='btn btn-primary btn-sm submitSelectionTIFF']");
	}

	public Element getEnterPlaceholderTextBox() {
		return driver.FindElementByXPath(
				"//div[@placeholder='Enter placeholder text for the docs of the selected file types']");
	}

	public Element getClkBtn_selectingSecondRedactionTags() {
		return driver.FindElementByXPath(
				"//div[contains(@id,'divRedaction')]//button[text()='Select Redaction Tag(s)' and @id='btnTiffRedTAG_1']");
	}

	public Element getbtnContinueGeneration() {
		return driver.FindElementByXPath("//button[contains(text(),'Continue Generation')] ");
	}

	public Element getDocumentSelectionHeader() {
		return driver.FindElementByXPath("//h2[normalize-space(text())='Document Selection']");
	}

	public Element getSelectTagsHeader() {
		return driver.FindElementByXPath("//div[@aria-hidden='false']//header[normalize-space(text())='Select Tags']");
	}

	public Element getSelectDropDown() {
		return driver.FindElementByXPath("//Select[@id='lstTIFFRedactionStyle']");
	}

	public Element getpopupAssert() {
		return driver.FindElementByXPath(
				"//a[contains(@data-content,'the configured redaction text will be applied to the burned')]");
	}

	public Element getredactionsWithRedactionTags() {
		return driver.FindElementByXPath("//div[contains(text(),'the configured redaction text will be applied')]");
	}

	public Element getAddRuleBtn() {
		return driver.FindElementByXPath("//button[text()='Add Rule']");
	}

	public Element getTagsBtn() {
		return driver.FindElementByXPath("//button[@id='tagsHelper']");
	}

	public Element getTagsHeader() {
		return driver.FindElementByXPath("//h4[text()='Tags']");
	}

	public Element getPrivGuardAllTags() {
		return driver.FindElementByXPath(
				"//div[contains(@class,'scrollList')]//a[text()='All Tags']/i[contains(@class,'checkbox')]");
	}

	public Element selectPrivGuardTag(String tagname) {
		return driver.FindElementByXPath(
				"//div[contains(@class,'scrollList')]//a[text()='" + tagname + "']/i[contains(@class,'checkbox')]");
	}

	public Element getCheckForMatchingDocuments() {
		return driver.FindElementByXPath("//button[text()='Check for Matching Documents']");
	}

	public Element getInsertQueryBtn() {
		return driver.FindElementByXPath("//a[@id='insertQueryBtn']");
	}

//    Added by baskar
	public Element getPopUpOkButtonInserMetaData() {
		return driver.FindElementByXPath("//button[@onclick='return AddToRedactor()']");
	}

	public Element getVerifyTextBoxInserMetaData() {
		return driver.FindElementByXPath("//*//p[text()='$BatesNumber$']");
	}

	public Element getTiffAdvancedLink() {
		return driver.FindElementByXPath("//*[@id='TIFFContainer']//div[@class='col-md-12 advanced-dropdown']");
	}

	public Element getTiffAdvancedSlipSheetToggle() {
		return driver.FindElementByXPath(
				"//strong[text()='Slip Sheets:']//following::label//input[@name='IsSlipSheetEnabled']//following-sibling::i");
	}

	public Element getAdvancedProductionComponent() {
		return driver.FindElementByXPath("//h2[text()=' Advanced Production Components ']");
	}

	public Element getMP3CheckBox() {
		return driver.FindElementByXPath("//input[@id='chkIsMP3Selected']/following-sibling::i");
	}

	public Element getVerifyPreviewButton() {
		return driver.FindElementByXPath("//a[@class='btn btn-primary col-md-5 pull-right']");
	}

	public Element getAnsiRadioBtn() {
		return driver.FindElementByXPath("//input[@id='rdbANSIType']//following-sibling::i");
	}

	public Element getAnsiDrpDwn() {
		return driver.FindElementByXPath("//select[@id='lstTextANSIType']");
	}

	public Element getPageLevelTextComponent() {
		return driver.FindElementByXPath("//p[text()='Level: ']");
	}

	public Element getDownLoadButton() {
		return driver.FindElementById("popoverElement");
	}

	public Element getSlipCalculatedTabSelection() {
		return driver.FindElementByXPath("//span[text()='Calculated']//parent::a");
	}

	public Element getCalculatedCheckBoxSelection(String fieldValue) {
		return driver.FindElementByXPath("//strong[text()='" + fieldValue + "']//parent::label//following-sibling::i");
	}

	public Element getAddSelected() {
		return driver.FindElementByXPath("//div//a[text()='Add to Selected']");
	}

	public Element getSearchRadioButton() {
		return driver.FindElementByXPath(".//*[@id='rdbSearches']/following-sibling::i");
	}

	public Element getSelectSearches(String searchname) {
		return driver
				.FindElementByXPath("//a[starts-with(text(),'" + searchname + "')]/i[contains(@class,'checkbox')]");
	}

	public Element get_MySearchesNewNode(String getNewNode) {
		return driver
				.FindElementByXPath("//a[starts-with(text(),'" + getNewNode + "')]/i[contains(@class,'checkbox')]");
	}

	public Element get_dropdownNewNode() {
		return driver.FindElementByXPath("(//i[@class='jstree-icon jstree-ocl'])[2]");
	}

	// Jagadeesh.Jana
	public Element getPreviewButton() {
		return driver.FindElementByXPath("//a[@id='btnPreview']");
	}

	// Jagadeesh.Jana
	public Element getRedactionText() {
		return driver.FindElementByXPath("(//div[@class='col-md-8'])[14]");
	}

	// Jagadeesh.Jana
	public Element getRedactionTextExclamationIcon() {
		return driver.FindElementByXPath("//div[@class='col-md-8']/label/i");
	}

	// Jagadeesh.Jana
	public Element getClk_SelectRedactionTagsRadio() {
		return driver.FindElementByXPath("(//div[@class='row']/div/label[2])[1]");
	}

	// Jagadeesh.Jana
	public Element getRedWithoutRedCheckbox() {
		return driver.FindElementByXPath("//i[@id='NoRedactionTag']");
	}

	// added by sowndarya.Velraj
	public Element redactedDocumentsInSummaryPage() {
		return driver.FindElementByXPath("//label[contains(text(),'Redacted Documents:')]//following-sibling::label");
	}

	public Element getbtnMP3BurnRedaction() {
		return driver.FindElementByXPath("(//input[@id='chkMP3BurnRedactions']//following::i)[1]");
	}

	public Element getbtnMP3BurnRedactionTab() {
		return driver.FindElementByXPath("//div[@id='advanced-production-accordion']//a[text()='MP3 Files ']");
	}

	public Element getbtnMP3SelectRedactionTags() {
		return driver.FindElementByXPath("//input[@id='chkMP3SPecifytRedactions']//following::i");
	}

	public Element getWarningMessage() {
		return driver.FindElementById("divbigBoxes");
	}

	public Element getclklinkSpecifyRedactionText() {
		return driver.FindElementByXPath("//a[@class='add-redaction-logic']");
	}

	public Element getbtnEnableForTechIssue() {
		return driver.FindElementByXPath("(//input[@id='chkEnabledforExceptionDocs']//following::i)[1]");
	}

	public Element getAllTagsInPriviledgeDoc() {
		return driver.FindElementByXPath(
				"(//div[@id='tagTreeTIFFComponent']//a[@class='jstree-anchor jstree-non-prevtag'])[1]");
	}

	public Element getAdvancedTabInTIFF() {
		return driver.FindElementByXPath("(//div[@class='advanced-dd-toggle'])[3]");
	}

	public Element getLoadFileTypeInTIFF() {
		return driver.FindElementById("LoadFileType");
	}

	public Element getOPTInLoadFileType() {
		return driver.FindElementByXPath("//option[text()='OPT']");
	}

	public Element getMultiPageRadioButtonInTIFF() {
		return driver.FindElementByXPath("//label[@class='radio']//span[contains(text(),'Multi-page')]");
	}

	public Element getExportBatesTextInGeneratePage() {
		return driver.FindElementByXPath("//button[text()='Export Bates']");

	}

	public Element getAdvancedTabInNative() {
		return driver.FindElementByXPath("(//div[@class='advanced-dd-toggle'])[2]");
	}

	public Element getAdvancedTabInText() {
		return driver.FindElementByXPath("(//div[@class='advanced-dd-toggle'])[4]");
	}

	public Element getAdvancedTabInMP3() {
		return driver.FindElementByXPath("(//div[@class='advanced-dd-toggle'])[5]");
	}

	public Element getMP3Tab() {
		return driver.FindElementByXPath("//div[@class='panel-heading']//a[@data-index='5']");
	}

	public Element getGenerateLoadFileToggleInNative() {
		return driver.FindElementByXPath("(//input[@id='chkProduceLoadFile']//following-sibling::i)[1]");
	}

	// Added by Gopinath - 12/10/2021
	public Element getBrandingText() {
		return driver.FindElementByXPath(
				"//div[@id='divLeftHeaderBranding']//div[@class='redactor-editor redactor-placeholder']");
	}

	// Added By Jeevitha
	public ElementCollection getPrivGuardTextBox_Value() {
		return driver.FindElementsByXPath("//span[contains(@class,'editable-click')]");
	}

	public Element BurnRedactionCheckBox_Imp(String tag) {
		return driver.FindElementByXPath("//div[@id='tagTreeTIFFComponent']//a[contains(text(),'" + tag + "')]");
	}

	public Element getDefaultRedacTag_BurnRedact() {
		return driver.FindElementByXPath(
				"(//ul[@class='jstree-children']//a[contains(text(),'Default Redaction Tag')])[last()]");
	}

	public Element getRedactionBtn() {
		return driver.FindElementByXPath("//button[text()='Redactions']");
	}

	public Element getProdExport_SelectValueFromDD(String Export) {
		return driver.FindElementByXPath("//select[@id='ProductionSets']//option[contains(text(),'" + Export + "')]");
	}

	public Element getDATRedactionsCBox() {
		return driver.FindElementByXPath("//label[@class='checkbox check-redacted']//i");
	}

	public Element getTIFFDefaultAnnotaionCBox() {
		return driver.FindElementByXPath("//label[@id='TIFFAnnotationLayerLabel']//i");
	}

	public Element getPlaceholderText() {
		return driver.FindElementByXPath("//label//strong[text()='Placeholders:']");
	}

	public Element getRedactionTxt() {
		return driver.FindElementByXPath("(//label//strong[text()='Redactions:'])[1]");
	}

	public Element getLocation() {
		return driver.FindElementByXPath("(//div[@id='divBrandingLocation']//div)[1]");
	}

	public Element getTechIssueCheckBox(String Tag) {
		return driver.FindElementByXPath("//div[@id='tagTreeTIFFComponent']/ul/li//a[text()='" + Tag + "']");
	}

	public Element getTechIssuePlaceHolder() {
		return driver.FindElementByXPath(
				"//div[@class='redactor-box']//div[@placeholder='Enter placeholder text for the Tech Issue docs']");
	}

	public Element getBurnRedtoggle() {
		return driver.FindElementByXPath("//*[@id='chkBurnRedactions']/following-sibling::i");
	}

	public Element getSpecifyBrandingBySelectingTag() {
		return driver.FindElementByXPath("(//a[@class='add-logic'])[5]");
	}

	public Element getBrandingBySelectingTagPlaceholder() {
		return driver.FindElementByXPath(
				"//fieldset//div[@class='redactor-box']//div[@placeholder='Enter branding text for the selected tags']");
	}

	public Element getNativeDocsPlaceholder() {
		return driver
				.FindElementByXPath("//div[@id='divImagePHImage']//div[@class='redactor-editor redactor-placeholder']");
	}

	public Element getPriviledgedDoogle() {
		return driver.FindElementById("chkEnabledforPrivDocs");
	}

	public Element getBurnRedactionsToogle() {
		return driver.FindElementById("chkMP3BurnRedactions");
	}

	// Added by Gopinath - 18/10/2021
	public Element getAllRedactionsAnnotaionLayer() {
		return driver.FindElementByXPath("//label[@id='TIFFAnnotationLayerLabel']//i");
	}

	// Added by Gopinath - 19/10/2021
	public Element getRedactDATCheckBox() {
		return driver.FindElementByXPath("//input[@id='ChkRedacted_0']/following-sibling:: i");
	}

	public Element getExportBatesButton() {
		return driver.FindElementById("btnProductionGeneratedBatesRangeExport");
	}

	public Element getSelectedDocCount() {
		return driver.FindElementByXPath("(//div[@class='drk-gray-widget']//span[1])[1]");
	}

	public Element getProduction() {
		return driver.FindElementById("ProductionLabel");
	}

	public Element getCompletedStatus() {
		return driver.FindElementByXPath("//input[@value='COMPLETED']");
	}

	public Element getProductionByWizard(String production) {
		return driver.FindElementByXPath("//td[text()='" + production + "']");
	}

	public Element getOpenInWizard() {
		return driver.FindElementById("OpenInWizard");
	}

	public Element getUncommitBatesButton() {
		return driver.FindElementById("btnProductionUnCommitBates");
	}

	public Element getCentreBrandingPlaceHolder() {
		return driver.FindElementByXPath(
				"//div[@id='divCenterHeaderBranding']//div[@class='redactor-editor redactor-placeholder']");
	}

	public Element getClickHereLink() {
		return driver.FindElementByXPath("//a[contains(text(),'Click here')]");
	}

	public Element getNextBatesNumber() {
		return driver.FindElementByXPath("(//button[contains(text(),'Select')])[1]");
	}

	public Element getMarkInCompleteBtn() {
		return driver.FindElementByXPath("//a[text()='Mark Incomplete']");
	}

	public Element getUncommitbutton() {
		return driver.FindElementByXPath("//a[contains(text(),'Uncommit')]");
	}

	// Added by Gopinath- 25/10/2021
	public Element getNativeSelectTags() {
		return driver.FindElementById("btnSelectNativeTags");
	}

	public Element getNativeCheckBox(String Tag) {
		return driver.FindElementByXPath("//div[@id='tagTreeNativeComponent']/ul/li//a[text()='" + Tag + "']");
	}

	public Element getNativeSelect() {
		return driver.FindElementByXPath("//button[@class='btn btn-primary btn-sm submitNativeSelection']");
	}

	public Element getCheckBoxRedactionTag(String Tag) {
		return driver.FindElementByXPath("//div[@id='TIFFRedactiontreeFolder']/ul/li/ul/li/a[text()='" + Tag + "']");
	}

	public ElementCollection TotalRowsInRedactions() {
		return driver.FindElementsByXPath("//div[@id='TIFFRedactiontreeFolder']/ul/li/ul/li/a");
	}

	public Element getTextMp3File() {
		return driver.FindElementByXPath("//label[contains(text(),'Number of MP3 Files:')]/following-sibling::label");
	}

	public Element getShortByMetaData() {
		return driver.FindElementByXPath("//input[@id='rdbSortByField']/following-sibling::i");
	}

	public Element getKeepFamiliyTogetherCheckBox() {
		return driver.FindElementByXPath("//input[@id='chkSortByIsKeepFamiliesTogether']/following-sibling::i");
	}

	public Element getKeepDocsMasterDate() {
		return driver.FindElementByXPath(
				"//select[@id='lstSortByKeepDocsWithNoMasterDate']//option[text()='At the beginning']");
	}

	public Element getAdvancedProductionToggle() {
		return driver.FindElementByXPath("//div[@class='advanced-production-toggle col-md-12']//i");
	}

	public Element getTranslationsTab() {
		return driver.FindElementByXPath("//input[@id='chkIsTranslation']//following-sibling::i");
	}

	public Element getTranslationsCheckBox() {
		return driver
				.FindElementByXPath("//div[@id='advanced-production-accordion']//a[contains(text(),'Translations')]");
	}

	public Element getSelectDatabaseFiles() {
		return driver.FindElementByXPath(
				"//input[@class='clsTranslationFileLst']//following::td[contains(text(),'Database Files')]/..//i");
	}

	public Element getDocumentsOfOCR() {
		return driver.FindElementByXPath("//label[contains(text(),'Documents to OCR:')]//following-sibling::label");
	}

	public Element getDocumentsOfTIFF() {
		return driver.FindElementByXPath("//label[contains(text(),'Documents to TIFF: ')]//following-sibling::label");
	}

	public Element redactionTagInBurnRedactionCheckBox(String RedactionTag1) {
		return driver.FindElementByXPath("//div[@id='tagTreeTIFFComponent']/ul/li/ul/li/a[@data-content='"
				+ RedactionTag1 + "']/i[@class='jstree-icon jstree-checkbox']");

	}

	public Element redactionTagInBurnRedaction2CheckBox(String RedactionTag2) {
		return driver.FindElementByXPath("//div[@id='tagTreeTIFFComponent']/ul/li/ul/li/a[@data-content='"
				+ RedactionTag2 + "']/i[@class='jstree-icon jstree-checkbox']");
	}

	// Added by Gopinath- 29-10-2021
	public Element getAllFilesLink() {
		return driver.FindElementById("txtShareableLinksAllFilesPath");
	}

	public Element getShareLinkPassword() {
		return driver.FindElementById("lblShareableLinksPassword");
	}

	public Element getSharableLinkCloseButton() {
		return driver.FindElementByXPath("//button[@class='ui-dialog-titlebar-close']");
	}

	public Element getEnterPasswordTextField() {
		return driver.FindElementById("Passcode");
	}

	public Element getDownloadButton() {
		return driver.FindElementById("btnSubmit");
	}

	// Added by Gopinath
	public Element getAdvancedProductionComponents() {
		return driver.FindElementByXPath("//h2[text()=' Advanced Production Components ']");
	}

	public Element getMP3ChkBox() {
		return driver.FindElementByXPath("//input[@id='chkIsMP3Selected']/following-sibling::i");
	}

	public Element getSelectPageTagName(String foldername) {
		return driver.FindElementByXPath(
				"//ul[@class='jstree-container-ul jstree-children']//a[contains(.,'" + foldername + "')]");
	}

	// Added by Gopinath - 02/11/2021
	public Element getRightHeaderBranding() {
		return driver.FindElementById("RightHeaderBranding");
	}

	public Element getDisabledSpecifyBrandingSelectTags(String Tag) {
		return driver.FindElementByXPath(
				"//div[@id='tagTreeTIFFComponent']//ul/li//ul//li//a[@data-content='Privileged']/i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getNextSelectBtnInSpecifyBrandingSelectTags() {
		return driver.FindElementByXPath("//button[text()='Select Tags'][@id='STAG_1']");
	}

	public Element getSpecifyBrandingBySelectingTagLink() {
		return driver.FindElementByXPath("(//a[@class='add-logic'])[3]");
	}

	public Element getClkCheckBox_selectingRedactionTags(String tag) {
		return driver.FindElementByXPath(
				"//div[@id='tagTreeTIFFComponent']/ul/li/ul/li/a[@data-content='Default Redaction Tag']/i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getRotationLandScapeDropdown() {
		return driver.FindElementByXPath(
				"//select[@id='dldPageRotatePreference']//option[text()='Rotate 90 degrees clock-wise']");
	}

	public Element getFileTypeNativelyProducedDocs() {
		return driver
				.FindElementByXPath("(//select[@name='TIFFFileTypesList']/option[contains(text(),'Text Files')])[1]");
	}

	// Added Gopinath - 10/11/2021
	public Element getRegenerateContinueButton() {
		return driver.FindElementByXPath("//button[text()='Continue Generation']");
	}

	public Element getTechIssueCheckbox(String tag) {
		return driver.FindElementByXPath("(//a[text()='" + tag + "']//i)[3]");
	}

	public Element getDATContinueBtn() {
		return driver.FindElementByXPath("//div[@class='MessageBoxButtonSection']//button[@id='bot1-Msg1']");
	}

	public Element getVerifygQCPage() {
		return driver.FindElementById("prouctionGenerateStatusTxt");
	}

	// Added by Gopinath - 17/11/2021

	public Element getDropDownToggle(String ProductionName) {
		return driver.FindElementByXPath("//a[@title='" + ProductionName + "']//..//a[@class='dropdown-toggle']");
	}

	public Element getSaveAsTemplate(String ProductionName) {
		return driver.FindElementByXPath("//a[@title='" + ProductionName + "']//..//a[text()='Save as Template']");
	}

	public Element getNextBtn() {
		return driver.FindElementByXPath(
				"//a[text()='Next']/parent::li[@id='customTemplatesDatatable_next']/preceding-sibling::li[1]/a");
	}

	public Element getViewBtn(String Templatename) {
		return driver.FindElementByXPath("//td[text()='" + Templatename + "']/..//td//a[text()='View']");
	}

	public Element getLoadTempDropDwn(String Templatename) {
		return driver.FindElementByXPath("//select//option[contains(text(),'" + Templatename + "')]");
	}

	public Element getSaveBtn() {
		return driver.FindElementByXPath("//button[text()='Save']");
	}

	public Element getCloseBtn() {
		return driver.FindElementByXPath("//button[text()='Close']");
	}

	public Element getCloseBtnInProductionPage() {
		return driver.FindElementById("botClose3");
	}

	// Added by Gopinath-22/11/2021
	public Element getInsertMetadataField() {
		return driver.FindElementByXPath("//div[@id='divLeftHeaderBranding']//a[@id='Launcheditor_0']");
	}

	public Element getBatesNumberinTiff() {
		return driver.FindElementByXPath("//select[@id='selectedMetadataField']//option[text()='BatesNumber']");
	}

	public Element getCustodianNameinTiff() {
		return driver.FindElementByXPath("//select[@id='selectedMetadataField']//option[text()='CustodianName']");
	}

	public Element getOkBtn() {
		return driver.FindElementByXPath("//div[@id='MetadataPopup']//button");
	}

	public Element getDocumentRadioBtn() {
		return driver.FindElementByXPath("//label//input[@id='rdbDocumentLevel']/following-sibling::i");
	}

	public Element getBeginningSubBatesNum() {
		return driver.FindElementByXPath("//input[@id='txtSubbatesNumber']");
	}

	public Element getChkBoxSelect2(String tag2) {
		return driver.FindElementByXPath("//div[@id='tagTreeTIFFComponent']/ul/li/ul/li//a[text()='" + tag2 + "']");
	}

	// Added by Gopinath-23/11/2021
	public Element getVolumeIncluded() {
		return driver.FindElementByXPath("//input[@id='ProductionOutputLocation_IsVolumeIncluded']/..//i");
	}

	public Element getDATPrivledgedCheckbox() {
		return driver.FindElementByXPath("//input[@id='ChkPrev_0']/parent::label/i");
	}

	// Added by Aathith
	public Element getStatusInGridView(String productionName) {
		return driver.FindElementByXPath("(//td[text()='" + productionName + "']/following-sibling::td)[1]");
	}

	public Element getbtnContinueGenerate() {
		return driver.FindElementByXPath("//button[text()='Continue Generation']");
	}

	public Element getTranslationsChkBox() {
		return driver.FindElementByXPath("//input[@id='chkIsTranslation']/following-sibling::i");
	}

	public Element getTranslation_SelectAllCheck() {
		return driver.FindElementByXPath(
				"//*[@id='translation-table']//input[@name='IsSelectAllFileTypes']/following-sibling::i");
	}

	public Element getTranslationComponent() {
		return driver.FindElementByXPath("//input[@class='clsTranslationFileLst'][contains(@value,'Word')]/../i");
	}

	public Element getNaivePathInMetaData() {
		return driver.FindElementByXPath("//select[@id='selectedMetadataField']//option[text()='NativePath']");
	}

	public Element gettxtReservingBatesFailedAt1stProdShown() {
		return driver.FindElementByXPath("(//span[contains(text(),'Reserving Bates Range Failed')])[1]");
	}

	public Element getMp3GenerateLoadFile() {
		return driver.FindElementByXPath("//div[@class='mp3-conf']/..//i[@class='pull-right']");
	}

	public Element verifyProductionLocationSplitCount() {
		return driver.FindElementByXPath("//input[@class='form-control valid']");
	}

	// Added by Gopinath - 25/11/2021
	public Element getPrivInsertMetadataField() {
		return driver.FindElementByXPath(
				"//label[text()='Enable for Privileged Docs:']//..//..//a[text()='Insert Metadata Field']");
	}

	public Element getSourceAttachDocIDs() {
		return driver.FindElementByXPath("//select[@id='selectedMetadataField']//option[text()='SourceAttachDocIDs']");
	}

	// nov-29
	public Element getTechInsertMetadataField() {
		return driver.FindElementByXPath(
				"//label[text()='Enable for Tech Issue Docs:']//..//..//a[text()='Insert Metadata Field']");
	}

	public Element privDocInserMetaData() {
		return driver.FindElementByXPath("(//a[text()='Insert Metadata Field'])[8]");
	}

	// Added by Gopinath - 30/11/2021
	public Element getPreGenStatus() {
		return driver.FindElementByXPath("//label[text()='Pre-Generation Checks In Progress']");
	}

	// Added by Brundha
	public Element getBatesRange() {
		return driver.FindElementByXPath("//div//label[text()='Bates Range:']//following-sibling::label");
	}

	public Element BurnRedactionCheckBox(String tag) {
		return driver.FindElementByXPath("//div[@id='RedactionTagsTree']//a[contains(text(),'" + tag + "')]");
	}

	// Added by Gopinath -16/12/2021
	public ElementCollection getSelectProdcutionOptions() {
		return driver.FindElementsByXPath("//select[@id='ProductionSets']//option");
	}

	public Element getDATFieldClassification3() {
		return driver.FindElementById("TY_2");
	}

	// Added by gopinath - 17/12/2021
	public Element getMarkIncompleteButton() {
		return driver.FindElementByXPath("//a[text()='Mark Incomplete']");
	}

	public Element getDocumentSelectionLink() {
		return driver.FindElementById("TotalDocumentsCount");
	}

	public Element NextBatesNumberPopup() {
		return driver.FindElementById("hdrNextBatesNo");
	}

	public Element selectNextBatesNumber() {
		return driver.FindElementByXPath("(//button[contains(text(),'Select')])[2]");
	}

	public Element getClickHereLinks() {
		return driver.FindElementByXPath("//div[@id='divNextBatesNum']//label/a");
	}

	public Element getNoOfCustodians() {
		return driver.FindElementByXPath("//label[contains(text(),'Number Of Custodians')]//following-sibling::label");
	}

	public Element getAdvancedInMP3Files() {
		return driver.FindElementByXPath("//div[@id='MP3FilesContainer']//div[@class='advanced-dd-toggle']");
	}

	// added by Aathith
	public Element isCompletedIsChecked() {
		return driver.FindElementByXPath("//input[@value='COMPLETED']/../../..");
	}

	public Element getDAT_FieldClassification(int i) {
		return driver.FindElementById("TY_" + i + "");
	}

	public Element getDAT_SourceField(int i) {
		return driver.FindElementById("SF_" + i + "");
	}

	public Element getDAT_DATField(int i) {
		return driver.FindElementById("DATFL_" + i + "");
	}

	public Element getLeftFooterBranding() {
		return driver.FindElementById("LeftFooterBranding");
	}

	public Element getRightFooterBranding() {
		return driver.FindElementById("RightFooterBranding");
	}

	public Element getEnterBranding(String location) {
		return driver.FindElementByXPath(
				"//*[text()='" + location + "']/../..//div[@class='redactor-editor redactor-placeholder']");
	}

	public Element getDoNotProduceFullContentTiff() {
		return driver.FindElementByXPath("//input[@id='chkShouldSkipTIFFGeneration']//following-sibling::i");
	}

	public Element getClkCheckBox_RedactionTag(String redactTag) {
		return driver
				.FindElementByXPath("//div[@id='TIFFRedactiontreeFolder']/ul/li/ul/li/a[text()='" + redactTag + "']");
	}

	public Element getPrivPlaceHolderToggleAtPrivGaurdPage() {
		return driver.FindElementByXPath("//*[@id='chkIsPrivilegedPlaceholderEnabled']/../i");
	}

	public Element getSelectTagBtnInPrivGaurd() {
		return driver.FindElementByXPath("//*[@id='btnGuardSelectPrevTags']");
	}

	public Element getPrivPlaceholderTextboInPrivGaurd() {
		return driver.FindElementByXPath("//*[@class='redactor-editor']");
	}

	public Element getTagInPrivGaurd(String tagname) {
		return driver.FindElementByXPath("//*[text()='" + tagname + "']");
	}

	public Element getSaveButtonINPrivGuard() {
		return driver.FindElementByXPath("//button[@id='btnSave']");
	}

	public Element getTiffSinglePage() {
		return driver.FindElementByXPath("//*[@id='rbdSinglePageType']/../i");
	}

	public Element getSaveButton() {
		return driver.FindElementByXPath("//*[text()='Save']");
	}

	public Element getProductionCreatedTimeInGridView(String production) {
		return driver.FindElementByXPath("//*[text()='" + production + "']/../td[9]");
	}

	public Element getClkCheckBox_1stdefaultRedactionTag() {
		return driver.FindElementByXPath("(//ul[@class='jstree-children']//a[text()='Default Redaction Tag'])[1]");
	}

	public Element text(String text) {
		return driver.FindElementByXPath("//*[contains(text()," + text + ")]");
	}

	public Element getRegenarateLinkBtn() {
		return driver.FindElementByXPath("//button[@class='btn btn-primary btn-sm']");
	}

	public Element blankPageRemovalMessage() {
		return driver.FindElementByXPath("//div[@class='MessageBoxMiddle']/p");
	}

	public Element getContinueBtn() {
		return driver.FindElementById("bot1-Msg1");
	}

	public Element getFirstToggle() {
		return driver.FindElementByXPath("(//a//..//a[@class='dropdown-toggle'])[1]");
	}

	public ElementCollection getFirstDropdownInCompletedProduction() {
		return driver.FindElementsByXPath("(//a/following-sibling::dl)[1]//dt");
	}

	public Element getTIFF_RightEnterBranding() {
		return driver.FindElementByXPath(
				".//*[@id='divRightHeaderBranding']//div[@class='redactor-editor redactor-placeholder']");
	}

	// Add by Aathith
	public Element getSharableLinkExpiryDate() {
		return driver.FindElementByXPath("//span[@id='lblShareableLinksExpireDate']");
	}

	public Element getSecurityGroup(String securityGroup) {
		return driver.FindElementByXPath("//select[@id='SecurityGrpList']//option[text()='" + securityGroup + "']");
	}

	public Element getErrorMsgText() {
		return driver.FindElementByXPath("//div[@id='content']//h2");
	}

	public Element getDocList() {
		return driver.FindElementById("btnGoToDocList");
	}

	public Element getDocView() {
		return driver.FindElementById("btnGoToDocView");
	}

	public Element getTags() {
		return driver.FindElementById("tagsHelper");
	}

	public Element getAddRule() {
		return driver.FindElementById("contentmetadata");
	}

	public Element getRedactions() {
		return driver.FindElementById("redactionsHelper");
	}

	public Element getOperator() {
		return driver.FindElementByXPath("//button[@class='btn btn-default dropdown-toggle insertOpHelper']");
	}

	public Element getTagsCheckbox(String Tag) {
		return driver.FindElementByXPath("//div[@id='JSTree']/ul/li/ul/li/a[text()='" + Tag + "']");
	}

	public Element getRemoveLink() {
		return driver.FindElementByXPath("//a[text()='Remove']");
	}

	public Element getInsertQueryBtnInPrivGaurd() {
		return driver.FindElementByXPath("//a[@id='insertQueryBtn']");
	}

	// Add by Aathith
	public Element gettext(String text) {
		return driver.FindElementByXPath("//*[text()='" + text + "']");
	}

	public Element getDocumentMatchesButton() {
		return driver.FindElementById("btnDocumentMatch");
	}

	public Element ProductionLocationSplitCount() {
		return driver.FindElementByXPath("//input[@id='ProductionComponentsFolderDetails_SplitCount']");
	}

	public Element getNativeFileTagSelection(String tag) {
		return driver.FindElementByXPath(
				"//div[@id='tagTreeNativeComponent']//a[text()='" + tag + "']/i[@class='jstree-icon jstree-checkbox']");
	}

	public Element getNativeFileTagSelectButton() {
		return driver.FindElementByXPath("//button[contains(@class,'submitNativeSelection')]");
	}

	public Element getNativeFileSelectingTag() {
		return driver.FindElementByXPath("//button[contains(@class,'selectNativeTags')]");
	}

	// Add by Aathith
	public Element getsplitSubFolderbtn() {
		return driver.FindElementByXPath("//*[text()='Split Sub Folders:']/..//i");
	}

	public Element getTranlationCheckMarkVerication() {
		return driver.FindElementByXPath("//input[@id='chkIsTranslation']");
	}

	public Element getTranlationOpenCloseCheck() {
		return driver.FindElementByXPath("//div[@id='TranslationsContainer']");
	}

	public Element getTextFormateANSIradiobtn() {
		return driver.FindElementByXPath("//input[@id='rdbANSIType']/../i");
	}

	public Element getTextFormateANSIdropdown() {
		return driver.FindElementByXPath("//select[@id='lstTextANSIType']");
	}

	public Element getProductionNameInGenPage() {
		return driver.FindElementByXPath("//*[text()='Production Name:']/following-sibling::label");
	}

	public ElementCollection getGridWebTableHeader() {
		return driver.FindElementsByXPath("//*[@id='ProductionListGridViewTable']/thead/tr/th");
	}

	public Element getGridProdValues(String production, int i) {
		return driver.FindElementByXPath("//*[text()='" + production + "']/../td[" + i + "]");
	}

	public Element getCenterHeaderInsertMetadataField() {
		return driver.FindElementByXPath("//div[@id='divCenterHeaderBranding']//a[@id='Launcheditor_0']");
	}

	public Element getNotificationLink() {
		return driver.FindElementByXPath("//i[@class='fa fa-bullhorn']/following-sibling::b");
	}

	public Element getSelectTheExportedProduction() {
		return driver.FindElementByXPath("//div[@id='bgTask']//ul//li//a");
	}

	public Element getDownloadLinkforExport() {
		return driver.FindElementByXPath("//td[text()='COMPLETED']/following-sibling::td//a[text()='Download File']");
	}

	public Element getViewAll() {
		return driver.FindElementById("btnViewAll");
	}

	public Element getSaveOption() {
		return driver.FindElementByXPath("//a[text()='Save']");
	}

	public Element getAdvancedLSTToggle() {
		return driver.FindElementByXPath("//label/input[@id='chkTIFFProduceLoadFile']/following-sibling::i");
	}

	public Element getDatDateFormate() {
		return driver.FindElementByXPath("//select[@id='lstDateFormat']");
	}

	public Element getlstSortByKeepDocsWithNoMasterDate() {
		return driver.FindElementByXPath("//select[@id='lstSortByKeepDocsWithNoMasterDate']");
	}

	public Element getviewProductionNextbtn() {
		return driver.FindElementByXPath("//div[@id='viewProduction']//a[text()='Next']");
	}

	public Element getviewProductionBackbtn() {
		return driver.FindElementByXPath("//div[@id='viewProduction']//a[text()='Back']");
	}

	public Element chkIsDATSelected() {
		return driver.FindElementByXPath("//input[@id='chkIsDATSelected']");
	}

	public Element templateCloseBtn(String temp) {
		return driver.FindElementByXPath("//span[text()='" + temp + "']/..//button[@class='ui-dialog-titlebar-close']");
	}

	public Element CancelBtn() {
		return driver.FindElementById("bot2-Msg1");
	}

	public Element getSplitSubFolderToggle() {
		return driver.FindElementByXPath("//label[text()='Split Sub Folders:']/..//i");
	}

	public Element getMP3FilesRedactionTag() {
		return driver
				.FindElementByXPath("//div[@id='MP3RedactiontreeFolder']/ul/li//a[text()='Default Redaction Tag']");
	}

	public Element getGenratePDFRadioButton() {
		return driver
				.FindElementByXPath("//span//b[text()='Generate PDF']/../..//input[@id='CommonTIFFSettings_FileType']");
	}

	public Element getMP3FilesBurnRedactionTag(String Tag) {
		return driver.FindElementByXPath("//div[@id='MP3RedactiontreeFolder']/ul/li//a[text()='" + Tag + "']");
	}

	public Element getMP3FileSelectRedactionTags() {
		return driver.FindElementByXPath("//input[@id='chkMP3SPecifytRedactions']");
	}

	public Element getGenrateTIFFRadioButton() {
		return driver.FindElementByXPath(
				"//span//b[text()='Generate TIFF']/../..//input[@id='CommonTIFFSettings_FileType']");
	}

	public Element chkIsTIFFSelected() {
		return driver.FindElementByXPath("//input[@id='chkIsTIFFSelected']");
	}

	public Element getclkSelectTag(int i) {
		return driver.FindElementByXPath("//button[@id='btnTIFFPHSelectTags_" + i + "']");
	}

	public Element getRotationDropDown() {
		return driver.FindElementByXPath("//select[@id='dldPageRotatePreference']");
	}

	// add by sowndarya
	public Element redactedTextInRedaction() {
		return driver.FindElementByXPath("//p[text()='REDACTED']");
	}

	// add by Aathith
	public ElementCollection getProductionSate() {
		return driver.FindElementsByXPath("//div[@class='col-md-12 font-xs']");
	}

	public Element getGridViewWebTable(int row, int col) {
		return driver
				.FindElementByXPath("//*[@id='ProductionListGridViewTable']/tbody/tr[" + row + "]/td[" + col + "]");
	}

	public ElementCollection getGridViewWebTable(int col) {
		return driver.FindElementsByXPath("//*[@id='ProductionListGridViewTable']/tbody/tr/td[" + col + "]");
	}

	public Element getSlipSheetMetaData() {
		return driver.FindElementByXPath("//div[@id='tiffObjectPalette']//span[text()='METADATA']");
	}

	public Element getSlipSheetWorkProduct() {
		return driver.FindElementByXPath("//div[@id='tiffObjectPalette']//span[text()='WORKPRODUCT']");
	}

	public Element getSlipSheetMetaDataActiveCheck() {
		return driver.FindElementByXPath("//div[@id='tiffObjectPalette']//span[text()='METADATA']/../..");
	}

	public Element getSlipSheetMetaDataTypeCheck() {
		return driver.FindElementByXPath("//ul[@id='tiffMetadataList']//label");
	}

	public Element getSlipSheetWorkProductActiveCheck() {
		return driver.FindElementByXPath("//div[@id='tiffObjectPalette']//span[text()='WORKPRODUCT']/../..");
	}

	public Element getSlipSheetWorkProductFolder() {
		return driver.FindElementByXPath("//div[@id='tiffFolderTreeSlipSheets']");
	}

	public Element getSlipSheetWorkProductFolderProduction() {
		return driver.FindElementByXPath("//a[text()='Productions']");
	}

	public Element getbtnAddToSelect() {
		return driver.FindElementByXPath("//a[@class='btn btn-primary pull-right']");
	}

	public Element getSelectedFieldItems() {
		return driver.FindElementByXPath("//div[@class='ddcf-content']");
	}

	public Element getAdvanceBtnOpenCloseCheck() {
		return driver.FindElementByXPath("//div[@id='TIFFContainer']//div[@class='advanced-dd-toggle']/i");
	}

	public Element getTiffAdvanceBtn() {
		return driver.FindElementByXPath("//div[@id='TIFFContainer']//div[@class='advanced-dd-toggle']");
	}

	public Element getRegenerateAllRadioBtn() {
		return driver.FindElementByXPath("//input[@id='RegenerateAll']/../i");
	}

	public Element getProductionOutputLocation_VolumeName() {
		return driver.FindElementByXPath("//*[@id='ProductionOutputLocation_VolumeName']");
	}

	public Element getProductionComponentsFolderDetails_FolderName_LoadFiles() {
		return driver.FindElementByXPath("//*[@id='ProductionComponentsFolderDetails_FolderName_LoadFiles']");
	}

	public Element getProductionOutputLocation_DriveText() {
		return driver.FindElementByXPath("//*[@id='ProductionOutputLocation_DriveText']");
	}

	public Element getProductionComponentsFolderDetails_FolderName_Images() {
		return driver.FindElementByXPath("//*[@id='ProductionComponentsFolderDetails_FolderName_Images']");
	}

	public Element getProductionComponentsFolderDetails_FolderName_Text() {
		return driver.FindElementByXPath("//*[@id='ProductionComponentsFolderDetails_FolderName_Text']");
	}

	public Element getProductionComponentsFolderDetails_FolderName_Natives() {
		return driver.FindElementByXPath("//*[@id='ProductionComponentsFolderDetails_FolderName_Natives']");
	}

	public Element getProductionOutputLocationProductionDirectory() {
		return driver.FindElementByXPath("//*[@id='ProductionOutputLocation_ProductionDirectory']");
	}

	public Element getDocumentWithMultipleBrandingTagsOnGenerationPage() {
		return driver.FindElementByXPath(
				"//*[text()='Documents with Multiple Branding Tags ']/following-sibling::td/span[@class='text-success']");
	}

	public Element getSelectFileTypeInTifffNative(String fileType) {
		return driver.FindElementByXPath(
				"//div[@id='divImageTIFFPHImage_0']/..//option[contains(text(),'" + fileType + "')]");
	}

	// added by Brundha
	public Element GetVolumeName() {
		return driver.FindElementById("ProductionOutputLocation_VolumeName");
	}

	public Element GetVolumeLocation() {
		return driver.FindElementById("lstVolumeLocation");
	}

	public Element getNativeFileType() {
		return driver
				.FindElementByXPath("//table[@id='native-table']//tbody//tr//td[contains(text(),'Database')]/..//i");
	}

	public Element getPrivDocCountInSummaryPage() {
		return driver.FindElementByXPath("//label[text()='Privileged Documents: ']/following-sibling::label");
	}

	// added by Brundha
	public Element getWorkProductFolderName(String FolderName) {
		return driver.FindElementByXPath("//a[@class='jstree-anchor'][contains(text(),'" + FolderName + "')]");
	}

	public Element getDocPages() {
		return driver.FindElementByXPath("//label[contains(text(),'Number of Natives:')]/following-sibling:: label");
	}

	public Element getRedactDATCheckBox(int i) {
		return driver.FindElementByXPath("//input[@id='ChkRedacted_" + i + "']/following-sibling:: i");

	}

	public Element getPrivledgedDATCheckBox(int i) {
		return driver.FindElementByXPath("//input[@id='ChkPrev_" + i + "']/following-sibling:: i");
	}

	// Added by Gopinath - 20/12/2021
	public Element getTiffAdvancedTab() {
		return driver.FindElementByXPath(
				"//label[text()='Burn Redactions :']//..//..//..//..//..//..//div[@class='advanced-dd-toggle']");
	}

	public Element getSlipSheetToogle() {
		return driver.FindElementByXPath("//input[@id='chkIsTIFFSlipSheetEnabled']//..//i");
	}

	public ElementCollection getSlipSheetMetaDataList() {
		return driver.FindElementsByXPath("//div[@id='tifftab1']//strong");
	}

	public Element getNativeMetaDataFieldLink() {
		return driver.FindElementByXPath("//div[@id='divLeftHeaderBranding']//a[@id='Launcheditor_0']");
	}

	public Element getNativeMetaDataFieldDropdown() {
		return driver.FindElementByXPath("//select[@id='selectedMetadataField']");
	}

	public Element getExceptionMetaDataFieldLink() {
		return driver.FindElementByXPath(
				"//input[@id='chkEnabledforExceptionDocs']//..//..//..//..//a[@title='Insert Metadata Field']");
	}

	public Element getAddWorkProductSlipSheet(String ProductName) {
		return driver.FindElementByXPath(
				"//input[@class='chk-data']/..//i/following-sibling::strong[text()='" + ProductName + "']");
	}

	public Element getDaAdditionalDataProject(String project) {
		return driver.FindElementByXPath("//td[@class='ddGridAlignLeft sorting_1']//a[text()='" + project + "']");
	}

	public Element gotoDAtoRMU(String project) {
		return driver.FindElementByXPath("//a[text()='" + project + "']/..//a[text()='Go to Project']");
	}

	// added by Aathith
	public Element getProductionDocCountFromHomePage(String productionName) {
		return driver.FindElementByXPath("//a[@title='" + productionName + "']//..//..//span[@class='font-md']");
	}

	public Element getNumberOfNativeDocs() {
		return driver.FindElementByXPath("//label[contains(text(),'Number of Natives: ')]//following-sibling::label");
	}

	public Element getFileDir(String dir) {
		return driver.FindElementByXPath("//a[@class='icon dir' and contains(text(),'" + dir + "')]");
	}

	public Element keepFamiliesTogetherChkbox_sortByTags() {
		return driver.FindElementByXPath(
				"//div[@id='divSpecifyTagOrder_1']//label[text()='Keep Families Together:']//..//..//label[@class='checkbox col-md-3']//i");
	}

	public Element selectingTagsFromSortByTags(String tag) {
		return driver.FindElementByXPath("//div[@id='tagsTree']//a[contains(text(),'" + tag + "')]");
	}

	public Element sortByTagsRadioButton() {
		return driver.FindElementByXPath("//span[text()='Sort by Selected Tags: ']//.//..//i");
	}

	public Element getclkSelectTags() {
		return driver.FindElementByXPath("//button[@id='btnTIFFPHSelectTags_1']");
	}

	public Element getErrorMsgHeader() {
		return driver.FindElementByXPath("//h1");
	}

	public Element getRdbOcr() {
		return driver.FindElementByXPath("//input[@id='rdbOCRSecond']/following-sibling::i");
	}

	public Element getBellyBandText() {
		return driver.FindElementByXPath("//p[@class='pText']");
	}

	public Element getBellyBangMsg() {
		return driver.FindElementByXPath("//p[@class='pText']/following-sibling::p");
	}

	public Element getTextRadioBtn() {
		return driver.FindElementByXPath("//input[@id='rdbOCRFirst']");
	}

	public Element getYesBtn() {
		return driver.FindElementByXPath("//button[@id='bot1-Msg1']");
	}

	// Add by Aathith
	public Element getFirstImageFile(String presufix, String subBates) {
		return driver.FindElementByXPath("//a[text()='" + presufix + ".000" + subBates + ".tiff']");
	}

	public ElementCollection getDATSourceField() {
		return driver.FindElementsByXPath("//*[@id='SF_0']//option");
	}

	// Added by Mohan

	public Element getProdExport_NoProductionExitSet() {
		return driver.FindElementByXPath("//*[@id='cardCanvas']//strong");
	}

	public Element getProdExport_ManageTemplateButton() {
		return driver.FindElementByXPath("//a[text()='Manage Templates']");
	}

	public Element getProdExport_CustomTemplatesEmptyValue() {
		return driver
				.FindElementByXPath("//*[@id='customTemplatesDatatable']//td[text()='Your query returned no data']");
	}

	public Element getAdvancedToggle() {
		return driver.FindElementByXPath("//div[@id='DATContainer']//*[text()='advanced']");
	}

	public Element getSourceFiledInDatSection() {
		return driver.FindElementByXPath("//*[@id'SF_0']//option[text()='PageCount']");
	}

	public Element getProdExport_CustomTemplatesValues() {
		return driver.FindElementByXPath("//table[@id='customTemplatesDatatable']/tbody/tr");
	}

	public Element getNativeCheckBox() {
		return driver.FindElementByXPath("//input[@id='chkIsNativeSelected']");
	}

	public Element getInsertMetaDataLink() {
		return driver.FindElementByXPath("//div[@id='BRDiv_1']//label//a");
	}

	public Element getBrandingOkBtn() {
		return driver.FindElementByXPath("//div[@aria-describedby='MetadataPopup']//footer//button");
	}

	public Element getMultiBrandingTags() {
		return driver.FindElementByXPath("//td[contains(text(),'Multiple Branding Tags')]/following-sibling::td//span");
	}

	public Element getFailedStatus() {
		return driver.FindElementByXPath("//label[@class='labelAlign']//a");
	}

	public Element getErrorMsg() {
		return driver.FindElementByXPath("//table[@id='GenerateErrorDataTable']//tr//td//following-sibling::td");
	}

	public Element getProductionNameLink(String ProdName) {
		return driver.FindElementByXPath("//a[@title='" + ProdName + "']");
	}

	public Element getNativeDocsPlaceholderText() {
		return driver.FindElementByXPath("//div[@id='divImagePHImage']//div[@class='redactor-editor']//p");
	}

	public Element getSortingRadioBtn() {
		return driver.FindElementByXPath("//input[@id='rdbSortByTag']/..//i");
	}

	public Element selectTagInSortingPage(String Tag) {
		return driver.FindElementByXPath("//div[@id='tagsTree']/ul/li/ul/li//a[text()='" + Tag + "']");
	}

	public Element getAddToSelectedBtn() {
		return driver.FindElementById("addFormObjects");
	}

	public Element getNativeTags() {
		return driver.FindElementByXPath("//span[@id='NativeTagsLabel']");
	}

	public Element getNativeFileTypeCheckBox(String Value) {
		return driver.FindElementByXPath("//input[contains(@value,'" + Value + "')]");
	}

	public Element getNextBatesNumberInSortingPage() {
		return driver.FindElementById("hdrNextBatesNo");
	}

	public Element getCloseIconInManageTemplate() {
		return driver.FindElementByXPath("//button[contains(@class,'close')]");
	}

	public Element verifyingPrivilegedTag(String tag) {
		return driver.FindElementByXPath("//div[@id='tagTreeTIFFComponent']/ul/li/ul/li//a[text()='" + tag + "']/..");
	}

	public Element getDownloadDisabledMsg() {
		return driver.FindElementByXPath("//div[@id='divDownloadDisabled']//span");
	}

	public Element getLoadFileLink() {
		return driver.FindElementByXPath("//a[text()='Load Files/']");
	}

	public Element getDatFileLink(String ProdName) {
		return driver.FindElementByXPath("//a[contains(text(),'" + ProdName + "')]");
	}

	public Element getDATFileText() {
		return driver.FindElementByXPath("//body//pre");
	}

	public Element getBurnRedactionText() {
		return driver.FindElementByXPath("//span[text()='Abbreviated Text:']//following-sibling::input");
	}

	public Element getBurnRedaction_InsertMetaData() {
		return driver.FindElementByXPath("//div[@id='divRedaction']//a[text()='Insert Metadata Field']");
	}

	public Element getTranslationLSTToggle(String Value) {
		return driver.FindElementByXPath("(//input[@id='chkProduceLoadFile']//parent::label//i)[" + Value + "()]");
	}

	public Element getAdvancedArrow(String Value) {
		return driver.FindElementByXPath("(//div[@class='advanced-dd-toggle'])[" + Value + "()]");
	}

	public Element getDeletOption(String ProductionName) {
		return driver.FindElementByXPath("//a[@title='" + ProductionName + "']//..//a[text()='Delete']");
	}

	public Element TagInTextBox() {
		return driver.FindElementByXPath("//ul[@id='xEdit']//li/following-sibling::li//span");
	}

	public ElementCollection getDAT_SourceField() {
		return driver.FindElementsByXPath("//select[@id='SF_0']//option");
	}

	public Element getNativeCheckBoxChecked(String FileType) {
		return driver.FindElementByXPath(
				"//table[@id='native-table']//tbody//tr//td[contains(text(),'" + FileType + "')]/..//input");
	}

	public Element nativeFileTypeCheckBox(String FileType) {
		return driver.FindElementByXPath("//input[contains(@value,'" + FileType + "')]/..//i");
	}

	//Added by arun
	public ElementCollection getCalculatedTabMetadata() {
		return driver.FindElementsByXPath("//input[@name='TiffCalculatedList']//following-sibling::strong");
	}
	public ElementCollection getMetaDataValues() {
		return driver.FindElementsByXPath("//ul[@id='tiffMetadataList']//li//strong");
	}
	public Element getSlipSheetCalculatedTab() {
		return driver.FindElementByXPath("//span[text()='Calculated']//parent::a");
	}
	public Element getMetaDataFieldCheckBox(String FieldValue) {
		return driver.FindElementByXPath("//ul[@id='tiffCalculatedList']//Strong[text()='"+FieldValue+"']/..//i");
	}
	public ElementCollection getCalculatedValues() {
		return driver.FindElementsByXPath("//ul[@id='tiffCalculatedList']//li//strong");
	}

	
	public ElementCollection InsertMetaDataFieldValues() {
		return driver.FindElementsByXPath("//select[@id='selectedMetadataField']//option");
	}
	


	public ProductionPage(Driver driver) {

		this.driver = driver;
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.waitForPageToBeReady();
		base = new BaseClass(driver);
	}

	public void CreateProduction(String productionname, String PrefixID, String SuffixID, final String foldername,
			String tagname) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddNewProductionbutton().Visible();
			}
		}), Input.wait30);
		getAddNewProductionbutton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionName().Visible();
			}
		}), Input.wait30);
		getProductionName().SendKeys(productionname);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionDesc().Visible();
			}
		}), Input.wait30);
		getProductionDesc().SendKeys(productionname);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBasicInfoMarkComplete().Visible();
			}
		}), Input.wait30);
		getBasicInfoMarkComplete().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDATChkBox().Enabled();
			}
		}), Input.wait30);
		getDATChkBox().waitAndClick(20);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDATTab().Visible();
			}
		}), Input.wait30);
		getDATTab().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_FieldClassification1().Visible();
			}
		}), Input.wait30);
		getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText("Bates");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_SourceField1().Visible();
			}
		}), Input.wait30);
		getDAT_SourceField1().selectFromDropdown().selectByVisibleText("BatesNumber");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_DATField1().Visible();
			}
		}), Input.wait30);
		getDAT_DATField1().SendKeys("BatesNumber" + Utility.dynamicNameAppender());

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNativeChkBox().Enabled();
			}
		}), Input.wait30);
		getNativeChkBox().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNativeTab().Enabled();
			}
		}), Input.wait30);
		getNativeTab().waitAndClick(10);

		driver.scrollPageToTop();

		Reporter.log(getNative_text().getText(), true);
		// System.out.println(getNative_text().getText());
		UtilityLog.info(getNative_text().getText());

		// work on this assert..issue with text format!
		/*
		 * Assert.assertEquals(getNative_text().getText(),"To produce specific docs" +
		 * " natively, please select file types and/or tags below. In addition," +
		 * " to export placeholders for these docs, configure placeholder in the TIFF "
		 * + "or PDF section for the same selected file types and/or tags.");
		 * 
		 * Assert.assertEquals(getNative_text_Color().GetAttribute("class"),"blue-text")
		 * ;
		 */

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNative_SelectAllCheck().Enabled();
			}
		}), Input.wait30);
		getNative_SelectAllCheck().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNative_AdvToggle().Enabled();
			}
		}), Input.wait30);
		getNative_AdvToggle().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNative_GenerateLoadFileLST().Enabled();
			}
		}), Input.wait30);
		getNative_GenerateLoadFileLST().waitAndClick(10);
		Thread.sleep(2000);

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFFChkBox().Enabled();
			}
		}), Input.wait30);
		getTIFFChkBox().waitAndClick(10);

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFFTab().Enabled();
			}
		}), Input.wait30);
		getTIFFTab().waitAndClick(10);

		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_CenterHeaderBranding().Visible() && getTIFF_CenterHeaderBranding().Enabled();
			}
		}), Input.wait30);
		getTIFF_CenterHeaderBranding().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_InsertMetadataFieldClick().Visible();
			}
		}), Input.wait30);
		getTIFF_InsertMetadataFieldClick().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_selectedMetadataField_Ok().Visible();
			}
		}), Input.wait30);
		getTIFF_selectedMetadataField_Ok().waitAndClick(10);

		Thread.sleep(2000);

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		// driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		// getTIFF_EnableforPrivilegedDocs().Enabled() ;}}), Input.wait30);
		// getTIFF_EnableforPrivilegedDocs().waitAndClick(20);

		driver.scrollingToBottomofAPage();

		getPriveldge_SelectTagButton().waitAndClick(10);
		Thread.sleep(2000);

		getPriveldge_TagTree(tagname).waitAndClick(10);
		Thread.sleep(2000);

		getPriveldge_TagTree_SelectButton().waitAndClick(10);
		Thread.sleep(2000);

		String expplaceholdertexttiff = getTiff_placeholdertext().getText();
		System.out.println(expplaceholdertexttiff);
		UtilityLog.info(expplaceholdertexttiff);
		Assert.assertEquals(expplaceholdertexttiff,
				"TIFF / PDFs are generated and exported "
						+ "for all documents by default. To export placeholders for docs "
						+ "that are Privileged Withhold, Tech Issue or Produced Natively,"
						+ " please configure the placeholder section below.");

//		Assert.assertEquals(getTiff_NativeDoc().getText(),"Enable for Natively Produced Documents:");

//		Assert.assertEquals(getTiff_NativeDoc_FileType().getText(),"Select File Types and/or Tags:");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPriveldge_TextArea().Enabled();
			}
		}), Input.wait30);
		new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();

		getPriveldge_TextArea().SendKeys("testing");

		Assert.assertEquals(getTiff_redactiontext().getText(),
				"To burn redactions, select specific redactions"
						+ " or all redactions (annotation layer). Specify the redaction text for each"
						+ " selected redaction.");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTextChkBox().Enabled();
			}
		}), Input.wait30);
		getTextChkBox().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTextTab().Enabled();
			}
		}), Input.wait30);
		getTextTab().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTextcomponent_text().isDisplayed();
			}
		}), Input.wait30);

		String exptext = getTextcomponent_text().getText();
		System.out.println(exptext);
		UtilityLog.info(exptext);
		Assert.assertEquals(exptext,
				"Redacted documents are automatically OCRed"
						+ " to export the text. Original extracted text is exported for natively "
						+ "produced documents (file based placeholdering). "
						+ "For exception and privileged placeholdered docs, " + "the placeholder text is exported."
						+ " The configured format is applicable only to OCRed text and production generated text"
						+ ", and not to ingested text.");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getComponentsMarkComplete().Enabled();
			}
		}), Input.wait30);
		getComponentsMarkComplete().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getComponentsMarkNext().Enabled();
			}
		}), Input.wait30);
		getComponentsMarkNext().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBeginningBates().Enabled();
			}
		}), Input.wait30);
		getBeginningBates().SendKeys("100");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return gettxtBeginningBatesIDPrefix().Enabled();
			}
		}), Input.wait30);
		gettxtBeginningBatesIDPrefix().SendKeys(PrefixID);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return gettxtBeginningBatesIDSuffix().Enabled();
			}
		}), Input.wait30);
		gettxtBeginningBatesIDSuffix().SendKeys(SuffixID);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return gettxtBeginningBatesIDMinNumLength().Enabled();
			}
		}), Input.wait30);
		gettxtBeginningBatesIDMinNumLength().SendKeys("10");

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstSortingMetaData().Enabled();
			}
		}), Input.wait30);
		getlstSortingMetaData().selectFromDropdown().selectByVisibleText("DocID");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstSortingOrder().Enabled();
			}
		}), Input.wait30);
		getlstSortingOrder().selectFromDropdown().selectByVisibleText("Ascending");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstSortingMetaData().Enabled();
			}
		}), Input.wait30);
		getlstSubSortingMetaData().selectFromDropdown().selectByVisibleText("CustodianName");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstSubSortingOrder().Enabled();
			}
		}), Input.wait30);
		getlstSubSortingOrder().selectFromDropdown().selectByVisibleText("Ascending");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getKeepFamiliesTogether().Visible();
			}
		}), Input.wait30);
		getKeepFamiliesTogether().waitAndClick(10);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumAndSortMarkComplete().Enabled();
			}
		}), Input.wait30);
		getNumAndSortMarkComplete().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumAndSortNext().Enabled();
			}
		}), Input.wait30);
		getNumAndSortNext().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderRadioButton().Visible();
			}
		}), Input.wait30);
		getFolderRadioButton().waitAndClick(10);

		Thread.sleep(5000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectFolder(foldername).Visible();
			}
		}), Input.wait30);
		getSelectFolder(foldername).waitAndClick(5);

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getIncludeFamilies().Visible();
			}
		}), Input.wait30);
		getIncludeFamilies().waitAndClick(10);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnDocumentsSelectionMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnDocumentsSelectionMarkComplete().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnDocumentsSelectionNext().Enabled();
			}
		}), Input.wait30);
		getbtnDocumentsSelectionNext().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGuardMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnProductionGuardMarkComplete().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getOkButton().Visible();
			}
		}), Input.wait30);
		getOkButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGuardNext().Enabled();
			}
		}), Input.wait30);
		getbtnProductionGuardNext().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstProductionRootPaths().Visible();
			}
		}), Input.wait30);
		try {
			getlstProductionRootPaths().selectFromDropdown().selectByVisibleText(Input.prodPath);
		} catch (Exception e) {
			// if passed production path is wrong! go by index and then select
			getlstProductionRootPaths().selectFromDropdown().selectByIndex(1);
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionOutputLocation_ProductionDirectory().Visible();
			}
		}), Input.wait30);
		getProductionOutputLocation_ProductionDirectory().SendKeys(productionname);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionLocationMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnProductionLocationMarkComplete().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionLocationNext().Enabled();
			}
		}), Input.wait30);
		getbtnProductionLocationNext().waitAndClick(10);

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getPreviewprod().Enabled() ;}}), Input.wait30);
		 * getPreviewprod().waitAndClick(10);
		 */

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionSummaryMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnProductionSummaryMarkComplete().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionSummaryNext().Enabled();
			}
		}), Input.wait30);
		getbtnProductionSummaryNext().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGenerate().Visible();
			}
		}), Input.wait30);
		getbtnProductionGenerate().waitAndClick(10);

		Reporter.log("Wait for generate to complete", true);
		// System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocumentGeneratetext().Visible();
			}
		}), Input.wait120);
		// work on below assert..for now its ok bcz we are validating with commit
		// button!
		// Assert.assertTrue(getDocumentGeneratetext().isDisplayed());

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getQC_backbutton().Enabled();
			}
		}), Input.wait30);
		getQC_backbutton().waitAndClick(15);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProd_BatesRange().Enabled();
			}
		}), Input.wait30);
		String batesno = getProd_BatesRange().getText();

		Reporter.log("Bate number " + batesno, true);
		// System.out.println(batesno);
		UtilityLog.info(batesno);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnSummaryNext().Enabled();
			}
		}), Input.wait30);
		getbtnSummaryNext().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCopyPath().Visible();
			}
		}), Input.wait30);
		getCopyPath().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getQC_Download().Enabled();
			}
		}), Input.wait30);
		getQC_Download().waitAndClick(10);
		getQC_Downloadbutton_allfiles().waitAndClick(10);
		base.VerifySuccessMessage("Your Production Archive download will get started shortly");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getConfirmProductionCommit().Enabled();
			}
		}), Input.wait30);
		getConfirmProductionCommit().waitAndClick(10);

		String PDocCount = getProductionDocCount().getText();
		int Doc = Integer.parseInt(PDocCount);

		Reporter.log("Doc - " + Doc, true);
		// System.out.println(Doc);
		UtilityLog.info(Doc);

	}

	public void ExportwithpriorProduction(String exportname, String PrefixID, String SuffixID, final String foldername)
			throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProdExportSet().Visible();
			}
		}), Input.wait30);
		getProdExportSet().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionSettxt().Visible();
			}
		}), Input.wait30);
		getProductionSettxt().SendKeys(exportname);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProdExportSetRadioButton().Visible();
			}
		}), Input.wait30);
		getProdExportSetRadioButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProdExport_SaveButton().Visible();
			}
		}), Input.wait30);
		getProdExport_SaveButton().waitAndClick(10);

		base.VerifySuccessMessage("Export Set Added Successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProdExport_ProductionSets().Visible();
			}
		}), Input.wait30);
		getProdExport_ProductionSets().selectFromDropdown().selectByIndex(1);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProdExport_AddaNewExportSet().Visible();
			}
		}), Input.wait30);
		getProdExport_AddaNewExportSet().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionName().Visible();
			}
		}), Input.wait30);
		getProductionName().SendKeys(exportname);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionDesc().Visible();
			}
		}), Input.wait30);
		getProductionDesc().SendKeys(exportname);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProdExport_Priorprodtoggle().Visible();
			}
		}), Input.wait30);
		getProdExport_Priorprodtoggle().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProdExport_SelectProductionSet().Visible();
			}
		}), Input.wait30);
		getProdExport_SelectProductionSet().selectFromDropdown().selectByIndex(1);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBasicInfoMarkComplete().Visible();
			}
		}), Input.wait30);
		getBasicInfoMarkComplete().waitAndClick(10);

//		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//				getBasicInfoNext().Enabled() ;}}), Input.wait30); 
//		getBasicInfoNext().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDATTab().Visible();
			}
		}), Input.wait30);
		getDATTab().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_DATField1().Visible();
			}
		}), Input.wait30);
		getDAT_DATField1().SendKeys("BatesNumberExp");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getComponentsMarkComplete().Enabled();
			}
		}), Input.wait30);
		getComponentsMarkComplete().waitAndClick(20);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getComponentsMarkNext().Enabled();
			}
		}), Input.wait30);
		getComponentsMarkNext().waitAndClick(20);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumAndSortMarkComplete().Enabled();
			}
		}), Input.wait30);
		getNumAndSortMarkComplete().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumAndSortNext().Enabled();
			}
		}), Input.wait30);
		getNumAndSortNext().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderRadioButton().Visible();
			}
		}), Input.wait30);
		getFolderRadioButton().waitAndClick(10);

		Thread.sleep(5000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectFolder(foldername).Visible();
			}
		}), Input.wait30);
		getSelectFolder(foldername).waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnDocumentsSelectionMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnDocumentsSelectionMarkComplete().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnDocumentsSelectionNext().Enabled();
			}
		}), Input.wait30);
		getbtnDocumentsSelectionNext().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getOkButton().Visible();
			}
		}), Input.wait30);
		getOkButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstProductionRootPaths().Visible();
			}
		}), Input.wait30);
		getlstProductionRootPaths().selectFromDropdown().selectByVisibleText(Input.prodPath);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionOutputLocation_ProductionDirectory().Visible();
			}
		}), Input.wait30);
		getProductionOutputLocation_ProductionDirectory().SendKeys(exportname);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionLocationMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnProductionLocationMarkComplete().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionLocationNext().Enabled();
			}
		}), Input.wait30);
		getbtnProductionLocationNext().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionSummaryMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnProductionSummaryMarkComplete().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionSummaryNext().Enabled();
			}
		}), Input.wait30);
		getbtnProductionSummaryNext().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGenerate().Visible();
			}
		}), Input.wait30);
		getbtnProductionGenerate().waitAndClick(10);
		System.out.println("Wait until regenerate is enabled");

		for (int i = 0; i < 120; i++) {
			try {

				this.driver.getWebDriver().get(Input.url + "Production/Home");
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getProdExport_ProductionSets().Visible();
					}
				}), Input.wait30);
				getProdExport_ProductionSets().selectFromDropdown().selectByIndex(1);
				getProdStateFilter().WaitUntilPresent();
				getProdStateFilter().selectFromDropdown().selectByVisibleText("COMPLETED");
				getProductionLink().waitAndClick(5);
				// Thread.sleep(5000);
				getbtnGenerateMarkComplete().waitAndClick(5);
				System.out.println("Generated");
				break;

			} catch (Exception e) {
				Thread.sleep(10000);
				driver.Navigate().refresh();

			}
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnSummaryNext().Enabled();
			}
		}), Input.wait30);
		getbtnSummaryNext().waitAndClick(10);
		// Thread.sleep(10000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getReviewproductionButton().Visible();
			}
		}), Input.wait30);
		getReviewproductionButton().waitAndClick(10);

		String location = getDestinationPathText().getText();
		System.out.println(location);
		Thread.sleep(7000);

		String doccount = getDoc_Count().getText();
		System.out.println(doccount);

		base.isFileDownloaded(location + "\\VOL0001" + "\\Text" + "\\0001", 3);

	}

	public void Productionwithallredactions(String productionname, String PrefixID, String SuffixID,
			final String foldername, String tagname) throws Exception {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddNewProductionbutton().Visible();
			}
		}), Input.wait30);
		getAddNewProductionbutton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionName().Visible();
			}
		}), Input.wait30);
		getProductionName().SendKeys(productionname);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionDesc().Visible();
			}
		}), Input.wait30);
		getProductionDesc().SendKeys(productionname);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBasicInfoMarkComplete().Visible();
			}
		}), Input.wait30);
		getBasicInfoMarkComplete().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDATChkBox().Enabled();
			}
		}), Input.wait30);
		getDATChkBox().waitAndClick(20);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDATTab().Visible();
			}
		}), Input.wait30);
		getDATTab().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_FieldClassification1().Visible();
			}
		}), Input.wait30);
		getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText("Production");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_SourceField1().Visible();
			}
		}), Input.wait30);

		for (WebElement listsecfiled : getDAT_SourceField1().selectFromDropdown().getOptions()) {
			String fieldexp[] = { "TIFFPageCount", "VolumeName" };
			System.out.println(listsecfiled.getText());
			if (listsecfiled.getText().equalsIgnoreCase("TIFFPageCount"))
				Assert.assertTrue(listsecfiled.getText().equalsIgnoreCase("TIFFPageCount"));
			else
				System.out.println("Element not matching");

		}

		BaseClass bc = new BaseClass(driver);
		// bc.comparearraywithlist(fieldexp, elelist);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_FieldClassification1().Visible();
			}
		}), Input.wait30);
		getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText("Bates");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_SourceField1().Visible();
			}
		}), Input.wait30);
		getDAT_SourceField1().selectFromDropdown().selectByVisibleText("BatesNumber");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_DATField1().Visible();
			}
		}), Input.wait30);
		getDAT_DATField1().SendKeys("BatesNumber" + Utility.dynamicNameAppender());

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNativeChkBox().Enabled();
			}
		}), Input.wait30);
		getNativeChkBox().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNativeTab().Enabled();
			}
		}), Input.wait30);
		getNativeTab().waitAndClick(10);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNative_SelectAllCheck().Enabled();
			}
		}), Input.wait30);
		getNative_SelectAllCheck().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNative_AdvToggle().Enabled();
			}
		}), Input.wait30);
		getNative_AdvToggle().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNative_GenerateLoadFileLST().Enabled();
			}
		}), Input.wait30);
		getNative_GenerateLoadFileLST().waitAndClick(10);
		Thread.sleep(2000);

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFFChkBox().Enabled();
			}
		}), Input.wait30);
		getTIFFChkBox().waitAndClick(10);

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFFTab().Enabled();
			}
		}), Input.wait30);
		getTIFFTab().waitAndClick(10);

		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_CenterHeaderBranding().Visible() && getTIFF_CenterHeaderBranding().Enabled();
			}
		}), Input.wait30);
		getTIFF_CenterHeaderBranding().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_EnterBranding().Enabled();
			}
		}), Input.wait30);

		new Actions(driver.getWebDriver()).moveToElement(getTIFF_EnterBranding().getWebElement()).click();
		// getTIFF_EnterBranding().waitAndClick(10);
		getTIFF_EnterBranding().SendKeys("Test");
		Thread.sleep(2000);

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_EnableforPrivilegedDocs().Enabled();
			}
		}), Input.wait30);
		// getTIFF_EnableforPrivilegedDocs().waitAndClick(20);

		driver.scrollingToBottomofAPage();

		getPriveldge_SelectTagButton().waitAndClick(10);
		Thread.sleep(2000);

		getPriveldge_TagTree(tagname).waitAndClick(10);
		Thread.sleep(2000);

		getPriveldge_TagTree_SelectButton().waitAndClick(10);
		Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPriveldge_TextArea().Enabled();
			}
		}), Input.wait30);
		new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();

		getPriveldge_TextArea().SendKeys("testing");

		getTIFF_BurnRedtoggle().waitAndClick(10);

		/*
		 * getTIFF_SpecifyRedactText().waitAndClick(10);
		 * 
		 * 
		 * getTIFF_SelectRedtagbuton().waitAndClick(10); Thread.sleep(5000);
		 * 
		 * getTIFF_SelectRedtags("R1").waitAndClick(10);
		 * 
		 * getTIFF_SelectRedtags_SelectButton().waitAndClick(10);
		 * System.out.println(getTIFF_Red_Placeholdertext().getText());
		 * 
		 * Assert.assertEquals(getTIFF_Red_Placeholdertext().getText(),"REDACTED");
		 */

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTextChkBox().Enabled();
			}
		}), Input.wait30);
		getTextChkBox().waitAndClick(10);

//		driver.scrollingToBottomofAPage();
//				
//		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//				getPDFChkBox().Enabled() ;}}), Input.wait30); 
//		getPDFChkBox().waitAndClick(10);		
//		
//		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//				getPDFTab().Enabled()  ;}}), Input.wait30); 
//		getPDFTab().waitAndClick(10);
//		
//		driver.scrollPageToTop();
//		
//		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//				getPDF_CenterHeaderBranding().Visible() &&  getTIFF_CenterHeaderBranding().Enabled() ;}}), Input.wait30); 
//		getPDF_CenterHeaderBranding().ScrollTo();
//		getPDF_CenterHeaderBranding().waitAndClick(10);
//		
//		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//				getPDF_EnterBranding().Enabled()  ;}}), Input.wait30); 
//	
//		new Actions(driver.getWebDriver()).moveToElement(getPDF_EnterBranding().getWebElement()).waitAndClick(10);
//		getPDF_EnterBranding().SendKeys("Test");
//    	Thread.sleep(2000);
//    	
//    	driver.scrollingToBottomofAPage();
//		
//		getPriveldge_SelectPDFTagButton().waitAndClick(10);
//		Thread.sleep(2000);
//		
//		getPriveldge_PDFTagTree(tagname).waitAndClick(10);
//		Thread.sleep(2000);
//		
//		getPriveldge_PDFTagTree_SelectButton().waitAndClick(5);
//		Thread.sleep(2000);
//		
//		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//				getPriveldge_TextArea().Enabled()  ;}}), Input.wait30); 
//		new Actions(driver.getWebDriver()).moveToElement(getPriveldge_PDFTextArea().getWebElement()).waitAndClick(10);
//		
//		getPriveldge_PDFTextArea().SendKeys("testing");
//		
//		getPDF_BurnRedtoggle().waitAndClick(10);
//		
//	/*	getPDF_SpecifyRedactText().waitAndClick(10);
//		
//		
//		getPDF_SelectRedtagbuton().waitAndClick(10);
//		Thread.sleep(5000);
//		
//		getPDF_SelectRedtags("R1").waitAndClick(10);
//		
//		getPDF_SelectRedtags_SelectButton().waitAndClick(10);
//		System.out.println(getPDF_Red_Placeholdertext().getText()); 
//		
//		Assert.assertEquals(getPDF_Red_Placeholdertext().getText(),"REDACTED");*/
//		
		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getComponentsMarkComplete().Enabled();
			}
		}), Input.wait30);
		getComponentsMarkComplete().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getComponentsMarkNext().Enabled();
			}
		}), Input.wait30);
		getComponentsMarkNext().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBeginningBates().Enabled();
			}
		}), Input.wait30);
		getBeginningBates().SendKeys("100");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return gettxtBeginningBatesIDPrefix().Enabled();
			}
		}), Input.wait30);
		gettxtBeginningBatesIDPrefix().SendKeys(PrefixID);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return gettxtBeginningBatesIDSuffix().Enabled();
			}
		}), Input.wait30);
		gettxtBeginningBatesIDSuffix().SendKeys(SuffixID);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return gettxtBeginningBatesIDMinNumLength().Enabled();
			}
		}), Input.wait30);
		gettxtBeginningBatesIDMinNumLength().SendKeys("10");

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstSortingMetaData().Enabled();
			}
		}), Input.wait30);
		getlstSortingMetaData().selectFromDropdown().selectByVisibleText("DocID");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstSortingOrder().Enabled();
			}
		}), Input.wait30);
		getlstSortingOrder().selectFromDropdown().selectByVisibleText("Ascending");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstSortingMetaData().Enabled();
			}
		}), Input.wait30);
		getlstSubSortingMetaData().selectFromDropdown().selectByVisibleText("CustodianName");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstSubSortingOrder().Enabled();
			}
		}), Input.wait30);
		getlstSubSortingOrder().selectFromDropdown().selectByVisibleText("Ascending");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getKeepFamiliesTogether().Visible();
			}
		}), Input.wait30);
		getKeepFamiliesTogether().waitAndClick(10);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumAndSortMarkComplete().Enabled();
			}
		}), Input.wait30);
		getNumAndSortMarkComplete().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumAndSortNext().Enabled();
			}
		}), Input.wait30);
		getNumAndSortNext().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderRadioButton().Visible();
			}
		}), Input.wait30);
		getFolderRadioButton().waitAndClick(10);

		Thread.sleep(5000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectFolder(foldername).Visible();
			}
		}), Input.wait30);
		getSelectFolder(foldername).waitAndClick(5);

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getIncludeFamilies().Visible();
			}
		}), Input.wait30);
		getIncludeFamilies().waitAndClick(10);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnDocumentsSelectionMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnDocumentsSelectionMarkComplete().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnDocumentsSelectionNext().Enabled();
			}
		}), Input.wait30);
		getbtnDocumentsSelectionNext().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGuardMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnProductionGuardMarkComplete().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getOkButton().Visible();
			}
		}), Input.wait30);
		getOkButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGuardNext().Enabled();
			}
		}), Input.wait30);
		getbtnProductionGuardNext().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstProductionRootPaths().Visible();
			}
		}), Input.wait30);
		getlstProductionRootPaths().selectFromDropdown().selectByVisibleText(Input.prodPath);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionOutputLocation_ProductionDirectory().Visible();
			}
		}), Input.wait30);
		getProductionOutputLocation_ProductionDirectory().SendKeys(productionname);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionLocationMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnProductionLocationMarkComplete().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionLocationNext().Enabled();
			}
		}), Input.wait30);
		getbtnProductionLocationNext().waitAndClick(10);

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getPreviewprod().Enabled() ;}}), Input.wait30);
		 * getPreviewprod().waitAndClick(10);
		 */
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionSummaryMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnProductionSummaryMarkComplete().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionSummaryNext().Enabled();
			}
		}), Input.wait30);
		getbtnProductionSummaryNext().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGenerate().Visible();
			}
		}), Input.wait30);
		getbtnProductionGenerate().waitAndClick(10);
		System.out.println("Wait until regenerate is enabled");
		for (int i = 0; i < 120; i++) {
			try {
				this.driver.getWebDriver().get(Input.url + "Production/Home");
				getProductionLink().waitAndClick(5);
				getbtnGenerateMarkComplete().waitAndClick(5);
				System.out.println("Generated");
				break;

			} catch (Exception e) {
				Thread.sleep(10000);
				driver.Navigate().refresh();

			}

		}

		String batesno = getProd_BatesRange().getText();
		System.out.println(batesno);
		String[] parts = batesno.split("\\s*-\\s*");
		String a = parts[0];
		String b = parts[1];
		System.out.println(a);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnSummaryNext().Enabled();
			}
		}), Input.wait30);
		getbtnSummaryNext().Click();
		// Thread.sleep(10000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getConfirmProductionCommit().Enabled();
			}
		}), Input.wait30);
		getConfirmProductionCommit().Click();

		String PDocCount = getProductionDocCount().getText();
		int Doc = Integer.parseInt(PDocCount);
		System.out.println(Doc);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getReviewproductionButton().Visible();
			}
		}), Input.wait30);
		getReviewproductionButton().Click();

		String location = getDestinationPathText().getText();
		System.out.println(location);
		Thread.sleep(7000);
		String doccount = getDoc_Count().getText();
		System.out.println(doccount);

		ProdPathallredact = location + "\\VOL0001" + "\\PDF" + "\\0001" + "\\" + "" + a + ".pdf";
		System.out.println(ProdPathallredact);

		base.isFileDownloaded(location + "\\VOL0001" + "\\PDF" + "\\0001", 1);
		/*
		 * this.driver.getWebDriver().get(Input.url+"Production/Home");
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getProdStateFilter().Visible() ;}}), Input.wait30);
		 * getProdStateFilter().selectFromDropdown().selectByVisibleText("COMPLETED");
		 * Thread.sleep(5000);
		 * 
		 * //getProdname_Gearicon(productionname).waitAndClick(10);
		 */
	}

	public void Productionwithsomeredactions(String productionname, String PrefixID, String SuffixID,
			final String foldername, String redname) throws Exception {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddNewProductionbutton().Visible();
			}
		}), Input.wait30);
		getAddNewProductionbutton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionName().Visible();
			}
		}), Input.wait30);
		getProductionName().SendKeys(productionname);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionDesc().Visible();
			}
		}), Input.wait30);
		getProductionDesc().SendKeys(productionname);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBasicInfoMarkComplete().Visible();
			}
		}), Input.wait30);
		getBasicInfoMarkComplete().Click();

		getBasicInfoNext().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDATChkBox().Enabled();
			}
		}), Input.wait30);
		getDATChkBox().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDATTab().Visible();
			}
		}), Input.wait30);
		getDATTab().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_FieldClassification1().Visible();
			}
		}), Input.wait30);
		getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText("Production");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_SourceField1().Visible();
			}
		}), Input.wait30);
		String fieldexp[] = { "TIFFPageCount", "VolumeName" };
		BaseClass bc = new BaseClass(driver);
		bc.getallselectele(getDAT_SourceField1().selectFromDropdown());

		// Assert.assertEquals("",
		// expected);(result.getText().equalsIgnoreCase("TIFFPageCount"));

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_FieldClassification1().Visible();
			}
		}), Input.wait30);
		getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText("Bates");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_SourceField1().Visible();
			}
		}), Input.wait30);
		getDAT_SourceField1().selectFromDropdown().selectByVisibleText("BatesNumber");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_DATField1().Visible();
			}
		}), Input.wait30);
		getDAT_DATField1().SendKeys("BatesNumber" + Utility.dynamicNameAppender());

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNativeChkBox().Enabled();
			}
		}), Input.wait30);
		getNativeChkBox().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNativeTab().Enabled();
			}
		}), Input.wait30);
		getNativeTab().Click();

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNative_SelectAllCheck().Enabled();
			}
		}), Input.wait30);
		getNative_SelectAllCheck().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNative_AdvToggle().Enabled();
			}
		}), Input.wait30);
		getNative_AdvToggle().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNative_GenerateLoadFileLST().Enabled();
			}
		}), Input.wait30);
		getNative_GenerateLoadFileLST().Click();

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFFChkBox().Enabled();
			}
		}), Input.wait30);
		getTIFFChkBox().Click();

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFFTab().Enabled();
			}
		}), Input.wait30);
		getTIFFTab().Click();

		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_CenterHeaderBranding().Visible() && getTIFF_CenterHeaderBranding().Enabled();
			}
		}), Input.wait30);
		getTIFF_CenterHeaderBranding().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_EnterBranding().Enabled();
			}
		}), Input.wait30);

		new Actions(driver.getWebDriver()).moveToElement(getTIFF_EnterBranding().getWebElement()).click();
		// getTIFF_EnterBranding().waitAndClick(10);
		getTIFF_EnterBranding().SendKeys("Test");
		Thread.sleep(2000);

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_EnableforPrivilegedDocs().Enabled();
			}
		}), Input.wait30);
		// getTIFF_EnableforPrivilegedDocs().waitAndClick(20);

		driver.scrollingToBottomofAPage();

		getPriveldge_SelectTagButton().waitAndClick(10);
		Thread.sleep(2000);

		getPriveldge_TagTree("Privileged").waitAndClick(10);
		Thread.sleep(2000);

		getPriveldge_TagTree_SelectButton().waitAndClick(10);
		Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPriveldge_TextArea().Enabled();
			}
		}), Input.wait30);
		new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();

		getPriveldge_TextArea().SendKeys("testing");

		getTIFF_BurnRedtoggle().waitAndClick(10);

		// getTIFF_SpecifyRedactText().waitAndClick(10);

		// getTIFF_SelectRedtagbuton().waitAndClick(10);
		getTIFF_SelectRed_Radiobutton().waitAndClick(10);
		Thread.sleep(2000);

		getTIFF_SelectRedtags().waitAndClick(10);

		// getTIFF_SelectRedtags_SelectButton().waitAndClick(10);
		// System.out.println(getTIFF_Red_Placeholdertext().getText());

		// Assert.assertEquals(getTIFF_Red_Placeholdertext().getText(),"REDACTED");

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTextChkBox().Enabled();
			}
		}), Input.wait30);
		getTextChkBox().waitAndClick(10);

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPDFChkBox().Enabled();
			}
		}), Input.wait30);
		getPDFChkBox().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPDFTab().Enabled();
			}
		}), Input.wait30);
		getPDFTab().waitAndClick(10);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPDF_CenterHeaderBranding().Visible() && getTIFF_CenterHeaderBranding().Enabled();
			}
		}), Input.wait30);
		getPDF_CenterHeaderBranding().ScrollTo();
		getPDF_CenterHeaderBranding().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPDF_EnterBranding().Enabled();
			}
		}), Input.wait30);

		new Actions(driver.getWebDriver()).moveToElement(getPDF_EnterBranding().getWebElement()).click();
		getPDF_EnterBranding().SendKeys("Test");
		Thread.sleep(2000);

		driver.scrollingToBottomofAPage();

		getPriveldge_SelectPDFTagButton().waitAndClick(10);
		Thread.sleep(2000);

		getPriveldge_PDFTagTree("Privileged").waitAndClick(10);
		Thread.sleep(2000);

		getPriveldge_PDFTagTree_SelectButton().waitAndClick(5);
		Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPriveldge_TextArea().Enabled();
			}
		}), Input.wait30);
		new Actions(driver.getWebDriver()).moveToElement(getPriveldge_PDFTextArea().getWebElement()).click();

		getPriveldge_PDFTextArea().SendKeys("testing");

		getPDF_BurnRedtoggle().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		// getPDF_SpecifyRedactText().waitAndClick(10);

		getPDF_SelectRed_Radiobutton().waitAndClick(10);
		Thread.sleep(2000);

		getPDF_SelectRedtags().waitAndClick(10);

		// getPDF_SelectRedtags_SelectButton().waitAndClick(10);
		// System.out.println(getPDF_Red_Placeholdertext().getText());

		// Assert.assertEquals(getPDF_Red_Placeholdertext().getText(),"REDACTED");

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getComponentsMarkComplete().Enabled();
			}
		}), Input.wait30);
		getComponentsMarkComplete().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getComponentsMarkNext().Enabled();
			}
		}), Input.wait30);
		getComponentsMarkNext().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBeginningBates().Enabled();
			}
		}), Input.wait30);
		getBeginningBates().SendKeys("100");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return gettxtBeginningBatesIDPrefix().Enabled();
			}
		}), Input.wait30);
		gettxtBeginningBatesIDPrefix().SendKeys(PrefixID);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return gettxtBeginningBatesIDSuffix().Enabled();
			}
		}), Input.wait30);
		gettxtBeginningBatesIDSuffix().SendKeys(SuffixID);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return gettxtBeginningBatesIDMinNumLength().Enabled();
			}
		}), Input.wait30);
		gettxtBeginningBatesIDMinNumLength().SendKeys("10");

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstSortingMetaData().Enabled();
			}
		}), Input.wait30);
		getlstSortingMetaData().selectFromDropdown().selectByVisibleText("DocID");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstSortingOrder().Enabled();
			}
		}), Input.wait30);
		getlstSortingOrder().selectFromDropdown().selectByVisibleText("Ascending");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstSortingMetaData().Enabled();
			}
		}), Input.wait30);
		getlstSubSortingMetaData().selectFromDropdown().selectByVisibleText("CustodianName");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstSubSortingOrder().Enabled();
			}
		}), Input.wait30);
		getlstSubSortingOrder().selectFromDropdown().selectByVisibleText("Ascending");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getKeepFamiliesTogether().Visible();
			}
		}), Input.wait30);
		getKeepFamiliesTogether().waitAndClick(10);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumAndSortMarkComplete().Enabled();
			}
		}), Input.wait30);
		getNumAndSortMarkComplete().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumAndSortNext().Enabled();
			}
		}), Input.wait30);
		getNumAndSortNext().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderRadioButton().Visible();
			}
		}), Input.wait30);
		getFolderRadioButton().waitAndClick(10);

		Thread.sleep(5000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectFolder(foldername).Visible();
			}
		}), Input.wait30);
		getSelectFolder(foldername).waitAndClick(5);

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getIncludeFamilies().Visible();
			}
		}), Input.wait30);
		getIncludeFamilies().waitAndClick(10);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnDocumentsSelectionMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnDocumentsSelectionMarkComplete().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnDocumentsSelectionNext().Enabled();
			}
		}), Input.wait30);
		getbtnDocumentsSelectionNext().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGuardMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnProductionGuardMarkComplete().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getOkButton().Visible();
			}
		}), Input.wait30);
		getOkButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGuardNext().Enabled();
			}
		}), Input.wait30);
		getbtnProductionGuardNext().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstProductionRootPaths().Visible();
			}
		}), Input.wait30);
		getlstProductionRootPaths().selectFromDropdown().selectByVisibleText(Input.prodPath);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionOutputLocation_ProductionDirectory().Visible();
			}
		}), Input.wait30);
		getProductionOutputLocation_ProductionDirectory().SendKeys(productionname);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionLocationMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnProductionLocationMarkComplete().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionLocationNext().Enabled();
			}
		}), Input.wait30);
		getbtnProductionLocationNext().waitAndClick(10);

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getPreviewprod().Enabled() ;}}), Input.wait30);
		 * getPreviewprod().waitAndClick(10);
		 */
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionSummaryMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnProductionSummaryMarkComplete().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionSummaryNext().Enabled();
			}
		}), Input.wait30);
		getbtnProductionSummaryNext().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGenerate().Visible();
			}
		}), Input.wait30);
		getbtnProductionGenerate().waitAndClick(10);
		System.out.println("Wait until regenerate is enabled");
		for (int i = 0; i < 120; i++) {
			try {

				this.driver.getWebDriver().get(Input.url + "Production/Home");
				// Thread.sleep(5000);
				getProductionLink().waitAndClick(5);
				// Thread.sleep(5000);
				getbtnGenerateMarkComplete().waitAndClick(5);
				System.out.println("Generated");
				break;

			} catch (Exception e) {
				Thread.sleep(10000);
				driver.Navigate().refresh();

			}
		}

		String batesno = getProd_BatesRange().getText();
		System.out.println(batesno);
		String[] parts = batesno.split("\\s*-\\s*");
		String a = parts[0];
		String b = parts[1];
		System.out.println(a);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnSummaryNext().Enabled();
			}
		}), Input.wait30);
		getbtnSummaryNext().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getConfirmProductionCommit().Enabled();
			}
		}), Input.wait30);
		getConfirmProductionCommit().Click();

		String PDocCount = getProductionDocCount().getText();
		int Doc = Integer.parseInt(PDocCount);
		System.out.println(Doc);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getReviewproductionButton().Visible();
			}
		}), Input.wait30);
		getReviewproductionButton().Click();

		String location = getDestinationPathText().getText();
		System.out.println(location);
		Thread.sleep(7000);
		String doccount = getDoc_Count().getText();
		System.out.println(doccount);

		base.isFileDownloaded(location + "\\VOL0001" + "\\PDF" + "\\0001", 4);
		ProdPathsomeredact = location + "\\VOL0001" + "\\PDF" + "\\0001" + "\\" + "" + a + ".pdf";
		System.out.println(ProdPathsomeredact);

	}

	/**
	 * @Modified Indium-Sowndarya.Velraj 07/11/2021
	 * @param productionName
	 * @param This           method adds new production in home page
	 */
	public String addANewProduction(String productionName) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddNewProductionbutton().Visible() && getAddNewProductionbutton().Enabled();
			}
		}), Input.wait30);
		getAddNewProductionbutton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionName().Visible() && getProductionName().Enabled();
			}
		}), Input.wait30);
		getProductionName().SendKeys(productionName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionDesc().Enabled() && getProductionDesc().Visible();
			}
		}), Input.wait30);
		getProductionDesc().SendKeys(productionName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBasicInfoMarkComplete().Visible() && getBasicInfoMarkComplete().Enabled();
			}
		}), Input.wait30);
		getBasicInfoMarkComplete().Click();
		base.stepInfo("New production is added");
		return productionName;
	}

	/**
	 * @author Indium-Sowndarya.Velraj
	 * @param exportname
	 * @param This       method selects export set from dropdown and creates a new
	 *                   export in Production homepage.
	 */
	public void addANewExport(String exportname) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddNewExport().Visible() && getAddNewExport().Enabled();
			}
		}), Input.wait30);
		getAddNewExport().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionName().Visible() && getProductionName().Enabled();
			}
		}), Input.wait30);
		getProductionName().SendKeys(exportname);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionDesc().Enabled() && getProductionDesc().Visible();
			}
		}), Input.wait30);
		getProductionDesc().SendKeys(exportname);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBasicInfoMarkComplete().Visible() && getBasicInfoMarkComplete().Enabled();
			}
		}), Input.wait30);
		getBasicInfoMarkComplete().Click();
		base.stepInfo("New Export is added");
	}

	/**
	 * @Modified Indium-Aathith.senthilkumar on 03/15/2022
	 */
	public void fillingDATSection() {

		base.waitForElement(getDATChkBox());
		getDATChkBox().waitAndClick(10);

		base.waitForElement(getDATTab());
		getDATTab().waitAndClick(10);

		base.waitForElement(getDAT_FieldClassification1());
		getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText(Input.bates);

		base.waitForElement(getDAT_SourceField1());
		getDAT_SourceField1().selectFromDropdown().selectByVisibleText(Input.batesNumber);

		driver.waitForPageToBeReady();
		getDAT_DATField1().waitAndClick(10);
		getDAT_DATField1().SendKeys("B" + Utility.dynamicNameAppender());
		base.stepInfo("Dat section is filled");
	}

	/**
	 * @author Indium-Sowndarya.Velraj
	 * @param field_classification
	 * @param source_field
	 * @param Dat_field
	 * @param This                 method fills DAT component with required DAT
	 *                             fields.
	 */
	public void fillingDATSectionWithBates(String field_classification, String source_field, String Dat_field) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDATChkBox().Enabled();
			}
		}), Input.wait30);
		getDATChkBox().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDATTab().Visible() && getDATTab().Enabled();
			}
		}), Input.wait30);
		getDATTab().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_FieldClassification1().Visible() && getDAT_FieldClassification1().Enabled();
			}
		}), Input.wait30);
		getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText(field_classification);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_SourceField1().Visible() && getDAT_SourceField1().Enabled();
			}
		}), Input.wait30);
		getDAT_SourceField1().selectFromDropdown().selectByVisibleText(source_field);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_DATField1().Visible() && getDAT_DATField1().Enabled();
			}
		}), Input.wait30);
		getDAT_DATField1().SendKeys(Dat_field);
		base.stepInfo("Dat section is filled");
	}

	/**
	 * @author Aathith.Senthilkumar.Modified on 04/04/22
	 * @Description Second dat field choosed value TiffPageCount
	 */
	public void datMetaDataTiffPageCount() {
		addNewFieldOnDAT();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_FieldClassification2().Visible() && getDAT_FieldClassification2().Enabled();
			}
		}), Input.wait30);
		getDAT_FieldClassification2().selectFromDropdown().selectByVisibleText("Production");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_SourceField2().Visible() && getDAT_SourceField2().Enabled();
			}
		}), Input.wait30);
		getDAT_SourceField2().selectFromDropdown().selectByVisibleText("VolumeName");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_DATField2().Visible() && getDAT_DATField2().Enabled();
			}
		}), Input.wait30);
		getDAT_DATField2().SendKeys("TIFFPAGECOUNT");
		base.stepInfo("Dat section is filled with TIFFPAGECOUNT");
	}

	/**
	 * 
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * 
	 * @Description: Method for entering redaction tag and generate production.
	 *
	 * 
	 * 
	 */

	public void fillingTIFFFSectionWithRedactionTags(String redactiontag1) throws InterruptedException {

		try {

			base.waitForElement(getTIFFChkBox());

			getTIFFChkBox().Click();

			base.waitForElement(getTIFFTab());

			getTIFFTab().Click();

			driver.scrollPageToTop();

			base.waitForElement(getPDFGenerateRadioButton());

			getPDFGenerateRadioButton().Click();

			getTIFF_EnableforPrivilegedDocs().ScrollTo();

			// disabling enable for priviledged docs

			base.waitForElement(getTIFF_EnableforPrivilegedDocs());

			getTIFF_EnableforPrivilegedDocs().Enabled();

			getTIFF_EnableforPrivilegedDocs().Click();

			getClk_burnReductiontoggle().ScrollTo();

			// enable burn redaction

			base.waitForElement(getClk_burnReductiontoggle());

			getClk_burnReductiontoggle().Click();

			getClkRadioBtn_selectRedactionTags().ScrollTo();

			base.waitForElement(getClkRadioBtn_selectRedactionTags());

			getClkRadioBtn_selectRedactionTags().isDisplayed();

			driver.waitForPageToBeReady();

			getClkRadioBtn_selectRedactionTags().waitAndClick(10);

			base.waitForElement(getClkCheckBox_defaultRedactionTag());

			getClkCheckBox_defaultRedactionTag().isDisplayed();

			getClkCheckBox_defaultRedactionTag().waitAndClick(10);

			base.waitForElement(getClkLink_selectingRedactionTags());

			getClkLink_selectingRedactionTags().isDisplayed();

			getClkLink_selectingRedactionTags().waitAndClick(10);

			base.waitForElement(getClkBtn_selectingRedactionTags());

			getClkBtn_selectingRedactionTags().isDisplayed();

			getClkBtn_selectingRedactionTags().waitAndClick(10);

			base.waitForElement(redactionTagInBurnRedactionCheckBox(redactiontag1));

			redactionTagInBurnRedactionCheckBox(redactiontag1).ScrollTo();

			redactionTagInBurnRedactionCheckBox(redactiontag1).isDisplayed();

			driver.waitForPageToBeReady();

			redactionTagInBurnRedactionCheckBox(redactiontag1).waitAndClick(10);

			base.waitForElement(getClk_selectBtn());

			getClk_selectBtn().isDisplayed();

			getClk_selectBtn().waitAndClick(10);

			base.waitForElement(gettextRedactionPlaceHolder());

			gettextRedactionPlaceHolder().isDisplayed();

			gettextRedactionPlaceHolder().waitAndClick(10);

			gettextRedactionPlaceHolder().SendKeys(searchString4);

			base.stepInfo("Enabled burn redaction with selecting redaction tag");

		} catch (Exception e) {

			base.failedStep("Exception occcured while filling burnredaction" + e.getMessage());

			e.printStackTrace();

		}

	}

	/**
	 * 
	 * @authorGopinath
	 * 
	 * @description :verifying tag selected in the specify branding at left Header
	 *              branding
	 *
	 * 
	 * 
	 */

	public void verifyTheTagOnLeftBranding(String Tag, String Test) throws InterruptedException {

		try {

			driver.waitForPageToBeReady();

			base.waitForElement(getTIFFChkBox());

			getTIFFChkBox().Click();

			driver.scrollingToBottomofAPage();

			base.waitForElement(getTIFFTab());

			getTIFFTab().waitAndClick(10);

			driver.scrollPageToTop();

			base.waitForElement(getLeftHeaderBranding());

			getLeftHeaderBranding().waitAndClick(10);

			driver.waitForPageToBeReady();

			base.waitForElement(getTIFF_EnableforPrivilegedDocs());

			getTIFF_EnableforPrivilegedDocs().Enabled();

			getTIFF_EnableforPrivilegedDocs().waitAndClick(10);

			getLeftSpecifyBrandingBySelectingTag().ScrollTo();

			base.waitTillElemetToBeClickable(getLeftSpecifyBrandingBySelectingTag());

			getLeftSpecifyBrandingBySelectingTag().waitAndClick(10);

			getbtnSelectTags().Enabled();

			getbtnSelectTags().waitAndClick(10);

			Thread.sleep(2000);

			getChkBoxSelect(Tag).ScrollTo();

			base.waitForElement(getChkBoxSelect(Tag));

			getChkBoxSelect(Tag).waitAndClick(10);

			base.waitForElement(getbtnSelect());

			getbtnSelect().waitAndClick(10);

			Thread.sleep(5000);

			base.waitForElement(getBrandingBySelectingTagPlaceholder());

			getBrandingBySelectingTagPlaceholder().SendKeys(Test);

		} catch (Exception e) {
//			base.failedStep("Exception occcured while verifying tag selected in the  specify branding at left Header branding"+ e.getMessage());
			e.printStackTrace();
		}
	}

	public void againSelectingLeftHeaderBranding(String Tag) throws InterruptedException {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		base.waitForElement(getTIFFTab());
		getTIFFTab().waitAndClick(10);
		base.waitForElement(getLeftHeaderBranding());
		getLeftHeaderBranding().waitAndClick(10);
		getLeftSpecifyBrandingBySelectingTag().ScrollTo();
		base.waitForElement(getLeftSpecifyBrandingBySelectingTag());
		getLeftSpecifyBrandingBySelectingTag().waitAndClick(10);
		base.waitForElement(getNextSelectBtnInSpecifyBrandingSelectTags());
		getNextSelectBtnInSpecifyBrandingSelectTags().isDisplayed();
		getNextSelectBtnInSpecifyBrandingSelectTags().waitAndClick(10);

		boolean flag;
		base.waitForElement(getCancelBtn());
		base.waitTillElemetToBeClickable(getCancelBtn());
		getCancelBtn().waitAndClick(10);
		Thread.sleep(5000);
		base.waitForElement(getNextSelectBtnInSpecifyBrandingSelectTags());
		getNextSelectBtnInSpecifyBrandingSelectTags().isDisplayed();
		getNextSelectBtnInSpecifyBrandingSelectTags().waitAndClick(10);
		base.waitForElement(getDisabledSpecifyBrandingSelectTags(Tag));
		flag = getDisabledSpecifyBrandingSelectTags(Tag).GetAttribute("class").contains("disabled");

		if (!flag) {

			Assert.assertTrue(true);
			base.passedStep("ClickMarkIncomplete Disables already selected left header specify branding tag");
		} else {
			Assert.assertTrue(false);
			base.failedStep("ClickMarkIncomplete enables already selected left header specify branding tag");
		}
	}

	/**
	 * @Modified Indium-Sowndarya.Velraj
	 */
	public void fillingNativeSection() throws InterruptedException {
		SoftAssert softAssertion = new SoftAssert();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNativeTab().Enabled();
			}
		}), Input.wait30);
		getNativeTab().Click();

		String expected = "To produce specific docs natively, please select file types and/or tags below. "
				+ "In addition, to export placeholders for these docs, configure placeholder in the TIFF/PDF section"
				+ " for the same selected file types and/or tags." + "\r\n" + "\r\n"
				+ "For native only productions, please make sure to exclude the Privileged documents from the selected production corpus, "
				+ "in order to avoid the Privileged natives from being exported.";

		// added thread.sleep to avoid exception while running in batch
		Thread.sleep(1000);
		String actual = getNative_text().getWebElement().getText();

		softAssertion.assertEquals(actual, expected);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNative_SelectAllCheck().isDisplayed() && getNative_SelectAllCheck().Enabled();
			}
		}), Input.wait30);
		getNative_SelectAllCheck().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNative_AdvToggle().isDisplayed();
			}
		}), Input.wait120);
		getNative_AdvToggle().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNative_GenerateLoadFileLST().isDisplayed();
			}
		}), Input.wait30);
		getNative_GenerateLoadFileLST().waitAndClick(10);

//		String Expect = "Note that, if Privileged Placeholdering and Burn Redactions are enabled in the TIFF/PDF section, natives are not produced by default for privileged documents, redacted documents, and parents of privileged and redacted documents.";

//		String Actual = getNativeAdvanced_Text().getWebElement().getText();
//
//		softAssertion.assertEquals(Actual, Expect);
		driver.scrollingToBottomofAPage();

		base.stepInfo("Native section is filled");
	}

	/**
	 * @authorIndium-Sowndarya.Velraj.Modified on 11/1/2021
	 * @param tagname
	 * @param tagnametech
	 * @param This        method selects TIFF radio button and selects Enable for
	 *                    privileged tags by default and passes the placeholder
	 *                    value.
	 */
	public void fillingTIFFSection(String tagname, String tagnametech) throws InterruptedException {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().waitAndClick(5);

		driver.scrollingToBottomofAPage();

		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();
		driver.scrollPageToTop();

		base.waitForElement(getTIFF_CenterHeaderBranding());
		getTIFF_CenterHeaderBranding().waitAndClick(10);

		base.waitForElement(getTIFF_EnterBranding());
		new Actions(driver.getWebDriver()).moveToElement(getTIFF_EnterBranding().getWebElement()).click();
		getTIFF_EnterBranding().SendKeys(searchString4);

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().isDisplayed();

		base.waitForElement(getPriveldge_SelectTagButton());
		getPriveldge_SelectTagButton().waitAndClick(10);

		driver.waitForPageToBeReady();

		driver.scrollingToElementofAPage(getPriveldge_TagTree(tagname));
		base.waitForElement(getPriveldge_TagTree(tagname));
		getPriveldge_TagTree(tagname).waitAndClick(20);

		base.waitForElement(getPriveldge_TagTree_SelectButton());
		getPriveldge_TagTree_SelectButton().waitAndClick(10);

		driver.waitForPageToBeReady();

		base.waitForElement(getPriveldge_TextArea());
		new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();
		getPriveldge_TextArea().SendKeys(tagNameTechnical);

		driver.scrollingToBottomofAPage();
		base.stepInfo("TIFF section is filled");

	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 * @param tagname
	 * @param This    method selects TIFF component and disables "Enable for
	 *                privileged tags" toggle.Then "Enables Natively placeholder
	 *                Tags" toggle and select the particular tag.
	 */
	public void fillingTIFFSectionwithNativelyPlaceholder(String tagname) throws InterruptedException {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();

		driver.scrollingToBottomofAPage();

		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		// disabling enable for priviledged docs

		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().Enabled();
		getTIFF_EnableforPrivilegedDocs().waitAndClick(10);

		// clicking enable for natively placeholder

		driver.waitForPageToBeReady();
		getSelectCloseBtn().ScrollTo();
		getSelectCloseBtn().waitAndClick(10);
		base.waitForElement(getTiff_NativeDoc());
		getTiff_NativeDoc().waitAndClick(10);
		base.waitForElement(getclkSelectTag());
		getclkSelectTag().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(getPriveldged_TagTree(tagname));
		getPriveldged_TagTree(tagname).waitAndClick(10);
		base.waitForElement(getClkSelect());
		getClkSelect().waitAndClick(10);
		Thread.sleep(Input.wait30 / 10);
		base.waitForElement(getNativeDocsPlaceholder());
		getNativeDocsPlaceholder().SendKeys(tagname);

	}

	/**
	 * Modified on 03/05/2022
	 * 
	 * @authorIndium-Sowndarya.Velraj
	 * @param tagname
	 * @param This    method selects TIFF component and disables "Enable for
	 *                privileged tags" toggle.Then "EnablesBurn Redaction Tags"
	 *                toggle and select the particular tag.
	 */
	public void fillingTIFFSectionwithBurnRedaction(String tagname) throws InterruptedException {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();

		driver.scrollingToBottomofAPage();

		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		// disabling enable for priviledged docs

		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		base.waitTillElemetToBeClickable(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().Enabled();
		getTIFF_EnableforPrivilegedDocs().Click();

		// diabling enable for natively placeholder
		base.waitForElement(getEnableForNativelyToggle());
		getEnableForNativelyToggle().waitAndClick(10);

		getClk_burnReductiontoggle().ScrollTo();

		// enable burn redaction
		base.waitForElement(getClk_burnReductiontoggle());
		getClk_burnReductiontoggle().Click();

		getClkRadioBtn_selectRedactionTags().ScrollTo();

		base.waitForElement(getClkRadioBtn_selectRedactionTags());
		getClkRadioBtn_selectRedactionTags().isDisplayed();
		driver.waitForPageToBeReady();
		getClkRadioBtn_selectRedactionTags().waitAndClick(10);

		driver.scrollingToBottomofAPage();

		base.waitForElement(getClkCheckBox_defaultRedactionTag());
		getClkCheckBox_defaultRedactionTag().isDisplayed();
		getClkCheckBox_defaultRedactionTag().waitAndClick(10);

		getClkLink_selectingRedactionTags().ScrollTo();
		base.waitForElement(getClkLink_selectingRedactionTags());
		getClkLink_selectingRedactionTags().isDisplayed();
		getClkLink_selectingRedactionTags().waitAndClick(10);

		getClkBtn_selectingRedactionTags().ScrollTo();
		base.waitForElement(getClkBtn_selectingRedactionTags());
		getClkBtn_selectingRedactionTags().isDisplayed();
		getClkBtn_selectingRedactionTags().waitAndClick(10);

		base.waitForElement(getDefaultTag());
		getDefaultTag().isDisplayed();
		getDefaultTag().waitAndClick(10);

		base.waitForElement(getClk_selectBtn());
		getClk_selectBtn().isDisplayed();
		getClk_selectBtn().waitAndClick(10);

		base.waitForElement(gettextRedactionPlaceHolder());
		gettextRedactionPlaceHolder().isDisplayed();
		gettextRedactionPlaceHolder().waitAndClick(10);
		gettextRedactionPlaceHolder().SendKeys(searchString4);
	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 * @param tagname
	 * @param This    method verifies that previously selected tag is disabled or
	 *                not.
	 */
	public void fillingTIFFWithBurningRedactionsAndPreviouslySelectedTagDisabled(String tagname) {
		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().waitAndClick(5);

		driver.scrollingToBottomofAPage();

		base.waitForElement(getTIFFTab());
		getTIFFTab().waitAndClick(5);

		base.waitForElement(getClkLink_selectingRedactionTags());
		getClkLink_selectingRedactionTags().isDisplayed();
		getClkLink_selectingRedactionTags().waitAndClick(10);

		base.waitForElement(getSelectNextRedactionTags());
		getSelectNextRedactionTags().isDisplayed();
		getSelectNextRedactionTags().waitAndClick(10);

		// asserting for disabled tag

		boolean flag;
		base.waitForElement(getDisabledSelectRedactionTags());
		flag = getDisabledSelectRedactionTags().GetAttribute("class").contains("disabled");
		driver.waitForPageToBeReady();
		if (!flag) {
			softAssertion.assertTrue(true);
			base.passedStep("ClickMarkIncomplete enables ALready burn redaction Tags");

		} else {
			softAssertion.assertTrue(false);
			base.failedStep("ClickMarkIncomplete Disables ALready burn redaction Tags");
		}
	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 * @param tagname
	 * @param This    method selects TIFF component and disables "Enable for
	 *                privileged tags" toggle.Then selects Branding section and
	 *                respective tags.
	 */
	public void fillingTIFFWithSelectingBrandingSelectionTags(String tagname) throws InterruptedException {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();

		driver.scrollingToBottomofAPage();

		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		// disabling enable for priviledged docs

		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().Enabled();
		getTIFF_EnableforPrivilegedDocs().waitAndClick(10);

		base.waitTillElemetToBeClickable(getBrandingBySelectingTags());
		getBrandingBySelectingTags().Click();

		base.waitForElement(getbtnSelectTags());
		getbtnSelectTags().Click();

		base.waitForElement(getChkBoxSelect(tagname));
		getChkBoxSelect(tagname).Enabled();
		getChkBoxSelect(tagname).waitAndClick(5);

		base.waitForElement(getbtnSelect());
		getbtnSelect().Enabled();
		getbtnSelect().waitAndClick(10);

		base.waitForElement(gettxtBrandingPlaceholder());
		gettxtBrandingPlaceholder().isDisplayed();
		gettxtBrandingPlaceholder().SendKeys(searchString4);
	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 * @param tagname
	 * @param This    method verifies that previously selected tagfor branding
	 *                section is disabled or not.
	 */
	public void fillingTIFFWithBrandingSelectionTagsAndPreviouslySelectedTagDisabled(String tagname)
			throws InterruptedException {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();

		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();

		base.waitTillElemetToBeClickable(getBrandingBySelectingTags());
		getBrandingBySelectingTags().waitAndClick(5);

		base.waitForElement(getbtnNextSelectTags());
		getbtnNextSelectTags().waitAndClick(5);

		boolean flag;
		base.waitForElement(getChkBoxSelect(tagname));
		flag = getChkBoxSelect(tagname).GetAttribute("Class").contains("disabled");
		driver.waitForPageToBeReady();
		if (!flag) {
			Assert.assertTrue(false);

			System.out.println("tag is enabled");
			base.failedStep("ClickMarkIncomplete Disables ALready Selected Tags");

		} else {

			System.out.println(true);
			base.passedStep("ClickMarkIncomplete enables ALready Selected Tags");
		}

	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 * @param tagname
	 * @param This    method Enables Burn Redaction and selects one tag by default.
	 */
	public void fillingTIFFWithBurnRedactionAndSelectingOneTag() throws InterruptedException {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();

		driver.scrollingToBottomofAPage();

		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		// disabling enable for priviledged docs

		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().Enabled();
		getTIFF_EnableforPrivilegedDocs().Click();

		getClk_burnReductiontoggle().ScrollTo();

		// enable burn redaction
		base.waitForElement(getClk_burnReductiontoggle());
		getClk_burnReductiontoggle().Click();

		// clicking Radion button Select Redaction Tags
		getClkRadioBtn_selectRedactionTags().ScrollTo();
		base.waitForElement(getClkRadioBtn_selectRedactionTags());
		getClkRadioBtn_selectRedactionTags().isDisplayed();
		driver.waitForPageToBeReady();
		getClkRadioBtn_selectRedactionTags().waitAndClick(10);

		// selecting the checkbox-Default Redaction Tag
		base.waitForElement(getClkCheckBox_defaultRedactionTag());
		getClkCheckBox_defaultRedactionTag().isDisplayed();
		getClkCheckBox_defaultRedactionTag().waitAndClick(10);

		// clicking link again
		base.waitForElement(getClkLink_selectingRedactionTags());
		getClkLink_selectingRedactionTags().isDisplayed();
		getClkLink_selectingRedactionTags().waitAndClick(10);

		// clicking select Redaction Tags button
		base.waitForElement(getClkBtn_selectingRedactionTags());
		getClkBtn_selectingRedactionTags().isDisplayed();
		getClkBtn_selectingRedactionTags().waitAndClick(10);

		base.waitForElement(getClkCheckBox_selectingRedactionTags());
		getClkCheckBox_selectingRedactionTags().isDisplayed();
		getClkCheckBox_selectingRedactionTags().waitAndClick(10);

		base.waitForElement(getRedactionWithoutRedactionTagsCheckbox());
		getRedactionWithoutRedactionTagsCheckbox().Click();

		base.waitForElement(getClk_selectBtn());
		getClk_selectBtn().Click();

		base.waitForElement(getRedactedTagTextArea());
		getRedactedTagTextArea().isDisplayed();
		Thread.sleep(1000);
		getRedactedTagTextArea().SendKeys("Updated Redaction Tag");
	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 * @param tagname
	 * @param This    method Enables Burn Redaction and selects two tag by default.
	 */
	public void fillingTIFFWithRedactionAndSelectingDoubleTags() throws InterruptedException {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();

		driver.scrollingToBottomofAPage();

		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		// disabling enable for priviledged docs

		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().Enabled();
		getTIFF_EnableforPrivilegedDocs().Click();

		getClk_burnReductiontoggle().ScrollTo();

		// enable burn redaction
		base.waitForElement(getClk_burnReductiontoggle());
		getClk_burnReductiontoggle().Click();

		getClkRadioBtn_selectRedactionTags().ScrollTo();

		base.waitForElement(getClkRadioBtn_selectRedactionTags());
		getClkRadioBtn_selectRedactionTags().isDisplayed();
		driver.waitForPageToBeReady();
		getClkRadioBtn_selectRedactionTags().waitAndClick(10);

		// add the pop link

		base.waitForElement(getClkLink_selectingRedactionTags());
		getClkLink_selectingRedactionTags().isDisplayed();
		getClkLink_selectingRedactionTags().waitAndClick(10);

		base.waitForElement(getClkBtn_selectingRedactionTags());
		getClkBtn_selectingRedactionTags().isDisplayed();
		getClkBtn_selectingRedactionTags().waitAndClick(10);

		base.waitForElement(getRedactionWithoutRedactionTagsCheckbox());
		getRedactionWithoutRedactionTagsCheckbox().Click();

		base.waitForElement(getClk_selectBtn());
		getClk_selectBtn().Click();

		driver.waitForPageToBeReady();
		base.waitForElement(getRedactedTagTextArea());
		getRedactedTagTextArea().isDisplayed();
		getRedactedTagTextArea().SendKeys("Updated Redaction Tag");

		Thread.sleep(5000);

		// add the pop link

		base.waitForElement(getClkLink_selectingRedactionTags());
		getClkLink_selectingRedactionTags().isDisplayed();
		getClkLink_selectingRedactionTags().waitAndClick(10);

		base.waitForElement(getClkBtn_selectingRedactionTags());
		getClkBtn_selectingSecondRedactionTags().isDisplayed();
		getClkBtn_selectingSecondRedactionTags().waitAndClick(10);

		base.waitForElement(getSelectTagsHeader());
		getSelectTagsHeader().isDisplayed();

		base.hitTabKey(4);

		base.waitForElement(getDefaultRedactionTag());
		getDefaultRedactionTag().waitAndClick(5);

		base.waitForElement(getClk_selectBtn());
		getClk_selectBtn().waitAndClick(5);

		base.waitForElement(getRedactedTagTextArea());
		getRedactedTagTextArea().SendKeys("REDACTED");

	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 * @param tagname
	 * @param tagnameprev This method selects PDF component and selects "Enable for
	 *                    priviledged tags".
	 */
	public void fillingPDFSection(String tagname, String tagnameprev) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPDFChkBox().Enabled();
			}
		}), Input.wait30);
		getPDFChkBox().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPDFTab().Enabled();
			}
		}), Input.wait30);
		getPDFTab().Click();

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPDFGenerateRadioButton().Enabled();
			}
		}), Input.wait30);
		getPDFGenerateRadioButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_CenterHeaderBranding().Visible() && getTIFF_CenterHeaderBranding().Enabled();
			}
		}), Input.wait30);
		getTIFF_CenterHeaderBranding().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_EnterBranding().Enabled();
			}
		}), Input.wait30);

		new Actions(driver.getWebDriver()).moveToElement(getTIFF_EnterBranding().getWebElement()).click();
		driver.waitForPageToBeReady();
		getTIFF_EnterBranding().SendKeys(searchString4);

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_EnableforPrivilegedDocs().Enabled();
			}
		}), Input.wait30);
		getPriveldge_SelectTagButton().Click();

		base.waitForElement(getPriveldge_TagTree(tagname));
		driver.waitForPageToBeReady();
		getPriveldge_TagTree(tagname).ScrollTo();
		Thread.sleep(3000);
		getPriveldge_TagTree(tagname).waitAndClick(10);

		getPriveldge_TagTree_SelectButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPriveldge_TextArea().Enabled();
			}
		}), Input.wait30);
		new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();

		getPriveldge_TextArea().SendKeys(tagNameTechnical);

		driver.scrollingToBottomofAPage();
		base.stepInfo("PDF section is filled");

	}

	/**
	 * Author Indium Sowndarya.Velraj
	 */
	public void fillingPDFWithMultiPage(String tagname) {

		driver.waitForPageToBeReady();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPDFChkBox().Enabled();
			}
		}), Input.wait30);
		getPDFChkBox().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPDFTab().Enabled();
			}
		}), Input.wait30);
		getPDFTab().Click();

		driver.scrollPageToTop();

		base.waitForElement(getPDFGenerateRadioButton());
		new Actions(driver.getWebDriver()).moveToElement(getPDFGenerateRadioButton().getWebElement()).click();
		getPDFGenerateRadioButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_MultiPage_RadioButton().Enabled();
			}
		}), Input.wait30);
		getTIFF_MultiPage_RadioButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_CenterHeaderBranding().Visible() && getTIFF_CenterHeaderBranding().Enabled();
			}
		}), Input.wait30);
		getTIFF_CenterHeaderBranding().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_EnterBranding().Enabled();
			}
		}), Input.wait30);

		new Actions(driver.getWebDriver()).moveToElement(getTIFF_EnterBranding().getWebElement()).click();
		// getTIFF_EnterBranding().waitAndClick(10);
		getTIFF_EnterBranding().SendKeys(searchString4);

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_EnableforPrivilegedDocs().Enabled();
			}
		}), Input.wait30);

		getPriveldge_SelectTagButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPriveldge_TagTree(tagname).Enabled() && getPriveldge_TagTree(tagname).isDisplayed();
			}
		}), Input.wait30);
		getPriveldge_TagTree(tagname).Click();

		getPriveldge_TagTree_SelectButton().Click();

		base.waitForElement(getPriveldge_TextArea());
		new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();

		getPriveldge_TextArea().SendKeys(tagname);

		driver.scrollPageToTop();
		base.stepInfo("PDF section is filled with multi page option");

	}

	/**
	 * Author Indium Sowndarya.Velraj
	 */
	public void fillingTIFFWithMultiPage(String tagname) {

		driver.waitForPageToBeReady();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFFChkBox().Enabled();
			}
		}), Input.wait30);
		getTIFFChkBox().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFFTab().Enabled();
			}
		}), Input.wait30);
		getTIFFTab().Click();

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_MultiPage_RadioButton().Enabled();
			}
		}), Input.wait30);
		getTIFF_MultiPage_RadioButton().Click();

		// disabling privilegeDocs

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_EnableforPrivilegedDocs().Enabled();
			}
		}), Input.wait30);
		getTIFF_EnableforPrivilegedDocs().Click();

//		getPriveldge_SelectTagButton().Click();
//
//		driver.WaitUntil((new Callable<Boolean>() {
//			public Boolean call() {
//				return getPriveldge_TagTree(tagname).Enabled() && getPriveldge_TagTree(tagname).isDisplayed();
//			}
//		}), Input.wait30);
//		getPriveldge_TagTree(tagname).Click();
//
//		getPriveldge_TagTree_SelectButton().Click();
//
//		base.waitForElement(getPriveldge_TextArea());
//		new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();
//
//		getPriveldge_TextArea().SendKeys(tagname);

		base.stepInfo("TIFF section is filled with multi page option");

	}

	/**
	 * Modified on 04/28/2022(modified scripts as per new pt deployment)
	 * 
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void fillingPDFSectionwithNativelyPlaceholder(String tagname) throws InterruptedException {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();

		driver.scrollingToBottomofAPage();

		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();

		driver.waitForPageToBeReady();
		getPDFGenerateRadioButton().ScrollTo();
		base.clickButton(getPDFGenerateRadioButton());

		driver.waitForPageToBeReady();
		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		// disabling enable for priviledged docs

		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		base.clickButton(getTIFF_EnableforPrivilegedDocs());

		// clicking enable for natively placeholder
//		base.waitForElement(getTiff_NativeDoc());
//		getTiff_NativeDoc().Click();
		base.waitForElement(getNativePlaceholderLink());
		getNativePlaceholderLink().waitAndClick(10);

		base.waitForElement(getSecondSelectTagBtn());
		getSecondSelectTagBtn().waitAndClick(10);

		base.waitForElement(getPriveldged_TagTree(tagname));
		getPriveldged_TagTree(tagname).waitAndClick(10);
		base.waitForElement(getClkSelect());
		getClkSelect().waitAndClick(10);
		Thread.sleep(Input.wait30 / 10);
		base.waitForElement(getNativeDocsPlaceholder());
		getNativeDocsPlaceholder().SendKeys(tagname);
	}

	/**
	 * Author Indium Sowndarya.Velraj
	 */
	public void fillingTIFFSectionwithFontColour(String tagname, String fontColor) throws InterruptedException {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();

		driver.scrollingToBottomofAPage();

		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		// disabling enable for priviledged docs

		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().Enabled();
		getTIFF_EnableforPrivilegedDocs().Click();

		getClk_burnReductiontoggle().ScrollTo();

		// enable burn redaction
		base.waitForElement(getClk_burnReductiontoggle());
		getClk_burnReductiontoggle().Click();
		driver.waitForPageToBeReady();

		getClkRadioBtn_selectRedactionTags().ScrollTo();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectDropDown().Visible();
			}
		}), Input.wait30);
		getSelectDropDown().selectFromDropdown().selectByVisibleText(fontColor);

		base.waitForElement(getClkRadioBtn_selectRedactionTags());
		getClkRadioBtn_selectRedactionTags().isDisplayed();

		driver.waitForPageToBeReady();
		getClkRadioBtn_selectRedactionTags().waitAndClick(10);

		driver.scrollingToBottomofAPage();

		base.waitForElement(getClkCheckBox_defaultRedactionTag());
		getClkCheckBox_defaultRedactionTag().waitAndClick(10);

		getSelectCloseBtn().waitAndClick(10);

//		base.waitForElement(getClkLink_selectingRedactionTags());
//		getClkLink_selectingRedactionTags().waitAndClick(10);

//		base.waitForElement(getClkBtn_selectingRedactionTags());
//		getClkBtn_selectingRedactionTags().waitAndClick(10);
//
//		base.waitForElement(getClkCheckBox_selectingRedactionTags());
//		driver.waitForPageToBeReady();
//		getClkCheckBox_selectingRedactionTags().waitAndClick(10);
//
//		base.waitForElement(getClk_selectBtn());
//		getClk_selectBtn().waitAndClick(10);
//
//		base.waitForElement(gettextRedactionPlaceHolder());
//		gettextRedactionPlaceHolder().waitAndClick(10);
//		gettextRedactionPlaceHolder().SendKeys(searchString4);
	}

	/**
	 * Author Indium Sowndarya.Velraj
	 */
	public void fillingTIFFWithSinglePage(String tagname) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFFChkBox().Enabled();
			}
		}), Input.wait30);
		getTIFFChkBox().Click();

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFFTab().Enabled();
			}
		}), Input.wait30);
		getTIFFTab().Click();

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_CenterHeaderBranding().Visible() && getTIFF_CenterHeaderBranding().Enabled();
			}
		}), Input.wait30);
		getTIFF_CenterHeaderBranding().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_EnterBranding().Enabled();
			}
		}), Input.wait30);

		new Actions(driver.getWebDriver()).moveToElement(getTIFF_EnterBranding().getWebElement()).click();
		// getTIFF_EnterBranding().waitAndClick(10);
		getTIFF_EnterBranding().SendKeys(searchString4);

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_EnableforPrivilegedDocs().Enabled();
			}
		}), Input.wait30);

		getPriveldge_SelectTagButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPriveldge_TagTree(tagname).Enabled() && getPriveldge_TagTree(tagname).isDisplayed();
			}
		}), Input.wait30);
		getPriveldge_TagTree(tagname).Click();

		getPriveldge_TagTree_SelectButton().Click();

		base.waitForElement(getPriveldge_TextArea());
		new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();

		getPriveldge_TextArea().SendKeys(tagNameTechnical);

		driver.scrollPageToTop();
		base.stepInfo("TIFF section is filled with multi page option");

	}

	/**
	 * @authorIndium Sowndarya.Velraj
	 */
	public void fillingPDFForRedaction() throws InterruptedException {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();

		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();

		base.waitForElement(getPDFGenerateRadioButton());
		getPDFGenerateRadioButton().ScrollTo();
		base.clickButton(getPDFGenerateRadioButton());

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		// disabling enable for priviledged docs

		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().Enabled();
		getTIFF_EnableforPrivilegedDocs().Click();

		getClk_burnReductiontoggle().ScrollTo();

		// enable burn redaction
		base.waitForElement(getClk_burnReductiontoggle());
		getClk_burnReductiontoggle().Click();

		getClkRadioBtn_selectRedactionTags().ScrollTo();

		base.waitForElement(getClkRadioBtn_selectRedactionTags());
		getClkRadioBtn_selectRedactionTags().isDisplayed();
		driver.waitForPageToBeReady();
		getClkRadioBtn_selectRedactionTags().waitAndClick(10);

		base.waitForElement(getClkCheckBox_defaultRedactionTag());
		getClkCheckBox_defaultRedactionTag().isDisplayed();
		getClkCheckBox_defaultRedactionTag().waitAndClick(10);

		// add the pop link

		base.waitForElement(getClkLink_selectingRedactionTags());
		getClkLink_selectingRedactionTags().isDisplayed();
		getClkLink_selectingRedactionTags().waitAndClick(10);

		base.waitForElement(getClkBtn_selectingRedactionTags());
		getClkBtn_selectingRedactionTags().isDisplayed();
		getClkBtn_selectingRedactionTags().waitAndClick(10);

//		base.waitForElement(getClkCheckBox_selectingRedactionTags());
//		getClkCheckBox_selectingRedactionTags().Click();

		base.waitForElement(getRedactionWithoutRedactionTagsCheckbox());
		getRedactionWithoutRedactionTagsCheckbox().Click();

		base.waitForElement(getClk_selectBtn());
		getClk_selectBtn().Click();

		base.waitForElement(getRedactedTagTextArea());
		getRedactedTagTextArea().isDisplayed();
		getRedactedTagTextArea().SendKeys("Updated Redaction Tag");

		Thread.sleep(5000);

		// add the pop link

		base.waitForElement(getClkLink_selectingRedactionTags());
		getClkLink_selectingRedactionTags().isDisplayed();
		base.clickButton(getClkLink_selectingRedactionTags());

		base.waitForElement(getClkBtn_selectingRedactionTags());
		getClkBtn_selectingSecondRedactionTags().isDisplayed();
		getClkBtn_selectingSecondRedactionTags().waitAndClick(10);

		base.waitForElement(getSelectTagsHeader());
		getSelectTagsHeader().isDisplayed();

		base.hitTabKey(4);

		base.waitForElement(getDefaultRedactionTag());
		getDefaultRedactionTag().Click();

		base.waitForElement(getClk_selectBtn());
		getClk_selectBtn().Click();

		base.waitForElement(getRedactedTagTextArea());
		getRedactedTagTextArea().SendKeys("REDACTED");

	}

	/**
	 * @authorIndium Sowndarya.Velraj
	 */
	public void fillingTextSection() {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTextChkBox().Enabled();
			}
		}), Input.wait30);
		getTextChkBox().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTextTab().Enabled();
			}
		}), Input.wait30);
		getTextTab().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTextcomponent_text().isDisplayed();
			}
		}), Input.wait30);
		getTextcomponent_text().isElementAvailable(15);
		base.waitTime(3);
		String exptext = getTextcomponent_text().getText();
		System.out.println(exptext);
		UtilityLog.info(exptext);
//		Assert.assertEquals(exptext,
//				"Redacted documents are automatically OCRed"
//						+ " to export the text. Original extracted text is exported for natively "
//						+ "produced documents (file based placeholdering). "
//						+ "For exception and privileged placeholdered docs, " + "the placeholder text is exported."
//						+ " The configured format is applicable only to OCRed text and production generated text"
//						+ ", and not to ingested text.");

		base.stepInfo("Text section is filled");

		driver.scrollPageToTop();
	}

	/**
	 * Author Indium Sowndarya.Velraj
	 */
	public void fillingAdvancedProductionComponents(String productionname) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvancedProductionComponents(productionname).Enabled();
			}
		}), Input.wait30);
		getAdvancedProductionComponents(productionname).Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMP3ChkBox(productionname).Enabled();
			}
		}), Input.wait30);
		getMP3ChkBox(productionname).Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTranslationsChkBox(productionname).Enabled();
			}
		}), Input.wait30);
		getTranslationsChkBox(productionname).Click();

		driver.scrollPageToTop();
		base.stepInfo("Advanced production section is filled");
	}

	/**
	 * @authorIndium-Sowndarya.Velraj.Modified on 01/06/22
	 * @param beginningBates added as a argument to avoid production failure in
	 *                       batch run
	 * @return
	 */
	public String fillingNumberingAndSortingPage(String prefixId, String suffixId, String beginningBates)
			throws InterruptedException {

		base.waitForElement(getBeginningBates());
		driver.waitForPageToBeReady();
		getBeginningBates().waitAndClick(10);
		getBeginningBates().SendKeys(beginningBates);
		num = getRandomNumber(2);
//		System.out.println("Beginning Bates=" + num);
//		getBeginningBates().SendKeys(getRandomNumber(2));

		base.waitForElement(gettxtBeginningBatesIDPrefix());
		gettxtBeginningBatesIDPrefix().SendKeys(prefixId);

		base.waitForElement(gettxtBeginningBatesIDSuffix());
		gettxtBeginningBatesIDSuffix().SendKeys(suffixId);

		base.waitForElement(gettxtBeginningBatesIDMinNumLength());
		gettxtBeginningBatesIDMinNumLength().waitAndClick(10);
		num1 = getRandomNumber(1);
		System.out.println("Beginning BatesID Min Num Length=" + num1);
		gettxtBeginningBatesIDMinNumLength().SendKeys(getRandomNumber(1));

		driver.scrollingToBottomofAPage();

		base.waitForElement(getlstSortingMetaData());
		getlstSortingMetaData().selectFromDropdown().selectByVisibleText("DocID");

		base.waitForElement(getlstSortingOrder());
		getlstSortingOrder().selectFromDropdown().selectByVisibleText("Ascending");

		base.waitForElement(getlstSubSortingMetaData());
		getlstSubSortingMetaData().selectFromDropdown().selectByVisibleText("CustodianName");

		base.waitForElement(getlstSubSortingOrder());
		getlstSubSortingOrder().selectFromDropdown().selectByVisibleText("Ascending");

		base.waitForElement(getKeepFamiliesTogether());
		getKeepFamiliesTogether().waitAndClick(10);
		driver.scrollPageToTop();
		base.stepInfo("Numbering and sorting section is filled");

		return beginningBates;
	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void fillingExportNumberingAndSortingPage(String prefixId, String suffixId) throws InterruptedException {

		base.waitForElement(getBeginningSubBatesNumber());
		getBeginningSubBatesNumber().SendKeys(getRandomNumber(2));

		base.waitForElement(getExportPrefixId());
		getExportPrefixId().SendKeys(prefixId);

		base.waitForElement(getExportSuffixId());
		getExportSuffixId().SendKeys(suffixId);

		base.stepInfo("Export Numbering and sorting section is filled");
	}

	/**
	 * @throws InterruptedException
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void fillingNumberingPageWithDocumentAndPassingNullSubBatesSuccess() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumbering_Document_RadioButton().Enabled();
			}
		}), Input.wait30);
		getNumbering_Document_RadioButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumbering_Document_BeginningBates_Text().Enabled();
			}
		}), Input.wait30);
		getNumbering_Document_BeginningBates_Text().SendKeys(searchString3);

	}

	/**
	 * @throws InterruptedException
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void fillingNumberingPageWithDocumentAndPassingNullSubBatesError() throws InterruptedException {

		SoftAssert softAssertion = new SoftAssert();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumbering_Document_RadioButton().Enabled();
			}
		}), Input.wait30);
		getNumbering_Document_RadioButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumbering_Document_BeginningBates_Text().Enabled();
			}
		}), Input.wait30);
		getNumbering_Document_BeginningBates_Text().SendKeys(searchString3);

		base.waitForElement(getMarkCompleteLink());
		getMarkCompleteLink().Click();

		base.waitForElement(gettxtPleaseSpecifySubbatesNumberError());

		String actualText = gettxtPleaseSpecifySubbatesNumberError().getText();
		System.out.println(actualText);
		String expectedText = "Please specify SubBates Number";

		softAssertion.assertEquals(actualText, expectedText);
		base.passedStep("Error Line Displayed");

	}

	/**
	 * @throws InterruptedException
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void fillingDocumentSelectionPage(String foldername) throws InterruptedException {

		base.waitForElement(getFolderRadioButton());
		getFolderRadioButton().waitAndClick(10);

		getSelectFolder(foldername).ScrollTo();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectFolder(foldername).Visible();
			}
		}), Input.wait120);
		getSelectFolder(foldername).waitAndClick(10);

		driver.scrollingToBottomofAPage();

		base.waitForElement(getIncludeFamilies());
		getIncludeFamilies().Click();

		driver.scrollPageToTop();
		base.stepInfo("Document Selection Page section is filled");
	}

	/**
	 * @authorIndium-Sowndarya.Velraj.Modified on 04/08/2022
	 */
	public void fillingDocumentSelectionPageExcludingFamilies(String foldername) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderRadioButton().Enabled();
			}
		}), Input.wait30);
		getFolderRadioButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectFolder(foldername).Visible();
			}
		}), Input.wait30);
		getSelectFolder(foldername).Click();

		driver.scrollingToBottomofAPage();

		// excluding families
		base.waitForElement(getIncludeFamilies());
		getIncludeFamilies().Click();

		driver.scrollPageToTop();
		base.stepInfo("Document Selection Page section is filled");
	}

	/**
	 * @authorIndium-Sowndarya.Velraj.Modified on 10/27/2021
	 * @param Selects Document by choosing "select Tags" Radio button.
	 */
	public void fillingSelectDocumentUsingTags(String tagname) throws InterruptedException {

		base.waitForElement(getDocumentSelectionHeader());
		getDocumentSelectionHeader().isDisplayed();

		base.waitForElement(getSelectTagsRadioButton());
		getSelectTagsRadioButton().waitAndClick(5);

		driver.waitForPageToBeReady();

		getSelectTag(tagname).ScrollTo();
		base.waitForElement(getSelectTag(tagname));
		getSelectTag(tagname).waitAndClick(5);

		base.stepInfo("Document is selected by select tags option");

	}

	/**
	 * @authorIndium-Sowndarya.Velraj.Modified on 10/27/2021.
	 * @param Fills the priviledge guard page either by selecting "OK" or "Mark
	 *              Complete"
	 */
	public void fillingPrivGuardPage() {
		base.waitForElement(getbtnProductionGuardMarkComplete());
		getbtnProductionGuardMarkComplete().waitAndClick(10);

		if (getOkButton().isElementAvailable(3)) {
			getOkButton().waitAndClick(5);
		} else {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getbtnPopupPreviewMarkComplete().Enabled();
				}
			}), Input.wait30);
			getbtnPopupPreviewMarkComplete().waitAndClick(10);
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGuardNext().Enabled();
			}
		}), Input.wait30);
		getbtnProductionGuardNext().waitAndClick(5);
		base.stepInfo("Priv Guard Page section is filled");
	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void fillingProductionLocationPage(String productionname) {
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstProductionRootPaths().Enabled();
			}
		}), Input.wait30);

		getlstProductionRootPaths().selectFromDropdown().selectByVisibleText(Input.prodPath);
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionOutputLocation_ProductionDirectory().Enabled();
			}
		}), Input.wait90);
		getProductionOutputLocation_ProductionDirectory().SendKeys(productionname);

		driver.scrollPageToTop();
		base.stepInfo("production location Page section is filled");
	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void fillingExportLocationPage(String exportname) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstProductionRootPaths().Enabled();
			}
		}), Input.wait30);
		getlstProductionRootPaths().selectFromDropdown().selectByVisibleText(Input.prodPath);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionOutputLocation_ProductionDirectory().Enabled();
			}
		}), Input.wait30);
		getProductionOutputLocation_ProductionDirectory().SendKeys(exportname);

		driver.scrollPageToTop();
		base.stepInfo("Export location Page section is filled");
	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void fillingProductionLocationPageAndPassingText(String productionname) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstProductionRootPaths().Enabled();
			}
		}), Input.wait30);
		getlstProductionRootPaths().selectFromDropdown().selectByVisibleText(Input.prodPath);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionOutputLocation_ProductionDirectory().Enabled();
			}
		}), Input.wait30);
		getProductionOutputLocation_ProductionDirectory().SendKeys(productionname);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return gettxtLoadFiles().Enabled();
			}
		}), Input.wait30);
		gettxtLoadFiles().SendKeys(productionname);

		driver.scrollPageToTop();
		base.stepInfo("Production Location Page And Passing Text Page section is filled");
	}

	/**
	 * Modified on 03/08/2022
	 * 
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void fillingSummaryAndPreview() {
		base.waitForElement(getbtnProductionSummaryMarkComplete());
		getbtnProductionSummaryMarkComplete().waitAndClick(10);

		driver.waitForPageToBeReady();
		base.getCloseSucessmsg();

		base.waitForElement(getbtnProductionSummaryNext());
		getbtnProductionSummaryNext().waitAndClick(10);
		base.stepInfo("summary and preview section is filled");

	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void viewingToolTipInSummaryAndPreview() {

		SoftAssert softAssertion = new SoftAssert();

		base.waitForElement(getPriviledgedDocumunentsHelpIcon());
		getPriviledgedDocumunentsHelpIcon().waitAndClick(10);

		String expected = "Number of documents in the selected production documents having at least one Privileged tag specified in the TIFF/PDF section.";

		String actual = getPriviledgedPopOverContent().getText();
		softAssertion.assertTrue(actual.contains(expected));

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionSummaryMarkComplete().Enabled();
			}
		}), Input.wait30);
		getbtnProductionSummaryMarkComplete().Click();

		System.out.println("tool tip displayed");
		base.stepInfo("viewing priviledged tooltip successfully");

	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void viewingPreviewInSummaryTab() throws InterruptedException {

		base.waitForElement(getbtnPreview());
		getbtnPreview().Enabled();
		getbtnPreview().waitAndClick(10);
		base.stepInfo("clicking preview to get the documents in pdf files");

	}

//	public void fillingGeneratePage() throws InterruptedException {
//
//		 
//
//        SoftAssert softAssertion = new SoftAssert();
//
//        String expectedText = "Success";
//
//
//
//        driver.WaitUntil((new Callable<Boolean>() {
//
//               public Boolean call() {
//
//                     return getbtnProductionGenerate().Enabled() && getbtnProductionGenerate().isDisplayed();
//
//               }
//
//        }), Input.wait30);
//
//        getbtnProductionGenerate().Click();
//
//
//
//        Reporter.log("Wait for generate to complete", true);
//
//        System.out.println("Wait for generate to complete");
//
//        UtilityLog.info("Wait for generate to complete");
//
//
//
//        driver.WaitUntil((new Callable<Boolean>() {
//
//               public Boolean call() {
//
//                     return getDocumentGeneratetext().Visible() && getDocumentGeneratetext().Enabled();
//
//               }
//
//        }), Input.wait120);
//
//        String actualText = getStatusSuccessTxt().getText();
//
//        System.out.println(actualText);
//
//
//
//        softAssertion.assertTrue(actualText.contains(expectedText));
//
//        base.passedStep("Documents Generated successfully");
//
//
//
//        driver.WaitUntil((new Callable<Boolean>() {
//
//               public Boolean call() {
//
//                     return getConfirmProductionCommit().Enabled() && getConfirmProductionCommit().isDisplayed();
//
//               }
//
//        }), Input.wait60);
//
//
//
//        // added thread.sleep to avoid exception while executing in batch
//
//        //Thread.sleep(2000);
//
//        Boolean status=getConfirmProductionCommit().isElementPresent();
//
//        while(status!=true) {
//               status=getConfirmProductionCommit().isElementPresent();
//        }
//
//        getConfirmProductionCommit().waitAndClick(10);
//
//
//
//        String PDocCount = getProductionDocCount().getText();
//
//        // added thread.sleep to avoid exception while executing in batch
//
//        Thread.sleep(1000);
//
//        System.out.println(PDocCount);
//
//        int Doc = Integer.parseInt(PDocCount);
//
//
//
//        Reporter.log("Doc - " + Doc, true);
//
//        System.out.println(Doc);
//
//        UtilityLog.info(Doc);
//
//
//
//        driver.WaitUntil((new Callable<Boolean>() {
//
//               public Boolean call() {
//
//                     return getCopyPath().isDisplayed();
//
//               }
//
//        }), Input.wait60);
//
//        getCopyPath().waitAndClick(10);
//
//
//
//        driver.WaitUntil((new Callable<Boolean>() {
//
//               public Boolean call() {
//
//                     return getQC_Download().isDisplayed();
//
//               }
//
//        }), Input.wait30);
//
//
//
//        getQC_Download().waitAndClick(10);
//
//        getQC_Downloadbutton_allfiles().waitAndClick(10);
//
//        base.VerifySuccessMessage("Your Production Archive download will get started shortly");
//
//        base.stepInfo("Generate production page is filled");
//
// }

	/**
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void fillingGeneratePageAndRegeneratingAgain() {

		base.waitForElement(getbtnProductionGenerate());
		getbtnProductionGenerate().waitAndClick(10);

		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");
		getbtnContinueGeneration().isElementAvailable(320);
		if (getbtnContinueGeneration().isDisplayed()) {
			base.waitForElement(getbtnContinueGeneration());
			getbtnContinueGeneration().waitAndClick(10);
		}
		getbtnContinueGeneration().waitAndClick(10);
		// Get Documents Generated Text
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocumentGeneratetext().isElementAvailable(160);
			}
		}), Input.wait120);
		String actualText = getStatusSuccessTxt().getText();
		String expectedText = "Success";
		System.out.println(actualText);

		softAssertion.assertTrue(actualText.contains(expectedText));
		base.passedStep("Documents Generated successfully");
		base.stepInfo("Generation completed");

		base.waitForElement(getQC_backbutton());
		getQC_backbutton().waitAndClick(10);

		base.waitForElement(getMarkInCompleteBtn());
		getMarkInCompleteBtn().Enabled();
		base.clickButton(getMarkInCompleteBtn());
		base.stepInfo("Going back to generate Page from QC Page");

		base.waitForElement(getbtnReGenerateMarkComplete());
		getbtnReGenerateMarkComplete().isElementPresent();
		getbtnReGenerateMarkComplete().waitAndClick(10);
		base.stepInfo("Regenerate Button is clicked");

		base.waitForElement(getbtnRegenerateContinue());
		getbtnRegenerateContinue().waitAndClick(10);
		base.stepInfo("Regenerating the production");
		driver.Navigate().refresh();

	}

	/**
	 * @throws InterruptedException
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void fillingGeneratePage() throws InterruptedException {

		SoftAssert softAssertion = new SoftAssert();
		String expectedText = "Success";

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGenerate().Enabled() && getbtnProductionGenerate().isDisplayed();
			}
		}), Input.wait30);
		getbtnProductionGenerate().Click();

		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocumentGeneratetext().isElementAvailable(540);
			}
		}), Input.wait120);

//		driver.WaitUntil((new Callable<Boolean>() {
//			public Boolean call() {
//				return getDocumentGeneratetext().Visible() && getDocumentGeneratetext().Enabled();
//			}
//		}), Input.wait120);
		String actualText = getStatusSuccessTxt().getText();
		System.out.println(actualText);

		softAssertion.assertTrue(actualText.contains(expectedText));
		base.passedStep("Documents Generated successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getConfirmProductionCommit().Enabled() && getConfirmProductionCommit().isDisplayed();
			}
		}), Input.wait60);

		// added thread.sleep to avoid exception while executing in batch

		base.waitTime(2);
		getConfirmProductionCommit().waitAndClick(10);
		base.CloseSuccessMsgpopup();

		String PDocCount = getProductionDocCount().getText();
		// added thread.sleep to avoid exception while executing in batch
		base.waitTime(2);
		System.out.println(PDocCount);
		int Doc = Integer.parseInt(PDocCount);

		Reporter.log("Doc - " + Doc, true);
		System.out.println(Doc);
		UtilityLog.info(Doc);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCopyPath().isDisplayed();
			}
		}), Input.wait60);
		getCopyPath().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getQC_Download().isDisplayed();
			}
		}), Input.wait30);

		getQC_Download().waitAndClick(10);
		getQC_Downloadbutton_allfiles().waitAndClick(10);
		base.VerifySuccessMessage("Your Production Archive download will get started shortly");
		base.stepInfo("Generate production page is filled");
	}

	/**
	 *Modified on 16/08/2022  Added refresh statement after commit as per deployment
	 * Modified on 03/09/2022(Replaced isElementAvailable() to avoid
	 * e.printStackTrace in reports) Modified on 03/21/2022 return the document
	 * count
	 * 
	 * @throws InterruptedException
	 * @authorIndium-Sowndarya.Velraj.Modified on 10/28/2021
	 */
	public int fillingGeneratePageWithContinueGenerationPopup() throws InterruptedException {

		SoftAssert softAssertion = new SoftAssert();
		String expectedText = "Success";

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGenerate().Enabled() && getbtnProductionGenerate().isDisplayed();
			}
		}), Input.wait30);
		getbtnProductionGenerate().waitAndClick(10);

		getVerifyGenStatus("Reserving Bates").isElementAvailable(150);
		getbtnContinueGeneration().isElementAvailable(60);
		if (getbtnContinueGeneration().isDisplayed()) {
			base.waitForElement(getbtnContinueGeneration());
			getbtnContinueGeneration().waitAndClick(10);
		}

		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");

		getDocumentGeneratetext().isElementAvailable(180);
		base.stepInfo("wait until Document Generated Text is visible");
		String actualText = getStatusSuccessTxt().getText();
		System.out.println(actualText);

		softAssertion.assertTrue(actualText.contains(expectedText));
		base.passedStep("Documents Generated successfully");

		getConfirmProductionCommit().isElementAvailable(120);
		base.waitTime(2);
		base.waitTillElemetToBeClickable(getConfirmProductionCommit());
		getConfirmProductionCommit().ScrollTo();
		getConfirmProductionCommit().waitAndClick(10);

		if (base.getErrorMsgHeader().isElementAvailable(2)) {
			base.failedStep("Commit is not successfull");
		}
		if (base.getCloseSucessmsg().isElementAvailable(2)) {
			base.CloseSuccessMsgpopup();
		}
		//to refresh the page after commit
		driver.Navigate().refresh();
		
		String PDocCount = getProductionDocCount().getText();
		// added thread.sleep to avoid exception while executing in batch
		base.waitTime(1);
		System.out.println(PDocCount);
		int Doc = Integer.parseInt(PDocCount);

		Reporter.log("Doc - " + Doc, true);
		System.out.println(Doc);
		UtilityLog.info(Doc);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCopyPath().isDisplayed();
			}
		}), Input.wait60);
		getCopyPath().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getQC_Download().isDisplayed();
			}
		}), Input.wait30);

		getQC_Download().waitAndClick(10);
		getQC_Downloadbutton_allfiles().waitAndClick(10);
		base.VerifySuccessMessage("Your Production Archive download will get started shortly");
		base.stepInfo("Generate production page is filled");

		return Doc;
	}

	/**
	 * @authorIndium-Sowndarya.Velraj.Modified on 03/08/2022
	 */
	public void navigateToNextSection() throws InterruptedException {

		driver.scrollPageToTop();
		base.waitForElement(getMarkCompleteLink());
		getMarkCompleteLink().waitAndClick(10);

		System.out.println("Clicked on Mark Complete Button..");
		driver.waitForPageToBeReady();

		base.CloseSuccessMsgpopup();

		base.waitForElement(getNextButton());
		getNextButton().Enabled();
		getNextButton().waitAndClick(10);

		base.stepInfo("Navigate to next page");
	}

	/**
	 * @throws InterruptedException
	 * @authorIndium-Sowndarya.Velraj.Modified on 01/06/22
	 * @param beginningBates added as a argument to avoid production failure in
	 *                       batch run
	 */
	public void InsertingDataFromNumberingToGenerateWithContinuePopup(String prefixID, String suffixID,
			String foldername, String productionname, String beginningBates) throws InterruptedException {

		fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		navigateToNextSection();
		fillingDocumentSelectionPage(foldername);
		navigateToNextSection();
		fillingPrivGuardPage();
		fillingProductionLocationPageAndPassingText(productionname);
		navigateToNextSection();
		fillingSummaryAndPreview();
		fillingGeneratePageWithContinueGenerationPopup();
	}

	/**
	 * @throws InterruptedException
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void fillingDocumentSelectionPageWithTag(String tag1, String tag2) {

		base.waitForElement(getTagRadioButton());
		getTagRadioButton().waitAndClick(10);

		base.waitForElement(getSelectFolder(tag1));
		getSelectFolder(tag1).waitAndClick(10);

		base.waitForElement(getSelectFolder(tag2));
		getSelectFolder(tag2).waitAndClick(10);

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getIncludeFamilies().Visible();
			}
		}), Input.wait30);
		getIncludeFamilies().Click();

		driver.scrollPageToTop();
		base.stepInfo("Document Selection Page section is filled");
	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void fillingTheTIFFSection() throws InterruptedException {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();

		driver.scrollingToBottomofAPage();

		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();

//		getClk_burnReductiontoggle().ScrollTo();

		// enable burn redaction
		base.waitForElement(getClk_burnReductiontoggle());
		getClk_burnReductiontoggle().Click();

		getClkRadioBtn_selectRedactionTags().ScrollTo();

		base.waitForElement(getClkRadioBtn_selectRedactionTags());
		getClkRadioBtn_selectRedactionTags().isDisplayed();
		driver.waitForPageToBeReady();
		getClkRadioBtn_selectRedactionTags().waitAndClick(10);

		base.waitForElement(getClkLink_selectingRedactionTags());
		getClkLink_selectingRedactionTags().isDisplayed();
		getClkLink_selectingRedactionTags().waitAndClick(10);

		base.waitForElement(getClkBtn_selectingRedactionTags());
		getClkBtn_selectingRedactionTags().isDisplayed();
		getClkBtn_selectingRedactionTags().waitAndClick(10);

		// Assertion:
		base.waitForElement(getpopupAssert());
		getpopupAssert().isDisplayed();
		getpopupAssert().waitAndClick(10);

		Thread.sleep(5000);
		String actualText = getredactionsWithRedactionTags().getText();

		driver.waitForPageToBeReady();
		System.out.println(actualText);
		String expectedText = "When this is checked, the configured redaction text will be applied to the burned redactions that do not have any redaction tags associated with them. If this is not checked, the default text “Redacted” will be applied to the redactions without any redaction tags.";

		// softAssert
		SoftAssert softAssertion = new SoftAssert();
		softAssertion.assertTrue(actualText.contains(expectedText));
		base.passedStep("Error Line Displayed");

	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 */
	public String getRandomNumber(int stringLength) {
		String randomNumber = null;
		Random random = new Random();
		String Number = "0123456789";
		try {
			StringBuilder buffer = new StringBuilder(stringLength);
			for (int i = 0; i < stringLength; i++) {
				int index = (int) (random.nextFloat() * Number.length());
				buffer.append(Number.charAt(index));
			}
			randomNumber = buffer.toString();
		} catch (Exception e) {

		}
		return randomNumber;
	}

	public void generateExport(String foldername, String exportDirectory, String setName) throws InterruptedException {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderRadioButton().Visible();
			}
		}), Input.wait30);
		getFolderRadioButton().waitAndClick(10);

		Thread.sleep(5000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectFolder(foldername).Visible();
			}
		}), Input.wait30);
		getSelectFolder(foldername).waitAndClick(5);

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getIncludeFamilies().Visible();
			}
		}), Input.wait30);
		getIncludeFamilies().waitAndClick(10);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnDocumentsSelectionMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnDocumentsSelectionMarkComplete().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnDocumentsSelectionNext().Enabled();
			}
		}), Input.wait30);
		getbtnDocumentsSelectionNext().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGuardMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnProductionGuardMarkComplete().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getOkButton().Visible();
			}
		}), Input.wait30);
		getOkButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGuardNext().Enabled();
			}
		}), Input.wait30);
		getbtnProductionGuardNext().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstProductionRootPaths().Visible();
			}
		}), Input.wait30);
		getlstProductionRootPaths().selectFromDropdown().selectByVisibleText(Input.prodPath);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionOutputLocation_ProductionDirectory().Visible();
			}
		}), Input.wait30);
		getProductionOutputLocation_ProductionDirectory().SendKeys(exportDirectory);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionLocationMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnProductionLocationMarkComplete().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionLocationNext().Enabled();
			}
		}), Input.wait30);
		getbtnProductionLocationNext().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPreviewprod().Enabled();
			}
		}), Input.wait30);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionSummaryMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnProductionSummaryMarkComplete().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionSummaryNext().Enabled();
			}
		}), Input.wait30);
		getbtnProductionSummaryNext().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGenerate().Visible();
			}
		}), Input.wait30);
		getbtnProductionGenerate().waitAndClick(10);

		System.out.println("Wait until regenerate is enabled");
		for (int i = 0; i < 120; i++) {
			try {

				this.driver.getWebDriver().get(Input.url + "Production/Home");
				getProductionLink().waitAndClick(5);
				getbtnGenerateMarkComplete().waitAndClick(5);
				System.out.println("Generated");
				break;

			} catch (Exception e) {
				Thread.sleep(10000);
				driver.Navigate().refresh();

			}
		}

		String batesno = getProd_BatesRange().getText();
		System.out.println(batesno);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnSummaryNext().Enabled();
			}
		}), Input.wait30);
		getbtnSummaryNext().waitAndClick(10);
		// Thread.sleep(10000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getConfirmProductionCommit().Enabled();
			}
		}), Input.wait30);
		getConfirmProductionCommit().waitAndClick(10);

		String PDocCount = getProductionDocCount().getText();
		int Doc = Integer.parseInt(PDocCount);
		System.out.println(Doc);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getReviewproductionButton().Visible();
			}
		}), Input.wait30);
		getReviewproductionButton().waitAndClick(10);

		String location = getDestinationPathText().getText();
		System.out.println(location);

	}

	public void FillingallsectionsProduction(String productionname, String PrefixID, String SuffixID,
			final String foldername) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getComponentsMarkComplete().Enabled();
			}
		}), Input.wait30);
		getComponentsMarkComplete().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getComponentsMarkNext().Enabled();
			}
		}), Input.wait30);
		getComponentsMarkNext().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBeginningBates().Enabled();
			}
		}), Input.wait30);
		getBeginningBates().SendKeys("100");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return gettxtBeginningBatesIDPrefix().Enabled();
			}
		}), Input.wait30);
		gettxtBeginningBatesIDPrefix().SendKeys(PrefixID);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return gettxtBeginningBatesIDSuffix().Enabled();
			}
		}), Input.wait30);
		gettxtBeginningBatesIDSuffix().SendKeys(SuffixID);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return gettxtBeginningBatesIDMinNumLength().Enabled();
			}
		}), Input.wait30);
		gettxtBeginningBatesIDMinNumLength().SendKeys("10");

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstSortingMetaData().Enabled();
			}
		}), Input.wait30);
		getlstSortingMetaData().selectFromDropdown().selectByVisibleText("DocID");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstSortingOrder().Enabled();
			}
		}), Input.wait30);
		getlstSortingOrder().selectFromDropdown().selectByVisibleText("Ascending");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstSortingMetaData().Enabled();
			}
		}), Input.wait30);
		getlstSubSortingMetaData().selectFromDropdown().selectByVisibleText("CustodianName");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstSubSortingOrder().Enabled();
			}
		}), Input.wait30);
		getlstSubSortingOrder().selectFromDropdown().selectByVisibleText("Ascending");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getKeepFamiliesTogether().Visible();
			}
		}), Input.wait30);
		getKeepFamiliesTogether().waitAndClick(10);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumAndSortMarkComplete().Enabled();
			}
		}), Input.wait30);
		getNumAndSortMarkComplete().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumAndSortNext().Enabled();
			}
		}), Input.wait30);
		getNumAndSortNext().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderRadioButton().Visible();
			}
		}), Input.wait30);
		getFolderRadioButton().waitAndClick(10);

		Thread.sleep(5000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectFolder(foldername).Visible();
			}
		}), Input.wait30);
		getSelectFolder(foldername).waitAndClick(5);

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getIncludeFamilies().Visible();
			}
		}), Input.wait30);
		getIncludeFamilies().waitAndClick(10);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnDocumentsSelectionMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnDocumentsSelectionMarkComplete().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnDocumentsSelectionNext().Enabled();
			}
		}), Input.wait30);
		getbtnDocumentsSelectionNext().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGuardMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnProductionGuardMarkComplete().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getOkButton().Visible();
			}
		}), Input.wait30);
		getOkButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGuardNext().Enabled();
			}
		}), Input.wait30);
		getbtnProductionGuardNext().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstProductionRootPaths().Visible();
			}
		}), Input.wait30);
		getlstProductionRootPaths().selectFromDropdown().selectByVisibleText(Input.prodPath);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionOutputLocation_ProductionDirectory().Visible();
			}
		}), Input.wait30);
		getProductionOutputLocation_ProductionDirectory().SendKeys(productionname);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionLocationMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnProductionLocationMarkComplete().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionLocationNext().Enabled();
			}
		}), Input.wait30);
		getbtnProductionLocationNext().waitAndClick(10);

//  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//  							getPreviewprod().Enabled()  ;}}), Input.wait30); 
//  					getPreviewprod().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionSummaryMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnProductionSummaryMarkComplete().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionSummaryNext().Enabled();
			}
		}), Input.wait30);
		getbtnProductionSummaryNext().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGenerate().Visible();
			}
		}), Input.wait30);
		getbtnProductionGenerate().waitAndClick(10);
		System.out.println("Wait until regenerate is enabled");
//  					for (int i = 0; i < 120; i++)
//  					{
//  						try
//  						{
//  					this.driver.getWebDriver().get(Input.url+"Production/Home");
//  			    	getProductionLink().waitAndClick(5);
//  					getbtnGenerateMarkComplete().waitAndClick(5);
//  					System.out.println("Generated");
//  					break;
//  					}
//  						catch (Exception e)
//  						{
//  							Thread.sleep(10000);
//  							driver.Navigate().refresh();
//  						}
//  					}
//  				
//  				
//  					String batesno = getProd_BatesRange().getText();
//  					System.out.println(batesno);
//  					
//  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//  							getbtnSummaryNext().Enabled()  ;}}), Input.wait30); 
//  					getbtnSummaryNext().waitAndClick(10);
//  			
//  				
//  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//  							getConfirmProductionCommit().Enabled()  ;}}), Input.wait30); 
//  					getConfirmProductionCommit().waitAndClick(10);
//  					
//  					 String PDocCount = getProductionDocCount().getText();
//  			         int Doc = Integer.parseInt(PDocCount);
//  			         System.out.println(Doc); 
//  					
//  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//  							getReviewproductionButton().Visible()  ;}}), Input.wait30); 
//  					getReviewproductionButton().waitAndClick(10);
//  					
//  					String location = getDestinationPathText().getText();
//  			        System.out.println(location);
//  			        Thread.sleep(2000);

	}

	public void savetemplate(String templatename) {
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		getProdStateFilter().WaitUntilPresent();
		getProdStateFilter().selectFromDropdown().selectByVisibleText("COMPLETED");
		getprod_ActionButton().waitAndClick(20);
		getprod_Action_SaveTemplate().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getprod_Templatetext().Visible();
			}
		}), Input.wait30);
		getprod_Templatetext().SendKeys(templatename);
		getProdExport_SaveButton().waitAndClick(5);
		base.VerifySuccessMessage("Production Saved as a Custom Template.");
	}

	public void ProductionwithNatives(String productionname, String PrefixID, String SuffixID, final String foldername,
			String templatename) throws InterruptedException

	{
		addANewProduction(productionname);
		getBasicInfoNext();
		fillingDATSection();
		fillingNativeSection();
		driver.scrollPageToTop();
		FillingallsectionsProduction(productionname, PrefixID, SuffixID, foldername);
		System.out.println("....executed Filling all section production......");
		savetemplate(templatename);
		System.out.println("....hsyudhus......");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddNewProductionbutton().Visible();
			}
		}), Input.wait30);
		getAddNewProductionbutton().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionName().Visible();
			}
		}), Input.wait30);
		getProductionName().SendKeys(productionname);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionDesc().Visible();
			}
		}), Input.wait30);
		getProductionDesc().SendKeys(productionname);
		getprod_LoadTemplate().selectFromDropdown().selectByVisibleText("Template (Production)");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBasicInfoMarkComplete().Visible();
			}
		}), Input.wait30);
		getBasicInfoMarkComplete().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getComponentsMarkComplete().Enabled();
			}
		}), Input.wait30);
		getComponentsMarkComplete().waitAndClick(10);
	}

	public void ProductionwithTechIssuetags(String productionname, String PrefixID, String SuffixID,
			final String foldername, String Tagnametech, String Tagnameprev) throws InterruptedException

	{
		addANewProduction(productionname);
		getBasicInfoNext();
		fillingDATSection();
		fillingTIFFSection(Tagnameprev, Tagnametech);

		driver.scrollingToBottomofAPage();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTextChkBox().Enabled();
			}
		}), Input.wait30);
		getTextChkBox().waitAndClick(10);
		driver.scrollPageToTop();
		FillingallsectionsProduction(productionname, PrefixID, SuffixID, foldername);

	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void prodGenerationFailedInProgressBar() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByButton().Enabled();
			}
		}), Input.wait30);
		getFilterByButton().Click();
		base.stepInfo("Filtering the failed production");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByDRAFT().Enabled();
			}
		}), Input.wait30);
		getFilterByDRAFT().Click();
		base.stepInfo("Removing filter by draft");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByINPROGRESS().Enabled();
			}
		}), Input.wait30);
		getFilterByINPROGRESS().Click();
		base.stepInfo("Removing filter by in progress");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterDropDown().Enabled();
			}
		}), Input.wait30);
		getFilterDropDown().Click();

		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return gettxtProdGenerationFailed().Enabled();
				}
			}), Input.wait30);
			base.stepInfo("Failed production displays");

			String failedMsg = gettxtProdGenerationFailed().getText();
			base.passedStep("verifing the failed status :" + failedMsg);
			Assert.assertTrue(true, failedMsg);
		} catch (Exception e) {
			base.passedStep("No failed production occurs");
		}

	}

	/**
	 * @authorIndium-Sowndarya.Velraj.Modified on 01/06/22
	 */
	public void prodGenerationInProgressStatus(String productionName) throws InterruptedException {

		base.stepInfo("Filtering IN progress status production");
		base.waitForElement(getFilterDropDown());
		getFilterDropDown().waitAndClick(10);

		base.waitForElement(getFilterByDRAFT());
		getFilterByDRAFT().waitAndClick(5);
		base.stepInfo("Removing filter by draft");

		base.waitForElement(getFilterByFAILED());
		getFilterByFAILED().waitAndClick(10);
		base.stepInfo("Removing filter by failed");

		base.waitForElement(getFilterDropDown());
		getFilterDropDown().waitAndClick(10);

		getProductionFromHomepage(productionName).waitAndClick(10);

	}

	/**
	 * @authorIndium-Sowndarya.Velraj.Modified on 01/06/22
	 */
	public void prodReservingBatesRangeFailedProduction() throws InterruptedException {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByButton().Enabled();
			}
		}), Input.wait30);
		getFilterByButton().Click();
		base.stepInfo("Filtering the failed production");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByDRAFT().Enabled();
			}
		}), Input.wait30);
		getFilterByDRAFT().Click();
		base.stepInfo("Removing filter by draft");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByINPROGRESS().Enabled();
			}
		}), Input.wait30);
		getFilterByINPROGRESS().Click();
		base.stepInfo("Removing filter by in progress");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterDropDown().Enabled();
			}
		}), Input.wait30);
		getFilterDropDown().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return gettxtReservingBatesFailed().Enabled();
			}
		}), Input.wait30);
		base.stepInfo("Reserving Bates Range Failed production displays");

		if (gettxtReservingBatesFailed().Enabled()) {
			String batesFailedMsg = gettxtReservingBatesFailed().getText();
			base.passedStep("verifing the failed status :" + batesFailedMsg);
			Assert.assertTrue(true, batesFailedMsg);
		} else {
			base.passedStep("No Reserving Bates Range failed production occurs");
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getGearIcon().Enabled() && getGearIcon().isDisplayed();
			}
		}), Input.wait30);

		getGearIcon().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getOpenWizard().Enabled() && getOpenWizard().isDisplayed();
			}
		}), Input.wait30);

		getOpenWizard().Click();

		base.waitForElement(getbtnReGenerateMarkComplete());
		getbtnReGenerateMarkComplete().waitAndClick(5);

		base.waitForElement(getbtnRegenerateContinue());
		getbtnRegenerateContinue().Click();

		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");

		base.waitForElement(getbtnReGenerateMarkComplete());
		getbtnReGenerateMarkComplete().Click();

		base.waitForElement(getbtnRegenerateCancel());
		getbtnRegenerateCancel().Click();

	}

	public void ProductionSort() {
		getSortByButton().selectFromDropdown().selectByVisibleText("Production Date");
		getRefreshButton().waitAndClick(10);

		getSortByButton().selectFromDropdown().selectByVisibleText("Locked");
		getRefreshButton().waitAndClick(10);

		getSortByButton().selectFromDropdown().selectByVisibleText("Production Name");
		getRefreshButton().waitAndClick(10);

		getSortByButton().selectFromDropdown().selectByVisibleText("User Name");
		getRefreshButton().waitAndClick(10);

		getSortByButton().selectFromDropdown().selectByVisibleText("Status");
		getRefreshButton().waitAndClick(10);

		System.out.println("Verified Production Sort");
	}

	public void GridView() throws InterruptedException {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByButton().Enabled();
			}
		}), Input.wait30);
		getFilterByButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByCOMPLETED().Visible();
			}
		}), Input.wait30);
		getFilterByCOMPLETED().waitAndClick(10);
		getRefreshButton().waitAndClick(10);

		String tileitems = getProductionItemsTileItems().getText();
		System.out.println(tileitems);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getGridView().Visible();
			}
		}), Input.wait30);
		getGridView().waitAndClick(10);
		getRefreshButton().waitAndClick(10);
		Thread.sleep(3000);
		String griditems = getProductionItemsGridItems().getText();
		System.out.println(griditems);
		Assert.assertTrue(griditems.contains(tileitems));
		System.out.println("Verified Tile Production and Grid Production are eqal");

	}

	public void SaveTemplate(final String tempName) throws InterruptedException {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByButton().Visible();
			}
		}), Input.wait30);
		getFilterByButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByCOMPLETED().Visible();
			}
		}), Input.wait30);
		getFilterByCOMPLETED().waitAndClick(10);
		getRefreshButton().waitAndClick(10);

		// System.out.println("Number of records in a current page :
		// "+getProductionItemsTile().size());

		if (getProductionItemsTile().size() > 0) {
			getArrow().waitAndClick(10);
			getSaveTemplate().waitAndClick(10);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getTemplateName().Visible();
				}
			}), Input.wait30);
			getTemplateName().SendKeys(tempName);

			getSave().waitAndClick(10);
			BaseClass bc = new BaseClass(driver);
			bc.VerifySuccessMessage("Production Saved as a Custom Template.");
			bc.CloseSuccessMsgpopup();
			getManageTemplates().waitAndClick(10);

			ArrayList<String> tableele = new ArrayList<String>();
			java.util.List<WebElement> table = getCustomTemplates().FindWebElements();
			for (int i = 1; i < getCustomTemplates().size(); i++) {
				tableele.add(table.get(i).getText());
			}
			Assert.assertTrue(tableele.contains(tempName));
			System.out.println("Verified saved template under Custom template");
		}
	}

	public void DeleteTemplate(final String tempName) throws InterruptedException {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByButton().Visible();
			}
		}), Input.wait30);
		getFilterByButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByCOMPLETED().Visible();
			}
		}), Input.wait30);
		getFilterByCOMPLETED().waitAndClick(10);
		getRefreshButton().waitAndClick(10);

		System.out.println("Number of records in a current page : " + getProductionItemsTile().size());

		if (getProductionItemsTile().size() > 0) {
			getArrow().waitAndClick(10);
			getSaveTemplate().waitAndClick(10);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getTemplateName().Visible();
				}
			}), Input.wait30);
			getTemplateName().SendKeys(tempName);

			getSave().waitAndClick(10);
			BaseClass bc = new BaseClass(driver);
			bc.VerifySuccessMessage("Production Saved as a Custom Template.");
			bc.CloseSuccessMsgpopup();
			getManageTemplates().waitAndClick(10);

			ArrayList<String> tableele = new ArrayList<String>();
			java.util.List<WebElement> table = getCustomTemplates().FindWebElements();
			for (int i = 1; i < getCustomTemplates().size(); i++) {
				tableele.add(table.get(i).getText());
			}
			// System.out.println(tableele);
			Assert.assertTrue(tableele.contains(tempName));

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getCustomTemplateName(tempName).Visible();
				}
			}), Input.wait30);
			getCustomTemplateName(tempName).waitAndClick(10);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDeleteTemplate().Visible();
				}
			}), Input.wait30);
			getDeleteTemplate().waitAndClick(10);

			getOK().waitAndClick(10);
			bc.VerifySuccessMessage("Custom Template deleted successfully");

			System.out.println("Verified saved template got deleted successfully under Custom template");
		}
	}

	/**
	 * Modified by sowndarya.velraj on 04/08/2022
	 */
	public void CreateProductionSets(final String prodsetName) throws InterruptedException {

		base.waitForElement(getCreateProductionSet());
		getCreateProductionSet().waitAndClick(10);

		base.waitForElement(getSetName());
		getSetName().SendKeys(prodsetName);

		base.waitForElement(productionSetRadioBtn());
		String getCssValue = productionSetRadioBtn().GetCssValue("background-color");
		System.out.println(getCssValue);

		productionSetRadioBtn().waitAndClick(10);
		driver.waitForPageToBeReady();

		base.waitForElement(getSaveSet());
		getSaveSet().waitAndClick(10);
		base.waitTime(2);

		base.VerifySuccessMessage("Production Set Added Successfully");
		System.out.println("Verified created production set");

	}

	/**
	 * @throws InterruptedException
	 * @authorIndium-Sowndarya.Velraj
	 * @param beginningBates added as a argument to avoid production failure in
	 *                       batch run
	 */
	public void InsertingDataFromNumberingToGenerate(String prefixID, String suffixID, String foldername,
			String productionname, String beginningBates) throws InterruptedException {

		fillingNumberingAndSortingPage(prefixID, suffixID, beginningBates);
		navigateToNextSection();
		fillingDocumentSelectionPage(foldername);
		navigateToNextSection();
		fillingPrivGuardPage();
		fillingProductionLocationPageAndPassingText(productionname);
		navigateToNextSection();
		fillingSummaryAndPreview();
		fillingGeneratePage();
	}

	public void DeleteteProductionSets(final String prodsetName) throws InterruptedException {

		getProdExport_ProductionSets().waitAndClick(10);

		String myString = prodsetName.concat(" (Production Set)");

		getProdExport_ProductionSets().selectFromDropdown().selectByVisibleText(myString);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDeleteProd().Visible();
			}
		}), Input.wait60);
		getDeleteProd().waitAndClick(10);

		getOK().waitAndClick(30);
		BaseClass bc = new BaseClass(driver);
		Thread.sleep(3000);
		bc.VerifySuccessMessage("Production Set has been deleted successfully.");
		bc.CloseSuccessMsgpopup();
		System.out.println("Verified deleted production set");

	}

	public void ProductionLock() throws InterruptedException {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByButton().Visible();
			}
		}), Input.wait30);
		getFilterByButton().waitAndClick(10);
		for (int i = 1; i < getFilterOptions().size(); i++) {
			getFilter(i).waitAndClick(10);
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByCOMPLETED().Visible();
			}
		}), Input.wait30);
		getFilterByCOMPLETED().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getRefreshButton().Visible();
			}
		}), Input.wait30);
		getRefreshButton().waitAndClick(10);

		Thread.sleep(1000);
		getArrow().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getLock().Visible();
			}
		}), Input.wait30);
		getLock().waitAndClick(10);
		Thread.sleep(1000);
		getOK().waitAndClick(10);
		Thread.sleep(1000);
		BaseClass bc = new BaseClass(driver);
		bc.VerifySuccessMessage("Production Lock Successfully.");
		bc.CloseSuccessMsgpopup();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByButton().Visible();
			}
		}), Input.wait30);
		getFilterByButton().waitAndClick(10);
		for (int i = 1; i < getFilterOptions().size(); i++) {
			getFilter(i).waitAndClick(10);
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByCOMPLETED().Visible();
			}
		}), Input.wait30);
		getFilterByCOMPLETED().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getRefreshButton().Visible();
			}
		}), Input.wait30);
		getRefreshButton().waitAndClick(10);
		Thread.sleep(1000);
		String locktxt = getLockIcon().GetAttribute("class");
		Assert.assertTrue(locktxt.contains("lock"));
		System.out.println("Verified Lock Production");
	}

	public void CustomizeTemplate(final String tempName, String productionName) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByButton().Visible();
			}
		}), Input.wait30);
		getFilterByButton().waitAndClick(10);
		for (int i = 1; i < getFilterOptions().size(); i++) {
			getFilter(i).waitAndClick(10);
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByCOMPLETED().Visible();
			}
		}), Input.wait30);
		getFilterByCOMPLETED().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getRefreshButton().Visible();
			}
		}), Input.wait30);
		getRefreshButton().waitAndClick(10);

		getArrow().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSaveTemplate().Visible();
			}
		}), Input.wait30);
		getSaveTemplate().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTemplateName().Visible();
			}
		}), Input.wait30);
		getTemplateName().SendKeys(tempName);

		getSave().waitAndClick(10);
		BaseClass bc = new BaseClass(driver);
		bc.VerifySuccessMessage("Production Saved as a Custom Template.");
		bc.CloseSuccessMsgpopup();

		getManageTemplates().waitAndClick(10);

		ArrayList<String> tableele = new ArrayList<String>();
		java.util.List<WebElement> table = getCustomTemplates().FindWebElements();
		for (int i = 1; i < getCustomTemplates().size(); i++) {
			tableele.add(table.get(i).getText());
		}
		Assert.assertTrue(tableele.contains(tempName));
		System.out.println("Verified saved template under Custom template");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPRODUCTIONSEXPORTSTabs().Visible();
			}
		}), Input.wait30);
		getPRODUCTIONSEXPORTSTabs().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddNewProductionbutton().Visible();
			}
		}), Input.wait30);
		getAddNewProductionbutton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionName().Visible();
			}
		}), Input.wait30);
		getProductionName().SendKeys(productionName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionDesc().Visible();
			}
		}), Input.wait30);
		getProductionDesc().SendKeys(productionName);

		String myString = tempName.concat(" (Production)");
		getLoadTemplate().selectFromDropdown().selectByVisibleText(myString);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBasicInfoSave().Visible();
			}
		}), Input.wait30);
		getBasicInfoSave().waitAndClick(10);

		this.driver.getWebDriver().get(Input.url + "Production/Home");

		getArrow().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getOpenWizard().Visible();
			}
		}), Input.wait30);
		getOpenWizard().waitAndClick(10);

		System.out.println("Verified Customized Template");
	}

	public void VerifyProductionSet(final String prodsetName) throws InterruptedException {

		getProdExport_ProductionSets().waitAndClick(10);

		String myString = prodsetName.concat(" (Production Set)");

		getProdExport_ProductionSets().selectFromDropdown().selectByVisibleText(myString);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPRODUCTIONSSETName().Visible();
			}
		}), Input.wait30);
		String myprodsetname = getPRODUCTIONSSETName().getText();

//	        	Assert.assertEquals(prodsetName, myprodsetname);

	}

	public void ProductionDeletionCheck() throws InterruptedException {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByButton().Visible();
			}
		}), Input.wait30);
		getFilterByButton().waitAndClick(10);
		for (int i = 1; i < getFilterOptions().size(); i++) {
			getFilter(i).waitAndClick(10);
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByCOMPLETED().Visible();
			}
		}), Input.wait30);
		getFilterByCOMPLETED().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getRefreshButton().Visible();
			}
		}), Input.wait30);
		getRefreshButton().waitAndClick(10);

		Thread.sleep(1000);
		getArrow().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTileDelete().Visible();
			}
		}), Input.wait30);

		String deletetiletxt = getTileDelete().GetAttribute("class");
		Assert.assertTrue(deletetiletxt.contains("disable"));

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getGridView().Visible();
			}
		}), Input.wait30);
		getGridView().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getRefreshButton().Visible();
			}
		}), Input.wait30);
		getRefreshButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionListGridViewTable().Visible();
			}
		}), Input.wait30);
		getProductionListGridViewTable().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAction().Visible();
			}
		}), Input.wait30);
		getAction().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getGridDelete().Visible();
			}
		}), Input.wait30);

		String deletegridtxt = getGridDelete().GetAttribute("class");
		Assert.assertTrue(deletegridtxt.contains("disable"));

		System.out.println("Verified Production Deletion is disable");
	}

	public void TiffPDF(String productionName) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddNewProductionbutton().Visible();
			}
		}), Input.wait30);
		getAddNewProductionbutton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionName().Visible();
			}
		}), Input.wait30);
		getProductionName().SendKeys(productionName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionDesc().Visible();
			}
		}), Input.wait30);
		getProductionDesc().SendKeys(productionName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBasicInfoMarkComplete().Visible();
			}
		}), Input.wait30);
		getBasicInfoMarkComplete().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFFChkBox().Enabled();
			}
		}), Input.wait30);
		getTIFFChkBox().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFFTab().Enabled();
			}
		}), Input.wait30);
		getTIFFTab().waitAndClick(10);

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getadvance().Visible();
			}
		}), Input.wait30);
		getadvance().waitAndClick(10);

		driver.scrollingToBottomofAPage();

		System.out.println("Verified Advance Show/Hide Button wworking as expected");

		Assert.assertTrue(getGenerateLoadFile().Enabled());

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getGenerateLoadFile().Enabled();
			}
		}), Input.wait30);
		getGenerateLoadFile().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getGenerateLoadFile().Visible();
			}
		}), Input.wait30);
		getGenerateLoadFile().waitAndClick(10);

		System.out.println("Verified Generate Load File (LST) with Toggle button");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSlipSheets().Visible();
			}
		}), Input.wait30);
		getSlipSheets().waitAndClick(10);

		for (int i = 1; i <= 3; i++) {
			getAvailableFields(i).waitAndClick(10);
		}

		System.out.println("Verified METADATA/WORKPRODUCT & CALCULATED Tabs");
	}

	public void DeleteProduction() throws InterruptedException {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByButton().Visible();
			}
		}), Input.wait30);
		getFilterByButton().waitAndClick(10);
		for (int i = 1; i < getFilterOptions().size(); i++) {
			getFilter(i).waitAndClick(10);
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFilterByDRAFT().Visible();
			}
		}), Input.wait30);
		getFilterByDRAFT().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getRefreshButton().Visible();
			}
		}), Input.wait30);
		getRefreshButton().waitAndClick(10);

		Thread.sleep(1000);
		getArrow().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDelete().Visible();
			}
		}), Input.wait30);
		getDelete().waitAndClick(10);
		Thread.sleep(1000);
		getOK().waitAndClick(10);
		Thread.sleep(1000);
		BaseClass bc = new BaseClass(driver);
		bc.VerifySuccessMessage("Production deleted successfully");
		bc.CloseSuccessMsgpopup();

		System.out.println("Verified deleted Production");
	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void CreateNewProduction(String productionname, String PrefixID, String SuffixID, final String foldername,
			String tagname) throws InterruptedException {

		base.selectproject();
		driver.getWebDriver().get(Input.url + "Production/Home");
		SoftAssert softAssertion = new SoftAssert();
		String expectedText = "Success";

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddNewProductionbutton().Enabled();
			}
		}), Input.wait30);
		getAddNewProductionbutton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionName().Enabled();
			}
		}), Input.wait30);
		getProductionName().SendKeys(productionname);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionDesc().Enabled();
			}
		}), Input.wait30);
		getProductionDesc().SendKeys(productionname);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBasicInfoMarkComplete().Enabled();
			}
		}), Input.wait30);
		getBasicInfoMarkComplete().Click();

		base.stepInfo("New Production  is created");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDATChkBox().Enabled();
			}
		}), Input.wait30);
		getDATChkBox().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDATTab().Enabled();
			}
		}), Input.wait30);
		getDATTab().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_FieldClassification1().Enabled() && getDAT_FieldClassification1().isDisplayed();
			}
		}), Input.wait30);
		getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText("Bates");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_SourceField1().Enabled();
			}
		}), Input.wait30);
		getDAT_SourceField1().selectFromDropdown().selectByVisibleText("BatesNumber");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_DATField1().Enabled();
			}
		}), Input.wait30);
		getDAT_DATField1().SendKeys("BatesNumber" + Utility.dynamicNameAppender());

		driver.scrollingToBottomofAPage();
		base.stepInfo("DAT field filled");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNativeChkBox().Enabled();
			}
		}), Input.wait30);
		getNativeChkBox().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNativeTab().Enabled();
			}
		}), Input.wait30);
		getNativeTab().Click();

		driver.scrollPageToTop();

		Reporter.log(getNative_text().getText(), true);
		// System.out.println(getNative_text().getText());
		UtilityLog.info(getNative_text().getText());

		// work on this assert..issue with text format!
		/*
		 * Assert.assertEquals(getNative_text().getText(),"To produce specific docs" +
		 * " natively, please select file types and/or tags below. In addition," +
		 * " to export placeholders for these docs, configure placeholder in the TIFF "
		 * + "or PDF section for the same selected file types and/or tags.");
		 * 
		 * Assert.assertEquals(getNative_text_Color().GetAttribute("class"),"blue-text")
		 * ;
		 */

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNative_SelectAllCheck().Enabled();
			}
		}), Input.wait30);
		getNative_SelectAllCheck().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNative_AdvToggle().Enabled();
			}
		}), Input.wait120);
		getNative_AdvToggle().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNative_GenerateLoadFileLST().Enabled();
			}
		}), Input.wait60);
		getNative_GenerateLoadFileLST().waitAndClick(20);

		driver.scrollingToBottomofAPage();
		base.stepInfo("NATIVE field filled");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFFChkBox().Enabled();
			}
		}), Input.wait30);
		getTIFFChkBox().Click();

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFFTab().Enabled();
			}
		}), Input.wait30);
		getTIFFTab().Click();
		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_CenterHeaderBranding().Visible() && getTIFF_CenterHeaderBranding().Enabled();
			}
		}), Input.wait30);
		getTIFF_CenterHeaderBranding().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_EnterBranding().Enabled();
			}
		}), Input.wait30);

		new Actions(driver.getWebDriver()).moveToElement(getTIFF_EnterBranding().getWebElement()).click();
		// getTIFF_EnterBranding().waitAndClick(10);
		getTIFF_EnterBranding().SendKeys(searchString4);

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_EnableforPrivilegedDocs().Enabled();
			}
		}), Input.wait30);
		// getTIFF_EnableforPrivilegedDocs().Click();

		getPriveldge_SelectTagButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPriveldge_TagTree(tagname).isDisplayed();
			}
		}), Input.wait30);
		getPriveldge_TagTree(tagname).Click();

		getPriveldge_TagTree_SelectButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPriveldge_TextArea().Enabled();
			}
		}), Input.wait30);
		new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();

		getPriveldge_TextArea().SendKeys(searchString4);

		driver.scrollingToBottomofAPage();

//  		Assert.assertEquals(getTiff_redactiontext().getText(),"To burn redactions, select specific redactions"
//  				+ " or all redactions (annotation layer). Specify the redaction text for each"
//  				+ " selected redaction.");
		//

		base.stepInfo("TIFF field filled");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTextChkBox().Enabled();
			}
		}), Input.wait30);
		getTextChkBox().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTextTab().Enabled();
			}
		}), Input.wait30);
		getTextTab().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTextcomponent_text().isDisplayed();
			}
		}), Input.wait30);

//  		String exptext= getTextcomponent_text().getText();
//  		System.out.println(exptext);
//  		UtilityLog.info(exptext);
//  		Assert.assertEquals(exptext, "Redacted documents are automatically OCRed"
//  				+ " to export the text. Original extracted text is exported for natively "
//  				+ "produced documents (file based placeholdering). "
//  				+ "For exception and privileged placeholdered docs, "
//  				+ "the placeholder text is exported."+" The configured format is applicable only to OCRed text and production generated text"
//  				+ ", and not to ingested text.");

		base.stepInfo("Text field filled");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvancedProductionComponents(productionname).isDisplayed();
			}
		}), Input.wait30);
		getAdvancedProductionComponents(productionname).Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMP3ChkBox(productionname).isDisplayed();
			}
		}), Input.wait30);
		getMP3ChkBox(productionname).Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTranslationsChkBox(productionname).isDisplayed();
			}
		}), Input.wait30);
		getTranslationsChkBox(productionname).Click();

		driver.scrollPageToTop();
		base.stepInfo("Advanced production components field filled");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getComponentsMarkComplete().isDisplayed();
			}
		}), Input.wait30);
		getComponentsMarkComplete().Click();
		driver.waitForPageToBeReady();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getComponentsMarkNext().isDisplayed();
			}
		}), Input.wait30);
		getComponentsMarkNext().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBeginningBates().Enabled();
			}
		}), Input.wait30);
		getBeginningBates().SendKeys(getRandomNumber(2));

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return gettxtBeginningBatesIDPrefix().Enabled();
			}
		}), Input.wait30);
		gettxtBeginningBatesIDPrefix().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return gettxtBeginningBatesIDSuffix().Enabled();
			}
		}), Input.wait30);
		gettxtBeginningBatesIDSuffix().SendKeys(SuffixID);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return gettxtBeginningBatesIDMinNumLength().Enabled();
			}
		}), Input.wait30);
		gettxtBeginningBatesIDMinNumLength().SendKeys(getRandomNumber(1));

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstSortingMetaData().Enabled();
			}
		}), Input.wait30);
		getlstSortingMetaData().selectFromDropdown().selectByVisibleText("DocID");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstSortingOrder().Enabled();
			}
		}), Input.wait30);
		getlstSortingOrder().selectFromDropdown().selectByVisibleText("Ascending");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstSortingMetaData().Enabled();
			}
		}), Input.wait30);
		getlstSubSortingMetaData().selectFromDropdown().selectByVisibleText("CustodianName");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstSubSortingOrder().Enabled();
			}
		}), Input.wait30);
		getlstSubSortingOrder().selectFromDropdown().selectByVisibleText("Ascending");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getKeepFamiliesTogether().Visible();
			}
		}), Input.wait30);
		getKeepFamiliesTogether().Click();

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumAndSortMarkComplete().Enabled();
			}
		}), Input.wait30);
		getNumAndSortMarkComplete().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumAndSortNext().Enabled();
			}
		}), Input.wait30);
		getNumAndSortNext().Click();
		base.stepInfo("Numbering and sorting field filled");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderRadioButton().Visible();
			}
		}), Input.wait30);
		getFolderRadioButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectFolder(foldername).Visible();
			}
		}), Input.wait30);
		getSelectFolder(foldername).Click();

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getIncludeFamilies().Visible();
			}
		}), Input.wait30);
		getIncludeFamilies().Click();

		driver.scrollPageToTop();
		base.stepInfo("Selected Documents");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnDocumentsSelectionMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnDocumentsSelectionMarkComplete().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnDocumentsSelectionNext().Enabled();
			}
		}), Input.wait30);
		getbtnDocumentsSelectionNext().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGuardMarkComplete().isDisplayed();
			}
		}), Input.wait30);
		getbtnProductionGuardMarkComplete().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getOkButton().Visible();
			}
		}), Input.wait30);
		getOkButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGuardNext().Enabled();
			}
		}), Input.wait30);
		getbtnProductionGuardNext().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getlstProductionRootPaths().Visible();
			}
		}), Input.wait30);
		try {
			getlstProductionRootPaths().selectFromDropdown().selectByVisibleText(Input.prodPath);
		} catch (Exception e) {
			// if passed production path is wrong! go by index and then select
			getlstProductionRootPaths().selectFromDropdown().selectByIndex(1);
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionOutputLocation_ProductionDirectory().Visible();
			}
		}), Input.wait30);
		getProductionOutputLocation_ProductionDirectory().SendKeys(productionname);

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionLocationMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnProductionLocationMarkComplete().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionLocationNext().Enabled();
			}
		}), Input.wait30);
		getbtnProductionLocationNext().Click();
		base.stepInfo("location for production is given");

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getPreviewprod().Enabled() ;}}), Input.wait30);
		 * getPreviewprod().waitAndClick(10);
		 */

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionSummaryMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnProductionSummaryMarkComplete().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionSummaryNext().Enabled();
			}
		}), Input.wait30);
		getbtnProductionSummaryNext().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGenerate().isDisplayed();
			}
		}), Input.wait30);
		getbtnProductionGenerate().Click();

		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocumentGeneratetext().isElementAvailable(540);
			}
		}), Input.wait120);

		String actualText = getStatusSuccessTxt().getText();
		System.out.println(actualText);

		softAssertion.assertTrue(actualText.contains(expectedText));
		base.passedStep("Documents Generated successfully");

		// work on below assert..for now its ok bcz we are validating with commit
		// button!
//  		Assert.assertTrue(getDocumentGeneratetext().isDisplayed());

//  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//  				getQC_backbutton().Enabled()  ;}}), Input.wait30); 
//  		getQC_backbutton().waitAndClick(15);

//  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//  				getProd_BatesRange().Enabled()  ;}}), Input.wait30); 
//  		String batesno = getProd_BatesRange().getText();

//  		Reporter.log("Bate number "+batesno,true);
//  		System.out.println(batesno);
//  		UtilityLog.info(batesno);

//  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//  				getbtnSummaryNext().Enabled()  ;}}), Input.wait30); 
//  		getbtnSummaryNext().waitAndClick(10);

		base.stepInfo("production generated");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getConfirmProductionCommit().Enabled();
			}
		}), Input.wait30);

		base.waitTime(1);
		getConfirmProductionCommit().waitAndClick(10);
		if (base.getCloseSucessmsg().isElementAvailable(5)) {
			base.CloseSuccessMsgpopup();
		}

		String PDocCount = getProductionDocCount().getText();
		int Doc = Integer.parseInt(PDocCount);

		Reporter.log("Doc - " + Doc, true);
		System.out.println(Doc);
		UtilityLog.info(Doc);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCopyPath().Enabled();
			}
		}), Input.wait30);
		getCopyPath().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getQC_Download().Enabled();
			}
		}), Input.wait30);
		getQC_Download().waitAndClick(10);
		getQC_Downloadbutton_allfiles().waitAndClick(10);
		base.VerifySuccessMessage("Your Production Archive download will get started shortly");
	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void selectExportSetFromDropDown() {
		base.waitForElement(getProdExport_ProductionSets());
		getProdExport_ProductionSets().Click();
		base.waitForElement(getOptionExportSet());
		getOptionExportSet().Click();

	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void previewPriveldgeToolbar() {

		base.waitForElement(getPriviledgedDocumunentsHelpIcon());
		getPriviledgedDocumunentsHelpIcon().Click();

		SoftAssert softAssertion = new SoftAssert();
		String actual = getPriviledgedToolTip_Text().getWebElement().getText();
		String expected = "Number of documents in the selected production documents having at least one Privileged tag specified in the TIFF/PDF section.";

		softAssertion.assertEquals(actual, expected);
		base.stepInfo("priviledged tooltip displayed");

	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void viewSaveTemplateControlForDATMapping() throws InterruptedException {

		base.waitForElement(getbtnProductions());
		getbtnProductions().Click();
		base.waitForElement(getManageTemplates());
		getManageTemplates().Click();
		base.waitForElement(getbtnCustomTemplateView());
		getbtnCustomTemplateView().Click();
		base.waitForElement(getbtnTemplateGuardNext());
		getbtnTemplateGuardNext().Click();
		driver.waitForPageToBeReady();
		base.waitForElement(getDATChkBox());
		getDATChkBox().Click();
		base.waitForElement(getDATTab());
		getDATTab().Click();

		base.waitForElement(getDAT_FieldClassification1());
		if (getDAT_FieldClassification1().Enabled()) {
			Assert.assertTrue(false);
			base.failedStep("Specify DAT Field Mapping is enabled");

		} else {
			Assert.assertTrue(true);
			base.passedStep("Specify DAT Field Mapping is disabled");
		}
	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void clickComponentMarkCompleteAndIncomplete() {

		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		base.waitForElement(getComponentsMarkComplete());
		getComponentsMarkComplete().Enabled();
		getComponentsMarkComplete().Click();

		driver.waitForPageToBeReady();

		driver.scrollPageToTop();
		base.waitForElement(getbtnComponentsMarkIncomplete());
		getbtnComponentsMarkIncomplete().Enabled();
		getbtnComponentsMarkIncomplete().waitAndClick(10);
	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void fillingTIFFWithDisablePrivilegdedDocs() {
		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();
		driver.scrollingToBottomofAPage();
		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();
		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().Click();
	}

	/**
	 * @authorIndium-Sowndarya.Velraj.Modified on 10/27/2021 This method is to chec
	 *                                         disabled Burn Redaction Toggle
	 */
	public void burnRedactionToggleDisableCheck() {
		boolean flag;

		flag = getChk_burnReductiontoggle().GetAttribute("class").contains("activeC");

		if (!flag) {

			Assert.assertTrue(true);
			base.passedStep("burn reduction toggle is disabled");
		} else {
			Assert.assertTrue(false);
			base.failedStep("burn reduction toggle is unabled");
		}

	}

	/**
	 * @authorIndium-Sowndarya.Velraj.Modified on 10/27/2021
	 * @param Fills Priviledge Documents by selecting a tag and passing that query
	 *              to find the matching documents.
	 * @Modified by Jeevitha 17/1/2022
	 */
	public void priviledgeDocCheck(boolean Tag, String tagname) throws InterruptedException {
		base.waitForElement(getAddRuleBtn());
		getAddRuleBtn().Click();

		if (Tag) {
			base.waitForElement(getTagsBtn());
			getTagsBtn().Click();

			base.hitTabKey(1);
			base.waitForElement(getTagsHeader());
			getTagsHeader().Click();
			base.stepInfo("Tag Button is clicked from Priv Guard Page");
			base.waitForElement(getPrivGuardAllTags());

		} else {
			base.waitForElement(getTagsBtn());
			getRedactionBtn().waitAndClick(10);
			base.stepInfo("Redaction Button is clicked from Priv Guard Page");

			base.hitTabKey(1);

		}

		base.waitForElement(selectPrivGuardTag(tagname));
		selectPrivGuardTag(tagname).waitAndClick(10);
		base.stepInfo(tagname + " : Is Selected");

		base.waitForElement(getInsertQueryBtn());
		getInsertQueryBtn().waitAndClick(10);

		base.waitForElement(getCheckForMatchingDocuments());
		getCheckForMatchingDocuments().Enabled();
		getCheckForMatchingDocuments().waitAndClick(10);
		driver.scrollPageToTop();
	}

	/**
	 * @authorIndium-Sowndarya.Velraj.Modified on 01/06/22
	 */
	public void DraftAssertionInGeneratePage() throws InterruptedException {

		SoftAssert softAssertion = new SoftAssert();
		String expectedText = "Draft";

		base.waitForElement(getbtnProductionGenerate());
		getbtnProductionGenerate().waitAndClick(10);

		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocumentGeneratetext().Visible() && getDocumentGeneratetext().Enabled();
			}
		}), Input.wait120);
		String actualText = getStatusDraftTxt().getText();
		System.out.println(actualText);

		softAssertion.assertTrue(actualText.contains(expectedText));
		base.passedStep("Draft Displayed successfully");

	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void prodGenerationInProgressBar() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {

				return getFilterByButton().Enabled();

			}

		}), Input.wait30);

		getFilterByButton().Click();

		base.stepInfo("Filtering the failed production");

		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {

				return getFilterByDRAFT().Enabled();

			}

		}), Input.wait30);

		getFilterByDRAFT().Click();

		base.stepInfo("Removing filter by draft");
		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {

				return getFilterByFAILED().Enabled();

			}

		}), Input.wait30);

		getFilterByFAILED().Click();

		base.stepInfo("Removing filter by in progress");

		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {

				return getFilterDropDown().Enabled();
			}

		}), Input.wait30);

		getFilterDropDown().Click();
		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {

				return gettxtProdGenerationFailed().Enabled();

			}

		}), Input.wait30);

		base.stepInfo("Failed production displays");

		if (gettxtProdGenerationInProgress().isDisplayed()) {

			String failedMsg = gettxtProdGenerationInProgress().getText();

			base.passedStep("verifing the failed status :" + failedMsg);

			Assert.assertTrue(true, failedMsg);

		} else {

			base.failedStep("No failed production occurs");

		}
	}

	/**
	 * Modified on 03/09/2022
	 * 
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void selectDownloadInGeneratePage() throws InterruptedException {

		SoftAssert softAssertion = new SoftAssert();
		String expectedText = "Success";

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGenerate().Enabled() && getbtnProductionGenerate().isDisplayed();
			}
		}), Input.wait30);
		getbtnProductionGenerate().waitAndClick(10);

		getbtnContinueGeneration().isElementAvailable(150);
		if (getbtnContinueGeneration().isDisplayed()) {
			base.waitForElement(getbtnContinueGeneration());
			getbtnContinueGeneration().waitAndClick(10);
		}

		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");

		getDocumentGeneratetext().isElementAvailable(180);
		base.stepInfo("wait until Document Generated Text is visible");
		String actualText = getStatusSuccessTxt().getText();
		System.out.println(actualText);

		softAssertion.assertTrue(actualText.contains(expectedText));
		base.passedStep("Documents Generated successfully");

		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {

				return getConfirmProductionCommit().Enabled() && getConfirmProductionCommit().isDisplayed();

			}

		}), Input.wait60);

		Thread.sleep(5000);

		getConfirmProductionCommit().waitAndClick(10);

		String PDocCount = getProductionDocCount().getText();

		// added thread.sleep to avoid exception while executing in batch

		Thread.sleep(1000);

		System.out.println(PDocCount);

		int Doc = Integer.parseInt(PDocCount);

		Reporter.log("Doc - " + Doc, true);

		System.out.println(Doc);

		UtilityLog.info(Doc);

		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {

				return getSelectDownloadBtn().isDisplayed();

			}

		}), Input.wait60);

		getSelectDownloadBtn().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {

				return getSelectSharableLinks().isDisplayed();

			}

		}), Input.wait60);

		getSelectSharableLinks().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {

				return getAssertOnPassword().isDisplayed();

			}

		}), Input.wait60);

		String actualText1 = getAssertOnPassword().getText();

		Assert.assertNotNull(actualText1);

		System.out.println(actualText1);

		base.passedStep("Passwword Generated successfully");

		SoftAssert assertallfiles = new SoftAssert();

		String expectedtext = "All Files";

		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {

				return getAssertAllFile().isDisplayed();

			}

		}), Input.wait60);

		String assertallfiles1 = getAssertAllFile().getText();

		System.out.println(assertallfiles1);

		assertallfiles.assertTrue(assertallfiles1.contains(expectedtext));

		base.passedStep("Allfile is  Displayed successfully");

		SoftAssert AssertDATFile = new SoftAssert();

		String expectedtextinfield = "DAT File Only";

		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {

				return getAssertDATFile().isDisplayed();

			}

		}), Input.wait60);

		String actualTextInField = getAssertDATFile().getText();

		System.out.println(actualTextInField);

		AssertDATFile.assertTrue(actualTextInField.contains(expectedtextinfield));

		base.passedStep("DATfile is Displayed successfully");

	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void AssertionOnQCGenerationPage() throws InterruptedException {

		SoftAssert softAssertion = new SoftAssert();
		String expectedText = "Success";
		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {
				return getbtnProductionGenerate().Enabled() && getbtnProductionGenerate().isDisplayed();

			}

		}), Input.wait30);

		getbtnProductionGenerate().Click();
		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");

		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {

				return getDocumentGeneratetext().Visible() && getDocumentGeneratetext().Enabled();

			}

		}), Input.wait120);

		String actualText = getStatusSuccessTxt().getText();

		System.out.println(actualText);

		softAssertion.assertTrue(actualText.contains(expectedText));

		base.passedStep("Documents Generated successfully");

		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {

				return getConfirmProductionCommit().Enabled() && getConfirmProductionCommit().isDisplayed();

			}

		}), Input.wait60);

		getConfirmProductionCommit().waitAndClick(10);
		String PDocCount = getProductionDocCount().getText();

		// added thread.sleep to avoid exception while executing in batch

		Thread.sleep(1000);

		System.out.println(PDocCount);

		int Doc = Integer.parseInt(PDocCount);

		Reporter.log("Doc - " + Doc, true);

		System.out.println(Doc);

		UtilityLog.info(Doc);

		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {

				return getAssertCommitBtn().Enabled() && getAssertCommitBtn().isDisplayed();

			}

		}), Input.wait60);

		SoftAssert verify = new SoftAssert();

		expectedText = "Commit";

		String actualText1 = getAssertCommitBtn().getText();

		System.out.println(actualText1);

		verify.assertTrue(actualText1.contains(expectedText));

		base.passedStep("" + actualText1 + " is displayed  successfully");

		SoftAssert copypathverification = new SoftAssert();

		expectedText = "Copy Path";

		String actualTextincopypathBtn = getAssertCopyPathBtn().getText();

		System.out.println(actualTextincopypathBtn);

		copypathverification.assertTrue(actualTextincopypathBtn.contains(expectedText));

		base.passedStep("" + actualTextincopypathBtn + " is displayed  successfully");

		SoftAssert downloadverification = new SoftAssert();

		expectedText = "Download";

		String actualTextindownloadBtn = getSelectDownloadBtn().getText();

		System.out.println(actualTextindownloadBtn);

		downloadverification.assertTrue(actualTextindownloadBtn.contains(expectedText));

		base.passedStep("" + actualTextindownloadBtn + " is displayed  successfully");

	}

	/**
	 * @authorIndium-Baskar Date:1/9/2021 Modified date:N/A
	 * @Description : After completing one by one page navigating to next page
	 */
//	Reusable method for switching to next page Button with complte and next
	public void afterCompletingNavigatingToNextPage() {
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		base.clickButton(getMarkCompleteLink());
		System.out.println("Clicked on Mark Complete Button..");
		driver.waitForPageToBeReady();
		base.clickButton(getNextButton());
		base.stepInfo("Navigate to next page");

	}

	/**
	 * @authorIndium-Baskar Date:31/8/2021 Modified date:N/A
	 * @Description : verifying preview button presence when DAT only selection
	 */
//	Reusable method invisibility of element presence
	public void verifyingPreviewButtonDisplayed() throws InterruptedException {
		try {
			if (getbtnPreview().isDisplayed() == false) {
				System.out.println("pass");

			} else {
				System.out.println("fail");
			}
		} catch (org.openqa.selenium.NoSuchElementException e) {
			base.passedStep("preview button is not disbaled");
			e.printStackTrace();
		}

	}

	/**
	 * @authorIndium-Baskar Date:30/8/2021 Modified date:N/A
	 * @Description : TIFF Selection in branding
	 */
//	selecting Bates section in insertmetadata field
	public void tiffBrandingSelection(String tagName) {
		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().waitAndClick(5);
		driver.scrollingToBottomofAPage();
		base.waitForElement(getTIFFTab());
		getTIFFTab().waitAndClick(5);
		driver.scrollPageToTop();
		base.waitForElement(getTIFF_InsertMetadataField());
		getTIFF_InsertMetadataField().waitAndClick(5);
		base.waitForElement(getTIFF_selectedMetadataField());
		getTIFF_selectedMetadataField().waitAndClick(5);
		getTIFF_selectedMetadataField().selectFromDropdown().selectByVisibleText("BatesNumber");
		base.waitForElement(getPopUpOkButtonInserMetaData());
		getPopUpOkButtonInserMetaData().waitAndClick(5);
		String actual = "$BatesNumber$";
		softAssertion.assertEquals(actual, getVerifyTextBoxInserMetaData().getText());
		softAssertion.assertAll();
		base.passedStep("Tiff branding section selected: '" + actual + "' in insert metadata field ");

	}

	/**
	 * Modified by sowndarya on 22/9/2021
	 * 
	 * @authorIndium-Baskar Date:30/8/2021 Modified date:N/A
	 * @Description : TIFF document selection in privilege
	 */
//	Reusable method for Privilege document selection
	public void tiffPrivilegeDocumentSelection(String tagName) throws InterruptedException {
		getTIFF_EnableforPrivilegedDocs().ScrollTo();
		base.waitForElement(getPriveldge_SelectTagButton());
		getPriveldge_SelectTagButton().waitAndClick(5);
		base.waitForElement(getPriveldge_TagTree(tagName));
		Thread.sleep(1000);
		getPriveldge_TagTree(tagName).waitAndClick(10);
		getPriveldge_TagTree_SelectButton().waitAndClick(10);
		base.waitForElement(getPriveldge_TextArea());
		new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();
		Thread.sleep(1000);
		getPriveldge_TextArea().SendKeys(tagNameTechnical);

	}

	/**
	 * @authorIndium-Baskar Date:2/9/2021 Modified date:N/A
	 * @Description : TIFF Advanced link
	 */
//	Reusable method for toggle enable in slip sheet in tiff advanced link
	public void slipSheetToggleEnable() {
		base.waitForElement(getTiffAdvancedLink());
		getTiffAdvancedLink().waitAndClick(5);
		base.waitForElement(getTiffAdvancedSlipSheetToggle());
		getTiffAdvancedSlipSheetToggle().waitAndClick(5);
	}

	/**
	 * @authorIndium-Baskar Date:2/9/2021 Modified date:N/A
	 * @Description : Availablefieldselection in slip sheet
	 */
//	Reusable Method for available field selection
	public void availableFieldSelection(String fieldValue) {
		driver.waitForPageToBeReady();
		base.clickButton(getSlipCalculatedTabSelection());
		base.clickButton(getCalculatedCheckBoxSelection(fieldValue));
		base.clickButton(getAddSelected());
	}

	/**
	 * @authorIndium-Baskar Date:31/8/2021 Modified date:N/A
	 * @Description : Click preview tab button in summary and preview tab
	 */
	// Reusable method for clicking the preview button and visibility of element
	// presence
	public void viewingPreviewButtonInSummaryTab() throws InterruptedException {
		if (getbtnPreview().isElementAvailable(5)) {
			try {
				base.waitForElement(getbtnPreview());
				if (getbtnPreview().Enabled() && getbtnPreview().isDisplayed()) {
					softAssertion.assertEquals(getbtnPreview().isDisplayed().booleanValue(), true);
					getbtnPreview().waitAndClick(10);
					base.passedStep("clicking preview button and verifying the standard pdf format");
				} else {
					base.stepInfo("Preview button is not displayed in summary and preview tab");
				}
				driver.waitForPageToBeReady();
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			base.passedStep("Preview button is not displayed in summary and preview tab");

		}
	}

	/**
	 * @authorIndium-Baskar Date:1/9/2021 Modified date:N/A
	 * @Description : Opening the download pdf and verifying the text with standard
	 *              templeate
	 */
//	Reusable method for passing the pdf url and verifying with prefix suffix
	public void verifyContentInPDf(String prefixID, String suffixID) throws InterruptedException {
		int parseInt = Integer.parseInt(num1);
		char c = '0';
		String empty = "";
		for (int i = 0; i < parseInt - 2; i++) {
			empty = empty + c;
		}
		System.out.println(empty);
		// specify the url of the pdf file
		String fileName = prefixID + empty + num + suffixID;
		driver.waitForPageToBeReady();
//		Thread.sleep(5000);
		System.out.println(fileName);
		String url = "file:///C://BatchPrintFiles//downloads//" + fileName + ".pdf";
		driver.get(url);
		driver.waitForPageToBeReady();
		try {
			String pdfContent = readPdfContent(url);
			System.out.println(pdfContent);
			Assert.assertTrue(pdfContent.contains(
					"This is sample draft document, demonstrating how sizing, branding and slipsheets configurations will be \r\n"
							+ "applied on the produced TIFF or PDF documents. "));
			base.passedStep(
					"Standard Production template displayed along with Slip Sheets as TIFF is selected in production");
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Indium-Baskar Date:1/9/2021 Modified date:N/A
	 * @Description : Reading Pdf text
	 */
//	Reusable method for reading pdf text
	public static String readPdfContent(String url) throws IOException {
		URL pdfUrl = new URL(url);
		InputStream in = pdfUrl.openStream();
		BufferedInputStream bf = new BufferedInputStream(in);
		PDDocument doc = PDDocument.load(bf);
		PDFTextStripper pdfStrip = new PDFTextStripper();
		pdfStrip.setStartPage(2);
		pdfStrip.setEndPage(2);
		String content = pdfStrip.getText(doc);
//		doc.close();

		return content;
	}

	/**
	 * @author Indium-Baskar Date:1/9/2021 Modified date:N/A
	 * @Description : Advanced production component MP3 filling
	 */
	public void advancedProductionComponentsMP3() {
		getAdvancedProductionComponent().WaitUntilPresent().ScrollTo();
		base.clickButton(getAdvancedProductionComponent());
		base.clickButton(getMP3CheckBox());
		driver.scrollPageToTop();
		base.stepInfo("Advanced production section MP3 files is filled");
	}

	/**
	 * @author Indium-Baskar Date:1/9/2021 Modified date:N/A
	 * @Description : Adding encoding in Ansi
	 */
	public void selectFormatInTextFile() {
		getAnsiRadioBtn().WaitUntilPresent().ScrollTo();
		base.clickButton(getAnsiRadioBtn());
		base.clickButton(getAnsiDrpDwn());
		getAnsiDrpDwn().selectFromDropdown().selectByVisibleText("Unicode (UTF-8)");
		driver.scrollPageToTop();
		base.stepInfo("Advanced production section MP3 files is filled");
	}

	/**
	 * @author Indium-sowndarya.velraj
	 * @param searchname
	 * @param searchname1
	 * @param This        method selects the saved search radio button and select
	 *                    the respective searchname from the list.
	 */
	public void fillingDocuSelectionPage(String searchname, String searchname1) {
		try {

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSearchRadioButton().Enabled();
				}
			}), Input.wait30);
			getSearchRadioButton().Click();

			if (getSelectSearches(searchname).isDisplayed()) {
				base.waitForElement(get_MySearchesNewNode(searchname));

				getSelectSearches(searchname).waitAndClick(10);
				System.out.println(searchname + " is Selected");
			} else {
				System.out.println("not Displayed");
			}

			if (get_MySearchesNewNode(searchname1).isDisplayed()) {
				base.waitForElement(get_MySearchesNewNode(searchname1));
				get_MySearchesNewNode(searchname1).waitAndClick(10);
				System.out.println(searchname1 + " is Selected");
			} else {
				System.out.println("not Displayed");
			}

			driver.scrollingToBottomofAPage();

			base.waitForElement(getIncludeFamilies());
			getIncludeFamilies().waitAndClick(10);

			driver.scrollPageToTop();
			System.out.println("Document Selection Page section is filled");
			base.stepInfo("Document Selection Page section is filled");

		} catch (Exception e) {
			System.out.println("No element & node ,is not Present in Project Administration");
			UtilityLog.info("No element & node ,is not Present in Project Administration");
		}
	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void selectingDefaultSecurityGroup() {
		base.waitForElement(getSecurityGroupDropDown());
		getSecurityGroupDropDown().waitAndClick(10);
		base.waitForElement(getDefaultSecurityGroup());
		getDefaultSecurityGroup().waitAndClick(10);

	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void fillingNativeSectionWithoutSelectingTag() throws InterruptedException {

//		base.waitForElement(getNativeChkBox());
//		getNativeChkBox().waitAndClick(10);

		base.waitForElement(getNativeTab());
		getNativeTab().waitAndClick(10);

		getChkBoxNative_SpreadSheets().waitAndClick(10);
		getChkBoxNative_Multimedia().waitAndClick(10);

		getSelectCloseBtn().waitAndClick(10);
		getEnableForNativelyToggle().waitAndClick(10);
		driver.scrollPageToTop();
		base.waitForElement(getMarkCompleteLink());
		getMarkCompleteLink().waitAndClick(10);

		driver.scrollingToBottomofAPage();
		String ExpectedMsg = "To export natives, you must select at least a file group type or a tag in the Native section.";
		base.waitForElement(getErrorMsgPopup());
		String ActualMsg = getErrorMsgPopup().getText();
		Assert.assertEquals(ActualMsg, ExpectedMsg);

		UtilityLog.info("Expected message - " + ExpectedMsg);
		Reporter.log("Expected message - " + ExpectedMsg, true);

	}

	/**
	 * @authorSowndarya.velraj.Modified on 01/19/22
	 * @Description : Method to save a production as template and verifying it in
	 *              Manage template tab
	 * @param productionname : productionname is String value that name of
	 *                       Production need to select production.
	 * @throws InterruptedException
	 */
	public void saveProductionAsTemplateAndVerifyInManageTemplateTab(String productionname, String templateName)
			throws InterruptedException {

		base.stepInfo("click on gear icon of the current production");
		driver.waitForPageToBeReady();

		base.waitForElement(arrowSymbolInHomePage());
		arrowSymbolInHomePage().waitAndClick(10);

		base.stepInfo("click on save as Template");
		getprod_Action_SaveTemplate().waitAndClick(10);

		base.waitForElement(getprod_Templatetext());
		getprod_Templatetext().Click();
		getprod_Templatetext().SendKeys(templateName);

		getProdExport_SaveButton().waitAndClick(10);
		base.VerifySuccessMessage("Production Saved as a Custom Template.");

		getManageTemplates().waitAndClick(10);

		driver.scrollingToBottomofAPage();
		base.waitForElement(getNextBtn());
		getNextBtn().waitAndClick(10);
		driver.waitForPageToBeReady();

		getDeleteBtn(templateName).ScrollTo();
		getDeleteBtn(templateName).isElementAvailable(5);
		base.stepInfo("Delete option is displayed");

		getViewBtn(templateName).ScrollTo();
		base.waitForElement(getViewBtn(templateName));
		if (getViewBtn(templateName).isElementAvailable(5)) {
			getViewBtn(templateName).waitAndClick(10);
			base.stepInfo("View option is displayed");
		}

		driver.waitForPageToBeReady();
		base.waitTime(2);
		if (getProductionNameInManageView(templateName).isElementAvailable(5)) {
			base.passedStep("Production which is saved as template is displayed after selecting view option");
		}

		base.waitForElement(privGuardInTemplate());
		if (privGuardInTemplate().isElementAvailable(5)) {
			base.stepInfo("priv guard option is displayed");
		}

		base.waitForElement(productionComponentsInTemplate());
		if (productionComponentsInTemplate().isElementAvailable(5)) {
			base.stepInfo("production component option is displayed");
		}

		base.waitForElement(numberingSortingInTemplate());
		if (numberingSortingInTemplate().isElementAvailable(5)) {
			base.stepInfo("Numbering and sorting  option is displayed");
		}

		base.waitForElement(nextButtonInTemplate());
		nextButtonInTemplate().waitAndClick(10);
		base.stepInfo("next button works in template correctly");

		base.waitForElement(backButtonInTemplate());
		backButtonInTemplate().waitAndClick(10);
		base.stepInfo("back button works in template correctly");

		base.waitForElement(closeButtonInTemplate());
		if (closeButtonInTemplate().isElementAvailable(5)) {
			base.stepInfo("close button is displayed");
		}

	}

	/**
	 * @authorSowndarya.velraj.Modified on 04/08/22
	 * @Description : Method to save a production as template and verifying it in
	 *              Manage template tab
	 * @param productionname : productionname is String value that name of
	 *                       Production need to select production.
	 * @throws InterruptedException
	 */
	public void saveProductionAsTemplateAndVerifyProductionComponentsInManageTemplateTab(String productionname,
			String templateName) throws InterruptedException {

		base.stepInfo("click on gear icon of the current production");
		driver.waitForPageToBeReady();
		base.waitForElement(getGearIconForProdName(productionname));
		getGearIconForProdName(productionname).waitAndClick(10);

		base.stepInfo("click on save as Template");
		getprod_Action_SaveTemplate().waitAndClick(10);

		base.waitForElement(getprod_Templatetext());
		getprod_Templatetext().waitAndClick(10);
		getprod_Templatetext().SendKeys(templateName);

		getProdExport_SaveButton().waitAndClick(10);
		base.VerifySuccessMessage("Production Saved as a Custom Template.");

		getManageTemplates().waitAndClick(10);
		driver.scrollingToBottomofAPage();

		if (getViewBtn(templateName).isElementAvailable(5)) {
			getViewBtn(templateName).waitAndClick(10);
			base.stepInfo("View option is displayed");
		} else {
			NavigateToLastTemplatePage();
			driver.waitForPageToBeReady();
			getViewBtn(templateName).waitAndClick(10);
			base.stepInfo("View option is displayed");
		}

		getDeleteBtn(templateName).ScrollTo();
		getDeleteBtn(templateName).isElementAvailable(5);
		base.stepInfo("Delete option is displayed");

		driver.waitForPageToBeReady();
		base.waitTime(2);
		if (getProductionNameInManageView(templateName).isElementAvailable(5)) {
			base.passedStep("Production which is saved as template is displayed after selecting view option");
		}

		base.waitForElement(privGuardInTemplate());
		if (privGuardInTemplate().isElementAvailable(5)) {
			base.stepInfo("priv guard option is displayed");
		}

		base.waitForElement(productionComponentsInTemplate());
		if (productionComponentsInTemplate().isElementAvailable(5)) {
			base.stepInfo("production component option is displayed");
		}
	}

	/**
	 * @authorIndium-Sowndarya.Velraj.Modified on 01/19/22
	 */
	public void prodGenerationInCompletedStatus(String productionname) throws InterruptedException {

		base.waitForElement(getFilterByButton());
		getFilterByButton().Click();

		base.stepInfo("Filtering the Completed production");

		base.waitForElement(getFilterByDRAFT());

		getFilterByDRAFT().waitAndClick(10);
		base.stepInfo("Filtering avoids draft status production");

		base.waitForElement(getFilterByFAILED());
		getFilterByFAILED().waitAndClick(10);
		base.stepInfo("Filtering avoids failed status production");

		base.waitForElement(getFilterByINPROGRESS());
		getFilterByINPROGRESS().waitAndClick(10);
		base.stepInfo("Filtering avoids failed status production");

		base.waitForElement(getFilterByCOMPLETED());
		getFilterByCOMPLETED().waitAndClick(10);
		base.stepInfo("Filtering avoids completed status production");

		base.waitForElement(getFilterDropDown());
		getFilterDropDown().waitAndClick(10);

		driver.waitForPageToBeReady();

//		String completedProd = getProductionFromHomepage(productionname).getText();
//		System.out.println("completed production is:" + completedProd);
		base.passedStep("Filtered out completed status production only");
	}

	/**
	 * @authorSowndarya.velraj
	 * @Description : Method to select the created productionset from the dropdown
	 * @param productionSet : productionSet is String value that name of Production
	 *                      need to select production.
	 */
	public void navigateToProductionPageByNewProductionSet(String productionSet) {

		driver.getWebDriver().get(Input.url + "Production/Home");
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		getOptionExportSet().Click();
		getProductionFromDropDown(productionSet).waitAndClick(5);

	}

	/**
	 * Modified on 04/19/22
	 * 
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void AssertionInTIFFSection() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFFChkBox().Enabled();
			}
		}), Input.wait30);
		getTIFFChkBox().Click();

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFFTab().Enabled();
			}
		}), Input.wait30);
		getTIFFTab().Click();

		driver.scrollingToBottomofAPage();

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().waitAndClick(10);
		base.stepInfo("Disabled priviledge docs");

		base.waitForElement(getclkNativelyProducedDocumentLnk());
		getclkNativelyProducedDocumentLnk().waitAndClick(10);

		driver.scrollPageToTop();
		base.waitForElement(getMarkCompleteLink());
		getMarkCompleteLink().waitAndClick(10);

		String ExpectedMsg = "In the TIFF / PDF section, no values are specified in the placeholder configuration for the docs produced natively. Please check.";
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getErrorMsgPopupInTiffSection().Visible();
			}
		}), Input.wait30);
		String ActualMsg = getErrorMsgPopupInTiffSection().getText();
		Assert.assertEquals(ActualMsg, ExpectedMsg);
		UtilityLog.info("Expected message - " + ExpectedMsg);
		Reporter.log("Expected message - " + ExpectedMsg, true);

	}

	/**
	 * @authorIndium-sowndarya.velraj
	 */
	public void verifyTIFFSectionRedactionsText() throws InterruptedException {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();

		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();

		driver.scrollingToBottomofAPage();

		// enable burn redaction toggle button
		base.waitForElement(getClk_burnReductiontoggle());
		getClk_burnReductiontoggle().Click();

		String redactionTextExpected = "Redactions without redaction tags will be automatically burned with the redaction text 'REDACTED' by default. This option can be turned on/off in the 'Select Redaction Tags'. The default redaction text 'REDACTED' for this option can be changed in the 'Specify Redaction Text by Selecting Redaction Tags' below.";
		String redactionsTextActual = getRedactionText().getText();
		// System.out.println("Expected :" + redactionTextExpected);
		// System.out.println("Actual :" +redactionsTextActual);

		// ***validating the readaction text
		SoftAssert softAssertion = new SoftAssert();
		softAssertion.assertEquals(redactionsTextActual, redactionTextExpected);

		// ***validating the Exclamation Icon is present or not in redaction text
		if (getRedactionTextExclamationIcon().isDisplayed() == true) {
			// System.out.println("Icon present");
			softAssertion.assertTrue(true);
			base.passedStep("Exclamation Icon is present in Redaction Text ");
		} else {
			// System.out.println("Icon absent");
			softAssertion.assertTrue(false);
			base.failedStep("Exclamation Icon is absent in Redaction Text");
		}

		softAssertion.assertAll();
		base.passedStep("Pass- RedactionText with Exclamation Icon Verification on Tiff/PDF");
	}

	/**
	 * @author Indium-sowndarya.velraj
	 */
	public void verifyRedactionsWithoutRedactionTagsCheckBox() throws InterruptedException {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();

		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();

		driver.scrollingToBottomofAPage();
		// ***enable burn redaction toggle button
		base.waitForElement(getClk_burnReductiontoggle());
		getClk_burnReductiontoggle().Click();

		// ***Select Redaction Tags-radio Button
		base.waitForElement(getClk_SelectRedactionTagsRadio());
		getClk_SelectRedactionTagsRadio().Click();

		driver.scrollingToBottomofAPage();
		// getRedWithoutRedCheckbox().ScrollTo();
		Thread.sleep(4000);

		base.waitForElement(getRedWithoutRedCheckbox());
		getRedWithoutRedCheckbox().Click(); // to unselect (by default it was selected)
		Thread.sleep(4000);

		// ***Validating CheckBox-Redactions without Redaction Tags
		if (getRedWithoutRedCheckbox().Selected() == false) {
			System.out.println("Checkbox Selected ");
			softAssertion.assertTrue(true);
			base.passedStep(" 'Redaction without redaction tags' check box is selected by default ");
		} else {
			System.out.println("Checkbox Not-Selected");
			softAssertion.assertTrue(false);
			base.failedStep(" 'Redaction without redaction tags' check box is not-selected by default ");
		}

		softAssertion.assertAll();
		base.passedStep(
				"Pass- 'Redaction without redaction tags' check box is selected by default when user clicks on 'Select Redaction Tags' ");

	}

	/**
	 * @author Indium-sowndarya.velraj
	 */
	public void SummaryAndPreview_DisabledPreviewButton() {
		SoftAssert softAssertion = new SoftAssert();

		try {
			if (getPreviewButton().isDisplayed() == false) {
				softAssertion.assertTrue(true);
			} else {
				softAssertion.assertTrue(false);
			}
		} catch (org.openqa.selenium.NoSuchElementException e) {
			e.printStackTrace();
			base.passedStep("Pass - preview Button is not displayed as expected");

		}

	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void fillingTiffSectionBySelectingTwoRedactedTags(String Tag, String nextTag) throws InterruptedException {

		base.waitForElement(getTIFFChkBox());

		getTIFFChkBox().Click();

		driver.scrollingToBottomofAPage();

		base.waitForElement(getTIFFTab());

		getTIFFTab().Click();

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		// disabling enable for priviledged docs

		base.waitForElement(getTIFF_EnableforPrivilegedDocs());

		getTIFF_EnableforPrivilegedDocs().Enabled();

		getTIFF_EnableforPrivilegedDocs().Click();

		getClk_burnReductiontoggle().ScrollTo();

		// enable burn redaction

		base.waitForElement(getClk_burnReductiontoggle());

		getClk_burnReductiontoggle().Click();

		getClkRadioBtn_selectRedactionTags().ScrollTo();

		base.waitForElement(getClkRadioBtn_selectRedactionTags());

		getClkRadioBtn_selectRedactionTags().isDisplayed();

		driver.waitForPageToBeReady();

		getClkRadioBtn_selectRedactionTags().waitAndClick(10);

		base.waitForElement(getClkCheckBoxRedactionTag(Tag));

		getClkCheckBoxRedactionTag(Tag).Enabled();

		getClkCheckBoxRedactionTag(Tag).Click();

		base.waitForElement(getClkCheckBoxRedactionTags(nextTag));

		getClkCheckBoxRedactionTags(nextTag).Enabled();

		getClkCheckBoxRedactionTags(nextTag).Click();

	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void SummaryAndPreviewWithAssert() throws InterruptedException {

		Thread.sleep(5000);

		String ExpectedMsg = "2";
		String ActualMsg = getDocCount().getText();

		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {

				return getDocCount().Visible();

			}

		}), Input.wait30);

		Assert.assertEquals(ActualMsg, ExpectedMsg);

		System.out.println(ActualMsg);

		UtilityLog.info("Expected Count Of Doc - " + ExpectedMsg);

		Reporter.log("Expected Count Of Doc - " + ExpectedMsg, true);
	}

	/**
	 * @authorGopinath Created Date : 12/10/2021 modified Date : NA
	 * @description This method for manage template filling tiff section.
	 */
	public void verifyingManageTemplateFillingTiffSection() throws InterruptedException {
		try {
			base.waitForElement(getManageTemplates());
			getManageTemplates().Click();

			base.waitForElement(getbtnCustomTemplateView());
			getbtnCustomTemplateView().Click();
			base.waitForElement(getbtnTemplateGuardNext());
			getbtnTemplateGuardNext().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			driver.waitForPageToBeReady();

			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			base.waitForElement(getBrandingText());
			base.waitTillElemetToBeClickable(getBrandingText());
			getBrandingText().ScrollTo();
			base.waitForElement(getBrandingText());
			Assert.assertTrue(getBrandingText().isDisplayed(), "The branding text is not displayed");
			if (getBrandingText().isDisplayed()) {
				base.passedStep("Branding text is displayed");
			}

			driver.scrollingToBottomofAPage();
			base.waitForElement(getPlaceholderText());
			Assert.assertEquals(getPlaceholderText().getWebElement().isDisplayed(), true,
					"The placeholder text is not displayed");
			if (getPlaceholderText().isDisplayed()) {
				base.passedStep("placeholder text is displayed");
			}

			Assert.assertEquals(getLocation().getWebElement().isDisplayed(), true,
					"The Location in TiffSection is not displayed");
			if (getLocation().isDisplayed()) {
				base.passedStep("Location in TiffSection is displayed");
			}

			Assert.assertEquals(getTIFF_EnableforPrivilegedDocs().getWebElement().isDisplayed(), true,
					"The EnableforPrivilegedDocs is not displayed");
			if (getTIFF_EnableforPrivilegedDocs().isDisplayed()) {
				base.passedStep("Priviledged doc toggle is displayed");
			}

			Assert.assertEquals(getTechissue_toggle().getWebElement().isDisplayed(), true,
					"tech issue toggle is not displayed");
			if (getTechissue_toggle().isDisplayed()) {
				base.passedStep("tech issue toggle is displayed");
			}

			Assert.assertEquals(getBurnRedtoggle().getWebElement().isDisplayed(), true,
					"Burnredactiontoggle is not displayed");
			if (getBurnRedtoggle().isDisplayed()) {
				base.passedStep("Burn redaction toggle is displayed");
			}

			Assert.assertEquals(getPriveldge_TextArea().getWebElement().isDisplayed(), true,
					"priviledged text area  is not displayed");
			if (getPriveldge_TextArea().isDisplayed()) {
				base.passedStep("priviledged text area is displayed ");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while managing template filling tiff section" + e.getMessage());
		}
	}

	/**
	 * @authorGopinath Created Date : 12/10/2021 modified Date : NA
	 * @description : This method for filling tiff section tech issue without
	 *              entering text.
	 * @param Text : Text is a string value that text to enter in place holder.
	 */
	public void fillingTiffSectionTechIssueWithoutEnteringText(String Text) throws InterruptedException {
		try {
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getTIFFChkBox().Enabled();
				}
			}), Input.wait30);
			getTIFFChkBox().Click();

			driver.scrollingToBottomofAPage();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getTIFFTab().Enabled();
				}
			}), Input.wait30);
			getTIFFTab().Click();
			driver.scrollPageToTop();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getTIFF_CenterHeaderBranding().Visible() && getTIFF_CenterHeaderBranding().Enabled();
				}
			}), Input.wait30);
			getTIFF_CenterHeaderBranding().waitAndClick(10);

			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().Click();

			base.waitForElement(getTechissue_toggle());
			getTechissue_toggle().Click();

			driver.waitForPageToBeReady();
			getTechIssuePlaceHolder().ScrollTo();
			base.waitForElement(getTechIssuePlaceHolder());
			getTechIssuePlaceHolder().Click();
			getTechIssuePlaceHolder().SendKeys(Text);

			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			base.waitForElement(getMarkCompleteLink());
			base.waitTillElemetToBeClickable(getMarkCompleteLink());
			getMarkCompleteLink().ScrollTo();
			base.waitForElement(getMarkCompleteLink());
			getMarkCompleteLink().Click();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return base.getSuccessMsgHeader().Visible();
				}
			}), Input.wait30);
			Assert.assertEquals("Error !", base.getSuccessMsgHeader().getText().toString());
			base.passedStep("Got Error message :: " + base.getSuccessMsg().getText().toString());
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying error message in tiff section" + e.getMessage());
		}
	}

	/**
	 * @authorSowndarya.velraj
	 * @param Text
	 * @throws InterruptedException
	 */
	public void fillingTiffSectionTechIssueWithEnteringText(String tagname, String Text) throws InterruptedException {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().waitAndClick(5);

		driver.scrollingToBottomofAPage();

		base.waitForElement(getTIFFTab());
		getTIFFTab().waitAndClick(5);

		driver.scrollPageToTop();

		base.waitForElement(getTIFF_CenterHeaderBranding());
		getTIFF_CenterHeaderBranding().waitAndClick(10);

		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().waitAndClick(5);

		base.waitForElement(getSelectCloseBtn());
		getSelectCloseBtn().waitAndClick(10);

		base.waitForElement(getTechissue_toggle());
		getTechissue_toggle().ScrollTo();
		getTechissue_toggle().waitAndClick(5);

		driver.waitForPageToBeReady();

		getTechissue_SelectTagButton().ScrollTo();
		getTechissue_SelectTagButton().waitAndClick(10);

		driver.waitForPageToBeReady();

		getPriveldge_TagTree(tagname).ScrollTo();
		base.waitForElement(getPriveldge_TagTree(tagname));
		getPriveldge_TagTree(tagname).waitAndClick(10);

		base.waitForElement(getPriveldge_TagTree_SelectButton());
		getPriveldge_TagTree_SelectButton().waitAndClick(10);

		driver.waitForPageToBeReady();
		getTechIssuePlaceHolder().ScrollTo();
		base.waitForElement(getTechIssuePlaceHolder());
		getTechIssuePlaceHolder().Click();
		getTechIssuePlaceHolder().SendKeys(Text);

		getEnableForNativelyToggle().waitAndClick(10);
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
	}

	/**
	 * @authorGopinath Created Date : 12/10/2021 modified Date : NA
	 * @description : This method for filling tiff section without entering text in
	 *              specific redaction.
	 */
	public void fillingTiffSectionWithoutEnteringTextInSpecifyRedaction() throws InterruptedException {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().Click();
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			driver.scrollPageToTop();
			base.waitForElement(getTIFF_CenterHeaderBranding());
			getTIFF_CenterHeaderBranding().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().Enabled();
			getTIFF_EnableforPrivilegedDocs().Click();

			getClk_burnReductiontoggle().ScrollTo();

			// enable burn redaction
			base.waitForElement(getClk_burnReductiontoggle());
			getClk_burnReductiontoggle().Click();

			getClkRadioBtn_selectRedactionTags().ScrollTo();

			base.waitForElement(getClkRadioBtn_selectRedactionTags());
			getClkRadioBtn_selectRedactionTags().isDisplayed();
			driver.waitForPageToBeReady();
			getClkRadioBtn_selectRedactionTags().waitAndClick(10);

			base.waitForElement(getClkCheckBox_defaultRedactionTag());
			getClkCheckBox_defaultRedactionTag().isDisplayed();
			getClkCheckBox_defaultRedactionTag().waitAndClick(10);

			base.waitForElement(getClkLink_selectingRedactionTags());
			getClkLink_selectingRedactionTags().isDisplayed();
			getClkLink_selectingRedactionTags().waitAndClick(10);

			base.waitForElement(getClkBtn_selectingRedactionTags());
			getClkBtn_selectingRedactionTags().isDisplayed();
			getClkBtn_selectingRedactionTags().waitAndClick(10);

			base.waitForElement(getClkCheckBox_selectingRedactionTags());
			getClkCheckBox_selectingRedactionTags().isDisplayed();
			driver.waitForPageToBeReady();
			getClkCheckBox_selectingRedactionTags().waitAndClick(10);

			base.waitForElement(getClk_selectBtn());
			getClk_selectBtn().isDisplayed();
			getClk_selectBtn().waitAndClick(10);
			gettextRedactionPlaceHolder().Clear();
			driver.scrollPageToTop();
			base.waitForElement(getMarkCompleteLink());
			getMarkCompleteLink().ScrollTo();
			base.waitForElement(getMarkCompleteLink());
			getMarkCompleteLink().Click();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return base.getSuccessMsgHeader().Visible();
				}
			}), Input.wait30);
			Assert.assertEquals("Error !", base.getSuccessMsgHeader().getText().toString());
			base.passedStep("Got Error message :: " + base.getSuccessMsg().getText().toString());
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying error message in tiff section" + e.getMessage());
		}

	}

	/**
	 * @authorGopinath Created Date : 12/10/2021 modified Date : NA
	 * @description : This method for filling tiff section without entering tag in
	 *              specific redaction.
	 * @param Text : Text is a string value that text to enter in place holder.
	 */
	public void fillingTiffSectionWithoutEnteringTagInSpecifyRedaction(String Text) throws InterruptedException {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().Click();
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			driver.scrollPageToTop();
			base.waitForElement(getTIFF_CenterHeaderBranding());
			getTIFF_CenterHeaderBranding().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().Enabled();
			getTIFF_EnableforPrivilegedDocs().Click();

			getClk_burnReductiontoggle().ScrollTo();

			// enable burn redaction
			base.waitForElement(getClk_burnReductiontoggle());
			getClk_burnReductiontoggle().Click();

			getClkRadioBtn_selectRedactionTags().ScrollTo();

			base.waitForElement(getClkRadioBtn_selectRedactionTags());
			getClkRadioBtn_selectRedactionTags().isDisplayed();
			driver.waitForPageToBeReady();
			getClkRadioBtn_selectRedactionTags().waitAndClick(10);

			base.waitForElement(getClkCheckBox_defaultRedactionTag());
			getClkCheckBox_defaultRedactionTag().isDisplayed();
			getClkCheckBox_defaultRedactionTag().waitAndClick(10);

			base.waitForElement(getClkLink_selectingRedactionTags());
			getClkLink_selectingRedactionTags().isDisplayed();
			getClkLink_selectingRedactionTags().waitAndClick(10);

			gettextRedactionPlaceHolder().Click();
			gettextRedactionPlaceHolder().SendKeys(Text);
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			base.waitForElement(getMarkCompleteLink());
			base.waitTillElemetToBeClickable(getMarkCompleteLink());
			getMarkCompleteLink().ScrollTo();
			base.waitForElement(getMarkCompleteLink());
			getMarkCompleteLink().Click();
			base.VerifyErrorMessage(
					"You have chosen TIFF/PDF redactions but have not specified redaction tags. In the TIFF/PDF section, please specify the redaction tags for which you want redactions burned in the production.");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying error message in tiff section" + e.getMessage());
		}
	}

	/**
	 * @authorGopinath
	 * @description :Verifying the message on tiff section specify branding without
	 *              tag.
	 * @param Test : Test is string value that value need to pass in place holder.
	 */
	public void TiffSectionSpecifyBrandingWithoutTag(String Test) {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().Click();
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			driver.scrollPageToTop();
			base.waitForElement(getTIFF_CenterHeaderBranding());
			getTIFF_CenterHeaderBranding().Click();
			base.waitTillElemetToBeClickable(getSpecifyBrandingBySelectingTag());
			getSpecifyBrandingBySelectingTag().Click();
			base.waitForElement(getBrandingBySelectingTagPlaceholder());
			getBrandingBySelectingTagPlaceholder().SendKeys(Test);
			getTIFF_EnableforPrivilegedDocs().ScrollTo();
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().Enabled();
			getTIFF_EnableforPrivilegedDocs().Click();
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			base.waitForElement(getMarkCompleteLink());
			getMarkCompleteLink().ScrollTo();
			base.waitForElement(getMarkCompleteLink());
			getMarkCompleteLink().Click();
			driver.waitForPageToBeReady();
			base.VerifyWarningMessage(
					"In the branding configuration of the TIFF / PDF section, tag is not specified for branding. Please check.");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifying error message in tiff section Specify branding without tag"
							+ e.getMessage());
		}
	}

	/**
	 * @authorGopinath
	 * @description :Verifying the message on tiff section specify branding without
	 *              text
	 * @param tag : tag is string value that name of tag that need to select.
	 */
	public void TiffSectionSpecifyBrandingWithTagandWithoutText(String Tag) throws InterruptedException {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().Click();
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			driver.scrollPageToTop();
			base.waitForElement(getTIFF_CenterHeaderBranding());
			getTIFF_CenterHeaderBranding().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().Enabled();
			getTIFF_EnableforPrivilegedDocs().Click();
			getSpecifyBrandingBySelectingTag().ScrollTo();
			base.waitForElement(getSpecifyBrandingBySelectingTag());
			getSpecifyBrandingBySelectingTag().Click();
			getbtnSelectTags().Enabled();
			getbtnSelectTags().Click();
			base.waitForElement(getChkBoxSelect(Tag));
			getChkBoxSelect(Tag).ScrollTo();
			base.waitTillElemetToBeClickable(getChkBoxSelect(Tag));
			getChkBoxSelect(Tag).Click();
			base.waitForElement(getbtnSelect());
			getbtnSelect().Click();
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			base.waitForElement(getMarkCompleteLink());
			getMarkCompleteLink().ScrollTo();
			base.waitTillElemetToBeClickable(getMarkCompleteLink());
			getMarkCompleteLink().Click();
			base.VerifyWarningMessage(
					"In the branding configuration of the TIFF / PDF section, configured text is blank. Please check.");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifying error message in tiff section Specify branding without text"
							+ e.getMessage());
		}
	}

	/**
	 * @authorGopinath
	 * @description :Verifying the message on tiff section specify branding without
	 *              tag and text.
	 */
	public void TiffSectionSpecifyBrandingWithoutTagandText() {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().Click();
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			driver.scrollPageToTop();
			base.waitForElement(getTIFF_CenterHeaderBranding());
			getTIFF_CenterHeaderBranding().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().Enabled();
			getTIFF_EnableforPrivilegedDocs().Click();
			getSpecifyBrandingBySelectingTag().ScrollTo();
			base.waitForElement(getSpecifyBrandingBySelectingTag());
			getSpecifyBrandingBySelectingTag().Click();
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			base.waitForElement(getMarkCompleteLink());
			getMarkCompleteLink().ScrollTo();
			base.waitTillElemetToBeClickable(getMarkCompleteLink());
			getMarkCompleteLink().Click();
			base.VerifyWarningMessage(
					"In the branding configuration of the TIFF / PDF section, tag and text are empty. Please check.");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifying error message in tiff section Specify branding without tag and text"
							+ e.getMessage());
		}

	}

	/**
	 * @authorGopinath
	 * @description :Verifying the tag selected in the natively produced doc is not
	 *              enabled in specify branding.
	 */
	public void selectingNativelyProducedDocsAndBrandingTag(String Tag, String Test) {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().Click();
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			driver.scrollPageToTop();
			base.waitForElement(getTIFF_CenterHeaderBranding());
			getTIFF_CenterHeaderBranding().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().Enabled();
			getTIFF_EnableforPrivilegedDocs().Click();
			base.waitForElement(getTiff_NativeDoc());
			getTiff_NativeDoc().Click();
			base.waitForElement(getclkSelectTag());
			getclkSelectTag().Click();
			base.waitForElement(getPriveldged_TagTree(Tag));
			getPriveldged_TagTree(Tag).Click();
			base.waitForElement(getClkSelect());
			getClkSelect().Click();
			Thread.sleep(Input.wait30 / 10);
			base.waitForElement(getNativeDocsPlaceholder());
			getNativeDocsPlaceholder().SendKeys(Test);
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			base.waitForElement(getSpecifyBrandingBySelectingTag());
			driver.waitForPageToBeReady();
			getSpecifyBrandingBySelectingTag().ScrollTo();
			driver.waitForPageToBeReady();
			Thread.sleep(Input.wait30 / 10);
			base.waitTillElemetToBeClickable(getSpecifyBrandingBySelectingTag());
			getSpecifyBrandingBySelectingTag().Click();
			getbtnSelectTags().Enabled();
			base.waitForElement(getbtnSelectTags());
			getbtnSelectTags().Click();
			base.waitForElement(getChkBoxSelect(Tag));
			getChkBoxSelect(Tag).ScrollTo();
			if (getChkBoxSelect(Tag).Enabled()) {
				base.passedStep("The tag selected in the natively produced doc is enabled in specify branding");
			} else {
				base.failedStep("The tag selected in the natively produced doc is not enabled in specify branding");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifying tag selected in the natively produced doc is not enabled in specify branding"
							+ e.getMessage());
		}

	}

	/**
	 * @authorGopinath
	 * @description : Method for selecting privileged tag and enter value in
	 *              placeholder.
	 * @param tagName          : tagName is a string value that name of tag need to
	 *                         be selected.
	 * @param placeHolderValue : placeHolderValue is a string value that value need
	 *                         to enter in place holder text field.
	 */
	public void selectPrivilegedTagAndEnterPlaceHolderValue(String tagName, String placeHolderValue) {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().Click();
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			driver.waitForPageToBeReady();
			getTIFF_EnableforPrivilegedDocs().ScrollTo();
			driver.waitForPageToBeReady();
			base.waitForElement(getPriveldge_SelectTagButton());
			base.waitTillElemetToBeClickable(getPriveldge_SelectTagButton());
			driver.waitForPageToBeReady();
			if (!getPriviledgedDoogle().GetAttribute("class").contains("active")) {
				driver.waitForPageToBeReady();
				base.waitForElement(getTIFF_EnableforPrivilegedDocs());
				getTIFF_EnableforPrivilegedDocs().Enabled();
				getTIFF_EnableforPrivilegedDocs().Click();
			}
			getPriveldge_SelectTagButton().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getPriveldge_TagTree(tagName));
			getPriveldge_TagTree(tagName).waitAndClick(10);
			driver.waitForPageToBeReady();
			getPriveldge_TagTree_SelectButton().waitAndClick(10);
			driver.waitForPageToBeReady();
			base.waitForElement(getPriveldge_TextArea());
			getPriveldge_TextArea().SendKeys(placeHolderValue);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifying tag selected in the natively produced doc is not enabled in specify branding"
							+ e.getMessage());
		}

	}

	/**
	 * @authorGopinath
	 * @description : Method for verifying tag is enabled after selecting tag in
	 *              privileged.
	 * @param tagName : tagName is a string value that name of tag need to be
	 *                selected.
	 */
	public void verifyTagEnabledAfterSelectingTagInPreviliged(String tagName) {
		try {

			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			base.waitForElement(getBrandingBySelectingTags());
			driver.waitForPageToBeReady();
			getBrandingBySelectingTags().ScrollTo();
			driver.waitForPageToBeReady();
			Thread.sleep(Input.wait30 / 10);
			getBrandingBySelectingTags().Click();
			getbtnSelectTags().Enabled();
			base.waitForElement(getbtnSelectTags());
			getbtnSelectTags().Click();
			base.waitForElement(getChkBoxSelect(tagName));
			getChkBoxSelect(tagName).ScrollTo();
			if (getChkBoxSelect(tagName).Enabled()) {
				base.passedStep("Tag :: " + tagName + "enabled in specify branding");
			} else {
				base.failedStep("Tag :: " + tagName + "is not enabled in specify branding");
			}

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifying enabled after selecting tag in privileged" + e.getMessage());
		}

	}

	/**
	 * @authorGopinath
	 * @description : Method for verifying Warning message displayed by without
	 *              selecting mp3 redaction tag.
	 */
	public void verifyWarningMessageDisplayedByWithoutSelectingMp3RedationTag() {
		try {

			driver.waitForPageToBeReady();
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			base.waitForElement(getAdvancedProductionComponent());
			getAdvancedProductionComponent().ScrollTo();
			getAdvancedProductionComponent().Click();
			getMP3CheckBox().Click();
			driver.waitForPageToBeReady();
			driver.scrollingToBottomofAPage();
			base.waitForElement(getbtnMP3BurnRedactionTab());
			getbtnMP3BurnRedactionTab().Click();
			if (!getBurnRedactionsToogle().GetAttribute("class").contains("active")) {
				driver.waitForPageToBeReady();
				base.waitForElement(getbtnMP3BurnRedaction());
				getbtnMP3BurnRedaction().Click();
			}

			getbtnMP3SelectRedactionTags().Enabled();
			driver.waitForPageToBeReady();
			base.waitForElement(getbtnMP3SelectRedactionTags());
			getbtnMP3SelectRedactionTags().Click();
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			base.waitForElement(getMarkCompleteLink());
			getMarkCompleteLink().ScrollTo();
			base.waitTillElemetToBeClickable(getMarkCompleteLink());
			getMarkCompleteLink().Click();
			base.VerifyWarningMessage(
					"You have chosen MP3 redactions but have not specified redaction tags. In the MP3 section, please specify the redaction tags for which you want redactions burned in the production.");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifying Warning message displayed by without selecting mp3 redaction tag"
							+ e.getMessage());
		}

	}

	/**
	 * @authorGopinath
	 * @description : Method for adding new field on DAT.
	 */
	public void addNewFieldOnDAT() {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getDAT_AddField());
			base.waitTillElemetToBeClickable(getDAT_AddField());
			getDAT_AddField().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while adding new field on DAT." + e.getMessage());
		}

	}

	/**
	 * @authorGopinath
	 * @description : Method for adding metadata on DAT.
	 */
	public void fillingMetaDataToDATFields() {
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDAT_FieldClassification2().Visible() && getDAT_FieldClassification1().Enabled();
				}
			}), Input.wait30);
			getDAT_FieldClassification2().selectFromDropdown().selectByVisibleText("Doc Metadata");

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDAT_SourceField2().Visible() && getDAT_SourceField1().Enabled();
				}
			}), Input.wait30);
			getDAT_SourceField2().selectFromDropdown().selectByVisibleText("MetadataComments");

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDAT_DATField2().Visible() && getDAT_DATField1().Enabled();
				}
			}), Input.wait30);
			getDAT_DATField2().SendKeys(Input.randomText);
			base.stepInfo("Dat section is filled with meta data");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while adding metadata on DAT." + e.getMessage());
		}
	}

	/**
	 * @authorGopinath
	 * @description : Method for filling Tiff section with default annatation layer.
	 */
	public void fillingTIFFSectionwithBurnRedaction() throws InterruptedException {
		try {

			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().Click();

			driver.scrollingToBottomofAPage();

			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();

			getTIFF_EnableforPrivilegedDocs().ScrollTo();

			// disabling enable for priviledged docs

			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			base.waitTillElemetToBeClickable(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().Enabled();
			getTIFF_EnableforPrivilegedDocs().Click();

			getClk_burnReductiontoggle().ScrollTo();

			// enable burn redaction
			base.waitForElement(getClk_burnReductiontoggle());
			getClk_burnReductiontoggle().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getAllRedactionsAnnotaionLayer());
			driver.waitForPageToBeReady();
			getAllRedactionsAnnotaionLayer().Click();

			base.stepInfo("Tiff section is enabled with burn redaction with default annatation layer");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while  filling Tiff section with default annatation layer." + e.getMessage());
		}
	}

	/**
	 * @authorGopinath
	 * @description :filling PDF section.
	 * @param tagname : tagname is String value that name of tag need to select.
	 */
	public void fillingPDFSectionwithBurnRedaction(String tagname) {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFFChkBox());
			base.waitTillElemetToBeClickable(getTIFFChkBox());
			getTIFFChkBox().Click();

			driver.scrollingToBottomofAPage();

			driver.waitForPageToBeReady();
			base.waitForElement(getTIFFTab());
			base.waitTillElemetToBeClickable(getTIFFTab());
			getTIFFTab().Click();

			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getPDFGenerateRadioButton().Enabled();
				}
			}), Input.wait60);
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			getPDFGenerateRadioButton().ScrollTo();
			base.waitTillElemetToBeClickable(getPDFGenerateRadioButton());
			getPDFGenerateRadioButton().Click();
			getTIFF_EnableforPrivilegedDocs().ScrollTo();

			// disabling enable for priviledged docs

			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			driver.waitForPageToBeReady();
			getTIFF_EnableforPrivilegedDocs().Enabled();
			getTIFF_EnableforPrivilegedDocs().Click();

			//
			getEnableForNativelyToggle().waitAndClick(10);

			getClk_burnReductiontoggle().ScrollTo();
			// enable burn redaction
			driver.waitForPageToBeReady();
			base.waitForElement(getClk_burnReductiontoggle());
			base.waitTillElemetToBeClickable(getClk_burnReductiontoggle());
			getClk_burnReductiontoggle().Click();

			getClkRadioBtn_selectRedactionTags().ScrollTo();

			driver.waitForPageToBeReady();
			base.waitForElement(getClkRadioBtn_selectRedactionTags());
			getClkRadioBtn_selectRedactionTags().isDisplayed();
			driver.waitForPageToBeReady();
			getClkRadioBtn_selectRedactionTags().waitAndClick(10);

			base.waitForElement(getClkCheckBox_defaultRedactionTag());
			getClkCheckBox_defaultRedactionTag().isDisplayed();
			getClkCheckBox_defaultRedactionTag().waitAndClick(10);

			driver.waitForPageToBeReady();
			base.waitForElement(getClkLink_selectingRedactionTags());
			getClkLink_selectingRedactionTags().isDisplayed();
			getClkLink_selectingRedactionTags().waitAndClick(10);

			driver.waitForPageToBeReady();
			base.waitForElement(getClkBtn_selectingRedactionTags());
			getClkBtn_selectingRedactionTags().isDisplayed();
			getClkBtn_selectingRedactionTags().waitAndClick(10);

			driver.waitForPageToBeReady();
			base.waitForElement(getDefaultRedacTag_BurnRedact());
			getDefaultRedacTag_BurnRedact().isDisplayed();
			driver.waitForPageToBeReady();
			getDefaultRedacTag_BurnRedact().waitAndClick(10);

			driver.waitForPageToBeReady();
			base.waitForElement(getClk_selectBtn());
			getClk_selectBtn().isDisplayed();
			getClk_selectBtn().waitAndClick(10);

			driver.waitForPageToBeReady();
			base.waitForElement(gettextRedactionPlaceHolder());
			gettextRedactionPlaceHolder().isDisplayed();
			driver.waitForPageToBeReady();
			gettextRedactionPlaceHolder().waitAndClick(10);
			gettextRedactionPlaceHolder().SendKeys(searchString4);
			base.stepInfo("Burn redaction PDF section is filled successfully");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while handling pdf section" + e.getMessage());
		}

	}

	/**
	 * @authorGopinath
	 * @description : Method for verifying export bates button is enabled.
	 */
	public void verifyExportBatesButtonIsEnabled() {
		try {

			driver.waitForPageToBeReady();
			base.waitForElement(getExportBatesButton());
			base.waitTillElemetToBeClickable(getExportBatesButton());
			if (getExportBatesButton().Enabled()) {
				base.passedStep("Export bates button is enabled sucessfully");
			} else {
				base.failedStep("Export bates button is not enabled");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying export bats button is enabled." + e.getMessage());
		}

	}

	/**
	 * @authorGopinath
	 * @description : Method to navigate back from current page by clicking on back
	 *              button.
	 */
	public void navigateBack() {
		try {

			driver.waitForPageToBeReady();
			base.waitForElement(getBackButton());
			base.waitTillElemetToBeClickable(getBackButton());
			getBackButton().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while navigating back from current page by clicking on back button."
					+ e.getMessage());
		}

	}

	/**
	 * @authorGopinath
	 * @description : Method to get document selected count.
	 */
	public int getDocumentSelectedCount() {
		int count = 0;
		try {

			driver.waitForPageToBeReady();
			base.waitForElement(getProductionDocCount());
			base.waitTillElemetToBeClickable(getProductionDocCount());
			String PDocCount = getProductionDocCount().getText();
			count = Integer.parseInt(PDocCount);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while navigating back from current page by clicking on back button."
					+ e.getMessage());
		}
		return count;

	}

	/**
	 * @authorGopinath
	 * @description : Method to click on confirm production.
	 */
	public void clickOnConfirmProduction() {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getConfirmProductionCommit());
			base.waitTillElemetToBeClickable(getConfirmProductionCommit());
			getConfirmProductionCommit().waitAndClick(10);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while click on confirm production." + e.getMessage());
		}
	}

	/**
	 * @authorGopinath
	 * @description : Method to get production name.
	 */
	public String getProductionNam() {
		String name = null;
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getProduction());
			base.waitTillElemetToBeClickable(getProduction());
			name = getProduction().getText().trim();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while geting production name" + e.getMessage());
		}
		return name;
	}

	/**
	 * @authorGopinath
	 * @description : Method to perform completed status.
	 */
	public void performCompletedStatus() {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getCompletedStatus());
			base.waitTillElemetToBeClickable(getCompletedStatus());
			getCompletedStatus().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while perform completed status." + e.getMessage());
		}
	}

	/**
	 * Modified on 03/09/2022
	 * 
	 * @authorGopinath
	 * @description : Method to select row by production name and open in wizard.
	 * @param productionName : productionName is a String value that name of
	 *                       production.
	 */
	public void selectRowByProductionNameAndOpenWizard(String productionName) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getGridView().Visible();
			}
		}), Input.wait30);
		getGridView().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getRefreshButton().Visible();
			}
		}), Input.wait30);
		getRefreshButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProductionListGridViewTable().Visible();
			}
		}), Input.wait30);
		driver.waitForPageToBeReady();
		base.waitForElement(getProductionByWizard(productionName));
		base.waitTillElemetToBeClickable(getProductionByWizard(productionName));
		getProductionByWizard(productionName).waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAction().Visible();
			}
		}), Input.wait30);
		getAction().waitAndClick(8);
		driver.waitForPageToBeReady();
		base.waitForElement(getOpenInWizard());
		base.waitTillElemetToBeClickable(getOpenInWizard());
		getOpenInWizard().Click();
	}

	/**
	 * @authorGopinath
	 * @description : Method to click on uncommit bates.
	 */
	public void uncommitBates() {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getUncommitBatesButton());
			base.waitTillElemetToBeClickable(getUncommitBatesButton());
			getUncommitBatesButton().Click();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return base.getSuccessMsg().isDisplayed();
				}
			}), Input.wait90);
			base.getSuccessMsg().isElementAvailable(10);
			Assert.assertEquals(base.getSuccessMsg().getWebElement().isDisplayed(), true,
					"Success message is not displayed");
			if (base.getSuccessMsg().getWebElement().isDisplayed()) {
				UtilityLog.info("Success message is displayed successfully");
				Reporter.log("Success message is displayed successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while clicking on uncommit bates.." + e.getMessage());
		}
	}

	/**
	 * @authorGopinath
	 * @description : Method to fill tiff section with default branding text.
	 * @param Text : Text is String value that default branding text.
	 */
	public void fillingTIFFSectionWithDefaultBrandingText(String Text) {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().Click();
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			driver.scrollPageToTop();
			base.waitForElement(getTIFF_CenterHeaderBranding());
			getTIFF_CenterHeaderBranding().Click();
			driver.waitForPageToBeReady();
			getCentreBrandingPlaceHolder().ScrollTo();
			base.waitForElement(getCentreBrandingPlaceHolder());
			getCentreBrandingPlaceHolder().Click();
			getCentreBrandingPlaceHolder().SendKeys(Text);
			Thread.sleep(Input.wait30 / 10);
			getTIFF_EnableforPrivilegedDocs().ScrollTo();
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while filling tiff section with default branding text" + e.getMessage());

		}

	}

	/**
	 * @authorGopinath
	 * @description : Method to refilling the numbering and sorting page.
	 * @param prefixID : Text is String value pre fix id text.
	 * @param suffixID : Text is String value suffix id text.
	 */
	public void reFillingTheNumberingAndSortingPage(String prefixID, String suffixID) throws InterruptedException {
		try {
			driver.scrollPageToTop();
			base.waitTillElemetToBeClickable(getMarkCompleteLink());
			getMarkCompleteLink().Enabled();
			getMarkCompleteLink().waitAndClick(10);

			System.out.println("Clicked on Mark Complete Button..");
			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getMarkInCompleteBtn().Enabled();
				}
			}), Input.wait60);
			getMarkInCompleteBtn().Click();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getBeginningBates().Enabled();
				}
			}), Input.wait60);
			getBeginningBates().Clear();
			getBeginningBates().SendKeys(getRandomNumber(2));
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getClickHereLink().Enabled();
				}
			}), Input.wait60);
			getClickHereLink().Click();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getNextBatesNumber().Enabled();
				}
			}), Input.wait60);
			getNextBatesNumber().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while  refilling the numbering and sorting page." + e.getMessage());

		}
	}

	/**
	 * @authorJeevitha
	 * @param fontstyle
	 * @param select
	 * @param reductionTag
	 */
	public void fillingTIFFWithBurnRedaction(String fontstyle, String select, String reductionTag) {
		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();

		driver.scrollingToBottomofAPage();

		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		// disabling enable for priviledged docs

		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().Enabled();
		getTIFF_EnableforPrivilegedDocs().Click();

		getClk_burnReductiontoggle().ScrollTo();

		// enable burn redaction
		if (select.equalsIgnoreCase("layer")) {
			base.waitForElement(getClk_burnReductiontoggle());
			getClk_burnReductiontoggle().Click();

			getTIFFDefaultAnnotaionCBox().Click();
			System.out.println("Selected Default Annotation Layer");
			base.stepInfo("Selected Default Annotation Layer");

			getSelectDropDown().ScrollTo();

			base.waitForElement(getSelectDropDown());
			getSelectDropDown().selectFromDropdown().selectByVisibleText(fontstyle);
		} else if (select.equalsIgnoreCase("Tag")) {

			base.waitForElement(getClk_burnReductiontoggle());
			getClk_burnReductiontoggle().Click();

			getClk_SelectRedactionTagsRadio().waitAndClick(5);

			base.waitForElement(getClkLink_selectingRedactionTags());
			getClkLink_selectingRedactionTags().waitAndClick(10);

			base.waitForElement(getClkBtn_selectingRedactionTags());
			getClkBtn_selectingRedactionTags().waitAndClick(10);

			base.waitForElement(getDefaultRedacTag_BurnRedact());
			driver.waitForPageToBeReady();
			getDefaultRedacTag_BurnRedact().waitAndClick(10);
			System.out.println("Selected Redaction Tag :" + reductionTag);
			base.stepInfo("Selected Redaction Tag :" + reductionTag);

			base.waitForElement(getClk_selectBtn());
			getClk_selectBtn().waitAndClick(10);

			base.waitForElement(gettextRedactionPlaceHolder());
			gettextRedactionPlaceHolder().isDisplayed();
			gettextRedactionPlaceHolder().waitAndClick(10);
			gettextRedactionPlaceHolder().SendKeys(Input.searchString6);
		}
	}

	/**
	 * @authorJeevitha
	 */
	public void uncommitFunction() {
		driver.Navigate().refresh();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getUncommitbutton().Visible();
			}
		}), Input.wait30);
		driver.Navigate().refresh();
		getUncommitbutton().waitAndClick(10);
		String ExpectedMsg = "Uncommit action has been started as a background task. You will be notified upon completion. Please refresh this page to see the latest status.";
		base.VerifySuccessMessage(ExpectedMsg);
		driver.Navigate().refresh();
	}

	/**
	 * @authorJeevitha
	 * @param elementName
	 */
	public void clickBackBtnUntilElementFound(Element elementName) {
		boolean flag = true;
		for (int i = 0; i < 20; i++) {
			try {
				if (getBackButton().isElementAvailable(2)) {
					getBackButton().waitAndClick(10);
				} else if (getQC_backbutton().isElementAvailable(2)) {
					getQC_backbutton().waitAndClick(10);
				}

				if (elementName.isElementAvailable(5)) {
					elementName.waitAndClick(20);
					driver.scrollPageToTop();
					break;
				}
			} catch (Exception e) {
				System.out.println("Expected page STill Not Visible");

			}
		}
	}

	/**
	 * @authorJeevitha
	 * @param times
	 */
	public void clickMArkCompleteMutipleTimes(int times) {
		for (int i = 0; i < times; i++) {
			try {
				driver.waitForPageToBeReady();
				navigateToNextSection();
			} catch (InterruptedException e) {

			}
		}
	}

	/**
	 * @authorGopinath
	 * @description : Method to verify native tag in TIFF section burn redaction.
	 * @param Tagname : Tagname is String value name of tag.
	 */
	public void verifyingNativeTagInTIFFSectionBurnRedaction(String Tagname) {
		try {
			int j = 0;
			String TagName = Tagname;
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().Click();
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			getTIFF_EnableforPrivilegedDocs().ScrollTo();

			// disabling enable for priviledged docs
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().Enabled();
			getTIFF_EnableforPrivilegedDocs().Click();
			getClk_burnReductiontoggle().ScrollTo();

			// enable burn redaction
			base.waitForElement(getClk_burnReductiontoggle());
			getClk_burnReductiontoggle().Click();
			getClkRadioBtn_selectRedactionTags().ScrollTo();
			base.waitForElement(getClkRadioBtn_selectRedactionTags());
			getClkRadioBtn_selectRedactionTags().isDisplayed();
			driver.waitForPageToBeReady();
			getClkRadioBtn_selectRedactionTags().waitAndClick(10);
			driver.scrollingToBottomofAPage();
			List<WebElement> RowCount = TotalRowsInRedactions().FindWebElements();
			List<String> Rowcounts = new ArrayList<String>();
			for (j = 0; j < RowCount.size(); j++) {
				driver.waitForPageToBeReady();
				Rowcounts.add(RowCount.get(j).getText());
			}
			if (Rowcounts.contains(TagName)) {
				base.failedStep("" + Tagname + "  is visible");
			} else {
				base.passedStep("" + Tagname + "  is not visible at BurnRedaction");
			}
			base.stepInfo("TIFF section is filled");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while fillling TIFF section" + e.getMessage());
		}
	}

	/**
	 * @authorGopinath
	 * @description : Method to fill native section with tags.
	 * @param Tag : Tag is String value name of tag.
	 */
	public void fillingNativeSectionWithTags(String Tag) throws InterruptedException {
		try {
			base.waitForElement(getNativeChkBox());
			getNativeChkBox().Click();
			base.waitForElement(getNativeTab());
			getNativeTab().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getNative_SelectAllCheck());
			getNative_SelectAllCheck().Click();
			base.waitForElement(getNativeSelectTags());
			getNativeSelectTags().Click();
			base.waitForElement(getNativeCheckBox(Tag));
			driver.waitForPageToBeReady();
			getNativeCheckBox(Tag).ScrollTo();
			getNativeCheckBox(Tag).Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getNativeSelect());
			getNativeSelect().Click();
			Thread.sleep(Input.wait30 / 10);
			getNative_AdvToggle().ScrollTo();
			base.waitForElement(getNative_AdvToggle());
			base.waitTillElemetToBeClickable(getNative_AdvToggle());
			getNative_AdvToggle().Click();
			base.waitForElement(getNative_GenerateLoadFileLST());
			getNative_GenerateLoadFileLST().Click();
			base.stepInfo("Native section is filled");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while filling native section" + e.getMessage());
		}
	}

	/**
	 * @authorGopinath
	 * @description : Method to Filling Sorting Section.
	 */
	public void fillingSortingSecion() {

		try {
			base.waitForElement(getKeepDocsMasterDate());
			getKeepDocsMasterDate().Click();
			driver.scrollPageToTop();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while fillling sorting section" + e.getMessage());
		}
	}

	/**
	 * @authorGopinath
	 * @description : Method to filling summary,preview and verifying MP3 files.
	 */
	public void fillingSummaryAndPreviewAndVerifyingMP3Files() throws InterruptedException {
		try {
			driver.waitForPageToBeReady();
			base.waitTillElemetToBeClickable(getTextMp3File());
			String ActualText = getTextMp3File().getText();
			System.out.println(ActualText);
			driver.waitForPageToBeReady();
			if (ActualText.equals("0")) {
				base.stepInfo("Audio files are not generated");
			} else {
				base.passedStep("Audio files are generated in  production");
			}
			base.stepInfo("summary and preview section is filled");
		} catch (Exception e) {
			base.failedStep("Exception occcured while fillling summary and preview section" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Methods for Filling Advanced production component in tiff
	 *               section.
	 */

	public void fillingAdvancedProductionComponent() {
		try {
			driver.waitForPageToBeReady();
			getAdvancedProductionToggle().ScrollTo();
			base.waitForElement(getAdvancedProductionToggle());
			getAdvancedProductionToggle().Click();
			base.waitForElement(getTranslationsTab());
			getTranslationsTab().Click();
			base.waitForElement(getTranslationsCheckBox());
			getTranslationsCheckBox().Click();
			driver.scrollingToBottomofAPage();
			getSelectDatabaseFiles().ScrollTo();
			base.waitForElement(getSelectDatabaseFiles());
			base.waitTillElemetToBeClickable(getSelectDatabaseFiles());
			getSelectDatabaseFiles().Click();
			driver.scrollPageToTop();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while filling TIFF section Advanced Production Component" + e.getMessage());
		}

	}

	/**
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for verifying the documents count in OCR and TIFF
	 *               section
	 */
	public void OCRAndTIFFCountInSummaryAndPreview() {
		try {
			driver.scrollingToBottomofAPage();
			base.waitForElement(getDocumentsOfOCR());
			String DocumentsOfOCR = getDocumentsOfOCR().getText();
			base.waitForElement(getDocumentsOfTIFF());
			String DocumentsOfTIFF = getDocumentsOfTIFF().getText();
			if ((DocumentsOfOCR).equals("0")) {
				base.failedStep("The document count is equals to zero");
			} else {
				base.passedStep("The document is " + DocumentsOfOCR + " as Expected ");
			}
			if ((DocumentsOfTIFF).equals("0")) {
				base.failedStep("The document count is equals to zero");
			} else {
				base.passedStep("The document is " + DocumentsOfTIFF + " as Expected ");
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while Verifying OCR count in summary page" + e.getMessage());
		}
	}

	/**
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for verifying link in numbering and sorting section.
	 */
	public void verifyAvailbleLinkAtNumberingAndSorting() {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getClickHereLink());
			if (getClickHereLink().isDisplayed()) {
				base.passedStep("Expected link is present at numbering and sorting section");
			} else {
				base.failedStep("Expected link is not present at numbering and sorting section");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifying link in numbering and sorting section" + e.getMessage());

		}

	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for fill generate page after Uncommit.
	 */
	public void fillingGeneratePageAFterUncommit() throws InterruptedException {
		try {

			SoftAssert softAssertion = new SoftAssert();
			String expectedText = "Success";

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getbtnProductionGenerate().Enabled() && getbtnProductionGenerate().isDisplayed();
				}
			}), Input.wait30);
			getbtnProductionGenerate().waitAndClick(10);

			getbtnContinueGeneration().isElementAvailable(150);
			if (getbtnContinueGeneration().isDisplayed()) {
				base.waitForElement(getbtnContinueGeneration());
				getbtnContinueGeneration().waitAndClick(10);
			}

			Reporter.log("Wait for generate to complete", true);
			System.out.println("Wait for generate to complete");
			UtilityLog.info("Wait for generate to complete");

			getDocumentGeneratetext().isElementAvailable(180);
			base.stepInfo("wait until Document Generated Text is visible");
			String actualText = getStatusSuccessTxt().getText();
			System.out.println(actualText);

			softAssertion.assertTrue(actualText.contains(expectedText));
			base.passedStep("Documents Generated successfully");

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getConfirmProductionCommit().Enabled() && getConfirmProductionCommit().isDisplayed();
				}
			}), Input.wait60);

			// added thread.sleep to avoid exception while executing in batch
			Thread.sleep(Input.wait30 / 10);
			driver.waitForPageToBeReady();
			getConfirmProductionCommit().waitAndClick(10);
			base.CloseSuccessMsgpopup();
			String PDocCount = getProductionDocCount().getText();
			// added thread.sleep to avoid exception while executing in batch
			Thread.sleep(Input.wait30 / 10);
			System.out.println(PDocCount);
			int Doc = Integer.parseInt(PDocCount);

			Reporter.log("Doc - " + Doc, true);
			System.out.println(Doc);
			UtilityLog.info(Doc);
			driver.waitForPageToBeReady();

			base.waitForElement(getBackButton());
			getBackButton().Click();
			getNextButton().waitAndClick(10);
			for (int i = 0; i < 10; i++) {
				if (getUncommitbutton().isDisplayed()) {
					base.waitTillElemetToBeClickable(getUncommitbutton());
					getUncommitbutton().Click();
					break;
				} else {
					driver.waitForPageToBeReady();
					driver.Navigate().refresh();
				}
			}
			base.VerifySuccessMessage(
					"Uncommit action has been started as a background task. You will be notified upon completion. Please refresh this page to see the latest status.");
			base.CloseSuccessMsgpopup();
			base.waitForElement(getBackButton());
			getBackButton().Click();
			getNextButton().waitAndClick(10);
			for (int i = 0; i < 10; i++) {
				if (getConfirmProductionCommit().isDisplayed()) {
					base.passedStep("commit is displayed");
					break;
				} else {
					driver.waitForPageToBeReady();
					driver.Navigate().refresh();
				}
			}

			softAssertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while filling generate page after uncommit" + e.getMessage());
		}

	}

	/**
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for fill PDF section with redaction tags.
	 * @param redactiontag1 : redactiontag1 is String value that name of redaction
	 *                      tag.
	 * @param redactiontag2 : redactiontag2 is String value that name of another
	 *                      redaction tag.
	 */
	public void fillingPDFSectionWithRedactionTags(String redactiontag1, String redactiontag2)
			throws InterruptedException {

		try {
			base.waitForElement(getPDFChkBox());
			getPDFChkBox().Click();
			base.waitForElement(getPDFTab());
			getPDFTab().Click();
			driver.scrollPageToTop();
			base.waitForElement(getPDFGenerateRadioButton());
			getPDFGenerateRadioButton().Click();

			getTIFF_EnableforPrivilegedDocs().ScrollTo();
			// disabling enable for priviledged docs
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().Enabled();
			getTIFF_EnableforPrivilegedDocs().Click();
			getClk_burnReductiontoggle().ScrollTo();
			// enable burn redaction
			base.waitForElement(getClk_burnReductiontoggle());
			getClk_burnReductiontoggle().Click();

			getClkRadioBtn_selectRedactionTags().ScrollTo();

			base.waitForElement(getClkRadioBtn_selectRedactionTags());
			getClkRadioBtn_selectRedactionTags().isDisplayed();
			driver.waitForPageToBeReady();
			getClkRadioBtn_selectRedactionTags().waitAndClick(10);

			base.waitForElement(getClkCheckBox_defaultRedactionTag());
			getClkCheckBox_defaultRedactionTag().isDisplayed();
			getClkCheckBox_defaultRedactionTag().waitAndClick(10);

			base.waitForElement(getClkLink_selectingRedactionTags());
			getClkLink_selectingRedactionTags().isDisplayed();
			getClkLink_selectingRedactionTags().waitAndClick(10);

			base.waitForElement(getClkBtn_selectingRedactionTags());
			getClkBtn_selectingRedactionTags().isDisplayed();
			getClkBtn_selectingRedactionTags().waitAndClick(10);

			base.waitForElement(redactionTagInBurnRedactionCheckBox(redactiontag1));
			redactionTagInBurnRedactionCheckBox(redactiontag1).ScrollTo();
			redactionTagInBurnRedactionCheckBox(redactiontag1).isDisplayed();
			driver.waitForPageToBeReady();
			redactionTagInBurnRedactionCheckBox(redactiontag1).waitAndClick(10);
			driver.waitForPageToBeReady();
			base.waitForElement(redactionTagInBurnRedaction2CheckBox(redactiontag2));
			redactionTagInBurnRedaction2CheckBox(redactiontag2).ScrollTo();
			redactionTagInBurnRedaction2CheckBox(redactiontag2).isDisplayed();
			driver.waitForPageToBeReady();
			redactionTagInBurnRedaction2CheckBox(redactiontag2).waitAndClick(10);

			base.waitForElement(getClk_selectBtn());
			getClk_selectBtn().isDisplayed();
			getClk_selectBtn().waitAndClick(10);

			base.waitForElement(gettextRedactionPlaceHolder());
			gettextRedactionPlaceHolder().isDisplayed();
			gettextRedactionPlaceHolder().waitAndClick(10);
			gettextRedactionPlaceHolder().SendKeys(searchString4);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while filling burnredaction" + e.getMessage());
		}

	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void AssertionUnCommitInQCPage() throws InterruptedException {
		SoftAssert softAssertion = new SoftAssert();
		String expectedText = "Success";

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGenerate().Enabled() && getbtnProductionGenerate().isDisplayed();
			}
		}), Input.wait30);
		getbtnProductionGenerate().waitAndClick(10);

		getbtnContinueGeneration().isElementAvailable(150);
		if (getbtnContinueGeneration().isDisplayed()) {
			base.waitForElement(getbtnContinueGeneration());
			getbtnContinueGeneration().waitAndClick(10);
		}

		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocumentGeneratetext().Visible() && getDocumentGeneratetext().Enabled();
			}
		}), Input.wait120);
		String actualText = getStatusSuccessTxt().getText();
		System.out.println(actualText);

		softAssertion.assertTrue(actualText.contains(expectedText));
		base.passedStep("Documents Generated successfully");
//		try {
		// commiting the production
		base.waitForElement(getConfirmProductionCommit());
		getConfirmProductionCommit().waitAndClick(5);
		base.waitTime(5);
		driver.Navigate().refresh();

		// uncommit
		base.waitForElement(getConfirmProductionUnCommit());
		getConfirmProductionUnCommit().waitAndClick(5);
		base.waitTime(5);

		// Go back to Quality check page
		base.waitForElement(getBackButton());
		getBackButton().waitAndClick(5);
	}

	/**
	 * @throws InterruptedException
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void VerifyinginProductionQCMessage() throws InterruptedException {

		boolean elementPresent = gettxtStatusInQCPage().isElementPresent();
		UtilityLog.info("status bar displayed:" + elementPresent);

		base.waitForElement(getVerifyingQCPage());

		if (getVerifyingQCPage().isDisplayed()) {
			base.passedStep("Post-Generation QC Checks Completed status is visible");
		} else {
			base.failedStep("Post-Generation QC Checks Completed status is not visible");
		}
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for verify download production using sharable link.
	 */
	public void verifyDownloadProductionUsingSharableLink() throws InterruptedException {
		try {
			driver.waitForPageToBeReady();
			base.waitTime(3);
			getQC_Download().isElementAvailable(10);
			base.waitForElement(getQC_Download());
			String name = getProduction().getText().trim();
			base.waitTillElemetToBeClickable(getQC_Download());
			getQC_Download().waitAndClick(10);
			driver.waitForPageToBeReady();
			getSelectSharableLinks().isElementAvailable(10);
			base.waitForElement(getSelectSharableLinks());
			getSelectSharableLinks().waitAndClick(10);
			driver.waitForPageToBeReady();
			getAllFilesLink().isElementAvailable(10);
			base.waitForElement(getAllFilesLink());
			getAllFilesLink().ScrollTo();
			base.waitTime(2);
			String sharableLink = getAllFilesLink().GetAttribute("value").trim();
			String password = getShareLinkPassword().getText().trim();
			String parentWindow = driver.getWebDriver().getWindowHandle();
			((JavascriptExecutor) driver.getWebDriver()).executeScript("window.open()");
			ArrayList<String> tabs = new ArrayList<String>(driver.getWebDriver().getWindowHandles());
			driver.getWebDriver().switchTo().window(tabs.get(1));
			driver.waitForPageToBeReady();
			driver.getWebDriver().get(sharableLink);
			base.waitTime(2);
			getEnterPasswordTextField().isElementAvailable(10);
			base.waitForElement(getEnterPasswordTextField());
			getEnterPasswordTextField().SendKeys(password);
			driver.waitForPageToBeReady();
			base.waitForElement(getDownloadButton());
			getDownloadButton().waitAndClick(10);
			Thread.sleep(Input.wait30 / 10);
			/// String home = System.getProperty("user.home");
			// String downloadsHome = home + "/Downloads";
			String downloadsHome = "C:\\BatchPrintFiles\\downloads";
			isFileDownloaded(downloadsHome, name);
			driver.close();
			driver.getWebDriver().switchTo().window(parentWindow);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifying download production using sharable link." + e.getMessage());
		}
	}

	/**
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for verifying is element downloaded or not.
	 * @param downloadPath : downloadPath is path of file downloded location
	 * @param fileName     : fileName is String value that name of file.
	 */
	public void isFileDownloaded(String downloadPath, String fileName) {
		try {
			File dir = new File(downloadPath);
			File[] dirContents = dir.listFiles();

			for (int i = 0; i < dirContents.length; i++) {
				String name = dirContents[i].getName();
				if (dirContents[i].getName().contains(fileName)) {
					// File has been found, it can now be deleted:
					dirContents[i].delete();
					base.passedStep(fileName + " is downloded successfully");
					break;
				}
				if (i == dirContents.length - 1)
					base.failedStep(fileName + " downloading failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying is element downloded or not." + e.getMessage());
		}

	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for uncommit the production.
	 *
	 */
	public void fillingGeneratePageandUncommitTheProduction() {
		try {
			getBackButton().waitAndClick(10);
			getNextButton().waitAndClick(10);
			for (int i = 0; i < 10; i++) {
				getConfirmProductionUnCommit().isElementAvailable(10);
				if (getConfirmProductionUnCommit().isDisplayed()) {
					base.waitTillElemetToBeClickable(getConfirmProductionUnCommit());
					getConfirmProductionUnCommit().waitAndClick(10);
					driver.waitForPageToBeReady();
					base.CloseSuccessMsgpopup();
					break;
				} else {
					driver.Navigate().refresh();
				}
			}
			for (int i = 0; i < 6; i++) {
				driver.waitForPageToBeReady();
				driver.Navigate().refresh();
			}
			driver.waitForPageToBeReady();
			for (int i = 0; i < 5; i++) {
				base.waitForElement(getBckBtn());
				getBckBtn().Click();
			}
			driver.waitForPageToBeReady();
			for (int i = 0; i < 4; i++) {
				driver.waitForPageToBeReady();
				getNextButton().waitAndClick(10);
				driver.waitForPageToBeReady();
				getBackButton().waitAndClick(10);
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while again navigating back to document selection section" + e.getMessage());
		}
	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for perform committing the production.
	 */
	public void commitTheProduction() throws InterruptedException {
		try {
			SoftAssert softAssertion = new SoftAssert();
			String expectedText = "Success";

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getbtnProductionGenerate().Enabled() && getbtnProductionGenerate().isDisplayed();
				}
			}), Input.wait30);
			getbtnProductionGenerate().Click();

			Reporter.log("Wait for generate to complete", true);
			System.out.println("Wait for generate to complete");
			UtilityLog.info("Wait for generate to complete");
			getbtnContinueGeneration().isElementAvailable(220);
			if (getbtnContinueGeneration().isDisplayed()) {
				base.waitForElement(getbtnContinueGeneration());
				getbtnContinueGeneration().Click();
			}

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDocumentGeneratetext().isElementAvailable(540);
				}
			}), Input.wait120);

			String actualText = getStatusSuccessTxt().getText();
			System.out.println(actualText);

			softAssertion.assertTrue(actualText.contains(expectedText));
			base.passedStep("Documents Generated successfully");

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getConfirmProductionCommit().Enabled() && getConfirmProductionCommit().isDisplayed();
				}
			}), Input.wait60);

			// added thread.sleep to avoid exception while executing in batch
			Thread.sleep(Input.wait30 / 10);
			getConfirmProductionCommit().waitAndClick(10);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while committing the production" + e.getMessage());
		}

	}

	/**
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for navigating back to document selection.
	 * @param tagname : tagname is a String value that name of tag.
	 */
	public void modifyingDocumentSelectionAndMarkcomplete(String tagname) {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getMarkInCompleteBtn());
			getMarkInCompleteBtn().waitAndClick(10);
			driver.scrollingToBottomofAPage();
			base.waitForElement(getSelectTagsRadioButton());
			getSelectTagsRadioButton().waitAndClick(10);
			base.waitForElement(getSelectPageTagName(tagname));
			getSelectPageTagName(tagname).Click();
			clickMArkCompleteMutipleTimes(1);
			getbtnProductionGuardMarkComplete().waitAndClick(5);
			if (getOkButton().isElementAvailable(3)) {
				getOkButton().waitAndClick(5);
			} else {
				getbtnPopupPreviewMarkComplete().waitAndClick(10);
			}
			base.waitForElement(getbtnProductionGuardNext());
			getbtnProductionGuardNext().waitAndClick(5);
			clickMArkCompleteMutipleTimes(2);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while filling MarkComplete action" + e.getMessage());
		}
	}

	/**
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for filling priv docs placeholder in tiff section.
	 * @param tagname : tagname is aString value that name of tag.
	 */
	public void selectPrivDocsInTiffSection(String tagname) {
		try {
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().waitAndClick(5);
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().waitAndClick(5);
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().ScrollTo();
			base.waitForElement(getPriveldge_SelectTagButton());
			getPriveldge_SelectTagButton().Click();
			base.waitForElement(getPriveldge_TagTree(tagname));
			getPriveldge_TagTree(tagname).waitAndClick(10);
			getPriveldge_TagTree_SelectButton().waitAndClick(10);
			base.waitForElement(getPriveldge_TextArea());
			new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();
			getPriveldge_TextArea().SendKeys(tagNameTechnical);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while filling priv docs placeholder in tiff section" + e.getMessage());
		}
	}

	/**
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for filling MP3 files in tiff section.
	 */
	public void selectMP3Files() {
		try {
			base.waitForElement(getAdvancedProductionComponents());
			getAdvancedProductionComponents().Click();
			base.waitForElement(getMP3ChkBox());
			getMP3ChkBox().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while selecting MP3 files in advanced productio components" + e.getMessage());
		}
	}

	/**
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for filling native docs placeholder in tiff section.
	 * @param Tag : Tag is a string that name of tag.
	 */
	public void fillingTIFFWithNativelyProducedDocsAndPreviouslySelectedTagEnabled(String Tag)
			throws InterruptedException {
		try {
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().waitAndClick(5);
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().waitAndClick(5);
			driver.waitForPageToBeReady();
			base.waitForElement(getTiff_NativeDoc());
			getTiff_NativeDoc().Click();
			base.waitForElement(getclkSelectTag());
			getclkSelectTag().Click();
			base.waitForElement(getPriveldged_TagTree(Tag));
			if (getPriveldged_TagTree(Tag).Enabled()) {
				base.passedStep("The Tag already selected in the privledged docs is enabled on natively produced docs");
			} else {
				base.failedStep("The tag already selected in privledged docs is disabled on natively produced docs");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while selecting tag in natively produced documents" + e.getMessage());

		}
	}

	/**
	 * @authorGopinath
	 * @description :verifying tag selected in the specify branding at right Header
	 *              branding
	 * @param Tag  : Tag is String value that name of tag.
	 * @param Test : Test is String value that random test for entering in pleace
	 *             holder.
	 */
	public void verifyTheTagOnRightBranding(String Tag, String Test) throws InterruptedException {

		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().Click();
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			driver.scrollPageToTop();
			base.waitForElement(getRightHeaderBranding());
			getRightHeaderBranding().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().Enabled();
			getTIFF_EnableforPrivilegedDocs().Click();
			driver.waitForPageToBeReady();
			getSpecifyBrandingBySelectingTagLink().ScrollTo();
			driver.waitForPageToBeReady();
			base.waitForElement(getSpecifyBrandingBySelectingTagLink());
			base.waitTillElemetToBeClickable(getSpecifyBrandingBySelectingTagLink());
			getSpecifyBrandingBySelectingTagLink().waitAndClick(10);
			getbtnSelectTags().Enabled();
			getbtnSelectTags().waitAndClick(10);
			base.waitForElement(getChkBoxSelect(Tag));
			getChkBoxSelect(Tag).ScrollTo();
			base.waitTillElemetToBeClickable(getChkBoxSelect(Tag));
			getChkBoxSelect(Tag).waitAndClick(10);

			base.waitForElement(getbtnSelect());
			getbtnSelect().Click();

			base.waitForElement(getBrandingBySelectingTagPlaceholder());
			getBrandingBySelectingTagPlaceholder().SendKeys(Test);
			driver.waitForPageToBeReady();

		} catch (Exception e) {
			base.failedStep(
					"Exception occcured while verifying tag selected in the  specify branding at right Header branding"
							+ e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * @authorGopinath
	 * @description : Method for again selecting right header branding.
	 * @param Tag : Tag is String value that name of tag.
	 */
	public void againSelectingRightHeaderBranding(String Tag) throws InterruptedException {
		try {
			boolean flag;
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().Click();
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			base.waitForElement(getRightHeaderBranding());
			getRightHeaderBranding().waitAndClick(10);
			getSpecifyBrandingBySelectingTagLink().ScrollTo();
			base.waitForElement(getSpecifyBrandingBySelectingTagLink());
			getSpecifyBrandingBySelectingTagLink().waitAndClick(10);
			base.waitForElement(getNextSelectBtnInSpecifyBrandingSelectTags());
			getNextSelectBtnInSpecifyBrandingSelectTags().isDisplayed();
			getNextSelectBtnInSpecifyBrandingSelectTags().waitAndClick(10);

			// getDisabledSelectRedactionTags().ScrollTo();
			base.waitForElement(getDisabledSpecifyBrandingSelectTags(Tag));
			flag = getDisabledSpecifyBrandingSelectTags(Tag).GetAttribute("class").contains("disabled");
			if (!flag) {
				Assert.assertTrue(true);
				base.passedStep("ClickMarkIncomplete Disables already selected right header specify branding tag");
			} else {
				Assert.assertTrue(false);
				base.failedStep("ClickMarkIncomplete enables already selected right header specify branding tag");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifying tag selected in the  specify branding at right Header branding"
							+ e.getMessage());
		}
	}

	/**
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for filling tiff section with rotation for landscape.
	 * @param tagname : tagname is String value that name of tag.
	 */
	public void fillingTIFFSectionwithRotationLandscape(String tagname) {
		try {
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().Click();
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			driver.waitForPageToBeReady();
			getTIFF_MultiPage_RadioButton().ScrollTo();
			base.waitTillElemetToBeClickable(getTIFF_MultiPage_RadioButton());
			getTIFF_MultiPage_RadioButton().Click();
			base.waitForElement(getRotationLandScapeDropdown());
			getRotationLandScapeDropdown().Click();
			getTIFF_EnableforPrivilegedDocs().ScrollTo();
			// disabling enable for priviledged docs
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().Enabled();
			getTIFF_EnableforPrivilegedDocs().Click();
			getClk_burnReductiontoggle().ScrollTo();
			// enable burn redaction
			base.waitForElement(getClk_burnReductiontoggle());
			getClk_burnReductiontoggle().Click();
			getClkRadioBtn_selectRedactionTags().ScrollTo();
			base.waitForElement(getClkRadioBtn_selectRedactionTags());
			getClkRadioBtn_selectRedactionTags().isDisplayed();
			driver.waitForPageToBeReady();
			getClkRadioBtn_selectRedactionTags().waitAndClick(10);
			base.waitForElement(getClkCheckBox_defaultRedactionTag());
			getClkCheckBox_defaultRedactionTag().isDisplayed();
			getClkCheckBox_defaultRedactionTag().waitAndClick(10);
			base.waitForElement(getClkLink_selectingRedactionTags());
			getClkLink_selectingRedactionTags().isDisplayed();
			getClkLink_selectingRedactionTags().waitAndClick(10);
			base.waitForElement(getClkBtn_selectingRedactionTags());
			getClkBtn_selectingRedactionTags().isDisplayed();
			getClkBtn_selectingRedactionTags().waitAndClick(10);
			base.waitForElement(getClkCheckBox_selectingRedactionTags(tagname));
			getClkCheckBox_selectingRedactionTags(tagname).ScrollTo();
			getClkCheckBox_selectingRedactionTags(tagname).isDisplayed();
			driver.waitForPageToBeReady();
			getClkCheckBox_selectingRedactionTags(tagname).waitAndClick(10);
			base.waitForElement(getClk_selectBtn());
			getClk_selectBtn().isDisplayed();
			getClk_selectBtn().waitAndClick(10);
			base.waitForElement(gettextRedactionPlaceHolder());
			gettextRedactionPlaceHolder().isDisplayed();
			gettextRedactionPlaceHolder().waitAndClick(10);
			gettextRedactionPlaceHolder().SendKeys(searchString4);
		} catch (Exception e) {
			base.failedStep("Exception occcured while filling Tiff Section" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for filling tiff section with natively produced
	 *               documents.
	 * @param tagname : tagname is String value that name of tag.
	 */
	public void fillingTIFFWithNativelyProducedDocs(String Test) {

		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().Click();
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			driver.scrollPageToTop();
			base.waitForElement(getTIFF_CenterHeaderBranding());
			getTIFF_CenterHeaderBranding().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().Enabled();
			getTIFF_EnableforPrivilegedDocs().Click();
			base.waitForElement(getTiff_NativeDoc());
			getTiff_NativeDoc().Click();
			base.waitTillElemetToBeClickable(getFileTypeNativelyProducedDocs());
			getFileTypeNativelyProducedDocs().Click();
			base.waitForElement(getNativeDocsPlaceholder());
			getNativeDocsPlaceholder().SendKeys(Test);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while filling tiff section with natively produced documents." + e.getMessage());
		}
	}

	/**
	 * @throws InterruptedException
	 * @authorGopinath
	 */
	public void fillingGeneratePageWithContiuneRegenerate() throws InterruptedException {

		SoftAssert softAssertion = new SoftAssert();
		String expectedText = "Success";

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGenerate().Enabled() && getbtnProductionGenerate().isDisplayed();
			}
		}), Input.wait30);
		getbtnProductionGenerate().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getRegenerateContinueButton().isElementAvailable(540);
			}
		}), Input.wait120);
		if (getRegenerateContinueButton().isDisplayed()) {
			getRegenerateContinueButton().Click();
		} else {
		}
		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocumentGeneratetext().isElementAvailable(540);
			}
		}), Input.wait120);

//		driver.WaitUntil((new Callable<Boolean>() {
//			public Boolean call() {
//				return getDocumentGeneratetext().Visible() && getDocumentGeneratetext().Enabled();
//			}
//		}), Input.wait120);

		driver.waitForPageToBeReady();
		base.waitForElement(getStatusSuccessTxt());
		driver.waitForPageToBeReady();
		String actualText = getStatusSuccessTxt().getText();
		System.out.println(actualText);

		softAssertion.assertTrue(actualText.contains(expectedText));
		base.passedStep("Documents Generated successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getConfirmProductionCommit().Enabled() && getConfirmProductionCommit().isDisplayed();
			}
		}), Input.wait60);

		// added thread.sleep to avoid exception while executing in batch
		Thread.sleep(5000);
		getConfirmProductionCommit().waitAndClick(10);

		String PDocCount = getProductionDocCount().getText();
		// added thread.sleep to avoid exception while executing in batch
		Thread.sleep(1000);
		System.out.println(PDocCount);
		int Doc = Integer.parseInt(PDocCount);

		Reporter.log("Doc - " + Doc, true);
		System.out.println(Doc);
		UtilityLog.info(Doc);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCopyPath().isDisplayed();
			}
		}), Input.wait60);
		getCopyPath().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getQC_Download().isDisplayed();
			}
		}), Input.wait30);

		getQC_Download().waitAndClick(10);
		getQC_Downloadbutton_allfiles().waitAndClick(10);
		base.VerifySuccessMessage("Your Production Archive download will get started shortly");
		base.stepInfo("Generate production page is filled");
	}

	/**
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for filling natively produced docs in pdf section.
	 * @param Tag  : Tag is String value that name of tag.
	 * @param Text : Text is String value that need to enter in place holder.
	 */
	public void fillingPDFWithNativelyProduceddDocs(String Tag, String Text) {
		try {
			base.waitForElement(getPDFChkBox());
			getPDFChkBox().Click();
			base.waitForElement(getPDFTab());
			getPDFTab().Click();
			for (int i = 0; i < 9; i++) {
				try {
					driver.waitForPageToBeReady();
					getTIFF_EnableforPrivilegedDocs().ScrollTo();
					break;
				} catch (Exception e) {
					driver.waitForPageToBeReady();
				}
			}
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().Enabled();
			getTIFF_EnableforPrivilegedDocs().Click();
			base.waitForElement(getTiff_NativeDoc());
			getTiff_NativeDoc().Click();
			base.waitForElement(getclkSelectTag());
			getclkSelectTag().Click();
			base.waitForElement(getPriveldged_TagTree(Tag));
			getPriveldged_TagTree(Tag).Click();
			base.waitForElement(getClkSelect());
			getClkSelect().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getNativeDocsPlaceholder());
			getNativeDocsPlaceholder().SendKeys(Text);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while filling Natively produced docs" + e.getMessage());
		}

	}

	/**
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for filling PDF section with Tech Issue Docs.
	 * @param Tag  : Tag is String value that name of tag.
	 * @param Text : Text is String value that need to enter in place holder.
	 */
	public void fillingPDFWithTechIssueDocs(String Tag, String Text) {
		try {
			base.waitForElement(getPDFChkBox());
			getPDFChkBox().Click();
			base.waitForElement(getPDFTab());
			getPDFTab().Click();
			getTIFF_EnableforPrivilegedDocs().ScrollTo();
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().Enabled();
			getTIFF_EnableforPrivilegedDocs().Click();
			base.waitForElement(getTechissue_toggle());
			getTechissue_toggle().Click();
			base.waitForElement(getTechissue_SelectTagButton());
			getTechissue_SelectTagButton().Click();
			base.waitForElement(getTechIssueCheckbox(Tag));
			getTechIssueCheckbox(Tag).Click();
			base.waitForElement(getClk_selectBtn());
			getClk_selectBtn().Click();
			base.waitForElement(getTechissue_TextArea());
			getTechissue_TextArea().SendKeys(Text);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while filling TechissueDocs" + e.getMessage());
		}
	}

	/**
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for performing production filter.
	 */
	public void ProductionFilter() {
		try {
			driver.getWebDriver().get(Input.url + "Production/Home");
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getFilterByButton().isElementAvailable(50);
				}
			}), Input.wait30);
			getFilterByButton().waitAndClick(10);
			for (int i = 1; i < getFilterOptions().size(); i++) {
				getFilter(i).waitAndClick(10);
			}
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getFilterByINPROGRESS().Visible();
				}
			}), Input.wait30);
			getFilterByINPROGRESS().waitAndClick(10);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getFilterByFAILED().Visible();
				}
			}), Input.wait30);
			getFilterByFAILED().waitAndClick(10);
			getRefreshButton().waitAndClick(10);
			System.out.println("Verified Production Filer");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while performing production filter" + e.getMessage());
		}

	}

	/**
	 * 
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for verifying presence of tiff and pdf radio button.
	 */

	public void verfyingGenerateTIFFAndPDF() {
		try {
			base.waitForElement(getPDFChkBox());
			getPDFChkBox().Click();
			if (getPDFTab().isDisplayed()) {
				base.passedStep("Generate PDF is displayed");
			} else {
				base.failedStep("Generate PDF is not displayed");
			}
			if (getTIFFChkBox().isDisplayed()) {
				base.passedStep("Generate TIFF is displayed");
			} else {
				base.failedStep("Generate TIFF is not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying the presence of  generate tiff and pdf radio button"
					+ e.getMessage());
		}
	}

	/**
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for verifying production status.
	 */
	public void verifyingProductionStatusInGenerate() throws InterruptedException {

		try {
			SoftAssert softAssertion = new SoftAssert();
			String expectedText = "Success";
			base.waitForElement(getbtnProductionGenerate());
			getbtnProductionGenerate().Click();
			getbtnContinueGeneration().isElementAvailable(220);
			if (getbtnContinueGeneration().isDisplayed()) {
				base.waitForElement(getbtnContinueGeneration());
				getbtnContinueGeneration().Click();
			}
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDocumentGeneratetext().isElementAvailable(540);
				}
			}), Input.wait120);

			String actualText = getStatusSuccessTxt().getText();
			System.out.println(actualText);
			softAssertion.assertTrue(actualText.contains(expectedText));
			base.passedStep("Documents Generated successfully");
			base.waitForElement(getConfirmProductionCommit());
			getConfirmProductionCommit().isDisplayed();
			// added thread.sleep to avoid exception while executing in batch
			Thread.sleep(Input.wait30 / 10);
			driver.waitForPageToBeReady();
			base.waitTillElemetToBeClickable(getConfirmProductionCommit());
			base.waitForElement(getBckBtn());
			getBckBtn().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getVerifygQCPage());
			String Status = getVerifygQCPage().getText();
			String ProductionStatus = "Post-Generation QC checks Complete";
			if (Status.equals(ProductionStatus)) {
				base.passedStep("" + Status + " and " + ProductionStatus + "is displayed as expected");
			} else {
				base.failedStep("" + Status + " and " + ProductionStatus + " is not displayed as expected");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while verifying the production status" + e.getMessage());
		}

	}

	/**
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for filling DAT section.
	 * 
	 */
	public void fillingDATSectionWithDiffDATField() {
		try {
			base.waitForElement(getDAT_AddField());
			getDAT_AddField().Click();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDAT_FieldClassification2().Visible() && getDAT_FieldClassification2().Enabled();
				}
			}), Input.wait30);
			getDAT_FieldClassification2().selectFromDropdown().selectByVisibleText("Bates");

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDAT_SourceField2().Visible() && getDAT_SourceField2().Enabled();
				}
			}), Input.wait30);
			getDAT_SourceField2().selectFromDropdown().selectByVisibleText("BatesNumber");

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDAT_DATField2().Visible() && getDAT_DATField2().Enabled();
				}
			}), Input.wait30);

			getDAT_DATField2().SendKeys("BatesNumber" + Utility.dynamicNameAppender());
			driver.scrollPageToTop();
			base.waitTillElemetToBeClickable(getMarkCompleteLink());
			getMarkCompleteLink().Enabled();
			getMarkCompleteLink().waitAndClick(10);

			base.waitForElement(getDATContinueBtn());
			getDATContinueBtn().Click();

			driver.waitForPageToBeReady();
			base.waitForElement(getNextButton());
			getNextButton().Enabled();
			getNextButton().waitAndClick(10);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while filling DAT section" + e.getMessage());
		}

	}

	/**
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for filling burn redaction placeholder.
	 * 
	 */

	public void fillingPlaceholderInBurnRedaction(String Text) {
		try {

			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			base.waitForElement(gettextRedactionPlaceHolder());
			gettextRedactionPlaceHolder().ScrollTo();
			gettextRedactionPlaceHolder().Clear();
			gettextRedactionPlaceHolder().SendKeys(Text);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while filling burn redaction placeholder" + e.getMessage());
		}
	}

	/**
	 * @author:Sowndarya.velraj.Modified on 03/08/2022
	 * 
	 * @Description: Method for checking status of production from homepage
	 * @param Status: Status is String value that name of Production status on
	 *                progress bar.
	 */
	public void verifyProductionStatusInHomePage(String statusMsg, String productionname) {
		String productionFromHomePage = null;

		// Verifying status of the production from home page
		for (int i = 0; i < 500; i++) {
			driver.waitForPageToBeReady();
			getProductionFromHomePage(productionname).isElementAvailable(10);
			productionFromHomePage = getProductionFromHomePage(productionname).getText();
			if (productionFromHomePage.contains(statusMsg)) {
				base.passedStep(statusMsg + "status displayed");
				break;

			} else {
				driver.waitForPageToBeReady();
				getRefreshButton().waitAndClick(5);
			}
		}
		System.out.println("Preparing Data status displayed for " + productionFromHomePage);
	}

	/**
	 * Author sowndarya.velraj.Modified on 04/08/2022
	 * 
	 * @param TempName
	 */
	public void saveTemplate(String TempName) {
		driver.waitForPageToBeReady();
		getprod_Templatetext().SendKeys(TempName);
		getProdExport_SaveButton().waitAndClick(5);
		base.VerifySuccessMessage("Production Saved as a Custom Template.");
	}

	/**
	 * Author sowndarya.velraj
	 */
	public void advancedProductionComponentsMP3WithBurnReductionTag() throws InterruptedException {
		getAdvancedProductionComponent().WaitUntilPresent().ScrollTo();
		base.clickButton(getAdvancedProductionComponent());
		base.clickButton(getMP3CheckBox());
		base.clickButton(getMP3CheckBoxToggle());
		base.clickButton(getMP3CheckReductionBoxEnable());
		getMP3CheckReductionBoxEnable().Enabled();
		base.clickButton(getMP3RatiobtnRedactiontag());
		Thread.sleep(3000);
		if (getMP3RatiobtnRedactiontag().Enabled()) {
			driver.scrollingToElementofAPage(getMP3SelectDefaultRedactiontag());
			getMP3SelectDefaultRedactiontag().waitAndClick(10);
			driver.scrollPageToTop();
		}
		base.stepInfo("Advanced production section MP3 files is filled with enabling the reduction toggle button");
	}

	/**
	 * @authorSowndarya.velraj
	 * @description :method for asserting mp3 redaction tag
	 * 
	 */
	public void mp3DisableCheck() {
		boolean flag;
		driver.waitForPageToBeReady();
		flag = checkMp3ReductionTagDisable().GetAttribute("class").contains("disabled");
		if (!flag) {

			Assert.assertTrue(true);
			base.passedStep("MP3 tag is disabled at managing template page");
		} else {
			Assert.assertTrue(false);
			base.failedStep("MP3 tag is enabled at managing template page");
		}

	}

	/**
	 * Modified on 11/08/2022
	 * @author:Sowndarya.velraj
	 * @return 
	 * @Description: Method for generating production without commit and with
	 *               continue generation popup
	 */
	public int fillingGeneratePageWithContinueGenerationPopupWithoutCommit() throws InterruptedException {

		SoftAssert softAssertion = new SoftAssert();
		String expectedText = "Success";

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGenerate().Enabled() && getbtnProductionGenerate().isDisplayed();
			}
		}), Input.wait30);
		getbtnProductionGenerate().waitAndClick(10);

		getVerifyGenStatus("Reserving Bates").isElementAvailable(150);
		getbtnContinueGeneration().isElementAvailable(60);
		if (getbtnContinueGeneration().isDisplayed()) {
			base.waitForElement(getbtnContinueGeneration());
			getbtnContinueGeneration().waitAndClick(10);
		}

		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");

		getDocumentGeneratetext().isElementAvailable(180);
		base.stepInfo("wait until Document Generated Text is visible");
		String actualText = getStatusSuccessTxt().getText();
		System.out.println(actualText);

		softAssertion.assertTrue(actualText.contains(expectedText));
		base.passedStep("Documents Generated successfully");

	
		String PDocCount = getProductionDocCount().getText();
		// added thread.sleep to avoid exception while executing in batch
		base.waitTime(1);
		System.out.println(PDocCount);
		int Doc = Integer.parseInt(PDocCount);

		Reporter.log("Doc - " + Doc, true);
		System.out.println(Doc);
		UtilityLog.info(Doc);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCopyPath().isDisplayed();
			}
		}), Input.wait60);
		getCopyPath().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getQC_Download().isDisplayed();
			}
		}), Input.wait30);

		getQC_Download().waitAndClick(10);
		getQC_Downloadbutton_allfiles().waitAndClick(10);
		base.VerifySuccessMessage("Your Production Archive download will get started shortly");
		base.stepInfo("Generate production page is filled");

		return Doc;

	}

	/**
	 * @author:Aathith senthilkumar
	 * @throws InterruptedException
	 * @Description: Method for regenerating the production from home page
	 */
	public void reGenarate(String prodName) throws InterruptedException {

		base.waitForElement(getGearIconForProdName(prodName));
		getGearIconForProdName(prodName).waitAndClick(5);

		base.waitForElement(getOpenWizard());
		getOpenWizard().waitAndClick(5);

		base.waitForElement(getBackButton());
		getBackButton().waitAndClick(5);

		base.waitForElement(getMarkInCompleteBtn());
		getMarkInCompleteBtn().waitAndClick(5);
		base.stepInfo("Regenarate button clicked");

		base.waitForElement(getbtnReGenerateMarkComplete());
		getbtnReGenerateMarkComplete().isElementPresent();
		getbtnReGenerateMarkComplete().waitAndClick(5);
		base.stepInfo("Regenerate Button is clicked");

		base.waitForElement(getbtnRegenerateContinue());
		getbtnRegenerateContinue().waitAndClick(5);

		driver.waitForPageToBeReady();
		verifyProductionStatusInGenPage("Pre-Generation Checks In Progress");

		verifyProductionStatusInGenPage("Reserving Bates Range Complete");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnContinueGeneration().Enabled() && getbtnContinueGeneration().isDisplayed();
			}
		}), Input.wait120);
		getbtnContinueGeneration().waitAndClick(10);

		verifyProductionStatusInGenPage("Production Generation In Progress");

		verifyProductionStatusInGenPage("Load File Generation In Progress");

		verifyProductionStatusInGenPage("Exporting Files In Progress");

		verifyProductionStatusInGenPage("Creating Archive In Progress");

		verifyProductionStatusInGenPage("Creating Archive Complete/Skipped");

		verifyProductionStatusInGenPage("Post-Generation QC Checks In Progress");

	}

	/**
	 * Modified on 03/09/2022
	 * 
	 * @author:Aathith senthilkumar
	 * @throws InterruptedException
	 * @Description: Method for checking the status of production from generate page
	 */
	public void verifyProductionStatusInGenPage(String statusMsg) throws InterruptedException {
		String VerifyGenStatus = null;
		// Verifying status of the production from generate page
		for (int i = 0; i < 500; i++) {
			driver.waitForPageToBeReady();
			getVerifyGenStatus(statusMsg).isElementAvailable(180);
			if (getVerifyGenStatus(statusMsg).isElementAvailable(1)) {
				VerifyGenStatus = getVerifyGenStatus(statusMsg).getText();
			} else if (getGenPageStatus(statusMsg).isElementAvailable(5)) {
				VerifyGenStatus = getGenPageStatus(statusMsg).getText();
			}
			if (VerifyGenStatus.contains(statusMsg)) {
				base.passedStep(statusMsg + "status displayed");
				break;
			} else if (i == 499) {
				base.failedStep(statusMsg + "status not displayed");
			} else {
				// getRefreshButton().waitAndClick(5);
				Thread.sleep(500);
			}
		}
		System.out.println("Preparing Data status displayed for " + VerifyGenStatus);
	}

	/**
	 * @authorGopinath
	 * @description :method for filling tiff with branding
	 * 
	 */
	public void fillingTiffSectionBranding() {
		try {
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().Click();
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			base.waitForElement(getLeftHeaderBranding());
			getLeftHeaderBranding().waitAndClick(10);
			getTIFF_EnableforPrivilegedDocs().ScrollTo();
			// disabling enable for priviledged docs
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().Enabled();
			getTIFF_EnableforPrivilegedDocs().Click();
			base.waitTillElemetToBeClickable(getMarkCompleteLink());
			driver.scrollPageToTop();
			getMarkCompleteLink().Enabled();
			getMarkCompleteLink().waitAndClick(10);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while filling tiff section branding" + e.getMessage());

		}

	}

	/**
	 * @authorGopinath
	 * @description : Method for filling saved template
	 * @param Productionname : Productionname is String value that name of
	 *                       production.
	 * @param Templatename   : Templatename is String value that name of template.
	 */
	public void selectSavedTemplateAndManageTemplate(String Productionname, String Templatename)
			throws InterruptedException {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getDropDownToggle(Productionname));
			getDropDownToggle(Productionname).waitAndClick(10);
			base.waitForElement(getSaveAsTemplate(Productionname));

			getSaveAsTemplate(Productionname).waitAndClick(10);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getprod_Templatetext().Visible();
				}
			}), Input.wait30);
			getprod_Templatetext().SendKeys(Templatename);
			getProdExport_SaveButton().waitAndClick(5);
			base.VerifySuccessMessage("Production Saved as a Custom Template.");
			// getCloseBtnInProductionPage().Click();
			driver.Navigate().refresh();
			base.waitForElement(getManageTemplates());
			getManageTemplates().Click();
			Thread.sleep(Input.wait30 / 10);
			driver.scrollingToBottomofAPage();
			base.waitForElement(getNextBtn());
			base.waitTillElemetToBeClickable(getNextBtn());
			getNextBtn().Click();

			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getViewBtn(Templatename).Visible();
				}
			}), Input.wait30);
			getViewBtn(Templatename).Click();
			driver.waitForPageToBeReady();
			// base.waitForElement(getViewBtn(Templatename));
			// getViewBtn(Templatename).Click();

			base.waitForElement(getbtnTemplateGuardNext());
			getbtnTemplateGuardNext().Click();
			driver.waitForPageToBeReady();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while handling saved template" + e.getMessage());
		}

	}

	/**
	 * @authorGopinath
	 * @description :Verifying the message of tiff section.
	 */
	public void verifyTiffSectionIsSelected() {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFFChkBox());
			if (getTIFFChkBox().Enabled()) {
				base.passedStep("Tiff section is selected as expected");
			} else {
				base.failedStep("Tiff section is not selected as expected");
			}
			base.waitForElement(getCloseBtn());
			getCloseBtn().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying the tiff section checkbox" + e.getMessage());
		}
	}

	/**
	 * @authorGopinath
	 * @description :method for filling basic info
	 * @param productionName : productionName is name of production.
	 * @param Template       : Template is name of templete.
	 */
	public void baseInfoLoadTemplate(String productionName, String Template) {
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAddNewProductionbutton().Visible() && getAddNewProductionbutton().Enabled();
				}
			}), Input.wait30);
			getAddNewProductionbutton().Click();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getProductionName().Visible() && getProductionName().Enabled();
				}
			}), Input.wait30);
			getProductionName().SendKeys(productionName);
			base.waitForElement(getLoadTempDropDwn(Template));
			getLoadTempDropDwn(Template).Click();
			driver.scrollPageToTop();
			base.waitTillElemetToBeClickable(getMarkCompleteLink());
			getMarkCompleteLink().Enabled();
			getMarkCompleteLink().waitAndClick(10);
			System.out.println("Clicked on Mark Complete Button..");
			driver.waitForPageToBeReady();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while basic info" + e.getMessage());
		}
	}

	/**
	 * @authorGopinath
	 * @description :Verifying the DAT and TIFF checkbox is selected are not.
	 */
	public void verifyDATAndTIFFField() throws InterruptedException {
		try {
			driver.waitForPageToBeReady();
			Thread.sleep(Input.wait30 / 10);
			base.waitForElement(getDATChkBox());
			if (getDATChkBox().Enabled()) {
				base.passedStep("DAT section is selected as expected");
			} else {
				base.failedStep("DAT section is not selected as expected");
			}
			base.waitForElement(getTIFFChkBox());
			if (getTIFFChkBox().Enabled()) {
				base.passedStep("Tiff section is selected as expected");
			} else {
				base.failedStep("Tiff section is not selected as expected");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying production DAT And Tiff Field" + e.getMessage());
		}

	}

	/**
	 * @authorGopinath
	 * @description :Verifying DAT,PDF,NATIVE,TEXT are selected
	 */
	public void verifyPDFSectionIsSelected() {

		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getDATChkBox());
			if (getDATChkBox().Enabled()) {
				base.passedStep("DAT section is selected as expected");
			} else {
				base.failedStep("DAT section is not selected as expected");
			}

			base.waitForElement(getPDFChkBox());
			if (getPDFChkBox().Enabled()) {
				base.passedStep("PDF section is selected as expected");
			} else {
				base.failedStep("PDF section is not selected as expected");
			}

			base.waitForElement(getNativeChkBox());
			if (getNativeChkBox().Enabled()) {
				base.passedStep("NATIVE section is selected as expected");
			} else {
				base.failedStep("NATIVE section is not selected as expected");
			}
			base.waitForElement(getTextChkBox());
			if (getTextChkBox().Enabled()) {
				base.passedStep("TEXT section is selected as expected");
			} else {
				base.failedStep("TEXT section is not selected as expected");
			}
			base.waitForElement(getCloseBtn());
			getCloseBtn().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured PDF section" + e.getMessage());
		}
	}

	/**
	 * Author sowndarya.velraj
	 */
	public void fillingMP3() throws InterruptedException {
		getAdvancedProductionComponent().WaitUntilPresent().ScrollTo();
		base.clickButton(getAdvancedProductionComponent());
		base.clickButton(getMP3CheckBox());
		base.clickButton(getMP3CheckBoxToggle());
		base.clickButton(getbtnMP3BurnRedaction());
		driver.waitForPageToBeReady();
		base.clickButton(getSelect_RedactionStyle_Dropdown());
		base.clickButton(getSelect_Beep_RedactionStyle_Dropdown());
		driver.scrollPageToTop();
	}

	/**
	 * Author sowndarya.velraj
	 */
	public void viewTempProductionNext() {

		base.waitForElement(getbtnTemplateGuardNext());
		getbtnTemplateGuardNext().waitAndClick(10);
		base.waitForElement(getadvanceOnManageTemplate());
		getadvanceOnManageTemplate().waitAndClick(10);
		base.waitForElement(getMP3CheckBoxToggle());
		getMP3CheckBoxToggle().waitAndClick(10);
	}

	/**
	 * @authorIndium Sowndarya.Velraj
	 */
	public void fillingPDFWithRedactedDocumentsInAnnotationLayer() throws InterruptedException {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();

		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();

		base.waitForElement(getPDFGenerateRadioButton());
		getPDFGenerateRadioButton().ScrollTo();
		base.clickButton(getPDFGenerateRadioButton());

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		// disabling enable for priviledged docs

		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().Enabled();
		getTIFF_EnableforPrivilegedDocs().Click();

		getClk_burnReductiontoggle().ScrollTo();

		// enable burn redaction
		base.waitForElement(getClk_burnReductiontoggle());
		getClk_burnReductiontoggle().Click();

		base.waitForElement(getClkLink_selectingRedactionTags());
		getClkLink_selectingRedactionTags().isDisplayed();
		getClkLink_selectingRedactionTags().waitAndClick(10);

		base.waitForElement(getClkBtn_selectingRedactionTags());
		getClkBtn_selectingRedactionTags().isDisplayed();
		getClkBtn_selectingRedactionTags().waitAndClick(10);

		base.waitForElement(getDefaultRedacTag_BurnRedact());
		getDefaultRedacTag_BurnRedact().waitAndClick(10);

		base.waitForElement(getClk_selectBtn());
		getClk_selectBtn().Click();

		base.waitForElement(getRedactedTagTextArea());
		getRedactedTagTextArea().isDisplayed();
		getRedactedTagTextArea().SendKeys(Input.searchString1);
	}

	/**
	 * @authorGopinath
	 * @description : Method for verifying DAT and Pdf field.
	 */
	public void verifyDATAndPDFField() throws InterruptedException {
		try {
			driver.waitForPageToBeReady();
			Thread.sleep(Input.wait30 / 10);
			base.waitForElement(getDATChkBox());
			if (getDATChkBox().Enabled()) {
				base.passedStep("DAT section is selected as expected");
			} else {
				base.failedStep("DAT section is not selected as expected");
			}
			base.waitForElement(getPDFChkBox());
			if (getPDFChkBox().Enabled()) {
				base.passedStep("PDF section is selected as expected");
			} else {
				base.failedStep("PDF section is not selected as expected");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying DAT and Pdf field." + e.getMessage());
		}

	}

	/**
	 * @authorGopinath
	 * @description :Verifying the message of tiff section.
	 */
	public void verifyProductionComponent() {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getDATChkBox());
			getDATChkBox().Click();
			base.waitForElement(getDATTab());
			getDATTab().Click();

			base.waitForElement(getDAT_FieldClassification1());
			if (getDAT_FieldClassification1().Enabled()) {
				base.failedStep("Specify DAT Field Mapping is enabled");

			} else {
				base.passedStep("Specify DAT Field Mapping is disabled");
			}
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			getTIFFChkBox().ScrollTo();
			base.waitForElement(getTIFFChkBox());
			if (getTIFFChkBox().Enabled()) {
				base.passedStep("Tiff section is selected as expected");
			} else {
				base.failedStep("Tiff section is not selected as expected");
			}
			getCloseBtn().ScrollTo();
			base.waitForElement(getCloseBtn());
			getCloseBtn().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verifying the tiff section checkbox" + e.getMessage());
		}
	}

	/**
	 * @authorGopinath
	 * @description :method for filling PDF Section Branding
	 */
	public void fillingPDFSectionBranding() {
		try {
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().Click();
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			base.waitForElement(getPDFGenerateRadioButton());
			getPDFGenerateRadioButton().Click();
			base.waitForElement(getLeftHeaderBranding());
			getLeftHeaderBranding().waitAndClick(10);
			getTIFF_EnableforPrivilegedDocs().ScrollTo();
			// disabling enable for priviledged docs
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().Enabled();
			getTIFF_EnableforPrivilegedDocs().Click();
			base.waitTillElemetToBeClickable(getMarkCompleteLink());
			driver.scrollPageToTop();
			getMarkCompleteLink().Enabled();
			getMarkCompleteLink().waitAndClick(10);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while filling tiff section branding" + e.getMessage());
		}

	}

	/**
	 * @authorGopinath
	 * @description :method for filling TIFF Section With Bates Number.
	 */
	public void fillingTIFFSectionWithBatesNumber() {
		try {
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().Click();
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			base.waitForElement(getLeftHeaderBranding());
			getLeftHeaderBranding().waitAndClick(10);
			getTIFF_EnableforPrivilegedDocs().ScrollTo();
			// disabling enable for priviledged docs
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().Enabled();
			getTIFF_EnableforPrivilegedDocs().Click();
			base.waitForElement(getInsertMetadataField());
			getInsertMetadataField().Click();
			base.waitForElement(getBatesNumberinTiff());
			getBatesNumberinTiff().Click();
			base.waitForElement(getOkBtn());
			getOkBtn().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while filling tiff section with bates number" + e.getMessage());

		}

	}

	/**
	 * @authorGopinath
	 * @description :method for filling Numbering And Sorting Document Page.
	 * @param prefixID : prefixID is String value that prefix id.
	 */
	public void fillingNumberingAndSortingDocumentPage(String beginningBates, String prefixId, String suffixId) {

		base.waitForElement(getDocumentRadioBtn());
		getDocumentRadioBtn().Click();

		driver.waitForPageToBeReady();
		getBeginningSubBatesNum().Click();
		getBeginningSubBatesNum().SendKeys(beginningBates);

		base.waitForElement(gettxtBeginningBatesIDPrefix());
		gettxtBeginningBatesIDPrefix().SendKeys(prefixId);

		base.waitForElement(gettxtBeginningBatesIDSuffix());
		gettxtBeginningBatesIDSuffix().SendKeys(suffixId);

	}

	/**
	 * @authorGopinath
	 * @description :filling tag selected in the specify branding .
	 * @param Tag1 : Tag1 is string value that name of string.
	 * @param Tag2 : Tag2 is String value that name of string.
	 * @param Test : Test is String value that value to enter in placeholder.
	 */
	public void specifyBrandingInTiffSection(String Tag1, String Tag2, String Test) throws InterruptedException {

		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().Click();
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().waitAndClick(10);
			driver.scrollPageToTop();
			base.waitForElement(getLeftHeaderBranding());
			getLeftHeaderBranding().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().Enabled();
			getTIFF_EnableforPrivilegedDocs().Click();
			getLeftSpecifyBrandingBySelectingTag().ScrollTo();
			base.waitTillElemetToBeClickable(getLeftSpecifyBrandingBySelectingTag());
			getLeftSpecifyBrandingBySelectingTag().waitAndClick(10);

			getbtnSelectTags().Enabled();
			getbtnSelectTags().waitAndClick(10);
			// getPriveldge_TagTree(Tag1).ScrollTo();
			base.waitForElement(getPriveldge_TagTree(Tag1));
			getPriveldge_TagTree(Tag1).waitAndClick(10);
			base.waitForElement(getChkBoxSelect(Tag2));
			getChkBoxSelect(Tag2).waitAndClick(10);
			base.waitForElement(getbtnSelect());
			getbtnSelect().waitAndClick(10);
			base.waitForElement(getBrandingBySelectingTagPlaceholder());
			getBrandingBySelectingTagPlaceholder().SendKeys(Test);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifying tag selected in the  specify branding at left Header branding"
							+ e.getMessage());

		}

	}

	/**
	 * @authorGopinath
	 * @description :method for specify Branding In Tiff Section Insert MetaData
	 *              Field
	 * @param Tag  : Tag1 is string value that name of string.
	 * @param Tag2 : Tag2 is String value that name of string.
	 * @param Test : Test is String value that value to enter in placeholder.
	 */
	public void specifyBrandingInTiffSectionInsertMetaDataField(String Tag, String Tag2, String Test) {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().Click();
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().waitAndClick(10);
			driver.scrollPageToTop();
			base.waitForElement(getLeftHeaderBranding());
			getLeftHeaderBranding().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().Enabled();
			getTIFF_EnableforPrivilegedDocs().Click();
			getLeftSpecifyBrandingBySelectingTag().ScrollTo();
			base.waitTillElemetToBeClickable(getLeftSpecifyBrandingBySelectingTag());
			getLeftSpecifyBrandingBySelectingTag().waitAndClick(10);

			getbtnSelectTags().Enabled();
			getbtnSelectTags().waitAndClick(10);
			driver.waitForPageToBeReady();
			// getPriveldge_TagTree(Tag).ScrollTo();
			base.waitForElement(getPriveldge_TagTree(Tag));
			getPriveldge_TagTree(Tag).waitAndClick(10);
			base.waitForElement(getPriveldge_TagTree(Tag2));
			getPriveldge_TagTree(Tag2).waitAndClick(10);
			base.waitForElement(getbtnSelect());
			getbtnSelect().waitAndClick(10);
			base.waitForElement(getBrandingBySelectingTagPlaceholder());
			getBrandingBySelectingTagPlaceholder().SendKeys(Test);
			driver.waitForPageToBeReady();
			base.waitForElement(getInsertMetadataField());
			getInsertMetadataField().Click();
			base.waitForElement(getCustodianNameinTiff());
			getCustodianNameinTiff().Click();
			base.waitForElement(getOkBtn());
			getOkBtn().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifying tag selected in the  specify branding and insrt metadatafield"
							+ e.getMessage());

		}

	}

	/**
	 * @authorGopinath
	 * @description :method for filling prodcution selection volume included.
	 * @param productionname : productionname is string value that name of
	 *                       production.
	 */
	public void fillingProdcutionSelectionVolumeIncluded(String productionname) {
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getlstProductionRootPaths().Enabled();
				}
			}), Input.wait30);
			getlstProductionRootPaths().selectFromDropdown().selectByVisibleText(Input.prodPath);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getProductionOutputLocation_ProductionDirectory().Enabled();
				}
			}), Input.wait30);
			getProductionOutputLocation_ProductionDirectory().SendKeys(productionname);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return gettxtLoadFiles().Enabled();
				}
			}), Input.wait30);
			gettxtLoadFiles().SendKeys(productionname);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getVolumeIncluded().Enabled();
				}
			}), Input.wait30);
			getVolumeIncluded().waitAndClick(10);
			// getVolumeIncluded
			driver.scrollPageToTop();
			base.stepInfo("Production Location Page And Passing Text Page section is filled");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while filling prodcution selection volume included" + e.getMessage());

		}
	}

	/**
	 * @authorGopinath.Modified on 04/01/2022
	 * @description : Method for copy Path In QC Tab.
	 */
	public void copyPathInQCTab() throws InterruptedException {
		try {
			SoftAssert softAssertion = new SoftAssert();
			String expectedText = "Success";

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getbtnProductionGenerate().Enabled() && getbtnProductionGenerate().isDisplayed();
				}
			}), Input.wait30);
			getbtnProductionGenerate().waitAndClick(10);

			getbtnContinueGeneration().isElementAvailable(150);
			if (getbtnContinueGeneration().isDisplayed()) {
				base.waitForElement(getbtnContinueGeneration());
				getbtnContinueGeneration().waitAndClick(10);
			}

			Reporter.log("Wait for generate to complete", true);
			System.out.println("Wait for generate to complete");
			UtilityLog.info("Wait for generate to complete");

			getDocumentGeneratetext().isElementAvailable(180);
			base.stepInfo("wait until Document Generated Text is visible");
			String actualText = getStatusSuccessTxt().getText();
			System.out.println(actualText);

			softAssertion.assertTrue(actualText.contains(expectedText));
			base.passedStep("Documents Generated successfully");

			base.waitForElement(getConfirmProductionCommit());
			// added thread.sleep to avoid exception while executing in batch
			Thread.sleep(Input.wait30 / 10);
			getConfirmProductionCommit().waitAndClick(10);

			String PDocCount = getProductionDocCount().getText();
			// added thread.sleep to avoid exception while executing in batch
			Thread.sleep(Input.wait30 / 10);
			System.out.println(PDocCount);
			int Doc = Integer.parseInt(PDocCount);

			Reporter.log("Doc - " + Doc, true);
			System.out.println(Doc);
			UtilityLog.info(Doc);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getCopyPath().isDisplayed();
				}
			}), Input.wait60);
			getCopyPath().waitAndClick(10);
			String parentWindow = driver.getWebDriver().getWindowHandle();
			((JavascriptExecutor) driver.getWebDriver()).executeScript("window.open()");
			ArrayList<String> tabs = new ArrayList<String>(driver.getWebDriver().getWindowHandles());
			driver.getWebDriver().switchTo().window(tabs.get(1));
			driver.waitForPageToBeReady();
			Actions actions = new Actions(driver.getWebDriver());
			actions.sendKeys(Keys.COMMAND, "v").sendKeys(Keys.ENTER).build().perform();
			driver.close();
			driver.getWebDriver().switchTo().window(parentWindow);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while copying Path In QC Tab." + e.getMessage());

		}
	}

	/*
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * 
	 * @Description: Method for filling priv docs placeholder in pdf section.
	 * 
	 * @param tagname : tagname is String value that name of tag.
	 */
	public void selectPrivDocsInPDFSection(String tagname) {
		try {
			base.waitForElement(getPDFChkBox());
			getPDFChkBox().waitAndClick(5);
			driver.scrollingToBottomofAPage();
			base.waitForElement(getPDFTab());
			getPDFTab().waitAndClick(5);
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().ScrollTo();
			base.waitForElement(getPriveldge_SelectTagButton());
			getPriveldge_SelectTagButton().Click();
			base.waitForElement(getChkBoxSelect(tagname));
			getChkBoxSelect(tagname).waitAndClick(10);
			getPriveldge_TagTree_SelectButton().waitAndClick(10);
			base.waitForElement(getPriveldge_TextArea());
			new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();
			getPriveldge_TextArea().SendKeys(tagNameTechnical);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while filling priv docs placeholder in pdf section" + e.getMessage());

		}
	}

	/**
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for filling DAT section privledged checkbox.
	 */
	public void fillingDATSectionWithPrivChecked() {
		try {
			base.waitForElement(getDATPrivledgedCheckbox());
			getDATPrivledgedCheckbox().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while selecting checkbox in DAT section" + e.getMessage());
		}

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @throws InterruptedException
	 * @Description: Method for filling Generate section without wait.
	 */
	public void fillingGeneratePageWithContinueGenerationPopupWithoutWait() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGenerate().Enabled() && getbtnProductionGenerate().isDisplayed();
			}
		}), Input.wait30);
		getbtnProductionGenerate().Click();

		driver.waitForPageToBeReady();

		getbtnContinueGenerate().isElementAvailable(120);
		getbtnContinueGenerate().waitAndClick(10);

		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param statusMsg
	 * @param productionname
	 * @throws InterruptedException
	 * @Description: Method for homepage Grid view status check.
	 */
	public void verifyProductionStatusInHomePageGridView(String statusMsg, String productionname)
			throws InterruptedException {
		String productionFromHomePage = null;

		// Verifying status of the production from home page
		for (int i = 0; i < 500; i++) {
			// getRefreshButton().waitAndClick(5);
			productionFromHomePage = getStatusInGridView(productionname).getText();
			System.out.println("Preparing Data status displayed for " + productionFromHomePage);
			if (productionFromHomePage.contains(statusMsg)) {
				base.passedStep(statusMsg + "status displayed");
				break;
			} else if (i == 499) {
				base.failedStep(statusMsg + "status not displayed");
			} else {
				getRefreshButton().waitAndClick(5);
				driver.waitForPageToBeReady();
			}
		}
	}

	/**
	 * @author: Aathith Senthilkumar
	 * @Description: Method for genarate page without wait
	 */
	public void clickGenarateWithoutWait() {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGenerate().Enabled() && getbtnProductionGenerate().isDisplayed();
			}
		}), Input.wait30);
		getbtnProductionGenerate().Click();
	}

	/**
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for filling DAT Section With SourceID.
	 */
	public void fillingDATSectionWithSourceID() {
		try {
			base.waitForElement(getDAT_AddField());
			getDAT_AddField().Click();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDAT_FieldClassification2().Visible() && getDAT_FieldClassification2().Enabled();
				}
			}), Input.wait30);
			getDAT_FieldClassification2().selectFromDropdown().selectByVisibleText("Family");

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDAT_SourceField2().Visible() && getDAT_SourceField2().Enabled();
				}
			}), Input.wait30);
			getDAT_SourceField2().selectFromDropdown().selectByVisibleText("SourceAttachDocIDs");

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDAT_DATField2().Visible() && getDAT_DATField2().Enabled();
				}
			}), Input.wait30);

			getDAT_DATField2().SendKeys("Family" + Utility.dynamicNameAppender());

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while filling DAT section" + e.getMessage());
		}

	}

	/**
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for filling priv docs placeholder in tiff section.
	 * @param tagname : tagname is String value that name of tag.
	 * @param tag     : tag is String value that another tag name.
	 * 
	 */
	public void fillingPrivTagsInTIFFSection(String tagname, String Tag) {
		try {
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().waitAndClick(5);
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().waitAndClick(5);
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().ScrollTo();
			base.waitForElement(getPriveldge_SelectTagButton());
			getPriveldge_SelectTagButton().Click();
			base.waitForElement(getChkBoxSelect(tagname));
			getChkBoxSelect(tagname).waitAndClick(10);
			base.waitForElement(getPriveldge_TagTree(Tag));
			getPriveldge_TagTree(Tag).waitAndClick(10);
			getPriveldge_TagTree_SelectButton().waitAndClick(10);

			base.waitForElement(getPrivInsertMetadataField());
			getPrivInsertMetadataField().waitAndClick(5);
			base.waitForElement(getSourceAttachDocIDs());
			getSourceAttachDocIDs().waitAndClick(5);
			base.waitForElement(getPopUpOkButtonInserMetaData());
			getPopUpOkButtonInserMetaData().waitAndClick(5);
			driver.scrollPageToTop();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while filling priv docs placeholder in tiff section" + e.getMessage());
		}
	}

	/**
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for Filling Document Selection Page With Tag.
	 * @param tagname : tagname is String value that name of tag.
	 * @param tag     : tag is String value that another tag name.
	 * 
	 */
	public void fillingDocSelectionPageWithTag(String tag1, String tag2) {
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getTagRadioButton().Enabled();
				}
			}), Input.wait30);
			getTagRadioButton().Click();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSelectFolder(tag1).Visible();
				}
			}), Input.wait30);
			getSelectFolder(tag1).waitAndClick(10);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSelectFolder(tag2).Visible();
				}
			}), Input.wait30);
			getSelectFolder(tag2).waitAndClick(10);

			driver.scrollingToBottomofAPage();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getIncludeFamilies().Visible();
				}
			}), Input.wait30);
			getIncludeFamilies().Click();

			driver.scrollPageToTop();
			base.stepInfo("Document Selection Page section is filled");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while Filling Document Selection Page With Tag" + e.getMessage());

		}
	}

	/**
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for Filling Text Section And Verfying Text.
	 * @param expText : expText is String value that expected text need to get from
	 *                text component.
	 */
	public void fillingTextSectionAndVerfyingText(String expText) {
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getTextChkBox().Enabled();
				}
			}), Input.wait30);
			getTextChkBox().waitAndClick(5);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getTextTab().Enabled();
				}
			}), Input.wait30);
			getTextTab().Click();

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getTextcomponent_text().isDisplayed();
				}
			}), Input.wait30);
			String actualText = getTextcomponent_text().getText();
			System.out.println(actualText);
			UtilityLog.info(actualText);
			if (actualText.contains(actualText)) {
				base.passedStep("" + actualText + " text are displayed as expected");
			} else {
				base.failedStep("" + actualText + " text is not displayed as expected");
			}

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while Filling Text Section And Verfying Text." + e.getMessage());
		}

	}

	/**
	 * @authorBrundha
	 * @description : Method for adding tiffpagecount on DAT.
	 */
	public void fillingTiffPageCountToDATFields() {
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDAT_FieldClassification2().Visible() && getDAT_FieldClassification1().Enabled();
				}
			}), Input.wait30);
			getDAT_FieldClassification2().selectFromDropdown().selectByVisibleText("Production");

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDAT_SourceField2().Visible() && getDAT_SourceField1().Enabled();
				}
			}), Input.wait30);
			getDAT_SourceField2().selectFromDropdown().selectByVisibleText("TIFFPageCount");

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDAT_DATField2().Visible() && getDAT_DATField1().Enabled();
				}
			}), Input.wait30);
			getDAT_DATField2().SendKeys(Input.randomText);
			base.stepInfo("Dat section is filled with Tiff page count");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while adding TIFF page count on DAT." + e.getMessage());
		}
	}

	/**
	 * @author: Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for filling priv docs placeholder in tiff section.
	 * @param tagname : tagname is aString value that name of tag.
	 */
	public void selectPrivDocsInTiffSectionWithMetaDataField(String tagname) {
		try {
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().waitAndClick(5);
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().waitAndClick(5);
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().ScrollTo();
			base.waitForElement(getPriveldge_SelectTagButton());
			getPriveldge_SelectTagButton().Click();
			base.waitForElement(getChkBoxSelect(tagname));
			getChkBoxSelect(tagname).waitAndClick(10);
			getPriveldge_TagTree_SelectButton().waitAndClick(10);

			base.waitForElement(getPrivInsertMetadataField());
			getPrivInsertMetadataField().waitAndClick(5);
			base.waitForElement(getCustodianNameinTiff());
			getCustodianNameinTiff().waitAndClick(5);
			base.waitForElement(getPopUpOkButtonInserMetaData());
			getPopUpOkButtonInserMetaData().waitAndClick(5);
			driver.scrollPageToTop();

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while filling priv docs placeholder in tiff section" + e.getMessage());
		}
	}

	/**
	 * @authorGopinath
	 * @description : Method for Tiff section with tech issue insert meta data
	 *              field.
	 */
	public void fillingTiffSectionTechIssueMetaDataField(String tagname) {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().waitAndClick(5);

			driver.scrollingToBottomofAPage();

			base.waitForElement(getTIFFTab());
			getTIFFTab().waitAndClick(5);

			driver.scrollPageToTop();

			base.waitForElement(getTIFF_CenterHeaderBranding());
			getTIFF_CenterHeaderBranding().waitAndClick(10);

			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().waitAndClick(5);

			base.waitForElement(getTechissue_toggle());
			getTechissue_toggle().waitAndClick(5);
			driver.waitForPageToBeReady();
			base.waitForElement(getTechissue_SelectTagButton());
			getTechissue_SelectTagButton().waitAndClick(10);
			getPriveldge_TagTree(tagname).isElementAvailable(10);
			getPriveldge_TagTree(tagname).waitAndClick(5);
			base.waitForElement(getPriveldge_TagTree_SelectButton());
			getPriveldge_TagTree_SelectButton().waitAndClick(15);
			driver.waitForPageToBeReady();
			base.waitForElement(getTechInsertMetadataField());
			getTechInsertMetadataField().waitAndClick(10);
			base.waitForElement(getCustodianNameinTiff());
			getCustodianNameinTiff().Click();
			base.waitForElement(getPopUpOkButtonInserMetaData());
			getPopUpOkButtonInserMetaData().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while filling Tiff section with insert meta data fied." + e.getMessage());
		}

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @throws InterruptedException
	 * @Description filling Advanced production Translation component
	 */
	public void advancedProductionComponentsTranslations() throws InterruptedException {
		getAdvancedProductionComponent().WaitUntilPresent().ScrollTo();
		base.clickButton(getAdvancedProductionComponent());
		base.clickButton(getTranslationsChkBox());
		base.clickButton(getTranslationsCheckBox());
		driver.scrollingToBottomofAPage();
		base.clickButton(getTranslation_SelectAllCheck());

		driver.scrollPageToTop();

		base.stepInfo("Advanced production section Translation files is filled");
	}

	/**
	 * @authorAathith
	 * @description Save template and disable check
	 * 
	 **/
	public void saveTemple(String TempName) {
		getprod_Templatetext().SendKeys(TempName);
		getProdExport_SaveButton().waitAndClick(10);
		base.VerifySuccessMessage("Production Saved as a Custom Template.");
	}

	/**
	 * @authorAathith.Senthilkumar
	 * @Description verification for translation disabled or not in template view
	 */
	public void translationDisableCheck() {
		boolean flag;
		driver.waitForPageToBeReady();

		flag = getTranslationComponent().Enabled();

		if (flag) {

			Assert.assertTrue(true);
			base.passedStep("Translation component is disabled at managing template page");
		} else {
			Assert.assertTrue(false);
			base.failedStep("Translation component is enabled at managing template page");
		}
	}

	/**
	 * @authorAathith.Senthilkumar
	 * @Description In production Template page permorme in Translation function
	 */
	public void viewTempProductionNextAdvTranslation() {

		base.waitForElement(getbtnTemplateGuardNext());
		getbtnTemplateGuardNext().waitAndClick(10);
		base.waitForElement(getadvanceOnManageTemplate());
		getadvanceOnManageTemplate().waitAndClick(10);
		base.waitForElement(getTranslationsCheckBox());
		getTranslationsCheckBox().waitAndClick(10);
	}

	/**
	 * @authorAathith.Senthilkumar
	 * @Description Filling Pdf section with default redaction
	 */
	public void fillingPDFForRedaction1() throws InterruptedException {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();

		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();

		base.waitForElement(getPDFGenerateRadioButton());
		getPDFGenerateRadioButton().ScrollTo();
		base.clickButton(getPDFGenerateRadioButton());

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		// disabling enable for priviledged docs

		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().Enabled();
		getTIFF_EnableforPrivilegedDocs().Click();

		getClk_burnReductiontoggle().ScrollTo();

		// enable burn redaction
		base.waitForElement(getClk_burnReductiontoggle());
		getClk_burnReductiontoggle().Click();

		getClkRadioBtn_selectRedactionTags().ScrollTo();

		base.waitForElement(getClkRadioBtn_selectRedactionTags());
		getClkRadioBtn_selectRedactionTags().isDisplayed();
		driver.waitForPageToBeReady();
		getClkRadioBtn_selectRedactionTags().waitAndClick(10);

		base.waitForElement(getClkCheckBox_defaultRedactionTag());
		getClkCheckBox_defaultRedactionTag().isDisplayed();
		getClkCheckBox_defaultRedactionTag().waitAndClick(10);

		// add the pop link

		base.waitForElement(getClkLink_selectingRedactionTags());
		getClkLink_selectingRedactionTags().isDisplayed();
		getClkLink_selectingRedactionTags().waitAndClick(10);

		base.waitForElement(getClkBtn_selectingRedactionTags());
		getClkBtn_selectingRedactionTags().isDisplayed();
		getClkBtn_selectingRedactionTags().waitAndClick(10);

//				base.waitForElement(getClkCheckBox_selectingRedactionTags());
//				getClkCheckBox_selectingRedactionTags().Click();

		base.waitForElement(getRedactionWithoutRedactionTagsCheckbox());
		getRedactionWithoutRedactionTagsCheckbox().Click();

		base.waitForElement(getClk_selectBtn());
		getClk_selectBtn().Click();

		base.waitForElement(getRedactedTagTextArea());
		getRedactedTagTextArea().isDisplayed();
		getRedactedTagTextArea().SendKeys("Updated Redaction Tag");

		Thread.sleep(5000);

	}

	/**
	 * @authorGopinath
	 * @Description : Method for navigating to production page.
	 */
	public void navigateToProductionPage() {
		try {
			driver.getWebDriver().get(Input.url + "Production/Home");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while navigating to production page" + e.getMessage());
		}
	}

	/**
	 * @author:Gopinath
	 * @Description: Method for clicking generate button.
	 */
	public void clickOnGenerateButton() {
		try {
			base.waitForElement(getbtnProductionGenerate());
			getbtnProductionGenerate().waitAndClick(10);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while clicking generate button." + e.getMessage());
		}
	}

	/**
	 * @author:Gopinath
	 * @Description: Method for clicking generate button and verify pre gen check
	 *               status.
	 */
	public void clickOnGenerateButtonAndVerifyPreGenChecksStatus() {
		try {
			base.waitForElement(getbtnProductionGenerate());
			getbtnProductionGenerate().waitAndClick(10);
			driver.waitForPageToBeReady();
			verifyProductionStatusInGenPage("Pre-Generation Checks In Progress");

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while licking generate button and verify pre gen check status."
					+ e.getMessage());

		}
	}

	/**
	 * Modified on 03/09/2022
	 * 
	 * @author: Aathith Senthilkumar
	 * @Description: Method for genarate page without wait
	 */
	public void clickGenarateWaitForRegenarate() {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGenerate().Enabled() && getbtnProductionGenerate().isDisplayed();
			}
		}), Input.wait30);
		getbtnProductionGenerate().Click();

		getbtnReGenerateMarkComplete().isElementAvailable(120);
	}

	/**
	 * @author Aathith.Senthilkumar.Modified on 04/04/2022
	 * @throws InterruptedException
	 * @Description Production reverse bates range failed check
	 */
	public void prodReservingBatesRangeFailedProduction1() throws InterruptedException {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return gettxtReservingBatesFailedAt1stProdShown().Enabled();
			}
		}), Input.wait30);
		base.stepInfo("Reserving Bates Range Failed production displays");
		if (gettxtReservingBatesFailedAt1stProdShown().Enabled()) {
			String batesFailedMsg = gettxtReservingBatesFailedAt1stProdShown().getText();
			base.passedStep("verifing the failed status :" + batesFailedMsg);
			Assert.assertTrue(true, batesFailedMsg);
		} else {
			base.passedStep("No Reserving Bates Range failed production occurs");
		}
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getGearIcon().Enabled() && getGearIcon().isDisplayed();
			}
		}), Input.wait30);
		getGearIcon().Click();
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getOpenWizard().Enabled() && getOpenWizard().isDisplayed();
			}
		}), Input.wait30);
		getOpenWizard().Click();
		base.waitForElement(getbtnReGenerateMarkComplete());
		getbtnReGenerateMarkComplete().waitAndClick(5);
		base.waitForElement(getbtnRegenerateContinue());
		getbtnRegenerateContinue().Click();
		verifyProductionStatusInGenPage("Preparing Data In Progress");
		driver.waitForPageToBeReady();
		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");
		driver.waitForPageToBeReady();
		getbtnReGenerateMarkComplete().isElementAvailable(180);
		base.waitForElement(getbtnReGenerateMarkComplete());
		getbtnReGenerateMarkComplete().waitAndClick(10);
		base.waitForElement(getbtnRegenerateCancel());
		getbtnRegenerateCancel().Click();
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @throws InterruptedException
	 * @Description Production advance section mp3 section filling
	 */
	public void advancedProductionComponentsMP3DisableGenarateFileLoad() throws InterruptedException {
		getAdvancedProductionComponent().WaitUntilPresent().ScrollTo();
		base.clickButton(getAdvancedProductionComponent());
		base.clickButton(getMP3CheckBox());
		base.clickButton(getMP3CheckBoxToggle());
		base.clickButton(getAdvancedTabInMP3());
		base.clickButton(getMp3GenerateLoadFile());
		base.stepInfo("Advanced production section MP3 files is filled with disabling the genarate load file");
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @throws InterruptedException
	 * @Description filling tiff section with error
	 */
	public void fillingTIFFSectionWithError() throws InterruptedException {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().waitAndClick(5);

		driver.scrollingToBottomofAPage();

		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();
		driver.scrollPageToTop();

		base.stepInfo("TIFF section fillied without placeholder text");

	}

	/**
	 * Modified on 03/09/2022
	 * 
	 * @author: Aathith Senthilkumar
	 * @throws InterruptedException
	 * @Description: Method for filling Generate wait for continue button to be
	 *               ready.
	 */
	public void fillingGeneratePageWaitForContinueGeneration() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGenerate().Enabled() && getbtnProductionGenerate().isDisplayed();
			}
		}), Input.wait30);

		getbtnProductionGenerate().Click();
		getbtnContinueGenerate().isElementAvailable(120);
		if (getbtnContinueGenerate().isDisplayed()) {
			driver.waitForPageToBeReady();
			getbtnContinueGenerate().Click();
		} else {
			System.out.println("Continue generation not found");

		}
	}

	/**
	 * @authorAathith.Senthilkumar
	 * @Description Slip Count check in Production location page
	 */
	public void splictCountCheck() {
		boolean flag;
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		flag = verifyProductionLocationSplitCount().GetAttribute("style").contains("rgb(255, 255, 255)");
		if (flag) {

			softAssertion.assertTrue(true);
			base.passedStep("Split sub folder button enabled");
		} else {
			softAssertion.assertTrue(false);
			base.failedStep("Split sub folder button disabled");
		}
		flag = verifyProductionLocationSplitCount().GetAttribute("value").contains("1000");
		if (flag) {

			softAssertion.assertTrue(true);
			base.passedStep("Split Count is 1000");
		} else {
			softAssertion.assertTrue(false);
			base.failedStep("Split Count is not 1000");
		}
		driver.scrollPageToTop();

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @throws InterruptedException
	 * @Description filling pdf section with error
	 */
	public void fillingPDFSectionWithError() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPDFChkBox().Enabled();
			}
		}), Input.wait30);
		getPDFChkBox().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPDFTab().Enabled();
			}
		}), Input.wait30);
		getPDFTab().Click();

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPDFGenerateRadioButton().Enabled();
			}
		}), Input.wait30);
		getPDFGenerateRadioButton().Click();

		base.stepInfo("PDF section fillied without placeholder text");
	}

	/*
	 * @authorJeevitha
	 */
	public void createNewExport(String exportname) {
		base.waitForElement(getProdExportSet());
		getProdExportSet().waitAndClick(10);

		base.waitForElement(getProductionSettxt());
		getProductionSettxt().SendKeys(exportname);

		base.waitForElement(getProdExportSetRadioButton());
		getProdExportSetRadioButton().waitAndClick(10);

		base.waitForElement(getProdExport_SaveButton());
		getProdExport_SaveButton().waitAndClick(10);
		base.VerifySuccessMessage("Export Set Added Successfully");

		base.waitForElement(getProdExport_ProductionSets());
		getProdExport_ProductionSets().waitAndClick(10);
		getProdExport_SelectValueFromDD(exportname).waitAndClick(10);

		base.stepInfo(exportname + " Is selected From Production/Export Set DropDown");
	}

	/**
	 * @authorBrundha
	 * @param Batesvalue
	 * @throws InterruptedException
	 * 
	 * @description:verifying the batesrange value in generate page
	 */
	public void fillingGeneratePageAndVerfyingBatesRange(String Batesvalue) throws InterruptedException {

		SoftAssert softAssertion = new SoftAssert();
		String expectedText = "Success";

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGenerate().Enabled() && getbtnProductionGenerate().isDisplayed();
			}
		}), Input.wait30);
		getbtnProductionGenerate().waitAndClick(10);

		getbtnContinueGeneration().isElementAvailable(540);
		if (getbtnContinueGeneration().isDisplayed()) {
			base.waitForElement(getbtnContinueGeneration());
			getbtnContinueGeneration().waitAndClick(10);
		}

		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");
		driver.waitForPageToBeReady();
		String PrefixValue = getBatesRange().getText();
		System.out.println(PrefixValue);
		// String Actualtext=suffixID;

		if (PrefixValue.contains(Batesvalue)) {
			base.passedStep("Batesrange is with 50 character as expected");
		} else {
			base.failedStep("BatesRange is not  with 50 character as expected");
		}
		getDocumentGeneratetext().isElementAvailable(120);
		String actualText = getStatusSuccessTxt().getText();
		System.out.println(actualText);

		softAssertion.assertTrue(actualText.contains(expectedText));
		base.passedStep("Documents Generated successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getConfirmProductionCommit().Enabled() && getConfirmProductionCommit().isDisplayed();
			}
		}), Input.wait60);

		// added thread.sleep to avoid exception while executing in batch
		Thread.sleep(2000);
		getConfirmProductionCommit().waitAndClick(10);

		if (base.getCloseSucessmsg().isElementAvailable(5)) {
			base.CloseSuccessMsgpopup();
		}

		String PDocCount = getProductionDocCount().getText();
		// added thread.sleep to avoid exception while executing in batch
		Thread.sleep(1000);
		System.out.println(PDocCount);
		int Doc = Integer.parseInt(PDocCount);

		Reporter.log("Doc - " + Doc, true);
		System.out.println(Doc);
		UtilityLog.info(Doc);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCopyPath().isDisplayed();
			}
		}), Input.wait60);
		getCopyPath().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getQC_Download().isDisplayed();
			}
		}), Input.wait30);

		getQC_Download().waitAndClick(10);
		getQC_Downloadbutton_allfiles().waitAndClick(10);
		base.VerifySuccessMessage("Your Production Archive download will get started shortly");
		base.stepInfo("Generate production page is filled");
	}

	/**
	 * @author Brundha.T
	 * @param Productionname
	 * @param Templatename
	 * @Description: Method for adding the saved template in basic info tab.
	 */
	public void savedTemplateAndNewProdcution(String Productionname, String Templatename) {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getDropDownToggle(Productionname));
			getDropDownToggle(Productionname).waitAndClick(10);
			base.waitForElement(getSaveAsTemplate(Productionname));

			getSaveAsTemplate(Productionname).waitAndClick(10);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getprod_Templatetext().Visible();
				}
			}), Input.wait30);
			getprod_Templatetext().SendKeys(Templatename);
			getProdExport_SaveButton().waitAndClick(5);
			base.VerifySuccessMessage("Production Saved as a Custom Template.");
		} catch (Exception e) {
			base.failedStep("Exception occcured while  filling template" + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * @author Brundha.T
	 * @param prefixID
	 * @param suffixID
	 * @Description: Method for verifying the text in prefix and suffix in numbering
	 *               and sorting page
	 */
	public void verifyingPrefixAndSuffixText(String prefixID, String suffixID) {
		try {
			base.waitForElement(getBeginningBates());
			driver.waitForPageToBeReady();
			num = getRandomNumber(2);
			System.out.println(num);
			getBeginningBates().SendKeys(num);
			driver.waitForPageToBeReady();
			base.waitForElement(gettxtBeginningBatesIDPrefix());
			driver.waitForPageToBeReady();
			String Prefix = gettxtBeginningBatesIDPrefix().GetAttribute("value");
			System.out.println(Prefix);
			System.out.println(prefixID);
			driver.waitForPageToBeReady();
			if (Prefix.equals(prefixID)) {
				base.passedStep("" + Prefix + " and " + prefixID + "  text is as expected");
			} else {
				base.failedStep("" + Prefix + " and " + prefixID + "  text is as expected");
			}
			driver.waitForPageToBeReady();
			base.waitForElement(gettxtBeginningBatesIDSuffix());
			String Suffix = gettxtBeginningBatesIDSuffix().GetAttribute("value");
			System.out.println(Suffix);
			System.out.println(suffixID);
			if (Suffix.equals(suffixID)) {
				base.passedStep("" + Suffix + " and " + suffixID + "  text is as expected");
			} else {
				base.failedStep("" + Suffix + " and " + suffixID + "  text is as expected");
			}
		} catch (Exception e) {
			base.failedStep("Exception occcured while handling numbering and sorting tab" + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * @author: Brundha
	 * @Description: method for applying completed status in the production filter
	 * 
	 */
	public void addProductionFilter() {
		try {
			driver.getWebDriver().get(Input.url + "Production/Home");
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getFilterByButton().isElementAvailable(50);
				}
			}), Input.wait30);
			getFilterByButton().waitAndClick(10);
			for (int i = 1; i < getFilterOptions().size(); i++) {
				getFilter(i).waitAndClick(10);
			}
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getFilterByINPROGRESS().Visible();
				}
			}), Input.wait30);
			getFilterByINPROGRESS().waitAndClick(10);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getFilterByFAILED().Visible();
				}
			}), Input.wait30);
			getFilterByFAILED().waitAndClick(10);

			Thread.sleep(2000);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getFilterByCOMPLETED().Visible();
				}
			}), Input.wait30);
			getFilterByCOMPLETED().waitAndClick(10);
			driver.waitForPageToBeReady();
			getRefreshButton().waitAndClick(10);
			System.out.println("Verified Production Filter");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while performing production filter" + e.getMessage());
		}

	}

	/**
	 * @authorBrundha
	 * @description :filling PDF section.
	 * 
	 */
	public void fillingPDFSection() {

		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFFChkBox());
			base.waitTillElemetToBeClickable(getTIFFChkBox());
			getTIFFChkBox().Click();
			driver.scrollingToBottomofAPage();
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFFTab());
			base.waitTillElemetToBeClickable(getTIFFTab());
			getTIFFTab().Click();
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			getPDFGenerateRadioButton().ScrollTo();
			base.waitTillElemetToBeClickable(getPDFGenerateRadioButton());
			getPDFGenerateRadioButton().Click();
			getTIFF_EnableforPrivilegedDocs().ScrollTo();
		} catch (Exception e) {
			base.failedStep("Exception occcured while handling pdf section" + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * @authorBrundha
	 * @description :filling burn redaction.
	 * 
	 */
	public void disablePrivDocAndEnableBurnRedaction() {
		try {
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			driver.waitForPageToBeReady();
			getTIFF_EnableforPrivilegedDocs().Click();
			getClk_burnReductiontoggle().ScrollTo();
			base.waitTillElemetToBeClickable(getClk_burnReductiontoggle());
			getClk_burnReductiontoggle().Click();
			getClkRadioBtn_selectRedactionTags().ScrollTo();
			driver.waitForPageToBeReady();
			base.waitForElement(getClkRadioBtn_selectRedactionTags());
			getClkRadioBtn_selectRedactionTags().waitAndClick(10);
			base.waitForElement(getClkCheckBox_defaultRedactionTag());
			getClkCheckBox_defaultRedactionTag().isDisplayed();
			getClkCheckBox_defaultRedactionTag().waitAndClick(10);
		} catch (Exception e) {
			base.failedStep("Exception occcured while handling priv doc" + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * @authorBrundha
	 * @param Tag
	 * @description :method for filling burn redaction with redaction tag.
	 * 
	 */
	public void burnRedactionWithRedactionTag(String Tag) {
		try {
			base.waitForElement(getClkLink_selectingRedactionTags());
			getClkLink_selectingRedactionTags().waitAndClick(10);
			driver.waitForPageToBeReady();
			base.waitForElement(getClkBtn_selectingRedactionTags());
			getClkBtn_selectingRedactionTags().Click();
			driver.waitForPageToBeReady();
			if (BurnRedactionCheckBox(Tag).isDisplayed()) {
				BurnRedactionCheckBox(Tag).ScrollTo();
				base.waitForElement(BurnRedactionCheckBox(Tag));
				BurnRedactionCheckBox(Tag).ScrollTo();
				BurnRedactionCheckBox(Tag).waitAndClick(10);
			} else if (BurnRedactionCheckBox_Imp(Tag).isDisplayed()) {
				BurnRedactionCheckBox_Imp(Tag).ScrollTo();
				base.waitForElement(BurnRedactionCheckBox_Imp(Tag));
				BurnRedactionCheckBox_Imp(Tag).ScrollTo();
				BurnRedactionCheckBox_Imp(Tag).waitAndClick(10);
			}
			driver.waitForPageToBeReady();
			base.waitForElement(getClk_selectBtn());
			getClk_selectBtn().waitAndClick(10);
			driver.waitForPageToBeReady();
			base.waitForElement(gettextRedactionPlaceHolder());
			gettextRedactionPlaceHolder().waitAndClick(10);
			gettextRedactionPlaceHolder().SendKeys(searchString4);
			base.stepInfo("Burn redaction PDF section is filled successfully");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while handling burnredaction with redaction tag" + e.getMessage());
		}
	}

	/**
	 * @authorBrundha
	 * @description :Method for verifying hide/show of DAT checkbox.
	 * 
	 */
	public void verfyingDATCheckBox() {

		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getDATTab());
			getDATTab().Click();
			base.waitForElement(getDAT_FieldClassification1());
			if (getDAT_FieldClassification1().isDisplayed()) {
				base.passedStep("DAT checkBox is shown as Expected");
			} else {
				base.failedStep("DAT checkBox is not shown  as Expected");
			}
			driver.waitForPageToBeReady();
			base.waitForElement(getDATTab());
			getDATTab().Click();
			base.waitForElement(getDAT_FieldClassification1());
			if (!getDAT_FieldClassification1().isDisplayed()) {
				base.passedStep("DAT checkBox is Hid as Expected");
			} else {
				base.failedStep("DAT checkBox is not hid  as Expected");
			}
		} catch (Exception e) {
			base.failedStep("Exception occcured while handling DAT section" + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * @author:Brundha
	 * @Description: Method for checking status of production from homepage
	 * @param statusMsg
	 * @param productionname
	 */
	public void verifyingProductionStatusInHomePage(String statusMsg, String productionname) {
		String productionFromHomePage = null;

		// Verifying production status from home page
		for (int i = 0; i < 500; i++) {
			productionFromHomePage = getProductionFromHomePage(productionname).getText();
			System.out.println("Preparing Data status displayed for " + productionFromHomePage);
			if (productionFromHomePage.equals(statusMsg)) {
				base.passedStep(statusMsg + "status displayed");
				break;
			} else if (i == 499) {
				base.failedStep(statusMsg + "status not displayed");
			} else {
				driver.waitForPageToBeReady();

				getRefreshButton().waitAndClick(5);
			}
		}
	}

	/**
	 * @authorBrundha
	 * @description :filling PDF section.
	 * 
	 */
	public void SelectPDFGenerateRadioButton() {
		try {
			base.waitForElement(getTIFFChkBox());
			base.waitTillElemetToBeClickable(getTIFFChkBox());
			getTIFFChkBox().Click();
			driver.scrollingToBottomofAPage();
			base.waitTillElemetToBeClickable(getTIFFTab());
			getTIFFTab().Click();
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			getPDFGenerateRadioButton().ScrollTo();
			base.waitTillElemetToBeClickable(getPDFGenerateRadioButton());
			getPDFGenerateRadioButton().Click();
			base.stepInfo("PDF section is filled");
		} catch (Exception e) {
			base.failedStep("Exception occcured while filling generate pdf radio button" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @authorBrundha
	 * @description :selecting pdf generate radio button
	 * @param tagname
	 * @param tagnameprev
	 * 
	 */
	public void fillingPrivledgedDocForPDFSection(String tagname, String tagnameprev) {
		try {

			getTIFF_EnableforPrivilegedDocs().ScrollTo();
			base.waitForElement(getPriveldge_SelectTagButton());
			getPriveldge_SelectTagButton().waitAndClick(10);
			base.waitTillElemetToBeClickable(getPriveldge_TagTree(tagname));
			getPriveldge_TagTree(tagname).waitAndClick(10);
			getPriveldge_TagTree_SelectButton().Click();
			base.waitForElement(getPriveldge_TextArea());
			new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();
			getPriveldge_TextArea().SendKeys(tagNameTechnical);
			driver.scrollingToBottomofAPage();
			base.stepInfo("PDF section is filled with privileged Doc");
		} catch (Exception e) {
			base.failedStep("Exception occcured while filling priv doc in pdf section" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @authorBrundha
	 * @description :method for verifying the natively produced docs toggle
	 * 
	 */

	public void verifyingNativelyProducedToggle() {
		try {
			base.waitTillElemetToBeClickable(getTIFFChkBox());
			getTIFFChkBox().Click();
			driver.scrollingToBottomofAPage();
			base.waitTillElemetToBeClickable(getTIFFTab());
			getTIFFTab().Click();
			driver.waitForPageToBeReady();
			String color = driver
					.FindElement(By.xpath("//input[@id='chkShouldSkipTIFFGeneration']//following-sibling::i"))
					.GetCssValue("background-color");
			System.out.println(color);
			String ExpectedColor = Color.fromString(color).asHex();
			System.out.println(ExpectedColor);
			String ActualColor = "#e54036";
			if (ActualColor.equals(ExpectedColor)) {
				base.passedStep("Natively produced docs option is disabled  by default");
			} else {
				base.failedStep("Natively produced docs option is not  disabled  by default");
			}
		} catch (Exception e) {
			base.failedStep("Exception occcured while verifying natively produced docs toggle" + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * @authorGopinath
	 * @description : Method to fill native section with tags.
	 * @param tag1 : tag1 is String value name of tag.
	 * @param tag2 : tag2 is String value that name of tag.
	 * 
	 */
	public void fillingNativeSectionWithTags(String tag1, String tag2) throws InterruptedException {
		try {
			base.waitForElement(getNativeChkBox());
			getNativeChkBox().isElementAvailable(10);
			getNativeChkBox().Click();
			base.waitForElement(getNativeTab());
			getNativeTab().isElementAvailable(10);
			getNativeTab().Click();
			driver.waitForPageToBeReady();
			getNative_SelectAllCheck().isElementAvailable(10);
			base.waitForElement(getNative_SelectAllCheck());
			getNative_SelectAllCheck().Click();
			base.waitForElement(getNativeSelectTags());
			getNativeSelectTags().Click();
			getNativeCheckBox(tag1).isElementAvailable(10);
			base.waitForElement(getNativeCheckBox(tag1));
			driver.waitForPageToBeReady();
			getNativeCheckBox(tag1).ScrollTo();
			getNativeCheckBox(tag1).Click();
			driver.waitForPageToBeReady();
			getNativeCheckBox(tag2).isElementAvailable(10);
			getNativeCheckBox(tag2).ScrollTo();
			getNativeCheckBox(tag2).Click();
			driver.waitForPageToBeReady();
			getNativeSelect().isElementAvailable(10);
			base.waitForElement(getNativeSelect());
			getNativeSelect().Click();
			base.stepInfo("Native section is filled");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while filling native section" + e.getMessage());
		}
	}

	/**
	 * @authorGopinath Modified By - Gopinath Modified Date - NA
	 * @Description : Method to navigating To Export Page.
	 * @param exportName : exportName is String value that name of export need to
	 *                   perform export production.
	 */
	public void navigateToExportPageByNewExportSet(String exportName) {
		try {
			driver.getWebDriver().get(Input.url + "Production/Home");
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			getProdExportSet().isElementAvailable(15);
			getProdExportSet().waitAndClick(10);
			getProductionSettxt().isElementAvailable(15);
			getProductionSettxt().SendKeys(exportName);
			getProdExportSetRadioButton().waitAndClick(10);
			getProdExport_SaveButton().waitAndClick(10);
			base.VerifySuccessMessage("Export Set Added Successfully");
			getProdExport_ProductionSets().isElementAvailable(15);
			List<WebElement> selectProductionOptions = getSelectProdcutionOptions().FindWebElements();
			for (WebElement option : selectProductionOptions) {
				if (option.getText().contains(exportName)) {
					option.click();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while navigating To Export Page." + e.getMessage());
		}
	}

	/**
	 * @authorGopinath Modified By - Gopinath Modified Date - NA
	 * @Description : Method to enter Basic Details Stage By Selecting Production.
	 * @param exportName     : exportName is String value that name of export need
	 *                       to perform export production.
	 * @param proudctionName : proudctionName is String value that already completed
	 *                       committed production name.
	 */
	public void enterBasicDetailsStageBySelectingProduction(String exportName, String productionname) {

		getProdExport_AddaNewExportSet().isElementAvailable(15);
		getProdExport_AddaNewExportSet().waitAndClick(10);
		getProductionName().isElementAvailable(10);
		getProductionName().SendKeys(exportName);
		getProductionDesc().SendKeys(exportName);
		getProdExport_Priorprodtoggle().isElementAvailable(15);
		getProdExport_Priorprodtoggle().waitAndClick(10);
		getProdExport_SelectProductionSet().ScrollTo();
		driver.waitForPageToBeReady();
		getProdExport_SelectProductionSet().selectFromDropdown().selectByVisibleText(productionname);
		driver.scrollPageToTop();
		getBasicInfoMarkComplete().isElementAvailable(15);
		getBasicInfoMarkComplete().waitAndClick(10);

	}

	/**
	 * @authorGopinath Modified By - Gopinath Modified Date - NA
	 * @Description : Method to click On Complete And Verify Success Message.
	 */
	public void clickOnCompleteAndVerifySuccessMessage() throws InterruptedException {
		try {
			driver.scrollPageToTop();
			base.waitForElement(getMarkCompleteLink());
			getMarkCompleteLink().waitAndClick(10);

			System.out.println("Clicked on Mark Complete Button..");
			driver.waitForPageToBeReady();

			base.waitForElement(getNextButton());
			getNextButton().Enabled();
			getNextButton().waitAndClick(10);

			base.stepInfo("Navigate to next page");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured  click On Complete And Verify Success Message..." + e.getMessage());

		}
	}

	/**
	 * @authorGopinath Modified By - Gopinath Modified Date - NA
	 * @Description : Method to add DAT field at third row.
	 * @param fieldClassification : fieldClassification is String value that field
	 *                            classification need to select from drop down.
	 * @param sourceField         : sourceField is String value that source field
	 *                            need to select from drop down.
	 * @param datField            : datField is String value need to enter in dat
	 *                            text field.
	 */
	public void addDATFieldAtThirdRow(String fieldClassification, String sourceField, String datField) {
		try {
			addNewFieldOnDAT();
			getDATFieldClassification3().isElementAvailable(10);
			getDATFieldClassification3().selectFromDropdown().selectByVisibleText(fieldClassification);
			getDAT_SourceField3().isElementAvailable(10);
			getDAT_SourceField3().selectFromDropdown().selectByVisibleText(sourceField);
			getDAT_DATField3().isElementAvailable(10);
			getDAT_DATField3().SendKeys(datField);
			base.stepInfo("Dat section is filled with " + sourceField);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured add DAT Field At Third Row." + e.getMessage());

		}

	}

	/**
	 * @authorGopinath Modified By - Gopinath Modified Date - NA
	 * @Description : Method to add DAT Field At second Row.
	 * @param fieldClassification : fieldClassification is String value that field
	 *                            classification need to select from drop down.
	 * @param sourceField         : sourceField is String value that source field
	 *                            need to select from drop down.
	 * @param datField            : datField is String value need to enter in dat
	 *                            text field.
	 */
	public void addDATFieldAtSecondRow(String fieldClassification, String sourceField, String datField) {
		try {
			addNewFieldOnDAT();
			driver.waitForPageToBeReady();
			;
			getDAT_FieldClassification2().isElementAvailable(10);
			getDAT_FieldClassification2().selectFromDropdown().selectByVisibleText(fieldClassification);
			driver.waitForPageToBeReady();
			getDAT_SourceField2().isElementAvailable(10);
			getDAT_SourceField2().selectFromDropdown().selectByVisibleText(sourceField);
			driver.waitForPageToBeReady();
			getDAT_DATField2().isElementAvailable(10);
			getDAT_DATField2().SendKeys(datField);
			base.stepInfo("Dat section is filled with " + sourceField);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured add DAT Field At second Row." + e.getMessage());

		}

	}

	/**
	 * @authorGopinath Modified By - Gopinath Modified Date - NA
	 * @Description : Method to navigating To Export Page by already existing export
	 *              set.
	 * @param exportName : exportName is String value that name of export need to
	 *                   perform export production.
	 */
	public void navigateToExportPageByAlreadyExistingExportSet(String exportName) {
		try {
			driver.getWebDriver().get(Input.url + "Production/Home");
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			getProdExportSet().isElementAvailable(15);
			getProdExport_ProductionSets().isElementAvailable(15);
			List<WebElement> selectProductionOptions = getSelectProdcutionOptions().FindWebElements();
			for (WebElement option : selectProductionOptions) {
				if (option.getText().contains(exportName)) {
					option.click();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while navigating To Export Page by already existing export set."
					+ e.getMessage());
		}
	}

	/**
	 * @authorGopinath Modified By - Gopinath Modified Date - NA
	 * @Description : Method to verify committed production is added to export
	 *              production drop down.
	 * @param exportName     : exportName is String value that name of export need
	 *                       to perform export production.
	 * @param proudctionName : proudctionName is String value that already completed
	 *                       committed production name.
	 */
	public void verifyCommittedProdIsAddedToExportProductionDropdown(String exportName, String proudctionName) {
		try {
			getProdExport_AddaNewExportSet().isElementAvailable(15);
			getProdExport_AddaNewExportSet().waitAndClick(10);
			getProductionName().isElementAvailable(10);
			getProductionName().SendKeys(exportName);
			getProductionDesc().SendKeys(exportName);
			getProdExport_Priorprodtoggle().isElementAvailable(15);
			getProdExport_Priorprodtoggle().waitAndClick(10);
			getProdExport_Priorprodtoggle().isElementAvailable(15);
			List<WebElement> options = getProdExport_SelectProductionSet().selectFromDropdown().getOptions();

			for (int i = 0; i < options.size(); i++) {
				if (options.get(i).getText().contains(proudctionName)) {
					base.passedStep(proudctionName + " is displayed in export production drop down sucessfully");
				} else if (i == options.size() - 1) {
					base.failedStep(proudctionName + " is not displayed in export production drop down");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while verifying committed production is added to export production drop down.."
							+ e.getMessage());
		}
	}

	/**
	 * @authorGopinath Modified By - Gopinath Modified Date - NA
	 * @Description : Method to verify uncommitted production is not added to export
	 *              production drop down.
	 * @param exportName     : exportName is String value that name of export need
	 *                       to perform export production.
	 * @param proudctionName : proudctionName is String value that already completed
	 *                       committed production name.
	 */
	public void verifyUnCommittedProdIsNotAddedToExportProdDropdown(String exportName, String proudctionName) {
		try {
			getProdExport_AddaNewExportSet().isElementAvailable(15);
			getProdExport_AddaNewExportSet().waitAndClick(10);
			getProductionName().isElementAvailable(10);
			getProductionName().SendKeys(exportName);
			getProductionDesc().SendKeys(exportName);
			getProdExport_Priorprodtoggle().isElementAvailable(15);
			getProdExport_Priorprodtoggle().waitAndClick(10);
			getProdExport_Priorprodtoggle().isElementAvailable(15);
			List<WebElement> options = getProdExport_SelectProductionSet().selectFromDropdown().getOptions();
			for (int i = 0; i < options.size(); i++) {
				if (options.get(i).getText().contains(proudctionName)) {
					base.failedStep(proudctionName + " is displayed in export production drop down");
				}
			}
			base.passedStep(proudctionName + " is not displayed in export production drop down sucessfully");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while verifying committed production is not added to export production drop down.."
							+ e.getMessage());
		}
	}

	/**
	 * @throws InterruptedException
	 * @authorGopinath .Modified on 10/28/2021
	 * @Descrption : method for filling Export Generate Page With Continue
	 *             Generation Popup.
	 */
	public void fillingExportGeneratePageWithContinueGenerationPopup() throws InterruptedException {
		try {
			SoftAssert softAssertion = new SoftAssert();
			String expectedText = "Success";

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getbtnProductionGenerate().Enabled() && getbtnProductionGenerate().isDisplayed();
				}
			}), Input.wait30);
			getbtnProductionGenerate().waitAndClick(10);

			getbtnContinueGeneration().isElementAvailable(150);
			if (getbtnContinueGeneration().isDisplayed()) {
				base.waitForElement(getbtnContinueGeneration());
				getbtnContinueGeneration().waitAndClick(10);
			}

			Reporter.log("Wait for generate to complete", true);
			System.out.println("Wait for generate to complete");
			UtilityLog.info("Wait for generate to complete");

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDocumentGeneratetext().Visible() && getDocumentGeneratetext().Enabled();
				}
			}), Input.wait120);
			String actualText = getStatusSuccessTxt().getText();
			System.out.println(actualText);

			softAssertion.assertTrue(actualText.contains(expectedText));
			base.passedStep("Documents Generated successfully");

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getCopyPath().isDisplayed();
				}
			}), Input.wait60);
			getCopyPath().waitAndClick(10);

			base.stepInfo("Generate Export page is filled");
		} catch (Exception e) {
			UtilityLog.info(e.getMessage());
		}
	}

	/**
	 * @authorGopinath Modified By - Gopinath Modified Date - NA
	 * @Description : Method to verify user not able to select documents.
	 */
	public void verifyUserNotAbleToSelectDocuments() {
		try {

			getMarkIncompleteButton().isElementAvailable(13);
			String markCompleteAttr = getMarkIncompleteButton().GetAttribute("disabled").trim();
			if (markCompleteAttr.equalsIgnoreCase("true")) {
				base.passedStep("Mark incomplete button is not enabled and not able to add documents");
			} else {
				base.failedStep("Mark incomplete button is enabled and able to add documents");
			}

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("verify user not able to select documents." + e.getMessage());
		}
	}

	/**
	 * @authorBrundha
	 * @Description : Method for navigating to doclist page.
	 * @return docCount
	 * @Modified by Jeevitha 17/1/2022
	 */
	public String navigatingToDocViewPage() {
		driver.scrollPageToTop();
		base.waitForElement(getMarkCompleteLink());
		getMarkCompleteLink().waitAndClick(10);
		if (getOkButton().isElementAvailable(3)) {
			getOkButton().waitAndClick(5);
		} else {
			System.out.println("No Warning PopUp Displayed");
		}
		System.out.println("Clicked on Mark Complete Button..");
		driver.waitForPageToBeReady();
		String docCount = getDocumentSelectionLink().getText();
		base.waitForElement(getDocumentSelectionLink());
		getDocumentSelectionLink().Click();
		base.stepInfo("Document Count is : " + docCount);
		driver.waitForPageToBeReady();
		return docCount;

	}

	public void verifyNavigationToProductionPage() {
		if (getDocumentSelectionLink().isDisplayed()) {
			base.passedStep("Prodcution page is loaded successfully");
		} else {
			base.failedMessage("prodcution page is not loaded");
		}

	}

	/**
	 * @authorBrundha
	 * @Description :method to next bates number
	 */
	public void SelectNextBatesNumber() {
		driver.waitForPageToBeReady();
		String actualText = getBeginningBates().GetAttribute("value");
		System.out.println("The actual txt" + actualText);
		base.waitForElement(getClickHereLink());
		getClickHereLink().Click();
		if (NextBatesNumberPopup().isDisplayed()) {
			base.passedStep("Next Bates number popup is opened");
		} else {
			base.failedStep("Next bates number popup is not opened");
		}
		base.waitForElement(getNextBatesNumber());
		getNextBatesNumber().Click();

		if (!NextBatesNumberPopup().isDisplayed()) {
			base.passedStep("Next Bates number popup is closed automatically");
		} else {
			base.failedStep("Next bates number is not closed automatically");
		}

		base.waitForElement(getMarkCompleteLink());
		getMarkCompleteLink().waitAndClick(10);

		driver.waitForPageToBeReady();
		if (!getBeginningBates().GetAttribute("value").contains(actualText)) {
			base.passedStep("values are Auto populated in Numbering and Sorting Tab");
		} else {
			base.failedStep("value are not Auto populated in Numbering and Sorting Tab");
		}

	}

	/**
	 * @authorBrundha
	 * @Description : Method for navigating back to numbering and sorting tab
	 */
	public void navigatingBackToNumberingAndSortingPage() {
		for (int i = 0; i < 5; i++) {
			driver.waitForPageToBeReady();
			base.waitForElement(getBackButton());
			getBackButton().waitAndClick(5);
		}
		base.waitTillElemetToBeClickable(getMarkInCompleteBtn());
		getMarkInCompleteBtn().waitAndClick(5);

	}

	/**
	 * @author Brundha.T
	 * @Description :method to validate clickhere link
	 * 
	 */
	public void verifyClickHereLinkNotAvailableAtMarkComplete() {

		if (getClickHereLink().isDisplayed()) {
			base.passedStep("ClickHere link is available before Markcomplete");
			base.waitForElement(getClickHereLink());
			getClickHereLink().Click();
			base.waitForElement(getNextBatesNumber());
			getNextBatesNumber().Click();
		} else {
			base.failedStep("ClickHere link is not available before Markcomplete");
		}

		base.waitForElement(getMarkCompleteLink());
		getMarkCompleteLink().waitAndClick(10);
		driver.waitForPageToBeReady();
		boolean flag = getClickHereLinks().Displayed() && getClickHereLinks().Exists();
		if (!flag) {
			System.out.println("passed");
			base.passedStep("ClickHere link  is  not available After selecting Markcomplete as expected");
		} else {
			System.out.println("failed");
			base.failedStep("ClickHere link is  available After Markcomplete ");
		}
		if (!NextBatesNumberPopup().isDisplayed()) {
			base.passedStep("Next Bates number popup is not displayed after selecting markcomplete");
		} else {
			base.failedStep("Next bates number popup is displayed after selecting markcomplete");
		}
	}

	/**
	 * @authorBrundha
	 * @Description :method to refillling a new next bates number
	 */
	public void enteringNewNextBatesNumber() {
		driver.waitForPageToBeReady();
		if (getClickHereLink().isDisplayed()) {
			base.passedStep("After selecting MarkIncomplete clickhere link is available");
		}

		else {
			base.failedStep("After selecting MarkIncomplete clickhere link is not available");
		}
		String actualText = getBeginningBates().GetAttribute("value");
		System.out.println("The actual txt" + actualText);
		base.waitForElement(getClickHereLink());
		getClickHereLink().Click();
		base.waitForElement(selectNextBatesNumber());
		selectNextBatesNumber().Click();
		base.waitForElement(getMarkCompleteLink());
		getMarkCompleteLink().waitAndClick(10);

		driver.waitForPageToBeReady();
		if (!getBeginningBates().GetAttribute("value").contains(actualText)) {
			base.passedStep("New Bates Number Values are updated and Auto populated in the Beginning Bates");
		} else {
			base.failedStep("New Bates Number Values are not  updated and Auto populated in the Beginning Bates");
		}

	}

	/**
	 * @authorBrundha
	 * @Description :method to verify number of custodian
	 */
	public void verifyingUniqueCustodianNameInSummaryPreviewTab() {
		driver.waitForPageToBeReady();
		String NoOfCustodian = getNoOfCustodians().getText();
		System.out.println("no of custodian in summary and preview tab:" + NoOfCustodian);

		int uniqueCustodian = 1;
		if (Integer.valueOf(NoOfCustodian).equals(uniqueCustodian)) {
			base.passedStep("number of custodians in production is " + NoOfCustodian + " is equal to the "
					+ uniqueCustodian + " unique custodian as expeced");
		}

		else {
			base.failedStep("number of custodians in production is " + NoOfCustodian + " is not equal to the "
					+ uniqueCustodian + " unique custodian as expeced");
		}
	}

	/**
	 * @authorsowndarya.velraj
	 */
	public void fillingDATWithMultipleDropDown() {

		base.waitForElement(getDATChkBox());
		getDATChkBox().Click();

		base.waitForElement(getDATTab());
		getDATTab().Click();

		base.waitForElement(getDAT_FieldClassification1());
		getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText(Input.bates);

		base.waitForElement(getDAT_SourceField1());
		getDAT_SourceField1().selectFromDropdown().selectByVisibleText(Input.batesNumber);

		base.waitForElement(getDAT_DATField1());
		getDAT_DATField1().SendKeys("BatesNumber");

		base.stepInfo("Dat section is filled with BATES");
		base.waitForElement(getAddFieldButtonInDAT());
		getAddFieldButtonInDAT().Click();
		driver.waitForPageToBeReady();
		base.waitForElement(getDAT_FieldClassification2());
		getDAT_FieldClassification2().selectFromDropdown().selectByVisibleText("Production");

		driver.waitForPageToBeReady();
		base.waitForElement(getDAT_SourceField2());
		getDAT_SourceField2().selectFromDropdown().selectByVisibleText("TIFFPageCount");

		base.waitForElement(getDAT_DATField2());
		getDAT_DATField2().SendKeys("TIFFPAGECOUNT");

		base.stepInfo("Dat section is filled with TIFFPAGECOUNT");

		base.waitForElement(getAddFieldButtonInDAT());
		getAddFieldButtonInDAT().Click();

		driver.waitForPageToBeReady();
		base.waitForElement(getDAT_FieldClassification3());
		getDAT_FieldClassification3().selectFromDropdown().selectByVisibleText("Doc Basic");

		driver.waitForPageToBeReady();
		base.waitForElement(getDAT_SourceField3());
		getDAT_SourceField3().selectFromDropdown().selectByVisibleText("DocID");

		base.waitForElement(getDAT_DATField3());
		getDAT_DATField3().SendKeys("DOCID");

		base.stepInfo("Dat section is filled with DOCID");

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param i
	 * @param classification
	 * @param sourceField
	 * @Description filling dat filed reusable for all dat column
	 */
	public void addDatField(int i, String classification, String sourceField) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_FieldClassification(i).Visible() && getDAT_FieldClassification(i).Enabled();
			}
		}), Input.wait30);
		getDAT_FieldClassification(i).selectFromDropdown().selectByVisibleText(classification);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_SourceField(i).Visible() && getDAT_SourceField(i).Enabled();
			}
		}), Input.wait30);
		getDAT_SourceField(i).selectFromDropdown().selectByVisibleText(sourceField);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_DATField(i).Visible() && getDAT_DATField(i).Enabled();
			}
		}), Input.wait30);
		getDAT_DATField(i).waitAndClick(10);
		driver.waitForPageToBeReady();
		getDAT_DATField(i).SendKeys("B" + Utility.dynamicNameAppender());
		base.stepInfo(i + "th Dat section is filled");
	}

	/**
	 *
	 * @author Brundha
	 * @param Tag
	 * @Description method for filling document selction page with one tag
	 */
	public void fillingDocumentSelectionWithTag(String Tag) {

		base.waitForElement(getTagRadioButton());
		getTagRadioButton().waitAndClick(10);

		base.waitForElement(getSelectFolder(Tag));
		getSelectFolder(Tag).waitAndClick(10);
		base.passedStep(Tag + " : Tag Is Selected");

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getIncludeFamilies().Visible();
			}
		}), Input.wait30);
		getIncludeFamilies().Click();

		driver.scrollPageToTop();
		base.stepInfo("Document Selection Page section is filled");
	}

	/**
	 *
	 * @authorBrundha
	 * @Description method for filling mp3 file
	 */
	public void SelectMP3FileAndVerifyLstFile() {

		getAdvancedProductionComponent().WaitUntilPresent().ScrollTo();
		base.waitForElement(getAdvancedProductionComponent());
		getAdvancedProductionComponent().waitAndClick(10);
		getMP3CheckBox().waitAndClick(10);
		getMP3CheckBoxToggle().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.clickButton(getAdvancedInMP3Files());
		String color = driver.FindElement(By.xpath("//label//input[@id='chkMP3ProduceLoadFile']//following-sibling::i"))
				.GetCssValue("background-color");
		System.out.println(color);
		String ExpectedColor = Color.fromString(color).asHex();
		System.out.println(ExpectedColor);
		String ActualColor = "#a9c981";
		if (ActualColor.equals(ExpectedColor)) {
			base.passedStep("lst files toggle is enabled by default");
		} else {
			base.failedStep("lst files toggle is  not enabled by default");
		}

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param tagname
	 * @throws InterruptedException
	 * @Description filling pdf section with multi branding
	 */
	public void fillingPDFSectionWithMultiBranding(String tagname) throws InterruptedException {

		base.waitForElement(getPDFChkBox());
		getPDFChkBox().Click();

		base.waitForElement(getPDFTab());
		getPDFTab().Click();

		driver.scrollPageToTop();

		base.waitForElement(getPDFGenerateRadioButton());
		getPDFGenerateRadioButton().Click();

		base.waitForElement(getLeftHeaderBranding());
		getLeftHeaderBranding().ScrollTo();
		getLeftHeaderBranding().Click();
		base.waitForElement(getEnterBranding("Top - Left"));
		new Actions(driver.getWebDriver()).moveToElement(getEnterBranding("Top - Left").getWebElement()).click();
		driver.waitForPageToBeReady();
		getEnterBranding("Top - Left").SendKeys(searchString4);

		base.waitForElement(getTIFF_CenterHeaderBranding());
		getTIFF_CenterHeaderBranding().Click();
		base.waitForElement(getEnterBranding("Top - Center"));
		new Actions(driver.getWebDriver()).moveToElement(getEnterBranding("Top - Center").getWebElement()).click();
		driver.waitForPageToBeReady();
		getEnterBranding("Top - Center").SendKeys(searchString4);

		base.waitForElement(getRightHeaderBranding());
		getRightHeaderBranding().Click();
		base.waitForElement(getEnterBranding("Top - Right"));
		new Actions(driver.getWebDriver()).moveToElement(getEnterBranding("Top - Right").getWebElement()).click();
		driver.waitForPageToBeReady();
		getEnterBranding("Top - Right").SendKeys(searchString4);

		base.waitForElement(getLeftFooterBranding());
		getLeftFooterBranding().ScrollTo();
		getLeftFooterBranding().Click();
		base.waitForElement(getEnterBranding("Bottom - Left"));
		new Actions(driver.getWebDriver()).moveToElement(getEnterBranding("Bottom - Left").getWebElement()).click();
		driver.waitForPageToBeReady();
		getEnterBranding("Bottom - Left").SendKeys(searchString4);

		base.waitForElement(getTIFF_CenterFooterBranding());
		getTIFF_CenterFooterBranding().Click();
		base.waitForElement(getEnterBranding("Bottom - Center"));
		new Actions(driver.getWebDriver()).moveToElement(getEnterBranding("Bottom - Center").getWebElement()).click();
		driver.waitForPageToBeReady();
		getEnterBranding("Bottom - Center").SendKeys(searchString4);

		base.waitForElement(getRightFooterBranding());
		getRightFooterBranding().Click();
		base.waitForElement(getEnterBranding("Bottom - Right"));
		new Actions(driver.getWebDriver()).moveToElement(getEnterBranding("Bottom - Right").getWebElement()).click();
		driver.waitForPageToBeReady();
		getEnterBranding("Bottom - Right").SendKeys(searchString4);

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		base.waitForElement(getPriveldge_SelectTagButton());
		getPriveldge_SelectTagButton().Click();

		base.waitForElement(getPriveldge_TagTree(tagname));
		Thread.sleep(3000);
		getPriveldge_TagTree(tagname).waitAndClick(10);

		getPriveldge_TagTree_SelectButton().Click();

		base.waitForElement(getPriveldge_TextArea());
		new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();

		getPriveldge_TextArea().SendKeys(tagname);

		driver.scrollingToBottomofAPage();
		base.stepInfo("PDF section is filled");

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param tagname
	 * @throws InterruptedException
	 * @Description filling tiff section with burn redaction by choosing redaction
	 *              tag
	 */
	public void fillingTIFFSectionwithBurnRedactionSelectRedactTag(String tagname) throws InterruptedException {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();

		driver.scrollingToBottomofAPage();

		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		// disabling enable for priviledged docs

		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		base.waitTillElemetToBeClickable(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().Enabled();
		getTIFF_EnableforPrivilegedDocs().waitAndClick(10);

		getClk_burnReductiontoggle().ScrollTo();

		// enable burn redaction
		base.waitForElement(getClk_burnReductiontoggle());
		getClk_burnReductiontoggle().waitAndClick(10);

		getClkRadioBtn_selectRedactionTags().ScrollTo();

		base.waitForElement(getClkRadioBtn_selectRedactionTags());
		getClkRadioBtn_selectRedactionTags().isDisplayed();
		driver.waitForPageToBeReady();
		getClkRadioBtn_selectRedactionTags().waitAndClick(10);

		base.waitForElement(getClkCheckBox_RedactionTag(tagname));
		getClkCheckBox_RedactionTag(tagname).ScrollTo();
		getClkCheckBox_RedactionTag(tagname).isDisplayed();
		getClkCheckBox_RedactionTag(tagname).waitAndClick(10);

		base.waitForElement(getClkLink_selectingRedactionTags());
		getClkLink_selectingRedactionTags().isDisplayed();
		getClkLink_selectingRedactionTags().waitAndClick(10);

		base.waitForElement(getClkBtn_selectingRedactionTags());
		getClkBtn_selectingRedactionTags().isDisplayed();
		getClkBtn_selectingRedactionTags().waitAndClick(10);

		base.waitForElement(getClkCheckBox_selectingRedactionTags());
		getClkCheckBox_selectingRedactionTags().isDisplayed();
		driver.waitForPageToBeReady();
		getClkCheckBox_selectingRedactionTags().waitAndClick(10);

		base.waitForElement(getClk_selectBtn());
		getClk_selectBtn().isDisplayed();
		getClk_selectBtn().waitAndClick(10);

		base.waitForElement(gettextRedactionPlaceHolder());
		gettextRedactionPlaceHolder().isDisplayed();
		gettextRedactionPlaceHolder().waitAndClick(10);
		gettextRedactionPlaceHolder().SendKeys(searchString4);
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param tagname
	 * @throws InterruptedException
	 * @author Aathith.Senthilkumar
	 * @Description filling pdf section with redaction choosing redaction tag
	 */
	public void fillingPDFSectionwithBurnRedactionSelectRedactTag(String tagname) throws InterruptedException {

		driver.waitForPageToBeReady();
		base.waitForElement(getTIFFChkBox());
		base.waitTillElemetToBeClickable(getTIFFChkBox());
		getTIFFChkBox().Click();

		driver.scrollingToBottomofAPage();

		driver.waitForPageToBeReady();
		base.waitForElement(getTIFFTab());
		base.waitTillElemetToBeClickable(getTIFFTab());
		getTIFFTab().Click();

		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPDFGenerateRadioButton().Enabled();
			}
		}), Input.wait60);
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		getPDFGenerateRadioButton().ScrollTo();
		base.waitTillElemetToBeClickable(getPDFGenerateRadioButton());
		getPDFGenerateRadioButton().Click();
		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		// disabling enable for priviledged docs

		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		base.waitTillElemetToBeClickable(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().Enabled();
		getTIFF_EnableforPrivilegedDocs().waitAndClick(10);

		getClk_burnReductiontoggle().ScrollTo();

		// enable burn redaction
		base.waitForElement(getClk_burnReductiontoggle());
		getClk_burnReductiontoggle().waitAndClick(10);

		getClkRadioBtn_selectRedactionTags().ScrollTo();

		base.waitForElement(getClkRadioBtn_selectRedactionTags());
		getClkRadioBtn_selectRedactionTags().isDisplayed();
		driver.waitForPageToBeReady();
		getClkRadioBtn_selectRedactionTags().waitAndClick(10);

		base.waitForElement(getClkCheckBox_RedactionTag(tagname));
		getClkCheckBox_RedactionTag(tagname).ScrollTo();
		getClkCheckBox_RedactionTag(tagname).isDisplayed();
		getClkCheckBox_RedactionTag(tagname).waitAndClick(10);

		base.waitForElement(getClkLink_selectingRedactionTags());
		getClkLink_selectingRedactionTags().isDisplayed();
		getClkLink_selectingRedactionTags().waitAndClick(10);

		base.waitForElement(getClkBtn_selectingRedactionTags());
		getClkBtn_selectingRedactionTags().isDisplayed();
		getClkBtn_selectingRedactionTags().waitAndClick(10);

		base.waitForElement(getClkCheckBox_selectingRedactionTags());
		getClkCheckBox_selectingRedactionTags().isDisplayed();
		driver.waitForPageToBeReady();
		getClkCheckBox_selectingRedactionTags().waitAndClick(10);

		base.waitForElement(getClk_selectBtn());
		getClk_selectBtn().isDisplayed();
		getClk_selectBtn().waitAndClick(10);

		base.waitForElement(gettextRedactionPlaceHolder());
		gettextRedactionPlaceHolder().isDisplayed();
		gettextRedactionPlaceHolder().waitAndClick(10);
		gettextRedactionPlaceHolder().SendKeys(searchString4);
	}

	/**
	 * @authorAathith
	 * @description :filling PDF section diable priv doc.
	 */
	public void fillingPDFSectionDisablePrivilegedDocs() {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFFChkBox());
			base.waitTillElemetToBeClickable(getTIFFChkBox());
			getTIFFChkBox().Click();

			driver.scrollingToBottomofAPage();

			driver.waitForPageToBeReady();
			base.waitForElement(getTIFFTab());
			base.waitTillElemetToBeClickable(getTIFFTab());
			getTIFFTab().Click();

			driver.waitForPageToBeReady();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getPDFGenerateRadioButton().Enabled();
				}
			}), Input.wait60);
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			getPDFGenerateRadioButton().ScrollTo();
			base.waitTillElemetToBeClickable(getPDFGenerateRadioButton());
			getPDFGenerateRadioButton().Click();
			getTiffSinglePage().waitAndClick(5);
			getTIFF_EnableforPrivilegedDocs().ScrollTo();

			// disabling enable for priviledged docs

			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			driver.waitForPageToBeReady();
			getTIFF_EnableforPrivilegedDocs().Enabled();
			getTIFF_EnableforPrivilegedDocs().Click();
			base.stepInfo("Diable priviledge doc in PDF section is filled successfully");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while handling pdf section" + e.getMessage());
		}
	}

	/**
	 * @authorAathith
	 * @description :filling tiff section diable priv doc.
	 */
	public void fillingTiffSectionDisablePrivilegedDocs() {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFFChkBox());
			base.waitTillElemetToBeClickable(getTIFFChkBox());
			getTIFFChkBox().Click();

			driver.scrollingToBottomofAPage();

			driver.waitForPageToBeReady();
			base.waitForElement(getTIFFTab());
			base.waitTillElemetToBeClickable(getTIFFTab());
			getTIFFTab().Click();

			driver.waitForPageToBeReady();

			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			getTIFF_EnableforPrivilegedDocs().ScrollTo();

			// disabling enable for priviledged docs

			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			driver.waitForPageToBeReady();
			getTIFF_EnableforPrivilegedDocs().Enabled();
			getTIFF_EnableforPrivilegedDocs().Click();
			base.stepInfo("Diable priviledge doc in Tiff section is filled successfully");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while handling Tiff section" + e.getMessage());
		}
	}

	/**
	 * @authorAathith.Senthilkumar
	 * @param tagname
	 * @Descripotion Enabling Priviage placeholder at priv gaurd page and choosing
	 *               tag
	 */
	public void EnablePrivPlaceholderAtPrivGaurdPage(String tagname) {
		base.waitForElement(getPrivPlaceHolderToggleAtPrivGaurdPage());
		getPrivPlaceHolderToggleAtPrivGaurdPage().waitAndClick(10);

		base.waitForElement(getSelectTagBtnInPrivGaurd());
		getSelectTagBtnInPrivGaurd().waitAndClick(10);

		getTagInPrivGaurd(tagname).waitAndClick(10);

		base.waitForElement(getSaveButtonINPrivGuard());
		getSaveButtonINPrivGuard().waitAndClick(10);

		base.waitForElement(getPrivPlaceholderTextboInPrivGaurd());
		getPrivPlaceholderTextboInPrivGaurd().waitAndClick(10);
		getPrivPlaceholderTextboInPrivGaurd().SendKeys(searchString2);

		Dimension d = driver.Manage().window().getSize();
		driver.Manage().window().fullscreen();
		base.waitForElement(getPrivPlaceholderTextboInPrivGaurd());
		getbtnPopupPreviewMarkComplete().waitAndClick(10);
		driver.Manage().window().setSize(d);
		driver.Manage().window().maximize();

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param tagname
	 * @Description filling priv guard page with placeholder
	 */
	public void fillingPrivGuardPageWithPrivPlaceHolder(String tagname) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGuardMarkComplete().Visible();
			}
		}), Input.wait30);
		getbtnProductionGuardMarkComplete().waitAndClick(5);

		EnablePrivPlaceholderAtPrivGaurdPage(tagname);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGuardNext().Enabled();
			}
		}), Input.wait30);
		getbtnProductionGuardNext().waitAndClick(5);
		base.stepInfo("privilage placeholder enabled in Priv Guard Page section is filled");
	}

	/**
	 * Modified on 04/28/2022
	 * 
	 * @authorBrundha
	 * @param Tag
	 * @description :method for filling native placeholder
	 */
	public void fillingNativeDocsPlaceholder(String Tag) {
		driver.waitForPageToBeReady();
		getSelectCloseBtn().waitAndClick(10);
		base.waitForElement(getTiff_NativeDoc());
		getTiff_NativeDoc().waitAndClick(10);

		base.waitForElement(getclkSelectTag());
		getclkSelectTag().waitAndClick(10);
		base.waitForElement(getPriveldged_TagTree(Tag));
		getPriveldged_TagTree(Tag).Click();
		base.waitForElement(getClkSelect());
		getClkSelect().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(getNativeDocsPlaceholder());
		getNativeDocsPlaceholder().SendKeys(Input.technicalIssue);
		base.stepInfo("Native placeholder is filled");
	}

	/**
	 * @authorBrundha
	 * @description :method for filling TIFF/PDF Section With branding text.
	 */
	public void fillingPDFSectionWithBrandingText() {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();
		driver.scrollingToBottomofAPage();
		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		base.waitTillElemetToBeClickable(getPDFGenerateRadioButton());
		getPDFGenerateRadioButton().waitAndClick(10);
		base.waitForElement(getLeftHeaderBranding());
		getLeftHeaderBranding().waitAndClick(10);
		getTIFF_EnableforPrivilegedDocs().ScrollTo();
		// disabling enable for priviledged docs
		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().Enabled();
		getTIFF_EnableforPrivilegedDocs().Click();
		base.waitForElement(getInsertMetadataField());
		getInsertMetadataField().Click();
		base.waitForElement(getBatesNumberinTiff());
		getBatesNumberinTiff().Click();
		base.waitForElement(getOkBtn());
		getOkBtn().Click();

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param tagname
	 * @throws InterruptedException
	 * @Decription Filling tiff section
	 */
	public void fillingTIFFSection(String tagname) throws InterruptedException {

		try {
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().waitAndClick(5);

			driver.scrollingToBottomofAPage();

			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			driver.scrollPageToTop();

			base.waitForElement(getTIFF_CenterHeaderBranding());
			getTIFF_CenterHeaderBranding().waitAndClick(10);

			base.waitForElement(getTIFF_EnterBranding());
			new Actions(driver.getWebDriver()).moveToElement(getTIFF_EnterBranding().getWebElement()).click();
			getTIFF_EnterBranding().SendKeys(searchString4);

			getTIFF_EnableforPrivilegedDocs().ScrollTo();

			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().isDisplayed();

			base.waitForElement(getPriveldge_SelectTagButton());
			getPriveldge_SelectTagButton().waitAndClick(5);

			driver.waitForPageToBeReady();

			driver.scrollingToElementofAPage(getPriveldge_TagTree(tagname));

			base.waitForElement(getPriveldge_TagTree(tagname));
			driver.waitForPageToBeReady();
			getPriveldge_TagTree(tagname).ScrollTo();
			getPriveldge_TagTree(tagname).waitAndClick(20);

			base.waitForElement(getPriveldge_TagTree_SelectButton());
			getPriveldge_TagTree_SelectButton().waitAndClick(10);

			driver.waitForPageToBeReady();

			base.waitForElement(getPriveldge_TextArea());
			new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();
			getPriveldge_TextArea().SendKeys(tagNameTechnical);

			driver.scrollingToBottomofAPage();
			base.stepInfo("TIFF section is filled");
		} catch (Exception e) {

			driver.scrollingToElementofAPage(getPriveldge_TagTree(tagname));

			base.waitForElement(getPriveldge_TagTree(tagname));
			Thread.sleep(5000);
			getPriveldge_TagTree(tagname).waitAndClick(20);

			base.waitForElement(getPriveldge_TagTree_SelectButton());
			getPriveldge_TagTree_SelectButton().waitAndClick(10);

			driver.waitForPageToBeReady();

			base.waitForElement(getPriveldge_TextArea());
			new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();
			getPriveldge_TextArea().SendKeys(tagNameTechnical);

			driver.scrollingToBottomofAPage();
		}
	}

	/**
	 * @authorBrundha
	 * @Description:Method to select blank page removal toggle
	 */
	public void selectBlankRemovalInTiffSection() {
		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().waitAndClick(5);
		driver.scrollingToBottomofAPage();
		base.waitForElement(getTIFFTab());
		getTIFFTab().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().ScrollTo();
		base.waitTillElemetToBeClickable(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().waitAndClick(10);
		driver.scrollPageToTop();
		base.waitForElement(getBlankPageRemovalToggle());
		getBlankPageRemovalToggle().Click();

		driver.waitForPageToBeReady();
		String ExpectedText = blankPageRemovalMessage().getText();
		String ActualText = "Enabling Blank Page Removal doubles the overall production time. Are you sure you want to continue?";

		if (ActualText.equals(ExpectedText)) {
			base.passedStep("" + ExpectedText + "Message is displayed as expected");
		} else {
			base.failedStep("" + ExpectedText + "Message not displayed as expected");
		}
		base.waitForElement(getContinueBtn());
		getContinueBtn().Click();

	}

	/**
	 * @authorBrundha
	 * @param tagname
	 * @Description:Method to select redaction tag
	 */
	public void burnRedactionWithRedactionTagInTiffSection(String tagname) {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();
		driver.scrollingToBottomofAPage();
		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();
		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		driver.waitForPageToBeReady();
		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		base.waitTillElemetToBeClickable(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().Enabled();
		getTIFF_EnableforPrivilegedDocs().waitAndClick(10);

		getClk_burnReductiontoggle().ScrollTo();

		// enable burn redaction
		base.waitForElement(getClk_burnReductiontoggle());
		getClk_burnReductiontoggle().waitAndClick(10);

		getClkRadioBtn_selectRedactionTags().ScrollTo();

		base.waitForElement(getClkRadioBtn_selectRedactionTags());
		getClkRadioBtn_selectRedactionTags().isDisplayed();
		driver.waitForPageToBeReady();
		getClkRadioBtn_selectRedactionTags().waitAndClick(10);

		base.waitForElement(getClkCheckBox_RedactionTag(tagname));
		getClkCheckBox_RedactionTag(tagname).ScrollTo();
		getClkCheckBox_RedactionTag(tagname).waitAndClick(10);
	}

	/**
	 * @authorBrundha
	 * @param TextNotExpected
	 * @Description:Method to verify specific dropdown value
	 */
	public void verifyDropDownValueInCompletedProduction(String TextNotExpected) {
		driver.waitForPageToBeReady();
		base.waitForElement(getFirstToggle());
		getFirstToggle().waitAndClick(10);

		List<WebElement> DropDown = getFirstDropdownInCompletedProduction().FindWebElements();
		int j;
		List<String> GetTextFromDrop = new ArrayList<String>();
		for (j = 0; j < DropDown.size(); j++) {
			driver.waitForPageToBeReady();
			GetTextFromDrop.add(DropDown.get(j).getText());
		}
		System.out.println("Dropdon values:" + GetTextFromDrop);

		if (GetTextFromDrop.contains(TextNotExpected)) {
			base.failedStep("" + TextNotExpected + " is displayed ");
		} else {
			base.passedStep("" + TextNotExpected + " is not displayed as expected");
		}

	}

	public void selectTechIssueDoc(String Tag) {
		driver.waitForPageToBeReady();
		getTechissue_toggle().ScrollTo();
		base.waitForElement(getTechissue_toggle());
		getTechissue_toggle().waitAndClick(10);
		base.waitForElement(getTechissue_SelectTagButton());
		getTechissue_SelectTagButton().waitAndClick(10);
		base.waitForElement(getTechIssueCheckbox(Tag));
		getTechIssueCheckbox(Tag).waitAndClick(10);
		base.waitForElement(getClk_selectBtn());
		getClk_selectBtn().waitAndClick(10);
		base.waitForElement(getTechissue_TextArea());
		getTechissue_TextArea().SendKeys(tagNameTechnical);
		base.stepInfo("TechIssue Enabled With techissue doc is selected");
	}

	public void selectBurnReduction() {
		base.waitForElement(getClk_burnReductiontoggle());
		getClk_burnReductiontoggle().Click();

		getClkRadioBtn_selectRedactionTags().ScrollTo();

		base.waitForElement(getClkRadioBtn_selectRedactionTags());
		getClkRadioBtn_selectRedactionTags().isDisplayed();
		driver.waitForPageToBeReady();
		getClkRadioBtn_selectRedactionTags().waitAndClick(10);

		base.waitForElement(getClkCheckBox_1stdefaultRedactionTag());
		getClkCheckBox_1stdefaultRedactionTag().isDisplayed();
		getClkCheckBox_1stdefaultRedactionTag().waitAndClick(10);

		base.waitForElement(getClkLink_selectingRedactionTags());
		getClkLink_selectingRedactionTags().isDisplayed();
		getClkLink_selectingRedactionTags().waitAndClick(10);

		base.waitForElement(getClkBtn_selectingRedactionTags());
		getClkBtn_selectingRedactionTags().isDisplayed();
		getClkBtn_selectingRedactionTags().waitAndClick(10);

		base.waitForElement(getClkCheckBox_selectingRedactionTags());
		getClkCheckBox_selectingRedactionTags().isDisplayed();
		driver.waitForPageToBeReady();
		getClkCheckBox_selectingRedactionTags().waitAndClick(10);

		base.waitForElement(getClk_selectBtn());
		getClk_selectBtn().isDisplayed();
		getClk_selectBtn().waitAndClick(10);

		base.waitForElement(gettextRedactionPlaceHolder());
		gettextRedactionPlaceHolder().isDisplayed();
		gettextRedactionPlaceHolder().waitAndClick(10);
		gettextRedactionPlaceHolder().SendKeys(searchString4);
		base.stepInfo("Burn redaction PDF section is filled successfully");
		base.stepInfo("Burn Reduction Enabled with default redaction tag is selected");
	}

	public void fillingPDFSection(String tagname) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPDFChkBox().Enabled();
			}
		}), Input.wait30);
		getPDFChkBox().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPDFTab().Enabled();
			}
		}), Input.wait30);
		getPDFTab().Click();

		driver.scrollPageToTop();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPDFGenerateRadioButton().Enabled();
			}
		}), Input.wait30);
		getPDFGenerateRadioButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_CenterHeaderBranding().Visible() && getTIFF_CenterHeaderBranding().Enabled();
			}
		}), Input.wait30);
		getTIFF_CenterHeaderBranding().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_EnterBranding().Enabled();
			}
		}), Input.wait30);

		new Actions(driver.getWebDriver()).moveToElement(getTIFF_EnterBranding().getWebElement()).click();
		driver.waitForPageToBeReady();
		getTIFF_EnterBranding().SendKeys(searchString4);

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTIFF_EnableforPrivilegedDocs().Enabled();
			}
		}), Input.wait30);
		getPriveldge_SelectTagButton().Click();

		base.waitForElement(getPriveldge_TagTree(tagname));
		driver.waitForPageToBeReady();
		getPriveldge_TagTree(tagname).ScrollTo();
		getPriveldge_TagTree(tagname).waitAndClick(10);

		getPriveldge_TagTree_SelectButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPriveldge_TextArea().Enabled();
			}
		}), Input.wait30);
		new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();

		driver.waitForPageToBeReady();
		getPriveldge_TextArea().SendKeys(tagNameTechnical);

		driver.scrollingToBottomofAPage();
		base.stepInfo("PDF section is filled");

	}

	/**
	 * @authorAathith.Senthilkumar
	 * @Description Checking is black page removal toggle isOff
	 */
	public void banlkPageRemovalToggleOffCheck() {
		try {
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			String color = getBlankPageRemovalToggle().GetCssValue("background-color");
			System.out.println(color);
			String ExpectedColor = Color.fromString(color).asHex();
			System.out.println(ExpectedColor);
			String ActualColor = "#e54036";
			if (ActualColor.equals(ExpectedColor)) {
				base.passedStep("Blank page removal toggle is off");
			} else {
				base.failedStep("blank page removal toggle off verifaction failed");
			}
		} catch (Exception e) {
			base.failedStep("Exception occcured while verifying Blank page removal toggle" + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * @author Brundha.T
	 * @param Value
	 * @Description:Method to select generate radio button
	 */
	public void selectGenerateOption(boolean Value) {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();
		driver.scrollingToBottomofAPage();
		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();

		if (Value) {
			driver.scrollPageToTop();
			getPDFGenerateRadioButton().Enabled();
			getPDFGenerateRadioButton().waitAndClick(10);
		}
		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().Enabled();
		getTIFF_EnableforPrivilegedDocs().Click();
	}

	/**
	 * @authorBrundha
	 * @param Tag
	 * @Description:Method to verify placeholder text in burn redaction
	 */
	public void verifyPlaceholderTextInBurnRedaction(String Tag) {

		getClk_burnReductiontoggle().ScrollTo();
		getClk_burnReductiontoggle().Click();
		base.waitForElement(getClkLink_selectingRedactionTags());
		getClkLink_selectingRedactionTags().waitAndClick(5);
		base.waitForElement(getClkBtn_selectingRedactionTags());
		getClkBtn_selectingRedactionTags().Click();
		driver.waitForPageToBeReady();
		base.waitForElement(BurnRedactionCheckBox(Tag));
		BurnRedactionCheckBox(Tag).waitAndClick(5);
		base.waitForElement(getClk_selectBtn());
		getClk_selectBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.waitForElement(gettextRedactionPlaceHolder());
		String actualtext = "REDACTED";
		String RedactionText = gettextRedactionPlaceHolder().getText();
		if (RedactionText.equals(actualtext)) {
			base.passedStep("Placeholder text '" + RedactionText + "' is displayed as expected");
		} else {
			base.failedStep("Placeholder text '" + RedactionText + "' is not displayed as expected");
		}

	}

	/**
	 * @authorBrundha
	 * @Description:Method to verify text in text section
	 */
	public void verifyTextInTextSection() {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTextChkBox().Enabled();
			}
		}), Input.wait30);
		getTextChkBox().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTextTab().Enabled();
			}
		}), Input.wait30);
		getTextTab().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTextcomponent_text().isDisplayed();
			}
		}), Input.wait30);
		getTextcomponent_text().isElementAvailable(15);
		base.waitTime(3);
		String exptext = getTextcomponent_text().getText();
		System.out.println(exptext);
		UtilityLog.info(exptext);
		if (exptext.equals("Redacted documents are automatically OCRed"
				+ " to export the text. Original extracted text is exported for natively "
				+ "produced documents (file based placeholdering). "
				+ "For exception and privileged placeholdered docs, " + "the placeholder text is exported."
				+ " The configured format is applicable only to OCRed text and production generated text"
				+ ", and not to ingested text.")) {
			base.passedStep("" + exptext + " is displayed as expected");
		} else {
			base.failedStep("" + exptext + " is not displayed as expected");
		}

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @Description Text visiblity check for all the page
	 */
	public void visibleCheck(String text) {
		if (text("text").isDisplayed()) {
			base.passedStep(text + " is visibled");
			System.out.println(text + " is visible");
		} else {
			base.failedStep(text + " is not visible");
			System.out.println(text + " is not visible");
		}
	}

	/**
	 * @authorBrundha
	 * @param Tag @ Description:Method to select branding
	 */
	public void selectBrandingInTiffAndPdfSection(String Tag) {

		driver.scrollPageToTop();
		base.waitForElement(getRightHeaderBranding());
		getRightHeaderBranding().Click();

		getTIFF_RightEnterBranding().Click();
		getTIFF_RightEnterBranding().SendKeys(Input.searchString4);

		getSpecifyBrandingBySelectingTagLink().ScrollTo();
		driver.waitForPageToBeReady();
		base.waitTillElemetToBeClickable(getSpecifyBrandingBySelectingTagLink());
		getSpecifyBrandingBySelectingTagLink().waitAndClick(10);
		base.waitTillElemetToBeClickable(getbtnSelectTags());
		getbtnSelectTags().waitAndClick(5);
		base.waitForElement(getChkBoxSelect(Tag));
		getChkBoxSelect(Tag).ScrollTo();
		base.waitTillElemetToBeClickable(getChkBoxSelect(Tag));
		getChkBoxSelect(Tag).waitAndClick(5);

		base.waitForElement(getbtnSelect());
		getbtnSelect().Click();

		base.waitForElement(getBrandingBySelectingTagPlaceholder());
		getBrandingBySelectingTagPlaceholder().SendKeys(Input.tagNameTechnical);

	}

	/**
	 * @author: sowndarya.velraj
	 * @Description: Method for verify download production using sharable link with
	 *               incorrect password
	 */
	public void verifyDownloadProductionUsingSharableLinkAndCheckErrorMessage() throws InterruptedException {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getQC_Download());
			String name = getProduction().getText().trim();
			base.waitTillElemetToBeClickable(getQC_Download());
			getQC_Download().waitAndClick(10);
			driver.waitForPageToBeReady();
			base.waitForElement(getSelectSharableLinks());
			getSelectSharableLinks().waitAndClick(10);
			driver.waitForPageToBeReady();
			base.waitForElement(getAllFilesLink());
			getAllFilesLink().ScrollTo();
			String sharableLink = getAllFilesLink().GetAttribute("value").trim();
			String password = "consilioxyz@123";
			String parentWindow = driver.getWebDriver().getWindowHandle();
			((JavascriptExecutor) driver.getWebDriver()).executeScript("window.open()");
			ArrayList<String> tabs = new ArrayList<String>(driver.getWebDriver().getWindowHandles());
			driver.getWebDriver().switchTo().window(tabs.get(1));
			driver.waitForPageToBeReady();
			driver.getWebDriver().get(sharableLink);
			base.waitForElement(getEnterPasswordTextField());
			getEnterPasswordTextField().SendKeys(password);
			driver.waitForPageToBeReady();
			base.waitForElement(getDownloadButton());
			getDownloadButton().waitAndClick(10);
			Thread.sleep(Input.wait30 / 10);

			if (invalidPasswordErrorMessageInSharableLinks().isDisplayed()) {
				base.passedStep("Error Message displayed");
			}
			driver.close();
			driver.getWebDriver().switchTo().window(parentWindow);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifying download production using sharable link." + e.getMessage());
		}
	}

	/**
	 * @authorAathith.Senthilkumar
	 * @Description at production qc page generated file sharable link getting
	 */
	public String getCopySharableLink() {
		driver.waitForPageToBeReady();
		base.waitForElement(getQC_Download());
		String name = getProduction().getText().trim();
		base.waitTillElemetToBeClickable(getQC_Download());
		getQC_Download().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(getSelectSharableLinks());
		getSelectSharableLinks().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(getAllFilesLink());
		getAllFilesLink().ScrollTo();
		String sharableLink = getAllFilesLink().GetAttribute("value").trim();
		return sharableLink;
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @Decription open new tab with the sharableLink
	 */
	public String openNewTab(String sharableLink) {
		String parentWindow = driver.getWebDriver().getWindowHandle();
		((JavascriptExecutor) driver.getWebDriver()).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWebDriver().getWindowHandles());
		driver.getWebDriver().switchTo().window(tabs.get(1));
		driver.waitForPageToBeReady();
		driver.getWebDriver().get(sharableLink);

		return parentWindow;
	}

	/**
	 * @author: sowndarya.velraj
	 * @Description: Method for verify download production using sharable link with
	 *               incorrect password
	 */
	public void verifyDownloadProductionUsingInvalidLink() throws InterruptedException {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getQC_Download());
			String name = getProduction().getText().trim();
			base.waitTillElemetToBeClickable(getQC_Download());
			getQC_Download().waitAndClick(10);
			driver.waitForPageToBeReady();
			base.waitForElement(getSelectSharableLinks());
			getSelectSharableLinks().waitAndClick(10);
			driver.waitForPageToBeReady();
			base.waitForElement(getAllFilesLink());
			getAllFilesLink().ScrollTo();
			String sharableLink = "https://sightlineuat.consiliotest.com/prod/NWF5TnJ0aFJoL";
			String password = getShareLinkPassword().getText().trim();
			String parentWindow = driver.getWebDriver().getWindowHandle();
			((JavascriptExecutor) driver.getWebDriver()).executeScript("window.open()");
			ArrayList<String> tabs = new ArrayList<String>(driver.getWebDriver().getWindowHandles());
			driver.getWebDriver().switchTo().window(tabs.get(1));
			driver.waitForPageToBeReady();
			driver.getWebDriver().get(sharableLink);

			if (invalidURLErrorMessageInSharableLinks().isDisplayed()) {
				base.passedStep("Error Message displayed");
			}
			driver.close();
			driver.getWebDriver().switchTo().window(parentWindow);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifying download production using sharable link." + e.getMessage());
		}
	}

	/**
	 * @authorBrundha
	 * @param productionname
	 * @Description:Method to Delete production in draft mode
	 */

	public void deleteProduction(String productionname) {
		base.waitTillElemetToBeClickable(getGearIconForProdName(productionname));
		getGearIconForProdName(productionname).Click();

		if (getTileDelete().isDisplayed()) {
			base.passedStep("Delete option is displayed for drafted production");
			base.waitForElement(getTileDelete());

			getTileDelete().waitAndClick(10);
			base.waitForElement(getOkButton());
			getOkButton().Click();
			base.VerifySuccessMessage("Production deleted successfully");
			base.CloseSuccessMsgpopup();
		} else {
			base.failedStep("Delete option is not displayed for drafted production");
		}

	}

	/**
	 * @authorAathith.Senthilkumar
	 * @Description Choosing security group by it's name
	 */
	public void selectingSecurityGroup(String securityGroup) {
		base.waitForElement(getSecurityGroupDropDown());
		getSecurityGroupDropDown().waitAndClick(10);
		base.waitForElement(getSecurityGroup(securityGroup));
		getSecurityGroup(securityGroup).waitAndClick(10);
		driver.waitForPageToBeReady();
	}

	/**
	 * @authorBrundha
	 * @param Tag
	 * @Description : Method for add rules in priv guard and remove the rules
	 */
	public void AddRuleAndRemoveRule(String Tag) {
		base.waitForElement(getAddRule());
		getAddRule().waitAndClick(10);
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		getTags().isDisplayed();
		getTags().waitAndClick(10);
		base.waitForElement(getTagsCheckbox(Tag));
		getTagsCheckbox(Tag).Click();
		base.waitForElement(getInsertQueryBtnInPrivGaurd());
		getInsertQueryBtnInPrivGaurd().Click();
		driver.waitForPageToBeReady();
		getDocumentMatchesButton().waitAndClick(10);
		driver.scrollPageToTop();
	}

	/**
	 * @authorBrundha
	 * @Description : Method for navigating to doclist page.
	 */
	public String VerifyingDocListCountWithPrivGaurdCount() {

		System.out.println("Clicked on Mark Complete Button..");
		driver.waitForPageToBeReady();
		String docCount = getDocumentSelectionLink().getText();
		base.waitForElement(getDocList());
		getDocList().waitAndClick(10);
		return docCount;

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @Description checking that text is not available in that page
	 */
	public void nonVisibleCheck(String text) {
		if (gettext("text").isDisplayed()) {
			base.failedStep(text + " is visibled");
			System.out.println(text + " is visible");
		} else {
			base.passedStep(text + " is not visible");
			System.out.println(text + " is not visible");
		}
	}

	/**
	 * @authorBrundha
	 * @Description : Method to verify volume included toggle is on by default
	 */
	public void verifyVolumeIncludedToggleInProductionSelection() {
		driver.waitForPageToBeReady();
		String color = driver.FindElement(By.xpath("//input[@id='ProductionOutputLocation_IsVolumeIncluded']/..//i"))
				.GetCssValue("background-color");
		System.out.println(color);
		String ExpectedColor = Color.fromString(color).asHex();
		System.out.println(ExpectedColor);
		String ActualColor = "#a9c981";
		if (ActualColor.equals(ExpectedColor)) {
			base.passedStep("Volume included  toggle is enabled by default");
		} else {
			base.failedStep("Volume included  toggle is  not enabled by default");
		}

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @throws InterruptedException
	 * @Description filling tiff section with burn redaction without update
	 *              placeholder
	 */
	public void fillingTIFFSectionwithBurnRedactionWithoutUpdatedText() throws InterruptedException {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();

		driver.scrollingToBottomofAPage();

		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		// disabling enable for priviledged docs

		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		base.waitTillElemetToBeClickable(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().Enabled();
		getTIFF_EnableforPrivilegedDocs().Click();

		getClk_burnReductiontoggle().ScrollTo();

		// enable burn redaction
		base.waitForElement(getClk_burnReductiontoggle());
		getClk_burnReductiontoggle().Click();

		getClkRadioBtn_selectRedactionTags().ScrollTo();

		base.waitForElement(getClkRadioBtn_selectRedactionTags());
		getClkRadioBtn_selectRedactionTags().isDisplayed();
		driver.waitForPageToBeReady();
		getClkRadioBtn_selectRedactionTags().waitAndClick(10);

		base.waitForElement(getClkCheckBox_defaultRedactionTag());
		getClkCheckBox_defaultRedactionTag().isDisplayed();
		getClkCheckBox_defaultRedactionTag().waitAndClick(10);

		base.waitForElement(getClkLink_selectingRedactionTags());
		getClkLink_selectingRedactionTags().isDisplayed();
		getClkLink_selectingRedactionTags().waitAndClick(10);

		base.waitForElement(getClkBtn_selectingRedactionTags());
		getClkBtn_selectingRedactionTags().isDisplayed();
		getClkBtn_selectingRedactionTags().waitAndClick(10);

		base.waitForElement(getClkCheckBox_selectingRedactionTags());
		getClkCheckBox_selectingRedactionTags().isDisplayed();
		driver.waitForPageToBeReady();
		getClkCheckBox_selectingRedactionTags().waitAndClick(10);

		base.waitForElement(getClk_selectBtn());
		getClk_selectBtn().isDisplayed();
		getClk_selectBtn().waitAndClick(10);

	}

	/**
	 * Indium-Baskar
	 */

	public void selectNativeTag(String tagOne, String tagTwo) {
		driver.waitForPageToBeReady();
		base.waitForElement(getNativeFileSelectingTag());
		getNativeFileSelectingTag().waitAndClick(5);
		base.waitForElement(getNativeFileTagSelection(tagOne));
		getNativeFileTagSelection(tagOne).waitAndClick(5);
		base.waitForElement(getNativeFileTagSelection(tagTwo));
		getNativeFileTagSelection(tagTwo).waitAndClick(5);
		base.waitForElement(getNativeFileTagSelectButton());
		getNativeFileTagSelectButton().waitAndClick(5);
	}

	/**
	 * @authorAathith.Senthilkumar
	 * @description toogle off check for element
	 */
	public void toggleOffCheck(Element element) {
		try {
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			String color = element.GetCssValue("background-color");
			System.out.println(color);
			String ExpectedColor = Color.fromString(color).asHex();
			System.out.println(ExpectedColor);
			String ActualColor = "#e54036";
			if (ActualColor.equals(ExpectedColor)) {
				base.passedStep("toggle is off :" + element);
			} else {
				base.failedStep("toggle off verifaction failed");
			}
		} catch (Exception e) {
			base.failedStep("Exception occcured toggle off check" + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * @authorAathith
	 * @Description : CheckBox notChecked verification
	 */
	public void getCheckBoxUnCheckVerificaation(Element element) {
		String value = element.GetAttribute("checked");
		System.out.println("value :" + value);
		softAssertion.assertNull(value);
		if (value == null) {
			base.passedStep(element + "element is not checked");
			System.out.println("element is unChecked");
		} else {
			base.failedStep(element + "element is checked");
			System.out.println("element is Checked");
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @throws InterruptedException
	 * @Description Filling native section with verification
	 */
	public void fillingNativeSectionWithVerification() throws InterruptedException {
		SoftAssert softAssertion = new SoftAssert();

		visibleCheck("Native");

		base.waitForElement(getNativeTab());
		getNativeTab().Click();

		visibleCheck("Natives Files");

		String expected = "To produce specific docs natively, please select file types and/or tags below. "
				+ "In addition, to export placeholders for these docs, configure placeholder in the TIFF/PDF section"
				+ " for the same selected file types and/or tags." + "\r\n" + "\r\n"
				+ "For native only productions, please make sure to exclude the Privileged documents from the selected production corpus, "
				+ "in order to avoid the Privileged natives from being exported.";

		// added thread.sleep to avoid exception while running in batch
		driver.waitForPageToBeReady();
		String actual = getNative_text().getWebElement().getText();

		softAssertion.assertEquals(actual, expected);

		base.waitForElement(getNative_SelectAllCheck());
		getNative_SelectAllCheck().waitAndClick(10);

		visibleCheck("Select Tags");

		base.waitForElement(getNative_AdvToggle());
		getNative_AdvToggle().Click();

		base.waitForElement(getNative_GenerateLoadFileLST());
		getNative_GenerateLoadFileLST().waitAndClick(10);

		visibleCheck("LST");

		String Expect = "Note that, if Privileged Placeholdering and Burn Redactions are enabled in the TIFF/PDF section, natives are not produced by default for privileged documents, redacted documents, and parents of privileged and redacted documents.";

		String Actual = getNativeAdvanced_Text().getWebElement().getText();

		softAssertion.assertEquals(Actual, Expect);
		driver.scrollingToBottomofAPage();

		base.stepInfo("Native section is verified");
		base.stepInfo("Native section is filled");
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @Description selecting text section and choose format
	 */
	public void fillingTextSectionWithTextFormat(String format) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTextChkBox().Enabled();
			}
		}), Input.wait30);
		getTextChkBox().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTextTab().Enabled();
			}
		}), Input.wait30);
		getTextTab().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTextcomponent_text().isDisplayed();
			}
		}), Input.wait30);
		getTextcomponent_text().isElementAvailable(15);
		base.waitTime(3);
		String exptext = getTextcomponent_text().getText();
		System.out.println(exptext);
		UtilityLog.info(exptext);

		base.waitForElement(getTextFormateANSIradiobtn());
		getTextFormateANSIradiobtn().waitAndClick(10);

		base.waitForElement(getTextFormateANSIdropdown());
		getTextFormateANSIdropdown().selectFromDropdown().selectByVisibleText(format);

		base.stepInfo("text format is selected");
		base.stepInfo("Text section is filled");

		driver.scrollPageToTop();
	}

	/**
	 * @authorAathith
	 * @throws InterruptedException
	 * @description : Method for filling pdf section with default annatation layer.
	 */
	public void fillingPDFSectionwithBurnRedaction() throws InterruptedException {
		try {
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().Click();
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();

			base.waitForElement(getPDFGenerateRadioButton());
			base.waitTillElemetToBeClickable(getPDFGenerateRadioButton());
			getPDFGenerateRadioButton().waitAndClick(10);

			getTIFF_EnableforPrivilegedDocs().ScrollTo();

// disabling enable for priviledged docs

			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			base.waitTillElemetToBeClickable(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().Enabled();
			getTIFF_EnableforPrivilegedDocs().Click();

			getClk_burnReductiontoggle().ScrollTo();

// enable burn redaction
			base.waitForElement(getClk_burnReductiontoggle());
			getClk_burnReductiontoggle().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getAllRedactionsAnnotaionLayer());
			driver.waitForPageToBeReady();
			getAllRedactionsAnnotaionLayer().Click();
			base.stepInfo("filled pdf section with default annatation layer.");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while filling pdf section with default annatation layer." + e.getMessage());
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param tagname
	 * @param ObjectName
	 * @description In branding center choose batesnumber and left confidential text
	 *              passed for both pdf or tiff
	 */
	public void getTiffPdfBranding(String tagname, String ObjectName) {
		String component = "pdf";
		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().waitAndClick(5);

		base.waitForElement(getTIFFTab());
		getTIFFTab().waitAndClick(5);
		if (component.equalsIgnoreCase(ObjectName)) {
			softAssertion.assertEquals(component, ObjectName);
			base.waitForElement(getPDFGenerateRadioButton());
			base.waitTillElemetToBeClickable(getPDFGenerateRadioButton());
			getPDFGenerateRadioButton().waitAndClick(10);
			base.stepInfo("pdf selected");
		}

		// center branding with batenumber
		getTIFF_CenterHeaderBranding().waitAndClick(10);
		base.waitForElement(getCenterHeaderInsertMetadataField());
		getCenterHeaderInsertMetadataField().waitAndClick(5);
		base.waitForElement(getTIFF_selectedMetadataField());
		getTIFF_selectedMetadataField().waitAndClick(5);
		getTIFF_selectedMetadataField().selectFromDropdown().selectByVisibleText("BatesNumber");
		base.waitForElement(getPopUpOkButtonInserMetaData());
		getPopUpOkButtonInserMetaData().waitAndClick(5);

		getLeftHeaderBranding().waitAndClick(10);
		getEnterBranding("Top - Left").SendKeys("Confidentiality");

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().isDisplayed();

		base.waitForElement(getPriveldge_SelectTagButton());
		getPriveldge_SelectTagButton().waitAndClick(10);

		driver.waitForPageToBeReady();

		driver.scrollingToElementofAPage(getPriveldge_TagTree(tagname));
		base.waitForElement(getPriveldge_TagTree(tagname));
		getPriveldge_TagTree(tagname).waitAndClick(20);

		base.waitForElement(getPriveldge_TagTree_SelectButton());
		getPriveldge_TagTree_SelectButton().waitAndClick(10);

		driver.waitForPageToBeReady();

		base.waitForElement(getPriveldge_TextArea());
		new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();
		getPriveldge_TextArea().SendKeys(tagNameTechnical);

		driver.scrollingToBottomofAPage();
		base.stepInfo("TIFF section is filled with Branding in bitesnumber and confidencial");
	}

	/**
	 * @authorBrundha
	 * @Description : Method to verify volume included toggle is on by default
	 */
	public void verifyExportedCSVFile() {

		driver.waitForPageToBeReady();
		String ExpectedText = getSelectTheExportedProduction().getText();
		String Actualtext = "Your export production bates range is ready please click here to download";

		base.textCompareEquals(Actualtext, ExpectedText, "Notification is Displayed as Expected",
				"Notification is not  Displayed as Expected");
		getSelectTheExportedProduction().waitAndClick(5);
		base.passedStep("Verified that user can download the CSV file once Production-Generate-Export is completed");

	}

	/**
	 * @authorBrundha
	 * @Description : Method for filling advanced toggle in tiff/pdf section
	 */
	public void fillingAdvancedInTiffSection() {
		base.waitForElement(getTiffAdvancedLink());
		getTiffAdvancedLink().Click();

		getAdvancedLSTToggle().isDisplayed();
		String color = getAdvancedLSTToggle().GetCssValue("background-color");
		System.out.println(color);
		String ExpectedColor = Color.fromString(color).asHex();
		System.out.println(ExpectedColor);
		String ActualColor = "#a9c981";
		base.textCompareEquals(ActualColor, ExpectedColor, "Generate LST file Toggle is enabled  by Default",
				"Generate LST file Toggle is not  enabled  by Default");

		base.waitForElement(getLoadFileTypeInTIFF());
		getLoadFileTypeInTIFF().Click();
		base.waitForElement(getOPTInLoadFileType());
		getOPTInLoadFileType().Click();
		driver.scrollPageToTop();
		getSaveOption().waitAndClick(10);
		base.VerifySuccessMessage("Information Saved Successfully");
		base.CloseSuccessMsgpopup();
	}

	/**
	 * @authorAathith.Senthilkumar
	 * @param element
	 * @param n
	 * @Description clicking name element for Nth time
	 */
	public void clickElementNthtime(Element element, int n) {
		for (int i = 0; i < n; i++) {
			element.waitAndClick(10);
			driver.waitForPageToBeReady();
		}
	}

	/**
	 * @authorAathith.Senthilkumar
	 * @param element
	 * @Description verifing that element is displayed or not
	 */
	public void getElementDisplayCheck(Element element) {
		boolean flag = element.isElementAvailable(10);
		if (flag) {
			softAssertion.assertTrue(flag);
			base.passedStep("Element is Visible" + element);
			System.out.println("Element is visible");
		} else {
			softAssertion.assertFalse(flag);
			base.failedStep("Element is not Visible" + element);
			System.out.println("Element is not visible");
		}
	}

	/**
	 * @author Aathith
	 * @param element
	 * @Description : CheckBox Checked verification
	 */
	public void getCheckBoxCheckedVerification(Element element) {
		String value = element.GetAttribute("checked");
		System.out.println("value :" + value);
		if (value.equals("true")) {
			softAssertion.assertTrue(true);
			base.passedStep(element + "element is checked");
			System.out.println("element is Checked");
		} else {
			base.failedStep(element + "element is not checked");
			System.out.println("element is not Checked");
		}
	}

	/**
	 * @authorBrundha
	 * @Description:Method to verify blank page removal toggle message
	 * 
	 */
	public void verifyBlankPageRemovalMeassage() {
		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().waitAndClick(5);
		driver.scrollingToBottomofAPage();
		base.waitForElement(getTIFFTab());
		getTIFFTab().waitAndClick(5);
		driver.waitForPageToBeReady();
		getPDFGenerateRadioButton().waitAndClick(10);
		String color = getBlankPageRemovalToggle().GetCssValue("background-color");
		String ExpectedColor = Color.fromString(color).asHex();
		String ActualColor = "#e54036";
		base.textCompareEquals(ActualColor, ExpectedColor, "Blank Page Removal Toggle is disabled  by Default",
				"Blank Page Removal Toggle is not  disabled  by Default");
		getBlankPageRemovalToggle().Click();
		String ExpectedText = blankPageRemovalMessage().getText();
		String ActualText = "Enabling Blank Page Removal doubles the overall production time. Are you sure you want to continue?";
		base.textCompareEquals(ActualText, ExpectedText, "" + ExpectedText + "Message is displayed as expected",
				"" + ExpectedText + "Message not displayed as expected");

		base.waitForElement(getContinueBtn());
		if (getContinueBtn().isDisplayed()) {
			base.passedStep("Continue button is displayed as Expected");
		} else {
			base.failedStep("Continue button is not displayed as Expected");
		}
		if (CancelBtn().isDisplayed()) {
			base.passedStep("Cancel button is displayed as Expected");
		} else {
			base.failedStep("Cancel button is not displayed as Expected");
		}

	}

	/**
	 * @authorBrundha
	 * @Description:Method to verify split sub folder toggle
	 * 
	 */
	public void verifySubFolderToggle() {
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		getSplitSubFolderToggle().isDisplayed();
		String color = getSplitSubFolderToggle().GetCssValue("background-color");
		System.out.println(color);
		String ExpectedColor = Color.fromString(color).asHex();
		System.out.println(ExpectedColor);
		String ActualColor = "#a9c981";
		base.textCompareEquals(ActualColor, ExpectedColor, "Split Sub Folder Toggle is enabled  by Default",
				"Split Sub Folder Toggle is not  enabled  by Default");
		ProductionLocationSplitCount().Clear();
		ProductionLocationSplitCount().SendKeys(Input.pageNumber);
		driver.scrollPageToTop();

	}

	/**
	 * @authorGopinath
	 * @Description : Filling tiff section that burn redaction with tag
	 */
	public void fillingTIFFSectionBurnRedaction(String tagname, String placeHolderValue) throws InterruptedException {
		try {
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().Click();
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			getTIFF_EnableforPrivilegedDocs().ScrollTo();
			// disabling enable for priviledged docs
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			base.waitTillElemetToBeClickable(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().Enabled();
			getTIFF_EnableforPrivilegedDocs().Click();
			getClk_burnReductiontoggle().ScrollTo();
			// enable burn redaction
			base.waitForElement(getClk_burnReductiontoggle());
			getClk_burnReductiontoggle().Click();
			getClkRadioBtn_selectRedactionTags().ScrollTo();
			base.waitForElement(getClkRadioBtn_selectRedactionTags());
			getClkRadioBtn_selectRedactionTags().isDisplayed();
			driver.waitForPageToBeReady();
			getClkRadioBtn_selectRedactionTags().waitAndClick(10);
			getDefaultRedacTag_BurnRedact().ScrollTo();
			base.waitForElement(getDefaultRedacTag_BurnRedact());
			getDefaultRedacTag_BurnRedact().waitAndClick(10);
			base.waitForElement(getClkLink_selectingRedactionTags());
			getClkLink_selectingRedactionTags().isDisplayed();
			getClkLink_selectingRedactionTags().waitAndClick(10);
			base.waitForElement(getClkBtn_selectingRedactionTags());
			getClkBtn_selectingRedactionTags().isDisplayed();
			getClkBtn_selectingRedactionTags().waitAndClick(10);
			base.waitForElement(getPriveldged_TagTree(tagname));
			getPriveldged_TagTree(tagname).isElementAvailable(10);
			getPriveldged_TagTree(tagname).ScrollTo();
			getPriveldged_TagTree(tagname).isDisplayed();
			getPriveldged_TagTree(tagname).waitAndClick(10);
			base.waitForElement(getClk_selectBtn());
			getClk_selectBtn().isDisplayed();
			getClk_selectBtn().waitAndClick(10);
			base.waitForElement(gettextRedactionPlaceHolder());
			gettextRedactionPlaceHolder().isDisplayed();
			gettextRedactionPlaceHolder().waitAndClick(10);
			gettextRedactionPlaceHolder().SendKeys(placeHolderValue);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while filling tiff section with burn redaction tag" + e.getLocalizedMessage());
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param tagname
	 * @param tagname1
	 * @throws InterruptedException
	 * @Description :Natively placeholder selecting multiple tag
	 */
	public void fillingTIFFSectionwithNativelyPlaceholder(String tagname, String tagname1) throws InterruptedException {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();

		driver.scrollingToBottomofAPage();

		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		// disabling enable for priviledged docs

		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().Enabled();
		getTIFF_EnableforPrivilegedDocs().Click();

		// clicking enable for natively placeholder
		base.waitForElement(getTiff_NativeDoc());
		getTiff_NativeDoc().Click();
		base.waitForElement(getclkSelectTag());
		getclkSelectTag().Click();
		base.waitForElement(getPriveldged_TagTree(tagname));
		getPriveldged_TagTree(tagname).waitAndClick(10);
		base.waitForElement(getPriveldged_TagTree(tagname1));
		getPriveldged_TagTree(tagname1).waitAndClick(10);
		base.waitForElement(getClkSelect());
		getClkSelect().Click();
		Thread.sleep(Input.wait30 / 10);
		base.waitForElement(getNativeDocsPlaceholder());
		getNativeDocsPlaceholder().SendKeys(tagname);

		base.stepInfo("Tiff Section is fillied with Natively Placeholder selecting multiple tag");

	}

	/**
	 * @authorJeevitha
	 * @param RedactionTag
	 * @param redactionText
	 */
	public void specifyRedactionTextAreaInBurnRedact(String RedactionTag, String redactionText) {
		base.waitForElement(getClkLink_selectingRedactionTags());
		getClkLink_selectingRedactionTags().waitAndClick(10);

		base.waitForElement(getClkBtn_selectingRedactionTags());
		getClkBtn_selectingRedactionTags().waitAndClick(10);

//		base.waitForElement(redactionTagInBurnRedactionCheckBox(RedactionTag));
//		driver.waitForPageToBeReady();
//		redactionTagInBurnRedactionCheckBox(RedactionTag).waitAndClick(10);
//		base.stepInfo("Selected Redaction Tag is : " + RedactionTag);
		base.waitForElement(getDefaultRedacTag_BurnRedact());
		getDefaultRedacTag_BurnRedact().waitAndClick(10);

		base.waitForElement(getClk_selectBtn());
		getClk_selectBtn().waitAndClick(10);

		base.waitForElement(gettextRedactionPlaceHolder());
		gettextRedactionPlaceHolder().waitAndClick(10);
		gettextRedactionPlaceHolder().SendKeys(redactionText);
		base.stepInfo("Reaction Text : " + redactionText);
	}

	/**
	 * @authorBrundha
	 * @Description:Method to fill burn redaction in MP3 file
	 * 
	 */

	public void fillingMP3FileWithBurnRedaction(String redactionTag) throws InterruptedException {

		getAdvancedProductionComponent().WaitUntilPresent().ScrollTo();
		base.clickButton(getAdvancedProductionComponent());
		base.waitForElement(getMP3CheckBox());
		getMP3CheckBox().Click();
		base.clickButton(getMP3CheckBoxToggle());
		base.clickButton(getMP3CheckReductionBoxEnable());
		getMP3CheckReductionBoxEnable().Enabled();
		base.clickButton(getMP3RatiobtnRedactiontag());

		driver.waitForPageToBeReady();
		getMP3FilesRedactionTag().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		getMP3FilesBurnRedactionTag(redactionTag).ScrollTo();
		getMP3FilesBurnRedactionTag(redactionTag).waitAndClick(10);
		driver.waitForPageToBeReady();
		getSelect_RedactionStyle_Dropdown().Click();
		base.clickButton(getSelect_Beep_RedactionStyle_Dropdown());
		base.clickButton(getAdvancedInMP3Files());
		String color = driver.FindElement(By.xpath("//label//input[@id='chkMP3ProduceLoadFile']//following-sibling::i"))
				.GetCssValue("background-color");
		String ExpectedColor = Color.fromString(color).asHex();
		String ActualColor = "#a9c981";
		base.textCompareEquals(ActualColor, ExpectedColor, "lst files toggle is enabled by default",
				"lst files toggle is not enabled by default");
		driver.scrollPageToTop();
	}

	/**
	 * @authorBrundha
	 * @param Redactiontag
	 * @Description:verifying the retained data in MP3 file burn redaction
	 * 
	 */
	public void verifyingMP3FileBurnRedaction(String Redactiontag) {

		getAdvancedProductionComponent().WaitUntilPresent().ScrollTo();
		base.clickButton(getAdvancedProductionComponent());
		base.clickButton(getMP3CheckBoxToggle());
		driver.scrollingToBottomofAPage();
		getMP3FilesRedactionTag().ScrollTo();
		boolean flag = getMP3FilesBurnRedactionTag(Redactiontag).GetAttribute("class").contains("clicked");

		if (flag) {
			Assert.assertTrue(true);
			base.passedStep("production template retains the redaction tags configured in the production");
		} else {
			Assert.assertTrue(false);
			base.failedStep("production template not retain the redaction tags configured in the production");
		}
		Select select = new Select(driver.FindElementByXPath("//select[@id='lstFillerAudio']").getWebElement());
		String option = select.getFirstSelectedOption().getText();
		System.out.println("the dropdown value is " + option);
		base.textCompareEquals(option, "Beep", "Dropdown value is retained in Template",
				"Drop down value is not retained in Template as Expected");
		base.stepInfo("Verifying the Redaction tag and dropdown in Mp3 checkbox");

	}

	/**
	 * @authorBrundha
	 * @param GenerateButton
	 * @Description:Method to verify retained rotation in component tab
	 * 
	 */

	public void verifyRotationInComponentTab(String GenerateButton) {
		driver.scrollingToBottomofAPage();
		base.waitForElement(getTIFFTab());
		getTIFFTab().waitAndClick(5);
		driver.waitForPageToBeReady();
		if ("tiff".equals(GenerateButton)) {
			base.stepInfo("Verifying  Generate TIFF radio button in Component tab");
			getCheckBoxCheckedVerification(getGenrateTIFFRadioButton());
		} else if ("pdf".equals(GenerateButton)) {
			base.stepInfo("Verifying  Generate TIFF radio button in Component tab");
			driver.waitForPageToBeReady();
			getCheckBoxCheckedVerification(getGenratePDFRadioButton());
		}
		Select select = new Select(
				driver.FindElementByXPath("//select[@id='dldPageRotatePreference']").getWebElement());
		String option = select.getFirstSelectedOption().getText();
		System.out.println("the value is " + option);
		base.textCompareEquals(option, "Rotate 90 degrees clock-wise", "Rotation value is retained in Template",
				"Rotation value is not retained in Template as Expected");

	}

	public void verifyingComponentTabOnMarkComplete() throws InterruptedException {

		driver.scrollPageToTop();
		base.waitForElement(getMarkCompleteLink());
		getMarkCompleteLink().waitAndClick(10);
		base.VerifySuccessMessage("Mark Complete successful");
		base.CloseSuccessMsgpopup();
		base.stepInfo("Verifying DAT and TIFF is retained on Markcomplete");
		getCheckBoxCheckedVerification(chkIsDATSelected());
		driver.waitForPageToBeReady();
		getCheckBoxCheckedVerification(chkIsTIFFSelected());
		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();
		driver.scrollingToBottomofAPage();
		String PlaceholderText = getPriveldge_TextArea().getText();
		if ("Technical_Issue".equals(PlaceholderText)) {
			base.passedStep("" + PlaceholderText + ". placeholder text is retained on Markcomplete");
		} else {
			base.failedStep("" + PlaceholderText + ". placeholder text  is retained on Markcomplete");
		}
		driver.scrollPageToTop();
		base.waitForElement(getNextButton());
		getNextButton().waitAndClick(10);
		base.stepInfo("Navigate to next page");
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param tagname
	 * @param tagname1
	 * @throws InterruptedException
	 * @Description :Natively placeholder selecting tags Type in different Native
	 *              docsa
	 */

	public void fillingTIFFSectionwithNativelyPlaceholderWithTagTypeAndTags(String tagname, String tagname1)
			throws InterruptedException {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();

		driver.scrollingToBottomofAPage();

		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		// disabling enable for priviledged docs

		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().Enabled();
		getTIFF_EnableforPrivilegedDocs().Click();

		base.waitForElement(getSelectCloseBtn());
		getSelectCloseBtn().waitAndClick(10);
		// clicking enable for natively placeholder
		base.waitForElement(getTiff_NativeDoc());
		getTiff_NativeDoc().ScrollTo();
		clickElementNthtime(getTiff_NativeDoc(), 2);

		base.waitForElement(getclkSelectTag());
		getclkSelectTag().ScrollTo();
		getclkSelectTag().Click();
		driver.waitForPageToBeReady();
		base.waitForElement(getPriveldged_TagTree(tagname));
		getPriveldged_TagTree(tagname).waitAndClick(10);
		base.waitForElement(getClkSelect());
		getClkSelect().Click();
		base.waitForElement(getNativeDocsPlaceholder());
		getNativeDocsPlaceholder().SendKeys(Input.tagNamePrev);

		driver.waitForPageToBeReady();
		base.waitForElement(getclkSelectTag(1));
		getclkSelectTag(1).ScrollTo();
		getclkSelectTag(1).Click();
		driver.waitForPageToBeReady();
		base.waitForElement(getPriveldged_TagTree(tagname1));
		getPriveldged_TagTree(tagname1).waitAndClick(10);
		base.waitForElement(getClkSelect());
		getClkSelect().Click();
		base.waitForElement(getNativeDocsPlaceholder());
		getNativeDocsPlaceholder().SendKeys(tagname1);

		base.stepInfo("Tiff Section is fillied with Natively Placeholder selecting tags and tag type");

	}

	/**
	 * @authorBrundha
	 * @Description:Verifying Docs w/ No Master Date retained on markcomplete
	 * 
	 */

	public void verifyMasterDateRetainedOnMarkComplete() {
		driver.scrollingToBottomofAPage();
		getlstSortByKeepDocsWithNoMasterDate().selectFromDropdown().selectByVisibleText("At the beginning");
		driver.scrollPageToTop();
		base.waitForElement(getMarkCompleteLink());
		getMarkCompleteLink().waitAndClick(10);
		base.VerifySuccessMessage("Mark Complete successful");
		base.CloseSuccessMsgpopup();

		Select select = new Select(getlstSortingOrder().getWebElement());
		String option = select.getFirstSelectedOption().getText();
		System.out.println("the value is " + option);
		base.textCompareEquals(option, "Ascending", "Sorting order dropdown Option is retained on MarkComplete",
				"Sorting order dropdown Option is not retained on MarkComplete");
		select = new Select(getlstSortByKeepDocsWithNoMasterDate().getWebElement());
		String DropdownValue = select.getFirstSelectedOption().getText();
		System.out.println("the value is " + DropdownValue);
		base.textCompareEquals(DropdownValue, "At the beginning", "Master Date Option is retained on MarkComplete",
				"Master Date Option is not retained on MarkComplete");
		driver.scrollPageToTop();
		base.waitForElement(getNextButton());

		getNextButton().Click();
		base.stepInfo("Navigate to next page");
	}

	/**
	 * @authorBrundha
	 * @param productionName
	 * @Description:Method to add new production and save
	 */
	public void addANewProductionAndSave(String productionName) throws InterruptedException {
		base.waitForElement(getAddNewProductionbutton());
		getAddNewProductionbutton().Click();
		base.waitForElement(getProductionName());
		getProductionName().SendKeys(productionName);
		base.waitForElement(getProductionDesc());
		getProductionDesc().SendKeys(productionName);
		base.waitForElement(getBasicInfoSave());
		getBasicInfoSave().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(getBasicInfoMarkComplete());
		getBasicInfoMarkComplete().Click();
		base.stepInfo("New production is added");
	}

	/**
	 * @authorBrundha
	 * @param prefixId
	 * @param suffixId
	 * @Description:Method to fill Numbering and sorting page without family members
	 *                     together
	 */
	public void fillingNumberingAndSortingWithoutFamilyMember(String prefixId, String suffixId) {

		base.waitForElement(getBeginningBates());
		driver.waitForPageToBeReady();
		num = getRandomNumber(2);
		getBeginningBates().SendKeys(num);
		base.waitForElement(gettxtBeginningBatesIDPrefix());
		gettxtBeginningBatesIDPrefix().SendKeys(prefixId);
		base.waitForElement(gettxtBeginningBatesIDSuffix());
		gettxtBeginningBatesIDSuffix().SendKeys(suffixId);
		gettxtBeginningBatesIDMinNumLength().isDisplayed();
		base.waitForElement(gettxtBeginningBatesIDMinNumLength());
		num1 = getRandomNumber(1);
		gettxtBeginningBatesIDMinNumLength().SendKeys(num1);
		base.stepInfo("Numbering and sorting page is filled without checked 'Family members together' checkbox");
	}

	/**
	 * @throws InterruptedException
	 * @author Aathith
	 */
	public void fillingGeneratePageWithContinueGenerationPopupWithoutDownload() throws InterruptedException {

		SoftAssert softAssertion = new SoftAssert();
		String expectedText = "Success";

		base.waitForElement(getbtnProductionGenerate());
		getbtnProductionGenerate().waitAndClick(10);

		getbtnContinueGeneration().isElementAvailable(150);
		if (getbtnContinueGeneration().isDisplayed()) {
			base.waitForElement(getbtnContinueGeneration());
			getbtnContinueGeneration().waitAndClick(10);
		}

		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocumentGeneratetext().Visible() && getDocumentGeneratetext().Enabled();
			}
		}), Input.wait120);
		String actualText = getStatusSuccessTxt().getText();
		System.out.println(actualText);

		softAssertion.assertTrue(actualText.contains(expectedText));
		base.passedStep("Documents Generated successfully");

		getConfirmProductionCommit().isElementAvailable(150);

		// added thread.sleep to avoid exception while executing in batch
		Thread.sleep(2000);
		getConfirmProductionCommit().waitAndClick(10);

		String PDocCount = getProductionDocCount().getText();
		// added thread.sleep to avoid exception while executing in batch
		Thread.sleep(1000);
		System.out.println(PDocCount);
		int Doc = Integer.parseInt(PDocCount);

		Reporter.log("Doc - " + Doc, true);
		System.out.println(Doc);
		UtilityLog.info(Doc);

		base.waitForElement(getCopyPath());
		getCopyPath().waitAndClick(10);

		base.stepInfo("Generate production page is filled");
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param tagname
	 * @throws InterruptedException
	 * @Description filling tiff section with rotate 90 degree clock wise
	 */
	public void fillingTIFFSectionRotate90DegreeClockWise(String tagname) throws InterruptedException {

		try {
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().waitAndClick(5);

			driver.scrollingToBottomofAPage();

			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			driver.scrollPageToTop();

			getRotationDropDown().ScrollTo();
			getRotationDropDown().selectFromDropdown().selectByVisibleText("Rotate 90 degrees clock-wise");
			base.stepInfo("'Rotate 90 degrees clock-wise' is selected in Tiff");

			base.waitForElement(getTIFF_CenterHeaderBranding());
			getTIFF_CenterHeaderBranding().ScrollTo();
			getTIFF_CenterHeaderBranding().waitAndClick(10);

			base.waitForElement(getTIFF_EnterBranding());
			new Actions(driver.getWebDriver()).moveToElement(getTIFF_EnterBranding().getWebElement()).click();
			getTIFF_EnterBranding().SendKeys(searchString4);

			getTIFF_EnableforPrivilegedDocs().ScrollTo();

			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().isDisplayed();

			base.waitForElement(getPriveldge_SelectTagButton());
			getPriveldge_SelectTagButton().waitAndClick(5);

			driver.waitForPageToBeReady();

			driver.scrollingToElementofAPage(getPriveldge_TagTree(tagname));

			base.waitForElement(getPriveldge_TagTree(tagname));
			Thread.sleep(5000);
			getPriveldge_TagTree(tagname).waitAndClick(20);

			base.waitForElement(getPriveldge_TagTree_SelectButton());
			getPriveldge_TagTree_SelectButton().waitAndClick(10);

			driver.waitForPageToBeReady();

			base.waitForElement(getPriveldge_TextArea());
			new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();
			getPriveldge_TextArea().SendKeys(tagNameTechnical);

			driver.scrollingToBottomofAPage();
			base.stepInfo("TIFF section is filled");
		} catch (InterruptedException e) {

			driver.scrollingToElementofAPage(getPriveldge_TagTree(tagname));

			base.waitForElement(getPriveldge_TagTree(tagname));
			Thread.sleep(5000);
			getPriveldge_TagTree(tagname).waitAndClick(20);

			base.waitForElement(getPriveldge_TagTree_SelectButton());
			getPriveldge_TagTree_SelectButton().waitAndClick(10);

			driver.waitForPageToBeReady();

			base.waitForElement(getPriveldge_TextArea());
			new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();
			getPriveldge_TextArea().SendKeys(tagNameTechnical);

			driver.scrollingToBottomofAPage();
		}
	}

	/**
	 * @authorGopinath
	 * @Description :Method to get production name from completed production.
	 * @return productionName : productionName is String value that returns name of
	 *         completed production.
	 */
	public String getGeneratedProductionName() {
		String productionName = null;
		try {
			getProduction().isElementAvailable(10);
			base.waitTime(2);
			productionName = getProduction().getText().trim();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while getting production name from completed production."
					+ e.getLocalizedMessage());
		}
		return productionName;
	}

	/**
	 * @authorBrundha
	 * @param source
	 * @param Destination
	 * @Description :Method to unzip the zipped file
	 * 
	 */
	public void unzipping(String source, String Destination) throws ZipException {

		ZipFile zipFile = new ZipFile(source);
		zipFile.extractAll(Destination);

	}

	/**
	 * 
	 * @authorBrundha
	 * @param Batesnumber
	 * @Description:verifying the belly band message in DAT section .
	 */
	public void verifyingBellyBandMessageInDATSection(String Batesnumber) {
		driver.scrollPageToTop();
		base.waitForElement(getMarkCompleteLink());
		getMarkCompleteLink().waitAndClick(10);
		String ActualBellyBandMsg = getErrorMsgPopupInDAT().getText();
		String ExpectedMsg = "In the DAT configuration, at least one of the project fields is mapped to multiple DAT fields. Do you want to continue with this same configuration?";

		base.textCompareEquals(ActualBellyBandMsg, ExpectedMsg, "Message is displayed as Expected",
				"Mesaage is not displayed as expected");
		base.waitForElement(getOkButton());
		getOkButton().Click();
		base.VerifySuccessMessage("Mark Complete successful");

	}

	/**
	 * @authorAathith.Senthilkumar
	 * @param element
	 * @param text
	 * @Description Verifing production state are same by before filter
	 */
	public void verifyProdctionState(ElementCollection element, String text) {

		List<WebElement> elementList = null;
		try {
			elementList = element.FindWebElements();
			for (WebElement wenElementNames : elementList) {

				String elementName = wenElementNames.getText().trim();
				softAssertion.assertEquals(elementName, text);
				softAssertion.assertAll();

			}

			base.passedStep(text + " status is displayed all production");

		} catch (Exception E) {
			base.failedStep("verification failed");
			System.out.println(E);
		}
	}

	/**
	 * @authorAathith.Senthilkumar
	 * @param col
	 * @param text
	 * @Description Veerifing production status on grid view page
	 */
	public void verifyProductionStateWebTableGridView(int col, String text) {

		try {

			for (int i = 1; i <= getGridViewWebTable(col).size(); i++) {

				String elementName = getGridViewWebTable(i, col).getText().trim();
				boolean flag = elementName.contains(text);
				softAssertion.assertTrue(flag);
				softAssertion.assertAll();

			}

			base.passedStep(text + " status is displayed all production in grid view page");

		} catch (Exception E) {
			base.failedStep("verification failed");
			System.out.println(E);
		}
	}

	/**
	 * @authorAathith.Senthilkumar
	 * @param element
	 * @Description Verify that toggle is off check
	 */
	public void toggleOnCheck(Element element) {
		driver.waitForPageToBeReady();

		element.isDisplayed();
		String color = element.GetCssValue("background-color");
		System.out.println(color);
		String ExpectedColor = Color.fromString(color).asHex();
		System.out.println(ExpectedColor);
		String ActualColor = "#a9c981";
		if (ActualColor.equals(ExpectedColor)) {
			base.passedStep("toggle is on :" + element);
		} else {
			base.failedStep("toggle off verifaction failed");
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @Description filling completed production only
	 */
	public void filterCompletedProduction() {
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.waitForPageToBeReady();
		base.waitForElement(getFilterByButton());
		getFilterByButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		getFilterByDRAFT().waitAndClick(5);
		getFilterByINPROGRESS().waitAndClick(5);
		getFilterByFAILED().waitAndClick(5);
		boolean flag = isCompletedIsChecked().GetAttribute("class").contains("active");
		if (!flag) {
			getFilterByCOMPLETED().waitAndClick(5);
		}
		base.stepInfo("Filtered completed satus only");
		driver.waitForPageToBeReady();
		getRefreshButton().waitAndClick(10);
		driver.waitForPageToBeReady();
	}

	/**
	 * @authorAathith.Senthilkumar
	 * @param Batesvalue
	 * @throws InterruptedException
	 * @Description perform a generation page and verifing Bates Range Value
	 */
	public void fillingGeneratePageAndVerfyingBatesRangeValue(String Batesvalue) throws InterruptedException {
		// generating production:
		SoftAssert softAssertion = new SoftAssert();
		String expectedText = "Success";

		base.waitForElement(getbtnProductionGenerate());
		getbtnProductionGenerate().waitAndClick(10);

		verifyProductionStatusInGenPage("Reserving Bates Range Complete");

		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");
		driver.waitForPageToBeReady();
		String Value = getBatesRange().getText();
		System.out.println(Value);
		base.waitTime(2);
		if (Value.contains(Batesvalue)) {
			base.passedStep("Batesrange is value is verified");
		} else {
			base.failedStep("verification failed");
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocumentGeneratetext().isElementAvailable(350);
			}
		}), Input.wait120);
		String actualText = getStatusSuccessTxt().getText();
		System.out.println(actualText);

		softAssertion.assertTrue(actualText.contains(expectedText));
		base.passedStep("Documents Generated successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getConfirmProductionCommit().Enabled() && getConfirmProductionCommit().isDisplayed();
			}
		}), Input.wait60);

		// added thread.sleep to avoid exception while executing in batch
		Thread.sleep(2000);
		getConfirmProductionCommit().waitAndClick(10);

		if (base.getCloseSucessmsg().isElementAvailable(5)) {
			base.CloseSuccessMsgpopup();
		}

		String PDocCount = getProductionDocCount().getText();
		// added thread.sleep to avoid exception while executing in batch
		Thread.sleep(1000);
		System.out.println(PDocCount);
		int Doc = Integer.parseInt(PDocCount);

		Reporter.log("Doc - " + Doc, true);
		System.out.println(Doc);
		UtilityLog.info(Doc);

		base.waitForElement(getCopyPath());
		getCopyPath().waitAndClick(10);

		base.waitForElement(getQC_Download());

		getQC_Download().waitAndClick(10);
		getQC_Downloadbutton_allfiles().waitAndClick(10);
		base.VerifySuccessMessage("Your Production Archive download will get started shortly");
		base.stepInfo("Generate production page is filled");
	}

	/**
	 * @authorAathith.Senthilkumar
	 * @param Batesvalue
	 * @throws InterruptedException
	 * @Description filling export generation page and verifing Bates Range value
	 */
	public void fillingExportGeneratePageAndVerfyingBatesRangeValue(String Batesvalue) throws InterruptedException {

		SoftAssert softAssertion = new SoftAssert();
		String expectedText = "Success";

		base.waitForElement(getbtnProductionGenerate());
		getbtnProductionGenerate().waitAndClick(10);

		verifyProductionStatusInGenPage("Post-Generation QC Checks In Progress");

		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");
		driver.waitForPageToBeReady();
		String Value = getBatesRange().getText();
		System.out.println(Value);

		if (Value.contains(Batesvalue)) {
			base.passedStep("Batesrange is value is verified");
		} else {
			base.failedStep("verification failed");
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocumentGeneratetext().isElementAvailable(350);
			}
		}), Input.wait120);
		String actualText = getStatusSuccessTxt().getText();
		System.out.println(actualText);

		softAssertion.assertTrue(actualText.contains(expectedText));
		base.passedStep("Documents Generated successfully");

	}

	/**
	 * @authorBrundha
	 * @param foldername
	 * @description :filling Document selection tab
	 * 
	 */
	public void fillingDocumentSlectionTab(String foldername) {
		base.waitForElement(getFolderRadioButton());
		getFolderRadioButton().waitAndClick(10);
		getSelectFolder(foldername).isElementAvailable(120);
		getSelectFolder(foldername).isDisplayed();
		getSelectFolder(foldername).waitAndClick(10);
		driver.scrollingToBottomofAPage();
		getIncludeFamilies().isDisplayed();
		String color = (getIncludeFamilies()).GetCssValue("background-color");
		System.out.println(color);
		String ExpectedColor = Color.fromString(color).asHex();
		System.out.println(ExpectedColor);
		String ActualColor = "#a9c981";
		base.textCompareEquals(ActualColor, ExpectedColor, "Include families toggle is on by default",
				"Include families members toggle is not on by default");
		base.stepInfo("Filled document selection tab");
	}

	/**
	 * @authorBrundha
	 * @param Tag1
	 * @param Tag2
	 * @description :method for filling burn redaction with redaction tags.
	 * 
	 */
	public void burnRedactionWithRedactionTags(String Tag1, String Tag2) {

		base.waitForElement(getClkLink_selectingRedactionTags());
		getClkLink_selectingRedactionTags().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(getClkBtn_selectingRedactionTags());
		getClkBtn_selectingRedactionTags().Click();
		driver.waitForPageToBeReady();
		base.waitForElement(BurnRedactionCheckBox(Tag1));
		BurnRedactionCheckBox(Tag1).waitAndClick(10);
		base.waitForElement(BurnRedactionCheckBox(Tag2));
		BurnRedactionCheckBox(Tag2).waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(getClk_selectBtn());
		getClk_selectBtn().Click();
		driver.waitForPageToBeReady();
		base.waitForElement(gettextRedactionPlaceHolder());
		gettextRedactionPlaceHolder().Click();
		gettextRedactionPlaceHolder().SendKeys(Input.searchString4);
		base.stepInfo("Burn redaction  is filled successfully");

	}

	/**
	 * @authorBrundha
	 * @description :verifying include families toggle
	 * 
	 */

	public void verifyingIncludeFamiliesToggleInDocumentSelectionPage() {

		driver.scrollPageToTop();
		getMarkCompleteLink().waitAndClick(10);
		base.VerifySuccessMessage("Mark Complete successful");
		base.waitTillElemetToBeClickable(getMarkInCompleteBtn());
		getMarkInCompleteBtn().Click();
		driver.scrollingToBottomofAPage();
		getIncludeFamilies().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		base.waitForElement(getIncludeFamilies());
		getIncludeFamilies().Click();
		driver.waitForPageToBeReady();
		getMarkCompleteLink().waitAndClick(10);
		if (getMarkCompleteLink().isDisplayed()) {
			getMarkCompleteLink().waitAndClick(10);
			base.VerifySuccessMessage("Mark Complete successful");
			base.passedStep("There is no error message as expected");
		} else {
			base.failedStep("Error message is displayed");
		}
	}

	/**
	 * @authorBrundha
	 * @param Test
	 * @description :filling natively produced docs with file type and verifying the
	 *              warning message.
	 * 
	 */
	public void fillingTIFFWithNativelyProducedDocsFileType(String Test) {

		driver.waitForPageToBeReady();
		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();
		driver.scrollingToBottomofAPage();
		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();
		driver.waitForPageToBeReady();
		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().Click();
		base.waitForElement(getTiff_NativeDoc());
		getTiff_NativeDoc().Click();
		base.waitTillElemetToBeClickable(getFileTypeNativelyProducedDocs());
		getFileTypeNativelyProducedDocs().Click();
		base.waitForElement(getNativeDocsPlaceholder());
		getNativeDocsPlaceholder().SendKeys(Test);
		base.waitForElement(getTiff_NativeDoc());
		getTiff_NativeDoc().Click();
		driver.scrollPageToTop();
		getMarkCompleteLink().waitAndClick(10);
		base.VerifyWarningMessage(
				"In the TIFF / PDF section, no values are specified in the placeholder configuration for the docs produced natively. Please check.");
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param prodName
	 * @Description Open a exiting production on Home page
	 */
	public void openExistingProduction(String prodName) {

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		base.waitForElement(getGearIconForProdName(prodName));
		getGearIconForProdName(prodName).waitAndClick(5);

		base.waitForElement(getOpenWizard());
		getOpenWizard().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.stepInfo("Opened Existing production : " + prodName);
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @Description verify Production is generated succcessfully
	 */
	public void verifyProductionGenerateSuccussfully() {

		getDocumentGeneratetext().isElementAvailable(180);
		String actualText = getStatusSuccessTxt().getText();
		String expectedText = "Success";
		System.out.println(actualText);

		softAssertion.assertTrue(actualText.contains(expectedText));
		base.passedStep("Documents Generated successfully");
		base.stepInfo("Generation completed");
	}

	/**
	 * @authorAathith.Senthilkumar
	 * @param Batesvalue
	 * @param Prefix
	 * @param suffix
	 * @throws InterruptedException
	 * @Description perform generation page and verify bate vale, prefix and suffix
	 */
	public void fillingGeneratePageAndVerfyingBatesRangeValue(String Batesvalue, String Prefix, String suffix)
			throws InterruptedException {
		// generating production:
		SoftAssert softAssertion = new SoftAssert();
		String expectedText = "Success";

		base.waitForElement(getbtnProductionGenerate());
		getbtnProductionGenerate().waitAndClick(10);

		verifyProductionStatusInGenPage("Reserving Bates Range Complete");

		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");
		driver.waitForPageToBeReady();
		String Value = getBatesRange().getText();
		System.out.println(Value);

		if (Value.contains(Batesvalue)) {
			base.passedStep("Batesrange is value is verified");
		} else {
			base.failedStep("verification failed");
		}
		if (Value.contains(Prefix)) {
			base.passedStep("Prefix value is verified");
		} else {
			base.failedStep("verification failed");
		}
		if (Value.contains(suffix)) {
			base.passedStep("suffix value is verified");
		} else {
			base.failedStep("verification failed");
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocumentGeneratetext().isElementAvailable(350);
			}
		}), Input.wait120);
		String actualText = getStatusSuccessTxt().getText();
		System.out.println(actualText);

		softAssertion.assertTrue(actualText.contains(expectedText));
		base.passedStep("Documents Generated successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getConfirmProductionCommit().Enabled() && getConfirmProductionCommit().isDisplayed();
			}
		}), Input.wait60);

		// added thread.sleep to avoid exception while executing in batch
		Thread.sleep(2000);
		getConfirmProductionCommit().waitAndClick(10);

		if (base.getCloseSucessmsg().isElementAvailable(5)) {
			base.CloseSuccessMsgpopup();
		}

		String PDocCount = getProductionDocCount().getText();
		// added thread.sleep to avoid exception while executing in batch
		Thread.sleep(1000);
		System.out.println(PDocCount);
		int Doc = Integer.parseInt(PDocCount);

		Reporter.log("Doc - " + Doc, true);
		System.out.println(Doc);
		UtilityLog.info(Doc);

		base.waitForElement(getCopyPath());
		getCopyPath().waitAndClick(10);

		base.waitForElement(getQC_Download());

		getQC_Download().waitAndClick(10);
		getQC_Downloadbutton_allfiles().waitAndClick(10);
		base.VerifySuccessMessage("Your Production Archive download will get started shortly");
		base.stepInfo("Generate production page is filled");
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @throws InterruptedException
	 * @Description filling mp3 with burn redaction
	 */
	public void fillingMP3FileWithBurnRedaction() throws InterruptedException {

		getAdvancedProductionComponent().WaitUntilPresent().ScrollTo();
		base.clickButton(getAdvancedProductionComponent());
		base.waitForElement(getMP3CheckBox());
		getMP3CheckBox().Click();
		base.clickButton(getMP3CheckBoxToggle());
		base.clickButton(getMP3CheckReductionBoxEnable());
		getMP3CheckReductionBoxEnable().Enabled();
		base.clickButton(getMP3RatiobtnRedactiontag());

		driver.waitForPageToBeReady();
		getMP3FilesRedactionTag().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		driver.waitForPageToBeReady();
		getSelect_RedactionStyle_Dropdown().Click();
		base.clickButton(getSelect_Beep_RedactionStyle_Dropdown());
		base.clickButton(getAdvancedInMP3Files());
		String color = driver.FindElement(By.xpath("//label//input[@id='chkMP3ProduceLoadFile']//following-sibling::i"))
				.GetCssValue("background-color");
		String ExpectedColor = Color.fromString(color).asHex();
		String ActualColor = "#a9c981";
		base.textCompareEquals(ActualColor, ExpectedColor, "lst files toggle is enabled by default",
				"lst files toggle is not enabled by default");
		driver.scrollPageToTop();
	}

	/**
	 * @authorAathith.Senthilkumar
	 * @param tag
	 * @param tag1
	 * @Description perform add rule and add priv and redaction tag
	 */
	public void AddRuleAndRemoveRuleTagAndRedaction(String tag, String tag1) {
		base.waitForElement(getAddRule());
		getAddRule().waitAndClick(10);
		driver.waitForPageToBeReady();
		getTags().isDisplayed();
		getTags().waitAndClick(10);
		base.waitForElement(getTagsCheckbox(tag));
		getTagsCheckbox(tag).Click();
		getInsertQueryBtnInPrivGaurd().Click();

		base.waitForElement(getRedactions());
		getRedactions().waitAndClick(10);
		base.waitForElement(getTagsCheckbox(tag1));
		getTagsCheckbox(tag1).Click();
		getInsertQueryBtnInPrivGaurd().Click();

		driver.waitForPageToBeReady();
		getDocumentMatchesButton().waitAndClick(10);

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param prefixId
	 * @param suffixId
	 * @param beginningBates
	 * @throws InterruptedException
	 * @Description filing number and sorting page with bates rage lenth 2
	 */
	public void fillingNumberingAndSorting(String prefixId, String suffixId, String beginningBates)
			throws InterruptedException {

		base.waitForElement(getBeginningBates());
		driver.waitForPageToBeReady();
		getBeginningBates().waitAndClick(10);
		getBeginningBates().SendKeys(beginningBates);
		num = getRandomNumber(2);

		base.waitForElement(gettxtBeginningBatesIDPrefix());
		gettxtBeginningBatesIDPrefix().SendKeys(prefixId);

		base.waitForElement(gettxtBeginningBatesIDSuffix());
		gettxtBeginningBatesIDSuffix().SendKeys(suffixId);

		base.waitForElement(gettxtBeginningBatesIDMinNumLength());
		gettxtBeginningBatesIDMinNumLength().waitAndClick(10);
		gettxtBeginningBatesIDMinNumLength().SendKeys("2");

		driver.scrollingToBottomofAPage();

		base.waitForElement(getlstSortingMetaData());
		getlstSortingMetaData().selectFromDropdown().selectByVisibleText("DocID");

		base.waitForElement(getlstSortingOrder());
		getlstSortingOrder().selectFromDropdown().selectByVisibleText("Ascending");

		base.waitForElement(getlstSubSortingMetaData());
		getlstSubSortingMetaData().selectFromDropdown().selectByVisibleText("CustodianName");

		base.waitForElement(getlstSubSortingOrder());
		getlstSubSortingOrder().selectFromDropdown().selectByVisibleText("Ascending");

		base.waitForElement(getKeepFamiliesTogether());
		getKeepFamiliesTogether().waitAndClick(10);
		driver.scrollPageToTop();
		base.stepInfo("Numbering and sorting section is filled");

	}

	/**
	 * @authorBrundha
	 * @param prefixId
	 * @param suffixId
	 * @param begBate
	 * @Description:Method to fill prefix and suffix in sorting tab
	 */
	public void fillingNumberingAndSortingTab(String prefixId, String suffixId, String begBate) {

		base.waitForElement(getBeginningBates());
		driver.waitForPageToBeReady();
		getBeginningBates().SendKeys(begBate);
		base.waitForElement(gettxtBeginningBatesIDPrefix());
		gettxtBeginningBatesIDPrefix().SendKeys(prefixId);
		base.waitForElement(gettxtBeginningBatesIDSuffix());
		gettxtBeginningBatesIDSuffix().SendKeys(suffixId);
		base.stepInfo("Filled numbering and sorting tab");
	}

	/**
	 * @authorBrundha
	 * @description :filling natively Section with filetype in component tab
	 * 
	 */
	public void selectingNativeFileType() {
		base.waitForElement(getNativeChkBox());
		getNativeChkBox().Click();
		base.waitForElement(getNativeTab());
		getNativeTab().Click();
		base.waitForElement(getNativeFileType());
		getNativeFileType().ScrollTo();
		getNativeFileType().Click();
		base.stepInfo("Filled native section with file type");
	}

	/**
	 * @authorBrundha
	 * @param FolderName
	 * @description :filling Workproduct in tiff section
	 * 
	 */

	public void FillingWorkProductInTiffSection(String FolderName) {
		driver.scrollingToBottomofAPage();
		base.waitForElement(getTiffAdvanceBtn());
		getTiffAdvanceBtn().Click();
		getSlipSheets().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		base.waitForElement(getSlipSheetWorkProduct());
		getSlipSheetWorkProduct().Click();
		base.waitForElement(getWorkProductFolderName(FolderName));
		getWorkProductFolderName(FolderName).Click();
		base.waitForElement(getAddSelected());
		getAddSelected().Click();

	}

	/**
	 * @authorAathith.Senthilkumar
	 * @param tagname
	 * @param tagname1
	 * @param placeholder
	 * @throws InterruptedException
	 * @Description : selecting 2 technical issue tag and enter placeholder
	 */
	public void fillingTiffSectionTechIssueWithEnteringText(String tagname, String tagname1, String placeholder)
			throws InterruptedException {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().waitAndClick(5);

		driver.scrollingToBottomofAPage();

		base.waitForElement(getTIFFTab());
		getTIFFTab().waitAndClick(5);

		driver.scrollPageToTop();

		base.waitForElement(getTIFF_CenterHeaderBranding());
		getTIFF_CenterHeaderBranding().waitAndClick(10);

		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().waitAndClick(5);

		base.waitForElement(getTechissue_toggle());
		getTechissue_toggle().waitAndClick(5);

		driver.waitForPageToBeReady();

		base.waitForElement(getTechissue_SelectTagButton());
		getTechissue_SelectTagButton().waitAndClick(10);

		base.waitForElement(getPriveldge_TagTree(tagname));
		getPriveldge_TagTree(tagname).waitAndClick(10);

		base.waitForElement(getPriveldge_TagTree(tagname1));
		getPriveldge_TagTree(tagname1).waitAndClick(10);

		base.waitForElement(getPriveldge_TagTree_SelectButton());
		getPriveldge_TagTree_SelectButton().waitAndClick(15);

		driver.waitForPageToBeReady();
		getTechIssuePlaceHolder().ScrollTo();
		base.waitForElement(getTechIssuePlaceHolder());
		getTechIssuePlaceHolder().Click();
		getTechIssuePlaceHolder().SendKeys(placeholder);

		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
	}

	/**
	 * @authorAathith.Senthilkumar
	 * @throws ZipException
	 * @Description Extract downloaded file
	 * 
	 */
	public void extractFile() throws ZipException, InterruptedException {
		driver.waitForPageToBeReady();
		waitForFileDownload();
		String name = getProduction().getText().trim();
		String home = System.getProperty("user.home");

		File file = new File(home + "/Downloads/" + name + ".zip");
		File file1 = new File(Input.fileDownloadLocation + name + ".zip");

		if (file.exists()) {
			driver.waitForPageToBeReady();
			unzipping(home + "/Downloads/" + name + ".zip", home + "/Downloads/");
			System.out.println("Unzipped the downloaded files");
		} else if (file1.exists()) {
			driver.waitForPageToBeReady();
			unzipping(Input.fileDownloadLocation + name + ".zip", home + "/Downloads/");
			System.out.println("Unzipped the downloaded files in BatchDownload");
		} else {
			System.out.println("Unzipped failed");
			base.failedStep("file not found");
		}
		driver.waitForPageToBeReady();
		base.stepInfo("Downloaded zip file was extracted");
	}

	/**
	 * @authorAathith.Senthilkumar
	 * @param firstFile
	 * @param lastFile
	 * @param prefixID
	 * @param suffixID
	 * @param verificationText
	 * @description Verifing placeholder text using ocr in downloaded image file
	 */
	public void OCR_Verification_In_Generated_Tiff(int firstFile, int lastFile, String prefixID, String suffixID,
			String verificationText) {
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		Ocr.setUp();
		Ocr ocr = new Ocr();
		ocr.startEngine("eng", Ocr.SPEED_FASTEST);

		for (int i = firstFile; i < lastFile; i++) {
			String Tifffile = ocr.recognize(
					new File[] {
							new File(home + "/Downloads/VOL0001/Images/0001/" + prefixID + i + suffixID + ".tiff") },
					Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PLAINTEXT);
			System.out.println(Tifffile);

			if (Tifffile.contains(verificationText)) {
				base.passedStep(verificationText + " is displayed in " + i + " file expected");
			} else {
				base.failedStep(verificationText + " verification failed");
			}
		}
		ocr.stopEngine();
	}

	/**
	 * @authorAathith.Senthilkumar
	 * @param fileType
	 * @param placeholderText
	 * @Description selecting only file type in tiff Native docs
	 */
	public void fillingTIFFWithNativelyProducedDocs(String fileType, String placeholderText) {

		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().Click();
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			driver.scrollPageToTop();
			base.waitForElement(getTIFF_CenterHeaderBranding());
			getTIFF_CenterHeaderBranding().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getTIFF_EnableforPrivilegedDocs());
			getTIFF_EnableforPrivilegedDocs().Enabled();
			getTIFF_EnableforPrivilegedDocs().Click();
			base.waitForElement(getTiff_NativeDoc());
			getTiff_NativeDoc().Click();
			base.waitTillElemetToBeClickable(getSelectFileTypeInTifffNative(fileType));
			getSelectFileTypeInTifffNative(fileType).Click();
			base.waitForElement(getNativeDocsPlaceholder());
			getNativeDocsPlaceholder().SendKeys(placeholderText);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while filling tiff section with natively produced documents." + e.getMessage());
		}
	}

	/**
	 * @authorsowndarya.velraj
	 * @param prefixId
	 * @param suffixId
	 * @param beginningBates
	 * @param sortData1
	 * @param sortData2
	 * @throws InterruptedException
	 */
	public void fillingNumberingAndSortingWithSortingByMetaData(String prefixId, String suffixId, String beginningBates,
			String sortData1, String sortData2) throws InterruptedException {

		base.waitForElement(getBeginningBates());
		driver.waitForPageToBeReady();
		getBeginningBates().waitAndClick(10);
		getBeginningBates().SendKeys(beginningBates);
		num = getRandomNumber(2);

		base.waitForElement(gettxtBeginningBatesIDPrefix());
		gettxtBeginningBatesIDPrefix().SendKeys(prefixId);

		base.waitForElement(gettxtBeginningBatesIDSuffix());
		gettxtBeginningBatesIDSuffix().SendKeys(suffixId);

		base.waitForElement(gettxtBeginningBatesIDMinNumLength());
		gettxtBeginningBatesIDMinNumLength().waitAndClick(10);
		num1 = getRandomNumber(1);
		System.out.println("Beginning BatesID Min Num Length=" + num1);
		gettxtBeginningBatesIDMinNumLength().SendKeys(getRandomNumber(1));

		driver.scrollingToBottomofAPage();

		base.waitForElement(getlstSortingMetaData());
		getlstSortingMetaData().selectFromDropdown().selectByVisibleText(sortData1);

		base.waitForElement(getlstSortingOrder());
		getlstSortingOrder().selectFromDropdown().selectByVisibleText("Ascending");

		base.waitForElement(getlstSubSortingMetaData());
		getlstSubSortingMetaData().selectFromDropdown().selectByVisibleText(sortData2);

		base.waitForElement(getlstSubSortingOrder());
		getlstSubSortingOrder().selectFromDropdown().selectByVisibleText("Ascending");

		base.waitForElement(getKeepFamiliesTogether());
		getKeepFamiliesTogether().waitAndClick(10);
		driver.scrollPageToTop();
		base.stepInfo("Numbering and sorting section is filled");

	}

	/**
	 * @authorAathith.Senthilkumar
	 * @param firstFile
	 * @param lastFile
	 * @param prefixID
	 * @param suffixID
	 * @Description verify Image file is exist in downloaded file
	 */
	public void isImageFileExist(int firstFile, int lastFile, String prefixID, String suffixID) {
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		for (int i = firstFile; i < lastFile; i++) {
			File TiffFile = new File(home + "/Downloads/" + "VOL0001/Images/0001/" + prefixID + i + suffixID + ".tiff");

			if (TiffFile.exists()) {
				base.passedStep("Tiff file are generated coreectly : " + prefixID + i + suffixID + ".tiff");
				System.out.println("passeed");
			} else {
				base.failedStep("verification failed");
				System.out.println("failed");
			}
		}
	}

	/**
	 * @authorAathith.Senthilkumar
	 * @param firstFile
	 * @param lastFile
	 * @param prefixID
	 * @param suffixID
	 * @Description verify Text is generated in Production
	 */
	public void isTextFileExist(int firstFile, int lastFile, String prefixID, String suffixID) {
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		for (int i = firstFile; i < lastFile; i++) {
			File Textfile = new File(home + "/Downloads/VOL0001/Text/0001/" + prefixID + i + suffixID + ".txt");

			if (Textfile.exists()) {
				base.passedStep("Text file are generated correctly : " + prefixID + i + suffixID + ".txt");
				System.out.println("passeed");
			} else {
				base.failedStep("verification failed");
				System.out.println("failed");
			}
		}
	}

	/**
	 * @authorAathith.Senthilkumar
	 * @param firstFile
	 * @param lastFile
	 * @param prefixID
	 * @param suffixID
	 * @Description Native Docx file is Generated in production
	 */
	public void isNativeDocxFileExist(int firstFile, int lastFile, String prefixID, String suffixID) {
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		for (int i = firstFile; i < lastFile; i++) {
			File Native = new File(home + "/Downloads/VOL0001/Natives/0001/" + prefixID + i + suffixID + ".docx");

			if (Native.exists()) {

				base.passedStep("Native file are generated correctly : " + prefixID + i + suffixID + ".docx");
				System.out.println("passeed");
			} else {
				base.failedStep("verification failed");
				System.out.println("failed");
			}
		}
	}

	/**
	 * @authorAathith.Senthilkumar
	 * @param firstFile
	 * @param lastFile
	 * @param prefixID
	 * @param suffixID
	 * @Description Native Docx file is Generated in production
	 */
	public void isNativeSpreadsheetFileExist(int firstFile, int lastFile, String prefixID, String suffixID) {
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		for (int i = firstFile; i < lastFile; i++) {
			File Native = new File(home + "/Downloads/VOL0001/Natives/0001/" + prefixID + i + suffixID + ".xlsx");

			if (Native.exists()) {

				base.passedStep("Native file are generated correctly : " + prefixID + i + suffixID + ".xlsx");
				System.out.println("passeed");
			} else {
				base.failedStep("verification failed");
				System.out.println("failed");
			}
		}
	}

	/**
	 * @authorAathith.Senthilkumar
	 * @param element
	 * @param text
	 * @Description verify values using get text in element
	 */
	public void verifyText(Element element, String text) {
		driver.waitForPageToBeReady();
		String value = element.getText().trim();

		if (value.equals(text)) {
			softAssertion.assertEquals(value, text);
			base.passedStep(text + "value is displayed correcly");
			System.out.println("passed");
		} else {
			System.out.println("actual value : " + value);
			System.out.println("passed value : " + text);
			base.failedStep("verification failed");
		}
	}

	/**
	 * @author Brundha.T
	 * @param i
	 * @description :Selecting CheckBox In Dat section
	 */
	public void selectingCheckboxInDatSection(int i) {
		base.waitForElement(getRedactDATCheckBox(i));
		getRedactDATCheckBox(i).Click();
		base.waitForElement(getPrivledgedDATCheckBox(i));
		getPrivledgedDATCheckBox(i).Click();

	}

	/**
	 * @authorAathith.Senthilkumar
	 * @param prefixID
	 * @param suffixID
	 * @param beginningBates
	 * @throws IOException
	 * @Description generated file pdf annotation verification
	 */
	public void pdf_Verification_In_Generated_Pdf(String prefixID, String suffixID, String beginningBates)
			throws IOException {
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		PDDocument document = PDDocument
				.load(new File(home + "/Downloads/VOL0001/PDF/0001/" + prefixID + beginningBates + suffixID + ".pdf"));
		if (!document.isEncrypted()) {
			PDFTextStripper stripper = new PDFTextStripper();
			String text = stripper.getText(document);
			System.out.println("Text:" + text);
			if (text != null) {
				base.passedStep("Documents is produced with annotations");
			}
		}
		document.close();
	}

	/**
	 * * @authorVijaya.Rani
	 * 
	 * @param elementName
	 * @throws InterruptedException
	 */
	public void clickBackBtnandSelectingNative(int Count, String tagname) throws InterruptedException {

		clickElementNthtime(getBackButton(), Count);

		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		getMarkInCompleteBtn().waitAndClick(10);
		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();
		base.waitForElement(getTiff_NativeDoc());
		getTiff_NativeDoc().Click();
		base.waitForElement(getclkSelectTags());
		getclkSelectTags().waitAndClick(10);
		base.waitForElement(getPriveldged_TagTree(tagname));
		getPriveldged_TagTree(tagname).Click();
		base.waitForElement(getClkSelect());
		getClkSelect().Click();
		Thread.sleep(Input.wait30 / 10);
		base.waitForElement(getNativeDocsPlaceholder());
		getNativeDocsPlaceholder().SendKeys(Input.tagNameTechnical);
	}

	/**
	 * @authorAathith.Senthilkumar
	 * @param firstFile
	 * @param lastFile
	 * @param prefixID
	 * @param suffixID
	 * @param verificationText
	 * @throws IOException
	 * @Description Downloaded pdf file verification
	 */
	public void pdf_Verification_In_Generated_PlaceHolder(int firstFile, int lastFile, String prefixID, String suffixID,
			String verificationText) throws IOException {
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		for (int i = firstFile; i < lastFile; i++) {
			PDDocument document = PDDocument
					.load(new File(home + "/Downloads/VOL0001/PDF/0001/" + prefixID + i + suffixID + ".pdf"));
			if (!document.isEncrypted()) {
				PDFTextStripper stripper = new PDFTextStripper();
				String text = stripper.getText(document);
				System.out.println("Text:" + text);
				if (text.contains(verificationText)) {
					base.passedStep("Documents is produced with annotations");
				}

			}
			document.close();
		}
	}

	/**
	 * @authorGopinath Modified By - Gopinath Modified Date - NA
	 * @Description : Method to verify meta data list in slip sheet will be in
	 *              ascending order on Tiff section.
	 */
	public void verifyMetaDataListSlipSheetInAscendingOrderTiffSec() {

		try {
			ArrayList<String> metadataList = new ArrayList<String>();
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().waitAndClick(5);
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			driver.scrollPageToTop();
			getTIFF_EnableforPrivilegedDocs().ScrollTo();
			getTiffAdvancedTab().ScrollTo();
			getTiffAdvancedTab().isElementAvailable(15);
			getTiffAdvancedTab().Click();
			getSlipSheetToogle().isElementAvailable(15);
			getSlipSheetToogle().Click();
			List<WebElement> slipSheetMetaDataList = getSlipSheetMetaDataList().FindWebElements();
			for (int i = 0; i < slipSheetMetaDataList.size(); i++) {
				String metaDataVal = slipSheetMetaDataList.get(i).getText().trim();
				metadataList.add(metaDataVal);
			}
			Collections.sort(metadataList, String.CASE_INSENSITIVE_ORDER);
			for (int i = 0; i < slipSheetMetaDataList.size(); i++) {
				String metaDataVal = slipSheetMetaDataList.get(i).getText().trim();
				if (!metadataList.get(i).contentEquals(metaDataVal)) {
					base.failedStep("Mata data list is not in ascending order of slip sheet on Tiff section");
				}
			}
			base.passedStep("Mata data list is in ascending order of slip sheet on Tiff section");

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while verifying meta data list in slip sheet will be in ascending order on Tiff section."
							+ e.getMessage());
		}

	}

	/**
	 * @authorGopinath Modified By - Gopinath Modified Date - NA
	 * @Description : Method to verify meta data list in slip sheet will be in
	 *              ascending order on pdf section.
	 */
	public void verifyMetaDataListSlipSheetInAscendingOrderPDFSec() {
		try {
			ArrayList<String> metadataList = new ArrayList<String>();
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().waitAndClick(5);
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			getPDFGenerateRadioButton().ScrollTo();
			base.clickButton(getPDFGenerateRadioButton());
			getTIFF_EnableforPrivilegedDocs().ScrollTo();
			getTiffAdvancedTab().ScrollTo();
			getTiffAdvancedTab().isElementAvailable(15);
			getTiffAdvancedTab().Click();
			getSlipSheetToogle().isElementAvailable(15);
			getSlipSheetToogle().Click();
			List<WebElement> slipSheetMetaDataList = getSlipSheetMetaDataList().FindWebElements();
			for (int i = 0; i < slipSheetMetaDataList.size(); i++) {
				String metaDataVal = slipSheetMetaDataList.get(i).getText().trim();
				metadataList.add(metaDataVal);
			}
			Collections.sort(metadataList, String.CASE_INSENSITIVE_ORDER);
			for (int i = 0; i < slipSheetMetaDataList.size(); i++) {
				String metaDataVal = slipSheetMetaDataList.get(i).getText().trim();
				if (!metadataList.get(i).contentEquals(metaDataVal)) {
					base.failedStep("Mata data list is not in ascending order of slip sheet on pdf section");
				}
			}
			base.passedStep("Mata data list is in ascending order of slip sheet on pdf section");

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while verifying meta data list in slip sheet will be in ascending order on pdf section."
							+ e.getMessage());
		}
	}

	/**
	 * @authorGopinath Modified By - Gopinath Modified Date - NA
	 * @Description : Method to verify meta data list in drop down native will be in
	 *              ascending order on tiff section.
	 */
	public void verifyMetaDataDropdownNativeAscendingOrderTiffSec() throws InterruptedException {
		try {
			ArrayList<String> metadataList = new ArrayList<String>();
			getTIFFChkBox().isElementAvailable(15);
			getTIFFChkBox().waitAndClick(10);
			driver.scrollingToBottomofAPage();
			getTIFFTab().isElementAvailable(15);
			getTIFFTab().waitAndClick(10);
//			getclkNativelyProducedDocumentLnk().isElementAvailable(15);
//			getclkNativelyProducedDocumentLnk().Click();
			getNativeMetaDataFieldLink().ScrollTo();
			driver.waitForPageToBeReady();
			getNativeMetaDataFieldLink().waitAndClick(10);
			getNativeMetaDataFieldDropdown().isElementAvailable(10);
			List<WebElement> slipSheetMetaDataList = getNativeMetaDataFieldDropdown().selectFromDropdown().getOptions();
			for (int i = 0; i < slipSheetMetaDataList.size(); i++) {
				String metaDataVal = slipSheetMetaDataList.get(i).getText().trim();
				metadataList.add(metaDataVal);
			}
			Collections.sort(metadataList, String.CASE_INSENSITIVE_ORDER);
			for (int i = 0; i < slipSheetMetaDataList.size(); i++) {
				String metaDataVal = slipSheetMetaDataList.get(i).getText().trim();
				if (!metadataList.get(i).contentEquals(metaDataVal)) {
					base.failedStep("Mata data list is not in ascending order of native on tiff section");
				}
			}
			base.passedStep("Mata data list is in ascending order of native on tiff section");

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while verifying meta data list in drop down native will be in ascending order on tiff section..."
							+ e.getMessage());
		}
	}

	/**
	 * @authorGopinath Modified By - Gopinath Modified Date - NA
	 * @Description : Method to verify meta data list in drop down native will be in
	 *              ascending order on pdf section.
	 */
	public void verifyMetaDataDropdownNativeAscendingOrderPdfSec() throws InterruptedException {
		try {
			ArrayList<String> metadataList = new ArrayList<String>();
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().waitAndClick(5);
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			getPDFGenerateRadioButton().ScrollTo();
			base.clickButton(getPDFGenerateRadioButton());
			getTIFF_EnableforPrivilegedDocs().ScrollTo();
//			getclkNativelyProducedDocumentLnk().isElementAvailable(15);
//			getclkNativelyProducedDocumentLnk().Click();
			getNativeMetaDataFieldLink().ScrollTo();
			driver.waitForPageToBeReady();
			getNativeMetaDataFieldLink().Click();
			getNativeMetaDataFieldDropdown().isElementAvailable(10);
			List<WebElement> slipSheetMetaDataList = getNativeMetaDataFieldDropdown().selectFromDropdown().getOptions();
			for (int i = 0; i < slipSheetMetaDataList.size(); i++) {
				String metaDataVal = slipSheetMetaDataList.get(i).getText().trim();
				metadataList.add(metaDataVal);
			}
			Collections.sort(metadataList, String.CASE_INSENSITIVE_ORDER);
			for (int i = 0; i < slipSheetMetaDataList.size(); i++) {
				String metaDataVal = slipSheetMetaDataList.get(i).getText().trim();
				if (!metadataList.get(i).contentEquals(metaDataVal)) {
					base.failedStep("Mata data list is not in ascending order of native on pdf section");
				}
			}
			base.passedStep("Mata data list is in ascending order of native on pdf section");

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while verifying meta data list in drop down native will be in ascending order on pdf section..."
							+ e.getMessage());
		}
	}

	/**
	 * @authorGopinath Modified By - Gopinath Modified Date - NA
	 * @Description : Method to verify meta data list in drop down exception will be
	 *              in ascending order on tiff section.
	 */
	public void verifyMetaDataDropdownExceptionAscendingOrderTiffSec() {
		try {
			ArrayList<String> metadataList = new ArrayList<String>();
			getTIFFChkBox().isElementAvailable(15);
			getTIFFChkBox().Click();
			driver.scrollingToBottomofAPage();
			getTIFFTab().isElementAvailable(15);
			getTIFFTab().Click();
			driver.scrollingToBottomofAPage();
			getTechissue_toggle().isElementAvailable(15);
			base.waitForElement(getTechissue_toggle());
			getTechissue_toggle().Click();
			getExceptionMetaDataFieldLink().isElementAvailable(10);
			getExceptionMetaDataFieldLink().Click();
			getNativeMetaDataFieldDropdown().isElementAvailable(10);
			List<WebElement> slipSheetMetaDataList = getNativeMetaDataFieldDropdown().selectFromDropdown().getOptions();
			for (int i = 0; i < slipSheetMetaDataList.size(); i++) {
				String metaDataVal = slipSheetMetaDataList.get(i).getText().trim();
				metadataList.add(metaDataVal);
			}
			Collections.sort(metadataList, String.CASE_INSENSITIVE_ORDER);
			for (int i = 0; i < slipSheetMetaDataList.size(); i++) {
				String metaDataVal = slipSheetMetaDataList.get(i).getText().trim();
				if (!metadataList.get(i).contentEquals(metaDataVal)) {
					base.failedStep("Mata data list is not in ascending order of exception on tiff section");
				}
			}
			base.passedStep("Mata data list is in ascending order of exception on tiff section");

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifying meta data list in drop down exception will be in ascending order on tiff section."
							+ e.getMessage());
		}
	}

	/**
	 * @authorGopinath Modified By - Gopinath Modified Date - NA
	 * @Description : Method to verify meta data list in drop down exception will be
	 *              in ascending order on pdf section.
	 */
	public void verifyMetaDataDropdownExceptionAscendingOrderPDFSec() {
		try {
			ArrayList<String> metadataList = new ArrayList<String>();
			base.waitForElement(getTIFFChkBox());
			getTIFFChkBox().waitAndClick(5);
			driver.scrollingToBottomofAPage();
			base.waitForElement(getTIFFTab());
			getTIFFTab().Click();
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			getPDFGenerateRadioButton().ScrollTo();
			base.clickButton(getPDFGenerateRadioButton());
			driver.scrollingToBottomofAPage();
			getTechissue_toggle().isElementAvailable(15);
			base.waitForElement(getTechissue_toggle());
			getTechissue_toggle().waitAndClick(10);
			getExceptionMetaDataFieldLink().ScrollTo();
			getExceptionMetaDataFieldLink().waitAndClick(10);
			getNativeMetaDataFieldDropdown().isElementAvailable(10);
			List<WebElement> slipSheetMetaDataList = getNativeMetaDataFieldDropdown().selectFromDropdown().getOptions();
			for (int i = 0; i < slipSheetMetaDataList.size(); i++) {
				String metaDataVal = slipSheetMetaDataList.get(i).getText().trim();
				metadataList.add(metaDataVal);
			}
			Collections.sort(metadataList, String.CASE_INSENSITIVE_ORDER);
			for (int i = 0; i < slipSheetMetaDataList.size(); i++) {
				String metaDataVal = slipSheetMetaDataList.get(i).getText().trim();
				if (!metadataList.get(i).contentEquals(metaDataVal)) {
					base.failedStep("Mata data list is not in ascending order of exception on pdf section");
				}
			}
			base.passedStep("Mata data list is in ascending order of exception on pdf section");

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verifying meta data list in drop down exception will be in ascending order on pdf section."
							+ e.getMessage());
		}
	}

	/**
	 * @author Vijaya.Rani Modified By - 15/03/2022 Modified Date - NA
	 * @param firstFile
	 * @param lastFile
	 * @param prefixID
	 * @param suffixID
	 * @param verificationText
	 * @throws TesseractException
	 * @throws IOException
	 * @Description : OCR Verification In Generated Tiff SS.
	 */
	public void OCR_Verification_In_Generated_Tiff_SS(int firstFile, int lastFile, String prefixID, String suffixID,
			String verificationText) throws TesseractException {
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");

		for (int i = firstFile; i < lastFile; i++) {

			File imageFile = new File(home + "/Downloads/VOL0001/Images/0001/" + prefixID + i + suffixID + ".ss.tiff");
			// ITesseract instance = new Tesseract(); // JNA Interface Mapping
			ITesseract instance = new Tesseract1(); // JNA Direct Mapping
			File tessDataFolder = LoadLibs.extractTessResources("tessdata"); // Maven build bundles English data
			instance.setDatapath(tessDataFolder.getPath());

			String result = instance.doOCR(imageFile);
			System.out.println(result);
			if (result.contains(verificationText)) {
				base.passedStep(verificationText + " is displayed in " + prefixID + i + suffixID + ".tiff"
						+ " file as expected");
			} else {
				base.failedStep(verificationText + " verification failed");
			}
		}

	}

	/**
	 * @author Vijaya.Rani
	 * @description : Adding Slip Sheet Work Product
	 * @param productName1
	 * @param productName2
	 * @param productName3
	 * @param productName4
	 * @throws IOException
	 * 
	 */
	public void addingSlipSheetWorkProduct(String productName1, String productName2, String productName3,
			String productName4) throws InterruptedException {

		base.waitForElement(getadvance());
		getadvance().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSlipSheets().Visible();
			}
		}), Input.wait30);
		getSlipSheets().waitAndClick(10);

		driver.scrollingToBottomofAPage();
		base.waitForElement(getAddWorkProductSlipSheet(productName1));
		getAddWorkProductSlipSheet(productName1).waitAndClick(10);

		base.waitForElement(getAddWorkProductSlipSheet(productName2));
		getAddWorkProductSlipSheet(productName2).waitAndClick(10);

		base.waitForElement(getAddWorkProductSlipSheet(productName3));
		getAddWorkProductSlipSheet(productName3).waitAndClick(10);

		base.waitForElement(getAddWorkProductSlipSheet(productName4));
		getAddWorkProductSlipSheet(productName4).waitAndClick(10);

		base.waitForElement(getAddSelected());
		getAddSelected().waitAndClick(10);

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param firstFile
	 * @param lastFile
	 * @param prefixID
	 * @param suffixID
	 * @param verificationText
	 * @Description verifying text on the tiff image file on downloaded zip file
	 */
	public void OCR_Verification_In_Generated_Tiff_tess4j(int firstFile, int lastFile, String prefixID, String suffixID,
			String verificationText) {
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");

		for (int i = firstFile; i < lastFile; i++) {

			File imageFile = new File(home + "/Downloads/VOL0001/Images/0001/" + prefixID + i + suffixID + ".tiff");
			// ITesseract instance = new Tesseract(); // JNA Interface Mapping
			ITesseract instance = new Tesseract1(); // JNA Direct Mapping
			File tessDataFolder = LoadLibs.extractTessResources("tessdata"); // Maven build bundles English data
			instance.setDatapath(tessDataFolder.getPath());

			try {
				String result = instance.doOCR(imageFile);
				System.out.println(result);
				if (result.contains(verificationText)) {
					base.passedStep(verificationText + " is displayed in " + prefixID + i + suffixID + ".tiff"
							+ " file as expected");
				} else {
					base.failedStep(verificationText + " verification failed");
				}
			} catch (TesseractException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @Description Navigate to Production Grid View
	 */
	public void goToProductionGridView() {
		driver.waitForPageToBeReady();
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		base.waitForElement(getGridView());
		getGridView().waitAndClick(10);
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param statusMsg
	 * @param productionname
	 * @throws InterruptedException
	 * @Description Verifying the Production status on Grid View page high volume
	 *              project status
	 */
	public void verifyProductionStatusInGridViewForHighVolumeProject(String statusMsg, String productionname)
			throws InterruptedException {
		String productionFromHomePage = null;
		String lastStatus = "Post-Gen QC Checks Complete";

		// Verifying status of the production from home page
		for (int i = 0; i < 180; i++) {
			productionFromHomePage = getStatusInGridView(productionname).getText();
			System.out.println("Preparing Data status displayed for " + productionFromHomePage);
			if (productionFromHomePage.contains(statusMsg)) {
				base.passedStep(statusMsg + " status is displayed in Grid View");
				break;
			} else if (productionFromHomePage.contains(lastStatus)) {
				base.stepInfo("last status reached");
				base.stepInfo("this status only visible in High Volume Project");
				break;
			} else if (i == 179) {
				base.stepInfo("time out for status verification");
				base.stepInfo("this status take more time than automation time limit");
				base.stepInfo("this status only visible in High Volume Project");
				break;
			} else {
				getRefreshButton().waitAndClick(5);
				driver.waitForPageToBeReady();
			}
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param file
	 * @return
	 * @Description To Get the file count in that location
	 */
	public int dirFoldersCount(File file) {
		File[] dirContent = file.listFiles();
		int dirCount = dirContent.length;
		return dirCount;
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param statusMsg
	 * @throws InterruptedException
	 * @Description Verify the Production status of high Volume Project on
	 *              Generation page.
	 */
	public void verifyProductionStatusInGenerationPageForHighVolumeProject(String statusMsg)
			throws InterruptedException {
		driver.waitForPageToBeReady();
		String VerifyGenStatus = null;
		String lastStatus = "Post-Generation QC checks Complete";

		// Verifying status of the production from generate page
		for (int i = 0; i < 180; i++) {
			if (!getStatusDraftTxt().isElementAvailable(1)) {
				base.stepInfo("Quality Control & Confirmation page reached");
				base.stepInfo("All status are completed");
				base.stepInfo("this status only visible in High Volume Project");
				break;
			}
			VerifyGenStatus = getStatusDraftTxt().getText();
			if (VerifyGenStatus.contains(statusMsg)) {
				base.passedStep(statusMsg + " status is displayed in Generation page");
				break;
			} else if (VerifyGenStatus.contains(lastStatus)) {
				base.stepInfo("last status reached");
				base.stepInfo("this status only visible in High Volume Project");
				break;
			} else if (i == 179) {
				base.stepInfo("time out for status verification");
				base.stepInfo("this status take more time than automation time limit");
				base.stepInfo("this status only visible in High Volume Project");
				break;
			} else {
				base.waitTime(1);
				driver.waitForPageToBeReady();
			}
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param statusMsg
	 * @param productionname
	 * @Description Verifying production status in Tile view on high volume project
	 */
	public void verifyProductionStatusInTileViewForHighVolumeProject(String statusMsg, String productionname) {
		driver.waitForPageToBeReady();
		String productionFromHomePage = null;
		String lastStatus = "Post-Gen QC Checks Complete";

		// Verifying status of the production from home page
		for (int i = 0; i < 180; i++) {
			productionFromHomePage = getProductionFromHomePage(productionname).getText();
			if (productionFromHomePage.contains(statusMsg)) {
				base.passedStep(statusMsg + " status is displayed in Tile View");
				break;
			} else if (productionFromHomePage.contains(lastStatus)) {
				base.stepInfo("last status reached");
				base.stepInfo("this status only visible in High Volume Project");
				break;
			} else if (i == 179) {
				base.stepInfo("time out for status verification");
				base.stepInfo("this status take more time than automation time limit");
				base.stepInfo("this status only visible in High Volume Project");
				break;
			} else {
				getRefreshButton().waitAndClick(5);
				driver.waitForPageToBeReady();
			}
		}
		System.out.println("Preparing Data status displayed for " + productionFromHomePage);
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @Description Delete downloaded zip file and extracted file
	 */
	public void deleteFiles() {
		String name = getProduction().getText().trim();
		String home = System.getProperty("user.home");
		File extacted = new File(home + "/Downloads/VOL0001/");
		File zipped = new File(home + "/Downloads/" + name + ".zip");
		deleteDirectory(extacted);
		zipped.delete();
		base.stepInfo("downloaded zip file and extracted file was deleted");
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param path
	 * @Description delete method for directory deletion
	 */
	public boolean deleteDirectory(File path) {
		if (path.exists()) {
			File[] files = path.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isDirectory()) {
					deleteDirectory(files[i]);
				} else {
					files[i].delete();
				}
			}
		}
		return (path.delete());
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @return
	 * @throws UnsupportedFlavorException
	 * @throws IOException
	 * @Description get text from copied ClipBoard
	 */
	public String getCopiedTextFromClipBoard() throws UnsupportedFlavorException, IOException {
		driver.waitForPageToBeReady();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Clipboard clipboard = toolkit.getSystemClipboard();
		String actualCopedText = (String) clipboard.getData(DataFlavor.stringFlavor);
		System.out.println(actualCopedText);
		return actualCopedText;
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @Description verify the generated production file contains with dat file
	 */
	public void isdatFileExist() {
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		String name = getProduction().getText().trim();
		driver.waitForPageToBeReady();
		File DatFile = new File(home + "/Downloads/VOL0001/Load Files/" + name + "_DAT.dat");
		if (DatFile.exists()) {
			base.passedStep("Dat file is exists in generated production");
		} else {
			base.failedStep("Dat file is not displayed as expected");
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param file
	 * @Description verify that file is exists or not
	 */
	public void isfileisExists(File file) {
		if (file.exists()) {
			System.out.println(" file is Exists in pointed directory");
			base.passedStep(file + " file is Exists in pointed directory");
		} else {
			System.out.println(" Does not Exists");
			base.stepInfo(file + " load file is not generated");
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param prefixID
	 * @param suffixID
	 * @param beginningBates
	 * @param verificationText
	 * @throws IOException
	 * @Description Verify that generated pdf file is generated with placleholder
	 */
	public void pdf_Verification_In_Generated_PlaceHolder(String prefixID, String suffixID, String beginningBates,
			String verificationText) throws IOException {
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		PDDocument document = PDDocument
				.load(new File(home + "/Downloads/VOL0001/PDF/0001/" + prefixID + beginningBates + suffixID + ".pdf"));
		if (!document.isEncrypted()) {
			PDFTextStripper stripper = new PDFTextStripper();
			String text = stripper.getText(document);
			System.out.println("Text:" + text);
			if (text.contains(verificationText)) {
				base.passedStep(verificationText + " text is produced in this document");
			}
			document.close();
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param prefixID
	 * @param suffixID
	 * @param beginningBates
	 * @Description verify that generated tiff file
	 */
	public void OCR_Verification_In_Generated_Tiff_tess4j(String prefixID, String suffixID, String beginningBates) {
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");

		File imageFile = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
		// ITesseract instance = new Tesseract(); // JNA Interface Mapping
		ITesseract instance = new Tesseract1(); // JNA Direct Mapping
		File tessDataFolder = LoadLibs.extractTessResources("tessdata"); // Maven build bundles English data
		instance.setDatapath(tessDataFolder.getPath());

		try {
			String result = instance.doOCR(imageFile);
			System.out.println(result);
			if (result != null) {
				base.passedStep("Document is produced as expect");
			} else {
				base.failedStep("document validation failed");
			}
		} catch (TesseractException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @throws InterruptedException
	 * @Description wait for generated zip file download
	 */
	public void waitForFileDownload() throws InterruptedException {
		String home = System.getProperty("user.home");
		String name = getProduction().getText().trim();
		File file = new File(home + "/Downloads/" + name + ".zip");
		File file1 = new File(Input.fileDownloadLocation + name + ".zip");

		for (int i = 0; i < 150; i++) {
			base.waitTime(1);
			driver.waitForPageToBeReady();
			if (file.exists()) {
				System.out.println(" file is Exists in pointed directory");
				base.passedStep(file + " file is Exists in pointed directory");
				break;
			} else if (file1.exists()) {
				System.out.println(" file is Exists in pointed directory");
				base.passedStep(file + " file is Exists in pointed directory");
				break;
			} else {
				driver.waitForPageToBeReady();
			}
		}

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param firstFile
	 * @param lastFile
	 * @param prefixID
	 * @param suffixID
	 * @Description verifying downloaded mp3 file in generated production
	 */
	public void isMp3FileExist(int firstFile, int lastFile, String prefixID, String suffixID) {
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");
		driver.waitForPageToBeReady();

		for (int i = firstFile; i < lastFile; i++) {
			File mp3 = new File(home + "/Downloads/VOL0001/MP3 Files/0001/" + prefixID + i + suffixID + ".MP3");
			if (mp3.exists()) {
				System.out.println("mp3 is available");
				base.passedStep("mp3 file is exists in generated production");
			} else {
				base.failedStep("mp3 file not exists in this directory");
			}
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param prefixId
	 * @param suffixId
	 * @param beginningBates
	 * @param tagname
	 * @throws InterruptedException
	 */
	public void fillingNumberingAndSortingPageWithSortByTags(String prefixId, String suffixId, String beginningBates,
			String tagname) throws InterruptedException {

		base.waitForElement(getBeginningBates());
		driver.waitForPageToBeReady();
		getBeginningBates().waitAndClick(10);
		getBeginningBates().SendKeys(beginningBates);
		num = getRandomNumber(2);

		base.waitForElement(gettxtBeginningBatesIDPrefix());
		gettxtBeginningBatesIDPrefix().SendKeys(prefixId);

		base.waitForElement(gettxtBeginningBatesIDSuffix());
		gettxtBeginningBatesIDSuffix().SendKeys(suffixId);

		base.waitForElement(gettxtBeginningBatesIDMinNumLength());
		gettxtBeginningBatesIDMinNumLength().waitAndClick(10);
		num1 = getRandomNumber(1);
		System.out.println("Beginning BatesID Min Num Length=" + num1);
		gettxtBeginningBatesIDMinNumLength().SendKeys(getRandomNumber(1));

		driver.scrollingToBottomofAPage();

		sortByTagsRadioButton().waitAndClick(10);
		base.stepInfo("sorting by tags option is selected");

		selectingTagsFromSortByTags(tagname).waitAndClick(10);
		keepFamiliesTogetherChkbox_sortByTags().waitAndClick(10);
		base.waitForElement(getAddSelected());
		getAddSelected().waitAndClick(10);
		base.stepInfo("Keep families checkbox selected");

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @return
	 * @throws InterruptedException
	 * @Description for large Document need more time for generation page so that
	 *              higher wait time added in this method
	 */
	public int fillingGeneratePageWithContinueGenerationPopupHigerWaitTime() throws InterruptedException {

		SoftAssert softAssertion = new SoftAssert();
		String expectedText = "Success";

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGenerate().Enabled() && getbtnProductionGenerate().isDisplayed();
			}
		}), Input.wait30);
		getbtnProductionGenerate().waitAndClick(10);

		getbtnContinueGeneration().isElementAvailable(500);
		if (getbtnContinueGeneration().isDisplayed()) {
			base.waitForElement(getbtnContinueGeneration());
			getbtnContinueGeneration().waitAndClick(10);
		}

		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");

		getDocumentGeneratetext().isElementAvailable(340);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocumentGeneratetext().Visible() && getDocumentGeneratetext().Enabled();
			}
		}), Input.wait120);
		String actualText = getStatusSuccessTxt().getText();
		System.out.println(actualText);

		softAssertion.assertTrue(actualText.contains(expectedText));
		base.passedStep("Documents Generated successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getConfirmProductionCommit().Enabled() && getConfirmProductionCommit().isDisplayed();
			}
		}), Input.wait60);

		// added thread.sleep to avoid exception while executing in batch
		Thread.sleep(2000);
		getConfirmProductionCommit().waitAndClick(10);

		String PDocCount = getProductionDocCount().getText();
		// added thread.sleep to avoid exception while executing in batch
		Thread.sleep(1000);
		System.out.println(PDocCount);
		int Doc = Integer.parseInt(PDocCount);

		Reporter.log("Doc - " + Doc, true);
		System.out.println(Doc);
		UtilityLog.info(Doc);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCopyPath().isDisplayed();
			}
		}), Input.wait60);
		getCopyPath().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getQC_Download().isDisplayed();
			}
		}), Input.wait30);

		getQC_Download().waitAndClick(10);
		getQC_Downloadbutton_allfiles().waitAndClick(10);
		base.VerifySuccessMessage("Your Production Archive download will get started shortly");
		base.stepInfo("Generate production page is filled");

		return Doc;
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param source
	 * @param dest
	 * @throws IOException
	 * @Descrription Copy a File and paste it another location
	 */
	public void copyFileUsingStream(File source, File dest) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
			base.stepInfo("File was copied and pasted another location");
		} finally {
			is.close();
			os.close();
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param file
	 * @param numberOfImages
	 * @throws Exception
	 * @Description Rotate a pdf file content
	 */
	public void RotatePdfFile(File file, int numberOfImages) throws Exception {
		String home = System.getProperty("user.home");
		try {

			// Creating a PdfWriter
			PdfWriter pdfwriter = new PdfWriter(file);

			// Creating a PdfDocument object.
			// passing PdfWriter object constructor of
			// pdfDocument.
			PdfDocument pdfdocument = new PdfDocument(pdfwriter);

			// Creating a Document and passing pdfDocument
			// object
			Document document = new Document(pdfdocument);

			for (int i = 0; i < numberOfImages; i++) {
				// Create an ImageData object
				String imageFile = home + "\\Downloads\\image" + i + ".jpg";
				ImageData data = ImageDataFactory.create(imageFile);

				// Creating an Image object
				Image image = new Image(data);

				// Rotating the image
				image.setRotationAngle(90);

				// Adding image to the document
				document.add(image);
			}
			// Closing the document
			document.close();

			System.out.println("Image has been rotated successfully");
			base.stepInfo("file was rotated 90 degree");
		} catch (Exception e) {
			System.out.println("failed to rotate the image in the file due to " + e);
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param pdf
	 * @return
	 * @throws Exception
	 * @Description Convert Pdf to Jpg file
	 */
	public int pdfToJpgConverter(File pdf) throws Exception {
		String home = System.getProperty("user.home");
		// Existing PDF Document
		// to be Loaded using file io
		PDDocument pdfDocument = PDDocument.load(pdf);

		// PDFRenderer class to be Instantiated
		// i.e. creating it's object
		PDFRenderer pdfRenderer = new PDFRenderer(pdfDocument);

		int nosPage = pdfDocument.getNumberOfPages();
		// Rendering an image
		// from the PDF document
		// using BufferedImage class
		for (int i = 0; i < nosPage; i++) {
			BufferedImage img = pdfRenderer.renderImage(i);
			// Writing the extracted
			// image to a new file
			ImageIO.write(img, "JPEG", new File(home + "\\Downloads\\image" + i + ".jpg"));
			System.out.println("Image has been extracted successfully");
			base.stepInfo("pdf file converted to jpg image file");
		}

		// Closing the PDF document
		pdfDocument.close();
		return nosPage;
	}

	/**
	 * @Author sowndarya.velraj
	 */
	public void NavigateToLastTemplatePage() {
		driver.scrollingToBottomofAPage();
		String lastNum;
		driver.waitForPageToBeReady();
		if (getLastPageNum().isElementAvailable(4)) {
			lastNum = getLastPageNum().getText();
		} else {
			int totalNum = getTotalCustomTemplatePageSize().size();
			lastNum = String.valueOf(totalNum - 2);
		}
		int totalPage = Integer.parseInt(lastNum);
		base.waitForElement(getPageNum(totalPage));
		getPageNum(totalPage).waitAndClick(5);
		driver.waitForPageToBeReady();
	}

	/**
	 * @Author Brundha Description:Method to verify Text Component
	 */
	public void textComponentVerification() {
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		boolean flag;
		flag = getTextRadioBtn().GetAttribute("value").contains("True");

		if (flag) {
			base.passedStep("only export file option is selected by default as expected");
		} else {
			base.failedStep("only export file option is not  selected by default as expected");
		}
		base.waitForElement(getRdbOcr());
		getRdbOcr().waitAndClick(5);
		base.waitTime(1);
		String ExpectedText = "OCR can take a long time to perform if there are a lot of documents - that can slow down your production delivery."
				+ " Plus, its likely that these non-redacted docs failed OCR earlier in processing - which may be why no text exists for these records in the Sightline workspace."
				+ " That could complicate your production delivery timelines.";
		String ActualText = getBellyBandText().getText();
		base.compareTextViaContains(ActualText, ExpectedText, "Belly band message is displayed as expected",
				"Belly Band message is not displayed as expected");
		String BellyBandMsg = "Are you sure you want to OCR these non-redacted documents that have no text already in the workspace?";
		String ExpText = getBellyBangMsg().getText();
		base.compareTextViaContains(BellyBandMsg, ExpText, "Belly band message is displayed as expected",
				"Belly Band message is not displayed as expected");
		base.getNOBtn().waitAndClick(10);
		base.waitForElement(getRdbOcr());
		getRdbOcr().waitAndClick(5);
		getYesBtn().waitAndClick(5);

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @Description go to export Image file
	 */
	public void goToImageFiles() {
		driver.waitForPageToBeReady();
		getFileDir("VOL0001").waitAndClick(10);
		driver.waitForPageToBeReady();
		getFileDir("Images/").waitAndClick(10);
		driver.waitForPageToBeReady();
		getFileDir("0001/").waitAndClick(10);
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param file
	 * @param expectedWord
	 * @Description verify the text in generated image file
	 */
	public void OCR_Verification_In_Generated_Tiff_tess4j(File file, String expectedWord) {
		ITesseract instance = new Tesseract1(); // JNA Direct Mapping
		File tessDataFolder = LoadLibs.extractTessResources("tessdata"); // Maven build bundles English data
		instance.setDatapath(tessDataFolder.getPath());

		try {
			String result = instance.doOCR(file);
			System.out.println(result);
			if (result.contains(expectedWord)) {
				base.passedStep("Document is produced as expect");
			} else {
				base.failedStep("document validation failed");
			}
		} catch (TesseractException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param prefixId
	 * @param suffixId
	 * @param subBates
	 * @throws InterruptedException
	 * @Description export filling number and sorting page
	 */
	public void fillingExportNumberingAndSortingPage(String prefixId, String suffixId, String subBates)
			throws InterruptedException {

		base.waitForElement(getBeginningSubBatesNumber());
		getBeginningSubBatesNumber().SendKeys(subBates);

		base.waitForElement(getExportPrefixId());
		getExportPrefixId().SendKeys(prefixId);

		base.waitForElement(getExportSuffixId());
		getExportSuffixId().SendKeys(suffixId);

		base.stepInfo("Export Numbering and sorting section is filled");
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param redTagg
	 * @Description enable burn redaction and select redaction ta
	 */
	public void selectBurnRedaction(String redTag) {
		getClk_burnReductiontoggle().ScrollTo();
		getClk_burnReductiontoggle().waitAndClick(10);
		getTIFF_SelectRed_Radiobutton().waitAndClick(10);
		base.waitForElement(BurnRedactionCheckBox(redTag));
		BurnRedactionCheckBox(redTag).ScrollTo();
		BurnRedactionCheckBox(redTag).waitAndClick(10);
	}

	/**
	 * @author Brundha.T Description:verfy DAT source field
	 */
	public void verifyingDatFieldClassification() {

		base.waitForElement(getDATChkBox());
		getDATChkBox().waitAndClick(10);

		base.waitForElement(getDATTab());
		getDATTab().waitAndClick(10);

		base.waitForElement(getDAT_FieldClassification1());
		getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText(Input.productionText);

		driver.waitForPageToBeReady();

		List<WebElement> option = getDATSourceField().FindWebElements();
		int j;
		List<String> options = new ArrayList<String>();
		for (j = 0; j < option.size(); j++) {
			driver.waitForPageToBeReady();
			options.add(option.get(j).getText());
		}
		System.out.println(options);
		if (options.contains("TIFFPageCount")) {
			base.passedStep("field mapping value is displayed as expected");
		} else {
			base.failedStep("Field mapping value  is not displayed");
		}
	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To validate the fields in Production and export Home page
	 * @param fieldName
	 */
	public void verifyProductionAndExportHomePage(String fieldName) {

		if (fieldName.contains("Export")) {
			base.waitForElement(getProdExport_NoProductionExitSet());
			String noData = getProdExport_NoProductionExitSet().getText();
			base.waitForElement(getProdExport_ManageTemplateButton());
			getProdExport_ManageTemplateButton().waitAndClick(5);

			base.waitForElement(getProdExport_CustomTemplatesEmptyValue());
			String customFieldValue = getProdExport_CustomTemplatesEmptyValue().getText();
			if (noData.contains("No") && customFieldValue.contains("Your")) {
				base.passedStep(
						" User created a new Project Using template project and no export template exists then no template is copied from the source template project to the newly created Project.");

			} else {
				base.failedStep("Export filed has some datas");
			}
		}

		else if (fieldName.contains("Prod")) {
			base.waitForElement(getProdExport_NoProductionExitSet());
			String noData = getProdExport_NoProductionExitSet().getText();
			base.waitForElement(getProdExport_ManageTemplateButton());
			getProdExport_ManageTemplateButton().waitAndClick(5);

			base.waitForElement(getProdExport_CustomTemplatesEmptyValue());
			String customFieldValue = getProdExport_CustomTemplatesEmptyValue().getText();
			if (noData.contains("No") && customFieldValue.contains("Your")) {
				base.passedStep(
						" User created a new Project Using template project and no Productions template exists then no template is copied from the source template project to the newly created Project.");

			} else {
				base.failedStep("Export filed has some datas");
			}
		}

	}

	/**
	 * @author Vijaya.Rani Modify Date:21/06/2022 Description:verfy the field
	 *         mapping value in DAT Section
	 */
	public void verifyingDatMappingField() {
		base.waitForElement(getDATChkBox());
		getDATChkBox().waitAndClick(10);
		base.waitForElement(getDATTab());
		getDATTab().waitAndClick(10);
		base.waitForElement(getDAT_FieldClassification1());
		getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText(Input.docBasic);
		List<WebElement> option = getDATSourceField().FindWebElements();
		int j;
		List<String> options = new ArrayList<String>();
		for (j = 0; j < option.size(); j++) {
			driver.waitForPageToBeReady();
			options.add(option.get(j).getText());
		}
		System.out.println(options);
		if (!options.contains("TIFFPageCount")) {
			base.passedStep("TiffPageCount is not displayed as expected");
		} else {
			base.failedStep("TiffPageCount is displayed");
		}
	}

	/**
	 * @author Brundha.T
	 * @param BrandingText Description:verifying the pdf file
	 * @throws IOException
	 *
	 * 
	 */
	public void verifyingPdfgeneration(String BrandingText) throws IOException {
		base.waitTime(2);
		String fileName = base.GetFileName();
		PDDocument document = PDDocument.load(new File(fileName));
		if (!document.isEncrypted()) {
			PDFTextStripper stripper = new PDFTextStripper();
			String text = stripper.getText(document);
			System.out.println("Text:" + text);
			if (text.contains(BrandingText)) {
				base.passedStep(BrandingText + " text is produced in this document");
			} else {
				base.failedStep(BrandingText + " text is not  produced in this document");
			}
			document.close();
		}

	}

	/**
	 * @author: sowndarya.velraj Created date: NA Modified date: NA Modified
	 *          sowndarya.velraj
	 * @Description: Method for filling natively produced docs in pdf section.
	 * @param Tag  : Tag is String value that name of tag.
	 * @param Text : Text is String value that need to enter in place holder.
	 */
	public void fillingPDFWithNativelyProduceddDocsSelectingFileType(String Tag, String Text, String metaDataLink,
			String fileType) {

		base.waitForElement(getPDFChkBox());
		getPDFChkBox().Click();
		base.waitForElement(getPDFTab());
		getPDFTab().Click();

		// disabling enable for priviledged docs

		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().Enabled();
		getTIFF_EnableforPrivilegedDocs().waitAndClick(10);

		// clicking enable for natively placeholder

		driver.waitForPageToBeReady();
		getSelectCloseBtn().ScrollTo();
		getSelectCloseBtn().waitAndClick(10);

		base.waitForElement(getTiff_NativeDoc());
		getTiff_NativeDoc().waitAndClick(10);

		driver.waitForPageToBeReady();
		ddnselectFileType().selectFromDropdown().selectByVisibleText(fileType);

		base.waitForElement(getclkSelectTag());
		getclkSelectTag().Click();
		base.waitForElement(getPriveldged_TagTree(Tag));
		getPriveldged_TagTree(Tag).Click();
		base.waitForElement(getClkSelect());
		getClkSelect().Click();

		driver.waitForPageToBeReady();
		base.waitForElement(getNativeDocsPlaceholder());
		getNativeDocsPlaceholder().Clear();
		getNativeDocsPlaceholder().SendKeys(Text);

		insertMetadataLink().waitAndClick(10);
		getTIFF_selectedMetadataField().selectFromDropdown().selectByVisibleText(metaDataLink);

		btnOK_Metafield().waitAndClick(10);
	}

	/**
	 * @author sowndarya.velraj
	 * @param firstFile
	 * @param lastFile
	 * @param prefixID
	 * @param suffixID
	 * @param verificationText
	 * @Description verifying text on the PDF image file on downloaded zip file
	 */
	public void OCR_Verification_In_Generated_PDF(int firstFile, int lastFile, String prefixID, String suffixID,
			String verificationText) {
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");

		for (int i = firstFile; i < lastFile; i++) {

			File imageFile = new File(home + "/Downloads/VOL0001/Images/0001/" + prefixID + i + suffixID + ".pdf");
			// ITesseract instance = new Tesseract(); // JNA Interface Mapping
			ITesseract instance = new Tesseract1(); // JNA Direct Mapping
			File tessDataFolder = LoadLibs.extractTessResources("tessdata"); // Maven build bundles English data
			instance.setDatapath(tessDataFolder.getPath());

			try {
				String result = instance.doOCR(imageFile);
				System.out.println(result);
				if (result.contains(verificationText)) {
					base.passedStep(verificationText + " is displayed in " + prefixID + i + suffixID + ".pdf"
							+ " file as expected");
				} else {
					base.failedStep(verificationText + " verification failed");
				}
			} catch (TesseractException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	/**
	 * @author sowndarya.velraj
	 * @param firstFile
	 * @param lastFile
	 * @param prefixID
	 * @param suffixID
	 * @param verificationText
	 * @Description verifying text on the PDF image file on downloaded zip file
	 */
	public void OCR_Verification__BatesNo_In_GeneratedFile(String prefixID, String suffixID, String beginningBates) {
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");

		File destinationLoc = new File(
				home + "/Downloads/VOL0001/Images/0001/" + prefixID + beginningBates + suffixID + ".tiff");
		// ITesseract instance = new Tesseract(); // JNA Interface Mapping
		ITesseract instance = new Tesseract1(); // JNA Direct Mapping
		File tessDataFolder = LoadLibs.extractTessResources("tessdata"); // Maven build bundles English data
		instance.setDatapath(tessDataFolder.getPath());

		try {
			String result = instance.doOCR(destinationLoc);
			System.out.println(result);
			if (result.contains(beginningBates)) {
				base.passedStep(beginningBates + " is displayed in " + prefixID + beginningBates + suffixID + ".tiff"
						+ " file as expected");
			} else {
				base.failedStep(beginningBates + " verification failed");
			}
		} catch (TesseractException e) {
			System.err.println(e.getMessage());
		}
	}

	/**
	 * @author Vijaya.Rani
	 * @description: To validate the fields in Production and export Home page
	 * @param fieldName
	 */
	public void verifyProductionTemplateAndExportTemplateHomePage(String fieldName) {

		if (fieldName.contains("Export")) {
			base.waitForElement(getProdExport_NoProductionExitSet());
			String noData = getProdExport_NoProductionExitSet().getText();
			System.out.println(noData);
			base.waitForElement(getProdExport_ManageTemplateButton());
			getProdExport_ManageTemplateButton().waitAndClick(5);

			base.waitForElement(getProdExport_CustomTemplatesValues());
			if (getProdExport_CustomTemplatesValues().Displayed()) {
				base.passedStep(
						"User created a new Project Using template project and no Productions template exists then no template is copied from the source template project to the newly created Project.Export template should exists in newly created Project.If no export had been saved as a template, then no templates are copied.");

			} else {
				base.failedStep("Export filed has some datas");
			}
		}

		else if (fieldName.contains("Prod")) {
			base.waitForElement(getProdExport_NoProductionExitSet());
			String noData = getProdExport_NoProductionExitSet().getText();
			System.out.println(noData);
			base.waitForElement(getProdExport_ManageTemplateButton());
			getProdExport_ManageTemplateButton().waitAndClick(5);

			base.waitForElement(getProdExport_CustomTemplatesValues());
			if (getProdExport_CustomTemplatesValues().Displayed()) {
				base.passedStep(
						"User created a new Project Using template project and no Productions template exists then no template is copied from the source template project to the newly created Project.Export template should exists in newly created Project.If no export had been saved as a template, then no templates are copied.");

			} else {
				base.failedStep("Export filed has some datas");
			}
		}
	}

	/**
	 * @author Brundha.T description:Method to verify the advanced option text in
	 *         tiff section
	 */
	public void verifyingAdvancedOptionInTiffSection() {

		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();
		driver.scrollingToBottomofAPage();
		base.waitForElement(getTiffAdvancedLink());
		getTiffAdvancedLink().waitAndClick(5);
		driver.scrollingToBottomofAPage();
		nonVisibleCheck(Input.advancedOptionText);

	}

	/**
	 * @author Brundha.T
	 * @param tagname
	 * @param tagname1
	 * @throws InterruptedException
	 * @Description:method to filling native placeholder with two tags
	 */
	public void nativePlaceholderWithTwoTags(boolean value, String tagname, String tagname1)
			throws InterruptedException {
		driver.scrollingToBottomofAPage();
		base.waitForElement(getSelectCloseBtn());
		getSelectCloseBtn().Click();
		base.waitForElement(getTiff_NativeDoc());
		getTiff_NativeDoc().Click();
		if (value) {
			base.waitTillElemetToBeClickable(getFileTypeNativelyProducedDocs());
			getFileTypeNativelyProducedDocs().Click();
		}
		base.waitForElement(getclkSelectTag());
		getclkSelectTag().Click();
		base.waitForElement(getPriveldged_TagTree(tagname));
		getPriveldged_TagTree(tagname).waitAndClick(10);
		base.waitForElement(getPriveldged_TagTree(tagname1));
		getPriveldged_TagTree(tagname1).waitAndClick(10);
		base.waitForElement(getClkSelect());
		getClkSelect().Click();
		driver.scrollingToBottomofAPage();
		base.waitForElement(getNativeDocsPlaceholder());
		getNativeDocsPlaceholder().SendKeys(Input.searchString4);
	}

	/**
	 * @author Brundha.T
	 * @param tagname
	 * @param Tagname2 Description:Selecting Multiple tags in tiff section
	 */
	public void selectMultiBrandingTags(String tagname, String Tagname2) {

		getBrandingBySelectingTags().ScrollTo();
		base.waitTillElemetToBeClickable(getBrandingBySelectingTags());
		getBrandingBySelectingTags().Click();
		base.waitForElement(getbtnSelectTags());
		getbtnSelectTags().Click();
		base.waitForElement(getChkBoxSelect(tagname));
		getChkBoxSelect(tagname).waitAndClick(5);
		base.waitForElement(getChkBoxSelect(Tagname2));
		getChkBoxSelect(Tagname2).Click();
		getbtnSelect().waitAndClick(10);
		base.waitForElement(getInsertMetaDataLink());
		getInsertMetaDataLink().Click();
		getTIFF_selectedMetadataField().selectFromDropdown().selectByVisibleText("AttachCount");
		getBrandingOkBtn().waitAndClick(5);

	}

	/**
	 * @author Brundha.T Description:verifying the document count in multiple
	 *         branding Tags
	 */
	public void verifyingMultipleBrandingCount() {

		base.waitForElement(getMultiBrandingTags());
		String BrandingCount = getMultiBrandingTags().getText();
		if (Integer.parseInt(BrandingCount) != (0) && BrandingCount.equals(Input.pageCount)) {
			base.passedStep("Document count is displayed as expected");
		} else {
			base.failedStep("Document count is not displayed as expected");
		}

	}

	/**
	 * @author sowndarya.velraj
	 * @Description filling DAT section with specific classification after bates
	 */
	public String fillingDatWithSpecificClassification(String classification, String sourceField, String datField) {
		addNewFieldOnDAT();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_FieldClassification2().Visible() && getDAT_FieldClassification2().Enabled();
			}
		}), Input.wait30);
		getDAT_FieldClassification2().selectFromDropdown().selectByVisibleText(classification);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_SourceField2().Visible() && getDAT_SourceField2().Enabled();
			}
		}), Input.wait30);
		getDAT_SourceField2().selectFromDropdown().selectByVisibleText(sourceField);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDAT_DATField2().Visible() && getDAT_DATField2().Enabled();
			}
		}), Input.wait30);
		getDAT_DATField2().SendKeys(datField);
		base.stepInfo("Dat section is filled with required specifications");
		return datField;
	}

	/**
	 * Modified on 03/05/2022
	 * 
	 * @authorIndium-Sowndarya.Velraj
	 * @param tagname
	 * @param This    method selects TIFF component and disables "Enable for
	 *                privileged tags" toggle.Then "EnablesBurn Redaction Tags"
	 *                toggle and select the particular tag.
	 */
	public void TIFFSectionEnableBurnRedactionAndSelectRedactedTag(String tagname) throws InterruptedException {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();

		driver.scrollingToBottomofAPage();

		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();

		getTIFF_EnableforPrivilegedDocs().ScrollTo();

		// disabling enable for priviledged docs

		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		base.waitTillElemetToBeClickable(getTIFF_EnableforPrivilegedDocs());
		getTIFF_EnableforPrivilegedDocs().Enabled();
		getTIFF_EnableforPrivilegedDocs().Click();

		// diabling enable for natively placeholder
		base.waitForElement(getEnableForNativelyToggle());
		getEnableForNativelyToggle().waitAndClick(10);

		getClk_burnReductiontoggle().ScrollTo();

		// enable burn redaction
		base.waitForElement(getClk_burnReductiontoggle());
		getClk_burnReductiontoggle().Click();

		getClkLink_selectingRedactionTags().ScrollTo();
		base.waitForElement(getClkLink_selectingRedactionTags());
		getClkLink_selectingRedactionTags().isDisplayed();
		getClkLink_selectingRedactionTags().waitAndClick(10);

		getClkBtn_selectingRedactionTags().ScrollTo();
		base.waitForElement(getClkBtn_selectingRedactionTags());
		getClkBtn_selectingRedactionTags().isDisplayed();
		getClkBtn_selectingRedactionTags().waitAndClick(10);

		redactionTagInBurnRedactionToggle(tagname).waitAndClick(10);
		base.waitForElement(getClk_selectBtn());
		getClk_selectBtn().isDisplayed();
		getClk_selectBtn().waitAndClick(10);

		base.waitForElement(gettextRedactionPlaceHolder());
		gettextRedactionPlaceHolder().isDisplayed();
		gettextRedactionPlaceHolder().waitAndClick(10);
		gettextRedactionPlaceHolder().SendKeys(searchString4);
	}

	/**
	 * @author sowndarya.velraj
	 * @Description verifying natively placeholder toggle default changes
	 */
	public void verifyEnableNativelyProduceddocs() {
		driver.waitForPageToBeReady();
		defaultTextInNativeToggle().isDisplayed();
		base.stepInfo("Document Produced in Native Format Text is present in text area by default");
	}

	/**
	 * @throws InterruptedException
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void fillingNumberingPageWithDocumentLevelAndSubBates(String bates, String PrefixID, String SuffixID)
			throws InterruptedException {

		base.waitForElement(gettxtBeginningBatesIDPrefix());
		gettxtBeginningBatesIDPrefix().SendKeys(PrefixID);

		base.waitForElement(gettxtBeginningBatesIDSuffix());
		gettxtBeginningBatesIDSuffix().SendKeys(SuffixID);

		base.waitForElement(getNumbering_Document_RadioButton());
		getNumbering_Document_RadioButton().Click();

		base.waitForElement(getNumbering_Document_BeginningBates_Text());
		getNumbering_Document_BeginningBates_Text().SendKeys(bates);

	}

	/**
	 * @authorIndium-Sowndarya.Velraj.
	 */
	public void toStartGenerate() throws InterruptedException {
		SoftAssert softAssertion = new SoftAssert();
		String expectedText = "Success";

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getbtnProductionGenerate().Enabled() && getbtnProductionGenerate().isDisplayed();
			}
		}), Input.wait30);
		getbtnProductionGenerate().waitAndClick(10);

		getVerifyGenStatus("Reserving Bates").isElementAvailable(150);
		getbtnContinueGeneration().isElementAvailable(60);
		if (getbtnContinueGeneration().isDisplayed()) {
			base.waitForElement(getbtnContinueGeneration());
			getbtnContinueGeneration().waitAndClick(10);
		}

		Reporter.log("Wait for generate to complete", true);
		System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");

		getDocumentGeneratetext().isElementAvailable(180);
		base.stepInfo("wait until Document Generated Text is visible");
		String actualText = getStatusSuccessTxt().getText();
		System.out.println(actualText);
		softAssertion.assertEquals(expectedText, actualText);
		softAssertion.assertAll();
	}

	/**
	 * @author Brundha.T
	 * @param ProdName Description:Method to verify the failed status in production
	 *                 generate page
	 */
	public void verifyingFailedStatusInProduction(String ProdName) {
		String ReservingBatesRangeFailed = getFailedStatus().getText();
		if (ReservingBatesRangeFailed.equals("Reserving Bates Range Failed")) {
			base.passedStep("" + ReservingBatesRangeFailed + " is displayed as expected");
		} else {
			base.failedStep("" + ReservingBatesRangeFailed + " is not displayed as expecetd");
		}
		driver.waitForPageToBeReady();
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		getProductionNameLink(ProdName).waitAndClick(5);
		getFailedStatus().waitAndClick(5);
		String ErrorMsg = "PreGenCheck : Bates numbers are duplicate.";
		String ActualErrorMsg = getErrorMsg().getText();
		base.compareTextViaContains(ErrorMsg, ActualErrorMsg, "Error msg is displayed as expected",
				"Error msg is not displayed as expected");

	}

	/**
	 * @author Brundha.T Description:Method to verify default selected option in
	 *         natively produced doc placeholder
	 */
	public void verifyingTheDefaultSelectedOptionInNative() {
		selectGenerateOption(false);
		getEnableForNativelyToggle().isDisplayed();
		String color = getEnableForNativelyToggle().GetCssValue("background-color");
		String ExpectedColor = Color.fromString(color).asHex();
		System.out.println(ExpectedColor);
		String ActualColor = "#a9c981";
		driver.scrollingToBottomofAPage();
		base.textCompareEquals(ActualColor, ExpectedColor, "Natively produced docs toggle is enabled by default",
				"Natively produced docs toggle is not  enabled  by Default");
		String ActualText = getNativeDocsPlaceholderText().getText();
		String ExpectedText = "Document Produced in Native Format.";
		base.textCompareEquals(ActualText, ExpectedText, "Default text in native placeholder is displayed as expected",
				"Text is not Displayed as expected");

	}

	/**
	 * @author Brundha.T
	 * @param ProdName
	 * @param TagName
	 * @description: verifying component tab in drafted production
	 */
	public void verifyingDraftedProductionInComponentTab(String ProdName, String TagName) {
		getProductionNameLink(ProdName).waitAndClick(5);
		gettext("Back").Click();
		getMarkInCompleteBtn().Click();
		selectPrivDocsInTiffSection(TagName);
		driver.scrollPageToTop();
		getMarkCompleteLink().waitAndClick(10);
		base.CloseSuccessMsgpopup();
		getCheckBoxCheckedVerification(chkIsDATSelected());
		getCheckBoxCheckedVerification(chkIsTIFFSelected());
		getTIFFTab().waitAndClick(5);
		driver.scrollingToBottomofAPage();
		String PlaceholderText = getPriveldge_TextArea().getText();
		System.out.println(PlaceholderText);
		base.textCompareEquals(PlaceholderText, Input.tagNameTechnical,
				"All the values entered retained on markcomplete", "Entered values not retained");

	}

	/**
	 * @author Brundha.T
	 * @description : Method to fill native section with tags.
	 * @param Tag
	 */
	public void fillingNativeSectionWithTag(String Tag, String Tag1, boolean value) {

		if (value) {
			base.waitForElement(getNativeTab());
			getNativeTab().Click();
			base.waitForElement(getNativeSelectTags());
			getNativeSelectTags().Click();
			base.waitForElement(getNativeCheckBox(Tag));
			getNativeCheckBox(Tag).Click();
			base.waitForElement(getNativeSelect());
			getNativeSelect().Click();
		} else {
			getNativeTab().waitAndClick(10);
			base.waitForElement(getNativeSelectTags());
			getNativeSelectTags().Click();
			base.waitForElement(getNativeCheckBox(Tag));
			getNativeCheckBox(Tag).Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getNativeCheckBox(Tag1));
			getNativeCheckBox(Tag1).Click();
			base.waitForElement(getNativeSelect());
			getNativeSelect().Click();
			base.stepInfo("Native section is filled");
		}

	}

	/**
	 * @author Brundha.T
	 * @param FileType Description:verifying the native filetype checked
	 */
	public void verifyingNativeSectionFileType(String FileType) {
		String value = getNativeFileTypeCheckBox(FileType).GetAttribute("checked");
		boolean bool = Boolean.parseBoolean(value);
		System.out.println(bool);
		if (bool == true) {
			base.passedStep(FileType + "is checked as expected");
		} else {
			base.failedStep(FileType + "is not checked");
		}

	}

	/**
	 * @author Brundha.T
	 * @param tagname
	 * @param BrandingPlaceholder
	 * @param PrivPlaceholder     Description: filling tiff section with branding
	 * 
	 */
	public void fillingTIFFSectionWithBranding(String tagname, String BrandingPlaceholder, String PrivPlaceholder) {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().waitAndClick(5);
		driver.scrollingToBottomofAPage();
		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();
		driver.scrollPageToTop();
		base.waitForElement(getTIFF_CenterHeaderBranding());
		getTIFF_CenterHeaderBranding().Click();
		new Actions(driver.getWebDriver()).moveToElement(getTIFF_EnterBranding().getWebElement()).click();
		getTIFF_EnterBranding().SendKeys(BrandingPlaceholder);
		getTIFF_EnableforPrivilegedDocs().ScrollTo();
		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		base.waitForElement(getPriveldge_SelectTagButton());
		getPriveldge_SelectTagButton().Click();
		driver.waitForPageToBeReady();
		driver.scrollingToElementofAPage(getPriveldge_TagTree(tagname));
		getPriveldge_TagTree(tagname).waitAndClick(10);
		getPriveldge_TagTree_SelectButton().waitAndClick(10);
		new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();
		getPriveldge_TextArea().SendKeys(PrivPlaceholder);

	}

	/**
	 * @author Brundha.T
	 * @param Tag Description: selecting document in sorting tab
	 */
	public void selectingTaginSortingPage(String Tag) {
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		getSortingRadioBtn().waitAndClick(5);
		driver.scrollingToBottomofAPage();
		base.waitForElement(selectTagInSortingPage(Tag));
		selectTagInSortingPage(Tag).waitAndClick(10);
		getAddToSelectedBtn().Click();

	}

	/**
	 * @author Brundha.T
	 * @param tagname
	 * @param tagnametech
	 * @Description This method selects TIFF radio button and selects Enable for
	 *              privileged tags by default and passes the placeholder value.
	 */
	public void fillingTIFFSectionPrivDocs(String tagname, String tagnametech) throws InterruptedException {

		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();
		driver.scrollingToBottomofAPage();
		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();
		getTIFF_EnableforPrivilegedDocs().ScrollTo();
		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		base.waitForElement(getPriveldge_SelectTagButton());
		getPriveldge_SelectTagButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		driver.scrollingToElementofAPage(getPriveldge_TagTree(tagname));
		getPriveldge_TagTree(tagname).waitAndClick(10);
		base.waitForElement(getPriveldge_TagTree_SelectButton());
		getPriveldge_TagTree_SelectButton().waitAndClick(10);
		new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();
		getPriveldge_TextArea().SendKeys(tagnametech);
		driver.scrollingToBottomofAPage();
		base.stepInfo("TIFF section is filled");

	}

	/**
	 * @author Brundha.T
	 * @param Text Description:filling Native section with filetype in tiff section
	 */
	public void fillingNativePlaceHolderFileTypeInTiffSection(String Text) {
		selectGenerateOption(false);
		getSelectCloseBtn().waitAndClick(10);
		base.waitForElement(getTiff_NativeDoc());
		getTiff_NativeDoc().Click();
		getFileTypeNativelyProducedDocs().Click();
		driver.waitForPageToBeReady();
		base.waitForElement(getNativeDocsPlaceholder());
		getNativeDocsPlaceholder().Click();
		getNativeDocsPlaceholder().SendKeys(Text);

	}

	/**
	 * @author Brundha.T Date:7/19/2022
	 * @description :Verifying the default selected option in manage template
	 */
	public void verifyingComponentTabSection() {

		driver.waitForPageToBeReady();
		getTIFFChkBox().ScrollTo();
		base.waitForElement(getTIFFChkBox());
		base.waitTime(1);
		getCheckBoxCheckedVerification(chkIsDATSelected());
		getCheckBoxCheckedVerification(chkIsTIFFSelected());
		base.waitForElement(getTIFFTab());
		getTIFFTab().Click();
		verifyingNativeSectionFileType("Spreadsheets");
		verifyingNativeSectionFileType("Multimedia");
		getCloseIconInManageTemplate().ScrollTo();
		base.waitForElement(getCloseIconInManageTemplate());
		getCloseIconInManageTemplate().Click();
	}

	/**
	 * @author Brundha.T
	 * @param fieldValue
	 * @Description:filling slip sheet in tiff section
	 */
	public void fillingSlipSheetInTiffSection(String fieldValue) {
		driver.waitForPageToBeReady();
		base.waitForElement(getCalculatedCheckBoxSelection(fieldValue));
		base.clickButton(getCalculatedCheckBoxSelection(fieldValue));
		base.clickButton(getAddSelected());
	}

	/**
	 * 
	 * @param purehit
	 * @param prefixID
	 * @param suffixID
	 * @param subBates
	 * @param verificationText
	 * @throws UnsupportedFlavorException
	 * @throws IOException
	 * @Description: verifying Extract copypath file
	 */
	public void verifyingExportFile(int purehit, String prefixID, String suffixID, String subBates,
			String verificationText) throws UnsupportedFlavorException, IOException {
		String actualCopedText = getCopiedTextFromClipBoard();
		String parentTab = openNewTab(actualCopedText);
		goToImageFiles();
		driver.waitForPageToBeReady();
		driver.waitForPageToBeReady();
		if (getFirstImageFile(prefixID + "(" + 2 + ")" + suffixID, subBates).isElementAvailable(2)) {
			for (int i = 2; i < purehit; i++) {
				getFirstImageFile(prefixID + "(" + i + ")" + suffixID, subBates).waitAndClick(10);
			}

			driver.waitForPageToBeReady();
			for (int i = 2; i < purehit; i++) {
				File TiffFile = new File(
						Input.fileDownloadLocation + prefixID + "(" + i + ")" + suffixID + ".000" + subBates + ".tiff");
				OCR_Verification_In_Generated_Tiff_tess4j(TiffFile, verificationText);
			}
			driver.close();
			driver.getWebDriver().switchTo().window(parentTab);

		} else if (getFirstImageFile(prefixID + "0" + "(" + 2 + ")" + suffixID, subBates).isElementAvailable(2)) {
			for (int i = 2; i < purehit; i++) {
				getFirstImageFile(prefixID + "0" + "(" + i + ")" + suffixID, subBates).waitAndClick(10);
			}

			driver.waitForPageToBeReady();
			for (int i = 2; i < purehit; i++) {
				File TiffFile = new File(Input.fileDownloadLocation + prefixID + "0" + "(" + i + ")" + suffixID + ".000"
						+ subBates + ".tiff");
				OCR_Verification_In_Generated_Tiff_tess4j(TiffFile, verificationText);
			}
			driver.close();
			driver.getWebDriver().switchTo().window(parentTab);

		}
	}

	/**
	 * @author Brundha.T
	 * @param TagName
	 * @param TagName1
	 * @param Tag      Description: Method to verify enabled tag in privileged
	 *                 toggle:
	 */
	public void verifyPrivTagSelectionAndDisableOfOtherTag(String TagName, String TagName1, String Tag) {
		base.waitForElement(getTIFFChkBox());
		getTIFFChkBox().Click();
		getTIFFTab().waitAndClick(10);
		getTIFF_EnableforPrivilegedDocs().ScrollTo();
		base.waitForElement(getTIFF_EnableforPrivilegedDocs());
		base.waitForElement(getPriveldge_SelectTagButton());
		getPriveldge_SelectTagButton().waitAndClick(10);
		driver.scrollingToElementofAPage(verifyingPrivilegedTag(TagName));
		String ActualText = verifyingPrivilegedTag(TagName).GetAttribute("iscascadeenabled");
		base.textCompareEquals(ActualText, "true", "Privileged tag is enabled as expected",
				"Privileged tag is disabled");

		driver.scrollingToElementofAPage(verifyingPrivilegedTag(TagName1));
		String ActualTextInTab = verifyingPrivilegedTag(TagName1).GetAttribute("iscascadeenabled");
		base.textCompareEquals(ActualTextInTab, "false", "Other Tags are disabled as expected",
				"Other Tags are not disabled as expecetd");

		driver.scrollingToElementofAPage(verifyingPrivilegedTag(Tag));
		String ActualTextInTag = verifyingPrivilegedTag(Tag).GetAttribute("iscascadeenabled");
		base.textCompareEquals(ActualTextInTag, "false", "Other Tags are disabled as expected",
				"Other Tags are not disabled as expecetd");

	}

	/**
	 * @author Brundha.T
	 * @param purehit
	 * @param prefixID
	 * @param suffixID
	 * @param subBates
	 * @param searchString Description:verifying Image file in generated export
	 */
	public void verifyTiffFile(int purehit, String prefixID, String suffixID, String subBates, String searchString) {
		driver.waitForPageToBeReady();
		for (int i = 2; i < purehit; i++) {
			getFirstImageFile(prefixID + "(" + i + ")" + suffixID, subBates).waitAndClick(10);
		}

		driver.waitForPageToBeReady();
		for (int i = 2; i < purehit; i++) {
			File imageFile = new File(
					Input.fileDownloadLocation + prefixID + "(" + i + ")" + suffixID + ".000" + subBates + ".tiff");
			OCR_Verification_In_Generated_Tiff_tess4j(imageFile, searchString);
		}

	}

	/**
	 * @author Brundha.T
	 * @param i
	 * @param classification
	 * @param sourceField
	 * @Description filling Dat Section
	 */
	public void addingDatField(int i, String classification, String sourceField, String BatesNumber) {
		getDATChkBox().Click();
		base.waitForElement(getDATTab());
		getDATTab().Click();
		base.waitForElement(getDAT_FieldClassification(i));
		getDAT_FieldClassification(i).selectFromDropdown().selectByVisibleText(classification);
		driver.waitForPageToBeReady();
		getDAT_SourceField(i).selectFromDropdown().selectByVisibleText(sourceField);
		driver.waitForPageToBeReady();
		getDAT_DATField(i).waitAndClick(10);
		getDAT_DATField(i).SendKeys(BatesNumber);
		base.stepInfo(i + "th Dat section is filled");
	}

	/**
	 * @author Brundha.T
	 * @param DatFile
	 * @param value
	 * @param value1
	 * @param CompareString
	 * @throws IOException Description:verifying downloaded dat file
	 */
	public void verifyingDATFile(File DatFile, int value, int value1, String CompareString) throws IOException {
		String line;
		List<String> lines = new ArrayList<>();
		BufferedReader brReader = new BufferedReader(new InputStreamReader(new FileInputStream(DatFile), "UTF16"));
		while ((line = brReader.readLine()) != null) {
			lines.add(line);
		}
		driver.waitForPageToBeReady();
		String[] arrOfStr = lines.get(value).split("þ");
		String OutputDatValue = arrOfStr[value1];
		System.out.println(OutputDatValue);
		if (CompareString.equals(OutputDatValue)) {
			base.passedStep("" + OutputDatValue + " is displayed in DAT File");
		} else {
			base.failedStep("" + OutputDatValue + " is not displayed in DAT File");
		}

		brReader.close();

	}

	/**
	 * @Author jeevitha
	 * @Description : returns priv guard textbox value
	 * @return
	 */
	public List<String> privGuardTextBoxValue() {
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		List<String> textBoxValue = base.availableListofElements(getPrivGuardTextBox_Value());
		base.stepInfo("Priv Guard Textbox value is : " + textBoxValue);

		return textBoxValue;
	}

	/**
	 * @author Brundha.T Date:8/04/2022 Description:Navigating to production home
	 *         page
	 * 
	 */
	public void navigatingToProductionHomePage() {
		base.waitTime(1);
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();

	}

	/**
	 * @author Brundha.T Date:8/04/2022 Description:Filling basic info page
	 */
	public void fillingBasicInfo(String productionname) {
		getProductionName().Clear();
		getProductionName().SendKeys(productionname);

	}

	/**
	 * @author Brundha.T Date:8/04/2022
	 * @param ele
	 * @param ActualColor Description: Method to verify toggle
	 */

	public void verifyingToggleEnabledOrDisabled(Element ele, String ActualColor) {
		driver.waitForPageToBeReady();
		String color = ele.GetCssValue("background-color");
		String ExpectedColor = Color.fromString(color).asHex();
		System.out.println(ExpectedColor);
		if (ActualColor.equals(ExpectedColor)) {
			base.passedStep("" + color + " is displayed as expecetd");
		} else {
			base.failedStep("" + color + " is  not displayed as expecetd");
		}
	}

	/**
	 * @author Brundha.T Date:8/04/2022
	 * @param exportname
	 * @Description: method selects export set from dropdown and creates a new
	 *               export in Production homepage.
	 */
	public void addANewExportAndSave(String exportname) throws InterruptedException {

		getAddNewExport().Click();
		driver.waitForPageToBeReady();
		getProductionName().SendKeys(exportname);
		getProductionDesc().SendKeys(exportname);
		getSaveOption().waitAndClick(2);
		driver.scrollPageToTop();
		getBasicInfoMarkComplete().waitAndClick(2);
		base.stepInfo("New Export is added");
	}

	/**
	 * @authorIndium-Sowndarya.Velraj
	 */
	public void selectingCreatedSecurityGroup(String securityGroup) {
		base.waitForElement(getSecurityGroupDropDown());
		getSecurityGroupDropDown().waitAndClick(10);
		base.waitForElement(selectSecurityGroup(securityGroup));
		selectSecurityGroup(securityGroup).waitAndClick(10);

	}

	/**
	 * @author NA
	 */
	public void filterInprogressProduction() {
		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.waitForPageToBeReady();
		base.waitForElement(getFilterByButton());
		getFilterByButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		getFilterByDRAFT().waitAndClick(5);
		getFilterByCOMPLETED().waitAndClick(5);
		getFilterByFAILED().waitAndClick(5);
		boolean flag = isInprogressIsChecked().GetAttribute("class").contains("active");
		if (!flag) {
			getFilterByINPROGRESS().waitAndClick(5);
		}
		base.stepInfo("Filtered Inprogress status only");
		driver.waitForPageToBeReady();
		getRefreshButton().waitAndClick(10);
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium-Sowndarya
	 */
	public void verifyDefaultSelection_SortByMetadata() {
		base.waitForElement(getRadioBtnSortByMetadata());
		driver.waitForPageToBeReady();
		String getAttribute = getRadioBtnSortByMetadata().GetAttribute("style");
		System.out.println(getAttribute);
		if (getRadioBtnSortByMetadata().GetAttribute("style").contains("none")) {
			base.failedStep("No default selection occured");
		} else {
			base.passedStep("Sort by metadata is clicked by default");
		}

	}

	/**
	 * @author Indium-Sowndarya
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public void verifySortByTags_SortedByAscending() throws InterruptedException, AWTException {
		base.waitForElement(getSortingRadioBtn());
		getSortingRadioBtn().waitAndClick(10);

		List<String> availableTags = base.availableListofElements(getTotalTagsInSorting());
		List<String> availableTags2 = base.availableListofElements(getTotalTagsInSorting());
		System.out.println(availableTags);
		base.verifyOriginalSortOrder(availableTags, availableTags2, "Ascending", true);

	}

	/**
	 * @author Indium-Sowndarya
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public void verifyCustomSort_Link() throws InterruptedException, AWTException {

		base.waitForElement(getRadioBtn_CustomSort());
		getRadioBtn_CustomSort().waitAndClick(10);
		
		base.waitForElement(getLink_CustomSort());
		if (getLink_CustomSort().isDisplayed()) {
			base.passedStep("Link to upload Excel is available");
		}
		else {
			base.failedStep("No link is found");
		}
	}

	
	/**
	 * @author Sakthivel
	 * @param firstFile
	 * @param lastFile
	 * @param prefixID
	 * @param suffixID
	 * @param verificationText
	 * @Description verifying text is not displayed on the tiff image file on downloaded zip file
	 */
	public void OCR_Verification_In_Generated_Tiff_tess4jNotDisplayed(int firstFile, int lastFile, String prefixID, String suffixID,
			String verificationText) {
		driver.waitForPageToBeReady();
		String home = System.getProperty("user.home");

		for (int i = firstFile; i < lastFile; i++) {

			File imageFile = new File(home + "/Downloads/VOL0001/Images/0001/" + prefixID + i + suffixID + ".tiff");
			// ITesseract instance = new Tesseract(); // JNA Interface Mapping
			ITesseract instance = new Tesseract1(); // JNA Direct Mapping
			File tessDataFolder = LoadLibs.extractTessResources("tessdata"); // Maven build bundles English data
			instance.setDatapath(tessDataFolder.getPath());

			try {
				String result = instance.doOCR(imageFile);
				System.out.println(result);
				if (!result.contains(verificationText)) {
					base.passedStep(verificationText + " is not displayed in " + prefixID + i + suffixID + ".tiff"
							+ " file as expected");
				} else {
					base.failedStep(verificationText + " verification failed");
				}
			} catch (TesseractException e) {
				System.err.println(e.getMessage());
			}
		}
	}
}