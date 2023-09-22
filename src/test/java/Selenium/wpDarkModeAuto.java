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
		driver.get("https://trytestingthis.netlify.app/");
		driver.manage().window().maximize();
	}

}
