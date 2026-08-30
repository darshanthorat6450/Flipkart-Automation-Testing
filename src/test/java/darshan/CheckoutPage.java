package darshan;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckoutPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public CheckoutPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(30)
        );
    }

    public void waitForCheckoutPage() {

        System.out.println(
                "Waiting for checkout/payment page..."
        );

        try {

            wait.until(
                    ExpectedConditions.or(

                            // Checkout URL
                            ExpectedConditions.urlContains(
                                    "checkout"
                            ),

                            // Payment text
                            ExpectedConditions
                                    .presenceOfElementLocated(
                                            By.xpath(
                                                    "//*[contains("
                                                    + "translate(normalize-space(.),"
                                                    + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                                                    + "'abcdefghijklmnopqrstuvwxyz'),"
                                                    + "'payment')]"
                                            )
                                    ),

                            // Address text
                            ExpectedConditions
                                    .presenceOfElementLocated(
                                            By.xpath(
                                                    "//*[contains("
                                                    + "translate(normalize-space(.),"
                                                    + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                                                    + "'abcdefghijklmnopqrstuvwxyz'),"
                                                    + "'delivery address')]"
                                            )
                                    )
                    )
            );

            System.out.println(
                    "=============================================="
            );

            System.out.println(
                    "CHECKOUT/PAYMENT PAGE REACHED"
            );

            System.out.println(
                    "NO PAYMENT WILL BE PERFORMED"
            );

            System.out.println(
                    "=============================================="
            );

        } catch (Exception e) {

            System.out.println(
                    "Could not verify checkout page."
            );

            System.out.println(
                    "Current URL: "
                    + driver.getCurrentUrl()
            );

            e.printStackTrace();
        }
    }
}