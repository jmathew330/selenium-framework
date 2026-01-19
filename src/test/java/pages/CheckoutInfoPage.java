package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.BasePage;

public class CheckoutInfoPage extends BasePage {

    // ------------------------------------------
    // Constructor & PageFactory Initialization
    // ------------------------------------------
    public CheckoutInfoPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    // ------------------------------------------
    // WebElement Locators
    // ------------------------------------------
    @FindBy(id = "first-name")
    private WebElement firstNameInput;

    @FindBy(id = "last-name")
    private WebElement lastNameInput;

    @FindBy(id = "postal-code")
    private WebElement zipInput;

    @FindBy(id = "continue")
    private WebElement continueButton;
    
    @FindBy(xpath="//h3[@data-test='error']")
    private WebElement errorMessage;

    // ------------------------------------------
    // Form Input Methods
    // ------------------------------------------
    public void enterFirstName(String firstName) {
        firstNameInput.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        lastNameInput.sendKeys(lastName);
    }

    public void enterZipCode(String zipCode) {
        zipInput.sendKeys(zipCode);
    }

    public void fillOutForm(String firstName, String lastName, String zipCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterZipCode(zipCode);
        log.info("Entering checkout information: {} {} {}", firstName, lastName, zipCode);
    }

    // ------------------------------------------
    // Navigation Methods
    // ------------------------------------------
    public void clickContinue() {
        log.info("Continuing checkout");
        continueButton.click();
    }
    
    
    public String getErrorMessageText() {
    	return errorMessage.getText();
    }

}
