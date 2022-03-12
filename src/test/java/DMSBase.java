import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import pages.DMSPage;

import java.util.concurrent.TimeUnit;

/**
 * @author Sergey Nesterov
 */
public class DMSBase {
    protected static WebDriver driver;
    protected static pages.DMSPage DMSPage;

    @BeforeAll
    public static void beforeAll(){
        System.setProperty("webdriver.gecko.driver", "src\\test\\resources\\browser-drivers\\geckodriver.exe");
        System.setProperty("webdriver.chrome.driver", "src\\test\\resources\\browser-drivers\\chromedriver98.exe");
        System.setProperty("webdriver.ie.driver", "src\\test\\resources\\browser-drivers\\IEDriverServer.exe");
        System.setProperty("webdriver.edge.driver", "src\\test\\resources\\browser-drivers\\msedgedriver.exe");
        String browser = System.getProperty("browser");
//        String browser = "";
        switch (browser==null ? "" : browser){
            case "chrome":
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
            case "edge":
                driver = new EdgeDriver();
                break;
            case "IE":
                driver = new InternetExplorerDriver();
                break;
            default:
                driver = new ChromeDriver();
        }
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
    }

    @BeforeEach
    public void beforeEach(){
        DMSPage = new DMSPage(driver);
    }

    @AfterAll
    public static void afterAll(){
        driver.quit();
    }
}
