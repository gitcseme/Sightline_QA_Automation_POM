package legalhold;

import automationLibrary.Driver;
import executionMaintenance.UtilityLog;
import legalhold.utilities.parse_locators.LocatorReader;
import pageFactory.BaseClass;
import pageFactory.DomainDashboard;

import java.io.FileInputStream;
import java.io.IOException;

public class BaseModule {


    protected BaseClass bc;
    private LocatorReader locatorReader;
    protected Driver driver;
    protected DomainDashboard domainDashboard;


    public BaseModule(String selectorFilename, Driver driver) throws IOException {
        this.driver = driver;

        FileInputStream file = new FileInputStream(selectorFilename);
        locatorReader=new LocatorReader(selectorFilename);
        bc=new BaseClass(driver);
        domainDashboard=new DomainDashboard(driver);
    }
}
