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

    public String getobjectLocator(String locatorName)
    {
        String locatorProperty = propertyFile.getProperty(locatorName);
        String locatorType = locatorProperty.split(":")[0];
        String locatorValue = locatorProperty.split(":")[1];
        return locatorValue;
    }

    public String[] getArray(String allFields)
    {
        String locatorArray = propertyFile.getProperty(allFields);
        String[] fields = new String[17];
        for(int i=0;i<17;i++){
            fields[i]=locatorArray.split(",")[i];
        }
        for(int i=0;i<fields.length;i++){
            fields[i]=fields[i].split(":")[1];
        }
        return fields;
        }
}

