package rediff.testcases;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
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

public class AddDelStockTest {

	WebDriver driver=null;
	@Test(priority=1)
	public void addStockTest() throws InterruptedException
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
		
		waitforPageToLoad();
		WebElement e=driver.findElement(By.xpath("//*[@id=\"portfolioid\"]"));
		Select s=new Select(e);
		s.selectByVisibleText("Ashi_29");
		driver.findElement(By.xpath("//*[@id=\"addStock\"]")).click();
		driver.findElement(By.xpath("//*[@id=\"addstockname\"]")).sendKeys("Tata Steel");
		driver.findElement(By.xpath("//div[@id='ajax_listOfOptions']/div[2]")).click();
		driver.findElement(By.id("stockPurchaseDate")).click();
		
		String cname=driver.findElement(By.xpath("//table[@id='stock']/tbody/tr/td[2]")).getText();
		System.out.println("cname is: "+cname);
		
		selectDate("12/04/2017");
		
		driver.findElement(By.id("addstockqty")).sendKeys("1000");
		driver.findElement(By.id("addstockprice")).sendKeys("500");
		driver.findElement(By.xpath("//input[@id='addStockButton']")).click();
		Thread.sleep(3000);
		System.out.println("Button clicked successfully.....");
		
		int rn=getRowWithCellData("Persistent Systems");
		System.out.println("Row number is: "+rn);
		if(rn==-1)
			Assert.fail("unable to find....company name...");
		
		//showBSELive();
		
		

	}
	
	public int getRowWithCellData(String Data)
	{
	 List<WebElement> rows=driver.findElements(By.xpath("//table[@id='stock']/tbody/tr"));
	  for(int rNum=0;rNum<rows.size();rNum++)
	  {
		  WebElement row=rows.get(rNum);
		  List<WebElement>cells=row.findElements(By.tagName("td"));
		  for(int cNum=0;cNum<cells.size();cNum++)
		  {
			  WebElement cell=cells.get(cNum);
			  System.out.println(cell.getText());
			  if(!cell.getText().trim().equals("") && Data.contains(cell.getText()))//
			  {
				System.out.println(cell.getText());
				  return ++rNum;
			  }
		  }
		  
	  }
	  return -1;
		
	}
	
	public void showBSELive()
	{
		String bseindex=driver.findElement(By.xpath("//span[@id='BseIndex']")).getText();
		System.out.println("BSE LIVE is: "+bseindex);
		int i1=1;
		while(i1<=5)
		{
			
			System.out.println("BSE LIVE is: "+bseindex);
			showBSELive();
			i1++;
		}
	}
	
	public void selectDate(String d)
	{
		Date current=new Date();
		System.out.println(current);
		
		SimpleDateFormat sd=new SimpleDateFormat("dd/MM/yyyy");
		try {
			Date selected= sd.parse(d);
			String day=new SimpleDateFormat("dd").format(selected);
			String month=new SimpleDateFormat("MMMM").format(selected);
			String year=new SimpleDateFormat("yyyy").format(selected);
			System.out.println(day+"--"+month+"--"+year);
			String desiredmonthyear=month+" "+year;
			System.out.println(desiredmonthyear);
			
			while(true)
			{
				String displayedmonthyear=driver.findElement(By.cssSelector(".dpTitleText")).getText();
				System.out.println(displayedmonthyear);
				if(desiredmonthyear.equals(displayedmonthyear))
				{
					driver.findElement(By.xpath("//td[text()='"+day+"']")).click();
					break;
				}
				else
				{
					if(selected.compareTo(current)>0)
					{
						driver.findElement(By.xpath("//*[@id=\"datepicker\"]/table/tbody/tr[1]/td[4]/button")).click();
					}
					else if(selected.compareTo(current)<0)
					{
						driver.findElement(By.xpath("//*[@id=\"datepicker\"]/table/tbody/tr[1]/td[2]/button")).click();
					}
				}
			}
			
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void waitforPageToLoad()
	{
		JavascriptExecutor js=(JavascriptExecutor)driver;
		String state=(String)js.executeScript("return document.readystate;");
		System.out.println(state);
		
		Long d=(Long)js.executeScript("return jQuery.active;");
		System.out.println(d);
		
	}
	
	@Test(priority=2, dependsOnMethods= {"addStockTest"})
	public void deleteStockTest() throws InterruptedException
	{
		int rNum=getRowWithCellData("Persistent Systems");
		driver.findElement(By.xpath("//table[@id='stock']/tbody/tr["+rNum+"]/td[1]")).click();
		driver.findElements(By.xpath("//input[@name='Delete']")).get(rNum-1).click();
		driver.switchTo().alert().accept();
		Thread.sleep(3000);
		driver.switchTo().defaultContent();
		int Num=getRowWithCellData("Persistent Systems");
		System.out.println(Num);
		Assert.assertEquals(Num, -1);
		
	}
}
