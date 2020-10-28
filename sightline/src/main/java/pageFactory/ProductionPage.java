package pageFactory;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;

public class ProductionPage {

    Driver driver;
    BaseClass base;
    public String ProdPathallredact;
    public String ProdPathsomeredact;
 
    
    public Element getAddNewProductionbutton(){ return driver.FindElementByXPath("//a[contains(.,'Add a New Production')]"); }
    public Element getProductionName(){ return driver.FindElementById("ProductionName"); }
    public Element getProductionNameLabel(){ return driver.FindElementByXPath("//*[@id=\"MainDivBasicInfo\"]/div[3]/div/div/div/div/div[1]/label[1]"); }
    public Element getProductionNameWarning(){ return driver.FindElementByXPath("//*[@id=\"MainDivBasicInfo\"]/div[3]/div/div/div/div/div[1]/label[2]/span/span"); }
    public Element getProductionDesc(){ return driver.FindElementById("ProductionDescription"); }
    public Element getProductionDescLabel(){ return driver.FindElementByXPath("//*[@id=\"MainDivBasicInfo\"]/div[3]/div/div/div/div/div[2]/label[1]"); }
    public Element getProductionLoadTempLabel(){ return driver.FindElementByXPath("//*[@id=\"MainDivBasicInfo\"]/div[3]/div/div/div/div/div[3]/label[1]"); }
    public Element getBasicInfoMarkComplete(){ return driver.FindElementById("BasicInfoMarkComplete"); }
    public Element getBasicInfoSave(){ return driver.FindElementById("BasicInfoSave"); }
    public Element getBasicInfoNext(){ return driver.FindElementById("BasicInfoNext"); }
    public ElementCollection getDAT_FieldMappingRows(){ return driver.FindElementsByCssSelector("#dt_basic>tbody>tr");}
    public Element getDAT_FieldClassification1(){ return driver.FindElementById("TY_0"); }
    public Element getDAT_FieldClassification2(){ return driver.FindElementById("TY_1"); }
    public Element getDAT_FieldClassification3(){ return driver.FindElementById("TY_1"); }
    public Element getDAT_FieldClassification1(String value){ return driver.FindElementByCssSelector(String.format("#TY_0%s",value)); }
    public Element getDAT_FieldClassificationOption(int index, String value){ return driver.FindElementByCssSelector(String.format("#TY_%d [value='%s']", index, value));}
    public Element getDAT_SourceField1(){ return driver.FindElementById("SF_0"); }
    public Element getDAT_SourceField2(){ return driver.FindElementById("SF_1"); }
    public Element getDAT_SourceField3() { return driver.FindElementById("SF_2"); }
    public Element getDAT_SourceField1(String value){ return driver.FindElementByCssSelector(String.format("#SF_0 %s",value)); }
    public Element getDAT_SourceFieldOption(int index, String value) { return driver.FindElementByXPath(String.format("//select[@id='SF_%d']//option[contains(text(),'%s')]", index, value)); }
    public Element getDAT_DATField1(){ return driver.FindElementById("DATFL_0"); }
    public Element getDAT_DATField2(){ return driver.FindElementById("DATFL_1"); }
    public Element getDAT_DATField3(){ return driver.FindElementById("DATFL_2"); }
    public Element getDAT_DATField(int index){ return driver.FindElementById(String.format("DATFL_%d", index)); }
    public Element getTIFF_CenterHeaderBranding(){ return driver.FindElementById("CenterHeaderBranding"); }
    public Element getTIFF_RightHeaderBranding(){ return driver.FindElementById("RightHeaderBranding"); }
    public Element getTIFF_LeftHeaderBranding(){ return driver.FindElementById("LeftHeaderBranding"); }
    public Element getPDF_InsertMetadataField(){ return driver.FindElementById("LaunchPDFeditor_0"); }
    public Element getPDF_CenterHeaderBranding(){ return driver.FindElementById("PDFCenterHeaderBranding"); }
    public Element getTIFF_CenterFooterBranding(){ return driver.FindElementById("CenterFooterBranding"); }
    public Element getTIFF_LeftFooterBranding(){ return driver.FindElementById("LeftFooterBranding"); }
    public Element getTIFF_RightFooterBranding(){ return driver.FindElementById("RightFooterBranding"); }
    public Element getTIFF_InsertMetadataField(){ return driver.FindElementById("Launcheditor_0"); }
    public Element getTIFF_selectedMetadataField(){ return driver.FindElementById("selectedMetadataField"); }
    public Element getTIFF_selectedMetadataField_Ok(){ return driver.FindElementByXPath("//*[@onclick='return AddToRedactor()']"); }
    public Element getManageTemplateTab() { return driver.FindElementById("ui-id-2"); }
    public Element getManageTemplateDiv() { return driver.FindElementById("tabs-b"); }
 
    public Element getTileViewIcon() { return driver.FindElementById("TileView"); }
    public Element getTileProdCount() { return driver.FindElementById("hddtotalProductionCount"); }
    public Element getGridViewIcon() { return driver.FindElementById("GridView"); }
    public Element getGridProdCount() { return driver.FindElementById("ProductionListGridViewTable_info"); }
    
    public Element getComponentsMarkComplete(){ return driver.FindElementById("ComponentsMarkComplete"); }
    public Element getComponentsMarkNext(){ return driver.FindElementById("ComponentsNext"); }
    public Element getComponentsMarkInComplete(){ return driver.FindElementById("ComponentsMarkInComplete"); }
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
    public Element getbtnProductionSummaryMarkInComplete(){ return driver.FindElementById("btnProductionSummaryMarkInComplete"); }
    public Element getbtnProductionSummaryNext(){ return driver.FindElementById("btnProductionSummaryNext"); }
    public Element getbtnProductionGenerate(){ return driver.FindElementById("btnProductionGenerate"); }
    public Element getProductionSettxt(){ return driver.FindElementById("ProductionSettxt"); }
    public Element getbtnGenerateMarkComplete(){ return driver.FindElementByXPath("//*[@id='btnGenerateMarkComplete']"); }
    public Element getbtnReGenerateMarkComplete(){ return driver.FindElementById("btnProductionReGenerate"); }
    public Element getbtnSummaryNext(){ return driver.FindElementById("btnGenerateNext"); }
    
    
    public Element getDATChkBox(){ return driver.FindElementByXPath("//input[@name='IsDATSelected']/following-sibling::i"); }
    public Element getDATTab(){ return driver.FindElementByXPath("//a[@href='#DATContainer']"); }
    public Element getDAT_AddField(){ return driver.FindElementByXPath(".//*[@id='DATContainer']//button[contains(.,'Add Field')]"); }
    public Element getDATComponentAdvanced(){ return driver.FindElementByCssSelector("#DATContainer div.advanced-dd-toggle"); }
    public Element getNativeChkBox(){ return driver.FindElementByXPath("//input[@name='IsNativeSelected']/following-sibling::i"); }
    public Element getNativeTab(){ return driver.FindElementByXPath("//a[@href='#NativeContainer']"); }
    public Element getNative_SelectAllCheck(){ return driver.FindElementByXPath(".//*[@id='native-table']//input[@name='IsSelectAllFileTypes']/following-sibling::i"); }
    public Element getNative_GenerateLoadFileLST(){ return driver.FindElementByXPath(".//*[@id='NativeContainer']//input[@name='ProduceLoadFile']/following-sibling::i"); }
    public Element getTIFFChkBox(){ return driver.FindElementByXPath("//input[@name='IsTIFFSelected']/following-sibling::i"); }
    public Element getTIFFTab(){ return driver.FindElementByXPath("//a[@href='#TIFFContainer']"); }
    public Element getTIFFTab_Page(){ return driver.FindElementByXPath("//*[@id=\"accordion\"]/div[3]/div[1]/h4/a"); }
    public Element getTIFF_EnterBranding(){ return driver.FindElementByXPath(".//*[@id='divCenterHeaderBranding']//div[@class='redactor-editor redactor-placeholder']"); }
    public Element getTIFF_InsertMetadataFieldOKButton(){ return driver.FindElementByXPath(".//*[@id='MetadataPopup']//button[contains(.,'OK')]"); }
    public Element getTIFF_EnableforPrivilegedDocs(){ return driver.FindElementByXPath(".//*[@id='TIFFContainer']//input[@name='CommonTIFFSettings.PlaceHolderImageSettings.EnabledforPrivDocs']/following-sibling::i"); }
    public ElementCollection getTIFFPrivilegeDocsDisabledToggle() { return driver.FindElementsByXPath("//input[@id='chkEnabledforPrivDocs' and @class='TIFF-encryp-check']"); }
    public Element getPDF_EnterBranding(){ return driver.FindElementByXPath(".//*[@id='divPDFCenterHeaderBranding']//div[@class='redactor-editor redactor-placeholder']"); }
    public Element getTIFF_SelectTagSButton(){ return driver.FindElementByXPath("//*[@id='btnSelectPrevTags']"); }
    public Element getTIFF_AllTags(){ return driver.FindElementByXPath("/html/body/div[3]/div/div[2]/div[2]/div/div[4]/form/div/div[3]/div[2]/div/div[1]/div/div[5]/div/div/div/form/fieldset/div/div[1]/div/ul/li/a/i[1]"); }
    public Element getTIFF_DefaultAutomationTag(){ return driver.FindElementByXPath("//div[@id='tagTreeTIFFComponent']//a[text()='Default Automation Tag']/parent::li"); }
    public Element getTIFF_Privileged(){ return driver.FindElementByXPath("//div[@id='tagTreeTIFFComponent']//a[text()='Privileged']/parent::li"); }
    public Element getTIFF_PrivileTagSelected(){ return driver.FindElementByXPath("//*[@id='PrevTagsLabel']"); }
    public Element getTIFF_PlaceholderText(){ return driver.FindElementByXPath(""); }
    public Element getNative_DefaultAutomationTag(){ return driver.FindElementByXPath("//div[@id='tagTreeNativeComponent']//a[text()='Default Automation Tag']");}
    public Element getNative_AttorneyCLientTag() {return driver.FindElementByXPath("/html/body/div[3]/div/div[2]/div[2]/div/div[4]/form/div/div[2]/div[2]/div/div/div[3]/div/div/div/form/fieldset/div/div[1]/div/ul/li/ul/li[4]/ul/li[1]/a/i[1]");}
    public Element getNative_ConfedentialTag() {return driver.FindElementByXPath("/html/body/div[3]/div/div[2]/div[2]/div/div[4]/form/div/div[2]/div[2]/div/div/div[3]/div/div/div/form/fieldset/div/div[1]/div/ul/li/ul/li[4]/ul/li[3]/a/i[1]");}
    public Element getSelectTagsButton() {return driver.FindElementByCssSelector(".btn.btn-primary.btn-sm.submitNativeSelection");}
    public Element getTIFF_SelectTagsButton() {return driver.FindElementById("btnTIFFPHSelectTags_0");}
    public Element getTIFF_SelectTagText(){ return driver.FindElementByCssSelector("div.col-md-12.tiff-img-logic > div:nth-child(3) > div.col-md-5.red.box > fieldset > div > div"); }
    public ElementCollection getTIFF_PageOptions(){ return driver.FindElementsByXPath("//label[@class = 'radio']//span[contains(text(), 'Multi-page')]/preceding-sibling::i");}
    
    public Element getTIFF_AttorneyClientTag() {return driver.FindElementByXPath("//div[@id='tagTreeTIFFComponent']//a[text()='Attorney_Client']");}
    public Element getNativeAttorneyClientTag() {return driver.FindElementByXPath("//div[@id='tagTreeNativeComponent']//a[text()='Attorney_Client']");}
    public Element getNativeConfidentialClientTag() {return driver.FindElementByXPath("//div[@id='tagTreeNativeComponent']//a[text()='Confidential']");}
    public Element getTIFF_AttorneyWorkProductClientTag() {return driver.FindElementByXPath("//div[@id='tagTreeTIFFComponent']//a[text()='Attorney_WorkProduct']");}    
    public Element getTIFF_TagSelectButton() {return driver.FindElementByCssSelector(".btn.btn-primary.btn-sm.submitSelectionTIFF");}
    public Element getSelectButton() { return driver.FindElementByXPath("//*[@class ='btn btn-primary btn-sm submitSelectionTIFF']"); }
   
    public Element getTextChkBox(){ return driver.FindElementByXPath(".//*[@id='accordion']//input[@name='IsTextSelected']/following-sibling::i"); }
    public Element getTextTab(){ return driver.FindElementByXPath("//a[@href='#TextContainer']"); }
    public Element getPDFChkBox(){ return driver.FindElementByXPath(".//*[@id='accordion']//input[@name='IsPDFSelected']/following-sibling::i"); }
    public Element getPDFTab(){ return driver.FindElementByXPath(".//*[@id='accordion']//a[@href='#PDFContainer']"); }
    public Element getKeepFamiliesTogether(){ return driver.FindElementByXPath(".//*[@id='divSortByMetadata_1']//input[@name='ProductionSortingSettings.SortByIsKeepFamiliesTogether']/following-sibling::i"); }
    public Element getSelectFolder(String foldername){ return driver.FindElementByXPath("//*[@id='folderTree']//ul[@class='jstree-children']//a[contains(.,'"+foldername+"')]"); }
    public Element getSelectFolderCheckbox (String foldername) { return driver.FindElementByXPath("//*[@id='folderTree']//ul[@class='jstree-children']//a[contains(.,'"+foldername+"')]//i[1]"); }
    public Element getPDF_SelectTagsButton() {return driver.FindElementById("btnPDFPHSelectTags_0");}
    public Element getPDF_ConfidentialTag() {return driver.FindElementByXPath("//div[@id='tagTreePDFComponent']//a[text()='Confidential']");}
    public Element getPDF_ForeignLanguageTag() {return driver.FindElementByXPath("//div[@id='tagTreePDFComponent']//a[text()='Foreign_Language']");}
    public Element getPDF_TagSelectButton() {return driver.FindElementByCssSelector(".btn.btn-primary.btn-sm.submitSelectionPDF");}
    public Element getFolderRadioButton(){ return driver.FindElementByXPath(".//*[@id='rdbFolders']/following-sibling::i"); }
    public Element getTagsRadioButton(){ return driver.FindElementByXPath(".//*[@id='rdbTags']/following-sibling::i"); }
    public Element getPDF_NoMatchingTagsWarning() {return driver.FindElementByCssSelector("[class=\"bigBox animated fadeIn fast\"] p");}
    
    //09252020
    public Element getFolderDropBox(){ return driver.FindElementByXPath("//*[@id='-1_anchor']"); }
    public ElementCollection getCheckedFolder(){ return driver.FindElementsByXPath("//*[@class='jstree-anchor jstree-clicked']"); }
    public Element getTotalDocProduction(){ return driver.FindElementById("TotalDocumentsCount");}
    public Element AnyCheckBoxClicked(){ return driver.FindElementByXPath("//*[@class='jstree-anchor jstree-clicked']");}
    public ElementCollection getAllDefaultFolderCheckboxes() {return driver.FindElementsByXPath("//i[@class='jstree-icon jstree-checkbox']");}
    
    
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
    public Element getPriveldge_TagTree(String tag){ return driver.FindElementByXPath("(//*[@id='tagTreeTIFFComponent']//a[contains(text(),'"+tag+"')])[2]"); }
    public Element getPriveldge_PDFTagTree(String tag){ return driver.FindElementByXPath("(//*[@id='tagTreePDFComponent']//a[contains(text(),'"+tag+"')])[2]"); }
    public Element getPriveldge_TagTree_SelectButton(){ return driver.FindElementByXPath("//*[@class='btn btn-primary btn-sm submitSelectionTIFF']"); }
    public Element getPriveldge_PDFTagTree_SelectButton(){ return driver.FindElementByXPath("//*[@class='btn btn-primary btn-sm submitSelectionPDF']"); }
    public Element getPriveldge_TextArea(){ return driver.FindElementByXPath("//textarea[@class='TIFFPrevDocPlaceHolderText']/preceding-sibling::div"); }
    public Element getPriveldge_PDFTextArea(){ return driver.FindElementByXPath("//textarea[@class='PDFPrevDocPlaceHolderText']/preceding-sibling::div"); }
    public Element getPrivilegedPlaceholderDocsToggle() { return driver.FindElementByCssSelector("#chkIsPrivilegedPlaceholderEnabled + input + i"); }
    public Element getGuardSelectPrevTagsButton() { return driver.FindElementByCssSelector("#btnGuardSelectPrevTags"); }
    public Element getGuardTreeTagCheckbox (String tag) {return driver.FindElementByCssSelector("#tagGuardTreeComponent ul.jstree-children [data-content=\'"+tag+"\'] i.jstree-checkbox"); }
    public Element getTagGuardTreeSaveButton() {return driver.FindElementByCssSelector("#btnSave"); }
    //public Element getGuardTagTextArea() { return driver.FindElementByCssSelector("#txtGuardPrevTagPlaceHolder"); }
    public Element getGuardTagTextArea() { return driver.FindElementByCssSelector(".redactor-editor p"); }
    public Element getPrivMarkCompleteButton() {return driver.FindElementByCssSelector("#AlwayShowButton"); }

    //addition on 23/04
    public Element getProdExportSetRadioButton(){ return driver.FindElementByXPath(".//*[@id='productionSet']//input[@id='IsExportSet']/following-sibling::i"); }
    public Element getProdExport_SaveButton(){ return driver.FindElementByXPath("//button[contains(.,'Save')]"); }
    public Element getProdExport_ProductionSets(){ return driver.FindElementById("ProductionSets"); }
    public Element getProdExport_AddaNewExportSet(){ return driver.FindElementByXPath(".//*[@id='cardGrid']//a[contains(.,'Add a New Export')]"); }
    public Element getProdExport_Priorprodtoggle(){ return driver.FindElementByXPath("//*[@id='ProductionListDiv']/label[2]/i"); }
    public Element getProdExport_SelectProductionSet(){ return driver.FindElementById("ProductionSetLst"); }
    public Element getProd_BatesRange(){ return driver.FindElementById("lblGeneratedBatesRange"); }
    public ElementCollection getProdBatesRange() {return driver.FindElementsById("lblGeneratedBatesRange");}
    public Element getPreviewprod(){ return driver.FindElementById("btnPreview"); }
    public Element getNative_AdvToggle(){ return driver.FindElementByXPath("//*[@id='NativeContainer']//div[@class='advanced-dd-toggle']"); }   
    public Element getProdStateFilter(){ return driver.FindElementById("productionStateFilter"); }   
    public Element getNativeContainer() { return driver.FindElementById("NativeContainer"); }
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
    public Element getTIFF_SelectRedtags(){ return driver.FindElementByXPath("//*[@id='RedactionTagsTree']//a[contains(text(),'R2')]"); }   
    public Element getTIFF_SelectRedtags_SelectButton(){ return driver.FindElementByXPath("//*[@id='myModal']//button[@title='Select']"); }   
    public Element getTIFF_Red_Placeholdertext(){ return driver.FindElementByXPath("//*[@id='divRedaction']//div[@class='redactor-editor']/p"); }   
    public Element getTIFF_SelectRed_Radiobutton(){ return driver.FindElementByXPath("//*[@id='chkTIFFSpecifytRedactions']/following-sibling::i"); }   
    public Element getPDF_SelectRed_Radiobutton(){ return driver.FindElementByXPath("//*[@id='chkPDFSPecifytRedactions']/following-sibling::i"); }   
    public Element getPDFSelectRedactionsTagTree(String tag) {return driver.FindElementByXPath(String.format("(//*[@id='PDFRedactiontreeFolder']//a[contains(text(), '%s')]/i)[1]",tag));}
    public Element getTIFFSelectRedactionsTagTree(String tag) {return driver.FindElementByXPath(String.format("(//*[@id='TIFFRedactiontreeFolder']//a[contains(text(), '%s')]/i)[1]",tag));}

    public Element getDoc_Count(){ return driver.FindElementByXPath("//*[@id='frmProductionConfirmation']//div[@class='drk-gray-widget']/span[1]"); }   
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
    public Element getMarkIncompleteLink() {return driver.FindElementByXPath("//a[text()='Mark Incomplete']");}
	public Element getNextButton() {return driver.FindElementByXPath("//button[text()='Next']");}
	public Element getprod_ActionButton(){ return driver.FindElementByXPath("(//*[@class='fa fa-lg fa-gear'])[1]"); }
	public Element getprod_Action_SaveTemplate(){ return driver.FindElementByXPath("//*[@id='pName']//a[contains(.,'Save as Template')]"); }
	public Element getProductionTileStatusTypeText() {return driver.FindElementByXPath("(//div[@class = 'col-md-12 font-xs']/strong)[1]");}
	public Element getprod_Templatetext(){ return driver.FindElementById("templatesettxt"); }
	public Element getprod_LoadTemplate(){ return driver.FindElementById("ddlTemplate"); }
	public ElementCollection getprod_LoadTemplateOptions(){ return driver.FindElementsByXPath("//*[@prodtype=\"PRODUCTION\"]"); }
	public Element getTechissue_toggle(){ return driver.FindElementByXPath("//*[@id='chkEnabledforExceptionDocs']/following-sibling::i"); }
	public Element getTechissue_SelectTagButton(){ return driver.FindElementById("btnSelectTechIssueTags"); }
    public Element getTechissue_TextArea(){ return driver.FindElementByXPath("//textarea[@class='TIFFTechIssueDocPlaceHolderText']/preceding-sibling::div"); }
    
   
    //added by Narendra
    public ElementCollection getFilterOptions(){ return driver.FindElementsByXPath("//div[@class='col-md-9']//select//option"); }
    public Element getFilter(int i){ return driver.FindElementByXPath("//div[@class='col-md-5']//li["+i+"]//a[1]/label/input"); }
    public Element getFilterByButton(){ return driver.FindElementByXPath(".//*[@id='cardGrid']/div[1]//button[@class='multiselect dropdown-toggle btn']"); }
    public Element getFilterByDRAFT(){ return driver.FindElementByXPath(".//*[@class='multiselect-container dropdown-menu']//label/input[@value='DRAFT']"); }
    public Element getFilterByINPROGRESS(){ return driver.FindElementByXPath(".//*[@class='multiselect-container dropdown-menu']//label/input[@value='INPROGRESS']"); }
    public Element getFilterByFAILED(){ return driver.FindElementByXPath(".//*[@class='multiselect-container dropdown-menu']//label/input[@value='FAILED']"); }
    public Element getFilterByCOMPLETED(){ return driver.FindElementByXPath(".//*[@class='multiselect-container dropdown-menu']//label/input[@value='COMPLETED']"); }
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
    public Element getTemplateNameSaveButton(){ return driver.FindElementByXPath("/html/body/div[8]/div[3]/div/button[2]"); }
    public Element getCustomTemplateName(String tempName){ return driver.FindElementByXPath("//table[@id='customTemplatesDatatable']//tr//td[1][contains(text(),'"+tempName+"')]"); }
    public Element getSave(){ return driver.FindElementByXPath("//button[@class='btn-primary']"); }
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
    public Element getUnlock(){ return driver.FindElementByXPath("//div[@class='dropdown pull-right actionBtn font-xs open']//a[contains(text(),'Unlock')]"); }
    public Element getDelete(){ return driver.FindElementByXPath("//div[@class='dropdown pull-right actionBtn font-xs open']//a[contains(text(),'Delete')]"); }
    public Element getTileDelete(){ return driver.FindElementByXPath("//div[@class='dropdown pull-right actionBtn font-xs open']//a[contains(text(),'Delete')]"); }
    public Element getAction(){ return driver.FindElementByXPath(" //span[@class='fa fa-chevron-down']"); }
    public Element getLockIcon(){ return driver.FindElementByXPath("//i[@class='fa fa-lock']"); }
    public Element getOpenWizard(){ return driver.FindElementByXPath("//div[@class='dropdown pull-right actionBtn font-xs open']//a[contains(text(),'Open In Wizard')]"); }
    public Element getLoadTemplate(){ return driver.FindElementById("ddlTemplate"); }
    public Element getGridDelete(){ return driver.FindElementById("Delete"); }    
    public Element getPRODUCTIONSSETName(){ return driver.FindElementByXPath("//div[@class='col-md-12 productionSummary']//span[@class='font-lg']"); }
    public Element getProductionListGridViewTable(){ return driver.FindElementByXPath("//table[@id='ProductionListGridViewTable']//tbody//tr[1]"); }
    public Element getPRODUCTIONSEXPORTSTabs(){ return driver.FindElementById("ui-id-1"); }
    public Element getadvance(){ return driver.FindElementByXPath("//div[@class='panel-body']//div[@class='col-md-12']//i[@class='fa fa-chevron-right']"); }
    public Element getGenerateLoadFile(){ return driver.FindElementByXPath("//div[@class='panel-body']//div[@class='col-md-12']//div[@class='col-md-4']//i[@class='pull-right']"); }
    public Element getSlipSheets(){ return driver.FindElementByXPath("//div[@class='form-group col-md-12 wrapperNew no-padding']//i[@class='pull-left']"); }
    public Element getAvailableFields(int i){ return driver.FindElementByXPath("//ul[@class='nav nav-tabs tab-style']//li["+i+"]//a"); }
    public Element getTIFF_OpenBrandingInsertMetadataFieldClick(){ return driver.FindElementByXPath("//*[@id=\"divLeftHeaderBranding\"]/div[2]/div[2]/label/a"); }	
    public Element getTIFF_InsertMetadataFieldClick(){ return driver.FindElementByXPath("//*[@id='selectedMetadataField']"); }	
    public ElementCollection getTIFF_BrandingInsertMetadataFieldOptions(){ return driver.FindElementsByXPath("//*[@id='selectedMetadataField']/option"); }	
    public Element getMetaDataInsertButton() {return driver.FindElementByCssSelector("#MetadataPopup > footer > input");}	
    public ElementCollection getPDF_CalculatedList() { return driver.FindElementsByXPath("//ul[@id='pdfCalculatedList']//strong"); }
    //added by shilpi on 08/17
    public Element getCopyPath() { return driver.FindElementByXPath("//a[@title='Copy Path']"); }
    public Element getDocumentGeneratetext() { return driver.FindElementByXPath("//span[contains(text(),'Documents Generated')]"); }
    public Element getQC_backbutton() { return driver.FindElementByXPath("//a[contains(text(),'< Back')]"); }
    public Element getQC_Download() { return driver.FindElementByXPath("//a[text()='Download']"); }
    public Element getQC_Downloadbutton_allfiles() { return driver.FindElementByXPath("(//a[@title='Download All files'])[1]"); }
    
    public Element getArchiveComponentTab() { return driver.FindElementByXPath("//a[@href='#ArchiveContainer']");}
    public Element getArchiveComponentType() { return driver.FindElementByCssSelector("#ArchiveContainer #lstArchiveType option");}	

    public Element getMP3Tab(){ return driver.FindElementByXPath("//a[@href='#MP3FilesContainer']"); }
    //public Element getMP3ChkBox(){ return driver.FindElementByXPath("//input[@name='chkIsMP3Selected']/following-sibling::i"); }
    public Element getMP3ChkBox(){ return driver.FindElementByCssSelector("#advanced-production-accordion > div:nth-child(1) > div:nth-child(1) > h4:nth-child(1) > label:nth-child(1) > i:nth-child(3)");}
    // Production Home Page
    public Element getProductionSetDropdown() { return driver.FindElementByCssSelector("[value='2063']"); }
    public Element getExportSetDropdown() { return driver.FindElementByCssSelector("[value='2064']"); }
    public Element getAddNewProductionButton(){ return driver.FindElementByXPath("//a[text()='Add a New Production']"); }
    public Element getAddNewExportButton(){ return driver.FindElementByXPath("//a[text()='Add a New Export']"); }
    public Element getProductionHomePageTitle(){ return driver.FindElementByClassName("page-title");}	
    public Element getProductionHomePageCurrentlySelectedProductionSet(){return driver.FindElementByCssSelector("#ProductionSetInfo>span.font-lg");}	
    public ElementCollection getProductionSetsOptions(){return driver.FindElementsByCssSelector("#ProductionSets>option");}	
    public ElementCollection getProductionsLastModifiedUser(){ return driver.FindElementsByClassName("prod-lastMod");}	
    public ElementCollection getProductionLastModifiedData(){return driver.FindElementsByXPath("//span[@class='prod-lastMod']/..");}	
    public Element getProductionLastModifiedDataByName(String name){return driver.FindElementByXPath(String.format("//a[@class='prod-Title'][contains(.,'%s')]/../..//span[@class='prod-lastMod']/..", name));}	
    public ElementCollection getProductionTileNames(){ return driver.FindElementsByClassName("prod-Title");}
    
    // Basic Info
    public Element getBasicInfoTemplate(String value) { return driver.FindElementByCssSelector(String.format("#ddlTemplate [label='Custom Templates']%s",value)); }
    public Element getBasicInfoPriorProd(String value) { return driver.FindElementByCssSelector(String.format("#ProductionSetLst%s",value)); }
    public Element getBasicInfoCompleteButton() { return driver.FindElementById("BasicInfoMarkComplete"); }
    public Element getBasicInfoNextButton() { return driver.FindElementById("BasicInfoNext"); }
    
    // Production Components
    public Element getProductionComponentTitle() { return driver.FindElementByCssSelector("[data-original-title='Production Components']"); }
    public Element getProductionAdvanced() { return driver.FindElementByCssSelector(".advanced-production-toggle"); }
    
    // Production Components - Native
    public Element getNativeAdvanced() { return driver.FindElementByCssSelector("#NativeContainer  div.advanced-dd-toggle"); }
    public Element getNativeDescription() { return driver.FindElementByCssSelector("#NativeContainer p.blue-text strong"); }
    public Element getNativeAdvancedHelp() { return driver.FindElementByCssSelector("[data-original-title='Family of Redacted and Privileged Documents']"); }
    public Element getNativeAdvancedParentsRadio() { return driver.FindElementByCssSelector("#rdbExcludeNativesofparent + span"); }
    public Element getNativeAdvancedFamilyRadio() { return driver.FindElementByCssSelector("#rdbExcludeEntireFamily + span"); }
    public Element getNativeAdvancedLST() { return driver.FindElementByCssSelector("#NativeContainer  div.advanced-content [name='ProduceLoadFile'] + i + strong span"); }
    public Element getNativeAdvancedLSTHelp() { return driver.FindElementByCssSelector("#NativeContainer [data-original-title='Generate Load File']"); }
    public String nativeBlueText = "To produce specific docs natively, please select file types and/or tags below. In addition, to " +
            "export placeholders for these docs, configure placeholder in the TIFF or PDF section for the same " +
            "selected file types and/or tags.";
    public String blueColor = "#4570BF";

    public Element getNativeAdvancedLSTToggle(){return driver.FindElementByCssSelector("#NativeContainer  div.advanced-content [name='ProduceLoadFile'] + i ");}
    public Element getNativeAdvancedExcludeNativeOfParentRadio(){return driver.FindElementById("rdbExcludeNativesofparent");}
    public ElementCollection getNativeSelectFileTypeOrTagsTableCheckboxes(){return driver.FindElementsByCssSelector("#tblNativeFileGroup .clsNativeFileLst");}

    
    // Production Components - TIFF
    public Element getTIFFComponentEnableNativelyProducedDocuments() {return driver.FindElementByCssSelector(".add-tiff-img-logic");}
    public Element getTIFFComponentNativelyProducedDocumentsType(int option) {return driver.FindElementByCssSelector("[class=\"custom-scroll tiff-multi-select\"] [value=\"" + option + "\"]");}
    public Element getTIFFComponenetNativelyProducedDocumentPlaceHolder() {return driver.FindElementByCssSelector("[class=\"form-group col-md-12 img-logic-group tiff-placeholderDiv\"] [class=\"col-md-5 red box\"] [class=\"redactor-box\"] p");}
    public Element getTIFFAdvanced() { return driver.FindElementByCssSelector("#TIFFContainer  div.advanced-dd-toggle"); }
    public Element getTIFFMultiRadio() { return driver.FindElementByCssSelector("#TIFFContainer #CommonTIFFSettings_PageType[value='1']"); }
    public Element getTIFFSingleRadio() { return driver.FindElementByCssSelector("#TIFFContainer #CommonTIFFSettings_PageType[value='0']"); }
    public Element getTIFFRotateDropdown() { return driver.FindElementByCssSelector("#TIFFContainer #dldPageRotatePreference"); }
    public Element getTIFFLetterRadio() { return driver.FindElementByCssSelector("#TIFFContainer #rbdTIFFPageFormatLetter"); }
    public Element getTIFFA4Radio() { return driver.FindElementByCssSelector("#TIFFContainer #rbdTIFFPageFormatA4"); }

    public Element getTIFFLetterRadioCheck() { return driver.FindElementById("rbdTIFFPageFormatLetter"); }
    public Element getTIFFA4RadioCheck() { return driver.FindElementById("rbdTIFFPageFormatA4"); }

    public Element getTIFFColorToggle() { return driver.FindElementByCssSelector("#TIFFContainer [name='CommonTIFFSettings.PreseveColor'] + i"); }
    public Element getTIFFBlankRemovalToggle() { return driver.FindElementByCssSelector("#TIFFContainer #chkTIFFBlankPageRemove + input + i"); }
    public Element getTIFFTiffToggle() { return driver.FindElementByCssSelector("#TIFFContainer #chkShouldSkipTIFFGeneration + input + i"); }
    public Element getTIFFBrandingTagsLink() { return driver.FindElementByCssSelector("#TIFFContainer #divLeftHeaderBranding a.add-logic"); }
    public Element getTIFFBrandingTextField() { return driver.FindElementByCssSelector("#TIFFContainer #divLeftHeaderBranding .redactor-editor"); }
    public Element getTIFFPlaceholderPrivilegedToggle() { return driver.FindElementByCssSelector("#TIFFContainer #chkEnabledforPrivDocs + input + i"); }
    public Element getTIFFPlaceholderPriviledgedToggleActive() { return driver.FindElementByCssSelector("#TIFFContainer #chkEnabledforPrivDocs.activeC + input + i"); }
    public Element getTIFFPlaceholderTechIssueToggle() { return driver.FindElementByCssSelector("#TIFFContainer #chkEnabledforExceptionDocs + input + i"); }
    public Element getTIFFPlaceholderPrivilegedTagsButton() { return driver.FindElementByCssSelector("#TIFFContainer #btnSelectPrevTags"); }
    public Element getTIFFPlaceholderPrivilegedTextField() { return driver.FindElementByCssSelector("#TIFFContainer div[placeholder='Enter placeholder text for the privileged docs']"); }
    public Element getTIFFPlaceholderTechTagsButton() { return driver.FindElementByCssSelector("#TIFFContainer #btnSelectTechIssueTags"); }
    //public Element getTIFFPlaceholderTechTextField() { return driver.FindElementByCssSelector("#TIFFContainer div[placeholder='Enter placeholder text for the Tech Issue docs']"); }
    public Element getTIFFPlaceholderTechTextField() { return driver.FindElementByCssSelector(".tiff-img-logic > div:nth-child(3) > div:nth-child(2) > fieldset:nth-child(3) > div:nth-child(1) > div:nth-child(1)"); }
    //public Element getTIFFPlaceholderTechMetadataLink() { return driver.FindElementByCssSelector("#TIFFContainer .tiff-img-logic > div:nth-of-type(2) [title='Insert Metadata Field']"); }
    public Element getTIFFPlaceholderTechMetadataLink() { return driver.FindElementByCssSelector(".tiff-img-logic > div:nth-child(3) > div:nth-child(2) > div:nth-child(4) > label:nth-child(1) > a:nth-child(1)"); }
    public Element getTIFFPlaceHolderBlankNativelyProducedDocumentsWarning() {return driver.FindElementByCssSelector("[class=\"bigBox animated fadeIn fast\"] p");}

    public Element getTIFFPlaceholderNative() { return driver.FindElementByCssSelector("#TIFFContainer .tiff-img-logic .add-tiff-img-logic"); }
    public Element getTIFFBurnRedactionToggle() { return driver.FindElementByCssSelector("#TIFFContainer #chkBurnRedactions + input + i"); }
    public Element getTIFFSlipSheetsToggle() { return driver.FindElementByCssSelector("#TIFFContainer #chkIsTIFFSlipSheetEnabled + input + i"); }
    public Element getTIFFAdvancedRemovedExcel() { return driver.FindElementByCssSelector("#TIFFContainer [name='TIFFOnlyPreviewPages'] + i + strong"); }
    public Element getPDFPlaceholderPrivDocsField() {return driver.FindElementByCssSelector("#PDFContainer div[placeholder='Enter placeholder text for the privileged docs']");}
    public Element getTIFFSlipSheetsFieldTabs(String tabName){ return driver.FindElementByXPath(String.format("//*[@id='TIFFContainer']//span[contains(text(),'%s')]", tabName));}
    public ElementCollection getTIFFSlipSheetsContainerLabels() { return driver.FindElementsByCssSelector(".SlipSheetslistContainer #tiffMetadataList li label");}

    // Production Components - Text
    public Element getTextAdvanced() { return driver.FindElementByCssSelector("#TextContainer  div.advanced-dd-toggle"); }
    public Element getTextDescription() { return driver.FindElementByCssSelector("#TextContainer p"); }
    public Element getTextRemovedTiff() { return driver.FindElementByCssSelector("#TextContainer div.advanced-content div:nth-child(2)"); }
    public String textDescriptionChange = "Redacted documents are automatically OCRed to export the text. Original extracted text is " +
            "exported for natively produced documents (file based placeholdering). For exception and " +
            "privileged placeholdered docs, the placeholder text is exported.";
    
    // Production Components - PDF
    public Element getPDFAdvanced() { return driver.FindElementByCssSelector("#PDFContainer  div.advanced-dd-toggle"); }
    public Element getPDFPlaceholderTechIssueToggle() { return driver.FindElementByCssSelector("#PDFContainer #chkPDFExceptionDocs + input + i"); }
    public Element getPDFPlaceholderTechTagsButton() { return driver.FindElementByCssSelector("#PDFContainer #btnSelectPDFTechIssueTags"); }
    public Element getPDFPlaceholderTechTextField() { return driver.FindElementByCssSelector("#PDFContainer div[placeholder='Enter placeholder text for the Tech Issue docs']"); }
    public Element getPDFPlaceholderTechMetadataLink() { return driver.FindElementByCssSelector("#PDFContainer .tiff-img-logic > div:nth-of-type(2) [title='Insert Metadata Field']"); }
    public Element getPDFAdvancedRemovedExcel() { return driver.FindElementByCssSelector("#PDFContainer [name='TIFFOnlyPreviewPages'] + i + strong"); }
    public Element getPDFComponentNativelyProducedDocuments() {return driver.FindElementByCssSelector(".add-pdf-img-logic");}
    public Element getPDFFirstPageElement() {return driver.FindElementByCssSelector("#c3 > div:nth-child(1) > div:nth-child(1) > strong:nth-child(1)");}
    public Element getPDFMultiRadio() {return driver.FindElementByCssSelector("#PDFContainer #CommonPDFSettings_PageType[value='1']");}
    public Element getPDFSingleRadio() {return driver.FindElementByCssSelector("#PDFContainer #CommonPDFSettings_PageType[value='0']");}
    public Element getPDFLetterRadio() { return driver.FindElementByCssSelector("#PDFContainer #rbdPDFPageFormatLetter"); }
    public Element getPDFA4Radio() { return driver.FindElementByCssSelector("#PDFContainer #rbdPDFPageFormatA4"); }
    public Element getPDFBlankRemovalToggle() { return driver.FindElementByCssSelector("#PDFContainer #chkPDFBlankPageRemove + input + i"); }
    public Element getPDFColorToggle() { return driver.FindElementByCssSelector("#PDFContainer [name='CommonPDFSettings.PreseveColor'] + i"); }
    public Element getPDFSkipPDFGenerationToggle() { return driver.FindElementByCssSelector("#PDFContainer #chkShouldSkipPDFGeneration + input + i"); }
    public Element getPDFRotateDropdown() { return driver.FindElementByCssSelector("#PDFContainer #dldPDFPageRotatePreference"); }
    public Element getPDFBrandingLocation() {return driver.FindElementById("divPDFBrandingLocation");}
    public Element getPDFBrandingText() {return driver.FindElementByCssSelector("#divPDFLeftHeaderBranding > div:nth-child(1) > div:nth-child(1) > label:nth-child(1)");}
    public Element getPDFSpecifyDefaultBranding() {return driver.FindElementByCssSelector("#divPDFLeftHeaderBranding > div:nth-child(1) > div:nth-child(1) > div");}
    public Element getPDFMetadataField() {return driver.FindElementByCssSelector("[id='divPDFLeftHeaderBranding'] [id='LaunchPDFeditor_0']");}
    public Element gePDFSpecificBrandingBySelectionTagsLink() {return driver.FindElementByCssSelector("#divPDFLeftHeaderBranding > div:nth-child(3) > div:nth-child(1) > label:nth-child(1)");}
    public Element getPDFRightHeaderBranding(){ return driver.FindElementById("PDFRightHeaderBranding"); }
    public Element getPDFLeftHeaderBranding(){ return driver.FindElementById("PDFLeftHeaderBranding"); }
    public Element getPDFCenterFooterBranding(){ return driver.FindElementById("PDFCenterFooterBranding"); }
    public Element getPDFLeftFooterBranding(){ return driver.FindElementById("PDFLeftFooterBranding"); }
    public Element getPDFRightFooterBranding(){ return driver.FindElementById("PDFRightFooterBranding"); }
    public Element getPDFRectangleMiddleText() {return driver.FindElementByCssSelector("div[id='divPDFBrandingLocation'] div[class='title1']");}
    public Element getPDFDefaultBrandingRectangleText() {return driver.FindElementByCssSelector("#divPDFLeftHeaderBranding > div:nth-child(1) > div:nth-child(2) > fieldset:nth-child(2) > div:nth-child(1) > div:nth-child(1)");}
    public Element getPDFEnableForPrivilegedDocs(){ return driver.FindElementByXPath("//input[@id='chkPDFPrivDocs']/following-sibling::i"); }
    public Element getPDFEnableForPrivDocsToggle(){return driver.FindElementById("chkPDFPrivDocs");}
    public Element getPDFPriveldgeSelectTagButton(){ return driver.FindElementById("btnSelectPDFPrevTags"); }
    public Element getPDFEnableTechIssueDocsToggle(){ return driver.FindElementByXPath("//input[@id='chkPDFExceptionDocs']/following-sibling::i"); }
    public Element getPDFEnableNativelyProducedDocs() { return driver.FindElementByCssSelector("#PDFContainer .pdf-img-logic .add-pdf-img-logic"); }
    public Element getPDFEnableNativelyProducedDocsHelpTip() {return driver.FindElementByCssSelector("#PDFContainer a.helptip[data-original-title='Enable Placeholders by Selecting File Types']");}
    public Element getPDFPlaceholderTextField() { return driver.FindElementByCssSelector(".pdf-img-logic > div:nth-child(3) > div:nth-child(2) > fieldset:nth-child(3) > div:nth-child(1) > div:nth-child(1)"); }
    //public Element getPDFPlaceholderTextField() { return driver.FindElementByXPath("//div[@id='PDFContainer']//div[@class='redactor-editor']"); }
    public Element getPDFPlaceholderMetadataLink() { return driver.FindElementByCssSelector(".pdf-img-logic > div:nth-child(3) > div:nth-child(2) > div:nth-child(4) > label:nth-child(1) > a:nth-child(1)"); }
    public Element getPDFBurnRedactionToggle() { return driver.FindElementByCssSelector("#PDFContainer #chkPDFBurnRedactions + input + i"); }
    public Element getPDFLSTLoadFileToggle() {return driver.FindElementById("chkPDFProduceLoadFile");}
    public Element getPDFSlipSheetsToggle() { return driver.FindElementByCssSelector("#PDFContainer #chkIsPDFSlipSheetEnabled + input + i"); }
    public Element getPDFLSTLoadFileType() {return driver.FindElementById("LoadFileType");}
    public Element getPDFPlaceholderPriviledgedToggleActive() { return driver.FindElementByCssSelector("#PDFContainer #chkPDFPrivDocs.activeC + input + i"); }

    // Production Components - MP3
    public Element getMP3ComponentAdvanced(){ return driver.FindElementByCssSelector("#MP3FilesContainer div.advanced-dd-toggle"); }
    public Element getMP3ComponentRedactionToggle(){ return driver.FindElementByCssSelector("[name='IsBurnRedactionSelected'] + i"); }
    public Element getMP3ComponentRedactionStyle(String value){ return driver.FindElementByCssSelector(String.format("#lstFillerAudio%s",value)); }
    public Element getMP3AdvancedList() { return driver.FindElementByCssSelector("#MP3FilesContainer #chkMP3ProduceLoadFile + input + i"); }
    public String mp3Warning = "For MP3 Files component, you must enable LST load file option or you must specify MP3FilePath in " + 
                               "the DAT, in order to generate a load file for the generated MP3s files.";
    public Element getMP3WarningBox() { return driver.FindElementByCssSelector("#divbigBoxes p"); }
    public Element getMP3_ToggElement() {return driver.FindElementByXPath("//*[@id=\"accordion\"]/div[7]/h2/i");}
    public Element getMP3SelectRedactionsTagTree(String tag) {return driver.FindElementByXPath(String.format("(//*[@id='MP3RedactiontreeFolder']//a[contains(text(), '%s')]/i)[1]",tag));}
    public Element getConfirmCompletePopup() {return driver.FindElementByCssSelector("#divbigBoxes p");}
    public Element getMP3_SelectRed_Radiobutton() {return driver.FindElementByXPath("//*[@id='chkMP3SPecifytRedactions']/following-sibling::i");}
    public Element getMP3_SelectAdvToggle(){return driver.FindElementByXPath("/html/body/div[3]/div/div[2]/div[2]/div/div[4]/form/div/div[8]/div/div[1]/div[2]/div/div/div[2]/div[1]/i");}

    public Element getMP3_SelectRed_RedactionByAnnotation() {return driver.FindElementByXPath("//*[@id='chkMP3RedactionByAnnotation']/following-sibling::i");}
    public Element getMP3_RedactionStyle() {return driver.FindElementByXPath("//*[@id=\"lstFillerAudio\"]");}
    public Element getMP3_RedactionStyle_Beet(){return driver.FindElementByXPath("//*[@id=\"lstFillerAudio\"]/option[2]");}
    public Element getMP3BurnRedactionsCheckboxToggle(){ return driver.FindElementById("chkMP3BurnRedactions"); }
    public Element getMP3DefaultAutomationRedactionCheckbox() {return driver.FindElementByCssSelector("#MP3RedactiontreeFolder a[data-content='Default Automation Redaction']"); }
    public Element getMP3GenerateLoadFileCheckboxToggle(){ return driver.FindElementById("chkMP3ProduceLoadFile"); }

    //Added on 8/21
    public Element getDATAnsiRadioButton() {return driver.FindElementById("rdbANSI");}
    public Element getDATAnsiType() {return driver.FindElementById("lstANSIType");}
    public Element getDATAnsiUnicode() {return driver.FindElementById("rdbUnicode");}
    public Element getDATFieldSeperator() {return driver.FindElementById("lstFieldSeparator");}
    public Element getDATTextQualifier() {return driver.FindElementById("lstTextQualifier");}
    public Element getDATMultiValue() {return driver.FindElementById("lstMultiValue");}
    public Element getDATNewLine() {return driver.FindElementById("lstNewLineSeparator");}
    public Element getDATDateFormat() {return driver.FindElementById("lstDateFormat");}
    public Element getDATRedactionsButton() {return driver.FindElementById("ChkRedacted_0");}
    public Element getDATPrivilegedButton() {return driver.FindElementById("ChkPrev_0");}
    

    //9-21

    public Element changeProjectSelector() {return driver.FindElementById("project-selector");}
    public Element changeProjectSelectorField() {return driver.FindElementByCssSelector("#ddlProject11 > li:nth-child(1) > a:nth-child(1)");}
    public Element productionProjectSelector() {return driver.FindElementByXPath("//a[@title='021320_EG']");}

    public Element getNewProject() { return driver.FindElementById("project-selector");}

    public Element getFieldClassification() {return driver.FindElementById("TY_0");}
    public Element getSourceField() {return driver.FindElementById("SF_0");}
    public Element getDatField() {return driver.FindElementById("DATFL_0");}
    public Element getDefaultAutomationChkBox() {return driver.FindElementByXPath("//*[@id='1031_anchor']/i[1]");}
    public Element getDefaultTagsChkBox() {return driver.FindElementByXPath("//*[@id='26_anchor']/i[1]");}
    public Element getDefaultSecurityGroupChkBox() {return driver.FindElementByXPath("//*[@id='1g_anchor']/i[1]");}
    
    public Element getDocumentCollapseBtn() { return driver.FindElementByXPath("//*[@id=\"2\"]/i");}
    public Element getNumAndSortMarkCompleteBtn() {return driver.FindElementById("NumAndSortMarkComplete");}
    public Element getNumAndSortNextBtn() { return driver.FindElementById("NumAndSortNext");}
    public Element getDocumentMarkCompleteBtn() { return driver.FindElementById("btnDocumentsSelectionMarkComplete");}
    public Element getDocumentNextBtn() { return driver.FindElementById("btnDocumentsSelectionNext");}
    public Element getPrivAddRuleBtn() { return driver.FindElementById("contentmetadata");}
    public Element getPrivRedactionsBtn() { return driver.FindElementById("redactionsHelper");}
    public ElementCollection getPrivTagsBtn() {return driver.FindElementsById("tagsHelper");}
    public Element getPrivDefaultAutomation() { return driver.FindElementByXPath("//*[@id=\"7_anchor\"]/i[1]");}
    public Element getPrivTagDefaultAutomation() {return driver.FindElementByXPath("(//*[@id = 'JSTree']//a[contains(text(), 'Default Automation Tag')]/i)[1]");}
    public Element getPrivInsertQuery() { return driver.FindElementById("insertQueryBtn");}
    public Element getPrivChkForMatching() { return driver.FindElementById("btnDocumentMatch");}
    public Element getTotalMatchedDocuments() {return driver.FindElementById("TotalDocumentsCount");}
    public Element getPrivDocViewBtn() { return driver.FindElementById("btnGoToDocView");}
    public Element getReviewModeText() { return driver.FindElementByCssSelector("#divAssigmnetProgress1 > div > span");}
    public Element getDocListButton() { return driver.FindElementById("btnGoToDocList");}
    public Element getDocListSourceCriteria() {return driver.FindElementByXPath("//a[@href='#c1']");}
    public Element getDocListProductionText() {return driver.FindElementByXPath("//div[@class='panel-body']/p[1]");}
    public Element getDocListBackToSourceButton() {return driver.FindElementByXPath("//a[text()='Back to Source']");} 
    public Element getDocListEntryAmountText() {return driver.FindElementById("dtDocList_info");}
    public Element getDocListTableEntry() {return driver.FindElementByXPath("//table[@id='dtDocList']//tbody");}
    public Element getDocViewTableEntry() {return driver.FindElementByXPath("//table[@id='SearchDataTable']//tbody");}
    public Element getTotalOfDocumentsTable() {return driver.FindElementByXPath("//*[@id='SearchDataTable']/tbody/tr");}
    public ElementCollection getChildDocuments() {return driver.FindElementsByXPath("//table[@id='dtDocList']/tbody/tr");}

    public Element getDocListDropDownCount() { return driver.FindElementById("idPageLength");}
    public Element getDocListDropDownCountMax() { return driver.FindElementByCssSelector("#idPageLength option[value = '500']");}

    public Element getNumDocumentLevelRadioButton() { return driver.FindElementByCssSelector("div.col-md-8:nth-child(3) > label:nth-child(1) > i:nth-child(2)");}
    public Element getNumDocumentLevelRadioButtonCheck() {return driver.FindElementByXPath("//*[@id='rdbDocumentLevel']/../i");} 
    public Element getNumPageLevelRadioButtonCheck() {return driver.FindElementById("rdbPageLevel");} 
    public Element getNumPageLevelRadioButton() {return driver.FindElementByCssSelector(".Number > div:nth-child(1) > div:nth-child(2) > div:nth-child(2) > label:nth-child(1) > i:nth-child(2)");}
    public Element getNumBatesRadioButton() {return driver.FindElementByCssSelector(".productionOnly > label:nth-child(1) > i:nth-child(2)");}
    public Element getNumBatesRadioButtonCheck() {return driver.FindElementById("rdbSpecifyNumber");}
    public Element getNumUseMetaFieldButton() {return driver.FindElementByXPath("//*[@id='rdbUserMetadata']/../i");}
    public Element getNumUseMetaFieldButtonCheck() { return driver.FindElementById("rdbUserMetadata");}
    public Element getNumSortMetaRadioButton() {return driver.FindElementById("div.box:nth-child(2) > div:nth-child(1) > label:nth-child(1) > i:nth-child(2)");}
    public Element getNumSortMetaRadioButtonCheck() {return driver.FindElementById("rdbSortByField");}
    public Element getNumNextBatesLink() {return driver.FindElementByCssSelector("label.col-md-12 > a:nth-child(1)");}
    public Element getNumSubBatesNum() {return driver.FindElementById("txtSubbatesNumber");}
    public Element getNumSubBatesMin() {return driver.FindElementById("txtSubbatesNumberMinNoLength");}
    public Element getNumMetaDataCustodiansTab() {return driver.FindElementById("lstNumberingMetaData");}
    public Element getNumMetaDataPrefix() {return driver.FindElementById("txtUserMetadataFieldPrefix");}
    public Element getNumMetaDataSuffix() {return driver.FindElementById("txtUserMetadataFieldSuffix");}
     
    public Element getDocSelectSearchRadioButton() {return driver.FindElementByXPath(".//*[@id='rdbSearches']/following-sibling::i");}
    public ElementCollection getDocListParentChildDetailsRowButton() {return driver.FindElementsByCssSelector("#dtDocList tr td[class = ' details-control']");}

    
    public Element getTIFFFirstPageElement() {return driver.FindElementByCssSelector("#c7 > div:nth-child(1) > div:nth-child(1) > label:nth-child(1)");}
    public Element getTIFFRectangleMiddleText() {return driver.FindElementByCssSelector("div[class='title1']");} 
    public Element getTIFFBrandingLocation() {return driver.FindElementById("divBrandingLocation");} 
    public Element getTIFFBrandingText() {return driver.FindElementByCssSelector("#divLeftHeaderBranding > div:nth-child(1) > div:nth-child(1) > label:nth-child(1)");}
    public Element getTIFFSpecifyDefaultBranding() {return driver.FindElementByCssSelector("#divLeftHeaderBranding > div:nth-child(1) > div:nth-child(1) > div:nth-child(2)");}
    public Element getTIFFMetadataField() {return driver.FindElementById("Launcheditor_0");}
    public Element getTIFFDefaultBrandingRectangleText() {return driver.FindElementByCssSelector("#divLeftHeaderBranding > div:nth-child(1) > div:nth-child(2) > fieldset:nth-child(2) > div:nth-child(1) > div:nth-child(1)");}
    public Element getTIFFNativeQuestionMarkLink() {return driver.FindElementByClassName(".tiff-img-logic > div:nth-child(5) > label:nth-child(1) > a:nth-child(2)");}
    public Element getTIFFLSTLoadFileToggle() {return driver.FindElementById("chkTIFFProduceLoadFile");}
    public Element getTIFFLSTLoadFileType() {return driver.FindElementById("LoadFileType");}
    public Element getTiFFPrivToggleButton() {return driver.FindElementById("chkEnabledforPrivDocs");}
    public Element getProductionGridViewProductionNameColumnHeader() {return driver.FindElementByCssSelector("th[aria-label='Production Name: activate to sort column ascending']");}	
    public Element getProductionGridViewActionDropDown() {return driver.FindElementById("DropDownAction");}
    public Element getProductionGridViewActionOpenWizard() {return driver.FindElementById("OpenInWizard");}
    public Element getProductionGridViewActionDelete() {return driver.FindElementById("Delete");}
    public Element getProductionGridViewActionLock() {return driver.FindElementById("Lock");}
    public Element getProductionGridViewActionUnLock() {return driver.FindElementById("Unlock");}
    public Element getProductionGridViewActionSaveTemplate() {return driver.FindElementByCssSelector("ul.dropdown-menu:nth-child(3) > li:nth-child(5) > a:nth-child(1)");}
    public Element getProductionGridViewActionAddDoc() {return driver.FindElementById("AddDocuments");}
    public Element getProductionGridViewActionRemoveDoc() {return driver.FindElementById("RemoveDocuments");}
    public Element getProductionSectionPageTitle() {return driver.FindElementByXPath("//h2[@class = 'col-md-8']");}
    public Element getProductionMarkIncompleteBtnByPage(String pageName) {return driver.FindElementById(String.format("btn%sMarkInComplete", pageName));}
    public Element getProductionMarkIncompleteLastBtn(String pageName) {return driver.FindElementById(String.format("%sMarkInComplete", pageName));}
    
    
        
    //Added 10/2/20 
    public Element getManageTemplatesTab() { return driver.FindElementByXPath("//a[text()='MANAGE TEMPLATES']");}
    public Element getDefaultAutomationTemplateView() { return driver.FindElementByXPath("//a[contains(@onclick, 'DefaultAutomationTemplate')]");}
    public Element getSavedTemplate() {return driver.FindElementByXPath("//*[@id=\"viewProduction\"]");}
    public Element getBackLink() {return driver.FindElementByXPath("//a[text()[contains(.,'Back')]]");}
    public Element getBackBtn() {return driver.FindElementByXPath("//*[@id=\"divLoadView\"]/form/div[1]/div/a");}
    public Element getGenarateTitle() { return driver.FindElementByCssSelector("[data-original-title='Production Components']"); }
    public Element getGenerateButton() {return driver.FindElementByXPath("//button[text()='Generate']");}
    public Element goToProductionHomePage() {return driver.FindElementByCssSelector("a[id='8']");}
    public Element getGenerateInProgressButton() {return driver.FindElementByXPath("//button[text()='in progress']");}
    public Element getGenerateInProgressStatus() {return driver.FindElementByXPath("//label[text()='IN PROGRESS']");}
    public Element getGeneratePreGenStatus() {return driver.FindElementByXPath("//label[text()='Pre generation check in progress']");}
    public WebElement getGeneratePostGenStatus() {return driver.FindElementsByCssSelector(".col-md-2.labelAlign").FindWebElements().get(2);}    
    public Element getGenerateProductionName() {return driver.FindElementByCssSelector(".col-md-4.labelAlign");}   

    public Element getProductionGear() {return driver.FindElementByCssSelector("[class=\"fa fa-lg fa-gear\"]");}
    public Element getProductionDeleteButton() {return driver.FindElementByCssSelector("#pName > div.dropdown.pull-right.actionBtn.font-xs.open > dl > dt:nth-child(2) > a");}
    public Element getProductionDeleteOkButton() {return driver.FindElementByCssSelector("[class=\"btn btn-default btn-sm botTempo\"]");}
    
    public Element getClickHereToViewNextBatesNumbers() {return driver.FindElementByCssSelector(".radio.col-md-12");}
    public Element getMarkCompleteSuccessfulText() { return driver.FindElementByXPath("//*[text()='Mark Complete successful']"); }
    public Element getClickHereLink() { return driver.FindElementByCssSelector("a[onclick='javascript:NextBatesNumber();']"); }
    public Element getNextBatesNumbersDialog() { return driver.FindElementByCssSelector(".ui-dialog.ui-widget.ui-widget-content.ui-corner-all.ui-front"); }
    public Element getFirstBatesNumberValue() { return driver.FindElementByXPath("//table[@id='dtNextBatesLst']//tr/td"); }
    public Element getSecondBatesNumberValue() { return driver.FindElementByXPath("//table[@id='dtNextBatesLst']//tr[2]/td"); }
    public Element getFirstBatesNumberSelectButton() { return driver.FindElementByXPath("//table[@id='dtNextBatesLst']//tr[1]/td[2]/button"); }
    public Element getSecondBatesNumberSelectButton() { return driver.FindElementByXPath("//table[@id='dtNextBatesLst']//tr[2]/td[2]/button"); }
    public Element getNumAndSortMarkCompleteButton() { return driver.FindElementById("NumAndSortMarkInComplete"); }
    public Element getNumAndSortNextButton() { return driver.FindElementById("NumAndSortNext"); }
    public Element getCurrentCrumbDocumentSelection() { return driver.FindElementByXPath("//li[@class='current-crumb']/span[text()='Document Selection']"); }
    public Element getCurrentCrumbProductionLocation() { return driver.FindElementByXPath("//li[@class='current-crumb']/span[text()='Production Location']"); }
    public Element getCurrentCrumbSummaryAndPreview() { return driver.FindElementByXPath("//li[@class='current-crumb']/span[text()='Summary & Preview']"); }
    public Element getCurrentCrumbGenerate() { return driver.FindElementByXPath("//li[@class='current-crumb']/span[text()='Generate']"); }
    public Element getSecondRootPathOption() { return driver.FindElementByXPath("//select[@id='lstProductionRootPaths']/option[2]"); }
    public Element getGenerationStartedSuccessfullyText() { return driver.FindElementByXPath("//*[text()='Generation Started Successfully']"); }
    public Element getInProgressStatus() { return driver.FindElementByXPath("//label[text()='IN PROGRESS']"); }
    public ElementCollection getRegenerateButtonElCollection() { return driver.FindElementsById("btnProductionReGenerate"); }
    public Element getExportBatesButton() { return driver.FindElementById("btnProductionGeneratedBatesRangeExport"); }
    public ElementCollection getExportBatesButtonVerify() { return driver.FindElementsById("btnProductionGeneratedBatesRangeExport"); }
    public Element getProductionLocationMarkIncompleteButton() { return driver.FindElementById("btnProductionLocationMarkInComplete"); }
    public Element getBulHornNotificationNumber() { return driver.FindElementByXPath("//span[@id='activity']/b"); }
    public Element getProductionsNavLink() { return driver.FindElementByName("Productions"); }
    public Element getRedBullHorn() { return driver.FindElementByCssSelector(".badge.bg-color-red.bounceIn.animated"); }
    public Element getMarkIncompleteButton() { return driver.FindElementByXPath("//a[text()='Mark Incomplete']"); }
    public Element getMarkCompleteButton() { return driver.FindElementByXPath("//a[text()='Mark Complete']"); }
    public ElementCollection getDisabledPrefixInputFieldElCollection() { return driver.FindElementsByXPath("//input[@id='txtBeginningBatesIDPrefix' and @disabled]"); }
    public Element getDisabledPrefixInputField() { return driver.FindElementByXPath("//input[@id='txtBeginningBatesIDPrefix' and @disabled]"); }
    public Element getBatesDialogTitle() { return driver.FindElementByXPath("//span[@id='ui-id-1']/div"); }
    public Element getBatesDialogProductionName() { return driver.FindElementById("producitonName"); }
    public Element getNextBatesNumberColumHeader() { return driver.FindElementById("hdrNextBatesNo"); }
    public Element getActionColumHeader() { return driver.FindElementById("hdrAction"); }
    public Element getSaveButton() { return driver.FindElementByXPath("//*[text()='Save']"); }
    public Element getPrivDocsStatus() { return driver.FindElementByXPath("(//span[@class='text-success'])[1]"); }
    public Element getFieldClassificationDropDown(int index) {return driver.FindElementByXPath(String.format("//*[@id='TY_0']/option[%s]", index));}
    public Element getSecondFieldClassificationDropDown(int index) {return driver.FindElementByXPath(String.format("//*[@id='TY_1']/option[%s]", index));}
    public Element getSourceFieldDropDown(int index) {return driver.FindElementByXPath(String.format("//*[@id='SF_0']/option[%s]", index));}
    public Element getSecondSourceFieldDropDown(int index) {return driver.FindElementByXPath(String.format("//*[@id='SF_1']/option[%s]", index));}
    public Element getDataFieldText(int index) {return driver.FindElementByXPath(String.format("//*[@id='DATFL_%s']", index));} 
    public Element getBasicInfoMarkedCompleteCloseBtn() { return driver.FindElementById("botClose1"); }
    
    public Element getSelectNativeTagsButton() {return driver.FindElementById("btnSelectNativeTags");}
    public Element getNativeSelectedTagList() {return driver.FindElementById("NativeTagsLabel");}

    public Element getProductionTitleName() {return driver.FindElementByXPath("//*[@id='cardCanvas']/ul/li[1]/div[1]/a");}
    public Element getProdductionNameOnQCPage() {return driver.FindElementByXPath("//*[@class='col-md-3']//div[@class='panel-title-container']/h2");}
    public Element getGeneratedDocCountOnQCPage() {return driver.FindElementByXPath("//*[@class='drk-gray-widget']/span[1]");}
    public Element getReviewProductionButton() {return driver.FindElementByXPath("//*[@class='btn btn-primary col-md-12']");}
    public Element getAutomatedQCChecklistText(int index) {return driver.FindElementByXPath(String.format("//*[@id='taskbasic']/tbody/tr[%s]/td[1]", index));}
    public ElementCollection getGeneratePageTitle() {return driver.FindElementsByXPath("//*[@id='frmProductionGenerate']/div/div[1]/div/h2");}
    public Element getConfirmAndCommitProdLink() {return driver.FindElementByXPath("//*[@id='btnProductionConfirmation']/strong");}
    public Element getSelectTagsModal() { return driver.FindElementByXPath("//div[@id='myModal']//div[@class='well no-padding prod ']"); }
    public Element getTIFFPrivilegedTagCheckbox() { return driver.FindElementByXPath("//div[@id='tagTreeTIFFComponent']//a[@data-content='Privileged']"); }
    public Element getTIFFSubmitSelectionButton() { return driver.FindElementByCssSelector(".btn.btn-primary.btn-sm.submitSelectionTIFF"); }
    public Element getDefaultAutomationRedactionCheckbox() { return driver.FindElementByXPath("//div[@id='RedactionTagsTree']//a[@data-content='Default Automation Redaction']"); }
    public Element getDefaultAutomationRedactionSelectionTagsCheckbox() { return driver.FindElementByXPath("//div[@id='tagTreeTIFFComponent']//a[@data-content='Default Automation Redaction']"); }
    public Element getSelectTagsModalBody() { return driver.FindElementByXPath("//div[@id='myModal']//div[@class='widget-body']"); }
    public Element getSelectedTagsLabel() { return driver.FindElementById("PrevTagsLabel"); }
    public Element getPrivilegedDocsPlaceholderTextField() { return driver.FindElementByXPath("//textarea[@id='textPrivDocs']/preceding-sibling::div"); }
    public Element getSpecifyBrandingBySelectingTagLink() { return driver.FindElementByXPath("//div[@id='divLeftHeaderBranding']//a[contains(text(), 'Specify Branding by Selecting Tags')]"); }
    public Element getDefaultBrandingTextField1() { return driver.FindElementByXPath("//textarea[@name='TIFFComponentModelData.CommonTIFFSettings.BrandingSetting.LeftHeaderBranding.BrandingDataList[0].BrandingText']/preceding-sibling::div"); }
    public Element getDefaultBrandingTextField2() { return driver.FindElementByXPath("//textarea[@name='TIFFComponentModelData.CommonTIFFSettings.BrandingSetting.LeftHeaderBranding.BrandingDataList[1].BrandingText']/preceding-sibling::div"); }
    public Element getTIFFRedactionTreeFolderDiv() { return driver.FindElementById("RedactionTagsTree"); }
    public Element getRedactionDefaultAutomationRedactionCheckbox() { return driver.FindElementByXPath("//div[@id='RedactionTagsTree']//li[@id='-1']//a[@data-content='Default Automation Redaction']"); }
    public Element getBrandingSelectTagsButton() { return driver.FindElementById("STAG_1"); }
    public Element getBrandingDefaultAutomationTagCheckbox() { return driver.FindElementByXPath("//div[@id='tagTreeTIFFComponent']//a[@data-content='Default Automation Tag']"); }
    public Element getWarningMessagePopup() { return driver.FindElementById("MsgBoxBack"); }
    public Element getRedactedDocumentsNumber() { return driver.FindElementByXPath("//label[contains(text(), 'Redacted Documents')]/following-sibling::label"); }
    public Element getSummaryAndPreviewHeading() { return driver.FindElementByXPath("//h2[contains(text(), 'Summary and Preview')]"); }
    public Element getMP3FilesCountNumber() { return driver.FindElementByXPath("//label[contains(text(), 'Number of MP3 Files')]/following-sibling::label"); }
    public Element getTotalDocumentsNumber() { return driver.FindElementByXPath("//label[contains(text(), 'Total Documents')]/following-sibling::label"); }
    public ElementCollection getProductionConfirmPopupCloseBtn(int i) {return driver.FindElementsById(String.format("botClose%s",i));}
    public Element getProductionDocumentSelectTagByName(String name) {return driver.FindElementByCssSelector(String.format("#tagTree a[data-content='%s'] i.jstree-checkbox", name));}
    public Element getCounterClockRotate() {return driver.FindElementByXPath("//*[@id='dldPageRotatePreference']/option[3]");}
    public Element getPDFCounterClockRotate() {return driver.FindElementByXPath("//*[@id='dldPDFPageRotatePreference']/option[3]");}
    public Element getPDF_EnableforPrivilegedDocs(){ return driver.FindElementByXPath(".//*[@id='PDFContainer']//input[@name='CommonPDFSettings.PlaceHolderImageSettings.EnabledforPrivDocs']/following-sibling::i"); }
    public ElementCollection getPDFPrivilegeDocsDisabledToggle() { return driver.FindElementsByXPath("//input[@id='chkPDFPrivDocs' and @class='PDF-encryp-check']"); }
    public Element getNumberingAndSortingTitle() {return driver.FindElementByXPath("//*[@id='divLoadView']/form/div[1]/div/h2");}
    public Element getPreGenerationCheckFailedLink() {return driver.FindElementById("ProductionErrorLaunchPopUp");}
    public Element getErrorDataTable() {return driver.FindElementByXPath("//*[@id='GenerateErrorDataTable']/tbody/tr/td[2]"); }
    public Element getCloseModalButton() {return driver.FindElementById("GenerateErrorDetailsPopUpClose");}
    public Element getCustomTemplateViewButton(String templateName) { return driver.FindElementByXPath("//table[@id='customTemplatesDatatable']//td[text()='"+templateName+"']/following-sibling::td/a[text()='View']"); }
    public Element getCustomTemplateWidget() { return driver.FindElementByCssSelector(".ui-dialog.ui-widget.ui-widget-content.ui-corner-all.ui-front.ui-resizable"); }
    public Element getTemplatePrivGuardNextButton() { return driver.FindElementById("TemplateGuardNext"); }
    public ElementCollection getPrivledgedRules() { return driver.FindElementsByCssSelector(".editable.editable-pre-wrapped.editable-click"); }
    public Element getAutomationTemplateWidget() { return driver.FindElementByCssSelector(".ui-dialog.ui-widget.ui-widget-content.ui-corner-all.ui-front.ui-resizable"); }
    public Element getTemplateProductionComponentsPanel() { return driver.FindElementById("accordion"); }
    public Element getTemplateProductionComponentToggle(String prodComponent) { return driver.FindElementByXPath("//form[@id='divProductionComponents']//h4//a[contains(text(), '"+prodComponent+"')]"); }
    
    public Element getTemplateFieldClassificationValue() { return driver.FindElementById("TY_0"); }
    public Element getTemplatePrivRulesList() { return driver.FindElementById("qb-1"); }
    public Element getTemplateSourceFieldValue() { return driver.FindElementById("SF_0"); }
    public Element getTemplateDatFieldValue() { return driver.FindElementById("DATFL_0"); }
    public Element getTemplateTIFFPlaceholderText() { return driver.FindElementByXPath("//div[@id='TIFFContainer']//div[@placeholder='Enter placeholder text for the privileged docs']/p"); }
    public Element getTemplateTIFFPageRotatePreferenceSelectedValue() { return driver.FindElementByXPath("//*[@id='dldPageRotatePreference']/option[@selected='selected']"); }
    public Element getTemplatePDFPlaceholderText() { return driver.FindElementByXPath("//div[@id='PDFContainer']//div[@placeholder='Enter placeholder text for the privileged docs']/p"); }
    public Element getTemplatePDFPageRotatePreferenceSelectedValue() { return driver.FindElementByXPath("//*[@id='dldPDFPageRotatePreference']/option[@selected='selected']"); }
    public Element getTemplateTIFFContiner() { return driver.FindElementById("TIFFContainer"); }
    public Element getTemplateNumAndSortingDiv() { return driver.FindElementById("divNumAndSort"); }
    public Element getTemplateBeginningBatesValue() { return driver.FindElementById("txtBeginningBatesID"); }
    public Element getTemplatePrefixValue() { return driver.FindElementById("txtBeginningBatesIDPrefix"); }
    public Element getTemplateSuffixValue() { return driver.FindElementById("txtBeginningBatesIDSuffix"); }
    public Element getTemplateMinNumValue() { return driver.FindElementById("txtBeginningBatesIDMinNumLength"); }
    public Element getTemplateCloseButton() { return driver.FindElementByCssSelector(".ui-dialog-titlebar-close"); }
    public Element getTemplateTableGridDiv() { return driver.FindElementById("tableGrid"); }
    public Element getTemplateDialogOverlay() { return driver.FindElementByCssSelector(".ui-widget-overlay.ui-front"); }
    public Element getTemplateDialog() { return driver.FindElementById("viewProduction"); }
    public Element getTemplateTitle() { return driver.FindElementById("ui-id-3"); }
    public Element getTIFFPlaceholderTag() { return driver.FindElementById("PrevTagsLabel"); }
    public Element getTIFFBurnRedactionInput() { return driver.FindElementById("chkBurnRedactions"); }
    public Element getPDFPlaceholderTag() { return driver.FindElementById("PDFPrevTagsLabel"); }
    public Element getRedactionTag(String redactionName) { return driver.FindElementByXPath("//div[@id='PDFRedactiontreeFolder']//a[@data-content='"+redactionName+"']"); }
    public Element getDefaultAutomationRedactionTag() { return driver.FindElementById("7_anchor"); }
    public Element getProductionComponentDATCheckbox() { return driver.FindElementById("chkIsDATSelected"); }
    public Element getProductionComponentNativeCheckbox() { return driver.FindElementById("chkIsNativeSelected"); }
    public Element getProductionComponentTIFFCheckbox() { return driver.FindElementById("chkIsTIFFSelected"); }
    public Element getProductionComponentPDFCheckbox() { return driver.FindElementById("chkIsPDFSelected"); }
    public Element getProductionComponentMP3FilesCheckbox() { return driver.FindElementById("chkIsMP3Selected"); }
    public Element getPDFBrandingPlaceholderTextField() { return driver.FindElementByXPath("//div[@id='PDFContainer']//div[@class='redactor-editor redactor-placeholder']"); }
    public Element getProductionComponentAdvanceToggle() { return driver.FindElementByCssSelector(".advanced-production-toggle.col-md-12"); }
    public Element getMP3RedactionsDefaultAutomation() { return driver.FindElementByXPath("//*[@id='MP3RedactiontreeFolder']//a[@id='7_anchor' and @data-content='Default Automation Redaction']"); }    
    public Element getMP3RedactionStyleValue() { return driver.FindElementById("lstFillerAudio"); }
    public Element getMP3BurnRedactions() { return driver.FindElementById("chkMP3BurnRedactions"); }
    public Element getProdSearchMenuButton() {return driver.FindElementById("3");}
    public Element getProdSessionSearchButton() { return driver.FindElementByCssSelector("a[name='Session']");}
    public ElementCollection getProdPrevPageDocSummary() {return driver.FindElementsByCssSelector(".col-md-12 label");}
    public Element getProductionLocComponent(int index) {return driver.FindElementByXPath(String.format("//*[@id=\"frmProductionLocation\"]/div/div[3]/div/div/div/div[1]/div/div[%s]/label[1]",index));}
    public Element getSummaryPageLabels(int index) {return driver.FindElementByCssSelector(String.format("#frmProductionSummary > div > div:nth-child(2) > div > div > div > div.col-md-8 > div:nth-child(%s) > label:nth-child(1)", index));}
    public Element getFieldClassificationDropdown(int index) {return driver.FindElementById(String.format("TY_%s", index));}	
    public Element getSourceFieldDropdown(int index) {return driver.FindElementById(String.format("SF_%s", index));}	
    public Element getDatFieldDropdown(int index) {return driver.FindElementById(String.format("DATFL_%s", index));}	
    public Element MultipleDatForceOccuranceMsg() {return driver.FindElementByCssSelector("#Msg1 > div > span");}	
    public Element MultipleDatForceOccuranceSubMsg() {return driver.FindElementByCssSelector("#Msg1 > div > p");}	
    public Element getContinueButton() {return driver.FindElementByCssSelector("#bot1-Msg1");}	
    public Element getDuplicateDatWarningBox() { return driver.FindElementByCssSelector("#divbigBoxes p"); }	
    public String duplicateDatWarning = "Multiple source fields cannot be mapped to the same field in the DAT file.";	
    public String emptyDataMappingWarning = "Specified Redaction Text in TIFF Burned Redactions cannot be blank.";	
    public Element getEmailSourceFieldEmailOption(int index) {return driver.FindElementByCssSelector(String.format("#SF_0 > option:nth-child(%s)", index));}	
    public Element getSelectDefaultAutomationRedactionTag() {return driver.FindElementByXPath("//*[@id='tagTreeTIFFComponent']//a[contains(text(), 'Default Automation Redaction')]/i[1]");}	
    public Element getAbbreviatedText() {return driver.FindElementByXPath("//*[@id=\"divRedaction_0\"]/div[2]/label/input");}	
    public Element getMappingIncompleteErrorMessage() {return driver.FindElementByCssSelector("#divbigBoxes p");}	
    public Element getPrivledgeTextEditor() {return driver.FindElementByXPath("//div[@class='redactor-editor']"); } 	
    public Element getTIFFPlaceHolderInsertMetaData() {return driver.FindElementByCssSelector("#LaunchMetaData0");}	
    public Element getTIFFRedactionsInsertMetaData() {return driver.FindElementByCssSelector("#divRedaction_0 > div.form-group.col-md-10 > div.col-md-5.no-padding > div.col-md-12.text-right.no-padding > label > a");}	
    public Element getTIFFTechIssueInsertMetaData() {return driver.FindElementByCssSelector("#c7 > div.panel-body > div > div.tiff-conf > div.col-md-12.tiff-img-logic > div:nth-child(4) > div.col-md-5.red.box > div.col-md-12.text-right.no-padding > label > a");}	
    public Element getSlipSheetCalculatedTab() {return driver.FindElementByCssSelector("#tiff-internal-tab-1 > li:nth-child(3) > a > span");}	
    public Element getBrandingMetaDataList() {return driver.FindElementByXPath("//*[@id=\"MetadataPopup\"]/fieldset/div/div/div");}	
    public Element getPDF_InsertMetadataFieldClick(){ return driver.FindElementByXPath("/html/body/div[3]/div/div[2]/div[2]/div/div[4]/form/div/div[5]/div[2]/div/div/div[3]/div/div[1]/div/div[2]/div[2]/div[2]/label/a"); }	
    public Element getPDF_PlaceholderInsertMetaData() {return driver.FindElementByCssSelector("#c3 > div > div.tiff-conf > div > div.col-md-12.pdf-img-logic > div:nth-child(3) > div.col-md-5.red.box > div.col-md-12.text-right.no-padding > label > a");}	
    public Element getPdf_NativeDoc(){ return driver.FindElementByXPath("//*[@class='add-pdf-img-logic']"); }	
    public Element getPDFNativePlaceHolderInsertMetaData() {return driver.FindElementByCssSelector("#divImagePDFPHImage_0 > div.col-md-5.red.box > div.col-md-12.text-right.no-padding > label > a");}	
    public Element getPDFRedactionsInsertMetaData() {return driver.FindElementByCssSelector("#divPDFRedaction_0 > div.form-group.col-md-10 > div.col-md-5.no-padding > div.col-md-12.text-right.no-padding > label > a");}	
    public Element getPDFTechIssueInsertMetaData() {return driver.FindElementByCssSelector("#c3 > div > div.tiff-conf > div > div.col-md-12.pdf-img-logic > div:nth-child(4) > div.col-md-5.red.box > div.col-md-12.text-right.no-padding > label > a");}	
    public Element getPDFSlipSheetCalculatedTab() {return driver.FindElementByCssSelector("#pdf-internal-tab-1 > li:nth-child(3) > a > span");}
    public Element getPopUpBoxText(){return driver.FindElementByCssSelector("#divbigBoxes p");}	
    public Element getProductionTitleLink(String title) { return driver.FindElementByXPath(String.format("//a[@title='%s']", title)); } 
    public Element getTIFFSelectTagsModal() { return driver.FindElementByXPath("//div[@id='myModal']//form[@class='smart-form client-form']"); }
    public Element getProductionComponentCompleteSuccessCloseBtn() { return driver.FindElementById("botClose3"); }
    public ElementCollection getPrivDocElementSection() { return driver.FindElementsByXPath("//div[@class='PDF-TIFF-Config']//div[@class='col-md-5 red box']"); }
    public Element getTIFFNativeDocumentTagsDialog() { return driver.FindElementByXPath("//div[@id='TIFFContainer']//form[@class='smart-form client-form']"); }
    public Element getPDFNativeDocumentTagsDialog() { return driver.FindElementByXPath("//div[@id='myPDFModal']//form[@class='smart-form client-form']"); }
    public Element getTIFFNativelyProductedDocumentSelect() { return driver.FindElementById("TIFFFileTypes_0"); }
    
    public Element getGridActionDropDown() { return driver.FindElementByXPath("//*[@id=\"DropDownAction\"]/span"); }    
    public Element getAddDocFromActionsDropDown() { return driver.FindElementById("AddDocuments"); }
    public Element getRemoveDocFromActionsDropDown() { return driver.FindElementById("RemoveDocuments"); }
    public Element getProductionCompletebutton() { return driver.FindElementById("btnProductionLocationMarkComplete"); }
    public Element getProductionNextbutton() { return driver.FindElementById("btnProductionLocationNext"); }
    public Element getBackToLocationbutton() { return driver.FindElementByXPath("//*[@id=\"frmProductionSummary\"]/div/div[1]/div/a"); }
    public Element getBackToPrivbutton() { return driver.FindElementByXPath("//*[@id=\"frmProductionLocation\"]/div/div[1]/div/a"); }
    public Element getPrivTitle() { return driver.FindElementByXPath("//*[@class='panel-title-container']"); }
    public Element getDocumentTagSelectionWithFamily_radio() {return driver.FindElementByXPath("//*[@id='frmDocumentsSelection']/div/div[3]/div/div/div/div/div[2]/label/i");}
    public Element getDefaultChildTag() {return driver.FindElementByXPath("//a[@data-content=\"Default Child Tag\"]");}
    public Element getMarkCompleteSuccessfulToaster() {return driver.FindElementByXPath("//*[@id=\"bigBoxColor7\"]/p");}
    public Element getTotalDocumentsCount() {return driver.FindElementByXPath("//*[@id=\"TotalDocumentsCount\"]");}
    public Element getDocumentSelectionCompleteBtn() {return driver.FindElementByXPath("//*[@id=\"btnDocumentsSelectionMarkComplete\"]");}
    public ElementCollection getFamilyDocsCount() {return driver.FindElementsById("#\\34");}
    public Element getDocumentSelectionNextBtn() {return driver.FindElementById("btnDocumentsSelectionNext");}
    public Element getAddRuleBtn() {return driver.FindElementById("contentmetadata");}
    public Element getPrivDefaultChildBtn() {return driver.FindElementByXPath("//*[@id=\"28_anchor\"]/i[1]");}
    public Element getPrivQueryBtn() {return driver.FindElementByXPath("//*[@id=\"insertQueryBtn\"]");}
    public Element getCheckForMatchingDocBtn() {return driver.FindElementById("btnDocumentMatch");}
    public Element getPrivDocListBtn() {return driver.FindElementById("btnGoToDocList");}
    public Element getFirstDocId() {return driver.FindElementByXPath("//*[@id=\"dtDocList\"]/tbody/tr[1]/td[2]/label/i");}
    public Element getPrivDocIdYesBtn() {return driver.FindElementById("bot1-Msg1");}
    public Element getProdGuardCompleteBtn() {return driver.FindElementById("btnProductionGuardMarkComplete");}
    public Element getFamilyList() {return driver.FindElementByXPath("//*[@id=\"dtDocList\"]/tbody/tr[1]/td[3]");}
    public ElementCollection getChildList(int i) {return driver.FindElementsByXPath(String.format("//*[@id=\"childlist_861_dtDocList\"]/tbody/tr[%s]",i));}
    public Element getSpecifyProdLocBackBtn() {return driver.FindElementByXPath("//*[@id=\"frmProductionLocation\"]/div/div[1]/div/a");}
    public Element getPrivMarkIncompleteBtn() {return driver.FindElementById("btnProductionGuardMarkInComplete");}
    public Element getPrivSecondRuleRemoveBtn() {return driver.FindElementByXPath("//*[.='Remove']/../*[@id='Rule 2']");}
    
    public Element getDocSelectionNumOfFamilyDocs() {return driver.FindElementById("ProductionDocumentsSelectedCount");}
    public Element getIncludeFamilyToggle() { return driver.FindElementByCssSelector("#ProductionDocumentsSelection_ToIncludeFamilies + input +i");}
    public Element getNumCustomSortUploadExcelRadioButton() {return driver.FindElementByCssSelector("div.box:nth-child(4) > div:nth-child(1) > label:nth-child(1) > i:nth-child(2)");}
    public Element getNumSortBySelectedGrid() { return driver.FindElementByXPath("//div[@id='divSpecifyTagOrder_1']/div[1]");}
    public ElementCollection getNumSortBySelectedGridHeaders() { return driver.FindElementsByXPath("//div[@id='divSpecifyTagOrder_1']/div[1]//h3"); }
    public ElementCollection getNumSortBySelectedGridTags() { return driver.FindElementsByCssSelector("div[id='tagsTree']>ul>li>ul>li") ;}
    public Element getNumCustomSortUploadExcelSelectExcelButton(){ return driver.FindElementByCssSelector("input[type=file]");}
    public ElementCollection getNumSortingMetadataDropdownList(){ return driver.FindElementsByCssSelector("#lstSortingMetaData>option");}
    public ElementCollection getNumSortingMetadataSubSortDropdownList(){ return driver.FindElementsByCssSelector("#lstSubSortingMetaData>option");}
    public Element getNumNextBatesNumberDialog(){ return driver.FindElementByCssSelector("div[aria-describedby='NextBatesPopUpdiv']");}
    public Element getNumBatesDialogCloseButton(){ return driver.FindElementByClassName("ui-dialog-titlebar-close");}
    public Element getNumSortBySelectedTagsRadioButton() {return driver.FindElementByCssSelector("div.box:nth-child(3) > div:nth-child(1) > label:nth-child(1) > i:nth-child(2)");}
    public Element getMessageContainerRemovalMessage() { return driver.FindElementByXPath("//div[@id='MsgBoxBack']//p"); }    
    public Element getPDFBlankPageRemoveToggle2() { return driver.FindElementByXPath("//div[@id='PDFContainer']//label[@class='toggle']//input[@id='chkPDFBlankPageRemove']/following-sibling::i"); } 

    //Click the desired production set option, in the dropdown menu by it's index
    public void clickProductionSetByIndex(int index) {
    	if(driver.FindElementsByCssSelector("[id=ProductionSets] option ").FindWebElements().size() > index) {
    		driver.FindElementsByCssSelector("[id=ProductionSets] option ").FindWebElements().get(index).click();
    	}
    }
    //Return the name of a production based on it's index on the production page
    public String getProductionTileNameByIndex(int index) {
    	if(driver.FindElementsByCssSelector("#pName").FindWebElements().size() > index) {
    		return driver.FindElementsByCssSelector("#pName").FindWebElements().get(index).findElement(By.cssSelector("a[title]")).getText(); 
    	}
    	return null;
    }
    //Return Production Based on Name
    public WebElement getProductionTileByName(String name) {
    	for(WebElement x: driver.FindElementsByCssSelector("#pName").FindWebElements()) {
    		if(x.findElement(By.cssSelector("a[title]")).getText().equals(name)) return x;
    	}
    	return null;
    }
    //Return Settings for a given Tile based on its WebElement
    public WebElement getProductionTileSettingsByName(WebElement x){
    	return x.findElement(By.cssSelector("a[data-toggle=dropdown]"));
    }
    
    //Get Bates Count for a given Tile based on its Index
    public WebElement getProductionTileViewBates(int index) { 
    	if(index < driver.FindElementsById("batesCount").FindWebElements().size()){
    		return driver.FindElementsById("batesCount").FindWebElements().get(index);}
    	else return null;


    }


    //Quick Function to get rows of Grid View Production Table
    public WebElement getProductionListGridViewTableRows(int row) {
    	if(row< driver.FindElementsByCssSelector("#ProductionListGridViewTable >tbody>tr").FindWebElements().size()){
    		return driver.FindElementsByCssSelector("#ProductionListGridViewTable >tbody>tr").FindWebElements().get(row);
    	}
    	return null;
    }

    public void closeAllPopUps() {
    	for(int i =0; i<100; i++) {
    		if(getProductionConfirmPopupCloseBtn(i).FindWebElements().size()!=0) {
    			getProductionConfirmPopupCloseBtn(i).FindWebElements().get(0).click();
    		}
    	}
    }

    public ProductionPage(Driver driver){

        this.driver = driver;
        this.driver.getWebDriver().get(Input.url+"Production/Home");
        driver.waitForPageToBeReady();
        base = new BaseClass(driver);
    }
    
    public void addNewProduction(String productionName, String template) {
    	try {
    		
    	
			
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getProdExport_ProductionSets().Visible()  ;}}), Input.wait30); 
		getProdExport_ProductionSets().SendKeys("DefaultProductionSet");

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getAddNewProductionbutton().Displayed()  ;}}), Input.wait30); 
		getAddNewProductionbutton().Click();
		driver.waitForPageToBeReady();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProductionName().Displayed()  ;}}), Input.wait30); 
		getProductionName().SendKeys(productionName);
		

		if (template != "false") {
			getprod_LoadTemplate().click();
			getprod_LoadTemplate().SendKeys(template);
			//driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			//		getBasicInfoTemplate(String.format(" [value='%s']",template)).Visible()  ;}}), Input.wait30); 
			//getBasicInfoTemplate(String.format(" [value='%s']",template)).Click();		
		}

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getBasicInfoCompleteButton().Visible()  ;}}), Input.wait30); 
		getBasicInfoCompleteButton().Click();

		//Added to get rid of Toast message, which I think is effecting the rest of Script
        
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getBasicInfoMarkedCompleteCloseBtn().Visible()  ;}}), Input.wait30); 
		getBasicInfoMarkedCompleteCloseBtn().Click();


		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getBasicInfoNextButton().Enabled()  ;}}), Input.wait30); 
		getBasicInfoNextButton().Click();
		driver.waitForPageToBeReady();
    	}
    	catch(Exception e){System.out.println("error occured");}

		
    }
    
    public void addNewExport(String productionName, String template, String priorProduction) {
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getExportSetDropdown().Visible()  ;}}), Input.wait30); 
		getExportSetDropdown().Click();

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getAddNewExportButton().Displayed()  ;}}), Input.wait30); 
		getAddNewExportButton().Click();
		driver.waitForPageToBeReady();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProductionName().Displayed()  ;}}), Input.wait30); 
		getProductionName().SendKeys(productionName);
		
		if (template != "false") {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getBasicInfoTemplate(String.format(" [value='%s']",template)).Visible()  ;}}), Input.wait30); 
			getBasicInfoTemplate(String.format(" [value='%s']",template)).Click();		
		}
		
		if (priorProduction != "false") {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getProdExport_Priorprodtoggle().Visible()  ;}}), Input.wait30);
			getProdExport_Priorprodtoggle().Click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getBasicInfoPriorProd(String.format(" [value='%s']",priorProduction)).Visible()  ;}}), Input.wait30); 
			getBasicInfoPriorProd(String.format(" [value='%s']",priorProduction)).Click();
		}

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getBasicInfoCompleteButton().Visible()  ;}}), Input.wait30); 
		getBasicInfoCompleteButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getBasicInfoNextButton().Enabled()  ;}}), Input.wait30); 
		getBasicInfoNextButton().Click();
		driver.waitForPageToBeReady();

		
    }
    
    public void clickProductionTileView() {
    	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getAddNewProductionbutton().Visible()  ;}}), Input.wait30); 
		getTileViewIcon().Click();
    }
    
    public void clickProductionGridView() {
    	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getAddNewProductionbutton().Visible()  ;}}), Input.wait30); 
		getGridViewIcon().Click();
		getGridViewIcon().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getGridProdCount().Visible()  ;}}), Input.wait30); 
    }
    
    public void CreateProduction(String productionname,String PrefixID,String SuffixID,final String foldername,String tagname) 
    		throws InterruptedException{
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getAddNewProductionbutton().Visible()  ;}}), Input.wait30); 
		getAddNewProductionbutton().Click();
	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProductionName().Visible()  ;}}), Input.wait30); 
		getProductionName().SendKeys(productionname);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProductionDesc().Visible()  ;}}), Input.wait30); 
		getProductionDesc().SendKeys(productionname);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getBasicInfoMarkComplete().Visible()  ;}}), Input.wait30); 
		getBasicInfoMarkComplete().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDATChkBox().Enabled()  ;}}), Input.wait30); 
		getDATChkBox().waitAndClick(20);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDATTab().Visible()  ;}}), Input.wait30); 
		getDATTab().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDAT_FieldClassification1().Visible()  ;}}), Input.wait30); 
		getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText("Bates");
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDAT_SourceField1().Visible()  ;}}), Input.wait30); 
		getDAT_SourceField1().selectFromDropdown().selectByVisibleText("BatesNumber");
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDAT_DATField1().Visible()  ;}}), Input.wait30); 
		getDAT_DATField1().SendKeys("BatesNumber"+Utility.dynamicNameAppender());
		
		driver.scrollingToBottomofAPage();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNativeChkBox().Enabled()  ;}}), Input.wait30); 
		getNativeChkBox().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNativeTab().Enabled()  ;}}), Input.wait30); 
		getNativeTab().Click();
		
		driver.scrollPageToTop();
		
		System.out.println(getNative_text().getText());
		Assert.assertEquals(getNative_text().getText(),"To produce specific docs"
		+ " natively, please select file types and/or tags below. In addition,"
		+ " to export placeholders for these docs, configure placeholder in the TIFF "
		+ "or PDF section for the same selected file types and/or tags.");
		
		Assert.assertEquals(getNative_text_Color().GetAttribute("class"),"blue-text");
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNative_SelectAllCheck().Enabled()  ;}}), Input.wait30); 
		getNative_SelectAllCheck().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNative_AdvToggle().Enabled()  ;}}), Input.wait30); 
		getNative_AdvToggle().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNative_GenerateLoadFileLST().Enabled()  ;}}), Input.wait30); 
		getNative_GenerateLoadFileLST().Click();
		Thread.sleep(2000);
		
		driver.scrollingToBottomofAPage();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTIFFChkBox().Enabled() ;}}), Input.wait30); 
		getTIFFChkBox().Click();		
		
		driver.scrollingToBottomofAPage();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTIFFTab().Enabled()  ;}}), Input.wait30); 
		getTIFFTab().Click();
		
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTIFF_CenterHeaderBranding().Visible() &&  getTIFF_CenterHeaderBranding().Enabled() ;}}), Input.wait30); 
		getTIFF_CenterHeaderBranding().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTIFF_InsertMetadataFieldClick().Visible()  ;}}), Input.wait30); 
		getTIFF_InsertMetadataFieldClick().waitAndClick(10);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTIFF_selectedMetadataField_Ok().Visible()  ;}}), Input.wait30); 
		getTIFF_selectedMetadataField_Ok().Click();
		
    	Thread.sleep(2000);
    	
    	getTIFF_EnableforPrivilegedDocs().ScrollTo();
    	
    	//driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				//getTIFF_EnableforPrivilegedDocs().Enabled()  ;}}), Input.wait30); 
		//getTIFF_EnableforPrivilegedDocs().waitAndClick(20);
		
		driver.scrollingToBottomofAPage();
		
		getPriveldge_SelectTagButton().waitAndClick(10);
		Thread.sleep(2000);
		
		getPriveldge_TagTree(tagname).waitAndClick(10);
		Thread.sleep(2000);
		
		getPriveldge_TagTree_SelectButton().waitAndClick(10);
		Thread.sleep(2000);
		
		String expplaceholdertexttiff = getTiff_placeholdertext().getText();
		System.out.println(expplaceholdertexttiff);
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
		
		Assert.assertEquals(getTiff_redactiontext().getText(),"To burn redactions, select specific redactions"
		+ " or all redactions (annotation layer). Specify the redaction text for each"
		+ " selected redaction.");
		
				
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTextChkBox().Enabled()  ;}}), Input.wait30); 
		getTextChkBox().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTextTab().Enabled()  ;}}), Input.wait30); 
		getTextTab().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTextcomponent_text().Displayed()  ;}}), Input.wait30); 
	
		String exptext= getTextcomponent_text().getText();
		System.out.println(exptext);
		Assert.assertEquals(exptext, "Redacted documents are automatically OCRed"
			+ " to export the text. Original extracted text is exported for natively "
			+ "produced documents (file based placeholdering). "
			+ "For exception and privileged placeholdered docs, "
			+ "the placeholder text is exported."+" The configured format is applicable only to OCRed text and production generated text"
			+ ", and not to ingested text.");
		
				
//		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//				getPDFChkBox().Enabled() ;}}), Input.wait30); 
//		getPDFChkBox().Click();		
//		
//		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//				getPDFTab().Enabled()  ;}}), Input.wait30); 
//		getPDFTab().Click();
//		
//		driver.scrollPageToTop();
//		
//		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//				getPDF_CenterHeaderBranding().Visible() &&  getTIFF_CenterHeaderBranding().Enabled() ;}}), Input.wait30); 
//		getPDF_CenterHeaderBranding().ScrollTo();
//		getPDF_CenterHeaderBranding().Click();
//		
//		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//				getPDF_EnterBranding().Enabled()  ;}}), Input.wait30); 
//	
//		new Actions(driver.getWebDriver()).moveToElement(getPDF_EnterBranding().getWebElement()).click();
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
//		new Actions(driver.getWebDriver()).moveToElement(getPriveldge_PDFTextArea().getWebElement()).click();
//		
//		getPriveldge_PDFTextArea().SendKeys("testing");
//		
//		String expplaceholdertextpdf = getpdf_placeholdertext().getText();
//		System.out.println(expplaceholdertextpdf);
//		Assert.assertEquals(expplaceholdertextpdf, "TIFFs are generated and exported "
//				+ "for all documents by default. To export placeholders for docs "
//				+ "that are Privileged Withhold, Tech Issue or Produced Natively,"
//				+ " please configure the placeholder section below.");
//		
//		Assert.assertEquals(getpdf_NativeDoc().getText(),"Enable for Natively Produced Documents:");
//		
//		Assert.assertEquals(getpdf_NativeDoc_FileType().getText(),"Select File Types and/or Tags:");
//	
//		Assert.assertEquals(getpdf_redactiontext().getText(),"To burn redactions, select specific redactions"
//				+ " or all redactions (annotation layer). Specify the redaction text for each"
//				+ " selected redaction.");
//				
//		
//		driver.scrollPageToTop();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getComponentsMarkComplete().Enabled()  ;}}), Input.wait30); 
		getComponentsMarkComplete().Click();
		
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
		getKeepFamiliesTogether().Click();
		
		driver.scrollPageToTop();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNumAndSortMarkComplete().Enabled()  ;}}), Input.wait30); 
		getNumAndSortMarkComplete().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNumAndSortNext().Enabled() ;}}), Input.wait30); 
		getNumAndSortNext().Click();
		
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
		getIncludeFamilies().Click();
		
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
		getOkButton().Click();
		
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
		getbtnProductionLocationMarkComplete().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionLocationNext().Enabled()  ;}}), Input.wait30); 
		getbtnProductionLocationNext().Click();
		
		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getPreviewprod().Enabled() ;}}), Input.wait30); getPreviewprod().Click();
		 */
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionSummaryMarkComplete().Visible()  ;}}), Input.wait30); 
		getbtnProductionSummaryMarkComplete().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionSummaryNext().Enabled()  ;}}), Input.wait30); 
		getbtnProductionSummaryNext().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionGenerate().Visible()  ;}}), Input.wait30); 
		getbtnProductionGenerate().Click();
		System.out.println("Wait for generate to complete");
				
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocumentGeneratetext().Visible()  ;}}), Input.wait120); 
		Assert.assertTrue(getDocumentGeneratetext().Displayed());
			
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getQC_backbutton().Enabled()  ;}}), Input.wait30); 
		getQC_backbutton().waitAndClick(15);
	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProd_BatesRange().Enabled()  ;}}), Input.wait30); 
		String batesno = getProd_BatesRange().getText();
		System.out.println(batesno);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnSummaryNext().Enabled()  ;}}), Input.wait30); 
		getbtnSummaryNext().Click();
		
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getCopyPath().Visible()  ;}}), Input.wait30); 
		getCopyPath().Click();
		
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
         System.out.println(Doc); 
		
		
    	}
    
    public void ExportwithpriorProduction(String exportname,String PrefixID,String SuffixID,final String foldername) 
    		throws InterruptedException{
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProdExportSet().Visible()  ;}}), Input.wait30); 
		getProdExportSet().Click();
	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProductionSettxt().Visible()  ;}}), Input.wait30); 
		getProductionSettxt().SendKeys(exportname);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProdExportSetRadioButton().Visible()  ;}}), Input.wait30); 
		getProdExportSetRadioButton().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProdExport_SaveButton().Visible()  ;}}), Input.wait30); 
		getProdExport_SaveButton().Click();
		
		base.VerifySuccessMessage("Export Set Added Successfully");
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProdExport_ProductionSets().Visible()  ;}}), Input.wait30); 
		getProdExport_ProductionSets().selectFromDropdown().selectByIndex(1);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProdExport_AddaNewExportSet().Visible()  ;}}), Input.wait30); 
		getProdExport_AddaNewExportSet().Click();
	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProductionName().Visible()  ;}}), Input.wait30); 
		getProductionName().SendKeys(exportname);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProductionDesc().Visible()  ;}}), Input.wait30); 
		getProductionDesc().SendKeys(exportname);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProdExport_Priorprodtoggle().Visible()  ;}}), Input.wait30); 
		getProdExport_Priorprodtoggle().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProdExport_SelectProductionSet().Visible()  ;}}), Input.wait30); 
		getProdExport_SelectProductionSet().selectFromDropdown().selectByIndex(1);
		
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getBasicInfoMarkComplete().Visible()  ;}}), Input.wait30); 
		getBasicInfoMarkComplete().Click();
				
//		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//				getBasicInfoNext().Enabled() ;}}), Input.wait30); 
//		getBasicInfoNext().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDATTab().Visible()  ;}}), Input.wait30); 
		getDATTab().Click();
		
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
		getNumAndSortMarkComplete().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNumAndSortNext().Enabled() ;}}), Input.wait30); 
		getNumAndSortNext().Click();
		
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
		getOkButton().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getlstProductionRootPaths().Visible()  ;}}), Input.wait30); 
		getlstProductionRootPaths().selectFromDropdown().selectByVisibleText(Input.prodPath);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProductionOutputLocation_ProductionDirectory().Visible()  ;}}), Input.wait30); 
		getProductionOutputLocation_ProductionDirectory().SendKeys(exportname);
		
		driver.scrollPageToTop();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionLocationMarkComplete().Visible()  ;}}), Input.wait30); 
		getbtnProductionLocationMarkComplete().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionLocationNext().Enabled()  ;}}), Input.wait30); 
		getbtnProductionLocationNext().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionSummaryMarkComplete().Visible()  ;}}), Input.wait30); 
		getbtnProductionSummaryMarkComplete().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionSummaryNext().Enabled()  ;}}), Input.wait30); 
		getbtnProductionSummaryNext().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionGenerate().Visible()  ;}}), Input.wait30); 
		getbtnProductionGenerate().Click();
		System.out.println("Wait until regenerate is enabled");
	
		for (int i = 0; i < 120; i++)
		{
			try
			{
				
				this.driver.getWebDriver().get(Input.url+"Production/Home");
		    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						getProdExport_ProductionSets().Visible()  ;}}), Input.wait30); 
				getProdExport_ProductionSets().selectFromDropdown().selectByIndex(1);
				getProdStateFilter().WaitUntilPresent();
				getProdStateFilter().selectFromDropdown().selectByVisibleText("COMPLETED");
		    	getProductionLink().waitAndClick(5);
		    	 //Thread.sleep(5000);
				getbtnGenerateMarkComplete().waitAndClick(5);
				System.out.println("Generated");
				break;
				
			}
			catch (Exception e)
			{
				Thread.sleep(10000);
				driver.Navigate().refresh();
				
			
			}
		}
	
	
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnSummaryNext().Enabled()  ;}}), Input.wait30); 
		getbtnSummaryNext().Click();
		//Thread.sleep(10000);
	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getReviewproductionButton().Visible()  ;}}), Input.wait30); 
		getReviewproductionButton().Click();
		
		String location = getDestinationPathText().getText();
        System.out.println(location);
        Thread.sleep(7000);
        
        String doccount = getDoc_Count().getText();
        System.out.println(doccount);
        
        base.isFileDownloaded(location+"\\VOL0001"+"\\Text"+"\\0001",3);
		
	    }
      
    public void Productionwithallredactions(String productionname,String PrefixID,String SuffixID,final String foldername,String tagname) 
    		throws Exception{
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getAddNewProductionbutton().Visible()  ;}}), Input.wait30); 
		getAddNewProductionbutton().Click();
	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProductionName().Visible()  ;}}), Input.wait30); 
		getProductionName().SendKeys(productionname);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProductionDesc().Visible()  ;}}), Input.wait30); 
		getProductionDesc().SendKeys(productionname);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getBasicInfoMarkComplete().Visible()  ;}}), Input.wait30); 
		getBasicInfoMarkComplete().Click();
						
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDATChkBox().Enabled()  ;}}), Input.wait30); 
		getDATChkBox().waitAndClick(20);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDATTab().Visible()  ;}}), Input.wait30); 
		getDATTab().Click();
		
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
			Assert.assertTrue(listsecfiled.getText().equalsIgnoreCase("TIFFPageCount"));
			else
				System.out.println("Element not matching");
			
		}
    	
		BaseClass bc= new BaseClass(driver);
	//	bc.comparearraywithlist(fieldexp, elelist);
	
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDAT_FieldClassification1().Visible()  ;}}), Input.wait30); 
		getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText("Bates");
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDAT_SourceField1().Visible()  ;}}), Input.wait30); 
		getDAT_SourceField1().selectFromDropdown().selectByVisibleText("BatesNumber");
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDAT_DATField1().Visible()  ;}}), Input.wait30); 
		getDAT_DATField1().SendKeys("BatesNumber"+Utility.dynamicNameAppender());
		
		driver.scrollingToBottomofAPage();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNativeChkBox().Enabled()  ;}}), Input.wait30); 
		getNativeChkBox().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNativeTab().Enabled()  ;}}), Input.wait30); 
		getNativeTab().Click();
		
		driver.scrollPageToTop();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNative_SelectAllCheck().Enabled()  ;}}), Input.wait30); 
		getNative_SelectAllCheck().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNative_AdvToggle().Enabled()  ;}}), Input.wait30); 
		getNative_AdvToggle().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNative_GenerateLoadFileLST().Enabled()  ;}}), Input.wait30); 
		getNative_GenerateLoadFileLST().Click();
		Thread.sleep(2000);
		
		driver.scrollingToBottomofAPage();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTIFFChkBox().Enabled() ;}}), Input.wait30); 
		getTIFFChkBox().Click();		
		
		driver.scrollingToBottomofAPage();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTIFFTab().Enabled()  ;}}), Input.wait30); 
		getTIFFTab().Click();
		
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTIFF_CenterHeaderBranding().Visible() &&  getTIFF_CenterHeaderBranding().Enabled() ;}}), Input.wait30); 
		getTIFF_CenterHeaderBranding().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTIFF_EnterBranding().Enabled()  ;}}), Input.wait30); 
	
		new Actions(driver.getWebDriver()).moveToElement(getTIFF_EnterBranding().getWebElement()).click();
		//getTIFF_EnterBranding().Click();
    	getTIFF_EnterBranding().SendKeys("Test");
    	Thread.sleep(2000);
    	
    	getTIFF_EnableforPrivilegedDocs().ScrollTo();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTIFF_EnableforPrivilegedDocs().Enabled()  ;}}), Input.wait30); 
		//getTIFF_EnableforPrivilegedDocs().waitAndClick(20);
		
		driver.scrollingToBottomofAPage();
		
		getPriveldge_SelectTagButton().waitAndClick(10);
		Thread.sleep(2000);
		
		getPriveldge_TagTree(tagname).waitAndClick(10);
		Thread.sleep(2000);
		
		getPriveldge_TagTree_SelectButton().waitAndClick(10);
		Thread.sleep(2000);
		
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getPriveldge_TextArea().Enabled()  ;}}), Input.wait30); 
		new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();
		
		getPriveldge_TextArea().SendKeys("testing");
		
	    getTIFF_BurnRedtoggle().waitAndClick(10);
		
		/*getTIFF_SpecifyRedactText().waitAndClick(10);
		
		
		getTIFF_SelectRedtagbuton().waitAndClick(10);
		Thread.sleep(5000);
		
		getTIFF_SelectRedtags("R1").waitAndClick(10);
	
		getTIFF_SelectRedtags_SelectButton().waitAndClick(10);	
		System.out.println(getTIFF_Red_Placeholdertext().getText());
		
		Assert.assertEquals(getTIFF_Red_Placeholdertext().getText(),"REDACTED");*/
		
		driver.scrollingToBottomofAPage();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTextChkBox().Enabled()  ;}}), Input.wait30); 
		getTextChkBox().Click();
		
//		driver.scrollingToBottomofAPage();
//				
//		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//				getPDFChkBox().Enabled() ;}}), Input.wait30); 
//		getPDFChkBox().Click();		
//		
//		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//				getPDFTab().Enabled()  ;}}), Input.wait30); 
//		getPDFTab().Click();
//		
//		driver.scrollPageToTop();
//		
//		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//				getPDF_CenterHeaderBranding().Visible() &&  getTIFF_CenterHeaderBranding().Enabled() ;}}), Input.wait30); 
//		getPDF_CenterHeaderBranding().ScrollTo();
//		getPDF_CenterHeaderBranding().Click();
//		
//		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//				getPDF_EnterBranding().Enabled()  ;}}), Input.wait30); 
//	
//		new Actions(driver.getWebDriver()).moveToElement(getPDF_EnterBranding().getWebElement()).click();
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
//		new Actions(driver.getWebDriver()).moveToElement(getPriveldge_PDFTextArea().getWebElement()).click();
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
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getComponentsMarkComplete().Enabled()  ;}}), Input.wait30); 
		getComponentsMarkComplete().Click();
		
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
		getKeepFamiliesTogether().Click();
		
		driver.scrollPageToTop();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNumAndSortMarkComplete().Enabled()  ;}}), Input.wait30); 
		getNumAndSortMarkComplete().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNumAndSortNext().Enabled() ;}}), Input.wait30); 
		getNumAndSortNext().Click();
		
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
		getIncludeFamilies().Click();
		
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
		getOkButton().Click();
		
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
		getbtnProductionLocationMarkComplete().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionLocationNext().Enabled()  ;}}), Input.wait30); 
		getbtnProductionLocationNext().Click();
		
		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getPreviewprod().Enabled() ;}}), Input.wait30); getPreviewprod().Click();
		 */
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionSummaryMarkComplete().Visible()  ;}}), Input.wait30); 
		getbtnProductionSummaryMarkComplete().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionSummaryNext().Enabled()  ;}}), Input.wait30); 
		getbtnProductionSummaryNext().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionGenerate().Visible()  ;}}), Input.wait30); 
		getbtnProductionGenerate().Click();
		System.out.println("Wait until regenerate is enabled");
		for (int i = 0; i < 120; i++)
		{
			try
			{			
				this.driver.getWebDriver().get(Input.url+"Production/Home");
			    getProductionLink().waitAndClick(5);
				getbtnGenerateMarkComplete().waitAndClick(5);
				System.out.println("Generated");
				break;
				
			}
			catch (Exception e)
			{
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
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnSummaryNext().Enabled()  ;}}), Input.wait30); 
		getbtnSummaryNext().Click();
		//Thread.sleep(10000);
	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getConfirmProductionCommit().Enabled()  ;}}), Input.wait30); 
		getConfirmProductionCommit().waitAndClick(10);
		
		 String PDocCount = getProductionDocCount().getText();
         int Doc = Integer.parseInt(PDocCount);
         System.out.println(Doc); 
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getReviewproductionButton().Visible()  ;}}), Input.wait30); 
		getReviewproductionButton().Click();
		
		String location = getDestinationPathText().getText();
        System.out.println(location);
        Thread.sleep(7000);
        String doccount = getDoc_Count().getText();
        System.out.println(doccount);
        
        ProdPathallredact= location+"\\VOL0001"+"\\PDF"+"\\0001"+"\\"+""+a+".pdf";
        System.out.println(ProdPathallredact);
      
        
         base.isFileDownloaded(location+"\\VOL0001"+"\\PDF"+"\\0001",1);
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
     
    public void Productionwithsomeredactions(String productionname,String PrefixID,String SuffixID,final String foldername,String redname) 
    		throws Exception{
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getAddNewProductionbutton().Visible()  ;}}), Input.wait30); 
		getAddNewProductionbutton().Click();
	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProductionName().Visible()  ;}}), Input.wait30); 
		getProductionName().SendKeys(productionname);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProductionDesc().Visible()  ;}}), Input.wait30); 
		getProductionDesc().SendKeys(productionname);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getBasicInfoMarkComplete().Visible()  ;}}), Input.wait30); 
		getBasicInfoMarkComplete().Click();
		
		getBasicInfoNext().waitAndClick(10);
				
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDATChkBox().Enabled()  ;}}), Input.wait30); 
		getDATChkBox().waitAndClick(20);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDATTab().Visible()  ;}}), Input.wait30); 
		getDATTab().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDAT_FieldClassification1().Visible()  ;}}), Input.wait30); 
		getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText("Production");
		
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDAT_SourceField1().Visible()  ;}}), Input.wait30);
		String fieldexp[]= {"TIFFPageCount","VolumeName"};
		BaseClass bc= new BaseClass(driver);
		bc.getallselectele(getDAT_SourceField1().selectFromDropdown());
	
		//Assert.assertEquals("", expected);(result.getText().equalsIgnoreCase("TIFFPageCount"));
		
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDAT_FieldClassification1().Visible()  ;}}), Input.wait30); 
		getDAT_FieldClassification1().selectFromDropdown().selectByVisibleText("Bates");
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDAT_SourceField1().Visible()  ;}}), Input.wait30); 
		getDAT_SourceField1().selectFromDropdown().selectByVisibleText("BatesNumber");
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDAT_DATField1().Visible()  ;}}), Input.wait30); 
		getDAT_DATField1().SendKeys("BatesNumber"+Utility.dynamicNameAppender());
		
		driver.scrollingToBottomofAPage();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNativeChkBox().Enabled()  ;}}), Input.wait30); 
		getNativeChkBox().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNativeTab().Enabled()  ;}}), Input.wait30); 
		getNativeTab().Click();
		
		driver.scrollPageToTop();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNative_SelectAllCheck().Enabled()  ;}}), Input.wait30); 
		getNative_SelectAllCheck().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNative_AdvToggle().Enabled()  ;}}), Input.wait30); 
		getNative_AdvToggle().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNative_GenerateLoadFileLST().Enabled()  ;}}), Input.wait30); 
		getNative_GenerateLoadFileLST().Click();
		Thread.sleep(2000);
		
		driver.scrollingToBottomofAPage();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTIFFChkBox().Enabled() ;}}), Input.wait30); 
		getTIFFChkBox().Click();		
		
		driver.scrollingToBottomofAPage();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTIFFTab().Enabled()  ;}}), Input.wait30); 
		getTIFFTab().Click();
		
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTIFF_CenterHeaderBranding().Visible() &&  getTIFF_CenterHeaderBranding().Enabled() ;}}), Input.wait30); 
		getTIFF_CenterHeaderBranding().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTIFF_EnterBranding().Enabled()  ;}}), Input.wait30); 
	
		new Actions(driver.getWebDriver()).moveToElement(getTIFF_EnterBranding().getWebElement()).click();
		//getTIFF_EnterBranding().Click();
    	getTIFF_EnterBranding().SendKeys("Test");
    	Thread.sleep(2000);
    	
    	getTIFF_EnableforPrivilegedDocs().ScrollTo();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTIFF_EnableforPrivilegedDocs().Enabled()  ;}}), Input.wait30); 
		//getTIFF_EnableforPrivilegedDocs().waitAndClick(20);
		
		driver.scrollingToBottomofAPage();
		
		getPriveldge_SelectTagButton().waitAndClick(10);
		Thread.sleep(2000);
		
		getPriveldge_TagTree("Privileged").waitAndClick(10);
		Thread.sleep(2000);
		
		getPriveldge_TagTree_SelectButton().waitAndClick(10);
		Thread.sleep(2000);
		
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getPriveldge_TextArea().Enabled()  ;}}), Input.wait30); 
		new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();
		
		getPriveldge_TextArea().SendKeys("testing");
		
	    getTIFF_BurnRedtoggle().waitAndClick(10);
		
		//getTIFF_SpecifyRedactText().waitAndClick(10);
		
		
		//getTIFF_SelectRedtagbuton().waitAndClick(10);
	    getTIFF_SelectRed_Radiobutton().waitAndClick(10);
	    Thread.sleep(2000);
		
		getTIFF_SelectRedtags().waitAndClick(10);
	
		//getTIFF_SelectRedtags_SelectButton().waitAndClick(10);	
		//System.out.println(getTIFF_Red_Placeholdertext().getText());
		
	//	Assert.assertEquals(getTIFF_Red_Placeholdertext().getText(),"REDACTED");
		
		driver.scrollingToBottomofAPage();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTextChkBox().Enabled()  ;}}), Input.wait30); 
		getTextChkBox().Click();
		
		driver.scrollingToBottomofAPage();
				
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getPDFChkBox().Enabled() ;}}), Input.wait30); 
		getPDFChkBox().Click();		
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getPDFTab().Enabled()  ;}}), Input.wait30); 
		getPDFTab().Click();
		
		driver.scrollPageToTop();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getPDF_CenterHeaderBranding().Visible() &&  getTIFF_CenterHeaderBranding().Enabled() ;}}), Input.wait30); 
		getPDF_CenterHeaderBranding().ScrollTo();
		getPDF_CenterHeaderBranding().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getPDF_EnterBranding().Enabled()  ;}}), Input.wait30); 
	
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
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getPriveldge_TextArea().Enabled()  ;}}), Input.wait30); 
		new Actions(driver.getWebDriver()).moveToElement(getPriveldge_PDFTextArea().getWebElement()).click();
		
		getPriveldge_PDFTextArea().SendKeys("testing");
		
		getPDF_BurnRedtoggle().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		//getPDF_SpecifyRedactText().waitAndClick(10);
		
		
		getPDF_SelectRed_Radiobutton().waitAndClick(10);
		Thread.sleep(2000);
		
		getPDF_SelectRedtags().waitAndClick(10);
		
	//	getPDF_SelectRedtags_SelectButton().waitAndClick(10);
	//	System.out.println(getPDF_Red_Placeholdertext().getText()); 
		
	//	Assert.assertEquals(getPDF_Red_Placeholdertext().getText(),"REDACTED");
		
		driver.scrollPageToTop();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getComponentsMarkComplete().Enabled()  ;}}), Input.wait30); 
		getComponentsMarkComplete().Click();
		
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
		getKeepFamiliesTogether().Click();
		
		driver.scrollPageToTop();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNumAndSortMarkComplete().Enabled()  ;}}), Input.wait30); 
		getNumAndSortMarkComplete().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNumAndSortNext().Enabled() ;}}), Input.wait30); 
		getNumAndSortNext().Click();
		
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
		getIncludeFamilies().Click();
		
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
		getOkButton().Click();
		
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
		getbtnProductionLocationMarkComplete().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionLocationNext().Enabled()  ;}}), Input.wait30); 
		getbtnProductionLocationNext().Click();
		
		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getPreviewprod().Enabled() ;}}), Input.wait30); getPreviewprod().Click();
		 */
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionSummaryMarkComplete().Visible()  ;}}), Input.wait30); 
		getbtnProductionSummaryMarkComplete().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionSummaryNext().Enabled()  ;}}), Input.wait30); 
		getbtnProductionSummaryNext().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnProductionGenerate().Visible()  ;}}), Input.wait30); 
		getbtnProductionGenerate().Click();
		System.out.println("Wait until regenerate is enabled");
		for (int i = 0; i < 120; i++)
		{
			try
			{
				
				
				this.driver.getWebDriver().get(Input.url+"Production/Home");
		    	//Thread.sleep(5000);
		    	getProductionLink().waitAndClick(5);
		    	 //Thread.sleep(5000);
				getbtnGenerateMarkComplete().waitAndClick(5);
				System.out.println("Generated");
				break;
				
			}
			catch (Exception e)
			{
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
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getbtnSummaryNext().Enabled()  ;}}), Input.wait30); 
		getbtnSummaryNext().Click();
		//Thread.sleep(10000);
	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getConfirmProductionCommit().Enabled()  ;}}), Input.wait30); 
		getConfirmProductionCommit().waitAndClick(10);
		
		 String PDocCount = getProductionDocCount().getText();
         int Doc = Integer.parseInt(PDocCount);
         System.out.println(Doc); 
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getReviewproductionButton().Visible()  ;}}), Input.wait30); 
		getReviewproductionButton().Click();
		
		String location = getDestinationPathText().getText();
        System.out.println(location);
        Thread.sleep(7000);
        String doccount = getDoc_Count().getText();
        System.out.println(doccount);
        
         base.isFileDownloaded(location+"\\VOL0001"+"\\PDF"+"\\0001",4);
         ProdPathsomeredact= location+"\\VOL0001"+"\\PDF"+"\\0001"+"\\"+""+a+".pdf";
         System.out.println(ProdPathsomeredact);
        
	    }
    
  	public void addANewProduction(String productionName) throws InterruptedException {
    	
  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  				 getAddNewProductionbutton().Visible()  ;}}), Input.wait30); 
  		getAddNewProductionbutton().Click();

  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  				getProductionName().Visible()  ;}}), Input.wait30); 
  		getProductionName().SendKeys(productionName);
  		
  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  				getProductionDesc().Visible()  ;}}), Input.wait30); 
  		getProductionDesc().SendKeys(productionName);
  		
  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  				getBasicInfoMarkComplete().Visible()  ;}}), Input.wait30); 
  		getBasicInfoMarkComplete().Click();
  	    }
  	    
  	    public void fillingDATSection() {

  			driver.WaitUntil((new Callable<Boolean>() {
  			public Boolean call() {return getDATChkBox().Enabled();}}), Input.wait30);
  			getDATChkBox().Click();

  			driver.WaitUntil((new Callable<Boolean>() {public Boolean call() {return getDATTab().Visible();}}), Input.wait30);
  			getDATTab().Click();

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
  			getNativeChkBox().Click();
  			
  			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  					getNativeTab().Enabled()  ;}}), Input.wait30); 
  			getNativeTab().Click();
  			
  			driver.scrollPageToTop();
  			
  			System.out.println(getNative_text().getText());
  			Assert.assertEquals(getNative_text().getText(),"To produce specific docs"
  			+ " natively, please select file types and/or tags below. In addition,"
  			+ " to export placeholders for these docs, configure placeholder in the TIFF "
  			+ "or PDF section for the same selected file types and/or tags.");
  			
  			Assert.assertEquals(getNative_text_Color().GetAttribute("class"),"blue-text");
  			
  			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  					getNative_SelectAllCheck().Enabled()  ;}}), Input.wait30); 
  			getNative_SelectAllCheck().Click();
  			
  			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  					getNative_AdvToggle().Enabled()  ;}}), Input.wait30); 
  			getNative_AdvToggle().Click();
  			
  			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  					getNative_GenerateLoadFileLST().Enabled()  ;}}), Input.wait30); 
  			getNative_GenerateLoadFileLST().Click();
  			Thread.sleep(2000);

  			 
  			}
  			
  			public void fillingTIFFSection(String tagnameprev,String tagnametech) throws InterruptedException{

  			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return getTIFFChkBox().Enabled() ;}}), Input.wait30);
  			getTIFFChkBox().Click();
  						
 		    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return getTIFFTab().Enabled() ;}}), Input.wait30);
  		    getTIFFTab().Click();
  			
  		    driver.scrollPageToTop();
  		    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return getTIFF_CenterHeaderBranding().Visible() && getTIFF_CenterHeaderBranding().Enabled() ;}}), Input.wait30);
  		    getTIFF_CenterHeaderBranding().Click();
  			
  			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return getTIFF_EnterBranding().Enabled() ;}}), Input.wait30);
  				
  			new Actions(driver.getWebDriver()).moveToElement(getTIFF_EnterBranding().getWebElement()).click();
  			getTIFF_EnterBranding().SendKeys("Test");
  		    Thread.sleep(2000);
  		    
  		    driver.scrollingToBottomofAPage();
  			
  			getPriveldge_SelectTagButton().waitAndClick(10);
  			Thread.sleep(2000);
  			
  			getPriveldge_TagTree(tagnameprev).waitAndClick(10);
  			Thread.sleep(2000);
  			
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
            getTechissue_toggle().Click();
    		  
  			getTechissue_SelectTagButton().waitAndClick(10);
  			Thread.sleep(2000);
  			
  			getPriveldge_TagTree(tagnametech).waitAndClick(10);
  			Thread.sleep(2000);
  			
  			getPriveldge_TagTree_SelectButton().waitAndClick(10);
  			Thread.sleep(2000);
  			
  			
  			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  					getPriveldge_TextArea().Enabled()  ;}}), Input.wait30); 
  			new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();
  			
  		    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return getPriveldge_TextArea().Enabled() ;}}), Input.wait30);
  		    new Actions(driver.getWebDriver()).moveToElement(getPriveldge_TextArea().getWebElement()).click();	
  	    	getPriveldge_TextArea().SendKeys("testing");
  			
  			}
  			
  			public void fillingPDFSection(String prefixId, String suffixId, String tagname) throws InterruptedException{
  				
  				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  						getTextChkBox().Enabled()  ;}}), Input.wait30); 
  				getTextChkBox().Click();
  						
  				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  						getPDFChkBox().Enabled() ;}}), Input.wait30); 
  				getPDFChkBox().Click();		
  				
  				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  						getPDFTab().Enabled()  ;}}), Input.wait30); 
  				getPDFTab().Click();
  				
  				driver.scrollPageToTop();
  				
  				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  						getPDF_CenterHeaderBranding().Visible() &&  getTIFF_CenterHeaderBranding().Enabled() ;}}), Input.wait30); 
  				getPDF_CenterHeaderBranding().ScrollTo();
  				getPDF_CenterHeaderBranding().Click();
  				
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
  				getComponentsMarkComplete().Click();
  				
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
  				getKeepFamiliesTogether().Click();

  			}
  			
  			public void navigateToNextSection(){
  				
  				driver.scrollPageToTop();
  				
  				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return getMarkCompleteLink().Enabled() ;}}), Input.wait30);
  				getMarkCompleteLink().Click();
  				
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
  					getIncludeFamilies().Click();
  					
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
  					getOkButton().Click();
  					
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
  					getbtnProductionLocationMarkComplete().Click();
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnProductionLocationNext().Enabled()  ;}}), Input.wait30); 
  					getbtnProductionLocationNext().Click();
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getPreviewprod().Enabled()  ;}}), Input.wait30); 
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnProductionSummaryMarkComplete().Visible()  ;}}), Input.wait30); 
  					getbtnProductionSummaryMarkComplete().Click();
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnProductionSummaryNext().Enabled()  ;}}), Input.wait30); 
  					getbtnProductionSummaryNext().Click();
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnProductionGenerate().Visible()  ;}}), Input.wait30); 
  					getbtnProductionGenerate().Click();
  					
  					
  					
  					System.out.println("Wait until regenerate is enabled");
  					for (int i = 0; i < 120; i++)
  					{
  						try
  						{
  							
  							
  							this.driver.getWebDriver().get(Input.url+"Production/Home");
  					    	getProductionLink().waitAndClick(5);
  							getbtnGenerateMarkComplete().waitAndClick(5);
  							System.out.println("Generated");
  							break;
  							
  						}
  						catch (Exception e)
  						{
  							Thread.sleep(10000);
  							driver.Navigate().refresh();
  							
  						
  						}
  					}
  				
  				
  					String batesno = getProd_BatesRange().getText();
  					System.out.println(batesno);
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnSummaryNext().Enabled()  ;}}), Input.wait30); 
  					getbtnSummaryNext().Click();
  					//Thread.sleep(10000);
  				
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getConfirmProductionCommit().Enabled()  ;}}), Input.wait30); 
  					getConfirmProductionCommit().waitAndClick(10);
  					
  					 String PDocCount = getProductionDocCount().getText();
  			         int Doc = Integer.parseInt(PDocCount);
  			         System.out.println(Doc); 
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getReviewproductionButton().Visible()  ;}}), Input.wait30); 
  					getReviewproductionButton().Click();
  					
  					String location = getDestinationPathText().getText();
  			        System.out.println(location);

  								
  				}
  				
  	   public void FillingallsectionsProduction(String productionname,String PrefixID,String SuffixID,final String foldername) 
  			    		throws InterruptedException{
  					
				  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				  							getComponentsMarkComplete().Enabled()  ;}}), Input.wait30); 
				  	getComponentsMarkComplete().Click();
				  					
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
  					getKeepFamiliesTogether().Click();
  					
  					driver.scrollPageToTop();
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getNumAndSortMarkComplete().Enabled()  ;}}), Input.wait30); 
  					getNumAndSortMarkComplete().Click();
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getNumAndSortNext().Enabled() ;}}), Input.wait30); 
  					getNumAndSortNext().Click();
  					
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
  					getIncludeFamilies().Click();
  					
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
  					getOkButton().Click();
  					
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
  					getbtnProductionLocationMarkComplete().Click();
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnProductionLocationNext().Enabled()  ;}}), Input.wait30); 
  					getbtnProductionLocationNext().Click();
  					
//  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//  							getPreviewprod().Enabled()  ;}}), Input.wait30); 
//  					getPreviewprod().Click();
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnProductionSummaryMarkComplete().Visible()  ;}}), Input.wait30); 
  					getbtnProductionSummaryMarkComplete().Click();
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnProductionSummaryNext().Enabled()  ;}}), Input.wait30); 
  					getbtnProductionSummaryNext().Click();
  					
  					driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  							getbtnProductionGenerate().Visible()  ;}}), Input.wait30); 
  					getbtnProductionGenerate().Click();
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
//  					getbtnSummaryNext().Click();
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
//  					getReviewproductionButton().Click();
//  					
//  					String location = getDestinationPathText().getText();
//  			        System.out.println(location);
//  			        Thread.sleep(2000);
  			        
  			    	}
  	
  	   public void savetemplate(String templatename)
  	   {
  		 this.driver.getWebDriver().get(Input.url+"Production/Home");
		getProdStateFilter().WaitUntilPresent();
		getProdStateFilter().selectFromDropdown().selectByVisibleText("COMPLETED");
	    getprod_ActionButton().waitAndClick(20);
	    getprod_Action_SaveTemplate().waitAndClick(10);
	    	
	    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						getprod_Templatetext().Visible()  ;}}), Input.wait30); 
	    getprod_Templatetext().SendKeys(templatename);
	    getProdExport_SaveButton().waitAndClick(5);
	    base.VerifySuccessMessage("Production Saved as a Custom Template.");
	   }

  		public void ProductionwithNatives(String productionname,String PrefixID,
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
  	  		getAddNewProductionbutton().Click();
  	  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	  				getProductionName().Visible()  ;}}), Input.wait30); 
  	  		getProductionName().SendKeys(productionname);
  	  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	  				getProductionDesc().Visible()  ;}}), Input.wait30); 
  	  		getProductionDesc().SendKeys(productionname);
  	  	  getprod_LoadTemplate().selectFromDropdown().selectByVisibleText("Template (Production)");
  	  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	  				getBasicInfoMarkComplete().Visible()  ;}}), Input.wait30); 
  	  		getBasicInfoMarkComplete().Click();
  	  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getComponentsMarkComplete().Enabled()  ;}}), Input.wait30); 
       getComponentsMarkComplete().Click();
  	  	    }
		
  		public void ProductionwithTechIssuetags(String productionname,String PrefixID,
  				String SuffixID,final String foldername,String Tagnametech,String Tagnameprev) 
  				throws InterruptedException 
	
  		{
  			 addANewProduction(productionname);
  			 getBasicInfoNext();
  			 fillingDATSection();
  			 fillingTIFFSection(Tagnameprev, Tagnametech);
  			 
  			 driver.scrollingToBottomofAPage();
  			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  					getTextChkBox().Enabled()  ;}}), Input.wait30); 
  			getTextChkBox().Click();
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
  	       		getFilterByCOMPLETED().Visible()  ;}}), Input.wait30); 
  	        getFilterByCOMPLETED().waitAndClick(10);
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
  	       		getFilterByCOMPLETED().Visible()  ;}}), Input.wait30); 
  	        getFilterByCOMPLETED().waitAndClick(10);
  	        getRefreshButton().waitAndClick(10);

  	        String tileitems = getProductionItemsTileItems().getText();
  	        System.out.println(tileitems);
  	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	    			getGridView().Visible()  ;}}), Input.wait30);
  	    	getGridView().Click();
  	    	getRefreshButton().waitAndClick(10);
  	    	Thread.sleep(3000);
  	    	String griditems = getProductionItemsGridItems().getText();
  	    	System.out.println(griditems);
  	    	Assert.assertTrue(griditems.contains(tileitems));
	   		System.out.println("Verified Tile Production and Grid Production are eqal");
	   		
  	    }

  	    public void SaveTemplate(final String tempName) throws InterruptedException{
  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			getFilterByButton().Visible()  ;}}), Input.wait30);
	    	getFilterByButton().waitAndClick(10);

	       	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	       		getFilterByCOMPLETED().Visible()  ;}}), Input.wait30); 
	        getFilterByCOMPLETED().waitAndClick(10);
	        getRefreshButton().waitAndClick(10);
	        
	        //System.out.println("Number of records in a current page : "+getProductionItemsTile().size());
	       	        
	        if(getProductionItemsTile().size()>0)
	        {
	        	getArrow().waitAndClick(10);
	        	getSaveTemplate().waitAndClick(10);
	        	
	        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	        			getTemplateName().Visible()  ;}}), Input.wait30); 
	        	getTemplateName().SendKeys(tempName);
	        	
	        	getSave().waitAndClick(10);
	        	BaseClass bc= new BaseClass(driver);
	        	bc.VerifySuccessMessage("Production Saved as a Custom Template.");
	        	bc.CloseSuccessMsgpopup();
	        	getManageTemplates().waitAndClick(10);
		        	
	        	ArrayList<String> tableele = new ArrayList<String>();
		   		java.util.List<WebElement> table = getCustomTemplates().FindWebElements();
				for (int i = 1; i<getCustomTemplates().size();i++) {
					tableele.add(table.get(i).getText());
	   		 	}
				Assert.assertTrue(tableele.contains(tempName));
				System.out.println("Verified saved template under Custom template");
	        }
  	  }

  	    public void DeleteTemplate(final String tempName) throws InterruptedException{
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	    			getFilterByButton().Visible()  ;}}), Input.wait30);
  	    	getFilterByButton().waitAndClick(10);

  	       	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	       		getFilterByCOMPLETED().Visible()  ;}}), Input.wait30); 
  	        getFilterByCOMPLETED().waitAndClick(10);
  	        getRefreshButton().waitAndClick(10);
  	        
  	        System.out.println("Number of records in a current page : "+getProductionItemsTile().size());
  	       	        
  	        if(getProductionItemsTile().size()>0)
  	        {
  	        	getArrow().waitAndClick(10);
  	        	getSaveTemplate().waitAndClick(10);
  	        	
  	        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	        			getTemplateName().Visible()  ;}}), Input.wait30); 
  	        	getTemplateName().SendKeys(tempName);
  	        	
  	        	getSave().waitAndClick(10);
  	        	BaseClass bc= new BaseClass(driver);
  	        	bc.VerifySuccessMessage("Production Saved as a Custom Template.");
  	        	bc.CloseSuccessMsgpopup();
  	        	getManageTemplates().waitAndClick(10);
  		        	
  	        	ArrayList<String> tableele = new ArrayList<String>();
  		   		java.util.List<WebElement> table = getCustomTemplates().FindWebElements();
  				for (int i = 1; i<getCustomTemplates().size();i++) {
  					tableele.add(table.get(i).getText());
  	   		 	}
  				//System.out.println(tableele);
  				Assert.assertTrue(tableele.contains(tempName));
  				
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
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			getFilterByButton().Visible()  ;}}), Input.wait30);
	    	getFilterByButton().waitAndClick(10);
	       	for(int i=1; i<getFilterOptions().size(); i++) {
	       	getFilter(i).waitAndClick(10);
	       	}
	      
	       	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	       		getFilterByCOMPLETED().Visible()  ;}}), Input.wait30); 
	        getFilterByCOMPLETED().waitAndClick(10);
	        
	        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	        		getRefreshButton().Visible()  ;}}), Input.wait30);
	        getRefreshButton().waitAndClick(10);
	        
	        Thread.sleep(1000);
	        getArrow().waitAndClick(10);
	        
	        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	        		getLock().Visible()  ;}}), Input.wait30); 
	        getLock().waitAndClick(10);
	        Thread.sleep(1000);
	        getOK().waitAndClick(10);
	        Thread.sleep(1000);
	        BaseClass bc= new BaseClass(driver);
        	bc.VerifySuccessMessage("Production Lock Successfully.");
        	bc.CloseSuccessMsgpopup();
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			getFilterByButton().Visible()  ;}}), Input.wait30);
	    	getFilterByButton().waitAndClick(10);
	       	for(int i=1; i<getFilterOptions().size(); i++) {
	       	getFilter(i).waitAndClick(10);
	       	}
        	
	       	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		       		getFilterByCOMPLETED().Visible()  ;}}), Input.wait30); 
		    getFilterByCOMPLETED().waitAndClick(10);
		    
		    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	        		getRefreshButton().Visible()  ;}}), Input.wait30);
	        getRefreshButton().waitAndClick(10);
	        Thread.sleep(1000);
        	String locktxt = getLockIcon().GetAttribute("class");
        	Assert.assertTrue(locktxt.contains("lock"));
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
	       		getFilterByCOMPLETED().Visible()  ;}}), Input.wait30); 
	        getFilterByCOMPLETED().waitAndClick(10);
	        
	        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	        		getRefreshButton().Visible()  ;}}), Input.wait30);
	        getRefreshButton().waitAndClick(10);
	        

	        getArrow().waitAndClick(10);
	        
	        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	        		getSaveTemplate().Visible()  ;}}), Input.wait30); 
	        getSaveTemplate().waitAndClick(10);
	        
	        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getTemplateName().Visible()  ;}}), Input.wait30); 
        	getTemplateName().SendKeys(tempName);
        	
        	getSave().waitAndClick(10);
        	BaseClass bc= new BaseClass(driver);
        	bc.VerifySuccessMessage("Production Saved as a Custom Template.");
        	bc.CloseSuccessMsgpopup();
        	
        	getManageTemplates().waitAndClick(10);
        	
        	ArrayList<String> tableele = new ArrayList<String>();
	   		java.util.List<WebElement> table = getCustomTemplates().FindWebElements();
			for (int i = 1; i<getCustomTemplates().size();i++) {
				tableele.add(table.get(i).getText());
   		 	}
			Assert.assertTrue(tableele.contains(tempName));
			System.out.println("Verified saved template under Custom template");
	        
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getPRODUCTIONSEXPORTSTabs().Visible()  ;}}), Input.wait30); 
			getPRODUCTIONSEXPORTSTabs().waitAndClick(10);
			
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getAddNewProductionbutton().Visible()  ;}}), Input.wait30); 
        	getAddNewProductionbutton().waitAndClick(10);
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	  				getProductionName().Visible()  ;}}), Input.wait30); 
  	  		getProductionName().SendKeys(productionName);
  	  		
  	  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	  				getProductionDesc().Visible()  ;}}), Input.wait30); 
  	  		getProductionDesc().SendKeys(productionName);
  	  		
  	  		String myString = tempName.concat(" (Production)");
  	  		getLoadTemplate().selectFromDropdown().selectByVisibleText(myString);
  	  		
  	  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	  			getBasicInfoSave().Visible()  ;}}), Input.wait30); 
  	  		getBasicInfoSave().waitAndClick(10);
  	  		
  	  		this.driver.getWebDriver().get(Input.url+"Production/Home");
  	  		
  	  		getArrow().waitAndClick(10);
  	  		
  	  		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	  				getOpenWizard().Visible()  ;}}), Input.wait30); 
  	  		getOpenWizard().waitAndClick(10);
  	  	
        	System.out.println("Verified Customized Template");
    	  }

  	    public void VerifyProductionSet(final String prodsetName) throws InterruptedException{
	    	
	  	    	getProdExport_ProductionSets().waitAndClick(10);
	        	
	        	String myString = prodsetName.concat(" (Production Set)");
	        			
	        	getProdExport_ProductionSets().selectFromDropdown().selectByVisibleText(myString);
	        	
	        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	        			getPRODUCTIONSSETName().Visible()  ;}}), Input.wait30); 
	        	String myprodsetname = getPRODUCTIONSSETName().getText();
	        	
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
	       		getFilterByCOMPLETED().Visible()  ;}}), Input.wait30); 
	        getFilterByCOMPLETED().waitAndClick(10);
	        
	        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	        		getRefreshButton().Visible()  ;}}), Input.wait30);
	        getRefreshButton().waitAndClick(10);
	        
	        Thread.sleep(1000);
	        getArrow().waitAndClick(10);
	        
	        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	        		getTileDelete().Visible()  ;}}), Input.wait30); 
       
	      	String deletetiletxt = getTileDelete().GetAttribute("class");
	      	Assert.assertTrue(deletetiletxt.contains("disable"));
	      	
	      	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	    			getGridView().Visible()  ;}}), Input.wait30);
  	    	getGridView().Click();
  	    	
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
	      	Assert.assertTrue(deletegridtxt.contains("disable"));
	
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
			getBasicInfoMarkComplete().Click();
			
			Thread.sleep(5000);
	  		
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getTIFFChkBox().Enabled() ;}}), Input.wait30); 
			getTIFFChkBox().Click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getTIFFTab().Enabled()  ;}}), Input.wait30); 
			getTIFFTab().Click();
			
			driver.scrollingToBottomofAPage();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getadvance().Visible()  ;}}), Input.wait30); 
			getadvance().Click();
			
			driver.scrollingToBottomofAPage();
			
			System.out.println("Verified Advance Show/Hide Button wworking as expected");
			
//			Assert.assertTrue(getGenerateLoadFile().Enabled());
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getGenerateLoadFile().Enabled() ;}}), Input.wait30); 
			getGenerateLoadFile().Click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getGenerateLoadFile().Visible() ;}}), Input.wait30); 
			getGenerateLoadFile().Click();
	  		
			System.out.println("Verified Generate Load File (LST) with Toggle button");
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getSlipSheets().Visible() ;}}), Input.wait30); 
			getSlipSheets().Click();
			
			for(int i=1;i<=3;i++)
			{
			getAvailableFields(i).Click();
			}
	  			  	
	  		System.out.println("Verified METADATA/WORKPRODUCT & CALCULATED Tabs");
  	  }
  	    
    public void DeleteProduction() throws InterruptedException{
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
        
        Thread.sleep(1000);
        getArrow().waitAndClick(10);
        
        driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        		getDelete().Visible()  ;}}), Input.wait30); 
        getDelete().waitAndClick(10);
        Thread.sleep(1000);
        getOK().waitAndClick(10);
        Thread.sleep(1000);
        BaseClass bc= new BaseClass(driver);
      	bc.VerifySuccessMessage("Production deleted successfully");
      	bc.CloseSuccessMsgpopup();
      	
      	System.out.println("Verified deleted Production");
    }
    
    public void selectDATWithDefaultValue() 
    		throws InterruptedException {
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDATChkBox().Visible()  ;}}), Input.wait30); 
		getDATChkBox().Click();

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDATTab().Visible()  ;}}), Input.wait30); 
 		getDATTab().Click();

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDAT_FieldClassification1(" [value='BATES']").Visible()  ;}}), Input.wait30); 
		getDAT_FieldClassification1(" [value='BATES']").Click();

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDAT_SourceField1(" [value='-146']").Visible()  ;}}), Input.wait30); 
		getDAT_SourceField1(" [value='-146']").Click();

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDAT_DATField1().Visible()  ;}}), Input.wait30); 
		getDAT_DATField1().SendKeys("BatesNumber");

//		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//				getDATTab().Visible()  ;}}), Input.wait30); 
//		getDATTab().Click();
    }
    
    public void selectMP3WithLSTOff() 
        		throws InterruptedException {
    	
    	Thread.sleep(1200);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProductionAdvanced().Visible()  ;}}), Input.wait30); 
		getProductionAdvanced().Click();

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getMP3ChkBox().Visible()  ;}}), Input.wait30); 
		getMP3ChkBox().Click();
        
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getMP3Tab().Visible()  ;}}), Input.wait30); 
        getMP3Tab().Click();
        
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getMP3ComponentAdvanced().Visible()  ;}}), Input.wait30); 
        getMP3ComponentAdvanced().Click();
        
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getMP3AdvancedList().Visible()  ;}}), Input.wait30); 
        getMP3AdvancedList().Click();
    }
    
    public void openNextBatesNumbersDialog() throws InterruptedException {
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getClickHereLink().Displayed()  ;}}), Input.wait30); 

		getClickHereLink().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNextBatesNumbersDialog().Displayed()  ;}}), Input.wait30); 
    }
  	    
}

