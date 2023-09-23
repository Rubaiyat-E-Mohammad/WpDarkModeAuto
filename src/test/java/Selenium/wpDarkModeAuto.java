package Selenium;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.*;
import org.openqa.selenium.support.ui.Select;
import org.testng.*;
import org.testng.annotations.*;

public class wpDarkModeAuto {

	WebDriver driver;
	
	@BeforeTest
	public void setBaseURL() throws InterruptedException {
		
		WebDriverManager.edgedriver().setup();
		driver = new EdgeDriver();
		driver.get("http://localhost/Remt/wp-login.php");
		driver.manage().window().maximize();
	}
	
	@Test(priority = 1)
	public void urlCheck() {
		String ExpectedURL = "http://localhost/Remt/wp-login.php";
		String ActualURL = driver.getCurrentUrl();
		Assert.assertEquals(ActualURL, ExpectedURL, "URL Doesn't Matched");
	}
	
	@Test(priority=2)
	public void loggingIn() {
		driver.findElement(By.id("user_login")).sendKeys("trubaiyatemohammad@gmail.com");
		driver.findElement(By.id("user_pass")).sendKeys("T46888468");
		driver.findElement(By.id("rememberme")).click();
		driver.findElement(By.id("wp-submit")).click();
	}
	
	@Test(priority=3)
	public void activateWpDarkMode() throws InterruptedException {
		driver.findElement(By.id("menu-plugins")).click();
		try {
		driver.findElement(By.cssSelector("tr[data-slug=wp-dark-mode]"));
		}catch(Exception e) {
			driver.findElement(By.className("page-title-action")).click();
			driver.findElement(By.id("search-plugins")).sendKeys("wp-dark-mode");
			driver.findElement(By.id("search-plugins")).sendKeys(Keys.RETURN);
			Thread.sleep(5000);
			driver.findElement(By.className("install-now")).click();
			Thread.sleep(20000);
			driver.findElement(By.className("activate-now")).click();
		}
	}
	@Test(priority=4)
	public void enableBackendDarkMode() {
		driver.findElement(By.className("toplevel_page_wp-dark-mode-settings")).click();
		WebElement checkbox = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[2]/div[2]/div[2]/div[1]/form/table/tbody/tr[2]/td/fieldset/label/div/div/label"));
		String isChecked = driver.findElement(By.id("wppool-wp_dark_mode_general[enable_backend]")).getAttribute("checked");
		if(isChecked != null) {
			driver.findElement(By.id("save_settings")).click();
		}else {
			System.out.println("false");
			checkbox.click();
			driver.findElement(By.id("save_settings")).click();
		}
	}

}
