package legalhold.legalholdpagefactory.cases;

import automationLibrary.Driver;
import legalhold.setup.BaseModule;
import legalhold.utilities.parse_locators.LocatorReader;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageAttachmentFactories extends BaseModule {
    protected LocatorReader attachmentReader;

    public ManageAttachmentFactories(Driver driver) throws IOException {
        super("src/main/java/legalhold/selectors/cases/manage_case/communication/manage_attachment.properties", driver);
        attachmentReader = new LocatorReader("src/main/java/legalhold/selectors/uploadFile.properties");
    }


    /*
    This method is applicable for all type of communication series (except global notice) page and email template pages.
    To navigate to Global Notice > Manage Attachment page, you'll require another method written in
    "GlobalNoticeFactories.java" class with the same name as this method.
    */
    public void goToManageAttachmentPage() throws InterruptedException {
        driver.scrollingToBottomofAPage();
        var btnManageAttachment = wait.until(ExpectedConditions.elementToBeClickable(driver.FindElementById(locatorReader.getobjectLocator("btnManageAttachment")).getWebElement()));
        btnManageAttachment.click();
        Thread.sleep(2000);
    }

    public int getAvailableAttachmentTableCount() {
        String paginationTextAvailableAttachmentTable = driver.FindElementById(locatorReader.getobjectLocator("paginationTextAvailableAttachmentTable")).getText();
        Pattern pattern = Pattern.compile("of (\\d+)");
        Matcher matcher = pattern.matcher(paginationTextAvailableAttachmentTable);

        int count = 0;
        while (matcher.find()) {
            count = Integer.parseInt(matcher.group(1));
        }
        return count;
    }

    public void searchAttachmentInAvailableAttachmentTableByUploadPath(String attachmentKey) throws InterruptedException {
        var attachmentPath = attachmentReader.getFileName(attachmentKey).split("\\\\");
        var attachmentName = attachmentPath[attachmentPath.length - 1].trim();

        driver.FindElementById(locatorReader.getobjectLocator("btnClearFilterAvailableAttachmentTable")).waitAndClick(30);
        var searchAttachmentNameAvailableTable = wait.until(ExpectedConditions.elementToBeClickable(driver.FindElementByCssSelector(locatorReader.getobjectLocator("searchAttachmentNameAvailableTable")).getWebElement()));
        searchAttachmentNameAvailableTable.sendKeys(attachmentName);
        Thread.sleep(1500);
        var count = getAvailableAttachmentTableCount();
        if (count == 0) {
            throw new RuntimeException("No Attachment found");
        } else if (count == 1) {
            System.out.println("Attachment found. Name of the attachment is: " + attachmentName);
        } else {
            throw new RuntimeException("Duplicate Attachment found");
        }
    }

    public void searchNonExistingAttachmentInAvailableAttachmentTableByUploadPath(String attachmentKey) throws InterruptedException {
        var attachmentPath = attachmentReader.getFileName(attachmentKey).split("\\\\");
        var attachmentName = attachmentPath[attachmentPath.length - 1].trim();

        driver.FindElementById(locatorReader.getobjectLocator("btnClearFilterAvailableAttachmentTable")).waitAndClick(30);
        var searchAttachmentNameAvailableTable = wait.until(ExpectedConditions.elementToBeClickable(driver.FindElementByCssSelector(locatorReader.getobjectLocator("searchAttachmentNameAvailableTable")).getWebElement()));
        searchAttachmentNameAvailableTable.sendKeys(attachmentName);
        Thread.sleep(1500);
        var count = getAvailableAttachmentTableCount();
        if (count != 0) {
            throw new RuntimeException("Attachment Still Exists. Attachment Name is: " + attachmentName);
        }
    }

    public void searchAttachmentInAvailableAttachmentTableByName(String attachmentName) throws InterruptedException {
        driver.FindElementById(locatorReader.getobjectLocator("btnClearFilterAvailableAttachmentTable")).waitAndClick(30);
        var searchAttachmentNameAvailableTable = wait.until(ExpectedConditions.elementToBeClickable(driver.FindElementByCssSelector(locatorReader.getobjectLocator("searchAttachmentNameAvailableTable")).getWebElement()));
        searchAttachmentNameAvailableTable.sendKeys(attachmentName);
        Thread.sleep(1500);
        var count = getAvailableAttachmentTableCount();
        if (count == 0) {
            throw new RuntimeException("No Attachment found");
        } else if (count == 1) {
            System.out.println("Attachment found. Name of the attachment is: " + attachmentName);
        } else {
            throw new RuntimeException("Duplicate Attachment found");
        }
    }

    public void searchNonExistingAttachmentInAvailableAttachmentTableByName(String attachmentName) throws InterruptedException {
        driver.FindElementById(locatorReader.getobjectLocator("btnClearFilterAvailableAttachmentTable")).waitAndClick(30);
        var searchAttachmentNameAvailableTable = wait.until(ExpectedConditions.elementToBeClickable(driver.FindElementByCssSelector(locatorReader.getobjectLocator("searchAttachmentNameAvailableTable")).getWebElement()));
        searchAttachmentNameAvailableTable.sendKeys(attachmentName);
        Thread.sleep(1500);
        var count = getAvailableAttachmentTableCount();
        if (count != 0) {
            throw new RuntimeException("Attachment Still Exists. Attachment Name is: " + attachmentName);
        }
    }

    public void uploadValidAttachmentWithoutCheckbox(String validAttachmentKey) throws InterruptedException {
        var btnUploadAttachment = wait.until(ExpectedConditions.elementToBeClickable(driver.FindElementById(locatorReader.getobjectLocator("btnUploadAttachment")).getWebElement()));
        btnUploadAttachment.click();
        var btnChooseFile = driver.FindElementById(locatorReader.getobjectLocator("btnChooseFile"));
        btnChooseFile.getWebElement().sendKeys(attachmentReader.getFileName(validAttachmentKey));
        driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnSubmitUploadModal")).waitAndClick(30);
        wait.until(ExpectedConditions.visibilityOf(driver.FindElementByXPath(locatorReader.getobjectLocator("inProgressText")).getWebElement()));
        var inProgressText = driver.FindElementByXPath(locatorReader.getobjectLocator("inProgressText")).getText();
        Assert.assertTrue(inProgressText.contains("Uploading and Virus Scanning are in progress"));
        var successTextOnModal = wait.until(ExpectedConditions.visibilityOf(driver.FindElementByCssSelector(locatorReader.getobjectLocator("successText")).getWebElement()));
        var successText = successTextOnModal.getText();
        Assert.assertTrue(successText.contains("The file has been uploaded successfully"));

        driver.FindElementById(locatorReader.getobjectLocator("checkboxSuccessModal")).waitAndClick(30);
        driver.FindElementById(locatorReader.getobjectLocator("okBtnSuccessModal")).waitAndClick(30);
        searchAttachmentInAvailableAttachmentTableByUploadPath(validAttachmentKey);
        System.out.println("Attachment has been uploaded successfully");
    }

    public int getColumnIndexFromAvailableAttachmentDataTable(String headerName) {
        var table = driver.FindElementById("id-AvailableEmailAttachments");
        wait.until(ExpectedConditions.elementToBeClickable(table.getWebElement()));
        int columnIndex = 0;

        var headerList = driver.FindElementsByXPath("//table[@id='id-AvailableEmailAttachments']//thead/tr[1]/th");
        for (int i = 0; i < headerList.size(); i++) {
            if (headerList.getElementByIndex(i).getText().equalsIgnoreCase(headerName)) {
                columnIndex = i + 1;
                break;
            }
        }

        if (columnIndex == 0) {
            driver.FindElementById(locatorReader.getobjectLocator("btnColumnSetupAvailableAttachment")).waitAndClick(30);
            var availableColumnList = driver.FindElementsByXPath("//tbody[@id='id-tablebody-available-AvailableEmailAttachments']/tr/td[2]");
            for (int i = 0; i < availableColumnList.size(); i++) {

                if (availableColumnList.getElementByIndex(i).getText().equalsIgnoreCase(headerName)) {
                    driver.FindElementByXPath("//tbody[@id='id-tablebody-available-AvailableEmailAttachments']/tr[" + (i + 1) + "]/td[1]/input").waitAndClick(30);
                    driver.FindElementByXPath(locatorReader.getobjectLocator("addBtnColumnSetup")).waitAndClick(30);
                    driver.FindElementByXPath(locatorReader.getobjectLocator("saveBtnColumnSetup")).waitAndClick(30);
                    break;
                }
            }
            columnIndex = headerList.size();
        }
        return columnIndex;
    }

    public void deleteAttachmentByUploadKey(String attachmentKey) throws InterruptedException {
        searchAttachmentInAvailableAttachmentTableByUploadPath(attachmentKey);
        var actionColumn = getColumnIndexFromAvailableAttachmentDataTable("Action");
        driver.FindElementByXPath("//table[@id='id-AvailableEmailAttachments']/tbody/tr[1]/td[" + actionColumn + "]//a[2]/img[1]").waitAndClick(30);
        driver.FindElementById(locatorReader.getobjectLocator("okBtnDeleteAttachmentModal")).waitAndClick(30);
        searchNonExistingAttachmentInAvailableAttachmentTableByUploadPath(attachmentKey);
    }

    public void addAttachmentToCommunicationByName(String attachmentName) throws InterruptedException {
        driver.FindElementById(locatorReader.getobjectLocator("btnClearFilterAvailableAttachmentTable")).waitAndClick(30);
        var searchAttachmentNameAvailableTable = wait.until(ExpectedConditions.elementToBeClickable(driver.FindElementByCssSelector(locatorReader.getobjectLocator("searchAttachmentNameAvailableTable")).getWebElement()));
        searchAttachmentNameAvailableTable.sendKeys(attachmentName);
        Thread.sleep(2000);

        driver.FindElementByName(locatorReader.getobjectLocator("btnSelectAllAvailableAttachmentTable")).waitAndClick(30);
        driver.FindElementByCssSelector(locatorReader.getobjectLocator("btnAddAttachment")).waitAndClick(30);

        driver.FindElementById(locatorReader.getobjectLocator("okBtnAddAttachmentModal")).waitAndClick(30);
        Thread.sleep(2000);
        driver.scrollingToBottomofAPage();
        var saveManageAttachment = wait.until(ExpectedConditions.elementToBeClickable(driver.FindElementByCssSelector(locatorReader.getobjectLocator("saveManageAttachment")).getWebElement()));
        saveManageAttachment.click();
        wait.until(ExpectedConditions.elementToBeClickable(driver.FindElementById(locatorReader.getobjectLocator("okBtnSaveManageAttachmentModal")).getWebElement())).click();
        Thread.sleep(3000);
        driver.scrollingToBottomofAPage();
        driver.waitForPageToBeReady();

        var addedAttachmentListToMatchTheAddedAttachment = (String) jsExecutor.executeScript("return $('#" + locatorReader.getobjectLocator("addedAttachmentList") + "').val()");
        System.out.println("The list of added attachments are: " + addedAttachmentListToMatchTheAddedAttachment);
        Assert.assertTrue(addedAttachmentListToMatchTheAddedAttachment.contains(attachmentName));
    }

}
