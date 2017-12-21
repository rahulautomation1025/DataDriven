package testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.Test;

import base.TestBaseNew;

public class OpenAccount extends TestBaseNew {

	@Test
	public void OpenAccountTest() throws InterruptedException{
		
		click("openaccount_XPATH");
		Thread.sleep(3000);
	
		select("customername_XPATH","Harry Potter");
		
		select("currency_XPATH","Rupee");
		click("process_XPATH");
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());

		//Assert.assertTrue(alert.getText().contains("Account created successfully"), "not found");
		Thread.sleep(3000);
		//alert=driver.switchTo().alert();

		alert.accept();
		
	}
	
	
}
