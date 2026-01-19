package base;

import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import io.github.bonigarcia.wdm.WebDriverManager;
import pages.CartPage;
import pages.CheckoutCompletePage;
import pages.CheckoutInfoPage;
import pages.CheckoutOverviewPage;
import pages.InventoryPage;
import pages.LoginPage;
import utils.ConfigReader;

public class BaseTest {

    // ------------------------------------------
    // WebDriver & Utilities
    // ------------------------------------------
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;
    protected Logger log = LogManager.getLogger(this.getClass());

    // ------------------------------------------
    // Page Objects
    // ------------------------------------------
    protected LoginPage loginPage;
    protected InventoryPage inventoryPage;
    protected CartPage cartPage;
    protected CheckoutInfoPage checkoutInfoPage;
    protected CheckoutOverviewPage checkoutOverviewPage;
    protected CheckoutCompletePage checkoutCompletePage;

    // ------------------------------------------
    // Test Lifecycle Methods
    // ------------------------------------------
    @BeforeMethod
    public void setup() {

        log.info("========== TEST SETUP STARTED ==========");

        // ------------------------------------------
        // Configuration Values
        // ------------------------------------------
        String browser = ConfigReader.getKey("browser");
        String baseUrl = ConfigReader.getKey("baseUrl");
        boolean headless = ConfigReader.getBoolean("headless");
        boolean maximize = ConfigReader.getBoolean("maximize");
        int zoom = ConfigReader.getInt("zoom");
        int explicitWait = ConfigReader.getInt("explicitWait");

        // ------------------------------------------
        // WebDriver Initialization
        // ------------------------------------------
        switch (browser.toLowerCase()) {

            case "chrome":
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                if (headless) {
                    chromeOptions.addArguments("--headless=new");
                }
                chromeOptions.addArguments("--guest");
                driver = new ChromeDriver(chromeOptions);
                break;

            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                if (headless) {
                    firefoxOptions.addArguments("--headless=new");
                }
                driver = new FirefoxDriver(firefoxOptions);
                break;

            case "edge":
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                if (headless) {
                    edgeOptions.addArguments("--headless=new");
                }
                driver = new EdgeDriver(edgeOptions);
                break;

            default:
                throw new RuntimeException("Unsupported browser: " + browser);
        }

        log.info("Launching browser: {}", browser);

        // ------------------------------------------
        // Browser Configuration
        // ------------------------------------------
        driver.get(baseUrl);
        log.info("Navigating to url: {}", baseUrl);

        if (maximize) {
            driver.manage().window().maximize();
        }

        js = (JavascriptExecutor) driver;
        if (zoom != 100) {
            js.executeScript("document.body.style.zoom='" + zoom + "%';");
        }

        // ------------------------------------------
        // Wait Initialization
        // ------------------------------------------
        wait = new WebDriverWait(driver, Duration.ofSeconds(explicitWait));

        // ------------------------------------------
        // Page Object Initialization
        // ------------------------------------------
        loginPage = new LoginPage(driver, wait);
        inventoryPage = new InventoryPage(driver, wait);
        cartPage = new CartPage(driver, wait);
        checkoutInfoPage = new CheckoutInfoPage(driver, wait);
        checkoutOverviewPage = new CheckoutOverviewPage(driver, wait);
        checkoutCompletePage = new CheckoutCompletePage(driver, wait);
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            log.info("Closing browser");
            driver.quit();
        }
    }

}
