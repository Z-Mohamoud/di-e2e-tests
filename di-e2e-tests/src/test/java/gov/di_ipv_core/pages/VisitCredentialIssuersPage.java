package gov.di_ipv_core.pages;

import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.fail;

public class VisitCredentialIssuersPage extends GlobalPage {

    private static final By KBV_CRI_BUILD = By.xpath("//input[@value='KBV CRI Build']");
    private static final By KBV_CRI_STAGING = By.xpath("//input[@value='KBV CRI Staging']");
    private static final By KBV_CRI_INT = By.xpath("//input[@value='KBV CRI Integration']");

    public void visitKbvCredentialIssuer() {
        String environment = System.getProperty("env");
        if (environment.equals("Build")) {
            clickElement(KBV_CRI_BUILD);
        } else if (environment.equals("Staging")) {
            clickElement(KBV_CRI_STAGING);
        } else if (environment.equals("Integation")){
            clickElement(KBV_CRI_INT);
        } else {
            fail("A valid Environment Value was not specified in the run configuration");
        }
    }


}
