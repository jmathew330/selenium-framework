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
    @Description("Logs in as a default product user, adds a product to the cart, and navigates to the checkout page")
    public void loginToInventory() {
        log.info("Logging in and adding product before checkout tests");

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
    // Checkout Flow Test
    // ------------------------------------------
    @Story("Verify complete checkout flow for single product")
    @Test(
            description = "Complete checkout flow: fill information, review overview, and finish checkout",
            dataProvider = "formData"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test completes the full checkout flow for a single product and verifies the final confirmation message")
    public void verifyCompleteCheckoutFlow(String firstName, String lastName, String zip) {

        log.info("===== START TEST: verifyCompleteCheckoutFlow =====");

        // Fill out checkout information
        checkoutInfoPage.fillOutForm(firstName, lastName, zip);

        // Continue to overview page
        checkoutInfoPage.clickContinue();

        // Assert items appear on overview page
        Assert.assertTrue(
                checkoutOverviewPage.getOverviewItemCount() > 0,
                "No items displayed on checkout overview page"
        );

        // Finish checkout
        checkoutOverviewPage.clickFinish();

        // Assert final confirmation text
        Assert.assertEquals(
                checkoutCompletePage.getCompleteHeaderText(),
                "Thank you for your order!",
                "Checkout completion message mismatch"
        );

        log.info("Checkout flow completed successfully");
    }

    // ------------------------------------------
    // Data Providers
    // ------------------------------------------
    @DataProvider(name = "formData")
    public Object[][] formData() {
        return new Object[][]{
                {"Jeffin", "Mathew", "11040"}
        };
    }

}
