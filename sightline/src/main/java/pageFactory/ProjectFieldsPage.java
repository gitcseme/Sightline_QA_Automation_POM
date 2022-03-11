package pageFactory;

import java.util.List;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;

public class ProjectFieldsPage {

	 Driver driver;
	 BaseClass base;
	
	public Element getAddProjectFieldButton(){ return driver.FindElementByXPath("//a[@id='btnAddProjectField']"); }
    public Element getFieldNameTextField() { return driver.FindElementById("FieldName"); }
    public Element getPresentationFieldLabelTextField() { return driver.FindElementById("FieldLabel"); }
    public Element getFieldGroupNameDropDown() { return driver.FindElementById("ddlProjectFieldGroups"); }
    public Element getFieldClassificationDropDown() { return driver.FindElementById("ddlFieldClassification"); }
    public Element getFieldDescriptionTextArea(){ return driver.FindElementById("FieldDesc"); }
    public Element getFieldTypeDropDown() { return driver.FindElementById("ddlProjectFieldGroupCode"); }
    public Element getIsSearchableCheckBox(){ return driver.FindElementByXPath("//label[text()='Is Searchable :']/../..//i"); }
    public Element getIsEditableCheckBox(){ return driver.FindElementByXPath("//label[text()='Is Editable :']/../..//input[@id='IsEditable']/..//i"); }
    public Element getSaveButton() { return driver.FindElementById("btnProjectFieldAddSave"); }
    public Element getFiltersByTextField() { return driver.FindElementById("txtsearchField"); }
    public Element getApplyButton() { return driver.FindElementById("btnAppyFilter"); }
    public Element getSearchedFieldName(){ return driver.FindElementByXPath("//table[@id='ProjectFieldsDataTable']//tbody/tr[1]/td[1]"); }
    public Element getFieldLengthDropDown() { return driver.FindElementById("ddlDataTypeColumnLengthCode"); }
    public Element getProjectFieldInactiveButton(String projectFieldName){ return driver.FindElementByXPath("//td[text()='"+projectFieldName+"']//..//a[text()='Inactive']"); }
    public Element getConfirmOkButton(){ return driver.FindElementById("bot1-Msg1"); }
    public Element getReindexAlertOkButton(){ return driver.FindElementById("bot1-Msg2"); }
    public Element getAllProductionBatesRanges() {	return driver.FindElementByXPath("//tr//td[text()='AllProductionBatesRanges']/following-sibling::td//a[text()='Edit']");}
	public Element getFieldName() {return driver.FindElementByXPath("//label[contains(text(),'Field Name')]/following-sibling::div//label//input");}
	public Element getFieldGroupName() {	return driver.FindElementByXPath("//label[contains(text(),'Field Group Name')]/following-sibling::div//select");}
	
	public Element getIsSearchable() {
		return driver.FindElementByXPath("//label[text()='Is Searchable :']/../..//label[@class='checkbox']/input[@value='true']");
	}
	//Added by Mohan
	public ElementCollection getProjectGridFieldNameValue(String textValue) {
		return driver.FindElementsByXPath(
				"//*[@id='ProjectFieldsDataTable']//tbody//td[contains(text(),'" + textValue + "')]");
	}

    //Annotation Layer added successfully
    public ProjectFieldsPage(Driver driver){

        this.driver = driver;
        this.driver.getWebDriver().get(Input.url+"ProjectFields/ProjectFieldsList");
        driver.waitForPageToBeReady();
        base = new BaseClass(driver);
    }
	
    
    /**<h1>Method for adding project field</h1>
     * <b>Description:</b> Method for adding project field in project felds page.
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @param fieldName : fieldName is a string value that need to enters in field name text field.
	 * @param presentationFieldName : presentationFieldName is String value that need to enter in presentation text field.
	 * @param fldClassification : fldClassification is a drop down value for selecting type of classification.
	 * @param fldDescription : fldDescription is a string value need to enter in field description text field.
	 * @param fieldType : fieldType is value need to select from field type drop down.
	 * @param fieldLength : fieldLength is value need to select from field length drop down.
	 * 
	 */
    public void addProjectField(String fieldName,String presentationFieldName,String fldClassification
    							,String fldDescription,String fieldType,String fieldLength) {
    	try{
    		base.stepInfo("Entered to Project fileds page and clicked on add project field button");
    		base.waitForElement(getAddProjectFieldButton());
    		getAddProjectFieldButton().Click();
    		
    		base.stepInfo("Creating new project field");
    		base.waitForElement(getFieldNameTextField());
    		getFieldNameTextField().SendKeys(fieldName);
    		getPresentationFieldLabelTextField().SendKeys(presentationFieldName);
    		getFieldClassificationDropDown().selectFromDropdown().selectByVisibleText(fldClassification);
    		getFieldDescriptionTextArea().SendKeys(fldDescription);
    		getFieldTypeDropDown().selectFromDropdown().selectByVisibleText(fieldType);
    		getFieldLengthDropDown().selectFromDropdown().selectByVisibleText(fieldLength);
    		getIsSearchableCheckBox().Click();
    		getIsEditableCheckBox().Click();
    		getSaveButton().Click();

    	}catch(Exception e) {
			base.failedStep("Exception occcured while adding project field"+e.getMessage());
			e.printStackTrace();
		}
    }
    
    
    /**<h1>Method for Apply Filter By Filter Name</h1>
     * <b>Description:</b> Method for applying filter by Filter Name.
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @param filterValue : filterValue is a string value that need to enters in filters by text field.
	 * 
	 */
	public void applyFilterByFilterName(String filterValue) {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getFiltersByTextField());
			getFiltersByTextField().SendKeys(filterValue);
			getApplyButton().Click();
		}catch(Exception e) {
			base.failedStep("Exception occcured while appling filter by filter name"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	
	/**<h1>Method for Applied Field Name Is Added In Project Fields Table</h1>
     * <b>Description:</b> Method for applying filed name is added in project fields table..
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @param fieldName : fieldName is a string value that need to verify weather present in table or not.
	 */
	public void verifyAppliedFieldNameIsAddedInProjectFieldsTable(String exfieldName) {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getSearchedFieldName());
			String fieldName = getSearchedFieldName().getText().trim();
			if(fieldName.contentEquals(exfieldName.trim())) {
				base.passedStep("Applied field name is added in Project fields table sucessfully");
			}else {
				base.failedStep("Applied field name is not added in Project fields table");
			}
		}catch(Exception e) {
			base.failedStep("Exception occcured while verifing added field in project table"+e.getMessage());
			e.printStackTrace();
		}
	}
	
	/**
	*@author Gopinath
	*@description : Method to verify Is Searchable Bates Range Is Selected.
	*/
	public void verifyingIsSearchableBatesRangeIsSelected() {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getAllProductionBatesRanges());
			base.waitTillElemetToBeClickable(getAllProductionBatesRanges());
			getAllProductionBatesRanges().Click();
			
			base.waitForElement(getFieldName());
			if(getFieldName().Enabled()) {base.failedStep("FieldName is enabled");}
			else {base.passedStep("Field name is disabled");
			
			
			base.waitForElement(getFieldGroupName());
			if(getFieldGroupName().Enabled()) {base.failedStep("FieldGroupName is enabled");}
			else {base.passedStep("Fieldgroupname is disabled");}
			
			
			base.waitForElement(getIsSearchableCheckBox());
			if(getIsSearchableCheckBox().Selected()) {
				base.failedStep("IsSeachable checkbox for all productionBatesRanges is selected");
			}else {
				base.passedStep("IsSearchable checkbox is not selected");}
			}
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verify is searchable bates range is selected."+e.getMessage());
			
		}
	}	
	
	/**
	*@author Gopinath
	*@description : Method to navigate project fields page.
	*/
	public void navigateToProjectFieldsPage() {
		try {
			driver.getWebDriver().get(Input.url+"ProjectFields/ProjectFieldsList");
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while navigating to projects fields page"+e.getMessage());
			
		}
	}	
	
	/**
	*@author Gopinath
	*@description : Method to inactive project field.
	*/
	public void inActiveProjectField(String projectFieldName) {
		try {
			driver.waitForPageToBeReady();
			getProjectFieldInactiveButton(projectFieldName).isElementAvailable(7);
			getProjectFieldInactiveButton(projectFieldName).waitAndClick(5);
			getConfirmOkButton().isElementAvailable(7);
			getConfirmOkButton().Click();
			getReindexAlertOkButton().isElementAvailable(7);
			getReindexAlertOkButton().Click();
			base.passedStep("Inactive project field -- "+projectFieldName+" successfully");
		}catch(Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while inactiving project field."+e.getMessage());
			
		}
	}
	
	
	/**
	 * @author Mohan Created Date: 10/02/2022 Modified Name: NA Modified by: NA
	 * @description: Used to validate Filter Filed By using Apply button and verify the search filter applied behaves like 'Contains'
	 * @param fieldByTextValue,textField
	 */
	public void validateFilterFieldsByContainsValueInTheGrid(ElementCollection fieldByTextValue, String textField) {

		driver.waitForPageToBeReady();
		List<String> availableListofElements = base.getAvailableListofElements(fieldByTextValue);
		System.out.println(availableListofElements);
		for(String avalValue : availableListofElements){
			if(!avalValue.contains(textField)){
				base.failedStep("The User is unable to enter the input in the textbox");
			}
		
		}
		base.passedStep("The user is able to enter the input in the textbox and apply filter button is working fine. The search filter applied behaves like 'Contains'. And the resulting fields from the filter is presented in the grid below");
			
		
		}

	
	/**
	 * @author Mohan Created Date: 10/02/2022 Modified Name: NA Modified by: NA
	 * @description: Used to enter the value in the filter field by textbox and click enter button.
	 * @param fieldByTextValue
	 */
	public void enterFilterFieldValueAndClickEnter(String fieldByTextValue) {
		
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getFiltersByTextField());
			getFiltersByTextField().Clear();
			getFiltersByTextField().SendKeys(fieldByTextValue);
			getFiltersByTextField().Enter();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("The Filter Filed by is unable to hit the Enter button");
		}

	}
	
	public void enableIsSearchableBatesRangeIsSelected() throws Exception {
		String value = getIsSearchable().GetAttribute("checked");
		System.out.println("value :" + value);
		//String expected = "true";
		driver.waitForPageToBeReady();
		if (value != null) {
			base.passedStep("isSearchable check box is checked");
			System.out.println("element is Checked");
		}
		else if(value == null) {
			getIsSearchableCheckBox().waitAndClick(5);
			getConfirmOkButton().waitAndClick(5);
			getSaveButton().waitAndClick(5);
			base.passedStep("isSearchable check box not Checked");
			System.out.println("isSearchable check box not Checked");
		}
	}
	public void disableIsSearchableBatesRangeIsSelected() {
		String value = getIsSearchable().GetAttribute("checked");
		System.out.println("value :" + value);
		driver.waitForPageToBeReady();
		if (value!=null) {
			getIsSearchableCheckBox().waitAndClick(5);
			getConfirmOkButton().waitAndClick(5);
			getSaveButton().waitAndClick(5);
			base.passedStep("isSearchable check box is checked");
			System.out.println("element is Checked");
		} else if(value == null){
			base.passedStep("isSearchable check box not Checked");
			System.out.println("isSearchable check box not Checked");
		}
	}

	
}
