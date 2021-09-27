package pageFactory;

import java.awt.AWTException;
import java.awt.Robot;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.Log;
import executionMaintenance.UtilityLog;
import testScriptsSmoke.Input;

public class SessionSearch {

    Driver driver;
    public static int pureHit;
    BaseClass base;
    SoftAssert assertion=new SoftAssert();
  
    //public Element getNewSearch(){ return driver.FindElementByXPath("//button[@id='add_tab']"); }
    public Element getEnterSearchString(){ return driver.FindElementByXPath(".//*[@id='xEdit']/li/input"); }
    public Element getSearchButton(){ return driver.FindElementById("btnBasicSearch"); }
    public Element getQuerySearchButton(){ return driver.FindElementById("qSearch"); }
    public Element getSaveAsNewSearchRadioButton(){ return driver.FindElementByXPath("//*[@id='saveAsNewSearchRadioButton']/following-sibling::i"); }
   
    //Hits
    public Element getPureHitsCount(){ return driver.FindElementByXPath(".//*[@id='001']/span/count"); }
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
    //public Element getBulkActionButton(){ return driver.FindElementById("idAction"); }
    public Element getBulkActionButton(){ return driver.FindElementByXPath("//*[@id=\"idAction\"]"); }
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
    //public Element getContinueButton(){ return driver.FindElementById("btnAdd"); }
    //public Element getContinueButton(){ return driver.FindElementByXPath("//*[@id=\"btnAdd\"]"); }
    public Element getFinalCount(){ return driver.FindElementByXPath("//span[@id='spanTotal']"); }
    public Element getFinalizeButton(){ return driver.FindElementById("btnfinalizeAssignment"); }
    public Element getFolderTab(){ return driver.FindElementByXPath("//a[contains(text(),'Folders')]"); }
    public Element getToggleDocCount(){ return driver.FindElementByXPath("folderDocCount"); }
    public Element getSuccessMsgHeader(){ return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span"); }
    public Element getSuccessMsg(){ return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p"); }
  
    //Actions
    public Element getas_Action_Exportdata(){ return driver.FindElementByXPath("//*[@id='ddlbulkactions']//a[text()='Export Data']"); }
    public Element getDocListAction(){ return driver.FindElementByXPath("//a[contains(text(),'View In DocList')]"); }
    public Element getDocViewAction(){ return driver.FindElementByXPath("//*[@id='ddlbulkactions']//a[contains(.,'View In DocView')]"); }
    public Element getDocViewActionDL(){ return driver.FindElementByXPath("//a[contains(.,'View in DocView')]"); }
    
    public Element getTallyResults(){ return driver.FindElementByXPath("//*[@id='ddlbulkactions']//a[contains(.,'Tally Results')]"); }
    public Element getBulkAssignAction(){ return driver.FindElementByXPath("//*[@id='ddlbulkactions']//a[contains(.,'Bulk Assign')]"); }
    public Element getBulkReleaseAction(){ return driver.FindElementByXPath("//*[@id='ddlbulkactions']//a[contains(.,'Bulk Release')]"); }
    public Element getBulkReleaseActionDL(){ return driver.FindElementByXPath("//a[contains(.,'Bulk Release')]"); }
        
    //save search    
    public Element getSaveSearch_Button(){ return driver.FindElementById("btnSaveSearch"); }
    public Element getAdvanceS_SaveSearch_Button(){ return driver.FindElementByXPath("//div[@class='searchInput smart-accordion-default']//div[@class='row']//*[@id='qSave']"); }
    
    public Element getSavedSearch_MySearchesTab(){ return driver.FindElementById("-1_anchor"); }
    public Element getSaveSearch_Name(){ return driver.FindElementById("txtSaveSearchName"); }
    public Element getSaveSearch_SaveButton(){ return driver.FindElementById("btnEdit"); }
    public Element getBulkRelDefaultSecurityGroup_CheckBox(int count){ return driver.FindElementByXPath("//form[@id='Edit User Group']/fieldset/div/div/div/div/div["+count+"]//i"); }
    public Element getBulkRelOther_CheckBox(String SGname){ return driver.FindElementByXPath(".//*[@id='Edit User Group']//div[text()='"+SGname+"']/../div[1]/label/i"); }
    public Element getBulkRelease_ButtonRelease(){ return driver.FindElementById("btnRelease"); }
    
    //Metadata
    public Element getBasicSearch_MetadataBtn(){ return driver.FindElementById("metadataHelper"); }
    public Element getAdvanceSearch_MetadataBtn(){ return driver.FindElementByXPath("(//*[@id='metadataHelper'])[2]"); }
    public Element getSelectMetaData(){ return driver.FindElementById("metatagSelect"); }
    public Element getMetaOption(){ return driver.FindElementById("selectOp"); }
    public Element getMetaDataSearchText1(){ return driver.FindElementById("val1"); }
    public Element getMetaDataSearchText2(){ return driver.FindElementById("val2"); }
    public Element getMetaDataInserQuery(){ return driver.FindElementById("insertQueryBtn"); }
   
    //Advance Saerch
    public Element getAdvancedSearchLink(){ return driver.FindElementByXPath("//*[@id='advancedswitch']"); }
    public Element getContentAndMetaDatabtn(){ return driver.FindElementByXPath("//button[@id='contentmetadata']"); }
    public Element getWorkproductBtn(){ return driver.FindElementByXPath("//button[@id='workproduct']"); }
    public Element getSavedSearchBtn(){ return driver.FindElementById("savedSearchesHelper"); }
    public Element getSavedSearchName(String savedSearchName){ return driver.FindElementByXPath("//a[@class='jstree-anchor'][contains(text(),'"+savedSearchName+"')]"); }
    public ElementCollection getTree(){ return driver.FindElementsByXPath("//a[@class='jstree-anchor'][contains(text(),'')]"); }
    public ElementCollection getSecurityNamesTree(){ return driver.FindElementsByXPath("//*[@id='JSTree']/div/label"); }
    
    //advanced Content search
    public Element getAdvancedContentSearchInput(){ return driver.FindElementByXPath("//*[@id='c-1']//*[@id='contentmetadata']//*[@id='xEdit']/li/input[@autocomplete='on']"); }
    
    //Audio Saerch
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
    public Element getOperatorAND(){ return driver.FindElementByXPath("(//*[@id='opAND'])[2]"); }
    public Element getOperatorOR(){ return driver.FindElementByXPath("(//*[@id='opOR'])[2]"); }
    public Element getOperatorNOT(){ return driver.FindElementByXPath("(//*[@id='opNOT'])[2]"); }
    
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
    
  //shilpi
    public Element getSaveSearch_AdvButton(){ return driver.FindElementByXPath(".//*[@id='tabsResult-1']//a[@id='qSave']"); }
    
    //conceptually playBtn
    public Element getConceptuallyPlayButton(){ return driver.FindElementByXPath("//*[contains(@id,'playButton')]"); }
    
    //modify advanced search
    public Element getModifyASearch(){ return driver.FindElementById("qModifySearch"); }
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
    
    //Query alert for proximity and regex search
   
    public Element getSecurityGrpEle(){ return driver.FindElementByXPath("//*[@id=\"Edit User Group\"]/fieldset/div/div/div/div"); }
    public Element getYesQueryAlert(){ return driver.FindElementByCssSelector("#bot1-Msg1"); }
    public ElementCollection getSecurityGName(){ return driver.FindElementsByXPath("//*[@id='Edit User Group']/fieldset/div/div/div/div/div/div[2]"); }
    
    public SessionSearch(Driver driver){

    	this.driver = driver;
        this.driver.getWebDriver().get(Input.url+ "Search/Searches");
        base = new BaseClass(driver);
        base.selectproject();
        //This initElements method will create all WebElements
        //PageFactory.initElements(driver.getWebDriver(), this);

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
    
    public void saveSearch(String searchName) {
    	try{
    
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getSaveSearch_Button().Visible() && getSaveSearch_Button().Enabled() ;}}), Input.wait30);  
    	getSaveSearch_Button().waitAndClick(5);
    	}catch (Exception e) { 
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getAdvanceS_SaveSearch_Button().Visible() && getAdvanceS_SaveSearch_Button().Enabled() ;}}), Input.wait30);  
    		getAdvanceS_SaveSearch_Button().waitAndClick(5);
		}
    	
    	try{
    		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    				getSaveAsNewSearchRadioButton().Visible() && getSaveAsNewSearchRadioButton().Enabled() ;}}), Input.wait30);  
    		getSaveAsNewSearchRadioButton().waitAndClick(5);
        	}catch (Exception e) {
        		System.out.println("Radio button already selected");
        		UtilityLog.info("Radio button already selected");
    		}
    	
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSavedSearch_MySearchesTab().Visible() && getSavedSearch_MySearchesTab().Enabled() ;}}), Input.wait30);     	
    	getSavedSearch_MySearchesTab().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSaveSearch_Name().Visible() && getSaveSearch_Name().Enabled()  ;}}), Input.wait30); 
    	getSaveSearch_Name().SendKeys(searchName);    	
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSaveSearch_SaveButton().Visible() && getSaveSearch_SaveButton().Enabled()  ;}}), Input.wait30); 
    	getSaveSearch_SaveButton().Click();
    	
    	base.VerifySuccessMessage("Saved search saved successfully");
    	
    	Reporter.log("Saved the search with name '"+searchName+"'", true);
    	UtilityLog.info("Saved search with name - "+searchName);
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
            Assert.assertEquals("YOUR QUERY CONTAINS A HYPHEN CHARACTER \"-\" THAT IS INTERPRETED IN DIFFERENT WAYS BY THE SEARCH ENGINE DEPENDING ON ITS USE IN THE QUERY\n\nIf the hyphen character is part of a string that is enveloped within quotations, such as â€œbi-weekly reportâ€, the hyphen character is treated literally, returning documents that hit on that exact phrase.\n If the hyphen character is not part of a string enveloped in quotations, and is preceded or succeeded by a space, then the hyphen character will be interpreted as a space, which is an implied OR operator (ex. bi - weekly will be interpreted as bi OR weekly).\n\nIf the hyphen character is not part of a string enveloped in quotations, and is the first character of an argument (ie. not separated by a space from an argument), then the search engine will interpret the hyphen as a NOT operator (ex. bi -weekly is interpreted as bi NOT weekly)\n\nBased on this information, is your query what you intended? If this is what you intended, please click YES to execute your search. If this is not what you intended, please click NO and correct your query.",getQueryAlertGetText().getText()); 
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
    //Function to perform content search for a given search string
    public int basicContentSearch(String SearchString){

    	//To make sure we are in basic search page
    	driver.getWebDriver().get(Input.url+ "Search/Searches");
    	
    	driver.waitForPageToBeReady();
        //Enter seatch string
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getEnterSearchString().Visible()  ;}}), Input.wait30); 
    	getEnterSearchString().SendKeys(SearchString) ;

        //Click on Search button
    	getSearchButton().Click();
    	
    	//handle pop confirmation for regex and proximity queries
    	try {
    		getYesQueryAlert().waitAndClick(8);
		} catch (Exception e) {
			// TODO: handle exception
		}
    	
    	//verify counts for all the tiles
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    	getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait120);
    	
    	int pureHit = Integer.parseInt(getPureHitsCount().getText());
    	//System.out.println("Search is done for "+SearchString+" and PureHit is : "+pureHit);
    	UtilityLog.info("Search is done for "+SearchString+" and PureHit is : "+pureHit);
    	Reporter.log("Search is done for "+SearchString+" and PureHit is : "+pureHit,true);
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
    	Reporter.log("Serach is done for '"+SearchString+"' and PureHit is : "+pureHit, true);
    	UtilityLog.info("Serach is done for "+SearchString+" and PureHit is : "+pureHit);
    	
    	return pureHit;
   }
    
    
    public int basicMetaDataSearch(String metaDataField, String option, String val1, String val2) {
	   
    	//To make sure we are in basic search page
    	driver.getWebDriver().get(Input.url+ "Search/Searches");
    	
    	getBasicSearch_MetadataBtn().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectMetaData().Visible()  ;}}), Input.wait30); 
		
	    try {
			Thread.sleep(4000);
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
    	System.out.println("Search is done for "+metaDataField+" with value "+val1+" - "+val2+" purehit is : "+pureHit);
    	UtilityLog.info("Search is done for "+metaDataField+" with value "+val1+" - "+val2+" purehit is : "+pureHit);
    	
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
    	System.out.println("Search is done for "+metaDataField+" with value "+val1+" - "+val2+" purehit is : "+pureHit);
    	UtilityLog.info("Search is done for "+metaDataField+" with value "+val1+" - "+val2+" purehit is : "+pureHit);
    	
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
    	System.out.println("Audio Search is done for "+SearchString+" and PureHit is : "+pureHit);
    	UtilityLog.info("Audio Search is done for "+SearchString+" and PureHit is : "+pureHit);
    	
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
    	try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
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
    	System.out.println("Conceptual search is done for "+SearchString+" and PureHit is : "+pureHit);
    	UtilityLog.info("Conceptual search is done for "+SearchString+" and PureHit is : "+pureHit);
    	
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
   			UtilityLog.info("Pure hit block already moved to action panel");
   		}		    	
   	 
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			getBulkActionButton().Visible()  ;}}), Input.wait60); 
   	 getBulkActionButton().waitAndClick(5);
   	
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getBulkFolderAction().Visible()  ;}}), Input.wait60); 
   	 
   	 getBulkFolderAction().waitAndClick(5);
   	 
   	 
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getBulkNewTab().Visible()  ;}}), Input.wait60); 
   	
   	 getBulkNewTab().waitAndClick(20);
   	
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
   	 //System.out.println("Bulk folder is done, folder is : "+folderName);
   	 UtilityLog.info("Bulk folder is done, folder is : "+folderName);
   	 Reporter.log("Bulk folder is done, folder is : "+folderName,true);
   	 //Since page is freezing after bulk actiononly in automation, lets reload page to avoid it..
   	 driver.getWebDriver().navigate().refresh();
   }
    
   //Function to perform bulk folder with existing folder
   public void bulkFolderExisting(final String folderName) throws InterruptedException{
   	 
   	 driver.getWebDriver().get(Input.url+"Search/Searches");
   	 try{
   		 getPureHitAddButton().waitAndClick(10);
   		}catch (Exception e) {
   			//System.out.println("Pure hit block already moved to action panel");
   			UtilityLog.info("Pure hit block already moved to action panel");
   			Reporter.log("Pure hit block already moved to action panel",true);
   		}
   	 
   	 getBulkActionButton().waitAndClick(10);
   	
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getBulkFolderAction().Visible()  ;}}), Input.wait60); 
   	 
   	 getBulkFolderAction().waitAndClick(10);
   	 
   	 
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getSelectFolderExisting(folderName).Visible()  ;}}), Input.wait60); 
   	 
   	 getSelectFolderExisting(folderName).waitAndClick(5);
   	 
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   		    	getContinueCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
   	 getContinueButton().waitAndClick(10);
   	 
   	 final BaseClass bc = new BaseClass(driver);
       final int Bgcount = bc.initialBgCount();
       
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   		    	getFinalCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
   	 getFinalizeButton().waitAndClick(10);
   	 
   	 base.VerifySuccessMessage("Records saved successfully");
   	 
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			bc.initialBgCount() == Bgcount+1  ;}}), Input.wait60); 
   	 //System.out.println("Bulk folder is done, folder is : "+folderName);
   	 UtilityLog.info("Bulk folder is done, folder is : "+folderName);
   	 Reporter.log("Bulk folder is done, folder is : "+folderName,true);
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
   			UtilityLog.info("Pure hit block already moved to action panel");
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
   	 UtilityLog.info("Bulk Tag is done, Tag is : "+tagName);
   	 
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
   		UtilityLog.info("Pure hit block already moved to action panel");
   	}
   	 
   	 getBulkActionButton().waitAndClick(30);
   	
   	 /*driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getBulkTagAction().Visible()  ;}}), Input.wait60); */
   	 Thread.sleep(2000); // synch with app!
   	 getBulkTagAction().waitAndClick(30);
   	 
   	 
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getBulkNewTab().Visible()  ;}}), Input.wait60); 
   	
   	 getBulkNewTab().waitAndClick(60);
   	
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
   	 //System.out.println("Bulk Tag is done, Tag is : "+TagName); 
   	 UtilityLog.info("Bulk Tag is done, Tag is : "+TagName);
   	 Reporter.log("Bulk Tag is done, Tag is : "+TagName, true);
	 
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
		 getPureHitAddButton().waitAndClick(20);
		}catch (Exception e) {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
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
	 UtilityLog.info("Navigated to doclist, to view docslist");
	
}

public void ViewInDocView() throws InterruptedException{
	//driver.getWebDriver().get(Input.url+"Search/Searches");
	
	 try{
		 getPureHitAddButton().waitAndClick(10);
		}catch (Exception e) {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}
		 
	 driver.scrollPageToTop();
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getBulkActionButton().Visible()  ;}}), Input.wait30); 
	 getBulkActionButton().waitAndClick(60);//increased time from 5 sec to 60sec
	 
	 try{
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getDocViewAction().Visible()  ;}}), Input.wait30);// added wait statement here
		 getDocViewAction().waitAndClick(60);//increased time from 5 sec to 60sec
		 }catch (Exception e) {
			 getDocViewActionDL().Click();
		}
	 System.out.println("Navigated to docView to view docs");
	 UtilityLog.info("Navigated to docView to view docs");
	
}

public void tallyResults() throws InterruptedException {
	driver.getWebDriver().get(Input.url+"Search/Searches");
	try{
		 getPureHitAddButton().Click();
		}catch (Exception e) {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}
	 
	 getBulkActionButton().Click();
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getDocListAction().Visible()  ;}}), Input.wait60); 
	 
	 getTallyResults().waitAndClick(10);
	 Thread.sleep(3000);
	 System.out.println("Navigated to Tally  to view docs");
	 UtilityLog.info("Navigated to Tally  to view docs");
}
public void bulkAssign() {
	driver.getWebDriver().get(Input.url+"Search/Searches");
	 try{
		 getPureHitAddButton().Click();
		}catch (Exception e) {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}
	 
	 getBulkActionButton().waitAndClick(10);
	
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getBulkAssignAction().Visible()  ;}}), Input.wait60); 
	
     
	 getBulkAssignAction().waitAndClick(10);
	 
	 System.out.println("performing bulk assign");
	 UtilityLog.info("performing bulk assign");
	
	
}




//Bulk release to default security group
public void bulkRelease(final String SecGroup) throws InterruptedException {
	int count=0;

	 try{
		 getPureHitAddButton().waitAndClick(10);
		}catch (Exception e) {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}
	 
	 getBulkActionButton().waitAndClick(10);
	 getBulkReleaseAction().ElementToBeClickableExplicitWait(getBulkReleaseAction(), 2000);
	 try{
		 getBulkReleaseAction().waitAndClick(10);
	 }catch (Exception e) {
		 getBulkReleaseActionDL().waitAndClick(10);
	}
	 Thread.sleep(5000);
	//added here in the pom
	 for (WebElement iterable_element : getSecurityGName().FindWebElements()) {
		 count=count+1;
			if(iterable_element.getText().contains(SecGroup)){
				
				
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click().perform();
				getBulkRelDefaultSecurityGroup_CheckBox(count).VisibilityOfElementExplicitWait(getBulkRelDefaultSecurityGroup_CheckBox(count), 5000);
				break;
			}
		}

	 getBulkRelDefaultSecurityGroup_CheckBox(count).Click(); 
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getBulkRelease_ButtonRelease().Visible()  ;}}),Input.wait60); 
	 getBulkRelease_ButtonRelease().waitAndClick(20);
	 
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getFinalizeButton().Visible()  ;}}), Input.wait60); 
	 getFinalizeButton().waitAndClick(20);
   
	 base.VerifySuccessMessageB("Records saved successfully");
	 
	 System.out.println("performing bulk release");
	 UtilityLog.info("performing bulk release");
	
}

public boolean bulkReleaseIngestions(final String SecGroup) {
	boolean release = false;
	int count=0;
	try{
	 try{
		 getPureHitAddButton().waitAndClick(10);
		}catch (Exception e) {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}
	 
	 getBulkActionButton().waitAndClick(10);
	 try {
		Thread.sleep(3000);
	} catch (InterruptedException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	 try{
		 getBulkReleaseAction().waitAndClick(10);
	 }catch (Exception e) {
		 getBulkReleaseActionDL().waitAndClick(10);
	}
	 for (WebElement iterable_element : getSecurityGName().FindWebElements()) {
			if(iterable_element.getText().contains(SecGroup)){
				count=count+1;
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				new Actions(driver.getWebDriver()).moveToElement(getBulkRelDefaultSecurityGroup_CheckBox(count).getWebElement()).click();
				driver.scrollingToBottomofAPage();
		
				getBulkRelDefaultSecurityGroup_CheckBox(count).Click();
			}
		}
//	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//			 getBulkRelDefaultSecurityGroup_CheckBox(SecGroup).Visible()  ;}}), Input.wait60); 
//	 getBulkRelDefaultSecurityGroup_CheckBox(SecGroup).waitAndClick(10);
	 
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getBulkRelease_ButtonRelease().Visible()  ;}}),Input.wait60); 
	 getBulkRelease_ButtonRelease().waitAndClick(20);
	 
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getFinalizeButton().Visible()  ;}}), Input.wait60); 
	 getFinalizeButton().waitAndClick(20);
  
	 if(!base.VerifySuccessMessageB("Records saved successfully")){
		    System.out.println("Execution aborted!");
			UtilityLog.info("Execution aborted!");
			System.out.println("Bulk relese did not go well! take a look and continue!!");
			UtilityLog.info("Bulk relese did not go well! take a look and continue!!");
			System.exit(1);
	 }
	 
	 System.out.println("performed bulk release");
	 UtilityLog.info("performed bulk release");
		release = true;
		
	}finally {
		System.out.println("in");
		return release;
	}
}
//Function to perform bulk untag
public void bulkUnTag(final String TagName) throws InterruptedException{

	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
			 getBulkActionButton().Visible() ;}}), Input.wait60);
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
	 UtilityLog.info("Bulk Untag is done, Tag is : "+TagName);
	 
	 //Since page is freezing after bulk actiononly in automation, lets reload page to avoid it..
	 driver.getWebDriver().navigate().refresh();
}

public void bulkUnTag_popUp(final String TagName) throws InterruptedException{
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getBulkUntagbutton().Visible()  ;}}), Input.wait30); 
	 getBulkUntagbutton().Click();//
	
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
	 UtilityLog.info("Bulk Untag is done, Tag is : "+TagName);
	 
	 //Since page is freezing after bulk actiononly in automation, lets reload page to avoid it..
	 driver.getWebDriver().navigate().refresh();//
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
	 UtilityLog.info("Bulk Unfolder is done, folder is : "+folderName);
	 
	 //Since page is freezing after bulk actiononly in automation, lets reload page to avoid it..
	 driver.getWebDriver().navigate().refresh();
}
public void bulkUnFolder_popUp(final String foldername) throws InterruptedException{
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getBulkUnFolderbutton().Visible()  ;}}), Input.wait30); 
		 getBulkUnFolderbutton().Click();
		 
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getSelectFolderExisting(foldername).Visible()  ;}}), Input.wait60); 
		 getSelectFolderExisting(foldername).waitAndClick(5);
		 
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			    	getContinueCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
		 getContinueButton().Click();
		 
		 final BaseClass bc = new BaseClass(driver);
		 final int Bgcount = bc.initialBgCount();

		 bc.VerifySuccessMessage("Records saved successfully");
		 
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				bc.initialBgCount() == Bgcount+1  ;}}), Input.wait60); 
		 System.out.println("Bulk Unfolder is done, folder is : "+foldername);
		 UtilityLog.info("Bulk Unfolder is done, folder is : "+foldername);
	   	assertion.assertAll();//
}


public void selectTagInASwp(String tagName) {
	
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getWP_TagBtn().Visible()  ;}}), Input.wait30); 
	 getWP_TagBtn().Click();
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getTree().Visible()  ;}}), Input.wait30); 
	 System.out.println(getTree().FindWebElements().size());
	 UtilityLog.info(getTree().FindWebElements().size());
		for (WebElement iterable_element : getTree().FindWebElements()) {
			if(iterable_element.getText().contains(tagName)){
				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
				driver.scrollingToBottomofAPage();
				iterable_element.click();
			}
		}
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getMetaDataInserQuery().Visible()  ;}}), Input.wait30); 
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
	UtilityLog.info(getTree().FindWebElements().size());
	for (WebElement iterable_element : getTree().FindWebElements()) {
		if(iterable_element.getText().contains(folderName)){
			new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
			driver.scrollingToBottomofAPage();
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
	 UtilityLog.info(getSecurityNamesTree().FindWebElements().size());
		for (WebElement iterable_element : getSecurityNamesTree().FindWebElements()) {
			//System.out.println(iterable_element.getText());
			if(iterable_element.getText().contains(sgname)){
				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
				driver.scrollingToBottomofAPage();
				iterable_element.click();
			}
		}
		driver.scrollingToBottomofAPage();
		getMetaDataInserQuery().waitAndClick(5);
	driver.scrollPageToTop();

}

public void selectOperator(String operator) {
	driver.scrollPageToTop();
	getOperatorDD().Click();
	if(operator.equalsIgnoreCase("AND")){
//		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//				getOperatorAND().Visible()  ;}}), Input.wait60); //increased time from 30 to 60 secs
		getOperatorAND().ElementToBeClickableExplicitWait(getOperatorAND(), 10000);
		getOperatorAND().Click();	
	}
	if(operator.equalsIgnoreCase("OR")){
//		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//				getOperatorOR().Visible()  ;}}), Input.wait60); 
		getOperatorOR().ElementToBeClickableExplicitWait(getOperatorOR(), 10000);
		getOperatorOR().waitAndClick(5);	
	}
	if(operator.equalsIgnoreCase("NOT")){
//		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
//				getOperatorNOT().Visible()  ;}}), Input.wait60); 
		getOperatorNOT().ElementToBeClickableExplicitWait(getOperatorNOT(), 10000);
		getOperatorNOT().Click();	
	}


}

//Function to perform redaction name search. 
public void  selectRedactioninWPS(final String redactName) throws InterruptedException{
	 
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getRedactionBtn().Visible()  ;}}), Input.wait30); 
	getRedactionBtn().Click();
	
	//
	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getTree().Visible()  ;}}), Input.wait30);
	System.out.println(getTree().FindWebElements().size());
	UtilityLog.info(getTree().FindWebElements().size());
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
	UtilityLog.info(getTree().FindWebElements().size());
	for (WebElement iterable_element : getTree().FindWebElements()) {
		//System.out.println(iterable_element.getText());
		if(iterable_element.getText().contains(assignMentName)){
			
			new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();
			driver.scrollingToBottomofAPage();
			System.out.println(iterable_element.getText());
			UtilityLog.info(iterable_element.getText());
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

public int serarchWP() throws InterruptedException {
	driver.scrollPageToTop();
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getQuerySearchButton().Visible()  ;}}), Input.wait30); 
	getQuerySearchButton().waitAndClick(5);
	//verify counts for all the tiles
	driver.waitForPageToBeReady();
//	getPureHitsCount().VisibilityOfElementExplicitWait(getPureHitsCount(), 10000);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait90);
		
		int pureHit = Integer.parseInt(getPureHitsCount().getText());
		System.out.println("Search is done and PureHit is : "+pureHit);
		UtilityLog.info("Search is done and PureHit is : "+pureHit);
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
	 UtilityLog.info("Bulk folder is done, folder is : "+folderName);
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
	 UtilityLog.info("Bulk Tag is done, Tag is : "+TagName);
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
	   	System.out.println("Saved search with name - "+searchName);
	   	UtilityLog.info("Saved search with name - "+searchName);
		}

public void Removedocsfromresults() {
	
	try {
		getRemovedocsfromresult().waitAndClick(20);
	}
	catch(Exception e)
	{
		System.out.println("No docs present in cart");
		UtilityLog.info("No docs present in cart");
	}
	
}


  public void quickbatch() {
	driver.getWebDriver().get(Input.url+"Search/Searches");
	 try{
		 getPureHitAddButton().Click();
		}catch (Exception e) {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}
	 
	 getBulkActionButton().waitAndClick(10);
	
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			 getBulkAssignAction().Visible()  ;}}), Input.wait60); 
	
     
	 getQuickBatchAction().Click();
	 
	 System.out.println("performing quick batch");
	 UtilityLog.info("performing quick batch");
	
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
		UtilityLog.info("NO pop up appears");
		}
  	
  	//verify counts for all the tiles
  	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getPureHitsCountwithOptions().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait90);
  	
  	int pureHit = Integer.parseInt(getPureHitsCountwithOptions().getText());
  	System.out.println("Serach is done for "+SearchString+" and PureHit is : "+pureHit);
  	UtilityLog.info("Serach is done for "+SearchString+" and PureHit is : "+pureHit);
  	
  	String backgroundColorthread = driver.FindElementByXPath(".//*[@id='002']").GetCssValue("background-color");
	System.out.println(backgroundColorthread);
	UtilityLog.info(backgroundColorthread);
	
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
		 UtilityLog.info(getTree().FindWebElements().size());
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
			UtilityLog.info(alloptions.size());
			
			for (WebElement options : getadwp_assgn_distributedto().FindWebElements())
			{
				System.out.println(options.getText());
				UtilityLog.info(options.getText());
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
				UtilityLog.info("Search is done and PureHit is : "+pureHit);
			 			
			

	}

 }
