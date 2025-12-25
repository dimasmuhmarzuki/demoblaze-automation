package com.techmart.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {
    public LoginPage(WebDriver driver) { super(driver); }

    public void login(String user, String pass) throws InterruptedException {
        click(By.id("login2"));
        Thread.sleep(2000);
        type(By.id("loginusername"), user);
        Thread.sleep(1000);
        type(By.id("loginpassword"), pass);
        Thread.sleep(1000);
        click(By.xpath("//button[text()='Log in']"));
        Thread.sleep(2500);
    }
}