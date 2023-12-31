package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.concurrent.Callable;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
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
    
		
		driver = new Driver();
		bc = new BaseClass(driver);
		sessionSearch = new SessionSearch(driver);
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.pa2userName, Input.pa2password);
    	

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
     
    //@Test(dataProvider = "special chars",groups= {"regression"})
    public void BSphraseAndProximitySearchWrongQueryAlert(String data) {
    	driver.getWebDriver().navigate().refresh();
    	bc.selectproject();
    	
   		sessionSearch.wrongQueryAlertBasicSaerch(data, 1,"non fielded", null);
	} 
    
    //@Test(dataProvider = "special chars",groups= {"regression"})
    public void ASphraseAndProximitySearchWrongQueryAlert(String data) {
    	driver.getWebDriver().navigate().refresh();
    	bc.selectproject();
    	
   		sessionSearch.wrongQueryAlertAdvanceSaerch(data, 1,"non fielded", null);
	} 
    
    @Test(dataProvider =  "reservedWords",groups= {"regression"})
    public void reserveredWordsAsearchNonFielded(String data) {
		// TODO Auto-generated method stub
    	driver.getWebDriver().navigate().refresh();
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertAdvanceSaerch(data, 2,"non fielded", null);
    	
	}
    @Test(dataProvider =  "reservedWords",groups= {"regression"})
    public void reserveredWordsASeachfielded(String data) {
		// TODO Auto-generated method stub
    	driver.getWebDriver().navigate().refresh();
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertAdvanceSaerch(data, 2,"fielded", "CustodianName");
    	
	}
    
    @Test(dataProvider =  "reservedWords",groups= {"regression"})
    public void reserveredWordsBsearchNonFielded(String data) {
		
    	driver.getWebDriver().navigate().refresh();
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertBasicSaerch(data, 2,"non fielded", null);
    	
	}
    @Test(dataProvider =  "reservedWords",groups= {"regression"})
    public void reserveredWordsBSeachfielded(String data) {
		
    	driver.getWebDriver().navigate().refresh();
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertBasicSaerch(data, 2,"fielded", "CustodianName");
    	
	}
    @Test
    public void dateandOtherSeachesInBSP() {
		
    	driver.getWebDriver().navigate().refresh();
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertBasicSaerch("2009-09-20", 3,"non fielded", null);
    	driver.getWebDriver().navigate().refresh();
    	sessionSearch.wrongQueryAlertBasicSaerch("2009/09/20", 3,"non fielded", null);
    	driver.getWebDriver().navigate().refresh();
    	
    	/*sessionSearch.wrongQueryAlertBasicSaerch("bi-weekly", 4,"non fielded", null);
    	sessionSearch.wrongQueryAlertBasicSaerch("bi-weekly", 4,"fielded", "CustodianName");*/
    	
    	sessionSearch.wrongQueryAlertBasicSaerch("(that this verification)", 10,"non fielded", null);
    	sessionSearch.getTallyContinue().Click();
    	//verify counts 
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			sessionSearch.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait90);
    	Assert.assertTrue(Integer.parseInt(sessionSearch.getPureHitsCount().getText())>1);
    	
    	

    	
	}
    
    @Test
    public void dateASeach() {
    	// TODO Auto-generated method stub
    	driver.getWebDriver().navigate().refresh();
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertAdvanceSaerch("2009-09-20", 3,"non fielded", null);
    	driver.getWebDriver().navigate().refresh();
    	sessionSearch.wrongQueryAlertAdvanceSaerch("2009/09/20", 3,"non fielded", null);
    	driver.getWebDriver().navigate().refresh();
    	/*sessionSearch.wrongQueryAlertAdvanceSaerch("bi-weekly", 4,"non fielded", null);
    	driver.getWebDriver().navigate().refresh();
    	sessionSearch.wrongQueryAlertAdvanceSaerch("bi-weekly", 4,"fielded", "CustodianName");*/

    	
    	sessionSearch.wrongQueryAlertAdvanceSaerch("(that this verification)", 10,"non fielded", null);
    	sessionSearch.getTallyContinue().Click();
    	//verify counts 
    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			sessionSearch.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?")  ;}}), Input.wait90);
    	Assert.assertTrue(Integer.parseInt(sessionSearch.getPureHitsCount().getText())>1);
    	
	}
    @Test
    public void otherWarningMessages() {
    	driver.getWebDriver().navigate().refresh();
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertAdvanceSaerch("(\"test test\"", 5,"non fielded", null);
    	
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertAdvanceSaerch("\"test test\")", 5,"non fielded", null);
    	
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertAdvanceSaerch("\"gove\"~2", 6,"non fielded", null);
    	
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertAdvanceSaerch("\"government \"money laundering\"\"~2", 7,"non fielded", null);
    	
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertAdvanceSaerch("\"TEST this\"~ 4", 8,"non fielded", null);
    	
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertBasicSaerch("PT AND", 9,"non fielded", null);
    	
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertBasicSaerch("PT OR", 9,"non fielded", null);
    	
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertBasicSaerch("PT NOT", 9,"non fielded", null);
    	
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertBasicSaerch("CustodianName : (P Allen", 5,"non fielded", null);
    	
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertBasicSaerch("Remark: (Reamark1", 5,"non fielded", null);
    	
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertBasicSaerch("Document_Comments: ( Comment1", 5,"non fielded", null);
    	
    	
    	//advanceSearch
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertBasicSaerch("(\"test test\"", 5,"non fielded", null);
    	
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertBasicSaerch("\"test test\")", 5,"non fielded", null);
    	
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertBasicSaerch("\"gove\"~2", 6,"non fielded", null);
    	
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertBasicSaerch("\"government \"money laundering\"\"~2", 7,"non fielded", null);
    	
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertBasicSaerch("\"TEST this\"~ 4", 8,"non fielded", null);
    	
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertAdvanceSaerch("PT AND", 9,"non fielded", null);
    	
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertAdvanceSaerch("PT OR", 9,"non fielded", null);
    	
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertAdvanceSaerch("PT NOT", 9,"non fielded", null);
    	
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertAdvanceSaerch("CustodianName : (P Allen", 5,"non fielded", null);
    	
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertAdvanceSaerch("Remark: (Reamark1", 5,"non fielded", null);
    	
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertAdvanceSaerch("Document_Comments: ( Comment1", 5,"non fielded", null);
    	
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertAdvanceSaerch("\"Term1 Term2\"~ 4", 11,"non fielded", null);
    	
    	bc.selectproject();
    	sessionSearch.wrongQueryAlertAdvanceSaerch("\"discrepan? scripts\"", 12,"non fielded", null);
	}
    @DataProvider(name = "reservedWords")
    public Object[][] dataProviderMethod1() {
        return new Object[][] { {"test or the"},
        	{"test and yes"},
        	{"test not yes"},
        	{"Test Or yes"},
        	{"Test And yes"},
        	{"yes Not the"}
    };
    }
    
    @DataProvider(name = "special chars")
    public Object[][] dataProviderMethod() {
        return new Object[][] { {"\"discrepa* scripts\""},
        	{"\"*screpancy in\""},
        	{"\"discre*cy in\""},
        	{"\"discrepancy i*\""},
        	{"\"discrepancy *n\""},
        	{"\"discr*ancy in\""},
        	{"\"discrepan* i*\""},
        	{"\"discrepan* script*\""},
        	{"\"discrepan* scripts\""},
        	{"\"discrepan* script*\""},
        	{"\"discrepan* scripts\""},
        	{"\"*screpancy scripts\"~3"},
        	{"\"discre*y scripts\"~3"},
        	{"\"*screpancy org*\""},
        	{"\"discre*y org\""},
        	{"\"discrepa* org\""},
        	{"\"*screpancy and*\""},
        	{"\"discre*y and\""},
        	{"\"discrepa* and\""},
        	{"\"*screpancy not*\""},
        	{"\"discre*y not*\""},
        	{"\"discrepa* not*\""},
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
        	{"\"discrepa? scripts\""},
        	{"\"?screpancy in\""},
        	{"\"discre?cy in\""},
        	{"\"discrepancy i?\""},
        	{"\"discrepancy ?n\""},
        	{"\"discr*ancy in\""},
        	{"\"discrepan? i?\""},
        	{"\"discrepan? script*\""},
        	{"\"discrepan? scripts\""},
        	{"\"discrepan? script*\""},
        	{"\"discrepan? scripts\""},
        	{"\"discrepan? scripts\""},
        	{"\"discrepan? scripts\""},
        	{"\"discrepan? scripts\""},
        	{"\"?screpancy scripts\"~3"},
        	{"\"discre*y scripts\"~3"},
        	{"\"?screpancy org?\""},
        	{"\"discre?y org\""},
        	{"\"discrepa? org\""},
        	{"\"?screpancy and?\""},
        	{"\"discre?y and\""},
        	{"\"discrepa? and\""},
        	{"\"?screpancy not?\""},
        	{"\"discre?y not?\""},
        	{"\"discrepa? not?\""},
        	{"\"discrepan* script?\"~3"},
        	{"\"discre*y scripts\"~3"},
        	{"\"*screpancy scripts\"~3"},
        	{"(\"?screpancy org?” OR \"*screpancy org*\" ) NOT \"discre?y org\"~3"},
        	{"\"discrepan? script*\"~3"},
        	{"\"*screpancy scripts\""},
        	{"\"discre*y scripts\""},
        	{"(\"?screpancy org?” OR \"*screpancy or\" ) NOT ((\"?screpancy and\" OR \"*screpancy and *\" ) AND (\"?screpancy not?\" OR \"*screpancy not\" ) ) OR "
        			+ "( \"?screpancy and?\"~3 OR \"?screpancy or?\"~3 OR \"?screpancy not\"~3)"},
        	{"\"?screpancy  and  scripts\" OR ( \"?screpancy  or scripts\" NOT \"*screpancy  not scripts\")"},
        	{"CustodianName: (Andrew)OR(\"?screpancy and scripts\" OR (\"?screpancy or scripts\")NOT(\"*screpancy not scripts\" )) AND \"?screpancy not?\"~3"},
        	{"\"discrepan* i?\""},
        	{"\"discrepan* script?\""},
        	{"\"discrepancy in\" OR \"discrepancy scripts\"~3"},
        	{"\"discrepancy scripts\"~3 OR \"discrepanc* in\"~3"},
        	{"\"discrepanc* in\"~3"},
        	
        	//new scenarios
        	/*{"stock investment~5"},
        	{"\"stock investment~5"},
        	{" stock investment\"~5"},
        	{"\"stock investment~5\""}*/
        };
    }
	@AfterClass(alwaysRun = true)
	public void close(){
		try{ 
			lp.logout();
		     //lp.quitBrowser();	
			}finally {
				lp.quitBrowser();
			}	
		LoginPage.clearBrowserCache();
	}
	
}
