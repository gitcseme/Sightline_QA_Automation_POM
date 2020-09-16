package stepDef;

import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.Callable;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import testScriptsSmoke.Input;
import cucumber.api.java.en.And;

@SuppressWarnings({"rawtypes" })
public class ExportContext extends CommonContext {

	/* 
	 * moved to CommonContext
	 * 
	public void sightline_is_launched(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
	public void login_as_pau(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

	 */

	@When("^.*(\\[Not\\] )? begin_new_export_process$")
	public void begin_new_export_process(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {
		String dateTime = new Long((new Date()).getTime()).toString();
		String template = (String) dataMap.get("export_template");
		String priorProd = (String) dataMap.get("prior_prod");

		try {
			if (scriptState) {
				prod.addNewExport("AutoExport"+dateTime, template, priorProd);
			} else {
				pass(dataMap,"Skipped adding new export");
			} 
		} catch (Exception e) {
			pass(dataMap,"Exception: 'When' condition 'begin_new_export_process'. Continuing...");
			// when conditions exceptions are ignored
		}
	}

	@Then("^.*(\\[Not\\] )? verify_export_dat_component_removed_advanced_option$")
	public void verify_export_dat_component_removed_advanced_option(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		try {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getDATTab().Visible()  ;}}), Input.wait30); 
			prod.getDATTab().Click();

			if (!prod.getDATComponentAdvanced().Displayed()) {
				pass(dataMap,"Production DAT component removed");
			} else {
				fail(dataMap,"Production DAT component not removed");
			}				
		} catch (Exception e) {
			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				pass(dataMap,"Production DAT component exception as expected");
			}
		}
	}


	@Then("^.*(\\[Not\\] )? verify_export_native_component_section$")
	public void verify_export_native_component_section(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		try {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getNativeTab().Visible()  ;}}), Input.wait30);
			prod.getNativeTab().Click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getNativeAdvanced().Displayed()  ;}}), Input.wait30);
			prod.getNativeAdvanced().Click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getNativeDescription().Displayed()  ;}}), Input.wait30);
			System.out.println(prod.getNativeDescription().getText());
			System.out.println((String) prod.getNativeDescription().GetCssValue("color"));
			
			if (prod.getNativeDescription().getText().contains(prod.nativeBlueText) && (String) prod.getNativeDescription().GetCssValue("color") == prod.blueColor) {
				pass(dataMap,"Native description has been updated to blue color");
			} else {
				fail(dataMap,"Native description has not been updated to blue color");
			}
		} catch (Exception e) {
			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				pass(dataMap,"Native description has not been updated to blue color as expected");
			}
		}
	}


	@Then("^.*(\\[Not\\] )? verify_export_text_component_changes$")
	public void verify_export_text_component_changes(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		try {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getTextTab().Visible()  ;}}), Input.wait30);
			prod.getTextTab().Click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getTextDescription().Visible()  ;}}), Input.wait30);
			
			if (!prod.getTextRemovedTiff().Displayed() && prod.getTextDescription().getText().contains(prod.textDescriptionChange)) {
				pass(dataMap,"Text component section has been changed");
			} else {
				fail(dataMap,"Text component section has not been changed");
			}
		} catch (Exception e) {
			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				pass(dataMap,"Text component section has not been changed as expected");
			}
		}
	}


	@Then("^.*(\\[Not\\] )? verify_export_tiff_pdf_advanced_options$")
	public void verify_export_tiff_pdf_advanced_options(boolean scriptState, HashMap dataMap) throws ImplementationException, Exception {

		try {
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getTIFFTab().Visible()  ;}}), Input.wait30);
			prod.getTIFFTab().Click();
			
			driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
					prod.getTIFFAdvanced().Displayed()  ;}}), Input.wait30);
			prod.getTIFFAdvanced().Click();

			if (!prod.getTIFFAdvancedRemovedExcel().Displayed()) {
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPDFTab().Visible()  ;}}), Input.wait30);
				prod.getPDFTab().Click();
				
				driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return 
						prod.getPDFAdvanced().Displayed()  ;}}), Input.wait30);
				prod.getPDFAdvanced().Click();
				
				if (!prod.getPDFAdvancedRemovedExcel().Displayed()) {
					pass(dataMap,"Excel option has been removed from TIFF and PDF advanced options");
				} else {
					fail(dataMap,"Excel option has not been removed from PDF advanced options");
				}	
			} else {
				fail(dataMap,"Excel option has not been removed from TIFF advanced options");
			}				
		} catch (Exception e) {
			if (scriptState) {
				throw new Exception(e.getMessage());
			} else {
				pass(dataMap,"Excel option has not been removed from TIFF and PDF advanced options as expected");
			}
		}

	}
}
