package legalhold.legalholdpagefactory.cases;

import automationLibrary.Driver;
import automationLibrary.Element;
import automationLibrary.ElementCollection;
import legalhold.setup.BaseModule;
import legalhold.utilities.parse_locators.LocatorReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import scala.xml.Elem;

import javax.swing.text.AbstractDocument;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class SurveyFactories extends BaseModule {

    LocatorReader reader;
    int index;
    int questionCount=0;
    ElementCollection addQuestionBtn;



    public SurveyFactories(Driver driver) throws IOException {

        super("./src/main/java/legalhold/selectors/cases/casepage.properties", driver);
        reader = new LocatorReader("src/main/java/legalhold/selectors/cases/manage_case/survey/survey.properties");
    }


    public void gotoAddNewSurveyPage() {
        Element addNewSurveyButton = driver.FindElementById(reader.getobjectLocator("addNewSurveyBtn"));
        addNewSurveyButton.waitAndClick(10);

        ElementCollection addQuestionBtn = driver.FindElementsByClassName(reader.getobjectLocator("addNewQuestionFromRootBtn"));
        index = addQuestionBtn.size()-1;



    }

    public void verifyPageHeader(String expectedHeader) {
        Element pageHeader = driver.FindElementByXPath(reader.getobjectLocator("pageHeader"));
        wait.until(ExpectedConditions.elementToBeClickable(pageHeader.getWebElement()));
        Assert.assertEquals(pageHeader.getText(), expectedHeader);
    }

    public String fillSurveyName(){
        Element surveyNameTextField = driver.FindElementById(reader.getobjectLocator("surveyName"));
        wait.until(ExpectedConditions.elementToBeClickable(surveyNameTextField.getWebElement()));
        String surveyName = faker.pokemon().name();
        surveyNameTextField.SendKeys(surveyName);
        return surveyName;
    }
    public void fillSurveyTitle(){
        Element surveyTitleTextField = driver.FindElementById(reader.getobjectLocator("surveyTitle"));
        wait.until(ExpectedConditions.elementToBeClickable(surveyTitleTextField.getWebElement()));
        String surveyName = faker.lorem().sentence();
        surveyTitleTextField.SendKeys(surveyName);

    }
    public void saveSurvey() throws InterruptedException {
        Element globalSaveBtn = driver.FindElementByXPath(reader.getobjectLocator("globalSaveSurvey"));
        driver.scrollingToBottomofAPage();
        wait.until(ExpectedConditions.elementToBeClickable(globalSaveBtn.getWebElement()));
        Thread.sleep(2000);
        globalSaveBtn.waitAndClick(20);

        Element globalSaveConfirmationBtn = driver.FindElementById(reader.getobjectLocator("globalSaveConfirmation"));
        wait.until(ExpectedConditions.elementToBeClickable(globalSaveConfirmationBtn.getWebElement()));
        globalSaveConfirmationBtn.Click();
    }
    public void searchSurveyByName(String surveyName) {
        try {
            WebElement surveyNameColumnFilterBox = driver.getWebDriver().findElement(By.xpath(reader.getobjectLocator("surveyNameFilterBox")));
            wait.until(ExpectedConditions.elementToBeClickable(surveyNameColumnFilterBox));
            surveyNameColumnFilterBox.clear();
            surveyNameColumnFilterBox.sendKeys(surveyName);
            String expected_text = "Showing 1 to 1 of 1 entries";
//            Thread.sleep(2000);
            wait.until(ExpectedConditions.textToBe(By.id(locatorReader.getobjectLocator("rowCount")), expected_text));
        } catch (Exception E) {
            System.out.println("Survey Name not found. The exception is: ");
            System.out.println(E.getMessage());
        }
    }

    public String editSurvey(String surveyToEdit){
           searchSurveyByName(surveyToEdit);
           Element editSurveyIcon = driver.FindElementByXPath(reader.getobjectLocator("editSurveyIcon"));
           wait.until(ExpectedConditions.elementToBeClickable(editSurveyIcon.getWebElement()));
           editSurveyIcon.Click();

           //Edit Name

        Element surveyNameTextField = driver.FindElementById(reader.getobjectLocator("surveyNameTextBox"));
        wait.until(ExpectedConditions.elementToBeClickable(surveyNameTextField.getWebElement()));
        String surveyName = faker.ancient().hero();
        surveyNameTextField.Clear();
        surveyNameTextField.SendKeys(surveyName);



        addNewQuestion();
        driver.scrollingToBottomofAPage();
        selectQuestionType("Date");
        createDateQuestion("YYYY/MM/DD");
        return surveyName;




    }

    public void addNewQuestion() {

       addQuestionBtn = driver.FindElementsByClassName(reader.getobjectLocator("addNewQuestionFromRootBtn"));
        wait.until(ExpectedConditions.elementToBeClickable(addQuestionBtn.getElementByIndex(index).getWebElement()));

        addQuestionBtn.getElementByIndex(index).waitAndClick(30);
        driver.scrollingToBottomofAPage();


//        System.out.println("index: " +index);

        ElementCollection questionSectionHeader = driver.FindElementsByXPath(reader.getobjectLocator("questionSectionHeader"));
        wait.until(ExpectedConditions.elementToBeClickable(questionSectionHeader.getElementByIndex(0).getWebElement()));
        Assert.assertTrue(questionSectionHeader.getElementByIndex(0).getText().contains("Question Type"));
        index = addQuestionBtn.size()-1;


    }
    public void selectQuestionType(String questionType) {

        if(questionType!="Section Title (Not a Question)")
        {
            ++questionCount;
        }
        ElementCollection questionTypeDropdown = driver.FindElementsByXPath(reader.getobjectLocator("questionTypeDropdown"));
        wait.until(ExpectedConditions.elementToBeClickable(questionTypeDropdown.getElementByIndex(questionTypeDropdown.size() - 1).getWebElement()));
        questionTypeDropdown.getElementByIndex(questionTypeDropdown.size() - 1).selectFromDropdown().selectByVisibleText(questionType);
    }

    public void verifyTotalNumberOfQuestions(){
        Element totalNumberOfQuestionsColumn = driver.FindElementByXPath(reader.getobjectLocator("totalNumberOfQuestionsColumn"));
        wait.until(ExpectedConditions.elementToBeClickable(totalNumberOfQuestionsColumn.getWebElement()));
        String actualValue = totalNumberOfQuestionsColumn.getText();
        System.out.println("question Number"+questionCount);
        System.out.println("actual"+actualValue);
        Assert.assertEquals(Integer.parseInt(actualValue),questionCount);
    }

    public void createFreeTextFormQuestion() {

        String questionValue = "Would you like to visit  " + faker.country().name() + " ?";
        ElementCollection questionTextBox = driver.FindElementsByXPath(reader.getobjectLocator("questionTextBox"));
        wait.until(ExpectedConditions.elementToBeClickable(questionTextBox.getElementByIndex(questionTextBox.size() - 1).getWebElement()));
        questionTextBox.getElementByIndex(questionTextBox.size() - 1).SendKeys(questionValue);
    }
    public void createDateQuestion(String dateFormat) {

        String questionValue = "What is you Date of Birth?";
        ElementCollection questionTextBox = driver.FindElementsByXPath(reader.getobjectLocator("questionTextBox"));
        wait.until(ExpectedConditions.elementToBeClickable(questionTextBox.getElementByIndex(questionTextBox.size() - 1).getWebElement()));
        questionTextBox.getElementByIndex(questionTextBox.size() - 1).SendKeys(questionValue);

        ElementCollection dateFormatDropdown = driver.FindElementsByXPath(reader.getobjectLocator("dateFormatDropdown"));
        wait.until(ExpectedConditions.elementToBeClickable(dateFormatDropdown.getElementByIndex(dateFormatDropdown.size() - 1).getWebElement()));
        dateFormatDropdown.getElementByIndex(dateFormatDropdown.size() - 1).selectFromDropdown().selectByVisibleText(dateFormat);
        index = addQuestionBtn.size()-1;
    }

    public void createYesOrNoQuestionWithoutConditional() {

        String questionValue = "Do you like " + faker.team().sport() + "?";
        ElementCollection questionTextBox = driver.FindElementsByXPath(reader.getobjectLocator("questionTextBox"));
        wait.until(ExpectedConditions.elementToBeClickable(questionTextBox.getElementByIndex(questionTextBox.size() - 1).getWebElement()));
        questionTextBox.getElementByIndex(questionTextBox.size() - 1).SendKeys(questionValue);
        index = addQuestionBtn.size();
        System.out.println("index: " +(addQuestionBtn.size()-1));
    }



    public void fillConditionalFields(String questionType) {
        if (questionType == "Free Text Form") {
            createFreeTextFormQuestion();
        } else if (questionType == "Yes or No") {
            createYesOrNoQuestionWithoutConditional();
        } else if (questionType == "One Choice (Radio buttons)") {
            createOneChoiceQuestion(1);
        }
    }

    public void createYesOrNoQuestionWithConditional(String... questionType) {

        String questionValue = "Do you like " + faker.team().sport() + "?";
        ElementCollection questionTextBox = driver.FindElementsByXPath(reader.getobjectLocator("questionTextBox"));
        wait.until(ExpectedConditions.elementToBeClickable(questionTextBox.getElementByIndex(questionTextBox.size() - 1).getWebElement()));
        questionTextBox.getElementByIndex(questionTextBox.size() - 1).SendKeys(questionValue);

        ElementCollection actionMenu = driver.FindElementsByXPath(reader.getobjectLocator("actonMenuBtn"));
        index = addQuestionBtn.size()-1;

        for (int i = 0; i < 2; i++) {
            wait.until(ExpectedConditions.elementToBeClickable(questionTextBox.getElementByIndex(i).getWebElement()));
            driver.scrollingToBottomofAPage();
            actionMenu.getElementByIndex(i).waitAndClick(30);

            driver.FindElementsByClassName("addNewSubQuestionBtn").getElementByIndex(i).waitAndClick(30);
            selectQuestionType(questionType[i]);
            fillConditionalFields(questionType[i]);


        }


    }

    public void createOneChoiceQuestion(int questionNum) {

        String questionValue = "What's your occupation? ?";
        ElementCollection questionTextBox = driver.FindElementsByXPath(reader.getobjectLocator("questionTextBox"));
        wait.until(ExpectedConditions.elementToBeClickable(questionTextBox.getElementByIndex(questionTextBox.size() - 1).getWebElement()));
        questionTextBox.getElementByIndex(questionTextBox.size() - 1).SendKeys(questionValue);



        for (int i = 0; i < questionNum; i++) {
            ElementCollection actionMenu = driver.FindElementsByXPath(reader.getobjectLocator("actonMenuBtn"));
            wait.until(ExpectedConditions.elementToBeClickable(actionMenu.getElementByIndex(actionMenu.size() - 1).getWebElement()));
            driver.scrollingToBottomofAPage();
            actionMenu.getElementByIndex(actionMenu.size() - 1).Click();
            driver.FindElementByXPath(reader.getobjectLocator("addNewOption")).waitAndClick(30);
        }


        for (int i = (2 + questionNum); i > 0; i--) {
            Element optionBoxOne = driver.FindElementsByXPath(reader.getobjectLocator("optionAnswerTextBox")).getElementByIndex(driver.FindElementsByXPath(reader.getobjectLocator("optionAnswerTextBox")).size() - i);
            wait.until(ExpectedConditions.elementToBeClickable(optionBoxOne.getWebElement()));
            optionBoxOne.SendKeys(faker.job().title());
        }

        ElementCollection actionMenu = driver.FindElementsByXPath(reader.getobjectLocator("actonMenuBtn"));
        wait.until(ExpectedConditions.elementToBeClickable(actionMenu.getElementByIndex(actionMenu.size() - 1).getWebElement()));
        driver.scrollingToBottomofAPage();
        actionMenu.getElementByIndex(actionMenu.size() - 1).Click();
        driver.FindElementByXPath(reader.getobjectLocator("addOtherOption")).waitAndClick(30);
        index=0;
        driver.scrollPageToTop();


    }

    public void makeQuestionRequired() {

        ElementCollection requiredCheckbox = driver.FindElementsByXPath(reader.getobjectLocator("requiredCheckBox"));
        wait.until(ExpectedConditions.elementToBeClickable(requiredCheckbox.getElementByIndex(requiredCheckbox.size() - 1).getWebElement()));
        requiredCheckbox.getElementByIndex(requiredCheckbox.size() - 1).Click();

    }

    public void createMultipleChoiceQuestion(int questionNum) {

        String questionValue = "What's your dream destination?";
        ElementCollection questionTextBox = driver.FindElementsByXPath(reader.getobjectLocator("questionTextBox"));
        wait.until(ExpectedConditions.elementToBeClickable(questionTextBox.getElementByIndex(questionTextBox.size() - 1).getWebElement()));
        questionTextBox.getElementByIndex(questionTextBox.size() - 1).SendKeys(questionValue);



        for (int i = 0; i < questionNum; i++) {
            ElementCollection actionMenu = driver.FindElementsByXPath(reader.getobjectLocator("actonMenuBtn"));
            wait.until(ExpectedConditions.elementToBeClickable(actionMenu.getElementByIndex(actionMenu.size() - 1).getWebElement()));
            driver.scrollingToBottomofAPage();
            actionMenu.getElementByIndex(actionMenu.size() - 1).Click();
            driver.FindElementByXPath(reader.getobjectLocator("addNewOption")).waitAndClick(30);
        }


        for (int i = (2 + questionNum); i > 0; i--) {
            Element optionBoxOne = driver.FindElementsByXPath("(//input[@name='answer'])").getElementByIndex(driver.FindElementsByXPath("(//input[@name='answer'])").size() - i);
            wait.until(ExpectedConditions.elementToBeClickable(optionBoxOne.getWebElement()));
            optionBoxOne.SendKeys(faker.country().name());
        }

        ElementCollection actionMenu = driver.FindElementsByXPath(reader.getobjectLocator("actonMenuBtn"));
        wait.until(ExpectedConditions.elementToBeClickable(actionMenu.getElementByIndex(actionMenu.size() - 1).getWebElement()));
        driver.scrollingToBottomofAPage();
        actionMenu.getElementByIndex(actionMenu.size() - 1).Click();
        driver.FindElementByXPath("//div[@class='dropdown-menu dropdown-menu-end show']//a[@aria-label='Add Other Input Field']").waitAndClick(30);
        index=0;
        driver.scrollPageToTop();


    }


}
