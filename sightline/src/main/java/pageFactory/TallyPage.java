package pageFactory;

import java.util.concurrent.Callable;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;

public class TallyPage {

    Driver driver;
    BaseClass base;
    DocListPage dp;
  
    public Element getAutoSelectedSearchSource(){ return driver.FindElementByXPath("//li[contains(text(),'Documents: Selected Documents from Search')]"); }
    public Element getTally_SelectSource(){ return driver.FindElementById("select-source"); }
    public Element getTally_SecurityGroupsButton(){ return driver.FindElementByXPath("//strong[contains(.,'Security Groups')]"); }
    public Element getTally_SelectSourceGroup(){ return driver.FindElementById("secgroup"); }
    public ElementCollection getfindSearchRow(){ return driver.FindElementsByXPath("//table[@id='GridSchedulerExecutionList']//td[2]"); }
    public Element getPopupYesBtn(){ return driver.FindElementByXPath("//button[@id='btnYes']"); }
    
    public Element getTally_SelectSecurityGroup(){ return driver.FindElementByXPath(".//*[@id='secgroup']/li[contains(.,'Default Security Group')]/label/i"); }
    public Element getTally_SaveSelections(){ return driver.FindElementByXPath("//button[@id='secgroup']"); } 
    public Element getTally_SelectaTallyFieldtoruntallyon(){ return driver.FindElementById("select-source1"); } 
    public Element getTally_Metadataselect(){ return driver.FindElementById("metadataselect"); } 
    public Element getTally_btnTallyApply(){ return driver.FindElementById("btnTallyApply"); } 
    public Element getTally_btnTallyAll(){ return driver.FindElementById("btnTallyAll"); } 
    public Element getTally_tallyactionbtn(){ return driver.FindElementById("tallyactionbtn"); }
    public Element getTally_actionSubTally(){ return driver.FindElementById("actionSubTally"); }
    public Element getTally_SourceSubTally(){ return driver.FindElementById("select-source21"); }
    public Element getTally_btnSubTallyApply(){ return driver.FindElementById("btnSubTallyApply"); }
    public Element getTally_btnSubTallyAll(){ return driver.FindElementById("btnSubTallyAll"); }
    public Element getTally_SubMetadata(){ return driver.FindElementById("subMetadata"); }
    public Element getTally_submetadataselect(){ return driver.FindElementById("submetadataselect"); }
  
    public Element getTally_SubTally_Action_ViewButton(){ return driver.FindElementByXPath(".//*[@id='freezediv']//a[contains(.,'View')]"); } 
    public Element getTally_subMetadata(){ return driver.FindElementByXPath(".//*[@id='accordion2']//strong[contains(.,'METADATA')]"); } 
    public Element getTally_SubTally_Action_ViewDocList(){ return driver.FindElementById("idSubTallyViewDoclist"); } 
    
    public Element getTally_LoadingScreen(){ return driver.FindElementByXPath("//span[@class='LoadImagePosition']/img[@src='/img/loading.gif']"); } 
    public Element getTallyContinue(){ return driver.FindElementByXPath("//*[@id='bot1-Msg1']"); } 
    
    //bulk actions
    public Element getTally_SubTallyActionButton(){ return driver.FindElementById("subtallyactionbtn"); }
    public Element getBulkTagAction(int i){ return driver.FindElementByXPath("(//a[contains(text(),'Bulk Tag')])["+i+"]"); }
    public Element getBulkActionButton(){ return driver.FindElementById("idAction"); }
    
    public Element getTagsAllRoot(){ return driver.FindElementByXPath("//*[@id='tagsJSTree']//*[@id='-1_anchor']"); }
    
    public Element getEnterTagName(){ return driver.FindElementById("txtTagName"); }
    public Element getBulkFolderAction(int i){ return driver.FindElementByXPath("(//a[contains(text(),'Bulk Folder')])["+i+"]"); }
    public Element getBulkNewTab(){ return driver.FindElementById("tabNew"); }
    public Element getBulkTagConfirmationButton(){ return driver.FindElementByXPath("//button[contains(text(),'Ok')]"); }
    
    public Element getFolderAllRoot(){ return driver.FindElementByXPath("//*[@id='folderJSTree']//*[@id='-1_anchor']"); }
    public Element getEnterFolderName(){ return driver.FindElementById("txtFolderName"); }
    public Element getContinueCount(){ return driver.FindElementByXPath("//div[@class='bulkActionsSpanLoderTotal']"); }
    public Element getContinueButton(){ return driver.FindElementByXPath(".//*[@id='divBulkAction']//button[contains(.,'Continue')]"); }
    public Element getFinalCount(){ return driver.FindElementByXPath("//span[@id='spanTotal']"); }
    public Element getFinalizeButton(){ return driver.FindElementById("btnfinalizeAssignment"); }
   
    public Element getView(){ return driver.FindElementByXPath("//div[@class='col-md-6']//div[@class='col-md-3 text-right']//li[@class='dropdown-submenu']/a[contains(text(),'View')]"); }
    public Element getDocListView(){ return driver.FindElementById("idViewDoclist"); }
    public Element getDocViewView(){ return driver.FindElementById("idViewInDocview"); }
    
    public Element getBulkAssignAction(int i){ return driver.FindElementByXPath("(//a[contains(.,'Bulk Assign')])["+i+"]"); }
    
    public ElementCollection getElements(){ return driver.FindElementsByXPath("//*[@class='a-menu']"); }
    
    public Element getQuickBatchAction(int i){ return driver.FindElementByXPath("(//a[contains(text(),'Quick Batch')])["+i+"]"); }
    public Element getMetadataPopup() { return driver.FindElementById("metadataPopup"); }
    public Element getMetadataButtonSelectedOption(String option) { return driver.FindElementByXPath("//button[@id='select-source1' and contains(text(), '"+option+"')]"); } 
    public Element getSubMetadataButtonSelectedOption(String option) { return driver.FindElementByXPath("//button[@id='select-source21' and contains(text(), '"+option+"')]"); } 
    public ElementCollection getMetadataViewText() { return driver.FindElementsByXPath("//*[name()='svg']//*[name()='text' and @dy='.35em']"); }
    public Element getMetadataTallyResults() { return driver.FindElementByXPath("//*[name()='svg']"); }
    public Element getMetadataSelected() { return driver.FindElementByXPath("//*[name()='svg']//*[name()='rect' and @class='selected']"); }
    public Element getTallyActionDropdownMenu() { return driver.FindElementByCssSelector(".dropdown-menu.action-dd"); }
    public Element getTallyActionOption(String option) { return driver.FindElementByXPath("//a[text()='"+option+"']"); }
    public Element getSubTallyByOption(String option) { return driver.FindElementByXPath("//div[@id='accordion2']//strong[text()='"+option+"']"); }
    public ElementCollection getSubTallyMetadataResults() { return driver.FindElementsByXPath("//div[@id='subTallyChart']//td[@class='text-left rowlead formatDate']"); }
    public Element getSubTallyTable() { return driver.FindElementByCssSelector(".table.table-striped.dataGrid"); }
    
  
    public TallyPage(Driver driver){

    	this.driver = driver;    	
    	// Adding logic to check if user is already on Tally view. Without this, if TallyPage gets initialized when user is already on 
    	// Tally view, then the existing data on the page gets wiped out
         if (!driver.getUrl().contains("Report/Tally")) {
        	 this.driver.getWebDriver().get(Input.url+ "Report/Tally");
         }
        base = new BaseClass(driver);
   
    }
    
    public void validateTally() {
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getTally_SelectSource().Visible()  ;}}), Input.wait30); 
		getTally_SelectSource().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_SecurityGroupsButton().Visible()  ;}}), Input.wait30); 
		getTally_SecurityGroupsButton().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_SelectSecurityGroup().Visible()  ;}}), Input.wait30); 
		getTally_SelectSecurityGroup().Click();
		
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_SaveSelections().Visible()  ;}}), Input.wait30); 
	  getTally_SaveSelections().Click();
	   
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getTally_SelectaTallyFieldtoruntallyon().Visible()  ;}}), Input.wait30);		
		getTally_SelectaTallyFieldtoruntallyon().Click();
		
		
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getTally_Metadataselect().Visible()  ;}}), Input.wait30);	
		getTally_Metadataselect().selectFromDropdown().selectByVisibleText("CustodianName");
		
		
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getTally_btnTallyApply().Visible()  ;}}), Input.wait30);	
		getTally_btnTallyApply().Click();
		
		BaseClass bc = new BaseClass(driver);
		//pop up may appear multiple times depends on app response
		bc.yesPopUp();
		bc.yesPopUp();

	}
    
    public void tallyActions() {
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_btnTallyAll().Visible()  ;}}), Input.wait30);	
		
		getTally_btnTallyAll().ScrollTo();
		getTally_btnTallyAll().waitAndClick(30);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_tallyactionbtn().Visible()  ;}}), Input.wait30);	
		getTally_tallyactionbtn().Click();
	}
    
    public void bulkAssign(final int tally1subtally2) {
    	
    	
    	
    	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			 getBulkAssignAction(tally1subtally2).Visible()  ;}}), Input.wait60); 
    	
         
    	 getBulkAssignAction(tally1subtally2).Click();
    	 
    	 System.out.println("performing bulk assign");
    	 
    	
    	
    }
  
    public void bulkTag(String TagName, final int tally1subtally2) {
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getBulkTagAction(tally1subtally2).Visible()  ;}}), Input.wait30);	
    	getBulkTagAction(tally1subtally2).Click();
    	
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

	}
    public void bulkFolder(String folderName,final int tally1subtally2) {

   	
   	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			 getBulkFolderAction(tally1subtally2).Visible()  ;}}), Input.wait60); 
   	 
   	 getBulkFolderAction(tally1subtally2).waitAndClick(20);
   	 
   	 
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
    
    public void validateSubTally() throws InterruptedException {
		
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_SourceSubTally().Visible()  ;}}), Input.wait30);	
		Thread.sleep(3000);
	   getTally_SourceSubTally().Click();
	   
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getTally_subMetadata().Visible()  ;}}), Input.wait30);	
		getTally_subMetadata().Click();
		
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getTally_submetadataselect().Visible()  ;}}), Input.wait30);	
		getTally_submetadataselect().selectFromDropdown().selectByVisibleText("DocFileExtension");
		
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getTally_btnSubTallyApply().Visible()  ;}}), Input.wait30);	
		getTally_btnSubTallyApply().Click();
		
		//pop up may appear multiple times depends on app response
		base.yesPopUp();
		base.yesPopUp();
	
	}
    public void subTallyActions() {
    	getTally_btnSubTallyAll().waitAndClick(30);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_SubTallyActionButton().Visible()  ;}}), Input.wait30);	
		getTally_SubTallyActionButton().waitAndClick(25);
	

	}
    public void ValidateTallySubTally() throws InterruptedException {
    	dp = new DocListPage(driver);
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getTally_SelectSource().Visible()  ;}}), Input.wait30); 
		getTally_SelectSource().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_SecurityGroupsButton().Visible()  ;}}), Input.wait30); 
		getTally_SecurityGroupsButton().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_SelectSecurityGroup().Visible()  ;}}), Input.wait30); 
		getTally_SelectSecurityGroup().Click();
		
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_SaveSelections().Visible()  ;}}), Input.wait30); 
	  getTally_SaveSelections().Click();
	   
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getTally_SelectaTallyFieldtoruntallyon().Visible()  ;}}), Input.wait30);		
		getTally_SelectaTallyFieldtoruntallyon().Click();
		
		
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getTally_Metadataselect().Visible()  ;}}), Input.wait30);	
		getTally_Metadataselect().selectFromDropdown().selectByVisibleText("CustodianName");
		
		
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getTally_btnTallyApply().Visible()  ;}}), Input.wait30);	
		getTally_btnTallyApply().Click();
		
		
		base.yesPopUp();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_btnTallyAll().Visible()  ;}}), Input.wait30);	
		
		getTally_btnTallyAll().ScrollTo();
		getTally_btnTallyAll().waitAndClick(30);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_tallyactionbtn().Visible()  ;}}), Input.wait30);	
		getTally_tallyactionbtn().Click();
		
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_actionSubTally().Visible()  ;}}), Input.wait30);
		getTally_actionSubTally().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_SourceSubTally().Visible()  ;}}), Input.wait30);	
		Thread.sleep(3000);
	   getTally_SourceSubTally().Click();
	   
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getTally_subMetadata().Visible()  ;}}), Input.wait30);	
		getTally_subMetadata().Click();
		
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getTally_submetadataselect().Visible()  ;}}), Input.wait30);	
		getTally_submetadataselect().selectFromDropdown().selectByVisibleText("DocFileExtension");
		
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getTally_btnSubTallyApply().Visible()  ;}}), Input.wait30);	
		getTally_btnSubTallyApply().Click();
		
		base = new BaseClass(driver);
		base.yesPopUp();
		
		getTally_btnSubTallyAll().waitAndClick(30);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_SubTallyActionButton().Visible()  ;}}), Input.wait30);	
		getTally_SubTallyActionButton().Click();
	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_SubTally_Action_ViewButton().Visible()  ;}}), Input.wait30);	
		
		
		getTally_SubTally_Action_ViewButton().ScrollTo();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_SubTally_Action_ViewDocList().Visible()  ;}}), Input.wait30);	
		getTally_SubTally_Action_ViewDocList().Click();
		
		try{
			getTallyContinue().waitAndClick(10);
		}catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("Navigated to Doclist page");
		
		   //dp.getDocList_info().WaitUntilPresent();
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    		   !dp.getDocList_info().getText().isEmpty()  ;}}),Input.wait30);
	       Assert.assertTrue(dp.getDocList_info().getText().toString().contains("Showing 1 to 10 of"));
	       System.out.println("Docs are shown in doclist");

			
    }
    
 public void ValidateTallySubTally_QuickBatch() throws InterruptedException {
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getTally_SelectSource().Visible()  ;}}), Input.wait30); 
		getTally_SelectSource().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_SecurityGroupsButton().Visible()  ;}}), Input.wait30); 
		getTally_SecurityGroupsButton().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_SelectSecurityGroup().Visible()  ;}}), Input.wait30); 
		getTally_SelectSecurityGroup().Click();
		
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_SaveSelections().Visible()  ;}}), Input.wait30); 
	  getTally_SaveSelections().Click();
	   
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getTally_SelectaTallyFieldtoruntallyon().Visible()  ;}}), Input.wait30);		
		getTally_SelectaTallyFieldtoruntallyon().Click();
		
		
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getTally_Metadataselect().Visible()  ;}}), Input.wait30);	
		getTally_Metadataselect().selectFromDropdown().selectByVisibleText("CustodianName");
		
		
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getTally_btnTallyApply().Visible()  ;}}), Input.wait30);	
		getTally_btnTallyApply().Click();
		
		
		base.yesPopUp();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_btnTallyAll().Visible()  ;}}), Input.wait30);	
		
		getTally_btnTallyAll().ScrollTo();
		getTally_btnTallyAll().waitAndClick(30);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_tallyactionbtn().Visible()  ;}}), Input.wait30);	
		getTally_tallyactionbtn().Click();
		
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_actionSubTally().Visible()  ;}}), Input.wait30);
		getTally_actionSubTally().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_SourceSubTally().Visible()  ;}}), Input.wait30);	
		Thread.sleep(3000);
	   getTally_SourceSubTally().Click();
	   
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getTally_subMetadata().Visible()  ;}}), Input.wait30);	
		getTally_subMetadata().Click();
		
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getTally_submetadataselect().Visible()  ;}}), Input.wait30);	
		getTally_submetadataselect().selectFromDropdown().selectByVisibleText("DocFileExtension");
		
		 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				 getTally_btnSubTallyApply().Visible()  ;}}), Input.wait30);	
		getTally_btnSubTallyApply().Click();
		
		base = new BaseClass(driver);
		base.yesPopUp();
		
		getTally_btnSubTallyAll().waitAndClick(30);
		
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTally_SubTallyActionButton().Visible()  ;}}), Input.wait30);	
		getTally_SubTallyActionButton().Click();
	
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getQuickBatchAction(2).Visible()  ;}}), Input.wait30);	
		
		getQuickBatchAction(2).waitAndClick(10);
		
		    }

 	public void selectMetadataOption(String option) {
 		getTally_Metadataselect().click();
 		getTally_Metadataselect().SendKeys(option);
 	}
 	
 	public void selectSubMetadataOption(String option) {
 		getTally_submetadataselect().click();
 		getTally_submetadataselect().SendKeys(option);
 	}

    public void selectTallyAction(String option) {
    	getTally_tallyactionbtn().click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
				getTallyActionDropdownMenu().Visible()  ;}}), Input.wait30);	
    	getTallyActionOption(option).click();
    }
}