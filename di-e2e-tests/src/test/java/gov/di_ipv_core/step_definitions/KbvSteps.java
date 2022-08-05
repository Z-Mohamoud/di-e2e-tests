package gov.di_ipv_core.step_definitions;

import gov.di_ipv_core.pages.*;
import gov.di_ipv_core.utilities.ConfigurationReader;
import gov.di_ipv_core.utilities.Driver;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.io.IOException;

public class KbvSteps {

    private final IpvCoreStubHomepage ipvCoreStubHomepage = new IpvCoreStubHomepage();
    private final VisitCredentialIssuersPage visitCredentialIssuersPage = new VisitCredentialIssuersPage();
    private final UserForKbvCriPage userForKbvCriPage = new UserForKbvCriPage();
    private final ExperianUserSearchResultsPage experianUserSearchResultsPage = new ExperianUserSearchResultsPage();
    private final KbvQuestionPage kbvQuestionPage = new KbvQuestionPage();
    private final KbvCriResponsePage kbvCriResponsePage = new KbvCriResponsePage();

    @Given("the user is on KBV CRI Staging as {string}")
    public void theUserIsOnKBVCRIStagingAs(String userName) {
        Driver.get().get(ConfigurationReader.getIPVCoreStubUrl());
        ipvCoreStubHomepage.clickVisitCredentialIssuers();
        visitCredentialIssuersPage.visitKbvCredentialIssuer();
        userForKbvCriPage.isUserOnKbvCriPage();
        userForKbvCriPage.enterUsernameAndSearch(userName);
        experianUserSearchResultsPage.goToKbvCri();
    }

    @When("the user answers their KBV Question {string} for {string}")
    public void theUserAnswersTheirKBVQuestionSuccessfully(String answerType, String userName) throws IOException {
        kbvQuestionPage.answerKbvQuestion(answerType, userName);
    }

    @Then("the user should see a {string} of {int} in the KBV CRI Response")
    public void theUserShouldSeeAOfInTheKBVCRIResponse(String attribute, int expectedValue) {
        kbvCriResponsePage.clickKbvResponseLink();
        Assert.assertEquals("The " + attribute + " value was not returned correctly. Expected: " + expectedValue + ". Actual: " + kbvCriResponsePage.getKbvCriAttribute(attribute),
                expectedValue, kbvCriResponsePage.getKbvCriAttribute(attribute));
    }

    @Then("the user should see a {string} of {string} in the KBV CRI Response")
    public void theUserShouldSeeAOfInTheKBVCRIResponse(String attribute, String expectedValue) {
        kbvCriResponsePage.clickKbvResponseLink();
        Assert.assertEquals("", expectedValue, kbvCriResponsePage.getKbvCriAttribute(attribute));
    }
}
