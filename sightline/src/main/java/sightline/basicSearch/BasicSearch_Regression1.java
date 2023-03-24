package sightline.basicSearch;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.CodingForm;
import pageFactory.DocViewPage;
import pageFactory.DocViewRedactions;
import pageFactory.LoginPage;
import pageFactory.MiniDocListPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class BasicSearch_Regression1 {
	Driver driver;
	LoginPage lp;
	int pureHit;
	SoftAssert softAssertion;
	SessionSearch ss;
	BaseClass bc;
	DocViewRedactions docViewRedact ;
	MiniDocListPage miniDocListpage ;

	String searchName = "Search" + Utility.dynamicNameAppender();

	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {

		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		//Input in = new Input();
  // 	in.loadEnvConfig();
	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) throws Exception, InterruptedException, IOException {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		// Open browser
		softAssertion = new SoftAssert();
		driver = new Driver();
		bc = new BaseClass(driver);
		ss = new SessionSearch(driver);
		lp = new LoginPage(driver);
		docViewRedact = new DocViewRedactions(driver);
		miniDocListpage = new MiniDocListPage(driver);

	}

	
	/**
	 * @author Jeevitha
	 * 
	 *         Description - verifies the Query .
	 */
	@Test(description ="RPMXCON-57470", groups = { "regression" })
	public void verifyBasicSearch1() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57470  basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data = "\"Hello U&C\"~5 OR \"U&C\" OR ( \"##U&C[0-9]{2}\")";

		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();

	}
	/**
	 * @author Jeevitha
	 * 
	 *         Description - verifies the Query .
	 */
	@Test(description ="RPMXCON-57471", groups = { "regression" })
	public void verifyBasicSearch7() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57471 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data = "\"Hello U&C\"~5 OR \"U&C\"";

		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();

	}
	/**
	 * @author Jeevitha
	 * 
	 *         Description - verifies the Query ..
	 */
	@Test(description ="RPMXCON-57472", groups = { "regression" })
	public void verifyBasicSearch8() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57472 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data = "( \"economy finance\"~5  ) OR (\"product@consilio.com\")";

		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();

	}
	/**
	 * @author Jeevitha
	 * 
	 *         Description - verifies the Query .
	 */
	@Test(description ="RPMXCON-57452", groups = { "regression" })
	public void verifyBasicSearch9() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57452 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data = "(( \"Hello U{C\"~5 OR \"U{C\" OR ( \"##U}C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";

		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();

	}
		
	/**
	 * @author Jeevitha
	 * 
	 *         Description - verifies the Query
	 * 
	 */
	@Test(description ="RPMXCON-57453", groups = { "regression" })
	public void verifyBasicSearch2() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57453 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data = "(( \"Hello U|C\"~5 OR \"U|C\" OR ( \"##U|C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";

		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();

	}
	/**
	 * @author Jeevitha
	 * 
	 *         Description - verifies the Query
	 * 
	 */
	@Test(description ="RPMXCON-57454", groups = { "regression" })
	public void verifyBasicSearch10() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57454 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data = "(( \"Hello U}C\"~5 OR \"U}C\" OR ( \"##U}C[0-9]{2}\" OR EmailAllDomains: ( consilio.com) ) ) OR \"economy finance\"~5 ) AND Agi*";

		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();

	}
	/**
	 * @author Jeevitha
	 * 
	 *         Description - verifies the Query
	 *        
	 * 
	 */
	@Test(description ="RPMXCON-57469", groups = { "regression" })
	public void verifyBasicSearch11() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57469 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data = "\"Hello U&C\"~5 OR \"U&C\" OR ( \"##U&C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  )";

		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();

	}
	
	/**
	 * @author Jeevitha
	 * 
	 *         Description - verifies the Query
	 *        
	 */
	@Test(description ="RPMXCON-57468", groups = { "regression" })
	public void verifyBasicSearch3() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57468 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data ="( \"Hello U&C\"~5 OR \"U&C\" OR ( \"##U&C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) )";
		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();

	}
	/**
	 * @author Jeevitha
	 * 
	 *         Description - verifies the Query
	 *        
	 */
	@Test(description ="RPMXCON-57457", groups = { "regression" })
	public void verifyBasicSearch12() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57457 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data ="(( \"Hello U«C\"~5 OR \"U«C\" OR ( \"##U?C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";
		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();

	}
		
	/**
	 * @author Jeevitha
	 * 
	 *         Description - verifies the Query
	 * 
	 */
	@Test(description ="RPMXCON-57460", groups = { "regression" })
	public void verifyBasicSearch4() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57460 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data = "(( \"Hello  U?C \"~5 OR \"U?C \" OR ( \"##U?C [0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";
		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();

	}
	/**
	 * @author Jeevitha
	 * 
	 *         Description - verifies the Query
	 * 
	 */
	@Test(description ="RPMXCON-57458", groups = { "regression" })
	public void verifyBasicSearch13() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57458 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data = "(( \"Hello U?C\"~5 OR \"U?C\" OR ( \"##U?C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";
		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();

	}
	/**
	 * @author Jeevitha
	 * 
	 *         Description - verifies the Query
	 * 
	 */
	@Test(description ="RPMXCON-57459", groups = { "regression" })
	public void verifyBasicSearch14() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57459 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data = "(( \"Hello U?C\"~5 OR \"UU?C\" OR ( \"##UU?C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";
		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();

	}
	
	/**
	 * @author Jeevitha
	 * 
	 *         Description - verifies the Query 
	 * 
	 */
	@Test(description ="RPMXCON-57461", groups = { "regression" })
	public void verifyBasicSearch5() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57461 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data = "(( \"Hello U C\"~5 OR \"U C\" OR ( \"##U C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";
		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();

	}
	/**
	 * @author Jeevitha
	 * 
	 *         Description - verifies the Query 
	 * 
	 */
	@Test(description ="RPMXCON-57432", groups = { "regression" })
	public void verifyBasicSearch15() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57432 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data = "(( \"Hello U%C\"~5 OR \"U%C\" OR ( \"##U%C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";
		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();

	}
	/**
	 * @author Jeevitha
	 * 
	 *         Description - verifies the Query 
	 * 
	 */
	@Test(description ="RPMXCON-57431", groups = { "regression" })
	public void verifyBasicSearch16() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57431 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data = "(( \"Hello U$C\"~5 OR \"U$C\" OR ( \"##U$C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";
		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();

	}

	
	/**
	 * @author Jeevitha
	 * 
	 *         Description - verifies the Query 
	 * 
	 */
	@Test(description ="RPMXCON-57451", groups = { "regression" })
	public void verifyBasicSearch6() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57451 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data = "(( \"Hello U^C\"~5 OR \"U^C\" OR ( \"##U^C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";
		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();

	}
	/**
	 * @author Jeevitha
	 * 
	 *         Description - verifies the Query 
	 * 
	 */
	@Test(description ="RPMXCON-57449", groups = { "regression" })
	public void verifyBasicSearch17() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57449 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data = "(( \"Hello U\\C\"~5 OR \"U\\C\" OR ( \"##U\\C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";
		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();

	}
	/**
	 * @author Jeevitha
	 * 
	 *         Description - verifies the Query 
	 * 
	 */
	@Test(description ="RPMXCON-57447", groups = { "regression" })
	public void verifyBasicSearch18() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57447 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data = "(( \"Hello U?C\"~5 OR \"U?C\" OR ( \"##U?C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";
		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();

	}

	/**
	 * @author Jeevitha Description:Verify the basic search for Near Duplicates
	 *         Near
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-57001",groups = { "regression" })
	public void verifyCountOfSearch() throws ParseException, InterruptedException, IOException {
		// login as PA
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("RPMXCON-57001  BasicSearch ");

		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		// Verify Duplicate count and Family members count
		sessionSearchPage.basicContentSearch(Input.searchString1);

		System.out.println("Near duplicate count : " + sessionSearchPage.verifyNearDupeCount());
		bc.stepInfo("Near duplicate count : " + sessionSearchPage.verifyNearDupeCount());

		System.out.println("Family Members count : " + sessionSearchPage.verifyFamilyount());
		bc.stepInfo("Family Members count : " + sessionSearchPage.verifyFamilyount());

		bc.getPopupYesBtn();
		System.out.println("Succesfully Searched For Input " + Input.searchString2);
		lp.logout();
	}
	/**
	 * @author Jeevitha Description:Verify the basic search for Near Duplicates
	 *         Nea
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-57002",groups = { "regression" })
	public void verifyCountOfSearch2() throws ParseException, InterruptedException, IOException {
		// login as PA
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("RPMXCON-57002  BasicSearch ");

		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		// Verify Duplicate count and Family members count
		sessionSearchPage.basicContentSearch(Input.searchString1);

		System.out.println("Near duplicate count : " + sessionSearchPage.verifyNearDupeCount());
		bc.stepInfo("Near duplicate count : " + sessionSearchPage.verifyNearDupeCount());

		System.out.println("Family Members count : " + sessionSearchPage.verifyFamilyount());
		bc.stepInfo("Family Members count : " + sessionSearchPage.verifyFamilyount());

		bc.getPopupYesBtn();
		System.out.println("Succesfully Searched For Input " + Input.searchString2);
		lp.logout();
	}

	/**
	 * @author Jeevitha 
	 * Description:Basic search pin search item(RPMXCON-57030)
	 * @throws InterruptedException
	 */

	@Test(description ="RPMXCON-57030",groups = { "regression" })
	public void verifyPinSearch() throws InterruptedException {
		// login as Pa
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("RPMXCON-57030 Basic Search");

		SessionSearch sessionSearchPage = new SessionSearch(driver);
		sessionSearchPage.basicMetaDataSearch("CustodianName", null, "Andrew", null);
		sessionSearchPage.saveSearch(searchName);
		sessionSearchPage.getConceptuallyPlayButton().waitAndClick(10);
		sessionSearchPage.getPinBtn(searchName).waitAndClick(10);

		// Get title attribute value
		String tooltipText = sessionSearchPage.getUnpinToolTipMsg().GetAttribute("title");
		System.out.println("Retrieved tooltip text as :" + tooltipText);
		bc.stepInfo("Retrieved tooltip text as :" + tooltipText);

		// Verification if tooltip text is matching expected value
		if (tooltipText.equalsIgnoreCase("Un Pin this Search")) {
			System.out.println("Pass : Tooltip matching expected value");
			bc.stepInfo("Pass : Tooltip matching expected value");

		} else {
			System.out.println("Fail : Tooltip NOT matching expected value");
			bc.stepInfo("Fail : Tooltip NOT matching expected value");

		}
		System.out.println("Family Members count : " + sessionSearchPage.verifyFamilyount());
		bc.stepInfo("Family Members count : " + sessionSearchPage.verifyFamilyount());

		System.out.println("Conceptually similar count : " + sessionSearchPage.verifyConceptuallySimilarCount());
		bc.stepInfo("Conceptually similar count : " + sessionSearchPage.verifyConceptuallySimilarCount());
		lp.logout();
	}
	/**
	 * @author Jeevitha 
	 *         Description: Verify Family Member Count (RPMXCON-47255)
	 * @throws InterruptedException
	 */

	@Test(description ="RPMXCON-47255",groups = { "regression" })
	public void verifyPinSearch2() throws InterruptedException {
		// login as Pa
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("RPMXCON-47255 Basic Search");

		SessionSearch sessionSearchPage = new SessionSearch(driver);
		sessionSearchPage.basicMetaDataSearch("CustodianName", null, "Andrew", null);
		sessionSearchPage.saveSearch(searchName);
		sessionSearchPage.getConceptuallyPlayButton().waitAndClick(10);
		sessionSearchPage.getPinBtn(searchName).waitAndClick(10);

		// Get title attribute value
		String tooltipText = sessionSearchPage.getUnpinToolTipMsg().GetAttribute("title");
		System.out.println("Retrieved tooltip text as :" + tooltipText);
		bc.stepInfo("Retrieved tooltip text as :" + tooltipText);

		// Verification if tooltip text is matching expected value
		if (tooltipText.equalsIgnoreCase("Un Pin this Search")) {
			System.out.println("Pass : Tooltip matching expected value");
			bc.stepInfo("Pass : Tooltip matching expected value");

		} else {
			System.out.println("Fail : Tooltip NOT matching expected value");
			bc.stepInfo("Fail : Tooltip NOT matching expected value");

		}
		System.out.println("Family Members count : " + sessionSearchPage.verifyFamilyount());
		bc.stepInfo("Family Members count : " + sessionSearchPage.verifyFamilyount());

		System.out.println("Conceptually similar count : " + sessionSearchPage.verifyConceptuallySimilarCount());
		bc.stepInfo("Conceptually similar count : " + sessionSearchPage.verifyConceptuallySimilarCount());
		lp.logout();
	}
	/**
	 * @author Jeevitha
	 *  Decription :verify Conceptually similar Count(RPMXCON-47256)
	 * @throws InterruptedException
	 */

	@Test(description ="RPMXCON-47256",groups = { "regression" })
	public void verifyPinSearch3() throws InterruptedException {
		// login as Pa
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("RPMXCON-47256 Basic Search");

		SessionSearch sessionSearchPage = new SessionSearch(driver);
		sessionSearchPage.basicMetaDataSearch("CustodianName", null, "Andrew", null);
		sessionSearchPage.saveSearch(searchName);
		sessionSearchPage.getConceptuallyPlayButton().waitAndClick(10);
		sessionSearchPage.getPinBtn(searchName).waitAndClick(10);

		// Get title attribute value
		String tooltipText = sessionSearchPage.getUnpinToolTipMsg().GetAttribute("title");
		System.out.println("Retrieved tooltip text as :" + tooltipText);
		bc.stepInfo("Retrieved tooltip text as :" + tooltipText);

		// Verification if tooltip text is matching expected value
		if (tooltipText.equalsIgnoreCase("Un Pin this Search")) {
			System.out.println("Pass : Tooltip matching expected value");
			bc.stepInfo("Pass : Tooltip matching expected value");

		} else {
			System.out.println("Fail : Tooltip NOT matching expected value");
			bc.stepInfo("Fail : Tooltip NOT matching expected value");

		}
		System.out.println("Family Members count : " + sessionSearchPage.verifyFamilyount());
		bc.stepInfo("Family Members count : " + sessionSearchPage.verifyFamilyount());

		System.out.println("Conceptually similar count : " + sessionSearchPage.verifyConceptuallySimilarCount());
		bc.stepInfo("Conceptually similar count : " + sessionSearchPage.verifyConceptuallySimilarCount());
		lp.logout();
	}

	/**
	 * @author jeevitha Description : Verify that in "DocFileType" metadata session
	 *         search, when User tries to do manually keyed terms then Auto-suggest
	 *         value is NOT selected (Though presence of a space in Multiple
	 *         words)
	 * @throws InterruptedException
	 */
	

	@Test(description ="RPMXCON-46989", groups = { "regression" })
	public void verifyAutoSuggest() throws InterruptedException {
		// login as Pa
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		String data1="DocFileType";
		String data2="oth";
		bc.stepInfo("RPMXCON-46989 Basic Search");

		SessionSearch sessionSearchPage = new SessionSearch(driver);
		String autoSuggestedValue = sessionSearchPage.basicMetaDataAutosuggest(data1, data2, 1);
		System.out.println(autoSuggestedValue
				+ " auto-suggest value is selected (presence of a space in Multiple words) then multiple values that are being selected is wrapped in double quotes.  ");
		bc.stepInfo(autoSuggestedValue
				+ " auto-suggest value is selected (presence of a space in Multiple words) then multiple values that are being selected is wrapped in double quotes.  ");
		lp.logout();
	}
	/**
	 * @author jeevitha Description : Verify that in "DocFileType" metadata session
	 *         search, when User tries to do manually keyed terms then Auto-suggest
	 *         value is NOT selected (Though presence of a space in Multiple
	 *         words)
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-46990", groups = { "regression" })
	public void verifyAutoSuggest2() throws InterruptedException {
		// login as Pa
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		String data1="DocFileName";
		String data2="tes";
		bc.stepInfo("RPMXCON-46990 Basic Search");

		SessionSearch sessionSearchPage = new SessionSearch(driver);
		String autoSuggestedValue = sessionSearchPage.basicMetaDataAutosuggest(data1, data2, 1);
		System.out.println(autoSuggestedValue
				+ " auto-suggest value is selected (presence of a space in Multiple words) then multiple values that are being selected is wrapped in double quotes.  ");
		bc.stepInfo(autoSuggestedValue
				+ " auto-suggest value is selected (presence of a space in Multiple words) then multiple values that are being selected is wrapped in double quotes.  ");
		lp.logout();
	}
	
	/**
	 * @author Jeevitha
	 * Description : to verify as an user login into the Application, user will
	 *             be able to search based on comments text on Content &
	 *              Metadata in basic search(RPMXCON-47777)
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47777",groups = { "regression" })
	public void verifyComments() throws InterruptedException {
        String data=Input.searchString5;
		// login as Pa
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		bc.stepInfo("RPMXCON-47777 Basic Search");
		
		sessionSearchPage.basicContentSearch(Input.searchString1);
		sessionSearchPage.ViewInDocView();
		
		DocViewPage docview=new DocViewPage(driver);
		
		//Apply comments to document
		bc.waitForElement(docview.getDocument_CommentsTextBox());
		docview.getDocument_CommentsTextBox().SendKeys(data);
		bc.waitForElement(docview.getCodingFormSaveBtn());
		docview.getCodingFormSaveBtn().waitAndClick(10);
		
		bc.selectproject();
		//Search COmments as RMU
		int PureHit=sessionSearchPage.getCommentsOrRemarksCount(Input.documentComments, data);
		System.out.println("PureHit COunt : "+PureHit);
		bc.stepInfo("PureHit COunt : "+PureHit);

		lp.logout();
		//Search COmments as pa
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		int PureHit1=sessionSearchPage.getCommentsOrRemarksCount(Input.documentComments, data);
		System.out.println("PureHit COunt : "+PureHit1);
		bc.stepInfo("PureHit COunt : "+PureHit1);

		lp.logout();
		//Search COmments as REV
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		int PureHit2=sessionSearchPage.getCommentsOrRemarksCount(Input.documentComments, data);
		System.out.println("PureHit COunt : "+PureHit2);
		bc.stepInfo("PureHit COunt : "+PureHit2);

		lp.logout();
		
		
	}

	/**
	 * @author Jeevitha Description: when auto-suggest value is selected (presence
	 *         of a space in Multiple words) then corresponding value gets wrapped
	 *         in double quotes. 
	 * @throws InterruptedException
	 */

	@Test(description ="RPMXCON-46988", groups = { "regression" })
	public void verifyAutoSugges3() throws InterruptedException {

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("RPMXCON- " + "46988Basic Search");
		String data1="EmailToAddresses";
		String data2="sat";
		String data3="Satish.Pawal@Consilio.com";
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		String autoSuggestedValue = sessionSearchPage.validateAutosuggestSearchResult_BS(data3, data1, data2);
		lp.logout();

	}
	/**
	 * @author Jeevitha Description: when auto-suggest value is selected (presence
	 *         of a space in Multiple words) then corresponding value gets wrapped
	 *         in double quotes.
	 * @throws InterruptedException
	 */

	@Test(description ="RPMXCON-46985", groups = { "regression" })
	public void verifyAutoSugges2() throws InterruptedException {

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("RPMXCON- " + "46985Basic Search");
		String data1="CustodianName";
		String data2="P A";
		String data3="\"P Allen\"";
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		String autoSuggestedValue = sessionSearchPage.validateAutosuggestSearchResult_BS(data3, data1, data2);
		lp.logout();

	}

	/**
	 * @author Jeevitha Description: when auto-suggest value is selected (presence
	 *         of a space in Multiple words) then corresponding value gets wrapped
	 *         in double quotes
	 * @throws InterruptedException
	 */

	@Test(description ="RPMXCON-46984", groups = { "regression" })
	public void verifyAutoSugges() throws InterruptedException {

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("RPMXCON- " + "46984Basic Search");
		String data1="DocFileName";
		String data2="tes";
		String data3="\"Testing Special characater\"";
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		String autoSuggestedValue = sessionSearchPage.validateAutosuggestSearchResult_BS(data3, data1, data2);
		lp.logout();

	}

	
	/**
	 * @author Jeevitha Description: Verify that correct result appears when User
	 *         configured Expanded query format and having 'White Space' &
	 *         character.
	 *        
	 * @param data
	 * @param TC_ID
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-57446", groups = { "regression" })
	public void verifyBasicSearch21() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57446 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data="(( \"Hello U>C\"~5 OR \"U>C\" OR ( \"##U>C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";

		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();
	}
	/**
	 * @author Jeevitha Description: Verify that correct result appears when User
	 *         configured Expanded query format and having 'White Space' &
	 *         character.
	 *       
	 * @param data
	 * @param TC_ID
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-57445", groups = { "regression" })
	public void verifyBasicSearch20() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57445 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data="(( \"Hello U=C\"~5 OR \"U=C\" OR ( \"##U=C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";

		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();
	}

	/**
	 * @author Jeevitha Description: Verify that correct result appears when User
	 *         configured Expanded query format and having 'White Space' &
	 *         character.
	 *        
	 * @param data
	 * @param TC_ID
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-57443", groups = { "regression" })
	public void verifyBasicSearch19() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57443 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data="(( \"Hello U;C\"~5 OR \"U;C\" OR ( \"##U;C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";

		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();
	}
	
	/**
	 * @author Jeevitha Description: Verify that correct result appears when User
	 *         configured Expanded query format and having 'White Space' &
	 *         character.
	 *       
	 * @param data
	 * @param TC_ID
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-57440", groups = { "regression" })
	public void verifyBasicSearch24() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57440 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data="(( \"Hello U.C\"~5 OR \"U.C\" OR ( \"##U.C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";
		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();
	}
	
	/**
	 * @author Jeevitha Description: Verify that correct result appears when User
	 *         configured Expanded query format and having 'White Space' &
	 *         character.
	 *        
	 * @param data
	 * @param TC_ID
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-57441", groups = { "regression" })
	public void verifyBasicSearch23() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57441 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data="(( \"Hello U/C\"~5 OR \"U/C\" OR ( \"##U/C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";
		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();
	}
	
	/**
	 * @author Jeevitha Description: Verify that correct result appears when User
	 *         configured Expanded query format and having 'White Space' &
	 *         character.
	 *        
	 * @param data
	 * @param TC_ID
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-57442", groups = { "regression" })
	public void verifyBasicSearch22() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57442 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data="(( \"Hello U`C\"~5 OR \"U`C\" OR ( \"##U`C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";
		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();
	}
	
	/**
	 * @author Jeevitha Description: Verify that correct result appears when User
	 *         configured Expanded query format and having 'White Space' &
	 *         character.
	 *        
	 * @param data
	 * @param TC_ID
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-57437", groups = { "regression" })
	public void verifyBasicSearch27() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57437 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data="(( \"Hello U+C\"~5 OR \"U+C\" OR ( \"##U+C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";

		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();
	}
	/**
	 * @author Jeevitha Description: Verify that correct result appears when User
	 *         configured Expanded query format and having 'White Space' &
	 *         character.
	 *        
	 * @param data
	 * @param TC_ID
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-57438", groups = { "regression" })
	public void verifyBasicSearch26() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57438 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data="(( \"Hello U,C\"~5 OR \"U,C\" OR ( \"##U,C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";

		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();
	}
	
	/**
	 * @author Jeevitha Description: Verify that correct result appears when User
	 *         configured Expanded query format and having 'White Space' &
	 *         character.
	 *        
	 * @param data
	 * @param TC_ID
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-57439", groups = { "regression" })
	public void verifyBasicSearch25() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57439 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data="(( \"Hello U-C\"~5 OR \"U-C\" OR ( \"##U-C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";

		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();
	}
	

	/**
	 * @author Jeevitha Description: Verify that correct result appears when User
	 *         configured Expanded query format and having 'White Space' &
	 *         character.
	 *         
	 * @param data
	 * @param TC_ID
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-57430",groups = { "regression" })
	public void verifyBasicSearch30() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57430 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data="(( \"Hello U C\"~5 OR \"U C\" OR ( \"##U C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";

		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();
	}
	/**
	 * @author Jeevitha Description: Verify that correct result appears when User
	 *         configured Expanded query format and having 'White Space' &
	 *         character.
	 *         
	 * @param data
	 * @param TC_ID
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-57433",groups = { "regression" })
	public void verifyBasicSearch29() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57433 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data="(( \"Hello U'C\"~5 OR \"U'C\" OR ( \"##U'C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";

		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();
	}
	
	
	/**
	 * @author Jeevitha Description: Verify that correct result appears when User
	 *         configured Expanded query format and having 'White Space' &
	 *         character.
	 *         
	 * @param data
	 * @param TC_ID
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-57436",groups = { "regression" })
	public void verifyBasicSearch28() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57436 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data="(( \"Hello U*C\"~5 OR \"U*C\" OR ( \"##U*C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";

		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();
	}
	
	/**
	 * @author Jeevitha Description: Verify that correct result appears when User
	 *         configured Expanded query format and having 'White Space' &
	 *         character.
	 *         
	 * @param data
	 * @param TC_ID
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-57426", groups = { "regression" })
	public void verifyBasicSearch33() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57426 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data="(( \"Hello U&C\"~5 OR \"U&C\" OR ( \"##U&C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";

		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();
	}
	/**
	 * @author Jeevitha Description: Verify that correct result appears when User
	 *         configured Expanded query format and having 'White Space' &
	 *         character.
	 *        
	 * @param data
	 * @param TC_ID
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-57427", groups = { "regression" })
	public void verifyBasicSearch32() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57427 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data="(( \"Hello U!C\"~5 OR \"U!C\" OR ( \"##U!C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR    \"economy finance\"~5 ) AND Agi*";

		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();
	}
	/**
	 * @author Jeevitha Description: Verify that correct result appears when User
	 *         configured Expanded query format and having 'White Space' &
	 *         character.
	 *         (57429)
	 * @param data
	 * @param TC_ID
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-57429", groups = { "regression" })
	public void verifyBasicSearch31() throws ParseException, InterruptedException, IOException {
		// login as PA

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("57429 basic  Search");
		SessionSearch sessionSearchPage = new SessionSearch(driver);
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		String data="(( \"Hello U:C\"~5 OR \"U:C\" OR ( \"##U:C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";

		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(data, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);

		// verify pure hit
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return sessionSearchPage.getPureHitsCount().getText().matches("-?\\d+(\\.\\d+)?");
			}
		}), Input.wait120);
		int pureHit = Integer.parseInt(sessionSearchPage.getPureHitsCount().getText());
		System.out.println("purehit : " + pureHit);

		System.out.println(" successfully Proximity Warning is diplayed for input " + data);
		bc.stepInfo(" successfully Proximity Warning is diplayed for input " + data);
		lp.logout();
	}
	
	

	/**
	 * @author Jeevitha Description: Verify that in "compound query (workproduct and
	 *         content/metadata) " metadata session search, when auto-suggest value
	 *         is selected (presence of a space in Multiple words) then
	 *         corresponding value gets wrapped in double quotes. (RPMXCON-46993)
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-46993",groups = { "regression" })
	public void verifyCompoundQuery() throws InterruptedException {
		String tag = "TAGX" + Utility.dynamicNameAppender();
		String metadataFieldLabel = "DocFileName";
		String Value = "tes";
		String metaDataFileName = "\"TEST MAIL\"";
		String expectedFileDisplayInQuerySelection = "\"TEST MAIL\"";
		String expectedFileDisplay = " DocFileName: (\"TEST MAIL\") ";

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("RPMXCON-46993  Basic Search");

		SessionSearch sessionSearchPage = new SessionSearch(driver);
		sessionSearchPage.basicContentSearch(Input.searchString1);
		sessionSearchPage.bulkTag(tag);

		bc.selectproject();
		this.driver.getWebDriver().get(Input.url + "Search/Searches");
		sessionSearchPage.validateAutosuggestSearchResult_AS(metaDataFileName, metadataFieldLabel, Value,
				expectedFileDisplay, expectedFileDisplayInQuerySelection, false);
		sessionSearchPage.advancedSearchWorkProduct(tag);

		// condition
		bc.waitForElement(sessionSearchPage.getAs_SelectOperation(1));
		sessionSearchPage.getAs_SelectOperation(1).selectFromDropdown().selectByVisibleText("OR");
		int pureHit4 = sessionSearchPage.saveAndReturnPureHitCount();
		System.out.println("Document count of Compound Query is: " + pureHit4);
		bc.stepInfo("Document count of Compound Query is: " + pureHit4);

		lp.logout();
	}

	/**
	 * @author Jeevitha Description : Verify that in "EmailToAddress" metadata
	 *         session search, when auto-suggest value is selected (presence of a
	 *         space in Multiple words) then corresponding value gets wrapped in
	 *         double quotes.
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-46991",enabled = true, groups = { "regression" })
	public void verifyMetadataAutoSuggest() throws InterruptedException {
		String expectedFileName = "\"Scott Tholan\"";
		String metaDataField = "EmailToAddresses";
		String value = "sco";

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("RPMXCON- 46991   Basic Search");
		bc.stepInfo(
				"Verify that in \"EmailToAddress\" metadata session search, when auto-suggest value is selected (presence of a space in Multiple words) then corresponding value gets wrapped in double quotes.");

		SessionSearch sessionSearchPage = new SessionSearch(driver);
		sessionSearchPage.validateAutosuggestSearchResult_BS(expectedFileName, metaDataField, value);
		lp.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify the basic search for Threaded Documents[RPMXCON-47288]
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47288",enabled = true, groups = { "regression" })
	public void verifyThreadedDocs() throws InterruptedException {
		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("RPMXCON- 47288   Basic Search Sprint-10");
		bc.stepInfo("Verify the basic search for Threaded Documents");

		// search with Metadata & Operator and verify purehit
		ss.navigateToSessionSearchPageURL();
		ss.basicMetaDataDraftSearch(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);
		ss.selectOperatorInBasicSearch("OR");
		int pureHit = ss.basicContentSearchWithSaveChanges(Input.searchString1, "Yes", "Third");
		bc.stepInfo("Search Term Is : " + Input.metaDataName + " : " + Input.metaDataCustodianNameInput + " OR "
				+ Input.searchString1);
		softAssertion.assertNotEquals(pureHit, Input.TextEmpty);

		// verify Threaded document count
		String threadedCount = ss.verifyThreadedCount();
		bc.stepInfo("Threaded Document Count is: " + threadedCount);
		softAssertion.assertNotEquals(threadedCount, Input.TextEmpty);

		softAssertion.assertAll();
		lp.logout();
	}

	/**
	 * @Author Jeevitha
	 * @Description : Verify Search result should work correctly for Comments with
	 *              format like \"##PF[0-9]{4}\" [RPMXCON-49637]
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-49637",enabled = true, groups = { "regression" })
	public void verifySearchResultForComment() throws InterruptedException {
		String docComment = "PF1231";
		String searchTerm = "\"##PF[0-9]{4}\"";
		int count = 2;

		lp = new LoginPage(driver);
		DocViewPage docview = new DocViewPage(driver);
		lp.loginToSightLine(Input.rmu2userName, Input.rmu2password);

		bc.stepInfo("RPMXCON- 49637   Basic Search Sprint-10");
		bc.stepInfo("Verify Search result should work correctly for Comments with format like \"##PF[0-9]{4}\"");

		ss.basicContentSearch(Input.testData1);
		ss.ViewInDocView();

		// Apply comments to document
		docview.addCommentAndSave(docComment, true, count, true);
		lp.logout();

		// login As PA
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("Logged In as : " + Input.pa1FullName);
		int PureHit1 = ss.getCommentsOrRemarksCount(Input.documentComments, searchTerm);
		softAssertion.assertEquals(PureHit1, count);
		lp.logout();

		// login As RMU
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Logged In as : " + Input.rmu1FullName);
		int PureHit2 = ss.getCommentsOrRemarksCount(Input.documentComments, searchTerm);
		softAssertion.assertEquals(PureHit2, count);
		lp.logout();

		// login As REV
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		bc.stepInfo("Logged In as : " + Input.rev1FullName);
		int PureHit3 = ss.getCommentsOrRemarksCount(Input.documentComments, searchTerm);
		softAssertion.assertEquals(PureHit3, count);
		softAssertion.assertAll();
		lp.logout();

	}

	/**
	 * @author Raghuram A Date: 1/25/22 Modified date:N/A Modified by: N/A
	 *         Description : Verify that basic search is working properly for
	 *         Metadata RPMXCON-47712 Sprint 11
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47712",enabled = true, groups = { "regression" })
	public void basicSearchWithMedtaDataDetails() throws InterruptedException {

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);

		bc.stepInfo("RPMXCON- 47712 Basic Search Sprint-11");
		bc.stepInfo("Verify that basic search is working properly for Metadata");

		ss.basicMetaDataSearch(Input.metaDataName, null, Input.metaDataCustodianNameInput, null);
		ss.verifySearchReult(Input.metaDataCustodianNameInput, Input.metaDataName.toUpperCase(), "Equal", true);

		lp.logout();

	}

	/**
	 * @author Raghuram A Date: 1/28/22 Modified date:N/A Modified by: N/A
	 *         Description : Verify that basic search for Custodian is working
	 *         properly with Wildcard Character like * RPMXCON-47278 Sprint 11
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-47278",enabled = true, groups = { "regression" })
	public void basicSearchWithMedtaDataEildSearchDetails() throws InterruptedException {
		String wildcaseString = "An*";
		String wildcaseCompareString = "An";

		lp = new LoginPage(driver);
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);

		bc.stepInfo("RPMXCON- 47278 Basic Search Sprint-11");
		bc.stepInfo("Verify that basic search for Custodian is working properly with Wildcard Character like * ");

		ss.basicMetaDataSearch(Input.metaDataName, null, wildcaseString, null);
		ss.verifySearchReult(wildcaseCompareString, Input.metaDataName.toUpperCase(), "contains", false);

		lp.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that correct result appears when User configured
	 *              Expanded query format and having 'White Space' ~ character.
	 *              [RPMXCON-57455]
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-57455",enabled = true, groups = { "regression" })
	public void verifyProximityWarning() throws ParseException, InterruptedException, IOException {
		String searchTerm = "(( \"Hello U~C\"~5 OR \"U~C\" OR ( \"##U~C[0-9]{2}\" OR  EmailAllDomains: ( consilio.com)  ) ) OR \"economy finance\"~5 ) AND Agi*";
		lp = new LoginPage(driver);
		SessionSearch sessionSearchPage = new SessionSearch(driver);

		// login as PA
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("RPMXCON-57455 Basic Search Sprint-11");
		bc.stepInfo(
				"Verify that correct result appears when User configured Expanded query format and having 'White Space' ~ character.");

		// Verify Expanded Query
		sessionSearchPage.wrongQueryAlertBasicSaerch(searchTerm, 11, "non fielded", null);
		sessionSearchPage.getYesQueryAlert().waitAndClick(10);
		sessionSearchPage.returnPurehitCount();

		System.out.println(" successfully Proximity Warning is diplayed for input " + searchTerm);
		bc.passedStep(" successfully Proximity Warning is diplayed for input " + searchTerm);
		lp.logout();

	}

	/**
	 * @Author Raghuram A date:01/02/2022 Modified date: NA Modified by:N/A
	 * @Description :Verify that added reviewer remarks for audio documents is
	 *              working correctly in basic search [RPMXCON-46880]
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-46880",enabled = true, groups = { "regression" })
	public void VerifyAddedReviewerRemarkForAudioDocInBasicSearch() throws Exception {

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewPage docviewpage = new DocViewPage(driver);

		String remark = "Remark" + Utility.dynamicNameAppender();
		int iteration = 2;

		bc.stepInfo("Test case Id:RPMXCON-46880 Sprint 11");
		bc.stepInfo("Verify that added reviewer remarks for audio documents is working correctly in basic search");

		// login as reviewer
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		bc.stepInfo("Loggedin As : " + Input.rev1FullName);

		// adding remark to audio documents
		sessionSearch.audioSearch(Input.audioSearchString1, Input.language);
		sessionSearch.ViewInDocView();
		docviewpage.addRemarkToDocuments(iteration, remark);

		lp.logout();

		// login as PA
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		bc.stepInfo("Loggedin As : " + Input.pa1FullName);

		// configure comment Fields / Remarks
		int purehitCount1 = sessionSearch.getCommentsOrRemarksCount("Remark", remark);

		// verifying the remark with purehitCount
		bc.digitCompareEquals(iteration, purehitCount1,
				"The Count of Remark add to the Audio Documents is matches with the purehit Count", "mis-matches");

		lp.logout();

	}

	/**
	 * @Author Raghuram A date:02/02/2022 Modified date: NA Modified by:N/A
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (Right Curly Brace }) embedded within a
	 *              Regular Expression query.[RPMXCON-61622]
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-61622",enabled = true, groups = { "regression" })
	public void verifyRightCurlyBracesInSearch() throws Exception {

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);

		String[] datasToVerify = { "U&C", "U& C", "U C", "U/C", "U &C", "U!C", "U%C", "U:C", "U)C", "U(C", "U>C", "U<C",
				"U?C", "U=C", "U;C", "U & C", "U", "C" };
		List<String> docIDlist = new ArrayList<>();
		String wildInputString = Input.specialString1;

		bc.stepInfo("Test case Id:RPMXCON-61622 Sprint 11");
		bc.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (Right Curly Brace }) embedded within a Regular Expression query.");

		// login as RMU
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Search and View in DocView
		bc.stepInfo("Configured Regular Expression query with Right Curly Brace }   : " + wildInputString);
		sessionSearch.basicContentSearch(wildInputString);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();

		// Collect total doc datas
		docIDlist = miniDocListpage.getDocListDatas();
		docViewRedact.verifyContentPresentForListOfDocs(docIDlist, datasToVerify);

		bc.stepInfo(
				"---------------------------------------------------------------------------------------------------------------------------------------------------");
		bc.passedStep(
				"Right Curly Brace } treated as whitespace and it returned all documents  having word mentioned \"U and C Tester \"on basic search screen. like\r\n"
						+ "U&C Tester\r\n" + "U C Tester\r\n" + "U\\C Tester etc.");
		bc.stepInfo(
				"---------------------------------------------------------------------------------------------------------------------------------------------------");

		lp.logout();

	}

	/**
	 * @Author Raghuram A date:02/02/2022 Modified date: NA Modified by:N/A
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (Double Quote ") embedded within a Regular
	 *              Expression query.[RPMXCON-61590]
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-61590",enabled = true, groups = { "regression" })
	public void verifyDoubleQuotesInSearch() throws Exception {

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);

		String[] datasToVerify = { "U&C", "U& C", "U C", "U/C", "U &C", "U!C", "U%C", "U:C", "U)C", "U(C", "U>C", "U<C",
				"U?C", "U=C", "U;C", "U & C", "U", "C" };
		List<String> docIDlist = new ArrayList<>();
		String wildInputString = Input.specialString2;

		bc.stepInfo("Test case Id:RPMXCON-61590 Sprint 11");
		bc.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (Double Quote \") embedded within a Regular Expression query.");

		// login as RMU
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Search and View in DocView
		bc.stepInfo("Configured Regular Expression query with Double Quotes }   : " + wildInputString);
		sessionSearch.basicContentSearch(wildInputString);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();

		// Collect total doc datas
		docIDlist = miniDocListpage.getDocListDatas();
		docViewRedact.verifyContentPresentForListOfDocs(docIDlist, datasToVerify);

		bc.stepInfo(
				"---------------------------------------------------------------------------------------------------------------------------------------------------");
		bc.passedStep(
				"Double Quote treated as whitespace and it returned all documents  having word mentioned \"U and C Tester \"on basic search screen. like\r\n"
						+ "U&C Tester\r\n" + "U C Tester\r\n" + "U\\C Tester etc.");
		bc.stepInfo(
				"---------------------------------------------------------------------------------------------------------------------------------------------------");

		lp.logout();

	}

	/**
	 * @Author date:02/02/2022 Modified date: NA Modified by:N/A
	 * @Description : Verify that Application is not displaying warning message when
	 *              white-space character (Left Curly Brace {) embedded within a
	 *              Regular Expression query.[RPMXCON-61589]
	 * @throws ParseException
	 * @throws InterruptedException
	 * @throws IOException
	 */
	@Test(description ="RPMXCON-61589",enabled = true, groups = { "regression" })
	public void verifyLeftCurlyBracesInSearch() throws Exception {

		SessionSearch sessionSearch = new SessionSearch(driver);
		DocViewRedactions docViewRedact = new DocViewRedactions(driver);
		MiniDocListPage miniDocListpage = new MiniDocListPage(driver);

		String[] datasToVerify = { "U&C", "U& C", "U C", "U/C", "U &C", "U!C", "U%C", "U:C", "U)C", "U(C", "U>C", "U<C",
				"U?C", "U=C", "U;C", "U & C", "U", "C" };
		List<String> docIDlist = new ArrayList<>();
		String wildInputString = Input.specialString3;

		bc.stepInfo("Test case Id:RPMXCON-61589 Sprint 11");
		bc.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (Left Curly Brace {) embedded within a Regular Expression query.");

		// login as RMU
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Search and View in DocView
		bc.stepInfo("Configured Regular Expression query with Left Curly Brace }   : " + wildInputString);
		sessionSearch.basicContentSearch(wildInputString);
		sessionSearch.ViewInDocView();
		driver.waitForPageToBeReady();

		// Collect total doc datas
		docIDlist = miniDocListpage.getDocListDatas();
		docViewRedact.verifyContentPresentForListOfDocs(docIDlist, datasToVerify);

		bc.stepInfo(
				"---------------------------------------------------------------------------------------------------------------------------------------------------");
		bc.passedStep(
				"Left Curly Brace { treated as whitespace and it returned all documents  having word mentioned \"U and C Tester \"on basic search screen. like\r\n"
						+ "U&C Tester\r\n" + "U C Tester\r\n" + "U\\C Tester etc.");
		bc.stepInfo(
				"---------------------------------------------------------------------------------------------------------------------------------------------------");

		lp.logout();

	}

	/**
	 * @Author Jeevitha
	 * @Description :Verify that added comments for audio documents is working
	 *              correctly in Basic Search. [RPMXCON-46882]
	 * @return
	 */
	
	@DataProvider(name = "commentsCF")
	public Object[][] commentsCF() {
		Object[][] commentsCF = { { Input.pa1userName, Input.pa1password, true, false },
				{ Input.rev1userName, Input.rev1password, false, false },
				{ Input.rmu1userName, Input.rmu1password, false, true } };
		return commentsCF;
	}

	@Test(description ="RPMXCON-46882",enabled = true, groups = { "regression" })
	public void verifyCommentsForAudioDoc()
			throws InterruptedException {
		final String docComment = Input.comments+ Utility.dynamicNameAppender();
		String codingform = "CF" + Utility.dynamicNameAppender();
		int count = 1;

		lp = new LoginPage(driver);
		CodingForm codingForm = new CodingForm(driver);
		DocViewPage docview = new DocViewPage(driver);

		bc.stepInfo("RPMXCON- 46882 Basic Search Sprint-11");
		bc.stepInfo("Verify that added comments for audio documents is working correctly in Basic Search.");

		
			
			// Login as RMU
			lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);

			// Create coding form with comment
			codingForm.navigateToCodingFormPage();
			codingForm.addNewCodingFormButton();
			codingForm.basedOnCreatingNewObject(null, Input.documentComments, null, Input.comments);
			codingForm.addcodingFormAddButton();
			codingForm.passingCodingFormName(codingform);
			codingForm.saveCodingForm();

			// set coding form as SG
			codingForm.assignCodingFormToSG(codingform);

			// Apply comments to document
			ss.audioSearch(Input.audioSearch, Input.language);
			ss.ViewInDocView();
			docview.addCommentAndSave(docComment, true, count, false);
			
			lp.logout();
		

		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		int PureHit1 = ss.getCommentsOrRemarksCount(Input.documentComments, docComment);
		softAssertion.assertEquals(PureHit1, count);
		lp.logout();
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		int PureHit2 = ss.getCommentsOrRemarksCount(Input.documentComments, docComment);
		softAssertion.assertEquals(PureHit2, count);
		lp.logout();
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		int PureHit = ss.getCommentsOrRemarksCount(Input.documentComments, docComment);
		softAssertion.assertEquals(PureHit, count);

		
			codingForm.assignCodingFormToSG(Input.codeFormName);
		

		softAssertion.assertAll();
		lp.logout();
	}

	@Test(description ="RPMXCON-61593",enabled = true, groups = { "regression" })
	public void verifyExclamatorymarkInSearch() throws Exception {

		String[] datasToVerify = { "U&C", "U& C", "U C", "U/C", "U &C", "U!C", "U%C", "U:C", "U)C", "U(C", "U>C", "U<C",
				"U?C", "U=C", "U;C", "U & C", "U", "C" };
		List<String> docIDlist = new ArrayList<>();
		String wildInputString = Input.specialString4;

		bc.stepInfo("Test case Id:RPMXCON-61593 Sprint 11");
		bc.stepInfo(
				"Verify that Application is not displaying warning message when white-space character (exclamation mark ! ) embedded within a Regular Expression query.");
		// login as RMU
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("Loggedin As : " + Input.rmu1FullName);

		// Search and View in DocView
		bc.stepInfo("Configured Regular Expression query with Right Curly Brace }   : " + wildInputString);
		
		ss.basicContentSearch(wildInputString);
		ss.ViewInDocView();
		driver.waitForPageToBeReady();

		// Collect total doc datas
		docIDlist = miniDocListpage.getDocListDatas();
		docViewRedact.verifyContentPresentForListOfDocs(docIDlist, datasToVerify);

		bc.stepInfo(
				"---------------------------------------------------------------------------------------------------------------------------------------------------");
		bc.passedStep(
				"Right Curly Brace } treated as whitespace and it returned all documents  having word mentioned \"U and C Tester \"on basic search screen. like\r\n"
						+ "U&C Tester\r\n" + "U C Tester\r\n" + "U\\C Tester etc.");
		bc.stepInfo(
				"---------------------------------------------------------------------------------------------------------------------------------------------------");

		lp.logout();

	}


	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());
			lp.logoutWithoutAssert();
		}
		try {
			lp.quitBrowser();
		} catch (Exception e) {
			lp.quitBrowser();
		}
	}

}
