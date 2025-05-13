import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class Logout {
    WebDriver myBrowser;
    WebDriver loginDriver;
    String username1 = "standard_user";
    String username2 = "locked_out_user";
    String username3="problem_user";
    String username4="performance_glitch_user";
    String password="secret_sauce";
    String url="https://www.saucedemo.com/v1/index.html";

    @BeforeClass
    public void start() {
        myBrowser = new ChromeDriver();
        myBrowser.get(url);
        myBrowser.manage().window().maximize();
        myBrowser.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @Test(testName = "Logout via menu", priority = 1)
    public void logout() {
        LogoutPage logoutPage = new LogoutPage(myBrowser);
        logoutPage.login(username1, password, url);
        logoutPage.logout();
        Assert.assertTrue(logoutPage.isLogoutSuccessful(), "Logout was not successful.");
    }


    @AfterClass
    public void tearDown() {
        myBrowser.quit();
    }
}