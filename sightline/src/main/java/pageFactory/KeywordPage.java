package pageFactory;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import junit.framework.Assert;
import testScriptsSmoke.Input;

/**
 * @author Mohan.Venugopal
 *
 */
/**
 * @author Mohan.Venugopal
 *
 */
public class KeywordPage {

    Driver driver;
    BaseClass base;
    SoftAssert softAssert;
   
    public Element getNewKeywordButton(){ return driver.FindElementById("btnAddKeyword"); }
    public Element getKeywordName(){ return driver.FindElementById("KeywordName"); }
    public Element getDescription(){ return driver.FindElementById("Description"); }
    public Element getSaveBtn(){ return driver.FindElementById("btnSaveKeywords"); }
    public Element getKeywords(){ return driver.FindElementById("Keywords");  }
    public Element getYesButton(){ return driver.FindElementById("bot1-Msg1");  }
    public Element getSelectColor(){ return driver.FindElementById("ddlKeywordColors");  }
    public ElementCollection getKeywordsList(){ return driver.FindElementsByXPath("//table[@id=KeywordsDatatable]/tbody/tr/td[4]");}
    
    public Element getSuccessMsgHeader(){ return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span"); }
    public Element getSuccessMsg(){ return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p"); }
    
    public Element getDeleteButton(String keywordName){ return driver.FindElementByXPath("//td[text()='"+keywordName+"']/parent::tr//a[text()='Delete']"); }
 
    //Added by Gopinath - 03/01/2022
    public Element getKeyword(int rowNum){ return driver.FindElementByXPath("//table[@id='KeywordsDatatable']//tbody//tr["+rowNum+"]//td[4]"); }
    public ElementCollection totalRows(){ return driver.FindElementsByXPath("//table[@id='KeywordsDatatable']//tbody//tr"); }
    public Element getNextButton(){ return driver.FindElementByXPath("//a[text()='Next']/parent::li"); }
    public Element getNextButtonEle(){ return driver.FindElementByXPath("//a[text()='Next']"); }
    
  //Added by Mohan
    public Element getKeywordListEmpty(){ return driver.FindElementByXPath("//*[@id='KeywordsDatatable']//td[contains(text(),'Your')]"); }
    public Element getKeywordRowList(){ return driver.FindElementById("KeywordsDatatable_info"); }
    public Element getKeywordSecurityGroupList(){ return driver.FindElementById("ddlSecurityGroup"); }
    public Element getKeywordTableListValues(String fieldValues){ return driver.FindElementByXPath("//*[@id='KeywordsDatatable']//th[text()='"+fieldValues+"']"); }
    public Element getKeywordTableActionFields(String fieldValues){ return driver.FindElementByXPath("//*[@id='KeywordsDatatable']//td//a[text()='"+fieldValues+"']"); }
    public ElementCollection getAssgnPaginationCount() {
		return driver.FindElementsByCssSelector("li[class*='paginate_button '] a");
	}
    public Element getKeywordTableFirstFieldValue(String fieldValues){ return driver.FindElementByXPath("//*[@id='KeywordsDatatable']/tbody/tr/td["+fieldValues+"]"); }
    public Element getKeywordSecurityErrorMessage(){ return driver.FindElementByXPath("//label[contains(text(),'Name already')]"); }
    public Element getCancelBtn(){ return driver.FindElementById("btnCancel"); }
    public Element getKeywordTableEditFields(){ return driver.FindElementByXPath("//*[@id='KeywordsDatatable']//tr[1]//td//a[text()='Edit']"); }  
    public Element getManageBtn(){ return driver.FindElementByName("Manage"); }
    public Element getKeywordBtn() { return driver.FindElementByName("Keywords");}
    
    //Annotation Layer added successfully
    public KeywordPage(Driver driver){

        this.driver = driver;
        this.driver.getWebDriver().get(Input.url+"Keywords/Keywords");
        driver.waitForPageToBeReady();
        base= new BaseClass(driver);
       }

    public void AddKeyword(String keywordname,String keywords) {
		
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getNewKeywordButton().Visible()  ;}}), Input.wait60); 
    	getNewKeywordButton().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getKeywordName().Visible()  ;}}), Input.wait30); 
    	getKeywordName().SendKeys(keywordname);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getDescription().Visible()  ;}}), Input.wait30); 
    	getDescription().SendKeys(keywordname);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getKeywords().Visible()  ;}}), Input.wait30); 
    	getKeywords().SendKeys(keywords);
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getSaveBtn().Visible()  ;}}), Input.wait30); 
    	getSaveBtn().Click();
    	
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			getYesButton().Visible()  ;}}), Input.wait30); 
    	getYesButton().Click();
    	
    	base.VerifySuccessMessage("Keyword Highlighting Group added successfully");
    	Reporter.log("Keyword '"+keywordname+" with keywords "+keywords+"' added successfully",true);
        base.CloseSuccessMsgpopup();
      }
    public void getIntoFullScreen() throws AWTException {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_F11);
		r.keyRelease(KeyEvent.VK_F11);
	}

	public void getExitFullScreen() throws AWTException {
		Robot r = new Robot();
		r.keyPress(KeyEvent.VK_F11);
		r.keyRelease(KeyEvent.VK_F11);
	}
    public void addKeywordWithColor(String keywordname, String color) throws AWTException {
    	base.waitForElement(getNewKeywordButton());
    	getNewKeywordButton().waitAndClick(5);
    	base.waitForElement(getKeywordName());
    	getKeywordName().SendKeys(keywordname);
    	base.waitForElement(getDescription());
    	getDescription().SendKeys(keywordname);
    	base.waitForElement(getKeywords());
    	getKeywords().SendKeys(keywordname);
    	getSelectColor().selectFromDropdown().selectByVisibleText(color);
    	getIntoFullScreen();
    	base.waitForElement(getSaveBtn());
    	getSaveBtn().waitAndClick(5);
    	base.waitForElement(getYesButton());
    	getYesButton().waitAndClick(5);
    	getExitFullScreen();
    	base.VerifySuccessMessage("Keyword Highlighting Group added successfully");
        base.CloseSuccessMsgpopup();    	
    }
    
    public void deleteKeyword(String keyword) {
    	base.waitForElement(getDeleteButton(keyword));
    	getDeleteButton(keyword).waitAndClick(5);
    	base.waitForElement(getYesButton());
    	getYesButton().waitAndClick(5);
    	base.VerifySuccessMessage("Keyword Highlighting Group successfully deleted");
        base.CloseSuccessMsgpopup();    	
    	
    }
    /**
	 * @author Gopinath
	 * Description: Method for navigating to keyword page.
	 */
    public void navigateToKeywordPage(){
    	try {
    		driver.getWebDriver().get(Input.url+"Keywords/Keywords");
    	}catch(Exception e) {
    		e.printStackTrace();
    		base.failedStep("Navigaing to keyword page is failed"+e.getLocalizedMessage());
    	}
    }
    
    /**
   	 * @author Gopinath
   	 * @Description: Method for getting all keywords
   	 * @return keywords : keywords list of String values that total keywords in keywords table.
   	 */
       public List<String> getAllKeywords(){
    	   List<String> keywords = new ArrayList<String>();
       	try {
       		driver.getWebDriver().get(Input.url+"Keywords/Keywords");
       		base.waitTime(2);
       		getKeyword(1).isElementAvailable(15);
       		int rowCount = totalRows().FindWebElements().size();
       		for(int i=0;i<rowCount;i++) {
       			keywords.add(getKeyword(i+1).getText().trim());
       			String getNextButtonAtt = getNextButton().GetAttribute("class");
       			if((i==rowCount-1)&&!(getNextButtonAtt.contains("disabled"))) {
       				driver.scrollingToBottomofAPage();
       				driver.waitForPageToBeReady();
       				getNextButtonEle().isElementAvailable(8);
       				getNextButtonEle().Click();
       				driver.waitForPageToBeReady();
       				rowCount = totalRows().FindWebElements().size();
       				i=-1;
       			}
       		}
       		System.out.println("Keywords are "+keywords);
       		base.passedStep("Keywords are "+keywords);
       	}catch(Exception e) {
       		e.printStackTrace();
       		base.failedStep("Execption occured while verifying all keywords"+e.getLocalizedMessage());
       	}
       	return keywords;
       }
 
       /**
      	 * @author Gopinath
      	 * @Description: Method for getting all keywords
      	 * @param keyword : keyword is String value that name of keyword.
      	 */
       public void deleteKeywordByName(String keyword) {
    	  	try {
           		driver.getWebDriver().get(Input.url+"Keywords/Keywords");
           		base.waitTime(2);
           		getKeyword(1).isElementAvailable(15);
           		int rowCount = totalRows().FindWebElements().size();
           		for(int i=0;i<rowCount;i++) {
           			String val =getKeyword(i+1).getText();
           			if(getKeyword(i+1).getText().trim().equalsIgnoreCase(keyword)) {
           				base.waitForElement(getDeleteButton(keyword));
           		    	getDeleteButton(keyword).waitAndClick(5);
           		    	base.waitForElement(getYesButton());
           		    	getYesButton().waitAndClick(5);
           		    	base.VerifySuccessMessage("Keyword Highlighting Group successfully deleted");
           		        base.CloseSuccessMsgpopup(); 
           		        break;
           			}
           			String getNextButtonAtt = getNextButton().GetAttribute("class");
           			if((i==rowCount-1)&&!(getNextButtonAtt.contains("disabled"))) {
           				driver.scrollingToBottomofAPage();
           				driver.waitForPageToBeReady();
           				getNextButtonEle().isElementAvailable(8);
           				getNextButtonEle().Click();
           				driver.waitForPageToBeReady();
           				rowCount = totalRows().FindWebElements().size();
           				i=-1;
           			}
           		}
    		}catch(Exception e) {
           		e.printStackTrace();
           		base.failedStep("Execption occured while deleting keyword"+e.getLocalizedMessage());
           	}
       	
       } 
       /**
     	 * @author Gopinath
     	 * @Description: Method for add keyword
     	 * @param keyword : keyword is String value that name of keyword.
     	 * @param color : color is String value that name of color
     	 */
       public void addKeyword(String keywordname, String color) throws AWTException {
       	base.waitForElement(getNewKeywordButton());
       	getNewKeywordButton().waitAndClick(5);
       	base.waitForElement(getKeywordName());
       	getKeywordName().SendKeys(keywordname);
       	base.waitForElement(getDescription());
       	getDescription().SendKeys(keywordname);
       	base.waitForElement(getKeywords());
       	getKeywords().SendKeys(keywordname);
       	getSelectColor().selectFromDropdown().selectByVisibleText(color);
       	getIntoFullScreen();
       	base.waitForElement(getSaveBtn());
       	getSaveBtn().waitAndClick(5);
       	base.waitForElement(getYesButton());
       	getYesButton().waitAndClick(5);
       	getExitFullScreen();
        	
       }
       
       /**
    	 * @author Gopinath
    	 * @Description: Method for add keyword
    	 * @param keyword : keyword is String value that name of keyword.
    	 * @param color : color is String value that name of color
    	 * @param keywordname : keywordname is string value that keyword name
    	 */
       public void addKeyword(String keywordName,String keywordname, String color) throws AWTException {
         	base.waitForElement(getNewKeywordButton());
         	getNewKeywordButton().waitAndClick(5);
         	base.waitForElement(getKeywordName());
         	getKeywordName().SendKeys(keywordName);
         	base.waitForElement(getDescription());
         	getDescription().SendKeys(keywordname);
         	base.waitForElement(getKeywords());
         	getKeywords().SendKeys(keywordname);
         	getSelectColor().selectFromDropdown().selectByVisibleText(color);
         	getIntoFullScreen();
         	base.waitForElement(getSaveBtn());
         	getSaveBtn().waitAndClick(5);
         	base.waitForElement(getYesButton());
         	getYesButton().waitAndClick(5);
         	getExitFullScreen();
          	
         }
/**
      	 * @author Arunkumar
           * @throws AWTException 
      	 * @Description: Method for adding two same keywords with different color
      	 */
    
         public void addTwoSameKeywordWithDifferentColor(String[] keywordGroupName,String keywordName,String[] color) throws AWTException {
      	driver.getWebDriver().get(Input.url+"Keywords/Keywords");
      	for(int i=0;i<=1;i++) {
      		driver.waitForPageToBeReady();
          	base.waitForElement(getNewKeywordButton());
             	getNewKeywordButton().waitAndClick(5);
             	base.waitForElement(getKeywordName());
             	getKeywordName().SendKeys(keywordGroupName[i]);
             	base.waitForElement(getDescription());
             	getDescription().SendKeys(keywordName);
             	base.waitForElement(getKeywords());
             	getKeywords().SendKeys(keywordName);
             	getSelectColor().selectFromDropdown().selectByVisibleText(color[i]);
             	getIntoFullScreen();
             	base.waitForElement(getSaveBtn());
             	getSaveBtn().waitAndClick(5);
             	base.waitForElement(getYesButton());
             	getYesButton().waitAndClick(5);
             	getExitFullScreen();
             	base.VerifySuccessMessage("Keyword Highlighting Group added successfully");
              base.CloseSuccessMsgpopup();
      		
      	}
   }
         
         
         /**
      	 * @author Gopinath
      	 * @Description: Method for add keyword
      	 * @param keyword : keyword is String value that name of keyword.
      	 * @param color : color is String value that name of color
      	 * @param keywordname : keywordname is string value that keyword name
      	 */
         public void addKeywordWithoutFullScreen(String keywordName,String keywordname, String color) throws AWTException {
           	base.waitForElement(getNewKeywordButton());
           	getNewKeywordButton().waitAndClick(5);
           	base.waitForElement(getKeywordName());
           	getKeywordName().SendKeys(keywordName);
           	base.waitForElement(getDescription());
           	getDescription().SendKeys(keywordname);
           	base.waitForElement(getKeywords());
           	getKeywords().SendKeys(keywordname);
           	getSelectColor().selectFromDropdown().selectByVisibleText(color);
           	base.waitTime(2);
           	getSaveBtn().isElementAvailable(10);
           	getSaveBtn().Click();
           	getYesButton().isElementAvailable(10);
           	getYesButton().Click();        	
           }
         
         /**
          * @author Mohan.Venugopal
          * @description: To verify KeywordHighlight count
         * @throws AWTException
         */
        public void validateKeywordHighlightingIsPresent() throws AWTException {

        	 driver.waitForPageToBeReady();
        	 String rowList = getKeywordRowList().getText();
        	 System.out.println(rowList);
        	 if (rowList.contains("0")&&rowList.contains("3")) {
        		 
					base.waitForElement(getNewKeywordButton());
					getNewKeywordButton().waitAndClick(5);
					addKeyword("test", "testDes", "Aqua");
					
					driver.waitForPageToBeReady();
					getNewKeywordButton().waitAndClick(5);
					addKeyword("basis", "basisDes", "Blue");
					
					driver.waitForPageToBeReady();
					getNewKeywordButton().waitAndClick(5);
					addKeyword("high", "highDes", "Gold");
					
					driver.waitForPageToBeReady();
					getNewKeywordButton().waitAndClick(5);
					addKeyword("is", "isDes", "Pink");
					
					base.passedStep("There are more than 2 Keywords highlighting present in each security Groups (1 -1 each comments) which also exists in newly created Project.");
				}
        		
        	 else {
        		 base.passedStep("There are more than 2 Keywords highlighting present in each security Groups (1 -1 each comments) which also exists in newly created Project.");
			}
        
		}
         
        /**
         * @author Mohan.Venugopal
         * @description: To verify KeywordHighlight list
        * @throws AWTException
        */
         public void verifyKeywordHighlight() {
        	 driver.waitForPageToBeReady();
        	 String rowList = getKeywordRowList().getText();
        	 System.out.println(rowList);
        	 if (rowList.contains("Showing 1 to")) {
        		 base.passedStep("There are more than 2 Keywords highlighting present in each security Groups (1 -1 each comments) which also exists in newly created Project.");
        		 
        	 }else {
        		 base.failedStep("There are no Keyword Highlight in the project");
			}
        	 
		}
         /**
          * @author Mohan.Venugopal
          * @description: To verify Manage Keyword Page for RMU and PA
         * @throws AWTException
         */
         public void verifyManageKeywordPageRMUAndPA(String userName) {

        	 driver.waitForPageToBeReady();
        	 base.waitForElement(getKeywordSecurityGroupList());
        	 if (userName.contains("RMU")&&!getKeywordSecurityGroupList().Enabled()) {
				base.passedStep("Security group under RMU is added is  displayed as read only and selected are on the Manage Keywords page.");
			}else if (userName.contains("PA")&&getKeywordSecurityGroupList().Enabled()) {
				base.passedStep("Security group under RMU is added is displayed as read only and selected are on the Manage Keywords page.");
			}else {
				base.failedStep("Security group under RMU is added is not displayed as read only and selected are not on the Manage Keywords page.");
			}
        	 
        	if (getKeywordTableActionFields("Edit").isElementPresent()&&getKeywordTableActionFields("Delete").isElementPresent()) {
        		
        		base.passedStep("Keywords list are displayed with columns - Group Name  - Color  - Description  - Keywords  - Action [Edit, Delete buttons] successfully");
			} else {
				base.failedStep("Keywords list are not displayed");
			}
        	
        	ElementCollection paginationCount = getAssgnPaginationCount();
        	int pageSize = paginationCount.size();
        	System.out.println(pageSize);
        	if (pageSize>1) {
        		base.passedStep("The pagination are displayed on Manage Keywords page successfully");
				
			}else {
				base.failedStep("The pagination are not displayed on Manage Keywords page");
			}
		}
         
         /**
          * @author Mohan.Venugopal
          * @description: To verify Dulpicate Keyword Page
         * @throws AWTException
         */
         public void verifyDuplicateKeywordFromList() {

        	 
        	 driver.waitForPageToBeReady();
        	 base.waitForElement(getKeywordTableFirstFieldValue("1"));
        	 String firstFeildValue = getKeywordTableFirstFieldValue("1").getText();
        	 System.out.println(firstFeildValue);
        	 
        	 base.waitForElement(getNewKeywordButton());
        	 getNewKeywordButton().waitAndClick(5);
        	 base.waitForElement(getKeywordName());
         	getKeywordName().SendKeys(firstFeildValue);
         	base.waitForElement(getDescription());
         	getDescription().SendKeys(firstFeildValue);
         	base.waitForElement(getKeywords());
         	getKeywords().SendKeys(firstFeildValue);
         	getSelectColor().selectFromDropdown().selectByVisibleText("Aqua");
         	
         	base.waitForElement(getSaveBtn());
         	getSaveBtn().waitAndClick(5);
         	base.waitForElement(getYesButton());
         	getYesButton().waitAndClick(5);
         	
         	String errorMessage = getKeywordSecurityErrorMessage().getText();
         	System.out.println(errorMessage);
         	softAssert = new SoftAssert();
         	softAssert.assertEquals("Name already exists", errorMessage);
         	softAssert.assertAll();
         	base.passedStep("Message is displayed like Keyword with name already exists with error code successfully.");
        	 
        	 
		}
         
         
        /**
         * @author Mohan.Venugopal
         * @description: To check dit option for existing Keyword
         * @param keywordName
         */
        public void editExistigKeywordAndVerifyThem(String keywordName) {

        	 
        	 driver.waitForPageToBeReady();
        	 base.waitForElement(getKeywordTableEditFields());
        	 getKeywordTableEditFields().waitAndClick(5);
        	 
        	 base.waitForElement(getDescription());
        	 getDescription().Clear();
          	getDescription().SendKeys(keywordName);
          	
          	base.waitForElement(getSaveBtn());
         	getSaveBtn().waitAndClick(5);
         	base.waitForElement(getYesButton());
         	getYesButton().waitAndClick(5);
         	
         	base.VerifySuccessMessage("Keyword Highlighting Group successfully updated");
         	
         	driver.waitForPageToBeReady();
         	getKeywordTableFirstFieldValue("3").isElementAvailable(5);
         	String keywordName1 = getKeywordTableFirstFieldValue("3").getText();
         	softAssert = new SoftAssert();
         	softAssert.assertEquals(keywordName, keywordName1);
         	softAssert.assertAll();
        	 
        	 
        	}
         
        /**
         * @author Mohan.Venugopal
         * @description: To add keyword and click on cancel button.
         * @param keywordName, color
         */
         public void addKeywordsAndPressCancelButton(String keywordName, String color) {

        	driver.waitForPageToBeReady();
        	base.waitForElement(getNewKeywordButton());
          	getNewKeywordButton().waitAndClick(5);
          	base.waitForElement(getKeywordName());
          	getKeywordName().SendKeys(keywordName);
          	base.waitForElement(getDescription());
          	getDescription().SendKeys(keywordName);
          	base.waitForElement(getKeywords());
          	getKeywords().SendKeys(keywordName);
          	getSelectColor().selectFromDropdown().selectByVisibleText(color);
          	base.waitForElement(getCancelBtn());
          	getCancelBtn().waitAndClick(5);
          	base.waitForElement(getNewKeywordButton());
          	softAssert = new SoftAssert();
          	softAssert.assertTrue(getNewKeywordButton().isElementPresent());
          	softAssert.assertAll();
          	
          	base.passedStep("keyword group and keyword is not be created on click of Cancel button successfully");
          	
          	
        	 
		}
	
 }