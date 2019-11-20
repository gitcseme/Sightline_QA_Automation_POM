# Sightline
This is a Test automation framework to validate SightLine functionalities.
This is a Page Object Model (POM) framework, below details brief about folder structure.
> src/main/java id root folder under this we have following packages:
  >automationLibrary - Contains main driver and element classes.
  >configsAndTestData - Contains class and XML files of configMain, environments and test data.
  >pageFactory - Each page of the application is mapped to specific class here. All locators and functions of the specific page are    	maintained in specific class.
  >testScriptSmoke - contains classes where somke test scenarios are covered. We could see sepearte class based on module.
  >testScriptsRegression- contains classes where regression scenarios are covered.

> src/test/java - just a place holder. Empty one!

Other folders in project directory :
> BrowserDrivers - contains chrome, IE browser drivers which are used by selenium to communicate with respective browsers.
                   This folder also contains bat files to kill background process of the browsers post execution of scripts.

>Misc
  
---------------------------------------------------------------------------------------------------
Plugins required in Eclipse :
1. TestNG 
2. Maven  
---------------------------------------------------------------------------------------------------
Project setup in Eclipse:

1. Pull the project code from GIT location:

   https://github.consilio.com/Consilio/Sightline_QA_Automation_POM.git


2. Build the project to make sure all the dependencies are in place

3. Open the required environment xml(like QA, US or UK) file under ConfigAndTestData package and check the project name.
   If executing on existing project then give the existing project name.
   If executing on new project then give the new project name that you wish to create.

4. Open ConfigMain xml under ConfigAndTestData package, check below param before start executing.
	<env>QA</env><!--it should be QA or DE or LD9PT or Chicago-->
	<newProject>NO</newProject><!--it should be YES or NO only--> // if it  is yes then project creation and user assignment will be done. 
	<ingestion>NO</ingestion><!--it should be YES or NO only-->  //If it yes ingestion will be done
	<suite>smoke</suite><!--it should be Smoke or Regression only--> 
	<numberOfDataSets>3</numberOfDataSets>  // if 1 then one data set will be ingested, if 3 then 3 data sets will be ingested(we go with option 1 for smoke suite)
	<browserName>CHROME</browserName>

5. Open testng.xml 
	uncomment required suite and comment others like below and run as TestNg

	<suite-file path="./smokeSuite.xml"/>

	<!-- <suite-file path="./regressionSuite.xml" /> -->

6. Refresh the project post execution to see test output folder
   Open emailable report to see the execution report.
---------------------------------------------------------------------------------------------------
Jenkins: 
---------------------------------------------------------------------------------------------------
Jenkin location :
http://172.22.155.19:8080/job/SL

Existing job is with the name SL and pointing to git code. Select SL job and click on build now to run scripts.

Create a new job with below settings:
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

8. we can monitor execution by clicking on 'Console Output'

9. Once execution completed we could see link to 'Test Results'.



