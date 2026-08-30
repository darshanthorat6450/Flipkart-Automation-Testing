package darshan;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public CartPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(20)
        );
    }

    public CheckoutPage proceedToCheckout() {

        System.out.println(
                "Looking for checkout button..."
        );

        WebElement button;

        try {

            // Place Order
            By placeOrder = By.xpath(
                    "//button[contains("
                    + "translate(normalize-space(.),"
                    + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                    + "'abcdefghijklmnopqrstuvwxyz'),"
                    + "'place order')]"
            );

            button = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            placeOrder
                    )
            );

        } catch (Exception e) {

            // Alternative checkout button
            By checkout = By.xpath(
                    "//*[contains("
                    + "translate(normalize-space(.),"
                    + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                    + "'abcdefghijklmnopqrstuvwxyz'),"
                    + "'proceed to checkout')]"
            );

            button = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            checkout
                    )
            );
        }

        try {

            button.click();

        } catch (Exception e) {

            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].click();",
                            button
                    );
        }

        System.out.println(
                "Proceeding to checkout..."
        );

        return new CheckoutPage(driver);
    }
}