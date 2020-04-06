package com.vaadin.starter.business.it;

import com.vaadin.testbench.IPAddress;
import com.vaadin.testbench.ScreenshotOnFailureRule;
import com.vaadin.testbench.TestBenchTestCase;
import com.vaadin.testbench.parallel.ParallelTest;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public abstract class AbstractIT extends ParallelTest {

    public static String APP_URL= "http://localhost:8080";

    @Rule
    public ScreenshotOnFailureRule rule = new ScreenshotOnFailureRule(this,
            true);
/*
    @Before
    public void startBrowser() {
        setDriver(new ChromeDriver(new ChromeOptions().setHeadless(false)));
    }
*/
    protected void assertNumbers(String expected, String actual) {
        // Remove any thousands and decimal separators before comparing
        Assert.assertEquals(stripSeparators(expected), stripSeparators(actual));
    }

    private String stripSeparators(String string) {
        return string.replaceAll("[\\., ]", "").replace((char) 160, ' ');
    }

    @Override
    public void setup() throws Exception {
        super.setup();
        if (getRunLocallyBrowser() == null) {
            APP_URL = "http://" + IPAddress.findSiteLocalAddress() + ":8080/";
        }
    }
}
