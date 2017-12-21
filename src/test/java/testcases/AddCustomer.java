package testcases;

import org.openqa.selenium.Alert;
import org.openqa.selenium.interactions.SendKeysAction;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utilities.TestUtil;
import base.TestBaseNew;

public class AddCustomer extends TestBaseNew {

	@Test(dataProvider = "getdata")
	public void AddCustomertest(String firstname, String lastname,
			String Postalcode, String alertmsg) throws InterruptedException {

		click("addCustBtn_CSS");
		type("firstName_CSS", firstname);
		type("lastName_CSS", lastname);
		type("postCode_CSS", Postalcode);
		click("addCustomer_CSS");

		
			try {
				Alert alert = wait.until(ExpectedConditions.alertIsPresent());

				Assert.assertTrue(alert.getText().contains(alertmsg), "not found");
				Thread.sleep(3000);
				//alert=driver.switchTo().alert();

				alert.accept();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		log.debug("Alert Accepted");
	//Assert.fail("TestCase Failed Manually");

	}

	@DataProvider
	public Object[][] getdata() {

		return TestUtil.getData("AddCustomerTest");
	}

}
