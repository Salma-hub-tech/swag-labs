import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LogoutPage {
    private WebDriver myBrowser;

    // Locators
    private By usernameField = By.id("user-name");
    private By passwordField = By.id("password");
    private By loginButton = By.id("login-button");
    private By menuButton = By.cssSelector(".bm-burger-button button");
    private By logoutLink = By.cssSelector("a.bm-item.menu-item[href='./index.html']");
    private By confirmLoginButton = By.id("login-button");

    // Constructor
    public LogoutPage(WebDriver myBrowser) {
        this.myBrowser = myBrowser;
    }

    // Actions
    public void login(String username, String password, String url) {
        myBrowser.get(url);
        myBrowser.findElement(usernameField).sendKeys(username);
        myBrowser.findElement(passwordField).sendKeys(password);
        myBrowser.findElement(loginButton).click();
    }

    public void logout() {
        myBrowser.findElement(menuButton).click();
        myBrowser.findElement(logoutLink).click();
    }

    public boolean isLogoutSuccessful() {
        return myBrowser.findElement(confirmLoginButton).isDisplayed();
    }
}
