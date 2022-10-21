package pageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.WebElement;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import testScriptsSmoke.Input;

public class ProjectPage {

	Driver driver;
	BaseClass bc;

	public Element getAddProjectBtn() {
		return driver.FindElementById("btnAdd");
	}

	public Element getProjectName() {
		return driver.FindElementById("txtproject");
	}

	public Element getSelectEntity() {
		return driver.FindElementById("ddlEntityTypeList");
	}

	public Element getSelectEntityType() {
		return driver.FindElementById("ddlDomainType");
	}

	public Element getHCode() {
		return driver.FindElementById("txtHcode");
	}

	public Element getProjectDBServerDropdown() {
		return driver.FindElementByXPath("//label[@class='SourceLabel']/select[@id='ddlProjDBServer']");
	}

	public Element getProjectServerPath() {
		return driver.FindElementByXPath("//*[@id='ddlEntityWS']/option[1]");
	}

	public Element getIngestionserverpath() {
		return driver.FindElementByXPath("//*[@id='ddlLoadWS']/option[1]");
	}

	public Element getProductionserverpath() {
		return driver.FindElementByXPath(".//*[@id='ddlProductionWS']/option[1]");
	}

	public Element getIsActiveButton() {
		return driver.FindElementByXPath(".//*[@id='iss1']//label[@class='checkbox']/i");
	}

	public Element getProjectFolder() {
		return driver.FindElementById("txtProjectFold");
	}

	public Element getIngestionFolder() {
		return driver.FindElementById("txtIngestionFold");
	}

	public Element getProductionFolder() {
		return driver.FindElementById("txtProductionFold");
	}

	public Element getAddProject_SettingsTab() {
		return driver.FindElementByXPath(".//*[@id='hr2']//a[contains(.,'Settings')]");
	}

	public Element getNoOfDocuments() {
		return driver.FindElementById("txtMaxNoOfDocs");
	}

	public Element getButtonSaveProject() {
		return driver.FindElementByXPath("//button[@id='btnSaveProject']");
	}

	public Element getSearchProjectName() {
		return driver.FindElementById("txtProjectLabel");
	}

	public Element getProjectFilterButton() {
		return driver.FindElementById("btnFilter");
	}

	public Element getProjectName(String projectname) {
		return driver.FindElementByXPath(".//*[@id='ProjectDataTable']/tbody/tr[td='" + projectname + "']");
	}

	public Element getManageBtn() {
		return driver.FindElementByXPath("//i[@class='fa fa-lg fa-fw fa-users']//following::label[text()='Manage']");
	}

	public Element getManageProjectBtn() {
		return driver.FindElementByXPath("//a[@name='Project']");
	}

	public Element getAddProjectButton() {
		return driver.FindElementByXPath("//a[@id='btnAddProjectField']");
	}

	public Element getProjectFiledName() {
		return driver.FindElementById("FieldName");
	}

	public Element getPresentationField() {
		return driver.FindElementById("FieldLabel");
	}

	public Element getFieldClassificationDropDown() {
		return driver.FindElementById("ddlFieldClassification");
	}

	public Element getFieldDataTypeDropdown() {
		return driver.FindElementById("ddlProjectFieldGroupCode");
	}

	public Element getSearchableCheckBox() {
		return driver.FindElementByXPath("//input[@id='IsSearchable']/parent::label");
	}

	public Element getEditableCheckBox() {
		return driver.FindElementByXPath("//input[@id='IsEditable']/parent::label");
	}

	public Element getFieldLengthDropDown() {
		return driver.FindElementById("ddlDataTypeColumnLengthCode");
	}

	public Element getSaveBtn() {
		return driver.FindElementById("btnProjectFieldAddSave");
	}

	// Added by Mohan
	public Element getCopyProjectName() {
		return driver.FindElementById("ddlProjToCopy");
	}

	public Element getUserToggleButton() {
		return driver.FindElementByXPath("//div[@id='projCopyOption']//i[@id='IsactiveUsersObject']");
	}

	public Element getSavedSearchToggleButton() {
		return driver.FindElementByXPath("//div[@id='projCopyOption']//i[@id='IsactiveSavedSearches']");
	}

	public Element getAssignmnetToggleButton() {
		return driver.FindElementByXPath("//div[@id='projCopyOption']//i[@id='IsactiveAssignments']");
	}
	
	//Added by Aathith
	public ElementCollection getProjectTableHeader() {
		return driver.FindElementsByXPath("//*[@id='ProjectDataTable']/thead/tr/th");
	}
	
	public Element getColumValue(int colum) {
		return driver.FindElementByXPath("//*[@id='ProjectDataTable']/tbody/tr/td["+colum+"]");
	}
	
	public Element getEditBtn() {
		return driver.FindElementByXPath("//a[@class='btn btn-primary btn-xs' and text()='Edit']");
	}
	
	public Element getIsProjectActiveBtn() {
		return driver.FindElementByXPath("//i[@id='Isactive']");
	}
	
	public Element getGeneralTab() {
		return driver.FindElementById("liGeneralTab");
	}
	
	public Element getProjectTableHeaderValue(int column) {
		return driver.FindElementByXPath("//*[@id='ProjectDataTable']/thead/tr/th["+column+"]");
	}
	
	public Element getEditProject(String projectName) {
		return driver.FindElementByXPath("//*[@id='ProjectDataTable']/tbody/tr/td[text()='"+projectName+"']/../td/a[text()='Edit']");
	}
	
	public Element getVerifyInputValues(String value) {
		return driver.FindElementByXPath("//input[@value='"+value+"']");
	}
	
	public Element getFirmTextBox() {
		return driver.FindElementByXPath("//input[@id='Firm' and @type='text']");
	}
	
	public Element getCorpClientTextBox() {
		return driver.FindElementByXPath("//input[@id='CorpClient' and @type='text']");
	}
	public Element getProjectEdits() {
		return driver.FindElementByXPath("//table[@id='ProjectDataTable']//tbody//td/a");
	}
	public Element getErrorMsgForProjectName() {
		return driver.FindElementByXPath("//span[@id='txtproject-error']");
	}
	public Element getModifyValidateName(String modifyName) {
		return driver.FindElementByXPath("//table[@id='ProjectDataTable']//tbody//td[text()='"+modifyName+"']");
	}
	public Element getIntialBullCount() {
		return driver.FindElementByXPath("//i[@class='fa fa-bullhorn']/../b");
	}
	public Element getEditProjectWindow() {
		return driver.FindElementByXPath("//h1[normalize-space()='Edit Project']");
	}
	
	//Add by Aathith
	public Element getCancelButton() {
		return driver.FindElementById("btnCancelProject");
	}
	
	public Element getClinetNameInputBox() {
		return driver.FindElementById("txtClientEntityLabel");
	}
	
	public Element getPageTitle() {
		return driver.FindElementByClassName("page-title");
	}
	
	//added by arun
	public Element getKickOffAnalyticsCheckbox() {
		 return driver.FindElementByXPath("//input[@id='chkAutoAnalytics']//following-sibling::i");
	 }
	 public Element getOptionStatus() {
		 return driver.FindElementById("chkAutoIncrAnalytics");
	 }
	 public Element getIncrementalAnalyticsCheckbox() {
		 return driver.FindElementByXPath("//input[@id='chkAutoIncrAnalytics']//following-sibling::i");
	 }
	 
	 //added by sowndarya
	
	 public Element getBtnOK() {
			return driver.FindElementByXPath("//div[@class='MessageBoxButtonSection']//button[contains(text(),'OK')]");
		}
	
	 public Element getBgTaskPopup() {
			return driver.FindElementByXPath("//p[@class='pText']");
		}
	
	 public Element getMinLengthValue() {
			return driver.FindElementByXPath("//input[@id='docIDNum']");
		}
	
	
	 public Element getSelectClientName() {
			return driver.FindElementByXPath("//select[@id='ddlEntityTypeList']");
		}
	 
	 public Element getDomainName() {
			return driver.FindElementByXPath("//input[@id='entity_domainid']");
		}
	 	public Element getClientNameSaveBtn() {
			return driver.FindElementById("btnsave");
		}
	 
	 
	 	public Element getProcessingEngineTxt() {
			return driver.FindElementByXPath("//div[@id='domainentitysettingsdiv']//strong[contains(text(),'Processing Engine')]");
		}
		
		public Element getClientShortName() {
			return driver.FindElementByXPath("//div[@class='col-md-9']//input[@name='EntityCode']");
		}
		public Element getClientName() {
			return driver.FindElementByXPath("//div[@class='col-md-9']//input[@name='EntityLabel']");
		}

		public Element getAddNewClient() {
			return driver.FindElementById("btnAddEntity");
		}

		public Element getManageClientBtn() {
			return driver.FindElementByXPath("//a[contains(text(),' Clients')]");
		}

		public Element getEnableAnalyticsToogle() {
			return driver.FindElementByXPath("//input[@id='chkIsEnabledAnalytics']/../i");
		}
		public Element getComponentLabel() {
			return driver.FindElementByXPath("//div[@class='col-md-12 analyticsBox']//label//strong[text()='Components']");
		}
		public Element getProcessEngineTypeICE() {
			return driver.FindElementByXPath("//section[@id='divProcessingEngineConfig' and @style='display: block;']//input[@id='rdbICE']");
		}
		public Element getProcessEngineTypeNUIX() {
			return driver.FindElementByXPath("//section[@id='divProcessingEngineConfig' and @style='display: block;']//input[@id='rdbNUIX']");
		}

	// Annotation Layer added successfully
	public ProjectPage(Driver driver) {

		this.driver = driver;
		// this.driver.getWebDriver().get(Input.url+"Project/Project");
		driver.waitForPageToBeReady();
		bc = new BaseClass(driver);
	}

	public void AddDomainProject(String projectname, String clientname) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddProjectBtn().Visible();
			}
		}), Input.wait30);
		getAddProjectBtn().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProjectName().Visible();
			}
		}), Input.wait30);
		getProjectName().SendKeys(projectname);

		bc.waitForElement(getSelectEntityType());
		getSelectEntityType().selectFromDropdown().selectByVisibleText("Domain");

		bc.waitForElement(getSelectClientName());
		getSelectClientName().selectFromDropdown().selectByVisibleText(clientname);
		
		driver.scrollingToBottomofAPage();

		getProjectFolder().Clear();
		getProjectFolder().SendKeys("Automation");

		getIngestionFolder().Clear();
		getIngestionFolder().SendKeys("Automation");

		getProductionFolder().Clear();
		getProductionFolder().SendKeys("Automation");

		driver.scrollPageToTop();
		
		//temporily added
//		bc.mouseHoverOnElement(getManageProjectBtn());
//		bc.mouseHoverOnElement(getSelectClientName());
		
		bc.waitForElement(getAddProject_SettingsTab());
		getAddProject_SettingsTab().waitAndClick(10);

		bc.waitForElement(getNoOfDocuments());
		getNoOfDocuments().waitAndClick(10);
		getNoOfDocuments().SendKeys("20000");

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();
		System.out.println(Bgcount);
		UtilityLog.info(Bgcount);
		
		driver.scrollingToBottomofAPage();
		bc.waitForElement(getButtonSaveProject());
		getButtonSaveProject().waitAndClick(10);

		bc.VerifySuccessMessage(
				"Project is being created. A notification is provided to you once it is complete in the upper right hand corner.");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait120 + Input.wait60);
		System.out.println(bc.initialBgCount());
		UtilityLog.info(bc.initialBgCount());

	}

	public void AddNonDomainProject(String projectname, String hcode) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddProjectBtn().Visible();
			}
		}), Input.wait30);
		getAddProjectBtn().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProjectName().Visible();
			}
		}), Input.wait30);
		getProjectName().SendKeys(projectname);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectEntity().Visible();
			}
		}), Input.wait30);
		getSelectEntity().selectFromDropdown().selectByIndex(1);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getHCode().Visible();
			}
		}), Input.wait30);
		getHCode().SendKeys(hcode);

		driver.scrollingToBottomofAPage();

		getProjectFolder().Clear();
		getProjectFolder().SendKeys("Automation");

		getIngestionFolder().Clear();
		getIngestionFolder().SendKeys("Automation");

		getProductionFolder().Clear();
		getProductionFolder().SendKeys("Automation");

		driver.scrollPageToTop();
		
		//temporily added
//		bc.mouseHoverOnElement(getManageProjectBtn());
//		bc.mouseHoverOnElement(getSelectClientName());
		
		bc.waitForElement(getAddProject_SettingsTab());
		getAddProject_SettingsTab().waitAndClick(10);
		

		bc.waitForElement(getNoOfDocuments());
		getNoOfDocuments().waitAndClick(10);
		getNoOfDocuments().SendKeys("20000");

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();
		System.out.println(Bgcount);
		UtilityLog.info(Bgcount);
		
		driver.scrollingToBottomofAPage();
		bc.waitForElement(getButtonSaveProject());
		getButtonSaveProject().waitAndClick(10);

		bc.waitTime(5);
		bc.VerifySuccessMessage(
				"Project is being created. A notification is provided to you once it is complete in the upper right hand corner.");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait120 + Input.wait60);
		System.out.println(bc.initialBgCount());
		UtilityLog.info(bc.initialBgCount());
	}

	/**
	 * @author Indium-Baskar date: 10/8/2021 Modified date: 23/8/2021 Modified
	 *         by:Baskar.
	 * @Description:project field creation with INT Datatype
	 */

	public void addCustomFieldProjectDataType(String projectName, String dataType) {
		bc.waitForElement(getManageBtn());
		bc.waitTillElemetToBeClickable(getManageBtn());
		getManageBtn().Click();
		bc.waitForElement(getManageProjectBtn());
		bc.waitTillElemetToBeClickable(getManageProjectBtn());
		getManageProjectBtn().Click();
		bc.waitForElement(getAddProjectButton());
		bc.waitTillElemetToBeClickable(getAddProjectButton());
		getAddProjectButton().Click();
		bc.waitForElement(getProjectFiledName());
		getProjectFiledName().SendKeys(projectName);
		bc.waitForElement(getPresentationField());
		getPresentationField().SendKeys(projectName);
		bc.waitForElement(getFieldClassificationDropDown());
		getFieldClassificationDropDown().selectFromDropdown().selectByVisibleText("Doc Basic");
		bc.waitForElement(getFieldDataTypeDropdown());
		getFieldDataTypeDropdown().selectFromDropdown().selectByVisibleText(dataType);
		bc.waitForElement(getFieldLengthDropDown());
		getFieldLengthDropDown().selectFromDropdown().selectByVisibleText("Small - 50 Characters");
		bc.waitForElement(getSearchableCheckBox());
		bc.waitTillElemetToBeClickable(getSearchableCheckBox());
		getSearchableCheckBox().Click();
		bc.waitForElement(getEditableCheckBox());
		bc.waitTillElemetToBeClickable(getEditableCheckBox());
		getEditableCheckBox().Click();
		bc.waitForElement(getSaveBtn());
		bc.waitTillElemetToBeClickable(getSaveBtn());
		getSaveBtn().Click();
	}

	/**
	 * @author Indium-Baskar date: 29/10/2021 Modified date: 23/8/2021 Modified
	 *         by:Baskar.
	 * @Description:project field creation with Datatype
	 */

	public void addCustomFieldDataType(String projectName, String dataType) {
		bc.waitForElement(getManageBtn());
		bc.waitTillElemetToBeClickable(getManageBtn());
		getManageBtn().Click();
		bc.waitForElement(getManageProjectBtn());
		bc.waitTillElemetToBeClickable(getManageProjectBtn());
		getManageProjectBtn().Click();
		bc.waitForElement(getAddProjectButton());
		bc.waitTillElemetToBeClickable(getAddProjectButton());
		getAddProjectButton().Click();
		bc.waitForElement(getProjectFiledName());
		getProjectFiledName().SendKeys(projectName);
		bc.waitForElement(getPresentationField());
		getPresentationField().SendKeys(projectName);
		bc.waitForElement(getFieldClassificationDropDown());
		getFieldClassificationDropDown().selectFromDropdown().selectByVisibleText("Doc Basic");
		bc.waitForElement(getFieldDataTypeDropdown());
		getFieldDataTypeDropdown().selectFromDropdown().selectByVisibleText(dataType);
		bc.waitForElement(getSearchableCheckBox());
		bc.waitTillElemetToBeClickable(getSearchableCheckBox());
		getSearchableCheckBox().Click();
		bc.waitForElement(getEditableCheckBox());
		bc.waitTillElemetToBeClickable(getEditableCheckBox());
		getEditableCheckBox().Click();
		bc.waitForElement(getSaveBtn());
		bc.waitTillElemetToBeClickable(getSaveBtn());
		getSaveBtn().Click();
	}

	/**
	 * @author Indium-Baskar date: 11/24/2021 Modified date: N/AModified by:Baskar.
	 */
//    Reusable method for clicking projectfield
	public void clickingManageButton() {
		driver.waitForPageToBeReady();
		bc.waitForElement(getManageBtn());
		bc.waitTillElemetToBeClickable(getManageBtn());
		getManageBtn().Click();
		bc.waitForElement(getManageProjectBtn());
		bc.waitTillElemetToBeClickable(getManageProjectBtn());
		getManageProjectBtn().Click();
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Indium-Baskar date: 11/24/2021 Modified date: N/AModified by:Baskar.
	 * @Description:project field creation with INT Datatype using integer
	 */

	public void addMetaDataFieldUsingIntergerType(String projectName, String dataType, String classify,
			String integerType) {
		driver.waitForPageToBeReady();
		bc.waitForElement(getAddProjectButton());
		bc.waitTillElemetToBeClickable(getAddProjectButton());
		getAddProjectButton().Click();
		bc.waitForElement(getProjectFiledName());
		getProjectFiledName().SendKeys(projectName);
		bc.waitForElement(getPresentationField());
		getPresentationField().SendKeys(projectName);
		bc.waitForElement(getFieldClassificationDropDown());
		getFieldClassificationDropDown().selectFromDropdown().selectByVisibleText(classify);
		bc.waitForElement(getFieldDataTypeDropdown());
		getFieldDataTypeDropdown().selectFromDropdown().selectByVisibleText(dataType);
		bc.waitForElement(getFieldLengthDropDown());
		getFieldLengthDropDown().selectFromDropdown().selectByVisibleText(integerType);
		bc.waitForElement(getSearchableCheckBox());
		bc.waitTillElemetToBeClickable(getSearchableCheckBox());
		getSearchableCheckBox().Click();
		bc.waitForElement(getEditableCheckBox());
		bc.waitTillElemetToBeClickable(getEditableCheckBox());
		getEditableCheckBox().Click();
		bc.waitForElement(getSaveBtn());
		bc.waitTillElemetToBeClickable(getSaveBtn());
		getSaveBtn().Click();
		bc.CloseSuccessMsgpopup();
		driver.waitForPageToBeReady();
	}

	/**
	 * @author Gopinath
	 * @Description : Method for navigating to projects page
	 */
	public void navigateToProductionPage() {
		try {
			driver.getWebDriver().get(Input.url + "Project/Project");
		} catch (Exception e) {
			e.printStackTrace();
			bc.failedStep("Exception occured while navigating to project page" + e.getMessage());
		}
	}

	
	/**
	 * @author Mohan
	 * @Description : Project to be copied
	 */
	public void selectProjectToBeCopied(String projectname, String clientname, String copyProjectName,
			String toggleNo) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddProjectBtn().Visible();
			}
		}), Input.wait30);
		getAddProjectBtn().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProjectName().Visible();
			}
		}), Input.wait30);
		getProjectName().SendKeys(projectname);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectEntityType().Visible();
			}
		}), Input.wait30);
		getSelectEntityType().selectFromDropdown().selectByVisibleText("Domain");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectEntity().Visible();
			}
		}), Input.wait30);
		getSelectEntity().selectFromDropdown().selectByVisibleText(clientname);

		driver.scrollingToBottomofAPage();

		bc.waitForElement(getCopyProjectName());
		getCopyProjectName().selectFromDropdown().selectByVisibleText(copyProjectName);

		if (toggleNo.equals("1")) {
			String user = getUserToggleButton().GetAttribute("data-swchoff-text");
			if (user.contains("OFF")) {
				getUserToggleButton().waitAndClick(5);
			}

		}

		else if (toggleNo.equals("2")) {
			String savedSearch = getSavedSearchToggleButton().GetAttribute("data-swchoff-text");
			if (savedSearch.contains("OFF")) {
				getSavedSearchToggleButton().waitAndClick(5);
			}

		}

		else if (toggleNo.equals("3")) {
			String assignment = getAssignmnetToggleButton().GetAttribute("data-swchoff-text");
			if (assignment.contains("OFF")) {
				getAssignmnetToggleButton().waitAndClick(5);
			}

		}

		else if (toggleNo.equals("4")) {
			String user = getUserToggleButton().GetAttribute("data-swchoff-text");
			if (user.contains("OFF")) {
				getUserToggleButton().waitAndClick(5);
				getSavedSearchToggleButton().waitAndClick(5);
			} else if (toggleNo.equals("0")) {
				bc.stepInfo("The test case doesn't need Toggle to be enabled");
			}

		} else {
			bc.stepInfo("The required toggle is not visible");
		}

		getProjectFolder().Clear();
		getProjectFolder().SendKeys("Automation");

		getIngestionFolder().Clear();
		getIngestionFolder().SendKeys("Automation");

		getProductionFolder().Clear();
		getProductionFolder().SendKeys("Automation");

		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddProject_SettingsTab().Visible();
			}
		}), Input.wait30);
		getAddProject_SettingsTab().waitAndClick(10);
		;

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getNoOfDocuments().Visible();
			}
		}), Input.wait30);
		getNoOfDocuments().SendKeys("999");

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();
		System.out.println(Bgcount);
		UtilityLog.info(Bgcount);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getButtonSaveProject().Visible();
			}
		}), Input.wait30);
		driver.scrollingToBottomofAPage();
		getButtonSaveProject().Click();

		bc.VerifySuccessMessage(
				"Project is being created. A notification is provided to you once it is complete in the upper right hand corner.");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait120 + Input.wait60);
		System.out.println(bc.initialBgCount());
		UtilityLog.info(bc.initialBgCount());

	}
	
	/**
	 * @author Aathith.Senthilkumar
	 * @param projectName
	 * @Description filter the project using project name
	 */
	public void filterTheProject(String projectName) {
	   	this.driver.getWebDriver().get(Input.url+"Project/Project");
	 	  
	 	 bc.waitForElement(getSearchProjectName());
	 	 getSearchProjectName().SendKeys(projectName);
	 	 
	 	bc.waitForElement(getProjectFilterButton());
	 	bc.waitTillElemetToBeClickable(getProjectFilterButton());
	 	getProjectFilterButton().waitAndClick(10);
	 	driver.waitForPageToBeReady();
	 	bc.stepInfo(projectName+" was filtered");
	 	
   }
	
	/**
	 * @author Aathith.Senthilkumar
	 * @param projectName
	 * @Description inactive a project
	 */
	public void inActiveProject(String projectName) {
		filterTheProject(projectName);
		driver.waitForPageToBeReady();
		bc.waitForElement(getEditBtn());
		getEditBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		bc.waitForElement(getIsProjectActiveBtn());
		getIsProjectActiveBtn().waitAndClick(10);
		driver.scrollingToBottomofAPage();
		bc.waitForElement(getButtonSaveProject());
		getButtonSaveProject().waitAndClick(10);
	}
	
	/**
	 * @author Aathith.Senthilkumar
	 * @Description wait till get notification for project creation
	 */
	public void waitTillProjectCreated() {
		if(bc.getRedBullHornIcon().isElementAvailable(300)) {
			bc.passedStep("project was created and notification received");
		}else {
			bc.failedStep("notification still not received,it's get too much time");
		}
	}
	
	/**
	 * @author VijayaRani
	 * @Description To select Nondomain as Client type and clone a project
	 */
	public void selectProjectToBeCopiedNonDomain(String projectname,String clientName,String projectName, String hcode,String toggleNo) {

        driver.WaitUntil((new Callable<Boolean>() {
            public Boolean call() {
                return getAddProjectBtn().Visible();
            }
        }), Input.wait30);
        getAddProjectBtn().Click();

        driver.WaitUntil((new Callable<Boolean>() {
            public Boolean call() {
                return getProjectName().Visible();
            }
        }), Input.wait30);
        getProjectName().SendKeys(projectname);

        driver.WaitUntil((new Callable<Boolean>() {
            public Boolean call() {
                return getSelectEntityType().Visible();
            }
        }), Input.wait30);
        getSelectEntityType().selectFromDropdown().selectByVisibleText("Not a Domain");

        driver.WaitUntil((new Callable<Boolean>() {
            public Boolean call() {
                return getSelectEntity().Visible();
            }
        }), Input.wait30);
        getSelectEntity().selectFromDropdown().selectByVisibleText(clientName);

        driver.WaitUntil((new Callable<Boolean>() {
            public Boolean call() {
                return getHCode().Visible();
            }
        }), Input.wait30);
        getHCode().SendKeys(hcode);

        driver.scrollingToBottomofAPage();

        bc.waitForElement(getCopyProjectName());
        getCopyProjectName().selectFromDropdown().selectByVisibleText(projectName);

        if (toggleNo.equals("1")) {
            String user = getUserToggleButton().GetAttribute("data-swchoff-text");
            if (user.contains("OFF")) {
                getUserToggleButton().waitAndClick(5);
            }

        }

        else if (toggleNo.equals("2")) {
            String savedSearch = getSavedSearchToggleButton().GetAttribute("data-swchoff-text");
            if (savedSearch.contains("OFF")) {
                getSavedSearchToggleButton().waitAndClick(5);
            }

        }

        else if (toggleNo.equals("3")) {
            String assignment = getAssignmnetToggleButton().GetAttribute("data-swchoff-text");
            if (assignment.contains("OFF")) {
                getAssignmnetToggleButton().waitAndClick(5);
            }

        }

        else if (toggleNo.equals("4")) {
            String user = getUserToggleButton().GetAttribute("data-swchoff-text");
            if (user.contains("OFF")) {
                getUserToggleButton().waitAndClick(5);
                getSavedSearchToggleButton().waitAndClick(5);
            } else if (toggleNo.equals("0")) {
                bc.stepInfo("The test case doesn't need Toggle to be enabled");
            }

        } else {
            bc.stepInfo("The required toggle is not visible");
        }


        driver.scrollingToBottomofAPage();

        driver.WaitUntil((new Callable<Boolean>() {
            public Boolean call() {
                return getProjectDBServerDropdown().Visible();
            }
        }), Input.wait30);
        getProjectDBServerDropdown().selectFromDropdown().selectByIndex(1);

        driver.WaitUntil((new Callable<Boolean>() {
            public Boolean call() {
                return getProjectServerPath().Visible();
            }
        }), Input.wait30);
        getProjectServerPath().Click();

        driver.WaitUntil((new Callable<Boolean>() {
            public Boolean call() {
                return getIngestionserverpath().Visible();
            }
        }), Input.wait30);
        getIngestionserverpath().selectFromDropdown().selectByIndex(0);
        getIngestionserverpath().selectFromDropdown().selectByIndex(1);

        driver.WaitUntil((new Callable<Boolean>() {
            public Boolean call() {
                return getProductionserverpath().Visible();
            }
        }), Input.wait30);
        getProductionserverpath().Click();

        getProjectFolder().SendKeys("Automation");

        getIngestionFolder().SendKeys("Automation");

        getProductionFolder().SendKeys("Automation");

        driver.scrollPageToTop();
        driver.WaitUntil((new Callable<Boolean>() {
            public Boolean call() {
                return getAddProject_SettingsTab().Visible();
            }
        }), Input.wait30);
        getAddProject_SettingsTab().waitAndClick(10);
        ;

        driver.WaitUntil((new Callable<Boolean>() {
            public Boolean call() {
                return getNoOfDocuments().Visible();
            }
        }), Input.wait30);
        getNoOfDocuments().SendKeys("999");

        final BaseClass bc = new BaseClass(driver);
        final int Bgcount = bc.initialBgCount();
        System.out.println(Bgcount);
        UtilityLog.info(Bgcount);
        driver.WaitUntil((new Callable<Boolean>() {
            public Boolean call() {
                return getButtonSaveProject().Visible();
            }
        }), Input.wait30);
        driver.scrollingToBottomofAPage();
        getButtonSaveProject().Click();

}
	
	/**
	 * @author Aathith.Senthilkumar
	 * @param projectname
	 * @Description add a domian project for DA user
	 */
	public void AddDomainProjectViaDaUser(String projectname) {
		navigateToProductionPage();
		bc.waitForElement(getAddProjectBtn());
		getAddProjectBtn().waitAndClick(10);
		bc.waitForElement(getProjectName());
		 getProjectName().SendKeys(projectname);
		 driver.scrollingToBottomofAPage();
		 driver.waitForPageToBeReady();
		 bc.waitForElement(getButtonSaveProject());
		 getButtonSaveProject().waitAndClick(10);
		 driver.waitForPageToBeReady();
		 bc.stepInfo(projectname+ " was added" );
	}
	
	/**
	 * @author Aathith.Senthilkumar
	 * @return temporay Project name
	 * @Description check that temporary project is available, is yes return project name else create project
	 */
	public String checkTempDomainProjectIsAvailable() {
		String tempProject = "TemporayDomainProject";
		filterTheProject(tempProject);
		if(!bc.text(tempProject).isElementAvailable(3)) {
			bc.clearBullHornNotification();
			System.out.println(bc.getCurrentLoginedUserRole());
			if(bc.getCurrentLoginedUserRole().equalsIgnoreCase(Input.SystemAdministrator)) {
				AddDomainProject(tempProject, Input.domainName);
			}else {
				AddDomainProjectViaDaUser(tempProject);
			}
			bc.waitForNotification();
		}
		return tempProject;
		
	}
	
	/**
	 * @author Aathith.Senthilkumar
	 * @param project
	 * @Description click edit the Project
	 */
	public void editProject(String project) {
		driver.waitForPageToBeReady();
		filterTheProject(project);
		bc.waitForElement(getEditProject(project));
		getEditProject(project).waitAndClick(10);
		driver.waitForPageToBeReady();
		bc.stepInfo(project+" was clicked on edit");
	}
	
	/**
	 * @author Vijaya.Rani
	 * @Description : Project to be copied IN DA
	 */
	public void selectProjectToBeCopiedInDA(String projectname, String copyProjectName,
			String toggleNo) {

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddProjectBtn().Visible();
			}
		}), Input.wait30);
		getAddProjectBtn().Click();
		
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getProjectName().Visible();
			}
		}), Input.wait30);
		getProjectName().SendKeys(projectname);

		driver.scrollingToBottomofAPage();

		bc.waitForElement(getCopyProjectName());
		getCopyProjectName().selectFromDropdown().selectByVisibleText(copyProjectName);

		if (toggleNo.equals("1")) {
			String user = getUserToggleButton().GetAttribute("data-swchoff-text");
			if (user.contains("OFF")) {
				getUserToggleButton().waitAndClick(5);
			}

		}

		else if (toggleNo.equals("2")) {
			String savedSearch = getSavedSearchToggleButton().GetAttribute("data-swchoff-text");
			if (savedSearch.contains("OFF")) {
				getSavedSearchToggleButton().waitAndClick(5);
			}

		}

		else if (toggleNo.equals("3")) {
			String assignment = getAssignmnetToggleButton().GetAttribute("data-swchoff-text");
			if (assignment.contains("OFF")) {
				getAssignmnetToggleButton().waitAndClick(5);
			}

		}

		else if (toggleNo.equals("4")) {
			String user = getUserToggleButton().GetAttribute("data-swchoff-text");
			if (user.contains("OFF")) {
				getUserToggleButton().waitAndClick(5);
				getSavedSearchToggleButton().waitAndClick(5);
			} else if (toggleNo.equals("0")) {
				bc.stepInfo("The test case doesn't need Toggle to be enabled");
			}

		} else {
			bc.stepInfo("The required toggle is not visible");
		}

		final BaseClass bc = new BaseClass(driver);
		final int Bgcount = bc.initialBgCount();
		System.out.println(Bgcount);
		UtilityLog.info(Bgcount);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getButtonSaveProject().Visible();
			}
		}), Input.wait30);
		driver.scrollingToBottomofAPage();
		getButtonSaveProject().Click();

		bc.VerifySuccessMessage(
				"Project is being created. A notification is provided to you once it is complete in the upper right hand corner.");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return bc.initialBgCount() == Bgcount + 1;
			}
		}), Input.wait120 + Input.wait60);
		System.out.println(bc.initialBgCount());
		UtilityLog.info(bc.initialBgCount());

	}
	
	/**
	 * @author Aathith.Senthilkumar
	 * @param projectname
	 * @param clientname
	 * @Desctiprion enter project details without click save button
	 */
	public void AddDomainProjectDetailsWithoutSave(String projectname, String clientname) {
		
		driver.waitForPageToBeReady();
		navigateToProductionPage();

		bc.waitForElement(getAddProjectBtn());
		getAddProjectBtn().waitAndClick(5);
		
		bc.waitForElement(getProjectName());
		getProjectName().SendKeys(projectname);

		bc.waitForElement(getSelectEntityType());
		getSelectEntityType().selectFromDropdown().selectByVisibleText("Domain");

		bc.waitForElement(getSelectEntity());
		getSelectEntity().selectFromDropdown().selectByVisibleText(clientname);

		driver.scrollingToBottomofAPage();

		getProjectFolder().Clear();
		getProjectFolder().SendKeys("Automation");

		getIngestionFolder().Clear();
		getIngestionFolder().SendKeys("Automation");

		getProductionFolder().Clear();
		getProductionFolder().SendKeys("Automation");

//		driver.scrollPageToTop();
//		bc.waitForElement(getAddProject_SettingsTab());
//		getAddProject_SettingsTab().waitAndClick(5);

//		bc.waitForElement(getNoOfDocuments());
//		getNoOfDocuments().SendKeys("20000");
		bc.stepInfo("project details added ");

	}
	
	/**
	 * @author Aathith.Senthilkumar
	 * @param ClinetName
	 * @Description filter table using clinet name
	 */
	public void filterTByClientName(String ClinetName) {
	 	driver.waitForPageToBeReady();
	 	bc.waitForElement(getClinetNameInputBox());
	 	getClinetNameInputBox().SendKeys(ClinetName);
	 	 
	 	bc.waitForElement(getProjectFilterButton());
	 	bc.waitTillElemetToBeClickable(getProjectFilterButton());
	 	getProjectFilterButton().waitAndClick(5);
	 	driver.waitForPageToBeReady();
	 	bc.stepInfo(ClinetName+" clinet name was filtered");
	 	
   }
	
	/**
	 * @author Aathith.Senthilkumar
	 * @Description clear filters in project table
	 */
	public void clearFilter() {
		driver.waitForPageToBeReady();
		bc.waitForElement(getSearchProjectName());
		getSearchProjectName().Clear();
		bc.waitForElement(getClinetNameInputBox());
		getClinetNameInputBox().Clear();
		bc.waitForElement(getProjectFilterButton());
		getProjectFilterButton().waitAndClick(5);
		driver.waitForPageToBeReady();
		bc.stepInfo("filters are cleared");
	}
	
	/**
	 * @author: Arun Created Date: 03/10/2022 Modified by: NA Modified Date: NA
	 * @description: this method will select the project and click edit button
	 *               
	 */
	public void selectProjectAndEdit(String projectName) {
		filterTheProject(projectName);
		bc.waitForElement(getEditBtn());
		getEditBtn().waitAndClick(10);
		driver.waitForPageToBeReady();
		if(getProjectName().isElementAvailable(15)) {
			bc.passedStep("Edit project page section displayed");
		}
		else {
			bc.failedStep("edit project section not displayed");
		}
		
	}
	
	/**
	 * @author: Arun Created Date: 03/10/2022 Modified by: NA Modified Date: NA
	 * @description: this method will disable/enable kickoff analytics
	 *               
	 */
	public void disableOrEnableKickOffAnalytics(String project,String action) {
		//select project and edit
		selectProjectAndEdit(project);
		driver.scrollingToBottomofAPage();
		bc.waitForElement(getKickOffAnalyticsCheckbox());
		String status = getOptionStatus().GetAttribute("disabled");
		bc.stepInfo("status of kickoff analytics :" + status);
		if(action.equalsIgnoreCase("Disable")) {
			if(status=="true") {
				bc.passedStep("kick off analytics disabled");
			}
			else if(status==null) {
				getKickOffAnalyticsCheckbox().waitAndClick(10);
				bc.passedStep("kick off analytics disabled");
			}	
		}
		else if(action.equalsIgnoreCase("Enable")) {
			if(status==null) {
				bc.waitForElement(getIncrementalAnalyticsCheckbox());
				getIncrementalAnalyticsCheckbox().waitAndClick(10);
				bc.passedStep("kick off analytics already in enabled state");
			}
			else {
				getKickOffAnalyticsCheckbox().waitAndClick(10);
				bc.waitForElement(getIncrementalAnalyticsCheckbox());
				getIncrementalAnalyticsCheckbox().waitAndClick(10);
				bc.passedStep("kick off analytics enabled");
			}
		}
		bc.waitForElement(getButtonSaveProject());
		getButtonSaveProject().waitAndClick(10);
		bc.VerifySuccessMessage("Project updated successfully");
	}

	/**
	 * @author sowndarya.
	 */
	public void navigateToClientFromHomePage() {
		driver.waitForPageToBeReady();
		bc.waitForElement(getManageBtn());
		bc.waitTillElemetToBeClickable(getManageBtn());
		getManageBtn().Click();
		bc.waitForElement(getManageClientBtn());
		bc.waitTillElemetToBeClickable(getManageClientBtn());
		getManageClientBtn().Click();
		driver.waitForPageToBeReady();
	}

	/**
	 * @author sowndarya.
	 */
	public void addNewClient(String clientName,String shortName,String type) {
		
		driver.waitForPageToBeReady();
		bc.waitForElement(getAddNewClient());
		getAddNewClient().ScrollTo();
		getAddNewClient().waitAndClick(10);
		
		bc.waitForElement(getClientName());
		getClientName().SendKeys(clientName);
		
		bc.waitForElement(getClientShortName());
		getClientShortName().SendKeys(shortName);
		
		bc.waitForElement(getSelectEntity());
		getSelectEntity().waitAndClick(10);
		getSelectEntity().selectFromDropdown().selectByVisibleText(type);
		
		driver.waitForPageToBeReady();
		
		if (getSelectEntity().getText().contains("Domain")) {
			String domainName="D"+Utility.dynamicRandomNumberAppender();
			bc.waitForElement(getDomainName());
			getDomainName().SendKeys(domainName);
			driver.scrollingToBottomofAPage();

			bc.waitForElement(getProjectDBServerDropdown());
		getProjectDBServerDropdown().selectFromDropdown().selectByIndex(1);

		bc.waitForElement(getProjectServerPath());
		getProjectServerPath().waitAndClick(10);

		bc.waitForElement(getIngestionserverpath());
		getIngestionserverpath().waitAndClick(10);

		bc.waitForElement(getProductionserverpath());
		getProductionserverpath().waitAndClick(10);
		
	}
		bc.waitForElement(getClientNameSaveBtn());
		getClientNameSaveBtn().waitAndClick(10);
		
		bc.waitTime(2);
		bc.VerifySuccessMessage("The new client was added successfully");
		bc.CloseSuccessMsgpopup();
	}

	/**
	 * @author sowndarya.
	 */
	public void addNewClient_NonDomainProject(String clientName,String shortName,String type) {
		
		driver.waitForPageToBeReady();
		bc.waitForElement(getAddNewClient());
		getAddNewClient().ScrollTo();
		getAddNewClient().waitAndClick(10);
		
		bc.waitForElement(getClientName());
		getClientName().SendKeys(clientName);
		
		bc.waitForElement(getClientShortName());
		getClientShortName().SendKeys(shortName);
		
		bc.waitForElement(getSelectEntity());
		getSelectEntity().waitAndClick(10);
		getSelectEntity().selectFromDropdown().selectByVisibleText(type);
		
		bc.waitForElement(getClientNameSaveBtn());
		getClientNameSaveBtn().waitAndClick(10);
		
		bc.waitTime(2);
		bc.VerifySuccessMessage("The new client was added successfully");
		bc.CloseSuccessMsgpopup();
	}
	/**
	 * @author Brundha.T
	 * @param ColValue
	 * Description:verifying the in domain column header
	 */
public void VerifyingColumnValuesInProjects(String ColValue) throws InterruptedException {
	
	ArrayList<String> ColumnHeaderValue=new ArrayList<>();
	List<WebElement> ListOfEle = getProjectTableHeader().FindWebElements();
	for(int i=0;i<ListOfEle.size();i++) {
		   String ColText=ListOfEle.get(i).getText();
		    ColumnHeaderValue.add(ColText);
	}
	 System.out.println(ColumnHeaderValue);
	if(ColumnHeaderValue.contains(ColValue)) {
		bc.passedStep("In Domain is displayed expected in Project column");
	}else {
		bc.failedStep("In Domain value is not in Projects column");
	}
	bc.stepInfo("verifying the position of "+ColValue+"");
	 int  Prjtcodeposition=ColumnHeaderValue.indexOf("PROJECT CODE")+1;
	 System.out.println(Prjtcodeposition);
	 int  InDomain=ColumnHeaderValue.indexOf(ColValue);
	 System.out.println(InDomain);
	 int  PrjtActive=ColumnHeaderValue.indexOf("IS PROJECT ACTIVE")-1;
	 System.out.println(PrjtActive);
	
	if (Prjtcodeposition==InDomain && PrjtActive==InDomain) {
		bc.passedStep("InDomain position inbetween Project code and is project active");
	} else {
		bc.failedStep("Postion is not maintained");
	}
	
}

}
