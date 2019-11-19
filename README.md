# Sightline_QA_Automation_POM

Plugins required in Eclipse :
1. TestNG 
2. Maven  

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

Jenkins: 

Jenkin location :
http://172.22.155.19:8080/job/SL

