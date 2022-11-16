package pageFactory;

import java.util.List;

import org.openqa.selenium.WebElement;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import testScriptsSmoke.Input;

public class ProjectFieldsPage {

	Driver driver;
	BaseClass base;

	public Element getAddProjectFieldGroup() {
		return driver.FindElementByXPath("//a[@id='btnAddProjectFieldGroup']");
	}
	public Element getGroupCancelBtn() {
		return driver.FindElementById("btnProjectFieldGroupCancel");
	}
	
	public Element getAddFieldGroupName() {
		return driver.FindElementByXPath("//*[@id='FieldGroupLabel']");
	}
	
	public Element getFieldGroupNameDp_Dwn() {
		return driver.FindElementById("ddlProjectFieldGroups");
	}
	public ElementCollection getFieldGroupNameDp_DwnValue() {
		return driver.FindElementsByXPath("//select[@id='ddlProjectFieldGroups']//option");
	}
	public Element getAddProjectFieldButton() {
		return driver.FindElementByXPath("//a[@id='btnAddProjectField']");
	}

	public Element getFieldNameTextField() {
		return driver.FindElementById("FieldName");
	}

	public Element getPresentationFieldLabelTextField() {
		return driver.FindElementById("FieldLabel");
	}

	public Element getFieldGroupNameDropDown() {
		return driver.FindElementById("ddlProjectFieldGroups");
	}

	public Element getFieldClassificationDropDown() {
		return driver.FindElementById("ddlFieldClassification");
	}

	public Element getFieldDescriptionTextArea() {
		return driver.FindElementById("FieldDesc");
	}

	public Element getFieldTypeDropDown() {
		return driver.FindElementById("ddlProjectFieldGroupCode");
	}

	public Element getIsSearchableCheckBox() {
		return driver.FindElementByXPath("//label[text()='Is Searchable :']/../..//i");
	}

	public Element getIsEditableCheckBox() {
		return driver.FindElementByXPath("//label[text()='Is Editable :']/../..//input[@id='IsEditable']/..//i");
	}

	public Element getSaveButton() {
		return driver.FindElementById("btnProjectFieldAddSave");
	}

	public Element getFiltersByTextField() {
		return driver.FindElementById("txtsearchField");
	}

	public Element getApplyButton() {
		return driver.FindElementById("btnAppyFilter");
	}

	public Element getSearchedFieldName() {
		return driver.FindElementByXPath("//table[@id='ProjectFieldsDataTable']//tbody/tr[1]/td[1]");
	}

	public Element getFieldLengthDropDown() {
		return driver.FindElementById("ddlDataTypeColumnLengthCode");
	}

	public Element getProjectFieldInactiveButton(String projectFieldName) {
		return driver.FindElementByXPath("//td[text()='" + projectFieldName + "']//..//a[text()='Inactive']");
	}

	public Element getConfirmOkButton() {
		return driver.FindElementById("bot1-Msg1");
	}

	public Element getReindexAlertOkButton() {
		return driver.FindElementById("bot1-Msg2");
	}

	public Element getAllProductionBatesRanges() {
		return driver.FindElementByXPath(
				"//tr//td[text()='AllProductionBatesRanges']/following-sibling::td//a[text()='Edit']");
	}

	public Element getFieldName() {
		return driver.FindElementByXPath("//label[contains(text(),'Field Name')]/following-sibling::div//label//input");
	}

	public Element getFieldGroupName() {
		return driver.FindElementByXPath("//label[contains(text(),'Field Group Name')]/following-sibling::div//select");
	}

	public Element getIsSearchable() {
		return driver.FindElementByXPath(
				"//label[text()='Is Searchable :']/../..//label[@class='checkbox']/input[@value='true']");
	}

	// Added by Mohan
	public ElementCollection getProjectGridFieldNameValue(String textValue) {
		return driver.FindElementsByXPath(
				"//*[@id='ProjectFieldsDataTable']//tbody//td[contains(text(),'" + textValue + "')]");
	}

	public ElementCollection getProjectFieldTotTableCount() {
		return driver.FindElementsByXPath("//*[@id='ProjectFieldsDataTable']//tbody/tr");
	}

	// Added by Gopinath - 25/03/2022
	public Element getQuaryReturnedNoDataMessage() {
		return driver.FindElementByXPath("//*[@id='ProjectFieldsDataTable']/tbody/tr/td[@class='dataTables_empty']");
	}

	public ElementCollection getFieldNames() {
		return driver.FindElementsByXPath("//*[@id='ProjectFieldsDataTable']/tbody/tr/td[1]");
	}

	public Element filterNameTextFieldLabel() {
		return driver.FindElementByXPath("//div[@class='col-md-2 pd-lab']/div/strong");
	}

	// Added by Gopinath - 25/03/2022
	public Element getFieldTableCurrentPageNumber() {
		return driver.FindElementByXPath("//li[@class='paginate_button active']/a");
	}

	public Element getFieldNameEdititButton(String fieldName) {
		return driver.FindElementByXPath("//table[@id='ProjectFieldsDataTable']/tbody/tr/td[text()='" + fieldName
				+ "']/following-sibling::td/a[contains(text(),'Edit')]");
	}

	public Element getFieldTableNextButton() {
		return driver.FindElementByXPath("//li[@id='ProjectFieldsDataTable_next']/a");
	}

	public Element getFieldTableNextButtonDisabled() {
		return driver.FindElementByXPath("//li[@class='paginate_button next disabled']/a");
	}
	public ElementCollection getProjectFieldHeader() {
		return driver.FindElementsByXPath("//table[@id='ProjectFieldsDataTable']//th");
	}
	public Element getPagination() {
		return driver.FindElementByXPath("//*[@id='ProjectFieldsDataTable_paginate']");
	}
	
	//added by sowndarya
	public Element getIsSearcHelpBtn() {
		return driver.FindElementByXPath("//label[text()='Is Searchable :']//following::a");
	}
	
	public Element getIsSearcHelpTxt() {
		return driver.FindElementByXPath("//label[text()='Is Searchable :']//following::a//following::div[@class='popover-content']");
	}
	
	//added by Arun
	public Element getFieldStatus(String field,int column) {
		return driver.FindElementByXPath("//td[@title='"+field+"']//following-sibling::td["+column+"]");
	}
	public Element getFieldCancelButton() {
		return driver.FindElementById("btnProjectFieldAddCancel");
	}
	public Element getProjectFieldactiveButton(String projectFieldName) {
		return driver.FindElementByXPath("//td[text()='" + projectFieldName + "']//..//a[text()='Active']");
	}

	//
	// Annotation Layer added successfully
	public ProjectFieldsPage(Driver driver) {

		this.driver = driver;
//		this.driver.getWebDriver().get(Input.url + "ProjectFields/ProjectFieldsList");
		driver.waitForPageToBeReady();
		base = new BaseClass(driver);
	}

	/**
	 * <h1>Method for adding project field</h1> <b>Description:</b> Method for
	 * adding project field in project felds page.
	 * 
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @param fieldName             : fieldName is a string value that need to
	 *                              enters in field name text field.
	 * @param presentationFieldName : presentationFieldName is String value that
	 *                              need to enter in presentation text field.
	 * @param fldClassification     : fldClassification is a drop down value for
	 *                              selecting type of classification.
	 * @param fldDescription        : fldDescription is a string value need to enter
	 *                              in field description text field.
	 * @param fieldType             : fieldType is value need to select from field
	 *                              type drop down.
	 * @param fieldLength           : fieldLength is value need to select from field
	 *                              length drop down.
	 * 
	 */
	public void addProjectField(String fieldName, String presentationFieldName, String fldClassification,
			String fldDescription, String fieldType, String fieldLength) {
		try {
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

		} catch (Exception e) {
			base.failedStep("Exception occcured while adding project field" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * <h1>Method for Apply Filter By Filter Name</h1> <b>Description:</b> Method
	 * for applying filter by Filter Name.
	 * 
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @param filterValue : filterValue is a string value that need to enters in
	 *                    filters by text field.
	 * 
	 */
	public void applyFilterByFilterName(String filterValue) {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getFiltersByTextField());
			getFiltersByTextField().SendKeys(filterValue);
			getApplyButton().Click();
		} catch (Exception e) {
			base.failedStep("Exception occcured while appling filter by filter name" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * <h1>Method for Applied Field Name Is Added In Project Fields Table</h1>
	 * <b>Description:</b> Method for applying filed name is added in project fields
	 * table..
	 * 
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @param fieldName : fieldName is a string value that need to verify weather
	 *                  present in table or not.
	 */
	public void verifyAppliedFieldNameIsAddedInProjectFieldsTable(String exfieldName) {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getSearchedFieldName());
			String fieldName = getSearchedFieldName().getText().trim();
			if (fieldName.contentEquals(exfieldName.trim())) {
				base.passedStep("Applied field name is added in Project fields table sucessfully");
			} else {
				base.failedStep("Applied field name is not added in Project fields table");
			}
		} catch (Exception e) {
			base.failedStep("Exception occcured while verifing added field in project table" + e.getMessage());
			e.printStackTrace();
		}
	}

	/**
	 * @author Gopinath
	 * @description : Method to verify Is Searchable Bates Range Is Selected.
	 */
	public void verifyingIsSearchableBatesRangeIsSelected() {
		try {
			driver.waitForPageToBeReady();
			base.waitForElement(getAllProductionBatesRanges());
			base.waitTillElemetToBeClickable(getAllProductionBatesRanges());
			getAllProductionBatesRanges().Click();

			base.waitForElement(getFieldName());
			if (getFieldName().Enabled()) {
				base.failedStep("FieldName is enabled");
			} else {
				base.passedStep("Field name is disabled");

				base.waitForElement(getFieldGroupName());
				if (getFieldGroupName().Enabled()) {
					base.failedStep("FieldGroupName is enabled");
				} else {
					base.passedStep("Fieldgroupname is disabled");
				}

				base.waitForElement(getIsSearchableCheckBox());
				if (getIsSearchableCheckBox().Selected()) {
					base.failedStep("IsSeachable checkbox for all productionBatesRanges is selected");
				} else {
					base.passedStep("IsSearchable checkbox is not selected");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while verify is searchable bates range is selected." + e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @description : Method to navigate project fields page.
	 */
	public void navigateToProjectFieldsPage() {
		try {
			driver.getWebDriver().get(Input.url + "ProjectFields/ProjectFieldsList");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while navigating to projects fields page" + e.getMessage());

		}
	}

	/**
	 * @author Gopinath
	 * @description : Method to inactive project field.
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
			base.passedStep("Inactive project field -- " + projectFieldName + " successfully");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while inactiving project field." + e.getMessage());

		}
	}

	/**
	 * @author Mohan Created Date: 10/02/2022 Modified Name: NA Modified by: NA
	 * @description: Used to validate Filter Filed By using Apply button and verify
	 *               the search filter applied behaves like 'Contains'
	 * @param fieldByTextValue,textField
	 */
	public void validateFilterFieldsByContainsValueInTheGrid(ElementCollection fieldByTextValue, String textField) {

		driver.waitForPageToBeReady();
		List<String> availableListofElements = base.getAvailableListofElements(fieldByTextValue);
		System.out.println(availableListofElements);
		for (String avalValue : availableListofElements) {
			if (!avalValue.contains(textField)) {
				base.failedStep("The User is unable to enter the input in the textbox");
			}

		}
		base.passedStep(
				"The user is able to enter the input in the textbox and apply filter button is working fine. The search filter applied behaves like 'Contains'. And the resulting fields from the filter is presented in the grid below");

	}

	/**
	 * @author Mohan Created Date: 10/02/2022 Modified Name: NA Modified by: NA
	 * @description: Used to enter the value in the filter field by textbox and
	 *               click enter button.
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
		// String expected = "true";
		driver.waitForPageToBeReady();
		if (value != null) {
			base.passedStep("isSearchable check box is checked");
			System.out.println("element is Checked");
		} else if (value == null) {
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
		if (value != null) {
			getIsSearchableCheckBox().waitAndClick(5);
			getConfirmOkButton().waitAndClick(5);
			getSaveButton().waitAndClick(5);
			base.passedStep("isSearchable check box is checked");
			System.out.println("element is Checked");
		} else if (value == null) {
			base.passedStep("isSearchable check box not Checked");
			System.out.println("isSearchable check box not Checked");
		}
	}

	/**
	 * @author Gopinath
	 * @Description:Used to validate Filter Filed By using Apply button and verify
	 *                   the search filter applied behaves like 'Contains'
	 * @param fieldName(Name of the applied filter)
	 */
	public void validateFilterFieldsByContainsFieldName(String fieldName) {
		try {
			driver.waitForPageToBeReady();
		base.waitForElement(getFiltersByTextField());
		getFiltersByTextField().SendKeys(fieldName);
		getApplyButton().Click();
	}catch (Exception e) {
		base.failedStep("Exception occcured while appling filter by filter name" + e.getMessage());
		e.printStackTrace();
	}
		
		base.waitForElementCollection(getFieldNames());
		for (WebElement fieldNamesAfterFilter : getFieldNames().FindWebElements()) {
			if (!fieldNamesAfterFilter.getText().contains(fieldName)) {
				base.failedStep("filter applied failed as field Name " + fieldNamesAfterFilter
						+ " is displayed which is not contain filter value");
			}
		}
		base.passedStep(
				"The user is able to enter the input in the textbox and apply filter button is working fine. The search filter applied behaves like 'Contains'. And the resulting fields from the filter is presented in the grid below");
	}

	/**
	 * @author Gopinath
	 * @Description: method to verify Query return no data message
	 */
	public void verifyfilterReturnNoData() {
		base.waitForElement(getQuaryReturnedNoDataMessage());
		if (getQuaryReturnedNoDataMessage().getWebElement().isDisplayed()
				&& getQuaryReturnedNoDataMessage().getText().contains("Your query returned no data")) {
			base.passedStep("'Your Query returned no data' message is displayed as expected ");
		} else {
			base.failedStep("'Your Query returned no data' message is not displayed ");
		}
	}

	/**
	 * @author Gopinath
	 * @Description:Method to verify field name text and label localized to german
	 */
	public void verifyfilterLabelApplyButtonLocaliseToGerman() {
		base.waitForElement(filterNameTextFieldLabel());
		if (filterNameTextFieldLabel().getText().trim().equals("DE-Filter Fields By")) {
			base.waitForElement(getFiltersByTextField());
			if (getFiltersByTextField().GetAttribute("placeholder").trim().equals("DE-Field Name")) {
				base.passedStep("filter name text and label localised to German");
			} else {
				base.failedStep("filter name text field text is no localised to german");
			}
		} else {
			base.failedStep("field name text field label is not localised to German");
		}
		base.waitForElement(getApplyButton());
		if (getApplyButton().getText().trim().equals("DE-Apply")) {
			base.passedStep("Apply button text localised and displayed in german");
		} else {
			base.failedStep("Apply button is not displayed German");
		}
	}

	/**
	 * @author Gopinath
	 * @Description: method to verify Query return no data message after localised
	 *               to German
	 */
	public void verifyfilterReturnNoDataGerman() {
		base.waitForElement(getQuaryReturnedNoDataMessage());
		if (getQuaryReturnedNoDataMessage().getWebElement().isDisplayed()
				&& getQuaryReturnedNoDataMessage().getText().contains("Keine Daten vorhanden in der Tabelle")) {
			base.passedStep(
					"'Your Query returned no data' message is displayed in german after localised as expected ");
		} else {
			base.failedStep("'Your Query returned no data' message is not displayed in German ");
		}
	}

	/**
	 * @author Gopinath
	 * @Description: Method to clear filter
	 */
	public void clearFilter() {
		try {
			base.waitForElement(getFieldNameTextField());
			getFiltersByTextField().Clear();
			getFiltersByTextField().Enter();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while clear the filter");
		}

	}

	/**
	 * @author Gopinath
	 * @Description: Method to verify all field name are displayed after clear
	 *               filter
	 * @param fieldNamesBeforeFilter(List of field names before apply filter)
	 */
	public void verifyAllFieldNamesDisplaydAfterClearFilter(List<String> fieldNamesBeforeFilter) {
		try {
			base.waitForElementCollection(getFieldNames());
			List<String> fieldNamesAfterClear = base.getAvailableListofElements(getFieldNames());
			for (String fieldNameBefore : fieldNamesBeforeFilter) {
				boolean flag = false;
				for (int i = 0; i < fieldNamesAfterClear.size(); i++) {
					if (fieldNameBefore.equals(fieldNamesAfterClear.get(i))) {
						flag = true;
						break;
					}
				}
				if (flag == false) {
					base.failedStep("field Name " + fieldNameBefore + " is not displayed after clear filter");
				}
			}
			base.passedStep("All field names are displayed after clear filter");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while verifying field names due to " + e.getMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @DEscription: method to navigate to field available page number
	 * @param fieldName(name of the filed)
	 */
	public void navigateTofieldPageInProjectFieldTable(String fieldName) {
		try {
			boolean fieldfound = false;
			while (true) {
				String currentPageNumber = getFieldTableCurrentPageNumber().getText().trim();
				if (getProjectGridFieldNameValue(fieldName).isElementAvailable(3)) {
					base.passedStep("field name '" + fieldName + "' is found in  page number " + currentPageNumber);
					fieldfound = true;// break loop if field name found
					break;
				} else if (!fieldfound) {
					driver.scrollingToBottomofAPage();
					if (getFieldTableNextButtonDisabled().isElementAvailable(3)) {
						break;// break loop if it is last page
					} else {
						getFieldTableNextButton().waitAndClick(10);
					}
				}
			}
			if (!fieldfound) {
				base.failedMessage("field name '" + fieldName + "' is not found in project field table");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while navigating to field name");
		}
	}

	/**
	 * @author Gopinath
	 * @description: method to verify filter field by text filed and apply buttons
	 *               are displayed
	 */
	public void verifyFilterFieldByNameAndApplyButton() {
		try {
			base.waitForElement(filterNameTextFieldLabel());
			if (filterNameTextFieldLabel().isElementAvailable(5)) {
				base.waitForElement(getFiltersByTextField());
				if (getFiltersByTextField().getWebElement().isDisplayed()
						&& getFiltersByTextField().GetAttribute("placeholder").equals("Field Name")) {
					base.passedStep("filter field by is displayed with filter text box and text 'filter name'");
				} else {
					base.failedStep("filter text box eith text filter name is not displayed");
				}
			} else {
				base.failedStep("filter field By label is not displayed");
			}
			base.waitForElement(getApplyButton());
			if (getApplyButton().getWebElement().isDisplayed()) {
				base.passedStep("filter apply button is displayed");
			} else {
				base.failedStep("filter apply button is not displayed");
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while verify filter text box and apply button");
		}
	}

	/**
	 * @author Gopinath
	 * @Description: method to edit field description
	 * @param fieldName   : fieldName is String value that name of field.
	 * @param description : description is String value that description need to
	 *                    enter in edit box.
	 */
	public void editprojectFieldFieldDescription(String fieldName, String description) {
		try {
			base.waitForElement(getFieldNameEdititButton(fieldName));
			getFieldNameEdititButton(fieldName).waitAndClick(10);
			base.waitForElement(getFieldDescriptionTextArea());
			getFieldDescriptionTextArea().SendKeys(description);
			base.waitForElement(getSaveButton());
			getSaveButton().waitAndClick(10);
			base.passedStep(fieldName + " field description edited successfully");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while editing field description due to " + e.getMessage());
		}

	}

	/**
	 * @author Gopinath
	 * @Description method to verify application retained on same page number and
	 *              filter text not cleared in filter field by text field after edit
	 *              the filed
	 * @param fieldName(field name to edit description)
	 * @param description
	 */
	public void verifyPageNumberRetainedAfterEditField(String fieldName, String description) {
		base.waitForElement(getFiltersByTextField());
		String filterValueInTextField = getFiltersByTextField().GetAttribute("value");
		base.waitForElement(getFieldTableCurrentPageNumber());
		String PageNumberBeforeEdit = getFieldTableCurrentPageNumber().getText();
		editprojectFieldFieldDescription(fieldName, description);
		base.waitForElement(getFiltersByTextField());
		String filterValueInTextFieldAFter = getFiltersByTextField().GetAttribute("value");
		base.waitForElement(getFieldTableCurrentPageNumber());
		String PageNumberAfterEdit = getFieldTableCurrentPageNumber().getText();
		if (filterValueInTextField.equals(filterValueInTextFieldAFter)) {
			if (PageNumberBeforeEdit.equals(PageNumberAfterEdit)) {
				base.passedStep(
						"After edit the field Application is on same page number and filter filed by text is not cleared");
			} else {
				base.failedStep("After edit field application is not retained on same page number");
			}
		} else {
			base.passedStep("After edit project field, filter field by text is cleared from text area");
		}
	}

	/**
	 * @author Mohan.Venugopal
	 * @Description To verify Custom fields are available in the project list.
	 */
	public void verifyCustomFieldsInProjectFieldsList() {

		driver.waitForPageToBeReady();
		ElementCollection totTableCount = getProjectFieldTotTableCount();
		int size = totTableCount.size();
		System.out.println(size);

		if (size > 2) {
			base.passedStep("The Custom fields are exists in newly created Project");

		} else {
			base.failedStep("There are no Custom fields in the newly created project");
		}

	}
	
	/**
	 * @author: Arun Created Date: 22/08/2022 Modified by: NA Modified Date: NA
	 * @description: this method will check values of the field is searchable and tallyable
	 */
	public void verifyFieldStatus(String field) {
			driver.waitForPageToBeReady();
			base.waitForElement(getFieldStatus(field,2));
			String tallyStatus =getFieldStatus(field,2).getText().trim();
			String searchStatus =getFieldStatus(field,3).getText().trim();
			String activeStatus =getFieldStatus(field,4).getText().trim();
			if(searchStatus.equalsIgnoreCase("True") && tallyStatus.equalsIgnoreCase("True")
					&& activeStatus.equalsIgnoreCase("Active")) {
				base.passedStep("Field is Tallyable,Searchable and Active by default");
			}
			else {
				base.failedStep("Field is not Tallyable,Searchable and active by default");
			}
	}
	/**
	 * @author: Arun Created Date: 22/08/2022 Modified by: NA Modified Date: NA
	 * @description: this method will check value of field classification for the field
	 */
	public void verifyFieldClassification(String field,String expected) {
		base.waitForElement(getFieldNameEdititButton(field));
		getFieldNameEdititButton(field).waitAndClick(5);
		base.waitForElement(getFieldClassificationDropDown());
		String value = getFieldClassificationDropDown().selectFromDropdown().getFirstSelectedOption().getText();
		if(value.equalsIgnoreCase(expected)){
			base.passedStep("Field classification for the '"+ field +"' is '"+value+"'");
		}
		else {
			base.failedStep("default field classification type is not expected");
		}
		base.waitForElement(getFieldCancelButton());
		getFieldCancelButton().waitAndClick(3);
		
	}
	
	/**
	 * @author: Arun Created Date: 17/10/2022 Modified by: NA Modified Date: NA
	 * @description: this method will check values of the field is searchable and tallyable
	 */
	public void verifyExpectedFieldStatus(String field,String tally,String search,String status) {
			driver.waitForPageToBeReady();
			base.waitForElement(getFieldStatus(field,2));
			String tallyStatus =getFieldStatus(field,2).getText().trim();
			base.stepInfo(field+"field is tallyable status: "+ tallyStatus);
			String searchStatus =getFieldStatus(field,3).getText().trim();
			base.stepInfo(field+"field is searchable status:"+ searchStatus);
			String activeStatus =getFieldStatus(field,4).getText().trim();
			base.stepInfo(field+"field active status"+ activeStatus);
			if(searchStatus.equalsIgnoreCase(search) && tallyStatus.equalsIgnoreCase(tally)
					&& activeStatus.equalsIgnoreCase(status)) {
				base.passedStep("Field Tallyable,Searchable and Active status displayed as expected");
			}
			else {
				base.failedStep("Field status not displayed as expected");
			}
	}
}
