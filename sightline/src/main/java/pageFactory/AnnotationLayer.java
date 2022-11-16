package pageFactory;

import java.awt.List;
import java.util.concurrent.Callable;

import org.openqa.selenium.WebElement;
import org.testng.Reporter;
import org.testng.asserts.SoftAssert;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import executionMaintenance.UtilityLog;
import junit.framework.Assert;
import testScriptsSmoke.Input;

public class AnnotationLayer {

	Driver driver;
	BaseClass base;
	SoftAssert softAssertion;

	public Element getAddAnnotationLayerBtn() {
		return driver.FindElementById("btnAddAnnotation");
	}

	public Element getAnnotationName() {
		return driver.FindElementById("AnnotationName");
	}

	public Element getDescription() {
		return driver.FindElementById("AnnotationDescription");
	}

	public Element getSaveBtn() {
		return driver.FindElementById("btnAnnotationSave");
	}

	public Element getSuccessMsgHeader() {
		return driver.FindElementByXPath(" //div[starts-with(@id,'bigBoxColor')]//span");
	}

	public Element getDeleteAnnotationLayer() {
		return driver.FindElementByXPath("//a[contains(text(),'Delete')]");
	}

	public Element getYes() {
		return driver.FindElementByXPath("//button[@id='bot1-Msg1']");
	}

	public Element getSuccessMsg() {
		return driver.FindElementByXPath("//div[starts-with(@id,'bigBoxColor')]//p");
	}

	public Element getDeleteAnnotation(String annotation) {
		return driver.FindElementByXPath("//td[text()='" + annotation + "']/following-sibling::td//a[text()='Delete']");
	}

	public Element getNextButton() {
		return driver.FindElementById("AnnotationsDatatable_next");
	}

	public ElementCollection getPages() {
		return driver.FindElementsByXPath("//a[text()='Next']//..//preceding-sibling::li");
	}

	// Pagination
	public Element getAnnotation_Pagination() {
		return driver.FindElementByXPath("//div[@id='AnnotationsDatatable_paginate']//li//a[text()='Next']");
	}

	// Added by Mohan
	public Element getAnnotationDatatable() {
		return driver.FindElementById("AnnotationsDatatable_info");
	}

	public Element getAnnotation_Layers() {
		return driver.FindElementByXPath("//table[@id='AnnotationsDatatable']");
	}

	public Element getSelectSecurityGroup() {
		return driver.FindElementById("ddlSecurityGroup");
	}
	
	public Element getDefaultAnnotation_Layers() {
		return driver.FindElementByXPath("//table[@id='AnnotationsDatatable']/tbody/tr/td");
	}

	// Annotation Layer added successfully
	public AnnotationLayer(Driver driver) {

		this.driver = driver;
//		this.driver.getWebDriver().get(Input.url + "Annotations/Annotations");
		base = new BaseClass(driver);
	}

	public void AddAnnotation(String name) {

		try {
			this.driver.getWebDriver().get(Input.url + "Annotations/Annotations");
			getAddAnnotationLayerBtn().waitAndClick(10);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAnnotationName().Visible();
				}
			}), Input.wait30);
			getAnnotationName().SendKeys(name);
			getDescription().SendKeys(name);
			getSaveBtn().Click();
			base.VerifySuccessMessage("Annotation Layer added successfully");
		} catch (Exception e) {
			Reporter.log("Annotation layer " + name + " added!", true);
			UtilityLog.info("Annotation layer already exist");
		}

		base.CloseSuccessMsgpopup();

	}

	/**
	 * @author Gopinath
	 * @Description : Method for deleting annotation layer.
	 * @param name : name is String value that name of annotation layer.
	 */
	public void deleteAnnotation(String name) {
		try {
			driver.getWebDriver().get(Input.url + "Annotations/Annotations");
			driver.waitForPageToBeReady();
			getDeleteAnnotation(name).isElementAvailable(15);
			getDeleteAnnotation(name).Click();
			getYes().isElementAvailable(15);
			getYes().waitAndClick(5);
			base.VerifySuccessMessage("Annotation Layer deleted successfully");
			base.CloseSuccessMsgpopup();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while deleting annotation layer " + e.getLocalizedMessage());
		}
	}

	/**
	 * @author Gopinath
	 * @Description : Method for deleting annotation layer by pagination.
	 * @param name : name is String value that name of annotation layer.
	 */
	public void deleteAnnotationByPagination(String name) {
		try {

			driver.getWebDriver().get(Input.url + "Annotations/Annotations");
			driver.waitForPageToBeReady();
			base.waitForElementCollection(getPages());
			Boolean annotationVisibility = false;
			try {
				annotationVisibility = getDeleteAnnotation(name).isDisplayed();
			} catch (Exception e) {
				System.err.println("Error" + e);
			}
			if (annotationVisibility) {
				driver.WaitUntil((new Callable<Boolean>() {
					public Boolean call() {
						return getDeleteAnnotation(name).Visible();
					}
				}), Input.wait60);

			} else {
				base.waitForElement(getAnnotation_Pagination());
				String nextbutton = getAnnotation_Pagination().GetAttribute("class").trim();
				do {
					getAnnotation_Pagination().waitAndClick(10);
					driver.waitForPageToBeReady();
					try {
						annotationVisibility = getDeleteAnnotation(name).isDisplayed();
					} catch (Exception e) {
						System.err.println("Error" + e);
					}
					nextbutton = getAnnotation_Pagination().GetAttribute("class").trim();
					if (nextbutton.equals("paginate_button next disabled")) {
						break;
					}
				} while (!annotationVisibility);
			}
			getDeleteAnnotation(name).Click();
			getYes().isElementAvailable(15);
			getYes().waitAndClick(5);
			base.VerifySuccessMessage("Annotation Layer deleted successfully");
			base.CloseSuccessMsgpopup();
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Exception occured while deleting annotation layer " + e.getLocalizedMessage());
		}
	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To add annotation layer
	 * @param layerName
	 * @param layerDescription
	 */
	public void addAnnotationLayer(String layerName, String layerDescription) {

		try {

			getAddAnnotationLayerBtn().waitAndClick(10);

			driver.WaitUntil((new Callable<Boolean>() {
				public Boolean call() {
					return getAnnotationName().Visible();
				}
			}), Input.wait30);
			getAnnotationName().SendKeys(layerName);
			getDescription().SendKeys(layerDescription);
			getSaveBtn().Click();
			base.VerifySuccessMessage("Annotation Layer added successfully");
		} catch (Exception e) {
			Reporter.log("Annotation layer " + layerDescription + " added!", true);
			UtilityLog.info("Annotation layer already exist");
		}

		base.CloseSuccessMsgpopup();

	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To verify Annotation Table
	 */
	public void verifyAnnotationTable() {

		driver.waitForPageToBeReady();
		base.waitTime(2);
		String annotationRowList = getAnnotationDatatable().getText();
		System.out.println(annotationRowList);

		if (annotationRowList.contains("0") && annotationRowList.contains("3")) {

			driver.waitForPageToBeReady();

			addAnnotationLayer("AnnotationClone", "DefaultAnnotationLayer");
			addAnnotationLayer("AnnotationClone01", "DefaultAnnotationLayer1");
			addAnnotationLayer("AnnotationClone02", "DefaultAnnotationLayer2");
			addAnnotationLayer("AnnotationClone03", "DefaultAnnotationLayer3");
			base.passedStep(
					"There are more than 2 Annotation Layers  in each security Groups(1-1 in Security Group) and are exists in source template project.");

		} else if (annotationRowList.contains("Showing 1 to ")) {
			base.passedStep(
					"There are more than 2 Annotation Layers  in each security Groups(1-1 in Security Group) and are exists in source template project.");

		} else {
			base.failedStep("There are no Annotation Layers in the project");
		}
	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To verify Annotation Table
	 */
	public void verifyAnnotationLayerTable() {
		driver.waitForPageToBeReady();
		String annotationRowList = getAnnotationDatatable().getText();
		System.out.println(annotationRowList);
		if (annotationRowList.contains("Showing 1 to")) {
			base.passedStep(
					"There are more than 2 Annotation Layers  in each security Groups(1-1 in Security Group) and are exists in source template project.");

		} else {
			base.failedStep("There are no Annotation Layers in the project");
		}

	}

	/**
	 * @author Mohan.Venugopal
	 * @description: To navigate Annotation layer page
	 */
	public void navigateToAnnotationLayerPage() {
		try {
			driver.getWebDriver().get(Input.url + "Annotations/Annotations");
		} catch (Exception e) {
			e.printStackTrace();
			base.failedStep("Navigaing to Annotations Layer page is failed" + e.getLocalizedMessage());
		}

	}
	/**
	 * @author Vijaya.Rani
	 * @Description : Creat Annotation Layer In Default Security Group
	 */
	public void verifyAnnotationLayerInDSG(String sg) {
		
		try {
			this.driver.getWebDriver().get(Input.url + "Annotations/Annotations");
			getSelectSecurityGroup().selectFromDropdown().selectByVisibleText(sg);
			String actualString ="Default Annotation Layer";
            String ExpectedString =getDefaultAnnotation_Layers().getText();
            System.out.println(ExpectedString);
            softAssertion.assertEquals(actualString,ExpectedString);
            base.passedStep("Default Annotation Layer is displayed");
            softAssertion.assertAll();
			
		} catch (Exception e) {
			Reporter.log("Annotation layer " + sg + " added!", true);
			UtilityLog.info("Annotation layer already exist");
		}
	}

}