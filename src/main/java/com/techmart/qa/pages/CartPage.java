package com.techmart.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage extends BasePage {
    public CartPage(WebDriver driver) { super(driver); }

    public void addProductToCart(String productName) throws InterruptedException {
        click(By.linkText(productName));
        Thread.sleep(2000);
        click(By.xpath("//a[text()='Add to cart']"));
        Thread.sleep(2000);
        getAlertTextAndAccept();
        click(By.id("nava")); // Back to home
        Thread.sleep(2000);
    }

    public void deleteFirstItem() throws InterruptedException {
        click(By.xpath("(//a[text()='Delete'])[1]"));
        Thread.sleep(2500);
    }

    public String getTotalPrice() {
        return driver.findElement(By.id("totalp")).getText();
    }
}