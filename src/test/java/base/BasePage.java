package base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class BasePage extends BaseTest {

    protected WebDriver driver;
    protected WebDriverWait wait;

    // ------------------------------------------
    // Constructor & PageFactory Initialization
    // ------------------------------------------
    public BasePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    // ------------------------------------------
    // Wait Helper Methods
    // ------------------------------------------
    protected void waitForVisibility(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }

}
