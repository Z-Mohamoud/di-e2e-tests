package gov.di_ipv_core.pages;

import org.openqa.selenium.By;

public class ExperianUserSearchResultsPage extends GlobalPage {

    private static final By GO_TO_KBV_CRI = By.xpath("//a[contains(@href,'/authorize')]");

    public void goToKbvCri(){
        clickElement(GO_TO_KBV_CRI);
    }



}
