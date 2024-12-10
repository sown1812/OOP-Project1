package datacollection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.concurrent.Callable;

public class KOLChecker implements Callable<Void> {
    private String userProfileUrl;
    private String filePath;
    private WriteFile W;
    private WebDriver driver;

    public KOLChecker(String userProfileUrl, String filePath, WriteFile W, WebDriver driver) {
        this.userProfileUrl = userProfileUrl;
        this.filePath = filePath;
        this.W = W;
        this.driver = driver;
    }

    @Override
    public Void call() throws Exception {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

        try {
            driver.get(userProfileUrl);

            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("a[href*='/verified_followers']")));
            WebElement followersElement = driver.findElement(By.cssSelector("a[href*='/verified_followers']"));
            String followersText = followersElement.getText();

            int followersCount = parseFollowersCount(followersText);

            if (followersCount > 100000) {
                String userAd = userProfileUrl.split("/")[3];
                W.writeToFile(filePath, "@" + userAd + " ", true);
                W.writeToFile(filePath, String.valueOf(followersCount) + " ", true);
                W.writeToFile(filePath, userProfileUrl, true);
                W.writeToFile(filePath, "\n", true);
            }

        } catch (Exception e) {
            System.out.println("Error processing " + userProfileUrl + ": " + e.getMessage());
        }

        return null;
    }

    private int parseFollowersCount(String followersText) {
        followersText = followersText.replace(",", "").toLowerCase();
        followersText = followersText.replace(" followers", "").trim();

        if (followersText.contains("k")) {
            followersText = followersText.replace("k", "").trim();
            return (int) (Float.parseFloat(followersText) * 1000);
        } else if (followersText.contains("m")) {
            followersText = followersText.replace("m", "").trim();
            return (int) (Float.parseFloat(followersText) * 1000000);
        }

        return Integer.parseInt(followersText);
    }
}