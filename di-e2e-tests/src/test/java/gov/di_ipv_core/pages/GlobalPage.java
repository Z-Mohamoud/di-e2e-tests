package gov.di_ipv_core.pages;

import gov.di_ipv_core.utilities.ConfigurationReader;
import gov.di_ipv_core.utilities.PageObjectSupport;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class GlobalPage extends PageObjectSupport {

    WebDriverWait wait = new WebDriverWait(getCurrentDriver(), Duration.ofSeconds(10));
    private static final String PAYLOAD_PATH = "src/test/resources/properties/";



    public void openPage(String url) {
        getCurrentDriver().navigate().to(url);
    }

    public GlobalPage switchAlert() {
        getCurrentDriver().switchTo().alert();
        return this;
    }

    public GlobalPage pageRefresh() {
        getCurrentDriver().navigate().refresh();
        return this;
    }

    public String getBaseUrl() {
        return ConfigurationReader.getIPVCoreStubUrl();
    }

    public String getCurrentPageUrl() {
        return getCurrentDriver().getCurrentUrl();
    }

    public void waitForUrlToChange(String previousUrl, int waitMaxSeconds) throws InterruptedException {
        for (int i = 0; i <= waitMaxSeconds; i++) {
            if (!previousUrl.equals(getCurrentDriver().getCurrentUrl())) {
                return;
            }
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }


    protected void highlightElementSelected(By selectorName) {
        WebElement ele = getCurrentDriver().findElement(selectorName);
        JavascriptExecutor js = (JavascriptExecutor) getCurrentDriver();
        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 1px solid red;');", ele);
    }

    public void populateDetailsInFields(By detailsSelector, String fieldValue) {
        waitForElementVisible(detailsSelector, 60);
        WebElement field = getCurrentDriver().findElement(detailsSelector);
        field.sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), fieldValue);
    }

    public void populateField(By selector, String value) {
        waitForElementVisible(selector, 60);
        WebElement field = getCurrentDriver().findElement(selector);
        field.sendKeys(value);
    }

    public void populateDropdownDetails(By detailsSelector, String fieldValue) {
        populateDetailsInFields(detailsSelector, fieldValue);
        WebElement ar = getCurrentDriver().findElement(detailsSelector);
        ar.sendKeys(Keys.ARROW_UP, Keys.RETURN);
    }

    public void selectDropdownValues(String value, By fieldSelector, By dropdownSelector) {
        waitForElementVisible(fieldSelector);
        WebElement ar = getCurrentDriver().findElement(fieldSelector);
        ar.sendKeys(value);
        waitForElementVisible(dropdownSelector, 10);
        ar.sendKeys(Keys.ARROW_DOWN, Keys.RETURN);
    }

    public void selectValueFromDropDownList(String dropDownValue, By fieldSelector, By dropDownListSelector) {
        boolean optionFound = false;
        waitForElementVisible(fieldSelector);
        List<WebElement> dropDownList = getCurrentDriver().findElements(dropDownListSelector);

        for (WebElement dropDownElement : dropDownList) {
            if (dropDownElement.getText().contains(dropDownValue)) {
                dropDownElement.click();
                optionFound = true;
                break;
            }
        }
        Assert.assertTrue("\nExpected option " + dropDownValue + " was not found in the list of available options in the element " + dropDownList, optionFound);
    }

    public void scrollToElement(By elementSelector) throws InterruptedException {
        WebElement element = waitForElementVisible(elementSelector);
        ((JavascriptExecutor) getCurrentDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        isElementOnScreen(element);
    }

    public void scrollDownToViewTheElement(WebElement element) throws InterruptedException {
        ((JavascriptExecutor) getCurrentDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
        isElementOnScreen(element);
    }

    public boolean isElementOnScreen(WebElement element) throws InterruptedException {
        boolean isOnScreen = false;
        int interactionCounter = 0;
        while (interactionCounter++ < 30) {
            if (element.getLocation().y + element.getSize().height / 2 < getCurrentDriver().manage().window().getSize().height) {
                isOnScreen = true;
                break;
            }
            TimeUnit.MILLISECONDS.sleep(10);
        }
        return isOnScreen;
    }


    public void scrollDownToBottom() {
        JavascriptExecutor js = (JavascriptExecutor) getCurrentDriver();
        js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public static String generateStringFromJsonPayloadResource(String jsonResourcePath, String fileName) throws IOException {
        try {
            return new String(Files.readAllBytes(Paths.get(jsonResourcePath + fileName + ".json"))).replaceAll("\n", "");
        } catch (NoSuchFileException e) {
            return new String(Files.readAllBytes(Paths.get(jsonResourcePath + "JSON/" + fileName + ".json"))).replaceAll("\n", "");
        }
    }

    public static String extractStringValueFromJsonString(String jsonString, String jsonPath) {
        return (String) com.jayway.jsonpath.JsonPath.read(jsonString, jsonPath);
    }

    public static Integer extractIntegerValueFromJsonString(String jsonString, String jsonPath) {
        return com.jayway.jsonpath.JsonPath.read(jsonString, jsonPath);
    }

}
