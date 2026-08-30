package darshan;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    public LoginPage(WebDriver driver) {

        this.driver = driver;

        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(20)
        );
    }

    // Top-nav "Login" link — clicking this opens a fresh,
    // predictable login popup instead of relying on Flipkart's
    // auto-popup timing.
    private By loginLink = By.xpath(
            "//a[normalize-space()='Login']"
    );

    // Close button on an auto-opened popup, if one beat us to it
    private By popupCloseButton = By.xpath(
            "//button[contains(@class,'_2KpZ6l') "
            + "and contains(@class,'_2doB4z')]"
    );

    // Mobile/email field.
    //
    // Flipkart's modal likely uses a FLOATING <label> rather than a
    // real placeholder attribute — the label text sits above the
    // input even while it's focused, which a true placeholder never
    // does. So a placeholder-based lookup can silently match nothing.
    // We try several strategies, in order, until one finds a visible
    // element.
    private WebElement locateMobileInput() {

        // Strategy 1: a genuine placeholder attribute, if it exists
        try {

            WebElement el = driver.findElement(By.xpath(
                    "//input[contains("
                    + "translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                    + "'abcdefghijklmnopqrstuvwxyz'),'mobile') "
                    + "or contains("
                    + "translate(@placeholder,'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                    + "'abcdefghijklmnopqrstuvwxyz'),'email')]"
            ));

            if (el.isDisplayed()) {
                return el;
            }

        } catch (Exception ignored) { }

        // Strategy 2: floating <label> pattern — grab the input that
        // immediately follows the "mobile" label text in DOM order
        try {

            WebElement el = driver.findElement(By.xpath(
                    "//*[contains("
                    + "translate(normalize-space(text()),"
                    + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
                    + "'abcdefghijklmnopqrstuvwxyz'),'mobile')]"
                    + "/following::input[1]"
            ));

            if (el.isDisplayed()) {
                return el;
            }

        } catch (Exception ignored) { }

        // Strategy 3: last visible text/tel input in the DOM that
        // is not the search box. Login modals are usually appended
        // to the end of <body>, so the last matching input is a
        // reasonable bet when the other strategies come up empty.
        WebElement el = driver.findElement(By.xpath(
                "(//input[(@type='text' or @type='tel') "
                + "and (not(@name) or @name!='q')])[last()]"
        ));

        return el;
    }

    /**
     * Prints every currently-visible &lt;input&gt; on the page to the
     * console. Run this after a failure so its output can be pasted
     * back for an exact-locator fix, instead of guessing from a
     * screenshot.
     */
    private void logVisibleInputsForDebugging() {

        try {

            String inputsHtml = (String) ((JavascriptExecutor) driver)
                    .executeScript(
                            "return Array.from(document.querySelectorAll('input'))"
                            + ".filter(el => el.offsetParent !== null)"
                            + ".map(el => el.outerHTML).join('\\n---\\n');"
                    );

            System.out.println(
                    "=============================================="
            );

            System.out.println(
                    "VISIBLE <input> ELEMENTS ON PAGE (for debugging):"
            );

            System.out.println(inputsHtml);

            System.out.println(
                    "=============================================="
            );

        } catch (Exception jsEx) {

            System.out.println(
                    "Could not collect visible inputs for debugging."
            );
        }
    }

    // Request OTP button
    private By requestOTP = By.xpath(
            "//button[contains(translate(normalize-space(.),"
            + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
            + "'abcdefghijklmnopqrstuvwxyz'),"
            + "'request otp')]"
    );

    // Login button
    private By loginButton = By.xpath(
            "//button[contains(translate(normalize-space(.),"
            + "'ABCDEFGHIJKLMNOPQRSTUVWXYZ',"
            + "'abcdefghijklmnopqrstuvwxyz'),"
            + "'login')]"
    );

    public void login(String mobile) {

        System.out.println("Opening login section...");

        try {

            // Close any popup that auto-opened before we could act
            try {

                WebElement autoClose = new WebDriverWait(
                        driver,
                        Duration.ofSeconds(3)
                ).until(
                        ExpectedConditions.elementToBeClickable(
                                popupCloseButton
                        )
                );

                try {
                    autoClose.click();
                } catch (Exception clickEx) {
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].click();", autoClose
                    );
                }

            } catch (Exception ignored) {
                // No popup was open — that's fine
            }

            // Explicitly trigger a fresh login popup ourselves,
            // rather than assuming one is already open
            WebElement loginNav = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            loginLink
                    )
            );

            // A normal click() sends real click coordinates, and on
            // Flipkart's nav bar an overlapping <span role="button">
            // wrapper intercepts that click before it reaches the
            // <a> (confirmed by ElementClickInterceptedException in
            // the logs). A JS-dispatched click ignores what's
            // visually on top and fires directly on the target.
            try {

                loginNav.click();

            } catch (Exception clickEx) {

                System.out.println(
                        "Normal click on Login was intercepted, "
                        + "retrying with JS click..."
                );

                ((JavascriptExecutor) driver).executeScript(
                        "arguments[0].click();", loginNav
                );
            }

            System.out.println(
                    "Login popup opened."
            );

            WebElement mobileInput = wait.until(d -> {
                try {
                    WebElement el = locateMobileInput();
                    return el.isDisplayed() ? el : null;
                } catch (Exception e) {
                    return null;
                }
            });

            mobileInput.clear();

            mobileInput.sendKeys(mobile);

            System.out.println(
                    "Mobile number entered."
            );

            // Try Request OTP
            try {

                WebElement otpButton = wait.until(
                        ExpectedConditions.elementToBeClickable(
                                requestOTP
                        )
                );

                try {
                    otpButton.click();
                } catch (Exception clickEx) {
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].click();", otpButton
                    );
                }

                System.out.println(
                        "OTP request sent."
                );

            } catch (Exception e) {

                // If Request OTP isn't present,
                // try Login button
                WebElement loginBtn = wait.until(
                        ExpectedConditions.elementToBeClickable(
                                loginButton
                        )
                );

                try {
                    loginBtn.click();
                } catch (Exception clickEx) {
                    ((JavascriptExecutor) driver).executeScript(
                            "arguments[0].click();", loginBtn
                    );
                }

                System.out.println(
                        "Login button clicked."
                );
            }

        } catch (Exception e) {

            System.out.println(
                    "Login step failed."
            );

            System.out.println(
                    "Current URL: " + driver.getCurrentUrl()
            );

            logVisibleInputsForDebugging();

            e.printStackTrace();

            throw e;
        }
    }
}