package legalhold.sprintwork.sprint_41.testrunner;

import legalhold.legalholdpagefactory.LHMenus;
import legalhold.legalholdpagefactory.cases.CaseFactories;
import legalhold.legalholdpagefactory.cases.ManageAttachmentFactories;
import legalhold.legalholdpagefactory.domain_setup.DomainSetupTabNavigation;
import legalhold.legalholdpagefactory.domain_setup.DomainSetupTabs;
import legalhold.legalholdpagefactory.domain_setup.data_source.DataSourceFactories;
import legalhold.legalholdpagefactory.global_notice.GlobalNoticeFactories;
import legalhold.setup.BaseRunner;
import legalhold.smoke_suite.cases.create_case.CreateCase;
import org.testng.annotations.Test;

import java.io.IOException;
import java.text.ParseException;

public class Sprint41TestRunner extends BaseRunner {
    protected DataSourceFactories dataSourceFactories;
    protected DomainSetupTabNavigation domainSetupTabNavigation;
    protected CaseFactories caseFactories;
    protected CreateCase createCase;
    protected GlobalNoticeFactories globalNoticeFactories;
    protected ManageAttachmentFactories manageAttachmentFactories;
    public static String dataSourceName;
    public static String createdCase;
    public static String createdGlobalNotice;

    public Sprint41TestRunner() throws ParseException, IOException, InterruptedException {
        dataSourceFactories = new DataSourceFactories(driver);
        domainSetupTabNavigation = new DomainSetupTabNavigation(driver);
        caseFactories = new CaseFactories(driver);
        createCase = new CreateCase(driver);
        globalNoticeFactories = new GlobalNoticeFactories(driver);
        manageAttachmentFactories = new ManageAttachmentFactories(driver);
    }

    @Test(priority = 1, enabled = true)
    public void navigateToDataSourceTab() throws IOException {
        getNavigation().navigateToMenu(LHMenus.DomainSetup);
        domainSetupTabNavigation.navigateToDomainSetupTab(DomainSetupTabs.DataSources);
    }

    @Test(priority = 2, enabled = true)
    public void createDataSource() throws InterruptedException {
        dataSourceFactories.goToCreateDataSourcePage();
        dataSourceName = dataSourceFactories.enterDataSourceNameAndDescription();
        dataSourceFactories.enterDataSourceUrl("https://login.microsoftonline.com");
        dataSourceFactories.openConfigModal();
        dataSourceFactories.enterTenantID("9440f0a4-3c93-44be-a379-740f10731bf6");
        dataSourceFactories.enterApplicationID("89abeaac-8113-463b-8d08-2f66b4210111");
        dataSourceFactories.enterUserName("madmin@consiliodeveloper.onmicrosoft.com");
        dataSourceFactories.enterPassword("SLHT3st@");
        dataSourceFactories.enterClientSecret("PHY8Q~XAwgFmKffKFe6Ulg8Z~-6qhwGlLvaAtbDP");
        dataSourceFactories.saveConfigModal();
        dataSourceFactories.verifySuccessfulTestConnection();
        dataSourceFactories.saveDataSource();
        var status = dataSourceFactories.getStatusOfDataSourceFromTable(dataSourceName);
        if (status.equalsIgnoreCase("Disabled")) {
            throw new RuntimeException("Data Source is Disabled");
        }
    }

    @Test(priority = 3, enabled = true)
    public void editDataSource() throws InterruptedException {
        dataSourceFactories.goToEditDataSource(dataSourceName);
        dataSourceName = dataSourceFactories.enterDataSourceNameAndDescription();
        dataSourceFactories.enterDataSourceUrl("https://login.microsoftonline.com");
        dataSourceFactories.openConfigModal();
        dataSourceFactories.enterTenantID("9440f0a4-3c93-44be-a379-740f10731bf6");
        dataSourceFactories.enterApplicationID("2007e8b1-efdf-4922-8c1d-c3a26c106714");
        dataSourceFactories.enterUserName("madmin@consiliodeveloper.onmicrosoft.com");
        dataSourceFactories.enterPassword("SLHT3st@");
        dataSourceFactories.enterClientSecret("Jzk8Q~u58Dgj1Nq31CyLe028coeI4vZguLkLFas4");
        dataSourceFactories.saveConfigModal();
        dataSourceFactories.verifySuccessfulTestConnection();
        dataSourceFactories.saveDataSource();
        var status = dataSourceFactories.getStatusOfDataSourceFromTable(dataSourceName);
        if (status.equalsIgnoreCase("Disabled")) {
            throw new RuntimeException("Data Source is Disabled");
        }
    }

    @Test(priority = 4, enabled = true)
    public void closeCase() throws InterruptedException, IOException {
        getNavigation().navigateToMenu(LHMenus.Cases);
        createdCase = createCase.createRandomCases();
        getNavigation().navigateToMenu(LHMenus.Cases);
        caseFactories.closeCase(createdCase);
        caseFactories.verifyCaseAfterClosing(createdCase);
        caseFactories.reopenCase(createdCase);
        caseFactories.verifyCaseAfterReopening(createdCase);
        caseFactories.deleteCase(createdCase);
    }

    @Test(priority = 5, enabled = true)
    public void createGlobalNotice() throws IOException, InterruptedException {
        getNavigation().navigateToMenu(LHMenus.GlobalNotice);
        globalNoticeFactories.goToCreateGlobalNoticePage();
        createdGlobalNotice = globalNoticeFactories.enterGlobalNoticeNameAndDescription();
        globalNoticeFactories.addMailToRecipients("auto-1");
        globalNoticeFactories.setGlobalNoticeAsWeekly();
        globalNoticeFactories.enterGlobalNoticeEmailSubject("Automated Global Notice > Notice for [FIRST NAME] [LAST NAME] ([EMAIL ADDRESS])");
        globalNoticeFactories.goToManageAttachmentPage();
        manageAttachmentFactories.uploadValidAttachmentWithoutCheckbox("validAttachment");
        manageAttachmentFactories.deleteAttachmentByUploadKey("validAttachment");
        manageAttachmentFactories.addAttachmentToCommunicationByName("attachment_for_communication.png");
        globalNoticeFactories.typeEmailBody("[CUSTODIAN PORTAL LINK]\n" +
                "Case list by custodian: [CASE LIST BY CUSTODIAN]");
        globalNoticeFactories.saveGlobalNotice();
    }

    @Test(priority = 6, enabled = true)
    public void editAndStartGlobalNotice() throws IOException, InterruptedException {
        globalNoticeFactories.goToEditGlobalNoticePage(createdGlobalNotice);
        globalNoticeFactories.addMailToRecipients("auto-21");
        globalNoticeFactories.addSummaryCommunicationRecipients("corr-01");
        globalNoticeFactories.setGlobalNoticeAsOneTime();
        globalNoticeFactories.startGlobalNotice();
    }
}
