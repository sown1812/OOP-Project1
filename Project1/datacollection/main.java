package datacollection;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class main {
    public static final int MAX = 9999;
    public static void main(String[] args) {
        System.setProperty("webdriver.edge.driver", "D:\\Code\\Edgedriver\\msedgedriver.exe");
        EdgeOptions options = new EdgeOptions();
//        options.addArguments("--headless");
        options.addArguments("--disable-notifications");

        WebDriver[] driver = new WebDriver[10];
        WebDriverWait[] wait = new WebDriverWait[10];
        for (int i = 0; i < 5; i++) {
            driver[i] = new EdgeDriver(options);
            wait[i] = new WebDriverWait(driver[i], Duration.ofSeconds(180));
        }

        String[][] accounts = {
                {"@tran_key666", "Key123456", "ahkey357@gmail.com"},
                {"@tran_key579", "Key123456", "ahkey579@gmail.com"},
                {"@hust_21503", "hudvat-7qoHfi-todryv", "projecthustoop@gmail.com"},
                {"@HustOop345399", "sundi4-guhMob-dopvun", "projecthustoop@gmail.com"},
                {"@project3834884", "gAkkoj-xifvy9-zirmuj", "projecthustoop@gmail.com"}
        };

        Log L = new Log();
        Write W = new Write();
        Find F = new Find();
        try {
            ExecutorService executorService = Executors.newFixedThreadPool(5);
            List<Future<Void>> futures = new ArrayList<>();
            for (int i = 0; i < accounts.length; i++) {
                final int index = i;
                Future<Void> future = executorService.submit(() -> {
                    L.Log_in(driver[index], wait[index], accounts[index][0], accounts[index][1], accounts[index][2]);
                    return null;
                });
                futures.add(future);
            }

            for (Future<Void> future : futures) {
                try {
                    future.get(); // Chờ cho mỗi task hoàn thành
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            executorService.shutdown();

            System.out.println("Dang nhap thanh cong");
            W.writeToFile("KOL.txt", "", false);
            try (BufferedReader br = new BufferedReader(new FileReader("hastag.txt"))) {
                String acc;
                W.writeToFile("KOL.txt", "", false);
                while ((acc = br.readLine()) != null){
                    System.out.println(acc);
                    acc = "https://twitter.com/search?q=%23" + acc + "&src=recent_search_click&f=user";
                    F.Find_user(driver[0], wait[0], acc, "button[role='button']", "a[href*='/']", 30, 1,
                    "KOL.txt", driver, 10000, 3300);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            W.writeToFile("tweets.txt", "", false);
            executorService = Executors.newFixedThreadPool(5);

            futures = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader("KOL.txt"))) {
                String acc;
                int driverIndex = 0;
                while ((acc = br.readLine()) != null) {
                    String[] parts = acc.split(" ");
                    String url = parts[parts.length - 1];

                    Future<Void> future = executorService.submit(new FindTaskCallable(driver[driverIndex], wait[driverIndex], url,
                            "article[role='article']", "a[href*='/status/']", 5, 0,
                            "tweets.txt", driver, 5000, 2000));

                    futures.add(future);

                    driverIndex = (driverIndex + 1) % 5;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (Future<Void> future : futures) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            executorService.shutdown();

            W.writeToFile("edge.txt", "", false);

            executorService = Executors.newFixedThreadPool(5);
            futures = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader("tweets.txt"))) {
                String tweet;
                int driverIndex = 0;
                while ((tweet = br.readLine()) != null) {

                    Future<Void> future = executorService.submit(new FindTaskCallable(driver[driverIndex], wait[driverIndex], tweet,
                            "article[role='article']", "a[href*='/status/']", MAX, 2, "edge.txt",
                            driver, 5000, 2000));

                    futures.add(future);

                    driverIndex = (driverIndex + 1) % 5;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (Future<Void> future : futures) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            executorService.shutdown();

            W.writeToFile("edge2.txt", "", false);
            executorService = Executors.newFixedThreadPool(5);
            futures = new ArrayList<>();

            try (BufferedReader br = new BufferedReader(new FileReader("tweets.txt"))) {
                String tweet;
                int driverIndex = 0;
                while ((tweet = br.readLine()) != null) {
                    tweet = tweet + "/retweets";
                    Future<Void> future = executorService.submit(new FindTaskCallable(driver[driverIndex], wait[driverIndex], tweet,
                            "button[role='button']", "a[href*='/']", MAX, 3,
                            "edge2.txt", driver, 3500, 1000));

                    futures.add(future);

                    driverIndex = (driverIndex + 1) % 5;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (Future<Void> future : futures) {
                try {
                    future.get();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
            executorService.shutdown();
        } finally {
            for (int i = 0; i < 5; i++) {
                driver[i].quit();
            }
        }
    }
}
