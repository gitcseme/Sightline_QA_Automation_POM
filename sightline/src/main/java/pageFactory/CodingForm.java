package pageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import testScriptsSmoke.Input;

public class CodingForm {

	Driver driver;
	BaseClass base;
	SoftAssert softAssertion;
	ReusableDocViewPage reusableDocView;
	final DocViewPage doc;
	AssignmentsPage assgnpage;

	public Element getAddNewCodingFormBtn() {
		return driver.FindElementByXPath("//*[@id='content']//button[@class='btn btn-primary new-coding-form']");
	}

	public Element getCodingFormName() {
		return driver.FindElementById("txtFormName");
	}

	public Element getSaveCFBtn() {
		return driver.FindElementByXPath("//*[@id='tab-create']//a[text()='Save']");
	}

	public Element getCodingForm_Validation_ButtonYes() {
		return driver.FindElementById("btnYes");
	}

	public Element getCodingForm_FirstTag() {
		return driver.FindElementByXPath("//*[@id='lstTags']/li[1]/label/i");
	}

	public Element getCodingForm_AddToFormButton() {
		return driver.FindElementById("addFormObjects");
	}

	public Element getCodingForm_CommentTab() {
		return driver.FindElementByXPath("//*[@id='internal-tab-1']//span[text()='COMMENTS']");
	}

	public Element getCodingForm_FirstComment() {
		return driver.FindElementByXPath("//*[@id='lstComments']/li[1]/label/i");
	}

	public Element getCodingForm_EDITABLE_METADATA_Tab() {
		return driver.FindElementByXPath("//*[@id='internal-tab-1']//span[text()='EDITABLE METADATA']");
	}

	public Element getCodingForm_SubjectMetadata() {
		return driver.FindElementByXPath("//*[@id='lstMetadata']//strong[text()='Subject']/../i");
	}

	public Element getCodingForm_RemoveLink() {
		return driver.FindElementByXPath("//*[a[text()='Remove']]//a");
	}

	public Element getCodingForm_ObjectName() {
		return driver.FindElementByXPath("//*[starts-with(@id, 'FriendlyInput_')]");
	}

	public Element getCodingForm_FirstMetadata() {
		return driver.FindElementByXPath("//*[@id='lstMetadata']/li[1]/label/i");
	}

	public Element getCodingForm_FirstRemoveLink() {
		return driver.FindElementByXPath("//*[a[text()='Remove']]//a[@id='0']");
	}

	public Element getCodingForm_SearchBox() {
		return driver.FindElementByXPath("//*[@id='CodingFormDataTable_filter']/label/input");
	}

	public Element getCodingForm_SearchButton() {
		return driver.FindElementByXPath("/*[@id='CodingFormDataTable_filter']/label/span/i");
	}

	public Element getCodingForm_Objects() {
		return driver.FindElementByXPath("//*[@class='itemFriendlyName']");
	}

	public Element getCF_SecondTag() {
		return driver.FindElementByXPath("//ul[@id='lstTags']/li[2]/label/i");
	}

	public Element getCF_All() {
		return driver.FindElementByXPath("//ul[contains(@id,'lstTags')]");
	}

	public Element getCF_PreviewButton() {
		return driver.FindElementByXPath("//a[contains(text(),'Preview')]");
	}

	public Element getCF_Preview_Ok() {
		return driver.FindElementByXPath("//button[@id='btnYes']");
	}

	public Element getCF_AddLogicRule() {
		return driver.FindElementByXPath("//button[@id='0']");
	}

	public Element getCF_Object1() {
		return driver.FindElementByXPath("//*[@id='SelectObjects_0_1']");
	}

	public Element getCF_FieldLogicCondition() {
		return driver.FindElementByXPath("//*[@id='SelectCondition_0_1']");
	}

	public Element getCF_FieldLogicAction() {
		return driver.FindElementByXPath("//select[@id='LogicFieldAction_0']");
	}

	public Element getCF_ObjectClose() {
		return driver.FindElementByXPath("//i[@id='1']");
	}

	public Element getCF_PreviewTagObject1() {
		return driver.FindElementByXPath("//*[@id='item0']/table/tbody/tr/td[1]/label/span");
	}

	public Element getCF_PreviewTagObject2() {
		return driver.FindElementByXPath("//*[@id='item1']/table/tbody/tr/td[1]/label/span");
	}

	public Element getSuccessMsgHeader() {
		return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span");
	}

	public Element getSuccessMsg() {
		return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p");
	}

	public Element getCodingForm_NumberToShow() {
		return driver.FindElementByCssSelector("*[name='CodingFormDataTable_length']");
	}

	public Element getCodingForm_SelectSecurityGroup(String cfname) {
		return driver
				.FindElementByXPath(".//*[@id='CodingFormDataTable']/tbody/tr[contains(.,'" + cfname + "')]/td[5]//i");
	}

	public Element getCodingForm_SGValidation_ButtonYes() {
		return driver.FindElementById("bot1-Msg1");
	}

	public Element getCF_firstObjecttab() {
		return driver.FindElementByXPath("//*[@href='#c-0']");
	}

	// addition by shilpi on 2/7
	public Element getCodingForm_EditButton(String CFName) {
		return driver.FindElementByXPath(
				".//*[@id='CodingFormDataTable']//td[text()='" + CFName + "']/../td//a[text()='Edit']");
	}

	public Element getCodingForm_CopyButton(String CFName) {
		return driver.FindElementByXPath(
				".//*[@id='CodingFormDataTable']//td[text()='" + CFName + "']/../td//a[text()='Copy']");
	}

	public Element getCodingForm_DeleteButton(String CFName) {
		return driver.FindElementByXPath(
				".//*[@id='CodingFormDataTable']//td[text()='" + CFName + "']/../td//a[text()='Delete']");
	}

	public Element getManageCodingFormButton() {
		return driver.FindElementByXPath("//a[contains(text(),'Manage Coding Forms')]");
	}

	// select coding's radio button from table
	public Element getCFlistTable() {
		return driver.FindElementByXPath("//*[@id='CodingFormDataTable']");
	}

	public ElementCollection getCFnames() {
		return driver.FindElementsByXPath("//*[@id='CodingFormDataTable']/tbody/tr/td[1]");
	}

	public Element getCFRadioBtn(int row) {
		return driver.FindElementByXPath("//*[@id='CodingFormDataTable']/tbody/tr[" + row + "]/td[5]/span/label/i");
	}

	// added on 25/03
	public Element getCodingForm_Comment(String commentname) {
		return driver.FindElementByXPath("//*[@id='lstComments']//li/label[@title='" + commentname + "']/i");
	}

	public Element getCodingForm_Tag(String Tagname) {
		return driver.FindElementByXPath("//*[@id='lstTags']/li/label[@title='" + Tagname + "']/i");
	}

	public Element getCF_Comment(String commentname) {
		return driver.FindElementByXPath(".//*[@id='collapseTwo']//span[contains(text(),'" + commentname + "')]");
	}

	public Element getCF_Metadata(String metadata) {
		return driver.FindElementByXPath(".//*[@id='lstMetadata']//strong[contains(text(),'" + metadata + "')]");
	}

	public Element getCodingForm_ErrorMsg() {
		return driver.FindElementById("ErrorMessage_0");
	}

	public Element getCodingForm_ErrorMsg1() {
		return driver.FindElementById("ErrorMessage_1");
	}

	public Element getCodingForm_ErrorMsg2() {
		return driver.FindElementById("ErrorMessage_2");
	}

	public Element getCodingForm_HelpText() {
		return driver.FindElementById("HelpText_0");
	}

	public Element getCF_StaticTextObject() {
		return driver.FindElementByXPath("//*[@data-type='STATICTEXT']/following-sibling::i");
	}

	public Element getCF_RadioGrpObject() {
		return driver.FindElementByXPath("//*[@data-type='RADIOGROUP']/following-sibling::i");
	}

	public Element getCF_CheckGrpObject() {
		return driver.FindElementByXPath("//*[@data-type='CHECKGROUP']/following-sibling::i");
	}

	public Element getCF_TagType() {
		return driver.FindElementById("3");
	}

	public Element getCF_TagGrpAssociation() {
		return driver.FindElementById("TagChk_3");
	}

	public Element getCF_AddLogicButton() {
		return driver.FindElementByXPath("//*[@class='add-logic btn btn-labeled btn-primary bg-color-blueLight']");
	}

	public Element getCF_ValidationAlert() {
		return driver.FindElementById("ui-id-1");
	}

	public Element getCodingForm(String CFName) {
		return driver.FindElementByXPath(".//*[@id='CodingFormDataTable']//td[text()='" + CFName + "']");
	}

	public Element getCF_DeletePopup() {
		return driver.FindElementByXPath(".//*[@class='pText']");
	}

	// aaded on 06/06
	public Element getCodingForm_MultipleTags(int i) {
		return driver.FindElementByXPath("//*[@id='lstTags']/li[" + i + "]/label/i");
	}

	public Element getCodingForm_DefaultAction(int i) {
		return driver.FindElementById("DefaultAction_" + i + "");
	}

	public Element getCodingForm_TagNames(int i) {
		return driver.FindElementByXPath("(//span[@class='itemFriendlyName'])[" + i + "]");
	}

	public Element getCodingForm_MandField() {
		return driver.FindElementByXPath("//span[@class='field-validation-error text-danger']");
	}

	public Element getCodingForm_MetadataField() {
		return driver.FindElementByXPath(".//*[@id='lstMetadata']//label");
	}

	public Element getCF_Metadata() {
		return driver.FindElementById("lstMetadata");
	}

	public Element getCF_TagTypes() {
		return driver.FindElementByXPath(".//*[@id='c-0']//select[@id='0']");
	}

	public Element getCF_Preview1() {
		return driver.FindElementByXPath(".//*[starts-with(@id,'item0')]//td[1]");
	}

	public Element getCF_RadioGroup() {
		return driver.FindElementById("TagRdo_0");
	}

	public Element getCF_RadioGroup1() {
		return driver.FindElementById("//*[@id='c-2']//select[@id='2']");
	}

	// added- System Level Template - Narendra
	public ElementCollection getTable() {
		return driver.FindElementsByXPath("//*[@id='CodingFormDataTable']/tbody/tr/td[1]");
	}

	public Element getEditClick() {
		return driver.FindElementByXPath("//a[@class='btn btn-primary btn-xs edit-coding-form']");
	}

	public ElementCollection getnesTable() {
		return driver.FindElementsByXPath("//div[@id='nestable']//ol//li");
	}

	public Element getRootClick() {
		return driver.FindElementByXPath("//li[@id='0']");
	}

	public Element getupClick1() {
		return driver.FindElementByXPath("//li[@id='0']//i[@class='fa fa-lg fa-angle-up pull-right']");
	}

	public Element getupClick2() {
		return driver.FindElementByXPath("//li[@id='1']//i[@class='fa fa-lg fa-angle-up pull-right']");
	}

	public Element getupClick3() {
		return driver.FindElementByXPath("//li[@id='2']//i[@class='fa fa-lg fa-angle-up pull-right']");
	}

	public Element getupClick4() {
		return driver.FindElementByXPath("//li[@id='3']//i[@class='fa fa-lg fa-angle-up pull-right']");
	}

	public Element getupClick5() {
		return driver.FindElementByXPath("//li[@id='4']//i[@class='fa fa-lg fa-angle-up pull-right']");
	}

	public Element getupClick6() {
		return driver.FindElementByXPath("//li[@id='10']//i[@class='fa fa-lg fa-angle-up pull-right']");
	}

	public Element getupClick7() {
		return driver.FindElementByXPath("//li[@id='6']//i[@class='fa fa-lg fa-angle-up pull-right']");
	}

	public Element getupClick8() {
		return driver.FindElementByXPath("//li[@id='7']//i[@class='fa fa-lg fa-angle-up pull-right']");
	}

	public Element getupClick9() {
		return driver.FindElementByXPath("//li[@id='8']//i[@class='fa fa-lg fa-angle-up pull-right']");
	}

	public Element getupClick10() {
		return driver.FindElementByXPath("//li[@id='9']//i[@class='fa fa-lg fa-angle-up pull-right']");
	}

	public Element getupClick11() {
		return driver.FindElementByXPath("//li[@id='11']//i[@class='fa fa-lg fa-angle-up pull-right']");
	}

	public Element getupClick12() {
		return driver.FindElementByXPath("//li[@id='12']//i[@class='fa fa-lg fa-angle-up pull-right']");
	}

	public Element getupClick13() {
		return driver.FindElementByXPath("//li[@id='13']//i[@class='fa fa-lg fa-angle-up pull-right']");
	}

	public Element getupClick14() {
		return driver.FindElementByXPath("//li[@id='14']//i[@class='fa fa-lg fa-angle-up pull-right']");
	}

	public Element getupClick15() {
		return driver.FindElementByXPath("//li[@id='15']//i[@class='fa fa-lg fa-angle-up pull-right']");
	}

	public Element getupClick16() {
		return driver.FindElementByXPath("//li[@id='16']//i[@class='fa fa-lg fa-angle-up pull-right']");
	}

	public Element getupClick17() {
		return driver.FindElementByXPath("//li[@id='17']//i[@class='fa fa-lg fa-angle-up pull-right']");
	}

	public Element getupClick18() {
		return driver.FindElementByXPath("//li[@id='18']//i[@class='fa fa-lg fa-angle-up pull-right']");
	}

	public Element getupClick19() {
		return driver.FindElementByXPath("//li[@id='19']//i[@class='fa fa-lg fa-angle-up pull-right']");
	}

	public Element getupClick20() {
		return driver.FindElementByXPath("//li[@id='20']//i[@class='fa fa-lg fa-angle-up pull-right']");
	}

	public Element getStaticText() {
		return driver.FindElementByXPath("//input[@id='FriendlyInput_0']");
	}

	public Element getRadioGroupLabel() {
		return driver.FindElementByXPath("//input[@id='FriendlyInput_1']");
	}

	public Element getInstructionText() {
		return driver.FindElementByXPath("//input[@id='HelpText_1']");
	}

	public Element getErrorMessage() {
		return driver.FindElementByXPath("//input[@id='ErrorMessage_1']");
	}

	public Element getRTagLabel() {
		return driver.FindElementByXPath("//input[@id='FriendlyInput_2']");
	}

	public Element getRDefaultAction() {
		return driver.FindElementByXPath("//div[@id='ab-2']//i[@class='defertext']");
	}

	public Element getRFieldLogic() {
		return driver.FindElementByXPath("//div[@id='c-2']//div[@class='logic-box col-md-12']");
	}

	public Element getRErrorMessage() {
		return driver.FindElementByXPath("//div[@id='c-2']//div[@class='form-group col-md-12']//i[@class='defertext']");
	}

	public Element getNRTagLabel() {
		return driver.FindElementByXPath("//input[@id='FriendlyInput_3']");
	}

	public Element getNRDefaultAction() {
		return driver.FindElementByXPath("//div[@id='ab-3']//i[@class='defertext']");
	}

	public Element getNRFieldLogic() {
		return driver.FindElementByXPath("//div[@id='c-3']//div[@class='logic-box col-md-12']");
	}

	public Element getNRErrorMessage() {
		return driver.FindElementByXPath("//div[@id='c-3']//div[@class='form-group col-md-12']//i[@class='defertext']");
	}

	public Element getTITagLabel() {
		return driver.FindElementByXPath("//input[@id='FriendlyInput_4']");
	}

	public Element getTIDefaultAction() {
		return driver.FindElementByXPath("//div[@id='ab-4']//i[@class='defertext']");
	}

	public Element getTIFieldLogic() {
		return driver.FindElementByXPath(
				"//div[@id='c-4']//div[@class='col-md-12 field-logic-container']//i[@class='defertext']");
	}

	public Element getTIErrorMessage() {
		return driver.FindElementByXPath("//div[@id='c-4']//div[@class='form-group col-md-12']//i[@class='defertext']");
	}

	public Element getTIHelpText() {
		return driver.FindElementByXPath("//input[@id='HelpText_4']");
	}

	public Element getTIGCHECKGROUPLabel() {
		return driver.FindElementByXPath("//input[@id='FriendlyInput_10']");
	}

	public Element getTIGInstructionText() {
		return driver.FindElementByXPath("//input[@id='HelpText_10']");
	}

	public Element getTIGErrorMessage() {
		return driver.FindElementByXPath("//input[@id='ErrorMessage_10']");
	}

	public Element getPITagLabel() {
		return driver.FindElementByXPath("//input[@id='FriendlyInput_6']");
	}

	public Element getPIDefaultAction() {
		return driver.FindElementByXPath("//div[@id='ab-6']//i[@class='defertext']");
	}

	public Element getPIFieldLogic() {
		return driver.FindElementByXPath("//div[@id='c-6']//div[@class='logic-box col-md-12']");
	}

	public Element getPIErrorMessage() {
		return driver.FindElementByXPath("//div[@id='c-6']//div[@class='form-group col-md-12']//i[@class='defertext']");
	}

	public Element getPIHelpText() {
		return driver.FindElementByXPath("//input[@id='HelpText_6']");
	}

	public Element getFLTagLabel() {
		return driver.FindElementByXPath("//input[@id='FriendlyInput_7']");
	}

	public Element getFLDefaultAction() {
		return driver.FindElementByXPath("//div[@id='ab-7']//i[@class='defertext']");
	}

	public Element getFLFieldLogic() {
		return driver.FindElementByXPath(
				"//div[@id='c-7']//div[@class='col-md-12 field-logic-container']//i[@class='defertext']");
	}

	public Element getFLErrorMessage() {
		return driver.FindElementByXPath("//div[@id='c-7']//div[@class='form-group col-md-12']//i[@class='defertext']");
	}

	public Element getFLHelpText() {
		return driver.FindElementByXPath("//input[@id='HelpText_7']");
	}

	public Element getHCTagLabel() {
		return driver.FindElementByXPath("//input[@id='FriendlyInput_8']");
	}

	public Element getHCDefaultAction() {
		return driver.FindElementByXPath("//div[@id='ab-8']//i[@class='defertext']");
	}

	public Element getHCFieldLogic() {
		return driver.FindElementByXPath(
				"//div[@id='c-8']//div[@class='col-md-12 field-logic-container']//i[@class='defertext']");
	}

	public Element getHCErrorMessage() {
		return driver.FindElementByXPath("//div[@id='c-8']//div[@class='form-group col-md-12']//i[@class='defertext']");
	}

	public Element getHCHelpText() {
		return driver.FindElementByXPath("//input[@id='HelpText_8']");
	}

	public Element getHDTagLabel() {
		return driver.FindElementByXPath("//input[@id='FriendlyInput_9']");
	}

	public Element getHDHelpText() {
		return driver.FindElementByXPath("//input[@id='HelpText_9']");
	}

	public Element getPGTagLabel() {
		return driver.FindElementByXPath("//input[@id='FriendlyInput_11']");
	}

	public Element getPGInstructionText() {
		return driver.FindElementByXPath("//input[@id='HelpText_11']");
	}

	public Element getPGErrorMessage() {
		return driver.FindElementByXPath("//input[@id='ErrorMessage_11']");
	}

	public Element getPTagLabel() {
		return driver.FindElementByXPath("//input[@id='FriendlyInput_12']");
	}

	public Element getPDefaultAction() {
		return driver.FindElementByXPath("//div[@id='ab-12']//i[@class='defertext']");
	}

	public Element getPFieldLogic() {
		return driver.FindElementByXPath(
				"//div[@id='c-12']//div[@class='col-md-12 field-logic-container']//i[@class='defertext']");
	}

	public Element getPErrorMessage() {
		return driver
				.FindElementByXPath("//div[@id='c-12']//div[@class='form-group col-md-12']//i[@class='defertext']");
	}

	public Element getNPTagLabel() {
		return driver.FindElementByXPath("//input[@id='FriendlyInput_13']");
	}

	public Element getNPDefaultAction() {
		return driver.FindElementByXPath("//div[@id='ab-13']//i[@class='defertext']");
	}

	public Element getNPFieldLogic() {
		return driver.FindElementByXPath(
				"//div[@id='c-13']//div[@class='col-md-12 field-logic-container']//i[@class='defertext']");
	}

	public Element getNPErrorMessage() {
		return driver
				.FindElementByXPath("//div[@id='c-13']//div[@class='form-group col-md-12']//i[@class='defertext']");
	}

	public Element getPTGCHECKGROUPLabel() {
		return driver.FindElementByXPath("//input[@id='FriendlyInput_14']");
	}

	public Element getPTGInstructionText() {
		return driver.FindElementByXPath("//input[@id='HelpText_14']");
	}

	public Element getPTGErrorMessage() {
		return driver.FindElementByXPath("//input[@id='ErrorMessage_14']");
	}

	public Element getACTagLabel() {
		return driver.FindElementByXPath("//input[@id='FriendlyInput_15']");
	}

	public Element getACDefaultAction() {
		return driver.FindElementByXPath("//div[@id='ab-15']//i[@class='defertext']");
	}

	public Element getACFieldLogic() {
		return driver.FindElementByXPath(
				"//div[@id='c-15']//div[@class='col-md-12 field-logic-container']//i[@class='defertext']");
	}

	public Element getACErrorMessage() {
		return driver
				.FindElementByXPath("//div[@id='c-15']//div[@class='form-group col-md-12']//i[@class='defertext']");
	}

	public Element getAWPTagLabel() {
		return driver.FindElementByXPath("//input[@id='FriendlyInput_16']");
	}

	public Element getAWPDefaultAction() {
		return driver.FindElementByXPath("//div[@id='ab-16']//i[@class='defertext']");
	}

	public Element getAWPFieldLogic() {
		return driver.FindElementByXPath(
				"//div[@id='c-16']//div[@class='col-md-12 field-logic-container']//i[@class='defertext']");
	}

	public Element getAWPErrorMessage() {
		return driver
				.FindElementByXPath("//div[@id='c-16']//div[@class='form-group col-md-12']//i[@class='defertext']");
	}

	public Element getCGTagLabel() {
		return driver.FindElementByXPath("//input[@id='FriendlyInput_17']");
	}

	public Element getCGInstructionText() {
		return driver.FindElementByXPath("//input[@id='HelpText_17']");
	}

	public Element getCGErrorMessage() {
		return driver.FindElementByXPath("//input[@id='HelpText_17']");
	}

	public Element getCTagLabel() {
		return driver.FindElementByXPath("//input[@id='FriendlyInput_18']");
	}

	public Element getCDefaultAction() {
		return driver.FindElementByXPath("//div[@id='ab-18']//i[@class='defertext']");
	}

	public Element getCFieldLogic() {
		return driver.FindElementByXPath(
				"//div[@id='c-18']//div[@class='col-md-12 field-logic-container']//i[@class='defertext']");
	}

	public Element getCErrorMessage() {
		return driver
				.FindElementByXPath("//div[@id='c-18']//div[@class='form-group col-md-12']//i[@class='defertext']");
	}

	public Element getHTagLabel() {
		return driver.FindElementByXPath("//input[@id='FriendlyInput_19']");
	}

	public Element getHDefaultAction() {
		return driver.FindElementByXPath("//div[@id='ab-19']//i[@class='defertext']");
	}

	public Element getHFieldLogic() {
		return driver.FindElementByXPath(
				"//div[@id='c-19']//div[@class='col-md-12 field-logic-container']//i[@class='defertext']");
	}

	public Element getHErrorMessage() {
		return driver
				.FindElementByXPath("//div[@id='c-19']//div[@class='form-group col-md-12']//i[@class='defertext']");
	}

	public Element getDCCOMMENTLabel() {
		return driver.FindElementByXPath("//input[@id='FriendlyInput_20']");
	}

	public Element getDCErrorMessage() {
		return driver.FindElementByXPath("//input[@id='ErrorMessage_20']");
	}

	public Element getDCHelpText() {
		return driver.FindElementByXPath("//input[@id='HelpText_20']");
	}

	public Element getRGDefaultAction() {
		return driver.FindElementByXPath("//select[@id='DefaultAction_1']");
	}

	public Element getRTagType() {
		return driver.FindElementByXPath("//select[@id='2']");
	}

	public Element getRGroupAssociation() {
		return driver.FindElementByXPath("//select[@id='TagRdo_2']");
	}

	public Element getNRTagType() {
		return driver.FindElementByXPath("//select[@id='3']");
	}

	public Element getNRGroupAssociation() {
		return driver.FindElementByXPath("//select[@id='TagRdo_3']");
	}

	public Element getTITagType() {
		return driver.FindElementByXPath("//select[@id='4']");
	}

	public Element getTIGroupAssociation() {
		return driver.FindElementByXPath("//select[@id='TagRdo_4']");
	}

	public Element getTIGDefaultAction() {
		return driver.FindElementByXPath("//select[@id='DefaultAction_10']");
	}

	public Element getTIGObject() {
		return driver.FindElementByXPath("//select[@id='SelectObjects_10_1']");
	}

	public Element getTIGCondition() {
		return driver.FindElementByXPath("//select[@id='SelectCondition_10_1']");
	}

	public Element getTIGAction() {
		return driver.FindElementByXPath("//select[@id='LogicFieldAction_10']");
	}

	public Element getPITagType() {
		return driver.FindElementByXPath("//select[@id='6']");
	}

	public Element getPIGroupAssociation() {
		return driver.FindElementByXPath("//select[@id='TagChk_6']");
	}

	public Element getFLTagType() {
		return driver.FindElementByXPath("//select[@id='7']");
	}

	public Element getFLGroupAssociation() {
		return driver.FindElementByXPath("//select[@id='TagChk_7']");
	}

	public Element getHCTagType() {
		return driver.FindElementByXPath("//select[@id='8']");
	}

	public Element getHCGroupAssociation() {
		return driver.FindElementByXPath("//select[@id='TagChk_8']");
	}

	public Element getHDTagType() {
		return driver.FindElementByXPath("//select[@id='9']");
	}

	public Element getHDGroupAssociation() {
		return driver.FindElementByXPath("//select[@id='TagChk_9']");
	}

	public Element getHDDefaultAction() {
		return driver.FindElementByXPath("//select[@id='DefaultAction_9']");
	}

	public Element getPGDefaultAction() {
		return driver.FindElementByXPath("//select[@id='DefaultAction_11']");
	}

	public Element getPTagType() {
		return driver.FindElementByXPath("//select[@id='12']");
	}

	public Element getPGroupAssociation() {
		return driver.FindElementByXPath("//select[@id='TagRdo_12']");
	}

	public Element getNPTagType() {
		return driver.FindElementByXPath("//select[@id='13']");
	}

	public Element getNPGroupAssociation() {
		return driver.FindElementByXPath("//select[@id='TagRdo_13']");
	}

	public Element getPTGDefaultAction() {
		return driver.FindElementByXPath("//select[@id='DefaultAction_14']");
	}

	public Element getPTGObject() {
		return driver.FindElementByXPath("//select[@id='SelectObjects_14_1']");
	}

	public Element getPTGCondition() {
		return driver.FindElementByXPath("//select[@id='SelectCondition_14_1']");
	}

	public Element getPTGAction() {
		return driver.FindElementByXPath("//select[@id='LogicFieldAction_14']");
	}

	public Element getACTagType() {
		return driver.FindElementByXPath("//select[@id='15']");
	}

	public Element getACGroupAssociation() {
		return driver.FindElementByXPath("//select[@id='TagChk_15']");
	}

	public Element getAWPTagType() {
		return driver.FindElementByXPath("//select[@id='16']");
	}

	public Element getAWPGroupAssociation() {
		return driver.FindElementByXPath("//select[@id='TagChk_16']");
	}

	public Element getCGDefaultAction() {
		return driver.FindElementByXPath("//select[@id='DefaultAction_17']");
	}

	public Element getCTagType() {
		return driver.FindElementByXPath("//select[@id='18']");
	}

	public Element getCGroupAssociation() {
		return driver.FindElementByXPath("//select[@id='TagRdo_18']");
	}

	public Element getHTagType() {
		return driver.FindElementByXPath("//select[@id='19']");
	}

	public Element getHGroupAssociation() {
		return driver.FindElementByXPath("//select[@id='TagRdo_19']");
	}

	public Element getDCDefaultAction() {
		return driver.FindElementByXPath("//select[@id='DefaultAction_20']");
	}

	public Element getDCObject() {
		return driver.FindElementByXPath("//select[@id='SelectObjects_20_1']");
	}

	public Element getDCCondition() {
		return driver.FindElementByXPath("//select[@id='SelectCondition_20_1']");
	}

	public Element getDCAction() {
		return driver.FindElementByXPath("//select[@id='LogicFieldAction_20']");
	}

	public Element getResponsiveCheked() {
		return driver
				.FindElementByXPath("//div[@id='item1']//div[@id='0_radiogroup']//div[1]//div[1]//label[1]//span[1]");
	}

	public Element getNotResponsiveCheked() {
		return driver.FindElementByXPath("//div[@id='item1']//div[2]//div[1]//label[1]//span[1]");
	}

	public Element getTechnicalIssueCheked() {
		return driver.FindElementByXPath("//div[@id='item1']//div[3]//div[1]//label[1]//span[1]");
	}

	public Element getTestUrCodeClick() {
		return driver.FindElementByXPath("//input[@id='previewForm']");
	}

	public Element getError1() {
		return driver.FindElementByXPath("//span[@class='validationSpan']");
	}

	public Element getError2() {
		return driver.FindElementByXPath("//td[@id='td_checkgroup_10']//span[@class='validationSpan']");
	}

	public Element getError4() {
		return driver.FindElementByXPath(
				"//td[@class='form-c c-form-textwidth option-group-cell']//span[@class='validationSpan']");
	}

	public Element getError3() {
		return driver.FindElementByXPath("//td[@id='td_COMMENT_1']//span[@class='validationSpan']");
	}

	public Element getDefaultActionDropdown() {
		return driver.FindElementByXPath(
				"//div[@class='panel-collapse collapse in']//child::div//label//following::div//div//select[starts-with(@id,'DefaultAction')]");
	}

	public Element getNonPrivilegeRadio() {
		return driver.FindElementByXPath("//input[@id='9_radio']//parent::label//span");
	}

	public Element getConfidentialRadio() {
		return driver
				.FindElementByXPath("//div[@id='item17']//div[@id='0_radiogroup']//div[1]//div[1]//label[1]//span[1]");
	}

	public Element getDocument_CommentsTextBox() {
		return driver.FindElementByXPath("//textarea[@id='1_textarea']");
	}

	public Element getReadOnlyTextBox() {
		return driver.FindElementByXPath("//input[@name='FIELD']");
	}

	public Element getTestCodingForm() {
		return driver.FindElementByXPath("//input[@id='previewForm']");
	}

	public Element getSuccessMessage() {
		return driver.FindElementById("divPreviewSuccess");
	}

	public Element getOkButton() {
		return driver.FindElementByXPath(
				"//div[@class='ui-dialog-buttonpane ui-widget-content ui-helper-clearfix']//button[text()='OK']");
	}

	// Added by Indium Consoilidation
	public Element getEditableMetaData(String projectFieldName) {
		return driver.FindElementByXPath("//label[@title='" + projectFieldName + "']");
	}

	// SearchFilter
	public Element getCodingForm_Search() {
		return driver.FindElementByXPath("//div[@id='CodingFormDataTable_filter']//input");
	}

	// Pagination coding form
	public Element getPagin_CodingForm() {
		return driver.FindElementByXPath("//div[@id='CodingFormDataTable_paginate']//li//a[text()='Next']");
	}

//    Added by baskar
	
	public Element getCF_PreviewFieldMandatoryFieldText() {
		return driver.FindElementByXPath("//span[text()='*']");
	}
	
	public Element getCF_PreviewCheckboxValue() {
		return driver.FindElementByXPath("//*[@id='_checkgroup']");
	}
	
	
	public Element getCF_CheckGroup() {
		return driver.FindElementById("TagChk_0");
	}
	
	public Element getAddCodingFormCheckToSG(String fieldValue) {
		return driver.FindElementByXPath(".//*[@id='CodingFormDataTable']//td[text()='" + fieldValue + "']//..//td//i");
	}
	public Element getSetCodingFormToSG() {
		return driver.FindElementByXPath("//button[@id='btnSetSGCodingForms']");
	}

	public Element getTagType2() {
		return driver.FindElementByXPath(".//*[@id='c-1']//select[@id='1']");
	}

	public Element getCF_RadioGroup2() {
		return driver.FindElementById("TagRdo_1");
	}

	public Element getCFInstructionText() {
		return driver.FindElementByXPath("//label[text()='Instruction Text']//..//input");
	}

	// added by iyappan
	public Element getCodingForm_SecondComment() {
		return driver.FindElementByXPath("//*[@id='lstComments']/li[2]/label/i");
	}

	public Element getCodingForm_SecondTag() {
		return driver.FindElementByXPath("//*[@id='lstTags']/li[2]/label/i");
	}

	public Element getCodingForm_SecondMetadata() {
		return driver.FindElementByXPath("//*[@id='lstMetadata']/li[2]/label/i");
	}

	public ElementCollection getCodingForm_getAllObjects() {
		return driver.FindElementsByXPath("//a[@class='collasped collapsed']");
	}

	public Element getCodingForm_getExtendObject() {
		return driver.FindElementByXPath("//a[@class='collasped collapsed']");
	}

	public Element getCodingForm_ErrorMsg(int i) {
		return driver.FindElementById("ErrorMessage_" + i + "");
	}

	public Element getCodingForm_HelpMsg(int i) {
		return driver.FindElementById("HelpText_" + i + "");
	}

	public Element getCodingForm_StaticText(int i) {
		return driver.FindElementById("FriendlyInput_" + i + "");
	}

	public Element getCF_CheckGroup(int i) {
		return driver.FindElementById("TagChk_" + i + "");
	}

	public Element getCFLabel(String cfName) {
		return driver.FindElementByXPath("//td[text()='" + cfName + "']");
	}

	public Element getCFValidateBtn() {
		return driver.FindElementByXPath("//a[text()='Validate']");
	}

	public Element getCFValidateMsg() {
		return driver.FindElementByXPath("//strong[text()='Validation Status:']/parent::div/ul/li");
	}

	public Element getCFValidationPopUpOkBtn() {
		return driver.FindElementByXPath(
				"//strong[text()='Validation Status:']/ancestor::div[@role='dialog']//button[@id='btnYes']");
	}

	public Element getCFCommentDisabledInPreview(String objectName) {
		return driver.FindElementByXPath("//textarea[@controllabel='" + objectName + "'][@disabled='']");
	}

	public Element getCFmetadataTextBox(String objectName) {
		return driver.FindElementByXPath("//span[text()='" + objectName + "']/ancestor::table//input[@name='FIELD']");
	}

	public Element getCF_objectName(int i) {
		return driver.FindElementByXPath("//input[@id='FriendlyInput_" + i + "']");
	}

	public Element getCF_TagType(int i) {
		return driver.FindElementByXPath("//label[text()='Tag Type']/parent::div//select[@id='" + i + "']");
	}

	public Element getCFOperatorFieldLogicObject(int i, int operatorNo) {
		return driver.FindElementByXPath("//select[@id='SelectObjects_" + i + "_" + operatorNo + "']");
	}

	public Element getCFOperatorFieldLogicCondition(int i, int operatorNo) {
		return driver.FindElementByXPath("//select[@id='SelectCondition_" + i + "_" + operatorNo + "']");
	}

	public Element getCFOperatorFieldLogicConditionName(int i, int operatorNo, String conditionName) {
		return driver.FindElementByXPath(
				"//select[@id='SelectCondition_" + i + "_" + operatorNo + "']//option[text()='" + conditionName + "']");
	}

	public Element getCFOperator(int i, int operatorNo) {
		return driver.FindElementByXPath("//select[@id='SelectOperator_" + i + "_" + operatorNo + "']");
	}

	public Element selectTagInPreviewBox(int objectNo) {
		return driver.FindElementByXPath("//span[@id='l_it_" + objectNo + "']/parent::div//input/parent::label/span");
	}

	public Element getCF_RadioGroup(int i) {
		return driver.FindElementById("TagRdo_" + i + "");
	}

	public Element getCFselectOperatorFieldLogicObj(String objectName, int operatorNo) {
		return driver.FindElementByXPath("(//option[contains(text(),'" + objectName + "')])[" + operatorNo + "]");
	}

	public Element getTagInPreviewBoxHidden(int objectNo) {
		return driver.FindElementByXPath("//span[@id='l_it_" + objectNo + "']/parent::div[@style='display: none;']");
	}

	public Element selectObjectsInPreviewBox(String objectName) {
		return driver.FindElementByXPath("//span[text()='" + objectName + "']/parent::span/parent::div/label/span");
	}

	public Element getHiddenObjectsInPreviewBox(String objectName) {
		return driver.FindElementByXPath("//span[text()='" + objectName + "']/ancestor::div[@style='display: none;']");
	}

	public Element geErrMsgInPreviewBox() {
		return driver.FindElementByXPath("//span[@style='color: red;font-weight: bold;']");
	}

	public Element getDisabledTagsInPreviewBox(String objectName) {
		return driver.FindElementByXPath(
				"//span[text()='" + objectName + "']/parent::span/parent::div/label/input[@disabled='']");
	}

	public Element getExtendCFobject(String objectName) {
		return driver.FindElementByXPath(
				"//span[text()='" + objectName + "']/parent::a/i[@class='fa fa-lg fa-angle-up pull-right']");
	}

	public Element getCFselectFieldLogicObject(String objectName, int objectNo) {
		return driver.FindElementByXPath(
				"//select[@id='SelectObjects_" + objectNo + "_1']//option[contains(text(),'" + objectName + "')]");
	}

	// created by Mohan
	public Element getCodingForm_PreviewText(int i) {
		return driver.FindElementByXPath("//span[@id='l_it_" + i + "']");
	}

	public Element getCFPreviewPopUpOkBtn() {
		return driver.FindElementByXPath(
				"//span[text()='Coding Form Preview']/ancestor::div[@role='dialog']//button[@id='btnYes']");
	}

	public Element getCFMetaFirstText() {
		return driver.FindElementByXPath("//*[@id='lstMetadata']/li[1]/label/strong");
	}

	public Element getCodingForm_SelectTagCheckBox(int rowNo) {
		return driver.FindElementByXPath("//*[@id='lstTags']/li[" + rowNo + "]/label/i");
	}

	public Element getCodingForm_SelectRemoveLink(int rowNo) {
		return driver.FindElementByXPath("//li[@id='" + rowNo + "']//a[@id='" + rowNo + "']");
	}

	public Element getCodingForm_TagText(int rowNo) {
		return driver.FindElementByXPath("(//label[@class='checkbox'])[" + rowNo + "]");
	}

	public Element getCFAddLogicRuleBtn(int i) {
		return driver.FindElementByXPath("//button[@id='" + i + "']");
	}

	public Element getCFFieldLogicObject(int i) {
		return driver.FindElementByXPath("//select[@id='SelectObjects_" + i + "_1']");
	}

	public Element getCFFieldLogicCondition(int i) {
		return driver.FindElementByXPath("//select[@id='SelectCondition_" + i + "_1']");
	}

	public Element getCFFieldLogicAction(int i) {
		return driver.FindElementByXPath("//select[@id='LogicFieldAction_" + i + "']");
	}

	public Element getCFPreviewObjectName(String objectName) {
		return driver.FindElementByXPath("//td//span[text()='" + objectName + "']");
	}

	public Element getCFselectFieldLogicObj(String objectName, int objectNo) {
		return driver.FindElementByXPath("//option[contains(text(),'" + objectName + "')]");
	}

	public Element getCFselectFieldLogicAction(String objectName, int objectNo) {
		return driver.FindElementByXPath(
				"//select[@id='LogicFieldAction_" + objectNo + "']//option[contains(text(),'" + objectName + "')]");
	}

	public Element getCFSuccessMsgNotDisplayed() {
		return driver.FindElementByXPath("//div[@id='divPreviewSuccess' and contains(@style,'display:none;')]");
	}

	public Element getCFErrorMsgNotDisplayed() {
		return driver.FindElementByXPath("//div[@id='divPreviewError' and contains(@style,'display:none;')]");

	}

	public Element getCFErrorMsg() {
		return driver.FindElementById("divPreviewError");
	}

	public Element getCFPreviewCommentField(String objectName) {
		return driver.FindElementByXPath("//textarea[@controllabel='" + objectName + "']");
	}

	public Element getCFPreviewRadioText(String objectName) {
		return driver.FindElementByXPath("//div[@class='radio-group']//span[text()='" + objectName + "']");
	}

	public Element getCFPreviewCheckText(String objectName) {
		return driver.FindElementByXPath("//div[@class='check-group']//span[text()='" + objectName + "']");
	}

	public Element getObjectsInPreviewBox(String objectName) {
		return driver.FindElementByXPath("//span[text()='" + objectName + "']/parent::span/parent::div/label/input");
	}

	// Added by Aathith
	public Element getCodingForm_HelpText1() {
		return driver.FindElementById("HelpText_1");
	}

	public Element getCF_AddLogicButton(int i) {
		return driver.FindElementByXPath(
				"(//*[@class='add-logic btn btn-labeled btn-primary bg-color-blueLight'])[" + i + "]");
	}

	public Element getCF_Object1(int i) {
		return driver.FindElementByXPath("//*[@id='SelectObjects_" + i + "_1']");
	}

	public Element getCF_FieldLogicCondition(int i) {
		return driver.FindElementByXPath("//*[@id='SelectCondition_" + i + "_1']");
	}

	public Element getCF_FieldLogicAction(int i) {
		return driver.FindElementByXPath("//select[@id='LogicFieldAction_" + i + "']");
	}

	public Element getCF_TagType1() {
		return driver.FindElementByXPath("//*[@class='tag-type form-control input-sm']");
	}

	public Element getCF_TagType2() {
		return driver.FindElementByXPath("(//*[@class='tag-type form-control input-sm'])[2]");
	}

	public Element getDefaultRadioBtn() {
		return driver.FindElementByXPath(
				"//td[text()='Default Project Coding Form']//parent::tr/td[5]/span//input[@checked]//parent::label/i");
	}

	public Element getDefaultSelectRadioBtn() {
		return driver.FindElementByXPath(
				"//td[text()='Default Project Coding Form']//parent::tr/td[5]/span//input[@Class]//parent::label/i");
	}

	public Element getDefaultYesBtn() {
		return driver.FindElementByXPath("//div[@class='MessageBoxButtonSection']/button[text()=' Yes']");
	}

	// Added by Sakthivel
	public Element getAttorney_ClientText() {
		return driver.FindElementByXPath("//span[@class='itemFriendlyName'][text()='Attorney_Client']");

	}

	public Element getCF_Preview2() {
		return driver.FindElementByXPath(".//*[starts-with(@id,'item1')]//td[1]");
	}

	public Element getCF_Preview2Disable() {
		return driver.FindElementByXPath("//div[@id='item1']//input[@class='checkbox']");
	}

	public Element getCF_Preview1CheckBox() {
		return driver.FindElementByXPath("//div[@id='item0']//input[@class='checkbox']");
	}

	public Element getCodingForm_MetaDataUsingIndex(int index) {
		return driver.FindElementByXPath("//*[@id='lstMetadata']/li[" + index + "]/label/i");
	}

	public Element getCF_PreviewTagNameHidden(String tagName) {
		return driver.FindElementByXPath("(//span[text()='" + tagName + "'])[last()]");
	}

	// projectfiledWithData
	String expectedStaticText = "This is the default Coding Form for this project.  Please complete all fields.";
	String expectedGroupText = "Responsive Group";
	String expectedInstructionText = "Responsiveness";
	String expectedPGInstructionText = "Privileged";
	String expectedPTGInstructionText = "Privilege Type";
	String expectedCGInstructionText = "Confidentiality";
	String expectedDefaultAction1Text = "Make It Required";
	String expectedDefaultAction2Text = "Make It Hidden";
	String expectedDefaultAction3Text = "Make It Optional";
	String expectedDefaultAction4Text = "Make It Display But Not Selectable";
	String expectedGErrorMsgText = "You must select one of these options";
	String expectedRTagLabelText = "Responsive";
	String expectedNRTagLabelText = "Not_Responsive";
	String expectedTITagLabelText = "Technical_Issue";
	String expectedFLTagLabelText = "Foreign_Language";
	String expectedHCTagLabelText = "Hidden_Content";
	String expectedHDTagLabelText = "Hot_Doc";
	String expectedPGTagLabelText = "Privileged Group";
	String expectedPTagLabelText = "Privileged";
	String expectedNPTagLabelText = "Not_Privileged";
	String expectedPTGTagLabelText = "Privilege Type Group";
	String expectedACTagLabelText = "Attorney_Client";
	String expectedAWPTagLabelText = "Attorney_WorkProduct";
	String expectedCGTagLabelText = "Confidentiality Group";
	String expectedCTagLabelText = "Confidential";
	String expectedHTagLabelText = "Highly_Confidential";
	String expectedDCTagLabelText = "Document_Comments";
	String expectedTagType1Text = "Radio Item";
	String expectedTagType2Text = "Check Item";
	String expectedGroupAssociation1Text = "Responsive Group(radiogroup_1)";
	String expectedGroupAssociation2Text = "Tech Issue Group(checkgroup_10)";
	String expectedGroupAssociation3Text = "Privileged Group(radiogroup_11)";
	String expectedGroupAssociation4Text = "Confidentiality Group(radiogroup_17)";
	String expectedGroupAssociation5Text = "Privilege Type Group(checkgroup_14)";
	String expectedGroupAssociation6Text = "No Checkbox Group Association";
	String expectedDefaultActionText = "Actions controlled at group level";
	String expectedFieldLogicText = "Field logic controlled at group level";
	String expectedErrorMsgText = "Error message controlled at group level";
	String expectedErrorMessageText = "You must determine whether the doc is privileged or not";
	String expectedTIHelpText = "Is there some reason that review cannot be determined?";
	String expectedTIGCHECKGROUPLabel = "Tech Issue Group";
	String expectedTIGInstructionText = "Tech Issue Group";
	String expectedFieldLogicObjectText1 = "Technical_Issue(TAG_14)";
	String expectedFieldLogicObjectText2 = "Privileged(TAG_11)";
	String expectedFieldLogicConditionText = "Selected";
	String expectedFieldLogicActionText = "Make this Required";
	String expectedTIGErrorMsgText = "If the document has a technical issue and cannot be reviewed, you must select a reason why from this list above";
	String expectedDCErrorMsgText = "If you select Technical Issue, you must enter a comment";
	String expectedPITagLabelText = "Processing_Issue";
	String expectedPIHelpText = "Does this doc contain some type of issue that prohibits the ability for the record to be reviewed?";
	String expectedFLHelpText = "Does this doc contain some language besides what you can review?";
	String expectedHCHelpText = "Do you believe the document contains some hidden content?";
	String expectedHDHelpText = "Check this tag if this document critical to surface to lead attorneys on the matter immediately";
	String expectedDCHelpText = "Please enter any general comments related to review of this document";

	// Added by Indium consolidation
	public Element getCodingForm_MetaData(String fieldText) {
		return driver.FindElementByXPath("//strong[text()='" + fieldText + "']//parent::label");
	}

	// Added by Aathith
	public Element getCF_TagTypes(int i) {
		return driver.FindElementByXPath(".//*[@id='c-" + i + "']//select[@id='" + i + "']");
	}

	/// CONSILIOPHASE2 ///

	// Added by baskar
	// for tagtype selection
	public Element getTagType(int i) {
		return driver.FindElementByXPath("//select[@class='tag-type form-control input-sm'][@id='" + i + "']");
	}

	public Element getAvailableObjInStructure(String availableObjects) {
		return driver.FindElementByXPath("//span[text()='" + availableObjects + "']");
	}

	public Element getDefaultNoBtn() {
		return driver.FindElementByXPath("//div[@class='MessageBoxButtonSection']/button[text()=' No']");
	}

	public ElementCollection getCfListSize(int i) {
		return driver.FindElementsByXPath("//table[@id='CodingFormDataTable']//tr//td[" + i + "]");
	}

	public ElementCollection getCf_HeaderValue() {
		return driver.FindElementsByXPath("//table[@id='CodingFormDataTable']//th");
	}

	public Element getCf_RemoveLink(int i) {
		return driver.FindElementByXPath("//a[@id='" + i + "'][text()='Remove']");
	}

	public Element getCf_TAGTAB() {
		return driver.FindElementByXPath("//*[@id='internal-tab-1']//span[text()='TAGS']");
	}

	public Element getCf_TagDisabled(String tag) {
		return driver.FindElementByXPath("//*[@id='lstTags']//label[@title='" + tag + "']/input[@disabled='']");
	}

	public Element getCf_CommentsDisabled(String comment) {
		return driver.FindElementByXPath("//*[@id='lstComments']//label[@title='" + comment + "']/input[@disabled='']");
	}

	public Element getCf_MetaDataDisabled(String metadata) {
		return driver
				.FindElementByXPath("//*[@id='lstMetadata']//label[@title='" + metadata + "']/input[@disabled='']");
	}

	public Element getCf_DownPull(String name) {
		return driver.FindElementByXPath(
				"//span[text()='" + name + "']//parent::a//i[@class='fa fa-lg fa-angle-up pull-right']");
	}

	public Element getCf_ValidationYesUsingPosition(int i) {
		return driver.FindElementByXPath("(//button[@id='btnYes'])[position()=" + i + "]");
	}

	public Element getCf_PreviewTagDisabled(String tagName) {
		return driver.FindElementByXPath("//span[text()='" + tagName + "']//ancestor::div/label/input[@disabled='']");
	}

	public ElementCollection getCFTableTotalShowingCount() {
		return driver.FindElementsByXPath("//*[@id='CodingFormDataTable']//tbody//tr");
	}

	//Added by shilpi for coding form changes
	public Element getSetCFButton() {return driver.FindElementById("btnSetSGCodingForms");	}
	public Element getSFFormCol(String cfname) {return driver.FindElementByXPath(
			".//*[@id='CodingFormDataTable']//td[text()='"+cfname+"']/../td[text()='YES (Default)']");
	}
	public Element getValidationText() {
		return driver.FindElementByXPath("(//div[contains(@class,'modal-body ui-dialog')])[last()]");
	}
	public Element getValidationButtonText() {
		return driver.FindElementByXPath("(//div[@class='ui-dialog-buttonset'])[last()]");
	}
	public Element getFullTagText() {
		return driver.FindElementByXPath("//a[@data-toggle='collapse']");
	}
	public Element getValidationButtonYes() {
		return driver.FindElementByXPath("(//div[@class='ui-dialog-buttonset']//button[text()='Yes'])[last()]");
	}
	public ElementCollection getListOfNameInCfStructure() {
		return driver.FindElementsByXPath("//a[@data-toggle='collapse']");
	}
	public Element getVerifyCfSavedName(String cfName) {
		return driver.FindElementByXPath("//table[@id='CodingFormDataTable']/tbody//..//td[text()='"+cfName+"']");
	}

	public ElementCollection getCfChecBoxUsingSize() {
		return driver.FindElementsByXPath("//table[@id='dtCodingFormList']//tbody//tr//input[@name='selectCodingForm']//..//i");
	}

	public ElementCollection getCfUnChecBoxUsingSize() {
		return driver.FindElementsByXPath(
				"//table[@id='dtCodingFormList']//tbody//tr//input[@name='selectCodingForm'] [@checked]//..//i");
	}

	public ElementCollection getAssignedCfName() {
		return driver.FindElementsByXPath(
				"//table[@id='dtCodingFormList']//tbody//tr//input[@name='selectCodingForm']//..//i//ancestor::td");
	}

	public Element getStep1CfPopUp() {
		return driver.FindElementByXPath("//span[text()='Add / Remove Coding Forms in this Security Group']");
	}

	public Element getStep2CfPopUp() {
		return driver.FindElementByXPath("//span[text()='Step 02: Sort Coding Form Order']");
	}
	
	public Element getCfPopUpCancel() {
		return driver.FindElementById("btnCodingFormCancel");
	}

	public ElementCollection sortOrderHamBurger() {
		return driver.FindElementsByXPath("//ol[@id='sortedCodingformList']//span");
	}
	public Element getHamBurgerDrag(String drag) {
		return driver.FindElementByXPath("//li[@value='"+drag+"']//div[@class='dd-handle ddcf-handle']");
	}
	public Element getHamBurgerDrop(String drop) {
		return driver.FindElementByXPath("//li[@value='"+drop+"']//..//div[@class='dd-placeholder'] ");
	}
	public Element getErrorMsgMore15CF() {
		return driver.FindElementByXPath("//p[text()='You cannot add more than 15 coding forms.']");
	}
	public Element getPopUpCheckBox() {
		return driver.FindElementByXPath("//input[@id='chkSelectAllCodingform']//..//i//ancestor::th/label");
	}
	public Element getVerifyRadioBtn(String CFName) {
		return driver.FindElementByXPath("//div[@id='dtCodingFormList_wrapper']//input[@value='" + CFName
				+ "']/ancestor::td/following-sibling::td//span/label");
	}
	public Element getSetDefaultSG() {
		return driver.FindElementByXPath("//th[@class='sorting_disabled']/b");
	}
	public Element getShowHide() {
		return driver.FindElementByXPath("//button//span[text()='Show / Hide Columns']");
	}
	public Element sortOrderNxtBtn() {
		return driver.FindElementById("btnSortOrderNext");
	}
	public Element getSelectCodeFormRadioBtn(String CFName) {
		return driver.FindElementByXPath("//div[@id='dtCodingFormList_wrapper']//input[@value='" + CFName
				+ "']/ancestor::td/following-sibling::td//span/label/i");
	}
	public ElementCollection getCodingForm_ListName() {
		return driver.FindElementsByXPath("//table[@id='CodingFormDataTable']//tr//td[1]");
	}
	
	//add by Aathith
		public ElementCollection getCodingFormTableHeaders() {
			return driver.FindElementsByXPath("//*[@id='CodingFormDataTable']/thead/tr/th");
		}
		
		public Element getCodingFormTableHeadColumn(int column) {
			return driver.FindElementByXPath("//*[@id='CodingFormDataTable']/thead/tr/th["+column+"]");
		}
		
		public Element checkDefaultCodingFormIsSelected() {
			return driver.FindElementByXPath("//input[@value='Default Project Coding Form']");
		}
		
		public Element checkDefaultCodingFormRadioBtnIsSelected() {
			return driver.FindElementByXPath("//input[@value='Default Project Coding Form']/../../following-sibling::td//input");
		}
		
		public Element getDefaultCodingFormInputBox() {
			return driver.FindElementByXPath("//input[@value='Default Project Coding Form']/../i");
		}
		
		public Element getDefaultCodingFormRadioBtn() {
			return driver.FindElementByXPath("//input[@value='Default Project Coding Form']/../../following-sibling::td//input/../i");
		}
		
		public Element btnCodingFormSave() {
			return driver.FindElementById("btnCodingFormSave");
		}
		
		public Element getDefaultCodingFormTableValues(int i) {
			return driver.FindElementByXPath("//td[text()='Default Project Coding Form']/..//td["+i+"]");
		}
		
		public Element getCodingFormTableValues(int row, int column) {
			return driver.FindElementByXPath("//*[@id='CodingFormDataTable']/tbody/tr["+row+"]/td["+column+"]");
		}
		
		public ElementCollection getCodingFormTableColumn(int column) {
			return driver.FindElementsByXPath("//*[@id='CodingFormDataTable']/tbody/tr/td["+column+"]");
		}
		
		public Element getCF_NthObjecttab(int i) {
			return driver.FindElementByXPath("//*[@href='#c-"+i+"']");
		}

		public Element getHideShowBtn() {
			return driver.FindElementByXPath("//button[@class='ColVis_Button ColVis_MasterButton']/span");
		}
		
		public Element getColumnsHideShowCheckBox(String columnName) {
			return driver.FindElementByXPath("//span[contains(text(),'"+columnName+"')]/../input");
		}
		
		public Element getHideShowBackGroundBtn() {
			return driver.FindElementByXPath("//div[@class='ColVis_collectionBackground']");
		}

		
		public Element getPreviewComment() {
			return driver.FindElementByXPath("//textarea[@name='COMMENT']");
		}
		
		public Element getPreviewMetaData() {
			return driver.FindElementByXPath("//input[@name='FIELD']");
		}
		
		public Element getPreviewRadioBtn() {
			return driver.FindElementByXPath("//input[@type='radio']");
		}
		
		public Element getPrevError(int i) {
			return driver.FindElementByXPath("(//span[@class='validationSpan'])["+i+"]");
		}
		
		public Element getPreview1stRadioBtn() {
			return driver.FindElementByXPath("//input[@name='radiogroup_2']");
		}
		
		public Element getPreview2ndRadioBtn() {
			return driver.FindElementByXPath("//input[@name='radiogroup_3']");
		}
		
		public Element getPreviewCheckBox() {
			return driver.FindElementByXPath("//input[contains(@id,'checkbox')]");
		}
		public Element getTagGroupValues(int objectNo) {
			return driver.FindElementByXPath("//span[@id='l_it_" + objectNo + "']/parent::div/div");

		}
		
		public Element getTag_Object(String tagName) {
			return driver.FindElementByXPath("//span[@class='itemFriendlyName'][text()='"+tagName +"']");

		}
		public Element getSaveWarningMsg() {
			return driver.FindElementByXPath("//span[text()='Wait - please make a decision before you leave this page']");

		}
		public Element getSaveConformMsg() {
			return driver.FindElementByXPath("//span[text()='Coding Form Save']");

		}
		public Element getCF_Preview_OkBtn() {
			return driver.FindElementByXPath("(//button[@id='btnYes'])[last()]");
		}
		public Element getCF_Preview_Radio(String tagName) {
			return driver.FindElementByXPath("//span[text()='"+tagName+"']//..//parent::div//input[@type='radio']");
		}
		public Element getCF_Preview_CheckBox(String tagName) {
			return driver.FindElementByXPath("//span[text()='"+tagName+"']//..//parent::div//input[@type='checkbox']");
		}
		public Element getCF_Preview_TagLabel(String tagName,int i) {
			return driver.FindElementByXPath("//span[text()='"+tagName+"']//ancestor::div[@id='item" + i + "']");
		}
		
		public Element getRemoveLinkMsg_YNButton() {
			return driver.FindElementByXPath("//p[text()='Are you sure?']");

		}
		public Element getRemoveLinkMsg_Delete() {
			return driver.FindElementByXPath("//h2//span[text()='Delete']");

		}
		
		public Element getCFHelpmsgTitle() {
			return driver.FindElementByXPath("//td[@id='td_TAG_2']//span");
		}
	
	
	
	public CodingForm(Driver driver) {

		this.driver = driver;
		base = new BaseClass(driver);
		doc = new DocViewPage(driver);
		reusableDocView = new ReusableDocViewPage(driver);
		assgnpage = new AssignmentsPage(driver);
//		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();

		softAssertion = new SoftAssert();
	}

	// Create CF with the given name
	// to create coding form with first tag and comments from the list
	public void createCodingform(String cfName) {

		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		getAddNewCodingFormBtn().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingFormName().Visible();
			}
		}), Input.wait30);
		getCodingFormName().SendKeys(cfName);
		getCodingForm_FirstTag().waitAndClick(10);

		getCodingForm_CommentTab().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_FirstComment().Visible();
			}
		}), Input.wait30);
		getCodingForm_FirstComment().Click();
		getCodingForm_AddToFormButton().Click();
		getSaveCFBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_Validation_ButtonYes().Visible();
			}
		}), Input.wait30);
		getCodingForm_Validation_ButtonYes().Click();

		System.out.println("Coding form " + cfName + " created");
		UtilityLog.info("Coding form " + cfName + " created");

		base.VerifySuccessMessage("Coding Form Saved successfully");
		Reporter.log(cfName + "coding Form Successful", true);
		UtilityLog.info("Coding Form Successful");
		// base.CloseSuccessMsgpopup();
	}

	public void CodingformToSecurityGroup(String cfName) throws InterruptedException {

		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_NumberToShow().Visible();
			}
		}), Input.wait60);
		getCodingForm_NumberToShow().selectFromDropdown().selectByVisibleText("100");
		Thread.sleep(3000);

		selectCF_SGF_RBtn(cfName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_SGValidation_ButtonYes().Visible();
			}
		}), Input.wait30);
		getCodingForm_SGValidation_ButtonYes().Click();

	}

	public void EditCodingform(final String cfName) {

		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_NumberToShow().Visible();
			}
		}), Input.wait60);
		getCodingForm_NumberToShow().selectFromDropdown().selectByVisibleText("100");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_EditButton(cfName).Visible();
			}
		}), Input.wait60);
		getCodingForm_EditButton(cfName).waitAndClick(10);

		getCF_SecondTag().waitAndClick(10);

		getCodingForm_AddToFormButton().Click();
		getSaveCFBtn().Click();
		try {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getCodingForm_Validation_ButtonYes().Visible();
				}
			}), Input.wait60);
			getCodingForm_Validation_ButtonYes().Click();
		} catch (Exception e) {
			System.out.println("Pop up button not appeared");
		}

		base.VerifySuccessMessage("Coding Form updated successfully");
		base.CloseSuccessMsgpopup();

		getCF_firstObjecttab().waitAndClick(10);
		driver.scrollingToBottomofAPage();

		getCodingForm_FirstRemoveLink().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_SGValidation_ButtonYes().Visible();
			}
		}), Input.wait30);
		getCodingForm_SGValidation_ButtonYes().Click();

		driver.scrollPageToTop();
		try {
			getCF_firstObjecttab().Displayed();
			Assert.assertFalse(1 == 0);
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("Tag successfullyremoved");
		}

	}

	public void DeleteCodingform(String cfName) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_NumberToShow().Visible();
			}
		}), Input.wait60);
		getCodingForm_NumberToShow().selectFromDropdown().selectByVisibleText("100");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}

		selectCF_SGF_RBtn(cfName);

		getCodingForm_DeleteButton(cfName).waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.getYesBtn().Visible();
			}
		}), Input.wait60);
		base.getYesBtn().Click();

		base.VerifySuccessMessage("Coding form deleted successfully");
		base.CloseSuccessMsgpopup();
	}

	public boolean CopyCodingform(final String cfName) {

		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_CopyButton(cfName).Visible();
			}
		}), Input.wait30);

		boolean nextPage = true;
		boolean found = false;
		System.out.println(getCFnames().size());
		while (nextPage) {
			int row = 1;

			for (WebElement ele : getCFnames().FindWebElements()) {
				System.out.println(ele.getText().trim());
				if (ele.getText().trim().equals(cfName)) {
					nextPage = false;
					found = true;
					getCodingForm_CopyButton(cfName).Click();
					System.out.println(cfName + " is selected");
					return true;

				}

				row++;

			}
			try {
				driver.scrollingToBottomofAPage();
				driver.getWebDriver().findElement(By.xpath("//li[@class='paginate_button next disabled']/a"))
						.isDisplayed();
				nextPage = false;
			} catch (Exception e) {
				driver.getWebDriver().findElement(By.linkText("Next")).click();
			}

		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return base.getYesBtn().Visible();
			}
		}), Input.wait60);
		String msg = getCF_DeletePopup().getText();
		System.out.println(msg);
		Assert.assertEquals("Are you sure you want to copy?", msg);
		base.getYesBtn().Click();

		base.VerifySuccessMessage("Coding form copied successfully");
		base.CloseSuccessMsgpopup();
		return found;

	}

	public void ViewCFinDocViewThrSearch(String cfName) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return doc.getDocView_CFName().Visible();
			}
		}), Input.wait60);
		if (doc.getDocView_CFName().Displayed()) {
			String name = doc.getDocView_CFName().getText().toString();
			System.out.println(name);
			Assert.assertEquals(cfName, name);
		}
	}

	public void ViewCFinDocViewThrAssignment(String cfName) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return doc.getDocView_CFName().Visible();
			}
		}), Input.wait60);
		if (doc.getDocView_CFName().Displayed()) {
			String name = doc.getDocView_CFName().getText().toString();
			System.out.println(name);
			Assert.assertEquals(cfName, name);
		}
	}

	public boolean selectCF_SGF_RBtn(final String CFname) {
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCFlistTable().Visible();
			}
		}), Input.wait30);

		boolean nextPage = true;
		boolean found = false;
		System.out.println(getCFnames().size());
		while (nextPage) {
			int row = 1;

			for (WebElement ele : getCFnames().FindWebElements()) {
				System.out.println(ele.getText().trim());
				if (ele.getText().trim().equals(CFname)) {
					nextPage = false;
					found = true;
					// System.out.println(row);
					getCFRadioBtn(row).waitAndClick(10);
					System.out.println(CFname + " is selected");
					return true;

				}

				row++;

			}
			try {
				driver.scrollingToBottomofAPage();
				driver.getWebDriver().findElement(By.xpath("//li[@class='paginate_button next disabled']/a"))
						.isDisplayed();
				nextPage = false;
				// System.out.println("Not found!!!!!!");
			} catch (Exception e) {
				driver.getWebDriver().findElement(By.linkText("Next")).click();
			}

		}
		return false;

	}

	public void AddCodingformwithcommentsandtag(String cfName, String comments, String tag) {

		getAddNewCodingFormBtn().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingFormName().Visible();
			}
		}), Input.wait30);
		getCodingFormName().SendKeys(cfName);
		getCodingForm_FirstTag().waitAndClick(10);
		getCodingForm_Tag(tag).waitAndClick(10);

		getCodingForm_CommentTab().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_FirstComment().Visible();
			}
		}), Input.wait30);

		getCodingForm_FirstComment().Click();
		getCodingForm_Comment(comments).Click();
		getCodingForm_AddToFormButton().Click();
		getSaveCFBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_Validation_ButtonYes().Visible();
			}
		}), Input.wait30);
		getCodingForm_Validation_ButtonYes().Click();

		System.out.println("Coding form " + cfName + " created");

		base.VerifySuccessMessage("Coding Form Saved successfully");
		base.CloseSuccessMsgpopup();
	}

	public void PreviewCodingform(String cfName, final String Comments) {

		getCF_PreviewButton().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCF_Comment(Comments).Visible();
			}
		}), Input.wait30);

		getCF_Comment(Comments).Displayed();
	}

	public void AddCodingformwithTag(String cfName, String tag) {

		getAddNewCodingFormBtn().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingFormName().Visible();
			}
		}), Input.wait30);
		getCodingFormName().SendKeys(cfName);
		getCodingForm_Tag(tag).waitAndClick(10);

		getCodingForm_AddToFormButton().Click();

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_ErrorMsg().Visible();
			}
		}), Input.wait30);
		getCodingForm_ErrorMsg().SendKeys("Error for testing");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_HelpText().Visible();
			}
		}), Input.wait30);
		getCodingForm_HelpText().SendKeys("Help for testing");

		getCF_AddLogicButton().waitAndClick(5);

		driver.scrollPageToTop();

		getSaveCFBtn().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCF_ValidationAlert().Visible();
			}
		}), Input.wait30);
		String text = getCF_ValidationAlert().getText();
		System.out.println(text.substring(19, 59));
		Assert.assertEquals("Please select Field Logic Action for TAG", text.substring(19, 59));
		getCodingForm_Validation_ButtonYes().Click();

		System.out.println("Coding form " + cfName + " created");

		base.VerifySuccessMessage("Coding Form Saved successfully");
		base.CloseSuccessMsgpopup();
	}

	public void AddCodingformwithComment(String cfName, String comment) {

		getAddNewCodingFormBtn().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingFormName().Visible();
			}
		}), Input.wait30);
		getCodingFormName().SendKeys(cfName);
		getCodingForm_CommentTab().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_FirstComment().Visible();
			}
		}), Input.wait30);
		getCodingForm_Comment(comment).Click();
		getCodingForm_AddToFormButton().Click();

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_ErrorMsg().Visible();
			}
		}), Input.wait30);
		getCodingForm_ErrorMsg().SendKeys("Error for testing");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_HelpText().Visible();
			}
		}), Input.wait30);
		getCodingForm_HelpText().SendKeys("Help for testing");

		getSaveCFBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_Validation_ButtonYes().Visible();
			}
		}), Input.wait30);
		getCodingForm_Validation_ButtonYes().Click();

		System.out.println("Coding form " + cfName + " created");

		base.VerifySuccessMessage("Coding Form Saved successfully");
		base.CloseSuccessMsgpopup();
	}

	public void AddCodingformwithMetadata(final String cfName) {

		getAddNewCodingFormBtn().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingFormName().Visible();
			}
		}), Input.wait30);
		getCodingFormName().SendKeys(cfName);

		getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(10);

		getCF_Metadata().waitAndClick(5);

		getCodingForm_AddToFormButton().Click();

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_ErrorMsg().Visible();
			}
		}), Input.wait30);
		getCodingForm_ErrorMsg().SendKeys("Error for testing");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_HelpText().Visible();
			}
		}), Input.wait30);
		getCodingForm_HelpText().SendKeys("Help for testing");

		getCF_AddLogicButton().waitAndClick(5);

		driver.scrollPageToTop();

		getSaveCFBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_Validation_ButtonYes().Visible();
			}
		}), Input.wait30);
		getCodingForm_Validation_ButtonYes().Click();

		System.out.println("Coding form " + cfName + " created");

		base.VerifySuccessMessage("Coding Form Saved successfully");
		base.CloseSuccessMsgpopup();

		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_NumberToShow().Visible();
			}
		}), Input.wait60);
		getCodingForm_NumberToShow().selectFromDropdown().selectByVisibleText("100");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_EditButton(cfName).Visible();
			}
		}), Input.wait60);
		getCodingForm_EditButton(cfName).waitAndClick(10);

		getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(10);

		softAssertion.assertEquals(getCodingForm_MetadataField().GetAttribute("disabled"), "disabled");
	}

	public void AddCodingformwithSpecialObjects(String cfName) {

		getAddNewCodingFormBtn().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingFormName().Visible();
			}
		}), Input.wait30);
		getCodingFormName().SendKeys(cfName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCF_StaticTextObject().Visible();
			}
		}), Input.wait30);
		getCF_StaticTextObject().Click();

		getCF_RadioGrpObject().waitAndClick(5);

		getCF_CheckGrpObject().waitAndClick(5);

		getCodingForm_AddToFormButton().Click();

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_ErrorMsg1().Visible();
			}
		}), Input.wait30);
		getCodingForm_ErrorMsg1().SendKeys("Error for testing radio group1");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_ErrorMsg2().Visible();
			}
		}), Input.wait30);
		getCodingForm_ErrorMsg2().SendKeys("Error for testing check group");

		driver.scrollPageToTop();

		getCodingForm_FirstTag().waitAndClick(10);

		getCodingForm_AddToFormButton().Click();

		driver.scrollingToBottomofAPage();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCF_TagType().Visible();
			}
		}), Input.wait30);
		getCF_TagType().selectFromDropdown().selectByVisibleText("Radio Item");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCF_TagGrpAssociation().Visible();
			}
		}), Input.wait30);
		getCF_TagGrpAssociation().selectFromDropdown().selectByIndex(0);

		driver.scrollPageToTop();

		getCF_PreviewButton().waitAndClick(10);

		System.out.println(getCF_Preview1().GetAttribute("type"));
		softAssertion.assertEquals(getCF_Preview1().GetAttribute("type"), "radio");
		softAssertion.assertTrue(getCF_Preview1().Displayed());
		softAssertion.assertFalse(getCF_Preview1().Enabled());

		softAssertion.assertTrue(getCodingForm_TagNames(1).Displayed() && getCodingForm_TagNames(1).Enabled());
		softAssertion.assertTrue(getCodingForm_TagNames(2).Displayed());
		softAssertion.assertFalse(getCodingForm_TagNames(2).Enabled());
		softAssertion.assertTrue(getCodingForm_MandField().Displayed());

		getSaveCFBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_Validation_ButtonYes().Visible();
			}
		}), Input.wait30);
		getCodingForm_Validation_ButtonYes().Click();

		System.out.println("Coding form " + cfName + " created");

		base.VerifySuccessMessage("Coding Form Saved successfully");
		base.CloseSuccessMsgpopup();
	}

	/*
	 * public void PreviewValidationswithmultipletags(String cfName) {
	 * 
	 * getAddNewCodingFormBtn().waitAndClick(10); driver.WaitUntil((new
	 * Callable<Boolean>() {public Boolean call(){return
	 * getCodingFormName().Visible() ;}}),Input.wait30);
	 * getCodingFormName().SendKeys(cfName); for(int i=1;i<=5;i++) {
	 * getCodingForm_MultipleTags(i).Click(); }
	 * 
	 * getCodingForm_AddToFormButton().Click();
	 * 
	 * for(int i=1;i<=5;i++) { if(i==1)
	 * getCodingForm_DefaultAction(i).selectFromDropdown().
	 * selectByVisibleText("Make It Hidden"); String tag1 =
	 * getCodingForm_TagNames(i).getText(); System.out.println(tag1); if(i==2)
	 * getCodingForm_DefaultAction(i).selectFromDropdown().
	 * selectByVisibleText("Make It Display But Not Selectable"); String tag2 =
	 * getCodingForm_TagNames(i).getText(); System.out.println(tag2); if(i==3)
	 * getCodingForm_DefaultAction(i).selectFromDropdown().
	 * selectByVisibleText("Make It Required"); String tag3 =
	 * getCodingForm_TagNames(i).getText(); System.out.println(tag3); }
	 * 
	 * driver.scrollPageToTop();
	 * 
	 * getCF_PreviewButton().waitAndClick(10);
	 * 
	 * softAssertion.assertTrue(getCodingForm_TagNames(1).Displayed() &&
	 * getCodingForm_TagNames(1).Enabled() );
	 * softAssertion.assertTrue(getCodingForm_TagNames(2).Displayed());
	 * softAssertion.assertFalse(getCodingForm_TagNames(2).Enabled());
	 * softAssertion.assertTrue(getCodingForm_MandField().Displayed());
	 * 
	 * }
	 */

	// String cfName,String ObjectName,String TagType,String Action
	public void PreviewValidations(String cfName, String ObjectName, String TagType, String Action)
			throws InterruptedException {

		getAddNewCodingFormBtn().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingFormName().Visible();
			}
		}), Input.wait30);
		getCodingFormName().SendKeys(cfName);
		switch (ObjectName) {
		case "tag":
			if (ObjectName.equalsIgnoreCase("tag"))
				getCodingForm_FirstTag().waitAndClick(10);
		case "comment":
			if (ObjectName.equalsIgnoreCase("comment")) {
				getCodingForm_CommentTab().waitAndClick(10);
				getCodingForm_FirstComment().waitAndClick(10);
			}
		case "metadata":
			if (ObjectName.equalsIgnoreCase("metadata")) {
				getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(10);
				getCodingForm_FirstMetadata().waitAndClick(10);
			}
		}
		getCodingForm_AddToFormButton().waitAndClick(10);

		if (ObjectName.equalsIgnoreCase("tag")) {
			switch (TagType) {
			case "check item":
				if (TagType.equalsIgnoreCase("check item"))
					getCF_TagTypes().selectFromDropdown().selectByVisibleText("Check Item");
			case "radio item":
				if (TagType.equalsIgnoreCase("radio item")) {
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
		} catch (Exception e) {
			System.out.println("Action not enabled");
		}
		driver.scrollPageToTop();

		getCF_PreviewButton().waitAndClick(10);

		if (ObjectName.equalsIgnoreCase("tag") && TagType.equalsIgnoreCase("check item")
				&& Action.equalsIgnoreCase("Make It Optional")) {
			System.out.println(getCF_Preview1().GetAttribute("class"));
			softAssertion.assertEquals(getCF_Preview1().GetAttribute("type"), "checkbox");
			softAssertion.assertTrue(getCF_Preview1().Displayed() && getCodingForm_TagNames(1).Enabled());
		} else if (ObjectName.equalsIgnoreCase("tag") && TagType.equalsIgnoreCase("radio item")
				&& Action.equalsIgnoreCase("Make It Optional")) {
			System.out.println(getCF_Preview1().GetAttribute("type"));
			softAssertion.assertEquals(getCF_Preview1().GetAttribute("type"), "radio");
			softAssertion.assertTrue(getCF_Preview1().Displayed() && getCodingForm_TagNames(1).Enabled());
		} else if (ObjectName.equalsIgnoreCase("tag") && TagType.equalsIgnoreCase("check item")
				&& Action.equalsIgnoreCase("Make It Hidden")) {
			// softAssertion.assertFalse(getCF_Preview1().Displayed());
			try {
				getCF_Preview1().Displayed();
				Assert.assertFalse(1 == 0);
			} catch (org.openqa.selenium.NoSuchElementException e) {
				System.out.println("Tag item not displayed");
			}
		} else if (ObjectName.equalsIgnoreCase("tag") && TagType.equalsIgnoreCase("radio item")
				&& Action.equalsIgnoreCase("Make It Display But Not Selectable")) {
			System.out.println(getCF_Preview1().GetAttribute("type"));
			softAssertion.assertEquals(getCF_Preview1().GetAttribute("type"), "radio");
			softAssertion.assertTrue(getCF_Preview1().Displayed());
			// softAssertion.assertFalse(getCF_Preview1().Enabled());
			try {
				getCF_Preview1().Enabled();
				Assert.assertFalse(1 == 0);
			} catch (org.openqa.selenium.NoSuchElementException e) {
				System.out.println("Tag item not displayed");
			}
		}

		else if (ObjectName.equalsIgnoreCase("tag") && TagType.equalsIgnoreCase("check item")
				&& Action.equalsIgnoreCase("Make It Required")) {
			System.out.println(getCF_Preview1().GetAttribute("type"));
			softAssertion.assertEquals(getCF_Preview1().GetAttribute("type"), "checkbox");
			softAssertion.assertTrue(getCF_Preview1().Displayed());
			softAssertion.assertTrue(getCodingForm_MandField().Displayed());
		}

		if (ObjectName.equalsIgnoreCase("comment") && Action.equalsIgnoreCase("Make It Optional")) {
			softAssertion.assertTrue(getCF_Preview1().Displayed() && getCodingForm_TagNames(1).Enabled());
		} else if (ObjectName.equalsIgnoreCase("comment") && Action.equalsIgnoreCase("Make It Required")) {
			softAssertion.assertTrue(getCF_Preview1().Displayed());
			softAssertion.assertTrue(getCodingForm_MandField().Displayed());
		} else if (ObjectName.equalsIgnoreCase("comment") && Action.equalsIgnoreCase("Make It Hidden")) {
			softAssertion.assertFalse(getCF_Preview1().Displayed());
		} else if (ObjectName.equalsIgnoreCase("comment")
				&& Action.equalsIgnoreCase("Make It Display But Not Selectable")) {
			softAssertion.assertTrue(getCF_Preview1().Displayed());
			softAssertion.assertFalse(getCF_Preview1().Enabled());
		}
		if (ObjectName.equalsIgnoreCase("metadata") && Action.equalsIgnoreCase("Make It Optional")) {
			softAssertion.assertTrue(getCF_Preview1().Displayed() && getCodingForm_TagNames(1).Enabled());
		} else if (ObjectName.equalsIgnoreCase("metadata") && Action.equalsIgnoreCase("Make It Required")) {
			softAssertion.assertTrue(getCF_Preview1().Displayed());
			softAssertion.assertTrue(getCodingForm_MandField().Displayed());
		} else if (ObjectName.equalsIgnoreCase("metadata") && Action.equalsIgnoreCase("Make It Hidden")) {
			softAssertion.assertFalse(getCF_Preview1().Displayed());
		} else if (ObjectName.equalsIgnoreCase("metadata")
				&& Action.equalsIgnoreCase("Make It Display But Not Selectable")) {
			softAssertion.assertTrue(getCF_Preview1().Displayed());
			softAssertion.assertFalse(getCF_Preview1().Enabled());
		}
	}

	// Code added by Narendra

	public void codingForm() {

		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCFlistTable().Visible();
			}
		}), Input.wait30);
		// System.out.println(getCFnames().size());
		boolean nextPage = true;

		while (nextPage) {

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getTable().Visible();
				}
			}), Input.wait30);
			// System.out.println("Number of records in a current page :
			// "+getTable().size());
			List<String> tableele = new ArrayList<String>();
			List<WebElement> table = getTable().FindWebElements();
			for (int i = 0; i < getTable().size(); i++) {
				tableele.add(table.get(i).getText());
				if (tableele.contains("Default Project Coding Form")) {
					Assert.assertTrue(tableele.contains("Default Project Coding Form"));
					System.out.println("Verified provisioned CF is available in the Project");
				}

			}
			try {
				driver.scrollingToBottomofAPage();
				driver.getWebDriver().findElement(By.xpath("//li[@class='paginate_button next disabled']/a"))
						.isDisplayed();
				nextPage = false;
			} catch (Exception e) {
				driver.getWebDriver().findElement(By.linkText("Next")).click();
			}

		}
	}

	public void codingFormTagsOrder() throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCFlistTable().Visible();
			}
		}), Input.wait30);
		// System.out.println(getCFnames().size());
		boolean nextPage = true;

		while (nextPage) {

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getTable().Visible();
				}
			}), Input.wait30);
			// System.out.println("Number of records in a current page :
			// "+getTable().size());
			List<String> tableele = new ArrayList<String>();
			List<WebElement> table = getTable().FindWebElements();
			for (int i = 0; i < getTable().size(); i++) {
				tableele.add(table.get(i).getText());
				if (tableele.contains("Default Project Coding Form")) {
					getEditClick().waitAndClick(10);

					// for (int j = 0; j<getnesTable().size();j++) {
					getupClick1().waitAndClick(10);
					String getStaticTxt = getStaticText().GetAttribute("Value");
					Assert.assertEquals(getStaticTxt, expectedStaticText);
					System.out.println("Verified Static Text");
					Thread.sleep(3000);

					getupClick2().waitAndClick(10);
					String getGroupTxt = getRadioGroupLabel().GetAttribute("Value");
					Assert.assertEquals(getGroupTxt, expectedGroupText);

					String getInstructionTxt = getInstructionText().GetAttribute("Value");
					Assert.assertEquals(getInstructionTxt, expectedInstructionText);

					WebElement optionG = getRGDefaultAction().selectFromDropdown().getFirstSelectedOption();
					String getGDefaultActionTxt = optionG.getText();
					Assert.assertEquals(getGDefaultActionTxt, expectedDefaultAction1Text);

					String getErrorMessageTxt = getErrorMessage().GetAttribute("Value");
					Assert.assertEquals(getErrorMessageTxt, expectedGErrorMsgText);
					System.out.println("Verified Responsive Group");
					Thread.sleep(3000);

					getupClick3().waitAndClick(10);
					String getRTagLabelTxt = getRTagLabel().GetAttribute("Value");
					Assert.assertEquals(getRTagLabelTxt, expectedRTagLabelText);

					WebElement optionRT = getRTagType().selectFromDropdown().getFirstSelectedOption();
					String getRTagTypeTxt = optionRT.getText();
					Assert.assertEquals(getRTagTypeTxt, expectedTagType1Text);

					WebElement optionRA = getRGroupAssociation().selectFromDropdown().getFirstSelectedOption();
					String getRGroupAssociationTxt = optionRA.getText();
					Assert.assertEquals(getRGroupAssociationTxt, expectedGroupAssociation1Text);

					String getRDefaultAction2Txt = getRDefaultAction().getText();
					Assert.assertEquals(getRDefaultAction2Txt, expectedDefaultActionText);

					String getRFieldLogicTxt = getRFieldLogic().getText();
					Assert.assertEquals(getRFieldLogicTxt, expectedFieldLogicText);

					String getRErrorMessage2Txt = getRErrorMessage().getText();
					Assert.assertEquals(getRErrorMessage2Txt, expectedErrorMsgText);
					System.out.println("Verified Responsive");
					Thread.sleep(3000);

					getupClick4().waitAndClick(10);
					String getNRTagLabelTxt = getNRTagLabel().GetAttribute("Value");
					Assert.assertEquals(getNRTagLabelTxt, expectedNRTagLabelText);

					WebElement optionNRT = getNRTagType().selectFromDropdown().getFirstSelectedOption();
					String getNRTagTypeTxt = optionNRT.getText();
					Assert.assertEquals(getNRTagTypeTxt, expectedTagType1Text);

					WebElement optionNRA = getNRGroupAssociation().selectFromDropdown().getFirstSelectedOption();
					String getNRGroupAssociationTxt = optionNRA.getText();
					Assert.assertEquals(getNRGroupAssociationTxt, expectedGroupAssociation1Text);

					String getNRDefaultAction2Txt = getNRDefaultAction().getText();
					Assert.assertEquals(getNRDefaultAction2Txt, expectedDefaultActionText);

					String getNRFieldLogicTxt = getNRFieldLogic().getText();
					Assert.assertEquals(getNRFieldLogicTxt, expectedFieldLogicText);

					String getNRErrorMessage2Txt = getNRErrorMessage().getText();
					Assert.assertEquals(getNRErrorMessage2Txt, expectedErrorMsgText);
					System.out.println("Verified Not Responsive");
					Thread.sleep(3000);

					getupClick5().waitAndClick(10);
					String getTITagLabelTxt = getTITagLabel().GetAttribute("Value");
					Assert.assertEquals(getTITagLabelTxt, expectedTITagLabelText);

					WebElement optionTIT = getTITagType().selectFromDropdown().getFirstSelectedOption();
					String getTITagTypeTxt = optionTIT.getText();
					Assert.assertEquals(getTITagTypeTxt, expectedTagType1Text);

					WebElement optionTIA = getTIGroupAssociation().selectFromDropdown().getFirstSelectedOption();
					String getTIGroupAssociationTxt = optionTIA.getText();
					Assert.assertEquals(getTIGroupAssociationTxt, expectedGroupAssociation1Text);

					String getTIDefaultActionTxt = getTIDefaultAction().getText();
					Assert.assertEquals(getTIDefaultActionTxt, expectedDefaultActionText);

					String getTiFieldLogicTxt = getTIFieldLogic().getText();
					Assert.assertEquals(getTiFieldLogicTxt, expectedFieldLogicText);

					String getTIErrorMessageTxt = getTIErrorMessage().getText();
					Assert.assertEquals(getTIErrorMessageTxt, expectedErrorMsgText);

					String getTIHelpTxt = getTIHelpText().GetAttribute("Value");
					Assert.assertEquals(getTIHelpTxt, expectedTIHelpText);
					System.out.println("Verified Technical_Issue");
					Thread.sleep(3000);

					getupClick6().waitAndClick(10);
					String getTIGGroupLabelTxt = getTIGCHECKGROUPLabel().GetAttribute("Value");
					Assert.assertEquals(getTIGGroupLabelTxt, expectedTIGCHECKGROUPLabel);

					String getTIGInstructionTxt = getTIGInstructionText().GetAttribute("Value");
					Assert.assertEquals(getTIGInstructionTxt, expectedTIGInstructionText);

					WebElement optionTIG = getTIGDefaultAction().selectFromDropdown().getFirstSelectedOption();
					String getTIGDefaultActionTxt = optionTIG.getText();
					Assert.assertEquals(getTIGDefaultActionTxt, expectedDefaultAction2Text);

					WebElement optionTIGO = getTIGObject().selectFromDropdown().getFirstSelectedOption();
					String getTIGObjectTxt = optionTIGO.getText();
					Assert.assertEquals(getTIGObjectTxt, expectedFieldLogicObjectText1);

					WebElement optionTIGC = getTIGCondition().selectFromDropdown().getFirstSelectedOption();
					String getTIGConditionTxt = optionTIGC.getText();
					Assert.assertEquals(getTIGConditionTxt, expectedFieldLogicConditionText);

					WebElement optionTIGA = getTIGAction().selectFromDropdown().getFirstSelectedOption();
					String getTIGActionTxt = optionTIGA.getText();
					Assert.assertEquals(getTIGActionTxt, expectedFieldLogicActionText);

					String getTIGErrorMessageTxt = getTIGErrorMessage().GetAttribute("Value");
					Assert.assertEquals(getTIGErrorMessageTxt, expectedTIGErrorMsgText);
					System.out.println("Verified Tech Issue Group");
					Thread.sleep(3000);

					getupClick7().waitAndClick(10);
					String getPITagLabelTxt = getPITagLabel().GetAttribute("Value");
					Assert.assertEquals(getPITagLabelTxt, expectedPITagLabelText);

					WebElement optionPIT = getPITagType().selectFromDropdown().getFirstSelectedOption();
					String getPITagTypeTxt = optionPIT.getText();
					Assert.assertEquals(getPITagTypeTxt, expectedTagType2Text);

					WebElement optionPIA = getPIGroupAssociation().selectFromDropdown().getFirstSelectedOption();
					String getPIGroupAssociationTxt = optionPIA.getText();
					Assert.assertEquals(getPIGroupAssociationTxt, expectedGroupAssociation2Text);

					String getPIDefaultActionTxt = getNRDefaultAction().getText();
					Assert.assertEquals(getPIDefaultActionTxt, expectedDefaultActionText);

					String getPIFieldLogicTxt = getNRFieldLogic().getText();
					Assert.assertEquals(getPIFieldLogicTxt, expectedFieldLogicText);

					String getPIErrorMessageTxt = getNRErrorMessage().getText();
					Assert.assertEquals(getPIErrorMessageTxt, expectedErrorMsgText);

					String getPIHelpTxt = getPIHelpText().GetAttribute("Value");
					Assert.assertEquals(getPIHelpTxt, expectedPIHelpText);
					System.out.println("Verified Processing Issue");
					Thread.sleep(3000);

					getupClick8().waitAndClick(10);
					String getFLTagLabelTxt = getFLTagLabel().GetAttribute("Value");
					Assert.assertEquals(getFLTagLabelTxt, expectedFLTagLabelText);

					WebElement optionFLT = getFLTagType().selectFromDropdown().getFirstSelectedOption();
					String getFLTagTypeTxt = optionFLT.getText();
					Assert.assertEquals(getFLTagTypeTxt, expectedTagType2Text);

					WebElement optionFLA = getFLGroupAssociation().selectFromDropdown().getFirstSelectedOption();
					String getFLGroupAssociationTxt = optionFLA.getText();
					Assert.assertEquals(getFLGroupAssociationTxt, expectedGroupAssociation2Text);

					String getFLDefaultActionTxt = getFLDefaultAction().getText();
					Assert.assertEquals(getFLDefaultActionTxt, expectedDefaultActionText);

					String getFLFieldLogicTxt = getFLFieldLogic().getText();
					Assert.assertEquals(getFLFieldLogicTxt, expectedFieldLogicText);

					String getFLErrorMessageTxt = getFLErrorMessage().getText();
					Assert.assertEquals(getFLErrorMessageTxt, expectedErrorMsgText);

					String getFLHelpTxt = getFLHelpText().GetAttribute("Value");
					Assert.assertEquals(getFLHelpTxt, expectedFLHelpText);
					System.out.println("Verified Foreign Language");
					Thread.sleep(3000);

					getupClick9().waitAndClick(10);
					String getHCTagLabelTxt = getHCTagLabel().GetAttribute("Value");
					Assert.assertEquals(getHCTagLabelTxt, expectedHCTagLabelText);

					WebElement optionHCT = getHCTagType().selectFromDropdown().getFirstSelectedOption();
					String getHCTagTypeTxt = optionHCT.getText();
					Assert.assertEquals(getHCTagTypeTxt, expectedTagType2Text);

					WebElement optionHCA = getHCGroupAssociation().selectFromDropdown().getFirstSelectedOption();
					String getHCGroupAssociationTxt = optionHCA.getText();
					Assert.assertEquals(getHCGroupAssociationTxt, expectedGroupAssociation2Text);

					String getHCDefaultActionTxt = getHCDefaultAction().getText();
					Assert.assertEquals(getHCDefaultActionTxt, expectedDefaultActionText);

					String getHCFieldLogicTxt = getHCFieldLogic().getText();
					Assert.assertEquals(getHCFieldLogicTxt, expectedFieldLogicText);

					String getHCErrorMessageTxt = getHCErrorMessage().getText();
					Assert.assertEquals(getHCErrorMessageTxt, expectedErrorMsgText);

					String getHCHelpTxt = getHCHelpText().GetAttribute("Value");
					Assert.assertEquals(getHCHelpTxt, expectedHCHelpText);
					System.out.println("Verified Hidden Content");
					Thread.sleep(3000);

					getupClick10().waitAndClick(10);
					String getHDTagLabelTxt = getHDTagLabel().GetAttribute("Value");
					Assert.assertEquals(getHDTagLabelTxt, expectedHDTagLabelText);

					WebElement optionHDT = getHDTagType().selectFromDropdown().getFirstSelectedOption();
					String getHDTagTypeTxt = optionHDT.getText();
					Assert.assertEquals(getHDTagTypeTxt, expectedTagType2Text);

					WebElement optionHDA = getHDGroupAssociation().selectFromDropdown().getFirstSelectedOption();
					String getHDGroupAssociationTxt = optionHDA.getText();
					Assert.assertEquals(getHDGroupAssociationTxt, expectedGroupAssociation6Text);

					WebElement optionHDDA = getHDDefaultAction().selectFromDropdown().getFirstSelectedOption();
					String getHDDefaultActionTxt = optionHDDA.getText();
					Assert.assertEquals(getHDDefaultActionTxt, expectedDefaultAction3Text);

					String getHDHelpTxt = getHDHelpText().GetAttribute("Value");
					Assert.assertEquals(getHDHelpTxt, expectedHDHelpText);
					System.out.println("Verified Hot Doc");
					Thread.sleep(3000);

					getupClick11().waitAndClick(10);
					String getPGTagLabelTxt = getPGTagLabel().GetAttribute("Value");
					Assert.assertEquals(getPGTagLabelTxt, expectedPGTagLabelText);

					String getPGInstructionTxt = getPGInstructionText().GetAttribute("Value");
					Assert.assertEquals(getPGInstructionTxt, expectedPGInstructionText);

					WebElement optionPGDA = getPGDefaultAction().selectFromDropdown().getFirstSelectedOption();
					String getPGDefaultActionTxt = optionPGDA.getText();
					Assert.assertEquals(getPGDefaultActionTxt, expectedDefaultAction1Text);

					String getPGErrorMessageTxt = getPGErrorMessage().GetAttribute("Value");
					Assert.assertEquals(getPGErrorMessageTxt, expectedErrorMessageText);
					System.out.println("Verified Privileged Group");
					Thread.sleep(3000);

					getupClick12().waitAndClick(10);
					String getPTagLabelTxt = getPTagLabel().GetAttribute("Value");
					Assert.assertEquals(getPTagLabelTxt, expectedPTagLabelText);

					WebElement optionPT = getPTagType().selectFromDropdown().getFirstSelectedOption();
					String getPTagTypeTxt = optionPT.getText();
					Assert.assertEquals(getPTagTypeTxt, expectedTagType1Text);

					WebElement optionPA = getPGroupAssociation().selectFromDropdown().getFirstSelectedOption();
					String getPGroupAssociationTxt = optionPA.getText();
					Assert.assertEquals(getPGroupAssociationTxt, expectedGroupAssociation3Text);

					String getPDefaultActionTxt = getPDefaultAction().getText();
					Assert.assertEquals(getPDefaultActionTxt, expectedDefaultActionText);

					String getPFieldLogicTxt = getPFieldLogic().getText();
					Assert.assertEquals(getPFieldLogicTxt, expectedFieldLogicText);

					String getPErrorMessageTxt = getPErrorMessage().getText();
					Assert.assertEquals(getPErrorMessageTxt, expectedErrorMsgText);
					System.out.println("Verified Privileged");
					Thread.sleep(3000);

					getupClick13().waitAndClick(10);
					String getNPTagLabelTxt = getNPTagLabel().GetAttribute("Value");
					Assert.assertEquals(getNPTagLabelTxt, expectedNPTagLabelText);

					WebElement optionNPT = getNPTagType().selectFromDropdown().getFirstSelectedOption();
					String getNPTagTypeTxt = optionNPT.getText();
					Assert.assertEquals(getNPTagTypeTxt, expectedTagType1Text);

					WebElement optionNPA = getNPGroupAssociation().selectFromDropdown().getFirstSelectedOption();
					String getNPGroupAssociationTxt = optionNPA.getText();
					Assert.assertEquals(getNPGroupAssociationTxt, expectedGroupAssociation3Text);

					String getNPDefaultActionTxt = getNPDefaultAction().getText();
					Assert.assertEquals(getNPDefaultActionTxt, expectedDefaultActionText);

					String getNPFieldLogicTxt = getNPFieldLogic().getText();
					Assert.assertEquals(getNPFieldLogicTxt, expectedFieldLogicText);

					String getNPErrorMessageTxt = getNPErrorMessage().getText();
					Assert.assertEquals(getNPErrorMessageTxt, expectedErrorMsgText);
					System.out.println("Verified Not Privileged");
					Thread.sleep(3000);

					getupClick14().waitAndClick(10);
					String getPTGGroupLabelTxt = getPTGCHECKGROUPLabel().GetAttribute("Value");
					Assert.assertEquals(getPTGGroupLabelTxt, expectedPTGTagLabelText);

					String getPTGInstructionTxt = getPTGInstructionText().GetAttribute("Value");
					Assert.assertEquals(getPTGInstructionTxt, expectedPTGInstructionText);

					WebElement optionPTG = getPTGDefaultAction().selectFromDropdown().getFirstSelectedOption();
					String getPTGDefaultActionTxt = optionPTG.getText();
					Assert.assertEquals(getPTGDefaultActionTxt, expectedDefaultAction2Text);

					WebElement optionPTGO = getPTGObject().selectFromDropdown().getFirstSelectedOption();
					String getPTGObjectTxt = optionPTGO.getText();
					Assert.assertEquals(getPTGObjectTxt, expectedFieldLogicObjectText2);

					WebElement optionPTGC = getPTGCondition().selectFromDropdown().getFirstSelectedOption();
					String getPTGConditionTxt = optionPTGC.getText();
					Assert.assertEquals(getPTGConditionTxt, expectedFieldLogicConditionText);

					WebElement optionPTGA = getPTGAction().selectFromDropdown().getFirstSelectedOption();
					String getPTGOActionTxt = optionPTGA.getText();
					Assert.assertEquals(getPTGOActionTxt, expectedFieldLogicActionText);

					String getPTGErrorMessageTxt = getPTGErrorMessage().GetAttribute("Value");
					Assert.assertEquals(getPTGErrorMessageTxt, expectedErrorMessageText);
					System.out.println("Verified Privilege Type Group");
					Thread.sleep(3000);

					getupClick15().waitAndClick(10);
					String getACTagLabelTxt = getACTagLabel().GetAttribute("Value");
					Assert.assertEquals(getACTagLabelTxt, expectedACTagLabelText);

					WebElement optionACT = getACTagType().selectFromDropdown().getFirstSelectedOption();
					String getACTagTypeTxt = optionACT.getText();
					Assert.assertEquals(getACTagTypeTxt, expectedTagType2Text);

					WebElement optionACA = getACGroupAssociation().selectFromDropdown().getFirstSelectedOption();
					String getACGroupAssociationTxt = optionACA.getText();
					Assert.assertEquals(getACGroupAssociationTxt, expectedGroupAssociation5Text);

					String getACDefaultActionTxt = getACDefaultAction().getText();
					Assert.assertEquals(getACDefaultActionTxt, expectedDefaultActionText);

					String getACFieldLogicTxt = getACFieldLogic().getText();
					Assert.assertEquals(getACFieldLogicTxt, expectedFieldLogicText);

					String getACErrorMessageTxt = getACErrorMessage().getText();
					Assert.assertEquals(getACErrorMessageTxt, expectedErrorMsgText);
					System.out.println("Verified Attorney Client");
					Thread.sleep(3000);

					getupClick16().waitAndClick(10);
					String getAWPTagLabelTxt = getAWPTagLabel().GetAttribute("Value");
					Assert.assertEquals(getAWPTagLabelTxt, expectedAWPTagLabelText);

					WebElement optionAWPT = getAWPTagType().selectFromDropdown().getFirstSelectedOption();
					String getAWPTagTypeTxt = optionAWPT.getText();
					Assert.assertEquals(getAWPTagTypeTxt, expectedTagType2Text);

					WebElement optionAWPA = getAWPGroupAssociation().selectFromDropdown().getFirstSelectedOption();
					String getAWPGroupAssociationTxt = optionAWPA.getText();
					Assert.assertEquals(getAWPGroupAssociationTxt, expectedGroupAssociation5Text);

					String getAWPDefaultActionTxt = getAWPDefaultAction().getText();
					Assert.assertEquals(getAWPDefaultActionTxt, expectedDefaultActionText);

					String getAWPFieldLogicTxt = getAWPFieldLogic().getText();
					Assert.assertEquals(getAWPFieldLogicTxt, expectedFieldLogicText);

					String getAWPErrorMessageTxt = getAWPErrorMessage().getText();
					Assert.assertEquals(getAWPErrorMessageTxt, expectedErrorMsgText);
					System.out.println("Verified Attorney Work Product");
					Thread.sleep(3000);

					getupClick17().waitAndClick(10);
					String getCGTagLabelTxt = getCGTagLabel().GetAttribute("Value");
					Assert.assertEquals(getCGTagLabelTxt, expectedCGTagLabelText);

					String getCGInstructionTxt = getCGInstructionText().GetAttribute("Value");
					Assert.assertEquals(getCGInstructionTxt, expectedCGInstructionText);

					WebElement optionCGA = getCGDefaultAction().selectFromDropdown().getFirstSelectedOption();
					String getCGDefaultActionTxt = optionCGA.getText();
					Assert.assertEquals(getCGDefaultActionTxt, expectedDefaultAction3Text);
					System.out.println("Verified Confidentiality Group");
					Thread.sleep(3000);

					getupClick18().waitAndClick(10);
					String getCTagLabelTxt = getCTagLabel().GetAttribute("Value");
					Assert.assertEquals(getCTagLabelTxt, expectedCTagLabelText);

					WebElement optionCT = getCTagType().selectFromDropdown().getFirstSelectedOption();
					String getCTagTypeTxt = optionCT.getText();
					Assert.assertEquals(getCTagTypeTxt, expectedTagType1Text);

					WebElement optionCA = getCGroupAssociation().selectFromDropdown().getFirstSelectedOption();
					String getCGroupAssociationTxt = optionCA.getText();
					Assert.assertEquals(getCGroupAssociationTxt, expectedGroupAssociation4Text);

					String getCDefaultActionTxt = getCDefaultAction().getText();
					Assert.assertEquals(getCDefaultActionTxt, expectedDefaultActionText);

					String getCFieldLogicTxt = getCFieldLogic().getText();
					Assert.assertEquals(getCFieldLogicTxt, expectedFieldLogicText);

					String getCErrorMessageTxt = getCErrorMessage().getText();
					Assert.assertEquals(getCErrorMessageTxt, expectedErrorMsgText);
					System.out.println("Verified Confidential");
					Thread.sleep(3000);

					getupClick19().waitAndClick(10);
					String getHTagLabelTxt = getHTagLabel().GetAttribute("Value");
					Assert.assertEquals(getHTagLabelTxt, expectedHTagLabelText);

					WebElement optionHT = getHTagType().selectFromDropdown().getFirstSelectedOption();
					String getHTagTypeTxt = optionHT.getText();
					Assert.assertEquals(getHTagTypeTxt, expectedTagType1Text);

					WebElement optionHA = getHGroupAssociation().selectFromDropdown().getFirstSelectedOption();
					String getHGroupAssociationTxt = optionHA.getText();
					Assert.assertEquals(getHGroupAssociationTxt, expectedGroupAssociation4Text);

					String getHDefaultActionTxt = getHDefaultAction().getText();
					Assert.assertEquals(getHDefaultActionTxt, expectedDefaultActionText);

					String getHFieldLogicTxt = getHFieldLogic().getText();
					Assert.assertEquals(getHFieldLogicTxt, expectedFieldLogicText);

					String getHErrorMessageTxt = getHErrorMessage().getText();
					Assert.assertEquals(getHErrorMessageTxt, expectedErrorMsgText);
					System.out.println("Verified Highly Confidential");
					Thread.sleep(3000);

					getupClick20().waitAndClick(10);
					String getDCGroupLabelTxt = getDCCOMMENTLabel().GetAttribute("Value");
					Assert.assertEquals(getDCGroupLabelTxt, expectedDCTagLabelText);

					WebElement optionDC = getDCDefaultAction().selectFromDropdown().getFirstSelectedOption();
					String getDCDefaultActionTxt = optionDC.getText();
					Assert.assertEquals(getDCDefaultActionTxt, expectedDefaultAction3Text);

					WebElement optionDCO = getDCObject().selectFromDropdown().getFirstSelectedOption();
					String getDCObjectTxt = optionDCO.getText();
					Assert.assertEquals(getDCObjectTxt, expectedFieldLogicObjectText1);

					WebElement optionDCC = getDCCondition().selectFromDropdown().getFirstSelectedOption();
					String getDCConditionTxt = optionDCC.getText();
					Assert.assertEquals(getDCConditionTxt, expectedFieldLogicConditionText);

					WebElement optionDCA = getDCAction().selectFromDropdown().getFirstSelectedOption();
					String getDCActionTxt = optionDCA.getText();
					Assert.assertEquals(getDCActionTxt, expectedFieldLogicActionText);

					String getDCErrorMessageTxt = getDCErrorMessage().GetAttribute("Value");
					Assert.assertEquals(getDCErrorMessageTxt, expectedDCErrorMsgText);

					String getDCHelpTxt = getDCHelpText().GetAttribute("Value");
					Assert.assertEquals(getDCHelpTxt, expectedDCHelpText);
					System.out.println("Verified Document Comments");

					driver.scrollPageToTop();
					driver.WaitUntil((new Callable<Boolean>() {
						public Boolean call() {
							return getRootClick().Visible();
						}
					}), Input.wait30);
					getRootClick().waitAndClick(10);

					driver.WaitUntil((new Callable<Boolean>() {
						public Boolean call() {
							return getCF_PreviewButton().Visible();
						}
					}), Input.wait30);
					getCF_PreviewButton().waitAndClick(10);
					driver.WaitUntil((new Callable<Boolean>() {
						public Boolean call() {
							return getResponsiveCheked().Visible();
						}
					}), Input.wait30);
					getResponsiveCheked().waitAndClick(10);
					driver.WaitUntil((new Callable<Boolean>() {
						public Boolean call() {
							return getTestUrCodeClick().Visible();
						}
					}), Input.wait30);
					getTestUrCodeClick().waitAndClick(10);
					String getResponsiveError = getError1().getText();
					Assert.assertEquals(getResponsiveError, expectedErrorMessageText);

					driver.WaitUntil((new Callable<Boolean>() {
						public Boolean call() {
							return getNotResponsiveCheked().Visible();
						}
					}), Input.wait30);
					getNotResponsiveCheked().waitAndClick(10);
					driver.WaitUntil((new Callable<Boolean>() {
						public Boolean call() {
							return getTestUrCodeClick().Visible();
						}
					}), Input.wait30);
					getTestUrCodeClick().waitAndClick(10);
					String getNotResponsiveError = getError1().getText();
					Assert.assertEquals(getNotResponsiveError, expectedErrorMessageText);

					driver.WaitUntil((new Callable<Boolean>() {
						public Boolean call() {
							return getTechnicalIssueCheked().Visible();
						}
					}), Input.wait30);
					getTechnicalIssueCheked().waitAndClick(10);
					driver.WaitUntil((new Callable<Boolean>() {
						public Boolean call() {
							return getTestUrCodeClick().Visible();
						}
					}), Input.wait30);
					getTestUrCodeClick().waitAndClick(10);
					String getTechIssueError1 = getError2().getText();
					Assert.assertEquals(getTechIssueError1, expectedTIGErrorMsgText);

					String getTechIssueError2 = getError4().getText();
					Assert.assertEquals(getTechIssueError2, expectedErrorMessageText);

					String getTechIssueError3 = getError3().getText();
					Assert.assertEquals(getTechIssueError3, expectedDCErrorMsgText);
					System.out.println("Verified Preview");
				}

			}
			try {
				driver.scrollingToBottomofAPage();
				driver.getWebDriver().findElement(By.xpath("//li[@class='paginate_button next disabled']/a"))
						.isDisplayed();
				nextPage = false;
			} catch (Exception e) {
				driver.getWebDriver().findElement(By.linkText("Next")).click();
			}

		}
	}

	/**
	 * @author Indium-Baskar date: 10/8/2021 Modified date: 23/8/2021 Modified
	 *         by:Baskar.
	 * @Description:Add project field with metadata value
	 */
	public void addProjectFieldMetaData(String cfName, String fieldText) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		base.waitForElement(getManageCodingFormButton());
		base.waitForElement(getCodingForm_Search());
		getCodingForm_Search().SendKeys(cfName);
		base.waitForElement(getCodingForm_EditButton(cfName));
		base.waitTillElemetToBeClickable(getCodingForm_EditButton(cfName));
		getCodingForm_EditButton(cfName).waitAndClick(5);
		base.waitForElement(getCodingForm_EDITABLE_METADATA_Tab());
		base.waitTillElemetToBeClickable(getCodingForm_EDITABLE_METADATA_Tab());
		getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(5);
		base.waitForElement(getCodingForm_MetaData(fieldText));
		base.waitTillElemetToBeClickable(getCodingForm_MetaData(fieldText));
		getCodingForm_MetaData(fieldText).waitAndClick(5);
		base.waitForElement(getCodingForm_AddToFormButton());
		base.waitTillElemetToBeClickable(getCodingForm_AddToFormButton());
		getCodingForm_AddToFormButton().waitAndClick(5);
		driver.scrollingToBottomofAPage();
		try {
			base.waitForElement(getDefaultActionDropdown());
			getDefaultActionDropdown().selectFromDropdown().selectByVisibleText("Make It Display But Not Selectable");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		driver.scrollPageToTop();
		base.waitForElement(getSaveCFBtn());
		base.waitTillElemetToBeClickable(getSaveCFBtn());
		getSaveCFBtn().waitAndClick(5);
		base.waitForElement(getCodingForm_Validation_ButtonYes());
		base.waitTillElemetToBeClickable(getCodingForm_Validation_ButtonYes());
		getCodingForm_Validation_ButtonYes().waitAndClick(5);
	}

	/**
	 * @author Indium-Baskar Description: Selected condition in logic rule
	 */
	public void addCodingFormWithTagSelectedCondition(String cfName) {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddNewCodingFormBtn().Displayed() && getAddNewCodingFormBtn().Enabled();
			}
		}), Input.wait30);
		getAddNewCodingFormBtn().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingFormName().Displayed() && getCodingFormName().Enabled();
			}
		}), Input.wait30);
		getCodingFormName().SendKeys(cfName);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_FirstTag().Displayed();
			}
		}), Input.wait30);
		getCodingForm_FirstTag().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCF_SecondTag().Displayed();
			}
		}), Input.wait30);
		getCF_SecondTag().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_AddToFormButton().Visible() && getCodingForm_AddToFormButton().Enabled();
			}
		}), Input.wait60);
		getCodingForm_AddToFormButton().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDefaultActionDropdown().Displayed();
			}
		}), Input.wait60);
		getDefaultActionDropdown().selectFromDropdown().selectByVisibleText("Make It Required");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCF_AddLogicButton().Displayed();
			}
		}), Input.wait60);
		getCF_AddLogicButton().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCF_Object1().Displayed();
			}
		}), Input.wait60);
		getCF_Object1().selectFromDropdown().selectByVisibleText("ATag4425702(TAG_62)");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCF_FieldLogicCondition().Displayed();
			}
		}), Input.wait60);
		getCF_FieldLogicCondition().selectFromDropdown().selectByVisibleText("Selected");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCF_FieldLogicAction().Displayed();
			}
		}), Input.wait60);
		getCF_FieldLogicAction().selectFromDropdown().selectByVisibleText("Make this Hidden");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_ErrorMsg().Visible();
			}
		}), Input.wait30);
		getCodingForm_ErrorMsg().SendKeys("Error for testing");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingForm_HelpText().Visible();
			}
		}), Input.wait30);
		getCodingForm_HelpText().SendKeys("Help for testing");
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSaveCFBtn().Visible() && getSaveCFBtn().Enabled();
			}
		}), Input.wait30);
		getSaveCFBtn().Click();
		getCodingForm_Validation_ButtonYes().Click();
		System.out.println("Coding form " + cfName + " created");
		base.VerifySuccessMessage("Coding Form Saved successfully");
		base.CloseSuccessMsgpopup();
	}

	/**
	 * <h1>Method for search code form by using search text field and edit code
	 * form</h1> <b>Description:</b> Method for searching code form by using code
	 * name on search box text field to get in manage coding forms table..
	 * 
	 * @author : Gopinath Created date: NA Modified date: NA Modified by:Gopinath.
	 * @param codeFormName     : codeFormName is a string value that need to search
	 *                         for codeing form.
	 * @param projectFieldName : projectFieldName is a string value that name of
	 *                         project field.
	 */
	public void searchAndEditCodeForm(String codeFormName, String projectFieldName) {
		try {
			driver.getWebDriver().get(Input.url + "CodingForm/Create");
			base.waitTime(3);
			base.waitForElement(getCodingForm_SearchBox());
			getCodingForm_SearchBox().SendKeys(codeFormName);
			// getCodingForm_SearchButton().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getCodingForm_EditButton(codeFormName));
			getCodingForm_EditButton(codeFormName).isElementAvailable(15);
			getCodingForm_EditButton(codeFormName).Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getCodingForm_EDITABLE_METADATA_Tab());
			getCodingForm_EDITABLE_METADATA_Tab().Click();
			driver.waitForPageToBeReady();
			base.waitForElement(getEditableMetaData(projectFieldName));
			getEditableMetaData(projectFieldName).Click();
			getCodingForm_AddToFormButton().Click();
			getSaveCFBtn().Click();
			try {
				base.waitForElement(getCodingForm_Validation_ButtonYes());
				getCodingForm_Validation_ButtonYes().Click();
			} catch (Exception e) {
				System.out.println("Pop up button not appeared");
				base.stepInfo("Pop up button not appeared");
			}
			base.VerifySuccessMessage("Coding Form updated successfully");
			base.CloseSuccessMsgpopup();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while clicking on project field link" + e.getMessage());

		}
	}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for clicking coding form tab based on parameter using string
	public void basedOnCodingFormParameter(String cfName, String tag, String comment, String metadata,
			String ObjectName) throws InterruptedException {
		driver.waitForPageToBeReady();
		base.waitForElement(getAddNewCodingFormBtn());
		getAddNewCodingFormBtn().waitAndClick(10);
		base.waitForElement(getCodingFormName());
		getCodingFormName().SendKeys(cfName);
		switch (ObjectName) {
		case "tag":
			if (ObjectName.equalsIgnoreCase("tag")) {
				base.waitForElement(getCodingForm_FirstTag());
				getCodingForm_FirstTag().waitAndClick(10);
				base.waitForElement(getCodingForm_Tag(tag));
				getCodingForm_Tag(tag).waitAndClick(10);
			}
		case "comment":
			if (ObjectName.equalsIgnoreCase("comment")) {
				base.waitForElement(getCodingForm_CommentTab());
				getCodingForm_CommentTab().waitAndClick(10);
//				base.waitForElement(getCodingForm_FirstComment());
//				getCodingForm_FirstComment().waitAndClick(10);
				base.waitForElement(getCodingForm_Comment(comment));
				getCodingForm_Comment(comment).waitAndClick(10);

			}
		case "metadata":
			if (ObjectName.equalsIgnoreCase("metadata")) {
				base.waitForElement(getCodingForm_EDITABLE_METADATA_Tab());
				getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(10);
				base.waitForElement(getCodingForm_FirstMetadata());
				getCodingForm_FirstMetadata().waitAndClick(10);
				base.waitForElement(getCodingForm_MetaData(metadata));
				getCodingForm_MetaData(metadata).waitAndClick(10);
			}
		}
	}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for Adding special object to coding form
	public void specialObjectToAddCodinForm(String ObjectName, String tagType) {
		if (ObjectName.equalsIgnoreCase("tag")) {
			switch (tagType) {
			case "check item":
				if (tagType.equalsIgnoreCase("check item"))
					getCF_TagTypes().selectFromDropdown().selectByVisibleText("Check Item");
			case "radio item":
				if (tagType.equalsIgnoreCase("radio item")) {
					getCF_RadioGrpObject().ScrollTo();
					base.waitForElement(getCF_RadioGrpObject());
					getCF_RadioGrpObject().waitAndClick(10);
				}

			}
		}
	}

	/**
	 * @author Indium-Baskar
	 */

//	Reusable method for Add to coding form
	public void addcodingFormAddButton() {
		base.waitForElement(getCodingForm_AddToFormButton());
		getCodingForm_AddToFormButton().waitAndClick(10);
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium-Baskar
	 */

//	Reusable method for assig coding form to SG
	public void assignCodingFormToSG(String cfName) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		base.waitForElement(getManageCodingFormButton());
		base.waitForElement(getSetCodingFormToSG());
		getSetCodingFormToSG().waitAndClick(15);
		if (assgnpage.SelectCFPopUpSG_Step1().isElementAvailable(2)) {
			base.stepInfo("Add / Remove Coding Forms in this Assignment Pop Up displayed.");
			base.waitForElement(assgnpage.getSelectCF_CheckBox(cfName));
			assgnpage.getSelectCF_CheckBox(cfName).ScrollTo();
			assgnpage.getSelectCF_CheckBox(cfName).waitAndClick(5);
			base.waitTime(1);
			assgnpage.getSelectCodeFormRadioBtn(cfName).waitAndClick(5);
			base.waitTime(2);
			assgnpage.sortOrderNxtBtn().ScrollTo();
			assgnpage.sortOrderNxtBtn().waitAndClick(5);
			base.waitForElement(assgnpage.getSelectedCodeForm_inSortingPopUp(cfName));
			if (assgnpage.getSelectedCodeForm_inSortingPopUp(cfName).isElementAvailable(2)) {
				assgnpage.sortCodeFormOrderSaveBtn().waitAndClick(5);
				base.waitForElement(getManageCodingFormButton());
				if(getManageCodingFormButton().Displayed()) {
					base.waitForElement(getCodingForm_Search());
					getCodingForm_Search().SendKeys(cfName);
				   System.out.println( getSFFormCol(cfName).getText());
				softAssertion.assertEquals("YES (Default)", getSFFormCol(cfName).getText());
				softAssertion.assertAll();
				base.passedStep("Selected a coding form and its reflected in manage coding form page as default");
		       }
		       else {base.failedStep("Selected  coding form is not reflected in manage coding form page as default");
		}
			}
				else {
			base.failedStep("Step-2 Sort CodeForm Pop Up Not displayed.");
		}
		}else {
			base.failedStep("Step-1 Select CodingForm Pop Up Not displayed.");
		}
			
		}

	

	/**
	 * @author Indium-Baskar date: 24/09/2021 Modified date: NA
	 * @Description : this method used for create coding form with two tags
	 */

	public void AddCodingFormWithTwoTag(String cfName, String tag, String comment, String ObjectName, String tagType)
			throws InterruptedException {
		basedOnCodingFormParameter(cfName, tag, comment, null, ObjectName);
		specialObjectToAddCodinForm(ObjectName, tagType);
		addcodingFormAddButton();
		try {
			getCodingForm_DefaultAction(0).ScrollTo();
			getCodingForm_DefaultAction(0).selectFromDropdown().selectByVisibleText("Make It Optional");
			Thread.sleep(2000);
			getCodingForm_DefaultAction(1).ScrollTo();
			getCodingForm_DefaultAction(1).selectFromDropdown()
					.selectByVisibleText("Make It Display But Not Selectable");
		} catch (Exception e) {
			System.out.println("Action not enabled");
		}
		driver.scrollPageToTop();
		base.waitForElement(getSaveCFBtn());
		getSaveCFBtn().waitAndClick(5);
		base.waitForElement(getCodingForm_Validation_ButtonYes());
		getCodingForm_Validation_ButtonYes().waitAndClick(5);
		base.VerifySuccessMessage("Coding Form Saved successfully");
		base.CloseSuccessMsgpopup();
	}

	/**
	 * @author Indium-Baskar date: 24/09/2021 Modified date: NA
	 * @return
	 * @Description : this method used for create coding form with Metadata
	 */

	public String addCodingFormWithAllTabsMetaDataError(String cfName, String tag, String comment)
			throws InterruptedException {
		addNewCodingFormButton();
		firstCheckBox("metadata");
		String MetaDatatext = getCFMetaFirstText().getText();
		addcodingFormAddButton();
		getCodingForm_DefaultAction(0).ScrollTo();
		getCodingForm_DefaultAction(0).selectFromDropdown().selectByVisibleText("Make It Required");
		base.waitForElement(getCodingForm_ErrorMsg());
		getCodingForm_ErrorMsg().SendKeys("Error for testing");
		base.waitForElement(getCodingForm_HelpText());
		getCodingForm_HelpText().SendKeys("Help for testing");
		driver.scrollPageToTop();
		passingCodingFormName(cfName);
		base.waitForElement(getSaveCFBtn());
		getSaveCFBtn().waitAndClick(5);
		base.waitForElement(getCodingForm_Validation_ButtonYes());
		getCodingForm_Validation_ButtonYes().waitAndClick(5);
		base.VerifySuccessMessage("Coding Form Saved successfully");
		base.CloseSuccessMsgpopup();
		return MetaDatatext;
	}

	/**
	 * @author Indium-Baskar date: 24/09/2021 Modified date: NA
	 * @Description : this method used for create coding form with comment
	 */

	public void addCodingFormWithComment(String cfName, String tag, String comment) throws InterruptedException {
		basedOnCodingFormParameter(cfName, tag, comment, null, "comment");
		addcodingFormAddButton();
		getCodingForm_DefaultAction(0).ScrollTo();
		getCodingForm_DefaultAction(0).selectFromDropdown().selectByVisibleText("Make It Display But Not Selectable");
		driver.scrollPageToTop();
		base.waitForElement(getSaveCFBtn());
		getSaveCFBtn().waitAndClick(5);
		base.waitForElement(getCodingForm_Validation_ButtonYes());
		getCodingForm_Validation_ButtonYes().waitAndClick(5);
		base.VerifySuccessMessage("Coding Form Saved successfully");
		base.CloseSuccessMsgpopup();

	}

	/**
	 * @author Indium-Baskar date: 24/09/2021 Modified date: NA
	 * @Description : this method used for create coding form with tag
	 */

	public void addCodingFormWithTag(String cfName, String tag, String comment, String objectName, String tagType,
			String action) throws InterruptedException {
		basedOnCodingFormParameter(cfName, tag, comment, null, objectName);
		addcodingFormAddButton();
		specialObjectToAddCodinForm(objectName, tagType);
		getCodingForm_DefaultAction(0).ScrollTo();
		getCodingForm_DefaultAction(0).selectFromDropdown().selectByVisibleText(action);
		driver.scrollPageToTop();
		base.waitForElement(getSaveCFBtn());
		getSaveCFBtn().waitAndClick(5);
		base.waitForElement(getCodingForm_Validation_ButtonYes());
		getCodingForm_Validation_ButtonYes().waitAndClick(5);
		base.VerifySuccessMessage("Coding Form Saved successfully");
		base.CloseSuccessMsgpopup();
		getCF_PreviewButton().waitAndClick(10);
		if (objectName.equalsIgnoreCase("tag") && tagType.equalsIgnoreCase("check item")
				&& action.equalsIgnoreCase("Make It Optional")) {
			softAssertion.assertTrue(getCF_Preview1().Displayed() && getCodingForm_TagNames(1).Enabled());
			base.stepInfo("Coding form created as per expected condition");
		}

	}

	/**
	 * @author Indium-Baskar date: 27/09/2021 Modified date: NA
	 * @Description : this method used for create coding form with tag
	 */

	public void addCodingFormWithCommentValidation(String cfName, String tag, String comment, String objectName,
			String tagType, String action) throws InterruptedException {
		basedOnCodingFormParameter(cfName, tag, comment, null, objectName);
		addcodingFormAddButton();
		getCodingForm_DefaultAction(0).ScrollTo();
		getCodingForm_DefaultAction(0).selectFromDropdown().selectByVisibleText(action);
		driver.scrollPageToTop();
		base.waitForElement(getCodingForm_ErrorMsg());
		getCodingForm_ErrorMsg().SendKeys("Error for testing");
		base.waitForElement(getCodingForm_HelpText());
		getCodingForm_HelpText().SendKeys("Help for testing");
		driver.scrollPageToTop();
		base.waitForElement(getSaveCFBtn());
		getSaveCFBtn().waitAndClick(5);
		base.waitForElement(getCodingForm_Validation_ButtonYes());
		getCodingForm_Validation_ButtonYes().waitAndClick(5);
		base.VerifySuccessMessage("Coding Form Saved successfully");
		base.CloseSuccessMsgpopup();
	}

	/**
	 * @author Indium-Baskar date: 24/09/2021 Modified date: NA
	 * @Description : this method used for create coding instruction text appearance
	 */

	public void addCodingFormWithTagInstructionText(String cfName, String tag, String comment, String objectName,
			String tagType, String action) throws InterruptedException {
		basedOnCodingFormParameter(cfName, tag, comment, null, objectName);
		specialObjectToAddCodinForm(objectName, tagType);
		addcodingFormAddButton();
		base.waitForElement(getCF_TagTypes());
		getCF_TagTypes().selectFromDropdown().selectByVisibleText("Radio Item");
		base.waitForElement(getCF_RadioGroup());
		getCF_RadioGroup().selectFromDropdown().selectByIndex(1);
		base.waitForElement(getTagType2());
		getTagType2().ScrollTo();
		getTagType2().selectFromDropdown().selectByVisibleText("Radio Item");
		base.waitForElement(getCF_RadioGroup2());
		getCF_RadioGroup2().ScrollTo();
		getCF_RadioGroup2().selectFromDropdown().selectByIndex(1);
		getInstructionText().ScrollTo();
		base.waitForElement(getCFInstructionText());
		getCFInstructionText().SendKeys("AppearAlwaysOnLeft");
		driver.scrollPageToTop();
		base.waitForElement(getSaveCFBtn());
		getSaveCFBtn().waitAndClick(5);
		base.waitForElement(getCodingForm_Validation_ButtonYes());
		getCodingForm_Validation_ButtonYes().waitAndClick(5);
		base.VerifySuccessMessage("Coding Form Saved successfully");
		base.CloseSuccessMsgpopup();

	}

	/**
	 * @author Indium-Baskar date: 24/09/2021 Modified date: NA
	 * @Description : this method used for create coding form static text
	 */

	public void addCodingFormWithStaticText(String cfName, String fieldValue) throws InterruptedException {
		driver.waitForPageToBeReady();
		base.waitForElement(getAddNewCodingFormBtn());
		getAddNewCodingFormBtn().waitAndClick(10);
		base.waitForElement(getCodingFormName());
		getCodingFormName().SendKeys(cfName);
		base.waitForElement(getCF_StaticTextObject());
		getCF_StaticTextObject().waitAndClick(10);
		addcodingFormAddButton();
		base.waitForElement(getStaticText());
		getStaticText().SendKeys(fieldValue);
		driver.scrollPageToTop();
		base.waitForElement(getSaveCFBtn());
		getSaveCFBtn().waitAndClick(5);
		base.waitForElement(getCodingForm_Validation_ButtonYes());
		getCodingForm_Validation_ButtonYes().waitAndClick(5);
		base.VerifySuccessMessage("Coding Form Saved successfully");
		base.CloseSuccessMsgpopup();

	}

	/**
	 * @author Indium-Baskar date: 24/09/2021 Modified date: NA
	 * @Description : this method used for create coding form and assign custom
	 *              fields
	 */

	public void creatingCodingFormAndAssgnCustomFields(String cfName, String fieldText) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		base.waitForElement(getAddNewCodingFormBtn());
		getAddNewCodingFormBtn().waitAndClick(10);
		base.waitForElement(getCodingFormName());
		getCodingFormName().SendKeys(cfName);
		base.waitForElement(getCodingForm_EDITABLE_METADATA_Tab());
		base.waitTillElemetToBeClickable(getCodingForm_EDITABLE_METADATA_Tab());
		getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(5);
		base.waitForElement(getCodingForm_MetaData(fieldText));
		base.waitTillElemetToBeClickable(getCodingForm_MetaData(fieldText));
		getCodingForm_MetaData(fieldText).waitAndClick(10);
		addcodingFormAddButton();
		try {
			getDefaultActionDropdown().ScrollTo();
			base.waitForElement(getDefaultActionDropdown());
			getDefaultActionDropdown().selectFromDropdown().selectByVisibleText("Make It Display But Not Selectable");
		} catch (Exception e) {
			e.printStackTrace();
		}
		driver.scrollPageToTop();
		base.waitForElement(getSaveCFBtn());
		base.waitTillElemetToBeClickable(getSaveCFBtn());
		getSaveCFBtn().waitAndClick(5);
		base.waitForElement(getCodingForm_Validation_ButtonYes());
		base.waitTillElemetToBeClickable(getCodingForm_Validation_ButtonYes());
		getCodingForm_Validation_ButtonYes().waitAndClick(5);
		driver.scrollPageToTop();
		base.VerifySuccessMessage("Coding Form Saved successfully");
		base.CloseSuccessMsgpopup();
	}

	/**
	 * @author Indium-Baskar date: 24/09/2021 Modified date: NA
	 * @Description : this method used for deleting the coding form
	 */

	public void deleteCodingForm(String cfName, String fieldValue) {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		getCodingForm_Search().SendKeys(cfName);
		driver.waitForPageToBeReady();
		base.waitForElement(getCodingForm_DeleteButton(fieldValue));
		getCodingForm_DeleteButton(fieldValue).waitAndClick(10);
		base.waitTime(4);
		if (getCodingForm_SGValidation_ButtonYes().isElementAvailable(1)) {
			base.waitForElement(getCodingForm_SGValidation_ButtonYes());
			getCodingForm_SGValidation_ButtonYes().waitAndClick(20);
		} else {
			System.out.println("Coding form Already deleted");
		}
	}

	/**
	 * @author Indium-Baskar date: 24/09/2021 Modified date: NA
	 * @Description : this method used for create coding form and assign custom
	 *              fields
	 */

	public void creatingCodingFormAndAssgnUsingParameter(String cfName, String fieldText, String roll)
			throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		base.waitForElement(getAddNewCodingFormBtn());
		getAddNewCodingFormBtn().waitAndClick(10);
		base.waitForElement(getCodingFormName());
		getCodingFormName().SendKeys(cfName);
		base.waitForElement(getCodingForm_EDITABLE_METADATA_Tab());
		base.waitTillElemetToBeClickable(getCodingForm_EDITABLE_METADATA_Tab());
		getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(5);
		base.waitForElement(getCodingForm_MetaData(fieldText));
		base.waitTillElemetToBeClickable(getCodingForm_MetaData(fieldText));
		getCodingForm_MetaData(fieldText).waitAndClick(10);
		addcodingFormAddButton();
		try {
			getDefaultActionDropdown().ScrollTo();
			base.waitForElement(getDefaultActionDropdown());
			getDefaultActionDropdown().selectFromDropdown().selectByVisibleText(roll);
		} catch (Exception e) {
			e.printStackTrace();
		}
		driver.scrollPageToTop();
		base.waitForElement(getSaveCFBtn());
		base.waitTillElemetToBeClickable(getSaveCFBtn());
		getSaveCFBtn().waitAndClick(5);
		base.waitForElement(getCodingForm_Validation_ButtonYes());
		base.waitTillElemetToBeClickable(getCodingForm_Validation_ButtonYes());
		getCodingForm_Validation_ButtonYes().waitAndClick(5);
		driver.scrollPageToTop();
		base.VerifySuccessMessage("Coding Form Saved successfully");
		base.CloseSuccessMsgpopup();
	}

	/**
	 * @author Indium-Baskar date: 24/09/2021 Modified date: NA
	 * @Description : this method used for create coding form using metadata
	 */
	public void metaDataValidation(String cdForm, String metadata, String action) throws InterruptedException {
		driver.waitForPageToBeReady();
		basedOnCodingFormParameter(cdForm, null, null, metadata, "metadata");
		addcodingFormAddButton();
		getCodingForm_DefaultAction(0).ScrollTo();
		getCodingForm_DefaultAction(0).selectFromDropdown().selectByVisibleText(action);
		getCodingForm_DefaultAction(1).ScrollTo();
		getCodingForm_DefaultAction(1).selectFromDropdown().selectByVisibleText(action);
		base.waitForElement(getCodingForm_ErrorMsg());
		getCodingForm_ErrorMsg().SendKeys("Error for testing");
		base.waitForElement(getCodingForm_HelpText());
		getCodingForm_HelpText().SendKeys("Help for testing");
		driver.scrollPageToTop();
		base.waitForElement(getSaveCFBtn());
		getSaveCFBtn().waitAndClick(5);
		base.waitForElement(getCodingForm_Validation_ButtonYes());
		getCodingForm_Validation_ButtonYes().waitAndClick(5);
		base.VerifySuccessMessage("Coding Form Saved successfully");
		base.CloseSuccessMsgpopup();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description : this method used for to add first two objects to coding form
	 */
	public void createCodingFormUsingTwoObjects(String cfName, String tag, String comment, String metadata,
			String ObjectName) throws InterruptedException {
		driver.waitForPageToBeReady();
		base.waitForElement(getAddNewCodingFormBtn());
		getAddNewCodingFormBtn().waitAndClick(10);
		base.waitForElement(getCodingFormName());
		getCodingFormName().SendKeys(cfName);
		switch (ObjectName) {
		case "tag":
			if (ObjectName.equalsIgnoreCase("tag")) {
				base.waitForElement(getCodingForm_FirstTag());
				getCodingForm_FirstTag().waitAndClick(10);
				base.waitForElement(getCodingForm_SecondTag());
				getCodingForm_SecondTag().waitAndClick(10);
			}
		case "comment":
			if (ObjectName.equalsIgnoreCase("comment")) {
				base.waitForElement(getCodingForm_CommentTab());
				getCodingForm_CommentTab().waitAndClick(10);
				base.waitForElement(getCodingForm_FirstComment());
				getCodingForm_FirstComment().waitAndClick(10);
				base.waitForElement(getCodingForm_SecondComment());
				getCodingForm_SecondComment().waitAndClick(10);
			}
		case "metadata":
			if (ObjectName.equalsIgnoreCase("metadata")) {
				base.waitForElement(getCodingForm_EDITABLE_METADATA_Tab());
				getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(10);
				base.waitForElement(getCodingForm_FirstMetadata());
				getCodingForm_FirstMetadata().waitAndClick(10);
				base.waitForElement(getCodingForm_SecondMetadata());
				getCodingForm_SecondMetadata().waitAndClick(10);
			}
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description : this method used for to select default actions
	 */
	public void selectDefaultActions(int objectNo, String action) {
		if (getCodingForm_getAllObjects().isElementAvailable(5) == true) {
			for (int i = 1; i <= getCodingForm_getAllObjects().size(); i++) {
				base.waitTillElemetToBeClickable(getCodingForm_getExtendObject());
				getCodingForm_getExtendObject().waitAndClick(10);
			}
		}
		getCodingForm_DefaultAction(objectNo).ScrollTo();
		getCodingForm_DefaultAction(objectNo).selectFromDropdown().selectByVisibleText(action);
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description : this method used for to fill the help and error messages
	 */
	public void enterErrorAndHelpMsg(int objectNo, String errorTestingEnabled, String helpMsg, String errorMsg) {
		if (getCodingForm_getAllObjects().isElementAvailable(5) == true) {
			for (int i = 1; i <= getCodingForm_getAllObjects().size(); i++) {
				base.waitTillElemetToBeClickable(getCodingForm_getExtendObject());
				getCodingForm_getExtendObject().waitAndClick(10);
			}
		}
		if (errorTestingEnabled == "Yes") {
			base.waitForElement(getCodingForm_ErrorMsg(objectNo));
			getCodingForm_ErrorMsg(objectNo).SendKeys(errorMsg);
		}
		base.waitForElement(getCodingForm_HelpMsg(objectNo));
		getCodingForm_HelpMsg(objectNo).SendKeys(helpMsg);
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description : this method used for to return cf objects name
	 */
	public String getCFObjectsLabel(int objectNo) {
		driver.waitForPageToBeReady();
		String label = getCodingForm_StaticText(objectNo).GetAttribute("value");
		return label;
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description : this method used for to save the coding form
	 */
	public void saveCodingForm() {
		driver.scrollPageToTop();
		base.waitForElement(getSaveCFBtn());
		getSaveCFBtn().waitAndClick(5);
		if(getCodingForm_Validation_ButtonYes().isElementAvailable(10)) {
			getCodingForm_Validation_ButtonYes().waitAndClick(5);
		}
		base.VerifySuccessMessage("Coding Form Saved successfully");
		base.CloseSuccessMsgpopup();
	
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description : this method used for to select tag type
	 */
	public void selectRadioOrCheckGroup(String TagType) {
		switch (TagType) {
		case "check item":
			if (TagType.equalsIgnoreCase("check item"))
				getCF_TagTypes().selectFromDropdown().selectByVisibleText("Check Item");
			getCF_CheckGrpObject().waitAndClick(10);
			getCodingForm_AddToFormButton().waitAndClick(10);
			getCF_CheckGroup(0).selectFromDropdown().selectByIndex(1);
		case "radio item":
			if (TagType.equalsIgnoreCase("radio item")) {
				getCF_TagTypes().selectFromDropdown().selectByVisibleText("Radio Item");
				getCF_RadioGrpObject().waitAndClick(10);
				getCodingForm_AddToFormButton().waitAndClick(10);
				getCF_RadioGroup().selectFromDropdown().selectByIndex(1);
			}
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description : this method used for to verify the deleted coding form is not
	 *              present
	 */
	public void verifyCodingFormIsDeleted(String cfName) {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		getCodingForm_Search().SendKeys(cfName);
		if (getCFLabel(cfName).isElementAvailable(5) == false) {
			base.passedStep("Deleted coding form is not in the list");
		} else {
			base.failedStep("Deleted coding form is displayed in the list");
		}
	}

	/**
	 * @author Mohan
	 * @Description : this method used for to return cf objects name
	 */
	public String getCFPreviewObjectsLabel(int objectNo) {
		base.waitForElement(getCodingForm_PreviewText(objectNo));
		String label = getCodingForm_PreviewText(objectNo).getText();
		return label;
	}

	/**
	 * @author Mohan
	 * @Description : this method used for to verify CodingForm Name in Manage
	 */
	public void verifyCodingFormNameWithoutObjectCreation(String codingForm) {

		driver.waitForPageToBeReady();

		base.waitForElement(getManageCodingFormButton());
		getManageCodingFormButton().waitAndClick(10);

		driver.waitForPageToBeReady();
		base.waitForElement(getCodingForm_SearchBox());
		getCodingForm_SearchBox().waitAndClick(3);
		getCodingForm_SearchBox().SendKeys(codingForm);

		driver.waitForPageToBeReady();
		if (getCFLabel(codingForm).isElementPresent()) {
			base.passedStep("Coding Form name is displayed as excepted");

		} else {
			base.failedStep("CodingForm name is not displayed under Manage");
		}

	}

	/**
	 * @author Mohan
	 * @Description : this method used to create CodingForm Without any Objects
	 */
	public void createCodingFormWithoutObjects(String cfName) {
		driver.waitForPageToBeReady();
		base.waitForElement(getAddNewCodingFormBtn());
		getAddNewCodingFormBtn().waitAndClick(10);
		base.waitForElement(getCodingFormName());
		getCodingFormName().SendKeys(cfName);
		base.waitForElement(getSaveCFBtn());
		getSaveCFBtn().waitAndClick(5);
		base.VerifySuccessMessage("Coding Form Saved successfully");
		base.stepInfo("CodingForm is created without any Objects");
	}

	/**
	 * @author Mohan
	 * @Description : this method used to edit coding form
	 */
	public void editCodingFormAfterSavingCodingForm(String codingForm) {
		driver.waitForPageToBeReady();

		base.waitForElement(getManageCodingFormButton());
		getManageCodingFormButton().waitAndClick(10);

		base.waitForElement(getCodingForm_SearchBox());
		getCodingForm_SearchBox().waitAndClick(3);
		getCodingForm_SearchBox().SendKeys(codingForm);

		base.waitForElement(getCodingForm_EditButton(codingForm));
		getCodingForm_EditButton(codingForm).waitAndClick(10);

		driver.waitForPageToBeReady();
		if (getCF_PreviewButton().isElementPresent()) {
			base.passedStep("Edit is performed for the CodingForm" + codingForm + "");

		} else {
			base.failedStep("Edit is not performed");
		}

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description : this method used for to add first object alone to coding form
	 */
	public void createCodingFormUsingFirstObject(String cfName, String ObjectName) throws InterruptedException {
		driver.waitForPageToBeReady();
		base.waitForElement(getAddNewCodingFormBtn());
		getAddNewCodingFormBtn().waitAndClick(10);
		base.waitForElement(getCodingFormName());
		getCodingFormName().SendKeys(cfName);
		switch (ObjectName) {
		case "tag":
			if (ObjectName.equalsIgnoreCase("tag")) {
				base.waitForElement(getCodingForm_FirstTag());
				getCodingForm_FirstTag().waitAndClick(10);
			}
		case "comment":
			if (ObjectName.equalsIgnoreCase("comment")) {
				base.waitForElement(getCodingForm_CommentTab());
				getCodingForm_CommentTab().waitAndClick(10);
				base.waitForElement(getCodingForm_FirstComment());
				getCodingForm_FirstComment().waitAndClick(10);
			}
		case "metadata":
			if (ObjectName.equalsIgnoreCase("metadata")) {
				base.waitForElement(getCodingForm_EDITABLE_METADATA_Tab());
				getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(10);
				base.waitForElement(getCodingForm_FirstMetadata());
				getCodingForm_FirstMetadata().waitAndClick(10);
			}
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description : this method used to verify validation of added objects to
	 *              coding form
	 */
	public void verifyCFValidation() {
		try {
			base.waitTillElemetToBeClickable(getCFValidateBtn());
			getCFValidateBtn().waitAndClick(5);
			base.waitForElement(getCFValidateMsg());
			softAssertion.assertEquals("Coding Form Validation Successful.", getCFValidateMsg().getText());
			base.passedStep("Validation success message " + getCFValidateMsg().getText() + " is displayed");
			base.waitTillElemetToBeClickable(getCFValidationPopUpOkBtn());
			getCFValidationPopUpOkBtn().waitAndClick(5);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to verify the coding form validation");
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description : this method used to verify comment field is disabled in
	 *              preview display
	 */
	public void verifyCommentDisabled(String commentName) {
		driver.waitForPageToBeReady();
		if (getCFCommentDisabledInPreview(commentName).isElementPresent() == true) {
			base.passedStep("The comment tab is disabled as expected");
		} else {
			base.failedStep("The comment tab is not disabled");
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description : this method used to verify cf objects name in preview display
	 */
	public void validatePreviewSuccessMsg() {
		try {
			base.waitTillElemetToBeClickable(getTestUrCodeClick());
			getTestUrCodeClick().waitAndClick(5);
			if (getCFErrorMsgNotDisplayed().isElementPresent() == true) {
				base.waitForElement(getSuccessMessage());
				softAssertion.assertEquals(
						"SUCCESS! If a user makes these same selections in DocView, the user will be able to Complete or Save the document!",
						getSuccessMessage().getText());
				base.passedStep("Expected message " + getSuccessMessage().getText() + "is displayed successfully");
			} else {
				base.failedStep("Expected error message is not displayed");
			}
			base.waitTillElemetToBeClickable(getCFPreviewPopUpOkBtn());
			getCFPreviewPopUpOkBtn().waitAndClick(5);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to verify success message");
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description : this method used to verify cf objects name in preview display
	 */
	public void verifyCFObjectsLabel(int objectNo, String objectName) {
		base.waitForElement(getCodingForm_PreviewText(objectNo));
		String label = getCodingForm_PreviewText(objectNo).getText();
		softAssertion.assertEquals(label, objectName);
		base.passedStep("Added " + objectName + " is displayed in preview dialog box as expected");
		softAssertion.assertAll();
	}

	/**
	 * 
	 * @author Indium-Baskar date: 24/09/2021 Modified date: NA
	 * @Description : this method used for create coding form with one metadata
	 */

	public String createWithOneMetaData(String cfName) {
		driver.waitForPageToBeReady();
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		base.waitForElement(getAddNewCodingFormBtn());
		getAddNewCodingFormBtn().waitAndClick(10);
		base.waitForElement(getCodingFormName());
		getCodingFormName().SendKeys(cfName);
		base.waitForElement(getCodingForm_EDITABLE_METADATA_Tab());
		getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(10);
		base.waitForElement(getCodingForm_FirstMetadata());
		String text = getCFMetaFirstText().getText();
		getCodingForm_FirstMetadata().waitAndClick(5);
		addcodingFormAddButton();
		base.waitForElement(getSaveCFBtn());
		getSaveCFBtn().waitAndClick(5);
		base.waitForElement(getCodingForm_Validation_ButtonYes());
		getCodingForm_Validation_ButtonYes().waitAndClick(5);
		base.VerifySuccessMessage("Coding Form Saved successfully");
		return text;

	}

	/**
	 * @author Mohan
	 * @Description : this method used to remove tag from coding form
	 */
	public void selectRemoveLinkFromCodingForm(int rowNo) {

		driver.waitForPageToBeReady();
		getCodingForm_SelectRemoveLink(rowNo).ScrollTo();
		base.waitForElement(getCodingForm_SelectRemoveLink(rowNo));
		getCodingForm_SelectRemoveLink(rowNo).waitAndClick(5);
		base.waitForElement(getCodingForm_SGValidation_ButtonYes());
		getCodingForm_SGValidation_ButtonYes().waitAndClick(10);

	}

	/**
	 * @author Mohan
	 * @Description : this method used to verify whether remove link is not present
	 *              in coding form
	 */
	public void verifyRemoveLinkCodingForm(int objectName) {

		driver.waitForPageToBeReady();
		if (getCodingForm_TagText(objectName).isElementPresent()) {
			base.passedStep("Element is present under Tag section");

		} else {
			base.failedStep("Element is not present under Tag section");
		}

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description : this method used to select field logic values in cf objects
	 */
	public void selectFieldLogicValues(int objectNo, String objectName, String conditionName, String actionName) {
		base.waitTillElemetToBeClickable(getCFAddLogicRuleBtn(objectNo));
		getCFAddLogicRuleBtn(objectNo).waitAndClick(5);
		base.waitForElement(getCFFieldLogicObject(objectNo));
		getCFFieldLogicObject(objectNo).waitAndClick(10);
		getCFselectFieldLogicObj(objectName, objectNo).waitAndClick(10);
		base.waitForElement(getCFFieldLogicCondition(objectNo));
		getCFFieldLogicCondition(objectNo).waitAndClick(10);
		getCFselectFieldLogicObj(conditionName, objectNo).waitAndClick(10);
		base.waitForElement(getCFFieldLogicAction(objectNo));
		getCFFieldLogicAction(objectNo).waitAndClick(10);
		getCFselectFieldLogicAction(actionName, objectNo).waitAndClick(10);
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description : this method used to verify preview error message
	 */
	public void validatePreviewErrorMsg() {
		try {
			base.waitTillElemetToBeClickable(getTestUrCodeClick());
			getTestUrCodeClick().waitAndClick(5);
			if (getCFSuccessMsgNotDisplayed().isElementPresent() == true) {
				base.waitForElement(getCFErrorMsg());
				softAssertion.assertEquals(
						"SUCCESS! If a user makes these same selections in DocView, the user will be able to Complete or Save the document!",
						getCFErrorMsg().getText());
				base.passedStep("Expected message " + getCFErrorMsg().getText() + "is displayed successfully");
			} else {
				base.failedStep("Error message is not displayed as expected");
			}
			base.waitTillElemetToBeClickable(getCFPreviewPopUpOkBtn());
			getCFPreviewPopUpOkBtn().waitAndClick(5);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to verify error message");
		}
	}

	/**
	 * @author Indium-Baskar
	 * @Description : this method used to click the add coding form button
	 */
//	Reusable method for add new coding form
	public void addNewCodingFormButton() {
		driver.waitForPageToBeReady();
		base.waitForElement(getAddNewCodingFormBtn());
		getAddNewCodingFormBtn().waitAndClick(10);
	}

	/**
	 * @author Indium-Baskar
	 * @Description : this method used to click the add coding form button
	 */
//	Reusable method for passing coding form name
	public String passingCodingFormName(String cfName) {
		driver.waitForPageToBeReady();
		base.waitForElement(getCodingFormName());
		getCodingFormName().SendKeys(cfName);
		String name=getCodingFormName().GetAttribute("value");
		return name;
	}

	/**
	 * @author Indium-Baskar
	 * @Description : this method used for to add first object alone to coding form
	 */
//	Reusable method for clicking first special object in three tabs
	public void createCodingForm(String ObjectName) throws InterruptedException {
		driver.waitForPageToBeReady();
		switch (ObjectName) {
		case "tag":
			if (ObjectName.equalsIgnoreCase("tag")) {
				base.waitForElement(getCodingForm_FirstTag());
				getCodingForm_FirstTag().waitAndClick(10);

			}
		case "comment":
			if (ObjectName.equalsIgnoreCase("comment")) {
				base.waitForElement(getCodingForm_CommentTab());
				getCodingForm_CommentTab().waitAndClick(10);

			}
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description : this method used to enter name to objects
	 */
	public void enterObjectName(int objectNo, String label) {
		base.waitForElement(getCF_objectName(objectNo));
		getCF_objectName(objectNo).Clear();
		getCF_objectName(objectNo).SendKeys(label);
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description : this method used to select tagtype based on index
	 */
	public void selectTagTypeByIndex(String TagType, int index, int objectNo) {
		switch (TagType) {
		case "check item":
			if (TagType.equalsIgnoreCase("check item")) {
				getCF_TagType(objectNo).selectFromDropdown().selectByVisibleText("Check Item");
				getCF_CheckGroup(objectNo).selectFromDropdown().selectByIndex(index);
			}
		case "radio item":
			if (TagType.equalsIgnoreCase("radio item")) {
				getCF_TagType(objectNo).selectFromDropdown().selectByVisibleText("Radio Item");
				getCF_RadioGroup(objectNo).selectFromDropdown().selectByIndex(index);
			}
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description : this method used to select field logics based on operator
	 */
	public void selectOperatorAndFieldLogics(int objectNo, int operatorNo, String objectName, String conditionName,
			String operatorName) throws Exception {
		base.waitTillElemetToBeClickable(getCFAddLogicRuleBtn(objectNo));
		getCFAddLogicRuleBtn(objectNo).waitAndClick(5);
		base.waitForElement(getCFOperator(objectNo, operatorNo));
		getCFOperator(objectNo, operatorNo).waitAndClick(10);
		getCFselectFieldLogicObj(operatorName, operatorNo).waitAndClick(10);
		getCFOperator(objectNo, operatorNo).selectFromDropdown().selectByVisibleText(operatorName);
		base.waitForElement(getCFOperatorFieldLogicObject(objectNo, operatorNo));
		getCFOperatorFieldLogicObject(objectNo, operatorNo).waitAndClick(10);
		getCFselectOperatorFieldLogicObj(objectName, operatorNo).waitAndClick(10);
		base.waitForElement(getCFOperatorFieldLogicCondition(objectNo, operatorNo));
		getCFOperatorFieldLogicCondition(objectNo, operatorNo).waitAndClick(10);
		getCFOperatorFieldLogicConditionName(objectNo, operatorNo, conditionName).waitAndClick(10);
	}

	/**
	 * @author Indium-Baskar
	 * @Description : this method used to create different coding assgn to SG
	 */

	public void differentCodingForm(String cfName) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		addNewCodingFormButton();
		base.waitForElement(getCodingForm_CommentTab());
		getCodingForm_CommentTab().waitAndClick(10);
		base.waitForElement(getCodingForm_SecondComment());
		getCodingForm_SecondComment().waitAndClick(10);
		addcodingFormAddButton();
		passingCodingFormName(cfName);
		saveCodingForm();
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description : this method used to create coding form based on parameter
	 */
	public void CreateCodingFormWithParameter(String cfName, String tag, String comment, String metadata,
			String ObjectName) throws InterruptedException {
		switch (ObjectName) {
		case "tag":
			if (ObjectName.equalsIgnoreCase("tag")) {
				driver.waitForPageToBeReady();
				getCodingForm_Tag(tag).ScrollTo();
				base.waitForElement(getCodingForm_Tag(tag));
				getCodingForm_Tag(tag).waitAndClick(10);
			}
		case "comment":
			if (ObjectName.equalsIgnoreCase("comment")) {
				base.waitForElement(getCodingForm_CommentTab());
				getCodingForm_CommentTab().waitAndClick(10);
				base.waitForElement(getCodingForm_Comment(comment));
				getCodingForm_Comment(comment).waitAndClick(10);

			}
		case "metadata":
			if (ObjectName.equalsIgnoreCase("metadata")) {
				base.waitForElement(getCodingForm_EDITABLE_METADATA_Tab());
				getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(10);
				base.waitForElement(getCodingForm_MetaData(metadata));
				getCodingForm_MetaData(metadata).waitAndClick(10);
			}
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description : this method used to verify the coding form object is not
	 *              displayed
	 */
	public void verifyCFObjectsNotDisplayed(String objectName) {
		driver.waitForPageToBeReady();
		if (getHiddenObjectsInPreviewBox(objectName).isElementPresent() == true) {
			base.passedStep("Expected object is not displayed");
		} else {
			base.failedStep("Expected object is displayed");
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description : this method used to verify the cf logic validations in preview
	 *              display
	 */
	public void verifyCFlogicValidationInPreviewDisplay() {
		try {
			base.waitTillElemetToBeClickable(getCF_PreviewButton());
			getCF_PreviewButton().waitAndClick(10);
			driver.waitForPageToBeReady();
			String InstructionText = getCFPreviewObjectsLabel(4);
			softAssertion.assertEquals(InstructionText, "Responsiveness");
			String TechIssueHelpText = getCFPreviewObjectName("Technical_Issue").GetAttribute("title");
			softAssertion.assertEquals(TechIssueHelpText, "Is there some reason that review cannot determined?");
			base.ValidateElement_Presence(getCFPreviewObjectName("Technical_Issue"), "Technical issue tag");
			base.ValidateElement_Presence(getCFPreviewObjectName("Responsive"), "Responsive tag");
			verifyCFObjectsNotDisplayed("Processing_Issue");
			verifyCFObjectsNotDisplayed("Foreign_Language");
			base.stepInfo("The processing issue and foreign language tag is not displayed");
			selectObjectsInPreviewBox("Technical_Issue").waitAndClick(10);
			base.ValidateElement_Presence(getCFPreviewObjectName("Processing_Issue"), "Processing issue tag");
			base.ValidateElement_Presence(getCFPreviewObjectName("Foreign_Language"), "Foreign_Language tag");
			base.stepInfo(
					"The processing issue and foreign language tag is displayed once the field logic rule is applied");
			selectObjectsInPreviewBox("Responsive").waitAndClick(10);
			verifyCFObjectsNotDisplayed("Processing_Issue");
			verifyCFObjectsNotDisplayed("Foreign_Language");
			base.stepInfo("The processing issue and foreign language tag is not displayed as expected");
			driver.waitForPageToBeReady();
			selectObjectsInPreviewBox("Technical_Issue").waitAndClick(10);
			base.waitTillElemetToBeClickable(getTestUrCodeClick());
			getTestUrCodeClick().waitAndClick(5);
			String errorMessage = geErrMsgInPreviewBox().getText();
			softAssertion.assertEquals(errorMessage,
					"If the document has technical issue cannot be reviewed,you must select reason why from the list above");
			base.passedStep("Got error messsage successfully as expected");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to validate preview display");
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @throws InterruptedException 
	 * @Description : this method used to verify the cf logic validations in parent
	 *              and child window in doc view page
	 */
	public void verifyCFlogicValidationsInDocViewPg(String group) {
		try {
			driver.waitForPageToBeReady();
			doc.validateRadioOrCheckGroupInDocviewPg(group);
			String InstructionText = getCFPreviewObjectsLabel(4);
			softAssertion.assertEquals(InstructionText, "Responsiveness");
			base.passedStep("Instruction text is displayed successfully");
			String TechIssueHelpText = getCFPreviewObjectName("Technical_Issue").GetAttribute("title");
			softAssertion.assertEquals(TechIssueHelpText, "Is there some reason that review cannot determined?");
			base.passedStep("Help text is displayed successfully");
			base.ValidateElement_Presence(getCFPreviewObjectName("Technical_Issue"), "Technical issue tag");
			base.ValidateElement_Presence(getCFPreviewObjectName("Responsive"), "Responsive tag");
			verifyCFObjectsNotDisplayed("Processing_Issue");
			verifyCFObjectsNotDisplayed("Foreign_Language");
			base.stepInfo("The processing issue and foreign language tag is not displayed");
			selectObjectsInPreviewBox("Technical_Issue").waitAndClick(10);
			base.ValidateElement_Presence(getCFPreviewObjectName("Processing_Issue"), "Processing issue tag");
			base.ValidateElement_Presence(getCFPreviewObjectName("Foreign_Language"), "Foreign_Language tag");
			base.stepInfo(
					"The processing issue and foreign language tag is displayed once the field logic rule is applied");
			if (group == "check-group") {
				selectObjectsInPreviewBox("Technical_Issue").waitAndClick(10);
			} else {
				selectObjectsInPreviewBox("Responsive").waitAndClick(10);
			}
			verifyCFObjectsNotDisplayed("Processing_Issue");
			verifyCFObjectsNotDisplayed("Foreign_Language");
			base.stepInfo("The processing issue and foreign language tag is not displayed as expected");
			driver.waitForPageToBeReady();
			selectObjectsInPreviewBox("Technical_Issue").waitAndClick(10);
			base.waitForElement(reusableDocView.getSaveAndNextButton());
			reusableDocView.getSaveAndNextButton().waitAndClick(10);
			String errorMessage = geErrMsgInPreviewBox().getText();
			softAssertion.assertEquals(errorMessage,
					"If the document has technical issue cannot be reviewed,you must select reason why from the list above");
			base.passedStep("Got error messsage successfully as expected");
			if (group == "check-group") {
				selectObjectsInPreviewBox("Technical_Issue").waitAndClick(10);
			} else {
				selectObjectsInPreviewBox("Responsive").waitAndClick(10);
			}
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to validate preview display");
		}
	}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for clicking coding form tab based on parameter using string
	public void basedOnCreatingNewObject(String tag, String comment, String metadata, String ObjectName)
			throws InterruptedException {
		driver.waitForPageToBeReady();
		switch (ObjectName) {
		case "tag":
			if (ObjectName.equalsIgnoreCase("tag")) {
				base.waitForElement(getCodingForm_Tag(tag));
				getCodingForm_Tag(tag).waitAndClick(10);
			}
		case "comment":
			if (ObjectName.equalsIgnoreCase("comment")) {
				base.waitForElement(getCodingForm_CommentTab());
				getCodingForm_CommentTab().waitAndClick(10);
				base.waitForElement(getCodingForm_Comment(comment));
				getCodingForm_Comment(comment).waitAndClick(10);

			}
		case "metadata":
			if (ObjectName.equalsIgnoreCase("metadata")) {
				base.waitForElement(getCodingForm_EDITABLE_METADATA_Tab());
				getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(10);
				base.waitForElement(getCodingForm_MetaData(metadata));
				getCodingForm_MetaData(metadata).waitAndClick(10);
			}
		}
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium-Baskar
	 */
	public void creatingCodingFormusingMultipleInteger(String cfName, String tiny, String small, String average,
			String bit, String huge, String action) throws InterruptedException {
		driver.waitForPageToBeReady();
		addNewCodingFormButton();
		basedOnCreatingNewObject(null, null, tiny, "metadata");
		addcodingFormAddButton();
		driver.scrollPageToTop();
		basedOnCreatingNewObject(null, null, small, "metadata");
		addcodingFormAddButton();
		driver.scrollPageToTop();
		basedOnCreatingNewObject(null, null, average, "metadata");
		addcodingFormAddButton();
		driver.scrollPageToTop();
		basedOnCreatingNewObject(null, null, bit, "metadata");
		addcodingFormAddButton();
		driver.scrollPageToTop();
		basedOnCreatingNewObject(null, null, huge, "metadata");
		addcodingFormAddButton();
		selectDefaultActions(0, action);
		selectDefaultActions(1, action);
		selectDefaultActions(2, action);
		selectDefaultActions(3, action);
		selectDefaultActions(4, action);
		driver.scrollPageToTop();
		passingCodingFormName(cfName);
		saveCodingForm();

	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description : this method used to verify the cf logic validations in doc
	 *              view page as reviewer
	 */
	public void verifyCFlogicValidationsAsRevInDocViewPg(String group) {
		try {
			driver.waitForPageToBeReady();
			doc.validateRadioOrCheckGroupInDocviewPg(group);
			String InstructionText = getCFPreviewObjectsLabel(4);
			softAssertion.assertEquals(InstructionText, "Responsiveness");
			base.passedStep("Instruction text is displayed successfully");
			String TechIssueHelpText = getCFPreviewObjectName("Technical_Issue").GetAttribute("title");
			softAssertion.assertEquals(TechIssueHelpText, "Is there some reason that review cannot determined?");
			base.passedStep("Help text is displayed successfully");
			base.ValidateElement_Presence(getCFPreviewObjectName("Technical_Issue"), "Technical issue tag");
			base.ValidateElement_Presence(getCFPreviewObjectName("Responsive"), "Responsive tag");
			verifyCFObjectsNotDisplayed("Processing_Issue");
			verifyCFObjectsNotDisplayed("Foreign_Language");
			base.stepInfo("The processing issue and foreign language tag is not displayed");
			selectObjectsInPreviewBox("Technical_Issue").waitAndClick(10);
			base.ValidateElement_Presence(getCFPreviewObjectName("Processing_Issue"), "Processing issue tag");
			base.ValidateElement_Presence(getCFPreviewObjectName("Foreign_Language"), "Foreign_Language tag");
			base.stepInfo(
					"The processing issue and foreign language tag is displayed once the field logic rule is applied");
			selectObjectsInPreviewBox("Responsive").waitAndClick(10);
			verifyCFObjectsNotDisplayed("Processing_Issue");
			verifyCFObjectsNotDisplayed("Foreign_Language");
			base.stepInfo("The processing issue and foreign language tag is not displayed as expected");
			driver.waitForPageToBeReady();
			selectObjectsInPreviewBox("Technical_Issue").waitAndClick(10);
			doc.verifyCustomizedMetaDataCodingForm(null);
			String errorMessage = geErrMsgInPreviewBox().getText();
			softAssertion.assertEquals(errorMessage,
					"If the document has technical issue cannot be reviewed,you must select reason why from the list above");
			base.passedStep("Got error messsage successfully as expected");
			selectObjectsInPreviewBox("Responsive").waitAndClick(10);
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Failed to validate preview display");
		}
	}

	/**
	 * @author Iyappan.Kasinathan
	 * @Description : this method used to verify the tags are disabled in preview
	 *              display
	 */
	public void verifyDisabledTagInPreviewDisplay(String objectName) {
		driver.waitForPageToBeReady();
		if (getDisabledTagsInPreviewBox(objectName).isElementPresent() == true) {
			base.passedStep(objectName + " is disabled as expected");
		} else {
			base.failedStep(objectName + " is not disabled as expected");
		}
	}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for selecting all check box
	public void specialObjectsBox(String ObjectName) {
		driver.waitForPageToBeReady();
		switch (ObjectName) {
		case "staticText":
			if (ObjectName.equalsIgnoreCase("staticText")) {
				base.waitForElement(getCF_StaticTextObject());
				getCF_StaticTextObject().waitAndClick(5);
			}
		case "radio":
			if (ObjectName.equalsIgnoreCase("radio")) {
				base.waitForElement(getCF_RadioGrpObject());
				getCF_RadioGrpObject().waitAndClick(5);
			}
		case "check":
			if (ObjectName.equalsIgnoreCase("check")) {
				base.waitForElement(getCF_CheckGrpObject());
				getCF_CheckGrpObject().waitAndClick(5);
			}
		}
	}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for static text field name passing
	public void staticTextFieldNamePassing(String staticText) {
		driver.waitForPageToBeReady();
		base.waitForElement(getStaticText());
		getStaticText().SendKeys(staticText);
	}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for creating new static text
	public void creationOfStaticText(String staticField, String cfName) {
		addNewCodingFormButton();
		specialObjectsBox("staticText");
		passingCodingFormName(cfName);
		addcodingFormAddButton();
		staticTextFieldNamePassing(staticField);
		saveCodingForm();
	}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for adding first tab to coding form
	public void firstCheckBox(String ObjectName) throws InterruptedException {
		driver.waitForPageToBeReady();
		switch (ObjectName) {
		case "tag":
			if (ObjectName.equalsIgnoreCase("tag")) {
				base.waitForElement(getCodingForm_FirstTag());
				getCodingForm_FirstTag().waitAndClick(10);
			}
		case "comment":
			if (ObjectName.equalsIgnoreCase("comment")) {
				base.waitForElement(getCodingForm_CommentTab());
				getCodingForm_CommentTab().waitAndClick(10);
				base.waitForElement(getCodingForm_FirstComment());
				getCodingForm_FirstComment().waitAndClick(10);
			}
		case "metadata":
			if (ObjectName.equalsIgnoreCase("metadata")) {
				base.waitForElement(getCodingForm_EDITABLE_METADATA_Tab());
				getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(10);
				base.waitForElement(getCodingForm_FirstMetadata());
				getCodingForm_FirstMetadata().waitAndClick(10);
			}
		}
	}

	/**
	 * @author Indium-Baskar
	 */
	public void errorMsgForComments(String cfName, String errorText, String helpText) throws InterruptedException {
		driver.waitForPageToBeReady();
		addNewCodingFormButton();
		firstCheckBox(Input.tag);
		addcodingFormAddButton();
		specialObjectToAddCodinForm(Input.tag, Input.checkItem);
		driver.waitForPageToBeReady();
		selectDefaultActions(0, Input.hidden);
		driver.scrollPageToTop();
		firstCheckBox(Input.comments);
		firstCheckBox(Input.metaData);
		addcodingFormAddButton();
		driver.waitForPageToBeReady();
		selectDefaultActions(1, Input.required);
		selectDefaultActions(2, Input.optional);
		driver.scrollPageToTop();
		specialObjectsBox(Input.staticText);
		specialObjectsBox(Input.radioGroup);
		specialObjectsBox(Input.checkGroup);
		addcodingFormAddButton();
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		selectDefaultActions(4, Input.hidden);
		selectDefaultActions(5, Input.notSelectable);
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		enterErrorAndHelpMsg(1, "Yes", errorText, helpText);
		passingCodingFormName(cfName);
		saveCodingForm();

	}

	/**
	 * @author Indium-Baskar
	 */
//	Reusable method for assign coding form using condition
	public void assignCodingFormByCondition(String cfName) {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		base.waitForElement(getCodingForm_SearchBox());
		getCodingForm_SearchBox().SendKeys(cfName);
		driver.waitForPageToBeReady();
		base.waitForElement(getDefaultRadioBtn());
		if (getDefaultRadioBtn().isElementAvailable(1)) {
			System.out.println("default coding form already updated successfully");
			base.stepInfo("default coding form form already updated successfully");
		} else {
			base.waitForElement(getDefaultSelectRadioBtn());
			getDefaultSelectRadioBtn().waitAndClick(10);
			getDefaultYesBtn().waitAndClick(10);
			System.out.println("default coding form form is updated successfully");
			base.stepInfo("default coding form form is updated successfully");
		}
	}

	/**
	 * @author Indium-Baskar
	 */

//	Reusable method for assig coding form to SG with alert
	public void assignCodingFormToSGAlert(String cfName) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.switchTo().alert().accept();
		driver.waitForPageToBeReady();
		base.waitForElement(getManageCodingFormButton());
		base.waitForElement(getCodingForm_Search());
		getCodingForm_Search().SendKeys(cfName);
		base.waitForElement(getAddCodingFormCheckToSG(cfName));
		getAddCodingFormCheckToSG(cfName).waitAndClick(5);
		base.waitForElement(getCodingForm_SGValidation_ButtonYes());
		getCodingForm_SGValidation_ButtonYes().waitAndClick(10);

	}

	/**
	 * @author Indium-Baskar
	 */
	public void commentRequired(String cfName) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		addNewCodingFormButton();
		firstCheckBox(Input.comments);
		addcodingFormAddButton();
		selectDefaultActions(0, Input.required);
		passingCodingFormName(cfName);
		saveCodingForm();

	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void saveCodingFormWithErrorAndTextmsg(String cfName) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		addNewCodingFormButton();
		firstCheckBox(Input.comments);
		addcodingFormAddButton();
		selectDefaultActions(0, Input.required);
		passingCodingFormName(cfName);
		getCodingForm_ErrorMsg().SendKeys(Input.errorMsg);
		getCodingForm_HelpText().SendKeys(Input.helpText);
		saveCodingForm();

	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void saveCodingFormWithErrorAndTextmsgInMetaData(String cfName) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		addNewCodingFormButton();
		firstCheckBox(Input.metaData);
		addcodingFormAddButton();
		selectDefaultActions(0, Input.required);
		passingCodingFormName(cfName);
		getCodingForm_ErrorMsg().SendKeys(Input.errorMsg);
		getCodingForm_HelpText().SendKeys(Input.helpText);
		saveCodingForm();

	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void saveCodingFormWithCheckGroup(String cfName) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		addNewCodingFormButton();

		firstCheckBox(Input.tag);
		addcodingFormAddButton();
		selectRadioOrCheckGroup("check item");
		selectDefaultActions(1, Input.required);
		passingCodingFormName(cfName);
		getCodingForm_ErrorMsg1().SendKeys(Input.errorMsg);
		getCodingForm_HelpText().SendKeys(Input.helpText);

		saveCodingForm();
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void saveCodingForm2TagsWithCheckGroup(String cfName) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		addNewCodingFormButton();

		addTwoCheckBox(Input.tag);
		addcodingFormAddButton();
		selectRadioOrCheckGroup("check item");

		passingCodingFormName(cfName);
		selectDefaultActions(1, Input.hidden);
		selectDefaultActions(2, Input.required);

		saveCodingForm();
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @Description : this method used for to add first two objects to coding form
	 */
	public void addTwoCheckBox(String ObjectName) throws InterruptedException {

		switch (ObjectName) {
		case "tag":
			if (ObjectName.equalsIgnoreCase("tag")) {
				base.waitForElement(getCodingForm_FirstTag());
				getCodingForm_FirstTag().waitAndClick(10);
				base.waitForElement(getCodingForm_SecondTag());
				getCodingForm_SecondTag().waitAndClick(10);
			}
		case "comment":
			if (ObjectName.equalsIgnoreCase("comment")) {
				base.waitForElement(getCodingForm_CommentTab());
				getCodingForm_CommentTab().waitAndClick(10);
				base.waitForElement(getCodingForm_FirstComment());
				getCodingForm_FirstComment().waitAndClick(10);
				base.waitForElement(getCodingForm_SecondComment());
				getCodingForm_SecondComment().waitAndClick(10);
			}
		case "metadata":
			if (ObjectName.equalsIgnoreCase("metadata")) {
				base.waitForElement(getCodingForm_EDITABLE_METADATA_Tab());
				getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(10);
				base.waitForElement(getCodingForm_FirstMetadata());
				getCodingForm_FirstMetadata().waitAndClick(10);
				base.waitForElement(getCodingForm_SecondMetadata());
				getCodingForm_SecondMetadata().waitAndClick(10);
			}
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void saveCodingFormWithRedioGroup(String cfName, String tagName) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		addNewCodingFormButton();

		// firstCheckBox(Input.tag);
		base.waitForElement(getCodingForm_Tag(tagName));
		getCodingForm_Tag(tagName).waitAndClick(10);
		addcodingFormAddButton();
		selectRadioOrCheckGroup("radio item");
		selectDefaultActions(1, Input.required);
		passingCodingFormName(cfName);
		getCodingForm_ErrorMsg1().SendKeys(Input.errorMsg);
		getCodingForm_HelpText().SendKeys(Input.helpText);
		getCodingForm_HelpText1().SendKeys(Input.helpText);

		saveCodingForm();
	}

	/**
	 * @author Indium-Baskar
	 */
	public void creatingCodingThreeDataType(String cfName, String date, String dateTime, String intData)
			throws InterruptedException {
		driver.waitForPageToBeReady();
		addNewCodingFormButton();
		basedOnCreatingNewObject(null, null, date, "metadata");
		basedOnCreatingNewObject(null, null, dateTime, "metadata");
		basedOnCreatingNewObject(null, null, intData, "metadata");
		addcodingFormAddButton();
		passingCodingFormName(cfName);
		saveCodingForm();
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void saveCodingForm2TagsWithNotSelectableAndOption(String cfName) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		addNewCodingFormButton();

		addTwoCheckBox(Input.tag);
		addcodingFormAddButton();
		passingCodingFormName(cfName);
		getCF_TagType1().selectFromDropdown().selectByVisibleText("Check Item");
		getCF_TagType2().selectFromDropdown().selectByVisibleText("Check Item");

		selectDefaultActions(0, Input.notSelectable);
		selectDefaultActions(1, Input.optional);

		saveCodingForm();
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void saveCodingForm2CommentWithOptionalAndRequired(String cfName) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		addNewCodingFormButton();

		addTwoCheckBox(Input.comments);
		addcodingFormAddButton();
		passingCodingFormName(cfName);

		selectDefaultActions(0, Input.optional);
		selectDefaultActions(1, Input.required);
		addLogicOptions(1, "COMMENT_1", "Not Selected", "Make this Read Only");

		saveCodingForm();
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void addLogicOptions(int i, String objname, String Condition, String rule) {
		base.waitForElement(getCF_AddLogicButton(2));
		getCF_AddLogicButton(i + 1).waitAndClick(5);
		getCF_Object1(i).selectFromDropdown().selectByValue(objname);
		getCF_FieldLogicCondition(i).selectFromDropdown().selectByVisibleText(Condition);
		getCF_FieldLogicAction(i).selectFromDropdown().selectByVisibleText(rule);

	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void addLogicOptionWithIndex(int i, int indexNum, String Condition, String rule) {
		driver.waitForPageToBeReady();
		base.waitForElement(getCF_AddLogicButton(i + 1));
		driver.scrollingToElementofAPage(getCF_AddLogicButton(i + 1));
		driver.waitForPageToBeReady();
		getCF_AddLogicButton(i + 1).waitAndClick(10);
		getCF_Object1(i).selectFromDropdown().selectByIndex(indexNum);
		getCF_FieldLogicCondition(i).selectFromDropdown().selectByVisibleText(Condition);
		getCF_FieldLogicAction(i).selectFromDropdown().selectByVisibleText(rule);
		base.stepInfo(Condition+" is selected in the role "+ rule);
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void addLogicOptionWithIndexWithoutIncreace(int i, int indexNum, String Condition, String rule) {
		driver.waitForPageToBeReady();
		base.waitForElement(getCF_AddLogicButton(i));
		driver.scrollingToElementofAPage(getCF_AddLogicButton(i));
		driver.waitForPageToBeReady();
		getCF_AddLogicButton(i).waitAndClick(10);
		getCF_Object1(i).selectFromDropdown().selectByIndex(indexNum);
		getCF_FieldLogicCondition(i).selectFromDropdown().selectByVisibleText(Condition);
		getCF_FieldLogicAction(i).selectFromDropdown().selectByVisibleText(rule);
		base.stepInfo(Condition+" is selected in the role "+ rule);
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void saveCodingForm2MetaDataAddLogicNotSelected(String cfName, String metaData1, String metaData2)
			throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		addNewCodingFormButton();

		addTwoCheckBox(Input.metaData, metaData1, metaData2);
		getCF_CheckGrpObject().waitAndClick(10);
		addcodingFormAddButton();
		getCF_CheckGrpObject().waitAndClick(10);
		addcodingFormAddButton();
		passingCodingFormName(cfName);

		selectDefaultActions(0, Input.hidden);
		selectDefaultActions(1, Input.notSelectable);
		selectDefaultActions(2, Input.optional);
		selectDefaultActions(3, Input.required);
		addLogicOptionWithIndex(2, 1, Input.notSelect, Input.thisRequired);

		saveCodingForm();
	}

	/**
	 * @author Aathith.Senthilkumar
	 */
	public void saveCodingForm2RadioGrpAddLogicWithSelectedNotSelected(String cfName, String tag, String tag1)
			throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		addNewCodingFormButton();

		addTwoCheckBox(Input.tag, tag, tag1);
		addcodingFormAddButton();
		selectRadioOrCheckGroup("radio item");

		getTagType2().selectFromDropdown().selectByVisibleText("Radio Item");
		getCF_RadioGrpObject().waitAndClick(10);
		driver.scrollPageToTop();
		getCodingForm_AddToFormButton().waitAndClick(10);
		getCF_RadioGroup2().selectFromDropdown().selectByIndex(2);

		passingCodingFormName(cfName);

		selectDefaultActions(2, Input.optional);
		selectDefaultActions(3, Input.required);
		addLogicOptionWithIndex(2, 1, Input.select, Input.thisOptional);
		addLogicOptionWithIndex(3, 2, Input.notSelect, Input.thisOptional);

		saveCodingForm();
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @Description : this method used for to add first two objects to coding form
	 */
	public void addTwoCheckBox(String ObjectName, String name1, String name2) throws InterruptedException {

		switch (ObjectName) {
		case "tag":
			if (ObjectName.equalsIgnoreCase("tag")) {
				base.waitForElement(getCodingForm_Tag(name1));
				getCodingForm_Tag(name1).waitAndClick(10);
				base.waitForElement(getCodingForm_Tag(name2));
				getCodingForm_Tag(name2).waitAndClick(10);
			}
		case "comment":
			if (ObjectName.equalsIgnoreCase("comment")) {
				base.waitForElement(getCodingForm_CommentTab());
				getCodingForm_CommentTab().waitAndClick(10);
				base.waitForElement(getCF_Comment(name1));
				getCF_Comment(name1).waitAndClick(10);
				base.waitForElement(getCF_Comment(name2));
				getCF_Comment(name2).waitAndClick(10);
			}
		case "metadata":
			if (ObjectName.equalsIgnoreCase("metadata")) {
				base.waitForElement(getCodingForm_EDITABLE_METADATA_Tab());
				getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(10);
				base.waitForElement(getCF_Metadata(name1));
				getCF_Metadata(name1).waitAndClick(10);
				base.waitForElement(getCF_Metadata(name2));
				getCF_Metadata(name2).waitAndClick(10);
			}
		}
	}

	/**
	 * @author Gopinath
	 * @description : Method to navigate coding form page.
	 */
	public void navigateToCodingFormPage() {
		try {
			driver.getWebDriver().get(Input.url + "CodingForm/Create");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occcured while navigating to coding form page" + e.getMessage());

		}
	}

	/*
	 * Indium-Baskar
	 */
//	Reusable method for edit coding form
	public void editCodingForm(String cfName) {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		base.waitForElement(getManageCodingFormButton());
		base.waitForElement(getCodingForm_Search());
		getCodingForm_Search().SendKeys(cfName);
		base.waitForElement(getCodingForm_EditButton(cfName));
		getCodingForm_EditButton(cfName).waitAndClick(10);
	}

	/*
	 * Indium-Baskar
	 */
//	Reusable method for coding form save buttons
	public void codingFormSaveButton() {
		driver.scrollPageToTop();
		base.waitForElement(getSaveCFBtn());
		getSaveCFBtn().waitAndClick(5);
		base.waitForElement(getCodingForm_Validation_ButtonYes());
		getCodingForm_Validation_ButtonYes().waitAndClick(5);
	}

	/*
	 * Indium-Baskar
	 */

	public void createCommentAndMetadata(String metadata, String comment, String cfName) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		addNewCodingFormButton();
		basedOnCreatingNewObject(null, comment, null, "comment");
		addcodingFormAddButton();
		basedOnCreatingNewObject(null, null, metadata, "metadata");
		addcodingFormAddButton();
		passingCodingFormName(cfName);
		saveCodingForm();
	}

	/*
	 * Indium-Baskar
	 */
//  Thismethod used to validate the unassigned coding form and removig object nd delete cf
	public void verifyUnassignedCf(String cfName) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		addNewCodingFormButton();
		firstCheckBox("tag");
		addcodingFormAddButton();
		passingCodingFormName(cfName);
		saveCodingForm();
		editCodingForm(cfName);
		getCodingForm_TagNames(1).waitAndClick(5);
		selectRemoveLinkFromCodingForm(0);
		if (getCodingForm_SelectRemoveLink(0).isElementAvailable(5)) {
			base.failedStep("Coding form object not removed");
		} else {
			base.passedStep("Coding form object removed ");
		}
		base.waitForElement(getSaveCFBtn());
		getSaveCFBtn().waitAndClick(5);
		deleteCodingForm(cfName, cfName);
		base.VerifySuccessMessage("Coding form deleted successfully");
	}

	/**
	 * @author Aathith
	 * @Description : this method used for to select tag type
	 */
	public void selectRadioOrCheckGroup(int i, String TagType) {
		int index = i + 1;
		switch (TagType) {
		case "check item":
			if (TagType.equalsIgnoreCase("check item"))
				getCF_TagTypes(i).selectFromDropdown().selectByVisibleText("Check Item");
			getCF_CheckGrpObject().waitAndClick(10);
			driver.scrollPageToTop();
			getCodingForm_AddToFormButton().waitAndClick(10);
			getCF_CheckGroup(i).selectFromDropdown().selectByIndex(index);
		case "radio item":
			if (TagType.equalsIgnoreCase("radio item")) {
				getCF_TagTypes(i).selectFromDropdown().selectByVisibleText("Radio Item");
				getCF_RadioGrpObject().waitAndClick(10);
				driver.scrollPageToTop();
				getCodingForm_AddToFormButton().waitAndClick(10);
				getCF_RadioGroup(i).selectFromDropdown().selectByIndex(index);
			}
		}
	}

	/**
	 * @author Sakthivel date:03/01/2022 Modified date:NA
	 * @throws InterruptedException
	 * @Description :this method is creating CodingForm Tags.
	 */
	public void createTagsSavedInCf(String cfName) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		getAddNewCodingFormBtn().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCodingFormName().Visible();
			}
		}), Input.wait30);
		getCodingFormName().SendKeys(cfName);
		base.waitForElement(getCodingForm_FirstTag());
		addTwoCheckBox(Input.tag);
		addcodingFormAddButton();
		getCF_AddLogicButton().waitAndClick(10);
		base.waitForElement(getCF_Object1());
		getCF_Object1().selectFromDropdown().selectByIndex(1);
		driver.scrollingToBottomofAPage();
		base.waitForElement(getCF_AddLogicButton(2));
		getCF_AddLogicButton(2).waitAndClick(5);
		base.waitForElement(getCF_Object1(1));
		getCF_Object1(1).selectFromDropdown().selectByIndex(1);
		base.waitForElement(getCF_FieldLogicCondition(1));
		getCF_FieldLogicCondition(1).selectFromDropdown().selectByIndex(1);
		base.waitForElement(getCF_FieldLogicAction(1));
		getCF_FieldLogicAction(1).selectFromDropdown().selectByIndex(2);
		driver.scrollPageToTop();
		base.waitForElement(getSaveCFBtn());
		getSaveCFBtn().Click();
		getCodingForm_Validation_ButtonYes().Click();
		System.out.println("Coding form " + cfName + " created");
		base.VerifySuccessMessage("Coding Form Saved successfully");
		base.CloseSuccessMsgpopup();
	}

	/**
	 * @author Sakthivel date:03/01/2022 Modified date:NA
	 * @Description : this method is verify CodingForm preview CheckBox Tags is
	 *              selected or UnSelected.
	 */
	public void verifyCfTagDependentSelctedOrNot() throws InterruptedException {
		driver.waitForPageToBeReady();
		getCF_PreviewButton().waitAndClick(10);
		base.waitForElement(getCF_Preview1());
		getCF_Preview1().waitAndClick(10);
		driver.waitForPageToBeReady();
		softAssertion.assertFalse(getCF_Preview2Disable().Enabled());
		base.stepInfo("Codingform preview tag2 checbox is disabled ");
		driver.waitForPageToBeReady();
		getCF_Preview1().waitAndClick(5);
		driver.waitForPageToBeReady();
		getCF_Preview2().waitAndClick(5);
		driver.waitForPageToBeReady();
		getCF_Preview1().waitAndClick(5);
		driver.waitForPageToBeReady();
		softAssertion.assertTrue(getCF_Preview1().Selected());
		base.stepInfo("Codingform preview tag1 checbox is selected");
		driver.waitForPageToBeReady();
		getCF_Preview1().waitAndClick(5);
		driver.waitForPageToBeReady();
		softAssertion.assertFalse(getCF_Preview1().Selected());
		base.stepInfo("Codingform preview tag1 checbox is unselected");
	}

	/**
	 * @author Sakthivel date:05/01/2022 Modified date:NA
	 * @Description :this method is creating CodingForm Objects saved.
	 * @throws InterruptedException
	 */
	public void verifyCodingFormObjectSaved(String cfName) throws InterruptedException {
		// create new coding form
		driver.waitForPageToBeReady();
		firstCheckBox(Input.comments);
		firstCheckBox(Input.metaData);
		specialObjectsBox("staticText");
		specialObjectsBox("radio");
		specialObjectsBox("check");
		addcodingFormAddButton();
		getCF_TagTypes(0).selectFromDropdown().selectByVisibleText("Check Item");
		driver.waitForPageToBeReady();
		getCF_CheckGroup(0).selectFromDropdown().selectByIndex(1);
		getCodingForm_HelpMsg(0).SendKeys(Input.helpText);
		driver.waitForPageToBeReady();
		selectDefaultActions(1, Input.hidden);
		getCodingForm_ErrorMsg(1).SendKeys(Input.errorMsg);
		getCodingForm_HelpMsg(1).SendKeys(Input.helpText);
		driver.waitForPageToBeReady();
		selectDefaultActions(2, Input.notSelectable);
		getCodingForm_ErrorMsg(2).SendKeys(Input.errorMsg);
		getCodingForm_HelpMsg(2).SendKeys(Input.helpText);
		driver.waitForPageToBeReady();
		selectDefaultActions(4, Input.optional);
		getCodingForm_ErrorMsg(4).SendKeys(Input.errorMsg);
		getCodingForm_HelpMsg(4).SendKeys(Input.helpText);
		driver.waitForPageToBeReady();
		selectDefaultActions(5, Input.required);
		getCodingForm_ErrorMsg(5).SendKeys(Input.errorMsg);
		getCodingForm_HelpMsg(5).SendKeys(Input.helpText);
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		addLogicOptionWithIndex(1, 0, Input.select, Input.thisHidden);
		driver.waitForPageToBeReady();
		addLogicOptionWithIndex(2, 0, Input.notSelect, Input.thisReadOnly);
		driver.waitForPageToBeReady();
		addLogicOptionWithIndexWithoutIncreace(4, 0, Input.select, Input.thisOptional);
		addLogicOptionWithIndexWithoutIncreace(5, 0, Input.notSelect, Input.thisRequired);
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium-Baskar
	 */
	// Reusable method for creating large coding form
	public void codingFormLarge(String cfName) throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		addNewCodingFormButton();
		base.waitForElement(getCodingForm_EDITABLE_METADATA_Tab());
		getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(10);
		for (int i = 1; i <= 25; i++) {
			base.waitForElement(getCodingForm_MetaDataUsingIndex(i));
			getCodingForm_MetaDataUsingIndex(i).waitAndClick(5);
		}
		base.stepInfo("Coding form for created for 25 fields");
		addcodingFormAddButton();
		passingCodingFormName(cfName);
		saveCodingForm();
	}

	/**
	 * @author Indium-Baskar date: 03/03/2022 Modified date: 03/03/2022
	 * @Description:This method used to create coding form
	 * @param cfName
	 * @param small
	 * @param tiny
	 * @param average
	 * @param bit
	 * @param action
	 */
	public void creatingCodingFormusingMultipleNvachar(String cfName, String tiny, String small, String average,
			String bit, String action) throws InterruptedException {
		driver.waitForPageToBeReady();
		addNewCodingFormButton();
		basedOnCreatingNewObject(null, null, tiny, "metadata");
		addcodingFormAddButton();
		driver.scrollPageToTop();
		basedOnCreatingNewObject(null, null, small, "metadata");
		addcodingFormAddButton();
		driver.scrollPageToTop();
		basedOnCreatingNewObject(null, null, average, "metadata");
		addcodingFormAddButton();
		driver.scrollPageToTop();
		basedOnCreatingNewObject(null, null, bit, "metadata");
		addcodingFormAddButton();
		addcodingFormAddButton();
		selectDefaultActions(0, action);
		selectDefaultActions(1, action);
		selectDefaultActions(2, action);
		selectDefaultActions(3, action);
		driver.scrollPageToTop();
		passingCodingFormName(cfName);
		saveCodingForm();

	}

	public void validateTotalShowingCountInCF() throws InterruptedException {

		driver.waitForPageToBeReady();
		ElementCollection totalShowingCount = getCFTableTotalShowingCount();
		int showingCountInCF = totalShowingCount.size();

		if (showingCountInCF < 3) {
			for (int i = 1; i <= 3; i++) {
				CodingformToSecurityGroup("CloningCodingForm" + Utility.dynamicNameAppender());
				base.passedStep("CodingForm Created Successfully");

			}

		} else {
			base.passedStep(
					"There are more than 2 Coding Form in each security Groups (1-1 in each Security Group) and are exists in newly created Project. ");
		}
	}

	public void finalVerificationForCodingForm() {
        driver.waitForPageToBeReady();
        ElementCollection totalShowingCount = getCFTableTotalShowingCount();
        int showingCountInCF = totalShowingCount.size();
        System.out.println(showingCountInCF);
        if (showingCountInCF<=3) {
            base.passedStep("There are more than 2 Coding Form in each security Groups (1-1 in each Security Group) and are exists in newly created Project. ");

        }else {
            base.failedStep("There is no Coding Form list in this prj");
        }

    }


/**@author Shilpi
 * @description This method used to assign coding form to security group
 * 
 */
 public void AssignCFstoSG(String CFName) {
	 this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 base.waitForElement(getSetCFButton());
	
	
	 getSetCFButton().ScrollTo();
	 getSetCFButton().waitAndClick(10);

	    assgnpage.SelectCFPopUp_Step1().isElementAvailable(2);
		base.stepInfo("Add / Remove Coding Forms in this Assignment Pop Up displayed.");
		base.waitForElement(assgnpage.getSelectCF_CheckBox(CFName));
		assgnpage.getSelectCF_CheckBox(CFName).ScrollTo();
		base.waitTime(1);
		assgnpage.getSelectCF_CheckBox(CFName).waitAndClick(5);
		base.waitTime(1);
		assgnpage.getSelectCodeFormRadioBtn(CFName).Click();
		base.waitTime(1);
		assgnpage.sortOrderNxtBtn().ScrollTo();
		assgnpage.sortOrderNxtBtn().Click();
		if (assgnpage.getSelectedCodeForm_inSortingPopUp(CFName).isElementAvailable(2)) {
			assgnpage.sortCodeFormOrderSaveBtn().Click();
			base.waitTime(2);
			base.passedStep("Coding Form applied successfully");
		} else {
			base.failedStep("Step-2 Sort CodeForm Pop Up Not displayed.");
		}
		base.waitForElement(getManageCodingFormButton());
		if(getManageCodingFormButton().Displayed()) {
			base.waitForElement(getCodingForm_Search());
			getCodingForm_Search().SendKeys(CFName);
		   System.out.println( getSFFormCol(CFName).getText());
		softAssertion.assertEquals("YES (Default)", getSFFormCol(CFName).getText());
		softAssertion.assertAll();
		base.passedStep("Selected a coding form and its reflected in manage coding form page as default");
       }
       else {base.failedStep("Selected  coding form is not reflected in manage coding form page as default");
		}
 
 }
 

/**@author Shilpi
 * @description This method used to assign coding form to security group
 * 
 */
 public void AssigndefaultCFstoSG(String CFName) {
	 this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 base.waitForElement(getSetCFButton());
	
	
	 getSetCFButton().ScrollTo();
	 getSetCFButton().waitAndClick(10);

	    assgnpage.SelectCFPopUp_Step1().isElementAvailable(2);
		base.stepInfo("Add / Remove Coding Forms in this Assignment Pop Up displayed.");
		base.waitForElement(assgnpage.getSelectCF_CheckBox(CFName));
	
		base.waitTime(1);
		assgnpage.getSelectCodeFormRadioBtn(CFName).Click();
		base.waitTime(1);
		assgnpage.sortOrderNxtBtn().ScrollTo();
		assgnpage.sortOrderNxtBtn().Click();
		if (assgnpage.getSelectedCodeForm_inSortingPopUp(CFName).isElementAvailable(2)) {
			assgnpage.sortCodeFormOrderSaveBtn().Click();
			base.waitTime(2);
			base.passedStep("Coding Form applied successfully");
		} else {
			base.failedStep("Step-2 Sort CodeForm Pop Up Not displayed.");
		}
		base.waitForElement(getManageCodingFormButton());
		if(getManageCodingFormButton().Displayed()) {
			base.waitForElement(getCodingForm_Search());
			getCodingForm_Search().SendKeys(CFName);
		   System.out.println( getSFFormCol(CFName).getText());
		softAssertion.assertEquals("YES (Default)", getSFFormCol(CFName).getText());
		softAssertion.assertAll();
		base.passedStep("Selected a coding form and its reflected in manage coding form page as default");
       }
       else {base.failedStep("Selected  coding form is not reflected in manage coding form page as default");
		}
 }
 
 

/**
* @author Malayala.Seenivasan
* @description this method used to check 15 checkbox
*/
public List<String> checkingBelow15CFCheckboxForSG() {
	 this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 base.waitForElement(getSetCFButton());
	 getSetCFButton().ScrollTo();
	 getSetCFButton().waitAndClick(10);
	 base.waitForElement(getStep1CfPopUp());
	 boolean flagPopup1=getStep1CfPopUp().isElementAvailable(2);
	 base.stepInfo("Add / Remove Coding Forms in this Security Group");
	 softAssertion.assertTrue(flagPopup1);
	 int unCheck=getCfUnChecBoxUsingSize().size();
	 for (int i = 0; i < unCheck; i++) {
			List<WebElement> element=getCfUnChecBoxUsingSize().FindWebElements();
			element.get(i).click();
	 }
	 int count=getCfChecBoxUsingSize().size();
	 for (int i = 0; i < count; i++) {
		List<WebElement> element=getCfChecBoxUsingSize().FindWebElements();
		element.get(i).click();
		// if more than 15 we can able to configure to sg
		if (i==14) {
			break;
		}
	}
	 List<String> name=new ArrayList<String>();
	 List<WebElement> element=getAssignedCfName().FindWebElements();
	 for (WebElement assignedName : element) {
		 name.add(assignedName.getText().trim().toString());
	}
	 softAssertion.assertAll();
	 return name;
	
	 
}

/**
* @author Malayala.Seenivasan
* @description this method used to default sg
*/

public void makingDefaultCfToSg(String CFName) {
	    base.waitTime(1);
		assgnpage.getSelectCodeFormRadioBtn(CFName).Click();
		base.waitTime(1);
		assgnpage.sortOrderNxtBtn().ScrollTo();
		base.waitForElement(sortOrderNxtBtn());
		sortOrderNxtBtn().waitAndClick(5);
		base.stepInfo("Step 02: Coding Form Order");
		if (assgnpage.getSelectedCodeForm_inSortingPopUp(CFName).isElementAvailable(2)) {
			assgnpage.sortCodeFormOrderSaveBtn().Click();
			base.waitTime(2);
			base.passedStep("Coding Form applied successfully");
		} else {
			base.failedStep("Step-2 Sort CodeForm Pop Up Not displayed.");
		}
}

/**
* @author Malayala.Seenivasan
* @description this method used to validate the default from manage screen
*/

public void validatingDefaultSgFromManageScreen(String CFName) {
	 
		base.waitForElement(getManageCodingFormButton());
		if (getManageCodingFormButton().Displayed()) {
			base.waitForElement(getCodingForm_Search());
			getCodingForm_Search().SendKeys(CFName);
			System.out.println(getSFFormCol(CFName).getText());
			String expected=getSFFormCol(CFName).getText().trim();
			String actual="YES (Default)";
			System.out.println(expected);
			System.out.println(actual);
			softAssertion.assertEquals(actual.toLowerCase(),expected.toLowerCase());
			softAssertion.assertAll();
			base.passedStep("Selected a coding form and its reflected in manage coding form page as default");
		} else {
			base.failedStep("Selected  coding form is not reflected in manage coding form page as default");
		}
	 
}

/**
 * @author Arun
 * @Description : this method used for saving codingform
 */
public void savingCodingForm() {
	driver.scrollPageToTop();
	base.waitForElement(getSaveCFBtn());
	getSaveCFBtn().waitAndClick(5);
	base.VerifySuccessMessage("Coding Form Saved successfully");
	
}
/**
* @author Malayala.Seenivasan
* @description this method used to check 15 checkbox
*/
public List<String> configureBelow15Cf(String CFName) {
	 this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	 base.waitForElement(getSetCFButton());
	 getSetCFButton().ScrollTo();
	 getSetCFButton().waitAndClick(10);
	 base.waitForElement(getStep1CfPopUp());
	 boolean flagPopup1=getStep1CfPopUp().isElementAvailable(2);
	 base.stepInfo("Step 01 : Add / Remove Coding Forms in this Security Group");
	 softAssertion.assertTrue(flagPopup1);
	 int unCheck=getCfUnChecBoxUsingSize().size();
	 for (int i = 0; i < unCheck; i++) {
			List<WebElement> element=getCfUnChecBoxUsingSize().FindWebElements();
			element.get(i).click();
	 }
	 int count=getCfChecBoxUsingSize().size();
	 for (int i = 0; i < count; i++) {
		List<WebElement> element=getCfChecBoxUsingSize().FindWebElements();
		element.get(i).click();
	 }
		base.waitTime(1);
		assgnpage.getSelectCodeFormRadioBtn(CFName).Click();
		base.waitTime(1);
		assgnpage.sortOrderNxtBtn().ScrollTo();
		base.waitForElement(assgnpage.sortOrderNxtBtn());
		assgnpage.sortOrderNxtBtn().waitAndClick(5);
		// if more than 15 we can able to configure to sg
		if (getErrorMsgMore15CF().isElementAvailable(3)) {
			base.stepInfo("User can configure only 15 cf for security group");
		}
		 this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		 base.waitForElement(getSetCFButton());
		 getSetCFButton().ScrollTo();
		 getSetCFButton().waitAndClick(10);
		 base.waitTime(2);
		int reCheck=getCfUnChecBoxUsingSize().size();
		 for (int i = 0; i < reCheck; i++) {
				List<WebElement> element=getCfUnChecBoxUsingSize().FindWebElements();
				element.get(i).click();
		 }
		 base.waitTime(2);
		 int recount=getCfChecBoxUsingSize().size();
		 for (int i = 0; i <=recount; i++) {
			List<WebElement> element=getCfChecBoxUsingSize().FindWebElements();
			element.get(i).click();
			if (i==14) {
				break;
				
			}
		 }
	 List<String> name=new ArrayList<String>();
	 List<WebElement> element=getAssignedCfName().FindWebElements();
	 for (WebElement assignedName : element) {
		 name.add(assignedName.getText().trim().toString());
	}
	 softAssertion.assertAll();
	 return name;
	
	 
	}
/**
 * @author Aathith.Senthilkumar
 * @param codingForm
 * @return boolean
 * @Description search codingform and return coding form is present or not
 */
public boolean searchCodingForm(String codingForm) {
	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	driver.waitForPageToBeReady();
	getCodingForm_Search().SendKeys(codingForm);
	driver.waitForPageToBeReady();
	if(base.text(codingForm).isElementAvailable(3)) {
		base.stepInfo(codingForm+"coding form is available");
		return true;
	}
	return false;
}

/**
 * @author Aathith.Senthilkumar
 * @param cfName
 * @Description add codingform name and click save
 */
public void addCodingFormNameOnly(String cfName) {
	
	driver.waitForPageToBeReady();
	getAddNewCodingFormBtn().waitAndClick(5);
	driver.waitForPageToBeReady();
	getCodingFormName().SendKeys(cfName);
	getSaveCFBtn().waitAndClick(5);
	base.stepInfo(cfName+" click save for this cofing form");
}

/**
 * @author Aathith.Senthilkumar
 * @Description select default coding form
 */
public void selectDefaultProjectCodingForm() {
	
	navigateToCodingFormPage();
	driver.waitForPageToBeReady();
	getSetCodingFormToSG().waitAndClick(5);
	driver.waitForPageToBeReady();
	if(checkDefaultCodingFormIsSelected().GetAttribute("checked")==null) {
	getDefaultCodingFormInputBox().waitAndClick(5);}
	if(checkDefaultCodingFormRadioBtnIsSelected().GetAttribute("checked")==null) {
	getDefaultCodingFormRadioBtn().waitAndClick(5);}
	sortOrderNxtBtn().waitAndClick(5);
	btnCodingFormSave().waitAndClick(5);
	base.stepInfo("default coding form was set as default");
}

/**
 * @author Aathith.Senthilkumar
 * @param n
 * @Description open coding form nth object
 */
public void openNthObject(int n) {
	driver.waitForPageToBeReady();
	getCF_NthObjecttab(n).waitAndClick(5);
	base.stepInfo(n+" object was opened");
	driver.waitForPageToBeReady();
}

/**
 * @author Aathith.Senthilkumar
 * @param n
 * @Description remove nth codingform object
 */
public void removeNthCodingForm(int n) {
	openNthObject(n);
	getCf_RemoveLink(n).waitAndClick(5);
	base.getYesBtn().waitAndClick(5);
	base.stepInfo(n+"th coding form was removed");
	driver.waitForPageToBeReady();
}

/**
 * @author Aathith.Senthilkumar
 * @Description update a existing codingform
 */
public void updateCodingForm() {
	driver.scrollPageToTop();
	base.waitForElement(getSaveCFBtn());
	getSaveCFBtn().waitAndClick(5);
	if(getCodingForm_Validation_ButtonYes().isElementAvailable(10)) {
		getCodingForm_Validation_ButtonYes().waitAndClick(5);
	}
	base.VerifySuccessMessage("Coding Form updated successfully");
	base.CloseSuccessMsgpopup();

}

/**
 * @author Aathith.Senthilkumar
 * @param columnName
 * @Description hide/show a column in coding from table
 */
public void hideOrShowColum(String columnName) {
	driver.waitForPageToBeReady();
	getHideShowBtn().waitAndClick(5);
	driver.waitForPageToBeReady();
	getColumnsHideShowCheckBox(columnName).waitAndClick(5);
	getHideShowBackGroundBtn().waitAndClick(5);
	base.stepInfo(columnName+" this column was hide/showned");
	driver.waitForPageToBeReady();
}




/**
 * @author Mohan.Venugopal 
 * @description: To validate Specific tags and Orders In CodingForm
 * @param cfName
 * @param ObjectName
 * @param TagType
 * @param action
 * @throws InterruptedException
 */
public void validateSpecificTagsAndOrdersInCodingForm(String cfName,String ObjectName, String TagType,String action ) throws InterruptedException {

	
	
	getAddNewCodingFormBtn().waitAndClick(10);
	driver.WaitUntil((new Callable<Boolean>() {
		public Boolean call() {
			return getCodingFormName().Visible();
		}
	}), Input.wait30);
	getCodingFormName().SendKeys(cfName);
	switch (ObjectName) {
	case "tag":
		if (ObjectName.equalsIgnoreCase("tag"))
			getCodingForm_FirstTag().waitAndClick(10);
		base.stepInfo("First Tag is added from the CodingForm");
	case "comment":
		if (ObjectName.equalsIgnoreCase("comment")) {
			getCodingForm_CommentTab().waitAndClick(10);
			getCodingForm_FirstComment().waitAndClick(10);
			base.stepInfo("First Comment is added from the CodingForm");
		}
	case "metadata":
		if (ObjectName.equalsIgnoreCase("metadata")) {
			getCodingForm_EDITABLE_METADATA_Tab().waitAndClick(10);
			getCodingForm_FirstMetadata().waitAndClick(10);
			base.stepInfo("First MetaData is added from the CodingForm");
		}
	}
	getCodingForm_AddToFormButton().waitAndClick(10);
	base.stepInfo("CodingForm with the "+ObjectName+" is successfully added to the form");
	
	base.waitTime(2);
	
	base.waitForElement(getCF_CheckGrpObject());
	getCF_CheckGrpObject().waitAndClick(10);
	
	base.waitForElement(getCodingForm_AddToFormButton());
	getCodingForm_AddToFormButton().waitAndClick(10);
	
	
	driver.scrollingToBottomofAPage();
	
	base.waitForElement(getRGDefaultAction());
	getRGDefaultAction().selectFromDropdown().selectByVisibleText(action);
	
	driver.scrollPageToTop();
	
	if (ObjectName.equalsIgnoreCase("tag")) {
		switch (TagType) {
		case "check item":
			if (TagType.equalsIgnoreCase("check item"))
				getCF_TagTypes().selectFromDropdown().selectByVisibleText("Check Item");
				getCF_CheckGroup().selectFromDropdown().selectByVisibleText("Check Group(checkgroup_1)");
		case "radio item":
			if (TagType.equalsIgnoreCase("radio item")) {
				getCF_TagTypes().selectFromDropdown().selectByVisibleText("Radio Item");
				getCF_RadioGrpObject().waitAndClick(10);
				getCodingForm_AddToFormButton().waitAndClick(10);

				base.waitTime(3);

				getCF_RadioGroup().selectFromDropdown().selectByIndex(1);
			}
		}
	}
	
	driver.scrollPageToTop();

	getCF_PreviewButton().waitAndClick(10);
	
	
	if (ObjectName.equalsIgnoreCase("tag") && TagType.equalsIgnoreCase("check item")
			&& action.equalsIgnoreCase("Make It Required")) {
		System.out.println(getCF_PreviewCheckboxValue().GetAttribute("class"));
		softAssertion.assertEquals(getCF_PreviewCheckboxValue().GetAttribute("class"),"check-group");
		softAssertion.assertTrue(getCF_PreviewCheckboxValue().Displayed());
		softAssertion.assertTrue(getCF_PreviewFieldMandatoryFieldText().Displayed());
		softAssertion.assertAll();
		base.passedStep("Coding Form is Previewed Successfully");
	}else {
		base.failedStep("Preview has some error");
	}
}

/**
 * @author Aathith.Senthilkumar
 * @param TagType
 * @param nthTag
 * @param index
 * @Description selecting tag type in coding form
 */
public void selectTagType(String TagType, int nthTag, int index) {
	switch (TagType) {
	case "check item":
		if (TagType.equalsIgnoreCase("check item"))
			getCF_TagTypes(nthTag).selectFromDropdown().selectByVisibleText("Check Item");
			getCF_CheckGroup(nthTag).selectFromDropdown().selectByIndex(index);
	case "radio item":
		if (TagType.equalsIgnoreCase("radio item")) {
			getCF_TagTypes(nthTag).selectFromDropdown().selectByVisibleText("Radio Item");
			getCF_RadioGroup(nthTag).selectFromDropdown().selectByIndex(index);
		}
	}
}

/**
 * @author Aathith.Senthilkumar
 * @param cfName
 * @Description add coding form name in input box
 */
public void addCodingFormName(String cfName) {
	navigateToCodingFormPage();
	 driver.waitForPageToBeReady();
	 base.waitForElement(getAddNewCodingFormBtn());
	 getAddNewCodingFormBtn().waitAndClick(10);
	 base.waitForElement(getCodingFormName());
	 getCodingFormName().SendKeys(cfName);
	 base.stepInfo(cfName+" coding form name is added ");
}

/**
 * @author Aathith.Senthilkumar
 * @Description click preview button in coding form 
 */
public void clickPreviewButon() {
	driver.waitForPageToBeReady();
	driver.scrollPageToTop();
	base.waitTillElemetToBeClickable(getCF_PreviewButton());
	getCF_PreviewButton().waitAndClick(5);
	driver.waitForPageToBeReady();
	base.stepInfo("preview button was clicked");
}


/**
 * @author Baskar
 * @Description copying the codingform
 */

public void copyCodingForm(String cfName) {
	this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	driver.waitForPageToBeReady();
	base.waitForElement(getCodingForm_Search());
	getCodingForm_Search().SendKeys(cfName);
	driver.waitForPageToBeReady();
	base.waitForElement(getCodingForm_CopyButton(cfName));
	getCodingForm_CopyButton(cfName).waitAndClick(5);
	driver.WaitUntil((new Callable<Boolean>() {
		public Boolean call() {
			return base.getYesBtn().Visible();
		}
	}), Input.wait60);
	String msg = getCF_DeletePopup().getText();
	System.out.println(msg);
	Assert.assertEquals("Are you sure you want to copy?", msg);
	base.getYesBtn().Click();

	base.VerifySuccessMessage("Coding form copied successfully");
	base.CloseSuccessMsgpopup();
}


/**
 * @author Baskar
 * @Description : this method used to remove all the object based on parameter
 */
public void selectRemoveLinkWithValidation(int rowNo) {
     String expectedYesNo="Are you sure?";
     String expectedDel="Delete";
	driver.waitForPageToBeReady();
	getCodingForm_SelectRemoveLink(rowNo).ScrollTo();
	base.waitForElement(getCodingForm_SelectRemoveLink(rowNo));
	getCodingForm_SelectRemoveLink(rowNo).waitAndClick(5);
	String actualYesNo=getRemoveLinkMsg_YNButton().getText();
	softAssertion.assertEquals(expectedYesNo, actualYesNo);
	String actualDel=getRemoveLinkMsg_Delete().getText();
	softAssertion.assertEquals(actualDel, expectedDel);
	base.passedStep("validation messsage as"+actualYesNo+" you want to "+actualDel+ "");
	base.waitForElement(getCodingForm_SGValidation_ButtonYes());
	getCodingForm_SGValidation_ButtonYes().waitAndClick(10);
	softAssertion.assertAll();

}

/**
 * @author Aathith.Senthilkumar
 * @Description validate coding form using validate button in coding form
 */
public void validateCodingForm() {
	driver.scrollPageToTop();
	 getCFValidateBtn().waitAndClick(10);
	 driver.waitForPageToBeReady();
	 softAssertion.assertTrue(base.text("Coding Form Validation Successful.").isElementAvailable(3));
	 getCodingForm_Validation_ButtonYes().waitAndClick(5);
	 softAssertion.assertAll();
	 base.passedStep("coding form validation validate successfully");
}

}

