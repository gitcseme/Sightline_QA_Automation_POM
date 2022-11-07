package sightline.basicSearch;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.LoginPage;
import pageFactory.SavedSearch;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class PerformanceTest_Regression {

	Driver driver;
	LoginPage lp;
	int pureHit;
	SoftAssert softAssertion;
	SessionSearch session;
	BaseClass base;
	SavedSearch saveSearch;

	/**
	 * @Author : Jeevitha
	 * @Description : The following test scenario shall comapare the pure hit counts
	 *              between two search criteria. The first test case validates that
	 *              the second search pure hit should greater than/equal to first
	 *              one. The second test case also validates that the second search
	 *              pure hit should be greated than/equal to first one.
	 *
	 *              Note: In future if the dataset change and the pure hit count
	 *              changes, make sure that the First search criteria should have
	 *              less/equal pure hit count than the second search criteria.
	 */
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws ParseException, InterruptedException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");

		// Open browser
		softAssertion = new SoftAssert();
	//	Input in = new Input();
    //	in.loadEnvConfig();
	
	}
	
	
	/**
	 * @author jeevitha Description: validate expanded query. test case no:-
	 *         RPMXCON-57422
	 * @param data1
	 * @param data2
	 * @param TC_Id
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-57422", groups = { "regression" })
	public void basicSearch9() throws InterruptedException {
		String dataSet[][] = { { Input.pa1userName, Input.pa1password }, 
				{ Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } 
				};
		String data1="transport";
		String data2="\"##[0-9]\"";

		for (int i = 0; i < dataSet.length; i++) {
			int j = 0;
			String search1 = "Search" + Utility.dynamicNameAppender();
			String search2 = "Search" + Utility.dynamicNameAppender();

			String username1 = dataSet[i][j];
			j++;
			String password2 = dataSet[i][j];
			// login
			lp = new LoginPage(driver);
			lp.loginToSightLine(username1, password2);
			base.stepInfo(" Basic Search");

			// Create saved search for first First Query
			driver.getWebDriver().get(Input.url + "Search/Searches");
			int pureHit1 = session.basicContentSearch(data1);
			
			session.saveSearchInNewNode(search1, null);
			System.out.println(pureHit1);

			// Add Operator and Search Second query
			session.selectOperatorInBasicSearch("OR");
			int pureHit2 = session.basicContentSearchWithSaveChanges(data2, "Yes", "Third");
			
			session.saveSearchInNewNode(search2, null);
			System.out.println(pureHit2);

			// Verify Purehit count
			if (pureHit1 == pureHit2) {
				System.out.println("PureHit1 : " + pureHit1 + " Is Equal To " + " Purehit2 : " + pureHit2);
				base.stepInfo("PureHit1 : " + pureHit1 + " Is Equal To " + " Purehit2 : " + pureHit2);
			} else if (pureHit2 > pureHit1) {
				System.out.println("PureHit2 : " + pureHit2 + " Is Greater Than " + " PureHit1 : " + pureHit1);
				base.stepInfo("PureHit2 : " + pureHit2 + " Is Greater Than " + " PureHit1 : " + pureHit1);
			} else {
				System.out.println("PureHit2 : " + pureHit2 + "," + "PureHit1 : " + pureHit1);
				base.stepInfo("PureHit2 : " + pureHit2 + "," + "PureHit1 : " + pureHit1);
			}

			// Execute the Second Search
			saveSearch.savedSearchExecute(search2, pureHit2);
			Thread.sleep(3000);
			lp.logout();

		}
	}
	/**
	 * @author jeevitha Description: validate expanded query. test case no:-
	 *         RPMXCON-57423
	 * @param data1
	 * @param data2
	 * @param TC_Id
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-57423", groups = { "regression" })
	public void basicSearch8() throws InterruptedException {
		String dataSet[][] = { { Input.pa1userName, Input.pa1password }, 
				{ Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } 
				};
		String data1="CustodianName: ( Allen)";
		String data2="\"##[0-9]\"";

		for (int i = 0; i < dataSet.length; i++) {
			int j = 0;
			String search1 = "Search" + Utility.dynamicNameAppender();
			String search2 = "Search" + Utility.dynamicNameAppender();

			String username1 = dataSet[i][j];
			j++;
			String password2 = dataSet[i][j];
			// login
			lp = new LoginPage(driver);
			lp.loginToSightLine(username1, password2);
			base.stepInfo(" Basic Search");

			// Create saved search for first First Query
			driver.getWebDriver().get(Input.url + "Search/Searches");
			int pureHit1 = session.basicContentSearch(data1);
			
			session.saveSearchInNewNode(search1, null);
			System.out.println(pureHit1);

			// Add Operator and Search Second query
			session.selectOperatorInBasicSearch("OR");
			int pureHit2 = session.basicContentSearchWithSaveChanges(data2, "Yes", "Third");
			
			session.saveSearchInNewNode(search2, null);
			System.out.println(pureHit2);

			// Verify Purehit count
			if (pureHit1 == pureHit2) {
				System.out.println("PureHit1 : " + pureHit1 + " Is Equal To " + " Purehit2 : " + pureHit2);
				base.stepInfo("PureHit1 : " + pureHit1 + " Is Equal To " + " Purehit2 : " + pureHit2);
			} else if (pureHit2 > pureHit1) {
				System.out.println("PureHit2 : " + pureHit2 + " Is Greater Than " + " PureHit1 : " + pureHit1);
				base.stepInfo("PureHit2 : " + pureHit2 + " Is Greater Than " + " PureHit1 : " + pureHit1);
			} else {
				System.out.println("PureHit2 : " + pureHit2 + "," + "PureHit1 : " + pureHit1);
				base.stepInfo("PureHit2 : " + pureHit2 + "," + "PureHit1 : " + pureHit1);
			}

			// Execute the Second Search
			saveSearch.savedSearchExecute(search2, pureHit2);
			Thread.sleep(3000);
			lp.logout();

		}
	}
	


	/**
	 * @author jeevitha Description: validate expanded query. test case no:-
	 *         RPMXCON-57424
	 * @param data1
	 * @param data2
	 * @param TC_Id
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-57424", groups = { "regression" })
	public void basicSearch4() throws InterruptedException {
		String dataSet[][] = { { Input.pa1userName, Input.pa1password }, 
				{ Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } 
				};
		String data1="information";
		String data2="\"money power\"~2";

		for (int i = 0; i < dataSet.length; i++) {
			int j = 0;
			String search1 = "Search" + Utility.dynamicNameAppender();
			String search2 = "Search" + Utility.dynamicNameAppender();

			String username1 = dataSet[i][j];
			j++;
			String password2 = dataSet[i][j];
			// login
			lp = new LoginPage(driver);
			lp.loginToSightLine(username1, password2);
			base.stepInfo(" Basic Search");

			// Create saved search for first First Query
			driver.getWebDriver().get(Input.url + "Search/Searches");
			int pureHit1 = session.basicContentSearch(data1);
			
			session.saveSearchInNewNode(search1, null);
			System.out.println(pureHit1);

			// Add Operator and Search Second query
			session.selectOperatorInBasicSearch("OR");
			int pureHit2 = session.basicContentSearchWithSaveChanges(data2, "Yes", "Third");
			
			session.saveSearchInNewNode(search2, null);
			System.out.println(pureHit2);

			// Verify Purehit count
			if (pureHit1 == pureHit2) {
				System.out.println("PureHit1 : " + pureHit1 + " Is Equal To " + " Purehit2 : " + pureHit2);
				base.stepInfo("PureHit1 : " + pureHit1 + " Is Equal To " + " Purehit2 : " + pureHit2);
			} else if (pureHit2 > pureHit1) {
				System.out.println("PureHit2 : " + pureHit2 + " Is Greater Than " + " PureHit1 : " + pureHit1);
				base.stepInfo("PureHit2 : " + pureHit2 + " Is Greater Than " + " PureHit1 : " + pureHit1);
			} else {
				System.out.println("PureHit2 : " + pureHit2 + "," + "PureHit1 : " + pureHit1);
				base.stepInfo("PureHit2 : " + pureHit2 + "," + "PureHit1 : " + pureHit1);
			}

			// Execute the Second Search
			saveSearch.savedSearchExecute(search2, pureHit2);
			Thread.sleep(3000);
			lp.logout();

		}
	}
	
	
	/**
	 * @author jeevitha Description: validate expanded query. test case no:-
	 *         (RPMXCON-57425)
	 *        
	 * @param data1
	 * @param data2
	 * @param TC_Id
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-57425", groups = { "regression" })
	public void basicSearch11() throws InterruptedException {
		String dataSet[][] = { { Input.pa1userName, Input.pa1password }, 
				{ Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } 
				};
		String data1="transport";
		String data2="\"##[0-9]*\"";

		for (int i = 0; i < dataSet.length; i++) {
			int j = 0;
			String search1 = "Search" + Utility.dynamicNameAppender();
			String search2 = "Search" + Utility.dynamicNameAppender();

			String username1 = dataSet[i][j];
			j++;
			String password2 = dataSet[i][j];
			// login
			lp = new LoginPage(driver);
			lp.loginToSightLine(username1, password2);
			base.stepInfo(" Basic Search");

			// Create saved search for first First Query
			driver.getWebDriver().get(Input.url + "Search/Searches");
			int pureHit1 = session.basicContentSearch(data1);
			
			session.saveSearchInNewNode(search1, null);
			System.out.println(pureHit1);

			// Add Operator and Search Second query
			session.selectOperatorInBasicSearch("OR");
			int pureHit2 = session.basicContentSearchWithSaveChanges(data2, "Yes", "Third");
			
			session.saveSearchInNewNode(search2, null);
			System.out.println(pureHit2);

			// Verify Purehit count
			if (pureHit1 == pureHit2) {
				System.out.println("PureHit1 : " + pureHit1 + " Is Equal To " + " Purehit2 : " + pureHit2);
				base.stepInfo("PureHit1 : " + pureHit1 + " Is Equal To " + " Purehit2 : " + pureHit2);
			} else if (pureHit2 > pureHit1) {
				System.out.println("PureHit2 : " + pureHit2 + " Is Greater Than " + " PureHit1 : " + pureHit1);
				base.stepInfo("PureHit2 : " + pureHit2 + " Is Greater Than " + " PureHit1 : " + pureHit1);
			} else {
				System.out.println("PureHit2 : " + pureHit2 + "," + "PureHit1 : " + pureHit1);
				base.stepInfo("PureHit2 : " + pureHit2 + "," + "PureHit1 : " + pureHit1);
			}

			// Execute the Second Search
			saveSearch.savedSearchExecute(search2, pureHit2);
			Thread.sleep(3000);
			lp.logout();

		}
	}
	/**
	 * @author jeevitha Description: validate expanded query. test case no:-
	 *         (RPMXCON-57464, RPMXCON-57463,RPMXCON-57425)
	 *        
	 * @param data1
	 * @param data2
	 * @param TC_Id
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-57463", groups = { "regression" })
	public void basicSearch10() throws InterruptedException {
		String dataSet[][] = { { Input.pa1userName, Input.pa1password }, 
				{ Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } 
				};
		String data1="\"money power\"~2";
		String data2="CustodianName: ( Allen)";

		for (int i = 0; i < dataSet.length; i++) {
			int j = 0;
			String search1 = "Search" + Utility.dynamicNameAppender();
			String search2 = "Search" + Utility.dynamicNameAppender();

			String username1 = dataSet[i][j];
			j++;
			String password2 = dataSet[i][j];
			// login
			lp = new LoginPage(driver);
			lp.loginToSightLine(username1, password2);
			base.stepInfo(" Basic Search");

			// Create saved search for first First Query
			driver.getWebDriver().get(Input.url + "Search/Searches");
			int pureHit1 = session.basicContentSearch(data1);
			
			session.saveSearchInNewNode(search1, null);
			System.out.println(pureHit1);

			// Add Operator and Search Second query
			session.selectOperatorInBasicSearch("OR");
			int pureHit2 = session.basicContentSearchWithSaveChanges(data2, "Yes", "Third");
			
			session.saveSearchInNewNode(search2, null);
			System.out.println(pureHit2);

			// Verify Purehit count
			if (pureHit1 == pureHit2) {
				System.out.println("PureHit1 : " + pureHit1 + " Is Equal To " + " Purehit2 : " + pureHit2);
				base.stepInfo("PureHit1 : " + pureHit1 + " Is Equal To " + " Purehit2 : " + pureHit2);
			} else if (pureHit2 > pureHit1) {
				System.out.println("PureHit2 : " + pureHit2 + " Is Greater Than " + " PureHit1 : " + pureHit1);
				base.stepInfo("PureHit2 : " + pureHit2 + " Is Greater Than " + " PureHit1 : " + pureHit1);
			} else {
				System.out.println("PureHit2 : " + pureHit2 + "," + "PureHit1 : " + pureHit1);
				base.stepInfo("PureHit2 : " + pureHit2 + "," + "PureHit1 : " + pureHit1);
			}

			// Execute the Second Search
			saveSearch.savedSearchExecute(search2, pureHit2);
			Thread.sleep(3000);
			lp.logout();

		}
	}
	/**
	 * @author jeevitha Description: validate expanded query. test case no:-
	 *         (RPMXCON-57464, RPMXCON-57463,RPMXCON-57425)
	 *        
	 * @param data1
	 * @param data2
	 * @param TC_Id
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-57464", groups = { "regression" })
	public void basicSearch5() throws InterruptedException {
		String dataSet[][] = { { Input.pa1userName, Input.pa1password }, 
				{ Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } 
				};
		String data1="\"##[0-9]\"";
		String data2="information";

		for (int i = 0; i < dataSet.length; i++) {
			int j = 0;
			String search1 = "Search" + Utility.dynamicNameAppender();
			String search2 = "Search" + Utility.dynamicNameAppender();

			String username1 = dataSet[i][j];
			j++;
			String password2 = dataSet[i][j];
			// login
			lp = new LoginPage(driver);
			lp.loginToSightLine(username1, password2);
			base.stepInfo(" Basic Search");

			// Create saved search for first First Query
			driver.getWebDriver().get(Input.url + "Search/Searches");
			int pureHit1 = session.basicContentSearch(data1);
			
			session.saveSearchInNewNode(search1, null);
			System.out.println(pureHit1);

			// Add Operator and Search Second query
			session.selectOperatorInBasicSearch("OR");
			int pureHit2 = session.basicContentSearchWithSaveChanges(data2, "Yes", "Third");
			
			session.saveSearchInNewNode(search2, null);
			System.out.println(pureHit2);

			// Verify Purehit count
			if (pureHit1 == pureHit2) {
				System.out.println("PureHit1 : " + pureHit1 + " Is Equal To " + " Purehit2 : " + pureHit2);
				base.stepInfo("PureHit1 : " + pureHit1 + " Is Equal To " + " Purehit2 : " + pureHit2);
			} else if (pureHit2 > pureHit1) {
				System.out.println("PureHit2 : " + pureHit2 + " Is Greater Than " + " PureHit1 : " + pureHit1);
				base.stepInfo("PureHit2 : " + pureHit2 + " Is Greater Than " + " PureHit1 : " + pureHit1);
			} else {
				System.out.println("PureHit2 : " + pureHit2 + "," + "PureHit1 : " + pureHit1);
				base.stepInfo("PureHit2 : " + pureHit2 + "," + "PureHit1 : " + pureHit1);
			}

			// Execute the Second Search
			saveSearch.savedSearchExecute(search2, pureHit2);
			Thread.sleep(3000);
			lp.logout();

		}
	}
	
	/**
	 * @author jeevitha Description: validate expanded query. test case no:-
	 *         (RPMXCON-57467,RPMXCON-57466,RPMXCON-57465)
	 * @param data1
	 * @param data2
	 * @param TC_Id
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-57465", groups = { "regression" })
	public void basicSearch13() throws InterruptedException {
		String dataSet[][] = { { Input.pa1userName, Input.pa1password }, 
				{ Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } 
				};
		String data1= "\"##[0-9]\"";
		String data2="CustodianName: ( Allen)";

		for (int i = 0; i < dataSet.length; i++) {
			int j = 0;
			String search1 = "Search" + Utility.dynamicNameAppender();
			String search2 = "Search" + Utility.dynamicNameAppender();

			String username1 = dataSet[i][j];
			j++;
			String password2 = dataSet[i][j];
			// login
			lp = new LoginPage(driver);
			lp.loginToSightLine(username1, password2);
			base.stepInfo(" Basic Search");

			// Create saved search for first First Query
			driver.getWebDriver().get(Input.url + "Search/Searches");
			int pureHit1 = session.basicContentSearch(data1);
			
			session.saveSearchInNewNode(search1, null);
			System.out.println(pureHit1);

			// Add Operator and Search Second query
			session.selectOperatorInBasicSearch("OR");
			int pureHit2 = session.basicContentSearchWithSaveChanges(data2, "Yes", "Third");
			
			session.saveSearchInNewNode(search2, null);
			System.out.println(pureHit2);

			// Verify Purehit count
			if (pureHit1 == pureHit2) {
				System.out.println("PureHit1 : " + pureHit1 + " Is Equal To " + " Purehit2 : " + pureHit2);
				base.stepInfo("PureHit1 : " + pureHit1 + " Is Equal To " + " Purehit2 : " + pureHit2);
			} else if (pureHit2 > pureHit1) {
				System.out.println("PureHit2 : " + pureHit2 + " Is Greater Than " + " PureHit1 : " + pureHit1);
				base.stepInfo("PureHit2 : " + pureHit2 + " Is Greater Than " + " PureHit1 : " + pureHit1);
			} else {
				System.out.println("PureHit2 : " + pureHit2 + "," + "PureHit1 : " + pureHit1);
				base.stepInfo("PureHit2 : " + pureHit2 + "," + "PureHit1 : " + pureHit1);
			}

			// Execute the Second Search
			saveSearch.savedSearchExecute(search2, pureHit2);
			Thread.sleep(3000);
			lp.logout();

		}
	}
	/**
	 * @author jeevitha Description: validate expanded query. test case no:-
	 *         (RPMXCON-57467,RPMXCON-57466,RPMXCON-57465)
	 * @param data1
	 * @param data2
	 * @param TC_Id
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-57466", groups = { "regression" })
	public void basicSearch12() throws InterruptedException {
		String dataSet[][] = { { Input.pa1userName, Input.pa1password }, 
				{ Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } 
				};
		String data1= "CustodianName: ( Allen)";
		String data2="\"##[0-9]*\"";

		for (int i = 0; i < dataSet.length; i++) {
			int j = 0;
			String search1 = "Search" + Utility.dynamicNameAppender();
			String search2 = "Search" + Utility.dynamicNameAppender();

			String username1 = dataSet[i][j];
			j++;
			String password2 = dataSet[i][j];
			// login
			lp = new LoginPage(driver);
			lp.loginToSightLine(username1, password2);
			base.stepInfo(" Basic Search");

			// Create saved search for first First Query
			driver.getWebDriver().get(Input.url + "Search/Searches");
			int pureHit1 = session.basicContentSearch(data1);
			
			session.saveSearchInNewNode(search1, null);
			System.out.println(pureHit1);

			// Add Operator and Search Second query
			session.selectOperatorInBasicSearch("OR");
			int pureHit2 = session.basicContentSearchWithSaveChanges(data2, "Yes", "Third");
			
			session.saveSearchInNewNode(search2, null);
			System.out.println(pureHit2);

			// Verify Purehit count
			if (pureHit1 == pureHit2) {
				System.out.println("PureHit1 : " + pureHit1 + " Is Equal To " + " Purehit2 : " + pureHit2);
				base.stepInfo("PureHit1 : " + pureHit1 + " Is Equal To " + " Purehit2 : " + pureHit2);
			} else if (pureHit2 > pureHit1) {
				System.out.println("PureHit2 : " + pureHit2 + " Is Greater Than " + " PureHit1 : " + pureHit1);
				base.stepInfo("PureHit2 : " + pureHit2 + " Is Greater Than " + " PureHit1 : " + pureHit1);
			} else {
				System.out.println("PureHit2 : " + pureHit2 + "," + "PureHit1 : " + pureHit1);
				base.stepInfo("PureHit2 : " + pureHit2 + "," + "PureHit1 : " + pureHit1);
			}

			// Execute the Second Search
			saveSearch.savedSearchExecute(search2, pureHit2);
			Thread.sleep(3000);
			lp.logout();

		}
	}

	
	/**
	 * @author jeevitha Description: validate expanded query. test case no:-
	 *         (RPMXCON-57467,RPMXCON-57466,RPMXCON-57465)
	 * @param data1
	 * @param data2
	 * @param TC_Id
	 * @throws InterruptedException
	 */
	@Test(description ="RPMXCON-57467", groups = { "regression" })
	public void basicSearch6() throws InterruptedException {
		String dataSet[][] = { { Input.pa1userName, Input.pa1password }, 
				{ Input.rmu1userName, Input.rmu1password },
				{ Input.rev1userName, Input.rev1password } 
				};
		String data1="\"##[0-9]\"";
		String data2="\"money power*\"";

		for (int i = 0; i < dataSet.length; i++) {
			int j = 0;
			String search1 = "Search" + Utility.dynamicNameAppender();
			String search2 = "Search" + Utility.dynamicNameAppender();

			String username1 = dataSet[i][j];
			j++;
			String password2 = dataSet[i][j];
			// login
			lp = new LoginPage(driver);
			lp.loginToSightLine(username1, password2);
			base.stepInfo(" Basic Search");

			// Create saved search for first First Query
			driver.getWebDriver().get(Input.url + "Search/Searches");
			int pureHit1 = session.basicContentSearch(data1);
			
			session.saveSearchInNewNode(search1, null);
			System.out.println(pureHit1);

			// Add Operator and Search Second query
			session.selectOperatorInBasicSearch("OR");
			int pureHit2 = session.basicContentSearchWithSaveChanges(data2, "Yes", "Third");
			
			session.saveSearchInNewNode(search2, null);
			System.out.println(pureHit2);

			// Verify Purehit count
			if (pureHit1 == pureHit2) {
				System.out.println("PureHit1 : " + pureHit1 + " Is Equal To " + " Purehit2 : " + pureHit2);
				base.stepInfo("PureHit1 : " + pureHit1 + " Is Equal To " + " Purehit2 : " + pureHit2);
			} else if (pureHit2 > pureHit1) {
				System.out.println("PureHit2 : " + pureHit2 + " Is Greater Than " + " PureHit1 : " + pureHit1);
				base.stepInfo("PureHit2 : " + pureHit2 + " Is Greater Than " + " PureHit1 : " + pureHit1);
			} else {
				System.out.println("PureHit2 : " + pureHit2 + "," + "PureHit1 : " + pureHit1);
				base.stepInfo("PureHit2 : " + pureHit2 + "," + "PureHit1 : " + pureHit1);
			}

			// Execute the Second Search
			saveSearch.savedSearchExecute(search2, pureHit2);
			Thread.sleep(3000);
			lp.logout();

		}
	}


	@DataProvider(name = "SavedSearchwithUsers")
	public Object[][] SavedSearchwithUsers() {
		Object[][] users = { { Input.pa1userName, Input.pa1password, Input.pa1FullName },
				{ Input.rmu1userName, Input.rmu1password, Input.rmu1FullName },
				{ Input.rev1userName, Input.rev1password, Input.rev1FullName } };
		return users;
	}

	/*
	 * @author Jeevitha
	 * Description : Validate Batch upload with expanded search queries containing Wildcard Phrase, Proximity and regular expression
	 *              (RPMXCON- 57462)
	 *              
	 *   Since Only 5K document is present  , Scripted with available data           
	 */
	@Test(description ="RPMXCON-57462",enabled = true,dataProvider = "SavedSearchwithUsers", groups = { "regression" })
	public void validateBatchFileCount(String username,String password,String fullname) throws Exception {
		String File = saveSearch.renameFile(Input.performaceBatchFile);
		boolean batchUpload = true;
		String filePath = System.getProperty("user.dir") + Input.performaceBatchFile + File;
		int value = 10000;

		// login
		lp = new LoginPage(driver);
		lp.loginToSightLine(username,password);
		base.stepInfo("Test case Id: RPMXCON-57462 Saved Search");
		base.stepInfo("Validate Batch upload with expanded search queries containing Wildcard Phrase, Proximity and regular expression");
		
		String[][] record = base.readExcelData(filePath,1);
		for (String[] data : record) {
			if (data[0] != null) {
				String Name =data[0];
				String DocumentContent =data[1];
				
				base.selectproject();
				int pureHits = session.basicContentSearch(DocumentContent);
				// upload batch File And verify Status
				driver.getWebDriver().get(Input.url + "SavedSearch/SavedSearches");
				if (batchUpload) {

					saveSearch.uploadWPBatchFile_New(File, Input.performaceBatchFile);
					batchUpload = false;
				}
				driver.Navigate().refresh();
				saveSearch.getMySeraches().waitAndClick(10);
				base.waitForElement(saveSearch.getSelectUploadedFile(File));
				saveSearch.getSelectUploadedFile(File).waitAndClick(20);

				saveSearch.verifyDocCountsAndStatus(Name, pureHits);

				// Verify Purehit count
				if (pureHits == value) {
					base.stepInfo("Purehit value is : " + pureHits + " equal to " + value);
				} else if (pureHits > value) {
					base.stepInfo("Purehit value is : " + pureHits + " Greater Than " + value);
				}else if(pureHits < value) {
					base.stepInfo("Purehit value is : " + pureHits + " Less Than " + value);
				}
			}
		}

		// Delete Uploaded File
		driver.Navigate().refresh();
		saveSearch.getMySeraches().waitAndClick(10);
		base.waitForElement(saveSearch.getSelectUploadedFile(File));
		saveSearch.getSelectUploadedFile(File).waitAndClick(20);

		saveSearch.deleteFunctionality();
		lp.logout();

	}

	@BeforeMethod
	public void beforeTestMethod(Method testMethod) throws Exception, InterruptedException, IOException {
		System.out.println("------------------------------------------");
		System.out.println("Executing method : " + testMethod.getName());
		// Open browser
		softAssertion = new SoftAssert();
		Input in = new Input();
		in.loadEnvConfig();
		driver = new Driver();
		base = new BaseClass(driver);
		session = new SessionSearch(driver);
		saveSearch = new SavedSearch(driver);

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

//	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			lp.closeBrowser();
		} catch (Exception e) {
			lp.clearBrowserCache();
			System.out.println("Sessions already closed");
		}
	}
}
