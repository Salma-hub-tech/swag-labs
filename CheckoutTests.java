
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CheckoutTests {
    WebDriver driver;
    LoginPage loginPage;
    InventoryPage inventoryPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    String url="https://www.saucedemo.com/v1/index.html";


    @BeforeClass
    public void setUp() {
        driver = new ChromeDriver();
        driver.get(url);
        loginPage = new LoginPage(driver);
        inventoryPage = new InventoryPage(driver);
        cartPage = new CartPage(driver);
        checkoutPage = new CheckoutPage(driver);
    }

    @Test
    public void TC_CHECKOUT_01_validData() {
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addItemToCart();
        inventoryPage.goToCart();
        cartPage.clickCheckout();
        checkoutPage.fillCheckoutForm("Maryam", "Ahmed", "11431");
        checkoutPage.clickContinue();

        String currentUrl = driver.getCurrentUrl();
        Assert.assertTrue(currentUrl.contains("checkout-step-two"));
    }

    @Test
    public void TC_CHECKOUT_02_missingFirstName() {
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addItemToCart();
        inventoryPage.goToCart();
        cartPage.clickCheckout();
        checkoutPage.fillCheckoutForm("", "Ahmed", "11431");
        checkoutPage.clickContinue();

        String errorMessage = checkoutPage.getErrorMessage();
        Assert.assertEquals(errorMessage, "Error: First Name is required");
    }

    @Test
    public void TC_CHECKOUT_05_emptyAllFields() {
        loginPage.login("standard_user", "secret_sauce");
        inventoryPage.addItemToCart();
        inventoryPage.goToCart();
        cartPage.clickCheckout();
        checkoutPage.fillCheckoutForm("", "", "");
        checkoutPage.clickContinue();

        String errorMessage = checkoutPage.getErrorMessage();
        Assert.assertEquals(errorMessage, "Error: First Name is required");
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    private class CartPage {
        public CartPage(WebDriver driver) {
        }

        public void clickCheckout() {
        }
    }
}
