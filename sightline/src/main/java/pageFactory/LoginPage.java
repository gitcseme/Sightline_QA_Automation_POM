package pageFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Properties;
import java.util.concurrent.Callable;
import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.FlagTerm;

import org.openqa.selenium.Alert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.interactions.Actions;
import org.testng.Reporter;

import automationLibrary.Driver;
import automationLibrary.Element;
import executionMaintenance.UtilityLog;
import junit.framework.Assert;
import testScriptsSmoke.Input;
import pageFactory.BaseClass;

public class LoginPage {

	Driver driver;
	BaseClass base;

	public Element getEuserName() {
		return driver.FindElementById("txtBxUserID");
	}

	public Element getEpassword() {
		return driver.FindElementById("txtBxUserPass");
	}

	public Element getEloginButton() {
		return driver.FindElementByName("submit");
	}

	public Element getActiveSessionYesButton() {
		return driver.FindElementByXPath("//button[@id='bot1-Msg1']");
	}

	public Element getSignoutMenu() {
		return driver.FindElementByXPath("//*[@id='user-selector']");
	}

	public Element getLogoutOption() {
		return driver.FindElementByXPath("//*[@id='utility-group']//a[contains(.,'Sign Out')]");
	}

	public Element getEmailMeButton() {
		return driver.FindElementById("emailme");
	}

	public Element getInputOTP() {
		return driver.FindElementById("txtPasscode");
	}

	// public Element getLogoutOption(){ return
	// driver.FindElementByXPath("//*[@id='utility-group']//a[contains(.,'Sign
	// Out')]"); }
	// Edit profile
	public Element getEditProfile() {
		return driver.FindElementById("EditProfile");
	}

	public Element getSelectLanguage() {
		return driver.FindElementById("select-1");
	}

	public Element getEditprofilesave() {
		return driver.FindElementById("btnSaveEditProfile");
	}

	// Added by krishna to handle additional pop up in New build

	public Element getGlobalMessagePopUp() {
		return driver.FindElementById("globalMessageModal");
	}

	public Element getGlobalMessagePopUpClose() {
		return driver.FindElementById("btnDialogClose");
	}

	// Added by Gopinath - 28/01/2022
	public Element getWarningLogOutMessage() {
		return driver.FindElementByXPath(
				"//i[@id='botClose1']/following-sibling::p[contains(text(),'Your current session expired. Please log in again')]");
	}

	// Added By Jeevitha
	public Element getUsername() {
		return driver.FindElementByXPath("//span[@class='clsUserName']");
	}

	public Element getProjectLang() {
		return driver.FindElementByXPath("//div[@class='project-context hidden-xs projclass']//span[text()='DE: Project:']");
	
	}
	
	//Added by arun
	public Element getFirstName() {
		return driver.FindElementById("txtBxUserName");
	}
	public Element getLastName() {
		return driver.FindElementById("txtBxUserLastName");
	}
	
	public LoginPage(Driver driver) {

		this.driver = driver;
		base = new BaseClass(driver);
		// This initElements method will create all WebElements
		// PageFactory.initElements(driver.getWebDriver(), this);

	}

	// @SuppressWarnings("static-access")
	public void loginToSightLine(String strUserName, String strPasword) {
		driver.waitForPageToBeReady();
		// Fill user name
		getEuserName().waitAndClick(10); // to adjust with app!
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEuserName().Visible();
			}
		}), Input.wait30);
		getEuserName().SendKeys(strUserName);

		// Fill password
		getEpassword().SendKeys(strPasword);

		// Click Login button
		getEloginButton().Click();
		// check if user session is active
		if (getActiveSessionYesButton().isElementAvailable(3)) {
			try {
				base.waitForElement(getActiveSessionYesButton());
				getActiveSessionYesButton().Click();
				// driver.Navigate().refresh();
				driver.waitForPageToBeReady();
				base.waitForElement(getEuserName());
				base.waitTillElemetToBeClickable(getEuserName());
				getEuserName().Clear();
				getEpassword().Clear();
				getEuserName().SendKeys(strUserName);
				// Fill password
				getEuserName().Click();
				getEpassword().SendKeys(strPasword);
				// Click Login button
				getEloginButton().Click();
				driver.waitForPageToBeReady();
			} catch (Exception e) {

			}
		}
//		if(getEmailMeButton().isElementAvailable(1)) {
//		// below code is to handles 2FA
//		try {
//			/*
//			 * driver.WaitUntil((new Callable<Boolean>() {public Boolean call(){return
//			 * getEuserName().Enabled() ;}}), Input.wait30);
//			 */
//			getEmailMeButton().Click();//30
//			getInputOTP().SendKeys(LoginPage.readGmailMail("Your one-time passcode to log into Sightline", "", "OTP",
//					strUserName, strPasword));
//			getEloginButton().Click();
//		} catch (Exception e1) {
//			// System.out.println("2FA is failed/disabled");
//		}
//		}

		// Make sure sign out menu is visible post login
		if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
			try {
				getGlobalMessagePopUpClose().waitAndClick(5);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSignoutMenu().Visible();
			}
		}), Input.wait30);
		BaseClass bc = new BaseClass(driver);
		try {
			// Modified on 12/24/21 - Raghuram (!strUserName.equals(Input.da1userName)
			if (!strUserName.equals(Input.sa1userName) && (!strUserName.equals(Input.da1userName)))
				bc.selectproject();
		} catch (Exception e) {
			// TODO: handle exception
		}
		Assert.assertTrue(getSignoutMenu().Visible());
		// System.out.println("Login success!");
		UtilityLog.info("Login success!");
		Reporter.log("Login success!", true);

	}

	/*
	 * login method overloaded to handle new projects for data for ingestion and
	 * additional req documents Added by Krishna
	 */
	public void loginToSightLine(String strUserName, String strPasword, String projectName) {
		driver.waitForPageToBeReady();
		// Fill user name
		getEuserName().waitAndClick(10); // to adjust with app!
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEuserName().Visible();
			}
		}), Input.wait30);
		getEuserName().SendKeys(strUserName);

		// Fill password
		getEpassword().SendKeys(strPasword);

		// Click Login button
		getEloginButton().Click();
		// check if user session is active
		if (getActiveSessionYesButton().isElementAvailable(3)) {
			try {
				base.waitForElement(getActiveSessionYesButton());
				getActiveSessionYesButton().Click();
				// driver.Navigate().refresh();
				driver.waitForPageToBeReady();
				base.waitForElement(getEuserName());
				base.waitTillElemetToBeClickable(getEuserName());
				getEuserName().Clear();
				getEpassword().Clear();
				getEuserName().SendKeys(strUserName);
				// Fill password
				getEuserName().Click();
				getEpassword().SendKeys(strPasword);
				// Click Login button
				getEloginButton().Click();
				driver.waitForPageToBeReady();
			} catch (Exception e) {

			}
		}

		// Make sure sign out menu is visible post login
		if (getGlobalMessagePopUpClose().isElementAvailable(10)) {
			try {
				getGlobalMessagePopUpClose().waitAndClick(5);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSignoutMenu().Visible();
			}
		}), Input.wait30);
		BaseClass bc = new BaseClass(driver);
		try {
			bc.selectproject(projectName);
		} catch (Exception e) {
			// TODO: handle exception
		}
		Assert.assertTrue(getSignoutMenu().Visible());
		// System.out.println("Login success!");
		UtilityLog.info("Login success!");
		Reporter.log("Login success!", true);

	}

//	public void logout() {
//
//		driver.Navigate().refresh();
//		
//		try {
//			try {
//				Thread.sleep(8000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			Alert alert = driver.getWebDriver().switchTo().alert();
//			String alertText = alert.getText();
//			System.out.println("Alert data: " + alertText);
//			UtilityLog.info("Alert data: " + alertText);
//			alert.accept();
//		} catch (NoAlertPresentException e) {
//			// e.printStackTrace();
//		}
//
//		driver.scrollPageToTop();
//		driver.WaitUntil((new Callable<Boolean>() {
//			public Boolean call() {
//				return getSignoutMenu().Visible();
//			}
//		}), Input.wait90);
//		getSignoutMenu().waitAndClick(10);
//		driver.WaitUntil((new Callable<Boolean>() {
//			public Boolean call() {
//				return getLogoutOption().Visible();
//			}
//		}), 30000);
//		getLogoutOption().waitAndClick(5);
//		;
//		driver.WaitUntil((new Callable<Boolean>() {
//			public Boolean call() {
//				return getEuserName().Visible();
//			}
//		}), 30000);
//		bc.waitTillElemetToBeClickable(getEuserName());)
//		Assert.assertTrue(getEuserName().Visible());
//		UtilityLog.info("Logged out successfully!");
//	}

	public void logout() {

		driver.Navigate().refresh();
		try {
			try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Alert alert = driver.getWebDriver().switchTo().alert();
			String alertText = alert.getText();
			System.out.println("Alert data: " + alertText);
			UtilityLog.info("Alert data: " + alertText);
			alert.accept();
		} catch (NoAlertPresentException e) {
			// e.printStackTrace();
		}

		driver.scrollPageToTop();
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSignoutMenu().Visible();
			}
		}), Input.wait90);
		getSignoutMenu().waitAndClick(10);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getLogoutOption().Visible();
			}
		}), 30000);
		getLogoutOption().waitAndClick(5);
		;
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEuserName().Visible();
			}
		}), 30000);
		// base.waitTillElemetToBeClickable(getEuserName());
		base.waitForElement(getEuserName());
		Assert.assertTrue(getEuserName().Visible());
		UtilityLog.info("Logged out successfully!");
	}

	public void logoutWithoutAssert() {
		if (getSignoutMenu().isElementAvailable(1)) {
			driver.Navigate().refresh();
			try {
				base.waitTime(8);
				base.handleAlert();
				driver.scrollPageToTop();
				base.waitForElement(getSignoutMenu());
				Element element = getSignoutMenu();
				element.Click();
				base.waitForElement(getLogoutOption());
				element = getLogoutOption();
				element.Click();
				driver.waitForPageToBeReady();
			} catch (Exception e) {
				UtilityLog.info("Logout failed due to exception " + e.getMessage());
			}
		}
	}

	public void logOutWithConfirmation() {

		driver.Navigate().refresh();

		driver.scrollPageToTop();

		getSignoutMenu().waitAndClick(5);
		try {
			BaseClass bc = new BaseClass(driver);
			bc.getPopupYesBtn().waitAndClick(10);
			System.out.println("For DocView - Pop up confirmation dialog is shown and clicked on yes button");
			UtilityLog.info("For DocView - Pop up confirmation dialog is shown and clicked on yes button");
		} catch (Exception e1) {
			System.out.println("For Doc View - Pop up confirmation dialog is not appeared");
			UtilityLog.info("For Doc View - Pop up confirmation dialog is not appeared");
		}

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getLogoutOption().Visible();
			}
		}), 30000);
		getLogoutOption().waitAndClick(5);
		;
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEuserName().Visible();
			}
		}), 30000);
		Assert.assertTrue(getEuserName().Visible());
	}

	public void closeBrowser() {
		driver.close();

	}

	public void quitBrowser() {
		driver.Quit();

	}

	public String dynamicNameAppender() {
		StringBuilder sb = new StringBuilder();
		Calendar now = Calendar.getInstance(); // Gives you the current time
		sb.append(now.get(Calendar.DAY_OF_MONTH));
		sb.append(now.get(Calendar.MONTH));
		sb.append(now.get(Calendar.HOUR));
		sb.append(now.get(Calendar.MINUTE));
		sb.append(now.get(Calendar.SECOND));
		return sb.toString();

	}

	// This function is to read user activation link or 2FA OTP from gmail
	// to read activation link call :LoginPage.readGmailMail("Welcome
	// ","DERev!","ActivationLink","a.utomationsightlineconsilio@gmail.com","consilio@123");
	// for project allocation call:LoginPage.readGmailMail("Dear
	// ","DERev!","ActivationLink","a.utomationsightlineconsilio@gmail.com","consilio@123");
	// to get otp call:LoginPage.readGmailMail("Your one-time passcode to log into
	// Sightline","","OTP",Input.pa1userName,Input.pa1password);

	@SuppressWarnings("null")
	public static String readGmailMail(String SujjectLine, String userName, String OTPorURL, String userID,
			String userPwd) {
		System.out.println("passed : " + SujjectLine + userName);
		UtilityLog.info("passed : " + SujjectLine + userName);
		Properties props = new Properties();
		String url = null;
		String otp = null;

		try {
			int waitForMail = 35;
			for (int wait = 0; wait < waitForMail; wait++) {
				props.load(new FileInputStream(new File(System.getProperty("user.dir") + Input.batchFilesPath
						+ "gmailProperties/" + "smtp.properties")));
				Session session = Session.getDefaultInstance(props, null);

				Store store = session.getStore("imaps");
				store.connect("smtp.gmail.com", userID, "consilio@123");

				Folder inbox = store.getFolder("inbox");
				inbox.open(Folder.READ_WRITE);
				// int messageCount = inbox.getMessageCount();
				// System.out.println("Total Messages:- " + messageCount);

				// search for all "unseen" messages
				Flags seen = new Flags(Flags.Flag.SEEN);
				FlagTerm unseenFlagTerm = new FlagTerm(seen, false);
				Message messages[] = inbox.search(unseenFlagTerm);
				boolean mailReceived = false;
				for (int i = messages.length - 1; i > messages.length - 5; i--) {

					if (messages[i].getSubject().trim().startsWith(SujjectLine + userName)) {
						mailReceived = true;
						System.out.println("e-mail Subject:- " + messages[i].getSubject());
						UtilityLog.info("e-mail Subject:- " + messages[i].getSubject());
						Object msgContent = messages[i].getContent();

						String content = "";
						String link = null;
						if (msgContent instanceof Multipart) {

							Multipart multipart = (Multipart) msgContent;

							for (int j = 0; j < multipart.getCount(); j++) {

								BodyPart bodyPart = multipart.getBodyPart(j);
								link = bodyPart.getContent().toString();
								if (OTPorURL.equalsIgnoreCase("OTP")) { // if looking for otp
									otp = link.substring(link.indexOf("code is") + 8, link.indexOf(", which"));
									// break;
								} else { // is looking for activation link
									if (link.substring(link.indexOf("<") + 1, link.indexOf(">")).startsWith("http")) {
										url = link.substring(link.indexOf("Click Here") + 11, link.indexOf(">"));
										// break;
									}
								}
								// String disposition = bodyPart.getDisposition();
								// break;
							}
						} else {
							if (msgContent instanceof String) {
								// System.out.println("not mp");
								link = msgContent.toString();
								content = (String) msgContent;
								if (OTPorURL.equalsIgnoreCase("OTP")) { // if looking for otp
									// System.out.println(link);
									otp = link.substring(link.indexOf("code is") + 8, link.indexOf(", which"));
									// break;
								} else { // is looking for activation link
									if (link.substring(link.indexOf("<") + 1, link.indexOf(">")).startsWith("http")) {
										url = link.substring(link.indexOf("Click Here") + 11, link.indexOf(">"));
										// break;

									}
								}
							}

						}

					}
				}

				if (mailReceived)
					break;
				else {
					if (wait >= waitForMail) {
						System.out.println("no mail, waited for given time..");
						UtilityLog.info("no mail, waited for given time..");
					}
					Thread.sleep(5000);
					System.out.println("Waiting for mail!");
					UtilityLog.info("Waiting for mail!");
				}
				inbox.close(true);
				store.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		if (OTPorURL.equalsIgnoreCase("OTP"))
			return otp;
		else
			return url;

	}

	public static void clearBrowserCache() {
		try {
			String[] command = { "cmd.exe", "/C", "Start",
					System.getProperty("user.dir") + "//BrowserDrivers//chromeBrowser.bat" };
			Runtime.getRuntime().exec(command);
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// p.destroyForcibly();
		} catch (IOException ex1) {
		}
		// Thread.sleep(5000); //wait for clean up activity
	}

	/*
	 * @created by Jeevitha.R 
	 * @parameter: Edit profile language
	 */
	public void editProfile(String language) {

		base.waitTime(4);
		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSignoutMenu().Visible() && getSignoutMenu().Enabled();
			}
		}), Input.wait60);
		getSignoutMenu().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getEditProfile().Visible() && getEditProfile().Enabled();
			}
		}), Input.wait30);
		getEditProfile().waitAndClick(10);

		driver.WaitUntil((new Callable<Boolean>() {
			public Boolean call() {
				return getSelectLanguage().Visible() && getSelectLanguage().Enabled();
			}
		}), Input.wait30);

		getSelectLanguage().selectFromDropdown().selectByVisibleText(language);
		System.out.println("Selected Language : " + language);
		base.stepInfo("Selected Language : " + language);
		getEditprofilesave().waitAndClick(10);
	}

	/**
	 * @Author Jeevitha
	 * @Description : returns Current Username
	 * @return
	 */
	public String getCurrentUserName() {
		driver.waitForPageToBeReady();
		base.waitForElement(getSignoutMenu());
		getSignoutMenu().waitAndClick(10);

		base.waitForElement(getUsername());
		String username = getUsername().getText();
		System.out.println("Logged In Username : " + username);
		getSignoutMenu().waitAndClick(10);
		return username;
	}
	
	/**
	 * @Author Jeevitha
	 * @Description : switch Language to English
	 */
	public void switchProjectToEnglish() {
		if(getProjectLang().isElementAvailable(3)) {
		editProfile("English - United States");
		}else {
			System.out.println("Selected language : English");
		}
	}
}
