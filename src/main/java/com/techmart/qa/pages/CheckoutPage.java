package com.techmart.qa.pages;

import com.techmart.qa.config.Config;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CheckoutPage extends BasePage {
    public CheckoutPage(WebDriver driver) { super(driver); }

    public void fillForm(String name) throws InterruptedException {
        type(By.id("name"), name);
        type(By.id("country"), Config.CHECKOUT_COUNTRY);
        type(By.id("city"), Config.CHECKOUT_CITY);
        type(By.id("card"), Config.CHECKOUT_CARD);
        type(By.id("month"), Config.CHECKOUT_MONTH);
        type(By.id("year"), Config.CHECKOUT_YEAR);
        Thread.sleep(1500);
        click(By.xpath("//button[text()='Purchase']"));
    }
}