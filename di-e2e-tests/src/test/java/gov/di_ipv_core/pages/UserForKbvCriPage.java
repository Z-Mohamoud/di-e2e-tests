package gov.di_ipv_core.pages;

import org.junit.Assert;
import org.openqa.selenium.By;

public class UserForKbvCriPage extends GlobalPage {

    private static final By PAGE_HEADING = By.cssSelector(".govuk-heading-xl");
    private static final By SEARCH_BOX = By.cssSelector(".govuk-input.govuk-input--width-20");
    private static final By SEARCH_BUTTON = By.xpath("//button[text()='Search']");


    private String PAGE_TITLE_PREFIX = "user for kbv cri ";

    public void isUserOnKbvCriPage() {
        String environment = System.getProperty("env").toLowerCase();
        String pageTitle = getCurrentDriver().findElement(PAGE_HEADING).getText().toLowerCase();
        Assert.assertEquals("The user is not on the correct page for the environment specified: " + environment, PAGE_TITLE_PREFIX + environment, pageTitle);
    }

    public void enterUsernameAndSearch(String username) {
        populateDetailsInFields(SEARCH_BOX, username);
        clickElement(SEARCH_BUTTON);
    }


}
