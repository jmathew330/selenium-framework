package tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
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
@Feature("Products / Inventory Module")
public class ProductsTests extends BaseTest {

    // ------------------------------------------
    // Test Setup
    // ------------------------------------------
    @BeforeMethod
    @Description("Logs in with default product user and ensures inventory page is loaded before each test")
    public void loginToInventory() {
        log.info("Logging in before inventory tests");

        loginPage.login(
            LoginTestDataReader.get("defaultProductUser"),
            LoginTestDataReader.get("validPassword")
        );

        inventoryPage.waitForInventoryPage();
    }

    // ------------------------------------------
    // Inventory Page Tests
    // ------------------------------------------
    @Story("Verify inventory page loads")
    @Test(description = "Check that inventory page URL contains 'inventory'")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that the inventory page loads successfully after login")
    public void verifyInventoryPageLoads() {
        log.info("===== START TEST: verifyInventoryPageLoads =====");

        Assert.assertTrue(
            driver.getCurrentUrl().contains("inventory"),
            "Inventory page did not load. URL does not contain 'inventory'. Actual URL: " + driver.getCurrentUrl()
        );
    }

    @Story("Verify products are displayed")
    @Test(description = "Check that products are displayed on the inventory page")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that at least one product is displayed on the inventory page")
    public void verifyProductsAreDisplayed() {
        log.info("===== START TEST: verifyProductsAreDisplayed =====");

        int productCount = inventoryPage.getProductCount();

        Assert.assertTrue(
            productCount > 0,
            "No products are displayed on the inventory page. Product count was: " + productCount
        );
    }

    @Story("Verify product names")
    @Test(description = "Check that specific product names exist on the inventory page")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that the product 'Sauce Labs Backpack' is displayed among inventory products")
    public void verifyProductNames() {
        log.info("===== START TEST: verifyProductNamesAreDisplayed =====");

        List<String> actualProductNames = inventoryPage.getProductNames();

        Assert.assertFalse(
            actualProductNames.isEmpty(),
            "Product names list is empty. Expected at least one product name."
        );

        Assert.assertTrue(
            actualProductNames.contains("Sauce Labs Backpack"),
            "Expected product 'Sauce Labs Backpack' was not found. Actual products: " + actualProductNames
        );
    }

    @Story("Verify product prices")
    @Test(description = "Check that product prices are displayed correctly")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that at least one product price exists and contains the expected value")
    public void verifyProductPrices() {
        log.info("===== START TEST: verifyProductPrices =====");

        List<Double> actualProductPrices = inventoryPage.getProductPrices();

        Assert.assertFalse(
            actualProductPrices.isEmpty(),
            "Product prices list is empty. Expected at least one product price."
        );

        Assert.assertTrue(
            actualProductPrices.contains(29.99),
            "Expected price 29.99 was not found. Actual prices: " + actualProductPrices
        );
    }

    // ------------------------------------------
    // Sorting Tests
    // ------------------------------------------
    @Story("Verify product sorting A to Z")
    @Test(description = "Sort products by name from A to Z and verify order")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that inventory products are sorted alphabetically from A to Z")
    public void verifySortByNameAToZ() {
        log.info("===== START TEST: verifySortByNameAToZ =====");

        inventoryPage.sortByVisibleText("Name (A to Z)");

        List<String> actual = inventoryPage.getProductNames();
        List<String> expected = new ArrayList<>(actual);
        Collections.sort(expected);

        Assert.assertEquals(
            actual,
            expected,
            "Products are not sorted alphabetically from A to Z. Actual order: " + actual
        );
    }

    @Story("Verify product sorting Z to A")
    @Test(description = "Sort products by name from Z to A and verify order")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that inventory products are sorted alphabetically from Z to A")
    public void verifySortByNameZToA() {
        log.info("===== START TEST: verifySortByNameZToA =====");

        inventoryPage.sortByVisibleText("Name (Z to A)");

        List<String> actual = inventoryPage.getProductNames();
        List<String> expected = new ArrayList<>(actual);
        expected.sort(Collections.reverseOrder());

        Assert.assertEquals(
            actual,
            expected,
            "Products are not sorted alphabetically from Z to A. Actual order: " + actual
        );
    }

    @Story("Verify product sorting by price low to high")
    @Test(description = "Sort products by price from low to high and verify order")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that inventory products are sorted by price from low to high")
    public void verifySortByPriceLowToHigh() {
        log.info("===== START TEST: verifySortByPriceLowToHigh =====");

        inventoryPage.sortByVisibleText("Price (low to high)");

        List<Double> actual = inventoryPage.getProductPrices();
        List<Double> expected = new ArrayList<>(actual);
        Collections.sort(expected);

        Assert.assertEquals(
            actual,
            expected,
            "Products are not sorted by price from low to high. Actual order: " + actual
        );
    }

    @Story("Verify product sorting by price high to low")
    @Test(description = "Sort products by price from high to low and verify order")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that inventory products are sorted by price from high to low")
    public void verifySortByPriceHighToLow() {
        log.info("===== START TEST: verifySortByPriceHighToLow =====");

        inventoryPage.sortByVisibleText("Price (high to low)");

        List<Double> actual = inventoryPage.getProductPrices();
        List<Double> expected = new ArrayList<>(actual);
        expected.sort(Collections.reverseOrder());

        Assert.assertEquals(
            actual,
            expected,
            "Products are not sorted by price from high to low. Actual order: " + actual
        );
    }

    // ------------------------------------------
    // Cart Badge & Single Product Tests
    // ------------------------------------------
    @Story("Verify single product badge count")
    @Test(description = "Add single product to cart and verify badge count updates")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that adding one product updates the cart badge count to 1")
    public void verifySingleProductBadgeCount() {
        log.info("===== START TEST: verifyAddSingleProductToCart =====");

        inventoryPage.addFirstItemToCart();

        Assert.assertEquals(
            inventoryPage.getBadgeCount(),
            1,
            "Cart badge count is incorrect after adding one item."
        );
    }

    @Story("Verify single product appears in cart")
    @Test(description = "Add a product to cart and verify it appears in the cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that a specific product is added to the cart and shows in the cart page")
    public void verifySingleProductAppearsInCart() {
        log.info("===== START TEST: verifyProductAppearsInCart =====");

        String productName = "Sauce Labs Fleece Jacket";

        inventoryPage.addItemToCartByName(productName);
        inventoryPage.openCart();

        List<String> cartItems = cartPage.getCartItemNames();

        Assert.assertTrue(
            cartItems.contains(productName),
            "Product '" + productName + "' was not found in the cart. Cart items: " + cartItems
        );

        Assert.assertEquals(
            cartPage.getCartItemCount(),
            1,
            "Cart item count is incorrect. Expected 1 item in the cart."
        );
    }

    @Story("Verify removing single product from cart")
    @Test(description = "Add product to cart and remove it, then verify cart is empty")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that removing a single product from the cart works correctly")
    public void verifyRemoveSingleProductFromCart() {
        log.info("===== START TEST: verifyRemoveSingleProductFromCart =====");

        inventoryPage.addFirstItemToCart();
        inventoryPage.openCart();

        Assert.assertEquals(
            cartPage.getCartItemCount(),
            1,
            "Cart item count is incorrect. Expected 1 item in the cart."
        );

        cartPage.removeFirstItemFromCart();

        Assert.assertEquals(
            cartPage.getCartItemCount(),
            0,
            "Cart is not empty after removing item."
        );
    }

    // ------------------------------------------
    // Multiple Products Tests
    // ------------------------------------------
    @Story("Verify multiple products badge count")
    @Test(description = "Add multiple products to cart and verify badge count updates")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that adding multiple products updates the cart badge count correctly")
    public void verifyMultipleProductsBadgeCount() {
        log.info("===== START TEST: verifyAddMultipleProductsToCart =====");

        List<String> productNames = List.of("Sauce Labs Backpack", "Sauce Labs Bike Light");

        inventoryPage.addItemsToCartByName(productNames);

        Assert.assertEquals(
            inventoryPage.getBadgeCount(),
            productNames.size(),
            "Cart badge count does not match number of added products."
        );
    }

    @Story("Verify multiple products appear in cart")
    @Test(description = "Add multiple products to cart and verify they appear in the cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that multiple selected products appear in the cart page correctly")
    public void verifyProductsAppearInCart() {
        log.info("===== START TEST: verifyProductsAppearInCart =====");

        List<String> productNames = List.of("Sauce Labs Backpack", "Sauce Labs Fleece Jacket");

        inventoryPage.addItemsToCartByName(productNames);
        inventoryPage.openCart();

        Assert.assertEquals(
            cartPage.getCartItemCount(),
            productNames.size(),
            "Cart item count does not match number of added products."
        );

        List<String> cartItems = cartPage.getCartItemNames();

        for (String name : productNames) {
            Assert.assertTrue(
                cartItems.contains(name),
                "Product '" + name + "' was not found in cart. Cart items: " + cartItems
            );
        }
    }

    @Story("Verify removing multiple products from cart")
    @Test(description = "Add multiple products to cart and remove them all, then verify cart is empty")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Test verifies that removing all products from the cart results in an empty cart")
    public void verifyRemoveMultipleItemsFromCart() {
        log.info("===== START TEST: verifyRemoveMultipleItemsFromCart =====");

        List<String> productNames = List.of("Sauce Labs Bolt T-Shirt", "Sauce Labs Fleece Jacket");

        inventoryPage.addItemsToCartByName(productNames);
        inventoryPage.openCart();

        cartPage.removeItemsFromCartByName(productNames);

        Assert.assertEquals(
            cartPage.getCartItemCount(),
            0,
            "Cart is not empty after removing all items."
        );
    }
}
