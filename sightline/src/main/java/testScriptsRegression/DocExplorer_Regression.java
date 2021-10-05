package testScriptsRegression;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import automationLibrary.Driver;
import pageFactory.AssignmentsPage;
import pageFactory.BaseClass;
import pageFactory.CommentsPage;
import pageFactory.DocExplorerPage;
import pageFactory.DocListPage;
import pageFactory.LoginPage;
import pageFactory.SessionSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class DocExplorer_Regression {

	Driver driver; 
	LoginPage lp;
	BaseClass bc;
	DocExplorerPage docexp;
	SessionSearch search;
	int purehits;
	
	String assignmentName1 = "Assgn1"+Utility.dynamicNameAppender();
	String assignmentName2 = "Assgn2"+Utility.dynamicNameAppender();
	String commentname = "C"+Utility.dynamicNameAppender();

	/*
	 * Author : Shilpi Mangal
	 * Created date: 29/01/2020
	 * Modified date: 
	 * Modified by: 
	 * Description : Login as PA user
	 */	
	@BeforeClass(alwaysRun=true)
	public void preCondition() throws ParseException, InterruptedException, IOException{
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		
	 	//Open browser
		driver = new Driver();
		//bc = new BaseClass(driver);
		//docexp = new DocExplorerPage(driver);
		//Login as a PA
		lp=new LoginPage(driver);
		lp.loginToSightLine(Input.pa1userName, Input.pa1password);
		
		bc = new BaseClass(driver);
		bc.selectproject();
		
		search= new SessionSearch(driver);
		
		purehits=search.basicContentSearch(Input.searchString1); 
		//search.bulkFolderExisting("Confidential");
		search.bulkTagExisting("Confidential");
		docexp = new DocExplorerPage(driver);
		search.basicContentSearch("*");
		//search.bulkFolderExisting("Attorney_Client");
		search.bulkTagExisting("Attorney_Client"); 
		
	}
	
	/*
	 * Author : Shilpi Mangal
	 * Created date: 
	 * Modified date: 
	 * Modified by:
	 * Description : Verify that Column header Filter is working correctly on Doc Explorer screen
	 */
	@Test(groups={"regression"},dataProvider="Filters",priority=1)
	public void HeaderFiltersVerification(String Filtername) throws InterruptedException, ParseException {
		
		docexp.HeaderFilter(Filtername);
    }
	
	/*
	 * Author : Shilpi Mangal
	 * Created date: 
	 * Modified date: 
	 * Modified by:
	 * Description : Verify that multiple Filters (Tags and MasterDate) with 
	 *              "Exclude" functionality is working correctly on Doc Explorer list.
	 */
	@Test(groups={"regression"},priority=2)
	public void TagMasterDateFilter() throws InterruptedException {
		
		docexp.TagWithMasterDateFilter("Confidential");
    	
    }
	
	/*
	 * Author : Shilpi Mangal
	 * Created date: 
	 * Modified date: 
	 * Modified by:
	 * Description : Verify that “CustodianName” Filter with "Include" functionality is working correctly on Doc Explorer list.
	 */
	@Test(groups={"regression"},priority=3)
	public void CustodianNameFilter() throws InterruptedException {
		
		docexp.CustodianFilter("P Allen","Andrew","include");
		docexp.CustodianFilter("P Allen","Andrew","exclude");
    }
	
	/*
	 * Author : Shilpi Mangal
	 * Created date: 
	 * Modified date: 
	 * Modified by:
	 * Description : Verify that “DocFileType” Filter with "Include" functionality is working correctly on Doc Explorer list.
	 */
	@Test(groups={"regression"},priority=4)
	public void DocFileTypeFilter() throws InterruptedException {
		
		docexp.DocFileTypeFilter("MS Outlook Message","Text File");
    }
	
	/*
	 * Author : Shilpi Mangal
	 * Created date: 
	 * Modified date: 
	 * Modified by:
	 * Description : Verify that “Tags” Filter with "Include" functionality is working correctly on Doc Explorer list.
	 */
	@Test(groups={"regression"},priority=5)
	public void TagsFilter() throws InterruptedException {
		
		docexp.TagFilter("Confidential","Attorney_Client");
    }
	
	/*
	 * Author : Shilpi Mangal
	 * Created date: 
	 * Modified date: 
	 * Modified by:
	 * Description : Verify that “Masterdate” Filter with "Include" functionality is working correctly on Doc Explorer list.
	 */
	@Test(groups={"regression"},priority=6)
	public void MasterdateFilter() throws InterruptedException {
		
		docexp.MasterDateFilter();
    }
	
	/*
	 * Author : Shilpi Mangal
	 * Created date: 
	 * Modified date: 
	 * Modified by:
	 * Description : Verify that “EmailAuthorName” Filter with "Include" functionality is working correctly on Doc Explorer list.
	 */
	@Test(groups={"regression"},priority=7)
	public void EmailAuthorNameFilter() throws InterruptedException {
		
		docexp.EmailAuthorNameFilter("Phillip.Allen@consilio.com","Amit.Bandal@symphonyteleca.com");
    }
	
	/*
	 * Author : Shilpi Mangal
	 * Created date: 
	 * Modified date: 
	 * Modified by:
	 * Description : Verify that “EmailRecipientName” Filter with "Include" functionality is working correctly on Doc Explorer list.
	 */
	@Test(groups={"regression"},priority=8)
	public void EmailRecipientNameFilter() throws InterruptedException {
		
		docexp.EmailRecipientNameFilter("Robert.Superty@consilio.com","amol.gawande@consilio.com");
    }
	

	/*
	 * Author : Shilpi Mangal
	 * Created date: 
	 * Modified date: 
	 * Modified by:
	 * Description : Verify that “EmailAuthorDomain” Filter with "Include" functionality is working correctly on Doc Explorer list.
	 */
	@Test(groups={"regression"},priority=9)
	public void EmailAuthorDomainFilter() throws InterruptedException {
		
		docexp.EmailAuthorDomainFilter("consilio.com","hotmail.com");
    }
	

	/*
	 * Author : Shilpi Mangal
	 * Created date: 
	 * Modified date: 
	 * Modified by:
	 * Description : Verify that “EmailAuthorDomain” Filter with "Include" functionality is working correctly on Doc Explorer list.
	 */
	@Test(groups={"regression"},priority=10)
	public void CommentsFilter() throws InterruptedException {
		
		docexp.CommentFilter();
    }
	
	
	/*
	 * Author : Shilpi Mangal
	 * Created date: 
	 * Modified date: 
	 * Modified by:
	 * Description : Verify that “Assignments” (Only For RMU) filter with "Include" and "Exclude" functionality is working correctly on Doc Explorer list.
	 */
	@Test(groups={"regression"},priority=11)
	public void AssignmentsFilter() throws InterruptedException {
		
		lp.logout();
		
		lp.loginToSightLine(Input.rmu1userName, Input.rmu1password);
		bc.stepInfo("*****Login Successfull*****");
		AssignmentsPage assgn = new AssignmentsPage(driver);
		DocListPage doclist = new DocListPage(driver);
		search.basicContentSearch(Input.searchString1);
		search.ViewInDocList();
		bc.stepInfo("*****Assign Docs to Assignment1*****");
		doclist.DoclisttobulkAssign(assignmentName1,"10");
		assgn.assignDocstoNewAssgn(assignmentName1, null,10);
		search.ViewInDocList();
		doclist.Selectpagelength("100");
		bc.stepInfo("*****Assign Docs to Assignment2*****");
		doclist.DoclisttobulkAssign(assignmentName2,"100");
		assgn.assignDocstoNewAssgn(assignmentName2, null,52);
		 bc.stepInfo("Test case Id: RPMXCON-54678 - Verify  Assignments Filter with Exclude functionality is working correctly on Doc Explorer list.");
		bc.stepInfo("*****Include Assignment Filter*****");
		docexp.AssignmentFilter(assignmentName1, assignmentName2,"include");
		bc.passedStep("*****Include Assignment Filter successfully*****");
		 bc.stepInfo("Test case Id: RPMXCON-54696 - AssignmentsFilter");
		bc.stepInfo("*****Exclude Assignment Filter*****");
		docexp.AssignmentFilter(assignmentName1, assignmentName2,"exclude");
		 bc.passedStep("*****Exclude Assignment Filter successfully*****");
  }
	
	
	@DataProvider(name="Filters")
	public static Object[][] getfiltername()
	{
		return new Object[][] {{"DocID"},{"Custodian"},{"MasterDate"}};
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
		@AfterClass(alwaysRun=true)
		public void close(){
			try{ 
				lp.logout();
			     //lp.quitBrowser();	
				}finally {
					lp.quitBrowser();
					lp.clearBrowserCache();
				}
		}

}
