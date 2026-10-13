package generic_utility;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This class contains reusable WebDriver utility methods such as window
 * management, waits, dropdown selections, mouse and keyboard interactions.
 * Italso includes additional utilities such as alerts, scrolling, screenshot,
 * JS execution, drag-drop, sliders, and more.
 * 
 * @version 1.0
 * 
 * @author Saurabh Kushwaha
 * 
 * @since 2026-02-18
 * 
 *        List of Utility Methods Available in WebDriverUtility Class
 *
 *        1. browserDriver(String browser): Launches a browser instance based on
 *        the provided browser name.
 *        2. getFullScreen(): Maximizes the browser window to full screen.
 *        3. getMaximize(): Maximizes the browser window.
 *
 *        4. globalWait(int seconds): Applies implicit wait for the specified
 *        number of seconds.
 *        5. globalWait(): Applies implicit wait with a default timeout of 15
 *        seconds.
 *        6. waitUntilClickable(WebElement element): Waits until the given
 *        element becomes clickable.
 *        7. waitForElementToBeVisible(WebElement element): Waits until the
 *        given element becomes visible.
 *        8. smartWait(WebElement element, int timeout, int pollingTime,
 *        Exception exceptionToIgnore): Performs FluentWait with specified
 *        timeout, polling time, and ignored exception.
 *        9. hardWait(int seconds): Pauses execution using Thread.sleep() for
 *        the specified seconds (not recommended for real automation).
 *
 *        10. hover(WebElement element): Performs mouse hover on the given
 *        element.
 *        11. rightClick(WebElement element): Performs right-click (context
 *        click) on the given element.
 *        12. click(WebElement element): Performs left-click on the given
 *        element.
 *        13. doubleClick(WebElement element): Performs double-click on the
 *        given element.
 *        14. clickAndHold(WebElement element): Performs click and hold on the
 *        given element.
 *
 *        15. scrollTop(): Scrolls to the top of the webpage.
 *        16. scrollBottom(): Scrolls to the bottom of the webpage.
 *        17. scrollUntil(WebElement element): Scrolls the page until the
 *        specified element becomes visible.
 *        18. scrollTo(int x, int y): Scrolls the page to the specified X and Y
 *        coordinates.
 *
 *        19. jsClick(WebElement element): Performs click action on the given
 *        element using JavaScript.
 *        20. jsSetValue(WebElement element, String value): Sets the value of
 *        the given element using JavaScript.
 *
 *        21. pageScreenshot(String path): Captures a screenshot of the current
 *        browser window and saves it to the specified path.
 *        22. elementScreenshot(String path, WebElement element): Captures a
 *        screenshot of the specified WebElement and saves it to the specified
 *        path.
 *
 *        23. switchWindowByTitle(String title): Switches to a browser window
 *        based on the window title.
 *        24. switchWindowById(String hId): Switches to a newly opened child
 *        window using window handle.
 *        25. switchWindowByUrl(String url): Switches to a browser window based
 *        on the window URL.
 *        26. switchToParent(String homeId): Switches back to the parent window
 *        using the provided window handle.
 *        27. closeAllChildWin(String homeId): Closes all child windows except
 *        the parent window.
 *
 *        28. selectFromDDusingVisibleText(WebElement dropdown, String option):
 *        Selects an option from dropdown using visible text.
 *        29. selectFromDDusingValue(WebElement dropdown, String option):
 *        Selects an option from dropdown using value.
 *        30. selectFromDDusing(WebElement dropdown, int option): Selects an
 *        option from dropdown using index.
 * 
 */

public class WebDriverUtility {

    WebDriver driver;
    Actions actions;
    JavascriptExecutor jse;
    TakesScreenshot tks;
    WebDriverWait wait;
    FluentWait<WebDriver> fluentWait;

    public WebDriverUtility(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
        this.jse = (JavascriptExecutor) driver;
        this.tks = (TakesScreenshot) driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.fluentWait = new FluentWait<>(driver);
    }

    /** <-------------BROWSER INITIALIZATION-------------> */

    /**
     * Launches a browser instance.
     * 
     * @param browser The name of the browser to launch
     * @return The WebDriver instance for the specified browser
     */
    public static WebDriver browserDriver(String browser) {
        WebDriver driverX;
        switch (browser.toLowerCase()) {
            case "chrome":
                ChromeOptions options = new ChromeOptions();

                Map<String, Object> prefs = new HashMap<>();

                // Disable notifications
                prefs.put("profile.default_content_setting_values.notifications", 2);

                // Disable password manager
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);

                // Disable password leak detection
                prefs.put("profile.password_manager_leak_detection", false);

                // Disable autofill
                prefs.put("autofill.profile_enabled", false);
                prefs.put("autofill.credit_card_enabled", false);

                options.setExperimentalOption("prefs", prefs);

                // Browser arguments
                options.addArguments("--disable-notifications");
                options.addArguments("--disable-popup-blocking");
                options.addArguments("--disable-save-password-bubble");
                // Disable permission prompts
                options.addArguments("--disable-features=PermissionsPrompt");

                // Disable media router
                options.addArguments("--disable-features=MediaRouter");

                // Disable automation info bar
                options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });

                // Disable features causing Google security popups
                options.addArguments("--disable-features=PasswordLeakDetection");
                options.addArguments("--disable-features=PasswordCheck");
                options.addArguments("--disable-features=SafetyTipUI");

                driverX = new ChromeDriver(options);
                break;
            case "firefox":
                driverX = new FirefoxDriver();
                break;
            case "safari":
                driverX = new SafariDriver();
                break;
            case "edge":
                driverX = new EdgeDriver();
                break;
            default:
                System.err.println("Invalid browser name!!! Launching Chrome as default browser....");
                driverX = new ChromeDriver();
                break;
        }
        return driverX;
    }

    /** <-------------OPERATION ON WINDOWS-------------> */

    /**
     * Maximizes the browser window to full screen.
     */
    public void getFullScreen() {
        driver.manage().window().fullscreen();
    }

    /**
     * Maximizes the browser window.
     */
    public void getMaximize() {
        driver.manage().window().maximize();
    }

    /** <------------------WAITS------------------> */

    /**
     * Waits for the given element to be clickable.
     * 
     * @param seconds The number of seconds to wait
     */
    public void globalWait(int seconds) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
    }

    /**
     * Waits for the given element to be clickable with a default timeout of 15
     * seconds.
     */
    public void globalWait() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
    }

    /**
     * Waits until the given element is clickable.
     * 
     * @param element The target WebElement
     */
    public WebElement waitUntilClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
        return element;
    }

    /**
     * Waits until the given element is clickable.
     * 
     * @param element The target WebElement
     */
    public WebElement waitUntilClickablein(int sec, WebElement element) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(sec));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        return element;
    }

    /**
     * Waits until the given element is visible.
     * 
     * @param element The target WebElement
     */
    public WebElement waitForElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element;
    }

    public WebElement waitForElementToBeVisibleAndClickable(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
        return element;
    }

    /**
     * Waits until the given element is visible.
     * 
     * @param element The target WebElement
     */
    public WebElement waitForElementToBeInvisible(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
        return element;
    }

    /**
     * Waits for the given element to be clickable with a specified timeout and
     * polling time, ignoring the specified exception.
     * 
     * @param element           The target WebElement
     * @param timeout           The maximum time to wait in seconds
     * @param pollingTime       The interval between checks in seconds
     * @param exceptionToIgnore The exception to ignore while waiting
     * @return The WebElement once it is clickable
     */
    public WebElement smartWait(WebElement element, int timeout, int pollingTime, Exception exceptionToIgnore) {
        fluentWait
                .withTimeout(Duration.ofSeconds(timeout))
                .pollingEvery(Duration.ofSeconds(pollingTime))
                .ignoring(exceptionToIgnore.getClass());
        fluentWait.until(ExpectedConditions.elementToBeClickable(element));
        return element;
    }

    /**
     * waits for the specified number of seconds.
     * 
     * Only use this method for debugging purposes or when you have no other option.
     * It is not recommended for use in real test automation scenarios due to its
     * drawbacks.
     * 
     * HardCoded wait using Thread.sleep()
     * Note: This method is not recommended for use in real test automation
     * scenarios.
     * Consider using explicit or fluent waits instead for better reliability and
     * performance.
     * 
     * @param seconds
     * @throws InterruptedException
     */
    public void hardWait(int seconds) throws InterruptedException {
        Thread.sleep(seconds * 1000);
    }

    /** <---------------------ACTIONS---------------------> */

    /**
     * Performs mouse hover on the given element.
     * 
     * @param element The target WebElement
     */
    public void hover(WebElement element) {
        actions.moveToElement(element).perform();
    }

    /**
     * Performs mouse hover on the given element.
     * 
     * @param element The target WebElement
     * @param x       The x coordinate to move to
     * @param y       The y coordinate to move to
     * 
     * @return The WebElement after performing hover action
     */
    public WebElement hover(WebElement element, int x, int y) {
        actions.moveToElement(element, x, y).perform();
        return element;
    }

    /**
     * Performs right-click (context click) on the given element.
     * 
     * @param element The target WebElement
     */
    public void rightClick(WebElement element) {
        actions.contextClick(element).perform();
    }

    /**
     * Performs left-click on the given element.
     * 
     * @param element The target WebElement
     */
    public void click(WebElement element) {
        actions.click(element).perform();
    }

    /**
     * Performs double-click on the given element.
     * 
     * @param element The target WebElement
     */
    public void doubleClick(WebElement element) {
        actions.doubleClick(element).perform();
    }

    /**
     * Performs click and hold on the given element.
     * 
     * @param element The target WebElement
     */
    public void clickAndHold(WebElement element) {
        actions.clickAndHold(element).perform();
    }

    /**
     * Performs drag and drop on the given elements.
     * 
     * @param source The source WebElement
     * @param target The target WebElement
     */
    public void dragAndDrop(WebElement source, WebElement target) {
        actions.dragAndDrop(source, target).perform();
    }

    /** <---------------------SCROLL---------------------> */

    // /**
    // * Performs scroll to the top of the page.
    // */
    // public void scrollTop() {
    // jse.executeScript("window.scrollTo(0, 0);");
    // }

    // /**
    // * Performs scroll to the bottom of the page.
    // */
    // public void scrollBottom() {
    // jse.executeScript("window.scrollTo(0, document.body.scrollHeight);");
    // }

    // /**
    // * Performs scroll until the given element is visible.
    // *
    // * @param element The target WebElement
    // */
    // public void scrollUntil(WebElement element) {
    // jse.executeScript("arguments[0].scrollIntoView(true)", element);
    // }

    // /**
    // * Performs scroll to the given x and y coordinates.
    // *
    // * @param x The x coordinate to scroll to
    // * @param y The y coordinate to scroll to
    // */
    // public void scrollTo(int x, int y) {
    // jse.executeScript("window.scrollTo(arguments[0], arguments[1]);", x, y);
    // }

    /**
     * Performs scroll to the top of the page.
     */
    public void scrollTop() {
        jse.executeScript(
                "window.scrollTo({top: 0, behavior: 'smooth'});");
    }

    /**
     * Performs scroll to the bottom of the page.
     */
    public void scrollBottom() {
        jse.executeScript(
                "window.scrollTo({top: document.body.scrollHeight, behavior: 'smooth'});");
    }

    /**
     * Performs scroll until the given element is visible.
     *
     * @param element The target WebElement
     */
    public void scrollUntil(WebElement element) {
        jse.executeScript(
                "arguments[0].scrollIntoView({behavior: 'smooth', block: 'center'});",
                element);
    }

    /**
     * Performs scroll to the given x and y coordinates.
     *
     * @param x The x coordinate to scroll to
     * @param y The y coordinate to scroll to
     */
    public void scrollTo(int x, int y) {
        jse.executeScript(
                "window.scrollTo({left: arguments[0], top: arguments[1], behavior: 'smooth'});",
                x, y);
    }

    /** <---------------------JS UTILITIES---------------------> */

    /**
     * Performs a click action on the given element using JavaScript.
     * 
     * @param element The target WebElement
     */
    public void jsClick(WebElement element) {
        jse.executeScript("arguments[0].click();", element);
    }

    public void jsElementValueManupulation(WebElement element,String attributeName, String value) {
        jse.executeScript(
    "arguments[0].setAttribute(arguments[1], arguments[2]);", element, attributeName, value);
    }

    /**
     * Sets the value of the given element using JavaScript.
     * 
     * @param element The target WebElement
     * @param value   The value to set
     */
    public void jsSetValue(WebElement element, String value) {
        jse.executeScript("arguments[0].value = arguments[1];", element, value);
    }

    /** <---------------------SCREENSHOT---------------------> */

    /**
     * Takes a screenshot of the current browser window and saves it to the
     * specified path.
     * 
     * @param path The file path where the screenshot will be saved
     * @throws IOException
     */
    public void pageScreenshot(String path) throws IOException {
        File dest = new File(path);
        File src = tks.getScreenshotAs(OutputType.FILE);
        FileHandler.copy(src, dest);
    }

    /**
     * Takes a screenshot of the specified WebElement and saves it to the specified
     * path.
     * 
     * @param path    The file path where the screenshot will be saved
     * @param element The target WebElement to capture
     * @throws IOException
     */
    public void elementScreenshot(String path, WebElement element) throws IOException {
        File dest = new File(path);
        File src = element.getScreenshotAs(OutputType.FILE);
        FileHandler.copy(src, dest);
    }

    /** <----------------WINDOWS HANDLING----------------> */

    /**
     * Switches to a browser window based on the window title.
     * 
     * @param title The title of the window to switch to
     */
    public void switchWindowByTitle(String title) {
        Set<String> ids = driver.getWindowHandles();
        for (String id : ids) {
            driver.switchTo().window(id);
            if (driver.getTitle().contains(title)) {
                driver.manage().window().maximize();
                break;
            }
        }
    }

    /**
     * Switches to a browser window based on the window id.
     * recommended to use when only 1 newly child window is open.
     * 
     * @param title The title of the window to switch to
     */
    public void switchWindowById(String hId) {
        Set<String> allId = driver.getWindowHandles();
        for (String id : allId) {
            if (!id.equals(hId)) {
                driver.switchTo().window(id);
                break;
            }
        }
    }

    /**
     * Switches to a browser window based on the window URL.
     * 
     * @param url The URL of the window to switch to
     */
    public void switchWindowByUrl(String url) {
        Set<String> ids = driver.getWindowHandles();
        for (String id : ids) {
            driver.switchTo().window(id);
            if (driver.getCurrentUrl().equals(url)) {
                driver.manage().window().maximize();
                break;
            }
        }
    }

    /**
     * Switches back to the parent window using the provided window handle.
     * 
     * @param homeId The window handle of the parent window
     */
    public void switchToParent(String homeId) {
        driver.switchTo().window(homeId);
    }

    /**
     * Closes all child windows except the parent window using the provided window
     * handle.
     * 
     * @param homeId The window handle of the parent window
     */
    public void closeAllChildWin(String homeId) {
        Set<String> ids = driver.getWindowHandles();
        for (String id : ids) {
            if (!id.equals(homeId)) {
                driver.switchTo().window(id);
                driver.close();
            }
        }
    }

    /** <------------------Selection------------------/> */

    /**
     * Selects an option from a dropdown using visible text.
     * 
     * @param dropdown
     * @param option
     */
    public void selectFromDDusingVisibleText(WebElement dropdown, String option) {
        Select select = new Select(dropdown);
        select.selectByVisibleText(option);
    }

    /**
     * Selects an option from a dropdown using value.
     * 
     * @param dropdown
     * @param option
     */
    public void selectFromDDusingValue(WebElement dropdown, String option) {
        Select select = new Select(dropdown);
        select.selectByValue(option);
    }

    /**
     * Selects an option from a dropdown using index.
     * 
     * @param dropdown
     * @param option
     */
    public void selectFromDDusing(WebElement dropdown, int option) {
        Select select = new Select(dropdown);
        select.selectByIndex(option);
    }

    /**
     * Clicks on element using coordinates (x, y) from element's location and size.
     * This method bypasses overlay interception by directly clicking at element
     * coordinates.
     * 
     * @param element The WebElement to click
     */
    public void clickUsingCoordinates(WebElement element) {
        try {
            org.openqa.selenium.Point location = element.getLocation();
            org.openqa.selenium.Dimension size = element.getSize();

            // Calculate center of element
            int x = location.getX() + (size.getWidth() / 2);
            int y = location.getY() + (size.getHeight() / 2);

            // Move to element center and click
            actions.moveByOffset(x, y).click().perform();

            System.out.println("Clicked at coordinates: (" + x + ", " + y + ")");
        } catch (Exception e) {
            System.out.println("Error clicking using coordinates: " + e.getMessage());
            // Fallback to regular click
            element.click();
        }
    }

}