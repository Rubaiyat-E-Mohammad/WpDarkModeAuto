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
		driver.findElement(By.id("user_login")).sendKeys("");
		driver.findElement(By.id("user_pass")).sendKeys("");
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
	
	@Test(priority=5)
	public void checkDarkMode() {
		String check = driver.findElement(By.className("wp-dark-mode-switcher")).getAttribute("class");
		if(check.contains("active")) {
			System.out.println("true");
		}else {
			System.out.println("false");
		}
	}
	
	@Test(priority=6)
	public void floatingSwitchStyle() {
		driver.findElement(By.className("toplevel_page_wp-dark-mode-settings")).click();
		driver.findElement(By.id("wp_dark_mode_switch-tab")).click();
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[2]/div[2]/div[2]/div[4]/form/table/tbody/tr[2]/td/fieldset/label[3]/img")).click();
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[2]/div[2]/div[2]/div[4]/form/div/p/input")).click();
	}
	
	@Test(priority=7)
	public void floatingSwitchSize() {
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[2]/div[2]/div[2]/div[4]/form/table/tbody/tr[3]/td/div/span[6]")).click();
		WebElement slider = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[2]/div[2]/div[2]/div[4]/form/table/tbody/tr[4]/td/div/div[1]"));
		Actions action = new Actions(driver);
	    action.clickAndHold(slider).moveByOffset(125, 0).perform();
	    driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[2]/div[2]/div[2]/div[4]/form/div/p/input")).click();
	}
	
	@Test(priority=8)
	public void switchPosition() throws InterruptedException {
		WebElement position = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[2]/div[2]/div[2]/div[4]/form/table/tbody/tr[5]/td/select"));
		Select newPosition = new Select(position);
		newPosition.selectByValue("right_bottom");
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[2]/div[2]/div[2]/div[4]/form/div/p/input")).click();
	}
	
	@Test(priority=9)
	public void disableKeyboardShortcut() {
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[2]/div[2]/div[1]/ul/li[8]/a/span")).click();
		WebElement checkbox = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[2]/div[2]/div[2]/div[8]/form/table/tbody/tr[5]/td/fieldset/label/div/div/label"));
		String isChecked = driver.findElement(By.id("wppool-wp_dark_mode_accessibility[keyboard_shortcut]")).getAttribute("checked");
		if(isChecked != null) {
			driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[2]/div[2]/div[2]/div[8]/form/div/p/input")).click();
		}else {
			System.out.println("false");
			checkbox.click();
			driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[2]/div[2]/div[2]/div[8]/form/div/p/input")).click();
		}
		
	}
	
	@Test(priority=10)
	public void toggleDarkmodeAnimation() {
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[2]/div[2]/div[1]/ul/li[13]/a/span")).click();
		WebElement checkbox = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[2]/div[2]/div[2]/div[13]/form/table/tbody/tr[1]/td/fieldset/label/div/div/label"));
		String isChecked = driver.findElement(By.id("wppool-wp_dark_mode_animation[toggle_animation]")).getAttribute("checked");
		if(isChecked != null) {
			driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[2]/div[2]/div[2]/div[13]/form/div/p/input")).click();
		}else {
			System.out.println("false");
			checkbox.click();
			driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[2]/div[2]/div[2]/div[13]/form/div/p/input")).click();
		}
		WebElement position = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[2]/div[2]/div[2]/div[13]/form/table/tbody/tr[2]/td/select"));
		Select newPosition = new Select(position);
		newPosition.selectByValue("pulse");
		driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[2]/div[1]/div[2]/div[2]/div[2]/div[13]/form/div/p/input")).click();
	}
	
	@Test(priority=11)
	public void checkFrontend() {
		driver.get("http://localhost/Remt/");
		driver.findElement(By.xpath("/html/body/div[3]/label/img[2]")).click();
	}
	
	@AfterTest
	public void endSession() {
		driver.quit();
	}
}
