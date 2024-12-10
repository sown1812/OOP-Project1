package datacollection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Log {
    public static final int D = 7;
    public void Log_in(WebDriver driver, WebDriverWait wait, String acc, String pass, String gmail) {
        try {
            driver.get("https://twitter.com/login");

            wait.until(ExpectedConditions.presenceOfElementLocated(By.name("text")));
            WebElement usernameField = driver.findElement(By.name("text"));
            usernameField.sendKeys(acc);

            WebElement nextButton = driver.findElement(By.xpath("//span[text()='Next']"));
            nextButton.click();

            WebDriverWait waitf = new WebDriverWait(driver, Duration.ofSeconds(30));
            try {
                waitf.until(ExpectedConditions.presenceOfElementLocated(By.name("text")));
                WebElement check = driver.findElement(By.name("text"));
                check.sendKeys(gmail);
                WebElement nextButton_2 = driver.findElement(By.xpath("//span[text()='Next']"));
                nextButton_2.click();

//                WebElement emailField = waitf.until(ExpectedConditions.presenceOfElementLocated(By.name("text")));
//                emailField.sendKeys(gmail);
//
//                WebElement verifyButton = driver.findElement(By.xpath("//span[text()='Next']"));
//                verifyButton.click();
            } catch (Exception e) {
            }

            wait.until(ExpectedConditions.presenceOfElementLocated(By.name("password")));

            WebElement passwordField = driver.findElement(By.name("password"));
            passwordField.sendKeys(pass);

            WebElement loginButton = driver.findElement(By.xpath("//span[text()='Log in']"));
            loginButton.click();

            wait.until(ExpectedConditions.urlContains("home"));
        } finally {
        }
    }

    public void Log_out(WebDriver driver, WebDriverWait wait) {
        driver.quit();
    }
}
