package datacollection;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Check {

    public void Check_KOL(HashSet<String> userProfileUrls, String filePath, WebDriver[] drivers) {
        Write W = new Write();

        ExecutorService executorService = Executors.newFixedThreadPool(5); // Tạo số lượng thread = số lượng WebDriver
        List<Future<Void>> futures = new ArrayList<>();
        int driverIndex = 0; // Biến chỉ mục để chọn driver từ danh sách

        for (String userProfileUrl : userProfileUrls) {
            // Tạo một task để xử lý mỗi userProfileUrl
            Future<Void> future = executorService.submit(new KOLChecker(userProfileUrl, filePath, W, drivers[driverIndex]));
            futures.add(future);

            // Cập nhật chỉ mục để vòng qua các driver
            driverIndex = (driverIndex + 1) % 5;
        }

        for (Future<Void> future : futures) {
            try {
                future.get(); // Chờ cho mỗi task hoàn thành
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
    }

    private static class KOLChecker implements Callable<Void> {
        private String userProfileUrl;
        private String filePath;
        private Write W;
        private WebDriver driver;

        public KOLChecker(String userProfileUrl, String filePath, Write W, WebDriver driver) {
            this.userProfileUrl = userProfileUrl;
            this.filePath = filePath;
            this.W = W;
            this.driver = driver;
        }

        @Override
        public Void call() throws Exception {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

            try {
                System.out.println(userProfileUrl + "     in");
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
}
