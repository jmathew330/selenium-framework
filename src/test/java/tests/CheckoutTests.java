package tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import utils.LoginTestDataReader;

@Epic("Core Selenium Tests")
@Feature("Checkout Module")
public class CheckoutTests extends BaseTest {

    // ------------------------------------------
    // Test Setup
    // ------------------------------------------
    @BeforeMethod
    @Description("Logs in, adds a product to the cart, and navigates to the checkout page before each test")
    public void setupCheckout() {
        log.info("Logging in and preparing checkout state");

        loginPage.login(
            LoginTestDataReader.get("defaultProductUser"),
            LoginTestDataReader.get("validPassword")
        );

        inventoryPage.waitForInventoryPage();
        inventoryPage.addFirstItemToCart();
        inventoryPage.openCart();
        cartPage.goToCheckout();
    }

    // ------------------------------------------
    // Checkout Flow Tests
    // ------------------------------------------
    @Story("Verify complete checkout flow for single product")
    @Test(
        description = "Complete checkout flow: fill information, review overview, and finish checkout",
        dataProvider = "checkoutInfo"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test completes the full checkout flow for a single product and verifies the final confirmation message")
    public void verifyCompleteCheckoutFlow(String firstName, String lastName, String zip) {

        log.info("===== START TEST: verifyCompleteCheckoutFlow =====");

        // Fill out checkout information
        checkoutInfoPage.fillOutForm(firstName, lastName, zip);

        // Continue to overview page
        checkoutInfoPage.clickContinue();

        // Verify item appears on overview page
        Assert.assertTrue(
            checkoutOverviewPage.getOverviewItemCount() > 0,
            "No items displayed on checkout overview page"
        );

        // Finish checkout
        checkoutOverviewPage.clickFinish();

        // Verify final confirmation message
        Assert.assertEquals(
            checkoutCompletePage.getCompleteHeaderText(),
            "Thank you for your order!",
            "Checkout completion message mismatch"
        );

        log.info("Checkout flow completed successfully");
    }

    // ------------------------------------------
    // Validation Tests
    // ------------------------------------------
    @Story("Verify checkout validation error for missing first name")
    @Test(description = "Verify validation error when first name is missing during checkout")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that an error message is displayed when first name is not provided")
    public void verifyFirstNameMissingValidationError() {

        log.info("===== START TEST: verifyFirstNameMissingValidationError =====");

        // Submit checkout form with missing first name
        checkoutInfoPage.fillOutForm("", "LastNameQA", "12345");
        checkoutInfoPage.clickContinue();

        // Verify validation error message
        Assert.assertTrue(
            checkoutInfoPage.getErrorMessageText().contains("Error: First Name is required"),
            "Expected validation error for missing first name was not displayed"
        );
        
        log.info("Checkout validation error displayed: {}", checkoutInfoPage.getErrorMessageText());
    }
    
    @Story("Verify checkout validation error for missing last name")
    @Test(description = "Verify validation error when last name is missing during checkout")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that an error message is displayed when last name is not provided")
    public void verifyLastNameMissingValidationError() {

        log.info("===== START TEST: verifyLastNameMissingValidationError =====");

        // Submit checkout form with missing last name
        checkoutInfoPage.fillOutForm("FirstNameQA", "", "12345");
        checkoutInfoPage.clickContinue();

        // Verify validation error message
        Assert.assertTrue(
            checkoutInfoPage.getErrorMessageText().contains("Error: Last Name is required"),
            "Expected validation error for missing last name was not displayed"
        );
        
        log.info("Checkout validation error displayed: {}", checkoutInfoPage.getErrorMessageText());
    }
    
    
    @Story("Verify checkout validation error for missing zip code")
    @Test(description = "Verify validation error when zip code is missing during checkout")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that an error message is displayed when zip code is not provided")
    public void verifyZipCodeMissingValidationError() {

        log.info("===== START TEST: verifyZipCodeMissingValidationError =====");

        // Submit checkout form with missing last name
        checkoutInfoPage.fillOutForm("FirstNameQA", "LastNameQA", "");
        checkoutInfoPage.clickContinue();

        // Verify validation error message
        Assert.assertTrue(
            checkoutInfoPage.getErrorMessageText().contains("Error: Postal Code is required"),
            "Expected validation error for missing zip code was not displayed"
        );
        
        log.info("Checkout validation error displayed: {}", checkoutInfoPage.getErrorMessageText());
    }

    // ------------------------------------------
    // Data Providers
    // ------------------------------------------
    @DataProvider(name = "checkoutInfo")
    public Object[][] checkoutFormData() {
        return new Object[][]{
            {"Jeffin", "Mathew", "11040"}
        };
    }
}
