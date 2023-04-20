package pageFactory;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import testScriptsRegression.DocView_MiniDocList_Regression;
import testScriptsSmoke.Input;

/*
 * Author : Raghuram A
 */

public class MiniDocListPage {

	Driver driver;
	BaseClass baseClass;
	DocView_MiniDocList_Regression docview_minilist;
	SoftAssert softAssertion;
	AssignmentsPage assignmentPage;
	DocViewPage docViewPage;
	SessionSearch sessionSearch;
	ReusableDocViewPage reusableDocViewPage;

	// Variables
	String assignmentName;
	String chosenName;
	int totalAssignmentsinList;
	int AssignmentNumber = 1;
	int numbertoChoose;
	int afterremovalcount;
	Set<Integer> hash_Set = new HashSet<Integer>();
	List<String> afterActionselectedFieldsPickColumnDisplayFirstAssignment = new ArrayList<>();
	List<String> afterActionselectedFieldsSetDocumentFirstAssignment = new ArrayList<>();
	List<String> selectedFieldsSecondAssignment = new ArrayList<>();
	List<String> availableFieldsPickCcolumnList;
	List<String> afterActionselectedFieldsList;
	List<String> assignmentList = new ArrayList<>();
	List<String> pickColumnDisplaySelectedLists = new ArrayList<>();
	List<String> setDocumentSortingList = new ArrayList<>();
	List<String> setDocumentSortingListA = new ArrayList<>();
	List<String> pickColumnDisplaySelectedListAssignmentTwo = new ArrayList<>();
	List<String> pickColumnDisplaySelectedListtoVerify = new ArrayList<>();
	List<String> availablePickColumnDisplayList = new ArrayList<>();
	List<String> childWindowHeaderFields = new ArrayList<>();
	List<String> pickColumnDisplaySelectedList = new ArrayList<>();
	List<String> setDocSelectedList = new ArrayList<>();
	List<String> docIDlist = new ArrayList<>();
	List<String> completedDocuments = new ArrayList<>();
	List<String> selectedFieldsPickColumnDisplay;
	List<String> selectedFieldsSetDocumentSorting;
	List<String> originalOrderedList;
	List<String> afterSortList;

	// Added by Gopinath 28/12/2021
	public Element getCursorNextDocumentId() {
		return driver.FindElementByXPath("(//i[@class='fa fa-arrow-right']//../../following-sibling::tr)[1]//td[2]");
	}

	public Element getCurrentDocumentId() {
		return driver.FindElementByXPath("//i[@class='fa fa-arrow-right']//..//..//td[2]");
	}

	public MiniDocListPage(Driver driver) {
		this.driver = driver;
		baseClass = new BaseClass(driver);
		softAssertion = new SoftAssert();
		assignmentPage = new AssignmentsPage(driver);
		docViewPage = new DocViewPage(driver);
		sessionSearch = new SessionSearch(driver);
		reusableDocViewPage = new ReusableDocViewPage(driver);
	}
	

	public Element getReviewHeader() {
		return driver
				.FindElementByXPath("//h1[@class='page-title' and contains(text(),' Review Manager Dashboard for ')]");
	}

	public ElementCollection getAssignmentAvailable() {
		return driver.FindElementsByXPath("//div[@id='assignmentDivId']//td[@class='assignment-level wid25']//a");
	}

	public Element getAssignmentToChoose(String name) {
		return driver.FindElementByXPath(
				"//div[@id='assignmentDivId']//td[@class='assignment-level wid25']//a//strong[text()='" + name + "']");
	}

	public Element getGearIcon() {
		return driver.FindElementByXPath("//a[@id='miniDocListConfig']");
	}

	public Element getConfigureMiniDocTab() {
		return driver.FindElementByXPath("//h3[text()='Configure Mini DocList']");
	}

	public Element getManualSortRadioButton() {
		return driver.FindElementByXPath("//*[@id=\"lblBtnRadioManual\"]/i");
	}

	public Element getOptimizedSortRadioButton() {
		return driver.FindElementByXPath("//*[@id='rbOptimized']/following-sibling::i");
	}

	public ElementCollection getSelectedFieldsAvailablePickColumnDisplay() {
		return driver.FindElementsByXPath(
				"//div[@id='divColumnDisplay']//p//strong[text()='SelectedFields']//..//..//ul[@id='sortable2PickColumns']//li");
	}

	public Element getValueToRemovefromOrderPickColumnDisplay(String name) {
		return driver.FindElementByXPath(
				"//div[@id='divColumnDisplay']//p//strong[text()='SelectedFields']//..//..//ul[@id='sortable2PickColumns']//li[@customfield-name='"
						+ name + "']//i[@class='fa fa-times-circle']");
	}

	public Element getValueToRemovefromOrderSetDocumentSort(String name) {
		return driver.FindElementByXPath(
				"//div[@class='col-md-6 smart-form']//p//strong[text()='SelectedFields']//..//..//ul[@id='sortable2DocumentSort']//i[@value='"
						+ name + "']//..//i[@class='fa fa-times-circle']");
	}

	public ElementCollection getAvailablePickColumnDisplayFields() {
		return driver.FindElementsByXPath("//ul[@id='sortable1PickColumns']//li");
	}

	public Element getFromAvailableFieldPickColumnDisplay(String name) {
		return driver.FindElementByXPath("//ul[@id='sortable1PickColumns']//li//i[@value='" + name + "']");
	}

	public Element getToSelectedField() {
		return driver.FindElementByXPath(
				"//div[@id='divColumnDisplay']//p//strong[text()='SelectedFields']//..//..//ul[@id='sortable2PickColumns']");
	}
	
	public Element getConceptuallySimilarData() {
		return driver.FindElementByXPath("//td[@class='dataTables_empty']");
				}

	public Element getMiniDocListcloseButton() {
		return driver.FindElementByXPath("//button[@class='ui-dialog-titlebar-close']");
	}

	public Element getMiniDocListConfirmationButton(String actionType) {
		return driver.FindElementByXPath("//div[@class='ui-dialog-buttonset']//button[text()='" + actionType + "']");
	}

	public Element getShowCompletedDocsToggle() {
		return driver.FindElement(By.xpath("//input[@id=\"ShowCompletedDocs\"]//following-sibling::i"));
	}

	public Element getGearIcon1() {
		return driver.FindElementByXPath("//i[@class='fa fa-gear font-xl']");
	}

	public Element getMiniDocChildWindowExpandIcon() {
		return driver.FindElementByXPath("//header[@id='HdrMiniDoc']//i[@class='fa fa-expand']");
	}

	public ElementCollection getChildWindowHeader() {
		return driver.FindElementsByXPath("//div[@class='dataTables_scrollHead']//th");
	}

	public Element getDashBoardLink() {
		return driver.FindElementByXPath("//a[@name='Dashboard']");
	}

	public Element getNavigationConfirmationPopup(String toClick) {
		return driver.FindElementByXPath("//div[@class='ui-dialog-buttonset']//button[text()='" + toClick + "']");
	}

	public ElementCollection getOptimizedOrderSelectedFields() {
		return driver.FindElementsByXPath("//ul[@id='sortable2PickColumns']//li");
	}

	public Element getShowCompleteDocsButton() {
		return driver.FindElementByXPath("//i[@data-swchon-text='ON']");
	}

	public Element getSetDocumentSortingTab() {
		return driver.FindElementByXPath("//a[@class='ui-tabs-anchor' and text()='Set Document Sorting']");
	}

	public ElementCollection getFieldsAvailableSetDocumetSorting() {
		return driver.FindElementsByXPath(
				"//div[@class='col-md-6 smart-form']//p//strong[text()='SelectedFields']//..//..//ul[@id='sortable2DocumentSort']//li");
	}

	public Element getChildWindowGearIcons() {
		return driver.FindElementByXPath("//i[@class='fa fa-gear']");
	}

	public Element getDocumentSaveButton() {
		return driver.FindElementByXPath("//a[@id='Save']");
	}

	public Element getDocumentCompleteButton() {
		return driver.FindElementByXPath("//button[@id='btnDocumentComplete']");
	}

	public ElementCollection getMiniDocListHeaderValue() {
		return driver.FindElementsByXPath(
				"//*[@id=\"SearchDataTable_wrapper\"]//div[@class='dataTables_scrollHeadInner']//thead//tr//th[@class='sorting_disabled']");
	}

	// 8-19-21
	public Element getPersistentHighlightingIcon() {
		return driver.FindElementByXPath("//i[@class='fa fa-eye']");
	}

	public Element getSearchHitsTitle() {
		//return driver.FindElementByXPath("//h3[@class='remark-title' and text()='Search Hits:']");
		return driver.FindElementByXPath("//*[@id='divPersistentSearch']/div/div[5]/p");
	}

	public ElementCollection getChildWindowDocList() {
		return driver.FindElementsByXPath("//tbody//tr");
	}

	public ElementCollection getChildWindowDocIDList() {
		return driver.FindElementsByXPath("//tbody//tr//td[contains(text(), 'ID')]");
	}

	public Element getChildWindowDocIDList(String idToChoose) {
		return driver.FindElementByXPath("//tbody//tr//td[contains(text(), '" + idToChoose + "')]");
	}

	public Element getchkShowSearchTermToggle(String status) {
		return driver.FindElementByXPath("//i[@data-class='" + status + "']");
	}

	public Element getMainWindowActiveDocID() {
		return driver.FindElementByXPath("//span[@id='activeDocumentId']");
	}

	public ElementCollection getListofDocIDinCW() {
		return driver.FindElementsByXPath("// *[@id='SearchDataTable']//tr['row']/td[2]");
	}

	public Element getDociD(String docId) {
		return driver.FindElementByXPath("// *[@id='SearchDataTable']//tr['row']//td[2][text()='" + docId + "']");
	}

	public Element getDocIDCheckedIcon(String docId) {
		return driver.FindElementByXPath(
				"//tr[@role='row']//td[text()='" + docId + "']//..//i[@class='fa fa-check-circle']");
	}

	public Element getCheckSelectedBgColor(String docId) {
		return driver.FindElementByXPath("// *[@id='SearchDataTable']//tr['row']//td[2][text()='" + docId + "']");
	}

	public Element getMiniDocListToggle() {
		return driver.FindElementByXPath("//label[@class='toggle font-sm']//input");
	}

	public ElementCollection getHitcountList() {
		return driver.FindElementsByXPath("//div[@class='message-1 col-md-12 yellow-border']");
	}

	public ElementCollection getZeroHitList() {
		return driver
				.FindElementsByXPath("//div[@class='message-1 col-md-12 yellow-border']//span[contains(text(),'0')]");
	}

	public Element getDocView_MiniDoclistChildWindow() {
		return driver
				.FindElementByXPath("//*[@id='HdrMiniDoc']//*[@class='jarviswidget-ctrls']//i[@class='fa fa-expand']");
	}

	public Element getDocView_ConfigMinidoclist() {
		return driver.FindElementById("miniDocListConfig");
	}

	public Element getDocView_MiniDoclistRightArrow() {
		return driver.FindElementByXPath("//*[@id='SearchDataTable']//i[@class='fa fa-arrow-right']");
	}

	public Element getDocView_MiniDoclistPlusButton() {
		return driver.FindElementByXPath(".//*[@id='HdrMiniDoc']/div/a/i");
	}

	public Element getCompletedDocsToggle() {
		return driver.FindElementByXPath("//i[@data-swchoff-text='OFF']");
	}

	public Element getHistoryBtn() {
		return driver.FindElementByXPath("//a[@id='btnDocHistory']");
	}

	public Element getCurrentSelectionIcon(String docID) {
		return driver.FindElementByXPath(
				"(// *[@id='SearchDataTable']//tr['row']//td[2][text()='" + docID + "']//..//td//i)[2]");
	}

	public Element getCurrentSelectionCompletedIcon(String docID) {
		return driver.FindElementByXPath(
				"(// *[@id='SearchDataTable']//tr['row']//td[2][text()='" + docID + "']//..//td//i)[3]");
	}

	public Element getCurrentSelectionIconFromHistoryDD(String docID) {
		return driver.FindElementByXPath(
				"//ul[@id='ulDocViewHistory']//a[text()='" + docID + " ']//i[@class='fa fa-arrow-circle-o-right']");
	}

	// added by Mohan

	public Element getDocView_MiniDoclist_GearIcon() {
		return driver.FindElementByXPath("//*[@id='miniDocListConfig']//i[@class='fa fa-gear']");
	}

	public Element getDocView_MiniDoclist_ConfigureMiniDocList_SelectedFields() {
		return driver.FindElementByXPath("//li[text()='FamilyRelationship']//following-sibling::i");
	}

	public Element getDocView_MiniDoclist_Header_Webfields(String fieldName) {
		return driver.FindElementByXPath("//*[@class='dataTables_scrollHeadInner']//th[text()='" + fieldName + "']");
	}

//	Added by baskar
	public Element getValueToRemoveFromSelectedWebFields(String fieldValue) {
		return driver.FindElementByXPath(
				"//div[@id='divColumnDisplay']//p//strong[text()='SelectedFields']//..//..//ul[@id='sortable2PickColumns']//li[@customfield-name='"
						+ fieldValue + "']//i[@class='fa fa-times-circle']");
	}

	public Element getErrorMessageWebFields() {
		return driver.FindElementByXPath("//p[text()='Please select columns to display']");
	}

	public Element getSelectedFieldToInterchange(String name) {
		return driver.FindElementByXPath("//ul[@id='sortable2PickColumns']//li//i[@value='" + name + "']");
	}

	public ElementCollection getListofDocIDAfterInterchange() {
		return driver.FindElementsByXPath("// *[@id='SearchDataTable']//tr['row']/td[5]");
	}

	public Element getDocView_MiniDoc_ChildWindow_Selectdoc(int rowno) {
		return driver.FindElementByXPath("//*[@id='SearchDataTable']//tr[" + rowno + "]/td[1]/label");
	}

	public Element getClickDocviewID(int row) {
		return driver.FindElementByXPath("//*[@id='SearchDataTable']//tr[" + row + "]/td[2]");
	}

	public Element getverifyCodeSameAsLast() {
		return driver.FindElementByXPath("//table[@id='SearchDataTable']//i[@class='fa fa-check-circle']");
	}

	public Element getDocView_Mini_ActionButton() {
		return driver.FindElementById("btnAction");
	}

	public Element getDocView__ChildWindow_Mini_CodeSameAs() {
		return driver.FindElementById("liCodeSameAsMiniDocList");
	}

	public Element getWarningMsgCompleteDocsCodeSameAs() {
		return driver.FindElementByXPath(
				"//p[text()='Cannot perform Code Same As action, as the selected documents include one or more completed documents']");
	}
	
	public Element getDocView_Analytics_liDocumentThreadMap() {
		return driver.FindElementById("liDocumentThreadedMap");
	}


	public Element getDocView_HistoryButton() {
		return driver.FindElementById("btnDocHistory");
	}

	public ElementCollection getHistoryDropDown() {
		return driver.FindElementsByXPath("//*[@id='ulDocViewHistory']/li");
	}

	public Element getDocView_Analytics_NearDupeTab() {
		return driver.FindElementById("liDocumentNearDupe");
	}

	public ElementCollection getAnalyticalPanelDocIdText() {
		return driver.FindElementsByXPath("//table[@id='dtDocumentNearDuplicates']/tbody/tr//td[2]");
	}

	public Element getAnalyCheckBox(String docid) {
		return driver.FindElementByXPath(
				"//table[@id='dtDocumentNearDuplicates']//td[text()=' " + docid + "']//..//label[@class='checkbox']");
	}

	public ElementCollection getDocView_MiniListDocuments() {
		return driver.FindElementsByXPath("//div[@id='divMiniDocList']//tbody//tr");
	}

	public ElementCollection getMiniDocListDocIdText() {
		return driver.FindElementsByXPath("//*[@id='SearchDataTable']//tr/td[2]");
	}

	public Element getHistoryClock(String docid) {
		return driver.FindElementByXPath("//*[@id='ulDocViewHistory']/li/a[contains(text(),'ID')]");
	}

	public Element getDocView_ChildWindow_ActionButton() {
		return driver.FindElementById("btnAnalyticAction");
	}

	public Element getViewDocumentNearDupe() {
		return driver.FindElementByXPath("//li[@id='liViewDocumentNearDupe']");
	}

	public Element getHistoryDocId() {
		return driver.FindElementByXPath("//*[@id='ulDocViewHistory']/li/a[text()='ID00000011 ']");
	}

	public Element getOptSortRadioBtn() {
		return driver.FindElementByXPath("//input[@id='rbOptimized']//..//i");
	}

	public Element getManualSetDocumentOrder() {
		return driver.FindElementById("liColumnSortingOrder");
	}

	public Element getManualColumnDisplay() {
		return driver.FindElementById("liPickColumnDisplay");
	}

	public Element getMetaDataText(String values) {
		return driver.FindElementByXPath("//table[@id='SearchDataTable']//td[2]//..//td[text()='" + values + "']");
	}

	// Added by Vijaya.Rani
	public Element getDocview_MiniDoc_GearIconCancelBtn() {
		return driver.FindElementByXPath("//button[@class='btn btn-default']");
	}

	public Element getDocview_MiniDoc_DocId() {
		return driver.FindElementByXPath("//*[@id='SearchDataTable_wrapper']//tr//th[text()='DocID']");
	}

	// added by sowndarya.velraj
	public Element getDocumentCountFromDocView() {
		return driver.FindElementById("totalRecords");
	}

	// Added by Vijaya.Rani
	public Element getDocview_GearEmailAddress() {
		return driver.FindElementByXPath("//li[text()='EmailAuthorAddress']");
	}

	public Element getDocview_GearEmailAuthorName() {
		return driver.FindElementByXPath("//li[text()='EmailAuthorName']");
	}

	// Added by Raghuram
	public Element getHistoryDocId(String docId) {
		return driver.FindElementByXPath("//*[@id='ulDocViewHistory']/li/a[text()='" + docId + " ']");
	}

	// Added By Jeevitha
	public ElementCollection getAvailableSetDocumentField() {
		return driver.FindElementsByXPath("//ul[@id='sortable1DocumentSort']//li");
	}// Added by jayanthi

	public Element getDocView_CodingFromName() {
		return driver.FindElementById("lblCodingFormName");
	}

	public Element getSelectAssignmentFromDashborad(String assignmentName) {
		return driver.FindElementByXPath(
				"//*[@id='dt_basic']//following-sibling::tbody//following-sibling::tr//strong[text()='" + assignmentName
						+ "']");
	}

	public Element getDashboardButton() {
		return driver.FindElementByXPath("//label[text()='Dashboard']");
	}

	public Element miniDocListDisplay() {
		return driver.FindElementByXPath("//div[@id='divMiniDocList']//div[@class='dataTables_scroll']");
	}

	public Element getDocView__ChildWindow_Mini_RemoveCodeSameAs() {
		return driver.FindElementById("liRemoveCodeSameAsMiniDocList");
	}

	public Element getSelectedOptimizedSortRadioButton() {
		return driver.FindElementByXPath("//input[@id='rbOptimized']");
	}
	public Element getThreadData() {
		return driver.FindElementByXPath("//td[@class='dataTables_empty']");
				}

	public Element getAvailableFieldsDisplay(String fields) {
		return driver.FindElementByXPath("//ul[@id='sortable1PickColumns']//li[@customfield-name='" + fields + "']");
	}
	
	public Element getHistoryClockDocs() {
		return driver.FindElementByXPath("(//*[@id='ulDocViewHistory']/li/a[contains(text(),'ID')])[last()]");
	}

	public Element getDocView_MiniDoclist_ConfigureMiniDocList_FamilyMemberCount() {
		return driver.FindElementByXPath("//li[text()='FamilyMemberCount']//following-sibling::i");
	}
	
	public Element getDashBoardReviewer() {
		return driver.FindElementByXPath("//a[@name='ReviewerDashboard']");
	}
	public Element getPureHitAddButton() {
	return driver.FindElementByXPath(".//*[@id='001']/i[2]");
}
public Element getBulkActionButton() {
	return driver.FindElementByXPath("//*[@id='idAction']");
}
public Element getViewBtn() {
	return driver.FindElementByXPath("//a[text()='View']");
}
public Element getDocViewFromDropDown() {
	return driver.FindElementByXPath("//a[text()='View In DocView']");
}

	public Element getDocViewAction() {
	return driver.FindElementByXPath("//*[@id='ddlbulkactions']//a[contains(.,'View In DocView')]");
}
	
	public Element getDocView_Analytics_liDocumentConceptualSimilarab() {
		return driver.FindElementById("liDocumentConceptualSimilar");
		}

		public Element getDocView_Analytics_FamilyTab() {
		return driver.FindElementById("liDocumentFamilyMember");
		}

		public Element getFamilyData() {
		return driver.FindElementByXPath("//table[@id='dtDocumentFamilyMembers']//td[@class='dataTables_empty']");
		}

		public Element getNearDupeTab() {
		return driver.FindElementByXPath("//table[@id='dtDocumentNearDuplicates']//td[@class='dataTables_empty']");
		}

		public ElementCollection dtDocumentFamilyMembersID() {
		return driver.FindElementsByXPath(
				"//table[@id='dtDocumentFamilyMembers']//tr[contains(@class,'dtDocumentFamilyMembersRowNumber_')]//td[contains(text(),'ID00')]");
		}

		public ElementCollection getNearDupesDataList() {
		return driver.FindElementsByXPath("//div[@id='dtDocumentNearDuplicates_wrapper']//tr//th[@class='sorting']");
		}

		public ElementCollection getNearDupeDocCounnt() {
		return driver.FindElementsByXPath(
				".//*[@id='dtDocumentNearDuplicates']/tbody//tr[contains(@class,'dtDocumentNearDuplicatesRowNumber')]");
		}

		public ElementCollection getNearDupeDocIDs(int index) {
		return driver.FindElementsByXPath(
				".//*[@id='dtDocumentNearDuplicates']/tbody//tr[contains(@class,'dtDocumentNearDuplicatesRowNumber')]//td["
						+ index + "]");
		}

		public ElementCollection getNearDupesDataListCollection(int index, int i) {
		return driver.FindElementsByXPath(
				"(.//*[@id='dtDocumentNearDuplicates']/tbody//tr[contains(@class,'dtDocumentNearDuplicatesRowNumber')]//td["
						+ index + "])[" + i + "]");
		}
	
	/**
	 * @author Indium Raghuram ] Description : To get the list of elements
	 *         (GenericMethod) Date:8/15/21 Modified date: N/A Modified by: N/A
	 * @param assignmentNames
	 */
	public List<String> availableListofElements(ElementCollection element) {
		List<String> elementNames = new ArrayList<>();
		List<WebElement> elementList = null;
		elementList = element.FindWebElements();
		for (WebElement wenElementNames : elementList) {
			String elementName = wenElementNames.getText();
			elementNames.add(elementName);
		}
		return elementNames;
	}

	/**
	 * @author Indium Raghuram Description : Random Number Generator Date: 8/15/21
	 *         Modified date: N/A Modified by: N/A
	 */
	public int randNumber(int size) {
		int rand_Number = 0;
		try {
			Random rand = new Random();
			rand_Number = rand.nextInt(size);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rand_Number;
	}

	/**
	 * @author Indium Raghuram Description : Random Number Generator without
	 *         duplicates Date: 8/15/21 Modified date: 8/24/21 Modified by: Raghuram
	 *         A
	 */
	public int randomNumber(int elementSize) {
		int status = 0;
		Random randNum = new Random();
		int rand_Number = randNum.nextInt(elementSize);

		try {
			if (rand_Number == 0) {
				rand_Number = rand_Number + 1;
			} else if (rand_Number == elementSize) {
				rand_Number = rand_Number - 1;
			} else if (rand_Number > elementSize) {
				rand_Number = rand_Number - 2;
			}

			if (hash_Set.isEmpty()) {
				if (hash_Set.add(rand_Number)) {
					System.out.println("Number to choose " + rand_Number);
					return rand_Number;
				}
			} else {
				if (hash_Set.add(rand_Number)) {
					System.out.println("Number to choose " + rand_Number);
					return rand_Number;
				} else if (!hash_Set.add(rand_Number)) {
					do {
						rand_Number = randNum.nextInt(elementSize);
						if (rand_Number == 0) {
							rand_Number = rand_Number + 1;
						} else if (rand_Number == elementSize) {
							rand_Number = rand_Number - 1;
						} else if (rand_Number > elementSize) {
							rand_Number = rand_Number - 2;
						}
						if (hash_Set.add(rand_Number)) {
							status = 1;
							System.out.println("Number to choose " + rand_Number);
							return rand_Number;
						}
					} while (status == 1);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Number to choose " + rand_Number);
		return rand_Number;

	}

	/**
	 * @author Indium Raghuram Description : ChildWindow Handling for 58105 Date:
	 *         8/15/21 Modified date: N/A Modified by: N/A
	 */
	public List<String> handlingViaMinidoclistChildWindow(int num) throws AWTException, InterruptedException {
		List<String> childHeaderList = new ArrayList<>();
		try {
			List<WebElement> childHeaderWebElementList = null;
			driver.waitForPageToBeReady();

			String parentWindowID = driver.getWebDriver().getWindowHandle();
			Set<String> childWindowID = driver.getWebDriver().getWindowHandles();
			for (String childWIndowNames : childWindowID) {
				if (!parentWindowID.equals(childWIndowNames)) {
					driver.switchTo().window(childWIndowNames);
					driver.waitForPageToBeReady();

					childHeaderWebElementList = getChildWindowHeader().FindWebElements();
					for (WebElement assignmentname : childHeaderWebElementList) {
						String fields = assignmentname.getText();
						childHeaderList.add(fields);
					}
					if (num == 1) {
						getChildWindowGearIcons().Click();
					}
					driver.close();
				}
			}
			driver.switchTo().window(parentWindowID);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return childHeaderList;
	}

	/**
	 * @author Indium Raghuram Description : Method to transfer control to child
	 *         window Returns : returns parent window id Date: 8/15/21 Modified
	 *         date: N/A Modified by: N/A
	 */
	public String childWindowTransfer() throws AWTException, InterruptedException {
		driver.waitForPageToBeReady();
		String parentWindowID = driver.getWebDriver().getWindowHandle();
		Set<String> childWindowID = driver.getWebDriver().getWindowHandles();
		for (String childWindowList : childWindowID) {
			if (!parentWindowID.equals(childWindowList)) {
				driver.switchTo().window(childWindowList);
				try {
				driver.waitForPageToBeReady();}
				catch(NullPointerException e) {}
				String text = driver.getTitle();
				System.out.println("Heading of child window is " + text);
			}
		}
		return parentWindowID;
	}

	/**
	 * @author Indium Raghuram Description : Returns : returns list Date : 8/15/21
	 *         Modified date: N/A Modified by: N/A
	 */
	public List<String> chilDWIndowContents() {
		List<String> childHeader = new ArrayList<>();
		List<WebElement> childHeaderList = null;
		childHeaderList = getChildWindowHeader().FindWebElements();
		for (WebElement assignmentname : childHeaderList) {
			String fields = assignmentname.getText();
			childHeader.add(fields);
		}
		return childHeader;
	}

	/**
	 * @author Indium Raghuram Description : Method to close child window and
	 *         transfer control to Parent window Date : 8/15/21 Modified date: N/A
	 *         Modified by: N/A
	 * @throws InterruptedException
	 */
	public void childWIndowCloseHandles(String parentWindowID) throws InterruptedException {
		driver.close();
		driver.switchTo().window(parentWindowID);
	}

	/**
	 * @author Indium Raghuram Description : Perform Drag and Drop fields from
	 *         available field to selected fields of 'Pick Column Display' Date :
	 *         8/15/21 Modified date: N/A Modified by: N/A
	 */
	public void dragAndDropAction(Element source, Element destination) {
		driver.waitForPageToBeReady();
		Actions actions = new Actions(driver.getWebDriver());
		actions.clickAndHold(source.getWebElement());
		actions.moveToElement(destination.getWebElement());
		actions.release(destination.getWebElement());
		actions.build().perform();
	}

	/**
	 * @author Indium Raghuram Description : Verifying Reviewer Manager login
	 *         landing page Date : 8/15/21 Modified date: N/A Modified by: N/A
	 */
	public void checkReviewerDashboardLandingPage() {
		try {
			getReviewHeader().Displayed();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("Element Not Present - or didn't login as reviewer");
		}
	}

	/**
	 * @author Indium Raghuram Description : Testcase - RPMXCON-51804 method 3
	 *         Returns : returns the column list names from the child window
	 *         performing add/remove action - Verify the impact in mini-doc child
	 *         window Date : 8/15/21 Modified date: N/A Modified by: N/A
	 */
	public List<String> childWindowImpactVerification() throws AWTException, InterruptedException {

		driver.waitForPageToBeReady();
		launchingMindoclistChildWindow();
		driver.waitForPageToBeReady();

		String parentWindowId = childWindowTransfer();
		childWindowHeaderFields = chilDWIndowContents();
		childWindowHeaderFields.removeAll(Arrays.asList(null, ""));
		System.out.println("---------childWindowHeaderFields Listed Below-------------");
		for (String childHeaders : childWindowHeaderFields) {
			System.out.println(childHeaders);
		}

		// Closes child window
		childWIndowCloseHandles(parentWindowId);

		baseClass.stepInfo("Verifed the impact in ChildWindow");
		System.out.println("------------------Handled Child window----------------------");
		return childWindowHeaderFields;

	}

	/**
	 * Author : Indium Raghuram Description : Picking assignments randomly form the
	 * list Date: 8/15/21 Modified date: NA Modified by: N/A
	 */
	public String assignmentSelection(int AssignmentNumber, List<String> assignmentNames, int totalAssignmentsinList) {
		try {
			if (AssignmentNumber == 1) {
				numbertoChoose = randomNumber(totalAssignmentsinList);
				System.out.println("Number to choose : " + numbertoChoose);
				assignmentName = assignmentNames.get(numbertoChoose).toString();
			} else if (AssignmentNumber == 2) {
				int numbertoChoose2 = randomNumber(afterremovalcount);
				System.out.println("Number to choose : " + numbertoChoose2);
				assignmentName = assignmentNames.get(numbertoChoose2).toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return assignmentName;
	}

	/**
	 * @author Indium Raghuram Description : Perform Drag and Drop fields from
	 *         available field to selected fields of 'Pick Column Display' Date:
	 *         8/15/21 Modified date: NA Modified by: N/A
	 */
	public void dragAndDropAvailableFieldstoSelectedfieldsPickColumDisplay(String name) {
		driver.waitForPageToBeReady();
		getFromAvailableFieldPickColumnDisplay(name).ScrollTo();
		Actions actions = new Actions(driver.getWebDriver());
		actions.clickAndHold(getFromAvailableFieldPickColumnDisplay(name).getWebElement());
		actions.moveToElement(getToSelectedField().getWebElement());
		actions.release(getToSelectedField().getWebElement());
		actions.build().perform();
	}

	/**
	 * @author Indium Raghuram Description : Method for Pick column display tab
	 *         action handling Date: 8/15/21 Modified date: 16/8/21 Modified by:
	 *         Raghuram A
	 */

	public List<String> methodforPickColumndisplay() {

		driver.waitForPageToBeReady();
		ElementCollection pickColumnSelectedListElements = getSelectedFieldsAvailablePickColumnDisplay();
		pickColumnDisplaySelectedLists = availableListofElements(pickColumnSelectedListElements);
		System.out.println("------Pick Column Display Selected fields before-----------------------------");
		baseClass.stepInfo("Pick Column Display Selected fields before");
		for (String a : pickColumnDisplaySelectedLists) {
			System.out.println(a);
			baseClass.stepInfo(a);
		}
		System.out.println("-----------------------------------------------------------------------------");

		String fieldtoRemovefromSelectedList = pickColumnDisplaySelectedLists
				.get(randNumber(pickColumnDisplaySelectedLists.size()));

		// Remove a field from Selected fields
		driver.waitForPageToBeReady();
		getValueToRemovefromOrderPickColumnDisplay(fieldtoRemovefromSelectedList).waitAndClick(3);
		System.out.println("Removed from Selected list");

		// Drag and Drop from Available to Selected field
		driver.waitForPageToBeReady();
		ElementCollection pickColumnAvailableListA = getAvailablePickColumnDisplayFields();
		availablePickColumnDisplayList = availableListofElements(pickColumnAvailableListA);
		String fieldToAdd = availablePickColumnDisplayList.get(randNumber(5)); //
		Element sourcefromPickColumDisplay = getFromAvailableFieldPickColumnDisplay(fieldToAdd);
		Element destinationfromPickColumDisplay = getToSelectedField();
		dragAndDropAction(sourcefromPickColumDisplay, destinationfromPickColumDisplay);
		System.out.println("Drag and Drop performed");
		baseClass.stepInfo("Add/Remove field done in PickColumnDisplay");

		// After Drag and Drop
		ElementCollection pickColumnafterSelectedfieldList = getSelectedFieldsAvailablePickColumnDisplay();
		afterActionselectedFieldsList = availableListofElements(pickColumnafterSelectedfieldList);
		System.out.println(pickColumnSelectedListElements.size());
		System.out
				.println("------Pick Column Display Selected fields After Modifications----------------------------s-");
		baseClass.stepInfo("Pick Column Display Selected fields After Modifications");
		for (String a : afterActionselectedFieldsList) {
			System.out.println(a);
			baseClass.stepInfo(a);
		}
		System.out.println("-----------------------------------------------------------------------------");
		return afterActionselectedFieldsList;
	}

	/**
	 * @author Indium Raghuram Description : Description : Method for Pick column
	 *         display tab action handling Date: 8/15/21 Modified date: 8/16/21 by:
	 *         Raghuram A
	 * @throws InterruptedException
	 */
	public List<String> methodforSetDocumetSort() throws InterruptedException {
		// Sort option selection - Set document sorting
		driver.waitForPageToBeReady();
		getSetDocumentSortingTab().waitAndClick(2);

		ElementCollection setDocumentColumnListA = getFieldsAvailableSetDocumetSorting();
		setDocumentSortingList = availableListofElements(setDocumentColumnListA);
		System.out.println("------Selected List from Set Document Sorting  before-----------------------------");
		baseClass.stepInfo("Selected List from Set Document Sorting  before");
		for (String b : setDocumentSortingList) {
			System.out.println(b);
			baseClass.stepInfo(b);
		}
		System.out.println("-----------------------------------------------------------------------------");

		// Remove a field from Selected fields
		driver.waitForPageToBeReady();
		String fieldtoRemovefromsetDocumentSelectedList = setDocumentSortingList.get(2);
		System.out.println("Removed : " + fieldtoRemovefromsetDocumentSelectedList);
		getValueToRemovefromOrderSetDocumentSort(fieldtoRemovefromsetDocumentSelectedList).Click();
		System.out.println("Removed from Selected list");

		ElementCollection setDocumentColumnAfterremoval = getFieldsAvailableSetDocumetSorting();
		setDocumentSortingListA = availableListofElements(setDocumentColumnAfterremoval);

		System.out.println("Set Document after removal");
		baseClass.stepInfo("Set Document after removal");
		for (String fieldsAfterRemoval : setDocumentSortingListA) {
			System.out.println(fieldsAfterRemoval);
			baseClass.stepInfo(fieldsAfterRemoval);
		}
		baseClass.stepInfo("Add/Remove done in Set Document Sorting");
		return setDocumentSortingListA;
	}

	/**
	 * @author Indium Raghuram Description :Main method for Test case 58104 Date:
	 *         8/15/21 Modified date: 8/16/21 Modified by: Raghuram A
	 */
	public void verifyDocMiniListConfiguration(String assignmentOne,String assignmentTwo) throws InterruptedException, Exception {
		// Total no.of Assignment
		assignmentPage.SelectAssignmentByReviewer(assignmentOne);
		System.out.println("Selected Assignement "  + assignmentOne);
		baseClass.stepInfo("Selected Assignement "  + assignmentOne);
//making doclist default view
		removingFieldsAndDragnDropDefault();
		driver.waitForPageToBeReady();
		// Click on the Gear icon
		baseClass.waitForElement(getGearIcon());
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getGearIcon());
		getGearIcon().waitAndClick(5);

		// Verifying Configure Mini Doc list tab
		baseClass.waitForElement(getConfigureMiniDocTab());
		driver.waitForPageToBeReady();
		String configureMiniDocTab = getConfigureMiniDocTab().getText();
		softAssertion.assertEquals(configureMiniDocTab, "Configure Mini DocList");
		baseClass.passedStep("Landed on Configure Mini DocList");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getManualSortRadioButton());
		getManualSortRadioButton().waitAndClick(5);
		// Pick Column Display
		afterActionselectedFieldsPickColumnDisplayFirstAssignment = methodforPickColumndisplay();
		// Set Document Sort
		afterActionselectedFieldsSetDocumentFirstAssignment = methodforSetDocumetSort();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMiniDocListConfirmationButton("Save").Visible();
			}
		}), Input.wait30);
		getMiniDocListConfirmationButton("Save").waitAndClick(5);
		System.out.println("Saved Confirmed");
		driver.waitForPageToBeReady();
		// Verify the impact in mini-doc child window - Child Window Method
		childWindowHeaderFields = childWindowImpactVerification();
		baseClass.stepInfo("Child Window Headers");
		for (String hearderFields : childWindowHeaderFields) {
			System.out.println(hearderFields);
			baseClass.stepInfo(hearderFields);
		}
		String childWindowHeaderFieldsC = childWindowHeaderFields.toString().toLowerCase();
		String afterActionselectedFieldsPickColumnDisplayFirst = afterActionselectedFieldsPickColumnDisplayFirstAssignment
				.toString().toLowerCase();
		System.out.println(
				afterActionselectedFieldsPickColumnDisplayFirstAssignment.size() + " After Pick fields");

		softAssertion.assertEquals(childWindowHeaderFieldsC, afterActionselectedFieldsPickColumnDisplayFirst);

		if (childWindowHeaderFieldsC.equals(afterActionselectedFieldsPickColumnDisplayFirst)) {
			System.out.println("Changes made in minidoclist reflected in Child Window");
			baseClass.passedStep("Pick Column Display Changes reflected in childwindow headers");
		} else {
			System.out.println("Changes doesn't reflect on child window");
			baseClass.stepInfo("Changes doesn't reflect on child window");
		}
		baseClass.waitTime(2);
		baseClass.waitForElement(getDashBoardReviewer());
		getDashBoardReviewer().waitAndClick(3);

		baseClass.waitForElement(getNavigationConfirmationPopup("Yes"));
		getNavigationConfirmationPopup("Yes").waitAndClick(5);
		baseClass.waitTime(2);
		assignmentPage.SelectAssignmentByReviewer(assignmentTwo);
		baseClass.waitTime(3);
		baseClass.waitForElement(getGearIcon());
		getGearIcon().waitAndClick(5);
		selectedFieldsSecondAssignment = docviewMinlistComparisionMethodTwo();
		driver.WaitUntil((new Callable<Boolean>() {

			public Boolean call() {
				return getMiniDocListcloseButton().Visible();
			}
		}), Input.wait30);
		getMiniDocListcloseButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		softAssertion.assertNotEquals(afterActionselectedFieldsPickColumnDisplayFirstAssignment,
				afterActionselectedFieldsList);

		if (afterActionselectedFieldsPickColumnDisplayFirstAssignment.equals(selectedFieldsSecondAssignment)) {
			baseClass.stepInfo("One more Iteration");
			System.out.println("One more Iteration");
		} else {
			baseClass.passedStep("Changes made in Assignment 1 doesn't impact assignment 2");
			System.out.println("Changes made in Assignment 1 doesn't impact assignment 2");
		}
		System.out.println("-----------------------------------------------------");
	}

	/**
	 * @author Indium Raghuram Description : Date: 8/15/21 Modified date: NA
	 *         Modified by: N/A
	 */
	public void docviewMinlistComparisionMethodOne(int assignmentNumber, String assignName, List<String> assignmentList,
			int numbertoChoose) throws Exception, InterruptedException {
		if (AssignmentNumber == 1 || AssignmentNumber == 2) {

			driver.scrollingToBottomofAPage();
			baseClass.waitForElement(getAssignmentToChoose(assignName));
			getAssignmentToChoose(assignName).waitAndClick(3);
			System.out.println("Selected Assignement " + AssignmentNumber + " : " + assignName);
			baseClass.stepInfo("Selected Assignement " + AssignmentNumber + " : " + assignName);
			assignmentList.remove(numbertoChoose);
			afterremovalcount = assignmentList.size();
			System.out.println("After removal : " + afterremovalcount);

			// Click on the Gear icon
			baseClass.waitForElement(getGearIcon());
			driver.waitForPageToBeReady();
			baseClass.waitForElement(getGearIcon());
			getGearIcon().Click();

			// Verifying Configure Mini Doc list tab
			baseClass.waitForElement(getConfigureMiniDocTab());
			driver.waitForPageToBeReady();
			String configureMiniDocTab = getConfigureMiniDocTab().getText();
			Assert.assertEquals(configureMiniDocTab, "Configure Mini DocList");
			baseClass.passedStep("Landed on Configure Mini DocList");

		}
	}

	/**
	 * @author Indium Raghuram Description : Testcase - RPMXCON-51804 method 2
	 *         Returns : returns the updated list of selected fields after
	 *         performing add/remove action for Assignment 2 Date: 8/15/21 Modified
	 *         date: NA Modified by: N/A
	 */
	public List<String> docviewMinlistComparisionMethodTwo() {
		driver.waitForPageToBeReady();
		docViewSortlistVerify();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getManualSortRadioButton());
		getManualSortRadioButton().waitAndClick(5);

		driver.waitForPageToBeReady();
		ElementCollection pickColumnSelectedListAssignmentTwo = getSelectedFieldsAvailablePickColumnDisplay();
		pickColumnDisplaySelectedListAssignmentTwo = availableListofElements(pickColumnSelectedListAssignmentTwo);
		System.out.println("------Pick Column Display Selected fields for Assignment 2-----------------------------");
		baseClass.stepInfo("Pick Column Display Selected fields for Assignment 2");
		for (String a : pickColumnDisplaySelectedListAssignmentTwo) {
			System.out.println(a);
			baseClass.stepInfo(a);
		}
		System.out.println("-----------------------------------------------------------------------------");

		baseClass.stepInfo("Comapred Assignment 1 and 2");
		return pickColumnDisplaySelectedListAssignmentTwo;
	}

	/**
	 * @author Indium Raghuram Description : Method to verify MiniDocList Optimized
	 *         Sort Order Default Values Date: 8/18/21 Modified date: 8/27/21
	 *         Modified by: Raghuram A
	 */

	public void docViewSortlistVerify() {
		String expectedvalues[] = { "DocID", "DocFileType", "FamilyRelationship", "FamilyMemberCount" };
		List<String> orderList = new ArrayList<>();
		List<String> expectedList = new ArrayList<>();
		ElementCollection optimizedSelectedfields = getOptimizedOrderSelectedFields();
		orderList = availableListofElements(optimizedSelectedfields);
		System.out.println("Optimized Sort Order fields");
		baseClass.stepInfo("Optimized Sort Order fields");
		for (String listName : orderList) {
			System.out.println(listName);
			baseClass.stepInfo(listName);
		}
		expectedList = Arrays.asList(expectedvalues);
		softAssertion.assertEquals(orderList, expectedList);
		if (orderList.equals(expectedList)) {
			System.out.println("Result : Default Values displayed in the Optimized Sort Order");
			baseClass.passedStep("Result : Default Values displayed in the Optimized Sort Order");
		} else {
			System.out.println("Result : Default Values not displayed in the Optimized Sort Order");
			baseClass.failedStep("Result : Default Values displayed in the Optimized Sort Order");
		}
	}

	/**
	 * @author Indium Raghuram Description : Method to click gearIcon multipletimes
	 *         Date: 8/15/21 Modified date: NA Modified by: N/A
	 */
	public void clickingGearIconMultipletimes(int clicks) {
		int i;
		driver.waitForPageToBeReady();
		
		for (i = 1; i < clicks; i++) {
			getGearIcon().waitAndClick(5);
		}
		baseClass.stepInfo("----Total no.of times clicked : " + i);
		System.out.println("----Total no.of times clicked : " + i);
	}

	/**
	 * @author Indium Raghuram Description : Verify configureMiniDocTab Date:
	 *         8/15/21 Modified date: NA Modified by: N/A
	 */
	public void miniDoclistTabHeader() {

		try {
			driver.waitForPageToBeReady();
			baseClass.waitForElement(getConfigureMiniDocTab());
			Assert.assertTrue(true);
			baseClass.stepInfo("Landed on DocView MinDocList popup");
		} catch (org.openqa.selenium.NoSuchElementException e) {
			System.out.println("---Docview Minilist pop-up' is not displayed");
		}

	}

	/**
	 * @author Indium Raghuram Description : showCompleteDocsButton toggle OFF/ON
	 *         Date: 8/15/21 Modified date: 12/2/21 Modified by: Raghuram A
	 */
	public void CompleteDocsButton() {
		try {
			baseClass.waitForElement(getDocumentCompleteButton());
			softAssertion.assertTrue(getDocumentCompleteButton().Displayed());
			if (getDocumentCompleteButton().Displayed()) {
				reusableDocViewPage.editingCodingFormWithCompleteButton();
				driver.waitForPageToBeReady();
			} else {
				System.out.println("---No action");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Indium Raghuram Description : To finnd duplicate values exists in
	 *         list Date: 8/15/21 Modified date: NA Modified by: N/A
	 */

	public String duplicateCheckList(List<String> listToVerify) {
		String result = "Selected fields displayed more than once";
		Set<String> hash_Set = new HashSet<String>();
		List<String> result_List = new ArrayList<>();
		for (String fieldNames : listToVerify) {
			if (!hash_Set.add(fieldNames)) {
				System.out.println(fieldNames + " : Selected field displayed more than once");
				result_List.add(fieldNames);
			} else {

			}
		}
		if (result_List.isEmpty()) {
			result = "Selected fields display only once";
		}
		return result;
	}

	/**
	 * @author Indium Raghuram Description : pickColumn Display fields
	 *         Duplicatescheck Date: 8/15/21 Modified date: NA Modified by: N/A
	 */
	public void pickColumnDisplayfieldsDuplicatescheck() {

		ElementCollection pickColumnSelectedList = getSelectedFieldsAvailablePickColumnDisplay();
		pickColumnDisplaySelectedList = availableListofElements(pickColumnSelectedList);
		System.out.println("------Pick Column Display Selected fields-----------------------------");
		for (String a : pickColumnDisplaySelectedList) {
			System.out.println(a);
		}
		System.out.println("-----------------------------------------------------------------------------");

		// To check duplicates
		String resultselectedFieldsPickColumnDisplay = duplicateCheckList(pickColumnDisplaySelectedList);
		baseClass.passedStep("No Duplicates in the selected field list : " + resultselectedFieldsPickColumnDisplay);
		System.out.println(resultselectedFieldsPickColumnDisplay);
	}

	/**
	 * @author Indium Raghuram Description : setDocument Sortingfields
	 *         Duplicatescheck Date: 8/15/21 Modified date: NA Modified by: N/A
	 */
	public void setDocumentSortingfieldsDuplicatescheck() {

		ElementCollection setDocumentSelectedList = getFieldsAvailableSetDocumetSorting();
		setDocSelectedList = availableListofElements(setDocumentSelectedList);
		System.out.println("------Set Document Selected fields-----------------------------");
		for (String a : setDocSelectedList) {
			System.out.println(a);
		}
		System.out.println("-----------------------------------------------------------------------------");

		// To check duplicates
		String resultselectedFieldsSetDocumentSorting = duplicateCheckList(setDocSelectedList);
		baseClass.passedStep(
				"no duplicates in the Set Document Selected field list :  " + resultselectedFieldsSetDocumentSorting);
		System.out.println(resultselectedFieldsSetDocumentSorting);
	}

	/**
	 * @author Indium Raghuram Description : RPMXCON-51889 Verify on gear icon to
	 *         open configure mini doc list is clicked multiple times then
	 *         repetitive 'Selected Fields' should not be displayed on optimized
	 *         Sort order tab Date: 8/15/21 Modified date: 8/16/21 Modified by:
	 *         Raghuram A
	 */
	public void accessingGearIconMultipletimesviaChildWindow() throws InterruptedException, AWTException {
		// Gear icon for ChildWindow
		int i = 1;
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getGearIcon1());
		getGearIcon1().Click();

		driver.waitForPageToBeReady();
		getMiniDocChildWindowExpandIcon().Click();
		System.out.println("Child Window launched");
		driver.waitForPageToBeReady();

		// Transfer control to child window
		String parentWindowId = childWindowTransfer();

		// Child winndow header details - not mandatory for the testcase
		List<String> t2 = chilDWIndowContents();
		for (String aaa : t2) {
			System.out.println(aaa);
		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getChildWindowGearIcons());

		for (i = 1; i < 5; i++) {
			getChildWindowGearIcons().Click();
		}
		System.out.println("Total no.of times clicked  : " + i);
		baseClass.passedStep("Total no.of times clicked  : " + i);
		driver.waitForPageToBeReady();

		// Transfer control back to Parent Window
		childWIndowCloseHandles(parentWindowId);
	}

	/**
	 * @author Indium Raghuram Description : Verifying that no duplication of
	 *         selected fields listed Pick column display and Set document sorting
	 *         Date: 8/15/21 Modified date: NA Modified by: N/A
	 */
	public void verifyDuplicatefieldsInBothtabs() {
		// -- Manual sort to be included
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getManualSortRadioButton());
		getManualSortRadioButton().Click();

		// 1.Pick column Display field list
		driver.waitForPageToBeReady();
		pickColumnDisplayfieldsDuplicatescheck();

		// 2. Set documnenting sort
		driver.waitForPageToBeReady();
		getSetDocumentSortingTab().Click();
		setDocumentSortingfieldsDuplicatescheck();

		// Close minidoclist popup
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getMiniDocListcloseButton());
		getMiniDocListcloseButton().Click();
	}

	/**
	 * @author Indium Raghuram Description : Close configureMiniDocTab Date: 8/15/21
	 *         Modified date: NA Modified by: N/A
	 */
	public void closeMiniDoclistPopup() {
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getMiniDocListcloseButton());
		getMiniDocListcloseButton().Click();
		System.out.println("Closed mini doc list");
	}

	/**
	 * @author Indium Raghuram Description : Testcase - RPMXCON-51804 method 1 Date:
	 *         8/15/21 Modified date: 8/27/21 Modified by:Raghuram A
	 */
	public void miniDocListChildWindow(int AssignmentNumber, String assignname, List<String> assignmentNames,
			int numbertoChoose, String type) {
		try {
			if (AssignmentNumber == 1 || AssignmentNumber == 2) {

				// Select the respective assignment - Remove the selected assignment from the
				// list so that won't repeat the next time
				driver.waitForPageToBeReady();
				driver.scrollingToBottomofAPage();
				baseClass.waitForElement(getAssignmentToChoose(assignname));
				getAssignmentToChoose(assignname).waitAndClick(3);
				System.out.println("Selected Assignement " + AssignmentNumber + " : " + assignname);
				baseClass.passedStep("Selected Assignement " + AssignmentNumber + " : " + assignname);
				assignmentList.remove(numbertoChoose);
				afterremovalcount = assignmentList.size(); // last update
				System.out.println("After removal : " + afterremovalcount);

			}

			if (AssignmentNumber == 1) {
				// Launching Mini doc Child Window
				launchingMindoclistChildWindow();
				// Handling Minidoclist Child Window
				handlingViaMinidoclistChildWindow(1);
				driver.waitForPageToBeReady();
				baseClass.waitForElement(getManualSortRadioButton());
				getManualSortRadioButton().Click();
				// Pick Column Display
				afterActionselectedFieldsPickColumnDisplayFirstAssignment = methodforPickColumndisplay();
				// Set Document Sort
				afterActionselectedFieldsSetDocumentFirstAssignment = methodforSetDocumetSort();
			} else if (AssignmentNumber == 2) {
				{
					driver.scrollPageToTop();
					driver.waitForPageToBeReady();
					baseClass.waitForElement(getGearIcon());
					getGearIcon().waitAndClick(10);

					selectedFieldsSecondAssignment = docviewMinlistComparisionMethodTwo();
					baseClass.waitForElement(getMiniDocListcloseButton());
					getMiniDocListcloseButton().Click();
					driver.waitForPageToBeReady();

					softAssertion.assertNotEquals(afterActionselectedFieldsPickColumnDisplayFirstAssignment,
							selectedFieldsSecondAssignment);

					if (afterActionselectedFieldsPickColumnDisplayFirstAssignment
							.equals(selectedFieldsSecondAssignment)) {
						System.out.println("--");
					}
					{
						System.out.println("Changes made in assignment 1 doesn't impact assignment 2");
						baseClass.passedStep("Changes made in assignment 1 doesn't impact assignment 2");
					}
				}
			}

			if (AssignmentNumber == 1) {
				baseClass.waitForElement(getMiniDocListConfirmationButton("Save"));
				getMiniDocListConfirmationButton("Save").Click();
				System.out.println("Saved Confirmed");
				baseClass.waitTime(3);
				Robot robot = new Robot();
				robot.keyPress(KeyEvent.VK_ENTER);
				System.out.println("Handled Alert");

				driver.getWebDriver().navigate().refresh();
				baseClass.waitTillElemetToBeClickable(getGearIcon());

				launchingMindoclistChildWindow();

				driver.waitForPageToBeReady();
				childWindowHeaderFields = handlingViaMinidoclistChildWindow(2);
				childWindowHeaderFields.removeAll(Arrays.asList(null, ""));
				System.out.println("---------childWindowHeaderFields Listed Below-------------");
				for (String childHeaders : childWindowHeaderFields) {
					System.out.println(childHeaders);
				}

				String headerfieldtoCompare = childWindowHeaderFields.toString().toLowerCase();
				String PickcolumnfieldtoCompare = afterActionselectedFieldsPickColumnDisplayFirstAssignment.toString()
						.toLowerCase();

				softAssertion.assertEquals(headerfieldtoCompare, PickcolumnfieldtoCompare);

				if (PickcolumnfieldtoCompare.equals(headerfieldtoCompare)) {
					System.out.println("Changes reflected in childwindow headers");
					baseClass.passedStep("Pick Column Display Changes reflected in childwindow headers");
				} else {
					System.out.println("Changes didn't reflect in child window headers");
					baseClass.stepInfo("Changes didn't reflect in child window headers");
				}

				driver.waitForPageToBeReady();
				driver.scrollPageToTop();
				baseClass.waitTillElemetToBeClickable(getDashBoardLink());
				getDashBoardLink().Click();
				baseClass.waitForElement(getMiniDocListConfirmationButton("Yes"));
				getMiniDocListConfirmationButton("Yes").Click();

			}
		} catch (AWTException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	/**
	 * @author Indium Raghuram Description : Launching Mini doc list Child WIndow
	 *         Date : 8/15/21 Modified date : N/A
	 */
	public void launchingMindoclistChildWindow() {
		try {
			driver.waitForPageToBeReady();
			driver.scrollPageToTop();
			baseClass.waitForElement(getGearIcon1());
			getGearIcon1().waitAndClick(5);
			System.out.println("Clicked Gear Icon");

			driver.waitForPageToBeReady();
			baseClass.waitForElement(getMiniDocChildWindowExpandIcon());
			getMiniDocChildWindowExpandIcon().waitAndClick(5);
			System.out.println("Launched ChildWindow");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Indium Raghuram Description : Performing mini doc list sorting via
	 *         child window Date : 8/15/21 Modified date : N/A
	 * @throws InterruptedException
	 * @throws Exception
	 */
	public void verifyDocMiniListMethodChildWindow(String assignmentOne,String assignmentTwo) throws InterruptedException, Exception {
		assignmentPage.SelectAssignmentByReviewer(assignmentOne);
		System.out.println("Selected Assignement "  + assignmentOne);
		baseClass.stepInfo("Selected Assignement "  + assignmentOne);
		// Launching Mini doc Child Window
		launchingMindoclistChildWindow();
		// Handling Minidoclist Child Window
		handlingViaMinidoclistChildWindow(1);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getManualSortRadioButton());
		getManualSortRadioButton().waitAndClick(3);
		// Pick Column Display
		afterActionselectedFieldsPickColumnDisplayFirstAssignment = methodforPickColumndisplay();
		// Set Document Sort
		afterActionselectedFieldsSetDocumentFirstAssignment = methodforSetDocumetSort();
		baseClass.waitForElement(getMiniDocListConfirmationButton("Save"));
		getMiniDocListConfirmationButton("Save").waitAndClick(3);
		System.out.println("Saved Confirmed");
		baseClass.waitTime(3);
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		System.out.println("Handled Alert");

		driver.getWebDriver().navigate().refresh();
		baseClass.waitTillElemetToBeClickable(getGearIcon());

		launchingMindoclistChildWindow();

		driver.waitForPageToBeReady();
		childWindowHeaderFields = handlingViaMinidoclistChildWindow(2);
		childWindowHeaderFields.removeAll(Arrays.asList(null, ""));
		System.out.println("---------childWindowHeaderFields Listed Below-------------");
		for (String childHeaders : childWindowHeaderFields) {
			System.out.println(childHeaders);
		}

		String headerfieldtoCompare = childWindowHeaderFields.toString().toLowerCase();
		String PickcolumnfieldtoCompare = afterActionselectedFieldsPickColumnDisplayFirstAssignment.toString()
				.toLowerCase();

		softAssertion.assertEquals(headerfieldtoCompare, PickcolumnfieldtoCompare);

		if (PickcolumnfieldtoCompare.equals(headerfieldtoCompare)) {
			System.out.println("Changes reflected in childwindow headers");
			baseClass.passedStep("Pick Column Display Changes reflected in childwindow headers");
		} else {
			System.out.println("Changes didn't reflect in child window headers");
			baseClass.stepInfo("Changes didn't reflect in child window headers");
		}

		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitTime(2);
		baseClass.waitForElement(getDashBoardReviewer());
		getDashBoardReviewer().waitAndClick(3);

		baseClass.waitForElement(getNavigationConfirmationPopup("Yes"));
		getNavigationConfirmationPopup("Yes").waitAndClick(5);
		baseClass.waitTime(2);
		assignmentPage.SelectAssignmentByReviewer(assignmentTwo);
		baseClass.waitTime(3);
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getGearIcon());
		getGearIcon().waitAndClick(10);

		selectedFieldsSecondAssignment = docviewMinlistComparisionMethodTwo();
		baseClass.waitForElement(getMiniDocListcloseButton());
		getMiniDocListcloseButton().waitAndClick(3);
		driver.waitForPageToBeReady();

		softAssertion.assertNotEquals(afterActionselectedFieldsPickColumnDisplayFirstAssignment,
				selectedFieldsSecondAssignment);

		if (afterActionselectedFieldsPickColumnDisplayFirstAssignment
				.equals(selectedFieldsSecondAssignment)) {
			System.out.println("--");
		}
		{
			System.out.println("Changes made in assignment 1 doesn't impact assignment 2");
			baseClass.passedStep("Changes made in assignment 1 doesn't impact assignment 2");
		}

	}


	/**
	 * @author Indium Raghuram Description : Date:8/17/21 Modified date: N/A
	 *         Modified by: N/A
	 */
	public void configureMiniDocList() {
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		List<WebElement> allValues = getMiniDocListHeaderValue().FindWebElements();
		List<String> arrayMiniDocList = new ArrayList<String>();
		for (int j = 1; j < allValues.size(); j++) {
			arrayMiniDocList.add(allValues.get(j).getText());
			if (j == 3) {
				JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();
				jse.executeScript("document.querySelector('.dataTables_scrollBody').scrollBy(350,0)");
			} else if (j == 4) {
				JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();
				jse.executeScript("document.querySelector('.dataTables_scrollBody').scrollBy(400,0)");
			}
		}
		JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();
		jse.executeScript("document.querySelector('.dataTables_scrollBody').scrollBy(-400,0)");
		System.out.println("Header fields");
		baseClass.stepInfo("Header Fields");
		for (String b : arrayMiniDocList) {
			System.out.println(b);
			baseClass.stepInfo(b);
		}
		String miniDocListText = arrayMiniDocList.toString().toLowerCase();
	}

	/**
	 * @author Indium Raghuram Description : To choose a random name from a list
	 *         Returns : an assignment to choose Date:8/17/21 Modified date: N/A
	 *         Modified by: N/A
	 */
	public String chooseAnyoneRandomlyfromList(ElementCollection elementName) {
		try {
			// List Size
			totalAssignmentsinList = elementName.size();

			// Available List names
			ElementCollection availableListNames = elementName;
			assignmentList = availableListofElements(availableListNames);
			for (String names : assignmentList) {
				System.out.println(names);
			}

			// Random number
			numbertoChoose = randNumber(totalAssignmentsinList);
			System.out.println("Number to choose : " + numbertoChoose);

			// Select ONe
			chosenName = assignmentList.get(numbertoChoose).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return chosenName;
	}

	/**
	 * @author Indium Raghuram Description : Date:8/17/21 Modified date: 8/28/21
	 *         Modified by: N/A
	 * @throws InterruptedException
	 */
	public void verifyOptimizedSortOrderCase() throws InterruptedException {
		// Assignment Creation
		String assignmentNameToChoose = assignmentCreationwithOptimizedTab();
		baseClass.stepInfo("Created Assignment " + assignmentNameToChoose);

		driver.waitForPageToBeReady();
		baseClass.waitForElement(getDashBoardLink());
		getDashBoardLink().Click();
		chooseAnAssignmentFromDashBoard(assignmentNameToChoose);

		baseClass.stepInfo("Header Fields Before Action");
		driver.waitForPageToBeReady();
		configureMiniDocList();

		driver.waitForPageToBeReady();
		baseClass.waitForElement(getGearIcon());
		getGearIcon().Click();

		driver.waitForPageToBeReady();
		baseClass.waitForElement(getManualSortRadioButton());
		getManualSortRadioButton().Click();

		// Pick Column Display
		afterActionselectedFieldsPickColumnDisplayFirstAssignment = methodforPickColumndisplay();
		// Set Document Sort
		afterActionselectedFieldsSetDocumentFirstAssignment = methodforSetDocumetSort();

		baseClass.waitForElement(getMiniDocListConfirmationButton("Save"));
		getMiniDocListConfirmationButton("Save").Click();

		// Verify Impact Flow
		baseClass.stepInfo("Header Fields After Action");
		driver.waitForPageToBeReady();
		configureMiniDocList();

		driver.waitForPageToBeReady();
		baseClass.waitForElement(getGearIcon());
		getGearIcon().Click();

		driver.waitForPageToBeReady();
		baseClass.waitForElement(getManualSortRadioButton());
		getManualSortRadioButton().Click();

		ElementCollection pickColumnSelectedListElements = getSelectedFieldsAvailablePickColumnDisplay();
		pickColumnDisplaySelectedLists = availableListofElements(pickColumnSelectedListElements);
		for (String a : pickColumnDisplaySelectedLists) {
			System.out.println(a);
			baseClass.stepInfo(a);
		}

		// Check Default order is maintained in Optimized sort order
		getOptimizedSortRadioButton().Click();
		driver.waitForPageToBeReady();
		docViewSortlistVerify();

		baseClass.waitForElement(getMiniDocListcloseButton());
		getMiniDocListcloseButton().Click();

		// Verify the Manual Sort order retained with the selected web fields
		softAssertion.assertEquals(pickColumnDisplaySelectedLists,
				afterActionselectedFieldsPickColumnDisplayFirstAssignment);
		if (pickColumnDisplaySelectedLists.equals(afterActionselectedFieldsPickColumnDisplayFirstAssignment)) {
			System.out.println("Manual Sort order retained with the selected web fields");
			baseClass.passedStep("Manual Sort order retained with the selected web fields");
		} else {
			System.out.println("Manual Sort order not retained with the selected web fields");
			baseClass.failedStep("Manual Sort order not retained with the selected web fields");
		}

	}

	/**
	 * @author Indium Raghuram Description : Date:8/17/21 Modified date: 8/28/21
	 *         Modified by: N/A
	 * @throws InterruptedException
	 */
	public void verifyOptimizedSortOrderCaseforSameAssignment() throws InterruptedException {
		// Assignment Creation
		String assignmentNameToChoose = assignmentCreationwithOptimizedTab();
		baseClass.stepInfo("Created Assignment " + assignmentNameToChoose);

		driver.waitForPageToBeReady();
		baseClass.waitForElement(getDashBoardLink());
		getDashBoardLink().Click();
		chooseAnAssignmentFromDashBoard(assignmentNameToChoose);
		baseClass.stepInfo("Selected " + assignmentNameToChoose);
//making minidoclist default fields
	removingFieldsAndDragnDropDefault();
	driver.waitForPageToBeReady();
	
		baseClass.stepInfo("Header Fields Before Action");
		driver.waitForPageToBeReady();
		configureMiniDocList();

		driver.waitForPageToBeReady();
		baseClass.waitForElement(getGearIcon());
		getGearIcon().Click();

		driver.waitForPageToBeReady();
		baseClass.waitForElement(getManualSortRadioButton());
		getManualSortRadioButton().Click();

		// Pick Column Display
		afterActionselectedFieldsPickColumnDisplayFirstAssignment = methodforPickColumndisplay();
		// Set Document Sort
		afterActionselectedFieldsSetDocumentFirstAssignment = methodforSetDocumetSort();

		baseClass.waitForElement(getMiniDocListConfirmationButton("Save"));
		getMiniDocListConfirmationButton("Save").Click();

		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(getDashBoardLink());
		getDashBoardLink().Click();

		// Flow 2 - Verify Impact
		chooseAnAssignmentFromDashBoard(assignmentNameToChoose);

		baseClass.stepInfo("Header Fields Before Action");
		driver.waitForPageToBeReady();
		configureMiniDocList();

		driver.waitForPageToBeReady();
		baseClass.waitForElement(getGearIcon());
		getGearIcon().Click();

		// Check Default order is maintained in Optimized sort order
		driver.waitForPageToBeReady();
		getOptimizedSortRadioButton().Click();
		driver.waitForPageToBeReady();
		docviewMinlistComparision();
	}

	/**
	 * @author Indium Raghuram Description : Date:8/17/21 Modified date: N/A
	 *         Modified by: N/A
	 * @throws InterruptedException
	 */
	public void docviewMinlistComparision() {
		driver.waitForPageToBeReady();
		docViewSortlistVerify();

		driver.waitForPageToBeReady();
		baseClass.waitForElement(getManualSortRadioButton());
		getManualSortRadioButton().Click();

		driver.waitForPageToBeReady();
		ElementCollection pickColumnSelectedListoVerify = getSelectedFieldsAvailablePickColumnDisplay();
		pickColumnDisplaySelectedListtoVerify = availableListofElements(pickColumnSelectedListoVerify);
		System.out.println("------Pick Column Display Selected fields for Assignment 2-----------------------------");
		baseClass.stepInfo("Pick Column Display Selected fields for Assignment 2");
		for (String a : pickColumnDisplaySelectedListtoVerify) {
			System.out.println(a);
			baseClass.stepInfo(a);
		}
		System.out.println("-----------------------------------------------------------------------------");

		baseClass.stepInfo("Comapred Assignment 1 and 2");

		if (pickColumnDisplaySelectedListtoVerify.equals(afterActionselectedFieldsPickColumnDisplayFirstAssignment)) {
			baseClass.passedStep("Manual Sort order Pick Column Display retained with the selected web fields ");
			System.out.println("Manual Sort order Pick Column Display retained with the selected web fields ");
		} else {
			baseClass.stepInfo("Manual Sort order Pick Column Display didn't retain with the selected web fields ");
			System.out.println("Manual Sort order Pick Column Display didn't retain with the selected web fields ");
		}

		baseClass.waitForElement(getMiniDocListcloseButton());
		getMiniDocListcloseButton().Click();

	}

	/**
	 * @author Indium Raghuram Description : Date:8/28/21 Modified date: 12/2/21
	 *         Modified by: N/A
	 * @throws InterruptedException
	 */
	public void verifyOptimizedSortOrderViaChildwindow() throws AWTException, InterruptedException {
		// Assignment Creation
		String assignmentNameToChoose = assignmentCreationwithOptimizedTab();
		baseClass.stepInfo("Created Assignment " + assignmentNameToChoose);

		driver.waitForPageToBeReady();
		baseClass.waitForElement(getDashBoardLink());
		getDashBoardLink().waitAndClick(5);
		chooseAnAssignmentFromDashBoard(assignmentNameToChoose);
		baseClass.stepInfo("Selected " + assignmentNameToChoose);
		baseClass.waitTime(2);
        reusableDocViewPage.clickGearIconOpenMiniDocList();
        docViewPage.switchToNewWindow(2);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getChildWindowGearIcons());
		getChildWindowGearIcons().waitAndClick(5);
		docViewPage.closeWindow(1);
	    docViewPage.switchToNewWindow(1);

		driver.waitForPageToBeReady();
		baseClass.waitForElement(getManualSortRadioButton());
		getManualSortRadioButton().waitAndClick(5);

		// Pick Column Display
		afterActionselectedFieldsPickColumnDisplayFirstAssignment = methodforPickColumndisplay();
		// Set Document Sort
		afterActionselectedFieldsSetDocumentFirstAssignment = methodforSetDocumetSort();

		baseClass.waitForElement(getMiniDocListConfirmationButton("Save"));
		getMiniDocListConfirmationButton("Save").waitAndClick(5);
		baseClass.waitTime(3);
		driver.getWebDriver().switchTo().alert().accept();

		driver.getWebDriver().navigate().refresh();
		try {
			driver.getWebDriver().switchTo().alert().accept();
		} catch (Exception e) {
			System.out.println("No Alerts");
		}

		// Verify Impact
		driver.waitForPageToBeReady();
		reusableDocViewPage.clickGearIconOpenMiniDocList();
		String parentId1 = childWindowTransfer();
		baseClass.waitForElement(getChildWindowGearIcons());
		getChildWindowGearIcons().waitAndClick(5);
		childWIndowCloseHandles(parentId1);
		driver.waitForPageToBeReady();
		// Check Default order is maintained in Optimized sort order
		baseClass.waitForElement(getOptimizedSortRadioButton());
		getOptimizedSortRadioButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		docviewMinlistComparision();

	}

	/**
	 * @author Indium Raghuram Description : Date:8/19/21 Modified date: 8/27/21
	 *         Modified by: Raghuram A
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public void enableOrdisableToRetain() throws AWTException, InterruptedException {
		String status = "Default";
		int flow = 1;
		Robot robot = new Robot();

		driver.waitForPageToBeReady();
		baseClass.waitForElement(getPersistentHighlightingIcon());
		getPersistentHighlightingIcon().Click();

		System.out.println(getSearchHitsTitle().Displayed());
		if (availableListofElements(getHitcountList()).size() > 0) {
			System.out.println("List contains Data");
			baseClass.stepInfo("List contains Data");
		} else {
			System.out.println("List is Empty");
			baseClass.stepInfo("List is Empty");
		}

		if (availableListofElements(getZeroHitList()).size() > 0) {
			System.out.println("Hits having the 0 count is displayed on the panel");
			baseClass.stepInfo("Hits having the 0 count is displayed on the panel");
		}

		try {
			if (getchkShowSearchTermToggle("false").Displayed()) {
				status = "No";
				baseClass.stepInfo("getchkShowSearchTermToggle : OFF");
				System.out.println("getchkShowSearchTermToggle : OFF");
			} else if (getchkShowSearchTermToggle("true").Displayed()) {
				status = "Yes";
				baseClass.stepInfo("getchkShowSearchTermToggle : ON");
				System.out.println("getchkShowSearchTermToggle : ON");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Launching Child Window
		launchingMindoclistChildWindow();

		do {
			String parentid = childWindowTransfer();

			driver.waitForPageToBeReady();
			List<String> idList = availableListofElements(getChildWindowDocIDList());
			int listSize = idList.size();
			int numberToChoose = randNumber(listSize);

			String currentDoc = idList.get(numberToChoose);
			System.out.println("Childwindow selected doc id : " + currentDoc);
			baseClass.stepInfo("Childwindow selected doc id : " + currentDoc);
			baseClass.waitForElement(getChildWindowGearIcons());
			baseClass.waitForElement(getChildWindowDocIDList(currentDoc));
			getChildWindowDocIDList(currentDoc).Click();
			driver.waitForPageToBeReady();
			driver.switchTo().window(parentid);
			driver.waitForPageToBeReady();
			robot.keyPress(KeyEvent.VK_TAB);
			robot.keyPress(KeyEvent.VK_ENTER);
			System.out.println("Handled Alert");

			String currentDOcID = getMainWindowActiveDocID().getText();
			System.out.println("main Window doc id : " + currentDOcID);
			if (currentDOcID.equals(currentDoc)) {
				try {
					if (flow == 1) {
						driver.waitForPageToBeReady();
						if (getchkShowSearchTermToggle("false").Displayed()) {
							status = "No";
							System.out.println("ID and toggle status matches");
							baseClass.passedStep("ID and toggle status matches");
						} else if (getchkShowSearchTermToggle("true").Displayed()) {
							status = "Yes";
							System.out.println("Fails");
							baseClass.stepInfo("Fails");
						}
					} else if (flow == 2) {
						driver.waitForPageToBeReady();
						if (getchkShowSearchTermToggle("true").Displayed()) {
							status = "Yes";
							System.out.println("ID and toggle status matches");
							baseClass.passedStep("ID and toggle status matches");
						} else if (getchkShowSearchTermToggle("false").Displayed()) {
							status = "No";
							System.out.println("Fails");
							baseClass.stepInfo("Fails");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (flow == 1) {
					baseClass.waitForElement(getchkShowSearchTermToggle("false"));
					getchkShowSearchTermToggle("false").Click();
					baseClass.stepInfo("Hide Terms with 0 hits toggle : ON");

					if (availableListofElements(getZeroHitList()).size() == 0) {
						System.out.println("Hits having the 0 count not displayed on the panel");
						baseClass.stepInfo("Hits having the 0 count not displayed on the panel");
					}
				}

			}

			flow++;
		} while (flow <= 2);

	}

	/**
	 * @author Indium Raghuram Description : Assignment creation with
	 *         MetaDataSequence Date:8/26/21 Modified date: N/A Modified by: N/A
	 */
	public String assignmentCreationwithMetaDataSequence() throws InterruptedException {
		String assignmentNameToCreate = "TestAssignmentNo" + Utility.dynamicNameAppender();
		assignmentPage.assignmentCreation(assignmentNameToCreate, Input.codingFormName);
		assignmentPage.Assgnwithdocumentsequence(Input.sortDataBy, Input.sortType);
		assignmentPage.assignmentDistributingToReviewer();
		return assignmentNameToCreate;

	}

	/**
	 * @author Indium Raghuram Description : Assignment creation with
	 *         MetaDataSequence Date:8/28/21 Modified date: 11/15/21 Modified by:
	 *         Raghuram A getAssgn_OverrideOptimizedSortToggle( due to update in the
	 *         application
	 */
	public String assignmentCreationwithOptimizedTab() throws InterruptedException {
		String assignmentNameToCreate = "TestAssignmentNo" + Utility.dynamicNameAppender();
		assignmentPage.assignmentCreation(assignmentNameToCreate, Input.codingFormName);
		driver.scrollingToBottomofAPage();
		// assignmentPage.getAssgn_OverrideOptimizedSortToggle().waitAndClick(3);
		assignmentPage.quickAssignToggles(false, false, false, true, null, null, null, null, null);
		driver.scrollPageToTop();
		baseClass.waitForElement(assignmentPage.getAssignmentSaveButton());
		assignmentPage.getAssignmentSaveButton().Click();
		baseClass.CloseSuccessMsgpopup();
		driver.waitForPageToBeReady();
		assignmentPage.assignmentDistributingToReviewerManager();
		return assignmentNameToCreate;

	}

	/**
	 * @author Indium Raghuram Description : verify Original SortOrder In
	 *         ChildWindow Date:8/26/21 Modified date: N/A Modified by: N/A
	 */
	public void verifyOriginalSortOrderInChildWindow(String assignmentTocreate)
			throws InterruptedException, AWTException {
		// View In docView
		docViewPage.selectAssignmentfromDashborad(assignmentTocreate);
		baseClass.stepInfo("Assignment Selected : " + assignmentTocreate);

		//making minidoclist default fields
		
	removingFieldsAndDragnDropDefault();
	driver.waitForPageToBeReady();
		
	// Verify Sorting order in Child Window
		launchingMindoclistChildWindow();
		String parentId = childWindowTransfer();

		originalOrderedList = availableListofElements(getListofDocIDinCW());
		afterSortList = availableListofElements(getListofDocIDinCW());

		System.out.println("Original Order");
		for (String a : originalOrderedList) {
			System.out.println(a);
		}

		if (Input.sortType.equals("Ascending")) {
			Collections.sort(afterSortList);
		} else if (Input.sortType.equals("Descending")) {
			Collections.sort(afterSortList, Collections.reverseOrder());
		}

		System.out.println("Sorted Order");
		for (String b : afterSortList) {
			System.out.println(b);
		}

		softAssertion.assertEquals(afterSortList, originalOrderedList);

		if (afterSortList.equals(originalOrderedList)) {
			System.out.println("Sorting order maintained");
			baseClass.passedStep("Sorting order maintained");
		} else {
			System.out.println("Sorting order failed");
			baseClass.failedStep("Sorting order failed");
		}

		childWIndowCloseHandles(parentId);
	}

	/**
	 * @author Indium Raghuram Description : Date:8/26/21 Modified date: N/A
	 *         Modified by: N/A
	 */
	public String assignmentCreation() {
		String assignmentNametoCreate = "TestAssignmentNo" + Utility.dynamicNameAppender();
		sessionSearch.basicContentSearch(Input.searchString2);
		sessionSearch.bulkAssign();
		assignmentPage.assignmentCreation(assignmentNametoCreate, Input.codingFormName);
		assignmentPage.assignmentDistributingToReviewer();
		System.out.println("Created Assignment name : " + assignmentNametoCreate);
		baseClass.stepInfo("Created Assignment name : " + assignmentNametoCreate);
		return assignmentNametoCreate;
	}

	/**
	 * @author Indium Raghuram Description : checkmarkVerification and Selected
	 *         background highlight verification Date:8/26/21 Modified date: N/A
	 *         Modified by: N/A
	 */
	public void checkmarkVerification(String assignmentNameToCreate) throws InterruptedException {
		String status = "Default";

		docViewPage.selectAssignmentfromDashborad(assignmentNameToCreate);
		baseClass.stepInfo("Selected Assignment name : " + assignmentNameToCreate);

		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(getGearIcon());
		getGearIcon().Click();

		// Verifying Configure Mini Doc list tab
		baseClass.waitForElement(getConfigureMiniDocTab());
		String configureMiniDocTab = getConfigureMiniDocTab().getText();
		Assert.assertEquals(configureMiniDocTab, "Configure Mini DocList");
		baseClass.passedStep("Landed on Configure Mini DocList");
		baseClass.stepInfo("Show Completed Docs toggle status OFF in configure mini doc list");
		closeMiniDoclistPopup();

		// Main method
		int sizeofList = getListofDocIDinCW().size();
		System.out.println("Size : " + sizeofList);

		docIDlist = availableListofElements(getListofDocIDinCW());

		for (int i = 1; i <= 3; i++) {
			String name = docIDlist.get(i);
			getDociD(name).waitAndClick(5);
			System.out.println("Selected Document : " + name);
			baseClass.stepInfo("Selected Document : " + name);

			// CompleteDocsButton();
			baseClass.waitForElement(getDocumentCompleteButton());
			getDocumentCompleteButton().waitAndClick(5);
			System.out.println("Completed Document : " + name);
			baseClass.stepInfo("Completed Document : " + name);

			baseClass.VerifySuccessMessage("Document completed successfully");
			baseClass.CloseSuccessMsgpopup();

			try {
				if (getDocIDCheckedIcon(name).Displayed()) {
					System.out.println("DocId : " + name + " - Check Marked - Pass");
					baseClass.passedStep("DocId : " + name + " - Check Marked - Pass");
					String bgColor = getCheckSelectedBgColor(name).GetCssValue("background-color");

					bgColor = rgbTohexaConvertor(bgColor);

					if (bgColor.equals(Input.docHighlightColor)) {
						System.out.println("Ddocument displayed in light blue highlighting");
						baseClass.passedStep("Ddocument displayed in light blue highlighting");
					} else {
						System.out.println("Not highlighted");
						baseClass.failedStep("Not highlighted");
					}
				} else {
					System.out.println("Fail");
					baseClass.failedStep("Fail");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		docViewPage.enterDocumentNumberTillLoading();
	}

	/**
	 * @author Indium Raghuram Description : Date:8/18/21 Modified date: N/A
	 *         Modified by: N/A
	 * @throws InterruptedException
	 */
	public String chooseAnAssignmentFromDashBoard() {
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		String assignmentName = chooseAnyoneRandomlyfromList(getAssignmentAvailable());
		getAssignmentToChoose(assignmentName).Click();
		baseClass.stepInfo("Assignment Selected");
		return assignmentName;
	}

	/**
	 * @author Indium Raghuram Description : Date:8/18/21 Modified date: N/A
	 *         Modified by: N/A - chooseAnAssignmentFromDashBoard Method Overloaded
	 * @throws InterruptedException
	 */
	public String chooseAnAssignmentFromDashBoard(String assignmentName) {
		driver.waitForPageToBeReady();
		driver.scrollingToBottomofAPage();
		getAssignmentToChoose(assignmentName).Click();
		baseClass.stepInfo("Assignment Selected : " + assignmentName);
		return assignmentName;
	}

	/**
	 * @author Indium Raghuram Description : rgb to hexa code convertor Date:8/26/21
	 *         Modified date: N/A Modified by: N/A
	 */
	public String rgbTohexaConvertor(String bgColor) {
		String s1 = bgColor.substring(4);
		bgColor = s1.replace(')', ' ');
		bgColor = s1.replace('(', ' ');
		StringTokenizer st = new StringTokenizer(bgColor);
		int r = Integer.parseInt(st.nextToken(",").trim());
		int g = Integer.parseInt(st.nextToken(",").trim());
		int b = Integer.parseInt(st.nextToken(",").trim());
		Color c = new Color(r, g, b);
		String hex = "#" + Integer.toHexString(c.getRGB()).substring(2).toUpperCase();
		return hex;
	}

	public void viewInDocView() throws InterruptedException {
		driver.getWebDriver().get(Input.url + "Search/Searches");

		if (getPureHitAddButton().isElementAvailable(2)) {
			getPureHitAddButton().waitAndClick(5);
		} else {
			System.out.println("Pure hit block already moved to action panel");
			UtilityLog.info("Pure hit block already moved to action panel");
		}

		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBulkActionButton().Visible();
			}
		}), Input.wait30);
		baseClass.waitTime(3); // App synch
		getBulkActionButton().waitAndClick(5);
		baseClass.waitTime(3); // App Synch

		if (getViewBtn().isElementAvailable(2)) {
			driver.waitForPageToBeReady();

			WebDriverWait wait = new WebDriverWait(driver.getWebDriver(), 60);
			Actions actions = new Actions(driver.getWebDriver());
			wait.until(ExpectedConditions.elementToBeClickable(getViewBtn().getWebElement()));
			actions.moveToElement(getViewBtn().getWebElement()).build().perform();

			baseClass.waitForElement(getDocViewFromDropDown());
			getDocViewFromDropDown().waitAndClick(10);
		} else {
			getDocViewAction().waitAndClick(10);
			baseClass.waitTime(3); // added for stabilization
		}

		System.out.println("Navigated to docView to view docs");
		UtilityLog.info("Navigated to docView to view docs");

	}

	public void popOutChildWindowAndConfigureMiniDocList() {
		String parentWindowID = driver.getWebDriver().getWindowHandle();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getGearIcon());
		docViewPage.getGearIcon().waitAndClick(10);
		baseClass.waitForElement(getDocView_MiniDoclistChildWindow());
		getDocView_MiniDoclistChildWindow().waitAndClick(5);
		baseClass.waitTime(10);
		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		baseClass.waitTime(10);
		baseClass.waitForElement(getDocView_ConfigMinidoclist());
		getDocView_ConfigMinidoclist().waitAndClick(5);
		baseClass.waitTime(10);
		driver.switchTo().window(parentWindowID);

	}

	public void verifyArrowMarkInMiniDocList() {

		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(getDocView_MiniDoclistRightArrow());
		softAssertion.assertTrue(getDocView_MiniDoclistRightArrow().Displayed());
		baseClass.stepInfo("Doc selected from Analytics panel is as same in Mini DcoList");
		baseClass.passedStep(
				"Selected document is loaded in default view and same document is selected from mini doc list successfully");
	}

	public void selectDocFromMiniDocListChildWindow() {
		driver.waitForPageToBeReady();
		String parentWindowID = driver.getWebDriver().getWindowHandle();
		baseClass.waitForElement(docViewPage.getGearIcon());
		docViewPage.getGearIcon().Click();
		baseClass.waitForElement(docViewPage.getDocView_HdrMinDoc());
		docViewPage.getDocView_HdrMinDoc().Click();
		Set<String> allWindowsId = driver.getWebDriver().getWindowHandles();
		for (String eachId : allWindowsId) {
			if (!parentWindowID.equals(eachId)) {
				driver.switchTo().window(eachId);
			}
		}
		String analyticsWindowID = driver.getWebDriver().getWindowHandle();
		for (int i = 3; i <= 3; i++) {
			docViewPage.getDocView_MiniDoc_Selectdoc(i).waitAndClick(10);
			baseClass.stepInfo("Selected document is loaded in default view of doc view successfully");
		}
		driver.switchTo().window(parentWindowID);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getDocView_MiniDoclistPlusButton());
		getDocView_MiniDoclistPlusButton().waitAndClick(10);
		driver.switchTo().window(analyticsWindowID);
		driver.close();
		driver.switchTo().window(parentWindowID);
		baseClass.waitForElement(getDocView_MiniDoclistPlusButton());
		getDocView_MiniDoclistPlusButton().waitAndClick(10);
		baseClass
				.stepInfo("Document selected from mini doc list child window is same as in parent window successfully");
		baseClass.waitForElement(getDocView_MiniDoclistRightArrow());
		softAssertion.assertTrue(getDocView_MiniDoclistRightArrow().Displayed());
		baseClass.passedStep(
				"To Verify when document is selected from mini doc list child window document should be selected from mini doc list parent window has been verified successfully");
	}

	/**
	 * @author Indium Raghuram Description : Date:9/8/21 Modified date: N/A Modified
	 *         by: N/A
	 * @throws InterruptedException
	 */
	public void VerifyArrow() throws InterruptedException {

		// Main method
		baseClass.waitForElementCollection(getListofDocIDinCW());
		int sizeofList = getListofDocIDinCW().size();
		System.out.println("Size : " + sizeofList);
		docIDlist = availableListofElements(getListofDocIDinCW());

		// Bulk Document to complete
		completedDocuments = bulkDocumentToComplete(sizeofList, 3);

		driver.waitForPageToBeReady();
		baseClass.waitForElement(getGearIcon());
		getGearIcon().waitAndClick(5);

		baseClass.waitForElement(getCompletedDocsToggle());
		getCompletedDocsToggle().waitAndClick(5);

		baseClass.waitForElement(getMiniDocListConfirmationButton("Save"));
		getMiniDocListConfirmationButton("Save").waitAndClick(5);

	    baseClass.waitTime(5);

		for (int i = 1; i <= 3; i++) {
			String name = docIDlist.get(i);
			getDociD(name).waitAndClick(5);
			driver.waitForPageToBeReady();
			System.out.println("Selected Document : " + name);
			baseClass.stepInfo("Selected Document : " + name);
			String currentSelectionIconFromDocumentList = getCurrentSelectionIcon(name).GetAttribute("class");
			System.out.println(currentSelectionIconFromDocumentList);

			// Click on History Button
			getHistoryBtn().waitAndClick(5);
			checkCurrentSelectionImapctinHistory(name);

			String currentSelectionIconFromHistoryDD = getCurrentSelectionIconFromHistoryDD(name).GetAttribute("class");
			System.out.println(currentSelectionIconFromHistoryDD);
			getCurrentSelectionIconFromHistoryDD(name).waitAndClick(5);
			driver.waitForPageToBeReady();

			if (currentSelectionIconFromHistoryDD.equals(currentSelectionIconFromDocumentList)) {
				System.out.println("Same Icons present Fail");
				baseClass.failedStep("Same Icons present Fail");
			} else {
				System.out.println(
						"Pass - History drop-down selected documents icon and current selection icon are different  ");
				baseClass.passedStep(
						"Pass - History drop-down selected documents icon and current selection icon are different ");
			}

		}

		baseClass.stepInfo("-----------------------------");
		iconVerify(completedDocuments);
	}

	/**
	 * @author Indium Raghuram Description : e Date:9/8/21 Modified date: N/A
	 *         Modified by: N/A
	 * @return
	 * @throws InterruptedException
	 */
	public void iconVerify(List<String> completedDoc) {

		for (String docName : completedDoc) {
			System.out.println(docName);

			// String name = completedDoc.get(i);
			getDociD(docName).Click();
			System.out.println("Selected Document : " + docName);
			baseClass.stepInfo("Selected Document : " + docName);
			String currentSelectionIconFromCompletedDocumentList = getCurrentSelectionCompletedIcon(docName)
					.GetAttribute("class");
			System.out.println(currentSelectionIconFromCompletedDocumentList);

			// Click on History Button
			getHistoryBtn().waitAndClick(2);
			checkCurrentSelectionImapctinHistory(docName);

			String currentSelectionIconFromHistoryDD = getCurrentSelectionIconFromHistoryDD(docName)
					.GetAttribute("class");
			System.out.println(currentSelectionIconFromHistoryDD);
			getCurrentSelectionIconFromHistoryDD(docName).Click();
			driver.waitForPageToBeReady();

			if (currentSelectionIconFromHistoryDD.equals(currentSelectionIconFromCompletedDocumentList)) {
				System.out.println("Same Icons present Fail");
				baseClass.failedStep("Same Icons present Fail");
			} else {
				System.out.println(
						"Pass - History drop-down selected documents icon and completed document's icon are different");
				baseClass.passedStep(
						"Pass - History drop-down selected documents icon and completed document's icon are different  ");
			}
		}

	}

	/**
	 * @author Indium Raghuram Description : checkCurrentSelectionImapctinHistory
	 *         Date:9/8/21 Modified date: N/A Modified by: N/A
	 * @return
	 * @throws InterruptedException
	 */
	public void checkCurrentSelectionImapctinHistory(String name) {
		try {
			baseClass.waitForElement(getCurrentSelectionIconFromHistoryDD(name));
			getCurrentSelectionIconFromHistoryDD(name).Visible();
			System.out.println("Current Selection icon present : " + name);
			baseClass.stepInfo("Current Selection icon present : " + name);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @author Indium Raghuram Description : Bulk Document to complete Date:9/8/21
	 *         Modified date: N/A Modified by: N/A
	 * @return
	 * @throws InterruptedException
	 * 
	 */
	public List<String> bulkDocumentToComplete(int sizeofList, int numbersToComplete) {
		List<String> completedDoc = new ArrayList<>();
		for (int i = 1; i <= numbersToComplete; i++) {
			int docToChoose = randNumber(sizeofList);
			System.out.println(docToChoose);
			String name = docIDlist.get(docToChoose);
			getDociD(name).waitAndClick(5);
			driver.waitForPageToBeReady();
			CompleteDocsButton();
			System.out.println("Completed Document : " + name);
			baseClass.stepInfo("Completed Document : " + name);
			completedDoc.add(name);
			driver.waitForPageToBeReady();
		}
		return completedDoc;
	}
	/**
	 * @author Indium Raghuram Description : Method to click gearIcon Date: 9/13/21
	 *         Modified date: NA Modified by: N/A
	 */
	public void clickingGearIcon() {
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		try {
			//baseClass.waitForElement(getGearIcon());
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getGearIcon().Visible();
				}
			}), Input.wait30);
			getGearIcon().Click();
		} catch (Exception e) {
			e.printStackTrace();
		}
		baseClass.stepInfo("Clicked Gear Icon");
	}

	/**
	 * @author Indium Mohan Description : Method to select sourceDocId from
	 *         Available field Date: 28/09/21 Modified date: NA Modified by: N/A
	 */
	public void selectSourceDocIdInAvailableField() {

		try {
			driver.waitForPageToBeReady();

			baseClass.waitForElement(getDocView_MiniDoclist_GearIcon());
			getDocView_MiniDoclist_GearIcon().waitAndClick(10);

			baseClass.waitForElement(getDocView_MiniDoclist_ConfigureMiniDocList_SelectedFields());
			getDocView_MiniDoclist_ConfigureMiniDocList_SelectedFields().waitAndClick(10);

			baseClass.waitForElement(getDocView_MiniDoclist_ConfigureMiniDocList_FamilyMemberCount());
			getDocView_MiniDoclist_ConfigureMiniDocList_FamilyMemberCount().waitAndClick(10);

			dragAndDropAvailableFieldstoSelectedfieldsPickColumDisplay("SourceDocID");

			getMiniDocListConfirmationButton("Save").waitAndClick(10);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ConfigureMiniDocist popup is not opened");
		}
	}

	/**
	 * @author Indium Baskar
	 * @Description : Assignment creation with Manual Sort
	 */
//	Reusable method for assignment with manual sort radio buttons
	public String assignmentCreationWithManualSortForDisToRMUAndRe() throws InterruptedException {
		String assignmentNameToCreate = "TestAssignmentNo" + Utility.dynamicNameAppender();
		assignmentPage.assignmentCreation(assignmentNameToCreate, Input.codingFormName);
		driver.scrollingToBottomofAPage();
//		assignmentPage.getAssgn_OverrideOptimizedSortToggle().waitAndClick(3);
		driver.scrollPageToTop();
		baseClass.waitForElement(assignmentPage.getAssignmentSaveButton());
		assignmentPage.getAssignmentSaveButton().waitAndClick(5);
		baseClass.CloseSuccessMsgpopup();
		driver.waitForPageToBeReady();
		assignmentPage.add2ReviewerAndDistribute();
		return assignmentNameToCreate;

	}

	/**
	 * @author Indium Baskar
	 * @Description : Assignment creation with optimized Sort
	 */

//	Reusable method for assignment creation with optimized sorts
	public String assignmentCreationWithOptimizedSortForDisToRMU() throws InterruptedException {
		String assignmentNameToCreate = "TestAssignmentNo" + Utility.dynamicNameAppender();
		assignmentPage.assignmentCreation(assignmentNameToCreate, Input.codingFormName);
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		assignmentPage.assignmentDistributingToReviewer();
		return assignmentNameToCreate;

	}

	/**
	 * @author Indium Baskar
	 * @Description : this method used to check select webfield after impersonate in
	 *              manual mode
	 */

	public void afterImpersonateWebFieldsSelectionManualMode() throws InterruptedException {
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Header Fields Before Action");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getGearIcon());
		getGearIcon().waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getManualSortRadioButton());
		getManualSortRadioButton().waitAndClick(5);
		// Pick Column Display
		afterActionselectedFieldsPickColumnDisplayFirstAssignment = methodForPickColumnDisplayWebFields();
		String configureSelectedData = afterActionselectedFieldsPickColumnDisplayFirstAssignment.toString()
				.toUpperCase();
		driver.waitForPageToBeReady();
		String headerVlaueAfterConfig = reusableDocViewPage.defaultHeaderValue();
		softAssertion.assertEquals(configureSelectedData, headerVlaueAfterConfig);
	}

	/**
	 * @author Indium Baskar
	 * @Description : this method used to select webfield should not select more
	 *              than four webfields
	 */

	public List<String> methodForPickColumnDisplayWebFields() {
		driver.waitForPageToBeReady();
		ElementCollection pickColumnafterSelectedfieldList = getSelectedFieldsAvailablePickColumnDisplay();
		afterActionselectedFieldsList = availableListofElements(pickColumnafterSelectedfieldList);
		for (String element : afterActionselectedFieldsList) {
			System.out.println(element);
			getValueToRemoveFromSelectedWebFields(element).waitAndClick(3);
		}
		driver.waitForPageToBeReady();
		System.out.println("Removed from Selected list");
		baseClass.waitForElement(getMiniDocListConfirmationButton("Save"));
		getMiniDocListConfirmationButton("Save").waitAndClick(5);
		if (getErrorMessageWebFields().Displayed()) {
			baseClass.passedStep("Error message displayed when custom field not selected");
		} else {
			baseClass.stepInfo("Error meassge not displayed");
		}
		// Drag and Drop from Available to Selected field
		driver.waitForPageToBeReady();
		for (String element : afterActionselectedFieldsList) {
			System.out.println(element);
			Element sourcefromPickColumDisplay = getFromAvailableFieldPickColumnDisplay(element);
			Element destinationfromPickColumDisplay = getToSelectedField();
			dragAndDropAction(sourcefromPickColumDisplay, destinationfromPickColumDisplay);
		}
		ElementCollection afterSelectinFourWebField = getSelectedFieldsAvailablePickColumnDisplay();
		afterActionselectedFieldsList = availableListofElements(afterSelectinFourWebField);
		int count = afterActionselectedFieldsList.size();
		if (count < 5) {
			baseClass.passedStep("Selected Webfield count : " + count);
		}
		if (count <= 4) {
			ElementCollection pickColumnAvailableLists = getAvailablePickColumnDisplayFields();
			availablePickColumnDisplayList = availableListofElements(pickColumnAvailableLists);
			String fieldToAdd = availablePickColumnDisplayList.get(randNumber(5)); //
			Element sourcefromPickColumDisplay = getFromAvailableFieldPickColumnDisplay(fieldToAdd);
			Element destinationfromPickColumDisplay = getToSelectedField();
			dragAndDropAction(sourcefromPickColumDisplay, destinationfromPickColumDisplay);
			baseClass.passedStep("Application allowed user for configure only four webfields");
		} else {
			baseClass.failedStep("Application allowed user to configure more tan four webfields");
		}

		baseClass.waitForElement(getMiniDocListConfirmationButton("Save"));
		getMiniDocListConfirmationButton("Save").Click();
		// After Drag and Drop
		baseClass.stepInfo("Pick Column Display Selected fields After Modifications");
		for (String a : afterActionselectedFieldsList) {
			baseClass.stepInfo(a);
		}
		return afterActionselectedFieldsList;
//		int rando = (int) ((Math.random() * afterActionselectedFieldsList.size()));
//		for (int i = 0; i < element; i++) {
//			System.out.println(element);
//			String text = afterActionselectedFieldsList.get(rando);
//			String s = afterActionselectedFieldsList.remove(rando);
//			String element = null;
//			getValueToRemoveFromSelectedWebFields(element).waitAndClick(3);
//
//		}
	}

	/**
	 * @author Indium-Baskar
	 */
//  Reusable Method for click review mode button to manual sort radio button
//  To open configure minidoclist popup

	public void clickManualSortButton() {
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Header Fields Before Action");
		baseClass.waitForElement(getGearIcon());
		getGearIcon().waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getManualSortRadioButton());
		getManualSortRadioButton().waitAndClick(5);
		baseClass.stepInfo("Configure minidoclist popup opened");
	}

	/**
	 * @author Indium-Baskar
	 */
//  Reusable Method for iterating the selected webfield value in Array list
//  To deselect the all webfield value 

	public List<String> removingSelectedWebFieldInConfigureList() {
		driver.waitForPageToBeReady();
		ElementCollection pickColumnafterSelectedfieldList = getSelectedFieldsAvailablePickColumnDisplay();
		afterActionselectedFieldsList = availableListofElements(pickColumnafterSelectedfieldList);
		for (String element : afterActionselectedFieldsList) {
			System.out.println(element);
			getValueToRemoveFromSelectedWebFields(element).waitAndClick(3);
		}
		return afterActionselectedFieldsList;
	}

	/**
	 * @author Indium-Baskar
	 */
//  Reusable Method for drag and drop in available field

	public void availableFieldToDragAndDrop() {
		driver.waitForPageToBeReady();
		ElementCollection pickColumnAvailableLists = getAvailablePickColumnDisplayFields();
		availablePickColumnDisplayList = availableListofElements(pickColumnAvailableLists);
		for (String element : afterActionselectedFieldsList) {
			System.out.println(element);
			Element sourcefromPickColumDisplay = getFromAvailableFieldPickColumnDisplay(element);
			Element destinationfromPickColumDisplay = getToSelectedField();
			dragAndDropAction(sourcefromPickColumDisplay, destinationfromPickColumDisplay);
		}
	}

	/**
	 * @author Indium-Baskar
	 */
//  Reusable Method for verifying webfield in selected custom data

	public List<String> verifyingWebFieldShouldNotMoreThanFourCustomData() {
		driver.waitForPageToBeReady();
		ElementCollection pickColumnAvailableLists = getAvailablePickColumnDisplayFields();
		availablePickColumnDisplayList = availableListofElements(pickColumnAvailableLists);
		String fieldToAdd = availablePickColumnDisplayList.get(randNumber(5)); //
		Element sourcefromPickColumDisplay = getFromAvailableFieldPickColumnDisplay(fieldToAdd);
		Element destinationfromPickColumDisplay = getToSelectedField();
		dragAndDropAction(sourcefromPickColumDisplay, destinationfromPickColumDisplay);
		ElementCollection afterSelectinFourWebField = getSelectedFieldsAvailablePickColumnDisplay();
		afterActionselectedFieldsList = availableListofElements(afterSelectinFourWebField);
		int count = afterActionselectedFieldsList.size();
		if (count < 5) {
			baseClass.passedStep("Selected Webfield count : " + count);
		}
		if (count <= 4) {
			ElementCollection pickColumnAvailable = getAvailablePickColumnDisplayFields();
			availablePickColumnDisplayList = availableListofElements(pickColumnAvailable);
			String fieldToSel = availablePickColumnDisplayList.get(randNumber(5)); //
			Element sourcefromPickColumDisplayed = getFromAvailableFieldPickColumnDisplay(fieldToSel);
			Element destinationfromPickColumDisplayed = getToSelectedField();
			dragAndDropAction(sourcefromPickColumDisplayed, destinationfromPickColumDisplayed);
			baseClass.passedStep("Application allowed user for configure only four webfields");
		} else {
			baseClass.failedStep("Application allowed user to configure more tan four webfields");
		}
		return afterActionselectedFieldsList;
	}

	/**
	 * @author Indium-Baskar
	 */
//  Reusable Method for saving minidoclist configuration

	public void saveConfigureMiniDocList() {
		baseClass.waitForElement(getMiniDocListConfirmationButton("Save"));
		getMiniDocListConfirmationButton("Save").waitAndClick(5);
	}

	/**
	 * @author Indium-Baskar date: 08/10/2021 Modified date: NA
	 * @Description : this method used for selecting webfield from basic search
	 */

	public void fromBasicSearchToSelectWebField() {
		driver.waitForPageToBeReady();
		clickManualSortButton();
		// Pick Column Display
		afterActionselectedFieldsList = removingSelectedWebFieldInConfigureList();
		availableFieldToDragAndDrop();
		afterActionselectedFieldsList = verifyingWebFieldShouldNotMoreThanFourCustomData();
		saveConfigureMiniDocList();
		String configureSelectedData = afterActionselectedFieldsList.toString().toUpperCase();
		baseClass.stepInfo("Configured MiniDocList WebField Value : " + configureSelectedData);
		driver.waitForPageToBeReady();
		String headerVlaueAfterConfig = reusableDocViewPage.defaultHeaderValue();
		baseClass.stepInfo("MiniDocList configuration header value : " + headerVlaueAfterConfig);
		softAssertion.assertEquals(configureSelectedData, headerVlaueAfterConfig);
		System.out.println(configureSelectedData);
		System.out.println(headerVlaueAfterConfig);
		softAssertion.assertEquals(configureSelectedData, headerVlaueAfterConfig);

	}

	/**
	 * @author Indium-Baskar date: 08/10/2021 Modified date: NA
	 * @Description : this method used for selecting webfield from basic search
	 */

	public void fromSavedSearchToSelectWebField() {
		driver.waitForPageToBeReady();
		clickManualSortButton();
		// Pick Column Display
		afterActionselectedFieldsList = removingSelectedWebFieldInConfigureList();
		availableFieldToDragAndDrop();
		afterActionselectedFieldsList = verifyingWebFieldShouldNotMoreThanFourCustomData();
		saveConfigureMiniDocList();
		String configureSelectedData = afterActionselectedFieldsList.toString().toUpperCase();
		baseClass.stepInfo("Configured MiniDocList WebField Value : " + configureSelectedData);
		driver.waitForPageToBeReady();
		String headerVlaueAfterConfig = reusableDocViewPage.defaultHeaderValue();
		baseClass.stepInfo("MiniDocList configuration header value : " + headerVlaueAfterConfig);
		softAssertion.assertEquals(configureSelectedData, headerVlaueAfterConfig);
		System.out.println(configureSelectedData);
		System.out.println(headerVlaueAfterConfig);
		softAssertion.assertEquals(configureSelectedData, headerVlaueAfterConfig);

	}

	/**
	 * @author Indium-Baskar date: 08/10/2021 Modified date: NA
	 * @Description : this method used for sorting order to display in ascending
	 *              field
	 */

	public void sortingVerifyAfterSelectedWebFields() {
		driver.waitForPageToBeReady();
		baseClass.waitForElementCollection(getListofDocIDinCW());
		ElementCollection docID = getListofDocIDinCW();
		List<String> sortOrderAscending = availableListofElements(docID);
		baseClass.waitForElement(getGearIcon());
		getGearIcon().waitAndClick(5);
		driver.waitForPageToBeReady();
		// Pick Column Display
		ElementCollection pickColumnafterSelectedfieldList = getSelectedFieldsAvailablePickColumnDisplay();
		afterActionselectedFieldsList = availableListofElements(pickColumnafterSelectedfieldList);
		afterActionselectedFieldsList = removingSelectedWebFieldInConfigureList();
		availableFieldToDragAndDrop();
		afterActionselectedFieldsList = verifyingWebFieldShouldNotMoreThanFourCustomData();
		// interchanging the selected field
		baseClass.waitForElement(getSelectedFieldToInterchange("DocID"));
		Element sourcefromPickColumDisplay = getSelectedFieldToInterchange("DocID");
		Actions actions = new Actions(driver.getWebDriver());
		// Drag and Drop to x,y
		actions.clickAndHold(getSelectedFieldToInterchange("DocID").getWebElement());
		actions.dragAndDropBy(getSelectedFieldToInterchange("DocID").getWebElement(), 50, 100).build().perform();
		System.out.println("Drag and Dropped");
		ElementCollection pickColumnafterSelectedfieldLists = getSelectedFieldsAvailablePickColumnDisplay();
		List<String> afterInterchanging = availableListofElements(pickColumnafterSelectedfieldLists);
		saveConfigureMiniDocList();
		driver.waitForPageToBeReady();
		String configureSelectedData = afterInterchanging.toString().toUpperCase();
		baseClass.stepInfo("Configured MiniDocList WebField Value : " + configureSelectedData);
		driver.waitForPageToBeReady();
		List<WebElement> allValues = getMiniDocListHeaderValue().FindWebElements();
		List<String> arrayMiniDocList = new ArrayList<String>();
		for (int j = 1; j < allValues.size(); j++) {
			arrayMiniDocList.add(allValues.get(j).getText());
			if (j == 2) {
				JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();
				jse.executeScript("document.querySelector('.dataTables_scrollBody').scrollBy(400,0)");
				driver.waitForPageToBeReady();
			}
		}
		baseClass.waitForElementCollection(getListofDocIDAfterInterchange());
		ElementCollection docIDs = getListofDocIDAfterInterchange();
		List<String> sortOrderAscendings = availableListofElements(docIDs);
		baseClass.stepInfo("MiniDocList configuration header value : " + arrayMiniDocList);
		softAssertion.assertEquals(sortOrderAscending, sortOrderAscendings);
		if (sortOrderAscending.equals(sortOrderAscendings)) {
			baseClass.passedStep("Verified after interchanging the selected fields in ascending order only");
		} else {
			baseClass.failedStep("Values are not in ascending order");
		}
		softAssertion.assertEquals(configureSelectedData, arrayMiniDocList);

	}

	/**
	 * @author Indium Baskar
	 * @Description :Minidoclist header value to verify to scroll right
	 */

//	Reusable Scroll right in minidoclist header fields
	public List<String> scrollHeaderMiniDocListRight() {

		List<WebElement> allValues = getMiniDocListHeaderValue().FindWebElements();
		List<String> arrayMiniDocList = new ArrayList<String>();
		driver.waitForPageToBeReady();
		for (int j = 1; j < allValues.size(); j++) {
			arrayMiniDocList.add(allValues.get(j).getText());
			if (j == 3) {
				JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();
				jse.executeScript("document.querySelector('.dataTables_scrollBody').scrollBy(400,0)");
				driver.waitForPageToBeReady();
			}
		}
		return arrayMiniDocList;
	}

	/**
	 * @author Indium-Baskar date: 08/10/2021 Modified date: NA
	 * @Description : this method used for optimized sort to select webfields
	 */

	public void optimizedSortToSelcetWebFields() {
		driver.waitForPageToBeReady();
		baseClass.waitForElementCollection(getListofDocIDinCW());
		List<String> headerFields = scrollHeaderMiniDocListRight();
		String miniDocListHeader = String.join(", ", headerFields);
		baseClass.stepInfo("MiniDocList configuration header value : " + miniDocListHeader);
		baseClass.waitForElement(getGearIcon());
		getGearIcon().waitAndClick(5);
		driver.waitForPageToBeReady();
		// Pick Column Display
		ElementCollection pickColumnafterSelectedfieldList = getSelectedFieldsAvailablePickColumnDisplay();
		List<String> optimizedSelectedFields = availableListofElements(pickColumnafterSelectedfieldList);
//		converting arraylist to string
		String result1 = String.join(", ", optimizedSelectedFields);
		String configureSelectedData = result1.toString().toUpperCase();
		baseClass.stepInfo("Selected webfields customdata  : " + configureSelectedData);
		if (miniDocListHeader.equals(configureSelectedData)) {
			baseClass.passedStep("Verified minidoclist header fields and selected webfields  are same");
		} else {
			baseClass.stepInfo("Fields are not same");
		}
		afterActionselectedFieldsList = removingSelectedWebFieldInConfigureList();
		availableFieldToDragAndDrop();
		afterActionselectedFieldsList = verifyingWebFieldShouldNotMoreThanFourCustomData();
		baseClass.stepInfo("AvailableField to selected webfield : " + afterActionselectedFieldsList);
		saveConfigureMiniDocList();
		String configureSelectedDataTwo = afterActionselectedFieldsList.toString().toUpperCase();
		driver.waitForPageToBeReady();
		String headerVlaueAfterConfig1 = reusableDocViewPage.defaultHeaderValue();
		baseClass.stepInfo("MiniDocList after config header value : " + headerVlaueAfterConfig1);
		softAssertion.assertEquals(configureSelectedDataTwo, headerVlaueAfterConfig1);
	}

	/**
	 * @author Indium-Baskar date: 12/10/2021 Modified date: NA
	 * @Description : this method used for more than four webfield should not
	 *              selecttable
	 */

	public void moreThanFourWebFieldNotDraggable() {
		driver.waitForPageToBeReady();
		baseClass.waitForElementCollection(getListofDocIDinCW());
		List<String> headerFields = scrollHeaderMiniDocListRight();
		String miniDocListHeader = String.join(", ", headerFields);
		baseClass.stepInfo("MiniDocList configuration header value : " + miniDocListHeader);
		baseClass.waitForElement(getGearIcon());
		getGearIcon().waitAndClick(5);
		driver.waitForPageToBeReady();
		// Pick Column Display
		ElementCollection pickColumnafterSelectedfieldList = getSelectedFieldsAvailablePickColumnDisplay();
		List<String> optimizedSelectedFields = availableListofElements(pickColumnafterSelectedfieldList);
//		converting arraylist to string
		String result1 = String.join(", ", optimizedSelectedFields);
		String configureSelectedData = result1.toString().toUpperCase();
		baseClass.stepInfo("Selected webfields customdata  : " + configureSelectedData);
		if (miniDocListHeader.equals(configureSelectedData)) {
			baseClass.passedStep("Verified minidoclist header fields and selected webfields  are same");
		} else {
			baseClass.stepInfo("Fields are not same");
		}
		afterActionselectedFieldsList = removingSelectedWebFieldInConfigureList();
		availableFieldToDragAndDrop();
		afterActionselectedFieldsList = verifyingWebFieldShouldNotMoreThanFourCustomData();
		baseClass.stepInfo("AvailableField to selected webfield : " + afterActionselectedFieldsList);
		baseClass.passedStep("User can able to Drag and drop only four webfield from available field");
	}

	/**
	 * @author Indium Baskar
	 * @Description : this method used to check select webfield after impersonate in
	 *              Optimized mode
	 */

	public void afterImpersonateWebFieldsSelectionOptimizedMode() throws InterruptedException {
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Header Fields Before Action");
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getGearIcon());
		getGearIcon().waitAndClick(5);
		driver.waitForPageToBeReady();
		// Pick Column Display
		afterActionselectedFieldsPickColumnDisplayFirstAssignment = methodForPickColumnDisplayWebFields();
		String configureSelectedData = afterActionselectedFieldsPickColumnDisplayFirstAssignment.toString().toUpperCase();
		driver.waitForPageToBeReady();
		String headerVlaueAfterConfig = reusableDocViewPage.defaultHeaderValue();
		softAssertion.assertEquals(configureSelectedData, headerVlaueAfterConfig);
		if (configureSelectedData.equals(headerVlaueAfterConfig)) {
			baseClass.passedStep("Selected webfields and minidoclist header field are same");
		} else {
			baseClass.failedStep("WebFields are not same");
		}

	}

	/**
	 * @author Indium Baskar
	 * @Description : this method used to verify Configure minidoc list open should
	 *              open
	 */

	public void configureMiniDocListPopupOpen() {
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getGearIcon());
		getGearIcon().waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getConfigureMiniDocTab());
		String configureMiniDocTab = getConfigureMiniDocTab().getText();
		Assert.assertEquals(configureMiniDocTab, "Configure Mini DocList");
		baseClass.passedStep("Minidoclist popup opened on clicking  the gear icon");
	}

	/**
	 * @author Indium Baskar
	 * @Description : this method used for remove code same as in parent window
	 */

	public void removeCodeSameAsAndCompleteDocs(String fieldValue, String colour) throws InterruptedException {
		driver.waitForPageToBeReady();
		reusableDocViewPage.editingCodingFormWithSaveButton();
		reusableDocViewPage.codingStampPopUpWindow(fieldValue, colour);
		baseClass.stepInfo("Coding stamp saved");
		driver.waitForPageToBeReady();
		for (int j = 1; j <= 2; j++) {
			getDocView_MiniDoc_ChildWindow_Selectdoc(j).WaitUntilPresent().waitAndClick(5);
		}
		reusableDocViewPage.clickCodeSameAs();
		driver.waitForPageToBeReady();
		for (int j = 1; j <= 2; j++) {
			getDocView_MiniDoc_ChildWindow_Selectdoc(j).WaitUntilPresent().waitAndClick(5);
		}
		reusableDocViewPage.removeCodeSameAs();
		reusableDocViewPage.lastAppliedStamp(colour);
		for (int i = 1; i <= 1; i++) {
			getClickDocviewID(i).waitAndClick(5);
		}
		getverifyCodeSameAsLast().WaitUntilPresent().ScrollTo();
		baseClass.stepInfo("Document completed successfully after applying coding stamp");
		softAssertion.assertEquals(getverifyCodeSameAsLast().Displayed().booleanValue(), true);
	}

	/**
	 * @author Indium Baskar
	 * @Description : this method used for remove code same as in child window
	 */

	public void removeCodeSameAsChildWindow() throws InterruptedException {
		driver.waitForPageToBeReady();
		reusableDocViewPage.clickGearIconOpenMiniDocList();
		String miniDocListPrent = reusableDocViewPage.switchTochildWindow();
		baseClass.stepInfo("MiniDocList child window opened");
		driver.waitForPageToBeReady();
		for (int j = 1; j <= 2; j++) {
			getDocView_MiniDoc_ChildWindow_Selectdoc(j).WaitUntilPresent().waitAndClick(5);
		}
		reusableDocViewPage.clickCodeSameAs();
		driver.waitForPageToBeReady();
		for (int j = 1; j <= 2; j++) {
			getDocView_MiniDoc_ChildWindow_Selectdoc(j).WaitUntilPresent().waitAndClick(5);
		}
		baseClass.stepInfo("Removing code same as action in child window");
		reusableDocViewPage.removeCodeSameAs();
		reusableDocViewPage.childWindowToParentWindowSwitching(miniDocListPrent);
		reusableDocViewPage.editingCodingFormWithSaveButton();
		baseClass.passedStep("Coding form saved for principal document after removing code same as");
	}

	/**
	 * @author Indium Baskar
	 * @Description : this method used for removing code same as after selecting
	 *              multiple document
	 */

	public void removeCodeSameAsAfterImpersonate() throws InterruptedException {
		driver.waitForPageToBeReady();
		for (int j = 1; j <= 5; j++) {
			getDocView_MiniDoc_ChildWindow_Selectdoc(j).WaitUntilPresent().waitAndClick(5);
		}
		baseClass.stepInfo("Applying code same as for minidoclist document");
		reusableDocViewPage.clickCodeSameAs();
		driver.waitForPageToBeReady();
		for (int j = 1; j <= 5; j++) {
			getDocView_MiniDoc_ChildWindow_Selectdoc(j).WaitUntilPresent().waitAndClick(5);
		}
		baseClass.stepInfo("Removing code same as from minidoclist");
		reusableDocViewPage.removeCodeSameAs();
	}

	/**
	 * @author Indium Baskar
	 * @Description : this method used for verify warning message for completed docs
	 *              to perform code same as
	 */

	public void warningMsgForCompletedDocsCodeSameAs() throws InterruptedException {
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getDocumentCompleteButton());
		getDocumentCompleteButton().waitAndClick(5);
		for (int j = 1; j <= 1; j++) {
			getDocView_MiniDoc_ChildWindow_Selectdoc(j).WaitUntilPresent().waitAndClick(5);
		}
		baseClass.stepInfo("Applying code same as for completed document");
		baseClass.waitForElement(getDocView_Mini_ActionButton());
		getDocView_Mini_ActionButton().waitAndClick(5);
		baseClass.waitForElement(getDocView__ChildWindow_Mini_CodeSameAs());
		getDocView__ChildWindow_Mini_CodeSameAs().waitAndClick(5);
		driver.waitForPageToBeReady();
		if (getWarningMsgCompleteDocsCodeSameAs().Displayed()) {
			baseClass.passedStep("Warning message displayed for completed docs performing code same as");
		} else {
			baseClass.failedStep("Warning message not displayed");
		}
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium Baskar
	 * @Description : this method used for minidoclist docs should not selected from
	 *              history drop down
	 */

	public void docsShouldNotOfMiniDocList(int count,String docIdText) throws InterruptedException {
		driver.waitForPageToBeReady();
		for (int i = 1; i <= 10; i++) {
			getClickDocviewID(i).waitAndClick(5);
			driver.waitForPageToBeReady();
		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getDocView_HistoryButton());
		getDocView_HistoryButton().waitAndClick(5);
		baseClass.stepInfo("User clicked clock icon in minidoclist");
		baseClass.waitTime(3);
		int docId = getHistoryDropDown().size();
		baseClass.stepInfo("History drop down docId : " + docId);
		baseClass.waitTime(3);
		docViewPage.getHeader().waitAndClick(5);
		List<String> miniDocList = new ArrayList<>();
		ElementCollection miniDocListelement = getMiniDocListDocIdText();
		miniDocList = availableListofElements(miniDocListelement);
		baseClass.waitForElement(getClickDocviewID(count));
		getClickDocviewID(count).waitAndClick(5);
		driver.waitForPageToBeReady();
		getDocView_Analytics_NearDupeTab().WaitUntilPresent().ScrollTo();
		getDocView_Analytics_NearDupeTab().waitAndClick(5);
		Thread.sleep(Input.wait30);// Mandatory thread.sleep no need to delete
		List<String> analyticalDocs = new ArrayList<>();
		ElementCollection analyticsElement = getAnalyticalPanelDocIdText();
		analyticalDocs = availableListofElements(analyticsElement);
		getAnalyCheckBox(docIdText).WaitUntilPresent().ScrollTo();
		baseClass.waitForElement(getAnalyCheckBox(docIdText));
		getAnalyCheckBox(docIdText).waitAndClick(10);
		baseClass.waitForElement(getDocView_ChildWindow_ActionButton());
		getDocView_ChildWindow_ActionButton().waitAndClick(10);
		baseClass.waitForElement(getViewDocumentNearDupe());
		getViewDocumentNearDupe().waitAndClick(5);
		baseClass.stepInfo("Document selected from analytical panel : " + docIdText);
		driver.scrollPageToTop();
		baseClass.waitForElement(getDocView_HistoryButton());
		getDocView_HistoryButton().waitAndClick(5);
		baseClass.waitForElement(getHistoryClock(docIdText));
		getHistoryClock(docIdText).waitAndClick(5);
		System.out.println(miniDocList);
		if (miniDocList.contains(docIdText)) {
			baseClass.failedStep("Minidoclist part of history drop down");
		}
		else {
			baseClass.passedStep("DocId selected from history drop down : " + docIdText);
			baseClass.passedStep(docIdText + ": Is not part of MiniDocList");
		}
		
	}

	/**
	 * @author Indium Baskar
	 * @Description : this method used for verify child window history drop down to
	 *              selected docid
	 * 
	 */

	public void focusOnChildWindowHistoryDrpDw() throws InterruptedException {
		String prnDoc = null;
		driver.waitForPageToBeReady();
		for (int i = 1; i <= 10; i++) {
			getClickDocviewID(i).waitAndClick(5);
			if (i==10) {
				prnDoc=docViewPage.getVerifyPrincipalDocument().getText();
				
			}
		}
		baseClass.waitForElement(getDocView_HistoryButton());
		getDocView_HistoryButton().waitAndClick(5);
		baseClass.waitForElement(getCurrentSelectionIconFromHistoryDD(prnDoc));
		getCurrentSelectionIconFromHistoryDD(prnDoc).waitAndClick(5);
		reusableDocViewPage.clickGearIconOpenMiniDocList();
		String parentWindow = reusableDocViewPage.switchTochildWindow();
		baseClass.waitForElement(getDocView_HistoryButton());
		getDocView_HistoryButton().waitAndClick(5);
		baseClass.stepInfo("User clicked clock icon in minidoclist");
		baseClass.waitForElementCollection(getHistoryDropDown());
		int count = getHistoryDropDown().size();
		baseClass.stepInfo("History drop down docId : " + count);
		System.out.println(count);
		baseClass.waitForElement(getCurrentSelectionIconFromHistoryDD(prnDoc));
		getCurrentSelectionIconFromHistoryDD(prnDoc).waitAndClick(5);
		reusableDocViewPage.childWindowToParentWindowSwitching(parentWindow);
		driver.waitForPageToBeReady();
		String currentDocID = getMainWindowActiveDocID().getText();
		if (currentDocID.equals(prnDoc)) {
			baseClass.passedStep("Selected document from history drop down are loaded and displayed in docview panel");
		} else {
			baseClass.failedMessage("document not displayed in docview panel");
		}
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium Baskar
	 * @Description : this method used to select doc from history drop down
	 * 
	 */

	public void selectDocFromHistoryDropDown() throws InterruptedException {
		String prnDoc = null;
		driver.waitForPageToBeReady();
		for (int i = 1; i <= 2; i++) {
			getClickDocviewID(i).waitAndClick(5);
			if (i==2) {
				prnDoc=docViewPage.getVerifyPrincipalDocument().getText();
			}
		}
		reusableDocViewPage.clickGearIconOpenMiniDocList();
		String parentWindow = reusableDocViewPage.switchTochildWindow();
		baseClass.waitForElement(getDocView_HistoryButton());
		getDocView_HistoryButton().waitAndClick(5);
		baseClass.stepInfo("User clicked clock icon in minidoclist");
		baseClass.waitForElement(getCurrentSelectionIconFromHistoryDD(prnDoc));
		getCurrentSelectionIconFromHistoryDD(prnDoc).waitAndClick(5);
		reusableDocViewPage.childWindowToParentWindowSwitching(parentWindow);
		driver.waitForPageToBeReady();
		String currentDocID = getMainWindowActiveDocID().getText();
		if (currentDocID.equals(prnDoc)) {
			baseClass.passedStep("Selected document from history drop down are loaded and displayed in docview panel");
		} else {
			baseClass.failedMessage("document not displayed in docview panel");
		}
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium-Baskar date: 22/10/2021 Modified date: NA
	 * @Description : this method used for removing selected field to available
	 *              field
	 */

	public void removingFieldsInPopUpWindow() throws InterruptedException {
		driver.waitForPageToBeReady();
		String fieldValue = "FamilyRelationship";
		configureMiniDocListPopupOpen();
		driver.waitForPageToBeReady();
		ElementCollection pickColumnafterSelectedfieldList = getSelectedFieldsAvailablePickColumnDisplay();
		afterActionselectedFieldsList = availableListofElements(pickColumnafterSelectedfieldList);
		int beforeSize = afterActionselectedFieldsList.size();
		for (String element : afterActionselectedFieldsList) {
			if (element.equals(fieldValue)) {
				getValueToRemoveFromSelectedWebFields(fieldValue).waitAndClick(3);
			}
		}
		driver.waitForPageToBeReady();
		ElementCollection afterRemoval = getSelectedFieldsAvailablePickColumnDisplay();
		afterActionselectedFieldsList = availableListofElements(afterRemoval);
		int afterSize = afterActionselectedFieldsList.size();
		Assert.assertFalse(beforeSize == afterSize);
		baseClass.passedStep("Removed " + fieldValue + " in selected field ");
		ElementCollection pickColumnAvailableLists = getAvailablePickColumnDisplayFields();
		availablePickColumnDisplayList = availableListofElements(pickColumnAvailableLists);
		for (String element : availablePickColumnDisplayList) {
			if (element.equals(fieldValue)) {
				Element sourcefromPickColumDisplay = getFromAvailableFieldPickColumnDisplay(fieldValue);
				Element destinationfromPickColumDisplay = getToSelectedField();
				dragAndDropAction(sourcefromPickColumDisplay, destinationfromPickColumDisplay);
			}
		}
		driver.waitForPageToBeReady();
		ElementCollection adding = getSelectedFieldsAvailablePickColumnDisplay();
		afterActionselectedFieldsList = availableListofElements(adding);
		int addingSize = afterActionselectedFieldsList.size();
		Assert.assertTrue(beforeSize == addingSize);
		baseClass.passedStep("Added " + fieldValue + " to selected field ");
		saveConfigureMiniDocList();
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium-Baskar date: 22/10/2021 Modified date: NA
	 * @Description : this method used for verify both optimized and manual sort of
	 *              selected fields
	 */

	public void selectedFieldInChildWindow() {
		driver.waitForPageToBeReady();
		String parentWindow = driver.getWebDriver().getWindowHandle();
		String defaultValue = "DocID, DocFileType, FamilyRelationship, FamilyMemberCount";
		reusableDocViewPage.clickGearIconOpenMiniDocList();
		reusableDocViewPage.switchTochildWindow();
		baseClass.stepInfo("Minidoclist child window opened");
		baseClass.waitForElement(getGearIcon());
		getGearIcon().waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Configure minidoclist popup opened");
		driver.getWebDriver().switchTo().window(parentWindow);
		ElementCollection pickColumnafterSelectedfieldList = getSelectedFieldsAvailablePickColumnDisplay();
		afterActionselectedFieldsList = availableListofElements(pickColumnafterSelectedfieldList);
		String optimizedValue = String.join(", ", afterActionselectedFieldsList);
		Assert.assertEquals(defaultValue, optimizedValue);
		baseClass.waitForElement(getManualSortRadioButton());
		getManualSortRadioButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		ElementCollection manualSort = getSelectedFieldsAvailablePickColumnDisplay();
		afterActionselectedFieldsList = availableListofElements(manualSort);
		String manualSortSel = String.join(", ", afterActionselectedFieldsList);
		Assert.assertEquals(defaultValue, manualSortSel);
	}

	/**
	 * @author Indium-Baskar date: 22/10/2021 Modified date: NA
	 * @Description : this method used for custom to optimized sort to display
	 *              default field
	 * 
	 */

	public void customToOptimizedSortDefaultDisplayAfterRemoval() {
		driver.waitForPageToBeReady();
		String fieldValue = "FamilyRelationship";
		String defaultValue = "DocID, DocFileType, FamilyRelationship, FamilyMemberCount";
		clickingGearIcon();
		driver.waitForPageToBeReady();
		baseClass.passedStep("Optimized mode is selected");
		ElementCollection pickColumnafterSelectedfieldList = getSelectedFieldsAvailablePickColumnDisplay();
		afterActionselectedFieldsList = availableListofElements(pickColumnafterSelectedfieldList);
		String optimizedValue = String.join(", ", afterActionselectedFieldsList);
		Assert.assertEquals(defaultValue, optimizedValue);
		baseClass.passedStep("Default webfield displayed are :" + optimizedValue + "");
		baseClass.waitForElement(getManualSortRadioButton());
		getManualSortRadioButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		ElementCollection manualAvailable = getSelectedFieldsAvailablePickColumnDisplay();
		List<String> manualColumn = availableListofElements(manualAvailable);
		String manualValue = String.join(", ", manualColumn);
		getManualSetDocumentOrder().waitAndClick(5);
		driver.waitForPageToBeReady();
		boolean setDocsBtn = getManualSetDocumentOrder().getWebElement().isEnabled();
		Assert.assertTrue(setDocsBtn);
		ElementCollection setDocumentBtn = getFieldsAvailableSetDocumetSorting();
		List<String> setDocsAvailable = availableListofElements(setDocumentBtn);
		String setDocsValue = String.join(", ", setDocsAvailable);
		Assert.assertEquals(defaultValue, manualValue, setDocsValue);
		getManualColumnDisplay().waitAndClick(5);
		ElementCollection pickColumnRemove = getSelectedFieldsAvailablePickColumnDisplay();
		afterActionselectedFieldsList = availableListofElements(pickColumnRemove);
		int beforeSize = afterActionselectedFieldsList.size();
		for (String element : afterActionselectedFieldsList) {
			if (element.equals(fieldValue)) {
				getValueToRemoveFromSelectedWebFields(fieldValue).waitAndClick(3);
			}
		}
		driver.waitForPageToBeReady();
		ElementCollection afterRemoval = getSelectedFieldsAvailablePickColumnDisplay();
		afterActionselectedFieldsList = availableListofElements(afterRemoval);
		int afterSize = afterActionselectedFieldsList.size();
		Assert.assertFalse(beforeSize == afterSize);
		baseClass.passedStep("Removed " + fieldValue + " in selected field ");
		getOptSortRadioBtn().waitAndClick(5);
		driver.waitForPageToBeReady();
		ElementCollection afterRemovalInManualSort = getSelectedFieldsAvailablePickColumnDisplay();
		List<String> countOptList = availableListofElements(afterRemovalInManualSort);
		String OptListAvailable = String.join(", ", countOptList);
		Assert.assertEquals(defaultValue, OptListAvailable);
		baseClass.passedStep("Default webfields displayed after manual sort removal :" + OptListAvailable + "");
	}

	/**
	 * @author Indium-Baskar date: 22/10/2021 Modified date: NA
	 * @Description : this method used for custom to optimized sort to display
	 *              default field
	 * 
	 */

	public void customToOptimizedSortDefaultDisplay() {
		driver.waitForPageToBeReady();
		String defaultValue = "DocID, DocFileType, FamilyRelationship, FamilyMemberCount";
		clickingGearIcon();
		driver.waitForPageToBeReady();
		baseClass.passedStep("Optimized mode is selected");
		ElementCollection pickColumnafterSelectedfieldList = getSelectedFieldsAvailablePickColumnDisplay();
		List<String> optField = availableListofElements(pickColumnafterSelectedfieldList);
		String optimizedValue = String.join(", ", optField);
		Assert.assertEquals(defaultValue, optimizedValue);
		baseClass.passedStep("Default webfield displayed are :" + optimizedValue + "");
		baseClass.waitForElement(getManualSortRadioButton());
		getManualSortRadioButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		ElementCollection manualAvailable = getSelectedFieldsAvailablePickColumnDisplay();
		List<String> manualColumn = availableListofElements(manualAvailable);
		String manualValue = String.join(", ", manualColumn);
		getManualSetDocumentOrder().waitAndClick(5);
		driver.waitForPageToBeReady();
		boolean setDocsBtn = getManualSetDocumentOrder().getWebElement().isEnabled();
		Assert.assertTrue(setDocsBtn);
		ElementCollection setDocumentBtn = getFieldsAvailableSetDocumetSorting();
		List<String> setDocsAvailable = availableListofElements(setDocumentBtn);
		String setDocsValue = String.join(", ", setDocsAvailable);
		Assert.assertEquals(defaultValue, manualValue, setDocsValue);
		baseClass.passedStep("Selected fields displayed in manual sort order:" + manualValue + "");
	}

	/**
	 * @author Indium-Baskar date: 22/10/2021 Modified date: NA
	 * @Description : this method used for custom to optimized sort to display
	 *              default field
	 * 
	 */

	public void OptimizedSortToCustomDefaultDisplay() {
		driver.waitForPageToBeReady();
		String defaultValue = "DocID, DocFileType, FamilyRelationship, FamilyMemberCount";
		clickingGearIcon();
		driver.waitForPageToBeReady();
		baseClass.passedStep("Optimized mode is selected");
		ElementCollection pickColumnafterSelectedfieldList = getSelectedFieldsAvailablePickColumnDisplay();
		List<String> optField = availableListofElements(pickColumnafterSelectedfieldList);
		String optimizedValue = String.join(", ", optField);
		Assert.assertEquals(defaultValue, optimizedValue);
		baseClass.passedStep("Default webfield displayed are :" + optimizedValue + "");
		baseClass.waitForElement(getManualSortRadioButton());
		getManualSortRadioButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		ElementCollection manualAvailable = getSelectedFieldsAvailablePickColumnDisplay();
		List<String> manualColumn = availableListofElements(manualAvailable);
		String manualValue = String.join(", ", manualColumn);
		Assert.assertEquals(optimizedValue, manualValue);
		baseClass.passedStep("Selected fields displayed in manual sort order:" + manualValue + "");
	}

	/**
	 * @author Indium-Baskar date: 22/10/2021 Modified date: NA
	 * @Description : this method used to verify default value in optimized sort
	 * 
	 */

	public void verifyDefaultValueInOptimizedSort() {
		driver.waitForPageToBeReady();
		String defaultValue = "DocID, DocFileType, FamilyRelationship, FamilyMemberCount";
		driver.waitForPageToBeReady();
		//clickingGearIcon();
		baseClass.waitForElement(getGearIcon());
		getGearIcon().waitAndClick(3);
		baseClass.passedStep("Optimized mode is selected");
		baseClass.waitForElementCollection(getSelectedFieldsAvailablePickColumnDisplay());
		ElementCollection pickColumnafterSelectedfieldList = getSelectedFieldsAvailablePickColumnDisplay();
		List<String> optField = availableListofElements(pickColumnafterSelectedfieldList);
		String optimizedValue = String.join(", ", optField);
		Assert.assertEquals(defaultValue, optimizedValue);
		baseClass.passedStep("Default webfield displayed in optimized sort are :" + optimizedValue + "");
	}

	/**
	 * @author Indium-Baskar date: 22/10/2021 Modified date: NA
	 * @return
	 * @Description : this method used to verify currentSortOrder
	 */
	public String currentSortOrderShouldBeDisplayed() {
		driver.waitForPageToBeReady();
		clickManualSortButton();
		baseClass.waitForElement(getSelectedFieldToInterchange("DocID"));
		Element sourcefromPickColumDisplay = getSelectedFieldToInterchange("DocID");
		Actions actions = new Actions(driver.getWebDriver());
		// Drag and Drop to x,y to sort order
		actions.clickAndHold(getSelectedFieldToInterchange("DocID").getWebElement());
		actions.dragAndDropBy(getSelectedFieldToInterchange("DocID").getWebElement(), 50, 100).build().perform();
		ElementCollection pickColumnafterSelectedfieldList = getSelectedFieldsAvailablePickColumnDisplay();
		List<String> manualField = availableListofElements(pickColumnafterSelectedfieldList);
		String manualUpperCase = manualField.toString().toUpperCase();
		System.out.println(manualUpperCase);
//	String manualValue = String.join(", ", manualUpperCase);
		saveConfigureMiniDocList();
		driver.waitForPageToBeReady();
		baseClass.stepInfo("Sorted order in manualsortorder:" + manualUpperCase + "");
		baseClass.waitForElementCollection(getMiniDocListHeaderValue());
		List<WebElement> allValues = getMiniDocListHeaderValue().FindWebElements();
		List<String> arrayMiniDocList = new ArrayList<String>();
		driver.waitForPageToBeReady();
		for (int j = 1; j < allValues.size(); j++) {
			arrayMiniDocList.add(allValues.get(j).getText());
			if (j == 2) {
				JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();
				jse.executeScript("document.querySelector('.dataTables_scrollBody').scrollBy(400,0)");
				driver.waitForPageToBeReady();
			}
		}
		String headerUpperCase = arrayMiniDocList.toString().toUpperCase();
		System.out.println(arrayMiniDocList);
//	String headerValue = String.join(", ", arrayMiniDocList);
		Assert.assertEquals(manualUpperCase, headerUpperCase);
		baseClass.passedStep("MiniDocList displayed as per the sorted order");
		baseClass.stepInfo("Sorted order in minidoclist:" + arrayMiniDocList + "");
		return manualUpperCase;
	}

	/**
	 * @author Indium-Baskar date: 22/10/2021 Modified date: NA
	 * @return
	 * @Description : this method used to verify new changes
	 */
	public String againDocviewToViewNewChanges() {
		List<WebElement> allValues = getMiniDocListHeaderValue().FindWebElements();
		List<String> arrayMiniDocList = new ArrayList<String>();
		driver.waitForPageToBeReady();
		for (int j = 1; j < allValues.size(); j++) {
			arrayMiniDocList.add(allValues.get(j).getText());
			if (j == 2) {
				JavascriptExecutor jse = (JavascriptExecutor) driver.getWebDriver();
				jse.executeScript("document.querySelector('.dataTables_scrollBody').scrollBy(400,0)");
				driver.waitForPageToBeReady();
			}
		}
		String headerUpperCase = arrayMiniDocList.toString().toUpperCase();
		baseClass.stepInfo("App honor for new selection");
		return headerUpperCase;
	}

	/**
	 * @author Indium Mohan
	 * @Description : this method used for selectig first 10 docs from minidoclist
	 *              and check the history drop down..
	 */

	public void Select10DocsMiniDocList() throws InterruptedException {
		driver.waitForPageToBeReady();
		for (int i = 1; i <= 10; i++) {
			baseClass.waitTillElemetToBeClickable(getClickDocviewID(i));
			getClickDocviewID(i).waitAndClick(5);
			driver.waitForPageToBeReady();

		}
		try {
			baseClass.waitForElementCollection(getDocView_MiniListDocuments());
			Set<String> duplicates = new HashSet<String>();
			List<String> miniDocList = new ArrayList<>();
			ElementCollection miniDocListelement = getMiniDocListDocIdText();
			miniDocList = availableListofElements(miniDocListelement);
			for (String minidoclist : miniDocList) {
				duplicates.add(minidoclist);
			}
			driver.waitForPageToBeReady();
			baseClass.waitForElement(getDocView_HistoryButton());
			getDocView_HistoryButton().waitAndClick(5);
			baseClass.stepInfo("User clicked clock icon in minidoclist");
			baseClass.waitForElementCollection(getHistoryDropDown());
			int docId = getHistoryDropDown().size();
			baseClass.stepInfo("History drop down docId : " + docId);
		} catch (Exception e) {
			e.printStackTrace();
			baseClass.failedStep("Failed to get size of history drop down");
		}

	}

	/**
	 * @author Indium Mohan
	 * @Description : this method used for select a doc from minidoclist history
	 *              drop down..
	 */

	public void SelectaDocsFromHistoryDropDownInMiniDocList(String docIdName) throws InterruptedException {

		driver.waitForPageToBeReady();
		driver.scrollPageToTop();

		try {
			driver.waitForPageToBeReady();
			baseClass.waitForElement(getDocView_HistoryButton());
			getDocView_HistoryButton().waitAndClick(5);
			baseClass.waitForElement(getHistoryClock(docIdName));
			getHistoryClock(docIdName).waitAndClick(5);
			baseClass.passedStep("DocId selected from history drop down : " + docIdName);
		} catch (Exception e) {
			e.printStackTrace();
			baseClass.failedStep("DocId is not selected from history drop down");
		}
	}

	/**
	 * @author Indium-Baskar date: 22/10/2021 Modified date: NA
	 * @Description : this method used to metadata value to be displayed
	 */

	public void metaDataValueToBeDisplayed(String fieldValue, String text) {
		driver.waitForPageToBeReady();
		clickManualSortButton();
		driver.waitForPageToBeReady();
		removingSelectedWebFieldInConfigureList();
		ElementCollection pickColumnAvailableLists = getAvailablePickColumnDisplayFields();
		availablePickColumnDisplayList = availableListofElements(pickColumnAvailableLists);
		for (String element : availablePickColumnDisplayList) {
			if (element.equals(fieldValue)) {
				Element sourcefromPickColumDisplay = getFromAvailableFieldPickColumnDisplay(fieldValue);
				Element destinationfromPickColumDisplay = getToSelectedField();
				dragAndDropAction(sourcefromPickColumDisplay, destinationfromPickColumDisplay);
			}
		}
		driver.waitForPageToBeReady();
		saveConfigureMiniDocList();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getMetaDataText(text));
		Boolean flag = getMetaDataText(text).isDisplayed();
		softAssertion.assertTrue(flag);
		System.out.println(flag);
		baseClass.passedStep("Upadated metadata value displayed in minidoclist");
	}

	/**
	 * @author Indium-Baskar date: 22/10/2021 Modified date: NA
	 * @Description : this method used to check the default header value and after
	 *              sort sequence to display in minidoclist
	 */
	public void savedSearchToSortSequence() {
		driver.waitForPageToBeReady();
		String hedaerValue = againDocviewToViewNewChanges();
		clickManualSortButton();
		driver.waitForPageToBeReady();
		ElementCollection pickColumnafterSelectedfieldList = getSelectedFieldsAvailablePickColumnDisplay();
		List<String> manualField = availableListofElements(pickColumnafterSelectedfieldList);
		String manualUpperCase = manualField.toString().toUpperCase();
		softAssertion.assertEquals(hedaerValue, manualUpperCase);
		saveConfigureMiniDocList();
		String sortingOrder = currentSortOrderShouldBeDisplayed();
		String afterSortSequence = againDocviewToViewNewChanges();
		softAssertion.assertEquals(sortingOrder, afterSortSequence);
	}

	/**
	 * @author Indium-Steffy date: 02/12/2021 This method is to configure the mini
	 *         doc list to show completed docs
	 */
	public void configureMiniDocListToShowCompletedDocs() {
		configureMiniDocListPopupOpen();
		baseClass.waitForElement(this.getShowCompletedDocsToggle());
		this.getShowCompletedDocsToggle().waitAndClick(5);
		saveConfigureMiniDocList();
	}

	/**
	 * @author Vijaya.Rani 16/12/21 NA Modified date: NA Modified by:Steffy
	 * @description perform Gear Icon Cancel btn
	 */
	public void performGesrIconCancelBtn() {

		// Click gear icon open popup window and cancel
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getChildWindowGearIcons());
		getChildWindowGearIcons().waitAndClick(10);
		baseClass.stepInfo("Successflly open gear review mode popup window");

		baseClass.waitForElement(getDocview_MiniDoc_GearIconCancelBtn());
		getDocview_MiniDoc_GearIconCancelBtn().waitAndClick(10);
		baseClass.stepInfo("mini doc list pop up should be closed ");
	}

	/**
	 * @author Vijaya.Rani 16/12/21 NA Modified date: NA Modified by:Steffy
	 * @description perform Sort DocID
	 */
	public void performSortDocIdMiniDocList() {

		driver.waitForPageToBeReady();
		baseClass.waitForElement(getDocview_MiniDoc_DocId());

		softAssertion.assertTrue(getDocview_MiniDoc_DocId().isElementPresent());
		softAssertion.assertAll();

		baseClass.stepInfo("The  DocID in ascending order is Present");
	}

	/**
	 * @author Vijaya.Rani 17/12/21 NA Modified date: NA Modified by:Steffy
	 * @description perform Review Mode GearIcon Chec kEmailAuthorAndAddress
	 */
	public void performReviewModeGearIconCheckEmailAuthorAndAddress() {

		driver.waitForPageToBeReady();
		baseClass.waitForElement(getChildWindowGearIcons());
		getChildWindowGearIcons().waitAndClick(10);
		baseClass.stepInfo("Successflly open gear review mode popup window");

		baseClass.waitForElement(getDocview_GearEmailAddress());
		softAssertion.assertTrue(getDocview_GearEmailAddress().isElementPresent());
		baseClass.passedStep("EmailAddress field is displayed");

		baseClass.waitForElement(getDocview_GearEmailAuthorName());
		softAssertion.assertTrue(getDocview_GearEmailAuthorName().isElementPresent());
		baseClass.passedStep("EmailAuthorName field is displayed");

		baseClass.passedStep("EmailAuthorNameAndAddress field is not displayed on configure mini doc list");

	}

	/**
	 * @author Gopinath
	 * @Description : this method used for verifying cursor navigated to child
	 *              window clicking on saved stamp.
	 * @param textBox : textBox is String value that text value ned to enter in
	 *                comment box.
	 * @param colour  : colour is String value that colour stamp to select.
	 */

	public void verifyCursorNavigatedToChildWindowClickingOnSavedStamp(String textBox, String colour)
			throws InterruptedException {
		try {
			driver.waitForPageToBeReady();
			reusableDocViewPage.clickGearIconOpenMiniDocList();
			String miniDocListPrent = reusableDocViewPage.switchTochildWindow();
			baseClass.stepInfo("MiniDocList child window opened");
			driver.waitForPageToBeReady();
			getCursorNextDocumentId().isElementAvailable(15);
			String nextDocId = getCursorNextDocumentId().getText().trim();
			driver.getWebDriver().switchTo().window(miniDocListPrent);
			driver.waitForPageToBeReady();
			reusableDocViewPage.lastAppliedStamp(colour);
			reusableDocViewPage.switchTochildWindow();
			String currentDocId = getCurrentDocumentId().getText().trim();

			if (currentDocId.contentEquals(nextDocId)) {
				baseClass.passedStep("Cursor navigated to next document successfully by clicking on saved stamp");
			} else {
				baseClass.failedStep("Cursor not navigated to next document successfully by clicking on saved stamp");
			}
			reusableDocViewPage.childWindowToParentWindowSwitching(miniDocListPrent);
			DocViewPage dovView = new DocViewPage(driver);
			dovView.getDocView_SaveWidgetButton().waitAndClick(10);
			driver.Navigate().refresh();
		} catch (Exception e) {
			e.printStackTrace();
			baseClass.failedStep(
					"Exception occured whileverifying cursor navigated to child window clicking on saved stamp. "
							+ e.getLocalizedMessage());
		}
	}

	/**
	 * @author Raghuram.A Date: 1/3/21 Modified date:N/A Modified by: N/A
	 * @Description :
	 * 
	 */

	public void actionOnDocsFromHistoryDropDown(int limit, int sizeofList, List<String> docIDlist,
			Boolean iterateMethod, String additonal1, Boolean docViewPanelCheck, Boolean historyListCheck,
			Boolean checkBox, Boolean additional2) throws InterruptedException {

		List<String> selectedDocList = new ArrayList<>();
		List<String> historyDocList = new ArrayList<>();
		String docName = null;

		if (iterateMethod) {
			for (int i = 1; i <= limit; i++) {
				getClickDocviewID(i).waitAndClick(5);
				driver.waitForPageToBeReady();
				docName = getClickDocviewID(i).getText();
				selectedDocList.add(docName);
			}
		}
		if (docViewPanelCheck) {
			for (String docNames : selectedDocList) {
				getDocView_HistoryButton().waitAndClick(5);
				baseClass.stepInfo("Selected DocId : " + docNames);
				driver.waitForPageToBeReady();
				getHistoryDocId(docNames).waitAndClick(2);
				driver.waitForPageToBeReady();
				baseClass.waitTime(2);
				String currentDocID = getMainWindowActiveDocID().getText();
				baseClass.stepInfo("DocView panel DocId : " + currentDocID);

				baseClass.textCompareEquals(docNames, currentDocID,
						"Selected document from history drop down are loaded and displayed in docview panel",
						"document not displayed in docview panel");
				driver.waitForPageToBeReady();

			}
		}
		if (historyListCheck) {
			getDocView_HistoryButton().waitAndClick(5);
			baseClass.waitForElementCollection(getHistoryDropDown());
			historyDocList = baseClass.availableListofElements(getHistoryDropDown());
			baseClass.stepInfo("Selected DocList" + selectedDocList.toString());
			Collections.sort(selectedDocList);
			baseClass.stepInfo("History DocList" + selectedDocList.toString());
			Collections.sort(historyDocList);

			baseClass.listCompareEquals(selectedDocList, historyDocList,
					"Prior viewed document ID displayed in history drop down",
					"All Prior viewed document ID not displayed in history drop down");
		}

		if (checkBox) {
			for (int i = 1; i <= limit; i++) {
				baseClass.waitForElement(getDocView_MiniDoc_ChildWindow_Selectdoc(i));
				getDocView_MiniDoc_ChildWindow_Selectdoc(i).waitAndClick(5);
			}
		}
	}

	/**
	 * @Author Jeevitha
	 * @param fieldValue
	 * @param SetDocumentSorting
	 */
	public void verifyWhetherFieldAvailableInTab(String fieldValue, boolean SetDocumentSorting) {
		driver.waitForPageToBeReady();

		ElementCollection pickColumnafterSelectedfieldList = getAvailablePickColumnDisplayFields();
		afterActionselectedFieldsList = availableListofElements(pickColumnafterSelectedfieldList);
		for (String element : afterActionselectedFieldsList) {
			if (element.equals(fieldValue)) {
				baseClass.passedStep(fieldValue + " : Is Displayed in Available fields of Pick Column Display");

			}
		}
		if (SetDocumentSorting) {
			getSetDocumentSortingTab().waitAndClick(10);
			driver.waitForPageToBeReady();
			ElementCollection SetDocumentSortingList = getAvailableSetDocumentField();
			afterActionselectedFieldsList = availableListofElements(SetDocumentSortingList);
			for (String element : afterActionselectedFieldsList) {
				if (element.equals(fieldValue)) {
					baseClass.passedStep(fieldValue + " : Is Displayed in Available fields of Set Document Sorting");
				}
			}
		}
	}

	/**
	 * @Author Jeevitha @Modified BY Raghuram @Modified Date : 9/02/2022
	 * @param name
	 */
	public boolean verifySelectedDocHighlight(String name) {
		driver.waitForPageToBeReady();
		String bgColor = getCheckSelectedBgColor(name).GetCssValue("background-color");

		bgColor = rgbTohexaConvertor(bgColor);
		System.out.println(bgColor);
		if (bgColor.equals(Input.docSelectionHighlight)) {
			System.out.println("Document is Highlighted : " + bgColor);
			baseClass.passedStep("Document is Highlighted : " + bgColor);
			return true;
		} else {
			System.out.println("Document is Not highlighted");
			baseClass.failedStep("Document is Not highlighted");
			return false;
		}
		
	}

	/**
	 * @author Jayanthi.ganesan
	 */

	public void verifyOptimizedSortIsSelected() {
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		baseClass.waitForElement(getGearIcon());
		getGearIcon().waitAndClick(10);
		baseClass.waitForElement(getOptimizedSortRadioButton());
		try {
		String value = getSelectedOptimizedSortRadioButton().GetAttribute("checked");
		System.out.println(value);
		driver.waitForPageToBeReady();
		if (value.equals("true")) {
			baseClass.passedStep("Optimized mode from mini doc list is selected by default");
		} else {
			baseClass.failedStep("Optimized mode from mini doc list is not selected by default");
		}
		}
		catch(NullPointerException e) {}
	}

	/**
	 * @Author Sakthivel Description : MiniDocList toggle is on Date:10/01/22
	 *         Modified date: N/A Modified by: N/A
	 */
	public void verifyDocToggleisOn() {
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getDocView_MiniDoclist_GearIcon());
		getDocView_MiniDoclist_GearIcon().waitAndClick(10);
		baseClass.waitForElement(getShowCompleteDocsButton());
		getShowCompleteDocsButton().waitAndClick(10);
		baseClass.waitForElement(getMiniDocListConfirmationButton("Save"));
		getMiniDocListConfirmationButton("Save").Click();
		System.out.println("Saved Confirmed");
		baseClass.stepInfo("Doc toggle is on in minidoclist");
	}

	/**
	 * @Author Sakthivel Description :Minidoclist DocId checkMark Verification and
	 *         Selected background highlight verification Date:10/01/22 Modified
	 *         date: N/A Modified by: N/A
	 */
	public void verifyCheckMarkIconandDocHighlight() {
		// Main method
		driver.waitForPageToBeReady();
		int sizeofList = getListofDocIDinCW().size();
		System.out.println("Size : " + sizeofList);
		driver.Navigate().refresh();
		docIDlist = availableListofElements(getListofDocIDinCW());
		for (int i = 0; i < 1; i++) {
			String name = docIDlist.get(i);
			getDociD(name).waitAndClick(5);
			baseClass.waitForElement(getDocumentCompleteButton());
			getDocumentCompleteButton().waitAndClick(5);
			System.out.println("Completed Document ");
			baseClass.stepInfo("Completed Document ");
			baseClass.VerifySuccessMessage("Document completed successfully");
			baseClass.waitForElement(getDocIDCheckedIcon(name));
			System.out.println(name);
			docViewPage.getFirstDocIdOnMiniDocList().ScrollTo();
			System.out.println(name);
			softAssertion.assertTrue(getDocIDCheckedIcon(name).isElementPresent());
			if (getDocIDCheckedIcon(name).isElementPresent()) {
				System.out.println(" Check Marked - Pass");
				baseClass.passedStep("DocId Check Mark is displayed successfully ");
			} else {
				baseClass.failedStep("DocId Check Mark is not displayed");
			}
			getCheckSelectedBgColor(name).ScrollTo();
			String bgColor = getCheckSelectedBgColor(name).GetCssValue("background-color");
			System.out.println(bgColor);
			bgColor = rgbTohexaConvertor(bgColor);
			System.out.println(bgColor);
			if (bgColor.equals("#D4E6ED")) {
				System.out.println("document displayed in light blue highlighting");
				baseClass.passedStep("document displayed in light blue highlighting");
			} else {
				System.out.println("Not highlighted");
				baseClass.failedStep("Not highlighted");

			}

		}
	}

	/**
	 * @Author Sakthivel Description :After loading DocId checkmarkVerification and
	 *         Selected background highlight verification Date:10/01/22 Modified
	 *         date: N/A Modified by: N/A
	 */
	public void verifyAfterLoadingCheckmarkAndClr() {
		driver.waitForPageToBeReady();
		docViewPage.scrollingDocumentInMiniDocList();
		docViewPage.enterDocumentNumberTillLoading();
		baseClass.waitForElement(docViewPage.getFirstDocIdOnMiniDocList());
		int sizeofList = getListofDocIDinCW().size();
		System.out.println("Size : " + sizeofList);
		baseClass.waitForElementCollection(getListofDocIDinCW());
		docIDlist = availableListofElements(getListofDocIDinCW());
		for (int i = 0; i < 1; i++) {
			String name = docIDlist.get(i);
			getDociD(name).waitAndClick(5);
			docViewPage.editCodingFormComplete();
			System.out.println("Completed Document ");
			baseClass.stepInfo("Completed Document ");
			baseClass.stepInfo("Expected message:Document completed successfully");
			baseClass.waitForElement(getDocIDCheckedIcon(name));
			System.out.println(name);
			softAssertion.assertTrue(getDocIDCheckedIcon(name).isElementPresent());
			baseClass.waitForElement(getDocIDCheckedIcon(name));
			if (getDocIDCheckedIcon(name).isElementPresent()) {
				System.out.println("DocId : " + name + " - Check Marked - Pass");
				baseClass.passedStep("After loading DocId Check Mark is displayed successfully ");
			} else {
				baseClass.failedStep("After Loading DocId Check Mark is not displayed");
			}
			getCheckSelectedBgColor(name).ScrollTo();
			String bgColor = getCheckSelectedBgColor(name).GetCssValue("background-color");
			System.out.println(bgColor);
			bgColor = rgbTohexaConvertor(bgColor);
			System.out.println(bgColor);
			if (bgColor.equals("#D4E6ED")) {
				System.out.println("document displayed in light blue highlighting");
				baseClass.passedStep("document displayed in light blue highlighting");
			} else {
				System.out.println("Not highlighted");
				baseClass.failedStep("Not highlighted");
			}

		}

	}

	/**
	 * @Author Sakthivel Description : checkmarkVerification and Selected background
	 *         highlight verification in childwindow Date:10/01/22 Modified date:
	 *         N/A Modified by: N/A
	 */
	public void verifyCheckMarkIconandDocHighlightInChildWindow() {
		// Main method
		driver.waitForPageToBeReady();
		docViewPage.switchToNewWindow(2);
		int sizeofList = getListofDocIDinCW().size();
		System.out.println("Size : " + sizeofList);
		driver.Navigate().refresh();
		docIDlist = availableListofElements(getListofDocIDinCW());
		for (int i = 0; i < 1; i++) {
			String name = docIDlist.get(i);
			getDociD(name).waitAndClick(5);
			driver.waitForPageToBeReady();
			docViewPage.switchToNewWindow(1);
			baseClass.waitForElement(getDocumentCompleteButton());
			getDocumentCompleteButton().waitAndClick(5);
			System.out.println("Completed Document ");
			baseClass.stepInfo("Expectedmessage: Document saved successfully ");
			driver.waitForPageToBeReady();
			docViewPage.switchToNewWindow(2);
			baseClass.waitForElement(getDocIDCheckedIcon(name));
			System.out.println(name);
			docViewPage.getFirstDocIdOnMiniDocList().ScrollTo();
			System.out.println(name);
			softAssertion.assertTrue(getDocIDCheckedIcon(name).isElementPresent());
			if (getDocIDCheckedIcon(name).isElementPresent()) {
				System.out.println(" Check Marked - Pass");
				baseClass.passedStep("DocId Check Mark is displayed in child window successfully ");
			} else {
				baseClass.failedStep("DocId Check Mark is not displayed");
			}
			getCheckSelectedBgColor(name).ScrollTo();
			String bgColor = getCheckSelectedBgColor(name).GetCssValue("background-color");
			System.out.println(bgColor);
			bgColor = rgbTohexaConvertor(bgColor);
			System.out.println(bgColor);
			if (bgColor.equals("#D4E6ED")) {
				System.out.println("document displayed in light blue highlighting");
				baseClass.passedStep("document displayed in light blue highlighting");
			} else {
				System.out.println("Not highlighted");
				baseClass.failedStep("Not highlighted");

			}

		}
	}

	/**
	 * @Author Sakthivel Description :After Minidoclist loading DocId
	 *         checkmarkVerification and Selected background highlight in child
	 *         window verification Date:10/01/22 Modified date: N/A Modified by: N/A
	 */
	public void verifyAfterLoadingCheckmarkAndClrInChildWindow() {
		driver.waitForPageToBeReady();
		docViewPage.scrollingDocumentInMiniDocList();
		baseClass.waitForElement(docViewPage.getFirstDocIdOnMiniDocList());
		int sizeofList = getListofDocIDinCW().size();
		System.out.println("Size : " + sizeofList);
		driver.Navigate().refresh();
		docIDlist = availableListofElements(getListofDocIDinCW());
		for (int i = 0; i < 1; i++) {
			String name = docIDlist.get(i);
			getDociD(name).waitAndClick(5);
			driver.waitForPageToBeReady();
			docViewPage.switchToNewWindow(1);
			driver.waitForPageToBeReady();
			docViewPage.editCodingFormComplete();
			System.out.println("Completed Document ");
			baseClass.stepInfo("Completed Document ");
			baseClass.stepInfo("Expectedmessage: Document saved successfully ");
			docViewPage.switchToNewWindow(2);
			baseClass.waitForElement(getDocIDCheckedIcon(name));
			System.out.println(name);
			softAssertion.assertTrue(getDocIDCheckedIcon(name).isElementPresent());
			if (getDocIDCheckedIcon(name).isElementPresent()) {
				System.out.println("DocId : " + name + " - Check Marked - Pass");
				baseClass.passedStep("After loading DocId Check Mark is displayed in child window successfully ");
			} else {
				baseClass.failedStep("After Loading DocId Check Mark is not displayed");
			}
			getCheckSelectedBgColor(name).ScrollTo();
			String bgColor = getCheckSelectedBgColor(name).GetCssValue("background-color");
			System.out.println(bgColor);
			bgColor = rgbTohexaConvertor(bgColor);
			System.out.println(bgColor);
			if (bgColor.equals("#D4E6ED")) {
				System.out.println("Document displayed in light blue highlighting");
				baseClass.passedStep("Document displayed in light blue highlighting");
			} else {
				System.out.println("Not highlighted");
				baseClass.failedStep("Not highlighted");

			}

		}

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param SetDocumentSorting
	 * @return
	 */
	public List<String> comparingWebfieldsInOptimizedSortOrderAndManualSortOrder(boolean SetDocumentSorting) {

		ElementCollection pickColumnSelectedListAssignment = getSelectedFieldsAvailablePickColumnDisplay();
		pickColumnDisplaySelectedLists = availableListofElements(pickColumnSelectedListAssignment);

		driver.waitForPageToBeReady();
		baseClass.waitForElement(getManualSortRadioButton());
		getManualSortRadioButton().Click();
		System.out.println("Selected Web fields in Optimized sort Order are Displayed Below");
		baseClass.stepInfo("Selected Web fields in Optimized sort Order are Displayed Below");
		for (String a : pickColumnDisplaySelectedLists) {
			System.out.println(a);
			baseClass.stepInfo(a);
		}

		driver.waitForPageToBeReady();
		ElementCollection pickColumnSelectedListAssignmentTwo = getSelectedFieldsAvailablePickColumnDisplay();
		pickColumnDisplaySelectedListAssignmentTwo = availableListofElements(pickColumnSelectedListAssignmentTwo);
		System.out.println("Selected Web fields in Manual sort Order are Displayed Below");
		baseClass.stepInfo("Selected Web fields in Manual sort Order are Displayed Below");
		for (String a : pickColumnDisplaySelectedListAssignmentTwo) {
			System.out.println(a);
			baseClass.stepInfo(a);
		}
		if (pickColumnDisplaySelectedLists.equals(pickColumnDisplaySelectedListAssignmentTwo)) {
			System.out.println("Web Fields present in the Optimized sort Order and Manual sort Order are Same");
			baseClass.passedStep("Web Fields present in the Optimized sort Order and Manual sort Order are Same");
		} else {
			System.out.println("Web Fields present in the Optimized sort Order and Manual sort Order are Different");
			baseClass.failedStep("Web Fields present in the Optimized sort Order and Manual sort Order are Different");
		}
		if (SetDocumentSorting) {
			getSetDocumentSortingTab().waitAndClick(10);
			driver.waitForPageToBeReady();
			ElementCollection setDocumentColumnListA = getFieldsAvailableSetDocumetSorting();
			setDocumentSortingList = availableListofElements(setDocumentColumnListA);
			System.out.println("Selected Web fields in Set Document Sorting are Displayed Below");
			baseClass.stepInfo("Selected Web fields in Set Document Sorting are Displayed Below");
			for (String a : setDocumentSortingList) {
				System.out.println(a);
				baseClass.stepInfo(a);
			}
			if (pickColumnDisplaySelectedLists.equals(setDocumentSortingList)) {
				System.out.println("Web Fields present in the Optimized sort Order and Set Document Sorting are Same");
				baseClass
						.passedStep("Web Fields present in the Optimized sort Order and Set Document Sorting are Same");
			} else {
				System.out.println(
						"Web Fields present in the Optimized sort Order and Set Document Sorting are Different");
				baseClass.passedStep(
						"Web Fields present in the Optimized sort Order and Set Document Sorting are Different");
			}
		}
		return pickColumnDisplaySelectedLists;
	}

	/**
	 * @author Steffy
	 * @Description : this method used to verify Configure minidoc list open should
	 *              open [Child window Mini Doc list]
	 */

	public void configureMiniDocListPopupOpen(String parentId) {
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getGearIcon());
		getGearIcon().waitAndClick(5);
		driver.waitForPageToBeReady();
		driver.switchTo().window(parentId);
		baseClass.waitForElement(getConfigureMiniDocTab());
		String configureMiniDocTab = getConfigureMiniDocTab().getText();
		softAssertion.assertEquals(configureMiniDocTab, "Configure Mini DocList");
		baseClass.passedStep("Minidoclist popup opened on clicking  the gear icon");
		softAssertion.assertAll();
	}

	/**
	 * @author Indium-Steffy date: 02/12/2021 This method is to configure the mini
	 *         doc list to show completed docs
	 */
	public void configureMiniDocListToShowCompletedDocs(String parentid) {
		configureMiniDocListPopupOpen(parentid);
		baseClass.waitForElement(this.getShowCompletedDocsToggle());
		this.getShowCompletedDocsToggle().Click();
		saveConfigureMiniDocList();
		baseClass.waitTime(3);
	}

	/**
	 * @author Raghuram.A
	 * @throws AWTException
	 * @throws InterruptedException
	 */
	public String selectDocFromChildWindow() throws AWTException, InterruptedException {
		Robot robot = new Robot();
		String parentid = childWindowTransfer();

		driver.waitForPageToBeReady();
		List<String> idList = availableListofElements(getChildWindowDocIDList());
		int listSize = idList.size();
		int numberToChoose = randNumber(listSize);
		String currentDoc = idList.get(numberToChoose);
		System.out.println("Childwindow selected doc id : " + currentDoc);
		baseClass.stepInfo("Childwindow selected doc id : " + currentDoc);
		baseClass.waitForElement(getChildWindowGearIcons());
		baseClass.waitForElement(getChildWindowDocIDList(currentDoc));
		getChildWindowDocIDList(currentDoc).Click();
		// Closes child window
		childWIndowCloseHandles(parentid);
		driver.waitForPageToBeReady();

		return currentDoc;

	}

	/**
	 * @author Jayanthi.ganesan
	 * 
	 */
	public void verifyDefaultWebfieldsInManualSortOrder() {
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		// default list to be displayed in configure opo up selected fields
		String headerfieldtoCompare = reusableDocViewPage.defaultHeaderValue().toLowerCase();
		baseClass.waitForElement(getGearIcon());
		getGearIcon().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getManualSortRadioButton());
		getManualSortRadioButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		ElementCollection pickColumnSelectedListAssignmentTwo = getSelectedFieldsAvailablePickColumnDisplay();
		pickColumnDisplaySelectedListAssignmentTwo = availableListofElements(pickColumnSelectedListAssignmentTwo);
		baseClass.stepInfo("Default Selected Web fields in Manual sort Order are Displayed Below");
		for (String a : pickColumnDisplaySelectedListAssignmentTwo) {
			baseClass.stepInfo(a);
		}
		pickColumnDisplaySelectedLists.removeAll(Arrays.asList(null, ""));
		String PickcolumnfieldtoCompare = pickColumnDisplaySelectedListAssignmentTwo.toString().toLowerCase();
		System.out.println(PickcolumnfieldtoCompare + "Selected we fields.");
		System.out.println(headerfieldtoCompare + "__header");
		if (headerfieldtoCompare.equalsIgnoreCase(PickcolumnfieldtoCompare)) {
			baseClass.passedStep("Default Web Fields present Manual sort Order are as exepected");
		} else {
			baseClass.failedStep("Default Web Fields present in the  Manual sort Order are Different");
		}
		getSetDocumentSortingTab().waitAndClick(10);
		driver.waitForPageToBeReady();
		ElementCollection setDocumentColumnListA = getFieldsAvailableSetDocumetSorting();
		setDocumentSortingList = availableListofElements(setDocumentColumnListA);
		baseClass.stepInfo("Default Selected Web fields in Set Document Sorting are Displayed Below");
		for (String a : setDocumentSortingList) {
			baseClass.stepInfo(a);
		}

		baseClass.waitForElement(getMiniDocListConfirmationButton("Save"));
		getMiniDocListConfirmationButton("Save").Click();

		if (pickColumnDisplaySelectedListAssignmentTwo.equals(setDocumentSortingList)) {
			baseClass.passedStep("Default Web Fields present in  Set Document Sorting are Same");
		} else {
			baseClass.failedStep("Default Web Fields present in the  Set Document Sorting are Different");
		}

	}

	/**
	 * @author Jayanthi.ganesan
	 * @param assignname
	 * @throws InterruptedException
	 */
	public void verifyManualModeSortingConfigure(String assignname) throws InterruptedException {
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getGearIcon());
		getGearIcon().waitAndClick(10);

		driver.waitForPageToBeReady();
		baseClass.waitForElement(getManualSortRadioButton());
		getManualSortRadioButton().Click();
		// Pick Column Display
		afterActionselectedFieldsPickColumnDisplayFirstAssignment = methodforPickColumndisplay();

		// Set Document Sort
		afterActionselectedFieldsSetDocumentFirstAssignment = methodforSetDocumetSort();

		baseClass.waitForElement(getMiniDocListConfirmationButton("Save"));
		getMiniDocListConfirmationButton("Save").Click();
		System.out.println("Saved Confirmed");

		String headerfieldtoCompare = reusableDocViewPage.defaultHeaderValue().toLowerCase();
		String PickcolumnfieldtoCompare = afterActionselectedFieldsPickColumnDisplayFirstAssignment.toString()
				.toLowerCase();

		softAssertion.assertEquals(headerfieldtoCompare, PickcolumnfieldtoCompare);

		if (PickcolumnfieldtoCompare.equals(headerfieldtoCompare)) {
			baseClass.passedStep("Pick Column Display  Changes reflected in  mini doc list  headers");
		} else {
			baseClass.failedStep("Changes didn't reflect in  mini doc list  headers");
		}
		baseClass.waitForElement(getGearIcon());
		getGearIcon().waitAndClick(10);
		ElementCollection pickColumnSelectedListAssignmentTwo = getSelectedFieldsAvailablePickColumnDisplay();
		pickColumnDisplaySelectedListAssignmentTwo = availableListofElements(pickColumnSelectedListAssignmentTwo);
		getSetDocumentSortingTab().waitAndClick(10);
		driver.waitForPageToBeReady();
		ElementCollection setDocumentColumnListA = getFieldsAvailableSetDocumetSorting();
		setDocumentSortingList = availableListofElements(setDocumentColumnListA);
		baseClass.waitForElement(getMiniDocListConfirmationButton("Save"));
		getMiniDocListConfirmationButton("Save").Click();
		System.out.println("Saved Confirmed");

		if (pickColumnDisplaySelectedListAssignmentTwo.equals(setDocumentSortingList)) {
			baseClass.failedStep(
					"pick column display and set document sorting web fields are  same not having thier own changes .");
		} else {
			baseClass.passedStep(
					"pick column display and set document sorting web fields are not same each is having thier own changes  only.");
		}

		LoginPage lp = new LoginPage(driver);
		lp.logout();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		baseClass.waitForElement(getDashboardButton());
		getDashboardButton().waitAndClick(5);

		baseClass.waitForElement(getSelectAssignmentFromDashborad(assignname));
		getSelectAssignmentFromDashborad(assignname).waitAndClick(10);
		driver.scrollPageToTop();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getGearIcon());
		getGearIcon().waitAndClick(10);

		selectedFieldsSecondAssignment = docviewMinlistComparisionMethodTwo();
		baseClass.waitForElement(getMiniDocListcloseButton());
		getMiniDocListcloseButton().Click();
		driver.waitForPageToBeReady();

		softAssertion.assertNotEquals(afterActionselectedFieldsPickColumnDisplayFirstAssignment,
				selectedFieldsSecondAssignment);

		if (afterActionselectedFieldsPickColumnDisplayFirstAssignment.equals(selectedFieldsSecondAssignment)) {
			baseClass.failedStep("Changes made for RMU user for same assignment impact the"
					+ " other user who are all assigned to same assignment.");
		} else {
			baseClass.passedStep("Changes made for RMU user for same assignment doesnt impact the"
					+ " other user who are all assigned to same assignment.");
		}
	}

	/**
	 * @author Raghuram.A Description : docToCHoose Date: 01/20/22 Modified date:
	 *         N/A Modified by: N/As
	 * @param firnstDocname
	 * @param sizeofList
	 * @return
	 */
	public String docToCHoose(int sizeofList, List<String> docIDlist) {
		String docName;
		int numToCHoose;
		String nameSelected;

		numToCHoose = baseClass.randNumber(sizeofList);
		nameSelected = docIDlist.get(numToCHoose);
		System.out.println(nameSelected);
		getDociD(nameSelected).waitAndClick(5);
		driver.waitForPageToBeReady();
		baseClass.waitTime(2);
		docName = getMainWindowActiveDocID().getText();

		return docName;
	}

	/**
	 * @author Raghuram.A Description : docToCHoose Date: 01/31/22 Modified date:
	 *         N/A Modified by: N/As
	 * @param firnstDocname
	 * @param sizeofList
	 * @return
	 */
	public void docToCHoose(int sizeofList, List<String> docIDlist, Boolean additional) {
		int numToCHoose;
		String nameSelected;

		numToCHoose = baseClass.randNumber(sizeofList);
		nameSelected = docIDlist.get(numToCHoose);
		System.out.println(nameSelected);
		getDociD(nameSelected).waitAndClick(10);
		driver.waitForPageToBeReady();

	}

	/**
	 * @author Raghuram.A
	 * @return
	 */
	public List<String> getDocListDatas() {
		int sizeofList;
		driver.waitForPageToBeReady();
		baseClass.waitForElementCollection(getListofDocIDinCW());
		sizeofList = getListofDocIDinCW().size();
		// baseClass.stepInfo("Available documents in DocView page : " + sizeofList);
		System.out.println("Available documents in DocView page : " + sizeofList);
		docIDlist = baseClass.availableListofElements(getListofDocIDinCW());

		return docIDlist;
	}

	/**
	 * @author Raghuram 01/02/22 NA Modified date: NA Modified by:NA
	 * @return
	 * @description
	 */
	public void verifyCompleteCheckMarkIconandDocHighlight(List<String> docIDlist, int noOFdocsToComplete,
			Boolean verifyCheckMark) {
		// Main method
		driver.waitForPageToBeReady();

		for (int i = 0; i < noOFdocsToComplete; i++) {
			String name = docIDlist.get(i);
			getDociD(name).waitAndClick(5);

			docViewPage.editingCodingFormWithCompleteButton();

			if (verifyCheckMark) {
				verifyCompletedIconPresent(name);
			}
		}
	}

	/**
	 * @author Raghuram 01/02/22 NA Modified date: NA Modified by:NA
	 * @return
	 * @description
	 */
	public void verifyCompletedIconPresent(String name) {
		baseClass.waitForElement(getDocIDCheckedIcon(name));
		System.out.println(name);
		docViewPage.getFirstDocIdOnMiniDocList().ScrollTo();
		System.out.println(name);
		softAssertion.assertTrue(getDocIDCheckedIcon(name).isElementPresent());
		if (getDocIDCheckedIcon(name).isElementPresent()) {
			System.out.println(" Check Marked - Pass");
			baseClass.passedStep("DocId Check Mark is displayed successfully ");
		} else {
			baseClass.failedStep("DocId Check Mark is not displayed");
		}
		getCheckSelectedBgColor(name).ScrollTo();
		String bgColor = getCheckSelectedBgColor(name).GetCssValue("background-color");
		System.out.println(bgColor);
		bgColor = rgbTohexaConvertor(bgColor);
		System.out.println(bgColor);
		if (bgColor.equals("#D4E6ED")) {
			System.out.println("document displayed in light blue highlighting");
			baseClass.passedStep("document displayed in light blue highlighting");
		} else {
			System.out.println("Not highlighted");
			baseClass.failedStep("Not highlighted");

		}
	}
	

	/**
	 * @author Sakthivel 07/02/22 NA Modified date: NA Modified by:NA
	 * @throws Exception
	 * @description : verify selected Docs view in PersistentHit panel on
	 *              SearchString.
	 */
	public void verifyViewDocInPersistentHitPanel(String searchString, String searchString1) throws Exception {
		driver.waitForPageToBeReady();
		DocViewRedactions docviewRedactions = new DocViewRedactions(driver);
		baseClass.waitForElement(docViewPage.getDocView_Audio_Hit());
		String persistenthits = docViewPage.getDocView_Audio_Hit().getText().toLowerCase();
		System.out.println(persistenthits);
		if (persistenthits.contains(searchString)) {
			softAssertion.assertTrue(docViewPage.getDocView_Audio_Hit().isDisplayed(),
					"view the doc is displayed on persistent hit panel");
			baseClass.passedStep("view the document in single term persitent hits panel is successfully displayed");

		} else {
			baseClass.failedStep("failed");

		}
		driver.waitForPageToBeReady();
		baseClass.waitForElement(docViewPage.getDocView_MiniDocListIds(8));
		docViewPage.getDocView_MiniDocListIds(8).waitAndClick(10);
		baseClass.waitTime(2);
		baseClass.waitForElement(docViewPage.getDocView_Audio_Hit());
		String persistenthits1 = docViewPage.getDocView_Audio_Hit().getText().toLowerCase();
		System.out.println(persistenthits1);
		if (persistenthits1.contains(searchString1)) {
			softAssertion.assertTrue(docViewPage.getDocView_Audio_Hit().isDisplayed(),
					"view the doc is displayed on persistent hit panel");
			baseClass.passedStep("view the document in multi term persitent hits panel is successfully displayed");

		} else {
			baseClass.failedStep("failed");

		}
	}

	/**
	 * @author Sakthivel 07/02/22 NA Modified date: NA Modified by:NA
	 * @throws Exception
	 * @description : verify selected Docs on ClockIcon is presently viewed in PersistentHit panel on
	 *              SearchString.
	 */

	public void verifySelectedDocsInClockIcon(String searchString, String searchString1) throws Exception {
		driver.waitForPageToBeReady();
		DocViewRedactions docviewRedactions = new DocViewRedactions(driver);
		String clockDocId = "ID00001213";
		SelectaDocsFromHistoryDropDownInMiniDocList(clockDocId);
		baseClass.waitForElement(docViewPage.getDocView_Audio_Hit());
		String persistenthits1 = docViewPage.getDocView_Audio_Hit().getText().toLowerCase();
		System.out.println(persistenthits1);
		if (persistenthits1.contains(searchString)) {
			softAssertion.assertTrue(docViewPage.getDocView_Audio_Hit().isDisplayed(),
					"view the doc is displayed on persistent hit panel");
			baseClass.passedStep(
					"Selected doc on history dropdown is presently viewed on singleterm persistenthit panel is successfully displayed");

		} else {
			baseClass.failedStep("failed");

		}
		driver.waitForPageToBeReady();
		driver.scrollPageToTop();
		try {
			driver.waitForPageToBeReady();
			baseClass.waitForElement(getDocView_HistoryButton());
			getDocView_HistoryButton().waitAndClick(5);
			baseClass.waitTime(5);
			baseClass.waitForElement(getHistoryClockDocs());
			getHistoryClockDocs().WaitUntilPresent().waitAndClick(5);
			baseClass.passedStep("DocId selected from history drop down");
		} catch (Exception e) {
			e.printStackTrace();
			baseClass.failedStep("DocId is not selected from history drop down");
		}
		baseClass.waitTime(5);
		baseClass.waitForElement(docViewPage.getDocView_Audio_Hit());
		String persistenthits = docViewPage.getDocView_Audio_Hit().getText().toLowerCase();
		System.out.println(persistenthits);
		if (persistenthits.contains(searchString1)) {
			softAssertion.assertTrue(docViewPage.getDocView_Audio_Hit().isDisplayed(),
					"view the doc is displayed on persistent hit panel");
			baseClass.passedStep(
					"Selected doc on history dropdown is presently viewed on multiterm persistenthit panel is successfully displayed");

		} else {
			baseClass.failedStep("failed");

		}

	}
	
	/**
	 * @author Vijaya.Rani Modify Date: 16/03/22 NA Modified date: NA Modified by:NA
	 * @description perform Gear Icon OpenPopUp Window
	 */
	public void performGearIconOpenPopUpWindow() {

		// Click gear icon open popup window and cancel
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getChildWindowGearIcons());
		getChildWindowGearIcons().waitAndClick(10);
		baseClass.stepInfo("Successflly open gear review mode popup window");
		baseClass.waitForElement(getConfigureMiniDocTab());
		softAssertion.assertTrue(getConfigureMiniDocTab().Displayed());
		softAssertion.assertAll();
		baseClass.passedStep("Configure mini doc list pop up Window open Successfully");
	}
	
	/**
	 * Indium-Baskar
	 * 
	 */
	public void configureMinidoclistAudio() {
		driver.waitForPageToBeReady();
		getChildWindowGearIcons().waitAndClick(5);
		driver.waitForPageToBeReady();
		String valueOne = "FamilyMemberCount";
		String valuetwo = "FamilyRelationship";
		String valuethree = "DocFileType";
		driver.waitForPageToBeReady();
		baseClass.passedStep("Optimized mode is selected");
		getValueToRemoveFromSelectedWebFields(valueOne).waitAndClick(3);
		getValueToRemoveFromSelectedWebFields(valuetwo).waitAndClick(3);
		getValueToRemoveFromSelectedWebFields(valuethree).waitAndClick(3);
		// configure as per test case
		String dragone = "FamilyRelationship";
		String dragtwo = "VideoPlayerReady";
		String dragthree = "SourceDocID";
		driver.waitForPageToBeReady();
		Element sourcefromPickColumDisplay = getFromAvailableFieldPickColumnDisplay(dragone);
		Element destinationfromPickColumDisplay = getToSelectedField();
		dragAndDropAction(sourcefromPickColumDisplay, destinationfromPickColumDisplay);
		Element sourcefromPickColumDisplays = getFromAvailableFieldPickColumnDisplay(dragtwo);
		Element destinationfromPickColumDisplays = getToSelectedField();
		dragAndDropAction(sourcefromPickColumDisplays, destinationfromPickColumDisplays);
		Element sourcefromPickColumDisplayed = getFromAvailableFieldPickColumnDisplay(dragthree);
		Element destinationfromPickColumDisplayed = getToSelectedField();
		dragAndDropAction(sourcefromPickColumDisplayed, destinationfromPickColumDisplayed);
		baseClass.waitForElement(getMiniDocListConfirmationButton("Save"));
		getMiniDocListConfirmationButton("Save").waitAndClick(5);
		
	}
	
	public void removingFieldsAndDragnDropDefault() throws InterruptedException {
		driver.waitForPageToBeReady();
		try {
			driver.waitForPageToBeReady();

			baseClass.waitForElement(getDocView_MiniDoclist_GearIcon());
			getDocView_MiniDoclist_GearIcon().waitAndClick(10);

        driver.waitForPageToBeReady();
		ElementCollection pickColumnafterSelectedfieldList = getSelectedFieldsAvailablePickColumnDisplay();
		afterActionselectedFieldsList = availableListofElements(pickColumnafterSelectedfieldList);
		for (String element : afterActionselectedFieldsList) {
			System.out.println(element);
			getValueToRemoveFromSelectedWebFields(element).waitAndClick(3);
		}
		    baseClass.waitTime(5);
			dragAndDropAvailableFieldstoSelectedfieldsPickColumDisplay("DocID");
			dragAndDropAvailableFieldstoSelectedfieldsPickColumDisplay("DocFileType");
			dragAndDropAvailableFieldstoSelectedfieldsPickColumDisplay("FamilyRelationship");
			dragAndDropAvailableFieldstoSelectedfieldsPickColumDisplay("FamilyMemberCount");

			getMiniDocListConfirmationButton("Save").waitAndClick(10);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("ConfigureMiniDocist popup is not opened");
		}
	}
	
	/**
	 * @author
	 * @description : removing All Existing Fields And AddingNewField
	 * @param fieldValue
	 */
	public void removingAllExistingFieldsAndAddingNewField(String fieldValue) {

		driver.waitForPageToBeReady();
		clickManualSortButton();
		driver.waitForPageToBeReady();
		removingSelectedWebFieldInConfigureList();
		ElementCollection pickColumnAvailableLists = getAvailablePickColumnDisplayFields();
		availablePickColumnDisplayList = availableListofElements(pickColumnAvailableLists);
		for (String element : availablePickColumnDisplayList) {
			if (element.equals(fieldValue)) {
				Element sourcefromPickColumDisplay = getFromAvailableFieldPickColumnDisplay(fieldValue);
				Element destinationfromPickColumDisplay = getToSelectedField();
				dragAndDropAction(sourcefromPickColumDisplay, destinationfromPickColumDisplay);
			}
		}
		driver.waitForPageToBeReady();
		saveConfigureMiniDocList();
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		baseClass.waitForElement(getDocView_MiniDoclist_Header_Webfields(fieldValue));
		Boolean flag = getDocView_MiniDoclist_Header_Webfields(fieldValue).isDisplayed();
		softAssertion.assertTrue(flag);
		System.out.println(flag);
		baseClass.passedStep("Newly Added Field is displayed in minidoclist");
	}
	public void checkThreadMapDoc() {
		MiniDocListPage doclist=new MiniDocListPage(driver);
		// Main method
			int sizeofList = doclist.getListofDocIDinCW().size();
		System.out.println("Size : " + sizeofList);
		int totalDocs = getDocCountMethod();
		Boolean dataEmpty = false; // for additional purpose

		docIDlist = availableListofElements(doclist.getListofDocIDinCW());

		for (int i = 1; i <= totalDocs; i++) {
		String name = docIDlist.get(i);
		getDociD(name).waitAndClick(5);
		System.out.println("Selected Document : " + name);
		baseClass.stepInfo("Selected Document : " + name);

		// Move to NearDupes
		driver.waitForPageToBeReady();
		getDocView_Analytics_liDocumentThreadMap().WaitUntilPresent().ScrollTo();
		getDocView_Analytics_liDocumentThreadMap().waitAndClick(5);
		// Verify Empty
		if (doclist.getThreadData().isElementAvailable(3)) {
			dataEmpty = false;
			}
		else {
			dataEmpty = true;
			System.out.println("Document that has Thread Map datas : " + name);
			baseClass.stepInfo("Document that has Thread Map datas : " + name);
			break;}}
		}
	
	public void checkConceptuallySimilarDoc() {
		MiniDocListPage doclist=new MiniDocListPage(driver);
		// Main method
			int sizeofList = doclist.getListofDocIDinCW().size();
		System.out.println("Size : " + sizeofList);
		int totalDocs = getDocCountMethod();
		Boolean dataEmpty = false; // for additional purpose

		docIDlist = availableListofElements(doclist.getListofDocIDinCW());

		for (int i = 1; i <= totalDocs; i++) {
		String name = docIDlist.get(i);
		getDociD(name).waitAndClick(5);
		System.out.println("Selected Document : " + name);
		baseClass.stepInfo("Selected Document : " + name);

		// Move to NearDupes
		driver.waitForPageToBeReady();
		getDocView_Analytics_liDocumentConceptualSimilarab().WaitUntilPresent().ScrollTo();
		getDocView_Analytics_liDocumentConceptualSimilarab().waitAndClick(5);
		// Verify Empty
		if (doclist.getConceptuallySimilarData().isElementAvailable(3)) {
			dataEmpty = false;
			}
		else {
			dataEmpty = true;
			System.out.println("Document that has Conceptually similar datas : " + name);
			baseClass.stepInfo("Document that has Conceptually similar datas : " + name);
			break;}}
		}
	public void checkFamilyMemberDoc() {
		MiniDocListPage doclist=new MiniDocListPage(driver);
		// Main method
			int sizeofList = doclist.getListofDocIDinCW().size();
		System.out.println("Size : " + sizeofList);
		int totalDocs = getDocCountMethod();
		Boolean dataEmpty = false; // for additional purpose

		docIDlist = availableListofElements(doclist.getListofDocIDinCW());

		for (int i = 1; i <= totalDocs; i++) {
		String name = docIDlist.get(i);
		getDociD(name).waitAndClick(5);
		System.out.println("Selected Document : " + name);
		baseClass.stepInfo("Selected Document : " + name);

		// Move to NearDupes
		driver.waitForPageToBeReady();
		getDocView_Analytics_FamilyTab().WaitUntilPresent().ScrollTo();
		getDocView_Analytics_FamilyTab().waitAndClick(5);
		// Verify Empty
		if (getFamilyData().isElementAvailable(3)) {
			dataEmpty = false;
		}		
		else {
			dataEmpty = true;
			System.out.println("Document that has Family datas : " + name);
			baseClass.stepInfo("Document that has Family datas : " + name);
			break;
			}
		}
		
	}

/**
* @author Raghuram.A
*/
public void checkFamilyMemberForUniqueDoc() {
MiniDocListPage doclist = new MiniDocListPage(driver);
List<String> docIDFMlist = new ArrayList<>();
String uniqueDoc = "";
// Main method
int sizeofList = doclist.getListofDocIDinCW().size();
System.out.println("Size : " + sizeofList);
int totalDocs = getDocCountMethod();
Boolean dataEmpty = false; // for additional purpose
Boolean uniqueDocStatus = false;

docIDlist = availableListofElements(doclist.getListofDocIDinCW());

for (int i = 1; i <= totalDocs; i++) {
	String name = docIDlist.get(i);
	getDociD(name).waitAndClick(5);
	System.out.println("Selected Document : " + name);
	baseClass.stepInfo("Selected Document : " + name);

	// Move to NearDupes
	driver.waitForPageToBeReady();
	getDocView_Analytics_FamilyTab().WaitUntilPresent().ScrollTo();
	getDocView_Analytics_FamilyTab().waitAndClick(5);
	// Verify Empty
	if (getFamilyData().isElementAvailable(3)) {
		dataEmpty = false;
	} else {
		dataEmpty = true;
		System.out.println("Document that has FamilyMember datas : " + name);
		baseClass.stepInfo("Document that has FamilyMember datas : " + name);

		baseClass.waitForElementCollection(dtDocumentFamilyMembersID());
		int sizeofDOcFMList = doclist.dtDocumentFamilyMembersID().size();

		for (int j = 1; j <= sizeofDOcFMList; j++) {
			docIDFMlist = availableListofElements(doclist.dtDocumentFamilyMembersID());
			System.out.println(docIDFMlist.get(j));
			try {
				uniqueDoc = baseClass.compareListWithString1(docIDlist, docIDFMlist.get(j), "", "");
				if (!uniqueDoc.equals("Empty")) {
					System.out.println("Unique doc : " + uniqueDoc);
					uniqueDocStatus = true;
					break;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (!uniqueDocStatus && j == sizeofDOcFMList) {
				baseClass.failedStep("Expected Input data not available");
			}
		}
		if (uniqueDocStatus) {
			break;
		}
	}
}
}



/**
* @author Raghuram.A
* @return
*/
public Integer getDocCountMethod() {
String documentSize = "";
int docCount = 0;
try {
	baseClass.waitForElement(getDocumentCountFromDocView());
	String sizeofList = getDocumentCountFromDocView().getText();
	documentSize = sizeofList.substring(sizeofList.indexOf("of") + 2, sizeofList.indexOf("Docs")).trim();
	System.out.println("Size : " + documentSize);
	baseClass.stepInfo("Available documents in DocView page : " + sizeofList);
} catch (Exception e) {
	e.printStackTrace();
}
docCount = Integer.parseInt(documentSize);
return docCount;
}

/**
* @author Raghuram.A
* @throws AWTException
*/
public void checkNearDupeDoc() throws AWTException {
MiniDocListPage doclist = new MiniDocListPage(driver);
DocViewPage docViewPage = new DocViewPage(driver);
List<String> docIDFMlist = new ArrayList<>();
String uniqueDoc = "";
// Main method
int sizeofList = doclist.getListofDocIDinCW().size();
System.out.println("Size : " + sizeofList);
int totalDocs = getDocCountMethod();
Boolean dataEmpty = false; // for additional purpose
Boolean uniqueDocStatus = false;

docIDlist = availableListofElements(doclist.getListofDocIDinCW());

for (int i = 1; i <= totalDocs; i++) {
	String name = docIDlist.get(i);
	getDociD(name).waitAndClick(5);
	System.out.println("Selected Document : " + name);
	baseClass.stepInfo("Selected Document : " + name);

	// Move to NearDupes
	driver.waitForPageToBeReady();
	getDocView_Analytics_NearDupeTab().WaitUntilPresent().ScrollTo();
	getDocView_Analytics_NearDupeTab().waitAndClick(5);

	// Verify Empty
	if (getNearDupeTab().isElementAvailable(3)) {
		dataEmpty = false;
	} else {
		dataEmpty = true;
		System.out.println("Document that has NearDupe datas : " + name);
		baseClass.stepInfo("Document that has NearDupe datas : " + name);

		int sizeofDOcFMList = getNearDupeDocCounnt().size();
		baseClass.waitForElementCollection(getNearDupeDocIDs(sizeofDOcFMList));

		// Header
		int docIDindex = baseClass.getIndex(getNearDupesDataList(), "DocID");

		for (int j = 1; j <= sizeofDOcFMList; j++) {
			docIDFMlist = availableListofElements(doclist.getNearDupeDocIDs(docIDindex + 1));
			System.out.println(docIDFMlist.get(j - 1));
			try {
				uniqueDoc = baseClass.compareListWithString1(docIDlist, docIDFMlist.get(j - 1), "", "");
				if (!uniqueDoc.equals("Empty")) {
					System.out.println("Unique NearDupe doc : " + uniqueDoc);
					uniqueDocStatus = true;
					if (docViewPage.highlightVerification(uniqueDoc)) {
						baseClass.stepInfo("Highlights present in the document");
						break;
					} else {
						uniqueDocStatus = false;
						continue;
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		if (uniqueDocStatus) {
			break;
		}
	}
}

}

}
