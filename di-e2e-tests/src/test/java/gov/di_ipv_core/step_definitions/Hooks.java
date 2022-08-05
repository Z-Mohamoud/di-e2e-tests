package gov.di_ipv_core.step_definitions;

import com.google.common.collect.ImmutableMap;
import gov.di_ipv_core.utilities.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import static com.github.automatedowl.tools.AllureEnvironmentWriter.allureEnvironmentWriter;


public class Hooks {

    public static String  startTime;
    public static String  endTime;



    @Before
    public void setUp() {
        Capabilities capabilities = ((RemoteWebDriver) Driver.get()).getCapabilities();
        allureEnvironmentWriter(
                ImmutableMap.<String, String>builder()
                        .put("Browser", capabilities.getBrowserName())
                        .put("Browser Version", capabilities.getBrowserVersion())
//                        .put("Environment", System.getProperty("env"))
                        .build());

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        startTime = dtf.format(LocalDateTime.now());
    }

    @After
    public void quitDriver() {
        Driver.closeDriver();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss.SSS");
        endTime = dtf.format(LocalDateTime.now());
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss.SSS");
        try {
            Date date1 = sdf.parse(startTime);
            Date date2 = sdf.parse(endTime);
            long differenceInTime = date2.getTime() - date1.getTime();
            Allure.addAttachment("Test Duration", differenceInTime + " milliseconds");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @After
    public void onFailure(Scenario scenario) {
        if (scenario.isFailed()) {
            Allure.addAttachment("Screenshot", new ByteArrayInputStream(((TakesScreenshot) Driver.get()).getScreenshotAs(OutputType.BYTES)));
        }
    }

}
