package pageFactory;
import static org.testng.Assert.assertTrue;

import java.awt.Graphics;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
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
    
    public Element getDocView_info(){ return driver.FindElementById("totalRecords"); }
    public Element getAddComment1(){ return driver.FindElementById("1_textarea"); }
    public Element getSaveDoc(){ return driver.FindElementById("Save"); }
    public Element getNonAudioRemarkBtn(){ return driver.FindElementById("remarks-btn"); }
    public Element getSelectRemarkDocArea(){ return driver.FindElementByXPath("//div[contains(@id,'pccViewerControl')]//*[name()='svg']//*[name()='text'][1]"); }
    public Element getAddRemarkbtn(){ return driver.FindElementById("addRemarks"); }
    public Element getRemarkTextArea(){ return driver.FindElementById("txt_remark"); }
    public Element getRemarkDeleteIcon(){ return driver.FindElementByXPath("//*[@id='remarksSaveCancelControls']//i[1]"); }
    public Element getSaveRemark(){ return driver.FindElementByXPath("(//span[@id='remarksSaveCancelControls']/i[2])[1]"); }
    public Element getCompleteDocBtn(){ return driver.FindElementById("btnDocumentComplete"); }
    public ElementCollection getElements(){ return driver.FindElementsByXPath("//*[@class='a-menu']"); }
   
//Audio-----------------------------------------------------------
    public Element getDocView_IconFileType(){ return driver.FindElementById("icofiletype"); }
    public Element getDocView_TextFileType(){ return driver.FindElementById("txtspanfileType"); }
    public Element getDocView_IconPlay(){ return driver.FindElementByCssSelector("*[title='Play']"); }
    public Element getDocView_IconPause(){ return driver.FindElementByCssSelector("*[title='Pause']"); }
    public Element getDocView_RunningTime(){ return driver.FindElementByCssSelector(".jp-current-time:nth-of-type(2)"); }
    public Element getDocView_IconStop(){ return driver.FindElementByCssSelector("*[title='Stop']"); }
    public Element getDocView_IconPrev(){ return driver.FindElementByCssSelector("#btnPreviousHit i"); }
    public Element getDocView_IconNext(){ return driver.FindElementByCssSelector("#btnNextHit i"); }
    public Element getDocView_IconBack(){ return driver.FindElementByCssSelector("#btnBackword i"); }
    public Element getDocView_IconForward(){ return driver.FindElementByCssSelector("#btnForword i"); }
    public Element getDocview_RedactionsTab(){ return driver.FindElementByXPath("//a[contains(.,'REDACTIONS')]"); }
    public Element getDocview_RedactionsTab_Add(){ return driver.FindElementByXPath(".//*[@id='new']//i"); }
    public Element getDocview_AddRedactions_StartTime(){ return driver.FindElementByXPath("//*[@id='audioGrid']//td[1]//input"); }
    public Element getDocview_AddRedactions_EndTime(){ return driver.FindElementByXPath("//*[@id='audioGrid']//td[2]//input"); }
    public Element getDocview_Audio_StartTime(){ return driver.FindElementByXPath("//*[@class='jp-current-time start']"); }
    public Element getDocview_Audio_EndTime(){ return driver.FindElementByXPath("//*[@class='jp-duration end']"); }
    public Element getDocview_Redactionstags_Value(){ return driver.FindElementByXPath("//ul[@class='multiselect-container dropdown-menu']/li[1]//input"); }
    public Element getDocview_AudioRedactions(){ return driver.FindElementById("ddlAudioRedactionTags"); }
    public Element getDocview_Redactionstags_Delete(){ return driver.FindElementById("btnDelete"); }
    public Element getDocview_Audio_Downloadbutton(){ return driver.FindElementById("liAudioDocumentTypeDropDown"); }
    public Element getDocview_Audio_DownloadFile(){ return driver.FindElementByXPath("//a[contains(text(),'MP3')]"); }
    public Element getDocview_Keywork_Next(){ return driver.FindElementById("NextHit_you"); }
    public Element getDocview_Keywork_Previous(){ return driver.FindElementById("PrevHit_you"); }
    public Element getDocview_ButtonNO(){ return driver.FindElementById("bot2-Msg1"); }
    public Element getDocview_ButtonYes(){ return driver.FindElementById("bot1-Msg1"); }
    public Element getSaveButton(){ return driver.FindElementById("btnSave"); }
    //non audio reduction page
    public Element getDocView_RedactThisPage(){ return driver.FindElementByXPath("//*[@id='redactCurrentPage_divDocViewer']"); }
    public Element getDocView_SelectReductionLabel(){ return driver.FindElementById("ddlRedactionTagsForPopup"); }
    public Element getRedactionTag_SaveButton() {return driver.FindElementByXPath("//*[@id=\"btnSave\"]"); }//added Element
    public Element getDocView_SaveReduction1(int i){ return driver.FindElementByXPath("(//div[@class='ui-dialog-buttonset']//button[1])["+i+"]"); }
    public Element getDocView_SaveReduction(){ return driver.FindElementByXPath("//div[@class='ui-dialog-buttonset']//button[1]"); }
    public Element getDocView_SaveReductionNew(){ return driver.FindElementByXPath("//*[@id='btnSave']"); }
    
   //remaks objects
    public Element getAdvancedSearchAudioRemarkIcon(){ return driver.FindElementByXPath("//*[@id='remarks-btn-audio-view']/a/span/i[2]"); }
    public Element getAdvancedSearchAudioRemarkPlusIcon(){ return driver.FindElementByXPath(".//*[@id='audAddRemark']/i"); }
    public Element getAdvancedSearchAudioRemarkTime(){ return driver.FindElementById("txtRemarkTime"); }
    public Element getDocView_RemarkTextField(){ return driver.FindElementById("txt_remark"); }
    public Element getDocView_Remark_RemarkPlusIcon(){ return driver.FindElementById("addRemarks"); }
    public Element getDocView_Remark_CheckIcon(){ return driver.FindElementByXPath(".//*[@id='remarksSaveCancelControls']/i[2]"); }
    public Element getDocView_Remark_DeleteIcon(){ return driver.FindElementByXPath("//*[@id='newRemarks']//i[@class='fa fa-trash-o']"); }
    public Element getDocView_SavedRemarkText(){ return driver.FindElementByXPath(".//*[@id='newRemarks']//p"); }
    public Element getDocViewTest_1stElement(){ return driver.FindElementByXPath(".//*[@id='SearchDataTable']/tbody/tr[1]/td[2]"); }
    public Element getDocView_AddRemarkIcon(){ return driver.FindElementByXPath(".//*[@id='remarks-btn']/a"); }
    public Element getDocView_AudioRemark_DeleteButton(){ return driver.FindElementByXPath(".//*[starts-with(@onclick,'DeleteReviewerRemarks')]"); }
    public Element getDocView_AudioRemark_SaveButton(){ return driver.FindElementByXPath(".//*[@onclick='AddReviewerRemarks()']"); }
    public Element getAudioComment(){ return driver.FindElementByXPath("//textarea[@name='COMMENT' and @id='1_textarea']"); }
    public Element getDocumentViewer_DocView_SaveBtn(){ return driver.FindElementById("Save"); }
    //Persistent Hits Validation--------------------------------------------------------------
    
    public ElementCollection getHitPanels(){ return driver.FindElementsByXPath("//div[starts-with(@class,'search-tab col-md-2 clsAccusoftViewerHit')]/div"); }
    public Element getTermInHitPanels(int num){ return driver.FindElementByXPath("//div[starts-with(@class,'search-tab col-md-2 clsAccusoftViewerHit')]/div["+num+"]/p"); }
    public Element getDocView_CFName(){ return driver.FindElementById("lblCodingFormName"); }
  //added on 04-01
    public Element getDocView_TextTab(){ return driver.FindElementById("liDocumentTxtView"); }
    public Element getDocView_Textarea(){ return driver.FindElementById("divViewerText"); } 
    public Element getDocView_Mini_ActionButton(){ return driver.FindElementById("btnAction"); }
    public Element getDocView_Mini_FolderAction(){ return driver.FindElementById("FolderMINIDOCLIST"); }
    public Element getDocView_FolderTab(){ return driver.FindElementById("liDocumentFolder"); }
    public Element getDocView_MiniDoc_SelectRow(int rowno){ return driver.FindElementByXPath(".//*[@id='SearchDataTable']/tbody/tr["+rowno+"]/td[1]//input/following-sibling::i"); }
    public Element getDocView_FolderTab_Expand(){ return driver.FindElementByXPath(".//*[@id='-1']/i"); }
    public Element getFolderFromList(String Foldername){ return driver.FindElementByXPath(".//*[@class='jstree-children']//a[text()='"+Foldername+"']"); }
    public Element getDocView_NumTextBox(){ return driver.FindElementById("txtBoxPageNum"); }
    public Element getDocView_MiniDocListIds(int rowno){ return driver.FindElementByXPath(".//*[@id='SearchDataTable']/tbody/tr["+rowno+"]"); }
    public Element getDocView_Persistent_PrevHit(){ return driver.FindElementByXPath("//*[starts-with(@id,'PrevHit')]"); }
    public Element getDocView_Persistent_NextHit(){ return driver.FindElementByXPath("//*[starts-with(@id,'NextHit')]"); }
    public Element getDocView_Annotate_Rectangle(){ return driver.FindElementById("highlightRectangle_divDocViewer"); }
    public Element getDocView_Annotate_ThisPage(){ return driver.FindElementById("highlightCurrentPage_divDocViewer"); }
    public Element getRemarkDeletetIcon(){ return driver.FindElementByXPath("//*[@id='remarksSaveCancelControls']/i[@class='fa fa-trash-o']"); }
    public Element getRemarkEditIcon(){ return driver.FindElementByXPath("//*[@id='remarksSaveCancelControls']/i[@class='fa fa-pencil']"); }
    public Element getDocView_Redact_ThisPage(){ return driver.FindElementByXPath(".//*[@id='redactCurrentPage_divDocViewer']/a/i"); }
    public Element getDocView_Annotate_TextArea(){ return driver.FindElementByCssSelector("rect[style*='#FFFF00']"); }
    public Element getDocView_Annotate_DeleteIcon(){ return driver.FindElementById("btn_delete"); }
    public Element getDocView_Redact_TextArea(){ return driver.FindElementById("divDocViewer"); }
    public Element getDocView_EditMode(){ return driver.FindElementById("wEdit"); }
    public Element getDocView_HdrMinDoc(){ return driver.FindElementByXPath(".//*[@id='HdrMiniDoc']/div/div/i"); }
    public Element getDocView_HdrCoddingForm(){ return driver.FindElementByXPath(".//*[@id='HdrCoddingForm']/div/div/i"); }
    public Element getDocView_HdrMetaData(){ return driver.FindElementByXPath(".//*[@id='HdrMetaData']/div/div/i"); }
    public Element getDocView_HdrAnalytics(){ return driver.FindElementByXPath(".//*[@id='HdrAnalytics']/div/div/i"); }
    
    //All Tabs-----------------------------------------------------------
    public Element getDocView_DefaultViewTab(){ return driver.FindElementById("aliDocumentDefaultView"); }
    public Element getDocView_ImagesTab(){ return driver.FindElementById("liDocumentProductionView"); }
    public Element getDocView_TranslationTab(){ return driver.FindElementById("liDocumentTranslationsView"); }
    public Element getDocView_MiniDoclistPanel(){ return driver.FindElementById("miniDocListWrap"); }
    public Element getDocView_AnalyticsPanel(){ return driver.FindElementById("leftPalette_AnalyticsPanel"); }
    public Element getDocView_CodingFormPanel(){ return driver.FindElementById("divParentCodingForm"); }
    public Element getDocView_MetadataPanel(){ return driver.FindElementById("rightPalette_MetaData"); }
    public Element getDocView_HitsTogglePanel(){ return driver.FindElementByXPath("//*[@class='toggletText light-bg']"); }
    public Element getDocView_ToogleLabel(){ return driver.FindElementByXPath("//*[@class='toggletText light-bg']//label/label"); }
    public Element getDocView_ToggleButton(){ return driver.FindElementById("EnableSearchTerm"); }
    public Element getDocView_DefaultTabNative(){ return driver.FindElementByXPath(".//*[@id='aliDocumentDefaultView']/span[contains(.,'NATIVE')]"); }
    public Element getDocView_Analytics_ChildWindow_FamilyTab_Firstdoc(){ return driver.FindElementByXPath("//*[@id='dtDocumentFamilyMembers']//tr[1]//input[starts-with(@id,'chkDocFamily')]/following-sibling::i"); }
    public Element geDocView_FamilyMem_CodeSameAsIcon(){ return driver.FindElementByXPath(".//*[@id='dtDocumentFamilyMembers']//i[@class='fa fa-link']"); }
    public Element geDocView_Threaded_CodeSameAsIcon(){ return driver.FindElementByXPath(".//*[@id='dtDocumentThreadedDocuments']//i[@class='fa fa-link ']"); }
    public Element geDocView_NearDupe_CodeSameAsIcon(){ return driver.FindElementByXPath(".//*[@id='dtDocumentNearDuplicates']//i[@class='fa fa-link']"); }
    public Element geDocView_Concept_CodeSameAsIcon(){ return driver.FindElementByXPath(".//*[@id='dtDocumentConceptuallySimilar']//i[@class='fa fa-link']"); }
    public Element getDocView_ChildWindow_ActionButton(){ return driver.FindElementById("btnAnalyticAction"); }
    public Element getDocView_Analytics_NearDupeTab(){ return driver.FindElementById("liDocumentNearDupe"); }
    public Element getDocView_Analytics_liDocumentConceptualSimilarab(){ return driver.FindElementById("liDocumentConceptualSimilar"); }
    public Element getDocView_Analytics_FamilyTab(){ return driver.FindElementById("liDocumentFamilyMember"); }
    public Element getDocView_MiniDoc_ChildWindow_Selectdoc(int rowno){ return driver.FindElementByXPath("//*[@id='SearchDataTable']//tr["+rowno+"]/td[1]/label"); }
    public Element getDocView__ChildWindow_Mini_FolderAction(){ return driver.FindElementById("liMiniDocListBulkFolder"); }
    public Element getDocView_Analytics_Thread_ViewDocument(){ return driver.FindElementById("liViewDocumentTHREADEDDOCUMENTS"); }
    public Element getDocView_Analytics_Thread_Folder(){ return driver.FindElementById("FolderAnalyticAction"); }
    public Element getDocView_Analytics_Thread_CodeSameAs(){ return driver.FindElementById("liCodeSameAsThreaded"); }
    public Element getDocView_Analytics_Thread_RemoveCodeSameAs(){ return driver.FindElementById("liRemoveCodeSameAsThreaded"); }
    public Element getDocView_Analytics_Thread_ViewDoclist(){ return driver.FindElementById("DocListThreaded"); }
    public Element getDocView__ChildWindow_Mini_CodeSameAs(){ return driver.FindElementById("liCodeSameAsMiniDocList"); }
    public Element getDocView__ChildWindow_Mini_RemoveCodeSameAs(){ return driver.FindElementById("liRemoveCodeSameAsMiniDocList"); }
    public Element getDocView_MiniDocList(){ return driver.FindElementById("divMiniDocList"); }
    public Element getDocView_FolderActionPopup(){ return driver.FindElementById("divBulkAction"); }
    public Element getDocView_ThreadedChild_Selectalldoc(){ return driver.FindElementById("threadMapSelectRows"); }
    public Element getDocView_NearDupeIcon(){ return driver.FindElementById("iconNearDupe"); }
    public Element getDocView_NearDupe_DocID(){ return driver.FindElementById("spanOriginalDocId"); }
    public Element getDocView_DocumentViewer_DocID(){ return driver.FindElementByCssSelector("#SearchDataTable tbody tr[class*='rowNumber_0'] td:nth-of-type(2)"); }
    public Element getDocView_Analytics_LoadMoreButton(){ return driver.FindElementById("LoadMore"); }
    public Element getDocView_Analytics_Threaded(){ return driver.FindElementByXPath(".//*[@id='theThreadMapUI']//*[@class='threadedEmailRowNoRecords']"); }
    public Element getDocView_Analytics_Threaded_FirstDoc(){ return driver.FindElementByXPath("//*[@id='threadedCheckBoxRow']//th[2]//input[contains(@id,'threadedDocumentCheckboxHeader')]/following-sibling::i"); }
    public Element getDocView_MiniDoc_CodeSameIcon(){ return driver.FindElementByXPath(".//*[@id='SearchDataTable']/tbody/tr[1]/td[1]//i[contains(@class,'fa fa-link')]"); }
    public Element getDocView_HistoryButton(){ return driver.FindElementById("btnDocHistory"); }
    public Element getDocView_Historydropdown(){ return driver.FindElementByXPath("//*[@id='ulDocViewHistory']/li[2]"); }
    public Element getDocView_Analytics_Threadedicon(){ return driver.FindElementByXPath("//*[@id='threadedDocumentIdRow']//th[@class='thread_current gray-bg-1 ']"); }
    public Element getDocView_Metadata_EmailInclusiveScore(){ return driver.FindElementByXPath("//*[@id='MetaDataDT']//td[contains(text(),'EmailInclusiveScore')]/following-sibling::td"); }
    public Element getDocView_Metadata_EmailInclusiveReason(){ return driver.FindElementByXPath("//*[@id='MetaDataDT']//td[contains(text(),'EmailInclusiveReason')]/following-sibling::td"); }
    
   //All icons
    public Element getPersistantHitEyeIcon(){ return driver.FindElementByXPath("//*[@id='search-btn']//a");}
    public Element getDocView_RedactIcon(){ return driver.FindElementByXPath("//*[@id='gray-tab']//a"); }
    public Element getDocView_AnnotateIcon(){ return driver.FindElementByXPath("//*[@id='yellow-tab']//a"); }
    public Element getDocView_IconDownload(){ return driver.FindElementByXPath("//*[@id='liDocumentTypeDropDown']//a"); }
    public Element getDocView_Zoomout(){ return driver.FindElementByXPath("//*[@id='zoomOut_divDocViewer']//a"); }
    public Element getDocView_Slider(){ return driver.FindElementById("slider_divDocViewer"); }
    public Element getDocView_ZoomIn(){ return driver.FindElementByXPath("//*[@id='zoomIn_divDocViewer']//a"); }
    public Element getDocView_Rotateright(){ return driver.FindElementByXPath("//*[@id='rotateRight_divDocViewer']//a"); }
    public Element getDocView_Rotateleft(){ return driver.FindElementByXPath("//*[@id='rotateLeft_divDocViewer']//a"); }
    public Element getDocView_Print(){ return driver.FindElementByXPath("//*[@id='print_divDocViewer']//a"); }
    public Element getDocView_Thumbnail(){ return driver.FindElementByXPath("//*[@id='thumbnails_divDocViewer']//a"); }
    public Element getDocView_ResetZoom(){ return driver.FindElementByXPath("//*[@id='fitContent_divDocViewer']//a"); }
    public Element getDocView_SearchButton(){ return driver.FindElementByXPath("//*[@class='sodIcon']//a"); }
    //Added on 28 May*********Analytics - Family************
    public Element getDocView_FamilyBulkFolder(){ return driver.FindElementById("liFamilyBulkFolder"); }
    public Element getDocView_FamilyCodeSameAs(){ return driver.FindElementById("liCodeSameAsFamilyMember"); }
    public Element getDocView_FamilyRemoveCodeSameAs(){ return driver.FindElementById("liRemoveCodeSameAsFamilyMember"); }
    public Element getDocView_FamilyViewInDoclist(){ return driver.FindElementById("liViewInDocListFamilyMember"); }
    public Element getDocView_FamilyViewInDocView(){ return driver.FindElementById("liViewDocumentFamilyMember"); }
   
    //Sort Order
    public Element getDocView_ReviewModeText(){ return driver.FindElementById("lblModeText"); }
    public Element getDocView_ConfigMinidoclist(){ return driver.FindElementById("miniDocListConfig"); }
    public Element getDocView_ConfigMinidoclist_ManualSort(){ return driver.FindElementById("lblBtnRadioManual"); }
    public Element getDocView_ConfigMinidoclist_OptimizedSort(){ return driver.FindElementByXPath("//*[@id='rbOptimized']/following-sibling::i"); }
    public ElementCollection getDocView_Config_Selectedfield(){ return driver.FindElementsByXPath("//*[@id='sortable2PickColumns']/li"); }
    public Element getDocView_SelectedFileds(int rowno){ return driver.FindElementByXPath("//*[@id='SearchDataTable_wrapper']/div[3]/div[1]//table/thead/tr/th["+rowno+"]"); }
    public Element getDocView_MiniDocFields_Remove() { return driver.FindElementByXPath(".//*[@id='sortable2PickColumns']/li[contains(.,'FamilyMemberCount')]/i[2]"); }
    public Element getDocView_MiniDocFields_ConfigSaveButton() { return driver.FindElementByXPath("//div[@class='ui-dialog-buttonset']//button[2]"); }
    public ElementCollection getDocView_AssignmentProgress(){ return driver.FindElementsByXPath(".//*[@id='divAssigmnetProgress']/div[@class='progress progress-micro']"); }
    
    public Element getDocView_SaveWidgetButton(){ return driver.FindElementById("wSave"); }
    public Element getDocView_Analytics_Collapsebutton(){ return driver.FindElementByXPath("//*[@class='fa fa-minus']"); }
    public Element getDocView_NearDupe_Selectdoc(){ return driver.FindElementByXPath(".//*[@id='dtDocumentNearDuplicates']/tbody//td[1]//input/following-sibling::i"); }
    public Element getDocView_NearViewInDoclist(){ return driver.FindElementById("liViewInDocListNearDupe"); }
    public ElementCollection getDocView_ConceptualDocs(){ return driver.FindElementsByXPath(".//*[@id='dtDocumentConceptuallySimilar']//tr"); }
    
    
    //Added on 29 may****Navigation***********
    public Element getDocView_First(){ return driver.FindElementById("btnFirst"); }
    public Element getDocView_Previous(){ return driver.FindElementById("btnPrevDocView"); }
    public Element getDocView_Last(){ return driver.FindElementById("btnLast"); }
    public Element getDocView_Next(){ return driver.FindElementById("btnNextDocView"); }
  
    //Added by sure 04/09/19
    public Element getDocView_defaultView(){ return driver.FindElementByXPath("//span[contains(text(),'Default')]"); }
    public Element getDocView_textView(){ return driver.FindElementByXPath("(//span[contains(text(),'TEXT')])[2]"); }
    public Element getDocView_imagesView(){ return driver.FindElementByXPath("//span[contains(text(),'Images')]"); }
    public Element getDocView_translationsView(){ return driver.FindElementByXPath("//span[contains(text(),'Translations')]"); }
    
    public Element getDocView_CurrentDocId(){ return driver.FindElementById("activeDocumentId"); }
    public Element getDocView_textArea(){ return driver.FindElementByXPath("//div[contains(@id,'pccViewerControl')]//*[name()='svg']//*[name()='text'][1]"); }
  
    public Element getDocView_Redact_Rectangle(){ return driver.FindElementById("blackRectRedact_divDocViewer"); }
    public WebElement getDocView_Redactrec_textarea(){ return driver.FindElementById("ig0level5").getWebElement(); }
    public Element getDocView_Redactedit_save(){ return driver.FindElementById("btnRedactionTag"); }
    public Element getDocView_Redactedit_selectlabel(){ return driver.FindElementById("ddlRedactionTags"); }
    public Element getDocView_DocId(String docid){ return driver.FindElementByXPath("//*[@id='SearchDataTable']//td[contains(text(),'"+docid+"')]"); }
    public Element getAudioPersistantHitEyeIcon(){ return driver.FindElementByXPath("//*[@id='search-btn-audio-view']//a");}
    public Element getDocView_Audio_Hit(){ return driver.FindElementByXPath("//*[@id='divAudioPersistentSearch']/div/p[1]"); }
    
    //Doc view page redaction
    public Element getPreRedaction(){ return driver.FindElementByCssSelector("#PrevAllRedaction");}
    //added by shilpi
    
    
    public DocViewPage(Driver driver){
    	
        this.driver = driver;
       
       
        softAssertion= new SoftAssert(); 
        base = new BaseClass(driver);
        sp = new SessionSearch(driver);
        this.driver.getWebDriver().get(Input.url+ "DocumentViewer/DocView");
    }
    
    
    
    public String getPersistentHit(String searchString) throws InterruptedException {
    	
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getPersistantHitEyeIcon().Visible()  ;}}), Input.wait60);
		 Thread.sleep(5000);
		 
		 try {
		getPersistantHitEyeIcon().waitAndClick(10);
		 }
		 catch(Exception e) {
			 getAudioPersistantHitEyeIcon().waitAndClick(10);
		 }
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getHitPanels().Visible()  ;}}), Input.wait30);
		 
		int numOfPanels = getHitPanels().size();
		String Phit = "NULL";
		System.out.println("numOfPanels"+(numOfPanels-1));
		Boolean flag = false;
		for (int i = 2; i <= numOfPanels; i++) {
			if(getTermInHitPanels(i).getText().contains(searchString)){
				System.out.println("Found "+searchString);
				flag = true;
				Phit = getTermInHitPanels(i).getText();
			break;
			}
		
		}
		//Assert.assertTrue(flag);
	//	driver.getWebDriver().navigate().refresh();
		return Phit;
	}
    
    public String getAudioPersistentHit(String searchString) throws InterruptedException {
    	
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getAudioPersistantHitEyeIcon().Visible()  ;}}), Input.wait60);
			 getAudioPersistantHitEyeIcon().waitAndClick(10);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getHitPanels().Visible()  ;}}), Input.wait30);
		 
		int numOfPanels = getHitPanels().size();
		String Phit = "NULL";
		System.out.println("numOfPanels"+(numOfPanels-1));
		Boolean flag = false;
		for (int i = 1; i <= numOfPanels; i++) {
			if(getTermInHitPanels(i).getText().contains(searchString)){
				System.out.println("Found "+searchString);
				flag = true;
				Phit = getTermInHitPanels(i).getText();
			break;
			}
		
		}
		//Assert.assertTrue(flag);
	//	driver.getWebDriver().navigate().refresh();
		return Phit;
	}
   
    public void addCommentToNonAudioDoc(String comment) throws Exception {
    	
    	driver.getWebDriver().get(Input.url+ "DocumentViewer/DocView");
    	driver.waitForPageToBeReady();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAddComment1().Visible()  ;}}), Input.wait30);   
    	getAddComment1().Clear();
    	getAddComment1().SendKeys(comment);
       	getCompleteDocBtn().Click();   	
       	
    	base.VerifySuccessMessage("Document completed successfully");
	}
   
    public void addRemarkNonAudioDoc(String remark) {
    	//To make sure we are in basic search page
    	driver.getWebDriver().get(Input.url+ "Search/Searches");
    	driver.waitForPageToBeReady();
    	
    	driver.getWebDriver().get(Input.url+ "DocumentViewer/DocView");
    	driver.waitForPageToBeReady();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getNonAudioRemarkBtn().Visible()  ;}}), Input.wait30); 
    	//getDocView_DocId("ID00000125").waitAndClick(20);
      
    	getNonAudioRemarkBtn().waitAndClick(5);
    	
    	   try {
    			getDocView_Remark_DeleteIcon().waitAndClick(5);
    			base.getPopupYesBtn().waitAndClick(5);
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			System.out.println("Remark not present");
    		}
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSelectRemarkDocArea().Visible()  ;}}), Input.wait30); 
//    	System.out.println(getSelectRemarkDocArea().getText());
    try {
		Thread.sleep(4000);
	} catch (InterruptedException e) {
		
		e.printStackTrace();
	}
    /*Actions newBuilder = new Actions(driver.getWebDriver());
	newBuilder.moveByOffset(6/2,0).clickAndHold().moveByOffset(6,0).release().build().perform();
   // getSelectRemarkDocArea().Click(); */
    	
    	WebElement element_node = driver.getWebDriver().findElement(By.xpath("//div[contains(@id,'pccViewerControl')]//*[name()='svg']//*[name()='text'][1]"));
         JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();
        jse.executeScript("arguments[0].style.border='3px solid red'", element_node);
         ((JavascriptExecutor) driver.getWebDriver()).executeScript("arguments[0].setAttribute('style', arguments[1]);", element_node, "color: yellow; border: 2px solid yellow;");
        
    	/*new Actions(driver.getWebDriver()).keyDown(driver.getWebDriver().findElement(By.xpath("//div[contains(@id,'pccViewerControl')]//*[name()='svg']//*[name()='text'][1]")), Keys.TAB).build().perform();
    	Actions builder = new Actions(driver.getWebDriver());
    	builder.moveToElement(driver.getWebDriver().findElement(By.xpath("//div[contains(@id,'pccViewerControl')]//*[name()='svg']//*[name()='text'][1]"))).build().perform();
    	//Integer width = element.getWebElement().getSize().getWidth();*/
    	Actions newBuilder = new Actions(driver.getWebDriver());
    	newBuilder.moveByOffset(6/2,0).clickAndHold().moveByOffset(6,0).release().build().perform();
	   getSelectRemarkDocArea().Click();
    
    	driver.scrollPageToTop();
    	getAddRemarkbtn().Click();
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getRemarkTextArea().Visible();}}), Input.wait30);  
    	getRemarkTextArea().SendKeys(remark);
    	getSaveRemark().Click();
    	//successMsgSaveDoc();

	}
    
    public void completeNonAudioDocument() {
    	driver.scrollPageToTop();
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getCompleteDocBtn().Visible()  ;}}), Input.wait30);   
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
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getDocView_IconFileType().Visible() ;}}), Input.wait120);
    	Assert.assertEquals(getDocView_IconFileType().getText().toString(), "M");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getDocView_TextFileType().Visible() ;}}), Input.wait120);
    	Assert.assertEquals(getDocView_TextFileType().getText().toString(), "MP3 VERSION");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getDocView_IconPlay().Displayed() ;}}), Input.wait30);
    	getDocView_IconPlay().waitAndClick(30);
    	//Thread.sleep(10000);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getDocView_IconPause().Displayed() ;}}), Input.wait30);
    	getDocView_IconPause().waitAndClick(30);
    	Thread.sleep(3000);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getDocView_RunningTime().Visible() ;}}), Input.wait30);
    	String runningTime = getDocView_RunningTime().getText();
		
    	if(Integer.parseInt(runningTime.substring(6, 8))>=5){
			System.out.println("The total time for audio played is greater than 5 i.e. :" + runningTime.substring(6, 8));
		UtilityLog.info("The total time for audio played is greater than 5 i.e. :" + runningTime.substring(6, 8));
    	}else{
			System.out.println("The total time for audio played is not displayed correctly");
			UtilityLog.info("The total time for audio played is not displayed correctly");
		}
	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getDocView_IconStop().Displayed() ;}}), Input.wait30);
    	getDocView_IconStop().Click();
    	String runningTime1 = getDocView_RunningTime().getText();
		if(Integer.parseInt(runningTime1.substring(6, 8))==0){
			System.out.println("The total time for audio played is greater than 5 i.e. :" + runningTime.substring(6, 8));
		UtilityLog.info("The total time for audio played is greater than 5 i.e. :" + runningTime.substring(6, 8));
		}else{
			System.out.println("The total time for audio played is not displayed correctly");
			UtilityLog.info("The total time for audio played is not displayed correctly");
		}

	}
public void audioRemark(String remark) throws InterruptedException, ParseException {
    	
        //adding remarks
    	 
    	  //click on remarks button
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getAdvancedSearchAudioRemarkIcon().Visible() ;}}), Input.wait30);
    	 getAdvancedSearchAudioRemarkIcon().waitAndClick(30);
        
         try {
           	 //Delete any existing remarks if any
        	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			 getDocView_AudioRemark_DeleteButton().Enabled() ;}}), Input.wait30);
        	 getDocView_AudioRemark_DeleteButton().Click();
            
        	 //click on yes button
    		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				 getDocview_ButtonYes().Visible() ;}}),Input.wait30);
    		 getDocview_ButtonYes().Click();
    	    
    		 base.VerifySuccessMessage("Record Deleted Successfully");
        	 Thread.sleep(10000);
               }
         catch (Exception e)
         {
        	 System.out.println("No Remarks exist'");
        	 UtilityLog.info("No Remarks exist'");
         }
			
		//click on + icon to add remarks
         getAdvancedSearchAudioRemarkPlusIcon().Click();
         
         //Get Audio duration start and End time first
        String Audiostarttimeremark=  getAdvancedSearchAudioRemarkTime().GetAttribute("value");
        System.out.println(Audiostarttimeremark);
        UtilityLog.info(Audiostarttimeremark);
        DateFormat df = new SimpleDateFormat("HH:mm");
        //Get Audio duration start and End time first
        String Audiostarttime=getDocview_Audio_StartTime().getText();
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
         
         //Enter time in remarks field
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getAdvancedSearchAudioRemarkTime().Visible() ;}}),Input.wait30);
         getAdvancedSearchAudioRemarkTime().SendKeys(newTime2);
	    
         //Enter text in field
         driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		 getDocView_RemarkTextField().Visible() ;}}),Input.wait30);
         getDocView_RemarkTextField().SendKeys(remark);
         
          //click on save button
         getDocView_AudioRemark_SaveButton().Click();
         
         //verify success message
         base.VerifySuccessMessage("Record added Successfully");
        	 
         }
public void audioReduction() throws InterruptedException, ParseException {
	//adding redactions
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getDocview_RedactionsTab().Visible() ;}}), Input.wait30);
	getDocview_RedactionsTab().waitAndClick(30);
	
	
	 try {
       	 //Delete any existing redaction if any
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getDocview_Redactionstags_Delete().Visible() ;}}),Input.wait30);
		 getDocview_Redactionstags_Delete().Click();
	    	
     	 //click on No button
	/*	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getDocview_ButtonNO().Visible() ;}}),Input.wait30);
		 getDocview_ButtonNO().Click();
	*/    
		 //click on yes button
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getDocview_ButtonYes().Visible() ;}}),Input.wait30);
		 getDocview_ButtonYes().waitAndClick(10);
	    
		 base.VerifySuccessMessage("Record Deleted Successfully");
		 base.CloseSuccessMsgpopup();
           }
     catch (Exception e)
     {
    	 System.out.println("No Redactions exist'");
    	 UtilityLog.info("No Redactions exist'");
     }
	 
	//click on + icon to add redactions
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getDocview_RedactionsTab_Add().Visible() ;}}), Input.wait30);
	 getDocview_RedactionsTab_Add().Click();
    
     //Get Audio duration start and End time first
     String Audiostarttime=getDocview_Audio_StartTime().getText();
     System.out.println(Audiostarttime);
     UtilityLog.info(Audiostarttime);
     String Audioendtime= getDocview_Audio_EndTime().getText();
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
     
     //Enter time in start field
     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		 getDocview_AddRedactions_StartTime().Visible() ;}}),Input.wait30);
     getDocview_AddRedactions_StartTime().SendKeys(newTime);
     
     //Enter time in end field
     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		 getDocview_AddRedactions_EndTime().Visible() ;}}),Input.wait30);
     getDocview_AddRedactions_EndTime().SendKeys(newTime1);
     
     //select redaction tags
     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		 getDocview_AudioRedactions().Visible() ;}}),Input.wait30);
     getDocview_AudioRedactions().selectFromDropdown().selectByIndex(1);
     Thread.sleep(2000);
    // getDocview_Redactionstags_Value().waitAndClick(10);
    
     //click on save button
     driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		 getSaveButton().Visible() ;}}),Input.wait30);
     //     driver.scrollingToBottomofAPage();
     getSaveButton().waitAndClick(30);
     
     //verify success message
     base.CloseSuccessMsgpopup();
     base.VerifySuccessMessageB("Record added Successfully");
	 System.out.println("Redaction added successfully");
	 UtilityLog.info("Redaction added successfully");
}

public void audioComment(String comments) {
	//adding comments
    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   		 getAudioComment().Visible() ;}}),Input.wait30);
    getAudioComment().Clear();
    getAudioComment().SendKeys(comments);
   
    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   		 getDocumentViewer_DocView_SaveBtn().Visible() ;}}),Input.wait30);
    getDocumentViewer_DocView_SaveBtn().waitAndClick(10);
    
   

}
public void audioDownload() {
	 //downlaod
    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   		 getDocview_Audio_Downloadbutton().Visible() ;}}),Input.wait30);
    getDocview_Audio_Downloadbutton().waitAndClick(30);
    
    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   		 getDocview_Audio_DownloadFile().Visible() ;}}),Input.wait30);
    getDocview_Audio_DownloadFile().Click();

}
    	
public void ViewTextTab() {
	
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	  		 getDocView_TextTab().Visible() ;}}),Input.wait30);
	   getDocView_TextTab().waitAndClick(15);
	   
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getDocView_Textarea().Visible() ;}}),Input.wait30);
	   String bodyText = getDocView_Textarea().getText();
	   getDocView_Textarea().Displayed();
	   System.out.println(bodyText );
	   
	   getDocView_NumTextBox().SendKeys("5");
	   getDocView_NumTextBox().Enter();
	   
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getDocView_MiniDocListIds(5).Visible() ;}}),Input.wait30);
	   Assert.assertTrue(getDocView_MiniDocListIds(5).Displayed());
	   
	    }
public void VerifyFolderTab(final String folderName,int rowno) throws InterruptedException {
	   
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		  		 getDocView_MiniDoc_SelectRow(rowno).Visible() ;}}),Input.wait60);
	   getDocView_MiniDoc_SelectRow(rowno).waitAndClick(15);
	   
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		  		 getDocView_Mini_ActionButton().Visible() ;}}),Input.wait30);
	   getDocView_Mini_ActionButton().Click();
	   
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		  		 getDocView_Mini_FolderAction().Visible() ;}}),Input.wait30);
	   getDocView_Mini_FolderAction().Click();
	   
	   sp.getSelectFolderExisting(folderName).waitAndClick(15);
	   	 
	   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   		    	sp.getContinueCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
	   	 sp.getContinueButton().Click();
	   	 
	   	 final BaseClass bc = new BaseClass(driver);
	     final int Bgcount = bc.initialBgCount();
	       
	   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   		    	sp.getFinalCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
	   	 sp.getFinalizeButton().Click();
	   	 
	   	 base.VerifySuccessMessage("Records saved successfully");
	   	 
	   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			bc.initialBgCount() == Bgcount+1  ;}}), Input.wait60); 
	   	 System.out.println("Bulk folder is done, folder is : "+folderName);
	   	 
	   	getDocView_MiniDocListIds(rowno).waitAndClick(15);
	   
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		  		 getDocView_FolderTab().Visible() ;}}),Input.wait30);
	   getDocView_FolderTab().waitAndClick(30);
	   
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		  		 getDocView_FolderTab_Expand().Visible() ;}}),Input.wait30);
	   getDocView_FolderTab_Expand().Click();
	   Thread.sleep(2000);
	   
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getFolderFromList(folderName).Displayed() ;}}),Input.wait30);
	   Assert.assertTrue(getFolderFromList(folderName).Displayed());
	   
}

public void VerifyPersistentHit(String searchString) throws InterruptedException {
	
    String hitscount = getPersistentHit(searchString);
	System.out.println("Hits are:"+hitscount);
	
   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		  		 getDocView_HitsTogglePanel().Displayed() ;}}),Input.wait30);
   
	   Assert.assertEquals("Hide Terms with 0 hits:", getDocView_ToogleLabel().getText());
	   
	
	getDocView_Persistent_PrevHit().Displayed();
	getDocView_Persistent_NextHit().Displayed();
	getDocView_Persistent_NextHit().waitAndClick(10);
	getDocView_Persistent_PrevHit().waitAndClick(10);
	
  System.out.println(getDocView_Persistent_NextHit().GetAttribute("key").toString());
	getPersistantHitEyeIcon().Click();//added
		
}

public void VerifyAudiopersistentHit(String searchString) throws InterruptedException {
	
	    String hitscount = getAudioPersistentHit(searchString);
		System.out.println("Hits are:"+hitscount);
		
	   System.out.println(getDocView_Audio_Hit().getText().toString());
		
			
	}



public void NonAudioRemarkAddEditDeletebyReviewer(String remark) throws InterruptedException {
	   	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getNonAudioRemarkBtn().Visible()  ;}}), Input.wait60);   
 	getNonAudioRemarkBtn().Click();
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getSelectRemarkDocArea().Visible()  ;}}), Input.wait30); 
 	System.out.println(getSelectRemarkDocArea().getText());
 try {
		Thread.sleep(4000);
	} catch (InterruptedException e) {
		
		e.printStackTrace();
	}
     getSelectRemarkDocArea().Click();
 
 	driver.scrollPageToTop();
 	getAddRemarkbtn().Click();
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getRemarkTextArea().Visible();}}), Input.wait30);  
 	getRemarkTextArea().SendKeys(remark);
 	getSaveRemark().Click();
 	
 	//click on next button
 	getDocView_Next().waitAndClick(10);
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getSelectRemarkDocArea().Visible()  ;}}), Input.wait30); 
 	System.out.println(getSelectRemarkDocArea().getText());
 try {
		Thread.sleep(4000);
	} catch (InterruptedException e) {
		
		e.printStackTrace();
	}
     getSelectRemarkDocArea().Click();
 
 	driver.scrollPageToTop();
 	getAddRemarkbtn().Click();
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getRemarkTextArea().Visible();}}), Input.wait30);  
 	getRemarkTextArea().SendKeys(remark);
 	getSaveRemark().Click();
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getRemarkEditIcon().Visible();}}), Input.wait30); 
 	getRemarkEditIcon().Click();
 	getRemarkTextArea().SendKeys(remark+"+Test1+");
 	getSaveRemark().Click();
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getRemarkDeletetIcon().Visible();}}), Input.wait30); 
 	getRemarkDeletetIcon().Click();
 	
 	base.getPopupYesBtn();
	}

   public void NonAudioAnnotation() throws InterruptedException {
	   	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getDocView_AnnotateIcon().Displayed()  ;}}), Input.wait30);   
		Thread.sleep(3000);
		getDocView_AnnotateIcon().waitAndClick(10);
		Thread.sleep(2000);
	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getDocView_Annotate_ThisPage().Displayed()  ;}}), Input.wait30); 
 	getDocView_Annotate_ThisPage().Click();
 	Thread.sleep(3000);
    	
 	
 	  // Verify Yellow color of highlighted text
		WebElement activeElement = driver.switchTo().activeElement();
		String HighlightedColor = driver.FindElementByCssSelector("rect[style*='#FFFF00']").GetCssValue("fill");
		System.out.println(HighlightedColor);
		
		Assert.assertEquals(HighlightedColor, "rgb(255, 255, 0)");

		/*if (HighlightedColor.equalsIgnoreCase("rgb(255, 255, 0)")) {

			System.out.println("Annotation_Highlight Yellow color displayed properly");
		} else {
			System.out.println("Annotation_Highlight Yellow color not displayed properly");
		}*/
		
		//delete annotation
		getDocView_Annotate_TextArea().waitAndClick(10);
		driver.scrollPageToTop();
		
		getDocView_Annotate_DeleteIcon().waitAndClick(5);
		base.VerifySuccessMessage("Annotation Removed successfully.");
		
}
		
		public void nonAudioPageRedaction(String redactiontag) throws InterruptedException {
		   	
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getDocView_RedactIcon().Displayed()  ;}}), Input.wait30);   
			getDocView_RedactIcon().waitAndClick(5);
			Thread.sleep(3000);
	   	
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getDocView_RedactThisPage().Displayed()  ;}}), Input.wait30); 
	    	getDocView_RedactThisPage().Click();
	    	Thread.sleep(3000);
	    	
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			getDocView_SelectReductionLabel().Displayed()  ;}}), Input.wait30); 
	    	getDocView_SelectReductionLabel().selectFromDropdown().selectByVisibleText(redactiontag);
	    	
	    	getDocView_SaveReductionNew().waitAndClick(5);
	    	
	    	base.VerifySuccessMessage("Redaction tags saved successfully.");
	    	
	    	//Verify color should change to Red of this page redaction icon
	    	System.out.println("BG color:::"+getDocView_Redact_ThisPage().GetCssValue("color"));
	    	//background-color
	    	
	    	String HighlightedColor = getDocView_Redact_ThisPage().GetCssValue("color");
			System.out.println(HighlightedColor);
			UtilityLog.info(HighlightedColor);
			
			Assert.assertEquals(HighlightedColor, "rgba(230, 70, 52, 1)");
		}
	    	

		
		
 public void NonAudioRedactionDelete(String redactiontag) throws InterruptedException {
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocView_RedactIcon().Displayed()  ;}}), Input.wait30);   
		getDocView_RedactIcon().Click();
		Thread.sleep(3000);
	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getDocView_RedactThisPage().Displayed()  ;}}), Input.wait30); 
 	getDocView_RedactThisPage().Click();
 	Thread.sleep(3000);
 	
 	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
 			getDocView_SelectReductionLabel().Displayed()  ;}}), Input.wait30); 
 	getDocView_SelectReductionLabel().selectFromDropdown().selectByVisibleText(redactiontag);
 	
 	getDocView_SaveReduction().waitAndClick(5);
 	
 	base.VerifySuccessMessage("Redaction tags saved successfully.");
 	

     	//delete redaction
     	getDocView_Redact_TextArea().waitAndClick(10);
 		
 		getDocView_Annotate_DeleteIcon().waitAndClick(5);
 		base.VerifySuccessMessage("Redaction Removed successfully.");
 		
		}
	   	
	   
	  public void VerifyTabswhenAllprefEnabled() throws InterruptedException {
		   	
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getDocView_RedactIcon().Displayed()  ;}}), Input.wait30);   
			Assert.assertTrue(getDocView_RedactIcon().Displayed());
		
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getDocView_AnnotateIcon().Displayed()  ;}}), Input.wait30);   
			Assert.assertTrue(getDocView_AnnotateIcon().Displayed());
		
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getPersistantHitEyeIcon().Displayed()  ;}}), Input.wait30);   
			Assert.assertTrue(getPersistantHitEyeIcon().Displayed());
		
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getDocView_AddRemarkIcon().Displayed()  ;}}), Input.wait30);   
			Assert.assertTrue(getDocView_AddRemarkIcon().Displayed());
		
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getDocView_DefaultViewTab().Displayed()  ;}}), Input.wait30);   
			Assert.assertTrue(getDocView_DefaultViewTab().Displayed());
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getDocView_AssignmentProgress().Displayed()  ;}}), Input.wait30);   
			Assert.assertEquals(getDocView_AssignmentProgress().Exists(),getDocView_RedactIcon().Displayed());
	/*	
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getDocView_RedactIcon().Displayed()  ;}}), Input.wait30);   
			Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().Displayed());
		
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getDocView_RedactIcon().Displayed()  ;}}), Input.wait30);   
			Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().Displayed());
		
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getDocView_RedactIcon().Displayed()  ;}}), Input.wait30);   
			Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().Displayed());
		
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getDocView_RedactIcon().Displayed()  ;}}), Input.wait30);   
			Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().Displayed());
		
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getDocView_RedactIcon().Displayed()  ;}}), Input.wait30);   
			Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().Displayed());
		
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getDocView_RedactIcon().Displayed()  ;}}), Input.wait30);   
			Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().Displayed());
		
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getDocView_RedactIcon().Displayed()  ;}}), Input.wait30);   
			Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().Displayed());
		
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getDocView_RedactIcon().Displayed()  ;}}), Input.wait30);   
			Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().Displayed());
	*/	
	  } 
			  public void VerifyTabswhenAllprefDisabled() throws InterruptedException {
				   	
				  try{
					  getDocView_RedactIcon().Displayed();
				           Assert.assertFalse(1==0);
				     }catch (org.openqa.selenium.NoSuchElementException e) {
				               System.out.println(" 'getDocView_RedactIcon' is not displayed");
				 }
					//Assert.assertFalse(getDocView_RedactIcon().Displayed());
				  try{
					  getDocView_AnnotateIcon().Displayed();
				           Assert.assertFalse(1==0);
				     }catch (org.openqa.selenium.NoSuchElementException e) {
				               System.out.println(" 'getDocView_AnnotateIcon' is not displayed");
				 }
				
				//	Assert.assertFalse(getDocView_AnnotateIcon().Displayed());
				  try{
					  getPersistantHitEyeIcon().Displayed();
				           Assert.assertFalse(1==0);
				     }catch (org.openqa.selenium.NoSuchElementException e) {
				               System.out.println(" 'getPersistantHitEyeIcon' is not displayed");
				 }
				
				//	Assert.assertFalse(getPersistantHitEyeIcon().Displayed());
				  try{
					  getDocView_AddRemarkIcon().Displayed();
				           Assert.assertFalse(1==0);
				     }catch (org.openqa.selenium.NoSuchElementException e) {
				               System.out.println(" 'getDocView_AddRemarkIcon' is not displayed");
				 }
				
				//	Assert.assertFalse(getDocView_AddRemarkIcon().Displayed());
				  try{
					  getDocView_DefaultViewTab().Displayed();
				           Assert.assertFalse(1==0);
				     }catch (org.openqa.selenium.NoSuchElementException e) {
				               System.out.println(" 'getDocView_DefaultViewTab' is not displayed");
				 }
				
				//	Assert.assertFalse(getDocView_DefaultViewTab().Displayed());
				
				  try{
					  getDocView_AssignmentProgress().Displayed();
				           Assert.assertFalse(1==0);
				     }catch (org.openqa.selenium.NoSuchElementException e) {
				               System.out.println(" 'getDocView_AssignmentProgress' is not displayed");
				 }
				/*	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							getDocView_AssignmentProgress().Displayed()  ;}}), Input.wait30);   
					Assert.assertFalse(getDocView_AssignmentProgress().Exists());*/
				  
				  try{
					  getDocView_AnalyticsPanel().Displayed();
				           Assert.assertFalse(1==0);
				     }catch (org.openqa.selenium.NoSuchElementException e) {
				               System.out.println(" 'getDocView_AnalyticsPanel' is not displayed");
				 }
			
				/*	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							getDocView_AnalyticsPanel().Displayed()  ;}}), Input.wait30);   
					Assert.assertEquals(getDocView_AnalyticsPanel().Exists(),getDocView_RedactIcon().Displayed());
					/*
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							getDocView_RedactIcon().Displayed()  ;}}), Input.wait30);   
					Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().Displayed());
				
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							getDocView_RedactIcon().Displayed()  ;}}), Input.wait30);   
					Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().Displayed());
				
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							getDocView_RedactIcon().Displayed()  ;}}), Input.wait30);   
					Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().Displayed());
				
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							getDocView_RedactIcon().Displayed()  ;}}), Input.wait30);   
					Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().Displayed());
				
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							getDocView_RedactIcon().Displayed()  ;}}), Input.wait30);   
					Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().Displayed());
				
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							getDocView_RedactIcon().Displayed()  ;}}), Input.wait30);   
					Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().Displayed());
				
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							getDocView_RedactIcon().Displayed()  ;}}), Input.wait30);   
					Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().Displayed());
				
					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
							getDocView_RedactIcon().Displayed()  ;}}), Input.wait30);   
					Assert.assertEquals(getDocView_RedactIcon().Exists(),getDocView_RedactIcon().Displayed());
				*/
			  }
	   
	   public void AnalyticsCodeSameAs() throws InterruptedException {
				   	
		   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getDocView_EditMode().Displayed()  ;}}), Input.wait60);   
		   getDocView_EditMode().waitAndClick(10);
	   
		   String parentWindowID = driver.getWebDriver().getWindowHandle();
			   
		   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getDocView_HdrAnalytics().Displayed()  ;}}), Input.wait30);   
		   getDocView_HdrAnalytics().Click();
		   
		   for(String winHandle : driver.getWebDriver().getWindowHandles()){
			    driver.switchTo().window(winHandle);
			}
		   
		   getDocView_Analytics_FamilyTab().waitAndClick(10);
		   
		   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				   getDocView_Analytics_ChildWindow_FamilyTab_Firstdoc().Displayed()  ;}}), Input.wait30);   
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
		   
		   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				   getDocView_DocumentViewer_DocID().Displayed()  ;}}), Input.wait30);   
		   String Docid = getDocView_DocumentViewer_DocID().getText();
		   System.out.println(Docid);
		   
		   
		   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getDocView_Analytics_NearDupeTab().Displayed()  ;}}), Input.wait30);   
		   getDocView_Analytics_NearDupeTab().waitAndClick(10);
		   
		   String parentWindowID = driver.getWebDriver().getWindowHandle();
		   
		   getDocView_NearDupeIcon().waitAndClick(10);
		   Thread.sleep(3000);
	   
		   for(String winHandle : driver.getWebDriver().getWindowHandles()){
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
		   
		   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				   getDocView_Analytics_Threaded_FirstDoc().Displayed()  ;}}), Input.wait60);   
		   getDocView_Analytics_Threaded_FirstDoc().waitAndClick(15);
		   
		   getDocView_ChildWindow_ActionButton().waitAndClick(10);
		   
		   getDocView_Analytics_Thread_CodeSameAs().waitAndClick(10);
		   
		   base.VerifySuccessMessage("Code same performed successfully.");
		   
		   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				   geDocView_Threaded_CodeSameAsIcon().Displayed()  ;}}), Input.wait30); 
		   Assert.assertTrue(geDocView_Threaded_CodeSameAsIcon().Displayed());
		   
		   getDocView_MiniDocListIds(1).waitAndClick(10);
		   getDocView_MiniDoc_CodeSameIcon().ScrollTo();
		   
		  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				   getDocView_MiniDocListIds(1).Displayed()  ;}}), Input.wait30);
		   Assert.assertTrue(getDocView_MiniDoc_CodeSameIcon().Displayed());
		   
		   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				   getDocView_MiniDoc_SelectRow(1).Displayed()  ;}}), Input.wait30);   
		   getDocView_MiniDoc_SelectRow(1).waitAndClick(10);
		   
		   getDocView_Mini_ActionButton().waitAndClick(10);
		   
		   getDocView__ChildWindow_Mini_RemoveCodeSameAs().waitAndClick(10);	
		   
		   base.CloseSuccessMsgpopup();
		   base.VerifySuccessMessage("Code Same has been successfully removed");
		   base.CloseSuccessMsgpopup();
		   Thread.sleep(2000);
		  
		   try{
			   geDocView_Threaded_CodeSameAsIcon().Displayed();
		           Assert.assertFalse(1==0);
		     }catch (org.openqa.selenium.NoSuchElementException e) {
		               System.out.println(" 'DocView_Threaded_CodeSameAsIcon' is not displayed");
		 }
		   
		   try{
			   getDocView_MiniDoc_CodeSameIcon().Displayed();
		           Assert.assertFalse(1==0);
		     }catch (org.openqa.selenium.NoSuchElementException e) {
		               System.out.println(" 'DocView_MiniDoc_CodeSameAsIcon' is not displayed");
		 }
		   
		   
		   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			getAddComment1().Visible()  ;}}), Input.wait30);   
	    	getAddComment1().SendKeys("Test Comment");
	    	
	    	//radio buttons and check boxes
	    	try{
	    		driver.getWebDriver().findElement(By.xpath("(//*[@id='0_radiogroup']/div[2]/div/label)[1]")).click();
	    		driver.getWebDriver().findElement(By.xpath("(//*[@id='0_radiogroup']/div[2]/div/label)[2]")).click();
	    		//Thread.sleep(3000);
	    		//driver.getWebDriver().findElement(By.xpath("(//*[@id='0_checkgroup']/div[1]/div/label)[2]")).click();
	    	}catch (Exception e) {
				// TODO: handle exception
			}
	    	getSaveDoc().waitAndClick(30);
	    	
	    	base.VerifySuccessMessage("Document saved successfully");
		/*   
		   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				   getDocView_Analytics_Threaded().Displayed()  ;}}), Input.wait30);  
		   getDocView_Analytics_Threaded().Displayed();*/
		   
		   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getDocView_EditMode().Displayed()  ;}}), Input.wait30);   
		   getDocView_EditMode().Click();
	   
		   String parentWindowID = driver.getWebDriver().getWindowHandle();
			   
		   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getDocView_HdrAnalytics().Displayed()  ;}}), Input.wait30);   
		   getDocView_HdrAnalytics().Click();
		   
		   for(String winHandle : driver.getWebDriver().getWindowHandles()){
			    driver.switchTo().window(winHandle);
			}
		   
		   getDocView_ThreadedChild_Selectalldoc().waitAndClick(10);
		   
		   getDocView_ChildWindow_ActionButton().waitAndClick(10);
		   
		   getDocView_Analytics_Thread_Folder().waitAndClick(10);
		   driver.getWebDriver().close();
		   
		   driver.switchTo().window(parentWindowID);
		   Thread.sleep(3000);
		   
		    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			    	sp.getContinueCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
		   int actualcount = Integer.parseInt(sp.getContinueCount().getText());
		   System.out.println(actualcount);
		   //Assert.assertEquals(actualcount,9);
		   
		   base.getCancelbutton().waitAndClick(10);
		   try {
			   Thread.sleep(3000);
		   alert = driver.switchTo().alert();
		   alert.accept();
		   } catch (Exception e)
		   { e.printStackTrace();}
		   
		   getSaveDoc().waitAndClick(30);
    	   driver.Navigate().refresh();
		   
	   }
	   
	   public void MiniDoclistFolderAction(String foldername) throws InterruptedException {
		   	
		   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getDocView_EditMode().Displayed()  ;}}), Input.wait30);   
		   getDocView_EditMode().waitAndClick(10);
	   
		   String parentWindowID = driver.getWebDriver().getWindowHandle();
			   
		   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getDocView_HdrAnalytics().Displayed()  ;}}), Input.wait30);   
		   getDocView_HdrMinDoc().Click();
		   
		   for(String winHandle1 : driver.getWebDriver().getWindowHandles()){
			    driver.switchTo().window(winHandle1);
			}
		   
		   for(int i=1;i<=2;i++){
		  
			  getDocView_MiniDoc_ChildWindow_Selectdoc(i).waitAndClick(10);
		   }
		   
		   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				   getDocView_Mini_ActionButton().Displayed()  ;}}), Input.wait30);   
		   getDocView_Mini_ActionButton().Click();
		   
		   getDocView__ChildWindow_Mini_FolderAction().waitAndClick(10);
		   
		   driver.getWebDriver().close();
		   driver.switchTo().window(parentWindowID);
		    
		  // sp.BulkActions_Folder(foldername);
		   sp.getSelectFolderExisting(foldername).waitAndClick(5);
		   	 
			  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   		    	sp.getContinueCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
			   sp.getContinueButton().Click();
			   	 
			   	final BaseClass bc = new BaseClass(driver);
			    final int Bgcount = bc.initialBgCount();
			       
			  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   		    	sp.getFinalCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
			  sp.getFinalizeButton().Click();
			   	 
			   base.VerifySuccessMessage("Records saved successfully");
			   	 
			  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   			bc.initialBgCount() == Bgcount+1  ;}}), Input.wait60); 
			  System.out.println("Bulk folder is done, folder is : "+foldername);
		   
		   driver.Navigate().refresh();
		   Thread.sleep(3000);
		   
		   alert = driver.switchTo().alert();
		   alert.accept();
		   driver.Navigate().refresh();
		   
		   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				   getDocView_MiniDocList().Displayed()  ;}}), Input.wait30);   
		   Assert.assertTrue(getDocView_MiniDocList().Visible() && getDocView_MiniDocList().Enabled());
		  
		   }
	   
	   public void AnalyticsThreaded_Actions() throws InterruptedException {
		   
		   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				   getDocView_ThreadedChild_Selectalldoc().Displayed()  ;}}), Input.wait30);   
		     try{
		    	   getAddRemarkbtn().Displayed();
			           Assert.assertFalse(1==0);
			     }catch (org.openqa.selenium.NoSuchElementException e) {
			               System.out.println("Remark Button not displayed for PA");
			 }

		   getDocView_DocId("ID00001059").waitAndClick(20);
		  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				  getDocView_ThreadedChild_Selectalldoc().Displayed()  ;}}), Input.wait30);   
		   getDocView_ThreadedChild_Selectalldoc().waitAndClick(10);
		   
		  // getDocView_Analytics_LoadMoreButton().waitAndClick(10);
		   
		 //  getDocView_ThreadedChild_Selectalldoc().waitAndClick(10);
		   
		   getDocView_ChildWindow_ActionButton().waitAndClick(10);
		   
		   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				   getDocView_Analytics_Thread_CodeSameAs().Displayed()  ;}}), Input.wait30); 
		   Assert.assertFalse(getDocView_Analytics_Thread_CodeSameAs().Displayed());
		   Assert.assertFalse(getDocView_Analytics_Thread_RemoveCodeSameAs().Displayed());
	   }
	   
	    public void AnalyticsThreadedNoDocument() throws InterruptedException {
		   	
			  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  getDocView_Analytics_Threaded().Displayed()  ;}}), Input.wait30);   
			  Assert.assertEquals("Your query returned no data", getDocView_Analytics_Threaded().getText());
			   
			   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						getDocView_EditMode().Displayed()  ;}}), Input.wait30);   
			   getDocView_EditMode().waitAndClick(10);
		   
			   String parentWindowID = driver.getWebDriver().getWindowHandle();
				   
			   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						getDocView_HdrAnalytics().Displayed()  ;}}), Input.wait30);   
			   getDocView_HdrAnalytics().Click();
			   
			   for(String winHandle : driver.getWebDriver().getWindowHandles()){
				    driver.switchTo().window(winHandle);
				}
			   
			   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						  getDocView_Analytics_Threaded().Displayed()  ;}}), Input.wait30);   
				  Assert.assertEquals("Your query returned no data", getDocView_Analytics_Threaded().getText());
				
			driver.getWebDriver().close();
			 
			driver.switchTo().window(parentWindowID);
			Thread.sleep(3000);
		   }
	   
	    public void audiodocthruhistorydropdown() {
	    	//adding comments
	        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	        		getDocView_NumTextBox().Visible() ;}}),Input.wait30);
	        getDocView_NumTextBox().SendKeys("2");
	        getDocView_NumTextBox().Enter();
	       
	        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	       		 getDocView_HistoryButton().Visible() ;}}),Input.wait30);
	        getDocView_HistoryButton().waitAndClick(10);
	        
	        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	        		getDocView_Historydropdown().Visible() ;}}),Input.wait30);
		    getDocView_Historydropdown().waitAndClick(10);
		    
		    Assert.assertTrue(getDocView_TextFileType().Enabled());
            Assert.assertEquals(getDocView_TextFileType().getText().toString(), "MP3 VERSION");
	    }
	   
	    public void getDocView_AnalyticsEmail() throws InterruptedException {
		   	
			  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  getDocView_Metadata_EmailInclusiveScore().Displayed()  ;}}), Input.wait60);  
			  String score =  getDocView_Metadata_EmailInclusiveScore().getText();
			 System.out.println(score);
			 
			 String reason =  getDocView_Metadata_EmailInclusiveReason().getText();
			 System.out.println(reason);
			 
			/*  if(getDocView_Metadata_EmailInclusive().getText().isEmpty())
			  {
			  System.out.println("Email Inclusive reason is not available");
			  }*/
			 /* else {
				  String icontext = getDocView_Analytics_Threadedicon().getText();
				  System.out.println(icontext);
			  }
			   */
			   /*  Actions action = new Actions(driver.getWebDriver());
			  action.moveToElement(getDocView_Analytics_Threadedicon().getWebElement()).build().perform();
		//	  action.clickAndHold(getDocView_Analytics_Threadedicon().getWebElement()).perform();
			  String text =  getDocView_Analytics_Threadedicon().GetAttribute("title");
			  System.out.println(text);
			  
			  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					   getDocView_Analytics_Thread_CodeSameAs().Displayed()  ;}}), Input.wait30); 
			   Assert.assertFalse(getDocView_Analytics_Thread_CodeSameAs().Displayed());
			   Assert.assertFalse(getDocView_Analytics_Thread_RemoveCodeSameAs().Displayed());
		  */ }
	   

	    public void VerifyTooltipsforallIconsGerman() throws InterruptedException {
			
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocView_RedactIcon().Displayed()  ;}}), Input.wait30);   
			softAssertion.assertTrue(getDocView_RedactIcon().Displayed());
		base.GetandVerifyTooltip(getDocView_RedactIcon(),"DE-Redact");
		
	/*
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocView_AnnotateIcon().Enabled()  ;}}), Input.wait30);   */
		softAssertion.assertTrue(getDocView_AnnotateIcon().Displayed());
		base.GetandVerifyTooltip(getDocView_AnnotateIcon(),"DE-Annotate");
		
	/*
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getPersistantHitEyeIcon().Displayed()  ;}}), Input.wait30);   */
		softAssertion.assertTrue(getPersistantHitEyeIcon().Displayed());
		base.GetandVerifyTooltip(getPersistantHitEyeIcon(),"DE-Persistent Highlighting");
		
	/*	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocView_AddRemarkIcon().Displayed()  ;}}), Input.wait30);   */
		softAssertion.assertTrue(getDocView_AddRemarkIcon().Displayed());
		base.GetandVerifyTooltip(getDocView_AddRemarkIcon(),"DE-Reviewer Remarks");
	/*	
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocView_Print().Displayed()  ;}}), Input.wait30);   */
	   softAssertion.assertTrue(getDocView_Print().Displayed());
		base.GetandVerifyTooltip(getDocView_Print(),"DE-Print");
		
	/*	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocView_IconDownload().Displayed()  ;}}), Input.wait30);   */
		softAssertion.assertTrue(getDocView_IconDownload().Displayed());
		base.GetandVerifyTooltip(getDocView_IconDownload(),"DE-Download");
	
	/*
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocView_Zoomout().Displayed()  ;}}), Input.wait30);   */
		softAssertion.assertTrue(getDocView_Zoomout().Displayed());
		base.GetandVerifyTooltip(getDocView_Zoomout(),"DE-Zoom Out");
		
		/*driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocView_Slider().Displayed()  ;}}), Input.wait30);   */
		softAssertion.assertTrue(getDocView_Slider().Displayed());
		base.GetandVerifyTooltip(getDocView_Slider(),"DE-Zoom");
			
	/*	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocView_ZoomIn().Displayed()  ;}}), Input.wait30); */  
		softAssertion.assertTrue(getDocView_ZoomIn().Displayed());
		base.GetandVerifyTooltip(getDocView_ZoomIn(),"DE-Zoom In");
				
		/*driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocView_ResetZoom().Displayed()  ;}}), Input.wait30);   */
		softAssertion.assertTrue(getDocView_ResetZoom().Displayed());
		base.GetandVerifyTooltip(getDocView_ResetZoom(),"DE-Reset Zoom");
	/*
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocView_Rotateright().Displayed()  ;}}), Input.wait30);   */
		softAssertion.assertTrue(getDocView_Rotateright().Displayed());
		base.GetandVerifyTooltip(getDocView_Rotateright(),"DE-Rotate Clockwise");
	/*	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocView_Rotateleft().Displayed()  ;}}), Input.wait30);   */
		softAssertion.assertTrue(getDocView_Rotateleft().Displayed());
		base.GetandVerifyTooltip(getDocView_Rotateleft(),"DE-Rotate Anticlockwise");
	/*s*/
		softAssertion.assertTrue(getDocView_SearchButton().Displayed());
		base.GetandVerifyTooltip(getDocView_SearchButton(),"DE-Search"); 
		
		softAssertion.assertAll();
	
	    }
	    
		  public void VerifyTooltipsforallIconsEnglish() throws InterruptedException {
			   	
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						getDocView_RedactIcon().Displayed()  ;}}), Input.wait30);   
				softAssertion.assertTrue(getDocView_RedactIcon().Displayed());
				base.GetandVerifyTooltip(getDocView_RedactIcon(),"Redact");
				
			
			/*	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						getDocView_AnnotateIcon().Enabled()  ;}}), Input.wait30);   
			*/	softAssertion.assertTrue(getDocView_AnnotateIcon().Displayed());
				base.GetandVerifyTooltip(getDocView_AnnotateIcon(),"Annotate");
				
			
			/*	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						getPersistantHitEyeIcon().Displayed()  ;}}), Input.wait30);   
			*/	softAssertion.assertTrue(getPersistantHitEyeIcon().Displayed());
				base.GetandVerifyTooltip(getPersistantHitEyeIcon(),"Persistent Highlighting");
				
				/*driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						getDocView_AddRemarkIcon().Displayed()  ;}}), Input.wait30);   
				*/softAssertion.assertTrue(getDocView_AddRemarkIcon().Displayed());
				base.GetandVerifyTooltip(getDocView_AddRemarkIcon(),"Reviewer Remarks");
				
			/*   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						getDocView_Print().Displayed()  ;}}), Input.wait30);   */
			   softAssertion.assertTrue(getDocView_Print().Displayed());
				base.GetandVerifyTooltip(getDocView_Print(),"Print");
				
				/*driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						getDocView_IconDownload().Displayed()  ;}}), Input.wait30);   */
				softAssertion.assertTrue(getDocView_IconDownload().Displayed());
				base.GetandVerifyTooltip(getDocView_IconDownload(),"Download");
			
			
			/*	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						getDocView_Zoomout().Displayed()  ;}}), Input.wait30);   */
				softAssertion.assertTrue(getDocView_Zoomout().Displayed());
				base.GetandVerifyTooltip(getDocView_Zoomout(),"Zoom Out");
				
			/*	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						getDocView_Slider().Displayed()  ;}}), Input.wait30);   */
				softAssertion.assertTrue(getDocView_Slider().Displayed());
				base.GetandVerifyTooltip(getDocView_Slider(),"Zoom");
					
			/*	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						getDocView_ZoomIn().Displayed()  ;}}), Input.wait30);   */
				softAssertion.assertTrue(getDocView_ZoomIn().Displayed());
				base.GetandVerifyTooltip(getDocView_ZoomIn(),"Zoom In");
						
			/*	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						getDocView_ResetZoom().Displayed()  ;}}), Input.wait30);   */
				softAssertion.assertTrue(getDocView_ResetZoom().Displayed());
				base.GetandVerifyTooltip(getDocView_ResetZoom(),"Reset Zoom");
			
			/*	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						getDocView_Rotateright().Displayed()  ;}}), Input.wait30);   */
				softAssertion.assertTrue(getDocView_Rotateright().Displayed());
				base.GetandVerifyTooltip(getDocView_Rotateright(),"Rotate Clockwise");
				
		/*		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						getDocView_Rotateleft().Displayed()  ;}}), Input.wait30);   */
				softAssertion.assertTrue(getDocView_Rotateleft().Displayed());
				base.GetandVerifyTooltip(getDocView_Rotateleft(),"Rotate Anticlockwise");
			
			/*	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						getDocView_SearchButton().Displayed()  ;}}), Input.wait30);   */
				softAssertion.assertTrue(getDocView_SearchButton().Displayed());
				base.GetandVerifyTooltip(getDocView_SearchButton(),"Search"); 
				
				softAssertion.assertAll();
		    
		     } 
		  
		  public void Analytics_FamilyActions(String folderName) throws InterruptedException {
			   	
			  getDocView_Analytics_FamilyTab().waitAndClick(30);
			   
			  getDocView_Analytics_ChildWindow_FamilyTab_Firstdoc().waitAndClick(10);
			   
			  getDocView_ChildWindow_ActionButton().waitAndClick(10);
			  
			  getDocView_FamilyBulkFolder().waitAndClick(10);
			   
			  sp.getSelectFolderExisting(folderName).waitAndClick(5);
			   	 
			  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   		    	sp.getContinueCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
			   sp.getContinueButton().Click();
			   	 
			   	final BaseClass bc = new BaseClass(driver);
			    final int Bgcount = bc.initialBgCount();
			       
			  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   		    	sp.getFinalCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
			  sp.getFinalizeButton().Click();
			   	 
			   base.VerifySuccessMessage("Records saved successfully");
			   	 
			  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   			bc.initialBgCount() == Bgcount+1  ;}}), Input.wait60); 
			  System.out.println("Bulk folder is done, folder is : "+folderName);
			  
			  //View in Doclist
			  getDocView_Analytics_ChildWindow_FamilyTab_Firstdoc().waitAndClick(10);
			   
			  getDocView_ChildWindow_ActionButton().waitAndClick(10);
			  
			  getDocView_FamilyViewInDoclist().waitAndClick(10);
			  
			 }
    
		  public void NavigationInDocView() throws InterruptedException {
			   	
			  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  getDocView_Last().Displayed()  ;}}), Input.wait60);   
			  getDocView_Last().waitAndClick(10);
			  
			  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  getDocView_NumTextBox().Enabled()  ;}}), Input.wait30);   
			  String nointextbox  = getDocView_Last().getText();
			  System.out.println(nointextbox);
			  Assert.assertEquals(nointextbox, 50);
			  
			  getDocView_First().waitAndClick(10);
			  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  getDocView_NumTextBox().Enabled()  ;}}), Input.wait30);   
			  String nointextbox1  = getDocView_Last().getText();
			  System.out.println(nointextbox1);
			  Assert.assertEquals(nointextbox1, 1);
		  }	  
			  
		  public void MiniDoclistConifgSortOrder() throws InterruptedException {
			   	
			   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						getDocView_EditMode().Displayed()  ;}}), Input.wait30);   
			   getDocView_EditMode().waitAndClick(10);
		   
			   String parentWindowID = driver.getWebDriver().getWindowHandle();
				   
			   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						getDocView_HdrAnalytics().Displayed()  ;}}), Input.wait30);   
			   getDocView_HdrMinDoc().Click();
			   
			   for(String winHandle1 : driver.getWebDriver().getWindowHandles()){
				    driver.switchTo().window(winHandle1);
				}
			   
			   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					   getDocView_ReviewModeText().Displayed()  ;}}), Input.wait30);
			   String sortmodetext = getDocView_ReviewModeText().getText();
			   System.out.println(sortmodetext);
			   Assert.assertEquals(sortmodetext, "You are reviewing docs in Optimized Sort mode");
			  
			   String expvalues[] = {"DOCID","DOCFILETYPE","FAMILYRELATIONSHIP","FAMILYMEMBERCOUNT"};
			   WebElement countoffields = driver.FindElementByXPath("//*[@id='SearchDataTable_wrapper']/div[3]/div[1]//table/thead/tr/th").getWebElement();
			   List<WebElement> allvalues = countoffields.findElements(By.tagName("th"));
			   List<String> all = new ArrayList<String>();
			   for(int j=1;j<=allvalues.size();j++)
			   {
				  System.out.println(all.add(allvalues.get(j).getText()));
				   if(all.equals(expvalues)) {
					   System.out.println("Pass");
					  
				   }
				   else {
					   System.out.println("FAIL");
				   }
			   }
			 	   
			   getDocView_ConfigMinidoclist().waitAndClick(10);
			   
			   driver.getWebDriver().close();
			   driver.switchTo().window(parentWindowID);
				Thread.sleep(3000);
			   
			  List<WebElement> optimized = getDocView_Config_Selectedfield().FindWebElements();
			  List<String> alloptimized = new ArrayList<String>();
              for(int k=0;k<optimized.size();k++)
              {
            	 System.out.println(alloptimized.add(optimized.get(k).getText()));
             }
               
			   getDocView_ConfigMinidoclist_ManualSort().waitAndClick(10);
			   List<WebElement> manual = getDocView_Config_Selectedfield().FindWebElements();
			   System.out.println(manual);
			   
			   base.CompareListStrings(optimized, manual);
			   
			   getDocView_MiniDocFields_Remove().waitAndClick(10);
			   
			   getDocView_ConfigMinidoclist_OptimizedSort().waitAndClick(10);
			     
			   for (String expected: all) {
				   if (alloptimized.contains(expected)) {
				     System.out.println("Passed");
				   } else {
				     System.out.println("Failed");
				     }
				   }		   
			   base.getCancelbutton().waitAndClick(10);
			  			   
		  }
		  
			  
		   public void getDocView_Conecptual() throws InterruptedException {
			   	
				  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						  getDocView_Analytics_liDocumentConceptualSimilarab().Displayed()  ;}}), Input.wait30);  
				  getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(10);
				  
				 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						 getDocView_ConceptualDocs().Displayed()  ;}}), Input.wait30); 
			   	
			    if(getDocView_ConceptualDocs().FindWebElements().size()>1)
			    {
			    	System.out.println("More than 1 count is displayed");
			    }
			    else 
			    {
			    	System.out.println("No docs displayed");
			    }
		   }
			  
			  
	 public void VerifyKeywordHit(String searchString) throws InterruptedException {
				
	    String hitscount = getPersistentHit(searchString);
		System.out.println("Hits are:"+hitscount);
				
			   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					  		 getDocView_HitsTogglePanel().Displayed() ;}}),Input.wait30);
				   Assert.assertEquals("Hide Terms with 0 hits:", getDocView_ToogleLabel().getText());
				   
				
				getDocView_Persistent_PrevHit().Displayed();
				getDocView_Persistent_NextHit().Displayed();
				getDocView_Persistent_NextHit().waitAndClick(10);
				getDocView_Persistent_PrevHit().waitAndClick(10);
				
			  System.out.println(getDocView_Persistent_NextHit().GetAttribute("key").toString());
				
					
			}
	 
	 public void redactbyrectangle(int off1,int off2,int cordinate,String redactiontag) throws InterruptedException
	 {
		 try {
			  System.out.println(off1+"...."+off2);
             Actions actions = new Actions(driver.getWebDriver());  
             WebElement text = getDocView_Redactrec_textarea();
             
             actions.moveToElement(text, off1,off2)
             .clickAndHold()
             .moveByOffset(100, 10)
             .release()
             .perform();
		 }
           
			catch (Exception e)
					{
						e.printStackTrace();
						System.out.println("Not able to select redacted area");
					}
		 
		    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		    getDocView_SelectReductionLabel().Displayed() ;}}), Input.wait30);
		    getDocView_SelectReductionLabel().selectFromDropdown().selectByVisibleText(redactiontag);
		 
	    	Thread.sleep(2000); 
		   	getDocView_SaveReduction1(cordinate).waitAndClick(15);
	    	Thread.sleep(2000);    	
	    	base.VerifySuccessMessage("Redaction tags saved successfully.");
	    	Thread.sleep(2000);
     
	 }
	 
	 public void editredaction(int off1,int off2,int cordinate,String redactiontag) throws InterruptedException
	 {
			 System.out.println(off1+"...."+off2);
             Actions actions = new Actions(driver.getWebDriver());  
             WebElement text = getDocView_Redactrec_textarea();
             
             actions.moveToElement(text, off1,off2).click().build().perform();
             Thread.sleep(1000);
             
             driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
            		 getDocView_Redactedit_selectlabel().Displayed() ;}}), Input.wait30);
             getDocView_Redactedit_selectlabel().selectFromDropdown().selectByVisibleText(redactiontag);
         		 
         	    	Thread.sleep(2000); 
         	    	getDocView_Redactedit_save().waitAndClick(15);
         	    	Thread.sleep(2000);    	
         	    	base.VerifySuccessMessage("Redaction tags saved successfully.");
         	    	Thread.sleep(2000);
  		
 		}
	 
	 public void Deleteredaction(int off1,int off2,int cordinate,String redactiontag) throws InterruptedException
	 {
			 System.out.println(off1+"...."+off2);
             Actions actions = new Actions(driver.getWebDriver());  
             WebElement text = getDocView_Redactrec_textarea();
             
             actions.moveToElement(text, off1,off2).click().build().perform();
             Thread.sleep(1000);
             
             driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
            		 getDocView_Annotate_DeleteIcon().Displayed() ;}}), Input.wait30);
             getDocView_Annotate_DeleteIcon().waitAndClick(15);
         	 Thread.sleep(2000);    	
         	 base.VerifySuccessMessage("Redaction Removed successfully.");
         	 Thread.sleep(2000);
  		
 		}
			  
	    public void paint(Graphics g)
		{
			g.fillRect(240, 240, 200, 100);
		}	  
			  
			  
	}
