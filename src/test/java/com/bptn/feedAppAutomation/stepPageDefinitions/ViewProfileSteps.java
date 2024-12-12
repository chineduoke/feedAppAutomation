package com.bptn.feedAppAutomation.stepPageDefinitions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.bptn.feedAppAutomation.provider.ResourceProvider;
import com.bptn.feedAppAutomation.web.DriverManager;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ViewProfileSteps {
	@Autowired
	DriverManager driverManager;

	@Autowired
	ResourceProvider provider;

	By xPathMessageBadge = By.xpath("//*[@id='messageBadge']");

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Given("User is on the my feeds page {string}")
	public void user_is_on_the_my_feeds_page(String string) {
		this.driverManager.getDriver().get(this.provider.getFrontEndUrl() + string);
	}

	@When("User clicks on {string} which navigates to profile page")
	public void user_clicks_on_which_navigates_to_profile_page(String string) {
		this.driverManager.getDriver().findElement(By.linkText(string)).click();
	}

	@Then("the form fields should have the following values:")
	public void the_form_fields_should_have_the_following_values(io.cucumber.datatable.DataTable dataTable) {
		List<Map<String, String>> fields = dataTable.asMaps(String.class, String.class);
		for (Map<String, String> field : fields) {
			String fieldName = field.get("fieldName");
			String expectedValue = field.get("expectedValue");

			WebElement formField = this.driverManager.getDriver().findElement(By.name(fieldName));

			String actualValue;
			if (formField.getTagName().equals("input") && formField.getAttribute("type").equals("checkbox")) {
				actualValue = formField.isSelected() ? "true" : "false";
			} else {
				actualValue = formField.getAttribute("value");
			}

			assertEquals(expectedValue, actualValue, "Value mismatch for field: " + fieldName);

		}
	}

	@And("User clears the following fields:")
	public void userClearsTheFollowingFields(io.cucumber.datatable.DataTable dataTable) {
		dataTable.asList().forEach(fieldName -> {
			WebElement field = this.driverManager.getDriver().findElement(By.name(fieldName));
			field.clear();
		});
	}

	@When("User updates the profile fields with the following values:")
	public void userUpdatesTheProfileFieldsWithValues(io.cucumber.datatable.DataTable dataTable) {
		dataTable.asMaps().forEach(field -> {
			String fieldName = field.get("fieldName");
			String value = field.get("value");
			WebElement fieldElement = this.driverManager.getDriver().findElement(By.name(fieldName));
			fieldElement.sendKeys(value);
		});
	}

	@When("User submits the profile form")
	public void userSubmitsTheProfileForm() {
		this.driverManager.getDriver().findElement(By.xpath("//input[@type='submit' and @value='Save']")).click();
	}

	@Then("The confirmation message should be {string}")
	public void theConfirmationMessageShouldBe(String expectedMessage) {

		String actualMessage = this.getMessage(this.xPathMessageBadge);
		assertEquals(expectedMessage, actualMessage, "The success message does not match.");
	}

	public String getMessage(By xPathMessage) {
		try {
			WebDriverWait wait = new WebDriverWait(this.driverManager.getDriver(), Duration.ofSeconds(10));
			WebElement messageBadgeElement = wait.until(ExpectedConditions.presenceOfElementLocated(xPathMessage));

			Thread.sleep(2000);

			return messageBadgeElement.getText();
		} catch (Exception ex) {
			this.logger.error(ex.getMessage(), ex);
		}

		return null;
	}

}
