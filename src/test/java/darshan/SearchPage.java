package darshan;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public SearchPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(20)
        );
    }

    // Product links on the search results grid
    private By productLinks = By.xpath(
            "//a[contains(@href,'/p/')]"
    );

    public ProductPage selectFirstProduct() {

        System.out.println(
                "Looking for first product..."
        );

        WebElement firstProduct = wait.until(
                ExpectedConditions.elementToBeClickable(
                        productLinks
                )
        );

        String originalWindow = driver.getWindowHandle();

        int windowsBefore = driver.getWindowHandles().size();

        firstProduct.click();

        System.out.println(
                "First product clicked."
        );

        // Flipkart may open the product in a new tab. Wait until
        // either a new window actually appears, or fall back to
        // treating it as same-tab navigation.
        try {

            wait.until(
                    d -> d.getWindowHandles().size() > windowsBefore
            );

            List<String> windows =
                    new ArrayList<>(
                            driver.getWindowHandles()
                    );

            for (String window : windows) {

                if (!window.equals(originalWindow)) {

                    driver.switchTo().window(window);

                    break;
                }
            }

            System.out.println(
                    "Switched to new product tab."
            );

        } catch (Exception e) {

            System.out.println(
                    "Product opened in same tab."
            );
        }

        System.out.println(
                "Product page opened."
        );

        return new ProductPage(driver);
    }
}