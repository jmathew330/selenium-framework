package tests;

import org.testng.Assert;
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
@Feature("Login Module")
public class LoginTests extends BaseTest {

    // ------------------------------------------
    // Data Providers
    // ------------------------------------------
    @DataProvider(name = "validUsers")
    @Description("Provides valid usernames and the corresponding password for login tests")
    public Object[][] validUsers() {

        log.info("Loading valid users from login test data");

        String[] usernames = LoginTestDataReader.getArray("validUsernames");
        String password = LoginTestDataReader.get("validPassword");

        Object[][] data = new Object[usernames.length][2];
        for (int i = 0; i < usernames.length; i++) {
            data[i][0] = usernames[i];
            data[i][1] = password;
        }

        log.info("Total valid users loaded: {}", usernames.length);
        return data;
    }

    // ------------------------------------------
    // Valid Login Tests
    // ------------------------------------------
    @Story("Verify valid user login")
    @Test(
        dataProvider = "validUsers",
        description = "Login with valid users and verify navigation to inventory page"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test logs in with each valid user and verifies that the inventory page loads successfully")
    public void verifyValidLogin(String username, String password) {

        log.info("===== START TEST: verifyValidLogin FOR USER {} =====", username);

        loginPage.login(username, password);
        inventoryPage.waitForInventoryPage();

        Assert.assertTrue(
            driver.getCurrentUrl().contains("inventory"),
            "Login failed: Inventory page did not load for user: " + username
        );

        log.info("Valid login verified successfully for user: {}", username);
    }

    // ------------------------------------------
    // Locked-Out User Tests
    // ------------------------------------------
    @Story("Verify login for locked-out user")
    @Test(
        description = "Attempt login with a locked-out user and verify error message"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that a locked-out user cannot login and receives the correct error message")
    public void verifyLockedOutLogin() {

        String lockedOutUser = LoginTestDataReader.get("lockedOutUsername");

        log.info("===== START TEST: verifyLockedOutLogin FOR USER {} =====", lockedOutUser);

        loginPage.login(lockedOutUser, LoginTestDataReader.get("validPassword"));

        log.warn("Expecting locked-out error message for user: {}", lockedOutUser);

        Assert.assertTrue(
            loginPage.getErrorMessage().contains("Sorry, this user has been locked out."),
            "Error message not displayed correctly for locked-out user"
        );

        log.info("Locked-out user behavior validated successfully for user: {}", lockedOutUser);
    }

    // ------------------------------------------
    // Invalid Login Tests
    // ------------------------------------------
    @Story("Verify login with invalid credentials")
    @Test(
        description = "Attempt login with invalid username and password and verify error message"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that logging in with invalid credentials displays the correct error message")
    public void verifyInvalidLogin() {

        log.info("===== START TEST: verifyInvalidLogin =====");

        loginPage.login(
            LoginTestDataReader.get("invalidUsername"),
            LoginTestDataReader.get("invalidPassword")
        );

        Assert.assertTrue(
            loginPage.getErrorMessage().contains("Username and password do not match any user in this service"),
            "Error message not displayed correctly for invalid user"
        );

        log.info("Invalid user error message validated successfully");
    }

    // ------------------------------------------
    // Empty Username Tests
    // ------------------------------------------
    @Story("Verify login with empty username")
    @Test(
        description = "Attempt login with empty username and valid password and verify error message"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that leaving the username empty displays the correct error message")
    public void verifyEmptyUsernameLogin() {

        log.info("===== START TEST: verifyEmptyUsernameLogin =====");

        loginPage.login("", LoginTestDataReader.get("validPassword"));

        Assert.assertTrue(
            loginPage.getErrorMessage().contains("Username is required"),
            "Error message not displayed correctly when username is empty"
        );

        log.info("Empty username validation verified successfully");
    }

    // ------------------------------------------
    // Empty Password Tests
    // ------------------------------------------
    @Story("Verify login with empty password")
    @Test(
        description = "Attempt login with valid username and empty password and verify error message"
    )
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that leaving the password empty displays the correct error message")
    public void verifyEmptyPasswordLogin() {

        log.info("===== START TEST: verifyEmptyPasswordLogin =====");

        loginPage.login(LoginTestDataReader.get("nonEmptyUsername"), "");

        Assert.assertTrue(
            loginPage.getErrorMessage().contains("Password is required"),
            "Error message not displayed correctly when password is empty"
        );

        log.info("Empty password validation verified successfully");
    }
}
