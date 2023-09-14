package legalhold.utilities.parse_locators;

import org.openqa.selenium.By;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class LocatorReader {
    private FileInputStream stream;
    private String RepositoryFile;
    private Properties propertyFile = new Properties();

    public LocatorReader(String fileName) throws IOException {
        this.RepositoryFile = fileName;
        stream = new FileInputStream(RepositoryFile);
        propertyFile.load(stream);
    }

    public String getobjectLocator(String locatorName) {
        String locatorProperty = propertyFile.getProperty(locatorName);
        String locatorType = locatorProperty.split(":")[0].trim();
        String locatorValue = locatorProperty.split(":")[1].trim();

        return locatorValue;
    }

    public String getFileName(String locatorName) {
        String filePath = propertyFile.getProperty(locatorName);
        return filePath;
    }

    public String[] getArray(String allFields) {
        String locatorArray = propertyFile.getProperty(allFields);
        locatorArray = locatorArray.trim();
        String[] splitArray = locatorArray.split(",");

        int arraySize = splitArray.length;

        String[] fields = new String[arraySize];
        for (int i = 0; i < arraySize; i++) {
            fields[i] = locatorArray.split(",")[i].trim();
        }
        for (int i = 0; i < fields.length; i++) {
            fields[i] = fields[i].split(":")[1].trim();
        }
        return fields;
    }
}

