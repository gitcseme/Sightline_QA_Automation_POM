package pageFactory;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import testScriptsSmoke.Input;

public class AIPage {

	Driver driver;
	BaseClass base;
	LoginPage login;
	ReportsPage report;
	String searchname;
	SoftAssert softassert;
	DocViewPage docview;
	AssignmentsPage assign;
	DocListPage doclist;
	SessionSearch search;

	/**
	 * @Author Swathi
	 * @return
	 */
	public Element getManage() {
		return driver.FindElementByXPath("//a[@name='Manage']");
	}

	public Element getProject() {
		return driver.FindElementByXPath("//a[@name='Project']");
	}

	public Element getProjectName() {
		return driver.FindElementByXPath("//input[@placeholder='Filter by Name']");
	}

	public Element getFilter() {
		return driver.FindElementByXPath("//a[@id='btnFilter']");
	}

	public Element getEditProject() {
		return driver.FindElementByXPath("//a[@class='btn btn-primary btn-xs' and contains(text(),'Edit')]");
	}

	public Element getAIEnabledStatus() {
		return driver.FindElementByXPath("//input[@id='chkIsAIEnabled' and contains(@disabled,'disabled')]");
	}

	public Element getResponsiveModelHeader() {
		return driver.FindElementByXPath("//div[@id='cardCanvas']//ul//li//a[contains(text(),'Responsive Model')]");
	} 

	public Element getActionDropdown() {
		return driver.FindElementByXPath("//div[@id='cardCanvas']//ul//li//input[contains(@value,'Actions')]");
	}

	public ElementCollection getResponsiveModelDetails() {
		return driver.FindElementsByXPath("//div[@class='col-xs-6 font-xs']");
	}

	public Element getSelectAllDocs() {
		return driver.FindElementByXPath("//th[contains(@class,'no-search select-checkbox ')]/label");
	}

	public Element getSelectYesAllpages() {
		return driver.FindElementByXPath("//input[@id='Yes']");
	}

	public Element getSelectNoAllpages() {
		return driver.FindElementByXPath("//input[@id='No']");
	}

	public Element getSelectOk() {
		return driver.FindElementByXPath("//button[contains(text(),'Ok')]");
	}
	public Element getSelectCancel() {
		return driver.FindElementByXPath("//button[contains(text(),'No')]");
	}

	public Element getDocExploreAction() {
		return driver.FindElementByXPath("//button[@id='idDocExplorerActions']/span");
	}

	public Element getBulkTag() {
		return driver.FindElementById("idBulkTag");
	}

	public Element getBulkFolder() {
		return driver.FindElementById("idBulkFolder");
	}

	public Element getNewFolder() {
		return driver.FindElementById("tabNew");
	}

	public Element getAllFolders() {
		return driver.FindElementByXPath("//div[@id='folderJSTree']//a[contains(text(),'All Folders')]");
	}

	public Element getFolderNameTextBox() {
		return driver.FindElementByXPath("//input[@id='txtFolderName']");
	}

	public Element getFolderContinue() {
		return driver.FindElementByXPath("//button[@id='btnAdd']");
	}

	public Element getFinalizeFolder() {
		return driver.FindElementByXPath("//button[@id='btnfinalizeAssignment']");
	}

	public Element getNewTag() {
		return driver.FindElementById("tabNew");
	}

	public Element getAllTags() {
		return driver.FindElementByXPath("//div[@id='tagsJSTree']//a[contains(text(),'All Tags')]");
	}

	public Element getTagNameTextBox() {
		return driver.FindElementByXPath("//input[@id='txtTagName']");
	}

	public Element getTagContinue() {
		return driver.FindElementByXPath("//button[@id='btnAdd']");
	}

	public Element getFinalizeTag() {
		return driver.FindElementByXPath("//button[@id='btnfinalizeAssignment']");
	}

	public Element getWarningOk() {
		return driver.FindElementByXPath("//button[@id='bot1-Msg1']");
	}
	public Element getSelectTag(String tag) {
		return driver.FindElementByXPath("//a[contains(text(),'"+tag+"')]");
	}

	public Element getDefaultAction() {
		return driver.FindElementByXPath("//button[@data-toggle='dropdown']");
	}

	public Element geEditTag() {
		return driver.FindElementByXPath("//a[@id='aEditTagTagGroup']");
	}

	public Element getEnableAIToggle() {
		return driver.FindElementByXPath("(//label[@class='toggle']//i)[3]");
	}
	public Element getDisableAIToggle() {
		return driver.FindElementByXPath("//button[contains(text(),'Yes, Disable AI')]");
	}

	public Element getSaveEditTag() {
		return driver.FindElementByXPath("//button[@id='btnUpdateTag']");
	}
	public Element getTagModel(String tag) {
		return driver.FindElementByXPath("//a[contains(text(),'"+tag+"')]");
	}
	public Element getModelActions() {
		return driver.FindElementByXPath("(//button[@id='idAction'])[1]");
	}
	public Element getEditModelConfig() {
		return driver.FindElementByXPath("(//a[contains(text(),'Edit Model Config')])[1]");
	}
	public Element getDeleteModelConfig() {
		return driver.FindElementByXPath("(//a[contains(text(),'Delete')])[2]");
	}
	/*
	public Element getDeleteModelConfigDup() {
		return driver.FindElementByXPath("//a[contains(text(),'Tag1 9148991 Model')]/../../..//a[contains(text(),'Delete')]");
	} */
	
	public Element getNext() {
		return driver.FindElementByXPath("//a[@id='btnConfirm']");
	}
	public Element getSubsetOfDocs() {
		return driver.FindElementByXPath("//input[@id='SubsetOfDocuments']");
	}
	public Element getSubsetFolder(String folder) {
		return driver.FindElementByXPath("//a[@class='jstree-anchor' and contains(text(),'"+folder+"')]");
	}
	public Element getYesbutton() {
		return driver.FindElementByXPath("//button[@id='bot1-Msg1']");
	}
	public Element getTagname(String tag) {
		return driver.FindElementByXPath("//span[@id='tagName'and contains(text(),'"+tag+"')]");
	}

	public ElementCollection getUnChecked() {
		return driver.FindElementsByXPath("//input[@checked='checked']");
	}
	public ElementCollection getChecked() {
		return driver.FindElementsByXPath("//label[@class='checkbox']/input[1]");
	}

	public Element get1stID() {
		return driver.FindElementByXPath("(//div[@class='text-wrap popout'])[1]");
	}
	public ElementCollection getInProgressStatus(String tag) {
		return driver.FindElementsByXPath("//a[contains(text(),'"+tag+"')]/../../..//div[contains(text(),'INPROGRESS')]");
	}
	public ElementCollection getCompleteStatus(String tag) {
		return driver.FindElementsByXPath("//a[contains(text(),'"+tag+"')]/../../..//div[contains(text(),'COMPLETE')]");
	}
	public ElementCollection getFailStatus(String tag) {
		return driver.FindElementsByXPath("//a[contains(text(),'"+tag+"')]/../../..//div[contains(text(),'FAIL')]");
	}
	public Element getNextPage() {
		return driver.FindElementByXPath("(//li[@class='paginate_button ']/a)[1]");
	}
	public Element getSelectedDocCount() {
		return driver.FindElementByXPath("//div[@class='bulkActionsSpanLoderTotal']");
	}

	public Element getNewCodingForm() {
		return driver.FindElementByXPath("//button[contains(text(),'New Coding Form')]");
	}
	public Element getSelectTaginGroup(String tag) {
		return driver.FindElementByXPath("//strong[contains(text(),'"+tag+"')]");
	}
	public Element getSelectRadioGroup() {
		return driver.FindElementByXPath("//strong[contains(text(),'Radio Group')]");
	}
	public Element getSelectComments() {
		return driver.FindElementByXPath("//span[contains(text(),'COMMENTS')]");
	}
	public Element getSelectDocComments() {
		return driver.FindElementByXPath("//strong[contains(text(),'Document_Comments')]");
	}
	public Element getAddToForm() {
		return driver.FindElementByXPath("//a[contains(text(),'Add to Form')]");
	}
	public Element getFormName() {
		return driver.FindElementByXPath("//input[contains(@id,'txtFormName')]");
	}
	public Element getTagType() {
		return driver.FindElementByXPath("//select[contains(@class,'tag-type form-control input-sm')]");
	}
	public Element getRadioItem() {
		return driver.FindElementByXPath("//option[contains(text(),'Radio Item')]");
	}
	public Element getRadioGroup() {
		return driver.FindElementByXPath("//select[contains(@class,'tag-radiogroup-select form-control input-sm')]");
	}
	public Element getSelectRadioGroupAssociation() {
		return driver.FindElementByXPath("//option[contains(@value,'radiogroup_2')]");
	}
	public Element getSave() {
		return driver.FindElementByXPath("//a[(text()='Save')]");
	}
	public Element getYes() {
		return driver.FindElementByXPath("//button[(@id='btnYes')]");
	}
	public Element getDocExp_SelectAllDocs(){ return driver.FindElementByXPath("(//*[@id='selectAllDocuments']/following-sibling::i)[1]"); }
	public Element getDocExp_actionButton(){ return driver.FindElementById("idDocExplorerActions"); }
	public Element getDocExp_action_quickbatch(){ return driver.FindElementById("idQuickAssign"); }
	public Element getDocExp_action_bulkAssign(){ return driver.FindElementById("idBulkAssign"); }

	public Element getDocViewAction(){ return driver.FindElementById("idViewInDocView"); }
	public Element getDocListAction(){ return driver.FindElementById("idViewInDocList"); }

	public Element getDocExp_Tags(){ return driver.FindElementByXPath("//li[contains(text(),'Tags')]"); }
	public Element getApplyFilter(){ return driver.FindElementByXPath("//*[@id='btnAppyFilter']"); }
	public Element getDocExp_ViewRight(){ return driver.FindElementByXPath("//i[contains(@class,'fa fa-chevron-left menu-icon')]"); }
	public Element SelectColumnBtn() {
		return driver.FindElementById("btnSelectColumn");
	}
	public Element getScoresTab() {
		return driver.FindElementByXPath("//font[text()='Scores ']/parent::a");
	}
	public Element getAllAIScores() {
		return driver.FindElementByXPath("//div[@id='headingTwo']//a[1]");
	}

	public ElementCollection getAIScores() {
		return driver.FindElementsByXPath("//div[@id='AiScoreTree']//a[@class='jstree-anchor']");
	}
	public Element getAddtoSelected() {
		return driver.FindElementByXPath("//a[contains(text(),'Add to Selected')]");
	}
	public Element GetSelect() {
		return driver.FindElementByXPath("//input[@title='Select']");
	}
	public Element GetAIScoresHeader() {
		return driver.FindElementByXPath("//table[@id='dtDocList']//th[9]");
	}
	public Element Get1stAIScore() {
		return driver.FindElementByXPath("(//td[@class='sorting_1'])[1]");
	}
	public ElementCollection getAIScoresinDocList() {
		return driver.FindElementsByXPath("//td[@class='sorting_1']");
	}
	public ElementCollection getDocIdsinDocList() {
		return driver.FindElementsByXPath("//td[contains(text(),'ID')]");
	}
	public Element getActionview(){ return driver.FindElementByLinkText("View");} 

	public Element getAsNameinDashboard(String asName) {
		return driver.FindElementByXPath("//strong[contains(text(),'"+asName+"')]");
	}

	public Element getAsNameinDocView(String asName) {
		return driver.FindElementByXPath("//h2[contains(@title,'"+asName+"')]");
	}
	public Element get1stDocID() {
		return driver.FindElementByXPath("//tr[contains(@class,'rowNumber_0')]//td[2]");
	}
	public Element get5thDocID() {
		return driver.FindElementByXPath("//tr[contains(@class,'rowNumber_4')]//td[2]");
	}
	public Element getPrediction() {
		return driver.FindElementByXPath("//span[contains(@class,'aitag')]");
	}
	public Element getSortByAIScore() {
		return driver.FindElementByXPath("//a[contains(text(),'SORT BY AI SCORE')]");
	}
	public Element getSelectAIScoreField() {
		return driver.FindElementByXPath("//select[@id='ddlAIScore']");
	}
	public Element getSelectAITag(String tag) {
		return driver.FindElementByXPath("//option[contains(text(),'"+tag+"')]");
	}
	public Element getSaveinEditAssignment() {
		return driver.FindElementByXPath("//input[@class='btn btn-primary save-btn']");
	}
	public Element getNumberOfDcosToBeShown() {
		return driver.FindElementByXPath("//*[@id='idPageLength']");
	}

	public AIPage(Driver driver) {

		this.driver = driver;
		driver.waitForPageToBeReady();
		base = new BaseClass(driver);
		doclist = new DocListPage(driver);
		search = new SessionSearch(driver);

	}



	public void verifyAIEnabled(String projectName) throws InterruptedException {

		driver.getWebDriver().get(Input.url+ "Project/Project");
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getProjectName().Visible()  ;}}), Input.wait30);

		base.waitForElement(getProjectName());
		getProjectName().SendKeys(projectName);
		base.waitForElement(getFilter());
		getFilter().waitAndClick(10);
		base.waitTime(1);
		base.waitForElement(getEditProject());
		getEditProject().waitAndClick(10);
		base.waitTime(2);
		driver.scrollingToBottomofAPage();
		base.waitTime(2);
		boolean status = base.ValidateElement_PresenceReturn(getAIEnabledStatus());
		if(status == true) {
			base.passedStep("AI is enabled for the project - "+projectName);
			System.out.println("AI is enabled for the project - "+projectName);
		}
		else {
			base.failedStep("AI is not Enabled for the given project.");
			System.out.println("AI is not Enabled for the given project.");
		}

		base.waitTime(2);

	}

	public void verifyResponsiveModel() throws InterruptedException {

		driver.getWebDriver().get(Input.url+ "Ai/AiHome");
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getResponsiveModelHeader().Visible()  ;}}), Input.wait30);

		boolean header = base.ValidateElement_PresenceReturn(getResponsiveModelHeader());
		if(header == true) {
			base.passedStep("Responsive Model header is displaying");
			System.out.println("Responsive Model header is displaying.");

			//softassert.assertTrue(getActionDropdown().getWebElement().isDisplayed(),"Action Dropdown is displaying.");
			List<WebElement> details = getResponsiveModelDetails().FindWebElements();
			System.out.println("Printing the details from Responsive Model tile");
			UtilityLog.info("Printing the details from Responsive Model tile");

			for(WebElement val : details) {
				System.out.println(val.getText());
				UtilityLog.info(val.getText());
			}
		}
		else {
			base.failedStep("Responsive Model header is not displaying");
			System.out.println("Responsive Model header is not displaying.");
		}


	}

	public void bulkFolder(String folder) throws InterruptedException {
		// TODO Auto-generated method stub
		this.driver.getWebDriver().get(Input.url+"DocExplorer/Explorer");
		base.waitTime(3);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectAllDocs().Visible()  ;}}), Input.wait30); 
		getSelectAllDocs().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectYesAllpages().Visible()  ;}}), Input.wait30); 
		getSelectYesAllpages().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectOk().Visible()  ;}}), Input.wait30); 
		getSelectOk().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocExploreAction().Visible()  ;}}), Input.wait30); 
		getDocExploreAction().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getBulkFolder().Visible()  ;}}), Input.wait30); 
		getBulkFolder().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNewFolder().Visible()  ;}}), Input.wait30); 
		getNewFolder().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getAllFolders().Visible()  ;}}), Input.wait30); 
		getAllFolders().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getFolderNameTextBox().Visible()  ;}}), Input.wait30); 
		getFolderNameTextBox().SendKeys(folder);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectedDocCount().Visible()  ;}}), Input.wait30); 
		Reporter.log("Selected Documents for Bulk Folder - "+getSelectedDocCount().getText());
		System.out.println("Selected Documents for Bulk Folder - "+getSelectedDocCount().getText());

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getFolderContinue().Visible()  ;}}), Input.wait30); 
		getFolderContinue().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getFinalizeFolder().Visible()  ;}}), Input.wait30); 
		getFinalizeFolder().waitAndClick(10);

		base.VerifySuccessMessage("Records saved successfully");
		base.CloseSuccessMsgpopup();
		Reporter.log("Folder "+folder+" added to security group -",true);
		UtilityLog.info("Folder Successful");


	}

	public void bulkTag(String tag) throws InterruptedException {
		// TODO Auto-generated method stub

		this.driver.getWebDriver().get(Input.url+"DocExplorer/Explorer");
		base.waitTime(2);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				get1stID().Visible()  ;}}), Input.wait30); 
		base.waitTime(1);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectAllDocs().Visible()  ;}}), Input.wait30); 
		getSelectAllDocs().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectOk().Visible()  ;}}), Input.wait30); 
		getSelectOk().waitAndClick(10);

		base.waitTime(1);
		getNextPage().waitAndClick(100);
		base.waitTime(1);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				get1stID().Visible()  ;}}), Input.wait30); 
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectAllDocs().Visible()  ;}}), Input.wait30); 
		getSelectAllDocs().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectOk().Visible()  ;}}), Input.wait30); 
		getSelectOk().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocExploreAction().Visible()  ;}}), Input.wait30); 
		getDocExploreAction().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getBulkTag().Visible()  ;}}), Input.wait30); 
		getBulkTag().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNewTag().Visible()  ;}}), Input.wait30); 
		getNewTag().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getAllTags().Visible()  ;}}), Input.wait30); 
		getAllTags().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTagNameTextBox().Visible()  ;}}), Input.wait30); 
		getTagNameTextBox().SendKeys(tag);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectedDocCount().Visible()  ;}}), Input.wait30); 
		Reporter.log("Selected Documents for Bulk Tag - "+getSelectedDocCount().getText());
		System.out.println("Selected Documents for Bulk Tag - "+getSelectedDocCount().getText());

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTagContinue().Visible()  ;}}), Input.wait30); 
		getTagContinue().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getFinalizeTag().Visible()  ;}}), Input.wait30); 
		getFinalizeTag().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getWarningOk().Visible()  ;}}), Input.wait30); 
		getWarningOk().waitAndClick(10);


		base.VerifySuccessMessage("Records saved successfully");
		Reporter.log("Tag '"+tag+"' is added successfully to security group " ,true);
		UtilityLog.info("Tag Successful");

		base.CloseSuccessMsgpopup();
	}

	public void EnableAI(String tag, String folder) throws InterruptedException {
		// TODO Auto-generated method stub

		this.driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");

		//driver.scrollingToElementofAPage(getSelectTag(tag));

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectTag(tag).Visible()  ;}}), Input.wait30); 
		getSelectTag(tag).waitAndClick(10);

		//driver.scrollingToElementofAPage(getDefaultAction());
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDefaultAction().Visible()  ;}}), Input.wait30); 
		getDefaultAction().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				geEditTag().Visible()  ;}}), Input.wait30); 
		geEditTag().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getEnableAIToggle().Visible()  ;}}), Input.wait30); 
		getEnableAIToggle().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSaveEditTag().Visible()  ;}}), Input.wait30); 
		getSaveEditTag().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getYesbutton().Visible()  ;}}), Input.wait30); 
		getYesbutton().waitAndClick(10);

		UtilityLog.info("Enabled AI for Tag "+tag);
		base.VerifySuccessMessage("Tag updated successfully");
		Reporter.log("Enabled AI for Tag "+tag);
		base.CloseSuccessMsgpopup();

		base.waitTime(3);
		this.driver.getWebDriver().get(Input.url+"AI/AIHome");

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTagModel(tag).Visible()  ;}}), Input.wait30); 

		UtilityLog.info("AI Model is displaying after enabling the AI for tag.");
		Reporter.log("AI Model is displaying after enabling the AI for tag.");

		getModelActions().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getEditModelConfig().Visible()  ;}}), Input.wait30); 
		getEditModelConfig().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTagname(tag).Visible()  ;}}), Input.wait30); 
		Reporter.log("Navigated to Edit Model Configuration screen.");
		UtilityLog.info("Navigated to Edit Model Configuration screen.");
		boolean displayed = getTagname(tag).isDisplayed();
		if(displayed == true) {
			System.out.println("Created Tag name is is dplsying in Edit Model Configuration screen");
		}
		else {
			System.out.println("Created Tag Name is not displaying in Edit Model Configuration screen. Please check !!!");
		}


		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNext().Visible()  ;}}), Input.wait30); 
		getNext().waitAndClick(10);
		base.waitTime(3);
		UtilityLog.info("Navigated to Training Configuration tab.");
		Reporter.log("Navigated to Training Configuration tab.");

		//Unchecking all File types to be included in training:
		List<WebElement> uncheck = getUnChecked().FindWebElements();
		for(int i=0;i<uncheck.size();i++) {
			if(i>0 && i<7) {
				uncheck.get(i).click();
			}
		}

		//Selecting all File types to be included in training:
		base.waitTime(1);
		List<WebElement> check = getChecked().FindWebElements();
		for(int i=0;i<check.size();i++) {
			if(i>=0 && i<11) {
				check.get(i).click();
			}
		}
		UtilityLog.info("Selected All File types to be included in training");
		Reporter.log("Selected All File types to be included in training");

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNext().Visible()  ;}}), Input.wait30); 
		getNext().waitAndClick(10);

		UtilityLog.info("Navigated to Scoring Configuaration tab.");
		Reporter.log("Navigated to Scoring Configuaration tab.");

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSubsetOfDocs().Visible()  ;}}), Input.wait30); 
		getSubsetOfDocs().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSubsetFolder(folder).Visible()  ;}}), Input.wait30); 
		getSubsetFolder(folder).waitAndClick(10);

		UtilityLog.info("Selected Folder for Subset of Documents.");
		Reporter.log("Selected Folder for Subset of Documents.");

		base.waitTime(1);
		//Unchecking all File types to be included in training:
		List<WebElement> uncheck1 = getUnChecked().FindWebElements();
		for(int i=0;i<uncheck1.size();i++) {
			uncheck1.get(i).click();
		}

		//Selecting all File types to be included in training:
		base.waitTime(1);
		List<WebElement> check1 = getChecked().FindWebElements();
		for(int i=0;i<check1.size();i++) {
			check1.get(i).click();
		}

		UtilityLog.info("Selected All File types to be included in scoring");
		Reporter.log("Selected All File types to be included in scoring");

		base.waitTime(1);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNext().Visible()  ;}}), Input.wait30); 
		getNext().waitAndClick(10);

		UtilityLog.info("Edit Model COnfiguartion is completed and AI has been enabled for the tag.");

		base.passedStep("Edit Model Configuartion is completed and AI has been enabled for the tag.");



	}
	
	public void EnableAIAllDcos(String tag) throws InterruptedException {
		// TODO Auto-generated method stub

		this.driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");

		//driver.scrollingToElementofAPage(getSelectTag(tag));

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectTag(tag).Visible()  ;}}), Input.wait30); 
		getSelectTag(tag).waitAndClick(10);

		//driver.scrollingToElementofAPage(getDefaultAction());
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDefaultAction().Visible()  ;}}), Input.wait30); 
		getDefaultAction().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				geEditTag().Visible()  ;}}), Input.wait30); 
		geEditTag().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getEnableAIToggle().Visible()  ;}}), Input.wait30); 
		getEnableAIToggle().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSaveEditTag().Visible()  ;}}), Input.wait30); 
		getSaveEditTag().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getYesbutton().Visible()  ;}}), Input.wait30); 
		getYesbutton().waitAndClick(10);

		UtilityLog.info("Enabled AI for Tag "+tag);
		base.VerifySuccessMessage("Tag updated successfully");
		Reporter.log("Enabled AI for Tag "+tag);
		base.CloseSuccessMsgpopup();

		base.waitTime(3);
		this.driver.getWebDriver().get(Input.url+"AI/AIHome");

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTagModel(tag).Visible()  ;}}), Input.wait30); 

		UtilityLog.info("AI Model is displaying after enabling the AI for tag.");
		Reporter.log("AI Model is displaying after enabling the AI for tag.");

		getModelActions().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getEditModelConfig().Visible()  ;}}), Input.wait30); 
		getEditModelConfig().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTagname(tag).Visible()  ;}}), Input.wait30); 
		Reporter.log("Navigated to Edit Model Configuration screen.");
		UtilityLog.info("Navigated to Edit Model Configuration screen.");
		boolean displayed = getTagname(tag).isDisplayed();
		if(displayed == true) {
			System.out.println("Created Tag name is is dplsying in Edit Model Configuration screen");
		}
		else {
			System.out.println("Created Tag Name is not displaying in Edit Model Configuration screen. Please check !!!");
		}


		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNext().Visible()  ;}}), Input.wait30); 
		getNext().waitAndClick(10);
		base.waitTime(3);
		UtilityLog.info("Navigated to Training Configuration tab.");
		Reporter.log("Navigated to Training Configuration tab.");

		//Unchecking all File types to be included in training:
		List<WebElement> uncheck = getUnChecked().FindWebElements();
		for(int i=0;i<uncheck.size();i++) {
			if(i>0 && i<7) {
				uncheck.get(i).click();
			}
		}

		//Selecting all File types to be included in training:
		base.waitTime(1);
		List<WebElement> check = getChecked().FindWebElements();
		for(int i=0;i<check.size();i++) {
			if(i>=0 && i<11) {
				check.get(i).click();
			}
		}
		UtilityLog.info("Selected All File types to be included in training");
		Reporter.log("Selected All File types to be included in training");

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNext().Visible()  ;}}), Input.wait30); 
		getNext().waitAndClick(10);

		UtilityLog.info("Navigated to Scoring Configuaration tab.");
		Reporter.log("Navigated to Scoring Configuaration tab.");

		UtilityLog.info("Selected All Documents in the Security Group");
		Reporter.log("Selected All Documents in the Security Group");
		base.waitTime(3);
		//Unchecking all File types to be included in training:
		List<WebElement> uncheck1 = getUnChecked().FindWebElements();
		for(int i=0;i<uncheck1.size();i++) {
			uncheck1.get(i).click();
		}

		//Selecting all File types to be included in training:
		base.waitTime(1);
		List<WebElement> check1 = getChecked().FindWebElements();
		for(int i=0;i<check1.size();i++) {
			check1.get(i).click();
		}

		UtilityLog.info("Selected All File types to be included in scoring");
		Reporter.log("Selected All File types to be included in scoring");

		base.waitTime(1);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNext().Visible()  ;}}), Input.wait30); 
		getNext().waitAndClick(10);

		UtilityLog.info("Edit Model COnfiguartion is completed and AI has been enabled for the tag.");

		base.passedStep("Edit Model Configuartion is completed and AI has been enabled for the tag.");

	}
	

	public void verifyScoringProgress(String tag) throws InterruptedException{

		this.driver.getWebDriver().get(Input.url+"AI/AIHome");
		int refreshCount = 10;
		System.out.println("Verifying In progress status");
		base.stepInfo("Verifying In progress status of AI Model");
		for(int i=0;i<refreshCount;i++) {
			List<WebElement> ele = getInProgressStatus(tag).FindWebElements();
			Thread.sleep(10000);
			if(ele.size() > 0){
				UtilityLog.info("Training and Scoring is started and status is displaying as -");
				Reporter.log("Training and Scoring is started and status is displaying as - " +ele.get(0).getText());
				System.out.println(ele.get(0).getText());
				UtilityLog.info(ele.get(0).getText());
				break;
			}
			else {
				driver.Navigate().refresh();
				System.out.println(i);
				Thread.sleep(20000);
			}
		}
		base.passedStep("T/S started successfully");

	}

	public void verifyScoringComplete(String tag) throws InterruptedException{

		this.driver.getWebDriver().get(Input.url+"AI/AIHome");
		int refreshCount = 20;
		System.out.println("Verifying Complete or Failed status");
		base.stepInfo("Verifying Complete or Failed status");
		for(int i=0;i<refreshCount;i++) {
			List<WebElement> ele = getCompleteStatus(tag).FindWebElements();
			List<WebElement> ele1 = getFailStatus(tag).FindWebElements();
			if(ele.size()>0){
				Thread.sleep(20000);
				UtilityLog.info("Training and Scoring is Completed and status is displaying as -");
				base.passedStep("Training and Scoring is Completed and status is displaying as - " +ele.get(0).getText());
				System.out.println(ele.get(0).getText());
				UtilityLog.info(ele.get(0).getText());
				break;
			}
			else if(ele1.size()>0){
				Thread.sleep(20000);
				UtilityLog.info("Training and Scoring is failed and status is displaying as -");
				Reporter.log("Training and Scoring is failed and status is displaying as - " +ele1.get(0).getText());
				System.out.println(ele1.get(0).getText());
				UtilityLog.info(ele1.get(0).getText());
				base.failedStep("Training and Scoring is failed.");
				break;
			}
			else if(i==14) {
				System.out.println("Training and Scoring is not completed even after 10 plus minutes of time. Please check after sometime...");
				UtilityLog.info("Training and Scoring is not completed even after 20 plus minutes of time. Please check after sometime...");
				base.failedStep("Training and Scoring is not completed even after 20 plus minutes of time. Please check after sometime...");
			}
			else {
				driver.Navigate().refresh();
				System.out.println(i);
				Thread.sleep(30000);
			}
		}

	}

	public void SelectDocsInDocExplorer(String tag) throws InterruptedException{

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocExp_Tags().Visible()  ;}}), Input.wait30); 
		getDocExp_Tags().waitAndClick(10);
		doclist.include(tag);
		getApplyFilter().waitAndClick(10);
		base.waitTime(2);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectAllDocs().Visible()  ;}}), Input.wait30); 
		getSelectAllDocs().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectYesAllpages().Visible()  ;}}), Input.wait30); 
		getSelectYesAllpages().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectOk().Visible()  ;}}), Input.wait30); 
		getSelectOk().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocExploreAction().Visible()  ;}}), Input.wait30); 
		getDocExploreAction().waitAndClick(10);
		base.waitTime(2);
		new Actions(driver.getWebDriver()).moveToElement(getDocExp_ViewRight().getWebElement()).build().perform();
		base.waitTime(2);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocListAction().Visible()  ;}}), Input.wait60); 
		Actions act = new Actions(driver.getWebDriver()).moveToElement(getDocListAction().getWebElement());
		act.click().build().perform();
		base.stepInfo("Selected View In DocList");

	}
	public void SelectDocsInDocExplorerExclude(String tag) throws InterruptedException{

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocExp_Tags().Visible()  ;}}), Input.wait30); 
		getDocExp_Tags().waitAndClick(10);
		doclist.exclude(tag);
		getApplyFilter().waitAndClick(10);
		base.waitTime(2);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectAllDocs().Visible()  ;}}), Input.wait30); 
		getSelectAllDocs().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectYesAllpages().Visible()  ;}}), Input.wait30); 
		getSelectYesAllpages().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectOk().Visible()  ;}}), Input.wait30); 
		getSelectOk().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocExploreAction().Visible()  ;}}), Input.wait30); 
		getDocExploreAction().waitAndClick(10);
		base.waitTime(2);
		new Actions(driver.getWebDriver()).moveToElement(getDocExp_ViewRight().getWebElement()).build().perform();
		base.waitTime(2);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocListAction().Visible()  ;}}), Input.wait60); 
		Actions act = new Actions(driver.getWebDriver()).moveToElement(getDocListAction().getWebElement());
		act.click().build().perform();
		base.stepInfo("Selected View In DocList");

	}

	public void verifyScoringsInDocList(String tag) throws InterruptedException{

		driver.waitForPageToBeReady();

		base.waitForElement(SelectColumnBtn());
		SelectColumnBtn().waitAndClick(10);
		base.waitForElement(getScoresTab());
		getScoresTab().waitAndClick(10);
		base.waitForElement(getAllAIScores());
		getAllAIScores().waitAndClick(10);
		base.waitTime(3);
		System.out.println(getAIScores().FindWebElements().size());
		UtilityLog.info(getAIScores().FindWebElements().size());
		for (WebElement iterable_element : getAIScores().FindWebElements() ){
			System.out.println(iterable_element.getText());
			if (iterable_element.getText().contains(tag)) {

				new Actions(driver.getWebDriver()).moveToElement(iterable_element).click();

				iterable_element.click();
			}
		}
		base.waitTime(2);
		base.stepInfo("Selected AI Scored Tag");

		base.waitForElement(getAddtoSelected());
		getAddtoSelected().waitAndClick(10);
		base.waitForElement(GetSelect());
		GetSelect().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				GetAIScoresHeader().Visible()  ;}}), Input.wait30); 
		String name = GetAIScoresHeader().getText();
		if(name.contains(tag)) {
			base.stepInfo("Trained AI Model is displaying as AI Score Header");
		}
		else {
			base.failedMessage("Trained AI Model is not  displaying as AI Score Header");
		}
		GetAIScoresHeader().waitAndClick(10);
		base.waitTime(2);
		base.stepInfo("Min AI Score - "+Get1stAIScore().getText());
		GetAIScoresHeader().waitAndClick(10);
		base.waitTime(2);
		base.stepInfo("Max AI Score - "+Get1stAIScore().getText());


	}

	public void CreateCodingform(String cfName, String tag) throws InterruptedException {

		//Actions action = new Actions((WebDriver) driver);
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNewCodingForm().Visible()  ;}}), Input.wait30); 
		getNewCodingForm().waitAndClick(10);
		//WebElement ele = (WebElement) driver.FindElement(By.xpath("//strong[contains(text(),'"+tag+"')]"));
		//action.moveToElement(ele).click().build().perform();
		getSelectTaginGroup(tag).waitAndClick(10);
		getSelectRadioGroup().waitAndClick(10);
		getSelectComments().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectDocComments().Visible()  ;}}), Input.wait30); 
		getSelectDocComments().waitAndClick(10);
		getAddToForm().waitAndClick(10);
		getFormName().SendKeys(cfName);
		base.stepInfo("Coding Form name is given as - "+cfName);
		getTagType().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getRadioItem().Visible()  ;}}), Input.wait30); 
		getRadioItem().waitAndClick(10);

		getRadioGroup().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectRadioGroupAssociation().Visible()  ;}}), Input.wait30); 
		getSelectRadioGroupAssociation().waitAndClick(10);

		getSave().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getYes().Visible()  ;}}), Input.wait30);
		getYes().waitAndClick(10);

		System.out.println("Coding form "+cfName+" created");
		UtilityLog.info("Coding form "+cfName+" created");

		base.VerifySuccessMessage("Coding Form Saved successfully");
		Reporter.log(cfName +"coding Form Successful", true);
		UtilityLog.info("Coding Form Successful");

	}
	public void bulkAssign() throws InterruptedException {
		this.driver.getWebDriver().get(Input.url+ "DocExplorer/Explorer");
		base.waitTime(2);
		base.stepInfo("Navigated to Doc Explorer screen");
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				get1stID().Visible()  ;}}), Input.wait30); 
		base.waitTime(1);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectAllDocs().Visible()  ;}}), Input.wait30); 
		getSelectAllDocs().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectOk().Visible()  ;}}), Input.wait30); 
		getSelectOk().waitAndClick(10);

		base.waitTime(1);
		getNextPage().waitAndClick(100);
		base.waitTime(1);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				get1stID().Visible()  ;}}), Input.wait30); 
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectAllDocs().Visible()  ;}}), Input.wait30); 
		getSelectAllDocs().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectOk().Visible()  ;}}), Input.wait30); 
		getSelectOk().waitAndClick(10);
		base.stepInfo("Selected Documents for Bulk Assign");
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocExploreAction().Visible()  ;}}), Input.wait30); 
		getDocExploreAction().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDocExp_action_bulkAssign().Visible()  ;}}), Input.wait30); 
		getDocExp_action_bulkAssign().Click();

	}

	public void navigateToDocViewFromRMUDashboard(String asName) throws InterruptedException{

		driver.waitForPageToBeReady();
		base.waitForElement(getAsNameinDashboard(asName));
		base.stepInfo("Assignemnt Name is displaying in RMU Dashboard");
		getAsNameinDashboard(asName).waitAndClick(10);

		base.waitForElement(getAsNameinDocView(asName));
		base.stepInfo("Assignemnt Name is displaying in DocView");
		getAsNameinDocView(asName).waitAndClick(10);

	}

	public void deleteAIModel(String tag) throws InterruptedException{

		driver.waitForPageToBeReady();
		this.driver.getWebDriver().get(Input.url+"AI/AIHome");

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTagModel(tag).Visible()  ;}}), Input.wait30); 

		getModelActions().waitAndClick(10);
		base.waitTime(3);
		getDeleteModelConfig().waitAndClick(10);
		getDisableAIToggle().waitAndClick(10);
		base.stepInfo("Disbaled or Deleted AI Model Succesfully");

	}

	public void verifyAIDisabled(String tag) throws InterruptedException{


		base.waitTime(3);
		this.driver.getWebDriver().get(Input.url+"TagsAndFolders/TagsAndFolders");
		driver.waitForPageToBeReady();
		//driver.scrollingToElementofAPage(getSelectTag(tag));
		base.waitTime(3);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectTag(tag).Visible()  ;}}), Input.wait30); 
		getSelectTag(tag).waitAndClick(10);

		//driver.scrollingToElementofAPage(getDefaultAction());
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getDefaultAction().Visible()  ;}}), Input.wait30); 
		getDefaultAction().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				geEditTag().Visible()  ;}}), Input.wait30); 
		geEditTag().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getEnableAIToggle().Visible()  ;}}), Input.wait30);

		base.passedStep("AI is disbaled in Edit tag Popup");

	}

	public void verifyAIScoreDeletion(String tag) throws InterruptedException{

		driver.waitForPageToBeReady();
		base.stepInfo("Navigating to Dco Explorer screen.");
		this.driver.getWebDriver().get(Input.url+"DocExplorer/Explorer");
		base.waitTime(2);

		base.stepInfo("Selecting trained documents using tag filter");
		SelectDocsInDocExplorer(tag);
		base.stepInfo("Checking scores in DocList screen.");
		base.waitForElement(SelectColumnBtn());
		SelectColumnBtn().waitAndClick(10);
		base.waitForElement(getScoresTab());
		getScoresTab().waitAndClick(10);
		base.waitForElement(getAllAIScores());
		getAllAIScores().waitAndClick(10);
		base.waitTime(3);
		System.out.println(getAIScores().FindWebElements().size());
		UtilityLog.info(getAIScores().FindWebElements().size());
		for (WebElement iterable_element : getAIScores().FindWebElements() ){
			System.out.println(iterable_element.getText());
			if (iterable_element.getText().contains(tag)) {
				base.failedStep("Tag is still displaying under scores tab.");
			}
		}


	}

	public void SortByAIScore(String tag) throws InterruptedException{

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSortByAIScore().Visible()  ;}}), Input.wait60);
		getSortByAIScore().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectAIScoreField().Visible()  ;}}), Input.wait60);
		getSelectAIScoreField().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSelectAITag(tag).Visible()  ;}}), Input.wait60);
		getSelectAITag(tag).waitAndClick(10);
		base.stepInfo("Selected AI Scored Tag and default Sort Order");
		base.waitTime(2);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getSaveinEditAssignment().Visible()  ;}}), Input.wait60);
		getSaveinEditAssignment().waitAndClick(10);
		driver.waitForPageToBeReady();
		base.waitTime(5);

	}

	public void SelectDocumentsForSubsequentRun() throws InterruptedException{
		final DocListPage dl = new DocListPage(driver);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getNumberOfDcosToBeShown().Visible()  ;}}), Input.wait60);

		getNumberOfDcosToBeShown().selectFromDropdown().selectByVisibleText("500");
		base.stepInfo("Selected Docs per page to be displayed as - 500");
		base.waitTime(2);
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call() {
			return dl.getSelectAll().Visible();}}), Input.wait30);
		base.waitForElement(dl.getSelectAll());
		dl.getSelectAll().waitAndClick(3);		
		dl.getPopUpOkBtn().waitAndClick(3);
		base.waitTime(2);


	}

	public void bulkTagExisting(String tagName) throws InterruptedException{

		search.getBulkActionButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				search.getBulkTagAction().Visible()  ;}}), Input.wait60); 

		search.getBulkTagAction().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				search.getSelectTagExisting(tagName).Visible()  ;}}), Input.wait60); 

		search.getSelectTagExisting(tagName).waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				search.getContinueCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
		search.getContinueButton().Click();

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				search.getFinalCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait60); 
		search.getFinalizeButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				search.getBulkTagConfirmationButton().Visible()  ;}}), Input.wait30); 
		search.getBulkTagConfirmationButton().Click();

		base.VerifySuccessMessage("Records saved successfully");

		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				bc.initialBgCount() == Bgcount+1  ;}}), Input.wait60); 
		System.out.println("Bulk Tag is done, Tag is : "+tagName);
		UtilityLog.info("Bulk Tag is done, Tag is : "+tagName);
	}
}


