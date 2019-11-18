package pageFactory;
import java.io.File;import java.util.Random;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.Reporter;
import automationLibrary.Driver;
import testScriptsSmoke.Input;

public class Utility {
	Driver driver;

	public Utility(Driver driver){

        this.driver = driver;
        //This initElements method will create all WebElements
        //PageFactory.initElements(driver.getWebDriver(), this);
    }
    
	public static int dynamicNameAppender() {
		 Random random = new Random();
			return random.nextInt(1000000);
		
	}

 public void screenShot(ITestResult result) {
	 if(Input.screenShotOnFail.equalsIgnoreCase("YES"))
	 try{
		 // To create reference of TakesScreenshot
   		 TakesScreenshot screenshot=(TakesScreenshot)driver.getWebDriver();
   		 // Call method to capture screenshot
   		 File src=screenshot.getScreenshotAs(OutputType.FILE);
   		 String name[] = result.getMethod().toString().replace(".", "--").split("\\(");
   		
   		String path=System.getProperty("user.dir")+"/Screenshots/"+name[0]+".png";
   		 
   		 // result.getName() will return name of test case so that screenshot name will be same as test case name
   		 FileUtils.copyFile(src, new File(path));
   		 System.out.println("Successfully captured a screenshot");
   		Reporter.log("<a href='"+ path + "'> <img src='"+ path + "' height='100' width='100'/> </a>");
   		 }catch (Exception e){
   			 e.printStackTrace();
   		 System.out.println("Exception while taking screenshot "+e.getMessage());
   		 } 
}
}
