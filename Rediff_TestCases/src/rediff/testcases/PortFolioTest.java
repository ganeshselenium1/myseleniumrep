package rediff.testcases;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

public class PortFolioTest {
 
    WebDriver driver;
	
	@Test(priority=1)
	public void createPortfolio() throws InterruptedException
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
		driver.manage().window().maximize();
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
		
		//create portfolio
		//waitForPageToLoad();
		Thread.sleep(10000);
		driver.findElement(By.xpath("//*[@id=\"createPortfolio\"]/img")).click();
		driver.findElement(By.xpath("//*[@id=\"create\"]")).clear();
		driver.findElement(By.xpath("//*[@id=\"create\"]")).sendKeys("Nitin12");
		driver.findElement(By.xpath("//*[@id=\"createPortfolioButton\"]")).click();
		
		
		//waitForPageToLoad();
		Thread.sleep(5000);
		waitStillSelectionToBe("Nitin12");
		
		//driver.quite();
	
	}
	
	public void waitStillSelectionToBe(String expected)
	{
		int i=0;
		while(i!=10)
		{
			WebElement e=driver.findElement(By.xpath("//*[@id=\"portfolioid\"]"));
			Select s=new Select(e);
			String actual=s.getFirstSelectedOption().getText();
			System.out.println(actual);
			if(actual.equals(expected))
				return;
			else
				wait(1);
			i++;
		}
		Assert.fail("Value never change in selected box.....");
	}
	
	public void wait(int time)
	{
		try {
			Thread.sleep(time*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void waitForPageToLoad()
	{
		long i=0;
		JavascriptExecutor js=(JavascriptExecutor)driver;
		while(i!=10)
		{
		 String state=(String)js.executeScript("return document.readystate;");
		 System.out.println(state);
		 if(state.equals("Complete"))
			 break;
		 else
			 wait(1);
		     i++;
		}
		
		i=0;
		while(i!=10)
		{
		  Long d=(Long)js.executeScript("return jQuery.active;");
		  System.out.println(d);
		  if(d.longValue()==0)
			  break;
		  else
			  wait(1);
		      i++;
		}
	}
	
	@Test(priority=2,dependsOnMethods= {"createPortfolio"})
	public void deletePortFolio()
	{
		
	  driver.findElement(By.xpath("//*[@id=\"deletePortfolio\"]")).click();
	  driver.switchTo().alert().accept();
	  driver.switchTo().defaultContent();
	}
}
