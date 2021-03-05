package executionMaintenance;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.Reporter;




public class UtilityLog {
	WebDriver driver;
	 static Logger log = Logger.getLogger("devpinoyLogger");
	public UtilityLog(WebDriver driver){

        this.driver = driver;
        //This initElements method will create all WebElements
        PageFactory.initElements(driver, this);
    }
    
	public static int dynamicNameAppender() {
		 Random random = new Random();
			return random.nextInt(1000000);
		
	}

	public static void logBefore(String methodName) throws IOException {
		
		  Properties prop = readPropertiesFile("mtm_TC.properties");
		 log.info("****************************************************************************************");
				 
		 log.info("$$$$$$$$$$$$$$$$$$$$$  Executing - "+methodName+ "  ,  MTM TC IDs:  "+prop.getProperty(methodName)+"       $$$$$$$$$$$$$$$$$$$$$$$$$");
				 
		log.info("MTM Id:  "+prop.getProperty("contentSearchWithOperatorsInBS"));
		
		System.out.println("MTM Id:  "+prop.getProperty(methodName));
		
		 log.info("****************************************************************************************\n");

	}
	
	 public static Properties readPropertiesFile(String fileName) throws IOException {
	      FileInputStream fis = null;
	      Properties prop = null;
	      try {
	         fis = new FileInputStream(fileName);
	         prop = new Properties();
	         prop.load(fis);
	      } catch(FileNotFoundException fnfe) {
	         fnfe.printStackTrace();
	      } catch(IOException ioe) {
	         ioe.printStackTrace();
	      } finally {
	         fis.close();
	      }
	      return prop;
	   }
	
	public static void logafter(String methodName) {
		log.info("XXXXXXXXXXXXXXXXXXXXXXX   "+"-E---N---D-"+"  "+methodName+ "  XXXXXXXXXXXXXXXXXXXXXX");
		 
		log.info("X");
		
	}
	
	public static void info(String message) {

		log.info(message);

		}

	public static void info(Date parse) {
	
		// TODO Auto-generated method stub
		log.info(parse);
	}

	public static void info(boolean add) {
		// TODO Auto-generated method stub
		log.info(add);
	}

	public static void info(String[] listarray) {
		// TODO Auto-generated method stub
		log.info(listarray);
	}

	public static void info(List<WebElement> values) {
		// TODO Auto-generated method stub
		log.info(values);
	}

	public static void info(int length) {
		// TODO Auto-generated method stub
		log.info(length);
	}
	public static void info(ArrayList<String> dataset) {

		log.info(dataset);

		}
	
	

}