package object_repository;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

public class LoginPage {
    WebDriver driver;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, LoginPage.this);
        this.driver = driver;
    }
    
    @FindBy(xpath= "//a[normalize-space()='IT Asset Management']")
    private WebElement pageTitle;
    public WebElement pageTitle(){
        return pageTitle;
    }
    
    @FindBy(xpath = "//img[@alt='App Icon']")
    private WebElement appIcon;
    public WebElement appIcon(){
        return appIcon;
    }
    
    @FindBy(xpath = "//img[@alt='Play Store Icon']")
    private WebElement playStoreIcon;
    public WebElement playStoreIcon(){
        return playStoreIcon;
    }
    
    @FindBy(xpath = "//div[@class='img-cover-fittable']//img")
    private WebElement logoImgElement;
    public WebElement logoImg(){
        return logoImgElement;
    }

    @FindBy(xpath="//input[@id='username']")
    private WebElement username;
    public WebElement username(){
        return username;
    }

    @FindBy(xpath="//span[normalize-space()='Remember me']")
    private WebElement rememberMe;
    public WebElement rememberMeCheckbox(){
        return rememberMe;
    }
    
    @FindBy(xpath="//a[normalize-space()='Forgot password?']")
    private WebElement forgotPasswordLink;
    public WebElement forgotPasswordLink(){
        return forgotPasswordLink;
    }
    
    @FindBy(xpath="//button[@id='btnContinue']")
    private WebElement btnContinue;
    public WebElement btnContinue(){
        return btnContinue;
    }
    
    @FindBy(xpath="(//a[@id='btnMsLogin'])[1]")
    private WebElement btnMsLogin;
    public WebElement btnMsLogin(){
        return btnMsLogin;
    }
    
    @FindBy(xpath="//a[contains(text(),'Terms &')]")
    private WebElement linkTermsAndConditions;
    public WebElement linkTermsAndConditions(){
        return linkTermsAndConditions;
    }
    
    @FindBy(xpath="//a[contains(text(),'Contact')]")
    private WebElement linkContact;
    public WebElement linkContact(){
        return linkContact;
    }
    
    @FindBy(xpath="//span[@class='validateUserMsg']")
    private WebElement validateUserMsg;
    public WebElement validateUserMsg(){
        return validateUserMsg;
    }

    public void verifyLoginPageElements() {
        pageTitle().isDisplayed();
        appIcon().isDisplayed();
        playStoreIcon().isDisplayed();
        logoImg().isDisplayed();
        username().isDisplayed();
        rememberMeCheckbox().isDisplayed();
        forgotPasswordLink().isDisplayed();
        btnContinue().isDisplayed();
        btnMsLogin().isDisplayed();
        linkTermsAndConditions().isDisplayed();
        linkContact().isDisplayed();
    }

    public void tryLoginWithInvalidUsername(String username) {
        username().sendKeys(username);
        rememberMeCheckbox().click();
        btnContinue().click();
        validateUserMsg().isDisplayed();
        Reporter.log("Invalid credentials entered: " + validateUserMsg().getText());
    }
}
