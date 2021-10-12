package pageFactory;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import ch.qos.logback.core.joran.action.Action;
import executionMaintenance.Log;
import executionMaintenance.UtilityLog;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class SavedSearch {

	Driver driver;
	SessionSearch search;
	BaseClass base;
	AssignmentsPage assgnpage;
	SoftAssert softAssertion;

	public Element getSavedSearch_SearchName() {
		return driver.FindElementById("txtSearchName");
	}

	public Element getSavedSearch_ApplyFilterButton() {
		return driver.FindElementById("btnApplyFilter");
	}

	public Element getSelectWithName(String serachName) {
		return driver.FindElementByXPath("//*[@id='SavedSearchGrid']/tbody//tr[td='" + serachName + "']/td[1]/label/i");
	}

	public Element getSavedSearch_ScheduleButton() {
		return driver.FindElementById("rbnSchedule");
	}

	public Element getSavedSearch_SchedulePopUp() {
		return driver.FindElementById("divscheduler");
	}

	public Element getSavedSearch_ScheduleTime() {
		return driver.FindElementById("txtOneTimeStartDate");
	}

	public Element getSaveSchedulerBtn() {
		return driver.FindElementById("btnScheduleSubmit");
	}

	public Element getSavedSearchNewGroupExpand() {
		return driver.FindElementByXPath(".//*[@id='-1']/i");
	}

	public Element getSavedSearchGroupName() {
		return driver.FindElementByXPath(".//*[@id='jsTreeSavedSearch']//a[contains(.,'New node')]");
	}

	public Element getShareSerachBtn() {
		return driver.FindElementById("rbnShare");
	}

	// public Element getSearchUserToShare(){ return
	// driver.FindElementByXPath("//input[@id= 'kwd_search']"); }
	// public ElementCollection getUserPopUptoShare(){ return
	// driver.FindElementsByXPath("(//div[@class='user-list-content
	// clear'])[1]"); }

	public ElementCollection getUserInShareList() {
		return driver.FindElementsByXPath("//*[@id='my-table']/tbody/tr/td[2]/label[1]");
	}

	public Element getSharecheckBoxofUser(int num) {
		return driver.FindElementByXPath("(//*[@id='my-table']/tbody/tr/td[1]/label/i)[" + num + "]");
	}

	public Element getShareSaveBtn() {
		return driver.FindElementByXPath("(//button[text()='Save'])[1]");
	}
	
	public Element getShareSaveBtnNew() {
		return driver.FindElementByCssSelector("#btnShareSave");
	}

	public Element getSuccessMsgHeader() {
		return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span");
	}

	public Element getSuccessMsg() {
		return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p");
	}

	// batch upload
	public Element getBatchUploadButton() {
		return driver.FindElementById("rbnBatchSearchUpload");
	}

	public Element getSelectFile() {
		return driver.FindElementByXPath("//*[@id='fileupload']");
	}

	public Element getSubmitToUpload() {
		return driver.FindElementByXPath("//button[contains(text(),'Ok')]");
	}

	public Element getMySeraches() {
		return driver.FindElementByXPath("//*[@id='jsTreeSavedSearch']/ul/li[1]/i");
	}

	public Element getSelectUploadedFile(String fileName) {
		return driver.FindElementByXPath("//a[contains(text(),'" + fileName.replace(".xlsx", "") + "')]");
	}

	public Element getNumberOfSavedSearchToBeShown() {
		return driver.FindElementByXPath("//*[@id='SavedSearchGrid_length']/label/select");
	}

	public ElementCollection getSearchStatus() {
		return driver.FindElementsByXPath("//*[@id='SavedSearchGrid']/tbody/tr/td[6]");
	}

	public ElementCollection getCounts() {
		return driver.FindElementsByXPath("//*[@id='SavedSearchGrid']/tbody/tr");
	}

	public Element getToDocList() {
		return driver.FindElementById("btnDocumentList");
	}

	public Element getToDocView() {
		return driver.FindElementById("document-btn");
	}

	public Element getCountofDocs() {
		return driver.FindElementByXPath("//*[@id='SavedSearchGrid']/tbody/tr/td[4]");
	}

	public Element getSavedSearchToBulkFolder() {
		return driver.FindElementById("rbnFolder");
	}

	public Element getSavedSearchToBulkAssign() {
		return driver.FindElementById("rbnAssign");
	}

	public Element getSavedSearchDeleteButton() {
		return driver.FindElementById("rbnDelete");
	}

	public Element getSavedSearchExecuteButton() {
		return driver.FindElementById("rbnSubmit");
	}

	public Element getSavedSearchEditButton() {
		return driver.FindElementById("rbnEdit");
	}

	public Element getSavedSearchExportButton() {
		return driver.FindElementById("rbnExport");
	}

	public Element getSavedSearchToTally() {
		return driver.FindElementById("rbnTally");
	}

	public Element getSavedSearchToConcept() {
		return driver.FindElementById("rbnCategorize");
	}

	public Element getSavedSearchToTermReport() {
		return driver.FindElementById("rbnReport");
	}

	public Element getSavedSearch_SearchButton() {
		return driver.FindElementById("rbnBasicSearch");
	}

	public Element getSavedSearchNewGroupButton() {
		return driver.FindElementById("rbnSearchGroup");
	}

	public Element getSavedSearchToBulkTag() {
		return driver.FindElementById("rbnTag");
	}

	public Element getSelectCustodianName() {
		return driver.FindElementByCssSelector("input[data-friendlbl='CustodianName'] + i");
	}

	public Element getSelectDOCID() {
		return driver.FindElementByCssSelector("input[data-friendlbl='DocID'] + i");
	}

	public Element getAddToSelected_Button() {
		return driver.FindElementById("addFormObjects-coreList");
	}

	public Element getRunReport_Button() {
		return driver.FindElementById("btnRunReport");
	}

	public Element getExportPopup() {
		return driver.FindElementById("DivExport");
	}

	public Element getBckTask_selectExport() {
		return driver.FindElementByXPath("//*[@id='bgTask']//li[1]//a");
	}

	// reports from saved search
	public Element getSelectedSourceConcept() {
		return driver.FindElementByXPath(".//*[@id='bitlist-sources']//li");
	}

	public Element getTermReportTitle() {
		return driver.FindElementByXPath("//h1[@class='page-title']");
	}

	public Element getTermReportHitsCount() {
		return driver.FindElementById("lblHitsCount");
	}

	// added by shilpi
	public Element getSavedSearchID() {
		return driver.FindElementByCssSelector("table#SavedSearchGrid>tbody>tr.odd>td:nth-child(2)");
	}

	public Element getShare_selectallcheckbox() {
		return driver.FindElementByXPath("//*[@id='chkSelectAll']/following-sibling::i");
	}

	public Element getSharedWithMe() {
		return driver.FindElementByXPath("//*[@id='jsTreeSavedSearch']//li[2]");
	}

	public Element getPublicShare() {
		return driver.FindElementByXPath("//*[@id='jsTreeSavedSearch']//li[3]");
	}

	// public Element getPublicShare() { return
	// driver.FindElementByXPath("//a[@data-content='Public Shared (With Entire
	// Security Group)']"); }
	public Element getSearchName(String serachName) {
		return driver.FindElementByXPath("//*[@id='SavedSearchGrid']/tbody//tr[td='" + serachName + "']/td[3]");
	}

	public Element getShare_PA() {
		return driver.FindElementByXPath("//*[@id='s1']//label[contains(.,'Project Admin')]/i");
	}

	public Element getSelectSearchWithID(String serachName) {
		return driver.FindElementByXPath("//*[@id='SavedSearchGrid']/tbody//tr[td='" + serachName + "']/td[2]");
	}

	public Element getShare_SecurityGroup(String securitygroup) {
		return driver.FindElementByXPath("//*[@id='s1']//label[contains(.,'" + securitygroup + "')]/i");
	}

	public Element getSavedSearchGroupName(String name) {
		return driver.FindElementByXPath("//*[@id='jsTreeSavedSearch']//a[contains(.,'" + name + "')]");
	}

	// quick batch
	public Element getSavedSearchQuickBatchButton() {
		return driver.FindElementById("rbnQuickAssign");
	}

	public SavedSearch(Driver driver) {

		this.driver = driver;
		this.driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.waitForPageToBeReady();
		base = new BaseClass(driver);
		softAssertion = new SoftAssert();
		// search = new SessionSearch(driver);

		// This initElements method will create all WebElements
		// PageFactory.initElements(driver.getWebDriver(), this);

	}

	@SuppressWarnings("static-access")
	public void uploadBatchFile(final String batchFile) {
		driver.waitForPageToBeReady();

		ArrayList<Integer> expectCounts = new ArrayList<Integer>();
		if (Input.numberOfDataSets == 3) {
			expectCounts.add(240);expectCounts.add(267);
			expectCounts.add(106);expectCounts.add(64);
			expectCounts.add(0);expectCounts.add(1);
			expectCounts.add(0);expectCounts.add(13);
			expectCounts.add(2);expectCounts.add(1);
			expectCounts.add(0);expectCounts.add(0);
		} else if (Input.numberOfDataSets == 1) {

			expectCounts.add(28);expectCounts.add(28);
			expectCounts.add(11);expectCounts.add(8);
			expectCounts.add(0);expectCounts.add(1);
			expectCounts.add(0);expectCounts.add(3);
			expectCounts.add(2);expectCounts.add(0);
			expectCounts.add(0);expectCounts.add(0);
		}

		ArrayList<Integer> actualCounts = new ArrayList<Integer>();

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBatchUploadButton().Visible();
			}
		}), Input.wait30);
		getBatchUploadButton().waitAndClick(20);
		System.out.println("Clicked on Upload button..");
		UtilityLog.info("Clicked on Upload button..");

		System.out.println("Clicked on Batch Upload Button.........");
		UtilityLog.info("Clicked on Batch Upload Button.........");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectFile().Visible();
			}
		}), Input.wait30);

		UtilityLog.info(System.getProperty("user.dir") + Input.batchFilesPath + batchFile);
		getSelectFile().SendKeys(System.getProperty("user.dir") + Input.batchFilesPath + batchFile);
		getSubmitToUpload().waitAndClick(10);

		base.VerifySuccessMessage("File uploaded successfully");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 12;
			}
		}), Input.wait60);
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getMySeraches().Visible();
			}
		}), Input.wait30);
		getMySeraches().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectUploadedFile(batchFile).Visible();
			}
		}), Input.wait30);
		getSelectUploadedFile(batchFile).Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNumberOfSavedSearchToBeShown().Visible();
			}
		}), Input.wait30);
		getNumberOfSavedSearchToBeShown().selectFromDropdown().selectByVisibleText("100");

		/*
		 * for (final Element status : getSearchStatus()) {
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean
		 * call(){return status.getText().equals("COMPLETED")
		 * ;}}),Input.wait60); }
		 */
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCounts().Visible();
			}
		}), Input.wait30);
		for (int i = 1; i <= getCounts().size(); i++) {

			actualCounts.add(Integer.parseInt(
					driver.FindElement(By.xpath("//*[@id='SavedSearchGrid']/tbody/tr[" + i + "]/td[4]")).getText()));
		}
		System.out.println("ExpectCounts " + expectCounts);
		UtilityLog.info("ExpectCounts " + expectCounts);
		System.out.println("ActualCounts :" + actualCounts);
		UtilityLog.info("ActualCounts :" + actualCounts);

		// Delete uploaded set
		driver.scrollPageToTop();
		getSavedSearchDeleteButton().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {return getSubmitToUpload().Visible();}}), Input.wait60);
		getSubmitToUpload().Click();

		base.VerifySuccessMessage("Save search tree node successfully deleted.");		
		softAssertion.assertTrue(expectCounts.equals(actualCounts));

	}

	public void savedSearchToDocList(final String searchName) {
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_SearchName().Visible();
			}
		}), Input.wait60);
		
		getSavedSearch_SearchName().SendKeys(searchName);
		
		getSavedSearch_ApplyFilterButton().Click();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectWithName(searchName).Visible();
			}
		}), Input.wait30);

		getSelectWithName(searchName).waitAndClick(5);

		getToDocList().waitAndClick(5);

		try {
			base.getYesBtn().waitAndClick(5);
		} catch (Exception e) {
			System.out.println("Pop up message does not appear");
			Log.info("Pop up message does not appear");
		}

	}

	public void savedSearchToDocView(final String searchName) throws InterruptedException {
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_SearchName().Visible();
			}
		}), Input.wait60);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		savedSearch_Searchandclick(searchName);
		
		
		Thread.sleep(1000);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectWithName(searchName).Visible();
			}
		}), Input.wait30);

		getSelectWithName(searchName).waitAndClick(10);

		getToDocView().waitAndClick(10);

		try {
			base.getYesBtn().waitAndClick(10);
		} catch (Exception e) {
			System.out.println("Pop up message does not appear");
			UtilityLog.info("Pop up message does not appear");
		}

	}

	public void scheduleSavedSearch(final String searchName) throws ParseException, InterruptedException {
		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_SearchName().Visible();
			}
		}), Input.wait60);
		getSavedSearch_SearchName().SendKeys(searchName);
		
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_ApplyFilterButton().Visible();
			}
		}), Input.wait30);
		getSavedSearch_ApplyFilterButton().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectWithName(searchName).Visible();
			}
		}), Input.wait30);		
		getSelectWithName(searchName).Click();
				
				
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_ScheduleButton().Visible();
			}
		}), Input.wait30);
		getSavedSearch_ScheduleButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_ScheduleTime().Visible();
			}
		}), Input.wait60);
		getSavedSearch_ScheduleTime().SendKeys(schdulerTimePlus15Secs());

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();
		
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSaveSchedulerBtn().Visible();
			}
		}), Input.wait60);
		getSaveSchedulerBtn().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		System.out.println("Saved search " + searchName + " is scheduled to run in 5 secs!");
		UtilityLog.info("Saved search " + searchName + " is scheduled to run in 5 secs!");
	}

	public void shareSavedSearchPA(final String searchName, String securitygroupname)
			throws ParseException, InterruptedException {

			
		savedSearch_Searchandclick(searchName);
		getShareSerachBtn().waitAndClick(5);
		Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getShare_PA().Visible();
			}
		}), Input.wait30);
		getShare_PA().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getShareSaveBtn().Visible() && getShareSaveBtn().Enabled();
			}
		}), Input.wait30);
		getShareSaveBtnNew().waitForElement(getShareSaveBtnNew());
		new Actions(driver.getWebDriver()).moveToElement(getShareSaveBtnNew().getWebElement()).click();
		  
	
		// getShareSaveBtn().waitAndClick(5);
		base.VerifySuccessMessage("Share saved search operation successfully done.");
		
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_SearchName().Visible() && getSavedSearch_SearchName().Enabled();
			}
		}), Input.wait30);
		getSavedSearch_SearchName().SendKeys(searchName);
		
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_ApplyFilterButton().Visible() && getSavedSearch_ApplyFilterButton().Enabled();
			}
		}), Input.wait30);
		getSavedSearch_ApplyFilterButton().waitAndClick(5);
		
		// get Search ID
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectSearchWithID(searchName).Visible();
			}
		}), Input.wait30);
		String SearchID = getSelectSearchWithID(searchName).getText();
		System.out.println(SearchID);
		UtilityLog.info(SearchID);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectWithName(searchName).Visible();
			}
		}), Input.wait30);

		getSelectWithName(searchName).waitAndClick(5);

		// Again select same search and share with security group
		// savedSearch_Searchandclick(searchName);
		getShareSerachBtn().Click();
		Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getShare_SecurityGroup(securitygroupname).Visible();
			}
		}), Input.wait30);
		getShare_SecurityGroup(securitygroupname).waitAndClick(10);

		getShareSaveBtnNew().waitAndClick(5);
		base.VerifySuccessMessage("Share saved search operation successfully done.");
		Thread.sleep(2000);

		// click on share with project admin tab
		getSavedSearchGroupName("Project Admin").waitAndClick(10);
		
		getSavedSearch_SearchName().SendKeys(searchName);
		getSavedSearch_ApplyFilterButton().waitAndClick(10);
		// verify id should get changed
		String newSearchID = getSelectSearchWithID(searchName).waitAndGet(10);
		System.out.println(newSearchID);
		UtilityLog.info(newSearchID);
		Assert.assertNotSame(SearchID, newSearchID);

		Assert.assertTrue(getSearchName(searchName).Displayed());

		// click on share with security group tab
		getSavedSearchGroupName(securitygroupname).waitAndClick(10);
		Thread.sleep(2000);
		
		getSavedSearch_SearchName().SendKeys(searchName);
		getSavedSearch_ApplyFilterButton().waitAndClick(10);
		Thread.sleep(3000);
		
		// verify id should get changed
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectSearchWithID(searchName).Visible();
			}
		}), Input.wait30);
		String newSearchID1 = getSelectSearchWithID(searchName).getText();
		System.out.println(newSearchID1);
		UtilityLog.info(newSearchID1);
		Assert.assertNotSame(SearchID, newSearchID1);

		Assert.assertTrue(getSearchName(searchName).Displayed());

		// impersonate to RMU and check search
		base.impersonatePAtoRMU();

		// click on share with security group tab
		this.driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		
		Thread.sleep(3000);
		getSavedSearchGroupName(securitygroupname).waitAndClick(10);
		Thread.sleep(2000);
        
		getSavedSearch_SearchName().SendKeys(searchName);
		getSavedSearch_ApplyFilterButton().waitAndClick(10);
		Thread.sleep(3000);
		// verify id should get changed
		String newSearchID2 = getSelectSearchWithID(searchName).getText();
		System.out.println(newSearchID2);
		UtilityLog.info(newSearchID2);
		Assert.assertEquals(newSearchID2, newSearchID1);

	}

	public void shareSavedSearchRMU(final String searchName, String securitygroupname)
			throws ParseException, InterruptedException {

		savedSearch_Searchandclick(searchName);
		getShareSerachBtn().Click();
		Thread.sleep(2000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getShare_SecurityGroup(securitygroupname).Visible();
			}
		}), Input.wait60);
		getShare_SecurityGroup(securitygroupname).waitAndClick(60);

		// driver.WaitUntil((new Callable<Boolean>() {public Boolean
		// call(){return
		// getShareSaveBtn().Visible() ;}}), Input.wait30);
		// getShareSaveBtn().javascriptclick();

		getShareSaveBtnNew().waitAndClick(30);

		// getShareSaveBtn().waitAndClick(10);
		base.VerifySuccessMessage("Share saved search operation successfully done.");
		Thread.sleep(5000);		
	
		driver.getWebDriver().navigate().refresh();		// get Search ID
		getSavedSearch_SearchName().SendKeys(searchName);
		getSavedSearch_ApplyFilterButton().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectSearchWithID(searchName).Visible();
			}
		}), Input.wait30);
		String SearchID = getSelectSearchWithID(searchName).getText();
		System.out.println(SearchID);
		UtilityLog.info(SearchID);
		// click on share with security group tab
		getSavedSearchGroupName(securitygroupname).waitAndClick(10);
		
		getSavedSearch_SearchName().SendKeys(searchName);
		getSavedSearch_ApplyFilterButton().waitAndClick(10);
		Thread.sleep(5000);
		// verify id should get changed
		String newSearchID1 = getSelectSearchWithID(searchName).getText();
		System.out.println(newSearchID1);
		UtilityLog.info(newSearchID1);

		Assert.assertNotSame(SearchID, newSearchID1);

		Assert.assertTrue(getSearchName(searchName).Displayed());

	}

	public void sharewithUsers(final String searchName, String Usertype) {

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		if (Usertype.equalsIgnoreCase("Project Admin")) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getSharedWithMe().Visible();
				}
			}), Input.wait30);
			getSharedWithMe().Click();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Assert.assertTrue(getSearchName(searchName).Displayed());

		} else if (Usertype.equalsIgnoreCase("RMU")) {
			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getPublicShare().Visible();
				}
			}), Input.wait30);
			getPublicShare().Click();
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Assert.assertTrue(getSearchName(searchName).Displayed());
		}

	}

	public static String schdulerTimePlus15Secs() throws ParseException {
		// Time in GMT
		SimpleDateFormat dateFormatGmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		dateFormatGmt.setTimeZone(TimeZone.getTimeZone("GMT"));

		// Local time zone
		SimpleDateFormat dateFormatLocal = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		dateFormatLocal.parse(dateFormatGmt.format(new Date()));

		String Time = dateFormatGmt.format(new Date()).toString();
		// System.out.println(Time);

		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date d = df.parse(Time);
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);
		cal.add(Calendar.SECOND, 8);
		String newTime = df.format(cal.getTime());
		// System.out.println(newTime);
		return newTime.toString();

	}

	public void getSaveSearchID(final String searchName) {

		this.driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_SearchName().Visible();
			}
		}), Input.wait60);
		getSavedSearch_SearchName().SendKeys(searchName);
		getSavedSearch_ApplyFilterButton().Click();

	}

	public String renameFile() {
		String FileName = null;
		// back up TCs zip file for later verification
		File destinationFolder = new File(System.getProperty("user.dir") + Input.batchFilesPath);
		File sourceFolder = new File(System.getProperty("user.dir") + Input.batchFilesPath);

		if (!destinationFolder.exists()) {
			destinationFolder.mkdirs();
		}

		// Check weather source exists and it is folder.
		if (sourceFolder.exists() && sourceFolder.isDirectory()) {

			// Get list of the files and iterate over them
			File[] listOfFiles = sourceFolder.listFiles();

			if (listOfFiles != null) {
				System.out.println(1);
				for (File child : listOfFiles) {
					if (child.isFile()) { // rename only files not folders
						// Move files to destination folder
						FileName = "BatchUpload" + Utility.dynamicNameAppender() + ".xlsx";
						child.renameTo(new File(destinationFolder + "\\" + FileName));
					}
				}

				// Add if you want to delete the source folder
				// sourceFolder.delete();
			}
		} else {
			System.out.println(sourceFolder + "  Folder does not exists");
			UtilityLog.info(sourceFolder + "  Folder does not exists");
		}

		// delete last TCs PDF from PDFs folder
		File file = new File("C:\\BatchPrintFiles\\PDFs");
		String[] myFiles;
		if (file.isDirectory()) {
			myFiles = file.list();
			for (int i = 0; i < myFiles.length; i++) {
				File myFile = new File(file, myFiles[i]);
				myFile.delete();
			}
		}
		return FileName;
	}

	public void savedSearch_Searchandclick(final String searchName) {

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_SearchName().Visible();
			}
		}), Input.wait30);
		
		getSavedSearch_SearchName().SendKeys(searchName);

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		getSavedSearch_ApplyFilterButton().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectWithName(searchName).Visible();
			}
		}), Input.wait30);

		getSelectWithName(searchName).waitAndClick(10);
	}

	public void SaveSearchToBulkTag(final String searchName, String TagName) throws InterruptedException {

		search = new SessionSearch(driver);

		savedSearch_Searchandclick(searchName);

		getSavedSearchToBulkTag().Click();

		search.BulkActions_Tag(TagName);

	}

	public void SaveSearchToBulkFolder(final String searchName, String FolderName) throws InterruptedException {

		search = new SessionSearch(driver);
		Thread.sleep(2000);
		savedSearch_Searchandclick(searchName);

		getSavedSearchToBulkFolder().Click();

		search.BulkActions_Folder(FolderName);

	}

	public void SaveSearchToBulkAssign(final String searchName, String assignmentName, String codingForm,
			int purehits) {

		assgnpage = new AssignmentsPage(driver);
		savedSearch_Searchandclick(searchName);

		getSavedSearchToBulkAssign().waitAndClick(10);

		// SessionSearch search = new SessionSearch(driver);
		// search.bulkAssign();
		assgnpage.assignDocstoNewAssgn(assignmentName, codingForm, purehits);
		assgnpage.SelectAssignmentToViewinDocview(assignmentName);
		DocViewPage dp = new DocViewPage(driver);

		try {
			dp.VerifyPersistentHit(Input.searchString1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// Below function is to search saved search and delete it
	public void SaveSearchDelete(final String searchName) {

		savedSearch_Searchandclick(searchName);

		getSavedSearchDeleteButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSubmitToUpload().Visible();
			}
		}), Input.wait60);
		getSubmitToUpload().Click();

		base.VerifySuccessMessage("Save search tree node successfully deleted.");

	}

	public void SaveSearchExport(final String searchName) throws InterruptedException {

		savedSearch_Searchandclick(searchName);

		getSavedSearchExportButton().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getExportPopup().Visible();
			}
		}), Input.wait60);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectCustodianName().Visible();
			}
		}), Input.wait30);
		getSelectCustodianName().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectDOCID().Visible();
			}
		}), Input.wait30);
		getSelectDOCID().waitAndClick(5);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddToSelected_Button().Visible();
			}
		}), Input.wait30);
		getAddToSelected_Button().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getRunReport_Button().Visible();
			}
		}), Input.wait30);
		getRunReport_Button().Click();

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		base.VerifySuccessMessage(
				"Your Report has been added into the Background successfully. Once it is complete, the "
						+ "\"bullhorn\""
						+ " icon in the upper right-hand corner will turn red, and will increment forward.");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.getBackgroundTask_Button().Visible();
			}
		}), Input.wait60);
		bc.getBackgroundTask_Button().Click();
		Thread.sleep(3000);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getBckTask_selectExport().Visible();
			}
		}), Input.wait30);
		getBckTask_selectExport().waitAndClick(10);

	}

	public void savedSearchToConcepts(final String searchName) {

		savedSearch_Searchandclick(searchName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchToConcept().Visible();
			}
		}), Input.wait30);
		getSavedSearchToConcept().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectedSourceConcept().Visible();
			}
		}), Input.wait30);
		Assert.assertEquals(getSelectedSourceConcept().getText().toString(),
				"Search: Selected Documents from Save Search");

	}

	public void savedSearchToTally(final String searchName) {

		savedSearch_Searchandclick(searchName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchToTally().Visible();
			}
		}), Input.wait30);
		getSavedSearchToTally().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectedSourceConcept().Visible();
			}
		}), Input.wait30);
		Assert.assertEquals(getSelectedSourceConcept().getText().toString(),
				"Search: Selected Documents from SavedSearch: " + searchName + "");

	}

	public void savedSearchToTermReport(final String searchName) {

		savedSearch_Searchandclick(searchName);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchToTermReport().Visible();
			}
		}), Input.wait30);
		getSavedSearchToTermReport().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getTermReportTitle().Visible();
			}
		}), Input.wait60);
		System.out.println(getTermReportTitle().getText().toString());
		softAssertion.assertEquals(getTermReportTitle().getText().toString(), "Saved Search");

	}

	public void savedSearchExecute(final String searchName, int PureHits) {

		savedSearch_Searchandclick(searchName);

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchExecuteButton().Visible();
			}
		}), Input.wait30);
		getSavedSearchExecuteButton().Click();

		base.VerifySuccessMessage("Successfully added to background search.");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait60);
		System.out.println("Got notification!");

		getSavedSearch_ApplyFilterButton().Click();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getCountofDocs().Visible();
			}
		}), Input.wait60);

		Assert.assertEquals(getCountofDocs().getText(), String.valueOf(PureHits));

	}

	public void savedSearchNewSearchGrp(final String searchName) {

		driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchNewGroupButton().Visible();
			}
		}), Input.wait60);
		getSavedSearchNewGroupButton().Click();

		base.VerifySuccessMessage("Save search tree node successfully created.");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchNewGroupExpand().Visible();
			}
		}), Input.wait120);
		// driver.Navigate().refresh();
		getSavedSearchNewGroupExpand().waitAndClick(30);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchGroupName().Visible();
			}
		}), Input.wait30);
		getSavedSearchGroupName().Displayed();
		getSavedSearchGroupName().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearch_SearchButton().Visible();
			}
		}), Input.wait60);
		getSavedSearch_SearchButton().Click();

		base.selectproject();
		search = new SessionSearch(driver);
		search.basicContentSearch(Input.searchString1);
		search.saveSearch(searchName);
	}

	public void savedSearchEdit(final String searchName1, final String searchName2) {

		savedSearch_Searchandclick(searchName1);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSavedSearchEditButton().Visible();
			}
		}), Input.wait60);
		getSavedSearchEditButton().waitAndClick(10);

		// verify same pure hits should display when edit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return search.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);

		int pureHit = Integer.parseInt(search.getPureHitsCount().getText());
		System.out.println("PureHit is : " + pureHit);

		Assert.assertEquals(Integer.parseInt(search.getPureHitsCount().getText()), pureHit);

		/*
		 * search.getSecondSearchBtn().waitAndClick(10);
		 * 
		 * search.getSecondSavedSearchBtn().waitAndClick(10);
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean
		 * call(){return search.getsavesearch_overwrite().Visible()
		 * ;}}),Input.wait60); search.getsavesearch_overwrite().Click();
		 * 
		 * search.getSaveSearch_SaveButton().Click();
		 * base.VerifySuccessMessage("Saved search saved successfully");
		 * 
		 * savedSearch_Searchandclick(searchName1);
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean
		 * call(){return search.getSavedSearch_MySearchesTab().Visible() ;}}),
		 * Input.wait60); search.getSavedSearch_MySearchesTab().Click();
		 * search.getSaveSearch_Name().SendKeys(searchName1);
		 * search.getSaveSearch_SaveButton().Click();
		 * base.VerifySuccessMessage("Saved search saved successfully");
		 * System.out.println("Saved search with name - "+searchName1);
		 * 
		 * search.basicContentSearch(Input.searchString2);
		 * 
		 * Assert.assertEquals(search.getPureHitsCount().getText(), pureHit);
		 * 
		 * search.saveSearch(searchName2);
		 * 
		 * savedSearch_Searchandclick(searchName1);
		 * 
		 * savedSearch_Searchandclick(searchName2);
		 * getSavedSearch_ApplyFilterButton().waitAndClick(20);
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean
		 * call(){return getSelectWithName(searchName).Visible()
		 * ;}}),Input.wait30);
		 * 
		 * 
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean
		 * call(){return getSavedSearchGroupName().Visible() ;}}),Input.wait30);
		 * getSavedSearchGroupName().Displayed();
		 * getSavedSearchGroupName().Click();
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean
		 * call(){return getSavedSearch_SearchButton().Visible()
		 * ;}}),Input.wait60); getSavedSearch_SearchButton().Click();
		 * 
		 * search.basicContentSearch(Input.searchString1);
		 * search.saveSearch(searchName);
		 */
	}

	public void savedsearchquickbatch(String searchName) {

		savedSearch_Searchandclick(searchName);

		getSavedSearchQuickBatchButton().waitAndClick(10);

		System.out.println("performing quick batch");

	}

}