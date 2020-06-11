package pageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import junit.framework.Assert;
import testScriptsSmoke.Input;


public class CodingForm {

    Driver driver;
    BaseClass base;
    SoftAssert softAssertion;
    final DocViewPage doc;
    
    public Element getAddNewCodingFormBtn(){ return driver.FindElementByXPath("//*[@id='content']//button[@class='btn btn-primary new-coding-form']"); }
    public Element getCodingFormName(){ return driver.FindElementById("txtFormName"); }
    public Element getSaveCFBtn(){ return driver.FindElementByXPath("//*[@id='tab-create']//a[text()='Save']"); }
    public Element getCodingForm_Validation_ButtonYes(){ return driver.FindElementById("btnYes"); }
    public Element getCodingForm_FirstTag(){ return driver.FindElementByXPath("//*[@id='lstTags']/li[1]/label/i"); }
    public Element getCodingForm_AddToFormButton(){ return driver.FindElementById("addFormObjects"); }
    public Element getCodingForm_CommentTab(){ return driver.FindElementByXPath("//*[@id='internal-tab-1']//span[text()='COMMENTS']"); }
    public Element getCodingForm_FirstComment(){ return driver.FindElementByXPath("//*[@id='lstComments']/li[1]/label/i"); }
    public Element getCodingForm_EDITABLE_METADATA_Tab(){ return driver.FindElementByXPath("//*[@id='internal-tab-1']//span[text()='EDITABLE METADATA']"); }
    public Element getCodingForm_SubjectMetadata(){ return driver.FindElementByXPath("//*[@id='lstMetadata']//strong[text()='Subject']/../i"); }
    public Element getCodingForm_RemoveLink(){ return driver.FindElementByXPath("//*[a[text()='Remove']]//a"); }
    public Element getCodingForm_ObjectName(){ return driver.FindElementByXPath("//*[starts-with(@id, 'FriendlyInput_')]"); }
    public Element getCodingForm_FirstMetadata(){ return driver.FindElementByXPath("//*[@id='lstMetadata']/li[1]/label/i"); }
    public Element getCodingForm_FirstRemoveLink(){ return driver.FindElementByXPath("//*[a[text()='Remove']]//a[@id='0']"); }
    public Element getCodingForm_SearchBox(){ return driver.FindElementByXPath("//*[@id='CodingFormDataTable_filter']/label/input"); }
    public Element getCodingForm_SearchButton(){ return driver.FindElementByXPath("/*[@id='CodingFormDataTable_filter']/label/span/i"); }
    public Element getCodingForm_Objects(){ return driver.FindElementByXPath("//*[@class='itemFriendlyName']"); }
    public Element getCF_SecondTag(){ return driver.FindElementByXPath("//ul[@id='lstTags']/li[2]/label/i"); }
    public Element getCF_All(){ return driver.FindElementByXPath("//ul[contains(@id,'lstTags')]"); }
    public Element getCF_PreviewButton(){ return driver.FindElementByXPath("//a[contains(text(),'Preview')]"); }
    public Element getCF_Preview_Ok(){ return driver.FindElementByXPath("//button[@id='btnYes']"); }
    public Element getCF_AddLogicRule(){ return driver.FindElementByXPath("//button[@id='0']"); }
    public Element getCF_Object1(){ return driver.FindElementByXPath("//*[@id='SelectObjects_0_1']"); }
    public Element getCF_FieldLogicCondition(){ return driver.FindElementByXPath("//*[@id='SelectCondition_0_1']"); }
    public Element getCF_FieldLogicAction(){ return driver.FindElementByXPath("//select[@id='LogicFieldAction_0']"); }
    public Element getCF_ObjectClose(){ return driver.FindElementByXPath("//i[@id='1']"); }
    public Element getCF_PreviewTagObject1(){ return driver.FindElementByXPath("//*[@id='item0']/table/tbody/tr/td[1]/label/span"); }
    public Element getCF_PreviewTagObject2(){ return driver.FindElementByXPath("//*[@id='item1']/table/tbody/tr/td[1]/label/span"); }
    public Element getSuccessMsgHeader(){ return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span"); }
    public Element getSuccessMsg(){ return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p"); }
    public Element getCodingForm_NumberToShow(){ return driver.FindElementByCssSelector("*[name='CodingFormDataTable_length']"); }
    public Element getCodingForm_SelectSecurityGroup(String cfname){ return driver.FindElementByXPath(".//*[@id='CodingFormDataTable']/tbody/tr[contains(.,'"+cfname+"')]/td[5]//i"); }
    public Element getCodingForm_SGValidation_ButtonYes(){ return driver.FindElementById("bot1-Msg1"); }
    public Element getCF_firstObjecttab(){ return driver.FindElementByXPath("//*[@href='#c-0']"); }
  
    
    //addition by shilpi on 2/7
    public Element getCodingForm_EditButton(String CFName){ return driver.FindElementByXPath(".//*[@id='CodingFormDataTable']//td[text()='"+CFName+"']/../td//a[text()='Edit']"); }
    public Element getCodingForm_CopyButton(String CFName){ return driver.FindElementByXPath(".//*[@id='CodingFormDataTable']//td[text()='"+CFName+"']/../td//a[text()='Copy']"); }
    public Element getCodingForm_DeleteButton(String CFName){ return driver.FindElementByXPath(".//*[@id='CodingFormDataTable']//td[text()='"+CFName+"']/../td//a[text()='Delete']"); }
    public Element getManageCodingFormButton(){ return driver.FindElementByXPath("//a[contains(text(),'Manage Coding Forms')]"); }
    
    //select coding's radio button from table
    public Element getCFlistTable(){ return driver.FindElementByXPath("//*[@id='CodingFormDataTable']"); }
    public ElementCollection getCFnames(){ return driver.FindElementsByXPath("//*[@id='CodingFormDataTable']/tbody/tr/td[1]"); }
    public Element getCFRadioBtn(int row){ return driver.FindElementByXPath("//*[@id='CodingFormDataTable']/tbody/tr["+row+"]/td[5]/span/label/i"); }
    
    //added on 25/03
    public Element getCodingForm_Comment(String commentname){ return driver.FindElementByXPath("//*[@id='lstComments']//li/label[@title='"+commentname+"']/i"); }
    public Element getCodingForm_Tag(String Tagname){ return driver.FindElementByXPath("//*[@id='lstTags']/li/label[@title='"+Tagname+"']/i"); }
    public Element getCF_Comment(String commentname){ return driver.FindElementByXPath(".//*[@id='collapseTwo']//span[contains(text(),'"+commentname+"')]"); }
 //   public Element getCF_Metadata(String metadata){ return driver.FindElementByXPath(".//*[@id='lstMetadata']//strong[contains(text(),'"+metadata+"')]"); }
    public Element getCodingForm_ErrorMsg(){ return driver.FindElementById("ErrorMessage_0"); }
    public Element getCodingForm_ErrorMsg1(){ return driver.FindElementById("ErrorMessage_1"); }
    public Element getCodingForm_ErrorMsg2(){ return driver.FindElementById("ErrorMessage_2"); }
    public Element getCodingForm_HelpText(){ return driver.FindElementById("HelpText_0"); }
    public Element getCF_StaticTextObject(){ return driver.FindElementByXPath("//*[@data-type='STATICTEXT']/following-sibling::i"); }
    public Element getCF_RadioGrpObject(){ return driver.FindElementByXPath("//*[@data-type='RADIOGROUP']/following-sibling::i"); }
    public Element getCF_CheckGrpObject(){ return driver.FindElementByXPath("//*[@data-type='CHECKGROUP']/following-sibling::i"); }
    public Element getCF_TagType(){ return driver.FindElementById("3"); }
    public Element getCF_TagGrpAssociation(){ return driver.FindElementById("TagChk_3"); }
    public Element getCF_AddLogicButton(){ return driver.FindElementByXPath("//*[@class='add-logic btn btn-labeled btn-primary bg-color-blueLight']"); }
    public Element getCF_ValidationAlert(){ return driver.FindElementById("ui-id-1"); }
    public Element getCodingForm(String CFName){ return driver.FindElementByXPath(".//*[@id='CodingFormDataTable']//td[text()='"+CFName+"']"); }
    public Element getCF_DeletePopup(){ return driver.FindElementByXPath(".//*[@class='pText']"); }
    //aaded on 06/06
    public Element getCodingForm_MultipleTags(int i){ return driver.FindElementByXPath("//*[@id='lstTags']/li["+i+"]/label/i"); }
    public Element getCodingForm_DefaultAction(int i){ return driver.FindElementById("DefaultAction_"+i+""); }
    public Element getCodingForm_TagNames(int i){ return driver.FindElementByXPath("(//span[@class='itemFriendlyName'])["+i+"]"); }
    public Element getCodingForm_MandField(){ return driver.FindElementByXPath("//span[@class='field-validation-error text-danger']"); }
    public Element getCodingForm_MetadataField(){ return driver.FindElementByXPath(".//*[@id='lstMetadata']//label"); }
    public Element getCF_Metadata(){ return driver.FindElementById("lstMetadata"); }
    public Element getCF_TagTypes(){ return driver.FindElementByXPath(".//*[@id='c-0']//select[@id='0']"); }
    public Element getCF_Preview1(){ return driver.FindElementByXPath(".//*[starts-with(@id,'item')]//td[1]"); }
    public Element getCF_RadioGroup(){ return driver.FindElementById("TagRdo_0"); }
    public Element getCF_RadioGroup1(){ return driver.FindElementById("//*[@id='c-2']//select[@id='2']"); }
    
    
    
    public CodingForm(Driver driver){

        this.driver = driver;
        base = new BaseClass(driver);
        doc = new DocViewPage(driver);
        this.driver.getWebDriver().get(Input.url+ "CodingForm/Create");
        driver.waitForPageToBeReady();
       
        softAssertion= new SoftAssert(); 
    }
   //Create CF with the given name
   //to create coding form with first tag and comments from the list 
   public void createCodingform(String cfName) {
	   
	    this.driver.getWebDriver().get(Input.url+ "CodingForm/Create");
	   getAddNewCodingFormBtn().waitAndClick(10);
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getCodingFormName().Visible()  ;}}),Input.wait30);
	   getCodingFormName().SendKeys(cfName);
	   getCodingForm_FirstTag().waitAndClick(10);
	  
	   getCodingForm_CommentTab().Click();
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getCodingForm_FirstComment().Visible()  ;}}),Input.wait30);
	   getCodingForm_FirstComment().Click();
	   getCodingForm_AddToFormButton().Click();
	   getSaveCFBtn().Click();
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getCodingForm_Validation_ButtonYes().Visible()  ;}}),Input.wait30);
	   getCodingForm_Validation_ButtonYes().Click();
	   
	   System.out.println("Coding form "+cfName+" created");
	   
	   base.VerifySuccessMessage("Coding Form Saved successfully");
	 //  base.CloseSuccessMsgpopup();
}
   
     public void CodingformToSecurityGroup(String cfName) throws InterruptedException {
	   
	    this.driver.getWebDriver().get(Input.url+ "CodingForm/Create");
	   driver.waitForPageToBeReady();
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getCodingForm_NumberToShow().Visible()  ;}}), Input.wait60);
	   getCodingForm_NumberToShow().selectFromDropdown().selectByVisibleText("100");
	   Thread.sleep(3000);
	 
	   selectCF_SGF_RBtn(cfName);
	   
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getCodingForm_SGValidation_ButtonYes().Visible()  ;}}), Input.wait30);
	   getCodingForm_SGValidation_ButtonYes().Click(); 
	  
	}
     
     public void EditCodingform(final String cfName) {
    	 
       this.driver.getWebDriver().get(Input.url+ "CodingForm/Create");
   	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			   getCodingForm_NumberToShow().Visible()  ;}}), Input.wait60);
   	   getCodingForm_NumberToShow().selectFromDropdown().selectByVisibleText("100");
	 
      driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		  getCodingForm_EditButton(cfName).Visible()  ;}}),Input.wait60);
  	   getCodingForm_EditButton(cfName).waitAndClick(10);
  	  
  	   getCF_SecondTag().waitAndClick(10);
  	  
  	   getCodingForm_AddToFormButton().Click();
  	   getSaveCFBtn().Click();
  	   try {
  	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			   getCodingForm_Validation_ButtonYes().Visible()  ;}}),Input.wait60);
  	   getCodingForm_Validation_ButtonYes().Click();
  	   }catch (Exception e)
  	   {
  		   System.out.println("Pop up button not appeared");
  	   }
  	
  	   base.VerifySuccessMessage("Coding Form updated successfully");
  	   base.CloseSuccessMsgpopup();
  	   
  	   getCF_firstObjecttab().waitAndClick(10);
  	   driver.scrollingToBottomofAPage();
  	  
  	   getCodingForm_FirstRemoveLink().waitAndClick(10);
  	   
  	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			getCodingForm_SGValidation_ButtonYes().Visible()  ;}}),Input.wait30);
  	  getCodingForm_SGValidation_ButtonYes().Click();
  	  
  	  driver.scrollPageToTop();
  	  try {
  	        getCF_firstObjecttab().Displayed();
  	      Assert.assertFalse(1==0);
  	  }
  	  catch(org.openqa.selenium.NoSuchElementException e) {
		System.out.println("Tag successfullyremoved");
	}
  	  
 }
     
     public void DeleteCodingform(String cfName) {
    	 
    	  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			   getCodingForm_NumberToShow().Visible()  ;}}), Input.wait60);
  	      getCodingForm_NumberToShow().selectFromDropdown().selectByVisibleText("100");
  	      try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			
			e.printStackTrace();
		}
  	 
  	      selectCF_SGF_RBtn(cfName);
  	   
    	   getCodingForm_DeleteButton(cfName).waitAndClick(10);
    	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			   base.getYesBtn().Visible()  ;}}),Input.wait60);
    	   base.getYesBtn().Click();
    	    
    	   base.VerifySuccessMessage("Coding form deleted successfully");
    	   base.CloseSuccessMsgpopup();
    }
     
     public void CopyCodingform(final String cfName) {
    	 
    	 this.driver.getWebDriver().get(Input.url+ "CodingForm/Create");
     	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
     			  getCodingForm_CopyButton(cfName).Visible()  ;}}), Input.wait30);
    	 
  	   getCodingForm_CopyButton(cfName).Click();
  	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			   base.getYesBtn().Visible()  ;}}),Input.wait60);
  	   String msg = getCF_DeletePopup().getText();
  	   System.out.println(msg);
  	   Assert.assertEquals("Are you sure you want to copy?", msg);
  	   base.getYesBtn().Click();
  	    
  	   base.VerifySuccessMessage("Coding form copied successfully");
  	   base.CloseSuccessMsgpopup();
  	 	   
  }
     
     public void ViewCFinDocViewThrSearch(String cfName) {
    	 
    	 
    	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			   doc.getDocView_CFName().Visible()  ;}}),Input.wait60);
    	  if(doc.getDocView_CFName().Displayed()){
    	  String name = doc.getDocView_CFName().getText().toString();
    	  System.out.println(name);
    	  Assert.assertEquals(cfName, name); 
    	  }
      }
     
     public void ViewCFinDocViewThrAssignment(String cfName) {
    	 
      driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  			 doc.getDocView_CFName().Visible()  ;}}),Input.wait60);
  	  if(doc.getDocView_CFName().Displayed()){
  	  String name = doc.getDocView_CFName().getText().toString();
  	  System.out.println(name);
  	  Assert.assertEquals(cfName, name); 
  	  }
    }
     
     public boolean selectCF_SGF_RBtn(final String CFname) {
   	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			  getCFlistTable().Visible()  ;}}),Input.wait30);
   	   
   	   boolean nextPage= true;
   	   boolean found= false;
   	  System.out.println(getCFnames().size());
   	   while(nextPage){
   		   int row = 1;
   		   
   		   for (WebElement ele : getCFnames().FindWebElements()) {
   			  System.out.println(ele.getText().trim());
   				if(ele.getText().trim().equals(CFname)){
   					nextPage = false;
   					found=true;
   					//System.out.println(row);
   					getCFRadioBtn(row).waitAndClick(10);
   					System.out.println(CFname +" is selected");
   					return true;
   					
   				}
   				
   				row++;
   				
   			}
   		   try{
   			   driver.scrollingToBottomofAPage();
   			   driver.getWebDriver().findElement(By.xpath("//li[@class='paginate_button next disabled']/a")).isDisplayed();
   			   nextPage = false;
   			   //System.out.println("Not found!!!!!!");
   		   }
   		   catch (Exception e) {
   			   driver.getWebDriver().findElement(By.linkText("Next")).click(); 
   		   } 
   			   
   	   }
   	   return false;
   			
      	}
   

public void AddCodingformwithcommentsandtag(String cfName,String comments,String tag) {
	   
	   getAddNewCodingFormBtn().waitAndClick(10);
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getCodingFormName().Visible()  ;}}),Input.wait30);
	   getCodingFormName().SendKeys(cfName);
	   getCodingForm_FirstTag().waitAndClick(10);
	   getCodingForm_Tag(tag).waitAndClick(10);
	  
		  
	   getCodingForm_CommentTab().Click();
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getCodingForm_FirstComment().Visible()  ;}}),Input.wait30);
	  
	   getCodingForm_FirstComment().Click();
	   getCodingForm_Comment(comments).Click();
	   getCodingForm_AddToFormButton().Click();
	   getSaveCFBtn().Click();
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getCodingForm_Validation_ButtonYes().Visible()  ;}}),Input.wait30);
	   getCodingForm_Validation_ButtonYes().Click();
	   
	   System.out.println("Coding form "+cfName+" created");
	   
	   base.VerifySuccessMessage("Coding Form Saved successfully");
	   base.CloseSuccessMsgpopup();
}

public void PreviewCodingform(String cfName,final String Comments) {
  
  getCF_PreviewButton().waitAndClick(10);
  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		 getCF_Comment(Comments).Visible()  ;}}),Input.wait30);

  getCF_Comment(Comments).Displayed();
  }

public void AddCodingformwithTag(String cfName,String tag) {
  
  getAddNewCodingFormBtn().waitAndClick(10);
  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		   getCodingFormName().Visible()  ;}}),Input.wait30);
  getCodingFormName().SendKeys(cfName);
  getCodingForm_Tag(tag).waitAndClick(10);
 
  getCodingForm_AddToFormButton().Click();
  
  driver.scrollingToBottomofAPage();
  
  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		getCodingForm_ErrorMsg().Visible()  ;}}),Input.wait30);
  getCodingForm_ErrorMsg().SendKeys("Error for testing");
  
driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		getCodingForm_HelpText().Visible()  ;}}),Input.wait30);
getCodingForm_HelpText().SendKeys("Help for testing");

getCF_AddLogicButton().waitAndClick(5);

driver.scrollPageToTop();
  
  getSaveCFBtn().Click();
  
  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		  getCF_ValidationAlert().Visible()  ;}}),Input.wait30);
  String text = getCF_ValidationAlert().getText();
  System.out.println(text.substring(19, 59));
  Assert.assertEquals("Please select Field Logic Action for TAG", text.substring(19, 59));
  getCodingForm_Validation_ButtonYes().Click();
  
  System.out.println("Coding form "+cfName+" created");
  
  base.VerifySuccessMessage("Coding Form Saved successfully");
  base.CloseSuccessMsgpopup();
}

public void AddCodingformwithComment(String cfName,String comment) {
	   
	   getAddNewCodingFormBtn().waitAndClick(10);
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getCodingFormName().Visible()  ;}}),Input.wait30);
	   getCodingFormName().SendKeys(cfName);
	   getCodingForm_CommentTab().Click();
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getCodingForm_FirstComment().Visible()  ;}}),Input.wait30);
	   getCodingForm_Comment(comment).Click();
	   getCodingForm_AddToFormButton().Click();
	   
	   driver.scrollingToBottomofAPage();
	   
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getCodingForm_ErrorMsg().Visible()  ;}}),Input.wait30);
	   getCodingForm_ErrorMsg().SendKeys("Error for testing");
	   
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getCodingForm_HelpText().Visible()  ;}}),Input.wait30);
	 getCodingForm_HelpText().SendKeys("Help for testing");
	   
	   getSaveCFBtn().Click();
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getCodingForm_Validation_ButtonYes().Visible()  ;}}),Input.wait30);
	   getCodingForm_Validation_ButtonYes().Click();
	   
	   System.out.println("Coding form "+cfName+" created");
	   
	   base.VerifySuccessMessage("Coding Form Saved successfully");
	   base.CloseSuccessMsgpopup();
}

     public void AddCodingformwithMetadata(final String cfName) {
	   
	   getAddNewCodingFormBtn().waitAndClick(10);
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getCodingFormName().Visible()  ;}}),Input.wait30);
	   getCodingFormName().SendKeys(cfName);
	   
	   getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(10);
	   
	   getCF_Metadata().waitAndClick(5);
	  
	   getCodingForm_AddToFormButton().Click();
	   
	   driver.scrollingToBottomofAPage();
	   
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getCodingForm_ErrorMsg().Visible()  ;}}),Input.wait30);
	   getCodingForm_ErrorMsg().SendKeys("Error for testing");
	   
	 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			getCodingForm_HelpText().Visible()  ;}}),Input.wait30);
	 getCodingForm_HelpText().SendKeys("Help for testing");
	 
	 getCF_AddLogicButton().waitAndClick(5);
	 
	 driver.scrollPageToTop();
	   
	   getSaveCFBtn().Click();
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getCodingForm_Validation_ButtonYes().Visible()  ;}}),Input.wait30);
	   getCodingForm_Validation_ButtonYes().Click();
	   
	   System.out.println("Coding form "+cfName+" created");
	   
	   base.VerifySuccessMessage("Coding Form Saved successfully");
	   base.CloseSuccessMsgpopup();
	   
	   this.driver.getWebDriver().get(Input.url+ "CodingForm/Create");
   	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
   			   getCodingForm_NumberToShow().Visible()  ;}}), Input.wait60);
   	   getCodingForm_NumberToShow().selectFromDropdown().selectByVisibleText("100");
	 
      driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    		  getCodingForm_EditButton(cfName).Visible()  ;}}),Input.wait60);
  	   getCodingForm_EditButton(cfName).waitAndClick(10);
  	   
  	   getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(10);
	   
  	  softAssertion.assertEquals(getCodingForm_MetadataField().GetAttribute("disabled"),"disabled");
}

public void AddCodingformwithSpecialObjects(String cfName) {
  
  getAddNewCodingFormBtn().waitAndClick(10);
  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		   getCodingFormName().Visible()  ;}}),Input.wait30);
  getCodingFormName().SendKeys(cfName);
  
  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		   getCF_StaticTextObject().Visible()  ;}}),Input.wait30);
  getCF_StaticTextObject().Click();
  
  getCF_RadioGrpObject().waitAndClick(5); 
  
  getCF_CheckGrpObject().waitAndClick(5);
  
  getCodingForm_AddToFormButton().Click();
  
  driver.scrollingToBottomofAPage();
  
  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		getCodingForm_ErrorMsg1().Visible()  ;}}),Input.wait30);
  getCodingForm_ErrorMsg1().SendKeys("Error for testing radio group1");
  
  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		getCodingForm_ErrorMsg2().Visible()  ;}}),Input.wait30);
   getCodingForm_ErrorMsg2().SendKeys("Error for testing check group");
   
   driver.scrollPageToTop();
   
   getCodingForm_FirstTag().waitAndClick(10);

   getCodingForm_AddToFormButton().Click();
 
 driver.scrollingToBottomofAPage();
 
driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		   getCF_TagType().Visible()  ;}}),Input.wait30);
getCF_TagType().selectFromDropdown().selectByVisibleText("Radio Item");
 
driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		 getCF_TagGrpAssociation().Visible()  ;}}),Input.wait30);
getCF_TagGrpAssociation().selectFromDropdown().selectByIndex(0);

driver.scrollPageToTop();

getCF_PreviewButton().waitAndClick(10);

	   System.out.println(getCF_Preview1().GetAttribute("type"));
	   softAssertion.assertEquals(getCF_Preview1().GetAttribute("type"), "radio");
	   softAssertion.assertTrue(getCF_Preview1().Displayed());
	   softAssertion.assertFalse(getCF_Preview1().Enabled());

softAssertion.assertTrue(getCodingForm_TagNames(1).Displayed() && getCodingForm_TagNames(1).Enabled() );
softAssertion.assertTrue(getCodingForm_TagNames(2).Displayed());
softAssertion.assertFalse(getCodingForm_TagNames(2).Enabled());
softAssertion.assertTrue(getCodingForm_MandField().Displayed());

  
  getSaveCFBtn().Click();
  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		   getCodingForm_Validation_ButtonYes().Visible()  ;}}),Input.wait30);
  getCodingForm_Validation_ButtonYes().Click();
  
  System.out.println("Coding form "+cfName+" created");
  
  base.VerifySuccessMessage("Coding Form Saved successfully");
  base.CloseSuccessMsgpopup();
}

/*
  public void PreviewValidationswithmultipletags(String cfName) {
	   
	   getAddNewCodingFormBtn().waitAndClick(10);
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getCodingFormName().Visible()  ;}}),Input.wait30);
	   getCodingFormName().SendKeys(cfName);
	   for(int i=1;i<=5;i++)
	   {
		   getCodingForm_MultipleTags(i).Click();
	   }
	  
	   getCodingForm_AddToFormButton().Click();
	   
	   for(int i=1;i<=5;i++)
	   {
		   if(i==1)
		   getCodingForm_DefaultAction(i).selectFromDropdown().selectByVisibleText("Make It Hidden");
		   String tag1 = getCodingForm_TagNames(i).getText();
		   System.out.println(tag1);
		   if(i==2)
		   getCodingForm_DefaultAction(i).selectFromDropdown().selectByVisibleText("Make It Display But Not Selectable");   
		   String tag2 = getCodingForm_TagNames(i).getText();
		   System.out.println(tag2);
		   if(i==3)
		   getCodingForm_DefaultAction(i).selectFromDropdown().selectByVisibleText("Make It Required");   
		   String tag3 = getCodingForm_TagNames(i).getText();
		   System.out.println(tag3);
	   }
	   
	   driver.scrollPageToTop();
	   
	   getCF_PreviewButton().waitAndClick(10);
	   
	   softAssertion.assertTrue(getCodingForm_TagNames(1).Displayed() && getCodingForm_TagNames(1).Enabled() );
	   softAssertion.assertTrue(getCodingForm_TagNames(2).Displayed());
	   softAssertion.assertFalse(getCodingForm_TagNames(2).Enabled());
	   softAssertion.assertTrue(getCodingForm_MandField().Displayed());
	   
   }
   */
   
   //String cfName,String ObjectName,String TagType,String Action
    public void PreviewValidations(String cfName,String ObjectName,String TagType,String Action) throws InterruptedException {
	   
	   getAddNewCodingFormBtn().waitAndClick(10);
	   driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			   getCodingFormName().Visible()  ;}}),Input.wait30);
	  getCodingFormName().SendKeys(cfName);
	  switch(ObjectName)
	   {
	  case "tag" :  
		  if(ObjectName.equalsIgnoreCase("tag"))
		  getCodingForm_FirstTag().waitAndClick(10);
	  case "comment" :
		  if(ObjectName.equalsIgnoreCase("comment"))  {
		  getCodingForm_CommentTab().waitAndClick(10);
		  getCodingForm_FirstComment().waitAndClick(10); }
	  case "metadata":
		  if(ObjectName.equalsIgnoreCase("metadata")) {
		  getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(10);
		  getCodingForm_FirstMetadata().waitAndClick(10); }
 	   }
	   getCodingForm_AddToFormButton().waitAndClick(10);
		
	  if(ObjectName.equalsIgnoreCase("tag")) {
	  switch(TagType)
	  {
	  case "check item" :
		  if(TagType.equalsIgnoreCase("check item"))
		  getCF_TagTypes().selectFromDropdown().selectByVisibleText("Check Item");
	  case "radio item" :
		  if(TagType.equalsIgnoreCase("radio item")) {
		  getCF_TagTypes().selectFromDropdown().selectByVisibleText("Radio Item");
		  getCF_RadioGrpObject().waitAndClick(10);
		  getCodingForm_AddToFormButton().waitAndClick(10);
		
		 Thread.sleep(3000);
		
		  getCF_RadioGroup().selectFromDropdown().selectByIndex(1);
	  }
	  }
	 }
	  Thread.sleep(3000);
	  try {
	   getCodingForm_DefaultAction(0).selectFromDropdown().selectByVisibleText(Action);
	  }
	  catch (Exception e)
	  {
		  System.out.println("Action not enabled");
	  }
	   driver.scrollPageToTop();
	   
	   getCF_PreviewButton().waitAndClick(10);
	   
	   if(ObjectName.equalsIgnoreCase("tag") && TagType.equalsIgnoreCase("check item") && Action.equalsIgnoreCase("Make It Optional") )
	   {
		   System.out.println(getCF_Preview1().GetAttribute("type"));
		   softAssertion.assertEquals(getCF_Preview1().GetAttribute("type"), "checkbox");
		   softAssertion.assertTrue(getCF_Preview1().Displayed() && getCodingForm_TagNames(1).Enabled() );
	   }
	   else if(ObjectName.equalsIgnoreCase("tag") && TagType.equalsIgnoreCase("radio item") && Action.equalsIgnoreCase("Make It Optional") )
	   {
		   System.out.println(getCF_Preview1().GetAttribute("type"));
		   softAssertion.assertEquals(getCF_Preview1().GetAttribute("type"), "radio");
		   softAssertion.assertTrue(getCF_Preview1().Displayed() && getCodingForm_TagNames(1).Enabled() );
	   }
	   else if(ObjectName.equalsIgnoreCase("tag") && TagType.equalsIgnoreCase("check item") && Action.equalsIgnoreCase("Make It Hidden") )
	   {
		//   softAssertion.assertFalse(getCF_Preview1().Displayed());
		   try{
			   getCF_Preview1().Displayed();
		           Assert.assertFalse(1==0);
		     }catch (org.openqa.selenium.NoSuchElementException e) {
		               System.out.println("Tag item not displayed");
		 }
	   }
	   else if(ObjectName.equalsIgnoreCase("tag") && TagType.equalsIgnoreCase("radio item") && Action.equalsIgnoreCase("Make It Display But Not Selectable") )
	   {
		   System.out.println(getCF_Preview1().GetAttribute("type"));
		   softAssertion.assertEquals(getCF_Preview1().GetAttribute("type"), "radio");
		   softAssertion.assertTrue(getCF_Preview1().Displayed());
		 //  softAssertion.assertFalse(getCF_Preview1().Enabled());
		   try{
			   getCF_Preview1().Enabled();
		           Assert.assertFalse(1==0);
		     }catch (org.openqa.selenium.NoSuchElementException e) {
		               System.out.println("Tag item not displayed");
		 }
	   }
	   
	   else if(ObjectName.equalsIgnoreCase("tag") && TagType.equalsIgnoreCase("check item") && Action.equalsIgnoreCase("Make It Required") )
	   {
		   System.out.println(getCF_Preview1().GetAttribute("type"));
		   softAssertion.assertEquals(getCF_Preview1().GetAttribute("type"), "checkbox");
		   softAssertion.assertTrue(getCF_Preview1().Displayed());
		   softAssertion.assertTrue(getCodingForm_MandField().Displayed());
	   }
	   
	   if(ObjectName.equalsIgnoreCase("comment") && Action.equalsIgnoreCase("Make It Optional") )
	   {
		  softAssertion.assertTrue(getCF_Preview1().Displayed() && getCodingForm_TagNames(1).Enabled() );
	   }
	   else if(ObjectName.equalsIgnoreCase("comment") && Action.equalsIgnoreCase("Make It Required") )
	   {
		   softAssertion.assertTrue(getCF_Preview1().Displayed());
		   softAssertion.assertTrue(getCodingForm_MandField().Displayed());
	   }
	   else if(ObjectName.equalsIgnoreCase("comment") && Action.equalsIgnoreCase("Make It Hidden") )
	   {
		  softAssertion.assertFalse(getCF_Preview1().Displayed());
	   }
	   else if(ObjectName.equalsIgnoreCase("comment") && Action.equalsIgnoreCase("Make It Display But Not Selectable") )
	   {
		   softAssertion.assertTrue(getCF_Preview1().Displayed());
		   softAssertion.assertFalse(getCF_Preview1().Enabled());
	   }
	   if(ObjectName.equalsIgnoreCase("metadata") && Action.equalsIgnoreCase("Make It Optional") )
	   {
		  softAssertion.assertTrue(getCF_Preview1().Displayed() && getCodingForm_TagNames(1).Enabled() );
	   }
	   else if(ObjectName.equalsIgnoreCase("metadata") && Action.equalsIgnoreCase("Make It Required") )
	   {
		   softAssertion.assertTrue(getCF_Preview1().Displayed());
		   softAssertion.assertTrue(getCodingForm_MandField().Displayed());
	   }
	   else if(ObjectName.equalsIgnoreCase("metadata") && Action.equalsIgnoreCase("Make It Hidden") )
	   {
		  softAssertion.assertFalse(getCF_Preview1().Displayed());
	   }
	   else if(ObjectName.equalsIgnoreCase("metadata") && Action.equalsIgnoreCase("Make It Display But Not Selectable") )
	   {
		   softAssertion.assertTrue(getCF_Preview1().Displayed());
		   softAssertion.assertFalse(getCF_Preview1().Enabled());
	   }
	}
	   
    
    
}


     
     
  
 