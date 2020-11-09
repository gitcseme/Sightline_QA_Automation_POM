package pageFactory;

import java.util.List;
import java.util.concurrent.Callable;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class SessionSearch {

    Driver driver;
    public static int pureHit;
    BaseClass base;
  
    public Element getNewSearch(){ return driver.FindElementByXPath("//button[@id='add_tab']"); }
    public String EnterSearchStringLocator = ".//*[@id='xEdit']/li/input";
    public Element getEnterSearchString(){ return driver.FindElementByXPath(EnterSearchStringLocator); }
    public Element getEditSearchString(int index){ return driver.FindElementByXPath(String.format("(//*[@id='xEdit'])[%s]/li[2]",index)); }
    public Element getSearchString(int index){ return driver.FindElementByXPath(String.format("(//*[@id='xEdit'])[%s]",index)); }
    public Element getSearchButton(){ return driver.FindElementById("btnBasicSearch"); }
    public String QuerySearchButton = "//*[@id='qSearch']";
    public Element getQuerySearchButton(){ return driver.FindElementById("qSearch"); }
    public Element getSaveAsNewSearchRadioButton(){ return driver.FindElementByXPath("//*[@id='saveAsNewSearchRadioButton']/following-sibling::i"); }
   
    //Hits
    public String PureHitsCountLocator = ".//*[@id='001']/span/count";
    public Element getPureHitsCount(){ return driver.FindElementByXPath(PureHitsCountLocator); }
    public Element getPureHitsCountwithOptions(){ return driver.FindElementByXPath(".//*[@id='001']/count"); } 
    public Element getPureHitsCount2ndSearch(){ return driver.FindElementByXPath("(//*[@id='001']/span/count)[2]"); }
    public Element getTDHitsCount(){ return driver.FindElementByXPath(".//*[@id='002']/span/count"); }
    public Element getNDHitsCount(){ return driver.FindElementByXPath(".//*[@id='003']/span/count"); }
    public Element getFMHitsCount(){ return driver.FindElementByXPath(".//*[@id='004']/span/count"); }
    public Element getCSHitsCount(){ return driver.FindElementByXPath(".//*[@id='005']/span/count"); }
    
    public Element getNewSearchPureHitsCount(int num){ return driver.FindElementByXPath("(//*[@id='001']/span/count)["+num+"]"); }
    public Element getThreadedCount(){ return driver.FindElementByXPath(".//*[@id='002']/span/count"); }
    public Element getNearDupeCount(){ return driver.FindElementByXPath(".//*[@id='003']/span/count"); }
    public Element getFamilyCount(){ return driver.FindElementByXPath(".//*[@id='004']/span/count"); }
    public Element getConceptualCount(){ return driver.FindElementByXPath(".//*[@id='005']/span/count"); }
    public Element getSaveSearchButton(){ return driver.FindElementByXPath("SaveSearch_Button"); }
    public Element getPureHitAddButton(){ return driver.FindElementByXPath(".//*[@id='001']/i[2]"); }
  
    //Bulk tag and folder
    public Element getBulkActionButton(){ return driver.FindElementById("idAction"); }
    public Element getBulkTagAction(){ return driver.FindElementByXPath("//a[contains(text(),'Bulk Tag')]"); }
    public Element getTagsAllRoot(){ return driver.FindElementByXPath("//*[@id='tagsJSTree']//*[@id='-1_anchor']"); }
    
    public Element getEnterTagName(){ return driver.FindElementById("txtTagName"); }
    public Element getBulkFolderAction(){ return driver.FindElementByXPath("//a[contains(text(),'Bulk Folder')]"); }
    public Element getBulkNewTab(){ return driver.FindElementById("tabNew"); }
    public Element getBulkTagConfirmationButton(){ return driver.FindElementByXPath("//button[contains(text(),'Ok')]"); }
    
    public Element getFolderAllRoot(){ return driver.FindElementByXPath("//*[@id='folderJSTree']//*[@id='-1_anchor']"); }
    public Element getEnterFolderName(){ return driver.FindElementById("txtFolderName"); }
    public Element getContinueCount(){ return driver.FindElementByXPath("//div[@class='bulkActionsSpanLoderTotal']"); }
    public Element getContinueButton(){ return driver.FindElementByXPath(".//*[@id='divBulkAction']//button[contains(.,'Continue')]"); }
    public Element getFinalCount(){ return driver.FindElementByXPath("//span[@id='spanTotal']"); }
    public Element getFinalizeButton(){ return driver.FindElementById("btnfinalizeAssignment"); }
    public Element getFolderTab(){ return driver.FindElementByXPath("//a[contains(text(),'Folders')]"); }
    public Element getToggleDocCount(){ return driver.FindElementByXPath("folderDocCount"); }
    public Element getSuccessMsgHeader(){ return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span"); }
    public Element getSuccessMsg(){ return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p"); }
  
    //Actions
    public Element getas_Action_Exportdata(){ return driver.FindElementByXPath("//*[@id='ddlbulkactions']//a[text()='Export Data']"); }
    public Element getDocListAction(){ return driver.FindElementByCssSelector("[id=DoclistOpt] a"); }
    public Element getDocViewAction(){ return driver.FindElementByCssSelector("[id=DocviewOpt] a"); }
    public Element getDocViewActionDL(){ return driver.FindElementByXPath("//a[contains(.,'View in DocView')]"); }
    
    public Element getTallyResults(){ return driver.FindElementByXPath("//*[@id='ddlbulkactions']//a[contains(.,'Tally Results')]"); }
    public Element getBulkAssignAction(){ return driver.FindElementByXPath("//*[@id='ddlbulkactions']//a[contains(.,'Bulk Assign')]"); }
    public Element getBulkReleaseAction(){ return driver.FindElementByXPath("//*[@id='ddlbulkactions']//a[contains(.,'Bulk Release')]"); }
    public Element getBulkReleaseActionDL(){ return driver.FindElementByXPath("//a[contains(.,'Bulk Release')]"); }
        
    //save search    
    public Element getSaveSearch_Button(){ return driver.FindElementById("btnSaveSearch"); }
    public Element getSaveSearch_Button(int index){ return driver.FindElementByXPath(String.format("(//*[@id='btnSaveSearch'])[%s]",index)); }
    public ElementCollection getAllSaveSearch_Button(){ return driver.FindElementsByXPath(String.format("(//*[@id='btnSaveSearch'])")); }
    public Element getAdvanceS_SaveSearch_Button(){ return driver.FindElementByXPath("//div[@class='searchInput smart-accordion-default']//div[@class='row']//*[@id='qSave']"); }
    
    public Element getSavedSearch_MySearchesTab(){ return driver.FindElementById("-1_anchor"); }
    public Element getSaveSearch_Name(){ return driver.FindElementById("txtSaveSearchName"); }
    public Element getSaveSearch_SaveButton(){ return driver.FindElementById("btnEdit"); }
    public Element getSaveSearch_CancelButton(){ return driver.FindElementByXPath("//button[contains(text(),'Cancel')]"); }
    public Element getBulkRelDefaultSecurityGroup_CheckBox(String SG){ return driver.FindElementByXPath(".//*[@id='Edit User Group']//div[text()='"+SG+"']/../div[1]/label/i"); }
    public Element getBulkRelOther_CheckBox(String SGname){ return driver.FindElementByXPath(".//*[@id='Edit User Group']//div[text()='"+SGname+"']/../div[1]/label/i"); }
    public Element getBulkRelease_ButtonRelease(){ return driver.FindElementById("btnRelease"); }
    public Element getBulkRelease_ButtonUnrelease() {return driver.FindElementById("btnUnrelease");}
    
    //Metadata
    public Element getBasicSearch_MetadataBtn(){ return driver.FindElementById("metadataHelper"); }
    public Element getBasicSearch_MetadataBtn(int index){ return driver.FindElementByXPath(String.format("(//*[@id='metadataHelper'])[%s]",index)); }
    public ElementCollection getAllBasicSearch_MetadataBtn(){ return driver.FindElementsByXPath("(//*[@id='metadataHelper'])"); }
    public Element getAdvanceSearch_MetadataBtn(){ return driver.FindElementByXPath("(//*[@id='metadataHelper'])[2]"); }
    public Element getSelectMetaData(){ return driver.FindElementById("metatagSelect"); }
    public Element getMetaOption(){ return driver.FindElementById("selectOp"); }
    public Element getMetaDataSearchText1(){ return driver.FindElementById("val1"); }
    public Element getMetaDataSearchText2(){ return driver.FindElementById("val2"); }
    public Element getMetaDataEditSearchBtn(){ return driver.FindElementByXPath("//*[@id='val1']/../../../../div[2]/button"); }
    public Element getMetaDataInserQuery(){ return driver.FindElementById("insertQueryBtn"); }
    public String MetaDataCancelButtonXPath = "//*[@id='insertmetadataPop']//a[@id='close']";
   
    //Advance Search
    public String AdvanceLabelXPath = "//*[@id='Adv']//span[contains(text(),'Advanced')]";
    public Element getAdvancedLabel() { return driver.FindElementByXPath(AdvanceLabelXPath); }
    public Element getAdvancedSearchLink(){ return driver.FindElementByXPath("//*[@id='advancedswitch']"); }
    public String ContentAndMetaDatabtnXPath = "//button[@id='contentmetadata']";
    public Element getContentAndMetaDatabtn(){ return driver.FindElementByXPath(ContentAndMetaDatabtnXPath); }
    public Element getWorkproductBtn(){ return driver.FindElementByXPath("//button[@id='workproduct']"); }
    public Element getSavedSearchBtn(){ return driver.FindElementById("savedSearchesHelper"); }
    //public Element getSavedSearchName(String savedSearchName){ return driver.FindElementByXPath("//a[@class='jstree-anchor'][contains(text(),'"+savedSearchName+"')]"); }
    public Element getSavedSearchName(String savedSearchName){ return driver.FindElementByXPath(String.format("//span[@data-savedname='%s']/..",savedSearchName)); }
    public ElementCollection getTree(){ return driver.FindElementsByXPath("//a[@class='jstree-anchor'][contains(text(),'')]"); }
    public ElementCollection getSecurityNamesTree(){ return driver.FindElementsByXPath("//*[@id='JSTree']/div/label"); }
    
    //advanced Content search
//  public String AdvancedContentSearchInputXPath = "//*[@id='c-1']//*[@id='contentmetadata']//*[@id='xEdit']/li/input[@autocomplete='on']";
  public String AdvancedContentSearchInputXPath = "//*[@id='contentmetadata']//*[@id='xEdit']/li/input[@autocomplete='on']";
    public Element getAdvancedContentSearchInput(){ return driver.FindElementByXPath(AdvancedContentSearchInputXPath); }
    
    //Audio Search
    public Element getAs_Audio(){ return driver.FindElementById("audio"); }
    public Element getAs_AudioLanguage(){ return driver.FindElementById("audioLanguage"); }
    public Element getAs_AudioText(){ return driver.FindElementByXPath("(//*[@id='xEdit']/li/input)[3]"); }
    
    //Advance search combinations
    public Element getAs_AudioText_Combination(){ return driver.FindElementByXPath("(//*[@id='xEdit']/li/input)[6]"); }
    public Element getAs_SelectOperation(int condition){ return driver.FindElementByXPath("(//*[@id='op']/section/div[1]/label/select)["+condition+"]"); }
   
    //Conceptual
    public Element getAs_Conceptual(){ return driver.FindElementByXPath("//button[@id='conceptual']"); }
    public Element getAs_ConceptualTextArea(){ return driver.FindElementByXPath("//textarea"); }
  
    public Element getSelectFolderExisting(String folderName){ return driver.FindElementByXPath("//*[@id='divBulkFolderJSTree']//a[contains(.,'"+folderName+"')]/i[1]"); }
    public Element getBulkUntagbutton(){ return driver.FindElementByXPath("//*[@id='toUnassign']/following-sibling::i"); }
    public Element getSelectTagExisting(String tagName){ return driver.FindElementByXPath("//*[@id='divBulkTagJSTree']//a[contains(.,'"+tagName+"')]"); }
    public Element getBulkUnFolderbutton(){ return driver.FindElementByXPath("//*[@id='toUnfolder']/following-sibling::i"); }
    public Element getWP_FolderBtn(){ return driver.FindElementById("foldersHelper"); }
    public Element getWP_TagBtn(){ return driver.FindElementById("tagsHelper"); }
    public Element getWP_assignmentsBtn(){ return driver.FindElementById("assignmentsHelper"); }
   
    public Element getWP_SelectFolderName(String FolderName){ return driver.FindElementByXPath("//a[@class='jstree-anchor'][contains(text(),'"+FolderName+"')]"); }
    public Element getWP_SelectTagName(String TagName){ return driver.FindElementByXPath("//a[@class='jstree-anchor'][contains(text(),'"+TagName+"')]"); }
    public Element getWP_SelectRedactionName(String redactName){ return driver.FindElementByXPath("//a[@class='jstree-anchor'][contains(text(),'"+redactName+"')]"); }
    public Element getWP_SelectSGName(String sgName){ return driver.FindElementByXPath("//a[@class='jstree-anchor'][contains(text(),'"+sgName+"')]"); }
    public Element getWP_SelectProductionName(String productionName){ return driver.FindElementByXPath("//a[@class='jstree-anchor'][contains(text(),'"+productionName+"')]"); }
    public Element getRedactionBtn(){ return driver.FindElementById("redactionsHelper"); }
    public Element getProductionBtn(){ return driver.FindElementById("productionsHelper"); }
    public Element getSecurityGrpBtn(){ return driver.FindElementById("SecurityGroupsHelper"); }
    public Element getOperatorDD(){ return driver.FindElementByXPath("(//*[@id='Tabs']//button[contains(text(),'Operator')])[2]"); }
    public Element getOperatorDD(int index){ return driver.FindElementByXPath(String.format("(//*[@id='Tabs']//button[contains(text(),'Operator')])[%s]",index)); }
    public Element getOperatorAND(){ return driver.FindElementByXPath("(//*[@id='opAND'])[2]"); }
    public Element getOperatorAND(int index){ return driver.FindElementByXPath(String.format("(//*[@id='opAND'])[%s]",index)); }
    public Element getOperatorOR(){ return driver.FindElementByXPath("(//*[@id='opOR'])[2]"); }
    public Element getOperatorOR(int index){ return driver.FindElementByXPath(String.format("(//*[@id='opOR'])[%s]",index)); }
    public Element getOperatorNOT(){ return driver.FindElementByXPath("(//*[@id='opNOT'])[2]"); }
    public Element getOperatorNOT(int index){ return driver.FindElementByXPath(String.format("(//*[@id='opNOT'])[%s]",index)); }
    
    //Conceptual Search right, left and mid
    public Element getConceptualRight(){ return driver.FindElementByXPath("//span[@class='irs-line-right']"); }
    public Element getConceptualLeft(){ return driver.FindElementByXPath("//span[@class='irs-line-left']"); }
    public Element getConceptualMid(){ return driver.FindElementByXPath("//span[@class='irs-line-mid']"); }
    
    
    //Audio
    public Element getAudioTermOperator(){ return driver.FindElementByXPath("//*[@id='operator']"); }
    public Element getAudioLocation(){ return driver.FindElementByXPath("//*[@id='location']"); }
    public Element getAudioTimeDiff(){ return driver.FindElementByXPath("//*[@id='timeDiff']"); }
    
    //Doclist
    public Element getTallyContinue(){ return driver.FindElementByXPath("//*[@id='bot1-Msg1']"); } 
    
    
    //search query alerts
    public Element getQueryAlertGetText(){ return driver.FindElementByXPath("//*[@id='Msg1']/div/div[1]"); } 
    public Element getQueryAlertGetTextSingleLine(){ return driver.FindElementByXPath("//*[@id='Msg1']/div/p"); } 
    public Element getQueryPossibleWrongAlertContinueButton() {return driver.FindElementById("bot1-Msg1");}
    
  //shilpi
    public Element getSaveSearch_AdvButton(){ return driver.FindElementByXPath(".//*[@id='tabsResult-1']//a[@id='qSave']"); }
    
    //conceptually playBtn
    public Element getConceptuallyPlayButton(){ return driver.FindElementByXPath("//*[contains(@id,'playButton')]"); }
    
    //modify advanced search
    public String ModifySearchLocator = "(.//*[@id='qModifySearch'])";
    public Element getModifyASearch(){ return driver.FindElementById(ModifySearchLocator); }
  //added by shilpi
    public Element getThreadedAddButton(){ return driver.FindElementByXPath(".//*[@id='002']/i[2]"); }
    public Element getNearDupesAddButton(){ return driver.FindElementByXPath(".//*[@id='003']/i[2]"); }
    public Element getFamilyAddButton(){ return driver.FindElementByXPath(".//*[@id='004']/i[2]"); }
    public Element getConceptAddButton(){ return driver.FindElementByXPath(".//*[@id='005']/i[2]"); }

  //added by shilpi on 17-07
    public Element getRemovedocsfromresult(){ return driver.FindElementByXPath("//*[@class='fa fa-times-circle ui-removeTile']"); }
    public Element getsavesearch_overwrite(){ return driver.FindElementByXPath("//*[@id='overwriteRadioButton']/following-sibling::i"); }
   
    
    //Info messages/ tool tips
    public Element getSingleToolTipIcon(){ return driver.FindElementByXPath("//*[@id='single']/section/label/i"); }
    public Element getRangeToolTipIcon1(){ return driver.FindElementByXPath("//*[@id='rangeVal']/div[1]/section/label/i"); }
    public Element getRangeToolTipIcon2(){ return driver.FindElementByXPath("//*[@id='rangeVal']/div[2]/section/label/i"); }
   
    //tool tip messages
    public Element getRangeToolTipIconFirst(){ return driver.FindElementByXPath("//*[@id='rangeVal']/div[1]/section/label/b"); }
    public Element getRangeToolTipIconSecond(){ return driver.FindElementByXPath("//*[@id='rangeVal']/div[2]/section/label/b"); }
    
    //displayed in black background
    public Element getSingleToolTipText(){ return driver.FindElementByXPath("//*[@id='single']/section/label/b"); }
    public Element getRangeToolTip1Text(){ return driver.FindElementByXPath("//*[@id='rangeVal']/div[1]/section/label/b"); }
    public Element getRangeToolTip2Text(){ return driver.FindElementByXPath("//*[@id='rangeVal']/div[2]/section/label/b"); }
   
    //second search
    public Element getCopyTo(){ return driver.FindElementByXPath("//*[@id='Basic']/div[1]/div/div[2]/div[1]/button"); }
    public Element getCopyToNewSearch(){ return driver.FindElementByXPath("//*[@id='Basic']/div[1]/div/div[2]/div[1]/ul/li[2]/a"); }
    public Element getSecondSearchBtn(){ return driver.FindElementByXPath("(//*[@id='btnBasicSearch'])[2]"); }
    public Element getSecondPureHit(){ return driver.FindElementByXPath("(//*[@id='001']/span/count)[2]"); }
    
    //Quick Batch added by shilpi
    public Element getQuickBatchAction(){ return driver.FindElementByXPath("//a[contains(text(),'Quick Batch')]"); }
    
    //search options
    public Element getadvoption_family(){ return driver.FindElementByXPath("//*[@id='chkIncludeFamilyMember']/following-sibling::i"); }
    public Element getadvoption_near(){ return driver.FindElementByXPath("//*[@id='chkIncludeNearDuplicate']/following-sibling::i"); }
    public Element getadvoption_threaded(){ return driver.FindElementByXPath("//*[@id='chkIncludeThreadedDocuments']/following-sibling::i"); }
    
    //Assignment distribution list
    public ElementCollection getadwp_assgn_distributedto(){ return driver.FindElementsById("dist"); }
    public Element getadwp_assgn_status(){ return driver.FindElementById("statusSel"); }
   // public Element getadvoption_threaded(){ return driver.FindElementByXPath("//*[@id='chkIncludeThreadedDocuments']/following-sibling::i"); }
    
    // 200913
    public Element getPageTitle() {return driver.FindElementByCssSelector("h1.page-title"); }
    public Element getHelpTip() {return driver.FindElementByCssSelector("a.helptip[data-original-title='Searching Help']"); }
    public Element getUniqueCount(){ return driver.FindElementByCssSelector("h1.page-title span label"); }
    public String SearchQueryTextXpath = "//*[@id='xEdit']/li";
    public Element getSearchQueryText(int listItem){ return driver.FindElementByXPath(String.format("(//*[@id='xEdit']/li)[%s]",listItem+1)); }
    public Element getSearchQueryText(){ return driver.FindElementByCssSelector("#xEdit li"); }
    public String RemoveSearchQueryXPath = "(//*[@id='xEdit']//a[@href='#'])";
    public String RemoveSearchQueryLocator = "#xEdit li.textboxlist-bit a.textboxlist-bit-box-deletebutton[href='#']";
    public Element getRemoveSearchQuery() { return driver.FindElementByCssSelector(RemoveSearchQueryLocator); }
    
    public Element getSearchTable() {return driver.FindElementById("sessionSearchList");}
    public ElementCollection getSearchTabName() { return driver.FindElementsByXPath("//span[@class = 'SrchText']");}
    public ElementCollection getMetaDataSearchButtons() {return driver.FindElementsById("metadataHelper");}
    public ElementCollection getSaveSearchButtons() {return driver.FindElementsById("btnSaveSearch");}
    public ElementCollection getSearchButtons() {return driver.FindElementsById("btnBasicSearch");}
    public ElementCollection getSavedQueryButtons() {return driver.FindElementsByXPath("//a[contains(@class,  'liClick ui-tabs-anchor')]");}
    public Element getQueryText2(int i) {return driver.FindElementByXPath(String.format("(//li[contains(@class, 'textboxlist-bit-box-deletable')]/span)[%d]",i+1));}
    public ElementCollection getQueryTextBoxes() {return driver.FindElementsByXPath("(//li[contains(@class, 'textboxlist-bit-box-deletable')]/span)");}
    public ElementCollection setQueryText() {return driver.FindElementsByXPath("(//li[contains(@class, 'textboxlist-bit textboxlist-bit-editable')])/input");}
    public Element getSearchTableResults() {return driver.FindElementById("taskbasic");}
    public ElementCollection getAllSearchTableResults() {return driver.FindElementsById("taskbasic");}

    public Element getMessageBoxButtonSection() { return driver.FindElementById("MessageBoxButtonSection");}
    public Element getOperatorDropDown() {return driver.FindElementByXPath("//button[@class = 'btn btn-default dropdown-toggle insertOpHelper']");}
    public ElementCollection getOperatorDropdown() {return driver.FindElementsByXPath("//button[text()='Operator']");}
    public ElementCollection getOperatorDropDownOP(String op) {return driver.FindElementsByXPath(String.format("//a[@id='op%s']", op));}
    public Element getResultsTab() {return driver.FindElementById("resultsTabs");}
    public Element getSearchAutoCompletePopup() {return driver.FindElementById("ui-id-2");}
    public Element getDocsThatMetCriteriaAddBtn() { return driver.FindElementByXPath("//a[@id='001']//i[@class='fa fa-plus-circle ui-addTile addTile']"); }
    public Element getResultTile() { return driver.FindElementByXPath("//div[@id='resultsCart']//li[contains(@id, 'results')]"); }

    public Element getSearchDocsResults() { return driver.FindElementById("countCount-2871-001");}
    
    public ElementCollection getSearchResultDocsMetCriteriaPlusButton() { return driver.FindElementsByCssSelector("[id=gallery] li [data-original-title='Docs That Met Your Criteria'] i.fa-plus-circle");}
    public ElementCollection getSearchResultDocsmetCriteriaCount() {return driver.FindElementsByCssSelector("[id=gallery] li [data-original-title='Docs That Met Your Criteria'] count");}
    public ElementCollection getSearchDocumentMatchNumber() { return driver.FindElementsByCssSelector("#gallery li count");}
    public ElementCollection getSearchResultsTableRows() {return driver.FindElementsByCssSelector("#taskbasic>tbody>tr");}
    

    // SavedSearchList
    public Element getSavedSearchList() { return driver.FindElementById("sessionSearchList"); }
    public String AutoSuggestXPath = "//li[@class='ui-menu-item']/div";
    public Element getAutoSuggest() { return driver.FindElementByCssSelector("ul.ui-ete li.ui-menu-item a"); }
    public Element isSearchInProgress() { return driver.FindElementById("imgLoadPM"); }

    public SessionSearch(Driver driver){
    	this(Input.url, driver);
    }
    public SessionSearch(String url, Driver driver){

    	this.driver = driver;
        this.driver.getWebDriver().get(url+ "Search/Searches");
        base = new BaseClass(driver);
        //This initElements method will create all WebElements
        //PageFactory.initElements(driver.getWebDriver(), this);

    }
    //Similar to the overloaded Function of the same name
    //This one does not rely on the appropriate ListItem Index, instead deleting the first avaliable item
    public Element removeSearchQueryRemove(){ 
    	Element removeLineItem = getActiveElementByXPath(SearchQueryTextXpath); 
		Actions builder=new Actions(driver.getWebDriver());
		// Mouse hover to see X button
		builder.moveToElement(removeLineItem.getWebElement()).perform();
		Element removeSearchQuery = getActiveElementByXPath(RemoveSearchQueryXPath);
		return removeSearchQuery;		
//		return getRemoveSearchQuery();		
    }

    public String getToolTipMsgBS(String isOrRange, String metaDataField) {
    	driver.getWebDriver().get(Input.url+ "Search/Searches");
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getEnterSearchString().Visible()  ;}}), Input.wait30);
    	getBasicSearch_MetadataBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectMetaData().Visible()  ;}}), Input.wait30); 
		
	    try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		getSelectMetaData().selectFromDropdown().selectByVisibleText(metaDataField);
		
		
    	 // Create action class object
		Actions builder=new Actions(driver.getWebDriver());
		if(isOrRange.equalsIgnoreCase("IS")){
			getSingleToolTipIcon().waitAndClick(10);
				// Mouse hover to that text message
				builder.moveToElement(getSingleToolTipText().getWebElement()).perform();
				return getSingleToolTipText().getText();
		}
		else if (isOrRange.equalsIgnoreCase("RANGE")){
			getMetaOption().selectFromDropdown().selectByVisibleText("RANGE");
			getRangeToolTipIcon1().waitAndClick(10);
			builder.moveToElement(getRangeToolTip1Text().getWebElement()).perform();
			String first = getRangeToolTip1Text().getText();
			builder.moveToElement(getRangeToolTip2Text().getWebElement()).perform();
			String second = getRangeToolTip1Text().getText();
			Assert.assertEquals(first, second);
			return first;
		}
		
		return null;
	}
    
    public String getToolTipMsgAS(String isOrRange, String metaDataField) {
    	
    	driver.getWebDriver().get(Input.url+ "Search/Searches");
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAdvancedSearchLink().Visible()  ;}}), Input.wait30); 
    	getAdvancedSearchLink().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getContentAndMetaDatabtn().Visible()  ;}}), Input.wait30); 
    	getContentAndMetaDatabtn().Click();
        //Enter seatch string
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAdvanceSearch_MetadataBtn().Visible()  ;}}), Input.wait30); 
    	getAdvanceSearch_MetadataBtn().Click();
	    try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		getSelectMetaData().selectFromDropdown().selectByVisibleText(metaDataField);
		
		
    	 // Create action class object
		Actions builder=new Actions(driver.getWebDriver());
		if(isOrRange.equalsIgnoreCase("IS")){
			getSingleToolTipIcon().waitAndClick(10);
				// Mouse hover to that text message
				builder.moveToElement(getSingleToolTipText().getWebElement()).perform();
				return getSingleToolTipText().getText();
		}
		else if (isOrRange.equalsIgnoreCase("RANGE")){
			getMetaOption().selectFromDropdown().selectByVisibleText("RANGE");
			getRangeToolTipIcon1().waitAndClick(10);
			builder.moveToElement(getRangeToolTip1Text().getWebElement()).perform();
			String first = getRangeToolTip1Text().getText();
			builder.moveToElement(getRangeToolTip2Text().getWebElement()).perform();
			String second = getRangeToolTip1Text().getText();
			Assert.assertEquals(first, second);
			return first;
		}
		
		return null;
	}

    public Element getActiveButtonById(String id) {
    	return getActiveElementByXPath(String.format("//*[@id='%s']",id));
    }
    
    public Element getActiveElementByXPath(String xPath) {
    	Element activeBtn = null;
    	ElementCollection coll = driver.FindElementsByXPath(String.format("(%s)",xPath));;
    	
    	for (int c=1; c<coll.size()+1;c++) {
    		Element btn = driver.FindElementByXPath(String.format("(%s)[%s]",xPath,c));
			if (btn.Visible()) {
				activeBtn = btn;
				break;
			}
    	}

    	return activeBtn;
    }
    public Element getActiveSaveSearch_Button() {
    	Element activeBtn = null;
    	ElementCollection coll = getAllSaveSearch_Button();
    	
    	for (int c=1; c<coll.size()+1;c++) {
    		Element btn = getSaveSearch_Button(c);
			if (btn.Visible()) {
				activeBtn = btn;
				break;
			}
    	}

    	return activeBtn;
    }
    
    public void initiateSaveSearch(String searchName) {
    	try{
    		getActiveSaveSearch_Button().waitAndClick(5);
    		//getSaveSearch_Button().waitAndClick(5);
    	}catch (Exception e) {
    		getAdvanceS_SaveSearch_Button().waitAndClick(5);
		}
    	
    	try{
    		getSaveAsNewSearchRadioButton().waitAndClick(5);
        	}catch (Exception e) {
        		System.out.println("Radio button already selected");
    		}
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSavedSearch_MySearchesTab().Visible()  ;}}), Input.wait60); 
    	getSavedSearch_MySearchesTab().Click();
    	getSaveSearch_Name().SendKeys(searchName);
    	
    }
    public void cancelSaveSearch(String searchName) {
    	initiateSaveSearch(searchName);
    	getSaveSearch_CancelButton().Click();
    	System.out.println("Cancelled search save with name - "+searchName);
    }  
    public void saveSearch(String searchName) {
    	initiateSaveSearch(searchName);
    	getSaveSearch_SaveButton().Click();
    	VerifySuccessMessage("Saved search saved successfully");
    	System.out.println("Saved search with name - "+searchName);
	}
    public void VerifySuccessMessage(String msg) {
		base.VerifySuccessMessage(msg);
		base.CloseSuccessMsgpopup();
    }

    public void wrongQueryAlertBasicSaerch(String SearchString, int MessageNumber, String fielded, String fieldName) {
    	
    	
    	driver.getWebDriver().get(Input.url+ "Search/Searches");
        //Enter seatch string
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getEnterSearchString().Visible()  ;}}), Input.wait30); 
    	
    	//
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getBasicSearch_MetadataBtn().Visible()  ;}}), Input.wait30); 
    	if(fielded.equalsIgnoreCase("fielded")){
    		//
    		getBasicSearch_MetadataBtn().Click();
    		
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getSelectMetaData().Visible()  ;}}), Input.wait30); 
    		
    	    try {
    			Thread.sleep(1500);
    		} catch (InterruptedException e) {
    			
    			e.printStackTrace();
    		}
    		getSelectMetaData().selectFromDropdown().selectByVisibleText(fieldName);
    		getMetaDataSearchText1().SendKeys(SearchString);
    		getMetaDataInserQuery().Click();
    		
    		//
    	}
    	else 
    	  	getEnterSearchString().SendKeys(SearchString) ;

        //Click on Search button
    	getSearchButton().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    	    	getQueryAlertGetText().Visible() ;}}), 10);
    	//System.out.println(getQueryAlertGetText().getText());  
    	if(MessageNumber == 1)
    		Assert.assertEquals("Wildcard characters in proximity searches and in phrase searches are not supported. Please contact your project manager to help structure your search needs.",getQueryAlertGetText().getText()); 
    	if(MessageNumber == 2){
    		String msg= "Your query contains at least one lowercase \"and\", \"or\" or \"not\" word. In Sightline, lowercase \"and\", \"or\" or \"not\" are treated as terms, whereas capitalized \"AND\", \"OR\" or \"NOT\" are treated as operators.Does your query reflect your intent? Click YES to continue with your search as is, or NO to cancel your search so you can edit the syntax.";
        	Assert.assertEquals(msg.replaceAll(" ", ""),getQueryAlertGetText().getText().replaceAll(" ", "").replaceAll("\n",""));
    	}
    	if(MessageNumber == 3){
        	String msg= "Your query contains an argument that may be intended to look for dates within any body content or metadata attribute. Please be advised that the search engine interprets dash - or slash / characters in non-fielded arguments as implied OR statement. For example, 1980/01/02 is treated by the search engine as 1980 OR 01 OR 02. The same is true of 1980-01-02. You can add quotation marks around a query to treat the dash or slash argument as a string. For example, \"1980/01/02\" is treated as is. The same is true of \"1980-01-02\".Does your query reflect your intent? Click YES to continue with your search as is, or NO to cancel your search so you can edit the syntax.";
        	Assert.assertEquals(msg.replaceAll(" ", ""),getQueryAlertGetText().getText().replaceAll(" ", "").replaceAll("\n","")); 
        	}
    	if(MessageNumber == 4)
            Assert.assertEquals("YOUR QUERY CONTAINS A HYPHEN CHARACTER \"-\" THAT IS INTERPRETED IN DIFFERENT WAYS BY THE SEARCH ENGINE DEPENDING ON ITS USE IN THE QUERY\n\nIf the hyphen character is part of a string that is enveloped within quotations, such as â€œbi-weekly reportâ€, the hyphen character is treated literally, returning documents that hit on that exact phrase.\n\nIf the hyphen character is not part of a string enveloped in quotations, and is preceded or succeeded by a space, then the hyphen character will be interpreted as a space, which is an implied OR operator (ex. bi - weekly will be interpreted as bi OR weekly).\n\nIf the hyphen character is not part of a string enveloped in quotations, and is the first character of an argument (ie. not separated by a space from an argument), then the search engine will interpret the hyphen as a NOT operator (ex. bi -weekly is interpreted as bi NOT weekly)\n\nBased on this information, is your query what you intended? If this is what you intended, please click YES to execute your search. If this is not what you intended, please click NO and correct your query.",getQueryAlertGetText().getText()); 
    	if(MessageNumber == 5){
        	String msg= "Invalid parenthesis balance, please ensure all the braces have proper parenthesis balance.";
        	 
        	Assert.assertEquals(msg.replaceAll(" ", ""),getQueryAlertGetTextSingleLine().getText().replaceAll(" ", "").replaceAll("\n","")); 
        	}
    	if(MessageNumber == 6){
        		String msg= "One or more of your Proximity Queries has only a single Term. This could be as a result of extra Double Quotes around terms or the use of Parenthesis which group multiple values as a single term.";
        	
            
        	Assert.assertEquals(msg.replaceAll(" ", ""),getQueryAlertGetTextSingleLine().getText().replaceAll(" ", "").replaceAll("\n","")); 
        	}
    	if(MessageNumber == 7){
        		String msg= "Your query may contain an invalid Proximity Query. Any Double Quoted phrases as part of a Proximity Query must also be wrapped in Parentheses, e.g. \"Term1 (\"Specific Phrase\")\"~4.";
        	Assert.assertEquals(msg.replaceAll(" ", ""),getQueryAlertGetTextSingleLine().getText().replaceAll(" ", "").replaceAll("\n","")); 
        	}
    	if(MessageNumber == 8){
        	String msg= "Your query has multiple potential syntax issues.1. Your query contains a ~ (tilde) character, which does not invoke a stemming search as dtSearch in Relativity does. If you want to perform a stemming search, use the trailing wildcard character (ex. cub* returns cubs, cubicle, cubby, etc.). To perform a proximity search in Sightline, use the ~ (tilde) character (ex. \"gas transportation\"~4 finds all documents where the word gas and transportation are within 4 words of each other.)2. Your query contains the ~ (tilde) character which does not immediately follow a double-quoted set of terms or is not immediately followed by a numeric value . If you are trying to run a proximity search, please use appropriate proximity query syntax e.g. \"Term1 Term2\"~4. Note there is no space before or after the tilde.Does your query reflect your intent? Click YES to continue with your search as is, or NO to cancel your search so you can edit the syntax.";
        	Assert.assertEquals(msg.replaceAll(" ", ""),getQueryAlertGetText().getText().replaceAll(" ", "").replaceAll("\n","")); 
        	}
    	
    	if(MessageNumber == 9){
        	String msg= "There is a trailing operator not followed by an argument, please verify the search syntax.";
        	Assert.assertEquals(msg.replaceAll(" ", ""),getQueryAlertGetTextSingleLine().getText().replaceAll(" ", "").replaceAll("\n","")); 
        	}
    	
    	if(MessageNumber == 10){
        	String msg= "Your query contains two or more arguments that do not have an operator between them. In Sightline, each term without an operator between them will be treated as A OR B, not \"A B\" as an exact phrase. If you want to perform a phrase search, wrap the terms in quotations (ex. \"A B\" returns all documents with the phrase A B).Does your query reflect your intent? Click YES to continue with your search as is, or NO to cancel your search so you can edit the syntax.";
        	Assert.assertEquals(msg.replaceAll(" ", ""),getQueryAlertGetText().getText().replaceAll(" ", "").replaceAll("\n","")); 
        	}
	//click on ok
   /* if(MessageNumber == 1)
	getTallyContinue().Click();
    else*/
   
    	
	}
    public void wrongQueryAlertAdvanceSaerch(String SearchString, int MessageNumber, String fielded, String fieldName) {
    	

    		driver.getWebDriver().get(Input.url+ "Search/Searches");
        
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getAdvancedSearchLink().Visible()  ;}}), Input.wait30); 
        	getAdvancedSearchLink().Click();
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getContentAndMetaDatabtn().Visible()  ;}}), Input.wait30); 
        	getContentAndMetaDatabtn().Click();
            //Enter seatch string
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        			getAdvanceSearch_MetadataBtn().Visible()  ;}}), Input.wait30); 
        	if(fielded.equalsIgnoreCase("fielded")){
        		//
        		getAdvanceSearch_MetadataBtn().Click();
        		
        		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        				getSelectMetaData().Visible()  ;}}), Input.wait30); 
        		
        	    try {
        			Thread.sleep(1500);
        		} catch (InterruptedException e) {
        	
        			e.printStackTrace();
        		}
        		getSelectMetaData().selectFromDropdown().selectByVisibleText(fieldName);
        		getMetaDataSearchText1().SendKeys(SearchString);
        		getMetaDataInserQuery().Click();
        		
        		//
        	}
        	else 
        	 getAdvancedContentSearchInput().SendKeys(SearchString) ;

            //Click on Search button
        	getQuerySearchButton().Click();
        	
        	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
        	    	getQueryAlertGetText().Visible() ;}}), 10);
        	//System.out.println(getQueryAlertGetText().getText());  
        	if(MessageNumber == 1)
        	Assert.assertEquals("Wildcard characters in proximity searches and in phrase searches are not supported. Please contact your project manager to help structure your search needs.",getQueryAlertGetText().getText()); 
        	if(MessageNumber == 2){
        		String msg= "Your query contains at least one lowercase \"and\", \"or\" or \"not\" word. In Sightline, lowercase \"and\", \"or\" or \"not\" are treated as terms, whereas capitalized \"AND\", \"OR\" or \"NOT\" are treated as operators.Does your query reflect your intent? Click YES to continue with your search as is, or NO to cancel your search so you can edit the syntax.";
            	Assert.assertEquals(msg.replaceAll(" ", ""),getQueryAlertGetText().getText().replaceAll(" ", "").replaceAll("\n",""));
        	}
        	if(MessageNumber == 3){
        		String msg= "Your query contains an argument that may be intended to look for dates within any body content or metadata attribute. Please be advised that the search engine interprets dash - or slash / characters in non-fielded arguments as implied OR statement. For example, 1980/01/02 is treated by the search engine as 1980 OR 01 OR 02. The same is true of 1980-01-02. You can add quotation marks around a query to treat the dash or slash argument as a string. For example, \"1980/01/02\" is treated as is. The same is true of \"1980-01-02\".Does your query reflect your intent? Click YES to continue with your search as is, or NO to cancel your search so you can edit the syntax.";
        	 
        	Assert.assertEquals(msg.replaceAll(" ", ""),getQueryAlertGetText().getText().replaceAll(" ", "").replaceAll("\n","")); 
        	}
        	if(MessageNumber == 4)
                Assert.assertEquals("YOUR QUERY CONTAINS A HYPHEN CHARACTER \"-\" THAT IS INTERPRETED IN DIFFERENT WAYS BY THE SEARCH ENGINE DEPENDING ON ITS USE IN THE QUERY\n\nIf the hyphen character is part of a string that is enveloped within quotations, such as â€œbi-weekly reportâ€, the hyphen character is treated literally, returning documents that hit on that exact phrase.\n\nIf the hyphen character is not part of a string enveloped in quotations, and is preceded or succeeded by a space, then the hyphen character will be interpreted as a space, which is an implied OR operator (ex. bi - weekly will be interpreted as bi OR weekly).\n\nIf the hyphen character is not part of a string enveloped in quotations, and is the first character of an argument (ie. not separated by a space from an argument), then the search engine will interpret the hyphen as a NOT operator (ex. bi -weekly is interpreted as bi NOT weekly)\n\nBased on this information, is your query what you intended? If this is what you intended, please click YES to execute your search. If this is not what you intended, please click NO and correct your query.",getQueryAlertGetText().getText()); 
              
        	if(MessageNumber == 5){
            	String msg= "Invalid parenthesis balance, please ensure all the braces have proper parenthesis balance.";
            	 
            	Assert.assertEquals(msg.replaceAll(" ", ""),getQueryAlertGetTextSingleLine().getText().replaceAll(" ", "").replaceAll("\n","")); 
            	}
        	if(MessageNumber == 6){
            		String msg= "One or more of your Proximity Queries has only a single Term. This could be as a result of extra Double Quotes around terms or the use of Parenthesis which group multiple values as a single term.";
            	
                
            	Assert.assertEquals(msg.replaceAll(" ", ""),getQueryAlertGetTextSingleLine().getText().replaceAll(" ", "").replaceAll("\n","")); 
            	}
        	if(MessageNumber == 7){
            		String msg= "Your query may contain an invalid Proximity Query. Any Double Quoted phrases as part of a Proximity Query must also be wrapped in Parentheses, e.g. \"Term1 (\"Specific Phrase\")\"~4.";
            	Assert.assertEquals(msg.replaceAll(" ", ""),getQueryAlertGetTextSingleLine().getText().replaceAll(" ", "").replaceAll("\n","")); 
            	}
        	if(MessageNumber == 8){
            	String msg= "Your query has multiple potential syntax issues.1. Your query contains a ~ (tilde) character, which does not invoke a stemming search as dtSearch in Relativity does. If you want to perform a stemming search, use the trailing wildcard character (ex. cub* returns cubs, cubicle, cubby, etc.). To perform a proximity search in Sightline, use the ~ (tilde) character (ex. \"gas transportation\"~4 finds all documents where the word gas and transportation are within 4 words of each other.)2. Your query contains the ~ (tilde) character which does not immediately follow a double-quoted set of terms or is not immediately followed by a numeric value . If you are trying to run a proximity search, please use appropriate proximity query syntax e.g. \"Term1 Term2\"~4. Note there is no space before or after the tilde.Does your query reflect your intent? Click YES to continue with your search as is, or NO to cancel your search so you can edit the syntax.";
            	Assert.assertEquals(msg.replaceAll(" ", ""),getQueryAlertGetText().getText().replaceAll(" ", "").replaceAll("\n","")); 
            	}
        	
        	if(MessageNumber == 9){
            	String msg= "There is a trailing operator not followed by an argument, please verify the search syntax.";
            	Assert.assertEquals(msg.replaceAll(" ", ""),getQueryAlertGetTextSingleLine().getText().replaceAll(" ", "").replaceAll("\n","")); 
            	}
        	
        	if(MessageNumber == 10){
            	String msg= "Your query contains two or more arguments that do not have an operator between them. In Sightline, each term without an operator between them will be treated as A OR B, not \"A B\" as an exact phrase. If you want to perform a phrase search, wrap the terms in quotations (ex. \"A B\" returns all documents with the phrase A B).Does your query reflect your intent? Click YES to continue with your search as is, or NO to cancel your search so you can edit the syntax.";
            	Assert.assertEquals(msg.replaceAll(" ", ""),getQueryAlertGetText().getText().replaceAll(" ", "").replaceAll("\n","")); 
            	}
        	
        	if(MessageNumber == 11){
            	String msg= "Your query has multiple potential syntax issues.1. Your query contains a ~ (tilde) character, which does not invoke a stemming search as dtSearch in Relativity does. If you want to perform a stemming search, use the trailing wildcard character (ex. cub* returns cubs, cubicle, cubby, etc.). To perform a proximity search in Sightline, use the ~ (tilde) character (ex. \"gas transportation\"~4 finds all documents where the word gas and transportation are within 4 words of each other.)2. Your query contains the ~ (tilde) character which does not immediately follow a double-quoted set of terms or is not immediately followed by a numeric value . If you are trying to run a proximity search, please use appropriate proximity query syntax e.g. \"Term1 Term2\"~4. Note there is no space before or after the tilde.Does your query reflect your intent? Click YES to continue with your search as is, or NO to cancel your search so you can edit the syntax.";
            	Assert.assertEquals(msg.replaceAll(" ", ""),getQueryAlertGetText().getText().replaceAll(" ", "").replaceAll("\n","")); 
            	}
        	if(MessageNumber == 12){
            	String msg= "Search queries with wildcard characters within phrases are not supported. Alternatively, you could get the same results by performing a proximity search with a distance of zero. For example, a search \"trade contr*\" can be equivalently reconstructed as \"trade contr*\"~0! to get the same results. Please reach out to Support or your administrator for any additional help.";
            	Assert.assertEquals(msg.replaceAll(" ", ""),getQueryAlertGetTextSingleLine().getText().replaceAll(" ", "").replaceAll("\n","")); 
            	}
    	//click on ok
       /* if(MessageNumber == 1)
    	getTallyContinue().Click();
        else*/
        driver.getWebDriver().navigate().refresh();	
    	

	}
    
    public void enterBasicContentSearchString(String SearchString) {
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getActiveElementByXPath(EnterSearchStringLocator).Visible()  ;}}), Input.wait30); 
    	getActiveElementByXPath(EnterSearchStringLocator).SendKeys(SearchString) ;
    }
    
    //Function to perform content search for a given search string
    public int basicContentSearch(String SearchString){

    	//To make sure we are in basic search page
    	driver.getWebDriver().get(Input.url+ "Search/Searches");
    	
        //Enter search string
    	enterBasicContentSearchString(SearchString);
    	
        //Click on Search button
    	getSearchButton().Click();
    	
    	//verify counts for all the tiles
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    	getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait120);
    	
    	int pureHit = Integer.parseInt(getPureHitsCount().getText());
    	System.out.println("Search is done for "+SearchString+" and PureHit is : "+pureHit);
    	
    	return pureHit;
   }
    //Function to perform content search for a given search string
      public int advancedContentSearch(String SearchString){
    	
    	//To make sure we are in basic search page
    	driver.getWebDriver().get(Input.url+ "Search/Searches");
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAdvancedSearchLink().Visible()  ;}}), Input.wait30); 
    	getAdvancedSearchLink().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getContentAndMetaDatabtn().Visible()  ;}}), Input.wait30); 
    	getContentAndMetaDatabtn().Click();
        //Enter seatch string
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAdvancedContentSearchInput().Visible()  ;}}), Input.wait30); 
    	getAdvancedContentSearchInput().SendKeys(SearchString) ;

        //Click on Search button
    	getQuerySearchButton().Click();
    	
    	//look for warnings, in case of proximity search
    	try{
    		getTallyContinue().waitAndClick(5);
    		Thread.sleep(4000);
    	}catch (Exception e) {
		
		}
    	
    	//verify counts for all the tiles
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    	getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait90);
    	
    	int pureHit = Integer.parseInt(getPureHitsCount().getText());
    	System.out.println("Serach is done for "+SearchString+" and PureHit is : "+pureHit);
    	
    	return pureHit;
   }
    
    public void selectMetaDataOption(String metaDataField) {
    	Element activeBtn = null;
    	ElementCollection coll = getAllBasicSearch_MetadataBtn();
    	
    	for (int c=1; c<coll.size()+1;c++) {
    		Element btn = getBasicSearch_MetadataBtn(c);
			if (btn.Visible()) {
				activeBtn = btn;
				break;
			}
    	}
		activeBtn.Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectMetaData().Visible()  ;}}), Input.wait30); 
		
		//changed this approach from a sleep to waiting for value to appear in dropdown
//	    try {
//			Thread.sleep(4000);
//		} catch (InterruptedException e) {
//			
//			e.printStackTrace();
//		}
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectMetaData().selectFromDropdownLoaded() ;}}), Input.wait30); 
		getSelectMetaData().selectFromDropdown().selectByVisibleText(metaDataField);
    	
    }
    
    public void setMetaDataValue(String option, String val1, String val2) {

    	driver.waitForPageToBeReady();
		if(option == null){
			getMetaDataSearchText1().SendKeys(val1+Keys.TAB);
		}
		else if(option.equalsIgnoreCase("IS")){
			getMetaOption().selectFromDropdown().selectByVisibleText("IS (:)");
			getMetaDataSearchText1().SendKeys(val1+Keys.TAB);
		}
		else if(option.equalsIgnoreCase("RANGE")){
			getMetaOption().selectFromDropdown().selectByVisibleText("RANGE");
			getMetaDataSearchText1().SendKeys(val1+Keys.TAB);
			getMetaDataSearchText2().SendKeys(val2+Keys.TAB);
			
		}

//	    try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			
//			e.printStackTrace();
//		}
		getMetaDataInserQuery().Click();    	
    }
    
    public void setMetaDataValueNoEnter(String option, String val1, String val2) {

    	driver.waitForPageToBeReady();

		if(option == null){
			getMetaDataSearchText1().SendKeys(val1);
		}
		else if(option.equalsIgnoreCase("IS")){
			getMetaOption().selectFromDropdown().selectByVisibleText("IS (:)");
			getMetaDataSearchText1().SendKeys(val1+Keys.TAB);
		}
		else if(option.equalsIgnoreCase("RANGE")){
			getMetaOption().selectFromDropdown().selectByVisibleText("RANGE");
			getMetaDataSearchText1().SendKeys(val1+Keys.TAB);
			getMetaDataSearchText2().SendKeys(val2+Keys.TAB);
		}
    }
    
    public int basicMetaDataSearch(String metaDataField, String option, String val1, String val2) {
	   
    	
    	//To make sure we are in basic search page
    	driver.getWebDriver().get(Input.url+ "Search/Searches");
    	selectMetaDataOption(metaDataField);
    	
    	setMetaDataValue(option, val1, val2);

    	//Click on Search button
    	getSearchButton().Click();
    	
    	//two handle twosearch strings
    	if(metaDataField.equalsIgnoreCase("CustodianName")||
				metaDataField.equalsIgnoreCase("EmailAuthorName")||
				metaDataField.equalsIgnoreCase("EmailRecipientNames")){
			
			try {
				
				getTallyContinue().waitAndClick(4000);
			} catch (Exception e) {
				
			}
		}
    	
    	//verify counts for all the tiles
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    	getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait90);
    	
    	int pureHit = Integer.parseInt(getPureHitsCount().getText());
    	System.out.println("Serach is done for "+metaDataField+" with value "+val1+" - "+val2+" purehit is : "+pureHit);
    	return pureHit;


	}
    
    public int advancedMetaDataSearch(String metaDataField, String option, String val1, String val2) {
 	   
    	//To make sure we are in basic search page
    	driver.getWebDriver().get(Input.url+ "Search/Searches");
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAdvancedSearchLink().Visible()  ;}}), Input.wait30); 
    	getAdvancedSearchLink().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getContentAndMetaDatabtn().Visible()  ;}}), Input.wait30); 
    	getContentAndMetaDatabtn().Click();
        //Enter seatch string
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAdvanceSearch_MetadataBtn().Visible()  ;}}), Input.wait30); 
    	getAdvanceSearch_MetadataBtn().Click();
	    try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		getSelectMetaData().selectFromDropdown().selectByVisibleText(metaDataField);
		if(option == null){
			
			getMetaDataSearchText1().SendKeys(val1+Keys.TAB);
		}
		else if(option.equalsIgnoreCase("IS")){
			getMetaOption().selectFromDropdown().selectByVisibleText("IS (:)");
			getMetaDataSearchText1().SendKeys(val1+Keys.TAB);
		}
		else if(option.equalsIgnoreCase("RANGE")){
			getMetaOption().selectFromDropdown().selectByVisibleText("RANGE");
			getMetaDataSearchText1().SendKeys(val1+Keys.TAB);
			getMetaDataSearchText2().SendKeys(val2+Keys.TAB);
			
		}
		getMetaDataInserQuery().Click();
		 //Click on Search button
    	getQuerySearchButton().Click();
    	//two handle twosearch strings
    	if(metaDataField.equalsIgnoreCase("CustodianName")||
				metaDataField.equalsIgnoreCase("EmailAuthorName")||
				metaDataField.equalsIgnoreCase("EmailRecipientNames")){
			
			try {
				
				getTallyContinue().waitAndClick(4000);
			} catch (Exception e) {
				
			}
		}
    	
    	//verify counts for all the tiles
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    	getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait90);
    	
    	int pureHit = Integer.parseInt(getPureHitsCount().getText());
    	System.out.println("Serach is done for "+metaDataField+" with value "+val1+" - "+val2+" purehit is : "+pureHit);
    	return pureHit;


	}
    
    public int audioSearch(String SearchString, String language) {
    	this.driver.getWebDriver().get(Input.url+ "Search/Searches");
    	
    	getAdvancedSearchLink().waitAndClick(20);
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAs_Audio().Visible()  ;}}), Input.wait30); 
    	getAs_Audio().ScrollTo();
    	getAs_Audio().waitAndClick(10);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAs_AudioLanguage().Visible()  ;}}), Input.wait30); 
    	getAs_AudioLanguage().selectFromDropdown().selectByVisibleText(language);
    	 //Enter seatch string
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAs_AudioText().Visible()  ;}}), Input.wait30); 
    	getAs_AudioText().SendKeys(SearchString) ;

        //Click on Search button
    	getQuerySearchButton().Click();
    	
    	//verify counts for all the tiles
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    	getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait90);
    	
    	int pureHit = Integer.parseInt(getPureHitsCount().getText());
    	System.out.println("Audio Serach is done for "+SearchString+" and PureHit is : "+pureHit);
    	
    	return pureHit;

	}
    //for two search strings with operator
    public int audioSearch(String SearchString1,String SearchString2, String language, String threshold) throws InterruptedException {
    	
    	getAdvancedSearchLink().Click();
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAs_Audio().Visible()  ;}}), Input.wait30); 
    	getAs_Audio().waitAndClick(10);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAs_AudioLanguage().Visible()  ;}}), Input.wait30); 
    	getAs_AudioLanguage().selectFromDropdown().selectByVisibleText(language);
    	 //Enter seatch string
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAs_AudioText().Visible()  ;}}), Input.wait30); 
    	getAs_AudioText().SendKeys(SearchString1+Keys.ENTER+SearchString2+Keys.ENTER) ;

    	//Select term operator 
    	getAudioTermOperator().selectFromDropdown().selectByVisibleText("ALL");
    	//Select location
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAudioLocation().Visible()  ;}}), Input.wait30); 
    	Thread.sleep(6000);
    	getAudioLocation().selectFromDropdown().selectByValue("range");
    	//enter time
    	getAudioTimeDiff().SendKeys("30");
    	
    	Thread.sleep(5000);
    	//select threshold
    	if (threshold.equalsIgnoreCase("right")) {
    		getConceptualRight().waitAndClick(15);
		}
    	else if(threshold.equalsIgnoreCase("mid")){
    		//Since by default it will be in mid,no need to click mid
    		//getConceptualMid().waitAndClick(15);
    	}
    	else if(threshold.equalsIgnoreCase("left")){
    		getConceptualLeft().waitAndClick(15);
    	}
        //Click on Search button
    	getQuerySearchButton().Click();
    	
    	//verify counts for all the tiles
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    	getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait90);
    	
    	int pureHit = Integer.parseInt(getPureHitsCount().getText());
    	System.out.println("Audio Serach is done for "+SearchString1+" and "+SearchString2 +" PureHit is : "+pureHit);
    	
    	return pureHit;

	}
    
    public int conceptualSearch(String SearchString, String Percetage) {
    	getAdvancedSearchLink().Click();
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAs_Conceptual().Visible()  ;}}), Input.wait30); 
    	getAs_Conceptual().Click();
    	
    	
    	 //Enter seatch string
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getAs_ConceptualTextArea().Visible()  ;}}), Input.wait30); 
    	getAs_ConceptualTextArea().SendKeys(SearchString) ;

    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
    	/*if(Percetage.equalsIgnoreCase("right")){
    		getConceptualRight().Click();
    	}
    	else if(Percetage.equalsIgnoreCase("left")){
    		getConceptualLeft().Click();
    	}
    	else if(Percetage.equalsIgnoreCase("mid")){
    		getConceptualMid().Click();
    	}*/
        //Click on Search button
    	getQuerySearchButton().Click();
    	
    	//verify counts for all the tiles
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    	getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait90);
    	
    	int pureHit = Integer.parseInt(getPureHitsCount().getText());
    	System.out.println("Conceptual serach is done for "+SearchString+" and PureHit is : "+pureHit);
    	
    	return pureHit;

	}
     
    public void searchSavedSearch(final String SaveName) {
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSavedSearchBtn().Visible()  ;}}), Input.wait60);
    	getSavedSearchBtn().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSavedSearchName(SaveName).Visible()  ;}}), Input.wait60);
    	driver.scrollingToBottomofAPage();
    	for (WebElement iterable_element : getTree().FindWebElements()) {
    		//System.out.println(iterable_element.getText());
			if(iterable_element.getText().contains(SaveName)){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
				driver.scrollingToBottomofAPage();
		//		System.out.println(iterable_element.getText());
				iterable_element.click();
			}
		}
    	getMetaDataInserQuery().Click();
		  //Click on Search button
    	driver.scrollPageToTop();
    	
    }
    
     //Function to fetch pure hit count 
     public String verifyPureHitsCount() {
    	
    	return getPureHitsCount().getText();
    	
       }
    //Function to fetch Threaded docs hit count 
    public String verifyThreadedCount() {
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getThreadedCount().Visible()  ;}}), Input.wait60);
    	
    	return getThreadedCount().getText();
    	
      }
    //Function to fetch neardupes hit count   
    public String verifyNearDupeCount() {
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getNearDupeCount().Visible()  ;}}), Input.wait60);
    	return getNearDupeCount().getText();
   
    }
    //Function to fetch Familymembers hit count 
    public String verifyFamilyount() {
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getFamilyCount().Visible()  ;}}), Input.wait60);
    	return getFamilyCount().getText();
    }
 
  //Function to perform new bulk folder with given tag name
    public void bulkFolder(String folderName) throws InterruptedException{
   	 
   	 //driver.getWebDriver().get(Input.url+"Search/Searches");
   	 try{
   		 getPureHitAddButton().Click();
   		}catch (Exception e) {
   			System.out.println("Pure hit block already moved to action panel");
   		}
   		 
   	
   	 
   	 getBulkActionButton().waitAndClick(20);
   	
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getBulkFolderAction().Visible()  ;}}), Input.wait60); 
   	 
   	 getBulkFolderAction().Click();
   	 
   	 
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getBulkNewTab().Visible()  ;}}), Input.wait60); 
   	
   	 getBulkNewTab().Click();
   	
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getEnterFolderName().Visible()  ;}}), Input.wait60); 
   	 getEnterFolderName().SendKeys(folderName);
   	
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getFolderAllRoot().Visible()  ;}}), Input.wait60); 
   	 getFolderAllRoot().Click();
   	 
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   		    	getContinueCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
   	 getContinueButton().Click();
   	 
   	 final BaseClass bc = new BaseClass(driver);
        final int Bgcount = bc.initialBgCount();
        
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   		    	getFinalCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
   	 getFinalizeButton().Click();
   	 
   	 base.VerifySuccessMessage("Records saved successfully");
   	 
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			bc.initialBgCount() == Bgcount+1  ;}}), Input.wait60); 
   	 System.out.println("Bulk folder is done, folder is : "+folderName);
   	
   	 //Since page is freezing after bulk actiononly in automation, lets reload page to avoid it..
   	 driver.getWebDriver().navigate().refresh();
   }
    
   //Function to perform bulk folder with existing folder
   public void bulkFolderExisting(final String folderName) throws InterruptedException{
   	 
   	 driver.getWebDriver().get(Input.url+"Search/Searches");
   	 try{
   		 getPureHitAddButton().Click();
   		}catch (Exception e) {
   			System.out.println("Pure hit block already moved to action panel");
   		}
   	 
   	 getBulkActionButton().Click();
   	
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getBulkFolderAction().Visible()  ;}}), Input.wait60); 
   	 
   	 getBulkFolderAction().waitAndClick(10);
   	 
   	 
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getSelectFolderExisting(folderName).Visible()  ;}}), Input.wait60); 
   	 
   	 getSelectFolderExisting(folderName).waitAndClick(5);
   	 
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   		    	getContinueCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
   	 getContinueButton().Click();
   	 
   	 final BaseClass bc = new BaseClass(driver);
       final int Bgcount = bc.initialBgCount();
       
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   		    	getFinalCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
   	 getFinalizeButton().Click();
   	 
   	 base.VerifySuccessMessage("Records saved successfully");
   	 
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			bc.initialBgCount() == Bgcount+1  ;}}), Input.wait60); 
   	 System.out.println("Bulk folder is done, folder is : "+folderName);
   	 
   	 //Since page is freezing after bulk actiononly in automation, lets reload page to avoid it..
   	 driver.getWebDriver().navigate().refresh();
   }

   //Function to perform bulk tag with existing tag
   public void bulkTagExisting(final String tagName) throws InterruptedException{
   	 
   	 driver.getWebDriver().get(Input.url+"Search/Searches");
   	 try{
   		 getPureHitAddButton().Click();
   		}catch (Exception e) {
   			System.out.println("Pure hit block already moved to action panel");
   		}
   	 
   	 getBulkActionButton().Click();
   	
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getBulkTagAction().Visible()  ;}}), Input.wait60); 
   	 
   	 getBulkTagAction().waitAndClick(10);
   	 
   	 
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getSelectTagExisting(tagName).Visible()  ;}}), Input.wait60); 
   	 
   	 getSelectTagExisting(tagName).waitAndClick(5);
   	 
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   		    	getContinueCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
   	 getContinueButton().Click();
   	 
   	 final BaseClass bc = new BaseClass(driver);
   	 final int Bgcount = bc.initialBgCount();
     
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   		    	getFinalCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
   	 getFinalizeButton().Click();
   	 
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getBulkTagConfirmationButton().Visible()  ;}}), Input.wait30); 
   	 getBulkTagConfirmationButton().Click();
   	 
   	 base.VerifySuccessMessage("Records saved successfully");
   	 
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			bc.initialBgCount() == Bgcount+1  ;}}), Input.wait60); 
   	 System.out.println("Bulk Tag is done, Tag is : "+tagName);
   	 
   	 //Since page is freezing after bulk actiononly in automation, lets reload page to avoid it..
   	 driver.getWebDriver().navigate().refresh();
   }

   //Function to perform bulk tag with given tag name
   public void bulkTag(String TagName) throws InterruptedException{
   	//driver.getWebDriver().get(Input.url+"Search/Searches");
   	try{
   	 getPureHitAddButton().Click();
   	}catch (Exception e) {
   		System.out.println("Pure hit block already moved to action panel");
   	}
   	 
   	 getBulkActionButton().waitAndClick(20);
   	
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getBulkTagAction().Visible()  ;}}), Input.wait60); 
   	 
   	 getBulkTagAction().waitAndClick(10);
   	 
   	 
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getBulkNewTab().Visible()  ;}}), Input.wait60); 
   	
   	 getBulkNewTab().Click();
   	
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getEnterTagName().Visible()  ;}}), Input.wait60); 
   	 getEnterTagName().SendKeys(TagName);
   	
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getTagsAllRoot().Visible()  ;}}), Input.wait60); 
   	 getTagsAllRoot().Click();
   	 
   	 
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   		    	getContinueCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
   	 getContinueButton().Click();
   	 
   	 final BaseClass bc = new BaseClass(driver);
        final int Bgcount = bc.initialBgCount();
        
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   		    	getFinalCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
   	 getFinalizeButton().Click();
   	 
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getBulkTagConfirmationButton().Visible()  ;}}), Input.wait60); 
   	 getBulkTagConfirmationButton().Click();
   	 
   	 base.VerifySuccessMessage("Records saved successfully");
   	 
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   	 			bc.initialBgCount() == Bgcount+1  ;}}), Input.wait60); 
   	 System.out.println("Bulk Tag is done, Tag is : "+TagName); 
   	 
   	 //Since page is freezing after bulk actiononly in automation, lets reload page to avoid it..
   	 driver.getWebDriver().navigate().refresh();
   }


/*public void exportData() {
	try{
		 getPureHitAddButton().Click();
		}catch (Exception e) {
			System.out.println("Pure hit block already moved to action panel");
		}
		 
		 getBulkActionButton().Click();
		 getas_Action_Exportdata().Click();
			
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getMetaDataFields().size()>1  ;}}), Input.wait60); 
		 
		 //getBulkTagAction().Click();
		 System.out.println(getMetaDataFields().size());
		 
		
}
*/

public void ViewInDocList() throws InterruptedException{
	 
	driver.getWebDriver().get(Input.url+"Search/Searches");
	 try{
		 getPureHitAddButton().waitAndClick(10);
		}catch (Exception e) {
			System.out.println("Pure hit block already moved to action panel");
		}
		 
	 
	 getBulkActionButton().waitAndClick(15);
	 Thread.sleep(2000);
	
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getDocListAction().Visible()  ;}}), Input.wait60); 
	 
	 getDocListAction().waitAndClick(20);
	 try{
			getTallyContinue().waitAndClick(10);
		}catch (Exception e) {
					}
	 System.out.println("Navigated to doclist, to view docslist");
	
}

public void ViewInDocView() throws InterruptedException{
	//driver.getWebDriver().get(Input.url+"Search/Searches");
	
	 try{
		 getPureHitAddButton().waitAndClick(10);
		}catch (Exception e) {
			System.out.println("Pure hit block already moved to action panel");
		}
		 
	 driver.scrollPageToTop();
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getBulkActionButton().Visible()  ;}}), Input.wait30); 
	 getBulkActionButton().waitAndClick(10);
	Thread.sleep(1000);
	 
	 try{
		 getDocViewAction().waitAndClick(10);
		 }catch (Exception e) {
			 getDocViewActionDL().Click();
		}
	 System.out.println("Navigated to docView to view docs");
	
}

public void tallyResults() throws InterruptedException {
	driver.getWebDriver().get(Input.url+"Search/Searches");
	try{
		 getPureHitAddButton().Click();
		}catch (Exception e) {
			System.out.println("Pure hit block already moved to action panel");
		}
	 
	 getBulkActionButton().Click();
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getDocListAction().Visible()  ;}}), Input.wait60); 
	 
	 getTallyResults().waitAndClick(10);
	 Thread.sleep(3000);
	 System.out.println("Navigated to Tally  to view docs");
}
public void bulkAssign() {
	driver.getWebDriver().get(Input.url+"Search/Searches");
	 try{
		 getPureHitAddButton().Click();
		}catch (Exception e) {
			System.out.println("Pure hit block already moved to action panel");
		}
	 
	 getBulkActionButton().waitAndClick(10);
	
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getBulkAssignAction().Visible()  ;}}), Input.wait60); 
	
     
	 getBulkAssignAction().waitAndClick(10);
	 
	 System.out.println("performing bulk assign");
	 
	
	
}




//Bulk release to default security group
public void bulkRelease(final String SecGroup) {

	 try{
		 getPureHitAddButton().Click();
		}catch (Exception e) {
			System.out.println("Pure hit block already moved to action panel");
		}
	 
	 getBulkActionButton().waitAndClick(10);
	
	 try{
		 getBulkReleaseAction().waitAndClick(10);
	 }catch (Exception e) {
		 getBulkReleaseActionDL().Click();
	}
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getBulkRelDefaultSecurityGroup_CheckBox(SecGroup).Visible()  ;}}), Input.wait60); 
	 getBulkRelDefaultSecurityGroup_CheckBox(SecGroup).Click();
	 
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getBulkRelease_ButtonRelease().Visible()  ;}}),Input.wait60); 
	 getBulkRelease_ButtonRelease().Click();
	 
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getFinalizeButton().Visible()  ;}}), Input.wait60); 
	 getFinalizeButton().Click();
   
	 base.VerifySuccessMessage("Records saved successfully");
	 
	 System.out.println("performing bulk release");
	 
	
	
}
//Function to perform bulk untag
public void bulkUnTag(final String TagName) throws InterruptedException{

	 Thread.sleep(1000);	
	 getBulkActionButton().Click();
	 
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
	 getBulkTagAction().Visible() ;}}), Input.wait60);
	 getBulkTagAction().waitAndClick(10);
		 
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getBulkUntagbutton().Visible()  ;}}), Input.wait30); 
	 getBulkUntagbutton().Click();
	
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getSelectTagExisting(TagName).Visible()  ;}}), Input.wait60); 
	 getSelectTagExisting(TagName).waitAndClick(10);
	
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    	getContinueCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
	 getContinueButton().Click();
	 
	 final BaseClass bc = new BaseClass(driver);
     final int Bgcount = bc.initialBgCount();
  
	 bc.VerifySuccessMessage("Records saved successfully");
	 
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	 			bc.initialBgCount() == Bgcount+1  ;}}), Input.wait60); 
	 System.out.println("Bulk Untag is done, Tag is : "+TagName); 
	 
	 //Since page is freezing after bulk actiononly in automation, lets reload page to avoid it..
	 driver.getWebDriver().navigate().refresh();
}

//Function to perform new bulk folder with given folder name
public void bulkUnFolder(final String folderName) throws InterruptedException{
	 driver.scrollPageToTop();
	 Thread.sleep(1000);
	 getBulkActionButton().Click();
	 
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
	 getBulkFolderAction().Visible() ;}}), Input.wait60);
	 getBulkFolderAction().Click();
		 
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getBulkUnFolderbutton().Visible()  ;}}), Input.wait30); 
	 getBulkUnFolderbutton().Click();
	 
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getSelectFolderExisting(folderName).Visible()  ;}}), Input.wait60); 
	 getSelectFolderExisting(folderName).waitAndClick(5);
	 
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    	getContinueCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
	 getContinueButton().Click();
	 
	 final BaseClass bc = new BaseClass(driver);
	 final int Bgcount = bc.initialBgCount();

	 bc.VerifySuccessMessage("Records saved successfully");
	 
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			bc.initialBgCount() == Bgcount+1  ;}}), Input.wait60); 
	 System.out.println("Bulk Unfolder is done, folder is : "+folderName);
	 
	 //Since page is freezing after bulk actiononly in automation, lets reload page to avoid it..
	 driver.getWebDriver().navigate().refresh();
}



public void selectTagInASwp(String tagName) {
	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getWP_TagBtn().Visible()  ;}}), Input.wait30); 
	 getWP_TagBtn().Click();
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getTree().Visible()  ;}}), Input.wait30); 
	 System.out.println(getTree().FindWebElements().size());
		for (WebElement iterable_element : getTree().FindWebElements()) {
			//System.out.println(iterable_element.getText());
			if(iterable_element.getText().contains(tagName)){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
				driver.scrollingToBottomofAPage();
		//		System.out.println(iterable_element.getText());
				iterable_element.click();
			}
		}
		getMetaDataInserQuery().Click();
		driver.scrollPageToTop();

}
public void selectFolderInASwp(String folderName) {
	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getWP_FolderBtn().Visible()  ;}}), Input.wait30); 
	getWP_FolderBtn().Click();
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getTree().Visible()  ;}}), Input.wait30);
	System.out.println(getTree().FindWebElements().size());
	for (WebElement iterable_element : getTree().FindWebElements()) {
		//System.out.println(iterable_element.getText());
		if(iterable_element.getText().contains(folderName)){
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
			driver.scrollingToBottomofAPage();
	//		System.out.println(iterable_element.getText());
			iterable_element.click();
		}
	}
	getMetaDataInserQuery().Click();
	driver.scrollPageToTop();

}

public void selectSecurityGinWPS(String sgname) {
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getSecurityGrpBtn().Visible()  ;}}), Input.wait30); 
	getSecurityGrpBtn().Click();
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getTree().Visible()  ;}}), Input.wait30); 
	 System.out.println(getSecurityNamesTree().FindWebElements().size());
		for (WebElement iterable_element : getSecurityNamesTree().FindWebElements()) {
			//System.out.println(iterable_element.getText());
			if(iterable_element.getText().contains(sgname)){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
				driver.scrollingToBottomofAPage();
		//		System.out.println(iterable_element.getText());
				iterable_element.click();
			}
		}
		driver.scrollingToBottomofAPage();
		getMetaDataInserQuery().waitAndClick(5);
	driver.scrollPageToTop();

}

public void selectOperator(String operator) {
	driver.scrollPageToTop();
	// not sure why this had a index of 2 ([2]) in the locator
	// kept that the same for backwards compatability, but updated to select the 1st DD by passing index of 1
	getOperatorDD(1).Click();
	if(operator.equalsIgnoreCase("and")){
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getOperatorAND(1).Visible()  ;}}), Input.wait30); 
		getOperatorAND(1).Click();	
	}
	if(operator.equalsIgnoreCase("OR")){
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getOperatorOR(1).Visible()  ;}}), Input.wait30); 
		getOperatorOR(1).waitAndClick(5);	
	}
	if(operator.equalsIgnoreCase("NOT")){
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getOperatorNOT(1).Visible()  ;}}), Input.wait30); 
		getOperatorNOT(1).Click();	
	}


}

//Function to perform redaction name search 
public void  selectRedactioninWPS(final String redactName) throws InterruptedException{
	 
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getRedactionBtn().Visible()  ;}}), Input.wait30); 
	getRedactionBtn().Click();
	
	//
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getTree().Visible()  ;}}), Input.wait30);
	System.out.println(getTree().FindWebElements().size());
	for (WebElement iterable_element : getTree().FindWebElements()) {
		//System.out.println(iterable_element.getText());
		if(iterable_element.getText().contains(redactName)){
			
			new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
			driver.scrollingToBottomofAPage();
			System.out.println(iterable_element.getText());
			iterable_element.click();
		}
	}
	//
	//driver.scrollingToBottomofAPage();
	 
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getMetaDataInserQuery().Visible()  ;}}), Input.wait60); 
	 getMetaDataInserQuery().Click();
	 
	 driver.scrollPageToTop();
	
}

//Function to perform assignment name search in work product
public void  selectAssignmentInWPS(final String assignMentName) throws InterruptedException{
	 
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getWP_assignmentsBtn().Visible()  ;}}), Input.wait30); 
	getWP_assignmentsBtn().Click();
	
	//
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getTree().Visible()  ;}}), Input.wait30);
	System.out.println(getTree().FindWebElements().size());
	for (WebElement iterable_element : getTree().FindWebElements()) {
		//System.out.println(iterable_element.getText());
		if(iterable_element.getText().contains(assignMentName)){
			
			new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
			driver.scrollingToBottomofAPage();
			System.out.println(iterable_element.getText());
			iterable_element.click();
		}
	}
	//
	//driver.scrollingToBottomofAPage();
	 
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getMetaDataInserQuery().Visible()  ;}}), Input.wait60); 
	 getMetaDataInserQuery().waitAndClick(20);
	 
	 driver.scrollPageToTop();
	
}
public void switchToWorkproduct() {
	driver.getWebDriver().get(Input.url+"Search/Searches");
	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getAdvancedSearchLink().Visible()  ;}}), Input.wait30);
getAdvancedSearchLink().Click();
	
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getWorkproductBtn().Visible()  ;}}), Input.wait30); 
	 getWorkproductBtn().Click();

}

public int serarchWP() {
	driver.scrollPageToTop();
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getQuerySearchButton().Visible()  ;}}), Input.wait30); 
	getQuerySearchButton().waitAndClick(5);
	//verify counts for all the tiles
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait90);
		
		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		System.out.println("Search is done and PureHit is : "+pureHit);
	 	return pureHit; 

}





//Function to perform new bulk folder with given tag name
public void BulkActions_Folder(String folderName) throws InterruptedException{
	 
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getBulkNewTab().Visible()  ;}}), Input.wait60); 
	
	 getBulkNewTab().Click();
	
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getEnterFolderName().Visible()  ;}}), Input.wait60); 
	 getEnterFolderName().SendKeys(folderName);
	
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getFolderAllRoot().Visible()  ;}}), Input.wait60); 
	 getFolderAllRoot().Click();
	 
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    	getContinueCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
	 getContinueButton().Click();
	 
	 final BaseClass bc = new BaseClass(driver);
    final int Bgcount = bc.initialBgCount();
  
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    	getFinalCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
	 getFinalizeButton().Click();
	 
	base.VerifySuccessMessage("Records saved successfully");
	 
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			bc.initialBgCount() == Bgcount+1  ;}}), Input.wait60); 
	 System.out.println("Bulk folder is done, folder is : "+folderName);
}
//Function to perform bulk tag from any page
public void BulkActions_Tag(String TagName) throws InterruptedException{
	 
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getBulkNewTab().Visible()  ;}}), Input.wait60); 
	
	 getBulkNewTab().Click();
	
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getEnterTagName().Visible()  ;}}), Input.wait60); 
	 getEnterTagName().SendKeys(TagName);
	
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getTagsAllRoot().Visible()  ;}}), Input.wait60); 
	 getTagsAllRoot().Click();
	 
	 
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    	getContinueCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
	 getContinueButton().Click();
	 
	 final BaseClass bc = new BaseClass(driver);
	 final int Bgcount = bc.initialBgCount();
 
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		    	getFinalCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
	 getFinalizeButton().Click();
	 
	 getTallyContinue().waitAndClick(10);
	 
	 base.VerifySuccessMessage("Records saved successfully");
	 
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	 			bc.initialBgCount() == Bgcount+1  ;}}), Input.wait60); 
	 System.out.println("Bulk Tag is done, Tag is : "+TagName); 
}
public void saveSearchAdvanced(String searchName) {
   	
	   /*	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getSaveSearch_AdvButton().Visible()  ;}}), Input.wait60); */
	   	getSaveSearch_AdvButton().waitAndClick(Input.wait30);
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   			getSavedSearch_MySearchesTab().Visible()  ;}}), Input.wait30); 
	   	getSavedSearch_MySearchesTab().Click();
	   	getSaveSearch_Name().SendKeys(searchName);
	   	getSaveSearch_SaveButton().Click();
	   	base.VerifySuccessMessage("Saved search saved successfully");
	   	base.CloseSuccessMsgpopup();
	   	System.out.println("Saved search with name - "+searchName);
		}

public void Removedocsfromresults() {
	
	try {
		getRemovedocsfromresult().waitAndClick(20);
	}
	catch(Exception e)
	{
		System.out.println("No docs present in cart");
	}
	
}


  public void quickbatch() {
	driver.getWebDriver().get(Input.url+"Search/Searches");
	 try{
		 getPureHitAddButton().Click();
		}catch (Exception e) {
			System.out.println("Pure hit block already moved to action panel");
		}
	 
	 getBulkActionButton().waitAndClick(10);
	
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getBulkAssignAction().Visible()  ;}}), Input.wait60); 
	
     
	 getQuickBatchAction().Click();
	 
	 System.out.println("performing quick batch");
	
}
  
  //Function to perform content search with advanced search options
  public int advContentSearchwithoptions(String SearchString){
  	
  	//To make sure we are in basic search page
  	driver.getWebDriver().get(Input.url+ "Search/Searches");
  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getAdvancedSearchLink().Visible()  ;}}), Input.wait30); 
  	getAdvancedSearchLink().Click();
  	
  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getContentAndMetaDatabtn().Visible()  ;}}), Input.wait30); 
  	getContentAndMetaDatabtn().Click();
      //Enter seatch string
  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getAdvancedContentSearchInput().Visible()  ;}}), Input.wait30); 
  	getAdvancedContentSearchInput().SendKeys(SearchString) ;
  	
  	 getadvoption_family().waitAndClick(10);
     
     getadvoption_near().waitAndClick(10);
     
     getadvoption_threaded().waitAndClick(10);

      //Click on Search button
  	getQuerySearchButton().waitAndClick(10);
  	
  	//look for warnings, in case of proximity search
  	try{
  		getTallyContinue().waitAndClick(5);
  	}catch (Exception e) {
		System.out.println("NO pop up appears");
		}
  	
  	//verify counts for all the tiles
  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getPureHitsCountwithOptions().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait90);
  	
  	int pureHit = Integer.parseInt(getPureHitsCountwithOptions().getText());
  	System.out.println("Serach is done for "+SearchString+" and PureHit is : "+pureHit);
  	
  	String backgroundColorthread = driver.FindElementByXPath(".//*[@id='002']").GetCssValue("background-color");
	System.out.println(backgroundColorthread);
	
	Assert.assertEquals(backgroundColorthread, "rgba(218, 218, 218, 1)");

	
  	
    return pureHit;
  	
 }
  
  public void selectAssignmentInASwp(String AssgnName) {
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getWP_assignmentsBtn().Visible()  ;}}), Input.wait30); 
		getWP_assignmentsBtn().Click();
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getTree().Visible()  ;}}), Input.wait30); 
		 System.out.println(getTree().FindWebElements().size());
			for (WebElement iterable_element : getTree().FindWebElements()) {
				//System.out.println(iterable_element.getText());
				if(iterable_element.getText().contains(AssgnName)){
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
			
						e.printStackTrace();
					}
					new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
					driver.scrollingToBottomofAPage();
					iterable_element.click();
				}
			}
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getadwp_assgn_distributedto().Visible()  ;}}), Input.wait30); 
		
			List<WebElement> alloptions = getadwp_assgn_distributedto().FindWebElements();
			
			System.out.println(alloptions.size());
			
			for (WebElement options : getadwp_assgn_distributedto().FindWebElements())
			{
				System.out.println(options.getText());
				Assert.assertTrue(options.getText().contains(Input.rmu1userName));
				Assert.assertTrue(options.getText().contains(Input.rmu2userName));
				Assert.assertTrue(options.getText().contains(Input.pa1userName));
				Assert.assertTrue(options.getText().contains(Input.pa2userName));
				Assert.assertTrue(options.getText().contains(Input.rev1userName));
				Assert.assertTrue(options.getText().contains(Input.rev2userName));
			}
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					getadwp_assgn_status().Visible()  ;}}), Input.wait30); 
		
			getadwp_assgn_status().selectFromDropdown().selectByVisibleText("Assigned");
			getMetaDataInserQuery().Click();
			driver.scrollPageToTop();
			
			 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					 getQuerySearchButton().Visible()  ;}}), Input.wait30); 
			getQuerySearchButton().waitAndClick(5);
			//verify counts for all the tiles
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait90);
				
				int pureHit = Integer.parseInt(getPureHitsCount().getText());
				System.out.println("Search is done and PureHit is : "+pureHit);
			 			
			

	}
  
   //Insert Directly into Search Query -> inserts into the current selected query
   public void insertFullText(String searchQuery) {
	   for(WebElement j: setQueryText().FindWebElements()) {
		   if(j.isDisplayed()) {
			   j.click();
			   j.sendKeys(searchQuery);
			   j.sendKeys(Keys.ENTER);
		   }
	  }
   }	   

   //Insert Two MetaData Values/Fields with a Condition Operator Between Them
   public void insertLongText(String metaData1, String metaData2, String condition, String metaVal1, String metaVal2) {

	   if(metaData2 == null){
		  insertFullText(metaVal1); 
	   }
	   else{
	   //Enter First Field MetaData and Value
	   	selectMetaDataOption(metaData1);   
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   	     	getMetaDataSearchText1().Enabled() && getMetaDataSearchText1().Displayed()  ;}}), Input.wait30); 
	   	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	   	     	getMetaDataInserQuery().Enabled() && getMetaDataSearchText1().Displayed()  ;}}), Input.wait30); 
           	setMetaDataValue(null,metaVal1,null);
       } 
       //Insert Condition Operator
       driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		getOperatorDropdown().FindWebElements().get(0).isEnabled()  ;}}), Input.wait30); 
       for(WebElement x: getOperatorDropdown().FindWebElements()) {
    	   if(x.isDisplayed() && x.isEnabled()) {
    		   x.click();
    		   for(WebElement y: getOperatorDropDownOP(condition).FindWebElements()) {
    			   if(y.isDisplayed() && y.isEnabled())
    				   y.click();
    		   }
    	   }
       }
       if(metaData2 == null){
		  insertFullText(metaVal2); 
	   }
       else
       {
       		//Enter Second Field MetaData and Value
       		selectMetaDataOption(metaData2);   
       		    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
       		 		getMetaDataSearchText1().Enabled() && getMetaDataSearchText1().Displayed()  ;}}), Input.wait30); 
       		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
       		 		getMetaDataInserQuery().Enabled() && getMetaDataSearchText1().Displayed()  ;}}), Input.wait30); 
       		setMetaDataValue(null,metaVal2,null);
	   }
	   
   }

}
