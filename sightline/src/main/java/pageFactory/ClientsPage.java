package pageFactory;

import java.util.concurrent.Callable;

import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class ClientsPage {

	Driver driver;
	BaseClass bc;
	ProjectPage project;

	public Element getAddEntity() {
		return driver.FindElementById("btnAddEntity");
	}

	public Element getEntityName() {
		return driver.FindElementByXPath("//*[@placeholder='Name']");
	}

	public Element getEntityNameFilter() {
		return driver.FindElementByXPath("//*[@placeholder='Filter by Name']");
	}

	public Element getEntityshortname() {
		return driver.FindElementById("EntityCode");
	}

	public Element getSaveBtn() {
		return driver.FindElementById("btnsave");
	}

	public Element getFilterButton() {
		return driver.FindElementById("btnSearch");
	}

	public Element getEntitytype() {
		return driver.FindElementById("ddlEntityTypeList");
	}

	public Element getDomainID() {
		return driver.FindElementById("entity_domainid");
	}

	public Element getFiler_Clientname() {
		return driver.FindElementByXPath("//*[@id='EntityDataTable']//td[1]");
	}

	public Element getEntityServerPath() {
		return driver.FindElementByXPath("//*[@id='ddlEntityWS']/option[1]");
	}

	// added by jayanthi 30/8/21
	public Element getSelectManageBtn() {
		return driver.FindElementByCssSelector("a[name='Manage'] i");
	}

	public Element getClient_clientPg() {
		return driver.FindElementByCssSelector("a[name='Entity'] i");
	}

	public Element databaseHelpIcon_clientPg() {
		return driver.FindElementByXPath("//label[text()='Initial Size of Project Database: ']//i");
	}

	public Element databaseHelpIconPopOver_clientPg() {
		return driver.FindElementByXPath(
				"//label[text()='Initial Size of Project Database: ']//a[contains(@aria-describedby,'popover')]");
	}

	public Element createClientHelpIcon_clientPg() {
		return driver.FindElementByXPath("//span[text()='Create Client']//i");
	}

	public Element createClientHelpIconPopOver_clientPg() {
		return driver.FindElementByXPath("//span[text()='Create Client']//a[contains(@aria-describedby,'popover')]");
	}

	public Element dataBaseTitle() {
		return driver.FindElementByXPath("//strong[text()='Database ']");
	}

	// Add by Aathith
	public Element getDBSizeOption() {
		return driver.FindElementById("ddlProjectDBSizeInstance");
	}

	public Element getProgressingInstanceOption() {
		return driver.FindElementById("ddlProjectDBSizeInstance");
	}

	public Element getClientDeleteBtn(String client) {
		return driver.FindElementByXPath(
				"//*[@id='EntityDataTable']/tbody/tr/td[text()='" + client + "']/../td/a[text()='Delete']");
	}

	public Element getTableHeader(int i) {
		return driver.FindElementByXPath("//*[@id='EntityDataTable']/thead/tr/th[" + i + "]");
	}

	public Element getAscending() {
		return driver.FindElementByXPath("//th[@aria-sort='ascending']");
	}

	public Element getDescending() {
		return driver.FindElementByXPath("//th[@aria-sort='descending']");
	}

	public Element getClientEditBtn(String client) {
		return driver.FindElementByXPath(
				"//*[@id='EntityDataTable']/tbody/tr/td[text()='" + client + "']/../td/a[text()='Edit']");
	}

	public ElementCollection getClientTableHeaders() {
		return driver.FindElementsByXPath("//*[@id='EntityDataTable']/thead/tr/th");
	}

	public Element getClientTableValue(int row, int column) {
		return driver.FindElementByXPath("//*[@id='EntityDataTable']/tbody/tr[" + row + "]/td[" + column + "]");
	}

	public Element getClientTableValue(String rowName, int column) {
		return driver.FindElementByXPath(
				"//*[@id='EntityDataTable']/tbody/tr/td[text()='" + rowName + "']/../td[" + column + "]");
	}

	public Element selectEntityType() {
		return driver.FindElementById("ddlEntityType");
	}

	// Add by Aathith
	public Element getEntityNameErrorMsg() {
		return driver.FindElementById("EntityLabel-error");
	}

	public Element getEntityIdErrorMsg() {
		return driver.FindElementById("entity_domainid-error");
	}

	public Element getCancelBtn() {
		return driver.FindElementById("btnCancel");
	}
	
	public Element getProcessingEngineType(){ 
		 return driver.FindElementById("ddlICEProcessingInstance"); 
	 }

	// Annotation Layer added successfully
	public ClientsPage(Driver driver) {

		this.driver = driver;
		this.driver.getWebDriver().get(Input.url + "Entity/Entity");
		driver.waitForPageToBeReady();

		bc = new BaseClass(driver);
		project = new ProjectPage(driver);
	}

	/**
	 * @Modified by : Aathith
	 * @Modified date : 07/19/2022 Modified on : change xpath 'getEntityName()' to
	 *           'getEntityNameFilter()'
	 * @param Clientnamenondomain
	 */
	public void AddNonDomainClient(String Clientnamenondomain) {
		this.driver.getWebDriver().get(Input.url + "Entity/Entity");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddEntity().Visible();
			}
		}), Input.wait30);
		getAddEntity().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEntityName().Visible();
			}
		}), Input.wait30);
		getEntityName().SendKeys(Clientnamenondomain);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEntityshortname().Visible();
			}
		}), Input.wait30);
		getEntityshortname().SendKeys(Clientnamenondomain);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSaveBtn().Visible();
			}
		}), Input.wait30);
		getSaveBtn().Click();

		bc.VerifySuccessMessage("The new client was added successfully");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEntityNameFilter().Visible();
			}
		}), Input.wait30);
		getEntityNameFilter().SendKeys(Clientnamenondomain);

		getFilterButton().waitAndClick(10);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(getFiler_Clientname().getText());

		Assert.assertEquals(getFiler_Clientname().getText(), Clientnamenondomain);

	}

	public void AddDomainClient(String Clientnamedomain, String domainid) {

		this.driver.getWebDriver().get(Input.url + "Entity/Entity");
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getAddEntity().Visible();
			}
		}), Input.wait30);
		getAddEntity().Click();

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEntityName().Visible();
			}
		}), Input.wait30);
		getEntityName().SendKeys(Clientnamedomain);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEntityshortname().Visible();
			}
		}), Input.wait30);
		getEntityshortname().SendKeys(Clientnamedomain);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return project.getSelectEntity().Visible();
			}
		}), Input.wait30);
		project.getSelectEntity().selectFromDropdown().selectByVisibleText("Domain");

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getDomainID().Visible();
			}
		}), Input.wait30);
		getDomainID().SendKeys(domainid);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return project.getProjectDBServerDropdown().Visible();
			}
		}), Input.wait30);
		project.getProjectDBServerDropdown().selectFromDropdown().selectByIndex(1);

		getEntityServerPath().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return project.getIngestionserverpath().Visible();
			}
		}), Input.wait30);
		project.getIngestionserverpath().selectFromDropdown().selectByIndex(0);
		project.getIngestionserverpath().selectFromDropdown().selectByIndex(1);

		project.getProductionserverpath().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSaveBtn().Visible();
			}
		}), Input.wait30);
		getSaveBtn().Click();

		bc.VerifySuccessMessage("The new client was added successfully");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEntityNameFilter().Visible();
			}
		}), Input.wait30);
		getEntityNameFilter().SendKeys(Clientnamedomain);

		getFilterButton().waitAndClick(10);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(getFiler_Clientname().getText());
		UtilityLog.info(getFiler_Clientname().getText());

		Assert.assertEquals(getFiler_Clientname().getText(), Clientnamedomain);

	}

	/**
	 * @author Jayanthi.ganesan
	 * @throws InterruptedException
	 * @description This method will create new client with entity type as Domain.
	 */
	public void addNewClientWithDomainType() throws InterruptedException {
		this.driver.getWebDriver().get(Input.url + "Entity/Entity");
		bc.waitForElement(getAddEntity());
		getAddEntity().waitAndClick(3);
		bc.waitForElement(getEntitytype());
		getEntitytype().selectFromDropdown().selectByVisibleText("Domain");
	}

	/**
	 * @author Jayanthi.ganesan
	 * @description This method will verifies whether Help text doesn't appear when
	 *              we hover near the Initial size of Project DataBase Help icon in
	 *              client page
	 */
	public void verifyHelpTextPopUpWhenHovering() throws InterruptedException {

		addNewClientWithDomainType();
		bc.waitForElement(databaseHelpIcon_clientPg());
		databaseHelpIcon_clientPg().ScrollTo();
		if (databaseHelpIconPopOver_clientPg().isElementAvailable(5) == false) {
			bc.passedStep("Help popup is not appeared while hovering on help icon");
		} else {
			bc.failedStep("Help popup is appeared while hovering on help icon");
		}

	}

	/**
	 * @author Jayanthi.ganesan
	 * @description This method will verifies whether Help text is appeared or not
	 *              when we click on the Initial size of Project DataBase Help icon
	 *              in client page.
	 */
	public void verifyHelpTextPopUpWhenClicking() throws InterruptedException {
		addNewClientWithDomainType();
		bc.waitForElement(databaseHelpIcon_clientPg());
		databaseHelpIcon_clientPg().Click();
		bc.stepInfo("Help icon is clicked");
		if (databaseHelpIconPopOver_clientPg().isElementAvailable(5) == true) {
			bc.passedStep("Help popup appeared when we click on help icon");
		} else {
			bc.failedStep("Help popup is not appeared");
		}
		dataBaseTitle().Click();
		bc.stepInfo("Other than help icon is clicked");
		if (databaseHelpIconPopOver_clientPg().isElementAvailable(5) == false) {
			bc.passedStep("Help popover is disappeared");
		} else {
			bc.failedStep("Help popup not disappeared");
		}

	}

	public void navigateToClientPage() {
		try {
			this.driver.getWebDriver().get(Input.url + "Entity/Entity");
		} catch (Exception e) {
			bc.failedStep(e.getMessage());
		}
	}

	public void verifyAscendingDescendingOrder() {
		driver.waitForPageToBeReady();
		if (getAscending().isElementAvailable(10)) {
			bc.passedStep("Web table is sorted in Ascending ordrer");
			getAscending().waitAndClick(10);
			driver.waitForPageToBeReady();
			if (getDescending().isElementAvailable(10)) {
				bc.stepInfo("Web table s in descending order");
				bc.passedStep("User is able to sort the column by ascending/descending order.");
			} else {
				bc.failedStep("verification failed");
			}
		} else {
			bc.stepInfo("Web table is sorted in Desscending ordrer");
			getDescending().waitAndClick(10);
			driver.waitForPageToBeReady();
			if (getAscending().isElementAvailable(10)) {
				bc.stepInfo("Web table s in Ascending order");
				bc.passedStep("User is able to sort the column by ascending/descending order.");
			} else {
				bc.failedStep("verification failed");
			}
		}
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param clientName
	 * @Description filter the client
	 */
	public void filterClient(String clientName) {
		driver.waitForPageToBeReady();
		bc.waitForElement(getEntityNameFilter());
		getEntityNameFilter().waitAndClick(5);
		getEntityNameFilter().SendKeys(clientName);
		bc.waitForElement(getFilterButton());
		getFilterButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		bc.stepInfo(clientName + " was filtered");
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param client
	 * @Description delete the client
	 */
	public void deleteClinet(String client) {
		driver.waitForPageToBeReady();
		filterClient(client);
		bc.waitForElement(getClientDeleteBtn(client));
		getClientDeleteBtn(client).waitAndClick(10);
		bc.getYesBtn().waitAndClick(10);
		bc.stepInfo("performed action for delete a client");
		bc.VerifySuccessMessage("The client has been deleted successfully");
	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param Clientnamedomain
	 * @param domainid
	 * @param dbsize
	 * @Description Add a Domain Client with choosing database size
	 */
	public void AddDomainClient(String Clientnamedomain, String domainid, String dbsize) {

		this.driver.getWebDriver().get(Input.url + "Entity/Entity");
		bc.waitForElement(getAddEntity());
		getAddEntity().waitAndClick(10);

		bc.waitForElement(getEntityName());
		getEntityName().SendKeys(Clientnamedomain);

		bc.waitForElement(getEntityshortname());
		getEntityshortname().SendKeys(Clientnamedomain);

		bc.waitForElement(project.getSelectEntity());
		project.getSelectEntity().selectFromDropdown().selectByVisibleText("Domain");

		bc.waitForElement(getDomainID());
		getDomainID().SendKeys(domainid);

		bc.waitForElement(project.getProjectDBServerDropdown());
		project.getProjectDBServerDropdown().selectFromDropdown().selectByIndex(1);
		bc.stepInfo("DB server was selected");

		bc.waitForElement(getDBSizeOption());
		getDBSizeOption().selectFromDropdown().selectByVisibleText(dbsize);
		bc.stepInfo("db size was selected");

		bc.waitForElement(getEntityServerPath());
		getEntityServerPath().waitAndClick(10);
		bc.stepInfo("project server path was selected");

		bc.waitForElement(project.getIngestionserverpath());
		project.getIngestionserverpath().selectFromDropdown().selectByIndex(0);
		project.getIngestionserverpath().selectFromDropdown().selectByIndex(1);
		bc.stepInfo("Ingetion path is selected");

		project.getProductionserverpath().waitAndClick(10);
		bc.stepInfo("production path is selected");

		bc.waitForElement(getSaveBtn());
		getSaveBtn().waitAndClick(10);
		bc.stepInfo("save button was clicked");

	}

	/**
	 * @author Aathith.Senthilkumar
	 * @param clientType
	 * @Description filter the client by Type
	 */
	public void filterClientByType(String clientType) {
		driver.waitForPageToBeReady();
		bc.waitForElement(selectEntityType());
		selectEntityType().selectFromDropdown().selectByVisibleText(clientType);
		bc.waitForElement(getFilterButton());
		getFilterButton().waitAndClick(10);
		driver.waitForPageToBeReady();
		bc.stepInfo(clientType + " entity was filtered");
	}
	
	/**
	  * @author Vijaya.Rani
	  * @param Clientnamedomain
	  * @param domainid
	  * @param dbsize
	  * @Description Add a Domain Client with choosing database size Processing engine
	  */
	 public void AddDomainClientProcessingEngine(String Clientnamedomain,String domainid,String dbsize) {
			
	  	this.driver.getWebDriver().get(Input.url+"Entity/Entity");
	  	SoftAssert softAssertion = new SoftAssert();
	  	bc.waitForElement(getAddEntity());
	  	getAddEntity().waitAndClick(10);
	  	
	  	bc.waitForElement(getEntityName());
	  	getEntityName().SendKeys(Clientnamedomain);
	  	
	  	bc.waitForElement(getEntityshortname());
	  	getEntityshortname().SendKeys(Clientnamedomain);
	  	
	  	bc.waitForElement(project.getSelectEntity());    	
	  	project.getSelectEntity().selectFromDropdown().selectByVisibleText("Domain");
	  	
	  	bc.waitForElement(getDomainID());
	  	getDomainID().SendKeys(domainid);
	  	
		softAssertion.assertTrue(getProcessingEngineType().isDisplayed());
		bc.passedStep("By default Processing Instance displays as ''Default ICE Instance' and Processing Engine Type is by default");
		
	  	bc.waitForElement(project.getProjectDBServerDropdown());
	  	project.getProjectDBServerDropdown().selectFromDropdown().selectByIndex(1);
	  	bc.stepInfo("DB server was selected");
	  	
	  	bc.waitForElement(getDBSizeOption());
	  	getDBSizeOption().selectFromDropdown().selectByVisibleText(dbsize);
	  	bc.stepInfo("db size was selected");
	  	
	  	bc.waitForElement(getEntityServerPath());
	  	getEntityServerPath().waitAndClick(10);
	  	bc.stepInfo("project server path was selected");
	  	
	  	bc.waitForElement(project.getIngestionserverpath());
	  	project.getIngestionserverpath().selectFromDropdown().selectByIndex(0);
	  	project.getIngestionserverpath().selectFromDropdown().selectByIndex(1);
	  	bc.stepInfo("Ingetion path is selected");
	  	
	  	project.getProductionserverpath().waitAndClick(10);
	  	bc.stepInfo("production path is selected");
	  	
	  	bc.waitForElement(getSaveBtn());
	  	getSaveBtn().waitAndClick(10);
	  	bc.stepInfo("save button was clicked");
	  	softAssertion.assertAll();
	  }

}