package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.BasePage;

public class CheckoutCompletePage extends BasePage {

    // ------------------------------------------
    // Constructor & PageFactory Initialization
    // ------------------------------------------
    public CheckoutCompletePage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    // ------------------------------------------
    // WebElement Locators
    // ------------------------------------------
    @FindBy(className = "complete-header")
    private WebElement completeHeader;

    // ------------------------------------------
    // Page Data Methods
    // ------------------------------------------
    public String getCompleteHeaderText() {
        return completeHeader.getText();
    }

}
