package darshan;

import java.time.Duration;
import java.util.Scanner;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Primary automated test suite (TestNG). Uses the Page Object
 * classes, which now handle Flipkart's login popup explicitly and
 * scope element locators so they can't accidentally match unrelated
 * elements (e.g. the search box during login, or a stray "8" on the
 * page during size selection).
 */
public class FlipkartTest {

    WebDriver driver;

    HomePage homePage;
    LoginPage loginPage;
    SearchPage searchPage;
    ProductPage productPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;

    Scanner scanner;

    // =====================================================
    // SETUP
    // =====================================================

    @BeforeMethod
    public void setUp() {

        System.setProperty(
                "webdriver.chrome.driver",
                "C:\\Automate_testing\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe"
        );

        driver = new ChromeDriver();

        driver.manage()
              .window()
              .maximize();

        driver.manage()
              .timeouts()
              .pageLoadTimeout(
                      Duration.ofSeconds(30)
              );

        homePage = new HomePage(driver);

        loginPage = new LoginPage(driver);

        scanner = new Scanner(System.in);
    }

    // =====================================================
    // TEST
    // =====================================================

    @Test
    public void buyShoesUntilPaymentPage() {

        // =================================================
        // STEP 1
        // =================================================

        System.out.println();
        System.out.println(
                "STEP 1: Opening Flipkart..."
        );

        homePage.openFlipkart();


        // =================================================
        // STEP 2
        // =================================================

        System.out.println();
        System.out.println(
                "STEP 2: Login..."
        );

        // Put your mobile number here
        loginPage.login("9730795604");

        System.out.println();
        System.out.println(
                "=============================================="
        );

        System.out.println(
                "OTP has been requested."
        );

        System.out.println(
                "Enter OTP manually on Flipkart."
        );

        System.out.println(
                "Complete CAPTCHA if required."
        );

        System.out.println(
                "After successful login, press ENTER"
        );

        System.out.println(
                "once in the Eclipse Console."
        );

        System.out.println(
                "=============================================="
        );

        // Wait for manual OTP/CAPTCHA
        scanner.nextLine();

        System.out.println(
                "Login completed."
        );


        // =================================================
        // STEP 3
        // =================================================

        System.out.println();
        System.out.println(
                "STEP 3: Searching for shoes..."
        );

        searchPage =
                homePage.searchProduct(
                        "shoes"
                );


        // =================================================
        // STEP 4
        // =================================================

        System.out.println();
        System.out.println(
                "STEP 4: Selecting first shoe..."
        );

        productPage =
                searchPage.selectFirstProduct();


        // =================================================
        // STEP 5
        // =================================================

        System.out.println();
        System.out.println(
                "STEP 5: Selecting shoe size..."
        );

        productPage.selectSize(
                "8"
        );


        // =================================================
        // STEP 6
        // =================================================

        System.out.println();
        System.out.println(
                "STEP 6: Adding shoe to cart..."
        );

        productPage.addToCart();


        // =================================================
        // STEP 7
        // =================================================

        System.out.println();
        System.out.println(
                "STEP 7: Opening cart..."
        );

        cartPage =
                productPage.goToCart();


        // =================================================
        // STEP 8
        // =================================================

        System.out.println();
        System.out.println(
                "STEP 8: Proceeding to checkout..."
        );

        checkoutPage =
                cartPage.proceedToCheckout();


        // =================================================
        // STEP 9
        // =================================================

        System.out.println();
        System.out.println(
                "STEP 9: Waiting for checkout/payment page..."
        );

        checkoutPage.waitForCheckoutPage();


        // =================================================
        // STOP BEFORE PAYMENT
        // =================================================

        System.out.println();
        System.out.println(
                "**********************************************"
        );

        System.out.println(
                "AUTOMATION COMPLETED"
        );

        System.out.println(
                "Reached checkout/payment stage."
        );

        System.out.println(
                "NO PAYMENT WAS PERFORMED."
        );

        System.out.println(
                "**********************************************"
        );

        System.out.println();
        System.out.println(
                "Browser will remain open."
        );

        System.out.println(
                "Press ENTER to close browser."
        );

        scanner.nextLine();
    }

    // =====================================================
    // TEARDOWN
    // =====================================================

    @AfterMethod
    public void tearDown() {

        if (scanner != null) {
            scanner.close();
        }

        if (driver != null) {
            driver.quit();
        }
    }
}