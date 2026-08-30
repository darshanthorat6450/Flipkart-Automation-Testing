package darshan;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProductPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public ProductPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(20)
        );
    }

    // =====================================================
    // SELECT SIZE
    // =====================================================

    public void selectSize(String size) {

        System.out.println(
                "Looking for size: " + size
        );

        try {

            // IMPORTANT FIX: a bare //*[normalize-space(.)='8']
            // matches ANY element on the page with that exact text
            // (rating counts, prices, review counts, etc). Scope it
            // to elements that actually behave like size chips:
            // list items, links, or role="button" containers.
            By sizeLocator = By.xpath(
                    "//li[normalize-space(.)='" + size + "'] "
                    + "| //a[normalize-space(.)='" + size + "'] "
                    + "| //div[(@role='button' or contains(@class,'size')) "
                    + "and normalize-space(.)='" + size + "']"
            );

            WebElement sizeElement = wait.until(
                    ExpectedConditions.presenceOfElementLocated(
                            sizeLocator
                    )
            );

            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].scrollIntoView({block:'center'});",
                            sizeElement
                    );

            wait.until(
                    ExpectedConditions.elementToBeClickable(
                            sizeElement
                    )
            );

            try {

                sizeElement.click();

            } catch (Exception clickEx) {

                // Some size chips sit under a sticky header/overlay
                // and reject a normal click — fall back to JS click
                ((JavascriptExecutor) driver)
                        .executeScript(
                                "arguments[0].click();",
                                sizeElement
                        );
            }

            System.out.println(
                    "Selected size: " + size
            );

        } catch (Exception e) {

            System.out.println(
                    "Could not automatically select size: "
                    + size
            );

            System.out.println(
                    "Current URL: "
                    + driver.getCurrentUrl()
            );

            e.printStackTrace();

            throw e;
        }
    }

    // =====================================================
    // ADD TO CART
    // =====================================================

    public void addToCart() {

        System.out.println(
                "Looking for Add to Cart button..."
        );

        By addToCartButton = By.xpath(
                "//button[contains("
                + "translate(normalize-space(.),"
                + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                + "'abcdefghijklmnopqrstuvwxyz'),"
                + "'add to cart')]"
        );

        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(
                        addToCartButton
                )
        );

        ((JavascriptExecutor) driver)
                .executeScript(
                        "arguments[0].scrollIntoView({block:'center'});",
                        button
                );

        try {

            button.click();

        } catch (Exception e) {

            // Fallback for when a sticky header/overlay
            // intercepts the click
            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].click();",
                            button
                    );
        }

        System.out.println(
                "Product added to cart."
        );
    }

    // =====================================================
    // GO TO CART
    // =====================================================

    public CartPage goToCart() {

        System.out.println(
                "Opening cart..."
        );

        By cartButton = By.xpath(
                "//*[normalize-space(.)='Cart']"
        );

        WebElement cart = wait.until(
                ExpectedConditions.elementToBeClickable(
                        cartButton
                )
        );

        try {

            cart.click();

        } catch (Exception e) {

            ((JavascriptExecutor) driver)
                    .executeScript(
                            "arguments[0].click();",
                            cart
                    );
        }

        System.out.println(
                "Cart opened."
        );

        return new CartPage(driver);
    }
}