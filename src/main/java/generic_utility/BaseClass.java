package generic_utility;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

// import object_repository.DashboardPage;
import object_repository.LoginPage;


public class BaseClass {
	public WebDriver driver = null;
	public static WebDriver sdriver = null;
	public FileUtility fu = null;
    public WebDriverUtility wdu = null;

    @BeforeClass
    public void configBeforeClass() throws IOException{
        this.fu = new FileUtility();
		String BROWSER = fu.getDataFromPropertiesFile("browser");
        this.driver = WebDriverUtility.browserDriver(BROWSER);
        sdriver = this.driver;
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));
        Reporter.log("Browser opened");
    }

    @BeforeMethod 
    public void configBeforeMethod() throws IOException {
        String URL = fu.getDataFromPropertiesFile("url");
        driver.get(URL);
        driver.manage().window().maximize();
    }

    @AfterMethod
    public void configAfterMethod() {
        // Logout logic can be added here if needed
    }

    @AfterClass
    public void closeBrowser() {
        driver.quit();
        Reporter.log("Browser closed");
    }
}
