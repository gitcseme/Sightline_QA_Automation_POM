# Sightline
---------------------------------------------------------------------------------------------------
This project is a Test Automation Framework built using Maven, TestNG and selenium to validate SightLine functionalities.
This is a Page Object Model (POM) framework and below details brief about folder structure.

> src/main/java is a root folder under this we have following packages:
  1. automationLibrary - Contains main driver and element classes.
  2. configsAndTestData - Contains class and XML files of configMain, environments and test data.
  3. pageFactory - Each page of the application is mapped to specific class here. All locators and functions of the specific page are    	maintained in specific class.
  4. testScriptSmoke - contains classes where somke test scenarios are covered. We could see sepearte class based on module.
  5. testScriptsRegression- contains classes where regression scenarios are covered.

> src/test/java - just a place holder. Empty one!

Other folders in the project directory :
------------------------------------------
> BrowserDrivers - contains chrome, IE browser drivers which are used by selenium to communicate with respective browsers.
                   This folder also contains bat files to kill background process of the browsers post execution of scripts.

> Misc -  Gmail properties file to deal with emails validation, fetch an OTP and activation links.
          Contains 'BatchPrintFiles' folder to manage batch print files.
          Also contains batch file to validate batch upload functionality.
	  
  
-----------------------------------------------------------------------------------------------------------
Plugins required in Eclipse :
1. TestNG -  To execute scripts and get automated report. 
2. Maven  - This is a Maven project so that building the project and getting all dependencies is made easy.
------------------------------------------------------------------------------------------------------------
Project setup in Eclipse:
--------------------------

1. Pull the project code from GIT location:

   https://github.consilio.com/Consilio/Sightline_QA_Automation_POM.git


2. Build the project to make sure all the dependencies are in place, .m2 folder will be created in your local machine to store all the jars.

3. Open the required environment xml(like QA, US or UK) file under ConfigAndTestData package and check the project name.
   If executing scripts on existing project then give the existing project name.
   If executing scripts on new project then give the new project name that you wish to create.
   Save changes

4. Open ConfigMain xml under ConfigAndTestData package, check below param before start executing.
	1. env - Environment on which you wish to run scripts, like QA, US, UK and so on.
	2. newProject - if it is YES then project creation and user assignment will be done. 
	3. ingestion - if it YES then ingestion will be done
	4. suite - to run required suites, it should be Smoke or Regression only 
	5. numberOfDataSets -  if 1 then one data set will be ingested, if 3 then 3 data sets will be ingested(we                                                go with option 1 for smoke suite)
	6. browserName - On which browser you wish to run, CHROME or IE
	
5. Open testng.xml 
	It conatins path to smoke and regression suites, uncomment required one(like shown below) suite and run. To run, right click on 	testng.xml and go to 'Run As' and then select TestNG Suite.
	
	<suite-file path="./smokeSuite.xml"/>

	<!-- <suite-file path="./regressionSuite.xml" /> -->

6. Refresh the project post execution to see test output folder
   Open emailable report to see the execution report.
---------------------------------------------------------------------------------------------------
Jenkins: 
---------------------------------------------------------------------------------------------------
Jenkin location :
http://172.22.155.19:8080/job/SL

One should create a job in jenkin with the right configurations to run the scripts/code on GIT.

Run existing job:
Existing job is with the name SL and pointing to git code. Select SL job and click on build now to run scripts.

Create a new job with below configurations:
1. Select New Item from Jenkin dashboard 
2. Provide a name and select maven option, click on OK
3. Select Git option under Source Code Management section 
4. In Repositories provide GIT code URL and credetials 
https://github.consilio.com/Consilio/Sightline_QA_Automation_POM.git
in 	Branches to build give brance you wish to run like */master

5. Under 'Build Triggers' select the option 'Build whenever a SNAPSHOT dependency is built'
6. In 'Build' section provide below details
Root POM : sightline/pom.xml
Goals and options : clean test

7. Save the config and click on biuld now to run scripts 

	Note: How suites picked up by Jenkins to run? 
	We have to mention testng.xml path in POM file, from there Jenkin picks suits.

8. We can monitor execution by clicking on 'Console Output' where we can see logs, for UI/browser refer point 10.

9. Once execution completed we could see link to 'Test Results'.
   Also we find details report under Trger folder :
   http://172.22.155.19:8080/job/SL/ws/sightline/target/surefire-reports/index.html
   http://172.22.155.19:8080/job/SL/ws/sightline/target/surefire-reports/emailable-report.html (along with time taken by each script)
   
   Screenshots for failed scripts : http://172.22.155.19:8080/job/SL/ws/sightline/Screenshots/
   
   Failed script's xml : 
   http://172.22.155.19:8080/job/SL/ws/sightline/target/surefire-reports/testng-failed.xml
   
   How to run only failed scripts in jenkins:
   --------------------------------------------
   1. We have added one xml in project folder with the name failed.xml
   2. Path to failed.xml is set in testng.xml
   3. Uncomment failed.xml line in testng.xml and comment somke and regression lines
   4. Copy the content from jenkin file http://172.22.155.19:8080/job/SL/ws/sightline/target/surefire-reports/testng-failed.xml to   failed.xml 
   
   Note: Post copying, add a test in failed.xml soon after suite definition for the Input class, like in smoke and regression suite xmls.
         
   5. Make sure failed.xml is updated in GIT branch that jenkin is pointing 
   6. Build the project, this timeonly failed scripts will be picked.

10. We could see the execution (UI/Browser) on the jenkin server. Below setting is required to see the UI/Browser.
	1. Logon to Jenkin server
	2. Open Windows services (or run: services.msc)
	3. Double click on Jenkins service
	4. In Logon tab, check this box: "Allow service to interact with Desktop"
	5. While executing small pop up will come, select 'view the message' to see exection and 'Return now' 
	   to comeout of view.



