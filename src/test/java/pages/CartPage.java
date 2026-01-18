package pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.BasePage;

public class CartPage extends BasePage {

    // ------------------------------------------
    // Constructor & PageFactory Initialization
    // ------------------------------------------
    public CartPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    // ------------------------------------------
    // WebElement Locators
    // ------------------------------------------
    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> cartItemNames;

    @FindBy(xpath = "(//button[normalize-space()='Remove'])")
    private List<WebElement> removeButtons;

    @FindBy(name = "checkout")
    private WebElement checkoutButton;

    // ------------------------------------------
    // Cart Item Methods
    // ------------------------------------------
    public int getCartItemCount() {
        int cartItemCount = cartItems.size();
        log.info("Fetching cart item count: {}", cartItemCount);
        return cartItemCount;
    }

    public List<String> getCartItemNames() {
        log.info("Retrieving product names from cart");

        List<String> items = new ArrayList<>();
        for (WebElement name : cartItemNames) {
            String itemName = name.getText();
            items.add(itemName);
        }

        log.info("Retrieved {} product(s) from cart: {}", items.size(), items);
        return items;
    }

    // ------------------------------------------
    // Remove Item Methods
    // ------------------------------------------
    public void removeFirstItemFromCart() {
        if (removeButtons.isEmpty()) {
            log.warn("No items to remove from cart");
            return;
        }

        log.info("Removing first item from cart");
        removeButtons.get(0).click();
    }

    public void removeItemsFromCartByName(List<String> productNames) {
        for (String name : productNames) {
            for (WebElement item : cartItems) {
                String itemName =
                        item.findElement(By.className("inventory_item_name")).getText();

                if (name.equals(itemName)) {
                    WebElement button = item.findElement(By.tagName("button"));
                    button.click();
                    log.info("Removed {} from cart", name);
                }
            }
        }
    }

    // ------------------------------------------
    // Navigation Methods
    // ------------------------------------------
    public void goToCheckout() {
        log.info("Proceeding to checkout");
        checkoutButton.click();
    }

}
