package darshan;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Plain-Java smoke test (no TestNG, no Page Objects).
 *
 * NOTE: FlipkartTest.java is the recommended primary test — it uses
 * the Page Object classes with the fixed, scoped locators. Keep this
 * file only if you specifically want a single-file script; it has
 * been updated with the same size-selector fix below (the original
 * "contains(text(),'6') or ... '9'" locator could match unrelated
 * numbers on the page, such as prices or ratings).
 */
public class Test {

    public static void main(String[] args) {

        // ChromeDriver path
        System.setProperty(
            "webdriver.chrome.driver",
            "C:\\Automate_testing\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe"
        );

        WebDriver driver = new ChromeDriver();

        WebDriverWait wait = new WebDriverWait(
            driver,
            Duration.ofSeconds(20)
        );

        driver.manage().window().maximize();

        // 1. Open Flipkart
        driver.get("https://www.flipkart.com");

        // ------------------------------------------------
        // 2. LOGIN
        // ------------------------------------------------

        System.out.println("Flipkart opened.");

        /*
         * Login/OTP may be required.
         *
         * If Flipkart shows the login popup, enter your
         * mobile number and complete OTP manually.
         *
         * After login, Selenium will continue.
         */

        System.out.println("Please login to Flipkart if required.");
        System.out.println("Complete OTP/CAPTCHA manually if shown.");
        System.out.println(
            "(This script does manual login only — for automated "
            + "mobile-number login use LoginPage.login() in FlipkartTest.java, "
            + "which scopes the input field correctly.)"
        );

        // Give yourself time to login
        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // ------------------------------------------------
        // 3. SEARCH SHOES
        // ------------------------------------------------

        WebElement searchBox = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.name("q")
            )
        );

        searchBox.sendKeys("shoes");

        searchBox.submit();

        System.out.println("Shoes searched.");

        // ------------------------------------------------
        // 4. SELECT FIRST PRODUCT
        // ------------------------------------------------

        WebElement firstProduct = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath(
                    "(//div[contains(@class,'_1AtVbE')]//a)[1]"
                )
            )
        );

        firstProduct.click();

        System.out.println("Product selected.");

        // ------------------------------------------------
        // 5. SWITCH TO PRODUCT TAB
        // ------------------------------------------------

        String originalWindow = driver.getWindowHandle();

        Set<String> windows = driver.getWindowHandles();

        for (String window : windows) {

            if (!window.equals(originalWindow)) {
                driver.switchTo().window(window);
                break;
            }
        }

        // ------------------------------------------------
        // 6. SELECT SIZE
        // ------------------------------------------------

        try {

            String size = "8";

            // Scoped to size-chip-like elements instead of matching
            // ANY text on the page containing a digit 6-9 (which
            // could hit prices, ratings, review counts, etc.)
            By sizeLocator = By.xpath(
                "//li[normalize-space(.)='" + size + "'] "
                + "| //a[normalize-space(.)='" + size + "'] "
                + "| //div[(@role='button' or contains(@class,'size')) "
                + "and normalize-space(.)='" + size + "']"
            );

            WebElement sizeEl = wait.until(
                ExpectedConditions.elementToBeClickable(sizeLocator)
            );

            try {
                sizeEl.click();
            } catch (Exception clickEx) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", sizeEl);
            }

            System.out.println("Size selected.");

        } catch (Exception e) {

            System.out.println(
                "Size selection was not found. Select the size manually."
            );
        }

        // ------------------------------------------------
        // 7. ADD TO CART
        // ------------------------------------------------

        WebElement addToCart = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath(
                    "//button[contains(translate(., 'abcdefghijklmnopqrstuvwxyz', 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'), 'ADD TO CART')]"
                )
            )
        );

        addToCart.click();

        System.out.println("Product added to cart.");

        // ------------------------------------------------
        // 8. GO TO CART
        // ------------------------------------------------

        WebElement cart = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath(
                    "//*[contains(text(),'Cart')]"
                )
            )
        );

        cart.click();

        System.out.println("Cart opened.");

        // ------------------------------------------------
        // 9. CHECKOUT / PLACE ORDER
        // ------------------------------------------------

        WebElement placeOrder = wait.until(
            ExpectedConditions.elementToBeClickable(
                By.xpath(
                    "//*[contains(text(),'Place Order') or contains(text(),'PLACE ORDER')]"
                )
            )
        );

        placeOrder.click();

        System.out.println("Checkout page opened.");

        // ------------------------------------------------
        // 10. STOP BEFORE PAYMENT
        // ------------------------------------------------

        System.out.println(
            "Reached checkout/payment stage."
        );

        System.out.println(
            "Automation stopped. Payment will NOT be performed."
        );

        /*
         * DO NOT add payment automation.
         *
         * Keep browser open so you can inspect the page.
         */

    }
}