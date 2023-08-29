package legalhold;

import automationLibrary.Driver;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import pageFactory.*;
import testScriptsSmoke.Input;

import java.io.IOException;
import java.text.ParseException;

import static io.restassured.RestAssured.given;

public class BaseRunner {
    protected Driver driver;


    public BaseRunner() throws ParseException, IOException, InterruptedException {
        Input in = new Input();
        in.loadEnvConfig();

        driver = new Driver();
    }

}
