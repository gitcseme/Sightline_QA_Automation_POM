package automationLibrary;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NotFoundException;
import org.openqa.selenium.Point;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.UnexpectedTagNameException;


/// <summary>
/// Wraps the Selenium IWebElement.  Utilizes lazy-loading, so the instance will
/// wait to get the page element until a property is called.
/// </summary>
public class Element{

private By by;
private WebElement element = null;
private  Driver driver;
	
   public Element(Driver driver, By by)
     {
		
		this.by = by;
        this.driver = driver;
         
     }

     public Element(Driver driver, WebElement element)
     {
    	
    	 this.element = element;
         this.driver = driver;
     }
     
     public String Class(){
             AssertExists();
             return element.getAttribute("class");
         
     }
     public String TagName()
     {
             AssertExists();
             return element.getTagName();
     }
     public void checkIn() {
			// TODO Auto-generated method stub
			 
    	 AssertExists();
				 if(!element.isSelected()&&element.getAttribute("checked")=="true"){
					 element.click();
				 }
			
		}
     public void checkOut() {
			// TODO Auto-generated method stub
			 
    	 AssertExists();
				 if(element.isSelected()&&element.getAttribute("checked")=="true"){
					 element.click();
				 }
			
		}
     
     
     /// <summary>
     /// get: Returns the value of an element.  
     /// set: Replaces the existing value of the element with the new value
     /// Note: Value should be used when data binding to the input attribute "value"
     /// </summary>
     public String Value()
     {
           AssertExists();
           return element.getAttribute("value");
        
     }

     
     public Boolean Enabled()
     {
             AssertExists();
             return element.isEnabled();
         
     }

     public Boolean Selected()
     {
    	 AssertExists();
         return element.isSelected();
         
     }

     public Point Location()
     {
         AssertExists();
         return element.getLocation();
         
     }

     /*public Size Size()
     {
     	 AssertExists();
         return element.getSize();
     }*/

     public Boolean Displayed()
     {
          AssertExists();
          return element.isDisplayed();
         
     }
     
     public Boolean Visible(){
    	
    	 return this.Displayed();
     }

     public Boolean Present(){
    	 
    	 return this.Exists();
     }
     
     /// <summary>
     /// get: checks for class value "checked"
     /// set: if state is different then value given, a "click" event is performed
     /// </summary>
  /*   public Boolean Checked()
     {
         AssertExists();
         return this.Class.Contains("checked");
         
     }*/
     
     public Boolean Exists()
     {
          AssertExists();
          return true;
         
     }
     
     /// <summary>
     /// Returns true if a previously located element is no longer attached to DOM
     /// </summary>
     public Boolean Stale() throws Exception
     {
            try
             {
                 if (element == null) throw new Exception("Can not check staleness of unused element");
                 
                  @SuppressWarnings("unused")
				boolean enabled = element.isEnabled();
                 return false;
             }
             catch(StaleElementReferenceException e)
             {
                 return true;
             }
         
     }

 
     /// <summary>
     /// Clears the cached element.  After calling this, any new references will execute 
     /// the lazy-loading of the element again.
     /// </summary>
     public void Reset()
     {
         this.element = null;
     }

     /// <summary>
     /// Scrolls to off-screen element
     /// </summary>
     public void ScrollTo()
     {
    	 AssertExists();
         if (element == null) return;
         Actions actions = new Actions(driver.getWebDriver());
         actions.moveToElement(element);
         actions.moveToElement(element);
         actions.build().perform();
         
         try
 		{
 			((JavascriptExecutor) driver).executeScript(
 					"arguments[0].scrollIntoView(true);", element);
 		}
 		catch (Exception e)
 		{
 			//System.out.println("Scrolling Into View Exception");
 		}
     }
     
     public void javascriptclick()
     {
    	 AssertExists();
         try
 		{
 			((JavascriptExecutor) driver).executeScript(
 					"arguments[0].click();", element);
 		}
 		catch (Exception e)
 		{
 			System.out.println("click exception");
 		}
     }
    
    
     public void Clear()
     {
         AssertExists();
         element.clear();
     }
     public void Click()
     {
         AssertExists();
         element.click();
     }
     
     public void click() {
    	 driver.wait.until(ExpectedConditions.elementToBeClickable(this.by)).click();
     }
     
     public void sendKeys(String text) {
    	 driver.wait.until(ExpectedConditions.elementToBeClickable(this.by)).sendKeys(text);
     }
     
     public void waitAndClick(int waitTime) {
    	 boolean clicked = false;
	  for(int i = 1; i<waitTime;i++){
		 
		 try{
			 driver.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
			  AssertExists();
		      element.click();
		      clicked =true;
				//System.out.println("Clicked after trying "+i+"th time!");
			 //System.out.println("Clicked after trying "+i+" times");
		      break;
			}
			catch(Exception e){
				
				//System.out.println("wait");
				try {
					
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
	  }
	  
	//System.out.println(i);
		if(clicked == false){
			 AssertExists();
	         element.click();
		}

     }
     public String waitAndGet(int waitTime) {
    	 String text = null;
    	 for(int i = 1; i<waitTime;i++){
   		 
   		 try{
   			  AssertExists();
   		      text = element.getText();
   				//System.out.println("Clicked after trying "+i+"th time!");
   			 //System.out.println("get after trying "+i+" times");
   		      break;
   			}
   			catch(Exception e){
   				
   				//Keep waitiing 
   			try {
   				Thread.sleep(1000);
   			} catch (InterruptedException e1) {
   				// TODO Auto-generated catch block
   				e1.printStackTrace();
   			}
   			if(i==waitTime-1){
				 AssertExists();
				 text = element.getText();
			}
   			}
   	  }
    	 return text;
        }
    
     
     public boolean waitAndFind(int waitTime) {
   	  for(int i = 1; i<waitTime;i++){
   		 
   		 try{
   			 driver.getWebDriver().manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
   			  AssertExists();
   		      element.isDisplayed();
   				//System.out.println("Clicked after trying "+i+"th time!");
   			 //System.out.println("found ele after trying "+i+" times");
   			 return true;
   		      
   			}
   			catch(Exception e){
   				
   			System.out.println("nope"+i);
   				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
   				if(i==waitTime-1){
   				 AssertExists();
   				element.isDisplayed();
   			}
   			}
   	  }
   	  	return false;
    }
	 public Element FindElement(By by)
     {
         AssertExists();
         return new Element(driver, element.findElement(by));
     }

     public Element FindElementByclassName(String className)
     {
         return FindElement(By.className(className));
     }

     public Element FindElementBycssSelector(String cssSelector)
     {
         return FindElement(By.cssSelector(cssSelector));
     }

     public Element FindElementById(String id)
     {
         return FindElement(By.id(id));
     }

     public Element FindElementBylinkText(String linkText)
     {
         return FindElement(By.linkText(linkText));
     }

     public Element FindElementByname(String name)
     {
         return FindElement(By.name(name));
     }

     public Element FindElementBytagName(String tagName)
     {
         return FindElement(By.tagName(tagName));
     }

     public ElementCollection FindElements(By by)
     {
         AssertExists();
         return new ElementCollection(driver, by, element);
     }

     public ElementCollection FindElementsByclassName(String className)
     {
         return FindElements(By.className(className));
     }

     public ElementCollection FindElementsBycssSelector(String cssSelector)
     {
         return FindElements(By.cssSelector(cssSelector));
     }

     public ElementCollection FindElementsById(String id)
     {
         return FindElements(By.id(id));
     }

     public ElementCollection FindElementsBylinkText(String linkText)
     {
         return FindElements(By.linkText(linkText));
     }

     public ElementCollection FindElementsByname(String name)
     {
         return FindElements(By.name(name));
     }

     public ElementCollection FindElementsBytagName(String tagName)
     {
         return FindElements(By.tagName(tagName));
     }
     public ElementCollection FindElementsByXPath(String className)
     {
         return FindElements(By.className(className));
     }

   /// <summary>
     /// Get/Set "value" for an Element which is a Select (dropdown) control 
     /// </summary>
    /* public String SelectValue()
     {
           AssertExists();
           return new SelectElement(element).SelectedOption.GetAttribute("value");
         
     }
    */
     
     public String GetAttribute(String attributeName)
     {
         AssertExists();
         return element.getAttribute(attributeName);
     }
    
     
     public String GetCssValue(String propertyName)
     {
         AssertExists();
         return element.getCssValue(propertyName);
     }
     
     /*public String GetProperty(String propertyName)
     {
         AssertExists();
         return element.GetProperty(propertyName);
     }*/
     
     /// <summary>
     /// Will append text to a given element
     /// </summary>
     /// <param name="text"></param>
     public void SendKeys(String text)
     {
         AssertExists();
         //element.clear();
         element.sendKeys(text);
     }
     /// <summary>
     /// Will append text to a given element
     /// </summary>
     /// <param name="text"></param>
     public void SendKeysNoClear(String text)
     {
         AssertExists();
         element.sendKeys(text);
     }
     
     /// <summary>
     /// Performs a keyboard "tab" event
     /// </summary>
     public void Tab()
     {
    	 AssertExists();
         element.sendKeys(Keys.TAB);
     }
     
     /// <summary>
     /// Performs a keyboard "enter" event
     /// </summary>
     public void Enter()
     {
    	 AssertExists();
         element.sendKeys(Keys.ENTER);
     }
     
     /// <summary>
     /// Performs a keyboard "esc" event
     /// </summary>
     public void Esc()
     {
    	 AssertExists();
         element.sendKeys(Keys.ESCAPE);
     }

     /// <summary>
     /// Any element within a form can execute Submit in order to submit the form.
     /// </summary>
     public void Submit()
     {
         AssertExists();
         element.submit();
     }
   
     
     private void AssertExists()
     {
        /* if(element != null && by != null)
         {
             try {
				if (Stale()) Reset();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         } else
			try {
				if(element != null && !Stale())
				 {
				     return;
				 }
				 else
				 {*/
				     element=driver.getWebDriver().findElement(by);
				    
				/*}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

         AssertElementFound();*/
         //ScrollTo();
     }
     /// <exception cref="NotFoundException">thrown if element could not be found in the driver</exception>
     public void AssertElementFound()
     {
         if (element != null) return;
         throw new NotFoundException(String.format("unable to locate element: {0}", by.toString()));
     }
     public String getText()
     {
    	 AssertExists();
    	 return element.getText();
     }
     public void setText(String Value)
     {
    	 
    	this.SendKeys(Value); 
     }
    
	     
  /// <summary>
     /// Waits until the element becomes present or until the timeout is reached.
     /// </summary>
     public Element WaitUntilPresent()
     {
    	 long startingTime = System.currentTimeMillis();
         Duration timeout = Duration.ofMillis((long) driver.getApplicationTimeout());
         Duration interval = Duration.ofMillis((long) driver.getApplicationPollingInterval());
         boolean running = true;
         while (running)
         {
             try
             {
                 if (element != null || (driver.FindElement(by) != null && driver.FindElement(by).Displayed())) return this;
             }
             catch (Exception e)
             {
             	try {
					Thread.currentThread().join(interval.toMillis());
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
                 //System.Threading.Thread.CurrentThread.Join((int)interval.Value.TotalMilliseconds);
                 if ((System.currentTimeMillis() - startingTime) > timeout.toMillis())
                 {
                     running = false;
                 }
              
             }
         }
         throw new NotFoundException(String.format("Unable to located element {0}", by.toString()));
     }

     /// <summary>
     /// Waits while the element remains present or until the timeout is reached.
     /// </summary>
     public Element WaitWhilePresent()
     {
    	 long startingTime = System.currentTimeMillis();
         Duration timeout = Duration.ofMillis((long) driver.getApplicationTimeout());
         Duration interval = Duration.ofMillis((long) driver.getApplicationPollingInterval());
         boolean running = true;
         while (running)
         {
             try
             {
                 if (driver.getWebDriver().findElement(by) == null) return this;
             }
             catch (Exception e)
             {
             	try {
					Thread.currentThread().join(interval.toMillis());
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
                 //System.Threading.Thread.CurrentThread.Join((int)interval.Value.TotalMilliseconds);
                 if ((System.currentTimeMillis() - startingTime) > timeout.toMillis())
                 {
                     running = false;
                 }
              
             }
         }
         throw new NotFoundException(String.format("Unable to located element {0}", by.toString()));
     }
   /// <summary>
     /// Waits until the element becomes present or until the timeout is reached.
     /// </summary>
     /// <param name="timeout">Optional: override the application timeout value</param>
     /// <param name="message">Optional: override the message used if the element is never found</param>
     /// <param name="interval">Optional: override the polling interval value</param>
     /// <returns>Returns the current Element so chaining can occur</returns>
     public Element WaitUntilPresent(Duration timeout, String message, Duration interval)
     {
    	 long startingTime = System.currentTimeMillis();
         boolean running = true;
         while (running)
         {
             try
             {
                 if (element != null || (driver.FindElement(by) != null && driver.FindElement(by).Displayed())) return this;
             }
             catch (Exception e)
             {
             	try {
					Thread.currentThread().join(interval.toMillis());
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
                 //System.Threading.Thread.CurrentThread.Join((int)interval.Value.TotalMilliseconds);
                 if ((System.currentTimeMillis() - startingTime) > timeout.toMillis())
                 {
                     running = false;
                 }
              
             }
         }
         throw new NotFoundException(message.toString());
     }

     /// <summary>
     /// Waits while the element remains present or until the timeout is reached.
     /// </summary>
     /// <param name="timeout">Optional: override the application timeout value</param>
     /// <param name="message">Optional: override the message used if the element is never found</param>
     /// <param name="interval">Optional: override the polling interval value</param>
     /// <returns>Returns the current Element so chaining can occur</returns>
     public Element WaitWhilePresent(long timeout, String message,long interval)
     {
    	 long startingTime = System.currentTimeMillis();
         boolean running = true;
         while (running)
         {
             try
             {
                 if (driver.getWebDriver().findElement(by) == null) return this;
             }
             catch (Exception e)
             {
             	try {
					Thread.currentThread().join(interval);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
                 //System.Threading.Thread.CurrentThread.Join((int)interval.Value.TotalMilliseconds);
                 if ((System.currentTimeMillis() - startingTime) > timeout)
                 {
                     running = false;
                 }
              
             }
         }
         throw new NotFoundException(message.toString());
     }
   
     public WebElement getWebElement()
	   {
    	 AssertExists();
		   return element;
	   }
   
     public Select selectFromDropdown() {
    	 AssertExists(); //this updates element !
    	 return new Select(element);
     }
     /* static ExpectedCondition<?> visibilityOfElementLocated = null;
     	public static Element waitUntilVisibility(By by, int time) throws InterruptedException {
			driver.Manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify implicitlyWait()
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver.getWebDriver()).withTimeout(time, TimeUnit.SECONDS)
					.pollingEvery(500, TimeUnit.MILLISECONDS)
					.ignoring(Exception.class);
			
			driver.waitUntil(visibilityOfElementLocated, by);
			return driver.FindElement(by);
			//new WebDriverWait(driver, time).until(ExpectedConditions.visibilityOfElementLocated(getLocator(locator)));
		}
    	public static Element waitUntilInVisibility(By by, int time) {
			driver.Manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify implicitlyWait()
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver.getWebDriver()).withTimeout(time, TimeUnit.SECONDS)
					.pollingEvery(500, TimeUnit.MILLISECONDS)
					.ignoring(Exception.class);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
			//new WebDriverWait(driver, time).until(ExpectedConditions.visibilityOfElementLocated(getLocator(locator)));
			return null;
		}
		
		public static void waitUntilPresence(By by, int time) {
			driver.Manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify implicitlyWait()
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver.getWebDriver()).withTimeout(time, TimeUnit.SECONDS)
					.pollingEvery(500, TimeUnit.MILLISECONDS)
					.ignoring(Exception.class);
			wait.until(ExpectedConditions.presenceOfElementLocated(by));
			//new WebDriverWait(driver, time).until(ExpectedConditions.presenceOfElementLocated(getLocator(locator)));
		}
		
		public static void waitUntilClickable(By by, int time) {
			driver.Manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS); // nullify implicitlyWait()
			Wait<WebDriver> wait = new FluentWait<WebDriver>(driver.getWebDriver()).withTimeout(time, TimeUnit.SECONDS)
					.pollingEvery(500, TimeUnit.MILLISECONDS)
					.ignoring(Exception.class);
			wait.until(ExpectedConditions.elementToBeClickable(by));
			//new WebDriverWait(driver, time).until(ExpectedConditions.elementToBeClickable(getLocator(locator)));
		}
		public ExpectedCondition<Integer> waitForElementToHaveCount(final By by) {
			return new ExpectedCondition<Integer>() {
		        public Integer apply(WebDriver driver) {
		            try {
		            	
		            	if(driver.findElement(by).getText().matches("-?\\d+(\\.\\d+)?")&&!driver.findElement(by).getText().isEmpty());
		            	return Integer.parseInt(driver.findElement(by).getText());
		            } catch (Exception e) {
		                return null; // catch all fail case
		            }
		        }

		        public String toString() {
		            return "an element to count";
		        }
		    };
		}
		public ExpectedCondition<Boolean> waitForElementToHaveExpectedText(final By by, final String text) {
			return new ExpectedCondition<Boolean>() {
		        public Boolean apply(WebDriver driver) {
		            try {
		            	
		            	if(driver.findElement(by).getText().equalsIgnoreCase(text)&&!driver.findElement(by).getText().isEmpty());
		            	return true;
		            } catch (Exception e) {
		                return false; // catch all fail case
		            }
		        }

		        public String toString() {
		            return "an element to count";
		        }
		    };
		}*/	

    public void Draganddrop(Element element1, Element element2)
     {
         AssertExists();
         Actions act = new Actions(driver.getWebDriver());
         act.dragAndDrop(element, element).perform();
   }	     

}
