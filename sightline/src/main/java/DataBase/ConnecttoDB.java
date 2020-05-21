package DataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

	public class ConnecttoDB {

	 public static void main(String[] args) throws Exception {
		 
	String url="jdbc:sqlserver://MTPVTDSLDB02.consilio.com:1433;"+
	"databaseName=EAutoP0C8A;integratedSecurity=true;";
	 // Object of Connection from the Database
	 Connection conn = null;
	 
	 // Object of Statement. It is used to create a Statement to execute the query
	 Statement stmt = null;
	 
	 //Object of ResultSet => 'It maintains a cursor that points to the current row in the result set'
	 ResultSet resultSet = null;
	 try {
	 Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver").newInstance();
	 
	 //	Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
	 
	 // Open a connection
	 //conn = DriverManager.getConnection(
	//"jdbc:mysql://MTPVTDSLDB02.consilio.com;user=SLUser;password=ConAdm1n;database=EAutoP0C8A");

	conn = DriverManager.getConnection(url, "SLUser", "ConAdm1n");
	System.out.println("Connection pass");
	 }
	 catch(Exception e)
	 {
		 e.printStackTrace();
	 }
	 //conn = DriverManager.getConnection(
	//		 "jdbc:mysql://LNTVTDSQLPT01.consilio.com/EDNDDP84E7","CONSILIO\\smangal","Pandey@7654321");

	 // Execute a query
	 stmt = conn.createStatement();
	 
	 resultSet = stmt.executeQuery("select* from EAutoP0C8A.dbo.documents where sourcedocid='65ID00000188'");
	 while (resultSet .next()) {
	 System.out.println(resultSet .getString(1) + "  " + resultSet.getString(2) + "  " + resultSet.getString(3) + "  "
	 + resultSet.getString(4) + "  " + resultSet.getString(5));
	 }
	 
	 if (resultSet != null) {
	 try {
	 resultSet.close();
	 } catch (Exception e) {
	 }
	 }
	 
	 if (stmt != null) {
	 try {
	 stmt.close();
	 } catch (Exception e) {
	 }
	 }
	 
	 if (conn != null) {
	 try {
	 conn.close();
	 } catch (Exception e) {
	 }
	 }
	 }
	}

	
