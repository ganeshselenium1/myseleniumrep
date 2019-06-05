package rediff.testcases;

import static org.testng.Assert.assertTrue;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTestCase {
	
	WebDriver driver;
	
	@Test
	public void loginTestCase()
	{
		String browser="Chrome";
		
		if(browser.equals("Chrome"))
		{
			driver=new ChromeDriver();
		}
		else if(browser.equals("Mozilla"))
		{
			driver=new FirefoxDriver();
		}
		else if(browser.equals("IE"))
		{
			driver=new InternetExplorerDriver();
		}
		
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		driver.get("https://www.rediff.com/");
		driver.findElement(By.xpath("//a[@class='moneyicon relative']")).click();
		WebDriverWait wait=new WebDriverWait(driver,20);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[text()='Sign In']"))));
		driver.findElement(By.xpath("//a[text()='Sign In']")).click();
		driver.findElement(By.name("email-id")).sendKeys("ashishthakur1983");
		driver.findElement(By.id("emailsubmit")).click();
		WebDriverWait wait1=new WebDriverWait(driver,20);
		wait1.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("userpass"))));
		driver.findElement(By.id("userpass")).sendKeys("pass@1234");
		driver.findElement(By.id("userpass")).sendKeys(Keys.ENTER);
		
		
		String xpathele="//span[@id='username']/a";
		boolean result=isElementPresent(xpathele);
		
		Assert.assertTrue(result, "Not able to login");
		
	}
	
	
	public boolean isElementPresent(String xpathEle)
	{
		int s=driver.findElements(By.xpath(xpathEle)).size();
		if(s==0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	

}
