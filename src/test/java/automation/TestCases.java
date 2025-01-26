package automation;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

// All the Test Cases will be executed in this class 
// Adequate priority has been assigned for sequential execution

public class TestCases extends CallyInitialize {

	@Test(priority = 1)
	public void Login() {

		// Verify the Login status
		boolean login_status = Utility.LOGIN_STATUS;
		Assert.assertEquals(login_status, true);
	}

	
	@Test(priority = 2)
	public void AgentCreation() {

		driver.get("https://app.getcalley.com/agents.aspx");
		
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		WebElement infoDiv1 = driver.findElement(By.id("dt_table_info"));
		String summary_before_add = infoDiv1.getText();
		
		String[] parts1 = summary_before_add.split(" ");
        int agent_count_before_add = Integer.parseInt(parts1[parts1.length - 2]);
		

		for (Agent agent : Utility.agents) {
                        
            driver.findElement(By.id("ContentPlaceHolder1_txt_name")).sendKeys(agent.getName());
    		driver.findElement(By.id("ContentPlaceHolder1_txt_mobile")).sendKeys(agent.getMobile());
    		driver.findElement(By.id("ContentPlaceHolder1_txt_email")).sendKeys(agent.getEmail());
    		driver.findElement(By.id("ContentPlaceHolder1_txt_pass")).sendKeys(agent.getPassword());
    		driver.findElement(By.id("ContentPlaceHolder1_btn_submit")).click();
    		
    		WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
    		WebElement confirmButton1 = wait1.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button.confirm")));
    		confirmButton1.click();
    		
    		try {
    			Thread.sleep(10000);
    		} catch (InterruptedException e) {
    			
    			e.printStackTrace();
    		}
            
        }

	
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {

			e.printStackTrace();
		}
		
		int added_agent_count = Utility.agents.size();
		int expected_agent_count = agent_count_before_add + added_agent_count;
		String expected_summary = "Showing 1 to " + expected_agent_count + " of " + expected_agent_count + " entries";
		
		WebElement infoDiv2 = driver.findElement(By.id("dt_table_info"));
		String summary_after_add = infoDiv2.getText();
		
				 
		Assert.assertEquals(summary_after_add, expected_summary);
		 
	}
		

	@Test(priority = 3)
	public void CallListPowerImport() {

		driver.get("https://app.getcalley.com/call-list-teams.aspx");
		driver.get("https://app.getcalley.com/BulkUploadCsv.aspx");
		
		driver.findElement(By.id("ContentPlaceHolder1_txtlistname")).sendKeys("PowerListUpload1");

        WebElement dropdownButton = driver.findElement(By.cssSelector(".multiselect.dropdown-toggle"));
        dropdownButton.click();

        WebElement selectAllOption = driver.findElement(By.cssSelector("a.multiselect-all"));

        selectAllOption.click();
		

		try {
			WebElement fileInput = driver.findElement(By.id("ContentPlaceHolder1_fileUpload"));

			String filePath = "C:\\DevSpace\\Automation\\Sample File.xlsx";
			fileInput.sendKeys(filePath);
			Thread.sleep(5000);
			driver.findElement(By.id("btnUp")).click();
			Thread.sleep(10000);

            WebElement okButton = driver.findElement(By.cssSelector(".sa-confirm-button-container .confirm"));
            okButton.click();
			

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			// driver.quit();
		}

		String actual_title = driver.getTitle();
		Assert.assertEquals(actual_title, "Getcalley - Map Data");

	}
}
