package automationLibrary;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriver.Navigation;
import org.openqa.selenium.WebDriver.Options;
import org.openqa.selenium.WebDriver.TargetLocator;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.gargoylesoftware.htmlunit.javascript.host.html.Option;

import configsAndTestData.ConfigLoader;
import configsAndTestData.ConfigMain;
import testScriptsSmoke.Input;

/// <summary>
/// Wraps the Selenium WebDriver class.  Encapsulates driver creation, 
/// error handling and destruction
/// </summary>
public  class Driver  {  

	   public  WebDriver driver;
	   /// <summary>
       /// Public constructor of Driver
       /// </summary>
       /// <param name="url">The URL which the driver will try to connect to when created</param>
       /// <param name="browserType">Currently only Chrome, IE and Firefox supported</param>
	
	public Driver() { 
		System.out.println(Input.browserName);
		  this.driver = createDriver(Input.browserName); 
	      this.driver.get(Input.url);
	      System.out.println(Input.browserName + "is opned and loading application");
	      waitForPageToBeReady();
	      
	   }  
	   
	
	   
	   /// <summary>
       /// Returns the Selenium Web Driver
       /// </summary>
	   public WebDriver getWebDriver()
	   {
		   return driver;
	   }
	   
	   /// <summary>
       /// Amount of time to wait for an element to become present.
       /// </summary>
	   public double getApplicationTimeout(){ 
			   return 60000;
					   //configuration.Timeout;
	   }
	   
	   /// <summary>
       /// Interval to check if an element becomes available.
       /// </summary>
	   public double getApplicationPollingInterval(){
		   return 1000;
				   //configuration.TimeoutPollingInterval;
	   }
	 /// <summary>
      /// Url which the browser will navigate to
      /// </summary>
	  public String getUrl() { 
		   return driver.getCurrentUrl(); 
	   }
	   /// <summary>
       /// Title of the browser page
       /// </summary>
	   public String getTitle(){
		   return driver.getTitle();
	   }
	   
	   /// <summary>
       /// HTML source of the page given as a string
       /// </summary>
	   public String getPageSource(){
		   return driver.getPageSource();
	   }
	   /// <summary>
       /// Id of the window
       /// </summary>
	   public String CurrentWindowHandle(){
		   return driver.getWindowHandle();
	   }
	   
    	/// <summary>
    	/// Collection of Id's of the windows
    	/// </summary>
	 /*  public ReadOnlyCollection<String> WindowHandles(){
		   return driver.WindowHandles;
	   }*/
	
	   /// <summary>
       /// Close the browser
       /// </summary>
	   public void close() { 
	      driver.close();   
	   }  
	  
       //Find element-----------------------------------------------------
	   public Element FindElement(By by)
       {
           
		   return new Element(this, by);
       }
	 
	   public Element FindElementByClassName(String className)
       {
           return FindElement(By.className(className));
       }

       public Element FindElementByCssSelector(String cssSelector)
       {
           return FindElement(By.cssSelector(cssSelector));
       }

       public Element FindElementById(String id)
       {
           return FindElement(By.id(id));
       }

       public Element FindElementByLinkText(String linkText)
       {
           return FindElement(By.linkText(linkText));
       }
       
       public Element FindElementByPartialLinkText(String linkText)
       {
           return FindElement(By.partialLinkText(linkText));
       }


       public Element FindElementByName(String name)
       {
           return FindElement(By.name(name));
       }
       
       public Element FindElementByTagName(String tagName)
       {
           return FindElement(By.tagName(tagName));
       }
       
       public Element FindElementByXPath(String Xpath)
       {
           return FindElement(By.xpath(Xpath));
       }
       //Find elements----------------------------------------------------------
       public ElementCollection FindElements(By by)
       {
           return new ElementCollection(this, by, null);
       }

       public ElementCollection FindElementsByClassName(String className)
       {
           return FindElements(By.className(className));
       }

       public ElementCollection FindElementsByCssSelector(String cssSelector)
       {
           return FindElements(By.cssSelector(cssSelector));
       }

       public ElementCollection FindElementsById(String id)
       {
           return FindElements(By.id(id));
       }

       public ElementCollection FindElementsByLinkText(String linkText)
       {
           return FindElements(By.linkText(linkText));
       }

       public ElementCollection FindElementsByName(String name)
       {
           return FindElements(By.name(name));
       }

       public ElementCollection FindElementsByTagName(String tagName)
       {
           return FindElements(By.tagName(tagName));
       }
       public ElementCollection FindElementsByXPath(String xpath)
       {
           return FindElements(By.xpath(xpath));
       }
       /*public Screenshot GetScreenshot()
       {
           return ((ITakesScreenshot)_driver).GetScreenshot();
       }*/

    

       public Options Manage()
       {
           return driver.manage();
       }

       public Navigation Navigate()
       {
           return driver.navigate();
       }

       public void Quit()
       {
           driver.quit();
       } 

	
	   private WebDriver createDriver(String browserName) { 
	     /* if (browserName.toUpperCase().equals("FIREFOX") )
	         return firefoxDriver(); */

	      if (browserName.toUpperCase().equals("CHROME") )
	      return chromeDriver(); 
	      
	      if (browserName.toUpperCase().equals("EDGE") )
		      return edgeDriver(); 
	      
	     /* if (browserName.toUpperCase().equals("FIREFOX") )
		  return chromeDriver(); 
	      
	      if (browserName.toUpperCase().equals("IE") )
		  return chromeDriver(); */
	      
	      throw new RuntimeException ("invalid browser name"); 
	   } 

	   private WebDriver chromeDriver() { 
	      
	      try { 
	    	    System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+ "//BrowserDrivers//chromedriver.exe");
				ChromeOptions options = new ChromeOptions();
				options.addArguments("chrome.switches","--disable-extensions");
				new DesiredCapabilities();
				DesiredCapabilities caps = DesiredCapabilities.chrome();
				caps.setCapability(ChromeOptions.CAPABILITY, options);
				
				Map<String, Object> prefs = new HashMap<String, Object>();
			
				prefs.put("profile.default_content_settings.popups", 0);
				prefs.put("download.prompt_for_download", "false");
				prefs.put("download.default_directory", System.getProperty("user.dir")+Input.batchFilesPath+"BatchPrintFiles/downloads");
				
				prefs.put("credentials_enable_service", false);
				prefs.put("profile.password_manager_enabled", false);
				options.setExperimentalOption("prefs", prefs);
		
				driver = new ChromeDriver(caps);
				driver.manage().window().maximize();
				return driver;	
	      } 

	      catch (Exception ex) { 
	        ex.printStackTrace();
	    	  throw new RuntimeException
	              ("couldnt create chrome driver"); 
	      } 
	   } 
	  
	   private WebDriver edgeDriver() { 
		      
		      try { 
		    	    System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+ "//BrowserDrivers//MicrosoftWebDriver.exe");
		    	  //Start Edge Session
		    		WebDriver driver=new EdgeDriver();
					driver.manage().window().maximize();
					return driver;	
		      } 

		      catch (Exception ex) { 
		        ex.printStackTrace();
		    	  throw new RuntimeException
		              ("couldnt create chrome driver"); 
		      } 
		   }
	
		
	 /// <summary>
    /// Generic method to wait until the given condition is true.
    /// </summary>
    /// <param name="condition">Will wait until this condition is true</param>
    /// <param name="timeout">Optional: override the application timeout value</param>
    /// <param name="message">Optional: override the message used if the element is never found</param>
    /// <param name="interval">Optional: override the polling interval value</param>
    public void WaitUntil(Callable<Boolean> condition, int timeout)
    {
 	   
       // message = (message==null) ? String.format("waiting for true condition on {0}", condition.toString()):message;

        long startingTime = System.currentTimeMillis();
        //timeout = (timeout==null) ? Duration.ofMillis(this.getApplicationTimeout()):timeout;
        //interval = (interval==null) ? Duration.ofMillis(this.getApplicationPollingInterval()):interval;
        boolean running = true;
        Thread thread = Thread.currentThread();
        while (running)
        {
     	   ExecutorService executor = Executors.newFixedThreadPool(1);
            try
            {
         	   Future<Boolean> future = executor.submit(condition);
         	   //System.out.println(future.get()+"----");
                if (future.get()) return;
            }
            catch(Exception e)
            {
         	   //e.printStackTrace();
            }
            

            if ((System.currentTimeMillis() - startingTime) > timeout)
            {
                running = false;
            }
            else
            {
               try {
					thread.join(Input.interval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
            executor.shutdown();
        }
        
        
        
    }
    
    public void waitAndAct(Callable condition, int timeout)
    {
 	   
       // message = (message==null) ? String.format("waiting for true condition on {0}", condition.toString()):message;

        long startingTime = System.currentTimeMillis();
        //timeout = (timeout==null) ? Duration.ofMillis(this.getApplicationTimeout()):timeout;
        //interval = (interval==null) ? Duration.ofMillis(this.getApplicationPollingInterval()):interval;
        boolean running = true;
        Thread thread = Thread.currentThread();
        while (running)
        {
     	   ExecutorService executor = Executors.newFixedThreadPool(1);
            try
            {
         	   Future<Boolean> future = executor.submit(condition);
         	   //System.out.println(future.get()+"----");
                if (future.get()) return;
            }
            catch(Exception e)
            {
         	   //e.printStackTrace();
            }
            

            if ((System.currentTimeMillis() - startingTime) > timeout)
            {
                running = false;
            }
            else
            {
               try {
					thread.join(Input.interval);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            }
            executor.shutdown();
        }
        
    }
    public  void scrollingToElementofAPage(Element element)
    {
    	
           try
           {
        	  
                  ((JavascriptExecutor) driver).executeScript(
                               "arguments[0].scrollIntoView(true);", element);
           }
           catch (Exception e)
           {
                   e.getLocalizedMessage();
                        e.printStackTrace();
           }
    }
    


    public  void waitForPageToBeReady() 
	{
	    JavascriptExecutor js = (JavascriptExecutor)driver;
	    //This loop will rotate for 100 times to check If page Is ready after every 1 second.
	    //You can replace your if you wants to Increase or decrease wait time.
	  
	    for (int i=0; i<30; i++)
	    { 
	        try 
	        {
	            Thread.sleep(1000);
	        }catch (InterruptedException e) {} 
	        //To check page ready state.

	        if (js.executeScript("return document.readyState").toString().equals("complete"))
	        { 
	        	//Add_Log.info("Page load is complete");
	        	break; 
	        }   
	      }
	 }

    public void scrollingToBottomofAPage()
 	{
 		
 		
 		try
		{
			((JavascriptExecutor) driver).executeScript(
					"window.scrollTo(0, document.body.scrollHeight)");
			Thread.sleep(500);
		}
		catch (Exception e)
		{
			System.out.println("IGNORE" + e.getLocalizedMessage());
		}
 	}
 	public  void scrollPageToTop()
 	{
 		// Scroll page to make element visisble.

 		for (int i = 0; i < 5; i++)
 		{
 			try
 			{
 				((JavascriptExecutor) driver).executeScript(
 						"window.scrollTo(document.body.scrollHeight, 0)");
 				Thread.sleep(500);
 			}
 			catch (Throwable e)
 			{
 				System.out.println(
 						"IGNORE" + e.getLocalizedMessage() + e.getMessage());
 				e.printStackTrace();
 			}
 		}
 	}
 	public TargetLocator switchTo()
    {
        return driver.switchTo();
    }
	
	   
	}