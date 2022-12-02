package pageFactory;

import java.util.*;
import java.util.concurrent.Callable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import junit.framework.Assert;
import testScriptsSmoke.Input;


public class ClassificationPage {

    Driver driver;
    BaseClass base;
    SoftAssert sa;
   
    public Element getMaxQualified(){ return driver.FindElementById("ddlMaxQualified"); }
    public Element getCommentName(){ return driver.FindElementById("CommentLabel"); }
    public Element getEditCommentButton(){ return driver.FindElementByXPath("//table[@id='CommentsTable']//tr[1]//a[contains(text(),'Edit')]"); }
    public Element getDeleteCommentButton(String commentname){ return driver.FindElementByXPath("//table[@id='CommentsTable']//tr[contains(.,'"+commentname+"')]//a[contains(text(),'Delete')]"); }
    public Element getPopupYesBtn(){ return driver.FindElementByXPath("//button[@id='bot1-Msg1']"); }
    
    public Element getSaveBtn(){ return driver.FindElementById("btnSave"); }
    public Element getCommentlistTable(){ return driver.FindElementByXPath("//*[@id='CommentsTable']"); }
    public Element getLabelQC(){ return driver.FindElementByXPath("//label[text()='QC']"); }
    public Element getLevellabel(int no){ return driver.FindElementByXPath("//label[text()='Level "+no+"']"); }
    //
    public Element getRateDD(int level) {
    	return driver.FindElementByXPath("(//select[@id='ddlCurrencyType'])["+level+"]");
    }
    
    public ElementCollection getRateDDAllOptions(int level) {
    	return driver.FindElementsByXPath("(//select[@id='ddlCurrencyType'])["+level+"]//option");
    }
    
    public Element getClassificationName(int level) {
    	return driver.FindElementByXPath("(//input[@id='txtClassificationName'])["+level+"]");
    }
    
    public Element getClassificationRatevalue(int level) {
    	return driver.FindElementByXPath("(//input[@id='txtCurrenctyValue'])["+level+"]");

    }
    
    public Element getClassificationNameQC() {
    	return driver.FindElementByXPath("(//input[@id='txtClassificationName'])[last()]");
    }
    
    public Element getClassificationRatevalueQC() {
    	return driver.FindElementByXPath("(//input[@id='txtCurrenctyValue'])[last()]");
    }
    
    public Element getRateDDQC() {
    	return driver.FindElementByXPath("(//select[@id='ddlCurrencyType'])[last()]");
    }
    
    public Element getClassificationNameQCErrormsg() {
    	return driver.FindElementByXPath("(//span[@id='txtClassificationName-error'])[last()]");
    }
    
    public Element getClassificationRateTypeErrormsgQC() {
    	return driver.FindElementByXPath("(//span[@id='ddlCurrencyType-error'])[last()]");
    }
 
    public Element getClassificationRatevalueErrormsgQC() {
    	return driver.FindElementByXPath("(//span[@id='txtCurrenctyValue-error'])[last()]");
    }
    
  
     public ClassificationPage(Driver driver){

        this.driver = driver;
        base = new BaseClass(driver);
        sa = new SoftAssert();
//        this.driver.getWebDriver().get(Input.url+ "Assignment/Classification");
      }

    public void VerifyClassification() throws InterruptedException {
    	
    	List<String> explevelname = new ArrayList<>(Arrays.asList("1","2","3","4","5"));
    	List<String> levelname = new ArrayList<String>();
		Select select = new Select(getMaxQualified().getWebElement());
		List<WebElement> levels = select.getOptions();
		
		for(int i=1;i<=5;i++)
		{
		    System.out.println(levels.get(i).getText());
		    levelname.add(levels.get(i).getText());
		}
		System.out.println(levelname);
		System.out.println(explevelname);
		
		if(explevelname.containsAll(levelname))
		{
			System.out.println("Pass");
		}
		else {
			System.out.println("Fail");
		}
		
	
		//display all level names
		for(int k=1;k<=5;k++)
		{
			getMaxQualified().selectFromDropdown().selectByValue(Integer.toString(k));
			Thread.sleep(2000);
			Assert.assertTrue(getLevellabel(k).Displayed());
			Assert.assertTrue(getLabelQC().Displayed());
	}
		
		
		
		
    }

	/**
	 * @author Iyappan.Kasinathan
	 * @description This method used to navigate to classification page
	 */
	public void navigateClassificationPage() {
		try {
			driver.getWebDriver().get(Input.url + "Assignment/Classification");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception while navigating to assignments page " + e.getMessage());
		}
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method used to verify the rate dd options
	 */
	public void VerifyRateDDOptions() {
		driver.waitForPageToBeReady();
		List<String> ExpectedOptionsList = new ArrayList<>(
				Arrays.asList("$ - AUD", "$ - CAD", "¥ - CNY", "¥ - JPY", "£ - GBP", "$ - USD", "? - INR"));
		List<String> ActualOptionsList = new ArrayList<String>();
		List<String> allOptions = base.availableListofElements(getRateDDAllOptions(1));

		for (int i = 1; i <= 7; i++) {
			base.stepInfo(allOptions.get(i) + " - Available in DropDown");
			ActualOptionsList.add(allOptions.get(i));
		}
		System.out.println(ActualOptionsList);
		System.out.println(ExpectedOptionsList);

		try {

			sa.assertEquals(ExpectedOptionsList, ActualOptionsList);
			sa.assertAll();
			base.passedStep("Options are all Available in Rate Type DropDown");
		} catch (Exception e) {
			base.failedStep("Rate Dropdown available options are not expected");
		}
	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method used to verify the error alerts
	 */
	public void verifyClassifactionFieldsErrorAlerts() {
		getMaxQualified().selectFromDropdown().selectByVisibleText("--Select--");
		driver.waitForPageToBeReady();
		if (getLabelQC().isElementAvailable(4)) {
			base.waitForElement(getClassificationNameQC());
			getClassificationNameQC().waitAndClick(5);
			getClassificationNameQC().Clear();
			getRateDDQC().selectFromDropdown().selectByVisibleText("--Select--");
			getClassificationRatevalueQC().waitAndClick(5);
			getClassificationRatevalueQC().Clear();
			base.waitForElement(getSaveBtn());
			getSaveBtn().Click();
			sa.assertEquals(getClassificationNameQCErrormsg().getText(),
					"You must specify the classification label");
			sa.assertEquals(getClassificationRateTypeErrormsgQC().getText(), "You must specify the currency type.");
			sa.assertEquals(getClassificationRatevalueErrormsgQC().getText(),
					"You must specify the currency value.");
			sa.assertAll();
		} else {
			base.failedStep("QC Label Is Not Displayed");
		}

	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method used to verify the max qualified function
	 */
	public void verifyMaxQualifiedFunctionality(int level) throws InterruptedException {
		base.waitForElement(getMaxQualified());
		getMaxQualified().selectFromDropdown().selectByValue(Integer.toString(level));

		for (int i = 1; i <= level; i++) {
			sa.assertTrue( getLevellabel(i).isElementAvailable(4),"Level " + i + " Label is Not Available");
			base.stepInfo("Level " + i + " Label is Displaying");

			sa.assertTrue(getClassificationName(i).isElementAvailable(4),"Level " + i + " Name Field is Not Available");
			base.stepInfo("Level " + i + " Name Field is Displaying");

			sa.assertTrue(getClassificationRatevalue(i).isElementAvailable(4),"Level " + i + " Rate Value Field is Not Available");
			base.stepInfo("Level " + i + " Rate Value Field is Displaying");

			sa.assertTrue( getRateDD(i).isElementAvailable(4),"Level " + i + " Rate Type Field is Not Available");
			base.stepInfo("Level " + i + " Rate Type Field is Displaying");
		}
		sa.assertTrue( getLabelQC().isElementAvailable(4),"QC Label is Not Available");
		base.stepInfo("QC Label is Displaying");

		sa.assertTrue( getClassificationNameQC().isElementAvailable(3),"QC Name Field is Not Available");
		base.stepInfo("QC Name Field is Displaying");

		sa.assertTrue(getClassificationRatevalueQC().isElementAvailable(3),"QC Rate Value Field is Not Available");
		base.stepInfo("QC Rate Value Field is Displaying");

		sa.assertTrue( getRateDDQC().isElementAvailable(3),"QC Rate Type Field is Not Available");
		base.stepInfo("QC Rate Type Field is Displaying");
		sa.assertAll();

	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method used to update the level classification
	 */
	public void updateLevelClassificDetailsNotSave(int level, String name, String rateType, String rateValue) {
		driver.waitForPageToBeReady();
		base.waitForElement(getLevellabel(level));
		if (getLevellabel(level).isElementAvailable(3)) {
			base.waitForElement(getClassificationName(level));
			getClassificationName(level).SendKeys(name);
			base.waitForElement(getClassificationRatevalue(level));
			getClassificationRatevalue(level).SendKeys(rateValue);
			base.waitForElement(getRateDD(level));
			getRateDD(level).selectFromDropdown().selectByVisibleText(rateType);
		} else {
			base.failedStep("Level " + level + " Label is not Present");
		}

	}
	/**
	 * @author Iyappan.Kasinathan
	 * @description This method used to update the qc level classification
	 */
	public void updateQCClassificDetailsNotSave(String name, String rateType, String rateValue) {
		driver.waitForPageToBeReady();
		base.waitForElement(getLabelQC());
		if (getLabelQC().isElementAvailable(3)) {
			base.waitForElement(getClassificationNameQC());
			getClassificationNameQC().SendKeys(name);
			base.waitForElement(getClassificationRatevalueQC());
			getClassificationRatevalueQC().SendKeys(rateValue);
			base.waitForElement(getRateDDQC());
			getRateDDQC().selectFromDropdown().selectByVisibleText(rateType);
		} else {
			base.failedStep("QC Label is not Present");
		}
	}

   }
     
  
   
 
 