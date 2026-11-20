
    package org.test;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

    public class webpagetest {

        private WebDriver driver;

        @BeforeMethod
        public void setUp() {
            ChromeOptions options = new ChromeOptions();
            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

            // Points to src/main/resources/index.html where your web files are located
            String fileUrl = Paths.get("src/main/resources/index.html").toAbsolutePath().toUri().toString();
            driver.get(fileUrl);
        }

        @Test(priority = 1)
        public void verifyMenuLoadsAllItems() {
            List<WebElement> menuCards = driver.findElements(By.className("menu-card"));
            Assert.assertEquals(menuCards.size(), 6, "All 6 menu items should be visible initially.");
        }

        @Test(priority = 2)
        public void verifyCategoryFiltering() {
            WebElement dessertsBtn = driver.findElement(By.cssSelector("button[data-category='desserts']"));
            dessertsBtn.click();

            List<WebElement> visibleCards = driver.findElements(By.className("menu-card"));
            Assert.assertEquals(visibleCards.size(), 2, "Only 2 dessert items should be displayed.");

            for (WebElement card : visibleCards) {
                Assert.assertEquals(card.getAttribute("data-category"), "desserts");
            }
        }

        @Test(priority = 3)
        public void verifyAddToCartAndCheckoutFlow() {
            // Add Garlic Bread ($5.50) and Butter Chicken ($14.50)
            driver.findElement(By.cssSelector("button.add-btn[data-id='1']")).click();
            driver.findElement(By.cssSelector("button.add-btn[data-id='3']")).click();

            // Verify cart count and total price
            String count = driver.findElement(By.id("cart-count")).getText();
            String total = driver.findElement(By.id("cart-total")).getText();
            Assert.assertEquals(count, "2", "Cart count should be 2.");
            Assert.assertEquals(total, "20.00", "Total price should be 20.00.");

            // Complete checkout
            WebElement checkoutBtn = driver.findElement(By.id("checkout-btn"));
            Assert.assertTrue(checkoutBtn.isEnabled(), "Checkout button should be enabled.");
            checkoutBtn.click();

            // Verify confirmation message and cart reset
            WebElement confirmation = driver.findElement(By.id("order-confirmation"));
            Assert.assertTrue(confirmation.isDisplayed(), "Order confirmation should be visible.");
            Assert.assertTrue(confirmation.getText().contains("$20.00"));
            Assert.assertEquals(driver.findElement(By.id("cart-count")).getText(), "0", "Cart should reset after order.");
        }

        @AfterMethod
        public void tearDown() {
            if (driver != null) {
                driver.quit();
            }
        }
    }
'");
        }
        @AfterTest
        public void closeBrowser() throws InterruptedException {
            Thread.sleep(1000);
            driver.quit();
        }
    }


}
