package sightline.productions;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.ParseException;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import pageFactory.BaseClass;
import pageFactory.DocListPage;
import pageFactory.DocViewPage;
import pageFactory.LoginPage;
import pageFactory.ProductionPage;
import pageFactory.SessionSearch;
import pageFactory.TagsAndFoldersPage;
import pageFactory.Utility;
import testScriptsSmoke.Input;

public class Export_Regression1 {

	Driver driver;
	LoginPage loginPage;
	BaseClass base;
	DocViewPage docView;
	Utility utility;

	@BeforeClass(alwaysRun = true)
	public void preConditions() throws InterruptedException, ParseException, IOException {
		System.out.println("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("******Execution started for " + this.getClass().getSimpleName() + "********");
		UtilityLog.info("Started Execution for prerequisite");

		Input input = new Input();
		input.loadEnvConfig();

	}

	@BeforeMethod(alwaysRun = true)
	public void beforeTestMethod(ITestResult result, Method testMethod) throws IOException {
		Reporter.setCurrentTestResult(result);
		System.out.println("------------------------------------------");
		System.out.println("Executing method :  " + testMethod.getName());
		UtilityLog.info(testMethod.getName());

		driver = new Driver();
		loginPage = new LoginPage(driver);
		loginPage.loginToSightLine(Input.pa1userName, Input.pa1password);
		UtilityLog.info("Logged in as User: " + Input.pa1userName);
		Reporter.log("Logged in as User: " + Input.pa1password);
	}

	/**
	 * @author Brundha RPMXCON-47500
	 * @Description Export: Verify that when production components step is completed
	 *              then DAT component should retain the 'TIFFPageCount'
	 * 
	 */
	@Test(description = "RPMXCON-47500", enabled = true, groups = { "regression" })
	public void verifyDatSectionMappingField() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47500 -Export component");
		base.stepInfo(
				"Export: Verify that when production components step is completed then DAT component should retain the 'TIFFPageCount'");

		String newExport = "Ex" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();

		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.verifyingDatFieldClassification();
		loginPage.logout();

	}

	/**
	 * @author Brundha RPMXCON-47983
	 * @Description To Verify Export Status after it complete the Export Generation
	 *              Task
	 * 
	 */
	@Test(description = "RPMXCON-47983", enabled = true, groups = { "regression" })
	public void verifyingCompletedStatusInTileView() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47983 -Export component");
		base.stepInfo("To Verify Export Status after it complete the Export Generation Task");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();

		this.driver.getWebDriver().get(Input.url + "Production/Home");
		driver.Navigate().refresh();
		driver.waitForPageToBeReady();
		page.selectExportSetFromDropDown();
		page.getProductionFromHomePage(productionname).isElementAvailable(10);
		String productionFromHomePage = page.getProductionFromHomePage(productionname).getText();
		if (productionFromHomePage.equals("Completed")) {
			base.passedStep("Completed status is displayed as expected");
		} else {
			base.failedStep("Status is not displayed as expected");
		}
		loginPage.logout();
	}

	/**
	 * @author Brundha RPMXCON-47471
	 * @Description To Verify PDF creation for export
	 * 
	 */
	@Test(description = "RPMXCON-47471", enabled = true, groups = { "regression" })
	public void verifyGenerationOfExportWithPdfSection() throws Exception {

		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47471 -Export component");
		base.stepInfo("To Verify PDF creation for export");

		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();

		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);

		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);

		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.fillingDATSection();
		page.fillingPDFSection(tagname);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani ModifyDate:21/06/2022 RPMXCON-48193
	 * @Description To Verify Export Generation for MP3 files using document level
	 *              numbering.
	 *
	 */
	@Test(description = "RPMXCON-48193", enabled = true, groups = { "regression" })
	public void verifyGenerationOfExportForMp3File() throws Exception {
		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-48193 -Export component");
		base.stepInfo("To Verify Export Generation for MP3 files using document level numbering.");
		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.fillingDATSection();
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		page.fillingSummaryAndPreview();
		page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
		loginPage.logout();
	}

	/**
	 * @author Vijaya.Rani Modify Date:21/06/2022 RPMXCON-47499
	 * @Description Export: Verify that TIFFPageCount is no longer presented under
	 *              "Doc Metadata"
	 *
	 */
	@Test(description = "RPMXCON-47499", enabled = true, groups = { "regression" })
	public void VerifydatSectionFieldMapping() throws Exception {
		base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47499 -Export component");
		base.stepInfo("Export: Verify that TIFFPageCount is no longer presented under 'Doc Metadata'");
		String newExport = "Ex" + Utility.dynamicNameAppender();
		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.verifyingDatMappingField();
		loginPage.logout();
	}

	

	/**
	 * @author Brundha
	 * 			RPMXCON-47933
	 * @Description To verify Preview for Export										 * 
	 */
		@Test(description="RPMXCON-47933",enabled = true,groups = { "regression" })
		public void verifyGenerationOfPdfFile() throws Exception {
			
			base = new BaseClass(driver);
		UtilityLog.info(Input.prodPath);
		base.stepInfo("RPMXCON-47933 -Export component");
		base.stepInfo("To verify Preview for Export");
		
		String foldername = "FolderProd" + Utility.dynamicNameAppender();
		String tagname = "Tag" + Utility.dynamicNameAppender();
		String newExport = "Ex" + Utility.dynamicNameAppender();
		String prefixID = Input.randomText + Utility.dynamicNameAppender();
		String suffixID = Input.randomText + Utility.dynamicNameAppender();
		
		TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
		tagsAndFolderPage.CreateFolder(foldername,Input.securityGroup);
		tagsAndFolderPage.createNewTagwithClassification(tagname,Input.tagNamePrev);
		
		SessionSearch sessionSearch = new SessionSearch(driver);
		sessionSearch.basicContentSearch(Input.testData1);
		sessionSearch.bulkFolderExisting(foldername);
		
		ProductionPage page = new ProductionPage(driver);
		String productionname = "p" + Utility.dynamicNameAppender();
		String subBates = page.getRandomNumber(2);
		page.selectingDefaultSecurityGroup();
		String text = page.getProdExport_ProductionSets().getText();
		if (text.contains("Export Set")) {
			page.selectExportSetFromDropDown();
		} else {
			page.createNewExport(newExport);
		}
		page.addANewExport(productionname);
		page.fillingDATSection();
		page.fillingTIFFSection(tagname,Input.tagNameTechnical);
		page.navigateToNextSection();
		page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
		page.navigateToNextSection();
		page.fillingDocumentSelectionPage(foldername);
		page.navigateToNextSection();
		page.fillingPrivGuardPage();
		page.fillingExportLocationPage(productionname);
		page.navigateToNextSection();
		driver.waitForPageToBeReady();
		page.viewingPreviewInSummaryTab();
		page.verifyingPdfgeneration(Input.searchString4);
		loginPage.logout();
		}
		
		
		/**
		 * @author Brundha
		 * 			RPMXCON-47473
		 * @Description To Verify TIFF creation for export
		 * 
		 */
			@Test(description="RPMXCON-47473",enabled = true,groups = { "regression" })
			public void verifyGenerationOfExportWithTiffSection() throws Exception {
				
				base = new BaseClass(driver);
			UtilityLog.info(Input.prodPath);
			base.stepInfo("RPMXCON-47473 -Export component");
			base.stepInfo("To Verify TIFF creation for export");
			
			String foldername = "FolderProd" + Utility.dynamicNameAppender();
			String tagname = "Tag" + Utility.dynamicNameAppender();
			String newExport = "Ex" + Utility.dynamicNameAppender();
			String prefixID = Input.randomText + Utility.dynamicNameAppender();
			String suffixID = Input.randomText + Utility.dynamicNameAppender();
		
			TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
			tagsAndFolderPage.CreateFolder(foldername,Input.securityGroup);
			tagsAndFolderPage.createNewTagwithClassification(tagname, "Select Tag Classification");
			
			SessionSearch sessionSearch = new SessionSearch(driver);
			sessionSearch.basicContentSearch(Input.testData1);
			sessionSearch.bulkFolderExisting(foldername);

			
			ProductionPage page = new ProductionPage(driver);
			String productionname = "p" + Utility.dynamicNameAppender();
			String subBates = page.getRandomNumber(2);
			page.selectingDefaultSecurityGroup();
			String text = page.getProdExport_ProductionSets().getText();
			if (text.contains("Export Set")) {
				page.selectExportSetFromDropDown();
			} else {
				page.createNewExport(newExport);
			}
			page.addANewExport(productionname);
			page.fillingDATSection();
			page.fillingTIFFSectionwithNativelyPlaceholder(tagname);
			page.navigateToNextSection();
			page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
			page.navigateToNextSection();
			page.fillingDocumentSelectionPage(foldername);
			page.navigateToNextSection();
			page.fillingPrivGuardPage();
			page.fillingExportLocationPage(productionname);
			page.navigateToNextSection();
			page.fillingSummaryAndPreview();
			page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
			loginPage.logout();
			}
	
			/**
			 * @author Brundha
			 * 			RPMXCON-47478
			 * @Description To Verify Export in production, basic Export without Production.
			 * 
			 */
				@Test(description="RPMXCON-47478",enabled = true,groups = { "regression" })
				public void verifyingGenerationOfExport() throws Exception {
					
					base = new BaseClass(driver);
				UtilityLog.info(Input.prodPath);
				base.stepInfo("RPMXCON-47478 -Export component");
				base.stepInfo("To Verify Export in production, basic Export without Production.");
				
				String foldername = "FolderProd" + Utility.dynamicNameAppender();
				String tagname = "Tag" + Utility.dynamicNameAppender();
				String newExport = "Ex" + Utility.dynamicNameAppender();
				String prefixID = Input.randomText + Utility.dynamicNameAppender();
				String suffixID = Input.randomText + Utility.dynamicNameAppender();
			
				TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
				tagsAndFolderPage.CreateFolder(foldername,Input.securityGroup);
				tagsAndFolderPage.createNewTagwithClassification(tagname,Input.tagNamePrev);
				
				SessionSearch sessionSearch = new SessionSearch(driver);
				sessionSearch.basicContentSearch(Input.testData1);
				sessionSearch.bulkFolderExisting(foldername);

				
				ProductionPage page = new ProductionPage(driver);
				String productionname = "p" + Utility.dynamicNameAppender();
				String subBates = page.getRandomNumber(2);
				page.selectingDefaultSecurityGroup();
				String text = page.getProdExport_ProductionSets().getText();
				if (text.contains("Export Set")) {
					page.selectExportSetFromDropDown();
				} else {
					page.createNewExport(newExport);
				}
				page.addANewExport(productionname);
				page.fillingDATSection();
				page.fillingTIFFSection(tagname);
				page.navigateToNextSection();
				page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
				page.navigateToNextSection();
				page.fillingDocumentSelectionPage(foldername);
				page.navigateToNextSection();
				page.fillingPrivGuardPage();
				page.fillingExportLocationPage(productionname);
				page.navigateToNextSection();
				page.fillingSummaryAndPreview();
				page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
				loginPage.logout();
				}
				

				/**
				 * @author Brundha
				 * 			RPMXCON-47498
				 * @DescriptionExport: Verify that the production field PageCount is renamed to TIFFPageCount
				 * 
				 */
					@Test(description="RPMXCON-47498",enabled = true,groups = { "regression" })
					public void verifyingTiffPageCountOptionInDatSection() throws Exception {
						
						base = new BaseClass(driver);
					UtilityLog.info(Input.prodPath);
					base.stepInfo("RPMXCON-47498 -Export component");
					base.stepInfo("Export: Verify that the production field PageCount is renamed to TIFFPageCount");
					
					String newExport = "Ex" + Utility.dynamicNameAppender();
					ProductionPage page = new ProductionPage(driver);
					String productionname = "p" + Utility.dynamicNameAppender();
					
					page.selectingDefaultSecurityGroup();
					String text = page.getProdExport_ProductionSets().getText();
					if (text.contains("Export Set")) {
						page.selectExportSetFromDropDown();
					} else {
						page.createNewExport(newExport);
					}
					page.addANewExport(productionname);
					page.verifyingDatFieldClassification();
					if(!page.getSourceFiledInDatSection().isElementAvailable(2)) {
						base.passedStep("PageCount is not displayed as expected");
					}else {
						base.failedStep("pagecount is displayed and not renamed ");}
					
					loginPage.logout();
					
					}
					/**
					 * @author Brundha
					 * 			RPMXCON-49383
					 * @Description Export: Verify 'Advanced Options' should be removed from the DAT component section in Production-Export
					 * 
					 */
						@Test(description="RPMXCON-49383",enabled = true,groups = { "regression" })
						public void verifyingAdvancedOptionInDatSection() throws Exception {
							
							base = new BaseClass(driver);
						UtilityLog.info(Input.prodPath);
						base.stepInfo("RPMXCON-49383 -Export component");
						base.stepInfo("Verify 'Advanced Options' should be removed from the DAT component section in Production-Export");
						
						String newExport = "Ex" + Utility.dynamicNameAppender();
						ProductionPage page = new ProductionPage(driver);
						String productionname = "p" + Utility.dynamicNameAppender();
						
						page.selectingDefaultSecurityGroup();
						String text = page.getProdExport_ProductionSets().getText();
						if (text.contains("Export Set")) {
							page.selectExportSetFromDropDown();
						} else {
							page.createNewExport(newExport);
						}
						page.addANewExport(productionname);
						page.getDATTab().waitAndClick(5);
						if(!page.getAdvancedToggle().isDisplayed()) {
							base.passedStep("Advanced option is removed  as expected");
						}else{
							base.failedStep("Advanced option is not removed");
						}
						
						loginPage.logout();
						
						}
						
						
						/**
						 * @author Brundha RPMXCON-49386
						 * @DescriptionVerify the Advanced section in Tiff /PDF component
						 * 
						 */
						@Test(description = "RPMXCON-49386", enabled = true, groups = { "regression" })
						public void verifyingAdvancedOptionInTiffSection() throws Exception {

							base = new BaseClass(driver);
							UtilityLog.info(Input.prodPath);
							base.stepInfo("RPMXCON-49386 -Export component");
							base.stepInfo("Verify the Advanced section in Tiff /PDF component.");

							String newExport = "Ex" + Utility.dynamicNameAppender();

							ProductionPage page = new ProductionPage(driver);
							String productionname = "p" + Utility.dynamicNameAppender();
							page.selectingDefaultSecurityGroup();
							String text = page.getProdExport_ProductionSets().getText();
							if (text.contains("Export Set")) {
								page.selectExportSetFromDropDown();
							} else {
								page.createNewExport(newExport);
							}
							page.addANewExport(productionname);
							page.verifyingAdvancedOptionInTiffSection();
							loginPage.logout();
						}

						/**
						 * @author Brundha RPMXCON-48187
						 * @Description To Verify On Export Basic info page User should be able to
						 *              select Custom Template (if available under custom template) and
						 *              move ahead with Export
						 * 
						 */
						@Test(description = "RPMXCON-48187", enabled = true, groups = { "regression" })
						public void verifyTemplateValueInProduction() throws Exception {

							base = new BaseClass(driver);
							UtilityLog.info(Input.prodPath);
							base.stepInfo("RPMXCON-48187 -Export component");
							base.stepInfo(
									"To Verify On Export Basic info page User should be able to select Custom Template (if available under custom template) and move ahead with Export");

							String foldername = "FolderProd" + Utility.dynamicNameAppender();
							String newExport = "Ex" + Utility.dynamicNameAppender();
							String prefixID = Input.randomText + Utility.dynamicNameAppender();
							String suffixID = Input.randomText + Utility.dynamicNameAppender();
							String TempName = "Temp" + Utility.dynamicNameAppender();

							TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
							tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);

							SessionSearch sessionSearch = new SessionSearch(driver);
							sessionSearch.basicContentSearch(Input.testData1);
							sessionSearch.bulkFolderExisting(foldername);

							ProductionPage page = new ProductionPage(driver);
							String productionname = "p" + Utility.dynamicNameAppender();
							String subBates = page.getRandomNumber(2);
							page.selectingDefaultSecurityGroup();
							String text = page.getProdExport_ProductionSets().getText();
							if (text.contains("Export Set")) {
								page.selectExportSetFromDropDown();
							} else {
								page.createNewExport(newExport);
							}
							page.addANewExport(productionname);
							page.fillingDATSection();
							page.navigateToNextSection();
							page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
							page.navigateToNextSection();
							page.fillingDocumentSelectionPage(foldername);
							page.navigateToNextSection();
							page.fillingPrivGuardPage();
							page.fillingExportLocationPage(productionname);
							page.navigateToNextSection();
							page.fillingSummaryAndPreview();
							page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();

							this.driver.getWebDriver().get(Input.url + "Production/Home");
							driver.Navigate().refresh();
							String productionname1 = "p" + Utility.dynamicNameAppender();
							page.selectExportSetFromDropDown();
							page.savedTemplateAndNewProdcution(productionname, TempName);

							driver.Navigate().refresh();
							driver.waitForPageToBeReady();
							page.baseInfoLoadTemplate(productionname1, TempName);
							page.getCheckBoxCheckedVerification(page.chkIsDATSelected());
							page.getCheckBoxCheckedVerification(page.getNativeCheckBox());
							driver.scrollPageToTop();
							page.getMarkCompleteLink().waitAndClick(10);
							base = new BaseClass(driver);
							base.VerifySuccessMessage("Mark Complete successful");
							loginPage.logout();
						}

						 /**
                         * @author Brundha RPMXCON-48067
                         * @Description To Verify selection of one or more tags without selecting any
                         *              file types for placeholdering a set of documents.(For Export).
                         * 
                         */
                        @Test(description = "RPMXCON-48067", enabled = true, groups = { "regression" }, priority = 11)
                        public void verifyingPlaceholderTextInGeneratedFile() throws Exception {

                            base = new BaseClass(driver);
                            UtilityLog.info(Input.prodPath);
                            base.stepInfo("RPMXCON-48067 -Export Component");
                            base.stepInfo(
                                    "To Verify selection of one or more tags without selecting any file types for placeholdering a set of documents.(For Export).");

                            String foldername = "FolderProd" + Utility.dynamicNameAppender();
                            String tagname1 = "Tag" + Utility.dynamicNameAppender();
                            String tagname2 = "Tag" + Utility.dynamicNameAppender();
                            String newExport = "Ex" + Utility.dynamicNameAppender();
                            String prefixID = "p" + Utility.dynamicNameAppender();
                            String suffixID = "S" + Utility.dynamicNameAppender();

                            TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
                            tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
                            tagsAndFolderPage.createNewTagwithClassification(tagname1, "Select Tag Classification");
                            tagsAndFolderPage.createNewTagwithClassification(tagname2, "Select Tag Classification");

                            SessionSearch sessionSearch = new SessionSearch(driver);
                            int purehit = sessionSearch.basicContentSearch(Input.testData1);
                            sessionSearch.bulkFolderExisting(foldername);
                            sessionSearch.bulkTagExisting(tagname1);

                            ProductionPage page = new ProductionPage(driver);
                            String productionname = "p" + Utility.dynamicNameAppender();
                            String subBates = page.getRandomNumber(2);
                            page.selectingDefaultSecurityGroup();
                            String text = page.getProdExport_ProductionSets().getText();
                            if (text.contains("Export Set")) {
                                page.selectExportSetFromDropDown();
                            } else {
                                page.createNewExport(newExport);
                            }
                            page.addANewExport(productionname);
                            page.fillingDATSection();
                            page.selectGenerateOption(false);
                            page.nativePlaceholderWithTwoTags(false, tagname1, tagname2);
                            page.navigateToNextSection();
                            page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
                            page.navigateToNextSection();
                            page.fillingDocumentSelectionPage(foldername);
                            page.navigateToNextSection();
                            page.fillingPrivGuardPage();
                            page.fillingExportLocationPage(productionname);
                            page.navigateToNextSection();
                            page.fillingSummaryAndPreview();
                            page.fillingGeneratePageWithContinueGenerationPopupWithoutCommit();
                            page.getCopyPath().waitAndClick(10);

                            String actualCopedText = page.getCopiedTextFromClipBoard();
                            String parentTab = page.openNewTab(actualCopedText);
                            page.goToImageFiles();
                            driver.waitForPageToBeReady();
                            for (int i = 2; i < purehit; i++) {
                                page.getFirstImageFile(prefixID + "(" + i + ")" + suffixID, subBates).waitAndClick(10);
                            }

                            driver.waitForPageToBeReady();
                            for (int i = 2; i < purehit; i++) {
                                File imageFile = new File(Input.fileDownloadLocation + prefixID +  "(" + i + ")"+ suffixID + ".000" + subBates + ".tiff");
                                page.OCR_Verification_In_Generated_Tiff_tess4j(imageFile, Input.searchString4);
                            }
                            driver.close();
                            driver.getWebDriver().switchTo().window(parentTab);

                            // delete tags and folders
                            tagsAndFolderPage = new TagsAndFoldersPage(driver);
                            tagsAndFolderPage.DeleteFolderWithSecurityGroup(foldername, Input.securityGroup);
                            tagsAndFolderPage.DeleteTagWithClassification(tagname1, Input.securityGroup);
                            loginPage.logout();

                        }
						/**
						 * @author Brundha RPMXCON-47977
						 * @Description To Verify Count displayed for "Documents with Multiple Branding
						 *              Tags" on Production-Export-Generate Page.
						 * 
						 */
						@Test(description = "RPMXCON-47977", enabled = true, groups = { "regression" }, priority = 16)
						public void verifyDocumentCountInMultipleBranding() throws Exception {

							base = new BaseClass(driver);
							UtilityLog.info(Input.prodPath);
							base.stepInfo("RPMXCON-47977 -Export component");
							base.stepInfo(
									"To Verify Count displayed for 'Documents with Multiple Branding Tags' on Production-Export-Generate Page.");

							String foldername = "FolderProd" + Utility.dynamicNameAppender();
							String tagname = "Tag" + Utility.dynamicNameAppender();
							String tagname1 = "Tag" + Utility.dynamicNameAppender();
							String newExport = "Ex" + Utility.dynamicNameAppender();
							String prefixID = Input.randomText + Utility.dynamicNameAppender();
							String suffixID = Input.randomText + Utility.dynamicNameAppender();

							TagsAndFoldersPage tagsAndFolderPage = new TagsAndFoldersPage(driver);
							tagsAndFolderPage.CreateFolder(foldername, Input.securityGroup);
							tagsAndFolderPage.createNewTagwithClassification(tagname, Input.tagNamePrev);
							tagsAndFolderPage.createNewTagwithClassification(tagname1, "Technical Issue");

							SessionSearch sessionSearch = new SessionSearch(driver);
							sessionSearch.basicContentSearchForTwoItems(Input.testData1, Input.telecaSearchString);
							sessionSearch.bulkFolderExisting(foldername);
							sessionSearch.ViewInDocList();

							DocListPage doc = new DocListPage(driver);
							doc.documentSelection(3);
							doc.bulkTagExistingFromDoclist(tagname);
							doc.documentSelection(6);
							doc.documentSelection(2);
							doc.bulkTagExistingFromDoclist(tagname1);

							ProductionPage page = new ProductionPage(driver);
							String productionname = "p" + Utility.dynamicNameAppender();
							String subBates = page.getRandomNumber(2);
							page.selectingDefaultSecurityGroup();
							String text = page.getProdExport_ProductionSets().getText();
							if (text.contains("Export Set")) {
								page.selectExportSetFromDropDown();
							} else {
								page.createNewExport(newExport);
							}
							page.addANewExport(productionname);
							page.fillingDATSection();
							page.selectGenerateOption(false);
							page.selectMultiBrandingTags(tagname, tagname1);
							page.navigateToNextSection();
							page.fillingExportNumberingAndSortingPage(prefixID, suffixID, subBates);
							page.navigateToNextSection();
							page.fillingDocumentSelectionPage(foldername);
							page.navigateToNextSection();
							page.fillingPrivGuardPage();
							page.fillingExportLocationPage(productionname);
							page.navigateToNextSection();
							page.fillingSummaryAndPreview();
							page.fillingGeneratePageWithContinueGenerationPopupWithoutWait();
							page.verifyingMultipleBrandingCount();
							loginPage.logout();
						}
	@AfterMethod(alwaysRun = true)
	public void takeScreenShot(ITestResult result) {
		if (ITestResult.FAILURE == result.getStatus()) {
			Utility bc = new Utility(driver);
			bc.screenShot(result);
			System.out.println("Executed :" + result.getMethod().getMethodName());

		}
		try {
			loginPage.quitBrowser();
		} catch (Exception e) {
			loginPage.quitBrowser();
		}
	}

	@AfterClass(alwaysRun = true)
	public void close() {
		try {
			// LoginPage.clearBrowserCache();

		} catch (Exception e) {
			System.out.println("Sessions already closed");
		}
	}

}
