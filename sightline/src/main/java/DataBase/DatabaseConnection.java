package DataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

	public class DatabaseConnection {
		
	 private static Statement stmt;
	 static ResultSet resultSet = null;
	 private Connection conn;
	public static String databaseName;
    private static Statement s1, s2, s3;
	private static ResultSet rs1, rs2, rs3;
	public static int docid=1426;
	
	

	 public void  setUp(String databaseName) throws Exception
	 { 
		String databaseURL = "jdbc:sqlserver://MTPVTDSLDB02.consilio.com:1433;" + "databaseName="+databaseName+";";
		String user = "SLUser";
		String password = "ConAdm1n";
		
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();

			conn = DriverManager.getConnection(databaseURL, user, password);
			System.out.println("Connection pass");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	 
	 public ResultSet getqueryresults(String query) throws SQLException
	 {
		 try{
	 // Execute a query
	 stmt = conn.createStatement();
	 
	 resultSet = stmt.executeQuery(query);
	 while (resultSet .next()) {
	 System.out.println(resultSet .getString(1) + "  " + resultSet.getString(2) + "  " + resultSet.getString(3));

	     String nodeid=resultSet.getNString("NodeId");
	     String OrphanedRedactionXml=resultSet.getNString("OrphanedRedactionXml");
	     String OrphandedRedactionTag=resultSet.getNString("OrphandedRedactionTag");
        // System.out.println(nodeid+"\t"+OrphanedRedactionXml+"\t"+OrphandedRedactionTag);
         
         if(OrphanedRedactionXml==null && OrphandedRedactionTag==null)
         {
        	 System.out.println("Test Passed");
         }
         else if(OrphanedRedactionXml.contains("Orphaned Tag")|| OrphandedRedactionTag.contains("Orphaned Tag"))
         {
        	System.out.println("Test Failed");
         }
	 }
 }
	 catch(Exception e)
	 {
	 e.printStackTrace();
	 }
		 return resultSet;
	 }
	
			public void tearDown() throws Exception {
			// Close DB connection
			if (conn != null) {
			conn.close();
			}
			}



	}	 
	

	
