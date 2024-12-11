package datacollection;

import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Check {
    public static final int D = 7;
    public void Check_KOL(HashSet<String> userProfileUrls, String filePath, WebDriver[] drivers) {
        WriteFile W = new WriteFile();

        ExecutorService executorService = Executors.newFixedThreadPool(D); // Tạo số lượng thread = số lượng WebDriver
        List<Future<Void>> futures = new ArrayList<>();
        int driverIndex = 0; // Biến chỉ mục để chọn driver từ danh sách

        for (String userProfileUrl : userProfileUrls) {
            // Tạo một task để xử lý mỗi userProfileUrl
            Future<Void> future = executorService.submit(new KOLChecker(userProfileUrl, filePath, W, drivers[driverIndex]));
            futures.add(future);

            // Cập nhật chỉ mục để vòng qua các driver
            driverIndex = (driverIndex + 1) % D;
        }

        for (Future<Void> future : futures) {
            try {
                future.get(); // Chờ cho mỗi task hoàn thành
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }
    }
}
