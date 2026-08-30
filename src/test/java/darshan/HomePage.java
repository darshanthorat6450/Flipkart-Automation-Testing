package darshan;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private WebDriver driver;
    private WebDriverWait wait;

    public HomePage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(20)
        );
    }

    // Flipkart search box
    private By searchBox = By.name("q");

    // The "X" close button on Flipkart's auto login popup.
    // If this popup is left open it can intercept clicks on
    // the search box, nav links, and other elements.
    private By popupCloseButton = By.xpath(
            "//button[contains(@class,'_2KpZ6l') "
            + "and contains(@class,'_2doB4z')]"
    );

    public void openFlipkart() {

        driver.get("https://www.flipkart.com");

        closeLoginPopupIfPresent();

        wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        searchBox
                )
        );

        System.out.println(
                "Flipkart opened."
        );
    }

    /**
     * Flipkart frequently shows a login popup automatically a
     * couple of seconds after the homepage loads. Close it (if
     * present) before interacting with anything else, otherwise
     * it can intercept subsequent clicks.
     */
    public void closeLoginPopupIfPresent() {

        try {

            WebElement closeBtn = new WebDriverWait(
                    driver,
                    Duration.ofSeconds(5)
            ).until(
                    ExpectedConditions.elementToBeClickable(
                            popupCloseButton
                    )
            );

            closeBtn.click();

            System.out.println(
                    "Closed auto login popup."
            );

        } catch (Exception e) {

            System.out.println(
                    "No auto login popup appeared."
            );
        }
    }

    public SearchPage searchProduct(String product) {

        System.out.println(
                "Searching for: " + product
        );

        // In case a popup snuck open again just before searching
        closeLoginPopupIfPresent();

        WebElement search = wait.until(
                ExpectedConditions.elementToBeClickable(
                        searchBox
                )
        );

        search.click();

        search.clear();

        search.sendKeys(product);

        System.out.println(
                "Entered search text: " + product
        );

        search.sendKeys(Keys.ENTER);

        System.out.println(
                "Search submitted."
        );

        // Give search results time to load
        wait.until(
                ExpectedConditions.or(
                        ExpectedConditions.urlContains("search"),
                        ExpectedConditions.presenceOfElementLocated(
                                By.xpath("//a[contains(@href,'/p/')]")
                        )
                )
        );

        System.out.println(
                "Search results loaded."
        );

        return new SearchPage(driver);
    }
}