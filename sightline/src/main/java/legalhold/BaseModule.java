package legalhold;

import automationLibrary.Driver;
import legalhold.utilities.parse_locators.LocatorReader;
import pageFactory.BaseClass;

import java.io.FileInputStream;
import java.io.IOException;

public class BaseModule {


    protected BaseClass bc;
    private LocatorReader locatorReader;


    public BaseModule(String selectorFilename, Driver driver) throws IOException {

        FileInputStream file = new FileInputStream(selectorFilename);
        locatorReader=new LocatorReader(selectorFilename);
        bc=new BaseClass(driver);
    }
}
