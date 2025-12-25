package com.techmart.qa.tests;
import com.techmart.qa.config.Config;
import com.techmart.qa.pages.*;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class DemoblazeTest extends BaseTest {

    // 1. SIGN UP
    @Test(priority = 1)
    public void testStep1_Signup() throws InterruptedException {
        SignupPage signupPage = new SignupPage(driver);
        signupPage.signup(Config.USERNAME, Config.PASSWORD);

        // Jeda agar audiens melihat alert sukses/user exist
        String msg = signupPage.getAlertTextAndAccept();
        System.out.println("Signup Result: " + msg);

        driver.navigate().refresh(); // Refresh agar modal tertutup sempurna
        Thread.sleep(2000);
    }
    // 2. LOGIN NEGATIVE (Salah Password)
    @Test(priority = 2)
    public void testStep2_LoginWrongPassword() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(Config.USERNAME, Config.WRONG_PASSWORD);

        // Jeda agar audiens melihat pesan kesalahan dari sistem
        String msg = loginPage.getAlertTextAndAccept();
        System.out.println("Login Negative Result: " + msg);

        driver.navigate().refresh();
        Thread.sleep(2000);
    }
    // 3. LOGIN POSITIVE
    @Test(priority = 3)
    public void testStep3_LoginValid() throws InterruptedException {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(Config.USERNAME, Config.PASSWORD);
        // PENTING: Tunggu sampai text "Welcome [username]" muncul di navbar
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nameofuser")));
            System.out.println("Login Berhasil: User terditeksi di Navbar.");
        } catch (Exception e) {
            Assert.fail("Gagal Login: Elemen 'nameofuser' tidak muncul setelah 12 detik.");
        }
        Thread.sleep(3000);
        Assert.assertTrue(driver.findElement(By.id("logout2")).isDisplayed());
    }
    // 4. TAMBAH PRODUK KE KERANJANG
    @Test(priority = 4)
    public void testStep4_AddProducts() throws InterruptedException {
        CartPage cartPage = new CartPage(driver);
        // Tambah S6
        cartPage.addProductToCart(Config.PHONE_PRODUCT);
        Thread.sleep(2000);
        // Tambah Sony Vaio
        cartPage.addProductToCart(Config.LAPTOP_PRODUCT);
        Thread.sleep(2000);
    }
    // 5. VERIFIKASI TOTAL HARGA
    @Test(priority = 5)
    public void testStep5_VerifyTotal() throws InterruptedException {
        // Klik menu Cart
        driver.findElement(By.id("cartur")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        // Tunggu sampai tabel belanja terisi (muncul angka di total)
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("totalp")));
            // Jeda tambahan agar perhitungan total dari server selesai
            Thread.sleep(4000);
        } catch (Exception e) {
            System.out.println("Peringatan: Elemen harga tidak muncul atau keranjang kosong.");
        }

        CartPage cartPage = new CartPage(driver);
        String total = cartPage.getTotalPrice();
        System.out.println("Total Price Verified: $" + total);
        // Pastikan total tidak kosong sebelum di-Assert
        Assert.assertFalse(total.isEmpty(),
                "Harga total kosong, item mungkin tidak masuk keranjang.");
        Assert.assertEquals(total, "1150");
    }

    // 6. DELETE ITEM (Demo Fitur Delete)
    @Test(priority = 6)
    public void testStep6_DeleteItems() throws InterruptedException {
        CartPage cartPage = new CartPage(driver);
        // Hapus item pertama
        cartPage.deleteFirstItem();
        Thread.sleep(2000);
        // Hapus item kedua
        cartPage.deleteFirstItem();
        // Tunjukkan keranjang yang sekarang kosong
        Thread.sleep(3000);
    }

    // 7. SIAPKAN TRANSAKSI BARU
    @Test(priority = 7)
    public void testStep7_PreparePurchase() throws InterruptedException {
        driver.findElement(By.id("nava")).click();
        Thread.sleep(2000);
        CartPage cartPage = new CartPage(driver);
        cartPage.addProductToCart(Config.PHONE_PRODUCT);
    }

    // 8. VALIDASI CHECKOUT (Tanpa Nama)
    @Test(priority = 8)
    public void testStep8_ValidateCheckout() throws InterruptedException {
        driver.findElement(By.id("cartur")).click();
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[text()='Place Order']")).click();
        Thread.sleep(2000);
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.fillForm(""); // Nama dikosongkan untuk trigger alert browser
        // Beri waktu audiens melihat browser alert "Please fill out this field"
        Thread.sleep(4000);
        checkoutPage.getAlertTextAndAccept();
    }

    // 9. CHECKOUT BERHASIL (Puncak Demo)
    @Test(priority = 9)
    public void testStep9_FinalPurchase() throws InterruptedException {
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        // Isi nama yang tadi kosong
        checkoutPage.type(By.id("name"), Config.CHECKOUT_NAME);
        Thread.sleep(2000);
        driver.findElement(By.xpath("//button[text()='Purchase']")).click();

        // MOMEN KRUSIAL: Tampilkan centang sukses dan struk belanja agak lama
        Thread.sleep(7000);
        Assert.assertTrue(driver.findElement(By.xpath
                ("//h2[text()='Thank you for your purchase!']")).isDisplayed());
        driver.findElement(By.xpath("//button[text()='OK']")).click();
    }

    // 10. LOGOUT & PENUTUP
    @Test(priority = 10)
    public void testStep10_Logout() throws InterruptedException {
        Thread.sleep(2000);
        driver.findElement(By.id("logout2")).click();
        Thread.sleep(3000);
        System.out.println("Demo Selesai.");
    }
}