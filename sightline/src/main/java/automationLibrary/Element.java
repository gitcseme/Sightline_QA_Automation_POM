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
import org.openqa.selenium.support.ui.WebDriverWait;


/// <summary>
/// Wraps the Selenium IWebElement.  Utilizes lazy-loading, so the instance will
/// wait to get the page element until a property is called.
/// </summary>
public class Element{

private By by;
private WebElement element = null;
private   Driver driver;
	
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
     
     public Element FindElementByXPath(String Xpath)
     {
         return FindElement(By.xpath(Xpath));
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
         element.clear();
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
     

     public By getBy() {
    	 return by;
     }

     public  Element VisibilityOfElementExplicitWait(Element element,int seconds) {

    	new WebDriverWait(driver.getWebDriver(),seconds).until(ExpectedConditions.visibilityOf(element.getWebElement()));//added here
    	return element;
     }
     public  Element ElementToBeClickableExplicitWait(Element element,int seconds) {
     	
     	new WebDriverWait(driver.getWebDriver(),seconds).until(ExpectedConditions.elementToBeClickable(element.getWebElement()));
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
    
    // ICE related elements
    
    public Element getA () { return driver.FindElement(By.tagName("a"));}
    public ElementCollection getAs () { return driver.FindElements(By.tagName("a"));}
    public Element getAbbr () { return driver.FindElement(By.tagName("abbr"));}
    public ElementCollection getAbbrs () { return driver.FindElements(By.tagName("abbr"));}
    public Element getAddress () { return driver.FindElement(By.tagName("address"));}
    public ElementCollection getAddresses () { return driver.FindElements(By.tagName("address"));}
    public Element getArea () { return driver.FindElement(By.tagName("area"));}
    public ElementCollection getAreas () { return driver.FindElements(By.tagName("area"));}
    public Element getArticle () { return driver.FindElement(By.tagName("article"));}
    public ElementCollection getArticles () { return driver.FindElements(By.tagName("article"));}
    public Element getAside () { return driver.FindElement(By.tagName("aside"));}
    public ElementCollection getAsides () { return driver.FindElements(By.tagName("aside"));}
    public Element getAudio () { return driver.FindElement(By.tagName("audio"));}
    public ElementCollection getAudios () { return driver.FindElements(By.tagName("audio"));}
    public Element getB () { return driver.FindElement(By.tagName("b"));}
    public ElementCollection getBs () { return driver.FindElements(By.tagName("b"));}
    public Element getBase () { return driver.FindElement(By.tagName("base"));}
    public ElementCollection getBases () { return driver.FindElements(By.tagName("base"));}
    public Element getBdi () { return driver.FindElement(By.tagName("bdi"));}
    public ElementCollection getBdis () { return driver.FindElements(By.tagName("bdi"));}
    public Element getBdo () { return driver.FindElement(By.tagName("bdo"));}
    public ElementCollection getBdos () { return driver.FindElements(By.tagName("bdo"));}
    public Element getBlockquote () { return driver.FindElement(By.tagName("blockquote"));}
    public ElementCollection getBlockquotes () { return driver.FindElements(By.tagName("blockquote"));}
    public Element getBody () { return driver.FindElement(By.tagName("body"));}
    public ElementCollection getBodys () { return driver.FindElements(By.tagName("body"));}
    public Element getBr () { return driver.FindElement(By.tagName("br"));}
    public ElementCollection getBrs () { return driver.FindElements(By.tagName("br"));}
    public Element getButton () { return driver.FindElement(By.tagName("button"));}
    public ElementCollection getButtons () { return driver.FindElements(By.tagName("button"));}
    public Element getCanvas () { return driver.FindElement(By.tagName("canvas"));}
    public ElementCollection getCanvases () { return driver.FindElements(By.tagName("canvas"));}
    public Element getCaption () { return driver.FindElement(By.tagName("caption"));}
    public ElementCollection getCaptions () { return driver.FindElements(By.tagName("caption"));}
    public Element getCite () { return driver.FindElement(By.tagName("cite"));}
    public ElementCollection getCites () { return driver.FindElements(By.tagName("cite"));}
    public Element getCode () { return driver.FindElement(By.tagName("code"));}
    public ElementCollection getCodes () { return driver.FindElements(By.tagName("code"));}
    public Element getCol () { return driver.FindElement(By.tagName("col"));}
    public ElementCollection getCols () { return driver.FindElements(By.tagName("col"));}
    public Element getColgroup () { return driver.FindElement(By.tagName("colgroup"));}
    public ElementCollection getColgroups () { return driver.FindElements(By.tagName("colgroup"));}
    public Element getDatalist () { return driver.FindElement(By.tagName("datalist"));}
    public ElementCollection getDatalists () { return driver.FindElements(By.tagName("datalist"));}
    public Element getDd () { return driver.FindElement(By.tagName("dd"));}
    public ElementCollection getDds () { return driver.FindElements(By.tagName("dd"));}
    public Element getDel () { return driver.FindElement(By.tagName("del"));}
    public ElementCollection getDels () { return driver.FindElements(By.tagName("del"));}
    public Element getDetails () { return driver.FindElement(By.tagName("details"));}
    public ElementCollection getDetailses () { return driver.FindElements(By.tagName("details"));}
    public Element getDfn () { return driver.FindElement(By.tagName("dfn"));}
    public ElementCollection getDfns () { return driver.FindElements(By.tagName("dfn"));}
    public Element getDialog () { return driver.FindElement(By.tagName("dialog"));}
    public ElementCollection getDialogs () { return driver.FindElements(By.tagName("dialog"));}
    public Element getDiv () { return driver.FindElement(By.tagName("div"));}
    public ElementCollection getDivs () { return driver.FindElements(By.tagName("div"));}
    public Element getDl () { return driver.FindElement(By.tagName("dl"));}
    public ElementCollection getDls () { return driver.FindElements(By.tagName("dl"));}
    public Element getDt () { return driver.FindElement(By.tagName("dt"));}
    public ElementCollection getDts () { return driver.FindElements(By.tagName("dt"));}
    public Element getEm () { return driver.FindElement(By.tagName("em"));}
    public ElementCollection getEms () { return driver.FindElements(By.tagName("em"));}
    public Element getEmbed () { return driver.FindElement(By.tagName("embed"));}
    public ElementCollection getEmbeds () { return driver.FindElements(By.tagName("embed"));}
    public Element getFieldset () { return driver.FindElement(By.tagName("fieldset"));}
    public ElementCollection getFieldsets () { return driver.FindElements(By.tagName("fieldset"));}
    public Element getFigcaption () { return driver.FindElement(By.tagName("figcaption"));}
    public ElementCollection getFigcaptions () { return driver.FindElements(By.tagName("figcaption"));}
    public Element getFigure () { return driver.FindElement(By.tagName("figure"));}
    public ElementCollection getFigures () { return driver.FindElements(By.tagName("figure"));}
    public Element getFooter () { return driver.FindElement(By.tagName("footer"));}
    public ElementCollection getFooters () { return driver.FindElements(By.tagName("footer"));}
    public Element getForm () { return driver.FindElement(By.tagName("form"));}
    public ElementCollection getForms () { return driver.FindElements(By.tagName("form"));}
    public Element getH1 () { return driver.FindElement(By.tagName("h1"));}
    public ElementCollection getH1s () { return driver.FindElements(By.tagName("h1"));}
    public Element getH2 () { return driver.FindElement(By.tagName("h2"));}
    public ElementCollection getH2s () { return driver.FindElements(By.tagName("h2"));}
    public Element getH3 () { return driver.FindElement(By.tagName("h3"));}
    public ElementCollection getH3s () { return driver.FindElements(By.tagName("h3"));}
    public Element getH4 () { return driver.FindElement(By.tagName("h4"));}
    public ElementCollection getH4s () { return driver.FindElements(By.tagName("h4"));}
    public Element getH5 () { return driver.FindElement(By.tagName("h5"));}
    public ElementCollection getH5s () { return driver.FindElements(By.tagName("h5"));}
    public Element getH6 () { return driver.FindElement(By.tagName("h6"));}
    public ElementCollection getH6s () { return driver.FindElements(By.tagName("h6"));}
    public Element getHead () { return driver.FindElement(By.tagName("head"));}
    public ElementCollection getHeads () { return driver.FindElements(By.tagName("head"));}
    public Element getHeader () { return driver.FindElement(By.tagName("header"));}
    public ElementCollection getHeaders () { return driver.FindElements(By.tagName("header"));}
    public Element getHr () { return driver.FindElement(By.tagName("hr"));}
    public ElementCollection getHrs () { return driver.FindElements(By.tagName("hr"));}
    public Element getHtml () { return driver.FindElement(By.tagName("html"));}
    public ElementCollection getHtmls () { return driver.FindElements(By.tagName("html"));}
    public Element getI () { return driver.FindElement(By.tagName("i"));}
    public ElementCollection getIs () { return driver.FindElements(By.tagName("i"));}
    public Element getIframe () { return driver.FindElement(By.tagName("iframe"));}
    public ElementCollection getIframes () { return driver.FindElements(By.tagName("iframe"));}
    public Element getImg () { return driver.FindElement(By.tagName("img"));}
    public ElementCollection getImgs () { return driver.FindElements(By.tagName("img"));}
    public Element getInput () { return driver.FindElement(By.tagName("input"));}
    public ElementCollection getInputs () { return driver.FindElements(By.tagName("input"));}
    public Element getIns () { return driver.FindElement(By.tagName("ins"));}
    public ElementCollection getInses () { return driver.FindElements(By.tagName("ins"));}
    public Element getKbd () { return driver.FindElement(By.tagName("kbd"));}
    public ElementCollection getKbds () { return driver.FindElements(By.tagName("kbd"));}
    public Element getLabel () { return driver.FindElement(By.tagName("label"));}
    public ElementCollection getLabels () { return driver.FindElements(By.tagName("label"));}
    public Element getLegend () { return driver.FindElement(By.tagName("legend"));}
    public ElementCollection getLegends () { return driver.FindElements(By.tagName("legend"));}
    public Element getLi () { return driver.FindElement(By.tagName("li"));}
    public ElementCollection getLis () { return driver.FindElements(By.tagName("li"));}
    public Element getLink () { return driver.FindElement(By.tagName("link"));}
    public ElementCollection getLinks () { return driver.FindElements(By.tagName("link"));}
    public Element getMain () { return driver.FindElement(By.tagName("main"));}
    public ElementCollection getMains () { return driver.FindElements(By.tagName("main"));}
    public Element getMap () { return driver.FindElement(By.tagName("map"));}
    public ElementCollection getMaps () { return driver.FindElements(By.tagName("map"));}
    public Element getMark () { return driver.FindElement(By.tagName("mark"));}
    public ElementCollection getMarks () { return driver.FindElements(By.tagName("mark"));}
    public Element getMenu () { return driver.FindElement(By.tagName("menu"));}
    public ElementCollection getMenus () { return driver.FindElements(By.tagName("menu"));}
    public Element getMenuitem () { return driver.FindElement(By.tagName("menuitem"));}
    public ElementCollection getMenuitems () { return driver.FindElements(By.tagName("menuitem"));}
    public Element getMeta () { return driver.FindElement(By.tagName("meta"));}
    public ElementCollection getMetas () { return driver.FindElements(By.tagName("meta"));}
    public Element getMeter () { return driver.FindElement(By.tagName("meter"));}
    public ElementCollection getMeters () { return driver.FindElements(By.tagName("meter"));}
    public Element getNav () { return driver.FindElement(By.tagName("nav"));}
    public ElementCollection getNavs () { return driver.FindElements(By.tagName("nav"));}
    public Element getOl () { return driver.FindElement(By.tagName("ol"));}
    public ElementCollection getOls () { return driver.FindElements(By.tagName("ol"));}
    public Element getOptgroup () { return driver.FindElement(By.tagName("optgroup"));}
    public ElementCollection getOptgroups () { return driver.FindElements(By.tagName("optgroup"));}
    public Element getOption () { return driver.FindElement(By.tagName("option"));}
    public ElementCollection getOptions () { return driver.FindElements(By.tagName("option"));}
    public Element getOutput () { return driver.FindElement(By.tagName("output"));}
    public ElementCollection getOutputs () { return driver.FindElements(By.tagName("output"));}
    public Element getP () { return driver.FindElement(By.tagName("p"));}
    public ElementCollection getPs () { return driver.FindElements(By.tagName("p"));}
    public Element getParam () { return driver.FindElement(By.tagName("param"));}
    public ElementCollection getParams () { return driver.FindElements(By.tagName("param"));}
    public Element getPicture () { return driver.FindElement(By.tagName("picture"));}
    public ElementCollection getPictures () { return driver.FindElements(By.tagName("picture"));}
    public Element getPre () { return driver.FindElement(By.tagName("pre"));}
    public ElementCollection getPres () { return driver.FindElements(By.tagName("pre"));}
    public Element getProgress () { return driver.FindElement(By.tagName("progress"));}
    public ElementCollection getProgresses () { return driver.FindElements(By.tagName("progress"));}
    public Element getQ () { return driver.FindElement(By.tagName("q"));}
    public ElementCollection getQs () { return driver.FindElements(By.tagName("q"));}
    public Element getS () { return driver.FindElement(By.tagName("s"));}
    public ElementCollection getSs () { return driver.FindElements(By.tagName("s"));}
    public Element getScript () { return driver.FindElement(By.tagName("script"));}
    public ElementCollection getScripts () { return driver.FindElements(By.tagName("script"));}
    public Element getSection () { return driver.FindElement(By.tagName("section"));}
    public ElementCollection getSections () { return driver.FindElements(By.tagName("section"));}
    public Element getSelect () { return driver.FindElement(By.tagName("select"));}
    public ElementCollection getSelects () { return driver.FindElements(By.tagName("select"));}
    public Element getSmall () { return driver.FindElement(By.tagName("small"));}
    public ElementCollection getSmalls () { return driver.FindElements(By.tagName("small"));}
    public Element getSource () { return driver.FindElement(By.tagName("source"));}
    public ElementCollection getSources () { return driver.FindElements(By.tagName("source"));}
    public Element getSpan () { return driver.FindElement(By.tagName("span"));}
    public ElementCollection getSpans () { return driver.FindElements(By.tagName("span"));}
    public Element getStrong () { return driver.FindElement(By.tagName("strong"));}
    public ElementCollection getStrongs () { return driver.FindElements(By.tagName("strong"));}
    public Element getStyle () { return driver.FindElement(By.tagName("style"));}
    public ElementCollection getStyles () { return driver.FindElements(By.tagName("style"));}
    public Element getSub () { return driver.FindElement(By.tagName("sub"));}
    public ElementCollection getSubs () { return driver.FindElements(By.tagName("sub"));}
    public Element getSummary () { return driver.FindElement(By.tagName("summary"));}
    public ElementCollection getSummarys () { return driver.FindElements(By.tagName("summary"));}
    public Element getSup () { return driver.FindElement(By.tagName("sup"));}
    public ElementCollection getSups () { return driver.FindElements(By.tagName("sup"));}
    public Element getSvg () { return driver.FindElement(By.tagName("svg"));}
    public ElementCollection getSvgs () { return driver.FindElements(By.tagName("svg"));}
    public Element getTable () { return driver.FindElement(By.tagName("table"));}
    public ElementCollection getTables () { return driver.FindElements(By.tagName("table"));}
    public Element getTbody () { return driver.FindElement(By.tagName("tbody"));}
    public ElementCollection getTbodys () { return driver.FindElements(By.tagName("tbody"));}
    public Element getTd () { return driver.FindElement(By.tagName("td"));}
    public ElementCollection getTds () { return driver.FindElements(By.tagName("td"));}
    public Element getTemplate () { return driver.FindElement(By.tagName("template"));}
    public ElementCollection getTemplates () { return driver.FindElements(By.tagName("template"));}
    public Element getTextarea () { return driver.FindElement(By.tagName("textarea"));}
    public ElementCollection getTextareas () { return driver.FindElements(By.tagName("textarea"));}
    public Element getTfoot () { return driver.FindElement(By.tagName("tfoot"));}
    public ElementCollection getTfoots () { return driver.FindElements(By.tagName("tfoot"));}
    public Element getTh () { return driver.FindElement(By.tagName("th"));}
    public ElementCollection getThs () { return driver.FindElements(By.tagName("th"));}
    public Element getThead () { return driver.FindElement(By.tagName("thead"));}
    public ElementCollection getTheads () { return driver.FindElements(By.tagName("thead"));}
    public Element getTime () { return driver.FindElement(By.tagName("time"));}
    public ElementCollection getTimes () { return driver.FindElements(By.tagName("time"));}
    public Element getTitle () { return driver.FindElement(By.tagName("title"));}
    public ElementCollection getTitles () { return driver.FindElements(By.tagName("title"));}
    public Element getTr () { return driver.FindElement(By.tagName("tr"));}
    public ElementCollection getTrs () { return driver.FindElements(By.tagName("tr"));}
    public Element getTrack () { return driver.FindElement(By.tagName("track"));}
    public ElementCollection getTracks () { return driver.FindElements(By.tagName("track"));}
    public Element getU () { return driver.FindElement(By.tagName("u"));}
    public ElementCollection getUs () { return driver.FindElements(By.tagName("u"));}
    public Element getUl () { return driver.FindElement(By.tagName("ul"));}
    public ElementCollection getUls () { return driver.FindElements(By.tagName("ul"));}
    public Element getVar () { return driver.FindElement(By.tagName("var"));}
    public ElementCollection getVars () { return driver.FindElements(By.tagName("var"));}
    public Element getVideo () { return driver.FindElement(By.tagName("video"));}
    public ElementCollection getVideos () { return driver.FindElements(By.tagName("video"));}
    public Element getWbr () { return driver.FindElement(By.tagName("wbr"));}
    public ElementCollection getWbrs () { return driver.FindElements(By.tagName("wbr"));}
}
