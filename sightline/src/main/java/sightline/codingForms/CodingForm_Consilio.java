package sightline.codingForms;

import pageFactory.DocViewPage;

import java.awt.AWTException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;


import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.ProjectPage;
import pageFactory.ReusableDocViewPage;
import pageFactory.SavedSearch;
import pageFactory.SecurityGroupsPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.UserManagement;
import pageFactory.Utility;
import testScriptsSmoke.Input;
import pageFactory.CodingForm;

public class CodingForm_Consilio {
	Driver driver;
	LoginPage loginPage;
	SoftAssert softAssertion;
	CodingForm codingForm;
	SavedSearch savedSearch;
	BaseClass baseClass;
	SessionSearch sessionSearch;
	TagsAndFoldersPage  tagsAndFoldersPage;

	 String codingformB = "DummyCF"+Utility.dynamicNameAppender();
	    String SpcharB="&>‘<";
	    String SpcharC="Test_term";

	@BeforeClass(alwaysRun = true)
	private void TestStart() throws Exception, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
	    in.loadEnvConfig();
	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException, ParseException, Exception {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		baseClass = new BaseClass(driver);
		loginPage = new LoginPage(driver);
	    sessionSearch = new SessionSearch(driver);
	    softAssertion = new SoftAssert();
		codingForm = new CodingForm(driver);
	}

	@Test(description = "RPMXCON-68850",groups = { "regression" })
	public void verifyCFnameSpecialChar() throws Exception {
	    baseClass.stepInfo("Test case Id: RPMXCON-68850 :Verify that error message display and application does NOT accepts - \"Coding Form\" Name with special characters < > & ‘");
	    String codingform = "test&'test>";
	    String codingform1= "CF"+Utility.dynamicNameAppender();	
		String errorMsg = "Coding Form names must not contain any special characters such as $ ^ % # @. Spaces, underscores, numbers and letters are accepted.";  

	    //Create coding form as per attachment
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Users loggedIn Successfully");
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.stepInfo("Navigated to Manage Coding Forms screen");
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(5);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		codingForm.getSaveCFBtn().waitAndClick(3);
		baseClass.passedStep("Error message displyed :"+errorMsg);
		codingForm.CFnameErrormsg(errorMsg);

		//create CodingForm
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		codingForm.createCodingFormWithoutObjects(codingform1);

		//edit CF name
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		codingForm.editCodingForm(codingform1);
		baseClass.stepInfo("Navigated to edit coding form editor");
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		codingForm.getSaveCFBtn().waitAndClick(3);
		baseClass.passedStep("Error message displyed :"+errorMsg);
		codingForm.CFnameErrormsg(errorMsg);

		//Delete CF
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(codingform1, codingform1);

		// logout
		loginPage.logout();
	}

	@Test(description= "RPMXCON-69033",groups= { "regression" })
	public void CheckGroupObjectERRORMsg() {
		baseClass.stepInfo("Test case Id: RPMXCON-69033 :Verify that error message display and Check Group Object inside Coding Form does NOT accept with special characters < > & ‘ ");
	    String codingform = "TestCF"+Utility.dynamicNameAppender();
	    String Spchar="&>‘<";
	    String Spchar1="Test_term";
	    
	    //Create coding form as per attachment
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
	    baseClass.stepInfo("Users loggedIn Successfully");
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.stepInfo("Navigated to Manage Coding Forms screen");
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(5);
		codingForm.select_CheckGroup(Spchar,Spchar,Spchar);
		  if (codingForm.ValidationErrormsg().Visible()){	
				baseClass.VerifyErrorMessage("Special characters(<,>) are not allowed.");
				}	
		
		//create CodingForm
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(5);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingform);
		codingForm.select_CheckGroup(Spchar1,Spchar1,Spchar1);
		codingForm.getSaveCFBtn().waitAndClick(3);
		codingForm.saveCodingForm();
		baseClass.passedStep("Coding form"+codingform+ "created successfully");
		
		//edit CF name
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		codingForm.editCodingForm(codingform);
		baseClass.stepInfo("Navigated to edit coding form editor");
		baseClass.waitForElement(codingForm.getRootClickDownarrow());
		codingForm.getRootClickDownarrow().waitAndClick(5);
		codingForm.select_CheckGroup(Spchar,Spchar,Spchar);
		
		
		//Delete CF
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(codingform, codingform);

		// logout
		loginPage.logout();
	}
	
	@Test(description= "RPMXCON-69032",groups= { "regression" })
	public void RadioGroupObjectERRORMsg() {
		baseClass.stepInfo("Test case Id: RPMXCON-69032 :Verify that error message  display and \"Radio Group Object\" inside Coding Form does NOT accept with special characters < > & ‘ ");
	    String codingformA = "DummyCF"+Utility.dynamicNameAppender();
	    String SpcharA="&>‘<";
	    String SpcharB="Test_term";
	    
	    //Create coding form as per attachment
	    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		baseClass.stepInfo("Users loggedIn Successfully");
	    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
	    driver.waitForPageToBeReady();
	    baseClass.stepInfo("Navigated to Manage Coding Forms screen");
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(5);
        codingForm.select_RadioGroup(SpcharA,SpcharA,SpcharA);
        if (codingForm.ValidationErrormsg().Visible()){	
			baseClass.VerifyErrorMessage("Special characters(<,>) are not allowed.");
			}	
		
		//create CodingForm
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
	    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
		codingForm.getAddNewCodingFormBtn().waitAndClick(5);
		baseClass.waitForElement(codingForm.getCodingFormName());
		codingForm.getCodingFormName().SendKeys(codingformA);
		codingForm.select_RadioGroup(SpcharB,SpcharB,SpcharB);
		codingForm.getSaveCFBtn().waitAndClick(3);
		codingForm.saveCodingForm();
		baseClass.passedStep("Coding form"+codingformA+ "created successfully");
		
		//edit CF name
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		codingForm.editCodingForm(codingformA);
		baseClass.stepInfo("Navigated to edit coding form editor");
		baseClass.waitForElement(codingForm.getRootClickDownarrow());
		codingForm.getRootClickDownarrow().waitAndClick(5);
		codingForm.select_RadioGroup(SpcharA,SpcharA,SpcharA);
		
		
		//Delete CF
		this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		driver.waitForPageToBeReady();
		codingForm.deleteCodingForm(codingformA, codingformA);

		// logout
		loginPage.logout();
	}
	
	@Test(description= "RPMXCON-68854",groups= { "regression" })
		public void EditableMetaDataObjectERRORMsg() {
			baseClass.stepInfo("Test case Id: RPMXCON-68854 :Verify that error message display and \"Editable MetaData Object\" inside Coding Form does NOT accept with special characters < > & ‘");
	   	    
		    loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		    driver.waitForPageToBeReady();
		    baseClass.stepInfo("Navigated to Manage Coding Forms screen");
		    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
			codingForm.getAddNewCodingFormBtn().waitAndClick(5);
			baseClass.stepInfo("Create or edit codign form page is displayed");
	        codingForm.select_EditableMetaDataObject(SpcharB,SpcharB,SpcharB);
	        if (codingForm.ValidationErrormsg().Visible()){	
				baseClass.VerifyErrorMessage("Special characters(<,>) are not allowed.");
				}	
			
			//create CodingForm
			this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
			driver.waitForPageToBeReady();
		    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
			codingForm.getAddNewCodingFormBtn().waitAndClick(5);
			baseClass.stepInfo("Create or edit codign form page is displayed");
			baseClass.waitForElement(codingForm.getCodingFormName());
			codingForm.getCodingFormName().SendKeys(codingformB);
			codingForm.select_EditableMetaDataObject(SpcharC,SpcharC,SpcharC);

			codingForm.getSaveCFBtn().waitAndClick(3);
			codingForm.saveCodingForm();
			baseClass.passedStep("Coding form"+codingformB+ "created successfully");
			
			//edit CF name
			this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
			driver.waitForPageToBeReady();
			codingForm.editCodingForm(codingformB);
			baseClass.stepInfo("Navigated to edit coding form editor");
			baseClass.waitForElement(codingForm.getRootClickDownarrow());
			codingForm.getRootClickDownarrow().waitAndClick(5);
			codingForm.select_EditableMetaDataObject(SpcharB,SpcharB,SpcharB);
			
			
			//Delete CF
			this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
			driver.waitForPageToBeReady();
			codingForm.deleteCodingForm(codingformB, codingformB);

			// logout
			loginPage.logout();
		}
		
	@Test(description= "RPMXCON-68852",groups= { "regression" })
		public void TagObjectERRORMsg() {
			baseClass.stepInfo("Test case Id: RPMXCON-68852 :Verify that error message displays and \"Tag Object\" inside Coding Form does NOT accept with special characters < > & ‘");
			   	    
				loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
					UtilityLog.info("Logged in as User: " + Input.rmu1userName);
				    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
				    driver.waitForPageToBeReady();
				    baseClass.stepInfo("Navigated to Manage Coding Forms screen");
				    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
					codingForm.getAddNewCodingFormBtn().waitAndClick(5);
					baseClass.stepInfo("Create or edit codign form page is displayed");
			        codingForm.select_TagObject(SpcharB,SpcharB,SpcharB);
			        if (codingForm.ValidationErrormsg().Visible()){	
						baseClass.VerifyErrorMessage("Special characters(<,>) are not allowed.");
						}	
					
					//create CodingForm
					this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
					driver.waitForPageToBeReady();
				    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
					codingForm.getAddNewCodingFormBtn().waitAndClick(5);
					baseClass.stepInfo("Create or edit codign form page is displayed");
					baseClass.waitForElement(codingForm.getCodingFormName());
					codingForm.getCodingFormName().SendKeys(codingformB);
					codingForm.select_TagObject(SpcharC,SpcharC,SpcharC);
					codingForm.getSaveCFBtn().waitAndClick(3);
					codingForm.saveCodingForm();
					baseClass.passedStep("Coding form"+codingformB+ "created successfully");
					
					//edit CF name
					this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
					driver.waitForPageToBeReady();
					codingForm.editCodingForm(codingformB);
					baseClass.stepInfo("Navigated to edit coding form editor");
					baseClass.waitForElement(codingForm.getRootClickDownarrow());
					codingForm.getRootClickDownarrow().waitAndClick(5);
					codingForm.select_TagObject(SpcharB,SpcharB,SpcharB);
					
					
					//Delete CF
					this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
					driver.waitForPageToBeReady();
					codingForm.deleteCodingForm(codingformB, codingformB);

					// logout
					loginPage.logout();
				}
		
		@Test(description= "RPMXCON-68853",groups= { "regression" })
        public void CommentObjectERRORMsg() {
	baseClass.stepInfo("Test case Id: RPMXCON-68853 :Verify that error message display and \"Comments Object\" inside Coding Form does NOT accept with special characters < > & ‘");
	   	    
		loginPage.loginToSightLine(Input.rmu1userName, Input.rmu1password);
			UtilityLog.info("Logged in as User: " + Input.rmu1userName);
		    this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
		    driver.waitForPageToBeReady();
		    baseClass.stepInfo("Navigated to Manage Coding Forms screen");
		    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
			codingForm.getAddNewCodingFormBtn().waitAndClick(5);
			baseClass.stepInfo("Create or edit codign form page is displayed");
	        codingForm.select_CommentObject(SpcharB,SpcharB,SpcharB);
	        if (codingForm.ValidationErrormsg().Visible()){	
				baseClass.VerifyErrorMessage("Special characters(<,>) are not allowed.");
				}	
			
			//create CodingForm
			this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
			driver.waitForPageToBeReady();
		    baseClass.waitForElement(codingForm.getAddNewCodingFormBtn());
			codingForm.getAddNewCodingFormBtn().waitAndClick(5);
			baseClass.stepInfo("Create or edit codign form page is displayed");
			baseClass.waitForElement(codingForm.getCodingFormName());
			codingForm.getCodingFormName().SendKeys(codingformB);
			codingForm.select_CommentObject(SpcharC,SpcharC,SpcharC);
			codingForm.getSaveCFBtn().waitAndClick(3);
			codingForm.saveCodingForm();
			baseClass.passedStep("Coding form"+codingformB+ "created successfully");
			
			//edit CF name
			this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
			driver.waitForPageToBeReady();
			codingForm.editCodingForm(codingformB);
			baseClass.stepInfo("Navigated to edit coding form editor");
			baseClass.waitForElement(codingForm.getRootClickDownarrow());
			codingForm.getRootClickDownarrow().waitAndClick(5);
			codingForm.select_CommentObject(SpcharB,SpcharB,SpcharB);
			
			
			//Delete CF
			this.driver.getWebDriver().get(Input.url + "CodingForm/Create");
			driver.waitForPageToBeReady();
			codingForm.deleteCodingForm(codingformB, codingformB);

			// logout
			loginPage.logout();
		}
		

	@AfterMethod(alwaysRun = true)
	private void afterMethod(ITestResult result) throws ParseException, Exception, Throwable {
		baseClass = new BaseClass(driver);
		Reporter.setCurrentTestResult(result);
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility baseClass = new Utility(driver);
			baseClass.screenShot(result);
		}
		try {;
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)

	public void close() {
		System.out.println("******TEST CASES FOR CODINGFORM EXECUTED******");
	}


}