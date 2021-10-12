package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;
import org.testng.asserts.SoftAssert;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class TS_005_BasicAndAdvanceAlerts {
	Driver driver;
	LoginPage lp;
	SessionSearch sessionSearch;	
	int pureHit;
	SoftAssert softAssertion;

	BaseClass bc;
	/*String tagName = "tagName"+Utility.dynamicNameAppender();
	String folderName = "AFolderName"+Utility.dynamicNameAppender();*/
	
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
     Input in = new Input();
     in.loadEnvConfig();
		
		driver = new Driver();
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);

	}
	
     
    @Test(dataProvider = "special chars",groups= {"regression"},priority=1)
    public void BSphraseAndProximitySearchWrongQueryAlert(String data) {
    	driver.getWebDriver().navigate().refresh();
    	bc.selectproject();
    	
   		sessionSearch.wrongQueryAlertBasicSaerch(data, 1,"non fielded", null, "TestcaseId");
   		} 
    
    @Test(dataProvider = "special chars with operator",groups= {"regression"},priority=10)
    public void BSphraseAndProximitySearchWrongQueryAlert1(String data) {
    	driver.getWebDriver().navigate().refresh();
    	bc.selectproject();
    	
   	  sessionSearch.wrongQueryAlertBasicSaerch(data, 11,"non fielded", null, "TestcaseId");
	}
    
    @Test(dataProvider = "special chars",groups= {"regression"},priority=2)
    public void ASphraseAndProximitySearchWrongQueryAlert(String data) {
    	driver.getWebDriver().navigate().refresh();
    	bc.selectproject();
    	
   		sessionSearch.wrongQueryAlertAdvanceSaerch(data, 1,"non fielded", null, "TestcaseId");
    }
    
    @Test(dataProvider = "special chars with operator",groups= {"regression"},priority=11)
    public void ASphraseAndProximitySearchWrongQueryAlert1(String data) {
    	driver.getWebDriver().navigate().refresh();
    	bc.selectproject();
   
   		sessionSearch.wrongQueryAlertAdvanceSaerch(data, 11,"non fielded", null, "TestcaseId");
	} 
    
    
    @Test(dataProvider =  "reservedWords",groups= {"regression"},priority=3)
    public void reserveredWordsAsearchNonFielded(String data, int MessageNumber, String fielded, String fieldName,String TestCaseId) {
		// TODO Auto-generated method stub
		driver.getWebDriver().navigate().refresh();
		bc.selectproject();
		sessionSearch.wrongQueryAlertAdvanceSaerch(data, MessageNumber, fielded, fieldName, TestCaseId);

	}

	@Test(dataProvider ="reservedWords1",groups= {"regression"},priority=4)
	public void reserveredWordsASeachfielded(String data, int MessageNumber, String fielded, String fieldName,
			String TestCaseId) {
		// TODO Auto-generated method stub
		driver.getWebDriver().navigate().refresh();
		bc.selectproject();
		sessionSearch.wrongQueryAlertAdvanceSaerch(data, MessageNumber, fielded, fieldName, TestCaseId);

	}

	@Test(dataProvider = "reservedWords2",groups= {"regression"},priority=5)
	public void reserveredWordsBsearchNonFielded(String data, int MessageNumber, String fielded, String fieldName,String TestCaseId) {

		driver.getWebDriver().navigate().refresh();
		bc.selectproject();
		sessionSearch.wrongQueryAlertBasicSaerch(data, 2,"non fielded", null, "TestCaseId");

	}

  @Test(dataProvider ="reservedWords3",groups = {"regression"},priority=6)
	public void reserveredWordsBSeachfielded(String data, int MessageNumber, String fielded, String fieldName,
			String TestCaseId) {

		driver.getWebDriver().navigate().refresh();
		bc.selectproject();

		sessionSearch.wrongQueryAlertBasicSaerch(data, 2,"fielded", "CustodianName", "TestCaseId");

	}
	@Test(dataProvider = "dateSearches", groups = {"regression" },priority=7)
	public void dateandOtherSeachesInBSP(String data, int MessageNumber, String fielded, String fieldName,
			String TestCaseId) {

		driver.getWebDriver().navigate().refresh();
		bc.selectproject();
		sessionSearch.wrongQueryAlertBasicSaerch(data, MessageNumber, fielded, fieldName, TestCaseId);

		// sessionSearch.wrongQueryAlertBasicSaerch("bi-weekly", 4,"non fielded", null);
		// sessionSearch.wrongQueryAlertBasicSaerch("bi-weekly", 4,"fielded","CustodianName");

		// sessionSearch.wrongQueryAlertBasicSaerch(data, MessageNumber, fielded, fieldName,TestCaseId);
		sessionSearch.getTallyContinue().Click();
		// verify counts
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearch.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait90);
		Assert.assertTrue(Integer.parseInt(sessionSearch.getPureHitsCount().getText()) >= 0);

	}

	@Test(dataProvider = "warningMessagesbasicsearch", groups = { "regression" },priority=8)
	public void otherWarningMessagesbasicsearch(String data, int MessageNumber, String fielded, String fieldName,
			String TestCaseId) {
		driver.getWebDriver().navigate().refresh();
		bc.selectproject();
		sessionSearch.wrongQueryAlertBasicSaerch(data, MessageNumber, fielded, fieldName, TestCaseId);

	}

	@Test(dataProvider = "warningMessagesadvancesearch", groups = { "regression" },priority=9)
	public void otherWarningMessagesadvancesearch(String data, int MessageNumber, String fielded, String fieldName,
			String TestCaseId) {
		driver.getWebDriver().navigate().refresh();
		bc.selectproject();
		sessionSearch.wrongQueryAlertAdvanceSaerch(data, MessageNumber, fielded, fieldName, TestCaseId);

	}
    
	 @DataProvider(name = "special chars")
	    public Object[][] dataProviderMethod() {
	        return new Object[][] { 
	        	{"\"*screpancy scripts\"~3"},
	        	{"\"discre*y scripts\"~3"},
	        	{"\"discrepan* scripts\"~3"},
	        	{"\"*screpancy scripts\"~3"},
	        	{"\"discre*y scripts\"~3"},
	        	{"\"discrepancy scrip*\"~3"}, 
	        	{"\"discrepancy *ripts\"~3"},
	        	{"\"discr*ancy scripts\"~3"},
	        	{"\"discrepan* scrip*\"~3"},
	        	{"\"discrepan* script*\"~3"},
	        	{"\"discrepan*  scripts\"~3"},
	        	{"\"discrepan* script*\"~3"},
	        	{"\"discrepan* scripts\"~3"},
	        	{"\"*screpancy org*\"~3"},
	        	{"\"discre*y org\"~3"},
	        	{"\"discrepa* org\"~3"},
	        	{"\"*screpancy and*\"~3"}, 
	        	{"\"discre*y and\"~3" },
	        	{"\"discrepa* and\"~3"},
	        	{"\"*screpancy not*\"~3"},
	        	{"\"discre*y not*\"~3" },
	        	{"\"discrepa* not*\"~3"},
	        	{"\"discrepan? scripts\"~3"},
	        	{"\"?screpancy scripts\"~3"},
	        	{"\"discre?y scripts\"~3"}, 
	        	{"\"discrepancy scrip?\"~3"}, 
	        	{"\"discrepancy ?ripts\"~3"}, 
	        	{"\"discr?ancy scripts\"~3"}, 
	        	{"\"discrepan? scrip*\"~3"},
	        	{"\"discrepan? script?\"~3"},
	        	{"\"discrepan?  scripts\"~3"},
	        	{"\"discrepan? script*\"~3"},
	        	{"\"discrepan? scripts\"~3"},
	        	{"\"discre?y scripts\"~3"},
	        	{"\"?screpancy scripts\"~3"},
	        	{"\"?screpancy org?\"~3"},
	        	{"\"discre?y org\"~3"},
	        	{"\"discrepa? org\"~3"},
	        	{"\"?screpancy and?\"~3"},
	        	{"\"discre?y and\"~3"},
	        	{"\"discrepa? and\"~3"},
	        	{"\"?screpancy not?\"~3"},
	        	{"\"discre?y not?\"~3"},
	        	{"\"discrepa? not?\"~3"},
	        	{"\"?screpancy scripts\"~3"},
	        	{"\"discre*y scripts\"~3"},
	        	{"\"discrepan* script?\"~3"},
	        	{"\"discre*y scripts\"~3"},
	        	{"\"*screpancy scripts\"~3"},
	        	{"(\"?screpancy org?” OR \"*screpancy org*\" ) NOT \"discre?y org\"~3"},
	        	{"\"discrepan? script*\"~3"},
	        	{"(\"?screpancy org?” OR \"*screpancy or\" ) NOT ((\"?screpancy and\" OR \"*screpancy and *\" ) AND (\"?screpancy not?\" OR \"*screpancy not\" ) ) OR "
	        			+ "( \"?screpancy and?\"~3 OR \"?screpancy or?\"~3 OR \"?screpancy not\"~3)"},
	        	
	         	
	        	{"\"discrepancy in\" OR \"discrepancy scripts\"~3"},
	        	{"\"discrepancy scripts\"~3 OR \"discrepanc* in\"~3"},
	        	{"\"discrepanc* in\"~3"},
	        	
	        	
	        };
	    }
	    
	 @DataProvider(name = "special chars with operator")
	    public Object[][] dataProviderMethod8() {
	        return new Object[][] { 
	        	{"CustodianName: (Andrew)OR(\"?screpancy and scripts\" OR (\"?screpancy or scripts\")NOT(\"*screpancy not scripts\" )) AND \"?screpancy not?\"~3"},
	        	
	        	
	        };
	     }
    
   

	@DataProvider(name = "reservedWords")
	public Object[][] dataProviderMethod1() {
		return new Object[][] { { "test or the", 2, "non fielded", null, "RPMXCON-57348" },
				{ "test and yes", 2, "non fielded", null, "RPMXCON-57350" },
				{ "test not yes", 2, "non fielded", null, "RPMXCON-57353" },
				{ "Test Or yes", 2, "non fielded", null, "RPMXCON-57348" },
				{ "Test And yes", 2, "non fielded", null, "RPMXCON-57350" },
				{ "yes Not the", 2, "non fielded", null, "RPMXCON-57353" }

		};
	}

	@DataProvider(name = "reservedWords1")
	public Object[][] dataProviderMethod5() {
		return new Object[][] { { "test or the", 2, "fielded", "CustodianName", "RPMXCON-57348" },
				{ "test and yes", 2, "fielded", "CustodianName", "RPMXCON-57350" },
				{ "test not yes", 2, "fielded", "CustodianName", "RPMXCON-57353" },
				{ "Test Or yes", 2, "fielded", "CustodianName", "RPMXCON-57348" },
				{ "Test And yes", 2, "fielded", "CustodianName", "RPMXCON-57350" },
				{ "yes Not the", 2, "fielded", "CustodianName", "RPMXCON-57353" }

		};
	}

	@DataProvider(name = "reservedWords2")
	public Object[][] dataProviderMethod6() {
		return new Object[][] { { "test or the", 2, "non fielded", null, "RPMXCON-57348" },
				{ "test and yes", 2, "non fielded", null, "RPMXCON-57350" },
				{ "test not yes", 2, "non fielded", null, "RPMXCON-57353" },
				{ "Test Or yes", 2, "non fielded", null, "RPMXCON-57348" },
				{ "Test And yes", 2, "non fielded", null, "RPMXCON-57350" },
				{ "yes Not the", 2, "non fielded", null, "RPMXCON-57353" }

		};
	}

	@DataProvider(name = "reservedWords3")
	public Object[][] dataProviderMethod7() {
		return new Object[][] { { "test or the", 2, "fielded", "CustodianName", "RPMXCON-57348" },
				{ "test and yes", 2, "fielded", "CustodianName", "RPMXCON-57350" },
				{ "test not yes", 2, "fielded", "CustodianName", "RPMXCON-57353" },
				{ "Test Or yes", 2, "fielded", "CustodianName", "RPMXCON-57348" },
				{ "Test And yes", 2, "fielded", "CustodianName", "RPMXCON-57350" },
				{ "yes Not the", 2, "fielded", "CustodianName", "RPMXCON-57353" }

		};
	}

	@DataProvider(name = "dateSearches")
	public Object[][] dataProviderMethod2() {

		return new Object[][] { { "2009-09-20", 3, "non fielded", null, "RPMXCON-57358" },
				{ "2009/09/20", 3, "non fielded", null, "RPMXCON-57358" },
				{ "(that this verification)", 10, "non fielded", null, "RPMXCON-47271" } };
	}

	
	@DataProvider(name = "warningMessagesbasicsearch")

	public Object[][] dataProviderMethod3() {
		return new Object[][] {

				{ "test test}", 10, "non fielded", null, "RPMXCON-47271" },

				{ "\"gove\"~2", 6, "non fielded", null, "RPMXCON-47271" },

				{ "\"government \"money laundering\"\"~2", 7, "non fielded", null, "RPMXCON-57356" },

				{ "\"TEST this\"~ 4", 8, "non fielded", null, "RPMXCON-57355" },

				{ "PT AND", 9, "non fielded", null, "RPMXCON-57354" },

				{ "PT OR", 9, "non fielded", null, "RPMXCON-57354" },

				{ "PT NOT", 9, "non fielded", null, "RPMXCON-57354" },

				{ "CustodianName : {P Allen", 10, "non fielded", null, "RPMXCON-47271" },

				{ "Document_Comments: { Comment1", 10, "non fielded", null, "RPMXCON-47271" },

		};
	}

	@DataProvider(name = "warningMessagesadvancesearch") // warningMessagesadvancesearch

	public Object[][] dataProviderMethod4() {
		return new Object[][] {

				// advanceSearch

				{ "test test", 10, "non fielded", null, "RPMXCON-47271" },

				{ "\"gove\"~2", 6, "non fielded", null, "RPMXCON-47271" },

				{ "\"government \"money laundering\"\"~2", 7, "non fielded", null, "RPMXCON-47271" },

				{ "\"TEST this\"~ 4", 8, "non fielded", null, "RPMXCON-57355" },

				{ "PT AND", 9, "non fielded", null, "RPMXCON-57374" },

				{ "PT OR", 9, "non fielded", null, "RPMXCON-57374" },

				{ "PT NOT", 9, "non fielded", null, "RPMXCON-57374" },

				{ "CustodianName : {P Allen", 10, "non fielded", null, "RPMXCON-47271" },

				{ "Document_Comments: { Comment1", 10, "non fielded", null, "RPMXCON-49754" },

				{ "\"Term1 Term2\"~ 4", 8, "non fielded", null, "RPMXCON-57355" },

				{ "discrepan? scripts", 10, "non fielded", null, "RPMXCON-47271" } };
	}

	
	 @BeforeMethod
	 public void beforeTestMethod(Method testMethod){
		System.out.println("------------------------------------------");
	    System.out.println("Executing method : " + testMethod.getName());       
	 }
	   @AfterMethod(alwaysRun = true)
		 public void takeScreenShot(ITestResult result) {
	 	 if(ITestResult.FAILURE==result.getStatus()){
	 		 
	 		Utility bc = new Utility(driver);
	 		bc.screenShot(result);
	 		
	 	}
	 	 System.out.println("Executed :" + result.getMethod().getMethodName());
	 	
	     }
	@AfterClass(alwaysRun = true)
	public void close(){
		try{
			lp.logout();
			//lp.quitBrowser();
		}finally {
			lp.quitBrowser();
		}
		
	}

}
