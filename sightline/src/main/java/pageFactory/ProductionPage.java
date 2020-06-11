package pageFactory;

import java.awt.List;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.Callable;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import de.redsix.pdfcompare.PdfComparator;
import testScriptsSmoke.Input;
import org.apache.commons.io.FileUtils;
import com.google.common.io.Files;

public class ProductionPage {

    Driver driver;
    BaseClass base;
    public static String ProdPath1;
    public static String ProdPath2;
    
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
    public Element getConfirmProductionCommit(){ return driver.FindElementByXPath(".//*[@id='btnProductionConfirmation']/strong"); }
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
    public Element getTIFF_SelectRedtags(){ return driver.FindElementByXPath("//*[@id='RedactionTagsTree']//a[contains(text(),'R2')]"); }   
    public Element getTIFF_SelectRedtags_SelectButton(){ return driver.FindElementByXPath("//*[@id='myModal']//button[@title='Select']"); }   
    public Element getTIFF_Red_Placeholdertext(){ return driver.FindElementByXPath("//*[@id='divRedaction']//div[@class='redactor-editor']/p"); }   
    public Element getTIFF_SelectRed_Radiobutton(){ return driver.FindElementByXPath("//*[@id='chkTIFFSpecifytRedactions']/following-sibling::i"); }   
    public Element getPDF_SelectRed_Radiobutton(){ return driver.FindElementByXPath("//*[@id='chkPDFSPecifytRedactions']/following-sibling::i"); }   
        
    public Element getDoc_Count(){ return driver.FindElementByXPath("//*[@id='frmProductionConfirmation']//div[@class='drk-gray-widget']/span[1]"); }   
    public Element getProd_Uncommitbutton(){ return driver.FindElementByXPath("//strong[contains(text(),'Uncommit Production')]"); }   

    
 
    
    public ProductionPage(Driver driver){

        this.driver = driver;
        this.driver.getWebDriver().get(Input.url+"Production/Home");
        driver.waitForPageToBeReady();
        base = new BaseClass(driver);
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
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getPreviewprod().Enabled()  ;}}), Input.wait30); 
		getPreviewprod().Click();
		
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
				
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getBasicInfoNext().Enabled() ;}}), Input.wait30); 
		getBasicInfoNext().Click();
		
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
		
		getPriveldge_PDFTagTree(tagname).waitAndClick(10);
		Thread.sleep(2000);
		
		getPriveldge_PDFTagTree_SelectButton().waitAndClick(5);
		Thread.sleep(2000);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getPriveldge_TextArea().Enabled()  ;}}), Input.wait30); 
		new Actions(driver.getWebDriver()).moveToElement(getPriveldge_PDFTextArea().getWebElement()).click();
		
		getPriveldge_PDFTextArea().SendKeys("testing");
		
		getPDF_BurnRedtoggle().waitAndClick(10);
		
	/*	getPDF_SpecifyRedactText().waitAndClick(10);
		
		
		getPDF_SelectRedtagbuton().waitAndClick(10);
		Thread.sleep(5000);
		
		getPDF_SelectRedtags("R1").waitAndClick(10);
		
		getPDF_SelectRedtags_SelectButton().waitAndClick(10);
		System.out.println(getPDF_Red_Placeholdertext().getText()); 
		
		Assert.assertEquals(getPDF_Red_Placeholdertext().getText(),"REDACTED");*/
		
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
        
        ProdPath1= location+"\\VOL0001"+"\\PDF"+"\\0001"+"\\"+""+a+".pdf";
        System.out.println(ProdPath1);
      
        
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
         ProdPath2= location+"\\VOL0001"+"\\PDF"+"\\0001"+"\\"+""+a+".pdf";
         System.out.println(ProdPath2);
        
	    }
    
      public void TestPDFCompare() throws Exception
    {    		
    	   copyfiles();
    	   String result="C:\\Users\\smangal\\Documents\\Production Files\\result.pdf";
    	   String file1="C:\\Users\\smangal\\Documents\\Production Files\\PDF1.pdf";
    	   String file2="C:\\Users\\smangal\\Documents\\Production Files\\PDF2.pdf";
    	  
    	   new PdfComparator(file1, file2).compare().writeTo(result);
    	   
    	   System.out.println("process completed");
    	   boolean isEquals = new PdfComparator(file1, file2).compare().writeTo(result);
    	   System.out.println("Are PDF files similar..."+isEquals);

    }
      

  	public void copyfiles() throws Exception
  	{
  		Path src2 = Paths.get("\\\\MTPVTSSLMQ01\\Productions\\Automation\\P657944\\VOL0001\\PDF\\0001\\A_7786590000000100_P128632.pdf");
  		Path src1 = Paths.get("\\\\MTPVTSSLMQ01\\Productions\\Automation\\P278843\\VOL0001\\PDF\\0001\\B_5684910000000100_N278731.pdf");
  		
  		//Path src1 = Paths.get(ProdPath1);
  		//Path src2 = Paths.get(ProdPath2);
  	    Path dest1 = Paths.get("C:\\Users\\smangal\\Documents\\Production Files\\PDF1.pdf"); 
  	    Path dest2 = Paths.get("C:\\Users\\smangal\\Documents\\Production Files\\PDF2.pdf"); 
  	    
  	  //delete last TCs PDF from PDFs folder
        File file = new File("C:\\Users\\smangal\\Documents\\Production Files");      
        String[] myFiles;    
            if(file.isDirectory()){
                myFiles = file.list();
                for (int i=0; i<myFiles.length; i++) {
                    File myFile = new File(file, myFiles[i]); 
                    myFile.delete();
                }
             }
    	  
  	    Files.copy(src1.toFile(), dest1.toFile());
  	    Files.copy(src2.toFile(), dest2.toFile());
  	    System.out.println("Copied file into another location successfully");
  			
  	}

}
    

   
