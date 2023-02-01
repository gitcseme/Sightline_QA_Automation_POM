package pageFactory;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;

public class SourceLocationPage {
	Driver driver;
	BaseClass base;
	SoftAssert softassert;
	CollectionPage collection;

	public Element getSrcDeleteBtn(String srceLoc) {
		return driver.FindElementByXPath("//td[text()='" + srceLoc + "']//parent::tr//a[text()='Delete']");
	}

	public Element getDeletePopUpHeader() {
		return driver.FindElementByXPath("//span[@class='MsgTitle']");
	}

	public Element getDeleteMsg() {
		return driver.FindElementByXPath("//p[@class='pText']");
	}

	public Element getDeleteYesBtn() {
		return driver.FindElementByXPath("//button[normalize-space()='Yes']");
	}

	public Element getAddNewSrcLoction() {
		return driver.FindElementByXPath("//input[@id='btnAddSourceLocaiton']");
	}

	public ElementCollection getTableHeader() {
		return driver.FindElementsByXPath("//div[@class='dataTables_scrollHeadInner']//tr//th[@aria-controls=\"dtSourceList\"]");
	}

	public ElementCollection getColumnValues(int i) {
		return driver.FindElementsByXPath("//tr[@role='row']//td[" + i + "]");
	}
	public Element getSetUpASourceLocation() {
		return driver.FindElementById("divAddNewSourceLocation");
	}
	
	public Element getSetUpASourceLocationLink() {
		return driver.FindElementByXPath("//a[text()='Set up a source location']");
	}
	
	
	public ElementCollection getSourceLocationListTable() {
		return driver.FindElementsByXPath("//*[@id='dtSourceList']//tbody//tr");
	}
	
	
	public Element getSetUpASourceLocationDeleteButton() {
		return driver.FindElementByXPath("//*[@id='dtSourceList']//tr[1]//a[text()='Delete']");
	}
	
	public Element getSetUpASourceLocationDeleteYesButton() {
		return driver.FindElementByXPath("//button[@id='bot1-Msg1']");
	}
	
	public Element getSetUpASourceLocationEditYesButton() {
		return driver.FindElementByXPath("//button[@id='bot1-Msg1']");
	}
	
	public Element getDataSourceName(String sourceName) {
		return driver.FindElementByXPath("//table[@id='dtSourceList']//tr//td[text()='"+sourceName+"']");
	}
	
	public Element columnHeader(String headerName) {
		return driver.FindElementByXPath("//th[text()='" + headerName + "']");
	}

	public Element getSrcActionBtn(String srceLoc, String actionType) {
		return driver.FindElementByXPath("//td[text()=\""+srceLoc+"\"]//parent::tr//a[text()='"+actionType+"']");
	}
	
	public Element getSourceLocationPopUp() {
		return driver.FindElementByXPath("//div[@class='ui-widget-overlay ui-front']");
	}
	
	public Element getEditSourceLocationPopUp() {
		return driver.FindElementByXPath("/html/body/div[7]/div[1]");
	}

	public Element getSrceLocPopup_SaveBtn() {
		return driver.FindElementByXPath(
				"//div[@class='ui-widget-overlay ui-front']//preceding::input[@id='btnSaveSourceLocation']");
	}
	
	public Element getSourceLocationPageFirstCollectionSelect() {
		return driver.FindElementByXPath("(//table[@id='dtSourceList']//tr//td[5]//a[1])[1]");
	}
	
	public Element getSourceLocationPageFirstdataSourceSelect() {
		return driver.FindElementByXPath("(//*[@id='dtSourceList']/tbody/tr[1]/td[2])[1]");
	}
	public Element getNewSrcLocationBtn() {
		return driver.FindElementByXPath("//input[@id='btnAddSourceLocaiton']");
	}
	public Element getOpenSightlineConnectToONNABtn() {
        return driver.FindElementByXPath("//input[@id='btnOpenSightlineCollect']");
    }
	public Element getOpenSightlineConnectToONNAHeaderText() {
        return driver.FindElementByXPath("//div[@id='content']/div[2]/div/div[3]/h1");
    }
	
	
	
	public SourceLocationPage(Driver driver) {
		this.driver = driver;
		base = new BaseClass(driver);
		softassert = new SoftAssert();
		collection=new CollectionPage(driver);
	}

	/**
	 * @Author Jeevitha
	 * @Dsecription : delete created source location
	 * @param srcLoction
	 * @param delete
	 */
	public void deleteSourceLocation(String srcLoction, boolean delete) {
		String expectedUrl = Input.url + Input.sourceLocationPageUrl;
		base.verifyPageNavigation(expectedUrl);

		base.waitForElement(getSrcDeleteBtn(srcLoction));
		getSrcDeleteBtn(srcLoction).waitAndClick(10);

		String expectedMsg = "If you delete this source location, you will no longer be able to use any collections configured with this source location. Do you want to really delete this source location?";
		base.waitForElement(getDeletePopUpHeader());
		softassert.assertEquals("Delete Source Location", getDeletePopUpHeader().getText().toString());
		base.passedStep("Displayed Header is : Delete Source Location");

		base.waitForElement(getDeleteMsg());
		softassert.assertEquals(expectedMsg, getDeleteMsg().getText().toString());
		base.passedStep("Displayed message - " + expectedMsg);

		getDeleteYesBtn().waitAndClick(10);
		base.VerifySuccessMessage("Source Location deleted successfully");
		softassert.assertAll();
	}

	/**
	 * @Author Jeevitha
	 * @Description : get the Table Value from source location page
	 * @param headerName
	 * @return
	 */
	public List<String> getListFromTable(String headerName, boolean prefixSuffix) {
		int i;
		i = base.getIndex(getTableHeader(), headerName);
		System.out.println(headerName + "--" + i);

		driver.waitForPageToBeReady();
		List<String> elementNames = new ArrayList<>();
		List<WebElement> elementList = null;
		elementList = getColumnValues(i).FindWebElements();
		for (WebElement webElementNames : elementList) {
			String elementName = webElementNames.getText();
			base.stepInfo("Available Source Location : "+elementName);
			if (prefixSuffix) {
				elementNames.add("(" + elementName + ")");
			} else {
				elementNames.add(elementName);
			}
		}
		return elementNames;
	}
	
	
	
	/**
	 * @author Mohan.Venugopal
	 * @description To Verify Source Location link is present in Collections Page.
	 * 
	 */
	public void verifySourceLocationIsNotPresent() {

		
		driver.waitForPageToBeReady();
		
		if (getSetUpASourceLocation().isElementAvailable(5)) {
			base.stepInfo("The Set Up for Source link is present in New Collection Page");
			
		}else {
			driver.Navigate().to("https://sightlinept.consilio.com/Collection/SourceLocation");
			
			ElementCollection locationListTable = getSourceLocationListTable();
			int locationListSize = locationListTable.size();
			System.out.println(locationListSize);
			
			for (int i = 0; i < locationListSize; i++) {
				driver.waitForPageToBeReady();
				base.waitTime(2);
				if (getSetUpASourceLocationDeleteButton().isElementAvailable(5)) {
					
					base.waitForElement(getSetUpASourceLocationDeleteButton());
					getSetUpASourceLocationDeleteButton().waitAndClick(5);
					
					base.waitForElement(getSetUpASourceLocationDeleteYesButton());
					getSetUpASourceLocationDeleteYesButton().waitAndClick(5);
					
					base.VerifySuccessMessage("Source Location deleted successfully");
					
				}else {
					base.stepInfo("All Source Locations are deleted successfully");
					base.passedStep("Source Location Link is Available now");
				}
				
				
			}
			
		}
		
		
	}
	
	
	/**
	 * @author Mohan.Venugopal
	 * @description: To verify source location name in source location page.
	 * @param sourceName
	 */
	public void verifySourceLocationName(String sourceName) {

		base.waitForElement(getDataSourceName(sourceName));
		String dataSourceName = getDataSourceName(sourceName).getText();
		System.out.println(dataSourceName);
		softassert.assertEquals(sourceName, dataSourceName);
		softassert.assertAll();
		base.passedStep("Newly added  source location entry is appeared on “Source Locations “screen(grid).");

	}
	
	/**
	 * @Author Jeevitha
	 * @Description : edit any field from source location popup
	 * @param dataSrcName
	 * @param edit
	 * @param editElement
	 * @param editText
	 */
	public void editSourceLocationDetails(String dataSrcName, boolean clickYes, Element editElement, String elementName,
			String newValue) {

		driver.waitForPageToBeReady();
		base.waitForElement(getSrcActionBtn(dataSrcName, "Edit"));
		(getSrcActionBtn(dataSrcName, "Edit")).waitAndClick(10);

		String expectedMsg = "If you change the attributes of this source location, all existing collections configured with this source location will use the modified attributes. Do you wish to continue?";

		base.waitForElement(getDeletePopUpHeader());
		softassert.assertEquals("Edit Source Location", getDeletePopUpHeader().getText().toString());
		base.passedStep("Displayed Header is : Edit Source Location");

		base.waitForElement(getDeleteMsg());
		softassert.assertEquals(expectedMsg, getDeleteMsg().getText().toString());
		base.passedStep("Displayed message - " + expectedMsg);

		if (clickYes) {
			getDeleteYesBtn().waitAndClick(10);
			if (getSourceLocationPopUp().isElementAvailable(10)) {
				base.stepInfo("Edit Source location Popup is Displayed");

				String beforeEdit = editElement.getText();
				base.stepInfo(elementName + "value Before Editing is :" + beforeEdit);

				editElement.SendKeys(newValue);
				base.stepInfo(elementName + " New value is : " + newValue);

				base.waitTillElemetToBeClickable(getSrceLocPopup_SaveBtn());
				getSrceLocPopup_SaveBtn().waitAndClick(10);

				base.VerifySuccessMessage("Source Location updated successfully");
				base.CloseSuccessMsgpopup();
			}
		}
	}
	
	public void performEditSource(String srceTyp, String srceName, String tentID, String appID, String secrtKey) {
		SourceLocationPage source = new SourceLocationPage(driver);

		if (source.getEditSourceLocationPopUp().isElementAvailable(10)) {
			base.passedStep("Add New Source location Popup is opened");

			// data source type
			base.waitForElement(collection.getDataSrcType());
			String actualType = collection.getDataSrcType().getText();
			String expectedType = "Microsoft 365";
			base.textCompareEquals(actualType, expectedType, "Data Source type : " + actualType,
					"Data Source type is not as expected");

			// data source Name
			collection.getDataSourceName().waitAndClick(5);
			collection.getDataSourceName().SendKeys(srceName);
			base.stepInfo("Entered Data source Name : " + srceName);
			// tenant ID
			collection.getTenantID().waitAndClick(5);
			collection.getTenantID().Clear();
			collection.getTenantID().SendKeys(tentID);
			base.stepInfo("Entered Tentant ID : " + tentID);
			// Application ID
			collection.getApplicationID().waitAndClick(5);
			collection.getApplicationID().SendKeys(appID);
			base.stepInfo("Entered Application ID : " + appID);
			// Application secret key
			collection.getAppSecretKey().waitAndClick(5);
			collection.getAppSecretKey().Clear();
			collection.getAppSecretKey().SendKeys(secrtKey);
			base.stepInfo("Entered Secret Key : " + secrtKey);

		} else {
			base.failedStep("Add New Source Location Popup is Not Present");
		}

		base.waitTillElemetToBeClickable(collection.getAddNewPopup_SaveBtn());
		collection.getAddNewPopup_SaveBtn().waitAndClick(10);
		base.stepInfo("Clicked Save Button");
		System.out.println("srceName :-"+srceName);

		if (srceName.equals("") && tentID.equals("") && appID.equals("") && secrtKey.equals("")) {

			for (int i = 1; i < 5; i++) {
				base.waitForElement(collection.getErrroMsg(i));
				String errorMsg = collection.getErrroMsg(i).getText();
				if (errorMsg.equals("")) {
					base.failedStep("Expected Error Message is Not Displayed");
				} else {
					System.out.println("Error : " + errorMsg);
					base.passedStep("Dispalyed Error Msg : " + errorMsg);
				}
			}
		}
		
		
			base.VerifySuccessMessage("Source Location updated successfully");
			base.CloseSuccessMsgpopup();
		
	}
	/**
     * @Author Hema MJ
     * @Description : verify sightline connect to ONNA button
     * @param Scbpoflag
     */
    public void verifySightlineConnectONNAText(boolean Scbpoflag) throws InterruptedException {
    	if(Scbpoflag) {
    		if(base.ValidateElement_PresenceReturn(getOpenSightlineConnectToONNAHeaderText())) {
    			String text=getOpenSightlineConnectToONNAHeaderText().getText();
    			text.trim();
    			System.out.println("text :-"+text);
    			Assert.assertEquals("Sightline Collect (Powered by Onna©)",text);
    			base.passedStep("Sightline Collect (Powered by Onna©) text is displayed");	
    		}
    		else {
    			base.failedStep("Sightline Collect (Powered by Onna©) text is not displayed");	
    		}
    		
    	}else {
    		base.ValidateElement_Absence(getOpenSightlineConnectToONNAHeaderText(), "Open sightline connect Text");
    		base.passedStep("Open sightline connect Text is not present" );
    		
    	}
    	
    }
    /**
     * @Author Hema MJ
     * @Description : verify sightline connect to ONNA Text
     * @param Scbpoflag
   
     */
    public void verifySightlineConnectONNAbutton(boolean Scbpoflag) throws InterruptedException {
    	if(Scbpoflag) {
    		base.ValidateElement_Presence(getOpenSightlineConnectToONNABtn(), "Open sightline connect button");
    		
    	}else {
    		base.ValidateElement_Absence(getOpenSightlineConnectToONNABtn(), "Open sightline connect button");
    		base.passedStep("Open sightline connect button is not present" );
    		
    	}
    	
    }
	/**
     * @Author Hema MJ
     * @Description : verify sightline connect to ONNA
     * @param dataSrcName
     * @param edit
     * @param editElement
     * @param editText
     * @throws InterruptedException 
     */
    public void verifyConnectToONNAbeforeclickingbtn(String ONNAdirectURL) throws InterruptedException {
            driver.waitForPageToBeReady();
            boolean flag=base.verifyNewTabOpened();
            driver.waitForPageToBeReady();
            for(int i=0;i<=10;i++) {
            	base.openNewTab();
        		base.waitTime(5);
        		flag=base.verifyNewTabOpened();
            	System.out.println(flag);
            	if(flag==false) {    	
            		break;
            	}
            }
            
            base.switchTab(1);
            driver.get(ONNAdirectURL);
            driver.waitForPageToBeReady();
            try {
            	String expectedError="AccessDenied";
            	base.verifyPageNavigation(expectedError); 
            	driver.close();
            	base.switchTab(0);
            	
                
            }catch(Exception e) {
               e.printStackTrace();
            }
            


    }
    
	/**
     * @Author Hema MJ
     * @Description : verify sightline connect to ONNA
     * @param dataSrcName
     * @param edit
     * @param editElement
     * @param editText
     * @throws InterruptedException 
     */
    public void verifyConnectToONNAAfterclickingbtn(String ONNAURL) throws InterruptedException {
    	LoginPage login=new LoginPage(driver);
    	base.switchTab(0);
        base.waitForElement(getOpenSightlineConnectToONNABtn());
        getOpenSightlineConnectToONNABtn().waitAndClick(5);
        base.switchTab(1);
        driver.waitForPageToBeReady();
        try {
        	driver.waitForPageToBeReady();
        	base.verifyPageNavigation(ONNAURL);
        	base.switchTab(0);
        	login.logout();
        	base.passedStep("logged Out Of Sightline Successfully");
        	base.switchTab(1);
        	for(int i=0;i<=5;i++) {
        		driver.waitForPageToBeReady();
        	}
        	base.verifyPageNavigation(ONNAURL);
        	base.passedStep("Onna Is Still logged in");
        	driver.close();
        	base.switchTab(0);
        	base.waitTime(5);
        	}catch(Exception e) {
        			e.printStackTrace();
        	}
     
    }


}
