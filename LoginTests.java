import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;
import java.time.Duration;

public class LoginTests {
    WebDriver driver;
    LoginPage loginPage;
    String url = "https://www.saucedemo.com/v1/index.html"; // Keep original URL
    WebDriverWait wait;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15)); // Increased wait for performance_glitch_user
    }

    @DataProvider(name = "loginData")
    public Object[][] loginDataProvider() {
        return new Object[][]{
                {"standard_user", "secret_sauce", true},
                {"locked_out_user", "secret_sauce", false},
                {"problem_user", "secret_sauce", true},
                {"performance_glitch_user", "secret_sauce", true},
                {"", "", false},
                {"standard_user", "wrong_pass", false},
                {"invalid_user", "secret_sauce", false}
        };
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String username, String password, boolean expectedSuccess) {
        driver.get(url);
        loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        if (expectedSuccess) {
            // Extra handling for performance_glitch_user
            if ("performance_glitch_user".equals(username)) {
                try {
                    Thread.sleep(5000); // Explicit delay for slow login
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            Assert.assertTrue(
                    loginPage.isLoginSuccessful(),
                    "Login should succeed for: " + username
            );
        } else {
            Assert.assertTrue(
                    loginPage.isErrorDisplayed(),
                    "Login should fail for: " + username
            );
        }
    }

    @AfterMethod
    public void cleanup() {
        driver.manage().deleteAllCookies();
        driver.navigate().refresh();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}