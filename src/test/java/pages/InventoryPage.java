package pages;

import java.util.ArrayList;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import base.BasePage;

public class InventoryPage extends BasePage {

    // ------------------------------------------
    // Constructor & PageFactory Initialization
    // ------------------------------------------
    public InventoryPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    // ------------------------------------------
    // WebElement Locators
    // ------------------------------------------
    @FindBy(id = "inventory_container")
    private WebElement inventoryContainer;

    @FindBy(className = "inventory_item")
    private List<WebElement> inventoryItems;

    @FindBy(className = "inventory_item_name")
    private List<WebElement> productNames;

    @FindBy(className = "inventory_item_price")
    private List<WebElement> productPrices;

    @FindBy(className = "product_sort_container")
    private WebElement sortDropdown;

    @FindBy(xpath = "//button[@class='btn btn_primary btn_small btn_inventory ']")
    private List<WebElement> addToCartButtons;

    @FindBy(xpath = "//a[@class='shopping_cart_link']")
    private WebElement shoppingCartLink;

    // ------------------------------------------
    // Page Load / State Methods
    // ------------------------------------------
    public void waitForInventoryPage() {
        log.info("Waiting for inventory page to load");
        waitForVisibility(inventoryContainer);
        log.info("Inventory page loaded successfully");
    }

    // ------------------------------------------
    // Product Data Methods
    // ------------------------------------------
    public int getProductCount() {
        int count = inventoryItems.size();
        log.info("Total products displayed: {}", count);
        return count;
    }

    public List<String> getProductInfo(
            List<String> stringList,
            List<WebElement> elementList) {

        stringList = new ArrayList<>();

        for (WebElement element : elementList) {
            String text = element.getText().trim();
            if (!text.isEmpty()) {
                stringList.add(text);
            }
        }

        log.info("Fetched product names: {}", stringList);
        return stringList;
    }

    public List<String> getProductNames() {
        List<String> names = new ArrayList<>();
        return getProductInfo(names, productNames);
    }

    public List<Double> getProductPrices() {
        List<Double> prices = new ArrayList<>();

        for (WebElement price : productPrices) {
            String priceText = price.getText().trim().replace("$", "");
            Double productPrice = Double.parseDouble(priceText);
            prices.add(productPrice);
        }

        log.info("Fetched product prices: {}", prices);
        return prices;
    }

    // ------------------------------------------
    // Sorting Methods
    // ------------------------------------------
    public void sortByVisibleText(String option) {
        new Select(sortDropdown).selectByVisibleText(option);
    }

    // ------------------------------------------
    // Cart Interaction Methods
    // ------------------------------------------
    public void addFirstItemToCart() {
        log.info("Adding first product to cart");
        addToCartButtons.get(0).click();
    }

    public void addItemToCartByName(String productName) {
        for (WebElement item : inventoryItems) {
            String itemName =
                    item.findElement(By.className("inventory_item_name")).getText();

            if (itemName.equals(productName)) {
                WebElement addToCartButton = item.findElement(By.tagName("button"));
                addToCartButton.click();
                log.info("Added {} to cart", itemName);
            }
        }
    }

    public void addItemsToCartByName(List<String> productNames) {
        for (String productName : productNames) {
            for (WebElement item : inventoryItems) {
                String itemName =
                        item.findElement(By.className("inventory_item_name")).getText();

                if (itemName.equals(productName)) {
                    WebElement addToCartButton = item.findElement(By.tagName("button"));
                    addToCartButton.click();
                    log.info("Added {} to cart", itemName);
                }
            }
        }
    }

    // ------------------------------------------
    // Cart Badge & Navigation Methods
    // ------------------------------------------
    public int getBadgeCount() {
        log.info("Fetching badge count");

        WebElement shoppingCartBadge =
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//span[@class='shopping_cart_badge']")));

        return Integer.parseInt(shoppingCartBadge.getText());
    }

    public void openCart() {
        log.info("Navigating to cart");
        shoppingCartLink.click();
    }

}