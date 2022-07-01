package pageFactory;

import java.util.concurrent.Callable;
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
		return driver.FindElementByXPath("//*[@id='ddlProjectWS']/option[1]");
	}

	public Element getIngestionserverpath() {
		return driver.FindElementByXPath(".//*[@id='ddlLoadWS']");
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
		return driver.FindElementById("btnSaveProject");
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
		getNoOfDocuments().SendKeys("20000");

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
		/*
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getIsActiveButton().Visible() ;}}), Input.wait30);
		 * getIsActiveButton().Click();
		 */
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
		getNoOfDocuments().SendKeys("20000");

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

		/*
		 * this.driver.getWebDriver().get(Input.url+"Project/Project");
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getSearchProjectName().Visible() ;}}), Input.wait30);
		 * getSearchProjectName().SendKeys(projectname);
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getProjectFilterButton().Visible() ;}}), Input.wait30);
		 * getProjectFilterButton().Click();
		 * 
		 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		 * getProjectName().Visible() ;}}), Input.wait30);
		 * getProjectName(projectname).Displayed();
		 */

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
	 	getProjectFilterButton().Click(); 	 
	 	
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

        bc.VerifySuccessMessage(
                "Project is being created. A notification is provided to you once it is complete in the upper right hand corner.");

        

    }
}
