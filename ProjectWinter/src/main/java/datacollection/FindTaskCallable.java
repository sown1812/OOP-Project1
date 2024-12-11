package datacollection;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.Callable;

public class FindTaskCallable implements Callable<Void> {
    private WebDriver driver;
    private WebDriverWait wait;
    private String url;
    private String user_f;
    private String user_p;
    private int size;
    private int needcheck;
    private String filePath;
    private WebDriver[] drivers;
    private int delay;
    private int step;

    public FindTaskCallable(WebDriver driver, WebDriverWait wait, String url, String user_f, String user_p,
                            int size, int needcheck, String filePath, WebDriver[] drivers, int delay, int step) {
        this.driver = driver;
        this.wait = wait;
        this.url = url;
        this.user_f = user_f;
        this.user_p = user_p;
        this.size = size;
        this.needcheck = needcheck;
        this.filePath = filePath;
        this.drivers = drivers;
        this.delay = delay;
        this.step = step;
    }

    @Override
    public Void call() throws Exception {
        Find F = new Find();
        F.Find_user(driver, wait, url, user_f, user_p, size, needcheck, filePath, drivers, delay, step);
        return null;
    }
}
