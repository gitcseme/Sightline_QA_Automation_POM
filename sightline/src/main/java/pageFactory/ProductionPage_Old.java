package pageFactory;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import org.openqa.selenium.interactions.Actions;

import com.google.common.io.Files;

import automationLibrary.Driver;
import automationLibrary.Element;
import testScriptsSmoke.Input;

public class ProductionPage_Old {

    Driver driver;
    BaseClass base;
    
    public Element getproductionmenu() {  return driver.FindElementByXPath("//label[text()='Productions']"); }
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
    public Element getPriveldge_TagTree(String tag){ return driver.FindElementByXPath("//*[@id='tagTreeTIFFComponent']//a[contains(text(),'"+tag+"')]"); }
    public Element getPriveldge_PDFTagTree(String tag){ return driver.FindElementByXPath("//*[@id='tagTreePDFComponent']//a[contains(text(),'"+tag+"')]"); }
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
    
 
    
    public ProductionPage_Old(Driver driver){

        this.driver = driver;
//        this.driver.getWebDriver().get(Input.url+"Production/Home");
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
		
		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getBasicInfoNext().Enabled() ;}}), Input.wait30); getBasicInfoNext().Click();
		 */
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDATChkBox().Enabled()  ;}}), Input.wait30); 
		getDATChkBox().Click();
		
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
    	
    	//getTIFF_EnableforPrivilegedDocs().ScrollTo();
    	
    	/*driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTIFF_EnableforPrivilegedDocs().Enabled()  ;}}), Input.wait30); */
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
	//	getPreviewprod().Click();
		
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
		
	    }
    

 
	
}
    

   
