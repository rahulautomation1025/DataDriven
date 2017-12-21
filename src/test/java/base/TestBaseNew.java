package base;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import utilities.DbManager;
import utilities.ExcelReader;

public class TestBaseNew {

	public static WebDriver driver;
	public static ExcelReader excel = new ExcelReader(
			System.getProperty("user.dir")
					+ "\\src\\test\\resources\\excel\\testdata.xlsx");
	public static Logger log = Logger.getLogger("devpinoyLogger");
	public static Properties config = new Properties();
	public static Properties OR = new Properties();
	public FileInputStream fis;
	public WebDriverWait wait;
	public static WebElement dropdown;

	@BeforeSuite
	public void setUp() {

		if (driver == null) {

			try {
				fis = new FileInputStream(
						System.getProperty("user.dir")
								+ "\\src\\test\\resources\\propertiess\\config.properties");

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				config.load(fis);
				log.debug("Config properties loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				fis = new FileInputStream(System.getProperty("user.dir")
						+ "\\src\\test\\resources\\propertiess\\OR.properties");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				OR.load(fis);
				log.debug("OR properties loaded");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		if (config.getProperty("browser").equals("chrome")) {

			System.setProperty(
					"WebDriver.chrome.driver",
					System.getProperty("user.dir")
							+ "\\src\\test\\resources\\executables\\chromedriver.exe");

			driver = new ChromeDriver();
			log.debug("Chrome driver launched");

		} else if (config.getProperty("browser").equals("firefox")) {

			driver = new FirefoxDriver();
			log.debug("Firefox driver launched");

		} else if (config.getProperty("browser").equals("IE")) {

			System.setProperty(
					"WebDriver.ie.driver",
					System.getProperty("user.dir")
							+ "\\src\\test\\resources\\executables\\IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			log.debug("IE driver launched");

		}

		driver.get(config.getProperty("testsiteurl"));
		log.debug("Navigated to : " + config.getProperty("testsiteurl"));
		driver.manage().window().maximize();
		// driver.manage().timeouts().implicitlyWait(Integer.parseInt(config.getProperty("implicit.wai")),
		// TimeUnit.SECONDS);
		driver.manage()
				.timeouts()
				.implicitlyWait(
						Integer.parseInt(config.getProperty("implicit.wait")),
						TimeUnit.SECONDS);
		// wait=new WebDriverWait(driver,
		// Integer.parseInt(config.getProperty("explicit.wait")));
		wait = new WebDriverWait(driver, Integer.parseInt(config
				.getProperty("explicit.wait")));
		try {
			DbManager.setMysqlDbConnection();
			log.debug("DB connection Created !!!");

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void click(String Locator) {

		if (Locator.endsWith("_XPATH")) {

			driver.findElement(By.xpath(OR.getProperty(Locator))).click();
		} else if (Locator.endsWith("_CSS")) {

			driver.findElement(By.cssSelector(OR.getProperty(Locator))).click();

		} else if (Locator.endsWith("_ID")) {

			driver.findElement(By.id(OR.getProperty(Locator))).click();

		}
		
		log.debug("Click on Element"+OR.getProperty(Locator));
	}
	
	public void type(String Locator, String value) {

		if (Locator.endsWith("_XPATH")) {

			driver.findElement(By.xpath(OR.getProperty(Locator))).sendKeys(value);
		} else if (Locator.endsWith("_CSS")) {

			driver.findElement(By.cssSelector(OR.getProperty(Locator))).sendKeys(value);

		} else if (Locator.endsWith("_ID")) {

			driver.findElement(By.id(OR.getProperty(Locator))).sendKeys(value);

		}
		
		log.debug("Type on Element"+OR.getProperty(Locator) + "entered Value is "+OR.getProperty(value));
	}
	
	public void select(String Locator, String value){
		
		if (Locator.endsWith("_XPATH")) {

			dropdown=driver.findElement(By.xpath(OR.getProperty(Locator)));
		} else if (Locator.endsWith("_CSS")) {

			dropdown=driver.findElement(By.cssSelector(OR.getProperty(Locator)));

		} else if (Locator.endsWith("_ID")) {

			dropdown=driver.findElement(By.id(OR.getProperty(Locator)));

		}
		
		Select select=new Select(dropdown);
		select.selectByVisibleText(value);
		
		log.debug("Select an Element"+OR.getProperty(Locator) + "Select Value is "+OR.getProperty(value));
		
		
		
		
	}
	
	public boolean IsElementPresent(String Locator){
		try{
		if (Locator.endsWith("_XPATH")) {

			driver.findElement(By.xpath(OR.getProperty(Locator)));
		} else if (Locator.endsWith("_CSS")) {

			driver.findElement(By.cssSelector(OR.getProperty(Locator)));

		} else if (Locator.endsWith("_ID")) {

			driver.findElement(By.id(OR.getProperty(Locator)));

		}
		}
		catch(Throwable t){
			
			
			log.debug("Element not found"+ Locator+t.getMessage());
			
			return false;
			
		}
		
		log.debug("Validating presence of element"+ Locator);
		
		
		return true;
		
		
	}
	
	@AfterSuite
	public void tearDown() {

		driver.quit();
		log.debug("Test successfully Executed !!!");

	}

}
