package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.BasePage;

public class LoginPage extends BasePage {

    // ------------------------------------------
    // Constructor & PageFactory Initialization
    // ------------------------------------------
    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    // ------------------------------------------
    // WebElement Locators
    // ------------------------------------------
    @FindBy(id = "user-name")
    private WebElement usernameInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "h3[data-test='error']")
    private WebElement errorMessage;

    // ------------------------------------------
    // Login Action Methods
    // ------------------------------------------
    public void enterUsername(String username) {
        usernameInput.clear();
        usernameInput.sendKeys(username);
    }

    public void enterPassword(String password) {
        passwordInput.clear();
        passwordInput.sendKeys(password);
    }

    public void clickLogin() {
        log.info("Login button clicked");
        loginButton.click();
    }

    public void login(String username, String password) {
        log.info("Attempting login with username: {}", username);
        enterUsername(username);
        enterPassword(password);
        clickLogin();
    }

    // ------------------------------------------
    // Error Handling Methods
    // ------------------------------------------
    public String getErrorMessage() {
        String error = errorMessage.getText();
        log.warn("Login error message displayed: {}", error);
        return error;
    }

}