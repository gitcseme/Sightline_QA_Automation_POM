package testScriptsRegression;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.Callable;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.awt.Graphics;
import automationLibrary.Driver;
import pageFactory.BaseClass;
import pageFactory.DocViewPage;
import pageFactory.HomePage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.RedactionPage;
import pageFactory.SavedSearch;
import pageFactory.Utility;
import testScriptsSmoke.Input;
import java.sql.*;

 public class DocView_Redactions {
	Driver driver;
	LoginPage lp;
	DocViewPage docView;
	HomePage hm;
	BaseClass bc;
	RedactionPage redact;
	private Connection connection;
	private static Statement s1,s2,s3;
	private static ResultSet rs1,rs2,rs3;
	
	String redactname = "Redaction"+Utility.dynamicNameAppender();
	String assignmentName= "assi488354";
	String productionname= "P"+Utility.dynamicNameAppender();
	String exportname= "EXP"+Utility.dynamicNameAppender();
	String PrefixID = "A_"+Utility.dynamicNameAppender();;
	String SuffixID = "_P"+Utility.dynamicNameAppender();;
	String foldername = "FolderProd"+Utility.dynamicNameAppender(); 
	String Tagname = "Privileged";
	
	@BeforeClass(alwaysRun = true)
	public void preCondition() throws InterruptedException, ParseException, IOException {
		
		System.out.println("******Execution started for "+this.getClass().getSimpleName()+"********");
		
		Input in = new Input();
		in.loadEnvConfig();
		
		String databaseURL = "jdbc:sqlserver://MTPVTDSLDB02.consilio.com:1433;"+
				"databaseName=EAutoP0C8A;";
        String user = "SLUser";
        String password = "ConAdm1n";
        connection = null;
        
        try {
       	 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
       	 
       	 connection = DriverManager.getConnection(databaseURL, user, password);
       	 System.out.println("Connection pass");
       	 }
       	 catch(Exception e)
       	 {
       		 e.printStackTrace();
       	 }
		
		
		//Open browser
		driver = new Driver();
		//Login as reviewer
		lp=new LoginPage(driver);
		/*
		 * lp.loginToSightLine(Input.rmu2userName,Input.rmu2password); bc = new
		 * BaseClass(driver); redact = new RedactionPage(driver);
		 * redact.AddRedaction(redactname,"RMU");
		 * 
		 * 
		 * lp.logout();
		*/
		 
		lp.loginToSightLine(Input.rev1userName, Input.rev1password);
		bc = new BaseClass(driver);
		
		//bc.selectsecuritygroup("SGForRedaction");
		
		SavedSearch ss = new SavedSearch(driver);
		ss.savedSearchToDocView("RedactTest");
		
		
		/*	
			//Search docs with docid
			SessionSearch search = new SessionSearch(driver);
			search.basicMetaDataSearch("DocID", null, "S00018829P", null);
			search.ViewInDocView();
		*/
		
			docView=new DocViewPage(driver);   
						
			docView.getDocView_DocId("ID00001426").waitAndClick(10);
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getDocView_Redact_Rectangle().Displayed()  ;}}), Input.wait30);   
		    docView.getDocView_RedactIcon().Click();
			Thread.sleep(1000);
	   	
	    	driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
	    			docView.getDocView_Redact_Rectangle().Displayed()  ;}}), Input.wait30); 
	    	docView.getDocView_Redact_Rectangle().Click();
	    	Thread.sleep(2000);
	    
	}
	
	@Test(dataProvider="Datasets",priority=1)
	public void AddRedaction(int off1,int off2,int k) throws InterruptedException
	{
	   
    	docView.redactbyrectangle(off1, off2, k, "R1");
    }
	
	@Test(priority=2)
	public void ModifyRedaction() throws InterruptedException
	{
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			docView.getDocView_Redact_Rectangle().Displayed()  ;}}), Input.wait30); 
    	docView.getDocView_Redact_Rectangle().Click();
    	Thread.sleep(2000);
    	docView.editredaction(300, 207, 4, "R4");
    	docView.editredaction(500, 210, 6, "R6");
    }
	
	@Test(priority=3)
	public void AddRedaction1() throws InterruptedException
	{
		driver.scrollPageToTop();
		  driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
		  docView.getDocView_Redact_Rectangle().Displayed() ;}}), Input.wait30);
		  docView.getDocView_Redact_Rectangle().waitAndClick(10);
		 Thread.sleep(2000);
       	docView.redactbyrectangle(600, 100, 7, "R1");
    	docView.redactbyrectangle(700, 150, 8, "R2");
    }
	
	
	
	@Test(priority=4)
	public void DeleteRedaction() throws InterruptedException
	{
		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			docView.getDocView_Redact_Rectangle().Displayed()  ;}}), Input.wait30); 
    	docView.getDocView_Redact_Rectangle().Click();
    	Thread.sleep(2000);
    	docView.Deleteredaction(150, 202, 2, "R1");
    	docView.Deleteredaction(200, 205, 3, "R2");
    }
	
	@Test(priority=5)
	public void AddRedactionnextpage() throws InterruptedException
	{
		driver.scrollPageToTop();
		//docView.getDocView_Next().Click();
		driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
    			docView.getDocView_Redact_Rectangle().Displayed()  ;}}), Input.wait30); 
    	docView.getDocView_Redact_Rectangle().waitAndClick(10);
    	Thread.sleep(2000);
    	docView.redactbyrectangle(250, 300, 9, "R1");
    	docView.redactbyrectangle(150, 480, 10, "R2");
    	//docView.getDocView_Next().Click();
    }
	
	    @Test(priority=6)
		public void Generateproduction() throws InterruptedException
		{
			driver.scrollPageToTop();
			docView.VerifyFolderTab("ProdRedact", 9);
	    	lp.logout();
	    	lp.loginToSightLine(Input.pa1userName, Input.pa1password);
	    	ProductionPage page= new ProductionPage(driver);
	    	page.Productionwithredactions(productionname, PrefixID, SuffixID, "ProdRedact",Tagname);
   	 
	    }
	@Test(priority=7)
	public void DatabaseValidation() throws Exception
	{
		try {
			//int docid=1426;
            String query1 = "select* from EAutoP0C8A.dbo.documents where DocumentID='1426'";
            String query2 = "select *from EAutoP0C8A.[dbo].DocumentRedactions where documentid=1426 and SecurityGroupID=1";
            String query3 = "SELECT * FROM EAutoP0C8A.[dbo].[DocumentAnnotationLayers] where documentid=1426";
             
            s1= connection.createStatement();
            rs1 = s1.executeQuery(query1);
           
            while(rs1.next()){
                int DocId= rs1.getInt("DocumentID");
                System.out.println(DocId);
            }
            
            //execute query 2
            s2= connection.createStatement();
            rs2 = s2.executeQuery(query2);
            
            while(rs2.next()){
                int DocId= rs2.getInt("DocumentID");
                int secgrpid = rs2.getInt("SecurityGroupID");
                int redactionid=rs2.getInt("RedactionID");
                String nodeid=rs2.getNString("NodeId");
                int userid=rs2.getInt("CreatedBy");
                System.out.println(DocId+"\t"+secgrpid+"\t"+redactionid+"\t"+nodeid
                		+"\t"+userid);
            }
   
            //execute query 3
            s3= connection.createStatement();
            rs3 = s3.executeQuery(query3);
            
            while(rs3.next()){
                String xml= rs3.getString("AnnotationLayer");
                System.out.println(xml);
            }
   
        } catch (SQLException ex) {
           ex.printStackTrace();
        }
    }

	
	
	 @DataProvider(name = "Datasets")
	 public static Object[][] offsets() {
		 
		 return new Object[][] { {100,200,1},{150,202,2},{200,205,3},{300,207,4},
			 {400,208,5},{500,210,6}};
	 }
	 
	 

	
	
   	
	  	
	  @AfterMethod(alwaysRun = true)
		 public void takeScreenShot(ITestResult result) {
	   	 if(ITestResult.FAILURE==result.getStatus()){
	   		Utility bc = new Utility(driver);
	   		bc.screenShot(result);
	   	}
	    }
		
		@AfterClass(alwaysRun = true)
		public void close(){
		/*
		 * try{ lp.logout(); }finally { lp.quitBrowser(); LoginPage.clearBrowserCache();
		 * }
		 */	if (connection != null) {
                try {
                    System.out.println("Closing Database Connection...");
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
		}
	}
}