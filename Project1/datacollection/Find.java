package datacollection;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashSet;
import java.util.List;

public class Find {
    public void Find_user(WebDriver driver, WebDriverWait wait, String url, String user_f, String user_p, int Size,
                          int needcheck, String filePath, WebDriver[] drivers, int delay, int step) {
        Write W = new Write();
        System.out.println(url);
        driver.get(url);
        String userAd = url.split("/")[3];

        JavascriptExecutor js = (JavascriptExecutor) driver;
        HashSet<String> userProfileUrls = new HashSet<>();

        Number scrollPositionBefore = (Number) js.executeScript("return window.scrollY");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        js.executeScript("window.scrollBy(0, 100);");
        Number scrollPositionAfter = (Number) js.executeScript("return window.scrollY");

        while(userProfileUrls.size() < Size && !scrollPositionBefore.equals(scrollPositionAfter)) {
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(user_f)));
            List<WebElement> users = driver.findElements(By.cssSelector(user_f));

            System.out.print("User: ");
            System.out.println(users.size());
            for (WebElement user : users){
                try {
                    WebElement userProfileLink = user.findElement(By.cssSelector(user_p));
                    String userProfileUrl = userProfileLink.getAttribute("href");
                    String usercmt = userProfileUrl.split("/")[3];
                    if (userProfileUrl != null && userProfileUrl.startsWith("https://x.com/") && userProfileUrls.size() < Size)
                    if(needcheck == 0) {
                        if(usercmt.equals(userAd)){
                            System.out.println(userProfileUrl);
                            userProfileUrls.add(userProfileUrl);
                        }
                    } else
                    if(needcheck == 2) {
                        if(!usercmt.equals(userAd)){
                            System.out.println(userProfileUrl);
                            String profileUrl = "https://x.com/" + usercmt;
                            userProfileUrls.add(profileUrl);
                        }
                    } else {
                        System.out.println(userProfileUrl);
                        userProfileUrls.add(userProfileUrl);
                    }
                    System.out.print("Size: ");
                    System.out.println(userProfileUrls.size());
                    System.out.println(needcheck);
                    System.out.println(userAd + " " + usercmt);
                    System.out.println(userProfileUrls.size());
                } catch (Exception e) {
                    continue;
                }
            }
            System.out.println(userProfileUrls.size());
            js.executeScript("window.scrollBy(0, arguments[0]);", step);
            scrollPositionBefore = (Number) js.executeScript("return window.scrollY");
            js.executeScript("window.scrollBy(0, 100);");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            scrollPositionAfter = (Number) js.executeScript("return window.scrollY");
        }
        if(needcheck == 1){
            Check check = new Check();
            check.Check_KOL(userProfileUrls, "userinterface.KOL.txt", drivers);
            System.out.println("finish " + url);
        } else
            if(needcheck == 0){
                for (String userprofileUrl : userProfileUrls) {
                    W.writeToFile(filePath, userprofileUrl, true);
                    W.writeToFile(filePath, "\n", true);
                }
            } else {
                System.out.println("ok    ok           OKKKKKKKKKKKKKK");
                System.out.println(filePath);
                for (String userprofileUrl : userProfileUrls) {
                    System.out.println(userprofileUrl);
                    W.writeToFile(filePath, url + " " + userprofileUrl, true);
                    W.writeToFile(filePath, "\n", true);
                }
            }
    }
}
