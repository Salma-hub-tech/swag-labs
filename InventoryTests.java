//public class InventoryTests {}
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;

public class InventoryTests {
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

    @Test(testName ="Add items", priority=1)
    public void additems(){
        myBrowser.get(url);
        myBrowser.findElement(By.id("user-name")).sendKeys(username1);
        myBrowser.findElement(By.id("password")).sendKeys(password);
        myBrowser.findElement(By.id("login-button")).click();
        // Wait for products to load
        WebDriverWait wait = new WebDriverWait(myBrowser, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));

        // Add item 1
        myBrowser.findElement(By.xpath("(//button[text()='ADD TO CART'])[1]")).click();

        // Go to cart and verify 1 item
        myBrowser.findElement(By.className("shopping_cart_link")).click();
        int itemCount = myBrowser.findElements(By.className("cart_item")).size();
    }

    @Test(testName = "Remove item 6 from cart", priority = 2)
    public void removeItem6FromCart() {
        // Login
        myBrowser.get(url);
        myBrowser.findElement(By.id("user-name")).sendKeys(username1);
        myBrowser.findElement(By.id("password")).sendKeys(password);
        myBrowser.findElement(By.id("login-button")).click();

        // Wait for products to load
        WebDriverWait wait = new WebDriverWait(myBrowser, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("inventory_list")));

        // Add item 6
        myBrowser.findElement(By.xpath("(//button[text()='ADD TO CART'])[6]")).click();

        // Go to cart
        myBrowser.findElement(By.className("shopping_cart_link")).click();

        // Remove item 6
        myBrowser.findElement(By.xpath("//button[text()='REMOVE']")).click();

        // Verify cart is empty
        int itemCount = myBrowser.findElements(By.className("cart_item")).size();
    }

    @AfterClass
    public void tearDown() {
        myBrowser.quit();
    }
}