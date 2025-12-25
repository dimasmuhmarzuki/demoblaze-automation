package com.techmart.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SignupPage extends BasePage {
    public SignupPage(WebDriver driver) { super(driver); }

    public void signup(String user, String pass) throws InterruptedException {
        click(By.id("signin2"));
        Thread.sleep(2000);
        type(By.id("sign-username"), user);
        Thread.sleep(1000);
        type(By.id("sign-password"), pass);
        Thread.sleep(1000);
        click(By.xpath("//button[text()='Sign up']"));
    }
}