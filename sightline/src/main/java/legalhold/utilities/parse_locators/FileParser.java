package legalhold.utilities.parse_locators;

import org.openqa.selenium.By;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class FileParser {
    private FileInputStream stream;
    private String RepositoryFile;
    private Properties propertyFile = new Properties();

    public FileParser(String fileName) throws IOException {
        this.RepositoryFile = fileName;
        stream = new FileInputStream(RepositoryFile);
        propertyFile.load(stream);
    }

    public By getbjectLocator(String locatorName)
    {
        String locatorProperty = propertyFile.getProperty(locatorName);
//        System.out.println(locatorProperty.toString());
        String locatorType = locatorProperty.split(":")[0];
        String locatorValue = locatorProperty.split(":")[1];

        By locator = null;
        switch(locatorType)
        {
            case "id":
                locator = By.id(locatorValue);
                break;
            case "name":
                locator = By.name(locatorValue);
                break;
            case "cssSelector":
                locator = By.cssSelector(locatorValue);
                break;
            case "linkText":
                locator = By.linkText(locatorValue);
                break;
            case "partialLinkText":
                locator = By.partialLinkText(locatorValue);
                break;
            case "tagName":
                locator = By.tagName(locatorValue);
                break;
            case "xpath":
                locator = By.xpath(locatorValue);
                break;
            case "class":
                locator = By.className(locatorValue);
                break;
        }
        return locator;
    }
}
