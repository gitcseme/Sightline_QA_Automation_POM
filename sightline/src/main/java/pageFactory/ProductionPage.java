package pageFactory;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import testScriptsSmoke.Input;

public class ProductionPage {

    Driver driver;
    BaseClass base;
    public String ProdPathallredact;
    public String ProdPathsomeredact;
    //public String tagname;
    public String templatename;
    public String productionname;
    public SoftAssert assertion = new SoftAssert();
    public WebDriverWait wait;
    public WebDriverWait generateWait;
    
    public Element getAddNewProductionbutton(){ return driver.FindElementByXPath("//a[contains(.,'Add a New Production')]"); }
    public Element getProductionName(){ return driver.FindElementById("ProductionName"); }
    public Element getProductionDesc(){ return driver.FindElementById("ProductionDescription"); }
    public Element getBasicInfoMarkComplete(){ return driver.FindElementById("BasicInfoMarkComplete"); }
    public Element getBasicInfoSave(){ return driver.FindElementById("BasicInfoSave"); }
    public Element getBasicInfoNext(){ return driver.FindElementById("BasicInfoNext"); }
   
    public Element getDAT_FieldClassification1(){ return driver.FindElementById("TY_0"); }
    public Element getDAT_FieldClassification2(){ return driver.FindElementById("TY_1"); }
    public Element getDAT_FieldClassification3(){ return driver.FindElementById("TY_1"); }
    public Element getDAT_SourceField1(){ return driver.FindElementById("SF_0"); }
    public Element getDAT_SourceField2(){ return driver.FindElementById("SF_1"); }
    public Element getDAT_SourceField3() { return driver.FindElementById("SF_2"); }
    public Element getDAT_DATField1(){ return driver.FindElementById("DATFL_0"); }
    public Element getDAT_DATField2(){ return driver.FindElementById("DATFL_1"); }
    public Element getDAT_DATField3(){ return driver.FindElementById("DATFL_2"); }
    public Element getTIFF_CenterHeaderBranding(){ return driver.FindElementById("CenterHeaderBranding"); }
    public Element getPDF_InsertMetadataField(){ return driver.FindElementById("LaunchPDFeditor_0"); }
    public Element getPDF_CenterHeaderBranding(){ return driver.FindElementById("PDFCenterHeaderBranding"); }
    public Element getTIFF_CenterFooterBranding(){ return driver.FindElementById("CenterFooterBranding"); }
    public Element getTIFF_InsertMetadataField(){ return driver.FindElementById("Launcheditor_0"); }
    public Element getTIFF_selectedMetadataField(){ return driver.FindElementById("selectedMetadataField"); }
    public Element getTIFF_selectedMetadataField_Ok(){ return driver.FindElementByXPath("//*[@onclick='return AddToRedactor()']"); }
    
    public Element getComponentsMarkComplete(){ return driver.FindElementById("ComponentsMarkComplete"); }
    public Element getComponentsMarkNext(){ return driver.FindElementById("ComponentsNext"); }
    public Element getBeginningBates(){ return driver.FindElementById("txtBeginningBatesID"); }
    public Element gettxtBeginningBatesIDPrefix(){ return driver.FindElementById("txtBeginningBatesIDPrefix"); }
    public Element gettxtBeginningBatesIDSuffix(){ return driver.FindElementById("txtBeginningBatesIDSuffix"); }
    public Element gettxtBeginningBatesIDMinNumLength(){ return driver.FindElementById("txtBeginningBatesIDMinNumLength"); }
    public Element getlstSortingMetaData(){ return driver.FindElementById("lstSortingMetaData"); }
    public Element getlstSortingOrder(){ return driver.FindElementById("lstSortingOrder"); }
    public Element getlstSubSortingMetaData(){ return driver.FindElementById("lstSubSortingMetaData"); }
    public Element getlstSubSortingOrder(){ return driver.FindElementById("lstSubSortingOrder"); }
    public Element getNumAndSortMarkComplete(){ return driver.FindElementById("NumAndSortMarkComplete"); }
    public Element getNumAndSortNext(){ return driver.FindElementById("NumAndSortNext"); }
    public Element getbtnDocumentsSelectionMarkComplete(){ return driver.FindElementById("btnDocumentsSelectionMarkComplete"); }
    public Element getbtnDocumentsSelectionNext(){ return driver.FindElementById("btnDocumentsSelectionNext"); }
    public Element getbtnProductionGuardMarkComplete(){ return driver.FindElementById("btnProductionGuardMarkComplete"); }
    public Element getbtnProductionGuardNext(){ return driver.FindElementById("btnProductionGuardNext"); }
    public Element getOkButton(){ return driver.FindElementById("bot1-Msg1"); }
    public Element getlstProductionRootPaths(){ return driver.FindElementById("lstProductionRootPaths"); }
    public Element getProductionOutputLocation_ProductionDirectory(){ return driver.FindElementById("ProductionOutputLocation_ProductionDirectory"); }
    public Element getbtnProductionLocationMarkComplete(){ return driver.FindElementById("btnProductionLocationMarkComplete"); }
    public Element getbtnProductionLocationNext(){ return driver.FindElementById("btnProductionLocationNext"); }
    public Element getbtnProductionSummaryMarkComplete(){ return driver.FindElementById("btnProductionSummaryMarkComplete"); }
    public Element getbtnProductionSummaryNext(){ return driver.FindElementById("btnProductionSummaryNext"); }
    public Element getbtnProductionGenerate(){ return driver.FindElementById("btnProductionGenerate"); }
    public Element getProductionSettxt(){ return driver.FindElementById("ProductionSettxt"); }
    public Element getbtnGenerateMarkComplete(){ return driver.FindElementByXPath("//*[@id='btnGenerateMarkComplete']"); }
    public Element getbtnReGenerateMarkComplete(){ return driver.FindElementById("btnProductionReGenerate"); }
    public Element getbtnSummaryNext(){ return driver.FindElementById("btnGenerateNext"); }
    
    
    public Element getDATChkBox(){ return driver.FindElementByXPath("//input[@name='IsDATSelected']/following-sibling::i"); }
    public Element getDATLabel(){ return driver.FindElementByXPath("//*[@id=\"accordion\"]/div[1]/div[1]/h4/a"); }
    public Element getDATTab(){ return driver.FindElementByXPath("//a[@href='#DATContainer']"); }
    public Element getDAT_AddField(){ return driver.FindElementByXPath(".//*[@id='DATContainer']//button[contains(.,'Add Field')]"); }
    public Element getNativeChkBox(){ return driver.FindElementByXPath("//input[@name='IsNativeSelected']/following-sibling::i"); }
    public Element getNativeTab(){ return driver.FindElementByXPath("//a[@href='#NativeContainer']"); }
    public Element getNative_SelectAllCheck(){ return driver.FindElementByXPath(".//*[@id='native-table']//input[@name='IsSelectAllFileTypes']/following-sibling::i"); }
    public Element getNative_GenerateLoadFileLST(){ return driver.FindElementByXPath(".//*[@id='NativeContainer']//input[@name='ProduceLoadFile']/following-sibling::i"); }
    public Element getTIFFChkBox(){ return driver.FindElementByXPath("//input[@name='IsTIFFSelected']/following-sibling::i"); }
    public Element getTIFFTab(){ return driver.FindElementByXPath("//a[@href='#TIFFContainer']"); }
    public Element getTIFF_EnterBranding(){ return driver.FindElementByXPath(".//*[@id='divCenterHeaderBranding']//div[@class='redactor-editor redactor-placeholder']"); }
    public Element getTIFF_InsertMetadataFieldOKButton(){ return driver.FindElementByXPath(".//*[@id='MetadataPopup']//button[contains(.,'OK')]"); }
    public Element getTIFF_EnableforPrivilegedDocs(){ return driver.FindElementByXPath(".//*[@id='TIFFContainer']//input[@name='CommonTIFFSettings.PlaceHolderImageSettings.EnabledforPrivDocs']/following-sibling::i"); }
    public Element getPDF_EnterBranding(){ return driver.FindElementByXPath(".//*[@id='divPDFCenterHeaderBranding']//div[@class='redactor-editor redactor-placeholder']"); }
    
    public Element getTextChkBox(){ return driver.FindElementByXPath(".//*[@id='accordion']//input[@name='IsTextSelected']/following-sibling::i"); }
    public Element getTextTab(){ return driver.FindElementByXPath("//a[@href='#TextContainer']"); }
    public Element getPDFChkBox(){ return driver.FindElementByXPath(".//*[@id='accordion']//input[@name='IsPDFSelected']/following-sibling::i"); }
    public Element getPDFTab(){ return driver.FindElementByXPath(".//*[@id='accordion']//a[@href='#PDFContainer']"); }
    public Element getKeepFamiliesTogether(){ return driver.FindElementByXPath(".//*[@id='divSortByMetadata_1']//input[@name='ProductionSortingSettings.SortByIsKeepFamiliesTogether']/following-sibling::i"); }
    public Element getSelectFolder(String foldername){ return driver.FindElementByXPath("//*[@id='folderTree']//ul[@class='jstree-children']//a[contains(.,'"+foldername+"')]"); }
  
    public Element getFolderRadioButton(){ return driver.FindElementByXPath(".//*[@id='rdbFolders']/following-sibling::i"); }
    
    public Element getIncludeFamilies(){ return driver.FindElementByXPath(".//*[@id='frmDocumentsSelection']//input[@name='ProductionDocumentsSelection.ToIncludeFamilies']/following-sibling::i"); }
    public Element getBackButton(){ return driver.FindElementByXPath("//a[@onclick='return LoadSummaryView()' and contains(.,'Back')]"); }
    public Element getMarkpopup(){ return driver.FindElementByXPath("//*[@id='AlwayShowButton']/span"); }
    public Element getProdExportSet(){ return driver.FindElementByXPath(".//*[@id='tabs-a']//a[contains(.,'Create a new production/export set')]"); }
    public Element getProductionLink(){ return driver.FindElementByXPath("(.//*[@id='pName']/a)[1]"); }
    public Element getConfirmProductionCommit(){ return driver.FindElementByXPath(".//*[@id='btnProductionConfirmation']"); }
    public Element getProductionDocCount(){ return driver.FindElementByXPath(".//*[@class='drk-gray-widget']/span[1]"); }
    public Element getReviewproductionButton(){ return driver.FindElementByXPath("//a[contains(text(),'Review Production')]"); }
    public Element getDestinationPathText(){ return driver.FindElementById("DestinationPathToCopy"); }
    
    //addition on 08/04
    public Element getPriveldge_SelectTagButton(){ return driver.FindElementById("btnSelectPrevTags"); }
    public Element getPriveldge_SelectPDFTagButton(){ return driver.FindElementById("btnSelectPDFPrevTags"); }
    public Element getPriveldge_TagTree(String tag){ return driver.FindElementByXPath("//div[@id='tagTreeTIFFComponent']//a[@data-content='"+tag+"']"); }
    public Element getPriveldge_PDFTagTree(String tag){ return driver.FindElementByXPath("(//*[@id='tagTreePDFComponent']//a[contains(text(),'"+tag+"')])[2]"); }
    public Element getPriveldge_TagTree_SelectButton(){ return driver.FindElementByXPath("//*[@class='btn btn-primary btn-sm submitSelectionTIFF']"); }
    public Element getPriveldge_PDFTagTree_SelectButton(){ return driver.FindElementByXPath("//*[@class='btn btn-primary btn-sm submitSelectionPDF']"); }
    public Element getPriveldge_TextArea(){ return driver.FindElementByXPath("//textarea[@class='TIFFPrevDocPlaceHolderText']/preceding-sibling::div"); }
    public Element getTech_Issue_TextArea(){ return driver.FindElementByXPath("//textarea[@class='TIFFTechIssueDocPlaceHolderText']/preceding-sibling::div"); }
    public Element getPriveldge_PDFTextArea(){ return driver.FindElementByXPath("//textarea[@class='PDFPrevDocPlaceHolderText']/preceding-sibling::div"); }
   
    //addition on 23/04
    public Element getProdExportSetRadioButton(){ return driver.FindElementByXPath(".//*[@id='productionSet']//input[@id='IsExportSet']/following-sibling::i"); }
    public Element getProdExport_SaveButton(){ return driver.FindElementByXPath("//button[contains(.,'Save')]"); }
    public Element getProdExport_ProductionSets(){ return driver.FindElementById("ProductionSets"); }
    public Element getProdExport_AddaNewExportSet(){ return driver.FindElementByXPath(".//*[@id='cardGrid']//a[contains(.,'Add a New Export')]"); }
    public Element getProdExport_Priorprodtoggle(){ return driver.FindElementByXPath("//*[@id='ProductionListDiv']/label[2]/i"); }
    public Element getProdExport_SelectProductionSet(){ return driver.FindElementById("ProductionSetLst"); }
    public Element getProd_BatesRange(){ return driver.FindElementById("lblGeneratedBatesRange"); }
    public Element getPreviewprod(){ return driver.FindElementById("btnPreview"); }   
    public Element getNative_AdvToggle(){ return driver.FindElementByXPath("//*[@id='NativeContainer']//div[@class='advanced-dd-toggle']"); }   
    public Element getProdStateFilter(){ return driver.FindElementById("productionStateFilter"); }   
    //added by shilpi on 29/04/2020
    public Element getPDF_SpecifyRedactText(){ return driver.FindElementByXPath("//*[@class='PDF-TIFF-Config']//a[@class='add-redaction-logic']"); }
    public Element getPDF_BurnRedtoggle(){ return driver.FindElementByXPath("//*[@id='chkPDFBurnRedactions']/following-sibling::i"); }
    public Element getPDF_SelectRedtagbuton(){ return driver.FindElementById("btnPdfRedTAG_0"); }   
    public Element getPDF_SelectRedtags(){ return driver.FindElementByXPath("//*[@id='PDFRedactionTagsTree']//a[contains(text(),'R2')]"); }   
    public Element getPDF_SelectRedtags_SelectButton(){ return driver.FindElementByXPath("//*[@id='myPDFModal']//button[@title='Select']"); }   
    public Element getPDF_Red_Placeholdertext(){ return driver.FindElementByXPath("//*[@id='divPDFRedaction']//div[@class='redactor-editor']/p"); }   
    public Element getProdname_Gearicon(String prodname){ return driver.FindElementById(".//*[@id='pName']/a[@title='"+prodname+"']/following-sibling::div"); }   
    public Element getTIFF_BurnRedtoggle(){ return driver.FindElementByXPath("//*[@id='chkBurnRedactions']/following-sibling::i"); }
    public Element getTIFF_SpecifyRedactText(){ return driver.FindElementByXPath("//*[@id='c7']//a[@class='add-redaction-logic']"); }
    public Element getTIFF_SelectRedtagbuton(){ return driver.FindElementById("btnTiffRedTAG_0"); }   
    public Element getTIFF_SelectRedtags(){ return driver.FindElementByXPath("//div[@id='RedactionTagsTree']//a[contains(text(),'Default Redaction Tag')]"); }   
    public Element getTIFF_SelectRedtags_SelectButton(){ return driver.FindElementByXPath("//*[@id='myModal']//button[@title='Select']"); }   
    public Element getTIFF_Red_Placeholdertext(){ return driver.FindElementByXPath("//*[@id='divRedaction']//div[@class='redactor-editor']/p"); }   
    public Element getTIFF_SelectRed_Radiobutton(){ return driver.FindElementByXPath("//*[@id='chkTIFFSpecifytRedactions']/following-sibling::i"); }   
    public Element getPDF_SelectRed_Radiobutton(){ return driver.FindElementByXPath("//*[@id='chkPDFSPecifytRedactions']/following-sibling::i"); }   
        
    public Element getDoc_Count(){ return driver.FindElementByXPath("//*[@id='frmProductionConfirmation']//div[@class='col-md-3']/div[2]/span[1]"); } 
    
    public Element getProd_Uncommitbutton(){ return driver.FindElementByXPath("//strong[contains(text(),'Uncommit Production')]"); }   
    //added by shilpi
    public Element getTextcomponent_text(){ return driver.FindElementByXPath("//*[@id='TextContainer']//p"); }
    public Element getTiff_placeholdertext(){ return driver.FindElementByXPath("//*[@class='col-md-12 tiff-img-logic']/label[2]"); }
    public Element getTiff_NativeDoc(){ return driver.FindElementByXPath("//*[@class='add-tiff-img-logic']"); }
    public Element getTiff_NativeDoc_FileType(){ return driver.FindElementByXPath("//*[@id='divImageTIFFPHImage_0']/div[1]/div[1]/label[1]"); }
    public Element getTiff_redactiontext(){ return driver.FindElementByXPath("//*[@class='col-md-12 tiff-redaction-logic']/label[2]"); }
    public Element getpdf_placeholdertext(){ return driver.FindElementByXPath("//*[@class='col-md-12 pdf-img-logic']/label[2]"); }
    public Element getpdf_NativeDoc(){ return driver.FindElementByXPath("//*[@class='add-pdf-img-logic']"); }
    public Element getpdf_NativeDoc_FileType(){ return driver.FindElementByXPath("//*[@id='divImagePDFPHImage_0']/div[1]/div[1]/label[1]"); }
    public Element getpdf_redactiontext(){ return driver.FindElementByXPath("//*[@class='col-md-12 pdf-redaction-logic']/label[2]"); }
    public Element getNative_text(){ return driver.FindElementByXPath("//*[@id='NativeContainer']//div[1]/p/strong"); }
    public Element getNative_text_Color(){ return driver.FindElementByXPath("//*[@id='NativeContainer']//div[1]/p"); }
    public Element getMarkCompleteLink() {return driver.FindElementByXPath("//a[text()='Mark Complete']");}
	public Element getNextButton() {return driver.FindElementByXPath("//button[text()='Next']");}
	public Element getprod_ActionButton(){ return driver.FindElementByXPath("(//*[@class='fa fa-lg fa-gear'])[1]"); }
	public Element getprod_Action_SaveTemplate(){ return driver.FindElementByXPath("//*[@id='pName']//a[contains(.,'Save as Template')]"); }
	public Element getprod_Templatetext(){ return driver.FindElementById("templatesettxt"); }
	public Element getprod_LoadTemplate(){ return driver.FindElementById("ddlTemplate"); }
	public Element getTechissue_toggle(){ return driver.FindElementByXPath("//*[@id='chkEnabledforExceptionDocs']/following-sibling::i"); }
	public Element getTechissue_SelectTagButton(){ return driver.FindElementById("btnSelectTechIssueTags"); }
    public Element getTechissue_TextArea(){ return driver.FindElementByXPath("//textarea[@class='TIFFTechIssueDocPlaceHolderText']/preceding-sibling::div"); }
    
   
    //added by Narendra
    public ElementCollection getFilterOptions(){ return driver.FindElementsByXPath("//div[@class='col-md-9']//select//option"); }
    public Element getFilter(int i){ return driver.FindElementByXPath("//div[@class='col-md-5']//li["+i+"]//a[1]"); }
    public Element getFilterByButton(){ return driver.FindElementByXPath(".//*[@id='cardGrid']/div[1]//button[@class='multiselect dropdown-toggle btn']"); }
    public Element getFilterByDRAFT(){ return driver.FindElementByXPath(".//*[@class='multiselect-container dropdown-menu']//label/input[@value='DRAFT']"); }
    public Element getFilterByINPROGRESS(){ return driver.FindElementByXPath(".//*[@class='multiselect-container dropdown-menu']//label/input[@value='INPROGRESS']"); }
    public Element getFilterByFAILED(){ return driver.FindElementByXPath(".//*[@class='multiselect-container dropdown-menu']//label/input[@value='FAILED']"); }
    public Element getFilterByCompleted(){ return driver.FindElementByXPath(".//*[@class='multiselect-container dropdown-menu']//label/input[@value='COMPLETED']"); }
    public Element getRefreshButton(){ return driver.FindElementById("refresh"); }  
    public Element getSortByButton(){ return driver.FindElementById("SortBy"); }
    public Element getGridView(){ return driver.FindElementById("GridView"); }
    public ElementCollection getProductionItemsTile(){ return driver.FindElementsByXPath("//div[@id='cardCanvas']//li/div[1]/a[1]"); }
    public ElementCollection getProductionItemsGrid(){ return driver.FindElementsByXPath("//table[@id='ProductionListGridViewTable']//tr"); }
    public Element getProductionItemsTileItems(){ return driver.FindElementById("totalProductionCount"); }
    public Element getProductionItemsGridItems(){ return driver.FindElementById("ProductionListGridViewTable_info"); }
    public Element getArrow(){ return driver.FindElementByXPath("//div[@id='pName']//div//a[1]"); }
    public Element getSaveTemplate(){ return driver.FindElementByXPath("//*[@class='dropdown-menu']//a[contains(text(),'Save as Template')]"); }
    public Element getTemplateName(){ return driver.FindElementById("templatesettxt"); }
    public Element getCustomTemplateName(String tempName){ return driver.FindElementByXPath("//table[@id='customTemplatesDatatable']//tr//td[1][contains(text(),'"+tempName+"')]"); }
    public Element getSave(){ return driver.FindElementByXPath("//button[text()='Save']"); }
    public Element getManageTemplates(){ return driver.FindElementByXPath("//li[@class='ui-tabs-tab ui-corner-top ui-state-default ui-tab']//a[@class='ui-tabs-anchor']"); }
    public ElementCollection getCustomTemplates(){ return driver.FindElementsByXPath("//table[@id='customTemplatesDatatable']//tr//td[1]"); }
    public Element getDeleteTemplate(){ return driver.FindElementByXPath("//table[@id='customTemplatesDatatable']//tr//td[9]//a[contains(text(),'Delete')]"); }
    public Element getOK(){ return driver.FindElementById("bot1-Msg1"); }
    public Element getCreateProductionSet(){ return driver.FindElementByXPath("//div[@id='tabs-a']/div[@class='col-md-12']/div[@class='col-md-5']/a[1]"); }
    public Element getSaveSet(){ return driver.FindElementByXPath("//button[@class='btn btn-primary btn-sm']"); }
    public Element getCancelSet(){ return driver.FindElementByXPath("//button[@class='btn btn-default btn-sm']"); }
    public Element getSetName(){ return driver.FindElementById("ProductionSettxt"); }
    public Element getDeleteProd(){ return driver.FindElementById("DeleteProd"); }
    public Element getLock(){ return driver.FindElementByXPath("//div[@class='dropdown pull-right actionBtn font-xs open']//a[contains(text(),'Lock')]"); }
    public Element getDelete(){ return driver.FindElementByXPath("//div[@class='dropdown pull-right actionBtn font-xs open']//a[contains(text(),'Delete')]"); }
    public Element getTileDelete(){ return driver.FindElementByXPath("//div[@class='dropdown pull-right actionBtn font-xs open']//a[contains(text(),'Delete')]"); }
    public Element getAction(){ return driver.FindElementByXPath(" //span[@class='fa fa-chevron-down']"); }
    public Element getLockIcon(){ return driver.FindElementByXPath("//i[@class='fa fa-lock']"); }
    public Element getOpenWizard(){ return driver.FindElementByXPath("//div[@class='dropdown pull-right actionBtn font-xs open']//a[contains(text(),'Open In Wizard')]"); }
    public Element getLoadTemplate(){ return driver.FindElementById("ddlTemplate"); }
    public Element getGridDelete(){ return driver.FindElementById("Delete"); }    
    public Element getProductionsSetName(){ return driver.FindElementByXPath("//div[@class='col-md-12 productionSummary']//span[@class='font-lg']"); }
    public Element getProductionListGridViewTable(){ return driver.FindElementByXPath("//table[@id='ProductionListGridViewTable']//tbody//tr[1]"); }
    public Element getProductionsExportsTabs(){ return driver.FindElementById("ui-id-1"); }
    public Element getadvance(){ return driver.FindElementByXPath("//div[@class='panel-body']//div[@class='col-md-12']//i[@class='fa fa-chevron-right']"); }
    public Element getGenerateLoadFile(){ return driver.FindElementByXPath("//div[@class='panel-body']//div[@class='col-md-12']//div[@class='col-md-4']//i[@class='pull-right']"); }
    public Element getSlipSheets(){ return driver.FindElementByXPath("//div[@class='form-group col-md-12 wrapperNew no-padding']//i[@class='pull-left']"); }
    public Element getAvailableFields(int i){ return driver.FindElementByXPath("//ul[@class='nav nav-tabs tab-style']//li["+i+"]//a"); }
    public Element getTIFF_InsertMetadataFieldClick(){ return driver.FindElementByXPath("//div[@class='col-md-8 tiff-logic insertPopup']//a[@class='LaunchPopup']"); }
    
    //added by shilpi on 08/17
    public Element getCopyPath() { return driver.FindElementByXPath("//a[@title='Copy Path']"); }
    public Element getDocumentGeneratetext() { return driver.FindElementByXPath("//span[contains(text(),'Documents Generated')]"); }
    public Element getQC_backbutton() { return driver.FindElementByXPath("//a[contains(@class,'btn btn-default')]"); }
    public Element getQC_Download() { return driver.FindElementByXPath("//a[text()='Download']"); }
    public Element getQC_Downloadbutton_allfiles() { return driver.FindElementByXPath("(//a[@title='Download All files'])[1]"); }
    

    // added by Lyudmila
    public Element getTagTreeTIFFComponent() { return driver.FindElementByXPath("//div[@id='tagTreeTIFFComponent']"); }
    public Element tagContainer() { return driver.FindElementByXPath("//*[@id='TIFFRedactiontreeFolder']//i[@role='presentation']");}
    public Element productionStateFilterDropdown() { return driver.FindElementByXPath("//select[@id='productionStateFilter']/following-sibling::div/button"); }
    public Element productionStateFilterCompleted() { return driver.FindElementByXPath("//select[@id='productionStateFilter']/following-sibling::div//input[@value='COMPLETED']"); }
    public Element productionLink(String exportname ) { return driver.FindElementByXPath("//div[@id='pName']//a[@title='"+exportname+"']");}
    public Element productionSetDiv() { return driver.FindElementById("ProductionSetdiv"); }
    public Element getUnlock(){ return driver.FindElementByXPath("//div[@id='pName']//a[text()='Unlock']"); }
    public Element gettotalCount() { return driver.FindElementById("totalProductionCount"); }
    public Element menuProductions() { return driver.FindElementByXPath("//ul[@id='LeftMenu']//a[@title='Productions']"); }
    public Element menuProductionActive() { return driver.FindElementByXPath("//ul[@id='LeftMenu']//a[@title='Productions']/.."); }
    public Element tagPrivileged() { return driver.FindElementByXPath("//*[@id='tagTreeTIFFComponent']//a[@data-content='Privileged']"); }
    public Element getOption(String string) { return driver.FindElementByXPath(".//option[contains(text(), '"+string+"')]"); }
    public String xpathThreeStepsUp = "./../../..";
    public Element productionGenerateStatus() { return driver.FindElementById("prouctionGenerateStatusTxt"); }

    public ProductionPage(Driver driver){
        this.driver = driver;
        this.driver.getWebDriver().get(Input.url+"Production/Home");
        driver.waitForPageToBeReady();
        base = new BaseClass(driver);
        this.wait = new WebDriverWait(driver.getWebDriver(), 15L);
        this.generateWait = new WebDriverWait(driver.getWebDriver(), 180L);
    }
    
    public void startProduction(String productionname) {
    	
    	driver.getWebDriver().get(Input.url+"Production/Home");
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getAddNewProductionbutton().Visible()  ;}}), Input.wait30); 
		getAddNewProductionbutton().waitAndClick(10);
	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProductionName().Visible()  ;}}), Input.wait30); 
		getProductionName().SendKeys(productionname);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProductionDesc().Visible()  ;}}), Input.wait30); 
		getProductionDesc().SendKeys(productionname);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getBasicInfoMarkComplete().Visible()  ;}}), Input.wait30); 
		getBasicInfoMarkComplete().waitAndClick(10);
		
//		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//				getBasicInfoNext().Enabled() ;}}), Input.wait30); 
//		getBasicInfoNext().waitAndClick(10);
    }
    
    public void fillDATFields( ) {
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDATChkBox().Enabled()  ;}}), Input.wait30); 
		getDATChkBox().waitAndClick(20);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDATTab().Visible()  ;}}), Input.wait30); 
		getDATTab().waitAndClick(10);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDAT_FieldClassification1().Visible()  ;}}), Input.wait30); 
		getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText("Bates");
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDAT_SourceField1().Visible()  ;}}), Input.wait30); 
		getDAT_SourceField1().selectFromDropdown().selectByVisibleText("BatesNumber");
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDAT_DATField1().Visible()  ;}}), Input.wait30); 
		getDAT_DATField1().SendKeys("BatesNumber"+Utility.dynamicNameAppender());
    }
    
    public void fillNativeFields() {
    	
    	driver.scrollingToBottomofAPage();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNativeChkBox().Enabled()  ;}}), Input.wait30); 
		getNativeChkBox().waitAndClick(10);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNativeTab().Enabled()  ;}}), Input.wait30); 
		getNativeTab().waitAndClick(10);
		
		driver.scrollPageToTop();
		
		Reporter.log(getNative_text().getText(),true);
		//System.out.println(getNative_text().getText());
		UtilityLog.info(getNative_text().getText());
		
		//work on this assert..issue with text format!
		/*Assert.assertEquals(getNative_text().getText(),"To produce specific docs"
		+ " natively, please select file types and/or tags below. In addition,"
		+ " to export placeholders for these docs, configure placeholder in the TIFF "
		+ "or PDF section for the same selected file types and/or tags.");
		
		Assert.assertEquals(getNative_text_Color().GetAttribute("class"),"blue-text");*/
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNative_SelectAllCheck().Enabled()  ;}}), Input.wait30); 
		getNative_SelectAllCheck().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNative_AdvToggle().Enabled()  ;}}), Input.wait30); 
		getNative_AdvToggle().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNative_GenerateLoadFileLST().Enabled()  ;}}), Input.wait30); 
		getNative_GenerateLoadFileLST().waitAndClick(5);
    }
    
    public void fillTIFFFields(String tagname) {
    	
		driver.scrollingToBottomofAPage();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTIFFChkBox().Enabled() ;}}), Input.wait30); 
		getTIFFChkBox().waitAndClick(5);		
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTIFFTab().Enabled()  ;}}), Input.wait30); 
		getTIFFTab().waitAndClick(5);
		
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTIFF_CenterHeaderBranding().Visible() &&  getTIFF_CenterHeaderBranding().Enabled() ;}}), Input.wait30); 
		getTIFF_CenterHeaderBranding().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTIFF_InsertMetadataFieldClick().Visible()  ;}}), Input.wait30); 
		getTIFF_InsertMetadataFieldClick().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTIFF_selectedMetadataField_Ok().Visible()  ;}}), Input.wait30); 
		getTIFF_selectedMetadataField_Ok().Click();;
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTIFF_EnableforPrivilegedDocs().Visible()  ;}}), Input.wait30);		    	
    	getTIFF_EnableforPrivilegedDocs().ScrollTo();
    	
    	//driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				//getTIFF_EnableforPrivilegedDocs().Enabled()  ;}}), Input.wait30); 
		//getTIFF_EnableforPrivilegedDocs().waitAndClick(20);		
    		
		driver.scrollingToBottomofAPage();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getPriveldge_SelectTagButton().Visible()  ;}}), Input.wait30);	
		getPriveldge_SelectTagButton().waitAndClick(5);
		
		wait.until(ExpectedConditions.elementToBeClickable(getTagTreeTIFFComponent().getBy()));
		getTagTreeTIFFComponent().Click();
			
		WebElement myTag = wait.until(ExpectedConditions.presenceOfElementLocated(getPriveldge_TagTree(tagname).getBy()));
		myTag.click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getPriveldge_TagTree_SelectButton().Visible()  ;}}), Input.wait30);	
		getPriveldge_TagTree_SelectButton().waitAndClick(5);
				
		String expplaceholdertexttiff = getTiff_placeholdertext().getText();
		UtilityLog.info(expplaceholdertexttiff);
		Assert.assertEquals(expplaceholdertexttiff, "TIFF / PDFs are generated and exported "
				+ "for all documents by default. To export placeholders for docs "
				+ "that are Privileged Withhold, Tech Issue or Produced Natively,"
				+ " please configure the placeholder section below.");
		
		
		
//		Assert.assertEquals(getTiff_NativeDoc().getText(),"Enable for Natively Produced Documents:");
		
//		Assert.assertEquals(getTiff_NativeDoc_FileType().getText(),"Select File Types and/or Tags:");
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getPriveldge_TextArea().Enabled()  ;}}), Input.wait30); 
		new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();
		
		getPriveldge_TextArea().SendKeys("testing");
		System.out.println(getPriveldge_TextArea().getText());
		
		Assert.assertEquals(getTiff_redactiontext().getText(),"To burn redactions, select specific redactions"
		+ " or all redactions (annotation layer). Specify the redaction text for each"
		+ " selected redaction.");
    }
    
    public void fillTechIssues(String tagname) {
    	driver.scrollingToBottomofAPage();
        
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call()
        {return getTechissue_toggle().Visible() ;}}), Input.wait30);
        getTechissue_toggle().waitAndClick(10);
		  
			getTechissue_SelectTagButton().waitAndClick(10);
			
			WebElement tagContainerTech = wait.until(ExpectedConditions.elementToBeClickable(getTagTreeTIFFComponent().getBy()));
			tagContainerTech.click();
			
			
			WebElement myTagTech = wait.until(ExpectedConditions.presenceOfElementLocated(getPriveldge_TagTree("Technical_Issue").getBy()));
			
			myTagTech.click();
			
			getPriveldge_TagTree_SelectButton().waitAndClick(10);
			
			
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getPriveldge_TextArea().Enabled()  ;}}), Input.wait30); 
			new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();
			
		    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return getTech_Issue_TextArea().Enabled() ;}}), Input.wait30);
		    new Actions(driver.getWebDriver()).moveToElement(getTech_Issue_TextArea().getWebElement()).click();
		    getTech_Issue_TextArea().SendKeys("testing");
			
			}
    	
   
    
    public void fillTextFields() {
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTextChkBox().Enabled()  ;}}), Input.wait30); 
		getTextChkBox().waitAndClick(10);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTextTab().Enabled()  ;}}), Input.wait30); 
		getTextTab().waitAndClick(10);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTextcomponent_text().Displayed()  ;}}), Input.wait30); 
	
		String exptext= getTextcomponent_text().getText();
		System.out.println(exptext);
		UtilityLog.info(exptext);
		Assert.assertEquals(exptext, "Redacted documents are automatically OCRed"
			+ " to export the text. Original extracted text is exported for natively "
			+ "produced documents (file based placeholdering). "
			+ "For exception and privileged placeholdered docs, "
			+ "the placeholder text is exported."+" The configured format is applicable only to OCRed text and production generated text"
			+ ", and not to ingested text.");
    }
    
//    public void fillPDFFields() {
//		
//				driver.scrollingToBottomofAPage();
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
//		Thread.sleep(2000);
//		
//		driver.scrollingToBottomofAPage();
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
//			getPDF_SpecifyRedactText().waitAndClick(10);
//		
//		
//		getPDF_SelectRedtagbuton().waitAndClick(10);
//		Thread.sleep(5000);
//		
//		getPDF_SelectRedtags().waitAndClick(10);
//		
//		getPDF_SelectRedtags_SelectButton().waitAndClick(10);
//		System.out.println(getPDF_Red_Placeholdertext().getText()); 
//		
//		Assert.assertEquals(getPDF_Red_Placeholdertext().getText(),"REDACTED");
//    }
		
		
		public void moveToNumbering( ) {
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getComponentsMarkComplete().Enabled()  ;}}), Input.wait30); 
		getComponentsMarkComplete().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getComponentsMarkNext().Enabled()  ;}}), Input.wait30); 
		getComponentsMarkNext().waitAndClick(5);
    }
    
    public void numberingAndSorting(String PrefixID,String SuffixID) {
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getBeginningBates().Enabled()  ;}}), Input.wait30); 
		getBeginningBates().SendKeys("100");
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				gettxtBeginningBatesIDPrefix().Enabled()  ;}}), Input.wait30); 
		gettxtBeginningBatesIDPrefix().SendKeys(PrefixID);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				gettxtBeginningBatesIDSuffix().Enabled()  ;}}), Input.wait30); 
		gettxtBeginningBatesIDSuffix().SendKeys(SuffixID);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				gettxtBeginningBatesIDMinNumLength().Enabled()  ;}}), Input.wait30); 
		gettxtBeginningBatesIDMinNumLength().SendKeys("10");
		
		driver.scrollingToBottomofAPage();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getlstSortingMetaData().Enabled()  ;}}), Input.wait30); 
		getlstSortingMetaData().selectFromDropdown().selectByVisibleText("DocID");
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getlstSortingOrder().Enabled()  ;}}), Input.wait30); 
		getlstSortingOrder().selectFromDropdown().selectByVisibleText("Ascending");
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getlstSortingMetaData().Enabled()  ;}}), Input.wait30); 
		getlstSubSortingMetaData().selectFromDropdown().selectByVisibleText("CustodianName");
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getlstSubSortingOrder().Enabled()  ;}}), Input.wait30); 
		getlstSubSortingOrder().selectFromDropdown().selectByVisibleText("Ascending");
		
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getKeepFamiliesTogether().Visible()  ;}}), Input.wait30); 
		getKeepFamiliesTogether().waitAndClick(5);
		
		driver.scrollPageToTop();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNumAndSortMarkComplete().Enabled()  ;}}), Input.wait30); 
		getNumAndSortMarkComplete().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNumAndSortNext().Enabled() ;}}), Input.wait30); 
		getNumAndSortNext().waitAndClick(10);
    }
    
    public void documentSelection(String foldername) {
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getFolderRadioButton().Visible() ;}}), Input.wait30); 
		getFolderRadioButton().waitAndClick(10);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectFolder(foldername).Visible() ;}}), Input.wait30); 
		getSelectFolder(foldername).waitAndClick(5);
		
		driver.scrollingToBottomofAPage();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getIncludeFamilies().Visible()  ;}}), Input.wait30); 
		getIncludeFamilies().waitAndClick(10);
		
		driver.scrollPageToTop();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnDocumentsSelectionMarkComplete().Visible()  ;}}), Input.wait30); 
		getbtnDocumentsSelectionMarkComplete().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnDocumentsSelectionNext().Enabled()  ;}}), Input.wait30); 
		getbtnDocumentsSelectionNext().waitAndClick(5);
    }
    
    public void privGuard() {
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionGuardMarkComplete().Visible()  ;}}), Input.wait30); 
		getbtnProductionGuardMarkComplete().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getOkButton().Visible()  ;}}), Input.wait30); 
		getOkButton().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionGuardNext().Enabled()  ;}}), Input.wait30); 
		getbtnProductionGuardNext().waitAndClick(5);
    }
    
    public void prodLocation(String productionname) {
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getlstProductionRootPaths().Visible()  ;}}), Input.wait30); 
		try{
			getlstProductionRootPaths().selectFromDropdown().selectByVisibleText(Input.prodPath);
		}catch (Exception e) {
			//if passed production path is wrong! go by index and then select 
			getlstProductionRootPaths().selectFromDropdown().selectByIndex(1);
		}
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProductionOutputLocation_ProductionDirectory().Visible()  ;}}), Input.wait30); 
		getProductionOutputLocation_ProductionDirectory().SendKeys(productionname);
		
		driver.scrollPageToTop();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionLocationMarkComplete().Visible()  ;}}), Input.wait30); 
		getbtnProductionLocationMarkComplete().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionLocationNext().Enabled()  ;}}), Input.wait30); 
		getbtnProductionLocationNext().waitAndClick(5);
    }
    
    public void productionSummary() {
    	
    	/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getPreviewprod().Enabled() ;}}), Input.wait30); getPreviewprod().waitAndClick(10);
		 */
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionSummaryMarkComplete().Visible()  ;}}), Input.wait30); 
		getbtnProductionSummaryMarkComplete().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionSummaryNext().Enabled()  ;}}), Input.wait30); 
		getbtnProductionSummaryNext().waitAndClick(5);
    }
    
    public void generateProduction() {
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionGenerate().Visible()  ;}}), Input.wait30); 
		getbtnProductionGenerate().waitAndClick(5);
		
		generateWait.until(ExpectedConditions.textToBe(productionGenerateStatus().getBy(), "Reserving Bates Range Complete"));
			System.out.println(productionGenerateStatus().getText());
			try {
				Boolean continuePresent = generateWait.until(ExpectedConditions.textToBe(getbtnProductionGenerate().getBy(), "Continue Generation"));
				if (continuePresent) {
				getbtnProductionGenerate().Click();}
			} catch (Exception e) {};
		
		Reporter.log("Wait for generate to complete",true);
		//System.out.println("Wait for generate to complete");
		UtilityLog.info("Wait for generate to complete");
		
		/*
		 * for (int i = 0; i < 120; i++) { try {
		 * this.driver.getWebDriver().get(Input.url+"Production/Home");
		 * getProductionLink().waitAndClick(5);
		 * getbtnGenerateMarkComplete().waitAndClick(5);
		 * System.out.println("Generated"); break;
		 * 
		 * } catch (Exception e) { Thread.sleep(10000); driver.Navigate().refresh();
		 * 
		 * 
		 * }
		 * 
		 * }
		 */
				
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocumentGeneratetext().Visible()  ;}}), Input.wait120); 
		//work on below assert..for now its ok bcz we are validating with commit button!
		//Assert.assertTrue(getDocumentGeneratetext().Displayed());
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocumentGeneratetext().Displayed() ;}}), Input.wait120); 	
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getQC_backbutton().Enabled()  ;}}), Input.wait30); 
		getQC_backbutton().waitAndClick(5);
	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProd_BatesRange().Visible()  ;}}), Input.wait30); 
		String batesno = getProd_BatesRange().getText();
		
		Reporter.log("Bate number "+batesno+"",true);
		UtilityLog.info(batesno);
		
		System.out.println(batesno);
		
		  String[] parts = batesno.split("\\s*-\\s*"); 
		  String a = parts[0]; 
		  String b = parts[1]; 
		  System.out.println(a);
		 
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnSummaryNext().Enabled()  ;}}), Input.wait30); 
		getbtnSummaryNext().waitAndClick(5);
		
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getCopyPath().Visible()  ;}}), Input.wait30); 
		getCopyPath().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getQC_Download().Enabled()  ;}}), Input.wait30); 
		getQC_Download().waitAndClick(10);
		getQC_Downloadbutton_allfiles().waitAndClick(10);
		base.VerifySuccessMessage("Your Production Archive download will get started shortly");
	
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getConfirmProductionCommit().Enabled()  ;}}), Input.wait30); 
		getConfirmProductionCommit().waitAndClick(10);
		
		 String PDocCount = getProductionDocCount().getText();
         int Doc = Integer.parseInt(PDocCount);
         
         Reporter.log("Doc - "+Doc,true);
         System.out.println(Doc); 
         UtilityLog.info(Doc);
         
// 		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
// 				getReviewproductionButton().Visible()  ;}}), Input.wait30); 
// 		getReviewproductionButton().waitAndClick(10);
 		
 		String location = getDestinationPathText().getText();
         System.out.println(location);
         try {
         Thread.sleep(7000);
         } catch (InterruptedException e) {
 			// TODO Auto-generated catch block
 			e.printStackTrace();
 		}
         String doccount = getDoc_Count().getText();
         System.out.println(doccount);
         
//         ProdPathallredact= location+"\\VOL0001"+"\\PDF"+"\\0001"+"\\"+""+batesno+".pdf";
//         System.out.println(ProdPathallredact);
//       
//         
//          base.isFileDownloaded(location+"\\VOL0001"+"\\PDF"+"\\0001",1);
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
    
    public void checkProductionOptions() {
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDATChkBox().Enabled()  ;}}), Input.wait30); 
		getDATChkBox().waitAndClick(20);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDATTab().Visible()  ;}}), Input.wait30); 
		getDATTab().waitAndClick(10);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDAT_FieldClassification1().Visible()  ;}}), Input.wait30); 
		getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText("Production");
		
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDAT_SourceField1().Visible()  ;}}), Input.wait30);
		
		
		for(WebElement listsecfiled:getDAT_SourceField1().selectFromDropdown().getOptions())
		{
			String fieldexp[]= {"TIFFPageCount","VolumeName"};
			System.out.println(listsecfiled.getText());
			if(listsecfiled.getText().equalsIgnoreCase("TIFFPageCount"))
				assertion.assertTrue(listsecfiled.getText().equalsIgnoreCase("TIFFPageCount"));
			else
				System.out.println("Element not matching");
		}
    	
		BaseClass bc= new BaseClass(driver);
	//	bc.comparearraywithlist(fieldexp, elelist);
		driver.Navigate().refresh();
    }
    
    public void specifyRedactions() {
    	
    	getTIFF_BurnRedtoggle().waitAndClick(10);
		
		getTIFF_SpecifyRedactText().waitAndClick(10);
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getTIFF_SelectRed_Radiobutton().waitAndClick(10);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		wait.until(ExpectedConditions.elementToBeClickable(tagContainer().getBy()));

		tagContainer().Click();
		wait.until(ExpectedConditions.invisibilityOfElementLocated(getTIFF_SelectRedtags().getBy()));
		tagContainer().Click();
		
		WebElement myTag = wait.until(ExpectedConditions.presenceOfElementLocated((getTIFF_SelectRedtags().getBy())));
		myTag.click();
		
		getTIFF_SelectRedtagbuton().waitAndClick(10);
		
		WebElement tagContainer1 = wait.until(ExpectedConditions.elementToBeClickable(getTagTreeTIFFComponent().getBy()));
		
		tagContainer1.click();
	
		wait.until(ExpectedConditions.elementToBeClickable(getTIFF_SelectRedtags_SelectButton().getBy()));
		
		getTIFF_SelectRedtags_SelectButton().waitAndClick(10);	
		System.out.println(getTIFF_Red_Placeholdertext().getText());
		
		Assert.assertEquals(getTIFF_Red_Placeholdertext().getText(),"REDACTED");
    }
    
    
    public void CreateProduction(String productionname,String PrefixID,String SuffixID,final String foldername, String tagname) 
    		throws InterruptedException{
		
    	startProduction(productionname);
		
    	fillDATFields( );
    	
    	fillNativeFields();
    	
    	fillTIFFFields(tagname);
    	
    	fillTextFields();
    	
    	moveToNumbering();
    	
    	numberingAndSorting(PrefixID, SuffixID);
    	
    	documentSelection(foldername);
    	
    	privGuard();
    	
    	prodLocation(productionname);
    	
    	productionSummary();
    	
    	generateProduction();
    	
    }
    
    public void ExportwithpriorProduction(String exportname,String PrefixID,String SuffixID,final String foldername) 
    		throws InterruptedException{
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProdExportSet().Visible()  ;}}), Input.wait30); 
		getProdExportSet().waitAndClick(10);
	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProductionSettxt().Visible()  ;}}), Input.wait30); 
		getProductionSettxt().SendKeys(exportname);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProdExportSetRadioButton().Visible()  ;}}), Input.wait30); 
		getProdExportSetRadioButton().waitAndClick(10);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProdExport_SaveButton().Visible()  ;}}), Input.wait30); 
		getProdExport_SaveButton().waitAndClick(10);
		
		base.VerifySuccessMessage("Export Set Added Successfully");
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProdExport_ProductionSets().Visible()  ;}}), Input.wait30); 
		getProdExport_ProductionSets().getWebElement().findElement(getOption("EXP").getBy()).click();
				
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProdExport_AddaNewExportSet().Visible()  ;}}), Input.wait30); 
		getProdExport_AddaNewExportSet().waitAndClick(10);
	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProductionName().Visible()  ;}}), Input.wait30); 
		getProductionName().SendKeys(exportname);
		System.out.println(exportname);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProductionDesc().Visible()  ;}}), Input.wait30); 
		getProductionDesc().SendKeys(exportname);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProdExport_Priorprodtoggle().Visible()  ;}}), Input.wait30); 
		getProdExport_Priorprodtoggle().waitAndClick(10);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProdExport_SelectProductionSet().Visible()  ;}}), Input.wait30); 
		getProdExport_SelectProductionSet().getWebElement().findElement(getOption("P").getBy()).click();
		
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getBasicInfoMarkComplete().Visible()  ;}}), Input.wait30); 
		getBasicInfoMarkComplete().waitAndClick(10);
				
//		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//				getBasicInfoNext().Enabled() ;}}), Input.wait30); 
//		getBasicInfoNext().waitAndClick(10);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDATLabel().Enabled()  ;}}), Input.wait30); 
		getDATLabel().waitAndClick(20);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDATTab().Visible()  ;}}), Input.wait30); 
		getDATTab().waitAndClick(10);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDAT_DATField1().Visible()  ;}}), Input.wait30); 
		getDAT_DATField1().SendKeys("BatesNumberExp");
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getComponentsMarkComplete().Enabled()  ;}}), Input.wait30); 
		getComponentsMarkComplete().waitAndClick(20);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getComponentsMarkNext().Enabled()  ;}}), Input.wait30); 
		getComponentsMarkNext().waitAndClick(20);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNumAndSortMarkComplete().Enabled()  ;}}), Input.wait30); 
		getNumAndSortMarkComplete().waitAndClick(10);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNumAndSortNext().Enabled() ;}}), Input.wait30); 
		getNumAndSortNext().waitAndClick(10);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getFolderRadioButton().Visible() ;}}), Input.wait30); 
		getFolderRadioButton().waitAndClick(10);
		
		Thread.sleep(5000);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectFolder(foldername).Visible() ;}}), Input.wait30); 
		getSelectFolder(foldername).waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnDocumentsSelectionMarkComplete().Visible()  ;}}), Input.wait30); 
		getbtnDocumentsSelectionMarkComplete().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnDocumentsSelectionNext().Enabled()  ;}}), Input.wait30); 
		getbtnDocumentsSelectionNext().waitAndClick(5);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getOkButton().Visible()  ;}}), Input.wait30); 
		getOkButton().waitAndClick(10);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getlstProductionRootPaths().Visible()  ;}}), Input.wait30); 
		getlstProductionRootPaths().selectFromDropdown().selectByVisibleText(Input.prodPath);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProductionOutputLocation_ProductionDirectory().Visible()  ;}}), Input.wait30); 
		getProductionOutputLocation_ProductionDirectory().SendKeys(exportname);
		
		driver.scrollPageToTop();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionLocationMarkComplete().Visible()  ;}}), Input.wait30); 
		getbtnProductionLocationMarkComplete().waitAndClick(10);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionLocationNext().Enabled()  ;}}), Input.wait30); 
		getbtnProductionLocationNext().waitAndClick(10);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionSummaryMarkComplete().Visible()  ;}}), Input.wait30); 
		getbtnProductionSummaryMarkComplete().waitAndClick(10);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionSummaryNext().Enabled()  ;}}), Input.wait30); 
		getbtnProductionSummaryNext().waitAndClick(10);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionGenerate().Visible()  ;}}), Input.wait30); 
		getbtnProductionGenerate().waitAndClick(10);
		System.out.println("Wait until regenerate is enabled");
		
		generateWait.until(ExpectedConditions.textToBe(productionGenerateStatus().getBy(), "Reserving Bates Range Complete"));
			System.out.println(productionGenerateStatus().getText());
			try {
				Boolean continuePresent = generateWait.until(ExpectedConditions.textToBe(getbtnProductionGenerate().getBy(), "Continue Generation"));
				if (continuePresent) {
				getbtnProductionGenerate().Click();}
			} catch (Exception e) {};
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocumentGeneratetext().Visible()  ;}}), Input.wait120); 
		//work on below assert..for now its ok bcz we are validating with commit button!
		//Assert.assertTrue(getDocumentGeneratetext().Displayed());
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocumentGeneratetext().Displayed() ;}}), Input.wait120); 	
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getQC_backbutton().Enabled()  ;}}), Input.wait30); 
		getQC_backbutton().waitAndClick(5);
	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProd_BatesRange().Enabled()  ;}}), Input.wait30); 
		String batesno = getProd_BatesRange().getText();
		
		Reporter.log("Bate number "+batesno,true);
		//System.out.println(batesno);
		UtilityLog.info(batesno);
		
		System.out.println(batesno);
		/*
		 * String[] parts = batesno.split("\\s*-\\s*"); 
		 * String a = parts[0]; 
		 * String b = parts[1]; 
		 * System.out.println(a);
		 */
		
//		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//				getbtnSummaryNext().Enabled()  ;}}), Input.wait30); 
//		getbtnSummaryNext().waitAndClick(5);
	
		this.driver.getWebDriver().get(Input.url+"Production/Home");
		
		

		
		String totalCountId = "totalProductionCount";
		final String oldtotalCountText = driver.getWebDriver().findElement(By.id(totalCountId)).getText();
    	
   	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProdExport_ProductionSets().Visible()  ;}}), Input.wait30); 
    	getProdExport_ProductionSets().getWebElement().findElement(getOption("EXP").getBy()).click();
    	
		ExpectedCondition<WebElement> totalCountChanged = BaseClass.waitForTextToChangeCondition(By.id(totalCountId), oldtotalCountText);
		wait.until(totalCountChanged);
    	
		WebElement dropdown =  wait.until(ExpectedConditions.presenceOfElementLocated((productionStateFilterDropdown()).getBy()));
		dropdown.click();
		
		final String oldtotalCountText1 = driver.getWebDriver().findElement(By.id(totalCountId)).getText();
		
		WebElement completed = wait.until(ExpectedConditions.elementToBeClickable(productionStateFilterCompleted().getBy()));
		if (completed.findElement(By.xpath(xpathThreeStepsUp)).getAttribute("class") != "active") {
			completed.click();
		}
		
		driver.getWebDriver().findElement(productionSetDiv().getBy()).click();
		
		ExpectedCondition<WebElement> totalCountChanged1 = BaseClass.waitForTextToChangeCondition(By.id(totalCountId), oldtotalCountText1);
		
		wait.until(totalCountChanged1);
		WebElement productionLink = wait.until(ExpectedConditions.elementToBeClickable(productionLink(exportname).getBy()));  
		productionLink.click();

//    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//		getReviewproductionButton().Visible()  ;}}), Input.wait30); 
//		getReviewproductionButton().waitAndClick(10);
		
		wait.until(ExpectedConditions.presenceOfElementLocated(getDestinationPathText().getBy()));
		String location = getDestinationPathText().getText();
        System.out.println(location);
        Thread.sleep(7000);
        
        String doccount = getDoc_Count().getText();
        System.out.println(doccount);
        
        
	}
	      
    public void Productionwithallredactions(String productionname,String PrefixID,String SuffixID,final String foldername,String tagname) 
    		throws Exception{
    	
    	startProduction(productionname);
						
    	checkProductionOptions();
    	
    	fillDATFields( );
	
    	fillNativeFields();
		
    	fillTIFFFields(tagname);
    	
    	specifyRedactions();
		
    	fillTextFields();
    	
    	moveToNumbering();
		
    	numberingAndSorting(PrefixID, SuffixID);
		
    	documentSelection(foldername);
		
    	privGuard();
		
    	prodLocation(productionname);
		
    	productionSummary();
		
    	generateProduction();
		
    }
     
    public void Productionwithsomeredactions(String productionname,String PrefixID,String SuffixID,final String foldername,String redname) 
    		throws Exception{
		
    	startProduction(productionname);
				
    	checkProductionOptions();
    	
    	fillDATFields( );
		
		fillNativeFields();
		
		fillTIFFFields("Priviledged");
		
		specifyRedactions();
		
		fillTextFields();
		
		//fillPDFFields();
		
		moveToNumbering();
		
		numberingAndSorting(PrefixID, SuffixID);
		
		documentSelection(foldername);
		
		privGuard();
		
		prodLocation(productionname);
		
    	productionSummary();
		
    	generateProduction();
		
    }
    
  	public void addANewProduction(String productionName) throws InterruptedException {
    	
  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  				 getAddNewProductionbutton().Visible()  ;}}), Input.wait30); 
  		getAddNewProductionbutton().waitAndClick(10);

  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  				getProductionName().Visible()  ;}}), Input.wait30); 
  		getProductionName().SendKeys(productionName);
  		
  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  				getProductionDesc().Visible()  ;}}), Input.wait30); 
  		getProductionDesc().SendKeys(productionName);
  		
  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  				getBasicInfoMarkComplete().Visible()  ;}}), Input.wait30); 
  		getBasicInfoMarkComplete().waitAndClick(10);
  	    }
  	    
  	    public void fillingDATSection() {

  			driver.WaitUntil((new Callable<Boolean>() {
  			public Boolean call() {return getDATChkBox().Enabled();}}), Input.wait30);
  			getDATChkBox().waitAndClick(10);

  			driver.WaitUntil((new Callable<Boolean>() {public Boolean call() {return getDATTab().Visible();}}), Input.wait30);
  			getDATTab().waitAndClick(10);

  			driver.WaitUntil((new Callable<Boolean>() {
  			public Boolean call() {return getDAT_FieldClassification1().Visible();}}), Input.wait30);
  			getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText("Bates");

  			driver.WaitUntil((new Callable<Boolean>() {
  			public Boolean call() {return getDAT_SourceField1().Visible();}}), Input.wait30);
  			getDAT_SourceField1().selectFromDropdown().selectByVisibleText("BatesNumber");

  			driver.WaitUntil((new Callable<Boolean>() {
  			public Boolean call() {return getDAT_DATField1().Visible();}}), Input.wait30);
  			getDAT_DATField1().SendKeys("BatesNumber" + Utility.dynamicNameAppender());
  		}
  		
  			public void fillingNativeSection() throws InterruptedException{

  			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  					getNativeChkBox().Enabled()  ;}}), Input.wait30); 
  			getNativeChkBox().waitAndClick(10);
  			
  			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  					getNativeTab().Enabled()  ;}}), Input.wait30); 
  			getNativeTab().waitAndClick(10);
  			
  			driver.scrollPageToTop();
  			
  			System.out.println(getNative_text().getText());
  			assertion.assertTrue(getNative_text().getText().contains("To produce specific docs natively,"));
  			Assert.assertEquals(getNative_text_Color().GetAttribute("class"),"blue-text");
  			
  			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  					getNative_SelectAllCheck().Enabled()  ;}}), Input.wait30); 
  			getNative_SelectAllCheck().waitAndClick(10);
  			
  			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  					getNative_AdvToggle().Enabled()  ;}}), Input.wait30); 
  			getNative_AdvToggle().waitAndClick(10);
  			
  			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  					getNative_GenerateLoadFileLST().Enabled()  ;}}), Input.wait30); 
  			getNative_GenerateLoadFileLST().waitAndClick(10);
  			Thread.sleep(2000);

  			 
  			}
  			
  			public void fillingTIFFSection(String tagnameprev,String tagnametech) throws InterruptedException{

  			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return getTIFFChkBox().Enabled() ;}}), Input.wait30);
  			getTIFFChkBox().waitAndClick(10);
  						
 		    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return getTIFFTab().Enabled() ;}}), Input.wait30);
  		    getTIFFTab().waitAndClick(10);
  			
  		    driver.scrollPageToTop();
  		    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return getTIFF_CenterHeaderBranding().Visible() && getTIFF_CenterHeaderBranding().Enabled() ;}}), Input.wait30);
  		    getTIFF_CenterHeaderBranding().waitAndClick(10);
  			
  			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return getTIFF_EnterBranding().Enabled() ;}}), Input.wait30);
  				
  			new Actions(driver.getWebDriver()).moveToElement(getTIFF_EnterBranding().getWebElement()).click();
  			getTIFF_EnterBranding().SendKeys("Test");
  		    Thread.sleep(2000);
  		    
  		    driver.scrollingToBottomofAPage();
  			
  			getPriveldge_SelectTagButton().Click();
  			
  			WebElement tagContainer = wait.until(ExpectedConditions.elementToBeClickable(getTagTreeTIFFComponent().getBy()));
  			tagContainer.click();
  			
  			
  			WebElement myTag = wait.until(ExpectedConditions.presenceOfElementLocated(tagPrivileged().getBy()));
  			myTag.click();
  			
//  			getPriveldge_TagTree(tagnameprev).waitAndClick(10);
//  			Thread.sleep(2000);
  			
  			getPriveldge_TagTree_SelectButton().waitAndClick(10);
  			Thread.sleep(2000);
  			
  			
  			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  					getPriveldge_TextArea().Enabled()  ;}}), Input.wait30); 
  			new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();
  			
  		    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return getPriveldge_TextArea().Enabled() ;}}), Input.wait30);
  		    new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();
  	    	getPriveldge_TextArea().SendKeys("testing");
  	    	
            driver.scrollingToBottomofAPage();
            
            driver.WaitUntil((new Callable<Boolean>() {public Boolean call()
            {return getTechissue_toggle().Visible() ;}}), Input.wait30);
            getTechissue_toggle().waitAndClick(10);
    		  
  			getTechissue_SelectTagButton().waitAndClick(10);
  			Thread.sleep(2000);
  			
  			
  			WebElement tagContainerTech = wait.until(ExpectedConditions.elementToBeClickable(getTagTreeTIFFComponent().getBy()));
  			tagContainerTech.click();
  			
  			
  			WebElement myTagTech = wait.until(ExpectedConditions.presenceOfElementLocated(getPriveldge_TagTree("Technical_Issue").getBy()));
  			
  			Thread.sleep(1000);
  			
  			myTagTech.click();
  			
//  			getPriveldge_TagTree(tagnametech).waitAndClick(10);
//  			Thread.sleep(2000);
  			
  			getPriveldge_TagTree_SelectButton().waitAndClick(10);
  			Thread.sleep(2000);
  			
  			
  			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  					getPriveldge_TextArea().Enabled()  ;}}), Input.wait30); 
  			new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();
  			
  		    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return getTech_Issue_TextArea().Enabled() ;}}), Input.wait30);
  		    new Actions(driver.getWebDriver()).moveToElement(getTech_Issue_TextArea().getWebElement()).click();
  		    getTech_Issue_TextArea().SendKeys("testing");
  			
  			}
  			
  			public void fillingPDFSection(String prefixId, String suffixId, String tagname) throws InterruptedException{
  				
  				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  						getTextChkBox().Enabled()  ;}}), Input.wait30); 
  				getTextChkBox().waitAndClick(10);
  						
  				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  						getPDFChkBox().Enabled() ;}}), Input.wait30); 
  				getPDFChkBox().waitAndClick(10);		
  				
  				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  						getPDFTab().Enabled()  ;}}), Input.wait30); 
  				getPDFTab().waitAndClick(10);
  				
  				driver.scrollPageToTop();
  				
  				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  						getPDF_CenterHeaderBranding().Visible() &&  getTIFF_CenterHeaderBranding().Enabled() ;}}), Input.wait30); 
  				getPDF_CenterHeaderBranding().ScrollTo();
  				getPDF_CenterHeaderBranding().waitAndClick(10);
  				
  				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  						getPDF_EnterBranding().Enabled()  ;}}), Input.wait30); 
  			
  				new Actions(driver.getWebDriver()).moveToElement(getPDF_EnterBranding().getWebElement()).click();
  				getPDF_EnterBranding().SendKeys("Test");
  		    	Thread.sleep(2000);
  		    	
  		    	driver.scrollingToBottomofAPage();
  				
  		    	
  				getPriveldge_SelectPDFTagButton().waitAndClick(10);
  				Thread.sleep(2000);
  				
  				getPriveldge_PDFTagTree(tagname).waitAndClick(10);
  				Thread.sleep(2000);
  				
  				getPriveldge_PDFTagTree_SelectButton().waitAndClick(5);
  				Thread.sleep(2000);
  				
  				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  						getPriveldge_TextArea().Enabled()  ;}}), Input.wait30); 
  				new Actions(driver.getWebDriver()).moveToElement(getPriveldge_PDFTextArea().getWebElement()).click();
  				
  				getPriveldge_PDFTextArea().SendKeys("testing");
  			
  				driver.scrollPageToTop();
  				
  				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  						getComponentsMarkComplete().Enabled()  ;}}), Input.wait30); 
  				getComponentsMarkComplete().waitAndClick(10);
  				
  				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  						getComponentsMarkNext().Enabled()  ;}}), Input.wait30); 
  				getComponentsMarkNext().waitAndClick(10);
  				
  				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  						getBeginningBates().Enabled()  ;}}), Input.wait30); 
  				getBeginningBates().SendKeys("100");
  				
  				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  						gettxtBeginningBatesIDPrefix().Enabled()  ;}}), Input.wait30); 
  				gettxtBeginningBatesIDPrefix().SendKeys(prefixId);
  				
  				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  						gettxtBeginningBatesIDSuffix().Enabled()  ;}}), Input.wait30); 
  				gettxtBeginningBatesIDSuffix().SendKeys(suffixId);
  				
  				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  						gettxtBeginningBatesIDMinNumLength().Enabled()  ;}}), Input.wait30); 
  				gettxtBeginningBatesIDMinNumLength().SendKeys("10");
  				
  				driver.scrollingToBottomofAPage();
  				
  				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  						getlstSortingMetaData().Enabled()  ;}}), Input.wait30); 
  				getlstSortingMetaData().selectFromDropdown().selectByVisibleText("DocID");
  				
  				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  						getlstSortingOrder().Enabled()  ;}}), Input.wait30); 
  				getlstSortingOrder().selectFromDropdown().selectByVisibleText("Ascending");
  				
  				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  						getlstSortingMetaData().Enabled()  ;}}), Input.wait30); 
  				getlstSubSortingMetaData().selectFromDropdown().selectByVisibleText("CustodianName");
  				
  				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  						getlstSubSortingOrder().Enabled()  ;}}), Input.wait30); 
  				getlstSubSortingOrder().selectFromDropdown().selectByVisibleText("Ascending");
  				
  				
  				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  						getKeepFamiliesTogether().Visible()  ;}}), Input.wait30); 
  				getKeepFamiliesTogether().waitAndClick(10);

  			}
  			
  			public void navigateToNextSection(){
  				
  				driver.scrollPageToTop();
  				
  				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return getMarkCompleteLink().Enabled() ;}}), Input.wait30);
  				getMarkCompleteLink().waitAndClick(10);
  				
  				System.out.println("Clicked on Mark Complete Button..");
  				
  				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return getNextButton().Enabled()  ;}}), Input.wait30); 
  				getNextButton().waitAndClick(10);
  			
  				}
  				
  				public void generateExport(String foldername, String exportDirectory, String setName) throws InterruptedException{
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getFolderRadioButton().Visible() ;}}), Input.wait30); 
  					getFolderRadioButton().waitAndClick(10);
  					
  					Thread.sleep(5000);
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getSelectFolder(foldername).Visible() ;}}), Input.wait30); 
  					getSelectFolder(foldername).waitAndClick(5);
  					
  					driver.scrollingToBottomofAPage();
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getIncludeFamilies().Visible()  ;}}), Input.wait30); 
  					getIncludeFamilies().waitAndClick(10);
  					
  					driver.scrollPageToTop();
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnDocumentsSelectionMarkComplete().Visible()  ;}}), Input.wait30); 
  					getbtnDocumentsSelectionMarkComplete().waitAndClick(5);
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnDocumentsSelectionNext().Enabled()  ;}}), Input.wait30); 
  					getbtnDocumentsSelectionNext().waitAndClick(5);
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnProductionGuardMarkComplete().Visible()  ;}}), Input.wait30); 
  					getbtnProductionGuardMarkComplete().waitAndClick(5);
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getOkButton().Visible()  ;}}), Input.wait30); 
  					getOkButton().waitAndClick(10);
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnProductionGuardNext().Enabled()  ;}}), Input.wait30); 
  					getbtnProductionGuardNext().waitAndClick(5);
  					
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getlstProductionRootPaths().Visible()  ;}}), Input.wait30); 
  					getlstProductionRootPaths().selectFromDropdown().selectByVisibleText(Input.prodPath);
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getProductionOutputLocation_ProductionDirectory().Visible()  ;}}), Input.wait30); 
  					getProductionOutputLocation_ProductionDirectory().SendKeys(exportDirectory);
  					
  					driver.scrollPageToTop();
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnProductionLocationMarkComplete().Visible()  ;}}), Input.wait30); 
  					getbtnProductionLocationMarkComplete().waitAndClick(10);
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnProductionLocationNext().Enabled()  ;}}), Input.wait30); 
  					getbtnProductionLocationNext().waitAndClick(10);
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getPreviewprod().Enabled()  ;}}), Input.wait30); 
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnProductionSummaryMarkComplete().Visible()  ;}}), Input.wait30); 
  					getbtnProductionSummaryMarkComplete().waitAndClick(10);
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnProductionSummaryNext().Enabled()  ;}}), Input.wait30); 
  					getbtnProductionSummaryNext().waitAndClick(10);
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnProductionGenerate().Visible()  ;}}), Input.wait30); 
  					getbtnProductionGenerate().waitAndClick(10);
  					
  					generateWait.until(ExpectedConditions.textToBe(productionGenerateStatus().getBy(), "Reserving Bates Range Complete"));
  					System.out.println(productionGenerateStatus().getText());
  					try {
  						Boolean continuePresent = generateWait.until(ExpectedConditions.textToBe(getbtnProductionGenerate().getBy(), "Continue Generation"));
  						if (continuePresent) {
  						getbtnProductionGenerate().Click();}
  					} catch (Exception e) {};
  					
  					
  					
//  					System.out.println("Wait until regenerate is enabled");
//  					for (int i = 0; i < 120; i++)
//  					{
//  						try
//  						{
//  							
//  							
//  							this.driver.getWebDriver().get(Input.url+"Production/Home");
//  					    	getProductionLink().waitAndClick(5);
//  							getbtnGenerateMarkComplete().waitAndClick(5);
//  							System.out.println("Generated");
//  							break;
//  							
//  						}
//  						catch (Exception e)
//  						{
//  							Thread.sleep(10000);
//  							driver.Navigate().refresh();
//  							
//  						
//  						}
//  					}
  				
  				
//  					String batesno = getProd_BatesRange().getText();
//  					System.out.println(batesno);
  					
//  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//  							getbtnSummaryNext().Enabled()  ;}}), Input.wait30); 
//  					getbtnSummaryNext().waitAndClick(10);
  					  				
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getConfirmProductionCommit().Enabled()  ;}}), Input.wait30); 
  					getConfirmProductionCommit().waitAndClick(10);
  					
  					 String PDocCount = getProductionDocCount().getText();
  			         int Doc = Integer.parseInt(PDocCount);
  			         System.out.println(Doc); 
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getReviewproductionButton().Visible()  ;}}), Input.wait30); 
  					getReviewproductionButton().waitAndClick(10);
  					
  					String location = getDestinationPathText().getText();
  			        System.out.println(location);

  								
  				}
  				
  	   public void FillingallsectionsProduction(String productionname,String PrefixID,String SuffixID,final String foldername) 
  			    		throws InterruptedException{
  					
				  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				  							getComponentsMarkComplete().Enabled()  ;}}), Input.wait30); 
				  	getComponentsMarkComplete().waitAndClick(10);
				  					
				  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				  						getComponentsMarkNext().Enabled()  ;}}), Input.wait30); 
				  	getComponentsMarkNext().waitAndClick(10);
				  					
				  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				  							getBeginningBates().Enabled()  ;}}), Input.wait30); 
				  	getBeginningBates().SendKeys("100");
				  					
				  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				  				gettxtBeginningBatesIDPrefix().Enabled()  ;}}), Input.wait30); 
				  	gettxtBeginningBatesIDPrefix().SendKeys(PrefixID);
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							gettxtBeginningBatesIDSuffix().Enabled()  ;}}), Input.wait30); 
  					gettxtBeginningBatesIDSuffix().SendKeys(SuffixID);
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							gettxtBeginningBatesIDMinNumLength().Enabled()  ;}}), Input.wait30); 
  					gettxtBeginningBatesIDMinNumLength().SendKeys("10");
  					
  					driver.scrollingToBottomofAPage();
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getlstSortingMetaData().Enabled()  ;}}), Input.wait30); 
  					getlstSortingMetaData().selectFromDropdown().selectByVisibleText("DocID");
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getlstSortingOrder().Enabled()  ;}}), Input.wait30); 
  					getlstSortingOrder().selectFromDropdown().selectByVisibleText("Ascending");
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getlstSortingMetaData().Enabled()  ;}}), Input.wait30); 
  					getlstSubSortingMetaData().selectFromDropdown().selectByVisibleText("CustodianName");
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getlstSubSortingOrder().Enabled()  ;}}), Input.wait30); 
  					getlstSubSortingOrder().selectFromDropdown().selectByVisibleText("Ascending");
  					
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getKeepFamiliesTogether().Visible()  ;}}), Input.wait30); 
  					getKeepFamiliesTogether().waitAndClick(10);
  					
  					driver.scrollPageToTop();
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getNumAndSortMarkComplete().Enabled()  ;}}), Input.wait30); 
  					getNumAndSortMarkComplete().waitAndClick(10);
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getNumAndSortNext().Enabled() ;}}), Input.wait30); 
  					getNumAndSortNext().waitAndClick(10);
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getFolderRadioButton().Visible() ;}}), Input.wait30); 
  					getFolderRadioButton().waitAndClick(10);
  					
  					Thread.sleep(5000);
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getSelectFolder(foldername).Visible() ;}}), Input.wait30); 
  					getSelectFolder(foldername).waitAndClick(5);
  					
  					driver.scrollingToBottomofAPage();
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getIncludeFamilies().Visible()  ;}}), Input.wait30); 
  					getIncludeFamilies().waitAndClick(10);
  					
  					driver.scrollPageToTop();
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnDocumentsSelectionMarkComplete().Visible()  ;}}), Input.wait30); 
  					getbtnDocumentsSelectionMarkComplete().waitAndClick(5);
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnDocumentsSelectionNext().Enabled()  ;}}), Input.wait30); 
  					getbtnDocumentsSelectionNext().waitAndClick(5);
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnProductionGuardMarkComplete().Visible()  ;}}), Input.wait30); 
  					getbtnProductionGuardMarkComplete().waitAndClick(5);
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getOkButton().Visible()  ;}}), Input.wait30); 
  					getOkButton().waitAndClick(10);
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnProductionGuardNext().Enabled()  ;}}), Input.wait30); 
  					getbtnProductionGuardNext().waitAndClick(5);
  					
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getlstProductionRootPaths().Visible()  ;}}), Input.wait30); 
  					getlstProductionRootPaths().selectFromDropdown().selectByVisibleText(Input.prodPath);
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getProductionOutputLocation_ProductionDirectory().Visible()  ;}}), Input.wait30); 
  					getProductionOutputLocation_ProductionDirectory().SendKeys(productionname);
  					
  					driver.scrollPageToTop();
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnProductionLocationMarkComplete().Visible()  ;}}), Input.wait30); 
  					getbtnProductionLocationMarkComplete().waitAndClick(10);
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnProductionLocationNext().Enabled()  ;}}), Input.wait30); 
  					getbtnProductionLocationNext().waitAndClick(10);
  					
//  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//  							getPreviewprod().Enabled()  ;}}), Input.wait30); 
//  					getPreviewprod().waitAndClick(10);
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnProductionSummaryMarkComplete().Visible()  ;}}), Input.wait30); 
  					getbtnProductionSummaryMarkComplete().waitAndClick(10);
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnProductionSummaryNext().Enabled()  ;}}), Input.wait30); 
  					getbtnProductionSummaryNext().waitAndClick(10);
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnProductionGenerate().Visible()  ;}}), Input.wait30); 
  					getbtnProductionGenerate().waitAndClick(10);
  					System.out.println("Wait until regenerate is enabled");
  					
  					generateWait.until(ExpectedConditions.textToBe(productionGenerateStatus().getBy(), "Reserving Bates Range Complete"));
  					System.out.println(productionGenerateStatus().getText());
  					try {
  						Boolean continuePresent = generateWait.until(ExpectedConditions.textToBe(getbtnProductionGenerate().getBy(), "Continue Generation"));
  						if (continuePresent) {
  						getbtnProductionGenerate().Click();}
  					} catch (Exception e) {};
  					
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
  	
  	   public void savetemplate(String templatename)
  	   {
  		this.driver.getWebDriver().get(Input.url+"Production/Home");
  		WebElement dropdown =  wait.until(ExpectedConditions.presenceOfElementLocated(productionStateFilterDropdown().getBy()));
		dropdown.click();
		
		String totalCountId = "totalProductionCount";
		final String oldtotalCount = driver.getWebDriver().findElement(By.id(totalCountId)).getText();
				
		WebElement completed = wait.until(ExpectedConditions.elementToBeClickable(productionStateFilterCompleted().getBy()));
		if (completed.findElement(By.xpath(xpathThreeStepsUp)).getAttribute("class") != "active") {
			completed.click();
		}
		
		driver.getWebDriver().findElement(By.id("ProductionSetdiv")).click();
		
		ExpectedCondition<WebElement> totalCountChanged1 = BaseClass.waitForTextToChangeCondition(By.id(totalCountId), oldtotalCount);
		wait.until(totalCountChanged1);
		
		
//		getProdStateFilter().WaitUntilPresent();
//		getProdStateFilter().selectFromDropdown().selectByVisibleText("COMPLETED");
	    getprod_ActionButton().waitAndClick(20);
	    getprod_Action_SaveTemplate().waitAndClick(10);
	    	
	    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						getprod_Templatetext().Visible()  ;}}), Input.wait30); 
	    getprod_Templatetext().SendKeys(templatename);
	    getProdExport_SaveButton().waitAndClick(5);
	    base.VerifySuccessMessage("Production Saved as a Custom Template.");
	   }

  		public void ProductionwithNatives(String productionname,String productionname1, String PrefixID,
  				String SuffixID,final String foldername,String templatename) 
  				throws InterruptedException 
	
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
  			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  					getAddNewProductionbutton().Visible()  ;}}), Input.wait30); 
  			getAddNewProductionbutton().waitAndClick(10);
  			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  					getProductionName().Visible()  ;}}), Input.wait30); 
  			getProductionName().SendKeys(productionname1);
  			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  					getProductionDesc().Visible()  ;}}), Input.wait30); 
  			getProductionDesc().SendKeys(productionname1);
  			
  			getprod_LoadTemplate().getWebElement().findElement(getOption(templatename).getBy()).click();
//  			getprod_LoadTemplate().selectFromDropdown().selectByVisibleText("Template (Production)");
  			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  					getBasicInfoMarkComplete().Visible()  ;}}), Input.wait30); 
  			getBasicInfoMarkComplete().waitAndClick(10);
  			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  					getComponentsMarkComplete().Enabled()  ;}}), Input.wait30); 
  			getComponentsMarkComplete().waitAndClick(10);
  		}
		
  		public void ProductionwithTechIssuetags(String productionname,String PrefixID,
  				String SuffixID,final String foldername,String Tagnameprev,String Tagnametech) 
  				throws InterruptedException 
	
  		{
  			startProduction(productionname);
			fillDATFields( );
			fillTIFFFields(Tagnameprev);
			fillTechIssues(Tagnametech);
  			 
  			driver.scrollingToBottomofAPage();
  			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  					getTextChkBox().Enabled()  ;}}), Input.wait30); 
  			getTextChkBox().waitAndClick(10);
  			 driver.scrollPageToTop();
  			 FillingallsectionsProduction(productionname, PrefixID, SuffixID, foldername);
  				
  	  	    }
  			 
  		//added by Narendra
  	    public void ProductionFilter(){
  	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	    			getFilterByButton().Visible()  ;}}), Input.wait30);
  	    	getFilterByButton().waitAndClick(10);
  	       	for(int i=1; i<getFilterOptions().size(); i++) {
  	       	getFilter(i).waitAndClick(10);
  	       	}
  	          	    
  	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	    			getFilterByDRAFT().Visible()  ;}}), Input.wait30); 
  	    	getFilterByDRAFT().waitAndClick(10);
  	    	getRefreshButton().waitAndClick(10);
  	    	
  	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	    			getFilterByButton().Visible()  ;}}), Input.wait30);
  	    	getFilterByButton().waitAndClick(10);
  	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	    			getFilterByDRAFT().Visible()  ;}}), Input.wait30); 
  	    	getFilterByDRAFT().waitAndClick(10);
  	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	    			getFilterByINPROGRESS().Visible()  ;}}), Input.wait30); 
  	    	getFilterByINPROGRESS().waitAndClick(10);
  	    	getRefreshButton().waitAndClick(10);
  	    	
  	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	    			getFilterByButton().Visible()  ;}}), Input.wait30);
  	    	getFilterByButton().waitAndClick(10);
  	       	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	    			getFilterByINPROGRESS().Visible()  ;}}), Input.wait30); 
  	        getFilterByINPROGRESS().waitAndClick(10);
  	        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	        		getFilterByFAILED().Visible()  ;}}), Input.wait30); 
  	        getFilterByFAILED().waitAndClick(10);
   	    	getRefreshButton().waitAndClick(10);
   	    	
   	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	    			getFilterByButton().Visible()  ;}}), Input.wait30);
  	    	getFilterByButton().waitAndClick(10);
  	    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   	        		getFilterByFAILED().Visible()  ;}}), Input.wait30); 
   	        getFilterByFAILED().waitAndClick(10);
  	       	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	       		getFilterByCompleted().Visible()  ;}}), Input.wait30); 
  	        getFilterByCompleted().waitAndClick(10);
  	        getRefreshButton().waitAndClick(10);
  	        System.out.println("Verified Production Filer");
  	    }
  	    
  	    public void ProductionSort(){
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
	
  	    public void GridView() throws InterruptedException{
  	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	    			getFilterByButton().Visible()  ;}}), Input.wait30);
  	    	getFilterByButton().waitAndClick(10);

  	       	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	       		productionStateFilterCompleted().Visible()  ;}}), Input.wait30); 
  	        productionStateFilterCompleted().waitAndClick(10);
  	        driver.getWebDriver().findElement(productionSetDiv().getBy()).click();
  	        
  	        String tileItemsOld = getProductionItemsTileItems().getText();
  	        
  	        wait.until(BaseClass.waitForTextToChangeCondition(getProductionItemsTileItems().getWebElement(), tileItemsOld));
  	        String tileitems = getProductionItemsTileItems().getText();
  	        System.out.println(tileitems);
  	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	    			getGridView().Visible()  ;}}), Input.wait30);
  	    	getGridView().waitAndClick(10);
  	    	
  	  		String griditemstext = wait.until(BaseClass.waitForTextToLoad(getProductionItemsGridItems().getBy()));
  	    	System.out.println(griditemstext);
  	    	assertion.assertTrue(griditemstext.contains(tileitems));
	   		System.out.println("Verified Tile Production and Grid Production are equal");
	   		
  	    }

  	    public void SaveTemplate(final String tempName) throws InterruptedException{
  	    	driver.getWebDriver().get(Input.url+"Production/Home");
  	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			getFilterByButton().Visible()  ;}}), Input.wait30);
	    	getFilterByButton().waitAndClick(10);

	       	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	       		getFilterByCompleted().Visible()  ;}}), Input.wait30); 
	        getFilterByCompleted().waitAndClick(10);
	        getRefreshButton().waitAndClick(10);
	        
	        //System.out.println("Number of records in a current page : "+getProductionItemsTile().size());
	       	        
	        if(getProductionItemsTile().size()>0)
	        {
	        	wait.until(ExpectedConditions.elementToBeClickable(getArrow().getBy())).click();
	        	wait.until(ExpectedConditions.elementToBeClickable(getSaveTemplate().getBy())).click();
	        	
	        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	        			getTemplateName().Visible()  ;}}), Input.wait30); 
	        	getTemplateName().SendKeys(tempName);
	        	
	        	getSave().waitAndClick(10);
	        	BaseClass bc= new BaseClass(driver);
	        	bc.VerifySuccessMessage("Production Saved as a Custom Template.");
	        	bc.CloseSuccessMsgpopup();
	        	getManageTemplates().Click();
		        
	        	wait.until(ExpectedConditions.elementToBeClickable(getDeleteTemplate().getBy()));
	        	ArrayList<String> tableele = new ArrayList<String>();
		   		java.util.List<WebElement> table = getCustomTemplates().FindWebElements();
				for (int i = 1; i<getCustomTemplates().size();i++) {
					tableele.add(table.get(i).getText());
	   		 	}
				assertion.assertTrue(tableele.contains(tempName));
				System.out.println("Verified saved template under Custom template");
	        }
  	  }

  	    public void DeleteTemplate(final String tempName) throws InterruptedException{
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	    			getFilterByButton().Visible()  ;}}), Input.wait30);
  	    	getFilterByButton().waitAndClick(10);

  	       	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	       		getFilterByCompleted().Visible()  ;}}), Input.wait30); 
  	        getFilterByCompleted().waitAndClick(10);
  	        getRefreshButton().waitAndClick(10);
  	        
  	        System.out.println("Number of records in a current page : "+getProductionItemsTile().size());
  	       	        
  	        if(getProductionItemsTile().size()>0)
  	        {
  	        	wait.until(ExpectedConditions.elementToBeClickable(getArrow().getBy())).click();
  	        	wait.until(ExpectedConditions.elementToBeClickable(getSaveTemplate().getBy())).click();
//  	        	getSaveTemplate().waitAndClick(10);
  	        	
  	        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	        			getTemplateName().Visible()  ;}}), Input.wait30); 
  	        	
  	        	wait.until(ExpectedConditions.visibilityOfElementLocated(getTemplateName().getBy()));
  	        	getTemplateName().SendKeys(tempName);
  	        	System.out.println(tempName);
  	        	
  	        	getSave().waitAndClick(10);
  	        	BaseClass bc= new BaseClass(driver);
  	        	bc.VerifySuccessMessage("Production Saved as a Custom Template.");
  	        	bc.CloseSuccessMsgpopup();
  	        	getManageTemplates().Click();
  		        	
  	        	wait.until(ExpectedConditions.elementToBeClickable(getDeleteTemplate().getBy()));
  	        	ArrayList<String> tableele = new ArrayList<String>();
  		   		java.util.List<WebElement> table = getCustomTemplates().FindWebElements();
  				for (int i = 1; i<getCustomTemplates().size();i++) {
  					tableele.add(table.get(i).getText());
  	   		 	}
  				System.out.println(tableele);
  				assertion.assertTrue(tableele.contains(tempName));
  				
  				 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  						getCustomTemplateName(tempName).Visible()  ;}}), Input.wait30); 
  				getCustomTemplateName(tempName).waitAndClick(10);
  				
  				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  						getDeleteTemplate().Visible()  ;}}), Input.wait30); 
  				getDeleteTemplate().waitAndClick(10);
  				
  				getOK().waitAndClick(10);
  				bc.VerifySuccessMessage("Custom Template deleted successfully");
  				
  				System.out.println("Verified saved template got deleted successfully under Custom template");
  	        }
    	  }

  	    public void CreateProductionSets(final String prodsetName) throws InterruptedException{
  	    	
  	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getCreateProductionSet().Visible()  ;}}), Input.wait30);
    			getCreateProductionSet().waitAndClick(10);

  	        	
  	        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	        			getSetName().Visible()  ;}}), Input.wait30); 
  	        	getSetName().SendKeys("Demo");
  	        	
  	        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	        			getCancelSet().Visible()  ;}}), Input.wait30); 
  	        	getCancelSet().waitAndClick(10);
  	    	
    			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getCreateProductionSet().Visible()  ;}}), Input.wait30);
    			getCreateProductionSet().waitAndClick(10);

  	        	
  	        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	        			getSetName().Visible()  ;}}), Input.wait30); 
  	        	getSetName().SendKeys(prodsetName);
  	        	
  	        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	        			getSaveSet().Visible()  ;}}), Input.wait30); 
  	        	getSaveSet().waitAndClick(10);
  	        	BaseClass bc= new BaseClass(driver);
  	        	Thread.sleep(3000);
  	        	bc.VerifySuccessMessage("Production Set Added Successfully");
  	        	bc.CloseSuccessMsgpopup();
  	        	Thread.sleep(3000);	        	
  				System.out.println("Verified created production set");

    	  }
  	    
  	    public void DeleteteProductionSets(final String prodsetName) throws InterruptedException{
	    	
  	    		getProdExport_ProductionSets().waitAndClick(10);
	        	
	        	String myString = prodsetName.concat(" (Production Set)");
	        			
	        	getProdExport_ProductionSets().selectFromDropdown().selectByVisibleText(myString);	
	        	
	        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	        			getDeleteProd().Visible()  ;}}), Input.wait60); 
	        	getDeleteProd().waitAndClick(10);
				
				getOK().waitAndClick(30);
				BaseClass bc= new BaseClass(driver);
				Thread.sleep(3000);
				bc.VerifySuccessMessage("Production Set has been deleted successfully.");
				bc.CloseSuccessMsgpopup();
				System.out.println("Verified deleted production set");

  	  }
  	  
  	    public void ProductionLock() throws InterruptedException{
  	    	wait.until(ExpectedConditions.elementToBeClickable(getFilterByButton().getBy())).click();
	    	for(int i=1; i<getFilterOptions().size(); i++) {
	       	getFilter(i).waitAndClick(10);
	       	}
	      
	       	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	       		getFilterByCompleted().Visible()  ;}}), Input.wait30); 
	        getFilterByCompleted().waitAndClick(10);
	        
	        productionSetDiv().Click();
	        String oldText = gettotalCount().getText();
	        wait.until(BaseClass.waitForTextToChangeCondition(gettotalCount().getBy(), oldText));
	        
	        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	        		getRefreshButton().Visible()  ;}}), Input.wait30);
	        getRefreshButton().waitAndClick(10);
	        
	        wait.until(ExpectedConditions.elementToBeClickable(getArrow().getBy())).click();
	        
	        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	        		getLock().Visible()  ;}}), Input.wait30); 
	        getLock().waitAndClick(10);
	        wait.until(ExpectedConditions.elementToBeClickable(getOK().getBy())).click();
	        BaseClass bc= new BaseClass(driver);
        	bc.VerifySuccessMessage("Production Lock Successfully.");
        	bc.CloseSuccessMsgpopup();
        	
        	String oldText1 = gettotalCount().getText();
	        wait.until(BaseClass.waitForTextToChangeCondition(gettotalCount().getBy(), oldText1));
        	
        	wait.until(ExpectedConditions.elementToBeClickable(getFilterByButton().getBy())).click();
	    	for(int i=1; i<getFilterOptions().size(); i++) {
	       	getFilter(i).waitAndClick(10);
	       	}
        	
	       	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		       		getFilterByCompleted().Visible()  ;}}), Input.wait30); 
		    getFilterByCompleted().waitAndClick(10);
		    
		    productionSetDiv().Click();

		    String oldText2 = gettotalCount().getText();
	        wait.until(BaseClass.waitForTextToChangeCondition(gettotalCount().getBy(), oldText2));
	        getprod_ActionButton().getWebElement().click();
			
			wait.until(ExpectedConditions.elementToBeClickable(getUnlock().getBy())).click();
			getOK().waitAndClick(10);
	        String locktxt = getLockIcon().GetAttribute("class");
        	assertion.assertTrue(locktxt.contains("lock"));
        	System.out.println("Verified Lock Production");
	    }

  	    public void CustomizeTemplate(final String tempName, String productionName) throws InterruptedException{
  	    	
  	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			getFilterByButton().Visible()  ;}}), Input.wait30);
	    	getFilterByButton().waitAndClick(10);
	       	for(int i=1; i<getFilterOptions().size(); i++) {
	       	getFilter(i).waitAndClick(10);
	       	}
	      
	       	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	       		getFilterByCompleted().Visible()  ;}}), Input.wait30); 
	        getFilterByCompleted().waitAndClick(10);
	        
	        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	        		getRefreshButton().Visible()  ;}}), Input.wait30);
	        getRefreshButton().waitAndClick(10);
	        
	        wait.until(ExpectedConditions.elementToBeClickable(getArrow().getBy())).click();
	        
	        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	        		getSaveTemplate().Visible()  ;}}), Input.wait30); 
	        getSaveTemplate().waitAndClick(10);
	        
	        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getTemplateName().Visible()  ;}}), Input.wait30); 
        	getTemplateName().SendKeys(tempName);
        	System.out.println(tempName);
        	
        	getSave().waitAndClick(10);
        	BaseClass bc= new BaseClass(driver);
        	bc.VerifySuccessMessage("Production Saved as a Custom Template.");
        	bc.CloseSuccessMsgpopup();
        	
        	getManageTemplates().Click();
        	
        	wait.until(ExpectedConditions.elementToBeClickable(getDeleteTemplate().getBy()));
        	
        	ArrayList<String> tableele = new ArrayList<String>();
	   		java.util.List<WebElement> table = getCustomTemplates().FindWebElements();
			for (int i = 0; i<getCustomTemplates().size();i++) {
				tableele.add(table.get(i).getText());
   		 	}
			System.out.println(tableele);
			
			assertion.assertTrue(tableele.contains(tempName));
			System.out.println("Verified saved template under Custom template");
	        
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getProductionsExportsTabs().Visible()  ;}}), Input.wait30); 
			getProductionsExportsTabs().waitAndClick(10);
			
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getAddNewProductionbutton().Visible()  ;}}), Input.wait30); 
        	getAddNewProductionbutton().waitAndClick(10);
        	
        	wait.until(ExpectedConditions.visibilityOfElementLocated(getProductionName().getBy())).sendKeys(productionName);
  	  		
         	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	  				getProductionDesc().Visible()  ;}}), Input.wait30); 
  	  		getProductionDesc().SendKeys(productionName);
  	  		
  	  		String myString = tempName.concat(" (Production)");
  	  		getLoadTemplate().selectFromDropdown().selectByVisibleText(myString);
  	  		
  	  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	  			getBasicInfoSave().Visible()  ;}}), Input.wait30); 
  	  		getBasicInfoSave().waitAndClick(10);
  	  		
  	  		wait.until(ExpectedConditions.elementToBeClickable(menuProductions().getBy())).click();
  	  		wait.until(ExpectedConditions.attributeToBe(menuProductionActive().getBy(), "class", "active"));
  	  		menuProductions().Click();

  	  		wait.until(ExpectedConditions.urlToBe(Input.url+"Production/Home"));
  	  	    wait.until(ExpectedConditions.elementToBeClickable(getArrow().getBy())).click();

  	  		
  	  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	  				getOpenWizard().Visible()  ;}}), Input.wait30); 
  	  		getOpenWizard().waitAndClick(10);
  	  	
        	System.out.println("Verified Customized Template");
        	
        	//Clean up
        	wait.until(ExpectedConditions.elementToBeClickable(menuProductions().getBy())).click();
  	  		wait.until(ExpectedConditions.attributeToBe(menuProductionActive().getBy(), "class", "active"));
  	  		menuProductions().Click();
  	  		getManageTemplates().Click();
        	
            wait.until(ExpectedConditions.elementToBeClickable(getDeleteTemplate().getBy())).click();
        	getOkButton().Click();
        	bc.VerifySuccessMessage("Custom Template deleted successfully");
        	bc.CloseSuccessMsgpopup();
        	
        	menuProductions().Click();
  	  		wait.until(ExpectedConditions.attributeToBe(menuProductionActive().getBy(), "class", "active"));
  	  		menuProductions().Click();
  	  		wait.until(ExpectedConditions.elementToBeClickable(getArrow().getBy())).click();
  	  		wait.until(ExpectedConditions.elementToBeClickable(getDelete().getBy())).click();
	  	  	getOkButton().Click();
	    	bc.VerifySuccessMessage("Production deleted successfully");
	    	bc.CloseSuccessMsgpopup();
    	  }

  	    public void VerifyProductionSet(final String prodsetName) throws InterruptedException{
	    	
	  	    	getProdExport_ProductionSets().waitAndClick(10);
	        	
	        	String myString = prodsetName.concat(" (Production Set)");
	        			
	        	getProdExport_ProductionSets().selectFromDropdown().selectByVisibleText(myString);
	        	
	        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	        			getProductionsSetName().Visible()  ;}}), Input.wait30); 
	        	String myprodsetname = getProductionsSetName().getText();
	        	
//	        	Assert.assertEquals(prodsetName, myprodsetname);

  	  }

  	    public void ProductionDeletionCheck() throws InterruptedException{
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			getFilterByButton().Visible()  ;}}), Input.wait30);
	    	getFilterByButton().waitAndClick(10);
	       	for(int i=1; i<getFilterOptions().size(); i++) {
	       	getFilter(i).waitAndClick(10);
	       	}
	      
	       	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	       		getFilterByCompleted().Visible()  ;}}), Input.wait30); 
	        getFilterByCompleted().waitAndClick(10);
	        
	        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	        		getRefreshButton().Visible()  ;}}), Input.wait30);
	        getRefreshButton().waitAndClick(10);
	        
	        wait.until(ExpectedConditions.elementToBeClickable(getArrow().getBy())).click();
	        
	        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	        		getTileDelete().Visible()  ;}}), Input.wait30); 
       
	      	String deletetiletxt = getTileDelete().GetAttribute("class");
	      	assertion.assertTrue(deletetiletxt.contains("disable"));
	      	
	      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	    			getGridView().Visible()  ;}}), Input.wait30);
  	    	getGridView().waitAndClick(10);
  	    	
  	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	        		getRefreshButton().Visible()  ;}}), Input.wait30);
  	    	getRefreshButton().waitAndClick(10);
  	    	
  	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	    			getProductionListGridViewTable().Visible()  ;}}), Input.wait30);
  	    	getProductionListGridViewTable().waitAndClick(10);
  	    	
  	    	
  	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	    			getAction().Visible()  ;}}), Input.wait30);
  	    	getAction().waitAndClick(10);
  	    	
  	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	    			getGridDelete().Visible()  ;}}), Input.wait30); 
       
	      	String deletegridtxt = getGridDelete().GetAttribute("class");
	      	assertion.assertTrue(deletegridtxt.contains("disable"));
	
	      	System.out.println("Verified Production Deletion is disable");
	    }
  	    
  	    public void TiffPDF(String productionName) throws InterruptedException{
			
	      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	      			getAddNewProductionbutton().Visible()  ;}}), Input.wait30); 
	      	getAddNewProductionbutton().waitAndClick(10);
	      	
	      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	  				getProductionName().Visible()  ;}}), Input.wait30); 
	  		getProductionName().SendKeys(productionName);
	  		
	  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	  				getProductionDesc().Visible()  ;}}), Input.wait30); 
	  		getProductionDesc().SendKeys(productionName);
	  		
	  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getBasicInfoMarkComplete().Visible()  ;}}), Input.wait30); 
			getBasicInfoMarkComplete().waitAndClick(10);
			
			Thread.sleep(5000);
	  		
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getTIFFChkBox().Enabled() ;}}), Input.wait30); 
			getTIFFChkBox().waitAndClick(10);
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getTIFFTab().Enabled()  ;}}), Input.wait30); 
			getTIFFTab().waitAndClick(10);
			
			driver.scrollingToBottomofAPage();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getadvance().Visible()  ;}}), Input.wait30); 
			getadvance().waitAndClick(10);
			
			driver.scrollingToBottomofAPage();
			
			System.out.println("Verified Advance Show/Hide Button wworking as expected");
			
			assertion.assertTrue(getGenerateLoadFile().Enabled());
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getGenerateLoadFile().Enabled() ;}}), Input.wait30); 
			getGenerateLoadFile().waitAndClick(10);
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getGenerateLoadFile().Visible() ;}}), Input.wait30); 
			getGenerateLoadFile().waitAndClick(10);
	  		
			System.out.println("Verified Generate Load File (LST) with Toggle button");
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getSlipSheets().Visible() ;}}), Input.wait30); 
			getSlipSheets().waitAndClick(10);
			
			for(int i=1;i<=3;i++)
			{
			getAvailableFields(i).waitAndClick(10);
			}
	  			  	
	  		System.out.println("Verified METADATA/WORKPRODUCT & CALCULATED Tabs");
  	  }
  	    
  	    public void DeleteProduction() throws InterruptedException{
  	    	String productionname1 = "P"+Utility.dynamicNameAppender();
  	    	startProduction(productionname1);
  	    	wait.until(ExpectedConditions.elementToBeClickable(menuProductions().getBy())).click();
  	  		wait.until(ExpectedConditions.attributeToBe(menuProductionActive().getBy(), "class", "active"));
  	  		menuProductions().Click();
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			getFilterByButton().Visible()  ;}}), Input.wait30);
	    	getFilterByButton().waitAndClick(10);
	       	for(int i=1; i<getFilterOptions().size(); i++) {
	       	getFilter(i).waitAndClick(10);
	       	}
	      
	       	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	       			getFilterByDRAFT().Visible()  ;}}), Input.wait30); 
	       	getFilterByDRAFT().waitAndClick(10);
	        
	        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	        		getRefreshButton().Visible()  ;}}), Input.wait30);
	        getRefreshButton().waitAndClick(10);
	        
	        wait.until(ExpectedConditions.elementToBeClickable(getArrow().getBy())).click();
	        
	        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	        		getDelete().Visible()  ;}}), Input.wait30); 
	        getDelete().waitAndClick(10);
	        wait.until(ExpectedConditions.elementToBeClickable(getOK().getBy())).click();
	        BaseClass bc= new BaseClass(driver);
	      	bc.VerifySuccessMessage("Production deleted successfully");
	      	bc.CloseSuccessMsgpopup();
	      	
	      	System.out.println("Verified deleted Production");
	    }
  	    
}
    

   
