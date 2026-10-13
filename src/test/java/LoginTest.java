
import java.io.IOException;

import org.openqa.selenium.Keys;
import org.testng.Reporter;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import generic_utility.BaseClass;
import generic_utility.FileUtility;
import generic_utility.ListenerImplementation;
import generic_utility.WebDriverUtility;
import object_repository.LoginPage;
import object_repository.MicrosoftSignInPage;

@Listeners(ListenerImplementation.class)
public class LoginTest extends BaseClass {
    
    // Test login page with invalid credentials
    @Test(groups = {"regression"}, priority = 1)
    public void testInvalidLogin() throws IOException {
        FileUtility fileUtility = new FileUtility();
        String USERNAME = fileUtility.getDataFromPropertiesFile("un");

        LoginPage loginPage = new LoginPage(driver);
        loginPage.verifyLoginPageElements();
        loginPage.tryLoginWithInvalidUsername(USERNAME);

    }

    // Test login page with Microsoft account
    @Test(groups = {"regression"} , priority = 2)
    public void testMicrosoftLogin() throws IOException, InterruptedException {
        WebDriverUtility wdu = new WebDriverUtility(driver);
        LoginPage loginPage = new LoginPage(driver);
        loginPage.verifyLoginPageElements();
        loginPage.btnMsLogin().click();
        MicrosoftSignInPage msSignInPage = new MicrosoftSignInPage(driver);
        FileUtility fileUtility = new FileUtility();
        String email = fileUtility.getDataFromExcelFile("Sheet1", 0, 0);


        // unable to Automate the Microsoft login page due to security reasons, so using Robot class to enter the passcode but it is not working as expected. Hence, commenting the below line and using sendKeys to enter the email and passcode.


        // msSignInPage.tryToLoginWithMSAccount(email);

        wdu.hardWait(2);
        msSignInPage.getEmailField().sendKeys(email + Keys.ENTER);
        wdu.hardWait(5);
        msSignInPage.enterPassCodeViaRobotClass();
        wdu.hardWait(5);
        msSignInPage.getPageDescription().isDisplayed();
        Reporter.log("Page description: " + msSignInPage.getPageDescription().getText());
        msSignInPage.getExceptionMessageContainer().isDisplayed();
        Reporter.log("Exception message: " + msSignInPage.getExceptionMessageContainer().getText());
        
    }
}
