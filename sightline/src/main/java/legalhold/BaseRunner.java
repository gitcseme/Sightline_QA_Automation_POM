package legalhold;

import automationLibrary.Driver;

import pageFactory.*;
import testScriptsSmoke.Input;

import java.io.IOException;
import java.text.ParseException;



public class BaseRunner {
    protected Driver driver;


    public BaseRunner() throws ParseException, IOException, InterruptedException {
        Input in = new Input();
        in.loadEnvConfig();

        driver = new Driver();
    }

}
