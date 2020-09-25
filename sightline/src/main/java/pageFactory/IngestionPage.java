package pageFactory;

import java.util.HashMap;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;

public class IngestionPage {

    Driver driver;
  
    public String IngestionName;
    BaseClass base;
    
   //ID's
    public Element getTotalIngestCount(){ return driver.FindElementById("totalIngestionCount"); }
    public Element getSpecifySourceSystem(){ return driver.FindElementById("ddlSourceSystem"); }
    public Element getSpecifySourceFolder(){ return driver.FindElementById("ddlFolders"); }
    public Element getSpecifyLocation(){ return driver.FindElementById("ddlServerLocation"); }
    public Element getSpecifySourceIngestionType(){ return driver.FindElementById("ddlOverwrite"); }
    public Element getDATDelimitersFieldSeparator(){ return driver.FindElementById("ddlColumnDelimiter"); }
    public Element getDATDelimitersTextQualifier(){ return driver.FindElementById("ddlTextQualifier"); }
    public Element getDATDelimitersNewLine(){ return driver.FindElementById("ddlDataDelimiter"); }
    public Element getSourceSelectionDAT(){ return driver.FindElementById("chkDAT"); }
    public Element getSourceSelectionDATLoadFile(){ return driver.FindElementById("ddlLoadDatFile"); }
    public Element getSourceSelectionDATKey(){ return driver.FindElementById("ddlKeyDatFile"); }
    public Element getSourceSelectionTextLoadFile(){ return driver.FindElementById("ddlLoadTextFile"); }
    public Element getDocumentKey(){ return driver.FindElementById("ddlKeyDatFile"); }
    public Element getNativeLST(){ return driver.FindElementById("ddlLoadNativeFile"); }
    
  
    public Element getNativeFilePathFieldinDAT(){ return driver.FindElementById("ddlFilesPathNative"); }
    public Element getTextLST(){ return driver.FindElementById("ddlLoadTextFile"); }
    public Element getTextFilePathFieldinDAT(){ return driver.FindElementById("ddlFilesPathText"); }
    public Element getPDFLST(){ return driver.FindElementById("ddlLoadPDFFile"); } 
    public Element getMP3LST(){ return driver.FindElementById("ddlLoadMP3Variantfile"); } 
    public Element getAudioTranscriptLST(){ return driver.FindElementById("ddlLoadTranscriptfile"); } 
    public Element getPDFFilePathFieldinDAT(){ return driver.FindElementById("ddlFilesPathPDF"); }
    public Element getTIFFLST(){ return driver.FindElementById("ddlLoadTIFFfile"); }
    public Element getTIFFFilePathFieldinDAT(){ return driver.FindElementById("ddlFilesPathTIFF"); }
    public Element getOtherLinkType(){ return driver.FindElementById("ddlLoadLinkType"); }
    public Element getOtherLoadFile(){ return driver.FindElementById("ddlLoadOtherfile"); }
    public Element getOtherFilePathFieldinDAT(){ return driver.FindElementById("ddlFilesPathOther"); }
    public Element getAddButton(){ return driver.FindElementById("AddButton"); }
    public Element getMappingSOURCEFIELD2(){ return driver.FindElementById("SF_2"); }
    public Element getMappingSOURCEFIELD3(){ return driver.FindElementById("SF_3"); }
    public Element getMappingSOURCEFIELD4(){ return driver.FindElementById("SF_4"); }
    public Element getMappingSOURCEFIELD5(){ return driver.FindElementById("SF_5"); }
    public Element getMappingSOURCEFIELD6(){ return driver.FindElementById("SF_6"); }
    public Element getMappingSOURCEFIELD7(){ return driver.FindElementById("SF_7"); }
    public Element getMappingSOURCEFIELD8(){ return driver.FindElementById("SF_8"); }
    public Element getMappingSOURCEFIELD9(){ return driver.FindElementById("SF_9"); }
    public Element getMappingSOURCEFIELD10(){ return driver.FindElementById("SF_10"); }
    public Element getMappingSOURCEFIELD11(){ return driver.FindElementById("SF_11"); }
    public Element getMappingSOURCEFIELD13(){ return driver.FindElementById("SF_13"); }
    public Element getMappingSOURCEFIELD14(){ return driver.FindElementById("SF_14"); }
    public Element getMappingSOURCEFIELD25(){ return driver.FindElementById("SF_25"); }
    
    public Element getMappingFIELDCAT5(){ return driver.FindElementById("TY_5"); }
    public Element getMappingFIELDCAT6(){ return driver.FindElementById("TY_6"); }
    public Element getMappingFIELDCAT7(){ return driver.FindElementById("TY_7"); }
    public Element getMappingFIELDCAT8(){ return driver.FindElementById("TY_8"); }
    public Element getMappingFIELDCAT9(){ return driver.FindElementById("TY_9"); }
    public Element getMappingFIELDCAT10(){ return driver.FindElementById("TY_10"); }
    public Element getMappingFIELDCAT11(){ return driver.FindElementById("TY_11"); }
    public Element getMappingFIELDCAT13(){ return driver.FindElementById("TY_13"); }
    public Element getMappingFIELDCAT14(){ return driver.FindElementById("TY_14"); }
    public Element getMappingFIELDCAT25(){ return driver.FindElementById("TY_25"); }   
    public Element getMappingFIELDCAT26(){ return driver.FindElementById("TY_26"); }    
    public Element getMappingFIELDCAT27(){ return driver.FindElementById("TY_27"); }
    public Element getMappingFIELDCAT28(){ return driver.FindElementById("TY_28"); }
    public Element getMappingFIELDCAT29(){ return driver.FindElementById("TY_29"); }
    public Element getMappingFIELDCAT31(){ return driver.FindElementById("TY_31"); }
    public Element getMappingFIELDCAT49(){ return driver.FindElementById("TY_49"); }
    public Element getMappingFIELDCAT50(){ return driver.FindElementById("TY_50"); }
    public Element getMappingFIELDCAT51(){ return driver.FindElementById("TY_51"); }
   
    public Element getMappingDESTINATIONFIELD1(){ return driver.FindElementById("DF_1"); }
    public Element getMappingDESTINATIONFIELD2(){ return driver.FindElementById("DF_2"); }
    public Element getMappingDESTINATIONFIELD3(){ return driver.FindElementById("DF_3"); }
    public Element getMappingDESTINATIONFIELD4(){ return driver.FindElementById("DF_4"); }
    public Element getMappingDESTINATIONFIELD5(){ return driver.FindElementById("DF_5"); }
    public Element getMappingDESTINATIONFIELD6(){ return driver.FindElementById("DF_6"); }
    public Element getMappingDESTINATIONFIELD7(){ return driver.FindElementById("DF_7"); }
    public Element getMappingDESTINATIONFIELD8(){ return driver.FindElementById("DF_8"); }
    public Element getMappingDESTINATIONFIELD9(){ return driver.FindElementById("DF_9"); }
    public Element getMappingDESTINATIONFIELD10(){ return driver.FindElementById("DF_10"); }
    public Element getMappingDESTINATIONFIELD11(){ return driver.FindElementById("DF_11"); }
    public Element getMappingDESTINATIONFIELD13(){ return driver.FindElementById("DF_13"); }
    public Element getMappingDESTINATIONFIELD14(){ return driver.FindElementById("DF_14"); }
    public Element getMappingDESTINATIONFIELD25(){ return driver.FindElementById("DF_25"); }
    public Element getMappingDESTINATIONFIELD26(){ return driver.FindElementById("DF_26"); }
    public Element getMappingDESTINATIONFIELD27(){ return driver.FindElementById("DF_27"); }
    public Element getMappingDESTINATIONFIELD28(){ return driver.FindElementById("DF_28"); }
    public Element getMappingDESTINATIONFIELD29(){ return driver.FindElementById("DF_29"); }
    public Element getMappingDESTINATIONFIELD31(){ return driver.FindElementById("DF_31"); }
    public Element getMappingDESTINATIONFIELD49(){ return driver.FindElementById("DF_49"); }
    public Element getMappingDESTINATIONFIELD50(){ return driver.FindElementById("DF_50"); }
    public Element getMappingDESTINATIONFIELD51(){ return driver.FindElementById("DF_51"); }
    public Element getPreviewRun(){ return driver.FindElementById("PreviewRun"); }
    public Element getSaveDraftButton(){ return driver.FindElementById("SaveIngestion"); }
    public Element getbtnRunIngestion(){ return driver.FindElementById("btnRunIngestion"); }
    public Element getRefreshButton(){ return driver.FindElementById("refresh"); }
    public Element getApproveMessageOKButton(){ return driver.FindElementById("bot1-Msg1"); }
    public Element getRunAnalyticsRunButton(){ return driver.FindElementById("run"); }
    public Element getRunAnalyticsPublishButton(){ return driver.FindElementById("publish"); }
    public Element getLanguage(){ return driver.FindElementById("worldSelect"); }
    
    //Xpaths    
    //public Element getAddanewIngestionButton(){return driver.FindElementByXPath("//a[text()='Addx a new Ingestion']"); }
    public Element getAddanewIngestionButton(){return driver.FindElement(By.linkText("Add a new Ingestion"));}

    public Element getSourceSelectionText(){ return driver.FindElementByXPath("//strong[contains(.,'Text')]/../i"); }
    //public Element getNextButton(){ return driver.FindElementByXPath(".//*[@class='btn btn-primary btn-next']"); }
    public Element getNextButton(){ return driver.FindElementById("NextButton"); }
    public Element getNativeCheckBox(){ return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsNativeFolder']/following-sibling::i"); }
    public Element getIsNativeInPathInDAT(){ return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsDATNative']/following-sibling::i"); }
    public Element getIsTextInPathInDAT(){ return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsDATText']/following-sibling::i"); }
    public Element getTextCheckBox(){ return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsTextFolder']/following-sibling::i"); }
    public Element getPDFCheckBoxstionButton(){ return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsPDFFolder']/following-sibling::i"); }
    public Element getMP3CheckBoxstionButton(){ return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsMP3VariantFolder']/following-sibling::i"); }
    public Element getAudioTranscriptCheckBoxstionButton(){ return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsTranscriptFolder']/following-sibling::i"); }
    public Element getIsPDFInPathInDAT(){ return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsDATPDF']/following-sibling::i"); }
    public Element getTIFFCheckBox(){ return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsTIFFFolder']/following-sibling::i"); }
    public Element getIsTIFFInPathInDAT(){ return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsDATTIFF']/following-sibling::i"); }
    public Element getOtherCheckBox(){ return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsOtherFolder']/following-sibling::i"); }
    public Element getIsOtherInPathInDAT(){ return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsDATOther']/following-sibling::i"); }
    public Element getCatalogedIngestionStatus(){ return driver.FindElementByXPath("//strong[contains(.,'Cataloged')]"); }
    public Element getCopiedIngestionStatus(){ return driver.FindElementByXPath("//strong[contains(.,'Copied')]"); }
    public Element getFailedIngestionStatus(){ return driver.FindElementByXPath("//strong[contains(.,'Failed')]"); }
    public Element getIndexedIngestionStatus(){ return driver.FindElementByXPath("//strong[contains(.,'Indexed')]"); }
    public Element getApproveIngestionStatus(){ return driver.FindElementByXPath("//strong[contains(.,'Approved')]"); }
    public Element getPublishIngestionStatus(){ return driver.FindElementByXPath("//strong[contains(.,'Published')]"); }
    public Element getFilterByButton(){ return driver.FindElementByXPath(".//*[@id='cardGrid']/div[1]//button[@class='multiselect dropdown-toggle btn']"); }
    public Element getFilterByDRAFT(){ return driver.FindElementByXPath(".//*[@class='multiselect-container dropdown-menu']//label/input[@value='DRAFT']"); }
    public Element getFilterByFAILED(){ return driver.FindElementByXPath(".//*[@class='multiselect-container dropdown-menu']//label/input[@value='FAILED']"); }
    public Element getFilterByINPROGRESS(){ return driver.FindElementByXPath(".//*[@class='multiselect-container dropdown-menu']//label/input[@value='INPROGRESS']"); }
    public Element getFilterByCATALOGED(){ return driver.FindElementByXPath(".//*[@class='multiselect-container dropdown-menu']//label/input[@value='CATALOGED']"); }
    public Element getFilterByCOPIED(){ return driver.FindElementByXPath(".//*[@class='multiselect-container dropdown-menu']//label/input[@value='COPIED']"); }
    public Element getFilterByINDEXED(){ return driver.FindElementByXPath(".//*[@class='multiselect-container dropdown-menu']//label/input[@value='INDEXED']"); }
    public Element getFilterByAPPROVED(){ return driver.FindElementByXPath(".//*[@class='multiselect-container dropdown-menu']//label/input[@value='APPROVED']"); }
    public Element getFilterByPUBLISHED(){ return driver.FindElementByXPath(".//*[@class='multiselect-container dropdown-menu']//label/input[@value='PUBLISHED']"); }
    public Element getRunCopying(){ return driver.FindElementByXPath(".//*[@id='RunCopying']/i"); }
    public Element getCloseButton(){ return driver.FindElementByXPath("//*[@class='ui-dialog-titlebar-close']"); }
    public Element getRunIndexing(){ return driver.FindElementByXPath(".//*[@id='RunIndexing']/i"); }
    public Element getIsAudioCheckbox(){ return driver.FindElementByXPath("//input[@id='IsAudio']/following-sibling::i"); }
    public Element getActionDropdownArrow(){ return driver.FindElementByXPath(".//*[@id='IngestionDetailsPopUp1']//button[2]"); }
    public Element getActionApprove(){ return driver.FindElementByXPath(".//*[@id='IngestionDetailsPopUp1']//a[text()='Approve']"); }
    public Element getApproveMessageHeader(){ return driver.FindElementByXPath(".//*[@id='Msg1']/div/span"); }
    public Element getApproveMessageContent(){ return driver.FindElementByXPath(".//*[@id='Msg1']/div/p"); }
    public Element getAnalyticsCAATINGESTIONCOMPLETED(){ return driver.FindElementByXPath(".//*[@id='ProjectFieldsDataTable']//td[contains(.,'ANALYTICS_INGESTION_COMPLETED')]"); }
    public Element getAnalyticsCAATSTAGINGCOMPLETED(){ return driver.FindElementByXPath(".//*[@id='ProjectFieldsDataTable']//td[contains(.,'ANALYTICS_STAGING_COMPLETED')]"); }
    public Element getAnalyticsCAATINDEXINGCOMPLETED(){ return driver.FindElementByXPath(".//*[@id='ProjectFieldsDataTable']//td[contains(.,'ANALYTICS_INDEXING_COMPLETED')]"); }
    public Element Analytics_CAATESINDEXINGCOMPLETED(){ return driver.FindElementByXPath(".//*[@id='ProjectFieldsDataTable']//td[contains(.,'ES_INDEXING_COMPLETED')]"); }
    public Element getIngestionName(){ return driver.FindElementByXPath("(//*[@id='cardCanvas']//span[contains(.,'Automation')])[1]"); }
    public Element getMP3Count(){ return driver.FindElementByXPath(".//*[@id='dt_basic']/tbody/tr[6]/td[2]"); }
    public Element getIngestionNameText(){ return driver.FindElementByXPath(".//*[@id='IngestionDetailsPopUp1']/section/div/div/div[3]/fieldset/div[2]/div"); }
    public Element getIncrementalAnalyticsbutton(){ return driver.FindElementByXPath(".//*[@id='IncrementalAnalytics']/following-sibling::i"); }
    
    //added on 09-23
    public Element getDATCheckbox(){ return driver.FindElementById(".//*[@name='IngestionSpecifySetting.IsDATFolder']/following-sibling::i"); }
    public Element getDATLoadFile(){ return driver.FindElementById("ddlLoadDatFile"); }
    public Element getNativePathCheckbox(){ return driver.FindElementById(".//*[@name='IngestionSpecifySetting.IsDATNative']/following-sibling::i"); }
    public Element getFilePathNative(){ return driver.FindElementById("ddlFilesPathNative"); }
    public Element getgenerateSearchablePDFs(){ return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsDATTIFF']/following-sibling::i"); }
    public Element getIsMP3InPathInDAT(){ return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsDATMP3Variant']/following-sibling::i"); }
    public Element getMP3FilePathFieldinDAT(){ return driver.FindElementById("ddlFilesPathMP3Variant"); }
    public Element getIsTransciptInPathInDAT(){ return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsDATTranscript']/following-sibling::i"); }
    public Element getTransciptFilePathFieldinDAT(){ return driver.FindElementById("ddlFilesPathTranscript"); }
    public Element getLinkType(){ return driver.FindElementByXPath(".//*[@name='IngestionSpecifySetting.IsDATTranscript']/following-sibling::i"); }
    
    //added on 04-04
    public Element getIngestionName_CloseButton(){ return driver.FindElementByXPath(".//*[@id='IngestionDetailsPopUp1']/se//button[@class='ui-dialog-titlebar-close']"); }
    public Element getIngestionName_ExecCloseButton(){ return driver.FindElementByXPath("//button[@class='ui-dialog-titlebar-close']"); }
    public Element getIngestionActionButton(){ return driver.FindElementByXPath(".//*[@id='cardCanvas']//div[1]/a"); }
    public Element getFirstIngestionActionButton() {return driver.FindElementByXPath(".//*[@id='cardCanvas']//li[1]//div[1]/a");}
    public Element getIngestionAction_Wizard(){ return driver.FindElementByXPath("//dl[@class='dropdown-menu']//a[contains(text(),'Open in Wizard')]"); }

    public Element getIngestionAction_Delete(){ return driver.FindElementByXPath("//dl[@class='dropdown-menu']//a[contains(text(),'Delete')]"); }
    public Element getIngestionAction_Copy(){ return driver.FindElementByXPath("//dl[@class='dropdown-menu']//a[contains(text(),'Copy')]"); }
    public Element getIngestion_IngestionType(){ return driver.FindElementById("ddlOverwrite"); }
    public Element getDateFormat(){ return driver.FindElementById("ddlDateFormat"); }
    public Element getIndexBlock(){ return driver.FindElementById("Indexingblock"); }
	
    //added on 200831
    public Element getDateTimeOption(){ return driver.FindElementByXPath("ddlDateFormat//option[text='YYYY/MM/DD HH:MM:SS']"); }
    public Element getTimeZone(){ return driver.FindElementByXPath("ddlTimeZone//option[text='UTC']"); }
    public Element getDESTINATIONFIELD1OPTION(){ return driver.FindElementByXPath("//select[@id='DF_1']//option[@value='-54']"); }
    public Element getFirstIngestionTileName(){ return driver.FindElementByXPath("//div[@id='cardCanvas']//li[1]//span"); }
    public Element getIngestionExecutionHeader(){ return driver.FindElementByXPath("//span[@id='ui-id-1']/div"); }
    public Element getConfigureMappingText(){ return driver.FindElementByXPath("//div[@class='stepHeader']//div[@class='col-md-7']"); }
    public Element getMappingRequiredSource1(){ return driver.FindElementByXPath("//select[@id='DF_1']/following-sibling::span"); }
    public Element getMappingRequiredSource2(){ return driver.FindElementByXPath("//select[@id='DF_2']/following-sibling::span"); }
    public Element getMappingRequiredSource3(){ return driver.FindElementByXPath("//select[@id='DF_3']/following-sibling::span"); }
    public Element getMappingRequiredSource4(){ return driver.FindElementByXPath("//select[@id='DF_4']/following-sibling::span"); }
    public Element getToastMessage() {return driver.FindElementByCssSelector("div.bigBox.animated.fadeIn.fast");}
    public Element getActualSourceSys() {return driver.FindElementByCssSelector("#ddlSourceSystem > option[selected]");}
    public Element getActualSrcLoc() {return driver.FindElementByCssSelector("#ddlServerLocation > option[selected]");}
    public Element getActualSrcFolder() {return driver.FindElementByCssSelector("#ddlFolders > option[selected]");}
    public Element getActualDocKey() {return driver.FindElementByCssSelector("#ddlKeyDatFile > option[selected]");}
    public Element getActualNativeFile() {return driver.FindElementByCssSelector("#ddlLoadNativeFile > option[selected]");}
    public Element getActualMp3File() {return driver.FindElementByCssSelector("#ddlLoadMP3Variantfile > option[selected]");}
    public Element getActualAudioFile() {return driver.FindElementByCssSelector("#ddlLoadTranscriptfile > option[selected]");}
    public Element getSourceSystemTitle() {return driver.FindElementByCssSelector("fieldset:nth-child(1) > section > div:nth-child(2) > div.col-md-5 > label");}
    public Element getSourceLocationTitle() {return driver.FindElementByCssSelector("fieldset:nth-child(1) > section > div:nth-child(3) > div.col-md-5 > label");}
    public Element getSourceFolderTitle() {return driver.FindElementByCssSelector("fieldset:nth-child(1) > section > div:nth-child(4) > div.col-md-5 > label");}
    public Element getRecordTable() {return driver.FindElementByCssSelector("#previewRecords > div > table > tbody");}
    public Element getDATcheckbox() {return driver.FindElementByCssSelector("#formSpecify > fieldset:nth-child(4) > div:nth-child(2) > div > label > i");}
    public Element getDATTitle() {return driver.FindElementByCssSelector("#formSpecify > fieldset:nth-child(4) > div:nth-child(2) > div > label > strong");}
    public Element getNativeTitle() {return driver.FindElementByCssSelector("#formSpecify > fieldset:nth-child(4) > div:nth-child(4) > div > label > strong");}
    public Element getIngestionWizardTitle() {return driver.FindElementByCssSelector("#content > div:nth-child(3) > div > h1");}
    
    
    //added on 200923
    public Element getCopyPlayButton() {return driver.FindElementById("RunCopying");}
    public Element getIngressionModal() {return driver.FindElementByCssSelector(".ui-dialog.ui-widget.ui-widget-content.ui-corner-all.ui-front.ui-dialog-buttons");}
    public Element getIngestionTile() {return driver.FindElementByCssSelector("#cardCanvas > ul > li > a ");}
    public Element changeProjectSelector() {return driver.FindElementById("project-selector");}
    public Element changeProjectSelectorField() {return driver.FindElementByCssSelector("#ddlProject11 > li:nth-child(2) > a:nth-child(1)");}
    
    
    //System Admin Profile
    
    //public Element getAddNewProjectBtn() {return driver.FindElementById("btnAdd");}
    //public Element getAddNewProjectBtn() {return driver.FindElementByXPath("//*[.='Add Project']");}
    //public Element getAddNewProjectBtn() { return driver.FindElementByCssSelector("a#f.btn.btn-primary");}
   // public Element getAddNewProjectBtn() {return driver.FindElement(By.linkText("Add Project"));}
    //public Element getAddNewProjectBtn() {return driver.FindElementByXPath("//a[@href ='/en-us/Project/CreateProject']");}
    
    public Element getAddNewProjectBtn() {return driver.FindElementByCssSelector("#content > form > div:nth-child(3) > div > div.entityDiv > div > div.col-md-4 > p> #btnAdd");}
    public Element getKickOffText() { return driver.FindElementByCssSelector("#popover818726 > div.popover-content");}
    public Element getRunIncAnalyticsText() {return driver.FindElementByCssSelector("#popover207925 > div.popover-content");}
    public Element getKickOffHelpIcon() { return driver.FindElementByCssSelector("#iss1 > section:nth-child(5) > div > div:nth-child(1) > label>#IngestionErrorsHelp");}
    public Element getRunIncAnalytics() {return driver.FindElementByCssSelector("#iss1 > section:nth-child(5) > div > div:nth-child(2) > label>#IngestionErrorsHelp");}

    public IngestionPage(Driver driver){

        this.driver = driver;
        //this.driver.getWebDriver().get(Input.url+"Ingestion/Home");
        base = new BaseClass(driver);
        driver.waitForPageToBeReady();

    }
    
    public void requiredFieldsAreEntered(boolean scriptState, HashMap dataMap) 
    		throws InterruptedException {
    	
    	driver.waitForPageToBeReady();
    	if (scriptState) {
	    	if(dataMap.containsKey("source_system")) {
	    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			getSpecifySourceSystem().Visible()  ;}}), Input.wait30); 
	    		getSpecifySourceSystem().SendKeys(dataMap.get("source_system").toString());
    		}
	    	if(dataMap.containsKey("source_location")) {
		    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			getSpecifyLocation().Visible()  ;}}), Input.wait30); 
	    		getSpecifyLocation().SendKeys(dataMap.get("source_location").toString());
	    	}
	    	
	    	if(dataMap.containsKey("source_folder")) {	
	    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getSpecifySourceFolder().Displayed()  ;}}), Input.wait30); 
	    		getSpecifySourceFolder().SendKeys(dataMap.get("source_folder").toString());    		
	    	}
	    	
	    	if(dataMap.containsKey("doc_key")) {	
	    		//getDAToptions(dataMap); 
	    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    				getDocumentKey().Visible()  ;}}), Input.wait30);
	    		getDocumentKey().SendKeys(dataMap.get("doc_key").toString());
	    	}
    		
	    	if(dataMap.containsKey("native_file") || dataMap.containsKey("native_path_file")) {
	    		getNativeoptions(dataMap);
	    	}
	    	
	    	if(dataMap.containsKey("text_file") || dataMap.containsKey("text_path_file")){
	    		getTextoptions(dataMap);	
	    	}
	    	
	    	if(dataMap.containsKey("PDF_file") || dataMap.containsKey("PDF_path_file")) {
	    		getPDFoptions(dataMap);
	    	}
	    	
	    	if(dataMap.containsKey("TIFF_file")) {
	    		getTIFFoptions(dataMap);	
	    	}
	
	    	if(dataMap.containsKey("mp3_file") || dataMap.containsKey("mp3_path_file")) {
	
	    		getMP3options(dataMap);	
	    	}
	    	
	    	if(dataMap.containsKey("audio_file") || dataMap.containsKey("audio_path_file")) {
	    		getTransciptoptions(dataMap);	
	    	}
	        
	    	if(dataMap.containsKey("other_file") || dataMap.containsKey("other_path_file")) {
	    		getTransciptoptions(dataMap);	
	    	}
	    	
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getDateFormat().Visible()  ;}}), Input.wait30); 
    		getDateFormat().SendKeys(dataMap.get("date_time").toString());

		} else {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getAudioTranscriptCheckBoxstionButton().Visible()  ;}}), Input.wait30); 
    		getAudioTranscriptCheckBoxstionButton().Click();

    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getAudioTranscriptLST().Visible()  ;}}), Input.wait30); 
    		getAudioTranscriptLST().SendKeys("audio_file");
		}

    }
    
    public void getDAToptions(HashMap dataMap) throws InterruptedException {

	    if(getDATCheckbox().Selected() == true) {	
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			getDATCheckbox().Visible()  ;}}), Input.wait30); 
	    	getDATCheckbox().Click();
	    }	

	    if(dataMap.containsKey("dat_file")) {
        	System.out.print("inIFclikec");
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			getDATLoadFile().Visible()  ;}}), Input.wait30); 
	    	getDATLoadFile().SendKeys(dataMap.get("dat_file").toString());
	    }
	    
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocumentKey().Enabled()  ;}}), Input.wait30);
		getDocumentKey().SendKeys(dataMap.get("doc_key").toString());
		
    }
    
    public void getNativeoptions(HashMap dataMap) throws InterruptedException {
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNativeCheckBox().Visible()  ;}}), Input.wait30); 
    	getNativeCheckBox().Click();
    	
    	if(dataMap.containsKey("")) {
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getNativePathCheckbox().Visible()  ;}}), Input.wait30); 
    		getNativePathCheckbox().Click();
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getFilePathNative().Visible()  ;}}), Input.wait30); 
        	getFilePathNative().SendKeys(dataMap.get("native_path_file").toString());
    	}
    	else{
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getNativeLST().Visible()  ;}}), Input.wait30); 
        	getNativeLST().SendKeys(dataMap.get("native_file").toString());	
    	}
    }
    
    public void getTextoptions(HashMap dataMap) throws InterruptedException{
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getTextCheckBox().Visible()  ;}}), Input.wait30); 
    	getTextCheckBox().Click();
    	
    	if(dataMap.containsKey("text_path_file")) {
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getIsTextInPathInDAT().Visible()  ;}}), Input.wait30); 
    		getIsTextInPathInDAT().Click();
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getTextFilePathFieldinDAT().Visible()  ;}}), Input.wait30); 
        	getTextFilePathFieldinDAT().SendKeys(dataMap.get("text_path_file").toString());
    	}
    	else{
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		getTextLST().Visible()  ;}}), Input.wait30); 
        	getTextLST().SendKeys(dataMap.get("test_file").toString());	
    	}
    	
    }
    
    public void getPDFoptions(HashMap dataMap) throws InterruptedException{
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getPDFCheckBoxstionButton().Visible()  ;}}), Input.wait30); 
    	getPDFCheckBoxstionButton().Click();   
    	
    	if(dataMap.containsKey("PDF_path_file")) {
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getIsPDFInPathInDAT().Visible()  ;}}), Input.wait30); 
    		getIsPDFInPathInDAT().Click();  
    		
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getPDFFilePathFieldinDAT().Visible()  ;}}), Input.wait30); 
    		getPDFFilePathFieldinDAT().SendKeys(dataMap.get("PDF_path_file").toString());  
    	}
    	else {
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getPDFLST().Visible()  ;}}), Input.wait30); 
    		getPDFLST().SendKeys(dataMap.get("PDF_file").toString()); 
    	}
    }
    
    public void getTIFFoptions(HashMap dataMap) throws InterruptedException{
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getTIFFCheckBox().Visible()  ;}}), Input.wait30); 
    	getTIFFCheckBox().Click();   
    	
    	if(dataMap.containsKey("TIFF_path_file")) {
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getIsTIFFInPathInDAT().Visible()  ;}}), Input.wait30); 
    		getIsTIFFInPathInDAT().Click();  
    		
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getTIFFFilePathFieldinDAT().Visible()  ;}}), Input.wait30); 
    		getTIFFFilePathFieldinDAT().SendKeys(dataMap.get("TIFF_path_file").toString());  
    	}
    	else {
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getTIFFLST().Visible()  ;}}), Input.wait30); 
    		getTIFFLST().SendKeys(dataMap.get("TIFF_file").toString()); 
    	}
    	
    	if(dataMap.get("").toString().equals("true")) {
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getgenerateSearchablePDFs().Visible()  ;}}), Input.wait30); 
    		getgenerateSearchablePDFs().Click();  
    	}
    }
    
    public void getMP3options(HashMap dataMap) throws InterruptedException{
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getMP3CheckBoxstionButton().Visible()  ;}}), Input.wait30); 
		getMP3CheckBoxstionButton().Click();
		
		if(dataMap.containsKey("mp3_path_file")) {
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getIsMP3InPathInDAT().Visible()  ;}}), Input.wait30); 
    		getIsMP3InPathInDAT().Click();  
    		
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getTIFFFilePathFieldinDAT().Visible()  ;}}), Input.wait30); 
    		getTIFFFilePathFieldinDAT().SendKeys(dataMap.get("mp3_path_file").toString());  
    	}
    	else {
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getMP3LST().Visible()  ;}}), Input.wait30); 
    		getMP3LST().SendKeys(dataMap.get("mp3_file").toString()); 
		}
    }
    
    public void getTransciptoptions(HashMap dataMap) throws InterruptedException{
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getAudioTranscriptCheckBoxstionButton().Visible()  ;}}), Input.wait30); 
    	getAudioTranscriptCheckBoxstionButton().Click();

    	if(dataMap.containsKey("audio_path_file")) {
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getIsTransciptInPathInDAT().Visible()  ;}}), Input.wait30); 
    		getIsTransciptInPathInDAT().Click();  
    		
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getTransciptFilePathFieldinDAT().Visible()  ;}}), Input.wait30); 
    		getTransciptFilePathFieldinDAT().SendKeys(dataMap.get("audio_path_file").toString());  
    	}
    	else {
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getAudioTranscriptLST().Visible()  ;}}), Input.wait30); 
    		getAudioTranscriptLST().SendKeys(dataMap.get("audio_file").toString());; 
    		System.out.print("audio_file");
    	}
    }
    
    public void getOtheroptions(HashMap dataMap) throws InterruptedException{
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getOtherCheckBox().Visible()  ;}}), Input.wait30); 
    	getOtherCheckBox().Click();
    	
    	if(dataMap.containsKey("Other_linktype")){
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			getOtherLinkType().Visible()  ;}}), Input.wait30); 
	    	getOtherLinkType().SendKeys(dataMap.get("other_linktype").toString());;;
    	}
    	
    	if(dataMap.containsKey("other_path_file")) {
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getIsOtherInPathInDAT().Visible()  ;}}), Input.wait30); 
    		getIsOtherInPathInDAT().Click();  
    		
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getOtherFilePathFieldinDAT().Visible()  ;}}), Input.wait30); 
    		getOtherFilePathFieldinDAT().SendKeys(dataMap.get("other_path_file").toString());  
    	}
    	else {
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getOtherLoadFile().Visible()  ;}}), Input.wait30); 
    		getOtherLoadFile().SendKeys(dataMap.get("other_file").toString());; 
    	}
    }
    
    
    
    public void openFirstIngestionSettings(boolean scriptState) 
    		throws InterruptedException {
    	
    	if (scriptState) {
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getFilterByButton().Displayed()  ;}}), Input.wait30); 
    		getFilterByButton().Click();
    		Thread.sleep(500);
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getFilterByDRAFT().Displayed()  ;}}), Input.wait30); 
    		getFilterByDRAFT().Click();
    		getTotalIngestCount().Click();
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			getFirstIngestionActionButton().Displayed()  ;}}), Input.wait30); 
	    	getFirstIngestionActionButton().Click();
		} else {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getAudioTranscriptCheckBoxstionButton().Visible()  ;}}), Input.wait30); 
    		getAudioTranscriptCheckBoxstionButton().Click();
		}
		
    }
    
    public void clearSourceDatFields(boolean scriptState) 
    		throws InterruptedException {
    	
    	if (scriptState) {
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getFilterByButton().Visible()  ;}}), Input.wait30); 
    		getFilterByButton().Click();
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getFilterByDRAFT().Visible()  ;}}), Input.wait30); 
    		getFilterByDRAFT().Click();
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			getFirstIngestionActionButton().Displayed()  ;}}), Input.wait30); 
	    	getFirstIngestionActionButton().Click();
		} else {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getAudioTranscriptCheckBoxstionButton().Visible()  ;}}), Input.wait30); 
    		getAudioTranscriptCheckBoxstionButton().Click();
		}
		
    }

    
    public void AddOnlyNewIngestion(String dataset) throws InterruptedException {
		
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAddanewIngestionButton().Enabled()  ;}}), Input.wait30); 
    	getAddanewIngestionButton().Click();
    	
    	// Select Source System
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSpecifySourceSystem().Visible()  ;}}), Input.wait30); 
    	getSpecifySourceSystem().selectFromDropdown().selectByVisibleText("TRUE");
    	
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSpecifyLocation().Visible()  ;}}), Input.wait30); 
    	for (int i = 0; i < 30; i++) {
			try {
				getSpecifyLocation().selectFromDropdown().selectByVisibleText(Input.SourceLocation);
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSpecifySourceFolder().Visible()   ;}}), Input.wait30); 
    	
    	for (int i = 0; i < 30; i++) {
			try {
				if(dataset.contains("Automation_Collection1K_Tally")) {
				getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.Collection1KFolder);
				}
				else if (dataset.contains("Automation_20Family_20Threaded")) {
					getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.FamilyFolder);
				}
				else if (dataset.contains("Automation_AllSources")) {
					getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.AllSourcesFolder);
				}
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getDATDelimitersFieldSeparator().Visible()  ;}}), Input.wait30); 
    	
    
    	for (int i = 0; i < 30; i++) {
			try {
				getDATDelimitersFieldSeparator().selectFromDropdown().selectByVisibleText("ASCII(20)");
			} catch (Exception e) {
				Thread.sleep(1000);
			}
		}
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getDATDelimitersTextQualifier().Visible()  ;}}), Input.wait30); 
    	getDATDelimitersTextQualifier().selectFromDropdown().selectByVisibleText("ASCII(254)");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getDATDelimitersNewLine().Visible()  ;}}), Input.wait30); 
    	getDATDelimitersNewLine().selectFromDropdown().selectByVisibleText("ASCII(174)");
    	
    	driver.scrollingToBottomofAPage();    	
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSourceSelectionDATLoadFile().Visible() ;}}), Input.wait30); 
    	
    	// getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(Input.AllSourcesDATFile);
    	    	
    	 if(dataset.contains("Automation_Collection1K_Tally")) {
    		 getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(Input.Collection1KDATFile);
			}
			else if (dataset.contains("Automation_20Family_20Threaded")) {
				getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(Input.FamilyDATFile);
			}
			else if (dataset.contains("Automation_AllSources")) {
				getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(Input.AllSourcesDATFile);
			}
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSourceSelectionDATKey().Visible() ;}}), Input.wait30); 
    	//getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText(Input.AllSourcesDockey);
    	Thread.sleep(3000);
     	 if(dataset.contains("Automation_Collection1K_Tally")) {
     		getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText(Input.Collection1KDockey);
			}
			else if (dataset.contains("Automation_20Family_20Threaded")) {
				getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText(Input.FamilyDockey);
			}
			else if (dataset.contains("Automation_AllSources")) {
				getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText(Input.AllSourcesDockey);
			}
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSourceSelectionText().Enabled()  ;}}), Input.wait30); 
    	getSourceSelectionText().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSourceSelectionTextLoadFile().Visible()  ;}}), Input.wait30); 
    	
    	   if(dataset.contains("Automation_Collection1K_Tally")) {
    		   getSourceSelectionTextLoadFile().selectFromDropdown().selectByVisibleText(Input.Collection1KTextFile);
 			}
 			else if (dataset.contains("Automation_20Family_20Threaded")) {
 				getSourceSelectionTextLoadFile().selectFromDropdown().selectByVisibleText(Input.FamilyTextFile);
 			}
 			else if (dataset.contains("Automation_AllSources")) {
 				getSourceSelectionTextLoadFile().selectFromDropdown().selectByVisibleText(Input.AllSourcesTextFile);
 			}
    	//getSourceSelectionTextLoadFile().selectFromDropdown().selectByVisibleText("DAT4_STC_Text.lst");
    	
    	if (dataset.contains("Automation_AllSources")|| dataset.contains("Automation_20Family_20Threaded")) {
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getNativeCheckBox().Visible()  ;}}), Input.wait30); 
    	getNativeCheckBox().Click();
    	    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getNativeLST().Visible()  ;}}), Input.wait30); 

 	       if (dataset.contains("Automation_20Family_20Threaded")) {
 	    	  getNativeLST().selectFromDropdown().selectByVisibleText(Input.FamilyNativeFile);
			}
			else if (dataset.contains("Automation_AllSources")) {
				getNativeLST().selectFromDropdown().selectByVisibleText(Input.AllSourcesNativeFile);
			}
 
}
    	   else
           {
         	  System.out.println("No need to select Native");
           }
    	if (dataset.contains("Automation_AllSources")) {
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getPDFCheckBoxstionButton().Enabled()  ;}}), Input.wait30); 
    	getPDFCheckBoxstionButton().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getPDFLST().Visible()  ;}}), Input.wait30); 
    	getPDFLST().selectFromDropdown().selectByVisibleText("PDFs.lst");
    	
    	driver.scrollingToBottomofAPage();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getTIFFCheckBox().Enabled()  ;}}), Input.wait30); 
    	getTIFFCheckBox().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSourceSelectionTextLoadFile().Visible()  ;}}), Input.wait30); 
    	getTIFFLST().selectFromDropdown().selectByVisibleText("Images.lst");
    	
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMP3CheckBoxstionButton().Enabled()  ;}}), Input.wait30); 
    	getMP3CheckBoxstionButton().Click();
    	
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSourceSelectionTextLoadFile().Visible()  ;}}), Input.wait30); 
    	getMP3LST().selectFromDropdown().selectByVisibleText("MP3.lst");
    	
    	driver.scrollingToBottomofAPage();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAudioTranscriptCheckBoxstionButton().Enabled()  ;}}), Input.wait30); 
    	getAudioTranscriptCheckBoxstionButton().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSourceSelectionTextLoadFile().Visible()  ;}}), Input.wait30); 
    	getAudioTranscriptLST().selectFromDropdown().selectByVisibleText("Transcript.lst");
    	
    	driver.scrollingToBottomofAPage();
    	    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getOtherCheckBox().Enabled()  ;}}), Input.wait30); 
    	getOtherCheckBox().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getOtherLoadFile().Visible()  ;}}), Input.wait30); 
    	getOtherLoadFile().selectFromDropdown().selectByVisibleText("Translation.lst");
    	}
    	 else
         {
       	  System.out.println("No need to select PDF and MP3");
         }
    	
    	//select date format
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getDateFormat().Visible()  ;}}), Input.wait30); 
    	getDateFormat().selectFromDropdown().selectByVisibleText("YYYY/MM/DD HH:MM:SS");
    	
    	driver.scrollPageToTop();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getNextButton().Visible()  ;}}), Input.wait30); 
    	getNextButton().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMappingSOURCEFIELD2().Visible()  ;}}), Input.wait30); 
    	 if(dataset.contains("Automation_Collection1K_Tally")) {
    		 getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText(Input.Collection1KDockey);
 			}
 			else if (dataset.contains("Automation_20Family_20Threaded")) {
 				getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("ProdEnd");
 			}
 			else if (dataset.contains("Automation_AllSources")) {
 				getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText(Input.AllSourcesDockey);
 			}
    	//getMappingSOURCEFIELD2().selectFromDropdown().selectByVisibleText("ProdBeg");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMappingSOURCEFIELD3().Visible()  ;}}), Input.wait30); 
    	if(dataset.contains("Automation_Collection1K_Tally")) {
   		 getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("Datasource");
			}
			else if (dataset.contains("Automation_20Family_20Threaded")) {
				getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText("Datasource");
			}
			else if (dataset.contains("Automation_AllSources")) {
				getMappingSOURCEFIELD3().selectFromDropdown().selectByVisibleText(Input.AllSourcesDockey);
			}
        	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMappingSOURCEFIELD4().Visible()  ;}}), Input.wait30); 
    	getMappingSOURCEFIELD4().selectFromDropdown().selectByVisibleText("Custodian");
    	
       	if(dataset.contains("Automation_Collection1K_Tally")||dataset.contains("Automation_AllSources")) {
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMappingFIELDCAT25().Visible()  ;}}), Input.wait30); 
    	getMappingFIELDCAT25().selectFromDropdown().selectByVisibleText("DOCBASIC");
    	getMappingDESTINATIONFIELD25().selectFromDropdown().selectByVisibleText("DocFileExtension");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMappingFIELDCAT26().Visible()  ;}}), Input.wait30); 
    	getMappingFIELDCAT26().selectFromDropdown().selectByVisibleText("DOCBASIC");
    	getMappingDESTINATIONFIELD26().selectFromDropdown().selectByVisibleText("DocFileName");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMappingFIELDCAT27().Visible()  ;}}), Input.wait30); 
    	getMappingFIELDCAT27().selectFromDropdown().selectByVisibleText("DOCBASIC");
    	getMappingDESTINATIONFIELD27().selectFromDropdown().selectByVisibleText("DocFileSize");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMappingFIELDCAT28().Visible()  ;}}), Input.wait30); 
    	getMappingFIELDCAT28().selectFromDropdown().selectByVisibleText("DOCBASIC");
    	getMappingDESTINATIONFIELD28().selectFromDropdown().selectByVisibleText("DocFileType");
    	}
    	else {
        	System.out.println("No need to selected 'Field 25 to 28' from here");
        }
    	
    	if (dataset.contains("Automation_20Family_20Threaded")) {
    		
    
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMappingFIELDCAT8().Visible()  ;}}), Input.wait30); 
    	getMappingFIELDCAT8().selectFromDropdown().selectByVisibleText("EMAIL");
    	getMappingDESTINATIONFIELD8().selectFromDropdown().selectByVisibleText("EmailAuthorName");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMappingFIELDCAT9().Visible()  ;}}), Input.wait30); 
    	getMappingFIELDCAT9().selectFromDropdown().selectByVisibleText("EMAIL");
    	getMappingDESTINATIONFIELD9().selectFromDropdown().selectByVisibleText("EmailAuthorAddress");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMappingFIELDCAT10().Visible()  ;}}), Input.wait30); 
    	getMappingFIELDCAT10().selectFromDropdown().selectByVisibleText("EMAIL");
    	getMappingDESTINATIONFIELD10().selectFromDropdown().selectByVisibleText("EmailBCCAddresses");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMappingFIELDCAT11().Visible()  ;}}), Input.wait30); 
    	getMappingFIELDCAT11().selectFromDropdown().selectByVisibleText("EMAIL");
    	getMappingDESTINATIONFIELD11().selectFromDropdown().selectByVisibleText("EmailBCCNames");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMappingFIELDCAT13().Visible()  ;}}), Input.wait30); 
    	getMappingFIELDCAT13().selectFromDropdown().selectByVisibleText("EMAIL");
    	getMappingDESTINATIONFIELD13().selectFromDropdown().selectByVisibleText("EmailCCAddresses");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMappingFIELDCAT14().Visible()  ;}}), Input.wait30); 
    	getMappingFIELDCAT14().selectFromDropdown().selectByVisibleText("EMAIL");
    	getMappingDESTINATIONFIELD14().selectFromDropdown().selectByVisibleText("EmailCCNames");
    
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMappingFIELDCAT25().Visible()  ;}}), Input.wait30); 
    	getMappingFIELDCAT25().selectFromDropdown().selectByVisibleText("FAMILY");
    	getMappingDESTINATIONFIELD25().selectFromDropdown().selectByVisibleText("FamilyRelationship");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMappingFIELDCAT26().Visible()  ;}}), Input.wait30); 
    	getMappingFIELDCAT26().selectFromDropdown().selectByVisibleText("DOCBASIC");
    	getMappingDESTINATIONFIELD26().selectFromDropdown().selectByVisibleText("DocFileExtension");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMappingFIELDCAT27().Visible()  ;}}), Input.wait30); 
    	getMappingFIELDCAT27().selectFromDropdown().selectByVisibleText("DOCBASIC");
    	getMappingDESTINATIONFIELD27().selectFromDropdown().selectByVisibleText("DocFileName");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMappingFIELDCAT28().Visible()  ;}}), Input.wait30); 
    	getMappingFIELDCAT28().selectFromDropdown().selectByVisibleText("DOCBASIC");
    	getMappingDESTINATIONFIELD28().selectFromDropdown().selectByVisibleText("DocFileSize");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMappingFIELDCAT29().Visible()  ;}}), Input.wait30); 
    	getMappingFIELDCAT29().selectFromDropdown().selectByVisibleText("DOCBASIC");
    	getMappingDESTINATIONFIELD29().selectFromDropdown().selectByVisibleText("DocFileType");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMappingFIELDCAT31().Visible()  ;}}), Input.wait30); 
    	getMappingFIELDCAT31().selectFromDropdown().selectByVisibleText("FAMILY");
    	getMappingDESTINATIONFIELD31().selectFromDropdown().selectByVisibleText("FamilyID");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMappingFIELDCAT49().Visible()  ;}}), Input.wait30); 
    	getMappingFIELDCAT49().selectFromDropdown().selectByVisibleText("EMAIL");
    	getMappingDESTINATIONFIELD49().selectFromDropdown().selectByVisibleText("EmailReceivedDate");
    	
      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMappingFIELDCAT50().Visible()  ;}}), Input.wait30); 
    	getMappingFIELDCAT50().selectFromDropdown().selectByVisibleText("EMAIL");
    	getMappingDESTINATIONFIELD50().selectFromDropdown().selectByVisibleText("EmailToNames");
        	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getMappingFIELDCAT51().Visible()  ;}}), Input.wait30); 
    	getMappingFIELDCAT51().selectFromDropdown().selectByVisibleText("EMAIL");
    	getMappingDESTINATIONFIELD51().selectFromDropdown().selectByVisibleText("EmailToAddresses");
    	} 
    	else {
			System.out.println("No need to select fields for this dataset'");
		 }
			
    	IngestionCatlogtoPublish(dataset);
    	    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getIngestionName().Visible()  ;}}),Input.wait60); 
        getIngestionName().Click();
        
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		getIngestionNameText().Visible()  ;}}),Input.wait60); 
     	IngestionName = getIngestionNameText().getText();
		Thread.sleep(2000);
		System.out.println(IngestionName);
  	}
 
    public void ReIngestionofNativeWithOverlay(String dataset) throws InterruptedException {
    	
    	 //Publish        
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFilterByButton().Visible();}}), Input.wait30); 
    	getFilterByButton().waitAndClick(Input.wait30);
    
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFilterByPUBLISHED().Visible()  ;}}), Input.wait30); 
    	getFilterByPUBLISHED().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getIngestionActionButton().Displayed()  ;}}), Input.wait30); 
    	getIngestionActionButton().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getIngestionAction_Copy().Displayed()  ;}}), Input.wait30); 
    	getIngestionAction_Copy().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSpecifySourceFolder().Visible()   ;}}), Input.wait30); 
    	
    	 getSpecifySourceFolder().selectFromDropdown().selectByVisibleText(Input.AllSourcesFolder);
    	 
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			getSpecifySourceFolder().Visible()   ;}}), Input.wait30); 
    	 getIngestion_IngestionType().selectFromDropdown().selectByVisibleText("Overlay Only");
    	 
    	 driver.scrollingToBottomofAPage(); 
    	 
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getSourceSelectionDATLoadFile().Visible()   ;}}), Input.wait30); 
    	 getSourceSelectionDATLoadFile().selectFromDropdown().selectByVisibleText(Input.AllSourcesDATFile);
    	 
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getSourceSelectionDATKey().Visible()   ;}}), Input.wait30); 
		 getSourceSelectionDATKey().selectFromDropdown().selectByVisibleText(Input.AllSourcesDockey);
		
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getNativeCheckBox().Visible()  ;}}), Input.wait30); 
    	 getNativeCheckBox().Click();
    	    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getNativeLST().Visible()  ;}}), Input.wait30); 

 	  	getNativeLST().selectFromDropdown().selectByVisibleText(Input.AllSourcesNativeFile);
	
        driver.scrollPageToTop();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getNextButton().Visible()  ;}}), Input.wait30); 
    	getNextButton().Click();
    	
    	IngestionCatlogtoPublish(dataset);
    	
    
    	//***************************Verify in DocView Default tab should come as Native*********************
		
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getIngestionName().Visible()  ;}}),Input.wait60); 
        getIngestionName().Click();
        
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		getIngestionNameText().Visible()  ;}}),Input.wait60); 
     	IngestionName = getIngestionNameText().getText();
		Thread.sleep(2000);
		System.out.println(IngestionName);
      }
    
    
    public void IngestionCatlogtoPublish(String dataset) throws InterruptedException {
    	
		driver.scrollPageToTop();
    	    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getPreviewRun().Visible()  ;}}), Input.wait30); 
    	getPreviewRun().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getApproveMessageOKButton().Visible()  ;}}), Input.wait30); 
    	getApproveMessageOKButton().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getbtnRunIngestion().Visible()  ;}}), Input.wait30); 
    	getbtnRunIngestion().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFilterByButton().Visible()  ;}}), Input.wait30); 
    	getFilterByButton().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFilterByFAILED().Visible()  ;}}), Input.wait30); 
    	getFilterByFAILED().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFilterByCATALOGED().Visible()  ;}}), Input.wait30); 
    	getFilterByCATALOGED().Click();
    	
    	//catlogging
    	for (int i = 0; i < 30; i++)
		{
			try{	
				getCatalogedIngestionStatus().Displayed();
				System.out.println("Catlogged");
				break;
			}catch (Exception e) {
						
					try
					{
						Thread.sleep(5000);
						getRefreshButton().Click();
					   if(getFailedIngestionStatus().Displayed()){ 
							System.exit(0);
						
							}
					}
					catch (Throwable e1)
					{
						System.out.println("Continue");
					}
			}
	}
    	
    	//copy
    	getRefreshButton().Click();
    	    		
        getIngestionName().waitAndClick(Input.wait30);
    
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getRunCopying().Visible()  ;}}),Input.wait30);
    	Thread.sleep(6000);
    	getRunCopying().ScrollTo();
        getRunCopying().Click();
        
        base.VerifySuccessMessage("Ingestion copy has Started.");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getCloseButton().Enabled()  ;}}), Input.wait30); 
    	getCloseButton().Click();	
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFilterByButton().Visible()  ;}}), Input.wait30); 
    	getFilterByButton().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFilterByCOPIED().Visible()  ;}}), Input.wait30); 
    	getFilterByCOPIED().Click();
    	
    	
    	for (int i = 0; i < 120; i++)
		{
				try{
					getCopiedIngestionStatus().Displayed();
					System.out.println("Copied");
					break;
				}catch (Exception e) {
				
				try
				{
					Thread.sleep(5000);
					getRefreshButton().Click();
					if(getFailedIngestionStatus().Displayed()){
					System.exit(0);
				
					}
				}
				catch (Throwable e1)
				{
					System.out.println("Continue");
				}
			
			}
		}
    	
    	//Indexing
    	getRefreshButton().Click();
    	    		
       getIngestionName().waitAndClick(Input.wait30);
       
       Actions actions = new Actions(driver.getWebDriver());
       actions.moveToElement(getIndexBlock().getWebElement());
       actions.perform();
       
       if(dataset.contains("Automation_AllSources")) {
       
       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		   getIsAudioCheckbox().Visible()  ;}}),Input.wait60); 
        getIsAudioCheckbox().Click();
    	
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		getLanguage().Visible()  ;}}),Input.wait60); 
        getLanguage().selectFromDropdown().selectByVisibleText("North American English");
       }
       else {
    	   System.out.println("No need to select for other datasets");
       }
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getRunIndexing().Visible()  ;}}),Input.wait60); 
        getRunIndexing().Click();
        
        base.VerifySuccessMessage("Ingestion Indexing has Started.");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getCloseButton().Enabled()  ;}}), Input.wait30); 
    	getCloseButton().Click();	
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFilterByButton().Visible()  ;}}), Input.wait30); 
    	getFilterByButton().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFilterByINDEXED().Visible()  ;}}), Input.wait30); 
    	getFilterByINDEXED().Click();
    	
    	
    	for (int i = 0; i < 120; i++)
		{
			
    		try{
				getIndexedIngestionStatus().Displayed();
				System.out.println("Indexed");
				break;
    		}catch (Exception e) {
				
			try
			{
				Thread.sleep(10000);
				getRefreshButton().Click();
				if(getFailedIngestionStatus().Displayed()){
					System.exit(0);
				
				}
			}
				catch (Throwable e1)
			{
				System.out.println("Continue");
			}
		}
}

    	//Approve
    	getIngestionName().waitAndClick(Input.wait30);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getActionDropdownArrow().Visible()  ;}}),Input.wait60); 
    	getActionDropdownArrow().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getActionApprove().Visible()  ;}}),Input.wait60); 
    	getActionApprove().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getApproveMessageOKButton().Visible()  ;}}), Input.wait30); 
    	getApproveMessageOKButton().Click();
    	
    	base.VerifySuccessMessage("Approve started successfully");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getCloseButton().Visible()  ;}}), Input.wait30); 
    	getCloseButton().Click();	
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFilterByButton().Visible()  ;}}), Input.wait30); 
    	getFilterByButton().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFilterByAPPROVED().Visible()  ;}}), Input.wait30); 
    	getFilterByAPPROVED().Click();
    	
    	
    	for (int i = 0; i < 30; i++)
		{
			try
			{
				getApproveIngestionStatus().Displayed();
				System.out.println("Approved");
				break;
				
			}
			catch (Exception e)
			{
				try
				{
					Thread.sleep(5000);
					getRefreshButton().Click();
					if(getFailedIngestionStatus().Displayed()){
						System.exit(0);
					
						}
				}
				catch (Throwable e1)
				{
					System.out.println("Continue");
				}
	
			}
}

    	Thread.sleep(5000); 
    	
    	//Analytics run
    	this.driver.getWebDriver().get(Input.url+"Ingestion/Analytics");
    	
    /*	if (dataset.contains("Automation_20Family_20Threaded")) {
			
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getIncrementalAnalyticsbutton().Visible()  ;}}),Input.wait60); 
    	getIncrementalAnalyticsbutton().Click();
    	}
    	 else {
			 System.out.println("No need to select incremental'");
		 }*/
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getRunAnalyticsRunButton().Visible()  ;}}),Input.wait60); 
    	getRunAnalyticsRunButton().Click();
    	
    	base.VerifySuccessMessage("Run has Started successfully");
    
    
    	for (int i = 0; i < 120; i++)
		{
			try
			{
				
		    	getRunAnalyticsPublishButton().Click();
				System.out.println("Analytics Completed");
				break;
				
			}
			catch (Exception e)
			{
				try
				{
					Thread.sleep(5000);
					driver.getWebDriver().navigate().refresh();
					if(getFailedIngestionStatus().Displayed()){
						System.exit(0);
					
						}
				}
				catch (Throwable e1)
				{
					System.out.println("Continue"+i);
				}
			
			}
		}

    	base.VerifySuccessMessage("Publish has Started successfully");
    	
    	this.driver.getWebDriver().get(Input.url+"Ingestion/Home");
    
      //Publish        
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFilterByButton().Visible();}}), Input.wait30); 
    	getFilterByButton().waitAndClick(Input.wait30);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFilterByFAILED().Visible()  ;}}), Input.wait30); 
    	getFilterByFAILED().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFilterByPUBLISHED().Visible()  ;}}), Input.wait30); 
    	getFilterByPUBLISHED().Click();
    	
    	
    	for (int i = 0; i < 10; i++)
		{
			try
			{
				
				getPublishIngestionStatus().Displayed();
				System.out.println("Published");
				break;
			}
			catch (Exception e)
			{
				try
				{
					Thread.sleep(5000);
					getRefreshButton().Click();
					if(getFailedIngestionStatus().Displayed()){
						System.exit(0);
					
						}
				}
				catch (Throwable e1)
				{
					System.out.println("Continue");
				}
				
			}

		}
    	
    }
    
} 
 
