package pages;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.BasePage;

public class CheckoutOverviewPage extends BasePage {

    // ------------------------------------------
    // Constructor & PageFactory Initialization
    // ------------------------------------------
    public CheckoutOverviewPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    // ------------------------------------------
    // WebElement Locators
    // ------------------------------------------
    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(id = "finish")
    private WebElement finishButton;

    // ------------------------------------------
    // Overview Data Methods
    // ------------------------------------------
    public int getOverviewItemCount() {
        int overviewItemCount = cartItems.size();
        log.info("Fetching cart item count: {}", overviewItemCount);
        return overviewItemCount;
    }

    // ------------------------------------------
    // Navigation Methods
    // ------------------------------------------
    public void clickFinish() {
        log.info("Clicking Finish on checkout overview");
        finishButton.click();
    }

}