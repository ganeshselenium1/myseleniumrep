package rediff.testcases;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;


public class Dice_Websit_Proj {

	WebDriver driver;
    @Test
	public void searchForJob() throws InterruptedException {
		
		driver=new ChromeDriver();
		String tool="Selenium WebDriver";
		String location="New York, NY";
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
		driver.get("https://www.dice.com/");
		driver.findElement(By.xpath("//input[@name='q']")).clear();
		driver.findElement(By.xpath("//input[@name='q']")).sendKeys("Selenium");
		selectItemfromDropDown(tool);		
		driver.findElement(By.xpath("//input[@name='l']")).clear();
		driver.findElement(By.xpath("//input[@name='l']")).sendKeys("New");
		selectItemfromDropDown(location);
		driver.findElement(By.xpath("//button[@id='findTechJobs']")).click();
		Thread.sleep(2000);
		
		int i=1;
		while(i<=5)
		{
			System.out.println("********** Page Number: "+i+" ********************");
			List<WebElement> jobLink=driver.findElements(By.xpath("//ul[@class='list-inline']/li/h3/a/span"));
			for(WebElement e:jobLink)
			{
				if(!e.getText().trim().equals(""))
					System.out.println(e.getText());
			}
			i++;
			if(i==6)
				break;
			driver.findElement(By.xpath("//a[text()='"+i+"']")).click();
			wait(3);
		}
		
		

	}
    
    public void selectItemfromDropDown(String selection)
    {
    	List<WebElement>options;
    	
    	if(selection.equals("Selenium WebDriver"))
    	{
    		options=driver.findElements(By.xpath("//ul[@class='typeahead__list']/li"));
    	}
    	else
    	{
    	    options=driver.findElements(By.xpath("//ul[@class='typeahead dropdown-menu']/li"));
    	}
		System.out.println("Total options: "+options.size());
		for(WebElement e:options)
		{
			System.out.println(e.getText());
			if(e.getText().equals(selection))
			{
				Actions act=new Actions(driver);
				act.click(e).build().perform();
			}
		}
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
	
	

}
