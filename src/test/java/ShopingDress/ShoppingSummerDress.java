package ShopingDress;
import java.time.Duration;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ShoppingSummerDress {


	public static WebDriver driver = null;
	
	static {
		
		//initiating Chrome driver with various required options
		System.setProperty("webdriver.chrome.driver", "C:\\Users\\HP\\eclipse-workspace\\com.Assignments\\src\\test\\resources\\ChromeDriver\\chromedriver.exe");
		ChromeOptions chOptions = new ChromeOptions();
		chOptions.setImplicitWaitTimeout(Duration.ofSeconds(10));
		chOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
		chOptions.setAcceptInsecureCerts(true);
		
		driver = new ChromeDriver(chOptions);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("http://automationpractice.com/index.php");
		driver.manage().window().maximize();
		
	}
	
	public static void addDressToCompare(String typeOfDress) throws InterruptedException {
		
		//Searching the given product
		WebElement searchBox = driver.findElement(By.id("search_query_top"));
		
		searchBox.clear();
		searchBox.sendKeys(typeOfDress +Keys.ENTER);
		
		//verifying the number of results displayed
		WebElement countLabel = new WebDriverWait(driver, Duration.ofSeconds(10))
		        .until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='product-count']")));
		
		//getting number of search results from label text
		String noOfDressesDisplayed = countLabel.getText();
		System.out.println("number of dresses displayed:"+noOfDressesDisplayed);
		noOfDressesDisplayed = noOfDressesDisplayed.substring(17, 19).trim();
		int noOdDresses = Integer.parseInt(noOfDressesDisplayed);
		
		if(noOdDresses>=1) {
						
			//Clicking add to compare button
			Actions actions = new Actions(driver);
			actions.moveToElement(driver.findElement(By.xpath("//ul[@class='product_list grid row']/li[1]//a"))).perform();
			driver.findElement(By.xpath("//li[1]//a[text()='Add to Compare']")).click();
			
		
		}
		else {
			System.out.println( " the given dress is not available in the stock");
		}

				
	}
	
	public static void chooseLessPriceProduct() {
		
		//Clicking compare button
				WebElement compareButton = new WebDriverWait(driver, Duration.ofSeconds(10))
				        .until(ExpectedConditions.elementToBeClickable(By.xpath("//form[@class='compare-form']//span")));
				if(compareButton.isEnabled()){
					compareButton.click();
				}
				else {
					System.out.println("compare button is not enabled, nicrease wait time or expected condition");
				}
				
		//Getting prices of each dress
		String price1 = driver.findElement(By.xpath("//td[2]/div[@class='prices-container'][1]/span")).getText().replace("$", "");
		String price2 = driver.findElement(By.xpath("//td[3]/div[@class='prices-container'][1]/span")).getText().replace("$", "");
		
		//comparing each dress, remove the other dress, click add to cart then proceed to checkout
		if (Float.parseFloat(price1)>Float.parseFloat(price2)) {
			
			driver.findElement(By.xpath("//td[3]//a[@title='Remove']")).click();
			driver.findElement(By.xpath("//td[2]//a/span[text()='Add to cart']")).click();	
			driver.findElement(By.xpath("//a/span[contains(text(),'Proceed to checkout')]")).click();

		}
		else {
			driver.findElement(By.xpath("//td[2]//a[@title='Remove']")).click();
			driver.findElement(By.xpath("//td[2]//a/span[text()='Add to cart']")).click();
			driver.findElement(By.xpath("//a/span[contains(text(),'Proceed to checkout')]")).click();		

		}
	}
	
	public static void main(String args[]) throws InterruptedException {
		addDressToCompare("PRINTED SUMMER DRESS");
		addDressToCompare("Faded Short Sleeve T-shirts");
		chooseLessPriceProduct();
	
	}

}
