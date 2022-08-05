package gov.di_ipv_core.pages;

import org.openqa.selenium.By;

public class IpvCoreStubHomepage extends GlobalPage {

    private static final By VISIT_CREDENTIAL_ISSUERS = By.cssSelector(".govuk-button");

    public void clickVisitCredentialIssuers(){
        clickElement(VISIT_CREDENTIAL_ISSUERS);
    }



}
