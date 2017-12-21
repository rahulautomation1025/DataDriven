package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.TestBaseNew;

public class BankManagerLogin extends TestBaseNew{

	@Test
	public void BankManagerLogin(){
		
		click("bmlBtn_CSS");
		Assert.assertTrue(IsElementPresent("addCustBtn_CSS"));
		
		
	}
	
	
	
	
}
