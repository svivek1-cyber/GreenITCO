package object_repository;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import generic_utility.WebDriverUtility;    

public class MicrosoftSignInPage {
    WebDriverUtility wdu;

    public MicrosoftSignInPage(WebDriver driver) {
        PageFactory.initElements(driver, MicrosoftSignInPage.this);
        this.wdu = new WebDriverUtility(driver);
    }

    @FindBy(xpath="//input[@type='email']")
    private WebElement emailField;
    public WebElement getEmailField(){
        return emailField;
    }

    @FindBy(xpath = "//a[@id='cantAccessAccount']")
    private WebElement cantAccessAccountLink;
    public WebElement getCantAccessAccountLink(){
        return cantAccessAccountLink;
    }

    @FindBy(xpath = "//input[@type='submit']")
    private WebElement btnNext;
    public WebElement getBtnNext(){
        return btnNext;
    }

    @FindBy(xpath = "//p[@id='pageDescription']")
    private WebElement pageDescription;
    public WebElement getPageDescription(){
        return pageDescription;
    }

    @FindBy(xpath = "//div[@id='exceptionMessageContainer']")
    private WebElement exceptionMessageContainer;
    public WebElement getExceptionMessageContainer(){
        return exceptionMessageContainer;
    }

    public void enterPassCodeViaRobotClass(){
        try {
            Robot robot = new Robot();
            
            // Optional: Initial delay to give you time to focus the target window
            robot.delay(2000); 

            // Press and Release 'Tab'
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_TAB);

            // 1. Press and Release 'X'
            robot.keyPress(KeyEvent.VK_X);
            robot.keyRelease(KeyEvent.VK_X);
            robot.delay(100); // 100ms short buffer between actions

            // 2. Press and Release '1'
            robot.keyPress(KeyEvent.VK_1);
            robot.keyRelease(KeyEvent.VK_1);
            robot.delay(100);

            // 3. Press and Release '2'
            robot.keyPress(KeyEvent.VK_2);
            robot.keyRelease(KeyEvent.VK_2);
            robot.delay(100);

            // 4. Press and Release '3'
            robot.keyPress(KeyEvent.VK_3);
            robot.keyRelease(KeyEvent.VK_3);

            // 5. Press and Release 'Enter'
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);

        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void tryToLoginWithMSAccount(String email) throws InterruptedException {
        wdu.hardWait(2);
        getEmailField().sendKeys(email + Keys.ENTER);
        wdu.hardWait(5);
        enterPassCodeViaRobotClass();
        wdu.hardWait(5);
        getPageDescription().isDisplayed();
        Reporter.log("Page description: " + getPageDescription().getText());
        getExceptionMessageContainer().isDisplayed();
        Reporter.log("Exception message: " + getExceptionMessageContainer().getText());
    }
    

}
