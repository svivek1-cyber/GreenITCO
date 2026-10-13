import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class simpleLoginTest {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://itassetmanagementsoftware.com/rolepermission/admin/login");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(java.time.Duration.ofSeconds(10));

        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("invalid_username");

        driver.findElement(By.xpath("//button[@id='btnContinue']")).click();

        WebElement validateUserMsg = driver.findElement(By.xpath("//span[@class='validateUserMsg']"));
        validateUserMsg.isDisplayed();

        driver.quit();
    }
}
