package pageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ISelect;
import org.openqa.selenium.support.ui.Select;
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
    
    //added- System Level Template - Narendra
    public ElementCollection getTable(){ return driver.FindElementsByXPath("//*[@id='CodingFormDataTable']/tbody/tr/td[1]"); }
    public Element getEditClick(){ return driver.FindElementByXPath("//a[@class='btn btn-primary btn-xs edit-coding-form']"); }
    public ElementCollection getnesTable(){ return driver.FindElementsByXPath("//div[@id='nestable']//ol//li"); }
    public Element getRootClick(){ return driver.FindElementByXPath("//li[@id='0']"); }
    public Element getupClick1(){ return driver.FindElementByXPath("//li[@id='0']//i[@class='fa fa-lg fa-angle-up pull-right']"); }
    public Element getupClick2(){ return driver.FindElementByXPath("//li[@id='1']//i[@class='fa fa-lg fa-angle-up pull-right']"); }
    public Element getupClick3(){ return driver.FindElementByXPath("//li[@id='2']//i[@class='fa fa-lg fa-angle-up pull-right']"); }
    public Element getupClick4(){ return driver.FindElementByXPath("//li[@id='3']//i[@class='fa fa-lg fa-angle-up pull-right']"); }
    public Element getupClick5(){ return driver.FindElementByXPath("//li[@id='4']//i[@class='fa fa-lg fa-angle-up pull-right']"); }
    public Element getupClick6(){ return driver.FindElementByXPath("//li[@id='10']//i[@class='fa fa-lg fa-angle-up pull-right']"); }
    public Element getupClick7(){ return driver.FindElementByXPath("//li[@id='6']//i[@class='fa fa-lg fa-angle-up pull-right']"); }
    public Element getupClick8(){ return driver.FindElementByXPath("//li[@id='7']//i[@class='fa fa-lg fa-angle-up pull-right']"); }
    public Element getupClick9(){ return driver.FindElementByXPath("//li[@id='8']//i[@class='fa fa-lg fa-angle-up pull-right']"); }
    public Element getupClick10(){ return driver.FindElementByXPath("//li[@id='9']//i[@class='fa fa-lg fa-angle-up pull-right']"); }
    public Element getupClick11(){ return driver.FindElementByXPath("//li[@id='11']//i[@class='fa fa-lg fa-angle-up pull-right']"); }
    public Element getupClick12(){ return driver.FindElementByXPath("//li[@id='12']//i[@class='fa fa-lg fa-angle-up pull-right']"); }
    public Element getupClick13(){ return driver.FindElementByXPath("//li[@id='13']//i[@class='fa fa-lg fa-angle-up pull-right']"); }
    public Element getupClick14(){ return driver.FindElementByXPath("//li[@id='14']//i[@class='fa fa-lg fa-angle-up pull-right']"); }
    public Element getupClick15(){ return driver.FindElementByXPath("//li[@id='15']//i[@class='fa fa-lg fa-angle-up pull-right']"); }
    public Element getupClick16(){ return driver.FindElementByXPath("//li[@id='16']//i[@class='fa fa-lg fa-angle-up pull-right']"); }
    public Element getupClick17(){ return driver.FindElementByXPath("//li[@id='17']//i[@class='fa fa-lg fa-angle-up pull-right']"); }
    public Element getupClick18(){ return driver.FindElementByXPath("//li[@id='18']//i[@class='fa fa-lg fa-angle-up pull-right']"); }
    public Element getupClick19(){ return driver.FindElementByXPath("//li[@id='19']//i[@class='fa fa-lg fa-angle-up pull-right']"); }
    public Element getupClick20(){ return driver.FindElementByXPath("//li[@id='20']//i[@class='fa fa-lg fa-angle-up pull-right']"); }
              
    public Element getStaticText(){ return driver.FindElementByXPath("//input[@id='FriendlyInput_0']"); }
    
    public Element getRadioGroupLabel(){ return driver.FindElementByXPath("//input[@id='FriendlyInput_1']"); }
    public Element getInstructionText(){ return driver.FindElementByXPath("//input[@id='HelpText_1']"); }
    public Element getErrorMessage(){ return driver.FindElementByXPath("//input[@id='ErrorMessage_1']"); }
    
    public Element getRTagLabel(){ return driver.FindElementByXPath("//input[@id='FriendlyInput_2']"); }
    public Element getRDefaultAction(){ return driver.FindElementByXPath("//div[@id='ab-2']//i[@class='defertext']"); }
    public Element getRFieldLogic(){ return driver.FindElementByXPath("//div[@id='c-2']//div[@class='logic-box col-md-12']"); }
    public Element getRErrorMessage(){ return driver.FindElementByXPath("//div[@id='c-2']//div[@class='form-group col-md-12']//i[@class='defertext']"); }
   
    public Element getNRTagLabel(){ return driver.FindElementByXPath("//input[@id='FriendlyInput_3']"); }
    public Element getNRDefaultAction(){ return driver.FindElementByXPath("//div[@id='ab-3']//i[@class='defertext']"); }
    public Element getNRFieldLogic(){ return driver.FindElementByXPath("//div[@id='c-3']//div[@class='logic-box col-md-12']"); }
    public Element getNRErrorMessage(){ return driver.FindElementByXPath("//div[@id='c-3']//div[@class='form-group col-md-12']//i[@class='defertext']"); }
    
    public Element getTITagLabel(){ return driver.FindElementByXPath("//input[@id='FriendlyInput_4']"); }
    public Element getTIDefaultAction(){ return driver.FindElementByXPath("//div[@id='ab-4']//i[@class='defertext']"); }
    public Element getTIFieldLogic(){ return driver.FindElementByXPath("//div[@id='c-4']//div[@class='col-md-12 field-logic-container']//i[@class='defertext']"); }
    public Element getTIErrorMessage(){ return driver.FindElementByXPath("//div[@id='c-4']//div[@class='form-group col-md-12']//i[@class='defertext']"); }
    public Element getTIHelpText(){ return driver.FindElementByXPath("//input[@id='HelpText_4']"); }
    
    public Element getTIGCHECKGROUPLabel(){ return driver.FindElementByXPath("//input[@id='FriendlyInput_10']"); }
    public Element getTIGInstructionText(){ return driver.FindElementByXPath("//input[@id='HelpText_10']"); }
    public Element getTIGErrorMessage(){ return driver.FindElementByXPath("//input[@id='ErrorMessage_10']"); }
        
    public Element getPITagLabel(){ return driver.FindElementByXPath("//input[@id='FriendlyInput_6']"); }
    public Element getPIDefaultAction(){ return driver.FindElementByXPath("//div[@id='ab-6']//i[@class='defertext']"); }
    public Element getPIFieldLogic(){ return driver.FindElementByXPath("//div[@id='c-6']//div[@class='logic-box col-md-12']"); }
    public Element getPIErrorMessage(){ return driver.FindElementByXPath("//div[@id='c-6']//div[@class='form-group col-md-12']//i[@class='defertext']"); }
    public Element getPIHelpText(){ return driver.FindElementByXPath("//input[@id='HelpText_6']"); }
    
    public Element getFLTagLabel(){ return driver.FindElementByXPath("//input[@id='FriendlyInput_7']"); }
    public Element getFLDefaultAction(){ return driver.FindElementByXPath("//div[@id='ab-7']//i[@class='defertext']"); }
    public Element getFLFieldLogic(){ return driver.FindElementByXPath("//div[@id='c-7']//div[@class='col-md-12 field-logic-container']//i[@class='defertext']"); }
    public Element getFLErrorMessage(){ return driver.FindElementByXPath("//div[@id='c-7']//div[@class='form-group col-md-12']//i[@class='defertext']"); }
    public Element getFLHelpText(){ return driver.FindElementByXPath("//input[@id='HelpText_7']"); }
    
    public Element getHCTagLabel(){ return driver.FindElementByXPath("//input[@id='FriendlyInput_8']"); }
    public Element getHCDefaultAction(){ return driver.FindElementByXPath("//div[@id='ab-8']//i[@class='defertext']"); }
    public Element getHCFieldLogic(){ return driver.FindElementByXPath("//div[@id='c-8']//div[@class='col-md-12 field-logic-container']//i[@class='defertext']"); }
    public Element getHCErrorMessage(){ return driver.FindElementByXPath("//div[@id='c-8']//div[@class='form-group col-md-12']//i[@class='defertext']"); }
    public Element getHCHelpText(){ return driver.FindElementByXPath("//input[@id='HelpText_8']"); }
    
    public Element getHDTagLabel(){ return driver.FindElementByXPath("//input[@id='FriendlyInput_9']"); }
    public Element getHDHelpText(){ return driver.FindElementByXPath("//input[@id='HelpText_9']"); }
    
    public Element getPGTagLabel(){ return driver.FindElementByXPath("//input[@id='FriendlyInput_11']"); }
    public Element getPGInstructionText(){ return driver.FindElementByXPath("//input[@id='HelpText_11']"); }
    public Element getPGErrorMessage(){ return driver.FindElementByXPath("//input[@id='ErrorMessage_11']"); }
    
    public Element getPTagLabel(){ return driver.FindElementByXPath("//input[@id='FriendlyInput_12']"); }
    public Element getPDefaultAction(){ return driver.FindElementByXPath("//div[@id='ab-12']//i[@class='defertext']"); }
    public Element getPFieldLogic(){ return driver.FindElementByXPath("//div[@id='c-12']//div[@class='col-md-12 field-logic-container']//i[@class='defertext']"); }
    public Element getPErrorMessage(){ return driver.FindElementByXPath("//div[@id='c-12']//div[@class='form-group col-md-12']//i[@class='defertext']"); }
    
    public Element getNPTagLabel(){ return driver.FindElementByXPath("//input[@id='FriendlyInput_13']"); }
    public Element getNPDefaultAction(){ return driver.FindElementByXPath("//div[@id='ab-13']//i[@class='defertext']"); }
    public Element getNPFieldLogic(){ return driver.FindElementByXPath("//div[@id='c-13']//div[@class='col-md-12 field-logic-container']//i[@class='defertext']"); }
    public Element getNPErrorMessage(){ return driver.FindElementByXPath("//div[@id='c-13']//div[@class='form-group col-md-12']//i[@class='defertext']"); }
    
    public Element getPTGCHECKGROUPLabel(){ return driver.FindElementByXPath("//input[@id='FriendlyInput_14']"); }
    public Element getPTGInstructionText(){ return driver.FindElementByXPath("//input[@id='HelpText_14']"); }
    public Element getPTGErrorMessage(){ return driver.FindElementByXPath("//input[@id='ErrorMessage_14']"); }
    
    public Element getACTagLabel(){ return driver.FindElementByXPath("//input[@id='FriendlyInput_15']"); }
    public Element getACDefaultAction(){ return driver.FindElementByXPath("//div[@id='ab-15']//i[@class='defertext']"); }
    public Element getACFieldLogic(){ return driver.FindElementByXPath("//div[@id='c-15']//div[@class='col-md-12 field-logic-container']//i[@class='defertext']"); }
    public Element getACErrorMessage(){ return driver.FindElementByXPath("//div[@id='c-15']//div[@class='form-group col-md-12']//i[@class='defertext']"); }
    
    public Element getAWPTagLabel(){ return driver.FindElementByXPath("//input[@id='FriendlyInput_16']"); }
    public Element getAWPDefaultAction(){ return driver.FindElementByXPath("//div[@id='ab-16']//i[@class='defertext']"); }
    public Element getAWPFieldLogic(){ return driver.FindElementByXPath("//div[@id='c-16']//div[@class='col-md-12 field-logic-container']//i[@class='defertext']"); }
    public Element getAWPErrorMessage(){ return driver.FindElementByXPath("//div[@id='c-16']//div[@class='form-group col-md-12']//i[@class='defertext']"); }
    
    public Element getCGTagLabel(){ return driver.FindElementByXPath("//input[@id='FriendlyInput_17']"); }
    public Element getCGInstructionText(){ return driver.FindElementByXPath("//input[@id='HelpText_17']"); }
    public Element getCGErrorMessage(){ return driver.FindElementByXPath("//input[@id='HelpText_17']"); }
    
    public Element getCTagLabel(){ return driver.FindElementByXPath("//input[@id='FriendlyInput_18']"); }
    public Element getCDefaultAction(){ return driver.FindElementByXPath("//div[@id='ab-18']//i[@class='defertext']"); }
    public Element getCFieldLogic(){ return driver.FindElementByXPath("//div[@id='c-18']//div[@class='col-md-12 field-logic-container']//i[@class='defertext']"); }
    public Element getCErrorMessage(){ return driver.FindElementByXPath("//div[@id='c-18']//div[@class='form-group col-md-12']//i[@class='defertext']"); }
    
    public Element getHTagLabel(){ return driver.FindElementByXPath("//input[@id='FriendlyInput_19']"); }
    public Element getHDefaultAction(){ return driver.FindElementByXPath("//div[@id='ab-19']//i[@class='defertext']"); }
    public Element getHFieldLogic(){ return driver.FindElementByXPath("//div[@id='c-19']//div[@class='col-md-12 field-logic-container']//i[@class='defertext']"); }
    public Element getHErrorMessage(){ return driver.FindElementByXPath("//div[@id='c-19']//div[@class='form-group col-md-12']//i[@class='defertext']"); }
    
    public Element getDCCOMMENTLabel(){ return driver.FindElementByXPath("//input[@id='FriendlyInput_20']"); }
    public Element getDCErrorMessage(){ return driver.FindElementByXPath("//input[@id='ErrorMessage_20']"); }
    public Element getDCHelpText(){ return driver.FindElementByXPath("//input[@id='HelpText_20']"); }
    
    public Element getRGDefaultAction(){ return driver.FindElementByXPath("//select[@id='DefaultAction_1']"); }

    public Element getRTagType(){ return driver.FindElementByXPath("//select[@id='2']"); }
    public Element getRGroupAssociation(){ return driver.FindElementByXPath("//select[@id='TagRdo_2']"); }

    public Element getNRTagType(){ return driver.FindElementByXPath("//select[@id='3']"); }
    public Element getNRGroupAssociation(){ return driver.FindElementByXPath("//select[@id='TagRdo_3']"); }

    public Element getTITagType(){ return driver.FindElementByXPath("//select[@id='4']"); }
    public Element getTIGroupAssociation(){ return driver.FindElementByXPath("//select[@id='TagRdo_4']"); }

    public Element getTIGDefaultAction(){ return driver.FindElementByXPath("//select[@id='DefaultAction_10']"); }
    public Element getTIGObject(){ return driver.FindElementByXPath("//select[@id='SelectObjects_10_1']"); }
    public Element getTIGCondition(){ return driver.FindElementByXPath("//select[@id='SelectCondition_10_1']"); }
    public Element getTIGAction(){ return driver.FindElementByXPath("//select[@id='LogicFieldAction_10']"); }

    public Element getPITagType(){ return driver.FindElementByXPath("//select[@id='6']"); }
    public Element getPIGroupAssociation(){ return driver.FindElementByXPath("//select[@id='TagChk_6']"); }

    public Element getFLTagType(){ return driver.FindElementByXPath("//select[@id='7']"); }
    public Element getFLGroupAssociation(){ return driver.FindElementByXPath("//select[@id='TagChk_7']"); }

    public Element getHCTagType(){ return driver.FindElementByXPath("//select[@id='8']"); }
    public Element getHCGroupAssociation(){ return driver.FindElementByXPath("//select[@id='TagChk_8']"); }

    public Element getHDTagType(){ return driver.FindElementByXPath("//select[@id='9']"); }
    public Element getHDGroupAssociation(){ return driver.FindElementByXPath("//select[@id='TagChk_9']"); }
    public Element getHDDefaultAction(){ return driver.FindElementByXPath("//select[@id='DefaultAction_9']"); }

    public Element getPGDefaultAction(){ return driver.FindElementByXPath("//select[@id='DefaultAction_11']"); }

    public Element getPTagType(){ return driver.FindElementByXPath("//select[@id='12']"); }
    public Element getPGroupAssociation(){ return driver.FindElementByXPath("//select[@id='TagRdo_12']"); }

    public Element getNPTagType(){ return driver.FindElementByXPath("//select[@id='13']"); }
    public Element getNPGroupAssociation(){ return driver.FindElementByXPath("//select[@id='TagRdo_13']");}

    public Element getPTGDefaultAction(){ return driver.FindElementByXPath("//select[@id='DefaultAction_14']"); }
    public Element getPTGObject(){ return driver.FindElementByXPath("//select[@id='SelectObjects_14_1']"); }
    public Element getPTGCondition(){ return driver.FindElementByXPath("//select[@id='SelectCondition_14_1']"); }
    public Element getPTGAction(){ return driver.FindElementByXPath("//select[@id='LogicFieldAction_14']"); }

    public Element getACTagType(){ return driver.FindElementByXPath("//select[@id='15']"); }
    public Element getACGroupAssociation(){ return driver.FindElementByXPath("//select[@id='TagChk_15']"); }

    public Element getAWPTagType(){ return driver.FindElementByXPath("//select[@id='16']"); }
    public Element getAWPGroupAssociation(){ return driver.FindElementByXPath("//select[@id='TagChk_16']"); }

    public Element getCGDefaultAction(){ return driver.FindElementByXPath("//select[@id='DefaultAction_17']"); }

    public Element getCTagType(){ return driver.FindElementByXPath("//select[@id='18']"); }
    public Element getCGroupAssociation(){ return driver.FindElementByXPath("//select[@id='TagRdo_18']"); }

    public Element getHTagType(){ return driver.FindElementByXPath("//select[@id='19']"); }
    public Element getHGroupAssociation(){ return driver.FindElementByXPath("//select[@id='TagRdo_19']"); }

    public Element getDCDefaultAction(){ return driver.FindElementByXPath("//select[@id='DefaultAction_20']"); }
    public Element getDCObject(){ return driver.FindElementByXPath("//select[@id='SelectObjects_20_1']"); }
    public Element getDCCondition(){ return driver.FindElementByXPath("//select[@id='SelectCondition_20_1']"); }
    public Element getDCAction(){ return driver.FindElementByXPath("//select[@id='LogicFieldAction_20']"); }
    
    public Element getResponsiveCheked(){ return driver.FindElementByXPath("//div[@id='item1']//div[@id='0_radiogroup']//div[1]//div[1]//label[1]//span[1]"); }
    public Element getNotResponsiveCheked(){ return driver.FindElementByXPath("//div[@id='item1']//div[2]//div[1]//label[1]//span[1]"); }
    public Element getTechnicalIssueCheked(){ return driver.FindElementByXPath("//div[@id='item1']//div[3]//div[1]//label[1]//span[1]"); }
    public Element getTestUrCodeClick(){ return driver.FindElementByXPath("//input[@id='previewForm']"); }
    public Element getError1(){ return driver.FindElementByXPath("//span[@class='validationSpan']"); }
    public Element getError2(){ return driver.FindElementByXPath("//td[@id='td_checkgroup_10']//span[@class='validationSpan']"); } 
    public Element getError4(){ return driver.FindElementByXPath("//td[@class='form-c c-form-textwidth option-group-cell']//span[@class='validationSpan']"); } 
    public Element getError3(){ return driver.FindElementByXPath("//td[@id='td_COMMENT_1']//span[@class='validationSpan']"); }

    String expectedStaticText ="This is the default Coding Form for this project.  Please complete all fields.";
    String expectedGroupText ="Responsive Group";
    String expectedInstructionText ="Responsiveness";
    String expectedPGInstructionText ="Privileged";
    String expectedPTGInstructionText ="Privilege Type";
    String expectedCGInstructionText ="Confidentiality";
    String expectedDefaultAction1Text ="Make It Required";
    String expectedDefaultAction2Text ="Make It Hidden";
    String expectedDefaultAction3Text ="Make It Optional";
    String expectedDefaultAction4Text ="Make It Display But Not Selectable";
    String expectedGErrorMsgText ="You must select one of these options";
    String expectedRTagLabelText ="Responsive";
    String expectedNRTagLabelText ="Not_Responsive";
    String expectedTITagLabelText ="Technical_Issue";
    String expectedFLTagLabelText ="Foreign_Language";
    String expectedHCTagLabelText ="Hidden_Content";
    String expectedHDTagLabelText ="Hot_Doc";
    String expectedPGTagLabelText ="Privileged Group";
    String expectedPTagLabelText ="Privileged";
    String expectedNPTagLabelText ="Not_Privileged";
    String expectedPTGTagLabelText ="Privilege Type Group";
    String expectedACTagLabelText ="Attorney_Client";
    String expectedAWPTagLabelText ="Attorney_WorkProduct";
    String expectedCGTagLabelText ="Confidentiality Group";
    String expectedCTagLabelText ="Confidential";
    String expectedHTagLabelText ="Highly_Confidential";
    String expectedDCTagLabelText ="Document_Comments";
    String expectedTagType1Text ="Radio Item";
    String expectedTagType2Text ="Check Item";
    String expectedGroupAssociation1Text ="Responsive Group(radiogroup_1)";
    String expectedGroupAssociation2Text ="Tech Issue Group(checkgroup_10)";
    String expectedGroupAssociation3Text ="Privileged Group(radiogroup_11)";
    String expectedGroupAssociation4Text ="Confidentiality Group(radiogroup_17)";
    String expectedGroupAssociation5Text ="Privilege Type Group(checkgroup_14)";
    String expectedGroupAssociation6Text ="No Checkbox Group Association";
    String expectedDefaultActionText ="Actions controlled at group level";
    String expectedFieldLogicText ="Field logic controlled at group level";
    String expectedErrorMsgText ="Error message controlled at group level";
    String expectedErrorMessageText ="You must determine whether the doc is privileged or not";
    String expectedTIHelpText ="Is there some reason that review cannot be determined?";
    String expectedTIGCHECKGROUPLabel ="Tech Issue Group";
    String expectedTIGInstructionText ="Tech Issue Group";  
    String expectedFieldLogicObjectText1 = "Technical_Issue(TAG_14)";
    String expectedFieldLogicObjectText2 = "Privileged(TAG_11)";
    String expectedFieldLogicConditionText = "Selected";
    String expectedFieldLogicActionText = "Make this Required";
    String expectedTIGErrorMsgText ="If the document has a technical issue and cannot be reviewed, you must select a reason why from this list above";
    String expectedDCErrorMsgText ="If you select Technical Issue, you must enter a comment";
    String expectedPITagLabelText ="Processing_Issue";
    String expectedPIHelpText ="Does this doc contain some type of issue that prohibits the ability for the record to be reviewed?";
    String expectedFLHelpText ="Does this doc contain some language besides what you can review?";
    String expectedHCHelpText ="Do you believe the document contains some hidden content?";
    String expectedHDHelpText ="Check this tag if this document critical to surface to lead attorneys on the matter immediately";
    String expectedDCHelpText ="Please enter any general comments related to review of this document";
    
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
	   

    	//added by Narendra
    		
		public void codingForm(){

  			    this.driver.getWebDriver().get(Input.url+ "CodingForm/Create");		
  			    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
  	 	   	    getCFlistTable().Visible()  ;}}),Input.wait30); 	   	   
  	 	   	    //System.out.println(getCFnames().size());
  	 	   	    boolean nextPage= true;
			  
			    while(nextPage){
			    	
			    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
			    	getTable().Visible()  ;}}),Input.wait30);
					//System.out.println("Number of records in a current page : "+getTable().size());
					List<String> tableele = new ArrayList<String>();
	 	   		 	List<WebElement> table = getTable().FindWebElements();
					for (int i = 0; i<getTable().size();i++) {
						tableele.add(table.get(i).getText());
						if (tableele.contains("Default Project Coding Form")) {
						Assert.assertTrue(tableele.contains("Default Project Coding Form"));
						System.out.println("Verified provisioned CF is available in the Project");
						}
						
					}
					try{
						   driver.scrollingToBottomofAPage();
						   driver.getWebDriver().findElement(By.xpath("//li[@class='paginate_button next disabled']/a")).isDisplayed();
						   nextPage = false;
					   }
					   catch (Exception e) {
						   driver.getWebDriver().findElement(By.linkText("Next")).click(); 
					 } 
					
	
  	 	   	  }
  		}
						
		public void codingFormTagsOrder() throws InterruptedException {
		  			    this.driver.getWebDriver().get(Input.url+ "CodingForm/Create");		
		  			    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
		  	 	   	    getCFlistTable().Visible()  ;}}),Input.wait30); 	   	   
		  	 	   	    //System.out.println(getCFnames().size());
		  	 	   	    boolean nextPage= true;
					  
					    while(nextPage){
					    	
					    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					    	getTable().Visible()  ;}}),Input.wait30);
							//System.out.println("Number of records in a current page : "+getTable().size());
							List<String> tableele = new ArrayList<String>();
			 	   		 	List<WebElement> table = getTable().FindWebElements();
							for (int i = 0; i<getTable().size();i++) {
								tableele.add(table.get(i).getText());
								if (tableele.contains("Default Project Coding Form")) {
									getEditClick().waitAndClick(10);
									
									//for (int j = 0; j<getnesTable().size();j++) {
										getupClick1().waitAndClick(10);
									    String getStaticTxt = getStaticText().GetAttribute("Value");
										Assert.assertEquals(getStaticTxt,expectedStaticText);
										System.out.println("Verified Static Text");
										Thread.sleep(3000);
										
										
										getupClick2().waitAndClick(10);
										String getGroupTxt = getRadioGroupLabel().GetAttribute("Value");
										Assert.assertEquals(getGroupTxt,expectedGroupText);
																				
										String getInstructionTxt = getInstructionText().GetAttribute("Value");
										Assert.assertEquals(getInstructionTxt,expectedInstructionText);
																				
										WebElement optionG = getRGDefaultAction().selectFromDropdown().getFirstSelectedOption();
										String getGDefaultActionTxt = optionG.getText();
										Assert.assertEquals(getGDefaultActionTxt,expectedDefaultAction1Text);
										
										String getErrorMessageTxt = getErrorMessage().GetAttribute("Value");
										Assert.assertEquals(getErrorMessageTxt,expectedGErrorMsgText);
										System.out.println("Verified Responsive Group");
										Thread.sleep(3000);
										
										getupClick3().waitAndClick(10);
										String getRTagLabelTxt = getRTagLabel().GetAttribute("Value");
										Assert.assertEquals(getRTagLabelTxt,expectedRTagLabelText );
																				
										
										WebElement optionRT = getRTagType().selectFromDropdown().getFirstSelectedOption();
										String getRTagTypeTxt = optionRT.getText();
										Assert.assertEquals(getRTagTypeTxt,expectedTagType1Text);
																				
										
										WebElement optionRA = getRGroupAssociation().selectFromDropdown().getFirstSelectedOption();
										String getRGroupAssociationTxt = optionRA.getText();
										Assert.assertEquals(getRGroupAssociationTxt,expectedGroupAssociation1Text);
																				
										String getRDefaultAction2Txt = getRDefaultAction().getText();
										Assert.assertEquals(getRDefaultAction2Txt,expectedDefaultActionText);
																				
										String getRFieldLogicTxt = getRFieldLogic().getText();
										Assert.assertEquals(getRFieldLogicTxt,expectedFieldLogicText);
																													
										String getRErrorMessage2Txt = getRErrorMessage().getText();
										Assert.assertEquals(getRErrorMessage2Txt,expectedErrorMsgText);
										System.out.println("Verified Responsive");
										Thread.sleep(3000);
										
										getupClick4().waitAndClick(10);
										String getNRTagLabelTxt = getNRTagLabel().GetAttribute("Value");
										Assert.assertEquals(getNRTagLabelTxt,expectedNRTagLabelText);
																			
										
										WebElement optionNRT = getNRTagType().selectFromDropdown().getFirstSelectedOption();
										String getNRTagTypeTxt = optionNRT.getText();
										Assert.assertEquals(getNRTagTypeTxt,expectedTagType1Text);
																				
										
										WebElement optionNRA = getNRGroupAssociation().selectFromDropdown().getFirstSelectedOption();
										String getNRGroupAssociationTxt = optionNRA.getText();
										Assert.assertEquals(getNRGroupAssociationTxt,expectedGroupAssociation1Text);
																				
										String getNRDefaultAction2Txt = getNRDefaultAction().getText();
										Assert.assertEquals(getNRDefaultAction2Txt,expectedDefaultActionText);
																				
										String getNRFieldLogicTxt = getNRFieldLogic().getText();
										Assert.assertEquals(getNRFieldLogicTxt,expectedFieldLogicText);
																													
										String getNRErrorMessage2Txt = getNRErrorMessage().getText();
										Assert.assertEquals(getNRErrorMessage2Txt,expectedErrorMsgText);
										System.out.println("Verified Not Responsive");
										Thread.sleep(3000);
										
										getupClick5().waitAndClick(10);
										String getTITagLabelTxt = getTITagLabel().GetAttribute("Value");
										Assert.assertEquals(getTITagLabelTxt,expectedTITagLabelText);
																		
										
										WebElement optionTIT = getTITagType().selectFromDropdown().getFirstSelectedOption();
										String getTITagTypeTxt = optionTIT.getText();
										Assert.assertEquals(getTITagTypeTxt,expectedTagType1Text);
																				
										
										WebElement optionTIA = getTIGroupAssociation().selectFromDropdown().getFirstSelectedOption();
										String getTIGroupAssociationTxt = optionTIA.getText();
										Assert.assertEquals(getTIGroupAssociationTxt,expectedGroupAssociation1Text);
																				
										String getTIDefaultActionTxt = getTIDefaultAction().getText();
										Assert.assertEquals(getTIDefaultActionTxt,expectedDefaultActionText);
																				
										String getTiFieldLogicTxt = getTIFieldLogic().getText();
										Assert.assertEquals(getTiFieldLogicTxt,expectedFieldLogicText);
																													
										String getTIErrorMessageTxt = getTIErrorMessage().getText();
										Assert.assertEquals(getTIErrorMessageTxt,expectedErrorMsgText);
																				
										String getTIHelpTxt = getTIHelpText().GetAttribute("Value");
										Assert.assertEquals(getTIHelpTxt,expectedTIHelpText);
										System.out.println("Verified Technical_Issue");
										Thread.sleep(3000);
										
										getupClick6().waitAndClick(10);
										String getTIGGroupLabelTxt = getTIGCHECKGROUPLabel().GetAttribute("Value");
										Assert.assertEquals(getTIGGroupLabelTxt,expectedTIGCHECKGROUPLabel);
																				
										String getTIGInstructionTxt = getTIGInstructionText().GetAttribute("Value");
										Assert.assertEquals(getTIGInstructionTxt,expectedTIGInstructionText);
																				
										
										WebElement optionTIG = getTIGDefaultAction().selectFromDropdown().getFirstSelectedOption();
										String getTIGDefaultActionTxt = optionTIG.getText();
										Assert.assertEquals(getTIGDefaultActionTxt,expectedDefaultAction2Text);
																				
										
										WebElement optionTIGO = getTIGObject().selectFromDropdown().getFirstSelectedOption();
										String getTIGObjectTxt = optionTIGO.getText();
										Assert.assertEquals(getTIGObjectTxt,expectedFieldLogicObjectText1);
																				
										
										WebElement optionTIGC= getTIGCondition().selectFromDropdown().getFirstSelectedOption();
										String getTIGConditionTxt = optionTIGC.getText();
										Assert.assertEquals(getTIGConditionTxt,expectedFieldLogicConditionText);
																				
										
										WebElement optionTIGA = getTIGAction().selectFromDropdown().getFirstSelectedOption();
										String getTIGActionTxt = optionTIGA.getText();
										Assert.assertEquals(getTIGActionTxt,expectedFieldLogicActionText);								
																									
										String getTIGErrorMessageTxt = getTIGErrorMessage().GetAttribute("Value");
										Assert.assertEquals(getTIGErrorMessageTxt,expectedTIGErrorMsgText);
										System.out.println("Verified Tech Issue Group");
										Thread.sleep(3000);
										

										getupClick7().waitAndClick(10);
										String getPITagLabelTxt = getPITagLabel().GetAttribute("Value");
										Assert.assertEquals(getPITagLabelTxt,expectedPITagLabelText);
																				
										
										WebElement optionPIT = getPITagType().selectFromDropdown().getFirstSelectedOption();
										String getPITagTypeTxt = optionPIT.getText();
										Assert.assertEquals(getPITagTypeTxt,expectedTagType2Text);
																				
										
										WebElement optionPIA = getPIGroupAssociation().selectFromDropdown().getFirstSelectedOption();
										String getPIGroupAssociationTxt = optionPIA.getText();
										Assert.assertEquals(getPIGroupAssociationTxt,expectedGroupAssociation2Text);
																				
										String getPIDefaultActionTxt = getNRDefaultAction().getText();
										Assert.assertEquals(getPIDefaultActionTxt,expectedDefaultActionText);
																				
										String getPIFieldLogicTxt = getNRFieldLogic().getText();
										Assert.assertEquals(getPIFieldLogicTxt,expectedFieldLogicText);
																													
										String getPIErrorMessageTxt = getNRErrorMessage().getText();
										Assert.assertEquals(getPIErrorMessageTxt,expectedErrorMsgText);
																				
										String getPIHelpTxt = getPIHelpText().GetAttribute("Value");
										Assert.assertEquals(getPIHelpTxt,expectedPIHelpText);
										System.out.println("Verified Processing Issue");
										Thread.sleep(3000);
										
										getupClick8().waitAndClick(10);
										String getFLTagLabelTxt = getFLTagLabel().GetAttribute("Value");
										Assert.assertEquals(getFLTagLabelTxt,expectedFLTagLabelText);
																				
										
										WebElement optionFLT = getFLTagType().selectFromDropdown().getFirstSelectedOption();
										String getFLTagTypeTxt = optionFLT.getText();
										Assert.assertEquals(getFLTagTypeTxt,expectedTagType2Text);
																				
										
										WebElement optionFLA = getFLGroupAssociation().selectFromDropdown().getFirstSelectedOption();
										String getFLGroupAssociationTxt = optionFLA.getText();
										Assert.assertEquals(getFLGroupAssociationTxt,expectedGroupAssociation2Text);
																				
										String getFLDefaultActionTxt = getFLDefaultAction().getText();
										Assert.assertEquals(getFLDefaultActionTxt,expectedDefaultActionText);
																				
										String getFLFieldLogicTxt = getFLFieldLogic().getText();
										Assert.assertEquals(getFLFieldLogicTxt,expectedFieldLogicText);
																													
										String getFLErrorMessageTxt = getFLErrorMessage().getText();
										Assert.assertEquals(getFLErrorMessageTxt,expectedErrorMsgText);
																				
										String getFLHelpTxt = getFLHelpText().GetAttribute("Value");
										Assert.assertEquals(getFLHelpTxt,expectedFLHelpText );
										System.out.println("Verified Foreign Language");
										Thread.sleep(3000);
										
										getupClick9().waitAndClick(10);
										String getHCTagLabelTxt = getHCTagLabel().GetAttribute("Value");
										Assert.assertEquals(getHCTagLabelTxt,expectedHCTagLabelText);
																				
										
										WebElement optionHCT = getHCTagType().selectFromDropdown().getFirstSelectedOption();
										String getHCTagTypeTxt = optionHCT.getText();
										Assert.assertEquals(getHCTagTypeTxt,expectedTagType2Text);
																				
										
										WebElement optionHCA = getHCGroupAssociation().selectFromDropdown().getFirstSelectedOption();
										String getHCGroupAssociationTxt = optionHCA.getText();
										Assert.assertEquals(getHCGroupAssociationTxt,expectedGroupAssociation2Text);
																				
										String getHCDefaultActionTxt = getHCDefaultAction().getText();
										Assert.assertEquals(getHCDefaultActionTxt,expectedDefaultActionText);
																				
										String getHCFieldLogicTxt = getHCFieldLogic().getText();
										Assert.assertEquals(getHCFieldLogicTxt,expectedFieldLogicText);
																													
										String getHCErrorMessageTxt = getHCErrorMessage().getText();
										Assert.assertEquals(getHCErrorMessageTxt,expectedErrorMsgText);
																				
										String getHCHelpTxt = getHCHelpText().GetAttribute("Value");
										Assert.assertEquals(getHCHelpTxt,expectedHCHelpText );
										System.out.println("Verified Hidden Content");
										Thread.sleep(3000);
										
										getupClick10().waitAndClick(10);
										String getHDTagLabelTxt = getHDTagLabel().GetAttribute("Value");
										Assert.assertEquals(getHDTagLabelTxt,expectedHDTagLabelText);
																		
										
										WebElement optionHDT = getHDTagType().selectFromDropdown().getFirstSelectedOption();
										String getHDTagTypeTxt = optionHDT.getText();
										Assert.assertEquals(getHDTagTypeTxt,expectedTagType2Text);
																				
										
										WebElement optionHDA = getHDGroupAssociation().selectFromDropdown().getFirstSelectedOption();
										String getHDGroupAssociationTxt = optionHDA.getText();
										Assert.assertEquals(getHDGroupAssociationTxt,expectedGroupAssociation6Text);
										
										
										WebElement optionHDDA = getHDDefaultAction().selectFromDropdown().getFirstSelectedOption();
										String getHDDefaultActionTxt = optionHDDA.getText();
										Assert.assertEquals(getHDDefaultActionTxt,expectedDefaultAction3Text);
																									
										String getHDHelpTxt = getHDHelpText().GetAttribute("Value");
										Assert.assertEquals(getHDHelpTxt,expectedHDHelpText);
										System.out.println("Verified Hot Doc");
										Thread.sleep(3000);
										
										getupClick11().waitAndClick(10);
										String getPGTagLabelTxt = getPGTagLabel().GetAttribute("Value");
										Assert.assertEquals(getPGTagLabelTxt,expectedPGTagLabelText);
																		
										String getPGInstructionTxt = getPGInstructionText().GetAttribute("Value");
										Assert.assertEquals(getPGInstructionTxt,expectedPGInstructionText);
										
										
										WebElement optionPGDA = getPGDefaultAction().selectFromDropdown().getFirstSelectedOption();
										String getPGDefaultActionTxt = optionPGDA.getText();
										Assert.assertEquals(getPGDefaultActionTxt,expectedDefaultAction1Text);
																									
										String getPGErrorMessageTxt = getPGErrorMessage().GetAttribute("Value");
										Assert.assertEquals(getPGErrorMessageTxt,expectedErrorMessageText);
										System.out.println("Verified Privileged Group");
										Thread.sleep(3000);
										
										getupClick12().waitAndClick(10);
										String getPTagLabelTxt = getPTagLabel().GetAttribute("Value");
										Assert.assertEquals(getPTagLabelTxt,expectedPTagLabelText);
																				
										
										WebElement optionPT = getPTagType().selectFromDropdown().getFirstSelectedOption();
										String getPTagTypeTxt = optionPT.getText();
										Assert.assertEquals(getPTagTypeTxt,expectedTagType1Text);
																				
										
										WebElement optionPA = getPGroupAssociation().selectFromDropdown().getFirstSelectedOption();
										String getPGroupAssociationTxt = optionPA.getText();
										Assert.assertEquals(getPGroupAssociationTxt,expectedGroupAssociation3Text);
																				
										String getPDefaultActionTxt = getPDefaultAction().getText();
										Assert.assertEquals(getPDefaultActionTxt,expectedDefaultActionText);
																				
										String getPFieldLogicTxt = getPFieldLogic().getText();
										Assert.assertEquals(getPFieldLogicTxt,expectedFieldLogicText);
																													
										String getPErrorMessageTxt = getPErrorMessage().getText();
										Assert.assertEquals(getPErrorMessageTxt,expectedErrorMsgText);
										System.out.println("Verified Privileged");
										Thread.sleep(3000);
										
										getupClick13().waitAndClick(10);
										String getNPTagLabelTxt = getNPTagLabel().GetAttribute("Value");
										Assert.assertEquals(getNPTagLabelTxt,expectedNPTagLabelText);
																				
										
										WebElement optionNPT = getNPTagType().selectFromDropdown().getFirstSelectedOption();
										String getNPTagTypeTxt = optionNPT.getText();
										Assert.assertEquals(getNPTagTypeTxt,expectedTagType1Text);
																				
										
										WebElement optionNPA = getNPGroupAssociation().selectFromDropdown().getFirstSelectedOption();
										String getNPGroupAssociationTxt = optionNPA.getText();
										Assert.assertEquals(getNPGroupAssociationTxt,expectedGroupAssociation3Text);
																				
										String getNPDefaultActionTxt = getNPDefaultAction().getText();
										Assert.assertEquals(getNPDefaultActionTxt,expectedDefaultActionText);
																				
										String getNPFieldLogicTxt = getNPFieldLogic().getText();
										Assert.assertEquals(getNPFieldLogicTxt,expectedFieldLogicText);
																													
										String getNPErrorMessageTxt = getNPErrorMessage().getText();
										Assert.assertEquals(getNPErrorMessageTxt,expectedErrorMsgText);
										System.out.println("Verified Not Privileged");
										Thread.sleep(3000);
										
										getupClick14().waitAndClick(10);
										String getPTGGroupLabelTxt = getPTGCHECKGROUPLabel().GetAttribute("Value");
										Assert.assertEquals(getPTGGroupLabelTxt,expectedPTGTagLabelText);
																				
										String getPTGInstructionTxt = getPTGInstructionText().GetAttribute("Value");
										Assert.assertEquals(getPTGInstructionTxt,expectedPTGInstructionText);
																				
										
										WebElement optionPTG = getPTGDefaultAction().selectFromDropdown().getFirstSelectedOption();
										String getPTGDefaultActionTxt = optionPTG.getText();
										Assert.assertEquals(getPTGDefaultActionTxt,expectedDefaultAction2Text);
																				
										
										WebElement optionPTGO = getPTGObject().selectFromDropdown().getFirstSelectedOption();
										String getPTGObjectTxt = optionPTGO.getText();
										Assert.assertEquals(getPTGObjectTxt,expectedFieldLogicObjectText2);
																				
										
										WebElement optionPTGC= getPTGCondition().selectFromDropdown().getFirstSelectedOption();
										String getPTGConditionTxt = optionPTGC.getText();
										Assert.assertEquals(getPTGConditionTxt,expectedFieldLogicConditionText);
																				
										
										WebElement optionPTGA = getPTGAction().selectFromDropdown().getFirstSelectedOption();
										String getPTGOActionTxt = optionPTGA.getText();
										Assert.assertEquals(getPTGOActionTxt,expectedFieldLogicActionText);								
																									
										String getPTGErrorMessageTxt = getPTGErrorMessage().GetAttribute("Value");
										Assert.assertEquals(getPTGErrorMessageTxt,expectedErrorMessageText);
										System.out.println("Verified Privilege Type Group");
										Thread.sleep(3000);
										
										getupClick15().waitAndClick(10);
										String getACTagLabelTxt = getACTagLabel().GetAttribute("Value");
										Assert.assertEquals(getACTagLabelTxt,expectedACTagLabelText);
																				
										
										WebElement optionACT = getACTagType().selectFromDropdown().getFirstSelectedOption();
										String getACTagTypeTxt = optionACT.getText();
										Assert.assertEquals(getACTagTypeTxt,expectedTagType2Text);
																				
										
										WebElement optionACA = getACGroupAssociation().selectFromDropdown().getFirstSelectedOption();
										String getACGroupAssociationTxt = optionACA.getText();
										Assert.assertEquals(getACGroupAssociationTxt,expectedGroupAssociation5Text);
																				
										String getACDefaultActionTxt = getACDefaultAction().getText();
										Assert.assertEquals(getACDefaultActionTxt,expectedDefaultActionText);
																				
										String getACFieldLogicTxt = getACFieldLogic().getText();
										Assert.assertEquals(getACFieldLogicTxt,expectedFieldLogicText);
																													
										String getACErrorMessageTxt = getACErrorMessage().getText();
										Assert.assertEquals(getACErrorMessageTxt,expectedErrorMsgText);
										System.out.println("Verified Attorney Client");
										Thread.sleep(3000);
										
										getupClick16().waitAndClick(10);
										String getAWPTagLabelTxt = getAWPTagLabel().GetAttribute("Value");
										Assert.assertEquals(getAWPTagLabelTxt,expectedAWPTagLabelText);
																				
										
										WebElement optionAWPT = getAWPTagType().selectFromDropdown().getFirstSelectedOption();
										String getAWPTagTypeTxt = optionAWPT.getText();
										Assert.assertEquals(getAWPTagTypeTxt,expectedTagType2Text);
																				
										
										WebElement optionAWPA = getAWPGroupAssociation().selectFromDropdown().getFirstSelectedOption();
										String getAWPGroupAssociationTxt = optionAWPA.getText();
										Assert.assertEquals(getAWPGroupAssociationTxt,expectedGroupAssociation5Text);
																				
										String getAWPDefaultActionTxt = getAWPDefaultAction().getText();
										Assert.assertEquals(getAWPDefaultActionTxt,expectedDefaultActionText);
																				
										String getAWPFieldLogicTxt = getAWPFieldLogic().getText();
										Assert.assertEquals(getAWPFieldLogicTxt,expectedFieldLogicText);
																													
										String getAWPErrorMessageTxt = getAWPErrorMessage().getText();
										Assert.assertEquals(getAWPErrorMessageTxt,expectedErrorMsgText);
										System.out.println("Verified Attorney Work Product");
										Thread.sleep(3000);
										
										getupClick17().waitAndClick(10);
										String getCGTagLabelTxt = getCGTagLabel().GetAttribute("Value");
										Assert.assertEquals(getCGTagLabelTxt,expectedCGTagLabelText);
																		
										String getCGInstructionTxt = getCGInstructionText().GetAttribute("Value");
										Assert.assertEquals(getCGInstructionTxt,expectedCGInstructionText);
										
										
										WebElement optionCGA = getCGDefaultAction().selectFromDropdown().getFirstSelectedOption();
										String getCGDefaultActionTxt = optionCGA.getText();
										Assert.assertEquals(getCGDefaultActionTxt,expectedDefaultAction3Text);
										System.out.println("Verified Confidentiality Group");
										Thread.sleep(3000);
										
										getupClick18().waitAndClick(10);
										String getCTagLabelTxt = getCTagLabel().GetAttribute("Value");
										Assert.assertEquals(getCTagLabelTxt,expectedCTagLabelText);
																				
										
										WebElement optionCT = getCTagType().selectFromDropdown().getFirstSelectedOption();
										String getCTagTypeTxt = optionCT.getText();
										Assert.assertEquals(getCTagTypeTxt,expectedTagType1Text);
																				
										
										WebElement optionCA = getCGroupAssociation().selectFromDropdown().getFirstSelectedOption();
										String getCGroupAssociationTxt = optionCA.getText();
										Assert.assertEquals(getCGroupAssociationTxt,expectedGroupAssociation4Text);
																				
										String getCDefaultActionTxt = getCDefaultAction().getText();
										Assert.assertEquals(getCDefaultActionTxt,expectedDefaultActionText);
																				
										String getCFieldLogicTxt = getCFieldLogic().getText();
										Assert.assertEquals(getCFieldLogicTxt,expectedFieldLogicText);
																													
										String getCErrorMessageTxt = getCErrorMessage().getText();
										Assert.assertEquals(getCErrorMessageTxt,expectedErrorMsgText);
										System.out.println("Verified Confidential");
										Thread.sleep(3000);
										
										getupClick19().waitAndClick(10);
										String getHTagLabelTxt = getHTagLabel().GetAttribute("Value");
										Assert.assertEquals(getHTagLabelTxt,expectedHTagLabelText);
																				
										
										WebElement optionHT = getHTagType().selectFromDropdown().getFirstSelectedOption();
										String getHTagTypeTxt = optionHT.getText();
										Assert.assertEquals(getHTagTypeTxt,expectedTagType1Text);
																				
										
										WebElement optionHA = getHGroupAssociation().selectFromDropdown().getFirstSelectedOption();
										String getHGroupAssociationTxt = optionHA.getText();
										Assert.assertEquals(getHGroupAssociationTxt,expectedGroupAssociation4Text);
																				
										String getHDefaultActionTxt = getHDefaultAction().getText();
										Assert.assertEquals(getHDefaultActionTxt,expectedDefaultActionText);
																				
										String getHFieldLogicTxt = getHFieldLogic().getText();
										Assert.assertEquals(getHFieldLogicTxt,expectedFieldLogicText);
																													
										String getHErrorMessageTxt = getHErrorMessage().getText();
										Assert.assertEquals(getHErrorMessageTxt,expectedErrorMsgText);
										System.out.println("Verified Highly Confidential");
										Thread.sleep(3000);
										
										getupClick20().waitAndClick(10);
										String getDCGroupLabelTxt = getDCCOMMENTLabel().GetAttribute("Value");
										Assert.assertEquals(getDCGroupLabelTxt,expectedDCTagLabelText );
																																							
										
										WebElement optionDC = getDCDefaultAction().selectFromDropdown().getFirstSelectedOption();
										String getDCDefaultActionTxt = optionDC.getText();
										Assert.assertEquals(getDCDefaultActionTxt,expectedDefaultAction3Text);
																				
										
										WebElement optionDCO = getDCObject().selectFromDropdown().getFirstSelectedOption();
										String getDCObjectTxt = optionDCO.getText();
										Assert.assertEquals(getDCObjectTxt,expectedFieldLogicObjectText1);
																				
										
										WebElement optionDCC= getDCCondition().selectFromDropdown().getFirstSelectedOption();
										String getDCConditionTxt = optionDCC.getText();
										Assert.assertEquals(getDCConditionTxt,expectedFieldLogicConditionText);
																				
										
										WebElement optionDCA = getDCAction().selectFromDropdown().getFirstSelectedOption();
										String getDCActionTxt = optionDCA.getText();
										Assert.assertEquals(getDCActionTxt,expectedFieldLogicActionText);								
																									
										String getDCErrorMessageTxt = getDCErrorMessage().GetAttribute("Value");
										Assert.assertEquals(getDCErrorMessageTxt,expectedDCErrorMsgText);
										
										String getDCHelpTxt = getDCHelpText().GetAttribute("Value");
										Assert.assertEquals(getDCHelpTxt,expectedDCHelpText);
										System.out.println("Verified Document Comments");
										
										driver.scrollPageToTop();
										driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
												getRootClick().Visible()  ;}}), Input.wait30);
										getRootClick().waitAndClick(10);
										
									    driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
									    		getCF_PreviewButton().Visible()  ;}}), Input.wait30);
										 getCF_PreviewButton().waitAndClick(10);
										 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
												 getResponsiveCheked().Visible()  ;}}), Input.wait30);
										 getResponsiveCheked().waitAndClick(10);
										 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
												 getTestUrCodeClick().Visible()  ;}}), Input.wait30);
										 getTestUrCodeClick().waitAndClick(10);
										 String getResponsiveError = getError1().getText();
										 Assert.assertEquals(getResponsiveError,expectedErrorMessageText);
										 
										 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
												 getNotResponsiveCheked().Visible()  ;}}), Input.wait30);
										 getNotResponsiveCheked().waitAndClick(10);
										 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
												 getTestUrCodeClick().Visible()  ;}}), Input.wait30);
										 getTestUrCodeClick().waitAndClick(10);
										 String getNotResponsiveError = getError1().getText();
										 Assert.assertEquals(getNotResponsiveError,expectedErrorMessageText);
										 
										 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
												 getTechnicalIssueCheked().Visible()  ;}}), Input.wait30);
										 getTechnicalIssueCheked().waitAndClick(10);
										 driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
												 getTestUrCodeClick().Visible()  ;}}), Input.wait30);
										 getTestUrCodeClick().waitAndClick(10);
										 String getTechIssueError1 = getError2().getText();
										 Assert.assertEquals(getTechIssueError1,expectedTIGErrorMsgText);
										 
										 String getTechIssueError2 = getError4().getText();
										 Assert.assertEquals(getTechIssueError2,expectedErrorMessageText);
										 
										 String getTechIssueError3 = getError3().getText();
										 Assert.assertEquals(getTechIssueError3,expectedDCErrorMsgText);
										 System.out.println("Verified Preview");
								}
								
							}
							try{
								   driver.scrollingToBottomofAPage();
								   driver.getWebDriver().findElement(By.xpath("//li[@class='paginate_button next disabled']/a")).isDisplayed();
								   nextPage = false;
							   }
							   catch (Exception e) {
								   driver.getWebDriver().findElement(By.linkText("Next")).click(); 
							 } 
							
			
		  	 	   	  }
		  		}

		public void PreviewCheck() throws InterruptedException {
			
		}
		
}


     
     
  
 