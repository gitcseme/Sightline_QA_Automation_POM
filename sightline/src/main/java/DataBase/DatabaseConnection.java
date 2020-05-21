package DataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

	public class DatabaseConnection {
		
	 static Connection conn = null;
	 private static Statement stmt;
	 static ResultSet resultSet = null;
	

	 public void  setUp() throws Exception
	 
	 {
		String url="jdbc:sqlserver://MTPVTDSLDB02.consilio.com:1433;"+
	"databaseName=EAutoP0C8A;integratedSecurity=true;";
		 //Object of ResultSet => 'It maintains a cursor that points to the current row in the result set'
	 
	 try {
	 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
	 System.out.println("Connecting to Database...");
	// Open a connection
	conn = DriverManager.getConnection(url, "SLUser", "ConAdm1n");
	if (conn != null) {
	System.out.println("Connected to the Database..");
	}
	
	 }
	 catch(Exception e)
	 {
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
	 System.out.println(resultSet .getString(1) + "  " + resultSet.getString(2) + "  " + resultSet.getString(3) + "  "
	 + resultSet.getString(4) + "  " + resultSet.getString(5));
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

	
	 public static void main(String[] args) throws Exception {
		 DatabaseConnection db = new DatabaseConnection();
		 db.setUp();
		 db.getqueryresults("select* from EAutoP0C8A.dbo.documents where sourcedocid='65ID00000188'");
		 
	 }

	}	 
	

	
