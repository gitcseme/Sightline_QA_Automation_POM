package executionMaintenance;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
//.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testScriptsSmoke.Input;

public class ExtentManager {
	
	//static LocalDateTime now = LocalDateTime.now();
   // static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
   // static String formatDateTime = now.format(formatter);

    private static ExtentReports extent;
    private static String reportFileName = "Sightline-SmokeTest-"+DateTimeFormatter.ISO_LOCAL_DATE.format(LocalDateTime.now())+"-Automaton-Report-"+Input.testingBuild+ ".html";
    private static String fileSeperator = System.getProperty("file.separator");
    private static String reportFilepath = System.getProperty("user.dir") +fileSeperator+ "TestReport";
    private static String reportFileLocation =  reportFilepath +fileSeperator+ reportFileName;
    
    // Get System Information
    private static String osName = System.getProperty("os.name");
    private static String vmVersion = System.getProperty("java.vm.version");
    private static String userName = System.getProperty("user.name");
    private static String hostName = System.getenv("COMPUTERNAME");
    private static String projectName = Input.projectName;
      
 
    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }
 
    //Create an extent report instance
    public static ExtentReports createInstance() {
        String fileName = getReportPath(reportFilepath);
       
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setDocumentTitle(reportFileName);
        htmlReporter.config().setEncoding("utf-8");
        htmlReporter.config().setReportName(reportFileName);
        htmlReporter.config().setTimeStampFormat("EEEE, MMMM dd, yyyy, hh:mm a '('zzz')'");
         
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        //Set environment details
		extent.setSystemInfo("OS", osName);
		extent.setSystemInfo("Java Version",vmVersion );
		extent.setSystemInfo("Execution on Host",hostName);
		//extent.setSystemInfo("Environment", "UK");
		extent.setSystemInfo("User", userName);
		extent.setSystemInfo("Execution on Project",projectName);
 
        return extent;
    }
     
    //Create the report path
    private static String getReportPath (String path) {
    	File testDirectory = new File(path);
        if (!testDirectory.exists()) {
        	if (testDirectory.mkdir()) {
                System.out.println("Directory: " + path + " is created!" );
                return reportFileLocation;
            } else {
                System.out.println("Failed to create directory: " + path);
                return System.getProperty("user.dir");
            }
        } else {
            System.out.println("Directory already exists: " + path);
        }
		return reportFileLocation;
    }
 
}