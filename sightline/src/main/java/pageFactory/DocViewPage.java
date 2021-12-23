package pageFactory;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Robot;

import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;
import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import testScriptsSmoke.Input;

public class DocViewPage {

	Driver driver;
	BaseClass base;
	SessionSearch sp;
	SoftAssert softAssertion;
	Alert alert;
	SavedSearch savedSearch;
	AssignmentsPage assgnpage;
	ReusableDocViewPage reusableDocView;
	public static String codeSameDocumentid;
	List<String> docIDlist = new ArrayList<>();
	List<String> completedDoc = new ArrayList<>();
	List<String> stampList = new ArrayList<>();
	List<String> completeStampList = new ArrayList<>();

	public Element getDocView_info() {
		return driver.FindElementById("totalRecords");
	}

	public Element getAddComment1() {
		return driver.FindElementById("1_textarea");
	}

	public Element getSaveDoc() {
		return driver.FindElementById("Save");
	}

	public Element getNonAudioRemarkBtn() {
		return driver.FindElementById("remarks-btn");
	}

	public Element getSelectRemarkDocArea() {
		return driver
				.FindElementByXPath("//div[contains(@id,'pccViewerControl')]//*[name()='svg']//*[name()='text'][1]");
	}

	public Element getAddRemarkbtn() {
		return driver.FindElementById("addRemarks");
	}

	public Element getRemarkTextArea() {
		return driver.FindElementById("txt_remark");
	}

	public Element getRemarkDeleteIcon() {
		return driver.FindElementByXPath("//*[@id='remarksSaveCancelControls']//i[1]");
	}

	public Element getSaveRemark() {
		return driver.FindElementByXPath("(//span[@id='remarksSaveCancelControls']/i[2])[1]");
	}

	public Element getCompleteDocBtn() {
		return driver.FindElementById("btnDocumentComplete");
	}

	public ElementCollection getElements() {
		return driver.FindElementsByXPath("//*[@class='a-menu']");
	}

//Audio-----------------------------------------------------------
	public Element getDocView_IconFileType() {
		return driver.FindElementById("icofiletype");
	}

	public Element getDocView_TextFileType() {
		return driver.FindElementById("txtspanfileType");
	}

	public Element getDocView_IconPlay() {
		return driver.FindElementByCssSelector("*[title='Play']");
	}

	public Element getDocView_IconPause() {
		return driver.FindElementByCssSelector("*[title='Pause']");
	}

	public Element getDocView_RunningTime() {
		return driver.FindElementByCssSelector(".jp-current-time:nth-of-type(2)");
	}

	public Element getDocView_IconStop() {
		return driver.FindElementByCssSelector("*[title='Stop']");
	}

	public Element getDocView_IconPrev() {
		return driver.FindElementByCssSelector("#btnPreviousHit i");
	}

	public Element getDocView_IconNext() {
		return driver.FindElementByCssSelector("#btnNextHit i");
	}

	public Element getDocView_IconBack() {
		return driver.FindElementByCssSelector("#btnBackword i");
	}

	public Element getDocView_IconForward() {
		return driver.FindElementByCssSelector("#btnForword i");
	}

	public Element getDocview_RedactionsTab() {
		return driver.FindElementByXPath("//a[contains(.,'REDACTIONS')]");
	}

	public Element getDocview_RedactionsTab_Add() {
		return driver.FindElementByXPath(".//*[@id='new']//i");
	}

	public Element getDocview_AddRedactions_StartTime() {
		return driver.FindElementByXPath("//*[@id='audioGrid']//td[1]//input");
	}

	public Element getDocview_AddRedactions_EndTime() {
		return driver.FindElementByXPath("//*[@id='audioGrid']//td[2]//input");
	}

	public Element getDocview_Audio_StartTime() {
		return driver.FindElementByXPath("//*[@class='jp-current-time start']");
	}

	public Element getDocview_Audio_EndTime() {
		return driver.FindElementByXPath("//*[@class='jp-duration end']");
	}

	public Element getDocview_Redactionstags_Value() {
		return driver.FindElementByXPath("//ul[@class='multiselect-container dropdown-menu']/li[1]//input");
	}

	public Element getDocview_AudioRedactions() {
		return driver.FindElementById("ddlAudioRedactionTags");
	}

	public Element getDocview_Redactionstags_Delete() {
		return driver.FindElementById("btnDelete");
	}

	public Element getDocview_Audio_Downloadbutton() {
		return driver.FindElementById("liAudioDocumentTypeDropDown");
	}

	public Element getDocview_Audio_DownloadFile() {
		return driver.FindElementByXPath("//a[contains(text(),'MP3')]");
	}

	public Element getDocview_Keywork_Next() {
		return driver.FindElementById("NextHit_you");
	}

	public Element getDocview_Keywork_Previous() {
		return driver.FindElementById("PrevHit_you");
	}

	public Element getDocview_ButtonNO() {
		return driver.FindElementById("bot2-Msg1");
	}

	public Element getDocview_ButtonYes() {
		return driver.FindElementById("bot1-Msg1");
	}

	public Element getSaveButton() {
		return driver.FindElementById("btnSave");
	}

	// non audio reduction page
	public Element getDocView_RedactThisPage() {
		return driver.FindElementByXPath("//*[@id='redactCurrentPage_divDocViewer']");
	}

	public Element getDocView_SelectReductionLabel() {
		return driver.FindElementById("ddlRedactionTagsForPopup");
	}

	public Element getDocView_SaveReduction1(int i) {
		return driver.FindElementByXPath("(//div[@class='ui-dialog-buttonset']//button[1])[" + i + "]");
	}

	public Element getDocView_SaveReduction() {
		return driver.FindElementByXPath("//div[@class='ui-dialog-buttonset']//button[1]");
	}

	public Element getDocView_SaveReductionNew() {
		return driver.FindElementByXPath("//*[@id='btnSave']");
	}

	// remaks objects
	public Element getAdvancedSearchAudioRemarkIcon() {
		return driver.FindElementByXPath("//*[@id='remarks-btn-audio-view']/a/span/i[2]");
	}

	public Element getAdvancedSearchAudioRemarkPlusIcon() {
		return driver.FindElementByXPath(".//*[@id='audAddRemark']/i");
	}

	public Element getAdvancedSearchAudioRemarkTime() {
		return driver.FindElementById("txtRemarkTime");
	}

	public Element getDocView_RemarkTextField() {
		return driver.FindElementById("txt_remark");
	}

	public Element getDocView_Remark_RemarkPlusIcon() {
		return driver.FindElementById("addRemarks");
	}

	public Element getDocView_Remark_CheckIcon() {
		return driver.FindElementByXPath(".//*[@id='remarksSaveCancelControls']/i[2]");
	}

	public Element getDocView_Remark_DeleteIcon() {
		return driver.FindElementByXPath("//*[@id='newRemarks']//i[@class='fa fa-trash-o']");
	}

	public Element getDocView_SavedRemarkText() {
		return driver.FindElementByXPath(".//*[@id='newRemarks']//p");
	}

	public Element getDocViewTest_1stElement() {
		return driver.FindElementByXPath(".//*[@id='SearchDataTable']/tbody/tr[1]/td[2]");
	}

	public Element getDocView_AddRemarkIcon() {
		return driver.FindElementByXPath(".//*[@id='remarks-btn']/a");
	}

	public Element getDocView_AudioRemark_DeleteButton() {
		return driver.FindElementByXPath(".//*[starts-with(@onclick,'DeleteReviewerRemarks')]");
	}

	public Element getDocView_AudioRemark_SaveButton() {
		return driver.FindElementByXPath(".//*[@onclick='AddReviewerRemarks()']");
	}

	public Element getAudioComment() {
		return driver.FindElementByXPath("//textarea[@name='COMMENT' and @id='1_textarea']");
	}

	public Element getDocumentViewer_DocView_SaveBtn() {
		return driver.FindElementById("Save");
	}
	// Persistent Hits
	// Validation--------------------------------------------------------------

	public ElementCollection getHitPanels() {
		return driver.FindElementsByXPath("//div[starts-with(@class,'search-tab col-md-2 clsAccusoftViewerHit')]/div");
	}

	public Element getTermInHitPanels(int num) {
		return driver.FindElementByXPath(
				"//div[starts-with(@class,'search-tab col-md-2 clsAccusoftViewerHit')]/div[" + num + "]/p");
	}

	public Element getDocView_CFName() {
		return driver.FindElementById("lblCodingFormName");
	}

	// added on 04-01
	public Element getDocView_TextTab() {
		return driver.FindElementById("liDocumentTxtView");
	}

	public Element getDocView_Textarea() {
		return driver.FindElementById("divViewerText");
	}

	public Element getDocView_Mini_ActionButton() {
		return driver.FindElementById("btnAction");

	}

	public Element getDocView_Mini_FolderAction() {
		return driver.FindElementById("FolderMINIDOCLIST");
	}

	public Element getDocView_FolderTab() {
		return driver.FindElementById("liDocumentFolder");
	}

	public Element getDocView_MiniDoc_SelectRow(int rowno) {
		return driver.FindElementByXPath(
				".//*[@id='SearchDataTable']/tbody/tr[" + rowno + "]/td[1]//input/following-sibling::i");
	}

	public Element getDocView_FolderTab_Expand() {
		return driver.FindElementByXPath(".//*[@id='-1']/i");
	}

	public Element getFolderFromList(String Foldername) {
		return driver.FindElementByXPath(".//*[@class='jstree-children']//a[text()='" + Foldername + "']");
	}

	public Element getDocView_NumTextBox() {
		return driver.FindElementById("txtBoxPageNum");
	}

	public Element getDocView_MiniDocListIds(int rowno) {
		return driver.FindElementByXPath(".//*[@id='SearchDataTable']/tbody/tr[" + rowno + "]");
	}

	public Element getDocView_Persistent_PrevHit() {
		return driver.FindElementByXPath("//*[starts-with(@id,'PrevHit')]");
	}

	public Element getDocView_Persistent_NextHit() {
		return driver.FindElementByXPath("//*[starts-with(@id,'NextHit')]");
	}

	public Element getDocView_Annotate_Rectangle() {
		return driver.FindElementById("highlightRectangle_divDocViewer");
	}

	public Element getDocView_Annotate_ThisPage() {
		return driver.FindElementById("highlightCurrentPage_divDocViewer");
	}

	public Element getRemarkDeletetIcon() {
		return driver.FindElementByXPath("//*[@id='remarksSaveCancelControls']/i[@class='fa fa-trash-o']");
	}

	public Element getRemarkEditIcon() {
		return driver.FindElementByXPath("//*[@id='remarksSaveCancelControls']/i[@class='fa fa-pencil']");
	}

	public Element getDocView_Redact_ThisPage() {
		return driver.FindElementByXPath(".//*[@id='redactCurrentPage_divDocViewer']/a/i");
	}

	public Element getDocView_Annotate_TextArea() {
		return driver.FindElementByCssSelector("rect[style*='#FFFF00']");
	}

	public Element getDocView_Annotate_DeleteIcon() {
		return driver.FindElementById("btn_delete");
	}

	public Element getDocView_Redact_TextArea() {
		return driver.FindElementById("divDocViewer");
	}

	public Element getDocView_EditMode() {
		return driver.FindElementById("wEdit");
	}

	public Element getDocView_HdrMinDoc() {
		return driver.FindElementByXPath(".//*[@id='HdrMiniDoc']/div/div/i");
	}

	public Element getDocView_HdrCoddingForm() {
		return driver.FindElementByXPath(".//*[@id='HdrCoddingForm']/div/div/i");
	}

	public Element getDocView_HdrMetaData() {
		return driver.FindElementByXPath(".//*[@id='HdrMetaData']/div/div/i");
	}

	public Element getDocView_HdrAnalytics() {
		return driver.FindElementByXPath(".//*[@id='HdrAnalytics']/div/div/i");
	}

	// All Tabs-----------------------------------------------------------
	public Element getDocView_DefaultViewTab() {
		return driver.FindElementById("aliDocumentDefaultView");
	}

	public Element getDocView_ImagesTab() {
		return driver.FindElementById("liDocumentProductionView");
	}

	public Element getDocView_TranslationTab() {
		return driver.FindElementById("liDocumentTranslationsView");
	}

	public Element getDocView_MiniDoclistPanel() {
		return driver.FindElementById("miniDocListWrap");
	}

	public Element getDocView_AnalyticsPanel() {
		return driver.FindElementById("leftPalette_AnalyticsPanel");
	}

	public Element getDocView_CodingFormPanel() {
		return driver.FindElementById("divParentCodingForm");
	}

	public Element getDocView_MetadataPanel() {
		return driver.FindElementById("rightPalette_MetaData");
	}

	public Element getDocView_HitsTogglePanel() {
		return driver.FindElementByXPath("//*[@class='toggletText light-bg']");
	}

	public Element getDocView_ToogleLabel() {
		return driver.FindElementByXPath("//*[@class='toggletText light-bg']//label/label");
	}

	public Element getDocView_ToggleButton() {
		return driver.FindElementById("EnableSearchTerm");
	}

	public Element getDocView_DefaultTabNative() {
		return driver.FindElementByXPath(".//*[@id='aliDocumentDefaultView']/span[contains(.,'NATIVE')]");
	}

	public Element getDocView_Analytics_ChildWindow_FamilyTab_Firstdoc() {
		return driver.FindElementByXPath(
				"//*[@id='dtDocumentFamilyMembers']//tr[1]//input[starts-with(@id,'chkDocFamily')]/following-sibling::i");
	}

	public Element geDocView_MiniList_CodeSameAsIcon() {
		return driver.FindElementByXPath(".//*[@id='SearchDataTable']//i[@class='fa fa-link']");
	}

	public Element geDocView_FamilyMem_CodeSameAsIcon() {
		return driver.FindElementByXPath(".//*[@id='dtDocumentFamilyMembers']//i[@class='fa fa-link']");
	}

	public Element geDocView_Threaded_CodeSameAsIcon() {
		return driver.FindElementByXPath(".//*[@id='dtDocumentThreadedDocuments']//i[@class='fa fa-link ']");
	}

	public Element geDocView_NearDupe_CodeSameAsIcon() {
		return driver.FindElementByXPath(".//*[@id='dtDocumentNearDuplicates']//i[@class='fa fa-link']");
	}

	public Element geDocView_Concept_CodeSameAsIcon() {
		return driver.FindElementByXPath(".//*[@id='dtDocumentConceptuallySimilar']//i[@class='fa fa-link']");
	}

	public Element getDocView_ChildWindow_ActionButton() {
		return driver.FindElementById("btnAnalyticAction");
	}

	public Element getDocView_Analytics_liDocumentThreadMap() {
		return driver.FindElementById("liDocumentThreadedMap");
	}

	public Element getDocView_Analytics_NearDupeTab() {
		return driver.FindElementById("liDocumentNearDupe");
	}

	public Element getDocView_Analytics_liDocumentConceptualSimilarab() {
		return driver.FindElementById("liDocumentConceptualSimilar");
	}

	public Element getDocView_Analytics_FamilyTab() {
		return driver.FindElementById("liDocumentFamilyMember");
	}

	public Element getDocView_MiniDoc_ChildWindow_Selectdoc(int rowno) {
		return driver.FindElementByXPath("//*[@id='SearchDataTable']//tr[" + rowno + "]/td[1]/label");
	}

	public Element getDocView__ChildWindow_Mini_FolderAction() {
		return driver.FindElementById("liMiniDocListBulkFolder");
	}

	public Element getDocView__ChildWindow_Mini_CodeAsSameAction() {
		return driver.FindElementById("ACodeSame");
	}

	public Element getDocView_Analytics_Thread_ViewDocument() {
		return driver.FindElementById("liViewDocumentTHREADEDDOCUMENTS");
	}

	public Element getDocView_Analytics_Thread_Folder() {
		return driver.FindElementById("FolderAnalyticAction");
	}

	public Element getDocView_Analytics_Thread_CodeSameAs() {
		return driver.FindElementById("liCodeSameAsThreaded");
	}

	public Element getDocView_Analytics_Thread_RemoveCodeSameAs() {
		return driver.FindElementById("liRemoveCodeSameAsThreaded");
	}

	public Element getDocView_Analytics_Thread_ViewDoclist() {
		return driver.FindElementById("DocListThreaded");
	}

	public Element getDocView__ChildWindow_Mini_CodeSameAs() {
		return driver.FindElementById("liCodeSameAsMiniDocList");
	}

	public Element getDocView__ChildWindow_Mini_RemoveCodeSameAs() {
		return driver.FindElementById("liRemoveCodeSameAsMiniDocList");
	}

	public Element getDocView_MiniDocList() {
		return driver.FindElementById("divMiniDocList");
	}

	public Element getDocView_FolderActionPopup() {
		return driver.FindElementById("divBulkAction");
	}

	public Element getDocView_ThreadedChild_Selectalldoc() {
		return driver.FindElementById("threadMapSelectRows");
	}

	public Element getDocView_NearDupeIcon() {
		return driver.FindElementById("iconNearDupe");
	}

	public Element getDocView_NearDupe_DocID() {
		return driver.FindElementById("spanOriginalDocId");
	}

	public Element getDocView_DocumentViewer_DocID() {
		return driver.FindElementByCssSelector("#SearchDataTable tbody tr[class*='rowNumber_0'] td:nth-of-type(2)");
	}

	public Element getDocView_Analytics_LoadMoreButton() {
		return driver.FindElementById("LoadMore");
	}

	public Element getDocView_Analytics_ThreadMap_Doc() {
		return driver.FindElementByXPath("//*[@id='157']//label//i");
	}

	public Element getDocView_Analytics_Threaded() {
		return driver.FindElementByXPath(".//*[@id='theThreadMapUI']//*[@class='threadedEmailRowNoRecords']");
	}

	public Element getDocView_Analytics_Threaded_FirstDoc() {
		return driver.FindElementByXPath(
				"//*[@id='threadedCheckBoxRow']//th[2]//input[contains(@id,'threadedDocumentCheckboxHeader')]/following-sibling::i");
	}

	public Element getDocView_MiniDoc_CodeSameIcon() {
		return driver
				.FindElementByXPath(".//*[@id='SearchDataTable']/tbody/tr[1]/td[1]//i[contains(@class,'fa fa-link')]");
	}

	public Element getDocView_HistoryButton() {
		return driver.FindElementById("btnDocHistory");
	}

	public Element getDocView_Historydropdown() {
		return driver.FindElementByXPath("//*[@id='ulDocViewHistory']/li[2]");
	}

	public Element getDocView_Analytics_Threadedicon() {
		return driver.FindElementByXPath("//*[@id='threadedDocumentIdRow']//th[@class='thread_current gray-bg-1 ']");
	}

	public Element getDocView_Metadata_EmailInclusiveScore() {
		return driver.FindElementByXPath(
				"//*[@id='MetaDataDT']//td[contains(text(),'EmailInclusiveScore')]/following-sibling::td");
	}

	public Element getDocView_Metadata_EmailInclusiveReason() {
		return driver.FindElementByXPath(
				"//*[@id='MetaDataDT']//td[contains(text(),'EmailInclusiveReason')]/following-sibling::td");
	}

	// All icons
	public Element getPersistantHitEyeIcon() {
		return driver.FindElementByXPath("//*[@id='search-btn']//a");
	}

	public Element getDocView_RedactIcon() {
		return driver.FindElementByXPath("//*[@id='gray-tab']//a");
	}

	public Element getDocView_AnnotateIcon() {
		return driver.FindElementByXPath("//*[@id='yellow-tab']//a");
	}

	public Element getDocView_IconDownload() {
		return driver.FindElementByXPath("//*[@id='liDocumentTypeDropDown']//a");
	}

	public Element getDocView_Zoomout() {
		return driver.FindElementByXPath("//*[@id='zoomOut_divDocViewer']//a");
	}

	public Element getDocView_Slider() {
		return driver.FindElementById("slider_divDocViewer");
	}

	public Element getDocView_ZoomIn() {
		return driver.FindElementByXPath("//*[@id='zoomIn_divDocViewer']//a");
	}

	public Element getDocView_Rotateright() {
		return driver.FindElementByXPath("//*[@id='rotateRight_divDocViewer']//a");
	}

	public Element getDocView_Rotateleft() {
		return driver.FindElementByXPath("//*[@id='rotateLeft_divDocViewer']//a");
	}

	public Element getDocView_Print() {
		return driver.FindElementByXPath("//*[@id='print_divDocViewer']//a");
	}

	public Element getDocView_Thumbnail() {
		return driver.FindElementByXPath("//*[@id='thumbnails_divDocViewer']//a");
	}

	public Element getDocView_ResetZoom() {
		return driver.FindElementByXPath("//*[@id='fitContent_divDocViewer']//a");
	}

	public Element getDocView_SearchButton() {
		return driver.FindElementByXPath("//*[@class='sodIcon']//a");
	}

	// Added on 28 May*********Analytics - Family************
	public Element getDocView_FamilyBulkFolder() {
		return driver.FindElementById("liFamilyBulkFolder");
	}

	public Element getDocView_FamilyCodeSameAs() {
		return driver.FindElementById("liCodeSameAsFamilyMember");
	}

	public Element getDocView_FamilyRemoveCodeSameAs() {
		return driver.FindElementById("liRemoveCodeSameAsFamilyMember");
	}

	public Element getDocView_FamilyViewInDoclist() {
		return driver.FindElementById("liViewInDocListFamilyMember");
	}

	public Element getDocView_FamilyViewInDocView() {
		return driver.FindElementById("liViewDocumentFamilyMember");
	}

	// Sort Order
	public Element getDocView_ReviewModeText() {
		return driver.FindElementById("lblModeText");
	}

	public Element getDocView_ConfigMinidoclist() {
		return driver.FindElementById("miniDocListConfig");
	}

	public Element getDocView_ConfigMinidoclist_ManualSort() {
		return driver.FindElementById("lblBtnRadioManual");
	}

	public Element getDocView_ConfigMinidoclist_OptimizedSort() {
		return driver.FindElementByXPath("//*[@id='rbOptimized']/following-sibling::i");
	}

	public ElementCollection getDocView_Config_Selectedfield() {
		return driver.FindElementsByXPath("//*[@id='sortable2PickColumns']/li");
	}

	public Element getDocView_SelectedFileds(int rowno) {
		return driver.FindElementByXPath(
				"//*[@id='SearchDataTable_wrapper']/div[3]/div[1]//table/thead/tr/th[" + rowno + "]");
	}

	public Element getDocView_MiniDocFields_Remove() {
		return driver.FindElementByXPath(".//*[@id='sortable2PickColumns']/li[contains(.,'FamilyMemberCount')]/i[2]");
	}

	public Element getDocView_MiniDocFields_ConfigSaveButton() {
		return driver.FindElementByXPath("//div[@class='ui-dialog-buttonset']//button[2]");
	}

	public ElementCollection getDocView_AssignmentProgress() {
		return driver.FindElementsByXPath(".//*[@id='divAssigmnetProgress']/div[@class='progress progress-micro']");
	}

	public Element getDocView_SaveWidgetButton() {
		return driver.FindElementById("wSave");
	}

	public Element getDocView_Analytics_Collapsebutton() {
		return driver.FindElementByXPath("//*[@class='fa fa-minus']");
	}

	public Element getDocView_NearDupe_Selectdoc() {
		return driver
				.FindElementByXPath(".//*[@id='dtDocumentNearDuplicates']/tbody//td[1]//input/following-sibling::i");
	}

	public Element getDocView_NearViewInDoclist() {
		return driver.FindElementById("liViewInDocListNearDupe");
	}

	public ElementCollection getDocView_ConceptualDocs() {
		return driver.FindElementsByXPath(".//*[@id='dtDocumentConceptuallySimilar']//tr");
	}

	// Added on 29 may****Navigation***********
	public Element getDocView_First() {
		return driver.FindElementById("btnFirst");
	}

	public Element getDocView_Previous() {
		return driver.FindElementById("btnPrevDocView");
	}

	public Element getDocView_Last() {
		return driver.FindElementById("btnLast");
	}

	public Element getDocView_Next() {
		return driver.FindElementById("btnNextDocView");
	}

	// Added by sure 04/09/19
	public Element getDocView_defaultView() {
		return driver.FindElementByXPath("//span[contains(text(),'Default')]");
	}

	public Element getDocView_textView() {
		return driver.FindElementByXPath("(//span[contains(text(),'TEXT')])[2]");
	}

	public Element getDocView_imagesView() {
		return driver.FindElementByXPath("//span[contains(text(),'Images')]");
	}

	public Element getDocView_translationsView() {
		return driver.FindElementByXPath("//span[contains(text(),'Translations')]");
	}

	public Element getDocView_CurrentDocId() {
		return driver.FindElementById("activeDocumentId");
	}

	public Element getDocView_textArea() {
		return driver
				.FindElementByXPath("//div[contains(@id,'pccViewerControl')]//*[name()='svg']//*[name()='text'][1]");
	}

	public Element getDocView_Redact_Rectangle() {
		return driver.FindElementById("blackRectRedact_divDocViewer");
	}

	public WebElement getDocView_Redactrec_textarea() {
		return driver.FindElementById("ig0level5").getWebElement();
	}

	public Element getDocView_Redactedit_save() {
		return driver.FindElementById("btnRedactionTag");
	}

	public Element getDocView_Redactedit_selectlabel() {
		return driver.FindElementById("ddlRedactionTags");
	}

	public Element getDocView_DocId(String docid) {
		return driver.FindElementByXPath("//*[@id='SearchDataTable']//td[contains(text(),'" + docid + "')]");
	}

	public Element getAudioPersistantHitEyeIcon() {
		return driver.FindElementByXPath("//*[@id='search-btn-audio-view']//a");
	}

	public Element getDocView_Audio_Hit() {
		return driver.FindElementByXPath("//*[@id='divAudioPersistentSearch']/div/p[1]");
	}

	// Doc view page redaction
	public Element getPreRedaction() {
		return driver.FindElementByCssSelector("#PrevAllRedaction");
	}

	// Doc View Coding Form
	public Element getResponsiveCheked() {
		return driver
				.FindElementByXPath("//div[@id='item1']//div[@id='0_radiogroup']//div[1]//div[1]//label[1]//span[1]");
	}

	public Element getNonPrivilegeRadio() {
		return driver.FindElementByXPath("//input[@id='9_radio']//parent::label//span");
	}

	public Element getConfidentialRadio() {
		return driver
				.FindElementByXPath("//div[@id='item17']//div[@id='0_radiogroup']//div[1]//div[1]//label[1]//span[1]");
	}

	public Element getDocument_CommentsTextBox() {
		return driver.FindElementByXPath("//textarea[@id='1_textarea']");
	}

	public Element getReadOnlyTextBox(String projectFieldName) {
		return driver.FindElementByXPath("//input[@name='FIELD'][@projectfieldname='" + projectFieldName + "']");
	}

	public Element getCodingFormSaveButton() {
		return driver.FindElementByXPath("//div[@id='divCodingFormSaveComplete']//child::a[@id='Save']");
	}

	public Element getCodingFormStampButton() {
		return driver.FindElementById("SaveUserStamps");
	}

	public Element getCodingStampTextBox() {
		return driver.FindElementById("txtStampName");
	}

	public Element getCodingEditStampTextBox() {
		return driver.FindElementByXPath("//input[@id='txtStampNameEdit']");
	}

	public Element getDrp_CodingEditStampColour() {
		return driver.FindElementByXPath("//div//dl[@id='ddlEditStamps']");
	}

	public Element getDrp_StampColour() {
		return driver.FindElementById("stampSelect");
	}

	public Element getCodingStampSaveBtn() {
		return driver.FindElementByXPath("//div[@class='ui-dialog-buttonset']//button[text()='Save']");
	}

	public Element getDocview_GearButton() {
		return driver.FindElementByXPath("//i[@class='fa fa-gear font-xl']");
	}

	public Element getAssignedColour(String colour) {
		return driver.FindElementByXPath("//dl[@id='stampSelect']//ul//li[@id='" + colour + "']");
	}

	public Element getEditAssignedColour(String editColour) {
		return driver.FindElementByXPath("//dl[@id='ddlEditStamps']//ul//li[@id='" + editColour + "']");
	}

	public Element getCodingStampLastIcon(String icon) {
		return driver.FindElementByXPath("//ul[@id='UserStamps']//li[@id='" + icon + "']");
	}

	public Element getCodingLastIcon() {
		return driver.FindElementByXPath("//div[@id='divCodingStamp']//ul//li");
	}

	public Element getverifyCodeSameAsLast() {
		return driver.FindElementByXPath("//table[@id='SearchDataTable']//i[@class='fa fa-check-circle']");
	}

	public Element getEditStampSettings() {
		return driver.FindElementByXPath("//div[@id='divUserStamps']//a[@id='stampSettings']//i");
	}

	public Element getClickDocviewID(int row) {
		return driver.FindElementByXPath("//*[@id='SearchDataTable']//tr[" + row + "]/td[2]");
	}

	public Element getCodingFormSaveBtn() {
		return driver.FindElementByXPath("//div[@id='divCodingFormSaveComplete']//a[@id='Save']");
	}

	public Element getCodeSameAsLast() {
		return driver.FindElementByXPath("//div[@id='lastCodingStamp']//div//ul//li//i");
	}

	// Added on 23/07/21 ******Doc view Analytics Panel****** by Indium Mohan

	public Element getAdvanceSearchButton() {
		return driver.FindElementByXPath("//a[text()='Switch to Advanced']");
	}

	public Element getWorkProductButton() {
		return driver.FindElementByXPath("//button[@id='workproduct']");
	}

	public Element getFolderButton() {
		return driver.FindElementByXPath("//button[@id='foldersHelper']");
	}

	public Element getFolderSelection() {
		return driver.FindElementByXPath("//ul[@class='jstree-children']//a[text()='01Prod']");
	}

	public Element getInsertQueryButton() {
		return driver.FindElementByXPath("//div[@class='col-md-12 text-right']//a[@class='btn btn-primary']");
	}

	public Element getSearchButon() {
		return driver.FindElementById("qSearch");
	}

	// modified by Mohan - 8/24/21
	public Element getPureHitsCount() {
		return driver.FindElementByXPath(".//*[@id='001']//i[contains(@class,'addTile')]");
	}

	public Element getFamilyMemberPureHitCount() {
		return driver.FindElementByXPath(".//*[@id='004']/i[2]");
	}

	public Element getActionButton() {
		return driver.FindElementByXPath("//button[@id='idAction']");
	}

	public Element getDocViewAction() {
		return driver.FindElementByXPath("//*[@id='ddlbulkactions']//a[text()='View In DocView']");
	}

	public Element getGearIcon() {
		return driver.FindElementById("wEdit");
	}

	public Element getDocFromTable() {
		return driver.FindElementByXPath(
				"//tr[@class='dtDocumentConceptuallySimilarRowNumber_104 odd']//label[@class='checkbox']");
	}

	public Element getDocTableAfterHitEnable() {
		return driver.FindElementByXPath(
				"//tr[@class='dtDocumentConceptuallySimilarRowNumber_102 odd']//label[@class='checkbox']");
	}

	public Element getAnalyticalDropDown() {
		return driver.FindElementByXPath(
				"//ul[@class='dropdown-menu dropdown-menu-right clsddlActions']//a[contains(text(),'View Document')]");
	}

	public Element getDocView_ChildWindowPopOut() {
		return driver.FindElementByXPath(
				"//*[@class='ui-sortable-handle']//*[@class='button-icon jarviswidget-pop-btn']//i[@class='fa fa-expand']");
	}

	public Element getDocView_SearchText() {
		return driver.FindElementById("contentmetadata");
	}

	public Element getDocView_DocumentThreadMap() {
		return driver.FindElementByXPath("(//*[@id='threadedEmailRow']//tr//preceding-sibling::td)[1]");
	}

	public Element getDocView_Document() {
		return driver.FindElementByClassName("doc-id");
	}

	public Element getDocView_DocName() {
		return driver.FindElementByXPath("//tr[@id='threadedDocumentIdRow']//th[contains(text(),'157')]");
	}

	public Element getSelectAssignmentFromDashborad(String assignmentName) {
		return driver.FindElementByXPath(
				"//*[@id='dt_basic']//following-sibling::tbody//following-sibling::tr//strong[text()='" + assignmentName
						+ "']");
	}

	public Element getDashboardButton() {
		return driver.FindElementByXPath("//label[text()='Dashboard']");
	}

	public Element getAlertConfrimButton() {
		return driver.FindElementById("btnYes");
	}

	public Element getMiniDocId(String text) {
		return driver.FindElementByXPath("//table[@id='SearchDataTable']//td[text()='" + text + "']");
	}

	public Element getDocumentConceptuallySimilar(int rowNo) {
		return driver.FindElementByXPath("//*[@id='dtDocumentConceptuallySimilar']//tr[1]/td[" + rowNo + "]/label");
	}

	public Element getDocView_Analytics_ThreadMap_Doc(int rowno) {
		return driver.FindElementByXPath(
				"//*[@id='analyticsResize']//tr[@id='threadedCheckBoxRow']//th[" + rowno + "]//label");
	}

	public Element getNearDupePureHitsCount() {
		return driver.FindElementByXPath("//*[@id='003']//i[contains(@class,'addTile')]");
	}

	public Element getApplyCodingNearDedupeBtn() {
		return driver.FindElementById("nearDupeCodeSameAs");
	}

	public ElementCollection getDocView_MiniListDocuments() {
		return driver.FindElementsByXPath("//div[@id='divMiniDocList']//tbody//tr");
	}

	public Element getOriginalDocId() {
		return driver.FindElementById("spanOriginalDocId");
	}

	public Element getNearDupeDocId() {
		return driver.FindElementById("spanNearDupeDocId");
	}

	public Element getViewInDocListAnalyticalDropDown() {
		return driver.FindElementByXPath(
				"//ul[@class='dropdown-menu dropdown-menu-right clsddlActions']//a[contains(text(),'View in DocList')]");
	}

	public ElementCollection getDocView_NearDupeIconForReviewer() {
		return driver.FindElementsById("iconNearDupe");
	}

	public Element getThreadMapPureHitsCount() {
		return driver.FindElementByXPath("//*[@id='002']//i[contains(@class,'addTile')]");
	}

	public Element getMetaDataDocId(String text) {
		return driver.FindElementByXPath("//*[@id='MetaDataDT']//td[text()='" + text + "']");
	}

	public Element getDocView_AnalyticsDocId(String documentToBeScrolled) {
		return driver.FindElementByXPath(
				"//tr[@id='threadedDocumentIdRow']//th[contains(text(),'" + documentToBeScrolled + "')]");
	}

	public Element getDocView_MiniDoc_SelectdocAsText(int rowno, int column) {
		return driver.FindElementByXPath(
				"//*[@id='SearchDataTable']//following-sibling::tbody//tr[" + rowno + "]//td[" + column + "]");
	}

	public Element getDocView_Analytics_NearDupe_Text(int rowno) {
		return driver
				.FindElementByXPath("//*[@id='analyticsResize']//*[@id='dtDocumentNearDuplicates']//td[" + rowno + "]");
	}

	public Element getDocView_Analytics_NearDupe_Doc(int rowno) {
		return driver.FindElementByXPath("//*[@id='analyticsResize']//*[@id='dtDocumentNearDuplicates']//tr[" + rowno
				+ "]//input//following-sibling::i");
	}

	public Element getDocView_MetaDataIcon() {
		return driver.FindElementById("liDocumentMetadata");
	}

	public Element getDocView_MetaDataTotalFields() {
		return driver.FindElementByXPath("//*[@id='MetaDataDT']");
	}

	public Element getDocView_NearDupeCodeSameAs() {
		return driver.FindElementById("liCodeSameAsNearDupe");
	}

	public Element getConceptuallyPlayButton() {
		return driver.FindElementByXPath("//*[contains(@id,'playButton')]");
	}

	public Element getDocView_Analytics_Conceptual_FirstDoc() {
		return driver.FindElementByXPath("//table[@id='dtDocumentConceptuallySimilar']//tbody/tr[1]//label");
	}

	public Element getConceptDocumentWhichHasCodeSameIcon() {
		return driver.FindElementByXPath(
				"//tr[contains(@class,'dtDocumentConceptuallySimilar')]//i[@class='fa fa-link']/..//following-sibling::td[1]");
	}

	public Element getCodeSameIconMiniDocList() {
		return driver.FindElementByXPath(
				"//table[@id='SearchDataTable']//i[@class='fa fa-link']/..//following-sibling::td[text()='"
						+ codeSameDocumentid + "']");
	}

	public Element getCodeSameIconOtherTab() {
		return driver.FindElementByXPath(
				"//div[@id='AnalyticsTab']//i[@class='fa fa-link']/..//following-sibling::td[text()='"
						+ codeSameDocumentid + "']");
	}

	public Element getCodeCompleteIconMiniDocList() {
		return driver.FindElementByXPath(
				"//table[@id='SearchDataTable']//i[@class='fa fa-check-circle']/..//following-sibling::td[text()='"
						+ codeSameDocumentid + "']");
	}

	public Element getCodeCompleteIconOtherTab() {
		return driver.FindElementByXPath(
				"//div[@id='AnalyticsTab']//i[@class='fa fa-check-circle']/..//following-sibling::td[text()='"
						+ codeSameDocumentid + "']");
	}

	public Element geDocView_ConceptuallySimilar_CodeSameAsIcon() {
		return driver.FindElementByXPath(".//*[@id='dtDocumentConceptuallySimilar']//i[@class='fa fa-link']");
	}

	public Element getDocView_CodingFormPopOut() {
		return driver.FindElementByXPath(
				"//*[@id='HdrCoddingForm']//*[@class='button-icon jarviswidget-pop-btn']//i[@class='fa fa-expand']");
	}

	public Element getDocView_MiniDocListExpand() {

		return driver.FindElementByXPath(

				"//*[@id='HdrMiniDoc']//*[@class='button-icon jarviswidget-pop-btn']//following-sibling::a//i[@class='fa fa-plus']");

	}

	public Element getDocView_AnalyticalPanelExpand() {

		return driver.FindElementByXPath(

				"//*[@id='HdrAnalytics']//*[@class='button-icon jarviswidget-pop-btn']//following-sibling::a//i[@class='fa fa-plus']");

	}

	public Element getViewInDocList() {

		return driver.FindElementByXPath("//a[text()='View All In DocList']");

	}

	public Element getSaveIcon() {

		return driver.FindElementByXPath("//a[@id='wSave']");

	}

	public Element getDocView_MiniDocListPopOut() {
		return driver.FindElementByXPath(
				"//*[@id='HdrMiniDoc']//*[@class='button-icon jarviswidget-pop-btn']//i[@class='fa fa-expand']");
	}

	public Element getDocView_Analytics_Concept_Similar_CodeSameAs() {
		return driver.FindElementById("liCodeSameAsConceptualSimilar");
	}

	public Element getDocView_MiniDoclist_SourceDocIdText(String sourceId) {
		return driver.FindElementByXPath("//*[@id='SearchDataTable']//td[text()='" + sourceId + "']");
	}

	public Element getNearDocumentWhichHasCodeSameIcon() {
		return driver.FindElementByXPath(
				"//tr[contains(@class,'dtDocumentNearDuplicates')]//i[@class='fa fa-link']/..//following-sibling::td[1]");
	}

	public Element getCodeCompleteIconNearDupeTab() {
		return driver.FindElementByXPath(
				"//table[@id='dtDocumentNearDuplicates']//i[@class='fa fa-check-circle']/..//following-sibling::td[contains(normalize-space(.),'"
						+ codeSameDocumentid + "')]");
	}

	public Element getCodeCompleteIconThreadTab() {
		return driver.FindElementByXPath(
				"//table[contains(@id,'dtDocumentThreadedDocuments')]//thead//tr//th//i[contains(@class,'fa fa-check-circle')]//ancestor::table//thead//tr[@id='threadedDocumentIdRow']//th[contains(text(),'"
						+ codeSameDocumentid + "')]");
	}

	public Element getCodeCompleteIconFamilyTab() {
		return driver.FindElementByXPath(
				"//table[@id='dtDocumentFamilyMembers']//i[@class='fa fa-check-circle']/..//following-sibling::td[contains(normalize-space(.),'"
						+ codeSameDocumentid + "')]");
	}

	public Element getCodeCompleteIconConceptTab() {
		return driver.FindElementByXPath(
				"//table[@id='dtDocumentConceptuallySimilar']//i[@class='fa fa-check-circle']/..//following-sibling::td[contains(normalize-space(.),'"
						+ codeSameDocumentid + "')]");
	}

	public Element getCodeSameIconConceptTab() {
		return driver.FindElementByXPath(
				"//table[@id='dtDocumentConceptuallySimilar']//i[@class='fa fa-link']/..//following-sibling::td[contains(normalize-space(.),'"
						+ codeSameDocumentid + "')]");
	}

	public Element getCodeSameIconFamilyTab() {
		return driver.FindElementByXPath(
				"//table[@id='dtDocumentFamilyMembers']//i[@class='fa fa-link']/..//following-sibling::td[contains(normalize-space(.),'"
						+ codeSameDocumentid + "')]");
	}

	public Element getCodeSameIconNearDupeTab() {
		return driver.FindElementByXPath(
				"//table[@id='dtDocumentNearDuplicates']//i[@class='fa fa-link']/..//following-sibling::td[contains(normalize-space(.),'"
						+ codeSameDocumentid + "')]");
	}

	public Element getCodeSameIconThreadTab() {
		return driver.FindElementByXPath(
				"//table[contains(@id,'dtDocumentThreadedDocuments')]//thead//tr//th//i[contains(@class,\"fa fa-link\")]//ancestor::table//thead//tr[@id=\"threadedDocumentIdRow\"]//th[contains(text(),'"
						+ codeSameDocumentid + "')]");
	}

	public Element getStampBlueColour() {
		return driver.FindElementById("ElmentIcon_BLUE");
	}

	public Element geDocView_ThreadMap_CodeSameAsIconForReviewer(int rowno) {
		return driver
				.FindElementByXPath("//*[@id='dtDocumentThreadedDocuments']//*[@id='threadedDocumentIdRowStatus']//th["
						+ rowno + "]//i[@class='fa fa-link ']");
	}

	public Element getDocView_AnalyticsNewFolderThreadMap() {
		return driver.FindElementById("tabNew");
	}

	public Element getDocView_AnalyticsNewFolderTree() {
		return driver.FindElementByXPath("//*[@id='folderJSTree']//a[contains(text(),'All Folders')]");
	}

	public Element getDocView_AnalyticsNewFolderTextBox() {
		return driver.FindElementById("txtFolderName");
	}

	public Element getDocView_AnalyticsNewFolderContiBtn() {
		return driver.FindElementByXPath("//footer//button[@id='btnAdd']");
	}

	public Element getTotalSelectedDocuments() {
		return driver.FindElement(By.id("spanTotal"));
	}

	public Element getDocView_AnalyticsNewFolderFinalizeBtn() {
		return driver.FindElementById("btnfinalizeAssignment");
	}

	public Element geDocView_ThreadMap_CheckMarkIcon(int rowno) {
		return driver
				.FindElementByXPath("//*[@id='dtDocumentThreadedDocuments']//*[@id='threadedDocumentIdRowStatus']//th["
						+ rowno + "]//i[@class='fa fa-check-circle ']");
	}

	public Element geDocView_ThreadMap_ArrowDownIcon(int rowno) {
		return driver
				.FindElementByXPath("//*[@id='dtDocumentThreadedDocuments']//*[@id='threadedDocumentIdRowStatus']//th["
						+ rowno + "]//i[@class='fa fa-arrow-down ']");
	}

	public Element getDocView_FamilyMember_NoQuery() {
		return driver.FindElementByXPath("//table[@id='dtDocumentFamilyMembers']//tr//td");
	}

	public Element geDocView_MiniDocList_ArrowDownIcon() {
		return driver.FindElementByXPath("//*[@id='SearchDataTable']//i[@class='fa fa-arrow-right']");
	}

	public Element getDocView_Analytics_Concept_DocCheckBox(int rowno) {
		return driver.FindElementByXPath("//*[@id='dtDocumentConceptuallySimilar']//tr[" + rowno + "]//label");
	}

	public Element getDocView_Analytics_FamilyMember_DocCheckBox(int rowno) {
		return driver.FindElementByXPath("//*[@id='dtDocumentFamilyMembers']//tr[" + rowno + "]//label");
	}

	public Element getDocView_Analytics_Concept_CodeSameLink() {
		return driver.FindElementByXPath("//table[@id='dtDocumentConceptuallySimilar']//i[@class='fa fa-link']");
	}

	public Element getDocView_Analytics_FamilyMember_CodeSameLink() {
		return driver.FindElementByXPath("//table[@id='dtDocumentFamilyMembers']//i[@class='fa fa-link']");
	}

	public Element getDocView_Analytics_NearDupe_RemoveCodeSameAs() {
		return driver.FindElementById("liRemoveCodeSameAsNearDupe");
	}

	public Element getDocView_Analytics_Concept_RemoveCodeSameAs() {
		return driver.FindElementById("liRemoveCodeSameAsConceptualSimilar");
	}

	public Element getDocView_Analytics_ThreadMap_TableFirstName() {
		return driver.FindElementByXPath(
				"//*[@id='dtDocumentThreadedDocuments']//tr//th[contains(text(),'Threaded Documents: ')]");
	}

	public Element getDocView_Analytics_NearDupe_WholeTable() {
		return driver.FindElementByXPath("//*[@id='analyticsResize']//*[@id='dtDocumentNearDuplicates']");
	}

	public Element getDocView_Analytics_FamilyMember_WholeTable() {
		return driver.FindElementByXPath("//*[@id='analyticsResize']//*[@id='dtDocumentFamilyMembers']");
	}

	public Element getDocView_Analytics_Concept_WholeTable() {
		return driver.FindElementByXPath("//*[@id='analyticsResize']//*[@id='dtDocumentConceptuallySimilar']");
	}

	public Element getDocView_Analytics_Concept_Docs(int rowno) {
		return driver.FindElementByXPath("//*[@id='dtDocumentConceptuallySimilar']//tr[" + rowno + "]//label//i");
	}

	public Element getDocView_Analytics_Concept_ViewDocument() {
		return driver.FindElementById("liViewDocumentConceptualSimilar");
	}

	public Element getDocView_MetaDataPopOut() {
		return driver.FindElementByXPath(
				"//*[@id='HdrMetaData']//*[@class='button-icon jarviswidget-pop-btn']//i[@class='fa fa-expand']");

	}

	public Element getDocView_Analytics_CodingFormPanel() {
		return driver.FindElementByXPath("//div[@class='panel-body']");
	}

	public Element getDocView_Analytics_MetaDataPanel() {
		return driver.FindElementById("divMetaTab");
	}

	// added by Aathith
	public Element getMetaDataInputInDocView() {
		return driver.FindElementByXPath("//td[@class='form-c c-form-textwidth']/label/input");
	}

	public Element getDocView_Analytics_NearDupe_OriginalView_ZoomIn() {
		return driver.FindElementById("zoomIn_divOriginalDoc");
	}

	public Element getDocView_Analytics_NearDupe_OriginalView_ZoomOut() {
		return driver.FindElementById("zoomOut_divOriginalDoc");
	}

	public Element getDocView_Analytics_NearDupe_OriginalView_RestButton() {
		return driver.FindElementById("fitContent_divOriginalDoc");
	}

	public Element getDocView_Analytics_NearDupe_NearDupeView_ZoomIn() {
		return driver.FindElementById("zoomIn_divNearDupDoc");
	}

	public Element getDocView_Analytics_NearDupe_NearDupeView_ZoomOut() {
		return driver.FindElementById("zoomOut_divNearDupDoc");
	}

	public Element getDocView_Analytics_NearDupe_NearDupeView_RestButton() {
		return driver.FindElementById("fitContent_divNearDupDoc");
	}

	public ElementCollection getDocView_Analytics_ThreadMap_ToolTipText(String text) {
		return driver.FindElementsByXPath("//div[@id='tooltip']//div//label[contains(text(),'" + text + "')]");
	}

	public Element getdocIdText() {
		return driver.FindElementByXPath("//th[contains(text(),'-I')]");
	}

	public ElementCollection getDocView_Analytics_ThreadedDocs() {
		return driver
				.FindElementsByXPath("//table[@id='dtDocumentThreadedDocuments']//tr[@id='threadedDocumentIdRow']//th");
	}

	public Element getDocView_MetaDataPanel_EmailAuthorNameAndAddress() {
		return driver.FindElementByXPath("//td[text()='EmailAuthorNameAndAddress']");
	}

	public Element getDocView_MetaDataPanel_PopupEmailAuthorNameAndAddress() {
		return driver.FindElementByXPath(
				"//*[@id='datatable_fixed_column_wrapper_test']//td[text()='EmailAuthorNameAndAddress']");
	}

	public Element getDocView_MetaDataPanel_BrowseAllMetaDataPanel() {
		return driver.FindElementById("btnViewAllMetaData");
	}

	public Element getDocView_MetaDataPanel_BrowseAllMetaDataPanel_PopupField() {
		return driver.FindElementByXPath("//span[text()='All Metadata for Document']");
	}

	public ElementCollection getAssgnPaginationCount() {
		return driver.FindElementsByCssSelector("li[class*='paginate_button '] a");
	}

	public Element getAssgnPaginationNextButton() {
		return driver.FindElementByCssSelector("li[class='paginate_button next'] a");
	}

	public Element getDocView_MetaDataPanel_Popup_CloseButton() {
		return driver.FindElementByXPath("//button[@class='btn btn-primary']");
	}

	public Element getDocView_MetaDataPanel_PopoutRow(String fieldName) {
		return driver.FindElementByXPath("//*[@id='MetaDataDT1']/tbody//tr[td='" + fieldName + "']");
	}

	public Element getMetaDataPaginationNextButton() {
		return driver.FindElementByXPath("//li[@id='MetaDataDT1_next']");
	}

	// Added By Baskar
	// DocView Mini DocList Header Count
	public Element getCountOffField() {
		return driver.FindElementByXPath("//*[@id='SearchDataTable_wrapper']/div[3]/div[1]//table/thead/tr/th");
	}

	public Element getConfigureMiniDocListPopUP() {
		return driver.FindElementById("Configure Mini DocList");
	}

	public ElementCollection getMiniDocListHeaderValue() {
		return driver.FindElementsByXPath(
				"//div[@class='dataTables_scrollHeadInner']//table[contains(@class,'DTTT_selectable')]/thead/tr/th");
	}

	public Element getCentralPanelDispaly() {
		return driver.FindElementByXPath("(//span[@class='LoadImagePosition']//img[@id='imgLoadPM'])[1]");
	}

	public Element getCodingStampPopup() {
		return driver.FindElementByXPath("(//div[contains(@class,'ui-dialog')])[1]");
	}

	public Element getCodingStampCancel() {
		return driver.FindElementByXPath("//div[@class='ui-dialog-buttonset']//button[text()='Cancel']");
	}

	// Modified on 9/01/21
	public Element getDocumetId() {
		return driver.FindElementByXPath("//td[contains(text(),'" + Input.docIDs + "')]");
	}

	public Element getDocumetListLoading() {
		return driver.FindElementByXPath("//div[@class='dataTables_processing dts_loading']");
	}

	// Added by Baskar
	// Document count verify in Minidoclist
	public ElementCollection getDocumetCountMiniDocList() {
		return driver.FindElementsByXPath("//*[@id='SearchDataTable']//tr/td[1]/label");
	}

	public Element getPureHitCount() {
		return driver.FindElementByXPath("//*[@id='001']//i[contains(@class,'addTile')]");

	}

	// Added by Mohan
	public Element getDocView_MiniDocListDocId(String text) {
		return driver.FindElementByXPath("//th[text()='" + text + "']");
	}

	public Element getDocView_MiniDoclistChildWindow() {
		return driver
				.FindElementByXPath("//*[@id='HdrMiniDoc']//*[@class='jarviswidget-ctrls']//i[@class='fa fa-expand']");
	}

	public Element getDocView_Analytics_NoQuery() {
		return driver.FindElementByXPath("//*[@id='analyticsResize']//div[contains(text(),'Your query')]");
	}

	public Element getDocView_Analytics_ThreadMap20PlusDocs() {
		return driver.FindElementByXPath("//*[@id='analyticsResize']//a[contains(text(),'More data')]");
	}

	public Element getDocView_Analytics_ThreadMap_DocId(int rowno) {
		return driver
				.FindElementByXPath("//*[@id='analyticsResize']//tr[@id='threadedCheckBoxRow']//th[" + rowno + "]");
	}

	public Element getDocView_Analytics_ThreadMap_EmailDocs(int rowno) {
		return driver.FindElementByXPath(
				"//*[@id='analyticsResize']//tr[@id='threadedCheckBoxRow']//th[" + rowno + "]//label");
	}

	public Element getDocView_CodingForm_BlueIcon() {
		return driver.FindElementById("ElmentIcon_BLUE");
	}

//	select doc form clock icon in mini doc list added by Baskar
	public Element getselectDocFromClckIcon() {
		return driver.FindElementByXPath("//*[@id='ulDocViewHistory']/li");
	}

//  Added by Baskar
	public Element getMiniDocListRightArrow() {
		return driver.FindElementByXPath("//*[@class='fa fa-arrow-right']");
	}

	public ElementCollection getMiniDocListCompletedCount() {
		return driver.FindElementsByXPath("	//table[@id='SearchDataTable']//i[@class='fa fa-check-circle']");
	}

	public Element getVerifyPrincipalDocument() {
		return driver.FindElementByXPath("//i[@class='fa fa-arrow-right']/parent::td//following-sibling::td[1]");
	}

	public Element getUnCompleteButton() {
		return driver.FindElementByXPath("//button[@id='btnDocumentUnComplete']");
	}

	public Element getDocView_MiniDoc_Selectdoc(int rowno) {
		return driver.FindElementByXPath("//*[@id='SearchDataTable']//following-sibling::tbody//tr[" + rowno + "]");
	}

	// Added by Jeevitha
	public Element getDocView_AllRedaction() {
		return driver.FindElementByXPath("//div[@class='unselectable h6']");
	}

	public Element getDocView_AllRedactionCount() {
		return driver.FindElementByXPath("//div[@class='unselectable h6']//following::div[@id='counterAll']");
	}

	public ElementCollection getDocView_BatchRedaction() {
		return driver.FindElementsByXPath("//div[@class='col-md-12']//div[@class='h6 unselectable']");
	}

	public Element getDocView_BatchRedactionCount() {
		return driver.FindElementByXPath(
				"//div[@class='col-md-12']//div[@class='h6 unselectable']//following::div[@id='counterAllBatch']");
	}

	// Added by Gopinath - 20/09/2020
	public Element getLastCodeIconWhitePencil() {
		return driver.FindElementById("CodeSameAsLast");
	}

	public Element getCodeSameAsLastDocMessage() {
		return driver.FindElementByXPath("//div[@role='tooltip']");
	}

	public Element getGearIcon1() {
		return driver.FindElementByXPath("//i[@id='wEdit']");
	}

	public Element getPopoutIcon() {
		return driver.FindElementByXPath(
				"//header[@id='HdrCoddingForm']//div[@class='button-icon jarviswidget-pop-btn']//i");
	}

	public Element getDocView_Analytics_ThreadMap_DesginationMarker() {
		return driver.FindElementByXPath("//*[@id='dtDocumentThreadedDocuments']//i[@class='fa fa-circle']");
	}

//	Added by Baskar
	public Element getVer_Responsive_ChckBox() {
		return driver.FindElementByXPath("//span[text()='Not_Responsive']//ancestor::div[@class='radio-group']");
	}

	public Element getHistoryDrp_Dwn() {
		return driver.FindElementByXPath(
				"//*[@id='ulDocViewHistory']//following-sibling::i[@class='fa fa-arrow-circle-o-right']//parent::a");
	}

	public Element getViewDocAllList() {
		return driver.FindElementByXPath("//a[@id='btnShowAllInDocList']");
	}

	public ElementCollection getDocList_ChckBox(int row) {
		return driver.FindElementsByXPath("//table[@id='dtDocList']/tbody/tr/td[" + row + "]");
	}

	public Element getDocList_Action_Drp_Dwn() {
		return driver.FindElementByXPath("//button[@id='idAction']");
	}

	public Element getDocListViewInDocView() {
		return driver.FindElementByXPath("//ul[@class='dropdown-menu action-dd']//li//a[@id='ViewInDocView']");
	}

	public ElementCollection getAnalyticalPanelDocIdText() {
		return driver.FindElementsByXPath("//table[@id='dtDocumentNearDuplicates']/tbody/tr//td[2]");
	}

	public ElementCollection getMiniDocListDocIdText() {
		return driver.FindElementsByXPath("//*[@id='SearchDataTable']//tr/td[2]");
	}

	public ElementCollection getDocList() {
		return driver.FindElementsByXPath("//tr[@role='row']//td[3]");
	}

	public Element getDocListCheckBox(String docId) {
		return driver.FindElementByXPath("//tr[@role='row']//td[text()='" + docId + "']//..//td//label");
	}

	public Element getAnalytical_ChckBox(int row) {
		return driver.FindElementByXPath("//table[@id='dtDocumentNearDuplicates']/tbody/tr[" + row + "]/td/label");
	}

	public Element getCodeSameAsNearDupe() {
		return driver.FindElementByXPath("//li[@id='liCodeSameAsNearDupe']");
	}

	public Element getViewDocumentNearDupe() {
		return driver.FindElementByXPath("//li[@id='liViewDocumentNearDupe']");
	}

	public Element getMetaDataLabel() {
		return driver.FindElementById("liDocumentMetadata");
	}

	public Element getAnalyCheckBox(String docid) {
		return driver.FindElementByXPath(
				"//table[@id='dtDocumentNearDuplicates']//td[text()=' " + docid + "']//..//label[@class='checkbox']");
	}

	public Element getMiniDocListText(String docid) {
		return driver.FindElementByXPath("//table[@id='SearchDataTable']//td[text()='" + docid + "']");
	}

	public Element getMiniDocListTextChck_Box(String docId) {
		return driver.FindElementByXPath(
				"//div[@id='miniDocListWrap']//tr//td[text()='" + docId + "']//..//label[@class='checkbox']//i");
	}

	public Element getSaveAndNextButton() {
		return driver.FindElementByXPath("//a[@id='SaveAndNext']");
	}

	public Element selectCodingFormCheckBoxes(String value) {
		return driver.FindElementByXPath("//span[text()='" + value + "']/ancestor::div/label");
	}

	public Element getDocumentId2() {
		return driver.FindElementByXPath("//td[contains(text(),'" + Input.crammerdocumentID + "')]");
	}

	public Element getCodingFormValidErrorMeta() {
		return driver.FindElementByXPath("//p[text()='Coding Form validation failed']");
	}

	public Element getCodingFormCommentDisabled() {
		return driver.FindElementByXPath("//textarea[@name='COMMENT']");
	}

	public Element getCodingFormCommentVal() {
		return driver.FindElementByXPath("//textarea[@name='COMMENT'][@title='Help for testing']");
	}

	public Element getCommentValidationDisplayed() {
		return driver.FindElementByXPath("//span[text()='Error for testing']");
	}

	public Element getInstructionTxt() {
		return driver.FindElementById("l_it_2");
	}

	public Element getBorderVerify() {
		return driver.FindElementByXPath("//*[@id='0_radiogroup']");
	}

	public Element getStaticWarningError() {
		return driver.FindElementByXPath(
				"//p[text()='Blank coding form or coding form with static text could not processed.']");
	}

	public Element getTagNotSelectable() {
		return driver.FindElementByXPath("//label[@id='l_it_1']//input");
	}

	public Element getDocView_Analytics_ThreadMap_DocCheckBox(int rowno) {
		return driver.FindElementByXPath(
				"//*[@id='analyticsResize']//tr[@id='threadedCheckBoxRow']//th[" + rowno + "]//label");
	}

	public Element getDocView_PrincipalDocIdE1(String docId1) {
		return driver.FindElementByXPath("//tr[@id='threadedDocumentIdRow']//th[contains(text(),'" + docId1 + "')]");
	}

	public Element getDocView_NearDupeIconForSpecificDocument(String documentId) {
		return driver.FindElementByXPath(
				"//tr[contains(@class,'dtDocumentNearDuplicates')]//td[contains(normalize-space(.),'" + documentId
						+ "')]");
	}

	public Element getOriginalDocPageNumber() {
		return driver.FindElementByXPath("//input[@id='PageNumber_divOriginalDoc']");
	}

	public Element getNearDupeDocPageNumber() {
		return driver.FindElementByXPath("//input[@id=\"PageNumber_divNearDupDoc\"]");
	}

	public Element getNearDupeDocTotalPageCount() {
		return driver.FindElementByXPath("//*[@id=\"lblTotalPageCount_divNearDupDoc\"]");
	}

	public Element getOriginalDocPaginationIcon() {
		return driver.FindElementByXPath("//li[@id=\"lastPage_divOriginalDoc\"]//i");
	}

	public Element getNearDupeDocPaginationIcon() {
		return driver.FindElementByXPath("//li[@id=\"lastPage_divNearDupDoc\"]//i");
	}

	public Element getDocView_Analytics_ThreadedDocument_Doc(int rowno) {
		return driver.FindElementByXPath("//*[@id='analyticsResize']//*[@id='dtDocumentThreadedDocuments']//tr[" + rowno
				+ "]//input//following-sibling::i");
	}

	public Element geDocView_ThreadMap_CodeSameAsIcon(int rowno) {
		return driver.FindElementByXPath("//*[@id='analyticsResize']//*[@id='dtDocumentThreadedDocuments']//tr[" + rowno
				+ "]//i[contains(@class,\"fa fa-link\")]");
	}

	public Element getDocView_FamilyMember_DocIdColumn() {
		return driver.FindElementByXPath("//table[@id=\"dtDocumentFamilyMembers\"]//th[text()='DocID']");
	}

	public Element getDocView_Family_Member_FileNameColumn() {
		return driver.FindElementByXPath("//table[@id=\"dtDocumentFamilyMembers\"]//th[text()='FILENAME']");
	}

	public Element getDocView_Family_Member_CheckBoxColumn() {
		return driver.FindElementByXPath("//input[contains(@id,'chkDocFamily')]/..");
	}

	public Element getDocView_Family_Member_CodeSameIconColumns() {
		return driver.FindElementByXPath("//label//input[contains(@id,'chkDocFamily')]//following-sibling::i");
	}

	public Element getDocView_NearDupe_DocIdColumn() {
		return driver.FindElementByXPath("//table[@id=\"dtDocumentNearDuplicates\"]//th[text()='DocID']");
	}

	public Element getDocView_NearDupe_FileNameColumn() {
		return driver.FindElementByXPath("//*[@id=\"dtDocumentNearDuplicates\"]//th[text()='FILENAME']");
	}

	public Element getDocView_NearDupe_PercentageColumn() {
		return driver.FindElementByXPath("//*[@id=\"dtDocumentNearDuplicates\"]//th[text()='PERCENTAGE']");
	}

	public Element getDocView_NearDupe_CheckBoxColumn() {
		return driver.FindElementByXPath("//*[@id=\"dtDocumentNearDuplicates\"]//input[@type=\"checkbox\"]/..");
	}

	public Element getDocView_NearDupe_CodeSameIconColumns() {
		return driver.FindElementByXPath("//*[@id=\"dtDocumentNearDuplicates\"]//label//i");
	}

	public Element getDocView_NearDupe_PercentageIconInNearDupeNativeViewer() {
		return driver.FindElementByXPath("//*[@id=\"dtDocumentNearDuplicates\"]//td[contains(text(),'%')]");
	}

	public Element getDocView_Conceptual_DocIdColumn() {
		return driver.FindElementByXPath("//table[@id=\"dtDocumentConceptuallySimilar\"]//th[text()='DocID']");
	}

	public Element getDocView_Conceptual_FileNameColumn() {
		return driver.FindElementByXPath("//*[@id=\"dtDocumentConceptuallySimilar\"]//th[text()='FILENAME']");
	}

	public Element getDocView_Conceptual_CheckBoxColumn() {
		return driver.FindElementByXPath("//*[@id=\"dtDocumentConceptuallySimilar\"]//input[@type=\"checkbox\"]/..");
	}

	public Element getDocView_Conceptual_CodeSameIconColumns() {
		return driver.FindElementByXPath("//*[@id=\"dtDocumentConceptuallySimilar\"]//label//i");
	}

	public Element getThreadedDocumentWhichHasCodeSameIcon() {
		return driver.FindElementByXPath(
				"//table[contains(@id,'dtDocumentThreadedDocuments')]//thead//tr//th//i[contains(@class,\"fa fa-link\")]//ancestor::table//thead//tr[@id=\"threadedDocumentIdRow\"]//th[contains(@class,'thread_same')]");
	}

	public Element geDocView_FamilyMembers_CodeSameAsIcon(int rowno) {
		return driver.FindElementByXPath("//*[@id='analyticsResize']//*[@id='dtDocumentFamilyMembers']//tr[" + rowno
				+ "]//i[contains(@class,\"fa fa-link\")]");
	}

	public Element getFamilyMembersWhichHasCodeSameIcon() {
		return driver.FindElementByXPath(
				"//tr[contains(@class,'dtDocumentFamilyMembers')]//i[@class='fa fa-link']/..//following-sibling::td[1]");
	}

	public Element getDocView_Analytics_Family_Member_CodeSameAs() {
		return driver.FindElementById("liCodeSameAsFamilyMember");
	}

	// Added by Gopinath - 09/10/2021
	public Element getDocviewCommentSection() {
		return driver.FindElementByXPath(" //textarea[@name='COMMENT']");
	}

	public Element getSelectSaveLink() {
		return driver.FindElementById("Save");
	}

	public Element getDocView_Analytics_FamilyMember_Doc(int rowno) {
		return driver.FindElementByXPath("//*[@id='analyticsResize']//*[@id='dtDocumentFamilyMembers']//tr[" + rowno
				+ "]//input//following-sibling::i");
	}

	// Added by Raghuram
	public Element getDocView_RedactionPanel() {
		return driver
				.FindElementByXPath("//div[@class='sub-tab-2 active']//ul[@class='list-inline pull-right active']");
	}

	public Element getDocView_AllRedationColumn() {
		return driver.FindElementByXPath("//div[@class='message-1 col-md-12']//div[text()='All Redactions']");
	}

	public Element getDocView_BatchRedactionColumn() {
		return driver.FindElementByXPath("//div[@class='col-md-12']//div[text()='Batch']");
	}

	public Element getDocView_SelectedDocID() {
		return driver.FindElementByXPath("//tr//i[@class='fa fa-arrow-right']//..//..//td[2]");
	}

	public Element getActiveTabInAnalyticalPanel() {
		return driver.FindElementByXPath(
				"//div[@id=\"AnalyticsTab\"]//div[@class=\"tab-content\"]//div[@class=\"tab-pane active\"]");
	}

	public Element getHitPanleVerify(String panel) {
		return driver.FindElementById("PHitCount_" + panel + "");
	}

	public ElementCollection getInclusiveElamilValueFromToolTip() {
		return driver.FindElementsByXPath(
				"//div[@id=\"tooltip\"]/div//div/label[contains(text(),'Inclusive')]//following-sibling::span");
	}

	public Element getInclusiveElamilValueFromToolTipElement() {
		return driver.FindElementByXPath(
				"//div[@id=\"tooltip\"]/div//div/label[contains(text(),'Inclusive')]//following-sibling::span");
	}

	public Element getDeletePopUpAssignedColour() {
		return driver.FindElementByXPath("//button[text()='Delete']");
	}

	public Element getHitPanel() {
		return driver.FindElementByXPath("//p[contains(@id,'PHitCount')]");
	}

	// Added by Gopinath - 10/11/2021
	public Element getAssertOnImage() {
		return driver.FindElementByCssSelector("g:nth-child(1) svg:nth-child(1)");
	}

	public Element getSelectTab() {
		return driver.FindElementById("//div[@class='dataTables_scrollBody'][1]");
	}

	public Element getImageTab() {
		return driver.FindElementByXPath("//span[text()='Images']");
	}

	public Element selectDocInImageTab() {
		return driver.FindElementById("ui-id-2");
	}

	public Element getDateFormat() {
		return driver.FindElementByXPath("//input[@class='myDtPkr hasDatepicker']");
	}

	public Element getVerifyStaticText(String fieldValue) {
		return driver.FindElementByXPath("//span[.='" + fieldValue + "']");
	}

	public ElementCollection getHitPanelCollection() {
		return driver.FindElementsByXPath("//p[contains(@id,'PHitCount')]");
	}

	// added by iyappan
	public Element getCodingFormObjectNames(int i) {
		return driver.FindElementById("l_it_" + i + "");
	}

	public Element getCodingFormRequiredField(int i) {
		return driver.FindElementByXPath("//span[@id='l_it_" + i + "']/span");
	}

	public Element getCodingFormHelpText(String objectName) {
		return driver.FindElementByXPath("//textarea[@controllabel='" + objectName + "']");
	}

	public Element getCodingFormErrorText(String objectName) {
		return driver.FindElementByXPath("//textarea[@controllabel='" + objectName + "']/ancestor::td/span");
	}

	public Element getCodingFormRadioOrCheckBox(String group) {
		return driver.FindElementByXPath("//div[@class='" + group + "']");
	}

	public Element getCodingFormRadioGrpTagNamenHelpText(int i) {
		return driver.FindElementByXPath("//div[@id='" + i + "_radiogroup']//span//span");
	}

	public Element getCodingFormRadioGrpTagErrorlabelText(int i) {
		return driver.FindElementByXPath("//div[@id='" + i + "_radiogroup']/ancestor::td/span");
	}

	public Element getCodingFormCheckGrpTagNamenHelpText(int i) {
		return driver.FindElementByXPath("//div[@id='" + i + "_checkgroup']//span//span");
	}

	public Element getCodingFormCheckGrpTagErrorlabelText(int i) {
		return driver.FindElementByXPath("//div[@id='" + i + "_checkgroup']/ancestor::td/span");
	}

	public Element getCodingFormTag(int i) {
		return driver.FindElementByXPath("//label[@id='l_it_" + i + "']/input[@disabled='']");
	}

	public Element getCodingFormTaglabel(int i) {
		return driver.FindElementByXPath("//span[@id='l_it_" + i + "']");
	}

	public Element getCodingFormMetaDataHelpText(String objectName) {
		return driver.FindElementByXPath("//input[@projectfieldname='" + objectName + "']");
	}

	public Element getCodingFormMetaDataErrorText(String objectName) {
		return driver.FindElementByXPath("//input[@projectfieldname='" + objectName + "']/ancestor::td/span");
	}

	public Element getErrorCodeSameAsIgnored() {
		return driver.FindElementByXPath(
				"//p[text()='Some documents were not in code same as list, they have been ignored.']");
	}

	public Element getNavigationMsg() {
		return driver.FindElementByXPath("//span[text()='Confirm Navigation']/ancestor::div[@role='dialog']//p");
	}

	public Element getNavigationMsgPopupYesBtn() {
		return driver.FindElementByXPath(
				"//span[text()='Confirm Navigation']/ancestor::div[@role='dialog']//button[text()='Yes']");
	}

	public Element getNavigationMsgPopupNoBtn() {
		return driver.FindElementByXPath(
				"//span[text()='Confirm Navigation']/ancestor::div[@role='dialog']//button[text()='No']");
	}

	public Element getEditProfile() {
		return driver.FindElementByXPath("//a[text()='Edit Profile']");
	}

	public Element getCodingStampIconText(String icon) {
		return driver.FindElementByXPath("//ul[@id='UserStamps']//li[@id='" + icon + "']//i[@class='fa fa-tag']");
	}

	public Element getCompleteBtnNotPresent() {
		return driver.FindElementByXPath("//button[@id='btnDocumentComplete' and @style='display: none;']");
	}

	// added by sowndarya.velraj
	public Element getDocFileTypeCheckbox() {
		return driver.FindElementByXPath("//tr[contains(@class,'rowNumber_0')]//label[@class='checkbox']/i");
	}

	public Element getAddRedationBtn() {
		return driver.FindElementByXPath("//button[@id='new']/i");
	}

	public Element getAudioStartTime() {
		return driver.FindElementByXPath("//div[@id='audioGrid_wrapper']//tr[@class='odd']/td[1]//input");
	}

	public Element getAudioEndTime() {
		return driver.FindElementByXPath("//div[@id='audioGrid_wrapper']//tr[@class='odd']/td[2]//input");
	}

	public Element getSaveAudioBtn() {
		return driver.FindElementByXPath("//i[@class='fa fa-fw fa-save']");
	}

	public ElementCollection getAudioBytes() {
		return driver.FindElementsByXPath("//div[@id='redAction']//span[@class='redactiondraw']");
	}

	public Element getDeleteAudioRedaction(int count) {
		return driver.FindElementByXPath("(//i[@class='fa fa-fw fa-close'])[" + count + "]");
	}

	public Element getDeleteRedactionMsgBox() {
		return driver.FindElementByXPath("//span[text()='Delete Audio Redaction']/..//button[normalize-space()='Yes']");
	}

	public Element getPopUpLeftButton() {
		return driver.FindElementByXPath("//span[.='Confirm Navigation']");
	}

	public Element getNavigationButton(String click) {
		return driver
				.FindElementByXPath("(//div[@class='ui-dialog-buttonset']//button[text()='" + click + "'])[last()]");
	}

	public Element getDashBoardHomeIcon() {
		return driver.FindElementByXPath("//i[@class='fa fa-home']");
	}

	public Element getReviewMode() {
		return driver.FindElementByXPath("//span[text()='REVIEW MODE ']");
	}

	public Element getDociD(String docId) {
		return driver.FindElementByXPath("// *[@id='SearchDataTable']//tr['row']//td[2][text()='" + docId + "']");

	}

	// added by krishna

	public Element getDocView_Analytics_Conceputal_ArrowMark() {
		return driver.FindElementByXPath("//*[@id='dtDocumentConceptuallySimilar']//i[@class='fa fa-arrow-right']");
	}

	public ElementCollection getDocView_MiniDocList_Docs() {
		return driver.FindElementsByXPath("//table[@id='SearchDataTable']/tbody//tr[@role='row']");
	}

	public Element getDocView_Select_MiniDocList_Docs(int rowNo) {
		return driver.FindElementByXPath("(//table[@id='SearchDataTable']/tbody//td[2])[" + rowNo + "]");
	}

	public ElementCollection getDocView_Analytics_NearDupes_Docs() {
		return driver.FindElementsByXPath("//table[@id='dtDocumentNearDuplicates']//tbody//tr[@role='row']");
	}

	public ElementCollection getDocView_Analytics_ThreadMap_Docs() {
		return driver.FindElementsByXPath(
				"//table[@id='dtDocumentThreadedDocuments']//thead//tr[@id='threadedDocumentIdRow']//th");
	}

	public ElementCollection getDocView_Analytics_FamilyMember_Docs() {
		return driver.FindElementsByXPath("//table[@id='dtDocumentFamilyMembers']//tbody//tr[@role='row']");
	}

	public ElementCollection getDocView_Analytics_Concept_Docs() {
		return driver.FindElementsByXPath("//table[@id='dtDocumentConceptuallySimilar']//tbody//tr[@role='row']");
	}

	public Element getFolderSelection(String foldName) {
		return driver.FindElementByXPath("//ul[@class='jstree-children']//a[text()='" + foldName + "']");
	}

	public Element getSelectedDocIdInMiniDocList() {
		return driver.FindElementByXPath(
				"//i[@class=\"fa fa-arrow-right\"]/..//following-sibling::td[contains(text(),'ID')]");
	}

	public Element getDocView_MiniDocList_SelectSecDocs() {
		return driver.FindElementByXPath("//*[@id='SearchDataTable']//tr[2]//td[2]");
	}

	// Added by Gopinath - 29/11/2021
	public Element getSelectedAreaElement() {
		return driver.FindElementByXPath("//div[@class='igViewerGraphics']");
	}

	public ElementCollection getRemarkPanelItems() {
		return driver.FindElementsByXPath("//div[@id='RemarkPnl']//p");
	}

	public ElementCollection getPencilsofRemarks() {
		return driver.FindElementsByXPath("//div[@id='RemarkPnl']//p//..//..//i[contains(@class,'pencil')]");
	}

	public Element getEditTextField() {
		return driver.FindElementByXPath("//strong/following-sibling::span//textarea");
	}

	public Element getDocView_CodingFormComments() {
		return driver.FindElementByXPath("//textarea[@name='COMMENT']");
	}

	public Element getDocView_ErrorMessage(String error) {
		return driver.FindElementByXPath("//span[@class='validationSpan'][text()='" + error + "']");
	}

	// Added by Vijaya.Rani
	public Element getDocView_NearDupeCheckMark() {
		return driver.FindElementByXPath(".//*[@id='dtDocumentNearDuplicates']//i[@class='fa fa-check-circle']");
	}

	public Element getDocView_ConceptuallySimilarCheckMark() {
		return driver
				.FindElementByXPath(".//table[@id='dtDocumentConceptuallySimilar']//i[@class='fa fa-check-circle']");
	}

	public Element getPopUpVerify() {
		return driver.FindElementByXPath("//div[contains(@class,'ui-dialog ui-corner')]");
	}

	public Element getCompletedDocs() {
		return driver.FindElement(
				By.xpath("//table[@id='SearchDataTable']//i[@class='fa fa-check-circle']//../following-sibling::td"));
	}

	// Added by Gopinath - 02/12/2021
	public Element getSaveAndNextLink() {
		return driver.FindElementById("SaveAndNext");
	}

	public Element getPersistentPanel() {
		return driver.FindElementByXPath("//div[@class='search-tab col-md-2 clsAccusoftViewerHit active']");
	}

	public Element getPersistentToogle() {
		return driver.FindElementById("EnableSearchTerm");
	}

	public Element getHeader() {
		return driver.FindElementById("header");
	}

	// added by jayanthi
	public ElementCollection getDocIds(int i) {
		return driver.FindElementsByXPath("//div[@id='SearchDataTable_wrapper']//tr[@role='row']/td[" + i + "]");
	}

	public ElementCollection tableheader() {
		return driver.FindElementsByXPath("//div[@class='dataTables_scrollHeadInner']//table//thead//tr//th");
	}

	public Element getLoadingImage() {
		return driver.FindElementByXPath("//span[@class=\"LoadImagePosition\"]");
	}

//Added by gopinath - 08/12/2021
	public ElementCollection getDeleteRemarks() {
		return driver.FindElementsByXPath("//div[@id='RemarkPnl']//span[@id='remarksSaveCancelControls']//i[1]");
	}

	public ElementCollection getAnnotations() {
		return driver.FindElementsByCssSelector("rect[height*='842'][style*='fill']");
	}

//		Added by baskar
	public Element getReviewGearIcon() {
		return driver.FindElementByXPath("//a[@id='miniDocListConfig']");
	}

	public Element getCodingFormPanel() {
		return driver.FindElementById("divLastCodeAndClassification");
	}

	public Element getVerifyStamp() {
		return driver.FindElementByXPath("//i[@title='newcolor']");
	}

	public Element getVerifySaveCodingDocStampMsg() {
		return driver.FindElementByXPath("//i[@title='Save this coding form as a new stamp']");
	}

	public Element getShowCompletedDocsToggle() {
		return driver.FindElement(By.xpath("//input[@id=\"ShowCompletedDocs\"]//following-sibling::i"));
	}

	public Element getMiniDocListConfirmationButton(String actionType) {
		return driver.FindElementByXPath("//div[@class='ui-dialog-buttonset']//button[text()='" + actionType + "']");
	}

	// added by brundha
	public Element getMIniDocListDocument() {
		return driver.FindElementByXPath("//table[@id='SearchDataTable']//tr[2]//td[2]");
	}

	public Element getDocId() {
		return driver.FindElementById("activeDocumentId");
	}

	public Element getMiniDocListIcon() {
		return driver.FindElementByXPath("//header[@id='HdrMiniDoc']//div//i[@class='fa fa-expand']");
	}

	public Element getPanelText() {
		return driver.FindElementByXPath("//p[@id='PHitCount_crammer' and contains(text(),'crammer')]");
	}

	// Added by Aathith
	public Element groupelementCheckBox() {
		return driver.FindElementByXPath("//div[@class='group-elements']//input[@type='checkbox']/..");
	}

	public Element getParentDocID() {
		return driver.FindElementByXPath("//table[@id='SearchDataTable']//tr[6]//td[2]");
	}

	public Element getNoDefaultCodingForm() {
		return driver.FindElementByXPath("//div[text()='No Default Coding Form']");
	}

	public Element getRadioBoxforRdoGrp_2() {
		return driver.FindElementByXPath("//input[@name='radiogroup_2']/..");
	}

	public Element getViewCoding() {
		return driver.FindElementById("btnViewCodingStampDetails");
	}

	public Element getDocumentsCommentViewCoding() {
		return driver.FindElementById("//div[@id='viewCodingStamp']//textarea[@id='1_textarea']");
	}

	public Element getDocListAllDocCheckBox() {
		return driver.FindElementByXPath("//div[@id='dtDocList_wrapper']//th/label/i");
	}

	public Element getYesButtonForAllDoc() {
		return driver.FindElementByXPath("(//input[@id='Yes'])[1]");
	}

	public Element getDocListOkBtn() {
		return driver.FindElementByXPath("//div[@class='MessageBoxButtonSection']//button[text()=' OK']");
	}

	// Added By Vijaya.Rani
	public Element getLastCodeSameAsIcon() {
		return driver.FindElementByXPath("//i[@class='fa fa-tags']");
	}

	public Element getDocView_Analytics_Conceptual_ThirdDoc() {
		return driver.FindElementByXPath("//table[@id='dtDocumentConceptuallySimilar']//tbody/tr[3]//label");
	}

	public Element getCodingStampIcon() {
		return driver.FindElementByXPath("//i[@class='fa fa-save']");
	}

	// Added by Aathith
	public Element getDocFileType() {
		return driver.FindElementByXPath("(//*[text()='MS Outlook Message'])[3]");
	}

	public Element getDocView_Analytics_ChildWindow_FamilyTab_doc(int i) {
		return driver.FindElementByXPath("//*[@id='dtDocumentFamilyMembers']//tr[" + i
				+ "]//input[starts-with(@id,'chkDocFamily')]/following-sibling::i");
	}

	public Element codingFormDisableCheck() {
		return driver.FindElementByXPath("//*[text()='Responsiveness']");
	}

	public Element get1stDocinMiniDocView() {
		return driver.FindElementByXPath("(//*[@id='SearchDataTable']//tr/td[2])[1]");
	}

	public Element getStampOverWriteMessage() {
		return driver.FindElementByXPath(
				"//p[text()[normalize-space()='The Stamp you selected is already in use. Do you want to overwrite this Stamp with the new selections?']]");
	}

	public Element getCodingStampPopUpColurVerify(String colour) {
		return driver.FindElementByXPath("//dl[@id='ddlEditStamps']//..//a[@id='" + colour + "']");
	}

	// Added by gopinath - 22/12/2021
	public Element getFirstDocIdOnMiniDocList() {
		return driver.FindElementByXPath("//table[@id='SearchDataTable']//tr[1]//td[2]");
	}

	public Element getSecondDocIdOnMiniDocList() {
		return driver.FindElementByXPath("//table[@id='SearchDataTable']//tr[2]//td[2]");
	}

	public DocViewPage(Driver driver) {

		this.driver = driver;

		softAssertion = new SoftAssert();
		base = new BaseClass(driver);
		sp = new SessionSearch(driver);
		reusableDocView = new ReusableDocViewPage(driver);
		// this.driver.getWebDriver().get(Input.url + "DocumentViewer/DocView");
		// this.driver.getWebDriver().get(Input.url + "Search/Searches");
	}

	public String getPersistentHit(String searchString) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPersistantHitEyeIcon().Visible();
			}
		}), Input.wait60);
		Thread.sleep(5000);

		try {
			getPersistantHitEyeIcon().waitAndClick(10);
		} catch (Exception e) {
			getAudioPersistantHitEyeIcon().waitAndClick(10);
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getHitPanels().Visible();
			}
		}), Input.wait30);

		int numOfPanels = getHitPanels().size();
		String Phit = "NULL";
		System.out.println("numOfPanels" + (numOfPanels - 1));
		Boolean flag = false;
		for (int i = 2; i <= numOfPanels; i++) {
			if (getTermInHitPanels(i).getText().contains(searchString)) {
				System.out.println("Found " + searchString);
				flag = true;
				Phit = getTermInHitPanels(i).getText();
				break;
			}

		}
		// Assert.assertTrue(flag);
		// driver.getWebDriver().navigate().refresh();
		return Phit;
	}

	public String getAudioPersistentHit(String searchString) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAudioPersistantHitEyeIcon().Visible();
			}
		}), Input.wait60);
		getAudioPersistantHitEyeIcon().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getHitPanels().Visible();
			}
		}), Input.wait30);

		int numOfPanels = getHitPanels().size();
		String Phit = "NULL";
		System.out.println("numOfPanels" + (numOfPanels - 1));
		Boolean flag = false;
		for (int i = 1; i <= numOfPanels; i++) {
			if (getTermInHitPanels(i).getText().contains(searchString)) {
				System.out.println("Found " + searchString);
				flag = true;
				Phit = getTermInHitPanels(i).getText();
				break;
			}

		}
		// Assert.assertTrue(flag);
		// driver.getWebDriver().navigate().refresh();
		return Phit;
	}

	public void addCommentToNonAudioDoc(String comment) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddComment1().Visible();
			}
		}), Input.wait60);
		getAddComment1().Clear();
		getAddComment1().SendKeys(comment);
		getCompleteDocBtn().waitAndClick(30);

		base.VerifySuccessMessage("Document completed successfully");
	}

	public void addRemarkNonAudioDoc(String remark) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNonAudioRemarkBtn().Visible();
			}
		}), Input.wait60);
		// getDocView_DocId("ID00000125").waitAndClick(20);

		getNonAudioRemarkBtn().waitAndClick(10);

		try {
			getDocView_Remark_DeleteIcon().waitAndClick(10);
			base.getPopupYesBtn().waitAndClick(5);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			System.out.println("Remark not present");
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectRemarkDocArea().Visible();
			}
		}), Input.wait30);
//    	System.out.println(getSelectRemarkDocArea().getText());
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		/*
		 * Actions newBuilder = new Actions(driver.getWebDriver());
		 * newBuilder.moveByOffset(6/2,0).clickAndHold().moveByOffset(6,0).release().
		 * build().perform(); // getSelectRemarkDocArea().Click();
		 */

		WebElement element_node = driver.getWebDriver()
				.findElement(By.xpath("//div[contains(@id,'pccViewerControl')]//*[name()='svg']//*[name()='text'][1]"));
		JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();
		jse.executeScript("arguments[0].style.border='3px solid red'", element_node);
		((JavascriptExecutor) driver.getWebDriver()).executeScript("arguments[0].setAttribute('style', arguments[1]);",
				element_node, "color: yellow; border: 2px solid yellow;");

		/*
		 * new
		 * Actions(driver.getWebDriver()).keyDown(driver.getWebDriver().findElement(By.
		 * xpath(
		 * "//div[contains(@id,'pccViewerControl')]//*[name()='svg']//*[name()='text'][1]"
		 * )), Keys.TAB).build().perform(); Actions builder = new
		 * Actions(driver.getWebDriver());
		 * builder.moveToElement(driver.getWebDriver().findElement(By.xpath(
		 * "//div[contains(@id,'pccViewerControl')]//*[name()='svg']//*[name()='text'][1]"
		 * ))).build().perform(); //Integer width =
		 * element.getWebElement().getSize().getWidth();
		 */
		Actions newBuilder = new Actions(driver.getWebDriver());
		newBuilder.moveByOffset(6 / 2, 0).clickAndHold().moveByOffset(6, 0).release().build().perform();
		getSelectRemarkDocArea().Click();

		driver.scrollPageToTop();
		getAddRemarkbtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getRemarkTextArea().Visible();
			}
		}), Input.wait30);
		getRemarkTextArea().SendKeys(remark);
		getSaveRemark().Click();
		// successMsgSaveDoc();

	}

	public void completeNonAudioDocument() {
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCompleteDocBtn().Visible();
			}
		}), Input.wait30);
		getCompleteDocBtn().waitAndClick(20);
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		base.VerifySuccessMessage("Document completed successfully");

	}

	public void playAudio() throws InterruptedException {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_IconFileType().Visible();
			}
		}), Input.wait120);
		Assert.assertEquals(getDocView_IconFileType().getText().toString(), "M");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_TextFileType().Visible();
			}
		}), Input.wait120);
		Assert.assertEquals(getDocView_TextFileType().getText().toString(), "MP3 VERSION");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_IconPlay().Displayed();
			}
		}), Input.wait30);
		getDocView_IconPlay().waitAndClick(30);
		// Thread.sleep(10000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_IconPause().Displayed();
			}
		}), Input.wait30);
		getDocView_IconPause().waitAndClick(30);
		Thread.sleep(3000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_RunningTime().Visible();
			}
		}), Input.wait30);
		String runningTime = getDocView_RunningTime().getText();

		if (Integer.parseInt(runningTime.substring(6, 8)) >= 5) {
			System.out
					.println("The total time for audio played is greater than 5 i.e. :" + runningTime.substring(6, 8));
			UtilityLog.info("The total time for audio played is greater than 5 i.e. :" + runningTime.substring(6, 8));
		} else {
			System.out.println("The total time for audio played is not displayed correctly");
			UtilityLog.info("The total time for audio played is not displayed correctly");
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_IconStop().Displayed();
			}
		}), Input.wait30);
		getDocView_IconStop().Click();
		String runningTime1 = getDocView_RunningTime().getText();
		if (Integer.parseInt(runningTime1.substring(6, 8)) == 0) {
			System.out
					.println("The total time for audio played is greater than 5 i.e. :" + runningTime.substring(6, 8));
			UtilityLog.info("The total time for audio played is greater than 5 i.e. :" + runningTime.substring(6, 8));
		} else {
			System.out.println("The total time for audio played is not displayed correctly");
			UtilityLog.info("The total time for audio played is not displayed correctly");
		}

	}

	public void audioRemark(String remark) throws InterruptedException, ParseException {

		// adding remarks

		// click on remarks button
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvancedSearchAudioRemarkIcon().Visible();
			}
		}), Input.wait30);
		getAdvancedSearchAudioRemarkIcon().waitAndClick(30);

		try {
			// Delete any existing remarks if any
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDocView_AudioRemark_DeleteButton().Enabled();
				}
			}), Input.wait30);
			getDocView_AudioRemark_DeleteButton().Click();

			// click on yes button
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDocview_ButtonYes().Visible();
				}
			}), Input.wait30);
			getDocview_ButtonYes().Click();

			base.VerifySuccessMessage("Record Deleted Successfully");
			Thread.sleep(10000);
		} catch (Exception e) {
			System.out.println("No Remarks exist'");
			UtilityLog.info("No Remarks exist'");
		}

		// click on + icon to add remarks
		getAdvancedSearchAudioRemarkPlusIcon().Click();

		// Get Audio duration start and End time first
		String Audiostarttimeremark = getAdvancedSearchAudioRemarkTime().GetAttribute("value");
		System.out.println(Audiostarttimeremark);
		UtilityLog.info(Audiostarttimeremark);
		DateFormat df = new SimpleDateFormat("HH:mm");
		// Get Audio duration start and End time first
		String Audiostarttime = getDocview_Audio_StartTime().getText();
		System.out.println(Audiostarttime);
		UtilityLog.info(Audiostarttime);

		Date d = df.parse(Audiostarttime);
		Calendar cal = Calendar.getInstance();
		d = df.parse(Audiostarttimeremark);
		cal.setTime(d);
		cal.add(Calendar.MINUTE, 2);
		String newTime2 = df.format(cal.getTime());
		System.out.println(newTime2);
		UtilityLog.info(newTime2);

		// Enter time in remarks field
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAdvancedSearchAudioRemarkTime().Visible();
			}
		}), Input.wait30);
		getAdvancedSearchAudioRemarkTime().SendKeys(newTime2);

		// Enter text in field
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_RemarkTextField().Visible();
			}
		}), Input.wait30);
		getDocView_RemarkTextField().SendKeys(remark);

		// click on save button
		getDocView_AudioRemark_SaveButton().Click();

		// verify success message
		base.VerifySuccessMessage("Record added Successfully");

	}

	public void audioReduction() throws InterruptedException, ParseException {
		// adding redactions
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocview_RedactionsTab().Visible();
			}
		}), Input.wait30);
		getDocview_RedactionsTab().waitAndClick(30);

		try {
			// Delete any existing redaction if any
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDocview_Redactionstags_Delete().Visible();
				}
			}), Input.wait30);
			getDocview_Redactionstags_Delete().Click();

			// click on No button
			/*
			 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
			 * getDocview_ButtonNO().Visible() ;}}),Input.wait30);
			 * getDocview_ButtonNO().Click();
			 */
			// click on yes button
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDocview_ButtonYes().Visible();
				}
			}), Input.wait30);
			getDocview_ButtonYes().waitAndClick(10);

			base.VerifySuccessMessage("Record Deleted Successfully");
			base.CloseSuccessMsgpopup();
		} catch (Exception e) {
			System.out.println("No Redactions exist'");
			UtilityLog.info("No Redactions exist'");
		}

		// click on + icon to add redactions
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocview_RedactionsTab_Add().Visible();
			}
		}), Input.wait30);
		getDocview_RedactionsTab_Add().Click();

		// Get Audio duration start and End time first
		String Audiostarttime = getDocview_Audio_StartTime().getText();
		System.out.println(Audiostarttime);
		UtilityLog.info(Audiostarttime);
		String Audioendtime = getDocview_Audio_EndTime().getText();
		System.out.println(Audioendtime);
		UtilityLog.info(Audioendtime);

		DateFormat df = new SimpleDateFormat("HH:mm");
		Date d = df.parse(Audiostarttime);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.MINUTE, 2);
		String newTime = df.format(cal.getTime());
		System.out.println(newTime);
		UtilityLog.info(newTime);

		cal.add(Calendar.MINUTE, 1);
		String newTime1 = df.format(cal.getTime());
		System.out.println(newTime1);
		UtilityLog.info(newTime1);

		// Enter time in start field
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocview_AddRedactions_StartTime().Visible();
			}
		}), Input.wait30);
		getDocview_AddRedactions_StartTime().SendKeys(newTime);

		// Enter time in end field
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocview_AddRedactions_EndTime().Visible();
			}
		}), Input.wait30);
		getDocview_AddRedactions_EndTime().SendKeys(newTime1);

		// select redaction tags
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocview_AudioRedactions().Visible();
			}
		}), Input.wait30);
		getDocview_AudioRedactions().selectFromDropdown().selectByIndex(1);
		Thread.sleep(2000);
		// getDocview_Redactionstags_Value().waitAndClick(10);

		// click on save button
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSaveButton().Visible();
			}
		}), Input.wait30);
		// driver.scrollingToBottomofAPage();
		getSaveButton().waitAndClick(30);

		// verify success message
		base.VerifySuccessMessage("Record added Successfully");
		Thread.sleep(7000);
		System.out.println("Redaction added successfully");
		UtilityLog.info("Redaction added successfully");
	}

	public void audioComment(String comments) {
		// adding comments
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAudioComment().Visible();
			}
		}), Input.wait30);
		getAudioComment().Clear();
		getAudioComment().SendKeys(comments);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocumentViewer_DocView_SaveBtn().Visible();
			}
		}), Input.wait30);
		getDocumentViewer_DocView_SaveBtn().waitAndClick(10);

	}

	public void audioDownload() {
		// downlaod
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocview_Audio_Downloadbutton().Visible();
			}
		}), Input.wait30);
		getDocview_Audio_Downloadbutton().waitAndClick(30);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocview_Audio_DownloadFile().Visible();
			}
		}), Input.wait30);
		getDocview_Audio_DownloadFile().Click();

	}

	public void ViewTextTab() {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_TextTab().Visible();
			}
		}), Input.wait30);
		getDocView_TextTab().waitAndClick(15);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Textarea().Visible();
			}
		}), Input.wait30);
		String bodyText = getDocView_Textarea().getText();
		getDocView_Textarea().Displayed();
		System.out.println(bodyText);

		getDocView_NumTextBox().SendKeys("5");
		getDocView_NumTextBox().Enter();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_MiniDocListIds(5).Visible();
			}
		}), Input.wait30);
		Assert.assertTrue(getDocView_MiniDocListIds(5).Displayed());

	}

	public void VerifyFolderTab(final String folderName, int rowno) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_MiniDoc_SelectRow(rowno).Visible();
			}
		}), Input.wait60);
		getDocView_MiniDoc_SelectRow(rowno).waitAndClick(15);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Mini_ActionButton().Visible();
			}
		}), Input.wait30);
		getDocView_Mini_ActionButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Mini_FolderAction().Visible();
			}
		}), Input.wait30);
		getDocView_Mini_FolderAction().Click();

		sp.getSelectFolderExisting(folderName).waitAndClick(15);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sp.getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		sp.getContinueButton().Click();

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sp.getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		sp.getFinalizeButton().Click();

		base.VerifySuccessMessage("Records saved successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		System.out.println("Bulk folder is done, folder is : " + folderName);

		getDocView_MiniDocListIds(rowno).waitAndClick(15);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_FolderTab().Visible();
			}
		}), Input.wait30);
		getDocView_FolderTab().waitAndClick(30);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_FolderTab_Expand().Visible();
			}
		}), Input.wait30);
		getDocView_FolderTab_Expand().Click();
		Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getFolderFromList(folderName).Displayed();
			}
		}), Input.wait30);
		Assert.assertTrue(getFolderFromList(folderName).Displayed());

	}

	public void VerifyPersistentHit(String searchString) throws InterruptedException {

		String hitscount = getPersistentHit(searchString);
		System.out.println("Hits are:" + hitscount);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_HitsTogglePanel().Displayed();
			}
		}), Input.wait30);
		Assert.assertEquals("Hide Terms with 0 hits:", getDocView_ToogleLabel().getText());

		getDocView_Persistent_PrevHit().Displayed();
		getDocView_Persistent_NextHit().Displayed();
		getDocView_Persistent_NextHit().waitAndClick(10);
		getDocView_Persistent_PrevHit().waitAndClick(10);

		System.out.println(getDocView_Persistent_NextHit().GetAttribute("key").toString());

	}

	public void VerifyAudiopersistentHit(String searchString) throws InterruptedException {

		String hitscount = getAudioPersistentHit(searchString);
		System.out.println("Hits are:" + hitscount);

		System.out.println(getDocView_Audio_Hit().getText().toString());

	}

	public void NonAudioRemarkAddEditDeletebyReviewer(String remark) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNonAudioRemarkBtn().Visible();
			}
		}), Input.wait60);
		getNonAudioRemarkBtn().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectRemarkDocArea().Visible();
			}
		}), Input.wait30);
		System.out.println(getSelectRemarkDocArea().getText());
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		getSelectRemarkDocArea().Click();

		driver.scrollPageToTop();
		getAddRemarkbtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getRemarkTextArea().Visible();
			}
		}), Input.wait30);
		getRemarkTextArea().SendKeys(remark);
		getSaveRemark().Click();

		// click on next button
		getDocView_Next().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectRemarkDocArea().Visible();
			}
		}), Input.wait30);
		System.out.println(getSelectRemarkDocArea().getText());
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		getSelectRemarkDocArea().Click();

		driver.scrollPageToTop();
		getAddRemarkbtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getRemarkTextArea().Visible();
			}
		}), Input.wait30);
		getRemarkTextArea().SendKeys(remark);
		getSaveRemark().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getRemarkEditIcon().Visible();
			}
		}), Input.wait30);
		getRemarkEditIcon().Click();
		getRemarkTextArea().SendKeys(remark + "+Test1+");
		getSaveRemark().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getRemarkDeletetIcon().Visible();
			}
		}), Input.wait30);
		getRemarkDeletetIcon().Click();

		base.getPopupYesBtn();
	}

	public void NonAudioAnnotation() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_AnnotateIcon().Displayed();
			}
		}), Input.wait30);
		Thread.sleep(3000);
		getDocView_AnnotateIcon().waitAndClick(10);
		Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Annotate_ThisPage().Displayed();
			}
		}), Input.wait30);
		getDocView_Annotate_ThisPage().Click();
		Thread.sleep(3000);

		// Verify Yellow color of highlighted text
		WebElement activeElement = driver.switchTo().activeElement();
		String HighlightedColor = driver.FindElementByCssSelector("rect[style*='#FFFF00']").GetCssValue("fill");
		System.out.println(HighlightedColor);

		Assert.assertEquals(HighlightedColor, "rgb(255, 255, 0)");

		/*
		 * if (HighlightedColor.equalsIgnoreCase("rgb(255, 255, 0)")) {
		 * 
		 * System.out.println("Annotation_Highlight Yellow color displayed properly"); }
		 * else {
		 * System.out.println("Annotation_Highlight Yellow color not displayed properly"
		 * ); }
		 */

		// delete annotation
		getDocView_Annotate_TextArea().waitAndClick(10);
		driver.scrollPageToTop();

		getDocView_Annotate_DeleteIcon().waitAndClick(5);
		base.VerifySuccessMessage("Annotation Removed successfully.");

	}

	public void nonAudioPageRedaction(String redactiontag) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_RedactIcon().Displayed();
			}
		}), Input.wait30);
		getDocView_RedactIcon().waitAndClick(5);
		Thread.sleep(3000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_RedactThisPage().Displayed();
			}
		}), Input.wait30);
		getDocView_RedactThisPage().Click();
		Thread.sleep(3000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_SelectReductionLabel().Displayed();
			}
		}), Input.wait30);
		getDocView_SelectReductionLabel().selectFromDropdown().selectByVisibleText(redactiontag);

		getDocView_SaveReductionNew().waitAndClick(5);

		base.VerifySuccessMessage("Redaction tags saved successfully.");

		// Verify color should change to Red of this page redaction icon
		System.out.println("BG color:::" + getDocView_Redact_ThisPage().GetCssValue("color"));
		// background-color

		String HighlightedColor = getDocView_Redact_ThisPage().GetCssValue("color");
		System.out.println(HighlightedColor);
		UtilityLog.info(HighlightedColor);

		Assert.assertEquals(HighlightedColor, "rgba(230, 70, 52, 1)");
	}

	public void NonAudioRedactionDelete(String redactiontag) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_RedactIcon().Displayed();
			}
		}), Input.wait30);
		getDocView_RedactIcon().Click();
		Thread.sleep(3000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_RedactThisPage().Displayed();
			}
		}), Input.wait30);
		getDocView_RedactThisPage().Click();
		Thread.sleep(3000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_SelectReductionLabel().Displayed();
			}
		}), Input.wait30);
		getDocView_SelectReductionLabel().selectFromDropdown().selectByVisibleText(redactiontag);

		getDocView_SaveReduction().waitAndClick(5);

		base.VerifySuccessMessage("Redaction tags saved successfully.");

		// delete redaction
		getDocView_Redact_TextArea().waitAndClick(10);

		getDocView_Annotate_DeleteIcon().waitAndClick(5);
		base.VerifySuccessMessage("Redaction Removed successfully.");

	}

	public void VerifyTabswhenAllprefEnabled() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_RedactIcon().Displayed();
			}
		}), Input.wait30);
		Assert.assertTrue(getDocView_RedactIcon().Displayed());

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_AnnotateIcon().Displayed();
			}
		}), Input.wait30);
		Assert.assertTrue(getDocView_AnnotateIcon().Displayed());

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getPersistantHitEyeIcon().Displayed();
			}
		}), Input.wait30);
		Assert.assertTrue(getPersistantHitEyeIcon().Displayed());

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_AddRemarkIcon().Displayed();
			}
		}), Input.wait30);
		Assert.assertTrue(getDocView_AddRemarkIcon().Displayed());

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_DefaultViewTab().Displayed();
			}
		}), Input.wait30);
		Assert.assertTrue(getDocView_DefaultViewTab().Displayed());

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_AssignmentProgress().Displayed();
			}
		}), Input.wait30);
		Assert.assertEquals(getDocView_AssignmentProgress().Exists(), getDocView_RedactIcon().Displayed());
		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_RedactIcon().Displayed() ;}}), Input.wait30);
		 * Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().
		 * Displayed());
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_RedactIcon().Displayed() ;}}), Input.wait30);
		 * Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().
		 * Displayed());
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_RedactIcon().Displayed() ;}}), Input.wait30);
		 * Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().
		 * Displayed());
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_RedactIcon().Displayed() ;}}), Input.wait30);
		 * Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().
		 * Displayed());
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_RedactIcon().Displayed() ;}}), Input.wait30);
		 * Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().
		 * Displayed());
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_RedactIcon().Displayed() ;}}), Input.wait30);
		 * Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().
		 * Displayed());
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_RedactIcon().Displayed() ;}}), Input.wait30);
		 * Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().
		 * Displayed());
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_RedactIcon().Displayed() ;}}), Input.wait30);
		 * Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().
		 * Displayed());
		 */
	}

	public void VerifyTabswhenAllprefDisabled() throws InterruptedException {

		try {
			getDocView_RedactIcon().Displayed();
			Assert.assertFalse(1 == 0);
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println(" 'getDocView_RedactIcon' is not displayed");
		}
		// Assert.assertFalse(getDocView_RedactIcon().Displayed());
		try {
			getDocView_AnnotateIcon().Displayed();
			Assert.assertFalse(1 == 0);
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println(" 'getDocView_AnnotateIcon' is not displayed");
		}

		// Assert.assertFalse(getDocView_AnnotateIcon().Displayed());
		try {
			getPersistantHitEyeIcon().Displayed();
			Assert.assertFalse(1 == 0);
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println(" 'getPersistantHitEyeIcon' is not displayed");
		}

		// Assert.assertFalse(getPersistantHitEyeIcon().Displayed());
		try {
			getDocView_AddRemarkIcon().Displayed();
			Assert.assertFalse(1 == 0);
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println(" 'getDocView_AddRemarkIcon' is not displayed");
		}

		// Assert.assertFalse(getDocView_AddRemarkIcon().Displayed());
		try {
			getDocView_DefaultViewTab().Displayed();
			Assert.assertFalse(1 == 0);
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println(" 'getDocView_DefaultViewTab' is not displayed");
		}

		// Assert.assertFalse(getDocView_DefaultViewTab().Displayed());

		try {
			getDocView_AssignmentProgress().Displayed();
			Assert.assertFalse(1 == 0);
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println(" 'getDocView_AssignmentProgress' is not displayed");
		}
		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_AssignmentProgress().Displayed() ;}}), Input.wait30);
		 * Assert.assertFalse(getDocView_AssignmentProgress().Exists());
		 */

		try {
			getDocView_AnalyticsPanel().Displayed();
			Assert.assertFalse(1 == 0);
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println(" 'getDocView_AnalyticsPanel' is not displayed");
		}

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_AnalyticsPanel().Displayed() ;}}), Input.wait30);
		 * Assert.assertEquals(getDocView_AnalyticsPanel().Exists(),
		 * getDocView_RedactIcon().Displayed()); /* driver.WaitUntil((new
		 * Callable<Boolean>() {public Boolean call(){return
		 * getDocView_RedactIcon().Displayed() ;}}), Input.wait30);
		 * Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().
		 * Displayed());
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_RedactIcon().Displayed() ;}}), Input.wait30);
		 * Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().
		 * Displayed());
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_RedactIcon().Displayed() ;}}), Input.wait30);
		 * Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().
		 * Displayed());
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_RedactIcon().Displayed() ;}}), Input.wait30);
		 * Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().
		 * Displayed());
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_RedactIcon().Displayed() ;}}), Input.wait30);
		 * Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().
		 * Displayed());
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_RedactIcon().Displayed() ;}}), Input.wait30);
		 * Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().
		 * Displayed());
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_RedactIcon().Displayed() ;}}), Input.wait30);
		 * Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().
		 * Displayed());
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_RedactIcon().Displayed() ;}}), Input.wait30);
		 * Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().
		 * Displayed());
		 */
	}

	public void AnalyticsCodeSameAs() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_EditMode().Displayed();
			}
		}), Input.wait60);
		getDocView_EditMode().waitAndClick(10);

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_HdrAnalytics().Displayed();
			}
		}), Input.wait30);
		getDocView_HdrAnalytics().Click();

		for (String winHandle : driver.getWebDriver().getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}

		getDocView_Analytics_FamilyTab().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Analytics_ChildWindow_FamilyTab_Firstdoc().Displayed();
			}
		}), Input.wait30);
		getDocView_Analytics_ChildWindow_FamilyTab_Firstdoc().Click();

		getDocView_ChildWindow_ActionButton().waitAndClick(10);

		getDocView_FamilyCodeSameAs().waitAndClick(10);

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);
		Thread.sleep(3000);

		getDocView_SaveWidgetButton().waitAndClick(10);

		driver.Navigate().refresh();
		alert = driver.switchTo().alert();
		alert.accept();

		getDocView_Analytics_FamilyTab().waitAndClick(20);
		geDocView_FamilyMem_CodeSameAsIcon().WaitUntilPresent().ScrollTo();

		Assert.assertTrue(geDocView_FamilyMem_CodeSameAsIcon().Displayed());

	}

	public void NearDupesCompwinodw() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_DocumentViewer_DocID().Displayed();
			}
		}), Input.wait30);
		String Docid = getDocView_DocumentViewer_DocID().getText();
		System.out.println(Docid);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Analytics_NearDupeTab().Displayed();
			}
		}), Input.wait30);
		getDocView_Analytics_NearDupeTab().waitAndClick(10);

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		getDocView_NearDupeIcon().waitAndClick(10);
		Thread.sleep(3000);

		for (String winHandle : driver.getWebDriver().getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}

		getDocView_NearDupe_DocID().WaitUntilPresent();
		String docidinchildwinodw = getDocView_NearDupe_DocID().getText().toString();
		System.out.println(docidinchildwinodw);

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);
		Thread.sleep(3000);

		Assert.assertEquals(Docid, docidinchildwinodw);

		getDocView_NearDupe_Selectdoc().waitAndClick(10);

		getDocView_ChildWindow_ActionButton().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		getDocView_NearViewInDoclist().waitAndClick(10);

	}

	public void AnalyticsActions() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Analytics_Threaded_FirstDoc().Displayed();
			}
		}), Input.wait60);
		getDocView_Analytics_Threaded_FirstDoc().waitAndClick(15);

		getDocView_ChildWindow_ActionButton().waitAndClick(10);

		getDocView_Analytics_Thread_CodeSameAs().waitAndClick(10);

		base.VerifySuccessMessage("Code same performed successfully.");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return geDocView_Threaded_CodeSameAsIcon().Displayed();
			}
		}), Input.wait30);
		Assert.assertTrue(geDocView_Threaded_CodeSameAsIcon().Displayed());

		getDocView_MiniDocListIds(1).waitAndClick(10);
		getDocView_MiniDoc_CodeSameIcon().ScrollTo();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_MiniDocListIds(1).Displayed();
			}
		}), Input.wait30);
		Assert.assertTrue(getDocView_MiniDoc_CodeSameIcon().Displayed());

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_MiniDoc_SelectRow(1).Displayed();
			}
		}), Input.wait30);
		getDocView_MiniDoc_SelectRow(1).waitAndClick(10);

		getDocView_Mini_ActionButton().waitAndClick(10);

		getDocView__ChildWindow_Mini_RemoveCodeSameAs().waitAndClick(10);

		base.VerifySuccessMessage("Code Same has been successfully removed");
		base.CloseSuccessMsgpopup();
		Thread.sleep(2000);

		try {
			geDocView_Threaded_CodeSameAsIcon().Displayed();
			Assert.assertFalse(1 == 0);
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println(" 'DocView_Threaded_CodeSameAsIcon' is not displayed");
		}

		try {
			getDocView_MiniDoc_CodeSameIcon().Displayed();
			Assert.assertFalse(1 == 0);
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println(" 'DocView_MiniDoc_CodeSameAsIcon' is not displayed");
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddComment1().Visible();
			}
		}), Input.wait30);
		getAddComment1().SendKeys("Test Comment");

		// radio buttons and check boxes
		try {
			driver.getWebDriver().findElement(By.xpath("(//*[@id='0_radiogroup']/div[2]/div/label)[1]")).click();
			driver.getWebDriver().findElement(By.xpath("(//*[@id='0_radiogroup']/div[2]/div/label)[2]")).click();
			// Thread.sleep(3000);
			// driver.getWebDriver().findElement(By.xpath("(//*[@id='0_checkgroup']/div[1]/div/label)[2]")).click();
		} catch (Exception e) {
			// TODO: handle exception
		}
		getSaveDoc().waitAndClick(30);

		base.VerifySuccessMessage("Document saved successfully");
		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_Analytics_Threaded().Displayed() ;}}), Input.wait30);
		 * getDocView_Analytics_Threaded().Displayed();
		 */

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_EditMode().Displayed();
			}
		}), Input.wait30);
		getDocView_EditMode().Click();

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_HdrAnalytics().Displayed();
			}
		}), Input.wait30);
		getDocView_HdrAnalytics().Click();

		for (String winHandle : driver.getWebDriver().getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}

		getDocView_ThreadedChild_Selectalldoc().waitAndClick(10);

		getDocView_ChildWindow_ActionButton().waitAndClick(10);

		getDocView_Analytics_Thread_Folder().waitAndClick(10);
		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);
		Thread.sleep(3000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sp.getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		int actualcount = Integer.parseInt(sp.getContinueCount().getText());
		System.out.println(actualcount);
		// Assert.assertEquals(actualcount,9);

		base.getCancelbutton().waitAndClick(10);
		try {
			Thread.sleep(3000);
			alert = driver.switchTo().alert();
			alert.accept();
		} catch (Exception e) {
			e.printStackTrace();
		}

		getSaveDoc().waitAndClick(30);
		driver.Navigate().refresh();

	}

	public void MiniDoclistFolderAction(String foldername) throws InterruptedException, AWTException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_EditMode().Displayed();
			}
		}), Input.wait30);
		getDocView_EditMode().waitAndClick(10);

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_HdrAnalytics().Displayed();
			}
		}), Input.wait30);
		getDocView_HdrMinDoc().Click();

		for (String winHandle1 : driver.getWebDriver().getWindowHandles()) {
			driver.switchTo().window(winHandle1);
		}

		for (int i = 1; i <= 2; i++) {

			getDocView_MiniDoc_ChildWindow_Selectdoc(i).waitAndClick(10);
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Mini_ActionButton().Displayed();
			}
		}), Input.wait30);
		getDocView_Mini_ActionButton().Click();

		getDocView__ChildWindow_Mini_FolderAction().waitAndClick(10);

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);

		// sp.BulkActions_Folder(foldername);
		sp.getSelectFolderExisting(foldername).waitAndClick(5);
		// sp.BulkActions_Folder(foldername);
		sp.getSelectFolderExisting(foldername).waitAndClick(5);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sp.getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		sp.getContinueButton().Click();

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sp.getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		sp.getFinalizeButton().Click();

		base.VerifySuccessMessage("Records saved successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		System.out.println("Bulk folder is done, folder is : " + foldername);

		driver.Navigate().refresh();
		Thread.sleep(3000);

		alert = driver.switchTo().alert();
		alert.accept();
		driver.Navigate().refresh();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_MiniDocList().Displayed();
			}
		}), Input.wait30);
		Assert.assertTrue(getDocView_MiniDocList().Visible() && getDocView_MiniDocList().Enabled());
	}

	public void MiniDoclistNewFolderCreation() {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_EditMode().Displayed();
			}
		}), Input.wait30);
		getDocView_EditMode().waitAndClick(3);

		for (int i = 1; i <= 2; i++) {

			getDocView_MiniDoc_ChildWindow_Selectdoc(i).waitAndClick(10);
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Mini_ActionButton().Displayed();
			}
		}), Input.wait30);
		getDocView_Mini_ActionButton().Click();

		getDocView__ChildWindow_Mini_FolderAction().waitAndClick(10);

	}

	/**
	 * @author Mohan Created date: NA Modified date: NA Modified by: Mohan Test Case
	 *         RPMXCON-51733
	 * 
	 */
	public void verifyCodeAsSameInMiniDocList() {

		driver.scrollPageToTop();
		for (int i = 1; i <= 2; i++) {

			getDocView_MiniDoc_ChildWindow_Selectdoc(i).waitAndClick(10);
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Mini_ActionButton().Displayed();
			}
		}), Input.wait30);
		getDocView_Mini_ActionButton().Click();

		softAssertion.assertFalse(getDocView__ChildWindow_Mini_CodeAsSameAction().Displayed());
		base = new BaseClass(driver);
		base.passedStep("Code as Same is not Visible in the Mini Doc list");

	}

	public void AnalyticsThreaded_Actions() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_ThreadedChild_Selectalldoc().Displayed();
			}
		}), Input.wait30);
		try {
			getAddRemarkbtn().Displayed();
			Assert.assertFalse(1 == 0);
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("Remark Button not displayed for PA");
		}

		getDocView_DocId("ID00001059").waitAndClick(20);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_ThreadedChild_Selectalldoc().Displayed();
			}
		}), Input.wait30);
		getDocView_ThreadedChild_Selectalldoc().waitAndClick(10);

		// getDocView_Analytics_LoadMoreButton().waitAndClick(10);

		// getDocView_ThreadedChild_Selectalldoc().waitAndClick(10);

		getDocView_ChildWindow_ActionButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Analytics_Thread_CodeSameAs().Displayed();
			}
		}), Input.wait30);
		Assert.assertFalse(getDocView_Analytics_Thread_CodeSameAs().Displayed());
		Assert.assertFalse(getDocView_Analytics_Thread_RemoveCodeSameAs().Displayed());
	}

	public void AnalyticsThreadedNoDocument() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Analytics_Threaded().Displayed();
			}
		}), Input.wait30);
		Assert.assertEquals("Your query returned no data", getDocView_Analytics_Threaded().getText());

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_EditMode().Displayed();
			}
		}), Input.wait30);
		getDocView_EditMode().waitAndClick(10);

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_HdrAnalytics().Displayed();
			}
		}), Input.wait30);
		getDocView_HdrAnalytics().Click();

		for (String winHandle : driver.getWebDriver().getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Analytics_Threaded().Displayed();
			}
		}), Input.wait30);
		Assert.assertEquals("Your query returned no data", getDocView_Analytics_Threaded().getText());

		driver.getWebDriver().close();

		driver.switchTo().window(parentWindowID);
		Thread.sleep(3000);
	}

	public void audiodocthruhistorydropdown() {
		// adding comments
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_NumTextBox().Visible();
			}
		}), Input.wait30);
		getDocView_NumTextBox().SendKeys("2");
		getDocView_NumTextBox().Enter();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_HistoryButton().Visible();
			}
		}), Input.wait30);
		getDocView_HistoryButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Historydropdown().Visible();
			}
		}), Input.wait30);
		getDocView_Historydropdown().waitAndClick(10);

		Assert.assertTrue(getDocView_TextFileType().Enabled());
		Assert.assertEquals(getDocView_TextFileType().getText().toString(), "MP3 VERSION");
	}

	public void getDocView_AnalyticsEmail() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Metadata_EmailInclusiveScore().Displayed();
			}
		}), Input.wait60);
		String score = getDocView_Metadata_EmailInclusiveScore().getText();
		System.out.println(score);

		String reason = getDocView_Metadata_EmailInclusiveReason().getText();
		System.out.println(reason);

		/*
		 * if(getDocView_Metadata_EmailInclusive().getText().isEmpty()) {
		 * System.out.println("Email Inclusive reason is not available"); }
		 */
		/*
		 * else { String icontext = getDocView_Analytics_Threadedicon().getText();
		 * System.out.println(icontext); }
		 */
		/*
		 * Actions action = new Actions(driver.getWebDriver());
		 * action.moveToElement(getDocView_Analytics_Threadedicon().getWebElement()).
		 * build().perform(); //
		 * action.clickAndHold(getDocView_Analytics_Threadedicon().getWebElement()).
		 * perform(); String text =
		 * getDocView_Analytics_Threadedicon().GetAttribute("title");
		 * System.out.println(text);
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_Analytics_Thread_CodeSameAs().Displayed() ;}}), Input.wait30);
		 * Assert.assertFalse(getDocView_Analytics_Thread_CodeSameAs().Displayed());
		 * Assert.assertFalse(getDocView_Analytics_Thread_RemoveCodeSameAs().Displayed()
		 * );
		 */ }

	public void VerifyTooltipsforallIconsGerman() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_RedactIcon().Displayed();
			}
		}), Input.wait30);
		softAssertion.assertTrue(getDocView_RedactIcon().Displayed());
		base.GetandVerifyTooltip(getDocView_RedactIcon(), "DE-Redact");

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_AnnotateIcon().Enabled() ;}}), Input.wait30);
		 */
		softAssertion.assertTrue(getDocView_AnnotateIcon().Displayed());
		base.GetandVerifyTooltip(getDocView_AnnotateIcon(), "DE-Annotate");

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getPersistantHitEyeIcon().Displayed() ;}}), Input.wait30);
		 */
		softAssertion.assertTrue(getPersistantHitEyeIcon().Displayed());
		base.GetandVerifyTooltip(getPersistantHitEyeIcon(), "DE-Persistent Highlighting");

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_AddRemarkIcon().Displayed() ;}}), Input.wait30);
		 */
		softAssertion.assertTrue(getDocView_AddRemarkIcon().Displayed());
		base.GetandVerifyTooltip(getDocView_AddRemarkIcon(), "DE-Reviewer Remarks");
		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_Print().Displayed() ;}}), Input.wait30);
		 */
		softAssertion.assertTrue(getDocView_Print().Displayed());
		base.GetandVerifyTooltip(getDocView_Print(), "DE-Print");

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_IconDownload().Displayed() ;}}), Input.wait30);
		 */
		softAssertion.assertTrue(getDocView_IconDownload().Displayed());
		base.GetandVerifyTooltip(getDocView_IconDownload(), "DE-Download");

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_Zoomout().Displayed() ;}}), Input.wait30);
		 */
		softAssertion.assertTrue(getDocView_Zoomout().Displayed());
		base.GetandVerifyTooltip(getDocView_Zoomout(), "DE-Zoom Out");

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_Slider().Displayed() ;}}), Input.wait30);
		 */
		softAssertion.assertTrue(getDocView_Slider().Displayed());
		base.GetandVerifyTooltip(getDocView_Slider(), "DE-Zoom");

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_ZoomIn().Displayed() ;}}), Input.wait30);
		 */
		softAssertion.assertTrue(getDocView_ZoomIn().Displayed());
		base.GetandVerifyTooltip(getDocView_ZoomIn(), "DE-Zoom In");

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_ResetZoom().Displayed() ;}}), Input.wait30);
		 */
		softAssertion.assertTrue(getDocView_ResetZoom().Displayed());
		base.GetandVerifyTooltip(getDocView_ResetZoom(), "DE-Reset Zoom");
		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_Rotateright().Displayed() ;}}), Input.wait30);
		 */
		softAssertion.assertTrue(getDocView_Rotateright().Displayed());
		base.GetandVerifyTooltip(getDocView_Rotateright(), "DE-Rotate Clockwise");
		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_Rotateleft().Displayed() ;}}), Input.wait30);
		 */
		softAssertion.assertTrue(getDocView_Rotateleft().Displayed());
		base.GetandVerifyTooltip(getDocView_Rotateleft(), "DE-Rotate Anticlockwise");
		/* s */
		softAssertion.assertTrue(getDocView_SearchButton().Displayed());
		base.GetandVerifyTooltip(getDocView_SearchButton(), "DE-Search");

		softAssertion.assertAll();

	}

	public void VerifyTooltipsforallIconsEnglish() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_RedactIcon().Displayed();
			}
		}), Input.wait30);
		softAssertion.assertTrue(getDocView_RedactIcon().Displayed());
		base.GetandVerifyTooltip(getDocView_RedactIcon(), "Redact");

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_AnnotateIcon().Enabled() ;}}), Input.wait30);
		 */ softAssertion.assertTrue(getDocView_AnnotateIcon().Displayed());
		base.GetandVerifyTooltip(getDocView_AnnotateIcon(), "Annotate");

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getPersistantHitEyeIcon().Displayed() ;}}), Input.wait30);
		 */ softAssertion.assertTrue(getPersistantHitEyeIcon().Displayed());
		base.GetandVerifyTooltip(getPersistantHitEyeIcon(), "Persistent Highlighting");

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_AddRemarkIcon().Displayed() ;}}), Input.wait30);
		 */softAssertion.assertTrue(getDocView_AddRemarkIcon().Displayed());
		base.GetandVerifyTooltip(getDocView_AddRemarkIcon(), "Reviewer Remarks");

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_Print().Displayed() ;}}), Input.wait30);
		 */
		softAssertion.assertTrue(getDocView_Print().Displayed());
		base.GetandVerifyTooltip(getDocView_Print(), "Print");

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_IconDownload().Displayed() ;}}), Input.wait30);
		 */
		softAssertion.assertTrue(getDocView_IconDownload().Displayed());
		base.GetandVerifyTooltip(getDocView_IconDownload(), "Download");

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_Zoomout().Displayed() ;}}), Input.wait30);
		 */
		softAssertion.assertTrue(getDocView_Zoomout().Displayed());
		base.GetandVerifyTooltip(getDocView_Zoomout(), "Zoom Out");

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_Slider().Displayed() ;}}), Input.wait30);
		 */
		softAssertion.assertTrue(getDocView_Slider().Displayed());
		base.GetandVerifyTooltip(getDocView_Slider(), "Zoom");

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_ZoomIn().Displayed() ;}}), Input.wait30);
		 */
		softAssertion.assertTrue(getDocView_ZoomIn().Displayed());
		base.GetandVerifyTooltip(getDocView_ZoomIn(), "Zoom In");

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_ResetZoom().Displayed() ;}}), Input.wait30);
		 */
		softAssertion.assertTrue(getDocView_ResetZoom().Displayed());
		base.GetandVerifyTooltip(getDocView_ResetZoom(), "Reset Zoom");

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_Rotateright().Displayed() ;}}), Input.wait30);
		 */
		softAssertion.assertTrue(getDocView_Rotateright().Displayed());
		base.GetandVerifyTooltip(getDocView_Rotateright(), "Rotate Clockwise");

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_Rotateleft().Displayed() ;}}), Input.wait30);
		 */
		softAssertion.assertTrue(getDocView_Rotateleft().Displayed());
		base.GetandVerifyTooltip(getDocView_Rotateleft(), "Rotate Anticlockwise");

		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getDocView_SearchButton().Displayed() ;}}), Input.wait30);
		 */
		softAssertion.assertTrue(getDocView_SearchButton().Displayed());
		base.GetandVerifyTooltip(getDocView_SearchButton(), "Search");

		softAssertion.assertAll();

	}

	public void Analytics_FamilyActions(String folderName) throws InterruptedException, AWTException {
		getDocView_Analytics_FamilyTab().waitAndClick(30);

		getDocView_Analytics_ChildWindow_FamilyTab_Firstdoc().waitAndClick(10);

		getDocView_ChildWindow_ActionButton().waitAndClick(10);

		getDocView_FamilyBulkFolder().waitAndClick(10);

		sp.getSelectFolderExisting(folderName).waitAndClick(5);

		sp.BulkActions_Folder(folderName);
		// sp.getSelectFolderExisting(folderName).waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sp.getContinueCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		sp.getContinueButton().Click();

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sp.getFinalCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait60);
		sp.getFinalizeButton().Click();

		base.VerifySuccessMessage("Records saved successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		System.out.println("Bulk folder is done, folder is : " + folderName);

		// View in Doclist
		getDocView_Analytics_ChildWindow_FamilyTab_Firstdoc().waitAndClick(10);

		getDocView_ChildWindow_ActionButton().waitAndClick(10);

		getDocView_FamilyViewInDoclist().waitAndClick(10);

	}

	/**
	 * @author Mohan.Venugopal Modified On: 11/11/2021
	 * @description : To create Folder for Family Member docs
	 * @throws Exception
	 */
	public void Analytics_FamilyActionsFolderCreation() throws Exception {
		driver.waitForPageToBeReady();
		JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
		driver.waitForPageToBeReady();
		Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
		je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
//		getDocView_Analytics_FamilyTab().ScrollTo();
		getDocView_Analytics_FamilyTab().waitAndClick(10);

		base.waitForElement(getDocView_Analytics_ChildWindow_FamilyTab_Firstdoc());
		getDocView_Analytics_ChildWindow_FamilyTab_Firstdoc().waitAndClick(10);

		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(10);

		base.waitForElement(getDocView_FamilyBulkFolder());
		getDocView_FamilyBulkFolder().waitAndClick(10);

		base.passedStep("Folder selection popup opened successfully");
	}

	public void NavigationInDocView() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Last().Displayed();
			}
		}), Input.wait60);
		getDocView_Last().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_NumTextBox().Enabled();
			}
		}), Input.wait30);
		String nointextbox = getDocView_Last().getText();
		System.out.println(nointextbox);
		Assert.assertEquals(nointextbox, 50);

		getDocView_First().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_NumTextBox().Enabled();
			}
		}), Input.wait30);
		String nointextbox1 = getDocView_Last().getText();
		System.out.println(nointextbox1);
		Assert.assertEquals(nointextbox1, 1);
	}

	public void MiniDoclistConifgSortOrder() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_EditMode().Displayed();
			}
		}), Input.wait30);
		getDocView_EditMode().waitAndClick(10);

		String parentWindowID = driver.getWebDriver().getWindowHandle();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_HdrAnalytics().Displayed();
			}
		}), Input.wait30);
		getDocView_HdrMinDoc().Click();

		for (String winHandle1 : driver.getWebDriver().getWindowHandles()) {
			driver.switchTo().window(winHandle1);
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_ReviewModeText().Displayed();
			}
		}), Input.wait30);
		String sortmodetext = getDocView_ReviewModeText().getText();
		System.out.println(sortmodetext);
		Assert.assertEquals(sortmodetext, "You are reviewing docs in Optimized Sort mode");

		String expvalues[] = { "DOCID", "DOCFILETYPE", "FAMILYRELATIONSHIP", "FAMILYMEMBERCOUNT" };
		WebElement countoffields = driver
				.FindElementByXPath("//*[@id='SearchDataTable_wrapper']/div[3]/div[1]//table/thead/tr/th")
				.getWebElement();
		List<WebElement> allvalues = countoffields.findElements(By.tagName("th"));
		List<String> all = new ArrayList<String>();
		for (int j = 1; j <= allvalues.size(); j++) {
			System.out.println(all.add(allvalues.get(j).getText()));
			if (all.equals(expvalues)) {
				System.out.println("Pass");

			} else {
				System.out.println("FAIL");
			}
		}

		getDocView_ConfigMinidoclist().waitAndClick(10);

		driver.getWebDriver().close();
		driver.switchTo().window(parentWindowID);
		Thread.sleep(3000);

		List<WebElement> optimized = getDocView_Config_Selectedfield().FindWebElements();
		List<String> alloptimized = new ArrayList<String>();
		for (int k = 0; k < optimized.size(); k++) {
			System.out.println(alloptimized.add(optimized.get(k).getText()));
		}

		getDocView_ConfigMinidoclist_ManualSort().waitAndClick(10);
		List<WebElement> manual = getDocView_Config_Selectedfield().FindWebElements();
		System.out.println(manual);

		base.CompareListStrings(optimized, manual);

		getDocView_MiniDocFields_Remove().waitAndClick(10);

		getDocView_ConfigMinidoclist_OptimizedSort().waitAndClick(10);

		for (String expected : all) {
			if (alloptimized.contains(expected)) {
				System.out.println("Passed");
			} else {
				System.out.println("Failed");
			}
		}
		base.getCancelbutton().waitAndClick(10);

	}

	public void getDocView_Conecptual() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Analytics_liDocumentConceptualSimilarab().Displayed();
			}
		}), Input.wait30);
		getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_ConceptualDocs().Displayed();
			}
		}), Input.wait30);

		if (getDocView_ConceptualDocs().FindWebElements().size() > 1) {
			System.out.println("More than 1 count is displayed");
		} else {
			System.out.println("No docs displayed");
		}
	}

	public void VerifyKeywordHit(String searchString) throws InterruptedException {

		String hitscount = getPersistentHit(searchString);
		System.out.println("Hits are:" + hitscount);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_HitsTogglePanel().Displayed();
			}
		}), Input.wait30);
		Assert.assertEquals("Hide Terms with 0 hits:", getDocView_ToogleLabel().getText());

		getDocView_Persistent_PrevHit().Displayed();
		getDocView_Persistent_NextHit().Displayed();
		getDocView_Persistent_NextHit().waitAndClick(10);
		getDocView_Persistent_PrevHit().waitAndClick(10);

		System.out.println(getDocView_Persistent_NextHit().GetAttribute("key").toString());

	}

	public void redactbyrectangle(int off1, int off2, int cordinate, String redactiontag) throws InterruptedException {
		try {
			System.out.println(off1 + "...." + off2);
			Actions actions = new Actions(driver.getWebDriver());
			WebElement text = getDocView_Redactrec_textarea();

			actions.moveToElement(text, off1, off2).clickAndHold().moveByOffset(100, 10).release().perform();
		}

		catch (Exception e) {
			e.printStackTrace();
			System.out.println("Not able to select redacted area");
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_SelectReductionLabel().Displayed();
			}
		}), Input.wait30);
		getDocView_SelectReductionLabel().selectFromDropdown().selectByVisibleText(redactiontag);

		Thread.sleep(2000);
		getDocView_SaveReduction1(cordinate).waitAndClick(15);
		Thread.sleep(2000);
		base.VerifySuccessMessage("Redaction tags saved successfully.");
		Thread.sleep(2000);

	}

	public void editredaction(int off1, int off2, int cordinate, String redactiontag) throws InterruptedException {
		System.out.println(off1 + "...." + off2);
		Actions actions = new Actions(driver.getWebDriver());
		WebElement text = getDocView_Redactrec_textarea();

		actions.moveToElement(text, off1, off2).click().build().perform();
		Thread.sleep(1000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Redactedit_selectlabel().Displayed();
			}
		}), Input.wait30);
		getDocView_Redactedit_selectlabel().selectFromDropdown().selectByVisibleText(redactiontag);

		Thread.sleep(2000);
		getDocView_Redactedit_save().waitAndClick(15);
		Thread.sleep(2000);
		base.VerifySuccessMessage("Redaction tags saved successfully.");
		Thread.sleep(2000);

	}

	public void Deleteredaction(int off1, int off2, int cordinate, String redactiontag) throws InterruptedException {
		System.out.println(off1 + "...." + off2);
		Actions actions = new Actions(driver.getWebDriver());
		WebElement text = getDocView_Redactrec_textarea();

		actions.moveToElement(text, off1, off2).click().build().perform();
		Thread.sleep(1000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Annotate_DeleteIcon().Displayed();
			}
		}), Input.wait30);
		getDocView_Annotate_DeleteIcon().waitAndClick(15);
		Thread.sleep(2000);
		base.VerifySuccessMessage("Redaction Removed successfully.");
		Thread.sleep(2000);

	}

	public void paint(Graphics g) {
		g.fillRect(240, 240, 200, 100);
	}

	/**
	 * @author Jayanthi.ganesan Modified date-24/821
	 * @Description-this method will create single Page redaction
	 * @param-Redaction name
	 * 
	 */
	public void pageRedaction(String RedactionName) {
		base.waitForElement(getDocView_RedactIcon());
		getDocView_RedactIcon().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_RedactThisPage());
		getDocView_RedactThisPage().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_SelectReductionLabel());
		getDocView_SelectReductionLabel().selectFromDropdown().selectByVisibleText(RedactionName);
		getDocView_SelectReductionLabel().waitAndClick(3);
		System.out.println("selected redaction");
		getDocView_SaveReductionNew().Click();

	}

	/**
	 * @author Indium-Baskar date: 10/8/2021 Modified date: 23/8/2021
	 * @Description:verify coding form in doc view metadata field value in read only
	 *                     format INT Datatype
	 */
	public void verifyCodingFormDocViewINT(String projectFieldName) throws InterruptedException, AWTException {
		driver.waitForPageToBeReady();
		getReadOnlyTextBox(projectFieldName).WaitUntilPresent().ScrollTo();
//		base.waitForElement(getReadOnlyTextBox(projectFieldName));
		if (getReadOnlyTextBox(projectFieldName).Displayed() && getReadOnlyTextBox(projectFieldName).Enabled()) {
			UtilityLog.info("Custom meta data field read and write only format when created with INT datatype ");
			base.stepInfo("Parent window");
			base.failedStep("Custom meta data field read and write only format when created INT datatype ");

		} else {
			UtilityLog.info("Custom meta data field read only format when created with INT datatype ");
			base.stepInfo("Parent window");
			base.passedStep("Custom meta data field read only format when created with INT datatype ");
		}
		driver.scrollPageToTop();
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parentWindow = reusableDocView.switchTochildWindow();
		getReadOnlyTextBox(projectFieldName).WaitUntilPresent().ScrollTo();
		softAssertion.assertEquals(
				getReadOnlyTextBox(projectFieldName).Displayed() && getReadOnlyTextBox(projectFieldName).Enabled(),
				true, "Custom meta data field read only format when created with INT datatype ");
		if (getReadOnlyTextBox(projectFieldName).Displayed() && getReadOnlyTextBox(projectFieldName).Enabled()) {
			UtilityLog.info("Custom meta data field read and write only format when created with INT datatype");
			base.stepInfo("Child window");
			base.failedStep("Custom meta data field read and write only format when created with INT datatype ");

		} else {
			UtilityLog.info("Custom meta data field read only format when created with INT datatype ");
			base.stepInfo("Child window");
			base.passedStep("Custom meta data field read only format when created with INT datatype ");
		}
		reusableDocView.childWindowToParentWindowSwitching(parentWindow);
	}

	/**
	 * @author Indium-Baskar date: 10/8/2021 Modified date: 24/8/2021
	 * @Description:Verify coding form in doc view metadata field value in read only
	 *                     format NVARCHAR Datatype
	 */

	public void verifyCodingFormDocViewNVARCHAR(String projectFieldName) throws InterruptedException, AWTException {
		driver.waitForPageToBeReady();
		getReadOnlyTextBox(projectFieldName).WaitUntilPresent().ScrollTo();
		if (getReadOnlyTextBox(projectFieldName).Displayed() && getReadOnlyTextBox(projectFieldName).Enabled()) {
			UtilityLog.info("Custom meta data field read and write only format when created with NVARCHAR datatype ");
			base.stepInfo("Parent window");
			base.failedStep("Custom meta data field read and write only format when created NVARCHAR datatype ");

		} else {
			UtilityLog.info("Custom meta data field read only format when created with NVARCHAR datatype ");
			base.stepInfo("Parent window");
			base.passedStep("Custom meta data field read only format when created with NVARCHAR datatype ");
		}
		driver.scrollPageToTop();
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parentWindow = reusableDocView.switchTochildWindow();
		getReadOnlyTextBox(projectFieldName).WaitUntilPresent().ScrollTo();
		driver.scrollingToBottomofAPage();
		softAssertion.assertEquals(
				getReadOnlyTextBox(projectFieldName).Displayed() && getReadOnlyTextBox(projectFieldName).Enabled(),
				true, "Custom meta data field read only format when created with NVARCHAR datatype ");
		if (getReadOnlyTextBox(projectFieldName).Displayed() && getReadOnlyTextBox(projectFieldName).Enabled()) {
			UtilityLog.info("Custom meta data field read and write only format when created with NVARCHAR datatype");
			base.stepInfo("Child window");
			base.failedStep("Custom meta data field read and write only format when created with NVARCHAR datatype ");

		} else {
			UtilityLog.info("Custom meta data field read only format when created with NVARCHAR datatype ");
			base.stepInfo("Child window");
			base.passedStep("Custom meta data field read only format when created with NVARCHAR datatype ");
		}
		reusableDocView.childWindowToParentWindowSwitching(parentWindow);

	}

	/**
	 * @author Indium-Baskar date: 10/8/2021 Modified date: 24/8/2021 Modified
	 *         by:Baskar
	 * @Description:Doc view coding form stamp selection
	 */

	public void docViewCodingFormPanelStampSelection(String colour) throws AWTException {
		driver.waitForPageToBeReady();
		base.waitForElement(getResponsiveCheked());
		getResponsiveCheked().Click();
		base.waitForElement(getNonPrivilegeRadio());
		getNonPrivilegeRadio().Click();
		base.waitForElement(getDocument_CommentsTextBox());
		getDocument_CommentsTextBox().SendKeys("Review");
		driver.scrollPageToTop();
		base.waitForElement(getCodingFormStampButton());
		base.waitTillElemetToBeClickable(getCodingFormStampButton());
		getCodingFormStampButton().waitAndClick(5);
		base.waitForElement(getCodingStampTextBox());
		getCodingStampTextBox().SendKeys("NewColour");
		base.waitForElement(getDrp_StampColour());
		base.waitTillElemetToBeClickable(getDrp_StampColour());
		getDrp_StampColour().Click();
		base.waitTillElemetToBeClickable(getAssignedColour(colour));
		getAssignedColour(colour).Click();
		base.waitForElement(getCodingStampSaveBtn());
		base.waitTillElemetToBeClickable(getCodingStampSaveBtn());
		getCodingStampSaveBtn().waitAndClick(5);
		base.waitForElement(getCodingFormSaveBtn());
		base.waitTillElemetToBeClickable(getCodingFormSaveBtn());
		getCodingFormSaveBtn().waitAndClick(5);
		base.stepInfo("Document saved successfully");
	}

	/**
	 * @author Indium-Baskar date: 10/8/2021 Modified date: 24/8/2021 Modified
	 *         by:Baskar.
	 * @Description:Doc view mini doc list code same as to
	 */

	public void docViewMiniCodeSameAs() throws InterruptedException {
		base.waitForElement(getDocView_EditMode());
		base.waitTillElemetToBeClickable(getDocView_EditMode());
		getDocView_EditMode().waitAndClick(5);
		base.waitForElement(getDocView_HdrMinDoc());
		base.waitTillElemetToBeClickable(getDocView_HdrMinDoc());
		getDocView_HdrMinDoc().waitAndClick(5);
		String childWindow = miniDocListChildWindowopening();
		getDocView_MiniDocList().Visible();
		for (int i = 1; i <= 3; i++) {
			base.waitForElement(getDocView_MiniDoc_ChildWindow_Selectdoc(i));
			getDocView_MiniDoc_ChildWindow_Selectdoc(i).waitAndClick(5);
		}
		base.waitForElement(getDocView_Mini_ActionButton());
		base.waitTillElemetToBeClickable(getDocView_Mini_ActionButton());
		getDocView_Mini_ActionButton().waitAndClick(5);
		base.waitForElement(getDocView__ChildWindow_Mini_CodeSameAs());
		base.waitTillElemetToBeClickable(getDocView__ChildWindow_Mini_CodeSameAs());
		getDocView__ChildWindow_Mini_CodeSameAs().waitAndClick(5);
		childWindowToParentWindowSwitching(childWindow);
		driver.getWebDriver().navigate().refresh();
		driver.switchTo().alert().accept();
		geDocView_MiniList_CodeSameAsIcon().WaitUntilPresent().ScrollTo();
		softAssertion.assertTrue(geDocView_MiniList_CodeSameAsIcon().Displayed());
		base.waitForElement(getCodingFormSaveBtn());
		base.waitTillElemetToBeClickable(getCodingFormSaveBtn());
		getCodingFormSaveBtn().waitAndClick(5);
		base.waitForElement(getCodeSameAsLast());
		base.waitTillElemetToBeClickable(getCodeSameAsLast());
		getCodeSameAsLast().waitAndClick(5);
		base.CloseSuccessMsgpopup();
		driver.waitForPageToBeReady();
		base.stepInfo("Coded as per the coding form for the previous document");
		base.passedStep("Cursor moved to the next document after getting success message ");
		for (int i = 3; i <= 3; i++) {
			base.waitForElement(getClickDocviewID(i));
			getClickDocviewID(i).WaitUntilPresent().Click();
		}
		try {
			getDocument_CommentsTextBox().WaitUntilPresent().ScrollTo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String getAttribute = getDocument_CommentsTextBox().WaitUntilPresent().GetAttribute("value");
			softAssertion.assertEquals("Review", getAttribute);
			if (getAttribute.equals("Review")) {
				base.stepInfo("Document is saved with the last applied coding of  the document..");
				base.passedStep("Expected Message - code same as last scuessfully..");
			} else {
				base.failedStep("Expected Message - code NOT same as last scuessfully..");
			}
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			e.printStackTrace();
		}
		driver.waitForPageToBeReady();
		for (int i = 1; i <= 1; i++) {
			getClickDocviewID(i).WaitUntilPresent().waitAndClick(5);
		}
		try {
			base.waitForElement(getDocument_CommentsTextBox());
			getDocument_CommentsTextBox().SendKeys("codesameas");
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			e.printStackTrace();
		}
		driver.scrollPageToTop();
		base.waitForElement(getCodingFormSaveBtn());
		base.waitTillElemetToBeClickable(getCodingFormSaveBtn());
		getCodingFormSaveBtn().waitAndClick(5);
		base.stepInfo("After editing the coding form of the document");
		base.passedStep("Expected message - Document saved successfully ");
		base.waitForElement(getCodeSameAsLast());
		base.waitTillElemetToBeClickable(getCodeSameAsLast());
		getCodeSameAsLast().waitAndClick(10);
		base.stepInfo("Coded as per the coding form for the previous document");
		base.stepInfo("Document is saved with the last applied coding of  the document..");
	}

	/**
	 * @author Indium-Baskar date: 10/8/2021 Modified date: 24/8/2021 Modified
	 *         by:Baskar
	 * @Description:Verify user click code same as last after saving in Doc view
	 *                     coding form
	 */
	public void userClickCodeSameAsAfterSaving() {
		driver.waitForPageToBeReady();
		base.waitForElement(getDocument_CommentsTextBox());
		getDocument_CommentsTextBox().SendKeys("Verifycodesameaslast");
		base.waitForElement(getCodingFormSaveBtn());
		base.waitTillElemetToBeClickable(getCodingFormSaveBtn());
		getCodingFormSaveBtn().waitAndClick(5);
		base.VerifySuccessMessage("Document saved successfully");
		base.waitForElement(getCodeSameAsLast());
		base.waitTillElemetToBeClickable(getCodeSameAsLast());
		getCodeSameAsLast().waitAndClick(5);
		softAssertion.assertTrue(getCodeSameAsLast().Displayed() && getCodeSameAsLast().Enabled());
		if (getCodeSameAsLast().Displayed() && getCodeSameAsLast().Enabled()) {
			base.stepInfo("coded as per the previous document..");
			base.passedStep("Cursor has moved to the next document in mini doc list..");
		} else {
			base.failedStep("Failed to move next document in mini doc list..");
		}
//		driver.waitForPageToBeReady();
		base.waitForElement(getCodeSameAsLast());
		base.waitTillElemetToBeClickable(getCodeSameAsLast());
		getCodeSameAsLast().waitAndClick(10);
		base.stepInfo("Again click code same as last");
		base.CloseSuccessMsgpopup();
		softAssertion.assertTrue(getCodeSameAsLast().Displayed() && getCodeSameAsLast().Enabled());
		if (getCodeSameAsLast().Displayed() && getCodeSameAsLast().Enabled()) {
			base.stepInfo("coded as per the previous document..");
			base.passedStep("Cursor has moved to the next document in mini doc list..");
		} else {
			base.failedStep("Failed to move next document in mini doc list..");
		}
		for (int i = 1; i <= 1; i++) {
			getClickDocviewID(i).waitAndClick(5);
		}
		driver.waitForPageToBeReady();
		try {
			getDocument_CommentsTextBox().WaitUntilPresent().ScrollTo();
			String getAttribute = getDocument_CommentsTextBox().WaitUntilPresent().GetAttribute("value");
			softAssertion.assertEquals("Verifycodesameaslast", getAttribute);
			if (getAttribute.equals("Verifycodesameaslast")) {
				base.stepInfo("Document is saved with the last applied coding of  the document..");
				base.passedStep("Expected Message - code same as last scuessfully..");
			} else {
				base.failedStep("Expected Message - code NOT same as last scuessfully..");
			}
		} catch (org.openqa.selenium.StaleElementReferenceException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Indium-Baskar TODO:BUG IN APPLICATION. EXPECT FAILURE ON THIS TEST
	 *         CASE Description:Code Same as last in child window
	 */

	public void codingFormChildWindowCodeAsLast() throws AWTException, InterruptedException {
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_EditMode().Visible();
			}
		}), Input.wait30);
		getDocView_EditMode().waitAndClick(5);
		Robot robot = new Robot();
		robot.mouseMove(200, 200);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_HdrCoddingForm().Visible();
			}
		}), Input.wait30);
		getDocView_HdrCoddingForm().Click();
		String parentWindowID = driver.getWebDriver().getWindowHandle();
		Set<String> childWindowID = driver.getWebDriver().getWindowHandles();
		for (String codingFormChildWindow : childWindowID) {
			if (!parentWindowID.equals(codingFormChildWindow)) {
				driver.switchTo().window(codingFormChildWindow);
				driver.waitForPageToBeReady();
				driver.waitForPageToBeReady();
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getDocument_CommentsTextBox().Displayed() && getDocument_CommentsTextBox().Enabled();
					}
				}), Input.wait30);
				getDocument_CommentsTextBox().SendKeys("Verifycodingformchildwindow");
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getCodingFormSaveBtn().Visible();
					}
				}), Input.wait30);
				getCodingFormSaveBtn().Click();
				base.stepInfo("Document saved successfully");
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getCodeSameAsLast().Visible();
					}
				}), Input.wait30);
				getCodeSameAsLast().Click();
				softAssertion.assertFalse(
						getCodeSameAsLast().Displayed() && getCodeSameAsLast().getWebElement().isSelected());
				if (getCodeSameAsLast().Displayed() && getCodeSameAsLast().getWebElement().isSelected()) {
					base.stepInfo("coded as per the previous document..");
					base.passedStep("Cursor has moved to the next document in mini doc list..");
				} else {
					base.failedStep("Code same as last not clickable");
					base.failedStep("Failed to move next document in mini doc list..");
				}
				driver.waitForPageToBeReady();
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getCodeSameAsLast().Visible();
					}
				}), Input.wait30);
				getCodeSameAsLast().Click();
				base.stepInfo("Again click code same as last");
				softAssertion.assertTrue(
						getCodeSameAsLast().Displayed() && getCodeSameAsLast().getWebElement().isSelected());
				if (getCodeSameAsLast().Displayed() && getCodeSameAsLast().getWebElement().isSelected()) {
					base.stepInfo("coded as per the previous document..");
					base.passedStep("Cursor has moved to the next document in mini doc list..");
				} else {
					base.failedStep("Code same as last not clickable");
					base.failedStep("Failed to move next document in mini doc list..");
				}
				driver.close();
			}
		}
		driver.switchTo().window(parentWindowID);
		driver.waitForPageToBeReady();
		for (int i = 1; i <= 1; i++) {
			getClickDocviewID(i).Click();
			String getAttribute = getDocument_CommentsTextBox().WaitUntilPresent().GetAttribute("value");
			softAssertion.assertEquals("Verifycodingformchildwindow", getAttribute);
			if (getAttribute.equals("Verifycodingformchildwindow")) {
				base.passedStep("Document is saved with the last applied coding of  the document..");
				base.passedStep("Expected Message - code same as last scuessfully..");
			} else {
				base.failedStep("Expected Message - code NOT same as last scuessfully..");
			}
		}
	}

	/**
	 * @author Indium-Baskar date: 10/8/2021 Modified date: 24/8/2021
	 * @Description:Docview child window coding form code same as last should not be
	 *                      clickable
	 */

	public void childWindowCodeSameAsLastDisabled() throws AWTException {
		driver.waitForPageToBeReady();
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String childWindow = miniDocListChildWindowopening();
		base.waitForElement(getCodeSameAsLast());
		softAssertion.assertFalse(getCodeSameAsLast().getWebElement().isSelected());
		if (getCodeSameAsLast().getWebElement().isSelected()) {
			base.failedStep("Code same as last clickable in child window");
		} else {
			base.passedStep("Code same as last not clcikable in child window");
		}
		childWindowToParentWindowSwitching(childWindow);
		driver.getWebDriver().navigate().refresh();
		driver.switchTo().alert().accept();

	}

	/**
	 * @author Indium-Baskar date: 16/8/2021 Modified date: 28/8/2021
	 * @Description:Stamp color selection in coding form
	 * 
	 */

	public void stampSelectionCodingForm(String colour) throws AWTException {
		driver.waitForPageToBeReady();
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		base.stepInfo("Coding form child window opened");
		String parentWindow = reusableDocView.switchTochildWindow();
		reusableDocView.editingCodingFormWithSaveButton();
		base.waitForElement(getCodingFormStampButton());
		getCodingFormStampButton().waitAndClick(10);
		base.stepInfo("Coding stamp opened in pop up window");
		reusableDocView.childWindowToParentWindowSwitching(parentWindow);
		base.waitForElement(getCodingStampTextBox());
		getCodingStampTextBox().SendKeys("NewColour");
		base.waitForElement(getDrp_StampColour());
		getDrp_StampColour().waitAndClick(5);
		base.waitForElement(getAssignedColour(colour));
		getAssignedColour(colour).waitAndClick(5);
		base.waitForElement(getCodingStampSaveBtn());
		if (getCodingStampSaveBtn().Displayed() && getCodingStampSaveBtn().Enabled()) {
			getCodingStampSaveBtn().waitAndClick(5);
			softAssertion.assertTrue(true, "Coding stamp saved successfully");
			base.passedStep("Coding stamp saved successfully with name selection and colour");
		} else {
			base.failedStep("Failed to save the coding stamp with colour");
		}
		driver.scrollPageToTop();

	}

	/**
	 * 
	 * @author Indium-Baskar date: 15/8/2021 Modified date: NA Description:configure
	 *         mini doc list and save the configuration RPMXCON-51988 Sprint01
	 * 
	 */

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
//Duplicate entries need to check -- Bhaskar PR
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
	 * @author Indium-Baskar date: 15/8/2021 Modified date: NA Description:Entering
	 *         document number till loading in Mini doc list RPMXCON-51865 Sprint01
	 */

	public void enterDocumentNumberTillLoading() {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_NumTextBox().Enabled();
			}
		}), Input.wait30);
		getDocView_NumTextBox().SendKeys("125" + Keys.ENTER);
		softAssertion.assertEquals(getDocumetListLoading().Displayed().booleanValue(), true);
		softAssertion.assertEquals(getCentralPanelDispaly().Displayed().booleanValue(), true);
		base.passedStep("While Entering the document number persistent hits displayed");
		base.passedStep("Persistent hits loaded once while entering the document number");
	}

	/**
	 * @author Indium-Baskar date: 15/8/2021 Modified date: 24/8/2021
	 *         Description:Popup window should open while clciking the coding stamp
	 * 
	 */
	public void codingStampPopupShouldOpen() {
		driver.waitForPageToBeReady();
		editingCodingFormWithCompleteButton();
		driver.waitForPageToBeReady();
		base.stepInfo("Document completed successfully");
//		base.waitForElementCollection(getDocumetCountMiniDocList());
		for (int i = 2; i <= 4; i++) {
			base.waitForElement(getDocView_MiniDoc_ChildWindow_Selectdoc(i));
			getDocView_MiniDoc_ChildWindow_Selectdoc(i).WaitUntilPresent().waitAndClick(5);
		}
		base.waitForElement(getDocView_Mini_ActionButton());
		base.waitTillElemetToBeClickable(getDocView_Mini_ActionButton());
		getDocView_Mini_ActionButton().waitAndClick(5);
		base.waitForElement(getDocView__ChildWindow_Mini_CodeSameAs());
		base.waitTillElemetToBeClickable(getDocView__ChildWindow_Mini_CodeSameAs());
		getDocView__ChildWindow_Mini_CodeSameAs().waitAndClick(5);
		geDocView_MiniList_CodeSameAsIcon().WaitUntilPresent().ScrollTo();
		softAssertion.assertTrue(geDocView_MiniList_CodeSameAsIcon().Displayed());
//		base.waitForElementCollection(getDocumetCountMiniDocList());
		for (int i = 5; i <= 5; i++) {
			getClickDocviewID(i).WaitUntilPresent().Click();
		}
		base.waitForElement(getCodeSameAsLast());
		base.waitTillElemetToBeClickable(getCodeSameAsLast());
		getCodeSameAsLast().waitAndClick(10);
		base.stepInfo("Coded as per the coding form for the previous document");
		for (int i = 2; i <= 2; i++) {
			getClickDocviewID(i).waitAndClick(5);
		}
		try {
			String getAttribute = getDocument_CommentsTextBox().WaitUntilPresent().GetAttribute("value");
			softAssertion.assertEquals("verify check mark icon", getAttribute);
			if (getAttribute.equals("verify check mark icon")) {
				base.passedStep("Document is saved with the last applied coding of  the document..");
				base.passedStep("Expected Message - code same as last scuessfully..");
			} else {
				base.failedStep("Expected Message - code NOT same as last scuessfully..");
			}
		} catch (org.openqa.selenium.NoSuchElementException e) {
			e.printStackTrace();
		}
		base.waitForElement(getCodingFormStampButton());
		base.waitTillElemetToBeClickable(getCodingFormStampButton());
		getCodingFormStampButton().waitAndClick(5);
		softAssertion.assertTrue(getCodingFormStampButton().Displayed());
		base.passedStep("Coding stamp pop up window opened successfully");
	}

	/**
	 * @Author Mohan Created on 26/08/2021
	 * @Description To View thread map in Analytics panel RPMXCON-51846
	 */
	public void docViewAnalyticsPanelThread() {

		try {
			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getGearIcon().Visible() && getGearIcon().Enabled();
				}
			}, Input.wait30);
			getGearIcon().waitAndClick(10);

			String parentWindowID = driver.CurrentWindowHandle();

			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getDocView_ChildWindowPopOut().Visible() && getDocView_ChildWindowPopOut().Enabled();
				}
			}, Input.wait30);
			getDocView_ChildWindowPopOut().Click();
			Set<String> allWindowsId1 = driver.WindowHandles();
			for (String eachId : allWindowsId1) {
				if (!parentWindowID.equals(eachId)) {
					driver.switchTo().window(eachId);

					driver.WaitUntil(new Callable<Boolean>() {
						public Boolean call() {
							return getDocView_DocumentThreadMap().Visible() && getDocView_DocumentThreadMap().Enabled();
						}
					}, Input.wait30);

					driver.switchTo().window(parentWindowID);
					driver.scrollPageToTop();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Thread Map tab is viewed and doc are verified successfully");

		}
	}

	/**
	 * @Author Mohan Created on 26/08/2021
	 * @Description To View HitTerms with childwindow in Analytics panel
	 *              'RPMXCON-51754'
	 */
	public void selectAnalyticsPanelWithChildWindow() {

		try {
			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getGearIcon().Visible() && getGearIcon().Enabled();
				}
			}, Input.wait30);
			getGearIcon().waitAndClick(10);

			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getPersistantHitEyeIcon().Visible() && getPersistantHitEyeIcon().Enabled();
				}
			}, Input.wait30);
			getPersistantHitEyeIcon().waitAndClick(5);

			String parentWindowID = driver.CurrentWindowHandle();

//            Element popOut = driver.FindElementByXPath(
//                    "//*[@class='ui-sortable-handle']//*[@class='button-icon jarviswidget-pop-btn']//i[@class='fa fa-expand']");
			// driver.scrollingToElementofAPage(getDocView_ChildWindowPopOut());

			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getDocView_ChildWindowPopOut().Visible() && getDocView_ChildWindowPopOut().Enabled();
				}
			}, Input.wait30);
			getDocView_ChildWindowPopOut().Click();
			Set<String> allWindowsId = driver.WindowHandles();
			for (String eachId : allWindowsId) {
				if (!parentWindowID.equals(eachId)) {
					driver.switchTo().window(eachId);

					driver.WaitUntil(new Callable<Boolean>() {
						public Boolean call() {
							return getDocView_Analytics_liDocumentConceptualSimilarab().Visible()
									&& getDocView_Analytics_liDocumentConceptualSimilarab().Enabled();
						}
					}, Input.wait30);
					getDocView_Analytics_liDocumentConceptualSimilarab().Click();

					for (int i = 1; i <= 1; i++) {

						getDocumentConceptuallySimilar(i).waitAndClick(3);
					}
					driver.WaitUntil(new Callable<Boolean>() {
						public Boolean call() {
							return getDocView_ChildWindow_ActionButton().Visible()
									&& getDocView_ChildWindow_ActionButton().Enabled();
						}
					}, Input.wait30);
					getDocView_ChildWindow_ActionButton().Click();

					driver.WaitUntil(new Callable<Boolean>() {
						public Boolean call() {
							return getAnalyticalDropDown().Visible() && getAnalyticalDropDown().Enabled();
						}
					}, Input.wait30);
					getAnalyticalDropDown().Click();

					driver.switchTo().window(parentWindowID);

				}
			}

			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getDocView_ToggleButton().Visible() && getDocView_ToggleButton().Enabled();
				}
			}, Input.wait30);
			getDocView_ToggleButton().waitAndClick(3);

			// driver.scrollingToElementofAPage(getDocView_ChildWindowPopOut());

			Set<String> allWindowsId1 = driver.WindowHandles();
			for (String eachId : allWindowsId1) {
				if (!parentWindowID.equals(eachId)) {
					driver.switchTo().window(eachId);
					driver.WaitUntil(new Callable<Boolean>() {
						public Boolean call() {
							return getDocView_Analytics_liDocumentThreadMap().Visible()
									&& getDocView_Analytics_liDocumentThreadMap().Enabled();
						}
					}, Input.wait30);

					getDocView_Analytics_liDocumentThreadMap().waitAndClick(5);
					driver.WaitUntil(new Callable<Boolean>() {
						public Boolean call() {
							return getDocView_Analytics_liDocumentConceptualSimilarab().Visible()
									&& getDocView_Analytics_liDocumentConceptualSimilarab().Enabled();
						}
					}, Input.wait30);
					getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(5);
					getDocView_Analytics_liDocumentThreadMap().waitAndClick(5);
					getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(5);
					for (int k = 0; k < 40; k++) {
						try {
							getDocumentConceptuallySimilar(1).waitAndClick(10);
							break;
						} catch (Exception e) {
							base.waitForElement(getDocumentConceptuallySimilar(1));
						}

					}

					driver.WaitUntil(new Callable<Boolean>() {
						public Boolean call() {
							return getDocView_ChildWindow_ActionButton().Visible()
									&& getDocView_ChildWindow_ActionButton().Enabled();
						}
					}, Input.wait30);
					getDocView_ChildWindow_ActionButton().Click();

					driver.WaitUntil(new Callable<Boolean>() {
						public Boolean call() {
							return getAnalyticalDropDown().Visible() && getAnalyticalDropDown().Enabled();
						}
					}, Input.wait30);
					getAnalyticalDropDown().Click();

				}
			}
			driver.switchTo().window(parentWindowID);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Doc is viewed in Analytics panel by pop-out child window successfully");
		}

	}

	/**
	 * @Author Mohan Created on 26/08/2021
	 * @Description To View EyeIcon common reusable
	 */
	public void selectEyeIcon() {

		driver.WaitUntil(new Callable<Boolean>() {
			public Boolean call() {
				return getPersistantHitEyeIcon().Visible() && getPersistantHitEyeIcon().Enabled();
			}
		}, Input.wait30);
		getPersistantHitEyeIcon().waitAndClick(7);
	}

	/**
	 * @Author Mohan Created on 26/08/2021
	 * @Description To View HitTerms without childwindow in Analytics panel
	 *              'RPMXCON-51753'
	 */
	public void selectAnalyticsPanelWithoutChildWindow() {

		try {
			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getGearIcon().Visible() && getGearIcon().Enabled();
				}
			}, Input.wait30);
			getGearIcon().Click();

			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			// getDocView_Analytics_liDocumentConceptualSimilarab().ScrollTo();

			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getDocView_Analytics_liDocumentConceptualSimilarab().Visible()
							&& getDocView_Analytics_liDocumentConceptualSimilarab().Enabled();
				}
			}, Input.wait30);
			getDocView_Analytics_liDocumentConceptualSimilarab().Click();

			for (int i = 1; i <= 1; i++) {

				getDocumentConceptuallySimilar(i).waitAndClick(3);
			}
			getDocView_ChildWindow_ActionButton().Click();
			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getAnalyticalDropDown().Visible() && getAnalyticalDropDown().Enabled();
				}
			}, Input.wait30);
			getAnalyticalDropDown().Click();

			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getDocView_ToggleButton().Visible() && getDocView_ToggleButton().Enabled();
				}
			}, Input.wait30);
			driver.scrollPageToTop();
			getDocView_ToggleButton().waitAndClick(3);
			getDocView_Analytics_liDocumentConceptualSimilarab().ScrollTo();
			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getDocView_Analytics_liDocumentConceptualSimilarab().Visible()
							&& getDocView_Analytics_liDocumentConceptualSimilarab().Enabled();
				}
			}, Input.wait30);
			getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(7);

			for (int i = 1; i <= 1; i++) {

				getDocumentConceptuallySimilar(i).waitAndClick(3);
			}
			getDocView_ChildWindow_ActionButton().Click();
			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getAnalyticalDropDown().Visible() && getAnalyticalDropDown().Enabled();
				}
			}, Input.wait30);
			getAnalyticalDropDown().Click();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Doc is viewed in Analytics panel without pop-out child window successfully");
		}

	}

	/**
	 * @Author Mohan Created on 26/08/2021
	 * @Description To View HitTerms with and without childwindow in Analytics
	 *              panel.
	 */
	public void selectViewInDocView() {

		driver.scrollPageToTop();
		driver.WaitUntil(new Callable<Boolean>() {
			public Boolean call() {
				return getActionButton().Visible() && getActionButton().Enabled();
			}
		}, Input.wait30);
		getActionButton().waitAndClick(10);
		driver.WaitUntil(new Callable<Boolean>() {
			public Boolean call() {
				return getDocViewAction().Visible() && getDocViewAction().Enabled();
			}
		}, Input.wait30);
		getDocViewAction().waitAndClick(10);
	}

	/**
	 * @Author Mohan Created on 26/08/2021
	 * @Description To verify Thread Map Tab in Analytics panel 'RPMXCON-51844'
	 */
	public void verifyThreadMapTab() {

		try {
			base = new BaseClass(driver);
			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getDocView_Analytics_liDocumentThreadMap().Visible()
							&& getDocView_Analytics_liDocumentThreadMap().Enabled();
				}
			}, Input.wait30);

			softAssertion.assertTrue(getDocView_Analytics_liDocumentThreadMap().Displayed());
			base.passedStep("Thread map tab is not visible in the analytics panel");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Author Mohan Created on 26/08/2021
	 * @Description To get Family Members count and view in DocView
	 */
	public void getFamilyMemberPurehit() {
		driver.WaitUntil(new Callable<Boolean>() {
			public Boolean call() {
				return getFamilyMemberPureHitCount().Visible() && getFamilyMemberPureHitCount().Enabled();
			}
		}, Input.wait30);
		getFamilyMemberPureHitCount().waitAndClick(20);

	}

	/**
	 * @Author Mohan Created on 26/08/2021
	 * @Description To select doc from minidoc and view in DocView and Analytics
	 *              panel.
	 */
	public void selectDocumentFromAnalyticPanel(String text) {
		try {
			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getGearIcon().Visible() && getGearIcon().Enabled();
				}
			}, Input.wait30);
			getGearIcon().waitAndClick(20);

			String parentWindowID = driver.CurrentWindowHandle();

			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getMiniDocId(text).Visible() && getMiniDocId(text).Enabled();
				}
			}, Input.wait30);
			getMiniDocId(text).ScrollTo();
			getMiniDocId(text).Click();

			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getDocView_Analytics_liDocumentThreadMap().Visible()
							&& getDocView_Analytics_liDocumentThreadMap().Enabled();
				}
			}, Input.wait30);
			getDocView_Analytics_liDocumentThreadMap().waitAndClick(3);

			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getDocView_DocumentThreadMap().Visible() && getDocView_DocumentThreadMap().Enabled();
				}
			}, Input.wait30);

			// driver.scrollingToElementofAPage(popOut);
			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getDocView_ChildWindowPopOut().Visible() && getDocView_ChildWindowPopOut().Enabled();
				}
			}, Input.wait30);
			getDocView_ChildWindowPopOut().Click();
			Set<String> allWindowsId1 = driver.WindowHandles();
			for (String eachId : allWindowsId1) {
				if (!parentWindowID.equals(eachId)) {
					driver.switchTo().window(eachId);

					driver.WaitUntil(new Callable<Boolean>() {
						public Boolean call() {
							return getDocView_Analytics_liDocumentThreadMap().Visible()
									&& getDocView_Analytics_liDocumentThreadMap().Enabled();
						}
					}, Input.wait30);

					getDocView_Analytics_liDocumentThreadMap().Click();

					driver.WaitUntil(new Callable<Boolean>() {
						public Boolean call() {
							return getDocView_DocumentThreadMap().Visible() && getDocView_DocumentThreadMap().Enabled();
						}
					}, Input.wait30);

				}
			}

			driver.switchTo().window(parentWindowID);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Thread map Doc is displayed on Analytics panel Successfully");
		}

	}

	/**
	 * @Author Mohan Created on 26/08/2021
	 * @Description To verify Horizontal Tab Scroll in Analytics panel
	 *              'RPMXCON-51753'
	 */
	public void verifyHorizontalTab(String text) {

		try {
			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getMiniDocId(text).Visible() && getMiniDocId(text).Enabled();
				}
			}, Input.wait30);
			getMiniDocId(text).ScrollTo();
			getMiniDocId(text).waitAndClick(7);

			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getGearIcon().Visible() && getGearIcon().Enabled();
				}
			}, Input.wait30);
			getGearIcon().waitAndClick(10);

			String parentWindowID = driver.CurrentWindowHandle();

			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getDocView_ChildWindowPopOut().Visible() && getDocView_ChildWindowPopOut().Enabled();
				}
			}, Input.wait30);
			getDocView_ChildWindowPopOut().waitAndClick(10);
			Set<String> allWindowsId1 = driver.WindowHandles();
			for (String eachId : allWindowsId1) {
				if (!parentWindowID.equals(eachId)) {
					driver.switchTo().window(eachId);

					driver.WaitUntil(new Callable<Boolean>() {
						public Boolean call() {
							return getDocView_DocumentThreadMap().Visible() && getDocView_DocumentThreadMap().Enabled();
						}
					}, Input.wait30);

					driver.WaitUntil((new Callable<Boolean>() {
						public Boolean call() {
							return getDocView_Analytics_LoadMoreButton().Visible()
									&& getDocView_Analytics_LoadMoreButton().Enabled();
						}
					}), Input.wait30);
					getDocView_Analytics_LoadMoreButton().waitAndClick(7);
					driver.waitForPageToBeReady();

					for (int i = 2; i <= 2; i++) {

						getDocView_Analytics_ThreadMap_EmailDocs(i).waitAndClick(5);
					}

					Robot robot = new Robot();

					for (int i = 0; i < 12; i++) {
						robot.keyPress(KeyEvent.VK_RIGHT);
						robot.keyRelease(KeyEvent.VK_RIGHT);

					}

					softAssertion.assertTrue(getDocView_DocumentThreadMap().Displayed());
					base.passedStep("Horizontal Tab is Verify and the doc remain selected in the ThreadMap tab");
					driver.switchTo().window(parentWindowID);
					driver.scrollPageToTop();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Thread Map tab is viewed and doc are verified successfully");
		}

	}

	public void verifyDocToViewInDocView(final String assginmentName) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDashboardButton().Visible();
			}
		}), Input.wait30);
		getDashboardButton().waitAndClick(5);
		driver.WaitUntil(new Callable<Boolean>() {
			public Boolean call() {
				return getAlertConfrimButton().Visible() && getAlertConfrimButton().Enabled();
			}
		}, Input.wait30);
		getAlertConfrimButton().Click();

		driver.scrollingToBottomofAPage();
		driver.WaitUntil(new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAssignmentFromDashborad(assginmentName).Visible()
						&& getSelectAssignmentFromDashborad(assginmentName).Enabled();
			}
		}, Input.wait30);
		getSelectAssignmentFromDashborad(assginmentName).Click();

	}

	public void selectAssignmentfromDashborad(String assginmentName) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDashboardButton().Visible() && getDashboardButton().Enabled();
			}
		}), Input.wait30);
		getDashboardButton().waitAndClick(5);

		driver.WaitUntil(new Callable<Boolean>() {
			public Boolean call() {
				return getSelectAssignmentFromDashborad(assginmentName).Visible()
						&& getSelectAssignmentFromDashborad(assginmentName).Enabled();
			}
		}, Input.wait30);
		getSelectAssignmentFromDashborad(assginmentName).waitAndClick(10);

	}

	public void selectDocAnalyticsPanelWithoutChildWindow() {

		try {

			BaseClass baseClass = new BaseClass(driver);
			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getGearIcon().Visible() && getGearIcon().Enabled();
				}
			}, Input.wait30);
			getGearIcon().waitAndClick(10);
			;
			driver.scrollingToBottomofAPage();
			if (getDocView_Analytics_liDocumentThreadMap().Displayed()) {
				baseClass.failedStep("Thread Map tab is visible and doc are present");
			} else {
				baseClass.passedStep("Thread Map tab is disabled and doesn't contains any doc");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Doc is viewed in Analytics panel without pop-out child window successfully");
		}
	}

	/**
	 * @author Indium-Mohan date: 15/8/2021 Modified date: NA
	 *         Description:searchDocumentBasedOnId
	 */

	public void searchDocumentBasedOnId(int id) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_DefaultViewTab().Visible();
			}
		}), Input.wait30);
		base.waitForElement(getDocView_NumTextBox());
		getDocView_NumTextBox().SendKeys(Integer.toString(id));
		getDocView_NumTextBox().Enter();
		driver.getWebDriver().navigate().refresh();
		driver.waitForPageToBeReady();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_MiniDocListIds(id).Visible();
			}
		}), Input.wait60);
		softAssertion.assertTrue(getDocView_MiniDocListIds(id).Displayed());

	}

	/**
	 * 
	 * @author Indium-Mohan date: 15/8/2021 Modified date: NA Description:playing
	 *         audio for document file
	 * @author Indium-Mohan date: 15/8/2021 Modified date: NA Description:playing
	 *         audio for document file
	 */

	public void playAudioOnly() throws InterruptedException {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_IconFileType().Visible();
			}
		}), Input.wait120);
		softAssertion.assertEquals(getDocView_IconFileType().getText().toString(), "M");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_TextFileType().Visible();
			}
		}), Input.wait120);
		softAssertion.assertEquals(getDocView_TextFileType().getText().toString(), "MP3 VERSION");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_IconPlay().Displayed();
			}
		}), Input.wait30);
		getDocView_IconPlay().waitAndClick(30);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_IconPause().Displayed();
			}
		}), Input.wait30);

	}

	/**
	 * @author Indium-Mohan date: 15/8/2021 Modified date: NA Description:Scrolling
	 *         the document and verify the loading display
	 */

	public void scrollUntilLoadingElementIsDisplayed() {
		try {
			WebElement element = getDocumetId().getWebElement();
			int xcord = 50;
			int ycord = 100;
			Actions actions = new Actions(driver.getWebDriver());
			for (int i = 0; i < 5; i++) {
				actions.moveToElement(element, xcord, ycord).build().perform();
			}
			softAssertion.assertEquals(getDocumetListLoading().Displayed().booleanValue(), true);
		} catch (Exception e) {
			e.getLocalizedMessage();
			e.printStackTrace();

		}
		softAssertion.assertEquals(getDocumetListLoading().Displayed().booleanValue(), true);

	}

	/**
	 * @author Indium-Mohan date: 15/8/2021 Modified date: NA Description:playing
	 *         audio for document file
	 */
	public void selectPureHits() {
		getPureHitCount().WaitUntilPresent().ScrollTo();
		driver.WaitUntil(new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitCount().Visible() && getPureHitCount().Enabled();
			}
		}, Input.wait30);
		getPureHitCount().waitAndClick(5);
	}

	/**
	 * @author Indium-Mohan date: 15/8/2021 Modified date: NA Description:Scrolling
	 *         the document and verify the loading display
	 */

	public void selectPureHit() {
		// driver.scrollingToBottomofAPage();
		// getPureHitsCount().WaitUntilPresent().ScrollTo();
		driver.WaitUntil(new Callable<Boolean>() {
			public Boolean call() {
				return getPureHitsCount().Visible() && getPureHitsCount().Enabled();
			}
		}, Input.wait30);
		getPureHitsCount().waitAndClick(5);
	}

	/**
	 * @author Indium-Baskar date: 18/8/2021 Modified date: NA Description:Switchig
	 *         to MiniDocList Child Window
	 */
	public String miniDocListChildWindowopening() {
		String parentWindow = driver.getWebDriver().getWindowHandle();
		Set<String> childWindow = driver.getWebDriver().getWindowHandles();
		for (String miniDocListChild : childWindow) {
			if (!parentWindow.equals(miniDocListChild)) {
				driver.switchTo().window(miniDocListChild);
				driver.waitForPageToBeReady();
			}
		}
		return parentWindow;

	}

	/**
	 * @author Indium-Baskar date: 18/8/2021 Modified date: NA Description:Closing
	 *         the child Window
	 */

	public void childWindowToParentWindowSwitching(String parentWindow) {
		driver.waitForPageToBeReady();
		driver.close();
		driver.switchTo().window(parentWindow);
	}

	/**
	 * @author Indium-Baskar date: 18/8/2021 Modified date: NA Description:Editing
	 *         the coding form
	 */

	public void editingCodingFormWithCompleteButton() {
		base.waitForElement(getResponsiveCheked());
		getResponsiveCheked().waitAndClick(5);
		base.waitForElement(getNonPrivilegeRadio());
		getNonPrivilegeRadio().waitAndClick(5);
		base.waitForElement(getDocument_CommentsTextBox());
		getDocument_CommentsTextBox().SendKeys("verify check mark icon");
		base.waitForElement(getCompleteDocBtn());
		getCompleteDocBtn().waitAndClick(5);
		base.stepInfo("Expected Message : Document completed successfully");
		base.passedStep("Check mark icon displayed in minidoclist after completing document");
	}

	/**
	 * @author Indium-Baskar date: 18/8/2021 Modified date: NA Description:Document
	 *         Count should verify in Mini Doc List
	 * @throws InterruptedException
	 */

	public void documentCountShouldBeSame(String search) throws InterruptedException {
		int basicContentSearchCount = sp.basicContentSearch(search);
		sp.ViewInDocView();
		base.stepInfo("Checking number of document present in mini doc list");
		driver.waitForPageToBeReady();
		base.waitForElementCollection(getDocumetCountMiniDocList());
		int miniDocListCount = getDocumetCountMiniDocList().WaitUntilPresent().size();
		System.out.println(miniDocListCount);
		base.waitForElement(getDocView_EditMode());
		getDocView_EditMode().waitAndClick(5);
		base.waitForElement(getDocView_HdrMinDoc());
		getDocView_HdrMinDoc().waitAndClick(5);
		String childWindow = miniDocListChildWindowopening();
		int miniDocListChildCount = getDocumetCountMiniDocList().WaitUntilPresent().size();
		base.stepInfo("Checking number of document equals to minidoc list in pop up state");
		if (miniDocListCount == miniDocListChildCount) {
			base.passedStep("Session Serach count :  " + basicContentSearchCount + " MiniDoclist count : "
					+ miniDocListCount + " MiniDocList Child Window Count : " + miniDocListChildCount + "");
		} else {
			base.failedStep("Session Serach count : " + basicContentSearchCount + " MiniDoclist count : "
					+ miniDocListCount + " MiniDocList Child Window Count : " + miniDocListChildCount + "");
		}
		childWindowToParentWindowSwitching(childWindow);
	}

	/**
	 * @author Indium-Baskar date: 18/8/2021 Modified date: NA Description:Check
	 *         mark icon should display after completeing the document
	 */

	public void verifyingCheckMarkIconInMiniDocList() {
		driver.waitForPageToBeReady();
		for (int i = 1; i <= 3; i++) {
			getDocView_MiniDoc_ChildWindow_Selectdoc(i).waitAndClick(5);
		}
		base.waitForElement(getDocView_Mini_ActionButton());
		getDocView_Mini_ActionButton().waitAndClick(5);
		base.waitForElement(getDocView__ChildWindow_Mini_CodeSameAs());
		getDocView__ChildWindow_Mini_CodeSameAs().waitAndClick(5);
		base.stepInfo("Expected Message : Code same performed successfully.");
		base.passedStep("Chain link displayed in minidoclist after performing code same as action");
		geDocView_MiniList_CodeSameAsIcon().WaitUntilPresent().ScrollTo();
		softAssertion.assertEquals(geDocView_MiniList_CodeSameAsIcon().Displayed().booleanValue(), true);
		editingCodingFormWithCompleteButton();
	}

	public void selectAdvancedSearch(String foldName) throws InterruptedException {

		driver.getWebDriver().get(Input.url + "Search/Searches");

		try {
			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getAdvanceSearchButton().Visible();
				}
			}, Input.wait30);
			softAssertion.assertTrue(getAdvanceSearchButton().Displayed());
			getAdvanceSearchButton().Click();

			softAssertion.assertTrue(getWorkProductButton().Displayed());
			getWorkProductButton().Click();

			driver.scrollingToBottomofAPage();
			softAssertion.assertTrue(getFolderButton().Displayed());
			getFolderButton().Click();

			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getFolderSelection(foldName).Visible();
				}
			}, Input.wait30);
			softAssertion.assertTrue(getFolderSelection(foldName).Displayed());
			getFolderSelection(foldName).waitAndClick(10);
			softAssertion.assertTrue(getInsertQueryButton().Displayed());
			getInsertQueryButton().Click();
			driver.scrollPageToTop();
			getSearchButon().Click();
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Doc is searched from Advanced Search Successfully");
		}
	}

	public void selectDocumentFromAnalyticPanel() {

		try {
			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getGearIcon().Visible() && getGearIcon().Enabled();
				}
			}, Input.wait30);
			getGearIcon().waitAndClick(10);

			String parentWindowID = driver.CurrentWindowHandle();

			Element popOut = driver.FindElementByXPath(
					"//*[@class='ui-sortable-handle']//*[@class='button-icon jarviswidget-pop-btn']//i[@class='fa fa-expand']");
			driver.scrollingToElementofAPage(popOut);

			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getDocView_Analytics_liDocumentThreadMap().Visible()
							&& getDocView_Analytics_liDocumentThreadMap().Enabled();
				}
			}, Input.wait30);
			getDocView_Analytics_liDocumentThreadMap().Click();

			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getDocView_DocumentThreadMap().Visible() && getDocView_DocumentThreadMap().Enabled();
				}
			}, Input.wait30);

			driver.scrollingToElementofAPage(popOut);
			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getDocView_ChildWindowPopOut().Visible() && getDocView_ChildWindowPopOut().Enabled();
				}
			}, Input.wait30);
			getDocView_ChildWindowPopOut().Click();
			Set<String> allWindowsId1 = driver.WindowHandles();
			for (String eachId : allWindowsId1) {
				if (!parentWindowID.equals(eachId)) {
					driver.switchTo().window(eachId);

					driver.WaitUntil(new Callable<Boolean>() {
						public Boolean call() {
							return getDocView_Analytics_liDocumentThreadMap().Visible()
									&& getDocView_Analytics_liDocumentThreadMap().Enabled();
						}
					}, Input.wait30);

					getDocView_Analytics_liDocumentThreadMap().Click();

					driver.WaitUntil(new Callable<Boolean>() {
						public Boolean call() {
							return getDocView_DocumentThreadMap().Visible() && getDocView_DocumentThreadMap().Enabled();
						}
					}, Input.wait30);

				}
			}

			driver.switchTo().window(parentWindowID);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Thread map Doc is displayed on Analytics panel Successfully");
		}

	}

	/**
	 * @author Mohan created for Test Case Id:RPMXCON-51872 Description Since select
	 *         all check box locator is not available this method is created...
	 *         date: 8/23/21 NA Modified date: NA Modified by:NA
	 */
	public void verifySelectAllCheckbox(String text) {
		driver.WaitUntil(new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_MiniDocListDocId(text).Visible();
			}
		}, Input.wait30);
		softAssertion.assertTrue(getDocView_MiniDocListDocId(text).Displayed());
	}

	/**
	 * @author Mohan created for Test Case Id:RPMXCON-51872 date: 8/23/21 NA
	 *         Modified date: NA Modified by:NA
	 */
	public void selectMiniDocListChildWindow(String text) {

		driver.WaitUntil(new Callable<Boolean>() {
			public Boolean call() {
				return getGearIcon().Visible() && getGearIcon().Enabled();
			}
		}, Input.wait30);
		getGearIcon().waitAndClick(10);

		String parentWindowID = driver.CurrentWindowHandle();

		driver.WaitUntil(new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_MiniDoclistChildWindow().Visible() && getDocView_MiniDoclistChildWindow().Enabled();
			}
		}, Input.wait30);

		getDocView_MiniDoclistChildWindow().Click();

		Set<String> allWindowsId1 = driver.WindowHandles();
		for (String eachId : allWindowsId1) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);

				verifySelectAllCheckbox(text);
				driver.switchTo().window(parentWindowID);
			}
		}
	}

	/**
	 * @author Indium-Baskar date: 20/8/2021 Modified date: NA Description:Click
	 *         Gear Icon popup out MiniDocList Child Window
	 */
	// Reusable Method For Click GearIcon To Open MiniDocList
	public void clickGearIconOpenMiniDocList() {
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		base.waitForElement(getDocView_EditMode());
		getDocView_EditMode().waitAndClick(5);
		base.waitForElement(getDocView_HdrMinDoc());
		getDocView_HdrMinDoc().waitAndClick(5);
	}

	/**
	 * @author Indium-Baskar date: 20/8/2021 Modified date: NA Description:Scrolling
	 *         with more number of document
	 */
	// Reusable Method For scrolling MiniDocList
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
				softAssertion.assertEquals(getDocumetListLoading().Displayed().booleanValue(), true);
				driver.waitForPageToBeReady();
			}
		}
		base.passedStep("Document scrolled with more number of document till scroll end");
		base.passedStep("While scrolling the document loading dispalyed");
	}

	/**
	 * @author Indium-Baskar date: 20/8/2021 Modified date: NA
	 * @Description:Verify the document count When minimize and maximize mini doc
	 *                     list
	 */

	public void minimizeAndMaximizeDocumentCountShouldBeSame(String search) throws InterruptedException {
		int basicContentSearchCount = sp.basicContentSearch(search);
		base.stepInfo("Session Serach count :  " + basicContentSearchCount);
		sp.ViewInDocView();
		base.stepInfo("Checking number of document present in mini doc list");
		driver.waitForPageToBeReady();
		base.waitForElementCollection(getDocumetCountMiniDocList());
		int miniDocListCount = getDocumetCountMiniDocList().WaitUntilPresent().size();
		base.stepInfo("MiniDoclist count :  " + miniDocListCount);
		System.out.println(miniDocListCount);
		clickGearIconOpenMiniDocList();
		String childWindow = miniDocListChildWindowopening();
		driver.waitForPageToBeReady();
		base.waitForElementCollection(getDocumetCountMiniDocList());
		int miniDocListChildCount = getDocumetCountMiniDocList().WaitUntilPresent().size();
		base.stepInfo("MiniDocList Child Window Count :  " + miniDocListChildCount);
		driver.Manage().window().maximize();
		base.stepInfo("Checking number of document equals to minidoc list in pop up state When Minimize and maximize");
		if (miniDocListCount == miniDocListChildCount) {
			base.passedStep(" MiniDoclist count : " + miniDocListCount + " MiniDocList Child Window Count : "
					+ miniDocListChildCount + "");
		} else {
			base.failedStep(" MiniDoclist count : " + miniDocListCount + " MiniDocList Child Window Count : "
					+ miniDocListChildCount + "");
		}
		childWindowToParentWindowSwitching(childWindow);
	}

	/**
	 * @author Indium-Baskar date: 21/8/2021 Modified date: NA Description:Click
	 *         clock icon in minidoc list
	 */
	// Reusable Method For Click clock icon in minidoclist
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
		softAssertion.assertEquals(getDocView_DefaultViewTab().Displayed().booleanValue(), true);
		base.passedStep("Document displaying in default view page");
	}

	/**
	 * @author Indium-Baskar date: 25/8/2021 Modified date: NA
	 * @Description:keep on completing document and verifying right arrow
	 */

	public void verifyPrincipalDocumentMiniDocList() {
		base.waitForElementCollection(getDocumetCountMiniDocList());
		int miniDocListCount = getDocumetCountMiniDocList().WaitUntilPresent().size();
		base.stepInfo("MiniDoclist count :  " + miniDocListCount);
		reusableDocView.editingCodingFormWithCompleteButton();
		for (int i = 0; i < 5; i++) {
			getCompleteDocBtn().waitAndClick(5);
		}
		getverifyCodeSameAsLast().WaitUntilPresent().ScrollTo();
		int completedCount = getMiniDocListCompletedCount().size();
		base.stepInfo("Document completed count  :  " + completedCount);
		int principalPendingDocument = miniDocListCount - completedCount;
		base.passedStep("Principal pending document visible in minidoc list panel : " + principalPendingDocument);
		driver.waitForPageToBeReady();
		softAssertion.assertEquals(getMiniDocListRightArrow().Displayed().booleanValue(), true);
		base.passedStep("Right arrow displayed for pending principal document");

	}

	/**
	 * 
	 * @author Mohan 8/29/21 NA Modified date: NA Modified by:NA
	 * @description to select NearDupe PureHit
	 * 
	 */
	public void selectNearDupePureHit() {

//		driver.WaitUntil(new Callable<Boolean>() {
//			public Boolean call() {
//				return getNearDupePureHitsCount().Visible() && getPureHitsCount().Enabled();
//			}
//		}, Input.wait30);
		// Indium - Stabilization
		base.waitForElement(getNearDupePureHitsCount());
		getNearDupePureHitsCount().Click();

	}

	/**
	 * @author Mohan 8/29/21 NA Modified date: NA Modified by:NA
	 * @description to open NearDupe ComparisonWindow
	 */

	public void openNearDupeComparisonWindow() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Analytics_NearDupeTab().Displayed();
			}
		}), Input.wait30);
		getDocView_Analytics_NearDupeTab().waitAndClick(10);

		getDocView_NearDupeIcon().waitAndClick(10);

		for (String winHandle : driver.getWebDriver().getWindowHandles()) {
			driver.switchTo().window(winHandle);
			driver.waitForPageToBeReady();
		}

		getDocView_NearDupe_DocID().WaitUntilPresent();
		String docidinchildwinodw = getDocView_NearDupe_DocID().getText().toString();
		System.out.println(docidinchildwinodw);
	}

	/**
	 * @author Mohan 8/29/21 NA Modified date: 25/10/2021 Modified by:Mohan
	 * @description to pop-out NearDupe tab in AnalyticsPanel
	 */
	public void popOutAnalyticsPanel() {

		try {

			driver.scrollPageToTop();

			base.waitForElement(getGearIcon());
			getGearIcon().waitAndClick(10);

//			getDocView_ChildWindowPopOut().ScrollTo();
			if (getDocView_ChildWindowPopOut().isDisplayed()) {
				base.waitForElement(getDocView_ChildWindowPopOut());
				getDocView_ChildWindowPopOut().waitAndClick(10);
				base.passedStep("Analytics Panel child window is popout successfully");
			}

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Analytics Panel child window is not popout");
			System.out.println("Analytics Panel child window is not popout");
		}
	}

	/**
	 * @author Mohan 8/29/21 NA Modified date: NA Modified by:NA
	 * @description to click Apply Coding button in AnalyticsPanel
	 */
	public void clickApplyCodingBtn() {

		driver.waitForPageToBeReady();

		driver.Navigate().refresh();
		driver.Navigate().refresh();
		driver.Navigate().refresh();

		base.waitForElement(getApplyCodingNearDedupeBtn());
		base.waitTillElemetToBeClickable(getApplyCodingNearDedupeBtn());
		getApplyCodingNearDedupeBtn().waitAndClick(10);
		base.passedStep("Apply Coding Near Dupe button is clicked successfully");

	}

	/**
	 * @author Mohan 8/29/21 NA Modified date: NA Modified by:NA
	 * @description to verify code as same chain link
	 */
	public void verifyChainLinkAfterCoding() {

		try {

			base.waitForElement(geDocView_NearDupe_CodeSameAsIcon());
			softAssertion.assertEquals(geDocView_NearDupe_CodeSameAsIcon().Displayed().booleanValue(), true);
			System.err.println("Link" + geDocView_NearDupe_CodeSameAsIcon().Displayed().booleanValue());
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Code Same As icon is not displayed");
		}
	}

	/**
	 * @author Mohan 8/29/21 NA Modified date: NA Modified by:NA
	 * @description to verify neardupe purehits
	 */

	public void verifyNdDocumentsPureHits(int ndPureHit) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_MiniListDocuments().Displayed();
			}
		}), Input.wait30);

		ElementCollection miniListDocuments = getDocView_MiniListDocuments();
		softAssertion.assertEquals(miniListDocuments.size(), ndPureHit);

	}

	/**
	 * @author Mohan 8/29/21 NA Modified date: NA Modified by:NA
	 * @description to edit coding form and save
	 */
	public void editCodingFormSave() {

		driver.waitForPageToBeReady();

//		driver.WaitUntil((new Callable<Boolean>() {
//
//			public Boolean call() {
//
//				return getDocument_CommentsTextBox().Displayed() && getDocument_CommentsTextBox().Enabled();
//			}
//		}), Input.wait30);

		base.waitForElement(getDocument_CommentsTextBox());

		getDocument_CommentsTextBox().SendKeys("Editing and click save button");

		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {
				return getCodingFormSaveBtn().Enabled() && getCodingFormSaveBtn().Visible();
			}
		}), Input.wait30);

		driver.scrollPageToTop();

		getCodingFormSaveBtn().Click();

		base.VerifySuccessMessage("Document saved successfully");

	}

	/**
	 * @author Mohan 9/01/21 NA Modified date: NA Modified by:NA
	 * @description to verify 'View in Doclist' is not visible in ThreadMap Tab
	 */
	public void viewThreadMapViewInDocList() {

		driver.waitForPageToBeReady();
		base.waitForElement(getGearIcon());
		getGearIcon().waitAndClick(5);

		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_Analytics_liDocumentThreadMap());
		getDocView_Analytics_liDocumentThreadMap().waitAndClick(5);

		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().Click();

		base.waitForElement(getViewInDocListAnalyticalDropDown());
		softAssertion.assertFalse(getViewInDocListAnalyticalDropDown().Displayed());
		base.passedStep(
				"To verify 'View in doc list' action when no document in Threaded Map panel has been verified successfully");

	}

	/**
	 * @author Mohan 9/02/21 NA Modified date: NA Modified by:Steffy
	 * @description To edit coding form and complete
	 */
	public void editCodingFormComplete() {

		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		base.waitForElement(getResponsiveCheked());
		getResponsiveCheked().Click();
		base.waitForElement(getNonPrivilegeRadio());
		getNonPrivilegeRadio().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocument_CommentsTextBox().Displayed() && getDocument_CommentsTextBox().Enabled();
			}
		}), Input.wait30);
		getDocument_CommentsTextBox().SendKeys("Editing and click complete button");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCompleteDocBtn().Enabled() && getCompleteDocBtn().Visible();
			}
		}), Input.wait30);

		driver.scrollPageToTop();
		getCompleteDocBtn().Click();
		base.VerifySuccessMessage("Document completed successfully");
		driver.waitForPageToBeReady();

	}

	/**
	 * @author Mohan 9/02/21 NA Modified date: NA Modified by:NA
	 * @description To Open NearDupe Comparison Window for Reviewer
	 */
	public void openNearDupeComparisonWindowForReviewer() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Analytics_NearDupeTab().Displayed();
			}
		}), Input.wait30);
		getDocView_Analytics_NearDupeTab().waitAndClick(10);

		JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
		driver.waitForPageToBeReady();
		Point p = getDocView_Analytics_NearDupeTab().getWebElement().getLocation();
		je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
		getDocView_Analytics_NearDupeTab().ScrollTo();

		List<WebElement> optimized = getDocView_NearDupeIconForReviewer().FindWebElements();
		for (int k = 3; k < optimized.size();) {
			optimized.get(k).click();
			break;
		}

		for (String winHandle : driver.getWebDriver().getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}

		getDocView_NearDupe_DocID().WaitUntilPresent();
		String docidinchildwinodw = getDocView_NearDupe_DocID().getText().toString();
		System.out.println(docidinchildwinodw);

	}

	/**
	 * @author Mohan 9/02/21 NA Modified date: NA Modified by:NA
	 * @description To Select ThreadMap Purehit count
	 */
	public void selectThreadMapPureHit() {

		driver.WaitUntil(new Callable<Boolean>() {
			public Boolean call() {
				return getThreadMapPureHitsCount().Visible() && getPureHitsCount().Enabled();
			}
		}, Input.wait30);
		getThreadMapPureHitsCount().Click();

	}

	/**
	 * @author Mohan 9/02/21 NA Modified date: NA Modified by:NA
	 * @description To Select DocId From mini doclist
	 */
	public void selectDocIdInMiniDocList(String docId) {

		try {
			driver.waitForPageToBeReady();
			for (int i = 0; i < 20; i++) {
				try {
					driver.waitForPageToBeReady();
//					getDocView_DocId(docId).ScrollTo();
					base.waitForElement(getDocView_DocId(docId));
					getDocView_DocId(docId).waitAndClick(15);
					softAssertion.assertTrue(getDocView_DocId(docId).isDisplayed());
					base.passedStep("Doc is selected from MiniDoclist successfully");
					break;

				} catch (Exception e) {
					driver.Navigate().refresh();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Docs Arenot selected from mini doclist");
		}
	}

	/**
	 * @author Indium-Baskar date: 3/9/2021 Modified date: NA
	 * @Description : Verifying already saved stamp colour
	 */

	public void clickStampBtnAndVerifyINPopUp(String textBox, String colour, String icon) {
		reusableDocView.stampColourSelection(textBox, colour);
		reusableDocView.pencilGearicon(icon);
		reusableDocView.verifyingPostFixAssignedColour();

	}

	/**
	 * @author Indium-Baskar date: 3/9/2021 Modified date: NA
	 * @Description : Verifying already saved stamp colour
	 */

	public void openStampPopUpFromChildWindow(String textBox, String colour, String iconColour) {
		reusableDocView.stampColourSelection(textBox, colour);
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parentWindow = reusableDocView.switchTochildWindow();
		reusableDocView.pencilGearicon(iconColour);
		reusableDocView.childWindowToParentWindowSwitching(parentWindow);
		reusableDocView.verifyingPostFixAssignedColour();

	}

	/**
	 * @author Indium-Baskar date: 07/09/2021 Modified date: NA
	 * @Description : Verifying Code same as
	 */
	public void uncompleteButtonShouldDisplay() {
		driver.waitForPageToBeReady();
		for (int i = 2; i <= 2; i++) {
			base.waitForElement(getDocView_MiniDoc_ChildWindow_Selectdoc(i));
			getDocView_MiniDoc_ChildWindow_Selectdoc(i).WaitUntilPresent().waitAndClick(5);
		}
		base.waitForElement(getDocView_Mini_ActionButton());
		getDocView_Mini_ActionButton().waitAndClick(5);
		base.waitForElement(getDocView__ChildWindow_Mini_CodeSameAs());
		getDocView__ChildWindow_Mini_CodeSameAs().waitAndClick(5);
		driver.getWebDriver().navigate().refresh();
		driver.switchTo().alert().accept();
		driver.waitForPageToBeReady();
		base.waitForElement(geDocView_MiniList_CodeSameAsIcon());
		geDocView_MiniList_CodeSameAsIcon().WaitUntilPresent().ScrollTo();
		base.passedStep("Chain link displayed after performing code same as action");
		softAssertion.assertEquals(geDocView_MiniList_CodeSameAsIcon().Displayed().booleanValue(), true);
		reusableDocView.editingCodingFormWithCompleteButton();
		base.passedStep("After clciking complete button verified tick mark in the minidoclist document");
		for (int i = 1; i <= 1; i++) {
			getClickDocviewID(i).waitAndClick(5);
		}
		if (getUnCompleteButton().Displayed()) {
			base.passedStep("Uncomplete button displayed for both documents after selecting each document separately");
			softAssertion.assertEquals(getUnCompleteButton().Displayed().booleanValue(), true);
		} else {
			base.failedStep(
					"Uncomplete button not displayed for both documents after selecting each document separately");

		}
	}

	/**
	 * @author Indium-Baskar date: 07/09/2021 Modified date: NA
	 * @Description : Principal document to view first
	 */

	public void verifyingRightArrowInMiniDocList() {
		reusableDocView.clickGearIconOpenMiniDocList();
		String parentWindows = reusableDocView.switchTochildWindow();
//		base.waitForElementCollection(getDocumetCountMiniDocList());
		List<WebElement> scrollTillLast = getDocumetCountMiniDocList().FindWebElements();
		for (int i = 0; i < scrollTillLast.size(); i++) {
			if (i == 5) {
				JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();
				jse.executeScript("document.querySelector('.dataTables_scrollBody').scrollBy(0,1500)");
				driver.waitForPageToBeReady();
				base.stepInfo("Document get scrolled down from the minidoclist child window");
			}
		}
		for (int j = 14; j <= 14; j++) {
			getDocView_MiniDoc_ChildWindow_Selectdoc(j).WaitUntilPresent().Click();
		}
		reusableDocView.clickCodeSameAs();
		reusableDocView.childWindowToParentWindowSwitching(parentWindows);
		driver.getWebDriver().navigate().refresh();
		driver.switchTo().alert().accept();
		reusableDocView.editingCodingFormWithCompleteButton();
		driver.waitForPageToBeReady();
		base.passedStep("Cursor moved to the next document from minidoclist");
		softAssertion.assertEquals(getMiniDocListRightArrow().Displayed().booleanValue(), true);
		base.passedStep("Right arrow displayed for pending principal document");
	}

	/**
	 * @author Indium-Baskar date: 07/09/2021 Modified date: NA
	 * @Description : Principal document to view first using last document buttton
	 */

	public void lastDocumentVerifyRightArrow() {
		driver.waitForPageToBeReady();
		reusableDocView.editingCodingFormWithCompleteButton();
		reusableDocView.clickGearIconOpenMiniDocList();
		String parentWindows = reusableDocView.switchTochildWindow();
		driver.waitForPageToBeReady();
		for (int j = 2; j <= 2; j++) {
			getDocView_MiniDoc_ChildWindow_Selectdoc(j).WaitUntilPresent().waitAndClick(5);
		}
		reusableDocView.clickCodeSameAsParent();
		reusableDocView.childWindowToParentWindowSwitching(parentWindows);
		reusableDocView.clickCodeSameAsLast();
		base.passedStep("Tick mark displayed in minidoclist for completed document");
		softAssertion.assertEquals(getverifyCodeSameAsLast().Displayed().booleanValue(), true);
		for (int i = 2; i <= 2; i++) {
			getClickDocviewID(i).waitAndClick(5);
		}
		reusableDocView.gettingAttributeWithCompleteButton();
		softAssertion.assertEquals(getMiniDocListRightArrow().Displayed().booleanValue(), true);
		base.passedStep("Right arrow displayed for pending principal document");
		base.passedStep("Principal document displayed at first");
	}

	/**
	 * @author Indium-Baskar date: 08/09/2021 Modified date: NA
	 * @Description : warning message should display for code same as
	 */

	public void verifyWarningMessage() {
		driver.waitForPageToBeReady();
		reusableDocView.editingCodingFormWithCompleteButton();
		reusableDocView.clickGearIconOpenMiniDocList();
		String parentWindow = reusableDocView.switchTochildWindow();
		for (int j = 1; j <= 1; j++) {
			getDocView_MiniDoc_ChildWindow_Selectdoc(j).WaitUntilPresent().Click();
		}
		reusableDocView.verifyWarningMessageClickCodeSameAs();
		reusableDocView.childWindowToParentWindowSwitching(parentWindow);
		base.VerifyWarningMessage(
				"Cannot perform Code Same As action, as the selected documents include one or more completed documents");
		base.passedStep("Warning message displayed for completed document when performing code same as action");
	}

	/**
	 * @author Indium-Baskar date: 08/09/2021 Modified date: NA
	 * @Description : coding stamp icon clicking to verify principal document
	 */

	public void codingStampIconToVerifyPrincipalDocument(String textBox, String colour, String icon) {
		driver.waitForPageToBeReady();
		reusableDocView.stampColourSelection(textBox, colour);
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parentWindow = reusableDocView.switchTochildWindow();
		base.waitForElement(getCodingStampLastIcon(icon));
		getCodingStampLastIcon(icon).waitAndClick(10);
		getCodingStampLastIcon(icon).waitAndClick(10);
		reusableDocView.childWindowToParentWindowSwitching(parentWindow);
		String expectedValue = getVerifyPrincipalDocument().getText().trim();
		base.waitForElement(getDocView_HdrMinDoc());
		getDocView_HdrMinDoc().waitAndClick(5);
		String parentWindows = reusableDocView.switchTochildWindow();
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String expectedValues = getVerifyPrincipalDocument().getText().trim();
		reusableDocView.childWindowToParentWindowSwitching(parentWindows);
		if (expectedValue.equals(expectedValues)) {
			base.passedStep("Verified principal document in both parent and child window");
		} else {
			base.failedStep("Principal document not as per the expected condition");
		}
	}

	/**
	 * @author Indium-Baskar date: 08/09/2021 Modified date: NA
	 * @Description : coding stamp icon clicking to verify principal document
	 */

	public void verifyprincipalDocumentMiniDocList() {
		reusableDocView.enableToggleConfigMiniDocList();
		reusableDocView.editingCodingFormWithCompleteButton();
		driver.waitForPageToBeReady();
		for (int j = 2; j <= 2; j++) {
			base.waitForElement(getDocView_MiniDoc_ChildWindow_Selectdoc(j));
			getDocView_MiniDoc_ChildWindow_Selectdoc(j).WaitUntilPresent().waitAndClick(5);
		}
		reusableDocView.clickCodeSameAsParent();
		base.waitForElement(getCompleteDocBtn());
		getCompleteDocBtn().waitAndClick(5);
		base.passedStep("Tick mark displayed after clicking the complete button");
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String expectedValue = getVerifyPrincipalDocument().getText().trim();
		reusableDocView.clickGearIconOpenMiniDocList();
		String parentWindows = reusableDocView.switchTochildWindow();
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String expectedValues = getVerifyPrincipalDocument().getText().trim();
		reusableDocView.childWindowToParentWindowSwitching(parentWindows);
		if (expectedValue.equals(expectedValues)) {
			base.passedStep("Verified principal document in both parent and child window");
		} else {
			base.failedStep("Principal document not as per the expected condition");
		}
		driver.waitForPageToBeReady();
	}

	public void selectAnalyticsPanelDocsAndViewDocs() {
		try {
			driver.waitForPageToBeReady();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getDocView_Analytics_liDocumentConceptualSimilarab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			getDocView_Analytics_liDocumentConceptualSimilarab().ScrollTo();
			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getDocView_Analytics_liDocumentConceptualSimilarab().Visible()
							&& getDocView_Analytics_liDocumentConceptualSimilarab().Enabled();
				}
			}, Input.wait30);
			getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);
			for (int i = 1; i <= 1; i++) {
				getDocumentConceptuallySimilar(i).waitAndClick(3);
			}
			getDocView_ChildWindow_ActionButton().waitAndClick(10);
			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getAnalyticalDropDown().Visible() && getAnalyticalDropDown().Enabled();
				}
			}, Input.wait30);
			getAnalyticalDropDown().waitAndClick(10);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Doc is viewed from Analytics panel Conceptually Similar tab successfully");
		}

	}

	/**
	 * @author jeevitha Description: Verifies Redaction Panel Modified by-jayanthi
	 *         17/11/21
	 */
	public void verifyRedactionPanel() {

		this.driver.getWebDriver().get(Input.url + "DocumentViewer/DocView");
		try {
			base.waitForElement(getDocumentId2());
			getDocumentId2().waitAndClick(20);
		} catch (Exception e) {

		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_RedactIcon().Visible();
			}
		}), Input.wait60);
		getDocView_RedactIcon().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_AllRedaction().Visible();
			}
		}), Input.wait60);

		System.out.println(getDocView_AllRedaction().getWebElement().getText() + " : "
				+ getDocView_AllRedactionCount().getWebElement().getText());
		UtilityLog.info(getDocView_AllRedaction().getWebElement().getText() + " : "
				+ getDocView_AllRedactionCount().getWebElement().getText());
		softAssertion.assertEquals(getDocView_AllRedaction().Displayed().booleanValue(), true);
		base.ValidateElementCollection_Presence(getDocView_BatchRedaction(),
				"Batch Redaction menu displayed in redaction panel");
		base.ValidateElement_Presence(getDocView_AllRedactionCount(), "All redaction count");
		base.ValidateElement_Presence(getDocView_BatchRedactionCount(), "Batch Redaction count");
		softAssertion.assertAll();
		base.passedStep("All Redactions menu is displayedin redaction panel");
		List<WebElement> batchRedaction = getDocView_BatchRedaction().FindWebElements();
		for (int k = 0; k < batchRedaction.size(); k++) {
			System.out.print(batchRedaction.get(k).getText());
		}
		System.out.print(" : " + getDocView_BatchRedactionCount().getWebElement().getText());
		UtilityLog.info("Count : " + getDocView_BatchRedactionCount().getWebElement().getText());

	}

	/**
	 * @author Indium-Baskar date: 14/09/2021 Modified date: NA
	 * @Description : Both save and complete button saving thr document
	 */

	public void saveAndCompleteButtonPerformCodeSameAs() {
		driver.waitForPageToBeReady();
		for (int j = 1; j <= 1; j++) {
			base.waitForElement(getDocView_MiniDoc_ChildWindow_Selectdoc(j));
			getDocView_MiniDoc_ChildWindow_Selectdoc(j).WaitUntilPresent().waitAndClick(5);
		}
		reusableDocView.clickCodeSameAsParent();
		base.passedStep("Performing code same as action with save button");
		reusableDocView.editingCodingFormWithSaveButton();
		driver.waitForPageToBeReady();
		for (int j = 2; j <= 2; j++) {
			getDocView_MiniDoc_ChildWindow_Selectdoc(j).WaitUntilPresent().Click();
		}
		reusableDocView.clickCodeSameAsParent();
		base.passedStep("performing code same as action with complete button");
		reusableDocView.editingCodingFormWithCompleteButton();
		base.stepInfo("Tick mark icon displayed after completed document");
		for (int i = 2; i <= 2; i++) {
			getClickDocviewID(i).waitAndClick(5);
		}
		if (getUnCompleteButton().Displayed()) {
			softAssertion.assertEquals(getUnCompleteButton().Displayed().booleanValue(), true);
			base.passedStep("Uncomplete button displayed for completed documents ");
		} else {
			base.failedStep("Uncomplete button not displayed for completed documents ");

		}

	}

	/**
	 * @Author Mohan Created on 13/09/2021
	 * @Description To verify Analytics Thread Map Tab with no docs
	 * 
	 */
	public void verifyThreadMapWithNoDocs() {

		try {
			driver.waitForPageToBeReady();
			for (int i = 3; i <= 3; i++) {

				getDocView_MiniDoc_Selectdoc(i).waitAndClick(10);

			}

			driver.waitForPageToBeReady();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			getDocView_Analytics_liDocumentThreadMap().ScrollTo();

			base.waitForElement(getDocView_Analytics_liDocumentThreadMap());
			getDocView_Analytics_liDocumentThreadMap().Click();

			base.waitForElement(getDocView_Analytics_NoQuery());
			softAssertion.assertTrue(getDocView_Analytics_NoQuery().Displayed());
			base.passedStep("Message is displayed as 'Your query returned no data' successfully");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No Doc is viewed from Analytics panel Thread Map tab successfully");
		}

	}

	/**
	 * @Author Mohan Created on 13/09/2021
	 * @Description To verify Analytics Thread Map Tab with more than 20 docs
	 * 
	 */
	public void verifyThreadMapWith20Docs(String text) {

		try {
			driver.waitForPageToBeReady();

			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getMiniDocId(text).Visible() && getMiniDocId(text).Enabled();
				}
			}, Input.wait30);
			getMiniDocId(text).ScrollTo();
			getMiniDocId(text).waitAndClick(10);

			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			getDocView_Analytics_liDocumentThreadMap().ScrollTo();
			base.waitForElement(getDocView_Analytics_liDocumentThreadMap());

			softAssertion.assertTrue(getDocView_Analytics_liDocumentThreadMap().Displayed());
			base.passedStep(
					"Default 20 documents are displayed on thread map analytics panel.  Link to 'View All' documents is enabled successfully");

			getDocView_Analytics_ThreadMap20PlusDocs().ScrollTo();
			base.waitForElement(getDocView_Analytics_ThreadMap20PlusDocs());
			driver.waitForPageToBeReady();
			getDocView_Analytics_ThreadMap20PlusDocs().waitAndClick(5);
			softAssertion.assertTrue(getDocView_Analytics_ThreadMap20PlusDocs().Displayed());
			base.passedStep("All threaded documents is load in the thread map tab/panel successfully");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Doc are viewed from Analytics panel Thread Map tab successfully");
		}
	}

	/**
	 * @Author Mohan Created on 13/09/2021
	 * @Description To verify Analytics Thread Map Tab with docsId
	 * 
	 */
	public void verifyThreadMapDocId() {

		try {
			driver.waitForPageToBeReady();

			for (int i = 6; i <= 6; i++) {
				getDocView_MiniDoc_Selectdoc(i).ScrollTo();
				getDocView_MiniDoc_Selectdoc(i).waitAndClick(10);
			}
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			getDocView_Analytics_liDocumentThreadMap().ScrollTo();
			driver.waitForPageToBeReady();
			for (int i = 2; i <= 2; i++) {
				getDocView_Analytics_ThreadMap_DocId(i).ScrollTo();
				softAssertion.assertTrue(getDocView_Analytics_ThreadMap_DocId(i).Displayed());
				base.passedStep("Threaded documents for the selected document is displayed successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Doc are viewed in Analytics panel Thread Map tab successfully");
		}

	}

	/**
	 * @Author Mohan Created on 14/09/2021
	 * @Description To verify Analytics Threaded docs from DocList
	 * 
	 */
	public void verifyThreadedDocsFromDocList() {

		try {
			driver.waitForPageToBeReady();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			getDocView_Analytics_liDocumentThreadMap().ScrollTo();
			driver.waitForPageToBeReady();
			for (int i = 2; i <= 2; i++) {
				getDocView_Analytics_ThreadMap_DocId(i).ScrollTo();
				softAssertion.assertTrue(getDocView_Analytics_ThreadMap_DocId(i).Displayed());
				base.passedStep("Threaded documents for the selected document is displayed successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Doc are viewed in Analytics panel Thread Map tab successfully");
		}

	}

	/**
	 * @Author Mohan Created on 14/09/2021
	 * @Description To verify Docs in MiniDocList
	 * 
	 */
	public void verifyDocsInMiniDocList() {

		try {

			for (int i = 6; i <= 6; i++) {
				driver.waitForPageToBeReady();
				base.waitForElement(getDocView_MiniDoc_Selectdoc(i));
//				getDocView_MiniDoc_Selectdoc(i).ScrollTo();
				String miniDocId = getDocView_MiniDoc_Selectdoc(i).getText();
				getDocView_MiniDoc_Selectdoc(i).waitAndClick(5);
				driver.waitForPageToBeReady();
				JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
				driver.waitForPageToBeReady();
				Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
				je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
//				getDocView_Analytics_liDocumentThreadMap().ScrollTo();
				for (int j = 2; j <= 2; j++) {
//					getDocView_Analytics_ThreadMap_DocId(j).ScrollTo();
					String threadMapTabDocId = getDocView_Analytics_ThreadMap_DocId(j).getText();
					softAssertion.assertEquals(miniDocId, threadMapTabDocId);
					base.passedStep(
							"Document displayed in doc view panel and threaded documents on thread map tab are verified successfully");
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Doc are viewed in Analytics panel Thread Map tab successfully");
		}
	}

	/**
	 * @Author Mohan Created on 14/09/2021
	 * @Description To select docs from Analytics Thread Map Tab
	 * 
	 */
	public void selectDocsFromThreadMapTab() {
		try {
			for (int i = 2; i <= 2; i++) {
				base.waitForElement(getDocView_Analytics_ThreadMap_EmailDocs(i));
				getDocView_Analytics_ThreadMap_EmailDocs(i).waitAndClick(5);
			}
			base.waitForElement(getDocView_ChildWindow_ActionButton());
			getDocView_ChildWindow_ActionButton().waitAndClick(5);

			base.waitForElement(getAnalyticalDropDown());
			softAssertion.assertTrue(getAnalyticalDropDown().Displayed());
			getAnalyticalDropDown().waitAndClick(5);
			base.stepInfo("'View Document' action is displayed on thread map successfully");

			driver.scrollPageToTop();

			base.waitForElement(getDocView_CurrentDocId());
			softAssertion.assertTrue(getDocView_CurrentDocId().Displayed());
			base.stepInfo(
					"On selecting document and view document action from thread map is displayed in doc view panel with complete DocID successfully");
			base.passedStep("Both the DocIds are verified successfully");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Doc are verified successfully");
		}
	}

	/**
	 * @Author Mohan Created on 13/09/2021
	 * @Description To select Analytics NearDupe Tab
	 * 
	 */

	public void selectAnalyticsNearDupeTab() {

		try {
			driver.waitForPageToBeReady();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			// getDocView_Analytics_NearDupeTab().ScrollTo();
			getDocView_Analytics_NearDupeTab().Click();

			driver.scrollPageToTop();
			for (int i = 3; i <= 3; i++) {
				driver.waitForPageToBeReady();
				base.waitForElement(getDocView_MiniDoc_Selectdoc(i));
//				getDocView_MiniDoc_Selectdoc(i).ScrollTo();
				getDocView_MiniDoc_Selectdoc(i).waitAndClick(5);
				base.stepInfo("Navigates to other document from Mini doc is done successfully");

			}
			JavascriptExecutor js = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p1 = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
			js.executeScript("window.scroll(" + p1.getX() + "," + (p1.getY() - 400) + ");");
//			getDocView_Analytics_NearDupeTab().ScrollTo();
			getDocView_Analytics_NearDupeTab().waitAndClick(10);
			softAssertion.assertTrue(getDocView_Analytics_NearDupeTab().Displayed());
			base.passedStep(
					"Navigates to other  document from Mini doc list and verify analytics panel is done Successfully");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Doc are verified successfully");
		}
	}

	/**
	 * @Author Mohan Created on 15/09/2021
	 * @Description To select docs and verify In Meta data panel
	 * 
	 */
	public void selectDocAndVerifyInMetaData(String text) {

		try {

			driver.waitForPageToBeReady();
			for (int i = 2; i <= 2; i++) {
				driver.waitForPageToBeReady();
				base.waitForElement(getDocView_MiniDoc_Selectdoc(i));
				getDocView_MiniDoc_Selectdoc(i).ScrollTo();
				getDocView_MiniDoc_Selectdoc(i).waitAndClick(5);
			}

			driver.waitForPageToBeReady();
			base.waitForElement(getMetaDataDocId(text));
			getMetaDataDocId(text).ScrollTo();
			String text2 = getMetaDataDocId(text).getText();
			softAssertion.assertEquals(text, text2);
			base.passedStep(
					"Metadata panel for AttachDocID document ID which is an attachment for the document is displayed successfully");

			driver.waitForPageToBeReady();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			getDocView_Analytics_liDocumentThreadMap().ScrollTo();

			base.waitForElement(getDocView_Analytics_liDocumentThreadMap());
			getDocView_Analytics_liDocumentThreadMap().Click();

			base.waitForElement(getDocView_Analytics_NoQuery());
			softAssertion.assertTrue(getDocView_Analytics_NoQuery().Displayed());
			base.passedStep("Message is displayed as 'Your query returned no data' successfully");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Doc are verified successfully");
		}

	}

	/**
	 * @Author Mohan Created on 15/09/2021
	 * @Description To select docs and verify In Meta data panel
	 * 
	 */
	public void dragAndPlaceAnalyticsWidget() {

		try {

			driver.waitForPageToBeReady();
			base.waitForElement(getGearIcon());
			getGearIcon().waitAndClick(5);

			driver.waitForPageToBeReady();
			WebElement source = (driver.FindElementByXPath("//h2[text()='Document Analytics']")).getWebElement();
			WebElement destination = (driver.FindElementByXPath("//h2[text()='Document Data Detail']")).getWebElement();
			Actions act = new Actions(driver.getWebDriver());
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			getDocView_Analytics_liDocumentThreadMap().ScrollTo();
			act.moveToElement(source).perform();
			act.clickAndHold().perform();
			driver.scrollPageToTop();
			act.moveToElement(destination).perform();
			act.release(source);
			act.build().perform();

			softAssertion.assertTrue(getDocView_Analytics_liDocumentThreadMap().Displayed());
			base.passedStep("Drag and place the analytics panel is done successfully");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Panel is Dragged and placed");
		}
	}

	/**
	 * @Author Mohan Created on 15/09/2021
	 * @Description To select docs and verify In Meta data panel
	 * 
	 */
	public void checkingDocsAndVerifyHorizontalScrollBar(String documentToBeScrolled) {

		try {

			for (int i = 6; i <= 6; i++) {
				driver.waitForPageToBeReady();
				base.waitForElement(getDocView_MiniDoc_Selectdoc(i));
				getDocView_MiniDoc_Selectdoc(i).ScrollTo();
				getDocView_MiniDoc_Selectdoc(i).waitAndClick(10);
			}

			for (int j = 11; j <= 11; j++) {
				driver.waitForPageToBeReady();
				base.waitForElement(getDocView_Analytics_ThreadMap_EmailDocs(j));
				getDocView_Analytics_ThreadMap_EmailDocs(j).ScrollTo();
				getDocView_Analytics_ThreadMap_EmailDocs(j).waitAndClick(5);
				softAssertion.assertTrue(getDocView_AnalyticsDocId(documentToBeScrolled).Displayed());
				base.passedStep(
						"Document is selected with the checked checkbox and horizontal scroll bar is continue to be at the same position that it was when the document was selected.");
				getDocView_Analytics_ThreadMap_EmailDocs(j).waitAndClick(7);
				softAssertion.assertTrue(getDocView_AnalyticsDocId(documentToBeScrolled).Displayed());
				base.stepInfo(
						"Document is unchecked after moving scroll bar to right then horizontal scroll bar is at same position it was when document was unchecked.");
			}

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Horizontal scroll bar is verified");
		}

	}

	/**
	 * @Author Mohan Created on 15/09/2021
	 * @Description To select docs and verify In Meta data panel with child window
	 * 
	 */
	public void checkingDocsAndVerifyHorizontalScrollBarWithChildWindow(String documentToBeScrolled) {

		try {

			for (int i = 6; i <= 6; i++) {
				driver.waitForPageToBeReady();
				base.waitForElement(getDocView_MiniDoc_Selectdoc(i));
				getDocView_MiniDoc_Selectdoc(i).ScrollTo();
				getDocView_MiniDoc_Selectdoc(i).waitAndClick(10);
			}

			String parentWindowID = driver.getWebDriver().getWindowHandle();
			driver.waitForPageToBeReady();
			base.waitForElement(getDocView_HdrAnalytics());
			driver.scrollPageToTop();
			getDocView_HdrAnalytics().waitAndClick(10);

			Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
			for (String eachId : allWindowsId) {
				if (!parentWindowID.equals(eachId)) {
					driver.switchTo().window(eachId);
				}
			}

			String analyticsWindowID = driver.getWebDriver().getWindowHandle();

			for (int j = 20; j <= 20; j++) {
				driver.waitForPageToBeReady();
				getDocView_Analytics_ThreadMap_EmailDocs(j).ScrollTo();
				getDocView_Analytics_ThreadMap_EmailDocs(j).waitAndClick(10);
				softAssertion.assertTrue(getDocView_AnalyticsDocId(documentToBeScrolled).Displayed());
				base.passedStep(
						"Document is selected with the checked checkbox and horizontal scroll bar is continue to be at the same position that it was when the document was selected.");
				getDocView_Analytics_ThreadMap_EmailDocs(j).waitAndClick(5);
				softAssertion.assertTrue(getDocView_AnalyticsDocId(documentToBeScrolled).Displayed());
				base.stepInfo(
						"Document is unchecked after moving scroll bar to right then horizontal scroll bar is at same position it was when document was unchecked.");
			}
			driver.getWebDriver().close();
			driver.switchTo().window(parentWindowID);

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Horizontal scroll bar is verified");
		}

	}

	/**
	 * @Author Mohan Created on 15/09/2021
	 * @Description To select docs and verify In Analytics panel with child window
	 * 
	 */
	public void VerifyHorizontalScrollBarWithChildWindow(String documentToBeScrolled) {

		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getGearIcon());
			getGearIcon().waitAndClick(5);

			for (int i = 6; i <= 6; i++) {
				driver.waitForPageToBeReady();
				base.waitForElement(getDocView_MiniDoc_Selectdoc(i));
				getDocView_MiniDoc_Selectdoc(i).ScrollTo();
				getDocView_MiniDoc_Selectdoc(i).waitAndClick(10);
			}

			String parentWindowID = driver.getWebDriver().getWindowHandle();
			driver.waitForPageToBeReady();

			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			getDocView_HdrAnalytics().ScrollTo();
			base.waitForElement(getDocView_HdrAnalytics());
			getDocView_HdrAnalytics().waitAndClick(10);

			Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
			for (String eachId : allWindowsId) {
				if (!parentWindowID.equals(eachId)) {
					driver.switchTo().window(eachId);
				}
			}

			String analyticsWindowID = driver.getWebDriver().getWindowHandle();

			for (int j = 11; j <= 11; j++) {
				driver.waitForPageToBeReady();
				getDocView_Analytics_ThreadMap_EmailDocs(j).ScrollTo();
				getDocView_Analytics_ThreadMap_EmailDocs(j).waitAndClick(10);
				softAssertion.assertTrue(getDocView_AnalyticsDocId(documentToBeScrolled).Displayed());
				base.passedStep(
						"Document is selected with the checked checkbox and horizontal scroll bar is continue to be at the same position that it was when the document was selected.");
				getDocView_Analytics_ThreadMap_EmailDocs(j).waitAndClick(5);
				softAssertion.assertTrue(getDocView_AnalyticsDocId(documentToBeScrolled).Displayed());
				base.stepInfo(
						"Document is unchecked after moving scroll bar to right then horizontal scroll bar is at same position it was when document was unchecked.");
			}

			driver.switchTo().window(parentWindowID);

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Horizontal scroll bar is verified");
		}

	}

	/**
	 * @author Gopinath modified By-Gopinath Modified date- NA
	 * @Description :: Method to verify 'Code this Document is same as last
	 *              document' message by mouse over on last code white pencil.
	 * @param expectedMessage :: (expectedMessage is string value that expected
	 *                        message need to get when mousehover on last code white
	 *                        pencil).
	 */
	public void verifyCodeSameAsLastDocMsgIsDisplayed(String expectedMessage) {
		try {
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			base.waitForElement(getLastCodeIconWhitePencil());
			Actions builder = new Actions(driver.getWebDriver());
			builder.moveToElement(getLastCodeIconWhitePencil().getWebElement()).build().perform();
			base.waitForElement(getCodeSameAsLastDocMessage());
			System.out.println("Got mesage - " + getCodeSameAsLastDocMessage().getText());
			Assert.assertEquals(getCodeSameAsLastDocMessage().getWebElement().isDisplayed(), true,
					"Code this Document is same as last document message is not displayed");
			String message = getCodeSameAsLastDocMessage().getText().trim();
			if (message.trim().equalsIgnoreCase(expectedMessage.trim())) {
				base.passedStep("Code this Document is same as last coded document message is displayed successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verify 'Code this Document is same as last document' message by mouse over on last code white pencil"
							+ e.getMessage());
		}
	}

	/**
	 * @author Gopinath modified By-Gopinath Modified date- NA
	 * @Description :: Method to verify 'Code this Document is same as last
	 *              document' message by mouse over on last code white pencil.
	 * @param expectedMessage :: (expectedMessage is string value that expected
	 *                        message need to get when mousehover on last code white
	 *                        pencil).
	 */
	public void verifyCodeSameAsLastDocMsgIsDisplayedOnChildWindow(String expectedMessage) {
		try {
			Actions builder = new Actions(driver.getWebDriver());
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			base.waitForElement(getGearIcon1());
			getGearIcon1().Click();
			String parentWindowID = driver.getWebDriver().getWindowHandle();
			builder.moveToElement(getPersistantHitEyeIcon().getWebElement()).build().perform();
			base.waitForElement(getPopoutIcon());
			getPopoutIcon().Click();
			Set<String> childWindowIDs = driver.getWebDriver().getWindowHandles();
			for (String codingFormChildWindow : childWindowIDs) {
				if (!parentWindowID.equals(codingFormChildWindow)) {
					driver.switchTo().window(codingFormChildWindow);
				}
			}
			base.waitForElement(getLastCodeIconWhitePencil());
			builder.moveToElement(getLastCodeIconWhitePencil().getWebElement()).build().perform();
			base.waitForElement(getCodeSameAsLastDocMessage());
			System.out.println("Got message - " + getCodeSameAsLastDocMessage().getText());
			Assert.assertEquals(getCodeSameAsLastDocMessage().getWebElement().isDisplayed(), true,
					"Code this Document is same as last document message is not displayed");
			String message = getCodeSameAsLastDocMessage().getText().trim();
			if (message.trim().equalsIgnoreCase(expectedMessage.trim())) {
				base.passedStep("Code this Document is same as last coded document message is displayed successfully");
			}
			driver.close();
			driver.switchTo().window(parentWindowID);
			base.waitForElement(getDocView_SaveWidgetButton());
			getDocView_SaveWidgetButton().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occcured while verify 'Code this Document is same as last document' message by mouse over on last code white pencil"
							+ e.getMessage());
		}
	}

	/**
	 * @author Indium-Baskar date: 14/09/2021 Modified date: NA
	 * @Description : verifying both parent and child window by using coding stamp
	 */

	public void codeSameAsLastHistoryDropDown() {
		driver.waitForPageToBeReady();
		reusableDocView.editingCodingFormWithSaveAndNextButton();
		String expectedValue = getVerifyPrincipalDocument().getText().trim();
		base.waitForElement(getDocView_HistoryButton());
		getDocView_HistoryButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		String expectedValues = getHistoryDrp_Dwn().getText().trim();
		if (expectedValue.equals(expectedValues)) {
			base.waitForElement(getHistoryDrp_Dwn());
			getHistoryDrp_Dwn().waitAndClick(5);
		}
		base.passedStep("User selected the document from history drop down");
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_DefaultViewTab());
		softAssertion.assertEquals(getDocView_DefaultViewTab().Enabled().booleanValue(), true);
		base.passedStep("Document displaying in default view page");
		String expectedLast = getVerifyPrincipalDocument().getText().trim();
		base.waitForElement(getCodeSameAsLast());
		getCodeSameAsLast().waitAndClick(10);
		base.VerifySuccessMessage("Coded as per the coding form for the previous document");
		driver.waitForPageToBeReady();
		base.waitForElement(getMiniDocListText(expectedLast));
		getMiniDocListText(expectedLast).waitAndClick(10);
		reusableDocView.VerifyTheDocument();
		base.stepInfo("Cursor moved to the next docs in minidoclist");
	}

	/**
	 * @author Indium-Baskar date: 14/09/2021 Modified date: NA
	 * @Description : veiw minidoclist last document
	 */

	public void codeSameAsLastInMiniDocListLastDocument() {
		driver.waitForPageToBeReady();
		reusableDocView.editingCodingFormWithSaveAndNextButton();
		// No.of rows
		base.waitForElementCollection(getDocView_MiniListDocuments());
		List rows = getDocView_MiniListDocuments().FindWebElements();
		System.out.println(rows);
		int lastRow = rows.size();
		base.stepInfo("No of rows are : " + rows.size());
		base.waitForElement(getClickDocviewID(lastRow));
		getClickDocviewID(lastRow).waitAndClick(5);
		base.passedStep("User clicked last document from minidoclist : " + lastRow);
		base.waitForElement(getCodeSameAsLast());
		getCodeSameAsLast().waitAndClick(10);
		base.VerifySuccessMessage("Coded as per the coding form for the previous document");
		base.stepInfo("Document is saved with last applied coding of the document");

	}

	/**
	 * @author Indium-Baskar date: 15/09/2021 Modified date: NA
	 * @Description : click code same as last in child window
	 */

	public void codeSameAsLastSelectDocInDocList(String fieldValue, String icon, String lastIcon, String lastIcons) {
		driver.waitForPageToBeReady();
		reusableDocView.docListCheckBoxAndViewInDocView();
		reusableDocView.editingCodingFormWithSaveButton();
		reusableDocView.codingFormSavingWithCodingStamp(fieldValue, icon);
		for (int i = 2; i <= 2; i++) {
			getClickDocviewID(i).waitAndClick(5);
		}
		base.waitForElement(getCodingStampLastIcon(lastIcon));
		getCodingStampLastIcon(lastIcon).waitAndClick(10);
		base.waitForElement(getCodingFormSaveButton());
		getCodingFormSaveButton().waitAndClick(5);
		base.stepInfo("Document saved successfully");
		reusableDocView.clickGearIconOpenMiniDocList();
		String parentWindow = reusableDocView.switchTochildWindow();
		for (int j = 4; j <= 4; j++) {
			getDocView_MiniDoc_ChildWindow_Selectdoc(j).WaitUntilPresent().Click();
		}
		reusableDocView.clickCodeSameAs();
		reusableDocView.childWindowToParentWindowSwitching(parentWindow);
		base.waitForElement(getDocView_HdrCoddingForm());
		getDocView_HdrCoddingForm().waitAndClick(5);
		String parentWindows = reusableDocView.switchTochildWindow();
		driver.waitForPageToBeReady();
		reusableDocView.clickCodeSameAsLast();
		reusableDocView.childWindowToParentWindowSwitching(parentWindows);
		driver.Navigate().refresh();
		driver.switchTo().alert().accept();
		driver.waitForPageToBeReady();
		reusableDocView.deleteStampColour(lastIcons);
		base.failedStep("Expected Failure:Code same as last button is not clickable in child window");
		driver.waitForPageToBeReady();
		for (int i = 4; i <= 4; i++) {
			getClickDocviewID(i).waitAndClick(5);
		}
		reusableDocView.VerifyTheDocument();
		base.stepInfo("Document verified for code same as last button as per the previous docs");
		for (int i = 2; i <= 2; i++) {
			getClickDocviewID(i).waitAndClick(5);
		}
		base.waitForElement(selectCodingFormCheckBoxes("Not_Responsive"));
		selectCodingFormCheckBoxes("Not_Responsive").waitAndClick(10);
		base.waitForElement(getCodingFormSaveButton());
		getCodingFormSaveButton().waitAndClick(5);
		base.stepInfo("Document saved successfully");
		reusableDocView.clickCodeSameAsLast();
		base.stepInfo("Document is sync fot both parent and child window in minidoclist");
		reusableDocView.deleteStampColour(lastIcons);
		driver.waitForPageToBeReady();

	}

	/**
	 * @author Indium-Baskar date: 15/09/2021 Modified date: NA
	 * @Description : afterLoadingclicklastdocument
	 */

	public void loadingDisplayLastDocumentBtnClick() throws InterruptedException {
		driver.waitForPageToBeReady();
		reusableDocView.editingCodingFormWithSaveAndNextButton();
		List<WebElement> scrollTillLast = getDocumetCountMiniDocList().FindWebElements();
		for (int j = 0; j < scrollTillLast.size(); j++) {
			if (j == 15) {
				JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();
				jse.executeScript("document.querySelector('.dataTables_scrollBody').scrollBy(0,4000)");
				softAssertion.assertEquals(getDocumetListLoading().Displayed(), true);
				driver.waitForPageToBeReady();
			} else {
			}
		}

		base.passedStep("While scrolling the document loading dispalyed");
		for (int i = 60; i <= 60; i++) {
			getClickDocviewID(i).waitAndClick(5);
			base.stepInfo("After loading displays code same as last clickable in parent window");
		}
		driver.waitForPageToBeReady();

	}

	/**
	 * @author Indium-Baskar date: 15/09/2021 Modified date: NA
	 * @Description : this method used for code same as minidoclist
	 */

	public void codeSameAsMiniDocList(String fieldValue, String icon, String lastIcon, String lastIcons)
			throws InterruptedException {
		driver.waitForPageToBeReady();
		reusableDocView.editingCodingFormWithSaveButton();
		reusableDocView.codingFormSavingWithCodingStamp(fieldValue, icon);
		base.waitForElement(getCodingStampLastIcon(lastIcon));
		getCodingStampLastIcon(lastIcon).waitAndClick(10);
		base.waitForElement(getCodingFormSaveButton());
		getCodingFormSaveButton().waitAndClick(5);
		base.stepInfo("Document saved successfully");
		driver.waitForPageToBeReady();
		String principalText = getVerifyPrincipalDocument().getText().trim();
		for (int j = 2; j <= 2; j++) {
			getDocView_MiniDoc_ChildWindow_Selectdoc(j).WaitUntilPresent().Click();
		}
		reusableDocView.clickCodeSameAs();
		reusableDocView.clickCodeSameAsLast();
		driver.waitForPageToBeReady();
		String actualValue = getVerifyPrincipalDocument().getText().trim();
		softAssertion.assertEquals(principalText, actualValue);
		driver.waitForPageToBeReady();
		getMiniDocListText(principalText).WaitUntilPresent().ScrollTo();
		getMiniDocListText(principalText).waitAndClick(10);
		reusableDocView.VerifyTheDocument();
		base.stepInfo("Document verified for code same as last button as per the previous docs");
		base.waitForElement(selectCodingFormCheckBoxes("Not_Responsive"));
		selectCodingFormCheckBoxes("Not_Responsive").waitAndClick(10);
		base.waitForElement(getCodingFormSaveButton());
		getCodingFormSaveButton().waitAndClick(10);
		softAssertion.assertEquals(getCodingFormSaveButton().Displayed().booleanValue(), true);
		base.stepInfo("Excepted Message:Document completed successfully");
		reusableDocView.clickCodeSameAsLast();
		getHeader().waitAndClick(5);
		reusableDocView.deleteStampColour(lastIcons);
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium-Baskar date: 15/09/2021 Modified date: NA
	 * @Description : this method used for code same as last childwindow
	 */

	public void codeSameAsLastChildWindow(String fieldValue, String icon, String lastIcon, String lastIcons)
			throws InterruptedException, AWTException {
		driver.waitForPageToBeReady();
		reusableDocView.editingCodingFormWithSaveButton();
		reusableDocView.codingFormSavingWithCodingStamp(fieldValue, icon);
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String principalExp = getVerifyPrincipalDocument().getText().trim();
		base.waitForElement(getCodingStampLastIcon(lastIcon));
		getCodingStampLastIcon(lastIcon).waitAndClick(10);
		base.waitForElement(getCodingFormSaveButton());
		getCodingFormSaveButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.stepInfo("Document saved successfully");
		reusableDocView.clickCodeSameAsLast();
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String principalAfterLastBtn = getVerifyPrincipalDocument().getText().trim();
		softAssertion.assertNotEquals(principalExp, principalAfterLastBtn);
		for (int i = 5; i <= 5; i++) {
			getClickDocviewID(i).waitAndClick(5);
		}
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String docsText = getVerifyPrincipalDocument().getText().trim();
		reusableDocView.clickCodeSameAsLast();
		String principalAgainAfterLastBtn = getVerifyPrincipalDocument().getText().trim();
		softAssertion.assertNotEquals(docsText, principalAgainAfterLastBtn);
		getHeader().waitAndClick(5);
		reusableDocView.deleteStampColour(lastIcons);
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium-Baskar date: 21/09/2021 Modified date: NA
	 * @Description : save and next should not display as reviewer
	 */
	public void shouldNotDisplaySaveAndNext() {
		driver.waitForPageToBeReady();
		softAssertion.assertEquals(getSaveAndNextButton().Displayed().booleanValue(), true);
		if (getSaveAndNextButton().Displayed()) {
			base.failedStep("Save and next displayed for reviewer");
		} else {
			base.passedStep("Reviewer while selecting assignment from dashboard Save and next is not displayed");

		}

	}

	/**
	 * @author Indium-Baskar date: 21/09/2021 Modified date: NA
	 * @Description : navigation from doclist coding stamp should display
	 */

	public void navigateFromDocListCodingStampShouldDisplay() {
		driver.waitForPageToBeReady();
		reusableDocView.docListCheckBoxAndViewInDocView();
		base.waitForElement(getCodingFormStampButton());
		getCodingFormStampButton().waitAndClick(10);
		softAssertion.assertEquals(getCodingFormStampButton().Displayed().booleanValue(), true);
		base.passedStep("Coding stamp displayed");
		base.waitForElement(getCodingStampCancel());
		getCodingStampCancel().waitAndClick(5);
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium-Baskar date: 21/09/2021 Modified date: NA
	 * @Description : navigation from doclist coding stamp should display
	 */

	public void navigateFromSavedSearchCodingStampShouldDisplay() {
		driver.waitForPageToBeReady();
		base.waitForElement(getCodingFormStampButton());
		getCodingFormStampButton().waitAndClick(10);
		softAssertion.assertEquals(getCodingFormStampButton().Displayed().booleanValue(), true);
		base.passedStep("Coding stamp displayed");
		base.waitForElement(getCodingStampCancel());
		getCodingStampCancel().waitAndClick(10);
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium-Baskar date: 21/09/2021 Modified date: NA
	 * @Description : code same as from minidoclist coding stamp last icon to click
	 */

	public void codeSameAsMiniDocListStampLastIcon(String fieldValue, String icon, String lastIcon, String lastIcons) {
		driver.waitForPageToBeReady();
		base.waitForElementCollection(getMiniDocListDocIdText());
		String docId = getVerifyPrincipalDocument().getText().trim();
		reusableDocView.editingCodingFormWithSaveButton();
		reusableDocView.codingFormSavingWithCodingStamp(fieldValue, icon);
		reusableDocView.errorMessageForStamp();
		base.stepInfo("Coding stamp saved for:" + docId);
		String checkBoxText = reusableDocView.miniDocListElementText(2);
		getMiniDocListTextChck_Box(checkBoxText).WaitUntilPresent().ScrollTo();
		base.waitForElement(getMiniDocListTextChck_Box(checkBoxText));
		getMiniDocListTextChck_Box(checkBoxText).waitAndClick(10);
		base.stepInfo("Code same as performed for document Id:" + checkBoxText);
		base.waitForElement(getCodingStampLastIcon(lastIcon));
		getCodingStampLastIcon(lastIcon).waitAndClick(10);
		base.stepInfo("Coding stamp values are loaded when clicking the saved stamp");
		reusableDocView.VerifyTheDocument();
		reusableDocView.deleteStampColour(lastIcons);
		driver.waitForPageToBeReady();
	}

	/**
	 * @Author Mohan Created on 15/09/2021
	 * @Description To select docs and complete docs In MiniDoclist
	 * 
	 */
	public void selectDocsFromMiniDocListCompletedDoc() {

		try {
			for (int i = 20; i <= 20; i++) {
				for (int j = 2; j <= 2; j++) {
					driver.waitForPageToBeReady();
					base.waitForElement(getDocView_MiniDoc_SelectdocAsText(i, j));
					getDocView_MiniDoc_SelectdocAsText(i, j).ScrollTo();
					String text = getDocView_MiniDoc_SelectdocAsText(i, j).getText();
					System.out.println(text);
					getDocView_MiniDoc_SelectdocAsText(i, j).waitAndClick(10);
					base.waitForElement(getCompleteDocBtn());
					getCompleteDocBtn().waitAndClick(5);
					base.passedStep("Doc is completed successfully");

				}
			}

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Docs are not completed");
		}

	}

	/**
	 * @Author Mohan Created on 15/09/2021
	 * @Description To verify Warning Message
	 * 
	 */
	public void verifyWarningMessageCodeAsSameIsSelectedCompletedDocs() {

		try {
			driver.waitForPageToBeReady();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getDocView_Analytics_NearDupeTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			base.waitForElement(getDocView_Analytics_NearDupeTab());
			getDocView_Analytics_NearDupeTab().waitAndClick(5);

			for (int i = 2; i <= 2; i++) {
				base.waitForElement(getDocView_Analytics_NearDupe_Text(i));
				String text = getDocView_Analytics_NearDupe_Text(i).getText();
				System.out.println(text);

			}
			for (int i = 1; i <= 1; i++) {
				base.waitForElement(getDocView_Analytics_NearDupe_Doc(i));
				getDocView_Analytics_NearDupe_Doc(i).waitAndClick(5);
			}

			base.waitForElement(getDocView_ChildWindow_ActionButton());
			getDocView_ChildWindow_ActionButton().waitAndClick(5);

			base.waitForElement(getDocView_NearDupeCodeSameAs());
			getDocView_NearDupeCodeSameAs().waitAndClick(5);

			base.VerifyWarningMessage(
					"Cannot perform Code Same As action, as the selected documents include one or more completed documents");
			base.passedStep("Warning message is displayed sucessfully");

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Docs are not completed");
		}

	}

	/**
	 * @Author Mohan Created on 15/09/2021
	 * @Description To click on gear icon in Docview Page
	 * 
	 */
	public void selectGearIcon() {

		try {
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			base.waitForElement(getGearIcon());
			getGearIcon().waitAndClick(10);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Gear Icon is not selected");
		}

	}

	/**
	 * @Author Mohan Created on 15/09/2021
	 * @Description To verify Warning Message with child window Test Case id:
	 *              RPMXCON-51412
	 * 
	 */
	public void verifyWarningMessageWithChildWindow() {

		try {
			driver.waitForPageToBeReady();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getDocView_Analytics_NearDupeTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			getDocView_HdrAnalytics().ScrollTo();
			base.waitForElement(getDocView_HdrAnalytics());
			getDocView_HdrAnalytics().waitAndClick(10);

			String parentWindowID = driver.getWebDriver().getWindowHandle();

			Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
			for (String eachId : allWindowsId) {
				if (!parentWindowID.equals(eachId)) {
					driver.switchTo().window(eachId);
				}
			}
			base.waitForElement(getDocView_Analytics_NearDupeTab());
			getDocView_Analytics_NearDupeTab().waitAndClick(7);
			for (int i = 2; i <= 2; i++) {
				driver.waitForPageToBeReady();
				base.waitForElement(getDocView_Analytics_NearDupe_Text(i));
				String text = getDocView_Analytics_NearDupe_Text(i).getText();
				System.out.println(text);

			}

			for (int i = 1; i <= 1; i++) {
				base.waitForElement(getDocView_Analytics_NearDupe_Doc(i));
				getDocView_Analytics_NearDupe_Doc(i).waitAndClick(5);
			}

			base.waitForElement(getDocView_ChildWindow_ActionButton());
			getDocView_ChildWindow_ActionButton().waitAndClick(5);

			base.waitForElement(getDocView_NearDupeCodeSameAs());
			getDocView_NearDupeCodeSameAs().waitAndClick(5);

			driver.switchTo().window(parentWindowID);

			driver.waitForPageToBeReady();
			base.VerifyWarningMessage(
					"Cannot perform Code Same As action, as the selected documents include one or more completed documents");
			base.passedStep("Warning message is displayed sucessfully");

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Docs are not completed");
		}

	}

	/**
	 * @Author Mohan Created on 15/09/2021
	 * @Description To verify Meta data panel is displayed in the DocView Test Case
	 *              id: RPMXCON-50960
	 * 
	 */
	public void verifyMetaDataPanelInDocView() {

		try {

			for (int i = 3; i <= 3; i++) {
				driver.waitForPageToBeReady();
				base.waitForElement(getDocView_MiniDoc_Selectdoc(i));
				getDocView_MiniDoc_Selectdoc(i).ScrollTo();
				getDocView_MiniDoc_Selectdoc(i).waitAndClick(10);
			}

			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			try {
				getDocView_MetaDataIcon().getWebElement().isDisplayed();
			} catch (Exception e) {
				driver.scrollingToBottomofAPage();
			}

			base.waitForElement(getDocView_MetaDataIcon());
			getDocView_MetaDataIcon().waitAndClick(10);

			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			try {
				getDocView_MetaDataTotalFields().getWebElement().isDisplayed();

			} catch (Exception e) {
				driver.scrollingToBottomofAPage();
			}
			Thread.sleep(5000);
			base.waitForElement(getDocView_MetaDataTotalFields());
			softAssertion.assertTrue(getDocView_MetaDataTotalFields().Displayed());
			base.passedStep("Details in Analytical and MetaData panel is displayed for selected document successfully");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Meta Data penel is not present");

		}

	}

	/**
	 * @Author Mohan Created on 27/09/2021
	 * @Description To perform CodeSame Conceputually docs in the DocView Test Case
	 *              id: RPMXCON-51382 and 51383
	 *
	 */
	public void performCodeSameForConceptualDocuments() throws InterruptedException {
		JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_Analytics_liDocumentConceptualSimilarab());
		Point p = getDocView_Analytics_liDocumentConceptualSimilarab().getWebElement().getLocation();
		je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");

		base.waitForElement(getDocView_Analytics_liDocumentConceptualSimilarab());
		getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);

		base.waitForElement(getDocView_Analytics_Conceptual_FirstDoc());
		getDocView_Analytics_Conceptual_FirstDoc().waitAndClick(10);
		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(15);

		getDocView_Analytics_Concept_Similar_CodeSameAs().waitAndClick(15);
		base.VerifySuccessMessage("Code same performed successfully.");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return geDocView_ConceptuallySimilar_CodeSameAsIcon().Displayed();
			}
		}), Input.wait30);
		softAssertion.assertEquals(geDocView_ConceptuallySimilar_CodeSameAsIcon().Displayed().booleanValue(), true,
				"1");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getConceptDocumentWhichHasCodeSameIcon().Displayed();
			}
		}), Input.wait30);

		codeSameDocumentid = getConceptDocumentWhichHasCodeSameIcon().getText();

		softAssertion.assertEquals(getCodeSameIconMiniDocList().Displayed().booleanValue(), true, "2");

	}

	/**
	 * @Author Mohan Created on 27/09/2021
	 * @Description To verify CodeSame Conceputually docs is displayed in the
	 *              DocView. Test Case id: RPMXCON-51382 and 51383
	 * 
	 */
	public void verifyWhetherCodeSameIconIsDisplayedInOtherTabs() throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Analytics_liDocumentThreadMap().Displayed();
			}
		}), Input.wait30);
		getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);
		driver.waitForPageToBeReady();

		try {
			if (getCodeSameIconThreadTab().isDisplayed())
				softAssertion.assertEquals(getCodeSameIconThreadTab().Displayed().booleanValue(), true, "3");
		} catch (Exception e) {
			base.stepInfo("Selected Document is not available under Thread tab");
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Analytics_FamilyTab().Displayed();
			}
		}), Input.wait30);
		getDocView_Analytics_FamilyTab().waitAndClick(10);
		driver.waitForPageToBeReady();

		try {
			if (getCodeSameIconFamilyTab().isDisplayed())
				softAssertion.assertEquals(getCodeSameIconFamilyTab().Displayed().booleanValue(), true, "4");
		} catch (Exception e) {
			base.stepInfo("Selected Document is not available under Family tab");
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Analytics_NearDupeTab().Displayed();
			}
		}), Input.wait30);
		getDocView_Analytics_NearDupeTab().waitAndClick(15);
		driver.waitForPageToBeReady();

		try {
			if (getCodeSameIconNearDupeTab().isDisplayed())
				softAssertion.assertEquals(getCodeSameIconNearDupeTab().Displayed().booleanValue(), true, "5");
		} catch (Exception e) {
			base.stepInfo("Selected Document is not available under Near Dupe tab");
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Analytics_liDocumentConceptualSimilarab().Displayed();
			}
		}), Input.wait60);
		getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);
		driver.waitForPageToBeReady();
		try {
			if (getCodeSameIconConceptTab().isDisplayed())
				softAssertion.assertEquals(getCodeSameIconConceptTab().Displayed().booleanValue(), true, "6");
		} catch (Exception e) {
			base.stepInfo("Selected Document is not available under Conceptual tabss");
		}
	}

	/**
	 * @Author Mohan Created on 27/09/2021
	 * @Description To verify completed checkmark Conceputually docs is displayed in
	 *              the DocView. Test Case id: RPMXCON-51382 and 51383
	 * 
	 */
	public void verifyWhetherCompletedCheckmarkIsDisplayedInOtherTabs() throws InterruptedException {

		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_Analytics_liDocumentThreadMap());
		getDocView_Analytics_liDocumentThreadMap().waitAndClick(25);
		driver.waitForPageToBeReady();

		try {
			if (getCodeCompleteIconThreadTab().isDisplayed())
				softAssertion.assertEquals(getCodeCompleteIconThreadTab().Displayed().booleanValue(), true, "7");
		} catch (Exception e) {
			base.stepInfo("Selected Document is not available under Thread tabs");
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Analytics_FamilyTab().Displayed();
			}
		}), Input.wait30);
		getDocView_Analytics_FamilyTab().waitAndClick(10);
		driver.waitForPageToBeReady();

		try {
			if (getCodeCompleteIconFamilyTab().isDisplayed())
				softAssertion.assertEquals(getCodeCompleteIconFamilyTab().Displayed().booleanValue(), true, "8");
		} catch (Exception e) {
			base.stepInfo("Selected Document is not available under Family tabss");
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Analytics_NearDupeTab().Displayed();
			}
		}), Input.wait30);
		getDocView_Analytics_NearDupeTab().waitAndClick(10);
		driver.waitForPageToBeReady();

		try {
			if (getCodeCompleteIconNearDupeTab().isDisplayed())
				softAssertion.assertEquals(getCodeCompleteIconNearDupeTab().Displayed().booleanValue(), true, "9");
		} catch (Exception e) {
			base.stepInfo("Selected Document is not available under Near Dupe tabss");
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Analytics_liDocumentConceptualSimilarab().Displayed();
			}
		}), Input.wait30);

		getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);
		driver.waitForPageToBeReady();
		getDocView_Analytics_liDocumentConceptualSimilarab().ScrollTo();
		try {
			if (getCodeCompleteIconConceptTab().isDisplayed())
				softAssertion.assertEquals(getCodeCompleteIconConceptTab().Displayed().booleanValue(), true, "10");
		} catch (Exception e) {
			base.stepInfo("Selected Document is not available under Conceptual tabss");
		}
		softAssertion.assertAll();

	}

	/**
	 * @Author Mohan Created on 27/09/2021
	 * @Description To popout coding form and complete docs in the DocView. Test
	 *              Case id: RPMXCON- 51383
	 */
	public void popOutCodingFormAndCompleteDocument() {

		try {

			driver.scrollPageToTop();

			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getGearIcon().Visible() && getGearIcon().Enabled();
				}
			}, Input.wait30);
			getGearIcon().waitAndClick(10);

			getDocument_CommentsTextBox().waitAndClick(5);

			String parentWindowID = driver.getWebDriver().getWindowHandle();

			driver.scrollPageToTop();
			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getDocView_CodingFormPopOut().Visible() && getDocView_CodingFormPopOut().Enabled();
				}
			}, Input.wait30);
			getDocView_CodingFormPopOut().waitAndClick(5);

			Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
			for (String eachId : allWindowsId) {
				if (!parentWindowID.equals(eachId)) {
					driver.switchTo().window(eachId);
				}
			}

			driver.waitForPageToBeReady();

			base.waitForElement(getResponsiveCheked());
			getResponsiveCheked().Click();
			base.waitForElement(getNonPrivilegeRadio());
			getNonPrivilegeRadio().Click();

			driver.WaitUntil((new Callable<Boolean>() {

				public Boolean call() {

					return getDocument_CommentsTextBox().Displayed() && getDocument_CommentsTextBox().Enabled();
				}
			}), Input.wait30);

			getDocument_CommentsTextBox().SendKeys("Editing and click complete button");

			driver.WaitUntil((new Callable<Boolean>() {

				public Boolean call() {
					return getCompleteDocBtn().Enabled() && getCompleteDocBtn().Visible();
				}
			}), Input.wait30);

			driver.scrollPageToTop();

			getCompleteDocBtn().Click();

			driver.switchTo().window(parentWindowID);

			base.VerifySuccessMessage("Document completed successfully");

			allWindowsId = driver.getWebDriver().getWindowHandles();
			for (String eachId : allWindowsId) {
				if (!parentWindowID.equals(eachId)) {
					driver.switchTo().window(eachId);
				}
			}

			driver.getWebDriver().close();

			driver.switchTo().window(parentWindowID);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Coding form is viewed with pop-out child window successfully");
		}
	}

	/**
	 * @Author Mohan Created on 27/09/2021
	 * @Description To popout MiniDocList in the DocView. Test Case id: RPMXCON-
	 *              51385
	 */
	public void popOutMiniDocList() {

		try {

			driver.scrollPageToTop();

			Thread.sleep(10000);

			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getGearIcon().Visible() && getGearIcon().Enabled();
				}
			}, Input.wait120);
			getGearIcon().Click();

			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getDocView_MiniDocListPopOut().Visible() && getDocView_MiniDocListPopOut().Enabled();
				}
			}, Input.wait30);
			getDocView_MiniDocListPopOut().Click();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Mini Doc list is viewed with pop-out child window successfully");
		}
	}

	/**
	 * @Author Mohan Created on 27/09/2021
	 * @Description To perform CodeSame in Conceptullay tab in the DocView. Test
	 *              Case id: RPMXCON- 51385
	 */
	public void performCodeSameForConceptualDocuments(String windowId) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Analytics_liDocumentConceptualSimilarab().Displayed();
			}
		}), Input.wait30);
		getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(15);

		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_Analytics_Conceptual_FirstDoc());
		getDocView_Analytics_Conceptual_FirstDoc().waitAndClick(15);

		getDocView_ChildWindow_ActionButton().waitAndClick(15);

		getDocView_Analytics_Concept_Similar_CodeSameAs().waitAndClick(15);

		driver.switchTo().window(windowId);

		base.VerifySuccessMessage("Code same performed successfully.");
		base.CloseSuccessMsgpopup();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!windowId.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return geDocView_Concept_CodeSameAsIcon().Displayed();
			}
		}), Input.wait30);
		softAssertion.assertEquals(geDocView_Concept_CodeSameAsIcon().Displayed().booleanValue(), true);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getConceptDocumentWhichHasCodeSameIcon().Displayed();
			}
		}), Input.wait30);

		codeSameDocumentid = getConceptDocumentWhichHasCodeSameIcon().getText();

	}

	/**
	 * @author Indium-Baskar date: 24/09/2021 Modified date: NA
	 * @Description : this method used for create coding form with tags and save and
	 *              next
	 */
	public void saveAndNextNewCodingFormTags() throws InterruptedException {
		driver.waitForPageToBeReady();
		base.stepInfo("Save and next Action performing in parent window");
		base.waitForElementCollection(getMiniDocListDocIdText());
		String exp = getVerifyPrincipalDocument().getText().trim();
		if (getTagNotSelectable().Enabled()) {
			base.passedStep("Tag checkBox is enabled when action make it has optional");
		} else {
			base.passedStep("Second Tag checkBox is disabled when action make it has not selectable");
		}
		base.waitForElement(getSaveAndNextButton());
		getSaveAndNextButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		String actExp = getVerifyPrincipalDocument().getText().trim();
		softAssertion.assertEquals(exp, actExp);
		if (exp.equals(actExp)) {
			base.passedStep("Cursor move to next document in minidoclist");
		} else {
			base.stepInfo("Cursor still in the same document");
		}
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parentWindowTO = reusableDocView.switchTochildWindow();
		driver.waitForPageToBeReady();
		base.waitForElement(getSaveAndNextButton());
		getSaveAndNextButton().waitAndClick(10);
		reusableDocView.childWindowToParentWindowSwitching(parentWindowTO);
		base.stepInfo("Same action performed in child window as well");

	}

	/**
	 * @author Indium-Baskar date: 24/09/2021 Modified date: NA
	 * @Description : this method used for create coding form with metadata with
	 *              error msg and save and next
	 */
	public void saveAndNextMetaDataErrorMsg(String metaData) throws InterruptedException {
		driver.waitForPageToBeReady();
		base.waitForElement(getReadOnlyTextBox(metaData));
		getReadOnlyTextBox(metaData).Clear();
		base.waitForElement(getSaveAndNextButton());
		getSaveAndNextButton().waitAndClick(10);
		String errorText = getCodingFormValidErrorMeta().getText().trim();
		String actual = "Coding Form validation failed";
		base.stepInfo("Save and next action performing in parent window");
		if (errorText.equals(actual)) {
			base.passedStep("Error message Dispalyed");
		} else {
			base.stepInfo("Error message not displayed");
		}
		driver.waitForPageToBeReady();
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parentWindowTO = reusableDocView.switchTochildWindow();
		driver.waitForPageToBeReady();
		base.stepInfo("Save and next action performing in child window");
		base.waitForElement(getSaveAndNextButton());
		getSaveAndNextButton().waitAndClick(10);
		reusableDocView.childWindowToParentWindowSwitching(parentWindowTO);
		if (errorText.equals(actual)) {
			base.passedStep("Error message Dispalyed");
		} else {
			base.stepInfo("Error message not displayed");
		}
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium-Baskar date: 24/09/2021 Modified date: NA
	 * @Description : this method used for create coding form with comment but
	 *              clickable and save and next
	 */

	public void saveAndNextCommentNotClickable() throws InterruptedException {
		driver.waitForPageToBeReady();
		base.stepInfo("Action performing in parent window");
		base.waitForElement(getCodingFormCommentDisabled());
		softAssertion.assertEquals(getCodingFormCommentDisabled().Enabled(), false);
		if (getCodingFormCommentDisabled().Enabled() && getCodingFormCommentDisabled().Displayed()) {
			base.stepInfo("Comment text box is clickable in docview coding form page");
		} else {
			base.passedStep("Comment text box is not clickable in docview coding form page parent window");
		}
		driver.waitForPageToBeReady();
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parentWindowTO = reusableDocView.switchTochildWindow();
		driver.waitForPageToBeReady();
		base.stepInfo("Action performing in child window");
		if (getCodingFormCommentDisabled().Enabled() && getCodingFormCommentDisabled().Displayed()) {
			base.stepInfo("Comment text box is clickable in docview coding form page");
		} else {
			base.passedStep("Comment text box is not clickable in docview coding form page child window");

		}
		reusableDocView.childWindowToParentWindowSwitching(parentWindowTO);
		reusableDocView.refreshAndAlert();
		driver.waitForPageToBeReady();
	}

	/**
	 * @Author Indium-Baskar
	 */
//	Reusable method for coding form validation error message
	public void errorMessage() {
		String errorText = getCodingFormValidErrorMeta().getText().trim();
		String actuals = "Coding Form validation failed";
		if (errorText.equals(actuals)) {
			base.passedStep("Error message Dispalyed");
		} else {
			base.stepInfo("Error message not displayed");
		}
	}

	/**
	 * @author Indium-Baskar date: 27/09/2021 Modified date: NA
	 * @Description : this method used for create coding form with comment but
	 *              clickable and save and next
	 */

	public void saveAndNextCommentTextBoxValidation() throws InterruptedException {
		driver.waitForPageToBeReady();
		base.stepInfo("Action performing in parent window");
		base.waitForElement(getCodingFormCommentVal());
		softAssertion.assertEquals(getCodingFormCommentVal().Enabled(), true);
		if (getCodingFormCommentVal().Enabled() && getCodingFormCommentVal().Displayed()) {
			base.passedStep("Comment text box is under validation text box filed value has to entered");
		} else {
			base.stepInfo("Comment text box is not under validation");
		}
		base.waitForElement(getSaveAndNextButton());
		getSaveAndNextButton().waitAndClick(10);
		String valExp = getCommentValidationDisplayed().getText().trim();
		String actual = "Error for testing";
		if (valExp.equalsIgnoreCase(actual)) {
			base.passedStep("Comment text box is under validation text box filed value has to entered");
			System.out.println("pass");
		}
		errorMessage();
		driver.waitForPageToBeReady();
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parentWindowTO = reusableDocView.switchTochildWindow();
		driver.waitForPageToBeReady();
		base.stepInfo("Action performing in child window");
		if (getCodingFormCommentVal().Enabled() && getCodingFormCommentVal().Displayed()) {
			base.passedStep("Comment text box is under validation text box filed value has to entered");
		} else {
			base.stepInfo("Comment text box is not under validation");

		}
		base.waitForElement(getSaveAndNextButton());
		getSaveAndNextButton().waitAndClick(10);
		reusableDocView.childWindowToParentWindowSwitching(parentWindowTO);
		errorMessage();
		driver.waitForPageToBeReady();

	}

	public String rgbTohexaConvertor(String bgColor) {
		String s1 = bgColor.substring(4);
		bgColor = s1.replace(')', ' ');
		bgColor = s1.replace('(', ' ');
		StringTokenizer st = new StringTokenizer(bgColor);
		int r = Integer.parseInt(st.nextToken(",").trim());
		int g = Integer.parseInt(st.nextToken(",").trim());
		int b = Integer.parseInt(st.nextToken(",").trim());
		Color c = new Color(r, g, b);
		String hex = "#" + Integer.toHexString(c.getRGB()).substring(2).toUpperCase();
		return hex;
	}

	/**
	 * @author Indium-Baskar date: 27/09/2021 Modified date: NA
	 * @Description : this method used for create coding form with comment but
	 *              clickable and save and next
	 */

	public void apperanceInstructionTextLeftParent() throws InterruptedException {
		driver.waitForPageToBeReady();
		base.waitForElement(getInstructionTxt());
		String left = getInstructionTxt().getWebElement().getCssValue("float");
		boolean flag = getInstructionTxt().Displayed();
		if (flag) {
			softAssertion.assertTrue(flag);
			base.stepInfo("Apperance of the instruction text on left above the Radio group");
		} else {
			base.stepInfo("Instruction text not displayed");
		}
		String fontsize = getBorderVerify().getWebElement().getCssValue("font-size");
		String bgColor = getBorderVerify().getWebElement().getCssValue("color");
		bgColor = rgbTohexaConvertor(bgColor);
		System.out.println(bgColor);
		if (bgColor.equals("#FFFFFF") && fontsize.equals("13px")) {
			base.stepInfo("Document displayed in white highlighting and font size are same");
			base.stepInfo("Text under the border box with extension");
		} else {
			base.failedStep("Not highlighted");
		}
	}

	/**
	 * @author Indium-Baskar date: 27/09/2021 Modified date: NA
	 * @Description : this method used for create coding form with comment but
	 *              clickable and save and next
	 */

	public void apperanceInstructionTextLeftChild() throws InterruptedException {
		driver.waitForPageToBeReady();
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parentWindow = reusableDocView.switchTochildWindow();
		base.waitForElement(getInstructionTxt());
		boolean flag = getInstructionTxt().Displayed();
		if (flag) {
			softAssertion.assertTrue(flag);
			base.stepInfo("Apperance of the instruction text on left above the Radio group");
		} else {
			base.stepInfo("Instruction text not displayed");
		}
		String left = getBorderVerify().getWebElement().getCssValue("float");
		String bgColor = getBorderVerify().getWebElement().getCssValue("color");
		bgColor = rgbTohexaConvertor(bgColor);
		System.out.println(bgColor);
		if (bgColor.equals("#FFFFFF")) {
			base.stepInfo("Document displayed in white highlighting");
			base.stepInfo("Text under the border box with extension");
		} else {
			base.failedStep("Not highlighted");
		}
		reusableDocView.childWindowToParentWindowSwitching(parentWindow);
		reusableDocView.refreshAndAlert();
	}

	/**
	 * @author Indium-Baskar date: 28/09/2021 Modified date: NA
	 * @Description : this method used to validate error message with static text
	 */

	public void staticTextErrorMessage() throws InterruptedException {
		driver.waitForPageToBeReady();
		base.stepInfo("Action performing in parent window");
		base.waitForElement(getSaveAndNextButton());
		getSaveAndNextButton().waitAndClick(10);
		base.VerifyWarningMessage("Blank coding form or coding form with static text could not processed.");
		String staticError = getStaticWarningError().getText().trim();
		String actualError = "Blank coding form or coding form with static text could not processed.";
		if (staticError.equals(actualError)) {
			base.passedStep("Warning message displayed when create coding form with static text");
		} else {
			base.stepInfo("Warning message not displayed for static text");
		}
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parentWindow = reusableDocView.switchTochildWindow();
		base.stepInfo("Action performing in child window");
		base.waitForElement(getSaveAndNextButton());
		getSaveAndNextButton().waitAndClick(10);
		reusableDocView.childWindowToParentWindowSwitching(parentWindow);
		base.VerifyWarningMessage("Blank coding form or coding form with static text could not processed.");
		String staticErrorC = getStaticWarningError().getText().trim();
		String actualErrorC = "Blank coding form or coding form with static text could not processed.";
		if (staticErrorC.equals(actualErrorC)) {
			base.passedStep("Warning message displayed when create coding form with static text");
		} else {
			base.stepInfo("Warning message not displayed for static text");
		}
		try {
			reusableDocView.refreshAndAlert();
		} catch (org.openqa.selenium.NoAlertPresentException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Indium-Baskar date: 14/09/2021 Modified date: NA
	 * @Description : verifying both parent and child window by using coding stamp
	 */

	public void codingStampVerificationBothParentAndChildWindow(String fieldValue, String colourIcon, String icon) {
		driver.waitForPageToBeReady();
		reusableDocView.editingCodingFormWithSaveButton();
		reusableDocView.codingFormSavingWithCodingStamp(fieldValue, colourIcon);
		for (int i = 2; i <= 2; i++) {
			getClickDocviewID(i).waitAndClick(5);
		}
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parentWindow = reusableDocView.switchTochildWindow();
		base.waitForElement(getCodingStampLastIcon(icon));
		getCodingStampLastIcon(icon).waitAndClick(10);
		base.stepInfo("click coding stamp in child window");
		reusableDocView.childWindowToParentWindowSwitching(parentWindow);
		driver.Navigate().refresh();
		driver.getWebDriver().switchTo().alert().accept();
		driver.waitForPageToBeReady();
		reusableDocView.VerifyTheDocument();
		base.passedStep("Verified Parent window get updated as well");
		for (int i = 3; i <= 3; i++) {
			getClickDocviewID(i).waitAndClick(5);
		}
		base.waitForElement(getCodingStampLastIcon(icon));
		getCodingStampLastIcon(icon).waitAndClick(10);
		base.stepInfo("click coding stamp in parent window");
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parentWindows = reusableDocView.switchTochildWindow();
		driver.waitForPageToBeReady();
		reusableDocView.VerifyTheDocument();
		base.passedStep("Verified child  window get updated as well");
		reusableDocView.childWindowToParentWindowSwitching(parentWindows);
		driver.Navigate().refresh();
		driver.getWebDriver().switchTo().alert().accept();
		driver.waitForPageToBeReady();
		reusableDocView.deleteStampColour(icon);
	}

	/**
	 * @author Indium-Baskar date: 24/09/2021 Modified date: NA
	 * @Description : impersoate and checking the coding stamp colour is clickable
	 *              for same user
	 */
	public void selectingStampColour(String fieldValue, String icon) {
		driver.waitForPageToBeReady();
		reusableDocView.editingCodingFormWithSaveButton();
		reusableDocView.codingFormSavingWithCodingStamp(fieldValue, icon);
		base.stepInfo("Coding stamp colour selected in drop down");
	}

	/**
	 * @Author Mohan Created on 28/09/2021
	 * @Description To verify Analytics Thread Map Tab with no email docs
	 * 
	 */
	public void verifyThreadMapWithNoEmailDocs() {

		try {

			driver.waitForPageToBeReady();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
//			getDocView_Analytics_liDocumentThreadMap().ScrollTo();

			base.waitForElement(getDocView_Analytics_liDocumentThreadMap());
			getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);

			base.waitForElement(getDocView_Analytics_NoQuery());
			softAssertion.assertTrue(getDocView_Analytics_NoQuery().isDisplayed());
			base.passedStep("Message is displayed as 'Your query returned no data' successfully");
			softAssertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No Doc is viewed from Analytics panel Thread Map tab successfully");
		}

	}

	/**
	 * @Author Mohan Created on 28/09/2021
	 * @Description To verify Analytics Thread Map Tab with no email docs
	 * 
	 */
	public void selectTextBoxInDocView(String docId, String sourceId) {

		try {
			driver.waitForPageToBeReady();

			base.waitForElement(getDocView_NumTextBox());
			getDocView_NumTextBox().waitAndClick(10);
			getDocView_NumTextBox().SendKeys(docId);
			getDocView_NumTextBox().Enter();

			base.waitForElement(getDocView_DocId(docId));
			getDocView_DocId(docId).ScrollTo();
			getDocView_DocId(docId).waitAndClick(10);

			getDocView_MiniDoclist_SourceDocIdText(sourceId).ScrollTo();
			String sourceDocId = getDocView_MiniDoclist_SourceDocIdText(sourceId).getText();
			softAssertion.assertEquals(sourceId, sourceDocId);
			softAssertion.assertTrue(getDocView_DocId(docId).Displayed());
			base.passedStep("Doc is viewed in the MiniDoclist successfully");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No Doc is viewed from MiniDoclist");

		}
	}

	/**
	 * @Author Mohan Created on 30/09/2021
	 * @Description To perform CodeSame near dupe docs in the DocView Test Case id:
	 *              RPMXCON-51382 and 51379
	 * 
	 */
	public void performCodeSameForNearDupeDocuments() throws InterruptedException {

		base.waitForElement(getDocView_Analytics_NearDupeTab());
		getDocView_Analytics_NearDupeTab().waitAndClick(10);

		for (int i = 1; i <= 1; i++) {
			base.waitForElement(getDocView_Analytics_NearDupe_Doc(i));
			getDocView_Analytics_NearDupe_Doc(i).waitAndClick(5);
		}

		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(15);

		base.waitForElement(getCodeSameAsNearDupe());
		getCodeSameAsNearDupe().waitAndClick(15);

		base.VerifySuccessMessage("Code same performed successfully.");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return geDocView_NearDupe_CodeSameAsIcon().Displayed();
			}
		}), Input.wait30);
		softAssertion.assertEquals(geDocView_NearDupe_CodeSameAsIcon().isDisplayed().booleanValue(), true);

		base.waitForElement(getNearDocumentWhichHasCodeSameIcon());
		codeSameDocumentid = getNearDocumentWhichHasCodeSameIcon().getText();
		softAssertion.assertAll();
	}

	/**
	 * @Author Mohan Created on 30/09/2021
	 * @Description To perform CodeSame near dupe docs in the DocView Test Case id:
	 *              RPMXCON-51379
	 * 
	 */

	public void performCodeSameForNearDupeDocumentsForReviewer() throws InterruptedException {

		base.waitForElement(getDocView_Analytics_NearDupeTab());
		getDocView_Analytics_NearDupeTab().waitAndClick(10);

		for (int i = 2; i <= 2; i++) {
			base.waitForElement(getDocView_Analytics_NearDupe_Doc(i));
			getDocView_Analytics_NearDupe_Doc(i).waitAndClick(5);
		}

		getDocView_ChildWindow_ActionButton().waitAndClick(15);

		getCodeSameAsNearDupe().waitAndClick(15);

		base.VerifySuccessMessage("Code same performed successfully.");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return geDocView_NearDupe_CodeSameAsIcon().Displayed();
			}
		}), Input.wait30);
		softAssertion.assertEquals(geDocView_NearDupe_CodeSameAsIcon().isDisplayed().booleanValue(), true);

		base.waitForElement(getNearDocumentWhichHasCodeSameIcon());
		codeSameDocumentid = getNearDocumentWhichHasCodeSameIcon().getText();
		softAssertion.assertAll();

	}

	/**
	 * @Author Mohan Created on 30/09/2021
	 * @Description To perform CodeSame in Near Dupe tab in the DocView. Test Case
	 *              id: RPMXCON- 51381
	 */
	public void performCodeSameForNearDupeDocuments(String windowId) throws InterruptedException {

		base.waitForElement(getDocView_Analytics_NearDupeTab());
		getDocView_Analytics_NearDupeTab().waitAndClick(10);

		for (int i = 1; i <= 1; i++) {
			base.waitForElement(getDocView_Analytics_NearDupe_Doc(i));
			getDocView_Analytics_NearDupe_Doc(i).waitAndClick(5);
		}

		getDocView_ChildWindow_ActionButton().waitAndClick(15);

		getCodeSameAsNearDupe().waitAndClick(15);

		driver.switchTo().window(windowId);

		base.VerifySuccessMessage("Code same performed successfully.");

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!windowId.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return geDocView_NearDupe_CodeSameAsIcon().Displayed();
			}
		}), Input.wait30);
		softAssertion.assertEquals(geDocView_NearDupe_CodeSameAsIcon().isDisplayed().booleanValue(), true);

		base.waitForElement(getNearDocumentWhichHasCodeSameIcon());
		codeSameDocumentid = getNearDocumentWhichHasCodeSameIcon().getText();
		softAssertion.assertAll();
	}

	/**
	 * @Author Mohan Created on 30/09/2021
	 * @Description To perform CodeSame in Near Dupe tab in the DocView for
	 *              Reviewer. Test Case id: RPMXCON- 51381
	 */
	public void performCodeSameForNearDupeDocumentsForReviewer(String windowId) throws InterruptedException {

		base.waitForElement(getDocView_Analytics_NearDupeTab());
		getDocView_Analytics_NearDupeTab().waitAndClick(10);

		getDocView_Analytics_NearDupeTab().ScrollTo();

		for (int i = 2; i <= 2; i++) {
			base.waitForElement(getDocView_Analytics_NearDupe_Doc(i));
			getDocView_Analytics_NearDupe_Doc(i).waitAndClick(5);
		}

		getDocView_ChildWindow_ActionButton().waitAndClick(15);

		getCodeSameAsNearDupe().waitAndClick(15);

		driver.switchTo().window(windowId);

		base.VerifySuccessMessage("Code same performed successfully.");

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!windowId.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return geDocView_NearDupe_CodeSameAsIcon().Displayed();
			}
		}), Input.wait30);
		softAssertion.assertEquals(geDocView_NearDupe_CodeSameAsIcon().Displayed().booleanValue(), true);

		base.waitForElement(getNearDocumentWhichHasCodeSameIcon());
		codeSameDocumentid = getNearDocumentWhichHasCodeSameIcon().getText();

	}

	/**
	 * @author Mohan 01/10/21 NA Modified date: NA Modified by:NA
	 * @description To complete Docs and verify checkmark
	 */
	public void completeDocsAndVerifyCheckMark() {

		try {
			base.waitForElement(getCompleteDocBtn());
			getCompleteDocBtn().waitAndClick(10);

			base.waitForElement(getverifyCodeSameAsLast());
			if (getverifyCodeSameAsLast().Displayed()) {
				softAssertion.assertTrue(getverifyCodeSameAsLast().getWebElement().isDisplayed());
				base.passedStep("CheckMark is displayed successfully");
			} else {
				base.failedStep("CheckMark is not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Check mark is not verified");
		}
	}

	/**
	 * @author Mohan 01/10/21 NA Modified date: NA Modified by:NA
	 * @description To complete Docs and verify Code Same As last
	 */
	public void completeDocsWithCodeSameAsLastAndVerifyCheckMark() {

		try {

			base.waitForElement(getCodeSameAsLast());
			getCodeSameAsLast().waitAndClick(10);

			base.waitForElement(getverifyCodeSameAsLast());
			if (getverifyCodeSameAsLast().Displayed()) {
				softAssertion.assertTrue(getverifyCodeSameAsLast().getWebElement().isDisplayed());
				base.passedStep("CheckMark is displayed successfully");
			} else {
				base.failedStep("CheckMark is not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Check mark is not verified");
		}
	}

	/**
	 * @author Mohan 01/10/21 NA Modified date: NA Modified by:NA
	 * @description To Select DocId From mini doclist
	 */
	public void selectDocIdInMiniDocListForReviewer() {

		driver.waitForPageToBeReady();
		for (int i = 3; i <= 3; i++) {
			base.waitForElement(getDocView_MiniDoc_Selectdoc(i));
			getDocView_MiniDoc_Selectdoc(i).ScrollTo();
			getDocView_MiniDoc_Selectdoc(i).waitAndClick(10);

			softAssertion.assertTrue(getDocView_MiniDoc_Selectdoc(i).Displayed());
			base.passedStep("Doc is selected from MiniDoclist successfully");
		}
	}

	/**
	 * @Author Mohan Created on 15/09/2021
	 * @Description To click on gear icon in Docview Page
	 * 
	 */
	public void selectStampGearIcon() {

		try {
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();

			base.waitForElement(getStampBlueColour());
			getStampBlueColour().waitAndClick(10);

			base.waitForElement(getverifyCodeSameAsLast());
			if (getverifyCodeSameAsLast().Displayed()) {
				softAssertion.assertTrue(getverifyCodeSameAsLast().getWebElement().isDisplayed());
				base.passedStep("CheckMark is displayed successfully");
			} else {
				base.failedStep("CheckMark is not displayed");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Gear Icon is not selected");
		}

	}

	/**
	 * @author Indium-Baskar date: 04/10/2021 Modified date: NA
	 * @Description : click Analytical panel document not part of minidoclist
	 */

	public void analyticalDocsPartOfMiniDocList() throws InterruptedException {
		driver.waitForPageToBeReady();
		reusableDocView.editingCodingFormWithSaveButton();
		reusableDocView.analyticalDocsPartOfMiniDocList(1);
		reusableDocView.codeSameAsInAnalyticalPanel();
		driver.scrollPageToTop();
		base.passedStep("Selected document is part of minidoclist document from analytical document");
		base.waitForElement(getCodeSameAsLast());
		getCodeSameAsLast().waitAndClick(10);
		base.VerifySuccessMessage("Coded as per the coding form for the previous document");
		base.stepInfo("Document is saved with last applied coding of the document");

	}

	/**
	 * @author Indium-Baskar date: 15/09/2021 Modified date: NA
	 * @Description : this method used for code same as in analytical panel child
	 *              window
	 */

	public void codeSameAsAnalyticalChildWindow(String fieldValue, String icon, String lastIcon, String lastIcons)
			throws InterruptedException {
		driver.waitForPageToBeReady();
		List<String> uniqueDocuments = new ArrayList<>();
		Set<String> duplicates = new HashSet<String>();
		List<String> miniDocList = reusableDocView.miniDocList();
		for (String addMini : miniDocList) {
			duplicates.add(addMini);

		}
		reusableDocView.editingCodingFormWithSaveButton();
		reusableDocView.codingFormSavingWithCodingStamp(fieldValue, icon);
		base.waitForElement(getCodingStampLastIcon(lastIcon));
		getCodingStampLastIcon(lastIcon).waitAndClick(10);
		base.waitForElement(getCodingFormSaveButton());
		getCodingFormSaveButton().waitAndClick(5);
		base.stepInfo("Document saved successfully");
		reusableDocView.clickGearIconOpenAnalyticalPanel();
		String parentWindow = reusableDocView.switchTochildWindow();
		// no docs 1
		List<String> analytics = reusableDocView.analyticalDocs();
		for (String analytical : analytics) {
			if (!duplicates.add(analytical)) {
				uniqueDocuments.add(analytical);
			}
		}
		String DocIdName = uniqueDocuments.get(1);
		getAnalyCheckBox(DocIdName).WaitUntilPresent().ScrollTo();
		base.waitForElement(getAnalyCheckBox(DocIdName));
		getAnalyCheckBox(DocIdName).waitAndClick(10);
		reusableDocView.codeSameAsInAnalyticalPanel();
		reusableDocView.childWindowToParentWindowSwitching(parentWindow);
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		reusableDocView.clickCodeSameAsLast();
		driver.waitForPageToBeReady();
		getMiniDocListText(DocIdName).WaitUntilPresent().ScrollTo();
		getMiniDocListText(DocIdName).waitAndClick(10);
		reusableDocView.VerifyTheDocument();
		base.stepInfo("Document verified for code same as last button as per the previous docs");
		base.waitForElement(selectCodingFormCheckBoxes("Not_Responsive"));
		selectCodingFormCheckBoxes("Not_Responsive").waitAndClick(10);
		base.waitForElement(getCodingFormSaveButton());
		getCodingFormSaveButton().waitAndClick(10);
		softAssertion.assertEquals(getCodingFormSaveButton().Displayed().booleanValue(), true);
		base.stepInfo("Excepted Message:Document completed successfully");
		reusableDocView.clickCodeSameAsLast();
		reusableDocView.deleteStampColour(lastIcons);
		driver.waitForPageToBeReady();

	}

	/**
	 * @author Indium-Baskar date: 15/09/2021 Modified date: NA
	 * @Description : this method used for code same as last in analytical panel
	 */

	public void codeSameAsLastAnalytical(String fieldValue, String icon, String lastIcon, String lastIcons)
			throws InterruptedException {
		driver.waitForPageToBeReady();
		List<String> uniqueDocuments = new ArrayList<>();
		Set<String> duplicates = new HashSet<String>();
		List<String> miniDocList = reusableDocView.miniDocList();
		for (String addMini : miniDocList) {
			duplicates.add(addMini);
		}
		reusableDocView.editingCodingFormWithSaveButton();
		reusableDocView.codingFormSavingWithCodingStamp(fieldValue, icon);
		base.waitForElement(getCodingStampLastIcon(lastIcon));
		getCodingStampLastIcon(lastIcon).waitAndClick(10);
		base.waitForElement(getCodingFormSaveButton());
		getCodingFormSaveButton().waitAndClick(5);
		base.stepInfo("Document saved successfully");
		reusableDocView.clickGearIconOpenAnalyticalPanel();
		String parentWindow = reusableDocView.switchTochildWindow();
		// no docs 1
		List<String> analytics = reusableDocView.analyticalDocs();
		for (String analytical : analytics) {
			if (!duplicates.add(analytical)) {
				uniqueDocuments.add(analytical);
			}
		}
		String DocIdName = uniqueDocuments.get(1);
		getAnalyCheckBox(DocIdName).WaitUntilPresent().ScrollTo();
		base.waitForElement(getAnalyCheckBox(DocIdName));
		getAnalyCheckBox(DocIdName).waitAndClick(10);
		reusableDocView.codeSameAsInAnalyticalPanel();
		reusableDocView.childWindowToParentWindowSwitching(parentWindow);
		driver.scrollPageToTop();
		base.waitForElement(getDocView_HdrCoddingForm());
		getDocView_HdrCoddingForm().waitAndClick(10);
		String parentCodigForm = reusableDocView.switchTochildWindow();
		reusableDocView.clickCodeSameAsLast();
		reusableDocView.childWindowToParentWindowSwitching(parentCodigForm);
		reusableDocView.refreshAndAlert();
		driver.waitForPageToBeReady();
		getMiniDocListText(DocIdName).WaitUntilPresent().ScrollTo();
		getMiniDocListText(DocIdName).waitAndClick(10);
		reusableDocView.VerifyTheDocument();
		base.stepInfo("Document verified for code same as last button as per the previous docs");
		base.waitForElement(selectCodingFormCheckBoxes("Not_Responsive"));
		selectCodingFormCheckBoxes("Not_Responsive").waitAndClick(10);
		base.waitForElement(getCodingFormSaveButton());
		getCodingFormSaveButton().waitAndClick(10);
		softAssertion.assertEquals(getCodingFormSaveButton().Displayed().booleanValue(), true);
		base.stepInfo("Excepted Message:Document completed successfully");
		reusableDocView.clickCodeSameAsLast();
		reusableDocView.deleteStampColour(lastIcons);
		driver.waitForPageToBeReady();

	}

	/**
	 * @author Indium-Baskar date: 15/09/2021 Modified date: NA
	 * @Description : After performing code sameas click save and next
	 */
	public void codeSameAsSaveAndNext() throws InterruptedException {
		driver.waitForPageToBeReady();
		List<String> uniqueDocuments = new ArrayList<>();
		Set<String> duplicates = new HashSet<String>();
		List<String> miniDocList = reusableDocView.miniDocList();
		for (String addMini : miniDocList) {
			duplicates.add(addMini);
		}
		List<String> analytics = reusableDocView.analyticalDocs();
		for (String analytical : analytics) {
			if (!duplicates.add(analytical)) {
				uniqueDocuments.add(analytical);
				System.out.println(uniqueDocuments);
			}
		}
		String DocIdName = uniqueDocuments.get(1);
		System.out.println(DocIdName);
		driver.scrollPageToTop();
		getMiniDocListTextChck_Box(DocIdName).WaitUntilPresent().ScrollTo();
		base.waitForElement(getMiniDocListTextChck_Box(DocIdName));
		getMiniDocListTextChck_Box(DocIdName).waitAndClick(10);
		String principalDocs = reusableDocView.clickCodeSameAs();
		base.stepInfo("Code same as performing for document number:" + principalDocs);
		base.waitForElement(getVerifyPrincipalDocument());
		String principalDocsSave = getVerifyPrincipalDocument().getText().trim();
		base.stepInfo("Editing the coding form with save and next button for document No:" + principalDocsSave);
		base.passedStep("Chain link get disappared after performing save and next");
		base.waitForElement(getVerifyPrincipalDocument());
		String principalDocsPresent = getVerifyPrincipalDocument().getText().trim();
		softAssertion.assertNotEquals(principalDocsSave, principalDocsPresent);
		base.passedStep("Curosr navigated to next document in minidoclist");
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium-Baskar date: 15/09/2021 Modified date: NA
	 * @Description : click Analytical panel document not part of minidoclist
	 */

	public void analyticalDocs() throws InterruptedException {
		driver.waitForPageToBeReady();
		base.waitForElement(getViewDocAllList());
		getViewDocAllList().waitAndClick(5);
		driver.waitForPageToBeReady();
		List<String> listOFData = new ArrayList<>();
		ElementCollection element = getDocList();
		System.out.println(element.size());
		listOFData = reusableDocView.availableListofElements(element);
//		String names = listOFData.get(1);
//		getDocListCheckBox(names).waitAndClick(10);
		System.out.println(listOFData.size());
		for (int i = 0; i <= 2; i++) {
			String name = listOFData.get(i);
			getDocListCheckBox(name).waitAndClick(10);
		}
		base.waitForElement(getDocList_Action_Drp_Dwn());
		getDocList_Action_Drp_Dwn().waitAndClick(5);
		base.waitForElement(getDocListViewInDocView());
		getDocListViewInDocView().waitAndClick(5);
		driver.waitForPageToBeReady();
		reusableDocView.editingCodingFormWithSaveButton();
		base.waitForElementCollection(getDocView_MiniListDocuments());
		List<String> uniqueDocuments = new ArrayList<>();
		Set<String> duplicates = new HashSet<String>();
		List<String> listOFData2 = new ArrayList<>();
		ElementCollection element2 = getMiniDocListDocIdText();
		listOFData2 = reusableDocView.availableListofElements(element2);
		for (String minidoclist : listOFData2) {
			duplicates.add(minidoclist);
		}
		System.out.println(listOFData2);
		driver.waitForPageToBeReady();
		Thread.sleep(Input.wait30);
		getDocView_Analytics_NearDupeTab().WaitUntilPresent().ScrollTo();
		getDocView_Analytics_NearDupeTab().waitAndClick(5);
		Thread.sleep(Input.wait30);
		List<String> listOFData3 = new ArrayList<>();
		ElementCollection element3 = getAnalyticalPanelDocIdText();
		listOFData3 = reusableDocView.availableListofElements(element3);
		System.out.println(listOFData3);
		for (String analytical : listOFData3) {
			if (duplicates.add(analytical)) {
				uniqueDocuments.add(analytical);
			}
		}
		System.out.println(uniqueDocuments);
		String name = uniqueDocuments.get(0);
		base.waitForElement(getAnalyCheckBox(name));
		getAnalyCheckBox(name).waitAndClick(10);
		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(10);
		base.waitForElement(getCodeSameAsNearDupe());
		getCodeSameAsNearDupe().waitAndClick(10);
		driver.scrollPageToTop();
		base.passedStep("Selected document is not part of minidoclist document");
		driver.waitForPageToBeReady();
		String expectedLast = getVerifyPrincipalDocument().getText().trim();
		base.waitForElement(getCodeSameAsLast());
		getCodeSameAsLast().waitAndClick(10);
		base.waitForElement(getMiniDocListText(expectedLast));
		getMiniDocListText(expectedLast).waitAndClick(10);
		reusableDocView.VerifyTheDocument();
		base.VerifySuccessMessage("Coded as per the coding form for the previous document");
		base.stepInfo("Document is saved with last applied coding of the document");

	}

	/**
	 * @Author Mohan Created on 06/10/2021
	 * @Description To verify Analytics Thread Map Tab with no docs
	 * 
	 */
	public void verifyThreadMapWithoutEmail() {

		try {

			driver.waitForPageToBeReady();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			getDocView_Analytics_liDocumentThreadMap().ScrollTo();

			base.waitForElement(getDocView_Analytics_liDocumentThreadMap());
			getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);

			base.waitForElement(getDocView_DocumentThreadMap());
			softAssertion.assertTrue(getDocView_DocumentThreadMap().Displayed());
			base.passedStep("Thread map does not have any mails from the same dataset");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Emails are displayed in the ThreadMap Tab");
		}

	}

	/**
	 * @Author Mohan Created on 06/10/2021
	 * @Description To verify error msg when selecting multi docs from Thread Map
	 *              Tab
	 * 
	 */
	public void verifyErrorMsgOnMultiSelectedDocsInThreadMap() {

		try {
			driver.waitForPageToBeReady();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			getDocView_Analytics_liDocumentThreadMap().ScrollTo();

			base.waitForElement(getDocView_Analytics_liDocumentThreadMap());
			getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);

			for (int i = 2; i <= 3; i++) {
				base.waitForElement(getDocView_Analytics_ThreadMap_DocCheckBox(i));
				getDocView_Analytics_ThreadMap_DocCheckBox(i).waitAndClick(10);
			}
			base.waitForElement(getDocView_ChildWindow_ActionButton());
			getDocView_ChildWindow_ActionButton().waitAndClick(10);

			base.waitForElement(getAnalyticalDropDown());
			getAnalyticalDropDown().waitAndClick(10);

			base.VerifyErrorMessage("You may only select one document at a time when viewing document");
			base.passedStep("Error message is displayed successfully");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error msg is not verified");
		}

	}

	/**
	 * @Author Mohan Created on 06/10/2021
	 * @Description To verify Analytics Thread Map Tab with docs
	 * 
	 */
	public void verifyThreadMapWithDocs(String docId1, String docId2, String docId3) {

		try {

			driver.waitForPageToBeReady();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			getDocView_Analytics_liDocumentThreadMap().ScrollTo();

			base.waitForElement(getDocView_Analytics_liDocumentThreadMap());
			getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);

			getDocView_Analytics_LoadMoreButton().ScrollTo();
			base.waitForElement(getDocView_Analytics_LoadMoreButton());
			getDocView_Analytics_LoadMoreButton().waitAndClick(10);

			driver.waitForPageToBeReady();
			getDocView_PrincipalDocIdE1(docId1).ScrollTo();
			base.waitForElement(getDocView_PrincipalDocIdE1(docId1));
			softAssertion.assertTrue(getDocView_PrincipalDocIdE1(docId1).Displayed());

			getDocView_PrincipalDocIdE1(docId2).ScrollTo();
			base.waitForElement(getDocView_PrincipalDocIdE1(docId2));
			softAssertion.assertTrue(getDocView_PrincipalDocIdE1(docId2).Displayed());

			getDocView_PrincipalDocIdE1(docId3).ScrollTo();
			base.waitForElement(getDocView_PrincipalDocIdE1(docId3));
			softAssertion.assertTrue(getDocView_PrincipalDocIdE1(docId1).Displayed());

			base.passedStep("E1,E2 and E3 docs are present in ThreadMap tab successfully");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("No Doc is viewed from Analytics panel Thread Map tab successfully");
		}

	}

	/**
	 * @author Indium-Baskar date: 06/10/2021 Modified date: NA
	 * @Description : this method used for verify tick mark in minidoclist child
	 *              window uncomplete button shpuld display in child window coding
	 *              form
	 */

	public void verifyCheckMarkFromMiniDocListChildWindow() {
		driver.waitForPageToBeReady();
		String parentWindow = driver.getWebDriver().getWindowHandle();
		System.out.println(parentWindow);
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String codingForm = reusableDocView.switchTochildWindow();
		reusableDocView.editingCodingFormWithCompleteButtonChild();
//		reusableDocView.childWindowToParentWindowSwitching(codingForm);
		driver.switchTo().window(parentWindow);
		base.waitForElement(getDocView_HdrMinDoc());
		getDocView_HdrMinDoc().waitAndClick(5);
		String miniDocList = reusableDocView.switchTochildWindow();
		base.stepInfo("Switching to minidoclist child window");
		driver.waitForPageToBeReady();
		getverifyCodeSameAsLast().ScrollTo();
		base.waitForElement(getverifyCodeSameAsLast());
		if (getverifyCodeSameAsLast().Displayed()) {
			base.stepInfo("Tick mark displayed in minidoclist child window");
		}
		for (int i = 1; i <= 1; i++) {
			getClickDocviewID(i).waitAndClick(5);
		}
		reusableDocView.childWindowToParentWindowSwitching(miniDocList);
		for (String CodingFormChild : driver.getWebDriver().getWindowHandles()) {
			driver.switchTo().window(CodingFormChild);
			System.out.println(CodingFormChild);
		}
		driver.waitForPageToBeReady();
		base.waitForElement(getUnCompleteButton());
		if (getUnCompleteButton().Displayed()) {
			softAssertion.assertEquals(getUnCompleteButton().Displayed().booleanValue(), true);
			base.passedStep("Uncomplete button displayed for completed documents ");
		} else {
			base.failedStep("Uncomplete button not displayed for completed documents ");
		}
		reusableDocView.childWindowToParentWindowSwitching(parentWindow);
		driver.waitForPageToBeReady();

	}

	/**
	 * @author Indium-Baskar date: 07/10/2021 Modified date: NA
	 * @Description : this method used for code same as action from minidoclist
	 *              child window and uncomplete button display in parent window
	 */

	public void verifyUncompletebuttonFromParentWindow() {
		driver.waitForPageToBeReady();
		String parentWindow = driver.getWebDriver().getWindowHandle();
		reusableDocView.clickGearIconOpenMiniDocList();
		String miniDocList = reusableDocView.switchTochildWindow();
		base.stepInfo("Minidoclist child window is opened");
		driver.waitForPageToBeReady();
		for (int j = 1; j <= 1; j++) {
			getDocView_MiniDoc_ChildWindow_Selectdoc(j).WaitUntilPresent().Click();
		}
		base.waitForElement(getDocView_Mini_ActionButton());
		getDocView_Mini_ActionButton().waitAndClick(5);
		base.waitForElement(getDocView__ChildWindow_Mini_CodeSameAs());
		getDocView__ChildWindow_Mini_CodeSameAs().waitAndClick(5);
		for (int i = 2; i <= 2; i++) {
			getClickDocviewID(i).waitAndClick(5);
		}
		geDocView_MiniList_CodeSameAsIcon().WaitUntilPresent().ScrollTo();
		softAssertion.assertEquals(geDocView_MiniList_CodeSameAsIcon().Displayed().booleanValue(), true);
		base.passedStep("Chain link displayed for document after performing code same as action");
		driver.switchTo().window(parentWindow);
		reusableDocView.editingCodingFormWithCompleteButtonChild();
		for (String miniDocListWindow : driver.getWebDriver().getWindowHandles()) {
			driver.switchTo().window(miniDocListWindow);
			System.out.println(miniDocListWindow);
		}
		base.stepInfo("Switching to minidoclist child window to verfity tick mark for completed document");
		driver.waitForPageToBeReady();
		getverifyCodeSameAsLast().ScrollTo();
		base.waitForElement(getverifyCodeSameAsLast());
		if (getverifyCodeSameAsLast().Displayed()) {
			base.stepInfo("Tick mark displayed in minidoclist child window for code same as action");
		}
		for (int i = 1; i <= 1; i++) {
			getClickDocviewID(i).waitAndClick(5);
		}
		reusableDocView.childWindowToParentWindowSwitching(miniDocList);
		driver.waitForPageToBeReady();
		base.waitForElement(getUnCompleteButton());
		if (getUnCompleteButton().Displayed()) {
			softAssertion.assertEquals(getUnCompleteButton().Displayed().booleanValue(), true);
			base.passedStep("Uncomplete button displayed for completed documents ");
		} else {
			base.failedStep("Uncomplete button not displayed for completed documents ");

		}
		driver.waitForPageToBeReady();

	}

	/**
	 * @author Indium-Baskar date: 07/10/2021 Modified date: NA
	 * @Description : this method used for code same as action in parent window
	 */

	public void miniDocListCodeSameAsUncomplteBtnChildWindow() {
		driver.waitForPageToBeReady();
		String parentWindow = driver.getWebDriver().getWindowHandle();
		for (int j = 1; j <= 1; j++) {
			getDocView_MiniDoc_ChildWindow_Selectdoc(j).WaitUntilPresent().Click();
		}
		base.waitForElement(getDocView_Mini_ActionButton());
		getDocView_Mini_ActionButton().waitAndClick(5);
		base.waitForElement(getDocView__ChildWindow_Mini_CodeSameAs());
		getDocView__ChildWindow_Mini_CodeSameAs().waitAndClick(5);
		for (int i = 2; i <= 2; i++) {
			getClickDocviewID(i).waitAndClick(5);
		}
		geDocView_MiniList_CodeSameAsIcon().WaitUntilPresent().ScrollTo();
		softAssertion.assertEquals(geDocView_MiniList_CodeSameAsIcon().Displayed().booleanValue(), true);
		base.passedStep("Chain link displayed for document after performing code same as action");
		base.passedStep("Performing code same as action with Complete Button");
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String codingForm = reusableDocView.switchTochildWindow();
		reusableDocView.editingCodingFormWithCompleteButtonChild();
		driver.switchTo().window(parentWindow);
		driver.waitForPageToBeReady();
		for (int i = 1; i <= 1; i++) {
			getClickDocviewID(i).waitAndClick(5);
		}
		getverifyCodeSameAsLast().ScrollTo();
		base.waitForElement(getverifyCodeSameAsLast());
		if (getverifyCodeSameAsLast().Displayed()) {
			base.stepInfo("Tick mark displayed in minidoclist child window for code same as action");
		}
		for (String miniDocListWindow : driver.getWebDriver().getWindowHandles()) {
			driver.switchTo().window(miniDocListWindow);
			driver.waitForPageToBeReady();
			System.out.println(miniDocListWindow);
		}
		if (getUnCompleteButton().Displayed()) {
			softAssertion.assertEquals(getUnCompleteButton().Displayed().booleanValue(), true);
			base.passedStep("Uncomplete button displayed for completed documents ");
		} else {
			base.failedStep("Uncomplete button not displayed for completed documents ");

		}
		reusableDocView.childWindowToParentWindowSwitching(codingForm);
		driver.waitForPageToBeReady();
	}

	/**
	 * 
	 * @author Steffy 10/06/21 NA Modified date: NA Modified by:NA
	 * @description to open NearDupe ComparisonWindow
	 */

	public void openNearDupeComparisonWindowForDocumentWhichHasMorePages(String documentId)
			throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Analytics_NearDupeTab().Displayed();
			}
		}), Input.wait30);
		getDocView_Analytics_NearDupeTab().waitAndClick(10);

		getDocView_NearDupeIconForSpecificDocument(documentId).waitAndClick(10);

		for (String winHandle : driver.getWebDriver().getWindowHandles()) {
			driver.switchTo().window(winHandle);
		}

		driver.waitForPageToBeReady();
		getDocView_NearDupe_DocID().WaitUntilPresent();
		String docidinchildwinodw = getDocView_NearDupe_DocID().getText().toString();
		System.out.println(docidinchildwinodw);

		driver.getWebDriver().navigate().refresh();
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Steffy 10/06/21 NA Modified date: NA Modified by:NA
	 * @description to open NearDupe ComparisonWindow
	 */

	public void verifyPaginationFromNearDupeWindow() throws InterruptedException {
		driver.getWebDriver().navigate().refresh();
		driver.waitForPageToBeReady();
		try {
			base.waitForElement(getOriginalDocPageNumber());
			String originalDocPageNumber = getOriginalDocPageNumber().GetAttribute("value");

			base.stepInfo("Verify pagination functionality is not works for original document which has 1 page");

			base.stepInfo("Verify pagination is not working for the documents which has 1 page");
			driver.waitForPageToBeReady();
			base.waitForElement(getOriginalDocPaginationIcon());
			for (int i = 0; i < 20; i++) {
				try {
					base.waitForElement(getOriginalDocPaginationIcon());
					base.waitTillElemetToBeClickable(getOriginalDocPaginationIcon());
					if (getOriginalDocPaginationIcon().Displayed()) {
						getOriginalDocPaginationIcon().waitAndClick(5);
					}
					break;
				} catch (Exception e) {
					System.err.println(e);
				}

			}
			String originalDocPageNumberAfterClick = getOriginalDocPageNumber().GetAttribute("value");
			softAssertion.assertEquals(originalDocPageNumber, originalDocPageNumberAfterClick,
					"Document page number is not changed even after clicking on pagination icon for the document which has 1 page");

			base.stepInfo("Verify pagination functionality works for near dupe document which has more than 1 page");

			base.waitForElement(getNearDupeDocPageNumber());
			String nearDupeDocPageNumber = getNearDupeDocPageNumber().GetAttribute("value");

			base.stepInfo("Verify pagination is working for the documents which has more than 1 page");
			for (int i = 0; i < 20; i++) {
				try {
					base.waitForElement(getNearDupeDocPaginationIcon());
					base.waitTillElemetToBeClickable(getNearDupeDocPaginationIcon());
					if (getNearDupeDocTotalPageCount().Displayed()) {
						getNearDupeDocPaginationIcon().waitAndClick(5);
					}
					break;
				} catch (Exception e) {
					driver.Navigate().refresh();
					driver.waitForPageToBeReady();
					System.err.println(e);
				}

			}

			String nearDupeDocPageNumberAfterClick = getNearDupeDocPageNumber().GetAttribute("value");
			softAssertion.assertNotEquals(nearDupeDocPageNumber, nearDupeDocPageNumberAfterClick,
					"Document page number is changed even after clicking on pagination icon for the document which has 1 page");

			base.waitForElement(getNearDupeDocTotalPageCount());
			String totalPageCount = getNearDupeDocTotalPageCount().getText();
			if (totalPageCount.contains(nearDupeDocPageNumberAfterClick)) {
				base.passedStep(
						"Expected Last Page Number is displayed in page number text box after clicking on pagination icon");
			} else {
				base.failedStep(
						"Expected Last Page Number is not displayed in page number text box after clicking on pagination icon");
			}
			softAssertion.assertAll();
		} catch (Exception e) {
			UtilityLog.info("Pagination functionality verifcation for near dupe comparsion window failed due to " + e);
			e.printStackTrace();
		}

	}

	/**
	 * @Author Mohan Created on 07/10/2021
	 * @Description To select docs from Analytics Thread Map Tab and verify code
	 *              same as icon
	 * 
	 */
	public void selectDocsFromThreadMapTabAndActionCodeSame() {

		try {
			driver.waitForPageToBeReady();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			// getDocView_Analytics_liDocumentThreadMap().ScrollTo();

			for (int i = 2; i <= 3; i++) {
				base.waitForElement(getDocView_Analytics_ThreadMap_DocCheckBox(i));
				getDocView_Analytics_ThreadMap_DocCheckBox(i).waitAndClick(10);
			}

			base.waitForElement(getDocView_ChildWindow_ActionButton());
			getDocView_ChildWindow_ActionButton().waitAndClick(10);

			base.waitForElement(getDocView_Analytics_Thread_CodeSameAs());
			getDocView_Analytics_Thread_CodeSameAs().waitAndClick(10);

			base.VerifySuccessMessage("Code same performed successfully.");

			// verify Code Same as Link
			base.waitForElement(geDocView_Threaded_CodeSameAsIcon());
			softAssertion.assertTrue(geDocView_Threaded_CodeSameAsIcon().isDisplayed());
			base.passedStep(
					"Code same icon is displayed for the selected documents and documents from thread map are unchecked successfull");
			softAssertion.assertAll();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Code Same As Icon is not displayed");
		}
	}

	/**
	 * @Author Mohan Created on 07/10/2021
	 * @Description Verify when user select 'Folder' action from thread map without
	 *              selecting document
	 * 
	 */
	public void verifyWhenNoDocsAreSelectedFromThreadMapAndFolderAction() {

		try {

			driver.waitForPageToBeReady();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			getDocView_Analytics_liDocumentThreadMap().ScrollTo();
			getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);

			base.waitForElement(getDocView_ChildWindow_ActionButton());
			getDocView_ChildWindow_ActionButton().waitAndClick(10);

			try {
				base.waitForElement(getDocView_Analytics_Thread_Folder());
				softAssertion.assertTrue(getDocView_Analytics_Thread_Folder().getWebElement().isEnabled());
				base.passedStep("Folder action is disable when document is not selected from thread map");
			} catch (Exception e) {
				e.printStackTrace();
				base.failedStep("Folder action is not disable when document is not selected from thread map");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Folder Drop Down is not Visible");
		}
	}

	/**
	 * @Author Mohan Created on 07/10/2021
	 * @Description Verify when no document to display on thread map all actions
	 *              should be disable
	 * 
	 */
	public void verifyNoDocsThereInThreadDisabed() {

		driver.waitForPageToBeReady();

		for (int i = 6; i <= 6; i++) {
			for (int j = 2; j <= 2; j++) {
				base.waitForElement(getDocView_MiniDoc_SelectdocAsText(i, j));
				getDocView_MiniDoc_SelectdocAsText(i, j).waitAndClick(10);
			}
		}

		driver.waitForPageToBeReady();
		JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
		driver.waitForPageToBeReady();
		Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
		je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
		getDocView_Analytics_liDocumentThreadMap().ScrollTo();
		getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);

		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(10);

		try {
			softAssertion.assertEquals(getDocView_Analytics_Thread_ViewDocument().Enabled().booleanValue(), true);
			base.passedStep("View Document action is disable when document is not selected from thread map");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("View Document action is not disable when document is not selected from thread map");
		}

		try {
			softAssertion.assertEquals(getDocView_Analytics_Thread_Folder().Enabled().booleanValue(), true);
			base.passedStep("Folder action is disable when document is not selected from thread map");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Folder action is not disable when document is not selected from thread map");
		}

		try {
			softAssertion.assertEquals(getDocView_Analytics_Thread_CodeSameAs().Enabled().booleanValue(), true);
			base.passedStep("Code Same action is disable when document is not selected from thread map");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Code Same action is not disable when document is not selected from thread map");
		}

		try {
			softAssertion.assertEquals(getDocView_Analytics_Thread_RemoveCodeSameAs().Enabled().booleanValue(), true);
			base.passedStep("Remove CodeSame As action is disable when document is not selected from thread map");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Remove CodeSame As action is not disable when document is not selected from thread map");
		}

		try {
			softAssertion.assertEquals(getDocView_Analytics_Thread_ViewDoclist().Enabled().booleanValue(), true);
			base.passedStep("View Dcolist action is disable when document is not selected from thread map");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("View Dcolist action is not disable when document is not selected from thread map");
		}

		base.passedStep("All actions are disable as per the test case and are verified successfully");
	}

	/**
	 * @Author Steffy Created on 07/10/2021
	 * @Description To perform CodeSame for threaded documents in the DocView Test
	 *              Case id: RPMXCON-51361 & 51371
	 * 
	 */
	public void performCodeSameForThreadedDocuments() throws InterruptedException {

		base.waitForElement(getDocView_Analytics_liDocumentThreadMap());
		getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);

		for (int i = 1; i <= 1; i++) {
			base.waitForElement(getDocView_Analytics_ThreadedDocument_Doc(i));
			getDocView_Analytics_ThreadedDocument_Doc(i).waitAndClick(5);
		}

		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(15);

		base.waitForElement(getDocView_Analytics_Thread_CodeSameAs());
		getDocView_Analytics_Thread_CodeSameAs().waitAndClick(15);

		base.VerifySuccessMessage("Code same performed successfully.");

		for (int i = 1; i <= 1; i++) {
			base.waitForElement(geDocView_ThreadMap_CodeSameAsIcon(i));
			softAssertion.assertEquals(geDocView_ThreadMap_CodeSameAsIcon(i).Displayed().booleanValue(), true);
			try {
				if (geDocView_ThreadMap_CodeSameAsIcon(i).Displayed()) {
					base.passedStep("CodeAsSame icon is displayed for the selected docs ");
				}
			} catch (Exception e) {
				base.failedStep("CodeAsSame icon is not displayed for the selected docs");
				UtilityLog.info("Verification failed due to " + e.getMessage());
			}

		}

		codeSameDocumentid = getThreadedDocumentWhichHasCodeSameIcon().getText();

		softAssertion.assertAll();
	}

	/**
	 * @Author Steffy Created on 07/10/2021
	 * @Description To verify Family Members tab columns Case id: RPMXCON-51288
	 * 
	 */
	public void verifyFamilyMembersTabColumns() throws InterruptedException {
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().ScrollTo();
		base.waitForElement(getDocView_Analytics_FamilyTab());
		getDocView_Analytics_FamilyTab().waitAndClick(10);
		base.waitForElement(getDocView_FamilyMember_DocIdColumn());
		try {
			base.stepInfo("Verify whether the DOC Id column is getting displayed in Family Members TAB Column");
			if (getDocView_FamilyMember_DocIdColumn().Displayed()) {
				softAssertion.assertEquals(getDocView_FamilyMember_DocIdColumn().Displayed().booleanValue(), true);
				base.passedStep("DOC ID column is displayed in Family Members TAB Column");
			}
			base.stepInfo("Verify whether the File Name column is getting displayed in Family Members TAB Column");
			if (getDocView_Family_Member_FileNameColumn().Displayed()) {
				softAssertion.assertEquals(getDocView_Family_Member_FileNameColumn().Displayed().booleanValue(), true);
				base.passedStep("File Name column is displayed in Family Members TAB Column");
			}
			base.stepInfo("Verify whether the Check Box is getting displayed in Family Members TAB Column");
			if (getDocView_Family_Member_CheckBoxColumn().Displayed()) {
				softAssertion.assertEquals(getDocView_Family_Member_CheckBoxColumn().Displayed().booleanValue(), true);
				base.passedStep("Check box column is displayed in Family Members TAB Column");
			}
			base.stepInfo(
					"Verify whether the Code Same/Code Complete icon column is getting displayed in Family Members TAB Column");
			if (getDocView_Family_Member_CodeSameIconColumns().Displayed()) {
				softAssertion.assertEquals(getDocView_Family_Member_CodeSameIconColumns().Displayed().booleanValue(),
						true);
				base.passedStep("Code Same Icon column is displayed in Family Members TAB Column");
			}
		} catch (Exception e) {
			base.failedStep("Family members column verification failed due to this exception " + e.getMessage());
		}

		softAssertion.assertAll();

	}

	/**
	 * @Author Steffy Created on 08/10/2021
	 * @Description To verify Near Dupe tab columns Case id: RPMXCON-51289
	 * 
	 */
	public void verifyNearDupeTabColumns() throws InterruptedException {
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().ScrollTo();
		base.waitForElement(getDocView_Analytics_NearDupeTab());
		getDocView_Analytics_NearDupeTab().waitAndClick(10);
		base.waitForElement(getDocView_NearDupe_DocIdColumn());
		try {
			base.stepInfo("Verify whether the DOC Id column is getting displayed in Near Dupe TAB Column");
			if (getDocView_NearDupe_DocIdColumn().Displayed()) {
				softAssertion.assertEquals(getDocView_NearDupe_DocIdColumn().Displayed().booleanValue(), true);
				base.passedStep("DOC ID column is displayed in Near Dupe TAB Column");
			}
			base.stepInfo("Verify whether the File Name column is getting displayed in Near Dupe TAB Column");
			if (getDocView_NearDupe_FileNameColumn().Displayed()) {
				softAssertion.assertEquals(getDocView_NearDupe_FileNameColumn().Displayed().booleanValue(), true);
				base.passedStep("File Name column is displayed in Near dupe TAB Column");
			}
			base.stepInfo("Verify whether the PERCENTAGE is getting displayed in Near dupe TAB Column");
			if (getDocView_NearDupe_PercentageColumn().Displayed()) {
				softAssertion.assertEquals(getDocView_NearDupe_PercentageColumn().Displayed().booleanValue(), true);
				base.passedStep("PERCENTAGE column is displayed in Near dupe TAB Column");
			}
			base.stepInfo("Verify whether the PERCENTAGE SYMBOL is not getting displayed in Near dupe TAB Column");
			if (getDocView_NearDupe_PercentageColumn().Displayed()) {
				softAssertion.assertEquals(getDocView_NearDupe_PercentageColumn().getText().contains("%"), false);
				base.passedStep("PERCENTAGE symbol is not displayed in Near dupe TAB Column");
			}
			base.stepInfo("Verify whether the Check Box is getting displayed in Near dupe TAB Column");
			if (getDocView_NearDupe_CheckBoxColumn().Displayed()) {
				softAssertion.assertEquals(getDocView_NearDupe_CheckBoxColumn().Displayed().booleanValue(), true);
				base.passedStep("Check box column is displayed in Near dupe TAB Column");
			}
			base.stepInfo(
					"Verify whether the Code Same/Code Complete icon column is getting displayed in Near dupe TAB Column");
			if (getDocView_NearDupe_CodeSameIconColumns().Displayed()) {
				softAssertion.assertEquals(getDocView_NearDupe_CodeSameIconColumns().Displayed().booleanValue(), true);
				base.passedStep("Code Same Icon column is displayed in Near dupe TAB Column");
			}
			base.stepInfo("Verify whether Near Dupe icon is getting displayed");
			if (getDocView_NearDupeIcon().Displayed()) {
				softAssertion.assertEquals(getDocView_NearDupeIcon().Displayed().booleanValue(), true);
				base.passedStep("Near dupe Icon column is displayed in Near dupe TAB Column");
			}
			base.stepInfo("Verify whether Near Dupe % icon is displayed in near native viewer");
			if (getDocView_NearDupe_PercentageIconInNearDupeNativeViewer().Displayed()) {
				softAssertion.assertEquals(
						getDocView_NearDupe_PercentageIconInNearDupeNativeViewer().Displayed().booleanValue(), true);
				base.passedStep("Near dupe % Icon column is displayed");
			}
		} catch (Exception e) {
			base.failedStep("Near dupe column verification failed due to this exception " + e.getMessage());
		}

		softAssertion.assertAll();

	}

	/**
	 * @Author Steffy Created on 08/10/2021
	 * @Description To verify Conceptual tab columns Case id: RPMXCON-51290
	 * 
	 */
	public void verifyConceptualTabColumns() throws InterruptedException {
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().ScrollTo();
		base.waitForElement(getDocView_Analytics_liDocumentConceptualSimilarab());
		getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);
		base.waitForElement(getDocView_Conceptual_DocIdColumn());
		try {
			base.stepInfo("Verify whether the DOC Id column is getting displayed in Conceptual TAB Column");
			if (getDocView_Conceptual_DocIdColumn().Displayed()) {
				softAssertion.assertEquals(getDocView_Conceptual_DocIdColumn().Displayed().booleanValue(), true);
				base.passedStep("DOC ID column is displayed in Conceptual TAB Column");
			}
			base.stepInfo("Verify whether the File Name column is getting displayed in Conceptual TAB Column");
			if (getDocView_Conceptual_FileNameColumn().Displayed()) {
				softAssertion.assertEquals(getDocView_Conceptual_FileNameColumn().Displayed().booleanValue(), true);
				base.passedStep("File Name column is displayed in Conceptual TAB Column");
			}
			base.stepInfo("Verify whether the PERCENTAGE SYMBOL is not getting displayed in Conceptual TAB Column");
			if (getDocView_Conceptual_FileNameColumn().Displayed()) {
				softAssertion.assertEquals(getDocView_Conceptual_DocIdColumn().getText().contains("%"), false);
				softAssertion.assertEquals(getDocView_Conceptual_FileNameColumn().getText().contains("%"), false);
				base.passedStep("PERCENTAGE symbol is not displayed in Conceptual TAB Column");
			}
			base.stepInfo("Verify whether the Check Box is getting displayed in Conceptual TAB Column");
			if (getDocView_Conceptual_CheckBoxColumn().Displayed()) {
				softAssertion.assertEquals(getDocView_Conceptual_CheckBoxColumn().Displayed().booleanValue(), true);
				base.passedStep("Check box column is displayed in Conceptual TAB Column");
			}
			base.stepInfo(
					"Verify whether the Code Same/Code Complete icon column is getting displayed in Conceptual TAB Column");
			if (getDocView_Conceptual_CodeSameIconColumns().Displayed()) {
				softAssertion.assertEquals(getDocView_Conceptual_CodeSameIconColumns().Displayed().booleanValue(),
						true);
				base.passedStep("Code Same Icon column is displayed in Conceptual TAB Column");
			}
		} catch (Exception e) {
			base.failedStep("Conceptual similar column verification failed due to this exception " + e.getMessage());
		}

		softAssertion.assertAll();

	}

	/**
	 * @Author Mohan Created on 07/10/2021
	 * @Description To verify Folder Action ThreadMap Docs
	 * 
	 */
	public void verifyFolderActionForThreadMapDocs(String text) {

		driver.waitForPageToBeReady();
		JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
		driver.waitForPageToBeReady();
		Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
		je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
		getDocView_Analytics_liDocumentThreadMap().ScrollTo();
		getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);

		for (int i = 2; i <= 3; i++) {
			base.waitForElement(getDocView_Analytics_ThreadMap_DocCheckBox(i));
			getDocView_Analytics_ThreadMap_DocCheckBox(i).waitAndClick(10);
		}

		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(10);

		base.waitForElement(getDocView_Analytics_Thread_Folder());
		getDocView_Analytics_Thread_Folder().waitAndClick(10);

		driver.waitForPageToBeReady();
		softAssertion.assertTrue(getDocView_AnalyticsNewFolderThreadMap().Displayed());
		base.passedStep("Folder pop up is opened successfully");

		base.waitForElement(getDocView_AnalyticsNewFolderThreadMap());
		getDocView_AnalyticsNewFolderThreadMap().waitAndClick(10);

		base.waitForElement(getDocView_AnalyticsNewFolderTree());
		getDocView_AnalyticsNewFolderTree().waitAndClick(10);

		base.waitForElement(getDocView_AnalyticsNewFolderTextBox());
		getDocView_AnalyticsNewFolderTextBox().SendKeys(text);

		base.waitForElement(getDocView_AnalyticsNewFolderContiBtn());
		getDocView_AnalyticsNewFolderContiBtn().waitAndClick(10);

		base.waitForElement(getTotalSelectedDocuments());
		softAssertion.assertTrue(getDocView_AnalyticsNewFolderFinalizeBtn().Displayed());
		getDocView_AnalyticsNewFolderFinalizeBtn().waitAndClick(10);

		base.passedStep("Selected folder is applied to the selected documents successfully");

	}

	/**
	 * @Author Mohan Created on 07/10/2021
	 * @Description To perform CodeSame for threaded documents in the DocView Test
	 *              Case id: RPMXCON-51370 & RPMXCON - 51371
	 * 
	 */
	public void performCodeSameForThreadedDocumentsForReviewer() throws InterruptedException {

		driver.waitForPageToBeReady();
		JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
		driver.waitForPageToBeReady();
		Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
		je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
		getDocView_Analytics_liDocumentThreadMap().ScrollTo();
		getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);

		for (int i = 3; i <= 3; i++) {
			base.waitForElement(getDocView_Analytics_ThreadMap_DocCheckBox(i));
			getDocView_Analytics_ThreadMap_DocCheckBox(i).waitAndClick(10);
		}

		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(15);

		base.waitForElement(getDocView_Analytics_Thread_CodeSameAs());
		getDocView_Analytics_Thread_CodeSameAs().waitAndClick(15);

		base.VerifySuccessMessage("Code same performed successfully.");

		driver.waitForPageToBeReady();
		for (int i = 3; i <= 3; i++) {
			base.waitForElement(geDocView_ThreadMap_CodeSameAsIconForReviewer(i));
			softAssertion.assertEquals(geDocView_ThreadMap_CodeSameAsIconForReviewer(i).Displayed().booleanValue(),
					true);
			try {
				if (geDocView_ThreadMap_CodeSameAsIconForReviewer(i).Displayed()) {
					base.passedStep("CodeAsSame icon is displayed for the selected docs ");
				}
			} catch (Exception e) {
				base.failedStep("CodeAsSame icon is not displayed for the selected docs");
				UtilityLog.info("Verification failed due to " + e.getMessage());
			}

		}
		codeSameDocumentid = getThreadedDocumentWhichHasCodeSameIcon().getText();

		softAssertion.assertAll();
	}

	/**
	 * @author Mohan 08/10/21 NA Modified date: NA Modified by:NA
	 * @description To edit coding form and complete
	 */
	public void editCodingFormCompleteForThreadDocs() {

		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_Analytics_liDocumentThreadMap());
		getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);
		for (int i = 2; i <= 2; i++) {
			base.waitForElement(getDocView_Analytics_ThreadMap_DocCheckBox(i));
			getDocView_Analytics_ThreadMap_DocCheckBox(i).waitAndClick(10);
		}

		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(15);

		base.waitForElement(getDocView_Analytics_Thread_ViewDocument());
		getDocView_Analytics_Thread_ViewDocument().waitAndClick(10);

		base.waitForElement(getDocument_CommentsTextBox());
		getDocument_CommentsTextBox().SendKeys("Editing and click complete button");

		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {
				return getCompleteDocBtn().Enabled() && getCompleteDocBtn().Visible();
			}
		}), Input.wait30);

		driver.scrollPageToTop();

		getCompleteDocBtn().waitAndClick(10);

		base.VerifySuccessMessage("Document completed successfully");

	}

	/**
	 * @author Mohan 08/10/21 NA Modified date: NA Modified by:NA
	 * @description To edit coding form and complete for reviewer
	 */
	public void editCodingFormCompleteForThreadDocsForReviewer() {

		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_Analytics_liDocumentThreadMap());
		getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);
		for (int i = 3; i <= 3; i++) {
			base.waitForElement(getDocView_Analytics_ThreadMap_DocCheckBox(i));
			getDocView_Analytics_ThreadMap_DocCheckBox(i).waitAndClick(10);
		}

		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(15);

		base.waitForElement(getDocView_Analytics_Thread_ViewDocument());
		getDocView_Analytics_Thread_ViewDocument().waitAndClick(10);

		base.waitForElement(getDocument_CommentsTextBox());
		getDocument_CommentsTextBox().SendKeys("Editing and click complete button");

		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {
				return getCompleteDocBtn().Enabled() && getCompleteDocBtn().Visible();
			}
		}), Input.wait30);

		driver.scrollPageToTop();

		getCompleteDocBtn().waitAndClick(10);

		base.VerifySuccessMessage("Document completed successfully");

	}

	/**
	 * @Author Steffy Created on 10/10/2021
	 * @Description To perform CodeSame in Threaded tab in the DocView. Test Case
	 *              id: RPMXCON- 51372
	 */
	public void performCodeSameForThreadDocuments(String windowId) throws InterruptedException {

		base.waitForElement(getDocView_Analytics_liDocumentThreadMap());
		getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);

		for (int i = 1; i <= 1; i++) {
			base.waitForElement(getDocView_Analytics_ThreadedDocument_Doc(i));
			getDocView_Analytics_ThreadedDocument_Doc(i).waitAndClick(5);
		}

		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(15);

		base.waitForElement(getDocView_Analytics_Thread_CodeSameAs());
		getDocView_Analytics_Thread_CodeSameAs().waitAndClick(15);

		driver.switchTo().window(windowId);

		base.VerifySuccessMessage("Code same performed successfully.");
		base.CloseSuccessMsgpopup();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!windowId.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		for (int i = 1; i <= 1; i++) {
			base.waitForElement(geDocView_ThreadMap_CodeSameAsIcon(i));
			softAssertion.assertEquals(geDocView_ThreadMap_CodeSameAsIcon(i).Displayed().booleanValue(), true);
			try {
				if (geDocView_ThreadMap_CodeSameAsIcon(i).Displayed()) {
					base.passedStep("CodeAsSame icon is displayed for the selected docs ");
				}
			} catch (Exception e) {
				base.failedStep("CodeAsSame icon is not displayed for the selected docs");
				UtilityLog.info("Verification failed due to " + e.getMessage());
			}

		}

		base.waitForElement(getThreadedDocumentWhichHasCodeSameIcon());
		codeSameDocumentid = getThreadedDocumentWhichHasCodeSameIcon().getText();

	}

	/**
	 * @Author Steffy Created on 10/10/2021
	 * @Description To perform CodeSame in Thread tab in the DocView for Reviewer.
	 *              Test Case id: RPMXCON- 51372
	 */
	public void performCodeSameForThreadDocumentsForReviewer(String windowId) throws InterruptedException {

		driver.waitForPageToBeReady();
		JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
		driver.waitForPageToBeReady();
		Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
		je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
		getDocView_Analytics_liDocumentThreadMap().ScrollTo();
		getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);

		for (int i = 3; i <= 3; i++) {
			base.waitForElement(getDocView_Analytics_ThreadMap_DocCheckBox(i));
			getDocView_Analytics_ThreadMap_DocCheckBox(i).waitAndClick(10);
		}

		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(15);

		base.waitForElement(getDocView_Analytics_Thread_CodeSameAs());
		getDocView_Analytics_Thread_CodeSameAs().waitAndClick(15);

		driver.switchTo().window(windowId);

		base.VerifySuccessMessage("Code same performed successfully.");

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!windowId.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		driver.waitForPageToBeReady();
		for (int i = 3; i <= 3; i++) {
			base.waitForElement(geDocView_ThreadMap_CodeSameAsIconForReviewer(i));
			softAssertion.assertEquals(geDocView_ThreadMap_CodeSameAsIconForReviewer(i).Displayed().booleanValue(),
					true);
			try {
				if (geDocView_ThreadMap_CodeSameAsIconForReviewer(i).Displayed()) {
					base.passedStep("CodeAsSame icon is displayed for the selected docs ");
				}
			} catch (Exception e) {
				base.failedStep("CodeAsSame icon is not displayed for the selected docs");
				UtilityLog.info("Verification failed due to " + e.getMessage());
			}

		}
		codeSameDocumentid = getThreadedDocumentWhichHasCodeSameIcon().getText();

	}

	/**
	 * @Author Steffy Created on 09/10/2021
	 * @Description To perform CodeSame thread docs in the DocView Test Case id:
	 *              RPMXCON-51374
	 * 
	 */
	public void performCodeSameForFamilyMembersDocuments() throws InterruptedException {

		base.waitForElement(getDocView_Analytics_FamilyTab());
		getDocView_Analytics_FamilyTab().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Analytics_ChildWindow_FamilyTab_Firstdoc().Displayed();
			}
		}), Input.wait60);
		getDocView_Analytics_ChildWindow_FamilyTab_Firstdoc().waitAndClick(10);

		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(15);

		getDocView_Analytics_Family_Member_CodeSameAs().waitAndClick(15);

		base.VerifySuccessMessage("Code same performed successfully.");

		for (int i = 1; i <= 1; i++) {
			base.waitForElement(geDocView_FamilyMembers_CodeSameAsIcon(i));
			softAssertion.assertEquals(geDocView_FamilyMembers_CodeSameAsIcon(i).Displayed().booleanValue(), true);
			try {
				if (geDocView_FamilyMembers_CodeSameAsIcon(i).Displayed()) {
					base.passedStep("CodeAsSame icon is displayed for the selected docs ");
				}
			} catch (Exception e) {
				base.failedStep("CodeAsSame icon is not displayed for the selected docs");
				UtilityLog.info("Verification failed due to " + e.getMessage());
			}

		}

		codeSameDocumentid = getFamilyMembersWhichHasCodeSameIcon().getText();

	}

	/**
	 * @author Gopinath
	 * @Description :Filling document comments section.
	 */
	public void fillingTheDocviewCommentsSection(String Text) {
		try {

			driver.waitForPageToBeReady();
			base.waitForElement(getDocviewCommentSection());
			base.waitTillElemetToBeClickable(getDocviewCommentSection());
			getDocviewCommentSection().Click();
			getDocviewCommentSection().SendKeys(Text);
			base.waitForElement(getSelectSaveLink());
			driver.waitForPageToBeReady();
			base.waitTillElemetToBeClickable(getSelectSaveLink());
			getSelectSaveLink().Click();
			driver.waitForPageToBeReady();
			base.VerifySuccessMessage("Document saved successfully");
		} catch (Exception e) {
			base.failedStep("Exception occcured while filling document_comments section ." + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @author Mohan 11/10/21 NA Modified date: NA Modified by:NA
	 * @description To select completed button
	 */
	public void selectCompleteButton() {

		try {
			if (getCompleteDocBtn().Displayed()) {
				base.waitForElement(getCompleteDocBtn());
				softAssertion.assertTrue(getCompleteDocBtn().Displayed());
				getCompleteDocBtn().waitAndClick(10);

				driver.waitForPageToBeReady();
				base.VerifySuccessMessage("Document completed successfully");

				base.passedStep("Complete button is clicked successfully");

			}
		} catch (Exception e) {
			base.failedStep("Complete button is not clicked");
		}
	}

	/**
	 * @author Mohan 11/10/21 NA Modified date: NA Modified by:NA
	 * @description To verify Complete check mark for thread map tab
	 */
	public void verifyCompleteCheckMarkForThreadMapTabDocs() {

		driver.waitForPageToBeReady();
		JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
		driver.waitForPageToBeReady();
		Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
		je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
		getDocView_Analytics_liDocumentThreadMap().ScrollTo();
		getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);
		base.waitForElement(getCodeCompleteIconThreadTab());

		for (int i = 2; i <= 2; i++) {
			try {
				if (geDocView_ThreadMap_CheckMarkIcon(i).getWebElement().isDisplayed())
					softAssertion.assertEquals(geDocView_ThreadMap_CheckMarkIcon(i).Displayed().booleanValue(), true);
				base.passedStep("Completed CheckMark is displayed under thread map tab Successfully");
			} catch (Exception e) {
				base.failedStep("Complete checkmark Icon is not displayed under thread map tab");
			}

		}
	}

	/**
	 * @author Mohan 11/10/21 NA Modified date: NA Modified by:NA
	 * @description To verify uncomplete check mark for thread map tab
	 */
	public void verifyUncompleteCheckMarkForThreadMapTabDocs() {

		driver.waitForPageToBeReady();
		JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
		driver.waitForPageToBeReady();
		Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
		je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
		getDocView_Analytics_liDocumentThreadMap().ScrollTo();
		getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);
		base.waitForElement(getCodeCompleteIconThreadTab());

		for (int i = 2; i <= 2; i++) {
			try {
				base.waitForElement(geDocView_ThreadMap_ArrowDownIcon(i));
				if (geDocView_ThreadMap_ArrowDownIcon(i).getWebElement().isDisplayed())
					softAssertion.assertEquals(geDocView_ThreadMap_ArrowDownIcon(i).Displayed().booleanValue(), true);
				base.passedStep("Arrow Down Icon is displayed under thread map tab Successfully");
			} catch (Exception e) {
				base.failedStep("Arrow Down Icon is not displayed under thread map tab");
			}

		}
	}

	/**
	 * @Author Steffy Created on 09/10/2021
	 * @Description To perform CodeSame thread docs in the DocView Test Case id:
	 *              RPMXCON-51376
	 * 
	 */
	public void performCodeSameForFamilyMembersDocuments(String windowId) throws InterruptedException {

		driver.waitForPageToBeReady();
		JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
		driver.waitForPageToBeReady();
		Point p = getDocView_Analytics_liDocumentThreadMap().getWebElement().getLocation();
		je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
//		getDocView_Analytics_FamilyTab().ScrollTo();
		getDocView_Analytics_FamilyTab().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Analytics_ChildWindow_FamilyTab_Firstdoc().Displayed();
			}
		}), Input.wait60);
		getDocView_Analytics_ChildWindow_FamilyTab_Firstdoc().waitAndClick(15);

		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(15);

		base.waitForElement(getDocView_Analytics_Family_Member_CodeSameAs());
		getDocView_Analytics_Family_Member_CodeSameAs().waitAndClick(15);

		driver.switchTo().window(windowId);

		base.VerifySuccessMessage("Code same performed successfully.");
		base.CloseSuccessMsgpopup();

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!windowId.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		for (int i = 1; i <= 1; i++) {
			base.waitForElement(geDocView_FamilyMembers_CodeSameAsIcon(i));
			softAssertion.assertEquals(geDocView_FamilyMembers_CodeSameAsIcon(i).isDisplayed().booleanValue(), true);
			try {
				if (geDocView_FamilyMembers_CodeSameAsIcon(i).isDisplayed()) {
					base.passedStep("CodeAsSame icon is displayed for the selected docs ");
				} else
					base.failedStep("CodeAsSame icon is not displayed for the selected docs");
			} catch (Exception e) {
				base.failedStep("CodeAsSame icon is not displayed for the selected docs");
				UtilityLog.info("Verification failed due to " + e.getMessage());
			}

		}

		codeSameDocumentid = getFamilyMembersWhichHasCodeSameIcon().getText();

	}

	/**
	 * @Author Steffy Created on 13/10/2021
	 * @Description To perform CodeSame thread docs in the DocView Test Case id:
	 *              RPMXCON-51377
	 * 
	 */
	public void performCodeSameForFamilyMembersDocumentsForReviewer(String windowId) throws InterruptedException {

		driver.waitForPageToBeReady();
		JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
		driver.waitForPageToBeReady();
		Point p = getDocView_Analytics_liDocumentThreadMap().getWebElement().getLocation();
		je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
		getDocView_Analytics_FamilyTab().ScrollTo();
		getDocView_Analytics_FamilyTab().waitAndClick(10);

		for (int i = 2; i <= 2; i++) {
			base.waitForElement(getDocView_Analytics_FamilyMember_Doc(i));
			getDocView_Analytics_FamilyMember_Doc(i).waitAndClick(5);
		}
		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(15);

		base.waitForElement(getDocView_Analytics_Family_Member_CodeSameAs());
		getDocView_Analytics_Family_Member_CodeSameAs().waitAndClick(15);

		driver.switchTo().window(windowId);

		base.VerifySuccessMessage("Code same performed successfully.");

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!windowId.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		for (int i = 2; i <= 2; i++) {
			base.waitForElement(geDocView_FamilyMembers_CodeSameAsIcon(i));
			softAssertion.assertEquals(geDocView_FamilyMembers_CodeSameAsIcon(i).Displayed().booleanValue(), true);
			try {
				if (geDocView_FamilyMembers_CodeSameAsIcon(i).Displayed()) {
					base.passedStep("CodeAsSame icon is displayed for the selected docs ");
				}
			} catch (Exception e) {
				base.failedStep("CodeAsSame icon is not displayed for the selected docs");
				UtilityLog.info("Verification failed due to " + e.getMessage());
			}

		}

		codeSameDocumentid = getFamilyMembersWhichHasCodeSameIcon().getText();

	}

	/**
	 * @author Mohan 10/13/21 NA Modified date: NA Modified by:NA
	 * @description to pop-out NearDupe tab in AnalyticsPanel
	 */
	public void popOutAnalyticsPanelForNearDupe() {

		try {

			driver.waitForPageToBeReady();
			getDocView_ChildWindowPopOut().ScrollTo();
			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getDocView_ChildWindowPopOut().Visible() && getDocView_ChildWindowPopOut().Enabled();
				}
			}, Input.wait30);
			getDocView_ChildWindowPopOut().Click();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Doc is viewed in Analytics panel with pop-out child window successfully");
		}
	}

	/**
	 * @Author Mohan Created on 13/20/2021
	 * @Description To popout coding form and complete docs in the DocView. Test
	 *              Case id: RPMXCON- 51718
	 */
	public void popOutCodingFormChildWindowForNearDupe() {

		try {

			driver.scrollPageToTop();
			getDocument_CommentsTextBox().waitAndClick(10);

			String parentWindowID = driver.getWebDriver().getWindowHandle();

			driver.scrollPageToTop();
			driver.WaitUntil(new Callable<Boolean>() {
				public Boolean call() {
					return getDocView_CodingFormPopOut().Visible() && getDocView_CodingFormPopOut().Enabled();
				}
			}, Input.wait30);
			getDocView_CodingFormPopOut().waitAndClick(10);

			Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
			for (String eachId : allWindowsId) {
				if (!parentWindowID.equals(eachId)) {
					driver.switchTo().window(eachId);
				}
			}

			driver.waitForPageToBeReady();

			driver.WaitUntil((new Callable<Boolean>() {

				public Boolean call() {

					return getDocument_CommentsTextBox().Displayed() && getDocument_CommentsTextBox().Enabled();
				}
			}), Input.wait30);

			getDocument_CommentsTextBox().SendKeys("Editing and click complete button");

			if (!getCodeSameAsLast().getWebElement().isDisplayed()) {
				driver.scrollPageToTop();
				base.waitForElement(getCodeSameAsLast());
				getCodeSameAsLast().waitAndClick(15);
			} else {
				base.waitForElement(getCodeSameAsLast());
				getCodeSameAsLast().waitAndClick(15);

			}

			driver.switchTo().window(parentWindowID);

			base.VerifySuccessMessage("Coded as per the coding form for the previous document");

			allWindowsId = driver.getWebDriver().getWindowHandles();
			for (String eachId : allWindowsId) {
				if (!parentWindowID.equals(eachId)) {
					driver.switchTo().window(eachId);
				}
			}

			driver.getWebDriver().close();

			driver.switchTo().window(parentWindowID);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Coding form is viewed with pop-out child window successfully");
		}
	}

	/**
	 * @author Mohan 13/10/21 NA Modified date: NA Modified by:NA
	 * @description to verify check mark
	 */
	public void verifyCheckMark() {

		try {
			driver.waitForPageToBeReady();
			if (getverifyCodeSameAsLast().getWebElement().isDisplayed()) {
				base.waitForElement(getverifyCodeSameAsLast());
				softAssertion.assertTrue(getverifyCodeSameAsLast().isDisplayed());
				base.passedStep("CheckMark verified successfully");
			} else
				base.failedStep("Checkmark is not verified");

		} catch (Exception e) {
			base.failedStep("Checkmark is not verified");
		}

	}

	/**
	 * @author Mohan 20/10/21 NA Modified date: NA Modified by:NA
	 * @description to verify check mark
	 */
	public void verifyFamilyMemberRemainsSameNavigatedAlso() {

		driver.waitForPageToBeReady();

		try {
			if (getDocView_Analytics_FamilyTab().getWebElement().isDisplayed()) {
//				getDocView_FamilyMember_NoQuery().ScrollTo();
				base.waitForElement(getDocView_FamilyMember_NoQuery());
				softAssertion.assertTrue(getDocView_FamilyMember_NoQuery().Displayed());
				base.passedStep(
						"After docs selected in MiniDoclist it remains in the same 'Family Member'Tab successfully");
			}

		} catch (Exception e) {
			base.failedStep("There is a change in the tab");
		}

	}

	/**
	 * @author Mohan 20/10/21 NA Modified date: NA Modified by:NA
	 * @description to verify check mark
	 */
	public void verifyConceputalTabRemainsSameNavigatedAlso() {

		driver.waitForPageToBeReady();

		try {
			if (getDocView_Analytics_liDocumentConceptualSimilarab().isDisplayed()) {
//				getDocView_Conceptual_DocIdColumn().ScrollTo();
				base.waitForElement(getDocView_Conceptual_DocIdColumn());
				softAssertion.assertTrue(getDocView_Conceptual_DocIdColumn().isDisplayed());
				base.passedStep(
						"After docs selected in MiniDoclist it remains in the same 'Conceptual Similar'Tab successfully");
				softAssertion.assertAll();
			} else
				base.failedStep("There is a change in the tab");
		} catch (Exception e) {
			base.failedStep("There is a change in the tab");
		}

	}

	/**
	 * @author Mohan 20/10/21 NA Modified date: NA Modified by:NA
	 * @description to verify thread map remains same
	 */
	public void verifyThreadMapTabRemainsSameNavigatedAlso() {

		driver.waitForPageToBeReady();

		try {
			if (getDocView_Analytics_liDocumentThreadMap().getWebElement().isDisplayed()) {
//				getDocView_Analytics_NoQuery().ScrollTo();
				base.waitForElement(getDocView_Analytics_NoQuery());
				softAssertion.assertTrue(getDocView_Analytics_NoQuery().Displayed());
				base.passedStep(
						"After docs selected in MiniDoclist it remains in the same 'Thread Map'Tab successfully");
			}

		} catch (Exception e) {
			base.failedStep("There is a change in the tab");
		}

	}

	/**
	 * @author Mohan 21/10/21 NA Modified date: NA Modified by:NA
	 * @description to select docs and remove code as same
	 */
	public void selectDocsFromMiniDocsAndRemoveCodeAsSame() {

		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		try {
			for (int i = 1; i <= 2; i++) {
				base.waitForElement(getDocView_MiniDoc_SelectRow(i));
				getDocView_MiniDoc_SelectRow(i).waitAndClick(10);
			}

			base.waitForElement(getDocView_Mini_ActionButton());
			getDocView_Mini_ActionButton().waitAndClick(10);

			base.waitForElement(getDocView__ChildWindow_Mini_RemoveCodeSameAs());
			getDocView__ChildWindow_Mini_RemoveCodeSameAs().waitAndClick(10);

			base.VerifySuccessMessage("Code Same has been successfully removed");

			try {
				if (geDocView_MiniDocList_ArrowDownIcon().isDisplayed()) {
					base.passedStep("ArrowDown icon is displayed for the selected docs ");
				} else {
					base.failedStep("ArrownDown icon is not displayed for the selected docs");
				}
			} catch (Exception e) {
				base.failedStep("ArrownDown icon is not displayed for the selected docs");
				UtilityLog.info("Verification failed due to " + e.getMessage());
			}

		} catch (Exception e) {
			base.failedStep("Remove CodeAs Same icon is displayed for the selected docs");
			UtilityLog.info("Verification failed due to " + e.getMessage());
		}
	}

	/**
	 * @Author Mohan Created on 21/10/2021
	 * @Description To select docs from Analytics Conceptual Tab and verify code
	 *              same as icon
	 * 
	 */
	public void selectDocsFromConceptualTabAndActionCodeSame() {

		try {
			driver.waitForPageToBeReady();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
//			getDocView_Analytics_liDocumentConceptualSimilarab().ScrollTo();
			getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);

			for (int i = 2; i <= 3; i++) {
				base.waitForElement(getDocView_Analytics_Concept_DocCheckBox(i));
				getDocView_Analytics_Concept_DocCheckBox(i).waitAndClick(10);
			}
			base.waitForElement(getDocView_ChildWindow_ActionButton());
			getDocView_ChildWindow_ActionButton().waitAndClick(10);

			base.waitForElement(getDocView_Analytics_Concept_Similar_CodeSameAs());
			getDocView_Analytics_Concept_Similar_CodeSameAs().waitAndClick(10);

			base.VerifySuccessMessage("Code same performed successfully.");

			// verify Code Same as Link
			try {
				if (getDocView_Analytics_Concept_CodeSameLink().isDisplayed()) {
					softAssertion.assertEquals(getDocView_Analytics_Concept_CodeSameLink().isDisplayed().booleanValue(),
							true);
					base.passedStep(
							"Code same icon is displayed for the selected documents and documents from Conceptual tab are unchecked successfull");
				} else {
					base.failedStep("Selected Document is not having code as same icon under Conceptual tabss");
				}
			} catch (Exception e) {
				base.failedStep("Selected Document is not having code as same icon under Conceptual tabss");
			}

			softAssertion.assertAll();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Code Same As Icon is not displayed");
		}
	}

	/**
	 * @author Mohan 21/10/21 NA Modified date: NA Modified by:NA
	 * @description to select docs and remove code as same for concept tab
	 */
	public void selectDocsFromMiniDocsAndRemoveCodeAsSameForConceptTab(String miniDocId) {

		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		try {
			for (int i = 1; i <= 1; i++) {
				for (int j = 3; j <= 3; j++) {
					base.waitForElement(getDocView_MiniDoc_SelectRow(i));
					getDocView_MiniDoc_SelectRow(i).waitAndClick(10);

					base.waitForElement(getDocView_MiniDoc_SelectRow(j));
					getDocView_MiniDoc_SelectRow(j).waitAndClick(10);

				}
			}

			base.waitForElement(getDocView_Mini_ActionButton());
			getDocView_Mini_ActionButton().waitAndClick(10);

			base.waitForElement(getDocView__ChildWindow_Mini_RemoveCodeSameAs());
			getDocView__ChildWindow_Mini_RemoveCodeSameAs().waitAndClick(10);

			base.VerifySuccessMessage("Code Same has been successfully removed");

			driver.waitForPageToBeReady();
			selectDocIdInMiniDocList(miniDocId);
			try {
				if (geDocView_MiniDocList_ArrowDownIcon().Displayed()) {
					base.passedStep("ArrowDown icon is displayed for the selected docs ");
				}
			} catch (Exception e) {
				base.failedStep("ArrownDown icon is not displayed for the selected docs");
				UtilityLog.info("Verification failed due to " + e.getMessage());
			}

		} catch (Exception e) {
			base.failedStep("Remove CodeAs Same icon is displayed for the selected docs");
			UtilityLog.info("Verification failed due to " + e.getMessage());
		}
	}

	/**
	 * @author Mohan 21/10/21 NA Modified date: NA Modified by:NA
	 * @description to select docs and action as remove code as same and verify
	 *              warning msg
	 */
	public void verifyWarningMsgWhenOtherDocsSelectedAndRemoveCodeAsSame() {

		driver.waitForPageToBeReady();
		for (int i = 3; i <= 3; i++) {
			base.waitForElement(getDocView_MiniDoc_SelectRow(i));
			getDocView_MiniDoc_SelectRow(i).waitAndClick(10);

		}

		base.waitForElement(getDocView_Mini_ActionButton());
		getDocView_Mini_ActionButton().waitAndClick(10);

		base.waitForElement(getDocView__ChildWindow_Mini_RemoveCodeSameAs());
		getDocView__ChildWindow_Mini_RemoveCodeSameAs().waitAndClick(10);

		base.VerifyWarningMessage("Some documents were not in code same as list, they have been ignored.");

	}

	/**
	 * @author Mohan 21/10/21 NA Modified date: NA Modified by:NA
	 * @description to verify remove code same as in Mini Doclist and Analytics
	 *              Panel
	 */
	public void verifyRemoveCodeAsSameInMiniDoclistAndAnalyticsPanel() {

		driver.waitForPageToBeReady();

		base.waitForElement(getDocView_Mini_ActionButton());
		getDocView_Mini_ActionButton().waitAndClick(10);

		try {
			if (!getDocView__ChildWindow_Mini_RemoveCodeSameAs().getWebElement().isDisplayed()) {
				base.passedStep("Remove Code Same as is not displayed in Mini DocList under action tab");
			}
		} catch (Exception e) {
			base.failedStep("Remove Code Same as is not displayed in Mini DocList under action tab");
		}

		driver.waitForPageToBeReady();
		JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
		driver.waitForPageToBeReady();
		Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
		je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
		getDocView_Analytics_liDocumentThreadMap().ScrollTo();
		getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);

		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(10);

		try {
			if (!getDocView_Analytics_Thread_RemoveCodeSameAs().getWebElement().isDisplayed()) {
				base.passedStep("Remove Code Same as is not displayed in Analytics panel Threadmap under action tab");
			}
		} catch (Exception e) {
			base.failedStep("Remove Code Same as is displayed in Analytics panel Threadmap under action tab");
		}

		getDocView_Analytics_FamilyTab().waitAndClick(10);
		try {
			if (!getDocView_FamilyRemoveCodeSameAs().getWebElement().isDisplayed()) {
				base.passedStep(
						"Remove Code Same as is not displayed in Analytics panel Family Member under action tab");
			}
		} catch (Exception e) {
			base.failedStep("Remove Code Same as is displayed in Analytics panel Family Member under action tab");
		}

		getDocView_Analytics_NearDupeTab().waitAndClick(10);
		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(10);
		try {
			if (!getDocView_Analytics_NearDupe_RemoveCodeSameAs().getWebElement().isDisplayed()) {
				base.passedStep("Remove Code Same as is not displayed in Analytics panel Near Dupe under action tab");
			}
		} catch (Exception e) {
			base.failedStep("Remove Code Same as is displayed in Analytics panel Near Dupe under action tab");
		}
		getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);
		getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);
		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(10);
		try {
			if (!getDocView_Analytics_Concept_RemoveCodeSameAs().getWebElement().isDisplayed()) {
				base.passedStep("Remove Code Same as is not displayed in Analytics panel Concept under action tab");
			}
		} catch (Exception e) {
			base.passedStep("Remove Code Same as is displayed in Analytics panel Concept under action tab");
		}

	}

	/**
	 * @author Raghuram 19/10/21 NA Modified date: NA Modified by:NA
	 * @return
	 * @description
	 */
	public boolean elementAvailability(Element element, String responseMsg1, String responseMsg2) {
		Boolean status = null;
		base.waitForElement(getDocView_RedactionPanel());
		try {
			if (element.isElementPresent()) {
				System.out.println(responseMsg1);
				base.passedStep(responseMsg1);
				status = true;
			} else {
				System.out.println(responseMsg2);
				base.failedMessage(responseMsg2);
				status = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}

	/**
	 * @author Indium-Raghuram date: 10/8/2021 Modified date: 24/8/2021 Modified
	 *         by:Baskar
	 * @Description:Doc view coding form stamp selection
	 */

	public void docViewCodingFormPanelStampSelectionWithoutSave(String colour) throws AWTException {
		driver.waitForPageToBeReady();
		base.waitForElement(getResponsiveCheked());
		getResponsiveCheked().Click();
		base.waitForElement(getNonPrivilegeRadio());
		getNonPrivilegeRadio().Click();
		base.waitForElement(getDocument_CommentsTextBox());
		getDocument_CommentsTextBox().SendKeys("Review");
		driver.scrollPageToTop();
		base.waitForElement(getCodingFormStampButton());
		base.waitTillElemetToBeClickable(getCodingFormStampButton());
		getCodingFormStampButton().waitAndClick(5);
		base.waitForElement(getCodingStampTextBox());
		getCodingStampTextBox().SendKeys("NewColour");
		base.waitForElement(getDrp_StampColour());
		base.waitTillElemetToBeClickable(getDrp_StampColour());
		getDrp_StampColour().Click();
		base.waitTillElemetToBeClickable(getAssignedColour(colour));
		getAssignedColour(colour).Click();
		base.waitForElement(getCodingStampSaveBtn());
		base.waitTillElemetToBeClickable(getCodingStampSaveBtn());
		getCodingStampSaveBtn().waitAndClick(5);
	}

	/**
	 * @author Mohan 22/10/21 NA Modified date: NA Modified by:NA
	 * @description to verify thread map remains same when docs are completed
	 */
	public void verifyThreadMapTabWhenThreadedDocsInMiniDocList() {

		driver.waitForPageToBeReady();
		JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
		driver.waitForPageToBeReady();
		Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
		je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
		getDocView_Analytics_liDocumentThreadMap().ScrollTo();

		try {
			if (getDocView_Analytics_liDocumentThreadMap().getWebElement().isDisplayed()) {
				driver.waitForPageToBeReady();
				getDocView_Analytics_ThreadMap_TableFirstName().ScrollTo();
				base.waitForElement(getDocView_Analytics_ThreadMap_TableFirstName());
				softAssertion.assertTrue(getDocView_Analytics_ThreadMap_TableFirstName().Displayed());
				base.passedStep(
						"After docs selected in MiniDoclist it remains in the same 'Thread Map'Tab successfully");
			}

		} catch (Exception e) {
			base.failedStep("There is a change in the tab");
		}

	}

	/**
	 * @author Mohan 22/10/21 NA Modified date: NA Modified by:NA
	 * @description to verify Family Member tab remains same when docs complted
	 */
	public void verifyFamilyMemberWhenDocsAreCompletedInMiniDocList() {

		driver.waitForPageToBeReady();

		try {
			if (getDocView_Analytics_FamilyTab().getWebElement().isDisplayed()) {
				// getDocView_FamilyMember_DocIdColumn().ScrollTo();
				base.waitForElement(getDocView_FamilyMember_DocIdColumn());
				softAssertion.assertTrue(getDocView_FamilyMember_DocIdColumn().Displayed());
				base.passedStep(
						"After docs selected in MiniDoclist it remains in the same 'Family Member'Tab successfully");
			}

		} catch (Exception e) {
			base.failedStep("There is a change in the tab");
		}

	}

	/**
	 * @author Indium-Baskar date: 22/10/2021 Modified date: NA
	 * @Description : this method used for verify persistent hit panel for disabled
	 *              and enabled conditions for 0 count
	 */
	public void verifyPersistentHitPanel(String panel) {
		driver.waitForPageToBeReady();
		driver.scrollingToElementofAPage(getHitPanleVerify(panel));
		softAssertion.assertTrue(getHitPanleVerify(panel).Displayed());
		String countPersistentHit = getHitPanleVerify(panel).getText();
		if (getHitPanleVerify(panel).Displayed()) {
			base.stepInfo("persistent hit panel displayed in docview panel");
		} else {
			base.failedStep("Hit panel not displayed");
		}
		reusableDocView.clickClockIconMiniDocList();
		driver.waitForPageToBeReady();
		boolean flag = getDocView_ToggleButton().GetAttribute("data-swchoff-text").contains("No");
		softAssertion.assertTrue(flag);
		base.stepInfo("Performing action in history drop down");
		base.passedStep("Hide Terms with 0 hits:Disabled");
		if (getHitPanleVerify(panel).Displayed()) {
			base.stepInfo("persistent hit panel displayed in docview panel in histrory drop down");
		} else {
			base.failedStep("Hit panel not displayed");
		}
		driver.waitForPageToBeReady();
		base.stepInfo("Enabling 0 hits terms in docview panel");
		getDocView_ToggleButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		boolean docYes = getDocView_ToggleButton().GetAttribute("data-swchoff-text").contains("Yes");
		softAssertion.assertFalse(docYes);
		driver.waitForPageToBeReady();
		base.passedStep("Hide Terms with 0 hits:Enabled");
		boolean miniDis = getHitPanleVerify(panel).Displayed();
		softAssertion.assertFalse(miniDis);
		base.passedStep("0 count terms not displayed in persistent hit panel");
		reusableDocView.clickClockIconMiniDocList();
		driver.waitForPageToBeReady();
		boolean historyYes = getDocView_ToggleButton().GetAttribute("data-swchoff-text").contains("Yes");
		softAssertion.assertFalse(historyYes);
		driver.waitForPageToBeReady();
		base.stepInfo("Performing action in history drop down");
		base.passedStep("Hide Terms with 0 hits:Enabled");
		boolean historyDis = getHitPanleVerify(panel).Displayed();
		softAssertion.assertFalse(historyDis);
		base.passedStep("0 count terms not displayed in persistent hit panel");
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium-Baskar date: 22/10/2021 Modified date: NA
	 * @Description : this method used to verify folder action for minidoclist
	 */

	public void performFolderAction(String folderName, int rowno) throws InterruptedException {
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_MiniDoc_SelectRow(rowno));
		getDocView_MiniDoc_SelectRow(rowno).waitAndClick(5);
		base.waitForElement(getDocView_Mini_ActionButton());
		getDocView_Mini_ActionButton().waitAndClick(5);
		base.waitForElement(getDocView_Mini_FolderAction());
		getDocView_Mini_FolderAction().waitAndClick(5);
		sp.getBulkNewTab().waitAndClick(5);
		base.waitForElement(sp.getEnterFolderName());
		sp.getEnterFolderName().SendKeys(folderName);
		base.waitForElement(sp.getFolderAllRoot());
		sp.getFolderAllRoot().waitAndClick(5);
		base.waitForElement(sp.getContinueButton());
		sp.getContinueButton().waitAndClick(5);
		base.waitForElement(sp.getFinalizeButton());
		sp.getFinalizeButton().waitAndClick(5);
		base.VerifySuccessMessage("Records saved successfully");
		base.CloseSuccessMsgpopup();
		System.out.println("Bulk folder is done, folder is : " + folderName);
		base.passedStep("Bulk folder is done, folder is : " + folderName);
		base.waitForElement(getDocView_MiniDocListIds(rowno));
		getDocView_MiniDocListIds(rowno).waitAndClick(5);
		base.waitForElement(getDocView_FolderTab());
		getDocView_FolderTab().waitAndClick(10);
		base.waitForElement(getDocView_FolderTab_Expand());
		getDocView_FolderTab_Expand().waitAndClick(5);
		Thread.sleep(Input.wait30);
		base.waitForElement(getFolderFromList(folderName));
		Assert.assertTrue(getFolderFromList(folderName).Displayed());
		driver.scrollPageToTop();

	}

	/**
	 * @author Mohan 26/10/21 NA Modified date: NA Modified by:NA
	 * @description to select Near Dupe tab and verify docs are displyaed
	 */
	public void selectNearDupeTabAndVerifyDocsAreDisplayed() {

		driver.waitForPageToBeReady();

//		getDocView_Analytics_NearDupeTab().ScrollTo();
		base.waitForElement(getDocView_Analytics_NearDupeTab());
		getDocView_Analytics_NearDupeTab().waitAndClick(10);

		try {
			if (getDocView_Analytics_NearDupe_WholeTable().isDisplayed()) {
				base.passedStep("Near dupes docs are loaded");
			} else {
				base.failedStep("Near dupes docs are not loaded");
			}

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Near dupes docs are not loaded");
		}

	}

	/**
	 * @author Mohan 26/10/21 NA Modified date: NA Modified by:NA
	 * @description to select Family Member tab and verify docs are displyaed
	 */
	public void selectFamilyMemberTabAndVerifyDocsAreDisplayed() {

		driver.waitForPageToBeReady();

//		getDocView_Analytics_FamilyTab().ScrollTo();
		base.waitForElement(getDocView_Analytics_FamilyTab());
		getDocView_Analytics_FamilyTab().waitAndClick(10);

		try {
			if (getDocView_Analytics_FamilyMember_WholeTable().isDisplayed()) {
				base.passedStep("Family Member docs are loaded");
			} else {
				base.failedStep("Family Member docs are not loaded");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Family Member docs are not loaded");
		}

	}

	/**
	 * @author Mohan 26/10/21 NA Modified date: NA Modified by:NA
	 * @description to select Conceptual tab and verify docs are displyaed
	 */
	public void selectConceptuallyTabAndVerifyDocsAreDisplayed() {

		driver.waitForPageToBeReady();

//		getDocView_Analytics_liDocumentConceptualSimilarab().ScrollTo();
		base.waitForElement(getDocView_Analytics_liDocumentConceptualSimilarab());
		getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);

		try {
			if (getDocView_Analytics_Concept_WholeTable().isDisplayed()) {
				base.passedStep("Conceptually Similar docs are loaded");
			} else
				base.failedStep("Conceptually Similar docs are not loaded");

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Conceptually Similar docs are not loaded");
		}

	}

	/**
	 * @author Mohan 26/10/21 NA Modified date: NA Modified by:NA
	 * @description to select Conceptual tab and select a docs and View The Docs.
	 */
	public void selectDocsFromConceptualTabAndViewTheDocs() {

		driver.waitForPageToBeReady();

//		getDocView_Analytics_liDocumentConceptualSimilarab().ScrollTo();
		base.waitForElement(getDocView_Analytics_liDocumentConceptualSimilarab());
		getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);

		try {
			for (int i = 1; i <= 1; i++) {
				base.waitForElement(getDocView_Analytics_Concept_Docs(i));
				getDocView_Analytics_Concept_Docs(i).waitAndClick(10);

			}

			base.waitForElement(getDocView_ChildWindow_ActionButton());
			getDocView_ChildWindow_ActionButton().waitAndClick(10);

			base.waitForElement(getDocView_Analytics_Concept_ViewDocument());
			getDocView_Analytics_Concept_ViewDocument().waitAndClick(10);

			base.passedStep("Docs are viewed in Docview successfully");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Docs are not viewed in Dcoview");
		}
	}

	/**
	 * @author Mohan 26/10/21 NA Modified date: NA Modified by:NA
	 * @description to verify Docs are selected in MiniDocList
	 */
	public void verifyArrowMark() {

		driver.scrollPageToTop();
		driver.waitForPageToBeReady();

		try {
			if (geDocView_MiniDocList_ArrowDownIcon().isDisplayed()) {
				base.waitForElement(geDocView_MiniDocList_ArrowDownIcon());
				softAssertion.assertTrue(geDocView_MiniDocList_ArrowDownIcon().isDisplayed());
				base.passedStep(
						"Document selected to view are loaded in doc view and selected from mini doc list successfully");
			} else
				base.failedStep("Documents are not loaded in Docview");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Documents are not loaded in Docview");
		}

	}

	/**
	 * @author Steffy 27/10/21 NA Modified date: NA Modified by:NA
	 * @description To verify thread docs display documents which has inclusive
	 *              email Yes or No
	 */
	public void verifyThreadDocsDisplayDocsIrrespectiveOfInclusiveEmailValue() {
		try {
			List<WebElement> inclusiveValues = getInclusiveElamilValueFromToolTip().FindWebElements();
			for (int i = 0; i < inclusiveValues.size(); i++) {
				String inclusiveValue = inclusiveValues.get(i).getAttribute("innerText");
				if ((inclusiveValue.equals("Yes")) || inclusiveValue.equals("No")) {
					base.passedStep(
							"Threaded Document display document which has Inclusive Email value as " + inclusiveValue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Threaded Documents does not loaded properly");
		}
	}

	/**
	 * @author Indium-Baskar date: 3/9/2021 Modified date: NA
	 * @Description : Verifying already saved stamp colour in parent window
	 */

	public void openStampPopUpFromParentWindowWindow(String textBox, String colour, String iconColour) {
		reusableDocView.stampColourSelection(textBox, colour);
		driver.getWebDriver().navigate().refresh();
		reusableDocView.pencilGearicon(iconColour);
		reusableDocView.verifyingPostFixAssignedColour();

	}

	/**
	 * @author Indium-Baskar date: 28/10/2021 Modified date: NA
	 * @Description : this method used to verify saved stamp on custom fields
	 */

	public void clickOnSavedStamp(String textBox, String colour, String projectFieldName)
			throws InterruptedException, AWTException {
		driver.waitForPageToBeReady();
		reusableDocView.stampColourSelection(textBox, colour);
		base.waitForElement(getCodingStampLastIcon(colour));
		getCodingStampLastIcon(colour).waitAndClick(10);
		driver.waitForPageToBeReady();
		getReadOnlyTextBox(projectFieldName).WaitUntilPresent().ScrollTo();
		base.waitForElement(getReadOnlyTextBox(projectFieldName));
		if (getReadOnlyTextBox(projectFieldName).Displayed() && getReadOnlyTextBox(projectFieldName).Enabled()) {
			UtilityLog.info("Custom meta data field read and write only format when created with :" + projectFieldName
					+ "dataType");
			base.stepInfo("Parent window");
			base.failedStep("Custom meta data field read and write only format when created with :" + projectFieldName
					+ "dataType");

		} else {
			UtilityLog.info(
					"Custom meta data field read only format when created with :" + projectFieldName + "dataType");
			base.stepInfo("Parent window");
			base.passedStep(
					"Custom meta data field read only format when created with :" + projectFieldName + "dataType");
		}
		driver.scrollPageToTop();
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parentWindow = reusableDocView.switchTochildWindow();
		driver.waitForPageToBeReady();
		base.waitForElement(getCodingStampLastIcon(colour));
		getCodingStampLastIcon(colour).waitAndClick(10);
		getReadOnlyTextBox(projectFieldName).WaitUntilPresent().ScrollTo();
		softAssertion.assertEquals(
				getReadOnlyTextBox(projectFieldName).Displayed() && getReadOnlyTextBox(projectFieldName).Enabled(),
				true, "Custom meta data field read only format when created with INT datatype ");
		if (getReadOnlyTextBox(projectFieldName).Displayed() && getReadOnlyTextBox(projectFieldName).Enabled()) {
			UtilityLog.info("Custom meta data field read and write only format when created with :" + projectFieldName
					+ "dataType");
			base.stepInfo("Child window");
			base.failedStep("Custom meta data field read and write only format when created with :" + projectFieldName
					+ "dataType");

		} else {
			UtilityLog.info(
					"Custom meta data field read only format when created with :" + projectFieldName + "dataType");
			base.stepInfo("Child window");
			base.passedStep(
					"Custom meta data field read only format when created with :" + projectFieldName + "dataType");
		}
		reusableDocView.childWindowToParentWindowSwitching(parentWindow);
		driver.getWebDriver().navigate().refresh();
		driver.switchTo().alert().accept();
		driver.waitForPageToBeReady();
		reusableDocView.pencilGearicon(colour);
		base.waitForElement(getDeletePopUpAssignedColour());
		getDeletePopUpAssignedColour().waitAndClick(10);
	}

	/**
	 * @author Indium-Baskar date: 28/10/2021 Modified date: NA
	 * @Description : this method used to verify saved stamp on parent window
	 */

	public void viewCodingPopUp(String textBox, String colour, String projectFieldName)
			throws InterruptedException, AWTException {
		driver.waitForPageToBeReady();
		reusableDocView.stampColourSelection(textBox, colour);
		driver.waitForPageToBeReady();
		driver.getWebDriver().navigate().refresh();
		reusableDocView.pencilGearicon(colour);
		base.waitForElement(getDeletePopUpAssignedColour());
		getDeletePopUpAssignedColour().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitForElement(getReadOnlyTextBox(projectFieldName));
		if (getReadOnlyTextBox(projectFieldName).Displayed() && getReadOnlyTextBox(projectFieldName).Enabled()) {
			UtilityLog.info("Custom meta data field read and write only format when created with :" + projectFieldName
					+ "dataType");
			base.stepInfo("Parent window");
			base.failedStep("Custom meta data field read and write only format when created with :" + projectFieldName
					+ "dataType");

		} else {
			UtilityLog.info(
					"Custom meta data field read only format when created with :" + projectFieldName + "dataType");
			base.stepInfo("Parent window");
			base.passedStep(
					"Custom meta data field read only format when created with :" + projectFieldName + "dataType");
		}
		driver.scrollPageToTop();
	}

	/**
	 * @author Mohan 03/11/21 NA Modified date: NA Modified by:NA
	 * @description to verify Original View Near Dupe Docs
	 */
	public void verifyOriginalViewNearDupeDocs() {

		driver.waitForPageToBeReady();
		for (int i = 0; i < 20; i++) {
			try {
				base.waitForElement(getDocView_Analytics_NearDupe_OriginalView_ZoomIn());
				base.waitTillElemetToBeClickable(getDocView_Analytics_NearDupe_OriginalView_ZoomIn());
				getDocView_Analytics_NearDupe_OriginalView_ZoomIn().waitAndClick(7);

				base.waitForElement(getDocView_Analytics_NearDupe_OriginalView_ZoomOut());
				base.waitTillElemetToBeClickable(getDocView_Analytics_NearDupe_OriginalView_ZoomOut());
				getDocView_Analytics_NearDupe_OriginalView_ZoomOut().waitAndClick(7);

				base.waitForElement(getDocView_Analytics_NearDupe_OriginalView_RestButton());
				base.waitTillElemetToBeClickable(getDocView_Analytics_NearDupe_OriginalView_RestButton());
				getDocView_Analytics_NearDupe_OriginalView_RestButton().waitAndClick(7);
				break;
			} catch (Exception e) {
				driver.Navigate().refresh();
			}

		}

	}

	/**
	 * @author Mohan 03/11/21 NA Modified date: NA Modified by:NA
	 * @description to verify NearDupe View Near Dupe Docs
	 */
	public void verifyNearDupeViewNearDupeDocs() {

		driver.waitForPageToBeReady();
		for (int i = 0; i < 20; i++) {
			try {
				base.waitForElement(getDocView_Analytics_NearDupe_NearDupeView_ZoomIn());
				base.waitTillElemetToBeClickable(getDocView_Analytics_NearDupe_NearDupeView_ZoomIn());
				getDocView_Analytics_NearDupe_NearDupeView_ZoomIn().waitAndClick(7);

				base.waitForElement(getDocView_Analytics_NearDupe_NearDupeView_ZoomOut());
				base.waitTillElemetToBeClickable(getDocView_Analytics_NearDupe_NearDupeView_ZoomOut());
				getDocView_Analytics_NearDupe_NearDupeView_ZoomOut().waitAndClick(7);

				base.waitForElement(getDocView_Analytics_NearDupe_NearDupeView_RestButton());
				base.waitTillElemetToBeClickable(getDocView_Analytics_NearDupe_NearDupeView_RestButton());
				getDocView_Analytics_NearDupe_NearDupeView_RestButton().waitAndClick(7);
				break;
			} catch (Exception e) {
				driver.Navigate().refresh();
			}

		}

	}

	/**
	 * @author Mohan 03/11/21 NA Modified date: NA Modified by:NA
	 * @description to verify Original View Near Dupe Docs
	 */
	public void verifyOriginalViewZoomInAndZoomOutNearDupeDocs() {

		driver.waitForPageToBeReady();
		for (int i = 0; i < 20; i++) {
			try {
				base.waitForElement(getDocView_Analytics_NearDupe_OriginalView_ZoomIn());
				base.waitTillElemetToBeClickable(getDocView_Analytics_NearDupe_OriginalView_ZoomIn());
				softAssertion.assertEquals(getDocView_Analytics_NearDupe_OriginalView_ZoomIn().Enabled().booleanValue(),
						true);
				getDocView_Analytics_NearDupe_OriginalView_ZoomIn().waitAndClick(7);
				base.passedStep("Zoom-in button is clicked successs");

				base.waitForElement(getDocView_Analytics_NearDupe_OriginalView_ZoomOut());
				base.waitTillElemetToBeClickable(getDocView_Analytics_NearDupe_OriginalView_ZoomOut());
				softAssertion.assertEquals(
						getDocView_Analytics_NearDupe_OriginalView_ZoomOut().Enabled().booleanValue(), true);
				getDocView_Analytics_NearDupe_OriginalView_ZoomOut().waitAndClick(7);
				base.passedStep("Zoom-out button is clicked successs");

				break;
			} catch (Exception e) {
				driver.Navigate().refresh();
			}

		}
	}

	/**
	 * @Author Mohan Created on 09/11/2021
	 * @Description To select docs from Analytics Family Member Tab and verify code
	 *              same as icon
	 * 
	 */
	public void selectDocsFromFamilyMemberTabAndActionCodeSame() {

		try {
			driver.waitForPageToBeReady();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
//			getDocView_Analytics_FamilyTab().ScrollTo();
			getDocView_Analytics_FamilyTab().waitAndClick(10);
			driver.getPageSource();

			for (int i = 1; i <= 2; i++) {
				base.waitForElement(getDocView_Analytics_FamilyMember_DocCheckBox(i));
				driver.getPageSource();
				getDocView_Analytics_FamilyMember_DocCheckBox(i).waitAndClick(10);
			}
			base.waitForElement(getDocView_ChildWindow_ActionButton());
			getDocView_ChildWindow_ActionButton().waitAndClick(10);

			base.waitForElement(getDocView_FamilyCodeSameAs());
			getDocView_FamilyCodeSameAs().waitAndClick(10);

			base.VerifySuccessMessage("Code same performed successfully.");

			// verify Code Same as Link
			try {
				if (getDocView_Analytics_FamilyMember_CodeSameLink().isDisplayed()) {
					softAssertion.assertEquals(
							getDocView_Analytics_FamilyMember_CodeSameLink().isDisplayed().booleanValue(), true);
					base.passedStep(
							"Code same icon is displayed for the selected documents and documents from Family Member are unchecked successfull");
				} else
					base.failedStep("Selected Document is not having code as same icon under Family Member tabss");
			} catch (Exception e) {
				base.failedStep("Selected Document is not having code as same icon under Family Member tabss");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Code Same As Icon is not displayed");
		}
	}

	/**
	 * @Author Mohan Created on 09/11/2021
	 * @Description To select docs from Analytics Family Member Tab and verify
	 *              remove code same as is performed.
	 * 
	 */
	public void selectDocsFromFamilyMemberTabAndActionRemoveCodeSame() {

		try {
			driver.waitForPageToBeReady();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
//			getDocView_Analytics_FamilyTab().ScrollTo();
			getDocView_Analytics_FamilyTab().waitAndClick(10);

			for (int i = 1; i <= 2; i++) {
				base.waitForElement(getDocView_Analytics_FamilyMember_DocCheckBox(i));
				getDocView_Analytics_FamilyMember_DocCheckBox(i).waitAndClick(10);
			}
			base.waitForElement(getDocView_ChildWindow_ActionButton());
			getDocView_ChildWindow_ActionButton().waitAndClick(10);

			base.waitForElement(getDocView_FamilyRemoveCodeSameAs());
			getDocView_FamilyRemoveCodeSameAs().waitAndClick(10);

			base.VerifySuccessMessage("Code Same has been successfully removed");
			driver.waitForPageToBeReady();
			base.waitTime(5);
			// verify Code Same as Link
			try {
				if (getDocView_Analytics_FamilyMember_CodeSameLink().isDisplayed()) {
					softAssertion.assertTrue(getDocView_Analytics_FamilyMember_CodeSameLink().isDisplayed());
					base.failedStep("Selected Document is having code as same icon under Family Member tabss");
				} else
					base.passedStep(
							"Code same icon is not displayed for the selected documents and documents from Family Member are unchecked successfull");
			} catch (Exception e) {
				base.passedStep(
						"Code same icon is not displayed for the selected documents and documents from Family Member are unchecked successfull");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Code Same As Icon is not displayed");
		}
	}

	/**
	 * @Author Mohan Created on 09/11/2021
	 * @Description To select docs from Analytics Family Member Tab and verify
	 *              remove code same as is performed.
	 * 
	 */
	public void selectDocsFromConceptTabAndActionRemoveCodeSame() {

		try {
			driver.waitForPageToBeReady();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
//			getDocView_Analytics_liDocumentConceptualSimilarab().ScrollTo();
			getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);

			for (int i = 2; i <= 3; i++) {
				base.waitForElement(getDocView_Analytics_Concept_DocCheckBox(i));
				getDocView_Analytics_Concept_DocCheckBox(i).waitAndClick(10);
			}
			base.waitForElement(getDocView_ChildWindow_ActionButton());
			getDocView_ChildWindow_ActionButton().waitAndClick(10);

			base.waitForElement(getDocView_Analytics_Concept_RemoveCodeSameAs());
			getDocView_Analytics_Concept_RemoveCodeSameAs().waitAndClick(10);

			base.VerifySuccessMessage("Code Same has been successfully removed");
			base.waitTime(3);

			// verify Code Same as Link
			try {
				if (getDocView_Analytics_Concept_CodeSameLink().isDisplayed()) {
					base.failedStep("Selected Document is having code as same icon under Conceptual tabss");
				}
			} catch (Exception e) {
				base.passedStep(
						"Code same icon is not displayed for the selected documents and documents from Conceptual tab are unchecked successfully");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Code Same As Icon is not displayed");
		}
	}

	/**
	 * @author Indium-Baskar date: 09/11/2021 Modified date: NA
	 * @Description : this method used to verify Code same as last button is
	 *              displayed or not
	 */

	public void codeSameAsLastIsDisplayed() {
		driver.waitForPageToBeReady();
		Boolean flag = getCodeSameAsLast().Displayed();
		softAssertion.assertTrue(flag);

	}

	/**
	 * @author Indium-Baskar date: 15/09/2021 Modified date: NA
	 * @Description : this method used for code same as last after applying stamp
	 */

	public void codeSameAsLastAfterStamp(String fieldValue, String lastIcon, String lastIcons)
			throws InterruptedException, AWTException {
		driver.waitForPageToBeReady();
		reusableDocView.editingCodingFormWithSaveButton();
		reusableDocView.codingFormSavingWithCodingStamp(fieldValue, lastIcon);
		reusableDocView.errorMessageForStamp();
		base.waitForElement(getCodingStampLastIcon(lastIcon));
		getCodingStampLastIcon(lastIcon).waitAndClick(10);
		String getAttribute = getDocument_CommentsTextBox().WaitUntilPresent().GetAttribute("value");
		softAssertion.assertEquals("Edited and save with save button", getAttribute);
		base.waitForElement(getCodingFormSaveButton());
		getCodingFormSaveButton().waitAndClick(5);
		base.stepInfo("Document saved successfully");
		for (int i = 5; i <= 5; i++) {
			getClickDocviewID(i).waitAndClick(5);
		}
		driver.waitForPageToBeReady();
		String docsText = getVerifyPrincipalDocument().getText().trim();
		reusableDocView.clickCodeSameAsLast();
		String principalAgainAfterLastBtn = getVerifyPrincipalDocument().getText().trim();
		softAssertion.assertNotEquals(docsText, principalAgainAfterLastBtn);
		getHeader().waitAndClick(5);
		reusableDocView.deleteStampColour(lastIcons);
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium-Baskar date: 15/09/2021 Modified date: NA
	 * @Description : this method used for edit coding form after impersonation
	 */

	public void verifyAfterImpersonateUserCanEditCodingForm() {
		driver.waitForPageToBeReady();
		String currentDocId = reusableDocView.editingCodingFormWithSaveAndNextButton();
		driver.waitForPageToBeReady();
		String afterEditDocId = getVerifyPrincipalDocument().getText().trim();
		softAssertion.assertNotEquals(currentDocId, afterEditDocId);
		if (currentDocId.equals(afterEditDocId)) {
			base.failedStep("Cursor not moved to the next document in minidoclist");
		} else {
			base.passedStep("Cursor moved to the next document in minidoclist");
		}
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parentWindow1 = reusableDocView.switchTochildWindow();
		reusableDocView.editingCodingFormWithSaveAndNextChild();
		reusableDocView.childWindowToParentWindowSwitching(parentWindow1);
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_HdrMinDoc());
		getDocView_HdrMinDoc().waitAndClick(5);
		for (String miniDocListWindow : driver.getWebDriver().getWindowHandles()) {
			driver.waitForPageToBeReady();
			driver.switchTo().window(miniDocListWindow);
		}
		String childDocID = getVerifyPrincipalDocument().getText().trim();
		softAssertion.assertNotEquals(childDocID, afterEditDocId);
		driver.close();
		driver.switchTo().window(parentWindow1);
	}

	/**
	 * @author Indium-Baskar date: 15/09/2021 Modified date: NA
	 * @Description : this method used to save stamp with save and next
	 */

	public void saveAndNextWithSavedStamp(String fieldValue, String stampColour) {
		driver.waitForPageToBeReady();
		reusableDocView.editCodingFormAndSaveWithStamp(fieldValue, stampColour);
		driver.waitForPageToBeReady();
		for (int i = 3; i <= 3; i++) {
			getClickDocviewID(i).waitAndClick(5);
		}
		driver.waitForPageToBeReady();
		String currentDocId = getVerifyPrincipalDocument().getText().trim();
		base.stepInfo("Document choose for applying stamp :" + currentDocId + "");
		base.waitForElement(getCodingStampLastIcon(stampColour));
		getCodingStampLastIcon(stampColour).waitAndClick(10);
		String getAttribute = getDocument_CommentsTextBox().WaitUntilPresent().GetAttribute("value");
		softAssertion.assertEquals("Saved with stamp", getAttribute);
		base.waitForElement(getSaveAndNextButton());
		getSaveAndNextButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		String afterEditDocId = getVerifyPrincipalDocument().getText().trim();
		softAssertion.assertNotEquals(currentDocId, afterEditDocId);
		if (currentDocId.equals(afterEditDocId)) {
			base.failedStep("Cursor not moved to the next document in minidoclist");
		} else {
			base.passedStep("Cursor moved to the next document in minidoclist");
		}
		String parentWindow1 = driver.getWebDriver().getWindowHandle();
		reusableDocView.clickGearIconOpenMiniDocList();
		Set<String> miniDocListChild = reusableDocView.switchToCodingFormchildWindow();
		for (int i = 5; i <= 5; i++) {
			getClickDocviewID(i).waitAndClick(5);
		}
		driver.waitForPageToBeReady();
		String miniChildDocId = getVerifyPrincipalDocument().getText().trim();
		driver.switchTo().window(parentWindow1);
		base.waitForElement(getDocView_HdrCoddingForm());
		getDocView_HdrCoddingForm().waitAndClick(10);
		Set<String> codingFormChild = reusableDocView.switchToCodingFormchildWindow();
		base.waitForElement(getCodingStampLastIcon(stampColour));
		getCodingStampLastIcon(stampColour).waitAndClick(10);
		String getAttributes = getDocument_CommentsTextBox().WaitUntilPresent().GetAttribute("value");
		softAssertion.assertEquals("Saved with stamp", getAttributes);
		base.waitForElement(getSaveAndNextButton());
		getSaveAndNextButton().waitAndClick(5);
		reusableDocView.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		String afterEditChild = getVerifyPrincipalDocument().getText().trim();
		softAssertion.assertNotEquals(miniChildDocId, afterEditChild);
		if (currentDocId.equals(afterEditDocId)) {
			base.failedStep("Cursor not moved to the next document in minidoclist");
		} else {
			base.passedStep("Cursor moved to the next document in minidoclist");
		}
		List<String> windows = new ArrayList<>(driver.getWebDriver().getWindowHandles());
		for (String s : windows) {
			System.out.println("window: " + s);
		}
		String parentWindow = windows.get(0);
		String miniChildWindow = windows.get(1);
		String codingChildWindow = windows.get(2);
		driver.switchTo().window(miniChildWindow).close();
		driver.switchTo().window(codingChildWindow).close();
		driver.switchTo().window(parentWindow);
		driver.getWebDriver().navigate().refresh();
		driver.waitForPageToBeReady();
		reusableDocView.deleteStampColour(stampColour);

	}

	/**
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @Description: Method for viewing doc view images.
	 */
	public void verifyDocViewImages() throws InterruptedException {
		try {

			for (int i = 0; i < 3; i++) {
				try {
					driver.waitForPageToBeReady();
					base.waitForElement(getImageTab());
					driver.waitForPageToBeReady();
					getImageTab().Click();
					driver.waitForPageToBeReady();
					Actions act = new Actions(driver.getWebDriver());
					base.waitForElement(selectDocInImageTab());
					act.doubleClick(selectDocInImageTab().getWebElement()).perform();
					boolean assertion = getAssertOnImage().Displayed();
					System.out.println(assertion);
					if (getAssertOnImage().Displayed()) {
						base.passedStep("Image is displayed");
						break;
					} else {
						driver.Navigate().refresh();
					}
				} catch (Exception e) {
					driver.Navigate().refresh();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while viewing doc view images" + e.getMessage());
		}
	}

	/**
	 * @author Indium-Baskar date: 10/11/2021 Modified date: NA
	 * @Description : this method used for verify persistent hit panel for disabled
	 *              and enabled conditions for 0 count
	 */
	public void verifyPersistentHitPanelUsingSaceAndNext(String panel) {
		driver.waitForPageToBeReady();
		driver.scrollingToElementofAPage(getHitPanleVerify(panel));
		softAssertion.assertTrue(getHitPanleVerify(panel).Displayed());
		String countPersistentHit = getHitPanleVerify(panel).getText();
		if (getHitPanleVerify(panel).Displayed()) {
			base.stepInfo("persistent hit panel displayed in docview panel");
		} else {
			base.failedStep("Hit panel not displayed");
		}
		reusableDocView.editingCodingFormWithSaveAndNextButton();
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_ToggleButton());
		boolean flag = getDocView_ToggleButton().GetAttribute("data-swchoff-text").contains("No");
		softAssertion.assertTrue(flag);
		base.passedStep("Hide Terms with 0 hits:Disabled");
		if (getHitPanleVerify(panel).Displayed()) {
			base.stepInfo("persistent hit panel displayed in docview panel after performing save and next");
		} else {
			base.failedStep("Hit panel not displayed");
		}
		driver.waitForPageToBeReady();
		base.stepInfo("Enabling 0 hits terms in docview panel");
		getDocView_ToggleButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_ToggleButton());
		boolean docYes = getDocView_ToggleButton().GetAttribute("data-swchoff-text").contains("Yes");
		softAssertion.assertFalse(docYes);
		driver.waitForPageToBeReady();
		base.passedStep("Hide Terms with 0 hits:Enabled");
		boolean miniDis = getHitPanleVerify(panel).Displayed();
		softAssertion.assertFalse(miniDis);
		base.passedStep("0 count terms not displayed in persistent hit panel");
		reusableDocView.editingCodingFormWithSaveAndNextButton();
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_ToggleButton());
		boolean afterSave = getDocView_ToggleButton().GetAttribute("data-swchoff-text").contains("Yes");
		softAssertion.assertFalse(afterSave);
		driver.waitForPageToBeReady();
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		Set<String> codingFormChild = reusableDocView.switchToCodingFormchildWindow();
		reusableDocView.editingCodingFormWithSaveAndNextChild();
		List<String> windows = new ArrayList<>(driver.getWebDriver().getWindowHandles());
		for (String s : windows) {
			System.out.println("window: " + s);
		}
		String parentWindow = windows.get(0);
		String childWindow = windows.get(1);
		driver.switchTo().window(childWindow).close();
		driver.switchTo().window(parentWindow);
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_ToggleButton());
		boolean afterSaveChild = getDocView_ToggleButton().GetAttribute("data-swchoff-text").contains("Yes");
		softAssertion.assertFalse(afterSaveChild);
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium-Baskar date: 10/8/2021 Modified date: 23/8/2021
	 * @Description: Validation of non-date format
	 */
	public void nonDateFormatValidation(String projectFieldName, String fieldValue, String colour)
			throws InterruptedException, AWTException {
		driver.waitForPageToBeReady();
		getReadOnlyTextBox(projectFieldName).WaitUntilPresent().ScrollTo();
//		base.waitForElement(getReadOnlyTextBox(projectFieldName));
		getDateFormat().SendKeys("11/10/2021");
		reusableDocView.stampColourSelection(fieldValue, colour);
		String errorText = getCodingFormValidErrorMeta().getText().trim();
		String actual = "Coding Form validation failed";
		base.stepInfo("Save using stamp on non-date format and verify error message");
		if (errorText.equals(actual)) {
			base.passedStep("Error message Dispalyed");
		} else {
			base.stepInfo("Error message not displayed");
		}
		getCodingStampCancel().waitAndClick(5);
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parentWindow = reusableDocView.switchTochildWindow();
		getDateFormat().SendKeys("11/10/2021");
		base.waitForElement(getCodingFormStampButton());
		getCodingFormStampButton().waitAndClick(10);
		driver.close();
		reusableDocView.switchToNewWindow(1);
		base.waitForElement(getCodingStampTextBox());
		getCodingStampTextBox().SendKeys(fieldValue);
		base.waitForElement(getDrp_StampColour());
		getDrp_StampColour().waitAndClick(10);
		base.waitForElement(getAssignedColour(colour));
		getAssignedColour(colour).waitAndClick(10);
		base.waitForElement(getCodingStampSaveBtn());
		getCodingStampSaveBtn().waitAndClick(10);
		String errorTextChild = getCodingFormValidErrorMeta().getText().trim();
		String actualChild = "Coding Form validation failed";
		base.stepInfo("Save using stamp on non-date format and verify error message");
		if (errorTextChild.equals(actualChild)) {
			base.passedStep("Error message Dispalyed");
		} else {
			base.stepInfo("Error message not displayed");
		}
	}

	/**
	 * @author Indium-Baskar date: 10/8/2021 Modified date: 23/8/2021
	 * @Description: this method is used to check the remove code same as after edit
	 *               without saving the document
	 */
	public void editAndRemoveCodeSameAS() {
		driver.waitForPageToBeReady();
		reusableDocView.clickCheckBoxDocListActionCodeSameAs();
		base.waitForElement(getResponsiveCheked());
		getResponsiveCheked().Click();
		base.waitForElement(getNonPrivilegeRadio());
		getNonPrivilegeRadio().Click();
		for (int i = 1; i <= 3; i++) {
			getDocView_MiniDoc_ChildWindow_Selectdoc(i).waitAndClick(5);
			;
		}
		reusableDocView.removeCodeSameAs();
		base.stepInfo("User can able to remove code same as after edit coding form without saving");
	}

	/**
	 * @author Indium-Baskar date: 10/8/2021 Modified date: 23/8/2021
	 * @Description: this method is used to check static text as per customized
	 *               coding form
	 * 
	 */
	public void staticTextToBeDisplayed(String fieldValue) {
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyStaticText(fieldValue));
		Boolean flag = getVerifyStaticText(fieldValue).Displayed();
		softAssertion.assertTrue(flag);
		if (getVerifyStaticText(fieldValue).Displayed()) {
			base.passedStep("Static text displayed as per customized coding form");
		} else {
			base.failedStep("Not as per customized coding form");
		}
	}

	public void verifyCustomizedMetaDataCodingForm(String fieldValue) throws InterruptedException {
		driver.waitForPageToBeReady();
		base.waitForElement(getCompleteDocBtn());
		getCompleteDocBtn().waitAndClick(5);
		String errorText = getCodingFormValidErrorMeta().getText().trim();
		String actual = "Coding Form validation failed";
		base.stepInfo("validationfor metadata using customized coding form");
		if (errorText.equals(actual)) {
			base.passedStep("Error message Dispalyed");
		} else {
			base.stepInfo("Error message not displayed");
		}

	}

	/**
	 * @author Mohan.Venugopal Created on : 11/11/2021 Modified By: NA Modified On:
	 *         NA
	 * @description: Create New Folder in Analytics Panel
	 * @param folderName
	 */
	public void createFolderforDocsInAnlyticsPanel(String folderName) {

		driver.waitForPageToBeReady();

		base.waitForElement(getDocView_AnalyticsNewFolderThreadMap());
		getDocView_AnalyticsNewFolderThreadMap().waitAndClick(10);

		base.waitForElement(getDocView_AnalyticsNewFolderTree());
		getDocView_AnalyticsNewFolderTree().waitAndClick(10);

		base.waitForElement(getDocView_AnalyticsNewFolderTextBox());
		getDocView_AnalyticsNewFolderTextBox().SendKeys(folderName);

		base.waitForElement(getDocView_AnalyticsNewFolderContiBtn());
		getDocView_AnalyticsNewFolderContiBtn().waitAndClick(10);

		base.waitForElement(getTotalSelectedDocuments());
		base.waitForElement(getDocView_AnalyticsNewFolderFinalizeBtn());
		getDocView_AnalyticsNewFolderFinalizeBtn().waitAndClick(10);

		base.VerifySuccessMessage("Records saved successfully");

		driver.scrollPageToTop();
		try {
			if (getDocView_CurrentDocId().isDisplayed()) {
				String docID = getDocView_CurrentDocId().getText();
				System.out.println(docID);
				base.passedStep("Message is displayed and selected documents are foldered successfully");
			} else
				base.failedStep("Selected docs are not foldered");

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Selected docs are not foldered");
		}

	}

	/*
	 * @author Steffy
	 * 
	 * @Description -- Method to verify whether the keywords along with counts are
	 * displayed
	 * 
	 */
	public void verifyKeywordsAreDisplayed(String[] keywords) throws Exception {
		try {
			if (getHitPanel().Displayed()) {
				base.passedStep("Persistent hit panels are displayed");
				List<String> keywordsText = base.getAvailableListofElements(getHitPanelCollection());
				List<String> actualKeywords = Arrays.asList(keywords);
				for (int i = 0; i < actualKeywords.size(); i++) {
					if (keywordsText.get(i).contains(actualKeywords.get(i))) {
						base.passedStep(actualKeywords.get(i) + " is displayed in persistent panel");
					} else {
						base.failedStep(actualKeywords.get(i) + " is not displayed in persistent panel");
					}
				}
			}
		} catch (Exception e) {
			base.failedStep("Persistent hit panels are not displayed");
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description: To verify the given object coding form name in docview page
	 */
	public void verifyCodingFormNameInDocviewPg(int objectNo, String expectedObjectName) {
		try {
			base.waitForElement(getCodingFormObjectNames(objectNo));
			String actualObjectName = getCodingFormObjectNames(objectNo).getText();
			System.out.println("actual: " + actualObjectName);
			softAssertion.assertEquals(expectedObjectName, actualObjectName);
			base.passedStep("Object added in the coding form is displayed in docview page as expected");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to verify coding form name in docview page");
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description: To verify the given tag coding form name in docview page
	 */
	public void verifyCodingFormTagNameInDocviewPg(int objectNo, String expectedObjectName) {
		try {
			base.waitForElement(getCodingFormTaglabel(objectNo));
			String actualObjectName = getCodingFormTaglabel(objectNo).getText();
			System.out.println("actual: " + actualObjectName);
			softAssertion.assertEquals(expectedObjectName, actualObjectName);
			base.passedStep("Object added in the coding form is displayed in docview page as expected");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to verify coding form tag name in docview page");
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description: To verify the help and error messages of coding form name in
	 *               docview page
	 */
	public void verifyHelpnErrorMsgInDocviewPg(String objectName, String expectedHelpMsg, String expectedErrorMsg) {
		try {
			base.waitForElement(getCodingFormHelpText(objectName));
			String actualHelpMsg = getCodingFormHelpText(objectName).GetAttribute("title");
			String actualErrorMsg = getCodingFormErrorText(objectName).getText();
			softAssertion.assertEquals(expectedHelpMsg, actualHelpMsg);
			softAssertion.assertEquals(expectedErrorMsg, actualErrorMsg);
			base.passedStep("Both help and error messages occured as expected");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to verify coding form help and error messages in docview page");
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description: To verify the metadata help and error messages of coding form
	 *               name in docview page
	 */
	public void verifyMetaDataHelpnErrorMsgInDocviewPg(String objectName, String expectedHelpMsg,
			String expectedErrorMsg) {
		try {
			base.waitForElement(getCodingFormMetaDataHelpText(objectName));
			String actualHelpMsg = getCodingFormMetaDataHelpText(objectName).GetAttribute("title");
			String actualErrorMsg = getCodingFormMetaDataErrorText(objectName).getText();
			softAssertion.assertEquals(expectedHelpMsg, actualHelpMsg);
			softAssertion.assertEquals(expectedErrorMsg, actualErrorMsg);
			base.passedStep("Both help and actual messages occured as expected");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to verify coding form help and error messages in docview page");
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description: To verify the radio and check group of coding form name in
	 *               docview page
	 */
	public void validateRadioOrCheckGroupInDocviewPg(String group) {
		base.waitForElement(getCodingFormRadioOrCheckBox(group));
		if (getCodingFormRadioOrCheckBox(group).isElementPresent() == true) {
			base.passedStep("Expected " + group + " is displayed in docview page");
		} else {
			base.failedStep("Failed to validate radio or check group in docview page");
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description: To verify tag with radio group, help and error messages of
	 *               coding form name in docview page
	 */
	public void verifyHelpnErrorMsgOfRadioGrpTagInDocviewPg(int objectNo, String expectedHelpMsg,
			String expectedErrorMsg) {
		try {
			base.waitForElement(getCodingFormRadioGrpTagNamenHelpText(objectNo));
			String actualHelpMsg = getCodingFormRadioGrpTagNamenHelpText(objectNo).GetAttribute("title");
			String actualErrorMsg = getCodingFormRadioGrpTagErrorlabelText(objectNo).getText();
			softAssertion.assertEquals(expectedHelpMsg, actualHelpMsg);
			softAssertion.assertEquals(expectedErrorMsg, actualErrorMsg);
			base.passedStep("Both help and error messages occured as expected");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to verify coding form help and error messages in docview page");
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description: To verify tag with radio group coding form name in docview page
	 */
	public void verifyCodingFormRadioGrpTagNameInDocviewPg(int objectNo, String expectedObjectName) {
		try {
			base.waitForElement(getCodingFormRadioGrpTagNamenHelpText(objectNo));
			String actualObjectName = getCodingFormRadioGrpTagNamenHelpText(objectNo).getText();
			System.out.println("actual: " + actualObjectName);
			softAssertion.assertEquals(expectedObjectName, actualObjectName);
			base.passedStep("Tag with radio group in the coding form is displayed in docview page as expected");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to verify coding form in docview page");
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description: To verify tag with check group coding form name in docview page
	 */
	public void verifyCodingFormCheckGrpTagNameInDocviewPg(int objectNo, String expectedObjectName) {
		try {
			base.waitForElement(getCodingFormCheckGrpTagNamenHelpText(objectNo));
			String actualObjectName = getCodingFormCheckGrpTagNamenHelpText(objectNo).getText();
			System.out.println("actual: " + actualObjectName);
			softAssertion.assertEquals(expectedObjectName, actualObjectName);
			base.passedStep("Tag with check group in the coding form is displayed in docview page as expected");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to verify coding form in docview page");
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description: To verify tag with check group, help and error messages of
	 *               coding form name in docview page
	 */
	public void verifyHelpnErrorMsgOfCheckGrpTagInDocviewPg(int objectNo, String expectedHelpMsg,
			String expectedErrorMsg) {
		try {
			base.waitForElement(getCodingFormCheckGrpTagNamenHelpText(objectNo));
			String actualHelpMsg = getCodingFormCheckGrpTagNamenHelpText(objectNo).GetAttribute("title");
			String actualErrorMsg = getCodingFormCheckGrpTagErrorlabelText(objectNo).getText();
			softAssertion.assertEquals(expectedHelpMsg, actualHelpMsg);
			softAssertion.assertEquals(expectedErrorMsg, actualErrorMsg);
			base.passedStep("Both help and error messages occured as expected");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to verify coding form in docview page");
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description: To verify tags of coding form name in docview page
	 */
	public void verifyTagsAreDisabled(int objectNo) {
		base.waitForElement(getCodingFormTag(objectNo));
		if (getCodingFormTag(objectNo).isElementPresent() == true) {
			base.passedStep("The added tags are checked and disabled");
		} else {
			base.failedStep("The added tags are not checked and disabled");
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @description: To verify coding form name in docview page
	 */
	public void verifyCodingFormName(String cfName) {
		try {
			base.waitForElement(getDocView_CFName());
			softAssertion.assertEquals(cfName, getDocView_CFName().getText());
			base.passedStep(cfName + " is present");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to verify coding form name in docview page");
		}
	}

	/**
	 * @author Indium-Baskar date: 16/11/2021 Modified date:N/A
	 * @Description: this method is used to Check the document loaded in correct tab
	 * 
	 */

	public void verifyAllTabsForDocument() {
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_TranslationTab());
		getDocView_TranslationTab().waitAndClick(5);
		Boolean flag = getDocView_TranslationTab().Enabled();
		softAssertion.assertTrue(flag);
		System.out.println(flag);
		base.stepInfo("Translation tab is loaded for the document");
		for (int i = 3; i <= 3; i++) {
			getClickDocviewID(i).waitAndClick(5);
		}
		base.waitForElement(getDocView_DefaultViewTab());

		Boolean flag1 = getDocView_DefaultViewTab().Enabled();
		softAssertion.assertTrue(flag1);
		System.out.println(flag1);
		base.passedStep("Document loaded in default view tab");
		base.stepInfo("Verify tabs in Text,Images,Translation when document clicked");
		getDocView_TextTab().waitAndClick(5);
		Boolean flag2 = getDocView_TextTab().Enabled();
		getDocView_ImagesTab().waitAndClick(5);
		Boolean flag3 = getDocView_ImagesTab().Enabled();
		getDocView_TranslationTab().waitAndClick(5);
		Boolean flag4 = getDocView_TranslationTab().Enabled();
		if (flag2 == true && flag3 == true && flag4 == true) {
			base.passedStep("Same document loaded in all tabs");
		} else {
			base.failedMessage("Same document not loaded in all tabs");
		}
	}

	/**
	 * @author Indium-Baskar date: 16/11/2021 Modified date:N/A
	 * @Description: this method is used to Check the principal document
	 * 
	 */

	public void verifyPrincipaDocumentBeFirst() {
		driver.waitForPageToBeReady();
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		base.waitForElement(getDocView_HdrMinDoc());
		getDocView_HdrMinDoc().waitAndClick(5);
		List<String> windows = new ArrayList<>(driver.getWebDriver().getWindowHandles());
		for (String s : windows) {
			System.out.println("window: " + s);
		}
		String parentWindow = windows.get(0);
		String childWindow = windows.get(1);
		String miniDocListChild = windows.get(2);
		String childDocId = "";
		for (int i = 1; i <= 3; i++) {
			driver.switchTo().window(childWindow);
			driver.waitForPageToBeReady();
			getClickDocviewID(i).waitAndClick(5);
			childDocId = getVerifyPrincipalDocument().getText().trim();
			System.out.println(childDocId);
			driver.switchTo().window(miniDocListChild);
			driver.waitForPageToBeReady();
			reusableDocView.editingCodingFormWithCompleteButtonChild();
		}
		driver.switchTo().window(miniDocListChild).close();
		driver.switchTo().window(childWindow).close();
		driver.switchTo().window(parentWindow);
		String DocId = getVerifyPrincipalDocument().getText().trim();
		softAssertion.assertEquals(childDocId, DocId);
		base.stepInfo("After completeing document cursor moved to next document from minidoclist");
		base.passedStep("Principal Document viewed at first");
	}

	/**
	 * @author Indium-Baskar date: 16/11/2021 Modified date:N/A
	 * @Description: this method is used to Check code same as warning msg for not
	 *               performed code same as
	 */

	public void warningMsgForCodeSameAs() {
		driver.waitForPageToBeReady();
		String actual = "Some documents were not in code same as list, they have been ignored.";
		for (int i = 1; i <= 3; i++) {
			getDocView_MiniDoc_ChildWindow_Selectdoc(i).waitAndClick(5);
		}
		reusableDocView.clickCodeSameAs();
		for (int i = 4; i <= 6; i++) {
			getDocView_MiniDoc_ChildWindow_Selectdoc(i).waitAndClick(5);
		}
		base.waitForElement(getDocView_Mini_ActionButton());
		getDocView_Mini_ActionButton().waitAndClick(5);
		base.waitForElement(getDocView__ChildWindow_Mini_RemoveCodeSameAs());
		getDocView__ChildWindow_Mini_RemoveCodeSameAs().waitAndClick(5);
		driver.waitForPageToBeReady();
		String warningMsg = getErrorCodeSameAsIgnored().getText();
		softAssertion.assertEquals(actual, warningMsg);
		base.passedStep("Warning message displayed when document selected for remove code Same As");
	}

	/**
	 * @author Indium-Baskar date: 16/11/2021 Modified date:N/A
	 * @Description: this method is used to Check the metadata value saved value in
	 *               minidoclist
	 * 
	 */
	public void metaDataTextInMiniDocList(String metaData, String fieldValue) {
		driver.waitForPageToBeReady();
		base.waitForElement(getReadOnlyTextBox(metaData));
		getReadOnlyTextBox(metaData).SendKeys(fieldValue);
		base.waitForElement(getCodingFormSaveButton());
		getCodingFormSaveButton().waitAndClick(10);
	}

	/**
	 * @author Indium-Baskar date: 16/11/2021 Modified date:N/A
	 * @Description: this method is used to verify checkmark icon
	 * 
	 */
	public void verifyCheckMarkIcon(String textBox, String colour) {
		driver.waitForPageToBeReady();
		reusableDocView.editCodingFormAndSaveWithStamp(textBox, colour);
		for (int i = 0; i < 3; i++) {
			reusableDocView.lastAppliedStamp(colour);
			driver.waitForPageToBeReady();
		}
		getverifyCodeSameAsLast().ScrollTo();
		base.waitForElement(getverifyCodeSameAsLast());
		if (getverifyCodeSameAsLast().Displayed()) {
			softAssertion.assertTrue(getverifyCodeSameAsLast().getWebElement().isDisplayed());
			base.passedStep("CheckMark is displayed successfully");
		} else {
			base.failedStep("CheckMark is not displayed");
		}
		base.VerifySuccessMessage("Coding Stamp applied successfully");
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium-Baskar date: 16/11/2021 Modified date:N/A
	 * @Description: this method is used to verify error msg whn navigate to other
	 *               page in left tab
	 * 
	 */

	public void verifyOtherPageNavUsingYesAndNoBtn() {
		driver.waitForPageToBeReady();
		for (int i = 1; i <= 3; i++) {
			getDocView_MiniDoc_ChildWindow_Selectdoc(i).waitAndClick(5);
		}
		reusableDocView.clickCodeSameAs();
		base.waitForElement(getDashboardButton());
		getDashboardButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.waitForElement(getPopUpLeftButton());
		Boolean flag = getPopUpLeftButton().Displayed();
		softAssertion.assertTrue(flag);
		base.waitForElement(getNavigationButton("No"));
		base.passedStep("Yes and No button displayed when navigation to other page");
		getNavigationButton("No").waitAndClick(5);
		base.stepInfo("User on docview page after clicking no button");
		getDashboardButton().waitAndClick(5);
		base.waitForElement(getNavigationButton("Yes"));
		getNavigationButton("Yes").waitAndClick(5);
		driver.waitForPageToBeReady();
		if (getDashBoardHomeIcon().Displayed()) {
			base.passedStep("User navigated to other page while clicking the yes button");
		} else {
			base.failedStep("User still in the same page");
		}

	}

	/**
	 * @author Indium-Baskar date: 16/11/2021 Modified date:N/A
	 * @Description: this method is used to verify reload button for refresh
	 * 
	 */

	public void verifyOtherPageNavUsingReloadBtn() {
		driver.waitForPageToBeReady();
		for (int i = 1; i <= 3; i++) {
			getDocView_MiniDoc_ChildWindow_Selectdoc(i).waitAndClick(5);
		}
		reusableDocView.clickCodeSameAs();
		base.waitForElement(getDocument_CommentsTextBox());
		getDocument_CommentsTextBox().SendKeys("Refresh");
		driver.getWebDriver().navigate().refresh();
		driver.switchTo().alert().dismiss();
		base.passedStep("Reload and cancel button displayed while refreshing the page");
		base.stepInfo("User on docview page after clicking cancel button");
		driver.getWebDriver().navigate().refresh();
		driver.switchTo().alert().accept();
		base.waitForElement(getDocument_CommentsTextBox());
		String textCompare = getDocument_CommentsTextBox().GetAttribute("value");
		if (textCompare.equals("Refresh")) {
			base.failedStep("After refreshing the pages values are same");
		} else {
			base.passedStep("After refreshing the page values became unchanged");
			base.stepInfo("User on docview page after clicking Reload button");
		}
		Boolean flag = getReviewMode().Displayed();
		softAssertion.assertTrue(flag);
	}

	/**
	 * @author Indium-Baskar date: 16/11/2021 Modified date:N/A
	 * @Description: this method is used to edit the coding form from saved search
	 * 
	 */

	public String editSavedSearchCodingForm(String fieldValue) {
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String docIdSaved = getVerifyPrincipalDocument().getText();
		base.waitForElement(getDocument_CommentsTextBox());
		getDocument_CommentsTextBox().SendKeys(fieldValue);
		base.waitForElement(getCodingFormSaveButton());
		getCodingFormSaveButton().waitAndClick(5);
		base.stepInfo("Coding form saved for DocId:" + docIdSaved + "from saved search");
		return docIdSaved;
	}

	/**
	 * @author Indium-Baskar date: 16/11/2021 Modified date:N/A
	 * @Description: this method is used to verify coding form from basic search
	 * 
	 */

	public String reVerifyCodingForm(String returnDocId) {
		driver.waitForPageToBeReady();
		base.waitForElement(getDociD(returnDocId));
		getDociD(returnDocId).waitAndClick(5);
		base.stepInfo("Reverify coding form from basic search DocId:" + returnDocId + "");
		base.waitForElement(getDocument_CommentsTextBox());
		String basicText = getDocument_CommentsTextBox().GetAttribute("value");
		return basicText;
	}

	/**
	 * @author Indium-Baskar date: 16/11/2021 Modified date:N/A
	 * @Description: this method is used to verify minidoclist panel
	 * 
	 */

	public void verifyMiniDocListPanel() {
		driver.waitForPageToBeReady();
		base.waitForElementCollection(getMiniDocListDocIdText());
		int count = getMiniDocListDocIdText().size();
		base.stepInfo("Minidoclist loaded document count:" + count + "");
		Boolean flag = getDocView_DefaultViewTab().Enabled();
		softAssertion.assertTrue(flag);
		reusableDocView.defaultHeaderValue();
		scrollingDocumentInMiniDocList();
	}

	/**
	 * @author sowndarya.velraj
	 * @throws InterruptedException This method is created to set the start time and
	 *                              end time for redaction
	 */
	public void enterTimeIntervalRedaction(String starttime, String endtime) {
		base.waitForElement(getAudioStartTime());
		getAudioStartTime().Clear();
		getAudioStartTime().SendKeys(starttime);

		base.waitForElement(getAudioEndTime());
		getAudioEndTime().Clear();
		getAudioEndTime().SendKeys(endtime);
	}

	/**
	 * @author sowndarya.velraj
	 * @throws InterruptedException This method is created to redact a audio file at
	 *                              the beginning,middle and end
	 */
	public void addAudioRedaction() throws InterruptedException {
		Thread.sleep(3000); // waits for page to load
		deleteExistingAudioRedactions();

		driver.scrollPageToTop();
		Thread.sleep(3000); // waits for page to load
		System.out.println("Wait for doc files");
		base.waitForElement(getDocFileTypeCheckbox());
		getDocFileTypeCheckbox().waitAndClick(10);

		Thread.sleep(3000); // waits for page to load
		driver.Navigate().refresh();
		base.waitForElement(getAddRedationBtn());
		getAddRedationBtn().waitAndClick(10);
		enterTimeIntervalRedaction("00:00:00", "00:03:00");
		System.out.println("Enter 1st time");
		getSaveAudioBtn().waitAndClick(10);

		Thread.sleep(3000); // waits for page to load
		driver.Navigate().refresh();
		base.waitForElement(getAddRedationBtn());
		getAddRedationBtn().waitAndClick(10);
		enterTimeIntervalRedaction("00:03:30", "00:06:00");
		System.out.println("Enter 2nd time");
		getSaveAudioBtn().waitAndClick(10);

		Thread.sleep(3000); // waits for page to load
		driver.Navigate().refresh();
		base.waitForElement(getAddRedationBtn());
		getAddRedationBtn().waitAndClick(10);
		enterTimeIntervalRedaction("00:06:30", "00:07:00");
		System.out.println("Enter 3rd time");
		getSaveAudioBtn().waitAndClick(10);
	}

	/**
	 * @author sowndarya.velraj
	 * @throws InterruptedException This method is created to delete existing
	 *                              redactions in an audio file.
	 */
	public void deleteExistingAudioRedactions() {
		Actions act = new Actions(driver.getWebDriver());
		try {
			Thread.sleep(5000); // waits for page to load
			int count = getAudioBytes().size();
			if (count > 0) {
				System.out.println("No of audio redaction to be deleted = " + count + "");
				act.sendKeys(Keys.PAGE_DOWN).build().perform();
				for (int i = 1; i <= count; i++) {
					Thread.sleep(10000); // waits for page to load
					System.out.println("" + i + "");
					base.waitForElement(getDeleteAudioRedaction(1));
					getDeleteAudioRedaction(1).Click();
					Thread.sleep(2000); // waits for page to load
					getDeleteRedactionMsgBox().Click();
					System.out.println("Successfully deleted audio redaction " + i + "");
				}

				act.sendKeys(Keys.PAGE_UP).build().perform();
				int finalcount = getAudioBytes().size();
				if (finalcount == 0) {
					System.out.println("Successfully deleted all existing audio redactions");
				}
			} else {
				System.out.println("No audio redactions are available");
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * @author Mohan 21/10/21 NA Modified date: 18/11/2021 Modified by:Mohan
	 * @description to select docs and remove code as same for concept tab
	 */
	public void selectDocsFromConceptualAndRemoveCodeAsSameForConceptTab() {

		driver.waitForPageToBeReady();
		try {
			driver.waitForPageToBeReady();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
//			getDocView_Analytics_liDocumentConceptualSimilarab().ScrollTo();
			getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);

			for (int i = 2; i <= 3; i++) {
				base.waitForElement(getDocView_Analytics_Concept_DocCheckBox(i));
				getDocView_Analytics_Concept_DocCheckBox(i).waitAndClick(10);
			}
			base.waitForElement(getDocView_ChildWindow_ActionButton());
			getDocView_ChildWindow_ActionButton().waitAndClick(10);

			base.waitForElement(getDocView_Analytics_Concept_RemoveCodeSameAs());
			getDocView_Analytics_Concept_RemoveCodeSameAs().waitAndClick(10);

			base.VerifySuccessMessage("Code Same has been successfully removed");

			try {
				if (getDocView_Analytics_Conceputal_ArrowMark().isDisplayed()) {
					base.failedStep("ArrowDown icon is displayed for the selected docs ");
				} else {
					base.passedStep("ArrownDown icon is not displayed for the selected docs");
				}
			} catch (Exception e) {
				base.passedStep("ArrownDown icon is not displayed for the selected docs");
				UtilityLog.info("Verification failed due to " + e.getMessage());
			}

		} catch (Exception e) {
			base.failedStep("Remove CodeAs Same icon is displayed for the selected docs");
			UtilityLog.info("Verification failed due to " + e.getMessage());
		}
	}

	// Added by jeevitha

	public void verifyPanel() {
		if (getDocView_RedactIcon().isElementPresent()) {
			getDocView_RedactIcon().waitAndClick(10);
			if (getDocView_RedactionPanel().isElementPresent()) {
				base.stepInfo("Redaction Panel Is diplayed");
				softAssertion.assertAll();
			}
		} else {
			base.stepInfo("Redaction Icon is not present and Redaction Panel Is not Displayed");
		}

	}

	/**
	 * @author Indium-Baskar date: 23/11/2021 Modified date: 23/11/2021
	 * @Description:This used for applying stamp for saved document
	 * 
	 */

	public void codingStampForSavedDocument(String textBox, String colour) {
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String docId = getVerifyPrincipalDocument().getText();
		reusableDocView.editingCodingFormWithSaveButton();
		reusableDocView.editCodingFormAndSaveWithStamp(textBox, colour);
		base.waitForElement(getDociD(docId));
		getDociD(docId).waitAndClick(5);
		driver.waitForPageToBeReady();
		reusableDocView.lastAppliedStamp(colour);
		driver.waitForPageToBeReady();
		getMiniDocListRightArrow().ScrollTo();
		Boolean flag = getMiniDocListRightArrow().Displayed();
		softAssertion.assertTrue(true);
		base.passedStep("CheckMark icon displayed in minidoclist for stamp applied document");
		base.waitForElement(getVerifyPrincipalDocument());
		String navDocId = getVerifyPrincipalDocument().getText();
		if (!docId.equals(navDocId)) {
			base.passedStep("Cursor navigated to next document in minidoclist");
		} else {
			base.failedStep("Cursor not navigated to next document in minidoclist page");
		}
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium-Baskar date: 23/11/2021 Modified date: 23/11/2021
	 * @Description:This used for applying stamp in same coding form
	 * 
	 */

	public String stampForSameCodingForm(String textBox, String colour) {
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String docId = getVerifyPrincipalDocument().getText();
		reusableDocView.editingCodingFormWithSaveButton();
		reusableDocView.editCodingFormAndSaveWithStamp(textBox, colour);
		base.waitForElement(getDociD(docId));
		getDociD(docId).waitAndClick(5);
		driver.waitForPageToBeReady();
		reusableDocView.lastAppliedStamp(colour);
		driver.waitForPageToBeReady();
		return docId;
	}

	/**
	 * @author Indium-Baskar date: 23/11/2021 Modified date: 23/11/2021
	 * @Description:This method used for reverifying the document for stamp applied
	 * 
	 */

	public void stampReVerificationFromSG(String docId) {
		driver.waitForPageToBeReady();
		driver.scrollingToElementofAPage(getDociD(docId));
		base.waitForElement(getDociD(docId));
		getDociD(docId).waitAndClick(5);
		driver.waitForPageToBeReady();
		base.waitForElement(getDocument_CommentsTextBox());
		String stampText = getDocument_CommentsTextBox().GetAttribute("value");
		if (stampText.equals("Saving with Stamp")) {
			base.stepInfo("Search done via outside of an assignment");
			base.passedStep("Coding form value applied successfully to SG from Assignment for same coding form");
		} else if (!stampText.equals("Saving with Stamp")) {
			base.stepInfo("Search done via outside of an assignment");
			base.passedStep("Coding form values are not same when different coding form is applied");
		} else {
			base.failedStep("Not as per expected coding form");
		}
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium-Baskar date: 23/11/2021 Modified date: 23/11/2021
	 * @Description:This method used for reverifying the document for stamp applied
	 *                   In different coding form
	 * 
	 */

	public void differentCoding(String docId) {
		driver.waitForPageToBeReady();
		driver.scrollingToElementofAPage(getDociD(docId));
		base.waitForElement(getDociD(docId));
		getDociD(docId).waitAndClick(5);
		driver.waitForPageToBeReady();
		if (getDocument_CommentsTextBox().isElementPresent() == false) {
			base.stepInfo("Search done via outside of an assignment");
			base.passedStep("Coding form values are not same when different coding form is applied");

		} else {
			base.failedStep("Not as per expected coding form");
		}
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Mohan
	 * @Description : this method used for to select docs form mini docs and verify
	 *              the docs present in analytics panel
	 */
	public void selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanel(String analyticsPanelName)
			throws InterruptedException {
		driver.waitForPageToBeReady();
		for (int i = 1; i <= getDocView_MiniDocList_Docs().size(); i++) {
			driver.waitForPageToBeReady();
			getDocView_Select_MiniDocList_Docs(i).waitAndClick(10);
			driver.getPageSource();
			switch (analyticsPanelName) {
			case "NearDupe":
				if (analyticsPanelName.equalsIgnoreCase("NearDupe")) {
					base.waitForElement(getDocView_Analytics_NearDupeTab());
					getDocView_Analytics_NearDupeTab().waitAndClick(10);
					System.out.println(getDocView_Analytics_NearDupes_Docs().size());
					if (getDocView_Analytics_NearDupes_Docs().size() >= 1) {
						System.out.println("NearDupe");

						return;
					}

				}
			case "ThreadMap":
				if (analyticsPanelName.equalsIgnoreCase("ThreadMap")) {
					base.waitForElement(getDocView_Analytics_liDocumentThreadMap());
					getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);
					System.out.println(getDocView_Analytics_ThreadMap_Docs().size());
					if (getDocView_Analytics_ThreadMap_Docs().size() > 3) {
						System.out.println("ThreadMap");
						return;
					}
				}
			case "FamilyMember":
				if (analyticsPanelName.equalsIgnoreCase("FamilyMember")) {
					base.waitForElement(getDocView_Analytics_FamilyTab());
					getDocView_Analytics_FamilyTab().waitAndClick(10);
					if (getDocView_Analytics_FamilyMember_Docs().size() > 2) {
						System.out.println("Family Member");
						return;
					}
				}
			case "Conceptually":
				if (analyticsPanelName.equalsIgnoreCase("Conceptually")) {
					base.waitForElement(getDocView_Analytics_liDocumentConceptualSimilarab());
					getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);
					System.out.println(getDocView_Analytics_Concept_Docs().size());
					if (getDocView_Analytics_Concept_Docs().size() > 2) {
						System.out.println("Conceptually");
						return;
					}
				}

			}
		}

		softAssertion.assertAll();
	}

	/**
	 * @author Mohan
	 * @Description : this method used for to select docs form mini docs and verify
	 *              the docs present in analytics panel
	 */
	public void selectDocsFromMiniDocsListAndCheckTheDocsInAnalyticsPanelForChildWindow(String analyticsPanelName,
			String parentWindowId) throws InterruptedException {
		driver.waitForPageToBeReady();
		for (int i = 1; i <= getDocView_MiniDocList_Docs().size(); i++) {
			driver.waitForPageToBeReady();
			base.waitForElement(getDocView_Select_MiniDocList_Docs(i));
			getDocView_Select_MiniDocList_Docs(i).waitAndClick(10);

			driver.switchTo().window(parentWindowId);

			switch (analyticsPanelName) {
			case "NearDupe":
				if (analyticsPanelName.equalsIgnoreCase("NearDupe")) {
					base.waitForElement(getDocView_Analytics_NearDupeTab());
					getDocView_Analytics_NearDupeTab().waitAndClick(10);
					System.out.println(getDocView_Analytics_NearDupes_Docs().size());
					driver.waitForPageToBeReady();
					if (getDocView_Analytics_NearDupes_Docs().size() >= 1) {
						System.out.println("NearDupe");

						Set<String> allWindowsId1 = driver.getWebDriver().getWindowHandles();
						for (String eachId : allWindowsId1) {
							if (!parentWindowId.equals(eachId)) {
								driver.switchTo().window(eachId);
							}
						}

						return;
					}

				}
			case "ThreadMap":
				if (analyticsPanelName.equalsIgnoreCase("ThreadMap")) {
					base.waitForElement(getDocView_Analytics_liDocumentThreadMap());
					getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);
					driver.waitForPageToBeReady();
					System.out.println(getDocView_Analytics_ThreadMap_Docs().size());
					if (getDocView_Analytics_ThreadMap_Docs().size() > 3) {
						System.out.println("ThreadMap");
						Set<String> allWindowsId1 = driver.getWebDriver().getWindowHandles();
						for (String eachId : allWindowsId1) {
							if (!parentWindowId.equals(eachId)) {
								driver.switchTo().window(eachId);
							}
						}
						return;
					}

				}
			case "FamilyMember":
				if (analyticsPanelName.equalsIgnoreCase("FamilyMember")) {
					base.waitForElement(getDocView_Analytics_FamilyTab());
					getDocView_Analytics_FamilyTab().waitAndClick(10);
					driver.waitForPageToBeReady();
					if (getDocView_Analytics_FamilyMember_Docs().size() > 2) {
						System.out.println("Family Member");
						Set<String> allWindowsId1 = driver.getWebDriver().getWindowHandles();
						for (String eachId : allWindowsId1) {
							if (!parentWindowId.equals(eachId)) {
								driver.switchTo().window(eachId);
							}
						}
						return;
					}

				}
			case "Conceptually":
				if (analyticsPanelName.equalsIgnoreCase("Conceptually")) {
					base.waitForElement(getDocView_Analytics_liDocumentConceptualSimilarab());
					getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);
					driver.waitForPageToBeReady();
					System.out.println(getDocView_Analytics_Concept_Docs().size());
					if (getDocView_Analytics_Concept_Docs().size() > 2) {
						System.out.println("Conceptually");
						Set<String> allWindowsId1 = driver.getWebDriver().getWindowHandles();
						for (String eachId : allWindowsId1) {
							if (!parentWindowId.equals(eachId)) {
								driver.switchTo().window(eachId);
							}
						}
						return;
					}
				}

			}

		}

		softAssertion.assertAll();
	}

	/**
	 * @author Mohan 18/11/21 NA Modified date: NA Modified by:NA
	 * @description To verify Complete check mark for thread map tab
	 */
	public void verifyCompletedCheckMarkForThreadMapTabDocs(int rowNo) {

		driver.waitForPageToBeReady();
		JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
		driver.waitForPageToBeReady();
		Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
		je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
		getDocView_Analytics_liDocumentThreadMap().ScrollTo();
		getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);
		base.waitForElement(getCodeCompleteIconThreadTab());

		try {
			driver.waitForPageToBeReady();
			geDocView_ThreadMap_CheckMarkIcon(rowNo).ScrollTo();
			if (geDocView_ThreadMap_CheckMarkIcon(rowNo).isElementPresent())
				base.passedStep("Completed CheckMark is displayed under thread map tab Successfully");
		} catch (Exception e) {
			base.failedStep("Complete checkmark Icon is not displayed under thread map tab");
		}

	}

	/**
	 * @author Steffy 24/11/21 NA Modified date: NA Modified by:NA
	 * @description Method to scroll and verify laoding text while scrolling
	 */
	public void scrollUntilloadingTextDisplay(Boolean loadingFlag) throws InterruptedException {
		driver.waitForPageToBeReady();
		List<WebElement> scrollTillLast = getDocumetCountMiniDocList().FindWebElements();
		for (int j = 0; j < scrollTillLast.size(); j++) {
			if (j == 15) {
				JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();
				jse.executeScript("document.querySelector('.dataTables_scrollBody').scrollBy(0,4000)");
				if (loadingFlag)
					softAssertion.assertEquals(getDocumetListLoading().Displayed(), true);
				driver.waitForPageToBeReady();
			} else {
				UtilityLog.info("Scroll is not required, Document is displayed");
			}
		}
	}

	/**
	 * @author Indium-Baskar date: 24/11/2021 Modified date: 24/11/2021
	 * @Description:This method used to pass alpha character
	 * 
	 */

	public void passingAlphaIntergerMetaData(String alpha, String tiny, String small, String average, String bit,
			String huge) {
		driver.waitForPageToBeReady();
		base.waitForElement(getReadOnlyTextBox(tiny));
		getReadOnlyTextBox(tiny).SendKeys(alpha);
		base.waitForElement(getReadOnlyTextBox(small));
		getReadOnlyTextBox(small).SendKeys(alpha);
		base.waitForElement(getReadOnlyTextBox(average));
		getReadOnlyTextBox(average).SendKeys(alpha);
		base.waitForElement(getReadOnlyTextBox(bit));
		getReadOnlyTextBox(bit).SendKeys(alpha);
		base.waitForElement(getReadOnlyTextBox(huge));
		getReadOnlyTextBox(huge).SendKeys(alpha);
	}

	/**
	 * @author Indium-Baskar date: 24/11/2021 Modified date: 24/11/2021
	 * @Description:This method used to pass interger value outside the range
	 * 
	 */
	public void passingOutsideRangeIntergerMetaData(String tiny, String small, String average, String bit,
			String huge) {
		driver.waitForPageToBeReady();
		driver.waitForPageToBeReady();
		base.waitForElement(getReadOnlyTextBox(tiny));
		getReadOnlyTextBox(tiny).SendKeys("999");
		base.waitForElement(getReadOnlyTextBox(small));
		getReadOnlyTextBox(small).SendKeys("32,7679");
		base.waitForElement(getReadOnlyTextBox(average));
		getReadOnlyTextBox(average).SendKeys("2,147,483,64734");
		base.waitForElement(getReadOnlyTextBox(bit));
		getReadOnlyTextBox(bit).SendKeys("2^64-1");
		base.waitForElement(getReadOnlyTextBox(huge));
		getReadOnlyTextBox(huge).SendKeys("9999999999999999999999999999999");
	}

	/**
	 * @author Indium-Baskar date: 24/11/2021 Modified date: 24/11/2021
	 * @Description:This method used to verify both alpha and integer value
	 * 
	 */

	public void verifyingIntergerMetaData(String alpha, String stamp, String colour, String tiny, String small,
			String average, String bit, String huge) {
		base.stepInfo("Performing from parent window");
		passingAlphaIntergerMetaData(alpha, tiny, small, average, bit, huge);
		reusableDocView.stampColourSelection(stamp, colour);
		getCodingStampCancel().waitAndClick(5);
		if (getCodingFormValidErrorMeta().Displayed()) {
			base.stepInfo("Alpha and numeric character are not entered in integer metadata field");
			base.passedStep("Validation message displayed for when alpha and numeric entered");
		} else {
			base.stepInfo("Alpha and numeric value entering in interger metadata fields");
		}
		passingOutsideRangeIntergerMetaData(tiny, small, average, bit, huge);
		reusableDocView.stampColourSelection(stamp, colour);
		getCodingStampCancel().waitAndClick(5);
		if (getCodingFormValidErrorMeta().Displayed()) {
			base.passedStep("Validation message displayed for when values entered in outside of range ");
		} else {
			base.stepInfo("Validation message not displayed");
		}
		base.stepInfo("Performing from child window");
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		reusableDocView.switchTochildWindow();
		passingAlphaIntergerMetaData(alpha, tiny, small, average, bit, huge);
		getCodingFormStampButton().waitAndClick(5);
		reusableDocView.switchToNewWindow(1);
		reusableDocView.stampColourInparentWindow(stamp, colour);
		getCodingStampCancel().waitAndClick(5);
		driver.waitForPageToBeReady();
		if (getCodingFormValidErrorMeta().Displayed()) {
			base.stepInfo("Alpha and numeric character are not entered in integer metadata field");
			base.passedStep("Validation message displayed for when alpha and numeric entered");
		} else {
			base.stepInfo("Alpha and numeric value entering in interger metadata fields");
		}
		List<String> windows = new ArrayList<>(driver.getWebDriver().getWindowHandles());
		for (String s : windows) {
			System.out.println("window: " + s);
		}
		String parentWindow = windows.get(0);
		String codingChildWindow = windows.get(1);
		reusableDocView.switchToNewWindow(2);
		passingOutsideRangeIntergerMetaData(tiny, small, average, bit, huge);
		getCodingFormStampButton().waitAndClick(5);
		reusableDocView.switchToNewWindow(1);
		reusableDocView.stampColourInparentWindow(stamp, colour);
		getCodingStampCancel().waitAndClick(5);
		driver.switchTo().window(codingChildWindow).close();
		driver.switchTo().window(parentWindow);
		if (getCodingFormValidErrorMeta().Displayed()) {
			base.passedStep("Validation message displayed for when values entered in outside of range ");
		} else {
			base.stepInfo("Validation message not displayed");
		}
	}

	/**
	 * @author Mohan 24/11/21 NA Modified date: NA Modified by:NA
	 * @description To verify thread docs display documents which has all the some
	 *              details
	 */
	public void verifyThreadDocsDisplayDocsInToolTips(String text) {
		try {
			driver.waitForPageToBeReady();
			List<WebElement> labelValues = getDocView_Analytics_ThreadMap_ToolTipText(text).FindWebElements();
			for (int i = 0; i < 1; i++) {
				String labelValue = labelValues.get(i).getAttribute("innerText");
				if (labelValue.contains(text)) {
					base.passedStep("Thread Docs contains " + labelValue + " when mouse hover to the document");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Threaded Documents does not loaded properly");
		}
	}

	/**
	 * @author Vijaya Rani 24/11/21 NA Modified date: NA Modified by:NA
	 * @description To verify thread docs mre data link and size thread
	 */
	public void selectDocsFromMiniDocsListAndCheckTheThreadedDocuments() throws InterruptedException {
		driver.waitForPageToBeReady();
		getDocView_Analytics_LoadMoreButton().Displayed();
		base.passedStep("LeadMoreButton is Displayed Successfully");
		base.waitForElementCollection(getDocView_Analytics_ThreadedDocs());

		base.passedStep("The Threaed Documents having :" + getDocView_Analytics_ThreadedDocs().size()
				+ "which is more than 20 docs");

	}

	/**
	 * @author Indium-Baskar date: 25/11/2021 Modified date:N/A
	 * @Description: this method is used to scroll less documents
	 * 
	 */
	public void verifyScrollingDocumentLess() {
		driver.waitForPageToBeReady();
		reusableDocView.clickGearIconOpenMiniDocList();
		String parentWindow = reusableDocView.switchTochildWindow();
		driver.waitForPageToBeReady();
		for (int i = 5; i <= 5; i++) {
			base.waitForElement(getClickDocviewID(i));
			getClickDocviewID(i).waitAndClick(5);
		}
		base.stepInfo("Document selected from minidoclist");
		reusableDocView.scrollDownWithLessDocument();
		reusableDocView.childWindowToParentWindowSwitching(parentWindow);
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium-Baskar date: 25/11/2021 Modified date:N/A
	 * @Description: this method is used to Check the document loaded in correct tab
	 * 
	 */

	public void verifyTextTabsForDocument() {
		driver.waitForPageToBeReady();
		reusableDocView.docviewTextTab();
		base.stepInfo("Document displayed in text tab");
		for (int i = 3; i <= 3; i++) {
			getClickDocviewID(i).waitAndClick(5);
		}
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_DefaultViewTab());
		Boolean flag1 = getDocView_DefaultViewTab().Enabled();
		softAssertion.assertTrue(flag1);
		base.passedStep("Document loaded in default view tab when other doc clicked");
		base.stepInfo("Verify tabs in Text,Images,Translation when document clicked");
		reusableDocView.verifyDocsNavigateToAllTabs();
	}

	/**
	 * @author Mohan 25/11/21 NA Modified date: NA Modified by:NA
	 * @description To Select documents from conceptual which are not marked as code
	 *              same and action as 'Remove code same'
	 */
	public void selectConceptualDocsWhichAreNotPerformedCodeAsSame() {

		driver.waitForPageToBeReady();
		JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
		driver.waitForPageToBeReady();
		Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
		je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
//		getDocView_Analytics_liDocumentConceptualSimilarab().ScrollTo();
		getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);

		driver.getPageSource();
		base.waitForElement(getDocView_Analytics_Concept_DocCheckBox(1));
		getDocView_Analytics_Concept_DocCheckBox(1).waitAndClick(10);

		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(10);

		base.waitForElement(getDocView_Analytics_Concept_RemoveCodeSameAs());
		getDocView_Analytics_Concept_RemoveCodeSameAs().waitAndClick(10);

		base.VerifyWarningMessage("Some documents were not in code same as list, they have been ignored.");
		base.passedStep("Warning message is displayed successfully");

	}

	/**
	 * @author Mohan 25/11/21 NA Modified date: NA Modified by:NA
	 * @description To Select documents from Family which are not marked as code
	 *              same and action as 'Remove code same'
	 */
	public void selectFamilyMemberDocsWhichAreNotPerformedCodeAsSame() {

		driver.waitForPageToBeReady();
		JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
		driver.waitForPageToBeReady();
		Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
		je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
//		getDocView_Analytics_FamilyTab().ScrollTo();
		getDocView_Analytics_FamilyTab().waitAndClick(10);
		driver.getPageSource();

		base.waitForElement(getDocView_Analytics_FamilyMember_DocCheckBox(3));
		driver.getPageSource();
		getDocView_Analytics_FamilyMember_DocCheckBox(3).waitAndClick(10);

		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(10);

		base.waitForElement(getDocView_FamilyRemoveCodeSameAs());
		getDocView_FamilyRemoveCodeSameAs().waitAndClick(10);

		base.VerifyWarningMessage("Some documents were not in code same as list, they have been ignored.");
		base.passedStep("Warning message is displayed successfully");

	}

	/**
	 * @Author Mohan Created on 26/11/2021
	 * @Description To select docs from Analytics Thread Map Tab and Action as
	 *              Remove Code Same
	 * 
	 */
	public void selectDocsFromThreadMapTabAndActionRemoveCodeSameAs() {

		try {
			driver.waitForPageToBeReady();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			getDocView_Analytics_liDocumentThreadMap().ScrollTo();

			for (int i = 2; i <= 3; i++) {
				base.waitForElement(getDocView_Analytics_ThreadMap_DocCheckBox(i));
				getDocView_Analytics_ThreadMap_DocCheckBox(i).waitAndClick(10);
			}
			base.waitForElement(getDocView_ChildWindow_ActionButton());
			getDocView_ChildWindow_ActionButton().waitAndClick(10);

			base.waitForElement(getDocView_Analytics_Thread_RemoveCodeSameAs());
			getDocView_Analytics_Thread_RemoveCodeSameAs().waitAndClick(10);

			base.VerifySuccessMessage("Code Same has been successfully removed");

			// verify Code Same as Link
			try {
				if (getDocView_Analytics_FamilyMember_CodeSameLink().getWebElement().isDisplayed()) {
					softAssertion.assertTrue(getDocView_Analytics_FamilyMember_CodeSameLink().Displayed());
					base.failedStep("Selected Document is having code as same icon under Family Member tabss");
				}
			} catch (Exception e) {
				base.passedStep(
						"Code same icon is not displayed for the selected documents and documents from Family Member are unchecked successfull");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Code Same As Icon is not displayed");
		}
	}

	/**
	 * @author Mohan 26/11/21 NA Modified date: NA Modified by:NA
	 * @description to select docs and CodeSameAs
	 */
	public void selectDocsFromMiniDocsAndCodeSameAs() {

		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		try {
			for (int i = 1; i <= 2; i++) {
				base.waitForElement(getDocView_MiniDoc_SelectRow(i));
				getDocView_MiniDoc_SelectRow(i).waitAndClick(10);
			}

			base.waitForElement(getDocView_Mini_ActionButton());
			getDocView_Mini_ActionButton().waitAndClick(10);

			base.waitForElement(getDocView__ChildWindow_Mini_CodeSameAs());
			getDocView__ChildWindow_Mini_CodeSameAs().waitAndClick(10);

			base.VerifySuccessMessage("Code same performed successfully.");

			try {
				if (geDocView_MiniList_CodeSameAsIcon().Displayed()) {
					base.passedStep("CodeSameAs icon is displayed for the selected docs ");
				}
			} catch (Exception e) {
				base.failedStep("CodeSameAs icon is not displayed for the selected docs");
				UtilityLog.info("Verification failed due to " + e.getMessage());
			}

		} catch (Exception e) {
			base.failedStep("CodeSameAs icon is not performed for the selected docs");
			UtilityLog.info("Verification failed due to " + e.getMessage());
		}
	}

	/**
	 * @author Mohan 26/11/21 NA Modified date: NA Modified by:NA
	 * @description to verify LoadMore link is present When Navigated to other tab
	 */
	public void verifyLoadMoreLinkNaviagtedToOtherTab() {

		driver.waitForPageToBeReady();
		if (getDocView_Analytics_LoadMoreButton().Displayed()) {
			base.failedStep("Load more link is visible");

		} else {
			base.passedStep("LoadMore Link is not visible when navigate to other Tab");

		}

	}

	/**
	 * @author VijayaRani 26/11/21 NA Modified date: NA Modified by:NA
	 * @description to verify ZoomIn/ZoomOut functions in Neardupe comparision
	 */
	public void verifyChildWindowZoominZoomOutNearDupeDocs() {
		driver.waitForPageToBeReady();
		for (int i = 0; i < 20; i++) {
			try {
				base.waitForElement(getDocView_Analytics_NearDupe_NearDupeView_ZoomIn());
				base.waitTillElemetToBeClickable(getDocView_Analytics_NearDupe_NearDupeView_ZoomIn());
				getDocView_Analytics_NearDupe_NearDupeView_ZoomIn().waitAndClick(7);

				base.waitForElement(getDocView_Analytics_NearDupe_NearDupeView_ZoomOut());
				base.waitTillElemetToBeClickable(getDocView_Analytics_NearDupe_NearDupeView_ZoomOut());
				getDocView_Analytics_NearDupe_NearDupeView_ZoomOut().waitAndClick(7);

				break;
			} catch (Exception e) {
				driver.Navigate().refresh();
			}
		}
	}

	/**
	 * @author Indium-Baskar date: 26/11/2021 Modified date:N/A
	 * @Description: This method used verify after refresh code same as last to
	 *               disbale
	 */

	public void verifyCodeSameAsLastDisable() {
		reusableDocView.editingCodingFormWithSaveButton();
		reusableDocView.clickCodeSameAsLast();
		driver.getWebDriver().navigate().refresh();
		driver.waitForPageToBeReady();
		reusableDocView.codeSameAsLastDisable();
	}

	/**
	 * @author Indium-Baskar date: 26/11/2021 Modified date:N/A
	 * @Description: This method used verify warning in static using save
	 * 
	 */
	public void warningMessageStaticUsingSave() {
		driver.waitForPageToBeReady();
		base.waitForElement(getCodingFormSaveButton());
		getCodingFormSaveButton().waitAndClick(15);
		driver.waitForPageToBeReady();
		boolean flag = getStaticWarningError().Displayed();
		softAssertion.assertTrue(flag);
		base.passedStep("Warning message displayed while saving coding using static text");
		reusableDocView.codeSameAsLastDisable();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description: This method used verify navigation confirmation popup buttons
	 * 
	 */
	public void verifyNavigationPopUpButtons() {
		driver.waitForPageToBeReady();
		if (getNavigationMsgPopupYesBtn().Displayed() == true) {
			base.passedStep("Navigation popup yes button is dsiplayed");
		} else {
			base.failedStep("Navigation popup yes button is not dsiplayed");
		}
		if (getNavigationMsgPopupNoBtn().Displayed() == true) {
			base.passedStep("Navigation popup no button is dsiplayed");
		} else {
			base.failedStep("Navigation popup no button is not dsiplayed");
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method for navigating to doc view page.
	 */
	public void navigateToDocViewPageURL() {
		try {
			driver.getWebDriver().get(Input.url + "DocumentViewer/DocView");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while navigating to doc view page is failed" + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method for adding remark to current selected document.
	 * @param remark : remark is String value that any remark value need to enter in
	 *               edit box.
	 */
	public void addRemarkToNonAudioDocument(int off1, int off2, String remark) {
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getNonAudioRemarkBtn().isElementAvailable(10);
				}
			}), Input.wait60);
			getNonAudioRemarkBtn().waitAndClick(9);

			if (getDocView_Remark_DeleteIcon().isElementAvailable(2)) {
				getDocView_Remark_DeleteIcon().waitAndClick(10);
				base.getPopupYesBtn().waitAndClick(5);
			} else {
				System.out.println("Remark not present");
			}
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSelectRemarkDocArea().isElementAvailable(10);
				}
			}), Input.wait30);
			Thread.sleep(Input.wait30 / 10);
			System.out.println(off1 + "...." + off2);
			Actions actions = new Actions(driver.getWebDriver());
			driver.waitForPageToBeReady();
			WebElement text = getSelectedAreaElement().getWebElement();
			actions.moveToElement(text, off1, off2).clickAndHold().moveByOffset(200, 220).release().perform();
			driver.scrollPageToTop();
			getAddRemarkbtn().getWebElement().click();
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getRemarkTextArea().isElementAvailable(10);
				}
			}), Input.wait30);
			getRemarkTextArea().SendKeys(remark);
			getSaveRemark().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while adding remark to current selected document" + e.getMessage());
		}

	}

	/**
	 * @author Gopinath
	 * @Description : Method for adding remark is added to document.
	 * @param remark : remark is String value that any remark value need to enter in
	 *               edit box.
	 */
	public void verifyRemarkIsAdded(String remark) {
		try {
			driver.Navigate().refresh();
			driver.waitForPageToBeReady();
			String panelItemValue = null;
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getNonAudioRemarkBtn().isElementAvailable(10);
				}
			}), Input.wait60);
			getNonAudioRemarkBtn().waitAndClick(10);
			driver.waitForPageToBeReady();
			List<WebElement> remarkPanelItems = getRemarkPanelItems().FindWebElements();
			for (WebElement remarkPanelItem : remarkPanelItems) {
				driver.waitForPageToBeReady();
				panelItemValue = remarkPanelItem.getText().trim();
				if (panelItemValue.equalsIgnoreCase(remark)) {
					base.passedStep("Remark is added to document successfully");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while adding remark to current selected document" + e.getMessage());
		}

	}

	/**
	 * @author Gopinath
	 * @Description : Method for editing added remark of document.
	 * @param remark : remark is String value that any remark value need to enter in
	 *               edit box.
	 */
	public void editRemark(String remark) {
		try {
			driver.Navigate().refresh();
			driver.waitForPageToBeReady();
			String panelItemValue = null;
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getNonAudioRemarkBtn().isElementAvailable(10);
				}
			}), Input.wait60);
			getNonAudioRemarkBtn().waitAndClick(9);
			driver.waitForPageToBeReady();
			List<WebElement> remarkPanelItems = getRemarkPanelItems().FindWebElements();
			List<WebElement> gettrashBasketsofRemarks = getPencilsofRemarks().FindWebElements();
			for (int i = 0; i < remarkPanelItems.size(); i++) {
				driver.waitForPageToBeReady();
				panelItemValue = remarkPanelItems.get(i).getText().trim();
				if (panelItemValue.equalsIgnoreCase(remark)) {
					driver.waitForPageToBeReady();
					gettrashBasketsofRemarks.get(i).click();
					getEditTextField().SendKeys(remark);
					driver.waitForPageToBeReady();
					base.waitForElement(getSaveRemark());
					getSaveRemark().Click();
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while editing added remark of document." + e.getMessage());
		}

	}

	/**
	 * @author Indium-Baskar date: 29/11/2021 Modified date:N/A
	 * @Description: This method used to verify Metadata textbox
	 * 
	 */
	public void verifyMetaDataTab(String metaDataBox, String fieldtext) {
		base.stepInfo("Performing in parent window");
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String docOne = getVerifyPrincipalDocument().getText();
		reusableDocView.editableMetaDataValidation(metaDataBox, fieldtext);
		driver.waitForPageToBeReady();
		String docTwo = getVerifyPrincipalDocument().getText();
		if (docOne != docTwo) {
			base.passedStep("After saving the metadata value  cursor navigated to next document");
		} else {
			base.failedStep("Cursor not navigated to next document");
		}
		base.stepInfo("Performing in child window");
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		base.waitForElement(getDocView_HdrMinDoc());
		getDocView_HdrMinDoc().waitAndClick(5);
		driver.waitForPageToBeReady();
		reusableDocView.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String docThree = getVerifyPrincipalDocument().getText();
		reusableDocView.switchToNewWindow(3);
		reusableDocView.editableMetaDataValidation(metaDataBox, fieldtext);
		driver.waitForPageToBeReady();
		reusableDocView.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String docFour = getVerifyPrincipalDocument().getText();
		if (docThree != docFour) {
			base.passedStep("After saving the metadata value  cursor navigated to next document");
		} else {
			base.failedStep("Cursor not navigated to next document");
		}
		reusableDocView.closeWindow(2);
		reusableDocView.closeWindow(1);
		reusableDocView.switchToNewWindow(1);
	}

	/**
	 * @author Indium-Baskar date: 29/11/2021 Modified date:N/A
	 * @Description: This method used to verify error message for required tag
	 * 
	 */

	public void validRequiredComment(String error) {
		driver.waitForPageToBeReady();
		base.stepInfo("Performing action in parent window");
		base.waitForElement(getDocView_CodingFormComments());
		getDocView_CodingFormComments().Clear();
		base.waitForElement(getSaveAndNextButton());
		getSaveAndNextButton().waitAndClick(5);
		base.waitForElement(getDocView_ErrorMessage(error));
		softAssertion.assertTrue(getDocView_ErrorMessage(error).Displayed());
		if (getDocView_ErrorMessage(error).Displayed()) {
			base.passedStep("Error message displayed for required tag");
		} else {
			base.stepInfo("Error message not displayed");
		}
		base.stepInfo("Performing action in child window");
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parent = reusableDocView.switchTochildWindow();
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_CodingFormComments());
		getDocView_CodingFormComments().Clear();
		base.waitForElement(getSaveAndNextButton());
		getSaveAndNextButton().waitAndClick(5);
		base.waitForElement(getDocView_ErrorMessage(error));
		softAssertion.assertTrue(getDocView_ErrorMessage(error).Displayed());
		if (getDocView_ErrorMessage(error).Displayed()) {
			base.passedStep("Error message displayed for required tag");
		} else {
			base.stepInfo("Error message not displayed");
		}
		reusableDocView.childWindowToParentWindowSwitching(parent);
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description: This method used to verify saved stamp tooltip *
	 */
	public void verifySavedStampTooltip(String expectedValue, String icon) {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getCodingStampIconText(icon));
			getCodingStampIconText(icon).ScrollTo();
			String actualValue = getCodingStampIconText(icon).GetAttribute("title");
			softAssertion.assertEquals(expectedValue, actualValue);
			base.passedStep("Tooltip is displayed successfully as expected");
		} catch (Exception e) {
			base.failedStep("Failed to display saved stamp tooltip");
		}

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description: This method used to select the uncompleted document *
	 */
	public void navigateToUncompleteDoc() {
		driver.waitForPageToBeReady();
		for (int i = 1; 1 <= getDocView_MiniListDocuments().size(); i++) {
			if (getCompleteBtnNotPresent().isElementAvailable(5)) {
				base.waitForElement(getDocView_MiniDocListIds(i));
				getDocView_MiniDocListIds(i).waitAndClick(10);
			} else {
				return;
			}
		}
	}

	/**
	 * @author Indium-Baskar date: 30/11/2021 Modified date:N/A
	 * @Description: This method used to verify saved colour should not clickable
	 *               again
	 * 
	 */

	public void verifySavedColourNotClickable(String fieldtext, String comment) {
		driver.waitForPageToBeReady();
		reusableDocView.editCodingFormAndSaveWithStampColour(fieldtext, Input.stampSelection, comment);
		base.waitForElement(getCodingFormStampButton());
		getCodingFormStampButton().waitAndClick(5);
		reusableDocView.savedColorNotClickable(Input.stampSelection);
		driver.getWebDriver().navigate().refresh();
//		driver.switchTo().alert().accept();
		driver.waitForPageToBeReady();
		reusableDocView.deleteStampColour(Input.stampSelection);
	}

	/**
	 * @author Indium-Baskar date: 30/11/2021 Modified date:N/A
	 * @Description: This method used to verify coding stamp display
	 * 
	 */

	public void verifyCodingStampDisplay() {
		driver.waitForPageToBeReady();
		base.stepInfo("Performing action in Child window");
		base.stepInfo("Validation for non-audio document");
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parent = reusableDocView.switchTochildWindow();
		reusableDocView.stampButtonShouldDisplay();
		reusableDocView.switchToNewWindow(1);
		for (int i = 20; i <= 20; i++) {
			getClickDocviewID(i).waitAndClick(5);
			driver.waitForPageToBeReady();
		}
		base.stepInfo("Validation for audio document");
		reusableDocView.switchToNewWindow(2);
		reusableDocView.stampButtonShouldDisplay();
		reusableDocView.childWindowToParentWindowSwitching(parent);
	}

	/**
	 * @author Indium-Baskar date: 30/11/2021 Modified date:N/A
	 * @Description: This method used to verify saved colour should not clickable
	 *               again
	 * 
	 */

	public void verifySavedColourNotClickableChildWindow(String fieldtext, String comment) {
		driver.waitForPageToBeReady();
		reusableDocView.editCodingFormAndSaveWithStampColour(fieldtext, Input.stampSelection, comment);
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parent = reusableDocView.switchTochildWindow();
		base.waitForElement(getCodingFormStampButton());
		getCodingFormStampButton().waitAndClick(5);
		reusableDocView.childWindowToParentWindowSwitching(parent);
		reusableDocView.savedColorNotClickable(Input.stampSelection);
		driver.getWebDriver().navigate().refresh();
		driver.switchTo().alert().accept();
		driver.waitForPageToBeReady();
		reusableDocView.deleteStampColour(Input.stampSelection);
	}

	/**
	 * @author Indium-Baskar date: 30/11/2021 Modified date:N/A
	 * @Description: This method used to validate cancel button in child popup
	 * 
	 */

	public void verifyCancelButtonInChild(String fieldtext, String comment) {
		driver.waitForPageToBeReady();
		base.stepInfo("Performing action in Child window");
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parent = reusableDocView.switchTochildWindow();
		reusableDocView.editCodingForm(comment);
		base.waitForElement(getCodingFormStampButton());
		getCodingFormStampButton().waitAndClick(10);
		reusableDocView.childWindowToParentWindowSwitching(parent);
		base.waitForElement(getCodingStampTextBox());
		getCodingStampTextBox().SendKeys(fieldtext);
		base.waitForElement(getDrp_StampColour());
		getDrp_StampColour().waitAndClick(10);
		base.waitForElement(getAssignedColour(Input.stampSelection));
		getAssignedColour(Input.stampSelection).waitAndClick(10);
		base.waitForElement(getCodingStampCancel());
		getCodingStampCancel().waitAndClick(10);
		if (getPopUpVerify().Displayed()) {
			base.failedStep("Coding stamp saved");
		} else {
			base.passedStep("Coding stamp not saved");
			base.passedStep("Coding stamp PopUp closed after clicking cancel button");
		}
	}

	public void validateWithoutEditUsingStamp(String fieldText) {
		driver.waitForPageToBeReady();
		base.stepInfo("Performing action from parent window");
		base.waitForElement(getDocView_CodingFormComments());
		getDocView_CodingFormComments().Clear();
		reusableDocView.stampColourSelection(fieldText, Input.stampSelection);
		boolean flag = getCodingFormValidErrorMeta().Displayed();
		softAssertion.assertTrue(flag);
		if (getCodingFormValidErrorMeta().Displayed()) {
			base.stepInfo("Coding form validation error message displayed");
			base.passedStep("Application not allowed to save without saving required field from stamp popup");
		} else {
			return;
		}
		getCodingStampCancel().waitAndClick(5);
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parent = reusableDocView.switchTochildWindow();
		base.waitForElement(getDocView_CodingFormComments());
		getDocView_CodingFormComments().Clear();
		base.waitForElement(getCodingFormStampButton());
		getCodingFormStampButton().waitAndClick(5);
		reusableDocView.childWindowToParentWindowSwitching(parent);
		reusableDocView.popUpAction(fieldText, Input.stampSelection);
		boolean flagOne = getCodingFormValidErrorMeta().Displayed();
		softAssertion.assertTrue(flagOne);
		if (getCodingFormValidErrorMeta().Displayed()) {
			base.stepInfo("Coding form validation error message displayed");
			base.passedStep("Application not allowed to save without saving required field from stamp popup");
		} else {
			return;
		}
		getCodingStampCancel().waitAndClick(5);
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium-Baskar date: 30/11/2021 Modified date:N/A
	 * @Description: This method used to verify Code same as in analytics doc
	 * 
	 */

	public void verifyAnalyticsCodeSameAs(String fieldtext, String comment) throws InterruptedException {
		driver.waitForPageToBeReady();
		reusableDocView.editCodingFormAndSaveWithStampColour(fieldtext, Input.stampSelection, comment);
		List<String> docId = reusableDocView.analyticalDocs();
		for (int i = 1; i <= 1; i++) {
			base.waitForElement(getDocView_Analytics_NearDupe_Doc(i));
			getDocView_Analytics_NearDupe_Doc(i).waitAndClick(5);
		}
		reusableDocView.codeSameAsInAnalyticalPanel();
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		reusableDocView.lastAppliedStamp(Input.stampSelection);
		reusableDocView.verifySavedStamp(comment);
		driver.getWebDriver().navigate().refresh();
		driver.switchTo().alert().accept();
		driver.waitForPageToBeReady();
		reusableDocView.deleteStampColour(Input.stampSelection);
	}

	/**
	 * @author Gopianth
	 * @Description : this method used for editing Coding Form And Entering To Next
	 *              Document
	 */
	public void editingCodingFormAndEnteringToNextDocument(String docComment) {
		try {
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			reusableDocView.clickGearIconOpenCodingFormChildWindow();
			String parentWindow = reusableDocView.switchTochildWindow();
			base.waitForElement(getResponsiveCheked());
			getResponsiveCheked().Click();
			base.waitForElement(getNonPrivilegeRadio());
			getNonPrivilegeRadio().Click();
			base.waitForElement(getDocument_CommentsTextBox());
			getDocument_CommentsTextBox().SendKeys(docComment);
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			base.waitForElement(getSaveAndNextLink());
			base.waitTillElemetToBeClickable(getSaveAndNextLink());
			getSaveAndNextLink().Click();
			reusableDocView.childWindowToParentWindowSwitching(parentWindow);
			driver.waitForPageToBeReady();
			base.waitForElement(getDocView_SaveWidgetButton());
			getDocView_SaveWidgetButton().Click();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception while editing Coding Form And Entering To Next Document" + e.getMessage());
		}
	}

	/**
	 * @author Gopianth
	 * @Description : this method used for verifying persistent Hits Panel
	 *              Displayed.
	 */
	public void verifyPersistentHitsPanelDisplayed() {
		try {

			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			getPersistentPanel().isElementAvailable(15);
			if (getPersistentPanel().Displayed()) {
				base.passedStep("Persistent hits panel is displayed successfully");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception while editing Coding Form And Entering To Next Document" + e.getMessage());
		}
	}

	/**
	 * @author Gopianth
	 * @Description : this method used for clicking on persistant hit eye icon.
	 */
	public void clickOnPersistantHitEyeIcon() {
		try {

			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			try {
				getPersistantHitEyeIcon().waitAndClick(10);
			} catch (Exception e) {
				getAudioPersistantHitEyeIcon().waitAndClick(10);
			}

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception while clicking on persistant hit eye icon." + e.getMessage());
		}
	}

	/**
	 * @author Gopianth
	 * @Description : this method used for verifying persistent Hits toogle not
	 *              Displayed.
	 */
	public void verifyPersistentHitsToogleNotDisplayed() {
		try {

			driver.scrollPageToTop();
			driver.waitForPageToBeReady();

			try {
				if (getPersistentToogle().isElementPresent()) {
					base.stepInfo("Persistent hits toogle is displayed");
				}
			} catch (Exception e) {
				base.passedStep("Persistent hits toogle is not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception while verifying persistent Hits toogle not Displayed." + e.getMessage());
		}
	}

	/**
	 * @author Mohan 01/12/21 NA Modified date: NA Modified by:NA
	 * @description To Select Family Member Purehit count
	 */
	public void selectFamilyMemberPureHit() {

		base.waitForElement(getFamilyMemberPureHitCount());
		getFamilyMemberPureHitCount().waitAndClick(5);
		base.stepInfo("Family member docs are added to the shopping cart");

	}

	/**
	 * @Author Mohan Created on 01/12/2021
	 * @Description To perform RemoveCodeSameAs FamilyMembers docs in the DocView
	 *
	 */
	public void performRemoveCodeSameForFamilyMembersDocuments(String windowId) throws InterruptedException {

		driver.waitForPageToBeReady();
		JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
		driver.waitForPageToBeReady();
		Point p = getDocView_Analytics_liDocumentThreadMap().getWebElement().getLocation();
		je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
		getDocView_Analytics_FamilyTab().ScrollTo();
		getDocView_Analytics_FamilyTab().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Analytics_ChildWindow_FamilyTab_Firstdoc().Displayed();
			}
		}), Input.wait60);
		getDocView_Analytics_ChildWindow_FamilyTab_Firstdoc().waitAndClick(15);

		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(15);

		base.waitForElement(getDocView_FamilyRemoveCodeSameAs());
		getDocView_FamilyRemoveCodeSameAs().waitAndClick(15);

		driver.switchTo().window(windowId);

		base.VerifySuccessMessage("Code Same has been successfully removed");

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!windowId.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		driver.waitForPageToBeReady();
		try {
			if (getDocView_Analytics_FamilyMember_CodeSameLink().getWebElement().isDisplayed()) {
				softAssertion.assertTrue(getDocView_Analytics_FamilyMember_CodeSameLink().Displayed());
				base.failedStep("Selected Document is having code as same icon under Family Member tabss");
			}
		} catch (Exception e) {
			base.passedStep(
					"Code same icon is not displayed for the selected documents and documents from Family Member are unchecked successfull");
		}

	}

	/**
	 * @Author Mohan Created on 01/12/2021
	 * @Description To perform RemoveCodeSame in Threaded tab in the DocView.
	 */
	public void performRemoveCodeSameForThreadDocuments(String windowId) throws InterruptedException {

		base.waitForElement(getDocView_Analytics_liDocumentThreadMap());
		getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);

		for (int i = 1; i <= 1; i++) {
			base.waitForElement(getDocView_Analytics_ThreadedDocument_Doc(i));
			getDocView_Analytics_ThreadedDocument_Doc(i).waitAndClick(5);
		}

		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(15);

		base.waitForElement(getDocView_Analytics_Thread_RemoveCodeSameAs());
		getDocView_Analytics_Thread_RemoveCodeSameAs().waitAndClick(15);

		driver.switchTo().window(windowId);

		base.VerifySuccessMessage("Code Same has been successfully removed");

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!windowId.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		driver.waitForPageToBeReady();
		for (int i = 1; i <= 1; i++) {
			try {
				if (geDocView_ThreadMap_CodeSameAsIcon(i).getWebElement().isDisplayed()) {
					softAssertion.assertTrue(geDocView_ThreadMap_CodeSameAsIcon(i).Displayed());

					base.failedStep("CodeAsSame icon is displayed for the selected docs ");
				}
			} catch (Exception e) {

				base.passedStep("CodeAsSame icon is not displayed for the selected docs");
				UtilityLog.info("Verification failed due to " + e.getMessage());
			}

		}

	}

	/**
	 * @Author Mohan Created on 01/12/2021
	 * @Description To perform RemoveCodeSame in Conceptullay tab in the DocView.
	 */
	public void performRemoveCodeSameForConceptualDocuments(String windowId) throws InterruptedException {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Analytics_liDocumentConceptualSimilarab().Displayed();
			}
		}), Input.wait30);
		getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(15);

		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_Analytics_Conceptual_FirstDoc());
		getDocView_Analytics_Conceptual_FirstDoc().waitAndClick(15);

		getDocView_ChildWindow_ActionButton().waitAndClick(15);

		getDocView_Analytics_Concept_RemoveCodeSameAs().waitAndClick(15);

		driver.switchTo().window(windowId);

		base.VerifySuccessMessage("Code Same has been successfully removed");

		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!windowId.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}

		driver.waitForPageToBeReady();
		try {
			if (geDocView_Concept_CodeSameAsIcon().getWebElement().isDisplayed()) {
				softAssertion.assertTrue(geDocView_Concept_CodeSameAsIcon().Displayed());
				base.failedStep("CodeAsSame icon is displayed for the selected docs ");
			}
		} catch (Exception e) {

			base.passedStep("CodeAsSame icon is not displayed for the selected docs");
			UtilityLog.info("Verification failed due to " + e.getMessage());
		}
	}

	/**
	 * @author Indium-Baskar date: 06/12/2021 Modified date:N/A
	 * @Description: This method used to verify coding stamp display
	 * 
	 */

	public void validateCodingstampInParent() {
		driver.waitForPageToBeReady();
		base.stepInfo("Performing action in Parent window");
		base.stepInfo("Validation for non-audio document");
		reusableDocView.stampButtonShouldDisplay();
		for (int i = 20; i <= 20; i++) {
			getClickDocviewID(i).waitAndClick(5);
			driver.waitForPageToBeReady();
		}
		base.stepInfo("Validation for audio document");
		reusableDocView.stampButtonShouldDisplay();
	}

	/**
	 * @author Indium-Baskar date: 06/12/2021 Modified date:N/A
	 * @Description: This method used to verify checkmark icon after scrolling
	 *               document clicking on code same as last
	 * 
	 */

	public void checkMarkiconverify() {
		driver.waitForPageToBeReady();
		reusableDocView.editingCodingFormWithCompleteButton();
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		base.waitForElement(getDocView_HdrMinDoc());
		getDocView_HdrMinDoc().waitAndClick(5);
		driver.waitForPageToBeReady();
		reusableDocView.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String prnDoc = getVerifyPrincipalDocument().getText();
		reusableDocView.scrollingDocumentInMiniDocList();
		reusableDocView.switchToNewWindow(3);
		reusableDocView.clickCodeSameAsLast();
		reusableDocView.switchToNewWindow(2);
		base.waitForElement(getVerifyPrincipalDocument());
		String secDoc = getVerifyPrincipalDocument().getText();
		if (prnDoc != secDoc) {
			getverifyCodeSameAsLast().WaitUntilPresent().ScrollTo();
			base.passedStep("Checkmark icon displayed for completed document");
		} else {
			base.failedStep("Checkmark icon not displayed");
		}
		reusableDocView.closeWindow(2);
		reusableDocView.closeWindow(1);
		reusableDocView.switchToNewWindow(1);
	}

	/**
	 * @author Indium-Baskar date: 06/12/2021 Modified date:N/A
	 * @Description: This method used to verify checkmark icon after scrolling
	 *               document clicking on saved stamp
	 * 
	 */

	public void checkMarkIconUsingSavedStamp(String fieldText) {
		driver.waitForPageToBeReady();
		reusableDocView.editCodingFormAndSaveWithStamp(fieldText, Input.stampSelection);
		base.waitForElement(getVerifyPrincipalDocument());
		String prnDoc = getVerifyPrincipalDocument().getText();
		reusableDocView.scrollingDocumentInMiniDocList();
		reusableDocView.lastAppliedStamp(Input.stampSelection);
		base.waitForElement(getVerifyPrincipalDocument());
		String secDoc = getVerifyPrincipalDocument().getText();
		if (prnDoc != secDoc) {
			getverifyCodeSameAsLast().WaitUntilPresent().ScrollTo();
			base.passedStep("Checkmark icon displayed for completed document");
		} else {
			base.failedStep("Checkmark icon not displayed");
		}
		softAssertion.assertTrue(getverifyCodeSameAsLast().Displayed());
	}

	/**
	 * @author Indium-Baskar date: 06/12/2021 Modified date:N/A
	 * @Description: This method used to verify checkmark icon after scrolling
	 *               document clicking on code same as last
	 * 
	 */

	public void checkMarkIconverifyParentWindow() {
		driver.waitForPageToBeReady();
		reusableDocView.editingCodingFormWithCompleteButton();
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String prnDoc = getVerifyPrincipalDocument().getText();
		reusableDocView.scrollingDocumentInMiniDocList();
		reusableDocView.clickCodeSameAsLast();
		base.waitForElement(getVerifyPrincipalDocument());
		String secDoc = getVerifyPrincipalDocument().getText();
		if (prnDoc != secDoc) {
			getverifyCodeSameAsLast().WaitUntilPresent().ScrollTo();
			base.passedStep("Checkmark icon displayed for completed document");
		} else {
			base.failedStep("Checkmark icon not displayed");
		}
		softAssertion.assertTrue(getverifyCodeSameAsLast().Displayed());
	}

	/**
	 * @author Indium-Baskar date: 06/12/2021 Modified date:N/A
	 * @Description: This method used to verify checkmark icon after scrolling
	 *               document clicking on complete button
	 * 
	 */

	public void validateCompleteBtnAndVerifyCheckMark() {
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String prnDoc = getVerifyPrincipalDocument().getText();
		reusableDocView.scrollingDocumentInMiniDocList();
		reusableDocView.editingCodingFormWithCompleteButton();
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String secDoc = getVerifyPrincipalDocument().getText();
		if (prnDoc != secDoc) {
			getverifyCodeSameAsLast().WaitUntilPresent().ScrollTo();
			base.passedStep("Checkmark icon displayed for completed document");
		} else {
			base.failedStep("Checkmark icon not displayed");
		}
		softAssertion.assertTrue(getverifyCodeSameAsLast().Displayed());
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		base.waitForElement(getDocView_HdrMinDoc());
		getDocView_HdrMinDoc().waitAndClick(5);
		driver.waitForPageToBeReady();
		reusableDocView.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String prnChildDoc = getVerifyPrincipalDocument().getText();
		reusableDocView.scrollingDocumentInMiniDocList();
		reusableDocView.switchToNewWindow(3);
		reusableDocView.editingCodingFormWithCompleteButtonChild();
		reusableDocView.switchToNewWindow(2);
		base.waitForElement(getVerifyPrincipalDocument());
		String secChildDoc = getVerifyPrincipalDocument().getText();
		if (prnChildDoc != secChildDoc) {
			getverifyCodeSameAsLast().WaitUntilPresent().ScrollTo();
			base.passedStep("Checkmark icon displayed for completed document");
		} else {
			base.failedStep("Checkmark icon not displayed");
		}
		softAssertion.assertTrue(getverifyCodeSameAsLast().Displayed());
		reusableDocView.closeWindow(2);
		reusableDocView.closeWindow(1);
		reusableDocView.switchToNewWindow(1);
	}

	/**
	 * @author Jayanthi.ganesan
	 * @return
	 */
	public String verifyDocCountDisplayed_DocView() {
		driver.waitForPageToBeReady();
		int i = base.getIndex(tableheader(), "DOCID");
		base.waitForElementCollection(getDocIds(i));
		getDocIds(i).FindWebElements().size();
		String count = String.valueOf(getDocIds(i).FindWebElements().size());
		base.stepInfo(count + "  Doc Count displayed in Doc view page that is selected to view in doc view.");
		return count;
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void verifyErrorMsg() {
		driver.waitForPageToBeReady();
		base.stepInfo("Performing action from parent window");
		base.waitForElement(getDocView_CodingFormComments());
		getDocView_CodingFormComments().Clear();
		getSaveAndNextButton().waitAndClick(5);

		boolean flag = getCodingFormValidErrorMeta().Displayed();
		softAssertion.assertTrue(flag);
		if (getCodingFormValidErrorMeta().Displayed()) {
			base.stepInfo("Coding form validation error message displayed");
			base.passedStep("Coding from without String get error message");
		} else {
			return;
		}
		boolean flag1 = getDocView_ErrorMessage("Error Message").Displayed();
		softAssertion.assertTrue(flag1);
		if (getDocView_ErrorMessage("Error Message").Displayed()) {
			base.stepInfo("Coding form validation error text displayed");
			base.passedStep("docview error text displayed");
		} else {
			return;
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void verifyErrorMsgforMetaData() {
		driver.waitForPageToBeReady();
		base.stepInfo("Performing action from parent window");
		base.waitForElement(getMetaDataInputInDocView());
		getMetaDataInputInDocView().Clear();
		getSaveAndNextButton().waitAndClick(5);

		boolean flag = getCodingFormValidErrorMeta().Displayed();
		softAssertion.assertTrue(flag);
		if (getCodingFormValidErrorMeta().Displayed()) {
			base.stepInfo("Coding form validation error message displayed");
			base.passedStep("Coding from without String get error message");
		} else {
			return;
		}
		boolean flag1 = getDocView_ErrorMessage("Error Message").Displayed();
		softAssertion.assertTrue(flag1);
		if (getDocView_ErrorMessage("Error Message").Displayed()) {
			base.stepInfo("Coding form validation error text displayed");
			base.passedStep("docview error text displayed");
		} else {
			return;
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void verifyPassedmsg() {
		base.waitForElement(getDocView_CodingFormComments());
		getDocView_CodingFormComments().SendKeys("Reviewed");
		getSaveAndNextButton().waitAndClick(5);
		base.VerifySuccessMessage("Document saved successfully");
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void verifyPassedmsgforMetaData() {
		base.waitForElement(getMetaDataInputInDocView());
		getMetaDataInputInDocView().SendKeys("Reviewed");
		getSaveAndNextButton().waitAndClick(5);
		base.VerifySuccessMessage("Document saved successfully");
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void verifyPassedmsginChildWindow() {
		reusableDocView.switchToNewWindow(2);
		base.waitForElement(getDocView_CodingFormComments());
		getDocView_CodingFormComments().SendKeys("Reviewed");
		getSaveAndNextButton().waitAndClick(5);
		driver.close();
		reusableDocView.switchToNewWindow(1);
		base.VerifySuccessMessage("Document saved successfully");
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void verifyPassedmsginChildWindowforMetaData() {
		reusableDocView.switchToNewWindow(2);
		base.waitForElement(getMetaDataInputInDocView());
		getMetaDataInputInDocView().SendKeys("Reviewed");
		getSaveAndNextButton().waitAndClick(5);
		driver.close();
		reusableDocView.switchToNewWindow(1);
		base.VerifySuccessMessage("Document saved successfully");
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void openChildWindow() {

		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		reusableDocView.switchTochildWindow();

		base.waitForElement(getDocView_CodingFormComments());
		getDocView_CodingFormComments().Clear();
		getSaveAndNextButton().waitAndClick(5);
		boolean flag = getDocView_ErrorMessage("Error Message").Displayed();
		softAssertion.assertTrue(flag);
		if (getDocView_ErrorMessage("Error Message").Displayed()) {
			base.stepInfo("Coding form validation error text displayed");
			base.passedStep("Child window error text displayed");
		} else {
			return;
		}
		reusableDocView.switchToNewWindow(1);

		boolean flagOne = getCodingFormValidErrorMeta().Displayed();
		softAssertion.assertTrue(flagOne);
		if (getCodingFormValidErrorMeta().Displayed()) {
			base.stepInfo("Coding form validation error message displayed");
			base.passedStep("Child window also not create doc without input text");
		} else {
			return;
		}
		reusableDocView.switchToNewWindow(2);
		driver.close();
		reusableDocView.switchToNewWindow(1);

	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void openChildWindowInMeta() {

		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		reusableDocView.switchTochildWindow();

		base.waitForElement(getMetaDataInputInDocView());
		getMetaDataInputInDocView().Clear();
		getSaveAndNextButton().waitAndClick(5);
		boolean flag = getDocView_ErrorMessage("Error Message").Displayed();
		softAssertion.assertTrue(flag);
		if (getDocView_ErrorMessage("Error Message").Displayed()) {
			base.stepInfo("Coding form validation error text displayed");
			base.passedStep("Child window error text displayed");
		} else {
			return;
		}
		reusableDocView.switchToNewWindow(1);

		boolean flagOne = getCodingFormValidErrorMeta().Displayed();
		softAssertion.assertTrue(flagOne);
		if (getCodingFormValidErrorMeta().Displayed()) {
			base.stepInfo("Coding form validation error message displayed");
			base.passedStep("Child window also not create doc without input text");
		} else {
			return;
		}
		reusableDocView.switchToNewWindow(2);
		driver.close();
		reusableDocView.switchToNewWindow(1);

	}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for verify complete button
	public void CompleteDocsButton() {
		try {
			base.waitForElement(getCompleteDocBtn());
			softAssertion.assertTrue(getCompleteDocBtn().Displayed());
			if (getCompleteDocBtn().Displayed()) {
				getCompleteDocBtn().Click();
			} else {
				System.out.println("---No action");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for how many docs to complete
	public List<String> bulkDocumentToCompleteButton(int sizeofList, int numbersToComplete, String comment) {
		for (int i = 1; i <= numbersToComplete; i++) {
			int docToChoose = base.randNumber(sizeofList);
			System.out.println(docToChoose);
			String name = docIDlist.get(docToChoose);
			getDociD(name).waitAndClick(5);
			driver.waitForPageToBeReady();
			reusableDocView.editCodingForm(comment);
			base.waitForElement(getCompleteDocBtn());
			getCompleteDocBtn().waitAndClick(5);
			System.out.println("Completed Document : " + name);
			base.stepInfo("Completed Document : " + name);
			completedDoc.add(name);
			driver.waitForPageToBeReady();
		}
		return completedDoc;
	}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for how many docs to complete using stamp
	public List<String> documentToCompleteUsingStamp(int sizeofList, int numbersToComplete, String comment,
			String fieldText, String colour) {
		for (int i = 1; i <= numbersToComplete; i++) {
			int docToChoose = base.randNumber(sizeofList);
			System.out.println(docToChoose);
			String name = stampList.get(docToChoose);
			getDociD(name).waitAndClick(5);
			driver.waitForPageToBeReady();
			reusableDocView.editCodingForm(comment);
			base.waitForElement(getCodingFormStampButton());
			getCodingFormStampButton().waitAndClick(5);
			reusableDocView.popUpAction(fieldText, colour);
			System.out.println("Completed Document : " + name);
			base.stepInfo("Completed Document : " + name);
			completeStampList.add(name);
			driver.waitForPageToBeReady();
		}
		return completeStampList;
	}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for completing document
	public void completeDocumentRandom(int count, String comment) {
		driver.waitForPageToBeReady();
		base.waitForElementCollection(getMiniDocListDocIdText());
		ElementCollection element2 = getMiniDocListDocIdText();
		int sizeofList = getMiniDocListDocIdText().size();
		docIDlist = base.availableListofElements(element2);
		completedDoc = bulkDocumentToCompleteButton(sizeofList, count, comment);
		System.out.println(completedDoc);
	}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for completing document using stamp
	public void completeRandomDocsUsingStamp(int count, String comment, String filedText, String colour) {
		driver.waitForPageToBeReady();
		base.waitForElementCollection(getMiniDocListDocIdText());
		ElementCollection element2 = getMiniDocListDocIdText();
		int sizeofList = getMiniDocListDocIdText().size();
		stampList = base.availableListofElements(element2);
		completeStampList = documentToCompleteUsingStamp(sizeofList, count, comment, filedText, colour);
		System.out.println(completeStampList);
	}

	/**
	 * @author Indium-Baskar date: 06/12/2021 Modified date:N/A
	 * @Description: This method used to verify uncomplete button after completeing
	 *               document
	 * 
	 */
	public void unCompleteButtonDisplay() {
		driver.waitForPageToBeReady();
		base.waitForElement(getReviewGearIcon());
		getReviewGearIcon().waitAndClick(5);
		base.waitForElement(getShowCompletedDocsToggle());
		getShowCompletedDocsToggle().waitAndClick(5);
		base.waitForElement(getMiniDocListConfirmationButton("Save"));
		getMiniDocListConfirmationButton("Save").waitAndClick(5);
		driver.waitForPageToBeReady();
		base.waitForElementCollection(getMiniDocListDocIdText());
		driver.waitForPageToBeReady();
		for (String docId : completedDoc) {
			getDociD(docId).ScrollTo();
			getDociD(docId).waitAndClick(5);
			softAssertion.assertTrue(getUnCompleteButton().Displayed());
		}
		base.passedStep("Uncomplete button displayed for completed document");
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium-Baskar date: 06/12/2021 Modified date:N/A
	 * @Description: This method used to validate complete document
	 */

	public void validateCompleteDocs() {
		driver.waitForPageToBeReady();
		base.waitForElementCollection(getMiniDocListDocIdText());
		for (int i = 1; 1 <= getMiniDocListDocIdText().size(); i++) {
//			miniDocList.configureMiniDocListToShowCompletedDocs();
			if (getUnCompleteButton().isElementPresent() == true) {
				for (String docId : completeStampList) {
					getDociD(docId).ScrollTo();
					getDociD(docId).waitAndClick(5);
					driver.waitForPageToBeReady();
				}
			}
		}
	}

	/**
	 * @author Indium-Baskar date: 06/12/2021 Modified date:N/A
	 * @Description: This method used to validate complete document
	 */
	public void stampAndCompleteBtnValidation(String comment, String fieldText, String colour) {
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String prnDoc = getVerifyPrincipalDocument().getText();
		reusableDocView.editCodingForm(comment);
		base.waitForElement(getCodingFormStampButton());
		getCodingFormStampButton().waitAndClick(5);
		reusableDocView.popUpAction(fieldText, colour);
		// clicking on stamp applied
		base.stepInfo("performing action using stamp button");
		reusableDocView.lastAppliedStamp(colour);
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String prnSecDoc = getVerifyPrincipalDocument().getText();
		boolean flag = getDocView_defaultView().Enabled();
		softAssertion.assertTrue(flag);
		if (!prnDoc.equals(prnSecDoc)) {
			base.passedStep("Cursor navigated to next document");
		} else {
			base.failedStep("cursor not navigated");
		}
		getDociD(prnDoc).ScrollTo();
		getDociD(prnDoc).waitAndClick(5);
		reusableDocView.verifySavedStamp(comment);
		for (int i = 3; i <= 3; i++) {
			getClickDocviewID(i).WaitUntilPresent().waitAndClick(5);
			driver.waitForPageToBeReady();
		}
		base.waitForElement(getVerifyPrincipalDocument());
		String prnTrdDoc = getVerifyPrincipalDocument().getText();
		base.stepInfo("performing action using complete button");
		reusableDocView.editCodingForm(comment);
		base.waitForElement(getCompleteDocBtn());
		getCompleteDocBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String prnFrDoc = getVerifyPrincipalDocument().getText();
		boolean flag1 = getDocView_defaultView().Enabled();
		softAssertion.assertTrue(flag1);
		if (!prnTrdDoc.equals(prnFrDoc)) {
			base.passedStep("Cursor navigated to next document");
		} else {
			base.failedStep("cursor not navigated");
		}
		getDociD(prnTrdDoc).ScrollTo();
		getDociD(prnTrdDoc).waitAndClick(5);
		reusableDocView.verifySavedStamp(comment);
	}

	/**
	 * @author Indium-Baskar date: 06/12/2021 Modified date:N/A
	 * @Description: This method used to verify docview panel
	 */
	public void verifyPanels() {
		driver.waitForPageToBeReady();
		boolean flag;
		base.waitForElement(getDocView_HistoryButton());
		flag = getDocView_HistoryButton().Displayed();
		base.waitForElement(getDocView_Analytics_liDocumentThreadMap());
		flag = getDocView_Analytics_liDocumentThreadMap().Displayed();
		base.waitForElement(getCodingFormPanel());
		flag = getCodingFormPanel().Displayed();
		base.waitForElement(getDocView_MetaDataIcon());
		flag = getDocView_MetaDataIcon().Displayed();
		if (flag == true) {
			base.passedStep("");

		}
	}

	/**
	 * @author Indium-Baskar date: 06/12/2021 Modified date:N/A
	 * @Description: This method used to validate tooltips
	 */
	public void validateToolTipTitle(String expectedTooltip) {
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		base.stepInfo("performing action in parent window");
		if (getCodingFormStampButton().Displayed()) {
			base.waitForElement(getCodingFormStampButton());
			Actions builder = new Actions(driver.getWebDriver());
			builder.moveToElement(getCodingFormStampButton().getWebElement()).build().perform();
		} else {
			System.out.println("Save this coding form as a new stamp not displayed");
		}
		reusableDocView.verifyFloppyIconToolTip(expectedTooltip);
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parent = reusableDocView.switchTochildWindow();
		base.stepInfo("performing action in child window");
		if (getCodingFormStampButton().Displayed()) {
			base.waitForElement(getCodingFormStampButton());
			Actions builder = new Actions(driver.getWebDriver());
			builder.moveToElement(getCodingFormStampButton().getWebElement()).build().perform();
		} else {
			System.out.println("Save this coding form as a new stamp not displayed");
		}
		reusableDocView.verifyFloppyIconToolTip(expectedTooltip);
		reusableDocView.childWindowToParentWindowSwitching(parent);
	}

	/**
	 * @author Indium-Baskar date: 06/12/2021 Modified date:N/A
	 * @Description: This method used to validate tooltips for saved stamp
	 */
	public void validateToolTipTitleForSavedStamp(String stampText, String comment) {
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		base.stepInfo("performing action in parent window");
		reusableDocView.editCodingFormAndSaveWithStampColour(stampText, Input.stampSelection, comment);
		driver.waitForPageToBeReady();
		base.waitForElement(getCodingStampLastIcon(Input.stampSelection));
		if (getCodingStampLastIcon(Input.stampSelection).Displayed()) {
			Actions builder = new Actions(driver.getWebDriver());
			builder.moveToElement(getCodingStampLastIcon(Input.stampSelection).getWebElement()).build().perform();
		} else {
			System.out.println("Save this coding form as a new stamp not displayed");
		}
		reusableDocView.verifyFloppyIconToolTip(stampText);
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parent = reusableDocView.switchTochildWindow();
		base.stepInfo("performing action in child window");
		driver.waitForPageToBeReady();
		base.waitForElement(getCodingStampLastIcon(Input.stampSelection));
		if (getCodingStampLastIcon(Input.stampSelection).Displayed()) {
			Actions builder = new Actions(driver.getWebDriver());
			builder.moveToElement(getCodingStampLastIcon(Input.stampSelection).getWebElement()).build().perform();
		} else {
			System.out.println("Save this coding form as a new stamp not displayed");
		}
		reusableDocView.verifyFloppyIconToolTip(stampText);
		reusableDocView.childWindowToParentWindowSwitching(parent);
		reusableDocView.refreshAndAlert();
		reusableDocView.deleteStampColour(Input.stampSelection);
	}

	/**
	 * @author Indium-Baskar date: 06/12/2021 Modified date:N/A
	 * @Description: This method used to validate code same as last in child window
	 */
	public void childWinodwValidationForCodeSameAsLast() {
		driver.waitForPageToBeReady();
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parent = reusableDocView.switchTochildWindow();
		driver.waitForPageToBeReady();
		base.waitForElement(getCodeSameAsLast());
		softAssertion.assertTrue(getCodeSameAsLast().Displayed());
		if (getCodeSameAsLast().getWebElement().isSelected()) {
			getCodeSameAsLast().waitAndClick(10);
			base.failedStep("code same as last is clickable");
		} else {
			base.passedStep("Code same as last not clickabele in child window");

		}
		reusableDocView.childWindowToParentWindowSwitching(parent);
		softAssertion.assertAll();
	}

	/**
	 * @author Indium-Baskar date: 06/12/2021 Modified date:N/A
	 * @Description: This method used to validate code same as last in child window
	 */
	public void codeSameAsLastValid(String fieldText, String comment) {
		reusableDocView.editCodingFormAndSaveWithStampColour(fieldText, Input.stampSelection, comment);
		reusableDocView.clickCodeSameAsLast();
		base.stepInfo("Again click code same as last");
		reusableDocView.clickCodeSameAsLast();
		driver.getWebDriver().navigate().refresh();
		driver.scrollPageToTop();
		reusableDocView.deleteStampColour(Input.stampSelection);
	}

	/**
	 * @author Indium-Baskar date: 06/12/2021 Modified date:N/A
	 * @Description: This method used to validate save and next
	 */
	public void validateSaveAndNext() {
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String prnDoc = getVerifyPrincipalDocument().getText();
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parent = reusableDocView.switchTochildWindow();
		reusableDocView.editingCodingFormWithSaveAndNextChild();
		reusableDocView.childWindowToParentWindowSwitching(parent);
		String prnSecDoc = getVerifyPrincipalDocument().getText();
		softAssertion.assertNotEquals(prnDoc, prnSecDoc);
		if (!prnDoc.equals(prnSecDoc)) {
			base.passedStep("Cursor navigated to next document");
		} else {
			base.failedStep("Cursor not navigated to next documnet");
		}
	}

	/**
	 * @author Indium-Baskar date: 06/12/2021 Modified date:N/A
	 * @Description: This method used to validate saved stamp
	 */
	public void validateSavedStamp(String fieldText, String comment) {
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String prnDoc = getVerifyPrincipalDocument().getText();
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parent = reusableDocView.switchTochildWindow();
		reusableDocView.editCodingForm(comment);
		base.waitForElement(getCodingFormStampButton());
		getCodingFormStampButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		reusableDocView.switchToNewWindow(1);
		reusableDocView.popUpAction(fieldText, Input.stampSelection);
		driver.waitForPageToBeReady();
		base.stepInfo("user on the same document");
		reusableDocView.switchToNewWindow(2);
		reusableDocView.lastAppliedStamp(Input.stampSelection);
		driver.waitForPageToBeReady();
		reusableDocView.verifySavedStamp(comment);
		base.waitForElement(getSaveAndNextButton());
		getSaveAndNextButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		reusableDocView.switchToNewWindow(1);
		base.waitForElement(getVerifyPrincipalDocument());
		String prnSecDoc = getVerifyPrincipalDocument().getText();
		if (!prnDoc.equals(prnSecDoc)) {
			base.passedStep("Cursor navigated to next document");
		} else {
			base.failedStep("Cursor not navigated to next documnet");
		}
		reusableDocView.switchToNewWindow(2);
		reusableDocView.closeWindow(1);
		reusableDocView.switchToNewWindow(1);
		driver.getWebDriver().navigate().refresh();
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		reusableDocView.deleteStampColour(Input.stampSelection);
	}

	/**
	 * @Author Brundha
	 * @Description :Method for verfying mini doclist doc with parent document
	 * 
	 */
	public void selectMiniDocListDocAndComparingWithParentWindow() {
		try {
			base.waitTillElemetToBeClickable(getGearIcon());
			getGearIcon().waitAndClick(10);
			String parentwindow = driver.getWebDriver().getWindowHandle();
			base.waitForElement(getMiniDocListIcon());
			getMiniDocListIcon().waitAndClick(10);
			driver.waitForPageToBeReady();
			for (String winHandle1 : driver.getWebDriver().getWindowHandles()) {
				driver.switchTo().window(winHandle1);
			}
			base.waitForElement(getMIniDocListDocument());
			getMIniDocListDocument().waitAndClick(10);
			driver.waitForPageToBeReady();
			String ChildDocument = getMIniDocListDocument().getText();
			System.out.println(ChildDocument);
			driver.close();
			driver.switchTo().window(parentwindow);
			driver.waitForPageToBeReady();
			String ParentDocument = getDocId().getText();
			System.out.println(ParentDocument);
			if (ParentDocument.equals(ChildDocument)) {
				base.passedStep("" + ParentDocument + " and " + ChildDocument + " DocID are equal as expected");
			} else {
				base.failedMessage("" + ParentDocument + " and " + ChildDocument + " DocID is not  equal as expected");
			}
			base.stepInfo("The DocumentId in the parent window is verified");
		} catch (Exception e) {
			base.failedStep("exception occured while handling mini doclist document:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @Author Brundha
	 * @Description :Method to verify navigating icon in doclist page
	 * 
	 */

	public void switchFromChildWindowToParentWindow() {

		try {
			driver.waitForPageToBeReady();
			String parentwindow = driver.getWebDriver().getWindowHandle();
			base.waitTillElemetToBeClickable(getParentDocID());
			String parentDocId = getParentDocID().getText();
			base.waitTillElemetToBeClickable(getGearIcon());
			getGearIcon().waitAndClick(10);
			getMiniDocListIcon().waitAndClick(10);
			Set<String> windowhandles = driver.getWebDriver().getWindowHandles();
			driver.waitForPageToBeReady();
			List<String> list = new ArrayList<String>(windowhandles);
			driver.switchTo().window(list.get(1));
			driver.waitForPageToBeReady();
			driver.switchTo().window(list.get(0));
			base.waitForElement(getDocView_Last());
			getDocView_Last().Click();
			driver.waitForPageToBeReady();
			String DocId = getDocId().getText();
			System.out.println(DocId);
			if (parentDocId.equals(DocId)) {
				base.passedStep("" + parentDocId + " and " + DocId + " DocID are equal as expected");
			} else {
				base.failedMessage("" + parentDocId + " and " + DocId + " DocID is not  equal as expected");
			}
			Thread.sleep(Input.wait30 / 10);
			driver.switchTo().window(list.get(1));
			driver.close();
			driver.switchTo().window(parentwindow);
			base.waitForElement(getDocView_SaveWidgetButton());
			getDocView_SaveWidgetButton().Click();
		} catch (Exception e) {
			base.failedStep(
					"exception occured while handling mini dolist document and parent window:" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @author Indium-Baskar date: 09/12/2021 Modified date:N/A
	 * @Description: This method used to verify check mark icon
	 */
	public void checkMarkVerification() {
		driver.waitForPageToBeReady();
		reusableDocView.editingCodingFormWithCompleteButton();
		driver.waitForPageToBeReady();
		base.waitForElement(getverifyCodeSameAsLast());
		getverifyCodeSameAsLast().ScrollTo();
		softAssertion.assertTrue(getverifyCodeSameAsLast().Displayed());
		if (getverifyCodeSameAsLast().Displayed()) {
			base.stepInfo("Tick mark displayed in minidoclist child window for code same as action");
		} else {
			base.failedStep("Tick mark icon is not displayed");
			;
		}
	}

	/**
	 * @author Indium-Baskar date: 09/12/2021 Modified date:N/A
	 * @Description: This method used to verify after impersonate to apply for saved
	 *               stamp
	 */
	public void validateSavedStampAfterImpersonate(String fieldText, String comment) {
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String docId = getVerifyPrincipalDocument().getText().trim();
		reusableDocView.editCodingFormAndSaveWithStampColour(fieldText, Input.stampSelection, comment);
		base.stepInfo("Coding stamp applied for the document");
		reusableDocView.lastAppliedStamp(Input.stampSelection);
		driver.waitForPageToBeReady();
		base.waitForElement(getDociD(docId));
		getDociD(docId).waitAndClick(5);
		driver.waitForPageToBeReady();
		// base.CloseSuccessMsgpopup();
		reusableDocView.verifySavedStamp(comment);
		driver.waitForPageToBeReady();
		base.stepInfo("Document saved successfully");
		softAssertion.assertAll();
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void verifyErrorMsgforCheckGroup() {
		driver.waitForPageToBeReady();
		base.stepInfo("Performing action from parent window");
		base.waitForElement(groupelementCheckBox());
		groupelementCheckBox().Click();
		getSaveAndNextButton().waitAndClick(5);

		boolean flag = getCodingFormValidErrorMeta().Displayed();
		softAssertion.assertTrue(flag);
		if (getCodingFormValidErrorMeta().Displayed()) {
			base.stepInfo("Coding form validation in check group error message displayed");
			base.passedStep("Codingfrom disable checkbox get error message");
		} else {
			return;
		}
		boolean flag1 = getDocView_ErrorMessage("Error Message").Displayed();
		softAssertion.assertTrue(flag1);
		if (getDocView_ErrorMessage("Error Message").Displayed()) {
			base.stepInfo("Coding form validation error text displayed");
			base.passedStep("docview error text displayed");
		} else {
			return;
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void openChildWindowInCheckGroup() {

		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		reusableDocView.switchTochildWindow();

		reusableDocView.switchToNewWindow(1);

		boolean flagOne = getCodingFormValidErrorMeta().Displayed();
		softAssertion.assertTrue(flagOne);
		if (getCodingFormValidErrorMeta().Displayed()) {
			// base.stepInfo("Coding form validation error message displayed");
			base.passedStep("Child window error popup message Displayed");
		} else {
			return;
		}
		reusableDocView.switchToNewWindow(2);
		driver.close();
		reusableDocView.switchToNewWindow(1);
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void verifyParentWindowCursor() {
		driver.waitForPageToBeReady();
		base.stepInfo("Performing action from parent window");

		base.waitForElement(getVerifyPrincipalDocument());
		String prnDoc = getVerifyPrincipalDocument().getText();
		getSaveAndNextButton().waitAndClick(5);
		base.waitForElement(getVerifyPrincipalDocument());
		driver.waitForPageToBeReady();
		String prnSecDoc = getVerifyPrincipalDocument().getText();
		softAssertion.assertNotEquals(prnDoc, prnSecDoc);
		if (!prnDoc.equals(prnSecDoc)) {
			base.passedStep("Cursor navigated to next document");
		} else {
			base.failedStep("Curosr not navigated to next document");
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void verifyChildWindowCursor() {

		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		reusableDocView.switchTochildWindow();
		driver.waitForPageToBeReady();
		base.stepInfo("Performing action from child window");
		reusableDocView.switchToNewWindow(1);
		base.waitForElement(getVerifyPrincipalDocument());
		String prnDoc = getVerifyPrincipalDocument().getText();
		reusableDocView.switchToNewWindow(2);
		getSaveAndNextButton().waitAndClick(5);
		reusableDocView.switchToNewWindow(1);
		base.waitForElement(getVerifyPrincipalDocument());
		String prnSecDoc = getVerifyPrincipalDocument().getText();
		softAssertion.assertNotEquals(prnDoc, prnSecDoc);
		if (!prnDoc.equals(prnSecDoc)) {
			base.passedStep("Cursor navigated to next document");
		} else {
			base.failedStep("Curosr not navigated to next document");
		}
		reusableDocView.switchToNewWindow(2);
		driver.close();
		reusableDocView.switchToNewWindow(1);
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void verifyErrorMsgforRadioGroup() {
		driver.waitForPageToBeReady();

		getSaveAndNextButton().waitAndClick(5);

		boolean flag = getCodingFormValidErrorMeta().Displayed();
		softAssertion.assertTrue(flag);
		if (getCodingFormValidErrorMeta().Displayed()) {
			base.stepInfo("Coding form validation error message displayed");
			base.passedStep("Coding from without String get error message");
		} else {
			return;
		}
		boolean flag1 = getDocView_ErrorMessage("Error Message").Displayed();
		softAssertion.assertTrue(flag1);
		if (getDocView_ErrorMessage("Error Message").Displayed()) {
			base.stepInfo("Coding form validation error text displayed");
			base.passedStep("docview error text displayed");
		} else {
			return;
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void openChildWindowInRadioGroup() {

		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		reusableDocView.switchTochildWindow();

		getSaveAndNextButton().waitAndClick(5);
		boolean flag = getDocView_ErrorMessage("Error Message").Displayed();
		softAssertion.assertTrue(flag);
		if (getDocView_ErrorMessage("Error Message").Displayed()) {
			base.stepInfo("Coding form validation error text displayed");
			base.passedStep("Child window error text displayed");
		} else {
			return;
		}
		reusableDocView.switchToNewWindow(1);

		boolean flagOne = getCodingFormValidErrorMeta().Displayed();
		softAssertion.assertTrue(flagOne);
		if (getCodingFormValidErrorMeta().Displayed()) {
			base.stepInfo("Coding form validation error message displayed");
			base.passedStep("Child window also not create doc without input text");
		} else {
			return;
		}
		reusableDocView.switchToNewWindow(2);
		driver.close();
		reusableDocView.switchToNewWindow(1);

	}

	/**
	 * @author Indium-Baskar date: 10/12/2021 Modified date:N/A
	 * @Description: Validation of non-date format using save
	 */
	public void nonDateFormatValidationUsingSave(String projectFieldName) throws InterruptedException, AWTException {
		driver.waitForPageToBeReady();
		getReadOnlyTextBox(projectFieldName).WaitUntilPresent().ScrollTo();
//		base.waitForElement(getReadOnlyTextBox(projectFieldName));
		base.stepInfo("Peforming action in parent window");
		base.waitForElement(getDateFormat());
		getDateFormat().SendKeys("11/10/2021");
		base.waitForElement(getCodingFormSaveButton());
		getCodingFormSaveButton().waitAndClick(5);
		String errorText = getCodingFormValidErrorMeta().getText().trim();
		String actual = "Coding Form validation failed";
		softAssertion.assertEquals(errorText, actual);
		base.stepInfo("Save using save button on non-date format and verify error message");
		if (errorText.equals(actual)) {
			base.passedStep("Error message Dispalyed");
		} else {
			base.stepInfo("Error message not displayed");
		}
		base.stepInfo("Peforming action in child window");
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parentWindow = reusableDocView.switchTochildWindow();
		base.waitForElement(getDateFormat());
		getDateFormat().SendKeys("11/10/2021");
		base.waitForElement(getCodingFormSaveButton());
		getCodingFormSaveButton().waitAndClick(5);
		reusableDocView.childWindowToParentWindowSwitching(parentWindow);
		String errorTextCf = getCodingFormValidErrorMeta().getText().trim();
		String actualCf = "Coding Form validation failed";
		softAssertion.assertEquals(errorTextCf, actualCf);
		base.stepInfo("Save using save button on non-date format and verify error message");
		if (errorTextCf.equals(actualCf)) {
			base.passedStep("Error message Dispalyed");
		} else {
			base.stepInfo("Error message not displayed");
		}
		softAssertion.assertAll();
	}

	/**
	 * @author Indium-Baskar date: 10/12/2021 Modified date: N/A
	 * @Description:This method used to verify both alpha and integer value using
	 *                   save button
	 * 
	 */

	public void verifyingIntergerMetaDataUsingSave(String alpha, String stamp, String colour, String tiny, String small,
			String average, String bit, String huge) {
		base.stepInfo("Performing from parent window");
		base.stepInfo("Coding action using save button");
		passingAlphaIntergerMetaData(alpha, tiny, small, average, bit, huge);
		base.waitForElement(getCodingFormSaveButton());
		getCodingFormSaveButton().waitAndClick(5);
		String errorText = getCodingFormValidErrorMeta().getText().trim();
		String actual = "Coding Form validation failed";
		softAssertion.assertEquals(errorText, actual);
		if (getCodingFormValidErrorMeta().Displayed()) {
			base.stepInfo("Alpha and numeric character are not entered in integer metadata field");
			base.passedStep("Validation message displayed for when alpha and numeric entered");
		} else {
			base.stepInfo("Alpha and numeric value entering in interger metadata fields");
		}
		passingOutsideRangeIntergerMetaData(tiny, small, average, bit, huge);
		base.waitForElement(getCodingFormSaveButton());
		getCodingFormSaveButton().waitAndClick(5);
		if (getCodingFormValidErrorMeta().Displayed()) {
			base.passedStep("Validation message displayed for when values entered in outside of range ");
		} else {
			base.stepInfo("Validation message not displayed");
		}
		base.stepInfo("Performing from child window");
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		reusableDocView.switchTochildWindow();
		passingAlphaIntergerMetaData(alpha, tiny, small, average, bit, huge);
		base.waitForElement(getCodingFormSaveButton());
		getCodingFormSaveButton().waitAndClick(5);
		reusableDocView.switchToNewWindow(1);
		driver.waitForPageToBeReady();
		if (getCodingFormValidErrorMeta().Displayed()) {
			base.stepInfo("Alpha and numeric character are not entered in integer metadata field");
			base.passedStep("Validation message displayed for when alpha and numeric entered");
		} else {
			base.stepInfo("Alpha and numeric value entering in interger metadata fields");
		}
		List<String> windows = new ArrayList<>(driver.getWebDriver().getWindowHandles());
		for (String s : windows) {
			System.out.println("window: " + s);
		}
		String parentWindow = windows.get(0);
		String codingChildWindow = windows.get(1);
		reusableDocView.switchToNewWindow(2);
		passingOutsideRangeIntergerMetaData(tiny, small, average, bit, huge);
		base.waitForElement(getCodingFormSaveButton());
		getCodingFormSaveButton().waitAndClick(5);
		driver.switchTo().window(codingChildWindow).close();
		driver.switchTo().window(parentWindow);
		String errorTextcf = getCodingFormValidErrorMeta().getText().trim();
		String actualcf = "Coding Form validation failed";
		softAssertion.assertEquals(errorTextcf, actualcf);
		if (getCodingFormValidErrorMeta().Displayed()) {
			base.passedStep("Validation message displayed for when values entered in outside of range ");
		} else {
			base.stepInfo("Validation message not displayed");
		}
		softAssertion.assertAll();
	}

	/**
	 * @author Indium-Baskar date: 10/12/2021 Modified date: N/A
	 * @Description:This method used to verify both alpha and integer value using
	 *                   save complete
	 * 
	 */

	public void verifyingIntergerMetaDataUsingComplete(String alpha, String stamp, String colour, String tiny,
			String small, String average, String bit, String huge) {
		base.stepInfo("Performing from parent window");
		base.stepInfo("Coding action using save button");
		passingAlphaIntergerMetaData(alpha, tiny, small, average, bit, huge);
		base.waitForElement(getCompleteDocBtn());
		getCompleteDocBtn().waitAndClick(5);
		String errorText = getCodingFormValidErrorMeta().getText().trim();
		String actual = "Coding Form validation failed";
		softAssertion.assertEquals(errorText, actual);
		if (getCodingFormValidErrorMeta().Displayed()) {
			base.stepInfo("Alpha and numeric character are not entered in integer metadata field");
			base.passedStep("Validation message displayed for when alpha and numeric entered");
		} else {
			base.stepInfo("Alpha and numeric value entering in interger metadata fields");
		}
		passingOutsideRangeIntergerMetaData(tiny, small, average, bit, huge);
		base.waitForElement(getCompleteDocBtn());
		getCompleteDocBtn().waitAndClick(5);
		if (getCodingFormValidErrorMeta().Displayed()) {
			base.passedStep("Validation message displayed for when values entered in outside of range ");
		} else {
			base.stepInfo("Validation message not displayed");
		}
		base.stepInfo("Performing from child window");
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		reusableDocView.switchTochildWindow();
		passingAlphaIntergerMetaData(alpha, tiny, small, average, bit, huge);
		base.waitForElement(getCompleteDocBtn());
		getCompleteDocBtn().waitAndClick(5);
		reusableDocView.switchToNewWindow(1);
		driver.waitForPageToBeReady();
		if (getCodingFormValidErrorMeta().Displayed()) {
			base.stepInfo("Alpha and numeric character are not entered in integer metadata field");
			base.passedStep("Validation message displayed for when alpha and numeric entered");
		} else {
			base.stepInfo("Alpha and numeric value entering in interger metadata fields");
		}
		List<String> windows = new ArrayList<>(driver.getWebDriver().getWindowHandles());
		for (String s : windows) {
			System.out.println("window: " + s);
		}
		String parentWindow = windows.get(0);
		String codingChildWindow = windows.get(1);
		reusableDocView.switchToNewWindow(2);
		passingOutsideRangeIntergerMetaData(tiny, small, average, bit, huge);
		base.waitForElement(getCompleteDocBtn());
		getCompleteDocBtn().waitAndClick(5);
		driver.switchTo().window(codingChildWindow).close();
		driver.switchTo().window(parentWindow);
		String errorTextcf = getCodingFormValidErrorMeta().getText().trim();
		String actualcf = "Coding Form validation failed";
		softAssertion.assertEquals(errorTextcf, actualcf);
		if (getCodingFormValidErrorMeta().Displayed()) {
			base.passedStep("Validation message displayed for when values entered in outside of range ");
		} else {
			base.stepInfo("Validation message not displayed");
		}
		softAssertion.assertAll();
	}

	/**
	 * @author Indium-Baskar date: 10/12/2021 Modified date:N/A
	 * @Description: Validation of non-date format and alpha data for integer
	 *               datatype using save
	 */
	public void nonDateFormatAndAlphaValidationUsingSave(String date, String dateTime, String intData)
			throws InterruptedException, AWTException {
		driver.waitForPageToBeReady();
		base.stepInfo("Peforming action in parent window");
		base.waitForElement(getReadOnlyTextBox(date));
		getReadOnlyTextBox(date).SendKeys("11/10/2021");
		base.waitForElement(getReadOnlyTextBox(dateTime));
		getReadOnlyTextBox(dateTime).SendKeys("11/10/2021");
		base.waitForElement(getReadOnlyTextBox(intData));
		getReadOnlyTextBox(intData).SendKeys("a@");
		base.waitForElement(getCodingFormSaveButton());
		getCodingFormSaveButton().waitAndClick(5);
		String errorText = getCodingFormValidErrorMeta().getText().trim();
		String actual = "Coding Form validation failed";
		softAssertion.assertEquals(errorText, actual);
		base.stepInfo("validating error message for non-date format and alpha character");
		if (errorText.equals(actual)) {
			base.passedStep("Error message Dispalyed");
		} else {
			base.stepInfo("Error message not displayed");
		}
		base.stepInfo("Peforming action in child window");
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parentWindow = reusableDocView.switchTochildWindow();
		base.waitForElement(getReadOnlyTextBox(date));
		getReadOnlyTextBox(date).SendKeys("11/10/2021");
		base.waitForElement(getReadOnlyTextBox(dateTime));
		getReadOnlyTextBox(dateTime).SendKeys("11/10/2021");
		base.waitForElement(getReadOnlyTextBox(intData));
		getReadOnlyTextBox(intData).SendKeys("a@");
		base.waitForElement(getCodingFormSaveButton());
		getCodingFormSaveButton().waitAndClick(5);
		reusableDocView.childWindowToParentWindowSwitching(parentWindow);
		String errorTextcf = getCodingFormValidErrorMeta().getText().trim();
		String actualcf = "Coding Form validation failed";
		softAssertion.assertEquals(errorText, actual);
		base.stepInfo("validating error message for non-date format and alpha character");
		if (errorTextcf.equals(actualcf)) {
			base.passedStep("Error message Dispalyed");
		} else {
			base.stepInfo("Error message not displayed");
		}
		softAssertion.assertAll();
	}

	/**
	 * @author Indium-Baskar date: 10/12/2021 Modified date:N/A
	 * @Description: Validation of Required comment without passing value
	 */
	public void validateErrorMsgRequiredComment() {
		driver.waitForPageToBeReady();
		base.stepInfo("Performing action from parent window");
		base.waitForElement(getDocView_CodingFormComments());
		getDocView_CodingFormComments().Clear();
		base.waitForElement(getSaveAndNextButton());
		getSaveAndNextButton().waitAndClick(5);
		boolean flag = getCodingFormValidErrorMeta().Displayed();
		softAssertion.assertTrue(flag);
		if (getCodingFormValidErrorMeta().Displayed()) {
			base.stepInfo("Coding form validation error message displayed");
		} else {
			return;
		}
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parent = reusableDocView.switchTochildWindow();
		base.stepInfo("Performing action from parent window");
		base.waitForElement(getDocView_CodingFormComments());
		getDocView_CodingFormComments().Clear();
		base.waitForElement(getSaveAndNextButton());
		getSaveAndNextButton().waitAndClick(5);
		reusableDocView.childWindowToParentWindowSwitching(parent);
		boolean flagOne = getCodingFormValidErrorMeta().Displayed();
		softAssertion.assertTrue(flagOne);
		if (getCodingFormValidErrorMeta().Displayed()) {
			base.stepInfo("Coding form validation error message displayed");
		} else {
			return;
		}
		driver.waitForPageToBeReady();
		softAssertion.assertAll();
	}

	/**
	 * @author Indium-Baskar date: 10/12/2021 Modified date:N/A
	 * @Description: Validation of after loading display click save and next
	 */

	public void afterLoadingDisplayClickSaveAndnext() {
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String prnDoc = getVerifyPrincipalDocument().getText();
		reusableDocView.scrollingDocumentInMiniDocList();
		reusableDocView.editingCodingFormWithSaveAndNextChild();
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String prnSecDoc = getVerifyPrincipalDocument().getText();
		softAssertion.assertNotEquals(prnDoc, prnSecDoc);
		if (!prnDoc.equals(prnSecDoc)) {
			base.passedStep("Cursor navigated to next document");
		} else {
			base.failedStep("Curosr not navigated to next document");
		}
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		base.waitForElement(getDocView_HdrMinDoc());
		getDocView_HdrMinDoc().waitAndClick(5);
		reusableDocView.switchToNewWindow(2);
		base.waitForElement(getVerifyPrincipalDocument());
		String prnDocCF = getVerifyPrincipalDocument().getText();
		reusableDocView.scrollingDocumentInMiniDocList();
		reusableDocView.switchToNewWindow(3);
		reusableDocView.editingCodingFormWithSaveAndNextChild();
		driver.waitForPageToBeReady();
		reusableDocView.switchToNewWindow(2);
		base.waitForElement(getVerifyPrincipalDocument());
		String prnSecDocCF = getVerifyPrincipalDocument().getText();
		softAssertion.assertNotEquals(prnDoc, prnSecDoc);
		if (!prnDocCF.equals(prnSecDocCF)) {
			base.passedStep("Cursor navigated to next document");
		} else {
			base.failedStep("Curosr not navigated to next document");
		}
		reusableDocView.closeWindow(2);
		reusableDocView.closeWindow(1);
		reusableDocView.switchToNewWindow(1);
		softAssertion.assertAll();
	}

	/**
	 * @Author Brundha
	 * @Description :method to verify persitant panel text
	 * 
	 */

	public void SearchStringVerification() {
		try {
			driver.waitForPageToBeReady();
			if (getPanelText().isElementPresent()) {
				base.passedStep("The search string text is displayed  as expected");
			} else {
				base.failedStep("The search string text is displayed as expected");
			}
		} catch (Exception e) {
			base.failedStep("exception occured while handling persistant panel" + e.getMessage());
			e.printStackTrace();
		}

	}

	/**
	 * @author Gopinath
	 * @Description : Method for getting annotation layer count on current document.
	 * @return : annotation layers count on current document.
	 */
	public int getAnnotationLayerCountOnCurrentDocument() throws InterruptedException {
		int layers = 0;
		try {
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			layers = getAnnotations().FindWebElements().size();
			base.passedStep("Current doucent annotaion layer count -- " + layers);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep(
					"Exception occured while getting annotation layer count on current document." + e.getMessage());

		}
		return layers;
	}

	/**
	 * @author Gopinath
	 * @Description : Method for performing non audio annotation.
	 */
	public void performNonAudioAnnotation() throws InterruptedException {
		try {
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			getDocView_AnnotateIcon().isElementAvailable(8);
			getDocView_AnnotateIcon().waitAndClick(10);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDocView_Annotate_ThisPage().Displayed();
				}
			}), Input.wait30);
			driver.waitForPageToBeReady();
			getDocView_Annotate_ThisPage().isElementAvailable(8);
			getDocView_Annotate_ThisPage().waitAndClick(10);
			base.passedStep("Added Annotation to document sucessfully");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while performing non audio annotation." + e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method for verifying annotation is added to document.
	 * @param alreadyExistsLayers : alreadyExistsLayers is integer value that
	 *                            annotaion layers count of previous document.
	 */
	public void verifyAnnotationAddedToDocument(int alreadyExistsLayers) throws InterruptedException {
		try {
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			getDocView_AnnotateIcon().isElementAvailable(8);
			getDocView_AnnotateIcon().waitAndClick(10);
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getDocView_Annotate_ThisPage().Displayed();
				}
			}), Input.wait30);
			driver.waitForPageToBeReady();
			int annotation = getAnnotations().FindWebElements().size();
			if (annotation > alreadyExistsLayers) {
				base.passedStep("Annotation is displayed to document successfully");
			} else {
				base.failedStep("Annotation is not displayed to document");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while performing non audio annotation." + e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method for editing annotation layer of current document.
	 */
	public void editAnnotationLayerOfCurrentDocument() throws InterruptedException {
		try {
			driver.scrollPageToTop();
			driver.waitForPageToBeReady();
			List<WebElement> annotations = getAnnotations().FindWebElements();
			driver.waitForPageToBeReady();
			annotations.get(0).click();
			base.passedStep("Editing annotation layer of current document successfull");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while editing annotation layer of current document." + e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method for deleting remark from doc view.
	 * @param remark : remark is String value that any remark value need to delete.
	 */
	public void deleteReamark(String remark) {
		try {
			driver.waitForPageToBeReady();
			String panelItemValue = null;
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getNonAudioRemarkBtn().isElementAvailable(10);
				}
			}), Input.wait60);
			getNonAudioRemarkBtn().waitAndClick(10);
			driver.waitForPageToBeReady();
			List<WebElement> remarkPanelItems = getRemarkPanelItems().FindWebElements();
			List<WebElement> deleteRmarks = getDeleteRemarks().FindWebElements();
			for (int i = 0; i < remarkPanelItems.size(); i++) {
				driver.waitForPageToBeReady();
				panelItemValue = remarkPanelItems.get(i).getText().trim();
				if (panelItemValue.equalsIgnoreCase(remark)) {
					driver.waitForPageToBeReady();
					deleteRmarks.get(i).click();
					getAlertConfrimButton().waitAndClick(7);
					base.passedStep("Remark : " + remark + " deleted successfully");
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while deleting remark from doc view." + e.getMessage());
		}

	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void verifyErrorMsgInDocView() {
		driver.waitForPageToBeReady();
		base.stepInfo("Performing action from parent window");

		getSaveAndNextButton().waitAndClick(5);

		boolean flag = getCodingFormValidErrorMeta().Displayed();
		softAssertion.assertTrue(flag);
		if (getCodingFormValidErrorMeta().Displayed()) {
			// base.stepInfo("Coding form validation in check group error message
			// displayed");
			base.passedStep("Error message displayed successfully");
		} else {
			return;
		}
	}

	/**
	 * @Author Mohan Created on 13/12/2021
	 * @Description To verify ViewDocument in Analytics panel
	 */
	public void verifyViewDocumentWithoutSelectingDocs() {

		base.waitForElement(getDocView_Analytics_liDocumentThreadMap());
		getDocView_Analytics_liDocumentThreadMap().waitAndClick(3);
		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(3);

		try {
			softAssertion.assertEquals(getDocView_Analytics_Thread_ViewDocument().Enabled().booleanValue(), true);
			base.passedStep("View Document action is disable when document is not selected from thread map");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("View Document action is not disable when document is not selected from thread map");
		}

		base.waitForElement(getDocView_Analytics_FamilyTab());
		getDocView_Analytics_FamilyTab().waitAndClick(3);
		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(3);

		try {
			softAssertion.assertEquals(getDocView_FamilyViewInDocView().Enabled().booleanValue(), true);
			base.passedStep("View Document action is disable when document is not selected from family member");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("View Document action is not disable when document is not selected from family member");
		}

		getDocView_Analytics_liDocumentThreadMap().waitAndClick(3);
		base.waitForElement(getDocView_Analytics_NearDupeTab());
		getDocView_Analytics_NearDupeTab().waitAndClick(5);
		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(3);

		try {
			softAssertion.assertEquals(getViewDocumentNearDupe().Enabled().booleanValue(), true);
			base.passedStep("View Document action is disable when document is not selected from NearDupe");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("View Document action is not disable when document is not selected from NearDupe");
		}

		getDocView_Analytics_liDocumentThreadMap().waitAndClick(3);
		base.waitForElement(getDocView_Analytics_liDocumentConceptualSimilarab());
		getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(5);
		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(3);

		try {
			softAssertion.assertEquals(getDocView_Analytics_Concept_ViewDocument().Enabled().booleanValue(), true);
			base.passedStep("View Document action is disable when document is not selected from Conceptual");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("View Document action is not disable when document is not selected from Conceptual");
		}
		softAssertion.assertAll();

	}

	/**
	 * @Author Mohan Created on 13/12/2021
	 * @Description To perform ViewDocument in ThreadMap
	 */
	public void performViewDocumentFromThreadMapTab() {

		driver.waitForPageToBeReady();
		String docId1 = getDocView_CurrentDocId().getText();

		base.waitForElement(getDocView_Analytics_liDocumentThreadMap());
		getDocView_Analytics_liDocumentThreadMap().waitAndClick(10);

		for (int i = 1; i <= 1; i++) {
			base.waitForElement(getDocView_Analytics_ThreadedDocument_Doc(i));
			getDocView_Analytics_ThreadedDocument_Doc(i).waitAndClick(5);
		}

		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(15);

		base.waitForElement(getDocView_Analytics_Thread_ViewDocument());
		getDocView_Analytics_Thread_ViewDocument().waitAndClick(3);

		String docId2 = getDocView_CurrentDocId().getText();

		try {
			softAssertion.assertNotEquals(docId1, docId2);
			base.passedStep("Viewed document is loaded in doc view panel successfully with" + docId2);
		} catch (Exception e) {
			base.failedStep("Documents are not loaded in doc view panel");
		}

	}

	/**
	 * @Author Mohan Created on 13/12/2021
	 * @Description To verify Coding form panel is loaded for the viewed document
	 * 
	 */
	public void verifyCodingFormPanelIsLoadedAfterDocsAreViewed() {

		try {
			base.waitForElement(getDocView_Analytics_CodingFormPanel());
			softAssertion.assertTrue(getDocView_Analytics_CodingFormPanel().isElementPresent());
			base.passedStep("Coding form is loaded for the viewed document");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Coding form is not loaded for the viewed document");
		}

	}

	/**
	 * @Author Mohan Created on 13/12/2021
	 * @Description To verify Meta data panel is loaded for the viewed document
	 * 
	 */
	public void verifyMetaDataPanelIsLoadedAfterDocsAreViewed() {

		try {
			base.waitForElement(getDocView_Analytics_MetaDataPanel());
			softAssertion.assertTrue(getDocView_Analytics_MetaDataPanel().isElementPresent());
			base.passedStep("Meta Data Panel is loaded for the viewed document");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Meta Data Panel is not loaded for the viewed document");
		}

	}

	/**
	 * @Author Mohan Created on 13/12/2021
	 * @Description To popout metadata panel in the DocView.
	 */
	public void popOutMetaDataPanel() {

		try {

			driver.scrollPageToTop();

			base.waitForElement(getGearIcon());
			getGearIcon().Click();

			base.waitForElement(getDocView_MetaDataPopOut());
			getDocView_MetaDataPopOut().Click();
			System.out.println("Meta Data Panel is popout successfully");
			base.passedStep("Meta Data Panel is popout successfully");

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Meta Data Panel is not popout");
		}
	}

	/**
	 * @Author Mohan Created on 13/12/2021
	 * @Description To popout CodingForm panel in the DocView.
	 */
	public void popOutCodingFormPanel() {

		try {

			driver.scrollPageToTop();

			if (getGearIcon().isDisplayed()) {
				base.waitForElement(getGearIcon());
				getGearIcon().Click();
			} else {
				getDocView_CodingFormPopOut().isElementPresent();
				System.out.println("Gear Icon Is already clicked");
			}

			base.waitForElement(getDocView_CodingFormPopOut());
			getDocView_CodingFormPopOut().Click();
			System.out.println("CodingForm Panel is popout successfully");
			base.passedStep("CodingForm Panel is popout successfully");

		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Meta Data Panel is not popout");
		}
	}

	/**
	 * @author Vijaya.Rani date: 13/12/2021 Modified date:N/A
	 * @Description:Check CodeSame As is present in nearDupe
	 */
	public void checkCodeSameAsIsPresentInNearDupe() {
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_Analytics_NearDupeTab());
		getDocView_Analytics_NearDupeTab().waitAndClick(10);

		base.waitForElement(getDocView_Analytics_NearDupe_Doc(1));
		getDocView_Analytics_NearDupe_Doc(1).waitAndClick(5);

		getDocView_ChildWindow_ActionButton().waitAndClick(10);
		if (geDocView_NearDupe_CodeSameAsIcon().isDisplayed()) {

			base.failedStep("The Code Same As Action is  Avaliable in Near Dupes");
		}

		else {
			base.passedStep("The Code Same As Action is Not Avaliable in Near Dupes");
		}
	}

	/**
	 * @author Vijaya.Rani date: 13/12/2021 Modified date:N/A
	 * @Description:Check CodeSame As is Disable Family Member
	 */
	public void checkCodeSameAsIsDisableFamilyMember() {
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_Analytics_FamilyTab());
		getDocView_Analytics_FamilyTab().waitAndClick(10);

		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(10);
		base.waitForElement(geDocView_MiniList_CodeSameAsIcon());

		softAssertion.assertFalse(geDocView_MiniList_CodeSameAsIcon().getWebElement().isSelected());
		if (getCodeSameAsLast().getWebElement().isSelected()) {

			base.failedStep("Code same as last clickable in Analytical Panel");
		} else {
			base.passedStep("Code same as last not clcikable in Analytical Panel");
		}

	}

	/**
	 * @author Vijaya.Rani 24/11/21 NA Modified date: NA Modified by:NA
	 * @description To verify thread docs more than 20 Cols/rows
	 */
	public void selectDocsFromMiniDocsListAndCheckTheThreadedDocsSize() throws InterruptedException {
		driver.waitForPageToBeReady();
		base.waitForElementCollection(getDocView_Analytics_ThreadedDocs());

		base.passedStep("The Threaed Documents having Columns are :" + getDocView_Analytics_ThreadedDocs().size()
				+ "which is more than 20 docs");

	}

	/**
	 * @author Vijaya.Rani date: 14/12/2021 Modified date:N/A
	 * @Description:select Docs Mini DocList And Coding Form SaveButton
	 */
	public void selectDocsMiniDocListAndCodingFormSaveButton() {
		driver.waitForPageToBeReady();
		reusableDocView.editingCodingFormWithSaveButton();

	}

	/**
	 * @author: Indium-Baskar
	 * @Description:This method used to verify coding form appear r not
	 * 
	 */
	public void noDefaultCodingForm() {
		driver.waitForPageToBeReady();
		base.stepInfo("Performing action in parent window");
		boolean flag = getNoDefaultCodingForm().isDisplayed();
		softAssertion.assertTrue(flag);
		if (getCodingFormSaveButton().isElementAvailable(1) && getSaveAndNextButton().isElementAvailable(1)) {
			base.passedStep("Save and SaveAndNext button not displayed in  docview page");
		} else {
			base.failedStep("Save and saveAndNext button displayed");
		}
		base.stepInfo("Performing action in child window");
		reusableDocView.clickGearIconOpenCodingFormChildWindow();
		String parent = reusableDocView.switchTochildWindow();
		boolean flagOne = getNoDefaultCodingForm().isDisplayed();
		softAssertion.assertTrue(flagOne);
		if (getCodingFormSaveButton().isElementAvailable(1) && getSaveAndNextButton().isElementAvailable(1)) {
			base.passedStep("Save and SaveAndNext button not displayed in  docview page");
		} else {
			base.failedStep("Save and saveAndNext button displayed");
		}
		reusableDocView.childWindowToParentWindowSwitching(parent);
		softAssertion.assertAll();
	}

	/**
	 * @author: Indium-Baskar
	 * @Description:This method used to verify coding form appear or not
	 * 
	 */

	public void vefiyingSavedStamp(String fieldText, String comment) {
		driver.waitForPageToBeReady();
		String docId = getVerifyPrincipalDocument().getText();
		reusableDocView.editCodingFormAndSaveWithStampColour(fieldText, Input.stampSelection, comment);
		reusableDocView.lastAppliedStamp(Input.stampSelection);
		driver.waitForPageToBeReady();
		String docSecId = getVerifyPrincipalDocument().getText();
		softAssertion.assertNotEquals(docId, docSecId);
		if (!docId.equals(docSecId)) {
			base.passedStep("Cursor navigated to next document from minidoclist");
		} else {
			base.failedStep("Curosr not navigate to next document");
		}
		getDociD(docId).waitAndClick(5);
		reusableDocView.uncompleteButtonValidate();
		softAssertion.assertAll();

	}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for click stamp button
	public void codingStampButton() {
		driver.waitForPageToBeReady();
		base.waitForElement(getCodingFormStampButton());
		getCodingFormStampButton().waitAndClick(5);
	}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for click stamp button
	public void codingStampPopUpSaveButton() {
		driver.waitForPageToBeReady();
		base.waitForElement(getCodingStampSaveBtn());
		getCodingStampSaveBtn().waitAndClick(5);
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
		base.waitForElement(getDocument_CommentsTextBox());
		getDocument_CommentsTextBox().SendKeys(comment);
	}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for click coding stamp button and text box filling & assign colour  
	public void codingStampcancelButtonValidate(String fieldValue, String colour) {
		driver.waitForPageToBeReady();
		base.waitForElement(getCodingFormStampButton());
		getCodingFormStampButton().waitAndClick(5);
		base.waitForElement(getCodingStampTextBox());
		getCodingStampTextBox().SendKeys(fieldValue);
		base.waitForElement(getDrp_StampColour());
		getDrp_StampColour().waitAndClick(5);
		base.waitForElement(getAssignedColour(colour));
		getAssignedColour(colour).waitAndClick(5);
		base.waitForElement(getCodingStampCancel());
		getCodingStampCancel().waitAndClick(5);
		driver.waitForPageToBeReady();
		boolean flag = getPopUpVerify().GetAttribute("style").contains("display: none");
		System.out.println(flag);
		if (flag) {
			base.passedStep("Coding stamp not saved");
			base.passedStep("Coding stamp PopUp closed after clicking cancel button");
		} else {
			base.failedStep("Coding stamp saved");
		}
	}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for verify unComplete to click complete button in random order
	public void unCompleteButtonToCompleteBtn() {
		driver.waitForPageToBeReady();
		for (String docId : completedDoc) {
			base.waitForElement(getDociD(docId));
			getDociD(docId).waitAndClick(5);
			base.waitForElement(getUnCompleteButton());
			getUnCompleteButton().waitAndClick(5);
			softAssertion.assertTrue(getCompleteDocBtn().isDisplayed());
		}
		driver.waitForPageToBeReady();
		if (getverifyCodeSameAsLast().isElementAvailable(5)) {
			base.failedStep("Tick mark icon not displayed");
		} else {
			base.passedStep("Tick mark removed for document after clicking the uncomplete button");
		}
		softAssertion.assertAll();
	}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for click pencil icon for last applied colour
	public void pencilGearicon(String icon) {
		driver.waitForPageToBeReady();
		base.waitForElement(getEditStampSettings());
		getEditStampSettings().waitAndClick(5);
		base.waitForElement(getCodingStampLastIcon(icon));
		getCodingStampLastIcon(icon).waitAndClick(5);
	}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for deleteing stamp colour
	public void deleteStampColour(String icon) {
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		pencilGearicon(icon);
		base.waitForElement(getDeletePopUpAssignedColour());
		getDeletePopUpAssignedColour().waitAndClick(5);
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
		getDrp_StampColour().waitAndClick(5);
		base.waitForElement(getAssignedColour(colour));
		getAssignedColour(colour).waitAndClick(5);
		base.waitForElement(getCodingStampSaveBtn());
		getCodingStampSaveBtn().waitAndClick(5);
	}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for click coding stamp button and text box filling & assign colour  
	public void stampColourSelection(String fieldValue, String colour) {
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
//	Reusable method for click for last applied colour
	public void lastAppliedStamp(String icon) {
		driver.waitForPageToBeReady();
		base.waitForElement(getCodingStampLastIcon(icon));
		getCodingStampLastIcon(icon).waitAndClick(10);
	}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for click view coding button
	public void clickViewCodingButton() {
		driver.waitForPageToBeReady();
		base.waitForElement(getViewCoding());
		getViewCoding().waitAndClick(5);
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
	 * @author Mohan.Venugopal created date: 15/12/2021
	 * @Description: Verify error msg when select docs form conceptual and action as
	 *               CodeSameAs
	 */
	public void verifyErrorMsgForActionCodeSameAsInConceptual() {

		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_Analytics_liDocumentConceptualSimilarab());
		getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(5);

		base.waitForElement(getDocView_Analytics_Conceptual_FirstDoc());
		getDocView_Analytics_Conceptual_FirstDoc().waitAndClick(5);
		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(5);

		base.waitForElement(getDocView_Analytics_Concept_Similar_CodeSameAs());
		getDocView_Analytics_Concept_Similar_CodeSameAs().waitAndClick(15);

		base.VerifyErrorMessage("Some selected docs are not in this Assignment. Please uncheck them and try again.");
	}

	/**
	 * @author Mohan.Venugopal created date: 17/12/2021
	 * @Description: Verify that EmailAuthorNameAndAddress field should be displayed
	 *               on the metadata panel of doc view
	 */
	public void verifyEmailAuthorNameAndAddressFieldInMetaDataPanel() {

		driver.waitForPageToBeReady();

		driver.scrollingToBottomofAPage();

		String value = getMetaDataPaginationNextButton().GetAttribute("class");
		while (!getMetaDataPaginationNextButton().GetAttribute("class").contains("disabled"))

		{
			driver.waitForPageToBeReady();
			Boolean status = getDocView_MetaDataPanel_PopupEmailAuthorNameAndAddress().isDisplayed();
			if (status == true) {

				base.passedStep(
						"EmailAuthorNameAndAddress field is displayed on metadata pop up and the field is a concatenation of EmailAuthorName and  EmailAddress fields");

				getDocView_MetaDataPanel_Popup_CloseButton().waitAndClick(4);
				base.stepInfo("Expected Field found in the displayed page ");
				break;
			} else {
				driver.scrollingToBottomofAPage();
				getAssgnPaginationNextButton().waitAndClick(3);
				base.stepInfo("Expected field not found in the displayed page ");
			}
		}
	}

	/**
	 * @author Vijaya.Rani 17/12/21 NA Modified date: NA Modified by:NA
	 * @description To edit coding form and complete
	 */
	public void editDefaultCodingFormCompleteBtn() {

		driver.scrollPageToTop();
		String currentDocId1 = getDocView_CurrentDocId().getText();
		base.stepInfo("The Current Display DocId Is" + currentDocId1 + "");
		driver.waitForPageToBeReady();
		base.waitForElement(getResponsiveCheked());
		getResponsiveCheked().Click();
		base.waitForElement(getNonPrivilegeRadio());
		getNonPrivilegeRadio().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocument_CommentsTextBox().Displayed() && getDocument_CommentsTextBox().Enabled();
			}
		}), Input.wait30);
		getDocument_CommentsTextBox().SendKeys("Editing and click complete button");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCompleteDocBtn().Enabled() && getCompleteDocBtn().Visible();
			}
		}), Input.wait30);

		driver.scrollPageToTop();
		String docRemark1 = getDocument_CommentsTextBox().getText();
		System.out.println(docRemark1);

		getCompleteDocBtn().Click();
		base.VerifySuccessMessage("Document completed successfully");
		driver.waitForPageToBeReady();

		String currentDocId2 = getDocView_CurrentDocId().getText();
		base.stepInfo("The Current Display DocId Is" + currentDocId2 + "");
		softAssertion.assertNotEquals(currentDocId1, currentDocId2);
		base.passedStep(
				"Next document of the main viewing document from the mini doc list is viewed in doc view panel successfully");
		softAssertion.assertAll();

		driver.waitForPageToBeReady();
		base.waitForElement(getLastCodeSameAsIcon());
		getLastCodeSameAsIcon().waitAndClick(10);
		base.VerifySuccessMessage("Coded as per the coding form for the previous document");
		String docRemark2 = getDocument_CommentsTextBox().getText();
		System.out.println(docRemark2);

		// if (docRemark1.contains("Editing")) {
		if (docRemark1 == docRemark2) {
			base.failedStep("The two MiniDocs Documents comments are Same");
		} else {
			base.passedStep("The two MiniDocs Documents comments are Not Same");
		}

	}

	/**
	 * @Author Vijaya.Rani Created on 17/12/2021
	 * @Description To perform CodeSame Conceputually docs in the DocView Test Case
	 *              id: RPMXCON-51071
	 *
	 */
	public void performCodeSameAsForConceptualDocuments() throws InterruptedException {

		JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_Analytics_liDocumentConceptualSimilarab());
		Point p = getDocView_Analytics_liDocumentConceptualSimilarab().getWebElement().getLocation();
		je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");

		base.waitForElement(getDocView_Analytics_liDocumentConceptualSimilarab());
		getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);

		base.waitForElement(getDocView_Analytics_Conceptual_ThirdDoc());
		getDocView_Analytics_Conceptual_ThirdDoc().waitAndClick(10);
		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(10);

		base.waitForElement(getDocView_Analytics_Concept_Similar_CodeSameAs());
		getDocView_Analytics_Concept_Similar_CodeSameAs().waitAndClick(10);
		base.VerifySuccessMessage("Code same performed successfully.");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return geDocView_ConceptuallySimilar_CodeSameAsIcon().Displayed();
			}
		}), Input.wait30);
		softAssertion.assertEquals(geDocView_ConceptuallySimilar_CodeSameAsIcon().Displayed().booleanValue(), true,
				"1");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getConceptDocumentWhichHasCodeSameIcon().Displayed();
			}
		}), Input.wait30);

		codeSameDocumentid = getConceptDocumentWhichHasCodeSameIcon().getText();

		softAssertion.assertEquals(getCodeSameIconMiniDocList().Displayed().booleanValue(), true, "2");

	}

	/**
	 * @Author Vijaya.Rani Created on 17/12/2021
	 * @Description perfrom Coding Stamp Selection testcase id: RPMXCON-51071
	 *
	 */
	public void perfromCodingStampSelection(String colour) throws InterruptedException {

		driver.waitForPageToBeReady();
		base.waitForElement(getCodingFormStampButton());
		base.waitTillElemetToBeClickable(getCodingFormStampButton());
		getCodingFormStampButton().waitAndClick(5);
		base.stepInfo("The Coding Stamp Popup window is open");
		base.waitForElement(getCodingStampTextBox());
		getCodingStampTextBox().SendKeys("NewColour");
		base.waitForElement(getDrp_StampColour());
		base.waitTillElemetToBeClickable(getDrp_StampColour());
		getDrp_StampColour().Click();
		base.waitTillElemetToBeClickable(getAssignedColour(colour));
		getAssignedColour(colour).Click();
		base.waitForElement(getCodingStampSaveBtn());
		base.waitTillElemetToBeClickable(getCodingStampSaveBtn());
		getCodingStampSaveBtn().waitAndClick(5);
		base.VerifySuccessMessage("Coding Stamp saved successfully");

	}

	/**
	 * @Author Vijaya.Rani Created on 17/12/2021
	 * @Description perfrom Last CodeSameA sIcon testcase id: RPMXCON-51071
	 *
	 */
	public void perfromLastCodeSameAsIcon() throws InterruptedException {

		driver.waitForPageToBeReady();
		base.waitForElement(getLastCodeSameAsIcon());
		base.waitTillElemetToBeClickable(getLastCodeSameAsIcon());
		getLastCodeSameAsIcon().waitAndClick(10);
		base.VerifySuccessMessage("Coded as per the coding form for the previous document");

	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void performCodeSameForFamilyMembersDocumentsWithScroll() throws InterruptedException {

		base.waitForElement(getDocView_Analytics_FamilyTab());
		getDocView_Analytics_FamilyTab().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDocView_Analytics_ChildWindow_FamilyTab_Firstdoc().Displayed();
			}
		}), Input.wait60);
		getDocView_Analytics_ChildWindow_FamilyTab_Firstdoc().ScrollTo();
		getDocView_Analytics_ChildWindow_FamilyTab_Firstdoc().waitAndClick(10);

		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().ScrollTo();
		getDocView_ChildWindow_ActionButton().waitAndClick(15);

		getDocView_Analytics_Family_Member_CodeSameAs().waitAndClick(15);

		base.VerifySuccessMessage("Code same performed successfully.");

		for (int i = 1; i <= 1; i++) {
			base.waitForElement(geDocView_FamilyMembers_CodeSameAsIcon(i));
			softAssertion.assertEquals(geDocView_FamilyMembers_CodeSameAsIcon(i).Displayed().booleanValue(), true);
			try {
				if (geDocView_FamilyMembers_CodeSameAsIcon(i).Displayed()) {
					base.passedStep("CodeAsSame icon is displayed for the selected docs ");
				}
			} catch (Exception e) {
				base.failedStep("CodeAsSame icon is not displayed for the selected docs");
				UtilityLog.info("Verification failed due to " + e.getMessage());
			}

		}

		codeSameDocumentid = getFamilyMembersWhichHasCodeSameIcon().getText();

	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void performCodeSameForFamilyMembersDocumentsReviewer() throws InterruptedException {

		base.waitForElement(getDocFileType());
		getDocFileType().waitAndClick(5);
		// driver.scrollingToBottomofAPage();
		base.waitForElement(getDocView_Analytics_FamilyTab());
		// driver.scrollingToElementofAPage(getDocView_Analytics_FamilyTab());
		// getDocView_Analytics_FamilyTab().ScrollTo();
		getDocView_Analytics_FamilyTab().waitAndClick(10);

		base.waitForElement(getDocView_Analytics_ChildWindow_FamilyTab_doc(2));
		getDocView_Analytics_ChildWindow_FamilyTab_doc(2).waitAndClick(10);

		base.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(15);

		getDocView_Analytics_Family_Member_CodeSameAs().waitAndClick(15);

		base.VerifySuccessMessage("Code same performed successfully.");

	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void completedDocEditableCheck() {
		SoftAssert softAssertion = new SoftAssert();
		boolean flag;
		driver.waitForPageToBeReady();
		// base.waitForElement(techIssueInput());
		flag = codingFormDisableCheck().GetAttribute("style").contains("block");
		driver.waitForPageToBeReady();
		if (flag) {
			softAssertion.assertTrue(true);
			base.passedStep("completed doument is not editable");

		} else {
			softAssertion.assertTrue(false);
			base.failedStep("completed document is editable");

		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void enableShowCompletedDoc() {

		driver.waitForPageToBeReady();
		base.waitForElement(getReviewGearIcon());
		getReviewGearIcon().waitAndClick(5);
		base.waitForElement(getShowCompletedDocsToggle());
		getShowCompletedDocsToggle().waitAndClick(5);
		base.waitForElement(getMiniDocListConfirmationButton("Save"));
		getMiniDocListConfirmationButton("Save").waitAndClick(5);
		driver.waitForPageToBeReady();
		base.waitForElementCollection(getMiniDocListDocIdText());
		driver.waitForPageToBeReady();
		get1stDocinMiniDocView().waitAndClick(5);
		base.stepInfo("enabled show completed document");
	}

	/**
	 * @author Indium-Baskar
	 */

//  Reusable method for verify  coding stamp button should display	
	public void stampButtonShouldDisplay() {
		driver.waitForPageToBeReady();
		boolean flag;
		base.waitForElement(getCodingFormStampButton());
		flag = getCodingFormStampButton().isDisplayed();
		if (flag) {
			base.passedStep("Coding stamp displayed");
		} else {
			base.failedStep("Coding stamp not displayed");
		}
	}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for complete display or not
	public void completeBtnShouldDisplay() {
		driver.waitForPageToBeReady();
		boolean flag;
		base.waitForElement(getCompleteDocBtn());
		flag = getCompleteDocBtn().isDisplayed();
		if (flag) {
			base.passedStep("Complete displayed");
		} else {
			base.failedStep("Complete not displayed");
		}
	}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for verifying complete button
	public void tickMarkForCompleteBtn() {
		boolean flag = getverifyCodeSameAsLast().isDisplayed();
		softAssertion.assertTrue(flag);
		base.passedStep("Tick mark displayed for completed document");
		softAssertion.assertAll();
	}

	public void codingStampShouldNotDisplay() {
		driver.waitForPageToBeReady();
		if (getCodingFormStampButton().getWebElement().isDisplayed()) {
			base.failedStep("Coding stamp displayed after impersoante as Reviewer");
		} else {
			base.passedStep("Coding stamp not displayed after impersoante as Reviewer");
		}
	}

	/**
	 * @author Indium-Baskar date: 17/12/2021 Modified date:N/A
	 * @Description: This method used to verify coding stamp display for parent
	 *               window
	 * 
	 */

	public void verifyCodingStampDisplayParent() {
		driver.waitForPageToBeReady();
		base.stepInfo("Performing action in parent window");
		base.stepInfo("Validation for non-audio document");
		reusableDocView.stampButtonShouldDisplay();
		for (int i = 20; i <= 20; i++) {
			getClickDocviewID(i).waitAndClick(5);
			driver.waitForPageToBeReady();
		}
		base.stepInfo("Validation for audio document");
		reusableDocView.stampButtonShouldDisplay();
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
//	Reusable method for switch child window
	public void switchToNewWindow(int windowNumber) {
		Set<String> s = driver.getWebDriver().getWindowHandles();
		Iterator<String> ite = s.iterator();
		int i = 1;
		while (ite.hasNext() && i < 10) {
			String popupHandle = ite.next().toString();
			driver.switchTo().window(popupHandle);
			System.out.println("Window title is : " + driver.getTitle());
			if (i == windowNumber)
				break;
			i++;
		}
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
	 * @author Indium-Baskar date: 17/12/2021 Modified date:N/A
	 * @Description: This method used to validate save and next for audio file
	 */
	public void validateSaveAndNextForAudioDocs() {
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String prnDoc = getVerifyPrincipalDocument().getText();
		base.waitForElement(getDocView_IconPlay());
		getDocView_IconPlay().waitAndClick(5);
		editingCodingFormWithSaveAndNextChild();
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String prnSecDoc = getVerifyPrincipalDocument().getText();
		if (!prnDoc.equals(prnSecDoc)) {
			base.passedStep("Cursor navigated to next document");
		} else {
			base.failedStep("Cursor not navigated to next documnet");
		}
		clickGearIconOpenCodingFormChildWindow();
		base.waitForElement(getDocView_HdrMinDoc());
		getDocView_HdrMinDoc().waitAndClick(5);
		switchToNewWindow(3);
		editingCodingFormWithSaveAndNextChild();
		switchToNewWindow(2);
		driver.waitForPageToBeReady();
		String prnSecDocCf = getVerifyPrincipalDocument().getText();
		if (!prnSecDoc.equals(prnSecDocCf)) {
			base.passedStep("Cursor navigated to next document");
		} else {
			base.failedStep("Cursor not navigated to next documnet");
		}
		closeWindow(2);
		closeWindow(1);
		switchToNewWindow(1);
	}

	/**
	 * @Author Vijaya.Rani Created on 20/12/2021
	 * @Description To select docs from Analytics Family Member Tab and verify code
	 *              same as icon
	 * 
	 */
	public void selectDocsFromFamilyMemberTabAndActionCodeSameAsThirdDocs() {

		try {
			driver.waitForPageToBeReady();
			JavascriptExecutor je = (JavascriptExecutor) driver.getWebDriver();
			driver.waitForPageToBeReady();
			Point p = getDocView_Analytics_FamilyTab().getWebElement().getLocation();
			je.executeScript("window.scroll(" + p.getX() + "," + (p.getY() - 400) + ");");
			getDocView_Analytics_FamilyTab().ScrollTo();
			getDocView_Analytics_FamilyTab().waitAndClick(10);

			for (int i = 3; i <= 3; i++) {
				base.waitForElement(getDocView_Analytics_FamilyMember_DocCheckBox(i));
				getDocView_Analytics_FamilyMember_DocCheckBox(i).waitAndClick(10);
			}
			base.waitForElement(getDocView_ChildWindow_ActionButton());
			getDocView_ChildWindow_ActionButton().waitAndClick(10);

			base.waitForElement(getDocView_FamilyCodeSameAs());
			getDocView_FamilyCodeSameAs().waitAndClick(10);

			base.VerifySuccessMessage("Code same performed successfully.");

			// verify Code Same as Link
			try {
				if (getDocView_Analytics_FamilyMember_CodeSameLink().getWebElement().isDisplayed())
					softAssertion.assertEquals(
							getDocView_Analytics_FamilyMember_CodeSameLink().Displayed().booleanValue(), true);
				base.passedStep(
						"Code same icon is displayed for the selected documents and documents from Family Member are unchecked successfull");
			} catch (Exception e) {
				base.failedStep("Selected Document is not having code as same icon under Family Member tabss");
			}

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Code Same As Icon is not displayed");
		}
	}

	/**
	 * @author Indium-Baskar
	 */

//	Reusable method for editing the coding form only with Save button click
//	Edited coding form save with save and next button
	public String editingCodingFormWithSaveAndNextButton() {
		driver.waitForPageToBeReady();
		base.waitForElement(getVerifyPrincipalDocument());
		String principalDocs = getVerifyPrincipalDocument().getText().trim();
		base.waitForElement(getResponsiveCheked());
		getResponsiveCheked().waitAndClick(5);
		base.waitForElement(getNonPrivilegeRadio());
		getNonPrivilegeRadio().waitAndClick(5);
		base.waitForElement(getDocument_CommentsTextBox());
		getDocument_CommentsTextBox().SendKeys("Edited and save with save button");
		base.waitForElement(getSaveAndNextButton());
		getSaveAndNextButton().waitAndClick(5);
		base.stepInfo("Excepted Message:Document completed successfully");
		return principalDocs;
	}

	/**
	 * @param colour
	 * @throws InterruptedException
	 * @Author Vijaya.Rani Created on 21/12/2021
	 * @Description To perform Coding Form And Stamp DocView Test Case id:
	 *              RPMXCON-52118
	 * 
	 */
	public void performCodeFormAndStampInDocView(String colour, String lastIcons, String commands)
			throws InterruptedException {

		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		base.waitForElement(getVerifyPrincipalDocument());
		getVerifyPrincipalDocument().waitAndClick(20);

		driver.waitForPageToBeReady();

		String expectedValue = getVerifyPrincipalDocument().getText().trim();
		base.waitForElement(getDocView_HistoryButton());
		getDocView_HistoryButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		String expectedValues = getHistoryDrp_Dwn().getText().trim();
		if (expectedValue.equals(expectedValues)) {
			base.waitForElement(getHistoryDrp_Dwn());
			getHistoryDrp_Dwn().waitAndClick(5);
		}
		base.passedStep("User selected the document from history drop down");
		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_DefaultViewTab());
		softAssertion.assertEquals(getDocView_DefaultViewTab().Enabled().booleanValue(), true);
		base.passedStep("Document displaying in default view page");

		base.waitForElement(getMiniDocListRightArrow());
		if (getMiniDocListRightArrow().isElementPresent()) {

			base.passedStep("The MiniDocList is Navigate to Next Document");

		} else {
			base.failedStep("The MiniDocList is Not Navigate to Next Document");
		}

		editingCodingFormWithSaveAndNextButton();

		driver.waitForPageToBeReady();
		base.waitForElement(getDocView_DefaultViewTab());
		softAssertion.assertEquals(getDocView_DefaultViewTab().Enabled().booleanValue(), true);
		base.passedStep("Document displaying in default view page");

		base.waitForElement(getStampBlueColour());
		getStampBlueColour().waitAndClick(10);

		base.waitForElement(getSaveAndNextButton());
		getSaveAndNextButton().waitAndClick(10);

		base.waitForElement(getMiniDocListRightArrow());
		if (getMiniDocListRightArrow().isElementPresent()) {

			base.passedStep("The MiniDocList is Navigate to Next Document");

		} else {
			base.failedStep("The MiniDocList is Not Navigate to Next Document");
		}

		// delete stamp Color
		// reusableDocView.deleteStampColour(lastIcons);
		driver.waitForPageToBeReady();

	}

	/**
	 * @author Indium-Baskar
	 */

//	Reusable method for edit coding form with stamp colour and save it.
	public void editCodingFormAndSaveWithStamp(String fieldValue, String stampColour) {
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
	 * @Author Vijaya.Rani Created on 21/12/2021
	 * @Description To perform CodeSame As Last in the DocView Test Case id:
	 *              RPMXCON-52124
	 * 
	 */
	public void performCodeSameAsLastInDocView() {

		driver.waitForPageToBeReady();
		base.waitForElement(getCodeSameAsLast());

		getCodeSameAsLast().waitAndClick(20);
		base.passedStep("doc view 'Code same as last' should not be clickable");

	}

	/**
	 * @author Gopinath
	 * @Description : Method for verifying weather term is not present in persistent
	 *              hit panel.
	 * @param searchString : searchString is string value that search string value.
	 */
	public void verifyPersistentPanelNotContainsTerm(String searchString) throws InterruptedException {

		try {
			getPersistantHitEyeIcon().isElementAvailable(10);

			if (getPersistantHitEyeIcon().isElementAvailable(3)) {
				getPersistantHitEyeIcon().waitAndClick(10);
			} else {
				getAudioPersistantHitEyeIcon().waitAndClick(10);
			}
			getHitPanels().isElementAvailable(10);
			int numOfPanels = getHitPanels().size();
			System.out.println("numOfPanels" + (numOfPanels - 1));
			for (int i = 2; i <= numOfPanels; i++) {
				if (getTermInHitPanels(i).getText().contains(searchString)) {
					System.out.println("Found " + searchString);
					base.failedStep(searchString + " term is not present in persistent hit panel.");
				}
			}
			base.passedStep(searchString + " term is not present in persistent hit panel.");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while verifying weather term is not present in persistent hit panel."
					+ e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method for verify user navigated to next document.
	 */
	public void verifyUserNavigatedToNextDocument() {
		try {
			driver.scrollPageToTop();
			String firstRowDocSId = getFirstDocIdOnMiniDocList().getText().trim();
			String secondRowDocId = getSecondDocIdOnMiniDocList().getText().trim();
			String docID = getDocView_CurrentDocId().getText().trim();
			System.out.println(docID);
			if (docID.equalsIgnoreCase(firstRowDocSId)) {
				base.passedStep("User currently on first document doc view");
			}
			driver.scrollPageToTop();
			getDocView_Next().isElementAvailable(10);
			getDocView_Next().Click();
			driver.waitForPageToBeReady();
			getDocView_CurrentDocId().isElementAvailable(10);
			docID = getDocView_CurrentDocId().getText().trim();
			if (docID.equalsIgnoreCase(secondRowDocId)) {
				base.passedStep("User navigated to next document succssfully");
			} else {
				base.failedStep("Failed to navigate to next document");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while verifying user navigated to next document." + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method for verify image tab is enabled.
	 */
	public void verifyImageTabIsEnabled() {
		try {
			Boolean flag = getDocView_TextTab().Enabled();
			if (flag)
				base.passedStep("Image tab is enabled successfully");
			else
				base.failedStep("Image tab is not enabled");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while verifying image tab is enabled." + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method for click on image tag.
	 */
	public void clickOnImageTab() {
		try {
			driver.scrollPageToTop();
			getDocView_ImagesTab().isElementAvailable(10);
			getDocView_ImagesTab().Click();
			base.passedStep("Clicked on image tab");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while verifying image tab is enabled." + e.getMessage());
		}
	}

}
